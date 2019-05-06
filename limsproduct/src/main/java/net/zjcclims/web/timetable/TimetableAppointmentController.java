package net.zjcclims.web.timetable;

import api.net.gvsunlims.constant.ConstantInterface;
import flex.messaging.io.ArrayList;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.ConvertUtil;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.operation.OperationService;
import net.zjcclims.service.timetable.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/********************************************************************************
 * Description: 欧亚排课模块-整体{新样式需要所产生的controller，逻辑没有大的变化}
 * @author: 贺子龙
 * @date: 2016-08-05
 *********************************************************************************/

@Controller("TimetableAppointmentController")
@SessionAttributes("selected_academy")
public class TimetableAppointmentController <JsonResult> {

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
    private SchoolCourseDAO schoolCourseDAO;
    @Autowired
    private SchoolCourseDetailService schoolCourseDetailService;
    @Autowired
    private TimetableAppointmentService timetableAppointmentService;
    @Autowired
    private OuterApplicationService outerApplicationService;
    @Autowired
    private OperationService operationService;
    @Autowired
    private ShareService shareService;
    @Autowired
    private TimetableBatchService timetableBatchService;
    @Autowired
    private TimetableAppointmentSaveService timetableAppointmentSaveService;
    @Autowired
    private TimetableBatchDAO timetableBatchDAO;
    @Autowired
    private SchoolCourseStudentService schoolCourseStudentService;
    @Autowired
    private TimetableGroupStudentsDAO timetableGroupStudentsDAO;
    @Autowired
    private OuterApplicationServiceImpl outerApplicationServiceImpl;
    @Autowired
    private TimetableGroupStudentsService timetableGroupStudentsService;
    @Autowired
    private TimetableGroupDAO timetableGroupDAO;
    @Autowired
    private TimetableGroupsService timetableGroupsService;
    @Autowired
    private TimetableAppointmentDAO timetableAppointmentDAO;
    @Autowired
    private TimetableAppointmentCycleDAO timetableAppointmentCycleDAO;
    @Autowired
    private CommonDocumentDAO commonDocumentDAO;

    /********************************************************************************
     * Description: 欧亚排课-排课首页{右侧：教务实验课}
     * @author: 戴昊宇
     * @date: 2017-08-25
     ***********************************11**********************************************/
    @RequestMapping(value="/timetable/doAdminTimetable/{courseNo}")
    public ModelAndView doAdminTimetable(@PathVariable String courseNo, @ModelAttribute("selected_academy") String acno){
        ModelAndView mav = new ModelAndView();
        // 根据编号找到对应教务课程
        SchoolCourse course = schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo);
        mav.addObject("courseNumber", course.getSchoolCourseInfo().getCourseNumber());
        mav.addObject("courseInfo", course.getSchoolCourseInfo());

        // 根据选课组编号获取教务排课信息
        List<SchoolCourseDetail> schoolCourseDetails = schoolCourseDetailService.getSchoolCourseDetailByCourseCode(courseNo);
        mav.addObject("schoolCourseDetails", schoolCourseDetails);
		/*// 用于查找到班级信息
		for (SchoolCourseDetail schoolCourseDetail : schoolCourseDetails) {
			List<SchoolCourseStudent> scl=new ArrayList<SchoolCourseStudent>();
			Set<SchoolCourseStudent> schoolCourseStudents = schoolCourseDetail.getSchoolCourseStudents();
			for()

		}*/
        // 定义空字符串用于存放课程安排
        String coursePlan = "";
        for (SchoolCourseDetail schoolCourseDetail : schoolCourseDetails) {
            coursePlan += "星期"+schoolCourseDetail.getWeekday()+" "+schoolCourseDetail.getStartClass()+"-"
                    +schoolCourseDetail.getEndClass()+" 【"+schoolCourseDetail.getStartWeek()+"-"+schoolCourseDetail.getEndWeek()
                    +"】<br>";
        }
        // 去掉最后一个<br>
        coursePlan = coursePlan.substring(0, coursePlan.length()-4);
        mav.addObject("coursePlan", coursePlan);
        // 已排课程
        List<TimetableAppointment> appointments = timetableAppointmentService.findTimetableAppointmentByCourseCode(courseNo);
        mav.addObject("appointments", appointments);

