package net.gvsun.lims.service.school;

import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.school.SchoolCourseStudentDTO;
import net.zjcclims.dao.SchoolCourseStudentDAO;
import net.zjcclims.domain.SchoolCourseStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("SchoolCourseStudentService")
public class SchoolCourseStudentServiceImpl implements SchoolCourseStudentService {
    @Autowired
    private SchoolCourseStudentDAO schoolCourseStudentDAO;

    /*************************************************************************************
     * Description:教务课程-获取课程库列表
     *
     * @author： 魏誠
     * @date：2018-08-03
     *************************************************************************************/
    public BaseDTO apiSchoolCourseStudentList(int termId, String courseNo, String sort, String order) {
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select c from SchoolCourseStudent c where  c.schoolTerm.id=" + termId);
        //查询条件
        if (courseNo != null && !"".equals(courseNo)) {
            sql.append(" and (c.schoolCourseDetail.schoolCourse like '" + courseNo + "')");
        }
        sql.append(" group by c.userByStudentNumber.username ");
        //排序
        if ("courseNo".equals(sort)) {
            sql.append(" order by c.c.schoolCourseDetail.schoolCourse." + sort +" " +order);
        }else if ("courseName".equals(sort)) {
            sql.append(" order by c.schoolCourseDetail." + sort +" " +order);
        }else if ("username".equals(sort)) {
            sql.append(" order by c.userByStudentNumber." + sort +" " +order);
        }else if ("teacherNumber".equals(sort)) {
            sql.append(" order by c.userByTeacherNumber." + sort +" " +order);
        }

        // 执行sb语句
        List<SchoolCourseStudent> schoolCourseStudents = schoolCourseStudentDAO.executeQuery(sql.toString(), 0,-1);

        int total = schoolCourseStudents.size();
        //封装VO
        List<SchoolCourseStudentDTO> schoolCourseStudentDTOs = new ArrayList<SchoolCourseStudentDTO>();
        for (SchoolCourseStudent schoolCourseStudent : schoolCourseStudents) {
            SchoolCourseStudentDTO schoolCourseStudentDTO = new SchoolCourseStudentDTO();
            //设置courseCode
            if(Objects.nonNull(schoolCourseStudent.getUserByStudentNumber().getSchoolClasses())) {
                schoolCourseStudentDTO.setCourseNo(schoolCourseStudent.getSchoolCourseDetail().getSchoolCourse().getCourseNo()+" \n " +schoolCourseStudent.getUserByStudentNumber().getSchoolClasses().getClassName()+ "");
            }else {
                schoolCourseStudentDTO.setCourseNo(schoolCourseStudent.getSchoolCourseDetail().getSchoolCourse().getCourseNo());
            }
            //设置课程名称
            schoolCourseStudentDTO.setCourseName(schoolCourseStudent.getSchoolCourseDetail().getCourseName());
            //设置课程编号
            schoolCourseStudentDTO.setCourseNumber(schoolCourseStudent.getSchoolCourseDetail().getCourseNumber());
            //设置学期
            schoolCourseStudentDTO.setTermName(schoolCourseStudent.getSchoolTerm().getTermName());
            schoolCourseStudentDTO.setTermId(schoolCourseStudent.getSchoolTerm().getId());
            //设置学院
            schoolCourseStudentDTO.setAcademyName(schoolCourseStudent.getSchoolAcademy().getAcademyName());
            //设置学院
            schoolCourseStudentDTO.setAcademyNumber(schoolCourseStudent.getSchoolAcademy().getAcademyNumber());
            //设置学生信息
            schoolCourseStudentDTO.setCname(schoolCourseStudent.getUserByStudentNumber().getCname());
            schoolCourseStudentDTO.setUsername(schoolCourseStudent.getUserByStudentNumber().getUsername());
            //设置上课教师
            if(Objects.nonNull(schoolCourseStudent.getUserByTeacherNumber())){
                schoolCourseStudentDTO.setTeacherName(schoolCourseStudent.getUserByTeacherNumber().getCname());
                schoolCourseStudentDTO.setTeacherNumber(schoolCourseStudent.getUserByTeacherNumber().getUsername());
            }

            schoolCourseStudentDTOs.add(schoolCourseStudentDTO);
        }
        BaseDTO baseVO = new BaseDTO();
        baseVO.setRows(schoolCourseStudentDTOs);
        baseVO.setTotal(total);
        return baseVO;
    }

}