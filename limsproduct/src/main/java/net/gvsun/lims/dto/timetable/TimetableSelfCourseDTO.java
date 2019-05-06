package net.gvsun.lims.dto.timetable;

import java.io.Serializable;

/************************************************************
 * Descriptions：共享库-SchoolCourseDetail的DTO对象
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ************************************************************/
public class TimetableSelfCourseDTO implements Serializable {
    private Integer id;
    //名称
    private String name;
    //课程的编号
    private String courseNumber;
    //自主排课的编号
    private String courseCode;
    //学院的编号
    private String academyNumber;
    //学生人数
    private Integer courseCount;
    //学期
    private Integer term;
    //学生名单
    private String students;
    private String teacher;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public Integer getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(Integer courseCount) {
        this.courseCount = courseCount;
    }

    public String getStudents() {
        return students;
    }

    public void setStudents(String students) {
        this.students = students;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getAcademyNumber() {
        return academyNumber;
    }

    public void setAcademyNumber(String academyNumber) {
        this.academyNumber = academyNumber;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
