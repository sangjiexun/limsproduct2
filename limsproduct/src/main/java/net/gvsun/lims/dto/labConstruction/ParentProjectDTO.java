package net.gvsun.lims.dto.labConstruction;

import java.math.BigDecimal;
import java.util.List;

/**
 * 父项目信息显示DTO
 */
public class ParentProjectDTO {

    /**
     * 项目id
     */
    private Integer id;

    /**
     * 项目名称
     */
    private String projectName;

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
     * 子项目集
     */
    private List<SonProjectDTO> sonProjects;

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

    public List<SonProjectDTO> getSonProjects() {
        return sonProjects;
    }

    public void setSonProjects(List<SonProjectDTO> sonProjects) {
        this.sonProjects = sonProjects;
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
