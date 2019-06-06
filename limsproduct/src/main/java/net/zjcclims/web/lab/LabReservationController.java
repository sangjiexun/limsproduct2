/****************************************************************************
 * 命名规范：
 * 本控制层调整必须是按照如下的路径调整：@RequestMapping("/appointment/sample")，全部小写。
 * 列表信息listxxxx，獲取信息：getxxxx 编辑信息：editxxxx 删除信息：detelexxxx 新增信息 newxxxx
 * 导出信息exportxxxx 保存信息：savexxxx 
 ****************************************************************************/

package net.zjcclims.web.lab;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.Labreservationlist;
import excelTools.TableData;
import net.zjcclims.common.LabAttendance;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.cmsshow.CMSShowService;
import net.zjcclims.service.common.CommonDocumentService;
import net.zjcclims.service.common.CommonVideoService;
import net.zjcclims.service.common.LabRoomLogService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.credit.CreditOptionService;
import net.zjcclims.service.lab.*;
import net.zjcclims.service.message.MessageService;
import net.zjcclims.service.system.SystemService;
import net.zjcclims.service.visualization.VisualizationService;
import net.zjcclims.service.common.MySQLService;
import net.zjcclims.util.HttpClientUtil;
import net.zjcclims.web.PageModel;
import net.zjcclims.web.common.PConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.BindException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/****************************************************************************
 * 功能：实验分室模块 作者：魏诚 时间：2014-07-14
 ****************************************************************************/
@Controller("LabReservationController")
@SessionAttributes("selected_academy")
public class LabReservationController<JsonResult> {
	// 读取属性文件中specialAcademy对应的值（此方法需要在web-content.xml中增加配置）
		@Value("${specialAcademy}")
		private String specialAcademy;
	@Autowired
	LabReservationService labReservationService;
	@Autowired
	LabRoomAdminDAO labRoomAdminDAO;
	@Autowired
	CommonVideoService commonVideoService;
	@Autowired
	CommonDocumentService commonDocumentService;
	@Autowired
	ShareService shareService;
	@Autowired
	private LabRoomLogService labRoomLogService;
	@Autowired
	private CMSShowService cmsShowService;
	@Autowired
	private LabAnnexService labAnnexService;
	@Autowired
    private MySQLService mySQLService;
	@Autowired
	private SchoolTermDAO schoolTermDAO;
	@Autowired
	private LabReservationDAO labReservationDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private TimetableSelfCourseDAO timetableSelfCourseDAO;
	@Autowired
	private LabCenterDAO labCenterDAO;
	@Autowired
	private LabRoomAgentDAO labRoomAgentDAO;
	@Autowired
	private MessageDAO messageDAO;
	@Autowired
	private LabRoomPermitUserDAO labRoomPermitUserDAO;
	@Autowired
	private MessageService messageService;
	@Autowired
	private LabRoomService labRoomService;
	@Autowired private CDictionaryDAO cDictionaryDAO;
	@Autowired private LabRoomDAO labRoomDAO;
	@Autowired private CreditOptionService creditOptionService;
	@Autowired private LabRoomReservationService labRoomReservationService;
	@Autowired private LabRoomAdminService labRoomAdminService;
	@Autowired private LabRoomReservationCreditDAO labRoomReservationCreditDAO;
	@Autowired private LabRoomTrainingDAO labRoomTrainingDAO;
	@Autowired private LabRoomTrainingPeopleDAO labRoomTrainingPeopleDAO;
	@Autowired private SoftwareReserveDAO softwareReserveDAO;
	@Autowired private SoftwareReserveAuditDAO softwareReserveAuditDAO;
	@Autowired private SystemService systemService;
	@Autowired private AuthorityDAO authorityDAO;
	@Autowired private SchoolAcademyDAO schoolAcademyDAO;
	@Autowired private LabRoomLimitTimeDAO labRoomLimitTimeDAO;
	@Autowired private ReservationSetItemDAO reservationSetItemDAO;
    @Autowired
    PConfig pConfig;
	@Autowired private VisualizationService visualizationService;
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

	/****************************************************************************
	 * 功能：查询实验室 作者：薛帅 时间：2014-08-6
	 ****************************************************************************/
	@RequestMapping("/lab/labAnnexList")
	public ModelAndView labAnnexList(@ModelAttribute LabRoom labRoom, @RequestParam Integer currpage,
			@ModelAttribute("selected_academy") String acno) {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		// 查询表单的对象
		mav.addObject("labRoom", labRoom);
		int pageSize = 30;// 每页20条记录
		// 查询出来的总记录条数
		int totalRecords = labReservationService.findLabRoom(labRoom).size();
		// 分页信息
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		// 根据分页信息查询出来的记录
		List<LabRoom> listLabRoom = labReservationService.findLabRoompage(labRoom, currpage, pageSize);
		
		//获取当前登陆人
		User user=shareService.getUser();
		mav.addObject("user",user);
		// 获取可用选课组
		mav.addObject("selfCourseList",
				timetableSelfCourseDAO.executeQuery("select c from TimetableSelfCourse c where c.status=-1"));
		mav.addObject("listLabRoom", listLabRoom);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("currpage", currpage);
		mav.addObject("pageSize", pageSize);
		// 查找预约类型
		mav.addObject("appointmenttype", labReservationService.appointmenttype());
		// 查找周次
		mav.addObject("week", labReservationService.getallweek());
		// 查找星期
		mav.addObject("date", labReservationService.getalldate());
		// 查找节次
		mav.addObject("festivaltimes", labReservationService.getallfestivaltimes());
		// 查找活动类别
		mav.addObject("activitycategory", labReservationService.getallactivitycategory());
		// 查找联系人
		mav.addObject("user", labReservationService.getUsersMap());
		// 查找课程组编号
		mav.addObject("classgruop", labReservationService.getallclassgruop());
		// 查找课程组编号
		mav.addObject("courseMap", labReservationService.getallclass());
		// 创建对象
		mav.addObject("labReservation", new LabReservation());
		// 学期
		mav.addObject("schoolterm", labReservationService.getschoolterm());
		// 当前学期
		mav.addObject("currTerm", shareService.getBelongsSchoolTerm(Calendar.getInstance()));
		// 获取当前学院下的年级
		if(acno != null && !acno.equals("-1"))
		{
			mav.addObject("grade",userDAO.executeQuery("select c from User c where c.schoolAcademy.academyNumber like '"
					+ shareService.findSchoolAcademyByPrimaryKey(acno).getAcademyNumber() + "' group by c.grade"));
		}
		else{
			mav.addObject("grade",userDAO.executeQuery("select c from User c where c.schoolAcademy.academyNumber like '"
					+ shareService.getUser().getSchoolAcademy().getAcademyNumber() + "' group by c.grade"));
		}

		mav.setViewName("lab/lab_reservation/labAppointment.jsp");
		return mav;
	}

	/**
	 * 功能 根据周次获取节次 ajax
	 * 
	 * @param
	 * @param idkey
	 *            周id
	 * @return
	 */
	@RequestMapping("labreservation/screeningtake")
	public @ResponseBody
	String screeningtake(@RequestParam Integer idkey, Integer labid, Integer time) {

		String str = "";
		for (SystemTime iter : labReservationService.getscreeningtake(idkey, labid, time)) {
			str += "<option value=" + iter.getSection() + ">" + iter.getSectionName()+ "</option>";
		}
		;
		return shareService.htmlEncode(str);
	}

	/**
	 * 功能 根据周次获取星期 ajax
	 * 
	 * @param
	 * @param idkey
	 *            周id
	 * @return
	 */
	@RequestMapping("labreservation/screeningtaketime")
	public @ResponseBody
	String screeningtaketime(@RequestParam Integer idkey) {
		String str = "";
		for (SchoolWeekday iter : labReservationService.getscreeningtaketime(idkey)) {
			str += "<option value=" + iter.getId() + ">" + iter.getWeekdayName() + "</option>";
		}
		;
		return shareService.htmlEncode(str);
	}

	/**
	 * 功能 根据星期获取周次
	 * 
	 * @param
	 * @param idkey
	 *            周id
	 * @return
	 */
	@RequestMapping("labreservation/screeningtakexingqi")
	public @ResponseBody
	String screeningtakexingqi(@RequestParam Integer idkey) {
		String str = "";
		/*for (CLabReservationWeek iter : labReservationService.getscreeningtakexingqi(idkey)) {
			str += "<option value=" + iter.getId() + ">" + iter.getName() + "</option>";
		}*/
		for (CDictionary iter : labReservationService.getscreeningtakexingqi(idkey)) {
			str += "<option value=" + iter.getId() + ">" + iter.getCName() + "</option>";
		}
		;
		return shareService.htmlEncode(str);
	}

	/**
	 * 功能 根据星期获取节次
	 * 
	 * @param
	 * @param idkey
	 *            周id
	 * @return
	 */
	@RequestMapping("labreservation/screeningtakejicebyxingqi")
	public @ResponseBody
	String screeningtakejicebyxingqi(@RequestParam Integer idkey) {
		String str = "";
		for (SystemTime iter : labReservationService.getscreeningtakejicebyxingqi(idkey)) {
			str += "<option value=" + iter.getSection() + ">" + iter.getSectionName() + "</option>";
		}
		;
		return shareService.htmlEncode(str);
	}

	@RequestMapping("/labreservation/savelabreservation")
	public String savelabreservation(@ModelAttribute LabReservation labReservation, HttpServletRequest request) throws ParseException {
		
		labReservationService.saveLabReservationProc(labReservation, request);
		List<LabRoomAdmin> labRoomAdmins=labRoomAdminService.findAllLabRoomAdminsByLabRoomId(labReservation.getLabRoom().getId());
		for(LabRoomAdmin lba:labRoomAdmins){
			Message message = new Message();
			message.setSendUser(shareService.getUserDetail().getCname());// 当前登录人
			message.setSendCparty(shareService.getUserDetail()
					.getSchoolAcademy().getAcademyName());// 当前登录人所在学院
			message.setTitle(CommonConstantInterface.STR_LABROOMRSEERVATIONUNDING_TITLE);
			String content = "申请成功，等待审核";
			content += "<a onclick='changeMessage(this)' href='../labreservation/Labreservationmanage?tage=0&currpage=1";
			content += "'>点击查看</a>";
			message.setContent(content);
			message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
			message.setCreateTime(Calendar.getInstance());
			// message.setUsername(excenterDirector.getUsername());
			message.setTage(2);
			message.setUsername(lba.getUser().getUsername());
			message = messageDAO.store(message);
		}
		//message=messageDAO.store(message);
		/*
		 * // 获取学期 String xueqi = request.getParameter("xueqi"); // 获取周次
		 * String[] zhouci = request.getParameterValues("zhouci"); // 获取星期
		 * String xingqi = request.getParameter("xingwq"); // 获取节次 String[] time
		 * = request.getParameterValues("jiexi"); // 获取学生 String students =
		 * request.getParameter("student1");
		 * 
		 * if (xueqi != null && xueqi != "" && zhouci != null && zhouci.length >
		 * 0 && xingqi != null && xingqi != "" && time != null && time.length >
		 * 0) { sa = labReservationService.savelabReservation1(labReservation,
		 * Integer.parseInt(idkey), xueqi, zhouci, xingqi, time);
		 * 
		 * } if (student != null && student != "") {
		 * labReservationService.savelabReservationstudent(sa, student); } if
		 * (students != null && students != "") {
		 * labReservationService.savelabReservationstudent(sa, students); }
		 */
		// return "redirect:/Lab/labAnnexList?currpage=1";
		return "redirect:/labreservation/Labreservationmanage?tage=0&currpage=1";
	}

