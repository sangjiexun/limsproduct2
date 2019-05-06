package net.zjcclims.web.lab;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import flex.messaging.io.ArrayCollection;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.constant.LabCenterVO;
import net.zjcclims.dao.AuthorityDAO;
import net.zjcclims.dao.LabRoomDAO;
import net.zjcclims.dao.UserDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabCenterService;
import net.zjcclims.service.system.SystemAuthorityService;
import net.zjcclims.service.system.SystemService;

import org.apache.derby.client.am.Decimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller("LabCenterController")
@SessionAttributes("selected_academy")
@RequestMapping("/labCenter")
public class LabCenterController<JsonResult> {

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
	private LabCenterService labCenterService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private AuthorityDAO authorityDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private SystemAuthorityService systemAuthorityService;
	@Autowired private LabRoomDAO labRoomDAO;
	
	/**
	 * 实验中心列表
	 * @author hly
	 * 2015.07.27
	 * 周志辉 2017-08-24 修改
	 */
	@RequestMapping("/listLabCenter")
	public ModelAndView listLabCenter(@RequestParam int currpage, @ModelAttribute LabCenter labCenter,
									  @ModelAttribute("selected_academy") String acno, HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		int pageSize =CommonConstantInterface.INT_PAGESIZE;  //分页记录数
		int labCenterDeviceCount=0;//一个实训中心设备数量
		// 学院
		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
		labCenter.setSchoolAcademy(academy);
		int totalRecords = labCenterService.findAllLabCenterByQueryCount(labCenter,request);//实训中心数量
		List<LabCenter> allLabCenter = labCenterService.findAllLabCenterByQuery(request, labCenter, currpage, pageSize);//所有实训中心

		List<LabCenterVO> labCenterVOS = new ArrayList<>();

		for(LabCenter labcenter : allLabCenter)//循环遍历所有实训中心
		{
			LabCenterVO labCenterVO = new LabCenterVO();
			labCenterVO.setId(labcenter.getId());
			labCenterVO.setCenterNo(labcenter.getCenterNumber());
			labCenterVO.setCenterName(labcenter.getCenterName());
			if(labcenter.getUserByCenterManager()!=null)
			labCenterVO.setCenterAdmin(labcenter.getUserByCenterManager().getCname()+"["+labcenter.getUserByCenterManager().getUsername()+"]");
			if(labcenter.getSystemCampus()!=null)
			labCenterVO.setCampus(labcenter.getSystemCampus().getCampusName());
			if(labcenter.getSchoolAcademy()!=null)
			labCenterVO.setAcademy(labcenter.getSchoolAcademy().getAcademyName());
			//每个实训中心面积统计
			labCenterVO.setArea(labCenterService.countAllLabRoomAreaByQuery(labcenter));
			//一个实训中心管理员数量列表
			labCenterVO.setAdminNum(labCenterService.findAllLabRoomAdminByLabCenterCount(labcenter));
			//一个实训中心实验室的数量
			labCenterVO.setLabNum(labCenterService.findAllLabRoomByQueryCount(labcenter));

			BigDecimal allLabCenterDevicePrices=new BigDecimal("0.0");
//			labCenterDevice = labCenterService.findAllLabRoomDeviceByQuery(labcenter, 1, -1);//一个实训中心设备列表
			labCenterDeviceCount=labCenterService.findAllLabRoomDeviceByQueryCount(labcenter);//一个实训中心设备数量列表
			// 设备总数
			labCenterVO.setDeviceNum(labCenterDeviceCount);

			// 设备总价
			List prices = labCenterService.sumAllLabRoomDeviceByQuery(labcenter, 1, -1);
			if(prices != null && prices.size() != 0){
				allLabCenterDevicePrices = (BigDecimal) prices.get(0);
			}

			labCenterVO.setDevicePrice(allLabCenterDevicePrices);
			labCenterVOS.add(labCenterVO);
		}
		mav.addObject("labCenterVOS", labCenterVOS);
		mav.addObject("labCenter", labCenter);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("listLabCenter",allLabCenter);
		mav.addObject("page",currpage);
		mav.setViewName("lab/lab_center/listLabCenter.jsp");
		return mav;
	}
	
	/**
	 * 新建实验中心
	 * @author hly
	 * 2015.07.27
	 */
	@RequestMapping("/newLabCenter")
	public ModelAndView newLabCenter(int page){
		ModelAndView mav = new ModelAndView();
		User user = new User();
		user.setUserRole("1");  //在老师中查找
		mav.addObject("labCenter", new LabCenter());
		mav.addObject("listSchoolAdademy", systemService.getAllSchoolAcademy(1, -1));
		mav.addObject("listSystemCampus", systemService.getAllSystemCampus(1, -1));
		mav.addObject("listUser", systemService.getAllUser(1, -1, user));
		mav.addObject("listSystemBuild", systemService.getAllSystemBuild(1, -1));
		mav.addObject("subject12s", systemService.getAllSystemSubject12(1, -1)); // 学科数据(12版)
		mav.addObject("page",page);
		mav.setViewName("lab/lab_center/editLabCenter.jsp");
		return mav;
	}
	
	/**
	 * 编辑实验中心
	 * @author hly
	 * 2015.07.27
	 */
	@RequestMapping("/editLabCenter")
	public ModelAndView editLabCenter(@RequestParam int labCenterId,int page){
		ModelAndView mav = new ModelAndView();
		User user = new User();
		user.setUserRole("1");  //在老师中查找
		mav.addObject("page",page);
		mav.addObject("labCenter", labCenterService.findLabCenterByPrimaryKey(labCenterId));
		mav.addObject("listSchoolAdademy", systemService.getAllSchoolAcademy(1, -1));
		mav.addObject("listSystemCampus", systemService.getAllSystemCampus(1, -1));
		mav.addObject("listUser", systemService.getAllUser(1, -1, user));
		mav.addObject("listSystemBuild", systemService.getAllSystemBuild(1, -1));
		mav.addObject("subject12s", systemService.getAllSystemSubject12(1, -1)); // 学科数据(12版)
		mav.setViewName("lab/lab_center/editLabCenter.jsp");
		return mav;
	}
	
