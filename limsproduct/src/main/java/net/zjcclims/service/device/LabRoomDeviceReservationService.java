package net.zjcclims.service.device;

import net.zjcclims.domain.LabRoomDeviceReservation;
import net.zjcclims.domain.LabRoomDeviceReservationCredit;
import net.zjcclims.domain.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;


/**
 * Spring service that handles CRUD requests for LabRoomDeviceReservation entities
 * 
 */
public interface LabRoomDeviceReservationService {

	
	/**
	 * Delete an existing LabRoomDeviceReparation entity
	 * 
	 */
	public void deleteLabRoomDeviceReservation(LabRoomDeviceReservation labRoomDeviceReservation);
	/************************************
	 *功能：判断当前设备预约是否在导师审核阶段
	 *作者：贺子龙
	 *时间：2015-10-31 
	 ************************************/
	public boolean isUnderTeacherAudit(LabRoomDeviceReservation labRoomDeviceReservation);
	
	/************************************
	 *功能：判断当前设备预约是否在实验室管理员审核阶段
	 *作者：贺子龙
	 *时间：2015-10-31 
	 ***********************************/
	public boolean isUnderLabManagerAudit(LabRoomDeviceReservation labRoomDeviceReservation);
	
	/************************************
	 *功能：判断当前设备预约是否在设备管理员审核阶段
	 *作者：贺子龙
	 *时间：2015-10-31 
	 ***********************************/
	public boolean isUnderManagerAudit(LabRoomDeviceReservation labRoomDeviceReservation);
	
	
	/************************************
	 *功能：找到innerSame相同的设备预约
	 *作者：贺子龙
	 *时间：2016-04-19
	 ***********************************/
	public List<LabRoomDeviceReservation> findInnerSame(int reservationId);
	
	/****************************************************************************
	 * @功能：根据设备id查询设备的预约记录
	 * @作者：贺子龙
	 * @日期：2016-05-05
	 ****************************************************************************/
	public LabRoomDeviceReservation saveLabRoomDeviceReservation(LabRoomDeviceReservation labRoomDeviceReservation);
	
	/****************************************************************************
	 * @throws IOException 
	 * @功能：根据设备id查询设备的预约记录
	 * @作者：贺子龙
	 * @日期：2016-05-05
	 ****************************************************************************/
	public void saveLabRoomDeviceReservationNew(LabRoomDeviceReservation labRoomDeviceReservation, HttpServletResponse response) throws IOException;
	
	/*
	 * 刷新权限
	 */
	public String refreshPermissions(Integer roomId, HttpServletResponse response) throws IOException;
	/*************************************************************************************
	 * Description 根据设备预约的id查找预约纪录
	 * 
	 * @param id
	 * @author 周志辉
	 * @date 2017-08-10
	 *************************************************************************************/
	public LabRoomDeviceReservation findlabRoomDeviceReservationById(Integer id);
	
	/*************************************************************************************
	 * Description 根据设备预约的id查找所有扣分项
	 * 
	 * @param id
	 * @author 周志辉
	 * @date 2017-08-10
	 *************************************************************************************/
	public List<LabRoomDeviceReservationCredit> findlabRoomDeviceReservationCreditOptionById(Integer id);

	/**
	 * Description 保存一个设备预约
	 * @param equinemtid 设备id
	 * @param description 备注
	 * @param phone 电话
	 * @param teacher 描述
	 * @param startDate 起始时间
	 * @param endDate 结束时间
	 * @param research 研究项目
	 * @return 保存后的设备预约（有id）
	 * @throws ParseException 日期解析异常
	 * @author 黄保钱 2019-1-23
	 */
	LabRoomDeviceReservation saveALabRoomDeviceReservation(Integer equinemtid, String description, String phone,
														   String teacher, String startDate, String endDate,
														   String research) throws ParseException;

	/**
	 * Description 设备预约判冲
	 * @param equinemtid 设备id
	 * @param reservationId 设备预约id
	 * @param calendar1 起始时间
	 * @param calendar2 结束时间
	 * @return 是否可预约（1-可预约，0-不可预约）
	 * @author 黄保钱 2019-1-23
	 */
	Integer judgeConflictForDeviceReservation(Integer equinemtid, Integer reservationId, Calendar calendar1, Calendar calendar2);

	/**
	 * Description 获取审核人
	 * @param authName 权限名
	 * @param labRoomDeviceReservation 设备预约
	 * @return 审核人列表
	 * @author 黄保钱 2019-1-23
	 */
    List<User> getAuditUser(String authName, LabRoomDeviceReservation labRoomDeviceReservation);

	/**
	 * Describing 获取阶段审核信息
	 * @param labRoomDeviceReservation 设备预约
	 * @param stage 阶段
	 * @return 信息
	 * @author 黄保钱 2019-1-23
	 */
    Object[] getCurrJSONObject(LabRoomDeviceReservation labRoomDeviceReservation, Integer stage);
}
