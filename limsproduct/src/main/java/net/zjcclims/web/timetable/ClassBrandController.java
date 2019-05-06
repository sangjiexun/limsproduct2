package net.zjcclims.web.timetable;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.zjcclims.dao.LabRoomDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.report.TeachingReportService;
import net.zjcclims.service.system.SchoolWeekService;
import net.zjcclims.service.system.SystemService;
import net.zjcclims.service.timetable.OuterApplicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;


@Controller("ClassBrandController")
@SessionAttributes({"selected_labCenter"})
@RequestMapping("/classBrand")
public class ClassBrandController<JsonResult> {
	
	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register
																				// static
																				// property
																				// editors.
		binder.registerCustomEditor(Calendar.class, new org.skyway.spring.util.databinding.CustomCalendarEditor());
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
	@Autowired
	private OuterApplicationService outerApplicationService;
	@Autowired
	ShareService shareService;
	@Autowired
	LabRoomDAO labRoomDAO;
	@Autowired
	TeachingReportService teachingReportService;
	@Autowired
	SystemService systemService;
	@Autowired
	SchoolWeekService schoolWeekService;
	@Autowired
	private LabRoomService labRoomService;
	@PersistenceContext
	private EntityManager entityManager;
	
	/************************************************************
	 * @实验室周课表
	 * @作者：戴昊宇
	 * @日期：2017-12-7
	 ************************************************************/
	@RequestMapping(value="/weekCalendar",produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	public ModelAndView weekCalendar(HttpServletRequest request,int roomId) {
		ModelAndView mav = new ModelAndView();
		/*-------用作查询------------*/
		mav.addObject("courseCode", request.getParameter("courseCode"));
		if(request.getParameter("labId")!=null){
			roomId=Integer.parseInt(request.getParameter("labId"));
		}
		// 获得学期
		Integer cid=-1;
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		// 获取学期列表用于查询
		List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
		mav.addObject("schoolTerm", schoolTerms);
		// 获取课程列表
		mav.addObject("schoolCourses", teachingReportService.getSchoolCourseLists(term));
		mav.addObject("term", term);
		//获取去重的实验分室室列表
        mav.addObject("labRoomMap", outerApplicationService.getLabRoomNumberList(null));
		
		/*-----------------------------*/
		// 获取实验室
		/*if (!EmptyUtil.isStringEmpty(request.getParameter("roomId"))) {
			roomId = Integer.parseInt(request.getParameter("roomId"));
		}*/
		mav.addObject("labRoom", labRoomDAO.findLabRoomById(roomId));
		mav.addObject("roomId", roomId);
		// 学院编号
		String academy = "-1";
		// 实验中心
		if (!EmptyUtil.isObjectEmpty(labRoomDAO.findLabRoomById(roomId))) {
			cid = labRoomDAO.findLabRoomById(roomId).getLabCenter().getId();
			academy = labRoomDAO.findLabRoomById(roomId).getLabCenter().getSchoolAcademy().getAcademyNumber();
		}
		mav.addObject("cid", cid);
		//保存的实验中心cid
		// 获取周
		int week = shareService.getBelongsSchoolWeek(Calendar.getInstance()); 
		if(!EmptyUtil.isStringEmpty(request.getParameter("week"))){
			week = Integer.parseInt(request.getParameter("week"));
		}
		mav.addObject("week", week);
		String teacher = "";
		//获取当前学期
		int termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		// 获取所有的学期
		mav.addObject("termId", termId);  //当前学期
		mav.addObject("weeksMap", teachingReportService.getWeekMap(termId));  //周次下拉框数据
		// 获取实验分室室排课记录
		mav.addObject("labTimetable", teachingReportService.getLabTimetableAppointments(request,termId, roomId, week,teacher, null));
		mav.addObject("labSelfTimetable", teachingReportService.getSelfLabAppointments(termId, roomId, week));
		//获取所有借此对应的时间
		mav.addObject("systemtime", systemService.getAllTimebyjieci());
		mav.addObject("schoolweeek", schoolWeekService.getMapDate());
		mav.setViewName("public/weekCalendar.jsp");
		return mav;
	}
	
	
	/************************************************************
	 * @实验室日课表
	 * @作者：戴昊宇
	 * @日期：2017-12-7
	 ************************************************************/
	@RequestMapping(value="/weekdayCalendar",produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	public ModelAndView weekdayCalendar(HttpServletRequest request,int roomId) {
		ModelAndView mav = new ModelAndView();
		/*-------用作查询------------*/
		mav.addObject("courseCode", request.getParameter("courseCode"));
		// 获取登录用户信息
		/*mav.addObject("user", shareService.getUserDetail());*/
		// 获得学期
		Integer cid=-1;
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		// 获取学期列表用于查询
		List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
		mav.addObject("schoolTerm", schoolTerms);
		// 获取课程列表
		mav.addObject("schoolCourses", teachingReportService.getSchoolCourseLists(term));
		mav.addObject("term", term);
		
		/*-----------------------------*/
		// 获取实验室
		/*if (!EmptyUtil.isStringEmpty(request.getParameter("roomId"))) {
			roomId = Integer.parseInt(request.getParameter("roomId"));
		}*/
		mav.addObject("labRoom", labRoomDAO.findLabRoomById(roomId));
		mav.addObject("roomId", roomId);
		// 实验中心
		if (!EmptyUtil.isObjectEmpty(labRoomDAO.findLabRoomById(roomId))) {
			cid = labRoomDAO.findLabRoomById(roomId).getLabCenter().getId();
		}
		mav.addObject("cid", cid);
		//保存的实验中心cid
		// 获取周
		int week = shareService.getBelongsSchoolWeek(Calendar.getInstance()); 
		if(!EmptyUtil.isStringEmpty(request.getParameter("week"))){
			week = Integer.parseInt(request.getParameter("week"));
		}
		mav.addObject("week", week);
		//获得当前星期
		Calendar c = Calendar.getInstance();
		int weekday =c.get(Calendar.DAY_OF_WEEK)-1;
		if(weekday==0){
			weekday=7;
		}
		//获取当前学期
		int termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		// 获取所有的学期
		mav.addObject("termId", termId);  //当前学期
		// 获取实验分室室排课记录
		List<TimetableLabRelated> appointmentsbyweekday = teachingReportService.getLabAppointmentsByWeekday(termId, cid,roomId, week,weekday);
		mav.addObject("labTimetable", appointmentsbyweekday);
		//获得上课班级
		String className="";
		for(TimetableLabRelated ta:appointmentsbyweekday){
			if(ta.getTimetableAppointment().getTimetableStyle()==1||ta.getTimetableAppointment().getTimetableStyle()==2||
					ta.getTimetableAppointment().getTimetableStyle()==3||ta.getTimetableAppointment().getTimetableStyle()==4){
				Set<SchoolCourseStudent> schoolCourseStudents = ta.getTimetableAppointment().getSchoolCourseDetail().getSchoolCourseStudents();
				for(SchoolCourseStudent ss:schoolCourseStudents){
					className=ss.getSchoolClasses().getClassName();
				}
			}else if(ta.getTimetableAppointment().getTimetableStyle()!=7){
				Set<TimetableCourseStudent> timetableCourseStudents = ta.getTimetableAppointment().getTimetableSelfCourse().getTimetableCourseStudents();
				for(TimetableCourseStudent ss:timetableCourseStudents){
					className="实验室开放";
				}
			}else {
				className="预约使用";
			}
		}
		mav.addObject("className", className);
		//获得上课人数
//		int studentNumber=0;
//		for(TimetableLabRelated ta:appointmentsbyweekday){
//			if(ta.getTimetableAppointment().getTimetableStyle()==1||ta.getTimetableAppointment().getTimetableStyle()==2||
//					ta.getTimetableAppointment().getTimetableStyle()==3||ta.getTimetableAppointment().getTimetableStyle()==4){
//				Set<SchoolCourseStudent> schoolCourseStudents = ta.getTimetableAppointment().getSchoolCourseDetail().getSchoolCourseStudents();
//				for(SchoolCourseStudent ss:schoolCourseStudents){
//					if(ss.getState()==1){
//						studentNumber++;
//					}
//				}
//			}else{
//				Set<TimetableCourseStudent> timetableCourseStudents = ta.getTimetableAppointment().getTimetableSelfCourse().getTimetableCourseStudents();
//				for(TimetableCourseStudent ss:timetableCourseStudents){
//					studentNumber++;
//				}
//			}
//		}
//		mav.addObject("studentNumber", studentNumber);
		//mav.addObject("labSelfTimetable", teachingReportService.getSelfLabTimetableAppointmentsbyweekday(termId, cid, roomId, week, weekday));
		//获取所有借此对应的时间
		mav.addObject("systemtime", systemService.getAllTimebyjieci());
		mav.addObject("schoolweeek", schoolWeekService.getMapDate());
		mav.setViewName("public/weekdayCalendar.jsp");
		return mav;
	}
	
	/**
	 * Description 班牌方法{获取实验室基本信息}
	 * @param id
	 * @return
	 * @author 陈乐为 2018-4-9
	 */
	@RequestMapping("/findLabRoomDetail")
	public @ResponseBody
	String findLabRoomDetail(@RequestParam int id) {
		LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(id);
		String str = "";
		// 实验室编号+名称
		str += labRoom.getLabRoomNumber() + " " + labRoom.getLabRoomName() + ",";
		// 实验室管理员+联系电话
		if(labRoom.getLabRoomAdmins()!=null && labRoom.getLabRoomAdmins().size()>0) {
			for(LabRoomAdmin admin : labRoom.getLabRoomAdmins()) {
				if(admin.getTypeId()==1) {// 实验室管理员
					str += "管理员:"+admin.getUser().getCname() + ",";
					str += "联系电话:"+admin.getUser().getTelephone() + ",";
					break;
				}
			}
		}else {
			str += ",";
			str += ",";
		}
		// 实验室简介
		str += "简介:"+labRoom.getLabRoomIntroduction() + ",";
		
		return shareService.htmlEncode(str);
	}
	
	/**
	 * Description 班牌方法{获取实验室基本信息}
	 * @param id
	 * @return
	 * @author 陈乐为 2018-4-9
	 */
	@RequestMapping("/getLabRoomDetail")
	public ModelAndView getLabRoomDetail(@RequestParam int id) {
		ModelAndView mav = new ModelAndView();
		LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(id);
		
		// 实验室编号
		mav.addObject("roomNumber", labRoom.getLabRoomNumber());
		// 名称
		mav.addObject("roomName", labRoom.getLabRoomName());
		// 实验室管理员、联系电话
		if(labRoom.getLabRoomAdmins()!=null && labRoom.getLabRoomAdmins().size()>0) {
			for(LabRoomAdmin admin : labRoom.getLabRoomAdmins()) {
				if(admin.getTypeId()==1) {
					mav.addObject("cname", admin.getUser().getCname());
					mav.addObject("telephone", admin.getUser().getTelephone());
					break;
				}
			}
		}
		// 实验室简介
		mav.addObject("intro", labRoom.getLabRoomIntroduction());
		
		mav.setViewName("public/labRoomDetail.jsp");
		return mav;
	}
	
	/************************************************************
	 * @实验室日考勤
	 * @作者：叶明盾
	 * @日期：2018-05-13
	 ************************************************************/
	@RequestMapping(value="/weekdayAttendance",produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	public ModelAndView weekdayAttendance(HttpServletRequest request,int roomId) {
		ModelAndView mav = new ModelAndView();
		String sql = "select * from view_attendance_today_board v where v.labroomID = " + roomId + " order by v.start_class";
		Query query = entityManager.createNativeQuery(sql);
		mav.addObject("list",query.getResultList());
		mav.setViewName("public/weekdayAttendance.jsp");
		return mav;
	}
	
	
}
