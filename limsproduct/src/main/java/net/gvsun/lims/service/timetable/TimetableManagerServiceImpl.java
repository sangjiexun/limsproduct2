package net.gvsun.lims.service.timetable;

import api.net.gvsunlims.constant.ConstantInterface;
import net.gvsun.lims.dto.common.BaseActionAuthDTO;
import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.timetable.TimetableBatchDTO;
import net.gvsun.lims.dto.timetable.TimetableDTO;
import net.gvsun.lims.dto.timetable.TimetableGroupDTO;
import net.gvsun.lims.dto.timetable.TimetableGroupStudentDTO;
import net.gvsun.lims.dto.user.UserDTO;
import net.gvsun.lims.service.user.UserActionService;
import net.gvsun.lims.vo.timtable.engineer.TimetableBatchListVO;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("timetableManagerService")
public class TimetableManagerServiceImpl implements TimetableManagerService {
    @Autowired
    private HttpServletRequest req;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private TimetableAppointmentDAO timetableAppointmentDAO;
    @Autowired
    private TimetableItemRelatedDAO timetableItemRelatedDAO;
    @Autowired
    private TimetableGroupDAO timetableGroupDAO;
    @Autowired
    private TimetableBatchDAO timetableBatchDAO;
    @Autowired
    private TimetableAppointmentSameNumberDAO timetableAppointmentSameNumberDAO;
    @Autowired
    private ShareService shareService;
    @Autowired
    private SchoolCourseDAO schoolCourseDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserActionService userActionService;
    @Autowired
    private TimetableTutorRelatedDAO timetableTutorRelatedDAO;
    @Autowired
    private TimetableLabRelatedDAO timetableLabRelatedDAO;
    @Autowired
    private TimetableTeacherRelatedDAO timetableTeacherRelatedDAO;
    @Autowired
    private TimetableGroupStudentsDAO timetableGroupStudentsDAO;

    @Autowired
    private TimetableCommonService timetableCommonService;
    @Autowired
    private TimetableSelfCourseDAO timetableSelfCourseDAO;

