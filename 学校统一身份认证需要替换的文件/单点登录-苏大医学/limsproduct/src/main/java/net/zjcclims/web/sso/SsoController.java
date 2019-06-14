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
            String secret = ""; // 认证加密串
            String id_tag = ""; // 登录用户ID（教师为工号比如050065）
            String pass = this.strKey;//this.strKey; // 握手密钥，密钥填写在此处，安全起见可以把密钥写入数据或者配置文件
            String timestamp = ""; // 时间戳
            String md5str = ""; // 接受参数后的加密串

            secret = request.getParameter("secret"); // 接受加密串
            id_tag = request.getParameter("id_tag"); // 接受用户名
            timestamp = request.getParameter("timestamp"); // 接受时间戳
            //MD5加密
            String str = id_tag + pass + timestamp;
            //加密后的字符串
            System.out.println("str:[" + str + "]3");
            md5str = shareService.createMD5(str);
            // 比对业务系统生成的和OA系统传过来的md5字符串
            if (!md5str.equals(secret)) {// 如不相同，则不做任何处理
                mav.addObject("username", "");
            } else {// 如相同，则跳转到本系统登录页面，做登录认证
                mav.addObject("username", id_tag);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // 本系统默认登录后的跳转页面
        mav.setViewName("../../indexForSso.jsp");
        return mav;
    }

}
