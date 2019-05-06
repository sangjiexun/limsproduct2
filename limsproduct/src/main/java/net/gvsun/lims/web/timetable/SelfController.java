package net.gvsun.lims.web.timetable;

import net.gvsun.lims.service.timetable.TimetableSelfCourseService;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.CStaticValueService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.timetable.OuterApplicationService;
import net.zjcclims.service.virtual.VirtualService;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/****************************************************************************
 * Descriptions：自主排课管理模块
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ****************************************************************************/
@Controller("selfController")
@SessionAttributes("selected_academy")
@RequestMapping("/lims/timetable/self")
public class SelfController<JsonResult> {
    @Autowired
    private OuterApplicationService outerApplicationService;
    @Autowired
    private PConfig pConfig;
    @Autowired
    private ShareService shareService;
    @Autowired
    private CStaticValueService cStaticValueService;
    @Autowired
    private SchoolCourseDAO schoolCourseDAO;
    @Autowired
    private SchoolAcademyDAO schoolAcademyDAO;
    @Autowired
    private TimetableSelfCourseService timetableSelfCourseService;
    @Autowired
    private TimetableSelfCourseDAO timetableSelfCourseDAO;
    @Autowired
    private TimetableAppointmentSameNumberDAO timetableAppointmentSameNumberDAO;
    @Autowired
    private SchoolTermDAO schoolTermDAO;
    @Autowired
    private VirtualService virtualService;

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
        binder.registerCustomEditor(Integer.class,
                new org.skyway.spring.util.databinding.NaNHandlingNumberEditor( Integer.class, true));
        binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                Double.class, true));
    }

    /************************************************************
     * Descriptions：自主排课管理-显示自主排课排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/selfCourseList")
    public ModelAndView selfCourseList(HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 当前学期
        mav.addObject("termId", shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId());
        // 获取学期列表
        List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
        mav.addObject("schoolTerms", schoolTerms);
        mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
        // 获取实验室排课的通用配置对象；
        CStaticValue cStaticValue = cStaticValueService.getCStaticValueByTimetableLabDevice(acno);
        mav.addObject("cStaticValue", cStaticValue);
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        // 操作权限配置
        mav.addObject("selfBatch", pConfig.selfBatch);
        mav.addObject("selfNoBatch", pConfig.selfNoBatch);
        // 获取可选的教师列表列表
        mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap(acno));
        // 是否审核
        mav.addObject("auditOrNot", shareService.getAuditOrNot("SelfTimetableAuditOrNot"));
        // 审核参数
        mav.addObject("businessType", "SelfTimetableAudit");
        mav.setViewName("lims/timetable/self/selfCourseList.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：自主排课管理-显示自主排课排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/selfCourseAdjustList")
    public ModelAndView selfCourseAdjustList(HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 当前学期
        mav.addObject("termId", shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId());
        // 获取学期列表
        List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
        mav.addObject("schoolTerms", schoolTerms);
        mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
        // 获取实验室排课的通用配置对象；
        CStaticValue cStaticValue = cStaticValueService.getCStaticValueByTimetableLabDevice(acno);
        mav.addObject("cStaticValue", cStaticValue);
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        // 操作权限配置
        mav.addObject("selfBatch", pConfig.selfBatch);
        mav.addObject("selfNoBatch", pConfig.selfNoBatch);
        // 获取可选的教师列表列表
        mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap(acno));
        mav.setViewName("lims/timetable/self/selfCourseAdjustList.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：自主排课管理-自主排课二次不分批排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/newSelfReTimetableCourse")
    public ModelAndView newSelfReTimetableCourse(HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        String academyNumber="";
        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
        if (!acno.equals("-1")) {
            //获取选择的实验中心
            academyNumber = schoolAcademyDAO.findSchoolAcademyByPrimaryKey(acno).getAcademyNumber();
        }
        mav.addObject("groupId",request.getParameter("groupId"));
        mav.addObject("timetableStyle",request.getParameter("timetableStyle"));
        mav.addObject("selfId",request.getParameter("selfId"));
        //获取课程编号
        SchoolCourse schoolCourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(request.getParameter("selfId"));
        String courseNumber = "";
        if(Objects.nonNull(schoolCourse)&&Objects.nonNull(schoolCourse.getSchoolCourseInfo())){
            courseNumber = schoolCourse.getSchoolCourseInfo().getCourseNumber();
        }
        mav.addObject("courseNumber",courseNumber);
        mav.addObject("term",request.getParameter("term"));
        mav.addObject("academyNumber",academyNumber);
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        // 虚拟镜像
        mav.addObject("virtual", pConfig.virtual);
        List<VirtualImage> virtualImageList = virtualService.getAllVirtualImage(null, 1, -1);
        mav.addObject("virtualImageList", virtualImageList);
        mav.setViewName("lims/timetable/self/newSelfReTimetableCourse.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：自主排课管理-自主排课二次分批排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/newSelfReGroupTimetableCourse")
    public ModelAndView newSelfReGroupTimetableCourse(HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        String academyNumber="";
        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
        if (!acno.equals("-1")) {
            //获取选择的实验中心
            academyNumber = schoolAcademyDAO.findSchoolAcademyByPrimaryKey(acno).getAcademyNumber();
        }
        TimetableAppointmentSameNumber timetableAppointmentSameNumber = null;
        if(Objects.nonNull(request.getParameter("sameNumberId"))&&Integer.parseInt(request.getParameter("sameNumberId"))!=-1){
            int timetableAppointmentSameNumberId = Integer.parseInt(request.getParameter("sameNumberId"));
            timetableAppointmentSameNumber =timetableAppointmentSameNumberDAO.findTimetableAppointmentSameNumberById(timetableAppointmentSameNumberId);
        }
        mav.addObject("timetableAppointmentSameNumber",timetableAppointmentSameNumber);
        mav.addObject("groupId",request.getParameter("groupId"));
        mav.addObject("timetableStyle",request.getParameter("timetableStyle"));
        mav.addObject("selfId",request.getParameter("selfId"));
        //获取课程编号
        SchoolCourse schoolCourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(request.getParameter("selfId"));
        String courseNumber = "";
        if(Objects.nonNull(schoolCourse)&&Objects.nonNull(schoolCourse.getSchoolCourseInfo())){
            courseNumber = schoolCourse.getSchoolCourseInfo().getCourseNumber();
        }
        mav.addObject("courseNumber",courseNumber);
        mav.addObject("term",request.getParameter("term"));
        mav.addObject("academyNumber",academyNumber);
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        // 虚拟镜像
        mav.addObject("virtual", pConfig.virtual);
        List<VirtualImage> virtualImageList = virtualService.getAllVirtualImage(null, 1, -1);
        mav.addObject("virtualImageList", virtualImageList);
        mav.setViewName("lims/timetable/self/newSelfReGroupTimetableCourse.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：自主排课管理-自主排课二次分批排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/newSelfReGroupCourse")
    public ModelAndView newSelfReGroupCourse(HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 获取学期列表
        List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
        mav.addObject("schoolTerms", schoolTerms);
        mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
        // 获取实验室排课的通用配置对象；
        CStaticValue cStaticValue = cStaticValueService.getCStaticValueByTimetableLabDevice(acno);
        mav.addObject("selfId",request.getParameter("selfId"));
        mav.addObject("term",request.getParameter("term"));
        mav.addObject("cStaticValue", cStaticValue);
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        // 获取可选的教师列表列表
        mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap(acno));
        // 虚拟镜像
        List<VirtualImage> virtualImageList = virtualService.getAllVirtualImage(null, 1, -1);
        mav.addObject("virtualImageList", virtualImageList);
        mav.setViewName("lims/timetable/self/newSelfReGroupCourse.jsp");
        return mav;
    }

    /**************************************************************************************
     * Descriptions： 自主排课管理-新建自主排课的选课组

     * @作者：魏诚
     * @日期：2018-10-26
     *************************************************************************************/
    @RequestMapping("/newSelfCourse")
    public ModelAndView newSelfCourse(@RequestParam Integer id,Integer term,
                                                  @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 登陆用户
        mav.addObject("user", shareService.getUserDetail());

        // 获取最大的id
        int maxId = timetableSelfCourseService.getTimetableSelfCourseTotalRecords();
        SimpleDateFormat sdf = new SimpleDateFormat("mmss");
        // 当前时间
        Calendar currTime = Calendar.getInstance();
        String dateStr = sdf.format(currTime.getTime());
        mav.addObject("maxId", maxId+"-"+dateStr);
        // id为-1时，新增，否则为修改
        mav.addObject("flagId", id);
        mav.addObject("term", term);
        //
        TimetableSelfCourse timetableSelfCourse = new TimetableSelfCourse();
        if (id!=-1) {
            timetableSelfCourse = timetableSelfCourseDAO.findTimetableSelfCourseById(id);
        }
        mav.addObject("timetableSelfCourse", timetableSelfCourse);
        // 获取学期列表
        List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
        mav.addObject("schoolTerms", schoolTerms);
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        // 年级列表
        mav.addObject("grade",schoolTermDAO.executeQuery("select st from SchoolTerm st group by st.yearCode"));
        mav.setViewName("lims/timetable/self/newSelfCourse.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：自主排课管理-显示自主排课的学生名单页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/timetableCourseStudentList")
    public ModelAndView timetablecourseStudentList(int term, int selfId,@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 获取可选的教师列表列表
        mav.addObject("selfId", selfId);
        mav.addObject("termId", term);
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        mav.setViewName("lims/timetable/self/timetableCourseStudentList.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：教务排课管理-教务二次分批排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/updateSelfReTimetableCourse")
    public ModelAndView updateSelfReTimetableCourse(HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        String academyNumber="";
        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
        if (!acno.equals("-1")) {
            //获取选择的实验中心
            academyNumber = schoolAcademyDAO.findSchoolAcademyByPrimaryKey(acno).getAcademyNumber();
        }
        TimetableAppointmentSameNumber timetableAppointmentSameNumber = null;
        if(Objects.nonNull(request.getParameter("sameNumberId"))&&Integer.parseInt(request.getParameter("sameNumberId"))!=-1){
            int timetableAppointmentSameNumberId = Integer.parseInt(request.getParameter("sameNumberId"));
            timetableAppointmentSameNumber =timetableAppointmentSameNumberDAO.findTimetableAppointmentSameNumberById(timetableAppointmentSameNumberId);
        }
        mav.addObject("timetableAppointmentSameNumber",timetableAppointmentSameNumber);
        mav.addObject("timetableStyle",request.getParameter("timetableStyle"));
        mav.addObject("selfId",request.getParameter("selfId"));
        //获取课程编号
        SchoolCourse schoolCourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(request.getParameter("courseNo"));
        String courseNumber = "";
        if(Objects.nonNull(schoolCourse)&&Objects.nonNull(schoolCourse.getSchoolCourseInfo())){
            courseNumber = schoolCourse.getSchoolCourseInfo().getCourseNumber();
        }
        mav.addObject("schoolCourse",schoolCourse);
        mav.addObject("courseNumber",courseNumber);
        mav.addObject("term",request.getParameter("term"));
        mav.addObject("academyNumber",academyNumber);
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        mav.setViewName("lims/timetable/self/updateSelfReTimetableCourse.jsp");
        return mav;
    }
}