        // 获取去重的实验分室室列表
        mav.addObject("labRoomMap", outerApplicationService.getLabRoomNumberList(acno));
        // 获取可选的教师列表列表
        mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap());
        // 获取可选的实验项目列表列表
        mav.addObject("timetableItem", operationService.
                findOperationItemByCourseNumber(course.getSchoolCourseInfo().getCourseNumber()));
        // 获取登录用户信息
        mav.addObject("user", shareService.getUserDetail());
        // 获取该选课组的所属学期
        mav.addObject("term", course.getSchoolTerm().getId());
        // 选课组编号映射
        mav.addObject("courseNo", "'"+courseNo+"'");
        mav.addObject("courseno", courseNo);
        mav.setViewName("timetable/special/doAdminTimetable.jsp");
        return mav;
    }

    /********************************************************************************
     * Description: 欧亚排课-排课二级页面{左侧：分批排课的分组列表，非自主排课}
     *
     * @author: 贺子龙
     * @date: 2016-08-26
     *********************************************************************************/
    @RequestMapping("/timetable/listTimetableGroupAdmin")
    public ModelAndView listTimetableGroupAdmin(@RequestParam String courseNo) {
        ModelAndView mav = new ModelAndView();
        // 根据编号找到对应教务课程
        SchoolCourse course = schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo);
        mav.addObject("courseNumber", course.getSchoolCourseInfo().getCourseNumber());
        mav.addObject("courseInfo", course.getSchoolCourseInfo());

        // 根据选课组编号获取教务排课信息
        List<SchoolCourseDetail> schoolCourseDetails = schoolCourseDetailService.getSchoolCourseDetailByCourseCode(courseNo);
        mav.addObject("schoolCourseDetails", schoolCourseDetails);
        // 定义空字符串用于存放课程安排
        String coursePlan = "";
        for (SchoolCourseDetail schoolCourseDetail : schoolCourseDetails) {
            coursePlan += "星期"+schoolCourseDetail.getWeekday()+" "+schoolCourseDetail.getStartClass()+"-"
                    +schoolCourseDetail.getEndClass()+" 【"+schoolCourseDetail.getStartWeek()+"-"+schoolCourseDetail.getEndWeek()
                    +"】<br>";
        }
        // 去掉最后一个<br>
        if (coursePlan.length()>4) {
            coursePlan = coursePlan.substring(0, coursePlan.length()-4);
            mav.addObject("coursePlan", coursePlan);
        }
        // 将选课组编号映射到前台
        mav.addObject("courseNo", courseNo);
        // 找到该选课组下的所有分组，映射到前台
        List<TimetableGroup> timetableGroups = timetableBatchService.findTimetableGroupByCourseCode(courseNo);
        mav.addObject("groups", timetableGroups);
        mav.setViewName("timetable/special/listTimetableGroupAdmin.jsp");
        return mav;
    }

    /********************************************************************************
     * Description: 循环排课（不分批）
     *
     * @author: 戴昊宇
     * @date: 2018-03-04
     *********************************************************************************/
    @RequestMapping("/timetable/listTimetableCycle")
    public ModelAndView listTimetableCycle(@RequestParam String courseNo,String courseCode) {
        ModelAndView mav = new ModelAndView();
        // 根据编号找到对应教务课程
        SchoolCourse course = schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo);
        // 获取相应的课程库
        SchoolCourseInfo courseInfo = course.getSchoolCourseInfo();
        String courseNumber = courseInfo.getCourseNumber();
        mav.addObject("courseNumber", courseNumber);
        mav.addObject("courseInfo", courseInfo);
        // 根据选课组编号获取教务排课信息
        List<SchoolCourseDetail> schoolCourseDetails = schoolCourseDetailService.getSchoolCourseDetailByCourseCode(courseCode);
        mav.addObject("schoolCourseDetails", schoolCourseDetails);
        // 定义空字符串用于存放课程安排
        String coursePlan = "";
        for (SchoolCourseDetail schoolCourseDetail : schoolCourseDetails) {
            coursePlan += "星期"+schoolCourseDetail.getWeekday()+" "+schoolCourseDetail.getStartClass()+"-"
                    +schoolCourseDetail.getEndClass()+" 【"+schoolCourseDetail.getStartWeek()+"-"+schoolCourseDetail.getEndWeek()
                    +"】<br>";
        }
        // 去掉最后一个<br>
        if(coursePlan.length()>4){
            coursePlan = coursePlan.substring(0, coursePlan.length()-4);
            mav.addObject("coursePlan", coursePlan);
        }
        // 循环预存排课
        List<TimetableAppointmentCycle> cycleTimetableBycourseNo = timetableAppointmentSaveService.findCycleTimetableBycourseNo(courseNo);
        mav.addObject("cycleTimetableBycourseNo", cycleTimetableBycourseNo);
        mav.addObject("cyccount",cycleTimetableBycourseNo.size());
        // 循环排课分组
        List<TimetableGroup> timetableGroupBycourseNo = timetableAppointmentSaveService.findTimetableGroupBycourseNo(courseCode);
        mav.addObject("timetableGroupBycourseNo", timetableGroupBycourseNo);
        // 循环已排课程
        // 课程日历
        // 获取去重的实验分室室列表
        mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap("-1"));
        // 获取可选的教师列表列表
        mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap());
        // 获取可选的学生列表列表
        mav.addObject("studentMap", outerApplicationService.getStudentMap());
        // 获取可选的实验项目列表列表
        mav.addObject("timetableItem", operationService.
                findOperationItemByCourseNumber(courseNumber));
        // 选课组编号映射
        mav.addObject("courseNo", "'"+courseNo+"'");
        mav.addObject("courseCode", "'"+courseCode+"'");
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
        //循环不分批排课

        List<TimetableAppointment> istimetable = timetableAppointmentService.findCycleTimetableAppointmentByCourseNo(courseNo);
        mav.addObject("istimetable", istimetable);
        //获得选择的周次
        Set<Integer> selectedWeeks = new HashSet<Integer>();
        for(TimetableAppointment ist:istimetable){
            selectedWeeks.add(ist.getStartWeek());
        }
        mav.addObject("selectedWeeks", selectedWeeks);
        mav.addObject("groupCount", selectedWeeks.size());
        mav.setViewName("timetable/special/doCycleTimetable.jsp");
        return mav;
    }

    /********************************************************************************
     * Description: 循环排课（分批）
     *
     * @author: 戴昊宇
     * @date: 2018-03-04
     *********************************************************************************/
    @RequestMapping("/timetable/listTimetableBatchCycle")
    public ModelAndView listTimetableBatchCycle(@RequestParam String courseNo,String courseCode) {
        ModelAndView mav = new ModelAndView();
        // 根据编号找到对应教务课程
        SchoolCourse course = schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo);
        // 获取相应的课程库
        SchoolCourseInfo courseInfo = course.getSchoolCourseInfo();
        String courseNumber = courseInfo.getCourseNumber();
        mav.addObject("courseNumber", courseNumber);
        mav.addObject("courseInfo", courseInfo);
        // 根据选课组编号获取教务排课信息
        List<SchoolCourseDetail> schoolCourseDetails = schoolCourseDetailService.getSchoolCourseDetailByCourseCode(courseCode);
        mav.addObject("schoolCourseDetails", schoolCourseDetails);
        // 定义空字符串用于存放课程安排
        String coursePlan = "";
        for (SchoolCourseDetail schoolCourseDetail : schoolCourseDetails) {
            coursePlan += "星期"+schoolCourseDetail.getWeekday()+" "+schoolCourseDetail.getStartClass()+"-"
                    +schoolCourseDetail.getEndClass()+" 【"+schoolCourseDetail.getStartWeek()+"-"+schoolCourseDetail.getEndWeek()
                    +"】<br>";
        }
        // 去掉最后一个<br>
        if(coursePlan.length()>4){
            coursePlan = coursePlan.substring(0, coursePlan.length()-4);
            mav.addObject("coursePlan", coursePlan);
        }
        // 循环预存排课
        List<TimetableAppointmentCycle> cycleTimetableBycourseNo = timetableAppointmentSaveService.findCycleTimetableBycourseNo(courseNo);
        mav.addObject("cycleTimetableBycourseNo", cycleTimetableBycourseNo);
        mav.addObject("cyccount",cycleTimetableBycourseNo.size());
        // 循环排课分组
        List<TimetableGroup> timetableGroupBycourseNo = timetableAppointmentSaveService.findTimetableBatchGroupBycourseNo(courseCode);
        mav.addObject("timetableGroupBycourseNo", timetableGroupBycourseNo);
        // 循环已排课程
        // 课程日历
        // 获取去重的实验分室室列表
        mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap("-1"));
        // 获取可选的教师列表列表
        mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap());
        // 获取可选的实验项目列表列表
        mav.addObject("timetableItem", operationService.
                findOperationItemByCourseNumber(courseNumber));
        // 选课组编号映射
        mav.addObject("courseNo", "'"+courseNo+"'");
        mav.addObject("courseCode", "'"+courseCode+"'");
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
        List<TimetableAppointment> istimetable = timetableAppointmentService.findCycleBatchTimetableAppointmentByCourseNo(courseNo);
        mav.addObject("istimetable", istimetable);
        // 获取可选的学生列表列表
        mav.addObject("studentMap", outerApplicationService.getStudentMap());
        //获得选择的周次
        Set<Integer> selectedWeeks = new HashSet<Integer>();
        for(TimetableAppointment ist:istimetable){
            selectedWeeks.add(ist.getStartWeek());
        }
        mav.addObject("selectedWeeks", selectedWeeks);
        mav.setViewName("timetable/special/doCycleBatchTimetable.jsp");
        return mav;
    }

    /********************************************************************************
     * Description: 排课-排课二级页面{查看选课组学生}
     *
     * @author: 戴昊宇
     * @date: 2017-08-26
     *********************************************************************************/
    @RequestMapping("/timetable/viewCourseStudent")
    public ModelAndView viewCourseStudent(@RequestParam String courseCode) {
        ModelAndView mav = new ModelAndView();
        // 通过courseCode查找学生
        mav.addObject("studentMap", timetableBatchService.findSchoolCourseStudentByCourseCode(courseCode));
        mav.setViewName("timetable/special/listCourseStudent.jsp");
        return mav;
    }

    /********************************************************************************
     * Description: 欧亚排课-排课二级页面{右侧：新建分批}
     * @param: isSelf 0 非自主排课 1 自主排课
     * @author: 贺子龙
     * @date: 2016-08-26
     *********************************************************************************/
    @RequestMapping(value="/timetable/newTimetableBatch/{courseCode}/{batchId}/{isSelf}")
    public ModelAndView newTimetableBatch(@PathVariable String courseCode, @PathVariable int batchId,
                                          @PathVariable Integer isSelf){
        ModelAndView mav = new ModelAndView();
        // 创建一个对象并映射到前台
        if (batchId==-1) {// 新建
            mav.addObject("timetableBatch", new TimetableBatch());
        }else {// 编辑
            mav.addObject("timetableBatch", timetableBatchDAO.findTimetableBatchByPrimaryKey(batchId));
        }
        // 将选课组编号映射到前台
        mav.addObject("courseCode", courseCode);
        // 自建选课组标志位
        mav.addObject("isSelf", isSelf);
        mav.setViewName("timetable/special/newTimetableBatch.jsp");
        return mav;
    }

    /********************************************************************************
     * Description: 欧亚排课-排课二级页面{右侧：保存分批}
     *
     * @author: 贺子龙
     * @date: 2016-08-27
     *********************************************************************************/
    @ResponseBody
    @RequestMapping("/timetable/saveTimetableBatch")
    public String saveTimetableBatch(@ModelAttribute TimetableBatch timetableBatch,
                                     HttpServletRequest request, @RequestParam int isSelf) {
        // 用request来获取前台的选课组编号
        String courseCode = request.getParameter("courseCode");
        // 设置分批的选课组编号，弱关联
        timetableBatch.setCourseCode(courseCode);
        // 保存分批
        timetableBatchService.saveTimetableBatch(timetableBatch, isSelf);
        return "success";
    }

    /***********************************************************************************************
     * Description：排课保存循环排课组
     * @author：戴昊宇
     * @Date：2018-03-2
     ***********************************************************************************************/
    @ResponseBody
    @RequestMapping("/timetable/saveCycleGroup")
    public List<CycleGroup> saveCycleGroup(@RequestParam  String courseCode, String courseNo, int batchCount) {
        int cycleTimetable = timetableAppointmentSaveService.findCycleTimetable(courseNo);
        List<TimetableBatch> timetableBatchList = timetableBatchService.getTimetableBatchByCourseCode(courseNo);
        //判断是否已经有分组
        if(timetableBatchList.size()<=0){
            //新建分批
            TimetableBatch timetableBatch = new TimetableBatch();
            timetableBatch.setCourseCode(courseCode);
            timetableBatch.setBatchName("第"+batchCount+"批");
            //批次内的分组数为预存排课数
            timetableBatch.setCountGroup(cycleTimetable);
            //保存分批 3为循环排课分组
            timetableBatch.setIfselect(3);
            timetableBatch.setStartDate(new GregorianCalendar());
            timetableBatch.setEndDate(new GregorianCalendar());
            Integer batchId = timetableBatchService.saveTimetableBatch(timetableBatch,3);
            if (batchId!=null) {// 保存成功
                //获得批次下的组
                List<TimetableGroup> groupsByBatchId = timetableBatchService.findTimetableGroupsByBatchId(batchId);
                List<SchoolCourseStudent> schoolCourseStudentList = schoolCourseStudentService.getSchoolCousrseStudnetByCourseNo(courseNo);
                int count =0;
                List<CycleGroup> listgroup = new ArrayList();
                int start = 0;
                for(TimetableGroup group:groupsByBatchId ){
                    count=count+1;
                    CycleGroup cyclegroup= new CycleGroup();
                    cyclegroup.setCount(count);
                    cyclegroup.setGroupId(group.getId());
                    cyclegroup.setGroupName(group.getGroupName());
                    cyclegroup.setGroupNumbers(group.getNumbers());
                    listgroup.add(cyclegroup);

                    for(int i=0;i < group.getNumbers();i++){
                        TimetableGroupStudents timetableGroupStudents = new TimetableGroupStudents();
                        timetableGroupStudents.setUser(schoolCourseStudentList.get(i+start).getUserByStudentNumber());
                        timetableGroupStudents.setTimetableGroup(group);
                        timetableGroupStudentsDAO.store(timetableGroupStudents);
                    }
                    start+=group.getNumbers();
                }

                return listgroup;
            }else {
                return null;
            }
        }

        return null;
    }

    /********************************************************************************
     * Description: 循环排课保存
     * @author: 戴昊宇
     * @date: 2018-03-05
     *********************************************************************************/
    @ResponseBody
    @RequestMapping("/timetable/saveCycleTimetableAppointment")
    public String saveCycleTimetableAppointment(@RequestParam Integer[] weeks,String courseNo,String courseCode,int flag) {
        // 获得选课组信息
        SchoolCourse course = schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo);
        Set<SchoolCourseDetail> schoolCourseDetails = course.getSchoolCourseDetails();
        // 获得课程信息（暂时认为选课组下就一门课）
        SchoolCourseDetail courseDetail = schoolCourseDetails.iterator().next();
        // 获得预存排课信息
        List<TimetableAppointmentCycle> timetableBycourseNo = timetableAppointmentSaveService.findCycleTimetableBycourseNo(courseNo);

        List<TimetableGroup> groupBycourseNo = new ArrayList();
        if(flag==1){
            // 获得批次与组信息（循环不分批排课）
            groupBycourseNo = timetableAppointmentSaveService.findTimetableGroupBycourseNo(courseCode);
        }
        if(flag==2){
            // 获得批次与组信息（循环分批排课）
            groupBycourseNo = timetableAppointmentSaveService.findTimetableBatchGroupBycourseNo(courseCode);
        }
        // 定义每行每组
        Map<Integer,int[]> groupList = new HashMap<Integer, int[]>();
        for(int i=0;i<weeks.length;i++){
            int[] groupIds=new int[weeks.length];
            int j=i;
            int k=0;
            for(TimetableGroup group:groupBycourseNo){
                if(j<weeks.length){
                    groupIds[j]=group.getId();
                }
                if(j>=weeks.length&&i!=0){
                    groupIds[k]=group.getId();
                    k++;
                }
                j++;
            }
            groupList.put(i, groupIds);
        }
        //  将项目上课地点与上课周数据装入list
        List<CycleTimetable> cycletimetable = new ArrayList();
        for(int i=0;i<weeks.length;i++){
            for(TimetableAppointmentCycle ta:timetableBycourseNo){
                CycleTimetable ct = new CycleTimetable();
                ct.setLabRoomId(ta.getLabRoom().getId());
                ct.setOperationItem(ta.getOperationItem().getId());
                ct.setTeacher(ta.getUserByTeacher().getUsername());
                ct.setTutor(ta.getUserByTutor().getUsername());
                ct.setWeek(weeks[i]);
                cycletimetable.add(ct);
            }
        }
        // 获取课程时间
        int term = courseDetail.getSchoolTerm().getId();
        // 获取节次
        int startclass = courseDetail.getStartClass();
        int totalclass= courseDetail.getTotalClasses();
        int[] classes = new  int[totalclass];
        for(int n=0;n<totalclass;n++){
            classes[n]+=startclass;
            startclass++;
        }
        // 获取所选实验室
        String labs="";
        // 转换成String类型数组
        String[] labRooms = new String[cycletimetable.size()];
        for(CycleTimetable cycletime:cycletimetable){
            labs+=cycletime.getLabRoomId()+",";
        }
        labs = labs.substring(0,labs.length()-1);
        labRooms = labs.split(",");
        // 转换成int类型数组
        int[] labrooms =  new int[cycletimetable.size()];
        for(int m=0;m<cycletimetable.size();m++){
            labrooms[m]+=Integer.parseInt(labRooms[m]);
        }
        int weekday=courseDetail.getWeekday();
        // 判冲所选时间实验室是是否被占用
        Integer[] validWeeks = outerApplicationServiceImpl.getValidWeeks(term, classes, labrooms, weekday, 0);
        // 转换为list
        List<Integer> listValidweeks = Arrays.asList(validWeeks);
        List<Integer> listWeek = Arrays.asList(weeks);
        // 判断可用周次是否符合
        boolean valid = listValidweeks.containsAll(listWeek);
        if(valid=true){
            // 循环排课保存
            // 通过预存选课的数量，循环取出组的id
            int i=0;
            int j=0;
            for(CycleTimetable cyt:cycletimetable){
                int groupId=0;
                if(i<weeks.length){
                    groupId=groupList.get(j)[i];
                    i++;
                }else{
                    j++;
                    i=0;
                    groupId=groupList.get(j)[i];
                    i++;
                }
                timetableAppointmentSaveService
                        .saveCycleTimetableAppointment(term, classes,
                                cyt.getLabRoomId(), cyt.getWeek(), weekday,
                                cyt.getOperationItem(), cyt.getTeacher(),
                                courseNo, groupId, cyt.getTutor(),flag);
            }
            return "success";
        }else{
            return "-1";
        }

    }

    /********************************************************************************
     * Description: 循环排课预存
     * @author: 戴昊宇
     * @date: 2016-08-31
     *********************************************************************************/
    @ResponseBody
    @RequestMapping("/timetable/saveCycleTimetable")
    public String saveCycleTimetable(@RequestParam int labRoom,
                                     int item, String teachers, String courseNo,  String teachers2) {
        TimetableAppointmentCycle saveCycleTimetable = timetableAppointmentSaveService.saveCycleTimetable(labRoom, item, teachers,
                teachers2,courseNo);
        if (saveCycleTimetable.getLabRoom()!=null) {// 保存成功
            return String.valueOf(item);
        }else {
            return "-1";
        }

    }

    /********************************************************************************
     * Description: 循环排课查看每组学生名单
     * @author:  麦凯俊
     * @param flag（为1时查询不属于原来组的名单，否则查询原来组的名单）
     * @date: 2018-8-7
     *********************************************************************************/
    @RequestMapping("/timetable/viewGroupStudent")
    public ModelAndView viewGroupStudent(int groupId,int flag) {
        ModelAndView mav = new ModelAndView();
        if(flag == 1){
            List<TimetableGroupStudents> groupStudentsList = timetableGroupStudentsService.findElseGroupStudents(groupId);
            TimetableGroup group = timetableGroupDAO.findTimetableGroupById(groupId);
            mav.addObject("groupStudents",groupStudentsList);
            mav.addObject("groupId",groupId);
            mav.setViewName("timetable/schedulingcourse/addListGroupStudent.jsp");
        }else{
            String sql = "select c from TimetableGroupStudents c where c.timetableGroup.id = "+groupId;
            List<TimetableGroupStudents> groupStudentsList = timetableGroupStudentsDAO.executeQuery(sql,-1,-1);
            mav.addObject("groupStudents",groupStudentsList);
            mav.addObject("groupId",groupId);
            mav.setViewName("timetable/schedulingcourse/listGroupStudent.jsp");
        }

        return mav;
    }

    /********************************************************************************
     * Description: 循环排课查看每组学生名单
     * @author:  麦凯俊
     * @date: 2018-8-7
     *********************************************************************************/
    @RequestMapping("/timetable/addGroupStudent")
    @ResponseBody
    public void addGroupStudent(@RequestParam(value = "groupIds[]")  String[]  groupIds,@RequestParam(value = "groupId")Integer groupId) {

        timetableGroupsService.addGroupStudent(groupIds,groupId);
		/*for(String s:groupIds){
			TimetableGroupStudents stu = timetableGroupStudentsService.findTimetableGroupStudentsByGroupId(s);
			//减去组的人数
			TimetableGroup group_sub = stu.getTimetableGroup();
			group_sub.setNumbers(group_sub.getNumbers()-1);
			timetableGroupDAO.store(group_sub);

			//增加组的人数
			TimetableGroup group_add = timetableGroupsService.findTimetableGroupByGroupNum(groupId);
			group_add.setNumbers(group_add.getNumbers()+1);
			timetableGroupDAO.store(group_add);

			stu.setTimetableGroup(group_add);
			timetableGroupStudentsDAO.store(stu);
		}*/
    }


    /********************************************************************************
     * Description: 欧亚排课-排课二级页面{左侧：查看分组学生}
     *
     * @author: 贺子龙
     * @date: 2016-08-27
     *********************************************************************************/
    @RequestMapping("/timetable/viewCourseGroupStudent")
    public ModelAndView viewCourseGroupStudent(@RequestParam int id) {
        ModelAndView mav = new ModelAndView();
        // 找到对应的分组·
        TimetableGroup currgroup=timetableGroupDAO.findTimetableGroupByPrimaryKey(id);
        // 分组对应的分批id
        int batchId=currgroup.getTimetableBatch().getId();//获取批次id
        // 所有同分批的分组
        mav.addObject("groups", timetableBatchService.findTimetableGroupsByBatchId(batchId));
        // 该分组下的学生
        mav.addObject("studentMap",currgroup.getTimetableGroupStudentses());
        //jsp modelAttribute
        mav.addObject("admin",new LabRoomAdmin());
        // id映射
        mav.addObject("id",id);
        // 页面跳转
        mav.setViewName("timetable/special/listCourseStudentGroup.jsp");
        return mav;
    }

    /***********************************************************************************************
     * Description：排课保存分批循环排课分组
     * @author：戴昊宇
     * @Date：2018-03-2
     ***********************************************************************************************/
    @RequestMapping("/timetable/saveBatchCycleGroup")
    public String saveBatchCycleGroup(@RequestParam  String courseCode,String courseNo,int batchCount) {
        // 根据教师填的分批数量进行循环
        for (int k = 1; k <= batchCount; k++) {
            //每个分批的组数
            int cycleTimetable = timetableAppointmentSaveService
                    .findCycleTimetable(courseNo);
            int averageCycle= cycleTimetable/batchCount;
            // 新建分批
            TimetableBatch timetableBatch = new TimetableBatch();
            timetableBatch.setCourseCode(courseCode);
            timetableBatch.setBatchName("第" + k + "批");
            if(k!=batchCount){
                // 批次内的分组数为预存排课数
                timetableBatch.setCountGroup(averageCycle);
            }else{
                //最后一批放剩下的组
                timetableBatch.setCountGroup(cycleTimetable-averageCycle*(batchCount-1));
            }
            // 保存分批 4为分批循环排课分组
            timetableBatch.setIfselect(4);
            // 保存分批
            timetableBatch = timetableBatchDAO.store(timetableBatch);
            timetableBatchDAO.flush();
            // 生成分组
            // 确定选课组
            String batchcourseCode = timetableBatch.getCourseCode();
            // 选课下的学生人数
            int studentNumber = 0;
            // 找到选课组下的所有学生
            List<SchoolCourseStudent> students = timetableBatchService
                    .findSchoolCourseStudentByCourseCode(batchcourseCode);
            studentNumber = students.size();
            int batchStudentNumber = studentNumber / batchCount;
            // 批次下面的分组数量
            int groupNumber = timetableBatch.getCountGroup();
            // 平均数
            int average = batchStudentNumber / groupNumber;
            // 循环建立分组
            TimetableGroup group = new TimetableGroup();
            for (int i = 1; i < groupNumber + 1; i++) {
                // 名称
                group.setGroupName("第" + i + "组");
                // 分批外键
                group.setTimetableBatch(timetableBatch);
                // 设置每个分组里面学生的数量
                if (i != groupNumber) {
                    group.setNumbers(average);
                } else {
                    // 最后一组放剩下的学生
                    group.setNumbers(studentNumber - average
                            * (groupNumber - 1));
                }
                // 保存分组
                timetableGroupDAO.store(group);
                timetableGroupDAO.flush();
            }
        }
        return "redirect:/timetable/listTimetableBatchCycle?courseNo="+courseNo+"&courseCode="+courseCode;

	/*	if (batchId!=null) {// 保存成功
			//获得批次下的组
			List<TimetableGroup> groupsByBatchId = timetableBatchService.findTimetableGroupsByBatchId(batchId);
			int count =0;
			List<CycleGroup> listgroup = new ArrayList();
			for(TimetableGroup group:groupsByBatchId ){
				count=count+1;
				CycleGroup cyclegroup= new CycleGroup();
				cyclegroup.setCount(count);
				cyclegroup.setGroupId(group.getId());
				cyclegroup.setGroupName(group.getGroupName());
				cyclegroup.setGroupNumbers(group.getNumbers());
				listgroup.add(cyclegroup);
			}

			return listgroup;
		}else {
			return null;
		}*/

    }

    /********************************************************************************
     * Description: 欧亚排课-排课首页{右侧：分组排课}
     * @param isAdmin 0-->教务分组   1-->自主分组
     * @author: 贺子龙
     * @date: 2016-08-05
     *********************************************************************************/
    @RequestMapping(value="/timetable/doGroupTimetable/{courseNo}/{groupId}/{isAdmin}")
    public ModelAndView doGroupTimetable(@PathVariable String courseNo, @PathVariable int groupId, @PathVariable int isAdmin,
                                         @ModelAttribute("selected_academy") String acno){
        ModelAndView mav = new ModelAndView();

        if (isAdmin == 1) {// 教务排课
            // 根据编号找到对应教务课程
            SchoolCourse course = schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo);
            mav.addObject("courseNumber", course.getSchoolCourseInfo().getCourseNumber());
            mav.addObject("courseInfo", course.getSchoolCourseInfo());
            // 获取可选的实验项目列表列表
            mav.addObject("timetableItem", operationService.
                    findOperationItemByCourseNumber(course.getSchoolCourseInfo().getCourseNumber()));
            // 课程所属学期
            mav.addObject("term", course.getSchoolTerm().getId());
            // 获取相应的课程库
            SchoolCourseInfo courseInfo = course.getSchoolCourseInfo();
            String courseNumber = courseInfo.getCourseNumber();
            mav.addObject("courseNumber", courseNumber);
            mav.addObject("courseInfo", courseInfo);
        }
        List<TimetableAppointment> appointments = timetableAppointmentService
                .findTimetableAppointmentByCourseAndGroup(courseNo, groupId);
        mav.addObject("appointments", appointments);

        // 获取去重的实验分室室列表
        mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
        // 获取可选的教师列表列表
        mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap());
        // 获取登录用户信息
        mav.addObject("user", shareService.getUserDetail());
        // 选课组编号
        mav.addObject("courseNo", "'"+courseNo+"'");
        // 分组id
        mav.addObject("groupId", groupId);
        // 是否为教务排课
        mav.addObject("isAdmin", isAdmin);
        mav.setViewName("timetable/special/doGroupTimetable.jsp");
        return mav;
    }

    /**************************************************************************
     * @排课管理的删除排课条目
     * @作者：魏诚
     * @日期：2014-07-25
     ***************************************************************************/
    @ResponseBody
    @RequestMapping("/timetable/deleteAppointmentAjax")
    public String deleteAppointmentAjax(@RequestParam int id) {
        // 排课管理的删除排课条目
        timetableAppointmentService.deleteAppointment(id);
        return "success";
    }

    /************************************************************
     * @循环排课完成查看
     * @作者：戴昊宇
     * @日期：2018-03-07
     ************************************************************/
    @RequestMapping("/timetable/listGeneralTimetable1")
    public ModelAndView labReservationCalendar(@RequestParam  String courseNo,int isBatch) {
        ModelAndView mav = new ModelAndView();
        //定义已排课集合
        List<TimetableAppointment> appointmentByCourseNo = new ArrayList();
        List<TimetableItemRelated>itemRelated=new ArrayList();
        //定义所选周集合
        List<Integer> listWeek = new ArrayList();
        if(isBatch==0){
            //循环不分批排课
            appointmentByCourseNo = timetableAppointmentService.findCycleTimetableAppointmentByCourseNo(courseNo);
        }
        if(isBatch==1){
            //循环分批排课
            appointmentByCourseNo = timetableAppointmentService.findCycleBatchTimetableAppointmentByCourseNo(courseNo);
        }
        for(TimetableAppointment ta :appointmentByCourseNo){
            itemRelated.addAll(ta.getTimetableItemRelateds());
            if(!listWeek.contains(ta.getStartWeek())){
                listWeek.add(ta.getStartWeek());
            }
        }
        //所选周排序
        Collections.sort(listWeek);
        //排课预存信息
        List<TimetableAppointmentCycle> cycleTimetableBycourseNo = timetableAppointmentSaveService.findCycleTimetableBycourseNo(courseNo);
        mav.addObject("itemRelated", itemRelated);
        mav.addObject("cycleTimetableBycourseNo", cycleTimetableBycourseNo);
        mav.addObject("listWeek", listWeek);
        mav.addObject("isBatch", isBatch);
        mav.setViewName("timetable/special/listGeneralTimetable1.jsp");
        return mav;
    }

    /********************************************************************************
     * Description: 欧亚排课-排课一级页面{右侧：保存教务排课}
     * @param: isOther是否为教务安排的时间段  1-->是   0/null-->否
     * @author: 贺子龙
     * @date: 2016-08-31
     *********************************************************************************/
    @ResponseBody
    @RequestMapping("/timetable/saveSchoolTimetable1")
    public String saveSchoolTimetable(@RequestParam int term, String classes, String labRooms, Integer weekday,
                                      String items, String weeks, String teachers, String courseNo, Integer isOther) {
        // 将字符串转化为int型的数组
        int[] classArray = ConvertUtil.stringToIntArray(classes);
        int[] labRoomArray = ConvertUtil.stringToIntArray(labRooms);
        int[] itemArray = ConvertUtil.stringToIntArray(items);
        int[] weekArray = ConvertUtil.stringToIntArray(weeks);
        TimetableAppointment appointment = timetableAppointmentSaveService.saveSchoolTimetable(term, classArray, labRoomArray, weekArray,
                weekday, itemArray, teachers, courseNo, isOther);
        if (appointment.getId()!=null) {// 保存成功
            return appointment.getId()+"";
        }else {
            return "-1";
        }
    }

    /********************************************************************************
    * Description: 欧亚排课-排课一级页面{右侧：保存分组排课}
    * @param isAdmin 0-->教务分组  1-->自主分组
    * @author: 贺子龙
    * @date: 2016-08-31
    *********************************************************************************/
    @ResponseBody
    @RequestMapping("/timetable/saveGroupTimetable")
    public String saveGroupTimetable(@RequestParam int term, String classes, String labRooms, Integer weekday,
                                     String items, String weeks, String teachers, String courseNo, int groupId, Integer isAdmin, String teachers2
    ) {
        // 将字符串转化为int型的数组
        int[] classArray = ConvertUtil.stringToIntArray(classes);
        int[] labRoomArray = ConvertUtil.stringToIntArray(labRooms);
        int[] itemArray = null;
        if (!EmptyUtil.isStringEmpty(items)) {
            itemArray = ConvertUtil.stringToIntArray(items);
        }
        int[] weekArray = ConvertUtil.stringToIntArray(weeks);

        TimetableAppointment appointment = timetableAppointmentSaveService.saveGroupTimetable(term, classArray, labRoomArray, weekArray,
                weekday, itemArray, teachers, courseNo, groupId, isAdmin, teachers2);
        if (appointment.getId()!=null) {// 保存成功
            return appointment.getId()+"";
        }else {
            return "-1";
        }

    }

    /********************************************************************************
     * Description: 排课-排课一级页面{右侧：判断要保存的节次是否连续（避免samenumber）}
     * @author: 戴昊宇
     * @date: 2017-08-29
     *********************************************************************************/
    @ResponseBody
    @RequestMapping("/timetable/judgeClassesIsContinuity")
    public String judgeClassesIsContinuity(String classes) {
        // 将字符串转化为int型的数组
        int[] classArray = ConvertUtil.stringToIntArray(classes);
        Arrays.sort(classArray);
        int len = classArray.length;
        boolean isContinuity = true;
        for(int i = 0; i < len - 1; i++){
            if(classArray[i] + 1 != classArray[i+1]){
                isContinuity = false;
                break;
            }
        }
        if(isContinuity == true){
            return "yes";
        }
        else return "no";
    }

    /**
     * Description 确认排课
     * @param courseNo 选课组课程编号
     * @return 成功与否的字符串
     * @author 黄保钱 2018-9-17
     */
    @RequestMapping("/timetable/submitTimetableAppointment")
    @ResponseBody
    public String submitTimetableAppointment(@RequestParam String courseNo){
        // 获取要确认的排课
        List<TimetableAppointment> timetableAppointmentList = timetableAppointmentService.findAppointmentByCourseCode(courseNo);
        // 如果可以找到，则进入循环
        if(timetableAppointmentList != null && timetableAppointmentList.size() != 0) {
            for (TimetableAppointment t : timetableAppointmentList) {
                t.setStatus(ConstantInterface.TIMETABLE_STATUS_NO_PUBLIC);
                timetableAppointmentDAO.store(t);
                timetableAppointmentDAO.flush();
            }
            return "success";
        }
        // 否则返回确认排课失败
        else{
            return "fail";
        }
    }

    /**
     * 取消排课
     * @param courseNo 选课组课程编号
     * @return 取消成功的字符串
     * @author 黄保钱 2018-9-17
     */
    @RequestMapping("/timetable/removeTimetableAppointment")
    @ResponseBody
    public String removeTimetableAppointment(@RequestParam String courseNo){
        // 获取已经添加的记录
        List<TimetableAppointment> timetableAppointmentList = timetableAppointmentService.findAppointmentByCourseCode(courseNo);
        List<TimetableBatch> timetableBatchList = timetableBatchService.getTimetableBatchByCourseCode(courseNo);
        List<TimetableAppointmentCycle> timetableAppointmentCycleList = timetableAppointmentSaveService.findCycleTimetableBycourseNo(courseNo);
        // 分别删除
        if(timetableAppointmentList != null && timetableAppointmentList.size() != 0) {
            for (TimetableAppointment t : timetableAppointmentList) {
                timetableAppointmentDAO.remove(t);
                timetableAppointmentDAO.flush();
            }
        }
        if(timetableBatchList != null && timetableBatchList.size() != 0) {
            for (TimetableBatch t : timetableBatchList) {
                timetableBatchDAO.remove(t);
                timetableBatchDAO.flush();
            }
        }
        if(timetableAppointmentCycleList != null && timetableAppointmentCycleList.size() != 0) {
            for (TimetableAppointmentCycle t : timetableAppointmentCycleList) {
                timetableAppointmentCycleDAO.remove(t);
                timetableAppointmentCycleDAO.flush();
            }
        }
        return "success";
    }

    /**
     * Description 查看排课方式
     * @param courseNo 选课组课程编号
     * @return 要显示的弹窗内容
     * @author 黄保钱 2018-9-17
     */
    @RequestMapping("/timetable/timetableWay")
    public @ResponseBody String timetableWay(@RequestParam String courseNo) {
        String result = "";
        // 获取排课主表记录
        List<TimetableAppointment> timetableAppointmentList = timetableAppointmentService.findAppointmentByCourseCode(courseNo);
        // 判断是否教务排课和是否教务时间排课
        for(TimetableAppointment t: timetableAppointmentList){
            if(t.getTimetableStyle() == 2 && !result.contains("<p>教务时间排课</p>")){
                result += "<p>教务时间排课</p>";
            }
            if(t.getTimetableStyle() == 3 && !result.contains("<p>非教务时间排课</p>")){
                result += "<p>非教务时间排课</p>";
            }
        }
        // 获取其他排课记录
        List<TimetableBatch> timetableBatchList = timetableBatchService.getTimetableBatchByCourseCode(courseNo);
        List<TimetableAppointmentCycle> timetableAppointmentCycleList = timetableAppointmentSaveService.findCycleTimetableBycourseNo(courseNo);
        // 判断分组和循环排课
        if(timetableBatchList != null && timetableBatchList.size() > 0){
            result = "<p>分组排课</p>";
        }
        if(timetableAppointmentCycleList != null && timetableAppointmentCycleList.size() > 0){
            result = "<p>循环排课</p>";
        }
        // 解决显示乱码
        try {
            result = URLEncoder.encode(result, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /****************************************************************************
     * 功能：AJAX 根据姓名、工号查询所有学生
     * 作者：贺子龙
     * 时间：2015-09-08
     ****************************************************************************/
    @RequestMapping("/appointment/findUserByCnameAndUsername")
    public @ResponseBody
    String findUserByCnameAndUsername(@RequestParam String cname, String username, Integer id, Integer page) {
        User user = new User();
        user.setCname(cname);
        user.setUsername(username);
        //分页开始
        int totalRecords = timetableAppointmentService.countStudent(user, id);
        int pageSize = 20;
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        //根据分页信息查询出来的记录
        List<User> userList = timetableAppointmentService.findStudent(user, page, pageSize, id);
        String s = "";
        for (User d : userList) {
            String academy = "";
            if (d.getSchoolAcademy() != null) {
                academy = d.getSchoolAcademy().getAcademyName();
            }
            s += "<tr>" +
                    "<td><input type='checkbox' name='CK_name' value='" + d.getUsername() + "'/></td>" +
                    "<td>" + d.getCname() + "</td>" +
                    "<td>" + d.getUsername() + "</td>" +
                    "<td>" + academy + "</td>" +

                    "</tr>";
        }
        s += "<tr><td colspan='6'>" +
                "<a href='javascript:void(0)' onclick='firstPage(1);'>" + "首页" + "</a>&nbsp;" +
                "<a href='javascript:void(0)' onclick='previousPage(" + page + ");'>" + "上一页" + "</a>&nbsp;" +
                "<a href='javascript:void(0)' onclick='nextPage(" + page + "," + pageModel.get("totalPage") + ");'>" + "下一页" + "</a>&nbsp;" +
                "<a href='javascript:void(0)' onclick='lastPage(" + pageModel.get("totalPage") + ");'>" + "末页" + "</a>&nbsp;" +
                "当前第" + page + "页&nbsp; 共" + pageModel.get("totalPage") + "页  " + totalRecords + "条记录" +
                "</td></tr>";
        return shareService.htmlEncode(s);
    }
    /********************************************************************************
     * Description: 欧亚排课-排课二级页面{右侧：编辑分批分组}
     * @author: 戴昊宇
     * @date: 2018-06-24
     *********************************************************************************/
    @RequestMapping(value="/timetable/editTimetableBatchGroup/{courseCode}/{batchId}")
    public ModelAndView editTimetableBatchGroup(@PathVariable String courseCode, @PathVariable int batchId){
        ModelAndView mav = new ModelAndView();
        TimetableBatch batch = timetableBatchDAO.findTimetableBatchByPrimaryKey(batchId);
        Set<TimetableGroup> timetableGroups = batch.getTimetableGroups();
        mav.addObject("timetableGroups", timetableGroups);
        int size = timetableGroups.size();
        mav.addObject("count", size);
        // 自建选课组标志位
        mav.setViewName("timetable/special/editTimetableBatchGroup.jsp");
        return mav;
    }

    /********************************************************************************
     * Description: 欧亚排课-排课二级页面{右侧：保存分批}
     *
     * @author: 戴昊宇
     * @date: 2018-06-25
     *********************************************************************************/
    @ResponseBody
    @RequestMapping("/timetable/saveTimetableBatchGroup")
    public String saveTimetableBatchGroup(HttpServletRequest request) {
        String[] groupId= request.getParameterValues("groupId");
        String[] name= request.getParameterValues("groupName");
        String[] number= request.getParameterValues("number");
        String count = request.getParameter("count");
        for(int i=0;i<Integer.parseInt(count);i++){
            TimetableGroup group = timetableGroupDAO.findTimetableGroupById(Integer.parseInt(groupId[i]));
            group.setGroupName(name[i]);
            group.setNumbers(Integer.parseInt(number[i]));
            timetableGroupDAO.store(group);
        }
        return "success";
    }
    /****************************************************************************
     * 功能：下载大纲文件
     * 作者：徐文
     * 日期：2016-06-01
     ****************************************************************************/
    @RequestMapping("/timetable/downloadfile")
    public void donloudfujian(HttpServletRequest request, HttpServletResponse response, @RequestParam int idkey) throws Exception {
        CommonDocument d=commonDocumentDAO.findCommonDocumentById(idkey);
        String filename = d.getDocumentName();
        String path=d.getDocumentUrl();
        try{
            File sendPath = new File(path);
            FileInputStream fis = new FileInputStream(sendPath);
            response.setCharacterEncoding("utf-8");
            //解决上传中文文件时不能下载的问题
            response.setContentType("multipart/form-data;charset=UTF-8");
            if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                filename = new String(filename.getBytes("UTF-8"),"ISO8859-1");// firefox浏览器
            } else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                filename = URLEncoder.encode(filename, "UTF-8");// IE浏览器
            }
            response.setHeader("Content-Disposition", "attachment;fileName="+filename);
            OutputStream fos = response.getOutputStream();
            byte[] buffer = new byte[8192];
            int count = 0;
            while((count = fis.read(buffer))>0){
                fos.write(buffer,0,count);
            }
            fis.close();
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
