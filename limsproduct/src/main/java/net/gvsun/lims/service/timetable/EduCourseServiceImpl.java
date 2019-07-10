package net.gvsun.lims.service.timetable;

import api.net.gvsunlims.constant.ConstantInterface;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.common.PConfigDTO;
import net.gvsun.lims.dto.timetable.TimetableParamVO;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service("eduCourseService")
public class EduCourseServiceImpl implements EduCourseService {
    @Autowired
    private SchoolCourseDetailDAO schoolCourseDetailDAO;
    @Autowired
    private ShareService shareService;
    @Autowired
    private TimetableAppointmentDAO timetableAppointmentDAO;
    @Autowired
    private SchoolCourseDAO schoolCourseDAO;
    @Autowired
    private TimetableGroupDAO timetableGroupDAO;
    @Autowired
    private TimetableGroupStudentsDAO timetableGroupStudentsDAO;
    @Autowired
    private TimetableManagerService timetableManagerService;
    @Autowired
    private TimetableCommonService timetableCommonService;
    @Autowired
    private MessageDAO messageDAO;

    /*************************************************************************************
     * Description:教务课程-保存调整排课
     *
     * @author： 魏誠
     * @date：2018-08-03
     *************************************************************************************/
    @Transactional
    public boolean apiSaveTimetableAppointmentByEduAdjustCourse(TimetableParamVO timetableParamVO) {
        //如果为编辑，先删除
        if (timetableParamVO.getSameNumberId() != -1 && timetableParamVO.getSameNumberId() > 0) {
            timetableManagerService.deleteTimetableByBySameNumberId(timetableParamVO.getSameNumberId());
        }
        // 初始化排课预约对象
        // 获取当前教学班下的排课计划
        TimetableAppointment timetableAppointment = new TimetableAppointment();
        SchoolCourseDetail schoolCourseDetail = schoolCourseDetailDAO.findSchoolCourseDetailByPrimaryKey(timetableParamVO.getCourseDetailNo());
        //获取当前的星期
        //星期根据所选计划：school_course_detail的weekday反算获取
        timetableParamVO.setWeekday(schoolCourseDetail.getWeekday());
        //根据选课组courseCode遍历SchoolCourseDetail，
        // 将相同选课组的教务数据直接推送到排课表
        timetableAppointment.setAppointmentNo(schoolCourseDetail.getCourseDetailNo());
        //保存学期
        timetableAppointment.setSchoolTerm(schoolCourseDetail.getSchoolTerm());
        timetableAppointment.setCreatedBy(shareService.getUserDetail().getUsername());
        timetableAppointment.setCreatedBy(shareService.getUserDetail().getUsername());
        timetableAppointment.setCreatedDate(Calendar.getInstance());
        timetableAppointment.setUpdatedDate(Calendar.getInstance());
        timetableAppointment.setSchoolCourseDetail(schoolCourseDetail);
        // 设置发布状态为待发布
        timetableAppointment.setStatus(timetableParamVO.getStatus());
        // 设置排课方式
        timetableAppointment.setTimetableStyle(timetableParamVO.getTimetableStyle());
        // 设置排课方式
        timetableAppointment.setDeviceOrLab(ConstantInterface.TIMETABLE_OBJECT_LAB);
        // 设置选课组编号
        timetableAppointment.setCourseCode(schoolCourseDetail.getSchoolCourse().getCourseCode());
        // 设置调整排课的内容
        //timetableAppointment.setTotalWeeks(schoolCourseDetail.getTotalWeeks());
        timetableAppointment.setWeekday(schoolCourseDetail.getWeekday());
        //timetableAppointment.setStartWeek(schoolCourseDetail.getStartWeek());
        //timetableAppointment.setEndWeek(schoolCourseDetail.getEndWeek());
        //timetableAppointment.setTotalClasses(schoolCourseDetail.getTotalClasses());
        //timetableAppointment.setStartClass(schoolCourseDetail.getStartClass());
        //timetableAppointment.setEndClass(schoolCourseDetail.getEndClass());
        timetableAppointment.setSchoolCourse(schoolCourseDetail.getSchoolCourse());
        timetableAppointment.setSchoolCourseInfo((schoolCourseDetail.getSchoolCourse().getSchoolCourseInfo()));
        timetableAppointment.setPreparation("");
        timetableAppointment.setGroups(-1);
        timetableAppointment.setLabhours(-1);
        timetableAppointment.setGroupCount(-1);
        timetableAppointment.setConsumablesCosts(new BigDecimal(-1));
        // ttimetableAppointment.setStatus(status);
        // 保存排课预约表
        TimetableAppointment timetableAppointmentSaved = timetableAppointmentDAO.store(timetableAppointment);
        //保存AppointmentSameNumber
        timetableParamVO.setTimetableId(timetableAppointmentSaved.getId());

        timetableCommonService.saveTimetableAppointmentSameNumber(timetableParamVO);
        // 设置此次排课的针对对象（实验室）
        timetableCommonService.saveTimetableLabRooms(timetableParamVO);
        // 设置此次排课的针对对象（授课教师）
        timetableCommonService.saveTimetableTeachers(timetableParamVO);
        // 设置此次排课的针对对象（指导教师）
        timetableCommonService.saveTimetableTutors(timetableParamVO);
        // 设置此次排课的针对对象（实验项目）
        timetableCommonService.saveTimetableItems(timetableParamVO);
        return true;
    }

