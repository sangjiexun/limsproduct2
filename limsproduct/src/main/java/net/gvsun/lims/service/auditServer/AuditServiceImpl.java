package net.gvsun.lims.service.auditServer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.common.PConfigDTO;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("AuditService")
public class AuditServiceImpl implements AuditService {
    @Autowired
    private ShareService shareService;



    /************************************************************************
     *@Description:调用接口保存业务预约初始的审核级别状态
     *@Author:吴奇臻
     *@return:业务预约所属初始审核级别：-1代表不需要审核或者所有审核级都关闭
     *@Date:2018/9/21
     ************************************************************************/
    public String saveInitBusinessAudit(String businessUid, String businessType, String businessAppUid)throws Exception{
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        String project=pConfigDTO.PROJECT_NAME;
        String type=project+businessType;
        Map<String, String> params = new HashMap<>();
        params.put("businessType",type);
        params.put("businessAppUid",businessAppUid);
        params.put("businessUid",businessUid);
        String s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl+"/audit/saveInitBusinessAuditStatus", params);
        return s;
    }
    /************************************************************************
     *@Description:调用接口获取业务目前所处审核级别状态
     *@Author:吴奇臻
     *@return:map: key:所处审核级别，-1代表审核全部通过 0 代表审核被某级拒绝 预约已失败 value：审核级别名称
     *@Date:2018/9/21
     ************************************************************************/
    public String getCurrAudit(String businessAppUid, String businessType)throws Exception{
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        String project=pConfigDTO.PROJECT_NAME;
        String type=project+businessType;
        Map<String, String> params = new HashMap<>();
        params.put("businessType",type);
        params.put("businessAppUid",businessAppUid);
        String s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl+"/audit/getCurrAuditStage", params);
        return s;
    }
    /************************************************************************
     *@Description:调用接口保存业务单级审核结果 并返回审核后结果
     *@Author:吴奇臻
     *@return:map: key:所处审核级别，-1代表审核全部通过 0 代表审核被某级拒绝 预约已失败 value：审核级别名称
     *@Date:2018/9/21
     ************************************************************************/
    public String saveBusinessLevel(String businessAppUid,String businessUid, String result, String info, String businessType,String username)throws Exception{
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        String project=pConfigDTO.PROJECT_NAME;
        String type=project+businessType;
        Map<String, String> params = new HashMap<>();
        params.put("businessType",type);
        params.put("businessAppUid",businessAppUid);
        params.put("businessUid",businessUid);
        params.put("username",username);
        params.put("result",result);
        params.put("info",info);
        String s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl+"/audit/saveBusinessLevelAudit", params);
        return s;
    }
    /************************************************************************
     *@Description:调用接口获取业务预约的各级审核状态
     *@Author:吴奇臻
     *@Date:2018/9/21
     ************************************************************************/
    public String getBusinessLevel(String businessUid,String businessAppUid,String businessType)throws Exception{
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        String project=pConfigDTO.PROJECT_NAME;
        String type=project+businessType;
        Map<String, String> params = new HashMap<>();
        params.put("businessType", type);
        params.put("businessAppUid", businessAppUid);
        params.put("businessUid", businessUid);
        String s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl+"/audit/getBusinessLevelStatus", params);
        return s;
    }
    /************************************************************************
     *@Description:调用接口获取业务预约审核设置状态
     *@Author:吴奇臻
     *@return:Map<Integer,String>:key代表级别value值有两种 on:开启 off:关闭
     *@Date:2018/9/21
     ************************************************************************/
    public String getBusinessAudit(String businessUid,String businessType)throws Exception{
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        String project=pConfigDTO.PROJECT_NAME;
        String type=project+businessType;
        Map<String, String> params = new HashMap<>();
        params.put("businessUid",businessUid);
        params.put("businessType",type);
        String s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl+"/audit/getBusinessAuditConfigs", params);
        return s;
    }
    /************************************************************************
     *@Description用接口保存业务的审核设置
     *@Author:吴奇臻
     *@param:config:Map<Integer,String>:key代表级别value值有两种 on:开启 off:关闭
     *@Date:2018/9/21
     ************************************************************************/
    public String saveBusinessAudit(String businessUid,String configs,String businessType) throws Exception{
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        String project=pConfigDTO.PROJECT_NAME;
        String type=project+businessType;
        Map<String, String> params = new HashMap<>();
        params.put("businessUid",businessUid);
        params.put("businessType",type);
        params.put("config", configs);
        String s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl+"/audit/saveBusinessAuditConfigs", params);
        return s;
    }
    /************************************************************************
     *@Description用接口获取业务的审核设置
     *@Author:吴奇臻
     *@Date:2018/9/21
     ************************************************************************/
    public List<String> getBusinessAuditConfigLevel(String businessType) throws Exception{
        Map<String, String> params = new HashMap<>();
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        String project=pConfigDTO.PROJECT_NAME;
        String type=project+businessType;
        params.put("businessType",type);
        String s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl+"/audit/getBusinessAuditConfigLevel", params);
        JSONObject jsonObject = JSON.parseObject(s);
        JSONArray auditConfig=jsonObject.getJSONArray("data");
        List<String> auditConfigLevel=new ArrayList<>();
        for(int i=0;i<auditConfig.size();i++){
            JSONObject audit=JSON.parseObject(auditConfig.get(i).toString());
            String auditLevel=audit.getString("auditLevel");
            String authId=audit.getString("authId");
            auditConfigLevel.add(authId);
        }
        return auditConfigLevel;
    }
    /************************************************************************
     *@Description用接口保存业务的审核设置
     *@Author:吴奇臻
     *@param:config:Map<Integer,String>:key代表级别value值有两种 on:开启 off:关闭
     *@Date:2018/9/21
     ************************************************************************/
    public String saveBusinessAuditConfigLevel(String businessType,String auditLevelConfig) throws Exception{
        Map<String, String> params1 = new HashMap<>();
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        String project=pConfigDTO.PROJECT_NAME;
        String type = project +businessType;
        params1.put("auditLevelConfig", auditLevelConfig);
        params1.put("businessType", type);
        String s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "/audit/saveBusinessAuditConfigLevel", params1);
        return s;
    }
    /**************************************************************************
     * Description 得到设备当前审核状态名
     * @author 吴奇臻
     * @date 2018-12-12
     **************************************************************************/
    public String getAuditLevelName(String uid,String type){
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        String project=pConfigDTO.PROJECT_NAME;
        String businessType=project+type;
        Map<String, String> params2 = new HashMap<>();
        params2.put("businessType",businessType);
        params2.put("businessAppUid",uid);
        String s2 = HttpClientUtil.doPost(pConfigDTO.auditServerUrl+"/audit/getCurrAuditStage", params2);
        JSONObject jsonObject2 = JSON.parseObject(s2);
        String status=jsonObject2.getString("status");
        if(!status.equals("500")){
            JSONArray auditLevel=jsonObject2.getJSONArray("data");
            JSONObject audit=JSON.parseObject(auditLevel.get(0).toString());
            String auditLevelName=audit.getString("result");
            return auditLevelName;
        }else {
            return null;
        }
    }
}
