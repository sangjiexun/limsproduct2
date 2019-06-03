package net.gvsun.lims.dto.labConstruction;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 孙项目显示DTO
 */
public class GrandSonProjectDTO {

    /**
     * 孙项目id
     */
    private Integer id;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 所属子项目名称
     */
    private String sonProjectName;

    /**
     * 所属子项目id
     */
    private Integer sonProjectId;

    /**
     * 所属父项目名称
     */
    private String parentProjectName;

    /**
     * 所属父项目id
     */
    private Integer parentProjectId;

    /**
     * 项目进度具体信息
     */
    private String progress;

    /**
     * 项目进度阶段
     */
    private Integer progressStage;

    /**
     * 项目进度状态
     */
    private Integer progressState;

    /**
     * 所属学院编号
     */
    private String username;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建人所属部门
     */
    private String unitName;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 经费预算
     */
    private BigDecimal budget;

    /**
     * 项目实施时间
     */
    private String projectImplementTime;

    /**
     * 招标纪要实际金额
     */
    private BigDecimal tenderActualAmount;

    /**
     * 验收实际金额
     */
    private BigDecimal acceptanceActualAmount;

    /**
     * 结余
     */
    private BigDecimal restAmount;

    /**
     * 相关文档
     */
    private Map<String, String> relatedDocuments;

    /**
     * 论证报告
     */
    private Map<String, String> reportDocuments;

    /**
     * 采购文件
     */
    private Map<String, String> purchaseDocuments;

    /**
     * 相关合同
     */
    private Map<String, String> relatedContracts;

    /**
     * 验收申请表
     */
    private Map<String, String> acceptanceDocuments;

    /**
     * 流程线
     */
    private List<Object[]> processLine;

    /**
     * 相关文件
     */
    private String documents;

    /**
     * 提交状态
     */
    private int submitted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSonProjectName() {
        return sonProjectName;
    }

    public void setSonProjectName(String sonProjectName) {
        this.sonProjectName = sonProjectName;
    }

    public Integer getSonProjectId() {
        return sonProjectId;
    }

    public void setSonProjectId(Integer sonProjectId) {
        this.sonProjectId = sonProjectId;
    }

    public String getParentProjectName() {
        return parentProjectName;
    }

    public void setParentProjectName(String parentProjectName) {
        this.parentProjectName = parentProjectName;
    }

    public Integer getParentProjectId() {
        return parentProjectId;
    }

    public void setParentProjectId(Integer parentProjectId) {
        this.parentProjectId = parentProjectId;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public Integer getProgressStage() {
        return progressStage;
    }

    public void setProgressStage(Integer progressStage) {
        this.progressStage = progressStage;
    }

    public Integer getProgressState() {
        return progressState;
    }

    public void setProgressState(Integer progressState) {
        this.progressState = progressState;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getProjectImplementTime() {
        return projectImplementTime;
    }

    public void setProjectImplementTime(String projectImplementTime) {
        this.projectImplementTime = projectImplementTime;
    }

    public BigDecimal getTenderActualAmount() {
        return tenderActualAmount;
    }

    public void setTenderActualAmount(BigDecimal tenderActualAmount) {
        this.tenderActualAmount = tenderActualAmount;
    }

    public BigDecimal getAcceptanceActualAmount() {
        return acceptanceActualAmount;
    }

    public void setAcceptanceActualAmount(BigDecimal acceptanceActualAmount) {
        this.acceptanceActualAmount = acceptanceActualAmount;
    }

    public BigDecimal getRestAmount() {
        return restAmount;
    }

    public void setRestAmount(BigDecimal restAmount) {
        this.restAmount = restAmount;
    }

    public Map<String, String> getRelatedDocuments() {
        if(relatedDocuments == null){
            relatedDocuments = new HashMap<>();
        }
        return relatedDocuments;
    }

    public void setRelatedDocuments(Map<String, String> relatedDocuments) {
        this.relatedDocuments = relatedDocuments;
    }

    public Map<String, String> getReportDocuments() {
        if(reportDocuments == null){
            reportDocuments = new HashMap<>();
        }
        return reportDocuments;
    }

    public void setReportDocuments(Map<String, String> reportDocuments) {
        this.reportDocuments = reportDocuments;
    }

    public Map<String, String> getPurchaseDocuments() {
        if(purchaseDocuments == null){
            purchaseDocuments = new HashMap<>();
        }
        return purchaseDocuments;
    }

    public void setPurchaseDocuments(Map<String, String> purchaseDocuments) {
        this.purchaseDocuments = purchaseDocuments;
    }

    public Map<String, String> getRelatedContracts() {
        if(relatedContracts == null){
            relatedContracts = new HashMap<>();
        }
        return relatedContracts;
    }

    public void setRelatedContracts(Map<String, String> relatedContracts) {
        this.relatedContracts = relatedContracts;
    }

    public Map<String, String> getAcceptanceDocuments() {
        if(acceptanceDocuments == null){
            acceptanceDocuments = new HashMap<>();
        }
        return acceptanceDocuments;
    }

    public void setAcceptanceDocuments(Map<String, String> acceptanceDocuments) {
        this.acceptanceDocuments = acceptanceDocuments;
    }

    public List<Object[]> getProcessLine() {
        return processLine;
    }

    public void setProcessLine(List<Object[]> processLine) {
        this.processLine = processLine;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public int getSubmitted() {
        return submitted;
    }

    public void setSubmitted(int submitted) {
        this.submitted = submitted;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
