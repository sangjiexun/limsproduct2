package net.gvsun.lims.web.timetable;

/****************************************************************************
 * Descriptions 学生选课模块相关
 * @author 陈乐为 2019年6月5日
 ****************************************************************************/

import net.gvsun.lims.dto.common.PConfigDTO;
import net.zjcclims.domain.SchoolTerm;
import net.zjcclims.service.common.ShareService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller("StudentCourseController")
@SessionAttributes("selected_academy")
@RequestMapping("/lims/timetable/student")
public class StudentCourseController<JsonResult> {
    @Autowired private HttpServletRequest request;
    @Autowired private ShareService shareService;
    /************************************************************
     * @初始化WebDataBinder，这个WebDataBinder用于填充被@InitBinder注释的处理 方法的command和form对象
     *
     ************************************************************/
    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register
        binder.registerCustomEditor(Calendar.class,
                new org.skyway.spring.util.databinding.CustomCalendarEditor());
        binder.registerCustomEditor(byte[].class,
                new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
        binder.registerCustomEditor(boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(false));
        binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
        binder.registerCustomEditor(java.math.BigDecimal.class,
                new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
        binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                Integer.class, true));
        binder.registerCustomEditor(Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
        binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
        binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                Long.class, true));
        binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                Double.class, true));
    }

    /************************************************************
     * Description 学生选课管理--选课页面
     * @param acno
     * @return
     * @author 陈乐为 2019年6月5日
     ************************************************************/
    @RequestMapping("/stuCourseList")
    public ModelAndView stuCourseList(@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        // 获取学期列表
        List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
        mav.addObject("schoolTerms", schoolTerms);
        mav.addObject("zuulServerUrl", pConfigDTO.zuulServerUrl);
        // 当前学期
        mav.addObject("termId", shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId());

        mav.setViewName("lims/timetable/course/stuCourseList.jsp");
        return mav;
    }


}


