package net.zjcclims.web.personal;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import excelTools.MatrixToImageWriter;
import net.gvsun.lims.dto.common.PConfigDTO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.zjcclims.JsonDateValueProcessor;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.MySQLService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabReservationService;
import net.zjcclims.service.message.MessageService;
import net.zjcclims.service.personal.PersonalCenterService;
import net.zjcclims.service.timetable.OuterApplicationService;
import net.zjcclims.service.timetable.SchoolCourseDetailService;
import net.zjcclims.service.timetable.TimetableAppointmentService;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller("UserController")
public class UserController {

	@Autowired
	PersonalCenterService personalCenterService;
	@Autowired
	TimetableAppointmentService timetableAppointmentService;
	@Autowired
	ShareService shareService;
	@Autowired
	SchoolCourseDetailService schoolCourseDetailService;
	@Autowired
	TimetableLabRelatedDAO timetableLabRelatedDAO;
	@Autowired
	TimetableGroupStudentsDAO timetableGroupStudentsDAO;
	@Autowired
	SchoolCourseStudentDAO schoolCourseStudentDAO;
	@Autowired
	TimetableCourseStudentDAO timetableCourseStudentDAO;
	@Autowired
	private OuterApplicationService outerApplicationService;
	@Autowired
	LabReservationService labReservationService;
	@Autowired
	LabReservationDAO labReservationDAO;
	@Autowired
	SchoolTermDAO schoolTermDAO;
	@Autowired
	UserDAO userDAO;
	@Autowired
	TimetableTeacherRelatedDAO timetableTeacherRelatedDAO;
	@Autowired
	TimetableAppointmentDAO timetableAppointmentDAO;
	@Autowired
	MessageDAO messageDAO;
	@Autowired
	MessageService messageService;
//	@Autowired
//	LabRoomDeviceService labRoomDeviceService;
//	@Autowired CTrainingResultDAO cTrainingResultDAO;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	SchoolCourseDetailDAO schoolCourseDetailDAO;

