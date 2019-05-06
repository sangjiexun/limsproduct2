package net.zjcclims.web.construction;


import net.zjcclims.dao.LabConstructAppDAO;
import net.zjcclims.dao.ProjectAcceptanceApplicationDAO;
import net.zjcclims.dao.ProjectCompletionItemDAO;
import net.zjcclims.dao.ProjectStartedReportDAO;
import net.zjcclims.domain.LabConstructApp;
import net.zjcclims.domain.ProjectAcceptanceApplication;
import net.zjcclims.domain.ProjectCompletionItem;
import net.zjcclims.domain.ProjectStartedReport;
import net.zjcclims.service.construction.ProjectCompletionItemService;
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
 * Spring MVC controller that handles CRUD requests for ProjectCompletionItem entities
 * 
 */

@Controller("ProjectCompletionItemController")
public class ProjectCompletionItemController {

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * DAO injected by Spring that manages ProjectAcceptanceApplication entities
	 * 
	 */
	@Autowired
	private ProjectAcceptanceApplicationDAO projectAcceptanceApplicationDAO;

	/**
	 * DAO injected by Spring that manages ProjectCompletionItem entities
	 * 
	 */
	@Autowired
	private ProjectCompletionItemDAO projectCompletionItemDAO;

	/**
	 * DAO injected by Spring that manages ProjectStartedReport entities
	 * 
	 */
	@Autowired
	private ProjectStartedReportDAO projectStartedReportDAO;

	/**
	 * Service injected by Spring that provides CRUD operations for ProjectCompletionItem entities
	 * 
	 */
	@Autowired
	private ProjectCompletionItemService projectCompletionItemService;

	/**
	 * Show all ProjectAcceptanceApplication entities by ProjectCompletionItem
	 * 
	 */
	@RequestMapping("/listProjectCompletionItemProjectAcceptanceApplication")
	public ModelAndView listProjectCompletionItemProjectAcceptanceApplication(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectcompletionitem", projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(idKey));
		mav.setViewName("projectcompletionitem/projectacceptanceapplication/listProjectAcceptanceApplication.jsp");

		return mav;
	}

	/**
	 * Create a new ProjectStartedReport entity
	 * 
	 */
	@RequestMapping("/newProjectCompletionItemProjectStartedReport")
	public ModelAndView newProjectCompletionItemProjectStartedReport(@RequestParam Integer projectcompletionitem_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.addObject("projectstartedreport", new ProjectStartedReport());
		mav.addObject("newFlag", true);
		mav.setViewName("projectcompletionitem/projectstartedreport/editProjectStartedReport.jsp");

		return mav;
	}

	/**
	 * Show all ProjectStartedReport entities by ProjectCompletionItem
	 * 
	 */
	@RequestMapping("/listProjectCompletionItemProjectStartedReport")
	public ModelAndView listProjectCompletionItemProjectStartedReport(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectcompletionitem", projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(idKey));
		mav.setViewName("projectcompletionitem/projectstartedreport/listProjectStartedReport.jsp");

		return mav;
	}

	/**
	 * Show all ProjectCompletionItem entities
	 * 
	 */
	@RequestMapping("/indexProjectCompletionItem")
	public ModelAndView listProjectCompletionItems() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectcompletionitems", projectCompletionItemService.loadProjectCompletionItems());

		mav.setViewName("projectcompletionitem/listProjectCompletionItems.jsp");

