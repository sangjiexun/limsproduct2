package net.gvsun.lims.dto.assets;
/**
*物资分类DTO
*/
public class MaterialKindDTO<T> {
    //编号
    private Integer id;
    //中文名称
    private String cname;
    //英文名称
    private String ename;
    //备注
    private String info;
    //是否归还
    private String isNeedReturn;
    //申领审核
    private String applyAudit;
    private Integer applyAuditLevel;
    //入库审核
    private String storageAudit;
    private Integer storageAuditLevel;
    //领用审核
    private String receiveAudit;
    private Integer receiveAuditLevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getIsNeedReturn() {
        return isNeedReturn;
    }

    public void setIsNeedReturn(String isNeedReturn) {
        this.isNeedReturn = isNeedReturn;
    }

    public String getApplyAudit() {
        return applyAudit;
    }

    public void setApplyAudit(String applyAudit) {
        this.applyAudit = applyAudit;
    }

    public String getStorageAudit() {
        return storageAudit;
    }

    public void setStorageAudit(String storageAudit) {
        this.storageAudit = storageAudit;
    }

    public String getReceiveAudit() {
        return receiveAudit;
    }

    public void setReceiveAudit(String receiveAudit) {
        this.receiveAudit = receiveAudit;
    }

    public Integer getApplyAuditLevel() {
        return applyAuditLevel;
    }

    public void setApplyAuditLevel(Integer applyAuditLevel) {
        this.applyAuditLevel = applyAuditLevel;
    }

    public Integer getStorageAuditLevel() {
        return storageAuditLevel;
    }

    public void setStorageAuditLevel(Integer storageAuditLevel) {
        this.storageAuditLevel = storageAuditLevel;
    }

    public Integer getReceiveAuditLevel() {
        return receiveAuditLevel;
    }

    public void setReceiveAuditLevel(Integer receiveAuditLevel) {
        this.receiveAuditLevel = receiveAuditLevel;
    }
}
