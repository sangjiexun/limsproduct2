package net.gvsun.lims.service.timetable;

import api.net.gvsunlims.constant.ConstantInterface;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.common.JsonValueDTO;
import net.gvsun.lims.dto.common.PConfigDTO;
import net.gvsun.lims.dto.common.SelectDTO;
import net.gvsun.lims.dto.timetable.TimetableDTO;
import net.gvsun.lims.dto.timetable.TimetableMergeDTO;
import net.gvsun.lims.dto.timetable.TimetableParamVO;
import net.gvsun.lims.vo.timtable.common.ViewTimetableVO;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.util.HttpClientUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Service("timetableCommonService")
public class TimetableCommonServiceImpl implements TimetableCommonService {
    @Autowired
    private SchoolCourseDetailDAO schoolCourseDetailDAO;
    @Autowired
    private TimetableItemRelatedDAO timetableItemRelatedDAO;
    @Autowired
    private SchoolCourseDAO schoolCourseDAO;
    @Autowired
    private TimetableAppointmentDAO timetableAppointmentDAO;
    @Autowired
    private LabRoomDAO labRoomDAO;
    @Autowired
    private TimetableAppointmentSameNumberDAO timetableAppointmentSameNumberDAO;
    @Autowired
    private SchoolWeekdayDAO schoolWeekdayDAO;
    @Autowired
    private SchoolWeekDAO schoolWeekDAO;
    @Autowired
    private SystemTimeDAO systemTimeDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private TimetableTutorRelatedDAO timetableTutorRelatedDAO;
    @Autowired
    private TimetableLabRelatedDAO timetableLabRelatedDAO;
    @Autowired
    private TimetableTeacherRelatedDAO timetableTeacherRelatedDAO;
    @Autowired
    private OperationItemDAO operationItemDAO;
    @Autowired
    private TimetableManagerService timetableManagerService;
    @Autowired
    private TimetableSelfCourseDAO timetableSelfCourseDAO;
    @Autowired
    private ShareService shareService;
    @Autowired
    private MessageDAO messageDAO;
    @PersistenceContext
    private EntityManager entityManager;