		return mav;
	}

	/**
	 * Delete an existing ProjectStartedReport entity
	 * 
	 */
	@RequestMapping("/deleteProjectCompletionItemProjectStartedReport")
	public ModelAndView deleteProjectCompletionItemProjectStartedReport(@RequestParam Integer projectcompletionitem_id, @RequestParam Integer related_projectstartedreport_id) {
		ModelAndView mav = new ModelAndView();

		ProjectCompletionItem projectcompletionitem = projectCompletionItemService.deleteProjectCompletionItemProjectStartedReport(projectcompletionitem_id, related_projectstartedreport_id);

		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.addObject("projectcompletionitem", projectcompletionitem);
		mav.setViewName("projectcompletionitem/viewProjectCompletionItem.jsp");

		return mav;
	}

	/**
	 * Select the child LabConstructApp entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectCompletionItemLabConstructApp")
	public ModelAndView confirmDeleteProjectCompletionItemLabConstructApp(@RequestParam Integer projectcompletionitem_id, @RequestParam Integer related_labconstructapp_id) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructapp", labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp_id));
		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.setViewName("projectcompletionitem/labconstructapp/deleteLabConstructApp.jsp");

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
	 * Save an existing ProjectCompletionItem entity
	 * 
	 */
	@RequestMapping("/saveProjectCompletionItem")
	public String saveProjectCompletionItem(@ModelAttribute ProjectCompletionItem projectcompletionitem) {
		projectCompletionItemService.saveProjectCompletionItem(projectcompletionitem);
		return "forward:/indexProjectCompletionItem";
	}

	/**
	 * Save an existing ProjectAcceptanceApplication entity
	 * 
	 */
	@RequestMapping("/saveProjectCompletionItemProjectAcceptanceApplication")
	public ModelAndView saveProjectCompletionItemProjectAcceptanceApplication(@RequestParam Integer projectcompletionitem_id, @ModelAttribute ProjectAcceptanceApplication projectacceptanceapplication) {
		ProjectCompletionItem parent_projectcompletionitem = projectCompletionItemService.saveProjectCompletionItemProjectAcceptanceApplication(projectcompletionitem_id, projectacceptanceapplication);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.addObject("projectcompletionitem", parent_projectcompletionitem);
		mav.setViewName("projectcompletionitem/viewProjectCompletionItem.jsp");

		return mav;
	}

	/**
	 */
	@RequestMapping("/projectcompletionitemController/binary.action")
	public ModelAndView streamBinary(@ModelAttribute HttpServletRequest request, @ModelAttribute HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("streamedBinaryContentView");
		return mav;

	}

	/**
	 * View an existing ProjectAcceptanceApplication entity
	 * 
	 */
	@RequestMapping("/selectProjectCompletionItemProjectAcceptanceApplication")
	public ModelAndView selectProjectCompletionItemProjectAcceptanceApplication(@RequestParam Integer projectcompletionitem_id, @RequestParam Integer projectacceptanceapplication_id) {
		ProjectAcceptanceApplication projectacceptanceapplication = projectAcceptanceApplicationDAO.findProjectAcceptanceApplicationByPrimaryKey(projectacceptanceapplication_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.addObject("projectacceptanceapplication", projectacceptanceapplication);
		mav.setViewName("projectcompletionitem/projectacceptanceapplication/viewProjectAcceptanceApplication.jsp");

		return mav;
	}

	/**
	 * Select the ProjectCompletionItem entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectCompletionItem")
	public ModelAndView confirmDeleteProjectCompletionItem(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectcompletionitem", projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(idKey));
		mav.setViewName("projectcompletionitem/deleteProjectCompletionItem.jsp");

		return mav;
	}

	/**
	 * Delete an existing ProjectCompletionItem entity
	 * 
	 */
	@RequestMapping("/deleteProjectCompletionItem")
	public String deleteProjectCompletionItem(@RequestParam Integer idKey) {
		ProjectCompletionItem projectcompletionitem = projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(idKey);
		projectCompletionItemService.deleteProjectCompletionItem(projectcompletionitem);
		return "forward:/indexProjectCompletionItem";
	}

	/**
	 * Edit an existing ProjectStartedReport entity
	 * 
	 */
	@RequestMapping("/editProjectCompletionItemProjectStartedReport")
	public ModelAndView editProjectCompletionItemProjectStartedReport(@RequestParam Integer projectcompletionitem_id, @RequestParam Integer projectstartedreport_id) {
		ProjectStartedReport projectstartedreport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(projectstartedreport_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.addObject("projectstartedreport", projectstartedreport);
		mav.setViewName("projectcompletionitem/projectstartedreport/editProjectStartedReport.jsp");

		return mav;
	}

	/**
	 * Select the child ProjectStartedReport entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectCompletionItemProjectStartedReports")
	public ModelAndView confirmDeleteProjectCompletionItemProjectStartedReports(@RequestParam Integer projectcompletionitem_id, @RequestParam Integer related_projectstartedreports_id) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectstartedreport", projectStartedReportDAO.findProjectStartedReportByPrimaryKey(related_projectstartedreports_id));
		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.setViewName("projectcompletionitem/projectstartedreports/deleteProjectStartedReports.jsp");

		return mav;
	}

	/**
	 * Select an existing ProjectCompletionItem entity
	 * 
	 */
	@RequestMapping("/selectProjectCompletionItem")
	public ModelAndView selectProjectCompletionItem(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectcompletionitem", projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(idKey));
		mav.setViewName("projectcompletionitem/viewProjectCompletionItem.jsp");

		return mav;
	}

	/**
	 * Edit an existing ProjectCompletionItem entity
	 * 
	 */
	@RequestMapping("/editProjectCompletionItem")
	public ModelAndView editProjectCompletionItem(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectcompletionitem", projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(idKey));
		mav.setViewName("projectcompletionitem/editProjectCompletionItem.jsp");

		return mav;
	}

	/**
	 * Create a new ProjectCompletionItem entity
	 * 
	 */
	@RequestMapping("/newProjectCompletionItem")
	public ModelAndView newProjectCompletionItem() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectcompletionitem", new ProjectCompletionItem());
		mav.addObject("newFlag", true);
		mav.setViewName("projectcompletionitem/editProjectCompletionItem.jsp");

		return mav;
	}

	/**
	 * Save an existing ProjectStartedReport entity
	 * 
	 */
	@RequestMapping("/saveProjectCompletionItemProjectStartedReport")
	public ModelAndView saveProjectCompletionItemProjectStartedReport(@RequestParam Integer projectcompletionitem_id, @ModelAttribute ProjectStartedReport projectstartedreport) {
		ProjectCompletionItem parent_projectcompletionitem = projectCompletionItemService.saveProjectCompletionItemProjectStartedReport(projectcompletionitem_id, projectstartedreport);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.addObject("projectcompletionitem", parent_projectcompletionitem);
		mav.setViewName("projectcompletionitem/viewProjectCompletionItem.jsp");

		return mav;
	}

	/**
	 * Create a new ProjectStartedReport entity
	 * 
	 */
	@RequestMapping("/newProjectCompletionItemProjectStartedReports")
	public ModelAndView newProjectCompletionItemProjectStartedReports(@RequestParam Integer projectcompletionitem_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.addObject("projectstartedreport", new ProjectStartedReport());
		mav.addObject("newFlag", true);
		mav.setViewName("projectcompletionitem/projectstartedreports/editProjectStartedReports.jsp");

		return mav;
	}

	/**
	 * Create a new ProjectAcceptanceApplication entity
	 * 
	 */
	@RequestMapping("/newProjectCompletionItemProjectAcceptanceApplication")
	public ModelAndView newProjectCompletionItemProjectAcceptanceApplication(@RequestParam Integer projectcompletionitem_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.addObject("projectacceptanceapplication", new ProjectAcceptanceApplication());
		mav.addObject("newFlag", true);
		mav.setViewName("projectcompletionitem/projectacceptanceapplication/editProjectAcceptanceApplication.jsp");

		return mav;
	}

	/**
	 * View an existing ProjectStartedReport entity
	 * 
	 */
	@RequestMapping("/selectProjectCompletionItemProjectStartedReport")
	public ModelAndView selectProjectCompletionItemProjectStartedReport(@RequestParam Integer projectcompletionitem_id, @RequestParam Integer projectstartedreport_id) {
		ProjectStartedReport projectstartedreport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(projectstartedreport_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.addObject("projectstartedreport", projectstartedreport);
		mav.setViewName("projectcompletionitem/projectstartedreport/viewProjectStartedReport.jsp");

		return mav;
	}

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/deleteProjectCompletionItemLabConstructApp")
	public ModelAndView deleteProjectCompletionItemLabConstructApp(@RequestParam Integer projectcompletionitem_id, @RequestParam Integer related_labconstructapp_id) {
		ModelAndView mav = new ModelAndView();

		ProjectCompletionItem projectcompletionitem = projectCompletionItemService.deleteProjectCompletionItemLabConstructApp(projectcompletionitem_id, related_labconstructapp_id);

		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.addObject("projectcompletionitem", projectcompletionitem);
		mav.setViewName("projectcompletionitem/viewProjectCompletionItem.jsp");

		return mav;
	}

	/**
	 * Edit an existing ProjectStartedReport entity
	 * 
	 */
	@RequestMapping("/editProjectCompletionItemProjectStartedReports")
	public ModelAndView editProjectCompletionItemProjectStartedReports(@RequestParam Integer projectcompletionitem_id, @RequestParam Integer projectstartedreports_id) {
		ProjectStartedReport projectstartedreport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(projectstartedreports_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.addObject("projectstartedreport", projectstartedreport);
		mav.setViewName("projectcompletionitem/projectstartedreports/editProjectStartedReports.jsp");

		return mav;
	}

	/**
	 * Delete an existing ProjectAcceptanceApplication entity
	 * 
	 */
	@RequestMapping("/deleteProjectCompletionItemProjectAcceptanceApplication")
	public ModelAndView deleteProjectCompletionItemProjectAcceptanceApplication(@RequestParam Integer projectcompletionitem_id, @RequestParam Integer related_projectacceptanceapplication_id) {
		ModelAndView mav = new ModelAndView();

		ProjectCompletionItem projectcompletionitem = projectCompletionItemService.deleteProjectCompletionItemProjectAcceptanceApplication(projectcompletionitem_id, related_projectacceptanceapplication_id);

		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.addObject("projectcompletionitem", projectcompletionitem);
		mav.setViewName("projectcompletionitem/viewProjectCompletionItem.jsp");

		return mav;
	}

	/**
	 * Entry point to show all ProjectCompletionItem entities
	 * 
	 */
	public String indexProjectCompletionItem() {
		return "redirect:/indexProjectCompletionItem";
	}

	/**
	 * Save an existing ProjectStartedReport entity
	 * 
	 */
	@RequestMapping("/saveProjectCompletionItemProjectStartedReports")
	public ModelAndView saveProjectCompletionItemProjectStartedReports(@RequestParam Integer projectcompletionitem_id, @ModelAttribute ProjectStartedReport projectstartedreports) {
		ProjectCompletionItem parent_projectcompletionitem = projectCompletionItemService.saveProjectCompletionItemProjectStartedReports(projectcompletionitem_id, projectstartedreports);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.addObject("projectcompletionitem", parent_projectcompletionitem);
		mav.setViewName("projectcompletionitem/viewProjectCompletionItem.jsp");

		return mav;
	}

	/**
	 * Select the child ProjectStartedReport entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectCompletionItemProjectStartedReport")
	public ModelAndView confirmDeleteProjectCompletionItemProjectStartedReport(@RequestParam Integer projectcompletionitem_id, @RequestParam Integer related_projectstartedreport_id) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectstartedreport", projectStartedReportDAO.findProjectStartedReportByPrimaryKey(related_projectstartedreport_id));
		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.setViewName("projectcompletionitem/projectstartedreport/deleteProjectStartedReport.jsp");

		return mav;
	}

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/saveProjectCompletionItemLabConstructApp")
	public ModelAndView saveProjectCompletionItemLabConstructApp(@RequestParam Integer projectcompletionitem_id, @ModelAttribute LabConstructApp labconstructapp) {
		ProjectCompletionItem parent_projectcompletionitem = projectCompletionItemService.saveProjectCompletionItemLabConstructApp(projectcompletionitem_id, labconstructapp);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.addObject("projectcompletionitem", parent_projectcompletionitem);
		mav.setViewName("projectcompletionitem/viewProjectCompletionItem.jsp");

		return mav;
	}

	/**
	 * Edit an existing ProjectAcceptanceApplication entity
	 * 
	 */
	@RequestMapping("/editProjectCompletionItemProjectAcceptanceApplication")
	public ModelAndView editProjectCompletionItemProjectAcceptanceApplication(@RequestParam Integer projectcompletionitem_id, @RequestParam Integer projectacceptanceapplication_id) {
		ProjectAcceptanceApplication projectacceptanceapplication = projectAcceptanceApplicationDAO.findProjectAcceptanceApplicationByPrimaryKey(projectacceptanceapplication_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.addObject("projectacceptanceapplication", projectacceptanceapplication);
		mav.setViewName("projectcompletionitem/projectacceptanceapplication/editProjectAcceptanceApplication.jsp");

		return mav;
	}

	/**
	 * Edit an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/editProjectCompletionItemLabConstructApp")
	public ModelAndView editProjectCompletionItemLabConstructApp(@RequestParam Integer projectcompletionitem_id, @RequestParam Integer labconstructapp_id) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.addObject("labconstructapp", labconstructapp);
		mav.setViewName("projectcompletionitem/labconstructapp/editLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Show all LabConstructApp entities by ProjectCompletionItem
	 * 
	 */
	@RequestMapping("/listProjectCompletionItemLabConstructApp")
	public ModelAndView listProjectCompletionItemLabConstructApp(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectcompletionitem", projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(idKey));
		mav.setViewName("projectcompletionitem/labconstructapp/listLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Create a new LabConstructApp entity
	 * 
	 */
	@RequestMapping("/newProjectCompletionItemLabConstructApp")
	public ModelAndView newProjectCompletionItemLabConstructApp(@RequestParam Integer projectcompletionitem_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.addObject("labconstructapp", new LabConstructApp());
		mav.addObject("newFlag", true);
		mav.setViewName("projectcompletionitem/labconstructapp/editLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Select the child ProjectAcceptanceApplication entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectCompletionItemProjectAcceptanceApplication")
	public ModelAndView confirmDeleteProjectCompletionItemProjectAcceptanceApplication(@RequestParam Integer projectcompletionitem_id, @RequestParam Integer related_projectacceptanceapplication_id) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectacceptanceapplication", projectAcceptanceApplicationDAO.findProjectAcceptanceApplicationByPrimaryKey(related_projectacceptanceapplication_id));
		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.setViewName("projectcompletionitem/projectacceptanceapplication/deleteProjectAcceptanceApplication.jsp");

		return mav;
	}

	/**
	 * View an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/selectProjectCompletionItemLabConstructApp")
	public ModelAndView selectProjectCompletionItemLabConstructApp(@RequestParam Integer projectcompletionitem_id, @RequestParam Integer labconstructapp_id) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.addObject("labconstructapp", labconstructapp);
		mav.setViewName("projectcompletionitem/labconstructapp/viewLabConstructApp.jsp");

		return mav;
	}

	/**
	 * View an existing ProjectStartedReport entity
	 * 
	 */
	@RequestMapping("/selectProjectCompletionItemProjectStartedReports")
	public ModelAndView selectProjectCompletionItemProjectStartedReports(@RequestParam Integer projectcompletionitem_id, @RequestParam Integer projectstartedreports_id) {
		ProjectStartedReport projectstartedreport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(projectstartedreports_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.addObject("projectstartedreport", projectstartedreport);
		mav.setViewName("projectcompletionitem/projectstartedreports/viewProjectStartedReports.jsp");

		return mav;
	}

	/**
	 * Delete an existing ProjectStartedReport entity
	 * 
	 */
	@RequestMapping("/deleteProjectCompletionItemProjectStartedReports")
	public ModelAndView deleteProjectCompletionItemProjectStartedReports(@RequestParam Integer projectcompletionitem_id, @RequestParam Integer related_projectstartedreports_id) {
		ModelAndView mav = new ModelAndView();

		ProjectCompletionItem projectcompletionitem = projectCompletionItemService.deleteProjectCompletionItemProjectStartedReports(projectcompletionitem_id, related_projectstartedreports_id);

		mav.addObject("projectcompletionitem_id", projectcompletionitem_id);
		mav.addObject("projectcompletionitem", projectcompletionitem);
		mav.setViewName("projectcompletionitem/viewProjectCompletionItem.jsp");

		return mav;
	}

	/**
	 * Show all ProjectStartedReport entities by ProjectCompletionItem
	 * 
	 */
	@RequestMapping("/listProjectCompletionItemProjectStartedReports")
	public ModelAndView listProjectCompletionItemProjectStartedReports(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectcompletionitem", projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(idKey));
		mav.setViewName("projectcompletionitem/projectstartedreports/listProjectStartedReports.jsp");

		return mav;
	}
}