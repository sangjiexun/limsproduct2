package net.gvsun.lims.web.preCourseManagement;

import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.common.JsonValueDTO;
import net.gvsun.lims.dto.common.SelectDTO;
import net.gvsun.lims.service.preCourseManagement.PreCourseManagementService;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.BaseApplicationService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.timetable.OuterApplicationService;
import net.zjcclims.service.timetable.SchoolCourseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/****************************************************************************
 * @描述： 预排课管理模块
 * @作者 ：张德冰
 * @时间： 2018-12-18
 ****************************************************************************/
@Controller("preCourseManagementController")
@SessionAttributes("selected_academy")
@RequestMapping("/lims/preCourse")
public class preCourseManagementController<JsonResult> {
    @Autowired
    private ShareService shareService;
    @Autowired
    private PreCourseManagementService preCourseManagementService;
    @Autowired
    private PreCapacityRangeDAO preCapacityRangeDAO;
    @Autowired
    private PreRoomTypeDAO preRoomTypeDAO;
    @Autowired
    private PreSoftwareDAO preSoftwareDAO;
    @Autowired
    private PreLabRoomDAO preLabRoomDAO;
    @Autowired
    private OuterApplicationService outerApplicationService;
    @Autowired
    private SchoolCourseInfoService schoolCourseInfoService;
    @Autowired
    private SystemTimeDAO systemTimeDAO;
    @Autowired
    private PreTimetableAppointmentDAO preTimetableAppointmentDAO;
    @Autowired
    private PreTimetableScheduleDAO preTimetableScheduleDAO;
    @Autowired
    private BaseApplicationService baseApplicationService;

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

