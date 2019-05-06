/**
 * 
 */
package net.zjcclims.web.video;

import excelTools.Labreservationlist;
import net.sf.json.JSONObject;
import net.zjcclims.dao.LabRoomDAO;
import net.zjcclims.dao.SchoolTermDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabReservationService;
import net.zjcclims.service.timetable.TimetableAttendanceService;
import net.zjcclims.service.video.VideoPublishService;
import net.zjcclims.web.common.PConfig;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author 贺子龙
 *
 */
@Controller("VideoPublishController")
public class VideoPublishController<JsonResult> {

	@Autowired
	private LabRoomDAO labRoomDAO;
	@Autowired
	private ShareService shareService;
	@Autowired
	private TimetableAttendanceService timetableAttendanceService;
	@Autowired
	private VideoPublishService videoPublishService;
	@Autowired
	LabReservationService labReservationService;
	@Autowired
	SchoolTermDAO schoolTermDAO;
	@Autowired
	private PConfig pConfig;
	/**
	 * 功能:获取某个实验室的实时上课信息
	 * 参数:
	 * 返回：
	 */
	@RequestMapping("/public/getLabInfo")
	@ResponseBody
	public String getLabInfo(@RequestParam Integer labId) throws ClientProtocolException, IOException {
		
		//当前时间
		Calendar calendar=Calendar.getInstance();
		//当前是周几
		int weekday=calendar.get(Calendar.DAY_OF_WEEK)-1;
		
		String labInfo="开放课程,-,不限,开放,不限,-";//要返回的字符串
		LabRoom labRoom=labRoomDAO.findLabRoomById(labId);//查找实验室
		//System.out.println(labInfo+"+++");
		if(labRoom==null){
			return shareService.htmlEncode(labInfo);
		}//判空处理
		int classSection=videoPublishService.getCurClass();//当前节次
		List<TimetableAppointment> timetableList=videoPublishService.getCurSch(classSection, labId);//获取当前课程	
		//假如此节次无课，就显示下个节次的安排
		if(0==timetableList.size()){//假如现在课程为空，进一步判断是否为开放的实验室
			labInfo="开放课程,-,不限,开放,不限,-";
		}else{
			String courseName="";//课程名称
			String teacher="";//老师
			String classes="未同步";//班级
			int stuNum=0;//应到人数
			String xkkch="";//获取实到人数的url
			for (TimetableAppointment timetableAppointment : timetableList) {
				// 课程名称
				courseName=timetableAppointment.getSchoolCourseInfo().getCourseName();
				// 教师名称
				Set<TimetableTeacherRelated> relateds = timetableAppointment.getTimetableTeacherRelateds();
				if (relateds!=null && relateds.size()>0) {
					for (TimetableTeacherRelated timetableTeacherRelated : relateds) {
						teacher += timetableTeacherRelated.getUser().getCname();
						break;
					}
				}
				xkkch="?scheduleId="+timetableAppointment.getId()+"&week="+shareService.getBelongsSchoolWeek(Calendar.getInstance())+
						"&weekday="+weekday+"&classSection="+classSection;
				if (timetableAppointment.getTimetableStyle() == 5 || timetableAppointment.getTimetableStyle() == 6) {// 自主排课
					classes = "自主排课班级";
					if (!EmptyUtil.isObjectEmpty(timetableAppointment.getTimetableSelfCourse())) {
						stuNum = timetableAppointment.getTimetableSelfCourse().getTimetableCourseStudents().size();
					}
				}else {// 非自主排课
//					if (!EmptyUtil.isObjectEmpty(timetableAppointment.getSchoolCourse())
//							&& !EmptyUtil.isStringEmpty(timetableAppointment.getSchoolCourse().getClassesName())) {
//						classes = timetableAppointment.getSchoolCourse().getClassesName().replace(",", "-");
//					}
					//需要新加字段，先拿来充数。。。
					if(!EmptyUtil.isObjectEmpty(timetableAppointment.getSchoolCourseDetail())&&
							!EmptyUtil.isStringEmpty(timetableAppointment.getSchoolCourseDetail().getCourseClassName())) {
						classes = timetableAppointment.getSchoolCourseDetail().getCourseClassName();
					}
					// classes+="第"+timetableAppointment.getStartClass()+"-"+timetableAppointment.getEndClass()+"节";
					stuNum=timetableAppointment.getSchoolCourseDetail().getSchoolCourseStudents().size();
				}
			}
			labInfo=courseName+","+teacher+","+classes+",有课,"+stuNum+","+xkkch;
		}
		
		return shareService.htmlEncode(labInfo);
	}
	
