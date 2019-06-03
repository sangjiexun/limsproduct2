package net.zjcclims.service.cmsshow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.zjcclims.common.LabAttendance;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.dao.CommonHdwlogDAO;
import net.zjcclims.dao.LabCenterDAO;
import net.zjcclims.dao.LabReservationDAO;
import net.zjcclims.dao.LabRoomDAO;
import net.zjcclims.dao.LabRoomDeviceDAO;
import net.zjcclims.dao.LabRoomDeviceReservationDAO;
import net.zjcclims.dao.SchoolAcademyDAO;
import net.zjcclims.dao.UserDAO;
import net.zjcclims.domain.CDictionary;
import net.zjcclims.domain.CommonHdwlog;
import net.zjcclims.domain.LabCenter;
import net.zjcclims.domain.LabReservation;
import net.zjcclims.domain.LabReservationTimeTable;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.LabRoomDevice;
import net.zjcclims.domain.LabRoomDeviceReservation;
import net.zjcclims.domain.SchoolAcademy;
import net.zjcclims.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import excelTools.Labreservationlist;



@Service("CmsShowZjccService")
public class CmsShowZjccServiceImpl implements  CmsShowZjccService {

	@Autowired private LabRoomDAO labRoomDAO;
	@Autowired private LabRoomDeviceDAO labRoomDeviceDAO;
	
	
	/*************************************************************************************
	 * Description 门户-实训室预约
	 * 
	 * @author 陈乐为
	 * @date 2017-6-19
	 *************************************************************************************/
	@Override
	public List<LabRoom> findLabRoomByQuery(LabRoom labRoom, int currpage, int pageSize) {
		String sql = "select l from LabRoom l where 1=1 and l.labRoomActive=1 and l.labRoomReservation=1 ";
		if (labRoom.getLabRoomName() != null && !labRoom.getLabRoomName().equals("")) {
			sql += " and  (l.labRoomName like '%" + labRoom.getLabRoomName() + "%'  or l.labRoomNumber like '%"
					+ labRoom.getLabRoomName() + "%')";
		}
		return labRoomDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
	}
	
	/****************************************************************************
	 *Description：查询某一实验中心下有设备的实验室
	 *
	 *@param isReservation 是否可预约
	 *@author：邵志峰
	 *@date：2017-06-22
	 ****************************************************************************/
	public List<LabRoom> findLabRoomWithDevices(Integer isReservation){
		String sql="select distinct m from LabRoom m inner join m.labRoomDevices ld where 1=1 ";
		sql += " and m.isUsed = 1";
		if(isReservation != null && isReservation == 1)//isReservation为1时可预约
		{
			 sql += "and m.labRoomActive = 1";
		}
		//sql+=" and m.labAnnex.labCenter.id="+cid;
		return labRoomDAO.executeQuery(sql, 0,-1);
	}
	
