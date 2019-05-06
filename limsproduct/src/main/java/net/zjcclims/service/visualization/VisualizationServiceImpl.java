package net.zjcclims.service.visualization;

import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.CommonDocumentService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.device.LabRoomDeviceService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.report.TeachingReportService;
import net.zjcclims.service.software.SoftwareService;
import net.zjcclims.service.system.SystemBuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

@Service("VisualizationService")
public class VisualizationServiceImpl implements VisualizationService 
{
	@Autowired
	private LabRoomDAO labRoomDAO;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private LabRoomDeviceService labRoomDeviceService;
	@Autowired
	private LabRoomService labRoomService;
	@Autowired
	private SystemBuildService systemBuildService;
	@Autowired
	private TeachingReportService teachingReportService;
	@Autowired
	private TimetableAppointmentDAO timetableAppointmentDAO;
	@Autowired
	private SchoolCourseStudentDAO schoolCourseStudentDAO;
	@Autowired
	private SoftwareService softwareService;
	@Autowired
	private SchoolDeviceDAO schoolDeviceDAO;
	@Autowired
	private SchoolTermDAO schoolTermDAO;
	@Autowired
	private LabRoomDeviceReservationDAO labRoomDeviceReservationDAO;
	@Autowired
	private OperationItemDAO operationItemDAO;
	@Autowired
	private SchoolAcademyDAO schoolAcademyDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ShareService shareService;
	@Autowired
	private SystemCampusDAO systemCampusDAO;
	@Autowired
	private TimetableCourseStudentDAO timetableCourseStudentDAO;
	@Autowired 
	private LabRoomAdminDAO labRoomAdminDAO;
	@Autowired
	private LabReservationDAO labReservationDAO;
	@Autowired
	private LabRoomDeviceDAO labRoomDeviceDAO;
	@Autowired
	private SystemRoomDAO systemRoomDAO;
	@Autowired
	private CommonDocumentService commonDocumentService;
	@Autowired
	private CommonDocumentDAO documentDAO;
	@Autowired
	private SystemBuildDAO systemBuildDAO;
	@Autowired
	private SystemFloorPicDAO systemFloorPicDAO;
	@Autowired
	private SchoolClassesDAO schoolClassesDAO;

	public VisualizationServiceImpl(){
		
	}
	

	/**
	 * 根据楼宇和楼层查找楼层房间列表
	 * 裴继超
	 * 2016年1月22日
	 */
	@Override
	public List<LabRoom> getLabRoomsByBuildAndFloor(String buildNumber,String floor,
			Integer page,int pageSize)
	{
		String sql = "select r from LabRoom r where 1=1 ";
		if(buildNumber!=null&&buildNumber!=""){
			sql = sql + " and r.systemBuild.buildNumber like '"+buildNumber+"'";
			if(floor!=null&floor!=""){
				sql = sql + " and r.floorNo = " + floor;
			}
		}

		sql = sql + " and r.isUsed = 1 order by r.labRoomNumber";
		List<LabRoom> labRooms = labRoomDAO.executeQuery(sql.toString(), (page-1)*pageSize, pageSize);
		return labRooms;
	}
	/**
	 * 根据楼宇和楼层查找楼层房间列表（所选中心）
	 * 廖文辉
	 * 2018年9月20日
	 */
	public List<LabRoom> getLabRoomsByBuildAndFloorAndLabCenter(String buildNumber,String floor,
																Integer page,int pageSize,String acno) {
		String sql = "select r from LabRoom r where 1=1 ";
		if(buildNumber!=null&&buildNumber!=""){
			sql = sql + " and r.systemBuild.buildNumber like '"+buildNumber+"'";
			if(floor!=null&floor!=""){
				sql = sql + " and r.floorNo =" + floor;
			}
		}
		if(acno!=null && !acno.equals("-1")) {
			sql +=" and r.labCenter.schoolAcademy.academyNumber='"+ acno +"'";
		}
		sql = sql + " and r.isUsed = 1 order by r.labRoomNumber";
		//sql = sql + " and r.labRoomNumber like '"+buildNumber+"%'";
		List<LabRoom> labRooms = labRoomDAO.executeQuery(sql.toString(), (page-1)*pageSize, pageSize);
		return labRooms;
	}
	/**
	 * 根据id查找实验室
	 * 裴继超
	 * 2016年1月23日
	 */
	@Override
	public LabRoom findLabRoomByPrimaryKey(int id){
		LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(id);
		return labRoom;
	}
	
	/**
	 * 保存实验室设备
	 * 裴继超
	 * 2016年1月27日
	 */
	@Override
	public void saveLabRoomDevice(LabRoomDevice labRoomDevice){
		labRoomDeviceDAO.store(labRoomDevice);
		labRoomDeviceDAO.flush();
	}
	
	/**
	 * 保存设备字典
	 * 裴继超
	 * 2016年1月27日
	 */
	@Override
	public SchoolDevice saveSchoolDevice(LabRoomDevice labRoomDevice){
		SchoolDevice schoolDevice = labRoomDevice.getSchoolDevice();
		schoolDevice.setId(0);
		schoolDevice = schoolDeviceDAO.store(schoolDevice);
		schoolDeviceDAO.flush();
		return schoolDevice;
	}
	
	/**
	 * 根据id查找实验室设备
	 * 裴继超
	 * 2016年1月27日
	 */
	@Override
	public LabRoomDevice findLabRoomDeviceByPrimaryKey(int id){
		LabRoomDevice labRoomDevice = labRoomDeviceDAO.findLabRoomDeviceByPrimaryKey(id);
		return labRoomDevice;
	}
	
	/**
	 * 删除实验室设备位置标记
	 * 裴继超
	 * 2016年1月28日
	 */
	@Override
	public void deletLabRoomDeviceXY(int id){
		LabRoomDevice labRoomDevice = labRoomDeviceDAO.findLabRoomDeviceByPrimaryKey(id);
		labRoomDevice.setxCoordinate(null);
		labRoomDevice.setyCoordinate(null);
		labRoomDeviceDAO.remove(labRoomDevice);
		labRoomDeviceDAO.flush();
	}
	
	/**
	 * 删除设备字典
	 * 裴继超
	 * 2016年1月28日
	 */
	@Override
	public void deletSchoolDevice(String schoolDeviceNumber){
		SchoolDevice schoolDevice = schoolDeviceDAO.findSchoolDeviceByPrimaryKey(schoolDeviceNumber);
		schoolDeviceDAO.remove(schoolDevice);
		schoolDeviceDAO.flush();
	}
	
