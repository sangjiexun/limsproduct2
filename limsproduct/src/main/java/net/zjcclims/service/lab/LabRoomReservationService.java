package net.zjcclims.service.lab;

import net.zjcclims.domain.*;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;

public interface LabRoomReservationService {

	/***************************** 
	*Description 根据学院找到用户信誉积分
	*
	*@author:余南新
	*@param:cid(实训中心id)
	*@date:2017.4.26
	*****************************/
	public List<User> findUserCreditWarningByCenter(int cid,int currpage,int pageSize);
	
	/***************************** 
	*Description 根据学院找到用户信誉积分
	*
	*@author:余南新
	*@param:cid(实训中心id)
	*@date:2017.4.26
	*****************************/
	public int getCountUserCreditWarningByCenter(int cid);
	/***************************** 
	*Description 找到教师信誉积分
	*
	*@author:周志辉
	*@param:
	*@date:2018.8.14
	*****************************/
	public int getCountTeacherCreditWarningByQuery(User user);
	
	/***************************** 
	*Description 找到教师信誉积分并分页
	*
	*@author:周志辉
	*@param:
	*@date:2017.8.14
	*****************************/
	public List<User> findTeacherCreditWarningByQuery(int currpage,int pageSize,User user);
	/***************************** 
	*Description 找到学生信誉积分
	*
	*@author:周志辉
	*@param:
	*@date:2018.8.14
	*****************************/
	public int getCountStudentCreditWarningByQuery(User user);
	
	/***************************** 
	*Description 找到学生信誉积分并分页
	*
	*@author:周志辉
	*@param:
	*@date:2017.8.14
	*****************************/
	public List<User> findStudentCreditWarningByQuery(int currpage,int pageSize,User user);
	/*************************************************************************************
	 * Description 根据实训室预约的id查找所有扣分项
	 * 
	 * @param id
	 * @author 孙虎
	 * @date 2017-08-16
	 *************************************************************************************/
	public List<LabRoomReservationCredit> findlabRoomReservationCreditOptionById(Integer id);
	/*************************************************************************************
	 * Description 分页查询实训室列表（实训室预约）
	 * 
	 * @author 孙虎
	 * @date 2017-09-20
	 *************************************************************************************/
	public List<LabRoom> findLabRoompage(LabRoom labRoom, int currpage, int pageSize, String acno,HttpServletRequest request);
	/*************************************************************************************
	 * 判断工位预约时间与实验室预约时间是否冲突
	 * 顾延钊
	 * 2019-5-5
	 *************************************************************************************/
	public int findReservationEnableOrNot(Integer labRoomId,Calendar reservationTime,Calendar startTime,Calendar endTime);
	/*************************************************************************************
	 * Description 分页查询实训室列表（实训室预约）,不包括软件
	 *
	 * @author 廖文辉
	 * @date 2018-08-28
	 *************************************************************************************/
	public List<LabRoom> findLabRoom(LabRoom labRoom, int currpage, int pageSize, String acno,HttpServletRequest request);
	/*************************************************************************************
	 * Description 根据所选时间段查询剩余工位数（实训室预约-预约）
	 * 
	 * @author 孙虎
	 * @date 2017-09-21
	 *************************************************************************************/
	public int findRestReservationStations(Integer labRoomId, Calendar reservationTime,  Calendar startTime,  Calendar endTime);
	/*************************************************************************************
	 * Description 保存实训室预约（实训室预约-预约）
	 * 
	 * @author 孙虎
	 * @throws NoSuchAlgorithmException 
	 * @date 2017-09-21
	 *************************************************************************************/
	public void saveReservationStations(Integer labRoomId, Calendar reservationTime,  Calendar startTime,  Calendar endTime,String[] array,String reason,String techer,String dean,String userRole) throws NoSuchAlgorithmException;
	/*************************************************************************************
	 * Description 判断所传数组是否是正确学生编号 返回错误的学号组
	 * 
	 * @author 孙虎
	 * @date 2017-09-22
	 *************************************************************************************/
	public String isAllStudent(String[] array);
	/*************************************************************************************
	 * Description 根据登陆人权限得到实验室预约列表
	 * 
	 * @author 孙虎
	 * @date 2017-09-25
	 *************************************************************************************/
	public List<LabRoomStationReservation> findLabRoomreservatioList(LabRoomStationReservation labRoomStationReservation, int tage, int currpage,
			int pageSize, String acno,int isAudit);
	/*************************************************************************************
	 * Description 保存不同身份的审核结果
	 * @author 孙虎
	 * @throws NoSuchAlgorithmException 
	 * @date 2017-09-27
	 *************************************************************************************/
	public LabRoomStationReservation saveAuditResult(LabRoomStationReservation labRoomStationReservation, LabRoomStationReservationResult labRoomStationReservationResult) throws NoSuchAlgorithmException;
	/*************************************************************************************
	 * Description 保存不同身份的审核结果
	 * @author 孙虎
	 * @throws NoSuchAlgorithmException 
	 * @date 2017-09-27
	 *************************************************************************************/
	public SoftwareReserve saveAuditResultForSoftware(SoftwareReserve softwareReserve, SoftwareReserveAudit softwareReserveAudit) throws NoSuchAlgorithmException,InterruptedException;
	/*************************************************************************************
	 * Description 保存不同身份的审核结果
	 * @author 孙虎
	 * @throws NoSuchAlgorithmException 
	 * @date 2017-10-19
	 *************************************************************************************/
	public LabRoomDeviceReservation saveAuditResultDevice(LabRoomDeviceReservation labRoomDeviceReservation, LabRoomDeviceReservationResult labRoomDeviceReservationResult) throws NoSuchAlgorithmException;
	/***************************** 
	*Description 初始化教师信誉积分管理
	*
	*@author:周志辉
	*@date:2017.9.28
	*****************************/
	public void initializeTeacherCredit();
	/***************************** 
	*Description 初始化学生信誉积分管理
	*
	*@author:周志辉
	*@date:2017.9.28
	*****************************/
	public void initializeStudentCredit();
	/***************************** 
	*Description 找到教师
	*
	*@author:周志辉
	*@param:
	*@date:2017.8.14
	*****************************/
	public List<User> findTeacherByQuery();
	/***************************** 
	*Description 找到学生
	*
	*@author:周志辉
	*@param:
	*@date:2017.8.14
	*****************************/
	public List<User> findStudentByQuery();
	/*************************************************************************************
	 * Description 根据登陆人权限得到实验室预约列表
	 * 
	 * @author 孙虎
	 * @date 2017-09-25
	 *************************************************************************************/
	public List<LabRoomDeviceReservation> findLabRoomDeviceReservation(LabRoomDevice labRoomDevice, int tage, int currpage,
			int pageSize, String acno,int isAudit,HttpServletRequest request);
	/****************************************************************************
	 * 功能：查询信誉登记纪录by username
	 * 作者：周志辉
	 * @return 
	 ****************************************************************************/
	public List<LabRoomStationReservationCredit> findCreditByUsername(
			String username);
	