	/**
	 * 功能 ： 导出大纲
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	@RequestMapping("/lab/labAnnexListexportall")
	public void labAnnexListexportall(@ModelAttribute LabRoom labRoom, HttpServletRequest request,
			HttpServletResponse response, @RequestParam int page)
			throws Exception {

		int pageSize = 30;

//		List<LabRoom> finList = labReservationService.findLabRoompage(labRoom, page, pageSize);
//		List<Map> list = new ArrayList<Map>();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		for (LabRoom f : finList) {
//			Map map = new HashMap();
//			map.put("operationoutlineName", f.getLabRoomName());
//			if (f.getUser() != null) {
//				map.put("user", f.getUser().getCname());
//			} else {
//				map.put("user", "");
//			}
//			map.put("classno", f.getLabRoomNumber());
//			map.put("classname", f.getLabRoomAddress());
//
//			list.add(map);
//		}
//		String title = "项目列表";
//		String[] hearders = new String[] { "实验室名称", "创建者", "实验室编号", "实验室地址" };// 表头数组
//		String[] fields = new String[] { "operationoutlineName", "user", "classno", "classname" };// Financialresources对象属性数组
//		TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
//		JsGridReportBase report = new JsGridReportBase(request, response);
//		report.exportToExcel(title, shareService.getUser().getCname(), td);

	}

	/****************************************************************************
	 * 功能：查询实验室 作者：薛帅 时间：2014-08-6
	 ****************************************************************************/
	@RequestMapping("/labreservation/Labreservationmanage")
	public ModelAndView Labreservationmanage(@ModelAttribute LabReservation labReservation,
			@RequestParam Integer currpage, int tage, @ModelAttribute("selected_academy") String acno) {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		// 查询表单的对象d
		mav.addObject("labReservation", labReservation);
		int pageSize = 30;// 每页20条记录
		// 查询出来的总记录条数
		int totalRecords = labReservationService.findLabreservationmanage(labReservation, tage, 1, -1, acno).size();
		// 分页信息
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		// 根据分页信息查询出来的记录
		Set<LabReservation> LabReservations = labReservationService.findLabreservationManage(labReservation, tage,
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
			Set<String> week = new HashSet<String>();
			Set<String> day = new HashSet<String>();
			Set<String> time = new HashSet<String>();
			if (lab.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() > 0)
				for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : lab.getTimetableAppointment()
						.getTimetableAppointmentSameNumbers()) {
					if (timetableAppointmentSameNumber.getStartWeek().intValue() != timetableAppointmentSameNumber.getEndWeek().intValue()) {
						week.add(timetableAppointmentSameNumber.getStartWeek().toString() + "-"
								+ timetableAppointmentSameNumber.getEndWeek().toString());
					} else {
						week.add(timetableAppointmentSameNumber.getStartWeek().toString());

					}
					day.add(lab.getTimetableAppointment().getWeekday().toString());
					if (timetableAppointmentSameNumber.getStartClass().intValue() != timetableAppointmentSameNumber.getEndClass().intValue()) {
						time.add(timetableAppointmentSameNumber.getStartClass().toString() + "-"
								+ timetableAppointmentSameNumber.getEndClass().toString());
					} else {
						time.add(timetableAppointmentSameNumber.getStartClass().toString());
					}
				}
			else {
				if (lab.getTimetableAppointment().getStartWeek().intValue() != lab.getTimetableAppointment().getEndWeek().intValue()) {
					week.add(lab.getTimetableAppointment().getStartWeek().toString() + "-"
							+ lab.getTimetableAppointment().getEndWeek().toString());
				}else{
					week.add(lab.getTimetableAppointment().getStartWeek().toString());
				}
				
				day.add(lab.getTimetableAppointment().getWeekday().toString());
				if (lab.getTimetableAppointment().getStartClass().intValue() != lab.getTimetableAppointment().getEndClass().intValue()) {
					time.add(lab.getTimetableAppointment().getStartClass().toString() + "-"
							+ lab.getTimetableAppointment().getEndClass().toString());
				}else{
					time.add(lab.getTimetableAppointment().getStartClass().toString());

				}
				

			}
			int dd = week.size();
			String[] weeks = week.toArray(new String[dd]);
			String[] days = day.toArray(new String[day.size()]);
			String[] timea = time.toArray(new String[time.size()]);
			;
			// 数组排序
			String[] weekt = insertSort(weeks);
			String[] timet = insertSort(timea);

			la.setWeek(weekt);
			la.setTime(timet);
			la.setDay(days);
			//设置实验室
			if (lab.getLabRoom() != null) {
				la.setLab(lab.getLabRoom().getLabRoomName());
				la.setLabRoom(lab.getLabRoom());
			}
			//设置申请者
			if (lab.getUser() != null) {
				la.setUser(lab.getUser());
			}
			la.setCont(lab.getAuditResults());
			la.setStart(lab.getReservations());
			la.setFabu(lab.getItemReleasese());
			la.setUser(lab.getUser());//申请人
			la.setLabRoom(lab.getLabRoom());//实验室
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
			int isAudit=0;
			Set<LabRoomAdmin> admins=lab.getLabRoom().getLabRoomAdmins();
			if (admins.size()>0) {
				for (LabRoomAdmin labRoomAdmin : admins) {
					if (labRoomAdmin.getTypeId()==1&&labRoomAdmin.getUser().getUsername()==shareService.getUser().getUsername()) {
						isAudit=1;break;
					}
				}
			}
			la.setIsAudit(isAudit);
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
		mav.setViewName("lab/lab_reservation/labreaervationmanageList.jsp");
		return mav;
	}

	public static String[] insertSort(String[] weeks) {// 插入排序算法
		for (int i = 1; i < weeks.length; i++) {
			for (int j = i; j > 0; j--) {
                String start =weeks[j];
                String end =weeks[j-1];
                if(start.indexOf("-")!=-1){
                	start = start.substring(start.indexOf("-"));
                }
                if(end.indexOf("-")!=-1){
                	end = end.substring(end.indexOf("-"));
                }
                
				if (Integer.parseInt(start) < Integer.parseInt(end)) {
					String temp = weeks[j - 1];
					weeks[j - 1] = weeks[j];
					weeks[j] = temp;
				} else
					break;
			}
		}
		return weeks;
	}

	/**
	 * 查看实验室预约课程的学生 作者：薛帅 参数 预约课程id
	 */
	@RequestMapping("/lab/findstudentforlabreation")
	public ModelAndView findstudentforlabreation(
			@ModelAttribute LabReservationTimeTableStudent labReservationTimeTableStudent, @RequestParam int idkey,
			int currpage) {

		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		// 查询表单的对象
		mav.addObject("labReservationTimeTableStudent", labReservationTimeTableStudent);
		int pageSize = 30;// 每页20条记录
		// 查询出来的总记录条数
		int totalRecords = labReservationService.getstudentforlabreation(labReservationTimeTableStudent, idkey).size();
		// 分页信息
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		// 根据分页信息查询出来的记录
		List<LabReservationTimeTableStudent> TableStudent = labReservationService.getstudentforlabreationpage(
				labReservationTimeTableStudent, idkey, currpage, pageSize);
		mav.addObject("TableStudent", TableStudent);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("currpage", currpage);
		mav.addObject("pageSize", pageSize);
		mav.addObject("idkey", idkey);
		mav.setViewName("lab/lab_reservation/labStudentforLabreation.jsp");// 设置jsp页面
		return mav;

	}

	/**
	 * 实验室日志显示 作者：何莉莉 时间：
	 */
	@RequestMapping("/lab/listLabRoomLog")
	public ModelAndView listLabRoomLog(@RequestParam int currpage) {
		ModelAndView mav = new ModelAndView();
		// 要返回的分页模型
		int pageSize = 15;
		PageModel pageModel = new PageModel(currpage, labRoomLogService.countLabRoomLogs(), pageSize);
		mav.addObject("pageModel", pageModel);// 返回分页模型
		mav.addObject("logs", labRoomLogService.findAllLabRoomLogs((currpage - 1) * pageSize, pageSize));// 返回数据
		mav.setViewName("lab/listLabRoomLog.jsp");// 设置jsp页面
		return mav;

	}

	/**
	 * 实验室日志显示 作者：何莉莉 时间：
	 */
	@RequestMapping("/lab/checkButton")
	public ModelAndView checkButton(@RequestParam int idkey, int tage) {
		ModelAndView mav = new ModelAndView();
		LabReservation sd = labReservationService.getlabReservationinfor(idkey);
		mav.addObject("infor", sd);
		mav.addObject("tage", tage);
		mav.setViewName("lab/lab_reservation/labreaervationCentrolManagerView.jsp");
		return mav;

	}

	@RequestMapping("/labreservation/labteachershow")
	public ModelAndView labteachershow(@RequestParam int idkey, int tage) {
		ModelAndView mav = new ModelAndView();
		LabReservation lab = labReservationService.getlabReservationinfor(idkey);
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
		Set<String> week = new HashSet<String>();
		Set<String> day = new HashSet<String>();
		Set<String> time = new HashSet<String>();
		for (LabReservationTimeTable labre : lab.getLabReservationTimeTables()) {
			//week.add(labre.getCLabReservationWeek().getId().toString());
			week.add(labre.getCDictionary().getId().toString());
			day.add(labre.getSchoolWeekday().getId().toString());
			time.add(labre.getSystemTime().getSection().toString());
		}
		int dd = week.size();
		String[] weeks = week.toArray(new String[dd]);
		String[] days = day.toArray(new String[day.size()]);
		String[] timea = time.toArray(new String[time.size()]);
		;
		// 数组排序
		String[] weekt = insertSort(weeks);
		String[] timet = insertSort(timea);

		la.setWeek(weekt);
		la.setTime(timet);
		la.setDay(days);
		if (lab.getLabRoom() != null) {
			la.setLab(lab.getLabRoom().getLabRoomName());
		}
		la.setCont(lab.getAuditResults());
		la.setStart(lab.getReservations());
		la.setFabu(lab.getItemReleasese());
		mav.addObject("infor", la);
		mav.addObject("tage", tage);
		mav.setViewName("lab/lab_reservation/labteachershow.jsp");
		return mav;

	}

	/**
	 * 实验室预约的详细信息 老师审核 作者：何莉莉 时间：
	 */
	@RequestMapping("/labreservation/teacherLabreservationaudt")
	public ModelAndView teacherLabreservationaudt(@RequestParam int idkey, int tage) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("labReservationAudit", new LabReservationAudit());
		mav.addObject("tage", tage);
		// 要返回的分页模型
		LabReservation lab = labReservationService.getlabReservationinfor(idkey);
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
		Set<String> week = new HashSet<String>();
		Set<String> day = new HashSet<String>();
		Set<String> time = new HashSet<String>();
		for (LabReservationTimeTable labre : lab.getLabReservationTimeTables()) {
			//week.add(labre.getCLabReservationWeek().getId().toString());
			week.add(labre.getCDictionary().getId().toString());
			day.add(labre.getSchoolWeekday().getId().toString());
			time.add(labre.getSystemTime().getSection().toString());
		}
		int dd = week.size();
		String[] weeks = week.toArray(new String[dd]);
		String[] days = day.toArray(new String[day.size()]);
		String[] timea = time.toArray(new String[time.size()]);
		;
		// 数组排序
		String[] weekt = insertSort(weeks);
		String[] timet = insertSort(timea);

		la.setWeek(weekt);
		la.setTime(timet);
		la.setDay(days);
		if (lab.getLabRoom() != null) {
			la.setLab(lab.getLabRoom().getLabRoomName());
		}
		
		la.setCont(lab.getAuditResults());
		la.setStart(lab.getReservations());
		la.setFabu(lab.getItemReleasese());

