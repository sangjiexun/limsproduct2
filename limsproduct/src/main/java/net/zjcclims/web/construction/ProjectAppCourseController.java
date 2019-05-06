package net.zjcclims.web.construction;


import net.zjcclims.dao.LabConstructAppDAO;
import net.zjcclims.dao.ProjectAppCourseDAO;
import net.zjcclims.dao.SchoolCourseDAO;
import net.zjcclims.domain.LabConstructApp;
import net.zjcclims.domain.ProjectAppCourse;
import net.zjcclims.domain.SchoolCourse;
import net.zjcclims.service.construction.ProjectAppCourseService;
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
 * Spring MVC controller that handles CRUD requests for ProjectAppCourse entities
 * 
 */

@Controller("ProjectAppCourseController")
public class ProjectAppCourseController {

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * DAO injected by Spring that manages ProjectAppCourse entities
	 * 
	 */
	@Autowired
	private ProjectAppCourseDAO projectAppCourseDAO;

	/**
	 * DAO injected by Spring that manages SchoolCourse entities
	 * 
	 */
	@Autowired
	private SchoolCourseDAO schoolCourseDAO;

	/**
	 * Service injected by Spring that provides CRUD operations for ProjectAppCourse entities
	 * 
	 */
	@Autowired
	private ProjectAppCourseService projectAppCourseService;

	/**
	 * Edit an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/editProjectAppCourseLabConstructApp")
	public ModelAndView editProjectAppCourseLabConstructApp(@RequestParam Integer projectappcourse_id, @RequestParam Integer labconstructapp_id) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectappcourse_id", projectappcourse_id);
		mav.addObject("labconstructapp", labconstructapp);
		mav.setViewName("projectappcourse/labconstructapp/editLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Select the ProjectAppCourse entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectAppCourse")
	public ModelAndView confirmDeleteProjectAppCourse(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectappcourse", projectAppCourseDAO.findProjectAppCourseByPrimaryKey(idKey));
		mav.setViewName("projectappcourse/deleteProjectAppCourse.jsp");

		return mav;
	}

	/**
	 * Select the child LabConstructApp entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectAppCourseLabConstructApp")
	public ModelAndView confirmDeleteProjectAppCourseLabConstructApp(@RequestParam Integer projectappcourse_id, @RequestParam Integer related_labconstructapp_id) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructapp", labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp_id));
		mav.addObject("projectappcourse_id", projectappcourse_id);
		mav.setViewName("projectappcourse/labconstructapp/deleteLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Select the child SchoolCourse entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectAppCourseSchoolCourse")
	public ModelAndView confirmDeleteProjectAppCourseSchoolCourse(@RequestParam Integer projectappcourse_id, @RequestParam String related_schoolcourse_courseNo) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("schoolcourse", schoolCourseDAO.findSchoolCourseByPrimaryKey(related_schoolcourse_courseNo));
		mav.addObject("projectappcourse_id", projectappcourse_id);
		mav.setViewName("projectappcourse/schoolcourse/deleteSchoolCourse.jsp");

		return mav;
	}

	/**
	 * Select an existing ProjectAppCourse entity
	 * 
	 */
	@RequestMapping("/selectProjectAppCourse")
	public ModelAndView selectProjectAppCourse(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectappcourse", projectAppCourseDAO.findProjectAppCourseByPrimaryKey(idKey));
		mav.setViewName("projectappcourse/viewProjectAppCourse.jsp");

		return mav;
	}

	/**
	 */
	@RequestMapping("/projectappcourseController/binary.action")
	public ModelAndView streamBinary(@ModelAttribute HttpServletRequest request, @ModelAttribute HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("streamedBinaryContentView");
		return mav;

	}

	/**
	 * Entry point to show all ProjectAppCourse entities
	 * 
	 */
	public String indexProjectAppCourse() {
		return "redirect:/indexProjectAppCourse";
	}

