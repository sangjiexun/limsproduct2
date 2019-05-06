package net.zjcclims.web.timetable;

import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.timetable.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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
@Controller("TimetableAllAjaxController")
@SessionAttributes("selected_academy")
public class TimetableAllAjaxController<JsonResult> {
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
	private LabCenterDAO labCenterDAO;
	@Autowired
	private SchoolCourseDetailService schoolCourseDetailService;
	@Autowired
	private ShareService shareService;
	@Autowired
	private SchoolCourseService schoolCourseService;
	@Autowired
	private TimetableReSchedulingService timetableReSchedulingService;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private TimetableCourseSchedulingService timetableCourseSchedulingService;
	@Autowired
	private SchoolCourseDetailDAO schoolCourseDetailDAO;
	@Autowired
	private TimetableCommonService timetableCommonService;
	@Autowired
	private TimetableGroupDAO timetableGroupDAO;
	@Autowired
	private TimetableGroupStudentsDAO timetableGroupStudentsDAO;
	@Autowired
	private TimetableBatchDAO timetableBatchDAO;
	@Autowired
	private TimetableSelfCourseDAO timetableSelfCourseDAO;
	@Autowired
	private TimetableAppointmentDAO timetableAppointmentDAO;
	@Autowired
	private SoftwareDAO softwareDAO;
	@Autowired
	private LabRoomDAO labRoomDAO;
	
	/************************************************************
	 * @功能：二次排课-进行周次过滤及去重的ajax调用
	 * @作者：魏诚
	 * @日期：2014-09-01
	 ************************************************************/
	@RequestMapping("/timetable/reTimetable/getValidWeeksMap")
	public @ResponseBody
	String getTimetableWeeksMap(@RequestParam int term, int weekday, int[] labrooms, int[] classes) {
		// 返回可用的星期信息
		return timetableReSchedulingService.getValidWeeksMap(term, weekday, labrooms, classes);
	}
	/************************************************************
	 * @功能：二次排课-进行周次过滤及去重的ajax调用--编辑排课记录
	 * @作者：贺子龙
	 * @日期：2016-01-06
	 ************************************************************/
	@RequestMapping("/timetable/reTimetable/getValidWeeksMapEdit")
	public @ResponseBody
	String getValidWeeksMapEdit(@RequestParam int term, int weekday, int[] labrooms, int[] classes, Integer tableAppId) {
		// 返回可用的星期信息
		return timetableReSchedulingService.getValidWeeksMap(term, weekday, labrooms, classes, tableAppId);
	}
	
	
	
	/************************************************************
	 * @功能：二次排课-返回实验项目相关的可用星期信息的ajax调用
	 * @作者：魏诚
	 * @日期：2014-09-01
	 ************************************************************/
	@RequestMapping("/timetable/reTimetable/getValidItemMap")
	public @ResponseBody
	String getValidItemMap(@RequestParam int itemid) {
		// 返回实验项目相关的可用星期信息
		return timetableReSchedulingService.getValidItemMap(itemid);
	}
	
	
	/************************************************************
	 * @功能：二次排课-进行设备联动的ajax调用
	 * @作者：贺子龙
	 * @日期：2015-10-27
	 ************************************************************/
	@RequestMapping("/timetable/reTimetable/getLabroomDeviceMap")
	public @ResponseBody
	String getLabroomDeviceMap(@RequestParam int[] labrooms) {
		// 返回实验室设备信息
		return shareService.htmlEncode(timetableReSchedulingService.getLabroomDeviceMap(labrooms));
	}

	/************************************************************
	 * @throws UnsupportedEncodingException 
	 * @功能：二次排课-进行学期及选择组查询同步
	 * @作者：魏诚
	 * @日期：2014-12-26
	 ************************************************************/
	@RequestMapping(value="/timetable/getAdminCourseCodeList", produces="application/json;charset=utf-8")
	public @ResponseBody
	String getAdminCourseCodeList(@RequestParam int term, @ModelAttribute("selected_academy") String acno) throws UnsupportedEncodingException {
		// 返回可用的星期信息
		return URLDecoder.decode(timetableAppointmentService.getAdminCourseCodeList(term, acno),"UTF-8");
	}

