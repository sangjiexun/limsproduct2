/****************************************************************************
 * 命名规范：
 * 本控制层调整必须是按照如下的路径调整：@RequestMapping("/appointment/sample")，全部小写。
 * 列表信息listxxxx，獲取信息：getxxxx 编辑信息：editxxxx 删除信息：detelexxxx 新增信息 newxxxx
 * 导出信息exportxxxx 保存信息：savexxxx 
 ****************************************************************************/

package net.zjcclims.web.lab;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.cmsshow.CMSShowService;
import net.zjcclims.service.common.CommonDocumentService;
import net.zjcclims.service.common.CommonVideoService;
import net.zjcclims.service.common.LabRoomLogService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.credit.CreditOptionService;
import net.zjcclims.service.device.LabRoomDeviceReservationService;
import net.zjcclims.service.device.LabRoomDeviceService;
import net.zjcclims.service.device.SchoolDeviceService;
import net.zjcclims.service.lab.*;
import net.zjcclims.service.message.MessageService;
import net.zjcclims.service.report.TeachingReportService;
import net.zjcclims.service.software.SoftwareService;
import net.zjcclims.service.system.SchoolWeekService;
import net.zjcclims.service.system.SystemService;
import net.zjcclims.service.timetable.TimetableAppointmentService;
import net.zjcclims.util.HttpClientUtil;
import net.zjcclims.web.common.PConfig;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/****************************************************************************
 * 功能：实验室预约模块 作者：孙虎  时间：2017-09-20
 ****************************************************************************/
@Controller("LabRoomReservationController")
@SessionAttributes({"selected_academy", "isAudit"})
public class LabRoomReservationController<JsonResult> {
    // 读取属性文件中specialAcademy对应的值（此方法需要在web-content.xml中增加配置）
    @Value("${specialAcademy}")
    private String specialAcademy;
    @Autowired
    LabReservationService labReservationService;
    @Autowired
    CommonVideoService commonVideoService;
    @Autowired
    CommonDocumentService commonDocumentService;
    @Autowired
    ShareService shareService;
    @Autowired
    private SchoolDeviceService schoolDeviceService;
    @Autowired
    private LabRoomLogService labRoomLogService;
    @Autowired
    private CMSShowService cmsShowService;
    @Autowired
    private LabAnnexService labAnnexService;
    @Autowired
    private SchoolTermDAO schoolTermDAO;
    @Autowired
    private LabReservationDAO labReservationDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private TimetableSelfCourseDAO timetableSelfCourseDAO;
    @Autowired
    private LabCenterDAO labCenterDAO;
    @Autowired
    private LabRoomAgentDAO labRoomAgentDAO;
    @Autowired
    private MessageDAO messageDAO;
    @Autowired
    private LabRoomPermitUserDAO labRoomPermitUserDAO;
    @Autowired
    private MessageService messageService;

    @Autowired
    private LabRoomService labRoomService;
    @Autowired
    private TeachingReportService teachingReportService;

    @Autowired
    private CDictionaryDAO cDictionaryDAO;
    @Autowired
    private LabRoomDAO labRoomDAO;
    @Autowired
    private CreditOptionService creditOptionService;
    @Autowired
    private LabRoomReservationService labRoomReservationService;
    @Autowired
    private LabRoomReservationCreditDAO labRoomReservationCreditDAO;
    @Autowired
    private LabRoomTrainingDAO labRoomTrainingDAO;
    @Autowired
    private LabRoomTrainingPeopleDAO labRoomTrainingPeopleDAO;
    @Autowired
    private LabRoomStationReservationDAO labRoomStationReservationDAO;
    @Autowired
    private LabRoomDeviceService labRoomDeviceService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private LabRoomStationReservationStudentDAO labRoomStationReservationStudentDAO;
    @Autowired
    private LabRoomStationReservationCreditDAO labRoomStationReservationCreditDAO;
    @Autowired
    private LabRoomDeviceReservationDAO labRoomDeviceReservationDAO;
    @Autowired
    private SoftwareReserveDAO softwareReserveDAO;
    @Autowired
    private SystemService systemService;
    @Autowired
    private SchoolCourseDAO schoolCourseDAO;
    @Autowired
    private OperationItemDAO operationItemDAO;
    @Autowired
    private SchoolClassesDAO schoolClassesDAO;
    @Autowired
    private SchoolWeekService schoolWeekService;
    @Autowired
    private LabroomTimetableRegisterDAO labroomTimetableRegisterDAO;
    @Autowired
    private TimetableAppointmentService timetableAppointmentService;
    @Autowired
    private TimetableAppointmentChangeDAO timetableAppointmentChangeDAO;
    @Autowired
    private TimetableLabRelatedDAO timetableLabRelatedDAO;
    @Autowired
    private SchoolDeviceDAO schoolDeviceDAO;
    @Autowired
    private SoftwareDAO softwareDAO;
    @Autowired
    private PConfig pConfig;
    @Autowired
    private LabRoomAdminService labRoomAdminService;
    @Autowired
    private LabCenterService labCenterService;
    @Autowired
    private AuthorityDAO authorityDAO;
    @Autowired
    private LabRoomDeviceReservationService labRoomDeviceReservationService;

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register
        // static
        // property
        // editors.
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

    /****************************************************************************
     * 功能：查询实验室 作者：孙虎 时间：2017-09-20--工位预约
     ****************************************************************************/
    @RequestMapping("/LabRoomReservation/labRoomStationList")
    public ModelAndView labRoomStationList(@ModelAttribute LabRoom labRoom, @RequestParam Integer currpage,
                                           @ModelAttribute("selected_academy") String acno, HttpServletRequest request) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // 查询表单的对象
        mav.addObject("labRoom", labRoom);
        int pageSize = 20;
        // 查询出来的总记录条数
        int totalRecords = labRoomReservationService.findLabRoompage(labRoom, 1, Integer.MAX_VALUE, "-1", request).size();
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoom> listLabRoom = labRoomReservationService.findLabRoompage(labRoom, currpage, pageSize, "-1", request);
        //获取当前登陆人
        User user = shareService.getUser();
        mav.addObject("user", user);
        //查找当前登陆人的系教学秘书和系主任
        boolean isHaveDeans = false;
        List<User> deanUsers = null;
        if(user.getSchoolAcademy() != null) {
            deanUsers = shareService.findDeansByAcademyNumber(user.getSchoolAcademy());
            //找到所有的导师
            mav.addObject("teacherList", shareService.findTheSameCollegeTeacher(user.getSchoolAcademy().getAcademyNumber()));
        }
        if (deanUsers != null && deanUsers.size() != 0) {
            isHaveDeans = true;
        }
        mav.addObject("isHaveDeans", isHaveDeans);
        //下拉框实验室
        mav.addObject("labRooms", labRoomDAO.findAllLabRooms());
        //选择年级
        mav.addObject("grade", schoolTermDAO.executeQuery("select st from SchoolTerm st group by st.yearCode"));
        mav.addObject("listLabRoom", listLabRoom);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("currpage", currpage);
        mav.addObject("pageSize", pageSize);
        mav.addObject("PROJECT_NAME", pConfig.PROJECT_NAME);
        mav.setViewName("/labroom/labAppointment.jsp");
        return mav;
    }

    /****************************************************************************
     * 功能：查询实验室 作者：孙虎 时间：2017-09-20
     * 工位预约
     ****************************************************************************/
    @RequestMapping("/LabRoomReservation/labRoomStationList1")
    public ModelAndView labRoomStationList1(@ModelAttribute LabRoom labRoom, @RequestParam Integer currpage, Integer selectedRoomId,
                                            @ModelAttribute("selected_academy") String acno, HttpServletRequest request) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // 查询表单的对象
        mav.addObject("labRoom", labRoom);
        mav.addObject("selectedRoomId", selectedRoomId);
        int pageSize = 20;
        // 查询出来的总记录条数
        int totalRecords = labRoomReservationService.findLabRoompage(labRoom, 1, Integer.MAX_VALUE, acno, request).size();
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoom> listLabRoom = labRoomReservationService.findLabRoompage(labRoom, currpage, pageSize, acno, request);
        //获取当前登陆人
        User user = shareService.getUser();
        mav.addObject("user", user);
        //查找当前登陆人的系教学秘书和系主任
        boolean isHaveDeans = false;
        List<User> deanUsers = shareService.findDeansByAcademyNumber(user.getSchoolAcademy());
        if (deanUsers.size() != 0) {
            isHaveDeans = true;
        }
        mav.addObject("isHaveDeans", isHaveDeans);
        //下拉框实验室
//        if (sid == -1) {
//            mav.addObject("labRooms", labRoomDAO.findAllLabRooms());
//        } else {
//            mav.addObject("labRooms", labRoomDAO.executeQuery("select l from LabRoom l where l.labCenter.id=" + sid));
//        }
        //选择年级
        mav.addObject("grade", userDAO.executeQuery("select st from SchoolTerm st group by st.yearCode"));
        mav.addObject("listLabRoom", listLabRoom);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("currpage", currpage);
        mav.addObject("pageSize", pageSize);
        mav.setViewName("/labroom/labAppointment.jsp");
        return mav;
    }

    /****************************************************************************
     * 功能：实训室预约列表查看 作者：孙虎 时间：2017-09-20实验室预约
     ****************************************************************************/
    @RequestMapping("/LabRoomReservation/labRoomList")
    public ModelAndView labRoomList(@ModelAttribute LabRoom labRoom, @RequestParam Integer currpage,
                                    @ModelAttribute("selected_academy") String acno, HttpServletRequest request) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // 查询表单的对象
        if(labRoom != null && labRoom.getId() != null){
            labRoom = labRoomDAO.findLabRoomById(labRoom.getId());
        }
        mav.addObject("labRoom", labRoom);
        int pageSize = 20;
        // 查询出来的总记录条数
        int totalRecords = labRoomReservationService.findLabRoomReservation(labRoom, 1, Integer.MAX_VALUE, acno, request).size();
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        String a=pConfig.softManage;
        if (a.equals("true")) {
            List<LabRoom> listLabRoom = labRoomReservationService.findLabRoomReservation(labRoom, currpage, pageSize, acno, request);
            mav.addObject("listLabRoom", listLabRoom);
        } else {
            List<LabRoom> listLabRoom = labRoomReservationService.findLabRoom(labRoom, currpage, pageSize, acno, request);
            mav.addObject("listLabRoom", listLabRoom);
        }
        //获取当前登陆人
        User user = shareService.getUser();
        mav.addObject("user", user);
        //班级
        mav.addObject("schoolClassess", systemService.loadSchoolClassess());//返回所有班级
        //使用人员类型
        List<CDictionary> userTypes = shareService.getCDictionaryData("lab_room_lending_user_type");
        mav.addObject("userTypes", userTypes);
        //借用类型
        List<CDictionary> lendingTypes = shareService.getCDictionaryData("lab_room_lending_type");
        mav.addObject("lendingTypes", lendingTypes);
            //找到所有的导师
        if(user.getSchoolAcademy() != null)
            mav.addObject("teacherList", shareService.findTheSameCollegeTeacher(user.getSchoolAcademy().getAcademyNumber()));
        //下拉框实验室
        List<LabRoom> labRooms=labRoomService.findLabRoom(acno, request);
        // 下拉框预约部门
        List<SchoolAcademy> schoolAcademyList = shareService.findAllSchoolAcademys();
        mav.addObject("currAcademy", shareService.findSchoolAcademyByPrimaryKey(acno));
        mav.addObject("schoolAcademyList", schoolAcademyList);

        mav.addObject("labRooms", labRooms);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("currpage", currpage);
        mav.addObject("pageSize", pageSize);
        mav.addObject("PROJECT_NAME", pConfig.PROJECT_NAME);
        mav.addObject("jobReservation", pConfig.jobReservation);
        if(pConfig.PROJECT_NAME.equals("shjulims")) {
            mav.setViewName("/labroom/labRoomAppointment2.jsp");
        }else {
            mav.setViewName("/labroom/labRoomAppointment.jsp");
        }
        return mav;
    }

    /****************************************************************************
     * 功能：实训室预约列表查看 作者：孙虎 时间：2017-09-20
     ****************************************************************************/
    @RequestMapping("/LabRoomReservation/labRoomList1")
    public ModelAndView labRoomList1(@ModelAttribute LabRoom labRoom, @RequestParam Integer currpage, Integer selectedRoomId,
                                     @ModelAttribute("selected_academy") String acno, HttpServletRequest request) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        mav.addObject("selectedRoomId", selectedRoomId);
        // 查询表单的对象
        mav.addObject("labRoom", labRoom);
        int pageSize = 20;
        // 查询出来的总记录条数
        int totalRecords = labRoomReservationService.findLabRoompage(labRoom, 1, Integer.MAX_VALUE, acno, request).size();
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoom> listLabRoom = labRoomReservationService.findLabRoompage(labRoom, currpage, pageSize, acno, request);
        //获取当前登陆人
        User user = shareService.getUser();
        mav.addObject("user", user);
        //班级
        mav.addObject("schoolClassess", systemService.loadSchoolClassess());//返回所有班级
        //使用人员类型
        List<CDictionary> userTypes = shareService.getCDictionaryData("lab_room_lending_user_type");
        mav.addObject("userTypes", userTypes);
        //借用类型
        List<CDictionary> lendingTypes = shareService.getCDictionaryData("lab_room_lending_type");
        mav.addObject("lendingTypes", lendingTypes);
        //下拉框实验室
