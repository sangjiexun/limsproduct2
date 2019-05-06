package net.gvsun.lims.vo.preCourseManagement;

import java.io.Serializable;

/****************************************************************************
 * @描述： 排课时间
 * @作者 ：张德冰
 * @时间： 2018-12-24
 ****************************************************************************/
public class PreTimetableScheduleVO implements Serializable {
    //编号
    private Integer id;
    //开始周
    private Integer startWeek;
    //结束周
    private Integer endWeek;
    //起始星期
    private Integer startWeekDay;
    //结束星期
    private Integer endWeekDay;
    //起始节次
    private Integer startClass;
    //结束节次
    private Integer endClass;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getStartWeekDay() {
        return startWeekDay;
    }

    public void setStartWeekDay(Integer startWeekDay) {
        this.startWeekDay = startWeekDay;
    }

    public Integer getEndWeekDay() {
        return endWeekDay;
    }

    public void setEndWeekDay(Integer endWeekDay) {
        this.endWeekDay = endWeekDay;
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
}
