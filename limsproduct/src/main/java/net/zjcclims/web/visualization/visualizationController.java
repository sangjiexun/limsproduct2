/****************************************************************************
 * 命名规范：
 * 本控制层调整必须是按照如下的路径调整：@RequestMapping("/visualization/sample")，全部小写。
 * 列表信息listxxxx，獲取信息：getxxxx 编辑信息：editxxxx 删除信息：detelexxxx 新增信息 newxxxx
 * 导出信息exportxxxx 保存信息：savexxxx  
 ****************************************************************************/

package net.zjcclims.web.visualization;

import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.CommonDocumentService;
import net.zjcclims.service.common.CommonVideoService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.device.LabRoomDeviceService;
import net.zjcclims.service.device.SchoolDeviceService;
import net.zjcclims.service.lab.LabRoomFurnitureService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.system.SystemBuildService;
import net.zjcclims.service.system.SystemService;
import net.zjcclims.service.tcoursesite.WkFolderService;
import net.zjcclims.service.visualization.VisualizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.BindException;
import java.util.*;



/****************************************************************************
 * 功能：可视化页面的显示
 * 可视化模块 作者：何立友 时间：2014-08-21
 ****************************************************************************/
@Controller("VisualizationController")
@SessionAttributes({"selected_academy", "isAudit"})
public class visualizationController<JsonResult> {
	@Autowired
	private SchoolDeviceService schoolDeviceService;
	@Autowired
	private ShareService shareService;
	@Autowired
	private LabRoomService labRoomService;
	@Autowired
	private LabRoomDAO labRoomDAO;
	@Autowired
	private SystemBuildService systemBuildService;
	@Autowired
	private LabRoomFurnitureService labRoomFurnitureService;
	@Autowired
	private LabRoomDeviceService labRoomDeviceService;
	@Autowired
	private LabRoomDeviceDAO labRoomDeviceDAO;
	@Autowired
	private VisualizationService visualizationService;
	@Autowired
	private SystemService systemService;
	@Autowired CommonDocumentService commonDocumentService;
	@Autowired CommonVideoService commonVideoService;
	@Autowired
	private WkFolderService wkFolderService;
	@Autowired
	private WkFolderDAO wkFolderDAO;
	@Autowired
	private	LabRoomAgentDAO labRoomAgentDAO;
	@Autowired
	private SystemBuildDAO systemBuildDAO;
	@Autowired
	private SystemFloorPicDAO systemFloorPicDAO;
	@Autowired
	private SystemCampusDAO systemCampusDAO;
	@Autowired
	private SchoolAcademyDAO schoolAcademyDAO;


	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register static property editors.
		binder.registerCustomEditor(Calendar.class, new org.skyway.spring.util.databinding.CustomCalendarEditor());
		binder.registerCustomEditor(byte[].class, new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(false));
		binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
		binder.registerCustomEditor(java.math.BigDecimal.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
		binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Integer.class, true));
		binder.registerCustomEditor(Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
		binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
		binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Long.class, true));
		binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Double.class, true));
	}
	
	
	/****************************************************************************
	 * 中医药实验室楼层展示
	 * 裴继超
	 * 2016年1月23日
	 ****************************************************************************/
	@RequestMapping("/visualization/roomList")
	public ModelAndView floor(@RequestParam int page,HttpServletRequest request,@ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView();
		
		String buildNumber = "";
		String floor = "";
		if(request.getParameter("buildNumber")!=null&&request.getParameter("buildNumber")!=""){
			buildNumber = request.getParameter("buildNumber").toString();
		}
		if(request.getParameter("floor")!=null&&request.getParameter("floor")!=""){
			floor = request.getParameter("floor").toString();
		}
		SystemBuild systemBuild = new SystemBuild();
		int pageSize = 10;
		//校级权限可以看到所有：超级管理员、教务处、设备科
		if(request.getSession().getAttribute("selected_role").equals("ROLE_SUPERADMIN") ||
				request.getSession().getAttribute("selected_role").equals("ROLE_DEAN") ||
				request.getSession().getAttribute("selected_role").equals("ROLE_ASSETMANAGER")){
			acno=null;
		}else {
			systemBuild.setSchoolAcademy(schoolAcademyDAO.findSchoolAcademyByAcademyNumber(acno));
		}
		int totalRecords = visualizationService.getLabRoomsByBuildAndFloorAndLabCenter(buildNumber,floor,1,-1,acno).size();
		//分页信息
		Map<String,Integer> pageModel =shareService.getPage(page, pageSize,totalRecords);
		
		List<LabRoom> labRooms = visualizationService.getLabRoomsByBuildAndFloorAndLabCenter(buildNumber,floor,page,pageSize,acno);
		mav.addObject("labRooms", labRooms); 
		mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", page);
		mav.addObject("pageSize", pageSize);
		LabRoom labRoom = new LabRoom();
		mav.addObject("labRoom", labRoom);

		List<SystemBuild> builds = systemBuildService.findSystemBuildByQuery(systemBuild, request, 1, -1);
		mav.addObject("builds", builds);
		mav.addObject("buildNumber", buildNumber); 
		if(buildNumber!=null){
			SystemBuild building = systemBuildService.finSystemBuildById(buildNumber);
			if(building!=null){
				String buildName = building.getBuildName();
				mav.addObject("buildName", buildName); 
			}
		}
		List<SystemFloorPic> systemFloorPics = visualizationService.findSystemFloorPic(buildNumber, null);
		mav.addObject("floor", floor);
		mav.addObject("floors", systemFloorPics);
		mav.setViewName("visualization/roomList.jsp");
		
		return mav;
	}
	/********************************
	 * @功能：联动查询（楼宇编号和楼层）
	 * @作者：廖文辉
	 * @时间：2018.8.29
	 ********************************/
	@ResponseBody
	@RequestMapping("/visualization/findFloorNumByBuildNumber")
	public Map<String, String> findFloorNumByBuildNumber(@RequestParam String buildNumber,HttpServletRequest request){
		Map<String, String> map = new HashMap<String, String>();
		String floorValue = visualizationService.LinkBuildNumberAndFloorNum(buildNumber,request);
		map.put("floorNum", floorValue);
		return map;
	}
	/************************************************************
	 * @可视化-切换楼层
	 * @作者：裴继超
	 * @日期：2016年1月22日
	 ************************************************************/
	@RequestMapping("/visualization/changeFloor")
	public @ResponseBody
	String changeFloor(@RequestParam String buildNumber,String floor) {
		List<LabRoom> labRooms = visualizationService.getLabRoomsByBuildAndFloor(buildNumber,floor,0,-1);

		String str = "";
		for(LabRoom l:labRooms){
			str = str + " <li style='transform: rotate(50deg); transform-origin: 50% 50% 0px;'>" +
					"<a href='javascript:void(0)' onclick='changeRoom("+l.getId()+")'>" +
							"<span>"+l.getLabRoomNumber()+"</span>&nbsp;&nbsp;"+l.getLabRoomName()+"</a>" +
									"<p style='display:none;'>"+l.getId()+"</p></li>";
		}
		return shareService.htmlEncode(str);
	}
	
	/************************************************************
	 * @可视化-切换楼层
	 * @作者：裴继超
	 * @日期：2016年1月22日
	 ************************************************************/
	@RequestMapping("/visualization/changeFloorFirstRoom")
	public @ResponseBody
	String changeFloorFirstRoom(@RequestParam String buildNumber,String floor,HttpServletRequest request) {
		List<LabRoom> labRooms = visualizationService.getLabRoomsByBuildAndFloor(buildNumber,floor,0,-1);
		LabRoom labRoom = new LabRoom();
		String str = "";
		if(labRooms.size()!=0){
			labRoom = labRooms.get(0);
			str = str + "<h2>" + labRoom.getLabRoomNumber()+"&nbsp;&nbsp;"+labRoom.getLabRoomName()+"</h2>";
			str = str + "<div class='banner_info'><p>容量：" + labRoom.getLabRoomCapacity() + "人</p>";
			str = str + "<p>面积：" + labRoom.getLabRoomArea() + "平方米</p><p>状态：";
			/*if(labRoom.getCActiveByLabRoomActive().getId()==1){
				str = str + "可用、";
			}else{
				str = str + "不可用、";
			}
			if(labRoom.getCActiveByLabRoomReservation().getId()==1){
				str = str + "可预约";
			}else{
				str = str + "不可预约";
			}*/
			if(labRoom.getLabRoomActive()!= null && labRoom.getLabRoomActive() == 1){
				str += "可用";
			}
			else{
				str += "不可用";
			}
			if(labRoom.getLabRoomReservation() != null && labRoom.getLabRoomReservation() == 1){
				str += "、可预约";
			}
			else{
				str += "、不可预约";
			}
			str = str + "</p> <a class='room_info_btn' href='javascript:void(0)' onclick='roomDetails()'> 教室详情</a></div>";
			str = str + "</p> <a class='room_visualization' target='_blank' href='"+request.getContextPath()+"/visualization/roomImage?page=1&id="+labRoom.getId()+"'> 房间可视化</a></div>";
		}

		
		return shareService.htmlEncode(str);
	}
	
	/************************************************************
	 * @可视化-切换实验室
	 * @作者：裴继超
	 * @日期：2016年1月23日
	 ************************************************************/
	@RequestMapping("/visualization/changeRoom")
	public @ResponseBody
	String changeRoom(@RequestParam int id,HttpServletRequest request) {
		LabRoom labRoom = visualizationService.findLabRoomByPrimaryKey(id);

		String str = "";
		str = str + "<h2>" + labRoom.getLabRoomNumber()+"&nbsp;&nbsp;"+labRoom.getLabRoomName()+"</h2>";
		str = str + "<div class='banner_info'><p>容量：" + labRoom.getLabRoomCapacity() + "人</p>";
		str = str + "<p>面积：" + labRoom.getLabRoomArea() + "平方米</p><p>状态：";
		/*if(labRoom.getCActiveByLabRoomActive().getId()==1){
			str = str + "可用、";
		}else{
			str = str + "不可用、";
		}
		if(labRoom.getCActiveByLabRoomReservation().getId()==1){
			str = str + "可预约";
		}else{
			str = str + "不可预约";
		}*/
		str = str + "可用、可预约";
		str = str + "</p> <a class='room_info_btn' href='javascript:void(0)' onclick='roomDetails()'> 教室详情</a></div>";
		str = str + "</p> <a class='room_visualization' target='_blank' href='"+request.getContextPath()+"/visualization/roomImage?page=1&id="+labRoom.getId()+"'> 房间可视化</a></div>";
		return shareService.htmlEncode(str);
	}
	
	/************************************************************
	 * @可视化-切换实验室全景图
	 * @作者：裴继超
	 * @日期：2016年3月14日
	 ************************************************************/
	@RequestMapping("/visualization/changeRoomPanoramagram")
	public @ResponseBody
	String changeRoomPanoramagram(@RequestParam int id,HttpServletRequest request) {
		LabRoom labRoom = visualizationService.findLabRoomByPrimaryKey(id);

		CommonDocument commonDocument = commonDocumentService.findCommonDocumentByLabRoom(labRoom,3);
		String str = ""+commonDocument.getDocumentUrl()+"";
		return shareService.htmlEncode(str);
	}
	
	/****************************************************************************
	 * 中医药实验室房间展示
	 * 裴继超
	 * 2016年1月23日
	 ****************************************************************************/
	@RequestMapping("/visualization/roomImage")
	public ModelAndView roomImage(@RequestParam int id){
		ModelAndView mav = new ModelAndView();
		LabRoom labRoom = visualizationService.findLabRoomByPrimaryKey(id);
		mav.addObject("labRoom", labRoom); 
		
		LabRoomDevice labRoomDevice = new LabRoomDevice();
		mav.addObject("labRoomDevice", labRoomDevice); 
		//此实验室已经标记位置的设备
		List<LabRoomDevice> labRoomDevices = visualizationService.findLabRoomDevicesByLabRoomIdAndXY(id);
		mav.addObject("labRoomDevices", labRoomDevices);
		
		//所有视频
		List<WkFolder> wkFolders = wkFolderService.findFoldersByType(1);
		mav.addObject("wkFolders", wkFolders);
		// 视频
				Set<LabRoomAgent> labRoomAgentSet = labRoom.getLabRoomAgents();

				// 多个视频就默认第一个视频
				int agentId = 0;
				for (LabRoomAgent labRoomAgent : labRoomAgentSet) {
					// 类型549才是视频还要判断是否可用
					if (shareService.checkCDictionary(labRoomAgent.getCDictionary().getId(),"3","c_agent_type")
							&& labRoomAgent.getCDictionary().getEnabled()) {
						agentId = labRoomAgent.getId();
						break;
					}
				}
				LabRoomAgent agent = labRoomAgentDAO
						.findLabRoomAgentByPrimaryKey(agentId);
				// 如果为空就是没有视频
				if (agent != null) {

					// 流媒体服务器地址
					String serverIp = agent.getCommonServer().getServerIp();
					mav.addObject("serverIp", serverIp);
					// 端口
					String hardwarePort = "1935";//agent.getHardwarePort();
					mav.addObject("hardwarePort", hardwarePort);
					// 摄像头本身ip的 xxx.xxx.xxx.123 最后那个123
					String lastFour = "";
					// 192.168.0.sz
					String hardWareIp = agent.getHardwareIp();
					// split .有问题，所以替换成了 , 逗号
					hardWareIp = hardWareIp.replace(".", ",");
					if (!EmptyUtil.isStringEmpty(hardWareIp)
							&& !EmptyUtil.isObjectEmpty(hardWareIp.split(","))
							&& hardWareIp.split(",").length > 3) {
						//lastFour = hardWareIp.split(",")[2] + hardWareIp.split(",")[3];
						lastFour = hardWareIp.split(",")[2] + hardWareIp.split(",")[3];
					}
					mav.addObject("lastFour", lastFour);

					// 测试输出
					System.out.println("rtmp://" + serverIp + ":" + hardwarePort
							+ "/live/" + lastFour);
				}
		mav.setViewName("visualization/roomImage.jsp");
		
		return mav;
	}
	
	/****************************************************************************
	 * 保存实验室设备
	 * 裴继超
	 * 2016年1月27日
	 ****************************************************************************/
	@RequestMapping("/visualization/saveDevice")
	public ModelAndView saveDevice(@ModelAttribute LabRoomDevice labRoomDevice){
		ModelAndView mav = new ModelAndView();
		//根据id获取实验室设备
		LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(labRoomDevice.getId());
		//保存坐标
		device.setxCoordinate(labRoomDevice.getxCoordinate());
		device.setyCoordinate(labRoomDevice.getyCoordinate());
		device.setWkFolder(labRoomDevice.getWkFolder());
		if(labRoomDevice.getWkFolder() != null && labRoomDevice.getWkFolder().getId() == null){
				device.setWkFolder(null);
				device.setqRCodeUrl(null);
		}
		else{
			device.setWkFolder(labRoomDevice.getWkFolder());
		}
		//保存视频二维码路径
		if(labRoomDevice.getWkFolder() != null && labRoomDevice.getWkFolder().getId() != null && !labRoomDevice.getWkFolder().getId().equals(""))
		{
			WkFolder wkFolder = wkFolderDAO.findWkFolderById(labRoomDevice.getWkFolder().getId());
			if(wkFolder != null)
			{
				device.setqRCodeUrl(wkFolder.getqRCodeUrl());
			}
		}
		//保存实验室设备
		visualizationService.saveLabRoomDevice(device);
		int id = labRoomDevice.getLabRoom().getId();
		mav.setViewName("redirect:/visualization/roomImage?id="+id);
		return mav;
	}
	
	/************************************************************
	 * @功能-获取设备信息ajax
	 * @作者：裴继超
	 * @日期：2016年1月27日
	 ************************************************************/
	@RequestMapping("/visualization/editDevice")
	public @ResponseBody
	Map<String, String> getSchoolClassesUser(@RequestParam int id) {
		LabRoomDevice labRoomDevice = visualizationService.findLabRoomDeviceByPrimaryKey(id);
		//设备信息map
		Map<String, String> map = new HashMap<String, String>();
		map.put("labRoomDeviceId", labRoomDevice.getId().toString());
		map.put("xCoordinate", labRoomDevice.getxCoordinate().toString());
		map.put("yCoordinate", labRoomDevice.getyCoordinate().toString());
		//map.put("deviceNumber", labRoomDevice.getSchoolDevice().getDeviceNumber());
		//map.put("deviceName", labRoomDevice.getSchoolDevice().getDeviceName());
		return map;
	}
	
	/****************************************************************************
	 * 删除实验室设备
	 * 裴继超
	 * 2016年1月28日
	 ****************************************************************************/
	@RequestMapping("/visualization/deletDevice")
	public @ResponseBody
	boolean deletDevice(@RequestParam int labRoomDeviceId){
		LabRoomDevice labRoomDevice = visualizationService.findLabRoomDeviceByPrimaryKey(labRoomDeviceId);
		int roomId = labRoomDevice.getLabRoom().getId();
		visualizationService.deletLabRoomDeviceXY(labRoomDeviceId);
		boolean b = true;
		return b;
	}
	
	/****************************************************************************
	 * 设置楼宇位置
	 * 裴继超
	 * 2016年3月22日
	 ****************************************************************************/
	@RequestMapping("/visualization/setBuildPlace")
	public ModelAndView setBuildPlace(@RequestParam String campusNumber, HttpServletRequest request, @ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView();

		//根据学院获取校区
		List<SystemCampus> systemCampuses = new ArrayList<>();

		SystemBuild systemBuilds = new SystemBuild();
		//校级权限可以看到所有：超级管理员、教务处、设备科
		if(request.getSession().getAttribute("selected_role").equals("ROLE_SUPERADMIN") ||
				request.getSession().getAttribute("selected_role").equals("ROLE_DEAN") ||
				request.getSession().getAttribute("selected_role").equals("ROLE_ASSETMANAGER")){
			systemCampuses = systemService.getSystemCampusBySchoolAcademy(null);
		}else {
			systemBuilds.setSchoolAcademy(schoolAcademyDAO.findSchoolAcademyByAcademyNumber(acno));
			systemCampuses = systemService.getSystemCampusBySchoolAcademy(acno);
		}
		if(campusNumber == null || campusNumber.equals("")) {
			systemBuilds.setSystemCampus(systemCampuses.get(0));
			campusNumber = systemCampuses.get(0).getCampusNumber();
		}else {
			systemBuilds.setSystemCampus(systemCampusDAO.findSystemCampusByPrimaryKey(campusNumber));
		}

		List<SystemBuild> buils = systemBuildService.findSystemBuildByQuery(systemBuilds, request, 1, -1);
		mav.addObject("buils", buils);

		mav.addObject("systemCampus", systemCampusDAO.findSystemCampusByPrimaryKey(campusNumber));
		//获取所有校区
		mav.addObject("listSystemCampus", systemCampuses);
		SystemBuild systemBuild = new SystemBuild();
		mav.addObject("systemBuild", systemBuild); 
		mav.setViewName("visualization/setBuildPlace.jsp");
		
		return mav;
	}
	
	/****************************************************************************
	 * 保存楼宇位置
	 * 裴继超
	 * 2016年3月22日
	 ****************************************************************************/
	@RequestMapping("/visualization/saveBuild")
	public ModelAndView saveBuild(@ModelAttribute SystemBuild systemBuild){
		ModelAndView mav = new ModelAndView();
		SystemBuild build = systemBuildService.finSystemBuildById(systemBuild.getBuildNumber());
		build.setxCoordinate(systemBuild.getxCoordinate());
		build.setyCoordinate(systemBuild.getyCoordinate());
		systemBuildService.saveSystemBuild(build);
		mav.setViewName("redirect:/visualization/setBuildPlace?campusNumber="+build.getSystemCampus().getCampusNumber());
		return mav;
	}

	/************************************************************
	 * @功能-获取楼栋ajax
	 * @作者：裴继超
	 * @日期：2016年1月27日
	 ************************************************************/
	@RequestMapping("/visualization/editBuild")
	public @ResponseBody
	Map<String, String> editBuild(@RequestParam String buildNumber) {
		SystemBuild systemBuild = systemBuildService.finSystemBuildById(buildNumber);
		//楼宇信息map
		Map<String, String> map = new HashMap<String, String>();
		map.put("buildNumber", systemBuild.getBuildNumber());
		map.put("xCoordinate", systemBuild.getxCoordinate().toString());
		map.put("yCoordinate", systemBuild.getyCoordinate().toString());
		return map;
	}
	
	/****************************************************************************
	 * 功能：实验室上传图片
	 * 作者：裴继超
	 * 时间：2016年5月27日10:53:11
	 ****************************************************************************/
	@RequestMapping("/visualization/addLabRoomImage")
	public ModelAndView updateLabRoom(@RequestParam Integer id) {
		ModelAndView mav=new ModelAndView();
		//id对应的实验分室
		LabRoom labRoom=labRoomService.findLabRoomByPrimaryKey(id);
		mav.addObject("labRoom", labRoom);
		List<SystemCampus> campusList= systemService.getAllSystemCampus(1,-1);
		mav.addObject("campusList", campusList);
		mav.setViewName("visualization/addLabRoomImage.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * 删除楼宇位置信息
	 * 裴继超
	 * 2016年1月28日
	 ****************************************************************************/
	@RequestMapping("/visualization/deletBuild")
	public @ResponseBody
	boolean deletBuild(@RequestParam String buildNumber){
		SystemBuild build = systemBuildService.finSystemBuildById(buildNumber);
		build.setxCoordinate(null);
		build.setyCoordinate(null);
		systemBuildService.saveSystemBuild(build);
		boolean b = true;
		return b;
	}
	
	/****************************************************************************
	 * 功能：保存实验分室
	 * 作者：李小龙
	 * 时间：2014-07-29
	 ****************************************************************************/
	@RequestMapping("/visualization/saveLabRoom")
	public ModelAndView saveLabRoom(@ModelAttribute LabRoom labRoom){
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		int labRoomId = labRoom.getId();
		//获取之前的实验室
		LabRoom oldLabRoom = labRoomService.findLabRoomByPrimaryKey(labRoomId);
		//实验室地点
		String number = "";
		if(labRoom.getSystemRoom()!=null) {
			number = labRoom.getSystemRoom().getRoomNumber();
		}

		//房间号对应的房间
		//SystemRoom sysRoom=visualizationService.findSystemRoomByPrimaryKey(number);
		// 格式为 楼宇 - 实训室
		String[] nameSize = number.split(" - ");
		//判断是否选择
		if(nameSize.length>1){
			String roomName = nameSize[1];
			SystemRoom sysRoom=visualizationService.findSystemRoomByPrimaryKey(roomName);
			oldLabRoom.setSystemRoom(sysRoom);	
		}
		
		//设置实验室地点字段
		oldLabRoom.setLabRoomAddress(number);
		//将实验室保存到数据库
		LabRoom room=visualizationService.save(oldLabRoom);
		mav.addObject("room", room);
		
		
		mav.setViewName("redirect:/visualization/roomList?page=1");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：给实验室上传图片
	 * 作者：裴继超
	 * 时间：2016年5月27日
	 ****************************************************************************/
	@RequestMapping("/visualization/uploadImageForLabRoom")
	public @ResponseBody String uploadImageForLabRoom(HttpServletRequest request, HttpServletResponse response, BindException errors,Integer id,Integer type) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		Map files = multipartRequest.getFileMap();
		Iterator fileNames = multipartRequest.getFileNames();
		boolean isup = true;
		//存放文件文件夹名称
		for(; fileNames.hasNext();) {
			String filename = (String) fileNames.next();
			CommonsMultipartFile file = (CommonsMultipartFile) files.get(filename);
			//判断图片大小是否在3M之内
			if (file.getSize() > 3*1048576) {
				isup = false;
				break;
			}
		}
		if(isup == true) {
			visualizationService.uploadImageForLabRoom(request, response, id, type);
			return "ok";
		}else {
			return "more";
		}
	}
	
	/****************************************************************************
	 * 功能：给实验室添加设备
	 * 作者：裴继超
	 * 时间：2016年5月27日
	 ****************************************************************************/
	@RequestMapping("visualization/addLabRoomDevice")
	public ModelAndView addLabRoomDevice(@RequestParam Integer id,@ModelAttribute SchoolDevice schoolDevice){
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		User user=shareService.getUser();
		mav.addObject("user", user);
		boolean flag=labRoomService.getLabRoomAdminReturn(id,user);
		mav.addObject("flag", flag);
		//根据实验分室id查询实验室设备
		List<LabRoomDevice> labDeviceList= labRoomDeviceService.findLabRoomDeviceByRoomId(id);
		mav.addObject("labDeviceList", labDeviceList);
		//设备查询表单对象
		mav.addObject("schoolDevice", schoolDevice);
		//id对应的实验分室信息
		LabRoom labRoom=labRoomDAO.findLabRoomByPrimaryKey(id);
		mav.addObject("labRoom", labRoom);
		//设备信息设置表单对象
		mav.addObject("labRoomDevice", new LabRoomDevice());
		mav.setViewName("visualization/addLabRoomDevice.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：保存实验室设备
	 * 作者：贺子龙
	 * 时间：2015-09-08 
	 ****************************************************************************/
	@RequestMapping("visualization/saveLabRoomDevice")
	public ModelAndView saveLabRoomDevice(@RequestParam Integer roomId,String[] array) throws Exception {
		ModelAndView mav=new ModelAndView();
		//roomId对应的实验分室
		LabRoom room=labRoomService.findLabRoomByPrimaryKey(roomId);
		for (String i : array) {
	  		//设备编号对应的设备
			SchoolDevice d=schoolDeviceService.findSchoolDeviceByPrimaryKey(i);
			LabRoomDevice device=new LabRoomDevice();
			device.setLabRoom(room);
			device.setSchoolDevice(d);
			/*//默认为现场培训
			CLabAccessType accessType=cLabAccessTypeDAO.findCLabAccessTypeById(1);
			device.setCLabAccessType(accessType);
			//默认为日历形式
			CAppointmentType type=cAppointmentTypeDAO.findCAppointmentTypeById(2);
			device.setCAppointmentType(type);*/
			device=labRoomDeviceService.save(device);
			//设备二维码
			String url=shareService.getDimensionalCode(device);
			device.setDimensionalCode(url);
			labRoomDeviceService.save(device);
	  	}
		mav.setViewName("redirect:/visualization/addLabRoomDevice?id="+roomId);
		return mav;
	}

	/****************************************************************************
	 * description:删除图片
	 * 
	 * author:于侃
	 * date:2016年09月21日
	 ****************************************************************************/
	@RequestMapping("/visualization/deleteImageForLabRoom")
	public ModelAndView deleteImageForLabRoom(Integer id,Integer type,Integer photoId,HttpServletRequest request){
		if(photoId != null && type == 2){
			visualizationService.deleteImageForLabRoom(id,type,photoId,request);
		}else{
			visualizationService.deleteImageForLabRoom(id,type,request);
		}
		ModelAndView mav=new ModelAndView();
		//id对应的实验分室
		LabRoom labRoom=labRoomService.findLabRoomByPrimaryKey(id);
		mav.addObject("labRoom", labRoom);
		List<SystemCampus> campusList= systemService.getAllSystemCampus(1,-1);
		mav.addObject("campusList", campusList);
		mav.setViewName("visualization/addLabRoomImage.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * description:下载图片
	 * 
	 * author:于侃
	 * date:2016年09月21日
	 ****************************************************************************/
	@RequestMapping("/visualization/downloadImageForLabRoom")
	public String downloadImageForLabRoom(Integer id,HttpServletRequest request,HttpServletResponse response){
		visualizationService.downloadImageForLabRoom(id,request,response);
		return null;
	}

	/**
	 * @Description 显示所有楼宇
	 * @author 张德冰
	 * @data 2018-10-29
	 */
	@RequestMapping("/visualization/systemBuildList")
	public ModelAndView systemBuildList(@RequestParam Integer page, HttpServletRequest request, @ModelAttribute("selected_academy") String acno,
											  @ModelAttribute SystemBuild systemBuild) {
		ModelAndView mav = new ModelAndView();
		// 每页10条记录
		int pageSize = 10;
		SchoolAcademy schoolAcademy = shareService.getUserDetail().getSchoolAcademy();
		List<SchoolAcademy> listSchoolAcademy = new ArrayList<>();
		//校级权限可以看到所有：超级管理员、教务处、设备科
		if(request.getSession().getAttribute("selected_role").equals("ROLE_SUPERADMIN") ||
				request.getSession().getAttribute("selected_role").equals("ROLE_DEAN") ||
				request.getSession().getAttribute("selected_role").equals("ROLE_ASSETMANAGER")){
			//获取所有学院
			listSchoolAcademy=systemService.getAllSchoolAcademy(1, -1);
		}else {
			systemBuild.setSchoolAcademy(schoolAcademy);
			listSchoolAcademy.add(schoolAcademy);
		}
		// 查询出来的总记录条数
		int totalRecords = systemBuildService.findSystemBuildByQuery(systemBuild, request, 1, -1).size();
		// 分页信息
		Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
		// 根据分页信息查询出来的记录
		List<SystemBuild> builds = systemBuildService.findSystemBuildByQuery(systemBuild, request, page, pageSize);
		mav.addObject("builds", builds);

		mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", page);
		mav.addObject("pageSize", pageSize);
		mav.addObject("systemBuild", systemBuild);

		mav.addObject("listSchoolAcademy", listSchoolAcademy);
		//获取所有校区
		mav.addObject("listSystemCampus", systemService.getAllSystemCampus(1, -1));

		mav.setViewName("/visualization/systemBuildList.jsp");
		return mav;
	}

	/**
	 * @Description 获取当前楼宇的楼层数和楼层图
	 * @author 张德冰
	 * @data 2018-10-29
	 */
	@RequestMapping("/visualization/getSystemFloorPic")
	@ResponseBody
	public ModelAndView getSystemFloorPic(HttpServletRequest request, @RequestParam String buildNumber) {
		ModelAndView mav = new ModelAndView();
		// 获取楼层
		List<SystemFloorPic> floorPics = visualizationService.findSystemFloorPic(buildNumber, null);
		mav.addObject("floorPics", floorPics);
		mav.addObject("buildNumber",buildNumber);

		mav.setViewName("/visualization/addSystemFloorPic.jsp");
		return mav;
	}

	/**
	 * @Description 上传楼层图
	 * @author 张德冰
	 * @data 2018-10-30
	 */
	@RequestMapping("/visualization/uploadSystemFloorPic")
	@ResponseBody
	public String uploadSystemFloorPic(HttpServletRequest request, HttpServletResponse response, String buildNumber, Integer floor) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		String sep = System.getProperty("file.separator");
		Map files = multipartRequest.getFileMap();
		Iterator fileNames = multipartRequest.getFileNames();
		boolean flag =false;
		//文件存放文件夹
		String fileDir = request.getSession().getServletContext().getRealPath( "/") +  "upload"+ sep+"systemFloor"+sep+buildNumber;
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
				//文件名
				String fileTrueName = file.getOriginalFilename();
				//文件重命名
				int endAddress = fileTrueName.lastIndexOf(".");
				String ss = fileTrueName.substring(endAddress, fileTrueName.length());//后缀名
				//文件名称
				String fileNewName = "systemFloorPic"+buildNumber+"-"+floor+ss;
				File uploadedFile = new File(fileDir + sep + fileNewName);
				try {
					FileCopyUtils.copy(bytes,uploadedFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//保存图片
				SystemFloorPic floorPic = new SystemFloorPic();
				//根据楼宇和楼层获取图片
				List<SystemFloorPic> systemFloorPics = visualizationService.findSystemFloorPic(buildNumber, floor);
				if(systemFloorPics != null && systemFloorPics.size() != 0){
					floorPic = systemFloorPics.get(0);
				}

				floorPic.setDocumentName(fileTrueName);
				String imageUrl="upload/systemFloor/"+buildNumber+"/"+fileNewName;
				floorPic.setDocumentUrl(imageUrl);
				floorPic.setSystemBuild(buildNumber);
				floorPic.setFloorNo(floor);
				systemFloorPicDAO.store(floorPic);

			}
		}
		return "ok";
	}

	/**
	 * @Description 删除楼层图
	 * @author 张德冰
	 * @data 2018-10-30
	 */
	@RequestMapping("/visualization/deleteSystemFloorPic")
	public ModelAndView deleteSystemFloorPic(HttpServletRequest request, @RequestParam Integer picId) {
		ModelAndView mav = new ModelAndView();

		String rootPath = request.getSession().getServletContext().getRealPath("/");
		//根据id获取楼层图片
		SystemFloorPic floorPic = systemFloorPicDAO.findSystemFloorPicById(picId);
		String path = rootPath+floorPic.getDocumentUrl();
		File file = new File(path);
		file.delete();
		floorPic.setDocumentUrl(null);

		systemFloorPicDAO.store(floorPic);
		systemFloorPicDAO.flush();
		mav.setViewName("redirect:/visualization/getSystemFloorPic?buildNumber="+floorPic.getSystemBuild());
		return mav;
	}

	/**
	 * @Description 根据学院获取校区
	 * @author 张德冰
	 * @data 2018-11-23
	 */
	@RequestMapping("/visualization/systemCampusList")
	public ModelAndView systemCampusList(HttpServletRequest request, @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();

		//校级权限可以看到所有：超级管理员、教务处、设备科
		if(request.getSession().getAttribute("selected_role").equals("ROLE_SUPERADMIN") ||
				request.getSession().getAttribute("selected_role").equals("ROLE_DEAN") ||
				request.getSession().getAttribute("selected_role").equals("ROLE_ASSETMANAGER")){
			acno = null;
		}
		//根据学院获取校区
		List<SystemCampus> systemCampuses = systemService.getSystemCampusBySchoolAcademy(acno);
		mav.addObject("systemCampuses",systemCampuses);

		mav.setViewName("/visualization/systemCampusPic.jsp");
		return mav;
	}

	/**
	 * @Description 上传校区图
	 * @author 张德冰
	 * @data 2018-11-23
	 */
	@RequestMapping("/visualization/uploadSystemCampusPic")
	@ResponseBody
	public String uploadSystemCampusPic(HttpServletRequest request, String campusNumber) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		String sep = System.getProperty("file.separator");
		Map files = multipartRequest.getFileMap();
		Iterator fileNames = multipartRequest.getFileNames();
		boolean flag =false;
		//文件存放文件夹
		String fileDir = request.getSession().getServletContext().getRealPath( "/") +  "upload"+ sep+"systemCampus";
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
				//文件名
				String fileTrueName = file.getOriginalFilename();
				//文件重命名
				int endAddress = fileTrueName.lastIndexOf(".");
				String ss = fileTrueName.substring(endAddress, fileTrueName.length());//后缀名
				//文件名称
				String fileNewName = "systemCampusPic"+campusNumber+ss;
				File uploadedFile = new File(fileDir + sep + fileNewName);
				try {
					FileCopyUtils.copy(bytes,uploadedFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//根据校区id获取图片
				SystemCampus systemCampus = systemCampusDAO.findSystemCampusByPrimaryKey(campusNumber);
				String imageUrl="upload/systemCampus/"+fileNewName;
				systemCampus.setPicUrl(imageUrl);
				systemCampusDAO.store(systemCampus);
			}
		}
		return "ok";
	}

	/**
	 * @Description 删除校区图
	 * @author 张德冰
	 * @data 2018-10-30
	 */
	@RequestMapping("/visualization/deleteSystemCampusPic")
	public ModelAndView deleteSystemCampusPic(HttpServletRequest request, @RequestParam String campusNumber) {
		ModelAndView mav = new ModelAndView();

		String rootPath = request.getSession().getServletContext().getRealPath("/");
		//根据id获取楼层图片
		SystemCampus systemCampus = systemCampusDAO.findSystemCampusByPrimaryKey(campusNumber);
		String path = rootPath+systemCampus.getPicUrl();
		File file = new File(path);
		file.delete();
		systemCampus.setPicUrl(null);
		systemCampusDAO.store(systemCampus);

		mav.setViewName("redirect:/visualization/systemCampusList");
		return mav;
	}

	/**
	 * Description 根据楼宇编号获取system_room
	 * @param buildNumber
	 * @param request
	 * @return
	 * @author 陈乐为 2018-11-28
	 */
	@ResponseBody
	@RequestMapping("/visualization/getSystemRoomByBuildNumber")
	public Map<String, String> getSystemRoomByBuildNumber(@RequestParam String buildNumber,HttpServletRequest request){
		Map<String, String> map = new HashMap<String, String>();
		String roomValue = visualizationService.LinkSystemRoomAndBuildNumber(buildNumber);
		map.put("roomValue", roomValue);
		return map;
	}

	/*************************************************************************************
	 * Description:根据校区id动态获取数据
	 *
	 * @author: 杨新蔚
	 * @date: 2018/12/11
	 *************************************************************************************/
	@ResponseBody
	@RequestMapping("/getDynamicDataById")
	public List<Map<String,String>> getDynamicDataById(@RequestParam String id,String type){
		List<Map<String,String>> dynamicData= new ArrayList<>();
		//楼宇
		if("buildData".equals(type)){
			dynamicData=visualizationService.getBuildByCampus(id);
		}//房间
		else if("roomData".equals(type)){
			dynamicData=visualizationService.getRoomByBuild(id);
		}//设备
		else if("deviceData".equals(type)){
			dynamicData=visualizationService.getDeviceByRoom(id);
		}//未完可续
		else{

		}
		return dynamicData;
	}

	/*************************************************************************************
	 * Description:根据id动态获取数据
	 *
	 * @author: 杨新蔚
	 * @date: 2018/12/13
	 *************************************************************************************/
	@ResponseBody
	@RequestMapping(value="/getInstallationNameById", produces="application/json;charset=utf-8")
	public String getInstallationNameById(@RequestParam String id,String type){
		String dynamicData= "";
		//楼宇
		if("buildData".equals(type)){
			SystemBuild systemBuild =systemBuildDAO.findSystemBuildByPrimaryKey(id);
			if(!EmptyUtil.isObjectEmpty(systemBuild)){
				dynamicData=systemBuild.getBuildName();
			}
		}//房间
		else if("roomData".equals(type)){
			LabRoom labRoom=labRoomDAO.findLabRoomByPrimaryKey(Integer.parseInt(id));
			if(!EmptyUtil.isObjectEmpty(labRoom)){
				dynamicData=labRoom.getLabRoomName();
			}
		}//设备
		else if("deviceData".equals(type)){
			LabRoomDevice labRoomDevice=labRoomDeviceDAO.findLabRoomDeviceByPrimaryKey(Integer.parseInt(id));
			if(!EmptyUtil.isObjectEmpty(labRoomDevice)&&!EmptyUtil.isObjectEmpty(labRoomDevice.getSchoolDevice())){
				dynamicData=labRoomDevice.getSchoolDevice().getDeviceName();
			}
		}//未完可续
		else{

		}
		return dynamicData;
	}

}