	/**
	 * 保存实验中心数据
	 * @author hly
	 * 2015.07.27
	 */
	@RequestMapping("saveLabCenter")
	public String saveLabCenter(@ModelAttribute LabCenter labCenter,int page){
		labCenterService.saveLabCenter(labCenter);

		// 增加实验中心主任权限
		User u = userDAO.findUserByUsername(labCenter.getUserByCenterManager().getUsername());
		Set<Authority> ahths = u.getAuthorities();
		Authority a = authorityDAO.findAuthorityByAuthorityName("EXCENTERDIRECTOR").iterator().next();
		if(a != null) {
			ahths.add(a);
			u.setAuthorities(ahths);
			userDAO.store(u);
		}

		return "redirect:/labCenter/listLabCenter?currpage="+page;
	}
	
	/**
	 * 删除实验中心
	 * @author hly
	 * 2015.07.27
	 */
	@RequestMapping("/deleteLabCenter")
	public String deleteLabCenter(@RequestParam int labCenterId,int page){
		LabCenter labCenter=labCenterService.findLabCenterByPrimaryKey(labCenterId);
		if(labCenterService.findAllLabRoomByQuery(labCenter, 0,-1).isEmpty()){
			labCenterService.deleteLabCenter(labCenterId);
		}
		// 删除权限
		User u = userDAO.findUserByUsername(labCenter.getUserByCenterManager().getUsername());
		List labCenters = labCenterService.findAllLabRoomByAdmin(u.getUsername());
		if (labCenters == null || labCenters.size() == 0) {
			Set<Authority> ahths = u.getAuthorities();
			Authority a = authorityDAO.findAuthorityByAuthorityName("EXCENTERDIRECTOR").iterator().next();
			if (a != null) {
				ahths.remove(a);
				u.setAuthorities(ahths);
				userDAO.store(u);
			}
		}
		//labCenterService.deleteLabCenter(labCenterId);
		return "redirect:/labCenter/listLabCenter?currpage="+page;
	}
	/**
	 * 显示实训中心所有管理员数量
	 * @author 周志辉
	 * 2017.08.25
	 */
	@RequestMapping("/showLabCenterAdminDetail")
	public ModelAndView showLabCenterAdminDetail(@RequestParam int labCenterId,@RequestParam int currpage){
		ModelAndView mav = new ModelAndView();
		int pageSize = 20;
		LabCenter labcenter=labCenterService.findLabCenterByPrimaryKey(labCenterId);
		// 总记录数
		int totalRecords = labCenterService.findAllLabRoomAdminByLabCenterCount(labcenter);
		// 分页
		Map<String, Integer> pageModel = shareService.getPage(
				currpage, pageSize, totalRecords);
		mav.addObject("labCenterId",labCenterId);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", currpage);
		
		List<LabRoomAdmin> labCenterAdmin=labCenterService.findAllLabRoomAdminByLabCenter(labcenter);
		mav.addObject("labCenterAdmin",labCenterAdmin);
		mav.setViewName("lab/lab_center/showLabCenterAdminDetail.jsp");
		return mav;
	}
	/**
	 * 显示实训中心所有管理员数量
	 * @author 周志辉
	 * 2017.08.25
	 */
	@RequestMapping("/showLabCenterRoomDetail")
	public ModelAndView showLabCenterRoomDetail(@RequestParam int labCenterId,@RequestParam int currpage){
		ModelAndView mav = new ModelAndView();
		int pageSize = 20;
		LabCenter labcenter=labCenterService.findLabCenterByPrimaryKey(labCenterId);
		// 总记录数
		int totalRecords = labCenterService.findAllLabRoomByQueryCount(labcenter);
		// 分页
		Map<String, Integer> pageModel = shareService.getPage(
				currpage, pageSize, totalRecords);
		mav.addObject("labCenterId",labCenterId);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", currpage);
		
		List<LabRoom> labCenterRoom=labCenterService.findAllLabRoomByQuery(labcenter, 1, -1);;
		mav.addObject("labCenterRoom",labCenterRoom);
		mav.setViewName("lab/lab_center/showLabCenterRoomDetail.jsp");
		return mav;
	}
	/**
	 * 显示实训中心所有管理员数量
	 * @author 周志辉
	 * 2017.08.25
	 */
	@RequestMapping("/showLabCenterDeviceDetail")
	public ModelAndView showLabCenterDeviceDetail(@RequestParam int labCenterId,@RequestParam int currpage){
		ModelAndView mav = new ModelAndView();
		int pageSize = 20;
		LabCenter labcenter=labCenterService.findLabCenterByPrimaryKey(labCenterId);
		// 总记录数
		int totalRecords = labCenterService.findAllLabRoomDeviceByQueryCount(labcenter);
		// 分页
		Map<String, Integer> pageModel = shareService.getPage(
				currpage, pageSize, totalRecords);
		mav.addObject("labCenterId",labCenterId);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", currpage);
		
		List<LabRoomDevice> labCenterDevice=labCenterService.findAllLabRoomDeviceByQuery(labcenter, currpage,pageSize);;
		mav.addObject("labCenterDevice",labCenterDevice);
		mav.setViewName("lab/lab_center/showLabCenterDeviceDetail.jsp");
		return mav;
	}
}
