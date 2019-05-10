/****************************************************************************
 * 命名规范：
 * 本控制层调整必须是按照如下的路径调整：@RequestMapping("/report/sample")，全部小写。
 * 列表信息listxxxx，獲取信息：getxxxx 编辑信息：editxxxx 删除信息：detelexxxx 新增信息 newxxxx
 * 导出信息exportxxxx 保存信息：savexxxx  
 ****************************************************************************/

package net.zjcclims.web.report;

import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;
import net.zjcclims.constant.OperationItemByCategory;
import net.zjcclims.constant.OperationItemByChange;
import net.zjcclims.constant.OperationItemByRequire;
import net.zjcclims.dao.LabCenterDAO;
import net.zjcclims.dao.LabRoomAgentDAO;
import net.zjcclims.dao.SchoolAcademyDAO;
import net.zjcclims.dao.SchoolTermDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.device.LabRoomDeviceService;
import net.zjcclims.service.device.SchoolDeviceService;
import net.zjcclims.service.lab.LabCenterService;
import net.zjcclims.service.report.ReportService;
import net.zjcclims.service.system.TermDetailService;
import net.zjcclims.service.timetable.OuterApplicationService;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.*;


/****************************************************************************
 * 功能：绩效报表的展示
 * 绩效报表模块 作者：何立友 时间：2014-08-19
 ****************************************************************************/
@Controller("ReportController")
@SessionAttributes("selected_academy")
@RequestMapping("/report")
public class ReportController<JsonResult> {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private LabRoomAgentDAO labRoomAgentDAO;
	@Autowired
	private ReportService reportService;
	@Autowired
	private SchoolAcademyDAO schoolAcademyDAO;
	@Autowired
	private ShareService shareService;
	@Autowired
	private TermDetailService termDetailService;
	@Autowired
	private SchoolTermDAO schoolTermDAO;
	@Autowired
	private SchoolDeviceService schoolDeviceService;
	@Autowired
	private LabRoomDeviceService labRoomDeviceService;
	@Autowired
	private LabCenterService labCenterService;
	@Autowired
	private OuterApplicationService outerApplicationService;
	@Autowired
	private LabCenterDAO labCenterDAO;
	@Autowired
	private PConfig pConfig;
	
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
	
	/**
	 * Description 获取中心实验室
	 * @param labCenter
	 * @param termId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findLabRoomByLabCenter")
	public Map<String, String> findLabRoomByLabCenter(@RequestParam String labCenter, Integer termId){
		return reportService.LinkLabCenter(labCenter, termId);
	}

	/**
	 * Description 报表-实验类型统计表
	 * @param request
	 * @param currpage
	 * @return
	 * @author 陈乐为 2018-7-20
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/reportOperationItemType")
	public ModelAndView reportOperationItemType(HttpServletRequest request, @RequestParam Integer currpage, @ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView();
		//学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		mav.addObject("term", term);

		// 实验中心
		int centerId = 0;
		if (request.getParameter("centerId")!=null) {
			centerId = Integer.valueOf(request.getParameter("centerId"));
		}
		mav.addObject("centerId", centerId);

		// 实验室
		String[] labRoomIdArray = {"0"};
		if (request.getParameter("roomId") != null) {
			labRoomIdArray = request.getParameterValues("roomId");
		}
		mav.addObject("labRoomIdArray", labRoomIdArray);
		Map<Integer,String> labRoomSelectedList = reportService.getLabRoomSelectedMap(labRoomIdArray);
		mav.addObject("labRoomSelectedList", labRoomSelectedList);

		// 获取所有学期
		Map<Integer,String> schoolTermMap = reportService.getTermsMap();
		mav.addObject("schoolTermMap",schoolTermMap);
		// 获取所有中心
		Map<Integer,String> labCenterMap = reportService.getAllLabCenterMap();
		mav.addObject("labCenterMap",labCenterMap);
		// 获取所有实验室
		Map<Integer,String> labRoomMap = reportService.getLabRoomsListMap(centerId,term);
		if(labRoomIdArray.length>0){
			for(String keyStr : labRoomIdArray){
				Integer tempInt = Integer.valueOf(keyStr);
				labRoomMap.remove(tempInt);
			}
		}
		mav.addObject("labRoomMap",labRoomMap);
		// 页面呈现
		int pageSize=50;
		if (request.getParameter("roomId") != null){
			// 获取页面呈现的列表数据
			List<Object[]> operationItemTypeList = reportService.operationItemViews(request, currpage, pageSize);//数组类型的list
			// 转化为容易页面呈现的形式（实验室）
			List<OperationItemByCategory> item = reportService.operationItemTypechangeViews(operationItemTypeList);
			mav.addObject("item", item);
		}else{
			// 获取页面呈现的列表数据
			List<Object[]> operationItemTypeLists = reportService.operationItemView(request, currpage, pageSize);//数组类型的list
			// 转化为容易页面呈现的形式
			List<OperationItemByCategory> items = reportService.operationItemTypechangeView(operationItemTypeLists);
			mav.addObject("items", items);
		}

		// 分页
		// 计算列表数据的总数
		int totalRecords = reportService.operationItemViewCount(request);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("currpage", currpage);
		mav.addObject("pageSize", pageSize);
		mav.addObject("totalRecords", totalRecords);

		mav.setViewName("reports/reportOperationItemType.jsp");

		return mav;
	}

	/************************************************************
	 * Description 实验类型统计表导出Excel
	 * @author 陈乐为 2018-7-20
	 ************************************************************/
	@RequestMapping("/exportOperationItemType")
	public void exportOperationItemType(HttpServletRequest request, HttpServletResponse response)throws Exception{
		reportService.exportOperationItemType(request, response);
	}

