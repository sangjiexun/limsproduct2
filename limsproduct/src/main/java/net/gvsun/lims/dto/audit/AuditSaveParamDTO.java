package net.gvsun.lims.dto.audit;

/**
 * 保存审核传参DTO
 */
public class AuditSaveParamDTO {

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 业务主体id
     */
    private String businessUid;

    /**
     * 业务id
     */
    private String businessAppUid;

    /**
     * 业务当前所处阶段
     */
    private Integer curStage;

    /**
     * 业务当前所处阶段
     */
    private Integer auditResult;

    /**
     * 业务当前所处阶段
     */
    private String remark;

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessUid() {
        return businessUid;
    }

    public void setBusinessUid(String businessUid) {
        this.businessUid = businessUid;
    }

    public String getBusinessAppUid() {
        return businessAppUid;
    }

    public void setBusinessAppUid(String businessAppUid) {
        this.businessAppUid = businessAppUid;
    }

    public Integer getCurStage() {
        return curStage;
    }

    public void setCurStage(Integer curStage) {
        this.curStage = curStage;
    }

    public Integer getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(Integer auditResult) {
        this.auditResult = auditResult;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