    /*************************************************************************************
     * Description:教务课程-保存二次排课
     *
     * @author： 魏誠
     * @date：2018-08-03
     *************************************************************************************/
    @Transactional
    public boolean apiSaveCourseTimetableAppointment(TimetableParamVO timetableParamVO) {
        //如果为编辑，先删除
        if (timetableParamVO.getSameNumberId() != -1 && timetableParamVO.getSameNumberId() > 0) {
            timetableManagerService.deleteTimetableByBySameNumberId(timetableParamVO.getSameNumberId());
        }
        // 初始化排课预约对象
        // 获取当前教学班下的排课计划
        TimetableAppointment timetableAppointment = new TimetableAppointment();
        SchoolCourse schoolCourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(timetableParamVO.getCourseNo());
        //获取当前的星期
        //星期根据所选计划：school_course_detail的weekday反算获取
        //根据选课组courseCode遍历SchoolCourseDetail，
        //保存学期
        timetableAppointment.setSchoolTerm(schoolCourse.getSchoolTerm());
        timetableAppointment.setCreatedBy(shareService.getUserDetail().getUsername());
        timetableAppointment.setCreatedBy(shareService.getUserDetail().getUsername());
        timetableAppointment.setCreatedDate(Calendar.getInstance());
        timetableAppointment.setUpdatedDate(Calendar.getInstance());
        //timetableAppointment.setSchoolCourseDetail(schoolCourseDetail);
        // 设置发布状态为待发布
        timetableAppointment.setStatus(timetableParamVO.getStatus());
        // 设置排课方式
        timetableAppointment.setTimetableStyle(timetableParamVO.getTimetableStyle());
        // 设置排课方式
        timetableAppointment.setDeviceOrLab(ConstantInterface.TIMETABLE_OBJECT_LAB);
        // 设置选课组编号
        timetableAppointment.setCourseCode(schoolCourse.getCourseCode());
        // 设置排课的内容
        timetableAppointment.setWeekday(timetableParamVO.getWeekday());
        timetableAppointment.setSchoolCourse(schoolCourse);
        timetableAppointment.setSchoolCourseDetail(schoolCourseDetailDAO.findSchoolCourseDetailByPrimaryKey(timetableParamVO.getCourseDetailNo()));
        timetableAppointment.setSchoolCourseInfo((schoolCourse.getSchoolCourseInfo()));
        timetableAppointment.setPreparation("");
        timetableAppointment.setGroups(-1);
        timetableAppointment.setLabhours(-1);
        timetableAppointment.setGroupCount(-1);
        timetableAppointment.setConsumablesCosts(new BigDecimal(-1));
        // ttimetableAppointment.setStatus(status);
        // 保存排课预约表
        TimetableAppointment timetableAppointmentSaved = timetableAppointmentDAO.store(timetableAppointment);
        TimetableGroup timetableGroup = timetableGroupDAO.findTimetableGroupById(timetableParamVO.getGroupId());
        //学生情况下，保存分组，并保存计划定义的学生名单
        if (Objects.nonNull(timetableGroup) && (timetableParamVO.getTimetableStyle() == 4 || timetableParamVO.getTimetableStyle() == 6)) {
            Set<TimetableAppointment> timetableAppointments = new HashSet<TimetableAppointment>();
            timetableAppointments = timetableGroup.getTimetableAppointments();
            timetableAppointments.add(timetableAppointmentSaved);
            timetableGroup.setTimetableAppointments(timetableAppointments);
            timetableGroup = timetableGroupDAO.store(timetableGroup);
            if (timetableGroup.getTimetableBatch().getIfselect() == 0) {
                timetableGroup = this.apiSaveTimetableGroupStudent(timetableGroup);//
            }
        }
        //保存AppointmentSameNumber
        timetableParamVO.setTimetableId(timetableAppointmentSaved.getId());
        timetableCommonService.saveTimetableAppointmentSameNumber(timetableParamVO);
        // 设置此次排课的针对对象（实验室）
        timetableCommonService.saveTimetableLabRooms(timetableParamVO);
        // 设置此次排课的针对对象（授课教师）
        timetableCommonService.saveTimetableTeachers(timetableParamVO);
        // 设置此次排课的针对对象（指导教师）
        timetableCommonService.saveTimetableTutors(timetableParamVO);
        // 设置此次排课的针对对象（实验项目）
        timetableCommonService.saveTimetableItems(timetableParamVO);
        return true;
    }