	/****************************************************************************
	 *Description：根据查询条件查询出所有的实验分室设备
	 *
	 *@param: isReservation 是否可预约
	 *@author：邵志峰
	 *@date： 2017-06-22
	 ****************************************************************************/
	@Override
	public int findAllLabRoomDeviceNew(LabRoomDevice device,Integer isReservation) {
		String sql="select count(d) from LabRoomDevice d where 1=1 ";
		//实验室
		if(device.getLabRoom()!=null){
			if(device.getLabRoom().getId()!=null&&!device.getLabRoom().getId().equals("")){
				sql+=" and d.labRoom.id="+device.getLabRoom().getId();
			}
		}
		//设备
		if(device.getSchoolDevice()!=null){
			//设备编号
			if(device.getSchoolDevice().getDeviceNumber()!=null&&!device.getSchoolDevice().getDeviceNumber().equals("")){
				sql+=" and d.schoolDevice.deviceNumber like '%"+device.getSchoolDevice().getDeviceNumber()+"%'";
			}
			//设备名称
			if(device.getSchoolDevice().getDeviceName()!=null&&!device.getSchoolDevice().getDeviceName().equals("")){
				sql+=" and d.schoolDevice.deviceName like '%"+device.getSchoolDevice().getDeviceName()+"%'";
			}
			if(device.getUser()!=null){
				//设备管理员
				if(device.getUser().getUsername()!=null&&!device.getUser().getUsername().equals("")){
					sql+="and d.user.username  like '%"+device.getUser().getUsername()+"%'";
				}
			}
		}
		if(device != null && device.getCDictionaryByAllowAppointment() != null 
				&& device.getCDictionaryByAllowAppointment().getCNumber() != null
				&& !device.getCDictionaryByAllowAppointment().getCNumber().equals("")){
			if(device.getCDictionaryByAllowAppointment().getCNumber().equals("1"))
			{
				sql += " and d.CDictionaryByAllowAppointment.CNumber ='"+device.getCDictionaryByAllowAppointment().getCNumber()+"'";
			}
			else{
				sql += " and (d.CDictionaryByAllowAppointment.CNumber !='1' or d.CDictionaryByAllowAppointment is null)";
			}
		}
		if(isReservation != null && isReservation == 1){
			sql += " and d.labRoom.labRoomActive=1";
		}
		/*if(cid!=null){
			sql+=" and d.labRoom.labCenter.id="+cid;
		}*/
		
		
		return ((Long) labRoomDeviceDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	
	/****************************************************************************
	 *Description：根据中心id查询该中心存放有设备的实验室（分页）
	 *
	 *@param：isReservation 是否可预约
	 *@author：邵志峰
	 *@date：2017-06-22
	 ****************************************************************************/
	@Override
	public List<LabRoom> findLabRoomWithDevices(LabRoomDevice device, Integer page, int pageSize, Integer isReservation) {
		String sql="select distinct m from LabRoom m inner join m.labRoomDevices lr where 1=1 ";
		//sql+=" and m.labAnnex.labCenter.id="+cid;
		if(device.getLabRoom()!=null){
			if(device.getLabRoom().getId()!=null&&!device.getLabRoom().getId().equals("")){
				sql+=" and m.id="+device.getLabRoom().getId();
			}
		}
		if(device != null && device.getCDictionaryByAllowAppointment() != null 
				&& device.getCDictionaryByAllowAppointment().getCNumber() != null
				&& !device.getCDictionaryByAllowAppointment().getCNumber().equals("")){
			sql += " and lr.CDictionaryByAllowAppointment.CNumber ="+device.getCDictionaryByAllowAppointment().getCNumber();
		}
		if(isReservation != null && isReservation == 1){
			sql += " and m.labRoomActive = 1";
		}
		return labRoomDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
	}
	
	/****************************************************************************
	 *Description：根据查询条件查询出所有的实验分室设备并分页
	 *
	 *@param isReservation 是否可预约
	 *@author：邵志峰
	 *@date：2017-06-22
	 ****************************************************************************/
	@Override
	public List<LabRoomDevice> findAllLabRoomDeviceNew(LabRoomDevice device,
			Integer page, int pageSize,Integer isReservation) {
		String sql="select d from LabRoomDevice d where 1=1 ";
		if(device.getLabRoom()!=null){
			if(device.getLabRoom().getId()!=null&&!device.getLabRoom().getId().equals("")){
				sql+=" and d.labRoom.id="+device.getLabRoom().getId();
			}
		}
		//设备
		if(device.getSchoolDevice()!=null){
			//设备编号
			if(device.getSchoolDevice().getDeviceNumber()!=null&&!device.getSchoolDevice().getDeviceNumber().equals("")){
				sql+="and d.schoolDevice.deviceNumber like '%"+device.getSchoolDevice().getDeviceNumber()+"%'";
			}
			//设备名称
			if(device.getSchoolDevice().getDeviceName()!=null&&!device.getSchoolDevice().getDeviceName().equals("")){
				sql+="and d.schoolDevice.deviceName like '%"+device.getSchoolDevice().getDeviceName()+"%'";
			}
			if(device.getUser()!=null){
				//设备管理员
				if(device.getUser().getUsername()!=null&&!device.getUser().getUsername().equals("")){
					sql+="and d.user.username  like '%"+device.getUser().getUsername()+"%'";
				}
			}
		}
		if(device != null && device.getCDictionaryByAllowAppointment() != null 
				&& device.getCDictionaryByAllowAppointment().getCNumber() != null
				&& !device.getCDictionaryByAllowAppointment().getCNumber().equals("")){
			if(device.getCDictionaryByAllowAppointment().getCNumber().equals("1"))
			{
				sql += " and d.CDictionaryByAllowAppointment.CNumber ='"+device.getCDictionaryByAllowAppointment().getCNumber()+"'";
			}
			else{
				sql += " and (d.CDictionaryByAllowAppointment is null or (d.CDictionaryByAllowAppointment is not null and d.CDictionaryByAllowAppointment.CNumber = '2'))";
			}
		}
		/*if(cid!=null){
			sql+=" and d.labRoom.labCenter.id="+cid;
		}*/
		
		if(isReservation != null && isReservation == 1){
			sql += " and d.labRoom.labRoomActive = 1";
		}
		sql+=" order by d.schoolDevice.deviceName";
		return labRoomDeviceDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
	}
	
	/****************************************************************************
	 *Description：查出某一中心下的所有设备管理员
	 *
	 *@author：邵志峰
	 *@date：2017-06-22
	 ****************************************************************************/
	public Map<String, String> findDeviceManageCnamerByCid(){
		Map<String, String> userMap = new HashMap<String, String>();
		//创造一个空的labRoomDevice对象
		LabRoomDevice device = new LabRoomDevice();
		List<LabRoomDevice> devices = findAllLabRoomDeviceNew(device,1,-1,1);
		for (LabRoomDevice labRoomDevice : devices) {
			if (labRoomDevice.getUser()!=null&&labRoomDevice.getUser().getUsername()!=null
					&&!labRoomDevice.getUser().getUsername().equals("")) {
				userMap.put(labRoomDevice.getUser().getUsername(), 
						labRoomDevice.getUser().getCname());
			}
		}
		return userMap;
	}
}