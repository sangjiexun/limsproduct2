package net.zjcclims.web.personal;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.zjcclims.JsonDateValueProcessor;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabReservationService;
import net.zjcclims.service.message.MessageService;
import net.zjcclims.service.personal.PersonalCenterService;
import net.zjcclims.service.system.SchoolAcademyService;
import net.zjcclims.service.system.SchoolWeekService;
import net.zjcclims.service.system.SystemService;
import net.zjcclims.service.timetable.OuterApplicationService;
import net.zjcclims.service.timetable.SchoolCourseDetailService;
import net.zjcclims.service.timetable.TimetableAppointmentService;
import net.zjcclims.service.virtual.VirtualService;
import net.zjcclims.web.common.PConfig;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
@Controller("TimetableController")
@SessionAttributes("selected_academy")
public class TimetableController {

    @Autowired
    PersonalCenterService personalCenterService;
    @Autowired
    TimetableAppointmentService timetableAppointmentService;
    @Autowired
    ShareService shareService;
    @Autowired
    SchoolCourseDetailService schoolCourseDetailService;
    @Autowired
    TimetableLabRelatedDAO timetableLabRelatedDAO;
    @Autowired
    TimetableGroupStudentsDAO timetableGroupStudentsDAO;
    @Autowired
    SchoolCourseStudentDAO schoolCourseStudentDAO;
    @Autowired
    TimetableCourseStudentDAO timetableCourseStudentDAO;
    @Autowired
    private OuterApplicationService outerApplicationService;
    @Autowired
    LabReservationService labReservationService;
    @Autowired
    LabReservationDAO labReservationDAO;
    @Autowired
    SchoolTermDAO schoolTermDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    TimetableTeacherRelatedDAO timetableTeacherRelatedDAO;
    @Autowired
    TimetableAppointmentDAO timetableAppointmentDAO;
    @Autowired
    MessageDAO messageDAO;
    @Autowired
    SchoolAcademyDAO schoolAcademyDAO;
    @Autowired
    MessageService messageService;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    SchoolCourseDetailDAO schoolCourseDetailDAO;
    @Autowired
    SchoolWeekDAO schoolWeekDAO;
    @Autowired
    SchoolAcademyService schoolAcademyService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private SchoolWeekService schoolWeekService;
    @Autowired
    private LabRoomDAO labRoomDAO;
    @Autowired
    PConfig pConfig;
    @Autowired
    private VirtualService virtualService;
    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register // static // property // editors.
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

