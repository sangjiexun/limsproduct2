package net.zjcclims.web.project;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.zjcclims.domain.LabCenter;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.OperationItem;
import net.zjcclims.domain.SchoolTerm;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabCenterService;
import net.zjcclims.service.project.ProjectSummaryService;
import net.zjcclims.service.report.ReportService;
import net.zjcclims.service.timetable.OuterApplicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller("PolitProjectSummaryController")
@SessionAttributes("selected_academy")
@RequestMapping("/operation")
public class PolitProjectSummaryController<JsonResult> {

	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register // static // property // editors.
		binder.registerCustomEditor(java.util.Calendar.class,new org.skyway.spring.util.databinding.CustomCalendarEditor());
		binder.registerCustomEditor(byte[].class,new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
		binder.registerCustomEditor(java.math.BigDecimal.class,new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
		binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Integer.class, true));
		binder.registerCustomEditor(java.util.Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
		binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
		binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Long.class, true));
		binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Double.class, true));
	}
	
	@Autowired
	private ShareService shareService;
	@Autowired
	private OuterApplicationService outerApplicationService;
	@Autowired
	private ProjectSummaryService projectSummaryService;
	@Autowired
	private LabCenterService labCenterService;
	@Autowired
	private ReportService reportService;

	/*****************************************************************
	 * @根据条件查询实验项目汇总列表(实验中心&实验室名称&学期&课程名称+代码)
	 * @作者：陈乐为
	 * @日期：2016-01-04（2016.03.30）
	 *****************************************************************/
	@RequestMapping("/project/projectsummary")
	public ModelAndView searchProjectSummary(HttpServletRequest request,
											 @RequestParam int currpage,@ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView();
		int pageSize = 20;

		List<OperationItem> operation = projectSummaryService.findOperationItems(request, currpage, pageSize, acno);
		mav.addObject("ProjectSummaries", operation);//页面列表

		int totalRecords = projectSummaryService.findOperationItems(request, 1, -1, acno).size();
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize,totalRecords);
		mav.addObject("pageModel", pageModel);
		//获取当前页的页码
		mav.addObject("page", currpage);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("schoolTermMap", reportService.getTermsMap());
		mav.addObject("schoolTerms", shareService.findAllSchoolTerm());  //所有学期
		//LabCenter labcenter=labCenterDAO.findLabCenterById(cid);
		//获取当前学期
		SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		mav.addObject("schoolTerm", schoolTerm);
		// 被选学期
		// 定义并初始化所选学期id数组
		String[] termIdArray = {"0"};
		// 获取页面选项数组
		if (request.getParameter("schoolTerm") != null) {
			termIdArray = request.getParameterValues("schoolTerm");
		}
		mav.addObject("termIdArray", termIdArray);
		Map<Integer,String> termSelectedList = projectSummaryService.getTermSelectedMap(termIdArray);
		mav.addObject("termSelectedList", termSelectedList);

		mav.addObject("labCenterMap", reportService.getAllLabCenterMap());
		mav.addObject("labCenters", labCenterService.findAllCenters());
		mav.addObject("labRooms", projectSummaryService.findAllLabRoom(acno));

		//获取所有中心
		Map<String,String> labCenterMap=new HashMap<String, String>();
		for(LabCenter labcenterss : labCenterService.findAllCenters()){
			labCenterMap.put(String.valueOf(labcenterss.getId()),labcenterss.getCenterName());
		}
		mav.addObject("labCenterMap",labCenterMap);

		// 被选中心
		int selectedCenterId = -1;
		if (request.getParameter("labCenter") != null && !request.getParameter("labCenter").equals("")) {
			selectedCenterId = Integer.valueOf(request.getParameter("labCenter"));
		}
		// 查询中心下的项目
		List<OperationItem> operationItems= projectSummaryService.findAllOperationItem(acno);
		// 中心下项目对应的课程映射
		Map<String, String> schoolCourseInfoMap = projectSummaryService.findAllcourseName(operationItems);
		mav.addObject("schoolCourseInfoMap", schoolCourseInfoMap);
		// 被选课程
		if(request.getParameter("courseNumber") != null && !request.getParameter("courseNumber").equals("")) {
			String courseSelected = request.getParameter("courseNumber");
			mav.addObject("courseSelected", courseSelected);
		}

		mav.addObject("courseName", projectSummaryService.findSchoolCourseInfo(request, acno));
		LabCenter selectedCenter = labCenterService.findLabCenterByPrimaryKey(selectedCenterId);
		if(selectedCenter != null && selectedCenter.getId() != null) {
			mav.addObject("selectedCenter",selectedCenter.getId());
		}
		//获取所有实验室
		Map<String,String> labRoomMap=new HashMap<String, String>();
		for(LabRoom labroomss:projectSummaryService.findAllLabRoom(String.valueOf(selectedCenterId))){
			labRoomMap.put(String.valueOf(labroomss.getId()), labroomss.getLabRoomName());
		}
		mav.addObject("labRoomMap",labRoomMap);
		// 被选实验室
		if(request.getParameter("labRoom") != null && !request.getParameter("labRoom").equals("")) {
			String roomId = request.getParameter("labRoom");
			mav.addObject("roomSelected", roomId);
		}

		mav.addObject("page", currpage);
		mav.setViewName("project/projectSummary.jsp");

		return mav;
	}
	/*****************************************************************
	 * @导出
	 * @作者：廖文辉
	 * @日期：2018.10.22
	 *****************************************************************/
	@RequestMapping("/project/exportProjectSummary")
	public void exportProjectSummary(HttpServletRequest request, HttpServletResponse response)throws Exception{
		String acno = null;
		if (request.getParameter("acno")!=null&&!request.getParameter("acno").equals("")) {
			acno = request.getParameter("acno");
		}

		projectSummaryService.exportOperationItem(request, response, acno);
	}
}