package net.gvsun.lims.service.DeviceRepair;

import api.net.gvsunlims.constant.ConstantInterface;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.common.BaseActionAuthDTO;
import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.common.PConfigDTO;
import net.gvsun.lims.dto.deviceRepair.DeviceRepairApplyDTO;
import net.gvsun.lims.dto.deviceRepair.SchoolDeviceRepairDTO;
import net.gvsun.lims.service.auditServer.AuditService;
import net.gvsun.lims.service.user.UserActionService;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("DeviceRepairService")
public class DeviceRepairServiceImpl implements DeviceRepairService {

    @Autowired
    private SchoolDeviceDAO schoolDeviceDAO;

    @Autowired
    private LabRoomDAO labRoomDAO;

    @Autowired
    private DeviceRepairDAO deviceRepairDAO;

    @Autowired
    private ShareService shareService;

    @Autowired
    private AuditService auditService;

    @Autowired
    private UserActionService userActionService;

    @Autowired
    private LabRoomDeviceDAO labRoomDeviceDAO;

    @Autowired
    private MessageDAO messageDAO;

    @Autowired
    private UserDAO userDAO;

    /**
     * Description 设备维修信息显示
     * @param offset 起始位置
     * @param limit 限制每页显示个数
     * @param search 查询条件
     * @param sort 排序条件
     * @param order 正序或倒序
     * @param request 请求
     * @return 设备维修信息
     * @author 黄保钱 2018-9-29
     */
    @Override
    public BaseDTO getStandardDeviceListBySelect(String labRoom,Integer deviceId, String search, Integer offset, Integer limit, String sort, String order, HttpServletRequest request) {
        //获取列表主查询语句
//        StringBuffer sql = new StringBuffer("select distinct c from SchoolDevice c");
        StringBuffer sql = new StringBuffer("select distinct lrds from SchoolDevice c");
        sql.append(" join c.labRoomDevices lrds ");
        sql.append(" where 1=1 ");
        //查询条件
        String academyId = (String) request.getSession().getAttribute("selected_academy");
        if (academyId != null && !"".equals(academyId) && !"-1".equals(academyId)) {
            sql.append(" and lrds.labRoom.labCenter.schoolAcademy.academyNumber like '").append(academyId).append("' ");
        }
        if(labRoom != null && !labRoom.equals("")){
            sql.append(" and lrds.labRoom.id=").append(labRoom);
        }
        if (search != null && !"".equals(search)) {
            sql.append(" and (c.deviceName like '%").append(search).append("%' or");
            sql.append(" c.deviceNumber like '%").append(search).append("%' or");
            sql.append(" c.devicePattern like '%").append(search).append("%' )");
        }
//        sql.append(" and c.deviceStatus = 1 and c.CDictionaryByUseStatus.CNumber = 1 and c.CDictionaryByUseStatus.CCategory = 'c_lab_room_device_status'");
        sql.append(" and lrds.CDictionaryByDeviceStatus.CNumber = 1 and lrds.CDictionaryByDeviceStatus.CCategory = 'c_lab_room_device_status'");

        //排序
        sql.append(" order by ");
        if(deviceId != null) {
            sql.append(" CASE WHEN lrds.id = ").append(deviceId).append(" THEN 0 ELSE 1 END, ");
        }
        sql.append(" c.").append(sort).append(" ").append(order);
        // 执行sb语句
//        List<SchoolDevice> devices = schoolDeviceDAO.executeQuery(sql.toString(),offset, limit);
        List<LabRoomDevice> devices = labRoomDeviceDAO.executeQuery(sql.toString(),offset, limit);
        // 获取总记录
        sql = new StringBuffer("select count(distinct lrds) from SchoolDevice c" );
        sql.append(" join c.labRoomDevices lrds ");
        sql.append(" where 1=1 " );
        //查询条件
        if (academyId != null && !"".equals(academyId) && !"-1".equals(academyId)) {
            sql.append(" and lrds.labRoom.labCenter.schoolAcademy.academyNumber like '").append(academyId).append("' ");
        }
        if(labRoom != null && !labRoom.equals("")){
            sql.append(" and lrds.labRoom.id=").append(labRoom);
        }
        if (search != null && !"".equals(search)) {
            sql.append(" and (c.deviceName like '%").append(search).append("%' or");
            sql.append(" c.deviceNumber like '%").append(search).append("%' or");
            sql.append(" c.devicePattern like '%").append(search).append("%' )");
        }
//        sql.append(" and c.deviceStatus = 1 and c.CDictionaryByUseStatus.CNumber = 1 and c.CDictionaryByUseStatus.CCategory = 'c_lab_room_device_status'");
        sql.append(" and lrds.CDictionaryByDeviceStatus.CNumber = 1 and lrds.CDictionaryByDeviceStatus.CCategory = 'c_lab_room_device_status'");
        int total = ((Long) labRoomDeviceDAO.createQuerySingleResult(sql.toString()).getSingleResult()).intValue();
        //封装VO
        List<SchoolDeviceRepairDTO> deviceRepairDTOs = new ArrayList<>();
//        for(SchoolDevice device:devices){
        for(LabRoomDevice device:devices){
            SchoolDeviceRepairDTO schoolDeviceRepairDTO = new SchoolDeviceRepairDTO();
            //设置主键
            schoolDeviceRepairDTO.setId(device.getId());
            //设置设备编号
            String deviceNumber =null;
            if(Objects.nonNull(device.getSchoolDevice().getDeviceNumber())){
                deviceNumber = device.getSchoolDevice().getDeviceNumber();
            }
            //设置设备名称
            String deviceName =null;
            if(Objects.nonNull(device.getSchoolDevice().getDeviceName())){
                deviceName = device.getSchoolDevice().getDeviceName();
            }
            //设置设备名称
            String devicePattern =null;
            if(Objects.nonNull(device.getSchoolDevice().getDevicePattern())){
                devicePattern = device.getSchoolDevice().getDevicePattern();
            }
            //设置设备名称
            BigDecimal devicePrice =null;
            if(Objects.nonNull(device.getSchoolDevice().getDevicePrice())){
                devicePrice = device.getSchoolDevice().getDevicePrice();
            }
            // 设置实验室名称
            Integer labAddress = null;
            if(Objects.nonNull(device.getLabRoom().getId())){
                labAddress = device.getLabRoom().getId();
            }
            // 数据置入VO
            schoolDeviceRepairDTO.setDeviceNumber(deviceNumber);
            schoolDeviceRepairDTO.setDeviceName(deviceName);
            schoolDeviceRepairDTO.setDevicePattern(devicePattern);
            schoolDeviceRepairDTO.setDevicePrice(devicePrice);
            schoolDeviceRepairDTO.setLabAddress(labAddress);
            deviceRepairDTOs.add(schoolDeviceRepairDTO);
        }
        BaseDTO baseDTO = new BaseDTO();
        baseDTO.setRows(deviceRepairDTOs);
        baseDTO.setTotal(total);
        return baseDTO;
    }

    /**
     * Description 获得所进学院的实验分室
     * @param acno 学院编号
     * @return 实验分室列表
     * @author 黄保钱 2018-10-9
     */
    @Override
    public List<LabRoom> getLabRoomByAcno(String acno){
        String sql = "select l from LabRoom l where 1=1 ";
        if(acno != null && !"".equals(acno) && !"-1".equals(acno)){
            sql += " and l.labCenter.schoolAcademy.academyNumber = '" + acno + "'";
        }
        return labRoomDAO.executeQuery(sql);
    }

