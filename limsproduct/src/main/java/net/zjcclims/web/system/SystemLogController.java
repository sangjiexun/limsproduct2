package net.zjcclims.web.system;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.domain.SchoolTerm;
import net.zjcclims.domain.SystemLog;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.system.SystemLogService;

import net.zjcclims.service.virtual.VirtualService;
import net.zjcclims.vo.QueryParamsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * Spring MVC controller that handles CRUD requests for SystemLog entities
 * 
 */

@Controller("SystemLogController")
@SessionAttributes("selected_academy")
public class SystemLogController {

	@Autowired private SystemLogService systemLogService;
	@Autowired private ShareService shareService;
	//@Autowired SchoolTermService schoolTermService;
	@Autowired private VirtualService virtualService;
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
	
	
	/***********************************************
	 * 功能：列出系统日志
	 * 作者：贺子龙
	 * 日期：2015-12-21
	 **********************************************/
	@RequestMapping("/log/listOperationLog")
	public ModelAndView listOperationLog(@RequestParam int currpage,@ModelAttribute SystemLog systemLog,
			@ModelAttribute("selected_academy") String acno,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		int pageSize = CommonConstantInterface.INT_PAGESIZE;
		int totalRecords=systemLogService.findSystemLogs(systemLog,acno,1,-1,request).size();
		List<SystemLog>  operationLogs=systemLogService.findSystemLogs(systemLog,acno,currpage,pageSize,request);
		Map<String, String> userDetailMap=systemLogService.getUserMap(acno);
		mav.addObject("userDetailMap", userDetailMap);
		mav.addObject("operationLogs", operationLogs);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("pageSize", pageSize);
		mav.addObject("currpage", currpage);
		mav.setViewName("reports/systemLog/listOperationLog.jsp");
		return mav;
	}
	
