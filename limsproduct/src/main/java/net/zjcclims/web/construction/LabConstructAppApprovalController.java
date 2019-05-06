package net.zjcclims.web.construction;


import net.zjcclims.dao.LabConstructAppApprovalDAO;
import net.zjcclims.dao.LabConstructAppDAO;
import net.zjcclims.dao.UserDAO;
import net.zjcclims.domain.LabConstructApp;
import net.zjcclims.domain.LabConstructAppApproval;
import net.zjcclims.domain.User;
import net.zjcclims.service.construction.LabConstructAppApprovalService;
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
 * Spring MVC controller that handles CRUD requests for LabConstructAppApproval entities
 * 
 */

@Controller("LabConstructAppApprovalController")
public class LabConstructAppApprovalController {

	/**
	 * DAO injected by Spring that manages LabConstructAppApproval entities
	 * 
	 */
	@Autowired
	private LabConstructAppApprovalDAO labConstructAppApprovalDAO;

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * DAO injected by Spring that manages User entities
	 * 
	 */
	@Autowired
	private UserDAO userDAO;

	/**
	 * Service injected by Spring that provides CRUD operations for LabConstructAppApproval entities
	 * 
	 */
	@Autowired
	private LabConstructAppApprovalService labConstructAppApprovalService;

	/**
	 * Create a new User entity
	 * 
	 */
	@RequestMapping("/newLabConstructAppApprovalUser")
	public ModelAndView newLabConstructAppApprovalUser(@RequestParam Integer labconstructappapproval_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("labconstructappapproval_id", labconstructappapproval_id);
		mav.addObject("user", new User());
		mav.addObject("newFlag", true);
		mav.setViewName("labconstructappapproval/user/editUser.jsp");

		return mav;
	}

	/**
	 * Edit an existing LabConstructAppApproval entity
	 * 
	 */
	@RequestMapping("/editLabConstructAppApproval")
	public ModelAndView editLabConstructAppApproval(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructappapproval", labConstructAppApprovalDAO.findLabConstructAppApprovalByPrimaryKey(idKey));
		mav.setViewName("labconstructappapproval/editLabConstructAppApproval.jsp");

		return mav;
	}

