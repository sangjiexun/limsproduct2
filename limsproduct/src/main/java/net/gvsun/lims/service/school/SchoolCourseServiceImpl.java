package net.gvsun.lims.service.school;

import api.net.gvsunlims.constant.ConstantInterface;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.common.BaseActionAuthDTO;
import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.school.SchoolCourseDetailDTO;
import net.gvsun.lims.dto.timetable.TimetableDTO;
import net.gvsun.lims.dto.timetable.TimetableMergeDTO;
import net.gvsun.lims.service.timetable.TimetableCommonService;
import net.gvsun.lims.service.user.UserActionService;
import net.gvsun.lims.vo.timtable.engineer.EduCourseListVO;
import net.zjcclims.dao.AuditRefuseBackupDAO;
import net.zjcclims.dao.SchoolCourseDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.util.HttpClientUtil;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("schoolCourseService")
public class SchoolCourseServiceImpl implements SchoolCourseService {
    @Autowired
    private SchoolCourseDAO schoolCourseDAO;
    @Autowired
    private UserActionService userActionService;
    @Autowired
    private TimetableCommonService timetableCommonService;
    @Autowired
    private ShareService shareService;
    @Autowired
    private PConfig pConfig;
    @Autowired
    private AuditRefuseBackupDAO auditRefuseBackupDAO;

