package net.gvsun.lims.dto.common;


import java.io.Serializable;
/**
 * 用于存放页面按钮显示的VO
 * Created by huanghao on 2018/8/20.
 */
public class PConfigDTO implements Serializable {

    public String PROJECT_NAME;

    public String annexManage;

    public String softManage;

    public String baseManage;

    public String jobReservation;

    public String deviceLend;

    public String stationNum;

    public String auditServerUrl;

    public String schoolCode;

    public String siteEnName;

    public String siteSecret;

    public String apiGateWayHost;

    public String zuulServerUrl;

    public String authTimetableType;

    public String professorIntroductionUrl;

    public String cmsSiteUrl;

    public String cmsUrl;

    public String backToCms;

    public String cmsAccess;

    public String cms;

    public String schoolName;

    public String userOperation;

    public String showroom;

    public String yuntai;

    public String labAddAdim;

    public String eduDirect;

    public String eduAjust;

    public String eduBatch;

    public String eduNoBatch;

    public String selfBatch;

    public String selfNoBatch;

    public String noREC;

    public String directTimetable;

    public String operationItemName;

    public String refuseTitle;

    public String selfRefuseTitle;

    public String newServer;

    public String virtual;

    public String limsUrl;

    public String advanceCancelTime;

    public String messageServiceUrl;

    public String messageProject;

    public PConfigDTO() {
    }

    public PConfigDTO(String PROJECT_NAME, String annexManage, String softManage, String baseManage, String jobReservation, String deviceLend, String stationNum, String auditServerUrl,
                      String schoolCode, String siteEnName, String siteSecret, String apiGateWayHost, String zuulServerUrl, String authTimetableType, String professorIntroductionUrl,
                      String cmsSiteUrl, String cmsUrl, String backToCms, String cmsAccess, String cms, String schoolName, String userOperation, String showroom, String yuntai,
                      String labAddAdim, String eduDirect, String eduAjust, String eduBatch, String eduNoBatch, String selfBatch, String selfNoBatch, String noREC, String directTimetable,
                      String operationItemName, String refuseTitle, String selfRefuseTitle, String newServer, String virtual, String limsUrl, String advanceCancelTime, String messageServiceUrl,
                      String messageProject) {
        this.PROJECT_NAME = PROJECT_NAME;
        this.annexManage = annexManage;
        this.softManage = softManage;
        this.baseManage = baseManage;
        this.jobReservation = jobReservation;
        this.deviceLend = deviceLend;
        this.stationNum = stationNum;
        this.auditServerUrl = auditServerUrl;
        this.schoolCode = schoolCode;
        this.siteEnName = siteEnName;
        this.siteSecret = siteSecret;
        this.apiGateWayHost = apiGateWayHost;
        this.zuulServerUrl = zuulServerUrl;
        this.authTimetableType = authTimetableType;
        this.professorIntroductionUrl = professorIntroductionUrl;
        this.cmsSiteUrl = cmsSiteUrl;
        this.cmsUrl = cmsUrl;
        this.backToCms = backToCms;
        this.cmsAccess = cmsAccess;
        this.cms = cms;
        this.schoolName = schoolName;
        this.userOperation = userOperation;
        this.showroom = showroom;
        this.yuntai = yuntai;
        this.labAddAdim = labAddAdim;
        this.eduDirect = eduDirect;
        this.eduAjust = eduAjust;
        this.eduBatch = eduBatch;
        this.eduNoBatch = eduNoBatch;
        this.selfBatch = selfBatch;
        this.selfNoBatch = selfNoBatch;
        this.noREC = noREC;
        this.directTimetable = directTimetable;
        this.operationItemName = operationItemName;
        this.refuseTitle = refuseTitle;
        this.selfRefuseTitle = selfRefuseTitle;
        this.newServer = newServer;
        this.virtual = virtual;
        this.limsUrl = limsUrl;
        this.advanceCancelTime = advanceCancelTime;
        this.messageServiceUrl = messageServiceUrl;
        this.messageProject = messageProject;
    }

    public String getPROJECT_NAME() {
        return PROJECT_NAME;
    }

    public void setPROJECT_NAME(String PROJECT_NAME) {
        this.PROJECT_NAME = PROJECT_NAME;
    }

    public String getAnnexManage() {
        return annexManage;
    }

    public void setAnnexManage(String annexManage) {
        this.annexManage = annexManage;
    }

    public String getSoftManage() {
        return softManage;
    }

    public void setSoftManage(String softManage) {
        this.softManage = softManage;
    }

    public String getBaseManage() {
        return baseManage;
    }

    public void setBaseManage(String baseManage) {
        this.baseManage = baseManage;
    }

    public String getJobReservation() {
        return jobReservation;
    }

    public void setJobReservation(String jobReservation) {
        this.jobReservation = jobReservation;
    }

    public String getDeviceLend() {
        return deviceLend;
    }

    public void setDeviceLend(String deviceLend) {
        this.deviceLend = deviceLend;
    }

    public String getStationNum() {
        return stationNum;
    }

    public void setStationNum(String stationNum) {
        this.stationNum = stationNum;
    }

    public String getAuditServerUrl() {
        return auditServerUrl;
    }

