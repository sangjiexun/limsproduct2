package net.gvsun.lims.dto.timetable;
import java.io.Serializable;

public class LabDeviceReservationParamDTO implements Serializable{
    //中心id
    private int cid;
    //设备id
    private int equinemtid;
    //开始时间
    private String startDate;
    //结束时间
    private String endDate;
    //联系电话
    private String phone;
    //描述
    private String description;
    //导师
    private String teacher;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //预约性质
    private int property;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getEquinemtid() {
        return equinemtid;
    }

    public void setEquinemtid(int equinemtid) {
        this.equinemtid = equinemtid;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getProperty() {
        return property;
    }

    public void setProperty(int property) {
        this.property = property;
    }
}

