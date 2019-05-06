package net.zjcclims.web.cms;

import net.zjcclims.domain.*;
import net.zjcclims.service.cms.CmsSystemService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.device.LabRoomDeviceService;
import net.zjcclims.service.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/******************************************************************************************
 * 功能：CMS（课程）模块 作者：李小龙 时间：2015-8-10 15:08:50
 *****************************************************************************************/
@Controller("CMSController")
public class CMSController<JsonResult> {
	/**************************************************************************************
	 * @初始化WebDataBinder，这个WebDataBinder用于填充被@InitBinder注释的处理 方法的command和form对象
	 * 
	 *************************************************************************************/
	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) {
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

	@Autowired
	private ShareService shareService;
	@Autowired
	private CmsSystemService cmsSystemService;
	@Autowired
	private LabRoomDeviceService labRoomDeviceService;

	@Autowired
	private ReportService reportService;

	/**************************************************************************************
	 * @功能 修改密码
	 * @作者：黄崔俊
	 * @日期：2015-9-15 14:33:20
	 *************************************************************************************/
	@RequestMapping("/cms/changePassword")
	public ModelAndView changePassword(@RequestParam String password,String type) {
		ModelAndView mav = new ModelAndView();
		// 获取登录用户
		User user = shareService.getUser();
		if (password!=null&&!"".equals(password)) {//传入的密码不为空则修改密码
			user = shareService.changeUserPassword(user,password);
		}
		mav.addObject("user", user);
        //课程列表还是我的课程
		if ("课程列表".equals(type)) {
			if (password==null||"".equals(password)) {
				mav.setViewName("cms/courseSite/courseList.jsp");
			}else {
				mav.setViewName("redirect:/cms/courseList");
			}
		}
		if ("我的课程".equals(type)) {
			if (password==null||"".equals(password)) {
				mav.setViewName("cms/courseSite/courseList.jsp");
			}else {
				mav.setViewName("redirect:/cms/myCourseList");
			}
		}
		mav.addObject("type", type);
		
		return mav;
	}
	
	/**************************************************************************************
	 * @功能 查看个人信息
	 * @作者：黄崔俊
	 * @日期：2015-9-16 10:22:22
	 *************************************************************************************/
	@RequestMapping("/cms/listMyInfo")
	public ModelAndView listMyInfo(@RequestParam String type) {
		ModelAndView mav = new ModelAndView();

		// 获取当前用户
		User user = shareService.getUser();
		Set<Authority> as = user.getAuthorities();
		String str = "";
		if(as.size()==0){
			str = "暂无身份";
		}
		if(as.size()>0){
			for(Authority a : as){
				str = a.getCname()+" ";
			}
		}
		str = str.substring(0, str.length()-1);
		mav.addObject("str", str);
		mav.addObject("user", user);
		
		//课程列表还是我的课程
		mav.addObject("type", type);
		mav.setViewName("cms/courseSite/courseList.jsp");
		return mav;
	}
	
	/**************************************************************************************
	 * @功能：保存个人信息
	 * @作者：黄崔俊
	 * @日期：2015-9-16 10:22:22
	 *************************************************************************************/
	@RequestMapping("/cms/saveMyInfo")
	public ModelAndView saveMyInfo(@ModelAttribute User user,@RequestParam String type) {
		ModelAndView mav = new ModelAndView();
		// 获取当前用户
		User user1 = shareService.getUser();

		// 直接设置用户的email telephone信息
		user1.setEmail(user.getEmail());
		user1.setTelephone(user.getTelephone());

		// 保存信息
		shareService.saveUser(user1);
		mav.addObject("type", type);
		mav.setViewName("redirect:/cms/listMyInfo");
		return mav;
	}

	/**************************************************************************************
	 * @功能 门户首页
	 * @作者：黄崔俊
	 * @日期：2016-4-18 09:29:59
	 *************************************************************************************/
	@RequestMapping("/cms/portal")
	public ModelAndView portal() {
		ModelAndView mav = new ModelAndView();

		mav.setViewName("portal/index.jsp");
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

	/****************************************************************************
	 * 功能：手机端设备信息页面
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/cmsshow/showDevice")
	public ModelAndView showDevice(@RequestParam Integer id) {
		ModelAndView mav=new ModelAndView();
		//id对应的实验室设备
		LabRoomDevice device=labRoomDeviceService.findLabRoomDeviceByPrimaryKey(id);
		mav.addObject("device", device);	
		mav.setViewName("cmsshow/showDevice.jsp");
		return mav;
	}
    
	/****************************************************************************
	 * 功能：手机端设备详情页面
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/cmsshow/showDeviceInfo")
	public ModelAndView showDeviceInfo(@RequestParam Integer id) {
		ModelAndView mav=new ModelAndView();
		//id对应的实验室设备
		LabRoomDevice device=labRoomDeviceService.findLabRoomDeviceByPrimaryKey(id);
		mav.addObject("device", device);	
		System.out.println(device);
		mav.setViewName("cmsshow/showDeviceInfo.jsp");
		return mav;
	}
	
	/*************************************************************************************
	 * @內容：登录类型
	 * @author 王建羽
	 * @日期：2015-3-4 16:8
	 *************************************************************************************/
	@RequestMapping("/cms/setSession")
	public @ResponseBody
	String setSession(HttpSession session, @RequestParam Integer type, String urlString) {
		session.setAttribute("turnUrl", urlString);
		if(type == 1 ){
			session.setAttribute("LOGINTYPE", "cmsIndex");  
		}
		else{
			session.setAttribute("LOGINTYPE", "index");
		}
		
		return "success";
	} 

	/**
	 * 根据year_code获取学期
	 * @param yearCode
	 * @author hely
	 * 2014.09.10
	 */
	@RequestMapping("/report/getTermsByYearCode")
	public @ResponseBody Map<String, String> getTermsByYearCode(@RequestParam String yearCode){
		Map<String, String> termsMap = new LinkedHashMap<String, String>();
		termsMap.put("terms", reportService.getTermsByYearCode(yearCode));
		
		return termsMap;
	}
	
}