package net.zjcclims.web.construction;


import net.zjcclims.dao.CProjectBudgetDAO;
import net.zjcclims.dao.ProjectBudgetDAO;
import net.zjcclims.domain.CProjectBudget;
import net.zjcclims.domain.ProjectBudget;
import net.zjcclims.service.construction.ProjectBudgetService;
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
 * Spring MVC controller that handles CRUD requests for ProjectBudget entities
 * 
 */

@Controller("ProjectBudgetController")
public class ProjectBudgetController {

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
	 * Service injected by Spring that provides CRUD operations for ProjectBudget entities
	 * 
	 */
	@Autowired
	private ProjectBudgetService projectBudgetService;

	/**
	 * Create a new CProjectBudget entity
	 * 
	 */
	@RequestMapping("/newProjectBudgetCProjectBudget")
	public ModelAndView newProjectBudgetCProjectBudget(@RequestParam Integer projectbudget_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("projectbudget_id", projectbudget_id);
		mav.addObject("cprojectbudget", new CProjectBudget());
		mav.addObject("newFlag", true);
		mav.setViewName("projectbudget/cprojectbudget/editCProjectBudget.jsp");

		return mav;
	}

	/**
	 * View an existing CProjectBudget entity
	 * 
	 */
	@RequestMapping("/selectProjectBudgetCProjectBudget")
	public ModelAndView selectProjectBudgetCProjectBudget(@RequestParam Integer projectbudget_id, @RequestParam Integer cprojectbudget_id) {
		CProjectBudget cprojectbudget = cProjectBudgetDAO.findCProjectBudgetByPrimaryKey(cprojectbudget_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectbudget_id", projectbudget_id);
		mav.addObject("cprojectbudget", cprojectbudget);
		mav.setViewName("projectbudget/cprojectbudget/viewCProjectBudget.jsp");

		return mav;
	}

	/**
	 * Entry point to show all ProjectBudget entities
	 * 
	 */
	public String indexProjectBudget() {
		return "redirect:/indexProjectBudget";
	}

	/**
	 * Select an existing ProjectBudget entity
	 * 
	 */
	@RequestMapping("/selectProjectBudget")
	public ModelAndView selectProjectBudget(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectbudget", projectBudgetDAO.findProjectBudgetByPrimaryKey(idKey));
		mav.setViewName("projectbudget/viewProjectBudget.jsp");

		return mav;
	}

	/**
	 * Create a new ProjectBudget entity
	 * 
	 */
	@RequestMapping("/newProjectBudget")
	public ModelAndView newProjectBudget() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectbudget", new ProjectBudget());
		mav.addObject("newFlag", true);
		mav.setViewName("projectbudget/editProjectBudget.jsp");

		return mav;
	}

	/**
	 * Save an existing ProjectBudget entity
	 * 
	 */
	@RequestMapping("/saveProjectBudget")
	public String saveProjectBudget(@ModelAttribute ProjectBudget projectbudget) {
		projectBudgetService.saveProjectBudget(projectbudget);
		return "forward:/indexProjectBudget";
	}

	/**
	 * Show all ProjectBudget entities
	 * 
	 */
	@RequestMapping("/indexProjectBudget")
	public ModelAndView listProjectBudgets() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectbudgets", projectBudgetService.loadProjectBudgets());

		mav.setViewName("projectbudget/listProjectBudgets.jsp");

