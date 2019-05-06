package net.zjcclims.service.cmsshow;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.zjcclims.common.LabAttendance;
import net.zjcclims.domain.CommonHdwlog;
import net.zjcclims.domain.LabReservation;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.LabRoomDevice;
import net.zjcclims.domain.LabRoomDeviceReservation;

import org.springframework.transaction.annotation.Transactional;

import excelTools.Labreservationlist;



public interface CmsShowZjccService {
	/*************************************************************************************
	 * Description 门户-实训室预约
	 * 
	 * @author 陈乐为
	 * @date 2017-6-19
	 *************************************************************************************/
	public List<LabRoom> findLabRoomByQuery(LabRoom labRoom, int currpage, int pageSize);
	
	/****************************************************************************
	 *Description：查询某一实验中心下有设备的实验室
	 *
	 *@param isReservation 是否可预约
	 *@author：邵志峰
	 *@date：2017-06-22
	 ****************************************************************************/
	public List<LabRoom> findLabRoomWithDevices(Integer isReservation);
	
	/****************************************************************************
	 *Description：根据查询条件查询出所有的实验分室设备
	 *
	 *@param: isReservation 是否可预约
	 *@author：邵志峰
	 *@date： 2017-06-21
	 ****************************************************************************/
	public int findAllLabRoomDeviceNew(LabRoomDevice device,Integer isReservation);
	
	/****************************************************************************
	 *Description：根据中心id查询该中心存放有设备的实验室（分页）
	 *
	 *@param：isReservation 是否可预约
	 *@author：邵志峰
	 *@date：2017-06-22
	 ****************************************************************************/
	public List<LabRoom> findLabRoomWithDevices(LabRoomDevice device, Integer page, int pageSize, Integer isReservation);
	
	/****************************************************************************
	 *Description：根据查询条件查询出所有的实验分室设备并分页
	 *
	 *@param isReservation 是否可预约
	 *@author：邵志峰
	 *@date：2017-06-22
	 ****************************************************************************/
	public List<LabRoomDevice> findAllLabRoomDeviceNew(LabRoomDevice device,
			Integer page, int pageSize,Integer isReservation);
	
	/****************************************************************************
	 *Description：查出某一中心下的所有设备管理员
	 *
	 *@author：邵志峰
	 *@date：2017-06-22
	 ****************************************************************************/
	public Map<String, String> findDeviceManageCnamerByCid();
}