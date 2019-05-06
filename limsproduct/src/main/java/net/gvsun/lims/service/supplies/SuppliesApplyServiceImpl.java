package net.gvsun.lims.service.supplies;

import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.supplies.*;
import net.zjcclims.domain.User;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service("SuppliesApplyService")
public class SuppliesApplyServiceImpl implements SuppliesApplyService{

    @Autowired ShareService shareService;

    /**************************************************************************************
     * 将项目数据封装为前台可接收的json格式数据
     * @param projects 项目列表
     * @param totalRecords 项目总数
     * @return json格式数据
     * @author 黄保钱 2019-2-26
     **************************************************************************************/
    private JSONObject getProjectJSON_1(List<?> projects, int totalRecords){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", projects);
        jsonObject.put("count", totalRecords);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "success");
        return jsonObject;
    }
    /**************************************************************************************
     * 将项目数据封装为前台可接收的json格式数据
     * @param projects 项目列表
     * @param totalRecords 项目总数
     * @param totalPage 总页数
     * @return json格式数据
     * @author 伍菁 2019-03-12
     **************************************************************************************/
    private JSONObject getProjectJSON_2(List<?> projects, int totalRecords,int totalPage){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", projects);
        jsonObject.put("count", totalRecords);
        jsonObject.put("totalPage",totalPage);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "success");
        return jsonObject;
    }

    /**************************************************************************************
     * @description 获取物资申领/借用条目列表
     * @return 物资申领/借用DTO
     * @param currPage 当前页数
     * @param limit 当前页限制大小
     * @param batchNumber 批次编号
     * @param applicant 申请人
     * @param type 申请类型：申领；借用
     * @param auditStatus 审核状态:审核通过；审核中；审核拒绝
     * @author 伍菁 2019-03-04
     **************************************************************************************/
    @Override
    public JSONObject getSuppliesApplyList(Integer currPage, Integer limit, String batchNumber,String applicant,String type,String auditStatus){
        List<SuppliesApplyDTO> suppliesApplyDTOList = new ArrayList<>();
        int totalRecords = 16;
        for(int i=0;i<totalRecords;i++) {
            SuppliesApplyDTO suppliesApplyDTO = new SuppliesApplyDTO();
            //申请批次编号
            suppliesApplyDTO.setBatchNumber("申领编号" + (i+1));
            //申请类型
            if (i%2==0) {
                suppliesApplyDTO.setType("申领");
            } else {
                suppliesApplyDTO.setType("借用");
            }
            //申请人
            User applicantTest = shareService.getUserDetail();
            suppliesApplyDTO.setApplicant(applicantTest.getCname());
            //申请单位
            suppliesApplyDTO.setApplicantCompany(applicantTest.getSchoolAcademy().getAcademyName());
            //申请时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm" );
            Calendar time = Calendar.getInstance();
            suppliesApplyDTO.setApplicationTime(sdf.format(time.getTime()));
            //审核状态
            if(i%3==0) {
                suppliesApplyDTO.setAuditStatus("审核拒绝");
            }else if(i%5==0){
                suppliesApplyDTO.setAuditStatus("审核中");
            }else{
                suppliesApplyDTO.setAuditStatus("审核通过");
            }
            //审核时间
            suppliesApplyDTO.setAuditTime(sdf.format(time.getTime()));
            //是否领取
            String auditStatusTset = suppliesApplyDTO.getAuditStatus();
            if("审核通过".equals(auditStatusTset)) {
                if (i % 2 == 0) {
                    suppliesApplyDTO.setIsReceive("已领取");
                } else {
                    suppliesApplyDTO.setIsReceive("未领取");
                }
            }
            //是否归还
            String typeTest = suppliesApplyDTO.getType();
            String auditStatusTest = suppliesApplyDTO.getAuditStatus();
            if("借用".equals(typeTest) && "审核通过".equals(auditStatusTest)){
                if(i%2==0) {
                    suppliesApplyDTO.setIsReturn("已归还");
                }else{
                    suppliesApplyDTO.setIsReturn("未归还");
                }
            }
            suppliesApplyDTOList.add(suppliesApplyDTO);
        }
        //总页数
        int totalPage = 1;
        if(limit!=0) {
            totalPage = (int) Math.ceil((double) totalRecords / limit);
        }
        // 分页
        suppliesApplyDTOList = suppliesApplyDTOList.subList((currPage-1)*limit, Math.min((currPage)*limit, suppliesApplyDTOList.size()));
        return getProjectJSON_2(suppliesApplyDTOList,totalRecords,totalPage);
    }
    /**************************************************************************************
     * @Description  获取全部物资类型
     * @return 物资类型DTO列表
     * @author 伍菁 2019-03-05
     **************************************************************************************/
    @Override
    public JSONObject getSuppliesCategoryList(){
        List<SuppliesApplySuppliesCategoryDTO> suppliesCategoryDTOList = new ArrayList<>();
        int totalRecords = 7;
        for(int i=0;i<totalRecords;i++) {
            SuppliesApplySuppliesCategoryDTO suppliesCategoryDTO = new SuppliesApplySuppliesCategoryDTO();
            //id
            suppliesCategoryDTO.setId(i+1);
            //中文名称
            suppliesCategoryDTO.setChineseName("分类"+(i+1));
            suppliesCategoryDTOList.add(suppliesCategoryDTO);
        }

        return getProjectJSON_1(suppliesCategoryDTOList,totalRecords);
    }
    /**************************************************************************************
     * @Description  通过物资类型id获取该类型下的全部全部物资信息（名称、型号及规格、生产厂家、单位、数量、单价）
     * @param id 物资类型id
     * @return 物资仓库DTO列表
     * @author 伍菁 2019-03-05
     **************************************************************************************/
    @Override
    public JSONObject getSuppliesInformationListById(Integer id){
        List<SuppliesStorageDTO> suppliesStorageDTOList = new ArrayList<>();
        int totalRecords = 6;
        for (int i=0;i<totalRecords;i++){
            SuppliesStorageDTO suppliesStorageDTO = new SuppliesStorageDTO();
            suppliesStorageDTO.setGoodsNumber("物品"+(i+1));
            suppliesStorageDTO.setGoodsName("名字"+(i+1));
            suppliesStorageDTO.setTypeSpecification("型号"+(i+1));
            suppliesStorageDTO.setFactory("生产厂家"+(i+1));
            suppliesStorageDTO.setUnit("单位"+(i+1));
            suppliesStorageDTO.setUnitPrice((float)(i+1));
            suppliesStorageDTO.setQuantity((i+1));
            suppliesStorageDTOList.add(suppliesStorageDTO);
        }
        return getProjectJSON_1(suppliesStorageDTOList,totalRecords);
    }
    /**************************************************************************************
     * @Description 保存物资申领
     * @param suppliesApplyDetailsDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 伍菁 2019-03-04
     **************************************************************************************/
    @Override
    public boolean saveSuppliesApply(SuppliesApplyDetailsDTO suppliesApplyDetailsDTO){
        return true;
    }
    /**************************************************************************************
     * @Description 通过申领/借用批次编号获取物资申领详情
     * @param batchNumber 申领/借用批次编号
     * @return 物资申领/借用详情DTO
     * @author 伍菁 2019-03-06
     **************************************************************************************/
    @Override
    public JSONObject getSuppliesApplyDetailsByBatchNumber(String batchNumber) {
        List<SuppliesApplyDetailsDTO> suppliesApplyDetailsDTOList = new ArrayList<>();
        SuppliesApplyDetailsDTO suppliesApplyDetailsDTO = new SuppliesApplyDetailsDTO();
        suppliesApplyDetailsDTO.setBatchNumber("编号1");
        suppliesApplyDetailsDTO.setType("申领");
        suppliesApplyDetailsDTO.setApplicantCompany("化工学院");
        suppliesApplyDetailsDTO.setApplicant("张三");
        suppliesApplyDetailsDTO.setApplicationTime("2019-03-06");
        suppliesApplyDetailsDTO.setAuditStatus("审核通过");
        suppliesApplyDetailsDTO.setAuditTime("2019-03-06");
        suppliesApplyDetailsDTO.setIsReceive("已领取");
        suppliesApplyDetailsDTO.setReceiver("张三");
        suppliesApplyDetailsDTO.setReceiveTime("2019-03-07");
        suppliesApplyDetailsDTO.setPurpose("实验");
        suppliesApplyDetailsDTO.setRemarks("无");
        suppliesApplyDetailsDTO.setSuppliesCategory("化学试剂");
        List<SuppliesApplyGoodsInformationDTO> suppliesApplyGoodsInformationDTOList = new ArrayList<>();
        for(int i=0; i<3; i++){
            SuppliesApplyGoodsInformationDTO suppliesApplyGoodsInformationDTO = new SuppliesApplyGoodsInformationDTO();
            suppliesApplyGoodsInformationDTO.setGoodsCategory("化学试剂");
            suppliesApplyGoodsInformationDTO.setGoodsName("试剂"+(i+1));
            suppliesApplyGoodsInformationDTO.setTypeSpecification("50g");
            suppliesApplyGoodsInformationDTO.setFactory("无锡硕鼎化工科技有限公司");
            suppliesApplyGoodsInformationDTO.setUnit("瓶");
            suppliesApplyGoodsInformationDTO.setUnitPrice((float)190.51);
            suppliesApplyGoodsInformationDTO.setQuantity(10);
            suppliesApplyGoodsInformationDTO.setItemPrice((float)1905.1);
            suppliesApplyGoodsInformationDTO.setMoney((float)2210);
            suppliesApplyGoodsInformationDTO.setItemRemarks("无");
            suppliesApplyGoodsInformationDTOList.add(suppliesApplyGoodsInformationDTO);
        }
        suppliesApplyDetailsDTO.setGoodsInformationDTOList(suppliesApplyGoodsInformationDTOList);
        suppliesApplyDetailsDTO.setTotalPrice((float)360);
        suppliesApplyDetailsDTOList.add(suppliesApplyDetailsDTO);
        return getProjectJSON_1(suppliesApplyDetailsDTOList,1);
    }
    /**************************************************************************************
     * @Description 删除物资申领
     * @param batchNumber 申领批次编号
     * @return 成功-"success"，失败-"fail"
     * @author 伍菁 2019-03-04
     **************************************************************************************/
    @Override
    public boolean deleteSuppliesApply(String batchNumber){
        return true;
    }

}
