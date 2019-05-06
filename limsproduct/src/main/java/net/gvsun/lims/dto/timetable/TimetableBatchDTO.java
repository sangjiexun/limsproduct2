package net.gvsun.lims.dto.timetable;

import net.gvsun.lims.dto.common.BaseActionAuthDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/************************************************************
 * Descriptions：直接排课-列表呈现vo
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ************************************************************/
public class TimetableBatchDTO implements Serializable {
    private int id;
    //教学班编号
    private String courseNo;
    //批次名称，默认为第1批等...
    private String batchName;
    //分组数
    private int countGroup;
    //是否学生选课0自动选课1学生选课
    private int ifselect;
    //每组人数
    private int numbers;
    private int flag;
    private int selfId;
    private List<TimetableGroupDTO> timetableGroupDTOs;
    private Date startDate;
    private Date endDate;

    public String getCourseNo() {
        return courseNo;
    }

    public int getSelfId() {
        return selfId;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }

    public void setSelfId(int selfId) {
        this.selfId = selfId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public int getCountGroup() {
        return countGroup;
    }

    public void setCountGroup(int countGroup) {
        this.countGroup = countGroup;
    }

    public int getIfselect() {
        return ifselect;
    }

    public void setIfselect(int ifselect) {
        this.ifselect = ifselect;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<TimetableGroupDTO> getTimetableGroupDTOs() {
        return timetableGroupDTOs;
    }

    public void setTimetableGroupDTOs(List<TimetableGroupDTO> timetableGroupDTOs) {
        this.timetableGroupDTOs = timetableGroupDTOs;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
