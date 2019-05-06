/****************************************************************************
 * 命名规范：
 * 本控制层调整必须是按照如下的路径调整：@RequestMapping("report/teachingReport/sample")，全部小写。
 * 列表信息listxxxx，獲取信息：getxxxx 编辑信息：editxxxx 删除信息：detelexxxx 新增信息 newxxxx
 * 导出信息exportxxxx 保存信息：savexxxx  
 ****************************************************************************/

package net.zjcclims.web.report;

import net.zjcclims.constant.MonthReport;
import net.zjcclims.dao.LabCenterDAO;
import net.zjcclims.dao.SchoolTermDAO;
import net.zjcclims.dao.TimetableAppointmentDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.report.ReportService;
import net.zjcclims.service.report.TeachingReportService;
import net.zjcclims.service.system.SchoolWeekService;
import net.zjcclims.service.timetable.OuterApplicationService;
import net.zjcclims.service.timetable.SchoolCourseDetailService;
import net.zjcclims.service.timetable.TimetableAppointmentService;
import net.zjcclims.vo.QueryParamsVO;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/****************************************************************************
 * 功能：教学报表
 * 绩效报表模块 作者：贺子龙 时间：2015-11-16
 ****************************************************************************/
@Controller("TeachingReportController")
@SessionAttributes("selected_academy")
public class TeachingReportController<JsonResult> {
	
	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register static property editors.
		binder.registerCustomEditor(java.util.Calendar.class, new org.skyway.spring.util.databinding.CustomCalendarEditor());
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
	private ShareService shareService;
	@Autowired
	private SchoolWeekService schoolWeekService;
	@Autowired
	private SchoolCourseDetailService schoolCourseDetailService;
	@Autowired
	private TeachingReportService teachingReportService;
	@Autowired
	private TimetableAppointmentService timetableAppointmentService;
	@Autowired
	private LabCenterDAO labCenterDAO;
	@Autowired
	private TimetableAppointmentDAO timetableAppointmentDAO;
	@Autowired
	private SchoolTermDAO schoolTermDAO;
	@Autowired
	private ReportService reportService;
	@Autowired
	private PConfig pConfig;
	
	/************************************************************
	 * @学期登记报表
	 * @作者：何立友
	 * @日期：2014-09-11
	 ************************************************************/
	@RequestMapping("/report/teachingReport/termRegister")
	public ModelAndView termRegister(@ModelAttribute SchoolTerm schoolTerm,@ModelAttribute("selected_academy") String acno, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		Map<Integer, String> map = outerApplicationService.getLabRoomMap(acno);
		// 进行实验室筛选
		Map<Integer, String> showMap = new HashMap<>();
		String labRoomIdStr = request.getParameter("labRoom");
		if(labRoomIdStr != null && !"".equals(labRoomIdStr)){
			Integer labRoomId = Integer.parseInt(labRoomIdStr);
			showMap.put(labRoomId, map.get(labRoomId));
		}else{
			showMap.putAll(map);
		}
		// 获取去重的实验分室列表
		mav.addObject("labRoomMap", showMap);
		// 获取登录用户信息
		mav.addObject("user", shareService.getUserDetail());
		// 获取当前学期
		SchoolTerm currentTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
		mav.addObject("schoolTerms", schoolTerms);
		List<LabRoom> labRooms = shareService.findAllLabRooms();
		mav.addObject("labRooms", labRooms);
		/**
		 * 如果学期获取为零则学期为空则返回显示页面
		 */
		if(schoolTerm.getId() == null) //默认页面显示当前学期
		{
			mav.addObject("schoolTerm", currentTerm);
			schoolTerm = currentTerm;
		}
		// 获取实验室分室排课记录
		mav.addObject("labTimetable", teachingReportService.getLabTimetableAppointments(schoolTerm.getId(), acno, 0));
		// 获取二次排课实验分室室排课记录
		mav.addObject("labSelfTimetable", teachingReportService.getSelfLabTimetableAppointments(schoolTerm.getId(), acno, 0));
		mav.setViewName("reports/teachingReport/termRegister.jsp");
		return mav;
	}
	
	/************************************************************
	 * @学期登记报表导出Excel
	 * @作者：何立友
	 * @日期：2014-09-11
	 ************************************************************/
	@RequestMapping("/report/teachingReport/exportTermRegister")
	public void exportTermRegister(@ModelAttribute SchoolTerm schoolTerm, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("selected_academy") String acno)throws Exception{
		teachingReportService.exportTermRegister(schoolTerm, acno, request, response);
	}
	