	/************************************************************
	 * @自建选课组-获取班级信息
	 * @作者：魏诚
	 * @日期：2015-07-09
	 ************************************************************/
	@RequestMapping("/timetable/selfTimetable/getSchoolClasses")
	public @ResponseBody
	Map<String, String> getSchoolClasses(@RequestParam String grade, @ModelAttribute("selected_academy") String acno) {
		// 返回可用的星期信息
		Map<String, String> map = new HashMap<String, String>();
		for (User user : userDAO.executeQuery("select c from User c where  c.grade like '" + grade + "' group by c.schoolClasses.classNumber order by c.schoolClasses.className ")) {
			if(user.getSchoolClasses()!=null){
				map.put(user.getSchoolClasses().getClassNumber(), user.getSchoolClasses().getClassName());

			}
		}
		return map;
	}
	
	/************************************************************
	 * @自建选课组-获取班级学生信息
	 * @作者：魏诚
	 * @日期：2015-07-09
	 ************************************************************/
	@RequestMapping("/timetable/selfTimetable/getSchoolClassesUser")
	public @ResponseBody
	Map<String, String> getSchoolClassesUser(@RequestParam String classNumber, @ModelAttribute("selected_academy") String acno) {
		// 返回可用的星期信息
		Map<String, String> map = new HashMap<String, String>();
		for (User user : userDAO.executeQuery("select c from User c where  c.schoolClasses.classNumber like '" + classNumber + "' order by c.username ",0,-1)) {
			if(user.getSchoolClasses()!=null){
				map.put(user.getUsername(), user.getCname());
			}
		}
		return map;
	}
	
	/************************************************************
	 * @功能：教务排课-进行调整排课:获取可用的星期数据的ajax调用
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/getTimetableWeeksMap")
	public @ResponseBody
	String getTimetableWeeksMap(@RequestParam String weekday, String[] labrooms, String courseCode) {
		// 返回可用的星期信息
		return schoolCourseDetailService.getValidWeek(Integer.parseInt(weekday), labrooms, courseCode);

	}
	
	/************************************************************
	 * @功能：教务排课-进行调整排课:获取可用的节次数据的ajax调用
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/getTimetableClassesMap")
	public @ResponseBody
	String getTimetableClassesMap(@RequestParam int term,String weekday, String courseCode) {
		// 返回可用的节次信息
		String jsonClass = timetableCourseSchedulingService.getTimetableClassesMap(term,weekday, courseCode);
		return jsonClass;
	}
	/************************************************************
	 * @教务排课-进行调整排课:获取可用的节次数据(针对教务排课--调整排课)
	 * @页面跳转：在页面listAdjustTimetableTerm.jsp的ajax调用
	 * @作者：魏诚
	 * @日期：2014-07-25
	 ************************************************************/
	@RequestMapping("/timetable/getTimetableClassesMapNew")
	public @ResponseBody
	String getTimetableClassesMapNew(@RequestParam int term,String weekday, String courseCode,String startClass) {
		// 返回可用的节次信息
		String jsonClass = timetableCourseSchedulingService.getTimetableClassesMap(term,weekday, courseCode,startClass);
		return jsonClass;
	}
	/************************************************************
	 * @throws UnsupportedEncodingException 
	 * @二次排课-进行学期及选择组查询同步
	 * @作者：魏诚
	 * @日期：2014-12-26
	 ************************************************************/
	@RequestMapping(value="/timetable/getCourseCodeList", produces="application/json;charset=utf-8")
	public @ResponseBody
	String getCourseCodeList(@RequestParam int term, @ModelAttribute("selected_academy") String acno) throws UnsupportedEncodingException {
		// 返回可用的星期信息
		return URLDecoder.decode(schoolCourseService.getCourseCodeList(term, acno),"UTF-8");
	}
	
