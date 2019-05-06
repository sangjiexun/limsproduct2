package net.gvsun.lims.service.auditServer;

import java.util.List;

public interface AuditService {
    /************************************************************************
     *@Description:调用接口保存业务预约初始的审核级别状态
     *@Author:吴奇臻
     *@return:业务预约所属初始审核级别：-1代表不需要审核或者所有审核级都关闭
     *@Date:2018/9/21
     ************************************************************************/
    public String saveInitBusinessAudit(String businessUid, String businessType, String businessAppUid)throws Exception;
    /************************************************************************
     *@Description:调用接口获取业务目前所处审核级别状态
     *@Author:吴奇臻
     *@return:map: key:所处审核级别，-1代表审核全部通过 0 代表审核被某级拒绝 预约已失败 value：审核级别名称
     *@Date:2018/9/21
     ************************************************************************/
    public String getCurrAudit(String businessAppUid, String businessType)throws Exception;
    /************************************************************************
     *@Description:调用接口保存业务单级审核结果 并返回审核后结果
     *@Author:吴奇臻
     *@return:map: key:所处审核级别，-1代表审核全部通过 0 代表审核被某级拒绝 预约已失败 value：审核级别名称
     *@Date:2018/9/21
     ************************************************************************/
    public String saveBusinessLevel(String businessAppUid,String businessUid, String result, String info, String businessType,String username)throws Exception;
    /************************************************************************
     *@Description:调用接口获取业务预约的各级审核状态
     *@Author:吴奇臻
     *@Date:2018/9/21
     ************************************************************************/
    public String getBusinessLevel(String businessUid,String businessAppUid,String businessType)throws Exception;
    /************************************************************************
     *@Description:调用接口获取业务预约审核设置状态
     *@Author:吴奇臻
     *@return:Map<Integer,String>:key代表级别value值有两种 on:开启 off:关闭
     *@Date:2018/9/21
     ************************************************************************/
    public String getBusinessAudit(String businessUid,String businessType)throws Exception;
    /************************************************************************
     *@Description用接口保存业务的审核设置
     *@Author:吴奇臻
     *@param:config:Map<Integer,String>:key代表级别value值有两种 on:开启 off:关闭
     *@Date:2018/9/21
     ************************************************************************/
    public String saveBusinessAudit(String businessUid,String configs,String businessType) throws Exception;
    /************************************************************************
     *@Description用接口获取业务的审核设置
     *@Author:吴奇臻
     *@Date:2018/9/21
     ************************************************************************/
    public List<String> getBusinessAuditConfigLevel(String businessType) throws Exception;
    /************************************************************************
     *@Description用接口保存业务的审核设置
     *@Author:吴奇臻
     *@param:config:Map<Integer,String>:key代表级别value值有两种 on:开启 off:关闭
     *@Date:2018/9/21
     ************************************************************************/
    public String saveBusinessAuditConfigLevel(String businessType,String auditLevelConfig) throws Exception;
    /**************************************************************************
     * Description 得到当前审核状态名
     * @author 吴奇臻
     * @date 2017-09-20
     **************************************************************************/
    public String getAuditLevelName(String uid,String type);
}
