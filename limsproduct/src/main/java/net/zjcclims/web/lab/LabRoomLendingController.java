package net.zjcclims.web.lab;

import api.net.gvsunlims.constant.ConstantInterface;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.device.LabRoomDeviceService;
import net.zjcclims.service.lab.*;
import net.zjcclims.service.message.MessageService;
import net.zjcclims.service.system.SystemService;
import net.zjcclims.util.HttpClientUtil;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description 实训室借用模块
 *
 * @param <JsonResult>
 * @author CLW
 * @date 2017-10-10
 */

@Controller("LabRoomLendingController")
@RequestMapping("/labRoomLending")
@SessionAttributes("selected_academy")
public class LabRoomLendingController<JsonResult> {
    @Autowired
    private ShareService shareService;
    @Autowired
    private LabRoomService labRoomService;
    @Autowired
    private LabReservationService labReservationService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private LabRoomLendingService labRoomLendingService;
    @Autowired
    private LabLendAuditDAO labLendAuditDAO;
    @Autowired
    private MessageService messageService;
    @Autowired
    private LabRoomLendingDAO labRoomLendingDAO;
    @Autowired
    private LabReservationDAO labReservationDAO;
    @Autowired
    private LabReservationAuditDAO labReservationAuditDAO;
    @Autowired
    private LabRoomAdminService labRoomAdminService;
    @Autowired
    private MessageDAO messageDAO;
    @Autowired
    private LabRoomDeviceService labRoomDeviceService;
    @Autowired
    private LabRoomReservationService labRoomReservationService;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private AuthorityDAO authorityDAO;
    @Autowired
    private TimetableAppointmentDAO timetableAppointmentDAO;
    @Autowired
    private SystemTimeDAO systemTimeDAO;
    @Autowired
    private TimetableAppointmentSameNumberDAO timetableAppointmentSameNumberDAO;
    @Autowired
    private TimetableLabRelatedDAO timetableLabRelatedDAO;
    @Autowired
    PConfig pConfig;
    @Autowired
    private LabReservationTimeTableDAO labReservationTimeTableDAO;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private TimetableTeacherRelatedDAO timetableTeacherRelatedDAO;
    @Autowired
    private LabRoomDAO labRoomDAO;
    @Autowired
    private SchoolTermDAO schoolTermDAO;

    /**
     * Description 实训室借用-实训室列表
     *
     * @param labRoom
     * @param currpage
     * @return
     * @author 陈乐为 2017-10-10
     */
    @RequestMapping("/listLabRoom")
    public ModelAndView listLabRoom(@ModelAttribute LabRoom labRoom, @RequestParam Integer currpage,
                                    @ModelAttribute("selected_academy") String acno) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // 查询表单的对象
        mav.addObject("labRoom", labRoom);
        int pageSize = 30;// 每页20条记录
        // 查询出来的总记录条数
        int totalRecords = labReservationService.findLabRoom(labRoom).size();
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoom> listLabRoom = labReservationService.findLabRoompage(labRoom, currpage, pageSize);

        mav.addObject("listLabRoom", listLabRoom);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("currpage", currpage);
        mav.addObject("pageSize", pageSize);