	/************************************************************
	 * @教务直接排课-进行判冲
	 * @作者：魏诚
	 * @日期：2015-03-10
	 ************************************************************/
	@RequestMapping("/timetable/getDirectValid")
	public @ResponseBody
	String getDirectValid(@RequestParam int term, int[] labrooms, String courseCode) {
		// 返回可用的星期信息
		StringBuffer sql = new StringBuffer(
				"select c from SchoolCourseDetail c where  c.schoolTerm.id="
						+ term + " and c.endClass>0  and c.state=1 and c.schoolCourse.courseCode like '%" + courseCode + "%'");
		List<SchoolCourseDetail> courses = schoolCourseDetailDAO.executeQuery(sql.toString());
		for(SchoolCourseDetail schoolCourseDetail:courses){
			int[] classes = new int[schoolCourseDetail.getEndClass()-schoolCourseDetail.getStartClass()+1];
			int[] weeks = new int[schoolCourseDetail.getEndWeek()-schoolCourseDetail.getStartWeek()+1];
			for(int i=0;i<schoolCourseDetail.getEndClass()-schoolCourseDetail.getStartClass()+1;i++){
				classes[i]=schoolCourseDetail.getStartClass()+i;
			}
			for(int i=0;i<schoolCourseDetail.getEndWeek()-schoolCourseDetail.getStartWeek()+1;i++){
				weeks[i]=schoolCourseDetail.getStartWeek()+i;
			}
			if(outerApplicationService.isTimetableConflict(term, schoolCourseDetail.getWeekday(), labrooms, classes,weeks)==false){
				return "no";
			}
		}
		return "ok";
	}
	
	/************************************************************
	 * @throws ParseException 
	 * @功能：判断教务排课是否实验设备冲突-进行判冲
	 * @作者：魏诚
	 * @日期：2015-03-10
	 ************************************************************/
	@RequestMapping("/timetable/academy/getLabroomDeviceValid")
	public @ResponseBody
	String getLabroomDeviceValid(@RequestParam String[] devices, String courseCode) {
		//判断是否实验设备冲突-进行判冲
		try {
			return timetableCommonService.getLabroomDeviceValid(devices, courseCode);
		} catch (ParseException e) {
			return "ok";
		}
	}
	
	/************************************************************
	 * 功能：排课管理--根据教务系统推过来的学生名单，时时更新选课的学生名单（限自动选课）
	 * 作者：贺子龙
	 * 日期：2016-03-12
	 ************************************************************/
	@RequestMapping("/timetable/updateTimetableStudentsGroup")
	public @ResponseBody
	String judgeTeacherTimetable(@RequestParam int groupId) {
		TimetableGroup group = timetableGroupDAO.findTimetableGroupByPrimaryKey(groupId);
		//找不到分组
		if (group==null) {
			return "error";
		}
		else {
			Set<TimetableAppointment> appointments = group.getTimetableAppointments();
			//分组无排课
			if (appointments.size()==0) {
				return "error";
			}else {
				//分组有排课
				List<TimetableAppointment> appointmentList = new ArrayList<TimetableAppointment>(appointments);
				//分组对应的其中一个排课，通过这一排课找到对应的schoolCourseDetail
				TimetableAppointment appointment = appointmentList.get(0);
				SchoolCourseDetail schoolCourseDetail = appointment.getSchoolCourseDetail();
				//schoolCourseDetail对应的学生数量
				int studentNumber = schoolCourseDetail.getSchoolCourseStudents().size();
				//分组对应的批次
				TimetableBatch batch = group.getTimetableBatch();
				//批次下面的分组数量
				int groupNumber = batch.getTimetableGroups().size();
				//重新设置每个分组里面学生的数量
				int count=0;//计数器
				for (TimetableGroup timetableGroup : batch.getTimetableGroups()) {
					count = count+1;
					//平均数
					int average = studentNumber/groupNumber;
					if (count!=groupNumber) {
						timetableGroup.setNumbers(average);
						timetableGroupDAO.store(timetableGroup);
					}else {
						//最后一组放剩下的学生
						timetableGroup.setNumbers(studentNumber-average*(groupNumber-1));
						timetableGroupDAO.store(timetableGroup);
					}
					//删除该分组现有学生
					Set<TimetableGroupStudents> students = timetableGroup.getTimetableGroupStudentses();
					for (TimetableGroupStudents timetableGroupStudents : students) {
						timetableGroupStudentsDAO.remove(timetableGroupStudents);
						timetableGroupStudentsDAO.flush();
					}
				}
				
				
				
				//重新分配学生
				Iterator<SchoolCourseStudent> iterator = null;
				for (TimetableGroup timetableGroup : batch.getTimetableGroups()) {
	                if (timetableGroup.getTimetableAppointments() == null) {
	                    continue;
	                }
	                if (iterator == null&&timetableGroup.getTimetableAppointments().size()>0) {
	                    iterator = timetableGroup.getTimetableAppointments().iterator().next().getSchoolCourseDetail()
	                            .getSchoolCourseStudents().iterator();
	                }
	                if(iterator!=null&&iterator.hasNext()){
		                for (int i = 1; i <= timetableGroup.getNumbers(); i++) {
		                    TimetableGroupStudents timetableGroupStudents = new TimetableGroupStudents();
		                    timetableGroupStudents.setUser(iterator.next().getUserByStudentNumber());
		                    timetableGroupStudents.setTimetableGroup(timetableGroup);
		                    timetableGroupStudentsDAO.store(timetableGroupStudents);
		                    timetableGroupStudentsDAO.flush();
		                }
	                }
	            }
			}
		}
		return "success";
	}
	
