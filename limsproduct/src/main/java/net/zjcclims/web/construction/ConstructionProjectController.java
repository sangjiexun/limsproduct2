package net.zjcclims.web.construction;


import net.zjcclims.dao.CProjectStatusDAO;
import net.zjcclims.dao.ConstructionProjectDAO;
import net.zjcclims.dao.LabConstructAppDAO;
import net.zjcclims.domain.CProjectStatus;
import net.zjcclims.domain.ConstructionProject;
import net.zjcclims.domain.LabConstructApp;
import net.zjcclims.service.construction.ConstructionProjectService;
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
 * Spring MVC controller that handles CRUD requests for ConstructionProject entities
 * 
 */

@Controller("ConstructionProjectController")
public class ConstructionProjectController {

	/**
	 * DAO injected by Spring that manages CProjectStatus entities
	 * 
	 */
	@Autowired
	private CProjectStatusDAO cProjectStatusDAO;

	/**
	 * DAO injected by Spring that manages ConstructionProject entities
	 * 
	 */
	@Autowired
	private ConstructionProjectDAO constructionProjectDAO;

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * Service injected by Spring that provides CRUD operations for ConstructionProject entities
	 * 
	 */
	@Autowired
	private ConstructionProjectService constructionProjectService;

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/deleteConstructionProjectLabConstructApp")
	public ModelAndView deleteConstructionProjectLabConstructApp(@RequestParam Integer constructionproject_id, @RequestParam Integer related_labconstructapp_id) {
		ModelAndView mav = new ModelAndView();

		ConstructionProject constructionproject = constructionProjectService.deleteConstructionProjectLabConstructApp(constructionproject_id, related_labconstructapp_id);

		mav.addObject("constructionproject_id", constructionproject_id);
		mav.addObject("constructionproject", constructionproject);
		mav.setViewName("constructionproject/viewConstructionProject.jsp");

		return mav;
	}

	/**
	 * Create a new ConstructionProject entity
	 * 
	 */
	@RequestMapping("/newConstructionProject")
	public ModelAndView newConstructionProject() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("constructionproject", new ConstructionProject());
		mav.addObject("newFlag", true);
		mav.setViewName("constructionproject/editConstructionProject.jsp");

