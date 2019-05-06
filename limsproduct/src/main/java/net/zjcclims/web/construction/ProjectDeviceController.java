package net.zjcclims.web.construction;


import net.zjcclims.dao.LabConstructAppDAO;
import net.zjcclims.dao.LabRoomDAO;
import net.zjcclims.dao.ProjectDeviceDAO;
import net.zjcclims.dao.ProjectStartedReportDAO;
import net.zjcclims.domain.LabConstructApp;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.ProjectDevice;
import net.zjcclims.domain.ProjectStartedReport;
import net.zjcclims.service.construction.ProjectDeviceService;
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
 * Spring MVC controller that handles CRUD requests for ProjectDevice entities
 * 
 */

@Controller("ProjectDeviceController")
public class ProjectDeviceController {

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * DAO injected by Spring that manages LabRoom entities
	 * 
	 */
	@Autowired
	private LabRoomDAO labRoomDAO;

	/**
	 * DAO injected by Spring that manages ProjectDevice entities
	 * 
	 */
	@Autowired
	private ProjectDeviceDAO projectDeviceDAO;

	/**
	 * DAO injected by Spring that manages ProjectStartedReport entities
	 * 
	 */
	@Autowired
	private ProjectStartedReportDAO projectStartedReportDAO;

	/**
	 * Service injected by Spring that provides CRUD operations for ProjectDevice entities
	 * 
	 */
	@Autowired
	private ProjectDeviceService projectDeviceService;

	/**
	 * Save an existing ProjectDevice entity
	 * 
	 */
	@RequestMapping("/saveProjectDevice")
	public String saveProjectDevice(@ModelAttribute ProjectDevice projectdevice) {
		projectDeviceService.saveProjectDevice(projectdevice);
		return "forward:/indexProjectDevice";
	}

	/**
	 * Delete an existing ProjectStartedReport entity
	 * 
	 */
	@RequestMapping("/deleteProjectDeviceProjectStartedReport")
	public ModelAndView deleteProjectDeviceProjectStartedReport(@RequestParam Integer projectdevice_id, @RequestParam Integer related_projectstartedreport_id) {
		ModelAndView mav = new ModelAndView();

		ProjectDevice projectdevice = projectDeviceService.deleteProjectDeviceProjectStartedReport(projectdevice_id, related_projectstartedreport_id);

		mav.addObject("projectdevice_id", projectdevice_id);
		mav.addObject("projectdevice", projectdevice);
		mav.setViewName("projectdevice/viewProjectDevice.jsp");

		return mav;
	}

	/**
	 * Show all ProjectStartedReport entities by ProjectDevice
	 * 
	 */
	@RequestMapping("/listProjectDeviceProjectStartedReport")
	public ModelAndView listProjectDeviceProjectStartedReport(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectdevice", projectDeviceDAO.findProjectDeviceByPrimaryKey(idKey));
		mav.setViewName("projectdevice/projectstartedreport/listProjectStartedReport.jsp");

		return mav;
	}

	/**
	 * Create a new ProjectDevice entity
	 * 
	 */
	@RequestMapping("/newProjectDevice")
	public ModelAndView newProjectDevice() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectdevice", new ProjectDevice());
		mav.addObject("newFlag", true);
		mav.setViewName("projectdevice/editProjectDevice.jsp");

