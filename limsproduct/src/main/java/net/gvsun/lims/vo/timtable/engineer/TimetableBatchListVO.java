package net.gvsun.lims.vo.timtable.engineer;

import net.gvsun.lims.dto.common.BaseActionAuthDTO;
import net.gvsun.lims.dto.school.SchoolCourseDetailDTO;
import net.gvsun.lims.dto.timetable.TimetableDTO;
import net.gvsun.lims.dto.timetable.TimetableGroupDTO;
import org.python.antlr.op.In;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/************************************************************
 * Descriptions：直接排课-列表呈现vo
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ************************************************************/
public class TimetableBatchListVO implements Serializable {
    private int id;
    //教学班编号
    private String courseNo;
    private Integer selfId;
    //批次名称，默认为第1批等...
    private String batchName;
    //分组数
    private int countGroup;
    //当前学生是否已选择
    private int selected;
    //是否学生选课0自动选课1学生选课
    private int ifselect;
    //排课情况1：排课完成0：未完成
    private int flag;
    private List<TimetableGroupDTO> timetableGroupDTOs;
    private Date startDate;
    private Date endDate;
    private BaseActionAuthDTO baseActionAuthDTO;

    public String getCourseNo() {
        return courseNo;
    }

    public int getId() {
        return id;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public Integer getSelfId() {
        return selfId;
    }

    public void setSelfId(Integer selfId) {
        this.selfId = selfId;
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

    public BaseActionAuthDTO getBaseActionAuthDTO() {
        return baseActionAuthDTO;
    }

    public void setBaseActionAuthDTO(BaseActionAuthDTO baseActionAuthDTO) {
        this.baseActionAuthDTO = baseActionAuthDTO;
    }
}
