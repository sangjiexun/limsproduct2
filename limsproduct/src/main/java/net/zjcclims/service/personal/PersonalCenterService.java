package net.zjcclims.service.personal;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import net.zjcclims.domain.CActive;
//import net.zjcclims.domain.CLabAnnexType;
//import net.zjcclims.domain.CLabRoomType;
import net.zjcclims.domain.LabReservation;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.LabRoomAgent;
//import net.zjcclims.domain.LabRoomDeviceReservation;
import net.zjcclims.dao.OperationItemDAO;
import net.zjcclims.domain.OperationItem;



public interface PersonalCenterService {

	public List<LabReservation> findLabreservationmanage(String username) ;

	/****************************************************************************
	 * 功能：根据当前用户查找实验室设备预约 前4条的记录
	 * 作者：方正
	 ****************************************************************************/

	/*public List<LabRoomDeviceReservation> findAllLabRoomDeviceReservationByUsername(String username);
	
	public List<LabRoomDeviceReservation> getAllLabRoomDeviceReservationByUsername(String username);
	
	public List<LabRoomDeviceReservation> getAllLabRoomDeviceReservationByUsername(
			String username ,int page ,int pageSize);
	
	public List<LabRoomDeviceReservation> getAllLabRoomDeviceReservationByLabRoomDeviceReservation(
			LabRoomDeviceReservation labRoomDeviceReservation);
	
	public List<LabRoomDeviceReservation> getAllLabRoomDeviceReservationByLabRoomDeviceReservation(
			LabRoomDeviceReservation labRoomDeviceReservation , int page , int pageSize);*/
	
	public List<OperationItem> getoperationItemByUserId(String username,int page,int pageSize);
	
	public List<OperationItem> getoperationItemByUsername(String username);
	public List<OperationItem> getcoperationItemByUsername(String username);
	public List<OperationItem> getoperationItemByUserId(String username) ;
	
	public List<OperationItem> getoperationItemByOperationItem(OperationItem operationItem,int page,int pageSize);
	
	public List<OperationItem> getoperationItemByOperationItem(OperationItem operationItem);
	
	/*public List<LabRoomDeviceReservation> getAllLabRoomDeviceReservationBy(
			LabRoomDeviceReservation labRoomDeviceReservation);
	public List<LabRoomDeviceReservation> getAllLabRoomDeviceReservationBy(
			LabRoomDeviceReservation labRoomDeviceReservation ,int page ,int pageSize);*/
}