package net.zjcclims.web.construction;


import net.zjcclims.constant.WordHandler;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.construction.*;
import net.zjcclims.service.system.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.BindException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Spring MVC controller that handles CRUD requests for ProjectStartedReport entities
 * 
 */

@Controller("ProjectStartedReportController")
@SessionAttributes("selected_academy")
public class ProjectStartedReportController {

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * DAO injected by Spring that manages ProjectCompletionItem entities
	 * 
	 */
	@Autowired
	private ProjectCompletionItemDAO projectCompletionItemDAO;

	/**
	 * DAO injected by Spring that manages ProjectDevice entities
	 * 
	 */
	@Autowired
	private ProjectDeviceDAO projectDeviceDAO;

	/**
	 * DAO injected by Spring that manages ProjectStartedReport entities
	 * 
	 */
	@Autowired
	private ProjectStartedReportDAO projectStartedReportDAO;

	/**
	 * Service injected by Spring that provides CRUD operations for ProjectStartedReport entities
	 * 
	 */
	@Autowired
	private ProjectStartedReportService projectStartedReportService;
	
	@Autowired
	private LabConstructAppService labConstructAppService;
	
	@Autowired
	AuthorityService authorityService;
	
	@Autowired
	ShareService shareService;
	@Autowired
	private LabConstructUserService labConstructUserService;
	@Autowired
	private ProjectAcceptanceApplicationService projectAcceptanceApplicationService;
	@Autowired
	private ProjectFeeListService projectFeeListService;
	@Autowired
	private ProjectDeviceService projectDeviceService;
	@Autowired
	private ProjectCompletionItemService projectCompletionItemService;
	@Autowired ProjectAcceptFeeListService projectAcceptFeeListService;
	@Autowired ProjectAcceptCompletionItemService projectAcceptCompletionItemService;
	@Autowired ProjectAcceptDeviceService projectAcceptDeviceService;
	@Autowired ProjectStartFeeListService projectStartFeeListService;
	@Autowired ProjectStartCompletionItemService projectStartCompletionItemService;
	@Autowired ProjectStartDeviceService projectStartDeviceService;
	@Autowired CProjectSourceService cProjectSourceService;
	@Autowired
	ProjectAcceptanceApplicationDAO projectAcceptanceApplicationDAO;
	@Autowired OperationItemDAO operationItemDAO;
	@Autowired ProjectStartFeeListDAO projectStartFeeListDAO;
	@Autowired ProjectStartDeviceDAO projectStartDeviceDAO;
	@Autowired ProjectStartCompletionItemDAO projectStartCompletionItemDAO;
	@Autowired CFundAppItemDAO cFundAppItemDAO;
	@Autowired SchoolDeviceDAO schoolDeviceDAO;
	@Autowired ProjectAcceptFeeListDAO projectAcceptFeeListDAO;
	@Autowired ProjectAcceptDeviceDAO projectAcceptDeviceDAO;
	@Autowired ProjectAcceptCompletionItemDAO projectAcceptCompletionItemDAO;
	@Autowired CommonDocumentDAO commonDocumentDAO;
	
