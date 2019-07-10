package net.zjcclims.web.timetable;

import api.net.gvsunlims.constant.ConstantInterface;
import net.gvsun.lims.dto.common.PConfigDTO;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.MySQLService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.operation.OperationService;
import net.zjcclims.service.timetable.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.*;


/****************************************************************************
 * @功能说明： 排课管理 排课管理的所有页面跳转的功能在本文件中实现 作者：魏诚 时间：2014-08-16
 ****************************************************************************/
@Controller("TimetableAdminController")
@SessionAttributes({"selected_academy","isPosted"})
public class TimetableAdminController<JsonResult> {

	/*************************************************************************
	 * @初始化WebDataBinder，这个WebDataBinder用于填充被@InitBinder注释的处理 方法的command和form对象
	 * 
	 *************************************************************************/
	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) {
		binder.registerCustomEditor(java.util.Calendar.class,
				new org.skyway.spring.util.databinding.CustomCalendarEditor());
		binder.registerCustomEditor(
				byte[].class,
				new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(boolean.class,
				new org.skyway.spring.util.databinding.EnhancedBooleanEditor(
						false));
		binder.registerCustomEditor(Boolean.class,
				new org.skyway.spring.util.databinding.EnhancedBooleanEditor(
						true));
		binder.registerCustomEditor(java.math.BigDecimal.class,
				new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
						java.math.BigDecimal.class, true));
		binder.registerCustomEditor(Integer.class,
				new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
						Integer.class, true));
		binder.registerCustomEditor(java.util.Date.class,
				new org.skyway.spring.util.databinding.CustomDateEditor());
		binder.registerCustomEditor(String.class,
				new org.skyway.spring.util.databinding.StringEditor());
		binder.registerCustomEditor(Long.class,
				new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
						Long.class, true));
		binder.registerCustomEditor(Double.class,
				new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
						Double.class, true));
	}

	@Autowired
	private ShareService shareService;
	@Autowired
	private SchoolCourseDetailService schoolCourseDetailService;
	@Autowired
	private TimetableAppointmentService timetableAppointmentService;
	@Autowired
	private TimetableAppointmentDAO timetableAppointmentDAO;
	@Autowired
	private OuterApplicationService outerApplicationService;
	@Autowired
	private TimetableSelfSchedulingService timetableSelfSchedulingService;
	
	@Autowired
	private TimetableBatchService timetableBatchService;	
	@Autowired
	private TimetableReSchedulingService timetableReSchedulingService;	
	@Autowired
	private LabRoomService labRoomService;
	@Autowired
	private
	TimetableCourseSchedulingService timetableCourseSchedulingService;
	@Autowired
	private
	TimetableSelfCourseService timetableSelfCourseService;
	@Autowired
	private
	SchoolCourseService schoolCourseService;
	@Autowired
	private 
	SchoolCourseInfoDAO schoolCourseInfoDAO;
	@Autowired
	private 
	TimetableAppointmentResultDAO timetableAppointmentResultDAO;
	@Autowired
	private SchoolCourseInfoService schoolCourseInfoService;
	@Autowired
	private SchoolMajorDAO schoolMajorDAO;
	@Autowired
	private SchoolTermDAO schoolTermDAO;
	@Autowired
	private CommonDocumentDAO commonDocumentDAO;
	@Autowired
	private OperationService operationService;
	@Autowired
	private MySQLService mySQLService;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private SchoolCourseStudentDAO schoolCourseStudentDAO;
	@Autowired
	private SchoolCourseDAO schoolCourseDAO;
    @Autowired
	private OperationItemDAO operationItemDAO;
    @Autowired
    private SchoolCourseDetailDAO schoolCourseDetailDAO;
	@Autowired
	private TimetableAppointmentSaveService timetableAppointmentSaveService;
	@Autowired
	private LabRoomDAO labRoomDAO;
	@RequestMapping("/timetable/saveTimetableAudit")
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
		mav.setViewName("redirect:/test#timetable/timetablevertify?currpage="+page+"&cid=-1&status=-1");
		return mav;
	}
	
	/**************************************************************************
	 * @排课管理  排课管理-页面列表
	 * @页面跳转：timetableAdmin-timetableAdminIframe
	 * @作者：魏诚
	 * @日期：2014-07-25
	 **************************************************************************/
	@RequestMapping("/timetable/timetableAdminIframe")
	public ModelAndView timetableAdminIframe(HttpServletRequest request,
			@ModelAttribute TimetableAppointment timetableAppointment,
			@RequestParam int currpage,@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		// 获取学期列表
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", schoolTerms);
		// 当前时间的学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term")!=null&&!request.getParameter("term").equals("null")) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		mav.addObject("term", term);

		//设置区分排课确认0与课表执行1
		Integer isposted = 0;
		if(request.getParameter("isposted") != null && !request.getParameter("isposted").equals("")){
			isposted = Integer.parseInt(request.getParameter("isposted"));
		}
		mav.addObject("auditTag", request.getParameter("auditTag"));
		mav.addObject("isPosted", isposted);
		// 设置分页变量并赋值为10；
		int pageSize = 10;//CommonConstantInterface.INT_PAGESIZE;
		// 根据课程及id获取课程排课列表的计数
		int totalRecords = timetableAppointmentService.findTimetableRegisterCounts(timetableAppointment,request,acno);
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		// 将currpage映射到page，用来获取当前页的页码
		mav.addObject("page", currpage);
		mav.addObject("status", request.getParameter("status"));
		// 根据labClassWeekdays搜索排课记录
		mav.addObject("labroomTimetableRegisters", timetableAppointmentService.findViewLabroomTimetableRegisters(timetableAppointment,currpage - 1, pageSize,request,acno));
		mav.setViewName("timetable/timetableAdmin/timetableExecution.jsp");
		return mav;
	}
	
	/**************************************************************************
	 * @排课管理  排课管理-页面列表
	 * @页面跳转：timetableAdmin-timetableAdminIframe
	 * @作者：魏诚
	 * @日期：2014-07-25
	 **************************************************************************/
	@RequestMapping("/timetable/timetablevertify")
	public ModelAndView timetablevertify(HttpServletRequest request,
			@ModelAttribute TimetableAppointment timetableAppointment,
			@RequestParam int currpage,int status) {
		ModelAndView mav = new ModelAndView();
		PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
		mav.addObject("PROJECT_NAME",pConfigDTO.PROJECT_NAME);
		// 获取学期列表
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", schoolTerms);
		// 当前时间的学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term")!=null&&!request.getParameter("term").equals("null")) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		
		//SchoolTerm schoolTerm = schoolTermDAO.findSchoolTermById(term);
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
		mav.setViewName("timetable/timetableAdmin/timetablevertify.jsp");
		Set<TimetableAppointmentResult> allresults = timetableAppointmentResultDAO.findAllTimetableAppointmentResults();
		mav.addObject("allresults",allresults);
		return mav;
	}
	
	
	/**************************************************************************
	 * @排课管理  上课确认页面-- 复制上级方法过滤今天之前数据
	 * @作者：张秦龙   
	 * @日期：2017-12-26
	 **************************************************************************/
	@RequestMapping("/timetable/timetableAdminIframeConfirm")
	public ModelAndView timetableAdminIframeConfirm(HttpServletRequest request,
			@ModelAttribute TimetableAppointment timetableAppointment,
			@RequestParam int currpage) {
		ModelAndView mav = new ModelAndView();
		// 获取学期列表
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", schoolTerms);
		// 当前时间的学期
		//int term = shareService.findNewTerm().getId();取当前所处学期或下个学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term")!=null&&!request.getParameter("term").equals("null")) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		
		//SchoolTerm schoolTerm = schoolTermDAO.findSchoolTermById(term);
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
		
		Integer status = 1;
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
		// 设置分页变量并赋值为10；
		int pageSize = 10;//CommonConstantInterface.INT_PAGESIZE;
		// 根据课程及id获取课程排课列表的计数
		int totalRecords = timetableAppointmentService
				.getCountTimetableAppointmentsByQuery(term,courseNumber,
						status,1);
		if(isposted == 1){     //zql 2017.12.26
			totalRecords = timetableAppointmentService.getViewLabroomTimetableRegistersConfirmCount(courseNumber,currpage - 1, pageSize);
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
		mav.addObject("status", request.getParameter("status"));

		// 根据labClassWeekdays搜索排课记录
		if(isposted == 1){               //zql 2017.12.26
			mav.addObject("labroomTimetableRegisters", timetableAppointmentService.getViewLabroomTimetableRegistersConfirm(courseNumber,currpage , pageSize));
			mav.setViewName("timetable/timetableAdmin/timetableExecutionConfirm.jsp");
		}else{
			mav.setViewName("timetable/timetableAdmin/listTimetableAdminIframe.jsp");
		}
		return mav;
	}
	
	/*********************************
	 * @功能：需要上课确认的数据推送到我的消息中
	 * @作者：张秦龙
	 * @时间：2017-12-27        
	 *********************************/
	@RequestMapping("/timetable/sendMessageAppointment")
	public String sendMessageAppointment(@RequestParam int id,int term, int currpage, String courseNumber, int flag){
		
		timetableAppointmentService.saveMessageAppointment(id);
		return "redirect:/timetable/timetableAdminIframeConfirm?";
	}
	
	/**************************************************************************
	 * @查看教学用设备
	 * @作者：贺子龙
	 * @日期：2016-05-16
	 ***************************************************************************/
	@RequestMapping("/timetable/listTeachingLabRoomDevice")
	public ModelAndView listTeachingLabRoomDevice(@RequestParam int timetableId,int labRoomId) {
		ModelAndView mav = new ModelAndView();
		/**
		 * 1、判断该实验室是否允许教学外的设备对外开放
		 * 2、若开放，显示排课用到的设备
		 * 3、若不开放，显示所有设备
		 */
		// 找到对应实验室
		LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(labRoomId);
		Set<LabRoomDevice> labRoomDeviceAll = labRoom.getLabRoomDevices();
		// 找到对应排课
		TimetableAppointment appointment = timetableAppointmentDAO.findTimetableAppointmentByPrimaryKey(timetableId);
		List<LabRoomDevice> deviceList = new ArrayList<LabRoomDevice>();
		if (appointment.getDeviceOrLab().equals(2)) {// 针对实验室
			deviceList.addAll(labRoom.getLabRoomDevices());
		}else {// 允许
			if (appointment!=null && appointment.getTimetableLabRelateds()!=null && appointment.getTimetableLabRelateds().size()>0) {
				for (TimetableLabRelated related : appointment.getTimetableLabRelateds()) {
					if(related.getLabRoom() != null && related.getLabRoom().getId() == labRoomId){
						if (related.getTimetableLabRelatedDevices()!=null && related.getTimetableLabRelatedDevices().size()>0) {
							for (TimetableLabRelatedDevice deviceRelated : related.getTimetableLabRelatedDevices()) {
								deviceList.add(deviceRelated.getLabRoomDevice());
								labRoomDeviceAll.remove(deviceRelated.getLabRoomDevice()) ;
							}
						}
					} 
				}
			}
		}
		mav.addObject("labRoomDeviceAll",labRoomDeviceAll);
		mav.addObject("timetableId",timetableId);
		mav.addObject("labRoomId",labRoomId);
		mav.addObject("deviceList", deviceList);
		mav.addObject("labRoom", labRoom);
		mav.addObject("appointment", appointment);
		mav.setViewName("timetable/timetableAdmin/listTeachingLabRoomDevice.jsp");
		return mav;
	}
	
	/**************************************************************************
	 * @排课管理的删除排课条目
	 * @页面跳转：timetableAdmin-timetableAdminIframe-deleteAppointment
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ***************************************************************************/
	@RequestMapping("/timetable/deleteAppointment")
	public ModelAndView deleteAppointment(@RequestParam int id,int term, int currpage,String courseNumber) {

		// 排课管理的删除排课条目
		timetableAppointmentService.deleteAppointment(id);

		// 删除排课相关的实验室禁用记录
		labRoomService.deleteLabRoomLimitTimeByAppointment(id);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/timetable/timetablevertify?currpage="+currpage+"&cid=-1&status=-1");
		return mav;
	}
	/**************************************************************************
	 * @排课管理的确认
	 * @页面跳转：timetableAdmin-timetableAdminIframe-confirmAppointment
	 * @作者：孙虎
	 * @日期：2017-11-20
	 ***************************************************************************/
	@RequestMapping("/timetable/confirmAppointment")
	public ModelAndView confirmAppointment(@ModelAttribute("isPosted") int isposted,@RequestParam int id, int currpage,int flag,String remark) {
		
		// 排课管理的确认
		timetableAppointmentService.confirmAppointment(id,flag,remark);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/timetable/timetableAdminIframe?status=-1&currpage="
				+ currpage +"&isposted="+isposted);
		return mav;
	}
	/****************************************************************************
	 * @排课管理-发布教务直接排课
	 * @页面跳转：timetableAdmin-timetableAdminIframe-doReleaseTimetable
	 * @作者：魏诚 日期：2014-08-4
	 ****************************************************************************/
	@RequestMapping("/timetable/doReleaseTimetable")
	public ModelAndView doReleaseTimetable(@RequestParam String courseCode,int flag,int term, String courseNumber) {
		ModelAndView mav = new ModelAndView();
		TimetableAppointment timetableAppointment = new TimetableAppointment();
		mav.addObject("timetableAppointment", timetableAppointment);
		// 发布排课,0为教务相关排课，1为自主排课
	    timetableAppointmentService.doReleaseTimetableAppointments(courseCode,flag);

		mav.setViewName("redirect:/timetable/timetableAdminIframe?currpage=1&id=-1&status=-1&term=" +term+"&courseNumber="+courseNumber);
		return mav;
	}

	/****************************************************************************
	 * @throws ParseException 
	 * @排课管理 排课管理-保存排课修改
	 * @页面跳转：timetableAdmin-timetableAdminIframe-doAdminTimetable-saveAdminTimetable 教务排课进行调整排课 
	 * @作者:魏诚 	 日期：2014-07-27
	 ****************************************************************************/
	@RequestMapping("/timetable/saveAdminTimetable")
	public ModelAndView saveAdminTimetable(HttpServletRequest request) throws ParseException {
		ModelAndView mav = new ModelAndView();
		// 获取排课记录
		TimetableAppointment requestTimetableAppointment = timetableAppointmentDAO
				.findTimetableAppointmentById(Integer.parseInt(request
						.getParameter("id")));
		
		int tapId=Integer.parseInt(request.getParameter("id"));
		// 处理教务排课及二次不分组排课
		if (requestTimetableAppointment.getTimetableStyle() == 2) {
			timetableAppointmentService.saveTimetableadjustment2(request,tapId);
			// 对于实验室内部调课数据插入
			timetableAppointmentService.saveAdjustMent2(request);
		}
		if (requestTimetableAppointment.getTimetableStyle() == 3) {
			timetableAppointmentService.saveNoGroupReTimetable(request);
		}
		// 处理二次分组排课
		if (requestTimetableAppointment.getTimetableStyle() == 4) {

			// 保存调整排课数据
			timetableAppointmentService.saveGroupReTimetable(request);
		}
		// 处理自主排课不分组排课
		if (requestTimetableAppointment.getTimetableStyle() == 5) {
			
			timetableSelfSchedulingService.saveNoGroupSelfTimetable(request);
		}
		// 处理自主排课分组排课
		if (requestTimetableAppointment.getTimetableStyle() == 6) {
			// 保存调整排课数据
			timetableSelfSchedulingService.saveGroupReTimetable(request);
		}
		mav.setViewName("redirect:/timetable/timetableAdminIframe?currpage=1&term=" + request.getParameter("term") + "&id=-1&status=10");
		return mav;
	}

	/****************************************************************************
	 * @排课管理 教务排课-进行调整排课
	 * @页面跳转：timetableAdmin-timetableAdminIframe-doReleaseTimetable 
	 * @作者：魏诚 日期：2014-07-27
	 ****************************************************************************/
	@RequestMapping("/timetable/doAdminTimetable")
	public ModelAndView doAdminTimetable(HttpServletRequest request,
			@RequestParam int id,@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		// 获取去重的实验分室室列表
		mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
		// 获取可选的教师列表列表
		mav.addObject("timetableTearcherMap",
				outerApplicationService.getTimetableTearcherMap());
		TimetableAppointment timetableAppointment = new TimetableAppointment();
		mav.addObject("timetableAppointment", timetableAppointment);
		// 获取去重的实验分室室列表
		mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
		// 获取可选的教师列表列表
		mav.addObject("timetableTearcherMap",
				outerApplicationService.getTimetableTearcherMap());
     	
		// 根据课程及id获取课程排课列表
		TimetableAppointment timetableAppointmentMap = timetableAppointmentDAO.findTimetableAppointmentById(id);
		mav.addObject("timetableAppointmentMap",timetableAppointmentMap);
		//记录时间
		if(timetableAppointmentMap.getTimetableStyle()==5||timetableAppointmentMap.getTimetableStyle()==6){
			mav.addObject("term",timetableAppointmentMap.getTimetableSelfCourse().getSchoolTerm().getId());
		}else{
			mav.addObject("term",timetableAppointmentMap.getSchoolCourse().getSchoolTerm().getId());
		}
		// 获取可选的实验项目列表列表
		mav.addObject("timetableItemMap", outerApplicationService
				.getTimetableItemMap(timetableAppointmentDAO
						.findTimetableAppointmentById(id).getSchoolCourseInfo()
						.getCourseNumber()));
		mav.setViewName("timetable/timetableAdmin/listAdminTimetableTerm.jsp");
		mav.addObject("tableAppId",id);
		return mav;
	}

	
	
	/****************************************************************************
	 * @分组管理-对分组排课的信息进行管理和维护
	 * @作者：魏诚 日期：2015-05-10
	 ****************************************************************************/
	@RequestMapping("/timetable/groupAdmin")
	public ModelAndView groupAdmin(HttpServletRequest request,
				@ModelAttribute TimetableBatch timetableBatch,
				@RequestParam int currpage,@ModelAttribute("selected_academy") String acno) {
			ModelAndView mav = new ModelAndView();
			// 获取学期列表
			List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
			mav.addObject("schoolTerms", schoolTerms);
			// 当前时间的学期
			int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
			if (request.getParameter("term") != null) {
				term = Integer.parseInt(request.getParameter("term"));
			}
			
			//SchoolTerm schoolTerm = schoolTermDAO.findSchoolTermById(term);
			mav.addObject("term", term);
			
			// 获取登录用户信息
			mav.addObject("user", shareService.getUserDetail());
			// 判断是否标记位为空，如果为空，则清空schoolCourseDetail
			if (request.getParameter("id") != null
					&& request.getParameter("id").equals("-1")) {
				// timetableAppointment.setId(-1) ;
			}
			// 根据课程及id获取课程排课列表的计数
			int totalRecords = timetableBatchService
					.getCountTimetableBatchByQuery(term,Integer.parseInt(request.getParameter("status")),acno);
			mav.addObject("totalRecords", totalRecords);
			// 设置分页变量并赋值为20；
			int pageSize = 50;//CommonConstantInterface.INT_PAGESIZE;
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

			// 根据课程及id获取课程排课列表
			mav.addObject("timetableBatchs", timetableBatchService
					.getTimetableBatchByQuery(term,Integer.parseInt(request.getParameter("status")),
							currpage - 1, pageSize));
			mav.addObject(
					"timetableBatchAll",
					timetableBatchService.getTimetableBatchByQuery(term,Integer.parseInt(request.getParameter("status")), 0, -1));
			// 根据labClassWeekdays搜索排课记录
			mav.setViewName("timetable/timetableAdmin/listTimetableBatch.jsp");
			return mav;
	}
	
	/************************************************************
	 * @二次排课： 删除id对应的批次的所有记录
	 * @页面跳转：listReTimetable-listReTimetable-doIframeGroupReTimetable-deleteBatch
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/deleteBatchAdmin")
	public ModelAndView deleteBatch(@RequestParam int id, int term, String courseCode) {
		ModelAndView mav = new ModelAndView();
		// 删除id对应的批次的所有记录
		timetableReSchedulingService.deleteBatch(id, term, courseCode);
		mav.setViewName("../../timetable/groupAdmin?currpage=1&status=-1" );
		return mav;
	}

	/**************************************************************************************
	 * @description排课管理： 保存添加的教学设备  
	 * @author：郑昕茹
	 * @date：2016-10-13
	 *************************************************************************************/
	@RequestMapping("/timetable/saveLabRoomDeviceRelated")
	public ModelAndView saveLabRoomDeviceRelated(@RequestParam String[] devices,@RequestParam Integer timetableId, @RequestParam Integer labRoomId) throws ParseException {
		ModelAndView mav = new ModelAndView(); 
		TimetableAppointment appointment = timetableAppointmentDAO.findTimetableAppointmentById(timetableId);
		if (appointment!=null && appointment.getTimetableLabRelateds()!=null && appointment.getTimetableLabRelateds().size()>0) {
			for (TimetableLabRelated related : appointment.getTimetableLabRelateds()) {
				if(related.getLabRoom() != null && related.getLabRoom().getId().equals(labRoomId) ){ 
						if(appointment.getTimetableStyle() == 1 || appointment.getTimetableStyle() == 2
								|| appointment.getTimetableStyle() == 3 || appointment.getTimetableStyle() == 4)
						{
							timetableCourseSchedulingService.saveTimetableLabroomDeviceReservation(related, devices, appointment.getSchoolCourse().getSchoolTerm().getId());
						}
						else{
							timetableCourseSchedulingService.saveTimetableLabroomDeviceReservation(related, devices, appointment.getTimetableSelfCourse().getSchoolTerm().getId());
						} 
				} 
			}
		}
		mav.setViewName("redirect:/timetable/listTeachingLabRoomDevice?timetableId="+timetableId+"&labRoomId="+labRoomId);
		return mav;

	}
	/**************************************************************************
	 * @排课管理  排课管理-调停课页面
	 * @作者：戴昊宇
	 * @日期：2017-09-26
	 **************************************************************************/
	@RequestMapping("/timetable/timetableAlterIframe")
	public ModelAndView timetableAlterIframe(HttpServletRequest request,
			@ModelAttribute TimetableAppointment timetableAppointment,
			@RequestParam int currpage) {
		ModelAndView mav = new ModelAndView();
		// 获取学期列表
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", schoolTerms);
		// 当前时间的学期
		//int term = shareService.findNewTerm().getId();取当前所处学期或下个学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term")!=null&&!request.getParameter("term").equals("null")) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		
		//SchoolTerm schoolTerm = schoolTermDAO.findSchoolTermById(term);
		mav.addObject("term", term);
		
		// 获取登录用户信息
		mav.addObject("user", shareService.getUserDetail());
		
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
		
				
		// 根据课程及id获取课程排课列表的计数
		int totalRecords = timetableAppointmentService.getCountTimetableAppointmentsByQuery(term,courseNumber, 1,0);
		mav.addObject("totalRecords", totalRecords);
		// 设置分页变量并赋值为20；
		int pageSize = 50;
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		// 将currpage映射到page，用来获取当前页的页码
		mav.addObject("page", currpage);
		
		// 获取所有的学期
		List<SchoolTerm> terms = schoolCourseDetailService.getSchoolTermsByNow();
		mav.addObject("terms", terms);
		// 将course映射到course；
		mav.addObject("course", new SchoolCourse());
		
		// 根据课程及id获取课程排课列表(调停页面为0)
		mav.addObject("timetableAppointments", timetableAppointmentService.getTimetableAppointmentsByQuery(term,courseNumber,
						1, currpage - 1, pageSize,0));
		
		mav.addObject("timetableAppointmentAll", timetableAppointmentService.getTimetableAppointmentsByQuery(term,courseNumber,
						1, 0, -1,0));
		
		// 根据labClassWeekdays搜索排课记录
		mav.setViewName("timetable/timetableAdmin/listTimetableAlterIframe.jsp");
		return mav;
	}
	/**************************************************************************
	 * @排课管理  排课管理-顺延课程页面
	 * @作者：贺子龙
	 * @日期：2016-07-16
	 **************************************************************************/
	@RequestMapping("/timetable/timetableAdmin/alterTimetable")
	public ModelAndView alterTimetable(String courseCode) {
		ModelAndView mav = new ModelAndView();
		List<TimetableAppointment> appointments = timetableAppointmentService.findAppointmentByCourseCode(courseCode);
		mav.addObject("timetableAppointments", appointments);
		TimetableSelfCourse selfCourse = new TimetableSelfCourse();
		SchoolCourse schoolCourse = new SchoolCourse();
		if (courseCode.indexOf("code")!=-1) {// 自建选课组
			selfCourse = timetableSelfCourseService.findTimetableSelfCourseByCourseCode(courseCode);
		}else {// school_course
			schoolCourse = schoolCourseService.findSchoolCourseByPrimaryKey(courseCode);
		}
		mav.addObject("selfCourse", selfCourse);
		mav.addObject("schoolCourse", schoolCourse);
		
		mav.setViewName("timetable/timetableAdmin/alterTimetable.jsp");
		return mav;
	}
	/**************************************************************************
	 * @功能：停课
	 * @作者：戴昊宇
	 * @日期：2017-10-10
	 ***************************************************************************/
	@RequestMapping("/timetable/disableAppointment")
	public ModelAndView disableAppointment(@RequestParam int id,int term, int currpage,String courseNumber) {

		ModelAndView mav = new ModelAndView();
		// 排课管理的删除排课条目
		timetableAppointmentService.disableAppointment(id);
		
		// 删除排课相关的实验室禁用记录
		labRoomService.deleteLabRoomLimitTimeByAppointment(id);
		
		mav.setViewName("redirect:/timetable/timetableAdminIframe?status=-1&term=" + term + "&currpage="
				+ currpage +"&courseNumber=" + courseNumber);
		return mav;
	}
	/**************************************************************************
	 * @Description 停课新
	 * @return ModelAndView
	 * @author 孙虎
	 * @date 2018-4-20
	 ***************************************************************************/
	@RequestMapping("/timetable/disableAppointmentNew")
	public ModelAndView disableAppointmentNew(@RequestParam int id) {
		
		ModelAndView mav = new ModelAndView();
		// 排课管理的删除排课条目
		timetableAppointmentService.disableAppointment(id);
		
		// 删除排课相关的实验室禁用记录
		labRoomService.deleteLabRoomLimitTimeByAppointment(id);
		
		mav.setViewName("redirect:/personal/message/mySelfTimetable");
		return mav;
	}


	/**************************************************************************
	 * @排课管理  课程管理-页面列表
	 * @作者：魏诚
	 * @日期：2018-01-10
	 **************************************************************************/
	@RequestMapping("/timetable/listSchoolCourseInfo")
	public ModelAndView schoolCourseInfo(HttpServletRequest request,@ModelAttribute SchoolCourseInfo schoolCourseInfo,@RequestParam int currpage ){
		ModelAndView mav = new ModelAndView();
		int pageSize = 50;
		int totalRecords = schoolCourseInfoService.findCountSchoolCourseInfo(schoolCourseInfo);

		List<SchoolCourseInfo> schoolCourseInfoList = schoolCourseInfoService.findListSchoolCourseInfo(schoolCourseInfo, currpage, pageSize);
		mav.addObject("schoolCourseInfoList",schoolCourseInfoList);
		mav.addObject("pageSize", pageSize);
		mav.addObject("pageModel",shareService.getPage(currpage, pageSize, totalRecords));

		mav.setViewName("timetable/schoolCourseManager/listSchoolCourseInfo.jsp");
		return mav;
	}
	/**************************************************************************
	 * @Description 编辑课程
	 * @return ModelAndView
	 * @author 魏好
	 * @date 2018-01-10
	 **************************************************************************/
	@RequestMapping("/timetable/editSchoolCourseInfo")
	public ModelAndView editSchoolCourseInfo(@RequestParam String courseNumber) {
		ModelAndView mav = new ModelAndView();

		SchoolCourseInfo schoolCourseInfo = schoolCourseInfoDAO
				.findSchoolCourseInfoByPrimaryKey(courseNumber);
		Set<SchoolMajor> schoolMajorSet = schoolMajorDAO.findAllSchoolMajors();
		//适用专业
		mav.addObject("schoolMajorSet",schoolMajorSet);
		//开课学期
		String term = schoolCourseInfo.getTerm();
		String[] split=new String[7];
		if(term!=null){
			String substring = term.substring(0, term.length()-1);
			split = substring.split(",");
		}
		mav.addObject("selectTerm", split);
		//所有教师
		List<User> teacheres = shareService.findAllTeacheres();
		mav.addObject("teacheres",teacheres);
		mav.addObject("schoolCourseInfo", schoolCourseInfo);
		mav.addObject("isEdit", false); // isEdit标记，新建true 编辑 false
		//添加实验项目
		mav.addObject("operationItem", new OperationItem());
		//实验室
		List<LabRoom> labRooms = labRoomService.findAllLabroom();
		mav.addObject("labRooms",labRooms);
		//课程性质
		Set<SchoolMajor> schoolMajorSet2 = schoolCourseInfo.getSchoolMajor();
		mav.addObject("schoolMajorSet2",schoolMajorSet2);
		//课程学期
		Set<SchoolTerm> courseSchoolTerms = schoolCourseInfo.getSchoolTerms();
		mav.addObject("courseSchoolTerms",courseSchoolTerms);
		//所有学期
		Set<SchoolTerm> schoolTerms = schoolTermDAO.findAllSchoolTerms();
		mav.addObject("schoolTerms",schoolTerms);
		//授课教师
		Set<User> users = schoolCourseInfo.getUsers();
		mav.addObject("users", users);
		//实验项目
		Set<OperationItem> items = schoolCourseInfo.getOperationItems();
		mav.addObject("items", items);
		//用于文件上传的课程编号
		mav.addObject("courseupload", schoolCourseInfo.getCourseNumber());
		mav.setViewName("timetable/schoolCourseManager/editSchoolCourseInfo.jsp");
		return mav;
	}

	/********************************
	 * 新建课程
	 * @author 魏好
	 * @date 2018-01-10
	 *********************************/
	@RequestMapping("/timetable/newSchoolCourseInfo")
	public ModelAndView newSchoolCourseInfo() {
		ModelAndView mav = new ModelAndView();
		//适用专业
		Set<SchoolMajor> schoolMajorSet = schoolMajorDAO.findAllSchoolMajors();
		mav.addObject("schoolMajorSet",schoolMajorSet);
		//所有学期
		Set<SchoolTerm> schoolTerms = schoolTermDAO.findAllSchoolTerms();
		mav.addObject("schoolTerms",schoolTerms);
		//所有教师
		List<User> teacheres = shareService.findAllTeacheres();
		mav.addObject("teacheres",teacheres);
		mav.addObject("isEdit", true);// isEdit标记，新建true 编辑 false
		mav.addObject("schoolCourseInfo", new SchoolCourseInfo());

		//当前登录人所在学院
		User user = shareService.getUserDetail();
		SchoolAcademy schoolAcademy = user.getSchoolAcademy();
		mav.addObject("academyName",schoolAcademy.getAcademyName());


		//未保存主表时候先不给上传文件写死
		mav.addObject("courseupload", 1);
		mav.setViewName("timetable/schoolCourseManager/editSchoolCourseInfo.jsp");
		return mav;
	}
	/**************************************************************************
	 * @Description 保存课程
	 * @return ModelAndView
	 * @author 魏好
	 * @date 2018-01-10
	 **************************************************************************/
	@RequestMapping("/timetable/saveSchoolCourseInfo")
	public String saveSchoolCourseInfo(
			@ModelAttribute SchoolCourseInfo schoolCourseInfo,HttpServletRequest request) {
		// 保存
		SchoolCourseInfo courseInfo = schoolCourseInfoService.saveSchoolCourseInfo(schoolCourseInfo,request);
		String docment=request.getParameter("docment");
		if(docment!=null && docment!=""){
			String[] str= docment.split(",");
			for (String string : str) {
				if(string!=null && string!=""){
					CommonDocument dd= commonDocumentDAO.findCommonDocumentByPrimaryKey(Integer.parseInt(string));
					if(dd!=null){
						dd.setSchoolCourseInfo(courseInfo);
						commonDocumentDAO.store(dd);
					}
				}
			}
		}
		return "redirect:/timetable/editSchoolCourseInfo?courseNumber="+courseInfo.getCourseNumber();
	}

	/*********************************************************************************
	 * @description:查看大纲内容
	 * @author：郑昕茹
	 * @date：2017-04-10
	 ********************************************************************************/
	@RequestMapping("/timetable/viewOperationOutline")
	public ModelAndView viewOperationOutline(@RequestParam String courseNumber) {
		ModelAndView mav = new ModelAndView();
		//登陆人权限获得
		mav.addObject("authorities", shareService.getUser().getAuthorities());
		SchoolCourseInfo schoolCourseInfo = schoolCourseInfoDAO.findSchoolCourseInfoByCourseNumber(courseNumber);
		mav.addObject("schoolCourseInfo",schoolCourseInfo);
		mav.addObject("items", operationService.findOperationItemBycourseNumber(schoolCourseInfo.getCourseNumber()));
		mav.setViewName("timetable/schoolCourseManager/viewSchoolCourseInfo.jsp");
		return mav;
	}

	/**************************************************************************
	 * @Description 删除上传详情信息
	 * @return ModelAndView
	 * @author 魏好
	 * @date 2018-01-10
	 **************************************************************************/
	@RequestMapping("/timetable/deleteSchoolCourseInfo")
	public String deleteSchoolCourseInfo(@RequestParam String courseNumber) {
		// 保存
		SchoolCourseInfo schoolCourseInfo = schoolCourseInfoDAO
				.findSchoolCourseInfoByPrimaryKey(courseNumber);
		schoolCourseInfoDAO.remove(schoolCourseInfo);
		return "redirect:/timetable/listSchoolCourseInfo?currpage=1";
	}

	/**************************************************************************
	 * @选课组首页
	 * @作者：戴昊宇
	 * @日期：2018-03-23
	 **************************************************************************/
	@RequestMapping("/timetable/schoolCourseAdmin")
	public ModelAndView schoolCourseAdmin() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("timetable/timetableAdmin/listCourseAdmin.jsp");
		return mav;

	}

	/********************************************************************************
	 * Description: 我的排课
	 * @author: 戴昊宇
	 * @date: 2018-04-03
	 *********************************************************************************/
	@SuppressWarnings("unchecked")
	@RequestMapping("/timetable/mylistCourseDetails")
	public ModelAndView mylistCourseDetails(HttpServletRequest request, @RequestParam int currpage,
											@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		// 查询条件
		// 所有学期
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", schoolTerms);
		// 当前学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		// 所选学期
		mav.addObject("term", term);
		// 教师
		String teacherUsername = request.getParameter("teacher");
		if (!EmptyUtil.isStringEmpty(teacherUsername)) {
			mav.addObject("teacherUsername", teacherUsername);
		}
		// 课程编号
		String courseNumber = request.getParameter("courseNumber");
		if (!EmptyUtil.isStringEmpty(courseNumber)) {
			mav.addObject("courseNumber", courseNumber);
		}
		// 所有选课组
		//去重课程名与课程编码
		List<Object[]> listCourseDetails = mySQLService.mylistCourseDetails(request,acno,1, -1);
		mav.addObject("courseDetailAll", listCourseDetails);

		Map<String,String> courseName = new HashMap<String, String>();
		for(Object[] lc:listCourseDetails){
			courseName.put(lc[1].toString(), lc[0].toString());
		}
		mav.addObject("courseName", courseName);
		//去重上课教师
		Map<String,String> courseTeacher = new HashMap<String, String>();
		for(Object[] lc:listCourseDetails){
			courseTeacher.put(lc[5].toString(), lc[4].toString());
		}
		mav.addObject("courseTeacher", courseTeacher);

		// 分页
		// 设置分页变量并赋值为20；
		int pageSize = CommonConstantInterface.INT_PAGESIZE;
		// 根据课程及id获取课程排课列表的计数
		int totalRecords = mySQLService.countmyCourseDetails(request,acno);
//		int totalRecords = listCourseDetails.size();
		// 分页模块映射
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", currpage);
		// 选课组列表呈现
		List<Object[]> schoolCourseDetails = mySQLService.mylistCourseDetails(request,acno, currpage, pageSize);
		mav.addObject("courseDetailPage", schoolCourseDetails);

		// 从课程寻找预约
		String[] currentStates = new String[schoolCourseDetails.size()];
		int i = 0;
		for(Object[] os: schoolCourseDetails){
			List<TimetableAppointment> timetableAppointmentList = timetableAppointmentService.findAppointmentByCourseCode((String) os[1]);
			List<TimetableBatch> timetableBatchList = timetableBatchService.getTimetableBatchByCourseCode((String) os[1]);
			List<TimetableAppointmentCycle> timetableAppointmentCycleList = timetableAppointmentSaveService.findCycleTimetableBycourseNo((String) os[1]);
			if((timetableAppointmentList == null
					|| timetableAppointmentList.size() == 0)
					&& (timetableBatchList == null
					|| timetableBatchList.size() == 0)
					&& (timetableAppointmentCycleList == null
					|| timetableAppointmentCycleList.size() == 0)
					){
				currentStates[i++] = "未排课";
			}else {
				if (timetableAppointmentList != null) {
					for (TimetableAppointment t:
							timetableAppointmentList) {
						if(t.getStatus() == ConstantInterface.TIMETABLE_STATUS_NO_PUBLIC){
							currentStates[i] = "已确认";
							break;
						}
					}
				}
				if(currentStates[i] == null){
					if(timetableAppointmentList != null
							&& timetableAppointmentList.size() > 0
							&& timetableAppointmentList.get(0).getStatus() == ConstantInterface.TIMETABLE_STATUS_NO_CONFIRM
							&& (timetableAppointmentList.get(0).getTimetableStyle() == 2
							|| timetableAppointmentList.get(0).getTimetableStyle() == 3)){
						currentStates[i] = "教务排课中";
					}
					if(timetableBatchList != null
							&& timetableBatchList.size() > 0){
						currentStates[i] = "分组排课中";
					}
					if(timetableAppointmentCycleList != null
							&& timetableAppointmentCycleList.size() > 0){
						currentStates[i] = "循环排课中";
					}
				}
				i++;
			}
		}
		mav.addObject("currentStates", currentStates);

		mav.setViewName("timetable/special/mylistCourseDetails.jsp");
		return mav;
	}
	/********************************************************************************
	 * Description: 排课-排课首页{左侧选课组列表显示}
	 * @author: 戴昊宇
	 * @date: 2017-08-25
	 *********************************************************************************/
	@SuppressWarnings("unchecked")
	@RequestMapping("/timetable/listCourseDetails1")
	public ModelAndView listCourseDetails(HttpServletRequest request, @RequestParam int currpage,
										  @ModelAttribute("selected_academy") String acno,Integer flag) {
		ModelAndView mav = new ModelAndView();
		// 查询条件
		// 所有学期
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", schoolTerms);
		// 当前学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		// 所选学期
		mav.addObject("term", term);
		// 教师
		String teacherUsername = request.getParameter("teacher");
		if (!EmptyUtil.isStringEmpty(teacherUsername)) {
			mav.addObject("teacherUsername", teacherUsername);
		}
		// 课程编号
		String courseNumber = request.getParameter("courseNumber");
		if (!EmptyUtil.isStringEmpty(courseNumber)) {
			mav.addObject("courseNumber", courseNumber);
		}
		//去重课程名与课程编码
		List<Object[]> listCourseDetails = mySQLService.listCourseDetails(request,acno, 1, -1,flag);
		// 所有选课组
		mav.addObject("courseDetailAll",listCourseDetails);

		Map<String,String> courseName = new HashMap<String, String>();
		for(Object[] lc:listCourseDetails){
			courseName.put(lc[1].toString(), lc[0].toString());
		}
		mav.addObject("courseName", courseName);
		//去重上课教师
		Map<String,String> courseTeacher = new HashMap<String, String>();
		for(Object[] lc:listCourseDetails){
			courseTeacher.put(lc[5].toString(), lc[4].toString());
		}
		mav.addObject("courseTeacher", courseTeacher);

		// 分页
		// 设置分页变量并赋值为20；
		int pageSize = CommonConstantInterface.INT_PAGESIZE;
		// 根据课程及id获取课程排课列表的计数
		//int totalRecords = mySQLService.countCourseDetails(request, cid,flag);
		int totalRecords = listCourseDetails.size();
		mav.addObject("weekdayMap", shareService.getWeekdays());
		// 分页模块映射
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", currpage);
		mav.addObject("flag", flag);
		// 选课组列表呈现
		List<Object[]> schoolCourseDetails = mySQLService.listCourseDetails(request,acno, currpage, pageSize,flag);
		mav.addObject("courseDetailPage", schoolCourseDetails);

		mav.setViewName("timetable/special/listCourseDetails.jsp");
		return mav;
	}

	/********************************
	 * 新建课程
	 * @author 魏好
	 * @date 2018-01-10
	 *********************************/
	@RequestMapping("/timetable/newSchoolCourse")
	public ModelAndView newSchoolCourse(@RequestParam int flag) {
		ModelAndView mav = new ModelAndView();
		//全部老师，课程，学期
		Set<SchoolTerm> schoolTermSet = schoolTermDAO.findAllSchoolTerms();
		List<User> teacherList = shareService.findAllTeacheres();
		mav.addObject("schoolTermList",schoolTermSet);
		mav.addObject("teacherList",teacherList);
		mav.addObject("isEdit", true);// isEdit标记，新建true 编辑 false
		mav.addObject("schoolCourse", new SchoolCourse());
		//学期
		//课程库课程
		List<SchoolCourseInfo> schoolCourseInfoList = schoolCourseInfoService.findSchoolCourseInfo(flag);
		mav.addObject("schoolCourseInfoList",schoolCourseInfoList);
	/*	//自动生成的编号
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String atuonumber=String.valueOf(year)+String.valueOf(month)+String.valueOf(day);
		int size = schoolCourseDAO.findAllSchoolCourses().size();
		String courseNo=atuonumber+String.valueOf(size);*/
		//实验室
		List<LabRoom> labRooms = labRoomService.findAllLabroom();
		mav.addObject("labRooms",labRooms);
		//课程类型
		mav.addObject("flag", flag);
		//所有教师
		List<User> teacheres = shareService.findAllTeacheres();
		mav.addObject("teacheres",teacheres);
		mav.addObject("courseStudents","无");
		mav.setViewName("timetable/schoolCourseManager/editSchoolCourse1.jsp");
		return mav;
	}

	/**************************************************************************
	 * @Description 保存课程
	 * @return ModelAndView
	 * @author 戴昊宇
	 * @date 2018-03-27
	 **************************************************************************/
	@RequestMapping("/timetable/saveSchoolCourse")
	public String saveSchoolCourse(
			@ModelAttribute SchoolCourse schoolCourse,HttpServletRequest request,int count,boolean isEdit,int flag) {
		ModelAndView mav = new ModelAndView();
		// 保存选课组主表
		SchoolCourse saveSchoolCourse = schoolCourseService.saveSchoolCourse(schoolCourse,request);
		List<SchoolCourseDetail>scds=new ArrayList<SchoolCourseDetail>();
		String courseNo = saveSchoolCourse.getCourseNo();
		// 获得开始周
		String[] startWeek =request.getParameterValues("startWeek");
		// 获得结束周
		String[] endWeek =request.getParameterValues("endWeek");
		// 获得星期
		String[] weekday =request.getParameterValues("weekday");
		// 开始节次
		String[] startClass =request.getParameterValues("startClass");
		// 结束节次
		String[] endClass =request.getParameterValues("endClass");
		for(int i=0;i<count;i++){
			SchoolCourseDetail schoolCourseDetail =
					schoolCourseDetailService.saveSchoolCourseDetail(courseNo,
							Integer.parseInt(startWeek[i]), Integer.parseInt(endWeek[i]), Integer.parseInt(weekday[i]), Integer.parseInt(startClass[i]),
							Integer.parseInt(endClass[i]),saveSchoolCourse.getSchoolTerm(),i);
			scds.add(schoolCourseDetail);
		}
		// 保存学生
		if(request.getParameter("students")!=null&&request.getParameter("students")!=""){
			System.out.println(request.getParameter("students"));
			scds=schoolCourseDetailService.findSchoolCourseDetailByCourseNO(schoolCourse.getCourseNo());
			for(SchoolCourseDetail s:scds){
				String students = request.getParameter("students");
				students = students.replaceAll("\t", "");
				String[] sStudents = students.replaceAll("\r\n", ";").split(";");
				for (String student : sStudents) {
					if (student == null || student.equals("") || student.equals("\t")) {
						continue;
					}
					//新建学生信息
					SchoolCourseStudent scs= new SchoolCourseStudent();
					User user = userDAO.findUserByUsername(student);
					if (user != null) {
						scs.setSchoolCourseDetail(s);
						scs.setUserByStudentNumber(user);
						scs.setSchoolTerm(s.getSchoolTerm());
						scs.setSchoolAcademy(saveSchoolCourse.getSchoolAcademy());
						scs.setState(1);
					}
					schoolCourseStudentDAO.store(scs);
				}
			}
		}
		if(isEdit) {
			return "redirect:/timetable/editSchoolCourse?courseNo=" + courseNo;
		}else {
			return "redirect:/timetable/listCourseDetails1?currpage=1&id=-1&flag="+flag;
		}
	}
	/**************************************************************************
	 * @Description 编辑课程
	 * @return ModelAndView
	 * @author 魏好
	 * @date 2018-01-10
	 **************************************************************************/
	@RequestMapping("/timetable/editSchoolCourse")
	public ModelAndView editSchoolCourse(@RequestParam String courseNo) {
		ModelAndView mav = new ModelAndView();

		SchoolCourseDetail schoolCourseDetail = schoolCourseDetailDAO.findSchoolCourseDetailByCourseDetailNo(courseNo);

		SchoolCourse schoolCourse = schoolCourseDetail.getSchoolCourse();
//		SchoolCourse schoolCourse = schoolCourseDAO
//				.findSchoolCourseByCourseNo(courseNo);
		Set<SchoolTerm> schoolTermSet = schoolTermDAO.findAllSchoolTerms();
		Set<SchoolCourseInfo> schoolCourseInfoSet = schoolCourseInfoDAO.findAllSchoolCourseInfos();
		List<User> teacherList = shareService.findAllTeacheres();
		//所有教师
		List<User> teacheres = shareService.findAllTeacheres();
		mav.addObject("teacheres",teacheres);
		mav.addObject("schoolTermList",schoolTermSet);
		mav.addObject("schoolCourseInfoList",schoolCourseInfoSet);
		mav.addObject("teacherList",teacherList);
		mav.addObject("schoolCourse", schoolCourse);
		mav.addObject("courseNo", schoolCourse.getCourseNo());
		mav.addObject("schoolCourseDetails", schoolCourse.getSchoolCourseDetails());
		//课程类型
		String flag = schoolCourse.getCourseType();
		mav.addObject("flag",flag);
		mav.addObject("isEdit", false); // isEdit标记，新建true 编辑 false
//		if(schoolCourse.getSchoolCourseDetails()!=null&&schoolCourse.getSchoolCourseDetails().size()>0){
//			SchoolCourseDetail schoolCourseDetail = schoolCourse.getSchoolCourseDetails().iterator().next();
			//开始周
			mav.addObject("Sweek",schoolCourseDetail.getStartWeek());
			//结束周
			mav.addObject("Eweek",schoolCourseDetail.getEndWeek());
			//开始节次
			mav.addObject("Sclass",schoolCourseDetail.getStartClass());
			//结束节次
			mav.addObject("Eclass",schoolCourseDetail.getEndClass());
			//星期
			mav.addObject("Wday",schoolCourseDetail.getWeekday());
			//实验室
			List<LabRoom> labRooms = labRoomService.findAllLabroom();
			mav.addObject("labRooms",labRooms);
			//选课学生编号
			Set<SchoolCourseStudent> schoolCourseStudents = schoolCourseDetail.getSchoolCourseStudents();

		/*String courseStudents = "";
		for(SchoolCourseStudent ss:schoolCourseStudents ){
			courseStudents+=ss.getUserByStudentNumber().getUsername()+",";
		}
		if(courseStudents!=""){
			courseStudents = courseStudents.substring(0, courseStudents.length()-1);
		}else{
			courseStudents="无";
		}*/
			mav.addObject("schoolCourseStudents",schoolCourseStudents);
//		}
		// 获取当前学院下的年级
		mav.addObject("grade", userDAO.executeQuery("select c from User c where 1=1 group by c.grade"));

		mav.setViewName("timetable/schoolCourseManager/editSchoolCourse1.jsp");
		return mav;
	}

	/****************************************************************************
	 * 功能：课程内容添加
	 * 作者：戴昊宇
	 * 日期：2018-06-23
	 ****************************************************************************/
	@RequestMapping("/timetable/getcourse")
	public void getcourse(Integer count,HttpServletRequest request, HttpServletResponse response)  throws Exception{
		//获得当前学期下的周次
		String str="";
		str+="<tr id='cou"+count+"' name='arrangement'><td>序号："+count+"</td><td>开始周次：<input id='startWeek' name='startWeek'><font color=red>*必填</font></td>";
		str+="<td>结束周次：<input id='endWeek' name='endWeek'><font color=red>*必填</font></td>";
		str+="<td>星期：<input id='weekday' name='weekday'><font color=red>*必填</font></td>";
		str+="<td>开始节次：<input id='startClass' name='startClass'><font color=red>*必填</font></td>";
		str+="<td>结束节次：<input id='endClass' name='endClass'><font color=red>*必填</font></td>";
		str+="<td><input id='delete' type='button'value='删除' onclick='delcourse("+count+")'></td></tr>";

		response.getWriter().write(str);
	}

	/**************************************************************************
	 * @Description 删除上传详情信息
	 * @return ModelAndView
	 * @author 魏好
	 * @date 2018-01-10
	 **************************************************************************/
	@RequestMapping("/timetable/deleteSchoolCourse")
	public String deleteSchoolCourse(@RequestParam String courseNo,int flag,int page) {
		SchoolCourse schoolCourse = schoolCourseDAO
				.findSchoolCourseByCourseNo(courseNo);
		schoolCourseDAO.remove(schoolCourse);
		return "redirect:/timetable/listCourseDetails1?currpage="+page+"&flag="+flag;
	}


    /***********************************************************************************
     * @description：保存实验项目
     * @author 戴昊宇
     * @date：2018-03-20
     * **********************************************************************************/
    @RequestMapping("timetable/saveEditOperationItem")
    public ModelAndView saveEditOperationItem(HttpServletRequest request, @RequestParam Integer itemId/*@ModelAttribute("selected_academy") String acno*/){
        ModelAndView mav=new ModelAndView();
        OperationItem operationItem = operationItemDAO.findOperationItemById(itemId);
        operationItem.setUserByLpCreateUser(shareService.getUserDetail());  //当前登录人
        operationItem.setCreatedAt(Calendar.getInstance());//设置为当前时间
        //保存当前所处的实验类型
		/*if(cid != -1)
		{
			operationItem.setLabCenter(labCenterService.findLabCenterByPrimaryKey(cid));
		}*/
        if(request.getParameter("lpName"+itemId) != null){
            operationItem.setLpName(request.getParameter("lpName"+itemId));
        }
        if(request.getParameter("lpPurposes"+itemId) != null){
            operationItem.setLpPurposes(request.getParameter("lpPurposes"+itemId));
        }
        if(request.getParameter("lpDepartmentHours"+itemId) != null){
            operationItem.setLpDepartmentHours(Integer.parseInt(request.getParameter("lpDepartmentHours"+itemId)));
        }
        if(request.getParameter("labRoomId"+itemId) != null){
            Integer labRoomId = Integer.parseInt(request.getParameter("labRoomId"+itemId));
            LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(labRoomId);
            operationItem.setLabRoom(labRoom);
        }
        operationItem = operationService.saveOperationItem(operationItem);

        mav.setViewName("redirect:/timetable/editSchoolCourseInfo?courseNumber="+operationItem.getSchoolCourseInfo().getCourseNumber());

        return mav;

    }
    /***********************************************************************************
     * @description：保存实验项目
     * @author 戴昊宇
     * @date：2018-03-20
     * **********************************************************************************/
    @RequestMapping("/timetable/saveOperationItemforxidlims")
    public ModelAndView saveOperationItemforxidlims(HttpServletRequest request,@ModelAttribute OperationItem operationItem,@RequestParam String courseNumber){
        ModelAndView mav=new ModelAndView();
        operationItem.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "3"));  //无需审核，全部审核通过
        operationItem.setUserByLpCreateUser(shareService.getUserDetail());  //当前登录人
        operationItem.setCreatedAt(Calendar.getInstance());//设置为当前时间
        SchoolCourseInfo courseInfo = schoolCourseInfoDAO.findSchoolCourseInfoByCourseNumber(courseNumber);
        operationItem.setSchoolCourseInfo(courseInfo);
        operationItem = operationService.saveOperationItem(operationItem);
        mav.setViewName("redirect:/timetable/editSchoolCourseInfo?courseNumber="+courseInfo.getCourseNumber());
        return mav;

    }

    /****************************************************************************
     * description：删除实验项目
     * @author：戴昊宇
     * @date：2018-03-22
     ****************************************************************************/
    @RequestMapping("/timetable/deleteOperationItem")
    public String deleteOperationItem(HttpServletRequest request,@RequestParam int operationItemId){
        OperationItem operationItem = operationItemDAO.findOperationItemById(operationItemId);
		/*SchoolCourseInfo schoolCourseInfo = operationItem.getSchoolCourseInfo();
		List<OperationItem> findOperationItemBycourseNumber = operationService.findOperationItemBycourseNumber(schoolCourseInfo.getCourseNumber());*/
        operationService.deleteOperationItem(operationItemId);
        return "redirect:/timetable/editSchoolCourseInfo?courseNumber="+operationItem.getSchoolCourseInfo().getCourseNumber();
    }

    /**************************************************************************
     * @Description 删除上传详情信息
     * @return ModelAndView
     * @author 魏好
     * @date 2018-01-10
     **************************************************************************/
    @RequestMapping("/timetable/deleteCoursedetail")
    public String deleteCoursedetail(@RequestParam String coursedetailNo,String courseNo) {
        // 保存
        SchoolCourseDetail findSchoolCourseDetailByCourseDetailNo = schoolCourseDetailDAO.findSchoolCourseDetailByCourseDetailNo(coursedetailNo);
        schoolCourseDetailDAO.remove(findSchoolCourseDetailByCourseDetailNo);
        return "redirect:/timetable/editSchoolCourse?courseNo="+courseNo;
    }

	@RequestMapping("/timetable/allTimetableAppointment")
	public ModelAndView allTimetableAppointment(HttpServletRequest request, @ModelAttribute("selected_academy") String acno){
    	ModelAndView mav = new ModelAndView();
		// 获取学期列表
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", schoolTerms);
		// 当前时间的学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term")!=null&&!request.getParameter("term").equals("null")) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		mav.addObject("term", term);


    	String labRoom = request.getParameter("labRoom");
		List<LabRoom> labRooms = labRoomDAO.executeQuery("select distinct l from LabRoom l join l.timetableLabRelateds tlr where tlr.timetableAppointment.schoolTerm.id=" + term);
		mav.addObject("labRooms", labRooms);
		List<SchoolAcademy> schoolAcademies = shareService.findAllSchoolAcademys();
		mav.addObject("schoolAcademies", schoolAcademies);
    	String sql = "select distinct ta from TimetableAppointment ta left join ta.timetableLabRelateds tlr where 1=1 ";
    	if(labRoom != null && !labRoom.equals("") && !labRoom.equals("-1")){
    		sql += " and tlr.labRoom.id = " + request.getParameter("labRoom");
		}else if(!"-1".equals(labRoom) && labRooms != null && labRooms.size() > 0){
			sql += " and tlr.labRoom.id = " + labRooms.get(0).getId();
			labRoom = labRooms.get(0).getId().toString();
		}
    	mav.addObject("labRoom", labRoom);
		if(request.getParameter("academyNumber") != null && !request.getParameter("academyNumber").equals("") && !request.getParameter("academyNumber").equals("-1")){
			sql += " and (ta.schoolCourse.schoolAcademy.academyNumber = " + request.getParameter("academyNumber")
			     + " or ta.timetableSelfCourse.schoolAcademy.academyNumber = " + request.getParameter("academyNumber") + ")";
		}
		sql += " and ta.schoolTerm.id=" + term;
    	List<TimetableAppointment> timetableAppointment = timetableAppointmentDAO.executeQuery(sql);
    	mav.addObject("timetableAppointment", timetableAppointment);
    	mav.setViewName("timetable/allTimetableAppointment/allTimetableAppointment.jsp");
    	return mav;
	}

	/**
	 * Description 新建自主课程
	 * @param acno
	 * @return
	 * @author 陈乐为 2019年6月13日
	 */
	@RequestMapping("/timetable/newSchoolCourseInfoForSelf")
	public ModelAndView newSchoolCourseInfoForSelf(@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();

		//当前登录人所在学院
		User user = shareService.getUserDetail();
		SchoolAcademy schoolAcademy = user.getSchoolAcademy();
		mav.addObject("schoolAcademy", schoolAcademy);

		// 课程编号
		int course_no = schoolCourseInfoService.getSelfSchoolCourseInfoTotalRecords();
		mav.addObject("course_number", "self-"+ acno +"-"+ course_no);
		mav.setViewName("timetable/schoolCourseManager/editSchoolCourseInfoForSelf.jsp");
		return mav;
	}

	/**
	 * Description 保存自主课程
	 * @return
	 * @author 陈乐为 2019-6-24
	 */
	@ResponseBody
	@RequestMapping("/timetable/saveSchoolCourseInfoForSelf")
	public String saveSchoolCourseInfoForSelf(@ModelAttribute("selected_academy") String acno, String courseName, String courseNumber) {
		SchoolCourseInfo courseInfo = new SchoolCourseInfo();
		courseInfo.setCourseName(courseName);
		courseInfo.setCourseNumber(courseNumber);
		courseInfo.setAcademyNumber(acno);
		courseInfo.setCreatedAt(Calendar.getInstance());
		courseInfo.setUpdatedAt(Calendar.getInstance());
		courseInfo.setFlag(1);
		courseInfo.setSchoolMajor(null);
		courseInfo.setSchoolTerms(null);
		courseInfo.setUsers(null);
		// 保存
		schoolCourseInfoService.saveSchoolCourseInfoForSelf(courseInfo);
		return "success";
	}
}
