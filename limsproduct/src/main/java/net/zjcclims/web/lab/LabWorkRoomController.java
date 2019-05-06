package net.zjcclims.web.lab;

import javax.servlet.http.HttpServletRequest;

import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.domain.LabWorkRoom;
import net.zjcclims.domain.User;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabWorkRoomService;
import net.zjcclims.service.system.SystemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller("LabWorkRoomController")
@SessionAttributes("selected_labWorkRoom")
@RequestMapping("/labWorkRoom")
public class LabWorkRoomController<JsonResult> {

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
	private LabWorkRoomService labWorkRoomService;
	@Autowired
	private SystemService systemService;
	
	/***************************** 
	*Description 实训室基础信息管理-工作室列表
	*
	*@author:余南新
	*@param:
	*@date:2017.4.25
	*****************************/
	@RequestMapping("/listLabWorkRoom")
	public ModelAndView listLabWorkRoom(@RequestParam int currpage, @ModelAttribute LabWorkRoom labWorkRoom){
		ModelAndView mav = new ModelAndView();
		int pageSize = CommonConstantInterface.INT_PAGESIZE;  //分页记录数
		int totalRecords = labWorkRoomService.findAllLabWorkRoomByQuery(labWorkRoom, 1, -1).size();

		mav.addObject("labWorkRoom", labWorkRoom);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("listLabWorkRoom",labWorkRoomService.findAllLabWorkRoomByQuery(labWorkRoom, currpage, pageSize));
		mav.setViewName("lab/lab_workroom/listLabWorkRoom.jsp");
		return mav;
	}
	
	/***************************** 
	*Description 实训室基础信息管理-新建工作室
	*
	*@author:余南新
	*@param:
	*@date:2017.4.25
	*****************************/
	@RequestMapping("/newLabWorkRoom")
	public ModelAndView newLabWorkRoom(){
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("labWorkRoom", new LabWorkRoom());
		mav.addObject("listSchoolAdademy", systemService.getAllSchoolAcademy(1, -1));
		mav.setViewName("lab/lab_workroom/editLabWorkRoom.jsp");
		return mav;
	}
	
	/***************************** 
	*Description 实训室基础信息管理-编辑工作室
	*
	*@author:余南新
	*@param:
	*@date:2017.4.25
	*****************************/
	@RequestMapping("/editLabWorkRoom")
	public ModelAndView editLabWorkRoom(@RequestParam int labWorkRoomId){
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("labWorkRoom", labWorkRoomService.findLabWorkRoomByPrimaryKey(labWorkRoomId));
		mav.addObject("listSchoolAdademy", systemService.getAllSchoolAcademy(1, -1));
		mav.addObject("flag", 1);
		mav.setViewName("lab/lab_workroom/editLabWorkRoom.jsp");
		return mav;
	}
	
	/***************************** 
	*Description 实训室基础信息管理-保存工作室
	*
	*@author:余南新
	*@param:
	*@date:2017.4.25
	*****************************/
	@RequestMapping("saveLabWorkRoom")
	public String saveLabWorkRoom(@ModelAttribute LabWorkRoom labWorkRoom){
		labWorkRoomService.saveLabWorkRoom(labWorkRoom);
		
		return "redirect:/labWorkRoom/listLabWorkRoom?currpage=1";
	}
	
	/***************************** 
	*Description 实训室基础信息管理-删除工作室
	*
	*@author:余南新
	*@param:
	*@date:2017.4.25
	*****************************/
	@RequestMapping("/deleteLabWorkRoom")
	public String deleteLabWorkRoom(@RequestParam int labWorkRoomId){
		labWorkRoomService.deleteLabWorkRoom(labWorkRoomId);
		
		return "redirect:/labWorkRoom/listLabWorkRoom?currpage=1";
	}
}
