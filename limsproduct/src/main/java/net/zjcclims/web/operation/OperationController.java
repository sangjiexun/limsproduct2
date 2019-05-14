package net.zjcclims.web.operation;

import api.net.gvsunlims.constant.ConstantInterface;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;
import net.gvsun.lims.dto.assets.MaterialKindDTO;
import net.gvsun.lims.service.assets.MaterialService;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.device.LabRoomDeviceService;
import net.zjcclims.service.device.SchoolDeviceService;
import net.zjcclims.service.lab.LabCenterService;
import net.zjcclims.service.lab.LabRoomAdminService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.message.MessageService;
import net.zjcclims.service.operation.OperationService;
import net.zjcclims.service.software.SoftwareService;
import net.zjcclims.service.system.SystemLogService;
import net.zjcclims.service.system.SystemService;
import net.zjcclims.service.timetable.OuterApplicationService;
import net.zjcclims.service.timetable.SchoolCourseInfoService;
import net.zjcclims.util.HttpClientUtil;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.BindException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller("OperationController")
@SessionAttributes("selected_academy")
@RequestMapping("/operation")
public class OperationController<JsonResult> {

	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register // static // property // editors.
		binder.registerCustomEditor(java.util.Calendar.class,new org.skyway.spring.util.databinding.CustomCalendarEditor());
		binder.registerCustomEditor(byte[].class,new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
		binder.registerCustomEditor(java.math.BigDecimal.class,new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
		binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Integer.class, true));
		binder.registerCustomEditor(java.util.Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
		binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
		binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Long.class, true));
		binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Double.class, true));
	}
	
	@Autowired private ShareService shareService;
	@Autowired private SystemService systemService;
	@Autowired private LabRoomService labRoomService;
	@Autowired private LabCenterService labCenterService;
	@Autowired private OperationService operationService;
	@Autowired private SystemLogService systemLogService;
	@Autowired private SchoolCourseInfoService schoolCourseInfoService;
	@Autowired private LabRoomDeviceService labRoomDeviceService;
	@Autowired private SoftwareService softwareService;
	@Autowired private OuterApplicationService outerApplicationService;

	@Autowired private MessageDAO messageDAO;
	@Autowired private CommonDocumentDAO commonDocumentDAO;
	@Autowired private OperationItemDAO operationItemDAO;
	@Autowired private AssetCabinetWarehouseAccessRecordDAO assetCabinetWarehouseAccessRecordDAO;
	@Autowired private OperationOutlineDAO operationOutlineDAO;
	@Autowired private SoftwareDAO softwareDAO;
	@Autowired private SoftwareItemRelatedDAO softwareItemRelatedDAO;
	@Autowired private LabRoomAdminService labRoomAdminService;
	@Autowired private OperItemAuditDAO operItemAuditDAO;
	@Autowired private MessageService messageService;
	@Autowired private SchoolDeviceService schoolDeviceService;
	@Autowired
	PConfig pConfig;
	@Autowired
	private OperationOutlineCourseDAO operationOutlineCourseDAO;
	@Autowired
	private CDictionaryDAO cDictionaryDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private SchoolCourseInfoDAO schoolCourseInfoDAO;
	@Autowired
	private SchoolTermDAO schoolTermDAO;
	@Autowired
	private SchoolMajorDAO schoolMajorDAO;
	@Autowired
	private AuthorityDAO authorityDAO;
	@Autowired
	private LabRoomDeviceDAO labRoomDeviceDAO;
	@Autowired
	private MaterialService materialService;
	@Autowired
	private AssetDAO assetDAO;
	/**
	 * 实验项目列表
	 * @author hly
	 * 2015.07.29
	 * 贺子龙修改于2015-09-02
	 */
	@RequestMapping("/listOperationItem")
	public ModelAndView listOperationItem(HttpServletRequest request,@RequestParam int currpage, int status, int orderBy, @ModelAttribute OperationItem operationItem, @ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView();
		SchoolCourseInfo schoolCourseInfo = new SchoolCourseInfo();
		User user = new User();
		User currUser = shareService.getUserDetail();  //当前登录人
		if (status==5) {
			if (!shareService.checkAuthority(currUser.getUsername(), "EXCENTERDIRECTOR")) {
				operationItem.setUserByLpCheckUser(currUser);//显示当前登录人需要审核的实验项目
			}
		}
		mav.addObject("schoolAcademy", shareService.findSchoolAcademyByPrimaryKey(acno));
		switch (status)
		{
		    case 1:
		    	operationItem.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "1"));
			    break;
		    case 2:
		    	operationItem.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "2"));
		    	break;
		    case 3:
		    	operationItem.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "3"));
		    	break;
		    case 4:
		    	operationItem.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "4"));
		    	break;
		    case 5://我的审核
		    	operationItem.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "2"));
		    	break;
		}
		
		int pageSize = CommonConstantInterface.INT_PAGESIZE;
		int totalRecords = operationService.findAllOperationItemExceptDraft(operationItem, acno);
		mav.addObject("listOperationItem", operationService.findAllOperationItemExceptDraft(currpage, pageSize, operationItem, acno, orderBy));
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("status", status);
		mav.addObject("orderBy", orderBy);
		mav.addObject("users",operationService.getsome());
		mav.addObject("schoolTerms", shareService.findAllSchoolTerm());  //所有学期
		mav.addObject("schoolCourseInfos",operationService.getCourse(acno));
		mav.addObject("currUser", currUser);  //当前登录用户
		mav.addObject("operationItem1", new OperationItem());  //用于设置项目编号
		mav.addObject("draft", shareService.getCDictionaryByCategory("status_operation_item_check", "1"));  // 草稿
		mav.addObject("toCheck", shareService.getCDictionaryByCategory("status_operation_item_check", "2"));  // 审核中
		mav.addObject("checkYes", shareService.getCDictionaryByCategory("status_operation_item_check", "3"));  // 审核通过
		mav.addObject("checkNo", shareService.getCDictionaryByCategory("status_operation_item_check", "4"));  //审核拒绝
		//获取当前学期
		SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		mav.addObject("schoolTerm", schoolTerm);
		//决定升序还是降序
		boolean asc=true;
		if (orderBy<10) {
			asc=false;
		}
		mav.addObject("asc", asc);
		mav.addObject("page", currpage);
		mav.setViewName("operation/listOperationItem.jsp");
		return mav;
	}
	
	/**
	 * 教师个人的实验项目列表
	 * @author hly
	 * 2015.08.07
	 */
	@RequestMapping("/listMyOperationItem")
	public ModelAndView listMyOperationItem(HttpServletRequest request,@RequestParam int currpage, int status, int orderBy,@ModelAttribute OperationItem operationItem, @ModelAttribute("selected_academy") String acno,String schoolMajorsa,String courseNumber){
		ModelAndView mav = new ModelAndView();
		//当前登录人
		User nowUser = shareService.getUserDetail();
		//实训部主任可以看到全部
		Set<Authority> aus=shareService.getUser().getAuthorities();
		int flag=0;
		for(Authority a:aus){
			if(a.getId()==3){
				flag=1;break;
			}
		}
		/*if(flag==1){
		}else{
			operationItem.setUserByLpCreateUser(shareService.getUserDetail());  //当前登录人
		}*/
		if(status!=6){
		}else{
			operationItem.setUserByLpCreateUser(shareService.getUserDetail());  //当前登录人
		}
		
		User user = new User();
		user.setUserRole("1");  //教师
		user.setSchoolAcademy(shareService.findSchoolAcademyByPrimaryKey(acno));  //显示本中心下的教师

		//status==6:我的项目，status==7：新增课程库
		switch (status)
		{
		    case 1:
		    	operationItem.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "1"));
			    break;
		    case 2:
		    	operationItem.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "2"));
		    	break;
		    case 3:
		    	operationItem.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "3"));
		    	break;
		    case 4:
		    	operationItem.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "4"));
		    	break;
		}

		int pageSize = CommonConstantInterface.INT_PAGESIZE;
		int totalRecords = operationService.findAllOperationItemByQueryCount(request,operationItem, acno);
		//添加自己审核的项目
		List<OperationItem> listOperationItem=operationService.findAllOperationItemByQuery(request,currpage, pageSize, operationItem, acno, 0);
		mav.addObject("listOperationItem", listOperationItem);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		int thereIsAHeader = 0;//0:审核权限没有设置；1：正常；2：审核权限没有对应审核人

		// 学院
		String academyNum = "";
		if(acno!=null && !acno.equals("-1")) {
			academyNum = acno;
		}else if(nowUser.getSchoolAcademy()!=null && nowUser.getSchoolAcademy().getAcademyNumber()!=null) {
			academyNum = shareService.getUserDetail().getSchoolAcademy().getAcademyNumber();
		}
		// 审核人备选名单集合
		List<User> auditorList = new ArrayList<>();
		// 从权限表中查找审核权限
		Map<String, String> params = new HashMap<>();
		params.put("businessUid", "");
		params.put("businessType", pConfig.PROJECT_NAME + "OperationItem");
		String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessAuditConfigs", params);
		JSONObject jsonObject = JSON.parseObject(s);
		Map auditConfigs = JSON.parseObject(jsonObject.getString("data"), Map.class);
		String name = "";
		if (auditConfigs != null && auditConfigs.size() != 0) {
			for (int i = 0; i < auditConfigs.size(); i++) {
				// 获取权限
				String auth = auditConfigs.get(i + 1).toString().split(":")[0];
				// 获取权限对应人员
				//List<User> auditors = operationService.getUserByQuery(academyNum, auth);
				List<User> auditors = new ArrayList<>();
				//如果是院级权限，则加学院查询条件；
				//教研室主任、实验室管理员、实验中心主任、系主任、助教、设备管理员、教师
				if(auth.equals("DEPARTMENTHEADER") || auth.equals("LABMANAGER") || auth.equals("EXCENTERDIRECTOR")
						|| auth.equals("CFO") || auth.equals("ASSISTANT") || auth.equals("EQUIPMENTADMIN")
						|| auth.equals("TEACHER")) {
					//浙江建设配置项，实验实训部实验中心主任可以审查全部课题
				    if(pConfig.PROJECT_NAME=="zjcclims"&&auth.equals("EXCENTERDIRECTOR")){
						auditors = shareService.findUsersByQuery(auth, "1036");
					}else{
						auditors = shareService.findUsersByQuery(auth, acno);
					}
				}else {
					auditors = shareService.findUsersByAuthorityName(auth);
				}
				auditorList.addAll(auditors);
				String authname = authorityDAO.findAuthorityByAuthorityName(auth).iterator().next().getCname();
				if(auditors.size() == 0){
					name += " "+authname + " ";
				}
			}
			thereIsAHeader = 1;
			mav.addObject("authname", name);
		}
		if(auditorList!=null && auditorList.size()>0 && name.equals("")) {
			mav.addObject("auditorList", auditorList);
		}else {
			thereIsAHeader = 2;
		}

		mav.addObject("courseNumber", courseNumber);
		mav.addObject("schoolMajorsa", schoolMajorsa);
		mav.addObject("thereIsAHeader", thereIsAHeader);
		//status==6:我的项目，status==7：新增课程库
		mav.addObject("status", status);
		mav.addObject("orderBy", orderBy);
//		mav.addObject("schoolCourseInfos",operationService.getCourse(acno,shareService.getUser().getUsername()));//课程数据
//		SchoolCourseInfo schoolCourseInfo = new SchoolCourseInfo();
//		mav.addObject("schoolCourseInfo", schoolCourseInfoService.getCourseInfoByQuery(schoolCourseInfo, -1, 1, -1));
		//专业数据
		mav.addObject("majors", systemService.getAllSchoolMajor(1, -1));
		// 所有软件
        List<Software> listSoftware = softwareService.findAllSoftwareByQuery(1, -1, null);
        mav.addObject("listSoftware", listSoftware);
		mav.addObject("draft", shareService.getCDictionaryByCategory("status_operation_item_check", "1"));  // 草稿
//		mav.addObject("toCheck", shareService.getCDictionaryByCategory("status_operation_item_check", "2"));  // 审核中
//		mav.addObject("checkYes", shareService.getCDictionaryByCategory("status_operation_item_check", "3"));  // 审核通过
		mav.addObject("checkNo", shareService.getCDictionaryByCategory("status_operation_item_check", "4"));  //审核拒绝
//		mav.addObject("schoolTerms", shareService.findAllSchoolTerm());  //所有学期
		//获取当前学期
//		SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
//		mav.addObject("schoolTerm", schoolTerm);
		mav.addObject("nowUser",nowUser);
		mav.setViewName("operation/listMyOperationItem.jsp");
		//决定升序还是降序
		boolean asc=true;
		if (orderBy<10) {
			asc=false;
		}
		mav.addObject("asc", asc);
		mav.addObject("page", currpage);
		
