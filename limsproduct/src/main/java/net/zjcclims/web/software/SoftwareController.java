package net.zjcclims.web.software;

import java.net.BindException;
import java.text.SimpleDateFormat;
import java.util.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.CommonDocumentService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.device.LabRoomDeviceService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.operation.OperationService;
import net.zjcclims.service.software.SoftwareService;
import net.zjcclims.service.system.SystemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller("SoftwareController")
@SessionAttributes("selected_academy")
public class SoftwareController<JsonResult> {
	@Autowired private SoftwareService softwareService;
	@Autowired private SystemService systemService;
	@Autowired private ShareService shareService;
	@Autowired private OperationService operationService;
	@Autowired private LabRoomService labRoomService;
	@Autowired private LabRoomDeviceService labRoomDeviceService;
	
	@Autowired private OperationItemDAO operationItemDAO;
	@Autowired private SoftwareItemRelatedDAO softwareItemRelatedDAO;
	@Autowired private LabRoomDAO labRoomDAO;
	@Autowired private SoftwareRoomRelatedDAO softwareRoomRelatedDAO;
	@Autowired private SoftwareDAO softwareDAO;
	@Autowired private CommonDocumentService commonDocumentService;
	@Autowired private SoftwareReserveDAO softwareReserveDAO;
	@Autowired private MessageDAO messageDAO;

	/*****************************************************************
	 * Description：软件列表
	 * 
	 * @param：currPage 页码
	 * @author 杨礼杰
	 * @date: 2017-06-23
	 *****************************************************************/
	@RequestMapping("/indexSoftware")
	public ModelAndView indexSoftware(@RequestParam int currPage,
			@ModelAttribute Software software,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		int pageSize = 10;
		int totalRecords = softwareService.findSoftwareByQuery(1, -1,
				software,request).size();
		List<Software> listSoftware = softwareService.findSoftwareByQuery(currPage, pageSize, software,request);
		//分页
		mav.addObject("pageModel",shareService.getPage(currPage, pageSize, totalRecords));
		mav.addObject("listSoftware", listSoftware);
		mav.addObject("currPage", currPage);
		mav.addObject("pageModel",
				shareService.getPage(currPage, pageSize, totalRecords));
		mav.setViewName("teaching/listSoftwares.jsp");
		return mav;
	}

	/************************************************************
	 * description：软件管理列表导出
	 * @author zhangyu
	 * @日期：2018-1-3
	 ************************************************************/
	@RequestMapping("/exportSoftList")
	public void exportSoftList(@RequestParam int currPage,@ModelAttribute Software software,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		int pageSize = 10;
		List<Software> listSoftware = softwareService.findAllSoftwareByQuery(currPage, pageSize, software);
		softwareService.exportSoftList(listSoftware,request,response);
	}
	/***********************************************************************************
	 * description：软件管理列表导入
	 * @author zhangyu
	 * @日期：2018-1-3
	 * **********************************************************************************/
	@RequestMapping("/importSoft")
	public ModelAndView importSoft(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		String fileName = shareService.getUpdateFilePath(request);
		String logoRealPathDir = request.getSession().getServletContext().getRealPath("/");
		String filePath = logoRealPathDir + fileName;
		System.out.println(filePath);
		if(filePath.endsWith("xls")||filePath.endsWith("xlsx")){
			softwareService.importSoft(filePath);
		}
		mav.setViewName("redirect:/indexSoftware?currPage=1");
		return mav;
	}
	/***********************************************************
	 * Description:软件详细信息查看
	 * 
	 * @param： idKey 软件编号
	 * @author 邵志峰
	 * @date: 2017-06-02
	 ***********************************************************/
	@RequestMapping("/softwareInfo")
	public ModelAndView softwareInfo(@RequestParam Integer idkey) {
		ModelAndView mav = new ModelAndView();
		Software software = softwareService.findSoftwareByPrimaryKey(idkey);
		if(software != null && software.getLabRoom() != null) {
			String[] roomIds = software.getLabRoom().split(",");
			StringBuilder roomNames = new StringBuilder();
			for (String roomname : roomIds) {
				Set<LabRoom> labRooms = (Set<LabRoom>) labRoomDAO.findLabRoomByLabRoomName(roomname);
					for (LabRoom tmp : labRooms) {
						//				LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(Integer.valueOf(roomId));
						if (tmp == null) {
							roomNames.append(" ");
						} else {
							roomNames.append(tmp.getLabRoomName() + " ");
						}
					}
			}
			software.setLabRoom(roomNames.toString());
		}
		
		mav.addObject("softwareInfo", software);
		mav.setViewName("teaching/softwareInfo.jsp");
		return mav;
	}