	/**********************************************
	 * Description 视图-实验要求统计表-呈现
	 * @author 陈乐为 2018-7-23
	 **********************************************/
	@SuppressWarnings("unchecked")
	@RequestMapping("/reportOperationItemRequire")
	public ModelAndView reportOperationItemRequire(HttpServletRequest request, @RequestParam Integer currpage, @ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView();
		//学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		mav.addObject("term", term);

		// 实验中心
		int centerId = 0;
		if (request.getParameter("centerId")!=null && Integer.valueOf(request.getParameter("centerId")) != 0) {
			centerId = Integer.valueOf(request.getParameter("centerId"));
		}
		mav.addObject("centerId", centerId);

		// 实验室
		String[] labRoomIdArray = {"0"};
		if (request.getParameter("roomId") != null) {
			labRoomIdArray = request.getParameterValues("roomId");
		}
		mav.addObject("labRoomIdArray", labRoomIdArray);

		Map<Integer,String> labRoomSelectedList = reportService.getLabRoomSelectedMap(labRoomIdArray);
		mav.addObject("labRoomSelectedList", labRoomSelectedList);

		// 获取所有学期
		Map<Integer,String> schoolTermMap = reportService.getTermsMap();
		mav.addObject("schoolTermMap",schoolTermMap);

		// 获取所有中心
		Map<Integer,String> labCenterMap = reportService.getAllLabCenterMap();
		mav.addObject("labCenterMap",labCenterMap);

		// 获取所有实验室
		Map<Integer,String> labRoomMap = reportService.getLabRoomsListMap(centerId,term);
		if(labRoomIdArray.length>0){
			for(String keyStr : labRoomIdArray){
				Integer tempInt = Integer.valueOf(keyStr);
				labRoomMap.remove(tempInt);
			}
		}
		mav.addObject("labRoomMap",labRoomMap);

		// 页面呈现
		int pageSize=50;
		if (request.getParameter("roomId") != null){
			// 获取页面呈现的列表数据
			List<Object[]> operationItemRequireLists = reportService.operationItemRequireViews(request, currpage, pageSize);//数组类型的list
			// 转化为容易页面呈现的形式（实验室）
			List<OperationItemByRequire> items = reportService.operationItemRequireChangeViews(operationItemRequireLists);
			mav.addObject("items", items);
		}else{
			// 获取页面呈现的列表数据
			List<Object[]> operationItemRequireList = reportService.operationItemRequireView(request, currpage, pageSize);//数组类型的list
			// 转化为容易页面呈现的形式
			List<OperationItemByRequire> item = reportService.operationItemRequireChangeView(operationItemRequireList);
			mav.addObject("item", item);
		}

		// 学校名称和编号
		mav.addObject("schoolName", pConfig.schoolName);
		mav.addObject("schoolCode", pConfig.schoolCode);
		// 分页
		// 计算列表数据的总数
		int totalRecords = reportService.operationItemRequireViewCount(request);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("currpage", currpage);
		mav.addObject("pageSize", pageSize);
		mav.addObject("totalRecords", totalRecords);

		mav.setViewName("reports/reportOperationItemRequire.jsp");

		return mav;
	}

	/************************************************************
	 * Description 实验要求统计表导出Excel
	 * @author 陈乐为 2018-7-23
	 ************************************************************/
	@RequestMapping("/exportOperationItemRequire")
	public void exportOperationItemRequire(HttpServletRequest request, HttpServletResponse response)throws Exception{
		reportService.exportOperationItemRequire(request, response);
	}

