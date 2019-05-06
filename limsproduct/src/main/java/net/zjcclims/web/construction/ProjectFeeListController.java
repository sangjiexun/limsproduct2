package net.zjcclims.web.construction;


import net.zjcclims.dao.CFundAppItemDAO;
import net.zjcclims.dao.LabConstructAppDAO;
import net.zjcclims.dao.ProjectFeeListDAO;
import net.zjcclims.domain.CFundAppItem;
import net.zjcclims.domain.LabConstructApp;
import net.zjcclims.domain.ProjectFeeList;
import net.zjcclims.service.construction.ProjectFeeListService;
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
 * Spring MVC controller that handles CRUD requests for ProjectFeeList entities
 * 
 */

@Controller("ProjectFeeListController")
public class ProjectFeeListController {

	/**
	 * DAO injected by Spring that manages CFundAppItem entities
	 * 
	 */
	@Autowired
	private CFundAppItemDAO cFundAppItemDAO;

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * DAO injected by Spring that manages ProjectFeeList entities
	 * 
	 */
	@Autowired
	private ProjectFeeListDAO projectFeeListDAO;

	/**
	 * Service injected by Spring that provides CRUD operations for ProjectFeeList entities
	 * 
	 */
	@Autowired
	private ProjectFeeListService projectFeeListService;

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/saveProjectFeeListLabConstructApp")
	public ModelAndView saveProjectFeeListLabConstructApp(@RequestParam Integer projectfeelist_id, @ModelAttribute LabConstructApp labconstructapp) {
		ProjectFeeList parent_projectfeelist = projectFeeListService.saveProjectFeeListLabConstructApp(projectfeelist_id, labconstructapp);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectfeelist_id", projectfeelist_id);
		mav.addObject("projectfeelist", parent_projectfeelist);
		mav.setViewName("projectfeelist/viewProjectFeeList.jsp");

		return mav;
	}

	/**
	 * Edit an existing ProjectFeeList entity
	 * 
	 */
	@RequestMapping("/editProjectFeeList")
	public ModelAndView editProjectFeeList(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectfeelist", projectFeeListDAO.findProjectFeeListByPrimaryKey(idKey));
		mav.setViewName("projectfeelist/editProjectFeeList.jsp");

		return mav;
	}

	/**
	 * Create a new CFundAppItem entity
	 * 
	 */
	@RequestMapping("/newProjectFeeListCFundAppItem")
	public ModelAndView newProjectFeeListCFundAppItem(@RequestParam Integer projectfeelist_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("projectfeelist_id", projectfeelist_id);
		mav.addObject("cfundappitem", new CFundAppItem());
		mav.addObject("newFlag", true);
		mav.setViewName("projectfeelist/cfundappitem/editCFundAppItem.jsp");

		return mav;
	}

