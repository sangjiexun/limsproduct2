package net.zjcclims.web.cms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.WebDataBinder;

import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;

/**
 *	系统的控制器，包括栏目文章的操作
 */
@RequestMapping("admin")
@Controller("CmsSystemController")
public class CmsSystemController {

	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register static property editors.
		binder.registerCustomEditor(java.util.Calendar.class, new org.skyway.spring.util.databinding.CustomCalendarEditor());
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
	 * 功能:登陆后的默认页面
	 * 参数:
	 */
	@RequestMapping("/logRes")
	public ModelAndView logRes() {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("/cms/turn/indexRes.jsp");
		return mav;
	}
	
	/**
     * 功能:登陆后的默认页面
     * 参数:
     */
    @RequestMapping("/logManage")
    public ModelAndView logManage() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/cms/turn/indexManage.jsp");
        return mav;
    }

    /**
     * 功能:登陆后的默认页面
     * 参数:
     */
    @RequestMapping("/logDevMag")
    public ModelAndView logDevMag() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/cms/turn/indexDevMag.jsp");
        return mav;
    }
    
    @RequestMapping("/logItemShow")
    public ModelAndView logItemShow() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/cms/turn/indexItemShow.jsp");
        return mav;
    }


    /**
     * 功能:登陆后的默认页面
     * 参数:
     */
    @RequestMapping("/logDevRes")
    public ModelAndView logDevRes() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/cms/turn/indexDevRes.jsp");
        return mav;
    }

    /**
     * 功能:登陆后的默认页面
     * 参数:
     */
    @RequestMapping("/logTime")
    public ModelAndView logTime() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/cms/turn/indexTime.jsp");
        return mav;
    }

    /**
     * 功能:登陆后的默认页面
     * 参数:
     */
    @RequestMapping("/logAsset")
    public ModelAndView logAsset() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/cms/turn/indexAsset.jsp");
        return mav;
    }

    /**
     * 功能:登陆后的默认页面
     * 参数:
     */
    @RequestMapping("/logSystem")
    public ModelAndView logSystem() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/cms/turn/indexSystem.jsp");
        return mav;
    }
    /**
     * 功能:登陆后的默认页面
     * 参数:
     */
    @RequestMapping("/logAssetRes")
    public ModelAndView logAssetRes() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/cms/turn/indexAssetRes.jsp");
        return mav;
    }

    /**
     * 功能:登陆后的默认页面
     * 参数:
     */
    @RequestMapping("/logKnowMap")
    public ModelAndView logKnowMap() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/cms/turn/indexKnowMap.jsp");
        return mav;
    }
     
}