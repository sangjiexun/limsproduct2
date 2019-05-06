package net.zjcclims.web.construction;


import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.construction.CFundAppItemService;
import net.zjcclims.service.system.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Spring MVC controller that handles CRUD requests for CFundAppItem entities
 * 
 */

@Controller("CFundAppItemController")
@SessionAttributes("selected_academy")
public class CFundAppItemController {

	/**
	 * DAO injected by Spring that manages CFundAppItem entities
	 * 
	 */
	@Autowired
	private CFundAppItemDAO cFundAppItemDAO;

	/**
	 * DAO injected by Spring that manages ProjectDeviceList entities
	 * 
	 */
	@Autowired
	private ProjectDeviceListDAO projectDeviceListDAO;

	/**
	 * Service injected by Spring that provides CRUD operations for CFundAppItem entities
	 * 
	 */
	@Autowired
	private CFundAppItemService cFundAppItemService;
	@Autowired
	AuthorityService authorityService;
	@Autowired
	ShareService shareService;

	/**
	 * Register custom, context-specific property editors
	 * 
	 */
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

	/****************************************************************************
	 * 功能：字典管理--预算科目
	 * 作者：李德
	 * 时间：2015-06-16
	 ****************************************************************************/
	@RequestMapping("/dictionary/listCFundAppItem")
	public ModelAndView listCFundAppItem(@ModelAttribute CFundAppItem cFundAppItem,
			@RequestParam int page,
			@ModelAttribute("selected_academy") String acno){
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		// 权限控制
/*		if (authorityService.getAuthorityResourses(modelId).getIsActive() == 0) {
			mav.setViewName("system/authorityManage/accessDenied.jsp");
			return mav;
		}else{

		}
		*/
		//查询表单的对象
		mav.addObject("cFundAppItem", cFundAppItem);
		int pageSize=10;//每页20条记录
		//查询出来的总记录条数
		int totalRecords=cFundAppItemService.findAllCFundAppItemByCFundAppItem(cFundAppItem).size();
		//分页信息
		Map<String,Integer> pageModel =shareService.getPage(page, pageSize,totalRecords);
		//根据分页信息查询出来的记录
		List<CFundAppItem> listCFundAppItem=cFundAppItemService.findAllCFundAppItemByCFundAppItem(cFundAppItem,page,pageSize);
		mav.addObject("listCFundAppItem",listCFundAppItem);
		mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", page);
		mav.addObject("pageSize", pageSize);
//		mav.addObject("cid", cid);
		//mav.addObject("exportLabConstructApp",authorityService.getAuthorityResourses(906));

		
		mav.setViewName("dictionary/listCFundAppItem.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：字典管理--预算科目
	 * 作者：李德
	 * 时间：2015-06-16
	 ****************************************************************************/
	@RequestMapping("/dictionary/newCFundAppItem")
	public ModelAndView newCFundAppItem(
			@ModelAttribute CFundAppItem cFundAppItem,
			@ModelAttribute("selected_academy") String acno) {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		mav.addObject("cFundAppItem", new CFundAppItem());
		mav.setViewName("dictionary/newCFundAppItem.jsp");
		return mav;
		
	}
	
	/****************************************************************************
	 * 功能：字典管理--预算科目
	 * 作者：李德
	 * 时间：2015-06-16
	 ****************************************************************************/
	@RequestMapping("/dictionary/editCFundAppItem")
	public ModelAndView editCFundAppItem(@RequestParam Integer idKey,
                                         @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();

		CFundAppItem cFundAppItem = cFundAppItemService.findCFundAppItemByPrimaryKey(idKey);
		mav.addObject("cFundAppItem", cFundAppItemDAO
				.findCFundAppItemByPrimaryKey(idKey));
		
		mav.setViewName("dictionary/editCFundAppItem.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：字典管理--预算科目
	 * 作者：李德
	 * 时间：2015-06-16
	 ****************************************************************************/
	@RequestMapping("/saveCFundAppItem")
	public String saveCFundAppItem(
			@ModelAttribute CFundAppItem cFundAppItem) {
		cFundAppItemService.save(cFundAppItem);
		return "redirect:/dictionary/listCFundAppItem?page=1&modelId=1211";
	}
	
	/****************************************************************************
	 * 功能：字典管理--预算科目
	 * 作者：李德
	 * 时间：2015-06-16
	 ****************************************************************************/
	@RequestMapping("/dictionary/deleteCFundAppItem")
	public String deleteCFundAppItem(@RequestParam Integer idKey) {
		CFundAppItem cFundAppItem = cFundAppItemService
				.findCFundAppItemByPrimaryKey(idKey);

		cFundAppItemService.deleteCFundAppItem(cFundAppItem);

		return "redirect:/dictionary/listCFundAppItem?page=1&modelId=1211";
	}
	
}