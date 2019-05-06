package net.gvsun.lims.dto.timetable;

import net.gvsun.lims.dto.common.BaseActionAuthDTO;

import java.io.Serializable;
import java.util.List;

/************************************************************
 * Descriptions：共享库-SchoolCourseDetail的DTO对象
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ************************************************************/
public class TimetableGroupDTO implements Serializable {
    //主键
    private int id;
    //外键对应批的主键
    private int batchId;
    //组的人数
    private int groupNumber;
    //实际分配的人数
    private int groupStudentNumbers;
    private int timetableId;
    private String timetable;
    //当前学生是否已选择
    private int selected;
    private List<TimetableDTO> timetables;
    //组名
    private String groupName;
    private BaseActionAuthDTO baseActionAuthDTO;
    public List<TimetableDTO> getTimetables() {
        return timetables;
    }

    public int getGroupStudentNumbers() {
        return groupStudentNumbers;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public void setGroupStudentNumbers(int groupStudentNumbers) {
        this.groupStudentNumbers = groupStudentNumbers;
    }

    public void setTimetables(List<TimetableDTO> timetables) {
        this.timetables = timetables;
    }

    public int getId() {
        return id;
    }

    public BaseActionAuthDTO getBaseActionAuthDTO() {
        return baseActionAuthDTO;
    }

    public void setBaseActionAuthDTO(BaseActionAuthDTO baseActionAuthDTO) {
        this.baseActionAuthDTO = baseActionAuthDTO;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public int getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(int timetableId) {
        this.timetableId = timetableId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getTimetable() {
        return timetable;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