    /*************************************************************************************
     * Description:教务课程-获取课程库列表
     *
     * @author： 魏誠
     * @date：2018-08-03
     *************************************************************************************/
    public BaseDTO getSchoolCourseListByPage(int termId, String search,String status, int offset, int limit, String sort, String sortOrder,HttpServletRequest request) {

        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select distinct c from SchoolCourse c ");
        //如果是学生，则只能查看自己的选课
        if(request.getSession().getAttribute("selected_role").equals("ROLE_STUDENT")){
            sql.append(" inner join c.schoolCourseDetails detail inner join detail.schoolCourseStudents student ");
            sql.append(" inner join c.timetableAppointments timetable inner join timetable.timetableGroups groups ");
            sql.append(" inner join timetable.timetableLabRelateds labRelated ");
            //sql.append(" inner join timetable.timetableGroups group ");
        }else{
           sql.append(" left join c.timetableAppointments timetable ");
           sql.append(" left join c.labRoom labRoom ");
        }

        sql.append(" where c.schoolTerm.id=" + termId );
        if(request.getSession().getAttribute("selected_role").equals("ROLE_STUDENT")){
            sql.append(" and  student.userByStudentNumber.username like '" + shareService.getUser().getUsername() + "'" );
            sql.append(" and  groups.timetableBatch.ifselect=1 ");
            sql.append(" and timetable.status=1");
        }
        //查询条件
        if (search != null && !"".equals(search)) {
            //sql.append(" and (c.courseNo like '%" + search + "%' or");
            sql.append(" and (c.userByTeacher.cname like '%" + search + "%' or");
            sql.append(" c.schoolAcademy.academyName like '%" + search + "%' or");
            sql.append(" c.userByTeacher.username like '%" + search + "%' or");
            //sql.append(" labRelated.labRoom.labRoomName like '%" + search + "%' or");
            sql.append(" c.schoolCourseInfo.courseName like '%" + search + "%' or");
            sql.append(" c.schoolCourseInfo.courseNumber like '%" + search + "%' or");;
            sql.append(" labRoom.labRoomName like '%" + search + "%')");
        }
        if (status != null && !"".equals(status)) {
            //未排课
            if (status.equals("NO_TIMETABLE") ) {
                sql.append(" and timetable.status is null ");
            }else if (status.equals("TIMETABLEING") ){
                sql.append(" and (timetable.status = 10 and timetable.timetableStyle != 1)");
            }else if (status.equals("REFUSE") ){
                sql.append(" and timetable.status = 4");
            }else if ("ALL".equals(status)){
                sql.append(" and ((timetable.status = 10 and timetable.timetableStyle != 1) or timetable.status is null)");
            }
        }
        // 如果是老师则只能看到自己教的课
        if(request.getSession().getAttribute("selected_role").equals("ROLE_TEACHER")){
            sql.append(" and c.userByTeacher.username like '").append(shareService.getUserDetail().getUsername()).append("'");
        }
        //判断显示
        //获取权限显示
        BaseActionAuthDTO baseActionAuthDTO = userActionService.getBaseActionAuthDTO(ConstantInterface.FUNCTION_MODEL_ACTION_TIMETABLE, request.getSession().getAttribute("selected_role").toString(), shareService.getUser().getUsername());
        if(!baseActionAuthDTO.isCrossAcademyActionAuth()){
            sql.append(" and c.schoolAcademy.academyNumber like '").append(shareService.getUserDetail().getSchoolAcademy().getAcademyNumber()).append("'");
        }
        //排序
        if ("courseName".equals(sort)||"courseNumber".equals(sort)) {
            sql.append(" order by c.schoolCourseInfo." + sort +" " +sortOrder);
        }else if ("cname".equals(sort)||"username".equals(sort)) {
            sql.append(" order by c.userByTeacher." + sort +" " +sortOrder);
        }else{
           sql.append(" order by case when c.schoolAcademy.academyNumber='" +
               request.getSession().getAttribute("selected_academy")+ "' then 0 else 1 end, c.courseNo ");
        }
        // 执行sb语句
        List<SchoolCourse> courses = schoolCourseDAO.executeQuery(sql.toString(), offset, limit);
        //获取总记录数主查询语句

        sql = new StringBuffer("select count(distinct c) from SchoolCourse c " );
        if(request.getSession().getAttribute("selected_role").equals("ROLE_STUDENT")){
            sql.append(" inner join c.schoolCourseDetails detail inner join detail.schoolCourseStudents student ");
            sql.append(" left join c.timetableAppointments timetable left join timetable.timetableGroups groups ");
            sql.append(" left join timetable.timetableLabRelateds labRelated ");
            //sql.append(" inner join timetable.timetableGroups group ");
        }else{
            sql.append(" left join c.timetableAppointments timetable ");
            sql.append(" left join c.labRoom labRoom ");
        }
        sql.append(" where c.schoolTerm.id=" + termId );
        if(request.getSession().getAttribute("selected_role").equals("ROLE_STUDENT")){
            sql.append(" and  student.userByStudentNumber.username like '" + shareService.getUser().getUsername() + "'" );
            sql.append(" and  groups.timetableBatch.ifselect=1 ");
            sql.append(" and timetable.status=1");
        }
        //查询条件
        if (search != null && !"".equals(search)) {
            sql.append("  and (c.userByTeacher.cname like '%" + search + "%' or");
            sql.append(" c.schoolAcademy.academyName like '%" + search + "%' or");
            sql.append(" c.userByTeacher.username like '%" + search + "%' or");
            //sql.append(" labRelated.labRoom.labRoomName like '%" + search + "%' or");
            sql.append(" c.schoolCourseInfo.courseName like '%" + search + "%' or");
            sql.append(" c.schoolCourseInfo.courseNumber like '%" + search + "%' or");
            sql.append(" labRoom.labRoomName like '%" + search + "%')");
        }
        if (status != null && !"".equals(status)) {
            //未排课
            if (status.equals("NO_TIMETABLE") ) {
                sql.append(" and timetable.status is null ");
            }else if (status.equals("TIMETABLEING") ){
                sql.append(" and (timetable.status = 10 and timetable.timetableStyle != 1)");
            }else if (status.equals("REFUSE") ){
                sql.append(" and timetable.status = 4");
            }else if ("ALL".equals(status)){
                sql.append(" and ((timetable.status = 10 and timetable.timetableStyle != 1) or timetable.status is null or timetable.status = 4)");
            }
        }
        // 如果是老师则只能看到自己教的课
        if(request.getSession().getAttribute("selected_role").equals("ROLE_TEACHER")){
            sql.append(" and c.userByTeacher.username like '").append(shareService.getUserDetail().getUsername()).append("'");
        }

        //判断显示
        //获取权限显示
        if(!baseActionAuthDTO.isCrossAcademyActionAuth()){
            sql.append(" and c.schoolAcademy.academyNumber like '").append(shareService.getUserDetail().getSchoolAcademy().getAcademyNumber()).append("'");
        }
        int total = ((Long) schoolCourseDAO.createQuerySingleResult(sql.toString()).getSingleResult()).intValue();
        //封装VO
        List<EduCourseListVO> eduCourseListVOs = new ArrayList<EduCourseListVO>();
        for (SchoolCourse schoolCourse : courses) {
            EduCourseListVO eduCourseListVO = new EduCourseListVO();
            //设置courseCode
            eduCourseListVO.setCourseNo(schoolCourse.getCourseNo());
            //设置课程名称
            eduCourseListVO.setCourseName(schoolCourse.getSchoolCourseInfo().getCourseName());
            //设置班级信息
            String classInfo = "";
            if(schoolCourse.getSchoolClasseses().size()>0){
                for(SchoolClasses schoolClasses:schoolCourse.getSchoolClasseses()){
                    classInfo += schoolClasses.getClassName()+"("+ schoolClasses.getClassNumber()+")</br>";
                }
                eduCourseListVO.setClassInfo(classInfo);
            }else{
                eduCourseListVO.setClassInfo("-");
            }
            //设置课程编号
            eduCourseListVO.setCourseNumber(schoolCourse.getSchoolCourseInfo().getCourseNumber());
            //设置学期
            eduCourseListVO.setTermName(schoolCourse.getSchoolTerm().getTermName());
            eduCourseListVO.setTermId(schoolCourse.getSchoolTerm().getId());
            eduCourseListVO.setAcademyName(schoolCourse.getSchoolAcademy().getAcademyName());
            //设置上课教师
            eduCourseListVO.setCname(schoolCourse.getUserByTeacher().getCname());
            eduCourseListVO.setUsername(schoolCourse.getUserByTeacher().getUsername());
            //设置是否排课
            if(schoolCourse.getTimetableAppointments().size()==ConstantInterface.TIMETABLE_STATUS_NO_APPOINTMENT){
                eduCourseListVO.setTimetableStatus(ConstantInterface.TIMETABLE_STATUS_NO_APPOINTMENT);
            }else if(schoolCourse.getTimetableAppointments().size()>0&&schoolCourse.getTimetableAppointments().iterator().next().getStatus()==ConstantInterface.TIMETABLE_STATUS_PUBLIC){
                eduCourseListVO.setTimetableStatus(ConstantInterface.TIMETABLE_STATUS_PUBLIC);
            }else if(schoolCourse.getTimetableAppointments().size()>0&&schoolCourse.getTimetableAppointments().iterator().next().getStatus()==ConstantInterface.TIMETABLE_STATUS_TOBE_PUBLIC){
                eduCourseListVO.setTimetableStatus(ConstantInterface.TIMETABLE_STATUS_TOBE_PUBLIC);
            }else if(schoolCourse.getTimetableAppointments().size()>0&&schoolCourse.getTimetableAppointments().iterator().next().getStatus()==ConstantInterface.TIMETABLE_STATUS_NO_PUBLIC){
                eduCourseListVO.setTimetableStatus(ConstantInterface.TIMETABLE_STATUS_NO_PUBLIC);
            }else if(schoolCourse.getTimetableAppointments().size()>0&&schoolCourse.getTimetableAppointments().iterator().next().getStatus()==ConstantInterface.TIMETABLE_STATUS_AUDIT_ACCESS){
                eduCourseListVO.setTimetableStatus(ConstantInterface.TIMETABLE_STATUS_AUDIT_ACCESS);
            }else if(schoolCourse.getTimetableAppointments().size()>0&&schoolCourse.getTimetableAppointments().iterator().next().getStatus()==ConstantInterface.TIMETABLE_STATUS_AUDIT_REFUSE){
                eduCourseListVO.setTimetableStatus(ConstantInterface.TIMETABLE_STATUS_AUDIT_REFUSE);
            }
            //获取课程计划
            List<SchoolCourseDetailDTO> schoolCourseDetailDTOs = new ArrayList<SchoolCourseDetailDTO>();
            Set<SchoolCourseDetail> schoolCourseDetails = schoolCourse.getSchoolCourseDetails();
            for(SchoolCourseDetail schoolCourseDetail : schoolCourseDetails) {
                SchoolCourseDetailDTO  schoolCourseDetailDTO = new SchoolCourseDetailDTO();
                //设置课程计划
                schoolCourseDetailDTO.setCoursePlan("星期"+schoolCourseDetail.getWeekday()+" ["+schoolCourseDetail.getStartClass()+"-"+schoolCourseDetail.getEndClass()+"]节" +" ["+schoolCourseDetail.getStartWeek()+"-"+schoolCourseDetail.getEndWeek()+"]周");
                //设置实验室
                schoolCourseDetailDTO.setLabInfo(Objects.nonNull(schoolCourseDetail.getClassroomType())?schoolCourseDetail.getClassroomType():"");
                schoolCourseDetailDTO.setWeekday(Objects.nonNull(schoolCourseDetail.getWeekday())?schoolCourseDetail.getWeekday():0);
                schoolCourseDetailDTO.setStartClass(Objects.nonNull(schoolCourseDetail.getStartClass())?schoolCourseDetail.getStartClass():0);
                schoolCourseDetailDTO.setEndClass(Objects.nonNull(schoolCourseDetail.getEndClass())?schoolCourseDetail.getEndClass():0);
                schoolCourseDetailDTO.setStartWeek(Objects.nonNull(schoolCourseDetail.getStartWeek())?schoolCourseDetail.getStartWeek():0);
                schoolCourseDetailDTO.setEndWeek(Objects.nonNull(schoolCourseDetail.getEndWeek())?schoolCourseDetail.getEndWeek():0);
//                eduCourseListVO.setStudent(schoolCourseDetail.getSchoolCourseStudents().size());
                schoolCourseDetailDTOs.add(schoolCourseDetailDTO);
            }
            eduCourseListVO.setSchoolCourseDetailDTOs(schoolCourseDetailDTOs);
            // 课程的学生名单数
            sql = new StringBuffer("select count(distinct student.userByStudentNumber.username) from SchoolCourse c ");
            sql.append(" inner join c.schoolCourseDetails detail inner join detail.schoolCourseStudents student ");
            sql.append(" where c.courseNo='"+ schoolCourse.getCourseNo() +"'");
            int stu = ((Long) schoolCourseDAO.createQuerySingleResult(sql.toString()).getSingleResult()).intValue();
            eduCourseListVO.setStudent(stu);
            //获取权限显示
            //BaseActionAuthDTO baseActionAuthDTO = userActionService.getBaseActionAuthDTO(ConstantInterface.FUNCTION_MODEL_ACTION_TIMETABLE,request.getSession().getAttribute("selected_role").toString(),schoolCourse.getUserByTeacher().getUsername());
            eduCourseListVO.setBaseActionAuthDTO(baseActionAuthDTO);
            if(schoolCourse.getTimetableAppointments().size()>0){
                eduCourseListVO.setTimetableStyle(schoolCourse.getTimetableAppointments().iterator().next().getTimetableStyle());
                //判断是否当前学生选课
                for(TimetableAppointment timetableAppointment:schoolCourse.getTimetableAppointments()){
                    for(TimetableGroup timetableGroup:timetableAppointment.getTimetableGroups()){
                        if(timetableGroup.getTimetableBatch().getIfselect()==1&&timetableGroup.getTimetableBatch().getStartDate().getTime().before(new Date())&&timetableGroup.getTimetableBatch().getEndDate().getTime().after(new Date())){
                            //条件满足，设置学生选课状态
                            eduCourseListVO.setIsSelect(ConstantInterface.TIMETABLE_BATCH_IS_SELECT);
                            break;
                        }
                    }
                }
            }
            TimetableMergeDTO timetableMergeDTO = new TimetableMergeDTO();
            timetableMergeDTO.setCourseNo(schoolCourse.getCourseNo());
            timetableMergeDTO.setTerm(schoolCourse.getSchoolTerm().getId());
            //增加排课
            eduCourseListVO.setTimetableMergeDTOs(timetableCommonService.apiGetMergeTimetableClassAndWeek(timetableMergeDTO));
            eduCourseListVO.setTimetableDTOs(timetableCommonService.apiTimetableDTOs(schoolCourse.getCourseNo()));
            eduCourseListVOs.add(eduCourseListVO);
        }
        BaseDTO baseVO = new BaseDTO();
        baseVO.setRows(eduCourseListVOs);
        baseVO.setTotal(total);
        return baseVO;
    }

