package net.zjcclims.web.teachingArrangement;

import flex.messaging.io.ArrayList;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.timetable.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/****************************************************************************
 * @功能：二次管理模块
 * @作者：魏诚 时间：2014-07-14
 ****************************************************************************/
@Controller("TeachingArrangementAjaxController")
@SessionAttributes("selected_academy")
public class TeachingArrangementAjaxController<JsonResult> {
	/************************************************************
	 * @初始化WebDataBinder，这个WebDataBinder用于填充被@InitBinder注释的处理 方法的command和form对象
	 *
	 ************************************************************/
	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) {
		binder.registerCustomEditor(Calendar.class,
				new org.skyway.spring.util.databinding.CustomCalendarEditor());
		binder.registerCustomEditor(byte[].class,
				new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(false));
		binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
		binder.registerCustomEditor(java.math.BigDecimal.class,
				new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
		binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
				Integer.class, true));
		binder.registerCustomEditor(Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
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
	private SchoolCourseDetailService schoolCourseDetailService;
	@Autowired
	private ShareService shareService;
	@Autowired
	private TimetableAppointmentDAO timetableAppointmentDAO;
	@Autowired
	private SchoolCourseDAO schoolCourseDAO;
	@Autowired
	private SchoolTermDAO schoolTermDAO;
	@Autowired
	private LabRoomService labRoomService;

	/************************************************************
	 * @教务排课-打开课程进度页面（有软件）
	 * @参数：courseCode选课组编号，currpage调用的当前页数以便返回定位，flag提示是否处理调课完成0不提示，1提示
	 * @作者：戴昊宇
	 * @日期：2017-07-24
	 ************************************************************/
	@RequestMapping("/teachingArrangement/openListTimetableAp")
	public ModelAndView openListTimetableAp(@RequestParam String courseCode, int currpage, int flag, Integer tableAppId,
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
		List<Integer> inte= new flex.messaging.io.ArrayList();
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
		//获得自主排课id
		List<TimetableSelfCourse> listt =new flex.messaging.io.ArrayList(schoolCourseDetailLists.get(0).getSchoolCourse().getSchoolCourseInfo().getTimetableSelfCourses());
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

		mav.setViewName("timetable/teachingArrangement/openListTimetableAp.jsp");
		return mav;
	}

	/**************************************************************************************
	 * @课程进度删除（有软件）
	 * @页面跳转：timetableAdmin-timetableAdminIframe-deleteAppointment
	 * @作者：戴昊宇
	 * @日期：2017-07-20
	 *************************************************************************************/
	@RequestMapping("/teachingArrangement/deleteTimeTableAppointment")
	public ModelAndView deleteTimeTableAppointment(@RequestParam int id,String courseCode) {
		ModelAndView mav = new ModelAndView();
		timetableAppointmentService.deleteTimetableAppointment(id);
		mav.setViewName("redirect:/teachingArrangement/openListTimetableAp?currpage=0&flag=0&courseCode="
				+courseCode+"&tableAppId=0");
		return mav;
	}

	/************************************************************
	 * @教务排课-确认调整排课是否完成--从排课页面进入
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/teachingArrangement/doJudgeTimetableOk")
	public ModelAndView doJudgeTimetableOk(HttpServletRequest request, @RequestParam String courseCode) {
		ModelAndView mav = new ModelAndView();
		// 发布排课
		List<TimetableAppointment> timetableAppointments = timetableAppointmentDAO
				.executeQuery("select c from TimetableAppointment c where c.courseCode like '" + courseCode + "'");
		for (TimetableAppointment timetableAppointment : timetableAppointments) {
			timetableAppointment.setStatus(2);
			timetableAppointmentDAO.store(timetableAppointment);
		}
		mav.setViewName("redirect:/teachingArrangement/listPracticalTimetableTrain?currpage=" + request.getParameter("currpage") + "&id=-1&flag=0");
		return mav;
	}

	/************************************************************
	 * @throws ParseException
	 * @教务排课-进行课程进度保存（有软件）
	 * @作者：戴昊宇
	 * @日期：2017-07-21
	 ************************************************************/
	@RequestMapping("/teachingArrangement/saveTimetableAp")
	public ModelAndView saveTimetableAp(HttpServletRequest request) throws ParseException {
		ModelAndView mav = new ModelAndView();
		// 保存调整排课数据
		timetableAppointmentService.saveTimetableAp(request);
		mav.setViewName("redirect:/teachingArrangement/openListTimetableAp?courseCode=" + request.getParameter("courseCode")
				+ "&currpage=" + request.getParameter("currpage") + "&flag=1&tableAppId=0");
		return mav;
	}

	/************************************************************
	 * @教务排课-打开课程进度页面（无软件）
	 * @作者：戴昊宇
	 * @日期：2017-07-24
	 ************************************************************/
	@RequestMapping("/teachingArrangement/openListTimetableApNoSoftware")
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
		mav.setViewName("timetable/teachingArrangement/openListTimetableApNoSoftWare.jsp");
		return mav;
	}

	/**************************************************************************************
	 * @课程进度删除（无软件）
	 * @作者：戴昊宇
	 * @日期：2017-07-20
	 *************************************************************************************/
	@RequestMapping("/teachingArrangement/deleteTimeTableAppointmentNosoftware")
	public ModelAndView deleteTimeTableAppointmentNosoftware(@RequestParam int id,String courseCode,int term) {
		ModelAndView mav = new ModelAndView();
		timetableAppointmentService.deleteTimetableAppointment(id);
		mav.setViewName("redirect:/teachingArrangement/openListTimetableApNoSoftware?term="+term+"&flag=0&courseCode="
				+courseCode+"&tableAppId=0");
		return mav;
	}

	/************************************************************
	 * @throws ParseException
	 * @教务排课-进行课程进度保存（无软件）
	 * @作者：戴昊宇
	 * @日期：2017-07-21
	 ************************************************************/
	@RequestMapping("/teachingArrangement/saveTimetableApNoSoftWare")
	public ModelAndView saveTimetableApNoSoftWare(HttpServletRequest request) throws ParseException {
		ModelAndView mav = new ModelAndView();
		// 保存调整排课数据
		timetableAppointmentService.saveTimetableAp(request);
		mav.setViewName("redirect:/teachingArrangement/openListTimetableApNoSoftware?term="+ request.getParameter("term")+"&courseCode=" + request.getParameter("courseCode")
				+"&tableAppId=0");
		return mav;
	}

	/**************************************************************************
	 * @实践性教学排课审核及发布（保存审核提交）
	 * @作者：魏诚
	 * @日期：2014-07-25
	 **************************************************************************/
	@RequestMapping("/teachingArrangement/saveTimetablevertifyRelease")
	public ModelAndView saveTimetableAudit(HttpServletRequest request, @RequestParam String courseCode,
										   int appStyle, int status,int labrelate,int tag,int page) {
		ModelAndView mav = new ModelAndView();
		int auditStr = Integer.valueOf(request.getParameter("auditResult"));
		String remark = request.getParameter("remark");
		//自主排课发布入口，标志位需改为静态常量
		if(appStyle==6){
			Set<TimetableAppointment> timetableAppointmentByCourseCode = timetableAppointmentDAO.findTimetableAppointmentByCourseCode(courseCode);
			for(TimetableAppointment ta:timetableAppointmentByCourseCode) {
				//获取一条排课记录相关实验室
				List<TimetableLabRelated> timetableLabRelateds = timetableAppointmentService.getlabRelatedByAppointmentID(ta.getId());
				for(TimetableLabRelated tlr:timetableLabRelateds){
					timetableAppointmentService.saveTimetableAppAudit(courseCode, appStyle, status, auditStr, remark, tlr.getLabRoom().getId(), tag);
				}
			}
		}else {
			List<TimetableLabRelated> list = timetableAppointmentService.getlabRelated(labrelate);
			int labroomId = 0;
			if (list != null) {
				labroomId = list.get(0).getLabRoom().getId();
			}
			timetableAppointmentService.saveTimetableAppAudit(courseCode, appStyle, status, auditStr, remark, labroomId, tag);
		}
		mav.setViewName("redirect:/test#teachingArrangement/timetablevertifyRelease?currpage="+page+"&cid=-1&status=-1");
		return mav;
	}

	/**************************************************************************
	 * @排课管理的删除排课条目
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ***************************************************************************/
	@RequestMapping("/teachingArrangement/deleteAppointment")
	public ModelAndView deleteAppointment(@RequestParam int id,int term, int currpage,String courseNumber) {

		// 排课管理的删除排课条目
		timetableAppointmentService.deleteAppointment(id);

		// 删除排课相关的实验室禁用记录
		labRoomService.deleteLabRoomLimitTimeByAppointment(id);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/teachingArrangement/timetablevertifyRelease?currpage="+currpage+"&cid=-1&status=-1");
		return mav;
	}
}