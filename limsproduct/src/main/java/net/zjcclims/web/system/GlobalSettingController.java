package net.zjcclims.web.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("GlobalSettingController")
@RequestMapping("/global")
public class GlobalSettingController<JsonResult> {

    @RequestMapping("/setting")
    public ModelAndView setting() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("/system/global/setting.jsp");
        return mav;
    }

}