    /**
     * Description 保存设备维修申请
     * @param labRoomName 实验室id
     * @param deviceName 设备id
     * @param memo 维修描述
     * @param id 设备维修id（编辑时需要）
     * @author 黄保钱 2018-10-9
     */
    @Override
    public void saveDeviceRepairApply(String labRoomName, String deviceName, String memo, Integer id, String reportUser, BigDecimal expectAmount){
        String[] deviceType = deviceName.split("_");
        // 初始化设备维修类型为物资
        int type = 2;
        String deviceNumber = null;
        if(deviceType[1].equals("1")){
            // 类型为设备时更新设备
            SchoolDevice schoolDevice = schoolDeviceDAO.findSchoolDeviceByPrimaryKey(deviceType[0]);
            deviceName = schoolDevice.getDeviceName();
            deviceNumber = schoolDevice.getDeviceNumber();
            schoolDeviceDAO.store(schoolDevice);
            type = 1;
        }else{
            // 类型为物资
            deviceName = deviceType[0];
        }
        String[] labRoomType = labRoomName.split("_");
        if(labRoomType[1].equals("1")){
            // 实验室已在数据库
            LabRoom labRoom = labRoomDAO.findLabRoomById(Integer.parseInt(labRoomType[0]));
            labRoomName = labRoom.getLabRoomName();
        }else{
            // 用户自定义数据库
            labRoomName = labRoomType[0];
        }
        // 保存设备维修信息
        DeviceRepair deviceRepair = new DeviceRepair();
        if(id != null){
            deviceRepair.setId(id);
        }
        deviceRepair.setDeviceName(deviceName);
        if(deviceNumber != null){
            deviceRepair.setDeviceNumber(deviceNumber);
        }
        deviceRepair.setLabAddress(labRoomName);
        deviceRepair.setType(type);
        deviceRepair.setCreateDate(new GregorianCalendar());
        deviceRepair.setContent(memo);
        User user = shareService.getUserDetail();
        deviceRepair.setCreater(user.getUsername());
        deviceRepair.setReportUser(reportUser);
        deviceRepair.setExpectAmount(expectAmount);
        // 设备维修状态置为未提交
        deviceRepair.setAuditStage(0);
        deviceRepairDAO.store(deviceRepair);
    }