	/*************************************************************************************
	 * Description 获取所有我的申请信息
	 * 
	 * @author 张德冰
	 * @date 2018.03.07
	 *************************************************************************************/
	public List<LabRoomDeviceReservation> findLabRoomDeviceReservationAll();
	/*************************************************************************************
	 * Description 分页查询实验室预约的实验室
	 *
	 * @author 廖文辉
	 * @date 2018.11.28
	 *************************************************************************************/
	public List<LabRoom> findLabRoomReservation(LabRoom labRoom, int currpage, int pageSize, String acno,HttpServletRequest request);

	/*************************************************************************************
	 * Description 获取所有设备预约表中所有设备
	 * @author 张德冰
	 * @date 2018-12-05
	 *************************************************************************************/
	public List<SchoolDevice> findSchoolDeviceByLabRoomDeviceReservation();

	@Transactional
	LabRoomDeviceReservation saveAuditResultDevice(LabRoomDeviceReservation labRoomDeviceReservation, Integer audit, String remark) throws NoSuchAlgorithmException;

	@Transactional
	public void saveReservationStations(Integer labRoomId, Calendar reservationTime,  Calendar startTime,  Calendar endTime,String[] array,String reason,String teacher,String userRole) throws NoSuchAlgorithmException;

	/**
	 * 获取下一级审核人
	 * @param nextAuth 下一级审核权限
	 * @return 审核人列表
	 * @author 黄保钱 2019-5-7
	 */
	@Transactional
	List<User> getNextAuditUser(String nextAuth, String businessAppUid);
}
