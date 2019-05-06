package net.zjcclims.service.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;

import net.zjcclims.service.common.ShareService;
import net.zjcclims.dao.LabRoomDeviceDAO;
import net.zjcclims.dao.SchoolAcademyDAO;
import net.zjcclims.dao.SchoolCourseDAO;
import net.zjcclims.dao.SchoolDeviceDAO;
import net.zjcclims.dao.SystemBuildDAO;
import net.zjcclims.dao.SystemRoomDAO;
import net.zjcclims.dao.TimetableAppointmentDAO;
import net.zjcclims.dao.UserDAO;
import net.zjcclims.domain.LabRoomDevice;
import net.zjcclims.domain.SchoolAcademy;
import net.zjcclims.domain.SchoolDevice;
import net.zjcclims.domain.SystemBuild;
import net.zjcclims.domain.SystemRoom;
import net.zjcclims.domain.TimetableAppointment;
import net.zjcclims.domain.User;

@Service("ShareDataService")
public class ShareDataServiceImpl implements ShareDataService {

	@Autowired
	private SystemBuildDAO systemBuildDAO;
	
	@Autowired
	private SystemRoomDAO systemRoomDAO;
	
	@Autowired
	private SchoolDeviceDAO schoolDeviceDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private SchoolAcademyDAO schoolAcademyDAO;
	
	@Autowired
	private TimetableAppointmentDAO timetableAppointmentDAO;
	
	@Autowired
	private ShareService shareService;
	@Autowired
	private LabRoomDeviceDAO labRoomDeviceDAO;
	
	@Override
	public List<SystemBuild> findSystemBuildBySystemBuild(SystemBuild systemBuild) {
		// TODO Auto-generated method stub
		String sql="select l from SystemBuild l where 1=1";
		if(systemBuild!=null){
		if(systemBuild.getBuildName()!=null&&!systemBuild.getBuildName().equalsIgnoreCase("")){
		
			sql+=" and l.buildName like '%"+systemBuild.getBuildName()+"%'";
		}
		}
		/*sql+=" order by l.CConsumables desc";*/
		//给语句添加分页机制；
		List<SystemBuild> systemBuilds=systemBuildDAO.executeQuery(sql,0,-1);
		return systemBuilds;
	}
	
