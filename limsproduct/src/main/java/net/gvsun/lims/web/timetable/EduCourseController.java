package net.gvsun.lims.web.timetable;

import api.net.gvsunlims.constant.ConstantInterface;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.common.PConfigDTO;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.CStaticValueService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.timetable.OuterApplicationService;
import net.zjcclims.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/****************************************************************************
 * Descriptions：教务排课管理模块
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ****************************************************************************/
@Controller("EduCourseController")
@SessionAttributes("selected_academy")
@RequestMapping("/lims/timetable/engineer/educourse")
public class EduCourseController<JsonResult> {
    @Autowired
    private OuterApplicationService outerApplicationService;
    @Autowired
    private ShareService shareService;
    @Autowired
    private CStaticValueService cStaticValueService;
    @Autowired
    private SchoolCourseDAO schoolCourseDAO;
    @Autowired
    private SchoolCourseDetailDAO schoolCourseDetailDAO;
    @Autowired
    private SchoolAcademyDAO schoolAcademyDAO;
    @Autowired
    private TimetableAppointmentSameNumberDAO timetableAppointmentSameNumberDAO;
    @Autowired
    private AuthorityDAO authorityDAO;
    @Autowired
    private TimetableAppointmentDAO timetableAppointmentDAO;
    @Autowired
    private TimetableSelfCourseDAO timetableSelfCourseDAO;
    @Autowired
    private MessageDAO messageDAO;
    @Autowired
    private AuditRefuseBackupDAO auditRefuseBackupDAO;
    @Autowired
    private RefuseItemBackupDAO refuseItemBackupDAO;

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
    public ModelAndView listTimetableTerm(HttpServletRequest request, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        // 当前学期
        mav.addObject("termId", shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId());
        // 获取学期列表
        List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
        mav.addObject("schoolTerms", schoolTerms);
        mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
        // 获取实验室排课的通用配置对象；
        CStaticValue cStaticValue = cStaticValueService.getCStaticValueByTimetableLabDevice(acno);
        mav.addObject("cStaticValue", cStaticValue);
        // 获取可选的教师列表列表
        mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap(acno));
        mav.addObject("directTimetable", pConfigDTO.directTimetable);
        mav.addObject("eduDirect", pConfigDTO.eduDirect);
        mav.addObject("eduAjust", pConfigDTO.eduAjust);
        mav.addObject("eduBatch", pConfigDTO.eduBatch);
        mav.addObject("eduNoBatch", pConfigDTO.eduNoBatch);
        mav.setViewName("lims/timetable/engineer/educourse/eduCourseList.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：教务排课管理-教务直接排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/newEduDirectCourse")
    public ModelAndView newEduCourseList(HttpServletRequest request, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        String academyNumber = "";
        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
        if (!acno.equals("-1")) {
            //获取选择的实验中心
            academyNumber = shareService.findSchoolAcademyByPrimaryKey(acno).getAcademyNumber();
        }
        //获取课程编号
        SchoolCourse schoolCourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(request.getParameter("courseNo"));
        mav.addObject("schoolCourse", schoolCourse);
        mav.addObject("courseNo", request.getParameter("courseNo"));
        mav.addObject("academyNumber", academyNumber);
        mav.setViewName("lims/timetable/engineer/educourse/newEduDirectCourse.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：教务排课管理-教务直接排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/newEduAdjustCourse")
    public ModelAndView newEduAdjustCourse(HttpServletRequest request, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        String academyNumber = "";
        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
        if (!acno.equals("-1")) {
            //获取选择的实验中心
            academyNumber = shareService.findSchoolAcademyByPrimaryKey(acno).getAcademyNumber();
        }
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
        mav.setViewName("lims/timetable/engineer/educourse/newEduAdjustCourse.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：教务排课管理-教务二次分批排课的主显示页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
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
        mav.setViewName("lims/timetable/engineer/educourse/updateEduAdjustCourse.jsp");
        return mav;
    }

    /**
     * Description 审核页面
     */
    @RequestMapping("/auditTimetable")
    public ModelAndView auditTimetable(HttpServletRequest request, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        Integer type = Integer.valueOf(request.getParameter("type"));
        mav.addObject("type", type);
        SchoolCourse schoolCourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(request.getParameter("courseNo"));
        String courseNo = request.getParameter("courseNo");
        mav.addObject("courseNo", courseNo);
        //默认教务排课，type=1
        String businessType = "TimetableAudit";
        if(type == 1) {
            mav.addObject("termId", schoolCourse.getSchoolTerm().getId());
        }else if(type == 2){//自主排课，type=2
            TimetableSelfCourse course = timetableSelfCourseDAO.findTimetableSelfCourseById(Integer.valueOf(courseNo));
            mav.addObject("termId", course.getSchoolTerm().getId());
            businessType = "SelfTimetableAudit";
        }
        // 获取审核状态
        Integer curStage = -2;
        String curAuthName = "";
        Map<String, String> params = new HashMap<>();
        params.put("businessType", pConfigDTO.PROJECT_NAME + businessType);
        params.put("businessUid", "-1");
        params.put("businessAppUid", courseNo);
        String currStr = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/getCurrAuditStage", params);
        JSONObject currJSONObject = JSONObject.parseObject(currStr);
        if ("success".equals(currJSONObject.getString("status"))) {
            JSONArray currArray = currJSONObject.getJSONArray("data");
            if (currArray != null && currArray.size() > 0) {
                JSONObject o = currArray.getJSONObject(0);
                curStage = o.getIntValue("level");
                curAuthName = o.getString("result");
            }
        }

        // 获取审核配置
        String s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/getBusinessLevelStatus", params);
        JSONObject jsonObject = JSONObject.parseObject(s);
        List<Object[]> auditItems = new ArrayList<>();
        if ("success".equals(jsonObject.getString("status"))) {
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray != null && jsonArray.size() > 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    Object[] os = new Object[6];
                    os[0] = authorityDAO.findAuthorityByAuthorityName(o.getString("authName")).iterator().next().getCname();
                    os[1] = o.getIntValue("level");
                    os[2] = o.getString("authName");
                    os[3] = courseNo;
                    os[4] = curStage;
                    os[5] = curAuthName;
                    auditItems.add(os);
                }
                mav.addObject("count", jsonArray.size());
            }
        }
        mav.addObject("auditConfigs", auditItems);
        mav.setViewName("lims/timetable/course/auditTimetableAll.jsp");
        return mav;
    }