	/**
	 * 功能:获取某个实验室的实时上课信息
	 * 参数:
	 * 返回：
	 */
	@RequestMapping("/public/getLabInfoPublic")
	@ResponseBody
	public ModelAndView getLabInfoPublic(@RequestParam Integer labId) throws ClientProtocolException, IOException {
		ModelAndView mav = new ModelAndView();
		//当前时间
		Calendar calendar=Calendar.getInstance();
		//当前是周几
		int weekday=calendar.get(Calendar.DAY_OF_WEEK)-1;
		
		String labInfo="开放课程,-,不限,开放,不限,-";//要返回的字符串
		LabRoom labRoom=labRoomDAO.findLabRoomById(labId);//查找实验室
		//System.out.println(labInfo+"+++");
		//if(labRoom==null){return shareService.htmlEncode(labInfo);}//判空处理
		int classSection=videoPublishService.getCurClass();//当前节次
		List<TimetableAppointment> timetableList=videoPublishService.getCurSch(classSection, labId);//获取当前课程	
		//假如此节次无课，就显示下个节次的安排
		if(0==timetableList.size()){//假如现在课程为空，进一步判断是否为开放的实验室
			labInfo="开放课程,-,不限,开放,不限,-";
		}else{
			String courseName="";//课程名称
			String teacher="";//老师
			String classes="未同步";//班级
			int stuNum=0;//应到人数
			String xkkch="";//获取实到人数的url
			for (TimetableAppointment timetableAppointment : timetableList) {
				// 课程名称
				courseName=timetableAppointment.getSchoolCourseInfo().getCourseName();
				// 教师名称
				Set<TimetableTeacherRelated> relateds = timetableAppointment.getTimetableTeacherRelateds();
				if (relateds!=null && relateds.size()>0) {
					for (TimetableTeacherRelated timetableTeacherRelated : relateds) {
						teacher += timetableTeacherRelated.getUser().getCname();
						break;
					}
				}
				xkkch="?scheduleId="+timetableAppointment.getId()+"&week="+shareService.getBelongsSchoolWeek(Calendar.getInstance())+
						"&weekday="+weekday+"&classSection="+classSection;
				if (timetableAppointment.getTimetableStyle() == 5 || timetableAppointment.getTimetableStyle() == 6) {// 自主排课
					classes = "自主排课班级";
					if (!EmptyUtil.isObjectEmpty(timetableAppointment.getTimetableSelfCourse())) {
						stuNum = timetableAppointment.getTimetableSelfCourse().getTimetableCourseStudents().size();
					}
				}else {// 非自主排课
//					if (!EmptyUtil.isObjectEmpty(timetableAppointment.getSchoolCourse())
//							&& !EmptyUtil.isStringEmpty(timetableAppointment.getSchoolCourse().getClassesName())) {
//						classes = timetableAppointment.getSchoolCourse().getClassesName().replace(",", "-");
//					}
					//需要新加字段，先拿来充数。。。
					if(!EmptyUtil.isObjectEmpty(timetableAppointment.getSchoolCourseDetail())&&
							!EmptyUtil.isStringEmpty(timetableAppointment.getSchoolCourseDetail().getCourseClassName())) {
						classes = timetableAppointment.getSchoolCourseDetail().getCourseClassName();
					}
					// classes+="第"+timetableAppointment.getStartClass()+"-"+timetableAppointment.getEndClass()+"节";
					stuNum=timetableAppointment.getSchoolCourseDetail().getSchoolCourseStudents().size();
				}
			}
			labInfo=courseName+","+teacher+","+classes+",有课,"+stuNum+","+xkkch;
		}
		mav.addObject("labRoom", labRoom);
		mav.addObject("labInfo", labInfo);
		mav.setViewName("/visualization/labInfoPublic.jsp");
		return mav;
	}
	