	/**
	 * View an existing CFundAppItem entity
	 * 
	 */
	@RequestMapping("/selectProjectFeeListCFundAppItem")
	public ModelAndView selectProjectFeeListCFundAppItem(@RequestParam Integer projectfeelist_id, @RequestParam Integer cfundappitem_id) {
		CFundAppItem cfundappitem = cFundAppItemDAO.findCFundAppItemByPrimaryKey(cfundappitem_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectfeelist_id", projectfeelist_id);
		mav.addObject("cfundappitem", cfundappitem);
		mav.setViewName("projectfeelist/cfundappitem/viewCFundAppItem.jsp");

		return mav;
	}

	/**
	 * Select the ProjectFeeList entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectFeeList")
	public ModelAndView confirmDeleteProjectFeeList(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectfeelist", projectFeeListDAO.findProjectFeeListByPrimaryKey(idKey));
		mav.setViewName("projectfeelist/deleteProjectFeeList.jsp");

		return mav;
	}

	/**
	 * View an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/selectProjectFeeListLabConstructApp")
	public ModelAndView selectProjectFeeListLabConstructApp(@RequestParam Integer projectfeelist_id, @RequestParam Integer labconstructapp_id) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectfeelist_id", projectfeelist_id);
		mav.addObject("labconstructapp", labconstructapp);
		mav.setViewName("projectfeelist/labconstructapp/viewLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Select the child CFundAppItem entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectFeeListCFundAppItem")
	public ModelAndView confirmDeleteProjectFeeListCFundAppItem(@RequestParam Integer projectfeelist_id, @RequestParam Integer related_cfundappitem_id) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("cfundappitem", cFundAppItemDAO.findCFundAppItemByPrimaryKey(related_cfundappitem_id));
		mav.addObject("projectfeelist_id", projectfeelist_id);
		mav.setViewName("projectfeelist/cfundappitem/deleteCFundAppItem.jsp");

		return mav;
	}

	/**
	 * Edit an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/editProjectFeeListLabConstructApp")
	public ModelAndView editProjectFeeListLabConstructApp(@RequestParam Integer projectfeelist_id, @RequestParam Integer labconstructapp_id) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectfeelist_id", projectfeelist_id);
		mav.addObject("labconstructapp", labconstructapp);
		mav.setViewName("projectfeelist/labconstructapp/editLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Edit an existing CFundAppItem entity
	 * 
	 */
	@RequestMapping("/editProjectFeeListCFundAppItem")
	public ModelAndView editProjectFeeListCFundAppItem(@RequestParam Integer projectfeelist_id, @RequestParam Integer cfundappitem_id) {
		CFundAppItem cfundappitem = cFundAppItemDAO.findCFundAppItemByPrimaryKey(cfundappitem_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectfeelist_id", projectfeelist_id);
		mav.addObject("cfundappitem", cfundappitem);
		mav.setViewName("projectfeelist/cfundappitem/editCFundAppItem.jsp");

		return mav;
	}

	/**
	 * Show all LabConstructApp entities by ProjectFeeList
	 * 
	 */
	@RequestMapping("/listProjectFeeListLabConstructApp")
	public ModelAndView listProjectFeeListLabConstructApp(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectfeelist", projectFeeListDAO.findProjectFeeListByPrimaryKey(idKey));
		mav.setViewName("projectfeelist/labconstructapp/listLabConstructApp.jsp");

		return mav;
	}

	/**
	 */
	@RequestMapping("/projectfeelistController/binary.action")
	public ModelAndView streamBinary(@ModelAttribute HttpServletRequest request, @ModelAttribute HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("streamedBinaryContentView");
		return mav;

	}

	/**
	 * Save an existing ProjectFeeList entity
	 * 
	 */
	@RequestMapping("/saveProjectFeeList")
	public String saveProjectFeeList(@ModelAttribute ProjectFeeList projectfeelist) {
		projectFeeListService.saveProjectFeeList(projectfeelist);
		return "forward:/indexProjectFeeList";
	}

	/**
	 * Select an existing ProjectFeeList entity
	 * 
	 */
	@RequestMapping("/selectProjectFeeList")
	public ModelAndView selectProjectFeeList(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectfeelist", projectFeeListDAO.findProjectFeeListByPrimaryKey(idKey));
		mav.setViewName("projectfeelist/viewProjectFeeList.jsp");

		return mav;
	}

	/**
	 * Show all ProjectFeeList entities
	 * 
	 */
	@RequestMapping("/indexProjectFeeList")
	public ModelAndView listProjectFeeLists() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectfeelists", projectFeeListService.loadProjectFeeLists());

		mav.setViewName("projectfeelist/listProjectFeeLists.jsp");

		return mav;
	}

	/**
	 * Entry point to show all ProjectFeeList entities
	 * 
	 */
	public String indexProjectFeeList() {
		return "redirect:/indexProjectFeeList";
	}

	/**
	 * Show all CFundAppItem entities by ProjectFeeList
	 * 
	 */
	@RequestMapping("/listProjectFeeListCFundAppItem")
	public ModelAndView listProjectFeeListCFundAppItem(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectfeelist", projectFeeListDAO.findProjectFeeListByPrimaryKey(idKey));
		mav.setViewName("projectfeelist/cfundappitem/listCFundAppItem.jsp");

		return mav;
	}

	/**
	 * Create a new ProjectFeeList entity
	 * 
	 */
	@RequestMapping("/newProjectFeeList")
	public ModelAndView newProjectFeeList() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectfeelist", new ProjectFeeList());
		mav.addObject("newFlag", true);
		mav.setViewName("projectfeelist/editProjectFeeList.jsp");

		return mav;
	}

