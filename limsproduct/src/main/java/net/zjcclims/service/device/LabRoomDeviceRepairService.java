package net.zjcclims.service.device;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.LabRoomDeviceRepair;
import net.zjcclims.domain.LabRoomDeviceReservation;
import net.zjcclims.domain.LabWorkerTraining;
import net.zjcclims.domain.SchoolDevice;

/**
 * Spring service that handles CRUD requests for LabRoomDeviceRepair entities
 * 
 */
public interface LabRoomDeviceRepairService {

	/**
	 * Delete an existing LabRoomDeviceRepair entity
	 * 
	 */
	public void deleteLabRoomDeviceRepair(LabRoomDeviceRepair labroomdevicerepair);

	/**
	 */
	public LabRoomDeviceRepair findLabRoomDeviceRepairByPrimaryKey(Integer id_3);

	/**
	 * Save an existing LabRoomDeviceRepair entity
	 * 
	 */

	public void saveLabRoomDeviceRepair(LabRoomDeviceRepair labRoomDeviceRepair);
	/**
	 * Load an existing LabRoomDeviceRepair entity
	 * 
	 */
	public Set<LabRoomDeviceRepair> loadLabRoomDeviceRepairs();

	/**
	 * Save an existing SchoolDevice entity
	 * 
	 */
	public LabRoomDeviceRepair saveLabRoomDeviceRepairSchoolDevice(Integer id_4, SchoolDevice related_schooldevice);

	/**
	 * Delete an existing SchoolDevice entity
	 * 
	 */
	public LabRoomDeviceRepair deleteLabRoomDeviceRepairSchoolDevice(Integer labroomdevicerepair_id_4, String related_schooldevice_deviceNumber);

	/**
	 * Return all LabRoomDeviceRepair entity
	 * 
	 */
	public List<LabRoomDeviceRepair> findAllLabRoomDeviceRepairs(Integer startResult, Integer maxRows);

	/**
	 * Return a count of all LabRoomDeviceRepair entity
	 * 
	 */
	public Integer countLabRoomDeviceRepairs();
	
	
	
	
	
	public List<LabRoomDeviceRepair> loadLabRoomDeviceRepair(LabRoomDeviceRepair labRoomDeviceRepair,
			int flag,int currpage, int pageSize);
	
	public List<LabRoomDeviceRepair> findLabRoomDeviceRepairByLabRoomDeviceRepair(String td,int page,int pageSize);
	public List<LabRoomDeviceRepair> findLabRoomDeviceRepairByLabRoomDeviceRepair(String td);
	
	/**
	 * 查找指定学院的设备
	 * @param academyNumber 学院编号
	 * @author 何立友
	 * 2014-09-17
	 */
	public List<SchoolDevice> getSchoolDevicesByAcademy(String academyNumber);

	/**
	 * 根据学院查找实验室分室
	 * @param academyNumber 学院编号(academyNumber为null或者空时，返回所有实验室分室)
	 * @author hely
	 * 2014.08.20
	 */
	public List<LabRoom> getLabRoomByAcademy(String academyNumber);
	/***************************** 
	*Description 设备维修登记详情
	*
	*@author:周志辉
	*@param:
	*@date:2018.08.21
	*****************************/
	public LabRoomDeviceRepair findLabRoomDeviceRepairDetailByPrimaryKey(Integer labRoomDeviceRepairId);
	/****************************************************************************
	 * 功能：上传附件
	 * 作者：周志辉
	 ****************************************************************************/
	public void labRoomDeviceRepairDocumentUpload(HttpServletRequest request,
			HttpServletResponse response, Integer id,int type);

}