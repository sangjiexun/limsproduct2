package net.zjcclims.web.audit;

/***************************************************************************
 * Description 系统权限设置相关方法
 * @author 陈乐为 2018-9-12
 ***************************************************************************/

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.zjcclims.dao.AuthorityDAO;
import net.zjcclims.domain.Authority;
import net.zjcclims.domain.SchoolAcademy;
import net.zjcclims.domain.User;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.util.HttpClientUtil;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

@Controller("AuditSettingController")
@SessionAttributes({"selected_academy", "isAudit"})
@RequestMapping("/audit")

public class AuditSettingController<JsonResult> {
    @Autowired
    private ShareService shareService;
    @Autowired
    private PConfig pConfig;
    @Autowired
    private AuthorityDAO authorityDAO;

    /**
     * Description 获取模块权限设置
     * @return
     * @author 陈乐为 2018-9-12
     */
    @RequestMapping("/auditSetting")
    public String auditSetting(HttpServletRequest request, Map<String, Object> map, @RequestParam int flag,
                               @ModelAttribute("selected_academy") String acno) {
        User user = shareService.getUserDetail();
        map.put("user", user);
        // 项目名
        String projectName = pConfig.PROJECT_NAME;
        String businessType = "";
        String businessName = "";
        Integer allType = 1;
        if(flag == 1){// 排课发布
//            businessType = projectName + "TimetableAudit" + acno;// 每个学院各自设置，暂时隐藏
            businessName = "TimetableAudit";
            businessType = projectName + businessName;
        }else if(flag == 2) {// 实验室预约
            businessName = "LabRoomReservation";
            businessType = projectName + businessName;
            allType = 0;
        }else if(flag == 3) {
            businessName = "OperationItem";
            businessType = projectName + businessName;
        }else if(flag == 4) {
            businessName = "DeviceRepair";
            businessType = projectName + businessName;
            // 获取所有权限
            Set<Authority> authorities = authorityDAO.findAllAuthoritys();
            map.put("authorities", authorities);
            // 获取原有的确认权限
            Map<String, String> params = new HashMap<>();
            // 获取原有的验收权限
            params.put("businessType", projectName + "RepairAcceptance");
            String res = HttpClientUtil.doPost(pConfig.auditServerUrl+"audit/getBusinessAuditConfigLevel", params);
            JSONObject j = JSONObject.parseObject(res);
            if(j.getJSONArray("data").size() != 0) {
                map.put("acceptanceVal", j.getJSONArray("data").getJSONObject(0).getString("authId"));
            }
            // 获取原有的编写权限
            params.put("businessType", projectName + "RepairWrite");
            res = HttpClientUtil.doPost(pConfig.auditServerUrl+"audit/getBusinessAuditConfigLevel", params);
            j = JSONObject.parseObject(res);
            if(j.getJSONArray("data").size() != 0) {
                map.put("writeVal", j.getJSONArray("data").getJSONObject(0).getString("authId"));
            }
            // 获取原有的入账权限
            params.put("businessType", projectName + "RepairRecord");
            res = HttpClientUtil.doPost(pConfig.auditServerUrl+"audit/getBusinessAuditConfigLevel", params);
            j = JSONObject.parseObject(res);
            if(j.getJSONArray("data").size() != 0) {
                map.put("recordVal", j.getJSONArray("data").getJSONObject(0).getString("authId"));
            }
        }else if(flag == 5){
            businessName = "DeviceReservation";
            businessType = projectName + businessName;
            allType = 0;
        }else if(flag == 6){
            businessName = "VirtualImageReservation";
            businessType = projectName + businessName;
            allType = 1;
        }else if(flag == 7) {// 新理工排课-自主排课发布
            businessName = "SelfTimetableAudit";
            businessType = projectName + businessName;
        }else if(flag == 8) {// 微服务排课-调课审核
            businessName = "AdjustTimetableAudit";
            businessType = projectName + businessName;
            allType = 1;
        }else if(flag == 9) {// 新理工排课-停课审核
            businessName = "CloseTimetableAudit";
            businessType = projectName + businessName;
            allType = 1;
        }else if(flag == 10) {// 实验项目（新）
            businessName = "OperationItemNewAudit";
            businessType = projectName + businessName;
            allType = 1;
        }else if(flag == 11) {// 工位预约
            // 获取是否开启分级--工位预约每个实验室设置
//            boolean isGraded = shareService.getAuditOrNot("LabRoomStationGradedOrNot");
//            String grade = "";
//            if(isGraded){
//                grade = request.getParameter("grade") == null ? "1" : request.getParameter("grade");
//            }
//            map.put("isGraded", isGraded);
//            map.put("grade", grade);
//
//            businessName = grade + "StationReservation";
            businessName = "StationReservation";
            businessType = projectName + businessName;
            //allType = 1;
            allType = 0;
        }else if(flag == 12){
            businessName = "CancelLabRoomReservation";
            businessType = projectName + businessName;
            allType = 1;
        }
        map.put("businessName", businessName);
        map.put("allType", allType);
//        else if(flag == 2){// 项目审核
//            businessType = projectName + "Item" + projectName;
//        }else if(flag == 3){// 设备维修
//            businessType = projectName + "Test" + projectName;
//        }else if(flag == 4){// 耗材维修
//            businessType = projectName + "Test" + projectName;
//        }
        Map<String, String> params = new HashMap<>();
        params.put("businessType",businessType);
        String s = HttpClientUtil.doPost(pConfig.auditServerUrl+"audit/getBusinessAuditConfigLevelContaining", params);
        JSONObject jsonObject = JSON.parseObject(s);
        JSONArray auditConfig = jsonObject.getJSONArray("data");
        List<String> auditConfigLevel = new ArrayList<>();

        // 获取学院
        List<SchoolAcademy> schoolAcademyList = shareService.findAllSchoolAcademys();
        List<SchoolAcademy> selectedAcno = new ArrayList<>();
        Map<String, Object[]> temp = new LinkedHashMap<>();

        for(int i=0; i < auditConfig.size(); i++){
            JSONObject audit = JSON.parseObject(auditConfig.get(i).toString());
            String myAcno = audit.getString("type").replace(businessType, "");
            SchoolAcademy myAcademy = shareService.findSchoolAcademyByPrimaryKey(myAcno);
            if(myAcademy != null || "-1".equals(myAcno) || "".equals(myAcno)){
                String authId = audit.getString("authId");
                Authority auth = authorityDAO.findAuthorityByAuthorityName(authId).iterator().next();
                Object[] objects = new Object[3];
                if(temp.containsKey(myAcno)){
                    objects = temp.get(myAcno);
                    objects[1] = (Integer)objects[1] + 1;
                    objects[2] += "," + auth.getCname();
                }else if("-1".equals(myAcno)) {
                    objects[0] = "校级";
                    objects[1] = 1;
                    objects[2] = auth.getCname();
                }else if("".equals(myAcno)) {
                    objects[0] = "全校";
                    objects[1] = 1;
                    objects[2] = auth.getCname();
                }else{
                    objects[0] = myAcademy.getAcademyName();
                    objects[1] = 1;
                    objects[2] = auth.getCname();
                }
                temp.put(myAcno, objects);
                if("1".equals(audit.getString("auditLevel"))) {
                    if ("-1".equals(myAcno)) {
                        map.put("isAll", 1);
                    }else if ("".equals(myAcno)) {
                        map.put("isAll", 2);
                    } else {
                        selectedAcno.add(myAcademy);
                        schoolAcademyList.remove(myAcademy);
                    }
                }
            }
        }

        // 权限选择
        Set<Authority> authorities = authorityDAO.findAllAuthoritys();
        map.put("authorities", authorities);

        map.put("schoolAcademyList", schoolAcademyList);
        map.put("selectedAcno", selectedAcno);
        map.put("showList", temp);

        map.put("auditConfigLevel",auditConfigLevel);

        return "audit/auditSetting.jsp";
    }

