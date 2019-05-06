package net.gvsun.lims.dto.timetable;

import net.gvsun.lims.dto.common.BaseActionAuthDTO;
import net.gvsun.lims.dto.user.UserDTO;

import java.io.Serializable;
import java.util.List;

/************************************************************
 * Descriptions：共享库-SchoolCourseDetail的DTO对象
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ************************************************************/
public class TimetableGroupStudentDTO implements Serializable {
    //主键
    private int groupId;
    //外键对应批的主键
    private List<UserDTO> userDTOs;
    //当前组的人数
    private TimetableGroupDTO timetableGroupDTO;
    //当前组的人数
    private int groupNumber;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public TimetableGroupDTO getTimetableGroupDTO() {
        return timetableGroupDTO;
    }

    public void setTimetableGroupDTO(TimetableGroupDTO timetableGroupDTO) {
        this.timetableGroupDTO = timetableGroupDTO;
    }

    public List<UserDTO> getUserDTOs() {
        return userDTOs;
    }

    public void setUserDTOs(List<UserDTO> userDTOs) {
        this.userDTOs = userDTOs;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }
}