	/************************************************************
	 * 功能：排课管理--分组管理--修改分批时间
	 * 作者：贺子龙
	 * 日期：2016-03-21
	 * @throws ParseException 
	 ************************************************************/
	@RequestMapping("/timetable/altBatchTime")
	public @ResponseBody
	String altBatchTime(@RequestParam int batchId,HttpServletRequest request) throws ParseException {
		//找到对应的分批
		TimetableBatch batch = timetableBatchDAO.findTimetableBatchByPrimaryKey(batchId);
		if (batch==null) {
			return "error";
		}
		if (request.getParameter("begintime")==null||request.getParameter("begintime").equals("")
				||request.getParameter("endtime")==null||request.getParameter("endtime").equals("")) {
			return "error";
		}
		//字符串转日期
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Date start =sdf.parse(request.getParameter("begintime"));//从前台将begintime取出来，然后转化成calendar格式
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(start);
		
		Date end =sdf.parse(request.getParameter("endtime"));//从前台将endtime取出来，然后转化成calendar格式
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(end);
		batch.setStartDate(startDate);
		batch.setEndDate(endDate);
		
		timetableBatchDAO.store(batch);
		
		return "success";
	}
	
	/************************************************************
	 * 功能：排课管理--分组管理--修改分组人数
	 * 作者：贺子龙
	 * 日期：2016-04-25
	 ************************************************************/
	@RequestMapping("/timetable/saveNumbers")
	public @ResponseBody String saveNumbers(@RequestParam int groupId, int number){
		//找到对应的分组
		TimetableGroup group = timetableGroupDAO.findTimetableGroupByPrimaryKey(groupId);
		group.setNumbers(number);
		timetableGroupDAO.store(group);
		return "success";
	}
	
	/************************************************************
	 * @功能：教务排课-教务直接排课，获取ajax的相关lab的设备资源
	 * @作者：魏诚
	 * @日期：2016-04-26
	 ************************************************************/
	@RequestMapping(value="/timetable/getValidLabroomDevice",produces="text/plain;charset=UTF-8")
	public @ResponseBody
	String getValidLabroomDevice(@RequestParam String[] labrooms) {
		// 返回可用的星期信息
		String labRoomDevice = timetableCourseSchedulingService.getValidLabroomDevice(labrooms);

		return labRoomDevice;
	}
	