    /**
     * Description 保存审核设置
     * @param request
     * @param map
     * @param acno
     * @return
     * @throws ParseException
     * @author 陈乐为 2018-10-15
     */
    @RequestMapping("/saveAuditLevelConfig")
    @ResponseBody
    public String saveAuditLevelConfig(HttpServletRequest request,Map<String, Object> map,
                                       @ModelAttribute("selected_academy") String acno) throws ParseException {
        String projectName = pConfig.PROJECT_NAME;
//        String type = projectName + request.getParameter("type") + acno;// 每个学院各自设置，暂时隐藏
        String type = projectName + request.getParameter("type");
        String allType = request.getParameter("allType");
        String auditLevelConfig = request.getParameter("auditLevelConfig");
        String range = request.getParameter("range");
        String[] rangeSplit = range.split(",");
        for (String aRangeSplit : rangeSplit) {
            // 判断是否为全校‘20190506’，全校则遍历保存
            if (aRangeSplit.equals("20190506")) {
                for (SchoolAcademy academy : shareService.findAllSchoolAcademys()) {
                    Map<String, String> params = new HashMap<>();
                    params.put("auditLevelConfig", auditLevelConfig);
                    params.put("businessType", type + academy.getAcademyNumber());
                    String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "/audit/saveBusinessAuditConfigLevel", params);
                    if("1".equals(allType)) {
                        int configNum = auditLevelConfig.split(",").length;
                        StringBuilder config = new StringBuilder();
                        for (int i = 0; i < configNum; i++) {
                            config.append("on,");
                        }
                        config = new StringBuilder(config.substring(0, config.length() - 1));
                        params = new HashMap<>();
                        params.put("businessUid", "-1");
                        params.put("config", config.toString());
                        params.put("businessType", type + aRangeSplit);
                        HttpClientUtil.doPost(pConfig.auditServerUrl + "/audit/saveBusinessAuditConfigs", params);
                    }
                }
                break;
            }else {
                Map<String, String> params = new HashMap<>();
                params.put("auditLevelConfig", auditLevelConfig);
                params.put("businessType", type + aRangeSplit);
                String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "/audit/saveBusinessAuditConfigLevel", params);
                if("1".equals(allType)) {
                    int configNum = auditLevelConfig.split(",").length;
                    StringBuilder config = new StringBuilder();
                    for (int i = 0; i < configNum; i++) {
                        config.append("on,");
                    }
                    config = new StringBuilder(config.substring(0, config.length() - 1));
                    params = new HashMap<>();
                    params.put("businessUid", "-1");
                    params.put("config", config.toString());
                    params.put("businessType", type + aRangeSplit);
                    HttpClientUtil.doPost(pConfig.auditServerUrl + "/audit/saveBusinessAuditConfigs", params);
                }
            }
        }
        return "success";
    }

    /**
     * Description 删除一个学院的审核等级设置
     * @param request 请求
     * @return 成功与否
     * @author 黄保钱 2018-10-22
     */
    @RequestMapping("/deleteAudit")
    @ResponseBody
    public String deleteAudit(HttpServletRequest request){
        Map<String, String> params = new HashMap<>();
        String projectName = pConfig.PROJECT_NAME;
        String type = projectName + request.getParameter("type");
        String acno = request.getParameter("deleteNumber");

        // 置空后保存为删除
        params.put("auditLevelConfig", "");
        params.put("businessType", type + acno);
        HttpClientUtil.doPost(pConfig.auditServerUrl + "/audit/saveBusinessAuditConfigLevel", params);
        return "success";
    }

    /**
     * Description 保存设备维修确认权限
     * @param request 请求传参
     * @return 保存成功与否字符串
     * @author 黄保钱 2019-1-22
     */
    @RequestMapping("/saveConfirmAuthority")
    @ResponseBody
    public String saveConfirmAuthority(HttpServletRequest request){
        String authName = request.getParameter("authName");
        String saveType = pConfig.PROJECT_NAME + request.getParameter("saveType");
        Map<String, String> params = new HashMap<>();
        params.put("auditLevelConfig", authName);
        params.put("businessType", saveType);
        String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "/audit/saveBusinessAuditConfigLevel", params);
        return JSONObject.parseObject(s).getString("status");
    }

    /**
     * Description 配置项设置
     * @return 配置项设置页面
     * @author 黄保钱 2019-1-22
     */
    @RequestMapping("/configurationSetting")
    public ModelAndView configurationSetting(){
        ModelAndView mav = new ModelAndView();

        // 获取配置项
        Map<String, String> params = new HashMap<>();
        // 项目名
        params.put("projectName", pConfig.PROJECT_NAME);
        String str = HttpClientUtil.doPost(pConfig.auditServerUrl + "/configuration/getBusinessConfigurationList", params);
        JSONObject listReturnObject = JSONObject.parseObject(str);
        List<Object[]> objects = new ArrayList<>();
        Map<Object, List<Object[]>> extendItems = new HashMap<>();
        if("success".equals(listReturnObject.getString("status"))){
            JSONArray listArray = listReturnObject.getJSONArray("data");
            if(listArray != null && listArray.size() > 0){
                for (int i = 0; i < listArray.size(); i++) {
                    JSONObject listObject = listArray.getJSONObject(i);
                    Object[] objectArray = new Object[4];
                    // 配置名称
                    objectArray[0] = listObject.getString("businessConfigItem");
                    // 状态
                    objectArray[1] = listObject.getString("businessConfigStatus");
                    // 配置信息
                    objectArray[2] = listObject.getString("info");
                    // 配置id
                    objectArray[3] = listObject.getString("id");
                    objects.add(objectArray);
                    // 获取配置项
                    params = new HashMap<>();
                    // 项目名
                    params.put("id", objectArray[3].toString());
                    str = HttpClientUtil.doPost(pConfig.auditServerUrl + "/configuration/getConfigurationExtensions", params);
                    JSONObject extendJSONObject = JSONObject.parseObject(str);
                    List<Object[]> extendObjectList = new ArrayList<>();
                    if ("success".equals(extendJSONObject.getString("status"))) {
                        JSONArray jsonArray = extendJSONObject.getJSONArray("data");
                        if (jsonArray != null && jsonArray.size() > 0) {
                            for (int j = 0; j < jsonArray.size(); j++) {
                                listObject = jsonArray.getJSONObject(j);
                                Object[] extendObjects = new Object[4];
                                // 配置名称
                                extendObjects[0] = listObject.getString("businessConfigItemExtend");
                                // 状态
                                extendObjects[1] = listObject.getString("businessConfigExtendStatus");
                                // 配置信息
                                extendObjects[2] = listObject.getString("info");
                                // 配置id
                                extendObjects[3] = listObject.getString("id");
                                extendObjectList.add(extendObjects);
                            }
                        }
                    }
                    extendItems.put(objectArray[3], extendObjectList);
                }
            }
        }
        mav.addObject("objects", objects);
        mav.addObject("extendItems", extendItems);

        mav.setViewName("audit/configurationSetting.jsp");
        return mav;
    }

    /**
     * Description 保存配置项设置
     * @param request 请求传参
     * @return 重定向配置项设置页面
     * @author 黄保钱 2019-1-22
     */
    @RequestMapping("/saveConfigurationSetting")
    public String saveConfigurationSetting(HttpServletRequest request){
        // 页面传入参数
        Map<String, String[]> map = request.getParameterMap();
        for(Map.Entry<String, String[]> entry: map.entrySet()){
            if(entry.getValue().length > 0) {
                Map<String, String> params = new HashMap<>();
                if(entry.getKey().contains("extend")) {
                    // 配置项id
                    params.put("id", entry.getKey().substring(6));
                    // 配置项状态
                    params.put("status", entry.getValue()[0]);
                    String str = HttpClientUtil.doPost(pConfig.auditServerUrl + "/configuration/setBusinessConfigurationExtendStat", params);
                }else{
                    // 配置项id
                    params.put("id", entry.getKey());
                    // 配置项状态
                    params.put("status", entry.getValue()[0]);
                    String str = HttpClientUtil.doPost(pConfig.auditServerUrl + "/configuration/setBusinessConfigurationStat", params);
                }
            }
        }
        return "redirect:/audit/configurationSetting";
    }
}