	/**
	 * Edit an existing ProjectAppCourse entity
	 * 
	 */
	@RequestMapping("/editProjectAppCourse")
	public ModelAndView editProjectAppCourse(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectappcourse", projectAppCourseDAO.findProjectAppCourseByPrimaryKey(idKey));
		mav.setViewName("projectappcourse/editProjectAppCourse.jsp");

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
	 * Create a new SchoolCourse entity
	 * 
	 */
	@RequestMapping("/newProjectAppCourseSchoolCourse")
	public ModelAndView newProjectAppCourseSchoolCourse(@RequestParam Integer projectappcourse_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("projectappcourse_id", projectappcourse_id);
		mav.addObject("schoolcourse", new SchoolCourse());
		mav.addObject("newFlag", true);
		mav.setViewName("projectappcourse/schoolcourse/editSchoolCourse.jsp");

		return mav;
	}

	/**
	 * Edit an existing SchoolCourse entity
	 * 
	 */
	@RequestMapping("/editProjectAppCourseSchoolCourse")
	public ModelAndView editProjectAppCourseSchoolCourse(@RequestParam Integer projectappcourse_id, @RequestParam String schoolcourse_courseNo) {
		SchoolCourse schoolcourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(schoolcourse_courseNo, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectappcourse_id", projectappcourse_id);
		mav.addObject("schoolcourse", schoolcourse);
		mav.setViewName("projectappcourse/schoolcourse/editSchoolCourse.jsp");

		return mav;
	}

	/**
	 * View an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/selectProjectAppCourseLabConstructApp")
	public ModelAndView selectProjectAppCourseLabConstructApp(@RequestParam Integer projectappcourse_id, @RequestParam Integer labconstructapp_id) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectappcourse_id", projectappcourse_id);
		mav.addObject("labconstructapp", labconstructapp);
		mav.setViewName("projectappcourse/labconstructapp/viewLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Save an existing SchoolCourse entity
	 * 
	 */
	@RequestMapping("/saveProjectAppCourseSchoolCourse")
	public ModelAndView saveProjectAppCourseSchoolCourse(@RequestParam Integer projectappcourse_id, @ModelAttribute SchoolCourse schoolcourse) {
		ProjectAppCourse parent_projectappcourse = projectAppCourseService.saveProjectAppCourseSchoolCourse(projectappcourse_id, schoolcourse);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectappcourse_id", projectappcourse_id);
		mav.addObject("projectappcourse", parent_projectappcourse);
		mav.setViewName("projectappcourse/viewProjectAppCourse.jsp");

		return mav;
	}

	/**
	 * Delete an existing ProjectAppCourse entity
	 * 
	 */
	@RequestMapping("/deleteProjectAppCourse")
	public String deleteProjectAppCourse(@RequestParam Integer idKey) {
		ProjectAppCourse projectappcourse = projectAppCourseDAO.findProjectAppCourseByPrimaryKey(idKey);
		projectAppCourseService.deleteProjectAppCourse(projectappcourse);
		return "forward:/indexProjectAppCourse";
	}

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/saveProjectAppCourseLabConstructApp")
	public ModelAndView saveProjectAppCourseLabConstructApp(@RequestParam Integer projectappcourse_id, @ModelAttribute LabConstructApp labconstructapp) {
		ProjectAppCourse parent_projectappcourse = projectAppCourseService.saveProjectAppCourseLabConstructApp(projectappcourse_id, labconstructapp);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectappcourse_id", projectappcourse_id);
		mav.addObject("projectappcourse", parent_projectappcourse);
		mav.setViewName("projectappcourse/viewProjectAppCourse.jsp");

		return mav;
	}

	/**
	 * Delete an existing SchoolCourse entity
	 * 
	 */
	@RequestMapping("/deleteProjectAppCourseSchoolCourse")
	public ModelAndView deleteProjectAppCourseSchoolCourse(@RequestParam Integer projectappcourse_id, @RequestParam String related_schoolcourse_courseNo) {
		ModelAndView mav = new ModelAndView();

		ProjectAppCourse projectappcourse = projectAppCourseService.deleteProjectAppCourseSchoolCourse(projectappcourse_id, related_schoolcourse_courseNo);

		mav.addObject("projectappcourse_id", projectappcourse_id);
		mav.addObject("projectappcourse", projectappcourse);
		mav.setViewName("projectappcourse/viewProjectAppCourse.jsp");

		return mav;
	}

	/**
	 * Show all SchoolCourse entities by ProjectAppCourse
	 * 
	 */
	@RequestMapping("/listProjectAppCourseSchoolCourse")
	public ModelAndView listProjectAppCourseSchoolCourse(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectappcourse", projectAppCourseDAO.findProjectAppCourseByPrimaryKey(idKey));
		mav.setViewName("projectappcourse/schoolcourse/listSchoolCourse.jsp");

		return mav;
	}

	/**
	 * View an existing SchoolCourse entity
	 * 
	 */
	@RequestMapping("/selectProjectAppCourseSchoolCourse")
	public ModelAndView selectProjectAppCourseSchoolCourse(@RequestParam Integer projectappcourse_id, @RequestParam String schoolcourse_courseNo) {
		SchoolCourse schoolcourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(schoolcourse_courseNo, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectappcourse_id", projectappcourse_id);
		mav.addObject("schoolcourse", schoolcourse);
		mav.setViewName("projectappcourse/schoolcourse/viewSchoolCourse.jsp");

		return mav;
	}

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/deleteProjectAppCourseLabConstructApp")
	public ModelAndView deleteProjectAppCourseLabConstructApp(@RequestParam Integer projectappcourse_id, @RequestParam Integer related_labconstructapp_id) {
		ModelAndView mav = new ModelAndView();

		ProjectAppCourse projectappcourse = projectAppCourseService.deleteProjectAppCourseLabConstructApp(projectappcourse_id, related_labconstructapp_id);

		mav.addObject("projectappcourse_id", projectappcourse_id);
		mav.addObject("projectappcourse", projectappcourse);
		mav.setViewName("projectappcourse/viewProjectAppCourse.jsp");

		return mav;
	}

	/**
	 * Create a new LabConstructApp entity
	 * 
	 */
	@RequestMapping("/newProjectAppCourseLabConstructApp")
	public ModelAndView newProjectAppCourseLabConstructApp(@RequestParam Integer projectappcourse_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("projectappcourse_id", projectappcourse_id);
		mav.addObject("labconstructapp", new LabConstructApp());
		mav.addObject("newFlag", true);
		mav.setViewName("projectappcourse/labconstructapp/editLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Create a new ProjectAppCourse entity
	 * 
	 */
	@RequestMapping("/newProjectAppCourse")
	public ModelAndView newProjectAppCourse() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectappcourse", new ProjectAppCourse());
		mav.addObject("newFlag", true);
		mav.setViewName("projectappcourse/editProjectAppCourse.jsp");

		return mav;
	}

	/**
	 * Save an existing ProjectAppCourse entity
	 * 
	 */
	@RequestMapping("/saveProjectAppCourse")
	public String saveProjectAppCourse(@ModelAttribute ProjectAppCourse projectappcourse) {
		projectAppCourseService.saveProjectAppCourse(projectappcourse);
		return "forward:/indexProjectAppCourse";
	}

	/**
	 * Show all ProjectAppCourse entities
	 * 
	 */
	@RequestMapping("/indexProjectAppCourse")
	public ModelAndView listProjectAppCourses() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectappcourses", projectAppCourseService.loadProjectAppCourses());

		mav.setViewName("projectappcourse/listProjectAppCourses.jsp");

		return mav;
	}

	/**
	 * Show all LabConstructApp entities by ProjectAppCourse
	 * 
	 */
	@RequestMapping("/listProjectAppCourseLabConstructApp")
	public ModelAndView listProjectAppCourseLabConstructApp(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectappcourse", projectAppCourseDAO.findProjectAppCourseByPrimaryKey(idKey));
		mav.setViewName("projectappcourse/labconstructapp/listLabConstructApp.jsp");

		return mav;
	}
}