    /**************************************************************************
     * Description 教务排课-查看排课管理列表-获取数据
     *
     * @author 魏诚
     * @date 2018年10月17日
     **************************************************************************/
    public BaseDTO apiEduSchoolCourseByPage(int termId, String search,String status, int offset, int limit, String sort, String sortOrder,HttpServletRequest request) {
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select distinct c from SchoolCourse c ");
        //如果是学生，则只能查看自己的选课
        if(request.getSession().getAttribute("selected_role").equals("ROLE_STUDENT")){
            sql.append(" inner join c.schoolCourseDetails detail inner join detail.schoolCourseStudents student ");
            sql.append(" inner join c.timetableAppointments timetable inner join timetable.timetableGroups groups ");
            sql.append(" inner join timetable.timetableLabRelateds labRelated ");
            //sql.append(" inner join timetable.timetableGroups group ");
        }
        // 如果是实验室管理员，则只能查看自己实验室的选课
        else if(request.getSession().getAttribute("selected_role").equals("ROLE_LABMANAGER")){
            sql.append(" inner join c.timetableAppointments timetable ");
            sql.append(" left join timetable.timetableLabRelateds timetableLabRelated ");
            sql.append(" left join timetableLabRelated.labRoom.labRoomAdmins labRoomAdmin ");
        }else{
            sql.append(" inner join c.timetableAppointments timetable ");
            sql.append(" left join c.labRoom labRoom ");
        }

        sql.append(" where timetable.weekday is not null and c.schoolTerm.id=" + termId );
        if(request.getSession().getAttribute("selected_role").equals("ROLE_STUDENT")){
            sql.append(" and  student.userByStudentNumber.username like '" + shareService.getUser().getUsername() + "'" );
            sql.append(" and  groups.timetableBatch.ifselect=1 ");
            sql.append(" and timetable.status=1");
        }
        //查询条件
        if (search != null && !"".equals(search)) {
            //sql.append(" and (c.courseNo like '%" + search + "%' or");
            sql.append("  and (c.userByTeacher.cname like '%" + search + "%' or");
            sql.append(" c.schoolAcademy.academyName like '%" + search + "%' or");
            sql.append(" c.userByTeacher.username like '%" + search + "%' or");
            //sql.append(" labRelated.labRoom.labRoomName like '%" + search + "%' or");
            sql.append(" c.schoolCourseInfo.courseName like '%" + search + "%' or");
            sql.append(" c.schoolCourseInfo.courseNumber like '%" + search + "%' or");
            sql.append(" labRoom.labRoomName like '%" + search + "%')");
        }
        if (status != null && !"".equals(status)) {
            //待发布
            if (status.equals("STAND_TO_RELEASE") ) {
                sql.append(" and (timetable.weekday is not null and timetable.status=3) or (timetable.status = 10 and timetable.timetableStyle = 1)");
            }
            //已发布
            if (status.equals("RELEASED") ) {
                sql.append(" and timetable.weekday is not null and timetable.status=1");
            }
            if ("ALL".equals(status)){
                sql.append(" and (timetable.status != 10 or timetable.timetableStyle = 1) and timetable.status != 4");
            }
            //待审核
            if (status.equals("STAND_TO_AUDIT") ) {
                sql.append(" and timetable.weekday is not null and timetable.status=2");
            }
        }
        // 如果是老师则只能看到自己教的课
        if(request.getSession().getAttribute("selected_role").equals("ROLE_TEACHER")){
            sql.append(" and c.userByTeacher.username like '").append(shareService.getUserDetail().getUsername()).append("'");
        }
        // 如果是实验室管理员则只能看到自己实验室上的课
        if(request.getSession().getAttribute("selected_role").equals("ROLE_LABMANAGER")){
            sql.append(" and labRoomAdmin.user.username like '").append(shareService.getUserDetail().getUsername()).append("'");
        }

        //判断显示
        //获取权限显示
        BaseActionAuthDTO baseActionAuthDTO = userActionService.getBaseActionAuthDTO(ConstantInterface.FUNCTION_MODEL_ACTION_TIMETABLE, request.getSession().getAttribute("selected_role").toString(), shareService.getUser().getUsername());
        if(!baseActionAuthDTO.isCrossAcademyActionAuth()){
            sql.append(" and c.schoolAcademy.academyNumber like '").append(shareService.getUserDetail().getSchoolAcademy().getAcademyNumber()).append("'");
        }

        //排序
        if ("courseName".equals(sort)||"courseNumber".equals(sort)) {
            sql.append(" order by c.schoolCourseInfo." + sort +" " +sortOrder);
        }else if ("cname".equals(sort)||"username".equals(sort)) {
            sql.append(" order by c.userByTeacher." + sort +" " +sortOrder);
        }else{
            sql.append(" order by case when c.schoolAcademy.academyNumber='" +
                    request.getSession().getAttribute("selected_academy")+ "' then 0 else 1 end, c.courseNo ");
        }
        // 执行sb语句
        List<SchoolCourse> courses = schoolCourseDAO.executeQuery(sql.toString(), offset, limit);
        //获取总记录数主查询语句
        sql = new StringBuffer("select count(distinct c) from SchoolCourse c " );
        if(request.getSession().getAttribute("selected_role").equals("ROLE_STUDENT")){
            sql.append(" inner join c.schoolCourseDetails detail inner join detail.schoolCourseStudents student ");
            sql.append(" inner join c.timetableAppointments timetable left join timetable.timetableGroups groups ");
            sql.append(" inner join timetable.timetableLabRelateds labRelated ");
            //sql.append(" inner join timetable.timetableGroups group ");
        }
        // 如果是实验室管理员，则只能查看自己实验室的选课
        else if(request.getSession().getAttribute("selected_role").equals("ROLE_LABMANAGER")){
            sql.append(" inner join c.timetableAppointments timetable ");
            sql.append(" left join timetable.timetableLabRelateds timetableLabRelated ");
            sql.append(" left join timetableLabRelated.labRoom.labRoomAdmins labRoomAdmin ");
        }else{
            sql.append(" inner join c.timetableAppointments timetable ");
            sql.append(" left join c.labRoom labRoom ");
        }
        sql.append(" where timetable.weekday is not null and c.schoolTerm.id=" + termId );
        if(request.getSession().getAttribute("selected_role").equals("ROLE_STUDENT")){
            sql.append(" and  student.userByStudentNumber.username like '" + shareService.getUser().getUsername() + "'" );
            sql.append(" and  groups.timetableBatch.ifselect=1 ");
            sql.append(" and timetable.status=1");
        }
        //查询条件
        if (search != null && !"".equals(search)) {
            sql.append("  and (c.userByTeacher.cname like '%" + search + "%' or");
            sql.append(" c.schoolAcademy.academyName like '%" + search + "%' or");
            sql.append(" c.userByTeacher.username like '%" + search + "%' or");
            //sql.append(" labRelated.labRoom.labRoomName like '%" + search + "%' or");
            sql.append(" c.schoolCourseInfo.courseName like '%" + search + "%' or");
            sql.append(" c.schoolCourseInfo.courseNumber like '%" + search + "%' or");
            sql.append(" labRoom.labRoomName like '%" + search + "%')");
        }
        if (status != null && !"".equals(status)) {
            //待发布
            if (status.equals("STAND_TO_RELEASE") ) {
                sql.append(" and (timetable.weekday is not null and timetable.status=3) or (timetable.status = 10 and timetable.timetableStyle = 1)");
            }
            //已发布
            if (status.equals("RELEASED") ) {
                sql.append(" and timetable.weekday is not null and timetable.status=1");
            }
            if ("ALL".equals(status)){
                sql.append(" and (timetable.status != 10 or timetable.timetableStyle = 1) and timetable.status != 4");
            }
            //待审核
            if (status.equals("STAND_TO_AUDIT") ) {
                sql.append(" and timetable.weekday is not null and timetable.status=2");
            }
        }

        // 如果是老师则只能看到自己教的课
        if(request.getSession().getAttribute("selected_role").equals("ROLE_TEACHER")){
            sql.append(" and c.userByTeacher.username like '").append(shareService.getUserDetail().getUsername()).append("'");
        }
        // 如果是实验室管理员则只能看到自己实验室上的课
        if(request.getSession().getAttribute("selected_role").equals("ROLE_LABMANAGER")){
            sql.append(" and labRoomAdmin.user.username like '").append(shareService.getUserDetail().getUsername()).append("'");
        }

        //判断显示
        //获取权限显示
        if(!baseActionAuthDTO.isCrossAcademyActionAuth()){
            sql.append(" and c.schoolAcademy.academyNumber like '").append(shareService.getUserDetail().getSchoolAcademy().getAcademyNumber()).append("'");
        }
        int total = ((Long) schoolCourseDAO.createQuerySingleResult(sql.toString()).getSingleResult()).intValue();
        //封装VO
        List<EduCourseListVO> eduCourseListVOs = new ArrayList<EduCourseListVO>();
        for (SchoolCourse schoolCourse : courses) {
            EduCourseListVO eduCourseListVO = new EduCourseListVO();
            //设置courseCode
            eduCourseListVO.setCourseNo(schoolCourse.getCourseNo());
            //设置课程名称
            eduCourseListVO.setCourseName(schoolCourse.getSchoolCourseInfo().getCourseName());
            //设置班级信息
            String classInfo = "";
            if(schoolCourse.getSchoolClasseses().size()>0){
                for(SchoolClasses schoolClasses:schoolCourse.getSchoolClasseses()){
                    classInfo += schoolClasses.getClassName()+"("+ schoolClasses.getClassNumber()+")</br>";
                }
                eduCourseListVO.setClassInfo(classInfo);
            }else{
                eduCourseListVO.setClassInfo("-");
            }
            //设置课程编号
            eduCourseListVO.setCourseNumber(schoolCourse.getSchoolCourseInfo().getCourseNumber());
            //设置学期
            eduCourseListVO.setTermName(schoolCourse.getSchoolTerm().getTermName());
            eduCourseListVO.setTermId(schoolCourse.getSchoolTerm().getId());
            eduCourseListVO.setAcademyName(schoolCourse.getSchoolAcademy().getAcademyName());
            //设置上课教师
            eduCourseListVO.setCname(schoolCourse.getUserByTeacher().getCname());
            eduCourseListVO.setUsername(schoolCourse.getUserByTeacher().getUsername());
            //设置是否排课
            if(schoolCourse.getTimetableAppointments().size()==ConstantInterface.TIMETABLE_STATUS_NO_APPOINTMENT){
                eduCourseListVO.setTimetableStatus(ConstantInterface.TIMETABLE_STATUS_NO_APPOINTMENT);
            }else if(schoolCourse.getTimetableAppointments().size()>0&&schoolCourse.getTimetableAppointments().iterator().next().getStatus()==ConstantInterface.TIMETABLE_STATUS_PUBLIC){
                eduCourseListVO.setTimetableStatus(ConstantInterface.TIMETABLE_STATUS_PUBLIC);
            }else if(schoolCourse.getTimetableAppointments().size()>0&&schoolCourse.getTimetableAppointments().iterator().next().getStatus()==ConstantInterface.TIMETABLE_STATUS_TOBE_PUBLIC){
                eduCourseListVO.setTimetableStatus(ConstantInterface.TIMETABLE_STATUS_TOBE_PUBLIC);
            }else if(schoolCourse.getTimetableAppointments().size()>0&&schoolCourse.getTimetableAppointments().iterator().next().getStatus()==ConstantInterface.TIMETABLE_STATUS_NO_PUBLIC){
                eduCourseListVO.setTimetableStatus(ConstantInterface.TIMETABLE_STATUS_NO_PUBLIC);
            }else if(schoolCourse.getTimetableAppointments().size()>0&&schoolCourse.getTimetableAppointments().iterator().next().getStatus()==ConstantInterface.TIMETABLE_STATUS_AUDIT_ACCESS){
                eduCourseListVO.setTimetableStatus(ConstantInterface.TIMETABLE_STATUS_AUDIT_ACCESS);
            }else if(schoolCourse.getTimetableAppointments().size()>0&&schoolCourse.getTimetableAppointments().iterator().next().getStatus()==ConstantInterface.TIMETABLE_STATUS_AUDIT_REFUSE){
                eduCourseListVO.setTimetableStatus(ConstantInterface.TIMETABLE_STATUS_AUDIT_REFUSE);
            }
            //获取课程计划
            List<SchoolCourseDetailDTO> schoolCourseDetailDTOs = new ArrayList<SchoolCourseDetailDTO>();
            for(SchoolCourseDetail schoolCourseDetail:schoolCourse.getSchoolCourseDetails()){
                SchoolCourseDetailDTO  schoolCourseDetailDTO = new SchoolCourseDetailDTO();
                //设置课程计划
                schoolCourseDetailDTO.setCoursePlan("星期"+schoolCourseDetail.getWeekday()+" ["+schoolCourseDetail.getStartClass()+"-"+schoolCourseDetail.getEndClass()+"]节" +" ["+schoolCourseDetail.getStartWeek()+"-"+schoolCourseDetail.getEndWeek()+"]周");
                //设置实验室
                schoolCourseDetailDTO.setLabInfo(Objects.nonNull(schoolCourseDetail.getClassroomType())?schoolCourseDetail.getClassroomType():"");
                schoolCourseDetailDTO.setWeekday(Objects.nonNull(schoolCourseDetail.getWeekday())?schoolCourseDetail.getWeekday():0);
                schoolCourseDetailDTO.setStartClass(Objects.nonNull(schoolCourseDetail.getStartClass())?schoolCourseDetail.getStartClass():0);
                schoolCourseDetailDTO.setEndClass(Objects.nonNull(schoolCourseDetail.getEndClass())?schoolCourseDetail.getEndClass():0);
                schoolCourseDetailDTO.setStartWeek(Objects.nonNull(schoolCourseDetail.getStartWeek())?schoolCourseDetail.getStartWeek():0);
                schoolCourseDetailDTO.setEndWeek(Objects.nonNull(schoolCourseDetail.getEndWeek())?schoolCourseDetail.getEndWeek():0);
//                eduCourseListVO.setStudent(schoolCourseDetail.getSchoolCourseStudents().size());
                schoolCourseDetailDTOs.add(schoolCourseDetailDTO);
            }
            eduCourseListVO.setSchoolCourseDetailDTOs(schoolCourseDetailDTOs);
            // 课程的学生名单数
            sql = new StringBuffer("select count(distinct student.userByStudentNumber.username) from SchoolCourse c ");
            sql.append(" inner join c.schoolCourseDetails detail inner join detail.schoolCourseStudents student ");
            sql.append(" where c.courseNo='"+ schoolCourse.getCourseNo() +"'");
            int stu = ((Long) schoolCourseDAO.createQuerySingleResult(sql.toString()).getSingleResult()).intValue();
            eduCourseListVO.setStudent(stu);
            //获取权限显示
            //BaseActionAuthDTO baseActionAuthDTO = userActionService.getBaseActionAuthDTO(ConstantInterface.FUNCTION_MODEL_ACTION_TIMETABLE,request.getSession().getAttribute("selected_role").toString(),schoolCourse.getUserByTeacher().getUsername());
            // 获取审核权限
            BaseActionAuthDTO temp = baseActionAuthDTO;
            baseActionAuthDTO = new BaseActionAuthDTO();
            baseActionAuthDTO.copy(temp);
            baseActionAuthDTO.setAuditActionAuth(false);
            Map<String, String> params = new HashMap<>();
            String businessType = "TimetableAudit";
            params.put("businessAppUid", schoolCourse.getCourseNo());
            params.put("businessType", pConfig.PROJECT_NAME + businessType);
            String curStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", params);
            JSONObject jsonObject = JSON.parseObject(curStr);
            String curStatus = jsonObject.getString("status");
            if ("success".equals(curStatus)) {
                if(jsonObject.getJSONArray("data") != null &&jsonObject.getJSONArray("data").size()>0 ) {
                    JSONObject curJSONObject = jsonObject.getJSONArray("data").getJSONObject(0);
                    String authName = curJSONObject.getString("result");
                    if (request.getSession().getAttribute("selected_role").equals("ROLE_" + authName)) {
                        baseActionAuthDTO.setAuditActionAuth(true);
                    }
                }
            }
            eduCourseListVO.setBaseActionAuthDTO(baseActionAuthDTO);
            if(schoolCourse.getTimetableAppointments().size()>0){
                eduCourseListVO.setTimetableStyle(schoolCourse.getTimetableAppointments().iterator().next().getTimetableStyle());
                //判断是否当前学生选课
                for(TimetableAppointment timetableAppointment:schoolCourse.getTimetableAppointments()){
                    for(TimetableGroup timetableGroup:timetableAppointment.getTimetableGroups()){
                        if(timetableGroup.getTimetableBatch().getIfselect()==1&&timetableGroup.getTimetableBatch().getStartDate().getTime().before(new Date())&&timetableGroup.getTimetableBatch().getEndDate().getTime().after(new Date())){
                            //条件满足，设置学生选课状态
                            eduCourseListVO.setIsSelect(ConstantInterface.TIMETABLE_BATCH_IS_SELECT);
                            break;
                        }
                    }
                }
            }
            TimetableMergeDTO timetableMergeDTO = new TimetableMergeDTO();
            timetableMergeDTO.setCourseNo(schoolCourse.getCourseNo());
            timetableMergeDTO.setTerm(schoolCourse.getSchoolTerm().getId());
            //增加排课
            eduCourseListVO.setTimetableMergeDTOs(timetableCommonService.apiGetMergeTimetableClassAndWeek(timetableMergeDTO));
            eduCourseListVO.setTimetableDTOs(timetableCommonService.apiTimetableDTOs(schoolCourse.getCourseNo()));
            eduCourseListVOs.add(eduCourseListVO);
        }
        BaseDTO baseVO = new BaseDTO();
        baseVO.setRows(eduCourseListVOs);
        baseVO.setTotal(total);
        return baseVO;
    }

