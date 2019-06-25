package net.zjcclims.web.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.assets.*;
import net.gvsun.lims.service.assets.MaterialService;
import net.gvsun.lims.service.timetable.TimetableSelfCourseService;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.asset.AssetService;
import net.zjcclims.service.common.CStaticValueService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.device.LabRoomDeviceService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.operation.OperationService;
import net.zjcclims.service.system.SystemLogService;
import net.zjcclims.service.system.SystemService;
import net.zjcclims.service.timetable.OuterApplicationService;
import net.zjcclims.service.timetable.SchoolCourseInfoService;
import net.zjcclims.service.virtual.VirtualService;
import net.zjcclims.util.HttpClientUtil;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller("OpenOperationItemController")
@SessionAttributes("selected_academy")
@RequestMapping("/openOperationItem")
public class OpenOperationItemController<JsonResult> {

	@Autowired private ShareService shareService;
	@Autowired private OperationService operationService;
	@Autowired private PConfig pConfig;
	@Autowired private UserDAO userDAO;
	@Autowired private AuthorityDAO authorityDAO;
	@Autowired private LabRoomService labRoomService;
	@Autowired private SystemService systemService;
	@Autowired private OperationOutlineDAO operationOutlineDAO;
	@Autowired private SystemLogService systemLogService;
	@Autowired private OperationItemDAO operationItemDAO;
	@Autowired private CommonDocumentDAO commonDocumentDAO;
	@Autowired private AssetDAO assetDAO;
	@Autowired private AssetService assetService;
	@Autowired private ItemAssetsDAO itemAssetsDAO;
	@Autowired private MaterialService materialService;
	@Autowired private ItemLabUsersDAO itemLabUsersDAO;
	@Autowired private ItemOpenTeacherDAO itemOpenTeacherDAO;
	@Autowired private SchoolCourseInfoService schoolCourseInfoService;
	@Autowired private LabRoomDeviceService labRoomDeviceService;
	@Autowired private TimetableSelfCourseService timetableSelfCourseService;
	@Autowired private ItemPlanDAO itemPlanDAO;
	@Autowired private SchoolTermDAO schoolTermDAO;
	@Autowired private VirtualService virtualService;
	@Autowired private TimetableSelfCourseDAO timetableSelfCourseDAO;
	@Autowired private OuterApplicationService outerApplicationService;
	@Autowired private CStaticValueService cStaticValueService;
	@Autowired private SchoolWeekDAO schoolWeekDAO;
	@Autowired private TimetableBatchDAO timetableBatchDAO;
	@Autowired private TimetableAppointmentSameNumberDAO timetableAppointmentSameNumberDAO;
	@Autowired private SchoolCourseDAO schoolCourseDAO;

	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register // static // property // editors.
		binder.registerCustomEditor(Calendar.class,new org.skyway.spring.util.databinding.CustomCalendarEditor());
		binder.registerCustomEditor(byte[].class,new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
		binder.registerCustomEditor(java.math.BigDecimal.class,new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
		binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Integer.class, true));
		binder.registerCustomEditor(Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
		binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
		binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Long.class, true));
		binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Double.class, true));
	}

	@RequestMapping("/listOpenOperationItem")
	public ModelAndView listOpenOperationItem(HttpServletRequest request, @RequestParam int currpage, int status, int orderBy, @ModelAttribute OperationItem operationItem, @ModelAttribute("selected_academy") String acno){
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
		int totalRecords = operationService.findOperationItemCountForLims(operationItem, "-1");
		List<OperationItem> itemList = operationService.findOperationItemForLims(operationItem, "-1", currpage, pageSize);
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
		mav.addObject("schoolTerms", shareService.findAllSchoolTerms());  //所有学期
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
		mav.addObject("PROJECT_NAME", pConfig.PROJECT_NAME);

		mav.setViewName("openOperationItem/listOpenOperationItem.jsp");
		return mav;
	}

	/**
	 * Description 编辑实验项目
	 * @param request
	 * @param itemId
	 * @param acno
	 * @return
	 * @author 陈乐为 2018-8-25
	 */
	@RequestMapping("/editOpenOperationItem")
	public ModelAndView editOpenOperationItem(HttpServletRequest request,@RequestParam int itemId, @ModelAttribute("selected_academy") String acno,int page){
		ModelAndView mav = new ModelAndView();
		// 对象
		OperationItem operationItem = new OperationItem();
		if(itemId != -1) {// 编辑
			operationItem = operationService.findOperationItemByPrimaryKey(itemId);
		}
		mav.addObject("operationItem", operationItem);

		// 实验开发人
		if(itemId == -1){
			mav.addObject("creator", shareService.getUserDetail());
		}else{
			mav.addObject("creator", operationItem.getUserByLpCreateUser());
		}

		if(operationItem.getLabRooms().size() > 0) {
			mav.addObject("labRoomAll", operationItem.getLabRooms());
		}else if(operationItem.getLabRoom() != null){
			List<LabRoom> labRoomAll = new ArrayList<>();
			labRoomAll.add(operationItem.getLabRoom());
			mav.addObject("labRoomAll", labRoomAll);
		}
		Set<OperationOutline> operationOutlines = operationOutlineDAO.findAllOperationOutlines();
		mav.addObject("operationOutlines", operationOutlines);

		mav.addObject("itemId", itemId);
		// 选填项
		mav.addObject("labProjectMain", shareService.getCDictionaryData("category_operation_item_main"));  //实验类别
		mav.addObject("labProjectApp", shareService.getCDictionaryData("c_operation_item_app"));  //实验类型
		mav.addObject("labProjectNature", shareService.getCDictionaryData("c_operation_item_nature"));  //实验性质
		mav.addObject("labProjectStudent", shareService.getCDictionaryData("category_operation_item_student"));  //实验者类型
		mav.addObject("labProjectChange", shareService.getCDictionaryData("status_operation_item_change"));  //变动状态
		mav.addObject("labProjectPublic", shareService.getCDictionaryData("category_operation_item_public"));  //开放实验
		mav.addObject("labProjectRewardLevel", shareService.getCDictionaryData("category_operation_item_reward_level"));  //获奖等级
		mav.addObject("labProjectRequire", shareService.getCDictionaryData("category_operation_item_require"));  //实验要求
		mav.addObject("labProjectGuideBook", shareService.getCDictionaryData("category_operation_item_guide_book"));  //实验指导书

		mav.addObject("titles", shareService.getCDictionaryData("c_operation_item_title")); // 职称选项
		mav.addObject("minUnits", shareService.getCDictionaryData("c_operation_item_min_unit"));
		mav.addObject("minUnit", shareService.getCDictionaryByCategory("c_operation_item_min_unit", "1")); // 最小单位选项
		mav.addObject("academies", shareService.findAllSchoolAcademys());
		mav.addObject("grades", shareService.getCDictionaryData("c_operation_item_open_grade")); // 开放年级选项
		mav.addObject("terms", shareService.getCDictionaryData("c_operation_item_open_term")); // 开放学期选项
		mav.addObject("itemResultTypes", shareService.getCDictionaryData("c_operation_item_result_type")); // 结果形式选项
		mav.addObject("assets", assetService.findAllAssetsInCabinet()); // 耗材选项
		mav.addObject("itemAssets", new ItemAssets()); // 耗材选项
		List<MaterialKindDTO> materialKindDTOList=materialService.findAllAssetClassificationList();
		Map<Integer, String> materialKindMap = new HashMap<>();
		for(MaterialKindDTO m: materialKindDTOList){
			materialKindMap.put(m.getId(), m.getCname());
		}
		mav.addObject("materialKindMap", materialKindMap);

		mav.addObject("schoolTerms", shareService.findAllSchoolTerms());  //学期
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
		List<SchoolMajor> allChooseMajorList = new ArrayList<>();
		String majors=operationItem.getLpMajorFit();
		if(majors!=null&&!majors.equals("")){
			String[] majorArray=majors.split(",");
			for (String string : majorArray) {
				SchoolMajor major = systemService.findSchoolMajorByNumber(string.replaceAll("\\D", ""));//去除所有非数字的值,针对导入实验室项目
				if (major!=null) {
					allMajorList.remove(major);
					allChooseMajorList.add(major);
				}
			}
		}
		mav.addObject("majors", allMajorList);  //所有专业（排除已选）
		mav.addObject("chooseMajors", allChooseMajorList); // 已选
		List<User> users=shareService.findUsersByQuery("TEACHER", acno);
		mav.addObject("users", users);

		mav.addObject("listItemDevice", operationService.getItemDeviceByItem(itemId, null, 1, -1));

		mav.addObject("listItemMaterialRecord", operationService.getItemMaterialRecordByItem(itemId));//实验材料 贺子龙新增
		mav.addObject("categoryMaterialRecordMain", shareService.getCDictionaryData("category_operation_item_material_record_main"));
		mav.addObject("operationItemMaterialRecord", new OperationItemMaterialRecord());
		mav.addObject("page",page);
		mav.setViewName("openOperationItem/editOpenOperationItem.jsp");

//		String ip = shareService.getIpAddr(request);
		//保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
		//tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id 0-查看 -1--新建
		//action:  0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核查看 6 保存 7 审核编辑后保存 8 导入 9 审核结果
//		systemLogService.saveOperationItemLog(ip, 0, 1, itemId);
		return mav;
	}


	/**
	 * Description 删除项目
	 * @param request
	 * @param operationItemId
	 * @param status
	 * @return
	 * @author 孙汉承 2018-9-10
	 */
	@RequestMapping("/deleteOpenOperationItem")
	public String deleteOpenOperationItem(HttpServletRequest request,@RequestParam int operationItemId, int status,int page){
		String ip = shareService.getIpAddr(request);
		//保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
		//tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id 0-查看 -1--新建
		//action:  0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核查看 6 保存 7 审核编辑后保存 8 导入 9 审核结果
		systemLogService.saveOperationItemLog(ip, status, 3, operationItemId);
		operationService.deleteOperationItem(operationItemId);
		return "redirect:/openOperationItem/listOpenOperationItem?currpage="+page+"&status="+status+"&orderBy=9";
	}


    /**
     * Description 保存项目
     * @param request
     * @param operationItem
     * @param acno
     * @return
     * @author 陈乐为 2018-8-25
     */
    @RequestMapping("/saveOpenOperationItem")
    public ModelAndView saveOpenOperationItem(HttpServletRequest request,@ModelAttribute OperationItem operationItem,
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
        if(operationItem.getId() != null){
        	OperationItem operationItemOld = operationItemDAO.findOperationItemById(operationItem.getId());
        	operationItem.setItemQuestionDocument(operationItemOld.getItemQuestionDocument());
		}
        operationItem.setUserByLpCreateUser(shareService.getUserDetail());  //当前登录人
        operationItem.setCreatedAt(Calendar.getInstance());//设置为当前时间
		operationItem.setSchoolTerm(shareService.getBelongsSchoolTerm(Calendar.getInstance()));
//        //保存实验室
//        if(!net.luxunsh.util.EmptyUtil.isStringEmpty(request.getParameter("labRoomId"))){
//            String[] labRoomId = request.getParameterValues("labRoomId");
//            HashSet<LabRoom> lab = new HashSet<LabRoom>();
//            for(String id:labRoomId){
//                LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(Integer.parseInt(id));
//                lab.add(labRoom);
//            }
//            operationItem.setLabRooms(lab);
//        }
//        //获取所属学院
        SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
        Set<LabCenter> labCenters = academy.getLabCenters();
        for (LabCenter lc:labCenters){
            operationItem.setLabCenter(lc);
            break;
        }
//        operationItem = operationService.saveOperationItem(operationItem);
//        String ip = shareService.getIpAddr(request);
//        //保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
//        //tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id 0-查看 -1--新建
//        //action:  0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核查看 6 保存 7 审核编辑后保存 8 导入 9 审核结果
//        int tag = 0;
//        int action = 6;
//        systemLogService.saveOperationItemLog(ip, tag, action, operationItem.getId());

        String[] labUsers = request.getParameterValues("relatedLabUsers");
        String[] openTeachers = request.getParameterValues("teachers");
        if(operationItem.getId() != null) {
			OperationItem operationItemOld = operationItemDAO.findOperationItemById(operationItem.getId());
			for (ItemOpenTeacher itemOpenTeacher : operationItemOld.getOpenTeachers()) {
				itemOpenTeacherDAO.remove(itemOpenTeacher);
			}
			for (ItemLabUsers itemLabUsers1 : operationItemOld.getLabUsers()) {
				itemLabUsersDAO.remove(itemLabUsers1);
			}
		}
		operationItem = operationItemDAO.store(operationItem);
        // 保存实验室相关人员
		if(labUsers != null) {
			for (String username : labUsers) {
				User user = shareService.findUserByUsername(username);
				ItemLabUsers itemLabUser = new ItemLabUsers();
				itemLabUser.setUser(user);
				itemLabUser.setOperationItem(operationItem);
				itemLabUser = itemLabUsersDAO.store(itemLabUser);
			}
		}
        // 保存开放教师
		if(openTeachers != null) {
			for (String username : openTeachers) {
				User user = shareService.findUserByUsername(username);
				ItemOpenTeacher itemOpenTeacher = new ItemOpenTeacher();
				itemOpenTeacher.setUser(user);
				itemOpenTeacher.setOperationItem(operationItem);
				itemOpenTeacher = itemOpenTeacherDAO.store(itemOpenTeacher);
			}
		}

        mav.setViewName("redirect:/openOperationItem/editOpenOperationItem?itemId="+operationItem.getId()+"&page="+page);
        return mav;

    }

	/**
	 * 上传思考题
	 * @param id 项目id
	 * @return 提示字符串数组
	 * @author 黄保钱 2019-04-26
	 */
	@RequestMapping("/uploadQuestion")
	public @ResponseBody String[] uploadQuestion(HttpServletRequest request,@RequestParam Integer id){
		//获取文件地址
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String sep = System.getProperty("file.separator");
		Map files = multipartRequest.getFileMap();
		Iterator fileNames = multipartRequest.getFileNames();
		boolean flag = false;
		String PathDir = "/upload/operationQuestion/" + id;
		String fileDir = request.getSession().getServletContext().getRealPath(PathDir);
		String returnFilePathName = "";
		CommonDocument doc = new CommonDocument();
		//存放文件文件夹名称
		for (; fileNames.hasNext(); ) {
			String filename = (String) fileNames.next();
			CommonsMultipartFile file = (CommonsMultipartFile) files.get(filename);
			byte[] bytes = file.getBytes();
			if (bytes.length != 0) {
				// 说明申请有附件
				if (!flag) {
					File dirPath = new File(fileDir);
					if (!dirPath.exists()) {
						flag = dirPath.mkdirs();
					}
				}
				String fileTrueName = file.getOriginalFilename();
				doc.setDocumentName(fileTrueName);
				returnFilePathName = PathDir + "/" + fileTrueName;
				doc.setDocumentUrl(returnFilePathName);
				//System.out.println("文件名称："+fileTrueName);
				File uploadedFile = new File(fileDir + sep + fileTrueName);
				//System.out.println("文件存放路径为："+fileDir + sep + fileTrueName);
				try {
					FileCopyUtils.copy(bytes, uploadedFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		OperationItem operationItem = operationItemDAO.findOperationItemById(id);
		doc = commonDocumentDAO.store(doc);
		operationItem.setItemQuestionDocument(doc);
		operationItemDAO.store(operationItem);
			String[] result = {"成功"};
			return result;
	}

	/**
	 * 保存项目物资
	 * @param itemId 项目id
	 * @param page 页数
	 * @param itemAssets 要保存项目物资对象
	 * @return 项目编辑页面
	 * @author 黄保钱 2019-04-26
	 */
	@RequestMapping("/saveItemAssets")
	public ModelAndView saveItemAssets(HttpServletRequest request,@RequestParam Integer itemId,@RequestParam Integer page, @ModelAttribute ItemAssets itemAssets,@RequestParam(defaultValue = "1") Integer type){
    	ModelAndView mav = new ModelAndView();
    	OperationItem operationItem = new OperationItem();
    	operationItem.setId(itemId);
    	itemAssets.setOperationItem(operationItem);
    	itemAssetsDAO.store(itemAssets);
    	System.out.println(page);
    	if(type==1) mav.setViewName("redirect:/openOperationItem/editOpenOperationItem?itemId="+itemId+"&page="+page);//开放项目管理编辑
    	if(type ==2) mav.setViewName("redirect:/operation/editOperationItemLims?itemId="+itemId+"&page="+page);//实验项目管理编辑
		return mav;
	}

	/**
	 * 寻找项目物资通过id
	 * @param itemAssetId 项目物资中间表id
	 * @return json格式的物资数据
	 * @author 黄保钱 2019-04-26
	 */
    @RequestMapping(value="/getItemAsset", produces="application/json;charset=UTF-8")
	public @ResponseBody String getItemAsset(@RequestParam Integer itemAssetId){
    	ItemAssets itemAssets = new ItemAssets();
    	if(itemAssetId != null) {
			itemAssets = itemAssetsDAO.findItemAssetsById(itemAssetId);
		}
    	String s = "{";
    	s+="\"amount\":" + itemAssets.getAmount() + ",";
    	s+="\"assetId\":" + itemAssets.getAsset().getId() + "";
    	s+="}";
    	return s;
	}

	/**
	 * 删除项目物资
	 * @param itemAssetId 项目物资中间表id
	 * @param itemId 项目id
	 * @param page 当前页数
	 * @return 重定向到项目编辑
	 * @author 黄保钱 2019-04-26
	 */
	@RequestMapping("/deleteItemAsset")
	public String deleteItemAsset(@RequestParam Integer itemAssetId, Integer itemId, Integer page){
		if(itemAssetId != null) {
			ItemAssets itemAssets = itemAssetsDAO.findItemAssetsById(itemAssetId);
			itemAssetsDAO.remove(itemAssets);
		}
		return "redirect:/openOperationItem/editOpenOperationItem?itemId="+itemId+"&page="+page;
	}

	/**
	 * Description 提交实验项目，保存指定审核人
	 * @param request
	 * @param operationItem
	 * @return
	 * @author 陈乐为 2018-8-25
	 */
	@RequestMapping("/submitOpenOperationItem")
	public String submitOperationItemLims(HttpServletRequest request,@ModelAttribute OperationItem operationItem,int page, @ModelAttribute("selected_academy") String acno){
		OperationItem oItem = new OperationItem();
		oItem = operationService.submitOperationItem(operationItem, acno);
		if (oItem.getItemAssets()!=null && oItem.getItemAssets().size()>0) {
			this.saveWarehousing(oItem.getId());
		}
		return "redirect:/openOperationItem/listOpenOperationItem?currpage="+page+"&status=1&orderBy=9";
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
	@RequestMapping(value = "/viewOpenOperationItem/{status}/{id}", method = RequestMethod.GET)
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
		mav.addObject("schoolTerms", shareService.findAllSchoolTerms());  //学期
		mav.addObject("users", systemService.getAllUser(1, -1, shareService.getUser()));  //教师数据
		LabRoom labRoom = new LabRoom();
		mav.addObject("labRooms", labRoomService.findAllLabRoomByQuery(1, -1, labRoom));  //实验室数据
		SchoolCourseInfo schoolCourseInfo = new SchoolCourseInfo();
		mav.addObject("schoolCourseInfos", schoolCourseInfoService.getCourseInfoByQuery(schoolCourseInfo, -1, 1, -1));  //课程数据
		mav.addObject("subjects", systemService.getAllSystemSubject12(1, -1));  //学科数据
		//物资类型
		List<MaterialKindDTO> materialKindDTOList=materialService.findAllAssetClassificationList();
		Map<Integer, String> materialKindMap = new HashMap<>();
		for(MaterialKindDTO m: materialKindDTOList){
			materialKindMap.put(m.getId(), m.getCname());
		}
		mav.addObject("materialKindMap", materialKindMap);
		//面向专业
		String majors=operationItem.getLpMajorFit();
		List<SchoolMajor>  majorList=new ArrayList<SchoolMajor>();
		if (majors!=null&&!majors.equals("")) {
			String[] majorArray=majors.split(",");
			for (String string : majorArray) {
				SchoolMajor major=systemService.findSchoolMajorByNumber(string);
				if (major!=null) {
					majorList.add(major);
				}
			}
		}
		mav.addObject("majorList", majorList);	//已选专业

		//开放学院
		String academies=operationItem.getLpCollege();
		List<SchoolAcademy>  academyList=new ArrayList<>();
		if (academies!=null&&!academies.equals("")) {
			String[] academyArray=academies.split(",");
			for (String string : academyArray) {
				SchoolAcademy academy=shareService.findSchoolAcademyByPrimaryKey(string);
				if (academy!=null) {
					academyList.add(academy);
				}
			}
		}
		mav.addObject("academyList", academyList);
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
		mav.setViewName("openOperationItem/viewOpenOperationItem.jsp");

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
		return "redirect:/openOperationItem/listOpenOperationItem?currpage=1&&status=2&orderBy=9";
	}

	/**
	 * 不分批选安排页面
	 * @return 不分批选安排页面
	 * @author 黄保钱 2019-04-26
	 */
	@RequestMapping("/arrangeNoBatchChoose")
	public ModelAndView arrangeNoBatchChoose(Integer id){
		ModelAndView mav = new ModelAndView();
		if(id != null) {
			mav.addObject("id", id);
			OperationItem operationItem = operationItemDAO.findOperationItemById(id);
			mav.addObject("termId", operationItem.getSchoolTerm().getId());
			mav.addObject("courseNumber", operationItem.getSchoolCourseInfo().getCourseNumber());
			mav.addObject("courseCount", operationItem.getLpStudentNumber());
			// 获取最大的id
			int maxId = timetableSelfCourseService.getTimetableSelfCourseTotalRecords();
			SimpleDateFormat sdf = new SimpleDateFormat("mmssSSS");
			// 当前时间
			Calendar currTime = Calendar.getInstance();
			String dateStr = sdf.format(currTime.getTime());
			mav.addObject("maxId", maxId+"-"+dateStr);
			mav.addObject("user", shareService.getUserDetail());
			mav.addObject("allNumber", operationItem.getLpStudentNumber());
		}
		mav.addObject("type", "0");
		mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
		mav.addObject("PROJECT_NAME", pConfig.PROJECT_NAME);
		mav.addObject("grade", schoolTermDAO.executeQuery("select st from SchoolTerm st group by st.yearCode"));
		mav.addObject("title", "不分批选");
		mav.setViewName("openOperationItem/arrangeNoBatchChoose.jsp");
		return mav;
	}

	/**
	 * 分批自选安排页面
	 * @return 分批自选安排页面
	 * @author 黄保钱 2019-04-26
	 */
	@RequestMapping("/arrangeBatchChoose")
	public ModelAndView arrangeBatchChoose(Integer id){
		ModelAndView mav = new ModelAndView();
		if(id != null) {
			mav.addObject("id", id);
			OperationItem operationItem = operationItemDAO.findOperationItemById(id);
			mav.addObject("termId", operationItem.getSchoolTerm().getId());
			mav.addObject("courseNumber", operationItem.getSchoolCourseInfo().getCourseNumber());
			mav.addObject("courseCount", operationItem.getLpStudentNumber());
			// 获取最大的id
			int maxId = timetableSelfCourseService.getTimetableSelfCourseTotalRecords();
			SimpleDateFormat sdf = new SimpleDateFormat("mmssSSS");
			// 当前时间
			Calendar currTime = Calendar.getInstance();
			String dateStr = sdf.format(currTime.getTime());
			mav.addObject("maxId", maxId+"-"+dateStr);
			mav.addObject("user", shareService.getUserDetail());
		}
		mav.addObject("type", "1");
		mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
		mav.addObject("PROJECT_NAME", pConfig.PROJECT_NAME);
		mav.addObject("grade", schoolTermDAO.executeQuery("select st from SchoolTerm st group by st.yearCode"));
		mav.addObject("title", "分批自选");
		mav.setViewName("openOperationItem/arrangeBatchChoose.jsp");
		return mav;
	}

	/**
	 * 不分批排安排页面
	 * @return 不分批排安排页面
	 * @author 黄保钱 2019-04-26
	 */
	@RequestMapping("/arrangeNoBatchNoChoose")
	public ModelAndView arrangeNoBatchNoChoose(Integer id){
		ModelAndView mav = new ModelAndView();
		if(id != null) {
			mav.addObject("id", id);
			OperationItem operationItem = operationItemDAO.findOperationItemById(id);
			mav.addObject("termId", operationItem.getSchoolTerm().getId());
			mav.addObject("courseNumber", operationItem.getSchoolCourseInfo().getCourseNumber());
			mav.addObject("courseCount", operationItem.getLpStudentNumber());
			// 获取最大的id
			int maxId = timetableSelfCourseService.getTimetableSelfCourseTotalRecords();
			SimpleDateFormat sdf = new SimpleDateFormat("mmssSSS");
			// 当前时间
			Calendar currTime = Calendar.getInstance();
			String dateStr = sdf.format(currTime.getTime());
			mav.addObject("maxId", maxId+"-"+dateStr);
			mav.addObject("user", shareService.getUserDetail());
		}
		mav.addObject("type", "2");
		mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
		mav.addObject("PROJECT_NAME", pConfig.PROJECT_NAME);
		mav.addObject("grade", schoolTermDAO.executeQuery("select st from SchoolTerm st group by st.yearCode"));
		mav.addObject("title", "不分批排");
		mav.setViewName("openOperationItem/arrangeNoBatchNoChoose.jsp");
		return mav;
	}

	/**
	 * 分批直排安排页面
	 * @return 分批直排安排页面
	 * @author 黄保钱 2019-04-26
	 */
	@RequestMapping("/arrangeBatchNoChoose")
	public ModelAndView arrangeBatchNoChoose(Integer id){
		ModelAndView mav = new ModelAndView();
		if(id != null) {
			mav.addObject("id", id);
			OperationItem operationItem = operationItemDAO.findOperationItemById(id);
			mav.addObject("termId", operationItem.getSchoolTerm().getId());
			mav.addObject("courseNumber", operationItem.getSchoolCourseInfo().getCourseNumber());
			mav.addObject("courseCount", operationItem.getLpStudentNumber());
			// 获取最大的id
			int maxId = timetableSelfCourseService.getTimetableSelfCourseTotalRecords();
			SimpleDateFormat sdf = new SimpleDateFormat("mmssSSS");
			// 当前时间
			Calendar currTime = Calendar.getInstance();
			String dateStr = sdf.format(currTime.getTime());
			mav.addObject("maxId", maxId+"-"+dateStr);
			mav.addObject("user", shareService.getUserDetail());
		}
		mav.addObject("type", "3");
		mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
		mav.addObject("PROJECT_NAME", pConfig.PROJECT_NAME);
		mav.addObject("grade", schoolTermDAO.executeQuery("select st from SchoolTerm st group by st.yearCode"));
		mav.addObject("title", "分批直排");
		mav.setViewName("openOperationItem/arrangeBatchNoChoose.jsp");
		return mav;
	}

	/**
	 * 保存安排数据
	 * @return 成功-"success"
	 * @author 黄保钱 2019-04-26
	 */
	@RequestMapping("/saveArrangeData")
	@ResponseBody
	public String saveArrangeData(Integer type, Integer operId, Integer selfId, String startTime, String endTime){
		ItemPlan itemPlan = new ItemPlan();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Calendar startTimeNew = Calendar.getInstance();
			startTimeNew.setTime(sdf.parse(startTime));
			itemPlan.setStartTime(startTimeNew);
			Calendar endTimeNew = Calendar.getInstance();
			endTimeNew.setTime(sdf.parse(endTime));
			itemPlan.setEndTime(endTimeNew);
		} catch (ParseException e) {
			e.printStackTrace();
			return "日期格式不正确";
		} catch (NullPointerException ignored){

		}
		OperationItem operationItem = new OperationItem();
		operationItem.setId(operId);
		itemPlan.setOperationItem(operationItem);
		TimetableSelfCourse timetableSelfCourse = new TimetableSelfCourse();
		timetableSelfCourse.setId(selfId);
		itemPlan.setTimetableSelfCourse(timetableSelfCourse);
		itemPlan.setType(type);
		itemPlanDAO.store(itemPlan);
		return "success";
	}

	/**
	 * 不分批排课页面
	 * @return 不分批排课页面
	 * @author 黄保钱 2019-04-26
	 */
	@RequestMapping("/timetableNoBatchNoChoose")
	public ModelAndView timetableNoBatchNoChoose(HttpServletRequest request,@ModelAttribute("selected_academy") String acno){
	    ModelAndView mav = new ModelAndView();
	    mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
	    Integer opId = Integer.valueOf(request.getParameter("opId"));
	    OperationItem operationItem = operationItemDAO.findOperationItemById(opId);
	    //
		mav.addObject(operationItem);
		mav.addObject("academyNumber",acno);
		mav.addObject("timetableStyle",request.getParameter("timetableStyle"));
		mav.addObject("selfId",request.getParameter("selfId"));
		mav.addObject("term", request.getParameter("term"));
		//获取课程编号
		TimetableSelfCourse schoolCourse = timetableSelfCourseDAO.findTimetableSelfCourseById(Integer.valueOf(request.getParameter("selfId")));
		String courseNumber = "";
		if(Objects.nonNull(schoolCourse)&&Objects.nonNull(schoolCourse.getSchoolCourseInfo())){
			courseNumber = schoolCourse.getSchoolCourseInfo().getCourseNumber();
		}
		mav.addObject("courseNumber",courseNumber);
		// 虚拟镜像
		mav.addObject("virtual", pConfig.virtual);
		List<VirtualImage> virtualImageList = virtualService.getAllVirtualImage(null, 1, -1);
		mav.addObject("virtualImageList", virtualImageList);
        mav.setViewName("openOperationItem/timetableNoBatchNoChoose.jsp");
	    return mav;
    }

	/**
	 * 分组列表页面
	 * @return 分组列表页面
	 * @author 黄保钱 2019-04-26
	 */
    @RequestMapping("/timetableChooseGroupList")
    public ModelAndView timetableChooseGroupList(HttpServletRequest request,@ModelAttribute("selected_academy") String acno){
        ModelAndView mav = new ModelAndView();
		// 获取学期列表
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", schoolTerms);
		mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
		// 获取实验室排课的通用配置对象；
		CStaticValue cStaticValue = cStaticValueService.getCStaticValueByTimetableLabDevice(acno);
		mav.addObject("selfId",request.getParameter("selfId"));
		mav.addObject("term",request.getParameter("term"));
		mav.addObject("cStaticValue", cStaticValue);
		mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
		// 获取可选的教师列表列表
		mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap(acno));
		// 虚拟镜像
		List<VirtualImage> virtualImageList = virtualService.getAllVirtualImage(null, 1, -1);
		mav.addObject("virtualImageList", virtualImageList);
		Integer opId = Integer.valueOf(request.getParameter("opId"));
		OperationItem operationItem = operationItemDAO.findOperationItemById(opId);
		mav.addObject("operationItem", operationItem);
		mav.addObject("opId", opId);
		try {
			ItemPlan itemPlan = (ItemPlan) itemPlanDAO.executeQuerySingleResult("select ip from ItemPlan ip where ip.operationItem.id=" + opId
					+ " and ip.timetableSelfCourse.id=" + request.getParameter("selfId"));
			mav.addObject("ipId", itemPlan.getId());
			if(itemPlan.getStartTime() != null && itemPlan.getEndTime() != null) {
				mav.addObject("startTime", itemPlan.getStartTime().getTime());
				mav.addObject("endTime", itemPlan.getEndTime().getTime());
			}else {
				mav.addObject("startTime", Calendar.getInstance().getTime());
				mav.addObject("endTime", Calendar.getInstance().getTime());
			}
			mav.addObject("type", itemPlan.getType());
		}catch (NoSuchElementException ignored){}
        mav.setViewName("openOperationItem/timetableChooseGroupList.jsp");
        return mav;
    }

	/**
	 * 学生选课页面
	 * @param id 项目id
	 * @return 学生选课页面
	 * @author 黄保钱 2019-04-26
	 */
	@RequestMapping("/arrangeForStudent")
	public ModelAndView arrangeForStudent(@RequestParam Integer id){
		ModelAndView mav = new ModelAndView();
		if(id != null) {
			mav.addObject("id", id);
			OperationItem operationItem = operationItemDAO.findOperationItemById(id);
			mav.addObject("termId", operationItem.getSchoolTerm().getId());
			mav.addObject("courseNumber", operationItem.getSchoolCourseInfo().getCourseNumber());
			mav.addObject("courseCount", operationItem.getLpStudentNumber());
			// 获取最大的id
			int maxId = timetableSelfCourseService.getTimetableSelfCourseTotalRecords();
			SimpleDateFormat sdf = new SimpleDateFormat("mmssSSS");
			// 当前时间
			Calendar currTime = Calendar.getInstance();
			String dateStr = sdf.format(currTime.getTime());
			mav.addObject("maxId", maxId+"-"+dateStr);
			mav.addObject("user", shareService.getUserDetail());
		}
		mav.addObject("type", "-1");
		mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
		mav.addObject("PROJECT_NAME", pConfig.PROJECT_NAME);
		mav.addObject("title", "学生选课");
		mav.setViewName("openOperationItem/arrangeForStudent.jsp");
		return mav;
	}

	/**
	 * 保存申领单
	 * @param selfId 排课id
	 * @return 成功-"success",失败-"fail"
	 * @author 黄保钱 2019-4-29
	 */
	@RequestMapping("/saveAssetReceive")
	@ResponseBody
	public String saveAssetReceive(Integer selfId){
		TimetableSelfCourse timetableSelfCourse = timetableSelfCourseDAO.findTimetableSelfCourseById(selfId);
		if(timetableSelfCourse.getItemPlans() == null || timetableSelfCourse.getItemPlans().size() == 0)
			return "fail";
		OperationItem operationItem = timetableSelfCourse.getItemPlans().iterator().next().getOperationItem();
		for(TimetableAppointment ta: timetableSelfCourse.getTimetableAppointments()) {
			for(TimetableAppointmentSameNumber tasn: ta.getTimetableAppointmentSameNumbers()) {
				for(Integer i = tasn.getStartWeek(); i <= tasn.getEndWeek(); i++) {
					// 保存申领单
					AssetsReceiveDTO assetsReceiveDTO = new AssetsReceiveDTO();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					List<SchoolWeek> schoolWeeks = schoolWeekDAO.executeQuery("select sw from SchoolWeek sw where sw.week = " + i
							+ " and sw.weekday = " + ta.getWeekday()
							+ " and sw.schoolTerm.id = " + ta.getSchoolTerm().getId());
					if(schoolWeeks == null || schoolWeeks.size() == 0 || operationItem.getItemAssets() == null || operationItem.getItemAssets().size() == 0){
						return "fail";
					}

					SchoolWeek schoolWeek = schoolWeeks.get(0);
					Calendar theTime = schoolWeek.getDate();
					String startStr = sdf.format(theTime.getTime());
					assetsReceiveDTO.setBeginTime(startStr);
					String endStr = sdf.format(theTime.getTime());
					assetsReceiveDTO.setEndTime(endStr);

					assetsReceiveDTO.setDepartment(operationItem.getLabCenter().getId().toString());
					assetsReceiveDTO.setItemId(operationItem.getId());
					assetsReceiveDTO.setGoodsCategory(operationItem.getItemAssets().iterator().next().getAsset().getCategory().toString());
					Integer assetsReceiveId = materialService.saveAssetsReceiveDetail(assetsReceiveDTO);

					// 保存申领单具体条目
					for(ItemAssets itemAssets: operationItem.getItemAssets()) {
						AssetsApplyItemDTO assetsApplyItemDTO = new AssetsApplyItemDTO();
						assetsApplyItemDTO.setAppId(assetsReceiveId.toString());
						assetsApplyItemDTO.setAssetsId(itemAssets.getAsset().getId().toString());
						Integer quantity = 0;
						/**
						 * 1.宁德需求：不分批排
						 * 2.分组实验，数量按照组数计算；否则按照实际上课人数计算
						 */
						if (pConfig.PROJECT_NAME.equals("ndyzlims") && operationItem.getCDictionaryByLpCategoryApp().getCNumber().equals("2")) {
							if (operationItem.getLpSetNumber()!=null && !operationItem.getLpSetNumber().equals("")) {
								quantity = Integer.parseInt(operationItem.getLpSetNumber());
							}
                        } else if (operationItem.getCDictionaryByLpCategoryApp().getCNumber().equals("1")) {
							quantity = 1;
						} else {
                            if (timetableSelfCourse.getItemPlans().iterator().next().getType() == 1 || timetableSelfCourse.getItemPlans().iterator().next().getType() == 3) {//分批自选、分批直排
                                for (TimetableGroup group : ta.getTimetableGroups()) {
                                    quantity += group.getNumbers();
                                }
//							List<TimetableBatch> batches = timetableBatchDAO.executeQuery("select tb from TimetableBatch tb where tb.selfId = " + selfId);
//							for(TimetableBatch tb: batches){
//								for (TimetableGroup tg: tb.getTimetableGroups()){
//									quantity += tg.getNumbers();
//								}
//							}
                            } else {
                                quantity = timetableSelfCourse.getTimetableCourseStudents().size();
                            }
                        }
						quantity = quantity*itemAssets.getAmount();//每组实验用量*学生数
						assetsApplyItemDTO.setQuantity(quantity);
						// 物品柜
						String cabinetId = materialService.allocateCabinetFromAssets(itemAssets.getAsset().getId(),quantity,0,assetsReceiveId);
						String[] split = cabinetId.split("-");
						assetsApplyItemDTO.setCabinet(split[0]);
						materialService.saveAddAssetsReceiveDetail(assetsApplyItemDTO);
					}
				}
			}
		}
		return "success";
	}

	/**
	 * 保存入库单
	 * @param id 项目id
	 * @return 成功-"success",失败-"fail"
	 * @author 黄保钱 2019-4-29
	 */
	@RequestMapping("/saveWarehousing")
	@ResponseBody
	public String saveWarehousing(@RequestParam Integer id){
		OperationItem operationItem = operationItemDAO.findOperationItemById(id);
		Asset asset = operationItem.getItemAssets().iterator().next().getAsset();
		AssetsInStorageDTO assetsInStorageDTO = new AssetsInStorageDTO();
		User user = operationItem.getUserByLpCreateUser();
		assetsInStorageDTO.setAcademyNumber(user.getSchoolAcademy().getAcademyNumber());
		assetsInStorageDTO.setUsername(user.getUsername());
		assetsInStorageDTO.setDepartment(operationItem.getLabCenter().getId().toString());
		assetsInStorageDTO.setGoodsCategory(asset.getCategory().toString());
		Integer assetStorage = materialService.saveAssetsInStorageDetail(assetsInStorageDTO);

		for(ItemAssets itemAssets: operationItem.getItemAssets()) {
			AssetsApplyItemDTO assetsApplyItemDTO = new AssetsApplyItemDTO();
			assetsApplyItemDTO.setAssetsId(itemAssets.getAsset().getId().toString());
			assetsApplyItemDTO.setPrice(new BigDecimal(itemAssets.getAsset().getPrice() == null ? "0" : itemAssets.getAsset().getPrice()));
			assetsApplyItemDTO.setAppId(assetStorage.toString());
			assetsApplyItemDTO.setQuantity(itemAssets.getAmount());
			materialService.saveAddAssetsInStorageDetail(assetsApplyItemDTO);
		}

		return "success";
	}

	/************************************************************
	 * Descriptions：自主排课管理-自主排课二次分批排课的主显示页面
	 *
	 * @作者：魏诚
	 * @时间：2018-09-04
	 ************************************************************/
	@RequestMapping("/newSelfReGroupTimetableCourse")
	public ModelAndView newSelfReGroupTimetableCourse(HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		TimetableAppointmentSameNumber timetableAppointmentSameNumber = null;
		if(Objects.nonNull(request.getParameter("sameNumberId"))&&Integer.parseInt(request.getParameter("sameNumberId"))!=-1){
			int timetableAppointmentSameNumberId = Integer.parseInt(request.getParameter("sameNumberId"));
			timetableAppointmentSameNumber =timetableAppointmentSameNumberDAO.findTimetableAppointmentSameNumberById(timetableAppointmentSameNumberId);
		}
		mav.addObject("timetableAppointmentSameNumber",timetableAppointmentSameNumber);
		mav.addObject("groupId",request.getParameter("groupId"));
		mav.addObject("timetableStyle",request.getParameter("timetableStyle"));
		mav.addObject("selfId",request.getParameter("selfId"));
		mav.addObject("operationItem", operationItemDAO.findOperationItemById(Integer.valueOf(request.getParameter("opId"))));
		//获取课程编号
		SchoolCourse schoolCourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(request.getParameter("selfId"));
		String courseNumber = "";
		if(Objects.nonNull(schoolCourse)&&Objects.nonNull(schoolCourse.getSchoolCourseInfo())){
			courseNumber = schoolCourse.getSchoolCourseInfo().getCourseNumber();
		}
		mav.addObject("courseNumber",courseNumber);
		mav.addObject("term",request.getParameter("term"));
		mav.addObject("academyNumber",acno);
		mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
		// 虚拟镜像
		mav.addObject("virtual", pConfig.virtual);
		List<VirtualImage> virtualImageList = virtualService.getAllVirtualImage(null, 1, -1);
		mav.addObject("virtualImageList", virtualImageList);
		mav.setViewName("openOperationItem/timetableChoose.jsp");
		return mav;
	}

	/**
	 * 保存申购单
	 * @param operationId 项目id
	 * @return 成功-"success",失败-"fail"
	 * @author 黄保钱 2019-5-13
	 */
	@RequestMapping(value = "/saveAssetApp",method = RequestMethod.POST)
	@ResponseBody
	public String saveAssetApp(@RequestParam String type, @RequestParam String courseNo,
							   @RequestParam String academyNumber, @RequestParam String username){
		Map<Integer, Integer> operation = new HashMap<>();
		Set<TimetableAppointment> timetableAppointments = new LinkedHashSet<>();
		if("schoolCourse".equals(type)){
			timetableAppointments = schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo).getTimetableAppointments();
		}else if("selfCourse".equals(type)){
			timetableAppointments = timetableSelfCourseDAO.findTimetableSelfCourseById(Integer.valueOf(courseNo)).getTimetableAppointments();
		}
		for (TimetableAppointment ta: timetableAppointments){
			for(TimetableItemRelated tir: ta.getTimetableItemRelateds()){
				Integer num = 0;
				if(operation.get(tir.getOperationItem().getId()) != null){
					num += operation.get(tir.getOperationItem().getId());
				}
				for(TimetableAppointmentSameNumber tasn: ta.getTimetableAppointmentSameNumbers()){
					num = num + tasn.getEndWeek() - tasn.getStartWeek() + 1;
				}
				operation.put(tir.getOperationItem().getId(), num);
			}
		}
		for(Map.Entry<Integer, Integer> entry: operation.entrySet()) {
			AssetsApplyDTO assetsApplyDTO = new AssetsApplyDTO();
			String today = (new SimpleDateFormat("yyyy-MM-dd")).format(Calendar.getInstance().getTime());
			assetsApplyDTO.setDate(today);
			assetsApplyDTO.setGoodsCategory("1");
			assetsApplyDTO.setStartDate(today);
			assetsApplyDTO.setEndDate(today);
			assetsApplyDTO.setAcademyNumber(academyNumber);
			User user = shareService.findUserByUsername(username);
			assetsApplyDTO.setApplicantUserName(username);
			assetsApplyDTO.setCourseNo(courseNo);
			Integer appId = materialService.saveAssetsApplyDetail(assetsApplyDTO);
			OperationItem operationItem = operationItemDAO.findOperationItemById(entry.getKey());
			for (ItemAssets itemAssets : operationItem.getItemAssets()) {
				AssetsApplyItemDTO assetsApplyItemDTO = new AssetsApplyItemDTO();
				assetsApplyItemDTO.setAppId(appId.toString());
				assetsApplyItemDTO.setAssetsId(itemAssets.getAsset().getId().toString());
				assetsApplyItemDTO.setName(itemAssets.getAsset().getChName());
				assetsApplyItemDTO.setType(itemAssets.getAsset().getSpecifications());
				assetsApplyItemDTO.setFactory(itemAssets.getAsset().getFactory());
				assetsApplyItemDTO.setUnit(itemAssets.getAsset().getUnit());
				assetsApplyItemDTO.setQuantity(itemAssets.getAmount() * entry.getValue());
				try {
					assetsApplyItemDTO.setPrice(new BigDecimal(itemAssets.getAsset().getPrice()));
				} catch (Exception e) {
					assetsApplyItemDTO.setPrice(BigDecimal.ZERO);
				}
				materialService.saveAssetsAppRecordDetail(assetsApplyItemDTO);
			}
		}
		return "success";
	}

}
	
	
