package net.gvsun.lims.web.timetable;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.timetable.TimetableParamVO;
import net.gvsun.lims.service.timetable.EduCourseService;
import net.gvsun.lims.service.timetable.TimetableCommonService;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.CStaticValueService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.timetable.OuterApplicationService;
import net.zjcclims.service.virtual.VirtualService;
import net.zjcclims.util.HttpClientUtil;
import net.zjcclims.web.common.PConfig;
import org.hibernate.Session;
import org.hibernate.ejb.HibernateEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/****************************************************************************
 * Descriptions：教务排课管理模块
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ****************************************************************************/
@Controller("timetableCourseController")
@SessionAttributes("selected_academy")
@RequestMapping("/lims/timetable/course")
public class TimetableCourseController<JsonResult> {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private PConfig pConfig;
    @Autowired
    private OuterApplicationService outerApplicationService;
    @Autowired
    private TimetableAppointmentDAO timetableAppointmentDAO;
    @Autowired
    private CStaticValueService cStaticValueService;
    @Autowired
    private SchoolCourseDAO schoolCourseDAO;
    @Autowired
    private SchoolAcademyDAO schoolAcademyDAO;
    @Autowired
    private TimetableAppointmentSameNumberDAO timetableAppointmentSameNumberDAO;
    @Autowired
    private TimetableBatchDAO timetableBatchDAO;
    @Autowired
    private ShareService shareService;
    @Autowired
    private TimetableGroupDAO timetableGroupDAO;
    @Autowired
    private TimetableCommonService timetableCommonService;
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private TimetableSelfCourseDAO timetableSelfCourseDAO;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private AuditSerialNumberDAO auditSerialNumberDAO;
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
        binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                Double.class, true));
    }

    /************************************************************
     * Descriptions：教务排课管理-显示教务排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/eduCourseList")
    public ModelAndView listTimetableTerm(@ModelAttribute("selected_academy") String acno) {
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
        // 获取可选的教师列表列表
        mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap(acno));
        // 操作权限配置
        mav.addObject("eduAjust", pConfig.eduAjust);
        mav.addObject("eduBatch", pConfig.eduBatch);
        mav.addObject("eduDirect", pConfig.eduDirect);
        mav.addObject("eduNoBatch", pConfig.eduNoBatch);
        // 是否审核
        mav.addObject("auditOrNot", shareService.getAuditOrNot("TimetableAuditOrNot"));
        // 审核参数
        mav.addObject("businessType", "TimetableAudit");
        System.out.println("微服务排课请求开始时间"+new Date());
        mav.setViewName("lims/timetable/course/eduCourseList.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：教务排课管理-显示教务排课的调停课主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/eduCourseAdjustList")
    public ModelAndView eduCourseAdustList(HttpServletRequest request, @ModelAttribute("selected_academy") String acno) {
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
        // 获取可选的教师列表列表
        mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap(acno));
        // 操作权限配置
        mav.addObject("eduAjust", pConfig.eduAjust);
        mav.addObject("eduBatch", pConfig.eduBatch);
        mav.addObject("eduDirect", pConfig.eduDirect);
        mav.addObject("eduNoBatch", pConfig.eduNoBatch);
        mav.addObject("authName", request.getSession().getAttribute("selected_role"));

        mav.setViewName("lims/timetable/course/eduCourseAdjustList.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：教务排课管理-教务直接排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/newEduDirectCourse")
    public ModelAndView newEduCourseList(@ModelAttribute("selected_academy") String acno) {

        ModelAndView mav = new ModelAndView();
        String academyNumber="";
        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
        if (!acno.equals("-1")) {
            //获取选择的实验中心
            academyNumber = shareService.findSchoolAcademyByPrimaryKey(acno).getAcademyNumber();
        }
        //获取课程编号
        SchoolCourse schoolCourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(request.getParameter("courseNo"));
        mav.addObject("schoolCourse",schoolCourse);
        mav.addObject("courseNo",request.getParameter("courseNo"));
        mav.addObject("academyNumber",academyNumber);
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        // 虚拟镜像
        mav.addObject("virtual", pConfig.virtual);
        List<VirtualImage> virtualImageList = virtualService.getAllVirtualImage(null, 1, -1);
        mav.addObject("virtualImageList", virtualImageList);
        mav.setViewName("lims/timetable/course/newEduDirectCourse.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：教务排课管理-学生判冲的主显示页面
     * @作者：Hezhaoyi
     * @时间：2019-5-14
     ************************************************************/
    @RequestMapping("/judgeTimetableConflictByStudent")
    public ModelAndView judgeTimetableConflictByStudent(@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();

        mav.addObject("courseNo",request.getParameter("courseNo"));
        mav.addObject("term",request.getParameter("term"));
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);

        mav.setViewName("lims/timetable/course/judgeTimetableConflictByStudent.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：教务排课管理-教务直接排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/newEduAdjustCourse")
    public ModelAndView newEduAdjustCourse(@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        String academyNumber="";
        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
        if (!acno.equals("-1")) {
            //获取选择的实验中心
            academyNumber = shareService.findSchoolAcademyByPrimaryKey(acno).getAcademyNumber();
        }
        mav.addObject("courseNo",request.getParameter("courseNo"));
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
        // 虚拟镜像传参
        mav.addObject("virtual", pConfig.virtual);
        List<VirtualImage> virtualImageList = virtualService.getAllVirtualImage(null, 1, -1);
        mav.addObject("virtualImageList", virtualImageList);
        mav.setViewName("lims/timetable/course/newEduAdjustCourse.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：教务排课管理-显示教务二次排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/eduReCourseList")
    public ModelAndView eduReCourseList(@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 获取学期列表
        List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
        mav.addObject("schoolTerms", schoolTerms);
        mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
        // 获取实验室排课的通用配置对象；
        CStaticValue cStaticValue = cStaticValueService.getCStaticValueByTimetableLabDevice(acno);
        mav.addObject("cStaticValue", cStaticValue);
        // 获取可选的教师列表列表
        mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap(acno));
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        mav.setViewName("lims/timetable/course/eduReCourseList.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：教务排课管理-教务二次不分批排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/newEduReTimetableCourse")
    public ModelAndView newEduReNoGroupCourse(@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        String academyNumber="";
        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
        if (!acno.equals("-1")) {
            //获取选择的实验中心
            academyNumber = schoolAcademyDAO.findSchoolAcademyByPrimaryKey(acno).getAcademyNumber();
        }

        mav.addObject("groupId",request.getParameter("groupId"));
        mav.addObject("timetableStyle",request.getParameter("timetableStyle"));
        mav.addObject("courseNo",request.getParameter("courseNo"));
        //获取课程编号
        SchoolCourse schoolCourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(request.getParameter("courseNo"));
        String courseNumber = "";
        if(Objects.nonNull(schoolCourse)&&Objects.nonNull(schoolCourse.getSchoolCourseInfo())){
            courseNumber = schoolCourse.getSchoolCourseInfo().getCourseNumber();
        }
        mav.addObject("courseNumber",courseNumber);
        mav.addObject("term",request.getParameter("term"));
        mav.addObject("academyNumber",academyNumber);
        mav.addObject("schoolCourse",schoolCourse);
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        // 虚拟镜像
        mav.addObject("virtual", pConfig.virtual);
        List<VirtualImage> virtualImageList = virtualService.getAllVirtualImage(null, 1, -1);
        mav.addObject("virtualImageList", virtualImageList);
        mav.setViewName("lims/timetable/course/newEduReTimetableCourse.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：教务排课管理-教务二次分批排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/newEduReGroupTimetableCourse")
    public ModelAndView newEduReGroupTimetableCourse(@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        String academyNumber="";
        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
        if (!acno.equals("-1")) {
            //获取选择的实验中心
            academyNumber = schoolAcademyDAO.findSchoolAcademyByPrimaryKey(acno).getAcademyNumber();
        }
        mav.addObject("groupId",request.getParameter("groupId"));
        TimetableAppointmentSameNumber timetableAppointmentSameNumber = null;
        if(Objects.nonNull(request.getParameter("sameNumberId"))&&Integer.parseInt(request.getParameter("sameNumberId"))!=-1){
            int timetableAppointmentSameNumberId = Integer.parseInt(request.getParameter("sameNumberId"));
            timetableAppointmentSameNumber =timetableAppointmentSameNumberDAO.findTimetableAppointmentSameNumberById(timetableAppointmentSameNumberId);
        }
        mav.addObject("timetableAppointmentSameNumber",timetableAppointmentSameNumber);
        mav.addObject("timetableStyle",request.getParameter("timetableStyle"));
        mav.addObject("courseNo",request.getParameter("courseNo"));
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
        // 虚拟镜像
        mav.addObject("virtual", pConfig.virtual);
        List<VirtualImage> virtualImageList = virtualService.getAllVirtualImage(null, 1, -1);
        mav.addObject("virtualImageList", virtualImageList);
        mav.setViewName("lims/timetable/course/newEduReGroupTimetableCourse.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：教务排课管理-教务二次分批排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/updateEduReTimetableCourse")
    public ModelAndView updateEduReTimetableCourse(@ModelAttribute("selected_academy") String acno) {
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
        //如果为调课，获取调课周
        //设置周次为调课周
        if(Objects.nonNull(request.getParameter("adjustStatus"))&&"1".equals(request.getParameter("adjustStatus"))){
            int week = Integer.parseInt(request.getParameter("week"));
            timetableAppointmentSameNumber.setStartWeek(week);
            timetableAppointmentSameNumber.setEndWeek(week);
        }
        mav.addObject("timetableAppointmentSameNumber",timetableAppointmentSameNumber);
        mav.addObject("timetableStyle",request.getParameter("timetableStyle"));
        mav.addObject("courseNo",request.getParameter("courseNo"));
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
        mav.addObject("adjustStatus",request.getParameter("adjustStatus"));

        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        mav.setViewName("lims/timetable/course/updateEduReTimetableCourse.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：教务排课管理-教务二次分批排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/adjustEduReTimetableCourse")
    public ModelAndView adjustEduReTimetableCourse(@ModelAttribute("selected_academy") String acno) {
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
        //如果为调课，获取调课周
        //设置周次为调课周
        if(Objects.nonNull(request.getParameter("adjustStatus"))&&"1".equals(request.getParameter("adjustStatus"))){
            int week = Integer.parseInt(request.getParameter("week"));
            //待调课的周
            mav.addObject("adjustWeek",week);
            timetableAppointmentSameNumber.setStartWeek(week);
            timetableAppointmentSameNumber.setEndWeek(week);
        }
        //将数据持久化改为脱离状态
        HibernateEntityManager hEntityManager = (HibernateEntityManager)entityManager;
        Session session = hEntityManager.getSession();
        session.evict(timetableAppointmentSameNumber);

        mav.addObject("timetableAppointmentSameNumber",timetableAppointmentSameNumber);

        mav.addObject("sameNumberId",request.getParameter("sameNumberId"));
        mav.addObject("timetableStyle",request.getParameter("timetableStyle"));
        mav.addObject("courseNo",request.getParameter("courseNo"));
        if(timetableAppointmentSameNumber.getTimetableAppointment() != null && timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourseDetail() != null) {
            mav.addObject("courseDetailNo", timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourseDetail().getCourseDetailNo());
        }
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
        mav.addObject("adjustStatus",request.getParameter("adjustStatus"));

        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        mav.setViewName("lims/timetable/course/adjustEduReTimetableCourse.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：教务排课管理-教务二次分批排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/newEduReGroupCourse")
    public ModelAndView newEduReGroupCourse(@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 获取学期列表
        List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
        //获取课程编号
        SchoolCourse schoolCourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(request.getParameter("courseNo"));
        mav.addObject("schoolTerms", schoolTerms);
        mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
        // 获取实验室排课的通用配置对象；
        CStaticValue cStaticValue = cStaticValueService.getCStaticValueByTimetableLabDevice(acno);
        mav.addObject("courseNo",request.getParameter("courseNo"));
        mav.addObject("term",request.getParameter("term"));
        mav.addObject("schoolCourse",schoolCourse);
        mav.addObject("cStaticValue", cStaticValue);
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        // 获取可选的教师列表列表
        mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap(acno));
        mav.setViewName("lims/timetable/course/newEduReGroupCourse.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：教务排课管理-显示教务排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/schoolCourseStudnetList")
    public ModelAndView schoolCourseStudnetList(int term, String courseNo,@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 获取可选的教师列表列表
        mav.addObject("courseNo", courseNo);
        mav.addObject("termId", term);
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        mav.setViewName("lims/timetable/course/schoolCourseStudentList.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions 教务排课管理-调整排课编辑页面
     * @param request
     * @param acno
     * @return
     * @author 陈乐为 2019-1-14
     ************************************************************/
    @RequestMapping("/updateEduAdjustCourse")
    public ModelAndView updateEduAdjustCourse(HttpServletRequest request, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        String academyNumber = "";
        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
        if (!acno.equals("-1")) {
            //获取选择的实验中心
            academyNumber = schoolAcademyDAO.findSchoolAcademyByPrimaryKey(acno).getAcademyNumber();
        }
        TimetableAppointmentSameNumber timetableAppointmentSameNumber = null;
        if (Objects.nonNull(request.getParameter("sameNumberId")) && Integer.parseInt(request.getParameter("sameNumberId")) != -1) {
            int timetableAppointmentSameNumberId = Integer.parseInt(request.getParameter("sameNumberId"));
            timetableAppointmentSameNumber = timetableAppointmentSameNumberDAO.findTimetableAppointmentSameNumberById(timetableAppointmentSameNumberId);
        }
        mav.addObject("timetableAppointmentSameNumber", timetableAppointmentSameNumber);
        mav.addObject("timetableStyle", request.getParameter("timetableStyle"));
        mav.addObject("courseNo", request.getParameter("courseNo"));
        //获取课程编号
        SchoolCourse schoolCourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(request.getParameter("courseNo"));
        String courseNumber = "";
        if (Objects.nonNull(schoolCourse) && Objects.nonNull(schoolCourse.getSchoolCourseInfo())) {
            courseNumber = schoolCourse.getSchoolCourseInfo().getCourseNumber();
        }
        mav.addObject("schoolCourse", schoolCourse);
        mav.addObject("courseNumber", courseNumber);
        mav.addObject("term", request.getParameter("term"));
        mav.addObject("academyNumber", academyNumber);
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        mav.setViewName("lims/timetable/course/updateEduAdjustCourse.jsp");
        return mav;
    }

    /**
     * Description 教务排课管理-审核主页面
     * @param request
     * @param acno
     * @return
     * @author 陈乐为 2019-1-8
     */
    @RequestMapping("/auditTimetable")
    public ModelAndView auditTimetable(HttpServletRequest request, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 审核微服务传参
        String courseNo = request.getParameter("businessAppUid");
        String businessType = request.getParameter("businessType");
        String businessUid = request.getParameter("businessUid");
        /**
         * @description 业务主键转流水单号
         */
        String serialNumber = shareService.getSerialNumber(courseNo, businessType);
        if (serialNumber.equals("fail")) {// 取流水号失败后返回
            mav.addObject("error", "disabled");
            mav.setViewName("lims/timetable/course/auditTimetable.jsp");
            return mav;
        }
        /**
         * @description 调停课-{页面传参}
         * @description timetableStyle在这里用于前端操作入口的判断
         */
        if(businessType.equals("AdjustTimetableAudit") || businessType.equals("CloseTimetableAudit")) {
            SchoolCourse sCourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo);
            if (sCourse != null) {
                mav.addObject("termId", sCourse.getSchoolTerm().getId());
                mav.addObject("timetableStyle", 1);
            }else {
                try {
                    TimetableSelfCourse course = timetableSelfCourseDAO.findTimetableSelfCourseById(Integer.valueOf(courseNo));
                    mav.addObject("termId", course.getSchoolTerm().getId());
                    mav.addObject("timetableStyle", 5);
                } catch (NumberFormatException ignored) {
                }
            }
        }else if(businessType.equals("TimetableAudit")) {//默认教务排课，type=1
            SchoolCourse schoolCourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo);
            mav.addObject("termId", schoolCourse.getSchoolTerm().getId());
        }else if(businessType.equals("SelfTimetableAudit")){//自主排课，type=2
            TimetableSelfCourse course = timetableSelfCourseDAO.findTimetableSelfCourseById(Integer.valueOf(courseNo));
            mav.addObject("termId", course.getSchoolTerm().getId());
        }
        mav.addObject("businessType", businessType);
        mav.addObject("businessUid", businessUid);
        /**
         * @description 获取审核状态
         */
        Integer curStage = -2;
        String curAuthName = "";
        Map<String, String> params = new HashMap<>();
        params.put("businessType", pConfig.PROJECT_NAME + businessType);
        params.put("businessUid", businessUid);
        params.put("businessAppUid", serialNumber);
        String currStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", params);
        JSONObject currJSONObject = JSONObject.parseObject(currStr);
        if ("success".equals(currJSONObject.getString("status"))) {
            JSONArray currArray = currJSONObject.getJSONArray("data");
            if (currArray != null && currArray.size() > 0) {
                JSONObject o = currArray.getJSONObject(0);
                curStage = o.getIntValue("level");
                curAuthName = o.getString("result");
            }
        }

        /**
         * @description 获取审核配置
         */
        String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessLevelStatus", params);
        JSONObject jsonObject = JSONObject.parseObject(s);
        List<Object[]> auditItems = new ArrayList<>();
        if ("success".equals(jsonObject.getString("status"))) {
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray != null && jsonArray.size() > 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    Object[] os = new Object[6];
                    os[0] = shareService.getAuthorityByName(o.getString("authName")).getCname();
                    os[1] = o.getIntValue("level");
                    os[2] = o.getString("authName");
                    os[3] = serialNumber;
                    os[4] = curStage;
                    os[5] = curAuthName;
                    auditItems.add(os);
                }
//                mav.addObject("count", jsonArray.size());
            }
        }
        mav.addObject("auditConfigs", auditItems);
        // 页面传参
        mav.addObject("courseNo", courseNo);
        mav.addObject("businessAppUid", serialNumber);

        mav.setViewName("lims/timetable/course/auditTimetable.jsp");
        return mav;
    }

    /**
     * 教务排课管理-审核iframe页面
     * @param request
     * @param acno
     * @return
     * @author 陈乐为 2019-1-8
     */
    @RequestMapping("/auditTimetableList")
    public ModelAndView auditTimetableList(HttpServletRequest request, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        Integer isAudit = 0;
        Integer state = Integer.parseInt(request.getParameter("state"));
        Integer curStage = Integer.parseInt(request.getParameter("curStage"));
        String curAuthName = request.getParameter("curAuthName");
        String authName = request.getParameter("authName");
        String businessType = request.getParameter("businessType");
        String businessUid = request.getParameter("businessUid");
        String businessAppUid = request.getParameter("businessAppUid");// 流水单号

        mav.addObject("state", state);
        mav.addObject("curStage", curStage);
        mav.addObject("businessType", businessType);
        mav.addObject("businessAppUid", businessAppUid);
        if (state < curStage || curStage == 0 || curStage == -1) {
            // 获取审核配置
            Map<String, String> params = new HashMap<>();
            params.put("businessType", pConfig.PROJECT_NAME + businessType);
            params.put("businessUid", businessUid);
            params.put("businessAppUid", businessAppUid);
            String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessLevelStatus", params);
            JSONObject jsonObject = JSONObject.parseObject(s);
            if ("success".equals(jsonObject.getString("status"))) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                if (jsonArray != null && jsonArray.size() > 0) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject o = jsonArray.getJSONObject(i);
                        Integer oi = o.getIntValue("level");
                        if (oi.equals(state)) {
                            Object[] userInfo = new Object[4];
                            userInfo[0] = shareService.findUserByUsername(o.getString("auditUser"));
                            userInfo[1] = o.getString("result");
                            userInfo[2] = o.getString("info");
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                            try {
                                userInfo[3] = sdf.parse(o.getString("createTime"));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            mav.addObject("userInfo", userInfo);
                        }
                    }
                    mav.addObject("count", jsonArray.size());
                }
            }
        } else {
            // 流水单号转业务主键
            String courseNo = auditSerialNumberDAO.findAuditSerialNumberByUuid(businessAppUid).getBusinessId();
            Set<User> isAuditUser = new HashSet<>();
            // 获取下一级审核人
            Query queryNextAudit = entityManager.createNativeQuery("call proc_timetable_next_auditor('" + authName + "','" + businessType + "','" + courseNo + "')");
            List nextAuditList = queryNextAudit.getResultList();
            for(Object name: nextAuditList){
                isAuditUser.add(shareService.findUserByUsername((String) name));
            }
//            switch (authName) {
//                case "LABMANAGER":
//                    if (businessType.equals(ConstantInterface.FIRM_TYPE_TIMETABLE)) {// 教务排课
//                        SchoolCourse course = schoolCourseDAO.findSchoolCourseByPrimaryKey(businessAppUid);
//                        for (TimetableAppointment timetableAppointment : course.getTimetableAppointments()) {
//                            for (TimetableLabRelated timetableLabRelated : timetableAppointment.getTimetableLabRelateds()) {
//                                for (LabRoomAdmin labRoomAdmin : timetableLabRelated.getLabRoom().getLabRoomAdmins()) {
//                                    isAuditUser.add(labRoomAdmin.getUser());
//                                }
//                            }
//                        }
//                    } else if (businessType.equals(ConstantInterface.FIRM_TYPE_SELF_TIMETABLE)) {// 自主排课
//                        TimetableSelfCourse course = timetableSelfCourseDAO.findTimetableSelfCourseById(Integer.parseInt(businessAppUid));
//                        for (TimetableAppointment timetableAppointment : course.getTimetableAppointments()) {
//                            for (TimetableLabRelated timetableLabRelated : timetableAppointment.getTimetableLabRelateds()) {
//                                for (LabRoomAdmin labRoomAdmin : timetableLabRelated.getLabRoom().getLabRoomAdmins()) {
//                                    isAuditUser.add(labRoomAdmin.getUser());
//                                }
//                            }
//                        }
//                    }
//                    break;
//                default:
//                    isAuditUser.addAll(shareService.findUsersByAuthorityNameAndAcno(curAuthName,acno));
//            }
//            if (request.getSession().getAttribute("selected_role").equals("ROLE_" + curAuthName)) {
//                for (User eUser : isAuditUser) {
//                    if (eUser.getUsername().equals(shareService.getUserDetail().getUsername()) && curAuthName.equals(authName)) {
//                        isAudit = 1;
//                        break;
//                    }
//                }
//            }
            if (request.getSession().getAttribute("selected_role").equals("ROLE_" + curAuthName)) {
                for (Object username: nextAuditList) {
                    if (username.equals(shareService.getUserDetail().getUsername()) && curAuthName.equals(authName)) {
                        isAudit = 1;
                        break;
                    }
                }
            }
            if (isAudit != 1) {
                mav.addObject("isAuditUser", isAuditUser);
            }
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        mav.setViewName("lims/timetable/course/auditTimetableList.jsp");
        return mav;
    }

    @RequestMapping("/chooseCopyTimetableGroup")
    public ModelAndView chooseCopyTimetableGroup(@RequestParam Integer id, @RequestParam Integer batchId, @RequestParam String courseNo, Integer termId){
        ModelAndView mav = new ModelAndView();
        mav.addObject("batchId", batchId);
        mav.addObject("sourceId", id);
        mav.addObject("courseNo", courseNo);
        TimetableBatch timetableBatch = timetableBatchDAO.findTimetableBatchById(batchId);
        TimetableGroup timetableGroup = timetableGroupDAO.findTimetableGroupById(id);
        Set<TimetableGroup> groups = timetableBatch.getTimetableGroups();
        groups.remove(timetableGroup);
        mav.addObject("groups", groups);
        mav.addObject("termId", termId);
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        mav.setViewName("lims/timetable/course/chooseCopyGroup.jsp");
        return mav;
    }

    @RequestMapping("/copyTimetableGroup")
    public ModelAndView copyTimetableGroup(@RequestParam Integer destinationId, @RequestParam Integer sourceId,
                                           @RequestParam String courseNo, Integer termId,
                                           @ModelAttribute("selected_academy") String acno) throws UnsupportedEncodingException {
        ModelAndView mav = new ModelAndView();
        TimetableGroup sourceGroup = timetableGroupDAO.findTimetableGroupById(sourceId);
        TimetableGroup destinationGroup = timetableGroupDAO.findTimetableGroupById(destinationId);
        String academyNumber="";
        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
        if (!acno.equals("-1")) {
            //获取选择的实验中心
            academyNumber = schoolAcademyDAO.findSchoolAcademyByPrimaryKey(acno).getAcademyNumber();
        }
        mav.addObject("groupId",destinationId);
        mav.addObject("destinationGroup",destinationGroup);
        // 要复制的课程
        mav.addObject("timetableAppointments", sourceGroup.getTimetableAppointments());
        Map<Integer, Set<Integer>> timetableAppClasses = new HashMap<>();
        Map<Integer, Set<Integer>> timetableAppWeeks = new HashMap<>();
        List<Integer> timetableAppointmentIds = new ArrayList<>();
        for(TimetableAppointment timetableAppointment: sourceGroup.getTimetableAppointments()){
            Set<Integer> classes = new LinkedHashSet<>();
            Set<Integer> weeks = new LinkedHashSet<>();
            for(TimetableAppointmentSameNumber tasn: timetableAppointment.getTimetableAppointmentSameNumbers()){
                for (int i = tasn.getStartClass(); i <= tasn.getEndClass(); i++) {
                    classes.add(i);
                }
                for (int i = tasn.getStartWeek(); i <= tasn.getEndWeek(); i++) {
                    weeks.add(i);
                }
            }
            timetableAppClasses.put(timetableAppointment.getId(), classes);
            timetableAppWeeks.put(timetableAppointment.getId(), weeks);
            timetableAppointmentIds.add(timetableAppointment.getId());
        }
        mav.addObject("timetableAppointmentIds", timetableAppointmentIds);
        mav.addObject("timetableAppClasses", timetableAppClasses);
        mav.addObject("timetableAppWeeks", timetableAppWeeks);
        mav.addObject("timetableStyle", 4);
        mav.addObject("courseNo",courseNo);
        //获取课程编号
        SchoolCourse schoolCourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo);
        String courseNumber = "";
        if(Objects.nonNull(schoolCourse)&&Objects.nonNull(schoolCourse.getSchoolCourseInfo())){
            courseNumber = schoolCourse.getSchoolCourseInfo().getCourseNumber();
        }
        mav.addObject("schoolCourse",schoolCourse);
        mav.addObject("courseNumber",courseNumber);
        mav.addObject("term",termId);
        mav.addObject("academyNumber",academyNumber);
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        mav.setViewName("lims/timetable/course/copyGroupTimetable.jsp");
        return mav;
    }

    @RequestMapping("/saveCopyTimetableGroup")
    public @ResponseBody
    String saveCopyTimetableGroup(HttpServletRequest request){
        String[] ids = request.getParameter("timetableAppointmentIds").replace("[", "").replace("]", "").replace(" ", "").split(",");
        List<TimetableParamVO> timetableParamVOS = new ArrayList<>();
        for(String id: ids){
            TimetableParamVO timetableParamVO = new TimetableParamVO();
            timetableParamVO.setCourseNo(request.getParameter("courseNo"));
            timetableParamVO.setGroupId(Integer.parseInt(request.getParameter("groupId")));
            timetableParamVO.setSameNumberId(-1);
            timetableParamVO.setStatus(10);
            timetableParamVO.setTimetableStyle(4);
            timetableParamVO.setItems(getArrayFromString(request.getParameterValues("items" + id)));
            timetableParamVO.setTearchs(request.getParameterValues("teacherRelated" + id));
            timetableParamVO.setTutors(request.getParameterValues("tutorRelated" + id));
            timetableParamVO.setClasses(getArrayFromString(request.getParameterValues("classes" + id)));
            timetableParamVO.setLabRoomIds(getArrayFromString(request.getParameterValues("labRoom_id" + id)));
            timetableParamVO.setWeekday(Integer.parseInt(request.getParameter("weekday" + id)));
            timetableParamVO.setTerm(Integer.parseInt(request.getParameter("term")));
            int[] weeks = getArrayFromString(request.getParameterValues("weeks" + id));
            if(weeks == null){
                return "fail";
            }
            int[] intWeeks = timetableCommonService.getWeeksListBySelect(timetableParamVO.getTerm());
            timetableParamVO.setWeeks(intWeeks);
            List<Integer> validWeeks = timetableCommonService.getTimetableValidWeeksList(timetableParamVO);
            for(int week: weeks){
                if(!validWeeks.contains(week)){
                    return "fail";
                }
            }
            timetableParamVO.setWeeks(weeks);
            timetableParamVOS.add(timetableParamVO);
        }
        for(TimetableParamVO timetableParamVO: timetableParamVOS){
            for(TimetableParamVO timetableParamVO1: timetableParamVOS){
                if(!timetableParamVO.equals(timetableParamVO1)){
                    if(timetableParamVO.getWeekday() == timetableParamVO1.getWeekday()){
                        for(int labRoomId: timetableParamVO.getLabRoomIds()){
                            if(Arrays.stream(timetableParamVO1.getLabRoomIds()).boxed().collect(Collectors.toList()).contains(labRoomId)){
                                for(int week: timetableParamVO.getWeeks()){
                                    if(Arrays.stream(timetableParamVO1.getWeeks()).boxed().collect(Collectors.toList()).contains(week)){
                                        for(int oneClass: timetableParamVO.getClasses()){
                                            if(Arrays.stream(timetableParamVO1.getClasses()).boxed().collect(Collectors.toList()).contains(oneClass)){
                                                return "repeat";
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        for(TimetableParamVO timetableParamVO: timetableParamVOS){
            boolean bool = eduCourseService.apiSaveCourseTimetableAppointment(timetableParamVO);
            if(!bool){
                return "error";
            }
        }
        return "success";
    }

    private int[] getArrayFromString(String[] origin){
        if(origin != null && origin.length > 0){
            int[] result = new int[origin.length];
            for (int i = 0; i < origin.length; i++) {
                result[i] = Integer.parseInt(origin[i]);
            }
            return result;
        }
        return new int[0];
    }

}
