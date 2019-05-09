package net.gvsun.lims.api.assets;

import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.assets.*;
import net.gvsun.lims.service.assets.MaterialService;
import net.gvsun.lims.service.auditServer.AuditService;
import net.zjcclims.JsonDateValueProcessor;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 物资名录api
 */
@Controller("MaterialController")
@RequestMapping("/lims/api/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private ShareService shareService;

    @Autowired
    private AssetDAO assetDAO;

    @Autowired
    private AssetAppDAO assetAppDAO;

    @Autowired
    private AssetStorageDAO assetStorageDAO;

    @Autowired
    private AssetReceiveDAO assetReceiveDAO;

    @Autowired
    private AssetAppRecordDAO assetAppRecordDAO;

    @Autowired
    private AssetReceiveRecordDAO assetReceiveRecordDAO;

    @Autowired
    private AssetCabinetRecordDAO assetCabinetRecordDAO;

    @Autowired
    private AssetStorageRecordDAO assetStorageRecordDAO;

    @Autowired
    private AuditService auditService;

    /**
     * 物资名录列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return json字符串格式的分类列表
     * @author 吴奇臻 2019-03-24
     */
    @RequestMapping("/listAssets")
    @ResponseBody
    public String listAssets(HttpServletRequest request, @RequestParam Integer page, Integer limit,String keywords,String kind){
        JSONObject jsonObject = materialService.findAllAssetList(page,limit,keywords,kind);
        return shareService.htmlEncode(jsonObject.toJSONString());
    }

    /**
     * 物资申购记录列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return json字符串格式的分类列表
     * @author 吴奇臻 2019-03-24
     */
    @RequestMapping("/assetsApplyList")
    @ResponseBody
    public String assetsApplyList(HttpServletRequest request, @RequestParam Integer page, Integer limit,String status,String kind){
        JSONObject jsonObject = materialService.findAllAssetApplyList(page,limit,status,kind);
        return shareService.htmlEncode(jsonObject.toJSONString());
    }
    /**
     * 物资入库记录列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return json字符串格式的分类列表
     * @author 吴奇臻 2019-03-24
     */
    @RequestMapping("/assetsInStorageList")
    @ResponseBody
    public String assetsInStorageList(HttpServletRequest request, @RequestParam Integer page, Integer limit,String status){
        JSONObject jsonObject = materialService.findAllAssetInStorageList(page,limit,status);
        return shareService.htmlEncode(jsonObject.toJSONString());
    }
    /**
     * 物资入库记录列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return json字符串格式的分类列表
     * @author 吴奇臻 2019-04-03
     */
    @RequestMapping("/assetsReceiveList")
    @ResponseBody
    public String assetsReveiveList(HttpServletRequest request, @RequestParam Integer page, Integer limit,String status){
        JSONObject jsonObject = materialService.findAllAssetReceiveList(page,limit,status);
        return shareService.htmlEncode(jsonObject.toJSONString());
    }
    /**
     * 物资入库明细列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return json字符串格式的分类列表
     * @author 吴奇臻 2019-03-24
     */
    @RequestMapping("/assetsInStorageItemList")
    @ResponseBody
    public String assetsInStorageItemList(HttpServletRequest request, @RequestParam Integer page, Integer limit,Integer id){
        JSONObject jsonObject = materialService.findAllAssetInStorageItemList(page,limit,id);
        return shareService.htmlEncode(jsonObject.toJSONString());
    }
    /**
     * 物资申购条目列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return json字符串格式的分类列表
     * @author 吴奇臻 2019-03-24
     */
    @RequestMapping("/assetsApplyItemList")
    @ResponseBody
    public String assetsApplyItemList(HttpServletRequest request, @RequestParam Integer page, Integer limit,Integer id){
        JSONObject jsonObject = materialService.findAllAssetApplyItemList(page,limit,id);
        return shareService.htmlEncode(jsonObject.toJSONString());
    }

    /**
     * 物资申购条目列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return json字符串格式的分类列表
     * @author 吴奇臻 2019-03-24
     */
    @RequestMapping("/assetsReceiveItemList")
    @ResponseBody
    public String assetsReceiveItemList(HttpServletRequest request, @RequestParam Integer page, Integer limit,Integer id){
        JSONObject jsonObject = materialService.findAllAssetReceiveItemList(page,limit,id);
        return shareService.htmlEncode(jsonObject.toJSONString());
    }
    /**
     * Description 保存物资名录
     * @param materialListDTO 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-3-26
     */
    @RequestMapping("/saveAssetsDetail")
    @ResponseBody
    public String saveAssetsDetail(@RequestBody MaterialListDTO materialListDTO,String imageUrl,String names,String sizes){
        //保存asset信息
        Asset asset=materialService.saveAssetsDetail(materialListDTO);
        //保存物资图片信息
        String status=materialService.saveAssetsRelatedImage(imageUrl,names,sizes,asset.getId(),"asset");
        return status;
    }

    /**
     * Description 保存物资申购条目
     * @param assetsApplyItemDTO 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-3-26
     */
    @RequestMapping("/saveAddAssetsDetail")
    @ResponseBody
    public String saveAddAssetsDetail(@RequestBody AssetsApplyItemDTO assetsApplyItemDTO){
        if(materialService.saveAddAssetsDetail(assetsApplyItemDTO)){
            return "success";
        }else{
            return "fail";
        }
    }
    /**
     * Description 保存物资入库条目
     * @param assetsApplyItemDTO 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-3-26
     */
    @RequestMapping("/saveAddAssetsInStorageDetail")
    @ResponseBody
    public String saveAddAssetsInStorageDetail(@RequestBody AssetsApplyItemDTO assetsApplyItemDTO){
        if(materialService.saveAddAssetsInStorageDetail(assetsApplyItemDTO)){
            return "success";
        }else{
            return "fail";
        }
    }
    /**
     * Description 保存物资入库条目
     * @param assetsApplyItemDTO 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-3-26
     */
    @RequestMapping("/saveAddAssetsReceiveDetail")
    @ResponseBody
    public String saveAddAssetsReceiveDetail(@RequestBody AssetsApplyItemDTO assetsApplyItemDTO){
        String s=materialService.allocateCabinetFromAssets(Integer.parseInt(assetsApplyItemDTO.getAssetsId()),assetsApplyItemDTO.getQuantity(),Integer.parseInt(assetsApplyItemDTO.getId()));
        if(!s.equals("insufficient")&&!s.equals("notEnough")){
            assetsApplyItemDTO.setCabinet(s);
            materialService.saveAddAssetsReceiveDetail(assetsApplyItemDTO);
        }
       return s;
    }
    /**
     * Description 保存物资申请记录
     * @param assetsApplyDTO 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-3-26
     */
    @RequestMapping("/saveAssetsApplyDetail")
    @ResponseBody
    public Integer saveAssetsApplyDetail(@RequestBody AssetsApplyDTO assetsApplyDTO){
        Integer appid=materialService.saveAssetsApplyDetail(assetsApplyDTO);
        return appid;
    }
    /**
     * Description 保存物资入库记录（申领入库）
     * @param assetsInStorageDTO 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-3-26
     */
    @RequestMapping("/savePutAssetsInStorage")
    @ResponseBody
    public Integer savePutAssetsInStorage(@RequestBody AssetsInStorageDTO assetsInStorageDTO,String imageUrl,String names,String sizes){
        Integer appid=materialService.saveAssetsInStorageDetail(assetsInStorageDTO);
        String status=materialService.saveAssetsRelatedImage(imageUrl,names,sizes,appid,"assetInStorage");
        return appid;
    }

    /**
     * Description 保存物资入库记录（直接入库）
     * @param assetsInStorageDTO 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-3-26
     */
    @RequestMapping("/saveAssetsInStorageDetail")
    @ResponseBody
    public Integer saveAssetsInStorageDetail(@RequestBody AssetsInStorageDTO assetsInStorageDTO,String imageUrl,String names,String sizes){
        Integer id=materialService.saveAssetsInStorageDetail(assetsInStorageDTO);
        String status=materialService.saveAssetsRelatedImage(imageUrl,names,sizes,id,"assetInStorage");
        return id;
    }

    /**
     * Description 保存物资申请记录
     * @param assetsReceiveDTO 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-3-26
     */
    @RequestMapping("/saveAssetsReceiveDetail")
    @ResponseBody
    public Integer saveAssetsReceiveDetail(@RequestBody AssetsReceiveDTO assetsReceiveDTO,String imageUrl,String names,String sizes){
        Integer id=materialService.saveAssetsReceiveDetail(assetsReceiveDTO);
        String status=materialService.saveAssetsRelatedImage(imageUrl,names,sizes,id,"assetReceive");
        return id;
    }
    /**
     * Description 提交物资申购记录
     * @param id 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-3-29
     */
    @RequestMapping("/submitAssetsApply")
    @ResponseBody
    public String submitAssetsApply(@RequestParam Integer id){
        AssetApp assetApp=assetAppDAO.findAssetAppById(id);
        assetApp.setAssetStatu(1);//提交审核
        //调用审核服务
        String businessUid=assetApp.getCategoryId().toString();
        String businessType="AssetsClassification"+ "ApplyAudit"+assetApp.getCategoryId();
        String businessAppUid=assetApp.getId().toString();
        try {//初始化审核状态
            String s = auditService.saveInitBusinessAudit(businessUid, businessType, businessAppUid);
        }catch (Exception e){
            e.printStackTrace();
        }
        String tag = auditService.getAuditLevelName(businessAppUid, businessType);
        assetApp.setCurAuditLevel(tag);
        if(tag==null||tag.equals("pass")){//没有设置审核层级或返回pass状态直接通过
            assetApp.setAssetStatu(2);//审核通过
        }
        assetAppDAO.store(assetApp);

        return "success";
    }

    /**
     * Description 获取物资申购审核标志位
     * @param id 参数封装DTO
     * @return 是-"success"，否-"fail"
     * @author 吴奇臻 2019-4-17
     */
    @RequestMapping("/getAssetsApplyAuditFlag")
    @ResponseBody
    public Boolean getAssetsApplyAuditFlag(@RequestParam Integer id){
        AssetApp assetApp=assetAppDAO.findAssetAppById(id);
        boolean flag=materialService.getAssetsApplyAuditFlag(assetApp.getCurAuditLevel(),assetApp.getAssetStatu());
        return flag;
    }
    /**
     * Description 提交物资申购记录
     * @param id 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-3-29
     */
    @RequestMapping("/submitAssetsInStorage")
    @ResponseBody
    public String submitAssetsInStorage(@RequestParam Integer id){
        AssetStorage assetStorage=materialService.findAssetStorageById(id);
        assetStorage.setStatus(1);//提交审核
        //调用审核服务
        String businessUid=assetStorage.getClassficationId().toString();
        String businessType="AssetsClassification"+ "StorageAudit"+assetStorage.getClassficationId();
        String businessAppUid=assetStorage.getId().toString();
        try {//初始化审核状态
            String s = auditService.saveInitBusinessAudit(businessUid, businessType, businessAppUid);
        }catch (Exception e){
            e.printStackTrace();
        }//获取当前审核层级
        String tag = auditService.getAuditLevelName(businessAppUid, businessType);
        assetStorage.setCurAuditLevel(tag);
        if(tag==null||tag.equals("pass")){//没有设置审核层级或返回pass状态直接通过
            assetStorage.setStatus(2);//审核通过
            assetStorage.setAuditDate(new Date());
        }
        assetStorageDAO.store(assetStorage);
        return "success";
    }
    /**
     * Description 提交物资申购记录
     * @param id 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-3-29
     */
    @RequestMapping("/submitAssetsReceive")
    @ResponseBody
    public String submitAssetsReceive(@RequestParam Integer id){
        AssetReceive assetReceive=assetReceiveDAO.findAssetReceiveById(id);
        assetReceive.setStatus(1);//提交审核
        //调用审核服务
        String businessUid=assetReceive.getCategoryId().toString();
        String businessType="AssetsClassification"+ "ReceiveAudit"+assetReceive.getCategoryId();
        String businessAppUid=assetReceive.getId().toString();
        try {//初始化审核状态
            String s = auditService.saveInitBusinessAudit(businessUid, businessType, businessAppUid);
        }catch (Exception e){
            e.printStackTrace();
        }//获取当前审核层级
        String tag = auditService.getAuditLevelName(businessAppUid, businessType);
        assetReceive.setCurAuditLevel(tag);
        if(tag==null||tag.equals("pass")){
            assetReceive.setStatus(2);//默认审核通过
            Calendar calendar=Calendar.getInstance();
            assetReceive.setAuditDate(calendar);
        }
        assetReceiveDAO.store(assetReceive);
        return "success";
    }
    /**
     * Description 修改物资申购状态
     * @param id
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-3-29
     */
    @RequestMapping("/changeAssetsApplyStatus")
    @ResponseBody
    public String changeAssetsApplyStatus(@RequestParam Integer id,String result){
        AssetApp assetApp=assetAppDAO.findAssetAppById(id);
        assetApp.setAssetStatu(1);//提交审核
        //调用审核服务
        String businessType="AssetsClassification"+ "ApplyAudit"+assetApp.getCategoryId();
        //保存审核结果
        try {
            auditService.saveBusinessLevel(id.toString(), assetApp.getCategoryId().toString(), result, "无", businessType, shareService.getUser().getUsername());
        }catch (Exception e){
            e.printStackTrace();
        }
        //获取审核后的状态结果
        String tag = auditService.getAuditLevelName(id.toString(), businessType);
        if(tag.equals("pass")){
            assetApp.setAssetStatu(2);//审核完成并通过
        }else if(tag.equals("fail")){
            assetApp.setAssetStatu(3);//审核被拒绝
        }
        assetApp.setCurAuditLevel(tag);
        assetAppDAO.store(assetApp);
        return "success";
    }
    /**
     * Description 提交物资入库记录
     * @param id 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-4-2
     */
    @RequestMapping("/changeAssetsInStorageStatus")
    @ResponseBody
    public String changeAssetsInStorageStatus(@RequestParam Integer id,String result){
        AssetStorage assetStorage=materialService.findAssetStorageById(id);
        assetStorage.setStatus(1);//提交审核
        //调用审核服务
        String businessType="AssetsClassification"+ "StorageAudit"+assetStorage.getClassficationId();
        //保存审核结果
        try {
            auditService.saveBusinessLevel(id.toString(), assetStorage.getClassficationId().toString(), result, "无", businessType, shareService.getUser().getUsername());
        }catch (Exception e){
            e.printStackTrace();
        }
        //获取审核后的状态结果
        String tag = auditService.getAuditLevelName(id.toString(), businessType);
        if(tag.equals("pass")){
            assetStorage.setStatus(2);//审核完成并通过
        }else if(tag.equals("fail")){
            assetStorage.setStatus(3);//审核被拒绝
        }
        assetStorage.setCurAuditLevel(tag);
        assetStorage.setAuditDate(new Date());
        assetStorageDAO.store(assetStorage);
        return "success";
    }
    /**
     * Description 提交物资入库记录
     * @param id 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-4-2
     */
    @RequestMapping("/changeAssetsReceiveStatus")
    @ResponseBody
    public String changeAssetsReceiveStatus(@RequestParam Integer id,String result){
        AssetReceive assetReceive=assetReceiveDAO.findAssetReceiveById(id);
        assetReceive.setStatus(1);//提交审核
        //调用审核服务
        String businessType="AssetsClassification"+ "ReceiveAudit"+assetReceive.getCategoryId();
        //保存审核结果
        try {
            auditService.saveBusinessLevel(id.toString(), assetReceive.getCategoryId().toString(), result, "无", businessType, shareService.getUser().getUsername());
        }catch (Exception e){
            e.printStackTrace();
        }
        //获取审核后的状态结果
        String tag = auditService.getAuditLevelName(id.toString(), businessType);
        if(tag.equals("pass")){
            assetReceive.setStatus(2);//审核完成并通过
            Calendar calendar=Calendar.getInstance();
            assetReceive.setAuditDate(calendar);
        }else if(tag.equals("fail")){
            assetReceive.setStatus(3);//审核被拒绝
        }
        assetReceive.setCurAuditLevel(tag);
        assetReceiveDAO.store(assetReceive);
        return "success";
    }
    /**
     * Description 确认物资入库
     * @param id 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-4-2
     */
    @RequestMapping("/confirmAssetsInStorage")
    @ResponseBody
    public String confirmAssetsInStorage(@RequestParam Integer id){
        AssetStorage assetStorage=materialService.findAssetStorageById(id);
        assetStorage.setStatus(4);//确认入库
        assetStorageDAO.store(assetStorage);
        //更新库存并生成库存记录
        materialService.saveAssetsCabinetRecordFromInStorage(id);
        return "success";
    }
    /**
     * Description 确认物资领用
     * @param id 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-4-2
     */
    @RequestMapping("/confirmAssetsReceive")
    @ResponseBody
    public String confirmAssetsReceive(@RequestParam Integer id){
        AssetReceive assetReceive=assetReceiveDAO.findAssetReceiveById(id);
        assetReceive.setStatus(4);//确认领用
        Calendar calendar=Calendar.getInstance();
        assetReceive.setStartData(calendar);//更新领用时间为开始时间
        assetReceiveDAO.store(assetReceive);
        return "success";
    }
    /**
     * Description 确认物资归还
     * @param id 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-4-2
     */
    @RequestMapping("/confirmReturnAssetsReceive")
    @ResponseBody
    public String confirmReturnAssetsReceive(@RequestParam Integer id){
        AssetReceive assetReceive=assetReceiveDAO.findAssetReceiveById(id);
        assetReceive.setStatus(5);//确认归还
        Calendar calendar=Calendar.getInstance();
        assetReceive.setEndDate(calendar);//更新领用时间为开始时间
        assetReceiveDAO.store(assetReceive);
        return "success";
    }
    /**
     * Description 删除物资名录
     * @param id 物资分类id
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-3-25
     */
    @RequestMapping("/deleteAssets")
    @ResponseBody
    public String deleteAssets(Integer id){
        if(materialService.delAssets(id)){
            return "success";
        }else{
            return "fail";
        }
    }
    /**
     * Description 删除物资名录
     * @param id 物资分类id
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-3-25
     */
    @RequestMapping("/deleteAssetsApply")
    @ResponseBody
    public String deleteAssetsApply(Integer id){
       AssetApp assetApp=assetAppDAO.findAssetAppById(id);
       List<AssetAppRecord> assetAppRecordList=materialService.findAssetsAppRecordByAssetsApp(id);
        for(int i=0;i<assetAppRecordList.size();i++){
            AssetAppRecord assetAppRecord=assetAppRecordDAO.findAssetAppRecordById(assetAppRecordList.get(i).getId());//获取具体条目
            assetAppRecordDAO.remove(assetAppRecord);//删除条目记录
        }
       assetAppDAO.remove(assetApp);//删除申领记录
       return "success";
    }
    /**
     * Description 删除物资具体申购条目
     * @param id 物资分类id
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-3-25
     */
    @RequestMapping("/deleteAssetsApplyItem")
    @ResponseBody
    public String deleteAssetsApplyItem(Integer id){
       AssetAppRecord assetAppRecord=assetAppRecordDAO.findAssetAppRecordById(id);//获取具体条目
       AssetApp assetApp=assetAppDAO.findAssetAppById(assetAppRecord.getAssetApp().getId());//获取预约记录
        //价格计算
        BigDecimal bd=new BigDecimal(assetAppRecord.getTotalPrice().toString());
        Double price=bd.doubleValue();
        Double old_price=assetApp.getPrice();
        Double new_price=old_price-price;
        assetApp.setPrice(new_price);
       assetAppDAO.store(assetApp);//保存修改后的价格
       assetAppRecordDAO.remove(assetAppRecord);//删除记录
       return "success";
    }
    /**
     * Description 删除物资名录
     * @param id 物资分类id
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-3-25
     */
    @RequestMapping("/deleteAssetsInStorage")
    @ResponseBody
    public String deleteAssetsInStorage(Integer id){
        AssetStorage assetStorage=materialService.findAssetStorageById(id);
        List<AssetStorageRecord> assetStorageRecordList=materialService.findAssetsStorageRecordByAssetsStorage(id);
        for(int i=0;i<assetStorageRecordList.size();i++){
            AssetStorageRecord assetStorageRecord=materialService.findAssetStorageRecordById(assetStorageRecordList.get(i).getId());//获取具体条目
            assetStorageRecordDAO.remove(assetStorageRecord);//删除条目记录
        }
        assetStorageDAO.remove(assetStorage);//删除入库记录
        return "success";
    }
    /**
     * Description 删除物资具体申购条目
     * @param id 物资分类id
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-3-25
     */
    @RequestMapping("/deleteAssetsInStorageItem")
    @ResponseBody
    public String deleteAssetsInStorageItem(Integer id){
        AssetStorageRecord assetStorageRecord=materialService.findAssetStorageRecordById(id);//获取具体条目
        AssetStorage assetStorage=materialService.findAssetStorageById(assetStorageRecord.getStoreId());//获取预约记录
        //价格计算
        BigDecimal bd=new BigDecimal(assetStorageRecord.getTotalPrice().toString());
        Double price=bd.doubleValue();
        Double old_price=assetStorage.getTotalPrice();
        Double new_price=old_price-price;
        assetStorage.setTotalPrice(new_price);
        assetStorageDAO.store(assetStorage);//保存修改后的价格
        assetStorageRecordDAO.remove(assetStorageRecord);//删除记录
        return "success";
    }

    /**
     * Description 删除物资申领记录
     * @param id 物资分类id
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-4-18
     */
    @RequestMapping("/deleteAssetsReceive")
    @ResponseBody
    public String deleteAssetsReceive(Integer id){
        AssetReceive assetReceive=assetReceiveDAO.findAssetReceiveById(id);
        List<AssetReceiveRecord> assetReceiveRecordList=materialService.findAssetsReceiveRecordByAssetsReceive(id);
        for(int i=0;i<assetReceiveRecordList.size();i++){
            AssetReceiveRecord assetReceiveRecord=assetReceiveRecordDAO.findAssetReceiveRecordByPrimaryKey(assetReceiveRecordList.get(i).getId());//获取具体条目
            AssetCabinetRecord assetCabinetRecord=materialService.findAssetsCabinetRecordByCabinetAndAssets(assetReceiveRecord.getCabinetId(),assetReceiveRecord.getAsset().getId());//获取物品柜记录数
            //计算删除后的库存数并保存
            Integer stockNumber=assetCabinetRecord.getStockNumber()+assetReceiveRecord.getQuantity().intValue();
            assetCabinetRecord.setStockNumber(stockNumber);
            assetCabinetRecordDAO.store(assetCabinetRecord);
            assetReceiveRecordDAO.remove(assetReceiveRecord);//删除申领条目
        }
        assetReceiveDAO.remove(assetReceive);//删除申领记录
        return "success";
    }
    /**
     * Description 删除物资具体申购条目
     * @param id 物资分类id
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-3-25
     */
    @RequestMapping("/deleteAssetsReceiveItem")
    @ResponseBody
    public String deleteAssetsReceiveItem(Integer id){
        AssetReceiveRecord assetReceiveRecord=assetReceiveRecordDAO.findAssetReceiveRecordByPrimaryKey(id);//获取具体条目
        AssetCabinetRecord assetCabinetRecord=materialService.findAssetsCabinetRecordByCabinetAndAssets(assetReceiveRecord.getCabinetId(),assetReceiveRecord.getAsset().getId());//获取物品柜记录数
        //计算删除后的库存数并保存
        Integer stockNumber=assetCabinetRecord.getStockNumber()+assetReceiveRecord.getQuantity().intValue();
        assetCabinetRecord.setStockNumber(stockNumber);
        assetCabinetRecordDAO.store(assetCabinetRecord);
        //删除条目
        assetReceiveRecordDAO.remove(assetReceiveRecord);
        return "success";
    }
    /**
     * Description 编辑,查看物资名录
     * @param id 物资名录ID
     * @return json字符串格式的物资分类数据
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/editAssets")
    @ResponseBody
    public MaterialListDTO editAssets(String id){
        MaterialListDTO materialListDTO = materialService.findAssetById(Integer.parseInt(id));
        return materialListDTO;
    }

    /**
     * Description 编辑,查看物资名录
     * @param id 物资申请ID
     * @return json字符串格式的物资分类数据
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/editAssetsApply")
    @ResponseBody
    public AssetsApplyDTO editAssetsApply(Integer id)throws Exception{
        AssetsApplyDTO assetsApplyDTO = materialService.findAssetApplyDetailById(id);
        return assetsApplyDTO;
    }
    /**
     * Description 编辑,查看物资条目
     * @param id 物资申请ID
     * @return json字符串格式的物资分类数据
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/editAssetsApplyItem")
    @ResponseBody
    public AssetsApplyItemDTO editAssetsApplyItem(Integer id)throws Exception{
        AssetsApplyItemDTO AssetsApplyItemDTO = materialService.findAssetApplyItemDetailById(id);
        return AssetsApplyItemDTO;
    }

    /**
     * Description 编辑,新建物资名录
     * @param id 物资申请ID
     * @return json字符串格式的物资分类数据
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/editAssetsInStorage")
    @ResponseBody
    public AssetsInStorageDTO editAssetsInStorage(Integer id)throws Exception{
        AssetsInStorageDTO assetsInStorageDTO = materialService.findAssetInStorageDetailById(id);
        return assetsInStorageDTO;
    }

    /**
     * Description 编辑,查看物资条目
     * @param id 物资申请ID
     * @return json字符串格式的物资分类数据
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/editAssetsInStorageItem")
    @ResponseBody
    public AssetsApplyItemDTO editAssetsInStorageItem(Integer id)throws Exception{
        AssetsApplyItemDTO AssetsApplyItemDTO = materialService.findAssetInStorageItemDetailById(id);
        return AssetsApplyItemDTO;
    }


    /**
     * Description 编辑,新建物资名录
     * @param id 物资申请ID
     * @return json字符串格式的物资分类数据
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/editAssetsReceive")
    @ResponseBody
    public AssetsReceiveDTO editAssetsReceive(Integer id)throws Exception{
        AssetsReceiveDTO assetsReceiveDTO = materialService.findAssetReceiveDetailById(id);
        return assetsReceiveDTO;
    }

    /**
     * Description 编辑,查看物资条目
     * @param id 物资申请ID
     * @return json字符串格式的物资分类数据
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/editAssetsReceiveItem")
    @ResponseBody
    public AssetsApplyItemDTO editAssetsReceiveItem(Integer id)throws Exception{
        AssetsApplyItemDTO AssetsApplyItemDTO = materialService.findAssetReceiveItemDetailById(id);
        return AssetsApplyItemDTO;
    }

    /**
     * Description 获取所有物资类别
     *
     * @return json字符串格式的物资分类数据
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/assetsClassificationList")
    @ResponseBody
    public List<MaterialKindDTO> materialKindDTOList() {
        List<MaterialKindDTO> materialKindDTOList=new ArrayList<>();
        materialKindDTOList=materialService.findAllAssetClassificationList();
        return materialKindDTOList;
    }

    /**
     * Description 获取所有物品柜
     *
     * @return json字符串格式的物资分类数据
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/assetsCabinetList")
    @ResponseBody
    public List<AssetsCabinetDTO> assetsCabinetList(String assetId) {
        List<AssetsCabinetDTO> assetsCabinetDTOList=new ArrayList<>();
        assetsCabinetDTOList=materialService.findAllAssetCabinetList(assetId);
        return assetsCabinetDTOList;
    }
    /**
     * Description 获取所有物资名录
     *
     * @return json字符串格式的物资分类数据
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/assetsList")
    @ResponseBody
    public List<MaterialListDTO> assetsList() {
        List<MaterialListDTO> materialListDTOList=new ArrayList<>();
        materialListDTOList=materialService.findAllAssets();
        return materialListDTOList;
    }

    /**
     * Description 获取学院
     *
     * @return json字符串格式的物资分类数据
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/schoolAcademyList")
    @ResponseBody
    public List<SchoolAcademy> schoolAcademyList() {
        List<SchoolAcademy> schoolAcademyList=new ArrayList<>();
        schoolAcademyList=materialService.findAllSchoolAcademyList();
        return schoolAcademyList;
    }

    /**
     * Description 获取中心
     *
     * @return json字符串格式的物资分类数据
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/labCenterList")
    @ResponseBody
    public List<LabCenter> labCenterList() {
        List<LabCenter> labCenterList=new ArrayList<>();
        labCenterList=materialService.findAllLabCenterList();
        return labCenterList;
    }

    /**
     * 判断该类物资是否需要归还
     * * @return
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/checkAssetsClassificationIsNeedReturn")
    @ResponseBody
    public Integer checkAssetsClassificationIsNeedReturn( String id){
        Integer isNeedReturn =materialService.checkAssetsClassificationIsNeedReturn(Integer.parseInt(id));
        return isNeedReturn;
    }
    /**
     * 获得入库申请单所需信息
     * * @return
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping(value = "/getInStorageCheckListInfo",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getInStorageCheckListInfo( String appId){
        JSONObject jsonObject=materialService.findAllAssetInStorageItem(Integer.parseInt(appId));
        return jsonObject.toJSONString();
    }
    /**
     * 获得领(发)料单所需信息
     * * @return
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping(value = "/getReceiveCheckListInfo",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getReceiveCheckListInfo(String appId){
        JSONObject jsonObject=materialService.findAllAssetReceiveItem(Integer.parseInt(appId));
        return jsonObject.toJSONString();
    }
    /**
     * 判断该类物资是否需要归还
     * * @return
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/getAssetsAmountFromCabinet")
    @ResponseBody
    public Integer getAssetsAmountFromCabinet(Integer id,Integer assetId,Integer quantity,Integer itemId){
        Integer amount =materialService.getAssetsAmountFromCabinet(id,assetId,quantity,itemId);
        return amount;
    }
    /**
     * 上传图片接口
     * * @return
     * @author 吴奇臻 2019-4-8
     */
    @RequestMapping("/uploadAssetsPic")
    @ResponseBody
    public JSONObject uploadAssetsPic(HttpServletRequest request, Integer id) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        String sep = System.getProperty("file.separator");
        Map files = multipartRequest.getFileMap();
        Iterator fileNames = multipartRequest.getFileNames();
        boolean flag =false;
        //文件存放文件夹
        String fileDir = request.getSession().getServletContext().getRealPath( "/") +  "upload"+ sep+"assetsPic";
        //所用图片链接
        List<String> imageUrls=new ArrayList<>();
        //存放文件文件夹名称
        for(; fileNames.hasNext();){
            String filename = (String) fileNames.next();
            CommonsMultipartFile file = (CommonsMultipartFile) files.get(filename);
            byte[] bytes = file.getBytes();
            if(bytes.length != 0) {
                // 说明申请有附件
                if(!flag) {
                    File dirPath = new File(fileDir);
                    if(!dirPath.exists()) {
                        flag = dirPath.mkdirs();
                    }
                }
                //文件名
                String fileTrueName = file.getOriginalFilename();
                //文件重命名
                int endAddress = fileTrueName.lastIndexOf(".");
                String ss = fileTrueName.substring(endAddress, fileTrueName.length());//后缀名
                //文件名称
                String fileNewName = "assetsPic"+id+fileTrueName;
                File uploadedFile = new File(fileDir + sep + fileNewName);
                try {
                    FileCopyUtils.copy(bytes,uploadedFile);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                imageUrls.add(fileNewName);
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", imageUrls);
        jsonObject.put("code", 0);
        return jsonObject;
    }

    /**
     * 针对申购入库的上传图片接口
     * * @return
     * @author 吴奇臻 2019-5-9
     */
    @RequestMapping("/uploadAssetsPicForApply")
    @ResponseBody
    public JSONObject uploadAssetsPicForApply(HttpServletRequest request, Integer id) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        String sep = System.getProperty("file.separator");
        Map files = multipartRequest.getFileMap();
        Iterator fileNames = multipartRequest.getFileNames();
        boolean flag =false;
        //文件存放文件夹
        String fileDir = request.getSession().getServletContext().getRealPath( "/") +  "upload"+ sep+"assetsPic";
        //所用图片链接
        List<String> imageUrls=new ArrayList<>();
        //存放文件文件夹名称
        for(; fileNames.hasNext();){
            String filename = (String) fileNames.next();
            CommonsMultipartFile file = (CommonsMultipartFile) files.get(filename);
            byte[] bytes = file.getBytes();
            if(bytes.length != 0) {
                // 说明申请有附件
                if(!flag) {
                    File dirPath = new File(fileDir);
                    if(!dirPath.exists()) {
                        flag = dirPath.mkdirs();
                    }
                }
                //文件名
                String fileTrueName = file.getOriginalFilename();
                //文件重命名
                int endAddress = fileTrueName.lastIndexOf(".");
                String ss = fileTrueName.substring(endAddress, fileTrueName.length());//后缀名
                //文件名称
                String fileNewName = "assetsPic"+id+fileTrueName;
                File uploadedFile = new File(fileDir + sep + fileNewName);
                try {
                    FileCopyUtils.copy(bytes,uploadedFile);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //将图片跟assetApply记录相关联
                materialService.saveAssetsRelatedImage("/limsproduct/upload/assetsPic/"+fileNewName,fileTrueName,"100kb",id,"putAssetInStorage");
                imageUrls.add(fileNewName);
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", imageUrls);
        jsonObject.put("code", 0);
        return jsonObject;
    }
    /**
     * 判断该类物资是否需要归还
     * * @return
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/getAssetsRelatedImage")
    @ResponseBody
    public List<AssetRelatedImage> getAssetsRelatedImage(Integer id,String type){
        List<AssetRelatedImage> imageList=new ArrayList<>();
        imageList=materialService.getAssetsRelatedImage(id,type);
        return imageList;
    }
    /**
     * 物资名录列表API
     * * @return 跳转页面
     * @author 吴奇臻 2019-3-124
     */
    @RequestMapping("/listAssetsAPI")
    public ModelAndView listAssetsAPI(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("lims/material/listAssets.jsp");
        return mav;
    }
    /**
     * 查看物资名录详情API
     * * @return 跳转页面
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/assetsDetailAPI")
    public ModelAndView assetsDetailAPI(@RequestParam String id){
        ModelAndView mav = new ModelAndView();
        mav.addObject("id",id);
        mav.setViewName("lims/material/assetsDetail.jsp");
        return mav;
    }
    /**
     * 新建/编辑物资名录详情API
     * * @return 跳转页面
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/editAssetsDetailAPI")
    public ModelAndView editAssetsDetailAPI( String id){
        ModelAndView mav = new ModelAndView();
        mav.addObject("id",id);
        mav.setViewName("lims/material/editAssetsDetail.jsp");
        return mav;
    }
    /**
     * 物资申购记录列表API
     * * @return 跳转页面
     * @author 吴奇臻 2019-3-124
     */
    @RequestMapping("/listAssetsApplyAPI")
    public ModelAndView listAssetsApplyAPI(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("lims/material/listAssetsApply.jsp");
        return mav;
    }
    /**
     * 新建/编辑物资申购详情API
     * * @return 跳转页面
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/editAssetsApplyDetailAPI")
    public ModelAndView editAssetsApplyAPI( String id){
        ModelAndView mav = new ModelAndView();
        mav.addObject("id",id);
        mav.setViewName("lims/material/editAssetsApplyDetail.jsp");
        return mav;
    }

    /**
     * 查看/审核物资申购详情API
     * * @return 跳转页面
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/checkAssetsApplyDetailAPI")
    public ModelAndView checkAssetsApplyDetailAPI( String id){
        ModelAndView mav = new ModelAndView();
        mav.addObject("id",id);
        mav.setViewName("lims/material/assetsApplyDetail.jsp");
        return mav;
    }
    /**
     * 查看/审核物资申购详情API
     * * @return 跳转页面
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/checkAssetsInStorageDetailAPI")
    public ModelAndView checkAssetsInStorageDetailAPI( String id){
        ModelAndView mav = new ModelAndView();
        mav.addObject("id",id);
        mav.setViewName("lims/material/assetsInStorageDetail.jsp");
        return mav;
    }
    /**
     * 查看/审核物资申购详情API
     * * @return 跳转页面
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/checkAssetsReceiveDetailAPI")
    public ModelAndView checkAssetsReceiveDetailAPI( String id){
        ModelAndView mav = new ModelAndView();
        mav.addObject("id",id);
        mav.setViewName("lims/material/assetsReceiveDetail.jsp");
        return mav;
    }
    /**
     * 添加物资名录详情API(申购）
     * * @return 跳转页面
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/addAssetsApplyItemAPI")
    public ModelAndView addAssetsApplyItemAPI( String id,String appId){
        ModelAndView mav = new ModelAndView();
        mav.addObject("id",id);
        mav.addObject("appId",appId);
        mav.setViewName("lims/material/addAssetsApplyItem.jsp");
        return mav;
    }
    /**
     * 添加物资名录详情API(申购）
     * * @return 跳转页面
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/editAddAssetsApplyItemAPI")
    public ModelAndView editAddAssetsApplyItemAPI( String id){
        ModelAndView mav = new ModelAndView();
        mav.addObject("id",id);
        mav.setViewName("lims/material/editAddAssetsApplyItemAPI.jsp");
        return mav;
    }
    /**
     * 添加物资名录详情API（入库）
     * * @return 跳转页面
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/addAssetsInStorageItemAPI")
    public ModelAndView addAssetsInStorageItemAPI( String id,String storeId){
        ModelAndView mav = new ModelAndView();
        mav.addObject("id",id);
        mav.addObject("storeId",storeId);
        mav.setViewName("lims/material/addAssetsInStorageItem.jsp");
        return mav;
    }
    /**
     * 添加物资名录详情API（申领）
     * * @return 跳转页面
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/addAssetsReceiveItemAPI")
    public ModelAndView addAssetsReceiveItemAPI( String id,String receiveId){
        ModelAndView mav = new ModelAndView();
        mav.addObject("id",id);
        mav.addObject("receiveId",receiveId);
        mav.setViewName("lims/material/addAssetsReceiveItem.jsp");
        return mav;
    }
    /**
     * 发起入库API
     * * @return 跳转页面
     * @author 吴奇臻 2019-3-24
     */
    @RequestMapping("/putAssetsInStorageAPI")
    public ModelAndView putAssetsInStorageAPI( String id){
        ModelAndView mav = new ModelAndView();
        mav.addObject("id",id);
        mav.setViewName("lims/material/putAssetsInStorage.jsp");
        return mav;
    }
    /**
     * 查看物资入库列表
     * * @return 跳转页面
     * @author 吴奇臻 2019-4-1
     */
    @RequestMapping("/listAssetsInStorageAPI")
    public ModelAndView listAssetsInStorageAPI(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("lims/material/listAssetsInStorage.jsp");
        return mav;
    }
    /**
     * 新建物资入库
     * * @return 跳转页面
     * @author 吴奇臻 2019-4-1
     */
    @RequestMapping("/editAssetsInStorageDetailAPI")
    public ModelAndView editAssetsInStorageDetailAPI(String id){
        ModelAndView mav = new ModelAndView();
        mav.addObject("id",id);
        mav.setViewName("lims/material/editAssetsInStorageDetail.jsp");
        return mav;
    }
    /**
     * 物资领用记录列表API
     * * @return 跳转页面
     * @author 吴奇臻 2019-3-124
     */
    @RequestMapping("/listAssetsReceiveAPI")
    public ModelAndView listAssetsReceiveAPI(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("lims/material/listAssetsReceive.jsp");
        return mav;
    }
    /**
     * 新建物资申领
     * * @return 跳转页面
     * @author 吴奇臻 2019-4-1
     */
    @RequestMapping("/editAssetsReceiveDetailAPI")
    public ModelAndView editAssetsReceiveDetailAPI(String id){
        ModelAndView mav = new ModelAndView();
        mav.addObject("id",id);
        mav.setViewName("lims/material/editAssetsReceiveDetail.jsp");
        return mav;
    }
    /**
     * 选择物品柜
     * * @return
     * @author 吴奇臻 2019-4-1
     */
    @ResponseBody
    @RequestMapping("/chooseAssetsCabinetForApply")
    public String chooseAssetsCabinetForApply(@RequestParam Integer id,@RequestParam Integer cabinet){
        AssetAppRecord assetAppRecord=assetAppRecordDAO.findAssetAppRecordById(id);
        assetAppRecord.setCabinetId(cabinet);
        assetAppRecordDAO.store(assetAppRecord);
        return "success";
    }
    /**
     * 选择物品柜
     * * @return 跳转页面
     * @author 吴奇臻 2019-4-9
     */
    @RequestMapping("/chooseAssetsCabinetAPI")
    public ModelAndView chooseCabinetAPI(String id){
        ModelAndView mav = new ModelAndView();
        mav.addObject("id",id);
        mav.setViewName("lims/material/chooseAssetsCabinet.jsp");
        return mav;
    }
    /**
     * 生成入库单
     * * @return 跳转页面
     * @author 吴奇臻 2019-4-29
     */
    @RequestMapping("/assetsInStorageCheckList")
    public ModelAndView assetsInStorageCheckList(Integer id){
        ModelAndView mav = new ModelAndView();
        mav.addObject("id",id);
        mav.setViewName("lims/material/assetsInStorageCheckList.jsp");
        return mav;
    }
    /**
     * 生成领发料
     * * @return 跳转页面
     * @author 吴奇臻 2019-4-30
     */
    @RequestMapping("/assetsReceiveCheckList")
    public ModelAndView assetsReceiveCheckList(Integer id){
        ModelAndView mav = new ModelAndView();
        mav.addObject("id",id);
        mav.setViewName("lims/material/assetsReceiveCheckList.jsp");
        return mav;
    }
    /**
     * Description 物资分类列表API
     * @return 跳转页面
     * @author 伍菁 2019-4-1
     */
    @RequestMapping("/listAssetsClassificationAPI")
    public ModelAndView listAssetsClassificationAPI(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("lims/material/listAssetsClassification.jsp");
        return mav;
    }
    /**
     * Description 获取物资类别列表
     * @param page
     * @param limit
     * @param cname
     * @return json字符串格式的物资分类数据
     * @author 伍菁 2019-4-2
     */
    @RequestMapping("/listAssetsClassification")
    @ResponseBody
    public String materialKindDTOList(Integer page,Integer limit,String cname) {
        JSONObject jsonObject = materialService.findAssetClassificationList(page,limit,cname);
        return shareService.htmlEncode(jsonObject.toJSONString());
    }
    /**
     * 新建/编辑物资类别详情API
     * @return 跳转页面
     * @author 伍菁 2019-4-2
     */
    @RequestMapping("/newAssetsClassificationAPI")
    public ModelAndView newAssetsClassificationAPI( String id){
        ModelAndView mav = new ModelAndView();
        mav.addObject("id",id);
        mav.setViewName("lims/material/newAssetsClassification.jsp");
        return mav;
    }
    /**
     * Description 保存物资类别
     * @param materialKindDTO 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 伍菁 2019-4-2
     */
    @RequestMapping("/saveAssetClassification")
    @ResponseBody
    public String saveAssetClassification(@RequestBody MaterialKindDTO materialKindDTO){
        if(materialService.saveAssetClassification(materialKindDTO)){
            return "success";
        }else{
            return "fail";
        }
    }
    /**
     * Description 编辑,查看物资类别
     * @param id 物资类别ID
     * @return json字符串格式的物资类别数据
     * @author 伍菁 2019-4-3
     */
    @RequestMapping("/editAssetClassification")
    @ResponseBody
    public MaterialKindDTO editAssetClassification(@RequestParam String id){
        MaterialKindDTO materialKindDTO = materialService.findAssetClassificationById(Integer.valueOf(id));
        return materialKindDTO;
    }
    /**
     * 查看物资类别详情API
     * @return 跳转页面
     * @author 伍菁 2019-4-4
     */
    @RequestMapping("/assetClassificationDetailAPI")
    public ModelAndView assetClassificationDetailAPI(@RequestParam String id){
        ModelAndView mav = new ModelAndView();
        mav.addObject("id",id);
        mav.setViewName("lims/material/assetsClassificationDetail.jsp");
        return mav;
    }
    /**
     * Description 删除物资类别
     * @param id 物资分类id
     * @return 成功-"success"，失败-"fail"
     * @author 伍菁 2019-4-7
     */
    @RequestMapping("/deleteAssetsClassification")
    @ResponseBody
    public String deleteAssetsClassification(Integer id){
        if(materialService.deleteAssetsClassification(id)){
            return "success";
        }else{
            return "fail";
        }
    }
    /**
     * Description 物资记录列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * @return json字符串格式的物资记录列表
     * @author 伍菁 2019-04-16
     **/
    @ResponseBody
    @RequestMapping("/assetCabinetRecordList")
    public String assetCabinetRecordList(@RequestParam Integer page,@RequestParam Integer limit,String cas){
        JSONObject jsonObject = materialService.findAllAssetCabinetRecordList(page,limit,cas);
        return shareService.htmlEncode(jsonObject.toJSONString());
    }
    /**
     * Description 物资记录列表API
     * @return 跳转页面
     * @author 伍菁 2019-04-17
     **/
    @RequestMapping("/listAssetCabinetRecordAPI")
    public ModelAndView listAssetCabinetRecordAPI(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("lims/material/listAssetCabinetRecord.jsp");
        return mav;
    }
    /**
     * Description 物资记录详情API
     * @return 跳转页面
     * @author 伍菁 2019-04-17
     **/
    @RequestMapping("/assetCabinetRecordDetailAPI")
    public ModelAndView assetCabinetRecordDetailAPI(@RequestParam String assetId,@RequestParam String type){
        ModelAndView mav = new ModelAndView();
        mav.addObject("assetId",assetId);
        mav.addObject("type",type);
        mav.setViewName("lims/material/assetCabinetRecordDetail.jsp");
        return mav;
    }
    /**
     * Description 查看物资记录详情
     * @param assetId 物资记录物资编号
     * @return json字符串格式的物资记录详情数据
     * @author 伍菁 2019-04-18
     **/
    @ResponseBody
    @RequestMapping("/viewAssetCabinetRecordDetails")
    public AssetCabinetRecordDTO viewAssetCabinetRecordDetails(@RequestParam String assetId){
        AssetCabinetRecordDTO assetCabinetRecordDTO = materialService.findAssetCabinetRecordDetailsByAssetId(Integer.valueOf(assetId));
        return assetCabinetRecordDTO;
    }
    /**
     * Description 增加物资记录
     * @param assetId 物资记录物资编号
     * @param cname 物资名称
     * @return 跳转页面
     * @author 伍菁 2019-04-22
     **/
    @RequestMapping("/addAssetCabinetRecordAPI")
    public ModelAndView addAssetCabinetRecordAPI(@RequestParam String assetId,String cname){
        ModelAndView mav = new ModelAndView();
        mav.addObject("assetId",assetId);
        mav.addObject("cname",cname);
        mav.setViewName("lims/material/addAssetCabinetRecord.jsp");
        return mav;
    }
    /**
     * Description 获取全部物品柜
     * @return AssetsCabinetDTO 物品柜DTO
     * @author 伍菁 2019-04-22
     **/
    @ResponseBody
    @RequestMapping("/getAllAssetsCabinet")
    public List<AssetsCabinetDTO> getAllAssetsCabinet(){
        List<AssetsCabinetDTO> assetsCabinetDTOList = materialService.getAllAssetsCabinet();
        return assetsCabinetDTOList;
    }
    /**
     * Description 保存物资记录
     * @param  assetCabinetRecordDTO 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 伍菁 2019-04-22
     **/
    @ResponseBody
    @RequestMapping("/saveAssetsCabinetRecord")
    public String saveAssetsCabinetRecord(@RequestBody AssetCabinetRecordDTO assetCabinetRecordDTO,Integer id){
        if(materialService.saveAssetsCabinetRecord(assetCabinetRecordDTO,id)){
            return "success";
        }else{
            return "fail";
        }
    }
    /**
     * Description 删除物资记录详情
     * @param id 物资记录id
     * @return 成功-"success"，失败-"fail"
     * @author 伍菁 2019-4-22
     */
    @RequestMapping("/deleteAssetCabinetRecordDetail")
    @ResponseBody
    public String deleteAssetCabinetRecordDetail(Integer id){
        if(materialService.deleteAssetCabinetRecordDetail(id)){
            return "success";
        }else{
            return "fail";
        }
    }
    /**
     * Description 删除物资记录
     * @param assetId 物资编号
     * @return 成功-"success"，失败-"fail"
     * @author 伍菁 2019-4-22
     */
    @RequestMapping("/deleteAssetCabinetRecords")
    @ResponseBody
    public String deleteAssetCabinetRecords(Integer assetId) {
        if (materialService.deleteAssetCabinetRecords(assetId)) {
            return "success";
        } else {
            return "fail";
        }
    }
}
