package net.zjcclims.service.cmsshow;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;
import net.gvsun.lims.dto.common.PConfigDTO;
import net.zjcclims.common.LabAttendance;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import excelTools.Labreservationlist;



@Service("CMSShowService")
@Transactional
public class CMSShowServiceServiceImpl implements  CMSShowService {
	

	@Autowired 
	private LabRoomDAO labRoomDAO;
	@Autowired 
	private LabReservationDAO labReservationDAO;
	@Autowired
	private LabRoomDeviceReservationDAO labRoomDeviceReservationDAO;
	@Autowired
	private LabRoomDeviceDAO labRoomDeviceDAO;
	@Autowired
	private CommonHdwlogDAO commonHdwlogDAO;
	@Autowired
	private ShareService shareService;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private SchoolAcademyDAO schoolAcademyDAO;
	@Autowired
	private LabRoomAgentDAO labRoomAgentDAO;
	@PersistenceContext
	EntityManager entityManager;
	
	
	/*************************************************************************************
	 * @內容：实验室开放的总记录数-校级页面
	 * @作者：叶明盾
	 * @日期：2014-09-10
	 *************************************************************************************/
	@Transactional
	public int getAllLabReservationTotalRecords(){
		//实验室开放的总记录数（由于数据量比较多，不能够使用findAll()方法查找）
		return  ((Long) labReservationDAO.createQuerySingleResult("select count(*) from LabReservation").getSingleResult()).intValue();
	}
	
	/*************************************************************************************
	 * @內容：查找所有的实验室开放-校级页面
	 * @作者：叶明盾
	 * @日期：2014-09-10
	 *************************************************************************************/
	public List<LabReservation> findAllLabReservations(int curr, int size){
		//利用sql语句从LabReservation表中查找出所有的数据，并赋给StringBuffer类型的sb变量
		StringBuffer sb= new StringBuffer("select l from LabReservation l where 1=1");
		//给语句添加分页机制
		sb.append(" order by l.id desc");
		List<LabReservation> labReservations=labReservationDAO.executeQuery(sb.toString(), curr*size, size);
		return labReservations;
	}
	
	/*************************************************************************************
	 * @內容：根据实验室开放的情况查找出相应的周次、星期、节次-校级页面
	 * @作者：叶明盾
	 * @日期：2014-09-10
	 *************************************************************************************/
	public  List<Labreservationlist> findLabReservationDate(int curr, int size){
		 List<LabReservation> LabReservations = this.findAllLabReservations(curr - 1, size);
		 List<Labreservationlist>    list = new ArrayList<Labreservationlist>();  
         for (LabReservation lab : LabReservations) {
        	 Labreservationlist   la=new Labreservationlist();
        	        Set<String>  week=new HashSet<String>(); 
        	        Set<String>  day=new HashSet<String>(); 
        	        Set<String>  time=new HashSet<String>(); 
        	for (LabReservationTimeTable labre : lab.getLabReservationTimeTables()) {
        		//week.add(labre.getCLabReservationWeek().getName());
        		week.add(labre.getCDictionary().getCName());
        		day.add(labre.getSchoolWeekday().getWeekdayName());
        	 	time.add(labre.getSystemTime().getSectionName());
			}
        	int dd=week.size();
        	String[] weeks=week.toArray(new String[dd]);
        	String[] days=day.toArray(new String[dd]);
        	String[] timea=time.toArray(new String[dd]);;
			la.setWeek(weeks);
			la.setTime(timea);
			la.setDay(days);
			list.add(la);		
		}
         return list;
	}
	
	
	
