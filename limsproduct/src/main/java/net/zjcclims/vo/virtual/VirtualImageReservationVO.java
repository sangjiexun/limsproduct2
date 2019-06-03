package net.zjcclims.vo.virtual;


import java.util.Calendar;

/*************************************************************************************
 * Description:虚拟镜像预约VO
 *
 * @author: 杨新蔚
 * @date: 2019/5/29
 *************************************************************************************/
public class VirtualImageReservationVO {
    //预约id
    private Integer virtualImageReservationID;
    //预约镜像名称
    private String virtualImageName;
    //预约人姓名
    private String userName;
    //预约开始时间
    private Calendar startTime;
    //预约开始时间后15分钟（用于显示判断）
    private Calendar startFifteenTime;
    //预约结束时间
    private Calendar endTime;
    //预约备注
    private String remarks;
    //预约审核层级
    private Integer auditStage;
    //预约镜像账号
    private String imageAccount;

    public Integer getVirtualImageReservationID() {
        return virtualImageReservationID;
    }

    public void setVirtualImageReservationID(Integer virtualImageReservationID) {
        this.virtualImageReservationID = virtualImageReservationID;
    }

    public String getVirtualImageName() {
        return virtualImageName;
    }

    public void setVirtualImageName(String virtualImageName) {
        this.virtualImageName = virtualImageName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getAuditStage() {
        return auditStage;
    }

    public void setAuditStage(Integer auditStage) {
        this.auditStage = auditStage;
    }

    public Calendar getStartFifteenTime() {
        return startFifteenTime;
    }

    public void setStartFifteenTime(Calendar startFifteenTime) {
        this.startFifteenTime = startFifteenTime;
    }

    public String getImageAccount() {
        return imageAccount;
    }

    public void setImageAccount(String imageAccount) {
        this.imageAccount = imageAccount;
    }
}
