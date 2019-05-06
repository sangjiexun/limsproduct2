package net.gvsun.lims.dto.school;

import java.io.Serializable;

/************************************************************
 * Descriptions：共享库-SchoolCourseDetail的DTO对象
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ************************************************************/
public class SchoolCourseDetailDTO implements Serializable {
    //schoolCourse的编号
    private String courseNo;
    //教学班编号
    private String courseDetailNo;
    //课程计划
    private String coursePlan;
    private int startClass;
    private int weekday;
    private int endClass;
    private int startWeek;
    //教学班编号
    private int endWeek;
    //地点
    private String labInfo;
    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(String labInfo) {
        this.labInfo = labInfo;
    }

    public String getCourseDetailNo() {
        return courseDetailNo;
    }

    public void setCourseDetailNo(String courseDetailNo) {
        this.courseDetailNo = courseDetailNo;
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

    public String getCoursePlan() {
        return coursePlan;
    }

    public void setCoursePlan(String coursePlan) {
        this.coursePlan = coursePlan;
    }
}
