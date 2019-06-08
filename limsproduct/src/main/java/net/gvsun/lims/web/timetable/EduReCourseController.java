package net.gvsun.lims.web.timetable;

import net.gvsun.lims.dto.timetable.TimetableParamVO;
import net.gvsun.lims.service.timetable.EduCourseService;
import net.gvsun.lims.service.timetable.TimetableCommonService;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.CStaticValueService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.timetable.OuterApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

/****************************************************************************
 * Descriptions：教务二次排课管理模块
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ****************************************************************************/
@Controller("EduReCourseController")
@SessionAttributes("selected_academy")
@RequestMapping("/lims/timetable/engineer/edurecourse")
public class EduReCourseController<JsonResult> {
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
     * Descriptions：教务排课管理-显示教务二次排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/eduReCourseList")
    public ModelAndView eduReCourseList(HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 获取学期列表
        List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
        mav.addObject("schoolTerms", schoolTerms);
        mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
        // 获取实验室排课的通用配置对象；
        CStaticValue cStaticValue = cStaticValueService.getCStaticValueByTimetableLabDevice(acno);
        mav.addObject("cStaticValue", cStaticValue);
        // 获取可选的教师列表列表
        mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap(acno));
        mav.setViewName("lims/timetable/engineer/edurecourse/eduReCourseList.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：教务排课管理-教务二次不分批排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/newEduReTimetableCourse")
    public ModelAndView newEduReNoGroupCourse(HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {
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
        mav.setViewName("lims/timetable/engineer/edurecourse/newEduReTimetableCourse.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：教务排课管理-教务二次分批排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/newEduReGroupTimetableCourse")
    public ModelAndView newEduReGroupTimetableCourse(HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {
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
        mav.setViewName("lims/timetable/engineer/edurecourse/newEduReGroupTimetableCourse.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：教务排课管理-教务二次分批排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/updateEduReTimetableCourse")
    public ModelAndView updateEduReTimetableCourse(HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {
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
        mav.setViewName("lims/timetable/engineer/edurecourse/updateEduReTimetableCourse.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：教务排课管理-教务二次分批排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/newEduReGroupCourse")
    public ModelAndView newEduReGroupCourse(HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 获取学期列表
        List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
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
        // 获取可选的教师列表列表
        mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap(acno));
        mav.setViewName("lims/timetable/engineer/edurecourse/newEduReGroupCourse.jsp");
        return mav;
    }

    /**
     * Description 选择要复制的分组页面
     * @param id 来源组id
     * @param batchId 批次id
     * @param courseNo 课程编号
     * @param termId 学期id
     * @return 页面
     * @author 2019-1-23
     */
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
        mav.setViewName("lims/timetable/engineer/edurecourse/chooseCopyGroup.jsp");
        return mav;
    }

    /**
     * Description 复制分组排课页面
     * @param destinationId 批次id
     * @param sourceId 来源组id
     * @param courseNo 课程编号
     * @param termId 学期id
     * @return 页面
     * @author 2019-1-23
     */
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
        mav.setViewName("lims/timetable/engineer/edurecourse/copyGroupTimetable.jsp");
        return mav;
    }


    /**
     * Description 保存复制分组排课
     * @param request 请求（传参）
     * @return 判冲结果
     * @author 2019-1-23
     */
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

    /**
     * Description 字符型数组转整型数组（转request传参用）
     * @param origin 来源字符型数组
     * @return 整型数组
     * @author 黄保钱 2019-1-23
     */
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
