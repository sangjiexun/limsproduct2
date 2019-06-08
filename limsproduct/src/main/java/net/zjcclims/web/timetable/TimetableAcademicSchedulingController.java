package net.zjcclims.web.timetable;

import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;
import flex.messaging.io.ArrayList;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.CStaticValueService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.operation.OperationService;
import net.zjcclims.service.timetable.OuterApplicationService;
import net.zjcclims.service.timetable.SchoolCourseDetailService;
import net.zjcclims.service.timetable.SchoolCourseService;
import net.zjcclims.service.timetable.TimetableAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/****************************************************************************
 * @功能：教务排课管理模块
 * @作者：魏诚 时间：2014-07-14
 ****************************************************************************/
@Controller("TimetableCourseSchedulingController")
@SessionAttributes("selected_academy")
public class TimetableAcademicSchedulingController<JsonResult> {

	/************************************************************
	 * @初始化WebDataBinder，这个WebDataBinder用于填充被@InitBinder注释的处理 方法的command和form对象
	 * 
	 ************************************************************/
	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register
		binder.registerCustomEditor(java.util.Calendar.class,
				new org.skyway.spring.util.databinding.CustomCalendarEditor());
		binder.registerCustomEditor(byte[].class,
				new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(false));
		binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
		binder.registerCustomEditor(java.math.BigDecimal.class,
				new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
		binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
				Integer.class, true));
		binder.registerCustomEditor(java.util.Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
		binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
		binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
				Long.class, true));
		binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
				Double.class, true));
	}

	@Autowired
	private OuterApplicationService outerApplicationService;
	@Autowired
	private SchoolCourseDetailService schoolCourseDetailService;
	@Autowired
	private TimetableAppointmentDAO timetableAppointmentDAO;
	@Autowired
	private TimetableAppointmentService timetableAppointmentService;
	@Autowired
	private SchoolCourseDAO schoolCourseDAO;
	@Autowired
	private SchoolCourseService schoolCourseService;
	@Autowired
	private ShareService shareService;
	@Autowired
	private SchoolTermDAO schoolTermDAO;
	@Autowired
	private CStaticValueService cStaticValueService;
	@Autowired
	private OperationService operationService;
	@Autowired
	private SoftwareDAO softwareDAO;
	@Autowired
	private TimetableSelfCourseDAO timetableSelfCourseDAO;
	@Autowired
	private SoftwareRoomRelatedDAO 	softwareRoomRelatedDAO;
	@Autowired
	private SoftwareItemRelatedDAO  softwareItemRelatedDAO;
	@Autowired
	private OperationItemDAO  operationItemDAO;
	@Autowired
	private SchoolTermActiveDAO schoolTermActiveDAO;
	@Autowired
	private UserDAO userDAO;
	/************************************************************
	 * @throws IOException 
	 * @throws ParseException 
	 * @功能：教务排课-进行直接排课
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/doDirectTimetable")
	public ModelAndView doDirectTimetable(HttpServletRequest request,HttpServletResponse response, @RequestParam String courseCode) throws IOException, ParseException {
		ModelAndView mav = new ModelAndView();
		//进行实验设备判冲
		
		/*//判断是否实验设备冲突
		if(devices.length>0&&!timetableCommonService.getLabroomDeviceValid(devices, courseCode)){
			mav.setViewName("../../timetable/listTimetableTerm?currpage=" + request.getParameter("currpage") + "&id=-1");
			return mav;
		}*/
		String[] sLabRoom_id = request.getParameterValues("labRoom_id");
		String[] sLabRoomDevice_id = request.getParameterValues("labRoomDevice_id");
		String[] sTutorRelated = request.getParameterValues("tutorRelated");
		String[] sTeacherRelated = request.getParameterValues("teacherRelated");
		// 记录选课组编号
		mav.addObject("courseCode", courseCode);
		schoolCourseDetailService.doDirectTimetable(courseCode, sLabRoom_id, sTutorRelated, sTeacherRelated,sLabRoomDevice_id);
		mav.setViewName("redirect:/timetable/listTimetableTerm?currpage=" + request.getParameter("currpage") + "&id=-1");
		return mav;
	}

	/************************************************************
	 * @教务排课-打开调整排课页面
	 * @参数：courseCode选课组编号，currpage调用的当前页数以便返回定位，flag提示是否处理调课完成0不提示，1提示
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/openAdjustTimetableByScience")
	public ModelAndView openAdjustTimetableByScience(@RequestParam String courseCode, int currpage, int flag, int tableAppId,
													 Integer isPlan, @ModelAttribute("selected_academy") String acno) {
		//tableAppId==0表示新建 tableAppId!=0表示编辑，编辑的时候，可选周次的判断方法就不能与自身判冲
//		if(cid==null){
//			cid=-1;
//		}
		ModelAndView mav = new ModelAndView();
		TimetableAppointment timetableAppointment = new TimetableAppointment();
		mav.addObject("timetableAppointment", timetableAppointment);
		// 获取去重的实验分室室列表
		mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
		// flag提示是否处理调课完成0不提示，1提示
		mav.addObject("flag", flag);
		// 获取可选的教师列表列表
		mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap(acno));
		// 获取登陆用户信息
		mav.addObject("user", shareService.getUserDetail());
		// 获取可选的实验项目列表列表
		mav.addObject(
				"timetableItemMap",
				outerApplicationService.getTimetableItemMap(schoolCourseDAO.findSchoolCourseByCourseCode(courseCode)
						.iterator().next().getSchoolCourseInfo().getCourseNumber()));
		// 获取可选的教师列表列表
		mav.addObject("courseCode", courseCode);
		// 根据选课组编号获取教务排课信息
		List<SchoolCourseDetail> schoolCourseDetailLists = schoolCourseDetailService
				.getSchoolCourseDetailByCourseCode(courseCode);
		mav.addObject("schoolCourseDetailMap", schoolCourseDetailLists);
		// 根据选课组获取课程排课列表
		mav.addObject("timetableAppointmentMap",
				timetableAppointmentDAO.findTimetableAppointmentByCourseCode(courseCode));
		// 获取可选的周列表列表
		mav.addObject("currpage", currpage);
		mav.addObject("validWeekMap", schoolCourseDetailService.getValidWeekMap(schoolCourseDetailLists.get(0)
				.getWeekday(), null, courseCode));
		mav.addObject("tableAppId", tableAppId);
		mav.setViewName("timetable/schedulingcourse/listAdjustTimetableTermByScience.jsp");
		return mav;
	}
	/************************************************************
	 * @教务排课-打开调整排课页面
	 * @参数：courseCode选课组编号，currpage调用的当前页数以便返回定位，flag提示是否处理调课完成0不提示，1提示
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/openAdjustTimetable")
	public ModelAndView openAdjustTimetable(@RequestParam String courseCode, int currpage, int flag, Integer tableAppId,
			@ModelAttribute("selected_academy") String acno) {
		//tableAppId==0表示新建 tableAppId!=0表示编辑，编辑的时候，可选周次的判断方法就不能与自身判冲
//		if(cid==null){
//			cid=-1;
//		}
		ModelAndView mav = new ModelAndView();
		TimetableAppointment timetableAppointment = new TimetableAppointment();
		mav.addObject("timetableAppointment", timetableAppointment);
		// 获取去重的实验分室室列表
		mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
		// flag提示是否处理调课完成0不提示，1提示
		mav.addObject("flag", flag);
		// 获取可选的教师列表列表
		mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap());
		// 获取登陆用户信息
		mav.addObject("user", shareService.getUserDetail());
		// 获取可选的实验项目列表列表
		mav.addObject(
				"timetableItemMap",
				outerApplicationService.getTimetableItemMap(schoolCourseDAO.findSchoolCourseByCourseCode(courseCode)
						.iterator().next().getSchoolCourseInfo().getCourseNumber()));
		// 获取可选的教师列表列表
		mav.addObject("courseCode", courseCode);
		// 根据选课组编号获取教务排课信息
		List<SchoolCourseDetail> schoolCourseDetailLists = schoolCourseDetailService
				.getSchoolCourseDetailByCourseCode(courseCode);
		mav.addObject("schoolCourseDetailMap", schoolCourseDetailLists);
		// 获取可选的周列表列表
		mav.addObject("currpage", currpage);
		if (schoolCourseDetailLists!=null && schoolCourseDetailLists.size()>0) {
			mav.addObject("validWeekMap", schoolCourseDetailService.getValidWeekMap(schoolCourseDetailLists.get(0)
					.getWeekday(), null, courseCode));
		}
		//通过tabkeAppId获得对应的实验项目
		if(tableAppId!=null && tableAppId!=0){
			TimetableAppointment findTimetableAppointmentById = timetableAppointmentDAO.findTimetableAppointmentById(tableAppId);
			// 获得有无软件排课
			if(findTimetableAppointmentById.getTimetableSoftwareRelateds() != null
					&& findTimetableAppointmentById.getTimetableSoftwareRelateds().size() != 0){
				mav.addObject("softwareEnable", 1);
			}else{
				mav.addObject("softwareEnable", 0);
			}
			// 通过tableAppId获得排课信息
			mav.addObject("timetableAppointmentMap",
					findTimetableAppointmentById);
			findTimetableAppointmentById.setEnabled(false);
			findTimetableAppointmentById.setOriginalTapid(tableAppId);
			timetableAppointmentDAO.store(findTimetableAppointmentById);
			timetableAppointmentDAO.flush();
			Set<TimetableItemRelated> timetableItemRelateds = findTimetableAppointmentById.getTimetableItemRelateds();
			if(timetableItemRelateds.size()>0) {
				List<OperationItem> operationItem = new ArrayList();
				for (TimetableItemRelated timetableItemrelate : timetableItemRelateds) {
					if(!EmptyUtil.isObjectEmpty(timetableItemrelate.getOperationItem())){
					operationItem.add(operationItemDAO.findOperationItemById(timetableItemrelate.getOperationItem().getId()));
				}
				}
				mav.addObject("operationItem", operationItem);
				//获得实验项目绑定的软件
				Set<SoftwareItemRelated> softwareItemRelateds =new HashSet<SoftwareItemRelated>();
				if(operationItem!=null&&operationItem.size()>0) {
					softwareItemRelateds=operationItem.get(0).getSoftwareItemRelateds();
				}
				List<Software> software = new ArrayList();
				for (SoftwareItemRelated softwareitemrelate : softwareItemRelateds) {
					software.add(softwareitemrelate.getSoftware());
				}
				mav.addObject("software", software);
			}
		}
	 // 获取所有软件列表
	    Map<Integer, String> map3 = outerApplicationService.getTimetableSoftwarerMap();
	 	mav.addObject("softwareMap", map3);
	 //获得学期周次
	   List<Integer> inte= new ArrayList();
       for(SchoolCourseDetail scd:schoolCourseDetailLists){
    	   SchoolTerm schoolTerm = scd.getSchoolTerm();
    	   for(SchoolWeek slw:schoolTerm.getSchoolWeeks()){
    		   if(!inte.contains(slw.getWeek())){
    			   inte.add(slw.getWeek());
    			   
    		   }
    	   }
       }
       //周次排序
        Collections.sort(inte);
   	    mav.addObject("schoolweek",inte);
   		List<TimetableSelfCourse> listt =new ArrayList(schoolCourseDetailLists.get(0).getSchoolCourse().getSchoolCourseInfo().getTimetableSelfCourses());
		if(listt!=null&&!listt.isEmpty()){
			mav.addObject("timetableSelfCourse",listt.get(0));
		}
		else{
		     TimetableSelfCourse timetableSelfCourse = new TimetableSelfCourse();
			 timetableSelfCourse.setId(-2);
			mav.addObject("timetableSelfCourse",timetableSelfCourse);
		}
		mav.addObject("tableAppId", tableAppId);
		//获取当前时间，判断你是否为排课约束时间
		Calendar time = Calendar.getInstance();
		Calendar time1 = Calendar.getInstance();
		Calendar time2 =Calendar.getInstance();
		if(time.before(time2)&&time.after(time1)){
	    mav.addObject("confinementTime",1);
		}else{
			 mav.addObject("confinementTime",0);
		}
		mav.setViewName("timetable/schedulingcourse/listAdjustTimetableTerm.jsp");
		return mav;
	}
	/************************************************************
	 * @教务排课-打开调出页面
	 * @参数：courseCode选课组编号，currpage调用的当前页数以便返回定位，flag提示是否处理调课完成0不提示，1提示
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/adjustment")
	public ModelAndView adjustment(@RequestParam String courseCode, int currpage, int flag, Integer tableAppId,
			@ModelAttribute("selected_academy") String acno) {
		//tableAppId==0表示新建 tableAppId!=0表示编辑，编辑的时候，可选周次的判断方法就不能与自身判冲
//		if(cid==null){
//			cid=-1;
//		}
		ModelAndView mav = new ModelAndView();
		TimetableAppointment timetableAppointment = new TimetableAppointment();
		mav.addObject("timetableAppointment", timetableAppointment);
		// 获取去重的实验分室室列表
		mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
		// 获取可选的教师列表列表
		mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap());
		// 获取登陆用户信息
		mav.addObject("user", shareService.getUserDetail());
		// 获取可选的教师列表列表
		mav.addObject("courseCode", courseCode);
		// 根据选课组编号获取教务排课信息
		List<SchoolCourseDetail> schoolCourseDetailLists = schoolCourseDetailService
				.getSchoolCourseDetailByCourseCode(courseCode);
		mav.addObject("schoolCourseDetailMap", schoolCourseDetailLists);
		// 获取可选的周列表列表
		mav.addObject("currpage", currpage);
		//通过tabkeAppId获得对应的实验项目
		TimetableAppointment findTimetableAppointmentById = timetableAppointmentDAO.findTimetableAppointmentById(tableAppId);
		// 通过tableAppId获得排课信息
				mav.addObject("timetableAppointmentMap",
						findTimetableAppointmentById);
		// 获得已选周次
		Integer startClass = findTimetableAppointmentById.getStartClass();		
		Integer endClass = findTimetableAppointmentById.getEndClass();
		Integer totalClass= endClass-startClass+1;
		int[] selectClass = new int[totalClass];
		for(int i=0;i<totalClass;i++){
			selectClass[i]=startClass;
			startClass++;
		}
		mav.addObject("selectClass", selectClass);
		Set<TimetableItemRelated> timetableItemRelateds = findTimetableAppointmentById.getTimetableItemRelateds();
		List<OperationItem> operationItem = new ArrayList();
		for(TimetableItemRelated timetableItemrelate:timetableItemRelateds){
			operationItem.add(operationItemDAO.findOperationItemById(timetableItemrelate.getOperationItem().getId()));  
		}
		mav.addObject("operationItem",operationItem);
	 //获得学期周次
	   List<Integer> inte= new ArrayList();
       for(SchoolCourseDetail scd:schoolCourseDetailLists){
    	   SchoolTerm schoolTerm = scd.getSchoolTerm();
    	   for(SchoolWeek slw:schoolTerm.getSchoolWeeks()){
    		   if(!inte.contains(slw.getWeek())){
    			   inte.add(slw.getWeek());
    			   
    		   }
    	   }
       }
       //周次排序
        Collections.sort(inte);
   	    mav.addObject("schoolweek",inte);
		mav.addObject("tableAppId", tableAppId);
		mav.setViewName("timetable/schedulingcourse/adjustment.jsp");
		return mav;
	}
	/************************************************************
	 * @教务排课-进行排课查看:查看排课信息期数据
	 * @页面跳转：listTimetableTerm-getTimetableWeeksMap
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/openSearchTimetable")
	public ModelAndView openSearchTimetable(@RequestParam String courseCode,
			@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		TimetableAppointment timetableAppointment = new TimetableAppointment();
		mav.addObject("timetableAppointment", timetableAppointment);
		// 获取去重的实验分室室列表
		mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
		// 获取可选的教师列表列表
		mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap(acno));
		// 根据选课组获取课程排课列表
		mav.addObject("timetableAppointmentMap",
				timetableAppointmentDAO.findTimetableAppointmentByCourseCode(courseCode));
		mav.setViewName("timetable/schedulingcourse/listCourseTimetable.jsp");
		return mav;
	}

	/************************************************************
	 * @throws ParseException
	 * @教务排课-进行调整排课保存
	 * @页面跳转：listTimetableTerm-openAdjustTimetable-saveAdjustTimetable
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@ResponseBody
	@RequestMapping("/timetable/ajaxSaveAdjustTimetable")
	public String ajaxSaveAdjustTimetable(HttpServletRequest request) throws ParseException {
		// 保存调整排课数据
		timetableAppointmentService.saveAdjustTimetable(request);
		// mav.setViewName("../../timetable/listTimetableTerm?currpage="+request.getParameter("currpage")
		// + "&id=-1");
		return "ok";
	}

	/************************************************************
	 * @throws ParseException 
	 * @教务排课-进行调整排课保存
	 * @页面跳转：listTimetableTerm-openAdjustTimetable-saveAdjustTimetable
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/saveAdjustTimetable")
	public ModelAndView saveAdjustTimetable(HttpServletRequest request) throws ParseException {
		ModelAndView mav = new ModelAndView();
		// 保存调整排课数据
		timetableAppointmentService.saveAdjustTimetable(request);
		// mav.setViewName("../../timetable/listTimetableTerm?currpage="+request.getParameter("currpage")
		// + "&id=-1");
		
		mav.setViewName("redirect:/timetable/openAdjustTimetable?currpage=1&flag=0&courseCode="+request
				.getParameter("courseCode"));
		return mav;
	}
	/************************************************************
	 * @throws ParseException 
	 * @教务排课-调出课程保存
	 * @作者：戴昊宇
	 * @日期：2017-11-21
	 ************************************************************/
	@RequestMapping("/timetable/saveAdjustMent")
	public String saveAdjustMent(HttpServletRequest request) throws ParseException {
		// 保存调整排课数据
		timetableAppointmentService.saveAdjustMent(request);
		
		return("redirect:/timetable/timetableAlterIframe?currpage=1&id=-1&status=1");
		
	}

	/************************************************************
	 * @教务排课-确认调整排课是否完成--从排课页面进入
	 * @页面跳转：listTimetableTerm-openAdjustTimetable-doJudgeTimetableOk
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@ResponseBody
	@RequestMapping("/timetable/ajaxDoJudgeTimetableOk")
	public String ajaxDoJudgeTimetableOk(HttpServletRequest request, @RequestParam String courseCode) {

		// 发布排课
		List<TimetableAppointment> timetableAppointments = timetableAppointmentDAO
				.executeQuery("select c from TimetableAppointment c where c.courseCode like '" + courseCode + "'");
		for (TimetableAppointment timetableAppointment : timetableAppointments) {
			timetableAppointment.setStatus(2);
			timetableAppointmentDAO.store(timetableAppointment);
		}
		return "ok";
	}

	/************************************************************
	 * @教务排课-确认调整排课是否完成--从排课页面进入
	 * @页面跳转：listTimetableTerm-openAdjustTimetable-doJudgeTimetableOk
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/doJudgeTimetableOk")
	public ModelAndView doJudgeTimetableOk(HttpServletRequest request, @RequestParam String courseCode) {
		ModelAndView mav = new ModelAndView();
		// 发布排课
		List<TimetableAppointment> timetableAppointments = timetableAppointmentDAO
				.executeQuery("select c from TimetableAppointment c where c.courseCode like '" + courseCode + "'");
		for (TimetableAppointment timetableAppointment : timetableAppointments) {
			timetableAppointment.setStatus(2);
			timetableAppointmentDAO.store(timetableAppointment);
		}
		mav.setViewName("redirect:/timetable/listTimetableTrain?currpage=" + request.getParameter("currpage") + "&id=-1&flag=0");
		return mav;
	}
	
	/************************************************************
	 * @教务排课-确认调整排课是否完成--从排课管理页面进入
	 * @作者：贺子龙
	 * @日期：2016-06-05
	 ************************************************************/
	@RequestMapping("/timetable/doJudgeTimetableOkFromAdmin")
	public ModelAndView doJudgeTimetableOkFromAdmin(@ModelAttribute("isPosted") String isposted,HttpServletRequest request, @RequestParam String courseCode,Integer currpage){
		ModelAndView mav = new ModelAndView();
		// 发布排课
		List<TimetableAppointment> timetableAppointments = timetableAppointmentDAO
				.executeQuery("select c from TimetableAppointment c where c.courseCode like '" + courseCode + "'");
		for (TimetableAppointment timetableAppointment : timetableAppointments) {
			timetableAppointment.setStatus(2);
			timetableAppointmentDAO.store(timetableAppointment);
		}
		mav.setViewName("redirect:/timetable/timetablevertify?status=-1&currpage="+currpage);
		return mav;
	}

	/************************************************************
	 * @显示教务排课的主显示页面
	 * @作者：魏诚
	 * @日期：2014-07-14
	 ************************************************************/
	@RequestMapping("/timetable/listTimetableTerm")
	public ModelAndView listTimetableTerm(HttpServletRequest request,
			@ModelAttribute SchoolCourseDetail schoolCourseDetail, @RequestParam int currpage,
			@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
        // 获取学期列表
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", schoolTerms);
		// 根据课程及id获取选课组列表
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		mav.addObject("term", term);
		SchoolTerm schoolTerm = schoolTermDAO.findSchoolTermById(term);
		// 设置分页变量并赋值为20；
		int pageSize = CommonConstantInterface.INT_PAGESIZE;
		// 判断是否标记位为空，如果为空，则清空schoolCourseDetail
		if (request.getParameter("id") != null && request.getParameter("id").equals("-1")) {
			schoolCourseDetail.setCourseDetailNo(null);
		}
		// 根据课程及id获取课程排课列表的计数
		int totalRecords = schoolCourseDetailService.getCountCourseDetailsByQuery(term, schoolCourseDetail, acno, request);
		mav.addObject("totalRecords", totalRecords);
		// 获取登陆用户信息
		mav.addObject("user", shareService.getUserDetail());
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		// 将currpage映射到page，用来获取当前页的页码
		mav.addObject("page", currpage);
		// 将TimetableAppointment映射到timetableAppointment；（教务调整排课入口属性）
		mav.addObject("timetableAppointment", new TimetableAppointment());
		// 获取当前周次
		int week = shareService.findNewWeek();
		// 映射week
		mav.addObject("week", String.valueOf(week));
		mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
		
		// 获取实验室排课的通用配置对象；
		CStaticValue cStaticValue = cStaticValueService.getCStaticValueByTimetableLabDevice(acno);
		mav.addObject("cStaticValue", cStaticValue);
		
		// 获取可选的教师列表列表
		mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap(acno));
		mav.addObject("schoolTerm", schoolTerm);
		// 映射schoolTerm的id
		mav.addObject("termId", schoolTerm.getId());
		// 根据课程及id获取课程排课列表
		List<SchoolCourseDetail> schoolCourseDetails = schoolCourseDetailService.getCourseDetailsByQuery(
				schoolTerm.getId(), schoolCourseDetail,currpage - 1, pageSize, acno, request);
		// 根据课程及id获取课程排课列表
		mav.addObject("schedulingCourseMap", schoolCourseDetails);
		mav.addObject("schedulingCourseAllMap", schoolCourseService.getCourseCodeListAll(schoolTerm.getId(),acno));
		// 获取该学期的所有周次
		mav.addObject("weeks", schoolCourseDetailService.getWeeksByNow(schoolTerm.getId()));
		mav.setViewName("timetable/schedulingcourse/listTimetableTerm.jsp");
		return mav;
	}
	
	/************************************************************
	 * @显示实训排课的主显示页面
	 * @作者：戴昊宇
	 * @日期：2017-07-24
	 ************************************************************/
	@RequestMapping("/timetable/listTimetableTrain")
	public ModelAndView listTimetableTrain(HttpServletRequest request,
			@ModelAttribute SchoolCourseDetail schoolCourseDetail, @RequestParam int currpage,
			@ModelAttribute("selected_academy") String acno,int flag) {
		ModelAndView mav = new ModelAndView();
        // 获取学期列表
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", schoolTerms);
		// 根据课程及id获取选课组列表
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		mav.addObject("term", term);
		SchoolTerm schoolTerm = schoolTermDAO.findSchoolTermById(term);
		// 设置分页变量并赋值为20；
		int pageSize = CommonConstantInterface.INT_PAGESIZE;
		// 判断是否标记位为空，如果为空，则清空schoolCourseDetail
		if (request.getParameter("id") != null && request.getParameter("id").equals("-1")) {
			schoolCourseDetail.setCourseDetailNo(null);
		}
		String teacher="";
		if(request.getParameter("teacher")!=null&&request.getParameter("teacher")!="")
		{
			teacher = userDAO.findUserByUsername(request.getParameter("teacher")).getUsername();
		}else{
			// 获取当前用户
			User currUser = shareService.getUser();
			teacher = currUser.getUsername();
		}
		mav.addObject("teacher", teacher);
		// 根据课程及id获取课程排课列表的计数
		int totalRecords = schoolCourseDetailService.getCountCourseDetailsByQuery(term, schoolCourseDetail, acno, teacher);
		mav.addObject("totalRecords", totalRecords);
		// 获取登陆用户信息
		mav.addObject("user", shareService.getUserDetail());
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		// 将currpage映射到page，用来获取当前页的页码
		mav.addObject("page", currpage);
		// 将TimetableAppointment映射到timetableAppointment；（教务调整排课入口属性）
		mav.addObject("timetableAppointment", new TimetableAppointment());
		// 获取当前周次
		int week = shareService.findNewWeek();
		// 映射week
		mav.addObject("week", String.valueOf(week));
		mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
		
		// 获取实验室排课的通用配置对象；
		CStaticValue cStaticValue = cStaticValueService.getCStaticValueByTimetableLabDevice(acno);
		mav.addObject("cStaticValue", cStaticValue);
		// 获得所有教师
		List<User> teacheres = shareService.findAllTeacheres();
		mav.addObject("teachers",teacheres);
		// 获取可选的教师列表列表
		mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap());
		//查出所有课程的名称
		List<SchoolCourseDetail> allCourseDetails = schoolCourseDetailService.getAllCourseDetails(schoolTerm.getId());
		Set<String> courseDetailName=new HashSet<String>();
		for(SchoolCourseDetail schoolcoursedetail:allCourseDetails){
			courseDetailName.add(schoolcoursedetail.getCourseName());
		}
		mav.addObject("courseDetailName", courseDetailName);
		String selectedDetailName="";
		if(request.getParameter("detailName")!=null&&request.getParameter("detailName")!=""){
			selectedDetailName=request.getParameter("detailName");
		}
		mav.addObject("selectedDetailName", selectedDetailName);
		mav.addObject("schoolTerm", schoolTerm);
		// 映射schoolTerm的id
		mav.addObject("termId", schoolTerm.getId());
		// 根据课程及id获取课程排课列表
		List<SchoolCourseDetail> schoolCourseDetails = schoolCourseDetailService.getCourseDetailsByQuery(
				schoolTerm.getId(), schoolCourseDetail, teacher,selectedDetailName, currpage - 1, pageSize, -1);
		// 根据课程及id获取课程排课列表
		mav.addObject("schedulingCourseMap", schoolCourseDetails);
		// 获取该学期的所有周次
		mav.addObject("weeks", schoolCourseDetailService.getWeeksByNow(schoolTerm.getId()));
		//获取当前时间，判断你是否为排课约束时间
		Calendar time = Calendar.getInstance();
		List<SchoolTermActive> activeByTerm = timetableAppointmentService.findSchoolTermActiveByTerm(term);
		int endflag = 0;
		if(activeByTerm!=null&&activeByTerm.size()>0){
		if(activeByTerm.get(0).getDeadLine().before(time)){
		   endflag=1;
			}
		}
		 mav.addObject("endflag",endflag);
		 mav.addObject("flag",flag);
		 if(flag==1){
			 mav.setViewName("timetable/schedulingcourse/listSpecialWeekTrain.jsp");
		 }
		 else{
			 mav.setViewName("timetable/schedulingcourse/listTimetableTrain.jsp"); 
		 }
		return mav;
	}
	
	/************************************************************
	 * @description:排课申请时间设置
	 * @author：黄烁
	 * @date：2017-11-03
	 ************************************************************/
	@RequestMapping("/timetable/listTimetableSetting")
	public ModelAndView listTimetableSetting(@RequestParam int currpage,@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		Set<SchoolTermActive> schoolTermActive = schoolTermActiveDAO.findAllSchoolTermActives();
		mav.addObject("schoolTermActive", schoolTermActive);
		// 设置分页变量并赋值为20；
		int pageSize = CommonConstantInterface.INT_PAGESIZE;
		int totalRecords = schoolTermActiveDAO.findAllSchoolTermActives().size();
		mav.addObject("totalRecords", totalRecords);
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		// 将currpage映射到page，用来获取当前页的页码
		mav.addObject("page", currpage);
		mav.setViewName("timetable/schedulingcourse/listTimetableSetting.jsp");
		return mav;
	}
	
	/************************************************************
	 * @description:新建排课申请时间页面
	 * @author：黄烁
	 * @date：2017-11-03
	 ************************************************************/
	@RequestMapping("/timetable/newTimetableSetting")
	public ModelAndView newTimetableSetting(@ModelAttribute SchoolTermActive schoolTermActive){
		ModelAndView mav = new ModelAndView();
		 // 获取学期列表
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", schoolTerms);
		mav.addObject("schoolTermActive", schoolTermActive);
		mav.setViewName("timetable/schedulingcourse/newTimetableSetting.jsp");
		return mav;
	}
	
	/************************************************************
	 * @description:保存排课申请时间页面
	 * @author：黄烁
	 * @date：2017-11-03
	 ************************************************************/
	@RequestMapping("/timetable/saveTimetableSetting")
	public String saveTimetableSetting(@ModelAttribute SchoolTermActive schoolTermActive){
		//int sid = schoolTermActive.getSchoolTerm().getId();
		//SchoolTerm schoolTerm = schoolTermDAO.findSchoolTermById(sid);
		//schoolTermActive.setSchoolTerm(schoolTerm);
		// 获取登陆用户信息
		User user = shareService.getUserDetail();
		schoolTermActive.setUser(user);
		schoolTermActiveDAO.store(schoolTermActive);
		return "redirect:/timetable/listTimetableSetting?currpage=1";
	}
	
	/************************************************************
	 * @description:删除排课申请时间记录
	 * @author：黄烁
	 * @date：2017-11-05
	 ************************************************************/
	@RequestMapping("/timetable/deleteTimetableSetting")
	public String deleteTimetableSetting(@RequestParam int id){
		SchoolTermActive sta = schoolTermActiveDAO.findSchoolTermActiveById(id);
		schoolTermActiveDAO.remove(sta);
		return "redirect:/timetable/listTimetableSetting?currpage=1&id=-1";
	}
	
	/************************************************************
	 * @description:导出排课时间设置页面
	 * @author：黄烁
	 * @date：2017-11-03
	 ************************************************************/
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	@RequestMapping("/timetable/exportTimetableTime")
	public void exportTimetableTime(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<Map> list = new ArrayList();
		Set<SchoolTermActive> schoolTermActives = schoolTermActiveDAO.findAllSchoolTermActives();
		int i = 1;
		for(SchoolTermActive s : schoolTermActives){
			Map map = new HashMap();
			map.put("i", i);
			i++;
			map.put("term", s.getSchoolTerm().getTermName());
			DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			if(s.getActiveStarttime() != null){
				Date sdate = s.getActiveStarttime().getTime();
				String startTime = fmt.format(sdate);
				map.put("startTime", startTime);
			}else{
				map.put("startTime", "");
			}
			if(s.getActiveStarttime() != null){
				Date fdate = s.getActiveFinishtime().getTime();
				String finishTime = fmt.format(fdate);
				map.put("finishTime", finishTime);
			}else{
				map.put("finishTime", "");
			}
			map.put("creater", s.getUser().getCname());
			list.add(map);
		}
		String title = "排课申请时间设置表";
		String[] headers = new String[]{"序号", "学期", "开始时间", "结束时间", "操作人"};
		String[] fields = new String[]{"i", "term", "startTime", "finishTime", "creater"};
		TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(headers), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportExcel(title, shareService.getUserDetail().getCname(), "", td);
	}
	
	/************************************************************
	 * @description:编辑排课申请时间页面
	 * @author：黄烁
	 * @date：2017-11-06
	 ************************************************************/
	@RequestMapping("/timetable/editTimetableSetting")
	public ModelAndView editTimetableSetting(@ModelAttribute SchoolTermActive schoolTermActive, @RequestParam int id){
		ModelAndView mav = new ModelAndView();
		 // 获取学期列表
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", schoolTerms);
		SchoolTermActive sta = schoolTermActiveDAO.findSchoolTermActiveById(id);
		mav.addObject("currSchoolTerm", sta.getSchoolTerm().getTermName());
		Date sd = sta.getActiveStarttime().getTime();
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		String str = fmt.format(sd);
		mav.addObject("currSd", str);
		Date fd = sta.getActiveFinishtime().getTime();
		String ftr = fmt.format(fd);
		mav.addObject("currFd", ftr);
		mav.addObject("schoolTermActive", schoolTermActive);
		mav.setViewName("timetable/schedulingcourse/editTimetableSetting.jsp");
		return mav;
	}
	
	/************************************************************
	 * @教务排课-打开课程进度页面
	 * @参数：courseCode选课组编号，currpage调用的当前页数以便返回定位，flag提示是否处理调课完成0不提示，1提示
	 * @作者：戴昊宇
	 * @日期：2017-07-24
	 ************************************************************/
	@RequestMapping("/timetable/openTimetableAp")
	public ModelAndView openTimetableAp(@RequestParam String courseCode, int currpage, int flag, Integer tableAppId,
			@ModelAttribute("selected_academy") String acno) {
		//tableAppId==0表示新建 tableAppId!=0表示编辑，编辑的时候，可选周次的判断方法就不能与自身判冲
		ModelAndView mav = new ModelAndView();
		TimetableAppointment timetableAppointment = new TimetableAppointment();
		mav.addObject("timetableAppointment", timetableAppointment);
		
		// 获取去重的实验分室室列表
		mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
		// flag提示是否处理调课完成0不提示，1提示
		mav.addObject("flag", flag);
		// 获取登陆用户信息
		mav.addObject("user", shareService.getUserDetail());
		// 获取可选的实验项目列表列表
		mav.addObject(
				"timetableItemMap",
				outerApplicationService.getTimetableItemMap(schoolCourseDAO.findSchoolCourseByCourseCode(courseCode)
						.iterator().next().getSchoolCourseInfo().getCourseNumber()));
		// 获取可选的教师列表列表
		mav.addObject("courseCode", courseCode);
		// 根据选课组编号获取教务排课信息
		List<SchoolCourseDetail> schoolCourseDetailLists = schoolCourseDetailService
				.getSchoolCourseDetailByCourseCode(courseCode);
		mav.addObject("schoolCourseDetailMap", schoolCourseDetailLists);
		// 用于排课选择项目时选择本课程名
		SchoolCourseDetail schoolCourseDetail = schoolCourseDetailLists.get(0);
		mav.addObject("schoolCourseDetail", schoolCourseDetail);
		// 获取可选的教师列表列表
		 Map<String,String> timetableTearcherMap = new HashMap<String, String>();
	        for (SchoolCourseDetail sc : schoolCourseDetailLists) {
	            timetableTearcherMap.put(sc.getUser().getUsername(), sc.getUser().getCname() /*+ ";" + user.getUsername()*/);
	        }
	        Map<String, String> timetableMap = outerApplicationService.getTimetableTearcherMap();
	        for(SchoolCourseDetail sc:schoolCourseDetailLists){
	            Iterator<Map.Entry<String, String>> it = timetableMap.entrySet().iterator();
	            while(it.hasNext()){
	            Map.Entry<String, String> entry = it.next();
	            if(entry.getKey().equals(sc.getUser().getUsername())){
	                it.remove();
	                   }
	               }
	            
	        }
	        mav.addObject("timetableMap", timetableMap);
	        mav.addObject("timetableTearcherMap", timetableTearcherMap);
	    	// 根据课程及id获取课程排课列表
			mav.addObject("courseCodes",schoolCourseDAO.findSchoolCourseByCourseCode(courseCode).iterator().next());
		Set<SchoolWeek> schoolweek = new HashSet<SchoolWeek>();
		List<Integer> inte= new ArrayList();
       for(SchoolCourseDetail scd:schoolCourseDetailLists){
    	   SchoolTerm schoolTerm = scd.getSchoolTerm();
    	   for(SchoolWeek slw:schoolTerm.getSchoolWeeks()){
    		   if(!inte.contains(slw.getWeek())){
    			   inte.add(slw.getWeek());
    			   
    		   }
    	   }
       }
       //周次排序
        Collections.sort(inte);
   	    mav.addObject("schoolweek",inte);
/*		TimetableSelfCourse timetableSelfCourse=timetableSelfCourseDAO.findTimetableSelfCourseById();*/
		//获得自主排课id
   	    List<TimetableSelfCourse> listt =new ArrayList(schoolCourseDetailLists.get(0).getSchoolCourse().getSchoolCourseInfo().getTimetableSelfCourses());
		if(listt!=null&&!listt.isEmpty()){
			mav.addObject("timetableSelfCourse",listt.get(0));
		}
		else{
			 TimetableSelfCourse timetableSelfCourse = new TimetableSelfCourse();
			 timetableSelfCourse.setId(-2);
			mav.addObject("timetableSelfCourse",timetableSelfCourse);
		}
	   Map<Integer, String> map3 = outerApplicationService.getTimetableSoftwarerMap();
	 // 获取软件列表
	 	mav.addObject("softwareMap", map3);
	 //获得实验项目
	    Set<OperationItem> opite = new HashSet<OperationItem>();
	     for(SchoolCourseDetail  sccd :schoolCourseDetailLists){
	    	 Set<OperationItem> opera = sccd.getSchoolCourse().getSchoolCourseInfo().getOperationItems();
               for(OperationItem op:opera)
               {
               	opite.add(op);
               }

	     }
	     mav.addObject("operationitems",opite);
	     //显示排课所选的软件
	    Map<Integer,List<String>> map5 = new HashMap<Integer, List<String>>();
		Set<Software> software = new HashSet<Software>();
		Set<TimetableAppointment>	tta  = timetableAppointmentDAO.findTimetableAppointmentByCourseCode(courseCode);
		for(TimetableAppointment t :tta){
			List<String>  sw = new ArrayList();
			Set<TimetableSoftwareRelated>	swr  = t.getTimetableSoftwareRelateds();
			for(TimetableSoftwareRelated s:swr){
				sw.add(s.getSoftware().getName());
				map5.put(t.getId(), sw);
			
			}	
		}
	 	mav.addObject("software", map5);
		// 根据选课组获取课程排课列表
		mav.addObject("timetableAppointmentMap",
				timetableAppointmentDAO.findTimetableAppointmentByCourseCode(courseCode));
		// 获取可选的周列表列表
		mav.addObject("currpage", currpage);
		/*mav.addObject("validWeekMap", schoolCourseDetailService.getValidWeekMap(schoolCourseDetailLists.get(0)
				.getWeekday(), null, courseCode));*/
		mav.addObject("tableAppId", tableAppId);
		//获取当前时间，判断你是否为排课约束时间
		Calendar time = Calendar.getInstance();
		Calendar time1 = Calendar.getInstance();
		Calendar time2 =Calendar.getInstance();
		if(time.before(time2)&&time.after(time1)){
	    mav.addObject("confinementTime",1);
		}else{
			 mav.addObject("confinementTime",0);
		}
		
		mav.setViewName("timetable/schedulingcourse/listTimetableAp.jsp");
		return mav;
	}
	/************************************************************
	 * @教务排课-打开课程进度页面（无软件判冲）
	 * @作者：戴昊宇
	 * @日期：2017-07-24
	 ************************************************************/
	@RequestMapping("/timetable/openTimetableApNoSoftware")
	public ModelAndView openTimetableApNoSoftware(HttpServletRequest request, @RequestParam int term,String courseCode, 
			Integer tableAppId, @ModelAttribute("selected_academy") String acno) {
		//tableAppId==0表示新建 tableAppId!=0表示编辑，编辑的时候，可选周次的判断方法就不能与自身判冲
		ModelAndView mav = new ModelAndView();
		// 获取去重的实验分室室列表
		mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
		// 获取可选的实验项目列表列表
		mav.addObject(
				"timetableItemMap",
				outerApplicationService.getTimetableItemMap(schoolCourseDAO.findSchoolCourseByCourseCode(courseCode)
						.iterator().next().getSchoolCourseInfo().getCourseNumber()));
		// 根据选课组编号获取教务排课信息
				List<SchoolCourseDetail> schoolCourseDetailLists = schoolCourseDetailService
						.getSchoolCourseDetailByCourseCode(courseCode);
				mav.addObject("schoolCourseDetailMap", schoolCourseDetailLists);
				
		// 获取可选的教师列表列表
	    Map<String,String> timetableTearcherMap = new HashMap<String, String>();
	    for (SchoolCourseDetail sc : schoolCourseDetailLists) {
			timetableTearcherMap.put(sc.getUser().getUsername(), sc.getUser().getCname() /*+ ";" + user.getUsername()*/);
		}
		Map<String, String> timetableMap = outerApplicationService.getTimetableTearcherMap();
		for(SchoolCourseDetail sc:schoolCourseDetailLists){
			Iterator<Map.Entry<String, String>> it = timetableMap.entrySet().iterator();
			while(it.hasNext()){
			Map.Entry<String, String> entry = it.next();
			if(entry.getKey().equals(sc.getUser().getUsername())){
				it.remove();
			       }
		       }
			
		}
	
		mav.addObject("timetableMap", timetableMap);
		mav.addObject("timetableTearcherMap", timetableTearcherMap);
		// 获取学期列表
		mav.addObject("schoolTerm", schoolTermDAO.findSchoolTermById(term));
		mav.addObject("term", term);
		// 根据选课组编号获取课程排课列表
		mav.addObject("timetableAppointmentMap", timetableAppointmentDAO.findTimetableAppointmentByCourseCode(courseCode));
		
		// 用于排课选择项目时选择本课程名
		SchoolCourseDetail schoolCourseDetail = schoolCourseDetailLists.get(0);
		mav.addObject("schoolCourseDetail", schoolCourseDetail);
		Set<SchoolWeek> schoolweek = new HashSet<SchoolWeek>();
		List<Integer> inte= new ArrayList();
        for(SchoolCourseDetail scd:schoolCourseDetailLists){
    	   SchoolTerm schoolTerm = scd.getSchoolTerm();
    	   for(SchoolWeek slw:schoolTerm.getSchoolWeeks()){
    		   if(!inte.contains(slw.getWeek())){
    			   inte.add(slw.getWeek());
    		   }
    	   }
       }
       //周次排序
        Collections.sort(inte);
   	    mav.addObject("schoolweek",inte);
		mav.addObject("courseCode", courseCode);
		mav.addObject("flag", 1);
		// 根据课程及id获取课程排课列表
		mav.addObject("courseCodes",schoolCourseDAO.findSchoolCourseByCourseCode(courseCode).iterator().next());
		// 获取登录用户信息
		mav.addObject("user", shareService.getUserDetail());
		// 根据选课组编号获取教务排课信息
		List<SchoolCourseDetail> schoolCourseDetails = schoolCourseDetailService
				.getSchoolCourseDetailByCourseCode(courseCode);
		mav.addObject("schoolCourseDetailMap", schoolCourseDetails);
		// 获取实验分室室排课记录
		mav.addObject("labTimetable",
				timetableAppointmentService.getReListLabTimetableAppointments(request, acno, term));
		//将排课id映射给jsp
		mav.addObject("tableAppId", tableAppId);
		if (tableAppId != null && tableAppId!=0&&tableAppId!=-1) {
			mav.addObject("timetableAppointment", timetableAppointmentDAO.findTimetableAppointmentByPrimaryKey(tableAppId));
		}
			mav.setViewName("timetable/schedulingcourse/listTimetableApNoSoftWare.jsp");
			return mav;
	}
	/************************************************************
	 * @throws ParseException 
	 * @教务排课-进行课程进度保存
	 * @作者：戴昊宇
	 * @日期：2017-07-21
	 ************************************************************/
	@RequestMapping("/timetable/saveTimetableAp")
	public ModelAndView saveTimetableAp(HttpServletRequest request) throws ParseException {
		ModelAndView mav = new ModelAndView();
		// 保存调整排课数据
		timetableAppointmentService.saveTimetableAp(request);
		mav.setViewName("redirect:/timetable/openTimetableAp?courseCode=" + request.getParameter("courseCode")
				+ "&currpage=" + request.getParameter("currpage") + "&flag=1&tableAppId=0");
		return mav;
	}
	/************************************************************
	 * @throws ParseException 
	 * @教务排课-进行课程进度保存
	 * @作者：戴昊宇
	 * @日期：2017-07-21
	 ************************************************************/
	@RequestMapping("/timetable/saveTimetableApNoSoftWare")
	public ModelAndView saveTimetableApNoSoftWare(HttpServletRequest request) throws ParseException {
		ModelAndView mav = new ModelAndView();
		// 保存调整排课数据
		timetableAppointmentService.saveTimetableAp(request);
		mav.setViewName("redirect:/timetable/openTimetableApNoSoftware?term="+ request.getParameter("term")+"&courseCode=" + request.getParameter("courseCode")
				+"&tableAppId=0");
		return mav;
	}
	
	/************************************************************
	 * @throws ParseException 
	 * @教务排课-进行课程进度编辑
	 * @作者：戴昊宇
	 * @日期：2017-07-22
	 ************************************************************/
	@RequestMapping("/timetable/editTimetableAp")
	public ModelAndView editTimetableAp(@RequestParam Integer id, Integer coursecode ){
		ModelAndView mav = new ModelAndView();
		TimetableAppointment appointmentById = timetableAppointmentDAO.findTimetableAppointmentById(id);
		Set<TimetableLabRelated> timetableLabRelateds = appointmentById.getTimetableLabRelateds();
		mav.addObject("timetableAppointmentAp",appointmentById);
		mav.addObject("coursecode",coursecode);
		mav.setViewName("timetable/schedulingcourse/listTimetableApedit.jsp");
		return mav;
	}
	
	
	/************************************************************
	 * @throws ParseException 
	 * @教务排课-进行课程编辑进度保存
	 * @作者：戴昊宇
	 * @日期：2017-07-21
	 ************************************************************/
	@RequestMapping("/timetable/saveTimetableApedit")
	public ModelAndView saveTimetableApedit(HttpServletRequest request) throws ParseException {
		ModelAndView mav = new ModelAndView();
		// 保存调整排课数据
		timetableAppointmentService.saveTimetableApedit(request);
		// mav.setViewName("../../timetable/listTimetableTerm?currpage="+request.getParameter("currpage")
		// + "&id=-1");
		mav.setViewName("redirect:/timetable/openTimetableAp?courseCode=" + request.getParameter("courseCode")
				+ "&currpage=0&flag=1&tableAppId=0");
		return mav;
	}
	/**************************************************************************************
	 * @课程进度删除
	 * @页面跳转：timetableAdmin-timetableAdminIframe-deleteAppointment
	 * @作者：戴昊宇
	 * @日期：2017-07-20
	 *************************************************************************************/
	@RequestMapping("/timetable/selfTimetable/deleteTimeTableAppointment")
	public ModelAndView deleteTimeTableAppointment(@RequestParam int id,String courseCode) {
		ModelAndView mav = new ModelAndView();
		timetableAppointmentService.deleteTimetableAppointment(id);
		mav.setViewName("redirect:/timetable/openTimetableAp?currpage=0&flag=0&courseCode="
		+courseCode+"&tableAppId=0");
		return mav;
	}
	/**************************************************************************************
	 * @课程进度删除
	 * @页面跳转：timetableAdmin-timetableAdminIframe-deleteAppointment
	 * @作者：戴昊宇
	 * @日期：2017-07-20
	 *************************************************************************************/
	@RequestMapping("/timetable/selfTimetable/deleteTimeTableAppointmentNosoftware")
	public ModelAndView deleteTimeTableAppointmentNosoftware(@RequestParam int id,String courseCode,int term) {
		ModelAndView mav = new ModelAndView();
		timetableAppointmentService.deleteTimetableAppointment(id);
		mav.setViewName("redirect:/timetable/openTimetableApNoSoftware?term="+term+"&flag=0&courseCode="
		+courseCode+"&tableAppId=0");
		return mav;
	}
	/**************************************************************************************
	 * @自主课程进度删除
	 * @作者：戴昊宇
	 * @日期：2017-07-20
	 *************************************************************************************/
	@RequestMapping("/timetable/selfTimetable/deleteSelfTimeTableAppointment")
	public ModelAndView deleteSelfTimeTableAppointment(@RequestParam int term,int weekday,String classids, String courseCode, int labroom, Integer tableAppId) {
		ModelAndView mav = new ModelAndView();
		int id=tableAppId;
		timetableAppointmentService.deleteTimetableAppointment(id);
		mav.setViewName("redirect:/timetable/selfTimetable/doIframeNoGroupSelfTimetable?term="+term+"&weekday="+weekday+"&classids="+classids+"&courseCode="+courseCode+
				"&labroom="+labroom+"&tableAppId="+tableAppId);
		return mav;
	}
	/**************************************************************************************
	 * @课程进度判断软件
	 * @作者：戴昊宇
	 * @日期：2017-07-20
	 *************************************************************************************/
	@RequestMapping("/timetable/selfTimetable/isSoftWareInstalled")
	public void isSoftWareInstalled(HttpServletResponse response,HttpServletRequest request,@RequestParam String[] softwares)throws Exception {
		String []soft =softwares;
		int count = 0;
		Set<SoftwareRoomRelated> findAllSoftwareRoomRelateds = softwareRoomRelatedDAO.findAllSoftwareRoomRelateds();
		for(SoftwareRoomRelated  softwareroom:findAllSoftwareRoomRelateds){
			//String code = softwareroom.getSoftware().getCode();
		for(int i=0;i<soft.length;i++){
			if(softwareroom.getSoftware().getCode().equals(soft[i])){
				count++;
			}
		}
		}
	    if(count>=soft.length)
	    {
	    	response.getWriter().write("true");
	    }
	    else {
	    	response.getWriter().write("false");
	  
		}
	}
	
	/**************************************************************************************
	 * @课程进度根据项目获得软件
	 * @作者：戴昊宇
	 * @日期：2017-07-20
	 *************************************************************************************/
	@RequestMapping("/timetable/selfTimetable/getSoftWare")
	public void getSoftWare(@RequestParam int operationitems ,HttpServletResponse response,HttpServletRequest request ) throws Exception {
		String json="{";
		OperationItem operationItem = operationItemDAO.findOperationItemById(operationitems);
		Set<SoftwareItemRelated> softwareItemRelateds = operationItem.getSoftwareItemRelateds();
		List<Software> software = new ArrayList();
 		for(SoftwareItemRelated softwareitemrelate:softwareItemRelateds){
			software.add(softwareitemrelate.getSoftware());
		}
		for(Software soft:software){
			json+="\"" +soft.getId()+"\":\""+soft.getName()+"\",";
		}
		json=json.substring(0,json.length()-1);
		json+="}";
		response.getWriter().write(json);
	}
	
	/************************************************************
	 * @功能：判断排课是否冲突
	 * @作者：张德冰
	 * @日期：2018.03.30
	 ************************************************************/
	@RequestMapping("/timetable/isTimetableApNoSoftWare")
	public void isTimetableApNoSoftWare(@RequestParam int[] classes ,int[] weeks,String[] teachers,Integer weekday,Integer term,String courseCode,HttpServletResponse response,HttpServletRequest request) throws Exception  {
			 int size =  timetableAppointmentService.getSzie(term,classes,weeks,weekday,teachers,courseCode);
			 if(size != 0){
		    	  response.getWriter().write("false");
			 }		
	}
	
}
