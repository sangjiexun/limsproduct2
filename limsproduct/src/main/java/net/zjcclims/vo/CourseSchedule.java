package net.zjcclims.vo;


import java.sql.Time;
import java.util.List;
import java.util.Map;

/**
 * Description: 和启动虚拟镜像相关
 *
 * @author 陈敬 2019年3月14日
 */
public class CourseSchedule {
    private Integer timetableAppointmentID;             //课程预约ID
    private String courseNumber;                        //课程ID
    private Integer virtualImageID;                     //虚拟镜像ID
    private Integer term;                               //课程所属学期
    private Integer startWeek;                          //课程开始周
    private Integer endWeek;                            //课程结束周
    private Integer weekday;                            //课程在第几周上课
    private Integer startClass;                         //在第几节上课
    private Integer endClass;                           //在第几节下课
    private Integer desktopNumber;                      //需要启动的虚拟桌面的数量
    private Time startTime;                             //上课时间：HH:mm:ss
    private Time endTime;                               //下课时间：HH:mm:ss

    public Integer getTimetableAppointmentID() {
        return timetableAppointmentID;
    }

    public void setTimetableAppointmentID(Integer timetableAppointmentID) {
        this.timetableAppointmentID = timetableAppointmentID;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public Integer getVirtualImageID() {
        return virtualImageID;
    }

    public void setVirtualImageID(Integer virtualImageID) {
        this.virtualImageID = virtualImageID;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(Integer startWeek) {
        this.startWeek = startWeek;
    }

    public Integer getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(Integer endWeek) {
        this.endWeek = endWeek;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public Integer getStartClass() {
        return startClass;
    }

    public void setStartClass(Integer startClass) {
        this.startClass = startClass;
    }

    public Integer getEndClass() {
        return endClass;
    }

    public void setEndClass(Integer endClass) {
        this.endClass = endClass;
    }

    public Integer getDesktopNumber() {
        return desktopNumber;
    }

    public void setDesktopNumber(Integer desktopNumber) {
        this.desktopNumber = desktopNumber;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
