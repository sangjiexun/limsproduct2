package net.gvsun.lims.vo.timtable.common;

import net.gvsun.lims.dto.timetable.TimetableDTO;

import java.io.Serializable;
import java.util.List;

/************************************************************
 * Descriptions：共享库-SchoolCourseDetail的DTO对象
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ************************************************************/
public class ViewTimetableVO implements Serializable {
    //教务教学班编号
    private String courseNo;
    //自主教学班编号
    private String selfId;
    //timetable
    private List<TimetableDTO> timetables;
    //课程编号
    private String courseNumber;
    //课程名称
    private String courseName;
    //状态
    private int status;
    //类型
    private int timetableStyle;

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    public List<TimetableDTO> getTimetables() {
        return timetables;
    }

    public void setTimetables(List<TimetableDTO> timetables) {
        this.timetables = timetables;
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

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTimetableStyle() {
        return timetableStyle;
    }

    public void setTimetableStyle(int timetableStyle) {
        this.timetableStyle = timetableStyle;
    }
}
