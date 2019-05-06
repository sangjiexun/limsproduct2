package net.gvsun.lims.vo.reservation;

import net.gvsun.lims.dto.common.BaseActionAuthDTO;
import net.gvsun.lims.dto.school.SchoolCourseDetailDTO;
import net.gvsun.lims.dto.timetable.TimetableDTO;
import net.gvsun.lims.dto.timetable.TimetableMergeDTO;
import net.gvsun.lims.dto.user.UserDTO;

import java.io.Serializable;
import java.util.List;

/************************************************************
 * Descriptions：实验室列表呈现vo
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ************************************************************/
public class LabRoomListVO implements Serializable {
    //实验室编号
    private String id;
    //教学班编号
    private String labRoomName;
    //课程编号
    private String labRoomNumber;
    //课程名称
    private String address;
    //教师工号
    private String username;
    //学院名称
    private String academyName;
    //学院名称
    private String academyNumer;
    //
    private List<UserDTO> managers;

    private BaseActionAuthDTO baseActionAuthDTO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabRoomName() {
        return labRoomName;
    }

    public void setLabRoomName(String labRoomName) {
        this.labRoomName = labRoomName;
    }

    public String getLabRoomNumber() {
        return labRoomNumber;
    }

    public void setLabRoomNumber(String labRoomNumber) {
        this.labRoomNumber = labRoomNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    public String getAcademyNumer() {
        return academyNumer;
    }

    public void setAcademyNumer(String academyNumer) {
        this.academyNumer = academyNumer;
    }

    public List<UserDTO> getManagers() {
        return managers;
    }

    public void setManagers(List<UserDTO> managers) {
        this.managers = managers;
    }

    public BaseActionAuthDTO getBaseActionAuthDTO() {
        return baseActionAuthDTO;
    }

    public void setBaseActionAuthDTO(BaseActionAuthDTO baseActionAuthDTO) {
        this.baseActionAuthDTO = baseActionAuthDTO;
    }
}