        mav.setViewName("lab/lab_lending/listLabRoom.jsp");
        return mav;
    }

    /**
     * Description 借用申请-弹窗
     *
     * @param labRoomId
     * @return
     * @author 陈乐为 2017-10-10
     */
    @RequestMapping("/showLabRoomLending")
    public ModelAndView showLabRoomLending(@RequestParam Integer labRoomId, @ModelAttribute("selected_academy") String acno) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        User user = shareService.getUserDetail();
        LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(labRoomId);
        mav.addObject("labRoom", labRoom);

        Map<String, String> params = new HashMap<>();
        String businessType = "LabRoomReservation" + labRoom.getLabCenter().getSchoolAcademy().getAcademyNumber();
        params.put("businessUid", labRoomId.toString());
        params.put("businessType", pConfig.PROJECT_NAME + businessType);
        String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessAuditConfigs", params);
        JSONObject jsonObject = JSONObject.parseObject(s);
        if("success".equals(jsonObject.getString("status"))){
            Map auditConfigs = JSON.parseObject(jsonObject.getString("data"), Map.class);
            if(auditConfigs != null && auditConfigs.size() > 0){
                for (int i = 1; i < auditConfigs.size(); i++) {
                    String o = (String) auditConfigs.get(i);
                    if(o.contains("CFO")){
                        mav.addObject("dean", 1);
                        //找到所有的系教学秘书、系主任
                        List<User> returnLsit = new ArrayList<User>();
                        //系主任
                        List<User> deanList = shareService.findUsersByAuthorityName("CFO");
                        //系教学秘书
                        List<User> departmentalSecretaryList = shareService.findUsersByAuthorityName("ASSISTANT");

                        returnLsit.addAll(deanList);
                        returnLsit.addAll(departmentalSecretaryList);

                        mav.addObject("deanList", returnLsit);
                    }
                    if(o.contains("TEACHER")){
                        mav.addObject("teacherAudit", "是");
                        mav.addObject("teacherList",
                                shareService.findTheSameCollegeTeacher(user.getSchoolAcademy().getAcademyNumber()));
                    }
                }
            }
        }
        //借用类型
        List<CDictionary> lendingTypes = shareService.getCDictionaryData("lab_room_lending_type");
        mav.addObject("lendingTypes", lendingTypes);
        //使用人员类型
        List<CDictionary> userTypes = shareService.getCDictionaryData("lab_room_lending_user_type");
        mav.addObject("userTypes", userTypes);
        //班级
        mav.addObject("schoolClassess", systemService.loadSchoolClassess());//返回所有班级
        // 学院
        List<SchoolAcademy> schoolAcademyList = shareService.findAllSchoolAcademys();
        mav.addObject("currAcademy", shareService.findSchoolAcademyByPrimaryKey(acno));
        mav.addObject("schoolAcademyList", schoolAcademyList);

        mav.setViewName("/lab/lab_lending/showLabRoomLending.jsp");
        return mav;
    }

    /**
     * Description 实训室借用-借用申请-保存
     *
     * @param request
     * @param labRoomId
     * @return
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @ResponseBody
    @RequestMapping("/saveLabRoomLending")
    public String saveLabRoomLending(HttpServletRequest request, @RequestParam Integer labRoomId, @ModelAttribute("selected_academy") String acno) throws ParseException, NoSuchAlgorithmException, InterruptedException {
        //当前时间
        Calendar date = Calendar.getInstance();
        String[] reservationTimes = request.getParameterValues("reservationTime[]");
        String successOrNotResult = "success";
        //获取日期及开始结束时间
        String lendingTimeS = request.getParameter("lendingTime");
        String reason = request.getParameter("reason");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date lendingTimeDate = sdf.parse(lendingTimeS);
        Calendar lendingTime = Calendar.getInstance();
        lendingTime.setTime(lendingTimeDate);
        String teacherName = request.getParameter("teacher");
        User teacher = userDAO.findUserByUsername(teacherName);
        User user = shareService.getUser();
        // 检查是否可预约
        LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(labRoomId);
        if (labRoom.getLabRoomReservation() == 0) {
            return "noReservation";
        }
        //检查安全准入
        String checkResult = labRoomService.securityAccess(user.getUsername(), labRoomId, request);
        if (!"success".equals(checkResult)) {
            return checkResult;
        }
        //检查申请人所在学院系主任
//        List<User> dUsers = shareService.findDeansByAcademyNumber(user.getSchoolAcademy());
//        if (dUsers == null || dUsers.size() == 0) {
//            return "noDean";
//        }
        //根据日期和id查找该实训室是否可借用
//        int lendingStatus = labRoomLendingService.findLendingEnableOrNot(labRoomId, lendingTime, start, end, acno);
//        if (lendingStatus == 2) {
//            return "reserved";
//        } else if (lendingStatus == 3) {
//            return "lent";
//        } else {//保存借用
        Integer auditNumber = 1;
        if(request.getParameter("labRid") != null){
            LabReservation labReservation = labReservationDAO.findLabReservationById(Integer.parseInt(request.getParameter("labRid")));
            for(LabReservationTimeTable lrtt: labReservation.getLabReservationTimeTables()){
                labReservationTimeTableDAO.remove(lrtt);
            }
            labReservation.setLabReservationTimeTables(null);
            labReservationDAO.store(labReservation);
            labReservationDAO.flush();
        }
        int id = labRoomLendingService.saveLabRoomLending(labRoomId, lendingTime, reservationTimes, reason, request, auditNumber);
        if (id == 0) {
            return "fail";
        }else if(request.getParameter("labRid") != null){
            return "editSuccess";
        }
        LabReservation labReservation = labReservationDAO.findLabReservationById(id);
        //消息
        Message message = new Message();
        message.setSendUser(user.getCname());
        message.setSendCparty(user.getSchoolAcademy().getAcademyName());
        message.setCond(0);
        message.setTitle("实验室预约审核");
        String content = "<a onclick='changeMessage(this)' href='../labRoomLending/checkButton?id=" + labReservation.getId() + "&tage=0&state=" + auditNumber + "&page=1'>审核</a>";
        message.setContent(content);
        message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
        message.setCreateTime(Calendar.getInstance());
        message.setTage(2);
        String businessType = "LabRoomReservation" + labRoom.getLabCenter().getSchoolAcademy().getAcademyNumber();

        //demo
        Map<String, String> params = new HashMap<>();
        params.put("businessUid", labRoom.getId().toString());
        params.put("businessType", pConfig.PROJECT_NAME + businessType);
        params.put("businessAppUid", labReservation.getId().toString());
        String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveInitBusinessAuditStatus", params);
        JSONObject jsonObject = JSON.parseObject(s);
        String status = jsonObject.getString("status");
        if (!"success".equals(status)) {
            return "fail";
        }
        Map<String, String> params2 = new HashMap<>();
        params2.put("businessType", pConfig.PROJECT_NAME + businessType);
        params2.put("businessAppUid", labReservation.getId().toString());
        String s2 = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", params2);
        JSONObject jsonObject2 = JSON.parseObject(s2);
        String status2 = jsonObject2.getString("status");
        JSONArray jsonArray = jsonObject2.getJSONArray("data");
        JSONObject jsonObject3 = jsonArray.getJSONObject(0);
        auditNumber = jsonObject3.getIntValue("level");
        String firstAuthName = jsonObject3.getString("result");
        if (auditNumber == 1 && !request.getSession().getAttribute("selected_role").equals("ROLE_STUDENT") && "TEACHER".equals(firstAuthName)) {
            Map<String, String> params3 = new HashMap<>();
            params3.put("businessType", pConfig.PROJECT_NAME + businessType);
            params3.put("businessAppUid", labReservation.getId().toString());
            params3.put("businessUid", labRoom.getId().toString());
            params3.put("result", "pass");
            params3.put("info", "不是学生不需要导师审核");
            params3.put("username", "username");
            String s3 = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveBusinessLevelAudit", params3);
            JSONObject jsonObject4 = JSON.parseObject(s3);
            String status3 = jsonObject4.getString("status");
            if (status3.equals("fail")) {
                return status3;
            }
        }
        Map<String, String> params4 = new HashMap<>();
        params4.put("businessType", pConfig.PROJECT_NAME + businessType);
        params4.put("businessAppUid", labReservation.getId().toString());
        String s4 = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", params4);
        JSONObject jsonObject5 = JSON.parseObject(s4);
        String status4 = jsonObject5.getString("status");
        JSONArray jsonArray4 = jsonObject5.getJSONArray("data");
        JSONObject jsonObject6 = jsonArray4.getJSONObject(0);
        auditNumber = jsonObject6.getIntValue("level");
        firstAuthName = jsonObject6.getString("result");
        // 审核通过推数据
        if(auditNumber == -1){
            Calendar calendar = Calendar.getInstance();
            // 把当前时间的时、分、秒、毫秒置成零，则为当前日期
            calendar.set(Calendar.MILLISECOND,0);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.HOUR_OF_DAY,0);
            // 如果当前日期和预约日期相同即同一天，则向物联发送刷新权限请求
            if(calendar.getTime().equals(labReservation.getLendingTime().getTime())) {
                labRoomService.sendRefreshInterfaceByJWT(labReservation.getLabRoom().getId());
            }
            TimetableAppointment timetableAppointment = new TimetableAppointment();
            timetableAppointment.setTimetableStyle(ConstantInterface.TIMETABLE_STYLE_LAB_RESERVATION);
            timetableAppointment.setCreatedDate(Calendar.getInstance());
            timetableAppointment.setUpdatedDate(Calendar.getInstance());
//            timetableAppointment.setCreatedBy(labReservation.getUser().getCname());
            timetableAppointment.setSchoolClasses(labReservation.getSchoolClasses());
            timetableAppointment.setCreatedBy(labReservation.getUser().getUsername());
            timetableAppointment.setStatus(ConstantInterface.TIMETABLE_STATUS_PUBLIC);
            timetableAppointment.setEnabled(true);
            timetableAppointment.setWeekday(labReservation.getLabReservationTimeTables().iterator().next().getSchoolWeekday().getId());
            timetableAppointment.setGroups(-1);
            // 预约人数类型变动，人数范围拆分
            String number = labReservation.getNumber();
            int stu_num = 0;
            if(number != null) {
                String[] stunum = number.split("-");
                if(stunum.length > 0) {
                    stu_num = Integer.valueOf(stunum[1]);
                }else {
                    stu_num = Integer.valueOf(number);
                }
            }
            timetableAppointment.setGroupCount(stu_num);
            timetableAppointment.setLabhours(-1);
            timetableAppointment.setConsumablesCosts(new BigDecimal(-1));
            timetableAppointment.setDeviceOrLab(2);
            timetableAppointment.setSchoolTerm(labReservation.getLabReservationTimeTables().iterator().next().getSchoolTerm());
            timetableAppointment = timetableAppointmentDAO.store(timetableAppointment);
            TimetableLabRelated tlr = new TimetableLabRelated();
            tlr.setLabRoom(labRoom);
            tlr.setTimetableAppointment(timetableAppointment);
            tlr = timetableLabRelatedDAO.store(tlr);
            Set<TimetableLabRelated> timetableLabRelateds = new HashSet<>();
            timetableLabRelateds.add(tlr);
            SimpleDateFormat compareSDF = new SimpleDateFormat("yyyyMMddHHmmss");
            Set<TimetableAppointmentSameNumber> timetableAppointmentSameNumbers = new HashSet<>();
            Integer week = 0;
            List<Integer> sections = new ArrayList<>();
            List<List<Integer>> results = new ArrayList<>();
            for(LabReservationTimeTable lrtt: labReservation.getLabReservationTimeTables()){
                week = Integer.parseInt(lrtt.getCDictionary().getCNumber());
                Integer section = labRoomLendingService.getSystemTimeByStartAndEnd(lrtt.getStartTime(), lrtt.getEndTime()).getSection();
                sections.add(section);
            }
            Collections.sort(sections);
            List<Integer> temp = new ArrayList<>();
            boolean flag = false;
            if(sections.size() == 1){
                temp.add(sections.get(0));
                results.add(temp);
            }else {
                for (int i = 0; i < sections.size() - 1; i++) {
                    if (flag) {
                        results.add(temp);
                        temp = new ArrayList<>();
                        flag = false;
                    }
                    temp.add(sections.get(i));
                    if(sections.get(i+1) - sections.get(i) != 1){
                        flag = true;
                    }
                }
                if (flag) {
                    results.add(temp);
                    temp = new ArrayList<>();
                    temp.add(sections.get(sections.size() - 1));
                    results.add(temp);
                }else{
                    temp.add(sections.get(sections.size() - 1));
                    results.add(temp);
                }
            }
            for(List<Integer> integerList: results){
                TimetableAppointmentSameNumber tasn = new TimetableAppointmentSameNumber();
                tasn.setStartWeek(week);
                tasn.setEndWeek(week);
                tasn.setStartClass(integerList.get(0));
                tasn.setEndClass(integerList.get(integerList.size() - 1));
                tasn.setTimetableAppointment(timetableAppointment);
                tasn = timetableAppointmentSameNumberDAO.store(tasn);
                timetableAppointmentSameNumberDAO.flush();
                timetableAppointmentSameNumbers.add(tasn);
            }
            Set<TimetableTeacherRelated> timetableTeacherRelateds = new HashSet<>();
            TimetableTeacherRelated timetableTeacherRelated = new TimetableTeacherRelated();
            timetableTeacherRelated.setTimetableAppointment(timetableAppointment);
            timetableTeacherRelated.setUser(labReservation.getUser());
            timetableTeacherRelated = timetableTeacherRelatedDAO.store(timetableTeacherRelated);
            timetableTeacherRelateds.add(timetableTeacherRelated);
            timetableAppointment.setTimetableTeacherRelateds(timetableTeacherRelateds);
            timetableAppointment.setTimetableAppointmentSameNumbers(timetableAppointmentSameNumbers);
            timetableAppointment.setTimetableLabRelateds(timetableLabRelateds);
            timetableAppointmentDAO.flush();
            labReservation.setTimetableAppointment(timetableAppointment);
            labReservationDAO.store(labReservation);
        }
        //第一级审核人
        switch (firstAuthName) {
            case "TEACHER":
                labReservation.setTeacher(teacher);

                shareService.sendMsg(teacher, message);
                break;
            case "CFO":
                List<User> deans = shareService.findDeansByAcademyNumber(user.getSchoolAcademy());
                for (User user2 : deans) {
                    shareService.sendMsg(user2, message);
                }
                break;
            case "LABMANAGER":
                List<LabRoomAdmin> labRoomAdmins = labRoomAdminService.findAllLabRoomAdminsByLabRoomId(labRoomId);
                for (LabRoomAdmin labRoomAdmin : labRoomAdmins) {
                    User user2 = labRoomAdmin.getUser();
                    shareService.sendMsg(user2, message);
                }
                break;
            case "EXCENTERDIRECTOR":
                shareService.sendMsg(labRoom.getLabCenter().getUserByCenterManager(), message);
                break;
            case "PREEXTEACHING":
                List<User> labRoomMasters = shareService.findUsersByAuthorityName("PREEXTEACHING");
                for (User user2 : labRoomMasters) {
                    shareService.sendMsg(user2, message);
                }
                break;
            case "pass":
                labReservation.setAuditStage(6);
                break;
            case "fail":
                labReservation.setAuditStage(0);
                break;
            default:
                List<User> auditUsers = shareService.findUsersByAuthorityName(firstAuthName);
                for (User user2: auditUsers){
                    shareService.sendMsg(user2, message);
                }
        }
        if("pass".equals(firstAuthName) || "fail".equals(firstAuthName)){
            message.setTitle("您的实验室预约不需要审核，欢迎使用！");
        }else{
            message.setTitle("实验室预约成功，请等待审核！");
        }
        content = "<a onclick='changeMessage(this)' href='../labRoomLending/checkButton?id=" + labReservation.getId() + "&tage=0&state=" + auditNumber + "&page=1'>查看</a>";
        message.setContent(content);
        message.setTage(1);
        shareService.sendMsg(user, message);
        successOrNotResult = "success";
//        }
        return successOrNotResult;
    }

    /**
     * Description 实训室借用-实训室对应借用列表
     *
     * @param currpage  当前页
     * @param labRoomId 实训室主键
     * @return
     * @author 陈乐为 2017-10-10
     */
    @RequestMapping("/listLabRoomLending")
    public ModelAndView listLabRoomLending(@RequestParam Integer currpage, Integer labRoomId,
                                           @ModelAttribute("selected_academy") String acno) {
        //新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        //分页参数
        int pageSize = 20;
        List<LabReservation> labRoomLendings = labRoomLendingService.findLendingsByLabId(labRoomId, currpage, pageSize);
        mav.addObject("labRoomLendings", labRoomLendings);
        mav.addObject("labRoomId", labRoomId);

        // 查询出来的总记录条数
        int totalRecords = labRoomLendingService.findLendingsByLabId(labRoomId, 1, -1).size();
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);

        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("currpage", currpage);
        mav.addObject("pageSize", pageSize);

        mav.setViewName("lab/lab_lending/listLabRoomLending.jsp");
        return mav;
    }

    /****************************************************************************
     * Description：查看实验室借用申请
     *
     * @author：张愉
     * @date:2017-10-27
     ****************************************************************************/
    @RequestMapping("/labRoomLendingInfoList")
    public ModelAndView labRoomLendingInfoList(int idKey, int flag, int step)
            throws Exception {
        ModelAndView mav = new ModelAndView();
        LabReservation labLend = labReservationDAO.findLabReservationById(idKey);
        // 查找审核记录
        List<LabReservationAudit> labReservationAudits = labRoomLendingService
                .findAllLabReservationAuditById(idKey);
        mav.addObject("labReservationAudits", labReservationAudits);
        // 查找实验室借用
        mav.addObject("labLend", labLend);
        mav.addObject("step", step);
        // flag1为查看，2为可审核
        mav.addObject("flag", flag);
        mav.addObject("id", idKey);
        mav.setViewName("lab/lab_lending/labRoomLendingInfoList.jsp");
        return mav;
    }

    /****************************************************************************
     * Description：审核实验室借用申请
     *
     * @author：张愉
     * @date:2017-10-28
     ****************************************************************************/
    @RequestMapping("/labReservationAudit")
    public String labReservationAudit(HttpServletRequest request, @RequestParam int Id, int result, int flag) {
        //审核完成后给申请人发送消息
        Message message = new Message();
        //当前时间
        Calendar date = Calendar.getInstance();
        //记录审核结果
        //SoftwareReserveAudit softwareReserveAudit=new SoftwareReserveAudit();
        message.setCond(0);
        String content = "";

        //当前实验室申请记录
        LabReservation labReservation = labReservationDAO.findLabReservationById(Id);
        if (flag == 1 && result == 1) {
            //审核中状态
            labReservation.setAuditResults(2);
            labReservation.setAuditStage(2);
            labReservationDAO.store(labReservation);
            labReservationDAO.flush();
            //保存审核记录
            List<LabReservationAudit> lrs = labReservationService.findAllLabReservationAudit(labReservation, shareService.getUser().getUsername(), "1");
            for (LabReservationAudit l : lrs) {
                l.setStatus(1);
                labReservationAuditDAO.store(l);
            }

            int labroomid = labReservation.getLabRoom().getId();
            List<LabRoomAdmin> labRoomAdmins = labRoomAdminService.findAllLabRoomAdminsByLabRoomId(labroomid);
            for (LabRoomAdmin l : labRoomAdmins) {
                //生成审核记录
                LabReservationAudit labReservationAudit = new LabReservationAudit();
                labReservationAudit.setLabReservation(labReservation);
                labReservationAudit.setStatus(3);
                labReservationAudit.setCreateDate(date);
                labReservationAudit.setUser(l.getUser());
                labReservationAuditDAO.store(labReservationAudit);
                content = "申请成功，等待二次审核";
                content += "<a onclick='changeMessage(this)' href='../labRoomLending/labRoomLendingInfoList?idKey=";
                content += Id;
                content += "&flag=2&step=2'>点击查看</a>";
                //发送消息
                messageService.saveMessages(l.getUser().getUsername(), "实验室借用申请", content, 1);

            }
            return "redirect:/personal/messageList?currpage=1";
        } else if (flag == 2 && result == 1) {
            //审核中状态
            labReservation.setAuditStage(3);
            labReservationDAO.store(labReservation);
            labReservationDAO.flush();
            //保存审核记录
            List<LabReservationAudit> lrs = labReservationService.findAllLabReservationAudit(labReservation, shareService.getUser().getUsername(), "2");
            for (LabReservationAudit l : lrs) {
                l.setStatus(1);
                labReservationAuditDAO.store(l);
            }
            //给实训中心主任发消息
            User user = new User();
            List<User> users = systemService.getUserByAuthority(user, 22);
            for (User u : users) {
                //生成审核记录
                LabReservationAudit labReservationAudit = new LabReservationAudit();
                labReservationAudit.setLabReservation(labReservation);
                labReservationAudit.setStatus(3);
                labReservationAudit.setCreateDate(date);
                labReservationAudit.setUser(u);
                labReservationAuditDAO.store(labReservationAudit);
                content = "实训部教学秘书审核";
                content += "<a onclick='changeMessage(this)' href='../labRoomLending/labRoomLendingInfoList?idKey=";
                content += Id;
                content += "&flag=2&step=3'>点击查看</a>";
                //发送消息
                messageService.saveMessages(u.getUsername(), "实验室借用申请", content, 1);

            }
            return "redirect:/personal/messageList?currpage=1";
        } else if (flag == 3 && result == 1) {
            //审核中状态
            labReservation.setAuditStage(4);
            labReservationDAO.store(labReservation);
            labReservationDAO.flush();
            //保存审核记录
            List<LabReservationAudit> lrs = labReservationService.findAllLabReservationAudit(labReservation, shareService.getUser().getUsername(), "3");
            for (LabReservationAudit l : lrs) {
                l.setStatus(1);
                labReservationAuditDAO.store(l);
            }
            //申请通过
            labReservation.setAuditResults(1);
            labReservationDAO.store(labReservation);
            content = "申请成功，审核通过";
            content += "<a onclick='changeMessage(this)' href='../labRoom/softwareReserveInfoList?idKey=";
            content += Id;
            content += "&flag=1&step=1'>点击查看</a>";
            //发送消息
            messageService.saveMessages(labReservation.getUser().getUsername(), "教学软件申请", content, 4);

            return "redirect:/personal/messageList?currpage=1";
        } else {//审核拒绝
            //保存审核记录
            List<LabReservationAudit> lrs = labReservationService.findAllLabReservationAudit(labReservation, shareService.getUser().getUsername(), "0");
            for (LabReservationAudit l : lrs) {
                l.setStatus(2);
                labReservationAuditDAO.store(l);
            }
            //申请拒绝
            labReservation.setAuditResults(4);
            labReservationDAO.store(labReservation);
            //发送消息
            content = "申请失败，审核拒绝";
            content += "<a onclick='changeMessage(this)' href='../labRoom/softwareReserveInfoList?idKey=";
            content += Id;
            content += "&flag=1&step=1'>点击查看</a>";
            //发送消息
            messageService.saveMessages(labReservation.getUser().getUsername(), "教学软件申请", content, 3);
            return "redirect:/personal/messageList?currpage=1";
        }
    }

    /**
     * Description 实验室借用申请-编辑
     *
     * @return
     * @author 陈乐为 2017-10-10
     */
    @RequestMapping("/editLabRoomLending")
    public ModelAndView editLabRoomLending(@RequestParam int id,Integer page, Integer isaudit, Integer tage,@ModelAttribute("selected_academy") String acno) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        //当前软件申请记录
        LabReservation labReservation = labReservationDAO.findLabReservationById(id);
        mav.addObject("labReservation", labReservation);
        mav.addObject("id", id);
        User user = shareService.getUserDetail();
        LabRoom labRoom = labReservation.getLabRoom();
        mav.addObject("labRoom", labRoom);
