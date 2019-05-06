package net.zjcclims.web.construction;


import net.zjcclims.dao.CProjectPurposeDAO;
import net.zjcclims.dao.LabConstructAppDAO;
import net.zjcclims.dao.ProjectPurposeDAO;
import net.zjcclims.domain.CProjectPurpose;
import net.zjcclims.domain.LabConstructApp;
import net.zjcclims.domain.ProjectPurpose;
import net.zjcclims.service.construction.ProjectPurposeService;
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
 * Spring MVC controller that handles CRUD requests for ProjectPurpose entities
 * 
 */

@Controller("ProjectPurposeController")
public class ProjectPurposeController {

	/**
	 * DAO injected by Spring that manages CProjectPurpose entities
	 * 
	 */
	@Autowired
	private CProjectPurposeDAO cProjectPurposeDAO;

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * DAO injected by Spring that manages ProjectPurpose entities
	 * 
	 */
	@Autowired
	private ProjectPurposeDAO projectPurposeDAO;

	/**
	 * Service injected by Spring that provides CRUD operations for ProjectPurpose entities
	 * 
	 */
	@Autowired
	private ProjectPurposeService projectPurposeService;

	/**
	 * Create a new ProjectPurpose entity
	 * 
	 */
	@RequestMapping("/newProjectPurpose")
	public ModelAndView newProjectPurpose() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectpurpose", new ProjectPurpose());
		mav.addObject("newFlag", true);
		mav.setViewName("projectpurpose/editProjectPurpose.jsp");

