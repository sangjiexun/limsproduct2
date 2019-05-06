package net.zjcclims.service.device;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.zjcclims.dao.LabRoomDeviceReservationCreditDAO;
import net.zjcclims.dao.LabRoomDeviceReservationDAO;
import net.zjcclims.dao.UserDAO;
import net.zjcclims.dao.ViewTimetableDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.util.HttpClientUtil;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Spring service that handles CRUD requests for LabRoomDeviceReservation entities
 * 
 */
@Service("LabRoomDeviceReservationService")
@Transactional
public class LabRoomDeviceReservationServiceImpl implements
LabRoomDeviceReservationService {
	
	@Autowired LabRoomDeviceReservationDAO labRoomDeviceReservationDAO;
	@Autowired LabRoomDeviceReservationCreditDAO labRoomDeviceReservationCreditDAO;
	@Autowired LabRoomDeviceService labRoomDeviceService;
	@Autowired SchoolDeviceService schoolDeviceService;
	@Autowired  LabRoomService labRoomService;
	@Autowired ViewTimetableDAO viewTimetableDAO;
	@Autowired private UserDAO userDAO;
	@Autowired private ShareService shareService;
	@Autowired private PConfig pConfig;
	@Transactional
	public void deleteLabRoomDeviceReservation(LabRoomDeviceReservation labRoomDeviceReservation) {
		labRoomDeviceReservationDAO.remove(labRoomDeviceReservation);
		labRoomDeviceReservationDAO.flush();
	}

	/************************************
	 *功能：判断当前设备预约是否在导师审核阶段
	 *作者：贺子龙
	 *时间：2015-10-31 
	 ************************************/
	@Override
	public boolean isUnderTeacherAudit(LabRoomDeviceReservation labRoomDeviceReservation) {
		boolean isUnderTeacherAudit = false;
		if (labRoomDeviceReservation.getCAuditResult().getId()==614&&
				labRoomDeviceReservation.getLabRoomDevice().getCDictionaryByTeacherAudit()!=null&&
				labRoomDeviceReservation.getLabRoomDevice().getCDictionaryByTeacherAudit().getId()==621) {//审核中状态且需要导师审核
			if(labRoomDeviceReservation.getLabRoomDevice().getCDictionaryByTeacherAudit()!=null && labRoomDeviceReservation.getLabRoomDevice().getCDictionaryByManagerAudit() != null){
				if (labRoomDeviceReservation.getStage()==0 &&labRoomDeviceReservation.getLabRoomDevice().getCDictionaryByManagerAudit().getId()==621) {
					isUnderTeacherAudit = true;
				}
			}
			
		}
		return isUnderTeacherAudit;
	}

	/************************************
	 *功能：判断当前设备预约是否在实验室管理员审核阶段
	 *作者：贺子龙
	 *时间：2015-10-31 
	 ***********************************/
	@Override
	public boolean isUnderLabManagerAudit(LabRoomDeviceReservation labRoomDeviceReservation) {
		boolean isUnderLabManagerAudit = false;
		if (labRoomDeviceReservation.getCAuditResult().getId()==614
				&&labRoomDeviceReservation.getLabRoomDevice().getCActiveByLabManagerAudit()!=null
				&&labRoomDeviceReservation.getLabRoomDevice().getCActiveByLabManagerAudit().getId()==621) {//审核中状态且需要实验室管理员审核
			
			if ((labRoomDeviceReservation.getStage()==1 
					&& labRoomDeviceReservation.getLabRoomDevice().getCActiveByLabManagerAudit()!=null && labRoomDeviceReservation.getLabRoomDevice().getCActiveByLabManagerAudit().getId()==621 
					&& labRoomDeviceReservation.getLabRoomDevice().getCDictionaryByTeacherAudit()!=null && labRoomDeviceReservation.getLabRoomDevice().getCDictionaryByTeacherAudit().getId()==621) || 
					(labRoomDeviceReservation.getStage()==0 
					&& labRoomDeviceReservation.getLabRoomDevice().getCDictionaryByTeacherAudit()!=null && labRoomDeviceReservation.getLabRoomDevice().getCDictionaryByTeacherAudit().getId()==622)
					) {
				isUnderLabManagerAudit = true;
			}
		}
		return isUnderLabManagerAudit;
	}

	/************************************
	 *功能：判断当前设备预约是否在设备管理员审核阶段
	 *作者：贺子龙
	 *时间：2015-10-31 
	 ***********************************/
	@Override
	public boolean isUnderManagerAudit(LabRoomDeviceReservation labRoomDeviceReservation) {
		boolean isUnderManagerAudit = false;
		if (labRoomDeviceReservation.getCAuditResult().getId()==614
				&&labRoomDeviceReservation.getLabRoomDevice().getCDictionaryByManagerAudit()!=null
				&&labRoomDeviceReservation.getLabRoomDevice().getCDictionaryByManagerAudit().getId()==621) {//审核中状态且需要设备管理员审核
			
			if (	(labRoomDeviceReservation.getLabRoomDevice().getCDictionaryByManagerAudit()!=null && labRoomDeviceReservation.getLabRoomDevice().getCActiveByLabManagerAudit()!=null && labRoomDeviceReservation.getLabRoomDevice().getCDictionaryByTeacherAudit()!=null)&&
					((labRoomDeviceReservation.getStage()==2 && labRoomDeviceReservation.getLabRoomDevice().getCDictionaryByManagerAudit().getId()==621 && labRoomDeviceReservation.getLabRoomDevice().getCActiveByLabManagerAudit().getId()==621 && labRoomDeviceReservation.getLabRoomDevice().getCDictionaryByTeacherAudit().getId()==621) || 
							(labRoomDeviceReservation.getStage()==1 && (labRoomDeviceReservation.getLabRoomDevice().getCDictionaryByTeacherAudit().getId()==622||labRoomDeviceReservation.getLabRoomDevice().getCActiveByLabManagerAudit().getId()==622))||
							(labRoomDeviceReservation.getStage()==0 && labRoomDeviceReservation.getLabRoomDevice().getCDictionaryByTeacherAudit().getId()==622 && labRoomDeviceReservation.getLabRoomDevice().getCActiveByLabManagerAudit().getId()==622))) {
				isUnderManagerAudit = true;
			}
		}
		
		return isUnderManagerAudit;
	}
	
	
	/************************************
	 *功能：找到innerSame相同的设备预约
	 *作者：贺子龙
	 *时间：2016-04-19
	 ***********************************/
	public List<LabRoomDeviceReservation> findInnerSame(int reservationId){
		LabRoomDeviceReservation reservation = labRoomDeviceReservationDAO.findLabRoomDeviceReservationByPrimaryKey(reservationId);
		String innerSame = reservation.getInnerSame();
		String sql = "select l from LabRoomDeviceReservation l where 1=1";
		sql+=" and l.innerSame like '"+innerSame+"'";
		sql+=" and l.id <>"+reservationId;
		List<LabRoomDeviceReservation> reservations = labRoomDeviceReservationDAO.executeQuery(sql, 0, -1);
		if (reservations.size()>0) {
			return reservations;
		}else {
			return null;
		}
		
	}
	
	/****************************************************************************
	 * @功能：根据设备id查询设备的预约记录
	 * @作者：贺子龙
	 * @日期：2016-05-05
	 ****************************************************************************/
	public LabRoomDeviceReservation saveLabRoomDeviceReservation(LabRoomDeviceReservation labRoomDeviceReservation){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		LabRoomDeviceReservation reservationOri= labRoomDeviceReservationDAO.store(labRoomDeviceReservation);
		String dateStr = sdf.format(Calendar.getInstance().getTime());
		// 将设备预约信息同步到该设备的关联设备
		List<SchoolDevice> innerSameDevices = schoolDeviceService.findInnerSameDevice(labRoomDeviceReservation.getLabRoomDevice().getSchoolDevice().getDeviceNumber());
		if (innerSameDevices!=null&&innerSameDevices.size()>0) {
			for (SchoolDevice schoolDevice : innerSameDevices) {
				LabRoomDevice deviceSame = labRoomDeviceService.findLabRoomDeviceByDeviceNumber(schoolDevice.getDeviceNumber());
				if (deviceSame!=null) {
					LabRoomDeviceReservation reservationSame = new LabRoomDeviceReservation();
					reservationSame.copy(reservationOri);
					reservationSame.setLabRoomDevice(deviceSame);
					//reservationSame.setInnerSame(deviceSame.getSchoolDevice().getInnerSame()+"-"+dateStr);
					reservationSame.setInnerDeviceName(deviceSame.getSchoolDevice().getInnerDeviceName().replace("]", "]</br>"));
					reservationSame.setTimetableLabDevice(labRoomDeviceReservation.getTimetableLabDevice());
					//保存排课
					reservationSame.setAppointmentId(labRoomDeviceReservation.getAppointmentId());
					labRoomDeviceReservationDAO.store(reservationSame);
				}
			}
		}
		return reservationOri;
		
	}

	/*
	 * 刷新权限
	 */
	public String refreshPermissions(Integer roomId, HttpServletResponse response) throws IOException {
		
		//根据roomId查询该实验室的门禁
		List<LabRoomAgent> agentList=labRoomService.findLabRoomAgentAccessByRoomId(roomId);
		LabRoomAgent a=new LabRoomAgent();
		if(agentList.size()>0){
			a=agentList.get(0);
		}
		
		String serverUrl="";//服务器地址
		if(a.getCommonServer()!=null){
			//格式------http://192.168.10.252:8080/services/ofthings/acldoor.asp?cmd=registrcard&roomnumber=
			if(a.getCommonServer().getServerSn()!=null&&!a.getCommonServer().getServerSn().equals("")){
				serverUrl="http://"+a.getCommonServer().getServerIp()+":"+a.getCommonServer().getServerSn()+"/services/ofthings/acldoor.asp?cmd=registrcard&roomnumber="+roomId;
			}else{
				serverUrl="http://"+a.getCommonServer().getServerIp()+"/services/ofthings/acldoor.asp?cmd=registrcard&roomnumber="+roomId;
			}
		}		
		System.out.println(serverUrl+"刷新权限");
		URL url=new URL(serverUrl);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection(); 
		// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false; 
		urlConn.setDoOutput(true);
		// 设置是否从httpUrlConnection读入，默认情况下是true;
		urlConn.setDoInput(true);
		// Post 请求不能使用缓存
		urlConn.setUseCaches(false);
		// 设定传送的内容类型是可序列化的java对象  
	    // (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
		urlConn.setRequestProperty("Content-type","application/x-java-serialized-object");
		// 设定请求的方法为"POST"，默认是GET
		urlConn.setRequestMethod("POST"); 
		// 连接，上面对urlConn的所有配置必须要在connect之前完成
		try{
			urlConn.connect();
		}catch(IOException e){
			return "error";
		}
		
		// 此处getOutputStream会隐含的进行connect (即：如同调用上面的connect()方法，  
	    // 所以在开发中不调用上述的connect()也可以)。
		OutputStream outStrm = urlConn.getOutputStream();
		
	    // 调用HttpURLConnection连接对象的getInputStream()函数,  
	    // 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。  
	    InputStream inStrm = urlConn.getInputStream(); // <===注意，实际发送请求的代码段就在这里
	    
	    InputStreamReader  inStream = new InputStreamReader(inStrm,"UTF-8");
	    String inputline="";
	    String info="";//返回的参数
	    BufferedReader buffer=new BufferedReader(inStream);
	    
        while((inputline=buffer.readLine())!=null){
            info+=inputline;
        }
		//System.out.println("返回的参数为："+info);
	    //设置超时时间  
	    HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();  
	    urlCon.setConnectTimeout(30000);  
	    urlCon.setReadTimeout(30000);  
	    if(info.contains("okok")){
	    	return "sucess";
	    }else{
	    	return "error";
	    }
	    
	}
	
	
	/****************************************************************************
	 * @throws IOException 
	 * @功能：根据设备id查询设备的预约记录
	 * @作者：贺子龙
	 * @日期：2016-05-05
	 ****************************************************************************/
	public void saveLabRoomDeviceReservationNew(LabRoomDeviceReservation labRoomDeviceReservation, HttpServletResponse response) throws IOException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		LabRoomDeviceReservation reservationOri= labRoomDeviceReservationDAO.store(labRoomDeviceReservation);
		if(reservationOri.getCAuditResult().getCNumber().equals("2")){
			ViewTimetable viewTimetable = new ViewTimetable();
			viewTimetable.setStartTime(sdf.format(reservationOri.getBegintime().getTime()));
			viewTimetable.setEndTime(sdf.format(reservationOri.getEndtime().getTime()));
			viewTimetable.setLabId(reservationOri.getLabRoomDevice().getLabRoom().getId());
			viewTimetable.setCourseNo("设备预约");
			viewTimetable.setPId("0");
			viewTimetable.setUsername(reservationOri.getUserByReserveUser().getUsername());
			viewTimetableDAO.store(viewTimetable);
			this.refreshPermissions(reservationOri.getLabRoomDevice().getLabRoom().getId(), response);
		}
		
		String dateStr = sdf.format(Calendar.getInstance().getTime());
		// 将设备预约信息同步到该设备的关联设备
		List<SchoolDevice> innerSameDevices = schoolDeviceService.findInnerSameDevice(labRoomDeviceReservation.getLabRoomDevice().getSchoolDevice().getDeviceNumber());
		if (innerSameDevices!=null&&innerSameDevices.size()>0) {
			for (SchoolDevice schoolDevice : innerSameDevices) {
				LabRoomDevice deviceSame = labRoomDeviceService.findLabRoomDeviceByDeviceNumber(schoolDevice.getDeviceNumber());
				if (deviceSame!=null) {
					LabRoomDeviceReservation reservationSame = new LabRoomDeviceReservation();
					reservationSame.copy(reservationOri);
					reservationSame.setLabRoomDevice(deviceSame);
					//reservationSame.setInnerSame(deviceSame.getSchoolDevice().getInnerSame()+"-"+dateStr);
					reservationSame.setInnerDeviceName(deviceSame.getSchoolDevice().getInnerDeviceName().replace("]", "]</br>"));
					reservationSame.setTimetableLabDevice(labRoomDeviceReservation.getTimetableLabDevice());
					//保存排课
					reservationSame.setAppointmentId(labRoomDeviceReservation.getAppointmentId());
					reservationSame = labRoomDeviceReservationDAO.store(reservationSame);
					
					if(reservationSame.getCAuditResult().getCNumber().equals("2")){
						ViewTimetable viewTimetable = new ViewTimetable();
						viewTimetable.setStartTime(sdf.format(reservationSame.getBegintime().getTime()));
						viewTimetable.setEndTime(sdf.format(reservationSame.getEndtime().getTime()));
						viewTimetable.setLabId(reservationSame.getLabRoomDevice().getLabRoom().getId());
						viewTimetable.setCourseNo("设备预约");
						viewTimetable.setPId("0");
						viewTimetable.setUsername(reservationSame.getUserByReserveUser().getUsername());
						viewTimetableDAO.store(viewTimetable);
					}
				}
			}
		}
	}
	/*************************************************************************************
	 * Description 根据设备预约的id查找预约纪录
	 * 
	 * @param id
	 * @author 周志辉
	 * @date 2017-08-10
	 *************************************************************************************/
	@Override
	public LabRoomDeviceReservation findlabRoomDeviceReservationById(Integer id) {
		// TODO Auto-generated method stub
		LabRoomDeviceReservation labRoomDeviceReservation = labRoomDeviceReservationDAO.findLabRoomDeviceReservationById(id);
		return labRoomDeviceReservation;
	}
	/*************************************************************************************
	 * Description 根据设备预约的id查找所有扣分项
	 * 
	 * @param id
	 * @author 周志辉
	 * @date 2017-08-10
	 *************************************************************************************/
	@Override
	public List<LabRoomDeviceReservationCredit> findlabRoomDeviceReservationCreditOptionById(Integer id) {
		// TODO Auto-generated method stub
		String hql="select c from LabRoomDeviceReservationCredit c where 1=1 and c.labRoomDeviceReservation.id="+id;	 
		return labRoomDeviceReservationCreditDAO.executeQuery(hql, 0,-1);
	}

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
	@Override
	public LabRoomDeviceReservation saveALabRoomDeviceReservation(Integer equinemtid, String description, String phone,
																  String teacher, String startDate, String endDate,
																  String research) throws ParseException {
		// id对应的实验室设备
		LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(equinemtid);
		// 当前登录人
		User user = shareService.getUser();
		// 要保存进数据库的预约对象
		LabRoomDeviceReservation reservation = new LabRoomDeviceReservation();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(startDate);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date);
		reservation.setBegintime(calendar1);
		Date date2 = sdf.parse(endDate);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
		reservation.setEndtime(calendar2);
		// 保存审核剩余时间
		if(device.getIsAuditTimeLimit() != null && device.getIsAuditTimeLimit() == 1){
			reservation.setAuditRestTime((long)device.getAuditTimeLimit());
		}
		//先保存一次获取数据库分配的id
		reservation = labRoomDeviceReservationDAO.store(reservation);
		reservation.setLabRoomDevice(device);// 预约的设备
		reservation.setUserByReserveUser(user);// 预约人
		reservation.setContent(description);// 描述
		reservation.setPhone(phone);// 联系电话
		// 申请性质--2016-03-22默认为预约
		CDictionary cReservationProperty = shareService.getCDictionaryByCategory("c_reservation_property", "1");
		reservation.setCReservationProperty(cReservationProperty);

		// 申请@时间
		reservation.setTime(Calendar.getInstance());
		// 申请所属的学期
		SchoolTerm term = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		reservation.setSchoolTerm(term);
		if (research != null && !research.equals("") && !research.equals("null")) {
			ResearchProject researchProject = labRoomDeviceService.findResearchProjectByPrimaryKey(Integer.parseInt(research));
			reservation.setResearchProject(researchProject);
		}
		BigDecimal reserveHours = labRoomDeviceService.getReserveHoursOfReservation(reservation);
		reservation.setReserveHours(reserveHours);
		// 指导教师
		if (!teacher.equals("")) {
			reservation.setUserByTeacher(userDAO.findUserByPrimaryKey(teacher));
		}
		return labRoomDeviceReservationDAO.store(reservation);
	}

	/**
	 * Description 设备预约判冲
	 * @param equinemtid 设备id
	 * @param reservationId 设备预约id
	 * @param calendar1 起始时间
	 * @param calendar2 结束时间
	 * @return 是否可预约（1-可预约，0-不可预约）
	 * @author 黄保钱 2019-1-23
	 */
	@Override
	public Integer judgeConflictForDeviceReservation(Integer equinemtid, Integer reservationId, Calendar calendar1, Calendar calendar2){
		int flag = 1;
		// 根据设备id查询设备的预约记录
		List<LabRoomDeviceReservation> reservationList = labRoomDeviceService.findReservationListByDeviceId(equinemtid);
		// 循环遍历预约记录，看是否和以前的预约有冲突
		for (LabRoomDeviceReservation r : reservationList) {
			if (r.getId().equals(reservationId)) {// 不与自己判冲
				continue;
			}
			Calendar start = r.getBegintime();
			Calendar end = r.getEndtime();
			if (end.after(calendar1) && start.before(calendar2)) {
				flag = 0;
			}
		}
		return flag;
	}

	/**
	 * Description 获取审核人
	 * @param authName 权限名
	 * @param labRoomDeviceReservation 设备预约
	 * @return 审核人列表
	 * @author 黄保钱 2019-1-23
	 */
	@Override
	public List<User> getAuditUser(String authName, LabRoomDeviceReservation labRoomDeviceReservation){
		List<User> auditUsers = new ArrayList<>();
		switch (authName){
			case "TEACHER":
				auditUsers.add(labRoomDeviceReservation.getUserByTeacher());
				break;
			case "CFO":
				auditUsers.addAll(shareService.findDeansByAcademyNumber(labRoomDeviceReservation.getUserByReserveUser().getSchoolAcademy()));
				break;
			case "LABMANAGER":
				Set<LabRoomAdmin> labRoomAdmins = labRoomDeviceReservation.getLabRoomDevice().getLabRoom().getLabRoomAdmins();
				for(LabRoomAdmin labRoomAdmin: labRoomAdmins){
					if(labRoomAdmin.getTypeId() == 1) {
						auditUsers.add(labRoomAdmin.getUser());
					}
				}
				break;
			case "EXCENTERDIRECTOR":
				auditUsers.add(labRoomDeviceReservation.getLabRoomDevice().getLabRoom().getLabCenter().getUserByCenterManager());
				break;
			case "EQUIPMENTADMIN":
				auditUsers.add(labRoomDeviceReservation.getLabRoomDevice().getUser());
				break;
			default:
				auditUsers.addAll(shareService.findUsersByAuthorityName(authName));

		}
		return auditUsers;
	}

	/**
	 * Describing 获取阶段审核信息
	 * @param labRoomDeviceReservation 设备预约
	 * @param stage 阶段
	 * @return 信息
	 * @author 黄保钱 2019-1-23
	 */
	@Override
	public Object[] getCurrJSONObject(LabRoomDeviceReservation labRoomDeviceReservation, Integer stage){
		Map<String, String> params = new HashMap<>();
		String businessType = "DeviceReservation" + labRoomDeviceReservation.getLabRoomDevice().getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber();
		params.put("businessType", pConfig.PROJECT_NAME + businessType);
		params.put("businessUid", labRoomDeviceReservation.getLabRoomDevice().getId().toString());
		params.put("businessAppUid", labRoomDeviceReservation.getId().toString());
		String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessLevelStatus", params);
		JSONObject jsonObject = JSONObject.parseObject(s);
		if("success".equals(jsonObject.getString("status"))) {
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			if(jsonArray != null && jsonArray.size() > 0){
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject o = jsonArray.getJSONObject(i);
					Integer in = o.getIntValue("level");
					if(stage.equals(in)){
						Object[] result = new Object[4];
						result[0] = userDAO.findUserByUsername(o.getString("auditUser"));
						if(result[0] == null){
							return null;
						}
						result[1] = o.getString("result");
						result[2] = o.getString("info");
						String createTime = o.getString("createTime");
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
						Calendar calendar = Calendar.getInstance();
						try {
							calendar.setTime(sdf.parse(createTime));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						result[3] = calendar;
						return result;
					}
				}
			}
		}
		return null;
	}
}
