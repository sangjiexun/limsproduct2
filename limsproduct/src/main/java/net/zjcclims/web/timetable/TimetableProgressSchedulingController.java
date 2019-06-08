package net.zjcclims.web.timetable;

import api.net.gvsunlims.dto.timetable.progressScheduling.DoAdminTimetableDTO;
import flex.messaging.io.ArrayList;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.domain.*;
import net.zjcclims.service.ConvertUtil;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.MySQLService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.dictionary.CDictionaryService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.operation.OperationService;
import net.zjcclims.service.timetable.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Controller("TimetableProgressSchedulingController")
@SessionAttributes("selected_academy")
public class TimetableProgressSchedulingController<JsonResult> {

    /************************************************************************************
     * @初始化WebDataBinder，这个WebDataBinder用于填充被@InitBinder注释的处理 方法的command和form对象
     *
     ************************************************************************************/
    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) {
        binder.registerCustomEditor(java.util.Calendar.class,
                new org.skyway.spring.util.databinding.CustomCalendarEditor());
        binder.registerCustomEditor(byte[].class,
                new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
        binder.registerCustomEditor(boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(false));
        binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
        binder.registerCustomEditor(java.math.BigDecimal.class,
                new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
        binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                Integer.class, true));
        binder.registerCustomEditor(java.util.Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
        binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
        binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                Long.class, true));
        binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                Double.class, true));
    }

    @Autowired
    private OuterApplicationService outerApplicationService;
    @Autowired
    private ShareService shareService;
    @Autowired
    private MySQLService mySQLService;
    @Autowired
    private SchoolCourseService schoolCourseService;
    @Autowired
    private OperationService operationService;
    @Autowired
    private SchoolCourseDetailService schoolCourseDetailService;
    @Autowired
    private TimetableAppointmentService timetableAppointmentService;
    @Autowired
    private TimetableProgressSchedulingService timetableProgressSchedulingService;
    @Autowired
    private LabRoomService labRoomService;
    @Autowired
    private CDictionaryService cDictionaryService;
    /********************************************************************************
     * Description: 排课-排课首页{左侧选课组列表显示}
     * @author: 戴昊宇
     * @date: 2017-08-25
     *********************************************************************************/
    @SuppressWarnings("unchecked")
    @RequestMapping("/timetable/listCourseDetails")
    public ModelAndView listCourseDetails(HttpServletRequest request, @RequestParam int currpage,
                                          @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 获取登录用户信息
        mav.addObject("user", shareService.getUserDetail());

        // 查询条件
        // 所有学期
        List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
        mav.addObject("schoolTerms", schoolTerms);
        // 当前学期
        int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
        if (request.getParameter("term") != null&&!"".equals(request.getParameter("term"))) {
            term = Integer.parseInt(request.getParameter("term"));
        }
        // 所选学期
        mav.addObject("term", term);
        // 教师
        String teacherUsername = request.getParameter("teacher");
        if (!EmptyUtil.isStringEmpty(teacherUsername)) {
            mav.addObject("teacherUsername", teacherUsername);
        }
        // 课程编号
        String courseNo = request.getParameter("courseNo");
        //	if (!EmptyUtil.isStringEmpty(courseNo)) {
        if (!EmptyUtil.isStringEmpty(courseNo)) {
            mav.addObject("courseNo", courseNo);
        }

        // 所有选课组
        mav.addObject("courseDetailAllCourse", mySQLService.listCourseDetailsQuery());
        //教师下拉框
        mav.addObject("courseDetailAll", mySQLService.listCourseDetailsFortearch(request,acno, 1, -1));
        // 分页
        // 设置分页变量并赋值为20；
        int pageSize = CommonConstantInterface.INT_PAGESIZE;
        // 根据课程及id获取课程排课列表的计数
        int totalRecords = mySQLService.countCourseDetails(request, acno);
        // 分页模块映射
        Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", currpage);

        // 选课组列表呈现
        List<Object[]> schoolCourseDetails = mySQLService.listCourseDetails(request,acno, currpage, pageSize);
        mav.addObject("courseDetailPage", schoolCourseDetails);
        mav.setViewName("timetable/progressScheduling/listCourseDetails.jsp");
        return mav;
    }

    /********************************************************************************
     * Description: 欧亚排课-排课首页{右侧：教务实验课}
     * @author: 戴昊宇
     * @date: 2017-08-25
     *********************************************************************************/
    @RequestMapping(value="/timetable/doAdminTimetable/{courseNo:.+}/{courseCode:.+}")
    public ModelAndView doAdminTimetable(@PathVariable String courseNo,@PathVariable String courseCode){
        ModelAndView mav = new ModelAndView();
        // 根据编号找到对应教务课程
        SchoolCourse course = schoolCourseService.findSchoolCourseByPrimaryKey(courseNo);
        // 获取相应的课程库
        SchoolCourseInfo courseInfo = course.getSchoolCourseInfo();
        String courseNumber = courseInfo.getCourseNumber();
        mav.addObject("courseNumber", courseNumber);
        mav.addObject("courseInfo", courseInfo);
        //负责老师
        String teacherCName = schoolCourseService.findSchoolCourseByPrimaryKey(courseNo).getUserByTeacher().getCname();
        mav.addObject("teacherCName", teacherCName);
        //通过编号找到对应教学大纲
        List<OperationOutline> operationOutline = operationService.getoperationOutlinebyclass(courseNumber);
        if (operationOutline!=null && operationOutline.size()>0) {
            BigDecimal totalhour = operationOutline.get(0).getTheoryCourseHour().add(operationOutline.get(0).getExperimentCourseHour());
            mav.addObject("totalhour", totalhour);
            Set<OperationOutlineCourse> outlineCourses = operationOutline.get(0).getOperationOutlineCourses();
            mav.addObject("outlineCourses", outlineCourses);
        }
        // 根据选课组编号获取教务排课信息
        List<SchoolCourseDetail> schoolCourseDetails = schoolCourseDetailService.getSchoolCourseDetailByCourseCode(courseCode);
        //用于判断实验室信息，列表显示通过下面的VOList
        mav.addObject("schoolCourseDetail", schoolCourseDetails.get(0));
        // 对教务排课信息进行排序处理
        List<DoAdminTimetableDTO> doAdminTimetableDTOList=new java.util.ArrayList<DoAdminTimetableDTO>();
        DoAdminTimetableDTO doAdminTimetableDTO=null;
        for (SchoolCourseDetail scd : schoolCourseDetails) {
            for(int i=scd.getStartWeek();i<=scd.getEndWeek();i++){
                doAdminTimetableDTO=new DoAdminTimetableDTO();
                doAdminTimetableDTO.setdATWeekday(scd.getWeekday());
                doAdminTimetableDTO.setdATStartClass(scd.getStartClass());
                doAdminTimetableDTO.setdATEndClass(scd.getEndClass());
                doAdminTimetableDTO.setdATWeek(i);
                doAdminTimetableDTOList.add(doAdminTimetableDTO);
            }
        }
        Collections.sort(doAdminTimetableDTOList);
        mav.addObject("doAdminTimetableDTOList", doAdminTimetableDTOList);
        // 定义空字符串用于存放课程安排
       /* String coursePlan = "";
        for (SchoolCourseDetail schoolCourseDetail : schoolCourseDetails) {
            coursePlan += "星期"+schoolCourseDetail.getWeekday()+" "+schoolCourseDetail.getStartClass()+"-"
                    +schoolCourseDetail.getEndClass()+" 【"+schoolCourseDetail.getStartWeek()+"-"+schoolCourseDetail.getEndWeek()
                    +"】<br>";
        }
        // 去掉最后一个<br>
        if(coursePlan.length()>4){
            coursePlan = coursePlan.substring(0, coursePlan.length()-4);
            mav.addObject("coursePlan", coursePlan);
        }*/
        // 已排课程
        List<TimetableAppointment> appointments = timetableProgressSchedulingService.findTimetableAppointmentByCourseCode(courseCode);
        mav.addObject("appointments", appointments);

        // 课程日历
        List<SchoolWeek> schoolWeeks = timetableProgressSchedulingService.findSchoolWeekByCourseCode(courseCode);
        mav.addObject("schoolWeeks", schoolWeeks);

        // 获取去重的实验分室室列表
        mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap("-1"));
        // 获取可选的教师列表列表
        mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap());
        // 获取可选的实验项目列表列表
        mav.addObject("timetableItem", operationService.
                findOperationItemByCourseNumber(courseNumber));
        /*// 获取登录用户信息
        mav.addObject("user", shareService.getUserDetail());*/
        //获取课程性质列表
        List<CDictionary> curriculumNature = cDictionaryService.findCurriculumNature();
        mav.addObject("curriculumNature", cDictionaryService.findCurriculumNature());
        // 获取该选课组的所属学期
        mav.addObject("term", course.getSchoolTerm().getId());
        // 选课组编号映射
        mav.addObject("courseno", courseNo);
        mav.addObject("courseNo", "'"+courseNo+"'");

        //获得学期周次
        List<Integer> inte = new ArrayList();
        for (SchoolCourseDetail scd : schoolCourseDetails) {
            SchoolTerm schoolTerm = scd.getSchoolTerm();
            for (SchoolWeek slw : schoolTerm.getSchoolWeeks()) {
                if (!inte.contains(slw.getWeek())) {
                    inte.add(slw.getWeek());
                }
            }
        }
        // 周次排序
        Collections.sort(inte);
        mav.addObject("schoolweek", inte);
        mav.setViewName("timetable/progressScheduling/doAdminTimetable.jsp");
        return mav;
    }

    /********************************************************************************
     * Description: 欧亚排课-排课一级页面{右侧：保存教务排课}
     * @param: isOther是否为教务安排的时间段  1-->是   0/null-->否
     * @author: 贺子龙
     * @date: 2016-08-31
     *********************************************************************************/
    @ResponseBody
    @RequestMapping("/timetable/saveSchoolTimetable")
    public String saveSchoolTimetable(@RequestParam int term, String classes, String labRooms, int weekday,
                                      String weeks, String teachers, String courseNo, Integer isOther,String teachers2,
                                      String content,Integer classType, String courseCopy) {
        // 将字符串转化为int型的数组
        int[] classArray = ConvertUtil.stringToIntArray(classes);
        int[] labRoomArray = ConvertUtil.stringToIntArray(labRooms);
        //int[] itemArray = ConvertUtil.stringToIntArray(items);
        int[] weekArray = ConvertUtil.stringToIntArray(weeks);
        if(EmptyUtil.isStringEmpty(labRooms) || EmptyUtil.isStringEmpty(teachers))
            return "empty";// 空值


        TimetableAppointment appointment = timetableProgressSchedulingService.saveSchoolTimetable(term, classArray, labRoomArray, weekArray,
                weekday, teachers, courseNo, isOther, teachers2, content, classType);

        // 把排课结果复制到相应的教学班下
        if (!EmptyUtil.isStringEmpty(courseCopy)) {
            String[] courseCopyArray = courseCopy.split(",");
            if (courseCopyArray.length>0) {
                for (String courseCopyNo : courseCopyArray) {
                    // 此处要注意，因为保存时也加了判冲，所以通过复制途径进入的保存逻辑，应该通过标志来实现不判冲
                    timetableProgressSchedulingService.saveSchoolTimetable(term, classArray, labRoomArray, weekArray,
                            weekday, teachers, courseCopyNo+"isCopy", isOther, teachers2, content, classType);
                }
            }
        }
        if(!EmptyUtil.isObjectEmpty(appointment)){
        if (appointment.getId()!=null) {// 保存成功
            return appointment.getId()+"";
        }else {
            return "-1";
        }}else {
            return "-1";
        }
    }

    /**************************************************************************
     * @description 获取要删除的排课记录对应的td的id
     * @author：戴昊宇
     * @date：2017-08-26
     ***************************************************************************/
    @ResponseBody
    @RequestMapping("/timetable/deleteAppointmentAjaxAndReturn")
    public Map<String, Object> deleteAppointmentAjaxAndReturn(@RequestParam int id) {
        Map<String, Object> map = new HashMap<String,Object>();
        TimetableAppointment t = timetableAppointmentService.findTimetableAppointmentByPrimaryKey(id);
        Set<Integer> classes = new HashSet<Integer>();
        for(int i = t.getStartClass(); i <= t.getEndClass(); i++){
            classes.add(i);
        }
        String week = t.getStartWeek().toString();
        String weekday = t.getWeekday().toString();
        Set<TimetableAppointmentSameNumber> sameNumbers = t.getTimetableAppointmentSameNumbers();
        if(sameNumbers != null && sameNumbers.size() > 0){
            for(TimetableAppointmentSameNumber s:sameNumbers){
                for(int i = s.getStartClass(); i <= s.getEndClass(); i++){
                    classes.add(i);
                }
            }
        }
        List<String> ids = new LinkedList<String>();
        for(Integer c:classes){
            ids.add(week+weekday+c.toString());
        }
        // 排课管理的删除排课条目
        timetableAppointmentService.deleteAppointment(id);
        // 删除排课相关的实验室禁用记录
        labRoomService.deleteLabRoomLimitTimeByAppointment(id);
        map.put("id",id);
        map.put("ids", ids);
        return map;
    }
}
