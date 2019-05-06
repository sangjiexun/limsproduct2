package net.gvsun.lims.api.supplies;

import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.supplies.SuppliesApplyDetailsDTO;
import net.gvsun.lims.service.supplies.SuppliesApplyService;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller("ApiSuppliesApplyController")
@RequestMapping("/lims/api/supplies")
public class ApiSuppliesApplyController {

    @Autowired private SuppliesApplyService suppliesApplyService;
    @Autowired private ShareService shareService;

    /**************************************************************************************
     * @Description 获取物资申领/借用条目列表
     * @param page 当前页数
     * @param limit 当前页限制大小
     * @param batchNumber 批次编号
     * @param applicant 申请/借用人
     * @param type 申请类型：申领；借用
     * @param auditStatus 审核状态:审核通过；审核中；审核拒绝
     * @return json字符串格式的物资申领/借用条目列表
     * @author 伍菁 2019-03-04
     **************************************************************************************/
    @RequestMapping("/getSuppliesApplyList")
    @ResponseBody
    public String getSuppliesApplyList(Integer page, Integer limit, String batchNumber, String applicant, String type,String auditStatus){
        JSONObject jsonObject = suppliesApplyService.getSuppliesApplyList(page,limit,batchNumber,applicant,type,auditStatus);
        return shareService.htmlEncode(jsonObject.toJSONString());
    }
    /**************************************************************************************
     * @Description  获取全部物资类型
     * @return json字符串格式的物资类型列表
     * @author 伍菁 2019-03-05
     **************************************************************************************/
    @RequestMapping("/getSuppliesCategoryList")
    @ResponseBody
    public String getSuppliesCategoryList(){
        JSONObject jsonObject = suppliesApplyService.getSuppliesCategoryList();
        return shareService.htmlEncode(jsonObject.toJSONString());
    }
    /**************************************************************************************
     * @Description  通过物资类型id获取该类型下的全部全部物资信息（名称、型号及规格、生产厂家、单位、数量、单价）
     * @param id 物资类型id
     * @return json字符串格式的物资信息列表
     * @author 伍菁 2019-03-05
     **************************************************************************************/
    @RequestMapping("/getSuppliesInformationListById")
    @ResponseBody
    public String getSuppliesInformationListById(Integer id){
        JSONObject jsonObject = suppliesApplyService.getSuppliesInformationListById(id);
        return shareService.htmlEncode(jsonObject.toJSONString());
    }
    /**************************************************************************************
     * @Description  保存物资申领/借用
     * @param suppliesApplyDetailsDTO 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 伍菁 2019-03-04
     **************************************************************************************/
    @RequestMapping("/saveSuppliesApply")
    @ResponseBody
    public String saveSuppliesApply(@RequestBody SuppliesApplyDetailsDTO suppliesApplyDetailsDTO){
        if(suppliesApplyService.saveSuppliesApply(suppliesApplyDetailsDTO)){
            return "success";
        }else{
            return "fail";
        }
    }
    /**************************************************************************************
     * @Description 通过申领/借用批次编号获取物资申领/借用详情
     * @param batchNumber 申领/借用批次编号
     * @return json字符串格式的物资申领/借用详情
     * @author 伍菁 2019-03-06
     **************************************************************************************/
    @RequestMapping("/getSuppliesApplyDetailsByBatchNumber")
    @ResponseBody
    public String getSuppliesApplyDetailsByBatchNumber(String batchNumber){
        JSONObject jsonObject = suppliesApplyService.getSuppliesApplyDetailsByBatchNumber(batchNumber);
        return shareService.htmlEncode(jsonObject.toJSONString());
    }
    /**************************************************************************************
     * @Description 删除物资申领/借用
     * @param batchNumber 申领/借用批次编号
     * @return 成功-"success"，失败-"fail"
     * @author 伍菁 2019-03-04
     **************************************************************************************/
    @RequestMapping("/deleteSuppliesApply")
    @ResponseBody
    public String deleteSuppliesApply(String batchNumber){
        if(suppliesApplyService.deleteSuppliesApply(batchNumber)){
            return "success";
        }else {
            return "fail";
        }
    }

}
