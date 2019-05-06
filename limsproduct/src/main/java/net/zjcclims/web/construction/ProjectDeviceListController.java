package net.zjcclims.web.construction;


import net.zjcclims.dao.CFundAppItemDAO;
import net.zjcclims.dao.ProjectDeviceListDAO;
import net.zjcclims.domain.CFundAppItem;
import net.zjcclims.domain.ProjectDeviceList;
import net.zjcclims.service.construction.ProjectDeviceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Spring MVC controller that handles CRUD requests for ProjectDeviceList entities
 * 
 */

@Controller("ProjectDeviceListController")
public class ProjectDeviceListController {

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
	 * Service injected by Spring that provides CRUD operations for ProjectDeviceList entities
	 * 
	 */
	@Autowired
	private ProjectDeviceListService projectDeviceListService;

	/**
	 * Delete an existing ProjectDeviceList entity
	 * 
	 */
	@RequestMapping("/deleteProjectDeviceList")
	public String deleteProjectDeviceList(@RequestParam Integer idKey) {
		ProjectDeviceList projectdevicelist = projectDeviceListDAO.findProjectDeviceListByPrimaryKey(idKey);
		projectDeviceListService.deleteProjectDeviceList(projectdevicelist);
		return "forward:/indexProjectDeviceList";
	}

	/**
	 * Select the child CFundAppItem entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectDeviceListCFundAppItem")
	public ModelAndView confirmDeleteProjectDeviceListCFundAppItem(@RequestParam Integer projectdevicelist_id, @RequestParam Integer related_cfundappitem_id) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("cfundappitem", cFundAppItemDAO.findCFundAppItemByPrimaryKey(related_cfundappitem_id));
		mav.addObject("projectdevicelist_id", projectdevicelist_id);
		mav.setViewName("projectdevicelist/cfundappitem/deleteCFundAppItem.jsp");

		return mav;
	}

	/**
	 * Delete an existing CFundAppItem entity
	 * 
	 */
	@RequestMapping("/deleteProjectDeviceListCFundAppItem")
	public ModelAndView deleteProjectDeviceListCFundAppItem(@RequestParam Integer projectdevicelist_id, @RequestParam Integer related_cfundappitem_id) {
		ModelAndView mav = new ModelAndView();

		ProjectDeviceList projectdevicelist = projectDeviceListService.deleteProjectDeviceListCFundAppItem(projectdevicelist_id, related_cfundappitem_id);

		mav.addObject("projectdevicelist_id", projectdevicelist_id);
		mav.addObject("projectdevicelist", projectdevicelist);
		mav.setViewName("projectdevicelist/viewProjectDeviceList.jsp");

		return mav;
	}

	/**
	 * Select an existing ProjectDeviceList entity
	 * 
	 */
	@RequestMapping("/selectProjectDeviceList")
	public ModelAndView selectProjectDeviceList(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectdevicelist", projectDeviceListDAO.findProjectDeviceListByPrimaryKey(idKey));
		mav.setViewName("projectdevicelist/viewProjectDeviceList.jsp");

		return mav;
	}

	/**
	 * Show all CFundAppItem entities by ProjectDeviceList
	 * 
	 */
	@RequestMapping("/listProjectDeviceListCFundAppItem")
	public ModelAndView listProjectDeviceListCFundAppItem(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectdevicelist", projectDeviceListDAO.findProjectDeviceListByPrimaryKey(idKey));
		mav.setViewName("projectdevicelist/cfundappitem/listCFundAppItem.jsp");

		return mav;
	}