	/***********************************************
	 * 功能：删除系统日志
	 * 作者：贺子龙
	 * 日期：2015-12-22
	 **********************************************/
	@RequestMapping("/log/deleteOperationLog")
	public ModelAndView deleteOperationLog(@RequestParam String logIds){
		ModelAndView mav = new ModelAndView();
		systemLogService.deleteSystemLog(logIds);
		mav.setViewName("redirect:/log/listOperationLog?currpage=1");
		return mav;
	}
	/***********************************************
	 * 功能：计划内实训室使用统计
	 * 作者：周志辉
	 * 日期：2017-09-19
	 **********************************************/
	@RequestMapping("/log/listLabRoomUsePlan")
	public ModelAndView listLabRoomUsePlan(@RequestParam int currpage,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.addObject("roomName",request.getParameter("roomName"));
		mav.addObject("yearCode",request.getParameter("yearCode"));
		mav.addObject("termCode",request.getParameter("termId"));
		
		//学期
		List<SchoolTerm> terms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", terms);
		
		// 页面设置
		int pageSize = 20;
		// 课程列表
		int totalRecords = systemLogService.allLabRoomUsePlanCount(currpage, pageSize, request);
		List<Object[]> details = systemLogService.findAllLabRoomUsePlan(currpage,
				pageSize, request);
		Map<String, Integer> pageModel = shareService.getPage(
				currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("details",details);
		mav.setViewName("reports/systemLog/listLabRoomUsePlan.jsp");
		return mav;
	}
	/***********************************************
	 * 功能：实训室课时课次使用统计表
	 * 作者：周志辉
	 * 日期：2017-09-25
	 **********************************************/
	@RequestMapping("/log/listLabRoomCourseCount")
	public ModelAndView listLabRoomCourseCount(@RequestParam int currpage,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.addObject("roomName",request.getParameter("roomName"));
		mav.addObject("roomAdmin",request.getParameter("roomAdmin"));
		mav.addObject("yearCode",request.getParameter("yearCode"));
		mav.addObject("termCode",request.getParameter("termCode"));
		mav.addObject("termId",request.getParameter("termId"));
		mav.addObject("courseCount",request.getParameter("courseCount"));
		mav.addObject("labId",request.getParameter("labId"));
		mav.addObject("courseHour",request.getParameter("courseHour"));
		
		// 页面设置
		int pageSize = 20;
		// 课程列表
		int totalRecords = systemLogService.allLabRoomCourseCount(currpage, pageSize, request);
		List<Object[]> details = systemLogService.findLabRoomCourseCount(currpage,
				pageSize, request);
		Map<String, Integer> pageModel = shareService.getPage(
				currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("details",details);
		mav.setViewName("reports/systemLog/listLabRoomCourseCount.jsp");
		return mav;
	}
	/***********************************************
	 * 功能：年度使用绩效评价表
	 * 作者：周志辉
	 * 日期：2017-09-25
	 **********************************************/
	@RequestMapping("/log/listUsePerformanceEvaluation")
	public ModelAndView listUsePerformanceEvaluation(@RequestParam int currpage,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.addObject("roomName",request.getParameter("roomName"));
		mav.addObject("labType",request.getParameter("labType"));
		mav.addObject("constructionAcademy",request.getParameter("constructionAcademy"));
		mav.addObject("constructionYear",request.getParameter("constructionYear"));
		mav.addObject("termId",request.getParameter("termId"));
		mav.addObject("courseCount",request.getParameter("courseCount"));
		mav.addObject("labId",request.getParameter("labId"));
		mav.addObject("courseHour",request.getParameter("courseHour"));
		
		// 页面设置
		int pageSize = 20;
		// 课程列表
		int totalRecords = systemLogService.allUsePerformanceEvaluation(currpage, pageSize, request);
		List<Object[]> details = systemLogService.findUsePerformanceEvaluation(currpage,
				pageSize, request);
		Map<String, Integer> pageModel = shareService.getPage(
				currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("details",details);
		mav.setViewName("reports/systemLog/listUsePerformanceEvaluation.jsp");
		return mav;
	}

	/**
	 * Description 计划外实验室使用统计-列表
	 * @param currpage
	 * @param request
	 * @return
	 * @author 陈乐为 2019年4月17日
	 */
	@RequestMapping("/log/listLabRoomUseUnplan")
	public ModelAndView listLabRoomUseUnplan(@RequestParam int currpage, int type,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.addObject("roomName",request.getParameter("roomName"));
		String term_id = request.getParameter("termId");
		if(term_id != null && !term_id.equals("")) {
			mav.addObject("term",request.getParameter("termId"));
		}else {
			term_id = "-1";
		}

		// 页面设置
		int pageSize = 20;
		// 参数封装
		QueryParamsVO paramsVO = new QueryParamsVO();
		paramsVO.setTerm_id(Integer.valueOf(term_id));
		paramsVO.setQuery_params(request.getParameter("roomName"));
		paramsVO.setType(type);
		paramsVO.setCurr_page(currpage);
		paramsVO.setPage_size(pageSize);
		// 课程列表
		int totalRecords = systemLogService.allLabRoomUseUnplanCount(paramsVO);
		//学期
		List<SchoolTerm> terms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", terms);
		List<Object[]> details = systemLogService.findAllLabRoomUseUnplan(paramsVO);
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("details", details);
		mav.addObject("type", type);

		mav.setViewName("reports/systemLog/listLabRoomUseUnplan.jsp");
		return mav;
	}

	@RequestMapping("reportPlanLabRateInfo")
	public ModelAndView reportPlanLabRateInfo(QueryParamsVO queryParamsVO) {
		ModelAndView mav = new ModelAndView();

		return mav;
	}

	/*************************************************************************************
	 * Description:虚拟镜像使用报表
	 *
	 * @author: 杨新蔚
	 * @date: 2018/12/18
	 *************************************************************************************/
	@RequestMapping(value="/log/listVirtualUse",produces = "application/json;charset=utf-8")
	public ModelAndView listVirtualUse(@RequestParam int currpage,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		List<Map<String,String>> list=virtualService.virtualHistory();
		mav.addObject("historyList",list);
		// 页面设置
		int pageSize = 20;
		// 课程列表
		int totalRecords =0;
		if(list!=null&list.size()>0){
			totalRecords=list.size();
		}
		Map<String, Integer> pageModel = shareService.getPage(
				currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.setViewName("reports/systemLog/listVirtualUse.jsp");
		return mav;
	}

}