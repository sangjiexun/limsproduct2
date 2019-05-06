package net.zjcclims.web.construction;


import net.zjcclims.dao.LabConstructAppDAO;
import net.zjcclims.dao.LabConstructUserDAO;
import net.zjcclims.dao.UserDAO;
import net.zjcclims.domain.LabConstructApp;
import net.zjcclims.domain.LabConstructUser;
import net.zjcclims.domain.User;
import net.zjcclims.service.construction.LabConstructUserService;
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
 * Spring MVC controller that handles CRUD requests for LabConstructUser entities
 * 
 */

@Controller("LabConstructUserController")
public class LabConstructUserController {

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * DAO injected by Spring that manages LabConstructUser entities
	 * 
	 */
	@Autowired
	private LabConstructUserDAO labConstructUserDAO;

	/**
	 * DAO injected by Spring that manages User entities
	 * 
	 */
	@Autowired
	private UserDAO userDAO;

	/**
	 * Service injected by Spring that provides CRUD operations for LabConstructUser entities
	 * 
	 */
	@Autowired
	private LabConstructUserService labConstructUserService;

	/**
	 * Save an existing LabConstructUser entity
	 * 
	 */
	@RequestMapping("/saveLabConstructUser")
	public String saveLabConstructUser(@ModelAttribute LabConstructUser labconstructuser) {
		labConstructUserService.saveLabConstructUser(labconstructuser);
		return "forward:/indexLabConstructUser";
	}

	/**
	 */
	@RequestMapping("/labconstructuserController/binary.action")
	public ModelAndView streamBinary(@ModelAttribute HttpServletRequest request, @ModelAttribute HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("streamedBinaryContentView");
		return mav;

	}

