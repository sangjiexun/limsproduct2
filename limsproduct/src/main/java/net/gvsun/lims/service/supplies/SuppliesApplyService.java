package net.gvsun.lims.service.supplies;

import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.supplies.SuppliesApplyDetailsDTO;

public interface SuppliesApplyService {

    /**************************************************************************************
     * @Description 获取物资申领/借用条目列表
     * @param page 当前页数
     * @param limit 当前页限制大小
     * @param batchNumber 批次编号
     * @param applicant 申请/借用人
     * @param type 申请类型：申领；借用
     * @param auditStatus 审核状态:审核通过；审核中；审核拒绝
     * @return 物资申领/借用DTO列表
     * @author 伍菁 2019-03-04
     **************************************************************************************/
    JSONObject getSuppliesApplyList(Integer page, Integer limit, String batchNumber,String applicant,String type,String auditStatus);
    /**************************************************************************************
     * @Description  获取全部物资类型
     * @return 物资l类型DTO列表
     * @author 伍菁 2019-03-05
     **************************************************************************************/
    JSONObject getSuppliesCategoryList();
    /**************************************************************************************
     * @Description  通过物资类型id获取该类型下的全部全部物资信息（名称、型号及规格、生产厂家、单位、数量、单价）
     * @param id 物资类型id
     * @return 物资仓库DTO列表
     * @author 伍菁 2019-03-05
     **************************************************************************************/
    JSONObject getSuppliesInformationListById(Integer id);
    /**************************************************************************************
     * @Description 保存物资申领/借用
     * @param suppliesApplyDetailsDTO 保存的数据封装DTO列表
     * @return 保存成功-true，失败-false
     * @author 伍菁 2019-03-04
     **************************************************************************************/
    boolean saveSuppliesApply(SuppliesApplyDetailsDTO suppliesApplyDetailsDTO);
    /**************************************************************************************
     * @Description 通过申领/借用批次编号获取物资申领/借用详情
     * @param batchNumber 申领/借用批次编号
     * @return 物资申领/借用详情DTO
     * @author 伍菁 2019-03-06
     **************************************************************************************/
    JSONObject getSuppliesApplyDetailsByBatchNumber(String batchNumber);
    /**************************************************************************************
     * @Description 删除物资申领
     * @param batchNumber 申领批次编号
     * @return 成功-"success"，失败-"fail"
     * @author 伍菁 2019-03-04
     **************************************************************************************/
    boolean deleteSuppliesApply(String batchNumber);

}
