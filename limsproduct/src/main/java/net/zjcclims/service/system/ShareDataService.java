package net.zjcclims.service.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zjcclims.domain.LabRoomDevice;
import net.zjcclims.domain.SchoolAcademy;
import net.zjcclims.domain.SchoolDevice;
import net.zjcclims.domain.SystemBuild;
import net.zjcclims.domain.SystemRoom;
import net.zjcclims.domain.TimetableAppointment;
import net.zjcclims.domain.User;

public interface ShareDataService {

	public List<SystemBuild> findSystemBuildBySystemBuild(SystemBuild systemBuild, int page,
			int pageSize);
	
	public List<SystemBuild> findSystemBuildBySystemBuild(SystemBuild systemBuild);
	
	public List<SystemRoom> findSystemRoomBySystemRoom(SystemRoom systemRoom);
	
	public List<SystemRoom> findSystemRoomBySystemRoom(SystemRoom systemRoom, int page,
			int pageSize);
	
	public List<SchoolDevice> findSchoolDeviceBySchoolDevice(SchoolDevice schoolDevice);
	
	public List<SchoolDevice> findSchoolDeviceBySchoolDevice(SchoolDevice schoolDevice, int page,
			int pageSize);
	
	public List<User> findUserByUser(User user);
	
	public List<User> findUserByUser(User user, int page,
			int pageSize);
	
	public List<SchoolAcademy> findSchoolAcademyBySchoolAcademy(SchoolAcademy schoolAcademy);
	
	public List<SchoolAcademy> findSchoolAcademyBySchoolAcademy(SchoolAcademy schoolAcademy, int page,
			int pageSize);
	
	public List<SchoolAcademy> findSchoolAcademyBySchoolAcademy1(SchoolAcademy schoolAcademy);
	
	public List<SchoolAcademy> findSchoolAcademyBySchoolAcademy1(SchoolAcademy schoolAcademy, int page,
			int pageSize);
	
	public List<TimetableAppointment> getTimetableAppointmentsByQuery(TimetableAppointment timetableAppointment,
			int status, int curr, int size);
	
	public int getCountTimetableAppointmentsByQuery(TimetableAppointment timetableAppointment,int status);

	public int getCountSchoolDeviceBySchoolDevice(SchoolDevice schoolDevice);
	

	public int getCountSchoolAcademyBySchoolAcademy1(SchoolAcademy schoolAcademy);

	public int getCountSchoolAcademyBySchoolAcademy(SchoolAcademy schoolAcademy);

	public int getCountSystemBuildBySystemBuild(SystemBuild systemBuild);

	public int getCountSystemRoomBySystemRoom(SystemRoom systemRoom); 
	
	public int getCountLabRoomDevice(LabRoomDevice labRoomDevice); 
	
	public List<LabRoomDevice> findLabRoomDevice(LabRoomDevice labRoomDevice, Integer page, Integer pageSize);
	
	public List<SchoolDevice> findSchoolDeviceNotInLabRoomDevice();
	/**查询所有设备保管人
	 * 周志辉
	 * 2017-08-17
	 *
	 */
	public List<User> findAllDeviceKeepUser();
	/**
	 * 周志辉
	 * 查询所有设备
	 * 2017-10-10
	 */
	public List<SchoolDevice> findSchoolDeviceBySchoolDevice(SchoolDevice schoolDevice, int page,
			int pageSize,HttpServletRequest request);
	/**
	 * 周志辉
	 * 查询所有设备数量
	 * 2017-10-10
	 */
	public int getCountSchoolDeviceBySchoolDevice(SchoolDevice schoolDevice,HttpServletRequest request);
	/****************************************************************************
	 * @功能：导出查询到的所有设备(分sheet导出)
	 * @作者：周志辉
	 * @日期：2017-10-10
	 ****************************************************************************/
	public void exportAllDevice(List<SchoolDevice> listSchoolDevice, HttpServletRequest request, HttpServletResponse response) throws Exception;
	/****************************************************************************
	 * @功能：导出查询到的所有实验室设备(分sheet导出)
	 * @作者：周志辉
	 * @日期：2017-10-10
	 ****************************************************************************/
	public void exportAllLabRoomDevice(List<LabRoomDevice> listLabRoomDevice, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
