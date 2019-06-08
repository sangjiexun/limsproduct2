package net.zjcclims.web.teachingArrangement;

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
import net.zjcclims.service.report.TeachingReportService;
import net.zjcclims.service.system.SchoolWeekService;
import net.zjcclims.service.system.SystemService;
import net.zjcclims.service.timetable.*;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/****************************************************************************
 * @功能：授课安排排课管理模块
 * @作者：魏诚 时间：2014-07-14
 ****************************************************************************/
@Controller("TeachingArrangementCourseSchedulingController")
@SessionAttributes("selected_academy")
public class TeachingArrangementCourseSchedulingController<JsonResult> {

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
	private TimetableAppointmentService timetableAppointmentService;
	@Autowired
	private SchoolCourseService schoolCourseService;
	@Autowired
	private ShareService shareService;
	@Autowired
	private SchoolTermDAO schoolTermDAO;
	@Autowired
	private CStaticValueService cStaticValueService;
	@Autowired
	private PConfig pConfig;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private SchoolCourseInfoDAO schoolCourseInfoDAO;
	@Autowired
	private TimetableAppointmentResultDAO timetableAppointmentResultDAO;
	@Autowired
	private LabRoomDAO labRoomDAO;
	@Autowired
	private TeachingReportService teachingReportService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private SchoolWeekService schoolWeekService;