    /**
     * Description 审核子页面
     */
    @RequestMapping("/auditTimetableSingle")
    public ModelAndView auditTimetableSingle(HttpServletRequest request, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        Integer isAudit = 0;
        Integer type = Integer.valueOf(request.getParameter("type"));
        String courseNo = request.getParameter("courseNo");
        Integer state = Integer.parseInt(request.getParameter("state"));
        Integer curStage = Integer.parseInt(request.getParameter("curStage"));
        String curAuthName = request.getParameter("curAuthName");
        String authName = request.getParameter("authName");
        mav.addObject("state", state);
        mav.addObject("curStage", curStage);
        mav.addObject("courseNo", courseNo);
        mav.addObject("type", type);
        if (state < curStage || curStage == 0 || curStage == -1) {
            // 获取审核配置
            Map<String, String> params = new HashMap<>();
            //默认教务排课，type=1
            String businessType = "TimetableAudit";
            if(type == 2){//自主排课，type=2
                businessType = "SelfTimetableAudit";
            }
            params.put("businessType", pConfigDTO.PROJECT_NAME + businessType);
            params.put("businessUid", "-1");
            params.put("businessAppUid", courseNo);
            String s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/getBusinessLevelStatus", params);
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
            Set<User> isAuditUser = new HashSet<>();
            switch (authName) {
                case "LABMANAGER":
                    if (type == 1) {
                        SchoolCourse course = schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo);
                        for (TimetableAppointment timetableAppointment : course.getTimetableAppointments()) {
                            for (TimetableLabRelated timetableLabRelated : timetableAppointment.getTimetableLabRelateds()) {
                                for (LabRoomAdmin labRoomAdmin : timetableLabRelated.getLabRoom().getLabRoomAdmins()) {
                                    isAuditUser.add(labRoomAdmin.getUser());
                                }
                            }
                        }
                    } else if (type == 2) {
                        TimetableSelfCourse course = timetableSelfCourseDAO.findTimetableSelfCourseById(Integer.parseInt(courseNo));
                        for (TimetableAppointment timetableAppointment : course.getTimetableAppointments()) {
                            for (TimetableLabRelated timetableLabRelated : timetableAppointment.getTimetableLabRelateds()) {
                                for (LabRoomAdmin labRoomAdmin : timetableLabRelated.getLabRoom().getLabRoomAdmins()) {
                                    isAuditUser.add(labRoomAdmin.getUser());
                                }
                            }
                        }
                    }
                    break;
                default:
                    isAuditUser.addAll(shareService.findUsersByAuthorityNameAndAcno(curAuthName,acno));
            }
            if (request.getSession().getAttribute("selected_role").equals("ROLE_" + curAuthName)) {
                for (User eUser : isAuditUser) {
                    if (eUser.getUsername().equals(shareService.getUserDetail().getUsername()) && curAuthName.equals(authName)) {
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
        mav.setViewName("lims/timetable/course/auditTimetableSingle.jsp");
        return mav;
    }

    /**
     * Description 保存排课审核结果
     * @param request
     * @return
     * @author 黄保钱 2019-1-17
     */
    @RequestMapping("/saveCourseTimetableAudit")
    public @ResponseBody
    String saveCourseTimetableAudit(HttpServletRequest request) {
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        String courseNo = request.getParameter("courseNo");
        Integer auditResult = Integer.parseInt(request.getParameter("auditResult"));
        String remark = request.getParameter("remark");
        Integer type = Integer.valueOf(request.getParameter("type"));
        User user = shareService.getUserDetail();

        //默认教务排课，type=1
        String businessType = "TimetableAudit";
        if(type == 2){//自主排课，type=2
            businessType = "SelfTimetableAudit";
        }
        Map<String, String> params = new HashMap<>();
        params.put("businessType", pConfigDTO.PROJECT_NAME + businessType);
        params.put("businessAppUid", courseNo);
        params.put("businessUid", "-1");
        params.put("result", auditResult == 1 ? "pass" : "fail");
        params.put("info", remark);
        params.put("username", shareService.getUserDetail().getUsername());
        String s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/saveBusinessLevelAudit", params);

        String nextAuthName = "";
        JSONObject jsonObject = JSONObject.parseObject(s);
        if ("success".equals(jsonObject.getString("status"))) {
            JSONObject resultJSONObject = jsonObject.getJSONObject("data");
            nextAuthName = (String) resultJSONObject.values().iterator().next();
        }
        // 排课人
        String creator = "";
        if (type == 1) {
            SchoolCourse course = schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo);
            creator = course.getTimetableAppointments().iterator().next().getCreatedBy();
        }else {
            TimetableSelfCourse course = timetableSelfCourseDAO.findTimetableSelfCourseById(Integer.parseInt(courseNo));
            creator = course.getTimetableAppointments().iterator().next().getCreatedBy();
        }
        // 保存审核拒绝的审核信息
        AuditRefuseBackup auditRefuseBackup = new AuditRefuseBackup();
        if(auditResult != 1) {
            //消息
            Message message = new Message();
            message.setSendUser(user.getCname());
            message.setSendCparty(user.getSchoolAcademy().getAcademyName());
            message.setCond(0);
            message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
            message.setCreateTime(Calendar.getInstance());
            String content = "";
            if (type == 1) {
                message.setTitle(pConfigDTO.refuseTitle);
                content = "<a onclick='changeMessage(this)' href='../lims/timetable/engineer/educourse/eduCourseList'>查看</a>";
            }else {
                message.setTitle(pConfigDTO.selfRefuseTitle);
                content = "<a onclick='changeMessage(this)' href='../lims/timetable/engineer/selfcourse/selfCourseList'>查看</a>";
            }
            message.setContent(content);
            message.setUsername(creator);
            message.setTage(1);
            messageDAO.store(message);
            messageDAO.flush();

            Map<String, String> allParams = new HashMap<>();
            allParams.put("businessType", pConfigDTO.PROJECT_NAME + businessType);
            allParams.put("businessAppUid", courseNo);
            allParams.put("businessUid", "-1");
            String allStr = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/getBusinessLevelStatus", allParams);
            JSONArray allJsonArray = JSONObject.parseObject(allStr).getJSONArray("data");
            String auditInfo = "";
            String auditContent = "";
            for (int i = 0; i < allJsonArray.size(); i++) {
                JSONObject o = allJsonArray.getJSONObject(i);
                String result = o.getString("result");
                String authName = o.getString("authName");
                Authority authority = authorityDAO.findAuthorityByName(authName);
                auditInfo += authority.getCname();
                String auditUsername = o.getString("auditUser");
                User auditUser = shareService.findUserByUsername(auditUsername);
                auditInfo += auditUser.getCname() + result;
                auditContent += o.getString("info");
                if ("审核拒绝".equals(result)) {
                    break;
                }else{
                    auditInfo += ", ";
                    auditContent += ", ";
                }
            }
            auditRefuseBackup.setAuditInfo(auditInfo);
            auditRefuseBackup.setAuditContent(auditContent);
            auditRefuseBackup = auditRefuseBackupDAO.store(auditRefuseBackup);
            Map<String, String> delParams = new HashMap<>();
            delParams.put("businessAppUid", courseNo);
            delParams.put("businessType", pConfigDTO.PROJECT_NAME + businessType);
            String delStr = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/deleteBusinessAudit", delParams);
        }

        // 审核拒绝的业务信息保存
        if (type == 1) {
            SchoolCourse course = schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo);
            if ("pass".equals(nextAuthName)) {
                for (TimetableAppointment ta : course.getTimetableAppointments()) {
                    ta.setStatus(ConstantInterface.TIMETABLE_STATUS_AUDIT_ACCESS);
                    timetableAppointmentDAO.store(ta);
                }
            } else if ("fail".equals(nextAuthName)) {
                for (TimetableAppointment ta : course.getTimetableAppointments()) {
                    for(TimetableAppointmentSameNumber tasn: ta.getTimetableAppointmentSameNumbers()) {
                        RefuseItemBackup refuseItemBackup = new RefuseItemBackup();
                        refuseItemBackup.setAuditRefuseBackup(auditRefuseBackup);
                        refuseItemBackup.setBusinessId(courseNo);
                        refuseItemBackup.setCreator(shareService.findUserByUsername(ta.getCreatedBy()).getCname());
                        refuseItemBackup.setStartClass(tasn.getStartClass());
                        refuseItemBackup.setEndClass(tasn.getEndClass());
                        refuseItemBackup.setStartWeek(tasn.getStartWeek());
                        refuseItemBackup.setEndWeek(tasn.getEndWeek());
                        refuseItemBackup.setTerm(ta.getSchoolTerm().getId());
                        refuseItemBackup.setWeekday(ta.getWeekday());
                        refuseItemBackup.setType("EduTimetableAudit");
                        String labRoomNames = "";
                        for (TimetableLabRelated tlr : ta.getTimetableLabRelateds()) {
                            labRoomNames += ", " + tlr.getLabRoom().getLabRoomName();
                        }
                        refuseItemBackup.setLabRoomName(labRoomNames.length() > 0 ? labRoomNames.substring(2) : "");
                        String operationItems = "";
                        for (TimetableItemRelated tir : ta.getTimetableItemRelateds()) {
                            operationItems += ", " + tir.getOperationItem().getLpName();
                        }
                        refuseItemBackup.setOperationItemName(operationItems.length() > 0 ? operationItems.substring(2) : "");
                        String teachers = "";
                        for (TimetableTeacherRelated ttr : ta.getTimetableTeacherRelateds()) {
                            teachers += ", " + ttr.getUser().getCname();
                        }
                        refuseItemBackup.setTeacher(teachers.length() > 0 ? teachers.substring(2) : "");
                        String tutors = "";
                        for (TimetableTutorRelated ttr : ta.getTimetableTutorRelateds()) {
                            tutors += ", " + ttr.getUser().getCname();
                        }
                        refuseItemBackup.setTutor(tutors.length() > 0 ? tutors.substring(2) : "");
                        refuseItemBackupDAO.store(refuseItemBackup);
                    }
                    timetableAppointmentDAO.remove(ta);
                }
            }
        }

        if (type == 2) {
            TimetableSelfCourse course = timetableSelfCourseDAO.findTimetableSelfCourseById(Integer.parseInt(courseNo));
            if ("pass".equals(nextAuthName)) {
                for (TimetableAppointment ta : course.getTimetableAppointments()) {
                    ta.setStatus(ConstantInterface.TIMETABLE_STATUS_AUDIT_ACCESS);
                    timetableAppointmentDAO.store(ta);
                }
            } else if ("fail".equals(nextAuthName)) {
                for (TimetableAppointment ta : course.getTimetableAppointments()) {
                    for(TimetableAppointmentSameNumber tasn: ta.getTimetableAppointmentSameNumbers()) {
                        RefuseItemBackup refuseItemBackup = new RefuseItemBackup();
                        refuseItemBackup.setAuditRefuseBackup(auditRefuseBackup);
                        refuseItemBackup.setBusinessId(courseNo);
                        refuseItemBackup.setCreator(shareService.findUserByUsername(ta.getCreatedBy()).getCname());
                        refuseItemBackup.setStartClass(tasn.getStartClass());
                        refuseItemBackup.setEndClass(tasn.getEndClass());
                        refuseItemBackup.setStartWeek(tasn.getStartWeek());
                        refuseItemBackup.setEndWeek(tasn.getEndWeek());
                        refuseItemBackup.setTerm(ta.getSchoolTerm().getId());
                        refuseItemBackup.setWeekday(ta.getWeekday());
                        refuseItemBackup.setType("EduTimetableAudit");
                        String labRoomNames = "";
                        for (TimetableLabRelated tlr : ta.getTimetableLabRelateds()) {
                            labRoomNames += ", " + tlr.getLabRoom().getLabRoomName();
                        }
                        refuseItemBackup.setLabRoomName(labRoomNames.length() > 0 ? labRoomNames.substring(2) : "");
                        String operationItems = "";
                        for (TimetableItemRelated tir : ta.getTimetableItemRelateds()) {
                            operationItems += ", " + tir.getOperationItem().getLpName();
                        }
                        refuseItemBackup.setOperationItemName(operationItems.length() > 0 ? operationItems.substring(2) : "");
                        String teachers = "";
                        for (TimetableTeacherRelated ttr : ta.getTimetableTeacherRelateds()) {
                            teachers += ", " + ttr.getUser().getCname();
                        }
                        refuseItemBackup.setTeacher(teachers.length() > 0 ? teachers.substring(2) : "");
                        String tutors = "";
                        for (TimetableTutorRelated ttr : ta.getTimetableTutorRelateds()) {
                            tutors += ", " + ttr.getUser().getCname();
                        }
                        refuseItemBackup.setTutor(tutors.length() > 0 ? tutors.substring(2) : "");
                        refuseItemBackupDAO.store(refuseItemBackup);
                    }
                    timetableAppointmentDAO.remove(ta);
                }
            }
        }
        if(auditResult != 1){
            return "refuse";
        }
        Map<String, String> curParams = new HashMap<>();
        curParams.put("businessType", pConfigDTO.PROJECT_NAME + businessType);
        curParams.put("businessAppUid", courseNo);
        String curStr = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/getCurrAuditStage", curParams);
        JSONObject curJSONObject = JSON.parseObject(curStr);
        String curStatus = curJSONObject.getString("status");
        JSONArray curJSONArray = curJSONObject.getJSONArray("data");
        JSONObject curJSONObject0 = curJSONArray.getJSONObject(0);
        String firstAuthName = curJSONObject0.getString("result");

        Integer termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
        //消息
        Message message = new Message();
        message.setSendUser(user.getCname());
        message.setSendCparty(user.getSchoolAcademy().getAcademyName());
        message.setCond(0);
        message.setTitle("排课预约增加");
        String content = "<a onclick='changeMessage(this)' href='../lims/timetable/engineer/educourse/auditTimetable?termId=" + termId.toString() + "&courseNo=" + courseNo + "&type=" + type + "'>审核</a>";
        message.setContent(content);
        message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
        message.setCreateTime(Calendar.getInstance());
        message.setTage(2);
        Set<User> isAuditUser = new HashSet<>();
        switch (firstAuthName) {
            case "pass":
                message.setTitle("排课审核通过");
                content = "<a onclick='changeMessage(this)' href='../lims/timetable/engineer/educourse/auditTimetable?termId=" + termId.toString() + "&courseNo=" + courseNo + "&type=" + type + "'>查看</a>";
                message.setContent(content);
                message.setUsername(creator);
                message.setTage(1);
                messageDAO.store(message);
                messageDAO.flush();
            case "LABMANAGER":
                if (type == 1) {// 教务排课
                    SchoolCourse course = schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo);
                    for (TimetableAppointment timetableAppointment : course.getTimetableAppointments()) {
                        for (TimetableLabRelated timetableLabRelated : timetableAppointment.getTimetableLabRelateds()) {
                            for (LabRoomAdmin labRoomAdmin : timetableLabRelated.getLabRoom().getLabRoomAdmins()) {
                                isAuditUser.add(labRoomAdmin.getUser());
                            }
                        }
                    }
                } else if (type == 2) {// 自主排课
                    TimetableSelfCourse course = timetableSelfCourseDAO.findTimetableSelfCourseById(Integer.parseInt(courseNo));
                    for (TimetableAppointment timetableAppointment : course.getTimetableAppointments()) {
                        for (TimetableLabRelated timetableLabRelated : timetableAppointment.getTimetableLabRelateds()) {
                            for (LabRoomAdmin labRoomAdmin : timetableLabRelated.getLabRoom().getLabRoomAdmins()) {
                                isAuditUser.add(labRoomAdmin.getUser());
                            }
                        }
                    }
                }
                break;
            default:
                isAuditUser.addAll(shareService.findUsersByAuthorityName(firstAuthName));
        }
        for (User receiveUser : isAuditUser) {
            message.setUsername(receiveUser.getUsername());
            messageDAO.store(message);
            messageDAO.flush();
        }
        return JSONObject.parseObject(s).getString("status");
    }

}
