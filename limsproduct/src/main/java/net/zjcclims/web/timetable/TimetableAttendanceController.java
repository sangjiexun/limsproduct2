package net.zjcclims.web.timetable;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import net.zjcclims.common.LabAttendance;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.service.cmsshow.CMSShowService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabAnnexService;
import net.zjcclims.service.timetable.OuterApplicationService;
import net.zjcclims.service.timetable.SchoolCourseDetailService;
import net.zjcclims.service.timetable.TimetableAppointmentService;
import net.zjcclims.service.timetable.TimetableAttendanceService;
import net.zjcclims.dao.LabRoomAgentDAO;
import net.zjcclims.dao.SchoolCourseDAO;
import net.zjcclims.dao.TimetableAppointmentDAO;
import net.zjcclims.dao.TimetableAttendanceDAO;
import net.zjcclims.domain.CommonHdwlog;
import net.zjcclims.domain.LabAnnex;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.LabRoomAgent;
import net.zjcclims.domain.SchoolCourse;
import net.zjcclims.domain.SchoolCourseStudent;
import net.zjcclims.domain.SchoolTerm;
import net.zjcclims.domain.TimetableAppointment;
import net.zjcclims.domain.TimetableAppointmentSameNumber;
import net.zjcclims.domain.TimetableAttendance;
import net.zjcclims.domain.User;

import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import excelTools.Attendance;
import excelTools.People;

@Controller("TimetableAttendanceController")
@SessionAttributes("selected_academy")
public class TimetableAttendanceController {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private TimetableAttendanceDAO timetableAttendanceDAO;
	@Autowired
	private TimetableAttendanceService timetableAttendanceService;

	@Autowired
	private SchoolCourseDetailService schoolCourseDetailService;
	@Autowired
	private TimetableAppointmentDAO timetableAppointmentDAO;
	@Autowired
	private TimetableAppointmentService timetableAppointmentService;
	@Autowired
	private ShareService shareService;
	@Autowired
	private LabAnnexService labAnnexService;
	@Autowired
	private CMSShowService cmsShowService;
	@Autowired
	private OuterApplicationService outerApplicationService;
	@Autowired
	private SchoolCourseDAO schoolCourseDAO;
	@Autowired
	private LabRoomAgentDAO labRoomAgentDAO;
	@Autowired
	PConfig pConfig;

