package net.gvsun.lims.dto.supplies;


public class SuppliesApplyDTO {
    //1、申领批次编号
    private String batchNumber;
    //2、申领人
    private String applicant;
    //3、申请类型
    private String type;
    //4、申请单位：学院或部门
    private String applicantCompany;
    //5、申请时间
    private String applicationTime;
    //6、审核状态
    private String auditStatus;
    //7、审核时间
    private String auditTime;
    //8、是否领取
    private  String isReceive;
    //9、是否归还
    private String isReturn;


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

    public String getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(String isReturn) {
        this.isReturn = isReturn;
    }
}