//        if (sid == -1) {
//            mav.addObject("labRooms", labRoomDAO.findAllLabRooms());
//        } else {
//            mav.addObject("labRooms", labRoomDAO.executeQuery("select l LabRoom labRoom l where l.labCenter.id=" + sid));
//        }
        mav.addObject("listLabRoom", listLabRoom);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("currpage", currpage);
        mav.addObject("pageSize", pageSize);
        mav.setViewName("/labroom/labRoomAppointment.jsp");
        return mav;
    }

    /****************************************************************************
     * 功能：实训室预约-学生 作者：孙虎 时间：2017-09-20
     ****************************************************************************/
    @RequestMapping("/LabRoomReservation/showLabRoomReservation")
    public ModelAndView showLabRoomReservation(@RequestParam Integer labRoomId) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        User user = shareService.getUserDetail();
        LabRoom labRoom = labRoomDAO.findLabRoomById(labRoomId);
        mav.addObject("labRoom", labRoom);
        if (labRoom.getLabRoomLevel() == 1) {
            //是否需要导师审核
            CDictionary cDictionary = labRoom.getCDictionaryByTeacherAudit();
            mav.addObject("teacherAudit", cDictionary.getCName());
            if (cDictionary.getCName().equals("是")) {
                //找到所有的导师
                mav.addObject("teacherList", shareService.findTheSameCollegeTeacher(user.getSchoolAcademy().getAcademyNumber()));
            }
        }

		/*是否需要系主任审核------------------系主任这一级的刷选逻辑修改了，不需要自己选
        Integer dean = labRoom.getDean();
		mav.addObject("dean", dean);
		if(dean == 1){
			//找到所有的系教学秘书、系主任
			List<User> returnLsit = new ArrayList<User>();
			//系主任
			List<User> deanList = shareService.findUsersByAuthorityId(17);
			//系教学秘书
			List<User> departmentalSecretaryList = shareService.findUsersByAuthorityId(9);

			returnLsit.addAll(deanList);
			returnLsit.addAll(departmentalSecretaryList);

			mav.addObject("deanList", returnLsit);
		}*/
        mav.setViewName("/labroom/showLabRoomReservation.jsp");
        return mav;
    }

    /****************************************************************************
     * 功能：实训室预约-教师 作者：孙虎 时间：2017-09-20
     ****************************************************************************/
    @RequestMapping("/LabRoomReservation/showLabRoomReservationTeacher")
    public ModelAndView showLabRoomReservationTeacher(@RequestParam Integer labRoomId) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        User user = shareService.getUserDetail();
        LabRoom labRoom = labRoomDAO.findLabRoomById(labRoomId);
        mav.addObject("labRoom", labRoom);
		/*是否需要系主任审核--------------系主任这一级的刷选逻辑修改了，不需要自己选
		Integer dean = labRoom.getDean();
		mav.addObject("dean", dean);
		if(dean == 1){
			//找到所有的系教学秘书、系主任
			List<User> returnLsit = new ArrayList<User>();
			//系主任
			List<User> deanList = shareService.findUsersByAuthorityId(17);
			//系教学秘书
			List<User> departmentalSecretaryList = shareService.findUsersByAuthorityId(9);

			returnLsit.addAll(deanList);
			returnLsit.addAll(departmentalSecretaryList);

			mav.addObject("deanList", returnLsit);
		}*/
        // 获取当前学院下的年级
        mav.addObject("grade", userDAO.executeQuery("select c from User c where 1=1 group by c.grade"));
        mav.setViewName("/labroom/showLabRoomReservationTeacher.jsp");
        return mav;
    }

    /****************************************************************************
     * 功能：实训室实况  作者：孙虎 时间：2017-09-20
     ****************************************************************************/
    @RequestMapping("/LabRoomReservation/showLabRoomNow")
    public ModelAndView showLabRoomNow(@RequestParam Integer labRoomId) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        User user = shareService.getUserDetail();
        LabRoom labRoom = labRoomDAO.findLabRoomById(labRoomId);
        mav.addObject("labRoom", labRoom);
        //是否需要系主任审核
        Integer dean = labRoom.getDean();
        mav.addObject("dean", dean);
        if (dean!=null && dean == 1) {
            //找到所有的系教学秘书、系主任
            List<User> returnLsit = new ArrayList<User>();
            //系主任
            List<User> deanList = shareService.findUsersByAuthorityId(17);
            //系教学秘书
            List<User> departmentalSecretaryList = shareService.findUsersByAuthorityId(9);
            returnLsit.addAll(deanList);
            returnLsit.addAll(departmentalSecretaryList);
            mav.addObject("deanList", returnLsit);
        }
        mav.setViewName("/labroom/showLabRoomNow.jsp");
        return mav;
    }

    /****************************************************************************
     * 功能：预约前判断申请人的资格  作者：孙虎 时间：2017-09-25
     *
     ****************************************************************************/
    @ResponseBody
    @RequestMapping("/LabRoomReservation/judgeAccess")
    public String judgeAccess(HttpServletRequest request, @RequestParam Integer labRoomId) {
        User user = shareService.getUserDetail();
        LabRoom labRoom = labRoomDAO.findLabRoomById(labRoomId);
        //是否需要培训准入
        CDictionary allowSecurityAccess = labRoom.getCDictionaryByAllowSecurityAccess();
        //信誉积分的判断
        if (user.getCreditScore() != null) {
            if (user.getCreditScore() >= 80) {
                //一级实训室才需要下面的判断
                if (labRoom.getLabRoomLevel() == 1) {
                    if (labRoomService.isSettingForLabRoom(labRoomId)) {//是否进行初始化设置
                        // 判断是否需要导师审核和导师是否存在
                        if("c_active".equals(labRoom.getCDictionaryByTeacherAudit().getCCategory())
                                && "1".equals(labRoom.getCDictionaryByTeacherAudit().getCNumber())
                                && request.getSession().getAttribute("selected_role").equals("ROLE_STUDENT")){
                            List<User> teachers = shareService.findTheSameCollegeTeacher(user.getSchoolAcademy().getAcademyNumber());
                            if (teachers == null || teachers.size() == 0){
                                return "noTeacher";
                            }
                        }
                        //由于更改系主任的筛选方式，需要判断是否系主任，以防产生脏数据
                        if (labRoom.getDean() == 1) {
                            SchoolAcademy schoolAcademy = user.getSchoolAcademy();
                            if (schoolAcademy != null) {
                                List<User> dUsers = shareService.findDeansByAcademyNumber(schoolAcademy);
                                if (dUsers == null) {
                                    return "noDean";
                                } else if (dUsers.size() == 0) {
                                    return "noDean";
                                }
                            } else {
                                return "noDean";
                            }
                        }
                        // 判断是否需要实验室管理员审核和实验室管理员是否存在
                        if("c_active".equals(labRoom.getCDictionaryByLabManagerAudit().getCCategory())
                                && "1".equals(labRoom.getCDictionaryByLabManagerAudit().getCNumber())){
                            List<LabRoomAdmin> lras = labRoomAdminService.findAllLabRoomAdminsByLabRoomId(labRoomId);
                            if(lras == null || lras.size() == 0){
                                return "noLabRoomManager";
                            }
                        }
                        // 判断是否需要实验中心主任审核和实验中心主任是否存在
                        if(labRoom.getTrainingCenterDirector() == 1){
                            if(labRoom.getLabCenter() == null || labRoom.getLabCenter().getUserByCenterManager() == null){
                                return "noLabCenterManager";
                            }
                        }
                        // 判断是否需要实训部主任审核和实训部主任是否存在
                        if(labRoom.getTrainingDepartmentDirrector() == 1){
                            List<User> userList = shareService.findUsersByAuthorityId(3);
                            if(userList == null || userList.size() == 0){
                                return "noLabDepartmentManager";
                            }
                        }
                        if (allowSecurityAccess != null) {
                            if (allowSecurityAccess.getCName().equals("是")) {
                                CDictionary trainingType = labRoom.getCDictionaryByTrainType();
                                if (trainingType.getCName().equals("网上答题")) {//未做！不明！
                                   /* if (labRoomService.isAccessTestForLabRoom(user.getUsername(), labRoomId) == false) {
                                        return "noPassTest";
                                    }*/
                                } else {//培训
                                    LabRoomTraining labRoomTraining = null;
                                    //该实训室对应的所有培训
                                    List<LabRoomTraining> trainingList = labRoomService.findLabRoomTrainingByLabRoomId(labRoomTraining, labRoomId);
                                    if (trainingList != null) {
                                        for (LabRoomTraining labRoomTraining2 : trainingList) {
                                            //该培训下所有通过培训的学生
                                            List<LabRoomTrainingPeople> lTrainingPeoples = labRoomService.findTrainingPassPeoplesByTrainingId(labRoomTraining2.getId());
                                            if (lTrainingPeoples != null) {
                                                for (LabRoomTrainingPeople labRoomTrainingPeople : lTrainingPeoples) {
                                                    if (labRoomTrainingPeople.getUser().getUsername().equals(user.getUsername())) {
                                                        //通过培训
                                                        return "isAccess";
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    //未通过培训
                                    return "noAccess";
                                }
                            } else {
                                //不需要培训
                                return "noTraining";
                            }
                        }
                    } else {
                        return "noSetting";
                    }
                }
            } else {
                //信誉积分不够
                return "noCreditScore";
            }
        } else {
            ////信誉积分不够
            return "noCreditScore";
        }
        return "noTraining";
    }

    /****************************************************************************
     * 功能：查询剩余工位数  作者：孙虎 时间：2017-09-20
     * @throws ParseException
     ****************************************************************************/
    @ResponseBody
    @RequestMapping("/LabRoomReservation/findRestStations")
    public Integer findRestStations(HttpServletRequest request, @RequestParam Integer labRoomId) throws ParseException {
        //获取日期及开始结束时间
        String reservationTimeS = request.getParameter("reservationTime");
        String startTimeS = request.getParameter("startTime");
        String endTimeS = request.getParameter("endTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date reservationTimeDate = sdf.parse(reservationTimeS);
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
        Date startDate = sdf1.parse(startTimeS);
        Date endDate = sdf1.parse(endTimeS);
        Calendar reservation = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        reservation.setTime(reservationTimeDate);
        start.setTime(startDate);
        end.setTime(endDate);
        //根据日期和id查找该实训室的剩余可预约工位数（还未关联预约结果及排课）
        int restStationNum = labRoomReservationService.findRestReservationStations(labRoomId, reservation, start, end);
        return restStationNum;
    }

    /****************************************************************************
     * 功能：保存提交的预约-学生预约  作者：孙虎 时间：2017-09-21
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     ****************************************************************************/
    @ResponseBody
    @RequestMapping("/LabRoomReservation/saveLabRoomReservation")
    public String saveLabRoomReservation(HttpServletRequest request, @RequestParam Integer labRoomId) throws ParseException, NoSuchAlgorithmException {
        //获取日期及开始结束时间
        String reservationTimeS = request.getParameter("reservationTime");
        String startTimeS = request.getParameter("startTime");
        String endTimeS = request.getParameter("endTime");
        String reason = request.getParameter("reason");
        String teacher = request.getParameter("teacher");
        String userRole = request.getParameter("userRole");
        //String dean = request.getParameter("dean");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date reservationTimeDate = sdf.parse(reservationTimeS);
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
        Date startDate = sdf1.parse(startTimeS);
        Date endDate = sdf1.parse(endTimeS);
        Calendar reservation = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        reservation.setTime(reservationTimeDate);
        start.setTime(startDate);
        end.setTime(endDate);
        //后面老师添加多个时要用到
        String users = shareService.getUserDetail().getUsername();
        String[] array = users.split(",");
        //根据日期和id查找该实训室的剩余可预约工位数（还未关联预约结果及排课）
        int restStationNum = labRoomReservationService.findRestReservationStations(labRoomId, reservation, start, end);
        if (array.length > restStationNum) {
            return "over";
        } else {
            User user = shareService.getUserDetail();
            User deanUser = shareService.findDeansByAcademyNumber(user.getSchoolAcademy()).get(0);
            //保存预约
            labRoomReservationService.saveReservationStations(labRoomId, reservation, start, end, array, reason, teacher, deanUser.getUsername(), userRole);
            //判断实训室等级
            if (labRoomDAO.findLabRoomById(labRoomId).getLabRoomLevel() == 1) {
                return "success1";
            } else {
                return "success2";
            }
        }
    }

    /****************************************************************************
     * 功能：保存提交的预约-教师预约  作者：孙虎 时间：2017-09-22
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     ****************************************************************************/
    @ResponseBody
    @RequestMapping("/LabRoomReservation/saveLabRoomReservationTeacher")
    public String saveLabRoomReservationTeacher(HttpServletRequest request, @RequestParam Integer labRoomId) throws ParseException, NoSuchAlgorithmException {
        //获取日期及开始结束时间
        String reservationTimeS = request.getParameter("reservationTime");
        String startTimeS = request.getParameter("startTime");
        String endTimeS = request.getParameter("endTime");
        String reason = request.getParameter("reason");
        String students = request.getParameter("students");
        String userRole = request.getParameter("userRole");
        String teacher = request.getParameter("teacher");
        //String dean = request.getParameter("dean");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date reservationTimeDate = sdf.parse(reservationTimeS);
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
        Date startDate = sdf1.parse(startTimeS);
        Date endDate = sdf1.parse(endTimeS);
        Calendar reservation = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        reservation.setTime(reservationTimeDate);
        start.setTime(startDate);
        end.setTime(endDate);
        //学生名单
        String[] array = null;
        if (students != null && !"".equals(students)) {
            array = students.split(",");
        }
        array = (String[]) ArrayUtils.add(array, shareService.getUserDetail().getUsername());
        //判断是否超过可预约工位数统一设置
        CDictionary cDictionary = shareService.getCDictionaryByCategory("max_reservation_count", "1");
        if(cDictionary !=null){
            int maxCount = Integer.valueOf(cDictionary.getCName());
            if(array.length>=maxCount){
                return "overMax";
            }
        }
        //根据日期和id查找该实训室的剩余可预约工位数（还未关联预约结果及排课）
        int restStationNum = labRoomReservationService.findRestReservationStations(labRoomId, reservation, start, end);
        if (array != null && array.length > restStationNum) {
            return "over";
        } else {
            //判断数组中是否都是正确学生编号并返回错误学生编号数组
            String errorUsernames = "";
            if (array != null) {
                errorUsernames = labRoomReservationService.isAllStudent(array);
            }
            if (errorUsernames.equals("") || errorUsernames.replaceAll(",", "").equals("") || !userRole.equals("2")) {//无错误编号
                User user = shareService.getUserDetail();
//                List<User> deans = shareService.findDeansByAcademyNumber(user.getSchoolAcademy());
//                if(deans != null && deans.size() > 0) {
//                    User deanUser = deans.get(0);
//                    //保存预约
//                    labRoomReservationService.saveReservationStations(labRoomId, reservation, start, end, array, reason, teacher, deanUser.getUsername(), userRole);
//                }
                labRoomReservationService.saveReservationStations(labRoomId, reservation, start, end, array, reason, teacher, userRole);
                //判断实训室等级
                if (pConfig.PROJECT_NAME.equals("zjcclims") && labRoomDAO.findLabRoomById(labRoomId).getLabRoomLevel() == 1) {
                    return "success1";
                } else if(pConfig.PROJECT_NAME.equals("zjcclims")){
                    return "success2";
                }else {
                    return "success1";
                }
            } else {
                return errorUsernames;
            }
        }
    }

    /****************************************************************************
     * 功能：展示实训室软件详情  作者：孙虎 时间：2017-09-22
     ****************************************************************************/
    @RequestMapping("/LabRoomReservation/showLabRoomSoftware")
    public ModelAndView showLabRoomSoftware(@RequestParam Integer labRoomId) {
        // 新建ModelAndView对象
        ModelAndView mav = new ModelAndView();
        LabRoom labRoom = labRoomDAO.findLabRoomById(labRoomId);
        mav.addObject("labRoom", labRoom);
        //根据实验室id查询实验室软件
        List<Software> softwareList = softwareService.findSoftwareByRoomId(labRoomId);
        mav.addObject("softwareList", softwareList);
        mav.setViewName("/labroom/showLabRoomSoftware.jsp");
        return mav;
    }

    /****************************************************************************
     * 功能：展示实训室软件详情  作者：孙虎 时间：2017-09-22
     ****************************************************************************/
    @RequestMapping("/LabRoomReservation/showLabRoomDevice")
    public ModelAndView showLabRoomDevice(@RequestParam Integer labRoomId) {
        // 新建ModelAndView对象
        ModelAndView mav = new ModelAndView();
        LabRoom labRoom = labRoomDAO.findLabRoomById(labRoomId);
        mav.addObject("labRoom", labRoom);
        //根据实验分室id查询实验室设备
        List<LabRoomDevice> labDeviceList = labRoomDeviceService.findLabRoomDeviceByRoomId(labRoomId);
        mav.addObject("labDeviceList", labDeviceList);
        mav.setViewName("/labroom/showLabRoomDevice.jsp");
        return mav;
    }

    /****************************************************************************
     * 功能：查询实验室预约审核列表 作者：孙虎 时间：2017-09-25
     ****************************************************************************/
    @RequestMapping("/LabRoomReservation/labRoomReservationList")
    public ModelAndView labRoomReservationList(@ModelAttribute LabRoomStationReservation labRoomStationReservation, @RequestParam Integer currpage,
                                               Integer tage, Integer isaudit, @ModelAttribute("selected_academy") String acno, HttpServletRequest request) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        //获取当前登陆人
        User user = shareService.getUser();
        // 查询表单的对象
        mav.addObject("labRoomStationReservation", labRoomStationReservation);
        int pageSize = 10;
        // 查询出来的总记录条数
        int totalRecords = labRoomReservationService.findLabRoomreservatioList(labRoomStationReservation, tage, 1, Integer.MAX_VALUE, acno, isaudit).size();
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoomStationReservation> listLabRoomStationReservation = labRoomReservationService.findLabRoomreservatioList(labRoomStationReservation, tage, currpage, pageSize, acno, isaudit);
        //判断所处审核阶段，关联到前端的按钮
        if (listLabRoomStationReservation != null) {
            for (LabRoomStationReservation labRoomStationReservation2 : listLabRoomStationReservation) {
                //先初始化为0
                labRoomStationReservation2.setButtonMark(0);
//                Integer auditNumber = labRoomService.getAuditNumber(labRoomStationReservation2.getLabRoom(), labRoomStationReservation2.getState());
                Integer auditNumber = labRoomStationReservation2.getState();
                //未审核和审核中
                if (labRoomStationReservation2.getResult() == 2 || labRoomStationReservation2.getResult() == 3) {
                    if (auditNumber != null && isaudit == 1) {
                        //实训室管理员审核阶段
                        if (auditNumber == 3) {
                            //当前登陆人是审核实训室管理员
                            if (labRoomDeviceService.getLabRoomAdmin(labRoomStationReservation2.getLabRoom().getId(), user.getUsername()) && "ROLE_LABMANAGER".equals(request.getSession().getAttribute("selected_role"))) {
                                labRoomStationReservation2.setButtonMark(3);
                            }
                        }
                        //实训中心主任审核阶段
                        if (auditNumber == 4) {
                            //当前登陆人是审核实验室中心主任
                            if (user.getUsername().equals(labRoomStationReservation2.getLabRoom().getLabCenter().getUserByCenterManager().getUsername()) && "ROLE_EXCENTERDIRECTOR".equals(request.getSession().getAttribute("selected_role"))) {
                                labRoomStationReservation2.setButtonMark(4);
                            }
                        }
                    }
                }
            }
        }
        mav.addObject("user", user);
        mav.addObject("listLabRoomStationReservation", listLabRoomStationReservation);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("currpage", currpage);
        mav.addObject("pageSize", pageSize);
        mav.addObject("tage", tage);
        mav.addObject("isAudit", isaudit);
        mav.setViewName("/labroom/labRoomStationReservationList.jsp");
        return mav;
    }

    /****************************************************************************
     * 功能：展示预约的学生列表  作者：孙虎 时间：2017-09-25
     ****************************************************************************/
    @RequestMapping("/LabRoomReservation/showStudentList")
    public ModelAndView showStudentList(@RequestParam Integer id) {
        // 新建ModelAndView对象
        ModelAndView mav = new ModelAndView();
        LabRoomStationReservation labRoomStationReservation = labRoomStationReservationDAO.findLabRoomStationReservationById(id);
        Set<LabRoomStationReservationStudent> labRoomStationReservationStudents = labRoomStationReservation.getLabRoomStationReservationStudents();
        mav.addObject("labRoomStationReservationStudents", labRoomStationReservationStudents);
        mav.setViewName("/labroom/showStudentList.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 实训室预约审核/查看  作者：孙虎  时间：：2017-09-27 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomReservation/checkButton")
    public ModelAndView checkButton(@RequestParam int id, int tage, int state, Integer currpage) {
        ModelAndView mav = new ModelAndView();
        LabRoomStationReservation labRoomStationReservation = labRoomStationReservationDAO.findLabRoomStationReservationById(id);
        LabRoom labRoom = labRoomStationReservation.getLabRoom();
        state = labRoomStationReservation.getState();
        mav.addObject("state", state);
        mav.addObject("labRoomStationReservation", labRoomStationReservation);
        mav.addObject("labRoom", labRoom);
        mav.addObject("tage", tage);
        mav.addObject("currpage", currpage);
        mav.setViewName("/labroom/labRoomReaervationAudit.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 实训室预约审核/查看 --教师 作者：孙虎  时间：：2017-09-27 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomReservation/teacherAudit")
    public ModelAndView teacherAudit(@RequestParam int id, int tage, int state, Integer currpage, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        LabRoomStationReservation labRoomStationReservation = labRoomStationReservationDAO.findLabRoomStationReservationById(id);
        User user = shareService.getUserDetail();
        User teacher = labRoomStationReservation.getUserByTeacher();
        //是否为审核导师
        int isAudit = 0;
        if (state == 1) {//导师审核阶段
            if (teacher.getUsername().equals(user.getUsername())) {//是审核导师
                isAudit = 1;
                LabRoomStationReservationResult labRoomStationReservationResult = new LabRoomStationReservationResult();
                mav.addObject("labRoomStationReservationResult", labRoomStationReservationResult);
            } else {//不是审核导师
                mav.addObject("teacher", teacher);
            }
        } else {//非导师审核阶段
            Set<LabRoomStationReservationResult> lReservationResults = labRoomStationReservation.getLabRoomStationReservationResults();
            for (LabRoomStationReservationResult t : lReservationResults) {
                if (t.getTag() == 1) {
                    //导师审核结果
                    mav.addObject("lStationReservationResult", t);
                }
            }
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("currpage", currpage);
        mav.setViewName("/labroom/teacherAudit.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 保存实训室预约审核 --教师 作者：孙虎  时间：：2017-09-27 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     * @throws NoSuchAlgorithmException
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomReservation/saveTeacherAudit")
    public String saveTeacherAudit(HttpServletRequest request, @ModelAttribute LabRoomStationReservationResult labRoomStationReservationResult, @RequestParam int id, int tage, int state, Integer currpage) throws NoSuchAlgorithmException {
        LabRoomStationReservation labRoomStationReservation = labRoomStationReservationDAO.findLabRoomStationReservationById(id);
        labRoomStationReservation = labRoomReservationService.saveAuditResult(labRoomStationReservation, labRoomStationReservationResult);
        return "redirect:/LabRoomReservation/teacherAudit?id=" + id + "&tage=" + tage + "&state=" + labRoomStationReservation.getState() + "&currpage=" + currpage;
    }

    /**********************************************************************************************************
     * 实训室预约审核/查看 --系主任 作者：孙虎  时间：：2017-09-27 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomReservation/deanAudit")
    public ModelAndView deanAudit(@RequestParam int id, int tage, int state, Integer currpage) {
        ModelAndView mav = new ModelAndView();
        LabRoomStationReservation labRoomStationReservation = labRoomStationReservationDAO.findLabRoomStationReservationById(id);
        User user = shareService.getUserDetail();
        User auditUser = labRoomStationReservation.getUserByDean();
        //是否为审核人
        int isAudit = 0;
        if (state == 2) {//在此审核人审核阶段
            if (auditUser.getUsername().equals(user.getUsername())) {//是审核人
                isAudit = 1;
            } else {
                mav.addObject("teacher", auditUser);
            }
        } else {//非此审核人审核阶段
            if (state < 2) {//未到此审核人审核阶段
                mav.addObject("teacher", auditUser);
            } else {//此审核人已审核完成阶段
                Set<LabRoomStationReservationResult> lReservationResults = labRoomStationReservation.getLabRoomStationReservationResults();
                for (LabRoomStationReservationResult t : lReservationResults) {
                    if (t.getTag() != null && t.getTag() == 2) {
                        //导师审核结果
                        mav.addObject("lStationReservationResult", t);
                    }
                }
            }
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("currpage", currpage);
        mav.setViewName("/labroom/deanAudit.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 保存实训室预约审核 --系主任 作者：孙虎  时间：：2017-09-27 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     * @throws NoSuchAlgorithmException
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomReservation/saveDeanAudit")
    public String saveDeanAudit(@RequestParam int id, int tage, int state, Integer currpage, int auditResult, String remark) throws NoSuchAlgorithmException {
        LabRoomStationReservation labRoomStationReservation = labRoomStationReservationDAO.findLabRoomStationReservationById(id);
        LabRoomStationReservationResult labRoomStationReservationResult = new LabRoomStationReservationResult();
        labRoomStationReservationResult.setAuditResult(auditResult);
        if (remark != null) {
            labRoomStationReservationResult.setRemark(remark);
        }
        labRoomStationReservation = labRoomReservationService.saveAuditResult(labRoomStationReservation, labRoomStationReservationResult);
        return "redirect:/LabRoomReservation/deanAudit?id=" + id + "&tage=" + tage + "&state=" + labRoomStationReservation.getState() + "&currpage=" + currpage;
    }

    /**********************************************************************************************************
     * 实训室预约审核/查看 --实训室管理员
     *
     * @作者：孙虎
     * @时间：：2017-09-28 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomReservation/labRoomAdminAudit")
    public ModelAndView labRoomAdminAudit(@RequestParam int id, int tage, int state, Integer currpage, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        LabRoomStationReservation labRoomStationReservation = labRoomStationReservationDAO.findLabRoomStationReservationById(id);
        User user = shareService.getUserDetail();
        LabRoom labRoom = labRoomStationReservation.getLabRoom();
        //实验室管理员
        List<User> labRoomAdmins = labRoomDeviceService.findAdminByLrid(labRoom.getId());
        //当前登陆人是否为实验室管理员
        Boolean islabRoomAdmin = labRoomDeviceService.getLabRoomAdmin(labRoom.getId(), user.getUsername());
        //是否为审核人
        int isAudit = 0;
        if (state == 3) {//在此审核人审核阶段
            if (islabRoomAdmin == true && "ROLE_LABMANAGER".equals(request.getSession().getAttribute("selected_role"))) {//是审核人
                isAudit = 1;
            } else {
                mav.addObject("labRoomAdmins", labRoomAdmins);
            }
        } else {//非此审核人审核阶段
            if (state < 3) {//未到此审核人审核阶段
                mav.addObject("labRoomAdmins", labRoomAdmins);
            } else {//此审核人已审核完成阶段
                Set<LabRoomStationReservationResult> lReservationResults = labRoomStationReservation.getLabRoomStationReservationResults();
                for (LabRoomStationReservationResult t : lReservationResults) {
                    if (t.getTag() != null && t.getTag() == 3) {
                        //实验室管理员审核结果
                        mav.addObject("lStationReservationResult", t);
                    }
                }
            }
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("currpage", currpage);
        mav.setViewName("/labroom/labRoomAdminAudit.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 保存实训室预约审核 --实训室管理员
     *
     * @作者：孙虎
     * @时间：：2017-09-28 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     * @throws NoSuchAlgorithmException
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomReservation/savelabRoomAdminAudit")
    public String savelabRoomAdminAudit(@RequestParam int id, int tage, int state, Integer currpage, int auditResult, String remark) throws NoSuchAlgorithmException {
        LabRoomStationReservation labRoomStationReservation = labRoomStationReservationDAO.findLabRoomStationReservationById(id);
        LabRoomStationReservationResult labRoomStationReservationResult = new LabRoomStationReservationResult();
        labRoomStationReservationResult.setAuditResult(auditResult);
        if (remark != null) {
            labRoomStationReservationResult.setRemark(remark);
        }
        labRoomStationReservation = labRoomReservationService.saveAuditResult(labRoomStationReservation, labRoomStationReservationResult);
        return "redirect:/LabRoomReservation/labRoomAdminAudit?id=" + id + "&tage=" + tage + "&state=" + labRoomStationReservation.getState() + "&currpage=" + currpage;
    }

    /**********************************************************************************************************
     * 实训室预约审核/查看 --实训室中心主任 作者：孙虎  时间：：2017-09-28 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomReservation/labRoomCenterDirectorAudit")
    public ModelAndView labRoomCenterDirectorAudit(@RequestParam int id, int tage, int state, Integer currpage, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        LabRoomStationReservation labRoomStationReservation = labRoomStationReservationDAO.findLabRoomStationReservationById(id);
        User user = shareService.getUserDetail();
        LabRoom labRoom = labRoomStationReservation.getLabRoom();
        //实验室中心主任
        LabCenter labCenter = labRoom.getLabCenter();
        User labRoomCenterDirector = labCenter.getUserByCenterManager();
        //是否为审核人
        int isAudit = 0;
        if (state == 4) {//在此审核人审核阶段
            if (labRoomCenterDirector.getUsername().equals(user.getUsername()) && "ROLE_EXCENTERDIRECTOR".equals(request.getSession().getAttribute("selected_role"))) {//是审核人
                isAudit = 1;
            } else {
                mav.addObject("labRoomAdmins", labRoomCenterDirector);
            }
        } else {//非此审核人审核阶段
            if (state < 4) {//未到此审核人审核阶段
                mav.addObject("labRoomAdmins", labRoomCenterDirector);
            } else {//此审核人已审核完成阶段
                Set<LabRoomStationReservationResult> lReservationResults = labRoomStationReservation.getLabRoomStationReservationResults();
                for (LabRoomStationReservationResult t : lReservationResults) {
                    if (t.getTag() != null && t.getTag() == 4) {
                        //审核结果
                        mav.addObject("lStationReservationResult", t);
                    }
                }
            }
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("currpage", currpage);
        mav.setViewName("/labroom/labRoomCenterDirectorAudit.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 保存实训室预约审核 --实训室中心主任
     *
     * @作者：孙虎
     * @时间：：2017-09-28 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     * @throws NoSuchAlgorithmException
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomReservation/savelabRoomCenterDirectorAudit")
    public String savelabRoomCenterDirectorAudit(@RequestParam int id, int tage, int state, Integer currpage, int auditResult, String remark) throws NoSuchAlgorithmException {
        LabRoomStationReservation labRoomStationReservation = labRoomStationReservationDAO.findLabRoomStationReservationById(id);
        LabRoomStationReservationResult labRoomStationReservationResult = new LabRoomStationReservationResult();
        labRoomStationReservationResult.setAuditResult(auditResult);
        if (remark != null) {
            labRoomStationReservationResult.setRemark(remark);
        }
        labRoomStationReservation = labRoomReservationService.saveAuditResult(labRoomStationReservation, labRoomStationReservationResult);
        return "redirect:/LabRoomReservation/labRoomCenterDirectorAudit?id=" + id + "&tage=" + tage + "&state=" + labRoomStationReservation.getState() + "&currpage=" + currpage;
    }

    /**********************************************************************************************************
     * 实训室预约审核/查看 --实训室部主任
     *
     * @作者：孙虎
     * @时间：：2017-09-28 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomReservation/labRoomDepartmentDirectorAudit")
    public ModelAndView labRoomDepartmentDirectorAudit(@RequestParam int id, int tage, int state, Integer currpage) {
        ModelAndView mav = new ModelAndView();
        LabRoomStationReservation labRoomStationReservation = labRoomStationReservationDAO.findLabRoomStationReservationById(id);
        User user = shareService.getUserDetail();
        LabRoom labRoom = labRoomStationReservation.getLabRoom();
        //实验室中心主任
        List<User> labRoomCenterDirectors = shareService.findUsersByAuthorityId(3);
        //是否为审核人
        int isAudit = 0;
        if (state == 5) {//在此审核人审核阶段
            for (User user2 : labRoomCenterDirectors) {
                if (user2.getUsername().equals(user.getUsername())) {//是审核人
                    isAudit = 1;
                } else {
                    mav.addObject("labRoomAdmins", labRoomCenterDirectors);
                }
            }

        } else {//非此审核人审核阶段
            if (state < 5) {//未到此审核人审核阶段
                mav.addObject("labRoomAdmins", labRoomCenterDirectors);
            } else {//此审核人已审核完成阶段
                Set<LabRoomStationReservationResult> lReservationResults = labRoomStationReservation.getLabRoomStationReservationResults();
                for (LabRoomStationReservationResult t : lReservationResults) {
                    if (t.getTag() != null && t.getTag() == 5) {
                        //审核结果
                        mav.addObject("lStationReservationResult", t);
                    }
                }
            }
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("currpage", currpage);
        mav.setViewName("/labroom/labRoomDepartmentDirectorAudit.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 保存实训室预约审核 --实训室部主任
     *
     * @作者：孙虎
     * @时间：：2017-09-28 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     * @throws NoSuchAlgorithmException
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomReservation/savelabRoomDepartmentDirectorAudit")
    public String savelabRoomDepartmentDirectorAudit(@RequestParam int id, int tage, int state, Integer currpage, int auditResult, String remark) throws NoSuchAlgorithmException {
        LabRoomStationReservation labRoomStationReservation = labRoomStationReservationDAO.findLabRoomStationReservationById(id);
        LabRoomStationReservationResult labRoomStationReservationResult = new LabRoomStationReservationResult();
        labRoomStationReservationResult.setAuditResult(auditResult);
        if (remark != null) {
            labRoomStationReservationResult.setRemark(remark);
        }
        labRoomStationReservation = labRoomReservationService.saveAuditResult(labRoomStationReservation, labRoomStationReservationResult);
        return "redirect:/LabRoomReservation/labRoomDepartmentDirectorAudit?id=" + id + "&tage=" + tage + "&state=" + labRoomStationReservation.getState() + "&currpage=" + currpage;
    }

    /****************************************************************************
     * @throws Exception
     * @description：实训室工位预约信誉登记
     * @author：孙虎
     * @date：2017-10-11
     ****************************************************************************/
    @RequestMapping("/LabRoomReservation/labRoomReservationCredit")
    public ModelAndView labRoomCredit(@RequestParam Integer id) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的预约
        LabRoomStationReservation labRoomStationReservation = labRoomStationReservationDAO.findLabRoomStationReservationById(id);
        mav.addObject("reservation", labRoomStationReservation);
        // 当前登录人
        User user = shareService.getUser();
        String username = user.getUsername();
        username = "[" + username + "]";
        mav.addObject("user", user);
        mav.addObject("username", username);
        mav.addObject("audit", new LabRoomDeviceReservationResult());
        // 结果
        List<CDictionary> results = shareService.getCDictionaryData("c_training_result");
        mav.addObject("results", results);
        //取得所有扣分项
        List<CreditOption> creditOption = creditOptionService.findAllCreditOptionByQuery();
        mav.addObject("listCreditOption", creditOption);
        //取得当前预约纪录的扣分项
        List<CreditOption> listCreditOptions = new ArrayList<CreditOption>();
        List<String> remarks = new ArrayList<String>();
        String remark = "";
        Set<LabRoomStationReservationCredit> labRoomDeviceReservationCreditOption = labRoomStationReservation.getLabRoomStationReservationCredits();
        for (LabRoomStationReservationCredit labRoomReservationCredit : labRoomDeviceReservationCreditOption) {
            remark = labRoomReservationCredit.getRemark();
            CreditOption creditoption = labRoomReservationCredit.getCreditOption();
            remarks.add(remark);
            listCreditOptions.add(creditoption);
        }
        mav.addObject("remark", remark);
        mav.addObject("listCreditOptions", listCreditOptions);
        // 再将状态信息传递给新的页面
        mav.addObject("isRest", 0);
        mav.setViewName("/labroom/lab_room_reservation/labRoomReservationCredit.jsp");
        return mav;

    }

    /****************************************************************************
     * @throws Exception
     * @description：保存实训室信誉登记
     * @author：孙虎
     * @date：2017-08-16
     ****************************************************************************/
    @RequestMapping("/LabRoomReservation/saveLabRoomDeviceReservationCredit")
    public String saveLabRoomDeviceReservationCredit(@RequestParam Integer reservationId, @RequestParam String itemIds, @RequestParam String remark) {
        LabRoomStationReservationCredit labRoomStationReservationCredit = new LabRoomStationReservationCredit();
        LabRoomStationReservation labRoomStationReservation = labRoomStationReservationDAO.findLabRoomStationReservationByPrimaryKey(reservationId);
        String[] ids = itemIds.split(",");
        for (String string : ids) {
            CreditOption creditOption = creditOptionService.findCreditOptionById(Integer.parseInt(string));
            labRoomStationReservationCredit.setCreditOption(creditOption);
            labRoomStationReservationCredit.setLabRoomStationReservation(labRoomStationReservation);
            labRoomStationReservationCredit.setRemark(remark);
            labRoomStationReservationCreditDAO.store(labRoomStationReservationCredit);
            labRoomStationReservationCreditDAO.flush();
            //扣分
            labRoomStationReservation = labRoomStationReservationDAO.findLabRoomStationReservationById(reservationId);
            User user = labRoomStationReservation.getUser();
            int creditScore = user.getCreditScore() - labRoomStationReservationCredit.getCreditOption().getDeduction();
            user.setCreditScore(creditScore);
            userDAO.store(user);
        }
        return "redirect:/LabRoomReservation/labRoomReservationCredit?id=" + reservationId;//导入后
    }

    /****************************************************************************
     * 功能：查询大仪预约审核列表
     *
     * @作者：孙虎
     * @时间：2017-09-25
     ****************************************************************************/
    @RequestMapping("/LabRoomDeviceReservation/labRoomDeviceReservationList")
    public ModelAndView labRoomDeviceReservationList(@ModelAttribute LabRoomDevice labRoomDevice, @RequestParam Integer currpage,
                                                     Integer tage, Integer isaudit, @ModelAttribute("selected_academy") String acno, HttpServletRequest request) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        //获取当前登陆人
        User user = shareService.getUser();
        // 查询表单的对象
        mav.addObject("labRoomDevice", labRoomDevice);
        int pageSize = 15;
        //设备名称
        //Set<SchoolDevice> schoolDevice = schoolDeviceService.findAllSchoolDevice();
        List<SchoolDevice>  schoolDevice = labRoomReservationService.findSchoolDeviceByLabRoomDeviceReservation();
        mav.addObject("schoolDevice", schoolDevice);
        // 实验室
        Set<LabRoom> rooms = labRoomService.findallLabRoom();
        mav.addObject("rooms", rooms);
        // 查询出来的总记录条数
        int totalRecords = labRoomReservationService.findLabRoomDeviceReservation(labRoomDevice, tage, 1, 0, acno, isaudit, request).size();
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoomDeviceReservation> listLabRoomDeviceReservation = labRoomReservationService.findLabRoomDeviceReservation(labRoomDevice, tage, currpage, pageSize, acno, isaudit, request);
        // 查询所有的记录
        /*List<LabRoomDeviceReservation> listLabRoomDeviceReservations = labRoomReservationService.findLabRoomDeviceReservationAll();
        mav.addObject("listLabRoomDeviceReservations", listLabRoomDeviceReservations);*/
        Map<Integer, String> allResult = new HashMap<>();
        //判断所处审核阶段，关联到前端的按钮
        if (listLabRoomDeviceReservation != null) {
            for (LabRoomDeviceReservation labRoomDeviceReservation2 : listLabRoomDeviceReservation) {
                //先初始化为0
                labRoomDeviceReservation2.setButtonMark(0);
                // 从审核服务获取审核数据
                Map<String, String> currAuditParams = new HashMap<>();
                String businessType = "DeviceReservation" + labRoomDeviceReservation2.getLabRoomDevice().getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber();
                currAuditParams.put("businessType", pConfig.PROJECT_NAME + businessType);
                currAuditParams.put("businessAppUid", labRoomDeviceReservation2.getId().toString());
                String currAuditStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", currAuditParams);
                JSONObject currAuditJSONObject = JSONObject.parseObject(currAuditStr);

                //未审核和审核中
                if("success".equals(currAuditJSONObject.getString("status")) &&
                        currAuditJSONObject.getJSONArray("data") != null &&
                        currAuditJSONObject.getJSONArray("data").size() > 0) {
//                if(labRoomDeviceReservation2.getCAuditResult() != null) {
                    JSONObject o = currAuditJSONObject.getJSONArray("data").getJSONObject(0);
                    if(labRoomDeviceReservation2.getCAuditResult()!=null && labRoomDeviceReservation2.getCAuditResult().getCNumber()!=null){
                        if ("1".equals(labRoomDeviceReservation2.getCAuditResult().getCNumber())) {
                            // 更新审核剩余时间
                            if (labRoomDeviceReservation2.getLabRoomDevice().getIsAuditTimeLimit() != null
                                    && labRoomDeviceReservation2.getLabRoomDevice().getIsAuditTimeLimit() == 1
                                    && "0".equals(labRoomDeviceReservation2.getUserByReserveUser().getUserRole())) {
                                Long restTime = labRoomDeviceReservation2.getTime().getTime().getTime()
                                        + labRoomDeviceReservation2.getLabRoomDevice().getAuditTimeLimit() * 3600000
                                        - new Date().getTime();
                                if (restTime < 0) {
                                    CDictionary status = cDictionaryDAO.findCDictionaryById(746);
                                    labRoomDeviceReservation2.setCAuditResult(status);
                                    labRoomDeviceReservationDAO.store(labRoomDeviceReservation2);
                                } else {
                                    labRoomDeviceReservation2.setAuditRestTime(restTime / 3600000);
                                    labRoomDeviceReservationDAO.store(labRoomDeviceReservation2);
                                }
                            }
                            if (request.getSession().getAttribute("selected_role").equals("ROLE_" + o.getString("result"))) {
//                        if (labRoomDeviceReservation2.getState() != null) {
                                //导师审核阶段
                                if ("TEACHER".equals(o.getString("result"))) {
                                    //当前登陆人是审核导师
                                    if (user.getUsername().equals(labRoomDeviceReservation2.getUserByTeacher().getUsername())) {
                                        labRoomDeviceReservation2.setButtonMark(1);
                                    }
                                }
                                //系主任审核阶段
                                else if ("CFO".equals(o.getString("result"))) {
                                    //当前登陆人是审核系主任
                                    if (user.getUsername().equals(labRoomDeviceReservation2.getUserByDean().getUsername())) {
                                        labRoomDeviceReservation2.setButtonMark(2);
                                    }
                                }
                                //实训室管理员审核阶段
                                else if ("LABMANAGER".equals(o.getString("result"))) {
                                    //当前登陆人是审核实训室管理员
                                    if (labRoomDeviceService.getLabRoomAdmin(labRoomDeviceReservation2.getLabRoomDevice().getLabRoom().getId(), user.getUsername()) == true) {
                                        labRoomDeviceReservation2.setButtonMark(3);
                                    }
                                }
                                //实训中心主任审核阶段
                                else if ("EXCENTERDIRECTOR".equals(o.getString("result"))) {
                                    //当前登陆人是审核实验室中心主任
                                    if (user.getUsername().equals(labRoomDeviceReservation2.getLabRoomDevice().getLabRoom().getLabCenter().getUserByCenterManager().getUsername())) {
                                        labRoomDeviceReservation2.setButtonMark(4);
                                    }
                                }
                                // 其他
                                else{
                                    labRoomDeviceReservation2.setButtonMark(1);
                                }
                            }
                        }
                    }
                }
                // 获取所有审核状态
                Map<String, String> allAuditStateParams = new HashMap<>();
                allAuditStateParams.put("businessType", pConfig.PROJECT_NAME + businessType);
                allAuditStateParams.put("businessAppUid", labRoomDeviceReservation2.getId().toString());
                allAuditStateParams.put("businessUid", labRoomDeviceReservation2.getLabRoomDevice().getId().toString());
                String allAuditStateStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessLevelStatus", allAuditStateParams);
                JSONObject allAuditStateJSON = JSONObject.parseObject(allAuditStateStr);
                if("success".equals(allAuditStateJSON.getString("status"))){
                    String htmlStr = "";
                    JSONArray allAuditStateJSONArray = allAuditStateJSON.getJSONArray("data");
                    for (int j = 0; j < allAuditStateJSONArray.size(); j++) {
                        JSONObject o = allAuditStateJSONArray.getJSONObject(j);
                        User auditUser = null;
                        if(o.getString("auditUser") != null){
                            htmlStr += "<span style='color: black";
                            auditUser = userDAO.findUserByUsername(o.getString("auditUser"));
                        }else {
                            htmlStr += "<span style='color: gray";
                        }
                        htmlStr += "'>";
                        String authCName = authorityDAO.findAuthorityByAuthorityName(o.getString("authName")).iterator().next().getCname();
                        htmlStr += authCName + " ";
                        htmlStr += auditUser == null ? "" : auditUser.getCname() + " ";
                        htmlStr += o.getString("result");
                        htmlStr += "</span><br>";
                    }
                    allResult.put(labRoomDeviceReservation2.getId(), htmlStr);
                }
            }
        }
        mav.addObject("allResult", allResult);
        mav.addObject("user", user);
        mav.addObject("listLabRoomDeviceReservation", listLabRoomDeviceReservation);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("currpage", currpage);
        mav.addObject("pageSize", pageSize);
        mav.addObject("tage", tage);
        mav.addObject("isAudit", isaudit);
        //mav.addObject("state", request.getParameter("state"));
        mav.addObject("begintime", request.getParameter("begintime"));
        mav.addObject("begintime1", request.getParameter("begintime1"));
        mav.setViewName("/device/device_reservation/labRoomDeviceReservationList.jsp");
        return mav;
    }

    /****************************************************************************
     * 功能：查询大仪预约审核列表--批量审核列表
     *
     * @作者：孙虎
     * @时间：2017-09-25
     * update Hezhaoyi 2019-3-21
     ****************************************************************************/
    @RequestMapping("/LabRoomDeviceReservation/labRoomDeviceReservationListBatch")
    public ModelAndView labRoomDeviceReservationListBatch(@ModelAttribute LabRoomDevice labRoomDevice, @RequestParam Integer currpage,
                                                     Integer tage, Integer isaudit, @ModelAttribute("selected_academy") String acno, HttpServletRequest request) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        //获取当前登陆人
        User user = shareService.getUser();
        // 查询表单的对象
        mav.addObject("labRoomDevice", labRoomDevice);
        int pageSize = 15;
        //设备名称
        List<SchoolDevice>  schoolDevice = labRoomReservationService.findSchoolDeviceByLabRoomDeviceReservation();
        mav.addObject("schoolDevice", schoolDevice);
        // 实验室
        Set<LabRoom> rooms = labRoomService.findallLabRoom();
        mav.addObject("rooms", rooms);
        /*// 查询出来的总记录条数
        int totalRecords = labRoomReservationService.findLabRoomDeviceReservation(labRoomDevice, tage, 1, 0, acno, isaudit, request).size();
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);*/
        // 根据分页信息查询出来的记录
        List<LabRoomDeviceReservation> listLabRoomDeviceReservationAll = labRoomReservationService.findLabRoomDeviceReservation(labRoomDevice, tage, 1, 0, acno, isaudit, request);
        List<LabRoomDeviceReservation> listLabRoomDeviceReservation = new ArrayList<>();
        // 查询所有的记录
        /*List<LabRoomDeviceReservation> listLabRoomDeviceReservations = labRoomReservationService.findLabRoomDeviceReservationAll();
        mav.addObject("listLabRoomDeviceReservations", listLabRoomDeviceReservations);*/
        Map<Integer, String> allResult = new HashMap<>();
        //判断所处审核阶段，关联到前端的按钮
        if (listLabRoomDeviceReservationAll != null) {
            for (LabRoomDeviceReservation labRoomDeviceReservation2 : listLabRoomDeviceReservationAll) {
                //先初始化为0
                labRoomDeviceReservation2.setButtonMark(0);
                // 从审核服务获取审核数据
                Map<String, String> currAuditParams = new HashMap<>();
                String businessType = "DeviceReservation" + labRoomDeviceReservation2.getLabRoomDevice().getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber();
                currAuditParams.put("businessType", pConfig.PROJECT_NAME + businessType);
                currAuditParams.put("businessAppUid", labRoomDeviceReservation2.getId().toString());
                String currAuditStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", currAuditParams);
                JSONObject currAuditJSONObject = JSONObject.parseObject(currAuditStr);

                //未审核和审核中
                if("success".equals(currAuditJSONObject.getString("status")) &&
                        currAuditJSONObject.getJSONArray("data") != null &&
                        currAuditJSONObject.getJSONArray("data").size() > 0) {
//                if(labRoomDeviceReservation2.getCAuditResult() != null) {
                    JSONObject o = currAuditJSONObject.getJSONArray("data").getJSONObject(0);
                    if(labRoomDeviceReservation2.getCAuditResult()!=null && labRoomDeviceReservation2.getCAuditResult().getCNumber()!=null){
                        if ("1".equals(labRoomDeviceReservation2.getCAuditResult().getCNumber())) {
                            // 更新审核剩余时间
                            if (labRoomDeviceReservation2.getLabRoomDevice().getIsAuditTimeLimit() != null
                                    && labRoomDeviceReservation2.getLabRoomDevice().getIsAuditTimeLimit() == 1
                                    && "0".equals(labRoomDeviceReservation2.getUserByReserveUser().getUserRole())) {
                                Long restTime = labRoomDeviceReservation2.getTime().getTime().getTime()
                                        + labRoomDeviceReservation2.getLabRoomDevice().getAuditTimeLimit() * 3600000
                                        - new Date().getTime();
                                if (restTime < 0) {
                                    CDictionary status = cDictionaryDAO.findCDictionaryById(746);
                                    labRoomDeviceReservation2.setCAuditResult(status);
                                    labRoomDeviceReservationDAO.store(labRoomDeviceReservation2);
                                } else {
                                    labRoomDeviceReservation2.setAuditRestTime(restTime / 3600000);
                                    labRoomDeviceReservationDAO.store(labRoomDeviceReservation2);
                                }
                            }
                            if (request.getSession().getAttribute("selected_role").equals("ROLE_" + o.getString("result"))) {
                                //筛选当前的登陆人的审核记录
                                listLabRoomDeviceReservation.add(labRoomDeviceReservation2);
                                //导师审核阶段
                                if ("TEACHER".equals(o.getString("result"))) {
                                    //当前登陆人是审核导师
                                    if (user.getUsername().equals(labRoomDeviceReservation2.getUserByTeacher().getUsername())) {
                                        labRoomDeviceReservation2.setButtonMark(1);
                                    }
                                }
                                //系主任审核阶段
                                else if ("CFO".equals(o.getString("result"))) {
                                    //当前登陆人是审核系主任
                                    if (user.getUsername().equals(labRoomDeviceReservation2.getUserByDean().getUsername())) {
                                        labRoomDeviceReservation2.setButtonMark(2);
                                    }
                                }
                                //实训室管理员审核阶段
                                else if ("LABMANAGER".equals(o.getString("result"))) {
                                    //当前登陆人是审核实训室管理员
                                    if (labRoomDeviceService.getLabRoomAdmin(labRoomDeviceReservation2.getLabRoomDevice().getLabRoom().getId(), user.getUsername()) == true) {
                                        labRoomDeviceReservation2.setButtonMark(3);
                                    }
                                }
                                //实训中心主任审核阶段
                                else if ("EXCENTERDIRECTOR".equals(o.getString("result"))) {
                                    //当前登陆人是审核实验室中心主任
                                    if (user.getUsername().equals(labRoomDeviceReservation2.getLabRoomDevice().getLabRoom().getLabCenter().getUserByCenterManager().getUsername())) {
                                        labRoomDeviceReservation2.setButtonMark(4);
                                    }
                                }
                                // 其他
                                else{
                                    labRoomDeviceReservation2.setButtonMark(1);
                                }
                            }
                        }
                    }
                }
                // 获取所有审核状态
                Map<String, String> allAuditStateParams = new HashMap<>();
                allAuditStateParams.put("businessType", pConfig.PROJECT_NAME + businessType);
                allAuditStateParams.put("businessAppUid", labRoomDeviceReservation2.getId().toString());
                allAuditStateParams.put("businessUid", labRoomDeviceReservation2.getLabRoomDevice().getId().toString());
                String allAuditStateStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessLevelStatus", allAuditStateParams);
                JSONObject allAuditStateJSON = JSONObject.parseObject(allAuditStateStr);
                if("success".equals(allAuditStateJSON.getString("status"))){
                    String htmlStr = "";
                    JSONArray allAuditStateJSONArray = allAuditStateJSON.getJSONArray("data");
                    for (int j = 0; j < allAuditStateJSONArray.size(); j++) {
                        JSONObject o = allAuditStateJSONArray.getJSONObject(j);
                        User auditUser = null;
                        if(o.getString("auditUser") != null){
                            htmlStr += "<span style='color: black";
                            auditUser = userDAO.findUserByUsername(o.getString("auditUser"));
                        }else {
                            htmlStr += "<span style='color: gray";
                        }
                        htmlStr += "'>";
                        String authCName = authorityDAO.findAuthorityByAuthorityName(o.getString("authName")).iterator().next().getCname();
                        htmlStr += authCName + " ";
                        htmlStr += auditUser == null ? "" : auditUser.getCname() + " ";
                        htmlStr += o.getString("result");
                        htmlStr += "</span><br>";
                    }
                    allResult.put(labRoomDeviceReservation2.getId(), htmlStr);
                }
            }
        }
        //查询框需要的学期
        Set<SchoolTerm> schoolTerm = schoolTermDAO.findAllSchoolTerms();
        mav.addObject("schoolTerm",schoolTerm);
        //当前学期
        /*SchoolTerm Term = schoolTermDAO.*/
        mav.addObject("allResult", allResult);
        mav.addObject("user", user);
        mav.addObject("listLabRoomDeviceReservation", listLabRoomDeviceReservation);
        // 查询出来的总记录条数
        int totalRecords = listLabRoomDeviceReservation.size();
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("currpage", currpage);
        mav.addObject("pageSize", pageSize);
        mav.addObject("tage", tage);
        mav.addObject("isAudit", isaudit);
        mav.addObject("isBatch",true);
        List<CDictionary> results = shareService.getCDictionaryData("c_training_result");
        mav.addObject("results", results);
        mav.addObject("isRest", 0);
        //mav.addObject("state", request.getParameter("state"));
        mav.addObject("begintime", request.getParameter("begintime"));
        mav.addObject("begintime1", request.getParameter("begintime1"));
        mav.setViewName("/device/device_reservation/labRoomDeviceReservationListBatch.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 大仪预约审核/查看
     *
     * @作者：孙虎
     * @时间：：2017-09-27 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任、0提交后没有审核查看
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomDeviceReservation/checkButton")
    public ModelAndView checkButtonDevice(@RequestParam int id, int tage, int state, Integer currpage) {
        ModelAndView mav = new ModelAndView();
        LabRoomDeviceReservation labRoomDeviceReservation = labRoomDeviceReservationDAO.findLabRoomDeviceReservationById(id);
        LabRoomDevice labRoomDevice = labRoomDeviceReservation.getLabRoomDevice();
        mav.addObject("state", state);
        mav.addObject("labRoomDeviceReservation", labRoomDeviceReservation);
        mav.addObject("labRoomDevice", labRoomDevice);
        mav.addObject("tage", tage);
        mav.addObject("currpage", currpage);

        Map<String, String> params = new HashMap<>();
        String businessType = "DeviceReservation" + labRoomDeviceReservation.getLabRoomDevice().getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber();
        params.put("businessType", pConfig.PROJECT_NAME + businessType);
        params.put("businessUid", labRoomDevice.getId().toString());
        params.put("businessAppUid", labRoomDeviceReservation.getId().toString());
        String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessLevelStatus", params);
        JSONObject jsonObject = JSONObject.parseObject(s);
        List<Object[]> auditItems = new ArrayList<>();
        if("success".equals(jsonObject.getString("status"))) {
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if(jsonArray != null && jsonArray.size() > 0){
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    Object[] os = new Object[3];
                    os[0] = authorityDAO.findAuthorityByAuthorityName(o.getString("authName")).iterator().next().getCname();
                    os[1] = o.getIntValue("level");
                    os[2] = o.getString("authName");
                    auditItems.add(os);
                }
            }
        }
        mav.addObject("auditItems", auditItems);

        mav.setViewName("/device/device_reservation/labRoomDeviceReaervationAudit.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 大仪预约审核/查看 --教师 作者：孙虎  时间：：2017-09-27 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomDeviceReservation/teacherAudit")
    public ModelAndView teacherAuditDevice(@RequestParam int id, int tage, int state, Integer currpage) {
        ModelAndView mav = new ModelAndView();
        LabRoomDeviceReservation labRoomDeviceReservation = labRoomDeviceReservationDAO.findLabRoomDeviceReservationById(id);
        User user = shareService.getUserDetail();
        User teacher = labRoomDeviceReservation.getUserByTeacher();
        //是否为审核导师
        int isAudit = 0;
        // 判断是否链接与实际情况不符
        if(labRoomDeviceReservation.getState() != state){
            state = labRoomDeviceReservation.getState();
        }
        if (state == 1) {//导师审核阶段
            if (teacher.getUsername().equals(user.getUsername())) {//是审核导师
                isAudit = 1;
                LabRoomDeviceReservationResult labRoomDeviceReservationResult = new LabRoomDeviceReservationResult();
                mav.addObject("labRoomDeviceReservationResult", labRoomDeviceReservationResult);
            } else {//不是审核导师
                mav.addObject("teacher", teacher);
            }
        } else {//非导师审核阶段
            Set<LabRoomDeviceReservationResult> ldReservationResults = labRoomDeviceReservation.getLabRoomDeviceReservationResults();
            for (LabRoomDeviceReservationResult t : ldReservationResults) {
                if (t.getTag() == 1) {
                    //导师审核结果
                    mav.addObject("lStationReservationResult", t);
                }
            }
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("currpage", currpage);
        mav.setViewName("/device/device_reservation/teacherAudit.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 保存大仪预约审核 --教师 作者：孙虎  时间：：2017-09-27 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     * @throws NoSuchAlgorithmException
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomDeviceReservation/saveTeacherAudit")
    public String saveTeacherAuditDevice(@ModelAttribute LabRoomDeviceReservationResult labRoomDeviceReservationResult, @RequestParam int id, int tage, int state, Integer currpage) throws NoSuchAlgorithmException {
        LabRoomDeviceReservation labRoomDeviceReservation = labRoomDeviceReservationDAO.findLabRoomDeviceReservationById(id);
        labRoomDeviceReservation = labRoomReservationService.saveAuditResultDevice(labRoomDeviceReservation, labRoomDeviceReservationResult);
        return "redirect:/LabRoomDeviceReservation/teacherAudit?id=" + id + "&tage=" + tage + "&state=" + labRoomDeviceReservation.getState() + "&currpage=" + currpage;
    }

    /**********************************************************************************************************
     * 大仪预约审核/查看 --系主任 作者：孙虎  时间：：2017-09-27 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomDeviceReservation/deanAudit")
    public ModelAndView deanAuditDevice(@RequestParam int id, int tage, int state, Integer currpage) {
        ModelAndView mav = new ModelAndView();
        LabRoomDeviceReservation labRoomDeviceReservation = labRoomDeviceReservationDAO.findLabRoomDeviceReservationById(id);
        User user = shareService.getUserDetail();
        User auditUser = labRoomDeviceReservation.getUserByDean();
        //是否为审核人
        int isAudit = 0;
        // 判断是否链接与实际情况不符
        if(labRoomDeviceReservation.getState() != state){
            state = labRoomDeviceReservation.getState();
        }
        if (state == 2) {//在此审核人审核阶段
            if (auditUser.getUsername().equals(user.getUsername())) {//是审核人
                isAudit = 1;
            } else {
                mav.addObject("teacher", auditUser);
            }
        } else {//非此审核人审核阶段
            if (state < 2) {//未到此审核人审核阶段
                mav.addObject("teacher", auditUser);
            } else {//此审核人已审核完成阶段
                Set<LabRoomDeviceReservationResult> lReservationResults = labRoomDeviceReservation.getLabRoomDeviceReservationResults();
                for (LabRoomDeviceReservationResult t : lReservationResults) {
                    if (t.getTag() != null && t.getTag() == 2) {
                        //导师审核结果
                        mav.addObject("lStationReservationResult", t);
                    }
                }
            }
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("currpage", currpage);
        mav.setViewName("/device/device_reservation/deanAudit.jsp");
        return mav;
    }

        /**********************************************************************************************************
     * 保存大仪预约审核 --系主任 作者：孙虎  时间：：2017-09-27 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     * @throws NoSuchAlgorithmException
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomDeviceReservation/saveDeanAudit")
    public String saveDeanAuditDevice(@RequestParam int id, int tage, int state, Integer currpage, int auditResult, String remark) throws NoSuchAlgorithmException {
        LabRoomDeviceReservation labRoomDeviceReservation = labRoomDeviceReservationDAO.findLabRoomDeviceReservationById(id);
        LabRoomDeviceReservationResult labRoomDeviceReservationResult = new LabRoomDeviceReservationResult();
        labRoomDeviceReservationResult.setCDictionaryByCTrainingResult(shareService.getCDictionaryByCategory("c_training_result", String.valueOf(auditResult)));
        if (remark != null) {
            labRoomDeviceReservationResult.setRemark(remark);
        }
        labRoomDeviceReservation = labRoomReservationService.saveAuditResultDevice(labRoomDeviceReservation, labRoomDeviceReservationResult);
        return "redirect:/LabRoomDeviceReservation/deanAudit?id=" + id + "&tage=" + tage + "&state=" + labRoomDeviceReservation.getState() + "&currpage=" + currpage;
    }
    /**********************************************************************************************************
     * 大仪预约审核/查看 --实训室管理员 作者：孙虎  时间：：2017-09-28 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomDeviceReservation/labRoomAdminAudit")
    public ModelAndView labRoomAdminAuditDevice(@RequestParam int id, int tage, int state, Integer currpage) {
        ModelAndView mav = new ModelAndView();
        LabRoomDeviceReservation labRoomDeviceReservation = labRoomDeviceReservationDAO.findLabRoomDeviceReservationById(id);
        User user = shareService.getUserDetail();
        LabRoom labRoom = labRoomDeviceReservation.getLabRoomDevice().getLabRoom();
        //实验室管理员
        List<User> labRoomAdmins = labRoomDeviceService.findAdminByLrid(labRoom.getId());
        //当前登陆人是否为实验室管理员
        Boolean islabRoomAdmin = labRoomDeviceService.getLabRoomAdmin(labRoom.getId(), user.getUsername());
        //是否为审核人
        int isAudit = 0;
        // 判断是否链接与实际情况不符
        if(labRoomDeviceReservation.getState() != state){
            state = labRoomDeviceReservation.getState();
        }
        if (state == 3) {//在此审核人审核阶段
            if (islabRoomAdmin == true) {//是审核人
                isAudit = 1;
            } else {
                mav.addObject("labRoomAdmins", labRoomAdmins);
            }
        } else {//非此审核人审核阶段
            if (state < 3) {//未到此审核人审核阶段
                mav.addObject("labRoomAdmins", labRoomAdmins);
            } else {//此审核人已审核完成阶段
                Set<LabRoomDeviceReservationResult> lReservationResults = labRoomDeviceReservation.getLabRoomDeviceReservationResults();
                for (LabRoomDeviceReservationResult t : lReservationResults) {
                    if (t.getTag() != null && t.getTag() == 3) {
                        //实验室管理员审核结果
                        mav.addObject("lStationReservationResult", t);
                    }
                }
            }
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("currpage", currpage);
        mav.setViewName("/device/device_reservation/labRoomAdminAudit.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 保存大仪预约审核 --实训室管理员 作者：孙虎  时间：：2017-09-28 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     * @throws NoSuchAlgorithmException
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomDeviceReservation/savelabRoomAdminAudit")
    public String savelabRoomAdminAuditDevice(@RequestParam int id, int tage, int state, Integer currpage, int auditResult, String remark) throws NoSuchAlgorithmException {
        LabRoomDeviceReservation labRoomDeviceReservation = labRoomDeviceReservationDAO.findLabRoomDeviceReservationById(id);
        LabRoomDeviceReservationResult labRoomDeviceReservationResult = new LabRoomDeviceReservationResult();
        labRoomDeviceReservationResult.setCDictionaryByCTrainingResult(shareService.getCDictionaryByCategory("c_training_result", String.valueOf(auditResult)));
        if (remark != null) {
            labRoomDeviceReservationResult.setRemark(remark);
        }
        labRoomDeviceReservation = labRoomReservationService.saveAuditResultDevice(labRoomDeviceReservation, labRoomDeviceReservationResult);
        return "redirect:/LabRoomDeviceReservation/labRoomAdminAudit?id=" + id + "&tage=" + tage + "&state=" + labRoomDeviceReservation.getState() + "&currpage=" + currpage;
    }

    /**********************************************************************************************************
     * 大仪预约审核/查看 --实训室中心主任 作者：孙虎  时间：：2017-09-28 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomDeviceReservation/labRoomCenterDirectorAudit")
    public ModelAndView labRoomCenterDirectorAuditDevice(@RequestParam int id, int tage, int state, Integer currpage) {
        ModelAndView mav = new ModelAndView();
        LabRoomDeviceReservation labRoomDeviceReservation = labRoomDeviceReservationDAO.findLabRoomDeviceReservationById(id);
        User user = shareService.getUserDetail();
        LabRoom labRoom = labRoomDeviceReservation.getLabRoomDevice().getLabRoom();
        //实验室中心主任
        LabCenter labCenter = labRoom.getLabCenter();
        User labRoomCenterDirector = labCenter.getUserByCenterManager();
        //是否为审核人
        int isAudit = 0;
        // 判断是否链接与实际情况不符
        if(labRoomDeviceReservation.getState() != state){
            state = labRoomDeviceReservation.getState();
        }
        if (state == 4) {//在此审核人审核阶段
            if (labRoomCenterDirector.getUsername().equals(user.getUsername())) {//是审核人
                isAudit = 1;
            } else {
                mav.addObject("labRoomAdmins", labRoomCenterDirector);
            }
        } else {//非此审核人审核阶段
            if (state < 4) {//未到此审核人审核阶段
                mav.addObject("labRoomAdmins", labRoomCenterDirector);
            } else {//此审核人已审核完成阶段
                Set<LabRoomDeviceReservationResult> lReservationResults = labRoomDeviceReservation.getLabRoomDeviceReservationResults();
                for (LabRoomDeviceReservationResult t : lReservationResults) {
                    if (t.getTag() != null && t.getTag() == 4) {
                        //审核结果
                        mav.addObject("lStationReservationResult", t);
                    }
                }
            }
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("currpage", currpage);
        mav.setViewName("/device/device_reservation/labRoomCenterDirectorAudit.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 保存大仪预约审核 --实训室中心主任 作者：孙虎  时间：：2017-09-28 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     * @throws NoSuchAlgorithmException
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomDeviceReservation/savelabRoomCenterDirectorAudit")
    public String savelabRoomCenterDirectorAuditDevice(@RequestParam int id, int tage, int state, Integer currpage, int auditResult, String remark) throws NoSuchAlgorithmException {
        LabRoomDeviceReservation labRoomDeviceReservation = labRoomDeviceReservationDAO.findLabRoomDeviceReservationById(id);
        LabRoomDeviceReservationResult labRoomDeviceReservationResult = new LabRoomDeviceReservationResult();
        labRoomDeviceReservationResult.setCDictionaryByCTrainingResult(shareService.getCDictionaryByCategory("c_training_result", String.valueOf(auditResult)));
        if (remark != null) {
            labRoomDeviceReservationResult.setRemark(remark);
        }
        labRoomDeviceReservation = labRoomReservationService.saveAuditResultDevice(labRoomDeviceReservation, labRoomDeviceReservationResult);
        return "redirect:/LabRoomDeviceReservation/labRoomCenterDirectorAudit?id=" + id + "&tage=" + tage + "&state=" + labRoomDeviceReservation.getState() + "&currpage=" + currpage;
    }

    /**********************************************************************************************************
     * 大仪预约审核/查看 --实训室部主任 作者：孙虎  时间：：2017-09-28 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomDeviceReservation/labRoomDepartmentDirectorAudit")
    public ModelAndView labRoomDepartmentDirectorAuditDevice(@RequestParam int id, int tage, int state, Integer currpage) {
        ModelAndView mav = new ModelAndView();
        LabRoomDeviceReservation labRoomDeviceReservation = labRoomDeviceReservationDAO.findLabRoomDeviceReservationById(id);
        User user = shareService.getUserDetail();
        LabRoom labRoom = labRoomDeviceReservation.getLabRoomDevice().getLabRoom();
        //实验室中心主任
        List<User> labRoomCenterDirectors = shareService.findUsersByAuthorityId(3);
        //是否为审核人
        int isAudit = 0;
        // 判断是否链接与实际情况不符
        if(labRoomDeviceReservation.getState() != state){
            state = labRoomDeviceReservation.getState();
        }
        if (state == 5) {//在此审核人审核阶段
            for (User user2 : labRoomCenterDirectors) {
                if (user2.getUsername().equals(user.getUsername())) {//是审核人
                    isAudit = 1;
                } else {
                    mav.addObject("labRoomAdmins", labRoomCenterDirectors);
                }
            }

        } else {//非此审核人审核阶段
            if (state < 5) {//未到此审核人审核阶段
                mav.addObject("labRoomAdmins", labRoomCenterDirectors);
            } else {//此审核人已审核完成阶段
                Set<LabRoomDeviceReservationResult> lReservationResults = labRoomDeviceReservation.getLabRoomDeviceReservationResults();
                for (LabRoomDeviceReservationResult t : lReservationResults) {
                    if (t.getTag() != null && t.getTag() == 5) {
                        //审核结果
                        mav.addObject("lStationReservationResult", t);
                    }
                }
            }
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("currpage", currpage);
        mav.setViewName("/device/device_reservation/labRoomDepartmentDirectorAudit.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 保存实训室预约审核 --实训室部主任 作者：孙虎  时间：：2017-09-28 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     * @throws NoSuchAlgorithmException
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomDeviceReservation/savelabRoomDepartmentDirectorAudit")
    public String savelabRoomDepartmentDirectorAuditDevice(@RequestParam int id, int tage, int state, Integer currpage, int auditResult, String remark) throws NoSuchAlgorithmException {
        LabRoomDeviceReservation labRoomDeviceReservation = labRoomDeviceReservationDAO.findLabRoomDeviceReservationById(id);
        LabRoomDeviceReservationResult labRoomDeviceReservationResult = new LabRoomDeviceReservationResult();
        labRoomDeviceReservationResult.setCDictionaryByCTrainingResult(shareService.getCDictionaryByCategory("c_training_result", String.valueOf(auditResult)));
        if (remark != null) {
            labRoomDeviceReservationResult.setRemark(remark);
        }
        labRoomDeviceReservation = labRoomReservationService.saveAuditResultDevice(labRoomDeviceReservation, labRoomDeviceReservationResult);
        return "redirect:/LabRoomDeviceReservation/labRoomDepartmentDirectorAudit?id=" + id + "&tage=" + tage + "&state=" + labRoomDeviceReservation.getState() + "&currpage=" + currpage;
    }

    /**********************************************************************************************************
     * 软件安装申请审核/查看  作者：孙虎  时间：：2017-11-5 state:2系主任3实训室管理员4实训系主任5实训部主任
     ***********************************************************************************************************/
    @RequestMapping("/SoftwareReservation/checkButton")
    public ModelAndView checkButtonforSoftware(@RequestParam int id, int tage, int state, Integer page) {
        ModelAndView mav = new ModelAndView();
        SoftwareReserve softwareReserve = softwareReserveDAO.findSoftwareReserveById(id);
        LabRoom labRoom = softwareReserve.getLabRoom();
        mav.addObject("state", state);
        mav.addObject("softwareReserve", softwareReserve);
        mav.addObject("labRoom", labRoom);
        mav.addObject("tage", tage);
        mav.addObject("page", page);
        mav.setViewName("/labroom/software/softwareReaervationAudit.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 软件安装审核/查看 --系主任 作者：孙虎  时间：：2017-11-5 state:2系主任3实训室管理员4实训系主任5实训部主任
     ***********************************************************************************************************/
    @RequestMapping("/SoftwareReservation/deanAudit")
    public ModelAndView deanAuditforSoftware(@RequestParam int id, int tage, int state, Integer page) {
        ModelAndView mav = new ModelAndView();
        SoftwareReserve softwareReserve = softwareReserveDAO.findSoftwareReserveById(id);
        User user = shareService.getUserDetail();
        List<User> deans = shareService.findAllDepartmentHead(user);
        //是否为审核人
        int isAudit = 0;
        // 更新状态，防止从个人消息进入时状态参数错误导致重新审核
        state = softwareReserve.getState();
        if (state == 2) {//在此审核人审核阶段
            if (deans != null) {
                for (User user2 : deans) {
                    if (user2.getUsername().equals(user.getUsername())) {
                        isAudit = 1;
                        break;
                    }
                }
            }
        } else {//非此审核人审核阶段
            if (state > 2) {//此审核人已审核完成阶段
                Set<SoftwareReserveAudit> lReservationResults = softwareReserve.getSoftwareReserveAudits();
                if (lReservationResults != null) {
                    for (SoftwareReserveAudit t : lReservationResults) {
                        if (t.getStatus() == 2) {
                            //系主任审核结果
                            mav.addObject("lStationReservationResult", t);
                        }
                    }
                }
            }
        }
        mav.addObject("deans", deans);
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("page", page);
        mav.setViewName("/labroom/software/softwaredeanAudit.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 保存系主任审核 -- 作者：孙虎  时间：：2017-11-5 state:1系主任2实训室管理员3实训系主任4实训部主任
     * @throws NoSuchAlgorithmException
     ***********************************************************************************************************/
    @Transactional
    @RequestMapping("/SoftwareReservation/saveDeanAudit")
    public String saveDeanAuditforSoftware(@RequestParam int id, int tage, int state, Integer page, String auditResult, String remark) throws NoSuchAlgorithmException, InterruptedException {
        SoftwareReserve softwareReserve = softwareReserveDAO.findSoftwareReserveById(id);
        SoftwareReserveAudit softwareReserveAudit = new SoftwareReserveAudit();
        softwareReserveAudit.setResult(auditResult);
        if (remark != null) {
            softwareReserveAudit.setMem(remark);
        }
        softwareReserve = labRoomReservationService.saveAuditResultForSoftware(softwareReserve, softwareReserveAudit);
        return "redirect:/SoftwareReservation/deanAudit?id=" + id + "&tage=" + tage + "&state=" + softwareReserve.getState() + "&page=" + page;
    }

    /**********************************************************************************************************
     * 软件安装 --实训室管理员 作者：孙虎  时间：：2017-09-28 state:2系主任3实训室管理员4实训系主任5实训部主任
     ***********************************************************************************************************/
    @RequestMapping("/SoftwareReservation/labRoomAdminAudit")
    public ModelAndView labRoomAdminAuditForSoftware(@RequestParam int id, int tage, int state, Integer page) {
        ModelAndView mav = new ModelAndView();
        SoftwareReserve softwareReserve = softwareReserveDAO.findSoftwareReserveById(id);
        User user = shareService.getUserDetail();
        LabRoom labRoom = softwareReserve.getLabRoom();
        //实验室管理员
        List<User> labRoomAdmins = labRoomDeviceService.findAdminByLrid(labRoom.getId());
        //当前登陆人是否为实验室管理员
        Boolean islabRoomAdmin = labRoomDeviceService.getLabRoomAdmin(labRoom.getId(), user.getUsername());
        //是否为审核人
        int isAudit = 0;
        // 更新状态，防止从个人消息进入时状态参数错误导致重新审核
        state = softwareReserve.getState();
        if (state == 3) {//在此审核人审核阶段
            if (islabRoomAdmin == true) {//是审核人
                isAudit = 1;
            } else {
                mav.addObject("labRoomAdmins", labRoomAdmins);
            }
        } else {//非此审核人审核阶段
            if (state < 3) {//未到此审核人审核阶段
                mav.addObject("labRoomAdmins", labRoomAdmins);
            } else {//此审核人已审核完成阶段
                Set<SoftwareReserveAudit> lReservationResults = softwareReserve.getSoftwareReserveAudits();
                for (SoftwareReserveAudit t : lReservationResults) {
                    if (t.getStatus() == 3) {
                        //实验室管理员审核结果
                        mav.addObject("lStationReservationResult", t);
                    }
                }
            }
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("page", page);
        mav.setViewName("/labroom/software/softwarelabRoomAdminAudit.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 保存实训室管理员审核 -- 作者：孙虎  时间：：2017-11-5 state:2系主任3实训室管理员4实训系主任5实训部主任
     * @throws NoSuchAlgorithmException
     ***********************************************************************************************************/
    @Transactional
    @RequestMapping("/SoftwareReservation/savelabRoomAdminAudit")
    public String savelabRoomAdminAuditForSoftware(@RequestParam int id, int tage, int state, Integer currpage, String auditResult, String remark) throws Exception {
        SoftwareReserve softwareReserve = softwareReserveDAO.findSoftwareReserveById(id);
        SoftwareReserveAudit softwareReserveAudit = new SoftwareReserveAudit();
        softwareReserveAudit.setResult(auditResult);
        if (remark != null) {
            softwareReserveAudit.setMem(remark);
        }
        softwareReserve = labRoomReservationService.saveAuditResultForSoftware(softwareReserve, softwareReserveAudit);
        return "redirect:/SoftwareReservation/labRoomAdminAudit?id=" + id + "&tage=" + tage + "&state=" + softwareReserve.getState() + "&currpage=" + currpage;
    }

    /**********************************************************************************************************
     * 软件安装 --实训室中心主任 作者：孙虎  时间：：2017-11-5 state:2系主任3实训室管理员4实训系主任5实训部主任
     ***********************************************************************************************************/
    @RequestMapping("/SoftwareReservation/labRoomCenterDirectorAudit")
    public ModelAndView labRoomCenterDirectorAuditForSoftware(@RequestParam int id, int tage, int state, Integer page) {
        ModelAndView mav = new ModelAndView();
        SoftwareReserve softwareReserve = softwareReserveDAO.findSoftwareReserveById(id);
        User user = shareService.getUserDetail();
        LabRoom labRoom = softwareReserve.getLabRoom();
        //实验室中心主任
        LabCenter labCenter = labRoom.getLabCenter();
        User labRoomCenterDirector = labCenter.getUserByCenterManager();
        //是否为审核人
        int isAudit = 0;
        // 更新状态，防止从个人消息进入时状态参数错误导致重新审核
        state = softwareReserve.getState();
        if (state == 4) {//在此审核人审核阶段
            if (labRoomCenterDirector.getUsername().equals(user.getUsername())) {//是审核人
                isAudit = 1;
            } else {
                mav.addObject("labRoomAdmins", labRoomCenterDirector);
            }
        } else {//非此审核人审核阶段
            if (state < 4) {//未到此审核人审核阶段
                mav.addObject("labRoomAdmins", labRoomCenterDirector);
            } else {//此审核人已审核完成阶段
                Set<SoftwareReserveAudit> lReservationResults = softwareReserve.getSoftwareReserveAudits();
                for (SoftwareReserveAudit t : lReservationResults) {
                    if (t.getStatus() == 4) {
                        //审核结果
                        mav.addObject("lStationReservationResult", t);
                    }
                }
            }
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("page", page);
        mav.setViewName("/labroom/software/softwarelabRoomCenterDirectorAudit.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 保存-实训室中心主任审核 - 作者：孙虎  时间：：2017-09-28 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     * @throws NoSuchAlgorithmException
     ***********************************************************************************************************/
    @Transactional
    @RequestMapping("/SoftwareReservation/savelabRoomCenterDirectorAudit")
    public String savelabRoomCenterDirectorAuditForSoftware(@RequestParam int id, int tage, int state, Integer page, String auditResult, String remark) throws Exception {
        SoftwareReserve softwareReserve = softwareReserveDAO.findSoftwareReserveById(id);
        SoftwareReserveAudit softwareReserveAudit = new SoftwareReserveAudit();
        softwareReserveAudit.setResult(auditResult);
        if (remark != null) {
            softwareReserveAudit.setMem(remark);
        }
        softwareReserve = labRoomReservationService.saveAuditResultForSoftware(softwareReserve, softwareReserveAudit);
        return "redirect:/SoftwareReservation/labRoomCenterDirectorAudit?id=" + id + "&tage=" + tage + "&state=" + softwareReserve.getState() + "&page=" + page;
    }

    /**********************************************************************************************************
     * 软件安装 --实训室部主任 作者：孙虎  时间：：2017-11-5 state:2系主任3实训室管理员4实训系主任5实训部主任
     ***********************************************************************************************************/
    @RequestMapping("/SoftwareReservation/labRoomDepartmentDirectorAudit")
    public ModelAndView labRoomDepartmentDirectorAuditForSoftware(@RequestParam int id, int tage, int state, Integer page) {
        ModelAndView mav = new ModelAndView();
        SoftwareReserve softwareReserve = softwareReserveDAO.findSoftwareReserveById(id);
        User user = shareService.getUserDetail();
        LabRoom labRoom = softwareReserve.getLabRoom();
        //实验室部主任
        List<User> labRoomCenterDirectors = shareService.findUsersByAuthorityId(22);
        //是否为审核人
        int isAudit = 0;
        // 更新状态，防止从个人消息进入时状态参数错误导致重新审核
        state = softwareReserve.getState();
        if (state == 5) {//在此审核人审核阶段
            for (User user2 : labRoomCenterDirectors) {
                if (user2.getUsername().equals(user.getUsername())) {//是审核人
                    isAudit = 1;
                } else {
                    mav.addObject("labRoomAdmins", labRoomCenterDirectors);
                }
            }

        } else {//非此审核人审核阶段
            if (state < 5) {//未到此审核人审核阶段
                mav.addObject("labRoomAdmins", labRoomCenterDirectors);
            } else {//此审核人已审核完成阶段
                Set<SoftwareReserveAudit> lReservationResults = softwareReserve.getSoftwareReserveAudits();
                for (SoftwareReserveAudit t : lReservationResults) {
                    if (t.getStatus() == 5) {
                        //审核结果
                        mav.addObject("lStationReservationResult", t);
                    }
                }
            }
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("page", page);
        mav.setViewName("/labroom/software/softwarelabRoomDepartmentDirectorAudit.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 保存实训室部主任审核 -- 作者：孙虎  时间：：2017-11-5 state:2系主任3实训室管理员4实训系主任5实训部主任
     * @throws NoSuchAlgorithmException
     ***********************************************************************************************************/
    @Transactional
    @RequestMapping("/SoftwareReservation/savelabRoomDepartmentDirectorAudit")
    public String savelabRoomDepartmentDirectorAuditForSoftware(@RequestParam int id, int tage, int state, Integer page, String auditResult, String remark) throws Exception {
        SoftwareReserve softwareReserve = softwareReserveDAO.findSoftwareReserveById(id);
        SoftwareReserveAudit softwareReserveAudit = new SoftwareReserveAudit();
        softwareReserveAudit.setResult(auditResult);
        if (remark != null) {
            softwareReserveAudit.setMem(remark);
        }
        softwareReserve = labRoomReservationService.saveAuditResultForSoftware(softwareReserve, softwareReserveAudit);
        return "redirect:/SoftwareReservation/labRoomDepartmentDirectorAudit?id=" + id + "&tage=" + tage + "&state=" + softwareReserve.getState() + "&page=" + page;
    }

    /**********************************************************************************************************
     * 删除软件安装申请 -- 作者：孙虎  时间：2017-11-6
     ***********************************************************************************************************/
    @RequestMapping("/SoftwareReservation/delete")
    public String deleteSoftwareReserve(@RequestParam int id, int tage, int state, Integer page, @ModelAttribute("isAudit") Integer isaudit) {
        SoftwareReserve softwareReserve = softwareReserveDAO.findSoftwareReserveById(id);
        softwareReserveDAO.remove(softwareReserve);
        softwareReserveDAO.flush();
        return "redirect:/labRoom/SoftwareReserveList?tage=" + tage + "&page=" + page + "&isaudit=" + isaudit;
    }

    /**********************************************************************************************************
     * 实训室使用情况表 -- 作者：孙虎  时间：2017-11-18
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomUseTable")
    public ModelAndView LabRoomUseTable(@RequestParam int termId,int roomId) {
        ModelAndView mav = new ModelAndView();
        LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(roomId);
        // 获取实验分室年使用频率正常教学（人时数）
        mav.addObject("labTimetable", teachingReportService.getLabTimetableAppointmentHours(termId, roomId));
        // 获取实验分室年使用频率预约开放（人时数）
        mav.addObject("labSelfTimetable", teachingReportService.getLabTimetableAppointmentSelfHours(termId, roomId));
        //获取实验分室的校外开放人时数
        mav.addObject("labOpenHour", teachingReportService.getLabTimetableAppointmentSelfHours(termId, roomId));
        StringBuffer hql = new StringBuffer("select distinct c from VisitingRegistration c tt where 1=1 ");

        mav.addObject("labRoom", labRoom);
        mav.addObject("BigZero", labRoom.getLabRoomArea().compareTo(BigDecimal.ZERO));
        mav.setViewName("/labroom/LabRoomUseTable.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 新建实训室使用登记 -- 作者：孙虎  时间：2017-11-21
     ***********************************************************************************************************/
    @RequestMapping("/timetable/newLabroomTimetableRegister")
    public ModelAndView newLabroomTimetableRegister() {
        ModelAndView mav = new ModelAndView();
        LabroomTimetableRegister labroomTimetableRegister = new LabroomTimetableRegister();
        mav.addObject("labroomTimetableRegister", labroomTimetableRegister);
        mav.addObject("labList", systemService.loadLabRooms());
        mav.addObject("schoolCourses", schoolCourseDAO.findAllSchoolCourses());
        mav.addObject("operationItems", operationItemDAO.findAllOperationItems());
        mav.addObject("schoolClasses", schoolClassesDAO.findAllSchoolClassess());
        mav.addObject("teachers", shareService.findAllTeacheres());
        mav.addObject("weeks", schoolWeekService.getSchoolWeekbyTerm(shareService.getBelongsSchoolTerm(Calendar.getInstance())));
        mav.addObject("devices", schoolDeviceDAO.findAllSchoolDevices());
        mav.addObject("softwares", softwareDAO.findAllSoftwares());
        mav.setViewName("/timetable/timetableAdmin/newLabroomTimetableRegister.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 保存实训室使用登记-- 作者：孙虎  时间：2017-11-22
     ***********************************************************************************************************/
    @Transactional
    @RequestMapping("/timetable/saveLabroomTimetableRegister")
    public String saveLabroomTimetableRegister(@ModelAttribute LabroomTimetableRegister labroomTimetableRegister) {
        labroomTimetableRegister.setTotalWeek(labroomTimetableRegister.getEndWeek() + 1 - labroomTimetableRegister.getStartWeek());
        labroomTimetableRegister.setTotalClass(labroomTimetableRegister.getEndClass() + 1 - labroomTimetableRegister.getStartClass());
        labroomTimetableRegisterDAO.store(labroomTimetableRegister);
        labroomTimetableRegisterDAO.flush();
        return "redirect:/timetable/timetableAdminIframe?currpage=1&id=-1&status=1&isposted=1";
    }

    /****************************************************************************
     * Description：调课申请/审核列表
     *
     * @author:孙虎
     * @date:2017-11-24
     ****************************************************************************/
    @RequestMapping("/timetable/timetableChangeAuditList")
    public ModelAndView timetableChangeAuditList(@ModelAttribute TimetableAppointmentChange timetableAppointmentChange, Integer page, Integer isaudit, Integer tage) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("timetableAppointmentChange", timetableAppointmentChange);
        User user = shareService.getUserDetail();
        // 查询出所有的调课申请
        int pageSize = 10;// 每页10条记录
        int totalRecords = timetableAppointmentService.findAllTimetableAppointmentChange(timetableAppointmentChange, page, pageSize, tage, isaudit).size();
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<TimetableAppointmentChange> timetableAppointmentChanges = timetableAppointmentService.findAllTimetableAppointmentChange(timetableAppointmentChange, page, pageSize, tage, isaudit);
        //判断所处审核阶段，关联到前端的按钮
        if (timetableAppointmentChanges != null) {
            for (TimetableAppointmentChange timetableAppointmentChange2 : timetableAppointmentChanges) {
                //先初始化为0
                timetableAppointmentChange2.setButtonMark(0);
                if (timetableAppointmentChange2.getState() != null) {
                    //系主任审核阶段
                    if (timetableAppointmentChange2.getState() == 2) {
                        //当前登陆人是审核系主任
                        List<User> deans = shareService.findDeansByAcademyNumber(timetableAppointmentChange2.getUser().getSchoolAcademy());
                        if (deans != null) {
                            for (User user2 : deans) {
                                if (user2.getUsername().equals(user.getUsername())) {
                                    timetableAppointmentChange2.setButtonMark(2);
                                    break;
                                }
                            }
                        }
                    }
                    //实训室管理员审核阶段
                    if (timetableAppointmentChange2.getState() == 3) {
                        //当前登陆人是审核实训室管理员
                        LabRoom labRoom = new LabRoom();
                        //判断内部调课还是外部调课
                        if (timetableAppointmentChange2.getFlag() == 1) {
                            labRoom = timetableAppointmentChange2.getLabRoom();
                        } else {
                            Set<TimetableLabRelated> appId = timetableAppointmentChange2.getTimetableAppointment().getTimetableLabRelateds();
                            for (Iterator iterator = appId.iterator(); iterator.hasNext(); ) {
                                TimetableLabRelated timetableLabRelated = (TimetableLabRelated) iterator.next();
                                labRoom = timetableLabRelated.getLabRoom();
                            }
                        }
                        if (labRoomDeviceService.getLabRoomAdmin(labRoom.getId(), user.getUsername()) == true) {
                            timetableAppointmentChange2.setButtonMark(3);
                        }
                    }
					/*实训中心主任审核阶段
					if(softwareReserve2.getState() == 4){
						//当前登陆人是审核实验室中心主任
						if(user.getUsername().equals(softwareReserve2.getLabRoom().getLabCenter().getUserByCenterManager().getUsername())){
							softwareReserve2.setButtonMark(4);
						}
					}*/
                    //实训部主任审核阶段
                    if (timetableAppointmentChange2.getState() == 5) {
                        //当前登陆人是审核实训部主任
                        List<User> labRoomCenterDirectors = shareService.findUsersByAuthorityId(22);
                        if (labRoomCenterDirectors != null) {
                            for (User user2 : labRoomCenterDirectors) {
                                if (user.getUsername().equals(user2.getUsername())) {
                                    timetableAppointmentChange2.setButtonMark(5);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        mav.addObject("timetableAppointmentChanges", timetableAppointmentChanges);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.addObject("isAudit", isaudit);
        mav.addObject("tage", tage);
        mav.addObject("user", user);
        mav.setViewName("/timetable/timetableChangeAudit/timetableChangeAuditList.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 调课申请审核/查看  作者：孙虎  时间：：2017-11-5 state:2系主任3实训室管理员4实训部秘书
     ***********************************************************************************************************/
    @RequestMapping("/timetableChangeAudit/checkButton")
    public ModelAndView checkButtonfortimetableChange(@RequestParam int id, int tage, int state, Integer page) {
        ModelAndView mav = new ModelAndView();
        TimetableAppointmentChange appointmentChange = timetableAppointmentChangeDAO.findTimetableAppointmentChangeById(id);
        mav.addObject("state", state);
        mav.addObject("appointmentChange", appointmentChange);
        mav.addObject("tage", tage);
        mav.addObject("page", page);
        mav.setViewName("/timetable/timetableChangeAudit/timetableChangeAudit.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 软件安装审核/查看 --系主任 作者：孙虎  时间：：2017-11-5 state:2系主任3实训室管理员4实训系主任5实训部主任
     ***********************************************************************************************************/
    @RequestMapping("/timetableChangeAudit/deanAudit")
    public ModelAndView deanAuditfortimetable(@RequestParam int id, int tage, int state, Integer page) {
        ModelAndView mav = new ModelAndView();
        TimetableAppointmentChange appointmentChange = timetableAppointmentChangeDAO.findTimetableAppointmentChangeById(id);
        User user = shareService.getUserDetail();
        List<User> deans = shareService.findDeansByAcademyNumber(user.getSchoolAcademy());
        //是否为审核人
        int isAudit = 0;
        if (state == 2) {//在此审核人审核阶段
            if (deans != null) {
                for (User user2 : deans) {
                    if (user2.getUsername().equals(user.getUsername())) {
                        isAudit = 1;
                        break;
                    }
                }
            }
        } else {//非此审核人审核阶段
            if (state > 2) {//此审核人已审核完成阶段
                Set<TimetableAppointmentChangeAduit> lReservationResults = appointmentChange.getTimetableAppointmentChangeAduits();
                if (lReservationResults != null) {
                    for (TimetableAppointmentChangeAduit t : lReservationResults) {
                        if (t.getStatus() == 2) {
                            //系主任审核结果
                            mav.addObject("lStationReservationResult", t);
                        }
                    }
                }
            }
        }
        mav.addObject("deans", deans);
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("page", page);
        mav.setViewName("/timetable/timetableChangeAudit/timetabledeanAudit.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 保存系主任审核 -- 作者：孙虎  时间：：2017-11-5 state:1系主任2实训室管理员3实训系主任4实训部主任
     * @throws NoSuchAlgorithmException
     ***********************************************************************************************************/
    @Transactional
    @RequestMapping("/timetableChangeAudit/saveDeanAudit")
    public String saveDeanAuditfortimetable(@RequestParam int id, int tage, int state, Integer page, String auditResult, String remark) throws NoSuchAlgorithmException {
        TimetableAppointmentChange appointmentChange = timetableAppointmentChangeDAO.findTimetableAppointmentChangeById(id);
        TimetableAppointmentChangeAduit aduit = new TimetableAppointmentChangeAduit();
        aduit.setResult(auditResult);
        if (remark != null) {
            aduit.setMem(remark);
        }
        appointmentChange = timetableAppointmentService.saveAuditResultForTimetable(appointmentChange, aduit);
        return "redirect:/timetableChangeAudit/deanAudit?id=" + id + "&tage=" + tage + "&state=" + appointmentChange.getState() + "&page=" + page;
    }

    /**********************************************************************************************************
     * 软件安装 --实训室管理员 作者：孙虎  时间：：2017-09-28 state:2系主任3实训室管理员4实训系主任5实训部主任
     ***********************************************************************************************************/
    @RequestMapping("/timetableChangeAudit/labRoomAdminAudit")
    public ModelAndView labRoomAdminAuditFortimetable(@RequestParam int id, int tage, int state, Integer page) {
        ModelAndView mav = new ModelAndView();
        TimetableAppointmentChange appointmentChange = timetableAppointmentChangeDAO.findTimetableAppointmentChangeById(id);
        User user = shareService.getUserDetail();
        LabRoom labRoom = new LabRoom();
        //判断内部调课还是外部调课
        if (appointmentChange.getFlag() == 1) {
            labRoom = appointmentChange.getLabRoom();
        } else {
            Set<TimetableLabRelated> appId = appointmentChange.getTimetableAppointment().getTimetableLabRelateds();
            for (Iterator iterator = appId.iterator(); iterator.hasNext(); ) {
                TimetableLabRelated timetableLabRelated = (TimetableLabRelated) iterator.next();
                labRoom = timetableLabRelated.getLabRoom();
            }
        }
        //实验室管理员
        List<User> labRoomAdmins = labRoomDeviceService.findAdminByLrid(labRoom.getId());
        //当前登陆人是否为实验室管理员
        Boolean islabRoomAdmin = labRoomDeviceService.getLabRoomAdmin(labRoom.getId(), user.getUsername());
        //是否为审核人
        int isAudit = 0;
        if (state == 3) {//在此审核人审核阶段
            if (islabRoomAdmin == true) {//是审核人
                isAudit = 1;
            } else {
                mav.addObject("labRoomAdmins", labRoomAdmins);
            }
        } else {//非此审核人审核阶段
            if (state < 3) {//未到此审核人审核阶段
                mav.addObject("labRoomAdmins", labRoomAdmins);
            } else {//此审核人已审核完成阶段
                Set<TimetableAppointmentChangeAduit> lReservationResults = appointmentChange.getTimetableAppointmentChangeAduits();
                for (TimetableAppointmentChangeAduit t : lReservationResults) {
                    if (t.getStatus() == 3) {
                        //实验室管理员审核结果
                        mav.addObject("lStationReservationResult", t);
                    }
                }
            }
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("page", page);
        mav.setViewName("/timetable/timetableChangeAudit/timetablelabRoomAdminAudit.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 保存实训室管理员审核 -- 作者：孙虎  时间：：2017-11-5 state:2系主任3实训室管理员4实训系主任5实训部主任
     * @throws NoSuchAlgorithmException
     ***********************************************************************************************************/
    @Transactional
    @RequestMapping("/timetableChangeAudit/savelabRoomAdminAudit")
    public String savelabRoomAdminAuditFortimetable(@RequestParam int id, int tage, int state, Integer currpage, String auditResult, String remark) throws NoSuchAlgorithmException {
        TimetableAppointmentChange appointmentChange = timetableAppointmentChangeDAO.findTimetableAppointmentChangeById(id);
        TimetableAppointmentChangeAduit aduit = new TimetableAppointmentChangeAduit();
        aduit.setResult(auditResult);
        if (remark != null) {
            aduit.setMem(remark);
        }
        appointmentChange = timetableAppointmentService.saveAuditResultForTimetable(appointmentChange, aduit);
        return "redirect:/timetableChangeAudit/labRoomAdminAudit?id=" + id + "&tage=" + tage + "&state=" + appointmentChange.getState() + "&currpage=" + currpage;
    }

    /**********************************************************************************************************
     * 软件安装 --实训室部主任 作者：孙虎  时间：：2017-11-5 state:2系主任3实训室管理员4实训系主任5实训部主任
     ***********************************************************************************************************/
    @RequestMapping("/timetableChangeAudit/labRoomDepartmentDirectorAudit")
    public ModelAndView labRoomDepartmentDirectorAuditFortimetable(@RequestParam int id, int tage, int state, Integer page) {
        ModelAndView mav = new ModelAndView();
        TimetableAppointmentChange appointmentChange = timetableAppointmentChangeDAO.findTimetableAppointmentChangeById(id);
        User user = shareService.getUserDetail();
        LabRoom labRoom = appointmentChange.getLabRoom();
        //实验室部秘书
        List<User> labRoomCenterDirectors = shareService.findUsersByAuthorityId(22);
        //是否为审核人
        int isAudit = 0;
        if (state == 5) {//在此审核人审核阶段
            for (User user2 : labRoomCenterDirectors) {
                if (user2.getUsername().equals(user.getUsername())) {//是审核人
                    isAudit = 1;
                } else {
                    mav.addObject("labRoomAdmins", labRoomCenterDirectors);
                }
            }

        } else {//非此审核人审核阶段
            if (state < 5) {//未到此审核人审核阶段
                mav.addObject("labRoomAdmins", labRoomCenterDirectors);
            } else {//此审核人已审核完成阶段
                Set<TimetableAppointmentChangeAduit> lReservationResults = appointmentChange.getTimetableAppointmentChangeAduits();
                for (TimetableAppointmentChangeAduit t : lReservationResults) {
                    if (t.getStatus() == 5) {
                        //审核结果
                        mav.addObject("lStationReservationResult", t);
                    }
                }
            }
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("page", page);
        mav.setViewName("/timetable/timetableChangeAudit/timetablelabRoomDepartmentDirectorAudit.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 保存实训室部主任审核 -- 作者：孙虎  时间：：2017-11-5 state:2系主任3实训室管理员4实训系主任5实训部主任
     * @throws NoSuchAlgorithmException
     ***********************************************************************************************************/
    @Transactional
    @RequestMapping("/timetableChangeAudit/savelabRoomDepartmentDirectorAudit")
    public String savelabRoomDepartmentDirectorAuditFortimetable(@RequestParam int id, int tage, int state, Integer page, String auditResult, String remark) throws NoSuchAlgorithmException {
        TimetableAppointmentChange appointmentChange = timetableAppointmentChangeDAO.findTimetableAppointmentChangeById(id);
        TimetableAppointmentChangeAduit aduit = new TimetableAppointmentChangeAduit();
        aduit.setResult(auditResult);
        if (remark != null) {
            aduit.setMem(remark);
        }
        appointmentChange = timetableAppointmentService.saveAuditResultForTimetable(appointmentChange, aduit);
        return "redirect:/timetableChangeAudit/labRoomDepartmentDirectorAudit?id=" + id + "&tage=" + tage + "&state=" + appointmentChange.getState() + "&page=" + page;
    }

    /**********************************************************************************************************
     * 大仪预约审核/查看 --系主任 作者：孙虎  时间：：2017-09-27 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomDeviceReservation/deviceReservationAllAudit")
    public ModelAndView deviceReservationAllAudit(@RequestParam int id, int tage,Integer state, Integer currpage, String authName, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        LabRoomDeviceReservation labRoomDeviceReservation = labRoomDeviceReservationDAO.findLabRoomDeviceReservationById(id);
        User user = shareService.getUserDetail();
        //是否为审核人
        int isAudit = 0;
        int curStage = 0;
        String curAuthName = "";
        // 从审核服务获取审核数据
        Map<String, String> currAuditParams = new HashMap<>();
        String businessType = "DeviceReservation" + labRoomDeviceReservation.getLabRoomDevice().getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber();
        currAuditParams.put("businessType", pConfig.PROJECT_NAME + businessType);
        currAuditParams.put("businessAppUid", labRoomDeviceReservation.getId().toString());
        String currAuditStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", currAuditParams);
        JSONObject currAuditJSONObject = JSONObject.parseObject(currAuditStr);
        if("success".equals(currAuditJSONObject.getString("status")) &&
            currAuditJSONObject.getJSONArray("data") != null &&
                currAuditJSONObject.getJSONArray("data").size() > 0){
            curStage = currAuditJSONObject.getJSONArray("data").getJSONObject(0).getIntValue("level");
            curAuthName = currAuditJSONObject.getJSONArray("data").getJSONObject(0).getString("result");
        }
        List<User> auditUser = labRoomDeviceReservationService.getAuditUser(authName, labRoomDeviceReservation);
        if (state == curStage) {//在当前登录用户为审核人的审核阶段-state为生成审核记录时保存的状态
            boolean flag = false;
            for(User u: auditUser){
                if(u.getUsername().equals(user.getUsername())){
                    flag = true;
                    break;
                }
            }
            if (flag && request.getSession().getAttribute("selected_role").equals("ROLE_" + curAuthName)) {//是审核人
                isAudit = 1;
            } else {
                mav.addObject("auditUser", auditUser);
            }
        } else {//非此审核人审核阶段
            if (curStage < state && curStage != 0 && curStage != -1) {//未到此审核人审核阶段
                mav.addObject("auditUser", auditUser);
            } else {//此审核人已审核完成阶段
                mav.addObject("result", labRoomDeviceReservationService.getCurrJSONObject(labRoomDeviceReservation, state));
            }
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("curStage", curStage);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("currpage", currpage);
        mav.addObject("authName", authName);
        mav.setViewName("/device/device_reservation/deviceReservationAllAudit.jsp");
        return mav;
    }

    /**********************************************************************************************************
     * 保存大仪预约审核 --系主任 作者：孙虎  时间：：2017-09-27 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     * @throws NoSuchAlgorithmException
     ***********************************************************************************************************/
    @RequestMapping("/LabRoomDeviceReservation/saveAudit")
    public String saveAuditDevice(@RequestParam int id, int tage, int state, Integer currpage, int auditResult, String remark, String authName) throws NoSuchAlgorithmException {
        LabRoomDeviceReservation labRoomDeviceReservation = labRoomDeviceReservationDAO.findLabRoomDeviceReservationById(id);
        labRoomDeviceReservation = labRoomReservationService.saveAuditResultDevice(labRoomDeviceReservation, auditResult, remark);
        return "redirect:/LabRoomDeviceReservation/deviceReservationAllAudit?id=" + id + "&tage=" + tage + "&state=" + state + "&currpage=" + currpage + "&authName=" + authName;
    }
    
    /**********************************************************************************************************
     * 保存大仪预约审核 --单级角色批量审核 作者：孙虎  时间：：2017-09-27 state:1导师审核2系主任3实训室管理员4实训系主任5实训部主任
     * @throws NoSuchAlgorithmException
     * update Hezhaoyi 2019-3-22
     ***********************************************************************************************************/
    @ResponseBody
    @RequestMapping(value = "/LabRoomDeviceReservation/saveAuditBatch", method = RequestMethod.POST)
    public String saveAuditDeviceBatch(@RequestBody ArrayList<LinkedHashMap>list)throws Exception {

        for(Map<String, String> Map:list){
            int id = Integer.parseInt(Map.get("resId"));
            int auditResult = Integer.parseInt(Map.get("resultId"));
            if(auditResult == 619){
                auditResult = 1;
            }else {
                auditResult = 0;
            }
            String remark = Map.get("remark");
            LabRoomDeviceReservation labRoomDeviceReservation = labRoomDeviceReservationDAO.findLabRoomDeviceReservationById(id);
            labRoomReservationService.saveAuditResultDevice(labRoomDeviceReservation, auditResult, remark);
       }
        return "success";
    }

}