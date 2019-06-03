package net.gvsun.lims.dto.labConstruction;

import java.math.BigDecimal;

/**
 * 子项目信息显示DTO
 */
public class SonProjectDTO {

    /**
     * 项目id
     */
    private Integer id;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 所属父项目名称
     */
    private String parentProjectName;

    /**
     * 所属父项目id
     */
    private Integer parentProjectId;

    /**
     * 所属学院
     */
    private String academyName;

    /**
     * 所属学院编号
     */
    private String[] academyNumbers;

    /**
     * 所属学院编号
     */
    private String academyNumber;

    /**
     * 创建人工号
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
     * 预算结算时间
     */
    private String budgetBalanceTime;

    /**
     * 项目实施时间
     */
    private String projectImplementTime;

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

    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    public String getAcademyNumber() {
        return academyNumber;
    }

    public void setAcademyNumber(String academyNumber) {
        this.academyNumber = academyNumber;
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

    public String getBudgetBalanceTime() {
        return budgetBalanceTime;
    }

    public void setBudgetBalanceTime(String budgetBalanceTime) {
        this.budgetBalanceTime = budgetBalanceTime;
    }

    public String getProjectImplementTime() {
        return projectImplementTime;
    }

    public void setProjectImplementTime(String projectImplementTime) {
        this.projectImplementTime = projectImplementTime;
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

    public String[] getAcademyNumbers() {
        return academyNumbers;
    }

    public void setAcademyNumbers(String[] academyNumbers) {
        this.academyNumbers = academyNumbers;
    }
}