	/**
	 * Description 实验变动统计表
	 * @param request
	 * @param currpage
	 * @param acno
	 * @return
	 * @author 陈乐为 2018-7-23
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/reportOperationItemChange")
	public ModelAndView reportOperationItemChange(HttpServletRequest request, @RequestParam Integer currpage, @ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView();
		//学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		mav.addObject("term", term);

		// 实验中心
		int centerId = 0;
		if (request.getParameter("centerId")!=null) {
			centerId = Integer.valueOf(request.getParameter("centerId"));
		}
		mav.addObject("centerId", centerId);

		// 实验室
		String[] labRoomIdArray = {"0"};
		if (request.getParameter("roomId") != null) {
			labRoomIdArray = request.getParameterValues("roomId");
		}
		mav.addObject("labRoomIdArray", labRoomIdArray);
		Map<Integer,String> labRoomSelectedList = reportService.getLabRoomSelectedMap(labRoomIdArray);
		mav.addObject("labRoomSelectedList", labRoomSelectedList);

		// 获取所有学期
		Map<Integer,String> schoolTermMap = reportService.getTermsMap();
		mav.addObject("schoolTermMap",schoolTermMap);

		// 获取所有中心
		Map<Integer,String> labCenterMap = reportService.getAllLabCenterMap();
		mav.addObject("labCenterMap",labCenterMap);
		// 获取所有实验室
		Map<Integer,String> labRoomMap = reportService.getLabRoomsListMap(centerId,term);
		if(labRoomIdArray.length>0){
			for(String keyStr : labRoomIdArray){
				Integer tempInt = Integer.valueOf(keyStr);
				labRoomMap.remove(tempInt);
			}
		}
		mav.addObject("labRoomMap",labRoomMap);

		// 页面呈现
		int pageSize=50;
		if (request.getParameter("roomId") != null){
			// 获取页面呈现的列表数据
			List<Object[]> operationItemChangeLists = reportService.operationItemChangeViews(request, currpage, pageSize);//数组类型的list
			// 转化为容易页面呈现的形式（实验室）
			List<OperationItemByChange> items = reportService.operationItemChangeViews(operationItemChangeLists);
			mav.addObject("items", items);
		}else{
			// 获取页面呈现的列表数据
			List<Object[]> operationItemChangeList = reportService.operationItemChangeView(request, currpage, pageSize);//数组类型的list
			// 转化为容易页面呈现的形式
			List<OperationItemByChange> item = reportService.operationItemChangeView(operationItemChangeList);
			mav.addObject("item", item);
		}

		// 分页
		// 计算列表数据的总数
		int totalRecords = reportService.operationItemChangeViewCount(request);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("currpage", currpage);
		mav.addObject("pageSize", pageSize);
		mav.addObject("totalRecords", totalRecords);

		mav.setViewName("reports/reportOperationItemChange.jsp");
		return mav;
	}

	/**
	 * Description 实验变动统计表导出Excel
	 * @param request
	 * @param response
	 * @param termid
	 * @throws Exception
	 * @author 陈乐为 2018-7-23
	 */
	@RequestMapping("/exportOperationItemChange")
	public void exportOperationItemChange(HttpServletRequest request, HttpServletResponse response, Integer termid)throws Exception{
		reportService.exportOperationItemChange(request, response);
	}

	/**
	 * Description 系统报表{实验学时数汇总表}
	 * @author 陈乐为 2018-7-23
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/operationItemStudentAndHours")
	public ModelAndView operationItemStudentAndHours(HttpServletRequest request, @RequestParam Integer flag){
		ModelAndView mav = new ModelAndView();
		Set<String> terms = new HashSet<String>();

		String[] selectTerms = request.getParameterValues("term");
		if(selectTerms != null && selectTerms.length != 0){
			for(String t:selectTerms){
				terms.add(t);
			}
		}else {
			// 默认把当前学期选中
			SchoolTerm currterm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
			terms.add(currterm.getId()+"");
		}
		mav.addObject("selectedTerms", terms);
		// 所有学期
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("allTerms", schoolTerms);
		// 查询记录
		List<Object[]> studentAndHours = reportService.getOperationItemStudentAndHours(request,flag);
		mav.addObject("studentAndHours", studentAndHours);
		mav.setViewName("reports/operationItemStudentAndHours.jsp");
		mav.addObject("flag", flag);
		return mav;
	}

	/**
	 * Description 实验学时数统计报表导出
	 * @param request
	 * @param response
	 * @param flag
	 * @throws Exception
	 */
	@RequestMapping("/exportOperationItemStudentAndHours")
	public void  exportOperationItemStudentAndHours(HttpServletRequest request,HttpServletResponse response, @RequestParam Integer flag)throws Exception{
		reportService.exportOperationItemStudentAndHours(flag, request, response);
	}

	/****************************************************************************
	 * Description: 系统报表{实验室利用率报表--中心}
	 * @author 贺子龙
	 * @param termBack--从实验室利用率列表返回
	 * @date 2016-10-10
	 ****************************************************************************/
	@SuppressWarnings("unchecked")
	@RequestMapping("/reportLabRate")
	public ModelAndView reportLabRate(@RequestParam Integer termBack, HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		// 当前学期
		int termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (!EmptyUtil.isObjectEmpty(termBack)&&termBack!=-1) {
			termId = termBack;
		}
		// 选择
		if (!EmptyUtil.isStringEmpty(request.getParameter("term"))) {
			termId = Integer.parseInt(request.getParameter("term"));
		}
		mav.addObject("selectedTermId", termId);
		// 所有学期
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("selectTerms", schoolTerms);
		// 查询记录
		List<Object[]> labRates = reportService.getLabUseRate(request);
		// 所有中心
		List<LabCenter> centerList = labCenterService.findAllCenters();
		// 横坐标：中心名称
		String xAxis = "";
		if(labRates != null && labRates.size() > 0){
			for(Object[] o: labRates){
				xAxis+="'"+o[1]+"',";
			}
		}
		mav.addObject("xAxis", xAxis);
		mav.addObject("labRates", labRates);
		mav.setViewName("reports/reportLabRate.jsp");
		return mav;
	}

