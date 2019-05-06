package net.gvsun.lims.dto.timetable;

import java.io.Serializable;

/************************************************************
 * Descriptions：共享库-SchoolCourseStudent的DTO对象
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ************************************************************/
public class TimetableCourseStudentDTO implements Serializable {
    //教学班编号
    private int id;

    private int selfId;
    //学生姓名
    private String cname;
    //学生学号
    private String username;
    //课程名称
    private String courseName;
    //课程名称
    private String courseNumber;
    //老师工号
    private String teacherNumber;
    //老师姓名
    private String teacherName;
    //所属学院
    private String academyName;
    //所属学院
    private String academyNumber;
    //所属学期
    private String termName;
    private int termId;
    // 行政班级
    private String className;

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public int getId() {
        return id;
    }

    public int getSelfId() {
        return selfId;
    }

    public void setSelfId(int selfId) {
        this.selfId = selfId;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    public String getAcademyNumber() {
        return academyNumber;
    }

    public void setAcademyNumber(String academyNumber) {
        this.academyNumber = academyNumber;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