	/**
	 * View an existing CFundAppItem entity
	 * 
	 */
	@RequestMapping("/selectProjectDeviceListCFundAppItem")
	public ModelAndView selectProjectDeviceListCFundAppItem(@RequestParam Integer projectdevicelist_id, @RequestParam Integer cfundappitem_id) {
		CFundAppItem cfundappitem = cFundAppItemDAO.findCFundAppItemByPrimaryKey(cfundappitem_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectdevicelist_id", projectdevicelist_id);
		mav.addObject("cfundappitem", cfundappitem);
		mav.setViewName("projectdevicelist/cfundappitem/viewCFundAppItem.jsp");

		return mav;
	}

	/**
	 * Select the ProjectDeviceList entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectDeviceList")
	public ModelAndView confirmDeleteProjectDeviceList(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectdevicelist", projectDeviceListDAO.findProjectDeviceListByPrimaryKey(idKey));
		mav.setViewName("projectdevicelist/deleteProjectDeviceList.jsp");

		return mav;
	}

	/**
	 * Show all ProjectDeviceList entities
	 * 
	 */
	@RequestMapping("/indexProjectDeviceList")
	public ModelAndView listProjectDeviceLists() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectdevicelists", projectDeviceListService.loadProjectDeviceLists());

		mav.setViewName("projectdevicelist/listProjectDeviceLists.jsp");

		return mav;
	}

	/**
	 * Entry point to show all ProjectDeviceList entities
	 * 
	 */
	public String indexProjectDeviceList() {
		return "redirect:/indexProjectDeviceList";
	}

	/**
	 * Edit an existing ProjectDeviceList entity
	 * 
	 */
	@RequestMapping("/editProjectDeviceList")
	public ModelAndView editProjectDeviceList(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectdevicelist", projectDeviceListDAO.findProjectDeviceListByPrimaryKey(idKey));
		mav.setViewName("projectdevicelist/editProjectDeviceList.jsp");

		return mav;
	}

	/**
	 */
	@RequestMapping("/projectdevicelistController/binary.action")
	public ModelAndView streamBinary(@ModelAttribute HttpServletRequest request, @ModelAttribute HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("streamedBinaryContentView");
		return mav;

	}

	/**
	 * Save an existing CFundAppItem entity
	 * 
	 */
	@RequestMapping("/saveProjectDeviceListCFundAppItem")
	public ModelAndView saveProjectDeviceListCFundAppItem(@RequestParam Integer projectdevicelist_id, @ModelAttribute CFundAppItem cfundappitem) {
		ProjectDeviceList parent_projectdevicelist = projectDeviceListService.saveProjectDeviceListCFundAppItem(projectdevicelist_id, cfundappitem);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectdevicelist_id", projectdevicelist_id);
		mav.addObject("projectdevicelist", parent_projectdevicelist);
		mav.setViewName("projectdevicelist/viewProjectDeviceList.jsp");

		return mav;
	}

	/**
	 * Edit an existing CFundAppItem entity
	 * 
	 */
	@RequestMapping("/editProjectDeviceListCFundAppItem")
	public ModelAndView editProjectDeviceListCFundAppItem(@RequestParam Integer projectdevicelist_id, @RequestParam Integer cfundappitem_id) {
		CFundAppItem cfundappitem = cFundAppItemDAO.findCFundAppItemByPrimaryKey(cfundappitem_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectdevicelist_id", projectdevicelist_id);
		mav.addObject("cfundappitem", cfundappitem);
		mav.setViewName("projectdevicelist/cfundappitem/editCFundAppItem.jsp");

		return mav;
	}

	/**
	 * Create a new ProjectDeviceList entity
	 * 
	 */
	@RequestMapping("/newProjectDeviceList")
	public ModelAndView newProjectDeviceList() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectdevicelist", new ProjectDeviceList());
		mav.addObject("newFlag", true);
		mav.setViewName("projectdevicelist/editProjectDeviceList.jsp");

		return mav;
	}

	/**
	 * Save an existing ProjectDeviceList entity
	 * 
	 */
	@RequestMapping("/saveProjectDeviceList")
	public String saveProjectDeviceList(@ModelAttribute ProjectDeviceList projectdevicelist) {
		projectDeviceListService.saveProjectDeviceList(projectdevicelist);
		return "forward:/indexProjectDeviceList";
	}

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

	/**
	 * Create a new CFundAppItem entity
	 * 
	 */
	@RequestMapping("/newProjectDeviceListCFundAppItem")
	public ModelAndView newProjectDeviceListCFundAppItem(@RequestParam Integer projectdevicelist_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("projectdevicelist_id", projectdevicelist_id);
		mav.addObject("cfundappitem", new CFundAppItem());
		mav.addObject("newFlag", true);
		mav.setViewName("projectdevicelist/cfundappitem/editCFundAppItem.jsp");

		return mav;
	}
}