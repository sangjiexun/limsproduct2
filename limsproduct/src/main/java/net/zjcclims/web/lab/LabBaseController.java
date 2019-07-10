package net.zjcclims.web.lab;

import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.LabAnnexDAO;
import net.zjcclims.domain.LabAnnex;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabBaseService;
import net.zjcclims.service.system.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller("LabBaseController")
@SessionAttributes("selected_labBase")
@RequestMapping("/labBase")
public class LabBaseController<JsonResult> {

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
	private LabBaseService labBaseService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private LabAnnexDAO labAnnexDAO;
	/*****************************
	*Description 实训室基础信息管理-基地列表
	*
	*@author:余南新
	*@param:
	*@date:2017.4.25
	*****************************/
	@RequestMapping("/listLabBase")
	public ModelAndView listLabBase(@RequestParam int currpage, @ModelAttribute LabAnnex labAnnex, HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		int pageSize = CommonConstantInterface.INT_PAGESIZE;  //分页记录数
		int totalRecords = labBaseService.getAllLabBaseByQueryCount(labAnnex, request);

		mav.addObject("labAnnex", labAnnex);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		List<LabAnnex> labAnnexList = labBaseService.findAllLabBaseByQuery(labAnnex, currpage, pageSize,request);
		for(LabAnnex la: labAnnexList){
			BigDecimal area = BigDecimal.ZERO;
			for (LabRoom l : la.getLabBases()) {
				if (l.getLabRoomArea() != null)
					area = area.add(l.getLabRoomArea());
			}
			la.setLabCapacity(area.toString());
		}
		mav.addObject("listLabAnnex",labAnnexList);
		mav.addObject("page",currpage);
		mav.setViewName("lab/lab_base/listLabBase.jsp");
		return mav;
	}
	/*****************************
	 *Description 显示实验基地下的实验室
	 *
	 *@author:廖文辉
	 *@param:
	 *@date:2018.11.15
	 *****************************/
	@RequestMapping("/showLabBaseRoomDetail")
	public ModelAndView showLabBaseRoomDetail(@RequestParam int labBaseId,@RequestParam int currpage){
		ModelAndView mav = new ModelAndView();
		int pageSize = 20;

		LabAnnex labAnnex=labAnnexDAO.findLabAnnexByPrimaryKey(labBaseId);
		// 总记录数

		int totalRecords = labBaseService.findLabRoomByQuery(labAnnex, 1, -1).size();
		// 分页
		Map<String, Integer> pageModel = shareService.getPage(
				currpage, pageSize, totalRecords);
		mav.addObject("labBaseId",labBaseId);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", currpage);

		List<LabRoom> labBaseRoom=labBaseService.findLabRoomByQuery(labAnnex, 1, -1);
		mav.addObject("labBaseRoom",labBaseRoom);
		mav.setViewName("lab/lab_base/showLabBaseRoomDetail.jsp");
		return mav;
	}
	/***************************** 
	*Description 实训室基础信息管理-新建基地列表
	*
	*@author:余南新
	*@param:
	*@date:2017.4.25
	*****************************/
	@RequestMapping("/newLabBase")
	public ModelAndView newLabBase(int page){
		ModelAndView mav = new ModelAndView();
		mav.addObject("listSchoolAdademy", systemService.getAllSchoolAcademy(1, -1));
		mav.addObject("labAnnex", new LabAnnex());
		mav.setViewName("lab/lab_base/editLabBase.jsp");
		mav.addObject("page",page);
		return mav;
	}
	
	/**
	 * Description 实训室基础信息管理-编辑基地列表
	 * @param labBaseId 编辑对象主键
	 * @author 陈乐为 2018-8-6
	 */
	@RequestMapping("/editLabBase")
	public ModelAndView editLabBase(@RequestParam int labBaseId,int page){
		ModelAndView mav = new ModelAndView();
		mav.addObject("listSchoolAdademy", systemService.getAllSchoolAcademy(1, -1));
		mav.addObject("labAnnex", labBaseService.findLabAnnexByPrimaryKey(labBaseId));
		mav.addObject("flag", 1);
		mav.addObject("page",page);
		mav.setViewName("lab/lab_base/editLabBase.jsp");
		return mav;
	}
	
	/**
	 * Description 实训室基础信息管理-保存基地列表
	 * @author 陈乐为 2018-8-6
	 */
	@RequestMapping("/saveLabBase")
	public String saveLabBase(@ModelAttribute LabAnnex labAnnex,int page,HttpServletRequest request){
		labBaseService.saveLabAnnex(labAnnex, request);
		return "redirect:/labBase/listLabBase?currpage="+page;
	}
	
	/**
	 * Description 实训室基础信息管理-删除基地列表
	 * @author 陈乐为 2018-8-6
	 */
	@Transactional
	@RequestMapping("/deleteLabBase")
	public String deleteLabBase(@RequestParam int labBaseId,int page){
		labBaseService.deleteLabAnnex(labBaseId);
		
		return "redirect:/labBase/listLabBase?currpage="+page;
	}
}
