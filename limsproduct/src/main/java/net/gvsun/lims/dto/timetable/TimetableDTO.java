package net.gvsun.lims.dto.timetable;

import java.io.Serializable;
import java.util.Date;

/************************************************************
 * Descriptions：共享库-SchoolCourseDetail的DTO对象
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ************************************************************/
public class TimetableDTO implements Serializable {
    private int id;
    private int sameNumberId;
    //schoolCourse的编号
    private String courseNo;
    //自主排课的编号
    private String selfId;
    //自主排课的编号
    private Integer batchId;
    //自主排课的编号
    private String batchName;
    //自主排课的分组编号
    private Integer groupId;
    //自主排课的分组名称
    private String groupName;
    //自主排课的分组计划人数
    private Integer groupNumbers;
    //自主排课的分组实际人数
    private Integer groupStudents;
    //教学班编号
    private String courseDetailNo;
    //时间安排
    private String timetable;
    private int startClass;
    private int weekday;
    private int endClass;
    private int startWeek;
    private int endWeek;
    private Date startDate;
    private Date endDate;
    // 调课状态
    private Integer adjustStatus;
    // 排课状态
    private Integer status;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    //授课实验室
    private String labs;
    //授课教师
    private String teachers;
    //指导教师
    private String tutors;
    //实验项目
    private String items;
    //课程信息
    private String courseName;
    private String courseNumber;
    //学期
    private String termName;
    //
    private String labInfo;
    public String getCourseNo() {
        return courseNo;
    }

    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    public int getId() {
        return id;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public int getSameNumberId() {
        return sameNumberId;
    }

    public void setSameNumberId(int sameNumberId) {
        this.sameNumberId = sameNumberId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupNumbers() {
        return groupNumbers;
    }

    public void setGroupNumbers(Integer groupNumbers) {
        this.groupNumbers = groupNumbers;
    }

    public Integer getGroupStudents() {
        return groupStudents;
    }

    public void setGroupStudents(Integer groupStudents) {
        this.groupStudents = groupStudents;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getCourseDetailNo() {
        return courseDetailNo;
    }

    public void setCourseDetailNo(String courseDetailNo) {
        this.courseDetailNo = courseDetailNo;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getTimetable() {
        return timetable;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }

    public String getLabs() {
        return labs;
    }

    public int getStartClass() {
        return startClass;
    }

    public void setStartClass(int startClass) {
        this.startClass = startClass;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public int getEndClass() {
        return endClass;
    }

    public void setEndClass(int endClass) {
        this.endClass = endClass;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public void setLabs(String labs) {
        this.labs = labs;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public String getTutors() {
        return tutors;
    }

    public void setTutors(String tutors) {
        this.tutors = tutors;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(String labInfo) {
        this.labInfo = labInfo;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public Integer getAdjustStatus() {
        return adjustStatus;
    }

    public void setAdjustStatus(Integer adjustStatus) {
        this.adjustStatus = adjustStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
