package net.zjcclims.service.lab;

import net.zjcclims.domain.*;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

public interface LabRoomLendingService {
	/**
	 * Description 判断借用申请时间是否可用
	 * @param labRoomId 实训室主键
	 * @param lendingTime 借用日期
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 * @author 陈乐为 2017-10-10
	 */
	public int findLendingEnableOrNot(Integer labRoomId, Calendar lendingTime, Calendar startTime, Calendar endTime, String acno);
	
	/**
	 * Description 实训室借用-借用申请-保存
	 * @param labRoomId 实训室主键
	 * @param lendingTime 借用日期
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param reason 借用原因
	 * @author 陈乐为 2017-10-10
	 */
	public int saveLabRoomLending(Integer labRoomId, Calendar lendingTime, String[] reservationTimes, String reason,HttpServletRequest request, Integer auditNumber) throws ParseException;
	
	/**
	 * Description 查询对应实训室的借用记录并分页
	 * @param labRoomId
	 * @param currpage
	 * @param pageSize
	 * @return
	 * @author 陈乐为 2017-10-10
	 */
	public List<LabReservation> findLendingsByLabId(Integer labRoomId, int currpage, int pageSize);
	
	/*********************************************************************************
	 *@description:实验室借用申请审核记录
	 *@author: 张愉
	 *@date：2017-10-10
	 ********************************************************************************/
	public List<LabReservationAudit> findAllLabReservationAuditById(int Id);
	/*************************************************************************************
	 * Description 保存不同身份的审核结果
	 * @author 孙虎
	 * @throws NoSuchAlgorithmException 
	 * @date 2017-11-5
	 *************************************************************************************/
	public LabReservation saveAuditResultForlab(LabReservation labReservation, LabReservationAudit labReservationAudit) throws NoSuchAlgorithmException;


	public LabReservation saveAuditResult(LabReservation labReservation, String sAuditResult, String remark, String acno) throws NoSuchAlgorithmException;

	public SystemTime getSystemTimeByStartAndEnd(Calendar start, Calendar end);
	/**
	 * Description 判断借用申请时间是否可用
	 *
	 * @param labReservationId 预约主键
	 * @param labRoomId 实训室主键
	 * @param lendingTime 借用日期
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 * @author 廖文辉 2018-12-20
	 */
	public int findLendingEnableOrNot(Integer labReservationId,Integer labRoomId, Calendar lendingTime, Calendar startTime, Calendar endTime, String acno);

	/**
	 * 实验室预约作废
	 * @param labReservationId 实验室预约id
	 * @return 成功的字符串
	 * @author 黄保钱 2019-1-11
	 */
    String obsoleteLabReservation(Integer labReservationId);

	/**
	 * Description 根据预约获取该预约的节次
	 * @param labReservation 预约
	 * @return 节次列表
	 * @author 黄保钱 2019-1-20
	 */
	List<List<Integer>> getSectionsList(LabReservation labReservation);

	/**
	 * Description 获取实验室预约审核拒绝日志列表
	 * @param firstResult 偏移量
	 * @param maxResult 获取的最大数据数量
	 * @param labName 实验室名称
	 * @return 实验室预约审核拒绝日志列表
	 * @author 黄保钱 2019-1-20
	 */
	List<AuditRefuseBackup> getAuditRefuseBackupForLabReservation(Integer firstResult, Integer maxResult, String labName);

	/**
	 * Description 获取实验室预约审核拒绝日志总数量
	 * @param labName 实验室名称
	 * @return 实验室预约审核拒绝日志总数量
	 * @author 黄保钱 2019-1-20
	 */
	Integer getCountAuditRefuseBackupForLabReservation(String labName);

	/**
	 * 实验室预约取消
	 * @param labReservationId 实验室预约id
	 * @return 成功的字符串
	 * @author 黄保钱 2019-5-8
	 */
	String cancelLabReservation(Integer labReservationId);

	/**
	 * 实验室预约取消
	 * @param labReservationId 实验室预约id
	 * @return 成功的字符串
	 * @author 黄保钱 2019-5-8
	 */
	String updateCancelLabReservation(Integer labReservationId);

	List<User> getNextUsers(String nextAuth, String businessAppUid);
}