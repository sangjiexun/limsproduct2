/****************************************************************************
 * 命名规范：
 * 本控制层调整必须是按照如下的路径调整：@RequestMapping("/device/system/sample")，全部小写。
 * 列表信息listxxxx，獲取信息：getxxxx 编辑信息：editxxxx 删除信息：detelexxxx 新增信息 newxxxx
 * 导出信息exportxxxx 保存信息：savexxxx 
 ****************************************************************************/

package net.zjcclims.web.system;

import net.sf.json.JSONObject;
import net.zjcclims.dao.UserDAO;
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
import java.util.Map;

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
    public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register static property editors.
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


    /**
     * 获取token的url
     * */
    @Value("${access-token-uri}")
    private String accessTokenUri;

    /**
     * 获取code的url
     * */
    @Value("${user-authorization-uri}")
    private String userAuthorizationUri;

    @Value("${application-host}")
    private String applicationHost;

    /**
     * 获取用户信息的url
     * */
    @Value("${user-info-uri}")
    private String userInfoUri;

    @Value("${client_id}")
    private String clientId;

    @Value("${secret_key}")
    private String secretKey;

    @Value("${scope}")
    private String scope;

    /**
     * 退出地址
     */
    @Value("${logout-url}")
    private String logoutUrl;

    /**
     *回调地址
     */
    @Value("${outUrl}")
    private String outUrl;

    /**
     *退出地址的参数
     */
    @Value("${outKey}")
    private String outKey;

    /**
     * 获取用户username的key
     */
    @Value("${user-key}")
    private String userKey;
//	@Autowired
//    private TokenStore tokenStore;

    /************************************************************
     * Description：Oauth2认证step1-获取code
     * @throws Exception
     * @作者：魏诚
     * @日期：2017-08-30
     ************************************************************/
    @RequestMapping(value = "/lims/authorization_code", method = RequestMethod.POST)
    public ModelAndView getOAuth2Code(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        Object objName = request.getSession().getAttribute("username");
        if (objName != null) {
            mav.setViewName("redirect:/test");
        } else {
            mav.addObject("userAuthorizationUri", userAuthorizationUri);
            mav.addObject("redirect_uri", applicationHost + "/authorization_code_callback");
            mav.addObject("client_id", clientId);
            mav.addObject("response_type", "code");
            mav.addObject("state", "xvvvf");
            // 公司oauth2不能传scope
            // mav.addObject("scope",scope);
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
        //创建一个客户端，类似打开一个浏览器
        HttpClient httpclient = new HttpClient();
        PostMethod postMethod = new PostMethod(accessTokenUri);
        //传参
        NameValuePair[] postData = new NameValuePair[6];
        postData[0] = new NameValuePair("code", code);
        System.out.println("code=" + code);
        postData[1] = new NameValuePair("state", state);
        postData[2] = new NameValuePair("redirect_uri", applicationHost + "/authorization_code_callback");
        postData[3] = new NameValuePair("client_id", clientId);
        postData[4] = new NameValuePair("client_secret", secretKey);
        postData[5] = new NameValuePair("grant_type", "authorization_code");
        postMethod.addParameters(postData);
        httpclient.executeMethod(postMethod);
        //获取json
        String jsonStr = postMethod.getResponseBodyAsString();
        JSONObject jb = JSONObject.fromObject(jsonStr);
        Map<String, String> map = (Map<String, String>) jb;
        String access_token = map.get("access_token");
        // 获取token
        System.out.println("token=" + access_token);
        // 通过token获取用户信息
        GetMethod userMethod = new GetMethod(userInfoUri + "?access_token=" + access_token);
        httpclient.executeMethod(userMethod);
        //获取json
        String userStr = userMethod.getResponseBodyAsString();
        JSONObject user = JSONObject.fromObject(userStr);
        System.out.println(user);
        if (userKey.contains("$") || userKey == null || userKey.equals("")) {
            this.userKey = "name";
        }
        String username = user.get(this.userKey).toString();
        System.out.println("username=" + username);
        System.out.println(request.getSession().isNew());
        request.getSession().setAttribute("username", username);
        Calendar time = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String logintime = df.format(time.getTime());
        request.getSession().setAttribute("logintime", logintime);
        mav.setViewName("redirect:/j_spring_security_check");
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
    public void logout(HttpServletRequest request, HttpServletResponse response) throws HttpException, IOException, Exception {
        //String logouturl=logoutUrl+"?url="+request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/redirect";
        String logouturl = logoutUrl;
        //参数名
        if (outKey != null && !outKey.contains("$") && !outKey.equals("")) {
            logouturl += "?" + outKey + "=";
        } else {
            logouturl += "?url=";
        }
        //退出返回地址
        if (outUrl != null && !outUrl.contains("$") && !outUrl.equals("")) {
            logouturl += outUrl;
        } else {
            logouturl += request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "cms";
        }
        request.getSession().invalidate();
        response.sendRedirect(logouturl);
    }


}