	/**
	 * Create a new LabConstructApp entity
	 * 
	 */
	@RequestMapping("/newLabConstructAppApprovalLabConstructApp")
	public ModelAndView newLabConstructAppApprovalLabConstructApp(@RequestParam Integer labconstructappapproval_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("labconstructappapproval_id", labconstructappapproval_id);
		mav.addObject("labconstructapp", new LabConstructApp());
		mav.addObject("newFlag", true);
		mav.setViewName("labconstructappapproval/labconstructapp/editLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Select an existing LabConstructAppApproval entity
	 * 
	 */
	@RequestMapping("/selectLabConstructAppApproval")
	public ModelAndView selectLabConstructAppApproval(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructappapproval", labConstructAppApprovalDAO.findLabConstructAppApprovalByPrimaryKey(idKey));
		mav.setViewName("labconstructappapproval/viewLabConstructAppApproval.jsp");

		return mav;
	}

	/**
	 * View an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/selectLabConstructAppApprovalLabConstructApp")
	public ModelAndView selectLabConstructAppApprovalLabConstructApp(@RequestParam Integer labconstructappapproval_id, @RequestParam Integer labconstructapp_id) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("labconstructappapproval_id", labconstructappapproval_id);
		mav.addObject("labconstructapp", labconstructapp);
		mav.setViewName("labconstructappapproval/labconstructapp/viewLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Create a new LabConstructAppApproval entity
	 * 
	 */
	@RequestMapping("/newLabConstructAppApproval")
	public ModelAndView newLabConstructAppApproval() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructappapproval", new LabConstructAppApproval());
		mav.addObject("newFlag", true);
		mav.setViewName("labconstructappapproval/editLabConstructAppApproval.jsp");

		return mav;
	}

	/**
	 * Edit an existing User entity
	 * 
	 */
	@RequestMapping("/editLabConstructAppApprovalUser")
	public ModelAndView editLabConstructAppApprovalUser(@RequestParam Integer labconstructappapproval_id, @RequestParam String user_username) {
		User user = userDAO.findUserByPrimaryKey(user_username, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("labconstructappapproval_id", labconstructappapproval_id);
		mav.addObject("user", user);
		mav.setViewName("labconstructappapproval/user/editUser.jsp");

		return mav;
	}

	/**
	 * Entry point to show all LabConstructAppApproval entities
	 * 
	 */
	public String indexLabConstructAppApproval() {
		return "redirect:/indexLabConstructAppApproval";
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
	 * Show all LabConstructApp entities by LabConstructAppApproval
	 * 
	 */
	@RequestMapping("/listLabConstructAppApprovalLabConstructApp")
	public ModelAndView listLabConstructAppApprovalLabConstructApp(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructappapproval", labConstructAppApprovalDAO.findLabConstructAppApprovalByPrimaryKey(idKey));
		mav.setViewName("labconstructappapproval/labconstructapp/listLabConstructApp.jsp");

		return mav;
	}

	/**
	 */
	@RequestMapping("/labconstructappapprovalController/binary.action")
	public ModelAndView streamBinary(@ModelAttribute HttpServletRequest request, @ModelAttribute HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("streamedBinaryContentView");
		return mav;

	}

	/**
	 * Edit an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/editLabConstructAppApprovalLabConstructApp")
	public ModelAndView editLabConstructAppApprovalLabConstructApp(@RequestParam Integer labconstructappapproval_id, @RequestParam Integer labconstructapp_id) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("labconstructappapproval_id", labconstructappapproval_id);
		mav.addObject("labconstructapp", labconstructapp);
		mav.setViewName("labconstructappapproval/labconstructapp/editLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/saveLabConstructAppApprovalLabConstructApp")
	public ModelAndView saveLabConstructAppApprovalLabConstructApp(@RequestParam Integer labconstructappapproval_id, @ModelAttribute LabConstructApp labconstructapp) {
		LabConstructAppApproval parent_labconstructappapproval = labConstructAppApprovalService.saveLabConstructAppApprovalLabConstructApp(labconstructappapproval_id, labconstructapp);

		ModelAndView mav = new ModelAndView();
		mav.addObject("labconstructappapproval_id", labconstructappapproval_id);
		mav.addObject("labconstructappapproval", parent_labconstructappapproval);
		mav.setViewName("labconstructappapproval/viewLabConstructAppApproval.jsp");

		return mav;
	}

	/**
	 * Select the child LabConstructApp entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteLabConstructAppApprovalLabConstructApp")
	public ModelAndView confirmDeleteLabConstructAppApprovalLabConstructApp(@RequestParam Integer labconstructappapproval_id, @RequestParam Integer related_labconstructapp_id) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructapp", labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp_id));
		mav.addObject("labconstructappapproval_id", labconstructappapproval_id);
		mav.setViewName("labconstructappapproval/labconstructapp/deleteLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Select the LabConstructAppApproval entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteLabConstructAppApproval")
	public ModelAndView confirmDeleteLabConstructAppApproval(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructappapproval", labConstructAppApprovalDAO.findLabConstructAppApprovalByPrimaryKey(idKey));
		mav.setViewName("labconstructappapproval/deleteLabConstructAppApproval.jsp");

		return mav;
	}

	/**
	 * Save an existing User entity
	 * 
	 */
	@RequestMapping("/saveLabConstructAppApprovalUser")
	public ModelAndView saveLabConstructAppApprovalUser(@RequestParam Integer labconstructappapproval_id, @ModelAttribute User user) {
		LabConstructAppApproval parent_labconstructappapproval = labConstructAppApprovalService.saveLabConstructAppApprovalUser(labconstructappapproval_id, user);

		ModelAndView mav = new ModelAndView();
		mav.addObject("labconstructappapproval_id", labconstructappapproval_id);
		mav.addObject("labconstructappapproval", parent_labconstructappapproval);
		mav.setViewName("labconstructappapproval/viewLabConstructAppApproval.jsp");

		return mav;
	}

	/**
	 * Show all LabConstructAppApproval entities
	 * 
	 */
	@RequestMapping("/indexLabConstructAppApproval")
	public ModelAndView listLabConstructAppApprovals() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructappapprovals", labConstructAppApprovalService.loadLabConstructAppApprovals());

		mav.setViewName("labconstructappapproval/listLabConstructAppApprovals.jsp");

		return mav;
	}

	/**
	 * View an existing User entity
	 * 
	 */
	@RequestMapping("/selectLabConstructAppApprovalUser")
	public ModelAndView selectLabConstructAppApprovalUser(@RequestParam Integer labconstructappapproval_id, @RequestParam String user_username) {
		User user = userDAO.findUserByPrimaryKey(user_username, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("labconstructappapproval_id", labconstructappapproval_id);
		mav.addObject("user", user);
		mav.setViewName("labconstructappapproval/user/viewUser.jsp");

		return mav;
	}

	/**
	 * Delete an existing LabConstructAppApproval entity
	 * 
	 */
	@RequestMapping("/deleteLabConstructAppApproval")
	public String deleteLabConstructAppApproval(@RequestParam Integer idKey) {
		LabConstructAppApproval labconstructappapproval = labConstructAppApprovalDAO.findLabConstructAppApprovalByPrimaryKey(idKey);
		labConstructAppApprovalService.deleteLabConstructAppApproval(labconstructappapproval);
		return "forward:/indexLabConstructAppApproval";
	}

	/**
	 * Delete an existing User entity
	 * 
	 */
	@RequestMapping("/deleteLabConstructAppApprovalUser")
	public ModelAndView deleteLabConstructAppApprovalUser(@RequestParam Integer labconstructappapproval_id, @RequestParam String related_user_username) {
		ModelAndView mav = new ModelAndView();

		LabConstructAppApproval labconstructappapproval = labConstructAppApprovalService.deleteLabConstructAppApprovalUser(labconstructappapproval_id, related_user_username);

		mav.addObject("labconstructappapproval_id", labconstructappapproval_id);
		mav.addObject("labconstructappapproval", labconstructappapproval);
		mav.setViewName("labconstructappapproval/viewLabConstructAppApproval.jsp");

		return mav;
	}

	/**
	 * Show all User entities by LabConstructAppApproval
	 * 
	 */
	@RequestMapping("/listLabConstructAppApprovalUser")
	public ModelAndView listLabConstructAppApprovalUser(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructappapproval", labConstructAppApprovalDAO.findLabConstructAppApprovalByPrimaryKey(idKey));
		mav.setViewName("labconstructappapproval/user/listUser.jsp");

		return mav;
	}

	/**
	 * Save an existing LabConstructAppApproval entity
	 * 
	 */
	@RequestMapping("/saveLabConstructAppApproval")
	public String saveLabConstructAppApproval(@ModelAttribute LabConstructAppApproval labconstructappapproval) {
		labConstructAppApprovalService.saveLabConstructAppApproval(labconstructappapproval);
		return "forward:/indexLabConstructAppApproval";
	}

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/deleteLabConstructAppApprovalLabConstructApp")
	public ModelAndView deleteLabConstructAppApprovalLabConstructApp(@RequestParam Integer labconstructappapproval_id, @RequestParam Integer related_labconstructapp_id) {
		ModelAndView mav = new ModelAndView();

		LabConstructAppApproval labconstructappapproval = labConstructAppApprovalService.deleteLabConstructAppApprovalLabConstructApp(labconstructappapproval_id, related_labconstructapp_id);

		mav.addObject("labconstructappapproval_id", labconstructappapproval_id);
		mav.addObject("labconstructappapproval", labconstructappapproval);
		mav.setViewName("labconstructappapproval/viewLabConstructAppApproval.jsp");

		return mav;
	}

	/**
	 * Select the child User entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteLabConstructAppApprovalUser")
	public ModelAndView confirmDeleteLabConstructAppApprovalUser(@RequestParam Integer labconstructappapproval_id, @RequestParam String related_user_username) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("user", userDAO.findUserByPrimaryKey(related_user_username));
		mav.addObject("labconstructappapproval_id", labconstructappapproval_id);
		mav.setViewName("labconstructappapproval/user/deleteUser.jsp");

		return mav;
	}
}