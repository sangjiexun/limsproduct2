package net.zjcclims.web.construction;


import net.zjcclims.dao.LabConstructAppDAO;
import net.zjcclims.dao.ProjectAppMajorDAO;
import net.zjcclims.dao.SchoolMajorDAO;
import net.zjcclims.domain.LabConstructApp;
import net.zjcclims.domain.ProjectAppMajor;
import net.zjcclims.domain.SchoolMajor;
import net.zjcclims.service.construction.ProjectAppMajorService;
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
 * Spring MVC controller that handles CRUD requests for ProjectAppMajor entities
 * 
 */

@Controller("ProjectAppMajorController")
public class ProjectAppMajorController {

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * DAO injected by Spring that manages ProjectAppMajor entities
	 * 
	 */
	@Autowired
	private ProjectAppMajorDAO projectAppMajorDAO;

	/**
	 * DAO injected by Spring that manages SchoolMajor entities
	 * 
	 */
	@Autowired
	private SchoolMajorDAO schoolMajorDAO;

	/**
	 * Service injected by Spring that provides CRUD operations for ProjectAppMajor entities
	 * 
	 */
	@Autowired
	private ProjectAppMajorService projectAppMajorService;

	/**
	 */
	@RequestMapping("/projectappmajorController/binary.action")
	public ModelAndView streamBinary(@ModelAttribute HttpServletRequest request, @ModelAttribute HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("streamedBinaryContentView");
		return mav;

	}

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/saveProjectAppMajorLabConstructApp")
	public ModelAndView saveProjectAppMajorLabConstructApp(@RequestParam Integer projectappmajor_id, @ModelAttribute LabConstructApp labconstructapp) {
		ProjectAppMajor parent_projectappmajor = projectAppMajorService.saveProjectAppMajorLabConstructApp(projectappmajor_id, labconstructapp);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectappmajor_id", projectappmajor_id);
		mav.addObject("projectappmajor", parent_projectappmajor);
		mav.setViewName("projectappmajor/viewProjectAppMajor.jsp");

		return mav;
	}

	/**
	 * Delete an existing SchoolMajor entity
	 * 
	 */
	@RequestMapping("/deleteProjectAppMajorSchoolMajor")
	public ModelAndView deleteProjectAppMajorSchoolMajor(@RequestParam Integer projectappmajor_id, @RequestParam String related_schoolmajor_majorNumber) {
		ModelAndView mav = new ModelAndView();

		ProjectAppMajor projectappmajor = projectAppMajorService.deleteProjectAppMajorSchoolMajor(projectappmajor_id, related_schoolmajor_majorNumber);

		mav.addObject("projectappmajor_id", projectappmajor_id);
		mav.addObject("projectappmajor", projectappmajor);
		mav.setViewName("projectappmajor/viewProjectAppMajor.jsp");

		return mav;
	}

