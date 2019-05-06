package net.gvsun.lims.service.DeviceRepair;

import net.gvsun.lims.dto.common.BaseActionAuthDTO;
import net.gvsun.lims.dto.common.BaseDTO;
import net.zjcclims.domain.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

public interface DeviceRepairService {

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
    BaseDTO getStandardDeviceListBySelect(String labRoom,Integer deviceId, String search, Integer offset, Integer limit, String sort, String order, HttpServletRequest request) ;

    /**
     * Description 获得所进学院的实验分室
     * @param acno 学院编号
     * @return 实验分室列表
     * @author 黄保钱 2018-10-9
     */
    List<LabRoom> getLabRoomByAcno(String acno);

    /**
     * Description 保存设备维修申请
     * @param labRoomName 实验室id
     * @param deviceName 设备id
     * @param memo 维修描述
     * @author 黄保钱 2018-10-9
     */
    void saveDeviceRepairApply(String labRoomName, String deviceName, String memo, Integer id, String reportUser, BigDecimal expectAmount);


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
    BaseDTO getMyDeviceRepairListBySelect(String search, Integer offset, Integer limit, String sort, String order, HttpServletRequest request);

    /**
     * Description 提交设备维修申请单
     * @param id 申请单id
     * @return 提交成功与否字符串
     * @author 黄保钱 2018-10-9
     */
    String submitDeviceRepair(Integer id, String acno);

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
    BaseDTO getDeviceRepairCheckListBySelect(Integer auditStage, String search, Integer offset, Integer limit, String sort, String order, HttpServletRequest request, String acno);


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
    BaseDTO getDeviceRepairConfirmListBySelect(String search, Integer offset, Integer limit, String sort, String order, HttpServletRequest request);

    /**
     * Description 通过id获取设备维修申请单
     * @param id 申请单id
     * @return 设备维修申请单
     * @author 黄保钱 2018-10-9
     */
    DeviceRepair getDeviceRepairById(Integer id);

    /**
     * Description 通过id删除设备维修申请单
     * @param id 申请单id
     * @return 删除成功与否字符串
     * @author 黄保钱 2018-10-9
     */
    String deleteDeviceRepair(Integer id);

    /**
     * Description 获取正常设备列表
     * @param acno 学院编号
     * @return 正常设备列表
     * @author 黄保钱 2018-10-12
     */
    List<SchoolDevice> getSchoolDevices(String acno);

    /**
     * Description 保存设备维修审核结果
     * @param id 设备维修申请单id
     * @param auditResult 审核结果
     * @param remark 审核意见
     * @author 黄保钱 2018-10-12
     */
    void saveDeviceRepairAudit(Integer id, Integer auditResult, String remark, String acno);

    /**
     * Description 保存设备维修确认单
     * @param id 设备维修申请单
     * @param confirmAmount 确认金额
     * @param content 确认内容
     * @param remark 确认备注
     * @author 黄保钱 2018-10-12
     */
    void saveDeviceRepairConfirm(Integer id, BigDecimal confirmAmount, String content, String remark);

    /**
     * Description 保存设备维修验收
     * @param id 设备维修申请单
     * @param remark 确认备注
     * @param result 维修状态
     * @author 黄保钱 2018-11-6
     */
    void saveAcceptanceDeviceRepair(Integer id, String remark, Integer result);

    /**
     * Description 保存设备维修填写
     * @param id 设备维修申请单
     * @param confirmAmount 确认金额
     * @param content 确认内容
     * @author 黄保钱 2018-11-6
     */
    void saveWriteDeviceRepair(Integer id, BigDecimal confirmAmount, String content);

    /**
     * Description 保存
     * @param deviceRepair 申请单
     * @author 黄保钱 2018-11-6
     */
    void saveDeviceRepair(DeviceRepair deviceRepair);

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
    BaseDTO getDeviceRepairViewListBySelect(String search, Integer offset, Integer limit, String sort, String order, HttpServletRequest request);

    /**
     * Description 根据设备编号和实验室名称寻找设备
     * @param deviceNumber 设备编号
     * @param labRoomName 实验室名称
     * @return 设备
     * @author 黄保钱 2019-1-23
     */
    LabRoomDevice getLabRoomDevice(String deviceNumber, String labRoomName);

    /**
     * Description 根据实验室名称获取该实验室设备
     * @param labAddress 实验室名称
     * @return 设备列表
     * @author 黄保钱 2019-1-23
     */
    List<LabRoomDevice> getLabRoomDevices(String labAddress);

    /**
     * Description 获取实际的业务名称
     * @param businessType 业务名称
     * @return 实际业务名称
     * @author 黄保钱 2019-1-23
     */
    String getBusinessLevels(String businessType);

    /**
     * Description 设备维修消息发送
     * @param username 接收用户名
     * @param id 设备维修id
     * @param title 消息标题
     * @param tag 消息类型
     * @author 黄保钱 2019-1-23
     */
    void sendDeviceRepairMessage(String username, Integer id, String title, Integer tag);

    /**
     * Description 通过权限名和学院寻找用户
     * @param authName 权限名
     * @param acno 学院编号
     * @return 用户列表
     * @author 黄保钱 2019-1-23
     */
    List<User> findUsersByAuthorityNameAndAcademy(String authName, String acno);

    /**
     * Description 获取导出权限
     * @param baseActionAuthDTO 权限封装对象
     * @param request 请求
     * @param deviceRepair 设备维修单
     * @param type 类型（1-审核人，2-确认人）
     * @return 权限封装对象
     * @author 黄保钱 2019-1-23
     */
    BaseActionAuthDTO getExportActionAuth(BaseActionAuthDTO baseActionAuthDTO, HttpServletRequest request, DeviceRepair deviceRepair, Integer type);

    /**
     * Description 保存设备维修
     * @param deviceRepair 要保存的设备维修（无id）
     * @return 保存后的设备维修（有id）
     * @author 黄保钱 2019-1-23
     */
    DeviceRepair save(DeviceRepair deviceRepair);

    /**
     * Description 提取重复查询的代码
     * @param search 查询条件
     * @param sql 查询语句
     * @author 黄保钱 2018-10-12
     */
    void searchForDeviceRepair(String search, StringBuilder sql);
}
