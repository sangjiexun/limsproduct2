package net.zjcclims.web.timetable;

import excelTools.JsGridReportBase;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.CStaticValueService;
import net.zjcclims.service.common.MySQLService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.report.TeachingReportService;
import net.zjcclims.service.system.SchoolWeekService;
import net.zjcclims.service.system.SystemService;
import net.zjcclims.service.timetable.OuterApplicationService;
import net.zjcclims.service.timetable.SchoolCourseDetailService;
import net.zjcclims.service.timetable.TimetableAppointmentService;
import net.zjcclims.service.timetable.TimetableReSchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.*;


/****************************************************************************
 * @功能：二次管理模块
 * @作者：魏诚 时间：2014-07-14
 ****************************************************************************/
@Controller("TimetableReSchedulingController")
@SessionAttributes("selected_academy")
public class TimetableReSchedulingController<JsonResult> {
	/************************************************************
	 * @初始化WebDataBinder，这个WebDataBinder用于填充被@InitBinder注释的处理 方法的command和form对象
	 * 
	 ************************************************************/
	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) {
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
	private TimetableAppointmentService timetableAppointmentService;
	@Autowired
	private SchoolTermDAO schoolTermDAO;
	@Autowired
	private SchoolCourseDetailService schoolCourseDetailService;
	@Autowired
	private ShareService shareService;
	@Autowired
	private OperationItemDAO operationItemDAO;
	@Autowired
	private TimetableGroupDAO timetableGroupDAO;
	@Autowired
	private TimetableAppointmentDAO timetableAppointmentDAO;
	@Autowired
	private TimetableReSchedulingService timetableReSchedulingService;
	@Autowired
	private SchoolCourseDAO schoolCourseDAO;
	@Autowired
	private TimetableGroupStudentsDAO timetableGroupStudentsDAO;
	@Autowired
	private CStaticValueService cStaticValueService;
	@Autowired
	private LabRoomDAO labRoomDAO;
	@Autowired
	private TeachingReportService teachingReportService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private SchoolWeekService schoolWeekService;
	@Autowired
	private MySQLService mySQLService;
	/************************************************************
	 * @二次排课： 实现二次排课（只有不含分组）的功能入口
	 * @页面跳转：listReTimetable-listReTimetable-doIframeGroupReTimetable-saveGroupReTimetable
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/listOnlyNoGroupReTimetable")
	public ModelAndView listOnlyNoGroupReTimetable(HttpServletRequest request,
			@ModelAttribute SchoolCourseDetail schoolCourseDetail, @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		// 获取去重的实验分室室列表
		mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
		// 获取登录用户信息
		mav.addObject("user", shareService.getUserDetail());

		// 获取学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		mav.addObject("schoolTerm", term);
		mav.addObject("term", term);
		SchoolTerm schoolTerm = schoolTermDAO.findSchoolTermById(term);

		// 获取课程列表
		mav.addObject("schoolCourses", shareService.getSchoolCourseList(schoolTerm.getId()));
		// 获取学期列表
		mav.addObject("schoolTerm", schoolTerm);
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", schoolTerms);
		// 获取实验分室室排课记录
		mav.addObject("labTimetable",
				timetableAppointmentService.getReListLabTimetableAppointments(request, acno, schoolTerm.getId()));

		// 获取二次排课实验分室室排课记录
		mav.addObject("labSelfTimetable",
				timetableAppointmentService.getSelfListLabTimetableAppointments(request, acno, schoolTerm.getId()));

		mav.setViewName("timetable/reTimetable/listOnlyNoGroupReTimetable.jsp");
		return mav;
	}

	/************************************************************
	 * @二次排课： 保存实现二次排课（不含分组）的内容
	 * @throws ParseException 
	 * @页面跳转：listReTimetable-listReTimetable-doIframeGroupReTimetable-saveGroupReTimetable
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/saveNoGroupReTimetable")
	public ModelAndView saveNoGroupReTimetable(HttpServletRequest request) throws ParseException {
		ModelAndView mav = new ModelAndView();
		// 保存调整排课数据
		timetableAppointmentService.saveNoGroupReTimetable(request);

		String rtUrl = "redirect:/timetable/doIframeNoGroupReTimetable?" + "term=" + request.getParameter("term")
				+ "&flag=1&courseCode=" + request.getParameter("courseCode") + "&classids="
				+ request.getParameter("classids") + "&weekday=" + request.getParameter("weekday") + "&labroom="
				+ request.getParameterValues("labRooms")[0];
		mav.setViewName(rtUrl);
		return mav;

	}

	/************************************************************
	 * @二次排课： 保存实现二次排课（仅不含分组）的内容
	 * @throws ParseException 
	 * @页面跳转：listReTimetable-listReTimetable-doIframeGroupReTimetable-saveGroupReTimetable
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/saveOnlyNoGroupReTimetable")
	public ModelAndView saveOnlyNoGroupReTimetable(HttpServletRequest request) throws ParseException {
		ModelAndView mav = new ModelAndView();
		// 保存调整排课数据
		timetableAppointmentService.saveNoGroupReTimetable(request);

		String rtUrl = "redirect:/timetable/doOnlyNoGroupReTimetable?" + "term=" + request.getParameter("term")
				+ "&flag=1&courseCode=" + request.getParameter("courseCode") + "&classids="
				+ request.getParameter("classids") + "&weekday=" + request.getParameter("weekday") + "&labroom="
				+ request.getParameterValues("labRooms")[0];
		mav.setViewName(rtUrl);
		return mav;

	}

	/************************************************************
	 * @二次排课： 保存实现二次排课（分组）的内容
	 * @throws ParseException 
	 * @页面跳转：listReTimetable-listReTimetable-doIframeGroupReTimetable-saveGroupReTimetable
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/saveGroupReTimetable")
	public ModelAndView saveGroupReTimetable(HttpServletRequest request) throws ParseException {
		ModelAndView mav = new ModelAndView();
		// 如果已保存排课记录，先删除，再保存修改。保存待删除的标记位
		TimetableAppointment deleteTimetableAppointment = null;
		if (!request.getParameter("appointment").isEmpty()) {
			deleteTimetableAppointment = timetableAppointmentDAO.findTimetableAppointmentById(Integer.parseInt(request
					.getParameter("appointment")));
		}
		// 保存调整排课数据
		TimetableAppointment timetableAppointment = timetableAppointmentService.saveGroupReTimetable(request);

		// 删除修改前的记录
		if (deleteTimetableAppointment != null) {
			timetableAppointmentService.deleteAppointment(deleteTimetableAppointment.getId());
		}
		mav.setViewName("redirect:/timetable/doIframeGroupReTimetable?term="
				+ timetableAppointment.getSchoolCourseDetail().getSchoolTerm().getId() + "&courseCode="
				+ timetableAppointment.getCourseCode());
		return mav;

	}

	/************************************************************
	 * @排课管理的删除排课条目
	 * @页面跳转：timetableAdmin-timetableAdminIframe-deleteAppointment
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/reTimetable/deleteReGroupAppointment")
	public ModelAndView deleteSelfGroupAppointment(@RequestParam int id) {
		ModelAndView mav = new ModelAndView();
		TimetableAppointment timetableAppointment = timetableAppointmentDAO.findTimetableAppointmentById(id);
		// 排课管理的删除排课条目
		timetableAppointmentService.deleteAppointment(id);
		mav.setViewName("redirect:/timetable/doIframeGroupReTimetable?term="
				+ timetableAppointment.getSchoolCourseDetail().getSchoolTerm().getId() + "&courseCode="
				+ timetableAppointment.getCourseCode());
		return mav;
	}
	/************************************************************
	 * @总课表页面
	 * @作者：戴昊宇
	 * @日期：2017-11-09
	 ************************************************************/
	@RequestMapping("/timetable/listGeneralTimetable")
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
		mav.addObject("term", term);
		// 获取实验室
		int roomId = 0;
		HttpSession session=request.getSession();
		if (!EmptyUtil.isStringEmpty(request.getParameter("roomId"))) {
			roomId = Integer.parseInt(request.getParameter("roomId"));
		}else if(session.getAttribute("roomId")!=null){
			roomId=(Integer)(session.getAttribute("roomId"));
			session.setAttribute("roomId", null);
		}
		mav.addObject("roomId", roomId);
		// 获取周
		int week = shareService.getBelongsSchoolWeek(Calendar.getInstance()); 
		if(!EmptyUtil.isStringEmpty(request.getParameter("week"))){
			week = Integer.parseInt(request.getParameter("week"));
		}
		mav.addObject("week", week);

		mav.addObject("labRoomMap", outerApplicationService.findLabRoomMaps(null, term, week));
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
		mav.addObject("termId", term);
		mav.addObject("weeksMap", teachingReportService.getWeekMap(term));  //周次下拉框数据
		// 获取实验分室室排课记录
		List<TimetableLabRelated> labTimetable = teachingReportService.getLabTimetableAppointments(request,term, roomId, week,teacher, null);
		mav.addObject("labTimetable", labTimetable);
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
		
		mav.setViewName("timetable/reTimetable/listGeneralTimetable.jsp");
		return mav;
	}
	/************************************************************
	 * @throws Exception
	 * @导出课表
	 * @作者：廖文辉
	 * @日期：2018-11-26
	 ************************************************************/
	@RequestMapping("/timetable/exportTimetable")
	public void exportTotalPractical(HttpServletRequest request,HttpServletResponse response,@ModelAttribute("selected_academy") String acno)throws Exception{
		int termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			termId = Integer.parseInt(request.getParameter("term"));
		}
		SchoolTerm schoolTerm=schoolTermDAO.findSchoolTermByPrimaryKey(termId);
		// 获取实验室
		int roomId = 0;
		HttpSession session=request.getSession();
		if (!EmptyUtil.isStringEmpty(request.getParameter("roomId"))) {
			roomId = Integer.parseInt(request.getParameter("roomId"));
		}else if(session.getAttribute("roomId")!=null){
			roomId=(Integer)(session.getAttribute("roomId"));
			session.setAttribute("roomId", null);
		}
		// 获取周
		int week = shareService.getBelongsSchoolWeek(Calendar.getInstance());
		if(!EmptyUtil.isStringEmpty(request.getParameter("week"))){
			week = Integer.parseInt(request.getParameter("week"));
		}
		String teacher = "";
		if (!EmptyUtil.isStringEmpty(request.getParameter("teahcer"))) {
			teacher = request.getParameter("teahcer");
		}
		// 查询
		List<TimetableLabRelated> labTimetable = teachingReportService.getLabTimetableAppointments(request,termId, roomId, week,teacher, null);
		List<TimetableLabRelated> timetableLabRelateds = new ArrayList<TimetableLabRelated>();
		if (labTimetable!=null && labTimetable.size()>0) {
			for (TimetableLabRelated timetableLabRelated : labTimetable) {
				timetableLabRelateds.add(timetableLabRelated);
			}
		}
		JsGridReportBase report = new JsGridReportBase(request, response);

		report.exportTeachingTimetableToExcel(timetableLabRelateds, schoolTerm.getTermName());
	}
	/************************************************************
	 * @二次排课： 实现二次排课（含分组）的功能入口
	 * @页面跳转：listReTimetable
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/listReTimetable")
	public ModelAndView listReTimetable(HttpServletRequest request, @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("labroom", request.getParameter("labroom"));
		mav.addObject("courseCode", request.getParameter("courseCode"));
		// 获取登录用户信息
		mav.addObject("user", shareService.getUserDetail());
		// 获取学期列表
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerm", schoolTerms);

		// 根据课程及id获取选课组列表
		//int term = shareService.findNewTerm().getId();取当前所处学期或下个学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		// 获取课程列表
		mav.addObject("schoolCourses", shareService.getSchoolCourseList(term));
		mav.addObject("term", term);
		mav.addObject("courseCodes", schoolCourseDetailService.getCourseCodeInSchoolCourse(term, acno));
		// 获取去重的实验分室室列表
		mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));

		// 获取二次排课实验分室室排课记录
		mav.addObject("labSelfTimetable",
				timetableAppointmentService.getSelfCourseLabTimetableAppointments(request, term));

		// 获取自建实验分室室排课记录
		mav.addObject("labReTimetable",
				timetableAppointmentService.getReListLabTimetableAppointments(request, acno, term));

		mav.setViewName("timetable/reTimetable/listReTimetable.jsp");
		return mav;
	}

	/************************************************************
	 * @二次排课： 实现二次排课的iframe功能入口（含分组）
	 * @页面跳转：listReTimetable-listReTimetable-doIframeGroupReTimetable
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/doIframeReTimetable")
	public ModelAndView doIframeReTimetable(HttpServletRequest request, @RequestParam int term, int weekday,
			String classids, @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		/*
		 * 获取查询条件
		 */
		mav.addObject("term", request.getParameter("term"));
		mav.addObject("labroom", request.getParameter("labroom"));
		// mav.addObject("week", request.getParameter("week"));
		mav.addObject("courseCode", request.getParameter("courseCode"));
		// 获取登录用户信息
		mav.addObject("user", shareService.getUserDetail());
		// 获取学期列表
		mav.addObject("schoolTerm", schoolTermDAO.findAllSchoolTerms());
		// 获取星期
		mav.addObject("weekday", weekday);
		// 根据课程及id获取课程排课列表
		SchoolCourseDetail schoolCourseDetail = new SchoolCourseDetail();
		schoolCourseDetail.setId(-1);
		mav.addObject("schedulingCourseMap",
				schoolCourseDetailService.getCourseDetailsByQuery(term, schoolCourseDetail,0, -1, acno, request));

		mav.addObject("courseCodes", schoolCourseDetailService.getCourseCodeInSchoolCourse(term, acno));
		// 获取星期
		mav.addObject("classids", classids);
		// 获取实验分室室排课记录
		mav.addObject(
				"labTimetable",
				timetableAppointmentService.getReListLabTimetableAppointments(request, acno,
						Integer.parseInt(request.getParameter("term"))));
		mav.setViewName("timetable/reTimetable/listIframeReTimetable.jsp");
		return mav;
	}

	/************************************************************
	 * @二次排课： 实现二次排课的不分组功能入口
	 * @页面跳转：listReTimetable-listReTimetable-doIframeGroupReTimetable
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/doIframeNoGroupReTimetable")
	public ModelAndView doIframeNoGroupReTimetable(HttpServletRequest request, @RequestParam int term, int weekday,
			String classids, int flag, String courseCode, int labroom, @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		// flag提示是否处理调课完成0不提示，1提示
		mav.addObject("flag", flag);
		// 获取去重的实验分室室列表
		mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
		// 获取可选的实验项目列表列表
		// 获取可选的实验项目列表列表
		mav.addObject(
				"timetableItemMap",
				outerApplicationService.getTimetableItemMap(schoolCourseDAO.findSchoolCourseByCourseCode(courseCode)
						.iterator().next().getSchoolCourseInfo().getCourseNumber()));

		// 获取可选的教师列表列表
		mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap(acno));
		// 获取学期列表
		mav.addObject("schoolTerm", schoolTermDAO.findSchoolTermById(term));
		// 根据选课组编号获取课程排课列表
		mav.addObject("timetableAppointmentMap",
				timetableAppointmentDAO.findTimetableAppointmentByCourseCode(courseCode));
		// 获取星期
		mav.addObject("weekday", weekday);
		// mav.addObject("week", week);
		// LabRoom labRoom = labRoomDAO.findLabRoomById(labroom);
		mav.addObject("labroom", labroom);
		// 根据所选节次获取可用的星期
		Integer[] weeks = outerApplicationService.getValidWeeks(term, Integer.parseInt(classids), labroom, weekday);
		mav.addObject("weeks", weeks);
		mav.addObject("courseCode", courseCode);
		
		// 获取实验室排课的通用配置对象；
		CStaticValue cStaticValue = cStaticValueService.getCStaticValueByTimetableLabDevice(acno);
		mav.addObject("cStaticValue", cStaticValue);
		
		// 根据课程及id获取课程排课列表
		mav.addObject("courseCodes", schoolCourseDetailService.getSchoolCourseDetailByCourseCode(courseCode));
		// 获取星期
		mav.addObject("classids", classids);
		// 获取登录用户信息
		mav.addObject("user", shareService.getUserDetail());
		// 获取实验分室室排课记录
		mav.addObject("labTimetable", timetableAppointmentService.getReListLabTimetableAppointments(request, acno, term));
		// 如果權限為true則，進行排課處理，否則返回列表
		if (timetableAppointmentDAO.findTimetableAppointmentByCourseCode(courseCode).size() == 0
				|| shareService.isOrNotAuthority(timetableAppointmentDAO
						.findTimetableAppointmentByCourseCode(courseCode).iterator().next())) {
			mav.setViewName("timetable/reTimetable/listIframeNoGroupReTimetable.jsp");
			return mav;
		} else {
			mav.setViewName("timetable/reTimetable/warning.jsp");
			return mav;
		}

	}

	/************************************************************
	 * @二次排课：实现二次排课的分组排课功能入口
	 * @页面跳转：listReTimetable-listReTimetable-doIframeGroupReTimetable
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/doIframeGroupReTimetable")
	public ModelAndView doIframeGroupReTimetable(HttpServletRequest request,
			@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		// 获取去重的实验分室室列表
		mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
		// 获取可选的教师列表列表
		mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap());
		// 获取学期列表
		mav.addObject("schoolTerm", schoolTermDAO.findSchoolTermById(Integer.parseInt(request.getParameter("term"))));
		// 获取选课组
		mav.addObject("courseCode", request.getParameter("courseCode"));
		// 根据选课组获取课程排课列表
		mav.addObject("courseCodes", schoolCourseDetailService.getSchoolCourseDetailByCourseCode(request.getParameter(
				"courseCode").toString()));
		// 获取登录用户信息
		mav.addObject("user", shareService.getUserDetail());
		// 获取批次列表
		mav.addObject("timetableGroups", timetableReSchedulingService.getTimetableGroupByCourseCode(request
				.getParameter("courseCode").toString()));

		// 如果權限為true則，進行排課處理，否則返回列表
		if (timetableAppointmentDAO.findTimetableAppointmentByCourseCode(request.getParameter("courseCode").toString())
				.size() == 0
				|| shareService.isOrNotAuthority(timetableAppointmentDAO
						.findTimetableAppointmentByCourseCode(request.getParameter("courseCode").toString()).iterator()
						.next())) {
			mav.setViewName("timetable/reTimetable/listIframeGroupReTimetable.jsp");
			return mav;
		} else {
			mav.setViewName("timetable/reTimetable/warning.jsp");
			return mav;
		}
	}
	
	/************************************************************
	 * 功能：排课管理--查看学生二次分组情况
	 * 作者：贺子龙
	 * 日期：2016-03-05
	 ************************************************************/
	@RequestMapping("/timetable/listTimetableGroup")
	public ModelAndView listTimetableGroup(HttpServletRequest request,
			@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		// 获取去重的实验分室室列表
		mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
		// 获取可选的教师列表列表
		mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap());
		// 获取学期列表
		mav.addObject("schoolTerm", schoolTermDAO.findSchoolTermById(Integer.parseInt(request.getParameter("term"))));
		// 获取选课组
		mav.addObject("courseCode", request.getParameter("courseCode"));
		// 根据选课组获取课程排课列表
		mav.addObject("courseCodes", schoolCourseDetailService.getSchoolCourseDetailByCourseCode(request.getParameter(
				"courseCode").toString()));
		// 获取登录用户信息
		mav.addObject("user", shareService.getUserDetail());
		// 获取批次列表
		mav.addObject("timetableGroups", timetableReSchedulingService.getTimetableGroupByCourseCode(request
				.getParameter("courseCode").toString()));
		// 将groupId映射到jsp页面
		mav.addObject("groupId", request.getParameter("groupId"));
		// 如果權限為true則，進行排課處理，否則返回列表
		if (timetableAppointmentDAO.findTimetableAppointmentByCourseCode(request.getParameter("courseCode").toString())
				.size() == 0
				|| shareService.isOrNotAuthority(timetableAppointmentDAO
						.findTimetableAppointmentByCourseCode(request.getParameter("courseCode").toString()).iterator()
						.next())) {
			TimetableAppointment timetableAppointment =
					timetableAppointmentDAO.findTimetableAppointmentByCourseCode(request.getParameter("courseCode")).iterator().next();
			mav.addObject("timetableStyle", timetableAppointment.getTimetableStyle());
			mav.setViewName("timetable/reTimetable/listTimetableGroup.jsp");
			return mav;
		} else {
			mav.setViewName("timetable/reTimetable/warning.jsp");
			return mav;
		}
	}

	/************************************************************
	 * 功能：排课管理--查看学生循环分组情况
	 * 作者：贺子龙
	 * 日期：2016-03-05
	 ************************************************************/
	@RequestMapping("/timetable/listTimetableGroupCycle")
	public ModelAndView listTimetableGroupCycle(HttpServletRequest request,
										   @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		// 获取去重的实验分室室列表
		mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
		// 获取可选的教师列表列表
		mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap());
		// 获取学期列表
		mav.addObject("schoolTerm", schoolTermDAO.findSchoolTermById(Integer.parseInt(request.getParameter("term"))));
		// 获取选课组
		mav.addObject("courseCode", request.getParameter("courseCode"));
		// 根据选课组获取课程排课列表
		mav.addObject("courseCodes", schoolCourseDetailService.getSchoolCourseDetailByCourseCode(request.getParameter(
				"courseCode").toString()));
		// 获取登录用户信息
		mav.addObject("user", shareService.getUserDetail());
		// 获取批次列表
		mav.addObject("timetableGroups", timetableReSchedulingService.getTimetableGroupByCourseCode(request
				.getParameter("courseCode").toString()));
		// 将groupId映射到jsp页面
		mav.addObject("groupId", request.getParameter("groupId"));
		// 如果權限為true則，進行排課處理，否則返回列表
		if (timetableAppointmentDAO.findTimetableAppointmentByCourseCode(request.getParameter("courseCode").toString())
				.size() == 0
				|| shareService.isOrNotAuthority(timetableAppointmentDAO
				.findTimetableAppointmentByCourseCode(request.getParameter("courseCode").toString()).iterator()
				.next())) {
			TimetableAppointment timetableAppointment =
					timetableAppointmentDAO.findTimetableAppointmentByCourseCode(request.getParameter("courseCode")).iterator().next();
			mav.addObject("timetableStyle", timetableAppointment.getTimetableStyle());
			mav.setViewName("timetable/reTimetable/listTimetableGroupCycle.jsp");
			return mav;
		} else {
			mav.setViewName("timetable/reTimetable/warning.jsp");
			return mav;
		}
	}

	/************************************************************
	 * @二次排课：实现含分组二次排课的选择选课组后的iframe功能入口
	 * @页面跳转：listReTimetable-listReTimetable-doIframeGroupReTimetable
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/doReTimetableIframe")
	public ModelAndView doReTimetableIframe(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("term", request.getParameter("term"));
		mav.addObject("labroom", request.getParameter("labroom"));
		mav.addObject("weekday", request.getParameter("weekday"));
		mav.addObject("classids", request.getParameter("classids"));
		mav.addObject("courseCode", request.getParameter("courseCode"));
		// 如果分批或分组建立则，不分组功能隐藏
		// mav.addObject("timetableBatches",timetableBatchDAO.findTimetableBatchByCourseCode(request.getParameter("courseCode")).iterator().hasNext());
		int ifgroup = -1;
		for (TimetableAppointment timetableAppointment : timetableAppointmentDAO
				.findTimetableAppointmentByCourseCode(request.getParameter("courseCode"))) {

			// 如果有排课记录,切有分组记录则跳转分组处理
			if (timetableAppointment.getTimetableGroups().size() > 0) {
				ifgroup = 1;
				break;
			}
			// 如果有排课记录则分组选择为
			ifgroup = 0;
		}
		mav.addObject("timetableBatches", ifgroup);
		mav.setViewName("timetable/reTimetable/listReTimetableIframe.jsp");
		return mav;
	}

	/************************************************************
	 * @二次排课：实现只有不含分组二次排课的选择选课选择
	 * @页面跳转：listReTimetable-listReTimetable-doIframeGroupReTimetable
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/doOnlyNoGroupSelectCourseCode")
	public ModelAndView doOnlyNoGroupSelectCourseCode(HttpServletRequest request,
			@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("courseCodes", schoolCourseDetailService.getCourseCodeInSchoolCourse(
				Integer.parseInt(request.getParameter("term")), acno));
		mav.addObject("term", request.getParameter("term"));
		mav.addObject("labroom", request.getParameter("labroom"));
		mav.addObject("weekday", request.getParameter("weekday"));
		mav.addObject("user", shareService.getUserDetail());
		mav.addObject("classids", request.getParameter("classids"));
		mav.setViewName("timetable/reTimetable/doOnlyNoGroupSelectCourseCode.jsp");
		return mav;
	}

	/************************************************************
	 * @二次排课： 实现二次排课的不分组功能入口
	 * @页面跳转：listReTimetable-listReTimetable-doIframeGroupReTimetable
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/doOnlyNoGroupReTimetable")
	public ModelAndView doOnlyNoGroupReTimetable(HttpServletRequest request, @RequestParam int flag,
			@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		// 获取传值
		int term = Integer.parseInt(request.getParameter("term"));
		String courseCode = request.getParameter("courseCode");
		int weekday = Integer.parseInt(request.getParameter("weekday"));
		int labroom = Integer.parseInt(request.getParameter("labroom"));
		int classids = Integer.parseInt(request.getParameter("classids"));
		// flag提示是否处理调课完成0不提示，1提示
		mav.addObject("flag", flag);
		// 获取去重的实验分室室列表
		mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
		// 获取可选的实验项目列表列表
		mav.addObject(
				"timetableItemMap",
				outerApplicationService.getTimetableItemMap(schoolCourseDAO.findSchoolCourseByCourseCode(courseCode)
						.iterator().next().getSchoolCourseInfo().getCourseNumber()));

		// 获取可选的教师列表列表
		mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap());
		// 获取学期列表
		mav.addObject("schoolTerm", schoolTermDAO.findSchoolTermById(term));
		// 根据选课组编号获取课程排课列表
		mav.addObject("timetableAppointmentMap",
				timetableAppointmentDAO.findTimetableAppointmentByCourseCode(courseCode));
		// 获取星期
		mav.addObject("weekday", weekday);
		// mav.addObject("week", week);
		// LabRoom labRoom = labRoomDAO.findLabRoomById(labroom);

		// 根据所选节次获取可用的星期
		Integer[] weeks = outerApplicationService.getValidWeeks(term, classids, labroom, weekday);
		// Integer[] weeks = outerApplicationService.getValidWeeks(term,
		// classids,
		// labroom, weekday);
		// Integer[] weeks =new
		// Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21};
		// 获取可用的星期

		// 根据课程及id获取课程排课列表
		List<SchoolCourseDetail> listSchoolCourseDetail = schoolCourseDetailService
				.getSchoolCourseDetailByCourseCode(courseCode);
		if (listSchoolCourseDetail.size() == 0) {
			// mav.addObject("warning","您所选的选课组无排课数据！");
			mav.setViewName("../../timetable/doOnlyNoGroupSelectCourseCode");
			return mav;
		} else {
			mav.addObject("courseCodes", listSchoolCourseDetail);
		}

		// 获取星期
		mav.addObject("classids", classids);
		mav.addObject("labroom", labroom);
		mav.addObject("weeks", weeks);
		mav.addObject("courseCode", courseCode);
		// 获取登录用户信息
		mav.addObject("user", shareService.getUserDetail());
		// 获取实验分室室排课记录
		mav.addObject("labTimetable", timetableAppointmentService.getReListLabTimetableAppointments(request, acno, term));

		// 如果權限為true則，進行排課處理，否則返回列表
		if (timetableAppointmentDAO.findTimetableAppointmentByCourseCode(courseCode).size() == 0
				|| shareService.isOrNotAuthority(timetableAppointmentDAO
						.findTimetableAppointmentByCourseCode(courseCode).iterator().next())) {
			mav.setViewName("timetable/reTimetable/doOnlyNoGroupReTimetable.jsp");
			return mav;
		} else {
			mav.setViewName("timetable/reTimetable/warning.jsp");
			return mav;
		}
	}

	/************************************************************
	 * @二次排课：实现含分组二次排课的选择选课组页面功能入口
	 * @页面跳转：listReTimetable-listReTimetable-doIframeGroupReTimetable-doSelectCourseCode
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/doSelectCourseCode")
	public ModelAndView doSelectCourseCode(HttpServletRequest request, @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("courseCodes", schoolCourseDetailService.getCourseCodeInSchoolCourse(
				Integer.parseInt(request.getParameter("term")), acno));
		mav.addObject("term", request.getParameter("term"));
		mav.addObject("labroom", request.getParameter("labroom"));
		mav.addObject("weekday", request.getParameter("weekday"));
		mav.addObject("classids", request.getParameter("classids"));
		mav.addObject("courseCode", request.getParameter("courseCode"));
		mav.addObject("user", shareService.getUserDetail());
		mav.setViewName("timetable/reTimetable/doSelectCourseCode.jsp");
		return mav;
	}

	/************************************************************
	 * @二次排课：实现含分组二次排课的选择选课组页面功能入口
	 * @页面跳转：listReTimetable-listReTimetable-doIframeGroupReTimetable-newTimetableGroup
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/newTimetableGroup")
	public ModelAndView newTimetableGroup(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("term", request.getParameter("term"));
		mav.addObject("labroom", request.getParameter("labroom"));
		mav.addObject("week", request.getParameter("week"));
		mav.addObject("weekday", request.getParameter("weekday"));
		mav.addObject("classids", request.getParameter("classids"));
		mav.addObject("item", operationItemDAO.findOperationItemById(Integer.parseInt(request.getParameter("item"))));
		mav.addObject("courseCode", request.getParameter("courseCode"));
		// 保存分组计数信息
		mav.addObject("countGroup", request.getParameter("countGroup"));
		// 保存批次名称
		mav.addObject("batchName", request.getParameter("batchName"));
		// 保存批次开始选课日期
		mav.addObject("startDate", request.getParameter("startDate"));
		// 保存批次结束选课日期
		mav.addObject("endDate", request.getParameter("endDate"));
		// 获取可选的实验项目列表列表
		mav.addObject("courseCodeItemList",
				outerApplicationService.getCourseCodeItemList(request.getParameter("courseCode")));
		// 根据课程及id获取课程排课列表
		// 保存学生选课信息
		mav.addObject("ifselect", request.getParameter("ifselect"));
		// 根据课程及id获取课程排课列表
		mav.addObject("courseCodes",
				schoolCourseDetailService.getSchoolCourseDetailByCourseCode(request.getParameter("courseCode")));
		mav.setViewName("timetable/reTimetable/newTimetableGroup.jsp");
		return mav;
	}

	/************************************************************
	 * @二次排课：保存分组信息
	 * @页面跳转：listReTimetable-listReTimetable-doIframeGroupReTimetable-newTimetableGroup-saveTimetableGroup
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/saveTimetableGroup")
	public ModelAndView saveTimetableGroup(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		/*
		 * mav.addObject("courseCodes", schoolCourseDetailService
		 * .getCourseCodeInSchoolCourse(4));
		 */
		mav.addObject("term", request.getParameter("term"));
		mav.addObject("item", request.getParameter("item"));
		timetableReSchedulingService.saveTimetableGroup(request);
		mav.setViewName("redirect:/timetable/doIframeGroupReTimetable?term=" + request.getParameter("term")
				+ "&courseCode=" + request.getParameter("courseCode"));
		return mav;
	}

	/************************************************************
	 * @二次排课：实现二次排课分组进行排课,appointment 排课timetableAppointment的id，-1为新建
	 * @页面跳转：listReTimetable-listReTimetable-doIframeGroupReTimetable-doGroupReTimetable
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/doGroupReTimetable")
	public ModelAndView doGroupReTimetable(HttpServletRequest request, @RequestParam int term, String courseCode,
			int appointment, @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		// 获取去重的实验分室室列表
		mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
		// 如果为新建排课
		if (appointment == -1) {
			mav.addObject("appointment", null);
		} else {
			mav.addObject("appointment", timetableAppointmentDAO.findTimetableAppointmentById(appointment));
		}
		
		// 获取分组对象
		if (request.getParameter("group")!=null&&!request.getParameter("group").equals("")) {
			TimetableGroup timetableGroup = timetableGroupDAO.findTimetableGroupById(Integer.parseInt(request
					.getParameter("group")));
			mav.addObject("group", timetableGroup);
		}else {//针对编辑情况
			if (appointment != -1) {
				Set<TimetableGroup> groups = timetableAppointmentDAO.findTimetableAppointmentById(appointment).getTimetableGroups();
				List<TimetableGroup> groupList = new ArrayList<TimetableGroup>(groups);
				TimetableGroup timetableGroup = groupList.get(0);
				mav.addObject("group", timetableGroup);
			}
		}
		
		// 获取可选的教师列表列表
		mav.addObject("timetableTearcherMap", outerApplicationService.getTimetableTearcherMap());
		// 获取学期列表
		mav.addObject("schoolTerm", schoolTermDAO.findSchoolTermById(term));
		// mav.addObject("week", week);
		mav.addObject("courseCode", courseCode);
		// 根据课程及id获取课程排课列表
		mav.addObject("courseCodes", schoolCourseDetailService.getSchoolCourseDetailByCourseCode(courseCode));
		// 获取可选的实验项目列表列表
		mav.addObject(
				"timetableItemMap",
				outerApplicationService.getTimetableItemMap(schoolCourseDAO.findSchoolCourseByCourseCode(courseCode)
						.iterator().next().getSchoolCourseInfo().getCourseNumber()));

		// 获取登录用户信息
		mav.addObject("user", shareService.getUserDetail());
		// 获取实验分室室排课记录
		mav.addObject("labTimetable", timetableAppointmentService.getReListLabTimetableAppointments(request, acno, term));
		//将排课id映射给jsp
		mav.addObject("tableAppId", appointment);
		mav.setViewName("timetable/reTimetable/doGroupReTimetable.jsp");
		return mav;
	}

	/************************************************************
	 * @二次排课： 删除id对应的批次的所有记录
	 * @页面跳转：listReTimetable-listReTimetable-doIframeGroupReTimetable-deleteBatch
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/deleteBatch")
	public ModelAndView deleteBatch(@RequestParam int id, int term, String courseCode) {
		ModelAndView mav = new ModelAndView();
		// 删除id对应的批次的所有记录
		timetableReSchedulingService.deleteBatch(id, term, courseCode);
		mav.setViewName("../../timetable/doIframeGroupReTimetable?term=" + term + "&courseCode=" + courseCode);
		return mav;
	}

	/************************************************************
	 * @二次排课 确认二次分组排课是否完成--从排课页面进入
	 * @页面跳转：listReTimetable-listReTimetable-doIframeGroupReTimetable-doReNOGroupTimetableOk
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/doReGroupTimetableOk")
	public ModelAndView doReGroupTimetableOk(@RequestParam String courseCode, int batchId, int term) {
		ModelAndView mav = new ModelAndView();
		// 确认二次分组排课是否完成
		timetableReSchedulingService.doReGroupTimetableOk(courseCode, batchId, term);
		mav.setViewName("../../timetable/doIframeGroupReTimetable?term=" + term + "&courseCode=" + courseCode);
		return mav;
	}
	
	/************************************************************
	 * @二次排课 确认二次分组排课是否完成--从排课管理页面进入
	 * @作者：贺子龙
	 * @日期：2016-06-05
	 ************************************************************/
	@RequestMapping("/timetable/doReGroupTimetableOkFromAdmin")
	public ModelAndView doReGroupTimetableOkFromAdmin(@RequestParam String courseCode, int batchId, int term) {
		ModelAndView mav = new ModelAndView();
		// 确认二次分组排课是否完成
		timetableReSchedulingService.doReGroupTimetableOk(courseCode, batchId, term);
		mav.setViewName("redirect:/timetable/timetableAdminIframe?currpage=1&id=-1&status=-1");
		return mav;
	}

	/************************************************************
	 * @二次排课 确认二次分组排课是否完成
	 * @页面跳转：listReTimetable-listReTimetable-doIframeGroupReTimetable-doReNOGroupTimetableOk
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/doOnlyNoGroupTimetableOk")
	public ModelAndView doOnlyNoGroupTimetableOk(@RequestParam String courseCode, int term) {
		ModelAndView mav = new ModelAndView();
		// 根据选课组编号，获取排课信息
		List<TimetableAppointment> timetableAppointments = new ArrayList<TimetableAppointment>(
				timetableAppointmentDAO.findTimetableAppointmentByCourseCode(courseCode));
		for (TimetableAppointment timetableAppointment : timetableAppointments) {
			timetableAppointment.setStatus(2);
			timetableAppointmentDAO.store(timetableAppointment);
		}

		String rtUrl = "redirect:/timetable/doOnlyNoGroupReTimetable?term=" + term + "&flag=0&courseCode=" + courseCode
				+ "&classids=1&weekday=1&labroom=1";
		mav.setViewName(rtUrl);

		// mav.setViewName("redirect:/timetable/listOnlyNoGroupReTimetable?term="
		// + term + "&courseCode=" + courseCode);
		return mav;
	}

	/************************************************************
	 * @二次排课 确认二次分组排课是否完成--从排课页面
	 * @页面跳转：listReTimetable-listReTimetable-doIframeGroupReTimetable-doReNOGroupTimetableOk
	 * @作者：魏诚
	 * @日期：2014-08-25
	 ************************************************************/
	@RequestMapping("/timetable/doNoGroupTimetableOk")
	public ModelAndView doNoGroupTimetableOk(@RequestParam String courseCode, int term) {

		ModelAndView mav = new ModelAndView();
		// 根据选课组编号，获取排课信息
		List<TimetableAppointment> timetableAppointments = new ArrayList<TimetableAppointment>(
				timetableAppointmentDAO.findTimetableAppointmentByCourseCode(courseCode));
		for (TimetableAppointment timetableAppointment : timetableAppointments) {
			timetableAppointment.setStatus(2);
			timetableAppointmentDAO.store(timetableAppointment);
		}

		String rtUrl = "redirect:/timetable/doIframeNoGroupReTimetable?term=" + term + "&flag=0&courseCode="
				+ courseCode + "&classids=1&weekday=1&labroom=1";
		mav.setViewName(rtUrl);
		return mav;
	}
	
	/************************************************************
	 * @二次排课 确认二次分组排课是否完成--从排课管理页面
	 * @作者：贺子龙
	 * @日期：2016-06-05
	 ************************************************************/
	@RequestMapping("/timetable/doNoGroupTimetableOkFromAdmin")
	public ModelAndView doNoGroupTimetableOkFromAdmin(@RequestParam String courseCode, int term) {

		ModelAndView mav = new ModelAndView();
		// 根据选课组编号，获取排课信息
		List<TimetableAppointment> timetableAppointments = new ArrayList<TimetableAppointment>(
				timetableAppointmentDAO.findTimetableAppointmentByCourseCode(courseCode));
		for (TimetableAppointment timetableAppointment : timetableAppointments) {
			timetableAppointment.setStatus(2);
			timetableAppointmentDAO.store(timetableAppointment);
		}

		mav.setViewName("redirect:/timetable/timetableAdminIframe?currpage=1&id=-1&status=-1");
		return mav;
	}

	/************************************************************
	 * @二次排课 确认二次不分组排课是否完成
	 * @页面跳转：listReTimetable-listReTimetable-doIframeNoGroupReTimetable-doReNOGroupTimetableOk
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/doReNOGroupTimetableOk")
	public ModelAndView doReNOGroupTimetableOk(@RequestParam String courseCode) {
		ModelAndView mav = new ModelAndView();
		// 根据选课组编号，获取排课信息
		List<TimetableAppointment> timetableAppointments = new ArrayList<TimetableAppointment>(
				timetableAppointmentDAO.findTimetableAppointmentByCourseCode(courseCode));
		for (TimetableAppointment timetableAppointment : timetableAppointments) {
			timetableAppointment.setStatus(2);
			timetableAppointmentDAO.store(timetableAppointment);
		}
		mav.setViewName("redirect:/timetable/listReTimetable?currpage=1&status=-1");
		return mav;
	}

	/************************************************************
	 * @二次排课 分组排课中的学生选课结果列表
	 * @页面跳转：listReTimetable-listReTimetable-doIframeNoGroupReTimetable-doReNOGroupTimetableOk
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/reTimetable/listGroupTimetableStudentSelected")
	public ModelAndView listGroupTimetableStudentSelected(@ModelAttribute TimetableGroupStudents timetableGroupStudents) {
		ModelAndView mav = new ModelAndView();

		// 根据选课组编号，获取排课信息
		List<TimetableGroupStudents> timetableGroupStudentses = new ArrayList<TimetableGroupStudents>(
				timetableGroupStudentsDAO
						.executeQuery("select c from TimetableGroupStudents c where c.timetableGroup.timetableAppointment.status=1 and c.user.username like '"
								+ shareService.getUserDetail().getUsername() + "'"));

		mav.addObject("timetableGroupStudentses", timetableGroupStudentses);
		mav.setViewName("timetable/reTimetable/listGroupTimetableStudentSelected.jsp");
		return mav;
	}

	/************************************************************
	 * @二次排课 分组排课中的可选的学生选课列表
	 * @页面跳转：listReTimetable-listReTimetable-doIframeNoGroupReTimetable-doReNOGroupTimetableOk
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/reTimetable/listGroupTimetableStudentSelect")
	public ModelAndView listGroupTimetableStudentSelect(@ModelAttribute TimetableGroup timetableGroup) {
		ModelAndView mav = new ModelAndView();
		// 获取学期列表
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerm", schoolTerms);

		// 获取分组排课信息，非登陆学生的用户，从list中去掉
		mav.addObject("timetableGroups", timetableReSchedulingService.getTimetableStudentSelect());
		List<TimetableGroupStudents> timetableGroupStudents = timetableGroupStudentsDAO
				.executeQuery("select c from TimetableGroupStudents c where c.user.username like '"
						+ shareService.getUserDetail().getUsername() + "'");
		mav.addObject("timetableGroupStudents", timetableGroupStudents);
		mav.addObject("user", shareService.getUserDetail());
		mav.addObject("totalRecords", timetableReSchedulingService.getTimetableStudentSelect().size());
		mav.setViewName("timetable/reTimetable/listGroupTimetableStudentSelect.jsp");
		return mav;
	}

	/************************************************************
	 * @二次排课 进行分组排课中的可选的学生选课列表
	 * @页面跳转：listReTimetable-listReTimetable-doIframeNoGroupReTimetable-doReNOGroupTimetableOk
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/reTimetable/doGroupTimetableStudentSelect")
	public ModelAndView doGroupTimetableStudentSelect(@RequestParam int id) {
		ModelAndView mav = new ModelAndView();
		// 根据选课组编号，获取排课信息
		List<TimetableGroup> timetableGroups = new ArrayList<TimetableGroup>(
				timetableGroupDAO.executeQuery("select c from TimetableGroup c where c.timetableBatch.id= " + id));
		List<TimetableGroupStudents> timetableGroupStudentses = new ArrayList<TimetableGroupStudents>(
				timetableGroupStudentsDAO
						.executeQuery("select c from TimetableGroupStudents c where c.timetableGroup.timetableBatch.id= "
								+ id + " and c.user.username like '" + shareService.getUserDetail().getUsername() + "'"));

		if (timetableGroupStudentses.size() > 0) {
			mav.addObject("ifselect", 1);
		} else {
			mav.addObject("ifselect", 0);
		}

		mav.addObject("timetableGroups", timetableGroups);
		mav.addObject("user", shareService.getUserDetail());
		mav.setViewName("timetable/reTimetable/doGroupTimetableStudentSelect.jsp");
		return mav;
	}

	/************************************************************
	 * @二次排课 进行分组排课中的可选的学生选课列表
	 * @页面跳转：listReTimetable-listReTimetable-doIframeNoGroupReTimetable-doReNOGroupTimetableOk
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/reTimetable/doStudentSelectGroup")
	public ModelAndView doStudentSelectGroup(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		// 根据选课组编号，获取排课信息
		int group = Integer.parseInt(request.getParameter("group"));
		TimetableGroupStudents timetableGroupStudents = new TimetableGroupStudents();
		timetableGroupStudents.setTimetableGroup(timetableGroupDAO.findTimetableGroupById(group));

		timetableGroupStudents.setUser(shareService.getUserDetail());
		//判断是否已经选课满，如果不满则可以插入
		if(timetableGroupStudents.getTimetableGroup().getNumbers()>timetableGroupStudents.getTimetableGroup().getTimetableGroupStudentses().size()){
			/*//如果没有相同组相同用户的选课，则可以增加
			List<TimetableGroupStudents> timetableGroupStudentsList=  timetableGroupStudentsDAO.executeQuery("select u from TimetableGroupStudents u where u.timetableGroup.id="+timetableGroupStudents.getTimetableGroup().getId() +" and u.user.username like '" + timetableGroupStudents.getUser().getUsername() + "'");
			if(timetableGroupStudentsList.size()==0){*/
				timetableGroupStudentsDAO.store(timetableGroupStudents);
			    timetableGroupStudentsDAO.flush();
     		/*}*/
			
		}
		mav.setViewName("../../timetable/reTimetable/doGroupTimetableStudentSelect?id="
				+ timetableGroupDAO.findTimetableGroupById(group).getTimetableBatch().getId());
		return mav;
	}
	
	/************************************************************
	 * @二次排课 进行分组排课中的可选的学生选课退选
	 * @作者：魏诚
	 * @日期：2015-03-6
	 ************************************************************/
	@RequestMapping("/timetable/reTimetable/ejectStudentSelectGroup")
	public ModelAndView ejectStudentSelectGroup(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		// 根据选课组编号，获取排课信息
		int group = Integer.parseInt(request.getParameter("group"));
		List<TimetableGroupStudents> timetableGroupStudentsList=  timetableGroupStudentsDAO.executeQuery("select u from TimetableGroupStudents u where u.timetableGroup.timetableBatch.id="+group +" and u.user.username like '" + shareService.getUserDetail().getUsername() + "'");
		
		timetableGroupStudentsDAO.remove(timetableGroupStudentsList.get(0));

		timetableGroupStudentsDAO.flush();
		mav.setViewName("../../timetable/reTimetable/listGroupTimetableStudentSelect");
		return mav;
		
	}
	/************************************************************
	 * @获得当前课表
	 * @作者：戴昊宇
	 * @日期：2018-04-18
	 ************************************************************/
	@RequestMapping("/timetable/currentCalendar")
	public ModelAndView currentCalendar(int roomId) {
		ModelAndView mav = new ModelAndView();
		//获取当前时间
		Calendar calendar = Calendar.getInstance();
		//获取当前学期周次星期
		SchoolTerm term = shareService.getBelongsSchoolTerm(calendar);
		int week = shareService.getBelongsSchoolWeek(calendar);
		int weekday = shareService.getBelongsSchoolWeekday(calendar);
		int belongsClass = shareService.getBelongsClass(calendar);
		// 获取实验分室室排课记录
		mav.addObject("labTimetable", teachingReportService.getCurrentLabTimetableAppointments(term.getId(), roomId, week, weekday, belongsClass));
		mav.addObject("labSelfTimetable", 
				teachingReportService.getCurrentSelfLabTimetableAppointments(term.getId(), roomId, week, weekday, belongsClass));
		mav.setViewName("timetable/reTimetable/listGeneralTimetable.jsp");
		return mav;
	}

	/**
	 * Description 课表查询直播查看
	 * @author 黄保钱 2018-11-21
	 */
	@RequestMapping("/timetable/playTimetableLive")
	public ModelAndView playTimetableLive(HttpServletRequest request) throws UnsupportedEncodingException {
		ModelAndView mav = new ModelAndView();
		String livePath = request.getParameter("livePath");
		String name = request.getParameter("name");
		if(name.equals(new String(name.getBytes("iso8859-1"), "iso8859-1"))){
			name = new String(name.getBytes("iso8859-1"), "utf-8");
		}
		mav.addObject("livePath", livePath);
		mav.addObject("name", name);
		mav.setViewName("timetable/reTimetable/timetableLiveVideo.jsp");
		return mav;
	}
}