	/************************************************************
	 * @周次登记报表
	 * @作者：何立友
	 * @日期：2014-09-11
	 ************************************************************/
	@RequestMapping("/report/teachingReport/weekRegister")
	public ModelAndView weekRegister(@ModelAttribute SchoolTerm schoolTerm,@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		// 获取去重的实验分室室列表
		mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
		// 获取登录用户信息
		mav.addObject("user", shareService.getUserDetail());
		//获取当前学期
		SchoolTerm currentTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		if(schoolTerm.getId() == null) //默认页面显示当前学期,所有周次
		{
			mav.addObject("schoolTerm", currentTerm);
			schoolTerm = currentTerm;
			schoolTerm.setTermCode(0);
		}
		List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
		mav.addObject("schoolTerms", schoolTerms);  //学期下拉框数据
		mav.addObject("weeksMap", teachingReportService.getWeekMap(schoolTerm.getId()));  //周次下拉框数据
		// 获取实验分室室排课记录
		mav.addObject("labTimetable", teachingReportService.getLabTimetableAppointments(schoolTerm.getId(), acno, schoolTerm.getTermCode()));
		mav.addObject("labSelfTimetable", teachingReportService.getSelfLabTimetableAppointments(schoolTerm.getId(), acno, schoolTerm.getTermCode()));
		mav.setViewName("reports/teachingReport/weekRegister.jsp");
		return mav;
	}
	
	
	
	/************************************************************
	 * @ajax获取本学期本学院的课程
	 * @作者：何立友
	 * @日期：2014-09-12
	 ************************************************************/
	@RequestMapping("/report/teachingReport/getTeachersByTerm")
	public @ResponseBody String getTeachersByTerm(@RequestParam int termId, @ModelAttribute("selected_academy") String acno){
		return shareService.htmlEncode(teachingReportService.getTeachersByTerm(termId, acno));
	}
	
	/*************************************************************************************
	 * @获取指定学期、指定教师课程(ajax)
	 * @作者： 何立友
	 * @日期：2014-09-12
	 *************************************************************************************/
	@RequestMapping("/report/teachingReport/getCoursesByTermTeacher")
	public @ResponseBody String getCoursesByTermTeacher(@RequestParam int termId, @RequestParam String username){
		return shareService.htmlEncode(teachingReportService.getCourseDetailsByTermTeacher(termId, username));
	}
	
	
	/************************************************************
	 * @throws Exception 
	 * @导出周次登记报表
	 * @作者：徐龙帅
	 * @日期：2015-01-14
	 ************************************************************/
	@RequestMapping("/report/teachingReport/exportWeekRegister")
	public void export(@ModelAttribute SchoolTerm schoolTerm,HttpServletRequest request, HttpServletResponse response,@ModelAttribute("selected_academy") String acno) throws Exception{
		teachingReportService.exportweekRegister(schoolTerm, acno, request, response);
	}

	/**
	 * Description 月报报表{列表}
	 * @param request
	 * @param timetableAppointment
	 * @param acno
	 * @param currpage
	 * @return
	 * @author 陈乐为 2019年4月24日
	 */
	@RequestMapping("/report/teachingReport/monthRegister")
	public ModelAndView monthRegister(HttpServletRequest request,
			@ModelAttribute TimetableAppointment timetableAppointment,
			@ModelAttribute("selected_academy") String acno,@RequestParam int currpage) {
		ModelAndView mav = new ModelAndView();
        int pageSize = 20;
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		mav.addObject("term", term);
		// 查询条件
		String params = request.getParameter("params");
		mav.addObject("params", params);
		String start_date = request.getParameter("start_date");
		mav.addObject("start_date", start_date);
		String end_date = request.getParameter("end_date");
		mav.addObject("end_date", end_date);
		// 获取所有的学期
		List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
		mav.addObject("schoolTerms", schoolTerms);
		// 封装参数
		QueryParamsVO queryParamsVO = new QueryParamsVO();
		queryParamsVO.setTerm_id(term);
		queryParamsVO.setQuery_params(params);
		queryParamsVO.setStart_date(start_date);
		queryParamsVO.setEnd_date(end_date);
		queryParamsVO.setCurr_page(currpage);
		queryParamsVO.setPage_size(pageSize);
		// 列表数据
		List<Object[]> list = reportService.getMonthReport(queryParamsVO);
		mav.addObject("list", list);
		// 分页参数
		queryParamsVO.setCurr_page(0);
		int totalRecords = reportService.getMonthReport(queryParamsVO).size();
		Map<String,Integer> pageModel =shareService.getPage(currpage, pageSize,totalRecords);
		mav.addObject("pageModel",pageModel);

		mav.setViewName("reports/teachingReport/monthRegister.jsp");
		return mav;
	}

	/**
	 * Description 月报报表{导出excel}
	 * @param request
	 * @param response
	 * @param acno
	 * @throws Exception
	 * @author 陈乐为 2019年4月24日
	 */
	@RequestMapping("/report/teachingReport/exportMonthRegister")
	public void exportMonthRegister(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("selected_academy") String acno)throws Exception{
		teachingReportService.exportMonthRegister(request, response);
	}

	@RequestMapping("/reportTest")
	public ModelAndView reportTest() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("zuulServerUrl", pConfig.auditServerUrl);

		mav.setViewName("reports/teachingReport/getTest.jsp");
		return mav;
	}
	

}
