package net.gvsun.lims.vo.timtable.engineer;

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
public class NewEduCourseVO implements Serializable {
    //直接排课的教学班编号
    private String courseNo;
    //直接排课的教学班编号
    private String courseDetailNo;
    private int weekday;
    private int[] classes;
    private int[] weeks;
    //所选实验室
    private String[] labRoomIds;
    //所选教师
    private String[] tearchs;
    //所选指导教师
    private String[] tutors;

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String[] getLabRoomIds() {
        return labRoomIds;
    }

    public void setLabRoomIds(String[] labRoomIds) {
        this.labRoomIds = labRoomIds;
    }

    public String[] getTearchs() {
        return tearchs;
    }

    public void setTearchs(String[] tearchs) {
        this.tearchs = tearchs;
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

    public void setWeeks(int[] weeks) {
        this.weeks = weeks;
    }
}