	/***********************************************************
	 * Description:软件管理--新建
	 * 
	 * @author 杨礼杰
	 * @date: 2017-06-23
	 ***********************************************************/
	@RequestMapping("/newSoftware")
	public ModelAndView newSoftware(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("software", new Software());
		int softwareId=-1;
		mav.addObject("softwareId",softwareId);
		// 所有中心
		mav.addObject("listSchoolAdademy", systemService.getAllSchoolAcademy(1, -1));
		mav.addObject("isNew", 1);
		// 所有项目
		List<OperationItem> listOperationItem = operationService.findAllOperationItemByQuery(1, -1, null, -1);
		mav.addObject("listOperationItem", listOperationItem);
		List<LabRoom> listLabRoom = labRoomService.findAllLabRoomByQuery(1, -1, null, 9,request);
		mav.addObject("listLabRoom", listLabRoom);
//		mav.addObject("page",page);
		mav.setViewName("teaching/newSoftware.jsp");
		return mav;
	}

	/***********************************************************
	 * Description:软件管理--保存
	 * 
	 * @author 杨礼杰
	 * @date: 2017-06-23
	 ***********************************************************/
	@RequestMapping("/saveSoftware")
	public String saveSoftware(@ModelAttribute Software software,Integer page) {
		software = softwareService.saveSoftware(software);
		// 保存软件-项目关系
		String items = software.getOperationItem();
		if(items!=null && items!=""){
		    String[] str= items.split(",");
			for (String string : str) {
				if(string!=null && string!=""){
					SoftwareItemRelated softwareItemRelated = softwareService.getItemRelatedByQuery(software.getId(), Integer.parseInt(string));
					if(softwareItemRelated != null && softwareItemRelated.getId() != null) {
						//已經存在 do nothing
					}else {
						SoftwareItemRelated related = new SoftwareItemRelated();
						OperationItem operationItem = operationItemDAO.findOperationItemByPrimaryKey(Integer.parseInt(string));
						if(operationItem!=null){
							related.setSoftware(software);
							related.setOperationItem(operationItem);
							softwareItemRelatedDAO.store(related);
						}
					}
				}
			}
		}
		List<SoftwareRoomRelated> softwareRoomRelated = softwareService.findSoftwareRoomRelatedByRoomId(software.getId());
		for(SoftwareRoomRelated s:softwareRoomRelated){
			softwareRoomRelatedDAO.remove(s);
		} 
		// 保存软件-实训室关系
		String rooms = software.getLabRoom();
		if(rooms!=null && rooms!=""){
		    String[] str= rooms.split(",");
			for (String string : str) {
				/*if(string!=null && string!=""){
					SoftwareRoomRelated softwareRoomRelated = softwareService.getRoomRelatedByQuery(software.getId(), Integer.parseInt(string));
					if(softwareRoomRelated != null && softwareRoomRelated.getId() != null) {
						//已經存在 do nothing
					}else {
						SoftwareRoomRelated related = new SoftwareRoomRelated();
						LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(Integer.parseInt(string));
						if(labRoom!=null){
							related.setSoftware(software);
							related.setLabRoom(labRoom);
							softwareRoomRelatedDAO.store(related);
						}*/
				//删除
				if(string!=null && string!=""){
						SoftwareRoomRelated related = new SoftwareRoomRelated();
						LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(Integer.parseInt(string));
						if(labRoom!=null){
							related.setSoftware(software);
							related.setLabRoom(labRoom);
							softwareRoomRelatedDAO.store(related);
					}
				}
			}
		}
		
		return "redirect:/editSoftware?softwareId="+software.getId()+"&currPage="+page;
	}

