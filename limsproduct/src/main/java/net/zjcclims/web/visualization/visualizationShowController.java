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
import net.zjcclims.service.lab.LabRoomFurnitureService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.system.SystemBuildService;
import net.zjcclims.service.system.SystemService;
import net.zjcclims.service.visualization.VisualizationService;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;



/****************************************************************************
 * 功能：可视化页面的显示
 * 可视化模块 作者：何立友 时间：2014-08-21
 ****************************************************************************/
@Controller("VisualizationShowController")
@SessionAttributes("selected_academy")
public class visualizationShowController<JsonResult> {

	@Autowired
	private ShareService shareService;
	@Autowired
	private CDictionaryDAO cDictionaryDAO;
	@Autowired
	private LabRoomService labRoomService;
	@Autowired
	private LabRoomDAO labRoomDAO;
	@Autowired
	private LabRoomFurnitureService labRoomFurnitureService;
	@Autowired
	private LabRoomDeviceService labRoomDeviceService;
	@Autowired
	private VisualizationService visualizationService;
	
	@Autowired CommonDocumentService commonDocumentService;
	@Autowired CommonVideoService commonVideoService;
	@Autowired
	private SystemBuildService systemBuildService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private	LabRoomAgentDAO labRoomAgentDAO;
	@Autowired
	private	PConfig pConfig;
	@Autowired
	private SystemCampusDAO systemCampusDAO;
	@Autowired
	private AuthorityDAO authorityDAO;

	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register static property editors.
		binder.registerCustomEditor(java.util.Calendar.class, new org.skyway.spring.util.databinding.CustomCalendarEditor());
		binder.registerCustomEditor(byte[].class, new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(false));
		binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
		binder.registerCustomEditor(java.math.BigDecimal.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
		binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Integer.class, true));
		binder.registerCustomEditor(java.util.Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
		binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
		binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Long.class, true));
		binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Double.class, true));
	}
	
	/****************************************************************************
	 * 可视化首页展示
	 * 裴继超
	 * 2016年1月23日
	 ****************************************************************************/
	@RequestMapping("/visualization/show/index")
	public ModelAndView buildPlace(@RequestParam String campusNumber,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();

		//默认进入延安路校区
		if(campusNumber == null || campusNumber.equals("")){
			campusNumber="1";
		}
		List<SystemBuild> buils = systemBuildService.findBuildByCampusIdAndAcno(campusNumber,request);
		//List<SystemBuild> buils = systemBuildService.finAllSystemBuilds();
		mav.addObject("buils", buils);
		mav.addObject("systemCampus", systemCampusDAO.findSystemCampusByPrimaryKey(campusNumber));
		//获取所有校区
		mav.addObject("listSystemCampus", systemService.getAllSystemCampus(1, -1));
		if(request.getSession().getAttribute("selected_role" ) != null){
			//.获取当前权限
			String authority = authorityDAO
					.findAuthorityByAuthorityName(((String) request
							.getSession()
							.getAttribute("selected_role" ))
							.substring(5))
					.iterator()
					.next()
					.getCname();
			mav.addObject("authority", authority);
		}else{
			mav.addObject("authority", null);
		}
		//当前登录人
		User user = shareService.getUser();
		mav.addObject("user", user);
//		if(user!=null){
//		String authority = "";
//		int i = 1;
//		Set<Authority> auths = user.getAuthorities();
//		for (Authority a : auths) {
//			if (a.getType() >= i) {
//				authority = a.getCname();
//				i = a.getType();
//			}
//		}
//		mav.addObject("authority", authority);
//		}
		mav.addObject("PROJECT_NAME", pConfig.PROJECT_NAME);
		mav.addObject("showroom",pConfig.showroom);
		mav.setViewName("visualization/show/index.jsp");
		
		return mav;
	}

	/**
	 * Description 可视化{实验室可视化}
	 * @param labRoom
	 * @param buildNumber
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @author 陈乐为 2019年4月29日 修改
	 */
	@RequestMapping("/visualization/show/floor")
	public ModelAndView floor(@ModelAttribute LabRoom labRoom,@RequestParam String buildNumber,HttpServletRequest request) throws UnsupportedEncodingException{
		ModelAndView mav = new ModelAndView();
		if(buildNumber.equals(new String(buildNumber.getBytes("iso8859-1"), "iso8859-1"))){
			buildNumber = new String(buildNumber.getBytes("iso8859-1"), "utf-8");
		}
		// 当前楼宇
		SystemBuild systemBuild = systemBuildService.findBuildingbyBuildNumber(buildNumber);
		mav.addObject("systemBuild", systemBuild);
		// 楼宇可视化的起始楼层
		Integer floor_no = 1;
		String floor_name = "未设置楼层";
		SystemFloorPic pic = visualizationService.getStartFloor(buildNumber);
		if(pic != null) {
			floor_no = pic.getFloorNo();
			floor_name = pic.getFloorName();
		}
		mav.addObject("floor_name", floor_name);
		// 获取楼层所有实验室
		List<LabRoom> labRooms = visualizationService.getLabRoomsByBuildAndFloorAndAcno(buildNumber, floor_no,1,-1,request);
		mav.addObject("labRooms", labRooms);
		// 实验室设备
		mav.addObject("labRoomDevice", new LabRoomDevice());
		if(labRooms.size()!=0){
			labRoom = labRooms.get(0);
			mav.addObject("labRoom", labRoom);
			//获取已经标点实验设备
			List<LabRoomDevice> labRoomDevices = visualizationService.findLabRoomDevicesByLabRoomIdAndXY(labRoom.getId());
			mav.addObject("labRoomDevices", labRoomDevices);
			// 资产总额
			BigDecimal totalAssets = labRoomDeviceService.getAgentPriceByLab(labRoom.getId());
			mav.addObject("totalAssets", totalAssets);
		}
		mav.addObject("buildNumber", buildNumber);
		//获取当前楼宇1层的楼层图
		SystemFloorPic floorPic = new SystemFloorPic();
		List<SystemFloorPic> systemFloorPics = visualizationService.findSystemFloorPic(buildNumber, floor_no);
		if(systemFloorPics != null && systemFloorPics.size() != 0){
			floorPic = systemFloorPics.get(0);
		}
		mav.addObject("floorPic", floorPic);
		// 楼宇列表
		List<SystemBuild> systemBuilds = systemBuildService.findBuildingByXY();
		mav.addObject("systemBuilds", systemBuilds);
		// 楼层列表
		List<SystemFloorPic> floorPics = visualizationService.findSystemFloorPic(buildNumber, null);
		mav.addObject("floorPics", floorPics);
		// 校区列表
		mav.addObject("systemCampus", systemBuild.getSystemCampus());
		// 视频列表
		List<LabRoomAgent> agentList = new ArrayList<LabRoomAgent>();
		if(labRoom!=null && labRoom.getId()!=null){
			agentList = labRoomService.getAgentByType(labRoom.getId(), "3", "c_agent_type");
		}
		mav.addObject("agentList", agentList);

		mav.addObject("deviceLend", pConfig.deviceLend);
		mav.addObject("jobReservation", pConfig.jobReservation);
		mav.addObject("noREC", pConfig.noREC);
		mav.addObject("proj_name", pConfig.PROJECT_NAME);
		mav.setViewName("visualization/show/floor.jsp");
		
		// 视频
		// 默认读取第一个视频
		// 如果为空就是没有视频+当前权限不在禁用权限内
		if (agentList != null && agentList.size() > 0 && !pConfig.noREC.contains(request.getSession().getAttribute("selected_role").toString())) {
			LabRoomAgent agent = agentList.get(0);
			// 流媒体服务器地址
			mav.addObject("serverIp", agent.getCommonServer().getServerIp());
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
				lastFour = hardWareIp.split(",")[2] + hardWareIp.split(",")[3];
			}
			mav.addObject("lastFour", lastFour);
		}else {
			// 实验室全景图
			if(labRoom != null && labRoom.getCommonDocuments()!=null && labRoom.getCommonDocuments().size()>0) {
				for (CommonDocument document : labRoom.getCommonDocuments()) {
					if (document.getType() == 3) {
						mav.addObject("documentUrl", document.getDocumentUrl());
						break;
					} else {
						mav.addObject("documentUrl", "");
					}
				}
			}else {
				mav.addObject("documentUrl", "");
			}
		}
		
		return mav;
	}
	/****************************************************************************
	 * 中医药实验室楼层展示
	 * 裴继超
	 * 2016年1月23日
	 ****************************************************************************/
	@RequestMapping("/visualization/show/roomImage")
	public ModelAndView roomImage(@RequestParam int page,@RequestParam int id){
		ModelAndView mav = new ModelAndView();
		LabRoom labRoom = visualizationService.findLabRoomByPrimaryKey(id);
		labRoom.getLabRoomDevices().size();
		//分页信息
		int pageSize = 10;
		int totalRecords = visualizationService.getLabRoomDeviceBylabroomid(id,1,-1).size();
		Map<String,Integer> pageModel =shareService.getPage(page, pageSize,totalRecords);
		mav.addObject("labRoomDeviceInfo", visualizationService.getLabRoomDeviceBylabroomid(id,page,pageSize)); 
		LabRoomDevice labRoomDevice = new LabRoomDevice();
		mav.addObject("labRoomDevice",labRoomDevice);
		mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", page);
		mav.addObject("pageSize", pageSize);
		//此实验室已经标记位置的设备
		List<LabRoomDevice> labRoomDevices = visualizationService.findLabRoomDevicesByLabRoomIdAndXY(id);
		mav.addObject("labRoomDevices", labRoomDevices); 
		mav.addObject("labRoom", labRoom); 
		mav.setViewName("visualization/show/roomImage.jsp");
		mav.addObject("id",id);
		// 视频
				// 传入的是labroom 的id 先查询labroom，再拿里面的set集合的labroomAgents。默认读取第一个视频
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
				// 通过id获取对象
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
		return mav;
	}
	/*********************************************************************************
	 * 功能:设备列表
	 * 作者：周志辉
	 * 时间：2017-11-06
	 ************************************************************************************/
	@RequestMapping("/visualization/listDevices")
	public ModelAndView listDevices(HttpServletRequest request, @RequestParam int page,@RequestParam int id){
		ModelAndView mav = new ModelAndView();
		LabRoom labRoom = visualizationService.findLabRoomByPrimaryKey(id);
		labRoom.getLabRoomDevices().size();
		//分页信息
		int pageSize = 10;
		int totalRecords = visualizationService.getLabRoomDeviceBylabroomid(id,1,-1).size();
		Map<String,Integer> pageModel =shareService.getPage(page, pageSize,totalRecords);
		mav.addObject("labRoomDeviceInfo", visualizationService.getLabRoomDeviceBylabroomid(id,page,pageSize)); 
		LabRoomDevice labRoomDevice = new LabRoomDevice();
		mav.addObject("labRoomDevice",labRoomDevice);
		mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", page);
		mav.addObject("pageSize", pageSize);
		//此实验室已经标记位置的设备
		List<LabRoomDevice> labRoomDevices = visualizationService.findLabRoomDevicesByLabRoomIdAndXY(id);
		mav.addObject("labRoomDevices", labRoomDevices); 
		mav.setViewName("visualization/show/listDevices.jsp");
		mav.addObject("id",id);
		return mav;
	}
	
	/************************************************************
	 * @功能-获取设备信息ajax
	 * @作者：裴继超
	 * @日期：2016年1月27日
	 ************************************************************/
	@RequestMapping("/visualization/show/lookDevice")
	public @ResponseBody
	Map<String, String> lookDevice(@RequestParam int id) {
		LabRoomDevice labRoomDevice = visualizationService.findLabRoomDeviceByPrimaryKey(id);
		//设备信息map
		Map<String, String> map = new HashMap<String, String>();
		map.put("labRoomDeviceId", labRoomDevice.getId().toString());
		map.put("xCoordinate", labRoomDevice.getxCoordinate().toString());
		map.put("yCoordinate", labRoomDevice.getyCoordinate().toString());
		map.put("deviceNumber", labRoomDevice.getSchoolDevice().getDeviceNumber());
		map.put("deviceName", labRoomDevice.getSchoolDevice().getDeviceName());
		map.put("deviceFormat", (labRoomDevice.getSchoolDevice().getDeviceFormat()==null)?"":labRoomDevice.getSchoolDevice().getDeviceFormat());
		map.put("deviceSupplier", (labRoomDevice.getSchoolDevice().getManufacturer()==null)?"":labRoomDevice.getSchoolDevice().getManufacturer());
		map.put("devicePattern", (labRoomDevice.getSchoolDevice().getDevicePattern()==null)?"":labRoomDevice.getSchoolDevice().getDevicePattern());
		map.put("qRCodeUrl", (labRoomDevice.getqRCodeUrl()==null)?"":labRoomDevice.getqRCodeUrl());
		map.put("dimensionalCode", (labRoomDevice.getDimensionalCode()==null)?"":labRoomDevice.getDimensionalCode());
		if(labRoomDevice.getSchoolDevice().getSchoolAcademy()!=null){
			map.put("academyName", labRoomDevice.getSchoolDevice().getSchoolAcademy().getAcademyName());
		}else{
			map.put("academyName", "");
		}
		if(labRoomDevice.getWkFolder() != null)
		{
			map.put("videoValue",labRoomDevice.getWkFolder().getId()+","+labRoomDevice.getWkFolder().getType());
		}
		//map.put("devicePattern", labRoomDevice.getSchoolDevice().getDevicePattern());
		return map;
	}
	/************************************************************
	 * @功能-获取设备信息ajax（切换楼层显示）
	 * @作者：刘博越
	 * @日期：2016年1月27日
	 ************************************************************/
	@RequestMapping("/visualization/show/lookDeviceNew")
	public @ResponseBody
	Map<String, String> lookDeviceNew(@RequestParam int id) {
		LabRoomDevice labRoomDevice = visualizationService.findLabRoomDeviceByPrimaryKey(id);
		//设备信息map
		Map<String, String> map = new HashMap<String, String>();
		map.put("labRoomDeviceId", labRoomDevice.getId().toString());
		map.put("xCoordinate", labRoomDevice.getxCoordinate().toString());
		map.put("yCoordinate", labRoomDevice.getyCoordinate().toString());
		map.put("deviceNumber", labRoomDevice.getSchoolDevice().getDeviceNumber());
		map.put("deviceName1", labRoomDevice.getSchoolDevice().getDeviceName());
		map.put("deviceFormat", (labRoomDevice.getSchoolDevice().getDeviceFormat()==null)?"":labRoomDevice.getSchoolDevice().getDeviceFormat());
		map.put("deviceSupplier", (labRoomDevice.getSchoolDevice().getManufacturer()==null)?"":labRoomDevice.getSchoolDevice().getManufacturer());
		map.put("devicePattern", (labRoomDevice.getSchoolDevice().getDevicePattern()==null)?"":labRoomDevice.getSchoolDevice().getDevicePattern());
		map.put("qRCodeUrl", (labRoomDevice.getqRCodeUrl()==null)?"":labRoomDevice.getqRCodeUrl());
		map.put("dimensionalCode", (labRoomDevice.getDimensionalCode()==null)?"":labRoomDevice.getDimensionalCode());
		if(labRoomDevice.getSchoolDevice().getSchoolAcademy()!=null){
			map.put("academyName", labRoomDevice.getSchoolDevice().getSchoolAcademy().getAcademyName());
		}else{
			map.put("academyName", "");
		}
		if(labRoomDevice.getWkFolder() != null)
		{
			map.put("videoValue",labRoomDevice.getWkFolder().getId()+","+labRoomDevice.getWkFolder().getType());
		}
		//map.put("devicePattern", labRoomDevice.getSchoolDevice().getDevicePattern());
		return map;
	}
	/************************************************************
	 * @可视化-切换楼层
	 * @作者：裴继超
	 * @日期：2016年1月22日
	 ************************************************************/
	@RequestMapping("/visualization/show/changeFloor")
	public @ResponseBody
	String changeFloor(@RequestParam String buildNumber,String floor) {
		List<LabRoom> labRooms = visualizationService.getLabRoomsByBuildAndFloor(buildNumber,floor,1,-1);

		String str = "<select id='labRoomListForSelect' onchange='changeRoom(this.options[this.selectedIndex].value);' style='width:500px;'>";
		for(LabRoom l:labRooms){
			str = str + "<option value='"+l.getId()+"'>"+l.getLabRoomName()+"</option>";
		}
		
		str=str+ "</select>";
		if(!labRooms.isEmpty()){
		str+="%"+labRooms.get(0).getId();
		}
		return shareService.htmlEncode(str);
	}
	
	/************************************************************
	 * @可视化-切换楼层
	 * @作者：裴继超
	 * @日期：2016年1月22日
	 ************************************************************/
	@RequestMapping("/visualization/show/changeFloorFirstRoom")
	public @ResponseBody
	String changeFloorFirstRoom(@RequestParam String buildNumber,String floor,HttpServletRequest request) {
		//服务器端口
		String baseAdress = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+request.getContextPath();
		List<LabRoom> labRooms = visualizationService.getLabRoomsByBuildAndFloor(buildNumber,floor,1,-1);
		LabRoom labRoom = new LabRoom();
		String str = "";
		if(labRooms.size()!=0){
			labRoom = labRooms.get(0);
			str = str + "<h2>"+"&nbsp;&nbsp;"+labRoom.getLabRoomName()+"</h2>";
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
			str += "</p><div class='videoContainer'>";
			if(labRoom.getLabRoomActive()!= null && labRoom.getLabRoomActive() == 1){
				//查找实训室所有的硬件物联
				List<LabRoomAgent> agentList = labRoomService.findLabRoomAgentByRoomId(labRoom.getId());
				//筛选出摄像头
				for (LabRoomAgent labRoomAgent : agentList) {
					if(shareService.checkCDictionary(labRoomAgent.getCDictionary().getId(),"3","c_agent_type")){
						str = str + "<a class='room_info_btn' style='cursor:pointer;' onclick='openVideo2("+labRoom.getId()+")'"+"> 实时视频</a>";
					}
				}
			}
			str += "</div>";
			str = str + "</p> <a class='room_visualization' target='_blank' href='"+request.getContextPath()+"/visualization/show/roomImage?page=1&id="+labRoom.getId()+"'> 设备信息</a>";
			String url = baseAdress + "/tms/courseList?currpage=1";
			if(labRoom.getLabRoomNumber().equals("4412")){
				url = "http://www.zjcclims.net:3380/wk/course/140";
			}else if(labRoom.getLabRoomNumber().equals("4118")){
				url = "http://www.zjcclims.net:3380/wk/course/82";
			}
			
			str = str + " <a class='room_visualization' target='_blank' href='"+url+"'>教学动态</a> </div>";
		}

		
		return shareService.htmlEncode(str);
	}
	
	/************************************************************
	 * @可视化-切换楼层第一个房间
	 * @作者：裴继超
	 * @日期：2016年1月22日
	 ************************************************************/
	@RequestMapping("/visualization/show/changeFloorFirstRoomMap")
	public @ResponseBody
	Map changeFloorFirstRoomMap(@RequestParam String buildNumber,String floor,HttpServletRequest request) {
		List<LabRoom> labRooms = visualizationService.getLabRoomsByBuildAndFloor(buildNumber,floor,1,-1);
		Map<String, String> map = new HashMap<String, String>();
		map = visualizationService.findLabRoomMap(labRooms.get(0));
		return map;
	}
	
	/************************************************************
	 * @可视化-切换实验室全景图
	 * @作者：裴继超
	 * @日期：2016年3月14日
	 ************************************************************/
	@RequestMapping("/visualization/show/changeFloorFirstRoomImage")
	public @ResponseBody
	String changeFloorFirstRoomImage(@RequestParam String buildNumber,String floor,int type,HttpServletRequest request) {
		List<LabRoom> labRooms = visualizationService.getLabRoomsByBuildAndFloor(buildNumber,floor,1,-1);
		LabRoom labRoom = new LabRoom();
		String str = "";
		if(labRooms.size()!=0){
			labRoom = labRooms.get(0);
			CommonDocument commonDocument = commonDocumentService.findCommonDocumentByLabRoom(labRoom,type);
			if(commonDocument==null){
				return str;
			}
			str = ""+commonDocument.getDocumentUrl()+"";
		}else{
			return str;
		}
		return shareService.htmlEncode(str);
	}
	
	/************************************************************
	 * @可视化-切换实验室
	 * @作者：裴继超
	 * @日期：2016年1月23日
	 ************************************************************/
	@RequestMapping("/visualization/show/changeRoomMap")
	public @ResponseBody
	Map changeRoomMap(@RequestParam int id,HttpServletRequest request) {
		LabRoom labRoom = visualizationService.findLabRoomByPrimaryKey(id);
		Map<String, String> map = new HashMap<String, String>();
		map = visualizationService.findLabRoomMap(labRoom);
		return map;
	}
	
	/************************************************************
	 * @可视化-切换实验室
	 * @作者：汪哲玮
	 * @日期：2018年4月22日
	 ************************************************************/
	@RequestMapping("/visualization/show/changeRoomAgentList")
	public @ResponseBody
	String changeRoomAgentList(@RequestParam int id,HttpServletRequest request) {
		LabRoom labRoom = visualizationService.findLabRoomByPrimaryKey(id);
		String map = visualizationService.findLabRoomAgent(labRoom);
		return shareService.htmlEncode(map);
	}
	
	
	/************************************************************
	 * @可视化-切换实验室时切换获取实验室全部设备显示
	 * @作者：魏好
	 * @日期：2018年1月12日
	 ************************************************************/
	@RequestMapping("/visualization/show/changeRoomDeviceMap")
	public @ResponseBody
	String changeRoomDeviceMap(@RequestParam int id,HttpServletRequest request) {
		LabRoom labRoom = visualizationService.findLabRoomByPrimaryKey(id);
		String str = visualizationService.findLabRoomDeviceMap(labRoom);
		return shareService.htmlEncode(str);
	}
	
	/************************************************************
	 * @可视化-切换实验室时切换获取实验室全部设备显示By查询条件
	 * @作者：汪哲玮
	 * @日期：2018年4月18日
	 ************************************************************/
	@RequestMapping("/visualization/show/changeRoomDeviceSearched")
	public @ResponseBody
	String changeRoomDeviceSearched(@RequestParam int id,@RequestParam int lend,@RequestParam int appoint,@RequestParam String name,HttpServletRequest request) {
		LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(id);
		int devicelend;
		int deviceAppoint;
		//字典表labrromdevice的可借字段和可预约字段相应值设定
		if(lend==0){
			devicelend=622;
		}else{
			devicelend=621;
		}
		if(appoint==0){
			deviceAppoint=2;
		}else{
			deviceAppoint=1;
		}
		String str = visualizationService.findLabRoomDeviceMapSearched(labRoom,name,devicelend,deviceAppoint);
		return shareService.htmlEncode(str);
	}
	
	
	/************************************************************
	 * @可视化-切换实验室时切换获取实验室当前课表
	 * @作者：汪哲玮
	 * @日期：2018年4月15日
	 ************************************************************/
	@RequestMapping("/visualization/show/changeRoomTimeTableMap")
	public @ResponseBody
	Map changeRoomTimeTableMap(@RequestParam int id,HttpServletRequest request) {
		LabRoom labRoom = visualizationService.findLabRoomByPrimaryKey(id);
		Map<String, String> map = new HashMap<String, String>();
		map = visualizationService.findLabRoomTimetableMap(labRoom);
		return map;
	}
	
	
	/************************************************************
	 * @可视化-切换实验室时切换获取实验楼当前课表
	 * @作者：汪哲玮
	 * @日期：2018年4月15日
	 ************************************************************/
	@RequestMapping("/visualization/show/changeBuildTimetable")
	public @ResponseBody
	String changeBuildTimetable(@RequestParam String buildnumber,String campusNumber) {
		String map="";
		map = visualizationService.findLabBuildingTimetableMap(buildnumber, campusNumber);
		return shareService.htmlEncode(map);
	}
	
	
	/************************************************************
	 * @可视化-切换实验室时切换获取实验室软件信息
	 * @作者：汪哲玮
	 * @日期：2018年4月15日
	 ************************************************************/
	@RequestMapping("/visualization/show/changeRoomSoftwareMap")
	public @ResponseBody
	String changeRoomSoftwareMap(@RequestParam int id,HttpServletRequest request) {
		LabRoom labRoom = visualizationService.findLabRoomByPrimaryKey(id);
		String map = visualizationService.findLabRoomSoftwareMap(labRoom);
		return shareService.htmlEncode(map);
	}
	/************************************************************
	 * @可视化-切换实验室MAP
	 * @作者：裴继超
	 * @日期：2016年1月23日
	 ************************************************************/
	@RequestMapping("/visualization/show/changeRoom")
	public @ResponseBody
	String changeRoom(@RequestParam int id,HttpServletRequest request) {
		LabRoom labRoom = visualizationService.findLabRoomByPrimaryKey(id);
		String baseAdress = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+request.getContextPath();
		HttpSession session=request.getSession();
		session.setAttribute("roomId", id);
		String str = "";
		str = str + "<h2>"+"&nbsp;&nbsp;"+labRoom.getLabRoomName()+"</h2>";
		str = str + "<div class='banner_info'><p>容量：" + labRoom.getLabRoomCapacity() + "人</p>";
		str = str + "<p>面积：" + labRoom.getLabRoomArea() + "平方米</p><p>状态：";

		if(labRoom.getLabRoomActive() == 1)
		{
			str = str + "可用、";
			if(labRoom.getLabRoomReservation() == 1){
				str += "可预约";
			}else{
				str += "不可预约";
			}
			str += "</p><div class='videoContainer'>";
			//查找实训室所有的硬件物联
			List<LabRoomAgent> agentList = labRoomService.findLabRoomAgentByRoomId(labRoom.getId());
			//筛选出摄像头
			for (LabRoomAgent labRoomAgent : agentList) {
				if(shareService.checkCDictionary(labRoomAgent.getCDictionary().getId(),"3","c_agent_type")){
					str = str + " <a class='room_info_btn' style='cursor:pointer;' onclick='openVideo("+labRoomAgent.getId()+")'"+"> 实时视频</a>";
				}
			}
			str += "</div>";
		}else{
			str = str + "不可用、不可预约";
		}
		
		str = str + "</p> <a class='room_visualization' target='_blank' href='"+request.getContextPath()+"/visualization/show/roomImage?page=1&id="+labRoom.getId()+"'> 设备信息</a>";
		String url = baseAdress + "/tms/courseList?currpage=1";
		if(labRoom.getLabRoomNumber().equals("4412")){
			url = "http://www.zjcclims.net:3380/wk/course/140";
		}else if(labRoom.getLabRoomNumber().equals("4118")){
			url = "http://www.zjcclims.net:3380/wk/course/82";
		}
		
		str = str + " <a class='room_visualization' target='_blank' href='"+url+"'>教学动态</a> </div>";

		return shareService.htmlEncode(str);
	}

	/************************************************************
	 * @可视化-切换实验室MAP（包含实验室设备）
	 * @作者：刘博越
	 * @日期：2018年8月14日
	 ************************************************************/
	@RequestMapping("/visualization/show/changeRoomNew")
	public @ResponseBody
	List<Object> changeRoomNew(@RequestParam int id,HttpServletRequest request) {
		LabRoom labRoom = visualizationService.findLabRoomByPrimaryKey(id);
		//获取已经标点实验设备
		List<LabRoomDevice> labRoomDevices = visualizationService.findLabRoomDevicesByLabRoomIdAndXY(id);

		String baseAdress = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+request.getContextPath();
		HttpSession session=request.getSession();
		session.setAttribute("roomId", id);
		String str = "";
		str = str + "<h2>"+"&nbsp;&nbsp;"+labRoom.getLabRoomName()+"</h2>";
		str = str + "<div class='banner_info'><p>容量：" + labRoom.getLabRoomCapacity() + "人</p>";
		str = str + "<p>面积：" + labRoom.getLabRoomArea() + "平方米</p><p>状态：";

		if(labRoom.getLabRoomActive() == 1)
		{
			str = str + "可用、";
			if(labRoom.getLabRoomReservation() != null && labRoom.getLabRoomReservation() == 1){
				str += "可预约";
			}else{
				str += "不可预约";
			}
			str += "</p><div class='videoContainer'>";
			//查找实训室所有的硬件物联
			List<LabRoomAgent> agentList = labRoomService.findLabRoomAgentByRoomId(labRoom.getId());
			//筛选出摄像头
			for (LabRoomAgent labRoomAgent : agentList) {
				if(shareService.checkCDictionary(labRoomAgent.getCDictionary().getId(),"3","c_agent_type")){
					str = str + " <a class='room_info_btn' style='cursor:pointer;' onclick='openVideo("+labRoomAgent.getId()+")'"+"> 实时视频</a>";
				}
			}
			str += "</div>";
		}else{
			str = str + "不可用、不可预约";
		}

		str = str + "</p> <a class='room_visualization' target='_blank' href='"+request.getContextPath()+"/visualization/show/roomImage?page=1&id="+labRoom.getId()+"'> 设备信息</a>";
		String url = baseAdress + "/tms/courseList?currpage=1";
		if(labRoom.getLabRoomNumber().equals("4412")){
			url = "http://www.zjcclims.net:3380/wk/course/140";
		}else if(labRoom.getLabRoomNumber().equals("4118")){
			url = "http://www.zjcclims.net:3380/wk/course/82";
		}

		str = str + " <a class='room_visualization' target='_blank' href='"+url+"'>教学动态</a> </div>";

		List<Object> list = new ArrayList<>();
		list.add(shareService.htmlEncode(str));
		list.add(labRoomDevices);
		return list;
		//return shareService.htmlEncode(str);
	}
	
	

	/************************************************************
	 * @可视化-切换实验室全景图
	 * @作者：裴继超
	 * @日期：2016年3月14日
	 ************************************************************/
	@RequestMapping("/visualization/show/changeRoomImage")
	public @ResponseBody
	String changeRoomPanoramagram(@RequestParam int id,int type,HttpServletRequest request) {
		LabRoom labRoom = visualizationService.findLabRoomByPrimaryKey(id);

		CommonDocument commonDocument = commonDocumentService.findCommonDocumentByLabRoom(labRoom,type);
		//为空就是没有
		if(commonDocument==null){
			return "";
		}
		String str = ""+commonDocument.getDocumentUrl()+"";
		return shareService.htmlEncode(str);
	}
	
	/****************************************************************************
	 * 功能：AJAX根据校区id查询楼宇
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/visualization/findBuildByCampusId")
	public @ResponseBody String findBuildByCampusId(@RequestParam String id, HttpServletResponse response) {
		
		response.setContentType("text/json; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		//根据校区id查询楼宇
		List<SystemBuild> builds=systemBuildService.findBuildByCampusId(id);
		String a="请选择";
		String s="<option  value='" + "'>" +a+ "</option>";
		for (SystemBuild b : builds) {
			s+="<option  value='" +b.getBuildNumber() + "'>" +b.getBuildName()+ "</option>";
		}
		return shareService.htmlEncode(s);
	}

	/****************************************************************************
	 * 功能：AJAX根据楼宇查询房间
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/visualization/findRoomByBuildNumber")
	public @ResponseBody String findRoomByBuildNumber(@RequestParam String buildNumber, HttpServletResponse response) {
		
		response.setContentType("text/json; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		//根据楼宇编号查询房间
		List<SystemRoom> roomList=systemBuildService.findRoomByBuildNumber(buildNumber);
		String a="请选择";
		String s="<option  value='" + "'>" +a+ "</option>";
		for (SystemRoom m : roomList) {
			s+="<option  value='" +m.getRoomName()+ "'>" +m.getRoomName()+"("+m.getRoomNo()+")"+ "</option>";
		}
		return shareService.htmlEncode(s);
	}
	/************************************************************
	 * @功能-获取房间信息ajax
	 * @作者：徐晓莹
	 * @日期：2017年3月19日
	 ************************************************************/
	@RequestMapping("/visualization/show/lookBuild")
	public @ResponseBody
	Map<String, String> lookBuild(@RequestParam String bulidnumber) {
		List<Object[]> deviceList=labRoomService.listSchoolDeviceAcademy(bulidnumber);
		
		//房间信息map
		Map<String, String> map = new HashMap<String, String>();
		if(deviceList!=null && deviceList.size()>0){
			map.put("device_all",deviceList.get(0)[1].toString());
			map.put("device_all_open",deviceList.get(0)[2].toString());
			map.put("device_all_close",deviceList.get(0)[3].toString());
			map.put("device_all_unknown",deviceList.get(0)[4].toString());
			map.put("device_all_smart",deviceList.get(0)[5].toString());
			map.put("less_10",deviceList.get(0)[6].toString());
			map.put("less_10_open",deviceList.get(0)[7].toString());
			map.put("less_10_close",deviceList.get(0)[8].toString());
			map.put("less_10_unknown",deviceList.get(0)[9].toString());
			map.put("less_10_smart",deviceList.get(0)[10].toString());
			map.put("over_40",deviceList.get(0)[11].toString());
			map.put("over_40_open",deviceList.get(0)[12].toString());
			map.put("over_40_close",deviceList.get(0)[13].toString());
			map.put("over_40_unknown",deviceList.get(0)[14].toString());
			map.put("over_40_smart",deviceList.get(0)[15].toString());
			map.put("between_10and40",deviceList.get(0)[16].toString());
			map.put("between_10and40_open",deviceList.get(0)[17].toString());
			map.put("between_10and40_close",deviceList.get(0)[18].toString());
			map.put("between_10and40_unknown",deviceList.get(0)[19].toString());
			map.put("between_10and40_smart",deviceList.get(0)[20].toString());
		}
		else{
			map.put("device_all","");
			map.put("device_all_open","");
			map.put("device_all_close","");
			map.put("device_all_unknown","");
			map.put("device_all_smart","");
			map.put("less_10","");
			map.put("less_10_open","");
			map.put("less_10_close","");
			map.put("less_10_unknown","");
			map.put("less_10_smart","");
			map.put("over_40","");
			map.put("over_40_open","");
			map.put("over_40_close","");
			map.put("over_40_unknown","");
			map.put("over_40_smart","");
			map.put("between_10and40","");
			map.put("between_10and40_open","");
			map.put("between_10and40_close","");
			map.put("between_10and40_unknown","");
			map.put("between_10and40_smart","");
		}
		return map;
	}

	/**
	 * Description 可视化--实验室可视化--切换实验室视频
	 * @param id
	 * @param request
	 * @return
	 * @author 陈乐为 2019年4月29日 修改
	 */
	@RequestMapping("/visualization/show/changeRoomMovie")
	public @ResponseBody
	String changeRoomMovie(@RequestParam int id, HttpServletRequest request) {
		// 获取实验室的视频设备列表
		List<LabRoomAgent> labRoomAgents = labRoomService.getAgentByType(id, "3","c_agent_type");
		// 取第一个视频设备
		if (labRoomAgents != null && labRoomAgents.size() > 0) {
			LabRoomAgent agent = labRoomAgents.get(0);
			// 流媒体服务器地址
			String serverIp = agent.getCommonServer().getServerIp();
			// 端口
			String hardwarePort = "1935";//agent.getHardwarePort();

			// 摄像头本身ip的 xxx.xxx.xxx.123 最后那个123
			String lastFour = "";
			// 192.168.0.sz
			String hardWareIp = agent.getHardwareIp();
			// split .有问题，所以替换成了 , 逗号
			hardWareIp = hardWareIp.replace(".", ",");
			if (!EmptyUtil.isStringEmpty(hardWareIp)
					&& !EmptyUtil.isObjectEmpty(hardWareIp.split(","))
					&& hardWareIp.split(",").length > 3) {
				lastFour = hardWareIp.split(",")[2] + hardWareIp.split(",")[3];
			}

			// 测试输出
			System.out.println("rtmp://" + serverIp + ":" + hardwarePort + "/live/" + lastFour);
			return "rtmp://" + serverIp + ":" + hardwarePort + "/live/" + lastFour;
		}else {
			return "";
		}
	}
	
	/************************************************************
	 * @可视化-切换实验室视频2
	 * @作者：汪哲玮
	 * @日期：2018年4月20日
	 ************************************************************/
	@RequestMapping("/visualization/show/changeRoomMovie2")
	public @ResponseBody
	String changeRoomMovie2(@RequestParam int id,@RequestParam int agentId, HttpServletRequest request) {
		// 传入的是labroom 的id 先查询labroom，再拿里面的set集合的labroomAgents。默认读取第一个视频
		LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(id);

		Set<LabRoomAgent> labRoomAgentSet = labRoom.getLabRoomAgents();

		// 多个视频就默认第一个视频
//		int agentId = 0;
//		for (LabRoomAgent labRoomAgent : labRoomAgentSet) {
//			// 类型549才是视频还要判断是否可用
//			if (549 == labRoomAgent.getCDictionary().getId()
//					&& labRoomAgent.getCDictionary().getEnabled()) {
//				agentId = labRoomAgent.getId();
//				break;
//			}
//		}

		// 获取agent然后拼接返回，然后ajax设置source的src
		LabRoomAgent agent = labRoomAgentDAO
				.findLabRoomAgentByPrimaryKey(agentId);
		// 如果为空就是没有视频
		if (agent == null) {
			return "";
		} else {

			// 流媒体服务器地址
			String serverIp = agent.getCommonServer().getServerIp();
			// 端口
			String hardwarePort = "1935";//agent.getHardwarePort();

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

			// 测试输出
			System.out.println("rtmp://" + serverIp + ":" + hardwarePort
					+ "/live/" + lastFour);
			return "rtmp://" + serverIp + ":" + hardwarePort + "/live/"
					+ lastFour;
		}
	}

	/**
	 * @Description 根据楼宇和楼层获取楼层图
	 * @author 张德冰
	 * @data 2018-10-30
	 */
	@RequestMapping(value="/visualization/show/changeSystemFloorPic", produces="application/json;charset=utf-8")
	@ResponseBody
	public String changeSystemFloorPic(@RequestParam String buildNumber,String floor) {

		String str = "";
		//根据楼宇和楼层获取图片
		List<SystemFloorPic> systemFloorPics = visualizationService.findSystemFloorPic(buildNumber, Integer.parseInt(floor));
		if(systemFloorPics != null && systemFloorPics.size() != 0){
			str = systemFloorPics.get(0).getDocumentUrl();
		}
		return str;

	}

	/****************************************************************************
	 * 可视化引擎展示
	 * 裴继超
	 * 2016年1月23日
	 ****************************************************************************/
	@RequestMapping("/visualization/show/visualNew")
	public ModelAndView buildPlaceNew(@RequestParam String directoryName,String type){
		ModelAndView mav = new ModelAndView();
		List<SystemBuild> buils = systemBuildService.finAllSystemBuilds();
		mav.addObject("buils", buils);
		//当前登录人
		User user = shareService.getUser();
		mav.addObject("user", user);
		if(user!=null){
			String authority = "";
			int i = 1;
			Set<Authority> auths = user.getAuthorities();
			for (Authority a : auths) {
				if (a.getType() >= i) {
					authority = a.getCname();
					i = a.getType();
				}
			}
			mav.addObject("authority", authority);
		}else{
			mav.addObject("authority", null);
		}
		mav.addObject("PROJECT_NAME", pConfig.PROJECT_NAME);
//		mav.addObject("resourceContainerHost", resourceContainerHost);
//		mav.addObject("directoryEngineHost", directoryEngineHost);
//		mav.addObject("visualHost", visualHost);
		mav.addObject("createUser", user.getUsername());
		mav.addObject("directoryName", directoryName);
		mav.addObject("type", type);
		mav.setViewName("visualization/show/visualNew.jsp");

		return mav;
	}
	/****************************************************************************
	 * 可视化引擎展示
	 * 裴继超
	 * 2016年1月23日
	 ****************************************************************************/
	@RequestMapping("/visualization/changeBackground")
	public ModelAndView changeBackground(@RequestParam Integer page,String type, HttpServletRequest request, @ModelAttribute("selected_academy") String acno,
										 @ModelAttribute LabRoomDevice labRoomDevice) {
		ModelAndView mav = new ModelAndView();
		// 每页10条记录
		int pageSize = 10;
		// 查询出来的总记录条数
		int totalRecords = 0;
		if(type.equals("device")){
			totalRecords = visualizationService.getAllLabRoomDevices(labRoomDevice, 1, -1).size();
			// 根据分页信息查询出来的记录
			List<LabRoomDevice> labRoomDevices = visualizationService.getAllLabRoomDevices(labRoomDevice, page, pageSize);
			mav.addObject("commonLists", labRoomDevices);
		}else if(type.equals("campus")){
			totalRecords = visualizationService.getAllSystemCampus(labRoomDevice,1, -1).size();
			List<SystemCampus> systemCampuses = visualizationService.getAllSystemCampus(labRoomDevice,page,pageSize);
			mav.addObject("commonLists",systemCampuses);
		}else if(type.equals("build")){
			totalRecords = visualizationService.getAllSystemBuilds(labRoomDevice,1, -1).size();
			List<SystemBuild> systemBuilds = visualizationService.getAllSystemBuilds(labRoomDevice,page,pageSize);
			mav.addObject("commonLists",systemBuilds);
		}else if(type.equals("room")){
			totalRecords = visualizationService.getAllSystemRooms(labRoomDevice,0, 0).size();
			List<SystemRoomVisual> systemRooms = visualizationService.getAllSystemRooms(labRoomDevice,page,pageSize);
			mav.addObject("commonLists",systemRooms);
		}

		// 分页信息
		Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", page);
		mav.addObject("pageSize", pageSize);
		mav.addObject("type", type);
//		mav.addObject("resourceContainerHost", resourceContainerHost);
//		mav.addObject("directoryEngineHost", directoryEngineHost);
//		mav.addObject("visualHost", visualHost);
		User user = shareService.getUser();
		mav.addObject("user", user);
		mav.addObject("createUser", user.getUsername());
		//获取所有学院
		mav.addObject("listSchoolAcademy", systemService.getAllSchoolAcademy(1, -1));
		//获取所有校区
		mav.addObject("listSystemCampus", systemService.getAllSystemCampus(1, -1));
		mav.setViewName("visualization/show/backgroundList.jsp");
		return mav;
	}

	/****************************************************************************
	 +	 * 可视化引擎展示
	 +	 * 裴继超
	 +	 * 2016年1月23日
	 +	 ****************************************************************************/
	@RequestMapping("/visualization/mapNew")
	public ModelAndView mapNew() {
		ModelAndView mav = new ModelAndView();
		List<SystemBuild> buils = systemBuildService.finAllSystemBuilds();
		mav.addObject("buils", buils);
		//当前登录人
		User user = shareService.getUser();
		mav.addObject("user", user);
		if (user != null) {
			String authority = "";
			int i = 1;
			Set<Authority> auths = user.getAuthorities();
			for (Authority a : auths) {
				if (a.getType() >= i) {
					authority = a.getCname();
					i = a.getType();

				}

			}
			mav.addObject("authority", authority);

		} else {
			mav.addObject("authority", null);

		}
		mav.addObject("PROJECT_NAME", pConfig.PROJECT_NAME);
//		mav.addObject("resourceContainerHost", resourceContainerHost);
//		mav.addObject("directoryEngineHost", directoryEngineHost);
//		mav.addObject("visualHost", visualHost);
		mav.addObject("createUser", user.getUsername());
		mav.setViewName("visualization/show/mapNew.jsp");
		return mav;

	}
	@RequestMapping("/visualization/allProperties")
	public ModelAndView allProperties() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("visualization/show/all_properties.jsp");
		return mav;

	}

	@RequestMapping("/visualization/visualTemplet")
	public ModelAndView visualTemplet() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("visualization/show/visual-templet.jsp");
		return mav;

	}
}