	/****************************************************************************
	 * Description: 系统报表{实验室利用率报表--实验室}
	 * @author 贺子龙
	 * @date 2016-10-10
	 ****************************************************************************/
	@SuppressWarnings("unchecked")
	@RequestMapping("/reportLabRateRoom")
	public ModelAndView reportLabRateRoom(@RequestParam Integer centerId, Integer termId){
		ModelAndView mav = new ModelAndView();
		// 查询记录
		List<Object[]> labRates = reportService.getLabUseRateRoom(centerId, termId);
		mav.addObject("labRates", labRates);
		// 中心傳遞
		mav.addObject("centerId", centerId);
		// 学期传递
		mav.addObject("termId", termId);
		mav.setViewName("reports/reportLabRateRoom.jsp");
		return mav;
	}

	/*****************************************************************************************
	 * @description：實驗室使用率報表導出--中心
	 * @param：termId 學期主鍵
	 * @author：陳樂為
	 * @date：2016-10-24
	 *****************************************************************************************/
	@SuppressWarnings("unchecked")
	@RequestMapping("/exportReportLabRate")
	public void exportReportLabRate(HttpServletRequest request, HttpServletResponse response)throws Exception{
		ModelAndView mav = new ModelAndView();
		// 查询记录
		List<Object[]> labRates = reportService.getLabUseRate(request);
		mav.addObject("labRates", labRates);

		reportService.exportReportLabRate(request, response, labRates, 1);
	}

	/*****************************************************************************************
	 * @description：實驗室使用率報表導出--實驗室
	 * @param：termId 學期主鍵
	 * @author：陳樂為
	 * @date：2016-10-24
	 *****************************************************************************************/
	@SuppressWarnings("unchecked")
	@RequestMapping("/exportReportLabRateRoom")
	public void exportReportLabRateRoom(HttpServletRequest request, HttpServletResponse response,
										@RequestParam Integer centerId, Integer termId)throws Exception{
		ModelAndView mav = new ModelAndView();
		// 查询记录
		List<Object[]> labRates = reportService.getLabUseRateRoom(centerId, termId);
		mav.addObject("labRates", labRates);

		reportService.exportReportLabRate(request, response, labRates, 2);
	}

	/**********************************************
	 * Description 视图-实验室任务及耗材统计表-呈现
	 * @author 陈乐为
	 * @date 2016.05.04
	 **********************************************/
	@SuppressWarnings("unchecked")
	@RequestMapping("/reportTaskAndConsume")
	public ModelAndView reportTaskAndConsume(HttpServletRequest request, @RequestParam Integer currpage, @ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView();
		// 查询模块

		//学期默认为当前学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		mav.addObject("term", term);

		//获取所有学期
		Map<Integer,String> schoolTermMap = reportService.getTermsMap();
		mav.addObject("schoolTermMap",schoolTermMap);

		int pageSize=50;
		// 获取页面呈现的列表数据
		List<Object[]> taskAndConsumeLists = reportService.viewTaskAndConsume(request, currpage, pageSize);//数组类型的list
		mav.addObject("taskAndConsumeLists", taskAndConsumeLists);
		// 计算列表数据的总数
		int totalRecords = reportService.viewTaskAndConsumeCount(request);

		// 分页模块
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("currpage", currpage);
		mav.addObject("pageSize", pageSize);
		mav.addObject("totalRecords", totalRecords);
		mav.setViewName("reports/reportTaskAndConsume.jsp");
		return mav;
	}

	/************************************************************
	 * Description 实验室任务及耗材统计表导出Excel
	 * @author 陈乐为
	 * @date 2016.05.04
	 ************************************************************/
	@RequestMapping("/exportTaskAndConsume")
	public void exportTaskAndConsume(HttpServletRequest request, HttpServletResponse response)throws Exception{
		reportService.exportTaskAndConsume(request, response);
	}

