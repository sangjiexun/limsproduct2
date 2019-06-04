package net.zjcclims.web.schedule;

import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.SchoolTerm;
import net.zjcclims.domain.SchoolWeek;
import net.zjcclims.domain.User;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.schedule.ScheduleService;
import net.zjcclims.service.system.SchoolWeekService;
import net.zjcclims.vo.ScheduleVO;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;

@Controller("ScheduleController")
@RequestMapping("/schedule")
public class ScheduleController<JsonResult> {
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
    /**************************************************************************************/
    @Autowired private ShareService shareService;
    @Autowired private ScheduleService scheduleService;
    @Autowired private LabRoomService labRoomService;
    @Autowired private PConfig pConfig;
    @Autowired private SchoolWeekService schoolWeekService;
    /**************************************************************************************/

    /**
     * Description 教师当前学期的个人课表--主页面
     * @return
     * @author 陈乐为
     */
    @RequestMapping("/myScheduleList")
    public ModelAndView myScheduleList(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        User user = shareService.getUserDetail();
        mav.addObject("user", user);
        // 当前学期
        SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
        mav.addObject("term", schoolTerm.getTermName());
        // 列表备用
//        List<ScheduleVO> list = scheduleService.findScheduleByQuery();
//        mav.addObject("lists", list);
        mav.addObject("zuulServerUrl", pConfig.auditServerUrl);
        // 周次集合
        List<SchoolWeek> schoolWeeks = schoolWeekService.findallschoolweekbytermId(schoolTerm.getId());
        mav.addObject("schoolWeeks", schoolWeeks);
        int week = 0;
        if(request.getParameter("week") != null && !request.getParameter("week").equals("")) {
            week = Integer.valueOf(request.getParameter("week"));
        }
        mav.addObject("week", week);

//        mav.setViewName("/schedule/myScheduleList2.jsp");
        mav.setViewName("/schedule/myScheduleList.jsp");
        return mav;
    }

    /**
     * Description 教师当前学期的个人课表--数据
     * @return
     * @author 陈乐为
     */
    @RequestMapping("/ajaxScheduleList")
    @ResponseBody
    public List<ScheduleVO> ajaxScheduleList(HttpServletRequest request) {
        List<ScheduleVO> list = scheduleService.findScheduleByQuery(request);

        return list;
    }
    /**
     * Description 综合课表
     * @return
     * @author 刘博越
     */
    @RequestMapping("/comprehensiveTimetable")
    @ResponseBody
    public ModelAndView comprehensiveTimetable(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        User user = shareService.getUserDetail();
        mav.addObject("user", user);
        // 当前学期
        SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
        mav.addObject("term", schoolTerm.getTermName());
        mav.addObject("zuulServerUrl", pConfig.auditServerUrl);
        // 周次集合
        List<SchoolWeek> schoolWeeks = schoolWeekService.findallschoolweekbytermId(schoolTerm.getId());
        mav.addObject("schoolWeeks", schoolWeeks);
        //学院下所有实验室
        List<LabRoom> labRooms = labRoomService.findLabRoomBySchoolAcademy(user.getSchoolAcademy().getAcademyNumber());
        mav.addObject("labRoomList",labRooms);
        mav.setViewName("/schedule/comprehensiveTimetable.jsp");
        return mav;
    }





}