	/**
	 * Edit an existing ProjectAppMajor entity
	 * 
	 */
	@RequestMapping("/editProjectAppMajor")
	public ModelAndView editProjectAppMajor(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectappmajor", projectAppMajorDAO.findProjectAppMajorByPrimaryKey(idKey));
		mav.setViewName("projectappmajor/editProjectAppMajor.jsp");

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
	 * Create a new LabConstructApp entity
	 * 
	 */
	@RequestMapping("/newProjectAppMajorLabConstructApp")
	public ModelAndView newProjectAppMajorLabConstructApp(@RequestParam Integer projectappmajor_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("projectappmajor_id", projectappmajor_id);
		mav.addObject("labconstructapp", new LabConstructApp());
		mav.addObject("newFlag", true);
		mav.setViewName("projectappmajor/labconstructapp/editLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Select an existing ProjectAppMajor entity
	 * 
	 */
	@RequestMapping("/selectProjectAppMajor")
	public ModelAndView selectProjectAppMajor(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectappmajor", projectAppMajorDAO.findProjectAppMajorByPrimaryKey(idKey));
		mav.setViewName("projectappmajor/viewProjectAppMajor.jsp");

		return mav;
	}

	/**
	 * Create a new SchoolMajor entity
	 * 
	 */
	@RequestMapping("/newProjectAppMajorSchoolMajor")
	public ModelAndView newProjectAppMajorSchoolMajor(@RequestParam Integer projectappmajor_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("projectappmajor_id", projectappmajor_id);
		mav.addObject("schoolmajor", new SchoolMajor());
		mav.addObject("newFlag", true);
		mav.setViewName("projectappmajor/schoolmajor/editSchoolMajor.jsp");

		return mav;
	}

	/**
	 * Select the child SchoolMajor entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectAppMajorSchoolMajor")
	public ModelAndView confirmDeleteProjectAppMajorSchoolMajor(@RequestParam Integer projectappmajor_id, @RequestParam String related_schoolmajor_majorNumber) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("schoolmajor", schoolMajorDAO.findSchoolMajorByPrimaryKey(related_schoolmajor_majorNumber));
		mav.addObject("projectappmajor_id", projectappmajor_id);
		mav.setViewName("projectappmajor/schoolmajor/deleteSchoolMajor.jsp");

		return mav;
	}

	/**
	 * Select the ProjectAppMajor entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectAppMajor")
	public ModelAndView confirmDeleteProjectAppMajor(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectappmajor", projectAppMajorDAO.findProjectAppMajorByPrimaryKey(idKey));
		mav.setViewName("projectappmajor/deleteProjectAppMajor.jsp");

		return mav;
	}

	/**
	 * Show all SchoolMajor entities by ProjectAppMajor
	 * 
	 */
	@RequestMapping("/listProjectAppMajorSchoolMajor")
	public ModelAndView listProjectAppMajorSchoolMajor(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectappmajor", projectAppMajorDAO.findProjectAppMajorByPrimaryKey(idKey));
		mav.setViewName("projectappmajor/schoolmajor/listSchoolMajor.jsp");

		return mav;
	}

	/**
	 * Edit an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/editProjectAppMajorLabConstructApp")
	public ModelAndView editProjectAppMajorLabConstructApp(@RequestParam Integer projectappmajor_id, @RequestParam Integer labconstructapp_id) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectappmajor_id", projectappmajor_id);
		mav.addObject("labconstructapp", labconstructapp);
		mav.setViewName("projectappmajor/labconstructapp/editLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Create a new ProjectAppMajor entity
	 * 
	 */
	@RequestMapping("/newProjectAppMajor")
	public ModelAndView newProjectAppMajor() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectappmajor", new ProjectAppMajor());
		mav.addObject("newFlag", true);
		mav.setViewName("projectappmajor/editProjectAppMajor.jsp");

		return mav;
	}

	/**
	 * Edit an existing SchoolMajor entity
	 * 
	 */
	@RequestMapping("/editProjectAppMajorSchoolMajor")
	public ModelAndView editProjectAppMajorSchoolMajor(@RequestParam Integer projectappmajor_id, @RequestParam String schoolmajor_majorNumber) {
		SchoolMajor schoolmajor = schoolMajorDAO.findSchoolMajorByPrimaryKey(schoolmajor_majorNumber, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectappmajor_id", projectappmajor_id);
		mav.addObject("schoolmajor", schoolmajor);
		mav.setViewName("projectappmajor/schoolmajor/editSchoolMajor.jsp");

		return mav;
	}

	/**
	 * Show all ProjectAppMajor entities
	 * 
	 */
	@RequestMapping("/indexProjectAppMajor")
	public ModelAndView listProjectAppMajors() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectappmajors", projectAppMajorService.loadProjectAppMajors());

		mav.setViewName("projectappmajor/listProjectAppMajors.jsp");

		return mav;
	}

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/deleteProjectAppMajorLabConstructApp")
	public ModelAndView deleteProjectAppMajorLabConstructApp(@RequestParam Integer projectappmajor_id, @RequestParam Integer related_labconstructapp_id) {
		ModelAndView mav = new ModelAndView();

		ProjectAppMajor projectappmajor = projectAppMajorService.deleteProjectAppMajorLabConstructApp(projectappmajor_id, related_labconstructapp_id);

		mav.addObject("projectappmajor_id", projectappmajor_id);
		mav.addObject("projectappmajor", projectappmajor);
		mav.setViewName("projectappmajor/viewProjectAppMajor.jsp");

		return mav;
	}

	/**
	 * Save an existing ProjectAppMajor entity
	 * 
	 */
	@RequestMapping("/saveProjectAppMajor")
	public String saveProjectAppMajor(@ModelAttribute ProjectAppMajor projectappmajor) {
		projectAppMajorService.saveProjectAppMajor(projectappmajor);
		return "forward:/indexProjectAppMajor";
	}

	/**
	 * Show all LabConstructApp entities by ProjectAppMajor
	 * 
	 */
	@RequestMapping("/listProjectAppMajorLabConstructApp")
	public ModelAndView listProjectAppMajorLabConstructApp(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectappmajor", projectAppMajorDAO.findProjectAppMajorByPrimaryKey(idKey));
		mav.setViewName("projectappmajor/labconstructapp/listLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Delete an existing ProjectAppMajor entity
	 * 
	 */
	@RequestMapping("/deleteProjectAppMajor")
	public String deleteProjectAppMajor(@RequestParam Integer idKey) {
		ProjectAppMajor projectappmajor = projectAppMajorDAO.findProjectAppMajorByPrimaryKey(idKey);
		projectAppMajorService.deleteProjectAppMajor(projectappmajor);
		return "forward:/indexProjectAppMajor";
	}

	/**
	 * Select the child LabConstructApp entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectAppMajorLabConstructApp")
	public ModelAndView confirmDeleteProjectAppMajorLabConstructApp(@RequestParam Integer projectappmajor_id, @RequestParam Integer related_labconstructapp_id) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructapp", labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp_id));
		mav.addObject("projectappmajor_id", projectappmajor_id);
		mav.setViewName("projectappmajor/labconstructapp/deleteLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Save an existing SchoolMajor entity
	 * 
	 */
	@RequestMapping("/saveProjectAppMajorSchoolMajor")
	public ModelAndView saveProjectAppMajorSchoolMajor(@RequestParam Integer projectappmajor_id, @ModelAttribute SchoolMajor schoolmajor) {
		ProjectAppMajor parent_projectappmajor = projectAppMajorService.saveProjectAppMajorSchoolMajor(projectappmajor_id, schoolmajor);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectappmajor_id", projectappmajor_id);
		mav.addObject("projectappmajor", parent_projectappmajor);
		mav.setViewName("projectappmajor/viewProjectAppMajor.jsp");

		return mav;
	}

	/**
	 * Entry point to show all ProjectAppMajor entities
	 * 
	 */
	public String indexProjectAppMajor() {
		return "redirect:/indexProjectAppMajor";
	}

	/**
	 * View an existing SchoolMajor entity
	 * 
	 */
	@RequestMapping("/selectProjectAppMajorSchoolMajor")
	public ModelAndView selectProjectAppMajorSchoolMajor(@RequestParam Integer projectappmajor_id, @RequestParam String schoolmajor_majorNumber) {
		SchoolMajor schoolmajor = schoolMajorDAO.findSchoolMajorByPrimaryKey(schoolmajor_majorNumber, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectappmajor_id", projectappmajor_id);
		mav.addObject("schoolmajor", schoolmajor);
		mav.setViewName("projectappmajor/schoolmajor/viewSchoolMajor.jsp");

		return mav;
	}

	/**
	 * View an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/selectProjectAppMajorLabConstructApp")
	public ModelAndView selectProjectAppMajorLabConstructApp(@RequestParam Integer projectappmajor_id, @RequestParam Integer labconstructapp_id) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectappmajor_id", projectappmajor_id);
		mav.addObject("labconstructapp", labconstructapp);
		mav.setViewName("projectappmajor/labconstructapp/viewLabConstructApp.jsp");

		return mav;
	}
}