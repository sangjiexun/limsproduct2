package net.zjcclims.web.construction;


import net.zjcclims.dao.CProjectBudgetDAO;
import net.zjcclims.dao.ProjectBudgetDAO;
import net.zjcclims.domain.CProjectBudget;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.construction.CProjectBudgetService;
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
 * Spring MVC controller that handles CRUD requests for CProjectBudget entities
 * 
 */

@Controller("CProjectBudgetController")
@SessionAttributes("selected_academy")
public class CProjectBudgetController {

	/**
	 * DAO injected by Spring that manages CProjectBudget entities
	 * 
	 */
	@Autowired
	private CProjectBudgetDAO cProjectBudgetDAO;

	/**
	 * DAO injected by Spring that manages ProjectBudget entities
	 * 
	 */
	@Autowired
	private ProjectBudgetDAO projectBudgetDAO;

	/**
	 * Service injected by Spring that provides CRUD operations for CProjectBudget entities
	 * 
	 */
	@Autowired
	private CProjectBudgetService cProjectBudgetService;
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
	 * 功能：字典管理--项目来源
	 * 作者：李德
	 * 时间：2015-05-08
	 ****************************************************************************/
	@RequestMapping("/dictionary/listCProjectBudget")
	public ModelAndView listCProjectSource(@ModelAttribute CProjectBudget cProjectBudget,
			@RequestParam int page,int modelId,
			@ModelAttribute("selected_academy") String acno){
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();

		//查询表单的对象
		mav.addObject("cProjectBudget", cProjectBudget);
		int pageSize=10;//每页20条记录
		//查询出来的总记录条数
		int totalRecords=cProjectBudgetService.findAllCProjectBudgetByCProjectBudget(cProjectBudget).size();
		//分页信息
		Map<String,Integer> pageModel =shareService.getPage(pageSize, page,totalRecords);
		//根据分页信息查询出来的记录
		List<CProjectBudget> listCProjectBudget=cProjectBudgetService.findAllCProjectBudgetByCProjectBudget(cProjectBudget,page,pageSize);
		mav.addObject("listCProjectBudget",listCProjectBudget);
		mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", page);
		mav.addObject("pageSize", pageSize);
//		mav.addObject("cid", cid);
		//mav.addObject("exportLabConstructApp",authorityService.getAuthorityResourses(906));


		mav.setViewName("dictionary/listCProjectBudget.jsp");
		return mav;

	}
	
	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目申请列表
	 * 作者：李德
	 * 时间：2015-05-08
	 ****************************************************************************/
	@RequestMapping("/dictionary/newCProjectBudget")
	public ModelAndView newCProjectBudget(
			@ModelAttribute CProjectBudget cProjectBudget,
			@RequestParam int modelId,
			@ModelAttribute("selected_academy") String acno) {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		mav.addObject("cProjectBudget", new CProjectBudget());
		mav.setViewName("dictionary/newCProjectBudget.jsp");
		return mav;
		
	}
	
	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目申请列表
	 * 作者：李德
	 * 时间：2015-05-08
	 ****************************************************************************/
	@RequestMapping("/dictionary/editCProjectBudget")
	public ModelAndView editCProjectBudget(@RequestParam Integer idKey, int modelId,
                                           @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();

		CProjectBudget cProjectBudget = cProjectBudgetService.findCProjectBudgetByPrimaryKey(idKey);
		mav.addObject("cProjectBudget", cProjectBudgetDAO
				.findCProjectBudgetByPrimaryKey(idKey));
		
		mav.setViewName("dictionary/editCProjectBudget.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目申请列表
	 * 作者：李德
	 * 时间：2015-05-08
	 ****************************************************************************/
	@RequestMapping("/saveCProjectBudget")
	public String saveCProjectBudget(
			@ModelAttribute CProjectBudget cProjectBudget) {
		cProjectBudgetService.save(cProjectBudget);
		return "redirect:/dictionary/listCProjectBudget?page=1&modelId=1211";
	}
	
	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目申请列表
	 * 作者：李德
	 * 时间：2015-05-11
	 ****************************************************************************/
	@RequestMapping("/dictionary/deleteCProjectBudget")
	public String deleteCProjectBudget(@RequestParam Integer idKey, int modelId) {
		CProjectBudget cProjectBudget = cProjectBudgetService
				.findCProjectBudgetByPrimaryKey(idKey);

		cProjectBudgetService.deleteCProjectBudget(cProjectBudget);

		return "redirect:/dictionary/listCProjectBudget?page=1&modelId=1211";
	}

}