/*        //是否需要系主任审核
        Integer dean = labRoom.getDean();
        mav.addObject("dean", dean);
        if (dean == 1) {
            //找到所有的系教学秘书、系主任
            List<User> returnLsit = new ArrayList<User>();
            //系主任
            List<User> deanList = shareService.findUsersByAuthorityName("CFO");
            //系教学秘书
            List<User> departmentalSecretaryList = shareService.findUsersByAuthorityName("ASSISTANT");

            returnLsit.addAll(deanList);
            returnLsit.addAll(departmentalSecretaryList);

            mav.addObject("deanList", returnLsit);
        }*/
        //借用类型
        List<CDictionary> lendingTypes = shareService.getCDictionaryData("lab_room_lending_type");
        mav.addObject("lendingTypes", lendingTypes);
        //使用人员类型
        List<CDictionary> userTypes = shareService.getCDictionaryData("lab_room_lending_user_type");
        mav.addObject("userTypes", userTypes);
        //班级
        mav.addObject("schoolClassess", systemService.loadSchoolClassess());//返回所有班级
        // 学院
        List<SchoolAcademy> schoolAcademyList = shareService.findAllSchoolAcademys();
        mav.addObject("currAcademy", shareService.findSchoolAcademyByPrimaryKey(acno));
        mav.addObject("schoolAcademyList", schoolAcademyList);
        mav.addObject("page",page);
        mav.addObject("isaudit",isaudit);
        mav.addObject("tage",tage);
        mav.setViewName("/lab/lab_lending/editLabRoomLending.jsp");
        return mav;
    }

    /**
     * Description 实训室借用-提交
     *
     * @param request
     * @return
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping("/submitLabRoomLending")
    public String submitLabRoomLending(HttpServletRequest request, @RequestParam Integer Id) throws ParseException {
        //修改审核状态
        LabReservation la = labReservationDAO.findLabReservationById(Id);
        la.setAuditResults(2);//审核中
        la.setAuditStage(1);//系主任审核
        labReservationDAO.store(la);
        //当前时间
        Calendar date = Calendar.getInstance();
        //发送消息
        User user = la.getUser();//获取申请人
        List<User> users = systemService.getUserByAuthority(user, 17);
        for (User u : users) {
            //生成审核记录
			/*LabReservationAudit labReservationAudit=new LabReservationAudit();
			labReservationAudit.setLabReservation(labReservationDAO.findLabReservationById(Id));
			labReservationAudit.setResult("1");
			labReservationAudit.setStatus(3);
			labReservationAudit.setCreateDate(date);
			labReservationAudit.setUser(u);
			labReservationAuditDAO.store(labReservationAudit);
			labReservationAuditDAO.flush();*/

            messageService.saveMessages(u.getUsername(), "实验室借用申请", "请等待审核 <a href='../labRoomLending/labRoomLendingInfoList?idKey=" + Id + "&flag=2&step=1" + "'>点击查看</a>", 2);

        }
        //发一条消息给自己
        Message message = new Message();
        message.setUsername(shareService.getUser().getUsername());
        message.setSendUser(shareService.getUser().getUsername());//发给预约人
        message.setTitle("实验室借用申请");
        message.setContent("实验室借用申请成功");//消息内容
        message.setMessageState(0);//设置未读
        message.setCreateTime(date);
        message.setTage(0);
        messageDAO.store(message);
        messageDAO.flush();
        return "redirect:/labRoomLending/listLabRoomLending?labRoomId=" + labReservationDAO.findLabReservationById(Id).getLabRoom().getId() + "&currpage=1";
    }

    /**
     * Description 实验室借用申请-我的审核
     *
     * @return
     * @author 张愉2017-11-7
     */
    @RequestMapping("/myLabRoomLendingAudit")
    public ModelAndView myLabRoomLendingAudit(@RequestParam int flag) {
        ModelAndView mav = new ModelAndView();
        LabReservation labR = new LabReservation();
        List<LabReservationAudit> lras = new ArrayList<LabReservationAudit>();
        List<LabReservationAudit> lrs = labReservationService.findAllLabReservationAuditByResult(labR, shareService.getUser().getUsername(), flag);
        if (flag == 3) {
            for (LabReservationAudit l : lrs) {
                if (Integer.parseInt(l.getResult()) == l.getLabReservation().getAuditStage()) {
                    lras.add(l);
                }
            }
            mav.addObject("labReservationAudit", lras);
        } else {
            mav.addObject("labReservationAudit", lrs);
        }
        mav.addObject("flag", flag);
        mav.setViewName("/lab/lab_lending/myLabRoomLendingAudit.jsp");
        return mav;
    }

    /**
     * Description 实验室借用申请-审核进度查看
     *
     * @return
     * @author 张愉2017-11-7
     */
    @RequestMapping("/viewLabRoomLendingAudit")
    public ModelAndView viewLabRoomLendingAudit(@RequestParam int id, @RequestParam Integer currpage) {
        ModelAndView mav = new ModelAndView();
        LabReservation labReservation = labReservationDAO.findLabReservationById(id);
        mav.addObject("labReservation", labReservation);
        mav.setViewName("/lab/lab_lending/viewLabRoomLendingAudit.jsp");
        return mav;
    }

    /**
     * Description 实验室借用申请-审核进度查看
     *
     * @return
     * @author 张愉2017-11-7
     */
    @RequestMapping("/viewLabRoomLendingAuditUser")
    public ModelAndView viewLabRoomLendingAuditUser(@RequestParam int id, int currpage, int flag) {
        ModelAndView mav = new ModelAndView();
        LabReservation labReservation = labReservationDAO.findLabReservationById(id);
        int statue = labReservation.getAuditStage();
        Set<LabReservationAudit> labRAudits = labReservation.getLabReservationAudits();
        List<LabReservationAudit> deanAudit = new ArrayList<LabReservationAudit>();
        List<LabReservationAudit> labRoomAdminAudit = new ArrayList<LabReservationAudit>();
        List<LabReservationAudit> labRoomCenterDirectorAudit = new ArrayList<LabReservationAudit>();
        //各级审核人
        List<User> users = systemService.getUserByAuthority(labReservation.getUser(), 17);//系副主任
        int labroomid = labReservation.getLabRoom().getId();
        List<LabRoomAdmin> labRoomAdmins = labRoomAdminService.findAllLabRoomAdminsByLabRoomId(labroomid);
        //实训部教学秘书
        User user = new User();
        List<User> labusers = shareService.findUsersByAuthorityName("PREEXTEACHING");
        //主任审核
        if (statue == 1) {
            for (LabReservationAudit op : labRAudits) {
                if (op.getStatus() == 3 && "1".equals(op.getResult())) {
                    deanAudit.add(op);
                }
            }
        }
        //待实训室管理员审核
        if (statue == 2) {
            for (LabReservationAudit op : labRAudits) {
                if (op.getStatus() == 3 && "2".equals(op.getResult())) {
                    labRoomAdminAudit.add(op);
                }
                if (op.getStatus() == 1 && "1".equals(op.getResult())) {
                    deanAudit.add(op);
                }
            }
        }
        //待实训中心主任审核
        if (statue == 3) {
            for (LabReservationAudit op : labRAudits) {
                if (op.getStatus() == 1 && "1".equals(op.getResult())) {
                    deanAudit.add(op);
                }
                if (op.getStatus() == 1 && "2".equals(op.getResult())) {
                    labRoomAdminAudit.add(op);
                }
                if (op.getStatus() == 3 && "3".equals(op.getResult())) {
                    labRoomCenterDirectorAudit.add(op);
                }
            }
        }
        //全部已近审核
        if (statue == 4) {
            for (LabReservationAudit op : labRAudits) {
                if (op.getStatus() == 1 && "1".equals(op.getResult())) {
                    deanAudit.add(op);
                }
                if (op.getStatus() == 1 && "2".equals(op.getResult())) {
                    labRoomAdminAudit.add(op);
                }
                if (op.getStatus() == 1 && "3".equals(op.getResult())) {
                    labRoomCenterDirectorAudit.add(op);
                }
            }
        }

        mav.addObject("deanAudit", deanAudit);
        mav.addObject("labRoomAdminAudit", labRoomAdminAudit);
        mav.addObject("labRoomCenterDirectorAudit", labRoomCenterDirectorAudit);
        mav.addObject("statue", statue);
        if (flag == 1) {
            mav.setViewName("/lab/lab_lending/viewAuditUser.jsp");
        }
        if (flag == 2) {
            mav.setViewName("/lab/lab_lending/labRoomAdminAudit.jsp");
        }
        if (flag == 3) {
            mav.setViewName("/lab/lab_lending/labRoomCenterDirectorAudit.jsp");
        }
        return mav;
    }

    /**
     * Description 实验室借用申请-删除
     *
     * @return
     * @author 张愉2017-11-7
     */
    @RequestMapping("/deleteLabRoomLending")
    public String deleteLabRoomLending(@RequestParam int id) {
        ModelAndView mav = new ModelAndView();
        LabReservation labReservation = labReservationDAO.findLabReservationById(id);
        int labRoomid = labReservation.getLabRoom().getId();
        labReservationDAO.remove(labReservation);
        return "redirect:/labRoomLending/listLabRoomLending?labRoomId=" + labRoomid + "&currpage=1";
    }


    /****************************************************************************
     * Description：实验室借用申请列表
     *
     * @param:labRoomLending 申请列表
     *
     * @author:张愉
     * @date:2017-09-30
     ****************************************************************************/
    @RequestMapping("/labReservationList")
    public ModelAndView labReserveList(
            @ModelAttribute LabReservation labReservation, Integer page, Integer isaudit, Integer tage,
            @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("labReservation", labReservation);
        User user = shareService.getUserDetail();
        // 查询出所有的设备设备预约记录
        int pageSize = 15;// 每页15条记录
        int totalRecords = 0;
        // 根据分页信息查询出来的记录
        List<LabReservation> labReservations = labReservationService.findAlllabReservation(labReservation, page, pageSize, tage, isaudit);
        totalRecords = labReservationService.findAlllabReservation(labReservation, 0, 0, tage, isaudit).size();
        List<Integer> auditState = new ArrayList<>();
        List<String> auditShow = new ArrayList<>();
        //判断所处审核阶段，关联到前端的按钮
        if (labReservations != null) {
            for (int i = 0; i < labReservations.size(); i++) {
                // 获取当前审核状态
                Map<String, String> params2 = new HashMap<>();
                String businessType = "LabRoomReservation" + labReservations.get(i).getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber();
                params2.put("businessType", pConfig.PROJECT_NAME + businessType);
                params2.put("businessAppUid", labReservations.get(i).getId().toString());
                String s2 = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", params2);
                JSONObject jsonObject2 = JSON.parseObject(s2);
                String status2 = jsonObject2.getString("status");
                Integer auditNumber = null;
                if("success".equals(status2)){
                    JSONArray jsonArray = jsonObject2.getJSONArray("data");
                    if(jsonArray != null) {
                        JSONObject jsonObject3 = jsonArray.getJSONObject(0);
                        auditNumber = labRoomService.getAuditNumber(labReservations.get(i).getLabRoom(), jsonObject3.getIntValue("level"));
                        auditState.add(jsonObject3.getIntValue("level"));
                    }
                }else{
                    auditState.add(-2);
                }
                // 获取所有审核状态
                Map<String, String> allAuditStateParams = new HashMap<>();
                allAuditStateParams.put("businessType", pConfig.PROJECT_NAME + businessType);
                allAuditStateParams.put("businessAppUid", labReservations.get(i).getId().toString());
                allAuditStateParams.put("businessUid", labReservations.get(i).getLabRoom().getId().toString());
                String allAuditStateStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessLevelStatus", allAuditStateParams);
                JSONObject allAuditStateJSON = JSONObject.parseObject(allAuditStateStr);
                String htmlStr = "";
                if(!"fail".equals(status2)) {
                    JSONArray allAuditStateJSONArray = allAuditStateJSON.getJSONArray("data");
                    if (allAuditStateJSONArray != null && allAuditStateJSONArray.size() != 0) {
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
                    }
                }
                auditShow.add(htmlStr);
                mav.addObject("auditShow", auditShow);

                //先初始化为0
                labReservations.get(i).setButtonMark(0);
                if (auditNumber != null) {
                    //教师审核阶段
                    if (auditNumber == 1) {
                        //当前登陆人是审核实训部主任
                        User teacher = labReservations.get(i).getTeacher();
                        if (teacher != null) {
                                if (user.getUsername().equals(teacher.getUsername())) {
                                    labReservations.get(i).setButtonMark(1);
                            }
                        }
                    }
                    //系主任审核阶段
                    if (auditNumber == 2) {
                        //当前登陆人是审核系主任
                        List<User> deans = shareService.findDeansByAcademyNumber(user.getSchoolAcademy());
                        if (deans != null) {
                            for (User user2 : deans) {
                                if (user2.getUsername().equals(user.getUsername())) {
                                    labReservations.get(i).setButtonMark(2);
                                    break;
                                }
                            }
                        }
                    }
                    //实训室管理员审核阶段
                    if (auditNumber == 3) {
                        //当前登陆人是审核实训室管理员
                        if (labRoomDeviceService.getLabRoomAdmin(labReservations.get(i).getLabRoom().getId(), user.getUsername()) == true) {
                            labReservations.get(i).setButtonMark(3);
                        }
                    }
                    //实验中心主任审核阶段
                    if (auditNumber == 4) {
                        User labRoomCenterDirector = labReservations.get(i).getLabRoom().getLabCenter().getUserByCenterManager();
                        if (labRoomCenterDirector != null) {
                                if (user.getUsername().equals(labRoomCenterDirector.getUsername())) {
                                    labReservations.get(i).setButtonMark(4);
                            }
                        }
                    }
                    //实训部教学秘书审核阶段
                    if (auditNumber == 5) {
                        //当前登陆人是审核实训部主任
                        List<User> labRoomCenterDirectors = shareService.findUsersByAuthorityName("PREEXTEACHING");
                        if (labRoomCenterDirectors != null) {
                            for (User user2 : labRoomCenterDirectors) {
                                if (user.getUsername().equals(user2.getUsername())) {
                                    labReservations.get(i).setButtonMark(5);
                                    break;
                                }
                            }
                        }
                    }
                }else{
                    labReservations.get(i).setButtonMark(0);
                }
            }
//            totalRecords = labReservations.size();
        }
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize,
                totalRecords);
        mav.addObject("labReservations", labReservations);

        mav.addObject("auditState", auditState);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.addObject("isAudit", isaudit);
        mav.addObject("tage", tage);
        mav.addObject("jobReservation", pConfig.jobReservation);
        mav.addObject("user", user);
        mav.addObject("proj_name", pConfig.PROJECT_NAME);

        mav.setViewName("/lab/lab_lending/labReservationList.jsp");
        return mav;
    }
    /****************************************************************************
     * Description：删除实验室预约
     *
     * @author:廖文辉
     * @date:2018-12-18
     ****************************************************************************/
    @RequestMapping("/deletelabReservation")
    @Transactional
    public ModelAndView deletelabReservation(
            @RequestParam int id, Integer page, Integer isaudit, Integer tage,
            @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav=new ModelAndView();
        LabReservation labReservation =labReservationDAO.findLabReservationByPrimaryKey(id);
        if(labReservation!=null) {
            List<LabReservationTimeTable> labReservationTimeTables = labReservationService.findLabReservationTimetableByLabReservation(labReservation);
            for (LabReservationTimeTable labReservationTimeTable : labReservationTimeTables) {
                labReservationTimeTableDAO.remove(labReservationTimeTable);
                labReservationTimeTableDAO.flush();
            }
            if (labReservation.getTimetableAppointment() != null) {
                List<TimetableAppointmentSameNumber> timetableAppointmentSameNumbers = labReservationService.getTimetableAppointmentSameNumber(labReservation);
                for(TimetableAppointmentSameNumber timetableAppointmentSameNumber:timetableAppointmentSameNumbers){
                    timetableAppointmentSameNumberDAO.remove(timetableAppointmentSameNumber);
                    timetableAppointmentSameNumberDAO.flush();
                }
                int app_id = labReservation.getTimetableAppointment().getId();
                labReservation.setTimetableAppointment(null);
                labReservationDAO.store(labReservation);
                labReservationDAO.flush();
                TimetableAppointment timetableAppointment = timetableAppointmentDAO.findTimetableAppointmentByPrimaryKey(app_id);
                timetableAppointmentDAO.remove(timetableAppointment);
                timetableAppointmentDAO.flush();

            }
            labReservationDAO.remove(labReservation);
        }
        mav.setViewName("redirect:/labRoomLending/labReservationList?page=" + page + "&isaudit="+isaudit+"&tage="+tage);
        return mav;
    }
    /****************************************************************************
     * Description：实验室借用申请-申请审核/查看
     *
     * @author:张愉
     * @date:2017-11-24
     ****************************************************************************/
    @RequestMapping("/checkButton")
    public ModelAndView checkButtonforlabRoomLend(@RequestParam int id, int tage, int state, Integer page, HttpServletRequest request,
                                                  @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        LabReservation labReservation = labReservationDAO.findLabReservationById(id);
        // 如果查不到预约，则说明该预约已撤回或者作废
        if(labReservation == null){
            mav.addObject("noLabReservation", 1);
            mav.setViewName("/lab/lab_lending/viewLabRoomLendingAudit.jsp");
            return mav;
        }
        LabRoom labRoom = labReservation.getLabRoom();
//        state = labRoomService.getAuditNumber(labRoom, state);
        mav.addObject("state", state);
        mav.addObject("labReservation", labReservation);
        mav.addObject("labRoom", labRoom);
        mav.addObject("tage", tage);
        mav.addObject("page", page);
        //demo
        String[] RSWITCH = {"on", "off"};
        List<String> titles = new ArrayList<>();
        List<Integer> isOpens = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        String businessType = "LabRoomReservation" + labRoom.getLabCenter().getSchoolAcademy().getAcademyNumber();
        params.put("businessUid", labRoom.getId().toString());
        params.put("businessType", pConfig.PROJECT_NAME + businessType);
        String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessAuditConfigs", params);
        JSONObject jsonObject = JSON.parseObject(s);
        String status = jsonObject.getString("status");
        if("success".equals(status)) {
            Map auditConfigs = JSON.parseObject(jsonObject.getString("data"), Map.class);
            if (auditConfigs != null && auditConfigs.size() != 0) {
                for (int i = 0; i < auditConfigs.size(); i++) {
                    String[] auditSwitch = ((String) auditConfigs.get(i+1)).split(":");
                    titles.add(authorityDAO.findAuthorityByAuthorityName(auditSwitch[0]).iterator().next().getCname() + "审核");
                    isOpens.add(
                            (auditSwitch[1].equals(RSWITCH[0])
                            && (!auditSwitch[0].equals("TEACHER") || labReservation.getTeacher() != null))
                            ? 1 : 2);
                }
            }
        }
        mav.addObject("titles", titles);
        mav.addObject("isOpens", isOpens);
        mav.setViewName("/lab/lab_lending/viewLabRoomLendingAudit.jsp");
        return mav;
    }

    /****************************************************************************
     * Description：实验室借用申请-系副主任审核
     *
     * @author:张愉
     * @date:2017-11-24
     ****************************************************************************/
    @RequestMapping("/deanAudit")
    public ModelAndView deanAuditforlabRoomlend(@RequestParam int id, int tage, int state, Integer page, HttpServletRequest request,
                                                @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        LabReservation labReservation = labReservationDAO.findLabReservationById(id);
        User user = shareService.getUserDetail();
        List<User> deans = shareService.findDeansByAcademyNumber(user.getSchoolAcademy());
        JSONObject j = getAllState(labReservation, "CFO", acno);
        //是否为审核人
        int isAudit = 0;
        if (state == 2) {//在此审核人审核阶段
            if (deans != null) {
                for (User user2 : deans) {
                    if (user2.getUsername().equals(user.getUsername()) && "CFO".equals(request.getSession().getAttribute("selected_role"))) {
                        isAudit = 1;
                        break;
                    }
                }
                if(isAudit == 0){
                    mav.addObject("deans", deans);
                }
            }
        } else {//非此审核人审核阶段
            if (state > 2 || state < 1) {//此审核人已审核完成阶段
                User auditUser = userDAO.findUserByUsername(j.getString("auditUser"));
                mav.addObject("user", auditUser);
                mav.addObject("result", j.getString("result"));
                mav.addObject("mem", j.getString("info"));
            }else{
                mav.addObject("deans", deans);
            }
        }
        mav.addObject("deans", deans);
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("page", page);
        mav.setViewName("/lab/lab_lending/labRoomLendDeanAudit.jsp");
        return mav;
    }

    /****************************************************************************
     * Description：实验室借用申请-实验室管理员审核
     *
     * @author:张愉
     * @date:2017-11-24
     ****************************************************************************/
    @RequestMapping("/labRoomAdminAudit")
    public ModelAndView labRoomAdminAuditForLabRoomlend(@RequestParam int id, int tage, int state, Integer page, HttpServletRequest request,
                                                        @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        LabReservation labReservation = labReservationDAO.findLabReservationById(id);
        LabRoom labRoom = labReservation.getLabRoom();
        User user = shareService.getUserDetail();
        //实验室管理员
        List<User> labRoomAdmins = labRoomDeviceService.findAdminByLrid(labRoom.getId());
        //当前登陆人是否为实验室管理员
        Boolean islabRoomAdmin = labRoomDeviceService.getLabRoomAdmin(labRoom.getId(), user.getUsername());
        JSONObject j = getAllState(labReservation, "LABMANAGER", acno);
        //是否为审核人
        int isAudit = 0;
        if (state == 3) {//在此审核人审核阶段
            if (islabRoomAdmin == true && "LABMANAGER".equals(request.getSession().getAttribute("selected_role"))) {//是审核人
                isAudit = 1;
            } else {
                mav.addObject("labRoomAdmins", labRoomAdmins);
            }
        } else {//非此审核人审核阶段
            if (state < 3  && state >= 1) {//未到此审核人审核阶段
                mav.addObject("labRoomAdmins", labRoomAdmins);
            } else {//此审核人已审核完成阶段
                User auditUser = userDAO.findUserByUsername(j.getString("auditUser"));
                mav.addObject("user", auditUser);
                mav.addObject("result", j.getString("result"));
                mav.addObject("mem", j.getString("info"));
            }
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("page", page);
        mav.setViewName("/lab/lab_lending/LendlabRoomAdminAudit.jsp");
        return mav;
    }

    /****************************************************************************
     * Description：实验室借用申请-实训部教学秘书审核
     *
     * @author:张愉
     * @date:2018-1-4
     ****************************************************************************/
    @RequestMapping("/presecTeacherAuditUser")
    public ModelAndView presecTeacherAuditUserForLabRoomlend(@RequestParam int id, int tage, int state, Integer page, HttpServletRequest request,
                                                             @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        LabReservation labReservation = labReservationDAO.findLabReservationById(id);
        LabRoom labRoom = labReservation.getLabRoom();
        User user = shareService.getUserDetail();
        //实训部教学秘书
        List<User> labRoomCenterDirectors = shareService.findUsersByAuthorityName("PREEXTEACHING");
        JSONObject j = getAllState(labReservation, "PREEXTEACHING", acno);
        //是否为审核人
        int isAudit = 0;
        if (state == 5) {//在此审核人审核阶段
            if (labRoomCenterDirectors != null) {
                for (User user2 : labRoomCenterDirectors) {
                    if (user2.getUsername().equals(user.getUsername()) && "PREEXTEACHING".equals(request.getSession().getAttribute("selected_role"))) {
                        isAudit = 1;
                        break;
                    }
                }
                if(isAudit == 0){
                    mav.addObject("labRoomCenterDirectors", labRoomCenterDirectors);
                }
            }
        } else {//非此审核人审核阶段
            if (state < 5 && state >= 1) {//未到此审核人审核阶段
                mav.addObject("labRoomCenterDirectors", labRoomCenterDirectors);
            } else {//此审核人已审核完成阶段
                User auditUser = userDAO.findUserByUsername(j.getString("auditUser"));
                mav.addObject("user", auditUser);
                mav.addObject("result", j.getString("result"));
                mav.addObject("mem", j.getString("info"));
            }
        }
        mav.addObject("labRoomCenterDirectors", labRoomCenterDirectors);
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("page", page);
        mav.setViewName("/lab/lab_lending/presecTeacherAuditUser.jsp");
        return mav;
    }

    /****************************************************************************
     * Description：实验室借用申请-保存审核
     *
     * @author:张愉
     * @date:2017-11-24
     ****************************************************************************/
    @Transactional
    @RequestMapping("/saveDeanAudit")
    public String saveDeanAuditforlabRoomlend(@RequestParam int id, int tage, int state, Integer page, String auditResult, String remark,
                                              @ModelAttribute("selected_academy") String acno) throws NoSuchAlgorithmException {
        LabReservation labReservation = labReservationDAO.findLabReservationById(id);
        LabReservationAudit labReservationAudit = new LabReservationAudit();
        labReservationAudit.setResult(auditResult);
        //保存审核意见
        if (remark != null) {
        }
        labReservation = labRoomLendingService.saveAuditResult(labReservation, auditResult, remark, acno);
        return "redirect:/labRoomLending/deanAudit?id=" + id + "&tage=" + tage + "&state=" + state+1 + "&page=" + page;
    }

    /****************************************************************************
     * Description：实验室借用申请-保存实验室管理员审核
     *
     * @author:孙虎
     * @date:2018-04-12
     ****************************************************************************/
    @Transactional
    @RequestMapping("/saveLabRoomAdminAuditforlabRoomlend")
    public String saveLabRoomAdminAuditforlabRoomlend(@RequestParam int id, int tage, int state, Integer page, String auditResult, String remark,
                                                      @ModelAttribute("selected_academy") String acno) throws NoSuchAlgorithmException {
        LabReservation labReservation = labReservationDAO.findLabReservationById(id);
        LabReservationAudit labReservationAudit = new LabReservationAudit();
        labReservationAudit.setResult(auditResult);
        labReservation = labRoomLendingService.saveAuditResult(labReservation, auditResult, remark, acno);
        return "redirect:/labRoomLending/labRoomAdminAudit?id=" + id + "&tage=" + tage + "&state=" + state+1 + "&page=" + page;
    }

    /****************************************************************************
     * Description：实验室借用申请-保存实训部教学秘书审核审核
     *
     * @author:孙虎
     * @date:2018-04-12
     ****************************************************************************/
    @Transactional
    @RequestMapping("/savePresecTeacherAuditforlabRoomlend")
    public String savePresecTeacherAuditforlabRoomlend(@RequestParam int id, int tage, int state, Integer page, String auditResult, String remark,
                                                       @ModelAttribute("selected_academy") String acno) throws NoSuchAlgorithmException {
        LabReservation labReservation = labReservationDAO.findLabReservationById(id);
        LabReservationAudit labReservationAudit = new LabReservationAudit();
        labReservationAudit.setResult(auditResult);
        labReservation = labRoomLendingService.saveAuditResult(labReservation, auditResult, remark, acno);
        return "redirect:/labRoomLending/presecTeacherAuditUser?id=" + id + "&tage=" + tage + "&state=" + state+1 + "&page=" + page;
    }

    /****************************************************************************
     * Description：实验室借用申请-实验室管理员审核
     *
     * @author:张愉
     * @date:2017-11-24
     ****************************************************************************/
    @RequestMapping("/teacherAudit")
    public ModelAndView teacherForLabRoomlend(@RequestParam int id, int tage, int state, Integer page, HttpServletRequest request,
                                              @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        LabReservation labReservation = labReservationDAO.findLabReservationById(id);
        LabRoom labRoom = labReservation.getLabRoom();
        User user = shareService.getUserDetail();
        User teacher = labReservation.getTeacher();
        JSONObject j = getAllState(labReservation, "TEACHER", acno);
        //是否为审核人
        int isAudit = 0;
        if (state == 1) {//在此审核人审核阶段
            if (user.getUsername().equals(teacher.getUsername()) && "ROLE_TEACHER".equals(request.getSession().getAttribute("selected_role"))) {//是审核人
                isAudit = 1;
            } else {
                mav.addObject("teacher", teacher);
            }
        } else {//非此审核人审核阶段
            User auditUser = userDAO.findUserByUsername(j.getString("auditUser"));
            mav.addObject("user", auditUser);
            mav.addObject("result", j.getString("result"));
            mav.addObject("mem", j.getString("info"));
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("page", page);
        mav.setViewName("/lab/lab_lending/LendTeacherAudit.jsp");
        return mav;
    }

	/****************************************************************************
	 * Description：实验室借用申请-实验室管理员审核
	 *
	 * @author:张愉
	 * @date:2017-11-24
	 ****************************************************************************/
	@RequestMapping("/labCenterManagerAudit")
	public ModelAndView labCenterManagerAuditForLabRoomlend(@RequestParam int id, int tage,int state,Integer page, HttpServletRequest request,
                                                            @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		LabReservation labReservation =labReservationDAO.findLabReservationById(id);
		LabRoom labRoom = labReservation.getLabRoom();
		User user = shareService.getUserDetail();
		User labCenterManager = labRoom.getLabCenter().getUserByCenterManager();
        JSONObject j = getAllState(labReservation, "EXCENTERDIRECTOR", acno);
        //是否为审核人
		int isAudit = 0;
		if(state == 4){//在此审核人审核阶段
			if(user.getUsername().equals(labCenterManager.getUsername()) && "EXCENTERDIRECTOR".equals(request.getSession().getAttribute("selected_role"))){//是审核人
				isAudit = 1;
			}else{
				mav.addObject("labCenterManager", labCenterManager);
			}
		}else{//非此审核人审核阶段
			if(state < 4 && state >= 1){//未到此审核人审核阶段
				mav.addObject("labCenterManager", labCenterManager);
			}else {//此审核人已审核完成阶段
                User auditUser = userDAO.findUserByUsername(j.getString("auditUser"));
                mav.addObject("user", auditUser);
                mav.addObject("result", j.getString("result"));
                mav.addObject("mem", j.getString("info"));
			}
		}
		mav.addObject("isAudit", isAudit);
		mav.addObject("state", state);
		mav.addObject("tage", tage);
		mav.addObject("id", id);
		mav.addObject("page", page);
		mav.setViewName("/lab/lab_lending/LendlabCenterManagerAudit.jsp");
		return mav;
	}

    @Transactional
    @RequestMapping("/saveTeacherAuditforlabRoomlend")
    public String saveTeacherAuditforlabRoomlend(@RequestParam int id, int tage, int state, Integer page, String auditResult, String remark,
                                                 @ModelAttribute("selected_academy") String acno) throws NoSuchAlgorithmException {
        LabReservation labReservation = labReservationDAO.findLabReservationById(id);
        LabReservationAudit labReservationAudit = new LabReservationAudit();
        labReservationAudit.setResult(auditResult);
        labReservation = labRoomLendingService.saveAuditResult(labReservation, auditResult, remark, acno);
        return "redirect:/labRoomLending/teacherAudit?id=" + id + "&tage=" + tage + "&state=" + state+1 + "&page=" + page;
    }

    @Transactional
    @RequestMapping("/saveLabCenterManagerAuditforlabRoomlend")
    @ResponseBody
    public String saveLabCenterManagerAuditforlabRoomlend(@RequestParam int id, int tage, int state, Integer page, String auditResult, String remark,
                                                          @ModelAttribute("selected_academy") String acno) throws NoSuchAlgorithmException {
        LabReservation labReservation = labReservationDAO.findLabReservationById(id);
        LabReservationAudit labReservationAudit = new LabReservationAudit();
        labReservationAudit.setResult(auditResult);
        labReservation = labRoomLendingService.saveAuditResult(labReservation, auditResult, remark, acno);
        return "success";
    }

    private JSONObject getAllState(LabReservation labReservation, String authName, String acno){
        Map<String, String> params = new HashMap<>();
        String businessType = "LabRoomReservation" + labReservation.getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber();
        params.put("businessType", pConfig.PROJECT_NAME + businessType);
        params.put("businessAppUid", labReservation.getId().toString());
        params.put("businessUid", labReservation.getLabRoom().getId().toString());
        String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessLevelStatus", params);
        JSONObject jsonObject = JSON.parseObject(s);
        String status = jsonObject.getString("status");
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        if(!status.equals("fail")){
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject o = jsonArray.getJSONObject(i);
                if(o.getString("authName").equals(authName)){
                    return o;
                }
            }
        }
        return new JSONObject();
    }

    /****************************************************************************
     * Description：实验室借用申请-实验室管理员审核
     *
     * @author:张愉
     * @date:2017-11-24
     ****************************************************************************/
    @RequestMapping("/labReservationAllAudit")
    public ModelAndView labReservationAllAudit(@RequestParam int id, int tage,int state,Integer page, HttpServletRequest request,
                                               @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        LabReservation labReservation =labReservationDAO.findLabReservationById(id);
        User user = shareService.getUserDetail();
        List<User> isAuditUsers = new ArrayList<>();
        JSONObject resultObject = new JSONObject();
        Map<String, String> allParams = new HashMap<>();
        String businessType = "LabRoomReservation" + labReservation.getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber();
        allParams.put("businessType", pConfig.PROJECT_NAME + businessType);
        allParams.put("businessAppUid", labReservation.getId().toString());
        allParams.put("businessUid", labReservation.getLabRoom().getId().toString());
        String allString = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessLevelStatus", allParams);
        JSONObject allObject = JSON.parseObject(allString);
        String allStatus = allObject.getString("status");
        JSONArray allJSONArray = allObject.getJSONArray("data");
        if (!allStatus.equals("fail")) {
            for (int i = 0; i < allJSONArray.size(); i++) {
                JSONObject o = allJSONArray.getJSONObject(i);
                if(o.getIntValue("level") == state){
                    resultObject = o;
                    break;
                }
            }
        }
        Map<String, String> curParams = new HashMap<>();
        curParams.put("businessType", pConfig.PROJECT_NAME + businessType);
        curParams.put("businessAppUid", labReservation.getId().toString());
        String curString = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", curParams);
        JSONObject curObject = JSONObject.parseObject(curString);
        String curPassStatus = curObject.getString("status");
        JSONArray curJSONArray = curObject.getJSONArray("data");
        JSONObject curJSONObject = curJSONArray.getJSONObject(0);
        Integer curStage = curJSONObject.getIntValue("level");
        String authName = curStage >= 1 ? curJSONObject.getString("result") : "";
        String allAuthName = state >= 1 ? resultObject.getString("authName") : "";
        switch (allAuthName){
            case "TEACHER":
                isAuditUsers.add(labReservation.getTeacher());
                break;
            case "CFO":
                isAuditUsers.addAll(shareService.findUsersByQuery("CFO",labReservation.getUser().getSchoolAcademy().getAcademyNumber()));
                break;
            case "LABMANAGER":
                isAuditUsers.addAll(labRoomDeviceService.findAdminByLrid(labReservation.getLabRoom().getId()));
                break;
            case "EXCENTERDIRECTOR":
                isAuditUsers.add(labReservation.getLabRoom().getLabCenter().getUserByCenterManager());
                break;
            default:
                isAuditUsers.addAll(shareService.findUsersByAuthorityName(allAuthName));
        }
        //是否为审核人
        int isAudit = 0;
        if(state == curStage){//在此审核人审核阶段
            if (("ROLE_" + authName).equals(request.getSession().getAttribute("selected_role"))
                    && ((!"TEACHER".equals(authName) || labReservation.getTeacher().getUsername().equals(user.getUsername()))
                    && (!"LABMANAGER".equals(authName) || labRoomDeviceService.getLabRoomAdmin(labReservation.getLabRoom().getId(), user.getUsername()))
                    && (!"CFO".equals(authName) || user.getSchoolAcademy().getAcademyNumber().equals(labReservation.getUser().getSchoolAcademy().getAcademyNumber()))
                    && (!"EXCENTERDIRECTOR".equals(authName) || labReservation.getLabRoom().getLabCenter().getUserByCenterManager().getUsername().equals(user.getUsername()))
            )) {//是审核人
                isAudit = 1;
            }else{
                mav.addObject("isAuditUser", isAuditUsers);
            }
        }else{//非此审核人审核阶段
            if(state > curStage && curStage >= 1){//未到此审核人审核阶段
                mav.addObject("isAuditUser", isAuditUsers);
            }else {//此审核人已审核完成阶段
                User auditUser = userDAO.findUserByUsername(resultObject.getString("auditUser"));
                mav.addObject("user", auditUser);
                mav.addObject("result", resultObject.getString("result"));
                mav.addObject("mem", resultObject.getString("info"));
                String createTimeStr = resultObject.getString("createTime");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                Calendar createTime = Calendar.getInstance();
                try {
                    createTime.setTime(sdf.parse(createTimeStr));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mav.addObject("createDate", createTime);
            }
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("curStage", curStage);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("page", page);
        mav.setViewName("/lab/lab_lending/LendlabAllAudit.jsp");
        return mav;
    }

    /**
     * Description 获取实验室可预约时间段
     * @param request
     * @param acno
     * @return
     * @throws ParseException
     */
    @RequestMapping("/checkConflict")
    public @ResponseBody
    String checkConflict(HttpServletRequest request,
                         @ModelAttribute("selected_academy") String acno) throws ParseException {
        Integer labRoomId = request.getParameter("labRoom") == null ? -1 : Integer.parseInt(request.getParameter("labRoom"));
        String lendingTimeS = request.getParameter("lendingTime");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date lendingTimeDate = sdf.parse(lendingTimeS);
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
        Calendar lendingTime = Calendar.getInstance();
        lendingTime.setTime(lendingTimeDate);
        StringBuilder r = new StringBuilder();

        List<SystemTime> systemTimes = new ArrayList<>(systemTimeDAO.findAllSystemTimes());
        Date nowDate = sdf.parse(sdf.format(new Date()));
        // 判断是否是当天
        if(lendingTimeDate.getTime()==nowDate.getTime()) {
            systemTimes = shareService.getNotObsoleteTime();
            // 遍历范围所有节次起止时间
            for(SystemTime systemTime: systemTimes){
                Calendar start = systemTime.getStartDate();
                Calendar end = systemTime.getEndDate();
                int result = labRoomLendingService.findLendingEnableOrNot(labRoomId, lendingTime, start, end, acno);
                if(result == 1){
                    r.append("<option value='")
                            .append(sdf1.format(start.getTime()))
                            .append("-")
                            .append(sdf1.format(end.getTime()))
                            .append("'>")
                            .append(sdf1.format(start.getTime()))
                            .append("-")
                            .append(sdf1.format(end.getTime()))
                            .append("</option>");
                }
            }
        }else {
            // 遍历范围所有节次起止时间
            for(SystemTime systemTime: systemTimes){
                Calendar start = systemTime.getStartDate();
                Calendar end = systemTime.getEndDate();
                int result = labRoomLendingService.findLendingEnableOrNot(labRoomId, lendingTime, start, end, acno);
                if(result == 1){
                    r.append("<option value='")
                            .append(sdf1.format(start.getTime()))
                            .append("-")
                            .append(sdf1.format(end.getTime()))
                            .append("'>")
                            .append(sdf1.format(start.getTime()))
                            .append("-")
                            .append(sdf1.format(end.getTime()))
                            .append("</option>");
                }
            }
        }

        if(request.getParameter("labRid")!=null) {
            LabReservation labReservation = labReservationDAO.findLabReservationById(Integer.parseInt(request.getParameter("labRid")));
            if(labReservation.getLendingTime().equals(lendingTime)){
                for(LabReservationTimeTable lrtt: labReservation.getLabReservationTimeTables()){
                    r.append("<option value='")
                            .append(sdf1.format(lrtt.getStartTime().getTime()))
                            .append("-")
                            .append(sdf1.format(lrtt.getEndTime().getTime()))
                            .append("' selected>")
                            .append(sdf1.format(lrtt.getStartTime().getTime()))
                            .append("-")
                            .append(sdf1.format(lrtt.getEndTime().getTime()))
                            .append("</option>");
                }
            }
        }
        return r.toString();
    }

    /**
     * 实验室预约作废
     * @param labReservationId 实验室预约id
     * @return 成功的字符串
     * @author 黄保钱 2019-1-11
     */
    @RequestMapping("/obsoleteLabReservation")
    public @ResponseBody String obsoleteLabReservation(@RequestParam Integer labReservationId){
        return labRoomLendingService.obsoleteLabReservation(labReservationId);
    }

    /**
     * 实验室预约作废列表
     * @param page 页数
     * @return 页面
     * @author 黄保钱 2019-1-11
     */
    @RequestMapping("/labReservationObsoleteList")
    public ModelAndView labReservationObsoleteList(@ModelAttribute LabReservation labReservation, Integer page){
        ModelAndView mav = new ModelAndView();
        mav.addObject("page", page);
        int pageSize = 10;
        List<AuditRefuseBackup> auditRefuseBackups =
                labRoomLendingService.getAuditRefuseBackupForLabReservation((page-1)*pageSize, pageSize,
                        labReservation.getLabRoom() == null ? null : labReservation.getLabRoom().getLabRoomName());
        Integer totalRecords = labRoomLendingService.getCountAuditRefuseBackupForLabReservation(
                labReservation.getLabRoom() == null ? null : labReservation.getLabRoom().getLabRoomName());
        List<Object[]> items = new ArrayList<>();
        for (AuditRefuseBackup auditRefuseBackup: auditRefuseBackups){
            Object[] objects = new Object[8];
            Integer id = Integer.parseInt(auditRefuseBackup.getRefuseItemBackup().iterator().next().getBusinessId());
            LabRoom labRoom = labRoomDAO.findLabRoomById(id);
            objects[0] = labRoom.getLabRoomName() + "(" + labRoom.getId() + ")";
            objects[1] = userDAO.findUserByUsername(auditRefuseBackup.getRefuseItemBackup().iterator().next().getCreator());
            Integer term = auditRefuseBackup.getRefuseItemBackup().iterator().next().getTerm();
            objects[2] = schoolTermDAO.findSchoolTermById(term).getTermName();
            objects[3] = auditRefuseBackup.getRefuseItemBackup().iterator().next().getStartWeek();
            objects[4] = auditRefuseBackup.getRefuseItemBackup().iterator().next().getWeekday();
            String section = "";
            for (RefuseItemBackup refuseItemBackup: auditRefuseBackup.getRefuseItemBackup()){
                if(!refuseItemBackup.getStartClass().equals(refuseItemBackup.getEndClass())) {
                    section += refuseItemBackup.getStartClass() + " - " + refuseItemBackup.getEndClass() + ", ";
                }else {
                    section += refuseItemBackup.getStartClass() + ", ";
                }
            }
            objects[5] = section;
            objects[6] = "审核信息：" + auditRefuseBackup.getAuditInfo() + "<br>审核备注：" + auditRefuseBackup.getAuditContent();
            objects[7] = auditRefuseBackup.getRefuseItemBackup().iterator().next().getOperationItemName();
            items.add(objects);
        }
        mav.addObject("items", items);
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        mav.addObject("auditRefuseBackups", auditRefuseBackups);
        mav.addObject("pageModel", pageModel);
        mav.setViewName("/lab/lab_lending/labReservationObsoleteList.jsp");
        return mav;
    }
}