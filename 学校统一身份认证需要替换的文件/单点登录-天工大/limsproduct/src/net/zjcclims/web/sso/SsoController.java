/**
 *
 */
package net.zjcclims.web.sso;

import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 贺子龙
 */
@RequestMapping("/sso")
@Controller("SsoController")
public class SsoController<JsonResult> {

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) {
        binder.registerCustomEditor(java.util.Calendar.class,
                new org.skyway.spring.util.databinding.CustomCalendarEditor());
        binder.registerCustomEditor(
                byte[].class,
                new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
        binder.registerCustomEditor(boolean.class,
                new org.skyway.spring.util.databinding.EnhancedBooleanEditor(
                        false));
        binder.registerCustomEditor(Boolean.class,
                new org.skyway.spring.util.databinding.EnhancedBooleanEditor(
                        true));
        binder.registerCustomEditor(java.math.BigDecimal.class,
                new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                        java.math.BigDecimal.class, true));
        binder.registerCustomEditor(Integer.class,
                new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                        Integer.class, true));
        binder.registerCustomEditor(java.util.Date.class,
                new org.skyway.spring.util.databinding.CustomDateEditor());
        binder.registerCustomEditor(String.class,
                new org.skyway.spring.util.databinding.StringEditor());
        binder.registerCustomEditor(Long.class,
                new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                        Long.class, true));
        binder.registerCustomEditor(Double.class,
                new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                        Double.class, true));
    }


    @Value("${strKey}")
    private String strKey;
    @Autowired
    private ShareService shareService;

    @RequestMapping("/login")
    public ModelAndView crpssoDemo(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        try {
            String verify = ""; // 认证加密串
            String userName = ""; // 验证用户名
            String strKey = "eyousecret";//this.strKey; // 握手密钥，密钥填写在此处，安全起见可以把密钥写入数据或者配置文件
            String strSysDatatime = ""; // 时间戳
            String jsName = ""; // 登录角色
            String md5str = ""; // 接受参数后的加密串

            verify = request.getParameter("verify"); // 接受加密串
            userName = request.getParameter("userName"); // 接受用户名
            strSysDatatime = request.getParameter("strSysDatetime"); // 接受时间戳
            jsName = request.getParameter("jsName"); // 接受角色名称
            System.out.println("verify:[" + verify + "]2");
            System.out.println("userName:[" + userName + "]");
            System.out.println("strSysDatatime:[" + strSysDatatime + "]2");
            System.out.println("jsName:[" + jsName + "]3");
            System.out.println("strKey:[" + strKey + "]3");
            //MD5加密
            String str = userName + strKey + strSysDatatime + jsName;
            //加密后的字符串
            System.out.println("str:[" + str + "]3");
            md5str = shareService.createMD5(str).toUpperCase();
            // 比对业务系统生成的和OA系统传过来的md5字符串
            if (!verify.equals(md5str)) {// 如不相同，则不做任何处理
                System.out.println("[verify]=[" + verify + "]");
                System.out.println("[md5str]=[" + md5str + "]4");
                mav.addObject("username", "");
            } else {// 如相同，则跳转到本系统登录页面，做登录认证
                System.out.println("[true]5");
                mav.addObject("username", userName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // 本系统默认登录后的跳转页面
        mav.setViewName("../../indexForSso.jsp");
        return mav;
    }

}