	/**
	 * Delete an existing CFundAppItem entity
	 * 
	 */
	@RequestMapping("/deleteProjectFeeListCFundAppItem")
	public ModelAndView deleteProjectFeeListCFundAppItem(@RequestParam Integer projectfeelist_id, @RequestParam Integer related_cfundappitem_id) {
		ModelAndView mav = new ModelAndView();

		ProjectFeeList projectfeelist = projectFeeListService.deleteProjectFeeListCFundAppItem(projectfeelist_id, related_cfundappitem_id);

		mav.addObject("projectfeelist_id", projectfeelist_id);
		mav.addObject("projectfeelist", projectfeelist);
		mav.setViewName("projectfeelist/viewProjectFeeList.jsp");

		return mav;
	}

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/deleteProjectFeeListLabConstructApp")
	public ModelAndView deleteProjectFeeListLabConstructApp(@RequestParam Integer projectfeelist_id, @RequestParam Integer related_labconstructapp_id) {
		ModelAndView mav = new ModelAndView();

		ProjectFeeList projectfeelist = projectFeeListService.deleteProjectFeeListLabConstructApp(projectfeelist_id, related_labconstructapp_id);

		mav.addObject("projectfeelist_id", projectfeelist_id);
		mav.addObject("projectfeelist", projectfeelist);
		mav.setViewName("projectfeelist/viewProjectFeeList.jsp");

		return mav;
	}

	/**
	 * Create a new LabConstructApp entity
	 * 
	 */
	@RequestMapping("/newProjectFeeListLabConstructApp")
	public ModelAndView newProjectFeeListLabConstructApp(@RequestParam Integer projectfeelist_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("projectfeelist_id", projectfeelist_id);
		mav.addObject("labconstructapp", new LabConstructApp());
		mav.addObject("newFlag", true);
		mav.setViewName("projectfeelist/labconstructapp/editLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Save an existing CFundAppItem entity
	 * 
	 */
	@RequestMapping("/saveProjectFeeListCFundAppItem")
	public ModelAndView saveProjectFeeListCFundAppItem(@RequestParam Integer projectfeelist_id, @ModelAttribute CFundAppItem cfundappitem) {
		ProjectFeeList parent_projectfeelist = projectFeeListService.saveProjectFeeListCFundAppItem(projectfeelist_id, cfundappitem);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectfeelist_id", projectfeelist_id);
		mav.addObject("projectfeelist", parent_projectfeelist);
		mav.setViewName("projectfeelist/viewProjectFeeList.jsp");

		return mav;
	}

	/**
	 * Delete an existing ProjectFeeList entity
	 * 
	 */
	@RequestMapping("/deleteProjectFeeList")
	public String deleteProjectFeeList(@RequestParam Integer idKey) {
		ProjectFeeList projectfeelist = projectFeeListDAO.findProjectFeeListByPrimaryKey(idKey);
		projectFeeListService.deleteProjectFeeList(projectfeelist);
		return "forward:/indexProjectFeeList";
	}

	/**
	 * Select the child LabConstructApp entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectFeeListLabConstructApp")
	public ModelAndView confirmDeleteProjectFeeListLabConstructApp(@RequestParam Integer projectfeelist_id, @RequestParam Integer related_labconstructapp_id) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructapp", labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp_id));
		mav.addObject("projectfeelist_id", projectfeelist_id);
		mav.setViewName("projectfeelist/labconstructapp/deleteLabConstructApp.jsp");

		return mav;
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
}