    /****************************************************************************
     * @描述： 预排课房间管理
     * @作者 ：张德冰
     * @时间： 2018-12-18
     ****************************************************************************/
    @RequestMapping("/preLabRoom")
    public ModelAndView listPreLabRoom() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("/lims/preCourseManagement/preLabRoom/preLabRoomList.jsp");
        return mav;
    }

    /****************************************************************************
     * @描述： 获取预排课房间记录
     * @作者 ：张德冰
     * @时间： 2018-12-18
     ****************************************************************************/
    @ResponseBody
    @RequestMapping("/apiPreLabRoomListByPage")
    public BaseDTO apiEduSchoolCourseByPage(int offset, int limit, String search, String sort, String order, HttpServletRequest request) {
        //获取查询课程库列表
        BaseDTO baseVo = preCourseManagementService.getPreLabRoomListByPage(search, offset, limit, sort, order, request);
        return baseVo;
    }

    /****************************************************************************
     * @描述： 新建预排课房间
     * @作者 ：张德冰
     * @时间： 2018-12-19
     ****************************************************************************/
    @RequestMapping("/newPreLabRoom")
    public ModelAndView newPreLabRoom(HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();

        //所属部门（学院）
        //List<SchoolAcademy> schoolAcademies = shareService.findAllSchoolAcademys();
        SchoolAcademy schoolAcademy = shareService.findSchoolAcademyByPrimaryKey(acno);
        mav.addObject("schoolAcademy",schoolAcademy);
        //容量范围
        Set<PreCapacityRange> preCapacityRanges = preCapacityRangeDAO.findAllPreCapacityRanges();
        mav.addObject("preCapacityRanges",preCapacityRanges);
        //房间布局类型
        Set<PreRoomType> preRoomTypes = preRoomTypeDAO.findAllPreRoomTypes();
        mav.addObject("preRoomTypes",preRoomTypes);
        //软件
        Set<PreSoftware> preSoftwares = preSoftwareDAO.findAllPreSoftwares();
        mav.addObject("preSoftwares",preSoftwares);
        //新建:0  编辑：1
        mav.addObject("isEdit",0);
        mav.addObject("preLabRoom",new PreLabRoom());

        mav.setViewName("/lims/preCourseManagement/preLabRoom/newPreLabRoom.jsp");
        return mav;
    }

    /****************************************************************************
     * @描述： 保存预排课房间
     * @作者 ：张德冰
     * @时间： 2018-12-19
     ****************************************************************************/
    @RequestMapping("/savePreLabRoom")
    public void savePreLabRoom(HttpServletRequest request, @ModelAttribute PreLabRoom preLabRoom, @ModelAttribute("selected_academy") String acno) {

        //获取软件
        String[] preSoftwareId = request.getParameterValues("preSoftwareId");
        HashSet<PreSoftware> sf = new HashSet<PreSoftware>();
        for(String id:preSoftwareId){
            PreSoftware p = preSoftwareDAO.findPreSoftwareById(Integer.parseInt(id));
            sf.add(p);
        }
        preLabRoom.setPreSoftwares(sf);
        //保存
        preLabRoomDAO.store(preLabRoom);
    }

    /****************************************************************************
     * @描述： 预排课房间-编辑
     * @作者 ：张德冰
     * @时间： 2018-12-21
     ****************************************************************************/
    @RequestMapping("/editPreLabRoom")
    public ModelAndView editPreLabRoom(Integer id,@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();

        PreLabRoom preLabRoom = preLabRoomDAO.findPreLabRoomById(id);

        Set<PreSoftware> preSoftwareAll = preLabRoom.getPreSoftwares();
        mav.addObject("preSoftwareAll",preSoftwareAll);

        //所属部门（学院）
        //List<SchoolAcademy> schoolAcademies = shareService.findAllSchoolAcademys();
        SchoolAcademy schoolAcademy = shareService.findSchoolAcademyByPrimaryKey(acno);
        mav.addObject("schoolAcademy",schoolAcademy);
        //容量范围
        Set<PreCapacityRange> preCapacityRanges = preCapacityRangeDAO.findAllPreCapacityRanges();
        mav.addObject("preCapacityRanges",preCapacityRanges);
        //房间布局类型
        Set<PreRoomType> preRoomTypes = preRoomTypeDAO.findAllPreRoomTypes();
        mav.addObject("preRoomTypes",preRoomTypes);
        //软件
        Set<PreSoftware> preSoftwares = preSoftwareDAO.findAllPreSoftwares();
        mav.addObject("preSoftwares",preSoftwares);
        //新建:0  编辑：1
        mav.addObject("isEdit",1);
        mav.addObject("preLabRoom",preLabRoom);

        mav.setViewName("/lims/preCourseManagement/preLabRoom/newPreLabRoom.jsp");
        return mav;
    }

    /****************************************************************************
     * @描述： 预排课房间-删除
     * @作者 ：张德冰
     * @时间： 2018-12-21
     ****************************************************************************/
    @RequestMapping("/deletePreLabRoom")
    public String deletePreLabRoom(Integer id) {

        PreLabRoom preLabRoom = preLabRoomDAO.findPreLabRoomById(id);
        preLabRoomDAO.remove(preLabRoom);
        return "redirect:/lims/preCourse/preLabRoom";
    }

    /****************************************************************************
     * @描述： 预排课房间-查看
     * @作者 ：张德冰
     * @时间： 2018-12-21
     ****************************************************************************/
    @RequestMapping("/viewPreLabRoom")
    public ModelAndView viewPreLabRoom(Integer id,@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();

        PreLabRoom preLabRoom = preLabRoomDAO.findPreLabRoomById(id);

        Set<PreSoftware> preSoftwareAll = preLabRoom.getPreSoftwares();
        mav.addObject("preSoftwareAll",preSoftwareAll);
        mav.addObject("preLabRoom",preLabRoom);

        //所属部门（学院）
        SchoolAcademy schoolAcademy = shareService.findSchoolAcademyByPrimaryKey(acno);
        mav.addObject("schoolAcademy",schoolAcademy);

        mav.setViewName("/lims/preCourseManagement/preLabRoom/viewPreLabRoom.jsp");
        return mav;
    }

    /****************************************************************************
     * @描述： 我的预排课-入口
     * @作者 ：张德冰
     * @时间： 2018-12-24
     ****************************************************************************/
    @RequestMapping("/myPreCourse")
    public ModelAndView listMyPreCourseList() {
        ModelAndView mav = new ModelAndView();

        // 当前学期
        mav.addObject("termId", shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId());
        // 获取学期列表
        List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
        mav.addObject("schoolTerms", schoolTerms);

        mav.addObject("isMy", "true");

        mav.setViewName("/lims/preCourseManagement/preCourse/myPreCourse.jsp");
        return mav;
    }

    /****************************************************************************
     * @描述： 获取预排课记录
     * @作者 ：张德冰
     * @时间： 2018-12-24
     ****************************************************************************/
    @ResponseBody
    @RequestMapping("/getPreCourseList")
    public BaseDTO getPreCourseList(int offset, int limit, String search, String sort, String order, HttpServletRequest request, Integer termId, String status) {
        //获取查询课程库列表
        BaseDTO baseVo = preCourseManagementService.getPreCourseList(offset, limit,search, sort, order, request,termId,status);
        return baseVo;
    }

    /****************************************************************************
     * @描述： 新建预排课记录
     * @作者 ：张德冰
     * @时间： 2018-12-25
     ****************************************************************************/
    @RequestMapping("/newMyPreCourse")
    public ModelAndView newMyPreCourse(HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();

        // 当前学期
        SchoolTerm term = shareService.getBelongsSchoolTerm(Calendar.getInstance());
        mav.addObject("term", term);
        // 获取学期列表
        List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
        mav.addObject("schoolTerms", schoolTerms);

        //当前登录人
        User currUser = shareService.getUserDetail();
        mav.addObject("currUser",currUser);

        //所属部门（学院）
        SchoolAcademy schoolAcademy = shareService.findSchoolAcademyByPrimaryKey(acno);
        mav.addObject("schoolAcademy",schoolAcademy);
        //房间布局类型
        Set<PreRoomType> preRoomTypes = preRoomTypeDAO.findAllPreRoomTypes();
        mav.addObject("preRoomTypes",preRoomTypes);
        //软件
        Set<PreSoftware> preSoftwares = preSoftwareDAO.findAllPreSoftwares();
        mav.addObject("preSoftwares",preSoftwares);

        //周次
        Map weeksMap = shareService.getWeeksMap(term.getId());
        mav.addObject("weeksMap",weeksMap);
        //节次
        Set<SystemTime> classes = systemTimeDAO.findAllSystemTimes();
        mav.addObject("classes",classes);
        //星期
        Map weekdayMap = shareService.getWeekdays();
        mav.addObject("weekdayMap",weekdayMap);
        //预排课房间
        List<PreLabRoom> preLabRooms = preCourseManagementService.findPreLabRooms(acno, null,null,null);
        mav.addObject("preLabRooms",preLabRooms);

        //新建:0  编辑：1
        mav.addObject("isEdit",0);
        mav.addObject("preTimetableAppointment",new PreTimetableAppointment());

        mav.setViewName("/lims/preCourseManagement/preCourse/newMyPreCourse.jsp");
        return mav;
    }

    /****************************************************************************
     * @描述： 获取课程信息
     * @作者 ：张德冰
     * @时间： 2018-12-27
     ****************************************************************************/
    @ResponseBody
    @RequestMapping("/apiSchoolCourseInfoBySelect")
    public SelectDTO apiSchoolCourseInfoBySelect(String search, String acno) {
        //获取查询课程库列表
        List<JsonValueDTO> jsonValueDTOs = preCourseManagementService.findSchoolCourseInfo(search, acno);
        SelectDTO SelectDTO = new SelectDTO();
        SelectDTO.setResults(jsonValueDTOs);
        return SelectDTO;
    }

    /****************************************************************************
     * @描述： 保存预排课记录
     * @作者 ：张德冰
     * @时间： 2018-12-25
     ****************************************************************************/
    @RequestMapping("/savePreCourse")
    public void savePreCourse(HttpServletRequest request, @ModelAttribute PreTimetableAppointment preTimetableAppointment) {

        //获取房间
        /*String[] preLabRoomId = request.getParameterValues("preLabRoomId");
        HashSet<PreLabRoom> sf = new HashSet<PreLabRoom>();
        for(String id:preLabRoomId){
            PreLabRoom p = preLabRoomDAO.findPreLabRoomById(Integer.parseInt(id));
            sf.add(p);
        }
        preTimetableAppointment.setPreLabRooms(sf);*/
        //默认未发布
        preTimetableAppointment.setState(0);
        //保存预排课记录
        preTimetableAppointment = preTimetableAppointmentDAO.store(preTimetableAppointment);
        //保存预排课时间
        String[] weekdayArray = request.getParameterValues("weekday");
        if(weekdayArray.length>0) {
            for (int i = 0; i < weekdayArray.length; i++) {
                Integer weekday = Integer.parseInt(weekdayArray[i]);
                String[] weekArray = request.getParameterValues("week"+i);
                int[] weeks=new int[weekArray.length];
                for (int j = 0; j < weekArray.length; j++) {
                    weeks[j] = Integer.parseInt(weekArray[j]);
                }
                String[] classArray = request.getParameterValues("class"+i);
                int[] classes=new int[classArray.length];
                for (int j = 0; j < classArray.length; j++) {
                    classes[j] = Integer.parseInt(classArray[j]);
                }
                preCourseManagementService.savePreTimetableSchedule(preTimetableAppointment.getId(),weeks,classes,weekday);
            }
        }
    }

    /****************************************************************************
     * @描述： 预排课记录-删除
     * @作者 ：张德冰
     * @时间： 2018-12-26
     ****************************************************************************/
    @RequestMapping("/deletePreCourse")
    public String deletePreCourse(Integer id) {

        PreTimetableAppointment pta = preTimetableAppointmentDAO.findPreTimetableAppointmentById(id);
        preTimetableAppointmentDAO.remove(pta);
        return "redirect:/lims/preCourse/myPreCourse";
    }

    /****************************************************************************
     * @描述： 预排课记录-发布
     * @作者 ：张德冰
     * @时间： 2018-12-26
     ****************************************************************************/
    @RequestMapping("/changePreCourseState")
    public String changePreCourseState(Integer id) {

        PreTimetableAppointment preTimetableAppointment = preTimetableAppointmentDAO.findPreTimetableAppointmentById(id);
        //改变状态为发布
        preTimetableAppointment.setState(1);
        //保存预排课记录
        preTimetableAppointmentDAO.store(preTimetableAppointment);
        return "redirect:/lims/preCourse/myPreCourse";
    }

    /****************************************************************************
     * @描述： 编辑预排课记录
     * @作者 ：张德冰
     * @时间： 2018-12-26
     ****************************************************************************/
    @RequestMapping("/editPreCourse")
    public ModelAndView editPreCourse(HttpServletRequest request,Integer id, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();

        //获取记录
        PreTimetableAppointment pta = preTimetableAppointmentDAO.findPreTimetableAppointmentById(id);
        // 当前学期
        SchoolTerm term = pta.getSchoolTerm();
        mav.addObject("term", term);
        // 获取学期列表
        List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
        mav.addObject("schoolTerms", schoolTerms);
        //课程信息
        SchoolCourseInfo schoolCourseInfo = pta.getSchoolCourseInfo();
        mav.addObject("schoolCourseInfo", schoolCourseInfo);
        //指导教师
        User tutor = pta.getUserByTutor();
        mav.addObject("tutor",tutor);
        //授课教师
        User currUser = pta.getUserByTeacher();
        mav.addObject("currUser",currUser);

        //所属部门（学院）
        SchoolAcademy schoolAcademy = pta.getSchoolAcademy();
        mav.addObject("schoolAcademy",schoolAcademy);
        //房间布局类型
        Set<PreRoomType> preRoomTypes = preRoomTypeDAO.findAllPreRoomTypes();
        mav.addObject("preRoomTypes",preRoomTypes);
        //软件
        Set<PreSoftware> preSoftwares = preSoftwareDAO.findAllPreSoftwares();
        mav.addObject("preSoftwares",preSoftwares);

        //周次
        Map weeksMap = shareService.getWeeksMap(term.getId());
        mav.addObject("weeksMap",weeksMap);
        //节次
        Set<SystemTime> classes = systemTimeDAO.findAllSystemTimes();
        mav.addObject("classes",classes);
        //星期
        Map weekdayMap = shareService.getWeekdays();
        mav.addObject("weekdayMap",weekdayMap);
        //预排课房间
        List<PreLabRoom> preLabRooms = preCourseManagementService.findPreLabRooms(acno, null,null,null);
        mav.addObject("preLabRooms",preLabRooms);

        if(pta.getPreLabRooms().size()>0) {
            Set<PreLabRoom> preLabRoom = pta.getPreLabRooms();
            mav.addObject("preLabRoom", preLabRoom);
        }
        //排课时间
        if(pta.getPreTimetableSchedules().size()>0) {
            Set<PreTimetableSchedule> preTimetableSchedules = pta.getPreTimetableSchedules();
            mav.addObject("preTimetableSchedules",preTimetableSchedules);
        }

        //新建:0  编辑：1
        mav.addObject("isEdit",1);
        mav.addObject("preTimetableAppointment",pta);

        mav.setViewName("/lims/preCourseManagement/preCourse/newMyPreCourse.jsp");
        return mav;
    }

    /****************************************************************************
     * @描述： 预排课记录时间-删除
     * @作者 ：张德冰
     * @时间： 2018-12-26
     ****************************************************************************/
    @RequestMapping("/deletePreTimetableSchedule")
    public String deletePreTimetableSchedule(Integer id) {

        PreTimetableSchedule pta = preTimetableScheduleDAO.findPreTimetableScheduleById(id);
        Integer ptaid = pta.getPreTimetableAppointment().getId();
        preTimetableScheduleDAO.remove(pta);
        return "redirect:/lims/preCourse/editPreCourse?id="+ptaid;
    }

    /****************************************************************************
     * @描述： 预排课记录-查看
     * @作者 ：张德冰
     * @时间： 2018-12-26
     ****************************************************************************/
    @RequestMapping("/viewPreCourse")
    public ModelAndView viewPreCourse(Integer id) {
        ModelAndView mav = new ModelAndView();

        //获取记录
        PreTimetableAppointment pta = preTimetableAppointmentDAO.findPreTimetableAppointmentById(id);
        mav.addObject("pta",pta);

        //预排课房间
        if(pta.getPreLabRooms().size()>0) {
            Set<PreLabRoom> preLabRoom = pta.getPreLabRooms();
            mav.addObject("preLabRoom", preLabRoom);
        }
        //排课时间
        if(pta.getPreTimetableSchedules().size()>0) {
            Set<PreTimetableSchedule> preTimetableSchedules = pta.getPreTimetableSchedules();
            mav.addObject("preTimetableSchedules",preTimetableSchedules);
        }

        mav.setViewName("/lims/preCourseManagement/preCourse/viewPreCourse.jsp");
        return mav;
    }

    /****************************************************************************
     * @描述： 预排课记录-入口
     * @作者 ：张德冰
     * @时间： 2018-12-26
     ****************************************************************************/
    @RequestMapping("/preCourseRecord")
    public ModelAndView preTimetableAppointmentRecord() {
        ModelAndView mav = new ModelAndView();

        // 当前学期
        mav.addObject("termId", shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId());
        // 获取学期列表
        List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
        mav.addObject("schoolTerms", schoolTerms);

        mav.addObject("isMy", "false");

        mav.setViewName("/lims/preCourseManagement/preCourse/preCourseRecord.jsp");
        return mav;
    }

    /****************************************************************************
     * @描述： 预排课记录-导出
     * @作者 ：张德冰
     * @时间： 2018-12-26
     ****************************************************************************/
    @RequestMapping("/exportPreCourseRecord")
    public void exportPreCourseRecord(HttpServletRequest request, HttpServletResponse response){
        String search = request.getParameter("search");
        Integer termId = Integer.parseInt(request.getParameter("term"));
        String status = request.getParameter("state");
        //获得记录
        List<PreTimetableAppointment> preTimetableAppointmentList = preCourseManagementService.findPreTimetableAppointmentList(0, 0, search, null, null, request, termId, status);
        // 新建一个map的list集合；
        List<Map> list = new ArrayList<Map>();
        Integer index = 0;
        for (PreTimetableAppointment p:preTimetableAppointmentList){
            index++;
            if(p.getPreTimetableSchedules().size()>0) {
                for(PreTimetableSchedule pts:p.getPreTimetableSchedules()) {
                    // 新建一个HashMap对象；
                    Map map = new HashMap();
                    // 序号
                    map.put("index", index);
                    //课程信息
                    map.put("courseName", p.getSchoolCourseInfo().getCourseName());
                    //所属部门
                    map.put("academyName", p.getSchoolAcademy().getAcademyName());
                    //上课人数
                    map.put("stuNumber", p.getStuNumber());
                    //课程安排[周次][星期][节次]
                    String time = "["+pts.getStartWeek()+"-"+pts.getEndWeek()+"周]"+//周次
                            "[周"+pts.getStartWday()+"]["+//星期
                            pts.getStartClass()+"-"+pts.getEndClass()+"节]";//节次
                    map.put("preTimetableSchedule", time);
                    //授课教师
                    map.put("teacher", p.getUserByTeacher().getCname());
                    //指导教师
                    if (p.getUserByTutor() != null) {
                        map.put("tutor", p.getUserByTutor().getCname());
                    }
                    //预排课房间
                    String room = "";
                    List<PreLabRoom> preLabRooms = preCourseManagementService.findPreLabRoomsByCapacityRange(p);
                    for (PreLabRoom l : preLabRooms) {
                        room += l.getRoomName() + "、";
                    }
                    map.put("preLabRoomList", room);
                    list.add(map);
                }
            }else {
                // 新建一个HashMap对象；
                Map map = new HashMap();
                // 序号
                map.put("index", index);
                //课程信息
                map.put("courseName", p.getSchoolCourseInfo().getCourseName());
                //所属部门
                map.put("academyName", p.getSchoolAcademy().getAcademyName());
                //上课人数
                map.put("stuNumber", p.getStuNumber());
                //课程安排[周次][星期][节次]
                map.put("preTimetableSchedule", "");
                //授课教师
                map.put("teacher", p.getUserByTeacher().getCname());
                //指导教师
                if (p.getUserByTutor() != null) {
                    map.put("tutor", p.getUserByTutor().getCname());
                }
                //预排课房间
                if (p.getPreLabRooms().size() > 0) {
                    String room = "";
                    for (PreLabRoom l : p.getPreLabRooms()) {
                        room += l.getRoomName() + "、";
                    }
                    map.put("preLabRoomList", room);
                }
                list.add(map);
            }

        }
        // 给表设置名称；
        String title = "预排课记录 - （学期、综合查询）导出";
        // 给表设置表名；
        String[] hearders = new String[] { "序号", "课程信息", "所属部门", "上课人数", "课程安排[周次][星期][节次]"
                , "预排课房间", "授课教师", "指导教师"};// 表头数组
        // 属性数组，写数据到excel时的顺序定位
        String[] fields = new String[] { "index", "courseName", "academyName", "stuNumber", "preTimetableSchedule"
                , "preLabRoomList", "teacher", "tutor"};
        // 输出excel；
        try {
            baseApplicationService.exportExcel(list, title, hearders, fields,
                    request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /****************************************************************************
     * @描述： 预排课记录时间-判冲
     * @作者 ：张德冰
     * @时间： 2018-12-28
     ****************************************************************************/
    @RequestMapping("/preCoursePunching")
    public @ResponseBody
    String preCoursePunchin(HttpServletRequest request, @ModelAttribute PreTimetableAppointment preTimetableAppointment) {
        //默认预排课记录时间没有冲突
        String result = "ok";
        //星期
        String[] weekdayArray = request.getParameterValues("weekday");
        if(weekdayArray.length>0) {
            for (int i = 0; i < weekdayArray.length; i++) {
                Integer weekday = Integer.parseInt(weekdayArray[i]);
                //周次
                String[] weekArray = request.getParameterValues("week"+i);
                int[] weeks=new int[weekArray.length];
                for (int j = 0; j < weekArray.length; j++) {
                    weeks[j] = Integer.parseInt(weekArray[j]);
                }
                //节次
                String[] classArray = request.getParameterValues("class"+i);
                int[] classes=new int[classArray.length];
                for (int j = 0; j < classArray.length; j++) {
                    classes[j] = Integer.parseInt(classArray[j]);
                }
                Integer p = preCourseManagementService.findPreTimetableAppointmentList(preTimetableAppointment, weeks, classes, weekday).size();
                if(p>0){
                    result = "havTea";
                    break;
                }
            }
        }

        return result;
    }
}
