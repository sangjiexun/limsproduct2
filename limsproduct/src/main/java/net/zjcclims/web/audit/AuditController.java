package net.zjcclims.web.audit;

/***************************************************************************
 * Description 审核页面相关方法
 * @author 陈乐为 2018-9-12
 ***************************************************************************/

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.audit.AuditSaveParamDTO;
import net.gvsun.lims.dto.common.PConfigDTO;
import net.zjcclims.dao.AuthorityDAO;
import net.zjcclims.dao.LabRoomDAO;
import net.zjcclims.dao.LabRoomStationReservationDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabRoomLendingService;
import net.zjcclims.service.lab.LabRoomReservationService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller("AuditController")
@SessionAttributes({"selected_academy", "isAudit"})
@RequestMapping("/auditing")

public class AuditController<JsonResult> {
    @Autowired
    private ShareService shareService;
    @Autowired
    private AuthorityDAO authorityDAO;
    @Autowired
    private LabRoomDAO labRoomDAO;
    @Autowired
    private LabRoomReservationService labRoomReservationService;
    @Autowired
    private LabRoomLendingService labRoomLendingService;
    @Autowired
    private LabRoomStationReservationDAO labRoomStationReservationDAO;
    @Autowired private LabRoomService labRoomService;

    @RequestMapping("/auditList")
    public ModelAndView auditList(HttpServletRequest request, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        String businessUid = request.getParameter("businessUid");
        String businessAppUid = request.getParameter("businessAppUid");
        String businessType= "";
        if(labRoomDAO.findLabRoomById(Integer.valueOf(businessUid))!=null){
            LabRoom labRoom = labRoomDAO.findLabRoomById(Integer.valueOf(businessUid));
            businessType = pConfig.PROJECT_NAME + "StationReservation" + (labRoom.getLabCenter() == null ? "-1" : labRoom.getLabCenter().getSchoolAcademy().getAcademyNumber());
        }
        if(request.getParameter("businessType")!=null){
            businessType = pConfig.PROJECT_NAME + request.getParameter("businessType");
        }
        // 获取审核状态
        Integer curStage = -2;
        String curAuthName = "";
        Map<String, String> params = new HashMap<>();
        if(shareService.getSerialNumber(businessAppUid, businessType)=="fail"){
            //没有流水单号的旧数据就用预约id用作业务id
           //do nothing
        }else{
            //有流水单号用流水单号做业务id
            businessAppUid = shareService.getSerialNumber(businessAppUid, businessType);
        }
        params.put("businessType", businessType);
        params.put("businessUid", businessUid);
        params.put("businessAppUid", businessAppUid);
        String currStr = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/getCurrAuditStage", params);
        JSONObject currJSONObject = JSONObject.parseObject(currStr);
        if ("success".equals(currJSONObject.getString("status"))) {
            JSONArray currArray = currJSONObject.getJSONArray("data");
            if (currArray != null && currArray.size() > 0) {
                JSONObject o = currArray.getJSONObject(0);
                curStage = o.getIntValue("level");
                curAuthName = o.getString("result");
            }
        }

        // 获取审核配置
        String s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/getBusinessLevelStatus", params);
        JSONObject jsonObject = JSONObject.parseObject(s);
        List<Object[]> auditItems = new ArrayList<>();
        if ("success".equals(jsonObject.getString("status"))) {
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray != null && jsonArray.size() > 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    Object[] os = new Object[6];
                    os[0] = authorityDAO.findAuthorityByAuthorityName(o.getString("authName")).iterator().next().getCname();
                    os[1] = o.getIntValue("level");
                    os[2] = o.getString("authName");
                    os[3] = businessAppUid;
                    os[4] = curStage;
                    os[5] = o.getString("authName");
                    auditItems.add(os);
                }
                mav.addObject("count", jsonArray.size());
            }
        }
        mav.addObject("businessAppUid", businessAppUid);
        mav.addObject("businessType", businessType);
        mav.addObject("businessUid", businessUid);
        mav.addObject("auditConfigs", auditItems);
        mav.setViewName("audit/auditList.jsp");
        return mav;
    }

    /**
     * Description 审核子页面
     */
    @RequestMapping("/auditSingle")
    public ModelAndView auditSingle(HttpServletRequest request, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        Integer isAudit = 0;
        Integer state = 0;
        if(request.getParameter("state") != null){
            state = Integer.parseInt(request.getParameter("state"));
        }
        Integer curStage = 0;
        if(request.getParameter("curStage") != null){
            curStage = Integer.parseInt(request.getParameter("curStage"));
        }
        String businessAppUid = request.getParameter("businessAppUid");
        String businessUid = request.getParameter("businessUid");
        String businessType = request.getParameter("businessType");
        String curAuthName = request.getParameter("curAuthName");
        String authName = request.getParameter("authName");
        mav.addObject("state", state);
        mav.addObject("curStage", curStage);
        mav.addObject("businessAppUid", businessAppUid);
        if (state < curStage || curStage == 0 || curStage == -1) {
            // 获取审核配置
            Map<String, String> params = new HashMap<>();
            params.put("businessType",businessType);
            params.put("businessUid", businessUid);
            params.put("businessAppUid", businessAppUid);
            String s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/getBusinessLevelStatus", params);
            JSONObject jsonObject = JSONObject.parseObject(s);
            if ("success".equals(jsonObject.getString("status"))) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                if (jsonArray != null && jsonArray.size() > 0) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject o = jsonArray.getJSONObject(i);
                        Integer oi = o.getIntValue("level");
                        if (oi.equals(state)) {
                            Object[] userInfo = new Object[4];
                            userInfo[0] = shareService.findUserByUsername(o.getString("auditUser"));
                            userInfo[1] = o.getString("result");
                            userInfo[2] = o.getString("info");
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                            try {
                                userInfo[3] = sdf.parse(o.getString("createTime"));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            mav.addObject("userInfo", userInfo);
                        }
                    }
                    mav.addObject("count", jsonArray.size());
                }
            }
        } else {
            List<User> isAuditUser = new ArrayList<>();
            // 判断业务类型分别取审核人
            switch (businessType){
 //               case "StationReservation":
//                case "1StationReservation":
//                case "2StationReservation":
//                    isAuditUser = labRoomReservationService.getNextAuditUser(authName, businessAppUid);
                case "CancelLabRoomReservation":
                    isAuditUser = labRoomLendingService.getNextUsers(authName, businessAppUid);
                    break;
                default:
                    isAuditUser = labRoomReservationService.getNextAuditUser(authName, businessAppUid,businessType);
            }
            // 如果当前登录人的权限是当前审核权限，则判断是否是审核人并且是否当前页面权限是当前审核权限
            if (request.getSession().getAttribute("selected_role").equals("ROLE_" + curAuthName)) {
                for (User eUser : isAuditUser) {
                    if (eUser.getUsername().equals(shareService.getUserDetail().getUsername())
                            && curAuthName.equals(authName)) {
                        // 赋予审核权限
                        isAudit = 1;
                        break;
                    }
                }
            }
            // 如果没有审核权限，则显示所有审核人
            if (isAudit != 1) {
                mav.addObject("isAuditUser", isAuditUser);
            }
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("businessType", businessType);
        mav.addObject("businessUid", businessUid);
        mav.addObject("businessAppUid", businessAppUid);
        mav.setViewName("audit/auditSingle.jsp");
        return mav;
    }


    /**
     * Description 保存审核
     */
    @RequestMapping("/saveAudit")
    @ResponseBody
    public String saveAudit(@RequestBody AuditSaveParamDTO auditSaveParamDTO) {
        Map<String, String> params = new HashMap<>();
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        params.put("businessType", auditSaveParamDTO.getBusinessType());
        params.put("businessAppUid", auditSaveParamDTO.getBusinessAppUid());
        params.put("businessUid", auditSaveParamDTO.getBusinessUid());
        params.put("result", auditSaveParamDTO.getAuditResult() == 1 ? "pass" : "fail");
        params.put("info", auditSaveParamDTO.getRemark());
        params.put("username", shareService.getUserDetail().getUsername());
        String s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/saveBusinessLevelAudit", params);

        String nextAuthName = "";
        JSONObject jsonObject = JSONObject.parseObject(s);
        if ("success".equals(jsonObject.getString("status"))) {
            JSONObject resultJSONObject = jsonObject.getJSONObject("data");
            nextAuthName = (String) resultJSONObject.values().iterator().next();
        }
        //若是工位预约
        if(auditSaveParamDTO.getBusinessType().contains("StationReservation")){
            String businessAppUid = auditSaveParamDTO.getBusinessAppUid();
            if(shareService.getReservationIdBySerialNumber(auditSaveParamDTO.getBusinessAppUid(),auditSaveParamDTO.getBusinessType())=="fail"){
                //没有流水单号
                //do nothing
            }else {
                //有流水单号反查预约id
                businessAppUid = shareService.getReservationIdBySerialNumber(auditSaveParamDTO.getBusinessAppUid(),auditSaveParamDTO.getBusinessType());
            }
            LabRoomStationReservation labRoomStationReservation =
                    labRoomStationReservationDAO.findLabRoomStationReservationById(Integer.valueOf(businessAppUid));
            if(nextAuthName.equals("pass")){   //审核通过，设置该条预约记录的状态值为审核通过
                if(auditSaveParamDTO.getBusinessType().contains("CancelLabRoomStationReservation")){  //取消预约审核通过
                    //备份流程记录 并删除相关记录
                    //type 2为取消预约
                    labRoomReservationService.obsoleteLabStationReservation(labRoomStationReservation.getId(),2);
                }else {
                    labRoomStationReservation.setResult(1);
                    labRoomStationReservation.setState(6);
                    labRoomStationReservationDAO.store(labRoomStationReservation);
                    labRoomStationReservationDAO.flush();
                }
                // 判断当天预约--下发权限
                Boolean bln = shareService.theSameDay(labRoomStationReservation.getReservation().getTime());
                // 如果当前日期和预约日期相同即同一天，则向物联发送刷新权限请求
                if(bln) {
                    labRoomService.sendAgentInfoTodayToIOT(labRoomStationReservation.getLabRoom().getId());
                }
            }else if(nextAuthName.equals("fail")){         //审核拒绝
                labRoomStationReservation.setResult(4);
                labRoomStationReservationDAO.store(labRoomStationReservation);
                labRoomStationReservationDAO.flush();
            }else {
                if(auditSaveParamDTO.getBusinessType().contains("CancelLabRoomStationReservation")){
                    //工位预约取消审核 do nothing
                }else {
                    labRoomStationReservation.setResult(2);        //审核中
                    labRoomStationReservationDAO.store(labRoomStationReservation);
                    labRoomStationReservationDAO.flush();
                }
            }
        }

        return nextAuthName;
    }

}