		return mav;
	}

	/**
	 * Save an existing CProjectStatus entity
	 * 
	 */
	@RequestMapping("/saveConstructionProjectCProjectStatus")
	public ModelAndView saveConstructionProjectCProjectStatus(@RequestParam Integer constructionproject_id, @ModelAttribute CProjectStatus cprojectstatus) {
		ConstructionProject parent_constructionproject = constructionProjectService.saveConstructionProjectCProjectStatus(constructionproject_id, cprojectstatus);

		ModelAndView mav = new ModelAndView();
		mav.addObject("constructionproject_id", constructionproject_id);
		mav.addObject("constructionproject", parent_constructionproject);
		mav.setViewName("constructionproject/viewConstructionProject.jsp");

		return mav;
	}

	/**
	 * Select the ConstructionProject entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteConstructionProject")
	public ModelAndView confirmDeleteConstructionProject(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("constructionproject", constructionProjectDAO.findConstructionProjectByPrimaryKey(idKey));
		mav.setViewName("constructionproject/deleteConstructionProject.jsp");

		return mav;
	}

	/**
	 * Select an existing ConstructionProject entity
	 * 
	 */
	@RequestMapping("/selectConstructionProject")
	public ModelAndView selectConstructionProject(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("constructionproject", constructionProjectDAO.findConstructionProjectByPrimaryKey(idKey));
		mav.setViewName("constructionproject/viewConstructionProject.jsp");

		return mav;
	}

	/**
	 * Delete an existing ConstructionProject entity
	 * 
	 */
	@RequestMapping("/deleteConstructionProject")
	public String deleteConstructionProject(@RequestParam Integer idKey) {
		ConstructionProject constructionproject = constructionProjectDAO.findConstructionProjectByPrimaryKey(idKey);
		constructionProjectService.deleteConstructionProject(constructionproject);
		return "forward:/indexConstructionProject";
	}

	/**
	 * View an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/selectConstructionProjectLabConstructApp")
	public ModelAndView selectConstructionProjectLabConstructApp(@RequestParam Integer constructionproject_id, @RequestParam Integer labconstructapp_id) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("constructionproject_id", constructionproject_id);
		mav.addObject("labconstructapp", labconstructapp);
		mav.setViewName("constructionproject/labconstructapp/viewLabConstructApp.jsp");

		return mav;
	}

	/**
	 * View an existing CProjectStatus entity
	 * 
	 */
	@RequestMapping("/selectConstructionProjectCProjectStatus")
	public ModelAndView selectConstructionProjectCProjectStatus(@RequestParam Integer constructionproject_id, @RequestParam Integer cprojectstatus_id) {
		CProjectStatus cprojectstatus = cProjectStatusDAO.findCProjectStatusByPrimaryKey(cprojectstatus_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("constructionproject_id", constructionproject_id);
		mav.addObject("cprojectstatus", cprojectstatus);
		mav.setViewName("constructionproject/cprojectstatus/viewCProjectStatus.jsp");

		return mav;
	}

	/**
	 * Show all ConstructionProject entities
	 * 
	 */
	@RequestMapping("/indexConstructionProject")
	public ModelAndView listConstructionProjects() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("constructionprojects", constructionProjectService.loadConstructionProjects());

		mav.setViewName("constructionproject/listConstructionProjects.jsp");

		return mav;
	}

	/**
	 * Select the child CProjectStatus entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteConstructionProjectCProjectStatus")
	public ModelAndView confirmDeleteConstructionProjectCProjectStatus(@RequestParam Integer constructionproject_id, @RequestParam Integer related_cprojectstatus_id) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("cprojectstatus", cProjectStatusDAO.findCProjectStatusByPrimaryKey(related_cprojectstatus_id));
		mav.addObject("constructionproject_id", constructionproject_id);
		mav.setViewName("constructionproject/cprojectstatus/deleteCProjectStatus.jsp");

		return mav;
	}

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/saveConstructionProjectLabConstructApp")
	public ModelAndView saveConstructionProjectLabConstructApp(@RequestParam Integer constructionproject_id, @ModelAttribute LabConstructApp labconstructapp) {
		ConstructionProject parent_constructionproject = constructionProjectService.saveConstructionProjectLabConstructApp(constructionproject_id, labconstructapp);

		ModelAndView mav = new ModelAndView();
		mav.addObject("constructionproject_id", constructionproject_id);
		mav.addObject("constructionproject", parent_constructionproject);
		mav.setViewName("constructionproject/viewConstructionProject.jsp");

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
	 * Edit an existing ConstructionProject entity
	 * 
	 */
	@RequestMapping("/editConstructionProject")
	public ModelAndView editConstructionProject(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("constructionproject", constructionProjectDAO.findConstructionProjectByPrimaryKey(idKey));
		mav.setViewName("constructionproject/editConstructionProject.jsp");

		return mav;
	}

	/**
	 * Save an existing ConstructionProject entity
	 * 
	 */
	@RequestMapping("/saveConstructionProject")
	public String saveConstructionProject(@ModelAttribute ConstructionProject constructionproject) {
		constructionProjectService.saveConstructionProject(constructionproject);
		return "forward:/indexConstructionProject";
	}

	/**
	 * Select the child LabConstructApp entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteConstructionProjectLabConstructApp")
	public ModelAndView confirmDeleteConstructionProjectLabConstructApp(@RequestParam Integer constructionproject_id, @RequestParam Integer related_labconstructapp_id) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructapp", labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp_id));
		mav.addObject("constructionproject_id", constructionproject_id);
		mav.setViewName("constructionproject/labconstructapp/deleteLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Create a new LabConstructApp entity
	 * 
	 */
	@RequestMapping("/newConstructionProjectLabConstructApp")
	public ModelAndView newConstructionProjectLabConstructApp(@RequestParam Integer constructionproject_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("constructionproject_id", constructionproject_id);
		mav.addObject("labconstructapp", new LabConstructApp());
		mav.addObject("newFlag", true);
		mav.setViewName("constructionproject/labconstructapp/editLabConstructApp.jsp");

		return mav;
	}

	/**
	 */
	@RequestMapping("/constructionprojectController/binary.action")
	public ModelAndView streamBinary(@ModelAttribute HttpServletRequest request, @ModelAttribute HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("streamedBinaryContentView");
		return mav;

	}

	/**
	 * Show all CProjectStatus entities by ConstructionProject
	 * 
	 */
	@RequestMapping("/listConstructionProjectCProjectStatus")
	public ModelAndView listConstructionProjectCProjectStatus(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("constructionproject", constructionProjectDAO.findConstructionProjectByPrimaryKey(idKey));
		mav.setViewName("constructionproject/cprojectstatus/listCProjectStatus.jsp");

		return mav;
	}

	/**
	 * Create a new CProjectStatus entity
	 * 
	 */
	@RequestMapping("/newConstructionProjectCProjectStatus")
	public ModelAndView newConstructionProjectCProjectStatus(@RequestParam Integer constructionproject_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("constructionproject_id", constructionproject_id);
		mav.addObject("cprojectstatus", new CProjectStatus());
		mav.addObject("newFlag", true);
		mav.setViewName("constructionproject/cprojectstatus/editCProjectStatus.jsp");

		return mav;
	}

	/**
	 * Show all LabConstructApp entities by ConstructionProject
	 * 
	 */
	@RequestMapping("/listConstructionProjectLabConstructApp")
	public ModelAndView listConstructionProjectLabConstructApp(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("constructionproject", constructionProjectDAO.findConstructionProjectByPrimaryKey(idKey));
		mav.setViewName("constructionproject/labconstructapp/listLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Delete an existing CProjectStatus entity
	 * 
	 */
	@RequestMapping("/deleteConstructionProjectCProjectStatus")
	public ModelAndView deleteConstructionProjectCProjectStatus(@RequestParam Integer constructionproject_id, @RequestParam Integer related_cprojectstatus_id) {
		ModelAndView mav = new ModelAndView();

		ConstructionProject constructionproject = constructionProjectService.deleteConstructionProjectCProjectStatus(constructionproject_id, related_cprojectstatus_id);

		mav.addObject("constructionproject_id", constructionproject_id);
		mav.addObject("constructionproject", constructionproject);
		mav.setViewName("constructionproject/viewConstructionProject.jsp");

		return mav;
	}

	/**
	 * Entry point to show all ConstructionProject entities
	 * 
	 */
	public String indexConstructionProject() {
		return "redirect:/indexConstructionProject";
	}

	/**
	 * Edit an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/editConstructionProjectLabConstructApp")
	public ModelAndView editConstructionProjectLabConstructApp(@RequestParam Integer constructionproject_id, @RequestParam Integer labconstructapp_id) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("constructionproject_id", constructionproject_id);
		mav.addObject("labconstructapp", labconstructapp);
		mav.setViewName("constructionproject/labconstructapp/editLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Edit an existing CProjectStatus entity
	 * 
	 */
	@RequestMapping("/editConstructionProjectCProjectStatus")
	public ModelAndView editConstructionProjectCProjectStatus(@RequestParam Integer constructionproject_id, @RequestParam Integer cprojectstatus_id) {
		CProjectStatus cprojectstatus = cProjectStatusDAO.findCProjectStatusByPrimaryKey(cprojectstatus_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("constructionproject_id", constructionproject_id);
		mav.addObject("cprojectstatus", cprojectstatus);
		mav.setViewName("constructionproject/cprojectstatus/editCProjectStatus.jsp");

		return mav;
	}
}