package net.zjcclims.web.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zjcclims.domain.LabCenter;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.LabWorker;
import net.zjcclims.domain.OperationItem;
import net.zjcclims.service.basic_data.BasicDataService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabCenterService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.report.LabRoomBasicService;
import net.zjcclims.service.report.LabWorkerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller("LabRoomBasicController")
@SessionAttributes("selected_academy")
@RequestMapping("/report")
public class LabRoomBasicController<JsonResult>{
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
	
	@Autowired private ShareService shareService;
	@Autowired private LabRoomBasicService labRoomBasicService;
	@Autowired private LabCenterService labCenterService;
	@Autowired private BasicDataService basicDataService;
	/*****************************************************************
	 * @实验室基本情况表
	 * @作者：陈乐为
	 * @日期：2016-01-06
	 *****************************************************************/
	@RequestMapping("/listLabRoomBasic")
	public ModelAndView listLabRoomBasic(HttpServletRequest request, @ModelAttribute LabRoom labRoom, 
			@RequestParam int currpage, Integer labCenterid,@ModelAttribute LabCenter labCenter, @ModelAttribute("selected_academy") String acno)throws ParseException{
		ModelAndView mav = new ModelAndView();
		int pageSize = 40;
		int totalRecords =  basicDataService.labBasic(request,currpage, pageSize).size();
		
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize,totalRecords);
		mav.addObject("pageModel", pageModel);
		//获取当前页的页码
		mav.addObject("page", currpage);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("listLabCenter",labCenterService.findAllLabCenterByQuery(request,labCenter, currpage, pageSize));
		mav.addObject("labRoomBasics", basicDataService.labBasic(request,currpage, pageSize));
		//mav.addObject("labRoomBasics", labRoomBasicService.findLabRoomByQuery(labRoom, labCenterid, currpage, pageSize));
		//获取所有中心
		List<LabCenter> centers = new ArrayList<LabCenter>();
		centers = labCenterService.findAllLabCenterByQuery(request,new LabCenter(), 1, -1);
		mav.addObject("centers", centers);
//		if (request.getParameter("labCenterId") != null) {
//			cid = Integer.parseInt(request.getParameter("labCenterId"));
//		}
//		mav.addObject("cid", cid);
		mav.setViewName("reports/labRoomBasic.jsp");
		
		return mav;
	}
	
	/*****************************************************************
	 * @根据实验中心查询
	 * @作者：陈乐为
	 * @日期：2016-01-06
	 *****************************************************************/
	@RequestMapping("/searchLabRoomBasic")
	public ModelAndView searchLabRoomBasic(HttpServletRequest request, @ModelAttribute LabRoom labRoom, 
			@RequestParam int currpage, Integer labCenterId, @ModelAttribute("selected_academy") String acno)throws ParseException{
		ModelAndView mav = new ModelAndView();
		int pageSize = 20;
		//int totalRecords = labRoomBasicService.findAllLabRoomByQuery(labRoom, labCenterId);
		int totalRecords = basicDataService.labBasic(request,currpage, pageSize).size();
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize,totalRecords);
		//获取所有中心
		List<LabCenter> centers = new ArrayList<LabCenter>();
		centers = labCenterService.findAllLabCenterByQuery(request,new LabCenter(), 1, -1);
		mav.addObject("centers", centers);
		if (request.getParameter("labCenterId") != null) {
//			cid = Integer.parseInt(request.getParameter("labCenterId"));
		}
		mav.addObject("pageModel", pageModel);
		//获取当前页的页码
		mav.addObject("page", currpage);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("labRoomBasics", basicDataService.labBasic(request,currpage, pageSize));
		
//		mav.addObject("cid", cid);
		mav.setViewName("reports/labRoomBasic.jsp");
		//mav.setViewName("reports/searchLabRoomBasic.jsp");
		
		return mav;
	}
}