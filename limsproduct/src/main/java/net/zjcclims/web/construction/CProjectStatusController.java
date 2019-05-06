package net.zjcclims.web.construction;


import net.zjcclims.dao.CProjectStatusDAO;
import net.zjcclims.dao.ConstructionProjectDAO;
import net.zjcclims.service.construction.CProjectStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;

/**
 * Spring MVC controller that handles CRUD requests for CProjectStatus entities
 * 
 */

@Controller("CProjectStatusController")
public class CProjectStatusController {

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
	 * Service injected by Spring that provides CRUD operations for CProjectStatus entities
	 * 
	 */
	@Autowired
	private CProjectStatusService cProjectStatusService;

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