		return mav;
	}

	/**
	 * Show all LabConstructApp entities by ProjectDevice
	 * 
	 */
	@RequestMapping("/listProjectDeviceLabConstructApp")
	public ModelAndView listProjectDeviceLabConstructApp(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectdevice", projectDeviceDAO.findProjectDeviceByPrimaryKey(idKey));
		mav.setViewName("projectdevice/labconstructapp/listLabConstructApp.jsp");

		return mav;
	}

	/**
	 * View an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/selectProjectDeviceLabConstructApp")
	public ModelAndView selectProjectDeviceLabConstructApp(@RequestParam Integer projectdevice_id, @RequestParam Integer labconstructapp_id) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectdevice_id", projectdevice_id);
		mav.addObject("labconstructapp", labconstructapp);
		mav.setViewName("projectdevice/labconstructapp/viewLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Show all LabRoom entities by ProjectDevice
	 * 
	 */
	@RequestMapping("/listProjectDeviceLabRoom")
	public ModelAndView listProjectDeviceLabRoom(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectdevice", projectDeviceDAO.findProjectDeviceByPrimaryKey(idKey));
		mav.setViewName("projectdevice/labroom/listLabRoom.jsp");

		return mav;
	}

	/**
	 * Create a new ProjectStartedReport entity
	 * 
	 */
	@RequestMapping("/newProjectDeviceProjectStartedReport")
	public ModelAndView newProjectDeviceProjectStartedReport(@RequestParam Integer projectdevice_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("projectdevice_id", projectdevice_id);
		mav.addObject("projectstartedreport", new ProjectStartedReport());
		mav.addObject("newFlag", true);
		mav.setViewName("projectdevice/projectstartedreport/editProjectStartedReport.jsp");

		return mav;
	}

	/**
	 * Show all ProjectDevice entities
	 * 
	 */
	@RequestMapping("/indexProjectDevice")
	public ModelAndView listProjectDevices() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectdevices", projectDeviceService.loadProjectDevices());

		mav.setViewName("projectdevice/listProjectDevices.jsp");

		return mav;
	}

	/**
	 * Select the child ProjectStartedReport entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectDeviceProjectStartedReport")
	public ModelAndView confirmDeleteProjectDeviceProjectStartedReport(@RequestParam Integer projectdevice_id, @RequestParam Integer related_projectstartedreport_id) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectstartedreport", projectStartedReportDAO.findProjectStartedReportByPrimaryKey(related_projectstartedreport_id));
		mav.addObject("projectdevice_id", projectdevice_id);
		mav.setViewName("projectdevice/projectstartedreport/deleteProjectStartedReport.jsp");

		return mav;
	}

	/**
	 * Delete an existing LabRoom entity
	 * 
	 */
	@RequestMapping("/deleteProjectDeviceLabRoom")
	public ModelAndView deleteProjectDeviceLabRoom(@RequestParam Integer projectdevice_id, @RequestParam Integer related_labroom_id) {
		ModelAndView mav = new ModelAndView();

		ProjectDevice projectdevice = projectDeviceService.deleteProjectDeviceLabRoom(projectdevice_id, related_labroom_id);

		mav.addObject("projectdevice_id", projectdevice_id);
		mav.addObject("projectdevice", projectdevice);
		mav.setViewName("projectdevice/viewProjectDevice.jsp");

		return mav;
	}

	/**
	 */
	@RequestMapping("/projectdeviceController/binary.action")
	public ModelAndView streamBinary(@ModelAttribute HttpServletRequest request, @ModelAttribute HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("streamedBinaryContentView");
		return mav;

	}

	/**
	 * View an existing ProjectStartedReport entity
	 * 
	 */
	@RequestMapping("/selectProjectDeviceProjectStartedReport")
	public ModelAndView selectProjectDeviceProjectStartedReport(@RequestParam Integer projectdevice_id, @RequestParam Integer projectstartedreport_id) {
		ProjectStartedReport projectstartedreport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(projectstartedreport_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectdevice_id", projectdevice_id);
		mav.addObject("projectstartedreport", projectstartedreport);
		mav.setViewName("projectdevice/projectstartedreport/viewProjectStartedReport.jsp");

		return mav;
	}

	/**
	 * Select the child LabConstructApp entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectDeviceLabConstructApp")
	public ModelAndView confirmDeleteProjectDeviceLabConstructApp(@RequestParam Integer projectdevice_id, @RequestParam Integer related_labconstructapp_id) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructapp", labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp_id));
		mav.addObject("projectdevice_id", projectdevice_id);
		mav.setViewName("projectdevice/labconstructapp/deleteLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Create a new LabConstructApp entity
	 * 
	 */
	@RequestMapping("/newProjectDeviceLabConstructApp")
	public ModelAndView newProjectDeviceLabConstructApp(@RequestParam Integer projectdevice_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("projectdevice_id", projectdevice_id);
		mav.addObject("labconstructapp", new LabConstructApp());
		mav.addObject("newFlag", true);
		mav.setViewName("projectdevice/labconstructapp/editLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Delete an existing ProjectDevice entity
	 * 
	 */
	@RequestMapping("/deleteProjectDevice")
	public String deleteProjectDevice(@RequestParam Integer idKey) {
		ProjectDevice projectdevice = projectDeviceDAO.findProjectDeviceByPrimaryKey(idKey);
		projectDeviceService.deleteProjectDevice(projectdevice);
		return "forward:/indexProjectDevice";
	}

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/saveProjectDeviceLabConstructApp")
	public ModelAndView saveProjectDeviceLabConstructApp(@RequestParam Integer projectdevice_id, @ModelAttribute LabConstructApp labconstructapp) {
		ProjectDevice parent_projectdevice = projectDeviceService.saveProjectDeviceLabConstructApp(projectdevice_id, labconstructapp);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectdevice_id", projectdevice_id);
		mav.addObject("projectdevice", parent_projectdevice);
		mav.setViewName("projectdevice/viewProjectDevice.jsp");

		return mav;
	}

	/**
	 * Select an existing ProjectDevice entity
	 * 
	 */
	@RequestMapping("/selectProjectDevice")
	public ModelAndView selectProjectDevice(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectdevice", projectDeviceDAO.findProjectDeviceByPrimaryKey(idKey));
		mav.setViewName("projectdevice/viewProjectDevice.jsp");

		return mav;
	}

	/**
	 * Entry point to show all ProjectDevice entities
	 * 
	 */
	public String indexProjectDevice() {
		return "redirect:/indexProjectDevice";
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
	 * Select the ProjectDevice entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectDevice")
	public ModelAndView confirmDeleteProjectDevice(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectdevice", projectDeviceDAO.findProjectDeviceByPrimaryKey(idKey));
		mav.setViewName("projectdevice/deleteProjectDevice.jsp");

		return mav;
	}

	/**
	 * View an existing LabRoom entity
	 * 
	 */
	@RequestMapping("/selectProjectDeviceLabRoom")
	public ModelAndView selectProjectDeviceLabRoom(@RequestParam Integer projectdevice_id, @RequestParam Integer labroom_id) {
		LabRoom labroom = labRoomDAO.findLabRoomByPrimaryKey(labroom_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectdevice_id", projectdevice_id);
		mav.addObject("labroom", labroom);
		mav.setViewName("projectdevice/labroom/viewLabRoom.jsp");

		return mav;
	}

	/**
	 * Edit an existing ProjectDevice entity
	 * 
	 */
	@RequestMapping("/editProjectDevice")
	public ModelAndView editProjectDevice(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("projectdevice", projectDeviceDAO.findProjectDeviceByPrimaryKey(idKey));
		mav.setViewName("projectdevice/editProjectDevice.jsp");

		return mav;
	}

	/**
	 * Edit an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/editProjectDeviceLabConstructApp")
	public ModelAndView editProjectDeviceLabConstructApp(@RequestParam Integer projectdevice_id, @RequestParam Integer labconstructapp_id) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectdevice_id", projectdevice_id);
		mav.addObject("labconstructapp", labconstructapp);
		mav.setViewName("projectdevice/labconstructapp/editLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Edit an existing ProjectStartedReport entity
	 * 
	 */
	@RequestMapping("/editProjectDeviceProjectStartedReport")
	public ModelAndView editProjectDeviceProjectStartedReport(@RequestParam Integer projectdevice_id, @RequestParam Integer projectstartedreport_id) {
		ProjectStartedReport projectstartedreport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(projectstartedreport_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectdevice_id", projectdevice_id);
		mav.addObject("projectstartedreport", projectstartedreport);
		mav.setViewName("projectdevice/projectstartedreport/editProjectStartedReport.jsp");

		return mav;
	}

	/**
	 * Select the child LabRoom entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteProjectDeviceLabRoom")
	public ModelAndView confirmDeleteProjectDeviceLabRoom(@RequestParam Integer projectdevice_id, @RequestParam Integer related_labroom_id) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labroom", labRoomDAO.findLabRoomByPrimaryKey(related_labroom_id));
		mav.addObject("projectdevice_id", projectdevice_id);
		mav.setViewName("projectdevice/labroom/deleteLabRoom.jsp");

		return mav;
	}

	/**
	 * Create a new LabRoom entity
	 * 
	 */
	@RequestMapping("/newProjectDeviceLabRoom")
	public ModelAndView newProjectDeviceLabRoom(@RequestParam Integer projectdevice_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("projectdevice_id", projectdevice_id);
		mav.addObject("labroom", new LabRoom());
		mav.addObject("newFlag", true);
		mav.setViewName("projectdevice/labroom/editLabRoom.jsp");

		return mav;
	}

	/**
	 * Save an existing ProjectStartedReport entity
	 * 
	 */
	@RequestMapping("/saveProjectDeviceProjectStartedReport")
	public ModelAndView saveProjectDeviceProjectStartedReport(@RequestParam Integer projectdevice_id, @ModelAttribute ProjectStartedReport projectstartedreport) {
		ProjectDevice parent_projectdevice = projectDeviceService.saveProjectDeviceProjectStartedReport(projectdevice_id, projectstartedreport);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectdevice_id", projectdevice_id);
		mav.addObject("projectdevice", parent_projectdevice);
		mav.setViewName("projectdevice/viewProjectDevice.jsp");

		return mav;
	}

	/**
	 * Save an existing LabRoom entity
	 * 
	 */
	@RequestMapping("/saveProjectDeviceLabRoom")
	public ModelAndView saveProjectDeviceLabRoom(@RequestParam Integer projectdevice_id, @ModelAttribute LabRoom labroom) {
		ProjectDevice parent_projectdevice = projectDeviceService.saveProjectDeviceLabRoom(projectdevice_id, labroom);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectdevice_id", projectdevice_id);
		mav.addObject("projectdevice", parent_projectdevice);
		mav.setViewName("projectdevice/viewProjectDevice.jsp");

		return mav;
	}

	/**
	 * Edit an existing LabRoom entity
	 * 
	 */
	@RequestMapping("/editProjectDeviceLabRoom")
	public ModelAndView editProjectDeviceLabRoom(@RequestParam Integer projectdevice_id, @RequestParam Integer labroom_id) {
		LabRoom labroom = labRoomDAO.findLabRoomByPrimaryKey(labroom_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("projectdevice_id", projectdevice_id);
		mav.addObject("labroom", labroom);
		mav.setViewName("projectdevice/labroom/editLabRoom.jsp");

		return mav;
	}

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/deleteProjectDeviceLabConstructApp")
	public ModelAndView deleteProjectDeviceLabConstructApp(@RequestParam Integer projectdevice_id, @RequestParam Integer related_labconstructapp_id) {
		ModelAndView mav = new ModelAndView();

		ProjectDevice projectdevice = projectDeviceService.deleteProjectDeviceLabConstructApp(projectdevice_id, related_labconstructapp_id);

		mav.addObject("projectdevice_id", projectdevice_id);
		mav.addObject("projectdevice", projectdevice);
		mav.setViewName("projectdevice/viewProjectDevice.jsp");

		return mav;
	}
}