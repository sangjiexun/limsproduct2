package net.zjcclims.web.construction;


import net.zjcclims.constant.WordHandler;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.*;
import net.zjcclims.service.construction.*;
import net.zjcclims.service.lab.*;
import net.zjcclims.service.system.*;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.zjcclims.web.common.PConfig;
/**
 * Spring MVC controller that handles CRUD requests for LabConstructApp entities
 * 
 */

@Controller("LabConstructAppController")
@SessionAttributes("selected_academy")
public class LabConstructAppController {

	/**
	 * DAO injected by Spring that manages CProjectPurpose entities
	 * 
	 */
	@Autowired
	private CProjectPurposeDAO cProjectPurposeDAO;

	/**
	 * DAO injected by Spring that manages CProjectSource entities
	 * 
	 */
	@Autowired
	private CProjectSourceDAO cProjectSourceDAO;

	/**
	 * DAO injected by Spring that manages ConstructionProject entities
	 * 
	 */
	@Autowired
	private ConstructionProjectDAO constructionProjectDAO;

	/**
	 * DAO injected by Spring that manages LabConstructAppApproval entities
	 * 
	 */
	@Autowired
	private LabConstructAppApprovalDAO labConstructAppApprovalDAO;

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
	 * DAO injected by Spring that manages User entities
	 * 
	 */
	@Autowired
	private UserDAO userDAO;