	/**
	 * Register custom, context-specific property editors
	 * 
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register
																				// static
																				// property
																				// editors.
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

	/************************************************************
	 * @考情管理模块保存方法
	 * @作者：喻泉声
	 * @日期：2014-08-9
	 ************************************************************/
	@RequestMapping("/timetable/saveTimetableAttendance")
	public String saveTimetableAttendance(@ModelAttribute TimetableAttendance timetableattendance) {

		TimetableAttendance s = timetableAttendanceDAO.findTimetableAttendanceById(timetableattendance.getId());
		s.setActualAttendance(timetableattendance.getActualAttendance());
		s.setMemo(timetableattendance.getMemo());
		TimetableAttendance att=timetableAttendanceDAO.store(s);
		return "redirect:/timetable/operatStudentAttendance?id=" + att.getTimetableAppointment().getId()+ "&week=" + att.getWeek();
       
	}
	/************************************************************
	 * @考情管理模块入口
	 * @作者：喻泉声
	 * @日期：2014-08-4
	 ************************************************************/
	@RequestMapping("/timetable/attendence")
	public ModelAndView attendenceIframe() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/timetable/attendence/listAttendanceManage.jsp");
		return mav;
	}

	/************************************************************
	 * @throws ParseException
	 * @考勤管理模块
	 * @作者：喻泉声
	 * @日期：2014-08-4
	 ************************************************************/
	@RequestMapping("/timetable/attendanceManageIframe")
	public ModelAndView attendanceManageIframe(HttpServletRequest request,
			@ModelAttribute TimetableAppointment timetableAppointment, @RequestParam int currpage,@ModelAttribute("selected_academy") String acno)
			throws ParseException {
//		if(cid==null){
//		    cid=-1;
//		}

		ModelAndView mav = new ModelAndView();
		// 获取学期列表
		List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
		mav.addObject("schoolTerms", schoolTerms);
		// 当前时间的学期
		//int term = shareService.findNewTerm().getId();获取当前时段所处学期或下个新学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		
		mav.addObject("term", term);
		// 判断是否标记位为空，如果为空，则清空schoolCourseDetail
		if (request.getParameter("id") != null && request.getParameter("id").equals("-1")) {
			
		}
		int totalRecords = timetableAppointmentService.getCountTimetableAppointmentsByQuery(term,timetableAppointment,1,acno);
		mav.addObject("totalRecords", totalRecords);
		// 设置分页变量并赋值为20；
		int pageSize = CommonConstantInterface.INT_PAGESIZE;
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		mav.addObject("page", currpage);
		mav.addObject("totalRecords", totalRecords);
		// 获取所有的学期
		List<SchoolTerm> terms = schoolCourseDetailService.getSchoolTermsByNow();
		mav.addObject("terms", terms);
		mav.addObject("course", new SchoolCourse());
		// 根据课程及id获取课程排课列表
		mav.addObject("timetableAppointments", timetableAppointmentService.getTimetableAppointmentsByQuery(term,timetableAppointment,1,currpage -1, pageSize,acno));
		mav.addObject("timetableAppointmentAll",timetableAppointmentService.getTimetableAppointmentsByQuery(term,timetableAppointment,1, 0, -1,acno));

		// 考勤机考勤遍历添加
		timetableAttendanceService.saveMachineAttendance(timetableAttendanceService.getTimetableAppointmentsByQuery(timetableAppointment, 1,acno, currpage - 1, pageSize));
		
		mav.addObject("teachers", timetableAppointmentService.getAllTimetableRelatedTeachers());
		mav.setViewName("timetable/attendence/listAttendanceManageIframe.jsp");
		return mav;
	}
	
	
	/************************************************************
	 * @throws ParseException
	 * @考勤管理模块--实验室考勤
	 * @作者：贺子龙
	 * @日期：2015-11-25
	 ************************************************************/
	@RequestMapping("/timetable/attendanceManageForLabIframe")
	public ModelAndView attendanceManageForLabIframe(@ModelAttribute LabAttendance labAttendance, @RequestParam int currpage,@ModelAttribute("selected_academy") String acno)
			throws ParseException {
//		if(cid==null){
//		    cid=-1;
//		}
		String username="";
		if (labAttendance.getUsername() != null) {
			username = labAttendance.getUsername();
		}
		String cname="";
		if (labAttendance.getCname() != null) {
			cname = labAttendance.getCname();
		}
		String labName="";
		if (labAttendance.getLabRoomName() != null) {
			labName = labAttendance.getLabRoomName();
		}
		ModelAndView mav = new ModelAndView();
		int pageSize = 10;
		List<LabAttendance> labRoomAttendList=timetableAttendanceService.findLabAttendance( username,  cname, labName,acno,currpage,pageSize);
		mav.addObject("labRoomAttendList", labRoomAttendList);
		int totalRecords = timetableAttendanceService.findLabAttendance(username,  cname, labName,acno);
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		mav.addObject("page", currpage);
		mav.addObject("totalRecords", totalRecords);
		mav.setViewName("timetable/attendence/listAttendanceManageForLabIframe.jsp");
		return mav;
	}
	
	
	/*************************************************************************************
	 * @內容：cms中 开放实验室资源
	 * @作者： 李小龙
	 * @日期：2014-12-2 
	 *************************************************************************************/
	@RequestMapping("/timetable/labRoomResource")
	public ModelAndView labRoomResource(@ModelAttribute LabRoom labRoom ,@RequestParam int page,@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		//获取当前用户
		User user=shareService.getUser();
		mav.addObject("user", user);
		//查询表单的对象
		mav.addObject("labRoom", labRoom);
		// 设置分页变量并赋值为20
		int pageSize = 15;
		//查询出来的总记录条数
		int totalRecords = cmsShowService.findLabRoomBySchoolAcademyDefault(labRoom,1,-1,1,acno).size();
		Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
		//当前中心下的实验室
		List<LabAnnex> labAnnexs=labAnnexService.findAllLabAnnexBySchoolAcademy(acno);
		mav.addObject("labAnnexs", labAnnexs);
		//页面显示的实验室
		List<LabRoom> labRoomList=cmsShowService.findLabRoomBySchoolAcademyDefault(labRoom,page,pageSize,1,acno);//考勤机--1
		mav.addObject("listLabRooms",cmsShowService.findLabRoomBySchoolAcademyDefault(new LabRoom(),1,-1,1,acno));
		mav.addObject("labRoomList",labRoomList);
		mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", page);
		mav.addObject("pageSize", pageSize);
		mav.setViewName("timetable/attendence/listAttendanceManageForLab.jsp");
		return mav;
	}
	/*************************************************************************************
	 * @內容：开放实验室资源--考勤刷卡机
	 * @作者： 李小龙
	 * @日期：2014-12-2 
	 *************************************************************************************/
	@RequestMapping("/timetable/Attendance")
	public ModelAndView Attendance(@RequestParam Integer id,Integer page,@ModelAttribute CommonHdwlog commonHdwlog,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		mav.addObject("starttime", starttime);
		mav.addObject("endtime", endtime);
		mav.addObject("id", id);
		//id对应的物联设备
		LabRoomAgent agent=labRoomAgentDAO.findLabRoomAgentByPrimaryKey(id);
		String ip=agent.getHardwareIp();
		String port=agent.getManufactor();
		// 设置分页变量并赋值为20
		//int pageSize = CommonConstantInterface.INT_PAGESIZE;
		int pageSize = 30;
		int totalRecords = 0;
		Map<String, Integer> pageModel = null;
		List<LabAttendance> accessList =null;
		//查询出来的总记录条数
		// 根据配置项是否切换获取对应的数据
		if(pConfig.newServer.equals("false")){
			// 老版获取考勤数据
			totalRecords = cmsShowService.findLabRoomAccessByIpCount(commonHdwlog,ip,port,request);
			pageModel = shareService.getPage(page, pageSize, totalRecords);
			//页面显示的实验室
			accessList=cmsShowService.findLabRoomAccessByIp(commonHdwlog,ip,port,page,pageSize,request);
		}else {
			// 新版从iot获取数据
			totalRecords = cmsShowService.findIotAttendanceByIpCount(commonHdwlog,ip,request,page,pageSize);
			pageModel = shareService.getPage(page, pageSize, totalRecords);
			//页面显示的实验室
			accessList=cmsShowService.findIotAttendanceByIp(commonHdwlog,ip,request,page,pageSize);
		}
		mav.addObject("accessList",accessList);
		mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", page);
		mav.addObject("pageSize", pageSize);
		mav.setViewName("timetable/attendence/listLabRoomAttendance.jsp");
		return mav;
	}

	/************************************************************
	 * @考勤细节模块入口
	 * @作者：喻泉声
	 * @日期：2014-08-4
	 ************************************************************/

	@RequestMapping("/timetable/detailattendanceManage")
	public ModelAndView detailattendanceManage(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		// id对应的对象
		TimetableAppointment t = timetableAppointmentDAO.findTimetableAppointmentByPrimaryKey(idKey);
		mav.addObject("t", t);
		List<Integer> list = new ArrayList<Integer>();
		//当没有外键关联时，从timetable_appointment表里读取数据
		if(t.getTimetableAppointmentSameNumbers().size()==0){
			for (int i = t.getStartWeek(); i<= t.getEndWeek();i++) {
				list.add(i);
	 	   	}
		}
		if(t.getTimetableAppointmentSameNumbers().size()>0){
			Set<TimetableAppointmentSameNumber> same=t.getTimetableAppointmentSameNumbers();
			for (TimetableAppointmentSameNumber s : same) {
				for (int i = s.getStartWeek(); i<=s.getEndWeek();i++) {
					list.add(i);
		 	   	}
			}
		}
		mav.addObject("list", list);
		mav.setViewName("timetable/attendence/attendanceShow.jsp");

		return mav;
	}

	
	
	/************************************************************
	 * 考勤管理-操作学生考勤 作者：徐龙帅 日期：2014-12-11
	 ************************************************************/
	@RequestMapping("/timetable/operatStudentAttendance")
	public ModelAndView operatStudentAttendance(@RequestParam  Integer id,Integer week) {

		ModelAndView mav = new ModelAndView();
 
		TimetableAppointment t = timetableAppointmentDAO.findTimetableAppointmentByPrimaryKey(id);
		Set<User> students=timetableAttendanceService.findStudentsByTimetableAppointment(t);
		boolean timeMark=shareService.getTimeMark(week,t.getWeekday());
		mav.addObject("timeMark",timeMark);	
		mav.addObject("studentMap",students);	
		List<TimetableAttendance> attendancetableByWeek=timetableAttendanceService.showStudentAttendanceByWeek( t, week);
		
		mav.addObject("attendancetableByWeek",attendancetableByWeek);
		mav.addObject("id", id);
		mav.addObject("week", week);
		mav.setViewName("timetable/attendence/operatStudentAttendance.jsp");
		return mav;
	}
	
	/************************************************************
	 * 手动考勤
	 * 李小龙
	 ************************************************************/
	@RequestMapping("/timetable/saveManualAttendance")
	public ModelAndView saveManualAttendance(@RequestParam Integer id) {
		ModelAndView mav = new ModelAndView();
		//id对应的考勤
		TimetableAttendance att=timetableAttendanceDAO.findTimetableAttendanceByPrimaryKey(id);
		//手动考勤
		att.setActualAttendance(1);
		
		// 保存学生考勤数据
		TimetableAttendance a=timetableAttendanceDAO.store(att) ;
		mav.setViewName("redirect:/timetable/operatStudentAttendance?id=" + a.getTimetableAppointment().getId() + "&week="+ a.getWeek() );
        return mav;
	}

	/************************************************************
	 * 修改考勤的方法-操作学生考勤 
	 * 作者：喻泉声 日期：2014-08-7
	 ************************************************************/
	@RequestMapping("/timetable/editStudentAttendance")
	public ModelAndView editStudentAttendance(@RequestParam Integer id) {

		ModelAndView mav = new ModelAndView();

		TimetableAttendance timetableAttendance = timetableAttendanceDAO.findTimetableAttendanceByPrimaryKey(id);
		mav.addObject("timetableAttendance", timetableAttendance);
		mav.setViewName("timetable/attendence/editStudentAttendance.jsp");
		return mav;
	}

	/************************************************************
	 * 查看考勤的方法-操作学生考勤 作者：喻泉声 日期：2014-08-10
	 ************************************************************/
	@RequestMapping("/timetable/checkStudentAttendance")
	public ModelAndView checkStudentAttendance(@RequestParam Integer id) {
		ModelAndView mav = new ModelAndView();
		
		TimetableAppointment t = timetableAppointmentDAO.findTimetableAppointmentByPrimaryKey(id);
		
		mav.addObject("t", t);
		//根据课程编号查询学生名单
		//Set<User> students=timetableAttendanceService.findStudentsByTimetableAppointment(t);
		// 查出对应课程的学生和总人数
		List<CheckAttendancetable> s=timetableAttendanceService.findStudentsByCourseCode(t);
			
		mav.addObject("checkTotalAttendance",s);
		//该学期的周次
		int weeks=shareService.getTermNumber(t);
		mav.addObject("weeks",weeks);
		
		mav.setViewName("timetable/attendence/checkStudentAttendance.jsp");
		return mav;
	}

	/************************************************************
	 *快速考勤
	 * 喻泉声
	 * 2014-08-4
	 ************************************************************/
	@RequestMapping("/timetable/quickAttendance")
	public ModelAndView quickAttendance(HttpServletRequest request, @RequestParam Integer id, Integer idKey,String courseDetailNo, Integer flag) {
		ModelAndView mav = new ModelAndView();
		String alls = request.getParameter("alls");
		if (alls != "") {
			String[] alls1 = null;
			alls1 = alls.split(",");

			for (int i = 0; i <= (alls1.length - 1); i++) {
				TimetableAttendance timetableAttendance = timetableAttendanceDAO.findTimetableAttendanceByPrimaryKey(Integer.parseInt(alls1[i]));
				timetableAttendance.setAttendDate(Calendar.getInstance());
				timetableAttendance.setActualAttendance(1);

				timetableAttendanceDAO.store(timetableAttendance);
				timetableAttendanceDAO.flush();
			}
		}
		mav.setViewName("redirect:/timetable/operatStudentAttendance?id=" + id + "&week="+ idKey );
       return mav;
	}
	/************************************************************
	 * @我的考勤页面跳转
	 * @作者：徐龙帅
	 * @日期：2014-12-4
	 ************************************************************/
	@RequestMapping("/timetable/myAttendance")
	public ModelAndView myAttendance(@ModelAttribute TimetableAppointment timetableAppointment, @RequestParam int page){
		//新建ModelAndView对象；
			ModelAndView mav=new ModelAndView();
			//查询表单的对象
			mav.addObject("timetableAppointment", timetableAppointment);
			int pageSize=20;//每页20条记录
			//查询出来的总记录条数
			List<TimetableAppointment> listTimetableAppointments=timetableAttendanceService.findTimetableAttendance(timetableAppointment);
			int totalRecords=0;
			if(listTimetableAppointments!=null)
				
			totalRecords=listTimetableAppointments.size();
			//分页信息
			Map<String,Integer> pageModel =shareService.getPage(page, pageSize,totalRecords);
			//根据分页信息查询出来的记录
			List<TimetableAppointment> listTimetableAppointment=timetableAttendanceService.findTimetableAttendance(timetableAppointment,page,pageSize);
			mav.addObject("listTimetableAppointment",listTimetableAppointment);
			// 获取学期列表
			List<SchoolTerm> schoolTerms = outerApplicationService
					.getSchoolTermList();
			
			mav.addObject("schoolTerm", schoolTerms);
			//mav.addObject("user",timetableAttendanceService.findUser());
			mav.addObject("user", shareService.getUserDetail());
			mav.addObject("pageModel",pageModel);
			mav.addObject("totalRecords", totalRecords);
			mav.addObject("page", page);
			mav.addObject("pageSize", pageSize);
		mav.setViewName("timetable/attendence/myAttendance.jsp");
		return mav;
		
	}
	/************************************************************
	 * 保存成绩
	 * 李小龙
	 ************************************************************/
	@RequestMapping("/timetable/saveAttendanceScore")
	public ModelAndView saveAttendanceScore(@RequestParam Integer id, String score) {

		ModelAndView mav = new ModelAndView();
		//保存成绩
		TimetableAttendance attendance=timetableAttendanceDAO.findTimetableAttendanceByPrimaryKey(id);
		BigDecimal decimal=new BigDecimal(score);
		attendance.setScore(decimal);
		TimetableAttendance att=timetableAttendanceDAO.store(attendance);
		mav.setViewName("redirect:/timetable/operatStudentAttendance?id=" + att.getTimetableAppointment().getId() + "&week="+ att.getWeek() );
        return mav;
	}
	/************************************************************
	 * 学期成绩查看
	 ************************************************************/
	@RequestMapping("/timetable/checkTearmAttendance")
	public ModelAndView checkTearmAttendance(@RequestParam Integer id) {
		ModelAndView mav = new ModelAndView();
		// id对应的排课记录
		TimetableAppointment t = timetableAppointmentDAO.findTimetableAppointmentByPrimaryKey(id);
		mav.addObject("t", t);
		//根据排课记录查询学生名单
		Set<User> students=timetableAttendanceService.findStudentsByTimetableAppointment(t);
		//排课所属的学期周次数量
		int weeks=shareService.getTermNumber(t);
		mav.addObject("weeks",weeks);
		//根据学生名单查询成绩
		List<CheckAttendancetable> checkList=timetableAttendanceService.getTearmScoreByStudents(students,weeks,t);
		mav.addObject("checkList",checkList);
		//根据成绩结果获取成绩次数
		int time=timetableAttendanceService.getTearmScoreTime(checkList);
		mav.addObject("time",time);
		mav.setViewName("timetable/attendence/checkTearmAttendance.jsp");
		return mav;
	}
	/************************************************************
	 * 我的考勤
	 * 李小龙
	 ************************************************************/
	@RequestMapping("/timetable/attendanceManage")
	public ModelAndView attendanceManage(@ModelAttribute SchoolCourse schoolCourse,@ModelAttribute("selected_academy") String acno){
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		mav.setViewName("timetable/attendanceManage/attendanceManage.jsp");
		//当前所在的学期
		SchoolTerm term=shareService.getBelongsSchoolTerm(Calendar.getInstance());
		mav.addObject("term", term);
		//当前所在的周次
		int week=shareService.getBelongsSchoolWeek(Calendar.getInstance());
		mav.addObject("week", week);
		//查询表单的对象
		mav.addObject("schoolCourse", schoolCourse);
		List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
		mav.addObject("schoolTerms", schoolTerms);// 学期
		Set<SchoolCourse> schoolCourses=shareService.findSchoolCourse();
		mav.addObject("schoolCourses", schoolCourses);//课程
		Set<User> users=shareService.findSchoolCourseTeachers(schoolCourses);
		mav.addObject("users", users);//教师
		//Map<Integer,String> weeks=shareService.getWeekMap();
		mav.addObject("weeks", shareService.getWeekMap());//周次
		
		//查询出当前登录人可以查看的课程考勤
		List<Attendance> attList=timetableAttendanceService.findAttendanceBySchoolCourse(schoolCourse,acno);
		mav.addObject("attList", attList);
		
		return mav;
		
	}
	/************************************************************
	 * 我的考勤
	 * 李小龙
	 ************************************************************/
	@RequestMapping("/timetable/listOfStudent")
	public ModelAndView listOfStudent(@RequestParam String courseNo){
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		mav.setViewName("timetable/attendanceManage/listOfStudent.jsp");
		//根据选课组查询学生名单
		List<SchoolCourseStudent> studentList=shareService.findStudentByCourseNo(courseNo);
		mav.addObject("studentList", studentList);
		return mav;
		
	}
	/************************************************************
	 * 查看实到人数
	 * 李小龙
	 ************************************************************/
	@RequestMapping("/timetable/showPeople")
	public ModelAndView showPeople(@RequestParam String courseNo,Integer week){
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		mav.setViewName("timetable/attendanceManage/showPeople.jsp");
		//根据选课组查询学生名单
		List<SchoolCourseStudent> studentList=shareService.findStudentByCourseNo(courseNo);
		//根据学生名单和周次统计考勤次数
		List<People> people=shareService.findPeopleByStudentsAndWeek(studentList,week,courseNo);
		mav.addObject("people", people);
		mav.addObject("week", week);
		mav.addObject("schoolCourse",schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo) );
		return mav;
		
	}
	/************************************************************
	 * 查看缺勤人数
	 * 李小龙
	 ************************************************************/
	@RequestMapping("/timetable/showAbsence")
	public ModelAndView showAbsence(@RequestParam String courseNo,Integer week){
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		mav.setViewName("timetable/attendanceManage/showAbsence.jsp");
		//根据选课组编号和周次统计缺勤次数
		List<People> people=shareService.findAbsenceByStudentsAndWeek(week,courseNo);
		mav.addObject("people", people);
		mav.addObject("week", week);
		mav.addObject("schoolCourse",schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo) );
		return mav;
		
	}

	/**
	 * Description 考勤课程列表
	 * @return
	 * @author 陈乐为 2018-12-11
	 */
	@RequestMapping("/attendanceList")
	public ModelAndView attendanceList(@RequestParam int currpage,@ModelAttribute("selected_academy") String acno,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		int pageSize = 20;
		// 获取学期列表
		List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
		mav.addObject("schoolTerms", schoolTerms);
		// 学期参数
		int termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(request.getParameter("schoolTerm")!=null) {
			termId = Integer.valueOf(request.getParameter("schoolTerm"));
		}
		mav.addObject("termId", termId);
		// 课程列表
		StringBuffer hql_query = new StringBuffer("select course_no,course_name,teacher from report_schedule where term_id="+termId);
		hql_query.append(" group by course_no");
		Query query = entityManager.createNativeQuery(hql_query.toString());
		mav.addObject("course_list", query.getResultList());
		// 课程编号
		mav.addObject("course_no", request.getParameter("course_no"));
		// 课程安排明细
		List<Object[]> courseInfos = timetableAttendanceService.findAttendanceCourseInfo(request,acno,currpage,pageSize);
		mav.addObject("courseInfos", courseInfos);
		// 分页参数
		int totalRecords = timetableAttendanceService.findAttendanceCourseInfo(request,acno,1,-1).size();
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));

		mav.setViewName("timetable/attendanceManage/attendanceList.jsp");
		return mav;
	}

	/**
	 * Description 课程学生列表
	 * @param app_id
	 * @param course_no
	 * @param weeks
	 * @param weekday
	 * @param start_class
	 * @param end_class
	 * @param acno
	 * @return
	 * @author 陈乐为 2018-12-11
	 */
	@RequestMapping(value = "/studentsList/{course_no}/{app_id}/{weeks}/{weekday}/{start_class}/{end_class}", method = RequestMethod.GET)
	public ModelAndView myReservationListRest(@PathVariable int app_id, @PathVariable String course_no, @PathVariable int weeks,
											  @PathVariable int weekday, @PathVariable int start_class, @PathVariable int end_class,
											  @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		StringBuffer hql = new StringBuffer("select c from TimetableAttendance c where 1=1");
		// 课程、上课日期、起始节次、结束节次
		hql.append(" and c.courseNo = '"+ course_no +"'");
		hql.append(" and c.timetableAppointment.id = "+ app_id);
		hql.append(" and c.week = "+ weeks);
		hql.append(" and c.weekday = "+ weekday);
		hql.append(" and c.startClass = "+ start_class);
		hql.append(" and c.endClass = "+ end_class);
		List<TimetableAttendance> list = timetableAttendanceDAO.executeQuery(hql.toString(),0,-1);
		mav.addObject("list", list);

		mav.setViewName("timetable/attendanceManage/studentsList.jsp");
		return mav;
	}

	/**
	 * Description 保存手动考勤
	 * @param idKey
	 * @return
	 * @author 陈乐为 2018-12-11
	 */
	@RequestMapping(value = "/saveAttendance/{idKey}", method = RequestMethod.GET)
	@ResponseBody
	public String saveAttendance(@PathVariable int idKey) {
		TimetableAttendance attendance = timetableAttendanceDAO.findTimetableAttendanceByPrimaryKey(idKey);
		attendance.setActualAttendance(1);
		timetableAttendanceDAO.store(attendance);
		timetableAttendanceDAO.flush();

		return "success";
	}




}