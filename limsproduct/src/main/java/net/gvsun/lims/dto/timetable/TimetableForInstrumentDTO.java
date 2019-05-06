package net.gvsun.lims.dto.timetable;

import java.io.Serializable;
import java.util.Date;

/************************************************************
 * Descriptions：共享库-SchoolCourseDetail的DTO对象
 *
 * @作者：魏诚
 * @时间：2018-11-20
 ************************************************************/
public class TimetableForInstrumentDTO implements Serializable {
    //排课及预约占用开始时间
    private String startDate;
    //排课及预约占用结束时间
    private String endDate;
    //说明
    private String description;
    //设备编号
    private String deviceNumber;
    //实验室编号
    private String labRoomId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getLabRoomId() {
        return labRoomId;
    }

    public void setLabRoomId(String labRoomId) {
        this.labRoomId = labRoomId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