	/**
	 * Register custom, context-specific property editors
	 * 
	 */
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
	 * 功能：实验室建设项目-实验室建设项目启动报告列表
	 * 作者：李德
	 * 时间：2015-03-05
	 ****************************************************************************/
	@RequestMapping("/labconstruction/listProjectStartedReport")
	public ModelAndView listProjectStartedReport(@ModelAttribute ProjectStartedReport projectStartedReport, @RequestParam int page,
			@ModelAttribute("selected_academy") String acno){
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();

		//查询表单的对象
		mav.addObject("projectStartedReport", projectStartedReport);
		//当前登录人为申请人
		mav.addObject("loginUser", shareService.getUserDetail());
		int pageSize=10;//每页20条记录
		//查询出来的总记录条数
		int totalRecords=projectStartedReportService.findAllProjectStartedReportByLabConstruct(projectStartedReport).size();
		//分页信息
		Map<String,Integer> pageModel =shareService.getPage(page, pageSize,totalRecords);
		//根据分页信息查询出来的记录
		List<ProjectStartedReport> listProjectStartedReport=projectStartedReportService.findAllProjectStartedReportByLabConstruct(projectStartedReport,page,pageSize);
		mav.addObject("listProjectStartedReport",listProjectStartedReport);
		mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", page);
		mav.addObject("pageSize", pageSize);
		mav.setViewName("labconstruction/listProjectStartedReport.jsp");
		return mav;

		
	}

	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目启动报告
	 * 作者：李德
	 * 时间：2015-03-20
	 ****************************************************************************/
	@RequestMapping("/labconstruction/editProjectStartedReport")
	public ModelAndView editProjectStartedReport(@RequestParam Integer idKey,
                                                 @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();

		// 获取登录用户信息
		mav.addObject("loginUser", shareService.getUserDetail());
		//获取启动报告
		ProjectStartedReport projectStartedReport = projectStartedReportDAO
		.findProjectStartedReportByPrimaryKey(idKey);		
		mav.addObject("projectStartedReport", projectStartedReport);
		//项目来源类别
		mav.addObject("CProjectSources", cProjectSourceService.findAllCProjectSource());
		//所在院系
		mav.addObject("SchoolAcademys", projectStartedReportService.findAllSchoolAcademy());
		//日期
		String date = "";
		if(projectStartedReport.getStartDate()!=null){
			date = shareService.format(projectStartedReport.getStartDate());
		}
		mav.addObject("date",date);
		//实验项目
		mav.addObject("Items", operationItemDAO.findAllOperationItems());
		//附件
		mav.addObject("CommonDocuments", projectStartedReportService.findCommonDocumentByProAppId(idKey));

		mav.setViewName("labconstruction/editProjectStartedReport.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目启动报告
	 * 作者：李德
	 * 时间：2015-03-20
	 ****************************************************************************/
	@RequestMapping("/labconstruction/viewProjectStartedReport")
	public ModelAndView viewProjectStartedReport(@RequestParam Integer idKey,
                                                 @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();

		// 获取登录用户信息
		mav.addObject("loginUser", shareService.getUserDetail());
        //获取启动报告信息
		mav.addObject("projectStartedReport", projectStartedReportDAO
				.findProjectStartedReportByPrimaryKey(idKey));
		//附件
		mav.addObject("CommonDocuments", projectStartedReportService.findCommonDocumentByProAppId(idKey));
		
		mav.setViewName("labconstruction/viewProjectStartedReport.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：下载文件 
	 * 作者：李德
	 * 时间：2015-07-13
	 ****************************************************************************/
	@RequestMapping("/labconstruction/projectStartedReport/downloadFile")
	public void downloadFile(int idkey, HttpServletRequest request,HttpServletResponse response) {
		shareService.downloadFileByDocumentId(idkey,request,response);
	}
	
	/****************************************************************************
	 * 功能：AJAX 上传实验室建设申请附件，并返回文档字符串信息
	 * 作者：李德
	 * 时间：2015-04-21
	 ****************************************************************************/
	@RequestMapping("/labconstruction/projectStartReport/uploadFile")
	public @ResponseBody
    String uploadFileStartReport(HttpServletRequest request, HttpServletResponse response, BindException errors) throws Exception {
		String s=projectStartedReportService.uploadFile(request,response);
		return shareService.htmlEncode(s);
	}
	
	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目启动报告
	 * 作者：李德
	 * 时间：2015-03-20
	 ****************************************************************************/
	@RequestMapping("/labconstruction/saveProjectStartedReport")
	public String saveProjectStartedReport(
            @ModelAttribute ProjectStartedReport projectStartedReport, String[] usernameArray,
            String[] sexArray, String[] ageArray, String[] positionArray, String[] jobtitleArray, String[] contentArray,
            int[] fundTypeArray, BigDecimal[] moneyArray, String[] explainArray, String[] schoolDeviceArray,
            int[] itemIdArray, String[] itemNameArray, int[] documentIdArray, String[] equipmentNameArray,
            String[] formatArray, BigDecimal[] amountArray, BigDecimal[] unitPriceArray) {
		//启动报告
		projectStartedReportService.save(projectStartedReport);
		
		//经费预算
		for (int i=0;i<fundTypeArray.length;i++) {
			ProjectStartFeeList fee=new ProjectStartFeeList();
			fee.setProjectStartedReport(projectStartedReport);
			//经费类型
			CFundAppItem fund=cFundAppItemDAO.findCFundAppItemByPrimaryKey(fundTypeArray[i]);
			fee.setCFundAppItem(fund);
			if(moneyArray.length>0){
				fee.setAmount(moneyArray[i]);
			}
			if(explainArray.length>0){
				fee.setBudgetaryItem(explainArray[i]);
			}
			projectStartFeeListDAO.store(fee);
		}
		//设备
		for(String i:schoolDeviceArray){
			ProjectStartDevice device=new ProjectStartDevice();
			device.setProjectStartedReport(projectStartedReport);
			//设备编号对应的设备
			SchoolDevice schoolDevice=schoolDeviceDAO.findSchoolDeviceByPrimaryKey(i);
			device.setEquipmentName(schoolDevice.getDeviceName());
			device.setFormat(schoolDevice.getDeviceFormat());
			//数量 单价 合计暂时不填写
			device.setAmount(new BigDecimal(1));
			device.setUnitPrice(schoolDevice.getDevicePrice());
			
			projectStartDeviceDAO.store(device);
		}
		//设备(input框新建的)
		if(equipmentNameArray.length>0){
			for (int i=0;i<equipmentNameArray.length;i++){
				ProjectStartDevice device=new ProjectStartDevice();
				device.setProjectStartedReport(projectStartedReport);
				device.setEquipmentName(equipmentNameArray[i]);
				if(formatArray.length>0){
					device.setFormat(formatArray[i]);
				}
				if(amountArray.length>0){
					device.setAmount(amountArray[i]);
				}
				if(unitPriceArray.length>0){
					device.setUnitPrice(unitPriceArray[i]);
				}
				projectStartDeviceDAO.store(device);
			}
		}
		//实验项目(下拉框选择的)
		for(int i:itemIdArray){
			ProjectStartCompletionItem projectItem=new ProjectStartCompletionItem();
			//实验项目id对应的实验项目
			if(i>0){
				OperationItem item=operationItemDAO.findOperationItemByPrimaryKey(i);
				projectItem.setProjectStartedReport(projectStartedReport);
				projectItem.setExperimentName(item.getLpName());
				projectItem.setIsOriginal("是");
				//保存申请的实验项目
				projectStartCompletionItemDAO.store(projectItem);
			}
		}
		//实验项目(input框新建的)
		for(String s:itemNameArray){
			ProjectStartCompletionItem projectItem=new ProjectStartCompletionItem();
			projectItem.setProjectStartedReport(projectStartedReport);
			projectItem.setExperimentName(s);
			projectItem.setIsOriginal("否");
			//保存申请的实验项目
			projectStartCompletionItemDAO.store(projectItem);
			
		}
		
		//申请附件
		for(int i:documentIdArray){
			//文档id对应的文档
			CommonDocument document=commonDocumentDAO.findCommonDocumentByPrimaryKey(i);
			document.setProjectStartedReportByProjectStartReportApproval(projectStartedReport);
			commonDocumentDAO.store(document);
		}
		
		return "redirect:/labconstruction/listProjectStartedReport?page=1&modelId=930";
	}
	/****************************************************************************
	 * 功能：删除启动报告申请的经费
	 * 作者：李德
	 * 时间：2015-04-16
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteProjectStartFeeList")
	public ModelAndView deleteProjectStartFeeList(@RequestParam Integer id, Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的启动报告的经费
		ProjectStartFeeList fee=projectStartFeeListDAO.findProjectStartFeeListByPrimaryKey(id);
		projectStartFeeListDAO.remove(fee);
		//AppId对应的启动报告
		ProjectStartedReport projectStartedReport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(AppId);
		//更新申请的经费
		List<ProjectStartFeeList> feeList=projectStartFeeListService.findProjectStartFeeListByProStartId(AppId);
		BigDecimal count=new BigDecimal("0.00");
		for (ProjectStartFeeList f : feeList) {
			count=count.add(f.getAmount());
		}
		//此处除以10000，使其单位为万元
		projectStartedReport.setFeeAmount(count.divide(new BigDecimal("10000.00")));
		projectStartedReportDAO.store(projectStartedReport);
		
		mav.setViewName("redirect:/labconstruction/editProjectStartedReport?idKey="+AppId+"&modelId=932");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：删除启动报告的设备
	 * 作者：李德
	 * 时间：2015-04-16
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteProjectStartDevice")
	public ModelAndView deleteProjectStartDevice(@RequestParam Integer id, Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的设备
		ProjectStartDevice device=projectStartDeviceDAO.findProjectStartDeviceByPrimaryKey(id);
		projectStartDeviceDAO.remove(device);
		mav.setViewName("redirect:/labconstruction/editProjectStartedReport?idKey="+AppId+"&modelId=932");
		return mav;
	}
	/****************************************************************************
	 * 功能：删除启动报告的实验项目
	 * 作者：李德
	 * 时间：2015-04-16
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteProjectStartCompletionItem")
	public ModelAndView deleteProjectStartCompletionItem(@RequestParam Integer id, Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的实验项目
		ProjectStartCompletionItem item=projectStartCompletionItemDAO.findProjectStartCompletionItemByPrimaryKey(id);
		projectStartCompletionItemDAO.remove(item);
		//***************************
		//此处还是没有删除掉operationItem里面新增的complex为2的记录，长期会导致operationItem里面出现大量无用数据
		//*****************************
		mav.setViewName("redirect:/labconstruction/editProjectStartedReport?idKey="+AppId+"&modelId=932");
		return mav;
	}
	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目启动报告
	 * 作者：李德
	 * 时间：2015-03-20
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteProjectStartedReport")
	public String deleteProjectStartedReport(@RequestParam Integer idKey) {
		//删除关联启动报告的验收申请及验收申请各子表
		List<ProjectAcceptanceApplication> listProjectAcceptanceApplication=projectAcceptanceApplicationService.findProjectAcceptanceApplicationByProAppId(idKey);
		for (ProjectAcceptanceApplication projectAcceptanceApplication : listProjectAcceptanceApplication)
		{
			//删除经费
			projectAcceptanceApplicationService.deleteProjectAcceptFeeListByAppId(projectAcceptanceApplication.getId());
			//删除设备
			projectAcceptanceApplicationService.deleteProjectAcceptDeviceByAppId(projectAcceptanceApplication.getId());
			//删除实验项目(此处暂不删除operationItem里面的新增的记录)
			projectAcceptanceApplicationService.deleteProjectAcceptCompletionItemByAppId(projectAcceptanceApplication.getId());
			//删除验收申请附件
			projectAcceptanceApplicationService.deleteProjectAcceptAppCommonDocumentByAppId(projectAcceptanceApplication.getId());
			//删除验收申请
			projectAcceptanceApplicationService.deleteProjectAcceptanceApplication(projectAcceptanceApplication);
		}
		
		//删除启动报告及各子表
		//删除经费
		projectStartedReportService.deleteProjectStartFeeListByReportId(idKey);
		//删除设备
		projectStartedReportService.deleteProjectStartDeviceByReportId(idKey);
		//删除实验项目
		projectStartedReportService.deleteProjectStartCompletionItemByReportId(idKey);
		//删除附件
		projectStartedReportService.deleteProjectStartReportCommonDocumentByAppId(idKey);
		//删除启动报告
		ProjectStartedReport projectStartedReport = projectStartedReportService
				.findProjectStartedReportByPrimaryKey(idKey);
		projectStartedReportService.deleteProjectStartedReport(projectStartedReport);

		return "redirect:/labconstruction/listProjectStartedReport?page=1&modelId=930";
	}
	
	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目验收申请
	 * 作者：李德
	 * 时间：2015-03-24
	 ****************************************************************************/
	@RequestMapping("/labconstruction/newProjectAcceptance")
	public ModelAndView newProjectAcceptance(@ModelAttribute ProjectAcceptanceApplication projectAcceptanceApplication,
                                             @RequestParam Integer idKey,
                                             @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();

		// 获取登录用户信息
		mav.addObject("loginUser", shareService.getUserDetail());
		//System.out.println(idKey);
		// 获取启动报告数据
		ProjectStartedReport projectStartedReport = projectStartedReportDAO
				.findProjectStartedReportByPrimaryKey(idKey);
		mav.addObject("projectStartedReport", projectStartedReport);
		//是否有该启动报告对应的验收申请,没有则插入一条数据
		ProjectAcceptanceApplication projectAcceptanceApplicationVary = new ProjectAcceptanceApplication();
		if (projectAcceptanceApplicationService.findProjectAcceptanceApplicationByProAppId(idKey).size()<1)
		{
			projectAcceptanceApplication.setProjectStartedReport(projectStartedReport);
			projectAcceptanceApplication.setCProjectSource(projectStartedReport.getCProjectSource());
			projectAcceptanceApplication.setProjectName(projectStartedReport.getProjectName());
			projectAcceptanceApplication.setOtherFee(projectStartedReport.getOtherFee());
			projectAcceptanceApplication.setFeeAmount(projectStartedReport.getFeeAmount());
			projectAcceptanceApplicationVary = projectAcceptanceApplicationService.save(projectAcceptanceApplication);
			
			//项目启动报告表经费子表数据插入验收申请的子表经费表
			if (projectAcceptFeeListService.findProjectAcceptFeeListByProAppId(projectAcceptanceApplicationVary.getId()).size()<1)
			{
				//读取启动报告表经费
				List<ProjectStartFeeList> listProjectStartFeeList=projectStartFeeListService.findProjectStartFeeListByProStartId(idKey);
				for (ProjectStartFeeList projectStartFeeList : listProjectStartFeeList)
				{
					ProjectAcceptFeeList projectAcceptFeeList = new ProjectAcceptFeeList();
					projectAcceptFeeList.setProjectAcceptanceApplication(projectAcceptanceApplicationVary);
					projectAcceptFeeList.setAmount(projectStartFeeList.getAmount());
					projectAcceptFeeList.setCFundAppItem(projectStartFeeList.getCFundAppItem());
					//System.out.println(projectAcceptFeeList.getAmount());
					projectAcceptFeeListService.save(projectAcceptFeeList);
				}
			}
			
			//启动报告表设备子表数据插入验收申请的子表设备表
			if (projectAcceptDeviceService.findProjectAcceptDeviceByProAppId(projectAcceptanceApplicationVary.getId()).size()<1)
			{
				//读取启动报告表子表设备表
				List<ProjectStartDevice> listProjectStartDevice=projectStartDeviceService.findProjectStartDeviceByProStartId(idKey);
				for (ProjectStartDevice projectStartDevice : listProjectStartDevice)
				{
					ProjectAcceptDevice projectAcceptDevice = new ProjectAcceptDevice();
					projectAcceptDevice.setProjectAcceptanceApplication(projectAcceptanceApplicationVary);
					projectAcceptDevice.setEquipmentName(projectStartDevice.getEquipmentName());
					projectAcceptDevice.setFormat(projectStartDevice.getFormat());
					projectAcceptDevice.setAmount(projectStartDevice.getAmount());
					projectAcceptDevice.setUnitPrice(projectStartDevice.getUnitPrice());
					//System.out.println(projectAcceptDevice.getEquipmentName());
					projectAcceptDeviceService.save(projectAcceptDevice);
				}
			}	
			
			//启动报告表开设实验项目子表数据插入验收申请的子表开设实验项目表
			if (projectAcceptCompletionItemService.findProjectAcceptCompletionItemByProAppId(projectAcceptanceApplicationVary.getId()).size()<1)
			{
				//读取启动报告表子表开设实验项目表
				List<ProjectStartCompletionItem> listProjectStartCompletionItem=projectStartCompletionItemService.findProjectStartCompletionItemByProStartId(idKey);
				for (ProjectStartCompletionItem projectStartCompletionItem : listProjectStartCompletionItem)
				{
					ProjectAcceptCompletionItem projectAcceptCompletionItem = new ProjectAcceptCompletionItem();
					projectAcceptCompletionItem.setProjectAcceptanceApplication(projectAcceptanceApplicationVary);
					projectAcceptCompletionItem.setExperimentName(projectStartCompletionItem.getExperimentName());
					//System.out.println(projectAcceptCompletionItem.getExperimentName());
					projectAcceptCompletionItemService.save(projectAcceptCompletionItem);
				}
			}

		}
		//验收申请信息
		ProjectAcceptanceApplication projectAcceptanceApplicationData=
				projectAcceptanceApplicationService.queryProjectAcceptanceApplicationByProAppId(idKey);
		mav.addObject("projectAcceptanceApplication",projectAcceptanceApplicationData);
		//System.out.println(projectAcceptanceApplicationData.getId());
		//验收申请经费
		mav.addObject("projectAcceptFeeLists", projectAcceptFeeListService.findProjectAcceptFeeListByProAppId(projectAcceptanceApplicationData.getId()));
		//验收申请设备
		mav.addObject("projectAcceptDevices", projectAcceptDeviceService.findProjectAcceptDeviceByProAppId(projectAcceptanceApplicationData.getId()));
		//验收申请开设实验项目		
		mav.addObject("projectAcceptCompletionItems", projectAcceptCompletionItemService.findProjectAcceptCompletionItemByProAppId(projectAcceptanceApplicationData.getId()));
		//项目来源
		mav.addObject("CProjectSources", cProjectSourceService.findAllCProjectSource());
		//专业名称
		mav.addObject("ID1", projectAcceptanceApplicationService.findAllSchoolMajor());
		//所在院系
		mav.addObject("SchoolAcademys", projectStartedReportService.findAllSchoolAcademy());
		//验收申请信息
		mav.addObject("idKeyReport", idKey);
		//实验项目
		mav.addObject("Items", operationItemDAO.findAllOperationItems());
		//课程名称
		mav.addObject("ID2", projectAcceptanceApplicationService.findAllSchoolCourse());
				
		mav.addObject("newFlag", true);
		mav.setViewName("labconstruction/newProjectAcceptance.jsp");

		return mav;
	}

	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目验收申请
	 * 作者：李德
	 * 时间：2015-03-24
	 ****************************************************************************/
	@RequestMapping("/labconstruction/saveProjectAcceptanceNew")
	public String saveProjectAcceptanceNew(
            @ModelAttribute ProjectAcceptanceApplication projectAcceptanceApplication, String[] usernameArray,
            String[] sexArray, String[] ageArray, String[] positionArray, String[] jobtitleArray, String[] contentArray,
            int[] fundTypeArray, BigDecimal[] moneyArray, String[] explainArray, String[] schoolDeviceArray,
            int[] itemIdArray, String[] itemNameArray, int[] documentIdArray, String[] equipmentNameArray,
            String[] formatArray, BigDecimal[] amountArray, BigDecimal[] unitPriceArray) {
		
		
		//System.out.println(projectAcceptanceApplication.getId());
		//获取验收申请数据库对象
		ProjectAcceptanceApplication projectAcceptanceApplicationVary = projectAcceptanceApplicationDAO.findProjectAcceptanceApplicationByPrimaryKey(projectAcceptanceApplication.getId());
		//将JSP表单的数据数据赋值数据库对象，project_id不变，仍为数据库的值
		projectAcceptanceApplicationVary.setCProjectSource(projectAcceptanceApplication.getCProjectSource());
		projectAcceptanceApplicationVary.setProjectName(projectAcceptanceApplication.getProjectName());
		projectAcceptanceApplicationVary.setRealityCompleteDate(projectAcceptanceApplication.getRealityCompleteDate());
		projectAcceptanceApplicationVary.setMajorName(projectAcceptanceApplication.getMajorName());
		projectAcceptanceApplicationVary.setCourseName(projectAcceptanceApplication.getCourseName());
		projectAcceptanceApplicationVary.setSchoolAcademy(projectAcceptanceApplication.getSchoolAcademy());
		projectAcceptanceApplicationVary.setExpectCompleteDate(projectAcceptanceApplication.getExpectCompleteDate());
		projectAcceptanceApplicationVary.setOriginalLabroomArea(projectAcceptanceApplication.getOriginalLabroomArea());
		projectAcceptanceApplicationVary.setOriginalLabroomAdd(projectAcceptanceApplication.getOriginalLabroomAdd());
		projectAcceptanceApplicationVary.setOriginalLabroomValue(projectAcceptanceApplication.getOriginalLabroomValue());
		projectAcceptanceApplicationVary.setOriginalLabroomItem(projectAcceptanceApplication.getOriginalLabroomItem());
		projectAcceptanceApplicationVary.setMajorAmount(projectAcceptanceApplication.getMajorAmount());
		projectAcceptanceApplicationVary.setCourseAmount(projectAcceptanceApplication.getCourseAmount());
		projectAcceptanceApplicationVary.setExpectLabItem(projectAcceptanceApplication.getExpectLabItem());
		projectAcceptanceApplicationVary.setRealityLabItem(projectAcceptanceApplication.getRealityLabItem());
		projectAcceptanceApplicationVary.setProjectSituation(projectAcceptanceApplication.getProjectSituation());
		projectAcceptanceApplicationVary.setProjectExpectedBenefits(projectAcceptanceApplication.getProjectExpectedBenefits());
		projectAcceptanceApplicationVary.setConstructCondition(projectAcceptanceApplication.getConstructCondition());
		projectAcceptanceApplicationVary.setActualBenefits(projectAcceptanceApplication.getActualBenefits());
		
		
		//System.out.println(projectAcceptanceApplicationVary.getCProjectSource());
		//System.out.println(projectAcceptanceApplicationVary.getProjectStartedReport());
		//System.out.println(projectAcceptanceApplicationVary.getProjectName());

		projectAcceptanceApplicationService.save(projectAcceptanceApplicationVary);
		
		//经费预算
		for (int i=0;i<fundTypeArray.length;i++) {
			ProjectAcceptFeeList fee=new ProjectAcceptFeeList();
			fee.setProjectAcceptanceApplication(projectAcceptanceApplicationVary);
			//经费类型
			CFundAppItem fund=cFundAppItemDAO.findCFundAppItemByPrimaryKey(fundTypeArray[i]);
			fee.setCFundAppItem(fund);
			if(moneyArray.length>0){
				fee.setAmount(moneyArray[i]);
			}
			if(explainArray.length>0){
				fee.setBudgetaryItem(explainArray[i]);
			}
			projectAcceptFeeListDAO.store(fee);
		}
		//设备
		for(String i:schoolDeviceArray){
			ProjectAcceptDevice device=new ProjectAcceptDevice();
			device.setProjectAcceptanceApplication(projectAcceptanceApplicationVary);
			//设备编号对应的设备
			SchoolDevice schoolDevice=schoolDeviceDAO.findSchoolDeviceByPrimaryKey(i);
			device.setEquipmentName(schoolDevice.getDeviceName());
			device.setFormat(schoolDevice.getDeviceFormat());
			//数量 单价 合计暂时不填写
			device.setAmount(new BigDecimal(1));
			device.setUnitPrice(schoolDevice.getDevicePrice());
			
			projectAcceptDeviceDAO.store(device);
		}
		//设备(input框新建的)
		if(equipmentNameArray.length>0){
			for (int i=0;i<equipmentNameArray.length;i++){
				ProjectAcceptDevice device=new ProjectAcceptDevice();
				device.setProjectAcceptanceApplication(projectAcceptanceApplicationVary);
				device.setEquipmentName(equipmentNameArray[i]);
				if(formatArray.length>0){
					device.setFormat(formatArray[i]);
				}
				if(amountArray.length>0){
					device.setAmount(amountArray[i]);
				}
				if(unitPriceArray.length>0){
					device.setUnitPrice(unitPriceArray[i]);
				}
				projectAcceptDeviceDAO.store(device);
			}
		}
		//实验项目(下拉框选择的)
		for(int i:itemIdArray){
			ProjectAcceptCompletionItem projectItem=new ProjectAcceptCompletionItem();
			//实验项目id对应的实验项目
			if(i>0){
				OperationItem item=operationItemDAO.findOperationItemByPrimaryKey(i);
				projectItem.setProjectAcceptanceApplication(projectAcceptanceApplicationVary);
				projectItem.setExperimentName(item.getLpName());
				projectItem.setIsOriginal("是");
				//保存申请的实验项目
				projectAcceptCompletionItemDAO.store(projectItem);
			}

		}
		//实验项目(input框新建的)
		for(String s:itemNameArray){
			ProjectAcceptCompletionItem projectItem=new ProjectAcceptCompletionItem();
			projectItem.setProjectAcceptanceApplication(projectAcceptanceApplicationVary);
			projectItem.setExperimentName(s);
			projectItem.setIsOriginal("否");
			//保存申请的实验项目
			projectAcceptCompletionItemDAO.store(projectItem);
			
		}
		
		//申请附件
		for(int i:documentIdArray){
			//文档id对应的文档
			CommonDocument document=commonDocumentDAO.findCommonDocumentByPrimaryKey(i);
			document.setProjectAcceptanceApplicationByProjectAcceptAppItem(projectAcceptanceApplicationVary);
			commonDocumentDAO.store(document);
		}
		
		return "redirect:/labconstruction/listProjectStartedReport?page=1&modelId=930";
	}
	
	/****************************************************************************
	 * 功能：删除验收申请的经费
	 * 作者：李德
	 * 时间：2015-04-17
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteProjectAcceptFeeListNew")
	public ModelAndView deleteProjectAcceptFeeListNew(@RequestParam Integer id, Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的启动报告的经费
		ProjectAcceptFeeList fee=projectAcceptFeeListDAO.findProjectAcceptFeeListByPrimaryKey(id);
		projectAcceptFeeListDAO.remove(fee);
		//AppId对应的启动报告
		ProjectAcceptanceApplication projectAcceptanceApplication = 
				projectAcceptanceApplicationService.queryProjectAcceptanceApplicationByProAppId(AppId);	
		//更新申请的经费
		List<ProjectAcceptFeeList> feeList=projectAcceptFeeListService.findProjectAcceptFeeListByProAppId(projectAcceptanceApplication.getId());
		BigDecimal count=new BigDecimal("0.00");
		for (ProjectAcceptFeeList f : feeList) {
			count=count.add(f.getAmount());
		}
		//此处除以10000，使其单位为万元
		projectAcceptanceApplication.setFeeAmount(count.divide(new BigDecimal("10000.00")));
		projectAcceptanceApplicationDAO.store(projectAcceptanceApplication);

		mav.setViewName("redirect:/labconstruction/newProjectAcceptance?idKey="+AppId+"&modelId=941");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：删除验收申请的设备
	 * 作者：李德
	 * 时间：2015-04-17
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteProjectAcceptDeviceNew")
	public ModelAndView deleteProjectAcceptDeviceNew(@RequestParam Integer id, Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的设备
		ProjectAcceptDevice device=projectAcceptDeviceDAO.findProjectAcceptDeviceByPrimaryKey(id);
		projectAcceptDeviceDAO.remove(device);
		mav.setViewName("redirect:/labconstruction/newProjectAcceptance?idKey="+AppId+"&modelId=941");
		return mav;
	}
	/****************************************************************************
	 * 功能：删除验收申请的实验项目
	 * 作者：李德
	 * 时间：2015-04-17
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteProjectAcceptCompletionItemNew")
	public ModelAndView deleteProjectAcceptCompletionItemNew(@RequestParam Integer id, Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的实验项目
		ProjectAcceptCompletionItem item=projectAcceptCompletionItemDAO.findProjectAcceptCompletionItemByPrimaryKey(id);
		projectAcceptCompletionItemDAO.remove(item);
		//***************************
		//此处还是没有删除掉operationItem里面新增的complex为2的记录，长期会导致operationItem里面出现大量无用数据
		//*****************************
		mav.setViewName("redirect:/labconstruction/newProjectAcceptance?idKey="+AppId+"&modelId=941");
		return mav;
	}
	
	/*********************************************************************************
	 * 功能：实验室建设项目启动报告-导出
	 * 作者：李德
	 * 时间：2015-03-26
	 ************************************************************************************/
	@RequestMapping("/labconstruction/exportProjectStartedReport")
	public void exportProjectStartedReport(@ModelAttribute ProjectStartedReport projectStartedReport,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.setContentType("application/excel;charset=UTF-8");
		// 获取所有的客户信息赋值给集合alls；
		List<ProjectStartedReport> projectStartedReports = projectStartedReportService.findAllProjectStartedReportByLabConstruct(projectStartedReport, 1, projectStartedReportService.findAllProjectStartedReportByLabConstruct(projectStartedReport).size());
		projectStartedReportService.exportExcelProjectStartedReport(projectStartedReports,request,response);
		
	}

	/****************************************************************************
	 * 功能：修改实验室建设启动报告的经费预算
	 * 作者：李德
	 * 时间：2015-04-17
	 ****************************************************************************/
	@RequestMapping("/labconstruction/updateProjectStartFeeList")
	public ModelAndView updateProjectStartFeeList(@RequestParam Integer id, Integer AppId, Integer flag)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的经费预算
		ProjectStartFeeList fee=projectStartFeeListDAO.findProjectStartFeeListByPrimaryKey(id);
		mav.addObject("fee", fee);
		//预算科目
		Set<CFundAppItem> fundTypes=cFundAppItemDAO.findAllCFundAppItems();
		mav.addObject("fundTypes", fundTypes);
		mav.addObject("flag", flag);
		//System.out.println(flag);
		mav.setViewName("labconstruction/app/editProjectStartFeeList.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：保存实验室建设启动报告的经费
	 * 作者：李德
	 * 时间：2015-04-17
	 ****************************************************************************/
	@RequestMapping("/labconstruction/saveUpdateProjectStartFeeList")
	public ModelAndView saveUpdatePrpjectFeeList(@ModelAttribute ProjectStartFeeList fee, @RequestParam Integer AppId, Integer flag)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//System.out.println(flag);
		//id对应的实验室建设申请
		ProjectStartedReport projectStartedReport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(AppId);
		
		fee.setProjectStartedReport(projectStartedReport);
		projectStartFeeListDAO.store(fee);
		//更新实验室建设申请的预算总额
		List<ProjectStartFeeList> feeList=projectStartFeeListService.findProjectStartFeeListByProStartId(AppId);
		BigDecimal count=new BigDecimal("0.00");
		for (ProjectStartFeeList f : feeList) {
			count=count.add(f.getAmount());
		}
		//此处除以10000，使其单位为万元
		projectStartedReport.setFeeAmount(count.divide(new BigDecimal("10000.00")));
		projectStartedReportDAO.store(projectStartedReport);
		//flag为1跳转启动报告新增页面，flag为2跳转启动报告编辑页面
		if(flag==1){
			int idkey = projectStartedReport.getLabConstructApp().getId();
			//System.out.println(idkey);
			mav.setViewName("redirect:/labconstruction/newProjectStartedReport?idKey="+idkey+"&modelId=931");
		}else{
			mav.setViewName("redirect:/labconstruction/editProjectStartedReport?idKey="+AppId+"&modelId=932");
		}
		return mav;
	}
	/****************************************************************************
	 * 功能：修改实验室建设启动报告的设备
	 * 作者：李德
	 * 时间：2015-04-17
	 ****************************************************************************/
	@RequestMapping("/labconstruction/updateProjectStartDevice")
	public ModelAndView updateProjectStartDevice(@RequestParam Integer id, Integer AppId, Integer flag)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的设备
		ProjectStartDevice device=projectStartDeviceDAO.findProjectStartDeviceByPrimaryKey(id);
		mav.addObject("device", device);
		mav.addObject("flag", flag);
		mav.setViewName("labconstruction/app/editProjectStartDevice.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：保存修改的实验室建设启动报告的设备
	 * 作者：李德
	 * 时间：2015-04-17
	 ****************************************************************************/
	@RequestMapping("/labconstruction/saveUpdateProjectStartDevice")
	public ModelAndView saveUpdateProjectStartDevice(@ModelAttribute ProjectStartDevice device, @RequestParam Integer AppId, Integer flag)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请
		ProjectStartedReport projectStartedReport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(AppId);

		device.setProjectStartedReport(projectStartedReport);
		projectStartDeviceDAO.store(device);
		//flag为1跳转启动报告新增页面，flag为2跳转启动报告编辑页面
		if(flag==1){
			int idkey = projectStartedReport.getLabConstructApp().getId();
			mav.setViewName("redirect:/labconstruction/newProjectStartedReport?idKey="+idkey+"&modelId=931");
		}else{
			mav.setViewName("redirect:/labconstruction/editProjectStartedReport?idKey="+AppId+"&modelId=932");
		}
		return mav;
	}
	/****************************************************************************
	 * 功能：修改实验室建设启动报告的项目
	 * 作者：李德
	 * 时间：2015-04-17
	 ****************************************************************************/
	@RequestMapping("/labconstruction/updateProjectStartCompletionItem")
	public ModelAndView updateProjectStartCompletionItem(@RequestParam Integer id, Integer AppId, Integer flag)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的项目
		ProjectStartCompletionItem item=projectStartCompletionItemDAO.findProjectStartCompletionItemByPrimaryKey(id);
		mav.addObject("item", item);
		//当前登录人所在学院的实验项目
		List<OperationItem> itemList=shareService.findOperationItemBySchoolAcademy(shareService.getUser().getSchoolAcademy().getAcademyNumber()) ;
		mav.addObject("itemList", itemList);
		mav.addObject("flag", flag);
		mav.setViewName("labconstruction/app/editProjectStartCompletionItem.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：保存修改的实验室建设启动报告的项目
	 * 作者：李德
	 * 时间：2015-04-17
	 ****************************************************************************/
	@RequestMapping("/labconstruction/saveUpdateProjectStartCompletionItem")
	public ModelAndView saveUpdateProjectStartCompletionItem(@ModelAttribute ProjectStartCompletionItem item, @RequestParam Integer AppId, Integer flag)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请项目
		ProjectStartedReport projectStartedReport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(AppId);
		
		item.setProjectStartedReport(projectStartedReport);
		projectStartCompletionItemDAO.store(item);
		//flag为1跳转启动报告新增页面，flag为2跳转启动报告编辑页面
		if(flag==1){
			int idkey = projectStartedReport.getLabConstructApp().getId();
			mav.setViewName("redirect:/labconstruction/newProjectStartedReport?idKey="+idkey+"&modelId=931");
		}else{
			mav.setViewName("redirect:/labconstruction/editProjectStartedReport?idKey="+AppId+"&modelId=932");
		}
		
		return mav;
	}
	
	/****************************************************************************
	 * 功能：删除实验室建设申请的文档
	 * 作者：李德
	 * 时间：2015-04-20
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteProjectStartedReportCommonDocument")
	public ModelAndView deleteCommonDocument(@RequestParam Integer id, Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的文档
		CommonDocument doc=commonDocumentDAO.findCommonDocumentByPrimaryKey(id);
		//文档保存路径
		String documentUrl=doc.getDocumentUrl();
		commonDocumentDAO.remove(doc);
		//***************************
		//此处还是没有删除掉服务器上upload目录下的文件，需要删掉服务器上的文件
		//*****************************
		shareService.deleteDocument(documentUrl);
		mav.setViewName("redirect:/labconstruction/editProjectStartedReport?idKey="+AppId+"&modelId=932");
		return mav;
	}

	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目启动导出word文档
	 * 作者：麦凯俊
	 * 时间：2018-8-17
	 ****************************************************************************/
	@RequestMapping("/downloadProjectStartedReport")
	public void downloadTeacherNeedWord(@RequestParam int idKey, HttpServletResponse response)throws Exception
	{
		response.setContentType("application/doc;charset=UTF-8");
		//id对应的实验室建设申请项目
		ProjectStartedReport report=projectStartedReportDAO.findProjectStartedReportByPrimaryKey(idKey);

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//转换日期格式

		Map map = new HashMap();
		//项目名称
		map.put("projectName",report.getProjectName());
		//申请人
		if(report.getLabConstructApp()!=null && report.getLabConstructApp().getUser()!=null && report.getLabConstructApp().getUser().getCname()!=null) {
			map.put("userName", report.getLabConstructApp().getUser().getCname());
		}else {
			map.put("userName", "");
		}
		//所在系（部）
		if(report.getSchoolAcademy()!=null && report.getSchoolAcademy().getAcademyName()!=null) {
			map.put("academyName", report.getSchoolAcademy().getAcademyName());
		}else {
			map.put("academyName", "");
		}
		//联系电话
		if(report.getLabConstructApp()!=null && report.getLabConstructApp().getUser()!=null && report.getLabConstructApp().getUser().getTelephone()!=null) {
			map.put("tel", report.getLabConstructApp().getUser().getTelephone());
		}else {
			map.put("tel", "");
		}
		//启动时间
		if(report.getLabConstructApp()!=null && report.getLabConstructApp().getAppDate()!=null){
			map.put("appDate",sdf.format(report.getLabConstructApp().getAppDate().getTime()));
		}else{
			map.put("appDate","");
		}
		//实训室地址及面积
		if(report.getLabAddress()!=null){
			map.put("labAddress",report.getLabAddress());
		}else{
			map.put("labAddress","");
		}

		//实训室地址及面积
		if(report.getLabArea()!=null){
			map.put("labArea",report.getLabArea().toString());
		}else{
			map.put("labArea","");
		}

		//申报经费
		if(report.getLabArea()!=null){
			map.put("freeApp",report.getFeeApp().toString());
		}else{
			map.put("freeApp","");
		}

		//批复经费
		if(report.getFeeAmount()!=null){
			map.put("freeAmount",report.getFeeAmount().toString());
		}else{
			map.put("freeAmount","");
		}

		//经费编号
		if(report.getFeeCode()!=null){
			map.put("freeCode",report.getFeeCode());
		}else{
			map.put("freeCode","");
		}

		//其他经费
		if(report.getOtherFee()!=null){
			map.put("otherFee",report.getOtherFee().toString());
		}else{
			map.put("otherFee","");
		}

		//预算科目集合
		if(report.getProjectStartFeeLists()!=null){
			map.put("projectStartFeeLists",report.getProjectStartFeeLists());
		}else{
			map.put("projectStartFeeLists","");
		}
		//设备集合
		if(report.getProjectStartDevices()!=null){
			map.put("projectStartDevices",report.getProjectStartDevices());
		}else{
			map.put("projectStartDevices","");
		}

		//建设后开设项目
		if(report.getProjectStartCompletionItems()!=null){
			map.put("projectStartCompletionItems",report.getProjectStartCompletionItems());
		}else{
			map.put("projectStartCompletionItems","");
		}

		//导出word
		WordHandler handler = new WordHandler();
 		File outFile =handler.createDoc("/net/zjcclims/ftl"
				, "start.ftl", map, "实验实训室项目启动报告");

		FileInputStream in = null;
		OutputStream o = null;
		try {
			byte b[] = new byte[1024];
			in = new FileInputStream(outFile);
			o = response.getOutputStream();
			response.setContentType("application/x-doc");
			response.setHeader("Content-disposition", "attachment; filename="
					+ URLEncoder.encode("report"+".doc", "UTF-8"));// 指定下载的文件名
			response.setHeader("Content_Length",String.valueOf( outFile.length()));       // download the file.
			int n = 0;
			while ((n = in.read (b))!= -1)
			{
				o.write(b, 0, n);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
				o.flush();
				o.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}