	/**
	 * Create a new LabConstructApp entity
	 * 
	 */
	@RequestMapping("/newLabConstructUserLabConstructApp")
	public ModelAndView newLabConstructUserLabConstructApp(@RequestParam Integer labconstructuser_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("labconstructuser_id", labconstructuser_id);
		mav.addObject("labconstructapp", new LabConstructApp());
		mav.addObject("newFlag", true);
		mav.setViewName("labconstructuser/labconstructapp/editLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Create a new LabConstructUser entity
	 * 
	 */
	@RequestMapping("/newLabConstructUser")
	public ModelAndView newLabConstructUser() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructuser", new LabConstructUser());
		mav.addObject("newFlag", true);
		mav.setViewName("labconstructuser/editLabConstructUser.jsp");

		return mav;
	}

	/**
	 * Edit an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/editLabConstructUserLabConstructApp")
	public ModelAndView editLabConstructUserLabConstructApp(@RequestParam Integer labconstructuser_id, @RequestParam Integer labconstructapp_id) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("labconstructuser_id", labconstructuser_id);
		mav.addObject("labconstructapp", labconstructapp);
		mav.setViewName("labconstructuser/labconstructapp/editLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Select the child User entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteLabConstructUserUser")
	public ModelAndView confirmDeleteLabConstructUserUser(@RequestParam Integer labconstructuser_id, @RequestParam String related_user_username) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("user", userDAO.findUserByPrimaryKey(related_user_username));
		mav.addObject("labconstructuser_id", labconstructuser_id);
		mav.setViewName("labconstructuser/user/deleteUser.jsp");

		return mav;
	}

	/**
	 * Show all User entities by LabConstructUser
	 * 
	 */
	@RequestMapping("/listLabConstructUserUser")
	public ModelAndView listLabConstructUserUser(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructuser", labConstructUserDAO.findLabConstructUserByPrimaryKey(idKey));
		mav.setViewName("labconstructuser/user/listUser.jsp");

		return mav;
	}

	/**
	 * Select an existing LabConstructUser entity
	 * 
	 */
	@RequestMapping("/selectLabConstructUser")
	public ModelAndView selectLabConstructUser(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructuser", labConstructUserDAO.findLabConstructUserByPrimaryKey(idKey));
		mav.setViewName("labconstructuser/viewLabConstructUser.jsp");

		return mav;
	}

	/**
	 * Edit an existing User entity
	 * 
	 */
	@RequestMapping("/editLabConstructUserUser")
	public ModelAndView editLabConstructUserUser(@RequestParam Integer labconstructuser_id, @RequestParam String user_username) {
		User user = userDAO.findUserByPrimaryKey(user_username, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("labconstructuser_id", labconstructuser_id);
		mav.addObject("user", user);
		mav.setViewName("labconstructuser/user/editUser.jsp");

		return mav;
	}

	/**
	 * Delete an existing User entity
	 * 
	 */
	@RequestMapping("/deleteLabConstructUserUser")
	public ModelAndView deleteLabConstructUserUser(@RequestParam Integer labconstructuser_id, @RequestParam String related_user_username) {
		ModelAndView mav = new ModelAndView();

		LabConstructUser labconstructuser = labConstructUserService.deleteLabConstructUserUser(labconstructuser_id, related_user_username);

		mav.addObject("labconstructuser_id", labconstructuser_id);
		mav.addObject("labconstructuser", labconstructuser);
		mav.setViewName("labconstructuser/viewLabConstructUser.jsp");

		return mav;
	}

	/**
	 * View an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/selectLabConstructUserLabConstructApp")
	public ModelAndView selectLabConstructUserLabConstructApp(@RequestParam Integer labconstructuser_id, @RequestParam Integer labconstructapp_id) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("labconstructuser_id", labconstructuser_id);
		mav.addObject("labconstructapp", labconstructapp);
		mav.setViewName("labconstructuser/labconstructapp/viewLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/saveLabConstructUserLabConstructApp")
	public ModelAndView saveLabConstructUserLabConstructApp(@RequestParam Integer labconstructuser_id, @ModelAttribute LabConstructApp labconstructapp) {
		LabConstructUser parent_labconstructuser = labConstructUserService.saveLabConstructUserLabConstructApp(labconstructuser_id, labconstructapp);

		ModelAndView mav = new ModelAndView();
		mav.addObject("labconstructuser_id", labconstructuser_id);
		mav.addObject("labconstructuser", parent_labconstructuser);
		mav.setViewName("labconstructuser/viewLabConstructUser.jsp");

		return mav;
	}

	/**
	 * Edit an existing LabConstructUser entity
	 * 
	 */
	@RequestMapping("/editLabConstructUser")
	public ModelAndView editLabConstructUser(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructuser", labConstructUserDAO.findLabConstructUserByPrimaryKey(idKey));
		mav.setViewName("labconstructuser/editLabConstructUser.jsp");

		return mav;
	}

	/**
	 * Select the child LabConstructApp entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteLabConstructUserLabConstructApp")
	public ModelAndView confirmDeleteLabConstructUserLabConstructApp(@RequestParam Integer labconstructuser_id, @RequestParam Integer related_labconstructapp_id) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructapp", labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp_id));
		mav.addObject("labconstructuser_id", labconstructuser_id);
		mav.setViewName("labconstructuser/labconstructapp/deleteLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Show all LabConstructApp entities by LabConstructUser
	 * 
	 */
	@RequestMapping("/listLabConstructUserLabConstructApp")
	public ModelAndView listLabConstructUserLabConstructApp(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructuser", labConstructUserDAO.findLabConstructUserByPrimaryKey(idKey));
		mav.setViewName("labconstructuser/labconstructapp/listLabConstructApp.jsp");

		return mav;
	}

	/**
	 * Create a new User entity
	 * 
	 */
	@RequestMapping("/newLabConstructUserUser")
	public ModelAndView newLabConstructUserUser(@RequestParam Integer labconstructuser_id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("labconstructuser_id", labconstructuser_id);
		mav.addObject("user", new User());
		mav.addObject("newFlag", true);
		mav.setViewName("labconstructuser/user/editUser.jsp");

		return mav;
	}

	/**
	 * Save an existing User entity
	 * 
	 */
	@RequestMapping("/saveLabConstructUserUser")
	public ModelAndView saveLabConstructUserUser(@RequestParam Integer labconstructuser_id, @ModelAttribute User user) {
		LabConstructUser parent_labconstructuser = labConstructUserService.saveLabConstructUserUser(labconstructuser_id, user);

		ModelAndView mav = new ModelAndView();
		mav.addObject("labconstructuser_id", labconstructuser_id);
		mav.addObject("labconstructuser", parent_labconstructuser);
		mav.setViewName("labconstructuser/viewLabConstructUser.jsp");

		return mav;
	}

	/**
	 * View an existing User entity
	 * 
	 */
	@RequestMapping("/selectLabConstructUserUser")
	public ModelAndView selectLabConstructUserUser(@RequestParam Integer labconstructuser_id, @RequestParam String user_username) {
		User user = userDAO.findUserByPrimaryKey(user_username, -1, -1);

		ModelAndView mav = new ModelAndView();
		mav.addObject("labconstructuser_id", labconstructuser_id);
		mav.addObject("user", user);
		mav.setViewName("labconstructuser/user/viewUser.jsp");

		return mav;
	}

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@RequestMapping("/deleteLabConstructUserLabConstructApp")
	public ModelAndView deleteLabConstructUserLabConstructApp(@RequestParam Integer labconstructuser_id, @RequestParam Integer related_labconstructapp_id) {
		ModelAndView mav = new ModelAndView();

		LabConstructUser labconstructuser = labConstructUserService.deleteLabConstructUserLabConstructApp(labconstructuser_id, related_labconstructapp_id);

		mav.addObject("labconstructuser_id", labconstructuser_id);
		mav.addObject("labconstructuser", labconstructuser);
		mav.setViewName("labconstructuser/viewLabConstructUser.jsp");

		return mav;
	}

	/**
	 * Entry point to show all LabConstructUser entities
	 * 
	 */
	public String indexLabConstructUser() {
		return "redirect:/indexLabConstructUser";
	}

	/**
	 * Delete an existing LabConstructUser entity
	 * 
	 */
	@RequestMapping("/deleteLabConstructUser")
	public String deleteLabConstructUser(@RequestParam Integer idKey) {
		LabConstructUser labconstructuser = labConstructUserDAO.findLabConstructUserByPrimaryKey(idKey);
		labConstructUserService.deleteLabConstructUser(labconstructuser);
		return "forward:/indexLabConstructUser";
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
	 * Show all LabConstructUser entities
	 * 
	 */
	@RequestMapping("/indexLabConstructUser")
	public ModelAndView listLabConstructUsers() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructusers", labConstructUserService.loadLabConstructUsers());

		mav.setViewName("labconstructuser/listLabConstructUsers.jsp");

		return mav;
	}

	/**
	 * Select the LabConstructUser entity for display allowing the user to confirm that they would like to delete the entity
	 * 
	 */
	@RequestMapping("/confirmDeleteLabConstructUser")
	public ModelAndView confirmDeleteLabConstructUser(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("labconstructuser", labConstructUserDAO.findLabConstructUserByPrimaryKey(idKey));
		mav.setViewName("labconstructuser/deleteLabConstructUser.jsp");

		return mav;
	}
}