    /**
     * Description 获取排课审核拒绝列表
     * @param termId 学期id
     * @param search 输入
     * @param status 状态
     * @param offset 偏移量
     * @param limit 每页最大数量
     * @param sort 排序字段
     * @param sortOrder 排序方式
     * @param request 请求（传参）
     * @return 排课审核拒绝列表
     * @author 黄保钱 2019-1-23
     */
    @Override
    public BaseDTO getSchoolCourseListByPageForRefuse(int termId, String search, String status, int offset, int limit, String sort, String sortOrder, HttpServletRequest request) {
        StringBuilder sql = new StringBuilder("select distinct arb from RefuseItemBackup rib join rib.auditRefuseBackup arb where rib.type = 'EduTimetableAudit' ");
        StringBuilder searchSql = new StringBuilder();
        searchSql.append(" and rib.term = " + termId);
        //查询条件
        if (search != null && !"".equals(search)) {
            searchSql.append(" and (rib.teacher like '%").append(search).append("%' or");
            searchSql.append(" rib.businessId like '%").append(search).append("%' or");
            searchSql.append(" rib.tutor like '%").append(search).append("%' or");
            searchSql.append(" rib.labRoomName like '%").append(search).append("%')");
        }
        sql.append(searchSql);
        //排序
        if(sort != null && "".equals(sort)) {
            sql.append(" order by oib.").append(sort).append(" ").append(sortOrder);
        }
        // 教务排课日志
        List<AuditRefuseBackup> auditRefuseBackups = auditRefuseBackupDAO.executeQuery(sql.toString());
        sql = new StringBuilder("select count(arb) from RefuseItemBackup rib join rib.auditRefuseBackup arb where rib.type = 'EduTimetableAudit' ");
        sql.append(searchSql);
        int totalSize = ((Long) auditRefuseBackupDAO.executeQuerySingleResult(sql.toString())).intValue();
        List<EduCourseListVO> eduCourseListVOs = new ArrayList<EduCourseListVO>();
        for(AuditRefuseBackup auditRefuseBackup: auditRefuseBackups){
            EduCourseListVO eduCourseListVO = new EduCourseListVO();
            SchoolCourse schoolCourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(auditRefuseBackup.getRefuseItemBackup().iterator().next().getBusinessId());
            //设置courseCode
            eduCourseListVO.setCourseNumber(auditRefuseBackup.getRefuseItemBackup().iterator().next().getBusinessId());
            //设置课程名称
            eduCourseListVO.setCourseName(schoolCourse.getSchoolCourseInfo().getCourseName());
            String teacher ="";
            String tutor = "";
            String creator = "";
            List<TimetableDTO> timetableDTOS = new ArrayList<>();
            for(RefuseItemBackup refuseItemBackup: auditRefuseBackup.getRefuseItemBackup()){
                teacher += refuseItemBackup.getTeacher() + ", ";
                tutor += refuseItemBackup.getTutor() + ", ";
                creator += refuseItemBackup.getCreator() + ", ";
                TimetableDTO timetableDTO = new TimetableDTO();
                timetableDTO.setStartClass(refuseItemBackup.getStartClass());
                timetableDTO.setEndClass(refuseItemBackup.getEndClass());
                timetableDTO.setStartWeek(refuseItemBackup.getStartWeek());
                timetableDTO.setEndWeek(refuseItemBackup.getEndWeek());
                timetableDTO.setWeekday(refuseItemBackup.getWeekday());
                timetableDTO.setLabInfo(refuseItemBackup.getLabRoomName());
                timetableDTOS.add(timetableDTO);
            }
            eduCourseListVO.setTimetableDTOs(timetableDTOS);
            eduCourseListVO.setCname(teacher);
            eduCourseListVO.setUsername(tutor);
            eduCourseListVO.setClassInfo(creator);
            eduCourseListVO.setAcademyName("审核信息：" + auditRefuseBackup.getAuditInfo() + "\n审核意见：" + auditRefuseBackup.getAuditContent());
            eduCourseListVOs.add(eduCourseListVO);
        }
        BaseDTO baseVO = new BaseDTO();
        baseVO.setRows(eduCourseListVOs);
        baseVO.setTotal(totalSize);
        return baseVO;
    }