	/**********************************************
	 * Description 视图-实践教学课程细化表-呈现
	 * @author 陈乐为
	 * @date 2016.04.14
	 **********************************************/
	@SuppressWarnings("unchecked")
	@RequestMapping("/reportSchoolCourseDetailType")
	public ModelAndView reportSchoolCourseDetailType(HttpServletRequest request, @RequestParam Integer currpage, @ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView();
		// 查询模块

		//学期默认为当前学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		mav.addObject("term", term);
		// 实验中心
		int centerId = -1;
		if (request.getParameter("centerId")!=null && !request.getParameter("centerId").equals("")) {
			centerId = Integer.valueOf(request.getParameter("centerId"));
		}
		mav.addObject("centerId", centerId);
		// 课程
		String[] courseNoArray = {"0"};
		if (request.getParameter("courseNo") != null) {
			courseNoArray = request.getParameterValues("courseNo");
		}
		mav.addObject("courseNoArray", courseNoArray);
		// 课程类型
		String[] courseTypeIdArray = {"0"};
		if (request.getParameter("courseTypeId") != null) {
			courseTypeIdArray = request.getParameterValues("courseTypeId");
		}
		mav.addObject("courseTypeIdArray", courseTypeIdArray);
		//获取所有学期
		Map<Integer,String> schoolTermMap = reportService.getTermsMap();
		mav.addObject("schoolTermMap",schoolTermMap);
		// 获取所有中心
		Map<Integer,String> labCenterMap = reportService.getAllLabCenterMap();
		mav.addObject("labCenterMap",labCenterMap);
		//获取所有课程
		Map<String,String> schoolCourseMap = reportService.findSchoolCourseMapByQuery(term, centerId);
		mav.addObject("schoolCourseMap",schoolCourseMap);
		//获取所有课程类型
		Map<String,String> schoolCourseTypeMap = reportService.findAllSchoolCourseTypeMap();
		mav.addObject("schoolCourseTypeMap",schoolCourseTypeMap);
		int pageSize=50;
		// 获取页面呈现的列表数据
		List<Object[]> schoolCourseDetailTypeLists = reportService.viewSchoolCourseDetail(request, currpage, pageSize, centerId);//数组类型的list
		mav.addObject("schoolCourseDetailTypeLists", schoolCourseDetailTypeLists);

		// 计算列表数据的总数
		int totalRecords = reportService.viewSchoolCourseDetailCount(request, centerId);

		// 分页模块
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("currpage", currpage);
		mav.addObject("pageSize", pageSize);
		mav.addObject("totalRecords", totalRecords);
		mav.setViewName("reports/reportSchoolCourseDetailType.jsp");
		return mav;
	}

	/************************************************************************************
	 * Description 联动查询--学期与课程
	 * @author 陈乐为
	 * @date 2016.04.19
	 *************************************************************************************/
	@ResponseBody
	@RequestMapping("/findSchoolTermBySchoolCourse")
	public Map<String, String> findSchoolTermBySchoolCourse(@RequestParam String schoolTerm){
		return reportService.LinkSchoolTerm(schoolTerm);
	}

	/************************************************************************************
	 * Description 联动查询--学期与中心
	 * @author 陈乐为
	 * @date 2016.05.18
	 *************************************************************************************/
	@ResponseBody
	@RequestMapping("/findLabCenterBySchoolTerm")
	public Map<String, String> findLabCenterBySchoolTerm(@RequestParam String schoolTerm){
		return reportService.LinkSchoolTerms(schoolTerm);
	}

	/************************************************************
	 * Description 课程类型统计表导出Excel
	 * @author 陈乐为
	 * @date 2016.04.14
	 ************************************************************/
	@RequestMapping("/exportSchoolCourseDetailType")
	public void exportSchoolCourseDetailType(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("selected_academy") String acno)throws Exception{
		reportService.exportSchoolCourseDetailType(request, response, acno);
	}

	/*************************************************************************************
	 * Description 实验教学情况
	 * @author 李彬
	 * @date 2018-4-23
	 *************************************************************************************/
	@RequestMapping("/experimentTeaching")
	public ModelAndView experimentTeaching(Integer currpage, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		//分页大小
		Integer pageSize = 20;
		//所有学期
		List<SchoolTerm> allSchoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("allSchoolTerms",allSchoolTerms);
		//当前学期
		Integer termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		mav.addObject("termId",termId);
		//所有学院
		List<SchoolAcademy> allSchoolAcademys = shareService.findAllSchoolAcademys();
		mav.addObject("allSchoolAcademys",allSchoolAcademys);
		//所有实验室
		List<LabRoom> allLabRooms = shareService.findAllLabRooms();
		mav.addObject("allLabRooms",allLabRooms);

		//查询实验室教学情况汇总视图总条目数
		Integer totalRecords = reportService.countOperationItemReport(request);
		//设置分页
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		mav.addObject("pageModel",pageModel);

		//查询实验室教学情况汇总视图数据
		List<Object[]> operationItemReportData = reportService.getOperationItemReport(request, pageModel);
		mav.addObject("operationItemReportData",operationItemReportData);

		//汇总信息统计(课程数，课程类型，试验项目)
		Object[] countRecords = reportService.countOperationItemReportRecords(request);
		mav.addObject("countRecords",countRecords);

		mav.setViewName("reports/experimentTeachingBak.jsp");
		return mav;
	}

	/**
	 * 导出实验室教学情况汇总表
	 * @author 罗璇
	 * @date 2018年4月23日
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/exportOperationItemReport")
	public void exportOperationItemReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//视图记录
		List<Object[]> operationItemReport = reportService.getOperationItemReport(request);
		reportService.exportOperationItemReport(request,response,operationItemReport);

	}

	/*************************************************************************************
	 * Description 实验类型情况
	 * @author 李彬
	 * @date 2018-4-23
	 *************************************************************************************/
	@RequestMapping("/experimentTypeAcademy")
	public ModelAndView experimentTypeAcademy(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		// 获取学期列表
		List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
		int termId = shareService.getBelongsSchoolTerm(Calendar.getInstance())
				.getId();
		String sql = "select * from view_report_academytype_1 v where 1=1";
		// 学期查询
		if (!EmptyUtil.isStringEmpty(request.getParameter("term"))
				&& !request.getParameter("term").equals("-1")) {
			termId = Integer.parseInt(request.getParameter("term"));
			SchoolTerm schoolTerm = schoolTermDAO.findSchoolTermById(termId);
			sql += " and v.lp_term = " + termId ;
		}else{
			sql += " and v.lp_term = " + termId ;
		}
		Query query = entityManager.createNativeQuery(sql);
		mav.addObject("list",query.getResultList());
		mav.addObject("schoolTerms",schoolTerms);
		mav.setViewName("reports/experimentTypeAcademy.jsp");
		mav.addObject("xAxis", reportService.getAcademyNames());
		//mav.addObject("labRate", reportService.getAcademyLabRateStr(studentTimeSum, ratedCourseTimeTerm, labRoomCapacity));  //实验室利用率

		return mav;
	}