	@Override
	public int getCountSystemBuildBySystemBuild(SystemBuild systemBuild) {
		// TODO Auto-generated method stub
		String sql="select count(*) from SystemBuild l where 1=1";
		if(systemBuild!=null){
			if(systemBuild.getBuildName()!=null&&!systemBuild.getBuildName().equalsIgnoreCase("")){
				
				sql+=" and l.buildName like '%"+systemBuild.getBuildName()+"%'";
			}
		}
		/*sql+=" order by l.CConsumables desc";*/
		//给语句添加分页机制；
		//List<SystemBuild> systemBuilds=systemBuildDAO.executeQuery(sql,0,-1);
		return ((Long) systemBuildDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	
	@Override
	public List<SystemBuild> findSystemBuildBySystemBuild(SystemBuild systemBuild, int page,
			int pageSize) {
		// TODO Auto-generated method stub
				String sql="select l from SystemBuild l where 1=1";
				if(systemBuild!=null){
				if(systemBuild.getBuildName()!=null&&!systemBuild.getBuildName().equalsIgnoreCase("")){
				
					sql+=" and l.buildName like '%"+systemBuild.getBuildName()+"%'";
				}
				}
		
		List<SystemBuild> systemBuilds=systemBuildDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
		return systemBuilds;
	}
	
	@Override
	public List<SystemRoom> findSystemRoomBySystemRoom(SystemRoom systemRoom) {
		// TODO Auto-generated method stub
		String sql="select l from SystemRoom l where 1=1";
		if(systemRoom.getSystemBuild()!=null){
		if(systemRoom.getSystemBuild().getBuildName()!=null&&!systemRoom.getSystemBuild().getBuildName().equalsIgnoreCase("")){
		
			sql+=" and l.systemBuild.buildName like '%"+systemRoom.getSystemBuild().getBuildName()+"%'";
		}
		}
		/*sql+=" order by l.CConsumables desc";*/
		//给语句添加分页机制；
		List<SystemRoom> systemRooms=systemRoomDAO.executeQuery(sql,0,-1);
		return systemRooms;
	}
	
	@Override
	public int getCountSystemRoomBySystemRoom(SystemRoom systemRoom) {
		// TODO Auto-generated method stub
		String sql="select count(*) from SystemRoom l where 1=1";
		if(systemRoom.getSystemBuild()!=null){
			if(systemRoom.getSystemBuild().getBuildName()!=null&&!systemRoom.getSystemBuild().getBuildName().equalsIgnoreCase("")){
				
				sql+=" and l.systemBuild.buildName like '%"+systemRoom.getSystemBuild().getBuildName()+"%'";
			}
		}
		/*sql+=" order by l.CConsumables desc";*/
		//给语句添加分页机制；
		//List<SystemRoom> systemRooms=systemRoomDAO.executeQuery(sql,0,-1);
		return ((Long) systemBuildDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	
	@Override
	public List<SystemRoom> findSystemRoomBySystemRoom(SystemRoom systemRoom, int page,
			int pageSize) {
		// TODO Auto-generated method stub
				String sql="select l from SystemRoom l where 1=1";
				if(systemRoom.getSystemBuild()!=null){
				if(systemRoom.getSystemBuild().getBuildName()!=null&&!systemRoom.getSystemBuild().getBuildName().equalsIgnoreCase("")){
				
					sql+=" and l.systemBuild.buildName like '%"+systemRoom.getSystemBuild().getBuildName()+"%'";
				}
				}
		
		List<SystemRoom> systemRooms=systemRoomDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
		return systemRooms;
	}
	
	@Override
	public List<SchoolDevice> findSchoolDeviceBySchoolDevice(SchoolDevice schoolDevice) {
		// TODO Auto-generated method stub
		String sql="select l from SchoolDevice l where 1=1";
		if(schoolDevice!=null){
		if(schoolDevice.getDeviceName()!=null&&!schoolDevice.getDeviceName().equalsIgnoreCase("")){
		
			sql+=" and l.deviceName like '%"+schoolDevice.getDeviceName()+"%'";
		}
		}
		/*sql+=" order by l.CConsumables desc";*/
		//给语句添加分页机制；
		List<SchoolDevice> schoolDevices=schoolDeviceDAO.executeQuery(sql,0,-1);
		return schoolDevices;
	}
	
	@Override
	public int getCountSchoolDeviceBySchoolDevice(SchoolDevice schoolDevice) {
		// TODO Auto-generated method stub
		String sql="select count(*) from SchoolDevice l where 1=1";
		if(schoolDevice!=null){
		if(schoolDevice.getDeviceName()!=null&&!schoolDevice.getDeviceName().equalsIgnoreCase("")){
		
			sql+=" and l.deviceName like '%"+schoolDevice.getDeviceName()+"%'";
		}
		if(schoolDevice.getDeviceNumber()!=null&&!schoolDevice.getDeviceNumber().equalsIgnoreCase("")){
			
			sql+=" and l.deviceNumber like '%"+schoolDevice.getDeviceNumber()+"%'";
		}
		}
		/*sql+=" order by l.CConsumables desc";*/
		//给语句添加分页机制；
		//List<SchoolDevice> schoolDevices=schoolDeviceDAO.executeQuery(sql,0,-1);
		return ((Long) schoolDeviceDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	
	@Override
	public int getCountLabRoomDevice(LabRoomDevice labRoomDevice) {
		// TODO Auto-generated method stub
		String sql="select count(*) from LabRoomDevice l where 1=1";
		if(labRoomDevice!=null){
		if(labRoomDevice.getSchoolDevice() != null && labRoomDevice.getSchoolDevice().getDeviceName()!=null&&!labRoomDevice.getSchoolDevice().getDeviceName().equalsIgnoreCase("")){
		
			sql+=" and l.schoolDevice.deviceName like '%"+labRoomDevice.getSchoolDevice().getDeviceName()+"%'";
		}
		if(labRoomDevice.getSchoolDevice() != null && labRoomDevice.getSchoolDevice().getDeviceNumber()!=null&&!labRoomDevice.getSchoolDevice().getDeviceNumber().equalsIgnoreCase("")){
			
			sql+=" and l.schoolDevice.deviceNumber like '%"+labRoomDevice.getSchoolDevice().getDeviceNumber()+"%'";
		}
		if(labRoomDevice.getLabRoom() != null && labRoomDevice.getLabRoom().getId()!=null&&!labRoomDevice.getLabRoom().getId().equals("")){
			
			sql+=" and l.labRoom.id = "+labRoomDevice.getLabRoom().getId();
		}if(labRoomDevice.getCDictionaryByDeviceStatus()!=null && labRoomDevice.getCDictionaryByDeviceStatus().getId()!=null && !labRoomDevice.getCDictionaryByDeviceStatus().getId().equals("")){
			
			sql+="and l.CDictionaryByDeviceStatus.id="+labRoomDevice.getCDictionaryByDeviceStatus().getId();
			
		}if(labRoomDevice.getSchoolDevice()!=null && labRoomDevice.getSchoolDevice().getUserByKeepUser()!=null && labRoomDevice.getSchoolDevice().getUserByKeepUser().getUsername()!=null && !labRoomDevice.getSchoolDevice().getUserByKeepUser().getUsername().equals("")){
			
			sql+="and l.schoolDevice.userByKeepUser.username="+labRoomDevice.getSchoolDevice().getUserByKeepUser().getUsername();
		
		}
		}
		/*sql+=" order by l.CConsumables desc";*/
		//给语句添加分页机制；
		//List<SchoolDevice> schoolDevices=schoolDeviceDAO.executeQuery(sql,0,-1);
		return ((Long) labRoomDeviceDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	
	@Override
	public List<LabRoomDevice> findLabRoomDevice(LabRoomDevice labRoomDevice, Integer page, Integer pageSize) {
		// TODO Auto-generated method stub
		String sql="select l from LabRoomDevice l  where 1=1";
		if(labRoomDevice!=null){
		if(labRoomDevice.getSchoolDevice() != null && labRoomDevice.getSchoolDevice().getDeviceName()!=null&&!labRoomDevice.getSchoolDevice().getDeviceName().equalsIgnoreCase("")){
		
			sql+=" and l.schoolDevice.deviceName like '%"+labRoomDevice.getSchoolDevice().getDeviceName()+"%'";
		}
		if(labRoomDevice.getSchoolDevice() != null && labRoomDevice.getSchoolDevice().getDeviceNumber()!=null&&!labRoomDevice.getSchoolDevice().getDeviceNumber().equalsIgnoreCase("")){
			
			sql+=" and l.schoolDevice.deviceNumber like '%"+labRoomDevice.getSchoolDevice().getDeviceNumber()+"%'";
		}
		if(labRoomDevice.getLabRoom() != null && labRoomDevice.getLabRoom().getId()!=null&&!labRoomDevice.getLabRoom().getId().equals("")){
			
			sql+=" and l.labRoom.id = "+labRoomDevice.getLabRoom().getId();
		}
		if(labRoomDevice.getCDictionaryByDeviceStatus()!=null && labRoomDevice.getCDictionaryByDeviceStatus().getId()!=null && !labRoomDevice.getCDictionaryByDeviceStatus().getId().equals("")){
			
			sql+="and l.CDictionaryByDeviceStatus.id="+labRoomDevice.getCDictionaryByDeviceStatus().getId();
			
		}if(labRoomDevice.getSchoolDevice()!=null && labRoomDevice.getSchoolDevice().getUserByKeepUser()!=null && labRoomDevice.getSchoolDevice().getUserByKeepUser().getUsername()!=null && !labRoomDevice.getSchoolDevice().getUserByKeepUser().getUsername().equals("")){
			sql+="and l.schoolDevice.userByKeepUser.username="+labRoomDevice.getSchoolDevice().getUserByKeepUser().getUsername();
		}
		}
		/*sql+=" order by l.CConsumables desc";*/
		//给语句添加分页机制；
		List<LabRoomDevice> schoolDevices=labRoomDeviceDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
		return schoolDevices;
	}
	
	@Override
	public List<SchoolDevice> findSchoolDeviceBySchoolDevice(SchoolDevice schoolDevice, int page,
			int pageSize) {
		// TODO Auto-generated method stub
				String sql="select l from SchoolDevice l where 1=1";
				if(schoolDevice!=null){
				if(schoolDevice.getDeviceName()!=null&&!schoolDevice.getDeviceName().equalsIgnoreCase("")){
				
					sql+=" and l.deviceName like '%"+schoolDevice.getDeviceName()+"%'";
				}
				if(schoolDevice.getDeviceNumber()!=null&&!schoolDevice.getDeviceNumber().equalsIgnoreCase("")){
					
					sql+=" and l.deviceNumber like '%"+schoolDevice.getDeviceNumber()+"%'";
				}
				}
		
		List<SchoolDevice> schoolDevices=schoolDeviceDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
		return schoolDevices;
	}
	
	@Override
	public List<User> findUserByUser(User user) {
		// TODO Auto-generated method stub
		String sql="select l from User l where 1=1";
		if(user!=null){
		if(user.getCname()!=null&&!user.getCname().equalsIgnoreCase("")){
		
			sql+=" and l.cname like '%"+user.getCname()+"%'";
		}
		}
		/*sql+=" order by l.CConsumables desc";*/
		//给语句添加分页机制；
		List<User> users=userDAO.executeQuery(sql,0,-1);
		return users;
	}
	
	@Override
	public List<User> findUserByUser(User user, int page,
			int pageSize) {
		// TODO Auto-generated method stub
				String sql="select l from User l where 1=1";
				if(user!=null){
				if(user.getCname()!=null&&!user.getCname().equalsIgnoreCase("")){
				
					sql+=" and l.cname like '%"+user.getCname()+"%'";
				}
				}
		
		List<User> users=userDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
		return users;
	}
	
	@Override
	public List<SchoolAcademy> findSchoolAcademyBySchoolAcademy(SchoolAcademy schoolAcademy) {
		// TODO Auto-generated method stub
		String sql="select sa from SchoolAcademy sa where 1=1 ";
		if(schoolAcademy!=null){
		if(schoolAcademy.getAcademyName()!=null&&!schoolAcademy.getAcademyName().equalsIgnoreCase("")){
		
			sql+=" and sa.academyName like '%"+schoolAcademy.getAcademyName()+"%'";
		}
		}
		/*sql+=" order by l.CConsumables desc";*/
		//给语句添加分页机制；
		List<SchoolAcademy> schoolAcademys=schoolAcademyDAO.executeQuery(sql,0,-1);
		return schoolAcademys;
	}
	
	@Override
	public int getCountSchoolAcademyBySchoolAcademy(SchoolAcademy schoolAcademy) {
		// TODO Auto-generated method stub
		String sql="select count(*) from SchoolAcademy sa where 1=1 ";
		if(schoolAcademy!=null){
			if(schoolAcademy.getAcademyName()!=null&&!schoolAcademy.getAcademyName().equalsIgnoreCase("")){
				
				sql+=" and sa.academyName like '%"+schoolAcademy.getAcademyName()+"%'";
			}
		}
		/*sql+=" order by l.CConsumables desc";*/
		//给语句添加分页机制；
		//List<SchoolAcademy> schoolAcademys=schoolAcademyDAO.executeQuery(sql,0,-1);
		return ((Long) schoolAcademyDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	
	@Override
	public List<SchoolAcademy> findSchoolAcademyBySchoolAcademy(SchoolAcademy schoolAcademy, int page,
			int pageSize) {
		// TODO Auto-generated method stub
				String sql="select sa from SchoolAcademy sa where 1=1 ";
				if(schoolAcademy!=null){
				if(schoolAcademy.getAcademyName()!=null&&!schoolAcademy.getAcademyName().equalsIgnoreCase("")){
				
					sql+=" and sa.academyName like '%"+schoolAcademy.getAcademyName()+"%'";
				}
				}
		
		List<SchoolAcademy> schoolAcademys=schoolAcademyDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
		return schoolAcademys;
	}
	
	@Override
	public List<SchoolAcademy> findSchoolAcademyBySchoolAcademy1(SchoolAcademy schoolAcademy) {
		// TODO Auto-generated method stub
		String sql="select sa from SchoolAcademy sa where 1=1";
		if(schoolAcademy!=null){
		if(schoolAcademy.getAcademyName()!=null&&!schoolAcademy.getAcademyName().equalsIgnoreCase("")){
		
			sql+=" and sa.academyName like '%"+schoolAcademy.getAcademyName()+"%'";
		}
		}
		String sql1="select sa from SchoolAcademy sa where sa.academyNumber like '02__' and sa.academyNumber between '0201' and '0213'";
		List<SchoolAcademy> findAcademys=schoolAcademyDAO.executeQuery(sql1,0,-1);
		/*sql+=" order by l.CConsumables desc";*/
		//给语句添加分页机制；
		List<SchoolAcademy> schoolAcademys=schoolAcademyDAO.executeQuery(sql,0,-1);
		schoolAcademys.removeAll(findAcademys);
		return schoolAcademys;
	}
	
	@Override
	public int getCountSchoolAcademyBySchoolAcademy1(SchoolAcademy schoolAcademy) {
		// TODO Auto-generated method stub
		String sql="select count(*) from SchoolAcademy sa where 1=1";
		if(schoolAcademy!=null){
			if(schoolAcademy.getAcademyName()!=null&&!schoolAcademy.getAcademyName().equalsIgnoreCase("")){
				
				sql+=" and sa.academyName like '%"+schoolAcademy.getAcademyName()+"%'";
			}
		}
		String sql1="select count(*) from SchoolAcademy sa where sa.academyNumber like '02__' and sa.academyNumber between '0201' and '0213'";
		//List<SchoolAcademy> findAcademys=schoolAcademyDAO.executeQuery(sql1,0,-1);
		/*sql+=" order by l.CConsumables desc";*/
		//给语句添加分页机制；
		//List<SchoolAcademy> schoolAcademys=schoolAcademyDAO.executeQuery(sql,0,-1);
		//schoolAcademys.removeAll(findAcademys);
		int schoolAcademysCount = ((Long) schoolAcademyDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
		int findAcademysCount = ((Long) schoolAcademyDAO.createQuerySingleResult(sql1).getSingleResult()).intValue();
		return (schoolAcademysCount-findAcademysCount);
	}
	
	@Override
	public List<SchoolAcademy> findSchoolAcademyBySchoolAcademy1(SchoolAcademy schoolAcademy, int page,
			int pageSize) {
		// TODO Auto-generated method stub
				String sql="select sa from SchoolAcademy sa where 1=1";
				if(schoolAcademy!=null){
				if(schoolAcademy.getAcademyName()!=null&&!schoolAcademy.getAcademyName().equalsIgnoreCase("")){
				
					sql+=" and sa.academyName like '%"+schoolAcademy.getAcademyName()+"%'";
				}
				}
				String sql1="select sa from SchoolAcademy sa where sa.academyNumber like '02__' and sa.academyNumber between '0201' and '0213'";
				List<SchoolAcademy> findAcademys=schoolAcademyDAO.executeQuery(sql1,0,-1);	
		
		List<SchoolAcademy> schoolAcademys=schoolAcademyDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
		schoolAcademys.removeAll(findAcademys);
		return schoolAcademys;
	}
	
	/*************************************************************************************
	 * @內容：查看所有的预约的列表安排
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<TimetableAppointment> getTimetableAppointmentsByQuery(TimetableAppointment timetableAppointment,
			int status, int curr, int size) {
		// 创建根据学期来查询课程；
		StringBuffer sql = new StringBuffer(
				"select c from TimetableAppointment c where 1=1 and c.schoolCourse.schoolAcademy.academyNumber like '%"	+ shareService.getUserDetail().getSchoolAcademy().getAcademyNumber() + "%'");
		if (status != -1) {
			sql.append(" and c.status = " + status);
		}
		if (timetableAppointment.getSchoolCourse()!=null&&timetableAppointment.getSchoolCourse().getCourseNo() != null) {
			sql.append(" and c.schoolCourse.courseNo like '%" + timetableAppointment.getSchoolCourse().getCourseNo() + "%'");
		}
		// 按照课程排序
		sql.append(" order by c.schoolCourse.courseNo,c.courseCode ,c.weekday desc ");
		List<TimetableAppointment> timetableAppointments = timetableAppointmentDAO
				.executeQuery(sql.toString(), curr * size, size);
		return timetableAppointments;
	}

	/*************************************************************************************
	 * @內容：查看计数的所有时间列表安排
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public int getCountTimetableAppointmentsByQuery(TimetableAppointment timetableAppointment,int status) {
		// 创建根据学期来查询课程；
		StringBuffer sql = new StringBuffer(
				"select count(*)  from TimetableAppointment c where 1=1 and c.schoolCourse.schoolAcademy.academyNumber like '%"	+ shareService.getUserDetail().getSchoolAcademy().getAcademyNumber() + "%'");
		if (status != -1) {
			sql.append(" and c.status = " + status);
		}
		if (timetableAppointment.getSchoolCourse()!=null&&timetableAppointment.getSchoolCourse().getCourseNo() != null) {
			sql.append(" and c.schoolCourse.courseNo like '%" + timetableAppointment.getSchoolCourse().getCourseNo() + "%'");
		}
		// 将query添加到sb1后
		return ((Long) timetableAppointmentDAO.createQuerySingleResult(
				sql.toString()).getSingleResult()).intValue();
	}

	@Override
	public List<SchoolDevice> findSchoolDeviceNotInLabRoomDevice() {
		// TODO Auto-generated method stub
		List<LabRoomDevice> devices = this.findLabRoomDevice(new LabRoomDevice(),1,-1);
		String sql="select l from SchoolDevice l where 1=1";
		if(devices != null && devices.size() != 0){
			int count = 0;
			sql += " and deviceNumber not in (";
			for(LabRoomDevice l:devices){
				if(count == 0)
				{
					sql += "'"+l.getSchoolDevice().getDeviceNumber()+"'";
					count++;
				}
				else{
					sql += " ,'"+l.getSchoolDevice().getDeviceNumber()+"'";
				}
			}
			sql+=" )";
		}
		List<SchoolDevice> schoolDevices=schoolDeviceDAO.executeQuery(sql,0,-1);
		return schoolDevices;
	}
	/**查询所有设备保管人
	 * 周志辉
	 * 2017-08-17
	 *
	 */
	@Override
	public List<User> findAllDeviceKeepUser() {
		String sql="select l from LabRoomDevice l";
		User labRoomDeviceKeepUser=new User();
		List<User> listLabRoomDeviceKeepUsers=new ArrayList<User>();
		List<LabRoomDevice> labRoomDevices=labRoomDeviceDAO.executeQuery(sql,0,-1);
		for(LabRoomDevice labRoomDeviceKeepUsers:labRoomDevices){
			labRoomDeviceKeepUser=labRoomDeviceKeepUsers.getSchoolDevice().getUserByKeepUser();
			listLabRoomDeviceKeepUsers.add(labRoomDeviceKeepUser);
		}
		return listLabRoomDeviceKeepUsers;
	}
	/**
	 * 周志辉
	 * 查询所有设备
	 * 2017-10-10
	 */
	@Override
	public List<SchoolDevice> findSchoolDeviceBySchoolDevice(
			SchoolDevice schoolDevice, int page, int pageSize,
			HttpServletRequest request) {
		String sql = "select l from SchoolDevice l where 1=1";
		if (schoolDevice != null) {
			if (schoolDevice.getDeviceName() != null
					&& !schoolDevice.getDeviceName().equalsIgnoreCase("")) {

				sql += " and l.deviceName like '%"
						+ schoolDevice.getDeviceName() + "%'";
			}
			if (schoolDevice.getDeviceNumber() != null
					&& !schoolDevice.getDeviceNumber().equalsIgnoreCase("")) {

				sql += " and l.deviceNumber like '%"
						+ schoolDevice.getDeviceNumber() + "%'";
			}
		}
		String price1 = request.getParameter("price1");
		String price2 = request.getParameter("price2");
		String searchflg = request.getParameter("searchflg");
		if(searchflg != null && searchflg != ""){
			if(price1 != null && price1 != ""){
				
				if(searchflg.equals("1")){
					sql += " and l.devicePrice = " + price1;
				}else if(searchflg.equals("2")){
					sql += " and devicePrice != " + price1;
				}else if(searchflg.equals("3")){
					sql += " and devicePrice >= " + price1;
				}else if(searchflg.equals("4")){
					sql += " and devicePrice > " + price1;
				}else if(searchflg.equals("5")){
					sql += " and devicePrice <= " + price1;
				}else if(searchflg.equals("6")){
					sql += " and devicePrice < " + price1;
				}else if(searchflg.equals("7") && price2 != null && price2 != ""){
					sql += " and devicePrice between " + price1 + " and " + price2;
				}
			}
		}
		List<SchoolDevice> schoolDevices = schoolDeviceDAO.executeQuery(sql,
				(page - 1) * pageSize, pageSize);
		return schoolDevices;
	}
	/**
	 * 周志辉
	 * 查询所有设备数量
	 * 2017-10-10
	 */
	@Override
	public int getCountSchoolDeviceBySchoolDevice(SchoolDevice schoolDevice,
			HttpServletRequest request) {
		String sql="select count(*) from SchoolDevice l where 1=1";
		if(schoolDevice!=null){
		if(schoolDevice.getDeviceName()!=null&&!schoolDevice.getDeviceName().equalsIgnoreCase("")){
		
			sql+=" and l.deviceName like '%"+schoolDevice.getDeviceName()+"%'";
		}
		if(schoolDevice.getDeviceNumber()!=null&&!schoolDevice.getDeviceNumber().equalsIgnoreCase("")){
			
			sql+=" and l.deviceNumber like '%"+schoolDevice.getDeviceNumber()+"%'";
		}
		}
		String price1 = request.getParameter("price1");
		String price2 = request.getParameter("price2");
		String searchflg = request.getParameter("searchflg");
		if(searchflg != null && searchflg != ""){
			if(price1 != null && price1 != ""){
				
				if(searchflg.equals("1")){
					sql += " and l.devicePrice = " + price1;
				}else if(searchflg.equals("2")){
					sql += " and devicePrice != " + price1;
				}else if(searchflg.equals("3")){
					sql += " and devicePrice >= " + price1;
				}else if(searchflg.equals("4")){
					sql += " and devicePrice > " + price1;
				}else if(searchflg.equals("5")){
					sql += " and devicePrice <= " + price1;
				}else if(searchflg.equals("6")){
					sql += " and devicePrice < " + price1;
				}else if(searchflg.equals("7") && price2 != null && price2 != ""){
					sql += " and devicePrice between " + price1 + " and " + price2;
				}
			}
		}
		/*sql+=" order by l.CConsumables desc";*/
		//给语句添加分页机制；
		//List<SchoolDevice> schoolDevices=schoolDeviceDAO.executeQuery(sql,0,-1);
		return ((Long) schoolDeviceDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	/****************************************************************************
	 * @功能：导出查询到的所有设备(分sheet导出)
	 * @作者：周志辉
	 * @日期：2016-10-10
	 ****************************************************************************/
	@Override
	public void exportAllDevice(List<SchoolDevice> listSchoolDevice,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//格式化时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				//新建一个mapList集合
				List<Map> mapList = new ArrayList<Map>();
				for(SchoolDevice equipment : listSchoolDevice){
					// 新建一个HashMap对象
					Map map = new HashMap();
					//设备编号
					if(equipment.getDeviceNumber()!= null){
						map.put("deviceNumber", equipment.getDeviceNumber());
					}else{
						map.put("deviceNumber", "*");
					}
					
					//设备名称
					if(equipment.getDeviceName()!= null){
						map.put("deviceName", equipment.getDeviceName());
					}else{
						map.put("deviceName", "*");
					}
					
					//设备型号
					if(equipment.getDevicePattern()!= null){
						map.put("devicePattern", equipment.getDevicePattern());
					}else{
						map.put("devicePattern", "*");
					}
					
					//设备规格
					if(equipment.getDeviceFormat()!= null){
						map.put("deviceFormat", equipment.getDeviceFormat());
					}else{
						map.put("deviceFormat", "*");
					}
					
					//使用方向
					if(equipment.getDeviceUseDirection()!= null){
						map.put("deviceUseDirection", equipment.getDeviceUseDirection());
					}else{
						map.put("deviceUseDirection", "*");
					}
					
					//购买日期
					if(equipment.getDeviceBuyDate()!= null){
						map.put("deviceBuyDate", sdf.format(equipment.getDeviceBuyDate().getTime()));
					}else{
						map.put("deviceBuyDate", "*");
					}
					//存放地点
					if(equipment.getDeviceAddress()!= null){
						map.put("deviceAddress",equipment.getDeviceAddress());
					}else{
						map.put("deviceAddress", "*");
					}
					//价格
					if(equipment.getDevicePrice()!= null){
						map.put("devicePrice",equipment.getDevicePrice());
					}else{
						map.put("devicePrice", "*");
					}
					//谁被入账日期
					if(equipment.getDeviceAccountedDate()!= null){
						map.put("deviceAccountedDate", sdf.format(equipment.getDeviceAccountedDate().getTime()));
					}else{
						map.put("deviceAccountedDate", "*");
					}
					//设备供应商
					if(equipment.getDeviceSupplier()!= null){
						map.put("deviceSupplier",equipment.getDeviceSupplier());
					}else{
						map.put("deviceSupplier", "*");
					}
					//领用人
					if(equipment.getUserByUserNumber()!= null){
						map.put("user",equipment.getUserByUserNumber());
					}else{
						map.put("user", "*");
					}
					//保管人
					if(equipment.getUserByKeepUser()!= null){
						map.put("keepUser", equipment.getUserByKeepUser().getCname());
					}else{
						map.put("keepUser", "*");
					}
					//项目来源
					if(equipment.getProjectSource()!= null){
						map.put("deviceProjectSource",equipment.getProjectSource());
					}else{
						map.put("deviceProjectSource", "*");
					}
					//生产厂家
					if(equipment.getManufacturer()!= null){
						map.put("deviceManufacturer",equipment.getManufacturer());
					}else{
						map.put("deviceManufacturerce", "*");
					}
					//序列号
					if(equipment.getSn()!= null){
						map.put("sn",equipment.getSn());
					}else{
						map.put("sn", "*");
					}
					//项目编号
					if(equipment.getProjectCode()!= null){
						map.put("deviceProjectCode",equipment.getProjectCode());
					}else{
						map.put("deviceProjectCode", "*");
					}
					//供应单位联系方式
					if(equipment.getSupplyPhone()!= null){
						map.put("deviceSupplyPhone",equipment.getSupplyPhone());
					}else{
						map.put("deviceSupplyPhone", "*");
					}
					//使用状态
					if(equipment.getCDictionaryByUseStatus()!=null){
						if(equipment.getCDictionaryByUseStatus().getCName()!= null){
							map.put("deviceUseStatus",equipment.getCDictionaryByUseStatus().getCName());
						}else{
							map.put("deviceUseStatus", "*");
						}
					}else{
						map.put("deviceUseStatus", "*");
					}
					
					//设备来源
					if(equipment.getCDictionaryByDeviceSource()!= null){
						if(equipment.getCDictionaryByDeviceSource().getCName()!= null){
							map.put("deviceDeviceSource",equipment.getCDictionaryByDeviceSource().getCName());
						}else{
							map.put("deviceDeviceSource", "*");
						}
					}else{
						map.put("deviceDeviceSource", "*");
					}
					//设备状态
					if(equipment.getCDictionaryByDeviceFlag()!= null){
						if(equipment.getCDictionaryByDeviceFlag().getCName()!= null){
							map.put("deviceDeviceFlag",equipment.getCDictionaryByDeviceFlag().getCName());
						}else{
							map.put("deviceDeviceFlag", "*");
						}
					}else{
						map.put("deviceDeviceFlag", "*");
					}

					mapList.add(map);
					
				}
				
				//新建一个用来存放分sheet的List对象
				List<List<Map>> wrapList = new ArrayList();
				//定义一个sheet的最大条目容量
				int quantity = 60000;
				//定义起点坐标
				int count = 0;
				while (count < mapList.size()) {//判断equipments的容量能够分割成几个规定容量的List
					wrapList.add(new ArrayList(mapList.subList(count, (count + quantity) > mapList.size() ? mapList.size() : count + quantity)));
					count += quantity;
				}
				
				//给表设置名称
				String title = "设备资产明细表";
				//给表设置表头名
				String[] hearders = new String[] {"仪器编号", "仪器名称", "设备型号", "设备规格", "使用方向","购买日期","存放地点","价格","设备入账日期","设备供应商","领用人","保管人","项目来源","生产厂家","序列号","项目编号","供应单位联系方式",
						"使用状态","设备来源","设备状态"};
				//属性数组，写数据到excel时的顺序定位
				String[] fields = new String[] {"deviceNumber", "deviceName","devicePattern","deviceFormat","deviceUseDirection","deviceBuyDate","deviceAddress","devicePrice","deviceAccountedDate","deviceSupplier","user","keepUser"
						,"deviceProjectSource","deviceManufacturer","sn","deviceProjectCode","deviceSupplyPhone","deviceUseStatus","deviceDeviceSource","deviceDeviceFlag"};
				//新建一个TableData的集合
				List<TableData> tableDataList = new ArrayList<TableData>();
				for(List<Map> tempList : wrapList){//将所需导出的数据集合遍历并拼接表头信息
					TableData td = ExcelUtils.createTableData(tempList, ExcelUtils.createTableHeader(hearders), fields);
					tableDataList.add(td);
				}
				JsGridReportBase report = new JsGridReportBase(request, response);
				
				report.exportToExcelForSheets(title, shareService.getUserDetail().getCname(), tableDataList);
		
	}
	/****************************************************************************
	 * @功能：导出查询到的所有实验室设备(分sheet导出)
	 * @作者：周志辉
	 * @日期：2017-10-10
	 ****************************************************************************/
	@Override
	public void exportAllLabRoomDevice(List<LabRoomDevice> listLabRoomDevice,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//格式化时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//新建一个mapList集合
		List<Map> mapList = new ArrayList<Map>();
		for(LabRoomDevice equipment : listLabRoomDevice){
			// 新建一个HashMap对象
			Map map = new HashMap();
			//设备编号
			if(equipment.getSchoolDevice().getDeviceNumber()!= null){
				map.put("deviceNumber", equipment.getSchoolDevice().getDeviceNumber());
			}else{
				map.put("deviceNumber", "*");
			}
			
			//设备名称
			if(equipment.getSchoolDevice().getDeviceName()!= null){
				map.put("deviceName", equipment.getSchoolDevice().getDeviceName());
			}else{
				map.put("deviceName", "*");
			}
			//所属实验室
			if(equipment.getLabRoom()!=null){
				if(equipment.getLabRoom().getLabRoomName()!=null){
					map.put("labRoomName", equipment.getSchoolDevice().getDeviceName());
				}else{
					map.put("labRoomName", "*");
				}
			}else{
				map.put("labRoomName", "*");
			}
			//设备型号
			if(equipment.getSchoolDevice().getDevicePattern()!= null){
				map.put("devicePattern", equipment.getSchoolDevice().getDevicePattern());
			}else{
				map.put("devicePattern", "*");
			}
			
			//设备规格
			if(equipment.getSchoolDevice().getDeviceFormat()!= null){
				map.put("deviceFormat", equipment.getSchoolDevice().getDeviceFormat());
			}else{
				map.put("deviceFormat", "*");
			}
			
			//使用方向
			if(equipment.getSchoolDevice().getDeviceUseDirection()!= null){
				map.put("deviceUseDirection", equipment.getSchoolDevice().getDeviceUseDirection());
			}else{
				map.put("deviceUseDirection", "*");
			}
			
			//购买日期
			if(equipment.getSchoolDevice().getDeviceBuyDate()!= null){
				map.put("deviceBuyDate", sdf.format(equipment.getSchoolDevice().getDeviceBuyDate().getTime()));
			}else{
				map.put("deviceBuyDate", "*");
			}
			//存放地点
			if(equipment.getSchoolDevice().getDeviceAddress()!= null){
				map.put("deviceAddress",equipment.getSchoolDevice().getDeviceAddress());
			}else{
				map.put("deviceAddress", "*");
			}
			//价格
			if(equipment.getSchoolDevice().getDevicePrice()!= null){
				map.put("devicePrice",equipment.getSchoolDevice().getDevicePrice());
			}else{
				map.put("devicePrice", "*");
			}
			//谁被入账日期
			if(equipment.getSchoolDevice().getDeviceAccountedDate()!= null){
				map.put("deviceAccountedDate", sdf.format(equipment.getSchoolDevice().getDeviceAccountedDate().getTime()));
			}else{
				map.put("deviceAccountedDate", "*");
			}
			//设备供应商
			if(equipment.getSchoolDevice().getDeviceSupplier()!= null){
				map.put("deviceSupplier",equipment.getSchoolDevice().getDeviceSupplier());
			}else{
				map.put("deviceSupplier", "*");
			}
			//领用人
			if(equipment.getSchoolDevice().getUserByUserNumber()!= null){
				map.put("user",equipment.getSchoolDevice().getUserByUserNumber());
			}else{
				map.put("user", "*");
			}
			//保管人
			if(equipment.getSchoolDevice().getUserByKeepUser()!= null){
				map.put("keepUser", equipment.getSchoolDevice().getUserByKeepUser().getCname());
			}else{
				map.put("keepUser", "*");
			}
			//项目来源
			if(equipment.getSchoolDevice().getProjectSource()!= null){
				map.put("deviceProjectSource",equipment.getSchoolDevice().getProjectSource());
			}else{
				map.put("deviceProjectSource", "*");
			}
			//生产厂家
			if(equipment.getSchoolDevice().getManufacturer()!= null){
				map.put("deviceManufacturer",equipment.getSchoolDevice().getManufacturer());
			}else{
				map.put("deviceManufacturerce", "*");
			}
			//序列号
			if(equipment.getSchoolDevice().getSn()!= null){
				map.put("sn",equipment.getSchoolDevice().getSn());
			}else{
				map.put("sn", "*");
			}
			//项目编号
			if(equipment.getSchoolDevice().getProjectCode()!= null){
				map.put("deviceProjectCode",equipment.getSchoolDevice().getProjectCode());
			}else{
				map.put("deviceProjectCode", "*");
			}
			//供应单位联系方式
			if(equipment.getSchoolDevice().getSupplyPhone()!= null){
				map.put("deviceSupplyPhone",equipment.getSchoolDevice().getSupplyPhone());
			}else{
				map.put("deviceSupplyPhone", "*");
			}
			//使用状态
			if(equipment.getSchoolDevice().getCDictionaryByUseStatus()!=null){
				if(equipment.getSchoolDevice().getCDictionaryByUseStatus().getCName()!= null){
					map.put("deviceUseStatus",equipment.getSchoolDevice().getCDictionaryByUseStatus().getCName());
				}else{
					map.put("deviceUseStatus", "*");
				}
			}else{
				map.put("deviceUseStatus", "*");
			}
			
			//设备来源
			if(equipment.getSchoolDevice().getCDictionaryByDeviceSource()!= null){
				if(equipment.getSchoolDevice().getCDictionaryByDeviceSource().getCName()!= null){
					map.put("deviceDeviceSource",equipment.getSchoolDevice().getCDictionaryByDeviceSource().getCName());
				}else{
					map.put("deviceDeviceSource", "*");
				}
			}else{
				map.put("deviceDeviceSource", "*");
			}
			//设备状态
			if(equipment.getSchoolDevice().getCDictionaryByDeviceFlag()!= null){
				if(equipment.getSchoolDevice().getCDictionaryByDeviceFlag().getCName()!= null){
					map.put("deviceDeviceFlag",equipment.getSchoolDevice().getCDictionaryByDeviceFlag().getCName());
				}else{
					map.put("deviceDeviceFlag", "*");
				}
			}else{
				map.put("deviceDeviceFlag", "*");
			}
			
			mapList.add(map);
			
		}
		
		//新建一个用来存放分sheet的List对象
		List<List<Map>> wrapList = new ArrayList();
		//定义一个sheet的最大条目容量
		int quantity = 60000;
		//定义起点坐标
		int count = 0;
		while (count < mapList.size()) {//判断equipments的容量能够分割成几个规定容量的List
			wrapList.add(new ArrayList(mapList.subList(count, (count + quantity) > mapList.size() ? mapList.size() : count + quantity)));
			count += quantity;
		}
		
		//给表设置名称
		String title = "设备资产明细表";
		//给表设置表头名
		String[] hearders = new String[] {"仪器编号", "仪器名称", "所属实验室","设备型号", "设备规格", "使用方向","购买日期","存放地点","价格","设备入账日期","设备供应商","领用人","保管人","项目来源","生产厂家","序列号","项目编号","供应单位联系方式",
				"使用状态","设备来源","设备状态"};
		//属性数组，写数据到excel时的顺序定位
		String[] fields = new String[] {"deviceNumber", "deviceName","labRoomName","devicePattern","deviceFormat","deviceUseDirection","deviceBuyDate","deviceAddress","devicePrice","deviceAccountedDate","deviceSupplier","user","keepUser"
				,"deviceProjectSource","deviceManufacturer","sn","deviceProjectCode","deviceSupplyPhone","deviceUseStatus","deviceDeviceSource","deviceDeviceFlag"};
		//新建一个TableData的集合
		List<TableData> tableDataList = new ArrayList<TableData>();
		for(List<Map> tempList : wrapList){//将所需导出的数据集合遍历并拼接表头信息
			TableData td = ExcelUtils.createTableData(tempList, ExcelUtils.createTableHeader(hearders), fields);
			tableDataList.add(td);
		}
		JsGridReportBase report = new JsGridReportBase(request, response);
		
		report.exportToExcelForSheets(title, shareService.getUserDetail().getCname(), tableDataList);
		
	}
	
}