    /**************************************************************************
     * @Description 课程列表式大课表
     * @author 廖文辉
     * @date 2018-10-30
     ***************************************************************************/
    @RequestMapping("/CourseListTimetable")
    @Transactional
    public ModelAndView CourseListTimetable(HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 获取当前用户
        User user = shareService.getUser();
        mav.addObject("role",user.getUserRole());
        String username = user.getUsername();
        String cname = user.getCname();
        mav.addObject("cname", cname);
        Map weeks=shareService.getWeekMap();
        mav.addObject("weeks",weeks);
        // 获取当前周次
        int week = shareService.findNewWeek();
        mav.addObject("week", week);
        // 获取当前学期
        SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
        mav.addObject("term", schoolTerm.getTermName());
        String date=request.getParameter("class_date");
        String weekss=request.getParameter("weeks");
        String courseName=request.getParameter("courseName");
        List<Object[]>timetableAppointment=timetableAppointmentService.getTimetable(date,courseName,weekss);
        // 排课
        mav.addObject("timetableAppointment", timetableAppointment);
        mav.setViewName("/personal/message/courseListTimetable.jsp");

        return mav;
    }
    /**************************************************************************
     * @Description 学院本日课表
     * @author 廖文辉
     * @date 2018-11-1
     ***************************************************************************/
    @RequestMapping("/CurrentDayTimetable")
    @Transactional
    public ModelAndView CurrentDayTimetable(@ModelAttribute("selected_academy") String acno,HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        //获取当前系统时间
        Calendar date = Calendar.getInstance();
        mav.addObject("date",date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 设置格式
        String Currentdate = sdf.format(date.getTime());
        List<SchoolAcademy> schoolAcademies=shareService.getSchoolAcademy();
        mav.addObject("schoolAcademies",schoolAcademies);
        if(request.getParameter("academyName")!=null){
            acno=request.getParameter("academyName");
        }
        mav.addObject("acno",acno);
        int schoolWeekday=shareService.getBelongsSchoolWeekday(date);
        mav.addObject("schoolWeekday",schoolWeekday);
        SchoolAcademy schoolAcademy=schoolAcademyDAO.findSchoolAcademyByPrimaryKey(acno);
        mav.addObject("schoolAcademy",schoolAcademy);
        List<Object[]>timetableAppointment=timetableAppointmentService.getTimetableByDateAndSchoolAcaemy(Currentdate,acno);
        mav.addObject("timetableAppointment",timetableAppointment);
        mav.setViewName("/personal/message/CurrentDayTimetable.jsp");
        return mav;
    }
    /**************************************************************************
     * @Description 学院本周课表
     * @author 廖文辉
     * @date 2018-11-1
     ***************************************************************************/
    @RequestMapping("/CurrentWeekTimetable")
    @Transactional
    public ModelAndView CurrentWeekTimetable(@ModelAttribute("selected_academy") String acno,HttpServletRequest request){
        ModelAndView mav= new ModelAndView();
        //获取当前系统时间
        Calendar date=Calendar.getInstance();
        //获取当前周
        int SchoolWeek=shareService.getBelongsSchoolWeek(date);
        if(request.getParameter("weeks")!=null&&!"".equals(request.getParameter("weeks"))){
            SchoolWeek=Integer.parseInt(request.getParameter("weeks"));
        }
        mav.addObject("SchoolWeek",SchoolWeek);
        List<SchoolWeek>schoolWeeks=shareService.getDate(SchoolWeek);
        //获取本周第一天和最后一天
        Calendar FirstDay=schoolWeeks.get(0).getDate();
        Calendar LastDay=schoolWeeks.get(6).getDate();
        mav.addObject("FirstDay",FirstDay);
        mav.addObject("LastDay",LastDay);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 设置格式
        String FirstDate = sdf.format(FirstDay.getTime());
        String LastDate = sdf.format(LastDay.getTime());
        //周次下拉框
        Map weeks=shareService.getWeekMap();
        mav.addObject("weeks",weeks);
        //学院下拉框
        List<SchoolAcademy> schoolAcademies=shareService.getSchoolAcademy();
        mav.addObject("schoolAcademies",schoolAcademies);
        if(request.getParameter("academyName")!=null){
            acno=request.getParameter("academyName");
        }
        mav.addObject("acno",acno);
        SchoolAcademy schoolAcademy=schoolAcademyDAO.findSchoolAcademyByPrimaryKey(acno);
        mav.addObject("schoolAcademy",schoolAcademy);
        List<Object[]>timetableAppointment=timetableAppointmentService.getCurrentWeekTimetable(FirstDate,LastDate,acno,String.valueOf(SchoolWeek));
        mav.addObject("timetableAppointment",timetableAppointment);
        mav.setViewName("/personal/message/CurrentWeekTimetable.jsp");
        return mav;
    }
    /**************************************************************************
     * @Description 学院本学期课表
     * @author 廖文辉
     * @date 2018-11-2
     ***************************************************************************/
    @RequestMapping("/CurrentTermTimetable")
    @Transactional
    public ModelAndView CurrentTermTimetable(@ModelAttribute("selected_academy") String acno,HttpServletRequest request){
        ModelAndView mav= new ModelAndView();
        //获取当前学期
        SchoolTerm schoolTerm=shareService.getBelongsSchoolTerm(Calendar.getInstance());
        mav.addObject("schoolTerm",schoolTerm);
        Calendar FirstDay=schoolTerm.getTermStart();
        Calendar LastDay=schoolTerm.getTermEnd();
        mav.addObject("FirstDay",FirstDay);
        mav.addObject("LastDay",LastDay);
        //学院下拉框
        List<SchoolAcademy> schoolAcademies=shareService.getSchoolAcademy();
        mav.addObject("schoolAcademies",schoolAcademies);
        if(request.getParameter("academyName")!=null){
            acno=request.getParameter("academyName");
        }
        mav.addObject("acno",acno);
        SchoolAcademy schoolAcademy=schoolAcademyDAO.findSchoolAcademyByPrimaryKey(acno);
        mav.addObject("schoolAcademy",schoolAcademy);
        mav.addObject("search", request.getParameter("search"));

        List<Object[]> timetableAppointment = timetableAppointmentService.getCurrentTermTimetable(schoolTerm,acno, request.getParameter("search"));
        mav.addObject("timetableAppointment",timetableAppointment);
        mav.setViewName("/personal/message/CurrentTermTimetable.jsp");
        return mav;
    }
    /**************************************************************************
     * @Description 学生本周课表
     * @author 廖文辉
     * @date 2018-11-2
     ***************************************************************************/
    @RequestMapping("/StudentCurrWeekTimetable")
    @Transactional
    public ModelAndView StudentCurrWeekTimetable(@ModelAttribute("selected_academy") String acno,HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        User user=shareService.getUser();

        Calendar date = Calendar.getInstance();
        //获取当前周
        int schoolWeek = shareService.getBelongsSchoolWeek(date);
        if (request.getParameter("weeks") != null) {
            schoolWeek = Integer.parseInt(request.getParameter("weeks"));
        }

        //查询虚拟镜像
        List<Object[]> todayVirtualImageList = virtualService.getTodayVirtualImage();
        mav.addObject("courseSchedules", todayVirtualImageList);

        //周次下拉框
        Map weeks=shareService.getWeekMap();
        mav.addObject("weeks",weeks);
        //获取当前学期
        int termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
        mav.addObject("user", user);
        mav.addObject("termId",termId);
        mav.addObject("schoolWeek", schoolWeek);
        mav.addObject("systemtime", systemService.getAllTimebyjieci());
        mav.addObject("schoolweeek", schoolWeekService.getMapDate());
        List<Object[]> timetableAppointment = timetableAppointmentService.getStudentCurrWeekTimetable(String.valueOf(schoolWeek));
        mav.addObject("timetableAppointment", timetableAppointment);
        mav.setViewName("/personal/message/StudentCurrWeekTimetable.jsp");
        return mav;
    }

    /**************************************************************************
     * @Description 实验室课表和预约结果
     * @author 张德冰
     * @date 2018-11-21
     ***************************************************************************/
    @RequestMapping("/labCurrDayTimetableAndReservation")
    @Transactional
    public ModelAndView labCurrDayTimetableAndReservation(@ModelAttribute("selected_academy") String acno,HttpServletRequest request,Integer tabpage, Integer respage) {
        ModelAndView mav = new ModelAndView();
        //获取当前时间
        Calendar date = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 设置格式
        String time =request.getParameter("time");
        mav.addObject("time", time);
        if(time != null && !"".equals(time)){
            Date datetime = null;
            try {
                datetime = sdf.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            date.setTime(datetime);
        }
        mav.addObject("date",date);
        //获取当前星期
        int schoolWeekday=shareService.getBelongsSchoolWeekday(date);
        mav.addObject("schoolWeekday",schoolWeekday);
        //下拉框实验室
        mav.addObject("labRooms", labRoomDAO.findAllLabRooms());

        String labRooms =request.getParameter("labRoom");
        if(labRooms!=null&&!"".equals(labRooms)){
            LabRoom labRoom=labRoomDAO.findLabRoomByPrimaryKey(Integer.parseInt(labRooms));
            mav.addObject("labRoom", labRoom);
        }

        Integer pagesize=10;
        //本日课表数据
        int tabtotalRecords = timetableAppointmentService.getLabCurrDayTimetable(request, 0, 0).size();
        mav.addObject("tabpageModel", shareService.getPage(tabpage, pagesize, tabtotalRecords));
        List<Object[]> labTimetable = timetableAppointmentService.getLabCurrDayTimetable(request, tabpage, pagesize);
        mav.addObject("labTimetable",labTimetable);
        //本日预约结果
        int restotalRecords = timetableAppointmentService.getLabCurrDayReservation(request, 0, 0).size();
        mav.addObject("respageModel", shareService.getPage(respage, pagesize, restotalRecords));
        List<Object[]> labReservation = timetableAppointmentService.getLabCurrDayReservation(request, respage, pagesize);
        mav.addObject("labReservation",labReservation);
        mav.setViewName("/personal/message/labCurrDayTimetableAndReservation.jsp");
        return mav;
    }
}
