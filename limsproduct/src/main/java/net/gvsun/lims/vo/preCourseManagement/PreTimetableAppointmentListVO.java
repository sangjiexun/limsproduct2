package net.gvsun.lims.vo.preCourseManagement;

import java.io.Serializable;
import java.util.List;

/****************************************************************************
 * @描述： 预排课记录
 * @作者 ：张德冰
 * @时间： 2018-12-24
 ****************************************************************************/
public class PreTimetableAppointmentListVO implements Serializable {
    //编号
    private Integer id;
    //课程名称
    private String courseName;
    //上课人数
    private Integer stuNumber;
    //授课安排
    private List<PreTimetableScheduleVO> preTimetableScheduleVO;
    //授课教师
    private String teacher;
    //指导教师
    private String tutor;
    //预排课房间
    private List<PreLabRoomListVO> preLabRoomListVO;
    //学期
    private String termName;
    //所属学院
    private String academyName;
    //布局类型
    private String roomType;
    //软件
    private String sofeware;
    //发布状态
    private String state;
    //操作权限
    private Integer isAudit;

    public List<PreLabRoomListVO> getPreLabRoomListVO() {
        return preLabRoomListVO;
    }

    public void setPreLabRoomListVO(List<PreLabRoomListVO> preLabRoomListVO) {
        this.preLabRoomListVO = preLabRoomListVO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(Integer stuNumber) {
        this.stuNumber = stuNumber;
    }

    public List<PreTimetableScheduleVO> getPreTimetableScheduleVO() {
        return preTimetableScheduleVO;
    }

    public void setPreTimetableScheduleVO(List<PreTimetableScheduleVO> preTimetableScheduleVO) {
        this.preTimetableScheduleVO = preTimetableScheduleVO;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getSofeware() {
        return sofeware;
    }

    public void setSofeware(String sofeware) {
        this.sofeware = sofeware;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(Integer isAudit) {
        this.isAudit = isAudit;
    }
}