    /**
     * Description 获取设备维修申请单列表（我的申请）
     * @param offset 起始位置
     * @param limit 限制每页显示个数
     * @param search 查询条件
     * @param sort 排序条件
     * @param order 正序或倒序
     * @param request 请求
     * @return 申请单显示列表
     * @author 黄保钱 2018-10-9
     */
    @Override
    public BaseDTO getMyDeviceRepairListBySelect(String search, Integer offset, Integer limit, String sort, String order, HttpServletRequest request) {
        // 查找设备维修sql语句
        StringBuilder sql = new StringBuilder("select dr from DeviceRepair dr where 1=1 ");

        searchForDeviceRepair(search, sql);
        User user = shareService.getUserDetail();
        sql.append(" and dr.creater = '").append(user.getUsername()).append("'");
        //排序
        sql.append(" order by dr.").append(sort).append(" ").append(order);
        List<DeviceRepair> deviceRepairs = deviceRepairDAO.executeQuery(sql.toString(), offset, limit);

        // 查找设备维修数量sql语句
        sql = new StringBuilder("select count(dr) from DeviceRepair dr where 1=1 ");

        searchForDeviceRepair(search, sql);
        sql.append(" and dr.creater = '").append(user.getUsername()).append("'");
        int totalRecords = ((Long) schoolDeviceDAO.createQuerySingleResult(sql.toString()).getSingleResult()).intValue();

        // 封装VO
        List<DeviceRepairApplyDTO> deviceRepairApplyDTOS = new ArrayList<>();
        for(DeviceRepair deviceRepair: deviceRepairs){
            DeviceRepairApplyDTO dto = new DeviceRepairApplyDTO();
            // 设置主键
            dto.setId(deviceRepair.getId());
            // 设置设备的数据
            String deviceNumber =null;
            if(Objects.nonNull(deviceRepair.getDeviceNumber())){
                deviceNumber = deviceRepair.getDeviceNumber();
            }
            String deviceName =null;
            if(Objects.nonNull(deviceRepair.getDeviceName())){
                deviceName = deviceRepair.getDeviceName();
            }
            String labAddress =null;
            if(Objects.nonNull(deviceRepair.getLabAddress())){
                labAddress = deviceRepair.getLabAddress();
            }
            String creator =null;
                User creatorUser = shareService.findUserByUsername(deviceRepair.getCreater());
            if(Objects.nonNull(creatorUser)){
                creator = creatorUser.getCname();
            }
            Calendar createDate =null;
            if(Objects.nonNull(deviceRepair.getCreateDate())){
                createDate = deviceRepair.getCreateDate();
            }
            String content =null;
            if(Objects.nonNull(deviceRepair.getContent())){
                content = deviceRepair.getContent();
            }
            Integer status = null;
            if(Objects.nonNull(deviceRepair.getAuditStage())){
                status = deviceRepair.getAuditStage();
            }
            // 报修人
            String reportUsername =null;
            User reportUser = shareService.findUserByUsername(deviceRepair.getReportUser());
            if(Objects.nonNull(reportUser)){
                reportUsername = reportUser.getCname();
            }
            // 预计金额
            String expectAmountStr =null;
            if(Objects.nonNull(deviceRepair.getExpectAmount())){
                expectAmountStr = deviceRepair.getExpectAmount().toString();
            }
            // 设备价格
            String devicePrice = null;
            if(deviceRepair.getType() == 1){
                LabRoomDevice lrd = getLabRoomDevice(deviceRepair.getDeviceNumber(), deviceRepair.getLabAddress());
                if(Objects.nonNull(lrd) && Objects.nonNull(lrd.getSchoolDevice().getDevicePrice())){
                    devicePrice = lrd.getSchoolDevice().getDevicePrice().toString();
                }
            }
            // 设置权限
            BaseActionAuthDTO baseActionAuthDTO = userActionService.getBaseActionAuthDTO(
                    ConstantInterface.FUNCTION_MODEL_ACTION_DEVICEREPAIR, "noSubmit", user.getUsername());
            // 数据置入VO
            dto.setBaseActionAuthDTO(baseActionAuthDTO);
            dto.setContent(content);
            dto.setCreateDate(createDate == null ? null : (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(createDate.getTime()));
            dto.setCreator(creator);
            dto.setDeviceName(deviceName);
            dto.setDeviceNumber(deviceNumber);
            dto.setLabAddress(labAddress);
            dto.setStatus(status);
            dto.setReportUser(reportUsername);
            dto.setExpectAmount(expectAmountStr);
            dto.setDevicePrice(devicePrice);
            deviceRepairApplyDTOS.add(dto);
        }
        BaseDTO baseDTO = new BaseDTO();
        baseDTO.setRows(deviceRepairApplyDTOS);
        baseDTO.setTotal(totalRecords);
        return baseDTO;
    }

    /**
     * Description 提交设备维修申请单
     * @param id 申请单id
     * @return 提交成功与否字符串
     * @author 黄保钱 2018-10-9
     */
    @Override
    public String submitDeviceRepair(Integer id, String acno){
        DeviceRepair deviceRepair = deviceRepairDAO.findDeviceRepairById(id);
        deviceRepair.setAuditStage(1);
        // 若为物资则对象id置为-1
        String objectId = "-1";
        String businessType = "DeviceRepair" + acno;
        businessType = getBusinessLevels(businessType);
        if(deviceRepair.getDeviceNumber() != null) {
//            SchoolDevice schoolDevice = schoolDeviceDAO.findSchoolDeviceByDeviceNumber(deviceRepair.getDeviceNumber());
            LabRoomDevice labRoomDevice = getLabRoomDevice(deviceRepair.getDeviceNumber(), deviceRepair.getLabAddress());
            CDictionary cDictionary = shareService.getCDictionaryByCategory("c_lab_room_device_status", "2");
            labRoomDevice.setCDictionaryByDeviceStatus(cDictionary);
            labRoomDeviceDAO.store(labRoomDevice);
        }
        String s;
        String s1;
        try {
            s = auditService.saveInitBusinessAudit("-1", businessType, id.toString());
            JSONObject jsonObject = JSONObject.parseObject(s);
            if(!"success".equals(jsonObject.getString("status"))){
                return "fail";
            }
            s1 = auditService.getCurrAudit(deviceRepair.getId().toString(), businessType);
            JSONObject j1 = JSONObject.parseArray(JSONObject.parseObject(s1).getString("data")).getJSONObject(0);
            if(j1 != null && "pass".equals(j1.getString("result"))){
                deviceRepair.setAuditStage(2);
                sendDeviceRepairMessage(shareService.getUserDetail().getUsername(), id, "设备维修申请通过", 1);
            }else if(j1 != null && !"fail".equals(j1.getString("result"))){
                List<User> users = findUsersByAuthorityNameAndAcademy(j1.getString("result"), acno);
                for(User u: users){
                    sendDeviceRepairMessage(u.getUsername(), id, "设备维修增加", 2);
                }
                sendDeviceRepairMessage(shareService.getUserDetail().getUsername(), id, "设备维修增加，请等待审核", 1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        deviceRepairDAO.store(deviceRepair);
        return "success";
    }

    /**
     * Description 获取设备维修申请单列表（我的审核）
     * @param auditStage 审核状态
     * @param offset 起始位置
     * @param limit 限制每页显示个数
     * @param search 查询条件
     * @param sort 排序条件
     * @param order 正序或倒序
     * @param request 请求
     * @return 申请单显示列表
     * @author 黄保钱 2018-10-9
     */
    @Override
    public BaseDTO getDeviceRepairCheckListBySelect(Integer auditStage, String search, Integer offset, Integer limit, String sort, String order, HttpServletRequest request, String acno){

        // sql查询语句
        StringBuilder sql = new StringBuilder("select distinct dr from DeviceRepair dr, LabRoomDevice lrd, User u where 1=1 and (dr.type = 2 or (dr.deviceNumber = lrd.schoolDevice.deviceNumber and dr.labAddress = lrd.labRoom.labRoomName)) and u.username=dr.creater ");

        // 条件
        StringBuilder condition = new StringBuilder();
        searchForDeviceRepair(search, condition);
        condition.append(" and dr.auditStage = ").append(auditStage);
        // 实践副院长仅显示本中心的
        if(request.getSession().getAttribute("selected_role").equals("ROLE_PREEXTEACHING")) {
            condition.append(" and (( lrd.labRoom.labCenter.schoolAcademy.academyNumber = '").append(shareService.getUserDetail().getSchoolAcademy().getAcademyNumber()).append("' and dr.type = 1)");
            condition.append(" or (u.schoolAcademy.academyNumber = '").append(shareService.getUserDetail().getSchoolAcademy().getAcademyNumber()).append("' and dr.type = 2))");
        }

        // 非校级学院查看本学院
        if(shareService.getUserDetail().getSchoolAcademy() != null && !"1".equals(shareService.getUserDetail().getSchoolAcademy().getAcademyType())){
            sql.append(" and dr.creater = u.username and u.schoolAcademy.academyNumber = '").append(shareService.getUserDetail().getSchoolAcademy().getAcademyNumber()).append("'");
        }

        //排序
        sql.append(condition);
        sql.append(" order by dr.").append(sort).append(" ").append(order);
        List<DeviceRepair> deviceRepairs = deviceRepairDAO.executeQuery(sql.toString(), offset, limit);

        // 查找设备维修数量sql语句
        sql = new StringBuilder("select count(distinct dr) from DeviceRepair dr, LabRoomDevice lrd, User u where 1=1 and (dr.type = 2 or (dr.deviceNumber = lrd.schoolDevice.deviceNumber and dr.labAddress = lrd.labRoom.labRoomName)) and u.username=dr.creater ");

        sql.append(condition);

        // 非校级学院查看本学院
        if(shareService.getUserDetail().getSchoolAcademy() != null && !"1".equals(shareService.getUserDetail().getSchoolAcademy().getAcademyType())){
            sql.append(" and dr.creater = u.username and u.schoolAcademy.academyNumber = '").append(shareService.getUserDetail().getSchoolAcademy().getAcademyNumber()).append("'");
        }

        int totalRecords = ((Long) schoolDeviceDAO.createQuerySingleResult(sql.toString()).getSingleResult()).intValue();

        // 封装VO
        List<DeviceRepairApplyDTO> deviceRepairApplyDTOS = new ArrayList<>();
        for(DeviceRepair deviceRepair: deviceRepairs){
            DeviceRepairApplyDTO dto = new DeviceRepairApplyDTO();
            // 设置主键
            dto.setId(deviceRepair.getId());
            // 设置设备的数据
            String deviceNumber =null;
            if(Objects.nonNull(deviceRepair.getDeviceNumber())){
                deviceNumber = deviceRepair.getDeviceNumber();
            }
            String deviceName =null;
            if(Objects.nonNull(deviceRepair.getDeviceName())){
                deviceName = deviceRepair.getDeviceName();
            }
            String labAddress =null;
            if(Objects.nonNull(deviceRepair.getLabAddress())){
                labAddress = deviceRepair.getLabAddress();
            }
            String creator =null;
                User creatorUser = shareService.findUserByUsername(deviceRepair.getCreater());
            if(Objects.nonNull(creatorUser)){
//                creator = deviceRepair.getCreater();
                creator = creatorUser.getCname();
            }
            String createDate =null;
            if(Objects.nonNull(deviceRepair.getCreateDate())){
                createDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(deviceRepair.getCreateDate().getTime());
            }
            String content =null;
            if(Objects.nonNull(deviceRepair.getContent())){
                content = deviceRepair.getContent();
            }
            Integer status = null;
            if(Objects.nonNull(deviceRepair.getAuditStage())){
                status = deviceRepair.getAuditStage();
            }
            // 报修人
            String reportUsername =null;
            User reportUser = shareService.findUserByUsername(deviceRepair.getReportUser());
            if(Objects.nonNull(reportUser)){
                reportUsername = reportUser.getCname();
            }
            // 预计金额
            String expectAmountStr =null;
            if(Objects.nonNull(deviceRepair.getExpectAmount())){
                expectAmountStr = deviceRepair.getExpectAmount().toString();
            }
            // 设备价格
            String devicePrice = null;
            if(deviceRepair.getType() == 1){
                LabRoomDevice lrd = getLabRoomDevice(deviceRepair.getDeviceNumber(), deviceRepair.getLabAddress());
                if(Objects.nonNull(lrd) && Objects.nonNull(lrd.getSchoolDevice().getDevicePrice())){
                    devicePrice = lrd.getSchoolDevice().getDevicePrice().toString();
                }
            }
            String s;
            JSONObject j = null;
            StringBuilder authNames = new StringBuilder();
            User createUser = shareService.findUserByUsername(deviceRepair.getCreater());
            try {
                if(deviceRepair.getAuditStage() == 1){
                    // 审核的权限名
                    String businessType = "DeviceRepair" + createUser.getSchoolAcademy().getAcademyNumber();
                    businessType = getBusinessLevels(businessType);
                    s = auditService.getCurrAudit(deviceRepair.getId().toString(), businessType);
                    j = JSONObject.parseArray(JSONObject.parseObject(s).getString("data")).getJSONObject(0);
                    if(j != null){
                        authNames.append(j.getString("result"));
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            // 设置权限
            BaseActionAuthDTO baseActionAuthDTO = userActionService.getBaseActionAuthDTO(
                    ConstantInterface.FUNCTION_MODEL_ACTION_DEVICEREPAIR, authNames.toString() + "_" + request.getSession().getAttribute("selected_role"),
                    "audit_" + ((createUser != null &&
                            request.getSession().getAttribute("selected_academy").equals(createUser.getSchoolAcademy().getAcademyNumber())) ||
                            "1".equals(shareService.getUserDetail().getSchoolAcademy().getAcademyType()) ? "sameAcademy" : ""));
            baseActionAuthDTO = getExportActionAuth(baseActionAuthDTO, request, deviceRepair, 1);
            // 将数据置入VO
            dto.setBaseActionAuthDTO(baseActionAuthDTO);
            dto.setContent(content);
            dto.setCreateDate(createDate);
            dto.setCreator(creator);
            dto.setDeviceName(deviceName);
            dto.setDeviceNumber(deviceNumber);
            dto.setLabAddress(labAddress);
            dto.setStatus(status);
            dto.setReportUser(reportUsername);
            dto.setExpectAmount(expectAmountStr);
            dto.setDevicePrice(devicePrice);
            deviceRepairApplyDTOS.add(dto);
        }
        BaseDTO baseDTO = new BaseDTO();
        baseDTO.setRows(deviceRepairApplyDTOS);
        baseDTO.setTotal(totalRecords);
        return baseDTO;
    }

    /**
     * Description 获取设备维修申请单列表（我的确认）
     * @param offset 起始位置
     * @param limit 限制每页显示个数
     * @param search 查询条件
     * @param sort 排序条件
     * @param order 正序或倒序
     * @param request 请求
     * @return 申请单显示列表
     * @author 黄保钱 2018-11-6
     */
    @Override
    public BaseDTO getDeviceRepairConfirmListBySelect(String search, Integer offset, Integer limit, String sort, String order, HttpServletRequest request){
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        // sql查询语句
        StringBuilder sql = new StringBuilder("select distinct dr from DeviceRepair dr, LabRoomDevice lrd, User u where 1=1 and (dr.type = 2 or (dr.deviceNumber = lrd.schoolDevice.deviceNumber and dr.labAddress = lrd.labRoom.labRoomName)) and u.username=dr.creater ");

        // 条件
        StringBuilder condition = new StringBuilder();
        searchForDeviceRepair(search, condition);
        condition.append(" and (dr.auditStage = 2 or dr.auditStage > 3)");
        // 实践副院长仅显示本中心的
        if(request.getSession().getAttribute("selected_role").equals("ROLE_PREEXTEACHING")) {
            condition.append(" and (( lrd.labRoom.labCenter.schoolAcademy.academyNumber = '").append(shareService.getUserDetail().getSchoolAcademy().getAcademyNumber()).append("' and dr.type = 1)");
            condition.append(" or (u.schoolAcademy.academyNumber = '").append(shareService.getUserDetail().getSchoolAcademy().getAcademyNumber()).append("' and dr.type = 2))");
        }
        if(request.getSession().getAttribute("selected_role").equals("ROLE_EXCENTERDIRECTOR")){
            condition.append(" and dr.creater = '").append(shareService.getUserDetail().getUsername()).append("' ");
        }
        //排序
        sql.append(condition);
        //时间
        String start = request.getParameter("starttime");
        String starttime = null;
        if(start != null && !"".equals(start)) {
            String[] stmp = start.split("-");
            starttime = stmp[0] + "-" + stmp[1];
            Integer st = Integer.parseInt(stmp[2]) - 1;
            if (st < 10) {
                starttime += "-0" + st.toString();
            } else {
                starttime += "-" + st.toString();
            }
        }
        String end = request.getParameter("endtime");
        String endtime = null;
        if(end != null && !"".equals(end)) {
            String[] etmp = end.split("-");
            endtime = etmp[0] + "-" + etmp[1];
            Integer et = Integer.parseInt(etmp[2]) + 1;
            if (et < 10) {
                endtime += "-0" + et.toString();
            } else {
                endtime += "-" + et.toString();
            }
        }
        if(starttime != null && !"".equals(starttime) && endtime != null && !"".equals(endtime)){
            sql.append(" and dr.createDate between '"+starttime +"' and '"+endtime+"' ");
        }else if(starttime != null && !"".equals(starttime) && (endtime == null ||"".equals(endtime))){
            sql.append(" and dr.createDate >= '"+starttime +"'");
        }else if((starttime != null || !"".equals(starttime)) && endtime != null && !"".equals(endtime)){
            sql.append(" and dr.createDate <= '"+endtime+"' ");
        }
        //排序
        sql.append(" order by dr.").append(sort).append(" ").append(order);
        List<DeviceRepair> deviceRepairs = deviceRepairDAO.executeQuery(sql.toString(), offset, limit);

        // 查找设备维修数量sql语句
        sql = new StringBuilder("select count(distinct dr) from DeviceRepair dr, LabRoomDevice lrd, User u where 1=1 and (dr.type = 2 or (dr.deviceNumber = lrd.schoolDevice.deviceNumber and dr.labAddress = lrd.labRoom.labRoomName)) and u.username=dr.creater ");

        sql.append(condition);
        if(starttime != null && !"".equals(starttime) && endtime != null && !"".equals(endtime)){
            sql.append(" and dr.createDate between '"+starttime +"' and '"+endtime+"' ");
        }else if(starttime != null && !"".equals(starttime) && (endtime == null ||"".equals(endtime))){
            sql.append(" and dr.createDate >= '"+starttime +"'");
        }else if((starttime != null || !"".equals(starttime)) && endtime != null && !"".equals(endtime)){
            sql.append(" and dr.createDate <= '"+endtime+"' ");
        }
        int totalRecords = ((Long) schoolDeviceDAO.createQuerySingleResult(sql.toString()).getSingleResult()).intValue();

        // 封装VO
        List<DeviceRepairApplyDTO> deviceRepairApplyDTOS = new ArrayList<>();
        for(DeviceRepair deviceRepair: deviceRepairs){
            DeviceRepairApplyDTO dto = new DeviceRepairApplyDTO();
            // 设置主键
            dto.setId(deviceRepair.getId());
            // 设置设备的数据
            String deviceNumber =null;
            if(Objects.nonNull(deviceRepair.getDeviceNumber())){
                deviceNumber = deviceRepair.getDeviceNumber();
            }
            String deviceName =null;
            if(Objects.nonNull(deviceRepair.getDeviceName())){
                deviceName = deviceRepair.getDeviceName();
            }
            String labAddress =null;
            if(Objects.nonNull(deviceRepair.getLabAddress())){
                labAddress = deviceRepair.getLabAddress();
            }
            String creator =null;
                User creatorUser = shareService.findUserByUsername(deviceRepair.getCreater());
            if(Objects.nonNull(creatorUser)){
//                creator = deviceRepair.getCreater();
                creator = creatorUser.getCname();
            }
            String createDate =null;
            if(Objects.nonNull(deviceRepair.getCreateDate())){
                createDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(deviceRepair.getCreateDate().getTime());
            }
            String content =null;
            if(Objects.nonNull(deviceRepair.getContent())){
                content = deviceRepair.getContent();
            }
            Integer status = null;
            if(Objects.nonNull(deviceRepair.getAuditStage())){
                status = deviceRepair.getAuditStage();
            }
            // 报修人
            String reportUsername =null;
            User reportUser = shareService.findUserByUsername(deviceRepair.getReportUser());
            if(Objects.nonNull(reportUser)){
                reportUsername = reportUser.getCname();
            }
            // 预计金额
            String expectAmountStr =null;
            if(Objects.nonNull(deviceRepair.getExpectAmount())){
                expectAmountStr = deviceRepair.getExpectAmount().toString();
            }
            // 设备价格
            String devicePrice = null;
            if(deviceRepair.getType() == 1){
                LabRoomDevice lrd = getLabRoomDevice(deviceRepair.getDeviceNumber(), deviceRepair.getLabAddress());
                if(Objects.nonNull(lrd) && Objects.nonNull(lrd.getSchoolDevice().getDevicePrice())){
                    devicePrice = lrd.getSchoolDevice().getDevicePrice().toString();
                }
            }
            String s;
            JSONObject j = null;
            StringBuilder authNames = new StringBuilder();
            try {
                String RepairAcceptance = "";
                String RepairWrite = "";
                String RepairRecord = "";
                Map<String, String> params = new HashMap<>();
                params.put("businessType", pConfigDTO.PROJECT_NAME + "RepairAcceptance");
                String res = HttpClientUtil.doPost(pConfigDTO.auditServerUrl+"audit/getBusinessAuditConfigLevel", params);
                JSONObject j1 = JSONObject.parseObject(res);
                RepairAcceptance = j1.getJSONArray("data").getJSONObject(0).getString("authId");
                params.put("businessType", pConfigDTO.PROJECT_NAME + "RepairWrite");
                res = HttpClientUtil.doPost(pConfigDTO.auditServerUrl+"audit/getBusinessAuditConfigLevel", params);
                j1 = JSONObject.parseObject(res);
                RepairWrite = j1.getJSONArray("data").getJSONObject(0).getString("authId");
                params.put("businessType", pConfigDTO.PROJECT_NAME + "RepairRecord");
                res = HttpClientUtil.doPost(pConfigDTO.auditServerUrl+"audit/getBusinessAuditConfigLevel", params);
                j1 = JSONObject.parseObject(res);
                RepairRecord = j1.getJSONArray("data").getJSONObject(0).getString("authId");
                if(deviceRepair.getAuditStage() == 2){
                    authNames.append(RepairAcceptance).append(",");
                }else if(deviceRepair.getAuditStage() == 4){
                    authNames.append(RepairWrite).append(",");
                }else if(deviceRepair.getAuditStage() == 6){
                    authNames.append(RepairRecord).append(",");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            String sameCenter = "sameCenter";
            if("ROLE_EXCENTERDIRECTOR".equals(request.getSession().getAttribute("selected_role"))) {
                Set<LabCenter> centers = shareService.getUserDetail().getLabCentersForCenterManager();
                if(deviceRepair.getType() == 2){
                    sameCenter = "noSameCenter";
                    for(LabCenter l: centers){
                        if(l.getUserByCenterManager().getUsername().equals(deviceRepair.getCreater())){
                            sameCenter = "sameCenter";
                            break;
                        }
                    }
                }else {
                    if (centers == null ||
                            !centers.contains(this
                                    .getLabRoomDevice(deviceRepair.getDeviceNumber(), deviceRepair.getLabAddress())
                                    .getLabRoom()
                                    .getLabCenter())) {
                        sameCenter = "noSameCenter";
                    }
                }
            }
            if(request.getSession().getAttribute("selected_role").equals("ROLE_PREEXTEACHING")) {
                if(deviceRepair.getType() != 2) {
                    if (!getLabRoomDevice(deviceRepair.getDeviceNumber(), deviceRepair.getLabAddress()).getLabRoom().getSchoolAcademy().getAcademyNumber().equals(shareService.getUserDetail().getSchoolAcademy().getAcademyNumber())) {
                        sameCenter = "noSameCenter";
                    }
                }else{
                    if(!shareService.findUserByUsername(deviceRepair.getCreater()).getSchoolAcademy().getAcademyNumber().equals(shareService.getUserDetail().getSchoolAcademy().getAcademyNumber())){
                        sameCenter = "noSameCenter";
                    }
                }
            }
            // 设置权限
            BaseActionAuthDTO baseActionAuthDTO = userActionService.getBaseActionAuthDTO(
                    ConstantInterface.FUNCTION_MODEL_ACTION_DEVICEREPAIR, authNames.toString(),
                    "confirmAction" + "_" + request.getSession().getAttribute("selected_role") + "_" +
                            (deviceRepair.getAuditStage() == 2 ? sameCenter : "sameCenter"));
            // 导出权限
            baseActionAuthDTO = "sameCenter".equals(sameCenter) ? getExportActionAuth(baseActionAuthDTO, request, deviceRepair, 2) : baseActionAuthDTO;
            // 将数据置入VO
            dto.setBaseActionAuthDTO(baseActionAuthDTO);
            dto.setContent(content);
            dto.setCreateDate(createDate);
            dto.setCreator(creator);
            dto.setDeviceName(deviceName);
            dto.setDeviceNumber(deviceNumber);
            dto.setLabAddress(labAddress);
            dto.setStatus(status);
            dto.setReportUser(reportUsername);
            dto.setExpectAmount(expectAmountStr);
            dto.setDevicePrice(devicePrice);
            deviceRepairApplyDTOS.add(dto);
        }
        BaseDTO baseDTO = new BaseDTO();
        baseDTO.setRows(deviceRepairApplyDTOS);
        baseDTO.setTotal(totalRecords);
        return baseDTO;
    }

    /**
     * Description 提取重复查询的代码
     * @param search 查询条件
     * @param sql 查询语句
     * @author 黄保钱 2018-10-12
     */
    @Override
    public void searchForDeviceRepair(String search, StringBuilder sql) {
        if (search != null && !"".equals(search)) {
            sql.append(" and (dr.deviceName like '%").append(search).append("%' or");
            sql.append(" dr.deviceNumber like '%").append(search).append("%' or");
            sql.append(" dr.labAddress like '%").append(search).append("%' or");
            sql.append(" dr.content like '%").append(search).append("%')");
        }
    }

    /**
     * Description 通过id获取设备维修申请单
     * @param id 申请单id
     * @return 设备维修申请单
     * @author 黄保钱 2018-10-9
     */
    @Override
    public DeviceRepair getDeviceRepairById(Integer id) {
        return deviceRepairDAO.findDeviceRepairById(id);
    }

    /**
     * Description 通过id删除设备维修申请单
     * @param id 申请单id
     * @return 删除成功与否字符串
     * @author 黄保钱 2018-10-9
     */
    @Override
    public String deleteDeviceRepair(Integer id) {
        DeviceRepair deviceRepair = deviceRepairDAO.findDeviceRepairById(id);
        deviceRepairDAO.remove(deviceRepair);
        return "success";
    }

    /**
     * Description 获取正常设备列表
     * @param acno 学院编号
     * @return 正常设备列表
     * @author 黄保钱 2018-10-12
     */
    @Override
    public List<SchoolDevice> getSchoolDevices(String acno) {
        //获取列表主查询语句
        StringBuilder sql = new StringBuilder("select c from SchoolDevice c where 1=1 " );
        //查询条件
        if (acno != null && !"".equals(acno) && !"-1".equals(acno)) {
            sql.append(" and c.schoolAcademy.academyNumber like '").append(acno).append("' ");
        }
        sql.append(" and c.deviceStatus = 1 and c.CDictionaryByUseStatus.CNumber = 1 and c.CDictionaryByUseStatus.CCategory = 'c_lab_room_device_status'");

        return schoolDeviceDAO.executeQuery(sql.toString());
    }

    /**
     * Description 保存设备维修审核结果
     * @param id 设备维修申请单id
     * @param auditResult 审核结果
     * @param remark 审核意见
     * @author 黄保钱 2018-10-12
     */
    @Override
    public void saveDeviceRepairAudit(Integer id, Integer auditResult, String remark, String acno) {
        if(new String(remark.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.ISO_8859_1).equals(remark)) {
            remark = new String(remark.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        }
        DeviceRepair deviceRepair = deviceRepairDAO.findDeviceRepairById(id);
        User createUser = shareService.findUserByUsername(deviceRepair.getCreater());
        String business = "DeviceRepair" + createUser.getSchoolAcademy().getAcademyNumber();
        business = getBusinessLevels(business);
        if(auditResult == 1){
            User user = shareService.getUserDetail();
            try {
                auditService.saveBusinessLevel(id.toString(),
                        "-1",
                        "pass",
                        remark,
                        business,
                        user.getUsername());
                String s = auditService.getCurrAudit(id.toString(), business);
                // 获取下一级审核
                String nextAuth = JSONObject.parseObject(s).getJSONArray("data").getJSONObject(0).getString("result");
                // 若无下一级则指状态为审核通过
                if("pass".equals(nextAuth)){
                    deviceRepair.setAuditStage(2);
                }else if(!"fail".equals(nextAuth)){
                    List<User> users = findUsersByAuthorityNameAndAcademy(nextAuth, createUser.getSchoolAcademy().getAcademyNumber());
                    for(User u: users){
                        sendDeviceRepairMessage(u.getUsername(), id, "设备维修审核", 2);
                    }
                }
                sendDeviceRepairMessage(deviceRepair.getCreater(), id, "设备维修" + user.getCname() + "审核通过", 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            User user = shareService.getUserDetail();
            try {
                auditService.saveBusinessLevel(id.toString(),
                        "-1",
                        "fail",
                        remark,
                        business,
                        user.getUsername());
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendDeviceRepairMessage(deviceRepair.getCreater(), id, "设备维修" + user.getCname() + "审核拒绝", 1);
            // 设置状态位为审核拒绝
            deviceRepair.setAuditStage(3);
            // 设置设备的状态
            setDeviceToStandard(deviceRepair, 1);
        }
        deviceRepairDAO.store(deviceRepair);
    }

    /**
     * Description 保存设备维修确认单
     * @param id 设备维修申请单
     * @param confirmAmount 确认金额
     * @param content 确认内容
     * @param remark 确认备注
     * @author 黄保钱 2018-10-12
     */
    @Override
    public void saveDeviceRepairConfirm(Integer id, BigDecimal confirmAmount, String content, String remark) {
        DeviceRepair deviceRepair = deviceRepairDAO.findDeviceRepairById(id);
        deviceRepair.setConfirmAmount(confirmAmount);
        deviceRepair.setConfirmContent(content);
        deviceRepair.setMemo(remark);
        User user = shareService.getUserDetail();
        deviceRepair.setConfirmUser(user.getCname());
        deviceRepair.setConfirmDate(new GregorianCalendar());
        deviceRepair.setAuditStage(4);
        // 设置设备的状态
        setDeviceToStandard(deviceRepair, 1);
        deviceRepairDAO.store(deviceRepair);
    }

    /**
     * Description 提取设置设备的状态的代码
     * @param deviceRepair 设备维修申请单
     * @author 黄保钱 2018-10-12
     */
    private void setDeviceToStandard(DeviceRepair deviceRepair, Integer state) {
        if(deviceRepair.getType() == 1){
//            SchoolDevice schoolDevice = schoolDeviceDAO.findSchoolDeviceByDeviceNumber(deviceRepair.getDeviceNumber());
//            CDictionary cDictionary = state == 2 ?
//                    // 若未维修则将设备置为故障状态
//                    shareService.getCDictionaryByCategory("c_lab_room_device_status", "4"):
//                    // 其他则置为正常使用
//                    shareService.getCDictionaryByCategory("c_lab_room_device_status", "1");
//            schoolDevice.setCDictionaryByUseStatus(cDictionary);
//            Set<LabRoomDevice> labRoomDevices = schoolDevice.getLabRoomDevices();
//            for(LabRoomDevice labRoomDevice: labRoomDevices){
//                labRoomDevice.setCDictionaryByDeviceStatus(cDictionary);
//            }
//            schoolDeviceDAO.store(schoolDevice);
            LabRoomDevice labRoomDevice = getLabRoomDevice(deviceRepair.getDeviceNumber(), deviceRepair.getLabAddress());
            CDictionary cDictionary = state == 2 ?
                    // 若未维修则将设备置为故障状态
                    shareService.getCDictionaryByCategory("c_lab_room_device_status", "4"):
                    // 其他则置为正常使用
                    shareService.getCDictionaryByCategory("c_lab_room_device_status", "1");
            labRoomDevice.setCDictionaryByDeviceStatus(cDictionary);
            labRoomDeviceDAO.store(labRoomDevice);
        }
    }

    /**
     * Description 保存设备维修验收
     * @param id 设备维修申请单
     * @param remark 确认备注
     * @param result 维修状态
     * @author 黄保钱 2018-11-6
     */
    @Override
    public void saveAcceptanceDeviceRepair(Integer id, String remark, Integer result) {
        DeviceRepair deviceRepair = deviceRepairDAO.findDeviceRepairById(id);
        // 备注
        deviceRepair.setMemo(remark);
        if(result == 1) {
            // 已维修
            deviceRepair.setAuditStage(8);
        }
        if(result == 2) {
            // 未维修
            deviceRepair.setAuditStage(5);
        }
        // 设置设备的状态
        setDeviceToStandard(deviceRepair, result);
        // 保存当前登录人为验收人
        deviceRepair.setAcceptanceUser(shareService.getUserDetail().getUsername());
        // 保存验收日期
        deviceRepair.setAcceptanceDate(Calendar.getInstance());
        deviceRepairDAO.store(deviceRepair);
    }

    /**
     * Description 保存设备维修填写
     * @param id 设备维修申请单
     * @param confirmAmount 确认金额
     * @param content 确认内容
     * @author 黄保钱 2018-11-6
     */
    @Override
    public void saveWriteDeviceRepair(Integer id, BigDecimal confirmAmount, String content) {
        DeviceRepair deviceRepair = deviceRepairDAO.findDeviceRepairById(id);
        // 确认金额
        deviceRepair.setConfirmAmount(confirmAmount);
        // 确认内容
        deviceRepair.setConfirmContent(content);
        // 确认人为当前登陆人
        User user = shareService.getUserDetail();
        deviceRepair.setConfirmUser(user.getCname());
        // 确认时间为当前系统时间
        deviceRepair.setConfirmDate(new GregorianCalendar());
        // 设置状态为已填写
        deviceRepair.setAuditStage(6);
        deviceRepairDAO.store(deviceRepair);
    }

    /**
     * Description 保存
     * @param deviceRepair 申请单
     * @author 黄保钱 2018-11-6
     */
    @Override
    public void saveDeviceRepair(DeviceRepair deviceRepair){
        deviceRepairDAO.store(deviceRepair);
        deviceRepairDAO.flush();
    }

    /**
     * Description 获取设备维修申请单列表（所有查看）
     * @param offset 起始位置
     * @param limit 限制每页显示个数
     * @param search 查询条件
     * @param sort 排序条件
     * @param order 正序或倒序
     * @param request 请求
     * @return 申请单显示列表
     * @author 黄保钱 2018-11-6
     */
    @Override
    public BaseDTO getDeviceRepairViewListBySelect(String search, Integer offset, Integer limit, String sort, String order, HttpServletRequest request){

        // sql查询语句
        StringBuilder sql = new StringBuilder("select distinct dr from DeviceRepair dr, LabRoomDevice lrd, User u where 1=1 and (dr.type = 2 or (dr.deviceNumber = lrd.schoolDevice.deviceNumber and dr.labAddress = lrd.labRoom.labRoomName)) and u.username=dr.creater ");

        // 条件
        StringBuilder condition = new StringBuilder();
        searchForDeviceRepair(search, condition);
        condition.append(" and (dr.auditStage > 0)");
        // 实验中心主任和实践副院长仅显示本中心的
        if(request.getSession().getAttribute("selected_role").equals("ROLE_PREEXTEACHING")) {
            condition.append(" and (( lrd.labRoom.labCenter.schoolAcademy.academyNumber = '").append(shareService.getUserDetail().getSchoolAcademy().getAcademyNumber()).append("' and dr.type = 1)");
            condition.append(" or (u.schoolAcademy.academyNumber = '").append(shareService.getUserDetail().getSchoolAcademy().getAcademyNumber()).append("' and dr.type = 2))");
        }
        if(request.getSession().getAttribute("selected_role").equals("ROLE_EXCENTERDIRECTOR")){
            condition.append(" and dr.creater = '").append(shareService.getUserDetail().getUsername()).append("' ");
        }
        //排序
        sql.append(condition);
        sql.append(" order by dr.").append(sort).append(" ").append(order);
        List<DeviceRepair> deviceRepairs = deviceRepairDAO.executeQuery(sql.toString(), offset, limit);

        // 查找设备维修数量sql语句
        sql = new StringBuilder("select count(distinct dr) from DeviceRepair dr, LabRoomDevice lrd, User u where 1=1 and (dr.type = 2 or (dr.deviceNumber = lrd.schoolDevice.deviceNumber and dr.labAddress = lrd.labRoom.labRoomName)) and u.username=dr.creater ");

        sql.append(condition);
        int totalRecords = ((Long) schoolDeviceDAO.createQuerySingleResult(sql.toString()).getSingleResult()).intValue();

        // 封装VO
        List<DeviceRepairApplyDTO> deviceRepairApplyDTOS = new ArrayList<>();
        for(DeviceRepair deviceRepair: deviceRepairs){
            DeviceRepairApplyDTO dto = new DeviceRepairApplyDTO();
            // 设置主键
            dto.setId(deviceRepair.getId());
            // 设置设备的数据
            String deviceNumber =null;
            if(Objects.nonNull(deviceRepair.getDeviceNumber())){
                deviceNumber = deviceRepair.getDeviceNumber();
            }
            String deviceName =null;
            if(Objects.nonNull(deviceRepair.getDeviceName())){
                deviceName = deviceRepair.getDeviceName();
            }
            String labAddress =null;
            if(Objects.nonNull(deviceRepair.getLabAddress())){
                labAddress = deviceRepair.getLabAddress();
            }
            String creator =null;
                User creatorUser = shareService.findUserByUsername(deviceRepair.getCreater());
            if(Objects.nonNull(creatorUser)){
//                creator = deviceRepair.getCreater();
                creator = creatorUser.getCname();
            }
            String createDate =null;
            if(Objects.nonNull(deviceRepair.getCreateDate())){
                createDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(deviceRepair.getCreateDate().getTime());
            }
            String content =null;
            if(Objects.nonNull(deviceRepair.getContent())){
                content = deviceRepair.getContent();
            }
            Integer status = null;
            if(Objects.nonNull(deviceRepair.getAuditStage())){
                status = deviceRepair.getAuditStage();
            }
            // 报修人
            String reportUsername =null;
            User reportUser = shareService.findUserByUsername(deviceRepair.getReportUser());
            if(Objects.nonNull(reportUser)){
                reportUsername = reportUser.getCname();
            }
            // 预计金额
            String expectAmountStr =null;
            if(Objects.nonNull(deviceRepair.getExpectAmount())){
                expectAmountStr = deviceRepair.getExpectAmount().toString();
            }
            // 设备价格
            String devicePrice = null;
            if(deviceRepair.getType() == 1){
                LabRoomDevice lrd = getLabRoomDevice(deviceRepair.getDeviceNumber(), deviceRepair.getLabAddress());
                if(Objects.nonNull(lrd) && Objects.nonNull(lrd.getSchoolDevice().getDevicePrice())){
                    devicePrice = lrd.getSchoolDevice().getDevicePrice().toString();
                }
            }
            String sameCenter = "sameCenter";
            if(request.getSession().getAttribute("selected_role").equals("ROLE_EXCENTERDIRECTOR")) {
                Set<LabCenter> centers = shareService.getUserDetail().getLabCentersForCenterManager();
                if(deviceRepair.getType() == 2){
                    sameCenter = "noSameCenter";
                    for(LabCenter l: centers){
                        if(l.getUserByCenterManager().getUsername().equals(deviceRepair.getCreater())){
                            sameCenter = "sameCenter";
                            break;
                        }
                    }
                }else {
                    if (centers == null ||
                            !centers.contains(this
                                    .getLabRoomDevice(deviceRepair.getDeviceNumber(), deviceRepair.getLabAddress())
                                    .getLabRoom()
                                    .getLabCenter())) {
                        sameCenter = "noSameCenter";
                    }
                }
            }
            if(request.getSession().getAttribute("selected_role").equals("ROLE_PREEXTEACHING")) {
                if(deviceRepair.getType() != 2) {
                    if (!getLabRoomDevice(deviceRepair.getDeviceNumber(), deviceRepair.getLabAddress()).getLabRoom().getSchoolAcademy().getAcademyNumber().equals(shareService.getUserDetail().getSchoolAcademy().getAcademyNumber())) {
                        sameCenter = "noSameCenter";
                    }
                }else{
                    if(!shareService.findUserByUsername(deviceRepair.getCreater()).getSchoolAcademy().getAcademyNumber().equals(shareService.getUserDetail().getSchoolAcademy().getAcademyNumber())){
                        sameCenter = "noSameCenter";
                    }
                }
            }

            // 设置权限
            BaseActionAuthDTO baseActionAuthDTO = userActionService.getBaseActionAuthDTO(
                    ConstantInterface.FUNCTION_MODEL_ACTION_DEVICEREPAIR, request.getSession().getAttribute("selected_role").toString(), "viewList_" + sameCenter);
            // 导出
            if(sameCenter.equals("sameCenter")) {
                baseActionAuthDTO = getExportActionAuth(baseActionAuthDTO, request, deviceRepair, 2);
                baseActionAuthDTO = getExportActionAuth(baseActionAuthDTO, request, deviceRepair, 1);
            }
            // 将数据置入VO
            dto.setBaseActionAuthDTO(baseActionAuthDTO);
            dto.setContent(content);
            dto.setCreateDate(createDate);
            dto.setCreator(creator);
            dto.setDeviceName(deviceName);
            dto.setDeviceNumber(deviceNumber);
            dto.setLabAddress(labAddress);
            dto.setStatus(status);
            dto.setReportUser(reportUsername);
            dto.setExpectAmount(expectAmountStr);
            dto.setDevicePrice(devicePrice);
            deviceRepairApplyDTOS.add(dto);
        }
        BaseDTO baseDTO = new BaseDTO();
        baseDTO.setRows(deviceRepairApplyDTOS);
        baseDTO.setTotal(totalRecords);
        return baseDTO;
    }

    /**
     * Description 根据设备编号和实验室名称寻找设备
     * @param deviceNumber 设备编号
     * @param labRoomName 实验室名称
     * @return 设备
     * @author 黄保钱 2019-1-23
     */
    @Override
    public LabRoomDevice getLabRoomDevice(String deviceNumber, String labRoomName){
        String sql = "select lrd from LabRoomDevice lrd where lrd.schoolDevice.deviceNumber='" +
                deviceNumber + "'" +
                " and lrd.labRoom.labRoomName='" +
                labRoomName + "'";
        LabRoomDevice labRoomDevice = (LabRoomDevice) labRoomDeviceDAO.executeQuerySingleResult(sql);
        return labRoomDevice;
    }

    /**
     * Description 根据实验室名称获取该实验室设备
     * @param labAddress 实验室名称
     * @return 设备列表
     * @author 黄保钱 2019-1-23
     */
    @Override
    public List<LabRoomDevice> getLabRoomDevices(String labAddress){
        String sql = "select lrd from LabRoomDevice lrd where 1=1";
        if(labAddress != null && !"".equals(labAddress)){
            sql += " and lrd.labRoom.labRoomName='" + labAddress + "'";
        }
        sql += " and lrd.CDictionaryByDeviceStatus.CNumber = 1 and lrd.CDictionaryByDeviceStatus.CCategory = 'c_lab_room_device_status'";
        List<LabRoomDevice> labRoomDevices = labRoomDeviceDAO.executeQuery(sql);
        return labRoomDevices;
    }

    /**
     * Description 获取实际的业务名称
     * @param businessType 业务名称
     * @return 实际业务名称
     * @author 黄保钱 2019-1-23
     */
    @Override
    public String getBusinessLevels(String businessType) {
        try {
            List<String> businessAuditConfigLevel = auditService.getBusinessAuditConfigLevel(businessType);
            if(businessAuditConfigLevel.size() == 0){
                businessType = "DeviceRepair-1";
                businessAuditConfigLevel = auditService.getBusinessAuditConfigLevel(businessType);
                if(businessAuditConfigLevel.size() == 0){
                    throw new RuntimeException("未配置审核权限");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return businessType;
    }

    /**
     * Description 设备维修消息发送
     * @param username 接收用户名
     * @param id 设备维修id
     * @param title 消息标题
     * @param tag 消息类型
     * @author 黄保钱 2019-1-23
     */
    @Override
    public void sendDeviceRepairMessage(String username, Integer id, String title, Integer tag){
        //消息(发送到审核人)
        Message message = new Message();
        Calendar today = Calendar.getInstance();
        message.setSendUser(shareService.getUserDetail().getCname());
        message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
        message.setCond(0);
        message.setTitle(title);
        message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
        message.setCreateTime(today);
        String content = "<a onclick='changeMessage(this)' href=\"../deviceRepair/" +
                (tag == 1 ? "viewDeviceRepairApply?id=" + id + "\">查看" : "deviceRepairCheckList\">审核") +
                "</a>";
        message.setContent(content);
        message.setTage(tag);
        User user = shareService.findUserByUsername(username);
        shareService.sendMsg(user, message);
    }

    /**
     * Description 通过权限名和学院寻找用户
     * @param authName 权限名
     * @param acno 学院编号
     * @return 用户列表
     * @author 黄保钱 2019-1-23
     */
    @Override
    public List<User> findUsersByAuthorityNameAndAcademy(String authName, String acno){
        String sql = "select u from User u join u.authorities a where 1=1";
        sql += " and a.authorityName = '" + authName + "'";
        sql += " and (u.schoolAcademy.academyType = 1 or u.schoolAcademy.academyNumber = '" + acno + "')";
        List<User> users = userDAO.executeQuery(sql);
        return users;
    }

    /**
     * Description 获取导出权限
     * @param baseActionAuthDTO 权限封装对象
     * @param request 请求
     * @param deviceRepair 设备维修单
     * @param type 类型（1-审核人，2-确认人）
     * @return 权限封装对象
     * @author 黄保钱 2019-1-23
     */
    @Override
    public BaseActionAuthDTO getExportActionAuth(BaseActionAuthDTO baseActionAuthDTO, HttpServletRequest request, DeviceRepair deviceRepair, Integer type){
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        // 导出权限
        if(type == 1) {
            User createUser = shareService.findUserByUsername(deviceRepair.getCreater());
            String businessType = "DeviceRepair" + createUser.getSchoolAcademy().getAcademyNumber();
            businessType = getBusinessLevels(businessType);
            try {
                String s = auditService.getBusinessLevel("-1", deviceRepair.getId().toString(), businessType);
                JSONArray jsonArray = JSONObject.parseObject(s).getJSONArray("data");
                if (jsonArray != null && jsonArray.size() > 0) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject o = jsonArray.getJSONObject(i);
                        if (request.getSession().getAttribute("selected_role").equals("ROLE_" + o.getString("authName"))) {
                            baseActionAuthDTO.setPublicActionAuth(true);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(type == 2){
            boolean flag = false;
            Map<String, String> params = new HashMap<>();
            params.put("businessType", pConfigDTO.PROJECT_NAME + "RepairAcceptance");
            String res = HttpClientUtil.doPost(pConfigDTO.auditServerUrl+"audit/getBusinessAuditConfigLevel", params);
            JSONObject j1 = JSONObject.parseObject(res);
            if(j1.getJSONArray("data").size() != 0) {
                if(request.getSession().getAttribute("selected_role").equals("ROLE_"+j1.getJSONArray("data").getJSONObject(0).getString("authId"))){
                    flag = true;
                }
            }
            params.put("businessType", pConfigDTO.PROJECT_NAME + "RepairWrite");
            res = HttpClientUtil.doPost(pConfigDTO.auditServerUrl+"audit/getBusinessAuditConfigLevel", params);
            j1 = JSONObject.parseObject(res);
            if(j1.getJSONArray("data").size() != 0) {
                if(request.getSession().getAttribute("selected_role").equals("ROLE_"+j1.getJSONArray("data").getJSONObject(0).getString("authId"))){
                    flag = true;
                }
            }
            params.put("businessType", pConfigDTO.PROJECT_NAME + "RepairRecord");
            res = HttpClientUtil.doPost(pConfigDTO.auditServerUrl+"audit/getBusinessAuditConfigLevel", params);
            j1 = JSONObject.parseObject(res);
            if(j1.getJSONArray("data").size() != 0) {
                if(request.getSession().getAttribute("selected_role").equals("ROLE_"+j1.getJSONArray("data").getJSONObject(0).getString("authId"))){
                    flag = true;
                }
            }
            baseActionAuthDTO.setPublicActionAuth(flag);
        }
        return baseActionAuthDTO;
    }

    /**
     * Description 保存设备维修
     * @param deviceRepair 要保存的设备维修（无id）
     * @return 保存后的设备维修（有id）
     * @author 黄保钱 2019-1-23
     */
    @Override
    public DeviceRepair save(DeviceRepair deviceRepair){
        deviceRepair = deviceRepairDAO.store(deviceRepair);
        return deviceRepair;
    }

}