	/***********************************************************
	 * Description:软件管理--编辑
	 * 
	 * @author 杨礼杰
	 * @date: 2017-06-23
	 ***********************************************************/
	@RequestMapping("/editSoftware")
	public ModelAndView editSoftware(@RequestParam int softwareId,HttpServletRequest request,Integer page) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("isNew", 0);
		mav.addObject("listSchoolAdademy", systemService.getAllSchoolAcademy(1, -1));
		Software software=new Software();
		if(softwareId !=-1) {
			software = softwareService.findSoftwareByPrimaryKey(softwareId);
		}
		mav.addObject("softwareId",softwareId);
		mav.addObject("software", software);
		// 已选、未选的项目列表
		if(software.getSoftwareItemRelateds() != null) {
			List<OperationItem> operationItems = new ArrayList<OperationItem>();
			List<OperationItem> listOperationItem = operationService.findAllOperationItemByQuery(1, -1, null, -1);
			for(SoftwareItemRelated related : software.getSoftwareItemRelateds()) {
				OperationItem operationItem = related.getOperationItem();
				operationItems.add(operationItem);
				listOperationItem.remove(operationItem);
			}
			// 已选
			mav.addObject("operationItems", operationItems);
			// 未选
			mav.addObject("listOperationItem", listOperationItem);
		}
		// 已选、未选的实训室列表
				if(software.getSoftwareRoomRelateds() != null) {
					List<LabRoom> labRooms = new ArrayList<LabRoom>();
					List<LabRoom> listLabRoom = labRoomService.findAllLabRoomByQuery(1, -1, null, 9,request);
					for(SoftwareRoomRelated related : software.getSoftwareRoomRelateds()) {
						LabRoom labRoom = related.getLabRoom();
						labRooms.add(labRoom);
						listLabRoom.remove(labRoom);
					}
					// 已选
					mav.addObject("labRooms", labRooms);
					// 未选
					mav.addObject("listLabRoom", listLabRoom);
				}
		mav.addObject("page",page);
		mav.setViewName("teaching/editSoftware.jsp");
		return mav;
	}

	/***********************************************************
	 * Description:软件管理--删除
	 * 
	 * @author 杨礼杰
	 * @date: 2017-06-23
	 ***********************************************************/
	@RequestMapping("/deleteSoftware")
	public String deleteSoftware(@RequestParam int softwareId,int page) {
		Software software = softwareService
				.findSoftwareByPrimaryKey(softwareId);
		softwareService.deleteSoftware(software);
		return "redirect:/indexSoftware?currPage="+page;
	}
	/***********************************************************
	 * Description:文件上传
	 * @author 周志辉
	 * @date: 2017-09-18
	 ***********************************************************/

	@RequestMapping("/softwareUseInstallDocumentUpload")
	public ModelAndView softwareUseInstallDocumentUpload(HttpServletRequest request, HttpServletResponse response, BindException errors, Integer id,Integer type) throws Exception {
		ModelAndView mav = new ModelAndView();
		softwareService.softwareDocumentUpload(request, response, id, type);
		mav.setViewName("redirect:/editSoftware?softwareId="+id);
		return mav;
	}
	/****************************************************************************
	 * @功能：下载软件文档
	 * @作者：周志辉
	 ****************************************************************************/
	@RequestMapping("/downloadDocument")
	public void downloadDocument(HttpServletRequest request, HttpServletResponse response, int id) {
		// id对应的文档
		CommonDocument doc = commonDocumentService.findCommonDocumentByPrimaryKey(id);
		softwareService.downloadFile(doc, request, response);
	}
	/****************************************************************************
	 * @功能：删除软件文档
	 * @作者：周志辉
	 ****************************************************************************/
	@RequestMapping("/deleteSoftwareDocument")
	public String deleteSoftwareDocument(@RequestParam int id,int softwareId){
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		// id对应的设备图片
		CommonDocument doc = commonDocumentService.findCommonDocumentByPrimaryKey(id);
		commonDocumentService.deleteCommonDocument(doc);
		return "redirect:/editSoftware?softwareId="+softwareId;
	}

	/**
	 * Description:软件申请保存
	 * @param softwareReserve
	 * @return
	 * @throws Exception
	 * @author 陈乐为 2018-12-27
	 */
	@Transactional
	@RequestMapping("saveSoftwareReserve")
	public String  saveSoftwareReserve(@ModelAttribute SoftwareReserve softwareReserve, HttpServletRequest request) throws Exception{
		// 安装时间
		String install = request.getParameter("require_time");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(install);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		softwareReserve.setRequireTime(calendar);

		softwareReserve.setUser(shareService.getUserDetail());
		int softwareId = 0;
		if(softwareReserve.getCode()!=null) {
			softwareId = Integer.valueOf(softwareReserve.getCode());//软件编号
		}
		//当前时间
		Calendar dates = Calendar.getInstance();
		if(softwareId>0){
			Software s=softwareDAO.findSoftwareByPrimaryKey(Integer.valueOf(softwareId));//软件
			if(s!=null){
				softwareReserve.setCode(s.getCode());
				softwareReserve.setName(s.getName());
			}
		}else{
			softwareReserve.setCode("未搜索到软件,无编号");//编号
		}
		softwareReserve.setState(2);
		softwareReserve.setCreateTime(dates);
		softwareReserve = softwareService.saveSoftwareReserve(softwareReserve);//保存申请单
		//发送消息
		User user=shareService.getUserDetail();
		Message message=new Message();
		message.setSendUser(shareService.getUserDetail().getCname());//发给预约人
		message.setTitle("软件安装申请审核");
		message.setMessageState(0);//设置未读
		message.setCreateTime(Calendar.getInstance());
		message.setTage(2);
		//系主任
		if(user.getSchoolAcademy() != null){
			//List<User> deans = shareService.findDeansByAcademyNumber(user.getSchoolAcademy());
			List<User> deans = shareService.findAllDepartmentHead(user);
			if(deans != null){
				for(User u:deans){
					message.setContent("软件安装申请已提交，请审核 <a href='../SoftwareReservation/checkButton?id="+softwareReserve.getId()+"&tage=0&state="+softwareReserve.getState()+"&page=1'>审核</a>");//消息内容
					shareService.sendMsg(u, message);
				}
			}
		}
		//给自己
		message.setTitle("软件安装申请审核");
		message.setContent("教学软件申请，您申请"+softwareReserve.getName()+" 教学软件成功，请等待审核 <a href='../SoftwareReservation/checkButton?id="+softwareReserve.getId()+"&tage=0&state="+softwareReserve.getState()+"&page=1'>点击查看</a>");//消息内容
		message.setTage(0);
		shareService.sendMsg(shareService.getUserDetail(), message);
		return "redirect:/labRoom/SoftwareReserveList?page=1&tage=0&isaudit=2";
	}


	/****************************************************************************
	 * @Description： 软件安装申请修改
	 * @author： 张德冰
	 * @date: 2018-12-28
	 ****************************************************************************/
	@RequestMapping("/SoftwareReservationChange")
	public ModelAndView SoftwareReservationChange(@RequestParam Integer id,Integer tage,Integer page) throws Exception{
		ModelAndView mav = new ModelAndView();

		SoftwareReserve s = softwareReserveDAO.findSoftwareReserveById(id);
		mav.addObject("softwareReserve",s);//新建申请单对象
		//附件分为两类（ 1 申请说明  2安装说明 ）
		List<CommonDocument> commonDocuments1=commonDocumentService.findCommonDocumentsBySoftwareReserve(id,1);
		List<CommonDocument> commonDocuments2=commonDocumentService.findCommonDocumentsBySoftwareReserve(id,2);
		mav.addObject("commonDocuments1", commonDocuments1);
		mav.addObject("commonDocuments2", commonDocuments2);
		//时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String createTime = sdf.format(s.getCreateTime().getTime());
		mav.addObject("createTime",createTime);
		String requireTime = sdf.format(s.getRequireTime().getTime());
		mav.addObject("requireTime",requireTime);
		//查找可以预约的软件，暂时不做
		mav.addObject("softwareList",systemService.loadSoftwares());
		mav.addObject("termList",systemService.loadSchoolTerms());//返回学期
		mav.addObject("labList",systemService.loadLabRooms());//返回实验室
		mav.addObject("user",shareService.getUser());//返回用户
		mav.addObject("courses",shareService.getMyCourse());//获取用户的课程列表
		mav.setViewName("lab/software/SoftwareReservationChange.jsp");
		return mav;
	}

	/****************************************************************************
	 * @Description： 软件安装申请修改-保存（不用发消息）
	 * @author： 张德冰
	 * @date: 2018-12-29
	 ****************************************************************************/
	@Transactional
	@RequestMapping("/saveSoftwareReservationChange")
	public String  saveSoftwareReservationChange(@ModelAttribute SoftwareReserve softwareReserve, HttpServletRequest request) throws Exception{
		// 安装时间
		String install = request.getParameter("require_time");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(install);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		softwareReserve.setRequireTime(calendar);

		softwareReserve.setUser(shareService.getUserDetail());
		int softwareId=Integer.valueOf(softwareReserve.getCode());//软件编号
		if(softwareId>0){
			Software s=softwareDAO.findSoftwareByPrimaryKey(Integer.valueOf(softwareId));//软件
			if(s!=null){
				softwareReserve.setCode(s.getCode());
				softwareReserve.setName(s.getName());
			}
		}else{
			softwareReserve.setCode("未搜索到软件,无编号");//编号
		}
		softwareService.saveSoftwareReserve(softwareReserve);//保存申请单
		return "redirect:/labRoom/SoftwareReserveList?page=1&tage=0&isaudit=2";
	}

	/****************************************************************************
	 * @Description： 软件安装申请表上传附件
	 * @author： 张德冰
	 * @date: 2018-12-28
	 ****************************************************************************/
	@RequestMapping("/softwareDocumentUpload")
	public @ResponseBody String acceptanceDocumentUpload(HttpServletRequest request, HttpServletResponse response,
														 BindException errors,Integer id,int flag) throws Exception {
		systemService.softwareReserveUpload(request, response,id,1,flag);
		return "ok";
	}
}