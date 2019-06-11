package net.gvsun.lims.service.assets;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.assets.*;
import net.gvsun.lims.service.auditServer.AuditService;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.zjcclims.web.common.PConfig;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("MaterialService")
public class MaterialServiceImpl implements MaterialService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    AssetDAO assetDAO;
    @Autowired
    AssetAppDAO assetAppDAO;
    @Autowired
    AssetAppRecordDAO assetAppRecordDAO;
    @Autowired
    AssetStorageRecordDAO assetStorageRecordDAO;
    @Autowired
    AssetCabinetRecordDAO assetCabinetRecordDAO;
    @Autowired
    AssetReceiveDAO assetReceiveDAO;
    @Autowired
    AssetReceiveRecordDAO assetReceiveRecordDAO;
    @Autowired
    UserDAO userDao;
    @Autowired
    ShareService shareService;
    @Autowired
    LabCenterDAO labCenterDAO;
    @Autowired
    AssetStorageDAO assetStorageDAO;
    @Autowired
    AssetRelatedImageDAO assetRelatedImageDAO;
    @Autowired
    AssetClassificationDAO assetClassificationDAO;
    @Autowired
    AssetCabinetAccessRecordDAO assetCabinetAccessRecordDAO;
    @Autowired
    private PConfig pConfig;
    @Autowired
    private AuditService auditService;
    @Autowired
    private AssetCabinetDAO assetCabinetDAO;
    @Autowired
    private OperationItemDAO operationItemDAO;
    @Autowired
    private AssetCabinetWarehouseDAO assetCabinetWarehouseDAO;
    @Autowired
    private AssetCabinetWarehouseRecordDAO assetCabinetWarehouseRecordDAO;
    @Autowired
    private AssetCabinetWarehouseAccessDAO assetCabinetWarehouseAccessDAO;
    @Autowired
    private MessageDAO messageDAO;

    /**
     * 物资分类列表

     * * @return MaterialKindDTO
     * @author 吴奇臻 2019-3-25
     */
    public List<MaterialKindDTO> findAllAssetClassificationList(){
        String sql="select id,cname from asset_classification";
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> objects=query.getResultList();
        List<MaterialKindDTO> materialKindDTOList=new ArrayList<>();
        for(Object[] o:objects){
            MaterialKindDTO materialKindDTO=new MaterialKindDTO();
            materialKindDTO.setId(o[0]!=null?Integer.parseInt(o[0].toString()):null);
            materialKindDTO.setCname(o[1]!=null?o[1].toString():null);
            materialKindDTOList.add(materialKindDTO);
        }
        return  materialKindDTOList;
    }

    /**
     * 物品柜列表

     * * @return MaterialKindDTO
     * @author 吴奇臻 2019-03-31
     */
    public List<AssetsCabinetDTO> findAllAssetCabinetList(String assetId){
        String sql="SELECT\n" +
                "\tac.id,\n" +
                "\tac.cabinet_name,\n" +
                "\tac.capacity,\n" +
                "  acr.stock_number\n" +
                "FROM\n" +
                "\tasset_cabinet ac\n" +
                "LEFT JOIN asset_cabinet_record acr ON ac.id = acr.cabinet_id\n" +
                "WHERE 1 = 1  ";
        if(assetId!=null&&!assetId.equals("")){
            sql+=" and acr.asset_id="+assetId;
        }
        sql+=" group by ac.id";
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> objects=query.getResultList();
        List<AssetsCabinetDTO> assetsCabinetDTOList=new ArrayList<>();
        for(Object[] o:objects){
            AssetsCabinetDTO assetsCabinetDTO=new AssetsCabinetDTO();
            assetsCabinetDTO.setId(o[0]!=null?Integer.parseInt(o[0].toString()):null);
            assetsCabinetDTO.setCabinetName(o[1]!=null?o[1].toString():null);
            assetsCabinetDTO.setCapacity(o[2]!=null?o[2].toString():null);
            assetsCabinetDTO.setQuantity(o[3]!=null?o[3].toString():null);
            assetsCabinetDTOList.add(assetsCabinetDTO);
        }
        return  assetsCabinetDTOList;
    }
    /**
     * 物品柜门列表
     * @author 吴奇臻 2019-06-05
     */
    public List<AssetsCabinetWareHouseDTO> findAllAssetCabinetWareHouseByCabinetId(Integer cabinetId){
        String sql="select id,CONCAT(warehouse_code,\"-\",warehouse_name) from asset_cabinet_warehouse where asset_cabinet_id="+cabinetId;
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> objects=query.getResultList();
        List<AssetsCabinetWareHouseDTO> assetsCabinetWareHouseDTOList=new ArrayList<>();
        for(Object[] o:objects){
            AssetsCabinetWareHouseDTO assetsCabinetWareHouseDTO=new AssetsCabinetWareHouseDTO();
            assetsCabinetWareHouseDTO.setId(o[0]!=null?Integer.parseInt(o[0].toString()):null);
            assetsCabinetWareHouseDTO.setWareHouseCode(o[1]!=null?o[1].toString():null);
            assetsCabinetWareHouseDTOList.add(assetsCabinetWareHouseDTO);
        }
        return  assetsCabinetWareHouseDTOList;
    }
    /**
     * 物资列表

     * * @return MaterialKindDTO
     * @author 吴奇臻 2019-03-27
     */
    public List<MaterialListDTO> findAllAssets(){
        List<MaterialListDTO> materialListDTOList = new ArrayList<>();
        String sql = "select a.id,CONCAT(a.ch_name,\"(\",a.specifications,\")\")from asset a LEFT JOIN asset_classification ac on a.category = ac.id where 1=1 order by a.ch_name ";
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> assetList=query.getResultList();
        for(Object[] o:assetList){
            MaterialListDTO materialListDTO=new MaterialListDTO();
            materialListDTO.setName(o[1]!=null?o[1].toString():null);
            materialListDTO.setId(o[0]!=null?o[0].toString():null);
            materialListDTOList.add(materialListDTO);
        }
        return materialListDTOList;
    }
    /**
     * 获取所有学院

     * * @return SchoolAcademy
     * @author 吴奇臻 2019-03-27
     */
    public List<SchoolAcademy> findAllSchoolAcademyList(){
        String sql="select academy_number,academy_name from school_academy";
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> objects=query.getResultList();
        List<SchoolAcademy> schoolAcademyList=new ArrayList<>();
        for(Object[] o:objects){
            SchoolAcademy schoolAcademy=new SchoolAcademy();
            schoolAcademy.setAcademyNumber(o[0]!=null?o[0].toString():null);
            schoolAcademy.setAcademyName(o[1]!=null?o[1].toString():null);
            schoolAcademyList.add(schoolAcademy);
        }
        return  schoolAcademyList;
    }
    /**
     * 获取所有物联服务器

     * * @return SchoolAcademy
     * @author 吴奇臻 2019-05-29
     */
    public List<CommonServer> findAllCommonServerList(){
        String sql="select id,server_name from common_server";
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> objects=query.getResultList();
        List<CommonServer> commonServerList=new ArrayList<>();
        for(Object[] o:objects){
            CommonServer commonServer=new CommonServer();
            commonServer.setId(o[0]!=null?Integer.parseInt(o[0].toString()):null);
            commonServer.setServerName(o[1]!=null?o[1].toString():null);
            commonServerList.add(commonServer);
        }
        return  commonServerList;
    }
    /**
     * 获取所有中心

     * * @return SchoolAcademy
     * @author 吴奇臻 2019-03-27
     */
    public List<LabCenter> findAllLabCenterList(){
        String sql="select id,center_name from lab_center";
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> objects=query.getResultList();
        List<LabCenter> labCenterList=new ArrayList<>();
        for(Object[] o:objects){
            LabCenter labCenter=new LabCenter();
            labCenter.setId(o[0]!=null?Integer.parseInt(o[0].toString()):null);
            labCenter.setCenterName(o[1]!=null?o[1].toString():null);
            labCenterList.add(labCenter);
        }
        return  labCenterList;
    }

    /**
     * 物品柜列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return 状态字符串
     * @author 吴奇臻 2019-5-15
     */
    public JSONObject findAllAssetCabinetList(Integer page, Integer limit){
        List<AssetsCabinetDTO> assetsCabinetDTOList=new ArrayList<>();
        String sql="select id,cabinet_code,cabinet_name from asset_cabinet";
        Query query=entityManager.createNativeQuery(sql);
        query.setMaxResults(limit);
        query.setFirstResult((page-1)*limit);
        List<Object[]> assetCabinetList=query.getResultList();
        for(Object[] o:assetCabinetList){
           AssetsCabinetDTO assetsCabinetDTO=new AssetsCabinetDTO();
           assetsCabinetDTO.setId(o[0]!=null?Integer.parseInt(o[0].toString()):null);
           assetsCabinetDTO.setCabinetCode(o[1]!=null?o[1].toString():null);
           assetsCabinetDTO.setCabinetName(o[2]!=null?o[2].toString():null);
           assetsCabinetDTOList.add(assetsCabinetDTO);
        }
        int totalRecords=entityManager.createNativeQuery(sql).getResultList().size();
        JSONObject jsonObject=this.getJSON(assetsCabinetDTOList,totalRecords);
        return jsonObject;
    }
    /**
     * 物资名录列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return 状态字符串
     * @author 吴奇臻 2019-3-6
     */
    @Override
    public JSONObject findAllAssetList(Integer page, Integer limit,String keywords,String kind) {
        List<MaterialListDTO> materialListDTOList = new ArrayList<>();
        String sql = "select ac.cname,a.id,a.ch_name,a.specifications,a.unit,a.price,a.factory,a.qRCode_url,a.cas,function from asset a LEFT JOIN asset_classification ac on a.category = ac.id where 1=1 ";
        if(keywords!=null&&!keywords.equals("")){
            sql+="and (a.ch_name like '%"+keywords+"%' or  a.cas like '%"+keywords+"%' or  ac.cname like '%"+keywords+"%') ";
        }
        if(kind!=null&&!kind.equals("")){
            sql+="and ac.id = '"+kind+"'";
        }
        Query query=entityManager.createNativeQuery(sql);
        query.setMaxResults(limit);
        query.setFirstResult((page-1)*limit);
        List<Object[]> assetList=query.getResultList();
        for(Object[] o:assetList){
            MaterialListDTO materialListDTO=new MaterialListDTO();
            materialListDTO.setName(o[2]!=null?o[2].toString():null);
            materialListDTO.setId(o[1]!=null?o[1].toString():null);
            materialListDTO.setKind(o[0]!=null?o[0].toString():null);
            materialListDTO.setType(o[3]!=null?o[3].toString():null);
            materialListDTO.setUnit(o[4]!=null?o[4].toString():null);
            materialListDTO.setPrice(o[5]!=null?o[5].toString():null);
            materialListDTO.setFactory(o[6]!=null?o[6].toString():null);
            materialListDTO.setPicture(o[7]!=null?o[7].toString():null);
            materialListDTO.setCas(o[8]!=null?o[8].toString():null);
            materialListDTO.setFunction(o[9]!=null?o[9].toString():null);
            materialListDTOList.add(materialListDTO);
        }
        int totalRecords=entityManager.createNativeQuery(sql).getResultList().size();
        JSONObject jsonObject=this.getJSON(materialListDTOList,totalRecords);
        return jsonObject;
    }

    /**
     * 物资申购列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return 状态字符串
     * @author 吴奇臻 2019-3-26
     */
    public JSONObject findAllAssetApplyList(Integer page, Integer limit,String status,String kind,HttpServletRequest request){
        List<AssetsApplyDTO> assetsApplyDTOList=new ArrayList<>();
        String sql="SELECT\n" +
                "\taa.id,\n" +
                "\taa.app_no,\n" +
                "\taa.app_date,\n" +
                "\tCONCAT(u.cname, \"(\", u.username, \")\") AS username,\n" +
                "\taa.price,\n" +
                "\tac.cname,\n" +
                "\taa.asset_statu,\n" +
                "\taa.cur_audit_level\n" +
                "FROM\n" +
                "\tasset_app aa\n" +
                "LEFT JOIN asset_classification ac ON aa.category_id = ac.id\n" +
                "LEFT JOIN `user` u ON aa.app_user = u.username where 1=1 ";
        if(status!=null&&!status.equals("")){
            sql+="and aa.asset_statu = "+status+"";
        }
        if(kind!=null&&!kind.equals("")){
            sql+=" and aa.category_id = "+kind+"";
        }
        //添加权限相关筛选条件
        String authorityName=request.getSession().getAttribute("selected_role").toString();//权限名
        User user=shareService.getUser();//获得用户
        String username=user.getUsername();//用户编号
        if(authorityName.equals("ROLE_TEACHER")){
            sql+=" and aa.app_user = '"+username+"'";//教师只看自己
        }
        if(authorityName.equals("ROLE_EXCENTERDIRECTOR")){//中心主任只看跟自己所负责中心的数据
            List<Integer> centerIds=this.getCenterIDListFormUsername(username);
            sql+=" and ( ";
            for(int i=0;i<centerIds.size()-1;i++){
                sql+=" aa.center_id ="+centerIds.get(i)+" or";
            }
            sql+=" aa.center_id ="+centerIds.get(centerIds.size()-1)+" )";
        }
        if(authorityName.equals("ROLE_EXCENTERDIRECTOR")||authorityName.equals("ROLE_OPEARTIONSECURITYMANAGEMENT")){
            sql+=" and aa.asset_statu >0 ";//审核权限只看提交后的数据
        }
        sql+=" order by aa.app_date desc ";
        Query query=entityManager.createNativeQuery(sql);
        query.setMaxResults(limit);
        query.setFirstResult((page-1)*limit);
        List<Object[]> assetapplyList=query.getResultList();
        for(Object[] o:assetapplyList){
            AssetsApplyDTO assetsApplyDTO=new AssetsApplyDTO();
            assetsApplyDTO.setId(o[0]!=null?o[0].toString():null);
            assetsApplyDTO.setBatchNumber(o[1]!=null?o[1].toString():null);
            assetsApplyDTO.setDate(o[2]!=null?o[2].toString():null);
            assetsApplyDTO.setApplicantUserName(o[3]!=null?o[3].toString():null);
            assetsApplyDTO.setPrice(o[4]!=null?Double.parseDouble(o[4].toString()):null);
            assetsApplyDTO.setGoodsCategory(o[5]!=null?o[5].toString():null);
            assetsApplyDTO.setStatus(o[6]!=null?o[6].toString():null);
            if(o[7]!=null){
                assetsApplyDTO.setCurAuditLevel(o[7].toString());
                if(authorityName.split("_")[1].equals(o[7].toString())){
                    assetsApplyDTO.setAuditFlag(1);
                }else{
                    assetsApplyDTO.setAuditFlag(0);
                }
            }
            String appUser="";
            if(o[3]!=null){
                appUser=o[3].toString();
            }
            if(appUser.contains(shareService.getUser().getUsername())){
                assetsApplyDTO.setAppFlag(1);
            }else{
                assetsApplyDTO.setAppFlag(0);
            }
            assetsApplyDTOList.add(assetsApplyDTO);
        }
        int totalRecords=entityManager.createNativeQuery(sql).getResultList().size();
        JSONObject jsonObject=this.getJSON(assetsApplyDTOList,totalRecords);
        return  jsonObject;
    }

    /**
     * 物资入库列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-1
     */
    public JSONObject findAllAssetInStorageList(Integer page, Integer limit,String status,HttpServletRequest request){
        List<AssetsInStorageDTO> assetsInStorageDTOList=new ArrayList<>();
        //查询语句
        String sql="SELECT\n" +
                "\tass.id,\n" +
                "\taca.cabinet_name,\n" +
                "\tCONCAT(u.cname,\"(\",u.username,\")\"),\n" +
                "\tass.date,\n" +
                "\tass.batch_number,\n" +
                "  ac.cname,\n" +
                "  ass.total_price,\n" +
                "  ass.status,\n" +
                "  ass.cur_audit_level\n" +
                "FROM\n" +
                "\tasset_storage ass\n" +
                "LEFT JOIN school_academy sa ON ass.academy_number = sa.academy_number\n" +
                "LEFT JOIN lab_center la on ass.center_id=la.id\n" +
                "LEFT JOIN `user` u on ass.username=u.username\n" +
                "LEFT JOIN asset_classification ac on ass.classification_id=ac.id\n" +
                "LEFT JOIN asset_cabinet aca on ass.cabinet_id=aca.id WHERE 1=1 ";
        if(status!=null&&!status.equals("")){
            sql+="and ass.status = "+status+"";
        }
        //添加权限相关筛选条件
        String authorityName=request.getSession().getAttribute("selected_role").toString();//权限名
        User user=shareService.getUser();//获得用户
        String username=user.getUsername();//用户编号
        if(authorityName.equals("ROLE_TEACHER")){
            sql+=" and ass.username = '"+username+"'";//教师只看自己
        }
        if(authorityName.equals("ROLE_EXCENTERDIRECTOR")){//中心主任只看跟自己所负责中心的数据
            List<Integer> centerIds=this.getCenterIDListFormUsername(username);
            sql+=" and ( ";
            for(int i=0;i<centerIds.size()-1;i++){
                sql+=" ass.center_id ="+centerIds.get(i)+" or";
            }
            sql+=" ass.center_id ="+centerIds.get(centerIds.size()-1)+" )";
        }
        if(authorityName.equals("ROLE_EXCENTERDIRECTOR")||authorityName.equals("ROLE_OPEARTIONSECURITYMANAGEMENT")){
            sql+=" and ass.status >0 ";//审核权限只看提交后的数据
        }
        sql+=" order by ass.date desc ";
        Query query=entityManager.createNativeQuery(sql);
        //分页记录
        query.setMaxResults(limit);
        query.setFirstResult((page-1)*limit);
        List<Object[]> assetsInStorageList=query.getResultList();
        for(Object[] o:assetsInStorageList){
            AssetsInStorageDTO assetsInStorageDTO=new AssetsInStorageDTO();
            assetsInStorageDTO.setId(o[0]!=null?Integer.parseInt(o[0].toString()):null);
            assetsInStorageDTO.setCabinet(o[1]!=null?o[1].toString():null);
            assetsInStorageDTO.setUsername(o[2]!=null?o[2].toString():null);
            assetsInStorageDTO.setDate(o[3]!=null?o[3].toString():null);
            assetsInStorageDTO.setBatchNumber(o[4]!=null?o[4].toString():null);
            assetsInStorageDTO.setGoodsCategory(o[5]!=null?o[5].toString():null);
            assetsInStorageDTO.setTotalPrice(o[6]!=null?o[6].toString():null);
            assetsInStorageDTO.setStatus(o[7]!=null?o[7].toString():null);
            if(o[8]!=null) {
                assetsInStorageDTO.setCurAuditLevel(o[8].toString());
                if(authorityName.split("_")[1].equals(o[8].toString())){
                    assetsInStorageDTO.setAuditFlag(1);
                }else{
                    assetsInStorageDTO.setAuditFlag(0);
                }
            }
            String appUser="";
            if(o[2]!=null){
                appUser=o[2].toString();
            }
            if(appUser.contains(shareService.getUser().getUsername())){
                assetsInStorageDTO.setAppFlag(1);
            }else{
                assetsInStorageDTO.setAppFlag(0);
            }
            assetsInStorageDTOList.add(assetsInStorageDTO);
        }
        int totalRecords=entityManager.createNativeQuery(sql).getResultList().size();
        JSONObject jsonObject=this.getJSON(assetsInStorageDTOList,totalRecords);
        return  jsonObject;
     }

    /**
     * 物资申领列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-1
     */
    public JSONObject findAllAssetReceiveList(Integer page, Integer limit,String status,HttpServletRequest request){
        List<AssetsReceiveDTO> assetsReceiveDTOList=new ArrayList<>();
        //查询语句
        String sql="SELECT\n" +
                "\tar.id,\n" +
                "\tar.receive_no,\n" +
                "\tar.receive_date,\n" +
                "\tCONCAT(u.cname, \"(\", u.username, \")\") AS username,\n" +
                "  ac.cname,\n" +
                "\tar. STATUS,\n" +
                "\tar.asset_usage,\n" +
                "\tac.is_need_return,\n" +
                "  ar.cur_audit_level,\n" +
                "  ar.start_data\n" +
                "FROM\n" +
                "\tasset_receive ar\n" +
                "LEFT JOIN `user` u ON u.username = ar.app_user\n" +
                "LEFT JOIN asset_classification ac on ar.category_id=ac.id WHERE 1=1 ";
        if(status!=null&&!status.equals("")){
            sql+=" and ar.status = "+status+"";
        }
        //添加权限相关筛选条件
        String authorityName=request.getSession().getAttribute("selected_role").toString();//权限名
        User user=shareService.getUser();//获得用户
        String username=user.getUsername();//用户编号
        if(authorityName.equals("ROLE_TEACHER")){
            sql+=" and ar.app_user = '"+username+"'";//教师只看自己
        }
        if(authorityName.equals("ROLE_EXCENTERDIRECTOR")){//中心主任只看跟自己所负责中心的数据
            List<Integer> centerIds=this.getCenterIDListFormUsername(username);
            sql+=" and ( ";
            for(int i=0;i<centerIds.size()-1;i++){
                sql+=" ar.center_id ="+centerIds.get(i)+" or";
            }
            sql+=" ar.center_id ="+centerIds.get(centerIds.size()-1)+" )";
        }
        if(authorityName.equals("ROLE_EXCENTERDIRECTOR")||authorityName.equals("ROLE_OPEARTIONSECURITYMANAGEMENT")){
            sql+=" and ar. STATUS >0 ";//审核权限只看提交后的数据
        }
        sql+=" order by ar.receive_date desc";
        Query query=entityManager.createNativeQuery(sql);
        //分页
        query.setMaxResults(limit);
        query.setFirstResult((page-1)*limit);
        List<Object[]> assetsReceiveList=query.getResultList();
        for(Object[] o:assetsReceiveList){
            AssetsReceiveDTO assetsReceiveDTO=new AssetsReceiveDTO();
            assetsReceiveDTO.setId(o[0]!=null?o[0].toString():null);
            assetsReceiveDTO.setBatchNumber(o[1]!=null?o[1].toString():null);
            assetsReceiveDTO.setApplicationTime(o[2]!=null?o[2].toString().substring(0,19):null);
            assetsReceiveDTO.setUsername(o[3]!=null?o[3].toString():null);
            assetsReceiveDTO.setGoodsCategory(o[4]!=null?o[4].toString():null);
            assetsReceiveDTO.setStatus(o[5]!=null?o[5].toString():null);
            assetsReceiveDTO.setPurpose(o[6]!=null?o[6].toString():null);
            assetsReceiveDTO.setIsNeedReturn(o[7]!=null?Integer.parseInt(o[7].toString()):null);
            if(o[8]!=null) {
                assetsReceiveDTO.setCurAuditLevel(o[8].toString());
                if(authorityName.split("_")[1].equals(o[8].toString())){
                    assetsReceiveDTO.setAuditFlag(1);
                }else{
                    assetsReceiveDTO.setAuditFlag(0);
                }
            }
            assetsReceiveDTO.setBeginTime(o[9]!=null?o[9].toString().substring(0,19):null);
            String appUser="";
            if(o[3]!=null){
                appUser=o[3].toString();
            }
            if(appUser.contains(shareService.getUser().getUsername())){
                assetsReceiveDTO.setAppFlag(1);
            }else{
                assetsReceiveDTO.setAppFlag(0);
            }
            String sql1="SELECT\n" +
                    "  acr.stock_number\n" +
                    "FROM\n" +
                    "\tasset_receive_record arr\n" +
                    "LEFT JOIN asset_receive ar on arr.receive_id = ar.id\n" +
                    "LEFT JOIN asset_cabinet_record acr on (arr.asset_id = acr.asset_id and arr.cabinet_id=acr.cabinet_id)\n" +
                    "WHERE 1=1 AND ar.id="+o[0].toString();
            Query query1=entityManager.createNativeQuery(sql1);
            try {
                List<Integer> amounts = query1.getResultList();
                if (amounts != null && amounts.size() > 0) {
                    for (int i = 0; i < amounts.size(); i++) {
                        if (amounts.get(i) < 0) {
                            assetsReceiveDTO.setRemarks("库存不足");
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            assetsReceiveDTOList.add(assetsReceiveDTO);
        }
        int totalRecords=entityManager.createNativeQuery(sql).getResultList().size();
        JSONObject jsonObject=this.getJSON(assetsReceiveDTOList,totalRecords);
        return   jsonObject;
    }
    /**
     * 物资申购条目列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return 状态字符串
     * @author 吴奇臻 2019-3-26
     */
    public JSONObject findAllAssetApplyItemList(Integer page, Integer limit,Integer id){
        List<MaterialListDTO> materialListDTOList = new ArrayList<>();
        //查询语句
        String sql = "SELECT\n" +
                "\tac.cabinet_name,\n" +
                "\ta.ch_name,\n" +
                "\ta.specifications,\n" +
                "\ta.unit,\n" +
                "\taar.app_quantity,\n" +
                "\taar.app_price,\n" +
                "\taar.app_supplier,\n" +
                "\taar.id,\n" +
                "\taar.total_price\n" +
                "FROM\n" +
                "\tasset_app_record aar\n" +
                "LEFT JOIN asset a ON aar.asset_id = a.id\n" +
                "LEFT JOIN asset_cabinet ac on aar.cabinet_id=ac.id\n" +
                "where 1=1 and aar.app_id= "+id;
        int totalRecords=entityManager.createNativeQuery(sql).getResultList().size();
        Query query=entityManager.createNativeQuery(sql);
        //分页
        query.setMaxResults(limit);
        query.setFirstResult((page-1)*limit);
        List<Object[]> assetList=query.getResultList();
        for(Object[] o:assetList){
            MaterialListDTO materialListDTO=new MaterialListDTO();
            materialListDTO.setCabinet(o[0]!=null?o[0].toString():null);
            materialListDTO.setName(o[1]!=null?o[1].toString():null);
            materialListDTO.setType(o[2]!=null?o[2].toString():null);
            materialListDTO.setUnit(o[3]!=null?o[3].toString():null);
            materialListDTO.setAmount(o[4]!=null?Integer.parseInt(o[4].toString()):null);
            materialListDTO.setPrice(o[5]!=null?o[5].toString():null);
            materialListDTO.setFactory(o[6]!=null?o[6].toString():null);
            materialListDTO.setId(o[7]!=null?o[7].toString():null);
            materialListDTO.setTotalPrice(o[8]!=null?Double.parseDouble(o[8].toString()):null);
            materialListDTOList.add(materialListDTO);
        }
        JSONObject jsonObject=this.getJSON(materialListDTOList,totalRecords);
        return jsonObject;
    }
    /**
     * 物资申购条目列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return 状态字符串
     * @author 吴奇臻 2019-3-26
     */
    public JSONObject findAllAssetInStorageItemList(Integer page, Integer limit,Integer id){
        //初始化物资名录DTO
        List<MaterialListDTO> materialListDTOList=new ArrayList<>();
        String sql="SELECT\n" +
                "\tac.cabinet_name,\n" +
                "\ta.ch_name,\n" +
                "\ta.specifications,\n" +
                "\ta.unit,\n" +
                "\tasr.quantity,\n" +
                "\tasr.price,\n" +
                "\tasr.supplier,\n" +
                "\tasr.id,\n" +
                "\tasr.total_price,\n" +
                "  acl.cname,\n" +
                "  acw.warehouse_code\n" +
                "FROM\n" +
                "\tasset_storage_record asr\n" +
                "LEFT JOIN asset a ON asr.asset_id = a.id\n" +
                "LEFT JOIN asset_cabinet ac on asr.cabinet_id=ac.id\n" +
                "LEFT JOIN asset_cabinet_warehouse acw on asr.warehouse_id=acw.id\n" +
                "LEFT JOIN asset_classification acl on acl.id=a.category\n" +
                "where 1=1 and asr.store_id= "+id+" group by asr.id";
        int totalRecords=entityManager.createNativeQuery(sql).getResultList().size();
        Query query=entityManager.createNativeQuery(sql);
        //分页
        query.setMaxResults(limit);
        query.setFirstResult((page-1)*limit);
        List<Object[]> assetList=query.getResultList();
        for(Object[] o:assetList){
            MaterialListDTO materialListDTO=new MaterialListDTO();
            if(o[10]!=null) {
                materialListDTO.setCabinet(o[0] != null ? o[0].toString()+"-"+o[10].toString() : null);
            }else{
                materialListDTO.setCabinet(o[0] != null ? o[0].toString() : null);
            }
            materialListDTO.setName(o[1]!=null?o[1].toString():null);
            materialListDTO.setType(o[2]!=null?o[2].toString():null);
            materialListDTO.setUnit(o[3]!=null?o[3].toString():null);
            materialListDTO.setAmount(o[4]!=null?Integer.parseInt(o[4].toString()):null);
            materialListDTO.setPrice(o[5]!=null?o[5].toString():null);
            materialListDTO.setFactory(o[6]!=null?o[6].toString():null);
            materialListDTO.setId(o[7]!=null?o[7].toString():null);
            materialListDTO.setTotalPrice(o[8]!=null?Double.parseDouble(o[8].toString()):null);
            materialListDTO.setKind(o[9]!=null?o[9].toString():null);
            materialListDTOList.add(materialListDTO);
        }
        JSONObject jsonObject=this.getJSON(materialListDTOList,totalRecords);
        return jsonObject;
    }

    /**
     * 物资入库单信息
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-30
     */
    public JSONObject findAllAssetInStorageItem(Integer appId){
        //初始化物资名录DTO
        List<MaterialListDTO> materialListDTOList=new ArrayList<>();
        String sql="SELECT\n" +
                "\ta.ch_name,\n" +
                "\ta.specifications,\n" +
                "\ta.unit,\n" +
                "\tasr.quantity,\n" +
                "\tasr.price,\n" +
                "\tasr.total_price,\n" +
                "\tasr.invoice_number,\n" +
                "\tasr.info,\n" +
                "\tasr.supplier,\n" +
                "\tass.app_id,\n" +
                "\tass.audit_date,\n" +
                "\tCONCAT(audit.cname,\"(\",audit.username,\")\"),\n" +
                "\tCONCAT(app.cname,\"(\",app.username,\")\"),\n" +
                "\tass.apply_date\n" +
                "FROM\n" +
                "\tasset_storage_record asr\n" +
                "LEFT JOIN asset a ON asr.asset_id = a.id\n" +
                "LEFT JOIN asset_storage ass on asr.store_id=ass.id\n" +
                "LEFT JOIN user audit on audit.username=ass.audit_user\n" +
                "LEFT JOIN user app on app.username=ass.username\n" +
                "WHERE 1 = 1 AND asr.store_id="+appId;
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> assetItemList=query.getResultList();
        Double totalPrice=0.00;
        for(Object[] o:assetItemList){
            MaterialListDTO materialListDTO=new MaterialListDTO();
            materialListDTO.setName(o[0]!=null?o[0].toString():null);//入库物品名称
            materialListDTO.setType(o[1]!=null?o[1].toString():null);//物品规格
            materialListDTO.setUnit(o[2]!=null?o[2].toString():null);//物品单位
            materialListDTO.setAmount(o[3]!=null?Integer.parseInt(o[3].toString()):null);//物品数量
            materialListDTO.setPrice(o[4]!=null?o[4].toString():null);//物品单价
            materialListDTO.setTotalPrice(o[5]!=null?Double.parseDouble(o[5].toString()):null);//物品总价
            materialListDTO.setInvoiceNumber(o[6]!=null?o[6].toString():null);//发票号
            materialListDTO.setInfo(o[7]!=null?o[7].toString():null);//备注
            materialListDTOList.add(materialListDTO);
            totalPrice +=Double.parseDouble(o[5].toString());
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("storageItemList",materialListDTOList);//保存入库单条目
        jsonObject.put("supplier",assetItemList.get(0)[8]);//供应商
        jsonObject.put("auditDate",assetItemList.get(0)[10].toString().substring(0,19));//入库日期
        jsonObject.put("auditUser",assetItemList.get(0)[11]);
        jsonObject.put("appUser",assetItemList.get(0)[12]);
        jsonObject.put("applyDate",assetItemList.get(0)[13]);
        jsonObject.put("totalPrice",totalPrice);
        return jsonObject;
    }
    /**
     * 物资申购条目列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * * @return 状态字符串
     * @author 吴奇臻 2019-3-26
     */
    public JSONObject findAllAssetReceiveItemList(Integer page, Integer limit,Integer id){
        //初始化物资名录DTO
        List<MaterialListDTO> materialListDTOList=new ArrayList<>();
        String sql="SELECT\n" +
                "\ta.ch_name,\n" +
                "\ta.specifications,\n" +
                "\ta.unit,\n" +
                "\tarr.quantity,\n" +
                "\tac.cabinet_name,\n" +
                "\tarr.id,\n" +
                "  acr.stock_number,\n" +
                "  arr.return_quantity,\n" +
                "  acw.warehouse_code\n" +
                "FROM\n" +
                "\tasset_receive_record arr\n" +
                "LEFT JOIN asset a ON arr.asset_id = a.id\n" +
                "LEFT JOIN asset_cabinet ac on arr.cabinet_id=ac.id\n" +
                "LEFT JOIN asset_cabinet_record acr on (acr.asset_id=arr.asset_id and arr.cabinet_id=acr.cabinet_id)\n" +
                "LEFT JOIN asset_cabinet_warehouse acw on arr.warehouse_id=acw.id\n" +
                "WHERE 1 = 1 AND arr.receive_id = "+id;
        int totalRecords=entityManager.createNativeQuery(sql).getResultList().size();
        Query query=entityManager.createNativeQuery(sql);
        //分页
        query.setMaxResults(limit);
        query.setFirstResult((page-1)*limit);
        List<Object[]> assetList=query.getResultList();
        for(Object[] o:assetList){
            MaterialListDTO materialListDTO=new MaterialListDTO();
            materialListDTO.setName(o[0]!=null?o[0].toString():null);
            materialListDTO.setType(o[1]!=null?o[1].toString():null);
            materialListDTO.setUnit(o[2]!=null?o[2].toString():null);
            //原数据库表定义类型为decimal,先做截取处理
            String amount=o[3].toString();
            String amount1=amount.substring(0,amount.length()-3);
            materialListDTO.setAmount(Integer.parseInt(amount1));
            if(o[8]!=null) {
                materialListDTO.setCabinet(o[4] != null ? o[4].toString()+"-"+o[8].toString() : null);
            }else{
                materialListDTO.setCabinet(o[4] != null ? o[4].toString() : null);
            }
            materialListDTO.setId(o[5]!=null?o[5].toString():null);
            materialListDTO.setStockNumber(o[6]!=null?o[6].toString():null);
            if(o[7]==null) {
                materialListDTO.setReturnAmount(0);
            }else{
                materialListDTO.setReturnAmount(Integer.parseInt(o[7].toString()));
            }
            materialListDTOList.add(materialListDTO);
        }
        JSONObject jsonObject=this.getJSON(materialListDTOList,totalRecords);
        return jsonObject;
    }

    /**
     * 智能物品柜具体信息列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * @author 吴奇臻 2019-5-29
     */
    public JSONObject findAllCabinetWareHoustList(Integer page, Integer limit,Integer id){
        List<AssetsCabinetWareHouseDTO> assetsCabinetWareHouseDTOList=new ArrayList<>();
        String sql="SELECT\n" +
                "\tacw.id,\n" +
                "\tacw.warehouse_code,\n" +
                "\tacw.warehouse_name\n" +
                "FROM\n" +
                "\tasset_cabinet_warehouse acw\n" +
                "where acw.asset_cabinet_id= "+id;
        int totalRecords=entityManager.createNativeQuery(sql).getResultList().size();
        Query query=entityManager.createNativeQuery(sql);
        //分页
        query.setMaxResults(limit);
        query.setFirstResult((page-1)*limit);
        List<Object[]> objects=query.getResultList();
        for(Object[] o:objects){
            AssetsCabinetWareHouseDTO assetsCabinetWareHouseDTO=new AssetsCabinetWareHouseDTO();
            assetsCabinetWareHouseDTO.setId(o[0]!=null?Integer.parseInt(o[0].toString()):null);
            assetsCabinetWareHouseDTO.setWareHouseCode(o[1]!=null?o[1].toString():null);
            assetsCabinetWareHouseDTO.setWareHouseName(o[2]!=null?o[2].toString():null);
            assetsCabinetWareHouseDTOList.add(assetsCabinetWareHouseDTO);
        }
        JSONObject jsonObject=this.getJSON(assetsCabinetWareHouseDTOList,totalRecords);
        return jsonObject;
    }
    /**
     * 物资出入库记录列表
     * @param page 页当前数
     * @param limit 当前页限制大小
     * @author 吴奇臻 2019-5-15
     */
    public JSONObject findAllAssetCabinetAccessRecordList(Integer page, Integer limit,Integer id){
        List<AssetsCabinetAccessRecordDTO> assetsCabinetAccessRecordDTOList=new ArrayList<>();
        String sql="SELECT\n" +
                "\tacar.app_id,\n" +
                "\tacar.type,\n" +
                "\tacar.create_date,\n" +
                "\tCONCAT(u.cname,\"(\",u.username,\")\") as username,\n" +
                "  ac.cabinet_name,\n" +
                "\tacar.quantity,\n" +
                "  acar.remain_quantity\n" +
                "FROM\n" +
                "\tasset_cabinet_access_record acar\n" +
                "INNER JOIN asset_cabinet_record acr ON (acar.cabinet_id = acr.cabinet_id and acar.asset_id = acr.asset_id)\n" +
                "LEFT JOIN asset_cabinet ac on acar.cabinet_id=ac.id\n" +
                "LEFT JOIN user u on acar.username=u.username\n" +
                "where acar.asset_id="+id;
        sql +=" order by acar.create_date desc";
        int totalRecords=entityManager.createNativeQuery(sql).getResultList().size();
        Query query=entityManager.createNativeQuery(sql);
        //分页
        query.setMaxResults(limit);
        query.setFirstResult((page-1)*limit);
        List<Object[]> assetAccessRecordList=query.getResultList();
        for(Object[] o:assetAccessRecordList){
            AssetsCabinetAccessRecordDTO assetsCabinetAccessRecordDTO=new AssetsCabinetAccessRecordDTO();
            assetsCabinetAccessRecordDTO.setId(o[0]!=null?Integer.parseInt(o[0].toString()):null);
            assetsCabinetAccessRecordDTO.setRecordType(o[1]!=null?(o[1].toString()):null);
            assetsCabinetAccessRecordDTO.setDate(o[2]!=null?(o[2].toString()):null);
            assetsCabinetAccessRecordDTO.setUsername(o[3]!=null?(o[3].toString()):null);
            assetsCabinetAccessRecordDTO.setCabinetName(o[4]!=null?(o[4].toString()):null);
            assetsCabinetAccessRecordDTO.setQuantity(o[5]!=null?(o[5].toString()):null);
            assetsCabinetAccessRecordDTO.setAmount(o[6]!=null?(o[6].toString()):null);
            assetsCabinetAccessRecordDTOList.add(assetsCabinetAccessRecordDTO);
        }
        JSONObject jsonObject=this.getJSON(assetsCabinetAccessRecordDTOList,totalRecords);
        return jsonObject;
    }
    /**
     * 物资申领条目列表
     * * @return 状态字符串
     * @author 吴奇臻 2019-3-26
     */
    public JSONObject findAllAssetReceiveItem(Integer appId){
        //初始化物资名录DTO
        List<MaterialListDTO> materialListDTOList=new ArrayList<>();
        String sql="SELECT\n" +
                "\ta.ch_name,\n" +
                "a.specifications,\n" +
                "a.factory,\n" +
                "a.unit,\n" +
                "arr.quantity,\n" +
                "a.price,\n" +
                "arr.quantity*a.price as total_price,\n" +
                "arr.info,\n" +
                "ar.audit_date,\n" +
                "CONCAT(u.cname,\"(\",u.username,\")\"),\n" +
                "ar.app_user\n" +
                "FROM\n" +
                "\tasset_receive_record arr\n" +
                "LEFT JOIN asset_receive ar ON arr.receive_id = ar.id\n" +
                "LEFT JOIN asset a on arr.asset_id = a.id\n" +
                "LEFT JOIN user u on ar.audit_user=u.username\n" +
                "where 1=1 AND ar.id="+appId;
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> assetList=query.getResultList();
        Double totalPrice=0.00;
        for(Object[] o:assetList){
            MaterialListDTO materialListDTO=new MaterialListDTO();
            materialListDTO.setName(o[0]!=null?o[0].toString():null);
            materialListDTO.setFactory(o[1]!=null?o[1].toString():null);
            materialListDTO.setType(o[2]!=null?o[2].toString():null);
            materialListDTO.setUnit(o[3]!=null?o[3].toString():null);
            //原数据库表定义类型为decimal,先做截取处理
            String amount=o[4].toString();
            String amount1=amount.substring(0,amount.length()-3);
            materialListDTO.setAmount(Integer.parseInt(amount1));
            materialListDTO.setPrice(o[5]!=null?o[5].toString():null);
            materialListDTO.setTotalPrice(o[6]!=null?Double.parseDouble(o[6].toString()):null);
            if(o[7]!=null){
                materialListDTO.setInfo(o[7].toString());
            }else{
                materialListDTO.setInfo("");
            }
            totalPrice +=Double.parseDouble(o[6].toString());
            materialListDTOList.add(materialListDTO);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("receiveItemList",materialListDTOList);//保存入库单条目
        //获取领料人用户
        String username=assetList.get(0)[10].toString();
        User user=userDao.findUserByUsername(username);
        String applicant=user.getCname()+"("+user.getUsername()+")";
        String department=user.getSchoolAcademy().getAcademyName();
        jsonObject.put("department",department);//学院
        jsonObject.put("applicant",applicant);//申请人
        jsonObject.put("auditDate",assetList.get(0)[8].toString().substring(0,19));//入库日期
        jsonObject.put("totalPrice",totalPrice);//总价
        jsonObject.put("auditUser",assetList.get(0)[9].toString());//审核人
        return jsonObject;
    }
    /**
     * 将项目数据封装为前台可接收的json格式数据
     * @param projects 项目列表
     * @param totalRecords 项目总数
     * @return json格式数据
     * @author 黄保钱 2019-2-26
     */
    private JSONObject getJSON(List<?> projects, int totalRecords){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", projects);
        jsonObject.put("count", totalRecords);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "success");
        return jsonObject;
    }

    /**
     * Description 保存物资名录
     * @param materialListDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-03-25
     */
    @Override
    public Asset saveAssetsDetail(MaterialListDTO materialListDTO) {
        Asset asset=new Asset();
        try{
            if(!materialListDTO.getId().equals("")) {
                asset = assetDAO.findAssetByPrimaryKey(Integer.parseInt(materialListDTO.getId()));
            }
            asset.setChName(materialListDTO.getName());
            asset.setSpecifications(materialListDTO.getType());
            asset.setUnit(materialListDTO.getUnit());
            if(materialListDTO.getCas()!=null&&!materialListDTO.getCas().equals("")) {
                asset.setCas(materialListDTO.getCas());
            }else{
                asset.setCas("无");
            }
            asset.setCategory(Integer.parseInt(materialListDTO.getKind()));
            asset.setPrice(materialListDTO.getPrice());
            asset.setFactory(materialListDTO.getFactory());
            asset.setFunction(materialListDTO.getFunction());
            asset=assetDAO.store(asset);
        }catch (Exception e){
            e.printStackTrace();
        }
       return asset;
    }

    /**
     * Description 通过申领或入库条目保存物资
     * @param assetsApplyItemDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-3-6
     */
    public String saveAssetsDetailFromItem(AssetsApplyItemDTO assetsApplyItemDTO){
        Asset asset=new Asset();
        asset.setChName(assetsApplyItemDTO.getName());
        asset.setSpecifications(assetsApplyItemDTO.getType());
        asset.setUnit(assetsApplyItemDTO.getUnit());
        asset.setCas(assetsApplyItemDTO.getCas());
        asset.setCategory(Integer.parseInt(assetsApplyItemDTO.getKind()));
        asset.setPrice(assetsApplyItemDTO.getPrice().toString());
        asset.setFactory(assetsApplyItemDTO.getFactory());
        asset.setFunction(assetsApplyItemDTO.getFunction());
        asset=assetDAO.store(asset);
        return asset.getId().toString();
    }
    /**
     * Description 保存物资申购条目
     * @param assetsApplyItemDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-3-6
     */
    public boolean saveAddAssetsDetail(AssetsApplyItemDTO assetsApplyItemDTO){
        boolean flag=true;
        AssetAppRecord assetAppRecord=new AssetAppRecord();
        AssetApp assetApp=assetAppDAO.findAssetAppById(Integer.parseInt(assetsApplyItemDTO.getAppId()));
        assetsApplyItemDTO.setKind(assetApp.getCategoryId().toString());
        //价格计算
        BigDecimal bd=new BigDecimal(assetsApplyItemDTO.getPrice().toString());
        Double price=bd.doubleValue()*assetsApplyItemDTO.getQuantity();
        Double old_price=0.00;
        Double new_price=0.00;
        if(assetApp.getPrice()!=null) {
            old_price = assetApp.getPrice();
        }
        //修改数量后进行价格计算
        if(assetsApplyItemDTO.getId()!=null&&!assetsApplyItemDTO.getId().equals("")){
            assetAppRecord=assetAppRecordDAO.findAssetAppRecordById(assetsApplyItemDTO.getId());
            new_price=old_price-assetAppRecord.getTotalPrice()+price;
        }else {
            new_price = price + old_price;
        }
        if(assetsApplyItemDTO.getAssetsId()!=null&&!assetsApplyItemDTO.getAssetsId().equals("")){
           this.saveAssetsAppRecordDetail(assetsApplyItemDTO);
           assetApp.setPrice(new_price);
        }else{//没有物资名录，先保存物资名录
            assetsApplyItemDTO.setAssetsId(this.saveAssetsDetailFromItem(assetsApplyItemDTO));
            this.saveAssetsAppRecordDetail(assetsApplyItemDTO);
            assetApp.setPrice(new_price);
        }
        assetAppDAO.store(assetApp);
        return flag;
    }
    /**
     * Description 保存物资入库条目
     * @param assetsApplyItemDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-3-6
     */
    public boolean saveAddAssetsInStorageDetail(AssetsApplyItemDTO assetsApplyItemDTO){
        boolean flag=true;
        if(assetsApplyItemDTO.getAssetsId()!=null&&!assetsApplyItemDTO.getAssetsId().equals("")){
            this.saveAssetsInStorageRecordDetail(assetsApplyItemDTO);
        }else{//没有物资名录，先保存物资名录
            AssetStorage assetStorage=this.findAssetStorageById(Integer.parseInt(assetsApplyItemDTO.getAppId()));
            assetsApplyItemDTO.setKind(assetStorage.getClassficationId().toString());//获取物资分类
            assetsApplyItemDTO.setAssetsId(this.saveAssetsDetailFromItem(assetsApplyItemDTO));
            this.saveAssetsInStorageRecordDetail(assetsApplyItemDTO);
        }
        return flag;
    }

    /**
     * Description 保存智能柜具体信息
     *
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-5-29
     */
    public boolean saveCabinetWareHouseDetail(AssetsCabinetWareHouseDTO assetsCabinetWareHouseDTO){
        boolean flag=true;
        AssetCabinetWarehouse assetCabinetWarehouse=new AssetCabinetWarehouse();
        try{
            if(assetsCabinetWareHouseDTO.getId()!=null&&!assetsCabinetWareHouseDTO.getId().equals("")) {
                assetCabinetWarehouse.setId(assetCabinetWarehouse.getId());
            }
            assetCabinetWarehouse.setAssetCabinetId(assetsCabinetWareHouseDTO.getCabinetId());
            assetCabinetWarehouse.setWarehouseCode(assetsCabinetWareHouseDTO.getWareHouseCode());
            assetCabinetWarehouse.setWarehouseName(assetsCabinetWareHouseDTO.getWareHouseName());
            assetCabinetWarehouseDAO.store(assetCabinetWarehouse);
        }catch (Exception e){
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }
    /**
     * Description 保存物资申领条目
     * @param assetsApplyItemDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-3-6
     */
    public boolean saveAddAssetsReceiveDetail(AssetsApplyItemDTO assetsApplyItemDTO){
        boolean flag=true;
        AssetReceiveRecord assetReceiveRecord=new AssetReceiveRecord();
        try{
            if(assetsApplyItemDTO.getId()!=null&&!assetsApplyItemDTO.getId().equals("")) {
                assetReceiveRecord.setId(assetsApplyItemDTO.getId());
            }
            assetReceiveRecord.setAssetReceive(assetReceiveDAO.findAssetReceiveById(Integer.parseInt(assetsApplyItemDTO.getAppId())));
            assetReceiveRecord.setAsset(assetDAO.findAssetByPrimaryKey(Integer.parseInt(assetsApplyItemDTO.getAssetsId())));
            BigDecimal bd=new BigDecimal(assetsApplyItemDTO.getQuantity().toString());
            assetReceiveRecord.setQuantity(bd);
            if(assetsApplyItemDTO.getCabinet() != null) {
                assetReceiveRecord.setCabinetId(Integer.parseInt(assetsApplyItemDTO.getCabinet()));
            }
            if(assetsApplyItemDTO.getWareHouse()!= null) {
                assetReceiveRecord.setWarehouseId(Integer.parseInt(assetsApplyItemDTO.getWareHouse()));
            }
            assetReceiveRecordDAO.store(assetReceiveRecord);
            //更新asset_cabinet_record表
//            if(assetsApplyItemDTO.getCabinet() != null && assetsApplyItemDTO.getAssetsId() != null) {
//                AssetCabinetRecord assetCabinetRecord = this.findAssetsCabinetRecordByCabinetAndAssets(Integer.parseInt(assetsApplyItemDTO.getCabinet()), Integer.parseInt(assetsApplyItemDTO.getAssetsId()));
//                assetCabinetRecord.setStockNumber(Integer.parseInt(assetsApplyItemDTO.getAmount()));
//                assetCabinetRecordDAO.store(assetCabinetRecord);
//            }
        }catch (Exception e){
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }
    /**
     * Description 保存物资申购条目
     * @param assetsApplyItemDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-3-6
     */
    public boolean saveAssetsAppRecordDetail(AssetsApplyItemDTO assetsApplyItemDTO){
        boolean flag=true;
        AssetAppRecord assetAppRecord=new AssetAppRecord();
        try{
            if(assetsApplyItemDTO.getId()!=null&&!assetsApplyItemDTO.getId().equals("")) {
                assetAppRecord.setId(assetsApplyItemDTO.getId());
            }
            assetAppRecord.setAssetApp(assetAppDAO.findAssetAppById(Integer.parseInt(assetsApplyItemDTO.getAppId())));
            //对原有的asset信息进行维护
            Asset asset=assetDAO.findAssetByPrimaryKey(Integer.parseInt(assetsApplyItemDTO.getAssetsId()));
            if(asset.getPrice()==null){
                asset.setPrice(assetsApplyItemDTO.getPrice().toString());
            }
            if(asset.getFactory()==null){
                asset.setFactory(assetsApplyItemDTO.getFactory());
            }
            assetAppRecord.setAsset(asset);
            assetAppRecord.setAppQuantity(assetsApplyItemDTO.getQuantity());
            assetAppRecord.setAppPrice(assetsApplyItemDTO.getPrice());
            Double totalPrice = assetsApplyItemDTO.getPrice().doubleValue() * assetsApplyItemDTO.getQuantity();
            assetAppRecord.setTotalPrice(totalPrice);
            assetAppRecord.setAppSupplier(assetsApplyItemDTO.getFactory());
            assetAppRecordDAO.store(assetAppRecord);

        }catch (Exception e){
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }
    /**
     * Description 保存物资入库条目
     * @param assetsApplyItemDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-3-29
     */
    public boolean saveAssetsInStorageRecordDetail(AssetsApplyItemDTO assetsApplyItemDTO){
        AssetStorageRecord assetStorageRecord=new AssetStorageRecord();
        if(assetsApplyItemDTO.getId()!=null&&!assetsApplyItemDTO.getId().equals("")){
            assetStorageRecord=this.findAssetStorageRecordById(assetsApplyItemDTO.getId());//获取原assetStorageRecord
            AssetStorage assetStorage=this.findAssetStorageById(Integer.parseInt(assetsApplyItemDTO.getAppId()));
            //价格计算
            BigDecimal bd=new BigDecimal(assetsApplyItemDTO.getPrice().toString());
            Double price=bd.doubleValue()*assetsApplyItemDTO.getQuantity();
            Double old_price=assetStorage.getTotalPrice();
            Double new_price=price-assetStorageRecord.getTotalPrice()+old_price;
            assetStorage.setTotalPrice(new_price);
            assetStorageDAO.store(assetStorage);
            assetStorageRecord.setStoreId(Integer.parseInt(assetsApplyItemDTO.getAppId()));
            assetStorageRecord.setAssetId(Integer.parseInt(assetsApplyItemDTO.getAssetsId()));
            assetStorageRecord.setQuantity(assetsApplyItemDTO.getQuantity().toString());
            assetStorageRecord.setPrice(Double.parseDouble(assetsApplyItemDTO.getPrice().toString()));
            assetStorageRecord.setSupplier(assetsApplyItemDTO.getFactory());
            if(assetsApplyItemDTO.getCabinet()!=null) {
                assetStorageRecord.setCabinetId(Integer.parseInt(assetsApplyItemDTO.getCabinet()));
            }
            if(assetsApplyItemDTO.getWareHouse()!=null&&!assetsApplyItemDTO.getWareHouse().equals("")) {
                assetStorageRecord.setWarehouseId(Integer.parseInt(assetsApplyItemDTO.getWareHouse()));
            }
            Double totalPrice=assetsApplyItemDTO.getPrice().doubleValue()*assetsApplyItemDTO.getQuantity();
            assetStorageRecord.setTotalPrice(totalPrice);
            assetStorageRecord.setInvoiceNumber(assetsApplyItemDTO.getInvoiceNumber());
            assetStorageRecord.setInfo(assetsApplyItemDTO.getItemRemarks());
            assetStorageRecordDAO.store(assetStorageRecord);

        }else{
            AssetStorage assetStorage=this.findAssetStorageById(Integer.parseInt(assetsApplyItemDTO.getAppId()));
            //价格计算
            BigDecimal bd=new BigDecimal(assetsApplyItemDTO.getPrice().toString());
            Double price=bd.doubleValue()*assetsApplyItemDTO.getQuantity();
            Double old_price=assetStorage.getTotalPrice();
            Double new_price=price+old_price;
            assetStorage.setTotalPrice(new_price);
            assetStorageDAO.store(assetStorage);
            assetStorageRecord.setStoreId(Integer.parseInt(assetsApplyItemDTO.getAppId()));
            assetStorageRecord.setAssetId(Integer.parseInt(assetsApplyItemDTO.getAssetsId()));
            assetStorageRecord.setQuantity(assetsApplyItemDTO.getQuantity().toString());
            assetStorageRecord.setPrice(Double.parseDouble(assetsApplyItemDTO.getPrice().toString()));
            assetStorageRecord.setSupplier(assetsApplyItemDTO.getFactory());
            if(assetsApplyItemDTO.getCabinet()!=null) {
                assetStorageRecord.setCabinetId(Integer.parseInt(assetsApplyItemDTO.getCabinet()));
            }
            if(assetsApplyItemDTO.getWareHouse()!=null&&!assetsApplyItemDTO.getWareHouse().equals("")) {
                assetStorageRecord.setWarehouseId(Integer.parseInt(assetsApplyItemDTO.getWareHouse()));
            }
            Double totalPrice=assetsApplyItemDTO.getPrice().doubleValue()*assetsApplyItemDTO.getQuantity();
            assetStorageRecord.setTotalPrice(totalPrice);
            assetStorageRecord.setInvoiceNumber(assetsApplyItemDTO.getInvoiceNumber());
            assetStorageRecord.setInfo(assetsApplyItemDTO.getItemRemarks());
            assetStorageRecordDAO.store(assetStorageRecord);
        }
        return true;
    }
    /**
     * Description 保存物资入库记录
     * @param assetsInStorageDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-3-31
     */
    public Integer saveAssetsInStorageDetail(AssetsInStorageDTO assetsInStorageDTO){
        AssetStorage assetStorage=new AssetStorage();
        if(assetsInStorageDTO.getApplyId()!=null) {
            String sql2 = "SELECT\n" +
                    "\tasset_id,\n" +
                    "\tcabinet_id\n" +
                    "FROM\n" +
                    "\tasset_app_record aar\n" +
                    "where 1=1 and aar.app_id= "+assetsInStorageDTO.getApplyId()+"";
            Query query=entityManager.createNativeQuery(sql2);
            List<Object[]> objects=query.getResultList();
            for(Object[] o:objects){
                if(o[1]==null){
                    return 0;
                }            }
            assetStorage.setAppId(assetsInStorageDTO.getApplyId());//保存对应申购记录编号
            AssetApp assetApp=assetAppDAO.findAssetAppById(assetsInStorageDTO.getApplyId());
            assetApp.setAssetStatu(4);//已发起入库
            assetAppDAO.store(assetApp);
        }
        if(assetsInStorageDTO.getId()!=null&&!assetsInStorageDTO.getId().equals("")) {
            assetStorage.setId(assetsInStorageDTO.getId());
        }
        assetStorage.setAcademyNumber(assetsInStorageDTO.getAcademyNumber());//保存学院编号
        if(assetsInStorageDTO.getDepartment()!=null&&!assetsInStorageDTO.getDepartment().equals("")) {
            assetStorage.setCenterId(Integer.parseInt(assetsInStorageDTO.getDepartment()));//保存部门编号(实验中心)
        }
        assetStorage.setUsername(shareService.getUser().getUsername());//保存入库人
        assetStorage.setClassficationId(Integer.parseInt(assetsInStorageDTO.getGoodsCategory()));//保存物资类别
        if(assetsInStorageDTO.getTotalPrice()!=null&&!assetsInStorageDTO.getTotalPrice().equals("")) {
            assetStorage.setTotalPrice(Double.parseDouble(assetsInStorageDTO.getTotalPrice()));//保存总金额
        }else{
            assetStorage.setTotalPrice(0.00);
        }
        assetStorage.setInvoiceNumber(assetsInStorageDTO.getInvoiceNumber());//保存发票编号
        assetStorage.setStatus(0);//保存状态
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        assetStorage.setDate(calendar.getTime());//保存日期
        try {
            Date applyDate = sdf.parse(assetsInStorageDTO.getApplyDate());
            assetStorage.setApplyDate(applyDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        //生成入库编号
        String dateStr = sdf.format(calendar.getTime()).replace("-","");//按日期生成编号
        String appNo=this.getAssetsRelatedAppNo("InStorage");//获取数量编号
        assetStorage.setBatchNumber("RK"+dateStr+appNo);
        assetStorage=assetStorageDAO.store(assetStorage);
        //对于申领入库，同步物资到入库记录
        if(assetsInStorageDTO.getApplyId()!=null) {
            String sql = "SELECT\n" +
                    "\tasset_id,\n" +
                    "\tapp_quantity,\n" +
                    "\tapp_price,\n" +
                    "\tapp_supplier,\n" +
                    "\ttotal_price,\n" +
                    "\tcabinet_id,\n" +
                    "\tinvoice_number,\n" +
                    "\tinfo\n" +
                    "FROM\n" +
                    "\tasset_app_record aar\n" +
                    "where 1=1 and aar.app_id= "+assetsInStorageDTO.getApplyId()+"";
            Query query=entityManager.createNativeQuery(sql);
            List<Object[]> objects=query.getResultList();
            for(Object[] o:objects){
                AssetStorageRecord assetStorageRecord=new AssetStorageRecord();
                assetStorageRecord.setStoreId(assetStorage.getId());
                assetStorageRecord.setAssetId(Integer.parseInt(o[0].toString()));
                assetStorageRecord.setQuantity(o[1].toString());
                assetStorageRecord.setPrice(Double.parseDouble(o[2].toString()));
                assetStorageRecord.setSupplier(o[3].toString());
                assetStorageRecord.setTotalPrice(o[4]!=null?Double.parseDouble(o[4].toString()):null);
                assetStorageRecord.setCabinetId(Integer.parseInt(o[5].toString()));
                assetStorageRecord.setInvoiceNumber(o[6]!=null?o[6].toString():null);
                assetStorageRecord.setInfo(o[7]!=null?o[7].toString():null);
                assetStorageRecordDAO.store(assetStorageRecord);
            }
        }
        return assetStorage.getId();
    }
    /**
     * Description 保存物资申购信息
     * @param assetsApplyDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-3-29
     */
    public Integer saveAssetsApplyDetail(AssetsApplyDTO assetsApplyDTO){
        AssetApp assetApp=new AssetApp();
        if(assetsApplyDTO.getId()!=null&&!assetsApplyDTO.getId().equals("")) {
            assetApp = assetAppDAO.findAssetAppByPrimaryKey(Integer.parseInt(assetsApplyDTO.getId()));
        }try{
            Calendar calendar = Calendar.getInstance();
            assetApp.setAppDate(calendar);//保存当前日期
            if(shareService.getUser() != null) {
                assetApp.setUser(shareService.getUser());//保存申请人
            }else{
                assetApp.setUser(shareService.findUserByUsername(assetsApplyDTO.getApplicantUserName()));
            }
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            assetApp.setBeginDate(sdf.parse(assetsApplyDTO.getStartDate()));//保存开始日期与结束日期
            assetApp.setEndDate(sdf.parse(assetsApplyDTO.getEndDate()));
            assetApp.setAcademyNumber(assetsApplyDTO.getAcademyNumber());//保存学院
            if(assetsApplyDTO.getDepartment()!=null&&!assetsApplyDTO.getDepartment().equals("")) {
                assetApp.setCenterId(Integer.parseInt(assetsApplyDTO.getDepartment()));//保存中心
            }
            assetApp.setCategoryId(Integer.parseInt(assetsApplyDTO.getGoodsCategory()));//保存物资类别
            String dateStr = sdf.format(calendar.getTime()).replace("-","");//获取日期编号
            String appNo=this.getAssetsRelatedAppNo("Apply");//获取数量编号
            assetApp.setAppNo("SG"+dateStr+appNo);//保存编号
            assetApp.setAssetStatu(0);//保存初始状态
            assetApp.setCourseNo(assetsApplyDTO.getCourseNo());
            assetApp=assetAppDAO.store(assetApp);
        }catch (Exception e){
            e.printStackTrace();
        }
        return assetApp.getId();
    }

    /**
     * Description 保存物资申领信息
     * @param assetsReceiveDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 吴奇臻 2019-3-6
     */
    public Integer saveAssetsReceiveDetail(AssetsReceiveDTO assetsReceiveDTO){
        AssetReceive assetReceive=new AssetReceive();
        if(assetsReceiveDTO.getId()!=null&&!assetsReceiveDTO.getId().equals("")) {
            assetReceive = assetReceiveDAO.findAssetReceiveById(Integer.parseInt(assetsReceiveDTO.getId()));
        }try{
            Calendar calendar = Calendar.getInstance();
            assetReceive.setReceiveDate(calendar);//保存当前日期
            assetReceive.setUser(shareService.getUser());//保存申请人
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            //保存开始日期与结束日期
            Calendar calendarBegin = Calendar.getInstance();
            calendarBegin.setTime(sdf.parse(assetsReceiveDTO.getBeginTime()));
            assetReceive.setStartData(calendarBegin);
            if(assetsReceiveDTO.getEndTime()!=null&&!assetsReceiveDTO.getEndTime().equals("")) {
                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.setTime(sdf.parse(assetsReceiveDTO.getEndTime()));
                assetReceive.setEndDate(calendarEnd);
            }
            assetReceive.setCategoryId(Integer.parseInt(assetsReceiveDTO.getGoodsCategory()));//保存物资类别
            if(assetsReceiveDTO.getDepartment()!=null&&!assetsReceiveDTO.getDepartment().equals("")) {
                assetReceive.setCenterId(Integer.parseInt(assetsReceiveDTO.getDepartment()));//保存中心
            }
            if(assetsReceiveDTO.getId()!=null&&!assetsReceiveDTO.getId().equals("")) {
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = sdf2.format(calendar.getTime()).replace("-", "");//获取日期编号
                String appNo = this.getAssetsRelatedAppNo("Receive");//获取数量编号
                assetReceive.setReceiveNo("SL" + dateStr + appNo);//保存编号
            }
            assetReceive.setAssetUsage(assetsReceiveDTO.getPurpose());//申领用途
            assetReceive.setStatus(0);//保存初始状态
            if (assetsReceiveDTO.getItemId()!=null) {
                OperationItem item = operationItemDAO.findOperationItemByPrimaryKey(assetsReceiveDTO.getItemId());
                assetReceive.setOperationItem(item);
            }
            assetReceive=assetReceiveDAO.store(assetReceive);
        }catch (Exception e){
            e.printStackTrace();
        }
        return assetReceive.getId();
    }

    /**
     * Description 删除物资名录
     * @param id 物资分类id
     * @return 成功-"success"，失败-"fail"
     * @author 吴奇臻 2019-3-27
     */
    @Override
    public boolean delAssets(Integer id) {
        boolean flag=true;
        try {
            Asset asset=assetDAO.findAssetByPrimaryKey(id);
            assetDAO.remove(asset);
        }catch (Exception e){
            flag=false;
        }
        return flag;
    }

    /**
     * 根据id获取入库记录
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-3-27
     */
    public AssetStorage findAssetStorageById(Integer id){
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select c from AssetStorage c where  c.id = " + id +"" );
        AssetStorage assetStorage = assetStorageDAO.executeQuery(sql.toString()).get(0);
        return assetStorage;
    }

    /**
     * 根据id获取入库记录
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-3-27
     */
    public AssetStorageRecord findAssetStorageRecordById(Integer id){
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select c from AssetStorageRecord c where  c.id = " + id +"" );
        AssetStorageRecord assetStorageRecord = assetStorageRecordDAO.executeQuery(sql.toString()).get(0);
        return assetStorageRecord;
    }
    /**
     * 根据id获取物资详细数据
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-3-24
     */
    public MaterialListDTO findAssetById(Integer id){
        MaterialListDTO materialListDTO=new MaterialListDTO();
        String sql="select category,id,ch_name,specifications,unit,price,factory,qRCode_url,cas,function from asset a where id='"+id+"'";
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> objects=query.getResultList();
        Object[] o=objects.get(0);
        materialListDTO.setName(o[2]!=null?o[2].toString():null);
        materialListDTO.setId(o[1]!=null?o[1].toString():null);
        materialListDTO.setKind(o[0]!=null?o[0].toString():null);
        materialListDTO.setType(o[3]!=null?o[3].toString():null);
        materialListDTO.setUnit(o[4]!=null?o[4].toString():null);
        materialListDTO.setPrice(o[5]!=null?o[5].toString():null);
        materialListDTO.setFactory(o[6]!=null?o[6].toString():null);
        materialListDTO.setPicture(o[7]!=null?o[7].toString():null);
        materialListDTO.setCas(o[8]!=null?o[8].toString():null);
        materialListDTO.setFunction(o[9]!=null?o[9].toString():null);
        return materialListDTO;
    }

    /**
     * 根据id获取物品柜数据
     *
     * @author 吴奇臻 2019-5-15
     */
    public AssetsCabinetDTO findAssetsCabinetById(Integer id){
        AssetsCabinetDTO assetsCabinetDTO=new AssetsCabinetDTO();
        String sql="select id,cabinet_code,cabinet_name,type,location,hardware_ip,hardware_type,server_id from asset_cabinet ac where ac.id="+id;
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> objects=query.getResultList();
        Object[] o=objects.get(0);
        assetsCabinetDTO.setId(o[0]!=null?Integer.parseInt(o[0].toString()):null);
        assetsCabinetDTO.setCabinetCode(o[1]!=null?o[1].toString():null);
        assetsCabinetDTO.setCabinetName(o[2]!=null?o[2].toString():null);
        assetsCabinetDTO.setType(o[3]!=null?o[3].toString():null);
        assetsCabinetDTO.setLocation(o[4]!=null?o[4].toString():null);
        assetsCabinetDTO.setHardwareIp(o[5]!=null?o[5].toString():null);
        assetsCabinetDTO.setHardwareType(o[6]!=null?o[6].toString():null);
        assetsCabinetDTO.setServerId(o[7]!=null?o[7].toString():null);
        return assetsCabinetDTO;
    }
    /**
     * 根据id获物资申购数据
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-3-27
     */
    public AssetsApplyDTO findAssetApplyDetailById(Integer id) throws Exception{
        AssetsApplyDTO assetsApplyDTO=new AssetsApplyDTO();
        String sql="SELECT\n" +
                "\taa.app_date,\n" +
                "\tCONCAT(u.cname, \"(\", u.username, \")\") AS username,\n" +
                "\taa.begin_date,\n" +
                "\taa.end_date,\n" +
                "\tsa.academy_number,\n" +
                "\tlc.id,\n" +
                "\taa.category_id,\n" +
                "\taa.price,\n" +
                "\taa.asset_statu,\n" +
                "\taa.reject_reason\n" +
                "FROM\n" +
                "\tasset_app aa\n" +
                "LEFT JOIN `user` u ON aa.app_user = u.username\n" +
                "LEFT JOIN school_academy sa ON aa.academy_number = sa.academy_number\n" +
                "LEFT JOIN lab_center lc ON aa.center_id = lc.id\n" +
                "WHERE aa.id = "+id;
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> objects=query.getResultList();
        if(objects.size()!=0){
            Object[] o=objects.get(0);
            assetsApplyDTO.setDate(o[0]!=null?o[0].toString():null);//申请日期
            assetsApplyDTO.setApplicantUserName(o[1]!=null?o[1].toString():null);//申请人
            assetsApplyDTO.setStartDate(o[2]!=null?o[2].toString():null);//开始时间
            assetsApplyDTO.setEndDate(o[3]!=null?o[3].toString():null);//结束时间
            assetsApplyDTO.setAcademyNumber(o[4]!=null?o[4].toString():null);//学院
            assetsApplyDTO.setDepartment(o[5]!=null?o[5].toString():null);//中心
            assetsApplyDTO.setGoodsCategory(o[6]!=null?o[6].toString():null);//物资类别
            assetsApplyDTO.setPrice(o[7]!=null?Double.parseDouble(o[7].toString()):null);//当前总价
            assetsApplyDTO.setStatus(o[8]!=null?o[8].toString():null);//状态位
            assetsApplyDTO.setRejectReason(o[9]!=null?o[9].toString():null);//拒绝原因
        }else{
            //获取当前日期
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //获取当前登录用户
            User user=shareService.getUser();
            String username=user.getCname()+"("+user.getUsername()+")";
            //获取学院
            SchoolAcademy academy=user.getSchoolAcademy();
            String academyNumber=academy.getAcademyNumber();
            assetsApplyDTO.setDate(sdf.format(dt));
            assetsApplyDTO.setApplicantUserName(username);
            assetsApplyDTO.setAcademyNumber(academyNumber);
        }
        return assetsApplyDTO;
    }

    /**
     * 根据id获物资申购条目具体信息
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-11
     */
    public AssetsApplyItemDTO findAssetApplyItemDetailById(Integer id) throws Exception{
        AssetsApplyItemDTO assetsApplyItemDTO=new AssetsApplyItemDTO();
        String sql="SELECT\n" +
                "  a.id,\n" +
                "\ta.ch_name,\n" +
                "\ta.specifications,\n" +
                "\ta.unit,\n" +
                "\taar.app_quantity,\n" +
                "\taar.app_price,\n" +
                "\taar.app_supplier\n" +
                "FROM\n" +
                "\tasset_app_record aar\n" +
                "LEFT JOIN asset a ON aar.asset_id = a.id\n" +
                "WHERE aar.id="+id;
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> objects=query.getResultList();
        if(objects.size()!=0){
            Object[] o=objects.get(0);
            assetsApplyItemDTO.setAssetsId(o[0]!=null?o[0].toString():null);//物资编号
            assetsApplyItemDTO.setName(o[1]!=null?o[1].toString():null);//物资名称
            assetsApplyItemDTO.setType(o[2]!=null?o[2].toString():null);//物资型号
            assetsApplyItemDTO.setUnit(o[3]!=null?o[3].toString():null);//单位
            assetsApplyItemDTO.setQuantity(o[4]!=null?Integer.parseInt(o[4].toString()):null);//数量
            BigDecimal bd =new BigDecimal(o[5].toString());
            assetsApplyItemDTO.setPrice(bd);//保存价格
            assetsApplyItemDTO.setFactory(o[6]!=null?o[6].toString():null);//物资类别
        }
        return assetsApplyItemDTO;
    }

    /**
     * 根据id获物资申购条目具体信息
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-11
     */
    public AssetsApplyItemDTO findAssetInStorageItemDetailById(Integer id) throws Exception{
        AssetsApplyItemDTO assetsApplyItemDTO=new AssetsApplyItemDTO();
        String sql="SELECT\n" +
                "\ta.id,\n" +
                "\ta.ch_name,\n" +
                "\ta.specifications,\n" +
                "\ta.unit,\n" +
                "\tasr.quantity,\n" +
                "\tasr.price,\n" +
                "\tasr.supplier,\n" +
                "\tasr.cabinet_id,\n" +
                "\tasr.invoice_number,\n" +
                "\tasr.info\n" +
                "FROM\n" +
                "\tasset_storage_record asr\n" +
                "LEFT JOIN asset a ON asr.asset_id = a.id\n" +
                "WHERE\n" +
                "\tasr.id = "+id;
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> objects=query.getResultList();
        if(objects.size()!=0){
            Object[] o=objects.get(0);
            assetsApplyItemDTO.setAssetsId(o[0]!=null?o[0].toString():null);//物资编号
            assetsApplyItemDTO.setName(o[1]!=null?o[1].toString():null);//物资名称
            assetsApplyItemDTO.setType(o[2]!=null?o[2].toString():null);//物资型号
            assetsApplyItemDTO.setUnit(o[3]!=null?o[3].toString():null);//单位
            assetsApplyItemDTO.setQuantity(o[4]!=null?Integer.parseInt(o[4].toString()):null);//数量
            BigDecimal bd =new BigDecimal(o[5].toString());
            assetsApplyItemDTO.setPrice(bd);//保存价格
            assetsApplyItemDTO.setFactory(o[6]!=null?o[6].toString():null);
            assetsApplyItemDTO.setCabinet(o[7]!=null?o[7].toString():null);
            assetsApplyItemDTO.setInvoiceNumber(o[8]!=null?o[8].toString():null);
            assetsApplyItemDTO.setItemRemarks(o[9]!=null?o[9].toString():null);
        }
        return assetsApplyItemDTO;
    }

    /**
     * 根据id获物资申购条目具体信息
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-11
     */
    public AssetsApplyItemDTO findAssetReceiveItemDetailById(Integer id) throws Exception{
        AssetsApplyItemDTO assetsApplyItemDTO=new AssetsApplyItemDTO();
        String sql="SELECT\n" +
                "\ta.id,\n" +
                "\ta.ch_name,\n" +
                "\ta.specifications,\n" +
                "\ta.unit,\n" +
                "\tarr.quantity,\n" +
                "\tarr.cabinet_id,\n" +
                "\tacr.stock_number,\n" +
                "\ta.factory\n" +
                "FROM\n" +
                "\tasset_receive_record arr\n" +
                "LEFT JOIN asset a ON arr.asset_id = a.id\n" +
                "LEFT JOIN asset_cabinet_record acr on (arr.cabinet_id=acr.cabinet_id and arr.asset_id=acr.asset_id) where arr.id="+id;
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> objects=query.getResultList();
        if(objects.size()!=0){
            Object[] o=objects.get(0);
            assetsApplyItemDTO.setAssetsId(o[0]!=null?o[0].toString():null);//物资编号
            assetsApplyItemDTO.setName(o[1]!=null?o[1].toString():null);//物资名称
            assetsApplyItemDTO.setType(o[2]!=null?o[2].toString():null);//物资型号
            assetsApplyItemDTO.setUnit(o[3]!=null?o[3].toString():null);//单位
            //原数据库表定义类型为decimal,先做截取处理
            String quantity=o[4].toString();
            String quantity1=quantity.substring(0,quantity.length()-3);
            assetsApplyItemDTO.setQuantity(Integer.parseInt(quantity1));//数量
            assetsApplyItemDTO.setCabinet(o[5]!=null?o[5].toString():null);//物品柜
            assetsApplyItemDTO.setAmount(o[6]!=null?o[6].toString():null);//剩余数量
            assetsApplyItemDTO.setFactory(o[7]!=null?o[7].toString():null);//物品柜
        }
        return assetsApplyItemDTO;
    }
    /**
     * 根据id获物资入库
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-1
     */
    public AssetsInStorageDTO findAssetInStorageDetailById(Integer id) throws Exception{
        AssetsInStorageDTO assetsInStorageDTO=new AssetsInStorageDTO();//对象初始化
        String sql="SELECT\n" +
                "\tass.id,\n" +
                "\tass.cabinet_id,\n" +
                "\tCONCAT(u.cname,\"(\",u.username,\")\"),\n" +
                "\tass.date,\n" +
                "\tass.batch_number,\n" +
                "  ass.academy_number,\n" +
                "  ass.center_id,\n" +
                "  ass.classification_id,\n" +
                "  ass.total_price,\n" +
                "  ass.status,\n" +
                "  ass.apply_date,\n" +
                "  ass.reject_reason\n" +
                "FROM\n" +
                "\tasset_storage ass\n" +
                "LEFT JOIN `user` u on ass.username=u.username\n" +
                "WHERE ass.id="+id;
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> objects=query.getResultList();
        if(objects.size()!=0){
            Object[] o=objects.get(0);
            assetsInStorageDTO.setId(o[0]!=null?Integer.parseInt(o[0].toString()):null);//id
            assetsInStorageDTO.setCabinet(o[1]!=null?o[1].toString():null);//物品柜
            assetsInStorageDTO.setUsername(o[2]!=null?o[2].toString():null);//入库人
            assetsInStorageDTO.setDate(o[3]!=null?o[3].toString():null);//发起入库时间
            assetsInStorageDTO.setBatchNumber(o[4]!=null?o[4].toString():null);//编号
            assetsInStorageDTO.setAcademyNumber(o[5]!=null?o[5].toString():null);//学院
            assetsInStorageDTO.setDepartment(o[6]!=null?o[6].toString():null);//中心
            assetsInStorageDTO.setGoodsCategory(o[7]!=null?o[7].toString():null);//物资类别
            assetsInStorageDTO.setTotalPrice(o[8]!=null?o[8].toString():null);//当前总价
            assetsInStorageDTO.setStatus(o[9]!=null?o[9].toString():null);//状态
            assetsInStorageDTO.setApplyDate(o[10]!=null?o[10].toString():null);//采购日期
            assetsInStorageDTO.setRejectReason(o[11]!=null?o[11].toString():null);//拒绝原因
        }else{
            //获取当前日期
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //获取当前登录用户
            User user=shareService.getUser();
            String username=user.getCname()+"("+user.getUsername()+")";
            assetsInStorageDTO.setDate(sdf.format(dt));
            assetsInStorageDTO.setUsername(username);//入库人
            assetsInStorageDTO.setAcademyNumber(user.getSchoolAcademy().getAcademyNumber());
        }
        return assetsInStorageDTO;
    }
    /**
     * 根据id获物资申领数据
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-3
     */
    public AssetsReceiveDTO findAssetReceiveDetailById(Integer id) throws Exception{
        AssetsReceiveDTO assetsReceiveDTO=new AssetsReceiveDTO();
        String sql="SELECT\n" +
                "\tac.id,\n" +
                "\tar.receive_date,\n" +
                "\tCONCAT(u.cname, \"(\", u.username, \")\"),\n" +
                "\tu.academy_number,\n" +
                "\tar.center_id,\n" +
                "\tar.start_data,\n" +
                "\tar.end_date,\n" +
                "\tar.receive_no,\n" +
                "\tar. STATUS,\n" +
                "  ac.is_need_return,\n" +
                "  ar.asset_usage,\n" +
                "  ar.reject_reason\n" +
                "FROM\n" +
                "\tasset_receive ar\n" +
                "LEFT JOIN `user` u ON ar.app_user = u.username\n" +
                "LEFT JOIN asset_classification ac on ar.category_id=ac.id \n" +
                "where ar.id="+id;
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> objects=query.getResultList();
        if(objects.size()!=0){
            Object[] o=objects.get(0);
            assetsReceiveDTO.setGoodsCategory(o[0]!=null?o[0].toString():null);//物资类别
            assetsReceiveDTO.setApplicationTime(o[1]!=null?o[1].toString().substring(0,19):null);//发起申领时间
            assetsReceiveDTO.setUsername(o[2]!=null?o[2].toString():null);//申领人
            assetsReceiveDTO.setAcademyNumber(o[3]!=null?o[3].toString():null);//学院
            assetsReceiveDTO.setDepartment(o[4]!=null?o[4].toString():null);//中心
            assetsReceiveDTO.setBeginTime(o[5]!=null?o[5].toString().substring(0,19):null);//使用时间
            assetsReceiveDTO.setEndTime(o[6]!=null?o[6].toString().substring(0,19):null);//预计归还时间
            assetsReceiveDTO.setBatchNumber(o[7]!=null?o[7].toString():null);//编号
            assetsReceiveDTO.setStatus(o[8]!=null?o[8].toString():null);//状态
            assetsReceiveDTO.setIsNeedReturn(o[9]!=null?Integer.parseInt(o[9].toString()):null);//是否需要归还
            assetsReceiveDTO.setPurpose(o[10]!=null?o[10].toString():null);//申领用途
            assetsReceiveDTO.setRejectReason(o[11]!=null?o[11].toString():null);//拒绝原因
        }else{
            //获取当前日期
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //获取当前登录用户
            User user=shareService.getUser();
            String username=user.getCname()+"("+user.getUsername()+")";
            assetsReceiveDTO.setApplicationTime(sdf.format(dt));
            assetsReceiveDTO.setUsername(username);//入库人
            assetsReceiveDTO.setAcademyNumber(user.getSchoolAcademy().getAcademyNumber());
        }
        return assetsReceiveDTO;

    }
    /**
     * 根据入库id更新物品柜资源
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-2
     */
    public void saveAssetsCabinetRecordFromInStorage(Integer id){
        String sql="SELECT\n" +
                "\tasr.cabinet_id,\n" +
                "\tasr.asset_id,\n" +
                "\tasr.quantity,\n" +
                "\tasr.warehouse_id\n" +
                "FROM\n" +
                "\tasset_storage_record asr\n" +
                "LEFT JOIN asset_storage ass ON asr.store_id = ass.id\n" +
                "WHERE 1=1 and ass.id="+id;
        Query query=entityManager.createNativeQuery(sql);//获取入库具体条目
        List<Object[]> objects=query.getResultList();
        for(Object[] o:objects){
            //获取库存情况
            AssetCabinetRecord assetCabinetRecord=this.findAssetsCabinetRecordByCabinetAndAssets(Integer.parseInt(o[0].toString()),Integer.parseInt(o[1].toString()));
            if(assetCabinetRecord==null){//无库存时新增库存
                AssetCabinetRecord assetCabinetRecord2=new AssetCabinetRecord();
                assetCabinetRecord2.setCabinetId(Integer.parseInt(o[0].toString()));
                assetCabinetRecord2.setAssetId(Integer.parseInt(o[1].toString()));
                assetCabinetRecord2.setStockNumber(Integer.parseInt(o[2].toString()));
                assetCabinetRecordDAO.store(assetCabinetRecord2);
            }else{//有库存时修改库存
                int quantity=assetCabinetRecord.getStockNumber();
                assetCabinetRecord.setStockNumber(quantity+Integer.parseInt(o[2].toString()));
                assetCabinetRecordDAO.store(assetCabinetRecord);
            }
            //生成入库记录
            AssetCabinetAccessRecord assetCabinetAccessRecord=new AssetCabinetAccessRecord();
            assetCabinetAccessRecord.setAppId(id);
            assetCabinetAccessRecord.setAssetId(Integer.parseInt(o[1].toString()));
            assetCabinetAccessRecord.setCreateDate(new Date());
            assetCabinetAccessRecord.setQuantity(Integer.parseInt(o[2].toString()));
            assetCabinetAccessRecord.setType("InStorage");
            assetCabinetAccessRecord.setUsername(shareService.getUser().getUsername());//登录人为入库人
            assetCabinetAccessRecord.setCabinetId(Integer.parseInt(o[0].toString()));
            if(assetCabinetRecord==null) {
                assetCabinetAccessRecord.setRemainQuantity(Integer.parseInt(o[2].toString()));
            }else{
                assetCabinetAccessRecord.setRemainQuantity(assetCabinetRecord.getStockNumber());
            }
            assetCabinetAccessRecordDAO.store(assetCabinetAccessRecord);
            //对于智能柜来说，保存智能柜记录
            if(o[3]!=null){
                AssetCabinetWarehouseRecord assetCabinetWarehouseRecord=this.findAssetsCabinetWarehouseRecordByWareHouseAndAssets(Integer.parseInt(o[3].toString()),Integer.parseInt(o[1].toString()));
                if(assetCabinetWarehouseRecord==null){//无库存时新增库存
                    AssetCabinetWarehouseRecord assetCabinetWarehouseRecord2=new AssetCabinetWarehouseRecord();
                    assetCabinetWarehouseRecord2.setWarehouseId(Integer.parseInt(o[3].toString()));
                    assetCabinetWarehouseRecord2.setAssetId(Integer.parseInt(o[1].toString()));
                    assetCabinetWarehouseRecord2.setStockNumber(Integer.parseInt(o[2].toString()));
                    assetCabinetWarehouseRecordDAO.store(assetCabinetWarehouseRecord2);
                }else{//有库存时修改库存
                    int quantity=assetCabinetWarehouseRecord.getStockNumber();
                    assetCabinetWarehouseRecord.setStockNumber(quantity+Integer.parseInt(o[2].toString()));
                    assetCabinetWarehouseRecordDAO.store(assetCabinetWarehouseRecord);
                }
                //生成入库记录
                Calendar calendar=Calendar.getInstance();
                AssetCabinetWarehouseAccess assetCabinetWarehouseAccess=new AssetCabinetWarehouseAccess();
                assetCabinetWarehouseAccess.setAppRecordId(id);
                assetCabinetWarehouseAccess.setAssetId(Integer.parseInt(o[1].toString()));
                assetCabinetWarehouseAccess.setCreateDate(calendar);
                assetCabinetWarehouseAccess.setCabinetQuantity(Integer.parseInt(o[2].toString()));
                assetCabinetWarehouseAccess.setType("InStorage");
                assetCabinetWarehouseAccess.setManager(shareService.getUser().getUsername());//登录人为入库人
                assetCabinetWarehouseAccess.setWarehouseId(Integer.parseInt(o[3].toString()));
                if(assetCabinetWarehouseRecord==null) {
                    assetCabinetWarehouseAccess.setRemainQuantity(Integer.parseInt(o[2].toString()));
                }else{
                    assetCabinetWarehouseAccess.setRemainQuantity(assetCabinetRecord.getStockNumber());
                }
                assetCabinetWarehouseAccessDAO.store(assetCabinetWarehouseAccess);
            }
        }
    }

    /**
     * 根据申领id生成出入库记录
     * @param id 名录id
     * * @return 状态字符串
     * @author 吴奇臻 2019-5-15
     */
    public void saveAssetsCabinetRecordFromReceive(Integer id){
        String sql="SELECT\n" +
                "\tarr.asset_id,\n" +
                "\tarr.quantity,\n" +
                "\tarr.cabinet_id,\n" +
                "  arr.return_quantity,\n" +
                "  ar.status,\n" +
                "  ac.is_need_return,\n" +
                "  arr.warehouse_id\n" +
                "FROM\n" +
                "\tasset_receive_record arr\n" +
                "LEFT JOIN asset_receive ar ON arr.receive_id = ar.id\n" +
                "LEFT JOIN asset_classification ac on ar.category_id=ac.id\n" +
                "WHERE 1=1 and ar.id="+id;
        Query query=entityManager.createNativeQuery(sql);//获取入库具体条目
        List<Object[]> objects=query.getResultList();
        for(Object[] o:objects){
            AssetCabinetRecord assetCabinetRecord=this.findAssetsCabinetRecordByCabinetAndAssets(Integer.parseInt(o[2].toString()),Integer.parseInt(o[0].toString()));
            //生成领用记录
            AssetCabinetAccessRecord assetCabinetAccessRecord=new AssetCabinetAccessRecord();
            assetCabinetAccessRecord.setAppId(id);
            assetCabinetAccessRecord.setAssetId(Integer.parseInt(o[0].toString()));
            assetCabinetAccessRecord.setCreateDate(new Date());
            if(Integer.parseInt(o[4].toString())==4) {//
                assetCabinetAccessRecord.setType("ReceiveReturn");
                if(Integer.parseInt(o[5].toString())==0) {//余料归还
                    assetCabinetAccessRecord.setQuantity(Integer.parseInt(o[3].toString()));
                }else{//领用归还
                    assetCabinetAccessRecord.setQuantity(Integer.parseInt(o[1].toString().substring(0,o[1].toString().length()-3)));
                }
            }else{//领用
                assetCabinetAccessRecord.setType("Receive");
                assetCabinetAccessRecord.setQuantity(Integer.parseInt(o[1].toString().substring(0,o[1].toString().length()-3)));
            }
            assetCabinetAccessRecord.setRemainQuantity(assetCabinetRecord.getStockNumber());
            assetCabinetAccessRecord.setUsername(shareService.getUser().getUsername());//登录人为入库人
            assetCabinetAccessRecord.setCabinetId(Integer.parseInt(o[2].toString()));
            assetCabinetAccessRecordDAO.store(assetCabinetAccessRecord);
            //对于智能柜生成智能柜记录
            if(o[6]!=null){
                AssetCabinetWarehouseRecord assetCabinetWarehouseRecord=this.findAssetsCabinetWarehouseRecordByWareHouseAndAssets(Integer.parseInt(o[5].toString()),Integer.parseInt(o[0].toString()));
                //生成领用记录
                AssetCabinetWarehouseAccess assetCabinetWarehouseAccess=new AssetCabinetWarehouseAccess();
                assetCabinetWarehouseAccess.setAppRecordId(id);
                assetCabinetWarehouseAccess.setWarehouseId(Integer.parseInt(o[6].toString()));
                assetCabinetWarehouseAccess.setAssetId(Integer.parseInt(o[0].toString()));
                Calendar calendar=Calendar.getInstance();
                assetCabinetWarehouseAccess.setCreateDate(calendar);
                if(Integer.parseInt(o[4].toString())==4) {//
                    assetCabinetWarehouseAccess.setType("ReceiveReturn");
                    if(Integer.parseInt(o[5].toString())==0) {//余料归还
                        assetCabinetWarehouseAccess.setCabinetQuantity(Integer.parseInt(o[3].toString()));
                    }else{//领用归还
                        assetCabinetWarehouseAccess.setCabinetQuantity(Integer.parseInt(o[1].toString().substring(0,o[1].toString().length()-3)));
                    }
                }else{//领用
                    assetCabinetWarehouseAccess.setType("Receive");
                    assetCabinetWarehouseAccess.setCabinetQuantity(Integer.parseInt(o[1].toString().substring(0,o[1].toString().length()-3)));
                }
                assetCabinetWarehouseAccess.setRemainQuantity(assetCabinetRecord.getStockNumber());
                assetCabinetWarehouseAccess.setManager(shareService.getUser().getUsername());//登录人为入库人
                assetCabinetWarehouseAccessDAO.store(assetCabinetWarehouseAccess);
            }
        }
    }
    /**
     * 根据物品柜id和物资id确认物品柜物资记录
     * =
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-2
     */
    public AssetCabinetRecord findAssetsCabinetRecordByCabinetAndAssets(Integer cabinetId, Integer assetsId){
        StringBuffer sql = new StringBuffer("select c from AssetCabinetRecord c where c.cabinetId = " + cabinetId +" and c.assetId= "+assetsId );
        AssetCabinetRecord assetCabinetRecord=new AssetCabinetRecord();
        if(assetCabinetRecordDAO.executeQuery(sql.toString()).size()!=0) {
            assetCabinetRecord = assetCabinetRecordDAO.executeQuery(sql.toString()).get(0);
            return assetCabinetRecord;
        }else{
        return null;
        }
    }

    /**
     * 根据物品柜id和物资id确认智能柜具体物资记录
     * =
     * * @return 状态字符串
     * @author 吴奇臻 2019-6-10
     */
    public List<AssetCabinetWarehouseRecord> findAssetsCabinetWarehouseRecordByCabinetAndAssets(Integer cabinetId, Integer assetsId){
        String sql="SELECT\n" +
                "\tacwr.id,acwr.warehouse_id,acwr.asset_id,acwr.stock_number\n" +
                "FROM\n" +
                "\tasset_cabinet_warehouse_record acwr\n" +
                "LEFT JOIN asset_cabinet_warehouse acw on acw.id=acwr.warehouse_id\n" +
                "where acw.asset_cabinet_id="+cabinetId+" and acwr.asset_id="+assetsId+"";
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> objects=query.getResultList();
        List<AssetCabinetWarehouseRecord> assetCabinetWarehouseRecords=new ArrayList<>();
        for(Object[] o:objects){
            AssetCabinetWarehouseRecord assetCabinetWarehouseRecord=new AssetCabinetWarehouseRecord();
            assetCabinetWarehouseRecord.setId(o[0]!=null?Integer.parseInt(o[0].toString()):null);
            assetCabinetWarehouseRecord.setWarehouseId(o[1]!=null?Integer.parseInt(o[1].toString()):null);
            assetCabinetWarehouseRecord.setAssetId(o[2]!=null?Integer.parseInt(o[2].toString()):null);
            assetCabinetWarehouseRecord.setStockNumber(o[3]!=null?Integer.parseInt(o[3].toString()):null);
            assetCabinetWarehouseRecords.add(assetCabinetWarehouseRecord);
        }
        return assetCabinetWarehouseRecords;
    }
    /**
     * 根据柜门id和物资id确认物品柜物资记录
     * =
     * * @return 状态字符串
     * @author 吴奇臻 2019-6-6
     */
    public AssetCabinetWarehouseRecord findAssetsCabinetWarehouseRecordByWareHouseAndAssets(Integer warehouseId, Integer assetsId){
        StringBuffer sql = new StringBuffer("select c from AssetCabinetWarehouseRecord c where c.warehouseId = " + warehouseId +" and c.assetId= "+assetsId );
        AssetCabinetWarehouseRecord assetCabinetWarehouseRecord=new AssetCabinetWarehouseRecord();
        if(assetCabinetWarehouseRecordDAO.executeQuery(sql.toString()).size()!=0) {
            assetCabinetWarehouseRecord = assetCabinetWarehouseRecordDAO.executeQuery(sql.toString()).get(0);
            return assetCabinetWarehouseRecord;
        }else{
            return null;
        }
    }
    /**
     * 根据申购ID获取所有申购条目
     *
     * @author 吴奇臻 2019-4-18
     */
    public List<AssetAppRecord> findAssetsAppRecordByAssetsApp(Integer appId){
        StringBuffer sql = new StringBuffer("select c from AssetAppRecord c where c.assetApp.id= "+appId);
        List<AssetAppRecord> assetAppRecordList=assetAppRecordDAO.executeQuery(sql.toString());
        return assetAppRecordList;
    }
    /**
     * 根据申购ID获取所有申购条目
     *
     * @author 吴奇臻 2019-4-18
     */
    public List<AssetStorageRecord> findAssetsStorageRecordByAssetsStorage(Integer storeId){
        StringBuffer sql = new StringBuffer("select c from AssetStorageRecord c where c.storeId= "+storeId);
        List<AssetStorageRecord> assetStorageRecordList=assetStorageRecordDAO.executeQuery(sql.toString());
        return assetStorageRecordList;
    }

    /**
     * 根据申领ID获取所有申购条目
     *
     * @author 吴奇臻 2019-4-18
     */
    public List<AssetReceiveRecord> findAssetsReceiveRecordByAssetsReceive(Integer receiveId){
        StringBuffer sql = new StringBuffer("select c from AssetReceiveRecord c where c.assetReceive.id= "+receiveId );
        List<AssetReceiveRecord> assetReceiveRecordList=assetReceiveRecordDAO.executeQuery(sql.toString());
        return assetReceiveRecordList;
    }
    /**
     * 根据物资类别判断是否需要进行归还流程
     * @param id
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-2
     */
    public Integer checkAssetsClassificationIsNeedReturn(Integer id){
        String sql="select is_need_return from asset_classification ac where ac.id="+id;
        Query query=entityManager.createNativeQuery(sql);
        Integer isNeedReturn=Integer.parseInt(query.getSingleResult().toString());//0不需要归还，1需要归还
        return isNeedReturn;
    }
    /**
     * 根据物资id,和物品柜id判断剩余物资数量
     * @param id
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-2
     */
    public Integer getAssetsAmountFromCabinet(Integer id,Integer assetId,Integer quantity,Integer itemId){
        String sql="select stock_number from asset_cabinet_record acr where acr.cabinet_id="+id+" and acr.asset_id="+assetId;
        Query query=entityManager.createNativeQuery(sql);
        Integer amount=Integer.parseInt(query.getSingleResult().toString());
        if(quantity!=null){
            amount=amount-quantity;//计算剩余数量
        }
        if(itemId!=null){
            AssetReceiveRecord assetReceiveRecord=assetReceiveRecordDAO.findAssetReceiveRecordByPrimaryKey(itemId);//编辑时获取具体条目
            amount=amount+assetReceiveRecord.getQuantity().intValue();
        }
        return amount;
    }

    /**
     * 保存物资余料归还数
     *
     * * @return 状态字符串
     * @author 吴奇臻 2019-5-10
     */
    public String saveReturnAssetsRemain(Integer amount, Integer id){
        //获取物资申领记录
        AssetReceiveRecord assetReceiveRecord=assetReceiveRecordDAO.findAssetReceiveRecordByPrimaryKey(id);
        //保存数量
        assetReceiveRecord.setReturnQuantity(amount);
        assetReceiveRecordDAO.store(assetReceiveRecord);
        //更新库存数
        Integer cabinetId=assetReceiveRecord.getCabinetId();
        Integer assetsId=assetReceiveRecord.getAsset().getId();
        AssetCabinetRecord assetCabinetRecord = this.findAssetsCabinetRecordByCabinetAndAssets(cabinetId,assetsId);
        assetCabinetRecord.setStockNumber(assetCabinetRecord.getStockNumber()+amount);
        assetCabinetRecordDAO.store(assetCabinetRecord);
        return "success";
    }
    /**
     * 保存图片记录
     *
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-8
     */
    public String saveAssetsRelatedImage(String url,String name,String size,Integer appId,String type){
        this.deleteAssetsRelatedImage(appId,type);//删除之前保存的图片
        AssetRelatedImage assetRelatedImage=new AssetRelatedImage();//初始化对象
        String urls[]=url.split(",");//分别获取多图片的url
        String names[]=name.split(",");//分别获取多图片的name
        String sizes[]=size.split(",");//分别获取多图片的size
        if(urls.length!=0){
            for(int i=0;i<urls.length;i++){
                String imageUrl=urls[i];
                String imageName=names[i];
                String imageSize=sizes[i];
                assetRelatedImage.setAppId(appId);
                assetRelatedImage.setImageUrl(imageUrl);
                assetRelatedImage.setType(type);
                assetRelatedImage.setImageName(imageName);
                assetRelatedImage.setImageSize(imageSize);
                assetRelatedImageDAO.store(assetRelatedImage);
            }
        }
        return "success";
    }

    /**
     * 删除图片记录
     *
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-8
     */
    public void deleteAssetsRelatedImage(Integer appId,String type){
        List<AssetRelatedImage> assetRelatedImageList=new ArrayList<>();
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select c from AssetRelatedImage c where  c.appId = " + appId +" and c.type='"+type+"'" );
        if(assetRelatedImageDAO.executeQuery(sql.toString()).size()!=0) {
            assetRelatedImageList = assetRelatedImageDAO.executeQuery(sql.toString());
            for(int i=0;i<assetRelatedImageList.size();i++){
                AssetRelatedImage assetRelatedImage=assetRelatedImageList.get(i);
                assetRelatedImageDAO.remove(assetRelatedImage);
            }
        }
    }

    /**
     * 获取图片记录
     *
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-8
     */
    public List<AssetRelatedImage> getAssetsRelatedImage(Integer id,String type){
        List<AssetRelatedImage> assetRelatedImageList=new ArrayList<>();
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select c from AssetRelatedImage c where  c.appId = " + id +" and c.type='"+type+"'" );
        if(assetRelatedImageDAO.executeQuery(sql.toString()).size()!=0) {
            assetRelatedImageList = assetRelatedImageDAO.executeQuery(sql.toString());
        }
        return assetRelatedImageList;
    }

    /**
     * 获取审核标志位
     *
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-8
     */
    public boolean getAssetsApplyAuditFlag(String tag,Integer status){
        boolean flag=false;
        String username=shareService.getUser().getUsername();
        String sql="select a.authority_name from user_authority ua LEFT JOIN authority a on ua.authority_id=a.id where user_id='"+username+"'";
        Query query=entityManager.createNativeQuery(sql);
        List<String> authorityList=query.getResultList();
        if(authorityList.contains(tag)&&status==1){
            flag = true;
        }
        if(tag==null){
            flag = true;
        }
        return flag;
    }

    /**
     * 根据物资及数量自动分配物品柜，更新物品柜数量，并返回状态
     *
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-8
     */
    public String allocateCabinetFromAssets(Integer assetsId,Integer quantity,Integer itemId,Integer appId){
        String sql="select cabinet_id,stock_number from asset_cabinet_record acr where acr.asset_id="+assetsId;//查询物品柜记录
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> objects=query.getResultList();
        Integer amount=0;//计算物资总量
        Integer cabinetId=0;//分配物品柜
        Integer warehouseId=0;//计算智能柜柜门
        Integer max=0;//获取最大值
        AssetReceive assetReceive=assetReceiveDAO.findAssetReceiveById(appId);
        if(objects.size()>1) {//物品柜不止一个时
            for (int i = 0; i < objects.size(); i++) {
                amount += Integer.parseInt(objects.get(i)[1].toString());
            }
            for (int i = 0; i < objects.size() - 1; i++) {//选取物品柜
                if (Integer.parseInt(objects.get(i)[1].toString()) > Integer.parseInt(objects.get(i + 1)[1].toString())) {
                    max = i;
                } else {
                    max = i + 1;
                }
                cabinetId = Integer.parseInt(objects.get(max)[0].toString());
            }
        }else if(objects.size()==1){//只有一个物品柜时
            amount += Integer.parseInt(objects.get(0)[1].toString());
            cabinetId=Integer.parseInt(objects.get(0)[0].toString());
        } else { //没有物品柜
            return "noCabinet";
        }
        if(amount<quantity&&assetReceive.getOperationItem()==null){//总数小于申请数时，无法申请
            return "insufficient";
        }else{
            AssetCabinetRecord assetCabinetRecord = this.findAssetsCabinetRecordByCabinetAndAssets(cabinetId,assetsId);
            if(itemId!=null&&!itemId.equals("")&& itemId != 0) {
                AssetReceiveRecord assetReceiveRecord=assetReceiveRecordDAO.findAssetReceiveRecordByPrimaryKey(itemId);
                assetCabinetRecord.setStockNumber(assetCabinetRecord.getStockNumber()+assetReceiveRecord.getQuantity().intValue()-quantity);
            }else{
                assetCabinetRecord.setStockNumber(assetCabinetRecord.getStockNumber() - quantity);
            }
            assetCabinetRecordDAO.store(assetCabinetRecord);
            //如果是智能柜的情况
            AssetCabinet assetCabinet=assetCabinetDAO.findAssetCabinetByPrimaryKey(cabinetId);
            if(assetCabinet.getType()==2){//智能柜类型
                List<AssetCabinetWarehouseRecord> assetCabinetWarehouseRecordList=this.findAssetsCabinetWarehouseRecordByCabinetAndAssets(cabinetId,assetsId);
                //目前只进行智能柜单个扣除
                int no=0;//智能柜所在顺序
                if(assetCabinetWarehouseRecordList.size()>1) {
                    for (int x = 0; x < assetCabinetWarehouseRecordList.size() - 1; x++) {
                        if (assetCabinetWarehouseRecordList.get(x).getStockNumber() > assetCabinetWarehouseRecordList.get(x + 1).getStockNumber()) {
                            no = x;
                        } else {
                            no = x + 1;
                        }
                        warehouseId = assetCabinetWarehouseRecordList.get(no).getWarehouseId();
                    }
                }else if(assetCabinetWarehouseRecordList.size()==1){//只有一个物品柜时
                    warehouseId = assetCabinetWarehouseRecordList.get(no).getWarehouseId();
                }
                //扣除智能柜库存
                AssetCabinetWarehouseRecord assetCabinetWarehouseRecord=this.findAssetsCabinetWarehouseRecordByWareHouseAndAssets(warehouseId,assetsId);
                if(itemId!=null&&!itemId.equals("")&& itemId != 0) {
                    AssetReceiveRecord assetReceiveRecord=assetReceiveRecordDAO.findAssetReceiveRecordByPrimaryKey(itemId);
                    assetCabinetWarehouseRecord.setStockNumber(assetCabinetRecord.getStockNumber()+assetReceiveRecord.getQuantity().intValue()-quantity);
                }else{
                    assetCabinetWarehouseRecord.setStockNumber(assetCabinetRecord.getStockNumber() - quantity);
                }
                assetCabinetWarehouseRecordDAO.store(assetCabinetWarehouseRecord);
            }
            return cabinetId.toString()+"-"+warehouseId.toString();
           }
    }

    /**
     * 根据物资及数量自动获取库存数最大的物品柜
     *
     * * @return 状态字符串
     * @author 吴奇臻 2019-4-8
     */
    public Integer getMaxAmountCabinetFromAssets(Integer assetsId,Integer quantity){
        String sql="select cabinet_id,stock_number from asset_cabinet_record acr where acr.asset_id="+assetsId;//查询物品柜记录
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> objects=query.getResultList();
        Integer cabinetId=0;//分配物品柜
        Integer max=0;//获取最大值
        for(int i=0;i<objects.size()-1;i++){//选取物品柜
            if(Integer.parseInt(objects.get(i)[1].toString())>Integer.parseInt(objects.get(i+1)[1].toString())){
                max=i;
            }else{
                max=i+1;
            }
            cabinetId=Integer.parseInt(objects.get(max)[0].toString());
        }
        return cabinetId;
    }
    /**
     * Description 获取物资类别列表
     * @param page
     * @param limit
     * @param cname
     * @return json字符串格式的物资分类数据
     * @author 伍菁 2019-4-2
     */
    public JSONObject findAssetClassificationList(Integer page,Integer limit,String cname){
        String sql="select id,cname,ename,info,is_need_return,apply_audit,storage_audit,receive_audit from asset_classification";
        if(cname!=null&&!cname.equals("")){
            sql+=" where cname like '%"+cname+"%'";
        }
        Query query=entityManager.createNativeQuery(sql);
        query.setMaxResults(limit);
        query.setFirstResult((page-1)*limit);
        List<Object[]> objects=query.getResultList();
        List<MaterialKindDTO> materialKindDTOList=new ArrayList<>();
        for(Object[] o:objects){
            MaterialKindDTO materialKindDTO=new MaterialKindDTO();
            materialKindDTO.setId(o[0]!=null?Integer.parseInt(o[0].toString()):null);
            materialKindDTO.setCname(o[1]!=null?o[1].toString():null);
            materialKindDTO.setEname(o[2]!=null?o[2].toString():null);
            materialKindDTO.setInfo(o[3]!=null?o[3].toString():null);
            Integer isNeedReturn = o[4]!=null?Integer.valueOf(o[4].toString()):null;
            if(isNeedReturn!=null&&isNeedReturn==0){
                materialKindDTO.setIsNeedReturn("否");
            }else if(isNeedReturn!=null&&isNeedReturn==1){
                materialKindDTO.setIsNeedReturn("是");
            }else if(isNeedReturn==null){
                materialKindDTO.setIsNeedReturn("没有设置该参数");
            }
            materialKindDTO.setApplyAudit(o[5]!=null?o[5].toString():null);
            materialKindDTO.setStorageAudit(o[6]!=null?o[6].toString():null);
            materialKindDTO.setReceiveAudit(o[7]!=null?o[7].toString():null);
            materialKindDTOList.add(materialKindDTO);
        }
        int totalRecords=entityManager.createNativeQuery(sql).getResultList().size();
        List<MaterialKindDTO> materialKindDTOListFinal = new ArrayList<>();
        for (MaterialKindDTO m : materialKindDTOList) {
            //申领审核
            String applyAudit = m.getApplyAudit();
            String applyAuditName = "";
            if (!"无需审核".equals(applyAudit) && !"".equals(applyAudit) && applyAudit != null) {
                String[] strings1 = applyAudit.split(",");
                for (int i = 0; i < strings1.length; i++) {
                    if ("OPEARTIONSECURITYMANAGEMENT".equals(strings1[i])) {
                        applyAuditName += "运行与安全管理科" + "->";
                    } else if ("EXCENTERDIRECTOR".equals(strings1[i])) {
                        applyAuditName += "实验中心主任" + "->";
                    }
                }
                applyAuditName = applyAuditName.substring(0, applyAuditName.length() - 2);
                m.setApplyAudit(applyAuditName);
            }
            //入库审核
            String storageAudit = m.getStorageAudit();
            String storageAuditName = "";
            if (!"无需审核".equals(storageAudit) && !"".equals(storageAudit) && storageAudit != null) {
                String[] strings2 = storageAudit.split(",");
                for (int j = 0; j < strings2.length; j++) {
                    if ("OPEARTIONSECURITYMANAGEMENT".equals(strings2[j])) {
                        storageAuditName += "运行与安全管理科" + "->";
                    } else if ("EXCENTERDIRECTOR".equals(strings2[j])) {
                        storageAuditName += "实验中心主任" + "->";
                    }
                }
                storageAuditName = storageAuditName.substring(0, storageAuditName.length() - 2);
                m.setStorageAudit(storageAuditName);
            }
            //领用审核
            String receiveAudit = m.getReceiveAudit();
            String receiveAuditName = "";
            if (!"无需审核".equals(receiveAudit) && !"".equals(receiveAudit) && receiveAudit != null) {
                String[] strings3 = receiveAudit.split(",");
                for (int x = 0; x < strings3.length; x++) {
                    if ("OPEARTIONSECURITYMANAGEMENT".equals(strings3[x])) {
                        receiveAuditName += "运行与安全管理科" + "->";
                    } else if ("EXCENTERDIRECTOR".equals(strings3[x])) {
                        receiveAuditName += "实验中心主任" + "->";
                    }
                }
                receiveAuditName = receiveAuditName.substring(0, receiveAuditName.length() - 2);
                m.setReceiveAudit(receiveAuditName);
            }
            materialKindDTOListFinal.add(m);
            materialKindDTOList = materialKindDTOListFinal;
        }
        JSONObject jsonObject = this.getJSON(materialKindDTOList, totalRecords);
        return jsonObject;
    }
    /**
     * Description 保存物资类别
     * @param materialKindDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     * @author 伍菁 2019-4-2,吴奇臻 2019-4-9
     */
    @Override
    public boolean saveAssetClassification(MaterialKindDTO materialKindDTO) {
        boolean flag=true;
        AssetClassification assetClassification = new AssetClassification();
        try{
            if(!"".equals(materialKindDTO.getId())&&materialKindDTO.getId()!=null) {
                assetClassification = assetClassificationDAO.findAsseClassificationtById(materialKindDTO.getId());
            }
            assetClassification.setCname(materialKindDTO.getCname());
            assetClassification.setEname(materialKindDTO.getEname());
            assetClassification.setInfo(materialKindDTO.getInfo());
            assetClassification.setIsNeedReturn(Integer.valueOf(materialKindDTO.getIsNeedReturn()));
            assetClassification.setApplyAudit(materialKindDTO.getApplyAudit());
            assetClassification.setStorageAudit(materialKindDTO.getStorageAudit());
            assetClassification.setReceiveAudit(materialKindDTO.getReceiveAudit());
            assetClassification = assetClassificationDAO.store(assetClassification);
            //保存审核状态
            String type = "AssetsClassification";
            if(!"无需审核".equals(assetClassification.getApplyAudit())) {
                //先保存审核层级
                String type1 = type + "ApplyAudit"+assetClassification.getId();
                String auditLevelConfig1 = materialKindDTO.getApplyAudit();
                String result1=auditService.saveBusinessAuditConfigLevel(type1,auditLevelConfig1);
                JSONObject jsonObject = JSON.parseObject(result1);
                result1=jsonObject.getString("status");
                if(!"success".equals(result1)){
                    flag = false;
                }
                //再保存审核层级开启情况(默认都开启）
                int configNum = auditLevelConfig1.split(",").length;
                StringBuilder configs1 = new StringBuilder();
                for (int i = 0; i < configNum; i++) {
                    configs1.append("on,");
                }
                configs1 = new StringBuilder(configs1.substring(0, configs1.length() - 1));
                String s=auditService.saveBusinessAudit(assetClassification.getId().toString(),configs1.toString(),type1);
            }
            if(!"无需审核".equals(assetClassification.getStorageAudit())) {
                //先保存审核层级
                String type2 = type + "StorageAudit"+assetClassification.getId();
                String auditLevelConfig2 = assetClassification.getStorageAudit();
                String result2=auditService.saveBusinessAuditConfigLevel(type2,auditLevelConfig2);
                JSONObject jsonObject = JSON.parseObject(result2);
                result2=jsonObject.getString("status");
                if(!"success".equals(result2)){
                    flag = false;
                }
                //再保存审核层级开启情况
                int configNum = auditLevelConfig2.split(",").length;
                StringBuilder configs2 = new StringBuilder();
                for (int i = 0; i < configNum; i++) {
                    configs2.append("on,");
                }
                configs2 = new StringBuilder(configs2.substring(0, configs2.length() - 1));
                String s=auditService.saveBusinessAudit(assetClassification.getId().toString(),configs2.toString(),type2);
            }
            if(!"无需审核".equals(assetClassification.getReceiveAudit())) {
                String type3 = type+"ReceiveAudit"+assetClassification.getId();
                String auditLevelConfig3 = assetClassification.getReceiveAudit();
                String result3=auditService.saveBusinessAuditConfigLevel(type3,auditLevelConfig3);
                JSONObject jsonObject = JSON.parseObject(result3);
                result3=jsonObject.getString("status");
                if(!"success".equals(result3)){
                    flag = false;
                }
                //再保存审核层级开启情况
                int configNum = auditLevelConfig3.split(",").length;
                StringBuilder configs3 = new StringBuilder();
                for (int i = 0; i < configNum; i++) {
                    configs3.append("on,");
                }
                configs3 = new StringBuilder(configs3.substring(0, configs3.length() - 1));
                String s=auditService.saveBusinessAudit(assetClassification.getId().toString(),configs3.toString(),type3);
            }
        }catch (Exception e){
            flag=false;
        }
        return flag;
    }

    /**
     * Description 保存物品柜
     * @author 吴奇臻 2019-5-15
     */
    public boolean saveAssetsCabinet(AssetsCabinetDTO assetsCabinetDTO){
        boolean flag=true;
        try {
            AssetCabinet assetCabinet = new AssetCabinet();
            assetCabinet.setId(assetsCabinetDTO.getId());
            assetCabinet.setCabinetCode(assetsCabinetDTO.getCabinetCode());
            assetCabinet.setCabinetName(assetsCabinetDTO.getCabinetName());
            assetCabinet.setLocation(assetsCabinetDTO.getLocation());
            assetCabinet.setType(Integer.parseInt(assetsCabinetDTO.getType()));
            if(assetsCabinetDTO.getType().equals("2")){
                assetCabinet.setHardwareIp(assetsCabinetDTO.getHardwareIp());
                assetCabinet.setHardwareType("685");
                assetCabinet.setServerId(Integer.parseInt(assetsCabinetDTO.getServerId()));
            }
            assetCabinetDAO.store(assetCabinet);
        }catch (Exception e){
            e.printStackTrace();
            flag=false;
        }
        return  flag;
    }
    /**
     * 根据id获取物资类别详细数据
     * @param id 物资类别id
     * @return 状态字符串
     * @author 伍菁 2019-4-3
     */
    public MaterialKindDTO findAssetClassificationById(Integer id){
        MaterialKindDTO materialKindDTO=new MaterialKindDTO();
        String sql="select id,cname,ename,info,is_need_return from asset_classification c where id='"+id+"'";
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> objects=query.getResultList();
        if(objects!=null&&!objects.isEmpty()) {
            Object[] o = objects.get(0);
            materialKindDTO.setId(o[0] != null ? Integer.valueOf(o[0].toString()) : null);
            materialKindDTO.setCname(o[1] != null ? o[1].toString() : null);
            materialKindDTO.setEname(o[2] != null ? o[2].toString() : null);
            materialKindDTO.setInfo(o[3] != null ? o[3].toString() : null);
            Integer isNeedReturn = o[4]!=null?Integer.valueOf(o[4].toString()):null;
            if(isNeedReturn!=null&&isNeedReturn==0){
                materialKindDTO.setIsNeedReturn("否");
            }else if(isNeedReturn!=null&&isNeedReturn==1){
                materialKindDTO.setIsNeedReturn("是");
            }else if(isNeedReturn==null){
                materialKindDTO.setIsNeedReturn("没有设置该参数");
            }
        }
        //获取审核层级
        String project = pConfig.PROJECT_NAME;
        String businessType = project + "AssetsClassification";
        //申领审核
        Map<String, String> param1 = new HashMap<>();
        param1.put("businessType",businessType+"ApplyAudit"+id);
        String result1 = HttpClientUtil.doPost(pConfig.auditServerUrl+"/audit/getBusinessAuditConfigLevel", param1);
        JSONArray applyArray = JSON.parseObject(result1).getJSONArray("data");
        Integer applyAuditLevel = 0;
        String applyAudit="";
        if(applyArray != null && applyArray.size() > 0 ){
            applyAuditLevel = applyArray.size();
            for(int a=0;a<applyAuditLevel;a++){
                String name1 = ((JSONObject)applyArray.get(a)).getString("authId");
                applyAudit+=name1+",";
            }
            applyAudit=applyAudit.substring(0,applyAudit.length()-1);
        }else{
            applyAudit="无需审核";
        }
        materialKindDTO.setApplyAuditLevel(applyAuditLevel);
        materialKindDTO.setApplyAudit(applyAudit);
        //入库审核
        Map<String, String> param2 = new HashMap<>();
        param2.put("businessType",businessType+"StorageAudit"+id);
        String result2 = HttpClientUtil.doPost(pConfig.auditServerUrl+"/audit/getBusinessAuditConfigLevel", param2);
        JSONArray storageArray = JSON.parseObject(result2).getJSONArray("data");
        Integer storageAuditLevel = 0;
        String storageAudit="";
        if(storageArray != null && storageArray.size() > 0) {
            storageAuditLevel = storageArray.size();
            for (int s = 0; s < storageAuditLevel; s++) {
                String name2 = ((JSONObject)storageArray.get(s)).getString("authId");
                storageAudit += name2 + ",";
            }
            storageAudit = storageAudit.substring(0, storageAudit.length() - 1);
        }else {
            storageAudit="无需审核";
        }
        materialKindDTO.setStorageAuditLevel(storageAuditLevel);
        materialKindDTO.setStorageAudit(storageAudit);
        //领用审核
        Map<String, String> param3 = new HashMap<>();
        param3.put("businessType",businessType+"ReceiveAudit"+id);
        String result3 = HttpClientUtil.doPost(pConfig.auditServerUrl+"/audit/getBusinessAuditConfigLevel", param3);
        JSONArray receiveArrary = JSON.parseObject(result3).getJSONArray("data");
        Integer receiveLevel = 0;
        String receiveAudit="";
        if(receiveArrary != null && receiveArrary.size() > 0) {
            receiveLevel = receiveArrary.size();
            for (int r = 0; r <receiveLevel; r++) {
                String name3 = ((JSONObject)receiveArrary.get(r)).getString("authId");
                receiveAudit += name3 + ",";
            }
            receiveAudit = receiveAudit.substring(0, receiveAudit.length() - 1);
        }else {
            receiveAudit="无需审核";
        }
        materialKindDTO.setReceiveAuditLevel(receiveLevel);
        materialKindDTO.setReceiveAudit(receiveAudit);
        return materialKindDTO;
    }
    /**
     * Description 删除物资类别
     * @param id 物资分类id
     * @return 成功-"success"，失败-"fail"
     * @author 伍菁 2019-4-7
     */
    @Override
    public boolean deleteAssetsClassification(Integer id) {
        boolean flag=true;
        try {
            AssetClassification assetClassification=assetClassificationDAO.findAsseClassificationtById(id);
            assetClassificationDAO.remove(assetClassification);
        }catch (Exception e){
            flag=false;
        }
        return flag;
    }
    /**
     * 物资记录列表
     * @param page 当前页数
     * @param limit 当前页限制大小
     * @return json字符串格式的物资记录列表数据
     * @author 伍菁 2019-04-16
     */
    public JSONObject findAllAssetCabinetRecordList(Integer page,Integer limit,String cas){
        List<AssetCabinetRecordDTO> assetCabinetRecordDTOList = new ArrayList<>();
        //查询语句
        String sql ="SELECT \n" +
                "r.asset_id,\n" +
                "a.cas,\n" +
                "a.ch_name,\n" +
                "c.cname,\n" +
                "a.unit,\n" +
                "a.specifications,\n" +
                "s.sum\n" +
                "FROM \n" +
                "asset_cabinet_record AS r\n" +
                "INNER JOIN\n" +
                "asset AS a ON a.id = r.asset_id\n" +
                "LEFT JOIN\n" +
                "asset_classification AS c ON c.id = a.category\n" +
                "INNER JOIN\n" +
                "(SELECT asset_id,SUM(stock_number) AS sum\n" +
                "FROM asset_cabinet_record  \n" +
                "GROUP BY asset_id) \n" +
                "AS s ON s.asset_id = r.asset_id";
        if(cas!=null&&!"".equals(cas)){
            sql += "\nwhere (a.cas LIKE '%"+cas+"%' or a.ch_name like '%"+cas+"%' or c.cname like '%"+cas+"%')";
        }
        sql += "\nGROUP BY r.asset_id";
        Query query=entityManager.createNativeQuery(sql);
        int totalRecords=query.getResultList().size();
        //分页
        query.setMaxResults(limit);
        query.setFirstResult((page-1)*limit);
        List<Object[]> assetCabinetRecordList=query.getResultList();
        for(Object[] o:assetCabinetRecordList){
            AssetCabinetRecordDTO assetCabinetRecordDTO = new AssetCabinetRecordDTO();
            assetCabinetRecordDTO.setAssetId(o[0]!=null?o[0].toString():null);
            assetCabinetRecordDTO.setCas(o[1]!=null?o[1].toString():null);
            assetCabinetRecordDTO.setCname(o[2]!=null?o[2].toString():null);
            assetCabinetRecordDTO.setCategoryCname(o[3]!=null?o[3].toString():null);
            assetCabinetRecordDTO.setUnit(o[4]!=null?o[4].toString():null);
            assetCabinetRecordDTO.setSpecifications(o[5]!=null?o[5].toString():null);
            assetCabinetRecordDTO.setSum(o[6]!=null?o[6].toString():null);
            assetCabinetRecordDTOList.add(assetCabinetRecordDTO);
        }
        JSONObject jsonObject=this.getJSON(assetCabinetRecordDTOList,totalRecords);
        return   jsonObject;
    }
    /**
     * 根据assetId获物资记录数据
     * @param assetId 物资记录物资编号
     * @return json字符串格式的物资记录详情数据
     * @author 伍菁 2019-04-18
     */
    public AssetCabinetRecordDTO findAssetCabinetRecordDetailsByAssetId(Integer assetId){
        AssetCabinetRecordDTO assetCabinetRecordDTO = new AssetCabinetRecordDTO();
        String sql = "SELECT\n" +
                "r.asset_id,\n" +
                "a.ch_name,\n" +
                "a.cas,\n" +
                "c.cname,\n" +
                "a.unit,\n" +
                "a.specifications,\n" +
                "a.price,\n" +
                "a.factory,\n" +
                "a.`function`,\n" +
                "r.id,\n" +
                "cab.cabinet_code,\n" +
                "cab.cabinet_name,\n" +
                "r.stock_number,\n" +
                "r.cabinet_id\n" +
                "FROM\n" +
                "asset_cabinet_record r\n" +
                "INNER JOIN\n" +
                "asset a ON a.id = r.asset_id\n" +
                "INNER JOIN\n" +
                "asset_classification c ON c.id = a.category\n" +
                "LEFT JOIN\n" +
                "asset_cabinet cab on cab.id = r.cabinet_id\n" +
                "WHERE r.asset_id = "+assetId+"";
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> resultList = query.getResultList();
        List<AssetCabinetRecordDTO> assetCabinetRecordDTOListItem = new ArrayList<>();
        for(Object[] o:resultList){
            AssetCabinetRecordDTO assetCabinetRecordDTO1 = new AssetCabinetRecordDTO();
            assetCabinetRecordDTO1.setId(o[9]!=null?o[9].toString():null);
            assetCabinetRecordDTO1.setCabinetCode(o[10]!=null?o[10].toString():null);
            assetCabinetRecordDTO1.setCabinetName(o[11]!=null?o[11].toString():null);
            assetCabinetRecordDTO1.setStockNumber(o[12]!=null?o[12].toString():null);
            assetCabinetRecordDTO1.setCabinetId(o[13]!=null?o[13].toString():null);
            assetCabinetRecordDTOListItem.add(assetCabinetRecordDTO1);
        }

        Object[] x = resultList.get(0);
        assetCabinetRecordDTO.setAssetId(x[0]!=null?x[0].toString():null);
        assetCabinetRecordDTO.setCname(x[1]!=null?x[1].toString():null);
        assetCabinetRecordDTO.setCas(x[2]!=null?x[2].toString():null);
        assetCabinetRecordDTO.setCategoryCname(x[3]!=null?x[3].toString():null);
        assetCabinetRecordDTO.setUnit(x[4]!=null?x[4].toString():null);
        assetCabinetRecordDTO.setSpecifications(x[5]!=null?x[5].toString():null);
        assetCabinetRecordDTO.setPrice(x[6]!=null?Float.valueOf(x[6].toString()):null);
        assetCabinetRecordDTO.setFactory(x[7]!=null?x[7].toString():null);
        assetCabinetRecordDTO.setFunction(x[8]!=null?x[8].toString():null);
        assetCabinetRecordDTO.setAssetCabinetRecordDTOList(assetCabinetRecordDTOListItem);

        return assetCabinetRecordDTO;
    }
    /**
     * Description 获取全部物品柜
     * @return AssetsCabinetDTO 物品柜DTO
     * @author 伍菁 2019-04-22
     **/
    public List<AssetsCabinetDTO> getAllAssetsCabinet(){
        List<AssetsCabinetDTO> assetsCabinetDTOList = new ArrayList<>();
        Set<AssetCabinet> assetCabinetList = assetCabinetDAO.findAllAssetCabinets();
        for(AssetCabinet a:assetCabinetList){
            AssetsCabinetDTO assetsCabinetDTO = new AssetsCabinetDTO();
            assetsCabinetDTO.setId(a.getId());
            assetsCabinetDTO.setCabinetName(a.getCabinetName()!=null?a.getCabinetName():null);
            assetsCabinetDTO.setCapacity(a.getCapacity()!=null?String.valueOf(a.getCapacity()):null);
            assetsCabinetDTOList.add(assetsCabinetDTO);
        }
        return assetsCabinetDTOList;
    }
    /**
     * Description 保存物资记录
     * @param  assetCabinetRecordDTO 参数封装DTO
     * @return 保存成功-true，失败-false
     * @author 伍菁 2019-04-22
     **/
    public boolean saveAssetsCabinetRecord(AssetCabinetRecordDTO assetCabinetRecordDTO,Integer id){
        boolean flag=true;
        try{
            AssetCabinetRecord assetCabinetRecord = new AssetCabinetRecord();
            //编辑
            if(id!=null){
                assetCabinetRecord = assetCabinetRecordDAO.findAssetCabinetRecordById(id);
                assetCabinetRecord.setAssetId(Integer.valueOf(assetCabinetRecordDTO.getAssetId()));
                assetCabinetRecord.setCabinetId(Integer.valueOf(assetCabinetRecordDTO.getCabinetId()));
                assetCabinetRecord.setStockNumber(Integer.valueOf(assetCabinetRecordDTO.getStockNumber()));
                assetCabinetRecord = assetCabinetRecordDAO.store(assetCabinetRecord);
            }else{//增加
                Integer assetId = Integer.valueOf(assetCabinetRecordDTO.getAssetId());
                Integer cabinetId = Integer.valueOf(assetCabinetRecordDTO.getCabinetId());
                String sql = "select acr from AssetCabinetRecord acr where acr.assetId = " + assetId +" and acr.cabinetId="+cabinetId+"";
                if(assetCabinetRecordDAO.executeQuery(sql).size()!=0) {//已存在数据
                    AssetCabinetRecord assetCabinetRecordTest = assetCabinetRecordDAO.executeQuery(sql).get(0);
                    Integer quantityTest = assetCabinetRecordTest.getStockNumber();
                    if(quantityTest==null){
                        quantityTest = 0;
                    }
                    assetCabinetRecordTest.setStockNumber(Integer.valueOf(assetCabinetRecordDTO.getStockNumber())+quantityTest);
                    assetCabinetRecord = assetCabinetRecordTest;
                }else{//新增数据
                    assetCabinetRecord.setAssetId(Integer.valueOf(assetCabinetRecordDTO.getAssetId()));
                    assetCabinetRecord.setCabinetId(Integer.valueOf(assetCabinetRecordDTO.getCabinetId()));
                    assetCabinetRecord.setStockNumber(Integer.valueOf(assetCabinetRecordDTO.getStockNumber()));
                }
                assetCabinetRecord = assetCabinetRecordDAO.store(assetCabinetRecord);
            }
        }catch (Exception e){
            flag=false;
        }
        return flag;
    }
    /**
     * Description 删除物资记录详情
     * @param id 物资记录id
     * @return 成功-"success"，失败-"fail"
     * @author 伍菁 2019-4-22
     */
    @Override
    public boolean deleteAssetCabinetRecordDetail(Integer id) {
        boolean flag=true;
        try {
            AssetCabinetRecord assetCabinetRecord=assetCabinetRecordDAO.findAssetCabinetRecordById(id);
            assetCabinetRecordDAO.remove(assetCabinetRecord);
        }catch (Exception e){
            flag=false;
        }
        return flag;
    }
    /**
     * Description 删除物资记录
     * @param assetId 物资编号
     * @return 成功-"success"，失败-"fail"
     * @author 伍菁 2019-4-22
     */
    @Override
    public boolean deleteAssetCabinetRecords(Integer assetId) {
        boolean flag=true;
        try {
            Set<AssetCabinetRecord> assetCabinetRecordList=assetCabinetRecordDAO.findAssetCabinetRecordsByAssetId(assetId);
            for(AssetCabinetRecord a:assetCabinetRecordList){
                assetCabinetRecordDAO.remove(a);
            }
        }catch (Exception e){
            flag=false;
        }
        return flag;
    }
    /**
     * 根据username获取实验中心主任所管中心的集合
     *
     * @author 吴奇臻 2019-5-10
     */
    public List<Integer> getCenterIDListFormUsername(String username){
        String sql="select id from lab_center lc where lc.center_manager='"+username+"'";
        Query query=entityManager.createNativeQuery(sql);
        List<Integer> objects=query.getResultList();
        return objects;
    }
    /**
     * Description 获取入库单后三位编号
     * @author 吴奇臻 2019-5-21
     */
    public String getAssetsRelatedAppNo(String type){
        String sql="";
        if(type.equals("Apply")) {
            sql = "select count(*) from asset_app aa where DATE_FORMAT(NOW(), '%Y-%m-%d') = DATE_FORMAT(aa.app_date, '%Y-%m-%d')";
        }else if(type.equals("InStorage")){
            sql = "select count(*) from asset_storage ass where DATE_FORMAT(NOW(), '%Y-%m-%d') = DATE_FORMAT(ass.date, '%Y-%m-%d')";
        }else if(type.equals("Receive")){
            sql = "select count(*) from asset_receive ar where DATE_FORMAT(NOW(), '%Y-%m-%d') = DATE_FORMAT(ar.receive_date, '%Y-%m-%d')";
        }
        Query query=entityManager.createNativeQuery(sql);
        Integer no=Integer.parseInt(query.getSingleResult().toString());
        String appNo="";
        if(no<10){
            appNo="00"+no;
        }else if (no<100){
            appNo="0"+no;
        }else{
            appNo=no.toString();
        }
        return appNo;
    }
    /**
     * Description 审核拒绝返回申领的数量
     * @author 吴奇臻 2019-5-22
     */
    public void returnAssetsReceiveItemAmount(Integer receiveId){
        String sql="SELECT\n" +
                "\tarr.quantity,\n" +
                "\tarr.cabinet_id,\n" +
                "\tarr.asset_id\n" +
                "FROM\n" +
                "\tasset_receive_record arr\n" +
                "LEFT JOIN asset_receive ar ON arr.receive_id = ar.id\n" +
                "WHERE 1=1 AND ar.id = "+receiveId;
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]>objects=query.getResultList();
        for(int i=0;i<objects.size();i++){
            AssetCabinetRecord assetCabinetRecord=this.findAssetsCabinetRecordByCabinetAndAssets(Integer.parseInt(objects.get(i)[1].toString()),Integer.parseInt(objects.get(i)[2].toString()));
            //原数据库表定义类型为decimal,先做截取处理
            String quantity=objects.get(i)[0].toString();
            String quantity1=quantity.substring(0,quantity.length()-3);
            assetCabinetRecord.setStockNumber(assetCabinetRecord.getStockNumber()+Integer.parseInt(quantity1));
            assetCabinetRecordDAO.store(assetCabinetRecord);
        }
    }
    /**
     * Description 根据申领编号获取申领审核人
     * @author 吴奇臻 2019-6-04
     */
    public List<Object[]> getAuditLevelUser(Integer receiveId){
        String sql="SELECT\n" +
                "\tua.user_id,\n" +
                "  u.cname,\n" +
                "  sa.academy_name\n" +
                "FROM\n" +
                "\tasset_receive ar\n" +
                "LEFT JOIN authority a ON ar.cur_audit_level = a.authority_name\n" +
                "LEFT JOIN user_authority ua on ua.authority_id=a.id\n" +
                "LEFT JOIN user u on u.username=ar.app_user\n" +
                "LEFT JOIN school_academy sa on sa.academy_number=u.academy_number\n" +
                "where ar.id="+receiveId;
        Query query=entityManager.createNativeQuery(sql);
        List<Object[]> auditUser=query.getResultList();
        return auditUser;
    }
    /**
     * Description 生成物资申领的通知消息
     * @author 吴奇臻 2019-6-04
     */
    public void setMessageInfoFroAssetsReceive(Integer receiveId){
        List<Object[]> auditUser=this.getAuditLevelUser(receiveId);
        for(Object[] o:auditUser) {
            Message message = new Message();
            message.setTitle("物资申领提醒");
            Calendar calendar = Calendar.getInstance();
            message.setCreateTime(calendar);
            message.setMessageState(0);
            message.setTage(1);
            message.setUsername(o[0]!=null?o[0].toString():null);
            message.setSendUser(o[1]!=null?o[1].toString():null);
            message.setSendCparty(o[2]!=null?o[2].toString():null);
            message.setContent("<a onclick='changeMessage(this)' href='../lims/api/material/checkAssetsReceiveDetailAPI?id="+receiveId+"'>查看</a>");
            messageDAO.store(message);
        }
    }
}
