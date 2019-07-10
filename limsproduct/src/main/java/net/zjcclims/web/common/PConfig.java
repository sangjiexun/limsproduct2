package net.zjcclims.web.common;

import net.gvsun.web.util.AuthorizationUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Administrator on 2018/7/2.
 */
@Component("pConfig")
public class PConfig {
    //版本名称
    @Value("${project.name}")
    public String PROJECT_NAME;

    // 导航栏显示配置

    @Value("${annexManage}")
    public String annexManage;
    @Value("${softManage}")
    public String softManage;
    @Value("${baseManage}")
    public String baseManage;
    @Value("${jobReservation}")
    public String jobReservation;
    @Value("${deviceLend}")
    public String deviceLend;
    @Value("${stationNum}")
    public String stationNum;
    @Value("${auditServerUrl}")
    public String auditServerUrl;
    @Value("${school.code}")
    public String schoolCode;
    @Value("${siteEnName}")
    public String siteEnName;
    @Value("${siteSecret}")
    public String siteSecret;
    @Value("${apiGateWayHost}")
    public String apiGateWayHost;
    @Value("${zuulServerUrl}")
    public String zuulServerUrl;
    @Value("${authTimetableType}")
    public String authTimetableType;
    @Value("${professorIntroductionUrl}")
    public String professorIntroductionUrl;
    @Value("${cmsSiteUrl}")
    public String cmsSiteUrl;
    @Value("${cmsUrl}")
    public String cmsUrl;
    @Value("${backToCms}")
    public String backToCms;
    @Value("${cmsAccess}")
    public String cmsAccess;
    @Value("${cms}")
    public String cms;
    @Value("${school.name}")
    public String schoolName;
    @Value("${userOperation}")
    public String userOperation;
    @Value("${showroom}")
    public String showroom;
    @Value("${yuntai}")
    public String yuntai;
    @Value("${labAddAdim}")
    public String labAddAdim;
    @Value("${eduDirect}")
    public String eduDirect;
    @Value("${eduAjust}")
    public String eduAjust;
    @Value("${eduBatch}")
    public String eduBatch;
    @Value("${eduNoBatch}")
    public String eduNoBatch;
    @Value("${selfBatch}")
    public String selfBatch;
    @Value("${selfNoBatch}")
    public String selfNoBatch;
    @Value("${noREC}")
    public String noREC;
    @Value("${directTimetable}")
    public String directTimetable;
    @Value("${operationItemName}")
    public String operationItemName;
    @Value("${refuseTitle}")
    public String refuseTitle;
    @Value("${selfRefuseTitle}")
    public String selfRefuseTitle;
    @Value("${newServer}")
    public String newServer;
    @Value("${virtual}")
    public String virtual;
    @Value("${limsUrl}")
    public String limsUrl;
    @Value("${advanceCancelTime}")
    public String advanceCancelTime;
    @Value("${messageServiceUrl}")
    public String messageServiceUrl;
    @Value("${messageProject}")
    public String messageProject;

    @PostConstruct
    public void getApiToken() {
        AuthorizationUtil.setApiGateWayHost(apiGateWayHost);
        AuthorizationUtil.setSiteEnName(siteEnName);
        AuthorizationUtil.setSiteSecret(siteSecret);
    }
}
