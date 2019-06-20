package net.gvsun.lims.service.assets;

import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.assets.*;
import net.zjcclims.domain.*;
import org.python.antlr.op.In;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MaterialService {
    /**
     * 物资分类列表

     * * @return MaterialKindDTO
     * @author 吴奇臻 2019-03-27
     */
    List<MaterialKindDTO> findAllAssetClassificationList();

    /**
     * 物品柜列表

     * * @return MaterialKindDTO
     * @author 吴奇臻 2019-03-31
     */
    List<AssetsCabinetDTO> findAllAssetCabinetList(String assetId);

    /**
     * 物品柜门列表
     * @author 吴奇臻 2019-06-05
     */
    List<AssetsCabinetWareHouseDTO> findAllAssetCabinetWareHouseByCabinetId(Integer cabinetId);

    /**
     * 物资列表

     * * @return MaterialKindDTO
     * @author 吴奇臻 2019-03-27
     */
    List<MaterialListDTO> findAllAssets();

    /**
     * 获取所有学院

     * * @return SchoolAcademy
     * @author 吴奇臻 2019-03-27
     */
    List<SchoolAcademy> findAllSchoolAcademyList();
    /**
     * 获取所有物联服务器

     * * @return SchoolAcademy
     * @author 吴奇臻 2019-05-29
     */
    List<CommonServer> findAllCommonServerList();

    /**
     * 获取所有中心

     * * @return SchoolAcademy
     * @author 吴奇臻 2019-03-27
     */
    List<LabCenter> findAllLabCenterList();

    /**
     * 物资分类列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return 状态字符串
     * @author 吴奇臻 2019-3-26
     */
    JSONObject findAllAssetCabinetList(Integer page, Integer limit);
    /**
     * 物资分类列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return 状态字符串
     * @author 吴奇臻 2019-3-26
     */
    JSONObject findAllAssetList(Integer page, Integer limit,String keywords,String kind);

    /**
     * 物资申购列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return 状态字符串
     * @author 吴奇臻 2019-3-26
     */
    JSONObject findAllAssetApplyList(Integer page, Integer limit,String status,String kind,HttpServletRequest request);

    /**
     * 物资入库列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-1
     */
    JSONObject findAllAssetInStorageList(Integer page, Integer limit,String status,HttpServletRequest request);

    /**
     * 物资申领列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-1
     */
    JSONObject findAllAssetReceiveList(Integer page, Integer limit,String status,HttpServletRequest request);

    /**
     * 物资申购条目列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return 状态字符串
     * @author 吴奇臻 2019-3-26
     */
    JSONObject findAllAssetApplyItemList(Integer page, Integer limit,Integer id);
    /**
     * 物资申购条目列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return 状态字符串
     * @author 吴奇臻 2019-3-26
     */
    JSONObject findAllAssetInStorageItemList(Integer page, Integer limit,Integer id);

    /**
     * 物资入库单信息
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-30
     */
    JSONObject findAllAssetInStorageItem(Integer appId);

    /**
     * 物资申领条目列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * @author 吴奇臻 2019-3-26
     */
    JSONObject findAllAssetReceiveItemList(Integer page, Integer limit,Integer id);


    /**
     * 智能物品柜具体信息列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * @author 吴奇臻 2019-5-29
     */
    JSONObject findAllCabinetWareHoustList(Integer page, Integer limit,Integer id);

    /**
     * 物资出入库记录列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * @author 吴奇臻 2019-5-15
     */
    JSONObject findAllAssetCabinetAccessRecordList(Integer page, Integer limit,Integer id);
    /**
     * 物资申领条目列表
     * * @return 状态字符串
     * @author 吴奇臻 2019-3-26
     */
    JSONObject findAllAssetReceiveItem(Integer appId);
    /**
     * Description 保存物资名录
     * @param materialListDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-3-6
     */
    Asset saveAssetsDetail(MaterialListDTO materialListDTO);

    /**
     * Description 通过申领或入库条目保存物资
     * @param assetsApplyItemDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-3-6
     */
    String saveAssetsDetailFromItem(AssetsApplyItemDTO assetsApplyItemDTO);

    /**
     * Description 保存物资申购条目
     * @param assetsApplyItemDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-3-6
     */
    boolean saveAddAssetsDetail(AssetsApplyItemDTO assetsApplyItemDTO);

    /**
     * Description 保存物资入库条目
     * @param assetsApplyItemDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-3-6
     */
    boolean saveAddAssetsInStorageDetail(AssetsApplyItemDTO assetsApplyItemDTO);

    /**
     * Description 保存智能柜具体信息
     *
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-5-29
     */
    boolean saveCabinetWareHouseDetail(AssetsCabinetWareHouseDTO assetsCabinetWareHouseDTO);

    /**
     * Description 保存物资入库条目
     * @param assetsApplyItemDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-3-6
     */
    boolean saveAddAssetsReceiveDetail(AssetsApplyItemDTO assetsApplyItemDTO);

    /**
     * Description 保存物资申购条目
     * @param assetsApplyItemDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-3-29
     */
    boolean saveAssetsAppRecordDetail(AssetsApplyItemDTO assetsApplyItemDTO);
    /**
     * Description 保存物资申购条目
     * @param assetsApplyItemDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-3-29
     */
    boolean saveAssetsInStorageRecordDetail(AssetsApplyItemDTO assetsApplyItemDTO);

    /**
     * Description 保存物资入库记录
     * @param assetsInStorageDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-3-31
     */
    Integer saveAssetsInStorageDetail(AssetsInStorageDTO assetsInStorageDTO);
    /**
     * Description 保存物资申购信息
     * @param assetsApplyDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-3-6
     */
    Integer saveAssetsApplyDetail(AssetsApplyDTO assetsApplyDTO);

    /**
     * Description 保存物资申领信息
     * @param assetsReceiveDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-4-3
     */
    Integer saveAssetsReceiveDetail(AssetsReceiveDTO assetsReceiveDTO);


    /**
     * Description 删除物资名录
     * @param id 物资分类id
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-3-25
     */
    boolean delAssets(Integer id);

    /**
     * 根据id获取入库记录
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-3-27
     */
    AssetStorage findAssetStorageById(Integer id);

    /**
     * 根据id获取入库详细条目记录
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-3-27
     */
    AssetStorageRecord findAssetStorageRecordById(Integer id);
    /**
     * 根据id获物资分类数据
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-3-27
     */
    MaterialListDTO findAssetById(Integer id);

    /**
     * 根据id获取物品柜数据
     *
     * @author 吴奇臻 2019-3-27
     */
    AssetsCabinetDTO findAssetsCabinetById(Integer id);

    /**
     * 根据id获物资申购数据
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-3-27
     */
    AssetsApplyDTO findAssetApplyDetailById(Integer id) throws Exception;

    /**
     * 根据id获物资申购条目具体信息
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-11
     */
    AssetsApplyItemDTO findAssetApplyItemDetailById(Integer id) throws Exception;

    /**
     * 根据id获物资申购条目具体信息
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-11
     */
    AssetsApplyItemDTO findAssetInStorageItemDetailById(Integer id) throws Exception;

    /**
     * 根据id获物资申购条目具体信息
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-11
     */
    AssetsApplyItemDTO findAssetReceiveItemDetailById(Integer id) throws Exception;

    /**
     * 根据id获物资入库
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-1
     */
    AssetsInStorageDTO findAssetInStorageDetailById(Integer id) throws Exception;

    /**
     * 根据id获物资申购数据
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-3-27
     */
    AssetsReceiveDTO findAssetReceiveDetailById(Integer id) throws Exception;

    /**
     * 根据入库id更新物品柜资源
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-2
     */
    void saveAssetsCabinetRecordFromInStorage(Integer id);

    /**
     * 根据申领id生成出入库记录
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-2
     */
    void saveAssetsCabinetRecordFromReceive(Integer id);

    /**
     * 根据物品柜id和物资id确认物品柜物资记录
     * =
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-2
     */
    AssetCabinetRecord findAssetsCabinetRecordByCabinetAndAssets(Integer cabinetId, Integer assetsId);

    /**
     * 根据物品柜id和物资id确认智能柜具体物资记录
     * =
     * * @return 状态字符串
     * @author 吴奇臻 2019-6-10
     */
    List<AssetCabinetWarehouseRecord> findAssetsCabinetWarehouseRecordByCabinetAndAssets(Integer cabinetId, Integer assetsId);



    /**
     * 根据柜门id和物资id确认物品柜物资记录
     * =
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-2
     */
    AssetCabinetWarehouseRecord findAssetsCabinetWarehouseRecordByWareHouseAndAssets(Integer warehouseId, Integer assetsId);

    /**
     * 根据申购ID获取所有申购条目
     *
     * @author 吴奇臻 2019-4-18
     */
    List<AssetAppRecord> findAssetsAppRecordByAssetsApp(Integer appId);

    /**
     * 根据申购ID获取所有申购条目
     *
     * @author 吴奇臻 2019-4-18
     */
    List<AssetStorageRecord> findAssetsStorageRecordByAssetsStorage(Integer storeId);

    /**
     * 根据申领ID获取所有申购条目
     *
     * @author 吴奇臻 2019-4-18
     */
    List<AssetReceiveRecord> findAssetsReceiveRecordByAssetsReceive(Integer receiveId);
    /**
     * 根据申领ID获取所有申领物资是否需要归还状态
     *
     * @author 吴奇臻 2019-6-20
     */
    int findAssetsClassificationReturnStatusByAssetsReceive(Integer receiveId);

    /**
     * 根据物资类别判断是否需要进行归还流程
     * @param id
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-2
     */
    Integer checkAssetsClassificationIsNeedReturn(Integer id);

    /**
     * 根据物资id,和物品柜id判断剩余物资数量
     * @param id
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-2
     */
    Integer getAssetsAmountFromCabinet(Integer id,Integer assetId,Integer quantity,Integer itemId);

    /**
     * 保存物资余料归还数
     *
     * * @return 状态字符串
     * @author 吴奇臻 2019-5-10
     */
    String saveReturnAssetsRemain(Integer amount, Integer id);

    /**
     * 保存图片记录
     *
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-8
     */
    String saveAssetsRelatedImage(String url,String name,String size,Integer appId,String type);



    /**
     * 删除图片记录
     *
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-8
     */
    void deleteAssetsRelatedImage(Integer appId,String type);

    /**
     * 获取图片记录
     *
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-8
     */
    List<AssetRelatedImage> getAssetsRelatedImage(Integer id,String type);

    /**
     * 获取审核标志位
     *
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-8
     */
    boolean getAssetsApplyAuditFlag(String tag,Integer status);

    /**
     * 根据物资及数量自动分配物品柜，更新物品柜数量，并返回状态
     *
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-8
     */
    String allocateCabinetFromAssets(Integer assetsId,Integer quantity,Integer itemId,Integer appId);

    /**
     * 根据物资及数量自动获取库存数最大的物品柜
     *
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-8
     */
    Integer getMaxAmountCabinetFromAssets(Integer assetsId,Integer quantity);


    /**
     * Description 获取物资类别列表
     * @param page
     * @param limit
     * @param cname
     * @return json字符串格式的物资分类数据
     * @author 伍菁 2019-4-2
     */
    JSONObject findAssetClassificationList(Integer page, Integer limit, String cname);
    /**
     * Description 保存物资类别
     * @param materialKindDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 伍菁 2019-4-2
     */
    boolean saveAssetClassification(MaterialKindDTO materialKindDTO);

    /**
     * Description 保存物品柜
     * @author 吴奇臻 2019-5-15
     */
    boolean saveAssetsCabinet(AssetsCabinetDTO assetsCabinetDTO);
    /**
     * Description 编辑,查看物资类别
     * @param id 物资类别ID
     * @return json字符串格式的物资类别数据
     * @author 伍菁 2019-4-3
     */
    MaterialKindDTO findAssetClassificationById(Integer id);
    /**
     * Description 删除物资类别
     * @param id 物资分类id
     * @return 成功-"success"，失败-"fail"
     * @author 伍菁 2019-4-7
     */
    boolean deleteAssetsClassification(Integer id);
    /**
     * 物资记录列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * @return json字符串格式的物资记录列表数据
     * @author 伍菁 2019-04-16
     */
    JSONObject findAllAssetCabinetRecordList(Integer page,Integer limit,String cas);
    /**
     * 根据assetId获物资记录数据
     * @param assetId 物资记录物资编号
     * @return json字符串格式的物资记录详情数据
     * @author 伍菁 2019-04-18
     */
    AssetCabinetRecordDTO findAssetCabinetRecordDetailsByAssetId(Integer assetId);
    /**
     * Description 获取全部物品柜
     * @return AssetsCabinetDTO 物品柜DTO
     * @author 伍菁 2019-04-22
     **/
    List<AssetsCabinetDTO> getAllAssetsCabinet();
    /**
     * Description 保存物资记录
     * @param  assetCabinetRecordDTO 参数封装DTO
     * @return 保存成功-true，失败-false
     * @author 伍菁 2019-04-22
     **/
    boolean saveAssetsCabinetRecord(AssetCabinetRecordDTO assetCabinetRecordDTO,Integer id);
    /**
     * Description 删除物资记录详情
     * @param id 物资记录id
     * @return 成功-"success"，失败-"fail"
     * @author 伍菁 2019-4-22
     */
    boolean deleteAssetCabinetRecordDetail(Integer id);
    /**
     * Description 删除物资记录
     * @param assetId 物资编号
     * @return 成功-"success"，失败-"fail"
     * @author 伍菁 2019-4-22
     */
    boolean deleteAssetCabinetRecords(Integer assetId);

    /**
     * Description 获取申购、入库、申领后三位编号
     * @author 吴奇臻 2019-5-21
     */
    String getAssetsRelatedAppNo(String type);

    /**
     * Description 审核拒绝返回申领的数量
     * @author 吴奇臻 2019-5-22
     */
    void returnAssetsReceiveItemAmount(Integer receiveId);
    /**
     * Description 根据申领编号获取申领审核人
     * @author 吴奇臻 2019-6-04
     */
    List<Object[]> getAuditLevelUser(Integer receiveId);
    /**
     * Description 生成物资申领的通知消息
     * @author 吴奇臻 2019-6-04
     */
    void setMessageInfoFroAssetsReceive(Integer receiveId);
}
