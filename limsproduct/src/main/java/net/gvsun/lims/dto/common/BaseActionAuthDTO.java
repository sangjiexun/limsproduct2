package net.gvsun.lims.dto.common;

import java.io.Serializable;

/**
 * 用于存放页面按钮显示的VO
 * Created by huanghao on 2018/8/20.
 */
public class BaseActionAuthDTO implements Serializable {
    //获取查看按钮权限
    private boolean searchActionAuth;
    //获取删除按钮权限
    private boolean deleteActionAuth;
    //获取增加按钮权限
    private boolean addActionAuth;
    //获取修改按钮权限
    private boolean updateActionAuth;
    //获取学生选课按钮权限
    private boolean selectGroupActionAuth;
    //获取发布按钮权限
    private boolean publicActionAuth;
    //获取跨学院查询权限
    private boolean CrossAcademyActionAuth;

    // 获取审核按钮权限
    private boolean auditActionAuth;
    //权限范围
    private String authRange;

    public boolean isSelectGroupActionAuth() {
        return selectGroupActionAuth;
    }

    public void setSelectGroupActionAuth(boolean selectGroupActionAuth) {
        this.selectGroupActionAuth = selectGroupActionAuth;
    }

    public boolean isSearchActionAuth() {
        return searchActionAuth;
    }

    public void setSearchActionAuth(boolean searchActionAuth) {
        this.searchActionAuth = searchActionAuth;
    }

    public boolean isDeleteActionAuth() {
        return deleteActionAuth;
    }

    public void setDeleteActionAuth(boolean deleteActionAuth) {
        this.deleteActionAuth = deleteActionAuth;
    }

    public boolean isAddActionAuth() {
        return addActionAuth;
    }

    public void setAddActionAuth(boolean addActionAuth) {
        this.addActionAuth = addActionAuth;
    }

    public boolean isUpdateActionAuth() {
        return updateActionAuth;
    }

    public void setUpdateActionAuth(boolean updateActionAuth) {
        this.updateActionAuth = updateActionAuth;
    }

    public String getAuthRange() {
        return authRange;
    }

    public boolean isPublicActionAuth() {
        return publicActionAuth;
    }

    public void setPublicActionAuth(boolean publicActionAuth) {
        this.publicActionAuth = publicActionAuth;
    }

    public void setAuthRange(String authRange) {
        this.authRange = authRange;
    }

    public boolean isCrossAcademyActionAuth() {
        return CrossAcademyActionAuth;
    }

    public void setCrossAcademyActionAuth(boolean crossAcademyActionAuth) {
        CrossAcademyActionAuth = crossAcademyActionAuth;
    }

    public boolean isAuditActionAuth() {
        return auditActionAuth;
    }

    public void setAuditActionAuth(boolean auditActionAuth) {
        this.auditActionAuth = auditActionAuth;
    }

    public void copy(BaseActionAuthDTO that){
        this.setAuditActionAuth(that.isAuditActionAuth());
        this.setSearchActionAuth(that.isSearchActionAuth());
        this.setAddActionAuth(that.isAddActionAuth());
        this.setDeleteActionAuth(that.isDeleteActionAuth());
        this.setUpdateActionAuth(that.isUpdateActionAuth());
        this.setCrossAcademyActionAuth(that.isCrossAcademyActionAuth());
        this.setPublicActionAuth(that.isPublicActionAuth());
        this.setSelectGroupActionAuth(that.isSelectGroupActionAuth());
        this.setAuthRange(that.getAuthRange());
    }
}
