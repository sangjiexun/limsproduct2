/****************************************************************************
 * 命名规范：
 * 本控制层调整必须是按照如下的路径调整：@RequestMapping("/device/system/sample")，全部小写。
 * 列表信息listxxxx，獲取信息：getxxxx 编辑信息：editxxxx 删除信息：detelexxxx 新增信息 newxxxx
 * 导出信息exportxxxx 保存信息：savexxxx 
 ****************************************************************************/

package net.zjcclims.web.system;

import net.sf.json.JSONObject;
import net.zjcclims.dao.UserDAO;
import net.zjcclims.domain.User;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/****************************************************************************
 * 功能：系统后台管理模块 作者：魏诚 时间：2014-07-14
 ****************************************************************************/
@Controller("OAuth2Controller")
public class OAuth2Controller<JsonResult> {
    @Autowired
    private UserDAO userDAO;

    /************************************************************
     * @初始化WebDataBinder，这个WebDataBinder用于填充被@InitBinder注释的处理 方法的command和form对象
     *
     ************************************************************/
    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) {
        binder.registerCustomEditor(Calendar.class, new org.skyway.spring.util.databinding.CustomCalendarEditor());
        binder.registerCustomEditor(byte[].class, new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
        binder.registerCustomEditor(boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(false));
        binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
        binder.registerCustomEditor(java.math.BigDecimal.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
        binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Integer.class, true));
        binder.registerCustomEditor(java.util.Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
        binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
        binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Long.class, true));
        binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Double.class, true));
    }

    @Value("${access-token-uri}")
    private String accessTokenUri;

    @Value("${user-authorization-uri}")
    private String userAuthorizationUri;

    @Value("${application-host}")
    private String applicationHost;

    @Value("${user-info-uri:}")
    private String userInfoUri;

    @Value("${user-profile-uri}")
    private String userProfileUri;

    @Value("${client_id}")
    private String clientId;

    @Value("${secret_key}")
    private String secretKey;

    @Value("${scope}")
    private String scope;

    @Value("${logout-url}")
    private String logoutUrl;

    //获取用户username的key
    @Value("${user-key}")
    private String userKey;

    /************************************************************
     * Description：Oauth2认证step1-获取code
     * @throws Exception
     * @作者：魏诚
     * @日期：2017-08-30
     ************************************************************/
    @RequestMapping(value = "/lims/authorization_code", method = RequestMethod.POST)
    public ModelAndView getOAuth2Code(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView();
        Object objName = request.getSession().getAttribute("username");
        if (objName != null) {
            mav.setViewName("redirect:/test");
        } else {
            mav.addObject("redirect_uri", applicationHost + "/authorization_code_callback");
            mav.addObject("client_id", clientId);
            mav.addObject("accessTokenUri", accessTokenUri);
            mav.addObject("userAuthorizationUri", userAuthorizationUri);
            mav.addObject("applicationHost", applicationHost);
            mav.addObject("scope", scope);
            mav.addObject("response_type", "code");
            mav.setViewName("lims/oauth2/authorization_code.jsp");
        }
        return mav;
    }

    /************************************************************
     * Description：Oauth2认证step1-获取code
     * @throws IOException
     * @throws HttpException
     *
     * @作者：魏诚
     * @日期：2017-08-30
     ************************************************************/
    @RequestMapping("/lims/authorization_code_callback")
    public ModelAndView authorization_code_callback(@RequestParam("code") String code, String state, HttpServletRequest request) throws HttpException, IOException {
        ModelAndView mav = new ModelAndView();
        System.out.println("code="+code);

        //--2 通过code获取access_token

        //创建一个客户端，类似打开一个浏览器
        HttpClient httpClient = new HttpClient();
        //设置路径，为了通过code获取accessToken
        PostMethod postMethod = new PostMethod(accessTokenUri);
        //设置传输参数
        NameValuePair[] postData = new NameValuePair[6];
        postData[0] = new NameValuePair("code",code);
        postData[1] = new NameValuePair("state",state);
        postData[2] = new NameValuePair("redirect_url",applicationHost+"/authorization_code_callback");
        postData[3] = new NameValuePair("client_id",clientId);
        postData[4] = new NameValuePair("client_secret",secretKey);
        postData[5] = new NameValuePair("grant_type","authorization_code");
        //添加进去
        postMethod.addParameters(postData);
        //发送请求获取状态码
        int statusCode = httpClient.executeMethod(postMethod);
        //获取返回数据
        String jsonStr = postMethod.getResponseBodyAsString();
        //json转换
        JSONObject jb = JSONObject.fromObject(jsonStr);
        //获取access_token
        String access_token = jb.get("access_token").toString();
        //获取用户id
        String user_id = jb.get("user_id").toString();
        System.out.println("access_token="+access_token);

        //--3 通过user_id和access_token获取用户信息
        String url = userProfileUri+"?access_token="+access_token+"&user_id="+user_id;
        GetMethod userInfoMethod = new GetMethod(userProfileUri+"?access_token="+access_token+"&user_id="+user_id);
        httpClient.executeMethod(userInfoMethod);
        String userInfoStr = userInfoMethod.getResponseBodyAsString();
        JSONObject userInfo = JSONObject.fromObject(userInfoStr);
        //获取username
        String username = userInfo.get(this.userKey).toString();
        if(username.length()<=4){
            try{
                Integer.valueOf(username);
                username = "0"+username;
            }catch(Exception e){
                System.out.println("user_no前面加0出错");
            }
        }
        //--4 进行本项目效验

        User user = userDAO.findUserByPrimaryKey(username);
        if(user!=null){
            request.getSession().setAttribute("password",user.getPassword());
        }
		System.out.println("username="+username);
        request.getSession().setAttribute("username",username);
        Calendar time = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String logintime = sdf.format(time.getTime());
        request.getSession().setAttribute("logintime",logintime);
        //使登录
        mav.setViewName("redirect:/xxx");
        return mav;
    }

    /************************************************************
     * Description：Oauth2认证---登出
     * @throws IOException
     * @throws HttpException
     * @throws Exception
     * @作者：缪军
     * @日期：2017-12-03
     ************************************************************/
    @RequestMapping("/lims/oauth2_logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String logouturl = logoutUrl + "?redirect_uri=" + request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        request.getSession().invalidate();
        response.sendRedirect(logouturl);
    }

}