	/************************************************************
	 * @throws ParseException 
	 * @功能：职校排课--技能鉴定和综合服务排课--筛选可用的实验室
	 * @作者：贺子龙
	 * @日期：2016-03-17
	 ************************************************************/
	@RequestMapping("/timetable/vocationalTimetable/getValidLabRoomsForSkill")
	public @ResponseBody
	String getValidLabRoomsForSkill(@RequestParam Integer selfCourseId,  int[] classes ,Integer weeks,Integer weekday,String coursecode,Integer[] softwares,Integer confinementTime) throws ParseException {
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(selfCourseId!=null){
		TimetableSelfCourse timetableSelfCourse=timetableSelfCourseDAO.findTimetableSelfCourseById(selfCourseId);
		if (timetableSelfCourse!=null&&timetableSelfCourse.getSchoolTerm()!=null) {
			term = timetableSelfCourse.getSchoolTerm().getId();
		 }
		}
		// 返回可用的星期信息
		String roomsForSkill = timetableReSchedulingService.getValidLabRoomsForSkill(term, weeks, weekday, classes,selfCourseId,coursecode,softwares,confinementTime);
		String htmlEncode = shareService.htmlEncode(roomsForSkill);
		return htmlEncode;
	}
	
	/************************************************************

	 * @功能：筛选可用的实验室（软件）
	 * @作者：戴昊宇
	 * @日期：2017-08-04
	 ************************************************************/
	@RequestMapping("/timetable/vocationalTimetable/isSoftWareConfilict")
	public void isSoftWareConfilict(@RequestParam Integer selfCourseId,  int[] classes ,Integer weeks,Integer weekday,Integer[] softwares,HttpServletResponse response,HttpServletRequest request) throws Exception  {
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(selfCourseId!=null){
		TimetableSelfCourse timetableSelfCourse=timetableSelfCourseDAO.findTimetableSelfCourseById(selfCourseId);
		if (timetableSelfCourse!=null&&timetableSelfCourse.getSchoolTerm()!=null) {
			term = timetableSelfCourse.getSchoolTerm().getId();
		  }
		}
		for(Integer softwareid:softwares){
		 Software software = softwareDAO.findSoftwareById(softwareid);
		 if(software.getDongle()==1){
			 int softsize =  timetableReSchedulingService.getLicence(term,  classes, weeks, weekday,softwareid);
			 if(softsize>software.getPoints()){
		    	  response.getWriter().write("false");
			 }
		 }
	       }
			
	}
	
	/************************************************************
	 * @throws UnsupportedEncodingException 
	 * @功能：顺延排课前的合法性判断
	 * @作者：贺子龙
	 * @日期：2016-07-16
	 ************************************************************/
	@RequestMapping("/timetable/timetableAdmin/judgeAlterTimetable")
	public @ResponseBody String judgeAlterTimetable(HttpServletRequest request, @RequestParam int[] appointmentIds, int gapWeek) throws UnsupportedEncodingException{
		
		String result = "success";
		for (int appointmentId : appointmentIds) {
			if (!timetableAppointmentService.isInValidWeeks(appointmentId, gapWeek, request, 0)) {// 记录不能顺延的排课记录
				result = timetableAppointmentService.createAlertForAppointment(appointmentId, gapWeek);
				break;
			}
		}
		return URLEncoder.encode(result, "utf-8");
	}

	/**
	 * Description 删除排课批次
	 * @param request 请求（传参）
	 * @param batchId 批次id
	 * @return 删除成功字符串
	 * @throws UnsupportedEncodingException 不支持的编码方式异常（URLEncoder.encode）
	 * @author 黄保钱 2019-1-23
	 */
	@RequestMapping("/timetable/deleteTimetableBatch")
	public @ResponseBody String deleteTimetableBatch(HttpServletRequest request, @RequestParam String batchId) throws UnsupportedEncodingException{

		String result = "success";
		TimetableBatch t = timetableBatchDAO.findTimetableBatchById(Integer.parseInt(batchId));
		Set<TimetableGroup> timetableGroups = t.getTimetableGroups();
		for(TimetableGroup tg: timetableGroups){
			Set<TimetableAppointment> timetableAppointments = tg.getTimetableAppointments();
			for(TimetableAppointment ta: timetableAppointments){
				timetableAppointmentDAO.remove(ta);
				timetableAppointmentDAO.flush();
			}
		}
		timetableBatchDAO.remove(t);
		timetableBatchDAO.flush();
		return URLEncoder.encode(result, "utf-8");
	}
}