		return mav;
	}

	/**
	 * Edit an existing CProjectPurpose entity
	 * 
	 */
	@RequestMapping("/editProjectPurposeCProjectPurpose")
	public ModelAndView editProjectPurposeCProjectPurpose(@RequestParam Integer projectpurpose_id, @RequestParam Integer cprojectpurpose_id) {
		CProjectPurpose cprojectpurpose = cProjectPurposeDAO.findCProjectPurposeByPrimaryKey(cprojectpurpose_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectpurpose_id", projectpurpose_id);
		mav.addObject("cprojectpurpose", cprojectpurpose);
		mav.setViewName("projectpurpose/cprojectpurpose/editCProjectPurpose.jsp");

		return mav;
	}

	/**
	 * Show all CProjectPurpose entities by ProjectPurpose
	 * 
	 */
	@RequestMapping("/listProjectPurposeCProjectPurpose")
	public ModelAndView listProjectPurposeCProjectPurpose(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectpurpose", projectPurposeDAO.findProjectPurposeByPrimaryKey(idKey));
		mav.setViewName("projectpurpose/cprojectpurpose/listCProjectPurpose.jsp");

		return mav;
	}

	/**
	 * Save an existing ProjectPurpose entity
	 * 
	 */
	@RequestMapping("/saveProjectPurpose")
	public String saveProjectPurpose(@ModelAttribute ProjectPurpose projectpurpose) {
		projectPurposeService.saveProjectPurpose(projectpurpose);
		return "forward:/indexProjectPurpose";
	}

	/**
	 * Save an existing CProjectPurpose entity
	 * 
	 */
	@RequestMapping("/saveProjectPurposeCProjectPurpose")
	public ModelAndView saveProjectPurposeCProjectPurpose(@RequestParam Integer projectpurpose_id, @ModelAttribute CProjectPurpose cprojectpurpose) {
		ProjectPurpose parent_projectpurpose = projectPurposeService.saveProjectPurposeCProjectPurpose(projectpurpose_id, cprojectpurpose);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectpurpose_id", projectpurpose_id);
		mav.addObject("projectpurpose", parent_projectpurpose);
		mav.setViewName("projectpurpose/viewProjectPurpose.jsp");

		return mav;
	}

	/**
	 * View an existing CProjectPurpose entity
	 * 
	 */
	@RequestMapping("/selectProjectPurposeCProjectPurpose")
	public ModelAndView selectProjectPurposeCProjectPurpose(@RequestParam Integer projectpurpose_id, @RequestParam Integer cprojectpurpose_id) {
		CProjectPurpose cprojectpurpose = cProjectPurposeDAO.findCProjectPurposeByPrimaryKey(cprojectpurpose_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectpurpose_id", projectpurpose_id);
		mav.addObject("cprojectpurpose", cprojectpurpose);
		mav.setViewName("projectpurpose/cprojectpurpose/viewCProjectPurpose.jsp");

		return mav;
	}

	/**
	 * View an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/selectProjectPurposeLabConstructApp")
	public ModelAndView selectProjectPurposeLabConstructApp(@RequestParam Integer projectpurpose_id, @RequestParam Integer labconstructapp_id) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectpurpose_id", projectpurpose_id);
		mav.addObject("labconstructapp", labconstructapp);
		mav.setViewName("projectpurpose/labconstructapp/viewLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Show all LabConstructApp entities by ProjectPurpose
	 * 
	 */
	@RequestMapping("/listProjectPurposeLabConstructApp")
	public ModelAndView listProjectPurposeLabConstructApp(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectpurpose", projectPurposeDAO.findProjectPurposeByPrimaryKey(idKey));
		mav.setViewName("projectpurpose/labconstructapp/listLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Delete an existing CProjectPurpose entity
	 * 
	 */
	@RequestMapping("/deleteProjectPurposeCProjectPurpose")
	public ModelAndView deleteProjectPurposeCProjectPurpose(@RequestParam Integer projectpurpose_id, @RequestParam Integer related_cprojectpurpose_id) {
		ModelAndView mav = new ModelAndView();

		ProjectPurpose projectpurpose = projectPurposeService.deleteProjectPurposeCProjectPurpose(projectpurpose_id, related_cprojectpurpose_id);

		mav.addObject("projectpurpose_id", projectpurpose_id);
		mav.addObject("projectpurpose", projectpurpose);
		mav.setViewName("projectpurpose/viewProjectPurpose.jsp");

		return mav;
	}

	/**
	 * Edit an existing ProjectPurpose entity
	 * 
	 */
	@RequestMapping("/editProjectPurpose")
	public ModelAndView editProjectPurpose(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectpurpose", projectPurposeDAO.findProjectPurposeByPrimaryKey(idKey));
		mav.setViewName("projectpurpose/editProjectPurpose.jsp");

		return mav;
	}

	/**
	 * Delete an existing ProjectPurpose entity
	 * 
	 */
	@RequestMapping("/deleteProjectPurpose")
	public String deleteProjectPurpose(@RequestParam Integer idKey) {
		ProjectPurpose projectpurpose = projectPurposeDAO.findProjectPurposeByPrimaryKey(idKey);
		projectPurposeService.deleteProjectPurpose(projectpurpose);
		return "forward:/indexProjectPurpose";
	}

	/**
	 * Create a new LabConstructApp entity
	 * 
	 */
	@RequestMapping("/newProjectPurposeLabConstructApp")
	public ModelAndView newProjectPurposeLabConstructApp(@RequestParam Integer projectpurpose_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("projectpurpose_id", projectpurpose_id);
		mav.addObject("labconstructapp", new LabConstructApp());
		mav.addObject("newFlag", true);
		mav.setViewName("projectpurpose/labconstructapp/editLabConstructApp.jsp");

		return mav;
	}

	/**
	 */
	@RequestMapping("/projectpurposeController/binary.action")
	public ModelAndView streamBinary(@ModelAttribute HttpServletRequest request, @ModelAttribute HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("streamedBinaryContentView");
		return mav;

	}

	/**
	 * Show all ProjectPurpose entities
	 * 
	 */
	@RequestMapping("/indexProjectPurpose")
	public ModelAndView listProjectPurposes() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectpurposes", projectPurposeService.loadProjectPurposes());

		mav.setViewName("projectpurpose/listProjectPurposes.jsp");

		return mav;
	}

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/deleteProjectPurposeLabConstructApp")
	public ModelAndView deleteProjectPurposeLabConstructApp(@RequestParam Integer projectpurpose_id, @RequestParam Integer related_labconstructapp_id) {
		ModelAndView mav = new ModelAndView();

		ProjectPurpose projectpurpose = projectPurposeService.deleteProjectPurposeLabConstructApp(projectpurpose_id, related_labconstructapp_id);

		mav.addObject("projectpurpose_id", projectpurpose_id);
		mav.addObject("projectpurpose", projectpurpose);
		mav.setViewName("projectpurpose/viewProjectPurpose.jsp");

		return mav;
	}

	/**
	 * Select the child LabConstructApp entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectPurposeLabConstructApp")
	public ModelAndView confirmDeleteProjectPurposeLabConstructApp(@RequestParam Integer projectpurpose_id, @RequestParam Integer related_labconstructapp_id) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructapp", labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp_id));
		mav.addObject("projectpurpose_id", projectpurpose_id);
		mav.setViewName("projectpurpose/labconstructapp/deleteLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Select an existing ProjectPurpose entity
	 * 
	 */
	@RequestMapping("/selectProjectPurpose")
	public ModelAndView selectProjectPurpose(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectpurpose", projectPurposeDAO.findProjectPurposeByPrimaryKey(idKey));
		mav.setViewName("projectpurpose/viewProjectPurpose.jsp");

		return mav;
	}

	/**
	 * Edit an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/editProjectPurposeLabConstructApp")
	public ModelAndView editProjectPurposeLabConstructApp(@RequestParam Integer projectpurpose_id, @RequestParam Integer labconstructapp_id) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectpurpose_id", projectpurpose_id);
		mav.addObject("labconstructapp", labconstructapp);
		mav.setViewName("projectpurpose/labconstructapp/editLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/saveProjectPurposeLabConstructApp")
	public ModelAndView saveProjectPurposeLabConstructApp(@RequestParam Integer projectpurpose_id, @ModelAttribute LabConstructApp labconstructapp) {
		ProjectPurpose parent_projectpurpose = projectPurposeService.saveProjectPurposeLabConstructApp(projectpurpose_id, labconstructapp);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectpurpose_id", projectpurpose_id);
		mav.addObject("projectpurpose", parent_projectpurpose);
		mav.setViewName("projectpurpose/viewProjectPurpose.jsp");

		return mav;
	}

	/**
	 * Select the ProjectPurpose entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectPurpose")
	public ModelAndView confirmDeleteProjectPurpose(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectpurpose", projectPurposeDAO.findProjectPurposeByPrimaryKey(idKey));
		mav.setViewName("projectpurpose/deleteProjectPurpose.jsp");

		return mav;
	}

	/**
	 * Create a new CProjectPurpose entity
	 * 
	 */
	@RequestMapping("/newProjectPurposeCProjectPurpose")
	public ModelAndView newProjectPurposeCProjectPurpose(@RequestParam Integer projectpurpose_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("projectpurpose_id", projectpurpose_id);
		mav.addObject("cprojectpurpose", new CProjectPurpose());
		mav.addObject("newFlag", true);
		mav.setViewName("projectpurpose/cprojectpurpose/editCProjectPurpose.jsp");

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
	 * Select the child CProjectPurpose entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectPurposeCProjectPurpose")
	public ModelAndView confirmDeleteProjectPurposeCProjectPurpose(@RequestParam Integer projectpurpose_id, @RequestParam Integer related_cprojectpurpose_id) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("cprojectpurpose", cProjectPurposeDAO.findCProjectPurposeByPrimaryKey(related_cprojectpurpose_id));
		mav.addObject("projectpurpose_id", projectpurpose_id);
		mav.setViewName("projectpurpose/cprojectpurpose/deleteCProjectPurpose.jsp");

		return mav;
	}

	/**
	 * Entry point to show all ProjectPurpose entities
	 * 
	 */
	public String indexProjectPurpose() {
		return "redirect:/indexProjectPurpose";
	}
}