		return mav;
	}

	/**
	 * Select the child CProjectBudget entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectBudgetCProjectBudget")
	public ModelAndView confirmDeleteProjectBudgetCProjectBudget(@RequestParam Integer projectbudget_id, @RequestParam Integer related_cprojectbudget_id) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("cprojectbudget", cProjectBudgetDAO.findCProjectBudgetByPrimaryKey(related_cprojectbudget_id));
		mav.addObject("projectbudget_id", projectbudget_id);
		mav.setViewName("projectbudget/cprojectbudget/deleteCProjectBudget.jsp");

		return mav;
	}

	/**
	 * Show all CProjectBudget entities by ProjectBudget
	 * 
	 */
	@RequestMapping("/listProjectBudgetCProjectBudget")
	public ModelAndView listProjectBudgetCProjectBudget(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectbudget", projectBudgetDAO.findProjectBudgetByPrimaryKey(idKey));
		mav.setViewName("projectbudget/cprojectbudget/listCProjectBudget.jsp");

		return mav;
	}

	/**
	 * Delete an existing CProjectBudget entity
	 * 
	 */
	@RequestMapping("/deleteProjectBudgetCProjectBudget")
	public ModelAndView deleteProjectBudgetCProjectBudget(@RequestParam Integer projectbudget_id, @RequestParam Integer related_cprojectbudget_id) {
		ModelAndView mav = new ModelAndView();

		ProjectBudget projectbudget = projectBudgetService.deleteProjectBudgetCProjectBudget(projectbudget_id, related_cprojectbudget_id);

		mav.addObject("projectbudget_id", projectbudget_id);
		mav.addObject("projectbudget", projectbudget);
		mav.setViewName("projectbudget/viewProjectBudget.jsp");

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

	/**
	 * Edit an existing ProjectBudget entity
	 * 
	 */
	@RequestMapping("/editProjectBudget")
	public ModelAndView editProjectBudget(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectbudget", projectBudgetDAO.findProjectBudgetByPrimaryKey(idKey));
		mav.setViewName("projectbudget/editProjectBudget.jsp");

		return mav;
	}

	/**
	 * Select the ProjectBudget entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectBudget")
	public ModelAndView confirmDeleteProjectBudget(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectbudget", projectBudgetDAO.findProjectBudgetByPrimaryKey(idKey));
		mav.setViewName("projectbudget/deleteProjectBudget.jsp");

		return mav;
	}

	/**
	 * Edit an existing CProjectBudget entity
	 * 
	 */
	@RequestMapping("/editProjectBudgetCProjectBudget")
	public ModelAndView editProjectBudgetCProjectBudget(@RequestParam Integer projectbudget_id, @RequestParam Integer cprojectbudget_id) {
		CProjectBudget cprojectbudget = cProjectBudgetDAO.findCProjectBudgetByPrimaryKey(cprojectbudget_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectbudget_id", projectbudget_id);
		mav.addObject("cprojectbudget", cprojectbudget);
		mav.setViewName("projectbudget/cprojectbudget/editCProjectBudget.jsp");

		return mav;
	}

	/**
	 * Save an existing CProjectBudget entity
	 * 
	 */
	@RequestMapping("/saveProjectBudgetCProjectBudget")
	public ModelAndView saveProjectBudgetCProjectBudget(@RequestParam Integer projectbudget_id, @ModelAttribute CProjectBudget cprojectbudget) {
		ProjectBudget parent_projectbudget = projectBudgetService.saveProjectBudgetCProjectBudget(projectbudget_id, cprojectbudget);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectbudget_id", projectbudget_id);
		mav.addObject("projectbudget", parent_projectbudget);
		mav.setViewName("projectbudget/viewProjectBudget.jsp");

		return mav;
	}

	/**
	 * Delete an existing ProjectBudget entity
	 * 
	 */
	@RequestMapping("/deleteProjectBudget")
	public String deleteProjectBudget(@RequestParam Integer idKey) {
		ProjectBudget projectbudget = projectBudgetDAO.findProjectBudgetByPrimaryKey(idKey);
		projectBudgetService.deleteProjectBudget(projectbudget);
		return "forward:/indexProjectBudget";
	}

	/**
	 */
	@RequestMapping("/projectbudgetController/binary.action")
	public ModelAndView streamBinary(@ModelAttribute HttpServletRequest request, @ModelAttribute HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("streamedBinaryContentView");
		return mav;

	}
}