	/*************************************************************************************
	 * @內容：仪器共享的总记录数-校级页面
	 * @作者：叶明盾
	 * @日期：2014-09-10
	 *************************************************************************************/
	@Transactional
	public int getAllLabRoomDeviceReservationTotalRecords(){
		//得出仪器共享的总记录数（由于数据量比较多，不能够使用findAll()方法查找）
		String sql = "select count(l) from LabRoomDeviceReservation l where 1=1";
		// 非排课占用
		sql+=" and (l.timetableLabDevice is null or l.appointmentId is null)";
		sql+=" group by l.innerSame";
		return  ((Long) labRoomDeviceReservationDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	
	/*************************************************************************************
	 * @內容：查找所有的仪器共享-校级页面
	 * @作者：叶明盾
	 * @日期：2014-09-10
	 *************************************************************************************/
	public List<LabRoomDeviceReservation> findAllLabRoomDeviceReservations(int curr, int size){
		//利用sql语句从仪器共享表中查找出所有的数据，并赋给StringBuffer类型的sb变量
		StringBuffer sb= new StringBuffer("select l from LabRoomDeviceReservation l where 1=1");
		// 非排课占用
		sb.append(" and (l.timetableLabDevice is null or l.appointmentId is null)");
		//给语句添加分页机制
		sb.append(" group by l.innerSame");
		List<LabRoomDeviceReservation> labRoomDeviceReservations = labRoomDeviceReservationDAO.executeQuery(sb.toString(), curr*size, size);
		return labRoomDeviceReservations;
	}
	
	
	/*************************************************************************************
	 * @內容：实验室开放的总记录数-计算机学院
	 * @作者：叶明盾
	 * @日期：2014-09-23
	 *************************************************************************************/
	@Transactional
	public int getCompLabReservationTotalRecords(){
		//实验室开放的总记录数（由于数据量比较多，不能够使用findAll()方法查找），0205为计算机学院的学院编号
		return  ((Long) labReservationDAO.createQuerySingleResult("select count(*) from LabReservation l where  l.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber=0205 ").getSingleResult()).intValue();
	}
	
	/*************************************************************************************
	 * @內容：查找所有的实验室开放-计算机学院
	 * @作者：叶明盾
	 * @日期：2014-09-23
	 *************************************************************************************/
	public List<LabReservation> findCompLabReservations(int curr, int size){
		//利用sql语句从LabReservation表中查找出计算机学院的数据，并赋给StringBuffer类型的sb变量，0205为计算机学院的学院编号；
		StringBuffer sb= new StringBuffer("select l from LabReservation l where  l.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber=0205 and 1=1");
		//给语句添加分页机制
		List<LabReservation> labReservations=labReservationDAO.executeQuery(sb.toString(), curr*size, size);
		return labReservations;
	}
	
	/*************************************************************************************
	 * @內容：仪器共享的总记录数-计算机学院
	 * @作者：叶明盾
	 * @日期：2014-09-23
	 *************************************************************************************/
	@Transactional
	public int getCompLabRoomDeviceReservationTotalRecords(){
		//得出计算机学院仪器共享的总记录数（由于数据量比较多，不能够使用findAll()方法查找），0205为计算机学院的学院编号
		return  ((Long) labRoomDeviceReservationDAO.createQuerySingleResult("select count(*) from LabRoomDeviceReservation l where l.labRoomDevice.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber=0205 ").getSingleResult()).intValue();
	}
	
	/*************************************************************************************
	 * @內容：查找所有的仪器共享-计算机学院
	 * @作者：叶明盾
	 * @日期：2014-09-23
	 *************************************************************************************/
	public List<LabRoomDeviceReservation> findCompLabRoomDeviceReservations(int curr, int size){
		//利用sql语句从仪器共享表中查找出计算机学院的数据，并赋给StringBuffer类型的sb变量，0205为计算机学院的学院编号
		StringBuffer sb= new StringBuffer("select l from LabRoomDeviceReservation l where l.labRoomDevice.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber=0205 and 1=1");
		//给语句添加分页机制
		List<LabRoomDeviceReservation> labRoomDeviceReservations = labRoomDeviceReservationDAO.executeQuery(sb.toString(), curr*size, size);
		return labRoomDeviceReservations;
	}
	
	
	/**
	 * 实验室开放的总记录数-各个学院
	 * @param academyId 学院Id
	 * @return
	 * @author 叶明盾
	 * @date 2014-11-11 上午12:47:07
	 */
	@Transactional
	public int getAcademyLabReservationTotalRecords(String academyId){
		//实验室开放的总记录数（由于数据量比较多，不能够使用findAll()方法查找），academyId为学院的Id
		return  ((Long) labReservationDAO.createQuerySingleResult("select count(*) from LabReservation l where  l.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber like '"+academyId+"'").getSingleResult()).intValue();
	}
	
	/**
	 * 查找所有的实验室开放-各个学院
	 * @param curr
	 * @param size
	 * @param academyId 学院Id
	 * @return
	 * @author 叶明盾
	 * @date 2014-11-11 上午12:48:15
	 */
	public List<LabReservation> findAcademyLabReservations(int curr, int size,String academyId){
		//利用sql语句从LabReservation表中查找出计算机学院的数据，并赋给StringBuffer类型的sb变量，academyId为学院编号；
		StringBuffer sb= new StringBuffer("select l from LabReservation l where  l.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber like '"+academyId+"'");
		//给语句添加分页机制
		List<LabReservation> labReservations=labReservationDAO.executeQuery(sb.toString(), curr*size, size);
		return labReservations;
	}
	
	/**
	 * 仪器共享的总记录数-各个学院
	 * @param academyId 学院Id
	 * @return
	 * @author 叶明盾
	 * @date 2014-11-11 上午12:49:03
	 */
	@Transactional
	public int getAcademyLabRoomDeviceReservationTotalRecords(String academyId){
		//得出计算机学院仪器共享的总记录数（由于数据量比较多，不能够使用findAll()方法查找），academyId为学院编号
		return  ((Long) labRoomDeviceReservationDAO.createQuerySingleResult("select count(*) from LabRoomDeviceReservation l where l.labRoomDevice.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber like '"+academyId+"'").getSingleResult()).intValue();
	}
	
	/**
	 * 查找所有的仪器共享-各个学院
	 * @param curr
	 * @param size
	 * @param academyId 学院Id
	 * @return
	 * @author 叶明盾
	 * @date 2014-11-11 上午12:49:57
	 */
	public List<LabRoomDeviceReservation> findAcademyLabRoomDeviceReservations(int curr, int size,String academyId){
		//利用sql语句从仪器共享表中查找出计算机学院的数据，并赋给StringBuffer类型的sb变量，academyId为学院编号
		StringBuffer sb= new StringBuffer("select l from LabRoomDeviceReservation l where l.labRoomDevice.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber like '"+academyId+"'");
		//给语句添加分页机制
		List<LabRoomDeviceReservation> labRoomDeviceReservations = labRoomDeviceReservationDAO.executeQuery(sb.toString(), curr*size, size);
		return labRoomDeviceReservations;
	}
	/*************************************************************************************
	 * @功能：根据学院查询实验室
	 * @作者： 李小龙
	 *************************************************************************************/
	@Override
	public List<LabRoom> findLabRoomBySchoolAcademy(LabRoom labRoom) {
		String sql="select r from LabRoom r where 1=1 ";
		if(labRoom.getLabAnnex()!=null&&labRoom.getLabAnnex().getLabCenter()!=null&&labRoom.getLabAnnex().getLabCenter().getSchoolAcademy()!=null){
			if(labRoom.getLabAnnex().getId()!=null&&!labRoom.getLabAnnex().getId().equals(0)){
				sql+=" and r.labAnnex.id="+labRoom.getLabAnnex().getId();
			}
			if(!labRoom.getLabAnnex().getLabCenter().getSchoolAcademy().getAcademyNumber().equals("")){
				sql+=" and r.labAnnex.labCenter.schoolAcademy.academyNumber='"+labRoom.getLabAnnex().getLabCenter().getSchoolAcademy().getAcademyNumber()+"' ";
			}
			
		}
		if(labRoom.getSystemBuild()!=null){
			if(!labRoom.getSystemBuild().getBuildNumber().equals("")){
				sql+=" and r.systemBuild.buildNumber='"+labRoom.getSystemBuild().getBuildNumber()+"' ";
			}
		}
		return labRoomDAO.executeQuery(sql, 0,-1);
	}
	/*************************************************************************************
	 * @功能：根据学院查询实验室并分页
	 * @作者： 李小龙
	 *************************************************************************************/
	@Override
	public List<LabRoom> findLabRoomBySchoolAcademy(LabRoom labRoom, int page, int pageSize) {
		String sql="select r from LabRoom r where 1=1 ";
		
		if(labRoom.getLabAnnex()!=null&&labRoom.getLabAnnex().getLabCenter()!=null&&labRoom.getLabAnnex().getLabCenter().getSchoolAcademy()!=null){
			if(labRoom.getLabAnnex().getId()!=null&&!labRoom.getLabAnnex().getId().equals(0)){
				sql+=" and r.labAnnex.id="+labRoom.getLabAnnex().getId();
			}
			if(!labRoom.getLabAnnex().getLabCenter().getSchoolAcademy().getAcademyNumber().equals("")){
				sql+=" and r.labAnnex.labCenter.schoolAcademy.academyNumber='"+labRoom.getLabAnnex().getLabCenter().getSchoolAcademy().getAcademyNumber()+"' ";
			}
			
		}
		if(labRoom.getSystemBuild()!=null){
			if(!labRoom.getSystemBuild().getBuildNumber().equals("")){
				sql+=" and r.systemBuild.buildNumber='"+labRoom.getSystemBuild().getBuildNumber()+" '";
			}
		}
		return labRoomDAO.executeQuery(sql, (page-1)*pageSize,pageSize);
	}
	
	/*************************************************************************************
	 * @功能：根据学院查询实验室并分页--默认显示当前学院的
	 * @作者： 贺子龙
	 *************************************************************************************/
	@Override
	public List<LabRoom> findLabRoomBySchoolAcademyDefault(
			LabRoom labRoom, int page, int pageSize,int type, String acno){
		String academyNumber="";
        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
        if (!acno.equals("-1")) {
    		//获取选择的实验中心
        	academyNumber = shareService.findSchoolAcademyByPrimaryKey(acno).getAcademyNumber();
        }else {
        	if(shareService.getUserDetail().getSchoolAcademy()!=null&&shareService.getUserDetail().getSchoolAcademy().getAcademyNumber()!=null) {
				academyNumber = shareService.getUserDetail().getSchoolAcademy().getAcademyNumber();
			}
        }	
		String sql="select r from LabRoom r,LabRoomAgent la where 1=1 ";
		sql+=" and la.labRoom.id=r.id";
		//sql+=" and la.CAgentType.id="+type;
		sql+=" and la.CDictionary.CCategory = 'c_agent_type' and la.CDictionary.CNumber = '"+type+"'";
		sql+=" and r.labCategory=1";
		if(shareService.getUser().getSchoolAcademy()!=null
				&&!shareService.getUser().getSchoolAcademy().getAcademyNumber().equals("")){
			sql+=" and r.labCenter.schoolAcademy.academyNumber='"+academyNumber+"' ";
		}
		if(labRoom.getLabAnnex()!=null&&labRoom.getLabAnnex().getId()!=null&&!labRoom.getLabAnnex().getId().equals(0)){
			sql+=" and r.labAnnex.id="+labRoom.getLabAnnex().getId();
		}
		if(labRoom != null && labRoom.getId() != null && !labRoom.getId().equals("")){
			sql += " and r.id ="+labRoom.getId();
		}
		return labRoomDAO.executeQuery(sql, (page-1)*pageSize,pageSize);
	}

	/*************************************************************************************
	 * @功能：根据学院查询设备
	 * @作者： 李小龙
	 *************************************************************************************/
	@Override
	public List<LabRoomDevice> findLabRoomDeviceBySchoolAcademy(
			LabRoomDevice labRoomDevice) {
		String sql="select d from LabRoomDevice d where 1=1";
		if(labRoomDevice.getLabRoom()!=null&&labRoomDevice.getLabRoom().getLabAnnex()!=null&&labRoomDevice.getLabRoom().getLabAnnex().getLabCenter()!=null&&labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSchoolAcademy()!=null){
			if(labRoomDevice.getLabRoom().getLabAnnex().getId()!=null&&!labRoomDevice.getLabRoom().getLabAnnex().getId().equals(0)){
				sql+=" and d.labRoom.labAnnex.id="+labRoomDevice.getLabRoom().getLabAnnex().getId();
			}
			if(labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSchoolAcademy().getAcademyNumber()!=null&&!labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSchoolAcademy().getAcademyNumber().equals("")){
				sql+=" and d.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber like'"+labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSchoolAcademy().getAcademyNumber()+"'";
			}
		}
		
		return labRoomDeviceDAO.executeQuery(sql, 0,-1);
	}
	/*************************************************************************************
	 * @功能：根据学院查询设备并分页
	 * @作者： 李小龙
	 *************************************************************************************/
	@Override
	public List<LabRoomDevice> findLabRoomDeviceBySchoolAcademy(
			LabRoomDevice labRoomDevice, int page, int pageSize) {
		String sql="select d from LabRoomDevice d where 1=1";
		if(labRoomDevice.getLabRoom()!=null&&labRoomDevice.getLabRoom().getLabAnnex()!=null&&labRoomDevice.getLabRoom().getLabAnnex().getLabCenter()!=null&&labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSchoolAcademy()!=null){
			if(labRoomDevice.getLabRoom().getLabAnnex().getId()!=null&&!labRoomDevice.getLabRoom().getLabAnnex().getId().equals(0)){
				sql+=" and d.labRoom.labAnnex.id="+labRoomDevice.getLabRoom().getLabAnnex().getId();
			}
			if(labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSchoolAcademy().getAcademyNumber()!=null&&!labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSchoolAcademy().getAcademyNumber().equals("")){
				sql+=" and d.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber like '"+labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSchoolAcademy().getAcademyNumber()+"'";
			}
		}
		return labRoomDeviceDAO.executeQuery(sql, (page-1)*pageSize,pageSize);
	}
	/*************************************************************************************
	 * @功能：根据ip查询刷卡记录
	 * @作者： 李小龙
	 *************************************************************************************/
	@Override
	public List<CommonHdwlog> findLabRoomAccessByIp(String ip,String port) {
		String sql="select c from CommonHdwlog c where 1=1";
		if(ip!=null&&!port.equals("")){
			sql+=" and c.deviceno='"+port+"' ";
		}
		sql+=" order by c.id desc";
		return commonHdwlogDAO.executeQuery(sql, 0,-1);
	}
	/*************************************************************************************
	 * @功能：根据ip查询刷卡记录并分页
	 * @作者： 李小龙
	 *************************************************************************************/
	@Override
	public List<CommonHdwlog> findLabRoomAccessByIp(String ip,String port, Integer page,
			int pageSize) {
		String sql="select c from CommonHdwlog c where 1=1";
		if(ip!=null&&!port.equals("")){
			sql+=" and c.deviceno='"+port+"' ";
		}
		sql+=" order by c.id desc";
		return commonHdwlogDAO.executeQuery(sql, (page-1)*pageSize,pageSize);
	}
	
	/*************************************************************************************
	 * @功能：根据ip查询刷卡记录并分页--增加查询功能
	 * @作者： 贺子龙
	 *************************************************************************************/
	@Override
	public List<LabAttendance> findLabRoomAccessByIp(CommonHdwlog commonHdwlog,String ip,String port, Integer page,
			int pageSize,HttpServletRequest request) {
		PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
		String sql="select c from CommonHdwlog c where 1=1";

		if(port!=null&&!port.equals("")){
			sql+=" and c.deviceno='"+port+"' ";
		}

		if(ip!=null&&!ip.equals("")){
			if (pConfigDTO.PROJECT_NAME.equals("zisulims")) {//浙外临时方法
				sql += " and c.doorindex = '"+ ip +"'";
			}else {
				sql+=" and c.hardwareid='"+ip+"' ";
			}
		}

		if (commonHdwlog.getCardname()!=null&&!commonHdwlog.getCardname().equals("")) {
			sql+=" and c.cardname like '%"+commonHdwlog.getCardname()+"%' ";
		}
		if (commonHdwlog.getUsername()!=null&&!commonHdwlog.getUsername().equals("")) {
			sql+=" and c.username like '%"+commonHdwlog.getUsername()+"%' ";

		}
		String starttime= request.getParameter("starttime");
		String endtime=	request.getParameter("endtime");

		if(starttime!=null && starttime.length()>0 && endtime!=null&& endtime.length()>0){
			sql += " and c.datetime between '"+starttime +"' and '"+endtime+"' ";
		}

		sql+=" order by c.id desc";
		List<CommonHdwlog> commonHdwlogs=commonHdwlogDAO.executeQuery(sql, (page-1)*pageSize,pageSize);
		//将查出来的日志数据导入labAttendanceList中
		List<LabAttendance> labAttendanceList=new ArrayList<LabAttendance>();
		for (CommonHdwlog commonHdwlog2 : commonHdwlogs) {
			LabAttendance labAttendance=new LabAttendance();
			//姓名
			labAttendance.setCname(commonHdwlog2.getCardname());
			//考勤时间
			String attendanceTime=commonHdwlog2.getDatetime();
			labAttendance.setAttendanceTime(attendanceTime.substring(0, attendanceTime.length()-2));
			//default数据
			labAttendance.setClassName("暂无数据");
			labAttendance.setMajor("暂无数据");
			labAttendance.setAcademyName("暂无数据");
			//所属学院
			if (commonHdwlog2.getAcademyNumber()!=null&&!commonHdwlog2.getAcademyNumber().equals("")) {
				String academyName="";
				SchoolAcademy schoolAcademy=schoolAcademyDAO.findSchoolAcademyByAcademyNumber(commonHdwlog2.getAcademyNumber());
				if (schoolAcademy!=null&&!schoolAcademy.getAcademyName().equals("")) {
					academyName=schoolAcademy.getAcademyName();
					labAttendance.setAcademyName(academyName);
				}
			}
			//学号
			String username="";
			if (commonHdwlog2.getUsername()!=null&&!commonHdwlog2.getUsername().equals("")) {
				username=commonHdwlog2.getUsername();
			}
			if (!username.equals("")) {
				labAttendance.setUsername(username);
				User user=userDAO.findUserByPrimaryKey(username);
				//班级
				String className="";
				if (user.getSchoolClasses()!=null&&user.getSchoolClasses().getClassNumber()!=null
						&&!user.getSchoolClasses().getClassNumber().equals("")
						&&!user.getSchoolClasses().getClassName().equals("")) {
					className=user.getSchoolClasses().getClassName();
					labAttendance.setClassName(className);
				}
			}
			labAttendance.setStatus(commonHdwlog2.getStatus());
			//浙外临时方法----------start--------------------
			User user = null;
			for (User us : userDAO.findUserByCardno(commonHdwlog2.getCardnumber())) {
				user = us;
				break;
			}
			if (user!=null) {
				//姓名
				labAttendance.setCname(user.getCname());
				labAttendance.setUsername(user.getUsername());
				if (user.getSchoolAcademy()!=null && user.getSchoolAcademy().getAcademyName()!=null) {
					labAttendance.setAcademyName(user.getSchoolAcademy().getAcademyName());
				}
				if (user.getSchoolClasses()!=null && user.getSchoolClasses().getClassName()!=null) {
					labAttendance.setClassName(user.getSchoolClasses().getClassName());
				}
			}
			//浙外临时方法------------end--------------------
			labAttendanceList.add(labAttendance);

		}
		return labAttendanceList;
	}
	/*************************************************************************************
	 * @功能：根据ip查询刷卡记录数量--增加查询功能
	 * @作者： 贺子龙
	 *************************************************************************************/
	@Override
	public int findLabRoomAccessByIpCount(CommonHdwlog commonHdwlog,String ip,String port,HttpServletRequest request) {
		PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
		String sql="select count(*) from CommonHdwlog c where 1=1";
		
		if(port!=null&&!port.equals("")){
			sql+=" and c.deviceno='"+port+"' ";
		}
		
		if(ip!=null&&!ip.equals("")){
			if (pConfigDTO.PROJECT_NAME.equals("zisulims")) {//浙外临时方法
				sql += " and c.doorindex = '"+ ip +"'";
			}else {
				sql+=" and c.hardwareid='"+ip+"' ";
			}
		}

		if (commonHdwlog.getCardname()!=null&&!commonHdwlog.getCardname().equals("")) {
			sql+=" and c.cardname like '%"+commonHdwlog.getCardname()+"%' ";
			
		}
		if (commonHdwlog.getUsername()!=null&&!commonHdwlog.getUsername().equals("")) {
			sql+=" and c.username like '%"+commonHdwlog.getUsername()+"%' ";
			
		}
		String starttime= request.getParameter("starttime");
		String endtime=	request.getParameter("endtime");
		
		  if(starttime!=null && starttime.length()>0 && endtime!=null&& endtime.length()>0){
			  sql += " and c.datetime between '"+starttime +"' and '"+endtime+"' "; 	
	        }
		
		int count=((Long)commonHdwlogDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
		return count;
	}
	
	/*************************************************************************************
	 * @功能：根据实验室id查询实验室使用记录
	 * @作者： 李小龙
	 *************************************************************************************/
	@Override
	public List<LabReservation> findLabRoomUseRecord(Integer roomId) {
		String sql="select l from LabReservation l where l.labRoom.id="+roomId;
		return labReservationDAO.executeQuery(sql, 0,-1);
	}

	@Override
	public List<LabReservation> findLabRoomUseRecord(Integer roomId, int page,
			int pageSize) {
		String sql="select l from LabReservation l where l.labRoom.id="+roomId;
		return labReservationDAO.executeQuery(sql, (page-1)*pageSize,pageSize);
	}
	/*************************************************************************************
	 * @功能：根据设备id查询设备使用记录
	 * @作者： 李小龙
	 *************************************************************************************/
	@Override
	public List<LabRoomDeviceReservation> findLabRoomDeviceUseRecord(
			Integer deviceId) {
		//String sql="select l from LabRoomDeviceReservation l where l.labRoomDevice.id="+deviceId+" and l.CAuditResult.id=2";
		String sql="select l from LabRoomDeviceReservation l where l.labRoomDevice.id="+deviceId+" and l.CDictionary.CCategory = 'c_audit_result' and l.CDictionary.CNumber = '2'";
		return labRoomDeviceReservationDAO.executeQuery(sql, 0,-1);
	}
	/*************************************************************************************
	 * @功能：根据设备id查询设备使用记录并分页
	 * @作者： 李小龙
	 *************************************************************************************/
	@Override
	public List<LabRoomDeviceReservation> findLabRoomDeviceUseRecord(
			Integer deviceId, int page, int pageSize) {
		//String sql="select l from LabRoomDeviceReservation l where l.labRoomDevice.id="+deviceId+" and l.CAuditResult.id=2";
		String sql="select l from LabRoomDeviceReservation l where l.labRoomDevice.id="+deviceId+" and l.CDictionary.CCategory = 'c_audit_result' and l.CDictionary.CNumber = '2'";
		return labRoomDeviceReservationDAO.executeQuery(sql, (page-1)*pageSize,pageSize);
	}
	/*************************************************************************************
	 * @功能：根据楼栋编号查询实验室
	 * @作者： 李小龙
	 *************************************************************************************/
	@Override
	public List<LabRoom> findLabRoomByBuildNumber(String buildNumber) {
		String sql="select m from LabRoom m where m.systemBuild.buildNumber="+buildNumber;
		return labRoomDAO.executeQuery(sql, 0,-1);
	}
	/*************************************************************************************
	 * @功能：根据楼栋编号查询实验室并分页
	 * @作者： 李小龙
	 *************************************************************************************/
	@Override
	public List<LabRoom> findLabRoomByBuildNumber(String buildNumber, int page,
			int pageSize) {
		String sql="select m from LabRoom m where m.systemBuild.buildNumber="+buildNumber;
		return labRoomDAO.executeQuery(sql, (page-1)*pageSize,pageSize);
	}
	/*************************************************************************************
	 * @功能：根据学院编号查询实验室设备
	 * @作者： 李小龙
	 *************************************************************************************/
	@Override
	public List<LabRoomDevice> findLabRoomDeviceByAcademy(String academyNumber) {
		
		String sql="select d from LabRoomDevice d where 1=1";
		if(academyNumber!=null&&!academyNumber.equals("")){
			sql+=" and d.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber="+academyNumber;
		}
		return labRoomDeviceDAO.executeQuery(sql, 0,-1);
	}
	/*************************************************************************************
	 * @功能：根据学院编号查询实验室设备并分页
	 * @作者： 李小龙
	 *************************************************************************************/
	@Override
	public List<LabRoomDevice> findLabRoomDeviceByAcademy(String academyNumber,
			int page, int pageSize) {
		String sql="select d from LabRoomDevice d where 1=1";
		if(academyNumber!=null&&!academyNumber.equals("")){
			sql+=" and d.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber="+academyNumber;
		}
		return labRoomDeviceDAO.executeQuery(sql, (page-1)*pageSize,pageSize);
	}
	/**
	 * @Description: 根据ip获取iot中考勤记录数量
	 * @Author: 徐明杭
	 * @CreateDate: 2019/3/25 9:44
	 */
	@Override
	public int findIotAttendanceByIpCount(CommonHdwlog commonHdwlog,String ip,HttpServletRequest request, Integer page, int pageSize){
		List<Object[]> list = getCommonHwdlogList(commonHdwlog,ip,request,page,pageSize);
		return list.size();
	}

	/**
	 * @Description: 根据ip获取iot中考勤记录数量
	 * @Author: 徐明杭
	 * @CreateDate: 2019/3/25 9:44
	 */
	@Override
	public int findIotAttendanceBylabRoomIdCount(CommonHdwlog commonHdwlog,String labRoomId,HttpServletRequest request, Integer page, int pageSize){
		List<Object[]> list = getCommonHwdlogListByLabroomId(commonHdwlog,labRoomId,request,page,pageSize);
		return list.size();
	}

	/**
	 * @Description: 根据ip获取iot中考勤记录
	 * @Author: 徐明杭
	 * @CreateDate: 2019/3/25 10:28
	 */
	@Override
	public List<LabAttendance> findIotAttendanceByIp(CommonHdwlog commonHdwlog,String ip,HttpServletRequest request, Integer page, int pageSize){

		List<Object[]> list = getCommonHwdlogList(commonHdwlog,ip,request,page,pageSize);
		// 实验室名称
		String lab_name = "";
		Set<LabRoomAgent> labRoomAgents = labRoomAgentDAO.findLabRoomAgentByHardwareIp(ip);
		if (labRoomAgents!=null) {
			LabRoomAgent labRoomAgent = labRoomAgents.iterator().next();
			lab_name = labRoomAgent.getLabRoom().getLabRoomName();
		}

		List<LabAttendance> labAttendances = new ArrayList<>();
		for(Object[] temp: list){
			LabAttendance labAttendance = new LabAttendance();
			labAttendance.setCname(temp[0].toString()); //用户
			labAttendance.setUsername(temp[1].toString());//用户工号
			labAttendance.setAcademyName(temp[2].toString());//学院名称
			if (temp[4]!=null){
				labAttendance.setClassName(temp[4].toString());//班级名称
			}else {labAttendance.setClassName("");}
			labAttendance.setMajor(temp[5].toString());//专业
			labAttendance.setAttendanceTime(temp[6].toString());//考勤时间
			labAttendance.setLabRoomName(lab_name);//实验室名称
			labAttendances.add(labAttendance);
		}

		return labAttendances;
	}

	/**
	 * @Description: 根据实验室获取iot中考勤记录
	 * @Author: 林威
	 * @CreateDate: 2019/6/6
	 */
	@Override
	public List<LabAttendance> findIotAttendanceBylabRoomId(CommonHdwlog commonHdwlog,String labNumber,HttpServletRequest request, Integer page, int pageSize){

		List<Object[]> list = getCommonHwdlogListByLabroomId(commonHdwlog,labNumber,request,page,pageSize);
		// 实验室名称
		String lab_name = labRoomDAO.findLabRoomById(Integer.parseInt(labNumber)).getLabRoomName();

		List<LabAttendance> labAttendances = new ArrayList<>();
		for(Object[] temp: list){
			LabAttendance labAttendance = new LabAttendance();
			labAttendance.setCname(temp[0].toString()); //用户
			labAttendance.setUsername(temp[1].toString());//用户工号
			labAttendance.setAcademyName(temp[2].toString());//学院名称
			if (temp[4]!=null){
				labAttendance.setClassName(temp[4].toString());//班级名称
			}else {labAttendance.setClassName("");}
			labAttendance.setMajor(temp[5].toString());//专业
			labAttendance.setAttendanceTime(temp[6].toString());//考勤时间
			labAttendance.setLabRoomName(lab_name);//实验室名称
			labAttendances.add(labAttendance);
		}

		return labAttendances;
	}

	/**
	 * @Description: 根据ip调用存储过程取iot中考勤记录
	 * @Author: 徐明杭
	 * @CreateDate: 2019/3/28 10:28
	 */
	@Override
	public List<Object[]> getCommonHwdlogList(CommonHdwlog commonHdwlog,String ip,HttpServletRequest request, Integer page, int pageSize){
		StringBuffer sql = new StringBuffer("call proc_common_hwdlog(");

		// hardware_ip
		if(ip!=null&&!ip.equals("")){
			sql.append("'"+ip+"'");
		}else {sql.append("''");}
		// username 学号
		if (commonHdwlog.getUsername()!=null&&!commonHdwlog.getUsername().equals("")) {
			sql.append(",'"+commonHdwlog.getUsername()+"'");
		}else {sql.append(",''");}
		// canem 姓名
		if (commonHdwlog.getCardname()!=null&&!commonHdwlog.getCardname().equals("")) {
			sql.append(",'"+commonHdwlog.getCardname()+"' ");
		}else {sql.append(",''");}

		// 考勤时间
		String starttime= (String)request.getAttribute("starttime");
		String endtime=	(String)request.getAttribute("endtime");

		if(starttime!=null && starttime.length()>0 ){
			sql.append( ",'"+starttime +"'");
		}else {sql.append(",''");}

		if(endtime!=null&& endtime.length()>0){
			sql.append( ",'"+endtime+"'");
		}else {sql.append(",''");}

		sql.append(","+page.toString()+"");
		sql.append(","+pageSize+"");
		sql.append(");");

		Query query = entityManager.createNativeQuery(String.valueOf(sql));
		List<Object[]> list = query.getResultList();
		return list;
	}

	/**
	 * @Description: 根据实验室调用存储过程取iot中考勤记录
	 * @Author: 徐明杭
	 * @CreateDate: 2019/3/28 10:28
	 */
	@Override
	public List<Object[]> getCommonHwdlogListByLabroomId(CommonHdwlog commonHdwlog,String labRoomId,HttpServletRequest request, Integer page, int pageSize){
		labRoomId = labRoomDAO.findLabRoomById(Integer.parseInt(labRoomId)).getLabRoomNumber();
		StringBuffer sql = new StringBuffer("call proc_common_hwdlog_roomId(");

		// labroom_id
		if(labRoomId!=null&&!labRoomId.equals("")){
			sql.append("'"+labRoomId+"'");
		}else {sql.append("''");}
		// username 学号
		if (commonHdwlog.getUsername()!=null&&!commonHdwlog.getUsername().equals("")) {
			sql.append(",'"+commonHdwlog.getUsername()+"'");
		}else {sql.append(",''");}
		// canem 姓名
		if (commonHdwlog.getCardname()!=null&&!commonHdwlog.getCardname().equals("")) {
			sql.append(",'"+commonHdwlog.getCardname()+"' ");
		}else {sql.append(",''");}

		// 考勤时间
		String starttime= (String)request.getAttribute("starttime");
		String endtime=	(String)request.getAttribute("endtime");

		if(starttime!=null && starttime.length()>0 ){
			sql.append( ",'"+starttime +"'");
		}else {sql.append(",''");}

		if(endtime!=null&& endtime.length()>0){
			sql.append( ",'"+endtime+"'");
		}else {sql.append(",''");}

		sql.append(","+page.toString()+"");
		sql.append(","+pageSize+"");
		sql.append(");");

		Query query = entityManager.createNativeQuery(String.valueOf(sql));
		List<Object[]> list = query.getResultList();
		return list;
	}

	/**
	 * Description 导出--实验室考勤名单
	 * @param labAttendanceList
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author 陈乐为 2019年5月15日
	 */
	public void exportLabAttendance(List<LabAttendance> labAttendanceList, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//格式化时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lab_name = "";
		//新建一个mapList集合
		List<Map> mapList = new ArrayList<Map>();
		for(LabAttendance attendance : labAttendanceList){
			lab_name = attendance.getLabRoomName();
			// 新建一个HashMap对象
			Map map = new HashMap();
			// 姓名
			if(attendance.getCname()!= null){
				map.put("c_name", attendance.getCname());
			}else{
				map.put("c_name", "*");
			}

			// 学号
			if(attendance.getUsername()!= null){
				map.put("user_name", attendance.getUsername());
			}else{
				map.put("user_name", "*");
			}

			// 学院
			if(attendance.getAcademyName()!= null){
				map.put("academy_name", attendance.getAcademyName());
			}else{
				map.put("academy_name", "*");
			}

			// 班级
			if(attendance.getClassName()!= null){
				map.put("class_name", attendance.getClassName());
			}else{
				map.put("class_name", "*");
			}

			// 专业
			if(attendance.getMajor()!= null){
				map.put("major", attendance.getMajor());
			}else{
				map.put("major", "*");
			}

			// 时间
			if(attendance.getAttendanceTime()!= null){
				map.put("attendance_time", attendance.getAttendanceTime());
			}else{
				map.put("attendance_time", "*");
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
		String title = lab_name+"学生考勤名单表";
		//给表设置表头名
		String[] hearders = new String[] {"姓名", "学号", "学院", "班级", "专业","时间"};
		//属性数组，写数据到excel时的顺序定位
		String[] fields = new String[] {"c_name", "user_name","academy_name","class_name","major","attendance_time"};
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