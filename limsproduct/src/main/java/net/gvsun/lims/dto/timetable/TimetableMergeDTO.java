package net.gvsun.lims.dto.timetable;

import java.io.Serializable;

/************************************************************
 * Descriptions：排课数据DTO-获取排课的周次节次
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ************************************************************/
public class TimetableMergeDTO implements Serializable {
    private int timetableId;
    //schoolCourse的编号
    private String courseNo;
    private int startClass;
    private int weekday;
    private int endClass;
    private int startWeek;
    private int endWeek;
    //学期
    private int term;
    //学期
    private String timetableInfo;
    //实验室
    private int lab;
    private String labInfo;
    public int getTimetableId() {
        return timetableId;
    }

    public int getLab() {
        return lab;
    }

    public void setLab(int lab) {
        this.lab = lab;
    }

    public void setTimetableId(int timetableId) {
        this.timetableId = timetableId;
    }

    public String getLabInfo() {
        return labInfo;
    }

    public String getTimetableInfo() {
        return timetableInfo;
    }

    public void setTimetableInfo(String timetableInfo) {
        this.timetableInfo = timetableInfo;
    }

    public void setLabInfo(String labInfo) {
        this.labInfo = labInfo;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
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

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }
}
