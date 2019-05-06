package net.zjcclims.web.basic_data;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Map;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zjcclims.constant.CommonConstantInterface;
/*import net.zjcclims.domain.AssetManage;*/
import net.zjcclims.dao.LabRoomDAO;
import net.zjcclims.dao.VisitingRegistrationDAO;
import net.zjcclims.domain.InnovationAchievementRegistration;
import net.zjcclims.domain.LabCenter;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.SchoolDeviceChangeReport;
import net.zjcclims.domain.SchoolYear;
import net.zjcclims.domain.VisitingRegistration;
import net.zjcclims.service.basic_data.BasicDataService;
import net.zjcclims.service.basic_data.InnovationAchievementRegistrationService;
import net.zjcclims.service.basic_data.VisitingRegistrationService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.system.ShareDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller("VisitingRegistrationController")
public class VisitingRegistrationController {
	@Autowired private ShareService shareService;
	@Autowired private BasicDataService basicDataService;
	@Autowired private ShareDataService shareDataService;
	@Autowired private LabRoomService labRoomService;
	@Autowired private LabRoomDAO labRoomDAO;
	@Autowired private VisitingRegistrationService visitingRegistrationService;
	@Autowired private VisitingRegistrationDAO visitingRegistrationDAO;
	
	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register static property editors.
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
	
	/**
	 * @Description:参观登记-列表
	 * @author 张愉
	 * @param flag 1为校外参观2为校内参观
	 * @date:2017-6-30
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/basic_data/VisitingRegistrationList")
	  public ModelAndView RegistrationAchievementsList(HttpServletRequest request,@RequestParam Integer currpage,@RequestParam Integer flag){
		ModelAndView mav = new  ModelAndView();
		//获取创新成果登记列表
		Set<VisitingRegistration> visitingRegistrations=visitingRegistrationDAO.findVisitingRegistrationByFlag(flag);
		mav.addObject("visitingRegistrations", visitingRegistrations);
		mav.addObject("flag", flag);
		mav.setViewName("basic_data/visitingRegistrationList.jsp");
		return mav;	
	}
	
	/**
	 * @Description:参观登记-新建
	 * @author 张愉
	 * @date:2017-6-30
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/basic_data/newVisitingRegistration")
	  public ModelAndView newVisitingRegistration(HttpServletRequest request,@RequestParam Integer currpage,@RequestParam Integer flag){
		ModelAndView mav = new  ModelAndView();
		//获取实验室
		/*mav.addObject("labRoom", labRoomDAO.findAllLabRooms());*/
		VisitingRegistration visitingRegistration=new VisitingRegistration();
		mav.addObject("visitingRegistration",visitingRegistration);
		mav.addObject("labRoom", labRoomService.findallLabRoom());
		mav.addObject("flag", flag);
		mav.setViewName("basic_data/newVisitingRegistration.jsp");
		return mav;	
	}
	/**
	 * @Description:参观登记-保存
	 * @author 张愉
	 * @date:2017-6-30
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/basic_data/saveVisitingRegistration")
	  public String saveRegistrationAchievements(@ModelAttribute VisitingRegistration visitingRegistration, HttpServletRequest request,@RequestParam Integer flag){
		visitingRegistration.setFlag(flag);
		visitingRegistrationService.saveVisitingRegistration(visitingRegistration);
		ModelAndView mav = new  ModelAndView();
        return "redirect:/basic_data/VisitingRegistrationList?currpage=1&flag="+flag;
	}
	/**
	 * @Description:参观登记-删除
	 * @author 张愉
	 * @param id为所删除对应记录的id
	 * @date:2017-6-30
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/basic_data/deleteVisitingRegistration")
	  public String deleteVisitingRegistration(@RequestParam int id){
		ModelAndView mav = new  ModelAndView();
		//根据id删除记录
		VisitingRegistration visitingRegistration=visitingRegistrationService.findVisitingRegistrationByPrimaryKey(id);
		visitingRegistrationService.deleteVisitingRegistration(visitingRegistration);
		return "redirect:/basic_data/VisitingRegistrationList?currpage=1&flag=1";
	}
	/**
	 * @Description:参观登记-编辑
	 * @author 张愉
	 * @param id为所编辑对应记录的id
	 * @date:2017-7-10
	 */
	@RequestMapping("/basic_data/editVisitingRegistration")
	public ModelAndView editVisitingRegistration(@RequestParam Integer flag,@RequestParam int id){
		ModelAndView mav = new ModelAndView();
		VisitingRegistration visitingRegistration=visitingRegistrationService.findVisitingRegistrationByPrimaryKey(id);
		mav.addObject("visitingRegistration", visitingRegistration);
		//获取实验室
		mav.addObject("labRoom", labRoomService.findallLabRoom());
		mav.addObject("flag", flag);
		mav.setViewName("basic_data/newVisitingRegistration.jsp");
		return mav;
	}
	/**
	 * @Description:参观登记-查询
	 * @author 张愉
	 * @param 
	 * @date:2017-7-10
	 */
	@RequestMapping("/basic_data/searchVisitingRegistration")
	public ModelAndView searchVisitingRegistration(HttpServletRequest request,@RequestParam Integer flag){
		ModelAndView mav = new ModelAndView();
		if(flag==1){
		String registrant=request.getParameter("registrant");
		Set<VisitingRegistration> visitingRegistration=visitingRegistrationDAO.findVisitingRegistrationByRegistrant(registrant);
		mav.addObject("visitingRegistrations", visitingRegistration);
		}else{
			String projectname=request.getParameter("projectname");
			Set<VisitingRegistration> visitingRegistration=visitingRegistrationDAO.findVisitingRegistrationByProjectName(projectname);
			mav.addObject("visitingRegistrations", visitingRegistration);
		}
		//获取实验室
		mav.addObject("labRoom", labRoomService.findallLabRoom());
		mav.addObject("flag", flag);
		mav.setViewName("basic_data/visitingRegistrationList.jsp");
		return mav;
	}
	
	
}