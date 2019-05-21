package net.gvsun.lims.dto.assets;

import net.gvsun.lims.dto.supplies.SuppliesApplyGoodsInformationDTO;

import java.util.List;

public class AssetsReceiveDTO {
    private String id;
    //1、申领批次编号
    private String batchNumber;
    //2、申请单位：学院
    private String academyNumber;
    //3、申请人
    private String username;
    //4、部门
    private String department;
    //5、申请时间
    private String applicationTime;
    //6、开始使用时间
    private String beginTime;
    //7、结束使用时间
    private String endTime;
    //8、是否领取
    private  String isReceive;
    //9、领取人
    private String receiver;
    //10、领取时间
    private String receiveTime;
    //11、是否归还
    private String isReturn;
    //12、归还时间
    private String returnTime;
    //13、用途
    private String purpose;
    //14、备注
    private String remarks;
    //15、物资类型
    private String goodsCategory;
    //16、审核时间
    private String auditDate;
    //17、当前状态
    private String status;
    //18、是否需要归还
    private Integer isNeedReturn;
    //18、file
    private String file;
    //15.当前审核层级
    private String curAuditLevel;
    //15.审核标志位
    private Integer auditFlag;
    //15.流程发起标志位(0代表审核人 1代表发起人)
    private Integer appFlag;
    //15.拒绝原因
    private String rejectReason;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getAcademyNumber() {
        return academyNumber;
    }

    public void setAcademyNumber(String academyNumber) {
        this.academyNumber = academyNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getIsReceive() {
        return isReceive;
    }

    public void setIsReceive(String isReceive) {
        this.isReceive = isReceive;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(String isReturn) {
        this.isReturn = isReturn;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(String goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIsNeedReturn() {
        return isNeedReturn;
    }

    public void setIsNeedReturn(Integer isNeedReturn) {
        this.isNeedReturn = isNeedReturn;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getCurAuditLevel() {
        return curAuditLevel;
    }

    public void setCurAuditLevel(String curAuditLevel) {
        this.curAuditLevel = curAuditLevel;
    }

    public Integer getAuditFlag() {
        return auditFlag;
    }

    public void setAuditFlag(Integer auditFlag) {
        this.auditFlag = auditFlag;
    }

    public Integer getAppFlag() {
        return appFlag;
    }

    public void setAppFlag(Integer appFlag) {
        this.appFlag = appFlag;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
