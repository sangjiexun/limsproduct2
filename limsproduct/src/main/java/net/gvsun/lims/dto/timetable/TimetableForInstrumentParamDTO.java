package net.gvsun.lims.dto.timetable;

import java.io.Serializable;
import java.util.Date;

/************************************************************
 * Descriptions：接口服务方法-大仪设备占用查询的参数DTO对象
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ************************************************************/
public class TimetableForInstrumentParamDTO implements Serializable {
    //设备编号（主键）
    private String deviceNumber;
    //授课实验室
    private int labRoomId;
    //欲查询起始时间
    private String startDate;
    //欲查询结束时间
    private String endDate;

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

    public int getLabRoomId() {
        return labRoomId;
    }

    public void setLabRoomId(int labRoomId) {
        this.labRoomId = labRoomId;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }
}