	/**
	 * 根据是否存在坐标查找实验室设备
	 * 裴继超
	 * 2016年3月24日11:09:34
	 */
	@Override
	public List<LabRoomDevice> findLabRoomDevicesByLabRoomIdAndXY(int labRoomId){
		String sql = "select d from LabRoomDevice d where d.labRoom.id = "+labRoomId+" and (d.xCoordinate != null or d.yCoordinate != null) ";
		List<LabRoomDevice> labRoomDevices = labRoomDeviceDAO.executeQuery(sql.toString(), 0, -1);;
		return labRoomDevices;
	}
	
	/**
	 * floor页面替换实验室详细信息map
	 * 裴继超
	 * 2016年3月25日
	 */
	@Override
	public Map findLabRoomMap(LabRoom labRoom){
		Map<String, String> map = new HashMap<String, String>();
		if(labRoom!=null){
			//map.put("labRoomName", labRoom.getLabRoomNumber()+"  "+labRoom.getLabRoomName());
			map.put("labRoomName", "  "+labRoom.getLabRoomName());
			//map.put("adress", (labRoom.getLabRoomAddress()==null)?"":labRoom.getLabRoomAddress());
			String str = "";
			if(labRoom.getSystemBuild()!=null) {
				str += labRoom.getSystemBuild().getSystemCampus().getCampusName()+labRoom.getSystemBuild().getBuildName();
			}
			if(labRoom.getSystemRoom()!=null) {
				str += labRoom.getSystemRoom().getRoomName()+"("+labRoom.getSystemRoom().getRoomNo()+")";
			}
			map.put("adress", str);
			String str1 = "";
			if(labRoom.getSystemRoom()!=null && labRoom.getSystemRoom().getDepartmentNumber()!=null)
				str1 = labRoom.getSystemRoom().getDepartmentNumber();
			map.put("departmentNumber", str1);
			map.put("labRoomNumber", (labRoom.getLabRoomNumber()==null)?"":labRoom.getLabRoomNumber());
			map.put("CLabRoomType", (labRoom.getCDictionaryByLabRoom()==null)?"":labRoom.getCDictionaryByLabRoom().getCName());
			map.put("labRoomCapacity", (labRoom.getLabRoomCapacity()==null)?"":labRoom.getLabRoomCapacity().toString());
			map.put("isUsed", labRoom.getLabRoomActive().toString());
			map.put("appointment", (labRoom.getLabRoomReservation()==null)?"":labRoom.getLabRoomReservation().toString());
			map.put("labRoomArea", (labRoom.getLabRoomArea()==null)?"":labRoom.getLabRoomArea().toString());
			map.put("labRoomIntroduction", (labRoom.getLabRoomIntroduction()==null)?"":labRoom.getLabRoomIntroduction());
			String lastr = "";
			if(labRoom.getLabRoomAdmins() != null && labRoom.getLabRoomAdmins().size()>0){
				for(LabRoomAdmin la:labRoom.getLabRoomAdmins()){
					if(la.getTypeId()==1 && la.getUser() != null) {
						lastr += la.getUser().getCname() + "<br/>";
					}
				}
			}
			map.put("labRoomAdmin", lastr);
			// 实验室设备总价
			BigDecimal totalAssets = labRoomDeviceService.getAgentPriceByLab(labRoom.getId());
			if(totalAssets != null) {
				map.put("totalAssets", totalAssets.toString());
			}else {
				map.put("totalAssets", "0");
			}
		}else{
			map.put("labRoomName","");
			map.put("adress","");
			map.put("departmentNumber","");
			map.put("labRoomNumber","");
			map.put("CLabRoomType","");
			map.put("labRoomCapacity","");
			map.put("isUsed","");
			map.put("appointment","");
			map.put("labRoomArea","");
			map.put("labRoomIntroduction","");
			map.put("labRoomAdmin", "");
			map.put("totalAssets", "");
		}
		return map;
	}
	
	/**
	 * 根据tag查找channel
	 * 裴继超
	 * 2016年3月25日
	 */
	/*@Override
	public List<Channel> findChannelsByTag(int tag){
		String sql = "select c from Channel c join c.tags t where t.id = "+tag;
		List<Channel> channels = channelDAO.executeQuery(sql.toString(), 0, -1);;
		return channels;
	}*/
	
	/****************************************************************************
	 * 功能：保存实验分室
	 * 作者：李小龙
	 * 时间：2014-07-29
	 ****************************************************************************/
	@Override
	public LabRoom save(LabRoom labRoom) {
		// TODO Auto-generated method stub
		return labRoomDAO.store(labRoom);
	}
	
	/**
	 * 根据主键查找字典实验室
	 * 裴继超
	 * 2016年5月27日
	 */
	@Override
	public SystemRoom findSystemRoomByPrimaryKey(String nummber){
		Set<SystemRoom> systemRoom = systemRoomDAO.findSystemRoomByRoomNameContaining(nummber);
		for(SystemRoom i:systemRoom){
			return i;
		}
		return null;
	}
	