	/*************************************************************************************
	 * Description 实验类型情况
	 * @author 李彬
	 * @date 2018-4-23
	 *************************************************************************************/
	@RequestMapping("/experimentType")
	public ModelAndView experimentType(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		// 获取学期列表
		List<SchoolTerm> schoolTerms = outerApplicationService
				.getSchoolTermList();
		int termId = shareService.getBelongsSchoolTerm(Calendar.getInstance())
				.getId();
		String sql = "select * from view_report_labtypesdu_1 v where 1=1";
		// 学期查询
		if (!EmptyUtil.isStringEmpty(request.getParameter("term"))
				&& !request.getParameter("term").equals("-1")) {
			termId = Integer.parseInt(request.getParameter("term"));
			SchoolTerm schoolTerm = schoolTermDAO.findSchoolTermById(termId);

			sql += " and v.lp_term = " + termId ;
		}else{
			sql += " and v.lp_term = " + termId ;
		}
		Query query = entityManager.createNativeQuery(sql);
		mav.addObject("list",query.getResultList());
		mav.addObject("schoolTerms",schoolTerms);
		mav.setViewName("reports/experimentType.jsp");
		return mav;
	}

	/*************************************************************************************
	 * Description：实验室综合利用率
	 *
	 * @作者：魏诚
	 * @日期：2018-05-09
	 *************************************************************************************/
	@RequestMapping("/reportLabOpenRateInfo")
	public ModelAndView reportLabOpenRateInfo(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		// 获取学期列表
		List<SchoolTerm> schoolTerms = outerApplicationService
				.getSchoolTermList();
		int termId = shareService.getBelongsSchoolTerm(Calendar.getInstance())
				.getId();
		String sql = "select * from view_report_labratesdu_10 v where 1=1";
		// 学期查询
		if (!EmptyUtil.isStringEmpty(request.getParameter("term"))
				&& !request.getParameter("term").equals("-1")) {
			termId = Integer.parseInt(request.getParameter("term"));
			SchoolTerm schoolTerm = schoolTermDAO.findSchoolTermById(termId);
			sql += " and v.term_id = " + termId ;
		}else{
			//sql += " and v.term_id = " + termId ;
		}
		Query query = entityManager.createNativeQuery(sql);
		mav.addObject("list",query.getResultList());
		mav.addObject("schoolTerms",schoolTerms);
		mav.setViewName("reports/reportLabOpenRateInfo.jsp");
		return mav;
	}

	/*************************************************************************************
	 * Description：实验室开放情况
	 *
	 * @作者：魏诚
	 * @日期：2018-4-23
	 *************************************************************************************/
	@RequestMapping("/reportLabOpenInfo")
	public ModelAndView reportLabOpenInfo(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		// 获取学期列表
		List<SchoolTerm> schoolTerms = outerApplicationService
				.getSchoolTermList();
		int termId = shareService.getBelongsSchoolTerm(Calendar.getInstance())
				.getId();
		String sql = "select * from view_report_labratesdu_10 v where 1=1";
		// 学期查询
		if (!EmptyUtil.isStringEmpty(request.getParameter("term"))
				&& !request.getParameter("term").equals("-1")) {
			termId = Integer.parseInt(request.getParameter("term"));
			SchoolTerm schoolTerm = schoolTermDAO.findSchoolTermById(termId);
			sql += " and v.term_id = " + termId ;
		}else{
			//sql += " and v.term_id = " + termId ;
		}
		Query query = entityManager.createNativeQuery(sql);
		mav.addObject("list",query.getResultList());
		mav.addObject("schoolTerms",schoolTerms);
		mav.setViewName("reports/reportLabOpenInfo.jsp");
		return mav;
	}