	/**
	 * 功能:查看某个课程的某个周次下的实际考勤人数
	 * @throws ClientProtocolException 
	 */
	@RequestMapping("/public/getRealNumber")
	@ResponseBody
	public int getRealNumber(@RequestParam Integer scheduleId,Integer week,Integer weekday,Integer classSection
			) throws ClientProtocolException, IOException{
		
		return videoPublishService.countStudentAttendanceByWeek(scheduleId, week, weekday, classSection);
	}


	/**
	 * 功能：课表显示
	 * 作者：周志辉
	 * 时间：2017-10-19
	 * @throws ParseException
	 */
	@RequestMapping("/public/timetableByFloor")
	public ModelAndView timetableByFloor(@RequestParam int floor, HttpServletRequest request) throws ParseException {
		ModelAndView mav=new ModelAndView();
		List<TimetableAppointment> timetableList=new ArrayList<TimetableAppointment>();
		List<TimetableAppointment> timetableLists=new ArrayList<TimetableAppointment>();
		List<Integer> labIds=new ArrayList<Integer>();
		List<String> ids=new ArrayList<String>();
		List<LabRoom> listLabRoom=timetableAttendanceService.getAllLabRoomByFloor(floor);
		List<String> classesName=new ArrayList<String>();
		List<String> ItemsName=new ArrayList<String>();
		List<String> StudentNum=new ArrayList<String>();
		for(LabRoom labRoom:listLabRoom){
			labIds.add(labRoom.getId());
		}
		for(Integer id:labIds){
			timetableList=timetableAttendanceService.getCurSch(id);//获取一个实验室的排课信息
			for(int i=0;i<timetableList.size();i++){
				String classNames="";
				//每个排课下的课程所有的班级
				/*Set<SchoolClasses> classes=timetableList.get(i).getSchoolCourse().getSchoolClasseses();*/
				//人数
				Integer studentNum=timetableList.get(i).getSchoolCourseDetail().getSchoolCourseStudents().size();
				for(SchoolCourseStudent s:timetableList.get(i).getSchoolCourseDetail().getSchoolCourseStudents()) {
					String className = s.getSchoolClasses().getClassName();//一个班级名称
					if(classNames.indexOf(className) == -1) {
						classNames += className+";";//一个课程下的所有班级名拼接
					}
					/*for (SchoolClasses sc : classes) {
						String className = sc.getClassName();//一个班级名称
						classNames += className;//一个课程下的所有班级名拼接
					}*/
				}
				//每个排课下的课程所有的实验项目
				String ItemNames="";
				Set<TimetableItemRelated> timetableItemRelateds = timetableList.get(i).getTimetableItemRelateds();
				for(TimetableItemRelated tb:timetableItemRelateds){
					String ItemName = tb.getOperationItem().getLpName();
					ItemNames = ItemName+";";
				}

				classesName.add(classNames);//班级名列表
				ItemsName.add(ItemNames);//实验项目
				StudentNum.add(studentNum.toString());//人数
				timetableLists.add(timetableList.get(i));//获取所有实验室的排课信息
				ids.add(labRoomDAO.findLabRoomById(id).getLabRoomNumber());//实验室编号（101）
			}

		}
		// 获取当前时间
		Date d = new Date();// 获取时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date d1 = sdf.parse(sdf.format(d));
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar calendar=Calendar.getInstance();
		int weekday=calendar.get(Calendar.DAY_OF_WEEK)-1;
		mav.addObject("weekday",weekday);
		mav.addObject("timetableList",timetableLists);
		mav.addObject("classNames",classesName);
		mav.addObject("ItemsName",ItemsName);
		mav.addObject("StudentNum",StudentNum);
		mav.addObject("ids",ids);
		mav.addObject("time",d);
		mav.addObject("floor",floor);
		mav.setViewName("show/showTimetable.jsp");
		return mav;

	}

