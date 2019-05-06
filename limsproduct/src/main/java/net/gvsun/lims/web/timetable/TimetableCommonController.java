package net.gvsun.lims.web.timetable;

import net.zjcclims.dao.*;
import net.zjcclims.domain.CStaticValue;
import net.zjcclims.domain.SchoolCourseDetail;
import net.zjcclims.domain.SchoolTerm;
import net.zjcclims.domain.TimetableAppointment;
import net.zjcclims.service.common.CStaticValueService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.operation.OperationService;
import net.zjcclims.service.timetable.OuterApplicationService;
import net.zjcclims.service.timetable.SchoolCourseDetailService;
import net.zjcclims.service.timetable.SchoolCourseService;
import net.zjcclims.service.timetable.TimetableAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/****************************************************************************
 * Descriptions：教务排课管理模块
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ****************************************************************************/
@Controller("timetableCommonController")
@SessionAttributes("selected_academy")
@RequestMapping("/lims/timetable/common")
public class TimetableCommonController<JsonResult> {
    @Autowired
    private OuterApplicationService outerApplicationService;
    @Autowired
    private SchoolCourseDetailService schoolCourseDetailService;
    @Autowired
    private TimetableAppointmentDAO timetableAppointmentDAO;
    @Autowired
    private TimetableAppointmentService timetableAppointmentService;
    @Autowired
    private SchoolCourseDAO schoolCourseDAO;
    @Autowired
    private SchoolCourseService schoolCourseService;
    @Autowired
    private ShareService shareService;
    @Autowired
    private SchoolTermDAO schoolTermDAO;
    @Autowired
    private CStaticValueService cStaticValueService;
    @Autowired
    private OperationService operationService;
    @Autowired
    private SoftwareDAO softwareDAO;
    @Autowired
    private TimetableSelfCourseDAO timetableSelfCourseDAO;
    @Autowired
    private SoftwareRoomRelatedDAO 	softwareRoomRelatedDAO;
    @Autowired
    private SoftwareItemRelatedDAO  softwareItemRelatedDAO;
    @Autowired
    private OperationItemDAO  operationItemDAO;
    @Autowired
    private LabCenterDAO labCenterDAO;
    @Autowired
    private SchoolCourseDetailDAO schoolCourseDetailDAO;

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
     * Descriptions：教务排课管理-显示教务排课的排课信息
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/viewTimetableInfo")
    public ModelAndView apiViewTimetableInfo(int style, String courseNo) {
        ModelAndView mav = new ModelAndView();
        // 获取可选的教师列表列表
        mav.addObject("courseNo", courseNo);
        mav.addObject("style", style);
        mav.setViewName("lims/timetable/common/viewTimetableInfo.jsp");
        return mav;
    }
 }