		mav.addObject("l", la);// 返回数据
		mav.addObject("lab", lab);// 返回数据
		int hh = 0;
		Set<LabRoomAdmin> s = lab.getLabRoom().getLabRoomAdmins();
		for (LabRoomAdmin labRoomAdmin : s) {
			if (labRoomAdmin.getUser().getUsername().equals(shareService.getUser().getUsername())) {
				hh++;
			}
		}
		mav.addObject("man", hh);
		mav.addObject("admins", s);
		//设置登录用户
		mav.addObject("user",shareService.getUserDetail());
		mav.setViewName("lab/lab_reservation/teacherLabreservationaudt.jsp");// 设置jsp页面
		return mav;

	}

	@RequestMapping("/labreservation/labreationdelectitem")
	public String administratorreview(@RequestParam int idkey) {

		labReservationService.labreationdelectitem(idkey);
		return "redirect:/labreservation/Labreservationmanage?tage=0&currpage=1";

	}

	/**
	 * 实验室日志显示 作者：薛帅 时间：8-12 参数 idkey 预约记录id tage 标记位区分审核状态
	 */
	@RequestMapping("/labreservation/administratorreview")
	public ModelAndView administratorreview(@RequestParam int idkey, int tage) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("labReservationAudit", new LabReservationAudit());
		// 要返回的分页模型
		mav.addObject("l", labReservationService.getlabReservationinfor(idkey));// 返回数据
		mav.setViewName("lab/lab_reservation/labreservationadministratorreview.jsp");// 设置jsp页面
		return mav;

	}

	/**
	 * 功能：保存老师审核结果 作者 薛帅
	 * 
	 * @param idkey
	 *            预约列表的id
	 * @param tage
	 *            标记为
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/labReservation/auditsavelabreservtion")
	public String auditsavelabreservtion(@ModelAttribute LabReservationAudit labReservationAudit,
			@RequestParam int idkey, int tage, HttpServletResponse response) throws IOException {

		labReservationAudit.setUser(shareService.getUser());
		labReservationService.auditsavelabreservtion(labReservationAudit, idkey,response);

		return "redirect:/labreservation/Labreservationmanage?tage=" + tage + "&currpage=1";
	}

	/**
	 * 功能：发布课程 作者 薛帅
	 * 
	 * @param idkey
	 *            预约列表的id
	 * @param tage
	 *            标记为
	 * @return
	 */
	@RequestMapping("/lab/publishedcourses")
	public String publishedcourses(@RequestParam int idkey, int tage) {

		LabReservation d = labReservationService.getlabReservationinfor(idkey);
		d.setItemReleasese(1);
		d.setSelecteNumber(0);
		labReservationDAO.store(d);
		return "redirect:/labreservation/Labreservationmanage?tage=" + tage + "&currpage=1";
	}

	
	/************************************************************
	 * @实验室预约-预约时间判冲
	 * @作者：魏诚
	 * @日期：2015-07-09
	 ************************************************************/
	@RequestMapping("/lab/getValidWeeksMap")
	public @ResponseBody
	String getTimetableWeeksMap(@RequestParam int term, int weekday, int[] labrooms, int[] classes) {
		// 返回可用的星期信息
		return labReservationService.getValidWeeksMap(term, weekday, labrooms, classes);
	}
	
	/****************************************************************************
	 * 功能：门禁进出记录
	 * 作者：贺子龙
	 * 时间：2015-11-30
	 ****************************************************************************/
	@RequestMapping("/lab/entranceManageForLab")
	public ModelAndView entranceManageForLab(@ModelAttribute LabRoom labRoom, @RequestParam Integer page,@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		//获取当前用户
		User user=shareService.getUser();
		mav.addObject("user", user);
		//查询表单的对象
		mav.addObject("labRoom", labRoom);
		// 设置分页变量并赋值为20
		int pageSize = 15;
		//查询出来的总记录条数
		int totalRecords = cmsShowService.findLabRoomBySchoolAcademyDefault(labRoom,1,-1,2,acno).size();
		Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
		//当前中心下的实验室
		List<LabAnnex> labAnnexs=labAnnexService.findAllLabAnnexBySchoolAcademy(acno);
		mav.addObject("labAnnexs", labAnnexs);
		//页面显示的实验室
		List<LabRoom> labRoomList=cmsShowService.findLabRoomBySchoolAcademyDefault(labRoom,page,pageSize,2,acno);//门禁--2
		
		mav.addObject("labRoomList",labRoomList);
		mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", page);
		mav.addObject("pageSize", pageSize);
		mav.setViewName("lab/lab_record/listEntranceManageForLab.jsp");
		return mav;
	}
	
	
	/*************************************************************************************
	 * @內容：开放实验室资源--门禁
	 * @作者：贺子龙
	 * @日期：2015-12-01
	 *************************************************************************************/
	@RequestMapping("/lab/entranceList")
	public ModelAndView entranceList(@RequestParam Integer id,Integer page,@ModelAttribute CommonHdwlog commonHdwlog,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		mav.addObject("starttime", starttime);
		mav.addObject("endtime", endtime);
		mav.addObject("commonHdwlog", commonHdwlog);
		mav.addObject("id", id);
		//id对应的物联设备
		LabRoomAgent agent=labRoomAgentDAO.findLabRoomAgentByPrimaryKey(id);
		LabRoomAgent videoAgent=new LabRoomAgent();
		Set<LabEntranceVideo> list = agent.getLabEntranceVideosForVideoId();
		int videoAgentId=0;
		for(LabEntranceVideo lev : list){
			if(lev.getLabRoomAgentByVideoId()!=null)
			videoAgentId = lev.getLabRoomAgentByVideoId().getId();
		}
		mav.addObject("videoAgentId",videoAgentId);
		String ip = agent.getHardwareIp();
		//浙外临时方法
		if (pConfig.PROJECT_NAME.equals("zisulims")) {
			ip = agent.getDoorindex().toString();
		}
		String port = agent.getManufactor();
		// 设置分页变量并赋值为20
		//int pageSize = CommonConstantInterface.INT_PAGESIZE;
		int pageSize = 30;
		int totalRecords = 0;
		Map<String, Integer> pageModel = null;
		List<LabAttendance> accessList =null;
		//查询出来的总记录条数
		// 根据配置项是否切换获取对应的数据
		if (agent.getCDictionary().getCNumber().equals("6") && agent.getCDictionary().getCCategory().equals("c_agent_type")) {// 智能班牌
			// 老版获取考勤数据
			totalRecords = cmsShowService.findLabRoomAccessByIpCount(commonHdwlog,ip,port,request);
			pageModel = shareService.getPage(page, pageSize, totalRecords);
			//页面显示的实验室
			accessList=cmsShowService.findLabRoomAccessByIp(commonHdwlog,ip,port,page,pageSize,request);
		}else if(pConfig.newServer.equals("false")){
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
		mav.setViewName("lab/lab_record/listLabRoomEntrance.jsp");
		return mav;
	}
	
	/************************************************************
	 * @description:实验室预约-预约时间判冲
	 * @author：郑昕茹
	 * @date：2016-11-12
	 ************************************************************/
	@RequestMapping("/lab/getValidWeeksMapApp")
	public void getTimetableWeeksMapApp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		response.setContentType("text / html；charset=utf-8");
        response.setCharacterEncoding("utf-8");       
        PrintWriter out = response.getWriter();
	    String labroomID = request.getParameter("labroomID");
	    String  weekDay= request.getParameter("weekDay");
	    String  section= request.getParameter("section");
	    System.out.println("labroomID -> "+labroomID);
	    System.out.println("weekDay -> "+weekDay);
	    System.out.println("section -> "+section);
	    
	    
	    out.println("1,2,3,6,8");
		out.flush();
		out.close();
		// 返回可用的星期信息
		//return labReservationService.getValidWeeksMap(term, weekday, labrooms, classes);
	}
	/****************************************************************************
	 * @功能：实验室管理---实验室设置
	 * @作者：孙虎
	 ****************************************************************************/
	@RequestMapping(value = "/device/editLabRoomSettingRest/{labRoomId}/{currpage}/{type}", method = RequestMethod.GET)
	public ModelAndView editDeviceSettingRest(@PathVariable int labRoomId,@PathVariable int currpage,@PathVariable int type,Model model,
			@ModelAttribute("selected_academy") String acno) {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		// id对应的实验分室
		LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(labRoomId);
		// 获取所有单选的结果集（是/否）
		List<CDictionary> CActives = shareService.getCDictionaryData("c_active");
		mav.addObject("CActives", CActives);
		// 预约形式
		List<CDictionary> CAppointmentTypes = shareService.getCDictionaryData("c_appointment_type");
		mav.addObject("CAppointmentTypes", CAppointmentTypes);
		List<CDictionary> CLabAccessTypes = shareService.getCDictionaryData("c_lab_access_type");
		mav.addObject("CLabAccessTypes", CLabAccessTypes);
		//培训形式隐去
        /*List<CDictionary> CTrainingTypes = shareService.getCDictionaryData("c_training_type");
		mav.addObject("CTrainingTypes", CTrainingTypes);*/
		mav.setViewName("/lab/lab_room/editLabRoomSetting.jsp");
		mav.addObject("type",type);
		if(labRoom.getLabRoomReservation() != null){
			mav.addObject("allowAppointment", labRoom.getLabRoomReservation());
		}

		if (labRoom.getCDictionaryByIsAudit()!=null&&labRoom.getCDictionaryByIsAudit().getId()!=null) {

			mav.addObject("isAudit", labRoom.getCDictionaryByIsAudit().getCNumber());
		}
		if (labRoom.getCDictionaryByAllowSecurityAccess()!=null&&labRoom.getCDictionaryByAllowSecurityAccess().getId()!=null) {
			mav.addObject("allowSecurityAccess", labRoom.getCDictionaryByAllowSecurityAccess().getCNumber());
		}

		//demo
		String[] RSWITCH = {"on", "off"};
		List<String> needAllAudits = new ArrayList<>();
		List<Integer> needAllAuditStatus = new ArrayList<>();
		List<String> authNames = new ArrayList<>();
		Map<String, String> params = new HashMap<>();
		String businessType = "LabRoomReservation" + (labRoom.getLabCenter() == null ? "-1" : labRoom.getLabCenter().getSchoolAcademy().getAcademyNumber());
		params.put("businessUid", labRoom.getId().toString());
		params.put("businessType", pConfig.PROJECT_NAME + businessType);
		String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessAuditConfigs", params);
		JSONObject jsonObject = JSON.parseObject(s);
		String status = jsonObject.getString("status");
		Map auditConfigs = JSON.parseObject(jsonObject.getString("data"), Map.class);
		if (auditConfigs != null && auditConfigs.size() != 0) {
			for (int i = 0; i < auditConfigs.size(); i++) {
//				mav.addObject(showAuditLevelName[i],
//						((String) auditConfigs.get(i + 1)).contains(RSWITCH[0]) ? 1 : 2);
				String[] sc = ((String) auditConfigs.get(i + 1)).split(":");
				authNames.add(authorityDAO.findAuthorityByAuthorityName(sc[0]).iterator().next().getCname());
				needAllAudits.add(sc[0]);
				needAllAuditStatus.add(sc[1].equals(RSWITCH[0]) ? 1 : 2);
			}
		}
		mav.addObject("needAllAudits", needAllAudits);
		mav.addObject("needAllAuditStatus", needAllAuditStatus);
		mav.addObject("authNames", authNames);
		mav.addObject("device", labRoom);
		mav.addObject("labRoomId", labRoomId);
		mav.addObject("page", currpage);
		mav.addObject("currpage", currpage);

		// 开放范围
		List<SchoolAcademy> schoolAcademies = shareService.findAllSchoolAcademys();
		mav.addObject("schoolAcademyList", schoolAcademies);
		Set<SchoolAcademy> selectedSchoolAcademies = labRoom.getOpenSchoolAcademies();
		mav.addObject("selectedSchoolAcademies", selectedSchoolAcademies);
		if(selectedSchoolAcademies.size() == 0) {
			SchoolAcademy selfAca = labRoom.getSchoolAcademy();
			selectedSchoolAcademies.add(selfAca);
		}

		return mav;
	}

    /****************************************************************************
     * @Description: 实验室管理---工位预约审核设置
     * @Author Hezhaoyi 2019-6-3
     ****************************************************************************/
    @RequestMapping(value = "/device/editLabRoomStationReserSetting/{labRoomId}/{currpage}/{type}", method = RequestMethod.GET)
    public ModelAndView editLabRoomStationReserSetting(@PathVariable int labRoomId,@PathVariable int currpage,@PathVariable int type,Model model,
                                              @ModelAttribute("selected_academy") String acno) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的实验分室
        LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(labRoomId);
        // 获取所有单选的结果集（是/否）
        List<CDictionary> CActives = shareService.getCDictionaryData("c_active");
        mav.addObject("CActives", CActives);
        mav.setViewName("/lab/lab_room/editLabRoomStationReserSetting.jsp");
        mav.addObject("type",type);

        //demo
        String[] RSWITCH = {"on", "off"};
        List<String> needAllAudits = new ArrayList<>();
        List<Integer> needAllAuditStatus = new ArrayList<>();
        List<String> authNames = new ArrayList<>();
        Map<String, String> params = new HashMap<>();

        String businessType = "StationReservation";
        params.put("businessUid", labRoom.getId().toString());
        params.put("businessType", pConfig.PROJECT_NAME + businessType);
        String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessAuditConfigs", params);
        JSONObject jsonObject = JSON.parseObject(s);
        String status = jsonObject.getString("status");
        Map auditConfigs = JSON.parseObject(jsonObject.getString("data"), Map.class);
        if (auditConfigs != null && auditConfigs.size() != 0) {
            for (int i = 0; i < auditConfigs.size(); i++) {
//				mav.addObject(showAuditLevelName[i],
//						((String) auditConfigs.get(i + 1)).contains(RSWITCH[0]) ? 1 : 2);
                String[] sc = ((String) auditConfigs.get(i + 1)).split(":");
                authNames.add(authorityDAO.findAuthorityByAuthorityName(sc[0]).iterator().next().getCname());
                needAllAudits.add(sc[0]);
                needAllAuditStatus.add(sc[1].equals(RSWITCH[0]) ? 1 : 2);
            }
        }
        if (labRoom.getCDictionaryByIsStationAudit()!=null&&labRoom.getCDictionaryByIsStationAudit().getId()!=null) {

            mav.addObject("isAudit", labRoom.getCDictionaryByIsStationAudit().getCNumber());
        }
        mav.addObject("needAllAudits", needAllAudits);
        mav.addObject("needAllAuditStatus", needAllAuditStatus);
        mav.addObject("authNames", authNames);
        mav.addObject("device", labRoom);
        mav.addObject("labRoomId", labRoomId);
        mav.addObject("page", currpage);
        mav.addObject("currpage", currpage);

        return mav;
    }

    /****************************************************************************
     * @功能：实验室管理---实验室开放设置
     * @作者：Hezhaoyi
     * 2019-4-19
     ****************************************************************************/
    @RequestMapping(value = "/device/editLabRoomOpenSettingRest/{labRoomId}/{page}/{type}", method = RequestMethod.GET)
    public ModelAndView editLabRoomOpenSettingRest(@PathVariable int labRoomId,@PathVariable int page,@PathVariable int type,
                                              @ModelAttribute("selected_academy") String acno) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的实验分室
        LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(labRoomId);
        mav.addObject("labRoomId",labRoomId);
        mav.addObject("labRoom",labRoom);
        mav.addObject("type",type);
        mav.addObject("currpage", page);

        User user = shareService.getUser();
        boolean flag = labRoomService.getLabRoomAdminReturn(labRoomId, user);
        mav.addObject("flag", flag);

        //获取实验室预约的相关开放设置
        ReservationSetItem reservationSetItem = reservationSetItemDAO.findReservationSetItemBylabRoomIdAndType(labRoomId,0);
        if(reservationSetItem != null){
            mav.addObject("reservationSetupItem",reservationSetItem);
        }
        if (reservationSetItem == null || reservationSetItem.getOpenInweekend() == null
                || reservationSetItem.getOpenInweekend() != null && reservationSetItem.getOpenInweekend() == 0) {
            mav.addObject("openInweekend", 0);
        } else {
            mav.addObject("openInweekend", 1);
        }
        if (reservationSetItem != null && reservationSetItem.getStartHourInweek() != null) {
            Double startTime = reservationSetItem.getStartHourInweek().doubleValue();
            Integer startHour = (int) Math.floor(startTime);
            mav.addObject("startHour", startHour);
            mav.addObject("startMinute", (startTime - startHour) * 60);
        } else {
            mav.addObject("startHour", -1);
            mav.addObject("startMinute", -1);
        }
        if (reservationSetItem != null && reservationSetItem.getEndHourInweek() != null) {
            Double endTime = reservationSetItem.getEndHourInweek().doubleValue();
            Integer endHour = (int) Math.floor(endTime);
            mav.addObject("endHour", endHour);
            mav.addObject("endMinute", (endTime - endHour) * 60);
        } else {
            mav.addObject("endHour", -1);
            mav.addObject("endMinute", -1);
        }
        if (reservationSetItem != null && reservationSetItem.getStartHourInweekend() != null) {
            Double startWeekendTime = reservationSetItem.getStartHourInweekend().doubleValue();
            Integer startWeekendHour = (int) Math.floor(startWeekendTime);
            mav.addObject("startWeekendHour", startWeekendHour);
            mav.addObject("startWeekendMinute", (startWeekendTime - startWeekendHour) * 60);
        } else {
            mav.addObject("startWeekendHour", -1);
            mav.addObject("startWeekendMinute", -1);
        }
        if (reservationSetItem != null && reservationSetItem.getEndHourInweekend() != null) {
            Double endWeekendTime = reservationSetItem.getEndHourInweekend().doubleValue();
            Integer endWeekendHour = (int) Math.floor(endWeekendTime);
            mav.addObject("endWeekendHour", endWeekendHour);
            mav.addObject("endWeekendMinute", (endWeekendTime - endWeekendHour) * 60);
        } else {
            mav.addObject("endWeekendHour", -1);
            mav.addObject("endWeekendMinute", -1);
        }

        // 实验室禁用时间段列表
        mav.addObject("labRoomLimitTimes",
                labRoomLimitTimeDAO.executeQuery("select c from LabRoomLimitTime c where c.type=0 and c.labId= " + labRoomId, 0, -1));

        mav.setViewName("/lab/lab_room/editLabRoomOpenSetting.jsp");

        return mav;
    }

    /***********************************************************************************
     * description：实验室安全准入名单导入
     * @author Hezhaoyi
     * @日期：2019-4-15
     * **********************************************************************************/
    @RequestMapping("/device/saveImportSecurityAccess")
    public ModelAndView saveImportSecurityAccess(HttpServletRequest request){
        Integer labRoomId = Integer.parseInt(request.getParameter("labRoomId"));
        ModelAndView mav = new ModelAndView();
        String fileName = shareService.getUpdateFilePath(request);
        String logoRealPathDir = request.getSession().getServletContext().getRealPath("/");
        String filePath = logoRealPathDir + fileName;
        System.out.println(filePath);
        if(filePath.endsWith("xls")||filePath.endsWith("xlsx")){
            labRoomService.importSecurityAccess(filePath,labRoomId);
        }
        mav.setViewName("redirect:/labRoom/managePermitUser?labRoomId=" + labRoomId + "&currpage=1");
        return mav;
    }
	/****************************************************************************
	 * @功能：实验室管理---保存实验室设置
	 * @作者：孙虎
	 ****************************************************************************/
	@RequestMapping(value = "/device/saveLabRoomSettingRest/{labRoomId}/{page}/{type}/{needLoan1}/{needAudit1}/{needAllowSecurityAccess1}/{appointment1}/{realAllAudits}/{academies}", method = RequestMethod.GET)
	@ResponseBody
	public String editDeviceSettingRest(@PathVariable int labRoomId, @PathVariable int page, @PathVariable int type, @PathVariable int needLoan1, @PathVariable int needAudit1,
											  @PathVariable int needAllowSecurityAccess1, @PathVariable int appointment1,
											  @PathVariable String[] realAllAudits, @PathVariable String[] academies,
										Model model, @ModelAttribute("selected_academy") String acno) {
		// id对应的实验分室
		LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(labRoomId);
		if (needLoan1 != -1) {
			labRoom.setCDictionaryByAllowLending(cDictionaryDAO.findCDictionaryById(needLoan1));
			labRoom.setCDictionaryByIsAudit(cDictionaryDAO.findCDictionaryById(needAudit1));
		}
		String status = "success";

		//demo
		if (!"0".equals(realAllAudits[0])) {
			String[] RSWITCH = {"on", "off"};
			String rConfig = "";
			Map<String, String> params = new HashMap<>();
			String businessType = "LabRoomReservation" + labRoom.getLabCenter().getSchoolAcademy().getAcademyNumber();
			params.put("businessUid", labRoom.getId().toString());
			for (int i = 0; i < realAllAudits.length; i++) {
				rConfig += realAllAudits[i].equals("1") && needAudit1 != -1 ? RSWITCH[0] + "," : RSWITCH[1] + ",";
			}
			params.put("businessUid", labRoom.getId().toString());
			params.put("config", rConfig);
			params.put("businessType", pConfig.PROJECT_NAME + businessType);
			String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveBusinessAuditConfigs", params);
			JSONObject jsonObject = JSON.parseObject(s);
			status = jsonObject.getString("status");
			JSONArray jsonArray = jsonObject.getJSONArray("data");
		}
		if (needAudit1 != -1) {
			labRoom.setCDictionaryByIsAudit(cDictionaryDAO.findCDictionaryById(needAudit1));
			//实验室预约设置为不需要审核时将审核服务审核层级关闭
            if(needAudit1 == 622){
                String[] RSWITCH = {"on", "off"};
                String offConfig = "";
                Map<String, String> params = new HashMap<>();
                String businessType = "LabRoomReservation" + labRoom.getLabCenter().getSchoolAcademy().getAcademyNumber();
                params.put("businessUid", labRoom.getId().toString());
                for (int i = 0; i < realAllAudits.length; i++) {
                    offConfig += RSWITCH[1] + ",";
                }
                params.put("businessUid", labRoom.getId().toString());
                params.put("config", offConfig);
                params.put("businessType", pConfig.PROJECT_NAME + businessType);
                String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveBusinessAuditConfigs", params);
                JSONObject jsonObject = JSON.parseObject(s);
                status = jsonObject.getString("status");
                JSONArray jsonArray = jsonObject.getJSONArray("data");
            }
		}
		if (needAllowSecurityAccess1 != -1) {
			labRoom.setCDictionaryByAllowSecurityAccess(cDictionaryDAO.findCDictionaryById(needAllowSecurityAccess1));
		}
		if (appointment1 != -1) {
			labRoom.setLabRoomReservation(appointment1);
		}

		// 保存开放学院
		Set<SchoolAcademy> schoolAcademies = new HashSet<>();
		if (academies != null && academies.length != 0 && !"-1".equals(academies[0])) {
			for (String s : academies) {
				if(s.equals("-2")) {//全校
					schoolAcademies.addAll(schoolAcademyDAO.findAllSchoolAcademys());
					break;
				}else {
					SchoolAcademy schoolAcademy = schoolAcademyDAO.findSchoolAcademyByAcademyNumber(s);
					schoolAcademies.add(schoolAcademy);
				}
			}
		}
		labRoom.setOpenSchoolAcademies(schoolAcademies);

		labRoomDAO.store(labRoom);
		return status;
	}

    /****************************************************************************
     * @Description：实验室管理---保存工位预约实验室审核设置
     * @Author：Hezhaoyi
     * 2019-6-3
     ****************************************************************************/
    @RequestMapping(value = "/device/saveLabRoomStationReserSetting/{labRoomId}/{page}/{type}/{needAudit}/{realAllAudits}", method = RequestMethod.GET)
    @ResponseBody
    public String saveLabRoomStationReserSetting(@PathVariable int labRoomId, @PathVariable int page, @PathVariable int type, @PathVariable int needAudit,
                                        @PathVariable String[] realAllAudits,
                                        Model model, @ModelAttribute("selected_academy") String acno) {
        // id对应的实验分室
        LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(labRoomId);
        String status = "success";

        //demo
        if (!"0".equals(realAllAudits[0])) {
            String[] RSWITCH = {"on", "off"};
            String rConfig = "";
            Map<String, String> params = new HashMap<>();
            String businessType = "StationReservation";
            params.put("businessUid", labRoom.getId().toString());
            for (int i = 0; i < realAllAudits.length; i++) {
                rConfig += realAllAudits[i].equals("1") && needAudit != -1 ? RSWITCH[0] + "," : RSWITCH[1] + ",";
            }
            params.put("businessUid", labRoom.getId().toString());
            params.put("config", rConfig);
            params.put("businessType", pConfig.PROJECT_NAME + businessType);
            String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveBusinessAuditConfigs", params);
            JSONObject jsonObject = JSON.parseObject(s);
            status = jsonObject.getString("status");
            JSONArray jsonArray = jsonObject.getJSONArray("data");
        }
        if (needAudit != -1) {
            labRoom.setCDictionaryByIsStationAudit(cDictionaryDAO.findCDictionaryById(needAudit));
            if(needAudit == 622){
                String[] RSWITCH = {"on", "off"};
                String offConfig = "";
                Map<String, String> params = new HashMap<>();
                String businessType = "StationReservation";
                params.put("businessUid", labRoom.getId().toString());
                for (int i = 0; i < realAllAudits.length; i++) {
                    offConfig += RSWITCH[1] + ",";
                }
                params.put("businessUid", labRoom.getId().toString());
                params.put("config", offConfig);
                params.put("businessType", pConfig.PROJECT_NAME + businessType);
                String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveBusinessAuditConfigs", params);
                JSONObject jsonObject = JSON.parseObject(s);
                status = jsonObject.getString("status");
                JSONArray jsonArray = jsonObject.getJSONArray("data");
            }
        }
        labRoomDAO.store(labRoom);
        return status;
    }

	/****************************************************************************
	 * @功能：设置-实验室图片
	 * @作者：孙虎
	 ****************************************************************************/
	@RequestMapping(value = "/labRoomSetting/editLabRoomImageRest/{labRoomId}/{currpage}/{type}", method = RequestMethod.GET)
	public ModelAndView editDeviceImageRest(@PathVariable int labRoomId,@PathVariable int currpage,@PathVariable int type,Model model,
			@ModelAttribute("selected_academy") String acno) {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		// id对应的实验分室
		LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(labRoomId);
		mav.addObject("device", labRoom);
		mav.setViewName("/labroom/lab_room_reservation/labroomImage.jsp");		
		mav.addObject("labRoomId", labRoomId);
		mav.addObject("currpage", currpage);
		mav.addObject("type",type);
		return mav;
	}
	/****************************************************************************
	 * @功能：设置-实验室视频
	 * @作者：孙虎
	 ****************************************************************************/
	@RequestMapping(value = "/labRoomSetting/editLabRoomVideoRest/{labRoomId}/{currpage}/{type}", method = RequestMethod.GET)
	public ModelAndView editDeviceVideoRest(@PathVariable int labRoomId,@PathVariable int currpage,@PathVariable int type,Model model,
			@ModelAttribute("selected_academy") String acno) {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		// id对应的实验分室
		LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(labRoomId);
		mav.addObject("device", labRoom);
		mav.setViewName("/labroom/lab_room_reservation/labroomVideo.jsp");
		mav.addObject("labRoomId", labRoomId);
		mav.addObject("currpage", currpage);
		mav.addObject("type",type);
		return mav;
	}
	/****************************************************************************
	 * @功能：设置-实验室文档
	 * @作者：孙虎
	 ****************************************************************************/
	@RequestMapping(value = "/labRoomSetting/editLabRoomDocumentRest/{labRoomId}/{currpage}/{type}", method = RequestMethod.GET)
	public ModelAndView editDeviceDocumentRest(@PathVariable int labRoomId,@PathVariable int currpage,@PathVariable int type,Model model,
			@ModelAttribute("selected_academy") String acno) {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		// id对应的实验分室
		LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(labRoomId);
		mav.addObject("device", labRoom);
		mav.setViewName("/labroom/lab_room_reservation/labroomDocument.jsp");
		mav.addObject("labRoomId", labRoomId);
		mav.addObject("currpage", currpage);
		mav.addObject("type",type);
		return mav;
	}
    /****************************************************************************
     * description：保存修改的实验室设备开放设置--开放设置
     *
     * @author：李小龙 @date：2014-07-29
     ****************************************************************************/
    @RequestMapping(value = "/labRoomSetting/saveLabRoomDeviceOpenTime", method = RequestMethod.POST)
    public ModelAndView saveLabRoomDeviceOpenTime(@ModelAttribute LabRoom labRoom, Integer id,
                                                  HttpServletRequest request) {
        String startHour = request.getParameter("startHour");
        String startMinute = request.getParameter("startMinute");
        String endHour = request.getParameter("endHour");
        String endMinute = request.getParameter("endMinute");
        String startHourInweekend = request.getParameter("startHourInweekend");
        String endHourInweekend = request.getParameter("endHourInweekend");
        String startMinuteInweekend = request.getParameter("startMinuteInweekend");
        String endMinuteInweekend = request.getParameter("endMinuteInweekend");
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // 只要能进来修改，证明都是开放的，所以，再将isUsed设置为1.否则会被默认设置为null
        /*int id = labRoom.getId();*/
        LabRoom labRoomNew = labRoomService.findLabRoomByPrimaryKey(id);

        if (request.getParameter("startHour") != null) {
            labRoomNew.setStartHour(new BigDecimal(Double.valueOf(startHour) + Double.valueOf(startMinute) / 60.0));
        }
        if (request.getParameter("endHour") != null) {
            labRoomNew.setEndHour(new BigDecimal(Double.valueOf(endHour) + Double.valueOf(endMinute) / 60.0));
        }
        if (request.getParameter("openInweekend") != null) {
            labRoomNew.setOpenInweekend(Integer.parseInt(request.getParameter("openInweekend")));
            if (request.getParameter("openInweekend").equals("1")) {
                if (request.getParameter("startHourInweekend") != null) {
                    labRoomNew.setStartHourInweekend(new BigDecimal(
                            Double.valueOf(startHourInweekend) + Double.valueOf(startMinuteInweekend) / 60.0));
                }
                if (request.getParameter("endHourInweekend") != null) {
                    labRoomNew.setEndHourInweekend(new BigDecimal(
                            Double.valueOf(endHourInweekend) + Double.valueOf(endMinuteInweekend) / 60.0));
                }
            }
        }
        labRoomDAO.store(labRoomNew);
        mav.setViewName("redirect:/labRoom/getLabRoom?type=1&currpage=1&id="+ id);
        return mav;
    }
    /****************************************************************************
     * description：保存修改的实验室开放设置--开放设置
     *
     *  @author：Hezhaoyi @date：2019-4-22
     ****************************************************************************/
    @RequestMapping(value = "/labRoomSetting/saveLabRoomOpenTime", method = RequestMethod.POST)
    public ModelAndView saveLabRoomOpenTime(HttpServletRequest request) {
        String startHour = request.getParameter("startHour");
        String startMinute = request.getParameter("startMinute");
        String endHour = request.getParameter("endHour");
        String endMinute = request.getParameter("endMinute");
        String startHourInweekend = request.getParameter("startHourInweekend");
        String endHourInweekend = request.getParameter("endHourInweekend");
        String startMinuteInweekend = request.getParameter("startMinuteInweekend");
        String endMinuteInweekend = request.getParameter("endMinuteInweekend");
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();

		int roomId = Integer.parseInt(request.getParameter("roomId"));

        ReservationSetItem reservationSetItem = new ReservationSetItem();
        if(request.getParameter("id") != null && request.getParameter("id") != ""){
            int id = Integer.parseInt(request.getParameter("id"));
            if(reservationSetItemDAO.findReservationSetItemById(id) != null){
				reservationSetItem = reservationSetItemDAO.findReservationSetItemById(id);
			}
        }
        reservationSetItem.setLabRoomId(roomId);
        //标志位为0 为实验室预约配置项
        reservationSetItem.setItemType(0);
        if (request.getParameter("startHour") != null) {
            reservationSetItem.setStartHourInweek(new BigDecimal(Double.valueOf(startHour) + Double.valueOf(startMinute) / 60.0));
        }
        if (request.getParameter("endHour") != null) {
            reservationSetItem.setEndHourInweek(new BigDecimal(Double.valueOf(endHour) + Double.valueOf(endMinute) / 60.0));
        }
        if (request.getParameter("openInweekend") != null) {
            reservationSetItem.setOpenInweekend(Integer.parseInt(request.getParameter("openInweekend")));
            if (request.getParameter("openInweekend").equals("1")) {
                if (request.getParameter("startHourInweekend") != null) {
                    reservationSetItem.setStartHourInweekend(new BigDecimal(
                            Double.valueOf(startHourInweekend) + Double.valueOf(startMinuteInweekend) / 60.0));
                }
                if (request.getParameter("endHourInweekend") != null) {
                    reservationSetItem.setEndHourInweekend(new BigDecimal(
                            Double.valueOf(endHourInweekend) + Double.valueOf(endMinuteInweekend) / 60.0));
                }
            }
        }
        reservationSetItemDAO.store(reservationSetItem);

        mav.setViewName("redirect:/device/editLabRoomOpenSettingRest/" + roomId +"/1/1");
        return mav;
    }

    /****************************************************************************
     * 功能：删除实验室禁用时间段 作者：魏诚--实验室设备预约禁用时间段
     ****************************************************************************/
    @RequestMapping(value = "/labRoomSetting/deleteLabRoomDeviceLimitTime", method = RequestMethod.GET)
    public ModelAndView deleteLabRoomDeviceLimitTime(@RequestParam Integer id,Integer labRoomId) {
        ModelAndView mav = new ModelAndView();
        // id对应的实验室管理员
        LabRoomLimitTime labRoomLimitTime = labRoomLimitTimeDAO.findLabRoomLimitTimeByPrimaryKey(id);

        labRoomLimitTimeDAO.remove(labRoomLimitTime);

        mav.setViewName("redirect:/labRoom/getLabRoom?type=1&currpage=1&id="+ labRoomId);    //返回页面
        return mav;
    }

    /****************************************************************************
     * 功能：删除实验室禁用时间段 作者：魏诚--实验室预约禁用时间段
     * update Hezhaoyi   2019-4-22
     ****************************************************************************/
    @RequestMapping(value = "/labRoomSetting/deleteLabRoomLimitTime", method = RequestMethod.GET)
    public ModelAndView deleteLabRoomLimitTime(@RequestParam Integer id,Integer labRoomId) {
        ModelAndView mav = new ModelAndView();
        // id对应的实验室管理员
        LabRoomLimitTime labRoomLimitTime = labRoomLimitTimeDAO.findLabRoomLimitTimeByPrimaryKeyAndType(id,0);

        labRoomLimitTimeDAO.remove(labRoomLimitTime);

        mav.setViewName("redirect:/device/editLabRoomOpenSettingRest/" + labRoomId +"/1/1");   //返回页面
        return mav;
    }
    /****************************************************************************
     * Description：保存实验室禁用时间段--实验室设备预约禁用时间段
     *
     * @作者：魏诚
     * @时间：2016-03-05
     ****************************************************************************/
	@RequestMapping(value = "/labRoomSetting/saveLabRoomDeviceLimitTime", method = RequestMethod.POST)
    public ModelAndView saveLabRoomDeviceLimitTime(HttpServletRequest request,
                                             @ModelAttribute LabRoomLimitTime labRoomLimitTime)
            throws Exception {
        ModelAndView mav = new ModelAndView();
        //保存禁用时间
        mySQLService.saveLabRoomLimitTime(request,labRoomLimitTime,1);
        String roomId = request.getParameter("labId");
        mav.setViewName("redirect:/labRoom/getLabRoom?type=1&currpage=1&id="+ roomId);
        return mav;
    }
    /****************************************************************************
     * Description：保存实验室禁用时间段--实验室预约禁用时间段
     *
     * @作者：魏诚
     * @时间：2016-03-05
     * update Hezhaoyi 2019-4-22
     ****************************************************************************/
    @RequestMapping(value = "/labRoomSetting/saveLabRoomLimitTime", method = RequestMethod.POST)
    public ModelAndView saveLabRoomLimitTime(HttpServletRequest request,
                                             @ModelAttribute LabRoomLimitTime labRoomLimitTime)
            throws Exception {
        ModelAndView mav = new ModelAndView();
        //保存禁用时间
        mySQLService.saveLabRoomLimitTime(request,labRoomLimitTime,0);
        String roomId = request.getParameter("labId");
        mav.setViewName("redirect:/device/editLabRoomOpenSettingRest/" + roomId +"/1/1");
        return mav;
    }
    /****************************************************************************
	 * @throws UnsupportedEncodingException 
	 * @功能：删除实验室图片
	 * @作者：孙虎
	 ****************************************************************************/
	@RequestMapping(value = "/device/deleteDeviceDocumentRest/{id}/{currpage}/{type}", method = RequestMethod.GET)
	public ModelAndView deleteDeviceDocumentRest(@PathVariable int id, @PathVariable int currpage,@PathVariable int type,Model model,
			@ModelAttribute("selected_academy") String acno) throws UnsupportedEncodingException  {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		// labRoomId对应的实验室图片
		CommonDocument doc = commonDocumentService.findCommonDocumentByPrimaryKey(id);
		// 图片所属的设备
		LabRoom labRoom = doc.getLabRoom();
		//int idkey = labRoom.getId();
		commonDocumentService.deleteCommonDocument(doc);
		// 删除服务器上的文件
		int type1 = doc.getType();
		mav.addObject("device", labRoom);	
		mav.addObject("labRoomId", labRoom.getId());
		mav.addObject("currpage", currpage);
		// 中心所属学院
		//String academy = labCenterDAO.findLabCenterByPrimaryKey(cid).getSchoolAcademy().getAcademyNumber();
	
			if (type1 == 1 || type1 ==4) {// 图片
				mav.setViewName("redirect:/labRoomSetting/editLabRoomImageRest/" + labRoom.getId()+"/"+ currpage+"/"+type);
			} else {// 文档
				//mav.setViewName("redirect:/device/deviceDocument?deviceId=" + idkey);
				mav.setViewName("redirect:/labRoomSetting/editLabRoomDocumentRest/" + labRoom.getId()+"/"+ currpage+"/"+type);
			}
		return mav;
	}
	
	/****************************************************************************
	 * @功能：给设备上传视频
	 * @作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/labRoomSetting/labRoomVideoUpload")
	public @ResponseBody String deviceVideoUpload(HttpServletRequest request, HttpServletResponse response, BindException errors, Integer id) throws Exception {
		labRoomService.deviceVideoUpload(request, response, id);
		return "ok";
	}
	/****************************************************************************
	 * @throws UnsupportedEncodingException 
	 * @功能：删除实验室视频
	 * @作者：孙虎
	 ****************************************************************************/
	@RequestMapping(value = "/device/deleteLabRoomVideoRest/{id}/{currpage}/{type}", method = RequestMethod.GET)
	public ModelAndView deleteLabRoomVideoRest(@PathVariable int id, @PathVariable int currpage,@PathVariable int type,Model model,
			@ModelAttribute("selected_academy") String acno) throws UnsupportedEncodingException  {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		// id对应的设备视频
		CommonVideo video = commonVideoService.findVideoByPrimaryKey(id);
		// 图片所属的设备
		LabRoom labroom = video.getLabRoom();
		//int idkey = labroom.getId();
		commonVideoService.deleteCommonVideo(video);
		// 删除服务器上的文件

		// 中心所属学院
		//String academy = labCenterDAO.findLabCenterByPrimaryKey(cid).getSchoolAcademy().getAcademyNumber();
		// 根据学院编号判断是否为配置文件配置的特殊学院（化工学院）
		//boolean flag = specialAcademy.contains(academy);
		/*if (flag) {
			
		} else {
			mav.setViewName("redirect:/device/editDeviceReservationInfo?id=" + idkey);
		}*/
		mav.setViewName("redirect:/labRoomSetting/editLabRoomVideoRest/" + labroom.getId() + "/"+currpage+"/"+type);

		mav.addObject("labRoomId", labroom.getId());
		mav.addObject("currpage", currpage);
		return mav;
	}
	/****************************************************************************
	 * @功能：给实验室上传文档
	 * @作者：孙虎
	 ****************************************************************************/
	@RequestMapping("/labRoomSetting/labRoomDocumentUpload")
	public ModelAndView deviceDocumentUpload(HttpServletRequest request, HttpServletResponse response, BindException errors, Integer id,int type) throws Exception {
		labRoomService.labRoomDocumentUpload(request, response, id, 2);
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		int currpage=1;
		mav.setViewName("redirect:/labRoomSetting/editLabRoomDocumentRest/" + id+"/"+ currpage+"/"+type);
		return mav;
	}
	
	/*****************************************
	 * Description:返回实验室的软件列表
	 * 
	 * @param: labId 实验室编号
	 * @author: 邵志峰 
	 * @date： 2017-08-16
	 **********************************************/
	@RequestMapping("getLabSoftwareList")
	@ResponseBody
	public Set<Software> getLabSoftwareList(@RequestParam Integer labId){
		return labRoomDAO.findLabRoomById(labId).getSoftwares();
	}
	/****************************************************************************
	 * @throws Exception 
	 * @description：实训室预约信誉登记
	 * @author：孙虎
	 * @date：2017-08-16
	 ****************************************************************************/
	@RequestMapping("/lab/labRoomReservationCredit")
	public ModelAndView labRoomCredit(@RequestParam  Integer id) {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		// id对应的预约
		LabReservation reservation = labReservationDAO.findLabReservationByPrimaryKey(id);
		mav.addObject("reservation", reservation);
		// 当前登录人
		User user = shareService.getUser();
		String username = user.getUsername();
		username = "[" + username + "]";
		mav.addObject("user", user);
		mav.addObject("username", username);
		mav.addObject("audit", new LabRoomDeviceReservationResult());
		// 结果
		List<CDictionary> results = shareService.getCDictionaryData("c_training_result");
		mav.addObject("results", results);
		//取得所有扣分项
		List<CreditOption> creditOption=creditOptionService.findAllCreditOptionByQuery();
		mav.addObject("listCreditOption",creditOption);
		//取得当前预约纪录的扣分项
		List<CreditOption> listCreditOptions=new ArrayList<CreditOption>();
	    List<String> remarks=new ArrayList<String>();
	    String remark="";
		List<LabRoomReservationCredit> labRoomDeviceReservationCreditOption= labRoomReservationService.findlabRoomReservationCreditOptionById(id);
		for(LabRoomReservationCredit labRoomReservationCredit:labRoomDeviceReservationCreditOption)
		{
			remark=labRoomReservationCredit.getRemark();
			CreditOption creditoption=labRoomReservationCredit.getCreditOption();
			remarks.add(remark);
			listCreditOptions.add(creditoption);
		}
		mav.addObject("remark",remark);
		mav.addObject("listCreditOptions",listCreditOptions);
		// 再将状态信息传递给新的页面
		mav.addObject("isRest", 0);
		mav.setViewName("/labroom/lab_room_reservation/labRoomReservationCredit.jsp");
		return mav;
	
	}
	/****************************************************************************
	 * @throws Exception 
	 * @description：保存实训室信誉登记
	 * @author：孙虎
	 * @date：2017-08-16
	 ****************************************************************************/
	@RequestMapping("/lab/saveLabRoomDeviceReservationCredit")
	public String saveLabRoomDeviceReservationCredit(@RequestParam Integer reservationId ,@RequestParam String itemIds,@RequestParam String remark){
		
		LabRoomReservationCredit labRoomReservationCredit=new LabRoomReservationCredit();
		LabReservation labReservation = labReservationDAO.findLabReservationByPrimaryKey(reservationId);
		String[] ids = itemIds.split(",");
		for (String string : ids)
		{
			CreditOption creditOption= creditOptionService.findCreditOptionById(Integer.parseInt(string));
			labRoomReservationCredit.setCreditOption(creditOption);
			labRoomReservationCredit.setLabReservation(labReservation);
			labRoomReservationCredit.setRemark(remark);
			labRoomReservationCreditDAO.store(labRoomReservationCredit);
		}
		return "redirect:/lab/labRoomReservationCredit?id="+reservationId;//导入后
	}
	
	/****************************************************************************
	 * @功能：根据实训室id查询培训
	 * @作者：孙虎
	 ****************************************************************************/
	@RequestMapping(value = "/labRoomSetting/editLabRoomTrainingRest/{labRoomId}/{page}/{type}", method = RequestMethod.GET)
	public ModelAndView editLabRoomTrainingRest(@PathVariable int labRoomId,@PathVariable int page,@PathVariable int type, Model model,
			@ModelAttribute LabRoomTraining train,@ModelAttribute("selected_academy") String acno) {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		// 培训查询表单的对象
		mav.addObject("train", train);
		// 实训室id对应的实训室
		LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(labRoomId);
		mav.addObject("labRoom", labRoom);
		// 学期
		List<SchoolTerm> terms = shareService.findAllSchoolTerms();
		mav.addObject("terms", terms);
		// 当前登录人
		User user = shareService.getUser();
		mav.addObject("user", user);
		
		//判断当前登陆人是否为项目立项的审核人或者超级管理员
		String judge=",";
		for(Authority authority:user.getAuthorities()){
			judge = judge + "," + authority.getId() + "," ;
		}
		boolean isEXCENTERDIRECTOR;//是否为非本学院审核人或超级管理员
		if(judge.indexOf(",4,")>-1||judge.indexOf(",11,")>-1){
			isEXCENTERDIRECTOR = true;
		}else isEXCENTERDIRECTOR = false;
		mav.addObject("isEXCENTERDIRECTOR", isEXCENTERDIRECTOR);
		
		// 根据实训室id和培训对象查询培训
		List<LabRoomTraining> trainList = labRoomService.findLabRoomTrainingById(train,labRoomId);
		for (LabRoomTraining trainForTime : trainList) {
			Calendar traintime=trainForTime.getTime();
			Calendar systemtime=Calendar.getInstance();
			if (traintime!=null&&traintime.before(systemtime)) {
				//trainForTime.setCTrainingStatus(cTrainingStatusDAO.findCTrainingStatusByPrimaryKey(3));//如果培训时间早于系统时间，将培训状态设置为“已过期”
				trainForTime.setCDictionary(shareService.getCDictionaryByCategory("c_training_status", "3"));
				labRoomTrainingDAO.store(trainForTime);
			}
		}

		mav.addObject("trainList", trainList);
		// 当前登录人是否参加过培训
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		// 第一个培训的培训名单
		if (trainList.size() > 0) {
			for (LabRoomTraining t : trainList) {
				int i = t.getId();
				List<LabRoomTrainingPeople> peoples = labRoomService.findTrainingPeoplesByTrainingId(i);
				mav.addObject("peoples", peoples);
				int flag = 1;// 默认为1：未参加，0为已参加
				for (LabRoomTrainingPeople p : peoples) {
					if (p.getUser().getUsername().equals(user.getUsername())) {
						flag = 0;
						break;
					} else {
						flag = 1;
					}
				}
				map.put(i, flag);
			}

		}
		mav.addObject("map", map);
		//单独随时培训的学生
		LabRoomDevicePermitUsers student=new LabRoomDevicePermitUsers();
		mav.addObject("student", student);
		//单独培训学生名单
		/*Set<LabRoomDevicePermitUsers> students =device.getLabRoomDevicePermitUserses();
		List<User> studentsPass=new ArrayList<User>();
		for (LabRoomDevicePermitUsers ss : students) {
			studentsPass.add(ss.getUser());
		}
		mav.addObject("studentsPass", studentsPass);*/
		// 添加培训表单的培训教师
		List<User> users = shareService.findTheSameCollegeTeacher(user.getSchoolAcademy().getAcademyNumber());
		mav.addObject("users", users);

		mav.setViewName("/labroom/lab_room_reservation/labRoomTraining.jsp");// 统一到集中培训页面
		//if ((device.getCTrainingType()!=null&&device.getCTrainingType().getId()==1)||device.getCTrainingType()==null) {
		//}else mav.setViewName("/device/specialAcademy/deviceTrainingForSingle.jsp");
		
		
		mav.addObject("labRoomId", labRoomId);
		mav.addObject("currpage", page);
		mav.addObject("type",type);
		return mav;
	}
	/****************************************************************************
	 * @功能：取消实验室培训
	 * @作者：廖文辉
	 ****************************************************************************/
	@RequestMapping("/labRoomSetting/cancelTraining")
	public @ResponseBody
	String cancelTraining(@RequestParam Integer id) {
		// id对应的培训
		LabRoomTraining train = labRoomTrainingDAO.findLabRoomTrainingByPrimaryKey(id);
		CDictionary status = shareService.getCDictionaryByCategory("c_training_status", "4");
		train.setCDictionary(status);
		labRoomTrainingDAO.store(train);
		// 告知培训学生
		Set<LabRoomTrainingPeople> people = train.getLabRoomTrainingPeoples();
		if (people != null && people.size() > 0) {
			for (LabRoomTrainingPeople LabRoomTrainingPeople : people) {
				LabRoomTrainingPeople.setMessageFlag(2);
				labRoomTrainingPeopleDAO.store(LabRoomTrainingPeople);
			}
		}

		return "success";
	}
	/****************************************************************************
	 * @throws UnsupportedEncodingException
	 * @功能：删除培训
	 * @作者：廖文辉
	 * @时间：2018/9/28
	 ****************************************************************************/
	@RequestMapping(value = "/labRoomSetting/deleteTrainingRest/{labRoomId}/{page}/{type}/{id}", method = RequestMethod.GET)
	public ModelAndView deleteTrainingRest(@PathVariable int labRoomId,
										   @PathVariable int page,@PathVariable int type,@PathVariable int id
										 , @ModelAttribute("selected_academy") String acno) throws UnsupportedEncodingException  {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		// id对应的培训
		LabRoomTraining train = labRoomTrainingDAO.findLabRoomTrainingByPrimaryKey(id);
		labRoomTrainingDAO.remove(train);
		mav.setViewName("redirect:/labRoomSetting/editLabRoomTrainingRest/" + labRoomId+"/"+ page + "/" + type);
		mav.addObject("labRoomId", labRoomId);
		mav.addObject("page", page);
		mav.addObject("type",type);
		return mav;
	}
	/****************************************************************************
	 * @功能：保存实训室培训
	 * @作者：孙虎
	 ****************************************************************************/
	@RequestMapping("/labRoomSetting/saveLabRoomTraining")
	public ModelAndView saveLabRoomDeviceTraining(@ModelAttribute LabRoomTraining train, @RequestParam Integer labRoomId,@RequestParam Integer type, @ModelAttribute("selected_academy") String acno) {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		// id对应的实验分室设备
		LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(labRoomId);
		train.setLabRoom(labRoom);
		// 当前@时间所属学期为培训所属学期
		SchoolTerm term = shareService.getBelongsSchoolTerm(train.getTime());
		train.setSchoolTerm(term);
		// 状态设置为待培训
		//CTrainingStatus status = cTrainingStatusDAO.findCTrainingStatusByPrimaryKey(1);
		CDictionary status = shareService.getCDictionaryByCategory("c_training_status", "1");
		train.setCDictionary(status);
		// 参加人数默认为0
		train.setJoinNumber(0);
		if (train.getUser() != null && train.getUser().getUsername() != null && !train.getUser().getUsername().equals("")) {
			train.getUser().setUsername(train.getUser().getUsername());
		} else {
			train.setUser(null);
		}
		labRoomTrainingDAO.store(train);
		
		mav.setViewName("redirect:/labRoomSetting/editLabRoomTrainingRest/"+labRoomId+"/1"+"/"+type);

		return mav;
	}
	
	/****************************************************************************
	 * @功能：保存实训室培训
	 * @作者：孙虎
	 ****************************************************************************/
	@RequestMapping("/labRoomSetting/saveNewLabRoomTrainingNew")
	public ModelAndView saveNewLabRoomTrainingNew(@ModelAttribute LabRoomTraining train, @RequestParam Integer labRoomId,@RequestParam Integer type, @ModelAttribute("selected_academy") String acno) {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		// id对应的实验分室设备
		LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(labRoomId);
		train.setLabRoom(labRoom);
		// 状态设置为待培训
		//CTrainingStatus status = cTrainingStatusDAO.findCTrainingStatusByPrimaryKey(1);
		CDictionary status = shareService.getCDictionaryByCategory("c_training_status", "1");
		train.setCDictionary(status);
		// 参加人数默认为0
		train.setJoinNumber(0);
		if (train.getUser() != null && train.getUser().getUsername() != null && !train.getUser().getUsername().equals("")) {
			train.getUser().setUsername(train.getUser().getUsername());
		} else {
			train.setUser(null);
		}
		labRoomTrainingDAO.store(train);
		
		mav.setViewName("redirect:/labRoomSetting/editLabRoomTrainingRest/"+labRoomId+"/1"+"/"+type);

		return mav;
	}
	/************************************************************
	 * 功能：排课管理--分组管理--修改分批时间
	 * 作者：孙虎
	 * 日期：2017-08-17
	 * @throws ParseException 
	 ************************************************************/
	@RequestMapping("/labRoomSetting/altTrainTime")
	public @ResponseBody
	String altTrainTime(@RequestParam int id,HttpServletRequest request) throws ParseException {
		// id对应的培训
		LabRoomTraining train = labRoomTrainingDAO.findLabRoomTrainingByPrimaryKey(id);
		if (train==null) {
			return "error";
		}
		if (request.getParameter("begintime")==null||request.getParameter("begintime").equals("")) {
			return "error";
		}
		//字符串转日期
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date start =sdf.parse(request.getParameter("begintime"));//从前台将begintime取出来，然后转化成calendar格式
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(start);
		
		train.setTime(startDate);
        Calendar traintime=train.getTime();
        Calendar systemtime=Calendar.getInstance();
        if (traintime!=null&&traintime.before(systemtime)) {
            //trainForTime.setCTrainingStatus(cTrainingStatusDAO.findCTrainingStatusByPrimaryKey(3));//如果培训时间早于系统时间，将培训状态设置为“已过期”
            train.setCDictionary(shareService.getCDictionaryByCategory("c_training_status", "3"));
        }else{
            train.setCDictionary(shareService.getCDictionaryByCategory("c_training_status", "1"));
        }
		
		labRoomTrainingDAO.store(train);
		
		// 告知培训学生
		Set<LabRoomTrainingPeople> people = train.getLabRoomTrainingPeoples();
		if (people!=null && people.size()>0) {
			for (LabRoomTrainingPeople labRoomTrainingPeople : people) {
				labRoomTrainingPeople.setMessageFlag(1);
				labRoomTrainingPeopleDAO.store(labRoomTrainingPeople);
			}
		}
		
		return "success";
	}
	
	/****************************************************************************
	 * @功能：查看培训计划
	 * @作者：周志辉
	 ****************************************************************************/
	@RequestMapping(value = "/labReservation/viewLabRoomTrainingRest/{labRoomId}/{deviceNumber}/{deviceName}/{username}/{page}/{id}/{labRoom_allowAppointment}", method = RequestMethod.GET)
	public ModelAndView viewDeviceTrainingRest(@PathVariable int labRoomId,
			@PathVariable String deviceNumber, @PathVariable String deviceName, @PathVariable String username, 
			@PathVariable int page,@PathVariable int id,@PathVariable String labRoom_allowAppointment, Model model,
			@ModelAttribute LabRoomTraining train,
			@ModelAttribute("selected_academy") String acno) {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		// 培训查询表单的对象
		mav.addObject("train", train);
		// 设备id对应的设备
		LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(id);
		mav.addObject("labRoom", labRoom);
		// 当前登录人
		User user = shareService.getUser();
		mav.addObject("user", user);
		//判断当前登陆人是否为项目立项的审核人或者超级管理员
		String judge=",";
		for(Authority authority:user.getAuthorities()){
			judge = judge + "," + authority.getId() + "," ;
		}
		boolean isEXCENTERDIRECTOR;//是否为非本学院审核人或超级管理员
		if(judge.indexOf(",4,")>-1||judge.indexOf(",11,")>-1){
			isEXCENTERDIRECTOR = true;
		}else isEXCENTERDIRECTOR = false;
		mav.addObject("isEXCENTERDIRECTOR", isEXCENTERDIRECTOR);
		
		// 根据设备id和培训对象查询培训
		List<LabRoomTraining> trainList = labRoomService.findLabRoomTrainingByLabRoomId(train,
				id);
		for (LabRoomTraining trainForTime : trainList) {
			Calendar traintime=trainForTime.getTime();
			Calendar systemtime=Calendar.getInstance();
			if (traintime!=null&&traintime.before(systemtime)) {
				//trainForTime.setCTrainingStatus(cTrainingStatusDAO.findCTrainingStatusByPrimaryKey(3));//如果培训时间早于系统时间，将培训状态设置为“已过期”
				trainForTime.setCDictionary(shareService.getCDictionaryByCategory("c_training_status", "3"));//如果培训时间早于系统时间，将培训状态设置为“已过期”
				labRoomTrainingDAO.store(trainForTime);
			}
		}
		// 学期
		List<SchoolTerm> terms = shareService.findAllSchoolTerms();
		mav.addObject("terms", terms);
		mav.addObject("trainList", trainList);
		// 当前登录人是否参加过培训
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		// 第一个培训的培训名单
		if (trainList.size() > 0) {
			for (LabRoomTraining t : trainList) {
				int i = t.getId();
				List<LabRoomTrainingPeople> peoples = labRoomService.findTrainingPeoplesByTrainingId(i);
				mav.addObject("peoples", peoples);
				int flag = 1;// 默认为1：未参加，0为已参加
				for (LabRoomTrainingPeople p : peoples) {
					if (p.getUser().getUsername().equals(user.getUsername())) {
						flag = 0;
						break;
					} else {
						flag = 1;
					}
				}
				map.put(i, flag);
			}

		}
		mav.addObject("map", map);
		
		mav.addObject("labRoomTrainingPeople", new LabRoomTrainingPeople());
		mav.setViewName("/labroom/lab_room_reservation/viewLabRoomTraining.jsp");
		
		mav.addObject("labRoomId", id);
		mav.addObject("deviceName", deviceName);
		mav.addObject("deviceNumber", deviceNumber);
		mav.addObject("username", username);
		mav.addObject("labRoom_allowAppointment", labRoom_allowAppointment);
		mav.addObject("page", page);
		return mav;
	}
	/************************************************************
	 * 功能：判断该学生是否能进行该培训的预约 作者：周志辉 日期：2017-08-22
	 ************************************************************/
	@RequestMapping("/labReservation/ifPermitted")
	public @ResponseBody String ifPermitted(@RequestParam int labRoomId) {
		// 获取当前用户
		String username = shareService.getUser().getUsername();
		// 找到当前培训所对应的设备
		LabRoomPermitUser permitUser = labRoomService.findPermitUserByUsernameAndDeivce(username, labRoomId);
		if (permitUser != null) {
			return "permitted";
		} else {
			LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(labRoomId);
			for (LabRoomTraining training : labRoom.getLabRoomTrainings()) {
				if(training.getCDictionary().getId().equals(4)){// 预约被取消
					continue;
				}
				int trainingId = training.getId();
				List<LabRoomTrainingPeople> peoples = labRoomService.findTrainingPeoplesByTrainingId(trainingId);
				for (LabRoomTrainingPeople labRoomTrainingPeople : peoples) {
					if (!labRoomTrainingPeople.getUser().getUsername().equals(username)) {// 不是当前用户，继续
						continue;
					} else {// 是当前用户，判断是否已经通过
						if (labRoomTrainingPeople.getCDictionary() == null) {// 未有结果
						    return "wait";
						} else {
							if ("1".equals(labRoomTrainingPeople.getCDictionary().getCNumber())) {
								// 培训通过 但不在准入名单里
                                if(permitUser != null) {
                                    return "permitted";
                                }
							} else {
								// 未通过
								return "ok";
							}
						}
					}
				}
			}
		}
		return "ok";
	}
	/****************************************************************************
	 * @throws UnsupportedEncodingException 
	 * @功能：保存实验室培训人
	 * @作者：周志辉
	 ****************************************************************************/
	@RequestMapping(value = "/labReservation/joinTrainingRest/{labRoomId}/{type}/{deviceNumber}/{deviceName}/{username}/{page}/{trainingId}/{telephone}/{eMail}/{schoolDevice_allowAppointment}", method = RequestMethod.GET)
	public ModelAndView joinTrainingRest(@PathVariable int labRoomId,@PathVariable int type,
			@PathVariable String deviceNumber, @PathVariable String deviceName, @PathVariable String username,
			@PathVariable int page,@PathVariable int trainingId,
			@PathVariable String telephone, @PathVariable String eMail,@PathVariable String schoolDevice_allowAppointment, Model model, @ModelAttribute("selected_academy") String acno) throws UnsupportedEncodingException  {
		// 替换邮箱中的特殊字符
		eMail=shareService.replaceString(eMail);
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		// id对应的培训
		LabRoomTraining train = labRoomTrainingDAO.findLabRoomTrainingByPrimaryKey(trainingId);
		User user = shareService.getUser();
		LabRoomTrainingPeople people = new LabRoomTrainingPeople();
		people.setLabRoomTraining(train);
		people.setUser(user);
		people.setTelephone(telephone);
		people.setEMail(eMail);
		labRoomTrainingPeopleDAO.store(people);
		Set<LabRoomTrainingPeople> peoples = train.getLabRoomTrainingPeoples();
		train.setJoinNumber(peoples.size());
		labRoomTrainingDAO.store(train);
		
		Message message= new Message();
		Calendar date=Calendar.getInstance();
		message.setSendUser(shareService.getUserDetail().getCname());
		message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
		message.setCond(0);
		message.setTitle("实验室培训人员增加");
		String content="实验室培训人员增加";
		content+="<a onclick='changeMessage(this)' href='../labRoomSetting/editLabRoomTrainingRest/"+labRoomId+"/"+page+"/"+type+"'";		                                                                                ///device/viewDeviceTrainingRest/{labRoomId}/{deviceNumber}/{deviceName}/{username}/{page}/{id}/{schoolDevice_allowAppointment}
		content+=" >点击查看</a>";
		message.setContent("申请成功，等待审核<a onclick='changeMessage(this)' href='../operation/viewOperationItem?operationItemId=44956&&flag=1&status=2'>点击查看</a>");
		message.setContent(content);
		message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
		message.setCreateTime(date);
		message.setUsername(train.getUser().getUsername());
		message.setTage(2);
		message=messageDAO.store(message);
		
		mav.setViewName("redirect:/labReservation/viewLabRoomTrainingRest/" + labRoomId+"/"+ deviceNumber + "/" + URLEncoder.encode(deviceName,"utf-8") +"/" + username + "/"+page+"/" + train.getLabRoom().getId()+"/-1");
		mav.addObject("labRoomId", labRoomId);
		mav.addObject("deviceName", deviceName);
		mav.addObject("deviceNumber", deviceNumber);
		mav.addObject("username", username);
		mav.addObject("page", page);
		mav.addObject("schoolDevice_allowAppointment", schoolDevice_allowAppointment);
		return mav;
	}
	/****************************************************************************
	 * @throws UnsupportedEncodingException 
	 * @功能：取消培训预约
	 * @作者：贺子龙
	 ****************************************************************************/
	@RequestMapping(value = "/labReservation/cancleTrainingRest/{labRoomId}/{deviceNumber}/{deviceName}/{username}/{page}/{id}/{labRoom_allowAppointment}", method = RequestMethod.GET)
	public ModelAndView cancleTrainingRest(@PathVariable int labRoomId,
			@PathVariable String deviceNumber, @PathVariable String deviceName, @PathVariable String username,
			@PathVariable int page,@PathVariable int id,@PathVariable String labRoom_allowAppointment,
			Model model, @ModelAttribute("selected_academy") String acno) throws UnsupportedEncodingException  {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		// id对应的培训
		LabRoomTraining train = labRoomTrainingDAO.findLabRoomTrainingByPrimaryKey(id);
		// 取消培训预约
		labRoomService.cancleTraining(id);
		Set<LabRoomTrainingPeople> peoples = train.getLabRoomTrainingPeoples();
		train.setJoinNumber(peoples.size());
		labRoomTrainingDAO.store(train);
		mav.setViewName("redirect:/labReservation/viewLabRoomTrainingRest/" + labRoomId+"/"+ deviceNumber + "/" + URLEncoder.encode(deviceName,"utf-8") +"/" + username + "/"+page+"/" + train.getLabRoom().getId()+"/-1");
		
		mav.addObject("labRoomId", labRoomId);
		mav.addObject("deviceName", deviceName);
		mav.addObject("deviceNumber", deviceNumber);
		mav.addObject("username", username);
		mav.addObject("page", page);
		mav.addObject("schoolDevice_allowAppointment", labRoom_allowAppointment);
		return mav;
	}
	/****************************************************************************
	 * @功能：根据设备id查询培训
	 * @作者：周志辉
	 ****************************************************************************/
	@RequestMapping(value = "/labReservation/findTrainingPeopleByTrainIdRest/{labRoomId}/{deviceNumber}/{deviceName}/{username}/{page}/{id}/{tag}/{toChangeAudit}/{labRoom_allowAppointment}", method = RequestMethod.GET)
	public ModelAndView findTrainingPeopleByTrainIdRest(@PathVariable int labRoomId,
			@PathVariable String deviceNumber, @PathVariable String deviceName, @PathVariable String username, 
			@PathVariable int page,@PathVariable int id, @PathVariable int tag,@PathVariable int toChangeAudit,@PathVariable String labRoom_allowAppointment,Model model,
			@ModelAttribute("selected_academy") String acno) {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		// id对应的培训
		LabRoomTraining train = labRoomTrainingDAO.findLabRoomTrainingByPrimaryKey(id);
		mav.addObject("train", train);
		// 培训所属的设备
		mav.addObject("device", train.getLabRoom());
		List<LabRoomTrainingPeople> peoples = labRoomService.findTrainingPeoplesByTrainingId(id);
		mav.addObject("peoples", peoples);
		List<LabRoomTrainingPeople> passPeoples = labRoomService.findTrainingPassPeoplesByTrainingId(id);
		mav.addObject("passPeoples", passPeoples);
		// 培训结果
		//Set<CTrainingResult> results = cTrainingResultDAO.findAllCTrainingResults();
		List<CDictionary> results = shareService.getCDictionaryData("c_training_result");
		mav.addObject("results", results);
		mav.setViewName("/lab/lab_reservation/peopleList.jsp");
		mav.addObject("tag", tag);//1  edit  2  view
		mav.addObject("toChangeAudit", toChangeAudit);//1  edit  2  view
		
		
		mav.addObject("labRoomId", labRoomId);
		mav.addObject("deviceName", deviceName);
		mav.addObject("deviceNumber", deviceNumber);
		mav.addObject("username", username);
		mav.addObject("page", page);
		mav.addObject("schoolDevice_allowAppointment", labRoom_allowAppointment);
		return mav;
	}
	/****************************************************************************
	 * @throws UnsupportedEncodingException 
	 * @功能：保存实训室培训结果
	 * @作者：周志辉
	 ****************************************************************************/
	@RequestMapping(value = "/labReservation/saveTrainResultRest/{labRoomId}/{type}/{deviceNumber}/{deviceName}/{username}/{page}/{idStr}/{valueStr}/{labRoom_allowAppointment}",
			method = RequestMethod.GET)
	public ModelAndView saveTrainResultRest(@PathVariable int labRoomId,@PathVariable int type,
			@PathVariable String deviceNumber, @PathVariable String deviceName,@PathVariable String username, @PathVariable int page,
			@PathVariable String idStr, @PathVariable String valueStr,@PathVariable String labRoom_allowAppointment,
			Model model,@ModelAttribute("selected_academy") String acno) throws UnsupportedEncodingException  {
		String[] idArray = idStr.split("S");
		String[] valueArray = valueStr.split("S");
		
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		for (int i = 0; i < idArray.length; i++) {
			// 培训人
			LabRoomTrainingPeople p = labRoomTrainingPeopleDAO
					.findLabRoomTrainingPeopleByPrimaryKey(Integer.parseInt(idArray[i]));
			// 培训结果
			//CTrainingResult result = cTrainingResultDAO.findCTrainingResultByPrimaryKey(Integer.parseInt(valueArray[i]));
			CDictionary result = shareService.getCDictionaryByCategory("c_training_result", valueArray[i]);
			//p.setCTrainingResult(result);
			p.setCDictionary(result);
			// 执行保存
			labRoomTrainingPeopleDAO.store(p);
			
			//将通过的学生添加到LabRoomDevicePermitUsers中，flag为2
			//先将该用户从permitUser里面清除，防止重复添加
			if (p.getUser()!=null&&p.getLabRoomTraining()!=null
					&&p.getLabRoomTraining().getLabRoom()!=null) {
				String username1 = p.getUser().getUsername();
				int labRoomId1 = p.getLabRoomTraining().getLabRoom().getId();
				LabRoomPermitUser permitUser = labRoomService.findPermitUserByUsernameAndLabRoom(username1,labRoomId1);
				if (permitUser!=null) {
					labRoomService.deletePermitUser(permitUser);
				}
			}
			if (Integer.parseInt(valueArray[i])==1) {//通过
				//username对应的用户
				LabRoomPermitUser student=new LabRoomPermitUser();
				student.setUser(p.getUser());
				if (p.getLabRoomTraining()!=null&&p.getLabRoomTraining().getLabRoom()!=null) {
					student.setLabRoom(p.getLabRoomTraining().getLabRoom());
				}
				student.setFlag(2);//标记位（1 单独培训通过  2 集训通过  3 集训后门）
				labRoomPermitUserDAO.store(student);
			}else {//不通过
				//do nothing
			}
		}
		// 培训人对应的培训
		LabRoomTrainingPeople people = labRoomTrainingPeopleDAO
				.findLabRoomTrainingPeopleByPrimaryKey(Integer.parseInt(idArray[0]));
		LabRoomTraining train = people.getLabRoomTraining();
		// 该培训的培训人
		Set<LabRoomTrainingPeople> peoples = train.getLabRoomTrainingPeoples();
		// 根据培训id查询培训通过的人
		List<LabRoomTrainingPeople> passPeoples = labRoomService
				.findPassLabRoomTrainingPeopleByTrainId(train.getId());

		// 计算通过率
		double a = passPeoples.size();
		double b = peoples.size();
		double c = a / b;
		// 获取格式化对象
		NumberFormat nt = NumberFormat.getPercentInstance();
		// 设置百分数精确度2即保留两位小数
		nt.setMinimumFractionDigits(2);
		String s = nt.format(c);
		train.setPassRate(s);
		// 状态改为已完成
		//CTrainingStatus status = cTrainingStatusDAO.findCTrainingStatusByPrimaryKey(2);
		CDictionary status = shareService.getCDictionaryByCategory("c_training_status", "2");
		train.setCDictionary(status);
		labRoomTrainingDAO.store(train);
		mav.setViewName("redirect:/labRoomSetting/editLabRoomTrainingRest/"+labRoomId+"/"+page+"/"+type);
		
		mav.addObject("labRoomId", labRoomId);
		mav.addObject("deviceName", deviceName);
		mav.addObject("deviceNumber", deviceNumber);
		mav.addObject("username", username);
		mav.addObject("page", page);
		mav.addObject("labRoom_allowAppointment", labRoom_allowAppointment);
		return mav;
	}
	/****************************************************************************
	 * @throws ParseException
	 * @功能：安全准入验证
	 * @作者：周志辉
	 ****************************************************************************/
	@RequestMapping("/labReservation/securityAccess")
	public @ResponseBody String securityAccess(@RequestParam Integer id, HttpServletRequest request) throws ParseException {
		// 更新预约结果
		labRoomService.updateReservationResult(id);
		User user = shareService.getUser();
		String data = labRoomService.securityAccess(user.getUsername(), id, request);
		return shareService.htmlEncode(data);
	}
	
	
	
	@RequestMapping("/softwareAudit")
	public String softwareAudit(HttpServletRequest request,@RequestParam int Id, int result,int flag){
		//审核完成后给申请人发送消息
		Message message= new Message();
		//当前时间
		Calendar date=Calendar.getInstance();
		//记录审核结果
		//SoftwareReserveAudit softwareReserveAudit=new SoftwareReserveAudit();
		message.setCond(0);
		String content="";
		
		//当前软件申请记录
		SoftwareReserve softwareReserve=softwareReserveDAO.findSoftwareReserveById(Id);
		if(flag==4&&result==1)  //实训中心主任审核
		{
			//保存审核记录
			List<SoftwareReserveAudit> ss=labRoomService.findAllSoftwareReserveAudit(softwareReserve,shareService.getUser().getUsername());
			for(SoftwareReserveAudit soft:ss){
			soft.setStatus(1);
			softwareReserveAuditDAO.store(soft);
			}
			//申请通过
			softwareReserve.setApproveState(3);
			softwareReserveDAO.store(softwareReserve);
			//发送消息
			messageService.saveMessages(softwareReserve.getUser().getUsername(),"教学软件申请","申请"+
				softwareReserve.getName()+" 等教学软件成功，审核通过<a href='../labRoom/softwareReserveInfoList?idKey="+Id+"&flag=1&step=1"+"'>点击查看</a>",4);
			return "redirect:/labRoom/SoftwareReserveList?page=1";
		}
		else if(flag==1&&result==1){
			//保存审核记录
			List<SoftwareReserveAudit> ss=labRoomService.findAllSoftwareReserveAudit(softwareReserve,shareService.getUser().getUsername());
			for(SoftwareReserveAudit soft:ss){
			soft.setStatus(1);
			softwareReserveAuditDAO.store(soft);
			}
			
			int labroomid=softwareReserve.getLabRoom().getId();
			List<LabRoomAdmin> labRoomAdmins=labRoomAdminService.findAllLabRoomAdminsByLabRoomId(labroomid);
			for(LabRoomAdmin l:labRoomAdmins){
				//生成审核记录
				SoftwareReserveAudit softwareReserveAudit=new SoftwareReserveAudit();
				softwareReserveAudit.setSoftwareReserve(softwareReserve);
				softwareReserveAudit.setStatus(3);
				softwareReserveAudit.setCreateDate(date);
				softwareReserveAudit.setUser(l.getUser());
				softwareReserveAuditDAO.store(softwareReserveAudit);
			content="申请成功，等待二次审核";
			content+="<a onclick='changeMessage(this)' href='../labRoom/softwareReserveInfoList?idKey=";
			content+=Id;
			content+="&flag=2&step=2'>点击查看</a>";
			//发送消息
			messageService.saveMessages(l.getUser().getUsername(),"教学软件申请",content,1);
			
			}
			return "redirect:/labRoom/SoftwareReserveList?page=1";
		}
		else if(flag==2&&result==1){
			//保存审核记录
			List<SoftwareReserveAudit> ss=labRoomService.findAllSoftwareReserveAudit(softwareReserve,shareService.getUser().getUsername());
			for(SoftwareReserveAudit soft:ss){
			soft.setStatus(1);
			softwareReserveAuditDAO.store(soft);
			}
			//给实训中心主任发消息
			User user=new User();
			List<User> users=systemService.getUserByAuthority(user, 4);
			for(User u:users){
				//生成审核记录
				SoftwareReserveAudit softwareReserveAudit=new SoftwareReserveAudit();
				softwareReserveAudit.setSoftwareReserve(softwareReserve);
				softwareReserveAudit.setStatus(3);
				softwareReserveAudit.setCreateDate(date);
				softwareReserveAudit.setUser(u);
				softwareReserveAuditDAO.store(softwareReserveAudit);
			content="等待实训中心主任审核";
			content+="<a onclick='changeMessage(this)' href='../labRoom/softwareReserveInfoList?idKey=";
			content+=Id;
			content+="&flag=2&step=3'>点击查看</a>";
			//发送消息
			messageService.saveMessages(u.getUsername(),"教学软件申请",content,1);
			
			}
			return "redirect:/labRoom/SoftwareReserveList?page=1";
		}
		else if(flag==3&&result==1){
			//保存审核记录
			List<SoftwareReserveAudit> ss=labRoomService.findAllSoftwareReserveAudit(softwareReserve,shareService.getUser().getUsername());
			for(SoftwareReserveAudit soft:ss){
			soft.setStatus(1);
			softwareReserveAuditDAO.store(soft);
			}
			//给教学秘书发消息
			User user=new User();
			List<User> users=systemService.getUserByAuthority(user, 22);
			for(User u:users){
				//生成审核记录
				SoftwareReserveAudit softwareReserveAudit=new SoftwareReserveAudit();
				softwareReserveAudit.setSoftwareReserve(softwareReserve);
				softwareReserveAudit.setStatus(3);
				softwareReserveAudit.setCreateDate(date);
				softwareReserveAudit.setUser(u);
				softwareReserveAuditDAO.store(softwareReserveAudit);
			content="等待教学秘书审核";
			content+="<a onclick='changeMessage(this)' href='../labRoom/softwareReserveInfoList?idKey=";
			content+=Id;
			content+="&flag=2&step=4'>点击查看</a>";
			//发送消息
			messageService.saveMessages(u.getUsername(),"教学软件申请",content,1);
			
			}
			return "redirect:/labRoom/SoftwareReserveList?page=1";
		}
		else{//审核拒绝
			//保存审核记录
			List<SoftwareReserveAudit> ss=labRoomService.findAllSoftwareReserveAudit(softwareReserve,shareService.getUser().getUsername());
			for(SoftwareReserveAudit soft:ss){
			soft.setStatus(0);
			softwareReserveAuditDAO.store(soft);
			}
			//申请拒绝
			softwareReserve.setApproveState(2);
			softwareReserveDAO.store(softwareReserve);
			//发送消息
			messageService.saveMessages(softwareReserve.getUser().getUsername(),"教学软件申请","申请"+
				softwareReserve.getName()+" 等教学软件失败，审核拒绝 <a href='../labRoom/softwareReserveInfoList?idKey="+Id+"&flag=1&step=1"+"'>点击查看</a>",3);
		    return "redirect:/labRoom/SoftwareReserveList?page=1";
		}
	}
	/************************************************************
	 * 功能：排修改培训时间和地点
	 * 作者：廖文辉
	 * 日期：2018-09-28
	 * @throws ParseException
	 ************************************************************/
	@RequestMapping("/labRoomSetting/addTimeAndAddress")
	public @ResponseBody
	String addTimeAndAddress(@RequestParam int id,@RequestParam int page,@RequestParam int type, HttpServletRequest request) throws ParseException {
		// id对应的培训
		LabRoomTraining train = labRoomTrainingDAO.findLabRoomTrainingByPrimaryKey(id);
		if (train == null) {
			return "error";
		}
		if (request.getParameter("begintime") == null || request.getParameter("begintime").equals("")) {
			return "error";
		}
		//字符串转日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date start = sdf.parse(request.getParameter("begintime"));//从前台将begintime取出来，然后转化成calendar格式
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(start);

		train.setTime(startDate);
		train.setSchoolTerm(shareService.getBelongsSchoolTerm(startDate));
		train.setAddress(request.getParameter("address"));
		labRoomTrainingDAO.store(train);

		// 告知培训学生
		Set<LabRoomTrainingPeople> people = train.getLabRoomTrainingPeoples();
		if (people != null && people.size() > 0) {
			for (LabRoomTrainingPeople labRoomTrainingPeople : people) {
				Message message = new Message();
				Calendar date = Calendar.getInstance();
				message.setSendUser(shareService.getUserDetail().getCname());
				message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
				message.setCond(0);
				message.setTitle("培训申请确认");
				String content = "培训时间和地点已设置";
				content += "<a onclick='changeMessage(this)' href='../labRoomSetting/editLabRoomTrainingRest/"+train.getLabRoom().getId()+"/"+page+"/"+type+"'";

				content += " >点击查看</a>";
				message.setContent(content);
				message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
				message.setCreateTime(date);
				message.setUsername(labRoomTrainingPeople.getUser().getUsername());
				message = messageDAO.store(message);
			}
		}

		return "success";
	}
	/****************************************************************************
	 * @throws UnsupportedEncodingException
	 * @功能：实验室上传图片
	 * @作者：廖文辉
	 * @时间：2018/12/13
	 ****************************************************************************/
	@RequestMapping("/labRoom/uploadImageForLabRoom")
	public ModelAndView uploadImageForLabRoom(HttpServletRequest request, HttpServletResponse response, int labRoomId,int currpage,int type) throws UnsupportedEncodingException {
		ModelAndView mav = new ModelAndView();
		visualizationService.uploadImageForLabRoom(request, response, labRoomId, 4);
		mav.setViewName("redirect:/labRoomSetting/editLabRoomImageRest/" + labRoomId + "/" + currpage + "/" +type);
		return mav;
	}
    /****************************************************************************
     * @throws UnsupportedEncodingException
     * @功能：实验室上传视频
     * @作者：廖文辉
     * @时间：2018/12/13
     ****************************************************************************/
    @RequestMapping("/labRoom/uploadVideoForLabRoom")
    public ModelAndView uploadVideoForLabRoom(HttpServletRequest request, HttpServletResponse response, int labRoomId,int currpage,int type) throws UnsupportedEncodingException {
        ModelAndView mav = new ModelAndView();
        labRoomService.deviceVideoUpload(request, response, labRoomId);
        mav.setViewName("redirect:/labRoomSetting/editLabRoomVideoRest/" + labRoomId + "/" + currpage + "/" +type);
        return mav;
    }
	/**
	 * @Description: 获取指定实验室下所有的门禁记录
	 * @Author: 徐明杭
	 * @CreateDate: 2019/4/9 9:07
	 */
	@RequestMapping("/lab/entranceListAll")
	public ModelAndView entranceListAll(@RequestParam Integer page,Integer labRoomId,@ModelAttribute CommonHdwlog commonHdwlog, HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");

		//默认获取最近一个月记录
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		if(StringUtils.isEmpty(endtime)){
			request.setAttribute("endtime",format.format(c.getTime()));
		} else{
			request.setAttribute("endtime",endtime);
		}
		if(StringUtils.isEmpty(starttime)){
			c.add(Calendar.MONTH, -1);
			request.setAttribute("starttime",format.format(c.getTime()));
		} else{
			request.setAttribute("starttime",starttime);
		}

		mav.addObject("labRoomId",labRoomId);
		List<LabRoomAgent> labRoomAgents = labRoomService.findLabRoomAgentByRoomId(labRoomId);
		int pageSize = 30;
		int totalRecords = 0;
		List<LabAttendance> accessListAll = new ArrayList<LabAttendance>();
		int setPage = 0;
		long start = System.currentTimeMillis();
		for(LabRoomAgent temp:labRoomAgents){
			String ip=temp.getHardwareIp();
			// 新版从iot获取数据
			totalRecords += cmsShowService.findIotAttendanceByIpCount(commonHdwlog,ip,request,setPage,pageSize);
			//页面显示的实验室
			accessListAll.addAll(cmsShowService.findIotAttendanceByIp(commonHdwlog,ip,request,page,pageSize));
		}
		long end = System.currentTimeMillis();
		System.out.println("运行时间:"+(end-start));
		Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
		mav.addObject("accessListAll",accessListAll);
		mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", page);
		mav.addObject("pageSize", pageSize);
		mav.setViewName("lab/lab_record/listAllLabRoomEntrance.jsp");
		return mav;
	}
	/**
	 * @Description: 导出指定实验室的考勤记录到execl表中
	 * @Author: 徐明杭
	 * @CreateDate: 2019/4/10 11:31
	 */
	@RequestMapping("/lab/exportEntranceList")
	public void exportEntranceList(@RequestParam Integer labRoomId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		List<LabRoomAgent> labRoomAgents = labRoomService.findLabRoomAgentByRoomId(labRoomId);
		CommonHdwlog commonHdwlog = new CommonHdwlog();
		List<net.zjcclims.common.LabAttendance> accessListAll = new ArrayList<LabAttendance>();
		for(LabRoomAgent temp:labRoomAgents){
			String ip=temp.getHardwareIp();
			//页面显示的实验室
			accessListAll.addAll(cmsShowService.findIotAttendanceByIp(commonHdwlog,ip,request,0,0));
		}
		labRoomService.exportEntranceList(accessListAll,request,response);
	}
}