	/****************************************************************************
	 * @Description: 绩效报表-督导教务实验课表{督导教务实验课表查看}
	 * @author: 张德冰
	 * @date 2018.09.18
	 ****************************************************************************/
	@RequestMapping("/reportSupervise")
	public ModelAndView listTakePost(HttpServletRequest request, @RequestParam Integer currpage, @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		// 获取学期列表
		List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
		mav.addObject("schoolTerm", schoolTerms);
		//获取查询条件
		String term = request.getParameter("term");
		mav.addObject("term", term);
		String courseName = request.getParameter("courseName");
		mav.addObject("courseName", courseName);
		String cName = request.getParameter("cName");
		mav.addObject("cName", cName);
		String academy = request.getParameter("academy");
		mav.addObject("currAcademy", academy);

		// 获取所有学院
		List<SchoolAcademy> schoolAcademyList = shareService.findAllSchoolAcademys();
		mav.addObject("academyList", schoolAcademyList);

//		//根据查询条件获取督导教务实验课表数据数量
//		Integer totalRecords=reportService.getCountReportSupervise(request, academy);
//		mav.addObject("totalRecords", totalRecords);
		//根据查询条件获取督导教务实验课表数据（分页）
		int pageSize = 10;
		List<Object[]> list = reportService.getReportSupervise(request, currpage, pageSize, academy);
		if (list.size() != 0)
			mav.addObject("list", list.subList((currpage - 1) * pageSize, currpage * pageSize > list.size() ? list.size() : currpage * pageSize));
		Integer totalRecords = list.size();
		mav.addObject("totalRecords", totalRecords);
		//获取分页
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		mav.addObject("pageModel",pageModel);

		mav.setViewName("reports/reportSupervise.jsp");
		return mav;
	}

	/****************************************************************************
	 * @Description: 绩效报表-督导教务实验课表-导出
	 * @author: 张德冰
	 * @date 2018.09.18
	 ****************************************************************************/
	@RequestMapping("/exportSupervise")
	public void exportSupervise(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("selected_academy") String acno) throws Exception {
		String academy = request.getParameter("academy");
		//根据查询条件获取督导教务实验课表数据（分页）
		List<Object[]> lists = reportService.getReportSupervise(request, 0,0, academy);
		List<Map> list = new ArrayList<Map>();
		for (Object[] obj : lists) {
			Map<String,Object> map = new HashMap<String, Object>();
			//系部
			map.put("cell1",obj[2]);
			//课程名称
			map.put("cell2",obj[3]);
			//性质
			map.put("cell3",obj[4]);
			//教师姓名
			map.put("cell4",obj[5]);
			//课程学分
			map.put("cell5",obj[16]);
			//总周次
			map.put("cell6",obj[15]);
			//总课时
			map.put("cell7",obj[17]);
			//人数
			map.put("cell8",obj[6]);
			//理论课时
			map.put("cell9",obj[18]);
			//上课时间
			map.put("cell10",obj[9]);
			//上课地点
			map.put("cell11",obj[10]);
			//教学班组成
			map.put("cell12",obj[8]);
			//实验室课时
			map.put("cell13",obj[19]);
			//实验上课时间
			map.put("cell14",obj[12]);
			//实验上课地点
			map.put("cell15",obj[11]);
			//实验室具体地点
			map.put("cell16",obj[13]);

			list.add(map);
		}
		//标题
		String title = "督导教务实验课表";
		//表头
		String[] hearders = new String[] {"系部","课程名称","性质","教师姓名",
				"课程学分","总周次","总课时","人数","理论课时",
				"上课时间","上课地点","教学班组成","实验室课时","实验上课时间","实验上课地点",
				"实验室具体地点"};
		//表头对应的key值
		String[] fields = new String[] {"cell1","cell2","cell3","cell4","cell5","cell6","cell7","cell8",
				"cell9","cell10","cell11","cell12","cell13","cell14","cell15","cell16"};
		//表数据
		TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportExcel(title, shareService.getUserDetail().getCname(), "", td);

	}

	/****************************************************************************
	 * @Description: 绩效报表-设备使用报表{查看}
	 * @author: 张德冰
	 * @date 2018-09-29
	 ****************************************************************************/
	@RequestMapping("/reportLabRoomDeviceUsed")
	public ModelAndView listLabRoomDeviceUsage(HttpServletRequest request, @ModelAttribute LabRoomDeviceReservation reservation,
											   @RequestParam int page, @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("reservation", reservation);
		// 获取当前登录人
		//User user = shareService.getUser();
		// 学期
		List<SchoolTerm> terms = shareService.findAllSchoolTerms();
		mav.addObject("terms", terms);
		// 查询出所有的设备设备预约记录
		List<LabRoomDeviceReservation> reservationLists = labRoomDeviceService.findLabRoomDeviceReservationByDeviceNumberAndCid(reservation, request, null,1, -1, 2, acno);
		int totalRecords = reservationLists.size();
		int pageSize = 20;// 每页10条记录
		// 分页信息
		Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
		// 根据分页信息查询出来的记录
		List<LabRoomDeviceReservation> reservationList = labRoomDeviceService.findLabRoomDeviceReservationByDeviceNumberAndCid(reservation, request, null, page, pageSize, 2, acno);
		mav.addObject("reservationList", reservationList);

		// 找出设备预约对应的所有的申请人、指导老师、设备管理员、实验室
		Map<String, String> reserveUsers = new HashMap<String, String>();
		Map<String, String> teachers = new HashMap<String, String>();
		Map<String, String> manageUsers = new HashMap<String, String>();
		Map<Integer, String> labrooms = new HashMap<Integer, String>();
		for (LabRoomDeviceReservation labRoomDeviceReservation : reservationLists) {
			if (labRoomDeviceReservation.getUserByReserveUser() != null) {
				reserveUsers.put(labRoomDeviceReservation.getUserByReserveUser().getUsername(), labRoomDeviceReservation.getUserByReserveUser().getCname());
			}
			if (labRoomDeviceReservation.getUserByTeacher() != null) {
				teachers.put(labRoomDeviceReservation.getUserByTeacher().getUsername(), labRoomDeviceReservation.getUserByTeacher().getCname());
			}
			if (labRoomDeviceReservation.getLabRoomDevice().getUser() != null) {
				manageUsers.put(labRoomDeviceReservation.getLabRoomDevice().getUser().getUsername(), labRoomDeviceReservation.getLabRoomDevice().getUser().getCname());
			}
			labrooms.put(labRoomDeviceReservation.getLabRoomDevice().getLabRoom().getId(), labRoomDeviceReservation.getLabRoomDevice().getLabRoom().getLabRoomName());
		}
		mav.addObject("reserveUsers", reserveUsers);
		mav.addObject("teachers", teachers);
		mav.addObject("manageUsers", manageUsers);
		mav.addObject("labrooms", labrooms);

		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", page);
		mav.addObject("pageSize", pageSize);

		// 起止时间
		String begintime= request.getParameter("begintime");
		String endtime=	request.getParameter("endtime");

		mav.addObject("begintime", begintime);
		mav.addObject("endtime", endtime);

		mav.setViewName("/reports/reportLabRoomDeviceUsed.jsp");
		return mav;
	}