    /*************************************************************************************
     * Description:排课管理-获取批次
     *
     * @author： 魏誠
     * @date：2018-08-03
     *************************************************************************************/
    public BaseDTO apiTimetableBatchList(String courseNo, String sort, String order) {
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select c from TimetableBatch c where  1=1 ");
        //查询条件
        // 教务、自主分批排课共用这个方法，通过课程区分
        SchoolCourse schoolCourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo);
        if(schoolCourse!=null) {
            sql.append(" and (c.courseNo like '" + courseNo + "')");
        }else if (courseNo != null && !"".equals(courseNo)) {
            sql.append(" and (c.selfId = " + courseNo + ")");
        }else{
            sql.append(" and 1=2 ");
        }
        // 执行sb语句
        List<TimetableBatch> timetableBatchs = timetableBatchDAO.executeQuery(sql.toString(), 0, -1);
        int total = timetableBatchs.size();
        //封装VO
        List<TimetableBatchListVO> timetableBatchListVOs = new ArrayList<TimetableBatchListVO>();
        for (TimetableBatch timetableBatch : timetableBatchs) {
            TimetableBatchListVO timetableBatchListVO = new TimetableBatchListVO();
            timetableBatchListVO.setId(timetableBatch.getId());
            //设置courseCode
            timetableBatchListVO.setCourseNo(timetableBatch.getCourseNo());
            String sqlBatch = "select c from TimetableBatch c inner join c.timetableGroups groups inner join groups.timetableGroupStudentses students ";
            sqlBatch += " where c.id=" + timetableBatch.getId() + " and students.user.username like '" + shareService.getUser().getUsername() + "'";
            if (timetableBatchDAO.executeQuery(sqlBatch).size() > 0) {
                //批次已选择
                timetableBatchListVO.setSelected(ConstantInterface.TIMETABLE_BATCH_IS_SELECT);
            } else {
            }
            //设置课程名称
            timetableBatchListVO.setBatchName(timetableBatch.getBatchName());
            BaseActionAuthDTO baseActionAuthDTO = userActionService.getBaseActionAuthDTO(ConstantInterface.FUNCTION_MODEL_ACTION_TIMETABLE, req.getSession().getAttribute("selected_role").toString(), shareService.getUser().getUsername());
            timetableBatchListVO.setBaseActionAuthDTO(baseActionAuthDTO);
            //设置课程编号
            timetableBatchListVO.setCountGroup(timetableBatch.getCountGroup());
            if (timetableBatch.getFlag() != null) {
                timetableBatchListVO.setFlag(timetableBatch.getFlag());
            }
            timetableBatchListVO.setStartDate(timetableBatch.getStartDate().getTime());
            timetableBatchListVO.setEndDate(timetableBatch.getEndDate().getTime());
            // 设置选课方式
            timetableBatchListVO.setIfselect(timetableBatch.getIfselect());
            List<TimetableGroupDTO> timetableGroupDTOs = new ArrayList<TimetableGroupDTO>();
            for (TimetableGroup timetableGroup : timetableBatch.getTimetableGroups()) {
                TimetableGroupDTO timetableGroupDTO = new TimetableGroupDTO();
                timetableGroupDTO.setBatchId(timetableGroup.getId());
                timetableGroupDTO.setGroupName(timetableGroup.getGroupName());
                String timetable = "";
                for (TimetableAppointment timetableAppointment : timetableGroup.getTimetableAppointments()) {
                    if (timetableAppointment.getTimetableAppointmentSameNumbers().size() == 0) {
                        timetable += "星期" + timetableAppointment.getWeekday() + " [" + timetableAppointment.getStartClass() + "-" +
                                timetableAppointment.getEndClass() + "]节;" + " [" + timetableAppointment.getStartWeek() + "-" +
                                timetableAppointment.getEndWeek() + "]周";
                    } else {
                        for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : timetableAppointment.getTimetableAppointmentSameNumbers()) {
                            timetable += "星期" + timetableAppointment.getWeekday() + " [" + timetableAppointmentSameNumber.getStartClass() + "-" +
                                    timetableAppointmentSameNumber.getEndClass() + "]节;" + " [" + timetableAppointmentSameNumber.getStartWeek() + "-" +
                                    timetableAppointmentSameNumber.getEndWeek() + "]周</br>";
                        }
                    }

                }
                timetableGroupDTO.setTimetable(timetable);
                timetableGroupDTOs.add(timetableGroupDTO);
            }
            timetableBatchListVOs.add(timetableBatchListVO);
        }
        BaseDTO baseVO = new BaseDTO();
        baseVO.setRows(timetableBatchListVOs);
        baseVO.setTotal(total);
        return baseVO;
    }

    /*************************************************************************************
     * Description:排课管理-获取批次
     *
     * @author： 魏誠
     * @date：2018-08-03
     *************************************************************************************/
    public BaseDTO apiSelfTimetableBatchList(Integer selfId, String sort, String order) {
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select c from TimetableBatch c where  1=1 ");
        //查询条件
        //如果查询为null则返回null
        if (selfId != null ) {
            sql.append(" and (c.selfId = " + selfId + ")");
        }else{
            sql.append(" and 1=2 ");
        }
        // 执行sb语句
        List<TimetableBatch> timetableBatchs = timetableBatchDAO.executeQuery(sql.toString(), 0, -1);
        int total = timetableBatchs.size();
        //封装VO
        List<TimetableBatchListVO> timetableBatchListVOs = new ArrayList<TimetableBatchListVO>();
        for (TimetableBatch timetableBatch : timetableBatchs) {
            TimetableBatchListVO timetableBatchListVO = new TimetableBatchListVO();
            timetableBatchListVO.setId(timetableBatch.getId());
            //设置courseCode
            timetableBatchListVO.setCourseNo(timetableBatch.getCourseNo());
            String sqlBatch = "select c from TimetableBatch c inner join c.timetableGroups groups inner join groups.timetableGroupStudentses students ";
            sqlBatch += " where c.id=" + timetableBatch.getId() + " and students.user.username like '" + shareService.getUser().getUsername() + "'";
            if (timetableBatchDAO.executeQuery(sqlBatch).size() > 0) {
                //批次已选择
                timetableBatchListVO.setSelected(ConstantInterface.TIMETABLE_BATCH_IS_SELECT);
            } else {
            }
            //设置课程名称
            timetableBatchListVO.setBatchName(timetableBatch.getBatchName());
            BaseActionAuthDTO baseActionAuthDTO = userActionService.getBaseActionAuthDTO(ConstantInterface.FUNCTION_MODEL_ACTION_TIMETABLE, req.getSession().getAttribute("selected_role").toString(), shareService.getUser().getUsername());
            timetableBatchListVO.setBaseActionAuthDTO(baseActionAuthDTO);
            timetableBatchListVO.setSelfId(timetableBatch.getSelfId());
            //设置课程编号
            timetableBatchListVO.setCountGroup(timetableBatch.getCountGroup());
            if (timetableBatch.getFlag() != null) {
                timetableBatchListVO.setFlag(timetableBatch.getFlag());
            }
            timetableBatchListVO.setStartDate(timetableBatch.getStartDate().getTime());
            timetableBatchListVO.setEndDate(timetableBatch.getEndDate().getTime());
            // 设置选课方式
            timetableBatchListVO.setIfselect(timetableBatch.getIfselect());
            List<TimetableGroupDTO> timetableGroupDTOs = new ArrayList<TimetableGroupDTO>();
            for (TimetableGroup timetableGroup : timetableBatch.getTimetableGroups()) {
                TimetableGroupDTO timetableGroupDTO = new TimetableGroupDTO();
                timetableGroupDTO.setBatchId(timetableGroup.getId());
                timetableGroupDTO.setGroupName(timetableGroup.getGroupName());
                String timetable = "";
                for (TimetableAppointment timetableAppointment : timetableGroup.getTimetableAppointments()) {
                    if (timetableAppointment.getTimetableAppointmentSameNumbers().size() == 0) {
                        timetable += "星期" + timetableAppointment.getWeekday() + " [" + timetableAppointment.getStartClass() + "-" +
                                timetableAppointment.getEndClass() + "]节;" + " [" + timetableAppointment.getStartWeek() + "-" +
                                timetableAppointment.getEndWeek() + "]周";
                    } else {
                        for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : timetableAppointment.getTimetableAppointmentSameNumbers()) {
                            timetable += "星期" + timetableAppointment.getWeekday() + " [" + timetableAppointmentSameNumber.getStartClass() + "-" +
                                    timetableAppointmentSameNumber.getEndClass() + "]节;" + " [" + timetableAppointmentSameNumber.getStartWeek() + "-" +
                                    timetableAppointmentSameNumber.getEndWeek() + "]周</br>";
                        }
                    }

                }
                timetableGroupDTO.setTimetable(timetable);
                timetableGroupDTOs.add(timetableGroupDTO);
            }
            timetableBatchListVOs.add(timetableBatchListVO);
        }
        BaseDTO baseVO = new BaseDTO();
        baseVO.setRows(timetableBatchListVOs);
        baseVO.setTotal(total);
        return baseVO;
    }


    /*************************************************************************************
     * Description:排课管理-获取批次
     *
     * @author： 魏誠
     * @date：2018-08-03
     *************************************************************************************/
    public BaseDTO apiTimetableGroupList(int batchId, String sort, String order) {
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select c from TimetableGroup c where  1=1 and c.timetableBatch.id=" + batchId);
        // 执行sb语句
        List<TimetableGroup> timetableGroups = timetableGroupDAO.executeQuery(sql.toString(), 0, -1);
        int total = timetableGroups.size();
        //封装VO
        List<TimetableGroupDTO> timetableGroupDTOs = new ArrayList<TimetableGroupDTO>();
        for (TimetableGroup timetableGroup : timetableGroups) {
            TimetableGroupDTO timetableGroupDTO = new TimetableGroupDTO();
            String sqlBatch = "select c from TimetableGroup c inner join c.timetableGroupStudentses students ";
            sqlBatch += " where c.id=" + timetableGroup.getId() + " and students.user.username like '" + shareService.getUser().getUsername() + "'";
            if (timetableGroupDAO.executeQuery(sqlBatch).size() > 0) {
                //批次已选择
                timetableGroupDTO.setSelected(ConstantInterface.TIMETABLE_BATCH_IS_SELECT);
            } else {
            }
            //id
            timetableGroupDTO.setId(timetableGroup.getId());
            timetableGroupDTO.setGroupNumber(timetableGroup.getNumbers());
            //设置课程名称
            timetableGroupDTO.setTimetableId(timetableGroup.getTimetableBatch().getId());
            //设置课程编号
            timetableGroupDTO.setGroupName(timetableGroup.getGroupName());
            timetableGroupDTO.setGroupStudentNumbers(timetableGroup.getTimetableGroupStudentses().size());
            BaseActionAuthDTO baseActionAuthDTO = userActionService.getBaseActionAuthDTO(ConstantInterface.FUNCTION_MODEL_ACTION_TIMETABLE, req.getSession().getAttribute("selected_role").toString(), shareService.getUser().getUsername());
            timetableGroupDTO.setBaseActionAuthDTO(baseActionAuthDTO);
            String timetable = "";
            List<TimetableDTO> timetableDTOs = timetableCommonService.apiTimetableDTOsByGroup(timetableGroup.getId());
            timetableGroupDTO.setTimetables(timetableDTOs);
            for(TimetableDTO t:timetableDTOs){
                timetable += t.getTimetable();
            }
            timetableGroupDTO.setTimetable(timetable);
            timetableGroupDTOs.add(timetableGroupDTO);
        }
        BaseDTO baseVO = new BaseDTO();
        baseVO.setRows(timetableGroupDTOs);
        baseVO.setTotal(total);
        return baseVO;
    }

    /*************************************************************************************
     * Description:排课管理-保存分批排课
     *
     * @author： 魏誠
     * @date：2018-09-19
     *************************************************************************************/
    @Transactional
    public boolean saveTimetableBatch(TimetableBatchDTO timetableBatchDTO) {
        //
        // 保存批次
        //
        TimetableBatch timetableBatch = new TimetableBatch();
        timetableBatch.setBatchName(timetableBatchDTO.getBatchName());
        timetableBatch.setCountGroup(timetableBatchDTO.getCountGroup());
        timetableBatch.setCourseNo(timetableBatchDTO.getCourseNo());
        if (timetableBatchDTO.getSelfId() > 0) {
            timetableBatch.setSelfId(timetableBatchDTO.getSelfId());
        }
        timetableBatch.setFlag(timetableBatchDTO.getFlag());
        Calendar start = Calendar.getInstance();
        start.setTime(timetableBatchDTO.getStartDate());
        Calendar end = Calendar.getInstance();
        end.setTime(timetableBatchDTO.getEndDate());
        timetableBatch.setStartDate(start);
        timetableBatch.setEndDate(end);
        timetableBatch.setSelfId(timetableBatchDTO.getSelfId());
        timetableBatch.setIfselect(timetableBatchDTO.getIfselect());
        timetableBatch = timetableBatchDAO.store(timetableBatch);
        //保存分组
        for (int i = 1; i <= timetableBatchDTO.getCountGroup(); i++) {
            TimetableGroup timetableGroup = new TimetableGroup();
            timetableGroup.setGroupName("第" + i + "组");
            timetableGroup.setNumbers(timetableBatchDTO.getNumbers());
            timetableGroup.setTimetableBatch(timetableBatch);
            timetableGroup = timetableGroupDAO.store(timetableGroup);
        }
        return true;
    }

    /*************************************************************************************
     * Description:排课管理-保存学生选择分组
     *
     * @author： 魏誠
     * @date：2018-09-26
     *************************************************************************************/
    @Transactional
    public boolean apiSelectBatchGroup(int group) {
        //获取分组对象
        TimetableGroup timetableGroup = timetableGroupDAO.findTimetableGroupById(group);
        //获取分组学生名单
        Set<TimetableGroupStudents> timetableGroupStudentses = timetableGroup.getTimetableGroupStudentses();
        String sql = "select c from TimetableGroupStudents c where c.timetableGroup.id=" + group + " and c.user.username like '" + shareService.getUser().getUsername() + "'";
        //增加分组
        if (timetableGroupStudentsDAO.executeQuery(sql).size() == 0) {
            TimetableGroupStudents timetableGroupStudents = new TimetableGroupStudents();
            timetableGroupStudents.setUser(shareService.getUser());
            timetableGroupStudents.setTimetableGroup(timetableGroup);
            timetableGroupStudents = timetableGroupStudentsDAO.store(timetableGroupStudents);
            timetableGroupStudentses.add(timetableGroupStudents);
            timetableGroup.setTimetableGroupStudentses(timetableGroupStudentses);
            //保存分组
            timetableGroupDAO.store(timetableGroup);
        }
        return true;
    }

    /*************************************************************************************
     * Description:排课管理-删除分批
     *
     * @author： 魏誠
     * @date：2018-09-19
     *************************************************************************************/
    @Transactional
    public boolean deleteTimetableBatch(int batchId) {
        //
        // 获取批次
        //
        List<TimetableGroup> timetableGroups = timetableGroupDAO.executeQuery("select c from TimetableGroup c where c.timetableBatch.id=" + batchId, 0, -1);
        //保存分组
        for (TimetableGroup timetableGroup : timetableGroups) {
            //删除相关分组学生
            for (TimetableGroupStudents timetableGroupStudents : timetableGroup.getTimetableGroupStudentses()) {
                timetableGroupStudentsDAO.remove(timetableGroupStudents);
            }
            timetableGroupDAO.remove(timetableGroup);
        }
        //删除批次
        timetableBatchDAO.remove(timetableBatchDAO.findTimetableBatchById(batchId));
        return true;
    }

    /*************************************************************************************
     * Description:排课管理-分批分组管理-修改分组的学生数量
     *
     * @author： 魏誠
     * @date：2018-10-10
     *************************************************************************************/
    @Transactional
    public int saveTimetableGroupNumbers(int groupId, int numbers) {
        //
        // 获取批次
        //
        TimetableGroup timetableGroup = timetableGroupDAO.findTimetableGroupById(groupId);
        //设置分组名单
        timetableGroup.setNumbers(numbers);
        //保存
        timetableGroup = timetableGroupDAO.merge(timetableGroup);
        return timetableGroup.getNumbers();
    }

    /*************************************************************************************
     * Description:排课管理-分批分组管理-根据分组获取选定分组的名单
     *
     * @author： 魏誠
     * @date：2018-10-10
     *************************************************************************************/
    @Transactional
    public TimetableGroupStudentDTO getTimetableGroupStudents(String courseNo, int groupId) {
        //目标：获取当前教学班的所有学生选课名单
        //将已加入当前分组的学生名单，进行标记
        //返回学生列表
        List<UserDTO> userDTOs = new ArrayList<UserDTO>();
        //获取分组信息
        TimetableGroup timetableGroup = timetableGroupDAO.findTimetableGroupById(groupId);
        TimetableGroupStudentDTO timetableGroupStudentDTO = new TimetableGroupStudentDTO();
        SchoolCourse schoolCourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo);
        if (Objects.nonNull(schoolCourse) && schoolCourse.getSchoolCourseDetails().size() > 0) {
            for (SchoolCourseStudent schoolCourseStudent : schoolCourse.getSchoolCourseDetails().iterator().next().getSchoolCourseStudents()) {
                UserDTO userDTO = new UserDTO();
                userDTO.setCname(schoolCourseStudent.getUserByStudentNumber().getCname());
                userDTO.setUsername(schoolCourseStudent.getUserByStudentNumber().getUsername());
                //默认该学生未在分组中分配
                userDTO.setSelected(0);
                userDTOs.add(userDTO);
                for (TimetableGroup timetableGroup_1 : timetableGroup.getTimetableBatch().getTimetableGroups()) {
                    //确定改学生是否选中
                    if(timetableGroup_1.getId()==timetableGroup.getId()){
                        for (TimetableGroupStudents timetableGroupStudent : timetableGroup.getTimetableGroupStudentses()) {
                            if (userDTO.getUsername().equals(timetableGroupStudent.getUser().getUsername())) {
                                //确定改学生是否选中，确定选中
                                userDTO.setSelected(1);
                            }
                        }
                    }else{
                        for (TimetableGroupStudents timetableGroupStudent : timetableGroup_1.getTimetableGroupStudentses()) {
                            if (userDTO.getUsername().equals(timetableGroupStudent.getUser().getUsername())) {
                                //确定改学生是否选中，确定选中
                                userDTO.setSelected(-1);
                            }
                        }
                    }

                }
            }
        }
        //设置返回
        timetableGroupStudentDTO.setUserDTOs(userDTOs);
        timetableGroupStudentDTO.setGroupId(groupId);
        timetableGroupStudentDTO.setGroupNumber(timetableGroup.getNumbers());
        return timetableGroupStudentDTO;
    }

    /*************************************************************************************
     * Description:排课管理-分批分组管理-根据分组获取选定分组的名单
     *
     * @author： 魏誠
     * @date：2018-10-10
     *************************************************************************************/
    @Transactional
    public TimetableGroupStudentDTO getSelfTimetableGroupStudents(int selfId, int groupId) {
        //目标：获取当前教学班的所有学生选课名单
        //将已加入当前分组的学生名单，进行标记
        //返回学生列表
        List<UserDTO> userDTOs = new ArrayList<UserDTO>();
        //获取分组信息
        TimetableGroup timetableGroup = timetableGroupDAO.findTimetableGroupById(groupId);
        TimetableGroupStudentDTO timetableGroupStudentDTO = new TimetableGroupStudentDTO();
        TimetableSelfCourse timetableSelfCourse = timetableSelfCourseDAO.findTimetableSelfCourseById(selfId);
        if (Objects.nonNull(timetableSelfCourse) && timetableSelfCourse.getTimetableCourseStudents().size() > 0) {
            for (TimetableCourseStudent timetableCourseStudent : timetableSelfCourse.getTimetableCourseStudents()) {
                UserDTO userDTO = new UserDTO();
                userDTO.setCname(timetableCourseStudent.getUser().getCname());
                userDTO.setUsername(timetableCourseStudent.getUser().getUsername());
                //默认该学生未在分组中分配
                userDTO.setSelected(0);
                userDTOs.add(userDTO);
                for (TimetableGroup timetableGroup_1 : timetableGroup.getTimetableBatch().getTimetableGroups()) {
                    //确定改学生是否选中
                    if(timetableGroup_1.getId()==timetableGroup.getId()){
                        for (TimetableGroupStudents timetableGroupStudent : timetableGroup.getTimetableGroupStudentses()) {
                            if (userDTO.getUsername().equals(timetableGroupStudent.getUser().getUsername())) {
                                //确定改学生是否选中，确定选中
                                userDTO.setSelected(1);
                            }
                        }
                    }else{
                        for (TimetableGroupStudents timetableGroupStudent : timetableGroup_1.getTimetableGroupStudentses()) {
                            if (userDTO.getUsername().equals(timetableGroupStudent.getUser().getUsername())) {
                                //确定改学生是否选中，确定选中
                                userDTO.setSelected(-1);
                            }
                        }
                    }

                }
            }
        }
        //设置返回
        timetableGroupStudentDTO.setUserDTOs(userDTOs);
        timetableGroupStudentDTO.setGroupId(groupId);
        timetableGroupStudentDTO.setGroupNumber(timetableGroup.getNumbers());
        return timetableGroupStudentDTO;
    }

    /*************************************************************************************
     * Description:排课管理-删除分组
     *
     * @author： 魏誠
     * @date：2018-09-19
     *************************************************************************************/
    @Transactional
    public boolean deleteTimetableGroup(int groupId) {
        //
        // 获取批次
        //
        TimetableGroup timetableGroup = timetableGroupDAO.findTimetableGroupById(groupId);
        //保存分组
        //删除相关分组学生
        for (TimetableGroupStudents timetableGroupStudents : timetableGroup.getTimetableGroupStudentses()) {
            timetableGroupStudentsDAO.remove(timetableGroupStudents);
        }
        for (TimetableAppointment timetableAppointment : timetableGroup.getTimetableAppointments()) {
            for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : timetableAppointment.getTimetableAppointmentSameNumbers()) {
                timetableAppointmentSameNumberDAO.remove(timetableAppointmentSameNumber);
            }
            timetableAppointmentDAO.remove(timetableAppointment);
        }
        timetableGroupDAO.remove(timetableGroup);
        return true;
    }

    /*************************************************************************************
     * Description:排课管理-删除教务类排课
     *
     * @author： 魏誠
     * @date：2018-09-19
     *************************************************************************************/
    @Transactional
    public boolean deleteTimetableByCourseNo(int term, String courseNo) {
        //
        // 删除实验室
        //
        String sqlTimetableLabRelated = "select c from TimetableLabRelated c where c.timetableAppointment.schoolCourse.courseNo like '" + courseNo + "' and c.timetableAppointment.schoolTerm.id =" + term;
        for (TimetableLabRelated timetableLabRelated : timetableLabRelatedDAO.executeQuery(sqlTimetableLabRelated, 0, -1)) {
            timetableLabRelatedDAO.remove(timetableLabRelated);
        }
        //
        // 删除授课教师
        //
        String sqlTimetableTeacherRelated = "select c from TimetableTeacherRelated c where c.timetableAppointment.schoolCourse.courseNo like '" + courseNo + "' and c.timetableAppointment.schoolTerm.id =" + term;
        for (TimetableTeacherRelated timetableTeacherRelated : timetableTeacherRelatedDAO.executeQuery(sqlTimetableTeacherRelated, 0, -1)) {
            timetableTeacherRelatedDAO.remove(timetableTeacherRelated);
        }
        //
        // 删除指导教师
        //
        String sqlTimetableTutorRelated = "select c from TimetableTutorRelated c where c.timetableAppointment.schoolCourse.courseNo like '" + courseNo + "' and c.timetableAppointment.schoolTerm.id =" + term;
        for (TimetableTutorRelated timetableTutorRelated : timetableTutorRelatedDAO.executeQuery(sqlTimetableTutorRelated, 0, -1)) {
            timetableTutorRelatedDAO.remove(timetableTutorRelated);
        }
        //
        // 删除实验项目
        //
        String sqlTimetableItemRelated = "select c from TimetableItemRelated c where c.timetableAppointment.schoolCourse.courseNo like '" + courseNo + "' and c.timetableAppointment.schoolTerm.id =" + term;
        for (TimetableItemRelated timetableItemRelated : timetableItemRelatedDAO.executeQuery(sqlTimetableItemRelated, 0, -1)) {
            timetableItemRelatedDAO.remove(timetableItemRelated);
        }
        //
        //删除相关分批分组
        //
        for (TimetableBatch timetableBatch : timetableBatchDAO.executeQuery("select c from TimetableBatch c where c.courseNo like '" + courseNo + "'")) {
            this.deleteTimetableBatch(timetableBatch.getId());
        }
        //
        //删除相关排课
        //
        String sqlTimetableAppointmentSameNumber = "select c from TimetableAppointmentSameNumber c where c.timetableAppointment.schoolCourse.courseNo like '" + courseNo + "' and c.timetableAppointment.schoolTerm.id =" + term;
        for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : timetableAppointmentSameNumberDAO.executeQuery(sqlTimetableAppointmentSameNumber, 0, -1)) {
            timetableAppointmentSameNumberDAO.remove(timetableAppointmentSameNumber);
        }
        String sqlTimetableAppointment = "select c from TimetableAppointment c where c.schoolCourse.courseNo like '" + courseNo + "' and c.schoolTerm.id =" + term;
        for (TimetableAppointment timetableAppointment : timetableAppointmentDAO.executeQuery(sqlTimetableAppointment, 0, -1)) {
            timetableAppointmentDAO.remove(timetableAppointment);
        }
        return true;

    }

    /*************************************************************************************
     * Description:排课管理-删除自主类排课
     *
     * @author： 魏誠
     * @date：2018-09-19
     *************************************************************************************/
    @Transactional
    public boolean deleteTimetableBySelfId(int term, Integer selfId) {
        //
        // 删除实验室
        //
        String sqlTimetableLabRelated = "select c from TimetableLabRelated c where c.timetableAppointment.timetableSelfCourse.id = " + selfId + " and c.timetableAppointment.schoolTerm.id =" + term;
        for (TimetableLabRelated timetableLabRelated : timetableLabRelatedDAO.executeQuery(sqlTimetableLabRelated, 0, -1)) {
            timetableLabRelatedDAO.remove(timetableLabRelated);
        }
        //
        // 删除授课教师
        //
        String sqlTimetableTeacherRelated = "select c from TimetableTeacherRelated c where c.timetableAppointment.timetableSelfCourse.id = " + selfId + " and c.timetableAppointment.schoolTerm.id =" + term;
        for (TimetableTeacherRelated timetableTeacherRelated : timetableTeacherRelatedDAO.executeQuery(sqlTimetableTeacherRelated, 0, -1)) {
            timetableTeacherRelatedDAO.remove(timetableTeacherRelated);
        }
        //
        // 删除指导教师
        //
        String sqlTimetableTutorRelated = "select c from TimetableTutorRelated c where c.timetableAppointment.timetableSelfCourse.id = " + selfId + " and c.timetableAppointment.schoolTerm.id =" + term;
        for (TimetableTutorRelated timetableTutorRelated : timetableTutorRelatedDAO.executeQuery(sqlTimetableTutorRelated, 0, -1)) {
            timetableTutorRelatedDAO.remove(timetableTutorRelated);
        }
        //
        // 删除实验项目
        //
        String sqlTimetableItemRelated = "select c from TimetableItemRelated c where c.timetableAppointment.timetableSelfCourse.id = " + selfId+ " and c.timetableAppointment.schoolTerm.id =" + term;
        for (TimetableItemRelated timetableItemRelated : timetableItemRelatedDAO.executeQuery(sqlTimetableItemRelated, 0, -1)) {
            timetableItemRelatedDAO.remove(timetableItemRelated);
        }
        //
        //删除相关分批分组
        //
        for (TimetableBatch timetableBatch : timetableBatchDAO.executeQuery("select c from TimetableBatch c where c.selfId = " + selfId )) {
            this.deleteTimetableBatch(timetableBatch.getId());
        }
        //
        //删除相关排课
        //
        String sqlTimetableAppointmentSameNumber = "select c from TimetableAppointmentSameNumber c where c.timetableAppointment.timetableSelfCourse.id = "+ selfId+ " and c.timetableAppointment.schoolTerm.id =" + term;
        for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : timetableAppointmentSameNumberDAO.executeQuery(sqlTimetableAppointmentSameNumber, 0, -1)) {
            timetableAppointmentSameNumberDAO.remove(timetableAppointmentSameNumber);
        }
        String sqlTimetableAppointment = "select c from TimetableAppointment c where c.timetableSelfCourse.id = "+ selfId+ " and c.schoolTerm.id =" + term;
        for (TimetableAppointment timetableAppointment : timetableAppointmentDAO.executeQuery(sqlTimetableAppointment, 0, -1)) {
            timetableAppointmentDAO.remove(timetableAppointment);
        }
        return true;

    }

    /*************************************************************************************
     * Description:排课管理-删除自主类排课
     *
     * @author： 魏誠
     * @date：2018-09-19
     *************************************************************************************/
    @Transactional
    public boolean deleteTimetableByByTimetableId(Integer id) {

        TimetableAppointment timetableAppointment = timetableAppointmentDAO.findTimetableAppointmentById(id);
        //
        // 删除实验室
        //
        String sqlTimetableLabRelated = "select c from TimetableLabRelated c where c.timetableAppointment.id = " + id ;
        for (TimetableLabRelated timetableLabRelated : timetableLabRelatedDAO.executeQuery(sqlTimetableLabRelated, 0, -1)) {
            timetableLabRelatedDAO.remove(timetableLabRelated);
        }
        //
        // 删除授课教师
        //
        String sqlTimetableTeacherRelated = "select c from TimetableTeacherRelated c where c.timetableAppointment.id = " + id ;
        for (TimetableTeacherRelated timetableTeacherRelated : timetableTeacherRelatedDAO.executeQuery(sqlTimetableTeacherRelated, 0, -1)) {
            timetableTeacherRelatedDAO.remove(timetableTeacherRelated);
        }
        //
        // 删除指导教师
        //
        String sqlTimetableTutorRelated = "select c from TimetableTutorRelated c where c.timetableAppointment.id = " + id ;
        for (TimetableTutorRelated timetableTutorRelated : timetableTutorRelatedDAO.executeQuery(sqlTimetableTutorRelated, 0, -1)) {
            timetableTutorRelatedDAO.remove(timetableTutorRelated);
        }
        //
        // 删除实验项目
        //
        String sqlTimetableItemRelated = "select c from TimetableItemRelated c where c.timetableAppointment.id = " + id;
        for (TimetableItemRelated timetableItemRelated : timetableItemRelatedDAO.executeQuery(sqlTimetableItemRelated, 0, -1)) {
            timetableItemRelatedDAO.remove(timetableItemRelated);
        }
        //
        // 删除排课相关的分组
        //
        timetableAppointment.setTimetableGroups(null);
        timetableAppointment = timetableAppointmentDAO.merge(timetableAppointment);
        //
        //删除相关timetableAppointmenntSameNumber排课
        //
        String sqlTimetableAppointmentSameNumber = "select c from TimetableAppointmentSameNumber c where c.timetableAppointment.id = "+ id;
        for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : timetableAppointmentSameNumberDAO.executeQuery(sqlTimetableAppointmentSameNumber, 0, -1)) {
            timetableAppointmentSameNumberDAO.remove(timetableAppointmentSameNumber);
        }

        //删除排课
        timetableAppointmentDAO.remove(timetableAppointment);
        return true;

    }

    /*************************************************************************************
     * Description:排课管理-删除自主类排课
     *
     * @author： 魏誠
     * @date：2018-09-19
     *************************************************************************************/
    @Transactional
    public boolean deleteTimetableByBySameNumberId(Integer id) {

        TimetableAppointmentSameNumber sameNumber = timetableAppointmentSameNumberDAO.findTimetableAppointmentSameNumberById(id);
        //如果samenumber>1则只删除子表，不动主表
        //如果samenumber=1则删除整个相关排课记录
        if(sameNumber.getTimetableAppointment().getTimetableAppointmentSameNumbers().size()>1){
            timetableAppointmentSameNumberDAO.remove(sameNumber);
        }else {
            TimetableAppointment timetableAppointment = sameNumber.getTimetableAppointment();
            //
            // 删除实验室
            //
            String sqlTimetableLabRelated = "select c from TimetableLabRelated c where c.timetableAppointment.id = " + timetableAppointment.getId() ;
            for (TimetableLabRelated timetableLabRelated : timetableLabRelatedDAO.executeQuery(sqlTimetableLabRelated, 0, -1)) {
                timetableLabRelatedDAO.remove(timetableLabRelated);
            }
            //
            // 删除授课教师
            //
            String sqlTimetableTeacherRelated = "select c from TimetableTeacherRelated c where c.timetableAppointment.id = " + timetableAppointment.getId() ;
            for (TimetableTeacherRelated timetableTeacherRelated : timetableTeacherRelatedDAO.executeQuery(sqlTimetableTeacherRelated, 0, -1)) {
                timetableTeacherRelatedDAO.remove(timetableTeacherRelated);
            }
            //
            // 删除指导教师
            //
            String sqlTimetableTutorRelated = "select c from TimetableTutorRelated c where c.timetableAppointment.id = " + timetableAppointment.getId() ;
            for (TimetableTutorRelated timetableTutorRelated : timetableTutorRelatedDAO.executeQuery(sqlTimetableTutorRelated, 0, -1)) {
                timetableTutorRelatedDAO.remove(timetableTutorRelated);
            }
            //
            // 删除实验项目
            //
            String sqlTimetableItemRelated = "select c from TimetableItemRelated c where c.timetableAppointment.id = " + timetableAppointment.getId();
            for (TimetableItemRelated timetableItemRelated : timetableItemRelatedDAO.executeQuery(sqlTimetableItemRelated, 0, -1)) {
                timetableItemRelatedDAO.remove(timetableItemRelated);
            }
            //
            // 删除排课相关的分组
            //
            timetableAppointment.setTimetableGroups(null);
            timetableAppointment = timetableAppointmentDAO.merge(timetableAppointment);
            //
            //删除相关timetableAppointmenntSameNumber排课
            //
            String sqlTimetableAppointmentSameNumber = "select c from TimetableAppointmentSameNumber c where c.timetableAppointment.id = "+ timetableAppointment.getId();
            for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : timetableAppointmentSameNumberDAO.executeQuery(sqlTimetableAppointmentSameNumber, 0, -1)) {
                timetableAppointmentSameNumberDAO.remove(timetableAppointmentSameNumber);
            }

            //删除排课
            timetableAppointmentDAO.remove(timetableAppointment);
        }

        return true;

    }

    /*************************************************************************************
     * Description:排课管理-保存分组
     *
     * @author： 魏誠
     * @date：2018-09-19
     *************************************************************************************/
    @Transactional
    public boolean saveTimetableGroup(int batchId) {
        //
        // 获取批次
        //
        TimetableBatch timetableBatch = timetableBatchDAO.findTimetableBatchById(batchId);
        TimetableGroup timetableGroup = new TimetableGroup();
        timetableGroup.setTimetableBatch(timetableBatch);
        timetableGroup.setGroupName("第" + timetableBatch.getTimetableGroups().size() + "组");
        if (timetableBatch.getTimetableGroups().size() > 0) {
            timetableGroup.setNumbers(timetableBatch.getTimetableGroups().iterator().next().getNumbers());
        } else {
            timetableGroup.setNumbers(0);
        }
        timetableGroupDAO.store(timetableGroup);
        return true;
    }

    /*************************************************************************************
     * Description:排课管理-分批分组维护-保存分批信息
     *
     * @author： 魏誠
     * @date：2018-10-11
     *************************************************************************************/
    @Transactional
    public boolean apiUpdateTimetableBatch(int batchId,String batchName,String startDate,String endDate) throws ParseException {
        //
        // 获取批次
        //
        TimetableBatch timetableBatch = timetableBatchDAO.findTimetableBatchById(batchId);

        if(!"".equals(startDate)){
            //转换日期
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
            Date date =sdf.parse(startDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            timetableBatch.setStartDate(calendar);
        }
        if(!"".equals(endDate)){
            //转换日期
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
            Date date =sdf.parse(endDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            timetableBatch.setEndDate(calendar);
        }
        if(!"".equals(batchName)){
            timetableBatch.setBatchName(batchName);
        }
        timetableBatchDAO.store(timetableBatch);
        return true;
    }

    /*************************************************************************************
     * Description:排课管理-分批分组维护-保存分组名单
     *
     * @author： 魏誠
     * @date：2018-10-11
     *************************************************************************************/
    @Transactional
    public int saveTimetableGroupNumbersReality(int groupId, String[] usernames) {
        //获取分组
        TimetableGroup timetableGroup = timetableGroupDAO.findTimetableGroupById(groupId);
        Set<TimetableGroupStudents> groupStudents = new HashSet<TimetableGroupStudents>();
        //删除相关分组学生
        for (TimetableGroupStudents timetableGroupStudents : timetableGroup.getTimetableGroupStudentses()) {
            timetableGroupStudentsDAO.remove(timetableGroupStudents);
        }
        timetableGroupStudentsDAO.flush();
        //开始循环前端传递的选定名单
        for (String username : usernames) {
            TimetableGroupStudents timetableGroupStudent = new TimetableGroupStudents();
            timetableGroupStudent.setTimetableGroup(timetableGroup);
            timetableGroupStudent.setUser(userDAO.findUserByPrimaryKey(username));
            timetableGroupStudent = timetableGroupStudentsDAO.store(timetableGroupStudent);
            //新的分组增加
            groupStudents.add(timetableGroupStudent);
        }
        //
        timetableGroup.setTimetableGroupStudentses(groupStudents);
        timetableGroup = timetableGroupDAO.merge(timetableGroup);
        timetableGroupDAO.flush();
        return timetableGroup.getTimetableGroupStudentses().size();
    }

    /*************************************************************************************
     * Description:排课管理-保存分组
     *
     * @author： 魏誠
     * @date：2018-09-19
     *************************************************************************************/
    public String getTimetableByOrder(String CourseNo) {
        String timetable = "";
        List<TimetableAppointment> returnTimetableAppointments = new ArrayList<TimetableAppointment>();
        List<TimetableAppointment> timetableAppointments = timetableAppointmentDAO.executeQuery("select c from TimetableAppointment c where c.schoolCourse.courseNo like '" + CourseNo + "'", 0, -1);
        for (TimetableAppointment timetableAppointment1 : timetableAppointments) {
            TimetableAppointment returnTimetableAppointment = timetableAppointment1;
            //遍历周次
            for (TimetableAppointment timetableAppointment2 : timetableAppointments) {
                //相同星期，相同节次，起始节相差1，则合并
                if (timetableAppointment1.getEndWeek() != null && timetableAppointment1.getStartWeek() != null
                        && timetableAppointment2.getEndWeek() != null && timetableAppointment2.getStartWeek() != null
                        && returnTimetableAppointment.getWeekday() == timetableAppointment2.getWeekday() && returnTimetableAppointment.getStartClass() == returnTimetableAppointment.getEndClass()
                        && returnTimetableAppointment.getEndWeek() + 1 == timetableAppointment2.getStartWeek()) {
                    returnTimetableAppointment.setEndWeek(timetableAppointment2.getEndWeek());
                }
            }
            //遍历节次
            for (TimetableAppointment timetableAppointment2 : timetableAppointments) {
                //相同星期，相同节次，起始节相差1，则合并
                if (timetableAppointment1.getEndWeek() != null && timetableAppointment1.getStartWeek() != null
                        && timetableAppointment2.getEndWeek() != null && timetableAppointment2.getStartWeek() != null
                        && returnTimetableAppointment.getWeekday() == timetableAppointment2.getWeekday() && returnTimetableAppointment.getStartWeek() == returnTimetableAppointment.getEndClass()
                        && returnTimetableAppointment.getEndWeek() + 1 == timetableAppointment2.getStartWeek()) {
                    returnTimetableAppointment.setEndWeek(timetableAppointment2.getEndWeek());
                }
            }
            returnTimetableAppointments.add(returnTimetableAppointment);
        }
        return timetable;
    }
}