    /*************************************************************************************
     * Description:教务课程-获取课程库列表
     *
     * @author： 魏誠
     * @date：2018-08-03
     *************************************************************************************/
    public BaseDTO apiViewTimetableInfo(String courseNo) {
        //封装VO
        ViewTimetableVO viewTimetableVO = new ViewTimetableVO();
        SchoolCourse schoolCourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo);
        //获取排课信息
        List<TimetableDTO> timetableDTOs = this.apiTimetableDTOs(courseNo);
        List<TimetableAppointment> timetableAppointments = timetableAppointmentDAO
                .executeQuery("select c from TimetableAppointment c where c.schoolCourse.courseNo like '" + courseNo
                        + "'");
        int total = timetableAppointments.size();
        //获取课程信息
        viewTimetableVO.setCourseName(schoolCourse.getCourseName());
        viewTimetableVO.setCourseNumber(schoolCourse.getSchoolCourseInfo().getCourseNumber());
        if (total > 0) {
            viewTimetableVO.setTimetableStyle(timetableAppointments.get(0).getTimetableStyle());
            viewTimetableVO.setStatus(timetableAppointments.get(0).getStatus());
        }
        viewTimetableVO.setTimetables(timetableDTOs);
        BaseDTO baseVO = new BaseDTO();
        baseVO.setRows(timetableDTOs);
        baseVO.setTotal(total);
        return baseVO;
    }

    /*************************************************************************************
     * Description:教务课程-获取课程库列表
     *
     * @author： 魏誠
     * @date：2018-08-03
     *************************************************************************************/
    public BaseDTO apiViewTimetableInfo(Integer selfId) {
        //封装VO
        ViewTimetableVO viewTimetableVO = new ViewTimetableVO();
        TimetableSelfCourse timetableSelfCourse = timetableSelfCourseDAO.findTimetableSelfCourseById(selfId);
        //获取排课信息
        List<TimetableDTO> timetableDTOs = this.apiTimetableDTOs(selfId);
        List<TimetableAppointment> timetableAppointments = timetableAppointmentDAO
                .executeQuery("select c from TimetableAppointment c where c.timetableSelfCourse.id = " + selfId);
        int total = timetableAppointments.size();
        //获取课程信息
        viewTimetableVO.setCourseName(timetableSelfCourse.getSchoolCourseInfo().getCourseName());
        viewTimetableVO.setCourseNumber(timetableSelfCourse.getSchoolCourseInfo().getCourseNumber());
        if (total > 0) {
            viewTimetableVO.setTimetableStyle(timetableAppointments.get(0).getTimetableStyle());
            viewTimetableVO.setStatus(timetableAppointments.get(0).getStatus());
        }
        viewTimetableVO.setTimetables(timetableDTOs);
        BaseDTO baseVO = new BaseDTO();
        baseVO.setRows(timetableDTOs);
        baseVO.setTotal(total);
        return baseVO;
    }

    /*************************************************************************************
     * Description:教务课程-获取课程排课信息列表
     *
     * @author： 魏誠
     * @date：2018-08-03
     *************************************************************************************/
    public List<TimetableMergeDTO> apiGetMergeTimetableClassAndWeek(TimetableMergeDTO timetableMergeDTO) {
        String timetableInfo = "";
        //合并周次
        // 获取当前教学班下的排课计划
        String sql = "select c from TimetableLabRelated c where c.timetableAppointment.schoolCourse.courseNo like '" + timetableMergeDTO.getCourseNo() + "' and c.timetableAppointment.schoolTerm.id=" + timetableMergeDTO.getTerm()
                + " order by c.labRoom.id, c.timetableAppointment.weekday";
        List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO
                .executeQuery(sql);
        TimetableMergeDTO timetableMergeDTO_1 = new TimetableMergeDTO();
        timetableMergeDTO_1.setWeekday(0);
        timetableMergeDTO_1.setStartClass(0);
        timetableMergeDTO_1.setEndClass(0);
        timetableMergeDTO_1.setStartWeek(0);
        timetableMergeDTO_1.setEndWeek(0);
        List<TimetableMergeDTO> timetableMergeDTOsByClass = new ArrayList<TimetableMergeDTO>();
        int thisSize = 1;
        for (TimetableLabRelated timetableLabRelated : timetableLabRelateds) {
            if (timetableMergeDTO_1.getStartClass() == 0 || timetableMergeDTO_1.getWeekday() == timetableLabRelated.getTimetableAppointment().getWeekday() && timetableMergeDTO_1.getLab() == timetableLabRelated.getLabRoom().getId()) {
                for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : timetableLabRelated.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
                    //如果为第一次，则赋初值
                    if (timetableMergeDTO_1.getStartClass() == 0) {
                        timetableMergeDTO_1.setStartClass(timetableAppointmentSameNumber.getStartClass());
                        timetableMergeDTO_1.setEndClass(timetableAppointmentSameNumber.getEndClass());
                        timetableMergeDTO_1.setStartWeek(timetableAppointmentSameNumber.getStartWeek());
                        timetableMergeDTO_1.setEndWeek(timetableAppointmentSameNumber.getEndWeek());
                        timetableMergeDTO_1.setWeekday(timetableLabRelated.getTimetableAppointment().getWeekday());
                        timetableMergeDTO_1.setLab(timetableLabRelated.getLabRoom().getId());
                        timetableMergeDTO_1.setLabInfo(timetableLabRelated.getLabRoom().getLabRoomName());
                        //如果排课不为同一星期或同一实验室则，节次相同周次连续则，拼接
                    } else if (timetableMergeDTO_1.getStartClass() == timetableAppointmentSameNumber.getStartClass() &&
                            timetableMergeDTO_1.getEndClass() == timetableAppointmentSameNumber.getEndClass() &&
                            timetableAppointmentSameNumber.getStartWeek() - timetableMergeDTO_1.getEndWeek() == 1) {
                        timetableMergeDTO_1.setEndWeek(timetableAppointmentSameNumber.getEndWeek());
                    } else if (timetableMergeDTO_1.getStartWeek() == timetableAppointmentSameNumber.getStartWeek() &&
                            timetableMergeDTO_1.getEndWeek() == timetableAppointmentSameNumber.getEndWeek() &&
                            timetableAppointmentSameNumber.getStartClass() - timetableMergeDTO_1.getEndClass() == 1) {
                        timetableMergeDTO_1.setEndClass(timetableAppointmentSameNumber.getEndClass());
                    } else {
                        if (thisSize < timetableLabRelateds.size()) {
                            timetableInfo = "星期" + timetableMergeDTO_1.getWeekday() +
                                    ";节[" + timetableMergeDTO_1.getStartClass() + "-" + timetableMergeDTO_1.getEndClass() + "]" +
                                    ";周[" + timetableMergeDTO_1.getStartWeek() + "-" + timetableMergeDTO_1.getEndWeek() + "]<br/>" +
                                    ";实验室[" + timetableMergeDTO_1.getLabInfo() + "]<br/>";
                            timetableMergeDTO_1.setLabInfo(timetableLabRelated.getLabRoom().getLabRoomName());
                            //如果不是最后一条记录
                            timetableMergeDTOsByClass.add(timetableMergeDTO_1);
                            timetableMergeDTO_1 = new TimetableMergeDTO();
                            timetableMergeDTO_1.setStartClass(timetableAppointmentSameNumber.getStartClass());
                            timetableMergeDTO_1.setEndClass(timetableAppointmentSameNumber.getEndClass());
                            timetableMergeDTO_1.setStartWeek(timetableAppointmentSameNumber.getStartWeek());
                            timetableMergeDTO_1.setEndWeek(timetableAppointmentSameNumber.getEndWeek());
                            timetableMergeDTO_1.setWeekday(timetableLabRelated.getTimetableAppointment().getWeekday());
                            timetableMergeDTO_1.setLab(timetableLabRelated.getLabRoom().getId());
                            timetableMergeDTO_1.setLabInfo(timetableLabRelated.getLabRoom().getLabRoomName());
                        }
                    }
                }
            } else {
                //如果不是最后一条记录
                if (thisSize < timetableLabRelateds.size()) {
                    timetableInfo = "星期" + timetableMergeDTO_1.getWeekday() +
                            ";节[" + timetableMergeDTO_1.getStartClass() + "-" + timetableMergeDTO_1.getEndClass() + "]" +
                            ";周[" + timetableMergeDTO_1.getStartWeek() + "-" + timetableMergeDTO_1.getEndWeek() + "]<br/>" +
                            ";实验室[" + timetableMergeDTO_1.getLabInfo() + "]<br/>";
                    timetableMergeDTO_1.setTimetableInfo(timetableInfo);
                    timetableMergeDTOsByClass.add(timetableMergeDTO_1);
                    timetableMergeDTO_1 = new TimetableMergeDTO();
                    timetableMergeDTO_1.setTimetableInfo(timetableInfo);
                    timetableMergeDTO_1.setWeekday(0);
                    timetableMergeDTO_1.setStartClass(0);
                    timetableMergeDTO_1.setEndClass(0);
                    timetableMergeDTO_1.setStartWeek(0);
                    timetableMergeDTO_1.setEndWeek(0);
                    timetableInfo = "";
                }
                continue;
            }
            //如果是最后一条记录
            if (thisSize == timetableLabRelateds.size()) {
                timetableInfo = "星期" + timetableMergeDTO_1.getWeekday() +
                        ";节[" + timetableMergeDTO_1.getStartClass() + "-" + timetableMergeDTO_1.getEndClass() + "]" +
                        ";周[" + timetableMergeDTO_1.getStartWeek() + "-" + timetableMergeDTO_1.getEndWeek() + "]<br/>" +
                        ";实验室[" + timetableMergeDTO_1.getLabInfo() + "]<br/>";
                timetableMergeDTO_1.setTimetableInfo(timetableInfo);
                timetableMergeDTOsByClass.add(timetableMergeDTO_1);
            }
            //累加
            thisSize++;
        }
        //
        //开始合并周次
        //
       /* List<TimetableMergeDTO> timetableMergeDTOsByWeek = new ArrayList<TimetableMergeDTO>();
        TimetableMergeDTO timetableMergeDTO_week_1 = new TimetableMergeDTO();

        for (TimetableMergeDTO timetableMergeDTO_2 : timetableMergeDTOsByClass) {
            if (timetableMergeDTO_week_1.getWeekday() == timetableMergeDTO_2.getWeekday() && timetableMergeDTO_week_1.getLab() == timetableMergeDTO_2.getLab()) {
                //如果为第一次，则赋初值
                if (timetableMergeDTO_week_1.getCourseNo() == null) {
                    timetableMergeDTO_week_1 =timetableMergeDTO_2;
                    //如果排课不为同一星期或同一实验室则，节次相同周次连续则，拼接
                } else if (timetableMergeDTO_2.getStartWeek() == timetableMergeDTO_week_1.getStartWeek() &&
                        timetableMergeDTO_2.getEndWeek() == timetableMergeDTO_week_1.getEndWeek() &&
                        timetableMergeDTO_2.getStartClass() - timetableMergeDTO_week_1.getEndClass() == 1) {
                    timetableMergeDTO_week_1.setEndWeek(timetableMergeDTO_2.getEndWeek());
                }
            } else {
                timetableInfo += "星期"+timetableMergeDTO_week_1.getWeekday()+
                        ";节["+timetableMergeDTO_week_1.getStartClass()+"-"+timetableMergeDTO_week_1.getEndClass()+"]"+
                        ";周["+timetableMergeDTO_week_1.getStartWeek()+"-"+timetableMergeDTO_week_1.getEndWeek()+"]"+
                        ";实验室["+timetableMergeDTO_week_1.getLabInfo()+"]<br/>";
                timetableMergeDTO_week_1.setTimetableInfo(timetableInfo);
                timetableMergeDTOsByClass.add(timetableMergeDTO_week_1);
                timetableMergeDTO_week_1 =timetableMergeDTO_2;
                continue;
            }
        }*/
        return timetableMergeDTOsByClass;
    }

    /*************************************************************************************
     * Description:教务课程-获取课程排课信息列表(教务排课)
     *
     * @author： 魏誠
     * @date：2018-08-03
     *************************************************************************************/
    public List<TimetableDTO> apiTimetableDTOs(String courseNo) {
        // 初始化排课预约对象
        // 获取当前教学班下的排课计划
        String sql = "select c from TimetableLabRelated c where c.timetableAppointment.schoolCourse.courseNo like '" + courseNo + "' "
                + " order by c.labRoom.id, c.timetableAppointment.weekday";
        List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO
                .executeQuery(sql);
        int total = timetableLabRelateds.size();
        //封装VO
        ViewTimetableVO viewTimetableVO = new ViewTimetableVO();
        timetableManagerService.getTimetableByOrder(courseNo);
        List<TimetableDTO> timetableDTOs = new ArrayList<TimetableDTO>();
        for (TimetableLabRelated timetableLabRelated : timetableLabRelateds) {
            for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : timetableLabRelated.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
                TimetableDTO timetableDTO = new TimetableDTO();
                String timetable = "";
                //如果断周断节
                timetable += "星期" + timetableLabRelated.getTimetableAppointment().getWeekday() + " [" + timetableAppointmentSameNumber.getStartClass() + "-" + timetableAppointmentSameNumber.getEndClass() + "]节" + " [" + timetableAppointmentSameNumber.getStartWeek() + "-" + timetableAppointmentSameNumber.getEndWeek() + "]周" + "<br/>";
                //保存排课时间安排
                timetableDTO.setTimetable(timetable);
                timetableDTO.setSameNumberId(timetableAppointmentSameNumber.getId());
                timetableDTO.setWeekday(timetableLabRelated.getTimetableAppointment().getWeekday());
                timetableDTO.setStartClass(timetableAppointmentSameNumber.getStartClass());
                timetableDTO.setEndClass(timetableAppointmentSameNumber.getEndClass());
                timetableDTO.setStartWeek(timetableAppointmentSameNumber.getStartWeek());
                timetableDTO.setEndWeek(timetableAppointmentSameNumber.getEndWeek());
                if (Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment()) && Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment().getTimetableGroups()) && timetableAppointmentSameNumber.getTimetableAppointment().getTimetableGroups().size() > 0) {
                    TimetableGroup timetableGroup = timetableAppointmentSameNumber.getTimetableAppointment().getTimetableGroups().iterator().next();
                    timetableDTO.setBatchId(timetableGroup.getTimetableBatch().getId());
                    timetableDTO.setBatchName(timetableGroup.getTimetableBatch().getBatchName());
                    timetableDTO.setGroupId(timetableGroup.getId());
                    timetableDTO.setGroupName(timetableGroup.getGroupName());
                    timetableDTO.setGroupNumbers(timetableGroup.getNumbers());
                    timetableDTO.setGroupStudents(timetableGroup.getTimetableGroupStudentses().size());
                }
                String courseDetailNo = null;
                if (Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourseDetail())) {
                    courseDetailNo = timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourseDetail().getCourseDetailNo();
                }
                timetableDTO.setCourseDetailNo(courseDetailNo);
                //保存教学班
                timetableDTO.setCourseNo(timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourse().getCourseNo());
                //保存课程
                timetableDTO.setCourseName(timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourseInfo().getCourseName());
                timetableDTO.setCourseNumber(timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourseInfo().getCourseNumber());
                //保存学期
                timetableDTO.setTermName(timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourse().getSchoolTerm().getTermName());
                //保存实验室
                timetableDTO.setLabInfo(timetableLabRelated.getLabRoom().getLabRoomName()+"[编号:"+timetableLabRelated.getLabRoom().getLabRoomNumber()+"]");
                //保存上课教师
                String teachers = "";
                for (TimetableTeacherRelated timetableTeacherRelated : timetableLabRelated.getTimetableAppointment().getTimetableTeacherRelateds()) {
                    teachers += timetableTeacherRelated.getUser().getCname() + "</br>(" + timetableTeacherRelated.getUser().getUsername() + ")";
                }
                timetableDTO.setTeachers(teachers);
                //保存上课教师
                String tutors = "";
                for (TimetableTutorRelated timetableTutorRelated : timetableLabRelated.getTimetableAppointment().getTimetableTutorRelateds()) {
                    tutors += timetableTutorRelated.getUser().getCname() + "</br>(" + timetableTutorRelated.getUser().getUsername() + ")";
                }
                timetableDTO.setTutors(tutors);
                //保存实验项目
                String items = "";
                for (TimetableItemRelated timetableItemRelated : timetableLabRelated.getTimetableAppointment().getTimetableItemRelateds()) {
                    if (Objects.nonNull(timetableItemRelated.getOperationItem())) {
                        items += timetableItemRelated.getOperationItem().getLpName() + "<br>";
                    }
                }
                timetableDTO.setItems(items);
                if (timetableDTO.getBatchId() == null) {
                    timetableDTO.setBatchId(-1);
                }
                if (timetableDTO.getGroupId() == null) {
                    timetableDTO.setGroupId(-1);
                }
                timetableDTO.setAdjustStatus(timetableAppointmentSameNumber.getTimetableAppointment().getAdjustStatus());
                timetableDTO.setStatus(timetableAppointmentSameNumber.getTimetableAppointment().getStatus());
                timetableDTOs.add(timetableDTO);

            }
        }
        //Collections.sort(timetableDTOs, Comparator.comparing(TimetableDTO::getBatchId).thenComparing(TimetableDTO::getGroupId).thenComparing(TimetableDTO::getStartClass).thenComparing(TimetableDTO::getStartWeek));
        Collections.sort(timetableDTOs, new Comparator<TimetableDTO>() {
            @Override
            public int compare(TimetableDTO a, TimetableDTO b) {
                // 批次优先
                if (!a.getBatchId().equals(b.getBatchId())) {
                    return a.getBatchId().compareTo(b.getBatchId());
                }
                // 当前分组
                if (!a.getGroupId().equals(b.getGroupId())) {
                    return a.getGroupId().compareTo(b.getGroupId());
                }
                // 当前星期
                if (!Integer.valueOf(a.getWeekday()).equals(Integer.valueOf(b.getWeekday()))) {
                    return Integer.valueOf(a.getWeekday()).compareTo(Integer.valueOf(b.getWeekday()));
                }
                if (!Integer.valueOf(a.getStartClass()).equals(Integer.valueOf(b.getStartClass()))) {
                    return Integer.valueOf(a.getStartClass()).compareTo(b.getStartClass());
                }
                return Integer.valueOf(a.getStartWeek()).compareTo(Integer.valueOf(b.getStartWeek()));
            }
        });
        //list按照批次，组，节次，周次排序
        //Collections.sort(timetableDTOs, Comparator.comparing(TimetableDTO::getBatchId).thenComparing(TimetableDTO::getGroupId).thenComparing(TimetableDTO::getStartClass).thenComparing(TimetableDTO::getStartWeek));
        return timetableDTOs;
    }

    /*************************************************************************************
     * Description:教务课程-获取课程排课信息列表(自主排课)
     *
     * @author： 魏誠
     * @date：2018-08-03
     *************************************************************************************/
    public List<TimetableDTO> apiTimetableDTOsByGroup(Integer groupId) {
        // 初始化排课预约对象
        // 获取当前教学班下的排课计划
        String sql = "select c from TimetableLabRelated c inner join c.timetableAppointment.timetableGroups g where g.id = " + groupId
                + " order by c.labRoom.id, c.timetableAppointment.weekday";
        List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO
                .executeQuery(sql);
        int total = timetableLabRelateds.size();
        //封装VO
        ViewTimetableVO viewTimetableVO = new ViewTimetableVO();
        //timetableManagerService.getTimetableByOrder(courseNo);
        List<TimetableDTO> timetableDTOs = new ArrayList<TimetableDTO>();
        for (TimetableLabRelated timetableLabRelated : timetableLabRelateds) {
            for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : timetableLabRelated.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
                TimetableDTO timetableDTO = new TimetableDTO();
                String timetable = "";
                //如果断周断节
                timetable += "星期" + timetableLabRelated.getTimetableAppointment().getWeekday() + " [" + timetableAppointmentSameNumber.getStartClass() + "-" + timetableAppointmentSameNumber.getEndClass() + "]节" + " [" + timetableAppointmentSameNumber.getStartWeek() + "-" + timetableAppointmentSameNumber.getEndWeek() + "]周" + "<br/>";
                //保存排课时间安排
                timetableDTO.setId(timetableLabRelated.getTimetableAppointment().getId());
                timetableDTO.setSameNumberId(timetableAppointmentSameNumber.getId());
                timetableDTO.setTimetable(timetable);
                timetableDTO.setWeekday(timetableLabRelated.getTimetableAppointment().getWeekday());
                timetableDTO.setStartClass(timetableAppointmentSameNumber.getStartClass());
                timetableDTO.setEndClass(timetableAppointmentSameNumber.getEndClass());
                timetableDTO.setStartWeek(timetableAppointmentSameNumber.getStartWeek());
                timetableDTO.setEndWeek(timetableAppointmentSameNumber.getEndWeek());
                if (Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment().getTimetableGroups()) && timetableAppointmentSameNumber.getTimetableAppointment().getTimetableGroups().size() > 0) {
                    TimetableGroup timetableGroup = timetableAppointmentSameNumber.getTimetableAppointment().getTimetableGroups().iterator().next();
                    timetableDTO.setBatchId(timetableGroup.getTimetableBatch().getId());
                    timetableDTO.setBatchName(timetableGroup.getTimetableBatch().getBatchName());
                    timetableDTO.setGroupId(timetableGroup.getId());
                    timetableDTO.setGroupName(timetableGroup.getGroupName());
                    timetableDTO.setGroupNumbers(timetableGroup.getNumbers());
                    timetableDTO.setGroupStudents(timetableGroup.getTimetableGroupStudentses().size());
                }
                String courseDetailNo = null;
                if (Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourseDetail())) {
                    courseDetailNo = timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourseDetail().getCourseDetailNo();
                }
                timetableDTO.setCourseDetailNo(courseDetailNo);
                //保存教学班
                if (Objects.nonNull(timetableAppointmentSameNumber) && Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment()) && Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourse())) {
                    timetableDTO.setCourseNo(timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourse().getCourseNo());
                }
                if (Objects.nonNull(timetableAppointmentSameNumber) && Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment()) && Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment().getTimetableSelfCourse())) {
                    timetableDTO.setSelfId(String.valueOf(timetableAppointmentSameNumber.getTimetableAppointment().getTimetableSelfCourse().getId()));
                }
                //保存课程
                timetableDTO.setCourseName(timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourseInfo().getCourseName());
                timetableDTO.setCourseNumber(timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourseInfo().getCourseNumber());
                //保存学期
                if (Objects.nonNull(timetableAppointmentSameNumber) && Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment()) && Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourse())) {
                    timetableDTO.setTermName(timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourse().getSchoolTerm().getTermName());
                }
                if (Objects.nonNull(timetableAppointmentSameNumber) && Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment()) && Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment().getTimetableSelfCourse())) {
                    timetableDTO.setTermName(timetableAppointmentSameNumber.getTimetableAppointment().getTimetableSelfCourse().getSchoolTerm().getTermName());
                }
                //保存实验室
                timetableDTO.setLabInfo(timetableLabRelated.getLabRoom().getLabRoomName());
                //保存上课教师
                String teachers = "";
                for (TimetableTeacherRelated timetableTeacherRelated : timetableLabRelated.getTimetableAppointment().getTimetableTeacherRelateds()) {
                    teachers += timetableTeacherRelated.getUser().getCname() + "</br>(" + timetableTeacherRelated.getUser().getUsername() + ")";
                }
                timetableDTO.setTeachers(teachers);
                //保存上课教师
                String tutors = "";
                for (TimetableTutorRelated timetableTutorRelated : timetableLabRelated.getTimetableAppointment().getTimetableTutorRelateds()) {
                    tutors += timetableTutorRelated.getUser().getCname() + "</br>(" + timetableTutorRelated.getUser().getUsername() + ")";
                }
                timetableDTO.setTutors(tutors);
                //保存实验项目
                String items = "";
                for (TimetableItemRelated timetableItemRelated : timetableLabRelated.getTimetableAppointment().getTimetableItemRelateds()) {
                    if (Objects.nonNull(timetableItemRelated.getOperationItem())) {
                        items += timetableItemRelated.getOperationItem().getLpName();
                    }
                }
                timetableDTO.setItems(items);
                if (timetableDTO.getBatchId() == null) {
                    timetableDTO.setBatchId(-1);
                }
                if (timetableDTO.getGroupId() == null) {
                    timetableDTO.setGroupId(-1);
                }
                timetableDTOs.add(timetableDTO);

            }
        }
        //list按照批次，组，节次，周次排序
        //Collections.sort(timetableDTOs, Comparator.comparing(TimetableDTO::getBatchId).thenComparing(TimetableDTO::getGroupId).thenComparing(TimetableDTO::getStartClass).thenComparing(TimetableDTO::getStartWeek));
        Collections.sort(timetableDTOs, new Comparator<TimetableDTO>() {
            @Override
            public int compare(TimetableDTO a, TimetableDTO b) {
                // 批次优先
                if (!a.getBatchId().equals(b.getBatchId())) {
                    return a.getBatchId().compareTo(b.getBatchId());
                }
                // 当前分组
                if (!a.getGroupId().equals(b.getGroupId())) {
                    return a.getGroupId().compareTo(b.getGroupId());
                }
                // 当前星期
                if (!Integer.valueOf(a.getWeekday()).equals(Integer.valueOf(b.getWeekday()))) {
                    return Integer.valueOf(a.getWeekday()).compareTo(Integer.valueOf(b.getWeekday()));
                }
                if (!Integer.valueOf(a.getStartClass()).equals(Integer.valueOf(b.getStartClass()))) {
                    return Integer.valueOf(b.getStartClass()).compareTo(a.getStartClass());
                }
                return Integer.valueOf(a.getStartWeek()).compareTo(Integer.valueOf(b.getStartWeek()));
            }
        });
        return timetableDTOs;
    }

    /*************************************************************************************
     * Description:教务课程-获取课程排课信息列表(自主排课)
     *
     * @author： 魏誠
     * @date：2018-08-03
     *************************************************************************************/
    public List<TimetableDTO> apiTimetableDTOs(Integer selfId) {
        // 初始化排课预约对象
        // 获取当前教学班下的排课计划
        String sql = "select c from TimetableLabRelated c where c.timetableAppointment.timetableSelfCourse.id = " + selfId
                + " order by c.labRoom.id, c.timetableAppointment.weekday";
        List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO
                .executeQuery(sql);
        int total = timetableLabRelateds.size();
        //封装VO
        ViewTimetableVO viewTimetableVO = new ViewTimetableVO();
        //timetableManagerService.getTimetableByOrder(courseNo);
        List<TimetableDTO> timetableDTOs = new ArrayList<TimetableDTO>();
        for (TimetableLabRelated timetableLabRelated : timetableLabRelateds) {
            for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : timetableLabRelated.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
                TimetableDTO timetableDTO = new TimetableDTO();
                String timetable = "";
                //如果断周断节
                timetable += "星期" + timetableLabRelated.getTimetableAppointment().getWeekday() + " [" + timetableAppointmentSameNumber.getStartClass() + "-" + timetableAppointmentSameNumber.getEndClass() + "]节" + " [" + timetableAppointmentSameNumber.getStartWeek() + "-" + timetableAppointmentSameNumber.getEndWeek() + "]周" + "<br/>";
                //保存排课时间安排
                timetableDTO.setId(timetableLabRelated.getTimetableAppointment().getId());
                timetableDTO.setSameNumberId(timetableAppointmentSameNumber.getId());
                timetableDTO.setTimetable(timetable);
                timetableDTO.setWeekday(timetableLabRelated.getTimetableAppointment().getWeekday());
                timetableDTO.setStartClass(timetableAppointmentSameNumber.getStartClass());
                timetableDTO.setEndClass(timetableAppointmentSameNumber.getEndClass());
                timetableDTO.setStartWeek(timetableAppointmentSameNumber.getStartWeek());
                timetableDTO.setEndWeek(timetableAppointmentSameNumber.getEndWeek());
                if (Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment().getTimetableGroups()) && timetableAppointmentSameNumber.getTimetableAppointment().getTimetableGroups().size() > 0) {
                    TimetableGroup timetableGroup = timetableAppointmentSameNumber.getTimetableAppointment().getTimetableGroups().iterator().next();
                    timetableDTO.setBatchId(timetableGroup.getTimetableBatch().getId());
                    timetableDTO.setBatchName(timetableGroup.getTimetableBatch().getBatchName());
                    timetableDTO.setGroupId(timetableGroup.getId());
                    timetableDTO.setGroupName(timetableGroup.getGroupName());
                    timetableDTO.setGroupNumbers(timetableGroup.getNumbers());
                    timetableDTO.setGroupStudents(timetableGroup.getTimetableGroupStudentses().size());
                }
                String courseDetailNo = null;
                if (Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourseDetail())) {
                    courseDetailNo = timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourseDetail().getCourseDetailNo();
                }
                timetableDTO.setCourseDetailNo(courseDetailNo);
                //保存教学班
                if (Objects.nonNull(timetableAppointmentSameNumber) && Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment()) && Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourse())) {
                    timetableDTO.setCourseNo(timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourse().getCourseNo());
                }
                if (Objects.nonNull(timetableAppointmentSameNumber) && Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment()) && Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment().getTimetableSelfCourse())) {
                    timetableDTO.setSelfId(String.valueOf(timetableAppointmentSameNumber.getTimetableAppointment().getTimetableSelfCourse().getId()));
                }
                //保存课程
                timetableDTO.setCourseName(timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourseInfo().getCourseName());
                timetableDTO.setCourseNumber(timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourseInfo().getCourseNumber());
                //保存学期
                if (Objects.nonNull(timetableAppointmentSameNumber) && Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment()) && Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourse())) {
                    timetableDTO.setTermName(timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourse().getSchoolTerm().getTermName());
                }
                if (Objects.nonNull(timetableAppointmentSameNumber) && Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment()) && Objects.nonNull(timetableAppointmentSameNumber.getTimetableAppointment().getTimetableSelfCourse())) {
                    timetableDTO.setTermName(timetableAppointmentSameNumber.getTimetableAppointment().getTimetableSelfCourse().getSchoolTerm().getTermName());
                }
                //保存实验室
                timetableDTO.setLabInfo(timetableLabRelated.getLabRoom().getLabRoomName());
                //保存上课教师
                String teachers = "";
                for (TimetableTeacherRelated timetableTeacherRelated : timetableLabRelated.getTimetableAppointment().getTimetableTeacherRelateds()) {
                    teachers += timetableTeacherRelated.getUser().getCname() + "</br>(" + timetableTeacherRelated.getUser().getUsername() + ")";
                }
                timetableDTO.setTeachers(teachers);
                //保存上课教师
                String tutors = "";
                for (TimetableTutorRelated timetableTutorRelated : timetableLabRelated.getTimetableAppointment().getTimetableTutorRelateds()) {
                    tutors += timetableTutorRelated.getUser().getCname() + "</br>(" + timetableTutorRelated.getUser().getUsername() + ")";
                }
                timetableDTO.setTutors(tutors);
                //保存实验项目
                String items = "";
                for (TimetableItemRelated timetableItemRelated : timetableLabRelated.getTimetableAppointment().getTimetableItemRelateds()) {
                    if (Objects.nonNull(timetableItemRelated.getOperationItem())) {
                        items += timetableItemRelated.getOperationItem().getLpName();
                    }
                }
                timetableDTO.setItems(items);
                if (timetableDTO.getBatchId() == null) {
                    timetableDTO.setBatchId(-1);
                }
                if (timetableDTO.getGroupId() == null) {
                    timetableDTO.setGroupId(-1);
                }
                timetableDTO.setAdjustStatus(timetableAppointmentSameNumber.getTimetableAppointment().getAdjustStatus());
                timetableDTO.setStatus(timetableAppointmentSameNumber.getTimetableAppointment().getStatus());
                timetableDTOs.add(timetableDTO);

            }
        }
        //list按照批次，组，节次，周次排序
        //Collections.sort(timetableDTOs, Comparator.comparing(TimetableDTO::getBatchId).thenComparing(TimetableDTO::getGroupId).thenComparing(TimetableDTO::getStartClass).thenComparing(TimetableDTO::getStartWeek));
        Collections.sort(timetableDTOs, new Comparator<TimetableDTO>() {
            @Override
            public int compare(TimetableDTO a, TimetableDTO b) {
                // 批次优先
                if (!a.getBatchId().equals(b.getBatchId())) {
                    return a.getBatchId().compareTo(b.getBatchId());
                }
                // 当前专题优先
                if (!a.getGroupId().equals(b.getGroupId())) {
                    return a.getGroupId().compareTo(b.getGroupId());
                }
                if (!Integer.valueOf(a.getStartClass()).equals(Integer.valueOf(b.getStartClass()))) {
                    return Integer.valueOf(b.getStartClass()).compareTo(a.getStartClass());
                }
                return Integer.valueOf(b.getStartWeek()).compareTo(Integer.valueOf(a.getStartWeek()));
            }
        });
        return timetableDTOs;
    }

    /*************************************************************************************
     * Description:排课管理-获取节次列表
     *
     * @author： 魏誠
     * @date：2018-09-06
     *************************************************************************************/
    public List<JsonValueDTO> getClassesListBySelect(String search) {
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select c from SystemTime c where 1=1 ");
        //查询条件
        if (search != null && !"".equals(search)) {
            sql.append(" and (c.username like '%" + search + "%' or");
            sql.append(" c.cname like '%" + search + "%' or");
            sql.append(" c.schoolAcademy.academyName like '%" + search + "%')");
        }
        sql.append(" order by c.id asc  ");
        // 执行sb语句
        List<SystemTime> systemTimes = systemTimeDAO.executeQuery(sql.toString(), 0, -1);
        int total = systemTimes.size();
        //封装VO
        List<JsonValueDTO> systemTimeDTOs = new ArrayList<JsonValueDTO>();
        for (SystemTime systemTime : systemTimes) {
            JsonValueDTO jsonValueDTO = new JsonValueDTO();
            //设置学院
            jsonValueDTO.setId(systemTime.getSection().toString());
            jsonValueDTO.setText(systemTime.getSectionName());
            systemTimeDTOs.add(jsonValueDTO);
        }
        return systemTimeDTOs;
    }

    /*************************************************************************************
     * Description:排课管理-获取星期列表
     *
     * @author： 魏誠
     * @date：2018-09-06
     *************************************************************************************/
    public List<JsonValueDTO> getWeekdayListBySelect(String search) {
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select c from SchoolWeekday c where 1=1 ");
        //查询条件
        if (search != null && !"".equals(search)) {
            sql.append(" and c.weekdayName like '%" + search + "%'");
        }
        sql.append(" order by c.id asc ");
        // 执行sb语句
        List<SchoolWeekday> schoolWeekdays = schoolWeekdayDAO.executeQuery(sql.toString(), 0, ConstantInterface.COMMON_SELECT_LIMIT);
        int total = schoolWeekdays.size();
        //封装VO
        List<JsonValueDTO> schoolWeekdayDTOs = new ArrayList<JsonValueDTO>();
        for (SchoolWeekday schoolWeekday : schoolWeekdays) {
            JsonValueDTO jsonValueDTO = new JsonValueDTO();
            //设置学院
            jsonValueDTO.setId(schoolWeekday.getId().toString());
            jsonValueDTO.setText(schoolWeekday.getWeekdayName());
            schoolWeekdayDTOs.add(jsonValueDTO);
        }
        return schoolWeekdayDTOs;
    }

    /*************************************************************************************
     * Description:排课管理-获取星期列表
     *
     * @author： 魏誠
     * @date：2018-09-06
     *************************************************************************************/
    public List<JsonValueDTO> apiWeekDayAdjustTimetableBySelect(String courseNo) {
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select c from SchoolCourseDetail c where 1=1 and c.schoolCourse.courseNo like '" + courseNo + "'");
        sql.append(" order by c.weekday asc ");
        // 执行sb语句
        List<SchoolCourseDetail> schoolCourseDetails = schoolCourseDetailDAO.executeQuery(sql.toString(), 0, ConstantInterface.COMMON_SELECT_LIMIT);
        int total = schoolCourseDetails.size();
        //封装VO
        List<JsonValueDTO> schoolCourseDetailsDTOs = new ArrayList<JsonValueDTO>();
        for (SchoolCourseDetail schoolCourseDetail : schoolCourseDetails) {
            JsonValueDTO jsonValueDTO = new JsonValueDTO();
            //设置学院
            jsonValueDTO.setId(schoolCourseDetail.getCourseDetailNo());
            jsonValueDTO.setText("星期" + schoolCourseDetail.getWeekday() + "(" + schoolCourseDetail.getStartClass() + "-" + schoolCourseDetail.getEndClass() + "节;" + schoolCourseDetail.getStartWeek() + "-" + schoolCourseDetail.getEndWeek() + "周)");
            schoolCourseDetailsDTOs.add(jsonValueDTO);
        }
        return schoolCourseDetailsDTOs;
    }

    /*************************************************************************************
     * Description:排课管理-获取星期列表
     *
     * @author： 魏誠
     * @date：2018-09-06
     *************************************************************************************/
    public List<JsonValueDTO> apiClassesAdjustTimetableBySelect(String courseDetailNo) {
        // 执行sb语句
        SchoolCourseDetail schoolCourseDetail = schoolCourseDetailDAO.findSchoolCourseDetailByPrimaryKey(courseDetailNo);
        //封装VO
        List<JsonValueDTO> schoolCourseDetailsDTOs = new ArrayList<JsonValueDTO>();
        for (int i = schoolCourseDetail.getStartClass(); i < schoolCourseDetail.getEndClass() + 1; i++) {
            JsonValueDTO jsonValueDTO = new JsonValueDTO();
            jsonValueDTO.setId(String.valueOf(i));
            jsonValueDTO.setText(String.valueOf(i));
            schoolCourseDetailsDTOs.add(jsonValueDTO);
        }
        return schoolCourseDetailsDTOs;
    }

    /*************************************************************************************
     * Description:排课管理-获取节次列表
     *
     * @author： 魏誠
     * @date：2018-09-06
     *************************************************************************************/
    public int[] getWeeksListBySelect(int term) {
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select c from SchoolWeek c where c.schoolTerm.id=" + term + " group by c.week order by c.week asc");
        //查询条件
        sql.append(" order by c.id asc ");
        // 执行sb语句
        List<SchoolWeek> schoolWeeks = schoolWeekDAO.executeQuery(sql.toString(), 0, -1);
        //封装VO
        int[] intSchoolWeeks = new int[schoolWeeks.size()];
        for (int i = 0; i < schoolWeeks.size(); i++) {
            intSchoolWeeks[i] = schoolWeeks.get(i).getWeek();
        }
        return intSchoolWeeks;
    }

    /*************************************************************************************
     * Description:教务课程-保存调整排课
     *
     * @author： 魏誠
     * @date：2018-08-03
     *************************************************************************************/
    @Transactional
    public boolean saveTimetableAppointmentSameNumber(TimetableParamVO timetableParamVO) {
        int[] classes = timetableParamVO.getClasses();
        int[] weeks = timetableParamVO.getWeeks();
        Arrays.sort(classes);
        Arrays.sort(weeks);
        List<TimetableAppointmentSameNumber> timetableClasses = new ArrayList<TimetableAppointmentSameNumber>();
        List<TimetableAppointmentSameNumber> timetableWeeks = new ArrayList<TimetableAppointmentSameNumber>();
        TimetableAppointmentSameNumber timetableAppointmentSameNumber = new TimetableAppointmentSameNumber();
        timetableAppointmentSameNumber.setTimetableAppointment(timetableAppointmentDAO.findTimetableAppointmentById(timetableParamVO.getTimetableId()));
        int startClass = 1;
        int endClass = 1;
        for (int i = 0; classes.length > i; i++) {
            if (i == 0 && i == classes.length - 1) {
                timetableAppointmentSameNumber.setStartClass(classes[i]);
                timetableAppointmentSameNumber.setEndClass(classes[i]);
                TimetableAppointmentSameNumber timetableAppointmentSameNumber_1 = new TimetableAppointmentSameNumber();
                timetableAppointmentSameNumber_1.setStartClass(timetableAppointmentSameNumber.getStartClass());
                timetableAppointmentSameNumber_1.setEndClass(timetableAppointmentSameNumber.getEndClass());
                timetableClasses.add(timetableAppointmentSameNumber_1);
            } else if (i == 0 && i != classes.length - 1) {
                timetableAppointmentSameNumber.setStartClass(classes[i]);
                timetableAppointmentSameNumber.setEndClass(classes[i]);
            } else if (i != 0 && i != classes.length - 1) {
                if (classes[i] - classes[i - 1] == 1) {
                    timetableAppointmentSameNumber.setEndClass(classes[i]);
                } else {
                    TimetableAppointmentSameNumber timetableAppointmentSameNumber_1 = new TimetableAppointmentSameNumber();
                    timetableAppointmentSameNumber_1.setStartClass(timetableAppointmentSameNumber.getStartClass());
                    timetableAppointmentSameNumber_1.setEndClass(timetableAppointmentSameNumber.getEndClass());
                    timetableClasses.add(timetableAppointmentSameNumber_1);
                    timetableAppointmentSameNumber.setStartClass(classes[i]);
                    timetableAppointmentSameNumber.setEndClass(classes[i]);
                }
            } else if (i != 0 && i == classes.length - 1) {
                if (classes[i] - classes[i - 1] == 1) {
                    timetableAppointmentSameNumber.setEndClass(classes[i]);
                    TimetableAppointmentSameNumber timetableAppointmentSameNumber_1 = new TimetableAppointmentSameNumber();
                    timetableAppointmentSameNumber_1.setStartClass(timetableAppointmentSameNumber.getStartClass());
                    timetableAppointmentSameNumber_1.setEndClass(timetableAppointmentSameNumber.getEndClass());
                    timetableClasses.add(timetableAppointmentSameNumber_1);
                } else {
                    TimetableAppointmentSameNumber timetableAppointmentSameNumber_1 = new TimetableAppointmentSameNumber();
                    timetableAppointmentSameNumber_1.setStartClass(timetableAppointmentSameNumber.getStartClass());
                    timetableAppointmentSameNumber_1.setEndClass(timetableAppointmentSameNumber.getEndClass());
                    timetableClasses.add(timetableAppointmentSameNumber_1);
                    TimetableAppointmentSameNumber timetableAppointmentSameNumber_2 = new TimetableAppointmentSameNumber();
                    timetableAppointmentSameNumber_2.setStartClass(classes[i]);
                    timetableAppointmentSameNumber_2.setEndClass(classes[i]);
                    timetableClasses.add(timetableAppointmentSameNumber_2);
                }
            }

        }

        int startWeek = 1;
        int endWeek = 1;
        for (int i = 0; weeks.length > i; i++) {
            if (i == 0 && i == weeks.length - 1) {
                timetableAppointmentSameNumber.setStartWeek(weeks[i]);
                timetableAppointmentSameNumber.setEndWeek(weeks[i]);
                TimetableAppointmentSameNumber timetableAppointmentSameNumber_1 = new TimetableAppointmentSameNumber();
                timetableAppointmentSameNumber_1.setStartWeek(timetableAppointmentSameNumber.getStartWeek());
                timetableAppointmentSameNumber_1.setEndWeek(timetableAppointmentSameNumber.getEndWeek());
                timetableWeeks.add(timetableAppointmentSameNumber_1);
            } else if (i == 0 && i != weeks.length - 1) {
                timetableAppointmentSameNumber.setStartWeek(weeks[i]);
                timetableAppointmentSameNumber.setEndWeek(weeks[i]);
            } else if (i != 0 && i != weeks.length - 1) {
                if (weeks[i] - weeks[i - 1] == 1) {
                    timetableAppointmentSameNumber.setEndWeek(weeks[i]);
                } else {
                    TimetableAppointmentSameNumber timetableAppointmentSameNumber_1 = new TimetableAppointmentSameNumber();
                    timetableAppointmentSameNumber_1.setStartWeek(timetableAppointmentSameNumber.getStartWeek());
                    timetableAppointmentSameNumber_1.setEndWeek(timetableAppointmentSameNumber.getEndWeek());
                    timetableWeeks.add(timetableAppointmentSameNumber_1);
                    timetableAppointmentSameNumber.setStartWeek(weeks[i]);
                    timetableAppointmentSameNumber.setEndWeek(weeks[i]);
                }
            } else if (i != 0 && i == weeks.length - 1) {
                if (weeks[i] - weeks[i - 1] == 1) {
                    timetableAppointmentSameNumber.setEndWeek(weeks[i]);
                    TimetableAppointmentSameNumber timetableAppointmentSameNumber_1 = new TimetableAppointmentSameNumber();
                    timetableAppointmentSameNumber_1.setStartWeek(timetableAppointmentSameNumber.getStartWeek());
                    timetableAppointmentSameNumber_1.setEndWeek(timetableAppointmentSameNumber.getEndWeek());
                    timetableWeeks.add(timetableAppointmentSameNumber_1);
                } else {
                    TimetableAppointmentSameNumber timetableAppointmentSameNumber_1 = new TimetableAppointmentSameNumber();
                    timetableAppointmentSameNumber_1.setStartWeek(timetableAppointmentSameNumber.getStartWeek());
                    timetableAppointmentSameNumber_1.setEndWeek(timetableAppointmentSameNumber.getEndWeek());
                    timetableWeeks.add(timetableAppointmentSameNumber_1);
                    TimetableAppointmentSameNumber timetableAppointmentSameNumber_2 = new TimetableAppointmentSameNumber();
                    timetableAppointmentSameNumber_2.setStartWeek(weeks[i]);
                    timetableAppointmentSameNumber_2.setEndWeek(weeks[i]);
                    timetableWeeks.add(timetableAppointmentSameNumber_2);
                }
            }

        }
        //计算不连续排课日期的保存问题
        //解决思路：
        //1：对节次数组进行排序，周次数据进行排序；
        //2：节次数组和周次数组进行二次循环；
        //3：对数组内连续周和节保存到timetableAppointmentSameNumber，直到循环结束
        for (TimetableAppointmentSameNumber sameNumberClass : timetableClasses) {
            for (TimetableAppointmentSameNumber sameNumberWeek : timetableWeeks) {
                TimetableAppointmentSameNumber timetableSave = new TimetableAppointmentSameNumber();
                timetableSave.setTimetableAppointment(timetableAppointmentDAO.findTimetableAppointmentById(timetableParamVO.getTimetableId()));
                timetableSave.setStartClass(sameNumberClass.getStartClass());
                timetableSave.setEndClass(sameNumberClass.getEndClass());
                timetableSave.setStartWeek(sameNumberWeek.getStartWeek());
                timetableSave.setEndWeek(sameNumberWeek.getEndWeek());
                timetableAppointmentSameNumberDAO.store(timetableSave);
            }
        }
        return true;
    }

    /*************************************************************************************
     * Description:教务课程-保存调整排课
     *
     * @author： 魏誠
     * @date：2018-08-03
     *************************************************************************************/
    @Transactional
    public boolean saveTimetableLabRooms(TimetableParamVO timetableParamVO) {
        // 设置预约编号
        // String appointNo = String.valueOf(appoints);
        // 获取选择的实验室列表
        List<LabRoom> matchLabs = new ArrayList<LabRoom>();
        //
        //对排课预约选定的实验分室进行保存
        //
        TimetableLabRelated timetableLabRelated = new TimetableLabRelated();
        if (timetableParamVO.getLabRoomIds() != null && timetableParamVO.getLabRoomIds().length > 0) {
            for (int labRoomId : timetableParamVO.getLabRoomIds()) {
                LabRoom labRoom = labRoomDAO.findLabRoomById(labRoomId);
                matchLabs.add(labRoom);
                timetableLabRelated.setLabRoom(labRoom);
                timetableLabRelated.setTimetableAppointment(timetableAppointmentDAO.findTimetableAppointmentById(timetableParamVO.getTimetableId()));
                timetableLabRelatedDAO.store(timetableLabRelated);
                timetableLabRelatedDAO.flush();
            }
        }
        return true;
    }

    /*************************************************************************************
     * Description:教务课程-保存调整排课
     *
     * @author： 魏誠
     * @date：2018-08-03
     *************************************************************************************/
    @Transactional
    public boolean saveTimetableTeachers(TimetableParamVO timetableParamVO) {
        //
        // 对排课预约选定的授课老师进行保存
        //
        TimetableTeacherRelated timetableTeacherRelated = new TimetableTeacherRelated();
        if (timetableParamVO.getTearchs() != null && timetableParamVO.getTearchs().length > 0) {
            for (String teacher : timetableParamVO.getTearchs()) {
                timetableTeacherRelated.setUser(userDAO.findUserByUsername(teacher));
                timetableTeacherRelated.setTimetableAppointment(timetableAppointmentDAO.findTimetableAppointmentById(timetableParamVO.getTimetableId()));
                timetableTeacherRelatedDAO.store(timetableTeacherRelated);
            }
        }
        return true;
    }

    /*************************************************************************************
     * Description:教务课程-保存调整排课
     *
     * @author： 魏誠
     * @date：2018-08-03
     *************************************************************************************/
    @Transactional
    public boolean saveTimetableTutors(TimetableParamVO timetableParamVO) {
        //
        // 对排课预约选定的指导老师进行保存
        //
        TimetableTutorRelated timetableTutorRelated = new TimetableTutorRelated();
        // 获取选择的实验室列表
        List<User> matchTutors = new ArrayList<User>();
        // 如果matchLabs不为空时
        if (timetableParamVO.getTutors() != null && timetableParamVO.getTutors().length > 0) {
            for (String tutor : timetableParamVO.getTutors()) {
                timetableTutorRelated.setUser(userDAO.findUserByUsername(tutor));
                timetableTutorRelated.setTimetableAppointment(timetableAppointmentDAO.findTimetableAppointmentById(timetableParamVO.getTimetableId()));
                timetableTutorRelatedDAO.store(timetableTutorRelated);
            }
        }
        return true;
    }

    /*************************************************************************************
     * Description:教务课程-保存实验项目
     *
     * @author： 魏誠
     * @date：2018-08-03
     *************************************************************************************/
    @Transactional
    public boolean saveTimetableItems(TimetableParamVO timetableParamVO) {
        // 对排课预约选定的实验项目进行保存
        TimetableItemRelated timetableItemRelated = new TimetableItemRelated();
        // 获取选择的实验室列表
        for (int item : timetableParamVO.getItems()) {
            timetableItemRelated.setOperationItem(operationItemDAO.findOperationItemById(item));
            timetableItemRelated.setTimetableAppointment(timetableAppointmentDAO.findTimetableAppointmentById(timetableParamVO.getTimetableId()));
            timetableItemRelatedDAO.store(timetableItemRelated);
        }
        return true;
    }

    /*************************************************************************************
     * Description:排课管理-获取节次列表
     *
     * @author： 魏誠
     * @date：2018-09-06
     *************************************************************************************/
    @Transactional
    public boolean publicTimetable(TimetableParamVO timetableParamVO) {
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        String businessAppUid = "";
        Integer type = 0;
        List<TimetableAppointment> timetableAppointments = new ArrayList<>();
        //如果是教务排课，则
        if (timetableParamVO.getTimetableStyle() == ConstantInterface.TIMETABLE_STYLE_DIRECT_COURSE ||
                timetableParamVO.getTimetableStyle() == ConstantInterface.TIMETABLE_STYLE_ADJUST_COURSE ||
                timetableParamVO.getTimetableStyle() == ConstantInterface.TIMETABLE_STYLE_SECOND_NOGROUP_COURSE ||
                timetableParamVO.getTimetableStyle() == ConstantInterface.TIMETABLE_STYLE_SECOND_GROUP_COURSE) {
            StringBuffer hql = new StringBuffer("select c from TimetableAppointment c where c.schoolCourse.courseNo like'" + timetableParamVO.getCourseNo() + "'");
            timetableAppointments = timetableAppointmentDAO.executeQuery(hql.toString(), 0, -1);
            //将所有关于该教学班下的排课发布
            for (TimetableAppointment timetableAppointment : timetableAppointments) {
                timetableAppointment.setStatus(timetableParamVO.getStatus());
                timetableAppointmentDAO.store(timetableAppointment);
            }
            businessAppUid = timetableParamVO.getCourseNo();
            type = 1;
            //独立非教务排课
        } else if (timetableParamVO.getTimetableStyle() == ConstantInterface.TIMETABLE_STYLE_INDEPENDENT_No_GOURP ||
                timetableParamVO.getTimetableStyle() == ConstantInterface.TIMETABLE_STYLE_INDEPENDENT_GROUP) {
            StringBuffer hql = new StringBuffer("select c from TimetableAppointment c where c.timetableSelfCourse.id =" + timetableParamVO.getSelfId());
            timetableAppointments = timetableAppointmentDAO.executeQuery(hql.toString(), 0, -1);
            for (TimetableAppointment timetableAppointment : timetableAppointments) {
                timetableAppointment.setStatus(timetableParamVO.getStatus());
                timetableAppointmentDAO.store(timetableAppointment);
            }
            businessAppUid = String.valueOf(timetableParamVO.getSelfId());
            type = 2;
        }
        if (timetableParamVO.getStatus() == 2) {
            Map<String, String> params = new HashMap<>();
            //默认教务排课，type=1
            String businessType = "TimetableAudit";
            if(type == 2){//自主排课，type=2
                businessType = "SelfTimetableAudit";
            }
            params.put("businessUid", "-1");
            params.put("businessType", pConfigDTO.PROJECT_NAME + businessType);
            params.put("businessAppUid", businessAppUid);
            String s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/saveInitBusinessAuditStatus", params);
            JSONObject jsonObject = JSON.parseObject(s);
            String status = jsonObject.getString("status");
            if (!"success".equals(status)) {
                for (TimetableAppointment timetableAppointment : timetableAppointments) {
                    timetableAppointment.setStatus(10);
                    timetableAppointmentDAO.store(timetableAppointment);
                }
                return false;
            }
            Map<String, String> curParams = new HashMap<>();
            curParams.put("businessType", pConfigDTO.PROJECT_NAME + businessType);
            curParams.put("businessAppUid", businessAppUid);
            String curStr = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/getCurrAuditStage", curParams);
            JSONObject curJSONObject = JSON.parseObject(curStr);
            String curStatus = curJSONObject.getString("status");
            JSONArray curJSONArray = curJSONObject.getJSONArray("data");
            JSONObject curJSONObject0 = curJSONArray.getJSONObject(0);
            String firstAuthName = curJSONObject0.getString("result");
            User user = shareService.getUserDetail();
            Integer termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
            //消息
            Message message = new Message();
            message.setSendUser(user.getCname());
            message.setSendCparty(user.getSchoolAcademy().getAcademyName());
            message.setCond(0);
            message.setTitle("排课预约增加");
            String content = "<a onclick='changeMessage(this)' href='../lims/timetable/engineer/educourse/auditTimetable?termId=" + termId.toString() + "&courseNo=" + businessAppUid + "&type=" + type + "'>审核</a>";
            message.setContent(content);
            message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
            message.setCreateTime(Calendar.getInstance());
            message.setTage(2);
            Set<User> isAuditUser = new HashSet<>();
            switch (firstAuthName) {
                case "pass":
                    break;
                case "fail":
                    break;
                case "LABMANAGER":
                    if (type == 1) {
                        SchoolCourse course = schoolCourseDAO.findSchoolCourseByPrimaryKey(businessAppUid);
                        for (TimetableAppointment timetableAppointment : course.getTimetableAppointments()) {
                            for (TimetableLabRelated timetableLabRelated : timetableAppointment.getTimetableLabRelateds()) {
                                for (LabRoomAdmin labRoomAdmin : timetableLabRelated.getLabRoom().getLabRoomAdmins()) {
                                    isAuditUser.add(labRoomAdmin.getUser());
                                }
                            }
                        }
                    } else if (type == 2) {
                        TimetableSelfCourse course = timetableSelfCourseDAO.findTimetableSelfCourseById(Integer.parseInt(businessAppUid));
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
        }


        return true;
    }

    /*************************************************************************************
     * Description:排课管理-输入判冲周次，返回可用的周次
     *
     * @author： 魏誠
     * @date：2018-09-15
     *************************************************************************************/
    public List<Integer> getTimetableValidWeeksList(TimetableParamVO timetableParamVO) {
        List<Integer> inValidlist = new ArrayList<Integer>();
        //冲突的排课情况数据获取
        if (Objects.nonNull(timetableParamVO.getLabRoomIds()) && Objects.nonNull(timetableParamVO.getClasses())) {
            for (int classes : timetableParamVO.getClasses()) {
                StringBuffer buffer = new StringBuffer("select c from TimetableAppointment c left join c.timetableAppointmentSameNumbers same " +
                        "left join c.timetableLabRelateds lab ");
                buffer.append(" where c.weekday=" + timetableParamVO.getWeekday() + " and");
                buffer.append(" (c.timetableStyle=1 or c.timetableStyle=2 or c.timetableStyle=3 or c.timetableStyle=4 or c.timetableStyle=5 or c.timetableStyle=6 or c.timetableStyle=6 or c.timetableStyle=7 or c.timetableStyle=8 ) and ");
                buffer.append(" c.schoolTerm=" + timetableParamVO.getTerm() + " and (1=2 ");
                //判断实验室
                for (Integer labRoom : timetableParamVO.getLabRoomIds()) {
                    buffer.append(" or lab.labRoom.id=" + labRoom);
                }
                buffer.append(")");
                for (int week : timetableParamVO.getWeeks()) {
                    //判断sameNumber子表是否时间冲突
                    StringBuffer sqlSubWhere = new StringBuffer(" and (" + classes + " BETWEEN  same.startClass and same.endClass ");
                    sqlSubWhere.append(" and " + week + " BETWEEN  same.startWeek and same.endWeek )");
                    List<TimetableAppointment> appointmentSames = timetableAppointmentDAO.executeQuery(buffer.toString() + sqlSubWhere.toString());
                    //判断appoint主表是否时间冲突
                    StringBuffer sqlWhere = new StringBuffer(" and same is null ");
                    sqlWhere = sqlWhere.append(" and (" + classes + " BETWEEN  c.startClass and c.endClass ");
                    sqlWhere.append(" and " + week + " BETWEEN  c.startWeek and c.endWeek )");
                    List<TimetableAppointment> appointments = timetableAppointmentDAO.executeQuery(buffer.toString() + sqlWhere.toString());
                    //如果冲突，或数组中存在该值，则添加
                    if (appointments.size() > 0 || appointmentSames.size() > 0) {
                        inValidlist.add(week);
                    }
                }
            }
        }
        //差分冲突数据
        List<Integer> allWeeks = Arrays.asList(ArrayUtils.toObject(timetableParamVO.getWeeks()));
        allWeeks = new ArrayList<Integer>(allWeeks);
        allWeeks.removeAll(inValidlist);
        return allWeeks;
    }

    /*************************************************************************************
     * Description:排课管理-输入判冲实验室，返回可用的周次
     *
     * @author： 魏誠
     * @date：2018-10-31
     *************************************************************************************/
    public List<Integer> getTimetableValidLabRoomsList(TimetableParamVO timetableParamVO) {
        List<Integer> inValidlist = new ArrayList<Integer>();
        //冲突的排课情况数据获取
        if (Objects.nonNull(timetableParamVO.getWeeks()) && Objects.nonNull(timetableParamVO.getClasses())) {
            for (int classes : timetableParamVO.getClasses()) {
                StringBuffer buffer = new StringBuffer("select c from TimetableAppointment c left join c.timetableAppointmentSameNumbers same " +
                        "left join c.timetableLabRelateds lab ");
                buffer.append(" where c.weekday=" + timetableParamVO.getWeekday() + " and");
                buffer.append(" (c.timetableStyle=1 or c.timetableStyle=2 or c.timetableStyle=3 or c.timetableStyle=4  or c.timetableStyle=5 or c.timetableStyle=6 or c.timetableStyle=7 or c.timetableStyle=8  ) and ");
                buffer.append(" c.schoolTerm=" + timetableParamVO.getTerm() + " and (1=2  ");
                for (int week : timetableParamVO.getWeeks()) {
                    //判断sameNumber子表是否时间冲突
                    buffer.append(" or (" + classes + " BETWEEN  same.startClass and same.endClass ");
                    buffer.append(" and " + week + " BETWEEN  same.startWeek and same.endWeek )");
                }
                buffer.append(")");
                //判断实验室
                for (Integer labRoom : timetableParamVO.getLabRoomIds()) {
                    StringBuffer sqlSubWhere = new StringBuffer(" and lab.labRoom.id=" + labRoom);
                    List<TimetableAppointment> appointmentSames = timetableAppointmentDAO.executeQuery(buffer.toString() + sqlSubWhere.toString());
                    //如果冲突，或数组中存在该值，则添加
                    if (appointmentSames.size() > 0) {
                        inValidlist.add(labRoom);
                    }
                }
            }
        }
        //差分冲突数据
        List<Integer> allLabRoomIds = Arrays.asList(ArrayUtils.toObject(timetableParamVO.getLabRoomIds()));
        allLabRoomIds = new ArrayList<Integer>(allLabRoomIds);
        allLabRoomIds.removeAll(inValidlist);
        return allLabRoomIds;
    }

    /*************************************************************************************
     * Description:排课管理-输入判冲实验室，返回可用的周次
     *
     * @author： 魏誠
     * @date：2018-10-31
     *************************************************************************************/
    public List<Integer> getTimetableInValidLabRoomsList(TimetableParamVO timetableParamVO) {
        List<Integer> inValidlist = new ArrayList<Integer>();
        //冲突的排课情况数据获取
        if (Objects.nonNull(timetableParamVO.getWeeks()) && Objects.nonNull(timetableParamVO.getClasses())) {
            for (int classes : timetableParamVO.getClasses()) {
                StringBuffer buffer = new StringBuffer("select c from TimetableAppointment c left join c.timetableAppointmentSameNumbers same " +
                        "left join c.timetableLabRelateds lab ");
                buffer.append(" where c.weekday=" + timetableParamVO.getWeekday() + " and");
                buffer.append(" (c.timetableStyle=1 or c.timetableStyle=2 or c.timetableStyle=3 or c.timetableStyle=4  or c.timetableStyle=5 or c.timetableStyle=6 or c.timetableStyle=7 or c.timetableStyle=8  ) and ");
                buffer.append(" c.schoolTerm=" + timetableParamVO.getTerm() + " and (1=2  ");
                for (int week : timetableParamVO.getWeeks()) {
                    //判断sameNumber子表是否时间冲突
                    buffer.append(" or (" + classes + " BETWEEN  same.startClass and same.endClass ");
                    buffer.append(" and " + week + " BETWEEN  same.startWeek and same.endWeek )");
                }
                buffer.append(")");
                //判断实验室
                for (Integer labRoom : timetableParamVO.getLabRoomIds()) {
                    StringBuffer sqlSubWhere = new StringBuffer(" and lab.labRoom.id=" + labRoom);
                    List<TimetableAppointment> appointmentSames = timetableAppointmentDAO.executeQuery(buffer.toString() + sqlSubWhere.toString());
                    //如果冲突，或数组中存在该值，则添加
                    if (appointmentSames.size() > 0) {
                        inValidlist.add(labRoom);
                    }
                }
            }
        }
        return inValidlist;
    }

    /**
     * Description 更新排课状态值
     * @param param 审核服务的返回值（""/fail/pass/下一级审核权限）
     * @param course_no 课程编号
     * @return
     * @author 陈乐为 2019-1-9
     */
    @Override
    public String updateTimetableStatus(String param, String course_no) {
        /**
         * 1.判断param值，
         * 	如果为fail则为审核拒绝，流程结束，status=4，同时给排课人发送消息
         * 	如果为pass则审核通过
         * 2.审核通过后，判断是否为最后一级审核
         * 	如果是，审核通过流程完毕，status=3，同时给排课人发送消息
         * 	如果不是，审核中，status=5，同时给下一级审核人发送消息
         */
        SchoolCourse course = schoolCourseDAO.findSchoolCourseByPrimaryKey(course_no);
        if(course!=null) {// 教务课程
            if("fail".equals(param)) {// 审核拒绝，流程结束
                for (TimetableAppointment ta : course.getTimetableAppointments()) {
                    ta.setStatus(ConstantInterface.TIMETABLE_STATUS_AUDIT_REFUSE);
                    timetableAppointmentDAO.store(ta);
                }
            }else if ("pass".equals(param)) {// 最后一级审核通过，流程结束
                for (TimetableAppointment ta : course.getTimetableAppointments()) {
                    ta.setStatus(ConstantInterface.TIMETABLE_STATUS_AUDIT_ACCESS);
                    timetableAppointmentDAO.store(ta);
                }
            }else {// 审核通过，下一级待审核
                for (TimetableAppointment ta : course.getTimetableAppointments()) {
                    ta.setStatus(ConstantInterface.TIMETABLE_STATUS_AUDIT_ONGOING);
                    timetableAppointmentDAO.store(ta);
                }
            }
        }else {// 自主课程
            TimetableSelfCourse selfCourse = timetableSelfCourseDAO.findTimetableSelfCourseById(Integer.parseInt(course_no));
            if("fail".equals(param)) {// 审核拒绝，流程结束
                for (TimetableAppointment ta : selfCourse.getTimetableAppointments()) {
                    ta.setStatus(ConstantInterface.TIMETABLE_STATUS_AUDIT_REFUSE);
                    timetableAppointmentDAO.store(ta);
                }
            }else if ("pass".equals(param)) {// 最后一级审核通过，流程结束
                for (TimetableAppointment ta : selfCourse.getTimetableAppointments()) {
                    ta.setStatus(ConstantInterface.TIMETABLE_STATUS_AUDIT_ACCESS);
                    timetableAppointmentDAO.store(ta);
                }
            }else {// 审核通过，下一级待审核
                for (TimetableAppointment ta : selfCourse.getTimetableAppointments()) {
                    ta.setStatus(ConstantInterface.TIMETABLE_STATUS_AUDIT_ONGOING);
                    timetableAppointmentDAO.store(ta);
                }
            }
        }

        return "success";
    }

    /**
     * Description 获取可用（实验室，星期，节次，周次）列表
     * @param labRoomId 实验室id
     * @param weekday 星期
     * @param classes 节次
     * @param weeks 周次
     * @return 可用列表
     * @author 黄保钱 2019-1-16
     */
    @Override
    public SelectDTO GetUsableList(String labRoomId, String weekday, String classes, String weeks, Integer type, String academyNumber, Integer term, String soft,String search) {
        StringBuilder sb = new StringBuilder("select distinct ");
        // 判冲获取实验室
        if(type == 0) {
            sb.append(" l.id, l.lab_room_name from lab_room l " +
                    " join lab_center lc on l.lab_center_id = lc.id " +
                    " left join software_room_related soft on soft.lab_room = l.id where 1=1");
            // 实验室学院筛选
            if(academyNumber != null && !"".equals(academyNumber)) {
                sb.append(" and lc.academy_number = '").append(academyNumber).append("'");
            }
            // 实验室软件筛选
            if(soft != null && !"".equals(soft)){
                sb.append(" and soft.software in (").append(soft).append(")");
            }
            // 实验室输入筛选
            if(search != null && !"".equals(search)){
                sb.append(" and l.lab_room_name like '%").append(search).append("%'");
            }
            // 判断是否不在有课的id内
            sb.append(" and l.id not in (select distinct l.id ");
        }
        // 判冲获取星期
        if(type == 1){
            sb.append(" sw.weekday, sw.weekday from school_week sw where 1=1");
            // 输入筛选星期
            if(search != null && !"".equals(search)){
                sb.append(" and sw.weekday like '%").append(search).append("%'");
            }
            // 判断是否不在有课的星期内
            sb.append(" and sw.weekday not in (select distinct sw.weekday ");
        }
        // 判冲获取节次
        if(type == 2){
            sb.append(" st.section, st.section from system_time st where 1=1");
            // 输入筛选节次
            if(search != null && !"".equals(search)){
                sb.append(" and st.section like '%").append(search).append("%'");
            }
            // 判断是否不在有课的节次内
            sb.append(" and st.section not in (select distinct st.section ");
        }
        // 判冲获取周次
        if(type == 3){
            sb.append(" sw.week, sw.week from school_week sw where 1=1");
            // 输入筛选周次
            if(search != null && !"".equals(search)){
                sb.append(" and sw.week like '%").append(search).append("%'");
            }
            // 判断是否不在有课的周次内
            sb.append(" and sw.week not in (select distinct sw.week ");
        }
        // 获取是否有课的sql
        sb.append(" from lab_room l " +
                " left join timetable_lab_related tlr on l.id = tlr.lab_id " +
                " left join timetable_appointment ta on ta.id = tlr.appointment_id " +
                " left join timetable_appointment_same_number tasn on tasn.appointment_id = ta.id " +
                " join system_time st on st.section between tasn.start_class and tasn.end_class " +
                " join school_week sw on sw.week between tasn.start_week and tasn.end_week and sw.weekday = ta.weekday ");
        SelectDTO selectDTO = new SelectDTO();
        List<JsonValueDTO> jsonValueDTOS = new ArrayList<>();
        sb.append(" where 1=1 ");
        // 学期
        sb.append(" and ta.term = ").append(term);
        // 实验室
        if(labRoomId != null && !"".equals(labRoomId) && type != 0){
            // 实验室筛选
            sb.append(" and l.id in (").append(labRoomId).append(")");
        }else if(type != 0){
            // 页面未选实验室，则无课
            sb.append(" and 1=0 ");
        }
        // 星期
        if(weekday != null && !"".equals(weekday) && type != 1){
            // 星期筛选
            sb.append(" and ta.weekday in (").append(weekday).append(")");
        }else if(type != 1){
            // 页面未选星期，则无课
            sb.append(" and 1=0 ");
        }
        // 节次
        if(classes != null && !"".equals(classes) && type != 2){
            // 节次筛选
            sb.append(" and st.section in (").append(classes).append(")");
        }else if(type != 2){
            // 页面未选节次，则无课
            sb.append(" and 1=0 ");
        }
        // 周次
        if(weeks != null && !"".equals(weeks) && type != 3){
            // 周次筛选
            sb.append(" and sw.week in (").append(weeks).append(")");
        }else if(type != 3){
            // 页面未选周次，则无课
            sb.append(" and 1=0 ");
        }
        sb.append(")");
        Query query = entityManager.createNativeQuery(sb.toString());
        // 获取结果集合
        List<Object[]> list = query.getResultList();
        // 转换成页面可识别形式
        for(Object[] o:list){
            JsonValueDTO jsonValueDTO = new JsonValueDTO();
            if(o[0] != null) {
                jsonValueDTO.setId(o[0].toString());
            }
            if(o[1] != null) {
                jsonValueDTO.setText(o[1].toString());
            }
            jsonValueDTOS.add(jsonValueDTO);
        }
        selectDTO.setResults(jsonValueDTOS);
        return selectDTO;
    }
}