	/****************************************************************************
	 * @Description: 绩效报表-设备使用报表{导出}
	 * @author: 张德冰
	 * @date 2018-09-30
	 ****************************************************************************/
	@RequestMapping("/exportReportLabRoomDeviceUsed")
	public void exportReportLabRoomDeviceUsed(HttpServletRequest request,HttpServletResponse response, @ModelAttribute LabRoomDeviceReservation reservation,
											  @ModelAttribute("selected_academy") String acno) throws Exception {
		labRoomDeviceService.exportLabRoomDeviceUsage(request, response, reservation, acno);

	}
	/*************************************************************************************
	 * Description 实验教学计划表——一级页面
	 * @author 刘博越
	 * @date 2019-5-10
	 *************************************************************************************/
	@RequestMapping("/listSchoolCourseDetail")
	public ModelAndView listSchoolCourseDetail(@RequestParam int currpage, @ModelAttribute SchoolCourseDetail schoolCourseDetail){
		ModelAndView mav = new ModelAndView();
		int pageSize = 10;
		List<Object[]> obj = reportService.getViewCourseDetail(schoolCourseDetail,currpage, pageSize);
		mav.addObject("listObj", obj);
		//分页总数
		int totalRecords = reportService.getViewCourseDetail(schoolCourseDetail, 0, -1).size();
		//下拉列表-课程名称
		mav.addObject("listCourseName", reportService.getViewCourseDetail(schoolCourseDetail, 0, -1));
		//查询条件-课程名称
		if(schoolCourseDetail != null && schoolCourseDetail.getSchoolCourse() != null) {
			mav.addObject("courseNo", schoolCourseDetail.getSchoolCourse().getCourseNo());
		}
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		// 当前学期
		SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		//学期列表
		List<SchoolTerm> termList = shareService.findAllSchoolTerm();
		mav.addObject("termList", termList);
		if(schoolCourseDetail != null && schoolCourseDetail.getSchoolTerm() != null) {
			mav.addObject("termId", schoolCourseDetail.getSchoolTerm().getId());
		}else {
			mav.addObject("termId", schoolTerm.getId());
		}
		mav.setViewName("reports/listSchoolCourseDetail.jsp");
		return mav;
	}
	/*****************************************************************
	 * Description 实验教学计划表-二级页面
	 *
	 * @author 刘博越
	 * @date 2019-5-10
	 *****************************************************************/
	@RequestMapping("/listExperimentTeachingPlan")
	public ModelAndView listExperimentTeachingPlan(@RequestParam String courseDetailNo,@RequestParam String course_number, int termId)throws ParseException {
		ModelAndView mav=new ModelAndView();
		//学期
		SchoolTerm schoolTerm = termDetailService.findTermById(termId);
		mav.addObject("schoolTerm", schoolTerm);
		//课程详细信息
		List<Object[]> courseDetail = reportService.getViewTimetableCourseDetail(courseDetailNo);
		if(courseDetail != null && courseDetail.size() > 0) {
			mav.addObject("courseDetail", courseDetail.get(0));
		}
		//课程项目信息
		List<Object[]> courseDetails = reportService.getListTimetableFull(courseDetailNo);
		//if(courseDetails != null && courseDetails.size() > 0) {
		mav.addObject("courseDetails", courseDetails);
		//}
		//当前登录人
		mav.addObject("cname", shareService.getUser().getCname());
		//当前日期
		Calendar currTime = Calendar.getInstance();
		mav.addObject("currTime", currTime);

		Map<String,String> yearCodes = reportService.findAllYearCodeMap();
		mav.addObject("yearCodes", yearCodes);

		mav.addObject("courseDetailNo", courseDetailNo);
		mav.addObject("course_number", course_number);
		mav.addObject("termId",termId);

		mav.setViewName("reports/ExperimentTeachingPlan.jsp");
		return mav;
	}
}