    /*************************************************************************************
     * Description:教务课程-保存分组学生
     *
     * @author： 魏誠
     * @date：2018-08-03
     *************************************************************************************/
    public TimetableGroup apiSaveTimetableGroupStudent(TimetableGroup timetableGroup) {
        //如果timetableGroup不为空且分组名单未满
        //获取尚未排TimetableGroupStudents
        TimetableGroup timetableGroupReturn = timetableGroup;
        if (Objects.nonNull(timetableGroup) && Objects.nonNull(timetableGroup.getTimetableGroupStudentses())
                && timetableGroup.getNumbers() > timetableGroup.getTimetableGroupStudentses().size()
                || (Objects.nonNull(timetableGroup) && Objects.isNull(timetableGroup.getTimetableGroupStudentses()))) {
            int startNumber = 0;
            if (Objects.nonNull(timetableGroup) && Objects.nonNull(timetableGroup.getTimetableGroupStudentses())) {
                startNumber = timetableGroup.getTimetableGroupStudentses().size();
            }
            Set<TimetableAppointment> timetableAppointments = timetableGroup.getTimetableAppointments();
            Set<SchoolCourseStudent> schoolCourseStudents = new HashSet<SchoolCourseStudent>();
            if (Objects.nonNull(timetableAppointments) && timetableAppointments.iterator().hasNext() && Objects.nonNull(timetableAppointments.iterator().next().getSchoolCourse()) && timetableAppointments.iterator().next().getSchoolCourse().getSchoolCourseDetails().iterator().hasNext()) {
                schoolCourseStudents = timetableAppointments.iterator().next().getSchoolCourse().getSchoolCourseDetails().iterator().next().getSchoolCourseStudents();
            }
            //
            for (SchoolCourseStudent schoolCourseStudent : schoolCourseStudents) {
                String sql = "select c from TimetableGroupStudents c where c.timetableGroup.timetableBatch=" + timetableGroup.getTimetableBatch().getId() + " and c.user.username like '" + schoolCourseStudent.getUserByStudentNumber().getUsername() + "'";
                List<TimetableGroupStudents> timetableGroupStudentses = timetableGroupStudentsDAO.executeQuery(sql);
                if ((Objects.isNull(timetableGroupStudentses) || timetableGroupStudentses.size() == 0) && timetableGroup.getNumbers() - timetableGroup.getTimetableGroupStudentses().size() > 0) {
                    TimetableGroupStudents timetableGroupStudents = new TimetableGroupStudents();
                    timetableGroupStudents.setTimetableGroup(timetableGroup);
                    timetableGroupStudents.setUser(schoolCourseStudent.getUserByStudentNumber());
                    timetableGroupStudents = timetableGroupStudentsDAO.store(timetableGroupStudents);
                    timetableGroupReturn = timetableGroupStudents.getTimetableGroup();
                    Set<TimetableGroupStudents> timetableGroupStudentss = timetableGroupReturn.getTimetableGroupStudentses();
                    timetableGroupStudentss.add(timetableGroupStudents);
                    timetableGroupReturn.setTimetableGroupStudentses(timetableGroupStudentss);
                    timetableGroupReturn = timetableGroupDAO.store(timetableGroupReturn);
                }
            }
        }
        return timetableGroupReturn;
    }

