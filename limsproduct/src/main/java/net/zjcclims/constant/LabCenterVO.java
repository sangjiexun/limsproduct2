package net.zjcclims.constant;

import java.math.BigDecimal;

public class LabCenterVO {
    private int id;
    private String centerName;
    private String centerNo;
    private String centerAdmin;
    private String campus;
    private String academy;
    private BigDecimal area;
    private int adminNum;
    private int labNum;
    private int deviceNum;
    private BigDecimal devicePrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCenterNo() {
        return centerNo;
    }

    public void setCenterNo(String centerNo) {
        this.centerNo = centerNo;
    }

    public String getCenterAdmin() {
        return centerAdmin;
    }

    public void setCenterAdmin(String centerAdmin) {
        this.centerAdmin = centerAdmin;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public int getAdminNum() {
        return adminNum;
    }

    public void setAdminNum(int adminNum) {
        this.adminNum = adminNum;
    }

    public int getLabNum() {
        return labNum;
    }

    public void setLabNum(int labNum) {
        this.labNum = labNum;
    }

    public int getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(int deviceNum) {
        this.deviceNum = deviceNum;
    }

    public BigDecimal getDevicePrice() {
        return devicePrice;
    }

    public void setDevicePrice(BigDecimal devicePrice) {
        this.devicePrice = devicePrice;
    }
}