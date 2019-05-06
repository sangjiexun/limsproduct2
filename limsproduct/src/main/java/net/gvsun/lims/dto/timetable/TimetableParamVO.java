package net.gvsun.lims.dto.timetable;

import net.gvsun.lims.dto.common.SelectDTO;
import net.gvsun.lims.dto.school.SchoolCourseDetailDTO;

import java.io.Serializable;
import java.util.List;

/************************************************************
 * Descriptions：直接排课-保存传参对象
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ************************************************************/
public class TimetableParamVO implements Serializable {
    //直接排课的教学班编号
    private String courseNo;
    //直接排课的教学班编号
    private String courseDetailNo;
    //自主排课id
    private int selfId;
    private int timetableId;
    private int sameNumberId;
    private int term;
    private int status;
    private int timetableStyle;
    private int weekday;
    private int groupId;
    //所选实验室
    private int[] labRoomIds;
    private int[] classes;
    private int[] weeks;
    //所选教师
    private String[] tearchs;
    //所选指导教师
    private String[] tutors;
    private int[] items;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int[] getItems() {
        return items;
    }

    public void setItems(int[] items) {
        this.items = items;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getSelfId() {
        return selfId;
    }

    public void setSelfId(int selfId) {
        this.selfId = selfId;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public int[] getLabRoomIds() {
        return labRoomIds;
    }

    public void setLabRoomIds(int[] labRoomIds) {
        this.labRoomIds = labRoomIds;
    }

    public int getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(int timetableId) {
        this.timetableId = timetableId;
    }

    public String[] getTearchs() {
        return tearchs;
    }

    public void setTearchs(String[] tearchs) {
        this.tearchs = tearchs;
    }

    public int getSameNumberId() {
        return sameNumberId;
    }

    public void setSameNumberId(int sameNumberId) {
        this.sameNumberId = sameNumberId;
    }

    public String[] getTutors() {
        return tutors;
    }

    public void setTutors(String[] tutors) {
        this.tutors = tutors;
    }

    public String getCourseDetailNo() {
        return courseDetailNo;
    }

    public void setCourseDetailNo(String courseDetailNo) {
        this.courseDetailNo = courseDetailNo;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public int[] getClasses() {
        return classes;
    }

    public void setClasses(int[] classes) {
        this.classes = classes;
    }

    public int[] getWeeks() {
        return weeks;
    }

    public int getTimetableStyle() {
        return timetableStyle;
    }

    public void setTimetableStyle(int timetableStyle) {
        this.timetableStyle = timetableStyle;
    }

    public void setWeeks(int[] weeks) {
        this.weeks = weeks;
    }
}