	/**
	 * 功能：返回课表信息至首页
	 * 作者：汪哲玮
	 * 时间：2017-10-23
	 * @throws ParseException
	 */
	@RequestMapping(value="/public/getTimetableByFloor",produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	public @ResponseBody String getTimetableByFloor(@RequestParam int floor, HttpServletRequest request){
		JSONObject aapJson = new JSONObject();
		String labroom=null;
		String course=null;
		String no=null;
		String classroom=null;


		List<TimetableAppointment> timetableList=new ArrayList<TimetableAppointment>();
		List<TimetableAppointment> timetableLists=new ArrayList<TimetableAppointment>();
		List<Integer> labIds=new ArrayList<Integer>();
		List<String> ids=new ArrayList<String>();
		List<LabRoom> listLabRoom=timetableAttendanceService.getAllLabRoomByFloor(floor);
		List<String> classesName=new ArrayList<String>();
		for(LabRoom labRoom:listLabRoom){
			labIds.add(labRoom.getId());
		}
		for(Integer id:labIds){
			timetableList=timetableAttendanceService.getCurSch(id);//获取一个实验室的排课信息
			for(int i=0;i<timetableList.size();i++){
				String classNames="";
				//每个排课下的课程所有的班级
				Set<SchoolClasses> classes=timetableList.get(i).getSchoolCourse().getSchoolClasseses();
				for(SchoolClasses sc:classes){
					String className=sc.getClassName();//一个班级名称
					classNames+=className;//一个课程下的所有班级名拼接
				}
				classesName.add(classNames);//班级名列表
				timetableLists.add(timetableList.get(i));//获取所有实验室的排课信息
				ids.add(labRoomDAO.findLabRoomById(id).getLabRoomNumber());//实验室编号（101）
			}

		}
		for(int i=0;i<timetableList.size();i++){
			JSONObject aapJson1 = new JSONObject();
			aapJson1.put("labroom","实验室"+ids.get(i) );
			aapJson1.put("course",timetableList.get(i).getSchoolCourse().getCourseName() );
			aapJson1.put("no",timetableList.get(i).getStartClass()+"-"+timetableList.get(i).getEndClass() );
			aapJson1.put("classroom",classesName.get(i) );
			aapJson.put(i, aapJson1);
		}
		String item = "successCallback("+aapJson.toString()+")";
		return item;
	}

	/****************************************************************************
	 * 功能：查询实验室预约 作者：周志辉 时间：2017-10-19
	 ****************************************************************************/
	@RequestMapping("/public/labreservationmanage")
	public ModelAndView labreservationmanage(@ModelAttribute LabReservation labReservation,
											 @RequestParam Integer currpage, int tage) {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		Calendar calendat = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-SS");
		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
		// 查询表单的对象d
		mav.addObject("labReservation", labReservation);
		mav.addObject("flag",0);
		int pageSize = 30;// 每页20条记录
		// 查询出来的总记录条数
		int totalRecords = labReservationService.findLabreservationmanage(labReservation, tage, 1, -1).size();
		// 分页信息
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		// 根据分页信息查询出来的记录
		Set<LabReservation> LabReservations = labReservationService.findLabreservationmanage(labReservation, tage,
				currpage, pageSize);

		List<Labreservationlist> list = new ArrayList<Labreservationlist>();
		for (LabReservation lab : LabReservations) {
			Labreservationlist la = new Labreservationlist();
			la.setId(lab.getId());
			/*if (lab.getCLabReservationType() != null) {
				la.setNametype(lab.getCLabReservationType().getName());
			}*/
			if (lab.getCDictionaryByLabReservetYpe() != null) {
				la.setNametype(lab.getCDictionaryByLabReservetYpe().getCName());
			}
			if (lab.getEventName() != null) {
				la.setName(lab.getEventName());
			} else {
				la.setName(lab.getEnvironmentalRequirements());
			}
//			Set<String> week = new HashSet<String>();
//			Set<String> day = new HashSet<String>();
//			Set<String> time = new HashSet<String>();
//			if (lab.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() > 0)
//				for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : lab.getTimetableAppointment()
//						.getTimetableAppointmentSameNumbers()) {
//					if (timetableAppointmentSameNumber.getStartWeek().intValue() != timetableAppointmentSameNumber.getEndWeek().intValue()) {
//						week.add(timetableAppointmentSameNumber.getStartWeek().toString() + "-"
//								+ timetableAppointmentSameNumber.getEndWeek().toString());
//					} else {
//						week.add(timetableAppointmentSameNumber.getStartWeek().toString());
//
//					}
//					day.add(lab.getTimetableAppointment().getWeekday().toString());
//					if (timetableAppointmentSameNumber.getStartClass().intValue() != timetableAppointmentSameNumber.getEndClass().intValue()) {
//						time.add(timetableAppointmentSameNumber.getStartClass().toString() + "-"
//								+ timetableAppointmentSameNumber.getEndClass().toString());
//					} else {
//						time.add(timetableAppointmentSameNumber.getStartClass().toString());
//					}
//				}
//			else {
//				if (lab.getTimetableAppointment().getStartWeek().intValue() != lab.getTimetableAppointment().getEndWeek().intValue()) {
//					week.add(lab.getTimetableAppointment().getStartWeek().toString() + "-"
//							+ lab.getTimetableAppointment().getEndWeek().toString());
//				}else{
//					week.add(lab.getTimetableAppointment().getStartWeek().toString());
//				}
//
//				day.add(lab.getTimetableAppointment().getWeekday().toString());
//				if (lab.getTimetableAppointment().getStartClass().intValue() != lab.getTimetableAppointment().getEndClass().intValue()) {
//					time.add(lab.getTimetableAppointment().getStartClass().toString() + "-"
//							+ lab.getTimetableAppointment().getEndClass().toString());
//				}else{
//					time.add(lab.getTimetableAppointment().getStartClass().toString());
//
//				}
//
//
//			}
//			int dd = week.size();
//			String[] weeks = week.toArray(new String[dd]);
//			String[] days = day.toArray(new String[day.size()]);
//			String[] timea = time.toArray(new String[time.size()]);
//			;
//			// 数组排序
//			String[] weekt = insertSort(weeks);
//			String[] timet = insertSort(timea);
//
//			la.setWeek(weekt);
//			la.setTime(timet);
//			la.setDay(days);
			//设置实验室
			if (lab.getLabRoom() != null) {
				la.setLab(lab.getLabRoom().getLabRoomName());
				la.setLabRoom(lab.getLabRoom());
			}
			//设置申请者
			if (lab.getUser() != null) {
				la.setUser(lab.getUser());
			}
			if(lab.getAuditResults() != null) {
				la.setCont(lab.getAuditResults());
			}
			la.setStart(lab.getReservations());
			la.setFabu(lab.getItemReleasese());
			la.setUser(lab.getUser());//申请人
			la.setLabRoom(lab.getLabRoom());//实验室
			String stime=sdfd.format(lab.getLendingTime().getTime());
			if(lab.getLabReservationTimeTables().size()!=0){
				StringBuilder startStr = new StringBuilder(stime);
				StringBuilder endStr = new StringBuilder(stime);
				for(LabReservationTimeTable lrtt: lab.getLabReservationTimeTables()) {
					startStr.append(" ").append(sdf.format(lrtt.getStartTime().getTime()));
					endStr.append(" ").append(sdf.format(lrtt.getStartTime().getTime()));
				}
				la.setStartTime(startStr.toString());
				la.setEndTime(endStr.toString());
			}
			String auditUser="";
			Set<LabReservationAudit> audits=lab.getLabReservationAudits();
			if (audits.size()>0) {
				for (LabReservationAudit labReservationAudit : audits) {
					auditUser=labReservationAudit.getUser().getCname()+",";
				}
			}
			if (auditUser.length()>0) {
				auditUser=auditUser.substring(0, auditUser.length()-1);
			}
			la.setAuditUser(auditUser);
			//当前用户是否能审核
//			int isAudit=0;
//			Set<LabRoomAdmin> admins=lab.getLabRoom().getLabRoomAdmins();
//			if (admins.size()>0) {
//				for (LabRoomAdmin labRoomAdmin : admins) {
//					if (labRoomAdmin.getTypeId()==1&&labRoomAdmin.getUser().getUsername()==shareService.getUser().getUsername()) {
//						isAudit=1;break;
//					}
//				}
//			}
//			la.setIsAudit(isAudit);
			list.add(la);

		}

		mav.addObject("labReservations", list);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("currpage", currpage);
		mav.addObject("pageSize", pageSize);
		mav.addObject("tage", tage);
		SchoolTerm shoolTerm = new SchoolTerm();
		List<SchoolTerm> set = new ArrayList<SchoolTerm>();
		set.addAll(labReservationService.timemap());
		if (labReservation.getRemarks() != null && labReservation.getRemarks().trim() != "") {
			shoolTerm = schoolTermDAO.findSchoolTermById(Integer.parseInt(labReservation.getRemarks()));
			mav.addObject("shoolTerm", shoolTerm);
			set.remove(shoolTerm);
		}

		mav.addObject("timemap", set);
		//登录用户
		mav.addObject("user", shareService.getUserDetail());
		mav.setViewName("/show/labreaervationmanageList.jsp");
		return mav;
	}

	/****************************************************************************
	 * 功能：查询课表作者：周志辉 时间：2017-10-20
	 ****************************************************************************/
	@RequestMapping("/public/listTimetable")
	public ModelAndView listTimetable(@RequestParam Integer currpage,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.addObject("labName",request.getParameter("labName"));
		mav.addObject("courseName",request.getParameter("courseName"));
		mav.addObject("className",request.getParameter("className"));
		// 页面设置
		int pageSize = 20;
		int totalRecords = timetableAttendanceService.allTimetableCount(currpage, pageSize, request);
		List<Object[]> listTimetable=timetableAttendanceService.findAllTimetable(currpage,pageSize,request);
		// 分页
		Map<String, Integer> pageModel = shareService.getPage(
				currpage, pageSize, totalRecords);
		mav.addObject("listTimetable",listTimetable);
		mav.addObject("flag",1);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", currpage);

		mav.setViewName("/show/listTimetable.jsp");
		return mav;

	}

	/**
	 * 班牌
	 * @param type （1实验室当天，2楼层当天，3楼宇当天，4全部实时，5全部当天）
	 * @param request (type=1,2,3需要id，type=2需要floor)
	 * @author
	 * 2018-9-30
	 */
	@RequestMapping("/public/classCard")
	public ModelAndView classCard(@RequestParam int currpage, @RequestParam Integer type, HttpServletRequest request) {
		// 获取参数
		String id = request.getParameter("id");
		Integer floor = request.getParameter("floor") == null ? 0 : Integer.valueOf(request.getParameter("floor"));
		ModelAndView mav = new ModelAndView();
		int pageSize = 8;
		List<Object[]> obj = videoPublishService.findAllLabByCourseTerms(currpage, pageSize, type, id, floor);
		mav.addObject("objects", obj);
		// 正在上课的实验室总数
		int totalRecords = videoPublishService.findAllLabCountByCourseTerm(type, id, floor);
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		// 当前周次
		int week = shareService.getBelongsSchoolWeek(Calendar.getInstance());//获取当前周次
		mav.addObject("week", week);

		// 查询类型
		mav.addObject("type", type);
		mav.addObject("id", id);
		if(pConfig.PROJECT_NAME.equals("zjcclims")){
			mav.setViewName("operation/classCardZjcc.jsp");
		}else {
			mav.setViewName("operation/classCard.jsp");
		}
		return mav;
	}

	/**
	 * 滚动显示的数据
	 * @param type （1实验室当天，2楼层当天，3楼宇当天，4全部实时，5全部当天）
	 * @param request (type=1,2,3需要id，type=2需要floor)   
	 */
	@RequestMapping(value="/public/classCardJson", produces="application/json;charset=utf-8")
	@ResponseBody
	public String classCardJson(@RequestParam int currpage, @RequestParam Integer type, HttpServletRequest request)throws UnsupportedEncodingException {
		// 获取参数
		String id = request.getParameter("id");
		Integer floor = request.getParameter("floor") == null ? 0 : Integer.valueOf(request.getParameter("floor"));
		int pageSize = 8;
		return URLDecoder.decode(videoPublishService.findAllLabByCourseTerm(currpage, pageSize, type, id, floor),"UTF-8");
	}
}
