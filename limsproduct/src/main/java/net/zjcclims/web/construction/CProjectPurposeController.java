package net.zjcclims.web.construction;

import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.construction.CProjectPurposeService;
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
 * Spring MVC controller that handles CRUD requests for CProjectPurpose entities
 * 
 */

@Controller("CProjectPurposeController")
public class CProjectPurposeController {

	/**
	 * DAO injected by Spring that manages CProjectPurpose entities
	 * 
	 */
	@Autowired
	private CProjectPurposeDAO cProjectPurposeDAO;

	/**
	 * Service injected by Spring that provides CRUD operations for CProjectPurpose entities
	 * 
	 */
	@Autowired
	private CProjectPurposeService cProjectPurposeService;
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
	 * 功能：字典管理--用途
	 * 作者：李德
	 * 时间：2015-05-08
	 ****************************************************************************/
	@RequestMapping("/dictionary/listCProjectPurpose")
	public ModelAndView listCProjectPurpose(@ModelAttribute CProjectPurpose cProjectPurpose,
			@RequestParam int page){
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();

		//查询表单的对象
		mav.addObject("cProjectPurpose", cProjectPurpose);
		int pageSize=10;//每页20条记录
		//查询出来的总记录条数
		int totalRecords=cProjectPurposeService.findAllCProjectPurposeByCProjectPurpose(cProjectPurpose).size();
		//分页信息
		Map<String,Integer> pageModel =shareService.getPage(page, pageSize,totalRecords);
		//根据分页信息查询出来的记录
		List<CProjectPurpose> listCProjectPurpose=cProjectPurposeService.findAllCProjectPurposeByCProjectPurpose(cProjectPurpose,page,pageSize);
		mav.addObject("listCProjectPurpose",listCProjectPurpose);
		mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", page);
		mav.addObject("pageSize", pageSize);

		mav.setViewName("dictionary/listCProjectPurpose.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：字典管理--用途新增
	 * 作者：李德
	 * 时间：2015-05-08
	 ****************************************************************************/
	@RequestMapping("/dictionary/newCProjectPurpose")
	public ModelAndView newCProjectPurpose(
			@ModelAttribute CProjectPurpose cProjectPurpose,
			@ModelAttribute("selected_academy") String acno) {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();
		mav.addObject("cProjectPurpose", new CProjectPurpose());
		mav.setViewName("dictionary/newCProjectPurpose.jsp");
		return mav;
		
	}
	
	/****************************************************************************
	 * 功能：字典管理--用途编辑
	 * 作者：李德
	 * 时间：2015-05-08
	 ****************************************************************************/
	@RequestMapping("/dictionary/editCProjectPurpose")
	public ModelAndView editCProjectSource(@RequestParam Integer idKey,
                                           @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();

		CProjectPurpose cProjectPurpose = cProjectPurposeService.findCProjectPurposeByPrimaryKey(idKey);
		mav.addObject("cProjectPurpose", cProjectPurposeDAO
				.findCProjectPurposeByPrimaryKey(idKey));
		
		mav.setViewName("dictionary/editCProjectPurpose.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：字典管理--用途保存
	 * 作者：李德
	 * 时间：2015-05-08
	 ****************************************************************************/
	@RequestMapping("/saveCProjectPurpose")
	public String saveCProjectPurpose(
			@ModelAttribute CProjectPurpose cProjectPurpose) {
		cProjectPurposeService.save(cProjectPurpose);
		return "redirect:/dictionary/listCProjectPurpose?page=1&modelId=1221";
	}
	
	/****************************************************************************
	 * 功能：字典管理--用途删除
	 * 作者：李德
	 * 时间：2015-05-11
	 ****************************************************************************/
	@RequestMapping("/dictionary/deleteCProjectPurpose")
	public String deleteCProjectPurpose(@RequestParam Integer idKey) {
		CProjectPurpose cProjectPurpose = cProjectPurposeService
				.findCProjectPurposeByPrimaryKey(idKey);

		cProjectPurposeService.deleteCProjectPurpose(cProjectPurpose);

		return "redirect:/dictionary/listCProjectPurpose?page=1&modelId=1221";
	}
	
}