	/****************************************************************************
	 * 功能：给实验室上传图片
	 * 作者：裴继超
	 * 时间：2016年5月27日
	 ****************************************************************************/
	@Override
	public void uploadImageForLabRoom(HttpServletRequest request,
			HttpServletResponse response, Integer id,Integer type) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request; 
		 String sep = System.getProperty("file.separator"); 
		 Map files = multipartRequest.getFileMap(); 
		 Iterator fileNames = multipartRequest.getFileNames();
		 boolean flag =false; 
		 String fileDir = request.getSession().getServletContext().getRealPath( "/") +  "upload"+ sep+"labroom"+sep+id;
		//存放文件文件夹名称
		for(; fileNames.hasNext();){
		  String filename = (String) fileNames.next(); 
		  CommonsMultipartFile file = (CommonsMultipartFile) files.get(filename); 
		  byte[] bytes = file.getBytes(); 
		  if(bytes.length != 0) {
			  // 说明申请有附件
			  if(!flag) { 
				  File dirPath = new File(fileDir); 
				  if(!dirPath.exists()) { 
					  flag = dirPath.mkdirs();
		              } 
		      } 
			  String fileTrueName = file.getOriginalFilename(); 
			  //文件重命名
			  int endAddress = fileTrueName.lastIndexOf(".");
			  String ss = fileTrueName.substring(endAddress, fileTrueName.length());//后缀名
			  String fileNewName = "roomImageType"+type+ss; 
			  //System.out.println("文件名称："+fileTrueName);
			  File uploadedFile = new File(fileDir + sep + fileNewName); 
			  //System.out.println("文件存放路径为："+fileDir + sep + fileNewName);
			  try {
				FileCopyUtils.copy(bytes,uploadedFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			  saveLabRoomDocument(fileTrueName,fileNewName,id,type);
		  } 
		}
		
	}
	
	/****************************************************************************
	 * 功能：保存实验分室的文档
	 * 作者：李小龙
	 * 时间：2014-07-29
	 ****************************************************************************/
	public void saveLabRoomDocument(String fileTrueName,String fileNewName, Integer labRoomid,Integer type) {
		// TODO Auto-generated method stub
		//id对应的实验分室
		LabRoom room=labRoomDAO.findLabRoomByPrimaryKey(labRoomid);
		CommonDocument doc=new CommonDocument();
		if(type==4){
			CommonDocument commonDocument = commonDocumentService.findCommonDocumentByLabRoom(room,4);
			if(commonDocument != null){
				doc = commonDocument;
			}
		}
		if(type==3){
			CommonDocument commonDocument = commonDocumentService.findCommonDocumentByLabRoom(room,3);
			if(commonDocument != null){
				doc = commonDocument;
			}
		}
		if(type==1){
			CommonDocument commonDocument = commonDocumentService.findCommonDocumentByLabRoom(room,1);
			if(commonDocument != null){
				doc = commonDocument;
			}
		}
		doc.setType(type);
		doc.setDocumentName(fileTrueName);
		String imageUrl="upload/labroom/"+labRoomid+"/"+fileNewName;
		doc.setDocumentUrl(imageUrl);
		doc.setLabRoom(room);
		documentDAO.store(doc);
	}

	/****************************************************************************
	 * description:删除图片
	 * 
	 * author:于侃
	 * date:2016年9月21日 14:45:13
	 ****************************************************************************/
	public void deleteImageForLabRoom(Integer labRoomid,Integer type,HttpServletRequest request) {
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		String path;
		if(type ==2){
			//id对应的实验分室
			LabRoom room=labRoomDAO.findLabRoomByPrimaryKey(labRoomid);
			List<CommonDocument> commonDocuments = commonDocumentService.findCommonDocumentsByLabRoom(room,type);
			for (CommonDocument c : commonDocuments) {
				path = rootPath+c.getDocumentUrl();
				File file = new File(path);
				file.delete();
				documentDAO.remove(c);
			}
		}else{
			//id对应的实验分室
			LabRoom room=labRoomDAO.findLabRoomByPrimaryKey(labRoomid);
			CommonDocument commonDocument = commonDocumentService.findCommonDocumentByLabRoom(room,type);
			path = rootPath+commonDocument.getDocumentUrl();
			File file = new File(path);
			file.delete();
			documentDAO.remove(commonDocument);
		}
	}
	
	
	/****************************************************************************
	 * @description:删除单独的图片附件
	 * 
	 * @author:魏好
	 * @date:2017年12月06日 
	 ****************************************************************************/
	public void deleteImageForLabRoom(Integer labRoomid,Integer type,Integer photoId,HttpServletRequest request) {
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		//id对应的实验分室
		LabRoom room=labRoomDAO.findLabRoomByPrimaryKey(labRoomid);
		CommonDocument commonDocument = commonDocumentService.findCommonDocumentByLabRoom(room,type);
		String path = rootPath+commonDocument.getDocumentUrl();
		File file = new File(path);
		file.delete();
		documentDAO.remove(commonDocument);
	}
	
	/****************************************************************************
	 * description:查找设备
	 * 
	 * author:张愉
	 * date:2017年11月4日 
	 ****************************************************************************/
	
	@Override
	public List<LabRoomDevice> getLabRoomDeviceBylabroomid(int id,
			Integer page,int pageSize) 
	{
		String sql = "select r from LabRoomDevice r where 1=1 ";
		sql = sql + " and r.labRoom.id = "+id;
		List<LabRoomDevice> labRoomDevices = labRoomDeviceDAO.executeQuery(sql.toString(), (page-1)*pageSize, pageSize);
		return labRoomDevices;
	}
	
	/****************************************************************************
	 * description:下载图片
	 * 
	 * author:于侃
	 * date:2016年9月22日 14:21:28
	 ****************************************************************************/
	public void downloadImageForLabRoom(Integer id,HttpServletRequest request,
			HttpServletResponse response) {
		try{
			//String sep = System.getProperty("file.separator");
			//根路径
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			//当前登陆人
			String username=shareService.getUser().getUsername();
			//String root = System.getProperty("gvsuntms.root");
			//获取下载的文件
			CommonDocument commonDocument = commonDocumentService.findCommonDocumentByPrimaryKey(id);
			//路径
			String filePath = rootPath+commonDocument.getDocumentUrl();
			//名称
			String fileName = commonDocument.getDocumentName();
			FileInputStream fis = new FileInputStream(filePath);
			response.setCharacterEncoding("utf-8");
			//解决上传中文文件时不能下载的问题
			response.setContentType("multipart/form-data;charset=UTF-8");
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes("UTF-8"),"ISO8859-1");// firefox浏览器
			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
				fileName = URLEncoder.encode(fileName, "UTF-8");// IE浏览器
			} else {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}
			
			response.setHeader("Content-Disposition", "attachment;fileName="+fileName.replaceAll("\\+", "%20").replaceAll("%28", "\\(").replaceAll("%29", "\\)").replaceAll("%3B", ";").replaceAll("%40", "@").replaceAll("%23", "\\#").replaceAll("%26", "\\&"));
			
			OutputStream fos = response.getOutputStream();
			byte[] buffer = new byte[8192];
			int count = 0;
			while((count = fis.read(buffer))>0){
				fos.write(buffer,0,count);   
			}
			fis.close();
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				response.getOutputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * floor页面替换实验室设备详细信息map
	 * 魏号好
	 * 2018年1月12日
	 */
	@Override
	public String findLabRoomDeviceMap(LabRoom labRoom){
		StringBuffer str = new StringBuffer();
		//拼接
		//Set<LabRoomDevice> labRoomDeviceSet = labRoom.getLabRoomDevices();
		List<LabRoomDevice> labRoomDeviceList = labRoomDeviceService.getAllDeviceByLabRoomId(labRoom.getId());
		for(int i=0;i<labRoomDeviceList.size();i++){
			int lend = 0;
			int res = 0;
			str.append("<tr>");
			str.append("<td style='vertical-align:middle; text-align:center;'>"+i+"</td>");
			str.append("<td style='vertical-align:middle; text-align:center;'>"+labRoomDeviceList.get(i).getSchoolDevice().getDeviceNumber()+"</td>");
			str.append("<td style='vertical-align:middle; text-align:center;'>"+labRoomDeviceList.get(i).getSchoolDevice().getDeviceName()+"</td>");
			str.append("<td style='vertical-align:middle; text-align:center;'>"+labRoomDeviceList.get(i).getSchoolDevice().getDevicePattern()+"</td>");
			str.append("<td style='vertical-align:middle; text-align:center;'>"+labRoomDeviceList.get(i).getSchoolDevice().getDevicePrice()+"</td>");
			if(labRoomDeviceList.get(i).getCDictionaryByAllowLending()!=null&&labRoomDeviceList.get(i).getCDictionaryByAllowLending().getId()==621){
			str.append("<td style='vertical-align:middle; text-align:center;'>"+"可借用"+
            "</td>");
			lend=1;
			}else{
			str.append("<td style='vertical-align:middle; text-align:center;'>"+"不可借用"+
			"</td>");
			}
			if (labRoomDeviceList.get(i).getCDictionaryByAllowAppointment() != null && labRoomDeviceList.get(i).getCDictionaryByAllowAppointment().getCNumber().equals("1")) {
				str.append("<td style='vertical-align:middle; text-align:center;'>可预约</td>");
				res = 1;
			} else {
				str.append("<td style='vertical-align:middle; text-align:center;'>不可预约</td>");
			}
			
			/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			try{
				String time = sdf.format(i.getSchoolDevice().getDeviceBuyDate().getTime().getTime());
				str.append("<td style='vertical-align:middle; text-align:center;'>"+time+"</td>");
			}catch(Exception e){
				str.append("<td style='vertical-align:middle; text-align:center;'></td>");
			}*/
			//str.append("<td style='vertical-align:middle; text-align:center;'><a href='/zjcclims/device/listLabRoomDevice?page=1&isReservation=1&isOrder=1' target='_blank'>预约&nbsp&nbsp</a><a href='/zjcclims/device/allLendableDeviceList?currpage=1' target='_blank'>借用</a> ");
			// 以下为操作栏，可视化直接借用、预约逻辑
//			str.append("<td style='vertical-align:middle; text-align:center;'>");
//			if (lend == 1){
//				str.append("<a href='/zjcclims/device/allLendableDeviceList?currpage=1' target='_blank'>借用&nbsp&nbsp</a>");
//			}
//			if(res == 1){
//				str.append("<a href='/zjcclims/device/listLabRoomDevice?page=1&isReservation=1&isOrder=1' target='_blank'>预约</a>");
//			}</td>
			str.append("</tr>");
			
		}
		
		return str.toString();
	}

	/**
	 * 页面替换实验室当前课表详细信息map
	 * 汪哲玮
	 * 2018年4月15日
	 */
	@Override
	public Map<String, String> findLabRoomTimetableMap(LabRoom labRoom) {
		Map<String, String> map = new HashMap<String, String>();
		if(labRoom!=null){
			Calendar calendar = Calendar.getInstance();
			//获取当前学期周次星期
			SchoolTerm term = shareService.getBelongsSchoolTerm(calendar);
			int week = shareService.getBelongsSchoolWeek(calendar);
			int weekday = shareService.getBelongsSchoolWeekday(calendar);
			int belongsClass = shareService.getBelongsClass(calendar);
			// 获取实验分室排课记录
			if (teachingReportService.getCurrentLabTimetableAppointments(term.getId(), labRoom.getId(), week, weekday, belongsClass).size()!=0) {
				for (TimetableLabRelated timetable : teachingReportService.getCurrentLabTimetableAppointments(term.getId(), labRoom.getId(), week, weekday, belongsClass)) {
					//当前课程名称
					map.put("currentCourseName", timetable.getTimetableAppointment().getSchoolCourseInfo().getCourseName());
					//当前授课教师
					map.put("currentCourseTeacher", timetable.getTimetableAppointment().getTimetableTeacherRelateds().iterator().next().getUser().getCname());
					//当前上课班级名称
					/*map.put("currentClassName", timetable.getTimetableAppointment().getSchoolClasses().getClassName());*/
					String classes = "";
					if (timetable.getTimetableAppointment().getSchoolCourse().getSchoolClasseses() != null
							&& timetable.getTimetableAppointment().getSchoolCourse().getSchoolClasseses().size() > 0) {
						for (SchoolClasses classes1 : timetable.getTimetableAppointment().getSchoolCourse().getSchoolClasseses()) {
							classes += classes1.getClassName() + " ";
						}
						map.put("currentClassName", classes);
					}
				}

			}else {
				map.put("currentCourseName", "");
				map.put("currentCourseTeacher", "");
				map.put("currentClassName", "");
			}
		}else{
			map.put("currentCourseName", "");
			map.put("currentCourseTeacher", "");
			map.put("currentClassName", "");
		}
		return map;
	}

	/**
	 * 页面替换实验室软件详细信息map
	 * 汪哲玮
	 * 2018年4月15日
	 */
	@Override
	public String findLabRoomSoftwareMap(LabRoom labRoom) {
		StringBuffer str = new StringBuffer();
			List<Software> listSoftware=softwareService.findSoftwareByRoomId(labRoom.getId());
			
			for(Software i:listSoftware){
				str.append("<tr>");
				str.append("<td style='vertical-align:middle; text-align:center;'>"+i.getName()+"</td>");
				
				str.append("<td style='vertical-align:middle; text-align:center;'>"+i.getEdition()+"</td>");
				
				/*str.append("<td style='vertical-align:middle; text-align:center;'>*</td>");*/
				str.append("<td style='vertical-align:middle; text-align:center;'>"+i.getPrice()+"</td>");
				str.append("</tr>");
			}
			return str.toString();
	}

	/**
	 * 页面替换实验室设备详细信息mapBy筛选条件
	 * 汪哲玮
	 * 2018年4月15日
	 */
	@Override
	public String findLabRoomDeviceMapSearched(LabRoom labRoom,
			String deviceName,int deviceLend,int deviceAppoint) {
		StringBuffer str = new StringBuffer();
		int snum = 0;
		//拼接
		//Set<LabRoomDevice> labRoomDeviceSet = labRoom.getLabRoomDevices();
		List<LabRoomDevice> labRoomDeviceList = labRoomDeviceService.getAllDeviceByLabRoomIdAndLabDevice(labRoom.getId(),deviceName,deviceLend,deviceAppoint);
		for(LabRoomDevice i:labRoomDeviceList){
			snum++;
			str.append("<tr>");
			str.append("<td style='vertical-align:middle; text-align:center;'>"+snum+"</td>");
			str.append("<td style='vertical-align:middle; text-align:center;'>"+i.getSchoolDevice().getDeviceNumber()+"</td>");
			str.append("<td style='vertical-align:middle; text-align:center;'>"+i.getSchoolDevice().getDeviceName()+"</td>");
			/*str.append("<td style='vertical-align:middle; text-align:center;'>*</td>");*/
			str.append("<td style='vertical-align:middle; text-align:center;'>"+i.getSchoolDevice().getDevicePattern()+"</td>");
			/*str.append("<td style='vertical-align:middle; text-align:center;'>"+i.getSchoolDevice().getDeviceFormat()+"</td>");*/
			str.append("<td style='vertical-align:middle; text-align:center;'>"+i.getSchoolDevice().getDevicePrice()+"</td>");
			if(i.getCDictionaryByAllowLending().getId()==621){
			str.append("<td style='vertical-align:middle; text-align:center;'>"+"可借用"+
            "</td>");
			}else{
			str.append("<td style='vertical-align:middle; text-align:center;'>"+"不可借用"+
			"</td>");
			}
			if(i.getCDictionaryByAllowAppointment()!=null && i.getCDictionaryByAllowAppointment().getCNumber().equals("1")){
				str.append("<td style='vertical-align:middle; text-align:center;'>可预约</td>");	
			}else{
				str.append("<td style='vertical-align:middle; text-align:center;'>不可预约</td>");	
			}
			
			/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			try{
				String time = sdf.format(i.getSchoolDevice().getDeviceBuyDate().getTime().getTime());
				str.append("<td style='vertical-align:middle; text-align:center;'>"+time+"</td>");
			}catch(Exception e){
				str.append("<td style='vertical-align:middle; text-align:center;'></td>");
			}*/
//			str.append("<td style='vertical-align:middle; text-align:center;'><a href='/zjcclims/device/listLabRoomDevice?page=1&isReservation=1&isOrder=1' target='_blank'>预约&nbsp&nbsp</a><a href='/zjcclims/device/allLendableDeviceList?currpage=1' target='_blank'>借用</a> ");
			str.append("</tr>");
			
		}
		return str.toString();
	}

	/**
	 * 页面替换实验室楼全部实验室课表列表
	 * 汪哲玮
	 * 2018年4月19日
	 */
	@Override
	public String findLabBuildingTimetableMap(String buildnumber, String campusNumber) {
		List<SystemBuild> systemBuilds=new ArrayList<SystemBuild>();
		StringBuffer str=new StringBuffer();
		if(buildnumber.equals("-1")){
			 systemBuilds = systemBuildService.findBuildByCampusId(campusNumber);
			String campusName = systemCampusDAO.findSystemCampusByPrimaryKey(campusNumber).getCampusName();
			 str.append("<caption><font><span>"+campusName+"所有实验楼今日课表</span></font><div></div></caption>");
		}else{
			SystemBuild build = systemBuildService.findBuildingbyBuildNumber(buildnumber);
			systemBuilds.add(build);
			str.append("<caption><font><span>"+build.getBuildName()+"今日课表</span></font><div></div></caption>");
		}
		str.append("<tr><th>课程名称</th><th>班级</th><th>实验室</th><th>上课时间</th><th>教师</th></tr>");

		Calendar calendar = Calendar.getInstance();
		//获取当前学期周次星期
		SchoolTerm term = shareService.getBelongsSchoolTerm(calendar);
		int week = shareService.getBelongsSchoolWeek(calendar);
		int weekday = shareService.getBelongsSchoolWeekday(calendar);

		List<TimetableAppointmentSameNumber> list = new ArrayList<>();
		for(SystemBuild build: systemBuilds){
			buildnumber=build.getBuildNumber();
			List<LabRoom> labRooms = new ArrayList<LabRoom>();
			labRooms.addAll(this.getLabRoomsByBuildAndFloor(buildnumber,"",1,-1));
			for(LabRoom labRoom : labRooms){
				if(!teachingReportService.getLabAppointmentsByWeekday(term.getId(), -1, labRoom.getId(), week, weekday).isEmpty()){
					for(TimetableLabRelated labRelated : teachingReportService.getLabAppointmentsByWeekday(term.getId(), labRoom.getLabCenter().getId(), labRoom.getId(), week, weekday)) {
						TimetableAppointment appointment = labRelated.getTimetableAppointment();
						Set<TimetableAppointmentSameNumber> sameNumber=appointment.getTimetableAppointmentSameNumbers();
						list.addAll(sameNumber);
					}
				}
			}
		}
		Collections.sort(list, new Comparator<TimetableAppointmentSameNumber>() {
			public int compare(TimetableAppointmentSameNumber arg0, TimetableAppointmentSameNumber arg1) {
				if (arg0.getStartClass() != null && arg1.getStartClass() != null) {
					return arg0.getStartClass()
							.compareTo(arg1.getStartClass());
				}  else {
					return 0;
				}

			}
		});
		for(TimetableAppointmentSameNumber timetableAppointmentSameNumber :list) {
			if(timetableAppointmentSameNumber.getStartWeek()<=week && timetableAppointmentSameNumber.getEndWeek()>=week){
				String classes = "";
				if (timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourse() != null) {
					if (timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourse().getSchoolClasseses() != null
							&& timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourse().getSchoolClasseses().size() > 0) {
						for (SchoolClasses classes1 : timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourse().getSchoolClasseses()) {
							classes += classes1.getClassName() + "(" + classes1.getClassStudentsNumber() + "人) ";
						}
					}else if(timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourse().getSchoolCourseDetails().size()>0){
						String no = timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourse().getSchoolCourseDetails().iterator().next().getCourseDetailNo();
						String sql = "select distinct c from SchoolClasses c join c.users u join u.schoolCourseStudentsForStudentNumber scs where 1=1";
						sql += " and scs.schoolTerm.id ="+term.getId();
						sql += " and scs.schoolCourseDetail.courseDetailNo ='"+no+"'";
						List<SchoolClasses> schoolClasses = schoolClassesDAO.executeQuery(sql,0,0);
						if(schoolClasses.size()>0){
							for(SchoolClasses sc: schoolClasses){
								String scssql = "select distinct scs from SchoolCourseStudent scs left join scs.userByStudentNumber.schoolClasses sc where 1=1";
								scssql += " and sc.classNumber ='"+sc.getClassNumber()+"'";
								scssql += " and scs.schoolTerm.id ="+term.getId();
								scssql += " and scs.schoolCourseDetail.courseDetailNo ='"+no+"'";
								Integer num = schoolCourseStudentDAO.executeQuery(scssql,0,0).size();
								classes += sc.getClassName() +"("+num+"人) ";
							}
						}else {
							classes = "-";
						}
					}else {
						classes = "-";
					}
				}else if(timetableAppointmentSameNumber.getTimetableAppointment().getTimetableStyle() == 7 &&
						timetableAppointmentSameNumber.getTimetableAppointment().getLabReservation().size()>0){
					for(LabReservation lr: timetableAppointmentSameNumber.getTimetableAppointment().getLabReservation()){
						classes += lr.getSchoolClasses().getClassName() +"("+lr.getSchoolClasses().getClassStudentsNumber()+"人) ";
					}
				} else {
					classes = "-";
				}
				String courseName = "";
				if(timetableAppointmentSameNumber.getTimetableAppointment().getTimetableStyle() == 7){
					courseName = "实验室预约/设备预约";
				}
				if(timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourse()!=null
						&& timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourse().getCourseName()!=null) {
					courseName = timetableAppointmentSameNumber.getTimetableAppointment().getSchoolCourse().getCourseName();
				}
				LabRoom labRoom = timetableAppointmentSameNumber.getTimetableAppointment().getTimetableLabRelateds().iterator().next().getLabRoom();
				str.append("<tr><td style='vertical-align:middle; text-align:center;'>"+courseName+
						"</td><td style='vertical-align:middle; text-align:center;'>"+classes+
						"</td><td style='vertical-align:middle; text-align:center;'>["+labRoom.getLabRoomNumber()+"]"+labRoom.getLabRoomName()+
						"</td><td style='vertical-align:middle; text-align:center;'>第");
				if(timetableAppointmentSameNumber.getStartClass().intValue()!=timetableAppointmentSameNumber.getEndClass().intValue()){
					str.append(timetableAppointmentSameNumber.getStartClass()+"-"+timetableAppointmentSameNumber.getEndClass()+"节");
				}else {
					str.append(timetableAppointmentSameNumber.getStartClass()+"节");
				}
				str.append("</td><td style='vertical-align:middle; text-align:center;'>");
				String teacherName = "";
				if(timetableAppointmentSameNumber.getTimetableAppointment().getTimetableStyle() == 7){
					teacherName = timetableAppointmentSameNumber.getTimetableAppointment().getLabReservation().iterator().next().getUser().getCname()+" ";
				}
				if(timetableAppointmentSameNumber.getTimetableAppointment().getTimetableTeacherRelateds() != null && timetableAppointmentSameNumber.getTimetableAppointment().getTimetableTeacherRelateds().size() != 0) {
					for (TimetableTeacherRelated teacher : timetableAppointmentSameNumber.getTimetableAppointment().getTimetableTeacherRelateds()) {
						teacherName = teacher.getUser().getCname()+" ";
					}
				}
				str.append(teacherName);
				str.append("</td></tr>");
			}
		}
		str.append("<tr></tr>");
		return str.toString();
	}

	/**
	 * 页面替换实验室楼全部实验室视频设备列表
	 * 汪哲玮
	 * 2018年4月19日
	 */
	@Override
	public String findLabRoomAgent(LabRoom labRoom) {
		List<LabRoomAgent> labRoomAgents = labRoomService.getAgentByType(labRoom.getId(), "3", "c_agent_type");
		StringBuffer str=new StringBuffer();
		str.append( "<select onchange='changeRoomMovie2(this.options[this.options.selectedIndex].value.split(\"/\")[0] , this.options[this.options.selectedIndex].value.split(\"/\")[1])'>");
		int j=1;
		for(LabRoomAgent labRoomAgent : labRoomAgents){
			str.append("<option value='"+labRoom.getId()+"/"+labRoomAgent.getId()+"'>监控"+
			j+"</option>");
			j++;
		}
		str.append("</select>");
		return str.toString();
	}
	
	/**
	 * 查找实验室楼全部实验室视频设备列表
	 * 汪哲玮
	 * 2018年5月10日
	 */
	@Override
	public List<LabRoomAgent> findLabRoomAgentList(LabRoom labRoom) {
		List<LabRoomAgent> agentList = labRoomService.findLabRoomAgentByRoomId(labRoom.getId());
		StringBuffer str=new StringBuffer();
		str.append( "<select onchange='changeRoomMovie2(this.options[this.options.selectedIndex].value.split(\"/\")[0] , this.options[this.options.selectedIndex].value.split(\"/\")[1])'>");
		int j=1;
		for(int i=1; i<=agentList.size();i++){
			if (shareService.checkCDictionary( agentList.get(i-1).getCDictionary().getId(),"3","c_agent_type")
					&& agentList.get(i-1).getCDictionary().getEnabled()) {
		str.append("<option value='"+labRoom.getId()+"/"+agentList.get(i-1).getId()+"'>监控"+
		j+"</option>");
		j++;
			}
		}
		str.append("</select>");
		return agentList;
	}
	/**
	 * 联动查询（樓宇编号和层数）
	 *
	 * 2018年8月29日
	 */
	public String LinkBuildNumberAndFloorNum(String buildNumber,HttpServletRequest request){
		List<SystemFloorPic> floorPics = this.findSystemFloorPic(buildNumber, null);
		String floor="<option value=''>请选择</option>";
		if(floorPics != null && floorPics.size() > 0) {
			for (SystemFloorPic pic : floorPics) {
				floor += "<option value=" + pic.getFloorNo() + ">" + pic.getFloorName() + "</option>";
			}
		}else {
			floor+="<option value=''>请先设置楼层数</option>";
		}
		String floorValue = shareService.htmlEncode(floor);

		return floorValue;
	}

	/**
	 * @Description 根据楼宇和楼层获取楼层图
	 * @author 张德冰
	 * @data 2018-10-29
	 */
	@Override
	public List<SystemFloorPic> findSystemFloorPic(String buildNumber, Integer floor){
		String sql="select s from SystemFloorPic s where 1=1";
		if(!buildNumber.equals("") && buildNumber != null) {
			sql += " and s.systemBuild = '"+ buildNumber +"'";
		}
		if(floor != null) {
			sql += " and s.floorNo = " + floor;
		}
		sql += " order by s.floorNo";
		List<SystemFloorPic> systemFloorPic = systemFloorPicDAO.executeQuery(sql);
		return systemFloorPic;
	}

	/*********************************************************************
	 * Description:获取所有实验室设备表信息
	 * @author: lay
	 * @date :2018/11/20
	 * @return:
	 **********************************************************************/
	@Override
	public List<LabRoomDevice> getAllLabRoomDevices(LabRoomDevice labRoomDevice,Integer currPage,Integer pageSize ){
		//查询语句
		String sql="select l from LabRoomDevice l where 1=1";
		if(labRoomDevice!=null&&labRoomDevice.getLabRoom()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex().getLabCenter()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemBuild() != null
				&&!labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemBuild().getBuildName().equals("")) {
			sql += " and l.labRoom.labAnnex.labCenter.systemBuild.buildName like '%"+labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemBuild().getBuildName() +"%'";
		}
		if(labRoomDevice!=null&&labRoomDevice.getLabRoom()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex().getLabCenter()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemCampus() != null
				&& labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemCampus().getCampusNumber() != null
				&& !labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemCampus().getCampusNumber().equals("")){
			sql += " and l.labRoom.labAnnex.labCenter.systemCampus.campusNumber = "+labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemCampus().getCampusNumber();
		}
		if(labRoomDevice!=null&& labRoomDevice.getSchoolDevice() != null && labRoomDevice.getSchoolDevice().getDeviceName() != null
				&& !labRoomDevice.getSchoolDevice().getDeviceName().equals("") ){
			sql += " and l.schoolDevice.deviceName like '%"+labRoomDevice.getSchoolDevice().getDeviceName()+"%'";
		}
		if(labRoomDevice!=null&&labRoomDevice.getLabRoom() != null && labRoomDevice.getLabRoom().getLabRoomName() != null
				&& !labRoomDevice.getLabRoom().getLabRoomName().equals("") ){
			sql += " and l.labRoom.labRoomName like '%"+labRoomDevice.getLabRoom().getLabRoomName()+"%'";
		}
		if(labRoomDevice!=null&&labRoomDevice.getLabRoom()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex().getLabCenter()!=null&&
				labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSchoolAcademy() != null && labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSchoolAcademy().getAcademyNumber() != null
				&& !labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSchoolAcademy().getAcademyNumber().equals("") && labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSchoolAcademy().getAcademyNumber().equals("00")){
			sql += " and l.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber = "+labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSchoolAcademy().getAcademyNumber();
		}
		List<LabRoomDevice> labRoomDevices = labRoomDeviceDAO.executeQuery(sql, (currPage - 1) * pageSize, pageSize);
		return labRoomDevices;
	}


	/*********************************************************************
	 * Description:获取所有校区
	 * @author: lay
	 * @date :2018/11/22
	 * @return:
	 **********************************************************************/
	@Override
	public List<SystemCampus> getAllSystemCampus(LabRoomDevice labRoomDevice,Integer currPage,Integer pageSize){
		StringBuffer hql = new StringBuffer("select c from SystemCampus c where 1=1");
		if(labRoomDevice!=null&&labRoomDevice.getLabRoom()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex().getLabCenter()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemCampus() != null
				&& labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemCampus().getCampusNumber() != null
				&& !labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemCampus().getCampusNumber().equals("")){
			hql.append(" and c.campusNumber = '"+labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemCampus().getCampusNumber()+"'");
		}
		return systemCampusDAO.executeQuery(hql.toString(), (currPage-1)*pageSize, pageSize);
	}

	/*********************************************************************
	 * Description:获取所有楼宇
	 * @author: lay
	 * @date :2018/11/22
	 * @return:
	 **********************************************************************/
	@Override
	public List<SystemBuild> getAllSystemBuilds(LabRoomDevice labRoomDevice, Integer currPage, Integer pageSize) {
		String hql = new String("select c from SystemBuild c where 1=1");
		if(labRoomDevice!=null&&labRoomDevice.getLabRoom()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex().getLabCenter()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemCampus() != null
				&& labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemCampus().getCampusNumber() != null
				&& !labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemCampus().getCampusNumber().equals("")){
			hql += " and c.systemCampus.campusNumber = "+labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemCampus().getCampusNumber();
		}
		if(labRoomDevice!=null&&labRoomDevice.getLabRoom()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex().getLabCenter()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemBuild() != null
				&&!labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemBuild().getBuildName().equals("")) {
			hql += " and c.buildName like '%"+labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemBuild().getBuildName() +"%'";
		}
		return systemBuildDAO.executeQuery(hql, (currPage-1)*pageSize, pageSize);
	}

	/*********************************************************************
	 * Description:获取所有房间
	 * @author: lay
	 * @date :2018/11/22
	 * @return:
	 **********************************************************************/
	@Override
	public List<SystemRoomVisual> getAllSystemRooms(LabRoomDevice labRoomDevice, Integer currPage, Integer pageSize) {
		StringBuffer hql = new StringBuffer(" select lr.id,(select count(l.id) from lab_room_device l " +
				" where l.lab_room_id = lr.id)," +
				" sc.campus_name," +
				" sb.build_number," +
				" sb.build_name," +
				" lr.floor_no," +
				" lr.lab_room_number," +
				" lr.lab_room_name" +
				" from lab_room as lr " +
				" join system_room as sr on sr.room_number = lr.system_room" +
				" join system_build as sb on sb.build_number = lr.build_number" +
				" join system_campus as sc on sc.campus_number = sb.campus_number where 1=1 " );
		if(labRoomDevice!=null&&labRoomDevice.getLabRoom()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex().getLabCenter()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemCampus() != null
				&& labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemCampus().getCampusNumber() != null
				&& !labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemCampus().getCampusNumber().equals("")){
			hql.append(" and sc.campus_number = "+labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemCampus().getCampusNumber());
		}
		if(labRoomDevice!=null&&labRoomDevice.getLabRoom()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex().getLabCenter()!=null
				&&labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemBuild() != null
				&&!labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemBuild().getBuildName().equals("")) {
			hql.append( " and sb.build_name like '%"+labRoomDevice.getLabRoom().getLabAnnex().getLabCenter().getSystemBuild().getBuildName() +"%'");
		}
		if(labRoomDevice!=null&&labRoomDevice.getLabRoom() != null && labRoomDevice.getLabRoom().getLabRoomName() != null
				&& !labRoomDevice.getLabRoom().getLabRoomName().equals("") ){
			hql.append(" and lr.lab_room_name like '%"+labRoomDevice.getLabRoom().getLabRoomName()+"%'");
		}
		hql.append(" ORDER BY sr.room_number ");
		if(pageSize!=0){
			hql.append(" limit "+(currPage-1)*pageSize+","+pageSize+"");
		}
		Query query = entityManager.createNativeQuery(hql.toString());
		List<Object[]> objects = query.getResultList();
		List<SystemRoomVisual> list = new ArrayList<>();
		for(Object[] o:objects){
			SystemRoomVisual systemRoomVisual = new SystemRoomVisual();
			if(o[0]!=null){
				systemRoomVisual.setRoomNumber(o[0].toString());
			}
			if(o[1]!=null){
				systemRoomVisual.setDeviceNo(Integer.parseInt(o[1].toString()));
			}
			if(o[2]!=null){
				systemRoomVisual.setCampusName(o[2].toString());
			}
			if(o[3]!=null){
				systemRoomVisual.setBuildNumber(o[3].toString());
			}
			if(o[4]!=null){
				systemRoomVisual.setBuildName(o[4].toString());
			}
			if(o[5]!=null){
				systemRoomVisual.setFloorNo(Integer.parseInt(o[5].toString()));
			}
			if(o[6]!=null){
				systemRoomVisual.setRoomNo(o[6].toString());
			}
			if(o[7]!=null){
				systemRoomVisual.setRoomName(o[7].toString());
			}
			list.add(systemRoomVisual);
		}
		return  list;
	}

	/**
	 * Description 根据楼宇编号获取
	 * @param buildNumber
	 * @return
	 * @author 陈乐为 2018-11-28
	 */
	public String LinkSystemRoomAndBuildNumber(String buildNumber){
		String sql = "select s from SystemRoom s where 1=1";
		sql += " and s.systemBuild.buildNumber='"+buildNumber+"'";
		List<SystemRoom> systemRooms = systemRoomDAO.executeQuery(sql, 0, -1);
		String sysr = "<option value=''>请选择</option>";
		for(SystemRoom systemRoom : systemRooms) {
			if(!EmptyUtil.isStringEmpty(systemRoom.getRoomNo())) {
				sysr += "<option value="+systemRoom.getRoomNumber()+">("+systemRoom.getRoomNo()+")"+systemRoom.getRoomName()+"</option>";
			}else {
				sysr += "<option value="+systemRoom.getRoomNumber()+">"+systemRoom.getRoomName()+"</option>";
			}
		}
		return shareService.htmlEncode(sysr);
	}

	/*************************************************************************************
	 * Description:根据校区id获取楼宇
	 *
	 * @author: 杨新蔚
	 * @date: 2018/12/11
	 *************************************************************************************/
	public List<Map<String,String>> getBuildByCampus(String CampusId){
		String sql = "select s from SystemBuild s where s.systemCampus.campusNumber="+CampusId;
		List<SystemBuild> systemBuilds = systemBuildDAO.executeQuery(sql, 0, -1);
		List<Map<String,String>> systemBuildList=new ArrayList<>();
		for(SystemBuild sb:systemBuilds){
			Map<String,String> systemBuildMap=new HashMap<>();
			systemBuildMap.put("id",sb.getBuildNumber());
			systemBuildMap.put("name",sb.getBuildName());
			systemBuildList.add(systemBuildMap);
		}
		return systemBuildList;
	}

	/*************************************************************************************
	 * Description:根据楼宇id获取实验室
	 *
	 * @author: 杨新蔚
	 * @date: 2018/12/11
	 *************************************************************************************/
	public List<Map<String,String>> getRoomByBuild(String buildNumber){
		String sql = "select s from LabRoom s where s.systemBuild.buildNumber="+buildNumber;
		List<LabRoom> systemRooms = labRoomDAO.executeQuery(sql, 0, -1);
		List<Map<String,String>> labRoomList=new ArrayList<>();
		for(LabRoom lr:systemRooms){
			Map<String,String> labRoomMap=new HashMap<>();
			labRoomMap.put("id",lr.getId().toString());
			labRoomMap.put("name",lr.getLabRoomName());
			labRoomList.add(labRoomMap);
		}
		return labRoomList;
	}

	/*************************************************************************************
	 * Description:根据实验室id获取设备
	 *
	 * @author: 杨新蔚
	 * @date: 2018/12/11
	 *************************************************************************************/
	public List<Map<String,String>> getDeviceByRoom(String labRoomId){
		String sql = "select s from LabRoomDevice s where s.labRoom.id="+Integer.parseInt(labRoomId);
		List<LabRoomDevice> labRoomDevices = labRoomDeviceDAO.executeQuery(sql, 0, -1);
		List<Map<String,String>> labRoomDeviceList=new ArrayList<>();
		for(LabRoomDevice lrd:labRoomDevices){
			Map<String,String> labRoomDeviceMap=new HashMap<>();
			labRoomDeviceMap.put("id",lrd.getId().toString());
			labRoomDeviceMap.put("name",lrd.getSchoolDevice().getDeviceName());
			labRoomDeviceList.add(labRoomDeviceMap);
		}
		return labRoomDeviceList;
	}
	/*************************************************************************************
	 * Description:根据楼宇和楼层查找楼层房间列表,根据权限添加学院筛选
	 *
	 * @author: 廖文辉
	 * @date: 2018/12/21
	 *************************************************************************************/
	public List<LabRoom> getLabRoomsByBuildAndFloorAndAcno(String buildNumber, Integer floor,
														   Integer page, int pageSize,HttpServletRequest request) {
		String sql = "select r from LabRoom r where 1=1 ";
		if(buildNumber!=null&&buildNumber!=""){
			sql = sql + " and r.systemBuild.buildNumber like '"+buildNumber+"'";
			if(floor != null){
				sql = sql + " and r.floorNo = " + floor;
			}
		}
		// 获取当前系统权限
		String auth = request.getSession().getAttribute("selected_role").toString();
		// 根据权限等级筛选
		int authLevel = shareService.getLevelByAuthName(auth);
		if(authLevel==5||authLevel==3 || authLevel==4) {
			sql+=" and r.schoolAcademy.academyNumber='"+ shareService.getUserDetail().getSchoolAcademy().getAcademyNumber() +"'";
		}
		sql = sql + " and r.isUsed = 1 order by r.labRoomNumber";
		List<LabRoom> labRooms = labRoomDAO.executeQuery(sql.toString(), (page-1)*pageSize, pageSize);
		return labRooms;
	}

	/**
	 * Description 可视化--获取楼层可视化的起始楼层
	 * @param buildNumber
	 * @return
	 * @author 陈乐为 2019年4月13日
	 */
	public SystemFloorPic getStartFloor(String buildNumber) {
		String sql = "select s from SystemFloorPic s where s.systemBuild = '"+ buildNumber +"' order by s.floorNo";
		List<SystemFloorPic> floorPics = systemFloorPicDAO.executeQuery(sql);
		if(floorPics != null && floorPics.size() > 0) {
			return floorPics.get(0);
		}else {
			return null;
		}
	}
}
