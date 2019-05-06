package net.zjcclims.web.construction;


import net.zjcclims.dao.CProjectSourceDAO;
import net.zjcclims.dao.LabConstructAppDAO;
import net.zjcclims.domain.CProjectSource;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.construction.CProjectSourceService;
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
 * Spring MVC controller that handles CRUD requests for CProjectSource entities
 * 
 */

@Controller("CProjectSourceController")
public class CProjectSourceController {

	/**
	 * DAO injected by Spring that manages CProjectSource entities
	 * 
	 */
	@Autowired
	private CProjectSourceDAO cProjectSourceDAO;

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * Service injected by Spring that provides CRUD operations for CProjectSource entities
	 * 
	 */
	@Autowired
	private CProjectSourceService cProjectSourceService;
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
	@RequestMapping("/dictionary/listCProjectSource")
	public ModelAndView listCProjectSource(@ModelAttribute CProjectSource cProjectSource,
			@RequestParam int page){
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//查询表单的对象
		mav.addObject("cProjectSource", cProjectSource);
		int pageSize=10;//每页20条记录
		//查询出来的总记录条数
		int totalRecords=cProjectSourceService.findAllCProjectSourceByCProjectSource(cProjectSource).size();
		//分页信息
		Map<String,Integer> pageModel =shareService.getPage(page, pageSize,totalRecords);
		//根据分页信息查询出来的记录
		List<CProjectSource> listCProjectSource=cProjectSourceService.findAllCProjectSourceByCProjectSource(cProjectSource,page,pageSize);
		mav.addObject("listCProjectSource",listCProjectSource);
		mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", page);
		mav.addObject("pageSize", pageSize);

		mav.setViewName("dictionary/listCProjectSource.jsp");
		return mav;
		
	}
	
	/****************************************************************************
	 * 功能：字典管理--项目来源新增
	 * 作者：李德
	 * 时间：2015-05-08
	 ****************************************************************************/
	@RequestMapping("/dictionary/newCProjectSource")
	public ModelAndView newCProjectSource(
			@ModelAttribute CProjectSource cProjectSource,
			@ModelAttribute("selected_academy") String acno) {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		mav.addObject("cProjectSource", new CProjectSource());
		mav.setViewName("dictionary/newCProjectSource.jsp");
		return mav;
		
	}
	
	/****************************************************************************
	 * 功能：字典管理--项目来源编辑
	 * 作者：李德
	 * 时间：2015-05-08
	 ****************************************************************************/
	@RequestMapping("/dictionary/editCProjectSource")
	public ModelAndView editCProjectSource(@RequestParam Integer idKey,
                                           @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();

		CProjectSource cProjectSource = cProjectSourceService.findCProjectSourceByPrimaryKey(idKey);
		mav.addObject("cProjectSource", cProjectSourceDAO
				.findCProjectSourceByPrimaryKey(idKey));
		
		mav.setViewName("dictionary/editCProjectSource.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：字典管理--项目来源保存
	 * 作者：李德
	 * 时间：2015-05-08
	 ****************************************************************************/
	@RequestMapping("/saveCProjectSource")
	public String saveCProjectSource(
			@ModelAttribute CProjectSource cProjectSource) {
		cProjectSourceService.save(cProjectSource);
		return "redirect:/dictionary/listCProjectSource?page=1&modelId=1201";
	}
	
	/****************************************************************************
	 * 功能：字典管理--项目来源删除
	 * 作者：李德
	 * 时间：2015-05-11
	 ****************************************************************************/
	@RequestMapping("/dictionary/deleteCProjectSource")
	public String deleteVirtualLabConstruction(@RequestParam Integer idKey) {
		CProjectSource cProjectSource = cProjectSourceService
				.findCProjectSourceByPrimaryKey(idKey);

		cProjectSourceService.deleteCProjectSource(cProjectSource);

		return "redirect:/dictionary/listCProjectSource?page=1";
	}
	
}