    /*************************************************************************************
     * Description:教务课程-保存直接排课
     *
     * @author： 魏誠
     * @date：2018-08-03
     *************************************************************************************/
    public boolean apiSaveTimetableAppointmentByEduDirectCourse(TimetableParamVO timetableParamVO) {
        // 先提交，如果失败则不往下进行
        // 提交到审核服务
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        Map<String, String> params = new HashMap<>();
        String businessType = "TimetableAudit";
        params.put("businessUid", "-1");
        params.put("businessType", pConfigDTO.PROJECT_NAME + businessType);
        params.put("businessAppUid", timetableParamVO.getCourseNo());
        String s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/saveInitBusinessAuditStatus", params);
        JSONObject jsonObject = JSON.parseObject(s);
        String status = jsonObject.getString("status");
        if (!"success".equals(status)) {
            return false;
        }
        // 初始化排课预约对象
        // 获取当前教学班下的排课计划
        TimetableAppointment timetableAppointment = new TimetableAppointment();
        List<SchoolCourseDetail> schoolCourseDetails = schoolCourseDetailDAO
                .executeQuery("select c from SchoolCourseDetail c where c.schoolCourse.courseNo like '" + timetableParamVO.getCourseNo()
                        + "'");
        //根据选课组courseCode遍历SchoolCourseDetail，
        // 将相同选课组的教务数据直接推送到排课表
        for (SchoolCourseDetail schoolCourseDetail : schoolCourseDetails) {
            timetableAppointment.setAppointmentNo(schoolCourseDetail.getCourseDetailNo());
            //保存学期
            timetableAppointment.setSchoolTerm(schoolCourseDetail.getSchoolTerm());
            timetableAppointment.setCreatedBy(shareService.getUserDetail().getUsername());
            timetableAppointment.setCreatedBy(shareService.getUserDetail().getUsername());
            timetableAppointment.setCreatedDate(Calendar.getInstance());
            timetableAppointment.setUpdatedDate(Calendar.getInstance());
            timetableAppointment.setSchoolCourseDetail(schoolCourseDetail);
            // 设置发布状态为待发布
            timetableAppointment.setStatus(ConstantInterface.TIMETABLE_STATUS_NO_PUBLIC);
            // 设置排课方式
            timetableAppointment.setTimetableStyle(ConstantInterface.TIMETABLE_STYLE_DIRECT_COURSE);
            // 设置排课方式
            timetableAppointment.setDeviceOrLab(ConstantInterface.TIMETABLE_OBJECT_LAB);
            // 设置选课组编号
            timetableAppointment.setCourseCode(schoolCourseDetail.getSchoolCourse().getCourseCode());
            // 设置调整排课的内容
            //timetableAppointment.setTotalWeeks(schoolCourseDetail.getTotalWeeks());
            timetableAppointment.setWeekday(schoolCourseDetail.getWeekday());
            //timetableAppointment.setStartWeek(schoolCourseDetail.getStartWeek());
            //timetableAppointment.setEndWeek(schoolCourseDetail.getEndWeek());
            //timetableAppointment.setTotalClasses(schoolCourseDetail.getTotalClasses());
            //timetableAppointment.setStartClass(schoolCourseDetail.getStartClass());
            //timetableAppointment.setEndClass(schoolCourseDetail.getEndClass());
            timetableAppointment.setSchoolCourse(schoolCourseDetail.getSchoolCourse());
            timetableAppointment.setSchoolCourseInfo((schoolCourseDetail.getSchoolCourse().getSchoolCourseInfo()));
            timetableAppointment.setPreparation("");
            timetableAppointment.setGroups(-1);
            timetableAppointment.setLabhours(-1);
            timetableAppointment.setGroupCount(-1);
            timetableAppointment.setConsumablesCosts(new BigDecimal(-1));
            timetableAppointment.setStatus(timetableParamVO.getStatus());
            // 保存排课预约表
            timetableAppointment = timetableAppointmentDAO.store(timetableAppointment);
            //保存AppointmentSameNumber
            timetableParamVO.setTimetableId(timetableAppointment.getId());
            //保存AppointmentSameNumber
            //设置获取的节次
            //设置获取的周次
            int[] classes = new int[schoolCourseDetail.getEndClass() - schoolCourseDetail.getStartClass() + 1];
            int[] weeks = new int[schoolCourseDetail.getEndWeek() - schoolCourseDetail.getStartWeek() + 1];
            for (int i = 0; i < schoolCourseDetail.getEndClass() - schoolCourseDetail.getStartClass() + 1; i++) {
                classes[i] = schoolCourseDetail.getStartClass() + i;
            }
            timetableParamVO.setClasses(classes);
            for (int i = 0; i < schoolCourseDetail.getEndWeek() - schoolCourseDetail.getStartWeek() + 1; i++) {
                weeks[i] = schoolCourseDetail.getStartWeek() + i;
            }
            timetableParamVO.setWeeks(weeks);
            timetableCommonService.saveTimetableAppointmentSameNumber(timetableParamVO);
            // 设置此次排课的针对对象（实验室）
            timetableCommonService.saveTimetableLabRooms(timetableParamVO);
            // 设置此次排课的针对对象（授课教师）
            timetableCommonService.saveTimetableTeachers(timetableParamVO);
            // 设置此次排课的针对对象（指导教师）
            timetableCommonService.saveTimetableTutors(timetableParamVO);
        }

        Map<String, String> curParams = new HashMap<>();
        curParams.put("businessType", pConfigDTO.PROJECT_NAME + businessType);
        curParams.put("businessAppUid", timetableParamVO.getCourseNo());
        String curStr = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/getCurrAuditStage", curParams);
        JSONObject curJSONObject = JSON.parseObject(curStr);
        String curStatus = curJSONObject.getString("status");
        JSONArray curJSONArray = curJSONObject.getJSONArray("data");
        JSONObject curJSONObject0 = curJSONArray.getJSONObject(0);
        String firstAuthName = curJSONObject0.getString("result");
        Set<User> isAuditUser = new HashSet<>();
        //消息
        User user = shareService.getUserDetail();
        Integer termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
        Message message = new Message();
        message.setSendUser(user.getCname());
        message.setSendCparty(user.getSchoolAcademy().getAcademyName());
        message.setCond(0);
        message.setTitle("排课预约增加");
        String content = "<a onclick='changeMessage(this)' href='../lims/timetable/engineer/educourse/auditTimetable?termId=" + termId.toString() + "&courseNo=" + timetableParamVO.getCourseNo() + "&type=1'>审核</a>";
        message.setContent(content);
        message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
        message.setCreateTime(Calendar.getInstance());
        message.setTage(2);
        switch (firstAuthName) {
            case "pass":
                break;
            case "fail":
                break;
            case "LABMANAGER":
                SchoolCourse course = schoolCourseDAO.findSchoolCourseByPrimaryKey(timetableParamVO.getCourseNo());
                for (TimetableAppointment timetableAppointment1 : course.getTimetableAppointments()) {
                    for (TimetableLabRelated timetableLabRelated : timetableAppointment1.getTimetableLabRelateds()) {
                        for (LabRoomAdmin labRoomAdmin : timetableLabRelated.getLabRoom().getLabRoomAdmins()) {
                            isAuditUser.add(labRoomAdmin.getUser());
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

        return true;
    }
}