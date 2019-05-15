package net.gvsun.lims.vo.OpenProjectRelatedReports;

import java.io.Serializable;

public class InstrumentLendingegistrationVO implements Serializable {
    //借出日期
    private String lendingTime;
    //仪器名称及规格
    private String deviceName;
    //数量
    private String number;
    //借用人
    private String lendingUser;
    //归还日期
    private String backTime;
    //归还情况
    private String backStatus;

    public String getLendingTime() {
        return lendingTime;
    }

    public void setLendingTime(String lendingTime) {
        this.lendingTime = lendingTime;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLendingUser() {
        return lendingUser;
    }

    public void setLendingUser(String lendingUser) {
        this.lendingUser = lendingUser;
    }

    public String getBackTime() {
        return backTime;
    }

    public void setBackTime(String backTime) {
        this.backTime = backTime;
    }

    public String getBackStatus() {
        return backStatus;
    }

    public void setBackStatus(String backStatus) {
        this.backStatus = backStatus;
    }
}