	/************************************************************
	 * @显示教务排课的主显示页面
	 * @作者：魏诚
	 * @日期：2014-07-14
	 ************************************************************/
	@RequestMapping("/teachingArrangement/listAcademicTimetableTerm")
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
		mav.setViewName("timetable/teachingArrangement/listAcademicTimetableTerm.jsp");
		return mav;
	}

	/************************************************************
	 * @显示实训排课的主显示页面
	 * @作者：戴昊宇
	 * @日期：2017-07-24
	 ************************************************************/
	@RequestMapping("/teachingArrangement/listPracticalTimetableTrain")
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
			mav.setViewName("timetable/teachingArrangement/listPracticalWeekTrain.jsp");
		}
		else{
			mav.setViewName("timetable/teachingArrangement/listApplicationTimetableTrain.jsp");
		}
		return mav;
	}

	/**************************************************************************
	 * @排课管理  排课管理-页面列表
	 * @页面跳转：timetableAdmin-timetableAdminIframe
	 * @作者：魏诚
	 * @日期：2014-07-25
	 **************************************************************************/
	@RequestMapping("/teachingArrangement/timetablevertifyRelease")
	public ModelAndView timetablevertify(HttpServletRequest request,
										 @ModelAttribute TimetableAppointment timetableAppointment,
										 @RequestParam int currpage,int status) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("PROJECT_NAME",pConfig.PROJECT_NAME);
		// 获取学期列表
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", schoolTerms);
		// 当前时间的学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term")!=null&&!request.getParameter("term").equals("null")) {
			term = Integer.parseInt(request.getParameter("term"));
		}

		mav.addObject("term", term);

		// 获取登录用户信息
		mav.addObject("user", shareService.getUserDetail());
		// 判断是否标记位为空，如果为空，则清空schoolCourseDetail
		if (request.getParameter("id") != null
				&& request.getParameter("id").equals("-1")) {
			// timetableAppointment.setId(-1) ;
		}

		// 当前查询条件中的课程编号
		String courseNumber = "";
		if (timetableAppointment.getSchoolCourseInfo() != null && timetableAppointment.getSchoolCourseInfo().getCourseNumber() != null) {
			courseNumber = timetableAppointment.getSchoolCourseInfo().getCourseNumber();
		}else {
			if (request.getParameter("courseNumber")!=null&&!request.getParameter("courseNumber").equals("")) {
				courseNumber = request.getParameter("courseNumber");
			}
		}
		mav.addObject("courseNumber", courseNumber);

		if(request.getParameter("status") != null && !request.getParameter("status").equals("")){
			status = Integer.parseInt(request.getParameter("status"));
		}
		//设置区分排课确认0与课表执行1
		Integer isposted = 0;
		if(request.getParameter("isposted") != null && !request.getParameter("isposted").equals("")){
			isposted = Integer.parseInt(request.getParameter("isposted"));
		}
		if(isposted == 1){
			status = 1;
		}
		mav.addObject("isPosted", isposted);
		//判断已审核和未审核查询
		String auditStr = request.getParameter("auditStr");
		if(auditStr!=null&&!auditStr.equals("")){
			status = Integer.parseInt(auditStr);
		}else{
			status= -1;
		}
		mav.addObject("auditStr", auditStr);
		// 设置分页变量并赋值为10；
		int pageSize = 10;//CommonConstantInterface.INT_PAGESIZE;
		// 根据课程及id获取课程排课列表的计数
		int totalRecords = timetableAppointmentService
				.getCountTimetableAppointmentsByQuery(term,courseNumber,
						status,1);
		if(isposted == 1){
			totalRecords = timetableAppointmentService.getViewLabroomTimetableRegistersCount(timetableAppointment);
		}
		mav.addObject("totalRecords", totalRecords);

		Map<String, Integer> pageModel = shareService.getPage(currpage,
				pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		// 将currpage映射到page，用来获取当前页的页码
		mav.addObject("page", currpage);
		// 获取所有的学期
		List<SchoolTerm> terms = schoolCourseDetailService
				.getSchoolTermsByNow();
		// 映射terms给terms
		mav.addObject("terms", terms);
		// 将course映射到course；
		mav.addObject("course", new SchoolCourse());
		mav.addObject("status",status);
		// 根据课程及id获取课程排课列表
		List<TimetableAppointment> timetableAppointmentsByQuery = timetableAppointmentService
				.getTimetableAppointmentsByQuery(term,courseNumber,
						status,
						currpage - 1, pageSize,1);
		mav.addObject("timetableAppointments", timetableAppointmentsByQuery);
		//获得实验室管理员

		Map<Integer,String>labRoomAdmin= new HashMap<Integer, String>();
		for(TimetableAppointment t:timetableAppointmentsByQuery){
			Set<TimetableLabRelated> timetableLabRelateds = t.getTimetableLabRelateds();
			for(TimetableLabRelated tr:timetableLabRelateds){
				String username="";
				List<LabRoomAdmin> getlabRoomAdmin = timetableAppointmentService.getlabRoomAdmin(tr.getLabRoom().getId());
				if(getlabRoomAdmin.size()>0){
					username = getlabRoomAdmin.get(0).getUser().getUsername();
				}
				labRoomAdmin.put(t.getId(),username);
			}
		}

		mav.addObject("labRoomAdmin", labRoomAdmin);
		Set<SchoolCourseInfo> findAllSchoolCourseInfos = schoolCourseInfoDAO.findAllSchoolCourseInfos();
		mav.addObject("schoolCoursess",findAllSchoolCourseInfos);
		mav.addObject("user",shareService.getUserDetail());
		mav.setViewName("timetable/teachingArrangement/timetablevertifyRelease.jsp");
		Set<TimetableAppointmentResult> allresults = timetableAppointmentResultDAO.findAllTimetableAppointmentResults();
		mav.addObject("allresults",allresults);
		return mav;
	}

	/************************************************************
	 * @总课表页面
	 * @作者：戴昊宇
	 * @日期：2017-11-09
	 ************************************************************/
	@RequestMapping("/teachingArrangement/listGeneralTimetableQuery")
	public ModelAndView labReservationCalendar(HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		/*-------用作查询------------*/
		mav.addObject("courseCode", request.getParameter("courseCode"));
		// 获取登录用户信息
		mav.addObject("user", shareService.getUserDetail());
		// 获得学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		// 获取学期列表用于查询
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerm", schoolTerms);
		// 获取课程列表
		mav.addObject("schoolCourses", shareService.getSchoolCourseList(term));
		mav.addObject("term", term);
		mav.addObject("courseCodes", schoolCourseDetailService.getCourseCodeInSchoolCourse(term, acno));
		// 获取去重的实验分室室列表
		mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
		/*-----------------------------*/
		// 获取实验室
		int roomId = 0;
		HttpSession session=request.getSession();
		if (!EmptyUtil.isStringEmpty(request.getParameter("roomId"))) {
			roomId = Integer.parseInt(request.getParameter("roomId"));
		}else if(session.getAttribute("roomId")!=null){
			roomId=(Integer)(session.getAttribute("roomId"));
			session.setAttribute("roomId", null);
		}
		mav.addObject("labRoom", labRoomDAO.findLabRoomById(roomId));
		mav.addObject("roomId", roomId);
		// 获取周
		int week = shareService.getBelongsSchoolWeek(Calendar.getInstance());
		if(!EmptyUtil.isStringEmpty(request.getParameter("week"))){
			week = Integer.parseInt(request.getParameter("week"));
		}
		mav.addObject("week", week);
		// 获得所有教师
		List<User> teacheres = shareService.findAllTeacheres();
		mav.addObject("teacheres", teacheres);
		String teacher = "";
		if (!EmptyUtil.isStringEmpty(request.getParameter("teahcer"))) {
			teacher = request.getParameter("teahcer");
		}else if(request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_TEACHER")!=-1) {
			teacher = shareService.getUserDetail().getUsername();
		}
		mav.addObject("teacherId", teacher);
		//获取当前学期
		int termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		// 获取所有的学期
		mav.addObject("termId", termId);  //当前学期
		mav.addObject("weeksMap", teachingReportService.getWeekMap(term));  //周次下拉框数据
		// 获取实验分室室排课记录
		mav.addObject("labTimetable", teachingReportService.getLabTimetableAppointments(request,term, roomId, week,teacher, null));
		mav.addObject("labSelfTimetable",
				teachingReportService.getSelfLabTimetableAppointment2s(request, term, roomId, week,teacher));
		//获取所有借此对应的时间
		mav.addObject("systemtime", systemService.getAllTimebyjieci());
		mav.addObject("schoolweeek", schoolWeekService.getMapDate());

		// 获取班级
		String classNumber = "";
		if (!EmptyUtil.isStringEmpty(request.getParameter("schoolClass"))) {
			classNumber = request.getParameter("schoolClass");
		}
		mav.addObject("classNumber", classNumber);
		mav.addObject("schoolClassess", systemService.loadSchoolClassess());// 返回所有班级

		mav.setViewName("timetable/teachingArrangement/listGeneralTimetableQuery.jsp");
		return mav;
	}

}
