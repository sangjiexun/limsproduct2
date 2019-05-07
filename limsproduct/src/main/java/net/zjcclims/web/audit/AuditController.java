package net.zjcclims.web.audit;

/***************************************************************************
 * Description 审核页面相关方法
 * @author 陈乐为 2018-9-12
 ***************************************************************************/

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.audit.AuditSaveParamDTO;
import net.zjcclims.dao.AuthorityDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabRoomReservationService;
import net.zjcclims.util.HttpClientUtil;
import net.zjcclims.web.common.PConfig;
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
    private PConfig pConfig;
    @Autowired
    private AuthorityDAO authorityDAO;
    @Autowired
    private LabRoomReservationService labRoomReservationService;

    @RequestMapping("/auditList")
    public ModelAndView auditList(HttpServletRequest request, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        String businessType = request.getParameter("businessType");
        String businessUid = request.getParameter("businessUid");
        String businessAppUid = request.getParameter("businessAppUid");
        // 获取审核状态
        Integer curStage = -2;
        String curAuthName = "";
        Map<String, String> params = new HashMap<>();
        params.put("businessType", pConfig.PROJECT_NAME + businessType);
        params.put("businessUid", businessUid);
        params.put("businessAppUid", businessAppUid);
        String currStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", params);
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
        String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessLevelStatus", params);
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
                    os[5] = curAuthName;
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
            params.put("businessType", pConfig.PROJECT_NAME + businessType);
            params.put("businessUid", businessUid);
            params.put("businessAppUid", businessAppUid);
            String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessLevelStatus", params);
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
                case "StationReservation":
                case "1StationReservation":
                case "2StationReservation":
                    isAuditUser = labRoomReservationService.getNextAuditUser(curAuthName, businessAppUid);
                    break;
                default:
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
        params.put("businessType", pConfig.PROJECT_NAME + auditSaveParamDTO.getBusinessType());
        params.put("businessAppUid", auditSaveParamDTO.getBusinessAppUid());
        params.put("businessUid", auditSaveParamDTO.getBusinessUid());
        params.put("result", auditSaveParamDTO.getAuditResult() == 1 ? "pass" : "fail");
        params.put("info", auditSaveParamDTO.getRemark());
        params.put("username", shareService.getUserDetail().getUsername());
        String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveBusinessLevelAudit", params);

        String nextAuthName = "";
        JSONObject jsonObject = JSONObject.parseObject(s);
        if ("success".equals(jsonObject.getString("status"))) {
            JSONObject resultJSONObject = jsonObject.getJSONObject("data");
            nextAuthName = (String) resultJSONObject.values().iterator().next();
        }
        return nextAuthName;
    }

}
