package net.gvsun.lims.dto.timetable;

import java.io.Serializable;
import java.util.Date;

/************************************************************
 * Descriptions：数据传输对象-排课的周次节次及星期描述方式的DTO对象
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ************************************************************/
public class TimetableDateFormatDTO implements Serializable {

    private int weekday;

    private int Week;

    private int startClass;

    private int endClass;

    private Date date;

    public int getStartClass() {
        return startClass;
    }

    public void setStartClass(int startClass) {
        this.startClass = startClass;
    }

    public int getEndClass() {
        return endClass;
    }

    public void setEndClass(int endClass) {
        this.endClass = endClass;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public int getWeek() {
        return Week;
    }

    public void setWeek(int week) {
        Week = week;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