	/**
	 * Service injected by Spring that provides CRUD operations for LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppService labConstructAppService;
	
	@Autowired AuthorityService authorityService;
	
	@Autowired
	ShareService shareService;
	@Autowired
	private CProjectSourceService cProjectSourceService;
	@Autowired
	private CProjectPurposeService cProjectPurposeService;
	@Autowired
	private ProjectStartedReportService projectStartedReportService;
	@Autowired
	private ProjectAcceptanceApplicationService projectAcceptanceApplicationService;
	@Autowired
	LabCenterService labCenterService;
	@Autowired
	UserDetailService userDetailService;
	@Autowired ProjectDeviceListService projectDeviceListService;
	@Autowired CFundAppItemDAO cFundAppItemDAO;
	@Autowired SchoolDeviceDAO schoolDeviceDAO;
	@Autowired OperationItemDAO operationItemDAO;
	@Autowired ProjectFeeListDAO projectFeeListDAO;
	@Autowired CommonDocumentDAO commonDocumentDAO;
	@Autowired LabConstructUserDAO labConstructUserDAO;
	@Autowired ProjectStartFeeListService projectStartFeeListService;
	@Autowired ProjectFeeListService projectFeeListService;
	@Autowired ProjectStartDeviceService projectStartDeviceService;
	@Autowired ProjectStartCompletionItemService projectStartCompletionItemService;
	@Autowired ProjectDeviceService projectDeviceService;
	@Autowired LabConstructUserService labConstructUserService;
	@Autowired ProjectStartFeeListDAO projectStartFeeListDAO;
	@Autowired ProjectStartDeviceDAO projectStartDeviceDAO;
	@Autowired ProjectStartCompletionItemDAO projectStartCompletionItemDAO;
	@Autowired ProjectCompletionItemService projectCompletionItemService;
	@Autowired
	private SchoolMajorDAO schoolMajorDAO;
	@Autowired
	private SchoolCourseDAO schoolCourseDAO;
	@Autowired
	private SchoolAcademyDAO schoolAcademyDAO;
	@Autowired
	PConfig pConfig;
	
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
	 * 功能：实验室建设项目-实验室建设项目申请列表
	 * 作者：李德
	 * 时间：2015-03-05
	 ****************************************************************************/
	@RequestMapping("/labconstruction/listLabConstructionApp")
	public ModelAndView listLabConstructionApp(@ModelAttribute LabConstructApp labConstructApp, @RequestParam int page
			, @ModelAttribute("selected_academy") String acno){
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();


		//查询表单的对象
		mav.addObject("labConstructApp", labConstructApp);
		//当前登录人为申请人
		mav.addObject("loginUser", shareService.getUserDetail());
		int pageSize=10;//每页20条记录
		//查询出来的总记录条数
		int totalRecords=labConstructAppService.findAllLabConstructAppByLabConstructApp(labConstructApp).size();
		//分页信息
		Map<String,Integer> pageModel =shareService.getPage(page, pageSize,totalRecords);
		//根据分页信息查询出来的记录
		List<LabConstructApp> listLabConstructApp=labConstructAppService.findAllLabConstructAppByLabConstructApp(labConstructApp,page,pageSize);
		mav.addObject("listLabConstructApp",listLabConstructApp);
		mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", page);
		mav.addObject("pageSize", pageSize);
//		mav.addObject("cid", cid);

		//专业名称
		mav.addObject("ID1", projectAcceptanceApplicationService.findAllSchoolMajor());

		mav.setViewName("labconstruction/listLabConstructApp.jsp");
		return mav;

		
	}
	
	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目申请
	 * 作者：李德
	 * 时间：2015-03-03
	 ****************************************************************************/
	@RequestMapping("/labconstruction/newLabConstructionApp")
	public ModelAndView newLabConstructionApp(@ModelAttribute LabConstructApp labConstructApp,
			@ModelAttribute("selected_academy") String acno) {
		//System.out.println("hshhshhs1");
		ModelAndView mav = new ModelAndView();
		//当前登录人为申请人
		mav.addObject("loginUser", shareService.getUserDetail());
		//表单对象
		mav.addObject("labconstructapp", new LabConstructApp());
		
		//项目来源类别
		mav.addObject("CProjectSources", cProjectSourceService.findAllCProjectSource());
		//用途类别
		mav.addObject("CProjectPurposes", cProjectPurposeService.findAllCProjectPurpose());
		//实验项目
		mav.addObject("Items", operationItemDAO.findAllOperationItems());
		//专业名称
		mav.addObject("ID1", projectAcceptanceApplicationService.findAllSchoolMajor());
		//课程名称
		mav.addObject("ID2", projectAcceptanceApplicationService.findSchoolCoursesByTerm());
		//用途
		mav.addObject("ProjectPurposes", labConstructAppService.findAllProjectPurpose());
		
		mav.setViewName("labconstruction/newLabConstructApp.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目申请列表-编辑
	 * 作者：李德
	 * 时间：2015-03-18
	 ****************************************************************************/
	@RequestMapping("/labconstruction/editLabConstructionApp")
	public ModelAndView editLabConstructApp(@RequestParam Integer idKey,
			@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		LabConstructApp labConstructApp=labConstructAppDAO.findLabConstructAppByPrimaryKey(idKey);
		// 获取登录用户信息
		mav.addObject("loginUser", shareService.getUserDetail());
		//日期
		String date = "";
		if(labConstructApp.getAppDate()!=null){
			date = shareService.format(labConstructApp.getAppDate());
		}
		mav.addObject("date",date);
		// id对应的实验队伍
		mav.addObject("labConstructApp",labConstructApp);
		//项目来源类别
		mav.addObject("CProjectSources", cProjectSourceService.findAllCProjectSource());
		//实验项目
		mav.addObject("Items", operationItemDAO.findAllOperationItems());
		//面向专业
	    Set<SchoolMajor> schoolMajors=new HashSet<SchoolMajor>();
	    if(labConstructApp.getMajorName()!=null){
		    String[] arraySchoolMajor=labConstructApp.getMajorName().split(",");
		    //读取本条数据的面向专业
	        for(String strAuth:arraySchoolMajor)
	        {		
		      SchoolMajor schoolMajor=schoolMajorDAO.findSchoolMajorByPrimaryKey(strAuth);
		      schoolMajors.add(schoolMajor);

	          //System.out.println("2222222="+schoolMajor);
	         }
	    }
        mav.addObject("schoolMajors",schoolMajors);
        //获取所有专业，并对本条数据的面向专业设置为灰色
        Set<SchoolMajor> schoolMajorEdit=projectAcceptanceApplicationService.findAllSchoolMajor();
        schoolMajorEdit.remove(schoolMajors);
		mav.addObject("ID1", schoolMajorEdit);
		
		//课程名称
		Set<SchoolCourse> schoolCourses=new HashSet<SchoolCourse>();
		if(labConstructApp.getCourseName()!=null){
			String[] arrayCourseName=labConstructApp.getCourseName().split(",");
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
		
		//用途类别
		//mav.addObject("CProjectPurposes", cProjectPurposeService.findAllCProjectPurpose());
	    Set<CProjectPurpose> CProjectPurposes=new HashSet<CProjectPurpose>();
	    if(labConstructApp.getPurposeName()!=null){
		    String[] arrayCProjectPurpose=labConstructApp.getPurposeName().split(",");
		    //读取本条数据的用途
	        for(String strAuth:arrayCProjectPurpose)
	        {		
	        	Pattern pattern = Pattern.compile("[1-9]*");
	        	Matcher isNum = pattern.matcher(strAuth);
	        	//处理异常数据，例如",3，a，2"
	            if(strAuth.length()>0&&isNum.matches()){
		        	int intKey = Integer.parseInt(strAuth);
			        CProjectPurpose cProjectPurpose=cProjectPurposeDAO.findCProjectPurposeByPrimaryKey(intKey);
			        CProjectPurposes.add(cProjectPurpose);
	            }
	          //System.out.println("2222222="+cProjectPurpose);
	         }
	    }

        mav.addObject("CProjectPurposes",CProjectPurposes);
        //获取所有专业，并对本条数据的面向专业设置为灰色
        Set<CProjectPurpose> cProjectPurposeEdit=labConstructAppService.findAllProjectPurpose();
        cProjectPurposeEdit.remove(CProjectPurposes);
		mav.addObject("cProjectPurposeEdit", cProjectPurposeEdit);
		
        //获取设计者
        mav.addObject("user", labConstructAppService.getUsersMap(-1));
        mav.addObject("users", shareService.getUser());

		mav.setViewName("labconstruction/editLabConstructApp.jsp");
		return mav;
	}


	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目申请列表
	 * 作者：李德
	 * 时间：2015-03-20
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteLabConstructionApp")
	public String deleteLabConstructApp(@RequestParam Integer idKey) {
		//删除关联实验室建设申请的启动报告及各子表
		List<ProjectStartedReport> listProjectStartedReport=projectStartedReportService.findProjectStartedReportByLabConstructId(idKey);
		for (ProjectStartedReport projectStartedReport : listProjectStartedReport)
		{
			//删除关联启动报告的验收申请及验收申请各子表
			List<ProjectAcceptanceApplication> listProjectAcceptanceApplication = 
					projectAcceptanceApplicationService.findProjectAcceptanceApplicationByProAppId(projectStartedReport.getId());
			for (ProjectAcceptanceApplication projectAcceptanceApplication : listProjectAcceptanceApplication)
			{
				//删除经费
				projectAcceptanceApplicationService.deleteProjectAcceptFeeListByAppId(projectAcceptanceApplication.getId());
				//删除设备
				projectAcceptanceApplicationService.deleteProjectAcceptDeviceByAppId(projectAcceptanceApplication.getId());
				//删除实验项目
				projectAcceptanceApplicationService.deleteProjectAcceptCompletionItemByAppId(projectAcceptanceApplication.getId());
				//删除验收申请附件
				projectAcceptanceApplicationService.deleteProjectAcceptAppCommonDocumentByAppId(projectAcceptanceApplication.getId());
				//删除验收申请
				projectAcceptanceApplicationService.deleteProjectAcceptanceApplication(projectAcceptanceApplication);
			}
			
			//删除启动报告经费
			projectStartedReportService.deleteProjectStartFeeListByReportId(projectStartedReport.getId());
			//删除启动报告设备
			projectStartedReportService.deleteProjectStartDeviceByReportId(projectStartedReport.getId());
			//删除启动报告实验项目
			projectStartedReportService.deleteProjectStartCompletionItemByReportId(projectStartedReport.getId());
			//删除启动报告附件
			projectStartedReportService.deleteProjectStartReportCommonDocumentByAppId(projectStartedReport.getId());
			//删除启动报告
			projectStartedReportService.deleteProjectStartedReport(projectStartedReport);
		}
		//删除人员
		labConstructAppService.deleteLabConstructAppUserByAppId(idKey);
		//删除经费
		labConstructAppService.deleteProjectFeeListByAppId(idKey);
		//删除设备
		labConstructAppService.deleteProjectDeviceByAppId(idKey);
		//删除实验项目(此处暂不删除operationItem里面的新增的记录)
		labConstructAppService.deleteProjectCompletionItemByAppId(idKey);
		//删除审核附件
		labConstructAppService.deleteCommonDocumentByAppId(idKey);
		
		//接下来删除idkey对应的实验室建设申请
		LabConstructApp labConstructApp = labConstructAppService.findLabConstructAppByPrimaryKey(idKey);
		labConstructAppService.deleteLabConstructApp(labConstructApp);

		return "redirect:/labconstruction/listLabConstructionApp?page=1&modelId=901";
	}


	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目申请列表
	 * 作者：李德
	 * 时间：2015-03-10
	 ****************************************************************************/
	@RequestMapping("/labconstruction/saveLabConstructApp")
	public String saveLabConstructApp(@ModelAttribute LabConstructApp labConstructApp,String[] usernameArray,
			String[] sexArray,String[] ageArray,String[] positionArray,String[] jobtitleArray,String[] contentArray,
			int[] fundTypeArray,BigDecimal[] moneyArray,String[] explainArray,String[] schoolDeviceArray,
			int[] itemIdArray,String[] itemNameArray,int[] documentIdArray,String[] equipmentNameArray,
			String[] formatArray,BigDecimal[] amountArray,BigDecimal[] unitPriceArray) {
		
		//System.out.println(labConstructApp.getUser());
        User appliant;
		if(labConstructApp.getUser().equals(shareService.getUser())){
			//新建申请时，当前登录人为申请人
			labConstructApp.setUser(shareService.getUser());
			appliant = shareService.getUser();
		}else{
			labConstructApp.setUser(labConstructApp.getUser());
			appliant = labConstructApp.getUser();
		}
		//SchoolAcademy schoolAcademy=schoolAcademyDAO.findSchoolAcademyByPrimaryKey(appliant.getSchoolAcademy().getAcademyNumber());
		
		//生成新的编号
		labConstructApp.setLabConstructAppCode(labConstructAppService.getLabConstructAppCode());
		//给party_id主键设置一个初始值0
		labConstructApp.setPartyId(0);
		//将实验室建设申请保存到数据库
		System.out.println("labConstructApp:"+labConstructApp);
		LabConstructApp labConstruct=labConstructAppService.save(labConstructApp);
		System.out.println(usernameArray.length);
		//参与人员
		if(usernameArray.length>0){
			for(int i=0;i<usernameArray.length;i++){
				LabConstructUser conUser=new LabConstructUser();
				conUser.setCname(usernameArray[i]);
				if(sexArray.length>0){
					conUser.setSex(sexArray[i]);
				}
				if(ageArray.length>0){
					int age=Integer.parseInt(ageArray[i]);
					conUser.setAge(age);
				}
                if(positionArray.length>0){
                	conUser.setPosition(positionArray[i]);
                }
				
                if(jobtitleArray.length>0){
                	conUser.setJobTitle(jobtitleArray[i]);
                }
				
                if(contentArray.length>0){
                	conUser.setResponsibilityContent(contentArray[i]);
                }
                conUser.setLabConstructApp(labConstruct);
				labConstructAppService.save(conUser);
			}
		}
		
		//经费预算
		if(fundTypeArray.length>0){
			for (int i=0;i<fundTypeArray.length;i++) {
				System.out.println("i:"+i);
				ProjectFeeList fee=new ProjectFeeList();
				fee.setLabConstructApp(labConstruct);
				//经费类型
				CFundAppItem fund=cFundAppItemDAO.findCFundAppItemByPrimaryKey(fundTypeArray[i]);
				fee.setCFundAppItem(fund);
				System.out.println("moneyArray.length:"+moneyArray.length);
				if(moneyArray.length>0){
					fee.setAmount(moneyArray[i]);
				}
				if(explainArray.length>0){
					fee.setBudgetaryItem(explainArray[i]);
				}
				
				projectFeeListDAO.store(fee);
			}
		}

		//设备
		if(schoolDeviceArray.length>0){
			for(String i:schoolDeviceArray){
				ProjectDevice device=new ProjectDevice();
				device.setLabConstructApp(labConstruct);
				//设备编号对应的设备
				SchoolDevice schoolDevice=schoolDeviceDAO.findSchoolDeviceByPrimaryKey(i);
				device.setEquipmentName(schoolDevice.getDeviceName());
				device.setFormat(schoolDevice.getDeviceFormat());
				//数量 单价 合计暂时不填写
				device.setAmount(new BigDecimal(1));
				device.setUnitPrice(schoolDevice.getDevicePrice());
				
				projectDeviceDAO.store(device);
			}
		}
		//设备(input框新建的)
		if(equipmentNameArray.length>0){
			for (int i=0;i<equipmentNameArray.length;i++){
				ProjectDevice device=new ProjectDevice();
				device.setLabConstructApp(labConstruct);
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
				projectDeviceDAO.store(device);
			}
		}


		//实验项目(下拉框选择的)
		if(itemIdArray.length>0){
			System.out.println(itemIdArray.length);
			for(int i:itemIdArray){
				ProjectCompletionItem projectItem=new ProjectCompletionItem();
				//实验项目id对应的实验项目
				//System.out.println(i);
				if(i>0){
					OperationItem item=operationItemDAO.findOperationItemByPrimaryKey(i);
					projectItem.setLabConstructApp(labConstruct);
					//System.out.println(item.getItemName());
					projectItem.setExperimentName(item.getLpName());
					projectItem.setIsOriginal("是");
					//保存申请的实验项目
					projectCompletionItemDAO.store(projectItem);
				}
			}
		}
		//实验项目(input框新建的)
		if(itemNameArray.length>0){
			for(String s:itemNameArray){
				ProjectCompletionItem projectItem=new ProjectCompletionItem();
				projectItem.setLabConstructApp(labConstruct);
				projectItem.setExperimentName(s);
				projectItem.setIsOriginal("否");
				//保存申请的实验项目
				projectCompletionItemDAO.store(projectItem);
			}
		}
		
		//申请附件
		for(int i:documentIdArray){
			//文档id对应的文档
			CommonDocument document=commonDocumentDAO.findCommonDocumentByPrimaryKey(i);
			document.setLabConstructApp(labConstruct);
			commonDocumentDAO.store(document);
		}
		
		return "redirect:/labconstruction/listLabConstructionApp?page=1&modelId=901";
	}
	
	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目申请详细
	 * 作者：李德
	 * 时间：2015-04-20
	 ****************************************************************************/
	@RequestMapping("/labconstruction/viewLabConstructionApp")
	public ModelAndView viewLabConstructionApp(@RequestParam Integer idKey,
			@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();

		// 获取登录用户信息
		mav.addObject("loginUser", shareService.getUserDetail());
		// 获取实验室建设申请信息
		LabConstructApp labConstructApp = labConstructAppDAO
				.findLabConstructAppByPrimaryKey(idKey);
		mav.addObject("labConstructApp", labConstructApp);
		//面向专业
		Map map=new HashMap();
		//System.out.println("11111="+labConstructApp.getMajorName());
		if(!(labConstructApp.getMajorName()==null))
		{
		    Set<SchoolMajor> schoolMajors=new HashSet<SchoolMajor>();
		    String[] arraySchoolMajor=labConstructApp.getMajorName().split(",");
		    //
	        for(String strAuth:arraySchoolMajor)
	        {			
		      SchoolMajor schoolMajor=schoolMajorDAO.findSchoolMajorByPrimaryKey(strAuth);
		      schoolMajors.add(schoolMajor);
	          //System.out.println("2222222="+schoolMajor);
	          map.put(labConstructApp.getId(), schoolMajors);

	         }
		}
		mav.addObject("map",map);
		
		//面向专业
		Map courseMap=new HashMap();
		if(!(labConstructApp.getCourseName()==null))
		{
			Set<SchoolCourse> schoolCourses=new HashSet<SchoolCourse>();
			String[] arrayCourseName=labConstructApp.getCourseName().split(",");
			for(String strAuth:arrayCourseName)
			{
				SchoolCourse schoolCourse=schoolCourseDAO.findSchoolCourseByPrimaryKey(strAuth);
				schoolCourses.add(schoolCourse);
				courseMap.put(labConstructApp.getId(), schoolCourses);
			}
		}
		mav.addObject("courseMap",courseMap);
		
		//用途
		Map purposeMap=new HashMap();
		//System.out.println("11111="+labConstructApp.getPurposeName());
		if(!(labConstructApp.getPurposeName()==null))
		{
		    Set<CProjectPurpose> cProjectPurposes=new HashSet<CProjectPurpose>();
		    String[] arrayProjectPurpose=labConstructApp.getPurposeName().split(",");
		    //
	        for(String strAuth:arrayProjectPurpose)
	        {	
	        	Pattern pattern = Pattern.compile("[1-9]*");
	        	Matcher isNum = pattern.matcher(strAuth);
	        	//System.out.println(isNum.matches());
	        	//System.out.println(strAuth.matches("[0-9]*"));
	        	//System.out.println(strAuth.length());
	        	//处理异常数据，例如",3，a，2"
	            if(strAuth.length()>0&&isNum.matches()){
		        	int intKey = Integer.parseInt(strAuth);
		            CProjectPurpose cProjectPurpose=cProjectPurposeDAO.findCProjectPurposeByPrimaryKey(intKey);
		            cProjectPurposes.add(cProjectPurpose);
		            //System.out.println("2222222="+cProjectPurpose);
		            purposeMap.put(labConstructApp.getId(), cProjectPurposes);
	            }

	         }
		}
		mav.addObject("purposeMap",purposeMap);

		mav.setViewName("labconstruction/viewLabConstructApp.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：下载文件 
	 * 作者：李德
	 * 时间：2015-07-13
	 ****************************************************************************/
	@RequestMapping("/labconstruction/labConstructApp/downloadFile")
	public void downloadFile(int idkey, HttpServletRequest request,HttpServletResponse response) {
		shareService.downloadFileByDocumentId(idkey,request,response);
	}
	
	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目启动报告
	 * 作者：李德
	 * 时间：2015-03-20
	 ****************************************************************************/
	@RequestMapping("/labconstruction/newProjectStartedReport")
	public ModelAndView newProjectStartedReport(@ModelAttribute ProjectStartedReport projectStartedReport,
			@RequestParam Integer idKey,
			@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		
		// 获取登录用户信息
		mav.addObject("loginUser", shareService.getUserDetail());
		
		//申请表信息
		LabConstructApp labConstructApp = labConstructAppDAO
				.findLabConstructAppByPrimaryKey(idKey);
		mav.addObject("labConstructApp", labConstructApp);
		//是否有该申请对应的启动报告,没有则插入一条数据
		ProjectStartedReport projectStartedReportVary = new ProjectStartedReport();
		if (projectStartedReportService.findProjectStartedReportByLabConstructId(idKey).size()<1)
		{
		    projectStartedReport.setLabConstructApp(labConstructApp);
		    projectStartedReport.setProjectName(labConstructApp.getProjectName());
		    projectStartedReport.setCProjectSource(labConstructApp.getCProjectSource());
		    projectStartedReport.setFeeAmount(labConstructApp.getFeeAmount());
		    projectStartedReport.setOtherFee(labConstructApp.getOtherFee());
		    projectStartedReport.setSchoolAcademy(labConstructApp.getUser().getSchoolAcademy());
		    //projectStartedReportService.save(projectStartedReport);
		    projectStartedReportVary = projectStartedReportService.save(projectStartedReport);
			//项目申请表经费子表数据插入启动报告的子表经费表
			//System.out.println("projectStartedReport.getId()="+projectStartedReportVary.getId());
			if (projectStartFeeListService.findProjectStartFeeListByProStartId(projectStartedReportVary.getId()).size()<1)
			{
				//读取项目申请表经费
				List<ProjectFeeList> listProjectFeeList=projectFeeListService.findProjectFeeListByAppKey(idKey);
				for (ProjectFeeList projectFeeList : listProjectFeeList)
				{
					ProjectStartFeeList projectStartFeeList = new ProjectStartFeeList();
					projectStartFeeList.setProjectStartedReport(projectStartedReportVary);
					projectStartFeeList.setAmount(projectFeeList.getAmount());
					projectStartFeeList.setCFundAppItem(projectFeeList.getCFundAppItem());
					//System.out.println(projectStartFeeList.getAmount());
					projectStartFeeListService.save(projectStartFeeList);
				}
			}
			//项目申请表设备子表数据插入启动报告的子表设备表
			if (projectStartDeviceService.findProjectStartDeviceByProStartId(projectStartedReportVary.getId()).size()<1)
			{
				//读取项目申请表子表设备表
				List<ProjectDevice> listProjectDevice=projectDeviceService.findProjectDeviceByAppKey(idKey);
				for (ProjectDevice projectDevice : listProjectDevice)
				{
					ProjectStartDevice projectStartDevice = new ProjectStartDevice();
					projectStartDevice.setProjectStartedReport(projectStartedReportVary);
					projectStartDevice.setEquipmentName(projectDevice.getEquipmentName());
					projectStartDevice.setFormat(projectDevice.getFormat());
					projectStartDevice.setAmount(projectDevice.getAmount());
					projectStartDevice.setUnitPrice(projectDevice.getUnitPrice());
					//System.out.println(projectStartDevice.getEquipmentName());
					projectStartDeviceService.save(projectStartDevice);
				}
			}
			//项目申请表开设实验项目子表数据插入启动报告的子表开设实验项目表
			if (projectStartCompletionItemService.findProjectStartCompletionItemByProStartId(projectStartedReportVary.getId()).size()<1)
			{
				//读取项目申请表子表开设实验项目表
				List<ProjectCompletionItem> listProjectCompletionItem=projectCompletionItemService.findProjectCompletionItemByAppKey(idKey);
				for (ProjectCompletionItem projectCompletionItem : listProjectCompletionItem)
				{
					ProjectStartCompletionItem projectStartCompletionItem = new ProjectStartCompletionItem();
					projectStartCompletionItem.setProjectStartedReport(projectStartedReportVary);
					projectStartCompletionItem.setExperimentName(projectCompletionItem.getExperimentName());
					//System.out.println(projectStartCompletionItem.getExperimentName());
					projectStartCompletionItemService.save(projectStartCompletionItem);
				}
			}
			
		}

		//启动报告信息
		ProjectStartedReport projectStartedReportData=projectStartedReportService.queryProjectStartedReportByReportId(idKey);
		mav.addObject("projectStartedReport", projectStartedReportData);
		//启动报告经费
		mav.addObject("projectStartFeeLists", projectStartFeeListService.findProjectStartFeeListByProStartId(projectStartedReportData.getId()));
		//启动报告设备
		mav.addObject("projectStartDevices", projectStartDeviceService.findProjectStartDeviceByProStartId(projectStartedReportData.getId()));
		//启动报告开设实验项目		
		mav.addObject("projectStartCompletionItems", projectStartCompletionItemService.findProjectStartCompletionItemByProStartId(projectStartedReportData.getId()));
		//项目来源类别
		mav.addObject("CProjectSources", cProjectSourceService.findAllCProjectSource());
		//用途类别
		mav.addObject("CProjectPurposes", cProjectPurposeService.findAllCProjectPurpose());
		//所在院系
		mav.addObject("SchoolAcademys", projectStartedReportService.findAllSchoolAcademy());
		//实验项目
		mav.addObject("Items", operationItemDAO.findAllOperationItems());

		
		mav.addObject("newFlag", true);
		mav.setViewName("labconstruction/newProjectStartedReport.jsp");

		return mav;
	}

	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目启动
	 * 作者：李德
	 * 时间：2015-03-23
	 ****************************************************************************/
	@RequestMapping("/labconstruction/saveProjectStartedReportNew")
	public String saveProjectStartedReportNew(
			@ModelAttribute ProjectStartedReport projectStartedReport,String[] usernameArray,
			String[] sexArray,String[] ageArray,String[] positionArray,String[] jobtitleArray,String[] contentArray,
			int[] fundTypeArray,BigDecimal[] moneyArray,String[] explainArray,String[] schoolDeviceArray,
			int[] itemIdArray,String[] itemNameArray,int[] documentIdArray,String[] equipmentNameArray,
			String[] formatArray,BigDecimal[] amountArray,BigDecimal[] unitPriceArray) {
		//获取启动报告数据库对象
		//System.out.println(projectStartedReport.getId());
		ProjectStartedReport projectStartedReportVary = 
				projectStartedReportDAO.findProjectStartedReportByPrimaryKey(projectStartedReport.getId());
		//将JSP表单的数据数据赋值数据库对象，project_id不变，仍为数据库的值
		projectStartedReportVary.setProjectName(projectStartedReport.getProjectName());
		projectStartedReportVary.setCProjectSource(projectStartedReport.getCProjectSource());
		projectStartedReportVary.setSchoolAcademy(projectStartedReport.getSchoolAcademy());
		projectStartedReportVary.setFeeApp(projectStartedReport.getFeeApp());
		projectStartedReportVary.setFeeCode(projectStartedReport.getFeeCode());
		projectStartedReportVary.setStartDate(projectStartedReport.getStartDate());
		projectStartedReportVary.setLabAddress(projectStartedReport.getLabAddress());
		projectStartedReportVary.setLabArea(projectStartedReport.getLabArea());

		projectStartedReportService.save(projectStartedReportVary);
		
		//经费预算
		for (int i=0;i<fundTypeArray.length;i++) {
			ProjectStartFeeList fee=new ProjectStartFeeList();
			fee.setProjectStartedReport(projectStartedReportVary);
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
			device.setProjectStartedReport(projectStartedReportVary);
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
				device.setProjectStartedReport(projectStartedReportVary);
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
		if(itemIdArray.length>0){
			for(int i:itemIdArray){
				ProjectStartCompletionItem projectItem=new ProjectStartCompletionItem();
				//实验项目id对应的实验项目
				if(i>0){
					OperationItem item=operationItemDAO.findOperationItemByPrimaryKey(i);
					projectItem.setProjectStartedReport(projectStartedReportVary);
					projectItem.setExperimentName(item.getLpName());
					projectItem.setIsOriginal("是");
					//保存申请的实验项目
					projectStartCompletionItemDAO.store(projectItem);
				}
			}
		}

		//实验项目(input框新建的)
		if(itemNameArray.length>0){
			for(String s:itemNameArray){
				ProjectStartCompletionItem projectItem=new ProjectStartCompletionItem();
				projectItem.setProjectStartedReport(projectStartedReportVary);
				projectItem.setExperimentName(s);
				projectItem.setIsOriginal("否");
				//保存申请的实验项目
				projectStartCompletionItemDAO.store(projectItem);
				
			}
		}

		//申请附件
		for(int i:documentIdArray){
			//文档id对应的文档
			CommonDocument document=commonDocumentDAO.findCommonDocumentByPrimaryKey(i);
			document.setProjectStartedReportByProjectStartReportApproval(projectStartedReportVary);
			commonDocumentDAO.store(document);
		}
		
		return "redirect:/labconstruction/listLabConstructionApp?page=1&modelId=901";
	}
	
	/****************************************************************************
	 * 功能：删除启动报告申请的经费
	 * 作者：李德
	 * 时间：2015-04-16
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteProjectStartFeeListNew")
	public ModelAndView deleteProjectStartFeeListNew(@RequestParam Integer id,Integer AppId)  {
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
		//启动报告对应的实验室建设申请ID
		int AppIdKey = projectStartedReport.getLabConstructApp().getId();
		
		mav.setViewName("redirect:/labconstruction/newProjectStartedReport?idKey="+AppIdKey+"&modelId=931");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：删除启动报告的设备
	 * 作者：李德
	 * 时间：2015-04-16
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteProjectStartDeviceNew")
	public ModelAndView deleteProjectStartDeviceNew(@RequestParam Integer id,Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的设备
		ProjectStartDevice device=projectStartDeviceDAO.findProjectStartDeviceByPrimaryKey(id);
		projectStartDeviceDAO.remove(device);
		mav.setViewName("redirect:/labconstruction/newProjectStartedReport?idKey="+AppId+"&modelId=931");
		return mav;
	}
	/****************************************************************************
	 * 功能：删除启动报告的实验项目
	 * 作者：李德
	 * 时间：2015-04-16
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteProjectStartCompletionItemNew")
	public ModelAndView deleteProjectStartCompletionItemNew(@RequestParam Integer id,Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的实验项目
		ProjectStartCompletionItem item=projectStartCompletionItemDAO.findProjectStartCompletionItemByPrimaryKey(id);
		projectStartCompletionItemDAO.remove(item);
		//***************************
		//此处还是没有删除掉operationItem里面新增的complex为2的记录，长期会导致operationItem里面出现大量无用数据
		//*****************************
		mav.setViewName("redirect:/labconstruction/newProjectStartedReport?idKey="+AppId+"&modelId=931");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目申请列表
	 * 作者：李德
	 * 时间：2015-03-05
	 ****************************************************************************/
	@RequestMapping("/labconstruction/listLabConstructUser")
	public ModelAndView listLabConstructUser(@ModelAttribute User user,@RequestParam int currpage,int modelId,int idKey
			,@ModelAttribute("selected_labCenlistLabConstructUserter") Integer cid){
		//新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		

		// 设置分页变量并赋值为20
		int pageSize = 20;
		//选择的中心
		LabCenter center=labCenterService.findLabCenterByPrimaryKey(cid);
		//中心所属学院编号
		String number=center.getSchoolAcademy().getAcademyNumber();
		// 设置用户的总记录数并赋值
		int totalRecords = userDetailService.getUserTotalRecords(number);
		if(user.getUsername() != null)
		{
			pageSize = -1;
			totalRecords = userDetailService.getUserTotalRecordsByQuery(user.getUsername());
		}
		
		Map<String, Integer> pageModel = shareService.getPage(pageSize, currpage, totalRecords);
		//
		mav.addObject("newFlag", true);
		//将pageModel映射到pageModel
		mav.addObject("pageModel", pageModel);
		//将currpage映射到page，用来获取当前页的页码
		mav.addObject("page", currpage);
		//将totalRecords映射到totalRecords，用来获取总记录数
		mav.addObject("totalRecords", totalRecords);
		
		//根据页面的页码，查找出20条记录，将找到的用户映射给users
		mav.addObject("users", userDetailService.findUsersByAcademy(number));
		//获取用户列表
		mav.addObject("userMap", userDetailService.findUserByQuery(user, currpage-1, pageSize,number));
		//mav.addObject("exportExperimentUser",authorityService.getAuthorityResourses(1011));
		// 将该Model映射到listUser.jsp
		mav.setViewName("labconstruction/listLabConstructUser.jsp");
		return mav;

		
	}
	
	/*********************************************************************************
	 * 功能：实验室建设项目-导出
	 * 作者：李德
	 * 时间：2015-03-26
	 ************************************************************************************/
	@RequestMapping("/labconstruction/exportLabConstructionApp")
	public void exportLabConstructApp(@ModelAttribute LabConstructApp labConstructApp,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.setContentType("application/excel;charset=UTF-8");
		// 获取所有的客户信息赋值给集合alls；
		List<LabConstructApp> labConstructApps = labConstructAppService.findAllLabConstructAppByLabConstructApp(labConstructApp, 1, labConstructAppService.findAllLabConstructAppByLabConstructApp(labConstructApp).size());
		labConstructAppService.exportExcelLabConstructApp(labConstructApps,request,response);
		
	}
	
	/****************************************************************************
	 * 功能：AJAX 根据姓名查询当前中心所在学院的用户
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/labconstruction/findUserByCname")
	public @ResponseBody String findUserByCname(@RequestParam String cname,Integer page,@ModelAttribute("selected_academy") String acno) {
			User user=new User();
			user.setCname(cname);
			
			//分页开始
			int totalRecords=labConstructAppService.findUserByCname(user,acno);
			int pageSize=20;
			Map<String,Integer> pageModel =shareService.getPage(pageSize, page,totalRecords);
			//根据分页信息查询出来的记录
			List<User> userList=labConstructAppService.findUserByCname(user,acno,page,pageSize);
		    String s="";
		    for (User d : userList) {
		    	String academy="";
		    	if(d.getSchoolAcademy()!=null){
		    		academy=d.getSchoolAcademy().getAcademyName();
		    	}
				s+="<tr>"+
				"<td><input type='checkbox' name='CK_name' value='"+d.getUsername()+"'/></td>"+
		    	"<td>"+d.getCname()+"</td>"+
				"<td>"+d.getUsername()+"</td>"+
				"<td>"+academy+"</td>"+
				
				"</tr>";			
			}
		    s+="<tr><td colspan='6'>"+
		    	    "<a href='#' onclick='firstPage(1);'>"+"首页"+"</a>&nbsp;"+
		    	    "<a href='#' onclick='previousPage("+page+");'>"+"上一页"+"</a>&nbsp;"+
		    	    "<a href='#' onclick='nextPage("+page+","+pageModel.get("totalPage")+");'>"+"下一页"+"</a>&nbsp;"+
		    	    "<a href='#' onclick='lastPage("+pageModel.get("totalPage")+");'>"+"末页"+"</a>&nbsp;"+
		    	    "当前第"+page+"页&nbsp; 共"+pageModel.get("totalPage")+"页  "+totalRecords+"条记录"+
		    	    		"</td></tr>";
			return shareService.htmlEncode(s);
		
	}
	/****************************************************************************
	 * 功能：AJAX 将要新增的人员显示到页面
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/labconstruction/saveParticipants")
	public @ResponseBody String saveParticipants(@RequestParam String[] array) {
		Set<User> userList=new HashSet<User>();
		for (String i : array) {
	  		//username对应的用户
			User u=userDAO.findUserByPrimaryKey(i);
			userList.add(u);
	  	}		
			
		    String s="";
		    for (User d : userList) {
		    	//职称
		    	String title="";
		    	if(d.getCMajorDirection()!=null){
		    		title=d.getCMajorDirection().getDirectionName();
		    	}
				s+="<tr>"+
		    	"<td>"+d.getCname()+"<input type='hidden'  name='username' value='"+d.getUsername()+"'>"+"</td>"+
		    	"<td>"+d.getUserSexy()+"</td>"+
				"<td>"+"---"+"</td>"+
				"<td>"+"---"+"</td>"+
				"<td>"+title+"</td>"+
				"<td>"+"<input type='text'  name='content'>"+"</td>"+
				"<td><a href='javascript:void(0)'  onclick='javascript:this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);' >删除</a></td>"+
				"</tr>";			
			}
		   
			return shareService.htmlEncode(s);
		
	}
	/****************************************************************************
	 * 功能：AJAX 添加新的预算
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/labconstruction/addMoney")
	public @ResponseBody String addMoney() {
		Set<CFundAppItem> funds=cFundAppItemDAO.findAllCFundAppItems();
			
		    String s="<tr><td><select name='fundtype'>";
		    String o="";
		    for (CFundAppItem d : funds) {
		    	o+="<option value='"+d.getId()+"'>"+d.getName()+"</option>";
			}
		    s+=o+"</select></td><td><input type='text'  onblur='count(this);' placeholder='请输入数字' name='money' class='easyui-numberbox'  maxlength='10' validType='length[0,9]'></td>"
				+"<td><input type='text' name='explain'> </td>"
				+"<td><a href='javascript:void(0)'  onclick='javascript:this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);' >删除</a></td>"
				+"</tr>	";
		   
			return shareService.htmlEncode(s);
		
	}
	/****************************************************************************
	 * 功能：AJAX 根据设备名称查询设备并分页
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/labconstruction/findDeviceBydeviceName")
	public @ResponseBody String findDeviceBydeviceName(@RequestParam String deviceName,Integer page) {
		
			
			//分页开始
			int totalRecords=labConstructAppService.findDeviceBydeviceName(deviceName);
			int pageSize=20;
			Map<String,Integer> pageModel =shareService.getPage(page, pageSize,totalRecords);
			//根据分页信息查询出来的记录
			List<SchoolDevice> deviceList=labConstructAppService.findDeviceBydeviceName(deviceName,page,pageSize);
		    String s="";
		    for (SchoolDevice d : deviceList) {
		    	
				s+="<tr>"+
				"<td><input type='checkbox' name='CK_device' value='"+d.getDeviceNumber()+"'/></td>"+
		    	"<td>"+d.getDeviceName()+"</td>"+
				"<td>"+d.getDevicePattern()+"</td>"+
				"<td>"+d.getDevicePrice()+"</td>"+
				
				"</tr>";			
			}
		    s+="<tr><td colspan='6'>"+
		    	    "<a onclick='addDevice(1);'>"+"首页"+"</a>&nbsp;"+
		    	    "<a onclick='addDevice("+pageModel.get("previousPage")+");'>"+"上一页"+"</a>&nbsp;"+
		    	    "<a onclick='addDevice("+pageModel.get("nextPage")+");'>"+"下一页"+"</a>&nbsp;"+
		    	    "<a onclick='addDevice("+pageModel.get("lastPage")+");'>"+"末页"+"</a>&nbsp;"+
		    	    "当前第"+page+"页&nbsp; 共"+pageModel.get("totalPage")+"页  "+totalRecords+"条记录"+
		    	    		"</td></tr>";
			return shareService.htmlEncode(s);
		
	}
	/****************************************************************************
	 * 功能：AJAX 根据设备编号查询设备
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/labconstruction/saveDevice2")
	public @ResponseBody String saveDevice(@RequestParam String[] deviceArray) {
		Set<SchoolDevice> deviceSet=new HashSet<SchoolDevice>();
		for (String i : deviceArray) {
	  		//deviceNumber对应的用户
			SchoolDevice device=schoolDeviceDAO.findSchoolDeviceByPrimaryKey(i);
			deviceSet.add(device);
	  	}		
			int i=1;
		    String s="";
		    for (SchoolDevice d : deviceSet) {
		    	
				s+="<tr>"+
		    	//"<td>"+i+"<input type='hidden' name='schoolDevice' value='"+d.getDeviceNumber()+"'>"+"</td>"+
		    	"<input type='hidden' name='schoolDevice' value='"+d.getDeviceNumber()+"'>"+
		    	"<td>"+d.getDeviceName()+"</td>"+
				"<td>"+d.getDevicePattern()+"</td>"+
				"<td>"+1+"</td>"+
				"<td>"+d.getDevicePrice()+"</td>"+
				"<td><a href='javascript:void(0)'  onclick='javascript:this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);' >删除</a></td>"+
				"</tr>";			
				i+=1;
			}
		   
			return shareService.htmlEncode(s);
		
	}
	/****************************************************************************
	 * 功能：AJAX 根据id查询实验项目
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/labconstruction/findOperationItemById")
	public @ResponseBody String findOperationItemById(@RequestParam Integer id,int i) {
		OperationItem item=operationItemDAO.findOperationItemByPrimaryKey(id);
		String s="";
		//序号
		i+=1;
		//itemId为0时表示这个实验项目是新增的（与数据库里面的OperationItem的id为0的那条记录无关系），详见js里面
		s+="<tr>"+
		    	/*"<td>"+i+"<input type='hidden' name='itemId' value='"+item.getId()+"'>"+"</td>"+*/
		    	"<input type='hidden' name='itemId' value='"+item.getId()+"'>"+
		    	"<td>"+item.getLpName()+"</td>"+
				"<td>"+"是"+"</td>"+
				"<td><a href='javascript:void(0)'  onclick='javascript:this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);' >删除</a></td>"+
				"</tr>";		
		return shareService.htmlEncode(s);
		
	}
	/****************************************************************************
	 * 功能：AJAX 上传实验室建设申请附件，并返回文档字符串信息
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/labconstruction/uploadFile")
	public @ResponseBody String uploadFile(HttpServletRequest request, HttpServletResponse response, BindException errors) throws Exception {
		String s=labConstructAppService.uploadFile(request,response);
		return shareService.htmlEncode(s);
	}
	/****************************************************************************
	 * 功能：删除实验室建设申请的人员
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteLabConstructUser")
	public ModelAndView deleteLabConstructUser(@RequestParam Integer id,Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的人员
		LabConstructUser user=labConstructUserDAO.findLabConstructUserByPrimaryKey(id);
		labConstructUserDAO.remove(user);
		mav.setViewName("redirect:/labconstruction/editLabConstructionApp?idKey="+AppId+"&modelId=903");
		return mav;
	}
	/****************************************************************************
	 * 功能：删除实验室建设申请的经费
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteProjectFeeList")
	public ModelAndView deleteProjectFeeList(@RequestParam Integer id,Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的经费
		ProjectFeeList fee=projectFeeListDAO.findProjectFeeListByPrimaryKey(id);
		projectFeeListDAO.remove(fee);
		//AppId对应的实验室建设申请
		LabConstructApp app=labConstructAppDAO.findLabConstructAppByPrimaryKey(AppId);
		//更新申请的经费
		List<ProjectFeeList> feeList=labConstructAppService.findProjectFeeListByAppId(AppId);
		BigDecimal count=new BigDecimal("0.00");
		for (ProjectFeeList f : feeList) {
			count=count.add(f.getAmount());
		}
		//此处除以10000，使其单位为万元
		app.setFeeAmount(count.divide(new BigDecimal("10000.00")));
		labConstructAppDAO.store(app);
		mav.setViewName("redirect:/labconstruction/editLabConstructionApp?idKey="+AppId+"&modelId=903");
		return mav;
	}
	/****************************************************************************
	 * 功能：删除实验室建设申请的设备
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteProjectDevice")
	public ModelAndView deleteProjectDevice(@RequestParam Integer id,Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的设备
		ProjectDevice device=projectDeviceDAO.findProjectDeviceByPrimaryKey(id);
		projectDeviceDAO.remove(device);
		mav.setViewName("redirect:/labconstruction/editLabConstructionApp?idKey="+AppId+"&modelId=903");
		return mav;
	}
	/****************************************************************************
	 * 功能：删除实验室建设申请的设备
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteProjectCompletionItems")
	public ModelAndView deleteProjectCompletionItems(@RequestParam Integer id,Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的实验项目
		ProjectCompletionItem item=projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(id);
		projectCompletionItemDAO.remove(item);
		//***************************
		//此处还是没有删除掉operationItem里面新增的complex为2的记录，长期会导致operationItem里面出现大量无用数据
		//*****************************
		mav.setViewName("redirect:/labconstruction/editLabConstructionApp?idKey="+AppId+"&modelId=903");
		return mav;
	}
	/****************************************************************************
	 * 功能：删除实验室建设申请的文档
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteCommonDocument")
	public ModelAndView deleteCommonDocument(@RequestParam Integer id,Integer AppId)  {
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
		mav.setViewName("redirect:/labconstruction/editLabConstructionApp?idKey="+AppId+"&modelId=903");
		return mav;
	}
	/****************************************************************************
	 * 功能：修改实验室建设申请的参与人员
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/labconstruction/updateLabConstructUser")
	public ModelAndView updateLabConstructUser(@RequestParam Integer id,Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的参与人员
		LabConstructUser user=labConstructUserDAO.findLabConstructUserByPrimaryKey(id);
		mav.addObject("user", user);
		
		mav.setViewName("labconstruction/app/editLabConstructUser.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：保存修改的实验室建设申请的参与人员
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/labconstruction/saveUpdateLabConstructUser")
	public ModelAndView saveUpdateLabConstructUser(@ModelAttribute LabConstructUser user,@RequestParam Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请
		LabConstructApp app=labConstructAppDAO.findLabConstructAppByPrimaryKey(AppId);
		user.setLabConstructApp(app);
		labConstructUserDAO.store(user);
		mav.setViewName("redirect:/labconstruction/editLabConstructionApp?idKey="+AppId+"&modelId=903");
		return mav;
	}
	/****************************************************************************
	 * 功能：修改实验室建设申请的经费预算
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/labconstruction/updateProjectFeeList")
	public ModelAndView updateProjectFeeList(@RequestParam Integer id,Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的经费预算
		ProjectFeeList fee=projectFeeListDAO.findProjectFeeListByPrimaryKey(id);
		mav.addObject("fee", fee);
		//预算科目
		Set<CFundAppItem> fundTypes=cFundAppItemDAO.findAllCFundAppItems();
		mav.addObject("fundTypes", fundTypes);
		mav.setViewName("labconstruction/app/editProjectFeeList.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：删除实验室建设申请的文档
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/labconstruction/saveUpdatePrpjectFeeList")
	public ModelAndView saveUpdatePrpjectFeeList(@ModelAttribute ProjectFeeList fee,@RequestParam Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请
		LabConstructApp app=labConstructAppDAO.findLabConstructAppByPrimaryKey(AppId);
		fee.setLabConstructApp(app);
		projectFeeListDAO.store(fee);
		//更新实验室建设申请的预算总额
		List<ProjectFeeList> feeList=labConstructAppService.findProjectFeeListByAppId(AppId);
		BigDecimal count=new BigDecimal("0.00");
		for (ProjectFeeList f : feeList) {
			count=count.add(f.getAmount());
		}
		//此处除以10000，使其单位为万元
		app.setFeeAmount(count.divide(new BigDecimal("10000.00")));
		labConstructAppDAO.store(app);
		
		mav.setViewName("redirect:/labconstruction/editLabConstructionApp?idKey="+AppId+"&modelId=903");
		return mav;
	}
	/****************************************************************************
	 * 功能：修改实验室建设申请的设备
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/labconstruction/updateProjectDevice")
	public ModelAndView updateProjectDevice(@RequestParam Integer id,Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的设备
		ProjectDevice device=projectDeviceDAO.findProjectDeviceByPrimaryKey(id);
		mav.addObject("device", device);
		
		mav.setViewName("labconstruction/app/editProjectDevice.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：保存修改的实验室建设申请的设备
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/labconstruction/saveUpdateProjectDevice")
	public ModelAndView saveUpdateProjectDevice(@ModelAttribute ProjectDevice device,@RequestParam Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请
		LabConstructApp app=labConstructAppDAO.findLabConstructAppByPrimaryKey(AppId);
		device.setLabConstructApp(app);
		projectDeviceDAO.store(device);
		mav.setViewName("redirect:/labconstruction/editLabConstructionApp?idKey="+AppId+"&modelId=903");
		return mav;
	}
	/****************************************************************************
	 * 功能：修改实验室建设申请的项目
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/labconstruction/updateProjectCompletionItem")
	public ModelAndView updateProjectCompletionItem(@RequestParam Integer id,Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的项目
		ProjectCompletionItem item=projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(id);
		mav.addObject("item", item);
		//当前登录人所在学院的实验项目
		List<OperationItem> itemList=shareService.findOperationItemBySchoolAcademy(shareService.getUser().getSchoolAcademy().getAcademyNumber()) ;
		mav.addObject("itemList", itemList);
		mav.setViewName("labconstruction/app/editProjectCompletionItem.jsp");
		return mav;
	}

	/****************************************************************************
	 * 功能：保存修改的实验室建设申请的项目
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/labconstruction/saveUpdateProjectCompletionItem")
	public ModelAndView saveUpdateProjectCompletionItem(@ModelAttribute ProjectCompletionItem item,@RequestParam Integer AppId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请项目
		LabConstructApp app=labConstructAppDAO.findLabConstructAppByPrimaryKey(AppId);
		
		item.setLabConstructApp(app);
		projectCompletionItemDAO.store(item);
		mav.setViewName("redirect:/labconstruction/editLabConstructionApp?idKey="+AppId+"&modelId=903");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目申请导出word文档
	 * 作者：李德
	 * 时间：2015-05-28
	 ****************************************************************************/
	@RequestMapping("/downloadLabConstructApp")
	public void downloadTeacherNeedWord(@RequestParam int idKey, HttpServletResponse response)throws Exception
	{
		response.setContentType("application/doc;charset=UTF-8");
		//id对应的实验室建设申请项目
		LabConstructApp app=labConstructAppDAO.findLabConstructAppByPrimaryKey(idKey);
		//新建一个map的list集合；
        List<Map> list1 = new ArrayList<Map>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//转换日期格式
      
    	Map map = new HashMap();
    	//项目名称
    	map.put("projectName",app.getProjectName());
    	//申请人
		if(app.getUser()!=null && app.getUser().getCname()!=null) {
			map.put("userName", app.getUser().getCname());
		}else {
			map.put("userName", "");
		}
    	//系部
		if(app.getUser()!=null && app.getUser().getSchoolAcademy()!=null && app.getUser().getSchoolAcademy().getAcademyName()!=null) {
			map.put("partyName", app.getUser().getSchoolAcademy().getAcademyName());
		}else {
			map.put("partyName", "");
		}
    	//联系电话
    	if(app.getUser()!=null && app.getUser().getTelephone()!=null){
    		map.put("tel",app.getUser().getTelephone());
    	}else{
    		map.put("tel","");
    	}
    	//申请日期
		if(app.getAppDate()!=null) {
			String str = sdf.format(app.getAppDate().getTime());

			map.put("appDate", str);
		}else{
    		map.put("appDate","");
		}
    	//用途
		// 用途；
		if(app.getPurposeName()!=null){
    	    String[] arrayPurposeName=app.getPurposeName().split(",");
    	    String purposeNames="";
            for(String strAuth:arrayPurposeName)
            {		
    	      int id = Integer.valueOf(strAuth);
              CProjectPurpose cProjectPurpose=cProjectPurposeDAO.findCProjectPurposeByPrimaryKey(id);
              purposeNames=purposeNames+cProjectPurpose.getName()+" ";
             }
            map.put("purposeNames",purposeNames);
		}else{
			map.put("purposeNames","");
		}
    	//面向专业
    	if(app.getMajorName()!=null){
    	    String[] arraySchoolMajor=app.getMajorName().split(",");
    	    String schoolMajors="";
            for(String strAuth:arraySchoolMajor)
            {		
    	      SchoolMajor schoolMajor=schoolMajorDAO.findSchoolMajorByPrimaryKey(strAuth);
    	      schoolMajors=schoolMajors+schoolMajor.getMajorName()+" ";
             }
            map.put("schoolMajors",schoolMajors);
    	}else{
    		map.put("schoolMajors","");
    	}
    	//面向专业数
    	if(app.getMajorAmount()!=null){
    		map.put("majorAmount",app.getMajorAmount());
    	}else{
    		map.put("majorAmount","");
    	}

    	//课程名称
		if(app.getCourseName()!=null){
			String[] arraySchoolCourse=app.getCourseName().split(",");
			String schoolCourses="";
			for(String strAuth:arraySchoolCourse)
			{
				SchoolCourse schoolCourse=schoolCourseDAO.findSchoolCourseByPrimaryKey(strAuth);
				schoolCourses=schoolCourses+schoolCourse.getCourseName()+" ";
			}
			map.put("schoolCourses",schoolCourses);
		}else{
			map.put("schoolCourses","");
		}
    	//面向课程数
		if(app.getCourseAmount()!=null){
			map.put("courseAmount",app.getCourseAmount());
		}else{
			map.put("courseAmount","");
		}
    	//申请人性别
    	if(app.getUser().getUserSexy()!=null){
    		map.put("sex",app.getUser().getUserSexy());
    	}else{
    		map.put("sex","");
    	}
    	//申请人出生年月
    	map.put("bornDate","");
    	//申请人职务
    	map.put("title","");
    	//申请人职称
    	map.put("post","");
    	//申请人手机
    	map.put("mobileNo","");
    	//申请人电话
    	//申请人e-mail
    	if(app.getUser().getEmail()!=null){
    		map.put("eMail",app.getUser().getEmail());
    	}else{
    		map.put("eMail","");
    	}
    	
        System.out.println(map);
        //人员
        List<LabConstructUser> labConstructUser = labConstructUserService.findLabConstructUserByAppKey(app.getId());
        List<Map<String,Object>> labConstructUserList = new ArrayList<Map<String,Object>>();
        int userNumber =0;
        for(LabConstructUser u:labConstructUser){
        	Map<String,Object> userMap = new HashMap<String,Object>();
        	userNumber=userNumber+1;
        	//序号
        	userMap.put("userNumber", userNumber);
        	//姓名
        	userMap.put("pName", u.getCname());
        	//性别
        	userMap.put("pSex", u.getSex());
        	//年龄
        	userMap.put("pAge", u.getAge());
        	//职务
        	userMap.put("pTitle", u.getJobTitle());
        	//职称
        	userMap.put("pPost", u.getJobTitle());
        	//负责内容
        	userMap.put("pContent", u.getResponsibilityContent());
        	labConstructUserList.add(userMap);
        	
		}
        map.put("labConstructUserList", labConstructUserList);
        
        //主要目标
        map.put("primaryObjective",app.getPrimaryObjective());
        //特色或创新
        map.put("specialInnovation",app.getSpecialInnovation());
        //立项依据
        map.put("projectBasis",app.getProjectBasis());
        //建设的基础
        map.put("constructBasis",app.getConstructBasis());
        //计划进度
		map.put("planSchedule",app.getPlanSchedule());
        //预期的成果、考核指标及预期成果形式
        map.put("expectedResult",app.getExpectedResult());
        //项目完成后的效益
        
        //经费预算
        //申请资助经费名称(万元)
		if(app.getFeeAmount()!=null) {
			map.put("feeAmount", app.getFeeAmount());
		}else{
			map.put("feeAmount","");
		}
        //其他经费来源及金额（万元）
		if(app.getOtherFee()!=null) {
			map.put("otherFee", app.getOtherFee());
		}else{
			map.put("otherFee","");
		}
		//预算科目集合
		if(app.getProjectFeeLists()!=null){
			map.put("projectFeeList",app.getProjectFeeLists());
		}else{
			map.put("projectFeeList","");
		}
        
        //设备
        List<ProjectDevice> projectDevice = projectDeviceService.findProjectDeviceByAppKey(app.getId());
        List<Map<String,Object>> projectDeviceList = new ArrayList<Map<String,Object>>();
        int deviceNumber =0;
        for(ProjectDevice device:projectDevice){
        	Map<String,Object> deviceMap = new HashMap<String,Object>();
        	deviceNumber = deviceNumber+1;
        	//序号
        	deviceMap.put("deviceNumber", deviceNumber);
        	//名称
        	deviceMap.put("equipmentName", device.getEquipmentName());
        	//规格或型号
        	deviceMap.put("format", device.getFormat());
        	//数量
        	deviceMap.put("amount", device.getAmount());
        	//单价
        	deviceMap.put("unitPrice", device.getUnitPrice());
        	//合计
        	projectDeviceList.add(deviceMap);
        	
		}
        map.put("projectDeviceList", projectDeviceList);
        
        //项目
        List<ProjectCompletionItem> projectCompletionItem = projectCompletionItemService.findProjectCompletionItemByAppKey(app.getId());
        List<Map<String,Object>> projectCompletionItemList = new ArrayList<Map<String,Object>>();
        int itemNumber =0;
        for(ProjectCompletionItem item:projectCompletionItem){
        	Map<String,Object> itemMap = new HashMap<String,Object>();
        	itemNumber=itemNumber+1;
        	//序号
        	itemMap.put("itemNumber", itemNumber);
        	//建成后可开设实验（实训）项目名称
        	itemMap.put("experimentName", item.getExperimentName());
        	projectCompletionItemList.add(itemMap);
        	
		}
        map.put("projectCompletionItemList", projectCompletionItemList);
        map.put("pConfig.PROJECT_NAME",pConfig.PROJECT_NAME);
        System.out.println(map);
        //导出word
		WordHandler handler = new WordHandler();
		File outFile =handler.createDoc("/net/zjcclims/ftl"
				, "app.ftl", map, "实验实训室项目申请书");
		
		FileInputStream in = null;
      	 OutputStream o = null;
      	  try {
      		 byte b[] = new byte[1024]; 
      	  in = new FileInputStream(outFile);
      	  o = response.getOutputStream();  
      	  response.setContentType("application/x-doc"); 
      	  response.setHeader("Content-disposition", "attachment; filename="
      	     + URLEncoder.encode("labApp"+".doc", "UTF-8"));// 指定下载的文件名 
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