package net.gvsun.lims.dto.supplies;

import java.util.List;

public class SuppliesApplyDetailsDTO {
    //1、申领批次编号
    private String batchNumber;
    //2、申请类型
    private String type;
    //3、申请单位：学院或部门
    private String applicantCompany;
    //4、申请人
    private String applicant;
    //5、申请时间
    private String applicationTime;
    //6、审核状态
    private String auditStatus;
    //7、审核时间
    private String auditTime;
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
    private String suppliesCategory;
    //16、申请物资
    private List<SuppliesApplyGoodsInformationDTO> goodsInformationDTOList;
    //17、总价
    private Float totalPrice;

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApplicantCompany() {
        return applicantCompany;
    }

    public void setApplicantCompany(String applicantCompany) {
        this.applicantCompany = applicantCompany;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
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

    public String getSuppliesCategory() {
        return suppliesCategory;
    }

    public void setSuppliesCategory(String suppliesCategory) {
        this.suppliesCategory = suppliesCategory;
    }

    public List<SuppliesApplyGoodsInformationDTO> getGoodsInformationDTOList() {
        return goodsInformationDTOList;
    }

    public void setGoodsInformationDTOList(List<SuppliesApplyGoodsInformationDTO> goodsInformationDTOList) {
        this.goodsInformationDTOList = goodsInformationDTOList;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
