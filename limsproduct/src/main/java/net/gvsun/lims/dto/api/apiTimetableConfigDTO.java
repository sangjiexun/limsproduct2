package net.gvsun.lims.dto.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * 用于存放timetable微服务配置德dto
 * Created by huanghao on 2018/8/20.
 */
public class apiTimetableConfigDTO implements Serializable {
    //项目名称
    private String projectName;
    //审核微服务地址
    private String auditServerUrl;
    //排课预约微服务地址
    private String authTimetableType;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAuditServerUrl() {
        return auditServerUrl;
    }

    public void setAuditServerUrl(String auditServerUrl) {
        this.auditServerUrl = auditServerUrl;
    }

    public String getAuthTimetableType() {
        return authTimetableType;
    }

    public void setAuthTimetableType(String authTimetableType) {
        this.authTimetableType = authTimetableType;
    }
}