    public void setAuditServerUrl(String auditServerUrl) {
        this.auditServerUrl = auditServerUrl;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSiteEnName() {
        return siteEnName;
    }

    public void setSiteEnName(String siteEnName) {
        this.siteEnName = siteEnName;
    }

    public String getSiteSecret() {
        return siteSecret;
    }

    public void setSiteSecret(String siteSecret) {
        this.siteSecret = siteSecret;
    }

    public String getApiGateWayHost() {
        return apiGateWayHost;
    }

    public void setApiGateWayHost(String apiGateWayHost) {
        this.apiGateWayHost = apiGateWayHost;
    }

    public String getZuulServerUrl() {
        return zuulServerUrl;
    }

    public void setZuulServerUrl(String zuulServerUrl) {
        this.zuulServerUrl = zuulServerUrl;
    }

    public String getAuthTimetableType() {
        return authTimetableType;
    }

    public void setAuthTimetableType(String authTimetableType) {
        this.authTimetableType = authTimetableType;
    }

    public String getProfessorIntroductionUrl() {
        return professorIntroductionUrl;
    }

    public void setProfessorIntroductionUrl(String professorIntroductionUrl) {
        this.professorIntroductionUrl = professorIntroductionUrl;
    }

    public String getCmsSiteUrl() {
        return cmsSiteUrl;
    }

    public void setCmsSiteUrl(String cmsSiteUrl) {
        this.cmsSiteUrl = cmsSiteUrl;
    }

    public String getCmsUrl() {
        return cmsUrl;
    }

    public void setCmsUrl(String cmsUrl) {
        this.cmsUrl = cmsUrl;
    }

    public String getBackToCms() {
        return backToCms;
    }

    public void setBackToCms(String backToCms) {
        this.backToCms = backToCms;
    }

    public String getCmsAccess() {
        return cmsAccess;
    }

    public void setCmsAccess(String cmsAccess) {
        this.cmsAccess = cmsAccess;
    }

    public String getCms() {
        return cms;
    }

    public void setCms(String cms) {
        this.cms = cms;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getUserOperation() {
        return userOperation;
    }

    public void setUserOperation(String userOperation) {
        this.userOperation = userOperation;
    }

    public String getShowroom() {
        return showroom;
    }

    public void setShowroom(String showroom) {
        this.showroom = showroom;
    }

    public String getYuntai() {
        return yuntai;
    }

    public void setYuntai(String yuntai) {
        this.yuntai = yuntai;
    }

    public String getLabAddAdim() {
        return labAddAdim;
    }

    public void setLabAddAdim(String labAddAdim) {
        this.labAddAdim = labAddAdim;
    }

    public String getEduDirect() {
        return eduDirect;
    }

    public void setEduDirect(String eduDirect) {
        this.eduDirect = eduDirect;
    }

    public String getEduAjust() {
        return eduAjust;
    }

    public void setEduAjust(String eduAjust) {
        this.eduAjust = eduAjust;
    }

    public String getEduBatch() {
        return eduBatch;
    }

    public void setEduBatch(String eduBatch) {
        this.eduBatch = eduBatch;
    }

    public String getEduNoBatch() {
        return eduNoBatch;
    }

    public void setEduNoBatch(String eduNoBatch) {
        this.eduNoBatch = eduNoBatch;
    }

    public String getSelfBatch() {
        return selfBatch;
    }

    public void setSelfBatch(String selfBatch) {
        this.selfBatch = selfBatch;
    }

    public String getSelfNoBatch() {
        return selfNoBatch;
    }

    public void setSelfNoBatch(String selfNoBatch) {
        this.selfNoBatch = selfNoBatch;
    }

    public String getNoREC() {
        return noREC;
    }

    public void setNoREC(String noREC) {
        this.noREC = noREC;
    }

    public String getDirectTimetable() {
        return directTimetable;
    }

    public void setDirectTimetable(String directTimetable) {
        this.directTimetable = directTimetable;
    }

    public String getOperationItemName() {
        return operationItemName;
    }

    public void setOperationItemName(String operationItemName) {
        this.operationItemName = operationItemName;
    }

    public String getRefuseTitle() {
        return refuseTitle;
    }

    public void setRefuseTitle(String refuseTitle) {
        this.refuseTitle = refuseTitle;
    }

    public String getSelfRefuseTitle() {
        return selfRefuseTitle;
    }

    public void setSelfRefuseTitle(String selfRefuseTitle) {
        this.selfRefuseTitle = selfRefuseTitle;
    }

    public String getNewServer() {
        return newServer;
    }

    public void setNewServer(String newServer) {
        this.newServer = newServer;
    }

    public String getVirtual() {
        return virtual;
    }

    public void setVirtual(String virtual) {
        this.virtual = virtual;
    }

    public String getLimsUrl() {
        return limsUrl;
    }

    public void setLimsUrl(String limsUrl) {
        this.limsUrl = limsUrl;
    }

    public String getAdvanceCancelTime() {
        return advanceCancelTime;
    }

    public void setAdvanceCancelTime(String advanceCancelTime) {
        this.advanceCancelTime = advanceCancelTime;
    }

    public String getMessageServiceUrl() {
        return messageServiceUrl;
    }

    public void setMessageServiceUrl(String messageServiceUrl) {
        this.messageServiceUrl = messageServiceUrl;
    }

    public String getMessageProject() {
        return messageProject;
    }

    public void setMessageProject(String messageProject) {
        this.messageProject = messageProject;
    }

}
