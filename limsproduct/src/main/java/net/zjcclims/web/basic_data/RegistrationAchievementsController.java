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
import net.zjcclims.dao.InnovationAchievementRegistrationDAO;
import net.zjcclims.dao.LabRoomDAO;
import net.zjcclims.domain.InnovationAchievementRegistration;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.SchoolDeviceChangeReport;
import net.zjcclims.domain.SchoolYear;
import net.zjcclims.domain.VisitingRegistration;
import net.zjcclims.service.basic_data.BasicDataService;
import net.zjcclims.service.basic_data.InnovationAchievementRegistrationService;
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

@Controller("RegistrationAchievementsController")
public class RegistrationAchievementsController {
	@Autowired private ShareService shareService;
	@Autowired private BasicDataService basicDataService;
	@Autowired private ShareDataService shareDataService;
	@Autowired private LabRoomService labRoomService;
	@Autowired private LabRoomDAO labRoomDAO;
	@Autowired private InnovationAchievementRegistrationService innovationAchievementRegistrationService;
	@Autowired private InnovationAchievementRegistrationDAO innovationAchievementRegistrationDAO;
	
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
	 * @Description:创新成果登记-列表
	 * @author 张愉
	 * @date:2017-6-30
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/basic_data/registrationAchievementsList")
	  public ModelAndView registrationAchievementsList(HttpServletRequest request,@RequestParam Integer currpage){
		ModelAndView mav = new  ModelAndView();
		mav.addObject("innovationname",request.getParameter("innovationname"));
		//获取创新成果登记列表
		Set<InnovationAchievementRegistration> InnovationAchievementRegistrations=innovationAchievementRegistrationService.loadInnovationAchievementRegistrations();
		mav.addObject("InnovationAchievementRegistrations", InnovationAchievementRegistrations);
		mav.addObject("InnovationAchievementRegistrations", InnovationAchievementRegistrations);
		mav.setViewName("basic_data/RegistrationAchievementsList.jsp");
		return mav;	
	}
	
	/**
	 * @Description:创新成果登记-新建
	 * @author 张愉
	 * @date:2017-7-7
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/basic_data/newRegistrationAchievements")
	  public ModelAndView newRegistrationAchievements(HttpServletRequest request,@RequestParam Integer currpage){
		ModelAndView mav = new  ModelAndView();
		//获取创新成果登记列表
		Set<InnovationAchievementRegistration> InnovationAchievementRegistrations=innovationAchievementRegistrationService.loadInnovationAchievementRegistrations();
		mav.addObject("InnovationAchievementRegistrations", InnovationAchievementRegistrations);
		//获取实验室
		mav.addObject("labRoom", labRoomDAO.findAllLabRooms());
		InnovationAchievementRegistration innovationAchievementRegistration=new InnovationAchievementRegistration();
		mav.addObject("innovationAchievementRegistration",innovationAchievementRegistration);
		mav.addObject("labRoom", labRoomService.findallLabRoom());
		mav.setViewName("basic_data/newRegistrationAchievements.jsp");
		return mav;	
	}
	/**
	 * @Description:创新成果登记-保存
	 * @author 张愉
	 * @date:2017-7-7
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/basic_data/saveRegistrationAchievements")
	  public String saveRegistrationAchievements(@ModelAttribute InnovationAchievementRegistration innovationAchievementRegistration, HttpServletRequest request){
	
		innovationAchievementRegistrationService.saveInnovationAchievementRegistration(innovationAchievementRegistration);
		return "redirect:/basic_data/registrationAchievementsList?currpage=1";
	}
	/**
	 * @Description:创新成果登记-删除
	 * @author 张愉
	 * @param id为所删除对应记录的id
	 * @date:2017-7-7
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/basic_data/deleteRegistrationAchievements")
	  public String deleteRegistrationAchievements(@RequestParam int id){
		//根据id删除记录
		InnovationAchievementRegistration innovationAchievementRegistration=innovationAchievementRegistrationService.findInnovationAchievementRegistrationByPrimaryKey(id);
		innovationAchievementRegistrationService.deleteInnovationAchievementRegistration(innovationAchievementRegistration);
		return "redirect:/basic_data/registrationAchievementsList?currpage=1";
	}
	/**
	 * @Description:创新成果登记-查询
	 * @author 张愉
	 * @param 
	 * @date:2017-7-10
	 */
	@RequestMapping("/basic_data/searchRegistrationAchievements")
	public ModelAndView searchRegistrationAchievements(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.addObject("innovationname",request.getParameter("innovationname"));
		String innovationname=request.getParameter("innovationname");
		       //获取创新成果登记列表
				Set<InnovationAchievementRegistration> InnovationAchievementRegistrations=innovationAchievementRegistrationDAO.findInnovationAchievementRegistrationByInnovationName(innovationname);
				mav.addObject("InnovationAchievementRegistrations", InnovationAchievementRegistrations);
				mav.setViewName("basic_data/RegistrationAchievementsList.jsp");
		        return mav;
	}
	/**
	 * @Description:参观登记-编辑
	 * @author 张愉
	 * @param id为所编辑对应记录的id
	 * @date:2017-7-10
	 */
	@RequestMapping("/basic_data/editRegistrationAchievement")
	public ModelAndView editRegistrationAchievement(@RequestParam int id){
		ModelAndView mav = new ModelAndView();
		//获取创新成果登记列表
		InnovationAchievementRegistration innovationAchievementRegistration=innovationAchievementRegistrationService.findInnovationAchievementRegistrationByPrimaryKey(id);
		mav.addObject("innovationAchievementRegistration", innovationAchievementRegistration);
		//获取实验室
		mav.addObject("labRoom", labRoomService.findallLabRoom());
		mav.setViewName("basic_data/newRegistrationAchievements.jsp");
		return mav;
	}
	
	
}