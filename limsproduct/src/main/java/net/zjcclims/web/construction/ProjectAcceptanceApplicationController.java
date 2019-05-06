package net.zjcclims.web.construction;


import net.zjcclims.common.RemoveHTML;
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
 * Spring MVC controller that handles CRUD requests for ProjectAcceptanceApplication entities
 * 
 */

@Controller("ProjectAcceptanceApplicationController")
@SessionAttributes("selected_academy")
public class ProjectAcceptanceApplicationController {

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * DAO injected by Spring that manages ProjectAcceptanceApplication entities
	 * 
	 */
	@Autowired
	private ProjectAcceptanceApplicationDAO projectAcceptanceApplicationDAO;

	/**
	 * DAO injected by Spring that manages ProjectCompletionItem entities
	 * 
	 */
	@Autowired
	private ProjectCompletionItemDAO projectCompletionItemDAO;

	/**
	 * Service injected by Spring that provides CRUD operations for ProjectAcceptanceApplication entities
	 * 
	 */
	@Autowired
	private ProjectAcceptanceApplicationService projectAcceptanceApplicationService;
	@Autowired
	AuthorityService authorityService;
	@Autowired
	ShareService shareService;
	@Autowired
	private LabConstructUserService labConstructUserService;
	@Autowired
	private ProjectFeeListService projectFeeListService;
	@Autowired
	private ProjectDeviceService projectDeviceService;
	@Autowired
	private ProjectCompletionItemService projectCompletionItemService;
	@Autowired
	private SchoolMajorDAO schoolMajorDAO;
	@Autowired
	private CProjectSourceService cProjectSourceService;
	@Autowired
	private ProjectStartedReportService projectStartedReportService;
	@Autowired
	CFundAppItemDAO cFundAppItemDAO;
	@Autowired OperationItemDAO operationItemDAO;
	@Autowired SchoolDeviceDAO schoolDeviceDAO;
	@Autowired ProjectAcceptFeeListDAO projectAcceptFeeListDAO;
	@Autowired ProjectAcceptDeviceDAO projectAcceptDeviceDAO;
	@Autowired ProjectAcceptCompletionItemDAO projectAcceptCompletionItemDAO;
	@Autowired ProjectAcceptFeeListService projectAcceptFeeListService;
	@Autowired CommonDocumentDAO commonDocumentDAO;
	@Autowired
	private SchoolCourseDAO schoolCourseDAO;
	
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
	 * 功能：实验室建设项目-实验室建设项目验收申请列表
	 * 作者：李德
	 * 时间：2015-03-23
	 ****************************************************************************/
	@RequestMapping("/labconstruction/listProjectAcceptance")
	public ModelAndView listProjectAcceptance(@ModelAttribute ProjectAcceptanceApplication projectAcceptanceApplication, @RequestParam int page,
			 @ModelAttribute("selected_academy") String acno){
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();


		//查询表单的对象
		mav.addObject("projectAcceptanceApplication", projectAcceptanceApplication);
		//当前登录人为申请人
		mav.addObject("loginUser", shareService.getUserDetail());
		int pageSize=10;//每页20条记录
		//查询出来的总记录条数
		int totalRecords=projectAcceptanceApplicationService.findAllProjectAcceptanceByLabConstruct(projectAcceptanceApplication).size();
		//分页信息
		Map<String,Integer> pageModel =shareService.getPage(page, pageSize,totalRecords);
		//根据分页信息查询出来的记录
		List<ProjectAcceptanceApplication> listProjectAcceptanceApplication=projectAcceptanceApplicationService.findAllProjectAcceptanceByLabConstruct(projectAcceptanceApplication,page,pageSize);
		mav.addObject("listProjectAcceptanceApplication",listProjectAcceptanceApplication);
		mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", page);
		mav.addObject("pageSize", pageSize);
		mav.setViewName("labconstruction/listProjectAcceptance.jsp");
		return mav;

		
	}

	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目申请列表
	 * 作者：李德
	 * 时间：2015-03-20
	 ****************************************************************************/
	@RequestMapping("/labconstruction/editProjectAcceptance")
	public ModelAndView editProjectAcceptance(@RequestParam Integer idKey,
                                              @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();

		// 获取登录用户信息
		mav.addObject("loginUser", shareService.getUserDetail());		
		//获取验收申请信息
		ProjectAcceptanceApplication projectAcceptanceApplication = projectAcceptanceApplicationDAO
				.findProjectAcceptanceApplicationByPrimaryKey(idKey);
		mav.addObject("projectAcceptanceApplication", projectAcceptanceApplication);
		//面向专业
		Set<SchoolMajor> schoolMajors=new HashSet<SchoolMajor>();
		if(projectAcceptanceApplication.getMajorName()!=null){
		    String[] arraySchoolMajor=projectAcceptanceApplication.getMajorName().split(",");
		    //读取本条数据的面向专业
	        for(String strAuth:arraySchoolMajor)
	        {		
		      SchoolMajor schoolMajor=schoolMajorDAO.findSchoolMajorByPrimaryKey(strAuth);
		      schoolMajors.add(schoolMajor);
	         }
		}
        mav.addObject("schoolMajors",schoolMajors);
        //获取所有专业，并对本条数据的面向专业设置为灰色
        Set<SchoolMajor> schoolMajorEdit=projectAcceptanceApplicationService.findAllSchoolMajor();
        schoolMajorEdit.remove(schoolMajors);
		mav.addObject("ID1", schoolMajorEdit);
		
		//课程名称
		Set<SchoolCourse> schoolCourses=new HashSet<SchoolCourse>();
		if(projectAcceptanceApplication.getCourseName()!=null){
			String[] arrayCourseName=projectAcceptanceApplication.getCourseName().split(",");
			for(String strAuth:arrayCourseName)
			{
				SchoolCourse schoolCourse=schoolCourseDAO.findSchoolCourseByPrimaryKey(strAuth);
				schoolCourses.add(schoolCourse);
			}
		}
		mav.addObject("schoolCourses",schoolCourses);
		Set<SchoolCourse> schoolCourseEdit=projectAcceptanceApplicationService.findAllSchoolCourse();
		schoolCourseEdit.remove(schoolCourses);
		mav.addObject("ID2", schoolCourseEdit);
		
		//项目来源
		mav.addObject("CProjectSources", cProjectSourceService.findAllCProjectSource());
		//所在院系
		mav.addObject("SchoolAcademys", projectStartedReportService.findAllSchoolAcademy());
		//实验项目
		mav.addObject("Items", operationItemDAO.findAllOperationItems());
		//附件
		mav.addObject("CommonDocuments", projectAcceptanceApplicationService.findCommonDocumentByProAppId(idKey));
		//日期
		String expectCompleteDate = "";
		String realityCompleteDate = "";
		String projectStartDate = "";
		if(projectAcceptanceApplication.getExpectCompleteDate()!=null){
			expectCompleteDate = shareService.format(projectAcceptanceApplication.getExpectCompleteDate());
		}
		mav.addObject("expectCompleteDate",expectCompleteDate);
		if(projectAcceptanceApplication.getRealityCompleteDate()!=null){
			realityCompleteDate = shareService.format(projectAcceptanceApplication.getRealityCompleteDate());
		}
		mav.addObject("realityCompleteDate",realityCompleteDate);
		
		if(projectAcceptanceApplication.getExpectCompleteDate()!=null){
			projectStartDate = shareService.format(projectAcceptanceApplication.getExpectCompleteDate());
		}
		mav.addObject("projectStartDate",projectStartDate);

		mav.setViewName("labconstruction/editProjectAcceptance.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目申请列表
	 * 作者：李德
	 * 时间：2015-03-20
	 ****************************************************************************/
	@RequestMapping("/labconstruction/viewProjectAcceptance")
	public ModelAndView viewProjectAcceptance(@RequestParam Integer idKey,
                                              @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();

		// 获取登录用户信息
		mav.addObject("loginUser", shareService.getUserDetail());
		// 获取验收申请信息
		ProjectAcceptanceApplication projectAcceptanceApplication = projectAcceptanceApplicationDAO
				.findProjectAcceptanceApplicationByPrimaryKey(idKey);
		mav.addObject("projectAcceptanceApplication", projectAcceptanceApplication);
		//面向专业
		Map map=new HashMap();
		//System.out.println("11111="+projectAcceptanceApplication.getMajorName());
		if(!(projectAcceptanceApplication.getMajorName()==null))
		{
		    Set<SchoolMajor> schoolMajors=new HashSet<SchoolMajor>();
		    String[] arraySchoolMajor=projectAcceptanceApplication.getMajorName().split(",");
		    //
	        for(String strAuth:arraySchoolMajor)
	        {		
		
		      SchoolMajor schoolMajor=schoolMajorDAO.findSchoolMajorByPrimaryKey(strAuth);
		      schoolMajors.add(schoolMajor);
	
	          //System.out.println("2222222="+schoolMajor);
	          map.put(projectAcceptanceApplication.getId(), schoolMajors);

	         }
		}
		
		mav.addObject("map",map);
		
		//面向专业
		Map courseMap=new HashMap();
		if(!(projectAcceptanceApplication.getCourseName()==null))
		{
			Set<SchoolCourse> schoolCourses=new HashSet<SchoolCourse>();
			String[] arrayCourseName=projectAcceptanceApplication.getCourseName().split(",");
			for(String strAuth:arrayCourseName)
			{
				SchoolCourse schoolCourse=schoolCourseDAO.findSchoolCourseByPrimaryKey(strAuth);
				schoolCourses.add(schoolCourse);
				courseMap.put(projectAcceptanceApplication.getId(), schoolCourses);
			}
		}
		mav.addObject("courseMap",courseMap);
		
		//附件
		mav.addObject("CommonDocuments", projectAcceptanceApplicationService.findCommonDocumentByProAppId(idKey));
		mav.setViewName("labconstruction/viewProjectAcceptance.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：下载文件 
	 * 作者：李德
	 * 时间：2015-07-13
	 ****************************************************************************/
	@RequestMapping("/labconstruction/projectAcceptance/downloadFile")
	public void downloadFile(int idkey, HttpServletRequest request,HttpServletResponse response) {
		shareService.downloadFileByDocumentId(idkey,request,response);
	}
	
	/****************************************************************************
	 * 功能：AJAX 上传实验室建设申请附件，并返回文档字符串信息
	 * 作者：李德
	 * 时间：2015-04-21
	 ****************************************************************************/
	@RequestMapping("/labconstruction/projectAcceptanceApp/uploadFile")
	public @ResponseBody
    String uploadFile(HttpServletRequest request, HttpServletResponse response, BindException errors) throws Exception {
		String s=projectAcceptanceApplicationService.uploadFile(request,response);
		return shareService.htmlEncode(s);
	}
	
	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目申请列表
	 * 作者：李德
	 * 时间：2015-03-20
	 ****************************************************************************/
	@RequestMapping("/labconstruction/saveProjectAcceptance")
	public String saveProjectAcceptance(
            @ModelAttribute ProjectAcceptanceApplication projectAcceptanceApplication, String[] usernameArray,
            String[] sexArray, String[] ageArray, String[] positionArray, String[] jobtitleArray, String[] contentArray,
            int[] fundTypeArray, BigDecimal[] moneyArray, String[] explainArray, String[] schoolDeviceArray,
            int[] itemIdArray, String[] itemNameArray, int[] documentIdArray, String[] equipmentNameArray,
            String[] formatArray, BigDecimal[] amountArray, BigDecimal[] unitPriceArray) {

		//除去html标签
		projectAcceptanceApplication.setProjectSituation(RemoveHTML.Html2Text(projectAcceptanceApplication.getProjectSituation()));
		projectAcceptanceApplication.setProjectExpectedBenefits(RemoveHTML.Html2Text(projectAcceptanceApplication.getProjectExpectedBenefits()));
		projectAcceptanceApplication.setConstructCondition(RemoveHTML.Html2Text(projectAcceptanceApplication.getConstructCondition()));
		projectAcceptanceApplication.setActualBenefits(RemoveHTML.Html2Text(projectAcceptanceApplication.getActualBenefits()));
		projectAcceptanceApplicationService.save(projectAcceptanceApplication);
		
		//经费预算
		for (int i=0;i<fundTypeArray.length;i++) {
			ProjectAcceptFeeList fee=new ProjectAcceptFeeList();
			fee.setProjectAcceptanceApplication(projectAcceptanceApplication);
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
			device.setProjectAcceptanceApplication(projectAcceptanceApplication);
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
				device.setProjectAcceptanceApplication(projectAcceptanceApplication);
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
			OperationItem item=operationItemDAO.findOperationItemByPrimaryKey(i);
			projectItem.setProjectAcceptanceApplication(projectAcceptanceApplication);
			projectItem.setExperimentName(item.getLpName());
			projectItem.setIsOriginal("是");
			//保存申请的实验项目
			projectAcceptCompletionItemDAO.store(projectItem);
		}
		//实验项目(input框新建的)
		for(String s:itemNameArray){
			ProjectAcceptCompletionItem projectItem=new ProjectAcceptCompletionItem();
			projectItem.setProjectAcceptanceApplication(projectAcceptanceApplication);
			projectItem.setExperimentName(s);
			projectItem.setIsOriginal("否");
			//保存申请的实验项目
			projectAcceptCompletionItemDAO.store(projectItem);
			
		}
		
		//申请附件
		for(int i:documentIdArray){
			//文档id对应的文档
			CommonDocument document=commonDocumentDAO.findCommonDocumentByPrimaryKey(i);
			document.setProjectAcceptanceApplicationByProjectAcceptAppItem(projectAcceptanceApplication);
			commonDocumentDAO.store(document);
		}
		
		return "redirect:/labconstruction/listProjectAcceptance?page=1&modelId=940";
	}

	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目申请列表
	 * 作者：李德
	 * 时间：2015-03-20
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteProjectAcceptance")
	public String deleteProjectAcceptance(@RequestParam Integer idKey) {
		//删除经费
		projectAcceptanceApplicationService.deleteProjectAcceptFeeListByAppId(idKey);
		//删除设备
		projectAcceptanceApplicationService.deleteProjectAcceptDeviceByAppId(idKey);
		//删除实验项目(此处暂不删除operationItem里面的新增的记录)
		projectAcceptanceApplicationService.deleteProjectAcceptCompletionItemByAppId(idKey);
		//删除附件
		projectAcceptanceApplicationService.deleteProjectAcceptAppCommonDocumentByAppId(idKey);
		//删除idkey对应的验收申请
		ProjectAcceptanceApplication projectAcceptanceApplication = projectAcceptanceApplicationService
				.findProjectAcceptanceApplicationByPrimaryKey(idKey);

		projectAcceptanceApplicationService.deleteProjectAcceptanceApplication(projectAcceptanceApplication);

		return "redirect:/labconstruction/listProjectAcceptance?page=1&modelId=940";
	}
	
	/****************************************************************************
	 * 功能：删除验收申请的经费
	 * 作者：李德
	 * 时间：2015-04-17
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteProjectAcceptFeeList")
	public ModelAndView deleteProjectAcceptFeeList(@RequestParam Integer id, Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的启动报告的经费
		ProjectAcceptFeeList fee=projectAcceptFeeListDAO.findProjectAcceptFeeListByPrimaryKey(id);
		projectAcceptFeeListDAO.remove(fee);
		//AppId对应的启动报告
		ProjectAcceptanceApplication projectAcceptanceApplication = projectAcceptanceApplicationDAO.findProjectAcceptanceApplicationByPrimaryKey(AppId);
		//更新申请的经费
		List<ProjectAcceptFeeList> feeList=projectAcceptFeeListService.findProjectAcceptFeeListByProAppId(AppId);
		BigDecimal count=new BigDecimal("0.00");
		for (ProjectAcceptFeeList f : feeList) {
			if(f.getAmount()!=null){
				count=count.add(f.getAmount());
			}
		}
		//此处除以10000，使其单位为万元
		projectAcceptanceApplication.setFeeAmount(count.divide(new BigDecimal("10000.00")));
		projectAcceptanceApplicationDAO.store(projectAcceptanceApplication);
	
		mav.setViewName("redirect:/labconstruction/editProjectAcceptance?idKey="+AppId+"&modelId=942");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：删除验收申请的设备
	 * 作者：李德
	 * 时间：2015-04-17
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteProjectAcceptDevice")
	public ModelAndView deleteProjectAcceptDevice(@RequestParam Integer id, Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的设备
		ProjectAcceptDevice device=projectAcceptDeviceDAO.findProjectAcceptDeviceByPrimaryKey(id);
		projectAcceptDeviceDAO.remove(device);
		mav.setViewName("redirect:/labconstruction/editProjectAcceptance?idKey="+AppId+"&modelId=942");
		return mav;
	}
	/****************************************************************************
	 * 功能：删除验收申请的实验项目
	 * 作者：李德
	 * 时间：2015-04-17
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteProjectAcceptCompletionItem")
	public ModelAndView deleteProjectAcceptCompletionItem(@RequestParam Integer id, Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的实验项目
		ProjectAcceptCompletionItem item=projectAcceptCompletionItemDAO.findProjectAcceptCompletionItemByPrimaryKey(id);
		projectAcceptCompletionItemDAO.remove(item);
		//***************************
		//此处还是没有删除掉operationItem里面新增的complex为2的记录，长期会导致operationItem里面出现大量无用数据
		//*****************************
		mav.setViewName("redirect:/labconstruction/editProjectAcceptance?idKey="+AppId+"&modelId=942");
		return mav;
	}
	
	
	/*********************************************************************************
	 * 功能：实验室建设项目启动报告-导出
	 * 作者：李德
	 * 时间：2015-03-27
	 ************************************************************************************/
	@RequestMapping("/labconstruction/exportProjectAcceptance")
	public void exportProjectAcceptance(@ModelAttribute ProjectAcceptanceApplication projectAcceptanceApplication,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.setContentType("application/excel;charset=UTF-8");
		// 获取所有的客户信息赋值给集合alls；
		List<ProjectAcceptanceApplication> projectAcceptanceApplications =
				projectAcceptanceApplicationService.findAllProjectAcceptanceByLabConstruct(
						projectAcceptanceApplication, 1, projectAcceptanceApplicationService.findAllProjectAcceptanceByLabConstruct(projectAcceptanceApplication).size());
		projectAcceptanceApplicationService.exportExcelProjectAcceptance(projectAcceptanceApplications,request,response);
		
	}
	/****************************************************************************
	 * 功能：修改实验室建设验收申请的经费预算
	 * 作者：李德
	 * 时间：2015-04-20
	 ****************************************************************************/
	@RequestMapping("/labconstruction/updateProjectAcceptFeeList")
	public ModelAndView updateProjectAcceptFeeList(@RequestParam Integer id, Integer AppId, Integer flag)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的经费预算
		ProjectAcceptFeeList fee=projectAcceptFeeListDAO.findProjectAcceptFeeListByPrimaryKey(id);
		mav.addObject("fee", fee);
		//预算科目
		Set<CFundAppItem> fundTypes=cFundAppItemDAO.findAllCFundAppItems();
		mav.addObject("fundTypes", fundTypes);
		mav.addObject("flag", flag);
		//System.out.println(flag);
		mav.setViewName("labconstruction/app/editProjectAcceptFeeList.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：保存实验室建设验收申请的经费
	 * 作者：李德
	 * 时间：2015-04-20
	 ****************************************************************************/
	@RequestMapping("/labconstruction/saveUpdateProjectAcceptFeeList")
	public ModelAndView saveUpdatePrpjectFeeList(@ModelAttribute ProjectAcceptFeeList fee, @RequestParam Integer AppId, Integer flag)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//System.out.println(flag);
		//id对应的实验室建设验收申请
		ProjectAcceptanceApplication projectAcceptanceApplication = projectAcceptanceApplicationDAO.findProjectAcceptanceApplicationByPrimaryKey(AppId);
		
		fee.setProjectAcceptanceApplication(projectAcceptanceApplication);
		projectAcceptFeeListDAO.store(fee);
		//更新实验室建设申请的预算总额
		List<ProjectAcceptFeeList> feeList=projectAcceptFeeListService.findProjectAcceptFeeListByProAppId(AppId);
		BigDecimal count=new BigDecimal("0.00");
		for (ProjectAcceptFeeList f : feeList) {
			count=count.add(f.getAmount());
		}
		//此处除以10000，使其单位为万元
		projectAcceptanceApplication.setFeeAmount(count.divide(new BigDecimal("10000.00")));
		projectAcceptanceApplicationDAO.store(projectAcceptanceApplication);
		//flag为1跳转验收申请新增页面，flag为2跳转验收申请编辑页面
		if(flag==1){
			int idkey = projectAcceptanceApplication.getProjectStartedReport().getId();
			//System.out.println(idkey);
			mav.setViewName("redirect:/labconstruction/newProjectAcceptance?idKey="+idkey+"&modelId=941");
		}else{
			mav.setViewName("redirect:/labconstruction/editProjectAcceptance?idKey="+AppId+"&modelId=942");
		}
		return mav;
	}
	/****************************************************************************
	 * 功能：修改实验室建设验收申请的设备
	 * 作者：李德
	 * 时间：2015-04-20
	 ****************************************************************************/
	@RequestMapping("/labconstruction/updateProjectAcceptDevice")
	public ModelAndView updateProjectAcceptDevice(@RequestParam Integer id, Integer AppId, Integer flag)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的设备
		ProjectAcceptDevice device=projectAcceptDeviceDAO.findProjectAcceptDeviceByPrimaryKey(id);
		mav.addObject("device", device);
		mav.addObject("flag", flag);
		mav.setViewName("labconstruction/app/editProjectAcceptDevice.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：保存修改的实验室建设验收申请的设备
	 * 作者：李德
	 * 时间：2015-04-20
	 ****************************************************************************/
	@RequestMapping("/labconstruction/saveUpdateProjectAcceptDevice")
	public ModelAndView saveUpdateProjectAcceptDevice(@ModelAttribute ProjectAcceptDevice device, @RequestParam Integer AppId, Integer flag)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设验收申请
		ProjectAcceptanceApplication projectAcceptanceApplication = projectAcceptanceApplicationDAO.findProjectAcceptanceApplicationByPrimaryKey(AppId);

		device.setProjectAcceptanceApplication(projectAcceptanceApplication);
		projectAcceptDeviceDAO.store(device);
		//flag为1跳转验收申请新增页面，flag为2跳转验收申请编辑页面
		if(flag==1){
			int idkey = projectAcceptanceApplication.getProjectStartedReport().getId();
			//System.out.println(idkey);
			mav.setViewName("redirect:/labconstruction/newProjectAcceptance?idKey="+idkey+"&modelId=941");
		}else{
			mav.setViewName("redirect:/labconstruction/editProjectAcceptance?idKey="+AppId+"&modelId=942");
		}
		return mav;
	}
	/****************************************************************************
	 * 功能：修改实验室建设验收申请的项目
	 * 作者：李德
	 * 时间：2015-04-20
	 ****************************************************************************/
	@RequestMapping("/labconstruction/updateProjectAcceptCompletionItem")
	public ModelAndView updateProjectAcceptCompletionItem(@RequestParam Integer id, Integer AppId, Integer flag)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的项目
		ProjectAcceptCompletionItem item=projectAcceptCompletionItemDAO.findProjectAcceptCompletionItemByPrimaryKey(id);
		mav.addObject("item", item);
		//当前登录人所在学院的实验项目
		List<OperationItem> itemList=shareService.findOperationItemBySchoolAcademy(shareService.getUser().getSchoolAcademy().getAcademyNumber()) ;
		mav.addObject("itemList", itemList);
		mav.addObject("flag", flag);
		mav.setViewName("labconstruction/app/editProjectAcceptCompletionItem.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：保存修改的实验室建设验收申请的项目
	 * 作者：李德
	 * 时间：2015-04-20
	 ****************************************************************************/
	@RequestMapping("/labconstruction/saveUpdateProjectAcceptCompletionItem")
	public ModelAndView saveUpdateProjectAcceptCompletionItem(@ModelAttribute ProjectAcceptCompletionItem item, @RequestParam Integer AppId, Integer flag)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设验收申请
		ProjectAcceptanceApplication projectAcceptanceApplication = projectAcceptanceApplicationDAO.findProjectAcceptanceApplicationByPrimaryKey(AppId);
		
		item.setProjectAcceptanceApplication(projectAcceptanceApplication);
		projectAcceptCompletionItemDAO.store(item);
		//flag为1跳转验收申请新增页面，flag为2跳转验收申请编辑页面
		if(flag==1){
			int idkey = projectAcceptanceApplication.getProjectStartedReport().getId();
			//System.out.println(idkey);
			mav.setViewName("redirect:/labconstruction/newProjectAcceptance?idKey="+idkey+"&modelId=941");
		}else{
			mav.setViewName("redirect:/labconstruction/editProjectAcceptance?idKey="+AppId+"&modelId=942");
		}
		
		return mav;
	}
	
	/****************************************************************************
	 * 功能：删除实验室建设申请的文档
	 * 作者：李德
	 * 时间：2015-04-20
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteProjectAcceptanceAppCommonDocument")
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
		mav.setViewName("redirect:/labconstruction/editProjectAcceptance?idKey="+AppId+"&modelId=942");
		return mav;
	}

	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目验收导出word文档
	 * 作者：麦凯俊
	 * 时间：2018-8-20
	 ****************************************************************************/
	@RequestMapping("/downloadProjectAcceptance")
	public void downloadProjectAcceptance(@RequestParam int idKey, HttpServletResponse response)throws Exception {
		response.setContentType("application/doc;charset=UTF-8");
		//id对应的实验室建设申请项目
		ProjectAcceptanceApplication acceptance=projectAcceptanceApplicationDAO.findProjectAcceptanceApplicationByPrimaryKey(idKey);

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//转换日期格式

		Map map = new HashMap();
		//项目名称
		map.put("projectName",acceptance.getProjectName());
		String userName = "";
		String tel = "";
		if(acceptance.getProjectStartedReport()!=null && acceptance.getProjectStartedReport().getLabConstructApp()!=null
				&& acceptance.getProjectStartedReport().getLabConstructApp().getUser()!=null){
			if(acceptance.getProjectStartedReport().getLabConstructApp().getUser().getCname()!=null) {
				//申请人
				userName = acceptance.getProjectStartedReport().getLabConstructApp().getUser().getCname();
			}
			if(acceptance.getProjectStartedReport().getLabConstructApp().getUser().getTelephone()!=null){
				tel = acceptance.getProjectStartedReport().getLabConstructApp().getUser().getTelephone();
			}
		}
		//申请人
		map.put("userName",userName);
		//所在系（部）
		if(acceptance.getSchoolAcademy()!=null && acceptance.getSchoolAcademy().getAcademyName()!=null) {
			map.put("academyName", acceptance.getSchoolAcademy().getAcademyName());
		}else {
			map.put("academyName", "");
		}
		//联系电话
		map.put("tel",tel);
		//申请时间
		if(acceptance.getProjectStartedReport()!=null && acceptance.getProjectStartedReport().getLabConstructApp()!=null && acceptance.getProjectStartedReport().getLabConstructApp().getAppDate()!=null){
			map.put("appDate",sdf.format(acceptance.getProjectStartedReport().getLabConstructApp().getAppDate().getTime()));
		}else{
			map.put("appDate","");
		}

		//立项时间
		if(acceptance.getProjectStartDate()!=null){
			map.put("startDate",sdf.format(acceptance.getProjectStartDate().getTime()));
		}else{
			map.put("startDate","");
		}

		//预期完成时间
		if(acceptance.getExpectCompleteDate()!=null){
			map.put("expectCompleteDate",sdf.format(acceptance.getExpectCompleteDate().getTime()));
		}else{
			map.put("expectCompleteDate","");
		}

		//实际完成情况
		if(acceptance.getRealityCompleteDate()!=null){
			map.put("realityCompleteDate",sdf.format(acceptance.getRealityCompleteDate().getTime()));
		}else{
			map.put("realityCompleteDate","");
		}

		//原实验室面积
		if(acceptance.getOriginalLabroomArea()!=null){
			map.put("originalLabroomArea",acceptance.getOriginalLabroomArea());
		}else{
			map.put("originalLabroomArea","");
		}
		//原实验室地点
		if(acceptance.getOriginalLabroomAdd()!=null){
			map.put("originalLabroomAdd",acceptance.getOriginalLabroomAdd());
		}else{
			map.put("originalLabroomAdd","");
		}
		//原实验室设备价值
		if(acceptance.getOriginalLabroomValue()!=null){
			map.put("originalLabroomValue",acceptance.getOriginalLabroomValue());
		}else{
			map.put("originalLabroomValue","");
		}
		//原实验室开设实验项目数
		if(acceptance.getOriginalLabroomItem()!=null){
			map.put("originalLabroomItem",acceptance.getOriginalLabroomItem());
		}else{
			map.put("originalLabroomItem","");
		}

		//建设后实验室面积
		if(acceptance.getTargetLabroomArea()!=null){
			map.put("targetLabroomArea",acceptance.getTargetLabroomArea());
		}else{
			map.put("targetLabroomArea","");
		}
		//建设后实验室地点
		if(acceptance.getTargetLabroomAdd()!=null){
			map.put("targetLabroomAdd",acceptance.getTargetLabroomAdd());
		}else{
			map.put("targetLabroomAdd","");
		}
		//建设后实验室设备价值
		if(acceptance.getTargetLabroomValue()!=null){
			map.put("targetLabroomValue",acceptance.getTargetLabroomValue());
		}else{
			map.put("targetLabroomValue","");
		}
		//建设后实验室开设实验项目数
		if(acceptance.getTargetLabroomItem()!=null){
			map.put("targetLabroomItem",acceptance.getTargetLabroomItem());
		}else{
			map.put("targetLabroomItem","");
		}

		//面向专业（方向）数
		if(acceptance.getMajorAmount()!=null){
			map.put("majorAmount",acceptance.getMajorAmount());
		}else{
			map.put("majorAmount","");
		}
		//专业名称
		if(acceptance.getMajorName()!=null) {
			List<SchoolMajor> schoolMajors=new ArrayList<SchoolMajor>();
			String[] arraySchoolMajor=acceptance.getMajorName().split(",");
			//
			for(String strAuth:arraySchoolMajor)
			{

				SchoolMajor schoolMajor=schoolMajorDAO.findSchoolMajorByPrimaryKey(strAuth);
				schoolMajors.add(schoolMajor);
			}
			map.put("schoolMajors",schoolMajors);
		}else{
			map.put("schoolMajors","");
		}

		//面向课程数
		if(acceptance.getCourseAmount()!=null){
			map.put("courseAmount",acceptance.getCourseAmount());
		}else{
			map.put("courseAmount","");
		}

		//课程名称
		if(acceptance.getCourseName()!=null){
			List<SchoolCourse> schoolCourses=new ArrayList<SchoolCourse>();
			String[] arrayCourseName=acceptance.getCourseName().split(",");
			for(String strAuth:arrayCourseName)
			{
				SchoolCourse schoolCourse=schoolCourseDAO.findSchoolCourseByPrimaryKey(strAuth);
				schoolCourses.add(schoolCourse);
			}
			map.put("schoolCourses",schoolCourses);
		}else{
			map.put("schoolCourses","");
		}

		//可开设实验室(实训)项目数
		if(acceptance.getExpectLabItem()!=null){
			map.put("expectLabItem",acceptance.getExpectLabItem());
		}else{
			map.put("expectLabItem","");
		}
		// 实际开设实验(实训)项目数
		if(acceptance.getRealityLabItem()!=null){
			map.put("realityLabItem",acceptance.getRealityLabItem());
		}else{
			map.put("realityLabItem","");
		}

		// 立项概况
		if(acceptance.getProjectSituation()!=null){
			map.put("projectSituation",acceptance.getProjectSituation());
		}else{
			map.put("projectSituation","");
		}

		// 立项预期效益
		if(acceptance.getProjectExpectedBenefits()!=null){
			map.put("projectExpectedBenefits",acceptance.getProjectExpectedBenefits());
		}else{
			map.put("projectExpectedBenefits","");
		}

		// 建设完成情况
		if(acceptance.getConstructCondition()!=null){
			map.put("constructCondition",acceptance.getConstructCondition());
		}else{
			map.put("constructCondition","");
		}

		// 实际效益
		if(acceptance.getActualBenefits()!=null){
			map.put("actualBenefits",acceptance.getActualBenefits());
		}else{
			map.put("actualBenefits","");
		}

		//设备费用清单
		if(acceptance.getProjectAcceptDevices()!=null){
			map.put("devicesList",acceptance.getProjectAcceptDevices());
		}else{
			map.put("devicesList","");
		}

		//建成后能开设的实验（实训）项目
		if(acceptance.getProjectAcceptCompletionItems()!=null){
			map.put("itemsList",acceptance.getProjectAcceptCompletionItems());
		}else{
			map.put("itemsList","");
		}


		//导出word
		WordHandler handler = new WordHandler();
		File outFile =handler.createDoc("/net/zjcclims/ftl", "acceptance_1.ftl", map, "实验实训室项目验收报告");

		FileInputStream in = null;
		OutputStream o = null;
		try {
			byte b[] = new byte[1024];
			in = new FileInputStream(outFile);
			o = response.getOutputStream();
			response.setContentType("application/x-doc");
			response.setHeader("Content-disposition", "attachment; filename="
					+ URLEncoder.encode("acceptance"+".doc", "UTF-8"));// 指定下载的文件名
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