	@Autowired
	private MySQLService mySQLService;
	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register // static // property // editors.
		binder.registerCustomEditor(Calendar.class, new org.skyway.spring.util.databinding.CustomCalendarEditor());
		binder.registerCustomEditor(byte[].class, new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(false));
		binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
		binder.registerCustomEditor(java.math.BigDecimal.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
		binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Integer.class, true));
		binder.registerCustomEditor(Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
		binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
		binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Long.class, true));
		binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Double.class, true));
	}
	
	/*********************************************************************************
	 * @description:我的个人信息列表
	 * @author:方正2014/08/07
	 ************************************************************************************/
	@RequestMapping("/personal/listMyInfo")
	public ModelAndView listMyInfo() {
		ModelAndView mav = new ModelAndView();

		User user = shareService.getUser();  // 获取当前用户
		Set<Authority> as = user.getAuthorities();
		String str = "";
		if(as.size()==0){
			str = "暂无身份";
		}
		if(as.size()>0){
			for(Authority a : as){
				str+= a.getCname()+",";
			}
			str=str.substring(0,str.length()-1);
		}
		mav.addObject("str", str);
		mav.addObject("user", user);
		mav.setViewName("personal/message/listMyInfo.jsp");
		return mav;
	}

	/************************************************************
	 * @内容: 跳入个人中心模块 方正 2014/8/14
	 * 
	 ************************************************************/
	@RequestMapping("/personal/message/mySelfTimetable")
	@Transactional
	public ModelAndView mySelfTimetable() {
		ModelAndView mav = new ModelAndView();
		PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
		// 获取当前用户
		User user = shareService.getUser();
		mav.addObject("role",user.getUserRole());
		String username = user.getUsername();
		String cname = user.getCname();
		mav.addObject("cname", cname);

		// 获取当前周次
		int week = shareService.findNewWeek();
		mav.addObject("week", week);

		// 获取当前学期
		SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		mav.addObject("term", schoolTerm.getTermName());

		String apSql=" select distinct t from TimetableTeacherRelated t " +
				" left join t.timetableAppointment.timetableTutorRelateds tu " +
				" where (t.user.username= '"+ username+"'" +
				" or tu.user.username='"+username+"') " +
				" and t.timetableAppointment.schoolTerm.id="+schoolTerm.getId() +
				" and t.timetableAppointment.timetableStyle != 7";

		// 根据权限更改查询方式
		int auth = 0;
		SimpleGrantedAuthority sga = (SimpleGrantedAuthority) SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next();
		if(sga.getAuthority().contains("ROLE_SUPERADMIN")){
			apSql=" select distinct t from TimetableTeacherRelated t where t.timetableAppointment.timetableStyle != 7 and "+
					" t.timetableAppointment.schoolTerm.id="+schoolTerm.getId();
			auth = 1;
		}
		if(sga.getAuthority().contains("ROLE_COLLEGELEADER")
				|| sga.getAuthority().contains("ROLE_EXCENTERDIRECTOR")
				|| sga.getAuthority().contains("ROLE_CFO")){
			apSql=" select distinct t from TimetableTeacherRelated t where"+
					" t.timetableAppointment.schoolTerm.id="+schoolTerm.getId();
			//apSql+=" and t.timetableAppointment.schoolCourseDetail.schoolAcademy.academyNumber="+user.getSchoolAcademy().getAcademyNumber();
			apSql+=" and t.timetableAppointment.schoolCourse.schoolCourseInfo.academyNumber="+user.getSchoolAcademy().getAcademyNumber();
			apSql += " and t.timetableAppointment.timetableStyle != 7 ";
			auth = 1;
		}
		// 根据当前用户去查找教师的预约课程的id的集合
		List<TimetableTeacherRelated> timetableTeacherRelated = timetableTeacherRelatedDAO
				.executeQuery(apSql);
		// 根据当前学生去查找教师的预约课程的id的集合
		List<TimetableLabRelated> timetableLabRelated = timetableLabRelatedDAO.executeQuery(
				"select distinct t from TimetableLabRelated t where t.timetableAppointment.schoolTerm.id="+schoolTerm.getId()+" and t.timetableAppointment.timetableStyle != 7 ", 0, -1);
       
		mav.addObject("timetableTeacherRelated", timetableTeacherRelated);
		String sql = "";
		// 根据获取的教师的集合id获取该老师所有的预约课程
		List<TimetableAppointment> timetableAppointment = new ArrayList<TimetableAppointment>();
		List<TimetableAppointment> selfTimetableAppointment = new ArrayList<TimetableAppointment>();
		if (shareService.getUserDetail().getUserRole().trim().equals("1") || auth == 1) {
			// 如果僅為老師，則顯示老師的本人的排課記錄
			
				for (TimetableTeacherRelated t : timetableTeacherRelated) {
					if (t.getTimetableAppointment().getStatus() == 1) {
						if (t.getTimetableAppointment().getTimetableStyle() == 5
								|| t.getTimetableAppointment().getTimetableStyle() == 6) {
							if (t.getTimetableAppointment().getSchoolTerm().getId() == schoolTerm.getId() || t.getTimetableAppointment().getStatus() == 1) {
								selfTimetableAppointment.add(t.getTimetableAppointment());
							}
						} else {
							if (t.getTimetableAppointment().getSchoolTerm().getId() == schoolTerm
									.getId() || t.getTimetableAppointment().getStatus() == 1) {
								timetableAppointment.add(t.getTimetableAppointment());
							}
						}
					}
				}
		} else {
			// 自主排课分批排课(含二次分批及自主分批排课)
			sql = "select t from TimetableGroupStudents t where t.user.username like '"
					+ shareService.getUserDetail().getUsername() + "' ";
			List<TimetableGroupStudents> tass = timetableGroupStudentsDAO.executeQuery(sql, 0, -1);
			if (tass.size() > 0) {
				for (TimetableGroupStudents ta : tass) {
					for (TimetableAppointment te : ta.getTimetableGroup().getTimetableAppointments()) {
						// 自主分批排课
						if (te.getTimetableStyle() == 6 && te.getStatus() == 1
								&& te.getSchoolTerm().getId() == schoolTerm.getId()) {
							selfTimetableAppointment.add(te);
						}
						// 二次分批排课
						if (te.getTimetableStyle() == 4 && te.getStatus() == 1 && te.getSchoolCourseDetail() != null
								&& te.getSchoolTerm().getId() == schoolTerm.getId()) {
							timetableAppointment.add(te);
						}
					}
				}
			}

			// 教务排课、二次不分批排课
			sql = "select  c from SchoolCourseStudent c where c.userByStudentNumber.username like '"
					+ shareService.getUserDetail().getUsername() + "' and c.state=1 ";
			List<SchoolCourseStudent> tas = schoolCourseStudentDAO.executeQuery(sql, 0, -1);
			sql = "select  c from TimetableCourseStudent c where c.user.username like '"
					+ shareService.getUserDetail().getUsername() + "'";
			List<TimetableCourseStudent> tase = timetableCourseStudentDAO.executeQuery(sql, 0, -1);
			mav.addObject("tas", tas);
			for (TimetableLabRelated t : timetableLabRelated) {
				// 如果是自主排课
				if (t.getTimetableAppointment().getTimetableStyle() == 5) {
					for (TimetableCourseStudent ta : tase) {
						if (ta.getTimetableSelfCourse() != null) {
							for (TimetableAppointment ltimetableAppointment : ta.getTimetableSelfCourse()
									.getTimetableAppointments()) {
								if (t.getTimetableAppointment().getId().equals(ltimetableAppointment.getId()) &&
										t.getTimetableAppointment().getSchoolTerm().getId().equals(schoolTerm.getId())) {
									if (t.getTimetableAppointment().getStatus() == 1) {
										selfTimetableAppointment.add(t.getTimetableAppointment());
									}

								}
							}
						}
					}
					// 教务课程的不分组排课
				} else if (t.getTimetableAppointment().getTimetableStyle() == 1
						|| t.getTimetableAppointment().getTimetableStyle() == 2
						|| t.getTimetableAppointment().getTimetableStyle() == 3) {
					for (SchoolCourseStudent ta : tas) {
						if (ta.getSchoolCourseDetail() != null) {
							for (TimetableAppointment ltimetableAppointment : ta.getSchoolCourseDetail()
									.getTimetableAppointments()) {
								if (t.getTimetableAppointment().getId().equals(ltimetableAppointment.getId()) &&
										t.getTimetableAppointment().getSchoolTerm().getId().equals(schoolTerm.getId())) {
									if (t.getTimetableAppointment().getStatus() == 1) {
										timetableAppointment.add(t.getTimetableAppointment());
									}
								}
							}
						}
					}
				}
			}
		}
		// 教务相关课程排课
		mav.addObject("timetableAppointment", timetableAppointment);
		timetableAppointment.sort(new Comparator<TimetableAppointment>() {
			@Override
			public int compare(TimetableAppointment t1, TimetableAppointment t2) {
				return t1.getSchoolCourseDetail().getCourseDetailNo().compareTo(t2.getSchoolCourseDetail().getCourseDetailNo());
			}
		});
		// 自主课程相关排课
		mav.addObject("selfTimetableAppointment", selfTimetableAppointment);
		// 实验室预约
		List<TimetableLabRelated> timetableLabReservation = timetableLabRelatedDAO.executeQuery(
				"select distinct t from TimetableLabRelated t where t.timetableAppointment.timetableStyle = 7 and t.timetableAppointment.createdBy = '"+shareService.getUserDetail().getUsername()+"'", 0, -1);
		mav.addObject("timetableLabReservation", timetableLabReservation);
		List<TimetableAppointment> timetableAppointments = new ArrayList<>();
		timetableAppointments.addAll(timetableAppointment);
		timetableAppointments.addAll(selfTimetableAppointment);
		if(pConfigDTO.PROJECT_NAME.equals("fdulims")) {
			Map<Integer, String> livePathMap = mySQLService.getLivePathByApp(timetableAppointments);
			mav.addObject("livePathMap", livePathMap);
			Map<Integer, List<String>> httpPathMap = mySQLService.getHttpPathByApp(timetableAppointments);
			mav.addObject("httpPathMap", httpPathMap);
		}

		mav.setViewName("/personal/message/mySelfTimetable.jsp");
		
		return mav;
	}

	/*********************************************************************************
	 * @description:保存我的个人信息
	 * @author:方正2014/08/07
	 ************************************************************************************/
	@RequestMapping("/personal/saveMyInfo")
	public String saveMyInfo(@ModelAttribute User user,@RequestParam int tage) {
		String a = "@";
		// 获取当前用户
		String i = user.getUsername();
		User user1 = userDAO.findUserByPrimaryKey(i);

		// 直接设置用户的email telephone信息
		user1.setEmail(user.getEmail());
		user1.setTelephone(user.getTelephone());
		user1.seteNname(user.geteNname());

		// 保存信息
		userDAO.store(user1);
		return "redirect:/personal/messageList?tage="+tage+"&currpage=1 ";
	}
	/*********************************************************************************
	 * @description:保存我的个人信息
	 * @author:廖文辉2018/08/27
	 ************************************************************************************/
	@RequestMapping("/personal/saveMyInfoNext")
	public String saveMyInfoNext(@ModelAttribute User user) {
		String a = "@";
		// 获取当前用户
		String i = user.getUsername();
		User user1 = userDAO.findUserByPrimaryKey(i);

		// 直接设置用户的email telephone信息
		user1.setEmail(user.getEmail());
		user1.setTelephone(user.getTelephone());

		// 保存信息
		userDAO.store(user1);
		return "redirect:/personal/listMyInfo ";
	}
	/*********************************************************************************
	 * 功能:显示我的消息列表
	 * 作者：贺子龙
	 * 时间：2015-09-15 20:13:40
	 ************************************************************************************/
	@RequestMapping("/personal/messageList")
	public ModelAndView messageList(@ModelAttribute Message message,@RequestParam int currpage,int tage,HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		mav.addObject("starttime", starttime);
		mav.addObject("endtime", endtime);
		int pageSize = 10;
		List<Message>  messages=messageService.findMessageBySome(message, currpage-1, pageSize,request,tage);
		int totalRecords =messageService.countmessage(message,request,tage);
		mav.addObject("messages",messages);
//		System.out.println(" totalRecords ="+totalRecords);
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", currpage);
		mav.addObject("tage", tage);
		User user = shareService.getUser();  // 获取当前用户
		Set<Authority> as = user.getAuthorities();
		String str = "";
		if(as.size()==0){
			str = "暂无身份";
		}
		if(as.size()>0){
			for(Authority a : as){
				str+= a.getCname()+",";
			}
			str=str.substring(0,str.length()-1);
		}
		//用户培训记录
		int trainingRecords =shareService.counttrainings(shareService.getUser().getUsername());
		mav.addObject("trainingRecords", trainingRecords);
		mav.addObject("str", str);
		mav.addObject("user", user);
		mav.addObject("PROJECT_NAME", pConfigDTO.PROJECT_NAME);
		if(pConfigDTO.PROJECT_NAME.equals("shjulims")){   //若是上交大项目隐去个人消息
			mav.addObject("isHide",1);
		}else{
			mav.addObject("isHide",0);
		}

		// 获得当前角色
		String role = (String) request.getSession().getAttribute("authorityName");
		mav.addObject("role", role);

		mav.setViewName("personal/message/message.jsp");
		return mav;
		
	}
	/**************************************************************************
	 * @Description 改变信息为已读
	 * @return ModelAndView
	 * @author 孙虎
	 * @date 2018-4-16
	 ***************************************************************************/
	@ResponseBody
	@RequestMapping("/personal/changeMessage")
	public void changeMessage(Integer mId){
		ModelAndView mav = new ModelAndView();
		Message message = messageDAO.findMessageById(mId);
		message.setMessageState(1);
		messageDAO.store(message);
		messageDAO.flush();
	}
	

	/*********************************************************************************
	 * 功能:准入培训记录
	 * 作者：张德冰
	 * 时间：2018.03.21
	 ************************************************************************************/
	@RequestMapping("/personal/trainingRecords")
	public ModelAndView trainingRecords() {
		ModelAndView mav=new ModelAndView();
		//获取用户名
		String username=shareService.getUser().getUsername();
		//获取当前用户的培训记录
		List<LabRoomTrainingPeople> trainingRecord=shareService.findTrainingRecordByUsername(username);
		int a=trainingRecord.size();
		mav.addObject("trainingRecord", trainingRecord);
		mav.setViewName("personal/trainingRecords.jsp");
		return mav;
	}
	
	/*********************************************************************************
	 * 功能:消息汇总统计
	 * 作者：孙虎
	 * 时间：2017-11-6
	 ************************************************************************************/
	@RequestMapping("/personal/messageStatistics")
	public ModelAndView messageStatistics(@RequestParam int tage){
		ModelAndView mav=new ModelAndView();
		PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
		List<String[]> messageStatistics = new ArrayList<String[]>();
		String[] s1 = new String[4];
		s1[0] = "申请";
		s1[1] = messageService.countmessage(0, 1)+"个";
		s1[2] = messageService.countmessage(0, 2)+"个";
		s1[3] = messageService.countmessage(0, 3)+"个";
		messageStatistics.add(s1);
		String[] s2 = new String[4];
		s2[0] = "在办";
		s2[1] = messageService.countmessage(1, 1)+messageService.countmessage(2, 1)+"个";
		s2[2] = messageService.countmessage(1, 2)+messageService.countmessage(2, 1)+"个";
		s2[3] = messageService.countmessage(1, 3)+messageService.countmessage(2, 1)+"个";
		messageStatistics.add(s2);
		String[] s3 = new String[4];
		s3[0] = "办结";
		s3[1] = messageService.countmessage(4, 1)+"个";
		s3[2] = messageService.countmessage(4, 2)+"个";
		s3[3] = messageService.countmessage(4, 3)+"个";
		messageStatistics.add(s3);
		String[] s4 = new String[4];
		s4[0] = "拒绝";
		s4[1] = messageService.countmessage(3, 1)+"个";
		s4[2] = messageService.countmessage(3, 2)+"个";
		s4[3] = messageService.countmessage(3, 3)+"个";
		messageStatistics.add(s4);
		mav.addObject("messages",messageStatistics);
		User user = shareService.getUser();  // 获取当前用户
		mav.addObject("user", user);
		mav.addObject("tage",tage);
		mav.addObject("PROJECT_NAME", pConfigDTO.PROJECT_NAME);

		Set<Authority> as = user.getAuthorities();
		String str = "";
		if(as.size()==0){
			str = "暂无身份";
		}
		if(as.size()>0){
			for(Authority a : as){
				str+= a.getCname()+",";
			}
			str=str.substring(0,str.length()-1);
		}
		mav.addObject("str", str);

		mav.setViewName("personal/message/messageStatistics.jsp");
		return mav;
	}
	
	/***************************************
	 * 功能：我的消息 查看，更新信息的状态，返回信息的内容；
	 * 作者：贺子龙
	 * 时间：2015-09-16 13:33:13
	 ****************************************/
	@RequestMapping("/setMsgStateNew")
	public @ResponseBody String[] setMsgState(@RequestParam Integer id) {
		Message message=messageService.findMessageByPrimaryKey(id);//查找消息
		message.setMessageState(1);//消息状态设置为已读
		messageDAO.store(message);
		Integer flag=message.getMessageState();
		String[] ss = new String[2];
		ss[0] = String.valueOf(flag);
		ss[1] = message.getContent();
		return ss;
	}
	
	/************************************************************
	 * @throws IOException 
	 * @内容: 跳入个人中心模块 方正 2014/8/14
	 * 
	 ************************************************************/
	@RequestMapping("/personal/message/mySelfTimetableApp")
	@Transactional
	public void mySelfTimetableApp(String username,HttpServletResponse response) throws IOException {

		// 获取当前用户
		User user = userDAO.findUserByUsername(username);
		String cname = user.getCname();
		// 获取当前周次
		int week = shareService.findNewWeek();

		// 获取当前学期
		SchoolTerm SchoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		String term = SchoolTerm.getTermName();
		
		// 根据当前用户去查找教师的预约课程的id的集合
		List<TimetableTeacherRelated> timetableTeacherRelated = timetableTeacherRelatedDAO
				.executeQuery("select t from TimetableTeacherRelated t where t.user.username='"
							+ username
							+ "' order by t.timetableAppointment.courseCode ,t.timetableAppointment.weekday,t.timetableAppointment.timetableNumber desc");

		// 根据当前学生去查找教师的预约课程的id的集合
		List<TimetableLabRelated> timetableLabRelated = timetableLabRelatedDAO.executeQuery(
				"select t from TimetableLabRelated t ", 0, -1);
		String sql = "";
		// 根据获取的教师的集合id获取该老师所有的预约课程
		List<TimetableAppointment> timetableAppointment = new ArrayList<TimetableAppointment>();
		List<TimetableAppointment> selfTimetableAppointment = new ArrayList<TimetableAppointment>();
		if (user.getUserRole().trim().equals("1")) {
			// 如果僅為老師，則顯示老師的本人的排課記錄
			
				for (TimetableTeacherRelated t : timetableTeacherRelated) {
					if (t.getTimetableAppointment().getStatus() == 1) {
						if (t.getTimetableAppointment().getTimetableStyle() == 5
								|| t.getTimetableAppointment().getTimetableStyle() == 6) {
							if (t.getTimetableAppointment().getTimetableSelfCourse().getSchoolTerm().getId() == SchoolTerm.getId() || t.getTimetableAppointment().getStatus() == 1) {
								selfTimetableAppointment.add(t.getTimetableAppointment());
							}
						} else {
							if (t.getTimetableAppointment().getSchoolCourse().getSchoolTerm().getId() == SchoolTerm
									.getId() || t.getTimetableAppointment().getStatus() == 1) {
								timetableAppointment.add(t.getTimetableAppointment());
							}
						}
					}
				}
		} else {
			// 自主排课分批排课(含二次分批及自主分批排课)
			sql = "select t from TimetableGroupStudents t where  t.user.username like '"
					+ user.getUsername() + "' ";
			List<TimetableGroupStudents> tass = timetableGroupStudentsDAO.executeQuery(sql, 0, -1);
			if (tass.size() > 0) {
				for (TimetableGroupStudents ta : tass) {
					for (TimetableAppointment te : ta.getTimetableGroup().getTimetableAppointments()) {
						// 自主分批排课
						if (te.getTimetableStyle() == 6 && te.getStatus() == 1
								&& te.getTimetableSelfCourse().getSchoolTerm().getId() == SchoolTerm.getId()) {
							selfTimetableAppointment.add(te);
						}
						// 二次分批排课
						if (te.getTimetableStyle() == 4 && te.getStatus() == 1 && te.getSchoolCourseDetail() != null
								&& te.getSchoolCourseDetail().getSchoolTerm().getId() == SchoolTerm.getId()) {
							timetableAppointment.add(te);
						}
					}
				}
			}

			// 教务排课、二次不分批排课
			sql = "select  c from SchoolCourseStudent c where c.userByStudentNumber.username like '"
					+ user.getUsername() + "' and c.state=1 ";
			List<SchoolCourseStudent> tas = schoolCourseStudentDAO.executeQuery(sql, 0, -1);
			sql = "select  c from TimetableCourseStudent c where c.user.username like '"
					+ user.getUsername() + "'";
			List<TimetableCourseStudent> tase = timetableCourseStudentDAO.executeQuery(sql, 0, -1);
			for (TimetableLabRelated t : timetableLabRelated) {
				// 如果是自主排课
				if (t.getTimetableAppointment().getTimetableStyle() == 5) {
					for (TimetableCourseStudent ta : tase) {
						if (ta.getTimetableSelfCourse() != null) {
							for (TimetableAppointment ltimetableAppointment : ta.getTimetableSelfCourse()
									.getTimetableAppointments()) {
								if (t.getTimetableAppointment().getId() == ltimetableAppointment.getId()&&
										t.getTimetableAppointment().getTimetableSelfCourse().getSchoolTerm().getId()==SchoolTerm.getId()) {
									if (t.getTimetableAppointment().getStatus() == 1) {
										selfTimetableAppointment.add(t.getTimetableAppointment());
									}

								}
							}
						}
					}
					// 教务课程的不分组排课
				} else if (t.getTimetableAppointment().getTimetableStyle() == 1
						|| t.getTimetableAppointment().getTimetableStyle() == 2
						|| t.getTimetableAppointment().getTimetableStyle() == 3) {
					for (SchoolCourseStudent ta : tas) {
						if (ta.getSchoolCourseDetail() != null) {
							for (TimetableAppointment ltimetableAppointment : ta.getSchoolCourseDetail()
									.getTimetableAppointments()) {
								if (t.getTimetableAppointment().getId() == ltimetableAppointment.getId()&&
										t.getTimetableAppointment().getSchoolCourseDetail().getSchoolTerm().getId()==SchoolTerm.getId()) {
									if (t.getTimetableAppointment().getStatus() == 1) {
										timetableAppointment.add(t.getTimetableAppointment());
									}
								}
							}
						}
					}
				}
			}
		}
		JSONObject jsonObject = new  JSONObject();
		 Configuration config = new Configuration().configure();
         SessionFactory sf     = config.buildSessionFactory();
         Session session = sf.openSession();
         Transaction ts = session.beginTransaction();
         response.setContentType("text/ html；charset=utf-8");
         response.setCharacterEncoding("utf-8"); 
  	   PrintWriter out = response.getWriter();
		if(selfTimetableAppointment != null && selfTimetableAppointment.size() != 0){
	    	   String finalSql = "select * from view_final_timetable_appointment where";
	    	   int count = 0;
	    	   for(TimetableAppointment t:selfTimetableAppointment){
	    		   if(count == 0){
	    			   finalSql += " timetable_appointmentId ="+t.getId();
	    			   count++;
	    		   }
	    		   else{
	    			   finalSql += " or timetable_appointmentId ="+t.getId();
	    		   }
	    	   }
	           SQLQuery queryList = session.createSQLQuery(finalSql); //返回对象
	           queryList.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
	           @SuppressWarnings("unchecked")
			List<Map<String, Object>> results = queryList.list();
	    	   JSONArray jsonArray = JSONArray.fromObject(results);
	    	   jsonObject.put("appointments", jsonArray);
	    	   //out.println(jsonArray);
	       }
		String sqlWeek = " select term_id, week, weekday, UNIX_TIMESTAMP(date) weekDate from school_week";
		SQLQuery queryList = session.createSQLQuery(sqlWeek); //返回对象
        queryList.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        @SuppressWarnings("unchecked")
		List<Map<String, Object>> results = queryList.list();
        JsonConfig jsonConfig = new JsonConfig();
     // 设置javabean中日期转换时的格式
        jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
 	    JSONArray jsonArray = JSONArray.fromObject(results,jsonConfig);
 	    //out.println(jsonArray); 
 	    jsonObject.put("schoolWeeks", jsonArray);
 	    out.print(jsonObject);
	}

	
	@RequestMapping("/test/zhudianya")
	@Transactional
	public void test(String id, String labroomID,String weekDay,String section,HttpServletResponse response) throws IOException {
		String ID=labroomID;
		//String ID = new String(labroomID.getBytes("ISO8859-1"),"UTF-8");
		String Day=weekDay;
		String Section=section;
		System.out.println("ID -> "+ID); 
		System.out.println("Day -> "+Day); 
		System.out.println("Section -> "+Section); 
		
		SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		String[] classes = section.split(",");
		int[] c = new int[classes.length];
		for(int i=0; i<classes.length; i++){
			c[i] = Integer.parseInt(classes[i]);
		}
		int[] labrooms = new int[1];
		labrooms[0] = Integer.parseInt(labroomID);
		
		Integer[] intWeeks = outerApplicationService.getValidLabWeeks(schoolTerm.getId(), c, labrooms, Integer.parseInt(weekDay));
		response.setContentType("text/html；charset=utf-8");
        response.setCharacterEncoding("utf-8"); 
 	   	PrintWriter out = response.getWriter();
 	   	String weeks ="";
 	   	if(intWeeks.length > 0){
 	   		weeks +=intWeeks[0].toString();
 	   		for(int i = 1; i < intWeeks.length; i++){
 	   			weeks +=","+intWeeks[i].toString();
 	   		}
 	   	}
 	   	out.print(weeks);
		
	 }
	@RequestMapping("/test/zhudianyaAll")
	@Transactional
	public void testAll(String labroomID,String weekDay,String section,String week,
			String act,String actName,String content,String ps,String students,
			HttpServletResponse response) throws IOException {
		String ID=labroomID;
		String Day=weekDay;
		String Section=section;
		String Week=week;
		String Act=act;
		String ActName=new String(actName.getBytes("ISO8859-1"),"UTF-8");
		String Content=new String(content.getBytes("ISO8859-1"),"UTF-8");
		String Ps=new String(ps.getBytes("ISO8859-1"),"UTF-8");
		String Students=new String(students.getBytes("ISO8859-1"),"UTF-8");
		System.out.println("ID -> "+ID); 
		System.out.println("Day -> "+Day); 
		System.out.println("Section -> "+Section); 
		System.out.println("Week -> "+Week); 
		System.out.println("Act -> "+Act); 
		System.out.println("ActName -> "+ActName); 
		System.out.println("Content -> "+Content); 
		System.out.println("Ps -> "+Ps); 
		System.out.println("Students -> "+Students); 
		response.setContentType("text/ html；charset=utf-8");
        response.setCharacterEncoding("utf-8"); 
 	   	PrintWriter out = response.getWriter();
 	   	out.print("success");
		
	 }

	/**************************************************************************
	 * @Description 保存新密码
	 * @author 张德冰
	 * @date 2018-09-27
	 ***************************************************************************/
	@RequestMapping("/personal/savePassword")
	public @ResponseBody String[] savePassword(@RequestParam String username, String password, String newPassword){
		User user = userDAO.findUserByPrimaryKey(username);
		String result;
		//加密
		String p= shareService.createMD5(password);
		if(!p.equals(user.getPassword())){
			result="NO";
		}else {
			String np= shareService.createMD5(newPassword);
			user.setPassword(np);
			userDAO.store(user);
			result="OK";
		}
		String[] results={result};
		return results;
	}

	/**************************************************************************
	 * @Description 绑定微信
	 * @author 张德冰
	 * @date 2018-12-06
	 ***************************************************************************/
	@RequestMapping("/personal/linkWeChat")
	public ModelAndView linkWeChat(){
		ModelAndView mav = new ModelAndView();
		PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
		User user = shareService.getUser();  // 获取当前用户

		mav.addObject("user", user);

		//用户培训记录
		int trainingRecords =shareService.counttrainings(shareService.getUser().getUsername());
		mav.addObject("trainingRecords", trainingRecords);
		mav.addObject("PROJECT_NAME", pConfigDTO.PROJECT_NAME);

		mav.setViewName("personal/WeChat.jsp");
		return mav;
	}

	/**
	 * @Description 生成二维码
	 * @author 张德冰
	 * @date 2018-12-07
	 */
	@RequestMapping("/personal/generateQrCodeForWeChat")
	@ResponseBody
	public String generateQrCodeForWeChat(HttpServletRequest request) throws WriterException, IOException {
		// 获取系统路径
		String root = System.getProperty("zjcclims.root");
		// 二维码的保存路径
		String path = "upload" + "/" + "weChatCode";
		//内容
		//获得当前登录人
		User user = shareService.getUser();
		String code = request.getSession().getAttribute("validateCode").toString();
		String cd= shareService.createMD5(code);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		String text = "{\"name:\""+ user.getUsername() +"\";\"key\":\""+ cd +"\";\"STMP\":\""+time+"\"}";
		int width = 300;
		int height = 300;
		// 二维码的图片格式
		String format = "gif";
		Hashtable hints = new Hashtable();
		// 内容所使用编码
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
		File sendPath = new File(root + path);
		// 构建文件夹
		if (!sendPath.exists()) {
			sendPath.mkdirs();
		}
		// 生成二维码
		File outputFile = new File(root + path + File.separator +"code.gif");
		MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
		String url = path + "/code.gif";
		String map = request.getContextPath().toString()+"/"+url;
		return shareService.htmlEncode(map);
	}

}
