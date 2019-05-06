package net.gvsun.lims.vo.timtable.engineer;

import net.gvsun.lims.dto.common.BaseActionAuthDTO;
import net.gvsun.lims.dto.school.SchoolCourseDetailDTO;
import net.gvsun.lims.dto.timetable.TimetableBatchDTO;
import net.gvsun.lims.dto.timetable.TimetableDTO;
import net.gvsun.lims.dto.timetable.TimetableMergeDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/************************************************************
 * Descriptions：直接排课-列表呈现vo
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ************************************************************/
public class EduCourseListVO implements Serializable {
    //教学班编号
    private String courseNo;
    //教学班编号
    private Integer selfId;
    //课程编号
    private String courseNumber;
    //课程名称
    private String courseName;
    //教师工号
    private String username;
    //教师名称
    private String cname;
    //是否选课
    private int isSelect;
    //学期名称
    private String termName;
    //学期院名称
    private String academyName;
    //班级信息
    private String classInfo;
    private int termId;
    private List<SchoolCourseDetailDTO> schoolCourseDetailDTOs;
    private List<TimetableDTO> timetableDTOs;
    private List<TimetableMergeDTO> timetableMergeDTOs;
    //学生数量
    private int student;
    private int timetableStatus;
    private int timetableStyle;
    private BaseActionAuthDTO baseActionAuthDTO;

    public Integer getSelfId() {
        return selfId;
    }

    public void setSelfId(Integer selfId) {
        this.selfId = selfId;
    }

    public int getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(int isSelect) {
        this.isSelect = isSelect;
    }

    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    public int getTimetableStyle() {
        return timetableStyle;
    }

    public void setTimetableStyle(int timetableStyle) {
        this.timetableStyle = timetableStyle;
    }

    public List<TimetableDTO> getTimetableDTOs() {
        return timetableDTOs;
    }

    public void setTimetableDTOs(List<TimetableDTO> timetableDTOs) {
        this.timetableDTOs = timetableDTOs;
    }

    public int getTimetableStatus() {
        return timetableStatus;
    }

    public void setTimetableStatus(int timetableStatus) {
        this.timetableStatus = timetableStatus;
    }

    public int getTermId() {
        return termId;
    }

    public List<TimetableMergeDTO> getTimetableMergeDTOs() {
        return timetableMergeDTOs;
    }

    public void setTimetableMergeDTOs(List<TimetableMergeDTO> timetableMergeDTOs) {
        this.timetableMergeDTOs = timetableMergeDTOs;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public int getStudent() {
        return student;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public List<SchoolCourseDetailDTO> getSchoolCourseDetailDTOs() {
        return schoolCourseDetailDTOs;
    }

    public void setSchoolCourseDetailDTOs(List<SchoolCourseDetailDTO> schoolCourseDetailDTOs) {
        this.schoolCourseDetailDTOs = schoolCourseDetailDTOs;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }

    public void setStudent(int student) {
        this.student = student;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setIsTimetable(boolean isTimetable) {
        isTimetable = isTimetable;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public BaseActionAuthDTO getBaseActionAuthDTO() {
        return baseActionAuthDTO;
    }

    public void setBaseActionAuthDTO(BaseActionAuthDTO baseActionAuthDTO) {
        this.baseActionAuthDTO = baseActionAuthDTO;
    }
}