    /**************************************************************************
     * Description 排课审核-获取当前审核对象信息-教务
     * @param termId
     * @param search
     * @return
     * @author 陈乐为 2019-1-16
     **************************************************************************/
    public BaseDTO apiEduSchoolCourseForAudit(int termId, String search) {
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select distinct c from SchoolCourse c ");
        // 如果是实验室管理员，则只能查看自己实验室的选课
        sql.append(" inner join c.timetableAppointments timetable ");
        sql.append(" left join c.labRoom labRoom ");
        sql.append(" where timetable.weekday is not null and c.schoolTerm.id=" + termId );
        //查询条件
        if (search != null && !"".equals(search)) {
            sql.append(" and c.courseNo = '" + search + "'");
        }
        // 执行sb语句
        List<SchoolCourse> courses = schoolCourseDAO.executeQuery(sql.toString(), 0, -1);

        //封装VO
        List<EduCourseListVO> eduCourseListVOs = new ArrayList<EduCourseListVO>();
        for (SchoolCourse schoolCourse : courses) {
            EduCourseListVO eduCourseListVO = new EduCourseListVO();
            //设置courseCode
            eduCourseListVO.setCourseNo(schoolCourse.getCourseNo());
            //设置课程名称
            eduCourseListVO.setCourseName(schoolCourse.getSchoolCourseInfo().getCourseName());
            //设置班级信息
            String classInfo = "";
            if(schoolCourse.getSchoolClasseses().size()>0){
                for(SchoolClasses schoolClasses:schoolCourse.getSchoolClasseses()){
                    classInfo += schoolClasses.getClassName()+"("+ schoolClasses.getClassNumber()+")</br>";
                }
                eduCourseListVO.setClassInfo(classInfo);
            }else{
                eduCourseListVO.setClassInfo("-");
            }
            //设置课程编号
            eduCourseListVO.setCourseNumber(schoolCourse.getSchoolCourseInfo().getCourseNumber());
            //设置学期
            eduCourseListVO.setTermName(schoolCourse.getSchoolTerm().getTermName());
            eduCourseListVO.setTermId(schoolCourse.getSchoolTerm().getId());
            eduCourseListVO.setAcademyName(schoolCourse.getSchoolAcademy().getAcademyName());
            //设置上课教师
            eduCourseListVO.setCname(schoolCourse.getUserByTeacher().getCname());
            eduCourseListVO.setUsername(schoolCourse.getUserByTeacher().getUsername());
            //设置是否排课
            if(schoolCourse.getTimetableAppointments().size()==ConstantInterface.TIMETABLE_STATUS_NO_APPOINTMENT){
                eduCourseListVO.setTimetableStatus(ConstantInterface.TIMETABLE_STATUS_NO_APPOINTMENT);
            }else if(schoolCourse.getTimetableAppointments().size()>0&&schoolCourse.getTimetableAppointments().iterator().next().getStatus()==ConstantInterface.TIMETABLE_STATUS_PUBLIC){
                eduCourseListVO.setTimetableStatus(ConstantInterface.TIMETABLE_STATUS_PUBLIC);
            }else if(schoolCourse.getTimetableAppointments().size()>0&&schoolCourse.getTimetableAppointments().iterator().next().getStatus()==ConstantInterface.TIMETABLE_STATUS_TOBE_PUBLIC){
                eduCourseListVO.setTimetableStatus(ConstantInterface.TIMETABLE_STATUS_TOBE_PUBLIC);
            }else if(schoolCourse.getTimetableAppointments().size()>0&&schoolCourse.getTimetableAppointments().iterator().next().getStatus()==ConstantInterface.TIMETABLE_STATUS_NO_PUBLIC){
                eduCourseListVO.setTimetableStatus(ConstantInterface.TIMETABLE_STATUS_NO_PUBLIC);
            }else if(schoolCourse.getTimetableAppointments().size()>0&&schoolCourse.getTimetableAppointments().iterator().next().getStatus()==ConstantInterface.TIMETABLE_STATUS_AUDIT_ACCESS){
                eduCourseListVO.setTimetableStatus(ConstantInterface.TIMETABLE_STATUS_AUDIT_ACCESS);
            }else if(schoolCourse.getTimetableAppointments().size()>0&&schoolCourse.getTimetableAppointments().iterator().next().getStatus()==ConstantInterface.TIMETABLE_STATUS_AUDIT_REFUSE){
                eduCourseListVO.setTimetableStatus(ConstantInterface.TIMETABLE_STATUS_AUDIT_REFUSE);
            }
            //获取课程计划
            List<SchoolCourseDetailDTO> schoolCourseDetailDTOs = new ArrayList<SchoolCourseDetailDTO>();
            for(SchoolCourseDetail schoolCourseDetail:schoolCourse.getSchoolCourseDetails()){
                SchoolCourseDetailDTO  schoolCourseDetailDTO = new SchoolCourseDetailDTO();
                //设置课程计划
                schoolCourseDetailDTO.setCoursePlan("星期"+schoolCourseDetail.getWeekday()+" ["+schoolCourseDetail.getStartClass()+"-"+schoolCourseDetail.getEndClass()+"]节" +" ["+schoolCourseDetail.getStartWeek()+"-"+schoolCourseDetail.getEndWeek()+"]周");
                //设置实验室
                schoolCourseDetailDTO.setLabInfo(Objects.nonNull(schoolCourseDetail.getClassroomType())?schoolCourseDetail.getClassroomType():"");
                schoolCourseDetailDTO.setWeekday(Objects.nonNull(schoolCourseDetail.getWeekday())?schoolCourseDetail.getWeekday():0);
                schoolCourseDetailDTO.setStartClass(Objects.nonNull(schoolCourseDetail.getStartClass())?schoolCourseDetail.getStartClass():0);
                schoolCourseDetailDTO.setEndClass(Objects.nonNull(schoolCourseDetail.getEndClass())?schoolCourseDetail.getEndClass():0);
                schoolCourseDetailDTO.setStartWeek(Objects.nonNull(schoolCourseDetail.getStartWeek())?schoolCourseDetail.getStartWeek():0);
                schoolCourseDetailDTO.setEndWeek(Objects.nonNull(schoolCourseDetail.getEndWeek())?schoolCourseDetail.getEndWeek():0);
                eduCourseListVO.setStudent(schoolCourseDetail.getSchoolCourseStudents().size());
                schoolCourseDetailDTOs.add(schoolCourseDetailDTO);
            }
            eduCourseListVO.setSchoolCourseDetailDTOs(schoolCourseDetailDTOs);
            TimetableMergeDTO timetableMergeDTO = new TimetableMergeDTO();
            timetableMergeDTO.setCourseNo(schoolCourse.getCourseNo());
            timetableMergeDTO.setTerm(schoolCourse.getSchoolTerm().getId());
            //增加排课
            eduCourseListVO.setTimetableMergeDTOs(timetableCommonService.apiGetMergeTimetableClassAndWeek(timetableMergeDTO));
            eduCourseListVO.setTimetableDTOs(timetableCommonService.apiTimetableDTOs(schoolCourse.getCourseNo()));
            eduCourseListVOs.add(eduCourseListVO);
        }
        BaseDTO baseVO = new BaseDTO();
        baseVO.setRows(eduCourseListVOs);
        return baseVO;
    }
}