/*		String ip = shareService.getIpAddr(request);
		//保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
		//tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id 0-查看 -1--新建
		//action:  0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核查看 6 保存 7 审核编辑后保存 8 导入 9 审核结果
		systemLogService.saveOperationItemLog(ip, 0, 2, 0);
		*/
		mav.addObject("isMine", 1);


		Map<Integer, String> auditShow = new HashMap<>();
		// 获取所有审核状态
		for (OperationItem oi : listOperationItem) {
			Map<String, String> allAuditStateParams = new HashMap<>();
			String businessType = "OperationItem";
			allAuditStateParams.put("businessType", pConfig.PROJECT_NAME + businessType);
			allAuditStateParams.put("businessAppUid", oi.getId().toString());
			allAuditStateParams.put("businessUid", "-1");
			String allAuditStateStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessLevelStatus", allAuditStateParams);
			JSONObject allAuditStateJSON = JSONObject.parseObject(allAuditStateStr);
			String htmlStr = "";
			if (!"fail".equals(allAuditStateJSON.getString("status"))) {
				JSONArray allAuditStateJSONArray = allAuditStateJSON.getJSONArray("data");
				if (allAuditStateJSONArray != null && allAuditStateJSONArray.size() != 0) {
					for (int j = 0; j < allAuditStateJSONArray.size(); j++) {
						JSONObject o = allAuditStateJSONArray.getJSONObject(j);
						User auditUser = null;
						if (o.getString("auditUser") != null) {
							htmlStr += "<span style='color: black";
							auditUser = userDAO.findUserByUsername(o.getString("auditUser"));
						} else {
							htmlStr += "<span style='color: gray";
						}
						htmlStr += "'>";
						String authCName = authorityDAO.findAuthorityByAuthorityName(o.getString("authName")).iterator().next().getCname();
						htmlStr += authCName + " ";
						htmlStr += auditUser == null ? "" : auditUser.getCname() + " ";
						htmlStr += o.getString("result");
						htmlStr += "</span><br>";
					}
				}
			}
			auditShow.put(oi.getId(), htmlStr);
			mav.addObject("auditShow", auditShow);
		}
		mav.addObject("users", shareService.findUsersByQuery("TEACHER", acno));
		return mav;
	}
	/************************************************************************
	 *@Description:课题审核列表
	 *@Author:孙虎
	 *@Date:2018/5/30
	 ************************************************************************/
	@RequestMapping("/listMyOperationItemAuditing")
	public ModelAndView listMyOperationItemAuditing(@ModelAttribute OperationItem operationItem, @ModelAttribute("selected_academy") String acno,
													@RequestParam int currpage){
		ModelAndView mav = new ModelAndView();
		int pageSize = ConstantInterface.PAGE_SIZE;
		//获取当前用户
		User user = shareService.getUser();
		mav.addObject("schoolTerms", shareService.findAllSchoolTerm());  //所有学期
		//获取当前学期
		SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		mav.addObject("schoolTerm", schoolTerm);
		mav.addObject("schoolCourseInfos",operationService.getCourse(acno,user.getUsername()));//课程数据
//		SchoolCourseInfo schoolCourseInfo = new SchoolCourseInfo();
//		mav.addObject("schoolCourseInfo", schoolCourseInfoService.getCourseInfoByQuery(schoolCourseInfo, -1, 1, -1));
		List<OperationItem> listOperationItem = operationService.findAllOperItemAuditBy2(operationItem,user.getSchoolAcademy().getAcademyNumber(),currpage,pageSize);
		mav.addObject("listOperationItem",listOperationItem);
		int totalRecords = operationService.findAllOperItemAuditBy2(operationItem,user.getSchoolAcademy().getAcademyNumber(),1,-1).size();
		mav.addObject("pageModel",shareService.getPage(currpage, pageSize, totalRecords));

		// 获取当前审核人
		if(listOperationItem.size() > 0) {
			Map<Integer, String> idAndAuth = new HashMap<>();
			Map<Integer, Integer> idAndLevel = new HashMap<>();
			Map<String, String> paramsGetCurr = new HashMap<>();
			String businessType = "OperationItem";
			paramsGetCurr.put("businessType", pConfig.PROJECT_NAME + businessType);
			StringBuilder sb = new StringBuilder();
			for (OperationItem oi : listOperationItem) {
				sb.append(oi.getId().toString()).append(",");
			}
			paramsGetCurr.put("businessAppUid", sb.toString().substring(0, sb.length() - 1));
			String getCurrStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", paramsGetCurr);
			System.out.println(getCurrStr);
			JSONObject getCurrJsonObject = JSONObject.parseObject(getCurrStr);
			if ("success".equals(getCurrJsonObject.getString("status"))) {
				JSONArray getCurrJsonArray = getCurrJsonObject.getJSONArray("data");
				if (getCurrJsonArray != null && getCurrJsonArray.size() != 0) {
					for (int i = 0; i < getCurrJsonArray.size(); i++) {
						JSONObject temp = getCurrJsonArray.getJSONObject(i);
						String authName = "ROLE_" + temp.getString("result");
						Integer opId = temp.getIntValue("businessAppId");
						Integer level = temp.getIntValue("level");
						idAndAuth.put(opId, authName);
						idAndLevel.put(opId, level);
					}
				}
			}
			mav.addObject("idAndAuth", idAndAuth);
			mav.addObject("idAndLevel", idAndLevel);

			Map<Integer, String> auditShow = new HashMap<>();
			// 获取所有审核状态
			for (OperationItem oi : listOperationItem) {
				Map<String, String> allAuditStateParams = new HashMap<>();
				allAuditStateParams.put("businessType", pConfig.PROJECT_NAME + businessType);
				allAuditStateParams.put("businessAppUid", oi.getId().toString());
				allAuditStateParams.put("businessUid", "-1");
				String allAuditStateStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessLevelStatus", allAuditStateParams);
				JSONObject allAuditStateJSON = JSONObject.parseObject(allAuditStateStr);
				String htmlStr = "";
				if (!"fail".equals(allAuditStateJSON.getString("status"))) {
					JSONArray allAuditStateJSONArray = allAuditStateJSON.getJSONArray("data");
					if (allAuditStateJSONArray != null && allAuditStateJSONArray.size() != 0) {
						for (int j = 0; j < allAuditStateJSONArray.size(); j++) {
							JSONObject o = allAuditStateJSONArray.getJSONObject(j);
							User auditUser = null;
							if (o.getString("auditUser") != null) {
								htmlStr += "<span style='color: black";
								auditUser = userDAO.findUserByUsername(o.getString("auditUser"));
							} else {
								htmlStr += "<span style='color: gray";
							}
							htmlStr += "'>";
							String authCName = authorityDAO.findAuthorityByAuthorityName(o.getString("authName")).iterator().next().getCname();
							htmlStr += authCName + " ";
							htmlStr += auditUser == null ? "" : auditUser.getCname() + " ";
							htmlStr += o.getString("result");
							htmlStr += "</span><br>";
						}
					}
				}
				auditShow.put(oi.getId(), htmlStr);
				mav.addObject("auditShow", auditShow);
			}
		}

		mav.setViewName("operation/listMyOperationItemAudit.jsp");
		return mav;
	}
	/************************************************************************
	 *@Description:课题审核及结果展示
	 *@Author:孙虎
	 *@Date:2018/5/30
	 ************************************************************************/
	@RequestMapping("/operationItemAndAudit")
	public ModelAndView listMyOperationItemAuditing(HttpServletRequest request,@RequestParam Integer oId, @ModelAttribute("selected_academy") String acno,Integer flag) {
		ModelAndView mav = new ModelAndView();
		OperationItem operationItem = operationItemDAO.findOperationItemById(oId);
		//获取当前审核情况
		List<OperItemAudit> operItemAuditList = operationService.findAllOperItemAuditByOId(operationItem);
		mav.addObject("count",operItemAuditList.size());
		User user = shareService.getUser();
		//所选设备涉及的实训室管理员
		List<User> labManagers = new ArrayList<>();
		List<OperationItemDevice> operationItemDevices = new ArrayList<>(operationItem.getOperationItemDevices());
		if (operationItemDevices.size()!=0){
			for (OperationItemDevice operationItemDevice:operationItemDevices){
				if(operationItemDevice.getSchoolDevice()!=null&&operationItemDevice.getSchoolDevice().getLabRoomDevices()!=null
						&&operationItemDevice.getSchoolDevice().getLabRoomDevices().size()!=0) {
					LabRoom labRoom = new ArrayList<>(operationItemDevice.getSchoolDevice().getLabRoomDevices()).get(0).getLabRoom();
					List<LabRoomAdmin> labRoomAdmins = new ArrayList<>(labRoom.getLabRoomAdmins());
					for (LabRoomAdmin l : labRoomAdmins) {
						labManagers.add(l.getUser());
					}
				}
			}
		}
		mav.addObject("labManagers",labManagers);
		//TODO 中心主任在optionItem中有
		//获取中心主任
		List<User> labCenterManager = shareService.findAuthByAuthNameAndAcademy("EXCENTERDIRECTOR",operationItem.getUserByLpCreateUser().getSchoolAcademy().getAcademyNumber());
		mav.addObject("labCenterManager",labCenterManager);
		//判断当前登录人是否可以审核
		Boolean canAudit = false;

		// 获取全部
		List<Object[]> auditInfo = new ArrayList<>();
		Map<String, String> params = new HashMap<>();
		String businessType = "OperationItem";
		params.put("businessType", pConfig.PROJECT_NAME + businessType);
		params.put("businessAppUid", String.valueOf(oId));
		params.put("businessUid", "-1");
		String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessLevelStatus", params);
		JSONObject jsonObject = JSONObject.parseObject(s);
		if("success".equals(jsonObject.getString("status"))){
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			if(jsonArray != null && jsonArray.size() != 0){
				for (int i = 0; i < jsonArray.size(); i++) {
					// 当前项目审核信息
					JSONObject temp = jsonArray.getJSONObject(i);
					Object[] o = new Object[6];
					// 审核等级
					Integer level = temp.getIntValue("level");
					o[0] = level;
					// 审核权限
					String authName = temp.getString("authName");
					Set<Authority> authorities = authorityDAO.findAuthorityByAuthorityName(authName);
					if(authorities.size() != 0){
						authName = authorities.iterator().next().getCname();
					}
					o[1] = authName;
					// 审核结果
					String result = temp.getString("result");
					o[2] = result;
					// 审核时间
					String createTime = temp.getString("createTime");
					o[3] = createTime;
					// 审核人
					String auditUsername = temp.getString("auditUser");
					User auditUser = userDAO.findUserByPrimaryKey(auditUsername);
					o[4] = auditUser;
					// 审核备注
					String info = temp.getString("info");
					o[5] = info;
					auditInfo.add(o);
				}
			}
		}
		mav.addObject("auditInfo", auditInfo);

		// 获取当前审核人
		Map<String, String> paramsGetCurr = new HashMap<>();
		paramsGetCurr.put("businessType", pConfig.PROJECT_NAME + businessType);
		paramsGetCurr.put("businessAppUid", oId.toString());
		String getCurrStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", paramsGetCurr);
		JSONObject getCurrJSON = JSONObject.parseObject(getCurrStr);
		String authName = "";
		if("success".equals(getCurrJSON.getString("status"))){
			JSONArray getCurrArray = getCurrJSON.getJSONArray("data");
			if(getCurrArray != null && getCurrArray.size() != 0){
				Integer level = getCurrArray.getJSONObject(0).getIntValue("level");
				// 这个项目的当前审核权限
				authName = getCurrArray.getJSONObject(0).getString("result");
				Set<Authority> authorities = authorityDAO.findAuthorityByAuthorityName(authName);
				if(authorities.size() > 0) {
					String authCname = authorities.iterator().next().getCname();
					mav.addObject("authCname", authCname);
				}
				mav.addObject("level", level);
				// 如果进入的审核等级为这个项目的当前审核等级并且审核权限相同，则可以审核
				if(level.equals(flag) && request.getSession().getAttribute("selected_role").toString().substring(5).equals(authName)){
					canAudit = true;
				}
			}
		}

		// 下一级审核人列表
		List<User> auditUsers = new ArrayList<>();
		List<User> auditUser = new ArrayList<>();
		//如果是院级权限，则加学院查询条件；
		//教研室主任、实验室管理员、实验中心主任、系主任、助教、设备管理员、教师
		if(authName.equals("DEPARTMENTHEADER") || authName.equals("LABMANAGER") || authName.equals("EXCENTERDIRECTOR")
				|| authName.equals("CFO") || authName.equals("ASSISTANT") || authName.equals("EQUIPMENTADMIN")
				|| authName.equals("TEACHER")) {
			auditUser = shareService.findUsersByQuery(authName, acno);
		}else {
			auditUser = shareService.findUsersByAuthorityName(authName);
		}
		// 根据权限筛选和获取不同的审核人列表
		switch(authName){
			// 其他
			default:
				auditUsers.addAll(auditUser);
		}
		mav.addObject("auditUsers", auditUsers);


		mav.addObject("canAudit",canAudit);
		mav.addObject("operItemAuditList",operItemAuditList);
		mav.addObject("oId",operationItem.getId());
		mav.addObject("flag",flag);
		mav.addObject("auditUsername",user.getUsername());
		mav.setViewName("exam.jsp");
		return  mav;
	}

	/**
	 * 保存审核
	 * @author hly
	 * 2015.08.07
	 */
	@RequestMapping("/saveOperationItemAudit")
	public String saveOperationItemAudit(HttpServletRequest request,Integer oId,String auditUser,String audit,String mem,Integer flag, @ModelAttribute("selected_academy") String acno){
		OperationItem operationItem = operationItemDAO.findOperationItemById(oId);
		Map<String, String> params = new HashMap<>();
		String businessType = "OperationItem";
		params.put("businessType", pConfig.PROJECT_NAME + businessType);
		params.put("businessAppUid", oId.toString());
		params.put("businessUid", "-1");
		params.put("result", audit.equals("1") ? "pass" : "fail");
		params.put("info", mem);
		params.put("username", auditUser);
		String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveBusinessLevelAudit", params);
		JSONObject jsonObject = JSONObject.parseObject(s);
		if(!"success".equals(jsonObject.getString("status"))){
			return "fail";
		}
		// 当前权限名称
		String authCName = authorityDAO.findAuthorityByAuthorityName(request.getSession().getAttribute("selected_role").toString().substring(5)).iterator().next().getCname();
		JSONObject result = jsonObject.getJSONObject("data");
		String title = pConfig.operationItemName + authCName + shareService.getUserDetail().getCname();
		if(result.containsKey(-1)){
			// 全部审核通过
			CDictionary cd =
				shareService.getCDictionaryByCategory("status_operation_item_check", "3");
			operationItem.setCDictionaryByLpStatusCheck(cd);
			title += "审核通过，全部审核已通过";
		}else if(result.containsKey(0)){
			// 审核拒绝
			CDictionary cd =
					shareService.getCDictionaryByCategory("status_operation_item_check", "4");
			operationItem.setCDictionaryByLpStatusCheck(cd);
			title += "审核拒绝，审核流程结束";
		}else{
			// 当前审核通过
			CDictionary cd =
					shareService.getCDictionaryByCategory("status_operation_item_check", "2");
			operationItem.setCDictionaryByLpStatusCheck(cd);
			title += "审核通过";
			// 给下一级审核人发消息
			// 获取当前审核权限
			Map<String, String> curParams = new HashMap<>();
			curParams.put("businessType", pConfig.PROJECT_NAME + businessType);
			curParams.put("businessAppUid", operationItem.getId().toString());
			String curStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", curParams);
			if("success".equals(JSONObject.parseObject(curStr).getString("status"))){
				String nextAuthName = JSONObject.parseObject(curStr).getJSONArray("data").getJSONObject(0).getString("result");
				// 获取权限所对应的用户
				//List<User> auditUsers = shareService.findUsersByAuthorityName(nextAuthName);
				List<User> auditUsers = new ArrayList<>();
				//如果是院级权限，则加学院查询条件；
				//教研室主任、实验室管理员、实验中心主任、系主任、助教、设备管理员、教师
				if(nextAuthName.equals("DEPARTMENTHEADER") || nextAuthName.equals("LABMANAGER") || nextAuthName.equals("EXCENTERDIRECTOR")
						|| nextAuthName.equals("CFO") || nextAuthName.equals("ASSISTANT") || nextAuthName.equals("EQUIPMENTADMIN")
						|| nextAuthName.equals("TEACHER")) {
					auditUsers = shareService.findUsersByQuery(nextAuthName, acno);
				}else {
					auditUsers = shareService.findUsersByAuthorityName(nextAuthName);
				}
				// 给这些用户发送消息
				for(User u: auditUsers) {
					Calendar date = Calendar.getInstance();
					Message message = new Message();
					message.setSendUser(shareService.getUserDetail().getCname());
					message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
					message.setCond(0);
					message.setTage(2);
					message.setTitle(pConfig.operationItemName + "审核");
					message.setContent("<a onclick='changeMessage(this)' href='../operation/operationItemAndAudit?oId=" + operationItem.getId() + "&cid=-1&flag=" + (flag+1) + "'>审核</a>");
					message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
					message.setCreateTime(date);
					message.setUsername(u.getUsername());
					message = messageDAO.store(message);
				}
				messageDAO.flush();
			}
		}
		// 给申请人发消息
		Message message = new Message();
		message.setSendUser(shareService.getUserDetail().getCname());
		message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
		message.setCond(0);
		message.setTage(1);
		message.setTitle(title);
		message.setContent("<a onclick='changeMessage(this)' href='../operation/operationItemAndAudit?oId=" + operationItem.getId() + "&cid=-1&flag=0'>查看</a>");
		message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
		message.setCreateTime(Calendar.getInstance());
		message.setUsername(operationItem.getUserByLpCreateUser().getUsername());
		message = messageDAO.store(message);
		operationItem.setUserByLpCheckUser(shareService.getUserDetail());
		operationItemDAO.store(operationItem);
		operationItemDAO.flush();
//		mav.setViewName("redirect:/personal/messageList?currpage=1&tage=1");
		return "success";
	}
	
	/**
	 * 可以导入的实验项目列表
	 * @author hly
	 * 2015.08.07
	 */
	@RequestMapping("/listOperationItemImport")
	public ModelAndView listOperationItemImport(HttpServletRequest request,@RequestParam int currpage, int orderBy, @ModelAttribute OperationItem operationItem, @ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView();
		//operationItem.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "1"));
		
		int pageSize = CommonConstantInterface.INT_PAGESIZE;
		List<OperationItem> listOperationItem=operationService.findAllOperationItemByQueryImport(currpage, pageSize, operationItem, acno, orderBy);
		List<OperationItem> allOperationItem=operationService.findAllOperationItemByQueryImport(1, -1, operationItem, acno);
		int totalRecords = allOperationItem.size();
		
		mav.addObject("page", currpage);  
//		mav.addObject("cid", cid);  //实验中心id
		mav.addObject("orderBy", orderBy);  //实验中心id
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("schoolTerms", shareService.findAllSchoolTerm());  //所有学期
		mav.addObject("schoolCourseInfos",operationService.getCourse(acno));//课程数据
		mav.addObject("listOperationItem", listOperationItem);
		mav.addObject("allOperationItem", allOperationItem);
		mav.setViewName("operation/listOperationItemImport.jsp");
		//决定升序还是降序
		boolean asc=true;
		if (orderBy<10) {
			asc=false;
		}
		mav.addObject("asc", asc);
		
/*		String ip = shareService.getIpAddr(request);
		//保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
		//tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id 0-查看 -1--新建
		//action:  0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核查看 6 保存 7 审核编辑后保存 8 导入 9 审核结果
		systemLogService.saveOperationItemLog(ip, 2, 2, 0);*/
		return mav;
	}
	
	/**
	 * 新建实验项目
	 * @author hly
	 * 2015.07.29
	 */
	@RequestMapping("/newOperationItem")
	public ModelAndView newOperationItem(HttpServletRequest request,@ModelAttribute("selected_academy") String acno,int isMine,int flagId,String schoolMajorsa,String courseNumber){
		ModelAndView mav = new ModelAndView();
		LabRoom labRoom = new LabRoom();
		SchoolCourseInfo schoolCourseInfo = new SchoolCourseInfo();

		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
		if(academy!=null && academy.getAcademyNumber()!=null)
		{
			mav.addObject("schoolAcademy", academy);
			schoolCourseInfo.setAcademyNumber(academy.getAcademyNumber());
		}
//		mav.addObject("listItemMaterialRecord", operationService.getItemMaterialRecordByItem(itemId));
		mav.addObject("operationItem", new OperationItem());
		//mav.addObject("schoolTerms", shareService.findAllSchoolTerm());  //学期
//        mav.addObject("users", systemService.getAllUser(1, -1, user));  //教师数据
        //mav.addObject("labRooms", labRoomService.findAllLabRoomByQuery(1, -1, labRoom));  //实验室数据
        mav.addObject("schoolCourseInfos", schoolCourseInfoService.getCourseInfoByQuery(schoolCourseInfo, -1, 1, -1));  //课程数据
       // mav.addObject("subjects", systemService.getAllSystemSubject12(1, -1));  //学科数据
        mav.addObject("majors", systemService.getAllSchoolMajor(1, -1));  //专业数据
        //mav.addObject("users", systemService.getAllUser(1, -1, shareService.getUser()));  //教师数据
        //mav.addObject("users", systemService.getAllJPAUser(1, -1, shareService.getUser()));  //教师数据
        //mav.addObject("labProjectMain", shareService.getCDictionaryData("category_operation_item_main"));  //实验类别
        //mav.addObject("labProjectApp", shareService.getCDictionaryData("category_operation_item_app"));  //实验类型
       // mav.addObject("labProjectNature", shareService.getCDictionaryData("category_operation_item_nature"));  //实验性质
        //mav.addObject("labProjectStudent", shareService.getCDictionaryData("category_operation_item_student"));  //实验者类型
        //mav.addObject("labProjectChange", shareService.getCDictionaryData("status_operation_item_change"));  //变动状态
        //mav.addObject("labProjectPublic", shareService.getCDictionaryData("category_operation_item_public"));  //开放实验
        //mav.addObject("labProjectRewardLevel", shareService.getCDictionaryData("category_operation_item_reward_level"));  //获奖等级
       // mav.addObject("labProjectRequire", shareService.getCDictionaryData("category_operation_item_require"));  //实验要求
        mav.addObject("labProjectGuideBook", shareService.getCDictionaryData("category_operation_item_guide_book"));  //实验指导书
        mav.addObject("currSchoolTerm", shareService.getBelongsSchoolTerm(Calendar.getInstance()));  //当前学期
		mav.addObject("schoolMajorsa", schoolMajorsa);
		mav.addObject("courseNumber", courseNumber);
		mav.addObject("flagId", flagId);
        mav.addObject("isEdit", false);
        mav.addObject("isMine", isMine);
        //当前类型下的实验大纲
        mav.addObject("outlines", operationOutlineDAO.executeQuery("select o from OperationOutline o where 1=1"));
        // 所有软件
        List<Software> listSoftware = softwareService.findAllSoftwareByQuery(1, -1, null);
        mav.addObject("listSoftware", listSoftware);
		//status==7表示从课程库入口进入;6表示从实验项目管理我的项目入口进入
		mav.setViewName("operation/editOperationItem.jsp");
//		mav.setViewName("operation/newOperationItem.jsp");
		String ip = shareService.getIpAddr(request);
		//保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
		//tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id 0-查看 -1--新建
		//action:  0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核查看 6 保存 7 审核编辑后保存 8 导入 9 审核结果
		systemLogService.saveOperationItemLog(ip, 0, 0, -1);
		// 设备查询条件
		mav.addObject("schoolDevice", new SchoolDevice());
		return mav;
	}
	
	/**
	 * 编辑实验项目
	 * @author hly
	 * 2015.07.29
	 */
	@RequestMapping("/editOperationItem")
	public ModelAndView editOperationItem(HttpServletRequest request,@RequestParam int operationItemId, @ModelAttribute("selected_academy") String acno,int isMine,int flagId){
		ModelAndView mav = new ModelAndView();
		SchoolCourseInfo schoolCourseInfo = new SchoolCourseInfo();

		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
		if(academy!=null && academy.getAcademyNumber()!=null)
		{
			mav.addObject("schoolAcademy", academy);
			schoolCourseInfo.setAcademyNumber(academy.getAcademyNumber());
		}

		//仪器设备新增
		OperationItem operationItem = operationService.findOperationItemByPrimaryKey(operationItemId);
		if(operationItem.getLpMajorFit() != null){
			//面向专业
			String majors=operationItem.getLpMajorFit();
			String[] majorArray=majors.split(",");
			List<SchoolMajor>  majorList=new ArrayList<SchoolMajor>();
			List<SchoolMajor> allMajorList = systemService.getAllSchoolMajor(1, -1);
			for (String string : majorArray) {
				SchoolMajor major = systemService.findSchoolMajorByNumber(string);
				if (major!=null) {
					majorList.add(major);
					allMajorList.remove(major);
				}
			}
			mav.addObject("majorList", majorList);	//已选专业
			mav.addObject("majors", allMajorList);  //所有专业（排除已选）
		}
		// 所有软件
		if(operationItem.getSoftwareItemRelateds() != null) {
			List<Software> softwares = new ArrayList<Software>();
			List<Software> listSoftware = softwareService.findAllSoftwareByQuery(1, -1, null);
			for(SoftwareItemRelated related : operationItem.getSoftwareItemRelateds()) {
				Software software = related.getSoftware();
				softwares.add(software);
				listSoftware.remove(software);
			}
			mav.addObject("softwares", softwares);
			mav.addObject("listSoftware", listSoftware);
		}
		
		operationItem.getCommonDocuments();
		mav.addObject("operationItem", operationItem);
		mav.addObject("listItemDevice", operationService.getItemDeviceByItem(operationItemId, null, 1, -1));
		
		mav.addObject("listItemMaterialRecord", operationService.getItemMaterialRecordByItem(operationItemId));//实验材料 贺子龙新增
//		mav.addObject("categoryMaterialRecordMain", shareService.getCDictionaryData("category_operation_item_material_record_main"));
		mav.addObject("operationItemMaterialRecord", new OperationItemMaterialRecord());
		
//        mav.addObject("schoolCourseInfos", schoolCourseInfoService.getCourseInfoByQuery(schoolCourseInfo, -1, 1, -1));  //课程数据
//        mav.addObject("courseInfo", operationItem.getSchoolCourseInfo());
//		mav.addObject("labCenters", labCenterService.findAllCenters());
        
//        mav.addObject("labProjectGuideBook", shareService.getCDictionaryData("category_operation_item_guide_book"));  //实验指导书
		//判断弹窗
        mav.addObject("flagId", flagId);
        mav.addObject("isEdit", true);
        mav.addObject("isMine", isMine);

		mav.setViewName("operation/editOperationItem.jsp");

		String ip = shareService.getIpAddr(request);
		//保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
		//tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id 0-查看 -1--新建
		//action:  0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核查看 6 保存 7 审核编辑后保存 8 导入 9 审核结果
//		systemLogService.saveOperationItemLog(ip, 0, 1, operationItemId);
		 //当前类型下的实验大纲
//        mav.addObject("outlines", operationOutlineDAO.executeQuery("select o from OperationOutline o where 1=1" +
//        		" and labCenter.schoolAcademy.academyNumber='"+ acno +"'", 0,-1));
		//找到该项目下的所有入库的物资
		List<AssetCabinetWarehouseAccessRecord> assetRecords = assetCabinetWarehouseAccessRecordDAO.executeQuery("select a from AssetCabinetWarehouseAccessRecord" +
				" a where 1=1 and status = 1 and asset.category = 0 " +  // 药品溶液
				"and (assetCabinetWarehouseAccess.type = 0 " +   // 公共
				"or assetCabinetWarehouseAccess.type != 0 and assetCabinetWarehouseAccess.operationItem.id="+operationItemId+")", 0, -1);  // 或对应项目
		mav.addObject("assetRecords", assetRecords);
		
		// 设备查询条件
		mav.addObject("schoolDevice", new SchoolDevice());
		return mav;
	}
	
	/**
	 * 保存实验项目
	 * @author hly
	 * 2015.07.29
	 */
	@RequestMapping("/saveOperationItem")
	public ModelAndView saveOperationItem(HttpServletRequest request,@ModelAttribute OperationItem operationItem,@RequestParam int toMyList, @RequestParam Integer flagId,@ModelAttribute("selected_academy") String acno){
		ModelAndView mav=new ModelAndView();
		operationItem.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "1"));  //草稿状态
		operationItem.setUserByLpCreateUser(shareService.getUserDetail());  //当前登录人
		operationItem.setCreatedAt(Calendar.getInstance());//设置为当前时间
		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
		Set<LabCenter> labCenters = academy.getLabCenters();
		for (LabCenter lc:labCenters){
			operationItem.setLabCenter(lc);
			break;
		}
		operationItem.setSchoolTerm(shareService.getBelongsSchoolTerm(Calendar.getInstance()));
		operationItem = operationService.saveOperationItem(operationItem);
			if (flagId == null) {
				flagId = 0;
			}
			if (flagId == 0) {
				mav.setViewName("redirect:/operation/editOperationItem?operationItemId=" + operationItem.getId() + "&isMine=" + toMyList + "&flagId=10");
			} else {
					if (toMyList == 1) {
						mav.setViewName("redirect:/operation/listMyOperationItem?currpage=1&status=7&orderBy=0");
					} else {
						mav.setViewName("redirect:/operation/listOperationItem?currpage=1&status=0&orderBy=9");
					}

			}

		// 保存项目-软件关系表
		String softwares = operationItem.getSoftware();
		if(softwares!=null && softwares!=""){
			String[] str= softwares.split(",");
			for (String string : str) {
				if(string!=null && string!=""){
					SoftwareItemRelated related = new SoftwareItemRelated();
					Software software = softwareDAO.findSoftwareByPrimaryKey(Integer.parseInt(string));
					if(software!=null){
						related.setSoftware(software);
						related.setOperationItem(operationItem);
						softwareItemRelatedDAO.store(related);
				    }
				}
			}
		}
		
		String ip = shareService.getIpAddr(request);
		//保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
		//tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id 0-查看 -1--新建
		//action:  0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核查看 6 保存 7 审核编辑后保存 8 导入 9 审核结果
		int tag=1;
		if (toMyList==1) {
			tag=0;
		}
		systemLogService.saveOperationItemLog(ip, tag, 6, operationItem.getId());
		
		String docment=request.getParameter("docment");
		  if(docment!=null && docment!=""){
			 String[] str= docment.split(",");
			 for (String string : str) {
				 if(string!=null && string!=""){
					CommonDocument dd= commonDocumentDAO.findCommonDocumentByPrimaryKey(Integer.parseInt(string));
				     if(dd!=null){ 
					dd.setOperationItem(operationItem);
				      commonDocumentDAO.store(dd);
				 }
				 }
			}
			  
		  }
		return mav;
	}
	
	/**
	 * 保存实验项目--审核时
	 * @author 贺子龙
	 * 2015-12-14
	 */
	@RequestMapping("/saveOperationItemWhileAudit")
	public String saveOperationItemWhileAudit(HttpServletRequest request,@ModelAttribute OperationItem operationItem,
											  int status){
		int operationItemId=operationItem.getId();
		OperationItem originalItem=operationService.findOperationItemByPrimaryKey(operationItemId);
		//保存页面上没有被编辑的字段，不然会被冲掉。
		operationItem.setCDictionaryByLpStatusCheck(originalItem.getCDictionaryByLpStatusCheck()); 
		operationItem.setUserByLpCreateUser(originalItem.getUserByLpCreateUser());
		operationItem.setCreatedAt(originalItem.getCreatedAt());
		operationItem.setUserByLpCheckUser(originalItem.getUserByLpCheckUser());
		operationItem.setLpCodeCustom(originalItem.getLpCodeCustom());
		operationItem.setLpCollege(originalItem.getLpCollege());
		operationItem.setLpPurposes(originalItem.getLpPurposes());
		operationItem.setLabCenter(originalItem.getLabCenter());
		operationItem = operationService.saveOperationItem(operationItem);
		
		String ip = shareService.getIpAddr(request);
		//保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
		//tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id 0-查看 -1--新建
		//action:  0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核查看 6 保存 7 审核编辑后保存 8 导入 9 审核结果
		systemLogService.saveOperationItemLog(ip, 1, 7, operationItemId);
		return "redirect:/operation/viewOperationItemLims/"+status+"/"+operationItemId;//flag=3是防止保存后跳转的页面“返回”报错
	}

	/**
	 * Description 课题审核后编辑保存{enough}
	 * @param request
	 * @param operationItem
	 * @param status
	 * @return
	 * @author 陈乐为 2018-12-29
	 */
	@RequestMapping("/saveItemWhileAudit")
	public String saveItemWhileAudit(HttpServletRequest request,@ModelAttribute OperationItem operationItem,
											  int status){
		int operationItemId=operationItem.getId();
		OperationItem originalItem=operationService.findOperationItemByPrimaryKey(operationItemId);
		//保存页面上没有被编辑的字段，不然会被冲掉。
		operationItem.setCDictionaryByLpStatusCheck(originalItem.getCDictionaryByLpStatusCheck());
		operationItem.setUserByLpCreateUser(originalItem.getUserByLpCreateUser());
		operationItem.setCreatedAt(originalItem.getCreatedAt());
		operationItem.setUserByLpCheckUser(originalItem.getUserByLpCheckUser());
		operationItem.setLpCodeCustom(originalItem.getLpCodeCustom());
		operationItem.setLpCollege(originalItem.getLpCollege());
		operationItem.setLpPurposes(originalItem.getLpPurposes());
		operationItem.setLabCenter(originalItem.getLabCenter());
		operationItem = operationService.saveOperationItem(operationItem);

		String ip = shareService.getIpAddr(request);
		//保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
		//tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id 0-查看 -1--新建
		//action:  0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核查看 6 保存 7 审核编辑后保存 8 导入 9 审核结果
		systemLogService.saveOperationItemLog(ip, 1, 7, operationItemId);
		return "redirect:/operation/viewOperationItem?status="+status+"&flag=3&operationItemId="+operationItemId;//flag=3是防止保存后跳转的页面“返回”报错
	}
	
	/**
	 * 保存实验项目2-跳转页面
	 * @author 贺子龙
	 * 2015-09-23 19:40:46
	 */
	@RequestMapping("/saveOperationItemAll")
	public String saveOperationItemAll(@RequestParam int isMine){
		if (isMine==1) {
			return "redirect:/operation/listMyOperationItem?currpage=1&status=0"+"&orderBy=0";
		}else {
			return "redirect:/operation/listOperationItem?currpage=1&status=0"+"&orderBy=9";
		}
	}
	
	/**
	 * 删除实验项目
	 * @author hly
	 * 2015.07.29
	 */
	@RequestMapping("/deleteOperationItem")
	public String deleteOperationItem(HttpServletRequest request,@RequestParam int operationItemId, int isMine, int status){
		String ip = shareService.getIpAddr(request);
		//保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
		//tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id 0-查看 -1--新建
		//action:  0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核查看 6 保存 7 审核编辑后保存 8 导入 9 审核结果
		int tag=1;
		if (isMine==1) {
			tag=0;
		}
		systemLogService.saveOperationItemLog(ip, tag, 3, operationItemId);
		operationService.deleteOperationItem(operationItemId);
		if (isMine==1) {
			return "redirect:/operation/listMyOperationItem?currpage=1&status="+status+"&orderBy=0";
		}else
		return "redirect:/operation/listOperationItem?currpage=1&status="+status+"&orderBy=0";
	}
	
	/**
	 * 提交实验项目，保存指定审核人
	 * @author hly
	 * 2015.08.06
	 */
	@RequestMapping("/submitOperationItem")
	public String submitOperationItem(HttpServletRequest request,@ModelAttribute OperationItem operationItem,int isMine, @ModelAttribute("selected_academy") String acno){
		OperationItem oItem=new OperationItem();
		oItem=operationService.submitOperationItem(operationItem, acno);
		//提交完成后向审核人发送消息
//		Message message= new Message();
//		Calendar date=Calendar.getInstance();
//		message.setSendUser(shareService.getUserDetail().getCname());
//		message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
//		message.setCond(0);
//		message.setTitle(CommonConstantInterface.STR_OPERATIONITEM_TITLE);
//		String content="申请成功，等待审核";
//		content+="<a onclick='changeMessage(this)' href='../operation/viewOperationItem?operationItemId=";
//		content+=oItem.getId();
//		content+="&&flag=1&status=2'>点击查看</a>";
////		message.setContent("申请成功，等待审核<a  href='/zjcclims/operation/viewOperationItem?operationItemId=44956&&flag=1&status=2'>点击查看</a>");
//		message.setContent(content);
//		message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
//		message.setCreateTime(date);
//		message.setUsername(oItem.getUserByLpCheckUser().getUsername());
//		message=messageDAO.store(message);
//		message.setTage(2);
		String ip = shareService.getIpAddr(request);
		//保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
		//tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id 0-查看 -1--新建
		//action:  0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核查看 6 保存 7 审核编辑后保存 8 导入 9 审核结果
		int tag=1;
		if (isMine==1) {
			tag=0;
		}
		systemLogService.saveOperationItemLog(ip, tag, 4, oItem.getId());
		if (isMine==0) {
			return "redirect:/operation/listOperationItem?currpage=1&status=2&orderBy=9";
		}else return "redirect:/operation/listMyOperationItem?currpage=1&status=2&orderBy=9";
	}
	
	/**
	 * 查看实验项目信息
	 * @author hly
	 * 2015.08.06
	 */
	@RequestMapping("/viewOperationItem")
	public ModelAndView viewOperationItem(@RequestParam int operationItemId,int flag,int status){
		ModelAndView mav = new ModelAndView();
		StringBuffer majorStr = new StringBuffer();
		OperationItem operationItem = operationService.findOperationItemByPrimaryKey(operationItemId);
		
		if(operationItem!=null&&operationItem.getLpMajorFit()!=null && !"".equals(operationItem.getLpMajorFit()))
		{
			String majorArr[] = operationItem.getLpMajorFit().split(",");
			
			for (String s : majorArr) 
			{
				if (systemService.findSchoolMajorByNumber(s)!=null) {
					majorStr.append(systemService.findSchoolMajorByNumber(s).getMajorName()+"["+s+"],");
				}
			}
			if(majorStr.length() > 0)
			{
				majorStr.deleteCharAt(majorStr.length()-1);  //去掉最后一个逗号
			}
		}
		mav.addObject("flag", flag);
		mav.addObject("status", status);
		//审核阶段
		List<OperItemAudit> opes=operationService.findAllOperaItemAuditByoperaItemId(operationItemId);
		int step=1;
		if(opes!=null){
		for(OperItemAudit ope:opes){
			if("一审通过".equals(ope.getResult())){
				step=2;
			}if("二审通过".equals(ope.getResult())){
				step=3;break;
			}
		}}
		mav.addObject("step", step);
		mav.addObject("operationItem", operationItem);
		mav.addObject("majorStr", majorStr);  //面向专业
		mav.addObject("status", status);
		//2015-09-24 10:10:45新增    仪器、材料
//		mav.addObject("listItemDevice", operationService.getItemDeviceByItem(operationItemId, null, 1, -1));
//		mav.addObject("listItemMaterialRecord", operationService.getItemMaterialRecordByItem(operationItemId));
		
		//审核人可以进行编辑
//        mav.addObject("labProjectMain", shareService.getCDictionaryData("category_operation_item_main"));  //实验类别
//        mav.addObject("labProjectApp", shareService.getCDictionaryData("category_operation_item_app"));  //实验类型
//        mav.addObject("labProjectNature", shareService.getCDictionaryData("category_operation_item_nature"));  //实验性质
//        mav.addObject("labProjectStudent", shareService.getCDictionaryData("category_operation_item_student"));  //实验者类型
//        mav.addObject("labProjectChange", shareService.getCDictionaryData("status_operation_item_change"));  //变动状态
//        mav.addObject("labProjectPublic", shareService.getCDictionaryData("category_operation_item_public"));  //开放实验
//        mav.addObject("labProjectRewardLevel", shareService.getCDictionaryData("category_operation_item_reward_level"));  //获奖等级
//        mav.addObject("labProjectRequire", shareService.getCDictionaryData("category_operation_item_require"));  //实验要求
//        mav.addObject("labProjectGuideBook", shareService.getCDictionaryData("category_operation_item_guide_book"));  //实验指导书
//        mav.addObject("schoolTerms", shareService.findAllSchoolTerm());  //学期
        mav.addObject("users", systemService.getAllUser(1, -1, shareService.getUser()));  //教师数据
//        LabRoom labRoom = new LabRoom();
//        mav.addObject("labRooms", labRoomService.findAllLabRoomByQuery(1, -1, labRoom));  //实验室数据
        SchoolCourseInfo schoolCourseInfo = new SchoolCourseInfo();
        mav.addObject("schoolCourseInfos", schoolCourseInfoService.getCourseInfoByQuery(schoolCourseInfo, -1, 1, -1));  //课程数据
        //添加材料
  		mav.addObject("operationItemMaterialRecord", new OperationItemMaterialRecord());
//  		mav.addObject("categoryMaterialRecordMain", shareService.getCDictionaryData("category_operation_item_material_record_main"));
		//专业数据
		mav.addObject("majors", systemService.getAllSchoolMajor(1, -1));
		// 所有软件
		List<Software> listSoftware = softwareService.findAllSoftwareByQuery(1, -1, null);
		mav.addObject("listSoftware", listSoftware);
  		//添加设备
  		if(operationItem!=null && operationItem.getLabRoom()!=null)
		{
			Set<SchoolDevice> allLabRoomDevice = schoolDeviceService.findAllSchoolDevice();

			List<SchoolDevice> existLabRoomDevice = new ArrayList<SchoolDevice>();
			
			for (OperationItemDevice operationItemDevice : operationItem.getOperationItemDevices()) 
			{
				existLabRoomDevice.add(operationItemDevice.getSchoolDevice());
			}
			
			allLabRoomDevice.removeAll(existLabRoomDevice);  //去除已经添加的labRoomDevice
			mav.addObject("listLabRoomDevice", allLabRoomDevice);
		}
		mav.addObject("orderBy", 9);//仅传参用，防止我的消息--实验项目卡--审核--返回报404
		
		List<AssetCabinetWarehouseAccessRecord> assetRecords = assetCabinetWarehouseAccessRecordDAO.executeQuery("select a from AssetCabinetWarehouseAccessRecord" +
				" a where 1=1 and status = 1 and asset.category = 0 " +
				"and (assetCabinetWarehouseAccess.type = 0 " +
				"or assetCabinetWarehouseAccess.type != 0 and assetCabinetWarehouseAccess.operationItem.id="+operationItemId+")", 0, -1);
		mav.addObject("assetRecords", assetRecords);
		
//		//查找审核记录
//		List<OperItemAudit> opeItemAudits=operationService.findAllOperItemAuditsByOperItemId(operationItemId);
//		mav.addObject("opeItemAudits", opeItemAudits);

		Map<String, String> params = new HashMap<>();
		String businessType = "OperationItem";
		params.put("businessType", pConfig.PROJECT_NAME + businessType);
		params.put("businessAppUid", String.valueOf(operationItemId));
		params.put("businessUid", "-1");
		String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessLevelStatus", params);
		JSONObject jsonObject = JSONObject.parseObject(s);
		List<Object[]> list = new ArrayList<>();
		if(jsonObject.getString("status").equals("success")){
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			if(jsonArray.size() != 0) {
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject j = jsonArray.getJSONObject(i);
					Object[] o = new Object[3];
					o[0] = j.getString("result");
					Set<Authority> authorities = authorityDAO.findAuthorityByAuthorityName(j.getString("authName"));
					if(authorities.size() != 0){
						o[1] = authorities.iterator().next().getCname();
					}
					o[2] = j.getString("info");
					list.add(o);
				}
			}
		}
		mav.addObject("auditResult", list);

		mav.setViewName("operation/viewOperationItem.jsp");
		return mav;
	}
	
	/**
	 * 审核实验项目
	 * @author hly
	 * 2015.08.06
	 */
	@RequestMapping("/checkOperationItem")
	public String checkOperationItem(HttpServletRequest request,@RequestParam int operationItemId, int result,int flag){
		OperationItem operationItem = operationService.findOperationItemByPrimaryKey(operationItemId);
		String ip = shareService.getIpAddr(request);
		//保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
		//tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id 0-查看 -1--新建
		//action:  0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核查看 6 保存 7 审核编辑后保存 8 导入 9 审核结果
		systemLogService.saveOperationItemLog(ip, 1, 9, operationItemId);
		int direct=0;
		if(flag==1&&operationItem!=null){
			//审核完成后给申请人发送消息
			Message message= new Message();
			Calendar date=Calendar.getInstance();
			message.setSendUser(shareService.getUserDetail().getCname());
			message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
			message.setCond(0);
			String content="";
			if(result==0)  //审核拒绝
			{
				//修改消息
				 List<Message> me=messageService.findAllMessagesById(operationItemId,"等待审核",shareService.getUserDetail().getUsername());
				for(Message m:me){
					content="已经审核";
					content+="<a onclick='changeMessage(this)' href='../operation/viewOperationItemAudited?operationItemId=";
					content+=operationItemId;
					content+="&&flag=3&status=2'>点击查看</a>";//flag设置为3，防止返回报错
					m.setContent(content);
					m.setTage(1);
					messageDAO.store(m);
				}
				operationItem.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "4"));
				message.setTitle(CommonConstantInterface.STR_FLAG_DISAGREE);
				content="您的审核未通过";
				direct=4;
				content+="<a onclick='changeMessage(this)' href='../operation/listItemMaterialRecord?itemId=";
				
				content+=operationItemId;
				content+="&currpage=1&isMine=1&status=0&orderBy=9"+
						"'>点击查看</a>";
				message.setContent(content);
				message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
				message.setCreateTime(date);
				message.setTage(1);
				if (operationItem.getUserByLpCreateUser()!=null) {
					message.setUsername(operationItem.getUserByLpCreateUser().getUsername());//把消息发给项目卡的申请人
				}
				message=messageDAO.store(message);
				//查找审核记录
				List<OperItemAudit> operItemAudits=operationService.findAllOperItemAuditBy(operationItem,shareService.getUser().getUsername());
				for(OperItemAudit o:operItemAudits){
					o.setStatus(0);
					o.setResult("一审拒绝");
					operItemAuditDAO.store(o);
				}
				operationItem.setAuditStage(0);
				operationService.saveOperationItem(operationItem);
				return "redirect:/operation/listOperationItem?currpage=1&&status="+direct+"&orderBy=9";
			}
			else{
				
				//修改消息
				 List<Message> me=messageService.findAllMessagesById(operationItemId,"等待审核",shareService.getUserDetail().getUsername());
				for(Message m:me){
					content="已经审核";
					content+="<a onclick='changeMessage(this)' href='../operation/viewOperationItemAudited?operationItemId=";
					content+=operationItemId;
					content+="&&flag=3&status=2'>点击查看</a>";//flag设置为3，防止返回报错
					m.setContent(content);
					m.setTage(1);
					messageDAO.store(m);
				}
				 

				int labroomid=operationItem.getLabRoom().getId();
				List<LabRoomAdmin> labRoomAdmins=labRoomAdminService.findAllLabRoomAdminsByLabRoomId(labroomid);
				for(LabRoomAdmin l:labRoomAdmins){

					//添加审核记录
					OperItemAudit operItemAudit=new OperItemAudit();
					operItemAudit.setOperationItem(operationItem);
					operItemAudit.setUser(l.getUser());
					operItemAudit.setStatus(3);
					operItemAudit.setResult("一审已审");
					operItemAudit.setCreateDate(date);
					operItemAuditDAO.store(operItemAudit);

				message.setTitle(CommonConstantInterface.STR_OPERATIONITEM_TITLE);
				content="申请成功，等待二次审核";
				content+="<a onclick='changeMessage(this)' href='../operation/viewOperationItem?operationItemId=";
				content+=operationItemId;
				content+="&&flag=3&status=2'>点击查看</a>";//flag设置为3，防止返回报错
				message.setContent(content);
				message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
				message.setCreateTime(date);
				message.setUsername(l.getUser().getUsername());
				message.setTage(2);
				messageDAO.store(message);

				//查找审核记录
				List<OperItemAudit> operItemAudits=operationService.findAllOperItemAuditBy(operationItem,shareService.getUser().getUsername());
				for(OperItemAudit o:operItemAudits){
					if("待审核".equals(o.getResult())){
					o.setStatus(1);
					o.setResult("一审通过");
					operItemAuditDAO.store(o);
					}
				}
				}
				operationItem.setAuditStage(2);
				operationService.saveOperationItem(operationItem);
				return "redirect:/operation/listMyOperationItem?currpage=1&status=0&orderBy=9";
			}
		}
		else if(flag==2&&operationItem!=null){
			//审核完成后给申请人发送消息
			Message message= new Message();
			//记录审核结果
			OperItemAudit operItemAudit=new OperItemAudit();
			Calendar date=Calendar.getInstance();
			message.setSendUser(shareService.getUserDetail().getCname());
			message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
			message.setCond(0);
			String content="";
			if(result==0)  //审核拒绝
			{
				//修改消息
				 List<Message> me=messageService.findAllMessagesById(operationItemId,"等待二次审核",shareService.getUserDetail().getUsername());
				for(Message m:me){
					content="已经审核";
					content+="<a onclick='changeMessage(this)' href='../operation/viewOperationItemAudited?operationItemId=";
					content+=operationItemId;
					content+="&&flag=3&status=2'>点击查看</a>";//flag设置为3，防止返回报错
					m.setContent(content);
					m.setTage(1);
					messageDAO.store(m);
				}
				
				operationItem.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "4"));
				message.setTitle(CommonConstantInterface.STR_FLAG_DISAGREE);
				content="您的审核未通过";
				direct=4;
				content+="<a onclick='changeMessage(this)' href='../operation/listItemMaterialRecord?itemId=";
				
				content+=operationItemId;
				content+="&currpage=1&isMine=1&status=0&orderBy=9"+
						"'>点击查看</a>";
				message.setContent(content);
				message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
				message.setCreateTime(date);
				message.setTage(1);
				if (operationItem.getUserByLpCreateUser()!=null) {
					message.setUsername(operationItem.getUserByLpCreateUser().getUsername());//把消息发给项目卡的申请人
				}
				message=messageDAO.store(message);
				//查找审核记录
				List<OperItemAudit> operItemAudits=operationService.findAllOperItemAuditBy(operationItem,shareService.getUser().getUsername());
				for(OperItemAudit o:operItemAudits){
					if("一审通过".equals(o.getResult())){
					o.setStatus(0);
					o.setResult("二审拒绝");
					operItemAuditDAO.store(o);
					}
				}
				operationItem.setAuditStage(0);
				operationService.saveOperationItem(operationItem);
				return "redirect:/operation/listOperationItem?currpage=1&&status="+direct+"&orderBy=9";
			}
			else{
				
				//修改消息
				 List<Message> me=messageService.findAllMessagesById(operationItemId,"等待二次审核",shareService.getUserDetail().getUsername());
				for(Message m:me){
					content="已经审核";
					content+="<a onclick='changeMessage(this)' href='../operation/viewOperationItemAudited?operationItemId=";
					content+=operationItemId;
					content+="&&flag=3&status=2'>点击查看</a>";//flag设置为3，防止返回报错
					m.setContent(content);
					m.setTage(1);
					messageDAO.store(m);
				}
				
				User user=new User();
				//实验室主任审核
				//List<User> users=systemService.getUserByAuthority(user, 4);
				int labroomid=operationItem.getLabRoom().getId();
				//List<LabRoomAdmin> labRoomAdmins=labRoomAdminService.findLabRoomAdminForType3ByLabRoomId(labroomid);
				//for(LabRoomAdmin la:labRoomAdmins){
					User u=operationItem.getLabRoom().getLabCenter().getUserByCenterManager();
					//添加审核记录
					OperItemAudit oper=new OperItemAudit();
					oper.setOperationItem(operationItem);
					oper.setUser(u);
					oper.setStatus(3);
					oper.setResult("二审已审");
					oper.setCreateDate(date);
					operItemAuditDAO.store(oper);
				message.setTitle(CommonConstantInterface.STR_OPERATIONITEM_TITLE);
				content="申请成功，等待实训中心主任审核";
				content+="<a onclick='changeMessage(this)' href='../operation/viewOperationItem?operationItemId=";
				content+=operationItemId;
				content+="&&flag=3&status=2'>点击查看</a>";//flag设置为3，防止返回报错
				message.setContent(content);
				message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
				message.setCreateTime(date);
				message.setUsername(u.getUsername());
				message.setTage(2);
				messageDAO.store(message);
				
				//查找审核记录
				List<OperItemAudit> operItemAudits=operationService.findAllOperItemAuditBy(operationItem,shareService.getUser().getUsername());
				for(OperItemAudit o:operItemAudits){
					if("一审已审".equals(o.getResult())){
					o.setStatus(1);
					o.setResult("二审通过");
					operItemAuditDAO.store(o);
					}
				}
				operationItem.setAuditStage(3);
				operationService.saveOperationItem(operationItem);
				return "redirect:/operation/listMyOperationItem?currpage=1&status=0&orderBy=9";
			}
			
		}
		else if(flag==3&&operationItem!=null){
			//审核完成后给申请人发送消息
			Message message= new Message();
			Calendar date=Calendar.getInstance();
			message.setSendUser(shareService.getUserDetail().getCname());
			message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
			message.setCond(0);
			String content="";
			if(result==0)  //审核拒绝
			{
				//修改消息
				 List<Message> me=messageService.findAllMessagesById(operationItemId,"等待实训中心主任审核",shareService.getUserDetail().getUsername());
				for(Message m:me){
					content="已经审核";
					content+="<a onclick='changeMessage(this)' href='../operation/viewOperationItemAudited?operationItemId=";
					content+=operationItemId;
					content+="&&flag=3&status=2'>点击查看</a>";//flag设置为3，防止返回报错
					m.setContent(content);
					m.setTage(1);
					messageDAO.store(m);
				}
				
				//查找审核记录
				List<OperItemAudit> operItemAudits=operationService.findAllOperItemAuditBy(operationItem,shareService.getUser().getUsername());
				for(OperItemAudit o:operItemAudits){
					if("二审已审".equals(o.getResult())){
					o.setStatus(0);
					o.setResult("三审拒绝");
					operItemAuditDAO.store(o);
					}
				}
				operationItem.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "4"));
				message.setTitle(CommonConstantInterface.STR_FLAG_DISAGREE);
				content="您的审核未通过";
				direct=4;
				operationItem.setAuditStage(0);
				operationService.saveOperationItem(operationItem);
			}
			else if(result==1)  //审核通过
			{
				//修改消息
				 List<Message> me=messageService.findAllMessagesById(operationItemId,"等待实训中心主任审核",shareService.getUserDetail().getUsername());
				for(Message m:me){
					content="已经审核";
					content+="<a onclick='changeMessage(this)' href='../operation/viewOperationItemAudited?operationItemId=";
					content+=operationItemId;
					content+="&&flag=3&status=2'>点击查看</a>";//flag设置为3，防止返回报错
					m.setContent(content);
					m.setTage(1);
					messageDAO.store(m);
				}
				
				//查找审核记录
				List<OperItemAudit> operItemAudits=operationService.findAllOperItemAuditBy(operationItem,shareService.getUser().getUsername());
				for(OperItemAudit o:operItemAudits){
					if("二审已审".equals(o.getResult())){
					o.setStatus(1);
					o.setResult("三审通过");
					operItemAuditDAO.store(o);
					}
				}
				operationItem.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "3"));
				message.setTitle(CommonConstantInterface.STR_FLAG_AGREE);
				content="您的审核已通过";
				direct=3;
			}
			content+="<a onclick='changeMessage(this)' href='../operation/listItemMaterialRecord?itemId=";
			
			content+=operationItemId;
			content+="&currpage=1&isMine=1&status=0&orderBy=9"+
					"'>点击查看</a>";
			message.setContent(content);
			message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
			message.setCreateTime(date);
			message.setTage(1);
			if (operationItem.getUserByLpCreateUser()!=null) {
				message.setUsername(operationItem.getUserByLpCreateUser().getUsername());//把消息发给项目卡的申请人
			}
			message=messageDAO.store(message);
			operationItem.setAuditStage(4);
			operationService.saveOperationItem(operationItem);
		return "redirect:/operation/listOperationItem?currpage=1&&status="+direct+"&orderBy=9";
		}
		else{return "redirect:/operation/listMyOperationItem?currpage=1&status=0&orderBy=9";}
	}
	
	/**
	 * 设置项目编号
	 * @author hly
	 * 2015.08.07
	 */
	@RequestMapping("/saveCodeCustom")
	public String saveCodeCustom(@ModelAttribute OperationItem operationItem1, Integer status){
		operationService.saveCodeCustom(operationItem1);
		
		return "redirect:/operation/listOperationItem?currpage=1&status="+status+"&orderBy=9";
	}
	
	/**
	 * 导入整个学期的实验项目
	 * @author hly
	 * 2015.08.10
	 */
	@RequestMapping("/importTermOperationItem")
	public String importTermOperationItem(@RequestParam int sourceId, int targetId, String acno){
		
		if(sourceId != targetId && !acno.equals("-1"))
		{
			operationService.importTermOperationItem(sourceId, targetId, acno);
		}
		return "redirect:/operation/listMyOperationItem?currpage=1&status=1&orderBy=9";
	}
	
	/**
	 * 导入选中的实验项目
	 * @author hly
	 * 2015.08.10
	 */
	@RequestMapping("/importOperationItem")
	public String importOperationItem(HttpServletRequest request,@RequestParam int termId, String itemIds){
		operationService.importOperationItem(request,termId, itemIds);
		
		return "redirect:/operation/listOperationItemLims?currpage=1&status=1&orderBy=9";//导入后
	}

	/**
	 * 导入选中学期的所有实验项目
	 * @author 贺照易修改
	 * 2018-11-12
	 */
    @ResponseBody
	@RequestMapping("/importAllOperationItem")
	public String importAllOperationItem(HttpServletRequest request,@RequestParam Integer termId,Integer itemId) {

	    operationService.importAllOperationItem(request,termId, itemId);
		/*return "redirect:/operation/listOperationItemLims?currpage=1&status=1&orderBy=9";*///导入后
		return "success";
	}
	
	/**
	 * 实验项目材料使用记录列表
	 * @author hly
	 * 2015.08.10
	 */
	@RequestMapping("/listItemMaterialRecord")
	public ModelAndView listItemMaterialRecord(@RequestParam int itemId, int currpage, int isMine,int status, int orderBy){
		ModelAndView mav = new ModelAndView();
		
		int pageSize = CommonConstantInterface.INT_PAGESIZE;
		int totalRecords = operationService.getItemMaterialRecordByItemCount(itemId);
		
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("listItemMaterialRecord", operationService.getItemMaterialRecordByItem(itemId, currpage, pageSize));
		mav.addObject("operationItemMaterialRecord", new OperationItemMaterialRecord());
		
		OperationItem operationItem=operationService.findOperationItemByPrimaryKey(itemId);
		mav.addObject("operationItem", operationItem);
		String majors=operationItem.getLpMajorFit();
		String majorStr="";
		if(majors!=null&&majors!=""){
		String[] majorArray=majors.split(",");
		
		for (String string : majorArray) {
			SchoolMajor major = systemService.findSchoolMajorByNumber(string);
			if (major!=null&&!major.getMajorName().equals("")) {
				majorStr+=major.getMajorName()+"，";
			}
			if (majorStr.length()>1) {
				majorStr=majorStr.substring(0,majorStr.length()-1);
			}
		}
		}
		mav.addObject("majorStr", majorStr);
		
		mav.addObject("categoryMaterialRecordMain", shareService.getCDictionaryData("category_operation_item_material_record_main"));
		mav.addObject("isMine", isMine);
		mav.addObject("status", status);
		mav.addObject("orderBy", orderBy);
		mav.addObject("page",currpage);
		mav.addObject("term_id",operationItem.getSchoolTerm().getId());
		mav.addObject("course_number",operationItem.getSchoolCourseInfo().getCourseNumber());
		mav.addObject("lp_create_user",operationItem.getUserByLpCreateUser().getUsername());
		mav.addObject("lp_name",operationItem.getLpName());
		mav.setViewName("operation/listItemMaterialRecord.jsp");
		return mav;
	}
	
	/**
	 * 保存实验项目材料使用记录
	 * @author hly
	 * 2015.08.10
	 */
	@RequestMapping("/saveItemMaterialRecord")
	public String saveItemMaterialRecord(@ModelAttribute OperationItemMaterialRecord operationItemMaterialRecord,@RequestParam int flag,int status,int id){
		operationService.saveItemMaterialRecord(operationItemMaterialRecord);
		
		return "redirect:/operation/viewOperationItem?operationItemId="+id+"&flag="+3+"&status="+status;//flag=3  保存完成以后，跳转页面不记录状态信息
	}
	
	/**
	 * 新建项目时保存实验项目材料使用记录
	 * @author hly
	 * 2015.08.10
	 */
	@RequestMapping("/saveItemMaterialRecordNew")
	public String saveItemMaterialRecordNew(@ModelAttribute OperationItemMaterialRecord operationItemMaterialRecord){
		operationService.saveItemMaterialRecord(operationItemMaterialRecord);
		return "redirect:/operation/editOperationItem?operationItemId="+operationItemMaterialRecord.getOperationItem().getId()+"&&isMine=1&flagId=1";
	}
	
	/**
	 * 删除实验项目材料使用记录--自己删除
	 * @author hly
	 * 2015.08.10
	 */
	@RequestMapping("/deleteItemMaterialRecord")
	public String deleteItemMaterialRecord(@RequestParam int mrId, int itemId){
		operationService.deleteItemMaterialRecord(mrId);
		return "redirect:/operation/editOperationItem?operationItemId="+itemId+"&&isMine=1&flagId=1";
	}
	
	
	/**
	 * 删除实验项目材料使用记录--审核人删除
	 * @author hly
	 * 2015.08.10
	 */
	@RequestMapping("/deleteItemMaterialRecordNew")
	public String deleteItemMaterialRecordNew(@RequestParam int mrId, int itemId,int flag,int status){
		operationService.deleteItemMaterialRecord(mrId);
		return "redirect:/operation/viewOperationItem?operationItemId="+itemId+"&flag="+3+"&status="+status;//flag=3  不记录页面状态
	}
	
	/**
	 * ajax获取材料使用记录信息
	 * @author hly
	 * 2015.08.10
	 */
	@RequestMapping(value="/ajaxGetMaterialRecord", produces="application/json;charset=UTF-8")
	public @ResponseBody String ajaxGetMaterialRecord(@RequestParam int mrId){
		OperationItemMaterialRecord mr = operationService.findItemMaterialRecordByPrimaryKey(mrId);
		StringBuffer result = new StringBuffer();
		result.append("{");
		result.append("\"lpmrId\":"+mr.getLpmrId()+",");
		result.append("\"lpmrName\":\""+mr.getLpmrName()+"\",");
		result.append("\"lpmrModel\":\""+mr.getLpmrModel()+"\",");
		result.append("\"lpmrCategory\":"+mr.getCDictionary().getId()+",");
		result.append("\"lpmrUnit\":\""+mr.getLpmrUnit()+"\",");
		result.append("\"lpmrQuantity\":\""+mr.getLpmrQuantity()+"\",");
		result.append("\"lpmrAmount\":\""+mr.getLpmrAmount()+"\",");
		result.append("\"lpmrRemark\":\""+mr.getLpmrRemark()+"\"");
		result.append("}");
		
		return result.toString();
	}
	
	/**
	 * 实验项目设备表
	 * @author hly
	 * 2015.08.19
	 */
	@RequestMapping("/listItemDevice")
	public ModelAndView listItemDevice(@RequestParam int itemId, int currpage, int isMine, int status, int orderBy){
		ModelAndView mav = new ModelAndView();
		
		int pageSize = CommonConstantInterface.INT_PAGESIZE;
		int totalRecords = operationService.getItemDeviceByItemCount(itemId, null);
		OperationItem operationItem = operationService.findOperationItemByPrimaryKey(itemId);
		
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("operationItem", operationItem);
		mav.addObject("isMine", isMine);
		mav.addObject("listItemDevice", operationService.getItemDeviceByItem(itemId, null, 1, -1));
		if(operationItem!=null && operationItem.getLabRoom()!=null)
		{
			Set<SchoolDevice> allLabRoomDevice = schoolDeviceService.findAllSchoolDevice();

			List<SchoolDevice> existLabRoomDevice = new ArrayList<SchoolDevice>();
			
			for (OperationItemDevice operationItemDevice : operationItem.getOperationItemDevices()) 
			{
				existLabRoomDevice.add(operationItemDevice.getSchoolDevice());
			}
			allLabRoomDevice.removeAll(existLabRoomDevice);  //去除已经添加的labRoomDevice
			
			mav.addObject("orderBy", orderBy);
			mav.addObject("listLabRoomDevice", allLabRoomDevice);
		}
		
		mav.addObject("status", status);
		mav.setViewName("operation/listItemDevice.jsp");
		return mav;
	}
	
	/**
	 * 保存实验项目设备
	 * @author hly
	 * 2015.08.19
	 */
	@RequestMapping("/saveItemDevice")
	public String saveItemDevice(@RequestParam int itemId, String category, String ids){
		
		operationService.saveItemDevice(itemId, category, ids);
		
		return "redirect:/operation/editOperationItem?operationItemId="+itemId+"&isMine=1&flagId=1";//2015-09-23 16:41:46   贺子龙  修改跳转页面
	}
	
	/**
	 * 保存实验项目设备
	 * @author hly
	 * 2015.08.19
	 */
	@RequestMapping("/saveItemDeviceNew")
	public String saveItemDeviceNew(@RequestParam int itemId, String category, String ids,int flag,int status){
		
		operationService.saveItemDevice(itemId, category, ids);
		return "redirect:/operation/viewOperationItem?operationItemId="+itemId+"&flag="+3+"&status="+status;//flag=3  跳转页面不记录状态信息
	}
	
	/**
	 * 删除实验项目设备--自己删除
	 * @author hly
	 * 2015.08.20
	 */
	@RequestMapping("/deleteItemDevice")
	public String deleteItemDevice(@RequestParam int itemDeviceId, int itemId){
		operationService.deleteItemDevice(itemDeviceId);
		
		return "redirect:/operation/editOperationItem?operationItemId="+itemId+"&&isMine=1&flagId=1";
	}
	
	/**
	 * 删除实验项目设备--审核人删除
	 * @author hly
	 * 2015.08.20
	 */
	@RequestMapping("/deleteItemDeviceNew")
	public String deleteItemDeviceNew(@RequestParam int itemDeviceId, int itemId, int flag,int status){
		operationService.deleteItemDevice(itemDeviceId);
		return "redirect:/operation/viewOperationItem?operationItemId="+itemId+"&flag="+3+"&status="+status;//flag=3  不记录状态信息
	}
	
	/**
	 * ajax获取指定学院、指定角色的用户数据
	 * @author hly
	 * 2015.08.28
	 */
	@RequestMapping("/ajaxGetUser")
	public @ResponseBody List<Map<String, String>> ajaxGetUser(@RequestParam String academyNumber, String role){
		List<Map<String, String>> result=operationService.getUserByAcademyRole(academyNumber, role, 18);
		if (result.size()>0) {
			return operationService.getUserByAcademyRole(academyNumber, role, 18);//18--系主任
		}else {
			return null;
		}
	}
	
	/**
	 * 批量删除实验项目
	 * @author 贺子龙
	 * 2015-11-30
	 */
	@RequestMapping("/batchDeleteOperationItem")
	public String batchDeleteOperationItem(@RequestParam int[] array, int status){
		for (int operationItemId : array) {
			operationService.deleteOperationItem(operationItemId);
		}
		return "redirect:/operation/listOperationItemLims?currpage=1&status="+status+"&orderBy=9";
	}
	/*********************************************************************************
	 * 功能： 实验大纲管理
	 * 作者：徐文
	 * 日期：2016-05-27
	 *********************************************************************************/
	@RequestMapping("/experimentalmanagement")
	public ModelAndView experimentalmanagement(@ModelAttribute OperationOutline operationOutline  ,@RequestParam int currpage,@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		int pageSize=30;
		int totalRecords=operationService.getOutlinelistpage(operationOutline,1,-1,acno).size();
		List<OperationOutline> Outlinelist=operationService.getOutlinelistpage(operationOutline,currpage,pageSize,acno);
		Map<String,Integer> pageModel = shareService.getPage(currpage, pageSize,totalRecords);
		
	    mav.addObject("newFlag", true);
	    mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("currpage", currpage);
		mav.addObject("pageSize", pageSize);
		mav.addObject("operationOutline", operationOutline);
		mav.addObject("user", shareService.getUser());
		int yes=0;//yes等于0不是所选择实验室的主任 1是
//		for (LabCenter labCenter : shareService.getUser().getLabCentersForCenterManager()) {
//			if(labCenter.getId().equals(sid)){
//				yes=1;
//			}
//		}
		 mav.addObject("yes", yes);
		//查找所有的实验大纲
		mav.addObject("Outlinelist",Outlinelist );
		
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId(); 
		mav.addObject("term", term);
		// 获取学期列表
		List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
		mav.addObject("schoolTerm", schoolTerms);
		mav.setViewName("operation/experimentalmanagement.jsp");
		return mav;
	}
	/*********************************************************************************
	 * 功能:查看大纲内容
	 * 作者：徐文
	 * 日期：2016-05-27
	 ********************************************************************************/
	@RequestMapping("/checkout")
	public ModelAndView checkout(@RequestParam int idkey,@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
        mav.addObject("infor", operationService.getoperationoutlineinfor(idkey));  
		mav.setViewName("operation/checout.jsp");
		return mav;
	}	
	/*********************************************************************************
	 * 功能:新建大纲内容
	 * 作者：徐文
	 * 日期：2016-05-30
	 ********************************************************************************/
	@RequestMapping("/newoperationproject")
	public ModelAndView newoperationproject(@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		OperationOutline OperationOutline= new OperationOutline();
		OperationOutline.setSchoolAcademy(shareService.getUser().getSchoolAcademy());
		mav.addObject("operationOutline",OperationOutline);
		Set<SchoolMajor> majorsEdit = OperationOutline.getSchoolMajors();
		mav.addObject("majorsEdit",majorsEdit);
		Set<CDictionary> property = OperationOutline.getCDictionarys();
		mav.addObject("property",property);
		//查找登录人所在学院课程
		mav.addObject("schoolCourseInfoMap", operationService.getschoolcouresMap(acno));
		//查找登录人所在的学院专业
		mav.addObject("schoolmajer", operationService.getschoolmajerSet(acno));
		//查找学分
		mav.addObject("operationscareMap", operationService.getcoperationscareMap());
		//查找登录人所在学院的开课学院
		mav.addObject("operationstartschooleMap", operationService.getoperationstartschooleMap(acno));
		//查找开课性质
		mav.addObject("commencementnaturemap", operationService.getcommencementnatureSet());
		//获取项目卡
		mav.addObject("operationItem", operationService.getoperationItemlist()); 
		mav.addObject("isNew", 1);
		//所有学期
		mav.addObject("schoolTerms",shareService.getTermsMap());
		mav.setViewName("operation/newoperationproject.jsp");
		return mav;
	}
	/*********************************************************************************
	 * 功能:保存item
	 * 作者：徐文
	 * 日期：2016-05-31
	 ********************************************************************************/
	@RequestMapping("/getuserprojectitems")
	public @ResponseBody String  getuserprojectitems(@RequestParam String projectitems){
		//System.out.println(projectitems);
		String stsr="";
		Integer ss=0;
		Integer aa=0;
		   if(projectitems.length()>0){
			      String[] str=projectitems.split(",");
			   
			   for (String string : str) { 
				   if(string!=null && string!=""){
				OperationItem s=  operationItemDAO.findOperationItemById(Integer.parseInt(string));
				if(s!=null){
				  String cop=null;
					if(s.getCDictionaryByLpCategoryMain()==null){
						cop="无";
				}else{
					cop=s.getCDictionaryByLpCategoryMain().getCName();
				}
					String sa=null;
					if(s.getLabRooms()!=null){
						for (LabRoom sts : s.getLabRooms()) {
							sa+=sts.getLabRoomName()+",";
						}
					
					}else{
					sa="无";
					}
					aa=s.getLpDepartmentHours();
					stsr+="<tr id='"+s.getId()+"' ><td >"+s.getLpCodeCustom()+"</td><td >"+s.getLpName()+"</td><td >"+cop+"</td><td >"+aa+"</td><td >"+ss+"</td><td >"+sa+"</td><td ><input class='savebutton'    id='"+s.getId()+"'  onclick='del(this.id)'   type='button' value='删除' /></td></tr>"; 
			}}}
		   }
		
		return shareService.htmlEncode(stsr);
	}
	/*********************************************************************************
	 * 功能:保存大纲内容
	 * 作者：徐文
	 * 日期：2016-05-31
	 ********************************************************************************/
	@RequestMapping("/saveoperationoutline")
	public String  saveoperationoutline(@ModelAttribute OperationOutline operationOutline ,HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {
	    //获取面向专业  多对多
		//String schoolMajors=request.getParameter("schoolMajorsa");
		//获取课程性质 多对多
		
		
		String[] commencementnaturemaps = request.getParameterValues("commencementnaturemap");
		String[] schoolMajors = request.getParameterValues("schoolMajorsa");
		String[] projectitrms = request.getParameter("projectitrms").split(",");
//		if(sid != -1)
//		{
//			operationOutline.setLabCenter(labCenterService.findLabCenterByPrimaryKey(sid));
//		}
//		System.out.println(projectitrms.length);
		
		//String commencementnaturemap=request.getParameter("commencementnaturemap");
		//String projectitrms=request.getParameter("projectitrms");
		String docment=request.getParameter("docment");
		 OperationOutline op=	operationService.saveoperationoutline(operationOutline,schoolMajors,commencementnaturemaps,projectitrms);
		  if(docment!=null && docment!=""){
			 String[] str= docment.split(",");
			 for (String string : str) {
				 if(string!=null && string!=""){
					CommonDocument dd= commonDocumentDAO.findCommonDocumentByPrimaryKey(Integer.parseInt(string));
				     if(dd!=null){ 
					dd.setOperationOutline(op);
				      commonDocumentDAO.store(dd);
				 }
				 }
			}
			  
		  }
		return "redirect:/operation/experimentalmanagement?currpage=1";
	}
	
	/*********************************************************************************
	 * 功能:多文件上传
	 * 作者：徐文
	 * 日期：2016-06-01
	 ********************************************************************************/
	@RequestMapping("/uploaddnolinedocment")
	public @ResponseBody String uploaddnolinedocment(HttpServletRequest request, HttpServletResponse response, BindException errors,Integer id) throws Exception {
		String ss=operationService.uploaddnolinedocment(request, response,id);
		return shareService.htmlEncode(ss);
	}
	/****************************************************************************
	 * 功能：删除大纲文件
	 * 作者：徐文
	 * 日期：2016-06-01
	 ****************************************************************************/
	@RequestMapping("/delectdnolinedocment")
	public @ResponseBody String delectdnolinedocment(@RequestParam Integer idkey) throws Exception {
		CommonDocument d=commonDocumentDAO.findCommonDocumentById(idkey);
		commonDocumentDAO.remove(d);
		return "ok";
	}
	/****************************************************************************
	 * 功能：下载大纲文件
	 * 作者：徐文
	 * 日期：2016-06-01
	 ****************************************************************************/
	@RequestMapping("/downloadfile ")
	public void donloudfujian(HttpServletRequest request, HttpServletResponse response,@RequestParam int idkey) throws Exception {
		CommonDocument d=commonDocumentDAO.findCommonDocumentById(idkey);
		String filename = d.getDocumentName();
		
		String path=d.getDocumentUrl();
		try{			
		File sendPath = new File(path);
        FileInputStream fis = new FileInputStream(sendPath);
		response.setCharacterEncoding("utf-8");
		//解决上传中文文件时不能下载的问题
		response.setContentType("multipart/form-data;charset=UTF-8");
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
			filename = new String(filename.getBytes("UTF-8"),"ISO8859-1");// firefox浏览器
		} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
			filename = URLEncoder.encode(filename, "UTF-8");// IE浏览器
		} else {
			filename = URLEncoder.encode(filename, "UTF-8");
		}
		response.setHeader("Content-Disposition", "attachment;fileName="+filename);		
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
		}
	}
	/****************************************************************************
	 * 功能：搜索项目卡
	 * 作者：徐文
	 * 日期：2016-06-01
	 ****************************************************************************/
	@SuppressWarnings("unused")
	@RequestMapping("/getitem")
	public @ResponseBody String getitem(String nameop) {
	
		List<OperationItem> d= operationService.getitem(nameop);
		String str="";
		if(d.size()>0){
		  for (OperationItem itm : d) {
				str+="<tr align='center'><td align='center'><input type='checkbox' value='"+itm.getId()+"'></td><td align='center'>"+itm.getLpCodeCustom()+"</td><td align='center'>"+itm.getLpName()+"</td><td align='center'></td></tr>";
		  }
		}
    return shareService.htmlEncode(str);
	}
	/****************************************************************************
	 * 功能：导出实验室大纲
	 * 作者：徐文
	 * 日期：2016-06-01
	 ****************************************************************************/
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	@RequestMapping("/outline/exportOutline")
	public void export(@ModelAttribute OperationOutline operationOutline ,HttpServletRequest request, HttpServletResponse response,@RequestParam int page,@ModelAttribute("selected_academy") String acno) throws Exception {
		
		int pageSize=30;
		
		List<OperationOutline> findList=operationService.getOutlinelistpage(operationOutline,page,pageSize,acno);
		List<Map> list=new ArrayList<Map>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (OperationOutline f : findList) {
			Map map=new HashMap();
			map.put("1", f.getLabOutlineName());//dagangmingcheng
			if(f.getUser().getCname() != null&&f.getUser().getCname() != null ){
			map.put("2", f.getUser().getCname());//jiaoshi
			}
			if(f.getSchoolCourseInfoByClassId()!=null&& f.getSchoolCourseInfoByClassId().getCourseNumber()!=null){
			map.put("3",   f.getSchoolCourseInfoByClassId().getCourseNumber());//kechengbiaohao
			}
			if(f.getSchoolCourseInfoByClassId()!=null && f.getSchoolCourseInfoByClassId().getCourseName()!=null){
			map.put("4",  f.getSchoolCourseInfoByClassId().getCourseName());//kechengmingcheng
			}
			if(f.getSchoolAcademy()!=null&&f.getSchoolAcademy().getAcademyName()!=null){
			map.put("5", f.getSchoolAcademy().getAcademyName());//xueyuan
			}
			list.add(map);
		 }
		 String title = "实验大纲列表";
	        String[] hearders = new String[] {"大纲名称","教师", "课程编号","课程名称", "学院"};//表头数组
	        String[] fields = new String[] {"1", "2","3","4","5"};//Financialresources对象属性数组
	        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders),fields);
	        JsGridReportBase report = new JsGridReportBase(request, response);
	        report.exportToExcel(title,shareService.getUser().getCname(), td);
		
	}
	/****************************************************************************
	 * 功能：删除实验室大纲
	 * 作者：徐文
	 * 日期：2016-06-01
	 ****************************************************************************/
	@RequestMapping("/delectuotline")
	public String  delectuotline(@RequestParam int idkey) {
		operationService.delectloutline(idkey);
		return "redirect:/operation/experimentalmanagement?currpage=1";
	}
	/****************************************************************************
	 * 功能：编辑实验室大纲
	 * 作者：徐文
	 * 日期：2016-06-01
	 ****************************************************************************/
	@RequestMapping("/editoutline")
	public ModelAndView newoperationproject(@RequestParam int idkey,@ModelAttribute("selected_academy") String acno) {
		/**
		 * 
		 */
		ModelAndView mav = new ModelAndView();
		// 查找被编辑的大纲对象
		OperationOutline operationOutline=operationService.getoperationoutlineinfor(idkey);
		mav.addObject("operationOutline",operationOutline);
		// 被编辑对象的多选框内容
		Set<SchoolMajor> majorsEdit = operationOutline.getSchoolMajors();
		mav.addObject("majorsEdit",majorsEdit);
		Set<CDictionary> property = operationOutline.getCDictionarys();
		mav.addObject("property",property);
		Set<OperationItem> item = operationOutline.getOperationItems();
		mav.addObject("item",item);
		//所有学期
		mav.addObject("schoolTerms",shareService.getTermsMap());		
		
		
		//查找登录人所在学院课程
		mav.addObject("schoolCourseInfoMap", operationService.getschoolcouresMap(acno));
		//查找登录人所在的学院专业
		mav.addObject("schoolmajer", operationService.getschoolmajerSet(acno));
		//查找学分
		mav.addObject("operationscareMap", operationService.getcoperationscareMap());
		//查找登录人所在学院的开课学院
		mav.addObject("operationstartschooleMap", operationService.getoperationstartschooleMap(acno));
		//查找开课性质
		mav.addObject("commencementnaturemap", operationService.getcommencementnatureSet());
		//获取项目卡
		mav.addObject("operationItem", operationService.getoperationItemlist());
		mav.addObject("isNew", 0);
		mav.setViewName("operation/newoperationproject.jsp");
		return mav;
	}
	
	/**
	 * 新建项目时保存实验项目材料使用记录
	 * @author hly
	 * 2015.08.10
	 */
	@RequestMapping("/saveItemMaterialRecordNewAfterAudit")
	public String saveItemMaterialRecordNewAfterAudit(@ModelAttribute OperationItemMaterialRecord operationItemMaterialRecord,@RequestParam String lp_name,@RequestParam int term_id,@RequestParam String course_number,
			@RequestParam String lp_create_user,@RequestParam int page,@RequestParam int isMine,@RequestParam int status,
			@RequestParam int orderBy,@RequestParam int id){
		operationService.saveItemMaterialRecord(operationItemMaterialRecord);
		return "redirect:/operationRest/listItemMaterialRecordRest/" + lp_name + "/"+ term_id + "/" + course_number + "/" + lp_create_user + "/"+page+ "/1/"+status+"/"+orderBy+"/" +id+"/";
	}
	
	/*********************************************************************************
	 * @description:实验项目多文件上传
	 * @author：郑昕茹
	 * @date：2016-11-09
	 ********************************************************************************/
	@RequestMapping("/uploadItemdocument")
	public @ResponseBody String uploadItemdocument(HttpServletRequest request, HttpServletResponse response, 
			BindException errors,Integer id, int flag) throws Exception {
		String ss=operationService.uploadItemdocument(request, response, id, flag);
		return shareService.htmlEncode(ss);
	}
	
	
	/**
	 * 保存实验项目设备
	 * @author hly
	 * 2015.08.19
	 */
	@RequestMapping("/saveItemDeviceRest")
	public @ResponseBody String saveItemDeviceRest(@RequestParam int itemId, String category, String ids){
		
		operationService.saveItemDevice(itemId, category, ids);
		
//		return "redirect:/operation/listItemDevice?itemId="+itemId+"&currpage=1";
		return "success";//2015-09-23 16:41:46   贺子龙  修改跳转页面
	}
	
	/**
	 * 删除实验项目设备--自己删除
	 * @author hly
	 * 2015.08.20
	 */
	@RequestMapping("/deleteItemDeviceRest")
	public @ResponseBody String deleteItemDeviceRest(@RequestParam int itemDeviceId){
		operationService.deleteItemDevice(itemDeviceId);
		
		return "success";
	}
	
	
	/**
	 * 删除实验项目材料使用记录--自己删除
	 * @author hly
	 * 2015.08.10
	 */
	@RequestMapping("/deleteItemMaterialRecordRest")
	public @ResponseBody String deleteItemMaterialRecordRest(@RequestParam int mrId){
		operationService.deleteItemMaterialRecord(mrId);
		return "success";
	}
	
	
	/**
	 * 查看实验项目信息
	 * @author hly
	 * 2015.08.06
	 */
	@RequestMapping("/viewOperationItemAudited")
	public ModelAndView viewOperationItemAudited(@RequestParam int operationItemId,int flag,int status){
		ModelAndView mav = new ModelAndView();
		StringBuffer majorStr = new StringBuffer();
		OperationItem operationItem = operationService.findOperationItemByPrimaryKey(operationItemId);
		
		if(operationItem!=null&&operationItem.getLpMajorFit()!=null && !"".equals(operationItem.getLpMajorFit()))
		{
			String majorArr[] = operationItem.getLpMajorFit().split(",");
			
			for (String s : majorArr) 
			{
				if (systemService.findSchoolMajorByNumber(s)!=null) {
					majorStr.append(systemService.findSchoolMajorByNumber(s).getMajorName()+"["+s+"],");
				}
			}
			if(majorStr.length() > 0)
			{
				majorStr.deleteCharAt(majorStr.length()-1);  //去掉最后一个逗号
			}
		}
		mav.addObject("flag", flag);
		mav.addObject("status", status);
		//审核阶段
		List<OperItemAudit> opes=operationService.findAllOperaItemAuditByoperaItemId(operationItemId);
		int step=1;
		if(opes!=null){
		for(OperItemAudit ope:opes){
			if("一审通过".equals(ope.getResult())){
				step=2;
			}if("二审通过".equals(ope.getResult())){
				step=3;break;
			}
		}}
		mav.addObject("step", step);
		mav.addObject("operationItem", operationItem);
		mav.addObject("currUser", shareService.getUserDetail());  //当前登录人
		mav.addObject("majorStr", majorStr);  //面向专业
		mav.addObject("toCheck", shareService.getCDictionaryByCategory("status_operation_item_check", "2"));  // 审核中
		mav.addObject("status", status); 
		//2015-09-24 10:10:45新增    仪器、材料
		mav.addObject("listItemDevice", operationService.getItemDeviceByItem(operationItemId, null, 1, -1));
		mav.addObject("listItemMaterialRecord", operationService.getItemMaterialRecordByItem(operationItemId));
		
		//审核人可以进行编辑
        mav.addObject("labProjectMain", shareService.getCDictionaryData("category_operation_item_main"));  //实验类别
        mav.addObject("labProjectApp", shareService.getCDictionaryData("category_operation_item_app"));  //实验类型
        mav.addObject("labProjectNature", shareService.getCDictionaryData("category_operation_item_nature"));  //实验性质
        mav.addObject("labProjectStudent", shareService.getCDictionaryData("category_operation_item_student"));  //实验者类型
        mav.addObject("labProjectChange", shareService.getCDictionaryData("status_operation_item_change"));  //变动状态
        mav.addObject("labProjectPublic", shareService.getCDictionaryData("category_operation_item_public"));  //开放实验
        mav.addObject("labProjectRewardLevel", shareService.getCDictionaryData("category_operation_item_reward_level"));  //获奖等级
        mav.addObject("labProjectRequire", shareService.getCDictionaryData("category_operation_item_require"));  //实验要求
        mav.addObject("labProjectGuideBook", shareService.getCDictionaryData("category_operation_item_guide_book"));  //实验指导书
        mav.addObject("schoolTerms", shareService.findAllSchoolTerm());  //学期
        mav.addObject("users", systemService.getAllUser(1, -1, shareService.getUser()));  //教师数据
        LabRoom labRoom = new LabRoom();
        mav.addObject("labRooms", labRoomService.findAllLabRoomByQuery(1, -1, labRoom));  //实验室数据
        SchoolCourseInfo schoolCourseInfo = new SchoolCourseInfo();
        mav.addObject("schoolCourseInfos", schoolCourseInfoService.getCourseInfoByQuery(schoolCourseInfo, -1, 1, -1));  //课程数据
        mav.addObject("subjects", systemService.getAllSystemSubject12(1, -1));  //学科数据
  		mav.addObject("majorList", null);	//已选专业
  		mav.addObject("majors", null);  //所有专业（排除已选）
        //添加材料
  		mav.addObject("operationItemMaterialRecord", new OperationItemMaterialRecord());
  		mav.addObject("categoryMaterialRecordMain", shareService.getCDictionaryData("category_operation_item_material_record_main"));
  		//添加设备
  		if(operationItem!=null && operationItem.getLabRoom()!=null)
		{
  			Set<SchoolDevice> allLabRoomDevice = schoolDeviceService.findAllSchoolDevice();

			List<SchoolDevice> existLabRoomDevice = new ArrayList<SchoolDevice>();
			
			for (OperationItemDevice operationItemDevice : operationItem.getOperationItemDevices()) 
			{
				existLabRoomDevice.add(operationItemDevice.getSchoolDevice());
			}
			
			allLabRoomDevice.removeAll(existLabRoomDevice);  //去除已经添加的labRoomDevice
			mav.addObject("listLabRoomDevice", allLabRoomDevice);
		}
		mav.addObject("orderBy", 9);//仅传参用，防止我的消息--实验项目卡--审核--返回报404
		
		List<AssetCabinetWarehouseAccessRecord> assetRecords = assetCabinetWarehouseAccessRecordDAO.executeQuery("select a from AssetCabinetWarehouseAccessRecord" +
				" a where 1=1 and status = 1 and asset.category = 0 " +
				"and (assetCabinetWarehouseAccess.type = 0 " +
				"or assetCabinetWarehouseAccess.type != 0 and assetCabinetWarehouseAccess.operationItem.id="+operationItemId+")", 0, -1);
		mav.addObject("assetRecords", assetRecords);
		
		//查找审核记录
		List<OperItemAudit> opeItemAudits=operationService.findAllOperItemAuditsByOperItemId(operationItemId);
		mav.addObject("opeItemAudits", opeItemAudits);
		mav.setViewName("operation/viewOperationItemAudited.jsp");
		return mav;
	}
	
	@RequestMapping("/viewAudit")
	public ModelAndView viewAudit(@RequestParam int id,@RequestParam Integer currpage){
		ModelAndView mav = new ModelAndView();
		OperationItem operationItem = operationService.findOperationItemByPrimaryKey(id);
		mav.addObject("operationItem", operationItem);
		mav.setViewName("operation/viewAudit.jsp");
		return mav;
	}
	
	@RequestMapping("/viewAuditUser")
	public ModelAndView viewAuditUser(@RequestParam int id,int currpage,int flag){
		ModelAndView mav = new ModelAndView();
		OperationItem operationItem = operationService.findOperationItemByPrimaryKey(id);
		int statue=operationItem.getAuditStage();
		List<OperItemAudit> operItemAudits=operationService.findAllOperItemAuditsByOperItemId(id);
		List<OperItemAudit> deanAudit= new ArrayList<OperItemAudit>();
		List<OperItemAudit> labRoomAdminAudit= new ArrayList<OperItemAudit>();
		List<OperItemAudit> labRoomCenterDirectorAudit= new ArrayList<OperItemAudit>();
		//待教研室主任审核
		if(statue==1){
		for(OperItemAudit op:operItemAudits){
			if(op.getStatus()==3&&"待审核".equals(op.getResult())){
				deanAudit.add(op);
			}
		}
		}
		//待实训室管理员审核
		if(statue==2){
			for(OperItemAudit op:operItemAudits){
				if(op.getStatus()==3&&"一审已审".equals(op.getResult())){
					labRoomAdminAudit.add(op);
				}
				if(op.getStatus()==1&&"一审通过".equals(op.getResult())){
					deanAudit.add(op);
				}
			}
		}
		//待实训中心主任审核
		if(statue==3){
					for(OperItemAudit op:operItemAudits){
						if(op.getStatus()==1&&"一审通过".equals(op.getResult())){
							deanAudit.add(op);
						}
						if(op.getStatus()==1&&"二审通过".equals(op.getResult())){
							labRoomAdminAudit.add(op);
						}
						if(op.getStatus()==3&&"二审已审".equals(op.getResult())){
							labRoomCenterDirectorAudit.add(op);
						}
					}
				}
		//全部已近审核
		if(statue==4){
				for(OperItemAudit op:operItemAudits){
								if(op.getStatus()==1&&"一审通过".equals(op.getResult())){
									deanAudit.add(op);
								}
								if(op.getStatus()==1&&"二审通过".equals(op.getResult())){
									labRoomAdminAudit.add(op);
								}
								if(op.getStatus()==1&&"三审通过".equals(op.getResult())){
									labRoomCenterDirectorAudit.add(op);
								}
							}
						}
		
		mav.addObject("deanAudit", deanAudit);
		mav.addObject("labRoomAdminAudit", labRoomAdminAudit);
		mav.addObject("labRoomCenterDirectorAudit", labRoomCenterDirectorAudit);
		mav.addObject("statue", statue);
		if(flag==1){
		mav.setViewName("operation/viewAuditUser.jsp");
		}
		if(flag==2){
			mav.setViewName("operation/labRoomAdminAudit.jsp");
		}
		if(flag==3){
			mav.setViewName("operation/labRoomCenterDirectorAudit.jsp");
		}
		return mav;
	}

	/**
	 * @description 导入选中的实验大纲
	 * @author 张德冰
	 * @data 2018.08.14
	 */
	@RequestMapping("/importOperationproject")
	public String importOperationproject(HttpServletRequest request,@RequestParam Integer termId, String itemIds){
		if(termId != null && itemIds!=null && itemIds.length()>0)
		{
			SchoolTerm t = new SchoolTerm();
			//导入的目标学期
			t.setId(termId);
			//获取选中的实验大纲id
			String[] ids = itemIds.split(",");

			for (String string : ids)
			{
				//根据id获取实验大纲
				OperationOutline operationOutline=operationService.getoperationoutlineinfor(Integer.parseInt(string));
				OperationOutline oi = new OperationOutline();
				oi.copy(operationOutline);
				//学期
				oi.setSchoolTerm(t);
				oi.setId(null);
				//课程
				oi.setSchoolCourseInfoByClassId(operationOutline.getSchoolCourseInfoByClassId());
				oi.setSchoolCourseInfoByFirstCourses(operationOutline.getSchoolCourseInfoByFirstCourses());
				oi.setSchoolCourseInfoByFollowUpCourses(operationOutline.getSchoolCourseInfoByFollowUpCourses());
				//理论学时
				oi.setTheoryCourseHour(operationOutline.getTheoryCourseHour());
				//实验学时
				oi.setExperimentCourseHour(operationOutline.getExperimentCourseHour());
				//实验中心
				oi.setLabCenter(operationOutline.getLabCenter());
				//其他教师
				oi.setExtraTeacher(operationOutline.getExtraTeacher());

				//面向专业
				Set<SchoolMajor> sy = operationOutline.getSchoolMajors();
				Set<SchoolMajor> sys =new HashSet<>();
				for (SchoolMajor s : sy){
					String syid = s.getMajorNumber();
					SchoolMajor sy12 = schoolMajorDAO.findSchoolMajorByPrimaryKey(syid);
					sys.add(sy12);
				}
				oi.setSchoolMajors(sys);
				//专业方向
				Set<CDictionary> cd = operationOutline.getCDictionarys();
				Set<CDictionary> cds = new HashSet<>();
				for(CDictionary c:cd){
					Integer cdid = c.getId();
					CDictionary cdi = cDictionaryDAO.findCDictionaryById(cdid);
					cds.add(cdi);
				}
				oi.setCDictionarys(cds);
				//教师
				Set<User> teacher = operationOutline.getOperationUser();
				Set<User> ts = new HashSet<>();
				for (User tea:teacher){
					String un = tea.getUsername();
					User te = userDAO.findUserByUsername(un);
					ts.add(te);
				}
				oi.setOperationUser(ts);

				oi.setUser(shareService.getUser());//导入人就是创建人

				OperationOutline op=operationOutlineDAO.store(oi);
				operationOutlineDAO.flush();

				List<OperationOutlineCourse> operationOutlineCourses=operationOutlineCourseDAO.executeQuery("select m from OperationOutlineCourse m where m.operationOutline.id ="+operationOutline.getId()+"");
				for(OperationOutlineCourse o:operationOutlineCourses){
					OperationOutlineCourse oc=new OperationOutlineCourse();
					oc.copy(o);
					oc.setOperationOutline(op);
					oc.setcDictionary(o.getcDictionary());
					oc.setId(null);
					operationOutlineCourseDAO.store(oc);
					operationOutlineCourseDAO.flush();
				}
			}

		}
		return "redirect:/operation/experimentalmanagement?currpage=1";//导入后
	}

	/**
	 * Description 编辑实验项目
	 * @param request
	 * @param itemId
	 * @param acno
	 * @return
	 * @author 陈乐为 2018-8-25
	 */
	@RequestMapping("/editOperationItemLims")
	public ModelAndView editOperationItemLims(HttpServletRequest request,@RequestParam int itemId, @ModelAttribute("selected_academy") String acno,int page){
		ModelAndView mav = new ModelAndView();
		// 对象
		OperationItem operationItem = new OperationItem();
		if(itemId != -1) {// 编辑
			operationItem = operationService.findOperationItemByPrimaryKey(itemId);
		}
		mav.addObject("operationItem", operationItem);

		if(operationItem.getLabRooms().size() > 0) {
			mav.addObject("labRoomAll", operationItem.getLabRooms());
		}else if(operationItem.getLabRoom() != null){
			List<LabRoom> labRoomAll = new ArrayList<>();
			labRoomAll.add(operationItem.getLabRoom());
			mav.addObject("labRoomAll", labRoomAll);
		}

		mav.addObject("itemId", itemId);
		// 选填项
		mav.addObject("labProjectMain", shareService.getCDictionaryData("category_operation_item_main"));  //实验类别
		mav.addObject("labProjectApp", shareService.getCDictionaryData("category_operation_item_app"));  //实验类型
		mav.addObject("labProjectNature", shareService.getCDictionaryData("category_operation_item_nature"));  //实验性质
		mav.addObject("labProjectStudent", shareService.getCDictionaryData("category_operation_item_student"));  //实验者类型
		mav.addObject("labProjectChange", shareService.getCDictionaryData("status_operation_item_change"));  //变动状态
		mav.addObject("labProjectPublic", shareService.getCDictionaryData("category_operation_item_public"));  //开放实验
		mav.addObject("labProjectRewardLevel", shareService.getCDictionaryData("category_operation_item_reward_level"));  //获奖等级
		mav.addObject("labProjectRequire", shareService.getCDictionaryData("category_operation_item_require"));  //实验要求
		mav.addObject("labProjectGuideBook", shareService.getCDictionaryData("category_operation_item_guide_book"));  //实验指导书
		mav.addObject("schoolTerms", shareService.findAllSchoolTerm());  //学期
		LabRoom labRoom = new LabRoom();
		SchoolCourseInfo schoolCourseInfo = new SchoolCourseInfo();
		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
		if(academy!=null && academy.getAcademyNumber()!=null) {
			mav.addObject("schoolAcademy", academy);
			schoolCourseInfo.setAcademyNumber(academy.getAcademyNumber());
		}
		mav.addObject("labRooms", labRoomService.findLabRoomBySchoolAcademy(acno));  //实验室数据
		mav.addObject("subjects", systemService.getAllSystemSubject12(1, -1));  //学科数据
//		mav.addObject("schoolCourseInfos", schoolCourseInfoService.getCourseInfoByQuery(schoolCourseInfo, -1, 1, -1));  //课程数据
		//面向专业
		List<SchoolMajor> allMajorList = systemService.getAllSchoolMajor(1, -1);
		String majors=operationItem.getLpMajorFit();
		if(majors!=null&&!majors.equals("")){
			String[] majorArray=majors.split(",");
			for (String string : majorArray) {
				SchoolMajor major = systemService.findSchoolMajorByNumber(string.replaceAll("\\D", ""));//去除所有非数字的值,针对导入实验室项目
				if (major!=null) {
					allMajorList.remove(major);
				}
			}
		}
		mav.addObject("majors", allMajorList);  //所有专业（排除已选）
		List<User> users=shareService.findUsersByQuery("TEACHER", acno);
		mav.addObject("users", users);

		mav.addObject("listItemDevice", operationService.getItemDeviceByItem(itemId, null, 1, -1));

		mav.addObject("assets", assetDAO.findAllAssets()); // 耗材选项
		List<MaterialKindDTO> materialKindDTOList=materialService.findAllAssetClassificationList();
		Map<Integer, String> materialKindMap = new HashMap<>();
		for(MaterialKindDTO m: materialKindDTOList){
			materialKindMap.put(m.getId(), m.getCname());
		}
		mav.addObject("materialKindMap", materialKindMap);
		mav.addObject("itemAssets", new ItemAssets());
		mav.addObject("listItemMaterialRecord", operationService.getItemMaterialRecordByItem(itemId));//实验材料 贺子龙新增
		mav.addObject("categoryMaterialRecordMain", shareService.getCDictionaryData("category_operation_item_material_record_main"));
		mav.addObject("operationItemMaterialRecord", new OperationItemMaterialRecord());
        mav.addObject("page",page);
		mav.setViewName("operation/editOperationItemLims.jsp");

//		String ip = shareService.getIpAddr(request);
		//保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
		//tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id 0-查看 -1--新建
		//action:  0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核查看 6 保存 7 审核编辑后保存 8 导入 9 审核结果
//		systemLogService.saveOperationItemLog(ip, 0, 1, itemId);
		return mav;
	}

	/**
	 * Description 获取课程库信息
	 * @return
	 * @author 陈乐为 2018-11-30
	 */
	@ResponseBody
	@RequestMapping("/getAllCourseInfos")
	public Map<String, String> getSystemRoomByBuildNumber(@ModelAttribute("selected_academy") String acno){
		Map<String, String> map = new HashMap<String, String>();
		String sql = "select s from SchoolCourseInfo s where 1=1";
		if(acno!=null&&!"".equals(acno)){
			sql += " and s.academyNumber='"+acno+"'";
		}
		List<SchoolCourseInfo> courses = schoolCourseInfoDAO.executeQuery(sql, 0, -1);
		String sysr = "<option value=''>请选择</option>";
		for(SchoolCourseInfo courseInfo : courses) {
			sysr += "<option value="+courseInfo.getCourseNumber()+">["+courseInfo.getCourseNumber()+"]"+courseInfo.getCourseName()+"</option>";
		}
		map.put("courseValue", shareService.htmlEncode(sysr));
		return map;
	}

	/**
	 * Description 项目的备选设备
	 * @param request
	 * @param itemId
	 * @param currpage
	 * @return
	 * @author 陈乐为 2018-11-30
	 */
	@RequestMapping("/listItemDevices")
	public ModelAndView listItemDevices(HttpServletRequest request,@RequestParam int itemId, int currpage) {
		ModelAndView mav = new ModelAndView();
		int pagesize = 20;
		StringBuffer hql = new StringBuffer("select c from LabRoomDevice c where 1=1");
		StringBuffer hqlc = new StringBuffer("select count(c) from LabRoomDevice c where 1=1");
		if(!EmptyUtil.isStringEmpty(request.getParameter("param"))) {
			hql.append(" and (c.labRoom.labRoomName like '%"+ request.getParameter("param")
					+"%' or c.schoolDevice.deviceName like '%"+ request.getParameter("param") +"%')");
			hqlc.append(" and (c.labRoom.labRoomName like '%"+ request.getParameter("param")
					+"%' or c.schoolDevice.deviceName like '%"+ request.getParameter("param") +"%')");
		}
		int totalRecords = ((Long) labRoomDeviceDAO.createQuerySingleResult(hqlc.toString()).getSingleResult()).intValue();
		List<LabRoomDevice> labRoomDevices = labRoomDeviceDAO.executeQuery(hql.toString(), (currpage-1)*pagesize, pagesize);
		mav.addObject("labRoomDevices", labRoomDevices);
		mav.addObject("pageModel", shareService.getPage(currpage, pagesize, totalRecords));

		mav.addObject("itemId", itemId);
		mav.setViewName("operation/listItemDevices.jsp");
		return mav;
	}

	/**
	 * Description 实验项目管理列表
	 * @param request
	 * @param currpage
	 * @param status
	 * @param orderBy
	 * @param operationItem
	 * @param acno
	 * @return
	 * @author 陈乐为 2018-8-25
	 */
	@RequestMapping("/listOperationItemLims")
	public ModelAndView listOperationItemLims(HttpServletRequest request,@RequestParam int currpage, int status, int orderBy, @ModelAttribute OperationItem operationItem, @ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView();
		User currUser = shareService.getUserDetail();  //当前登录人
		if(status == 1) {// 我的项目
			operationItem.setUserByLpCreateUser(currUser);
		}else if (status==2) {// 我的审核
			if(EmptyUtil.isObjectEmpty(operationItem) || EmptyUtil.isObjectEmpty(operationItem.getCDictionaryByLpStatusCheck())) {
				operationItem.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "2"));
			}
		}
		// 查询参数
		if(EmptyUtil.isObjectEmpty(operationItem) || EmptyUtil.isObjectEmpty(operationItem.getSchoolTerm())) {
			operationItem.setSchoolTerm(shareService.getBelongsSchoolTerm(Calendar.getInstance()));
		}
		int termId=0;
		if(operationItem!=null&&operationItem.getSchoolTerm()!=null&&operationItem.getSchoolTerm().getId()!=null){
			termId=operationItem.getSchoolTerm().getId();
			mav.addObject("termId",termId);
		}else if(operationItem.getSchoolTerm().getId().equals(-1)){
			mav.addObject("termId",-1);
		}else {
			termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
			mav.addObject("termId",termId);
		}
		// 项目所处阶段
		List<CDictionary> cDictionaries = shareService.getCDictionaryData("status_operation_item_check");
		mav.addObject("cDictionaries", cDictionaries);
		// 分页参数
		int pageSize = 60;
		int totalRecords = operationService.findOperationItemCountForLims(operationItem, acno);
		List<OperationItem> itemList = operationService.findOperationItemForLims(operationItem, acno, currpage, pageSize);
		// 查询条件
		if(acno!=null&&!acno.equals("-1")) {
			mav.addObject("schoolAcademy", shareService.findSchoolAcademyByPrimaryKey(acno));
		}else {
			mav.addObject("schoolAcademy", currUser.getSchoolAcademy());
		}
		mav.addObject("listOperationItem", itemList);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("status", status);
		mav.addObject("orderBy", orderBy);
		mav.addObject("users",operationService.getsome());
		mav.addObject("schoolTerms", shareService.findAllSchoolTerm());  //所有学期
		mav.addObject("schoolCourseInfos",operationService.getCourse(acno));
		mav.addObject("currUser", currUser);  //当前登录用户
		mav.addObject("operationItem1", new OperationItem());  //用于设置项目编号
		mav.addObject("draft", shareService.getCDictionaryByCategory("status_operation_item_check", "1"));  // 草稿
		mav.addObject("toCheck", shareService.getCDictionaryByCategory("status_operation_item_check", "2"));  // 审核中
		mav.addObject("checkYes", shareService.getCDictionaryByCategory("status_operation_item_check", "3"));  // 审核通过
		mav.addObject("checkNo", shareService.getCDictionaryByCategory("status_operation_item_check", "4"));  //审核拒绝
		//获取当前学期
		SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		mav.addObject("schoolTerm", schoolTerm);
		//决定升序还是降序
		boolean asc=true;
		if (orderBy<10) {
			asc=false;
		}
		mav.addObject("asc", asc);
		mav.addObject("page", currpage);

		// 获取当前审核人
		if(itemList.size() > 0) {
			Map<Integer, String> idAndAuth = new HashMap<>();
			Map<Integer, Integer> idAndLevel = new HashMap<>();
			Map<String, String> paramsGetCurr = new HashMap<>();
			String businessType = "OperationItem";
			paramsGetCurr.put("businessType", pConfig.PROJECT_NAME + businessType);
			StringBuilder sb = new StringBuilder();
			for (OperationItem oi : itemList) {
				sb.append(oi.getId().toString()).append(",");
			}
			paramsGetCurr.put("businessAppUid", sb.toString().substring(0, sb.length() - 1));
			String getCurrStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", paramsGetCurr);
			System.out.println(getCurrStr);
			JSONObject getCurrJsonObject = JSONObject.parseObject(getCurrStr);
			if ("success".equals(getCurrJsonObject.getString("status"))) {
				JSONArray getCurrJsonArray = getCurrJsonObject.getJSONArray("data");
				if (getCurrJsonArray != null && getCurrJsonArray.size() != 0) {
					for (int i = 0; i < getCurrJsonArray.size(); i++) {
						JSONObject temp = getCurrJsonArray.getJSONObject(i);
						String authName = "ROLE_" + temp.getString("result");
						Integer opId = temp.getIntValue("businessAppId");
						Integer level = temp.getIntValue("level");
						idAndAuth.put(opId, authName);
						idAndLevel.put(opId, level);
					}
				}
			}
			mav.addObject("idAndAuth", idAndAuth);
			mav.addObject("idAndLevel", idAndLevel);


			Map<Integer, String> auditShow = new HashMap<>();
			// 获取所有审核状态
			for (OperationItem oi : itemList) {
				Map<String, String> allAuditStateParams = new HashMap<>();
				allAuditStateParams.put("businessType", pConfig.PROJECT_NAME + businessType);
				allAuditStateParams.put("businessAppUid", oi.getId().toString());
				allAuditStateParams.put("businessUid", "-1");
				String allAuditStateStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessLevelStatus", allAuditStateParams);
				JSONObject allAuditStateJSON = JSONObject.parseObject(allAuditStateStr);
				String htmlStr = "";
				if (!"fail".equals(allAuditStateJSON.getString("status"))) {
					JSONArray allAuditStateJSONArray = allAuditStateJSON.getJSONArray("data");
					if (allAuditStateJSONArray != null && allAuditStateJSONArray.size() != 0) {
						for (int j = 0; j < allAuditStateJSONArray.size(); j++) {
							JSONObject o = allAuditStateJSONArray.getJSONObject(j);
							User auditUser = null;
							if (o.getString("auditUser") != null) {
								htmlStr += "<span style='color: black";
								auditUser = userDAO.findUserByUsername(o.getString("auditUser"));
							} else {
								htmlStr += "<span style='color: gray";
							}
							htmlStr += "'>";
							String authCName = authorityDAO.findAuthorityByAuthorityName(o.getString("authName")).iterator().next().getCname();
							htmlStr += authCName + " ";
							htmlStr += auditUser == null ? "" : auditUser.getCname() + " ";
							htmlStr += o.getString("result");
							htmlStr += "</span><br>";
						}
					}
				}
				auditShow.put(oi.getId(), htmlStr);
				mav.addObject("auditShow", auditShow);
			}
		}

		mav.setViewName("operation/listOperationItemLims.jsp");
		return mav;
	}

	/**
	 * Description 提交实验项目，保存指定审核人
	 * @param request
	 * @param operationItem
	 * @return
	 * @author 陈乐为 2018-8-25
	 */
	@RequestMapping("/submitOperationItemLims")
	public String submitOperationItemLims(HttpServletRequest request,@ModelAttribute OperationItem operationItem,int page, @ModelAttribute("selected_academy") String acno){
		OperationItem oItem = new OperationItem();
		oItem = operationService.submitOperationItem(operationItem, acno);
		// 保存审核初始数据
//		Map<String, String> params = new HashMap<>();
//		params.put("businessUid", oItem.getId().toString());
//		params.put("businessType", pConfig.PROJECT_NAME + "OperationItem");
//		params.put("businessAppUid", oItem.getId().toString());
//		String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveInitBusinessAuditStatus", params);
//		JSONObject jsonObject = JSON.parseObject(s);
//		String status = jsonObject.getString("status");
//		if(!"success".equals(status)){
//			return "fail";
//		}
		//提交完成后向审核人发送消息
//		Message message= new Message();
//		Calendar date=Calendar.getInstance();
//		message.setSendUser(shareService.getUserDetail().getCname());
//		message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
//		message.setCond(0);
//		message.setTitle(CommonConstantInterface.STR_OPERATIONITEM_TITLE);
//		String content="申请成功，等待审核";
//		content+="<a onclick='changeMessage(this)' href='../operation/viewOperationItem?operationItemId=";
//		content+=oItem.getId();
//		content+="&&flag=1&status=2'>点击查看</a>";
//		message.setContent(content);
//		message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
//		message.setCreateTime(date);
//		message.setUsername(oItem.getUserByLpCheckUser().getUsername());
//		message=messageDAO.store(message);
//		message.setTage(2);
		String ip = shareService.getIpAddr(request);
		//保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
		//tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id 0-查看 -1--新建
		//action:  0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核查看 6 保存 7 审核编辑后保存 8 导入 9 审核结果
		systemLogService.saveOperationItemLog(ip, 0, 4, oItem.getId());
		return "redirect:/operation/listOperationItemLims?currpage="+page+"&status=1&orderBy=9";
	}

	/**
	 * Description 保存项目
	 * @param request
	 * @param operationItem
	 * @param acno
	 * @return
	 * @author 陈乐为 2018-8-25
	 */
	@RequestMapping("/saveOperationItemLims")
	public ModelAndView saveOperationItemLims(HttpServletRequest request,@ModelAttribute OperationItem operationItem,
										  @ModelAttribute("selected_academy") String acno,int page){
		ModelAndView mav=new ModelAndView();
		if(operationItem == null || operationItem.getCDictionaryByLpStatusCheck() == null ||
				operationItem.getCDictionaryByLpStatusCheck().getId() == null) {
			operationItem.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "1"));  //草稿状态
		}
		if(operationItem == null || operationItem.getUserByLpCheckUser() == null ||
				operationItem.getUserByLpCheckUser().getUsername() == "" || operationItem.getUserByLpCheckUser().getUsername() == null) {
			operationItem.setUserByLpCheckUser(null);
		}
		operationItem.setUserByLpCreateUser(shareService.getUserDetail());  //当前登录人
		operationItem.setCreatedAt(Calendar.getInstance());//设置为当前时间
		//保存实验室
		if(!net.luxunsh.util.EmptyUtil.isStringEmpty(request.getParameter("labRoomId"))){
			String[] labRoomId = request.getParameterValues("labRoomId");
			HashSet<LabRoom> lab = new HashSet<LabRoom>();
			for(String id:labRoomId){
				LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(Integer.parseInt(id));
				lab.add(labRoom);
			}
			operationItem.setLabRooms(lab);
		}
		//获取所属学院
		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
		Set<LabCenter> labCenters = academy.getLabCenters();
		for (LabCenter lc:labCenters){
			operationItem.setLabCenter(lc);
			break;
		}
		operationItem = operationService.saveOperationItem(operationItem);
		String ip = shareService.getIpAddr(request);
		//保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
		//tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id 0-查看 -1--新建
		//action:  0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核查看 6 保存 7 审核编辑后保存 8 导入 9 审核结果
		int tag = 0;
		int action = 6;
		systemLogService.saveOperationItemLog(ip, tag, action, operationItem.getId());



		mav.setViewName("redirect:/operation/editOperationItemLims?itemId="+operationItem.getId()+"&page="+page);
		return mav;

	}

	/**
	 * Description 保存实验项目材料使用记录
	 * @param operationItemMaterialRecord
	 * @param status 重定向参数 1：我的项目--编辑，其他：审核编辑
	 * @return
	 * @author 陈乐为 2018-8-25
	 */
	@RequestMapping("/saveItemMaterialRecordLims")
	public String saveItemMaterialRecordLims(@ModelAttribute OperationItemMaterialRecord operationItemMaterialRecord, int status,int page){
		operationService.saveItemMaterialRecord(operationItemMaterialRecord);
		if(status == 1) {
			return "redirect:/operation/editOperationItemLims?itemId="+operationItemMaterialRecord.getOperationItem().getId()+"&page="+page;
		}else {
			return "redirect:/operation/viewOperationItemLims/"+status+"/"+operationItemMaterialRecord.getOperationItem().getId();
		}
	}

	/**
	 * Description 保存实验项目设备
	 * @param itemId
	 * @param ids
	 * @return
	 * @author 陈乐为 2018-8-25
	 */
	@RequestMapping("/saveItemDeviceLims")
	@ResponseBody
	public String saveItemDeviceLims(@RequestParam int itemId, String ids){
		operationService.saveItemDeviceLims(itemId, ids);

		return "success";
	}

	/**
	 * Description 删除项目关联设备
	 * @param itemDeviceId
	 * @param itemId
	 * @param status 重定向参数  1：我的项目，其他：项目详情
	 * @return
	 */
	@RequestMapping("/deleteItemDeviceLims")
	public String deleteItemDeviceLims(@RequestParam int itemDeviceId, int itemId, int status,int page){
		operationService.deleteItemDevice(itemDeviceId);
		if(status == 1) {// 我的项目
			return "redirect:/operation/editOperationItemLims?itemId=" + itemId+"&page="+page;
		}else {
			return "redirect:/operation/viewOperationItemLims/"+status+"/"+itemId;
		}
	}

	/**
	 * Description 删除实验项目材料使用记录
	 * @param mrId
	 * @param itemId
	 * @param status 重定向参数
	 * @return
	 * @author 陈乐为 2018-8-25
	 */
	@RequestMapping("/deleteItemMaterialLims")
	public String deleteItemMaterialLims(@RequestParam int mrId, int itemId, int status,int page){
		operationService.deleteItemMaterialRecord(mrId);
		if(status == 1) {// 我的项目
			return "redirect:/operation/editOperationItemLims?itemId="+itemId+"&page="+page;
		}else {// 查看详情
			return "redirect:/operation/viewOperationItemLims/"+status+"/"+itemId;
		}
	}

	/**
	 * Description 打印项目卡片
	 * @param idKey
	 * @param acno
	 * @return
	 * @author 陈乐为 2018-8-25
	 */
	@RequestMapping("/operationItemPrint")
	@ResponseBody
	public Map<String,Object> operationItemPrint(@RequestParam int idKey, @ModelAttribute("selected_academy") String acno){
		Map<String,Object> map = new HashMap<String, Object>();
		LabRoom labRoom = new LabRoom();
		SchoolCourseInfo schoolCourseInfo = new SchoolCourseInfo();
		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
		if(academy!=null && academy.getAcademyNumber()!=null)
		{
			map.put("schoolAcademy", academy.getAcademyName());
			schoolCourseInfo.setAcademyNumber(academy.getAcademyNumber());
		}else {
			map.put("schoolAcademy", shareService.getUserDetail().getSchoolAcademy().getAcademyName());
		}

		OperationItem operationItem = operationService.findOperationItemByPrimaryKey(idKey);
		//面向专业
		List<SchoolMajor>  majorList = new ArrayList<SchoolMajor>();
		List<SchoolMajor> allMajorList = systemService.getAllSchoolMajor(1, -1);
		String majors=operationItem.getLpMajorFit();
		if(majors!=null&&!majors.equals("")){
			String[] majorArray=majors.split(",");
			for (String string : majorArray) {
				SchoolMajor major = systemService.findSchoolMajorByNumber(string.replaceAll("\\D", ""));//去除所有非数字的值,针对导入实验室项目
				if (major!=null) {
					majorList.add(major);
					allMajorList.remove(major);
				}
			}
		}
		String majorFit = "";
		for(int i=0; i<majorList.size(); i++) {
			majorFit += majorList.get(i).getMajorName() + " ";
		}

		String majorFitNum = "";
		for(int i=0; i<majorList.size(); i++) {
			majorFitNum += majorList.get(i).getMajorNumber() + " ";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String format = sdf.format(new Date());


		map.put("totalCodes", 1);
		map.put("year", format.substring(0, 4));
		map.put("month", format.substring(4, 6));
		map.put("day", format.substring(6, 8));
		map.put("lpCodeCustom", operationItem.getLpCodeCustom());
		map.put("lpName", operationItem.getLpName());
		map.put("subject12Num", operationItem.getSystemSubject12().getSNumber());
		map.put("subject12Name", operationItem.getSystemSubject12().getSName());
		map.put("labRoomName", operationItem.getLabRoom().getLabRoomName());
		map.put("labRoomNum", operationItem.getLabRoom().getLabRoomNumber());
		map.put("majorName", operationItem.getSchoolMajor().getMajorName());
		map.put("majorNum", operationItem.getSchoolMajor().getMajorNumber());
		map.put("courseName", operationItem.getSchoolCourseInfo().getCourseName());
		map.put("courseNum", operationItem.getSchoolCourseInfo().getCourseNumber());
		map.put("totalPeoples", operationItem.getLpYearsPeopleNumberPlan());
		map.put("totalHours1", operationItem.getLpDepartmentHoursTotal());
		map.put("totalHours2", operationItem.getLpDepartmentHours());
		map.put("totalHours3", operationItem.getSchoolCourseInfo().getTotalHours());
		map.put("studentNum", operationItem.getLpStudentNumber());
		map.put("setNum", operationItem.getLpSetNumber());
		map.put("groupNum", operationItem.getLpStudentNumberGroup());
		map.put("cycNum", operationItem.getLpCycleNumber());
		map.put("nature", operationItem.getCDictionaryByLpCategoryNature().getCNumber());
		map.put("main", operationItem.getCDictionaryByLpCategoryMain().getCNumber());
		map.put("app", operationItem.getCDictionaryByLpCategoryApp().getCNumber());
		map.put("operator", operationItem.getCDictionaryByLpCategoryStudent().getCNumber());
		map.put("changeStatus", operationItem.getCDictionaryByLpStatusChange().getCNumber());
		map.put("pub", operationItem.getCDictionaryByLpCategoryPublic().getCNumber());
		map.put("reward", operationItem.getCDictionaryByLpCategoryRewardLevel().getCNumber());
		map.put("require", operationItem.getCDictionaryByLpCategoryRequire().getCNumber());
		map.put("majorFit", majorFit);
		map.put("majorFitNum", majorFitNum);
		map.put("introduction", operationItem.getLpIntroduction());
		map.put("guideBook", operationItem.getCDictionaryByLpCategoryGuideBook().getCNumber());
		map.put("guideBookTitle", operationItem.getLpGuideBookTitle());
		map.put("guideBookAuthor", operationItem.getLpGuideBookAuthor());
		map.put("assessment", operationItem.getLpAssessmentMethods());
		String teacherSpeaker = "";
		if(operationItem.getUserByLpTeacherSpeakerId() != null) {
			teacherSpeaker = operationItem.getUserByLpTeacherSpeakerId().getUsername()+" "+ operationItem.getUserByLpTeacherSpeakerId().getCname();
		}
		map.put("teacherSpeaker", teacherSpeaker);
		String teacherAssistant = "";
		if(operationItem.getUserByLpTeacherAssistantId() != null) {
			teacherAssistant = operationItem.getUserByLpTeacherAssistantId().getUsername() +" "+operationItem.getUserByLpTeacherAssistantId().getCname();
		}
		map.put("teacherAssistant", teacherAssistant);
		map.put("preparation", operationItem.getLpPreparation());

		return map;
	}

	/**
	 * Description 项目审核-一级
	 * @param request
	 * @param status
	 * @param id
	 * @param acno
	 * @return
	 * @author 陈乐为 2018-8-26
	 */
	@RequestMapping(value = "/viewOperationItemLims/{status}/{id}", method = RequestMethod.GET)
	public ModelAndView viewOperationItemRest(HttpServletRequest request,@PathVariable int status,
											  @PathVariable int id, @ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView();
		StringBuffer majorStr = new StringBuffer();
		OperationItem operationItem = operationService.findOperationItemByPrimaryKey(id);

		if(operationItem!=null&&operationItem.getLpMajorFit()!=null && !"".equals(operationItem.getLpMajorFit()))
		{
			String majorArr[] = operationItem.getLpMajorFit().split(",");
			for (String s : majorArr) {
				if (systemService.findSchoolMajorByNumber(s)!=null) {
					majorStr.append(systemService.findSchoolMajorByNumber(s).getMajorName()+"["+s+"],");
				}
			}
			if(majorStr.length() > 0) {
				majorStr.deleteCharAt(majorStr.length()-1);  //去掉最后一个逗号
			}
		}

		mav.addObject("operationItem", operationItem);
		mav.addObject("currUser", shareService.getUserDetail());  //当前登录人
		mav.addObject("majorStr", majorStr);  //面向专业
		mav.addObject("toCheck", shareService.getCDictionaryByCategory("status_operation_item_check", "2"));  // 审核中
		mav.addObject("status", status);
		//2015-09-24 10:10:45新增    仪器、材料
		mav.addObject("listItemDevice", operationService.getItemDeviceByItem(id, null, 1, -1));
		mav.addObject("listItemMaterialRecord", operationService.getItemMaterialRecordByItem(id));

		//审核人可以进行编辑
		mav.addObject("labProjectMain", shareService.getCDictionaryData("category_operation_item_main"));  //实验类别
		mav.addObject("labProjectApp", shareService.getCDictionaryData("category_operation_item_app"));  //实验类型
		mav.addObject("labProjectNature", shareService.getCDictionaryData("category_operation_item_nature"));  //实验性质
		mav.addObject("labProjectStudent", shareService.getCDictionaryData("category_operation_item_student"));  //实验者类型
		mav.addObject("labProjectChange", shareService.getCDictionaryData("status_operation_item_change"));  //变动状态
		mav.addObject("labProjectPublic", shareService.getCDictionaryData("category_operation_item_public"));  //开放实验
		mav.addObject("labProjectRewardLevel", shareService.getCDictionaryData("category_operation_item_reward_level"));  //获奖等级
		mav.addObject("labProjectRequire", shareService.getCDictionaryData("category_operation_item_require"));  //实验要求
		mav.addObject("labProjectGuideBook", shareService.getCDictionaryData("category_operation_item_guide_book"));  //实验指导书
		mav.addObject("schoolTerms", shareService.findAllSchoolTerm());  //学期
		mav.addObject("users", systemService.getAllUser(1, -1, shareService.getUser()));  //教师数据
		LabRoom labRoom = new LabRoom();
		mav.addObject("labRooms", labRoomService.findAllLabRoomByQuery(1, -1, labRoom));  //实验室数据
		SchoolCourseInfo schoolCourseInfo = new SchoolCourseInfo();
		mav.addObject("schoolCourseInfos", schoolCourseInfoService.getCourseInfoByQuery(schoolCourseInfo, -1, 1, -1));  //课程数据
		mav.addObject("subjects", systemService.getAllSystemSubject12(1, -1));  //学科数据
		//面向专业
		String majors=operationItem.getLpMajorFit();
		List<SchoolMajor>  majorList=new ArrayList<SchoolMajor>();
		List<SchoolMajor> allMajorList = systemService.getAllSchoolMajor(1, -1);
		if (majors!=null&&!majors.equals("")) {
			String[] majorArray=majors.split(",");
			for (String string : majorArray) {
				SchoolMajor major=systemService.findSchoolMajorByNumber(string);
				if (major!=null) {
					majorList.add(major);
					allMajorList.remove(major);
				}
			}
		}
		mav.addObject("majorList", majorList);	//已选专业
		mav.addObject("majors", allMajorList);  //所有专业（排除已选）
		//添加材料
		mav.addObject("operationItemMaterialRecord", new OperationItemMaterialRecord());
		mav.addObject("categoryMaterialRecordMain", shareService.getCDictionaryData("category_operation_item_material_record_main"));
		//添加设备
		if(operationItem!=null && operationItem.getLabRoom()!=null)
		{
			List<LabRoomDevice> allLabRoomDevice = labRoomDeviceService.findLabRoomDeviceByRoomId(operationItem.getLabRoom().getId());
			List<LabRoomDevice> existLabRoomDevice = new ArrayList<LabRoomDevice>();
			for (OperationItemDevice operationItemDevice : operationItem.getOperationItemDevices()) {
				existLabRoomDevice.add(operationItemDevice.getLabRoomDevice());
			}

			allLabRoomDevice.removeAll(existLabRoomDevice);  //去除已经添加的labRoomDevice
			mav.addObject("listLabRoomDevice", allLabRoomDevice);
		}
		mav.addObject("orderBy", 9);//仅传参用，防止我的消息--实验项目卡--审核--返回报404
		mav.setViewName("operation/viewOperationItemLims.jsp");

		boolean canAudit = false;

		// 获取当前审核人
		Map<String, String> paramsGetCurr = new HashMap<>();
		String businessType = "OperationItem";
		paramsGetCurr.put("businessType", pConfig.PROJECT_NAME + businessType);
		paramsGetCurr.put("businessAppUid", operationItem.getId().toString());
		String getCurrStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", paramsGetCurr);
		JSONObject getCurrJSON = JSONObject.parseObject(getCurrStr);
		if("success".equals(getCurrJSON.getString("status"))){
			JSONArray getCurrArray = getCurrJSON.getJSONArray("data");
			if(getCurrArray != null && getCurrArray.size() != 0){
				String authName = getCurrArray.getJSONObject(0).getString("result");
				SimpleGrantedAuthority sga = (SimpleGrantedAuthority) SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next();
				if(sga.getAuthority().equals("ROLE_" + authName)){
					canAudit = true;
				}
			}
		}

		mav.addObject("canAudit",canAudit);

		//前台状态传参
		String ip = shareService.getIpAddr(request);
		//保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
		//tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id 0-查看 -1--新建
		//action:  0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核查看 6 保存 7 审核编辑后保存 8 导入 9 审核结果
		systemLogService.saveOperationItemLog(ip, status, 5, id);
		return mav;
	}

	/**
	 * Description 实验项目审核结果保存
	 * @param request
	 * @param operationItemId
	 * @param result
	 * @return
	 * @author 陈乐为 2018-8-26
	 */
	@RequestMapping("/auditOperationItem")
	public String checkOperationItem(HttpServletRequest request,@RequestParam int operationItemId, int result){
		OperationItem operationItem = operationService.findOperationItemByPrimaryKey(operationItemId);
		String ip = shareService.getIpAddr(request);
		//保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
		//tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id 0-查看 -1--新建
		//action:  0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核查看 6 保存 7 审核编辑后保存 8 导入 9 审核结果
		systemLogService.saveOperationItemLog(ip, 1, 9, operationItemId);
		if(operationItem!=null)
		{
//			//审核完成后给申请人发送消息
//			Message message= new Message();
//			Calendar date=Calendar.getInstance();
//			message.setSendUser(shareService.getUserDetail().getCname());
//			message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
//			message.setCond(0);
//			String content="";
//			if(result==0)  //审核拒绝
//			{
//				operationItem.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "4"));
//				message.setTitle(CommonConstantInterface.STR_FLAG_DISAGREE);
//				content="您的审核未通过";
//			}
//			else if(result==1)  //审核通过
//			{
//				operationItem.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "3"));
//				message.setTitle(CommonConstantInterface.STR_FLAG_AGREE);
//				content="您的审核已通过";
//			}
//			content+="<a href='../operation/viewOperationItemLims/2/";
//
//			content+=operationItemId;
//			content+="'>点击查看</a>";
//			message.setContent(content);
//			message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
//			message.setCreateTime(date);
//			if (operationItem.getUserByLpCreateUser()!=null) {
//				message.setUsername(operationItem.getUserByLpCreateUser().getUsername());//把消息发给项目卡的申请人
//			}
//			message=messageDAO.store(message);
//
//			String lpCodeCustom = operationService.getLpCodeCustom(operationItem.getId());
//			operationItem.setLpCodeCustom(lpCodeCustom);
//			operationService.saveOperationItem(operationItem);
			Map<String, String> params = new HashMap<>();
			String businessType = "OperationItem";
			params.put("businessType", pConfig.PROJECT_NAME + businessType);
			params.put("businessAppUid", String.valueOf(operationItemId));
			params.put("businessUid", "-1");
			params.put("result", result == 1 ? "pass" : "fail");
			params.put("info", "已审核");
			params.put("username", shareService.getUserDetail().getUsername());
			String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveBusinessLevelAudit", params);
			JSONObject jsonObject = JSONObject.parseObject(s);
			if(!"success".equals(jsonObject.getString("status"))){
				return "redirect:/operation/listOperationItemLims?currpage=1&&status=2&orderBy=9";
			}
			JSONObject resultJSON = jsonObject.getJSONObject("data");
			if(resultJSON.containsKey(-1)){
				CDictionary cd =
						shareService.getCDictionaryByCategory("status_operation_item_check", "3");
				operationItem.setCDictionaryByLpStatusCheck(cd);
			}else if(resultJSON.containsKey(0)){
				CDictionary cd =
						shareService.getCDictionaryByCategory("status_operation_item_check", "4");
				operationItem.setCDictionaryByLpStatusCheck(cd);
			}else{
				CDictionary cd =
						shareService.getCDictionaryByCategory("status_operation_item_check", "2");
				operationItem.setCDictionaryByLpStatusCheck(cd);
			}
			operationItemDAO.store(operationItem);
			operationItemDAO.flush();
		}
		return "redirect:/operation/listOperationItemLims?currpage=1&&status=2&orderBy=9";
	}

	/**
	 * Description 删除项目
	 * @param request
	 * @param operationItemId
	 * @param status
	 * @return
	 * @author 陈乐为 2018-8-26
	 */
	@RequestMapping("/deleteOperationItemLims")
	public String deleteOperationItemLims(HttpServletRequest request,@RequestParam int operationItemId, int status,int page){
		String ip = shareService.getIpAddr(request);
		//保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
		//tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id 0-查看 -1--新建
		//action:  0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核查看 6 保存 7 审核编辑后保存 8 导入 9 审核结果
		systemLogService.saveOperationItemLog(ip, status, 3, operationItemId);
		operationService.deleteOperationItem(operationItemId);
		return "redirect:/operation/listOperationItemLims?currpage="+page+"&status="+status+"&orderBy=9";
	}

	/*************************************************************************************
	 * Description:新建实验大纲-新建实验项目AJAX
	 *
	 * @author: 杨新蔚
	 * @date: 2018/8/27
	 *************************************************************************************/
	@RequestMapping("/saveOperationItemFromNewOperation")
	@ResponseBody
	public Map<String,Object> saveOperationItemFromNewOperation(HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {
		Map<String, Object> map = new HashMap<String, Object>();
		//新建大纲入口-新建项目为审核通过状态
		OperationItem operationItem=new OperationItem();
		//课题名称
		operationItem.setLpName(request.getParameter("lpName"));
		//实验学时
		operationItem.setLpDepartmentHours(Integer.parseInt(request.getParameter("lpDepartmentHours")));
		//所属课程
		operationItem.setSchoolCourseInfo(schoolCourseInfoDAO.findSchoolCourseInfoByCourseNumber(request.getParameter("schoolCourseInfo")));
		//面向专业
		operationItem.setLpMajorFit(request.getParameter("lpMajorFit"));
		//所属学期
		int m=Integer.parseInt(request.getParameter("searchTerm"));
		operationItem.setSchoolTerm(schoolTermDAO.findSchoolTermById(Integer.parseInt(request.getParameter("searchTerm"))));
		operationItem.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "3"));
		operationItem.setUserByLpCreateUser(shareService.getUserDetail());  //当前登录人
		operationItem.setCreatedAt(Calendar.getInstance());//设置为当前时间
		//保存当前所处的实验类型
//		if(cid != -1)
//		{
//			operationItem.setLabCenter(labCenterService.findLabCenterByPrimaryKey(cid));
//		}
		operationItemDAO.store(operationItem);
		map.put("operationItemLpName",operationItem.getLpName());
		String ip = shareService.getIpAddr(request);
		//保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
		//tag：子模块标志位  1=我的项目 2=我的审核 3=全部项目 4=新建大纲  id：项目卡的id 0-查看 -1--新建
		//action:  0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核查看 6 保存 7 审核编辑后保存 8 导入 9 审核结果
		systemLogService.saveOperationItemLog(ip, 4, 0,-1);
		return map;
	}

	/********************************************************************************
	 * Description: 实验项目模块{某一课程下的实验项目列表页面}
	 * @author: 戴昊宇
	 * @date: 2017-08-26
	 *********************************************************************************/
	@RequestMapping("/listOperationItemByCourse")
	public ModelAndView listOperationItemByCourse(@RequestParam String courseNumber){
		ModelAndView mav = new ModelAndView();
		// 通过课程找到实验项目
		mav.addObject("operationItems", operationService.findOperationItemByCourseNumber(courseNumber));
		mav.setViewName("operation/listOperationItemByCourse.jsp");
		return mav;
	}

	/**
	 * Description 实验项目审核人选择页面
	 * @param idKey
	 * @param acno
	 * @param page
	 * @return
	 * @author 陈乐为 2018-11-16
	 */
	@RequestMapping("/choseAuditUser")
	public ModelAndView getAuditUser(@RequestParam Integer idKey, @ModelAttribute("selected_academy") String acno,int page) {
		ModelAndView mav = new ModelAndView();
		// 项目
		OperationItem item = operationService.findOperationItemByPrimaryKey(idKey);
		mav.addObject("operationItem", item);
		// 学院
		User user = shareService.getUserDetail();
		String academyNum = "";
		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
		if(academy!=null && academy.getAcademyNumber()!=null) {
			academyNum = academy.getAcademyNumber();
		}else if(user.getSchoolAcademy()!=null && user.getSchoolAcademy().getAcademyNumber()!=null) {
			academyNum = shareService.getUserDetail().getSchoolAcademy().getAcademyNumber();
		}
		// 审核人备选名单集合
		List<User> auditorList = new ArrayList<>();
		// 从权限表中查找审核权限
		Map<String, String> params = new HashMap<>();
		params.put("businessUid", "");
		params.put("businessType", pConfig.PROJECT_NAME + "OperationItem");
		String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessAuditConfigs", params);
		JSONObject jsonObject = JSON.parseObject(s);
		Map auditConfigs = JSON.parseObject(jsonObject.getString("data"), Map.class);
		if (auditConfigs != null && auditConfigs.size() != 0) {
			for (int i = 0; i < auditConfigs.size(); i++) {
				// 获取权限
				String auth = auditConfigs.get(i + 1).toString().split(":")[0];
				// 获取权限对应人员
				List<User> auditors = operationService.getUserByQuery(academyNum, auth);
				auditorList.addAll(auditors);
			}
		}
		mav.addObject("auditorList", auditorList);
        mav.addObject("page",page);
		mav.setViewName("/operation/choseAuditUser.jsp");
		return mav;
	}
}
	
	
