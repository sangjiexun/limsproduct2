/****************************************************************************
 * 命名规范：
 * 本控制层调整必须是按照如下的路径调整：@RequestMapping("/device/system/sample")，全部小写。
 * 列表信息listxxxx，獲取信息：getxxxx 编辑信息：editxxxx 删除信息：detelexxxx 新增信息 newxxxx
 * 导出信息exportxxxx 保存信息：savexxxx 
 ****************************************************************************/

package net.zjcclims.web.common;

import net.gvsun.lims.dto.user.AuthorityDTO;
import net.sf.json.JSONObject;
import net.zjcclims.dao.AuthorityDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.dictionary.CDictionaryService;
import net.zjcclims.service.lab.LabCenterService;
import net.zjcclims.service.message.MessageService;
import net.zjcclims.service.systemMenu.SystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/****************************************************************************
 * 功能：系统后台管理模块 作者：魏诚 时间：2014-07-14
 ****************************************************************************/
@Controller("CommonController")
@SessionAttributes("selected_academy")
public class CommonController<JsonResult> {
	
	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register
																				// static
																				// property
																				// editors.
		binder.registerCustomEditor(java.util.Calendar.class,
				new org.skyway.spring.util.databinding.CustomCalendarEditor());
		binder.registerCustomEditor(byte[].class,
				new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
		binder.registerCustomEditor(java.math.BigDecimal.class,
				new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
		binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
				Integer.class, true));
		binder.registerCustomEditor(java.util.Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
		binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
		binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
				Long.class, true));
		binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
				Double.class, true));
	}

	@Autowired
	private ShareService shareService;
	@Autowired
	private LabCenterService labCenterService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private AuthorityDAO authorityDAO;
	@Autowired
	private PConfig pConfig;
	@Autowired
	private CDictionaryService cDictionaryService;
	@Autowired
	private SystemMenuService systemMenuService;

	@RequestMapping("/schoolSelect")
	public ModelAndView schoolSelect(HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		List<CDictionary> list = cDictionaryService.findAllSchool();
		mav.addObject("list",list);
		mav.setViewName("schoolSelect.jsp");
		return mav;
	}
	@RequestMapping("/home")
	public ModelAndView index(HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("PROJECT_NAME",pConfig.PROJECT_NAME);
		mav.setViewName("cms/indexZjcc.jsp");
		// 判断是否登录
		boolean needLogin = true;
		if (!EmptyUtil.isObjectEmpty(shareService.getUser())) {
			needLogin = false;
			mav.addObject("username", shareService.getUser().getCname());
		}
		mav.addObject("needLogin", needLogin);
		return mav;
	}
	/****************************************************************************
	 * 功能：系统默认的url 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/test")
	public ModelAndView test(HttpSession session, ModelMap modelMap, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("PROJECT_NAME",pConfig.PROJECT_NAME);

		mav.addObject("annexManage",pConfig.annexManage);
		mav.addObject("softManage",pConfig.softManage);
		mav.addObject("baseManage",pConfig.baseManage);
		mav.addObject("jobReservation", pConfig.jobReservation);
		mav.addObject("deviceLend", pConfig.deviceLend);
		mav.addObject("stationNum",pConfig.stationNum);
		mav.addObject("userOperation",pConfig.userOperation);
		mav.addObject("showroom",pConfig.showroom);
		mav.addObject("yuntai",pConfig.yuntai);
		mav.addObject("labAddAdim",pConfig.labAddAdim);
		// 学校代码
		mav.addObject("schoolCode", pConfig.schoolCode);
		mav.addObject("cmsAccess", pConfig.cmsAccess);
		session.setAttribute("cmsUrl", pConfig.cmsUrl);
		session.setAttribute("cmsSiteUrl", pConfig.cmsSiteUrl);
		session.setAttribute("backToCms", pConfig.backToCms);
		session.setAttribute("cms",pConfig.cms);
		session.setAttribute("PROJECT_NAME",pConfig.PROJECT_NAME);


		// 当前登录人
		User user = shareService.getUser();
		mav.addObject("user", user);
		String acno;
		// 默认学院
		if(user.getSchoolAcademy()!=null && user.getSchoolAcademy().getAcademyNumber()!=null) {
			modelMap.addAttribute("selected_academy", user.getSchoolAcademy().getAcademyNumber());
			mav.addObject("academy", user.getSchoolAcademy());
			acno = user.getSchoolAcademy().getAcademyNumber();
		}else {
			modelMap.addAttribute("selected_academy", "-1");
			acno = "-1";
		}
		//权限管理
		String authority = "";
		int i = 0;
		int id = 1;
		Set<Authority> auths = user.getAuthorities();//得到登陆者所拥有的所有权限
		for (Authority a : auths) {
			if (a.getType() >= i) {
				authority = a.getCname();
				i = a.getType();
				id=a.getId();
			}
		} 
		mav.addObject("authority", authority);
		Cookie[] cookies = request.getCookies(); //获取cookie数组
		if(cookies != null) {
			int isuser = 0;
			for (Cookie cookie : cookies) {//遍历cookie数组
				if(cookie.getName().equals("username")) {
					if(cookie.getValue().equals(user.getUsername())){
						isuser = 1;
						break;
					}
				}
			}
			if(isuser == 1) {
				for (Cookie cookie : cookies) {//遍历cookie数组
					if (cookie.getName().equals("authorityId")) {
						session.setAttribute("authorityName", authorityDAO.findAuthorityById(Integer.parseInt(cookie.getValue())).getCname());
						session.setAttribute("selected_role", "ROLE_" + authorityDAO.findAuthorityById(Integer.parseInt(cookie.getValue())).getAuthorityName());
						break;
					}
				}
			}
		}
//		if (session.getAttribute("authorityName") == null && auths.toString().contains("SUPERADMIN")) {
//			changeRole("SUPERADMIN");
//			session.setAttribute("authorityName", "系统管理员");
//			session.setAttribute("selected_role", "ROLE_SUPERADMIN");
//		} else if (session.getAttribute("authorityName") == null) {
//			changeRole(authorityDAO.findAuthorityById(id).getAuthorityName());
//			session.setAttribute("authorityName", authorityDAO.findAuthorityById(id).getCname());
//			session.setAttribute("selected_role", "ROLE_" + authorityDAO.findAuthorityById(id).getAuthorityName());
//		}
		/*
		 * 角色判断：如果具有老师权限则默认为老师，如果没有教师权限则默认为学生
		*/
//		if(session.getAttribute("selected_role")!=null) {
////			// 切换角色后不需要重置权限
////		}else if(auths.toString().contains("TEACHER")){
////			session.setAttribute("selected_role", "ROLE_TEACHER");
////		}else if(auths.toString().contains("STUDENT")){
////			session.setAttribute("selected_role", "ROLE_STUDENT");
////		}else if(auths.toString().contains("SUPERADMIN")){
////			session.setAttribute("selected_role", "ROLE_SUPERADMIN");
////		}
        //用户多个身份登录的角色优先级顺序：院系级管理员（实验中心主任）、实验室管理员、教师（学生）
		if(session.getAttribute("selected_role")!=null) {
			// 切换角色后不需要重置权限
		}else if(auths.toString().contains("ACADEMYLEVELM")){
            session.setAttribute("authorityName","院系级系统管理员");
            session.setAttribute("selected_role", "ROLE_ACADEMYLEVELM");
        }else if(auths.toString().contains("EXCENTERDIRECTOR")){
            session.setAttribute("authorityName","实验中心主任");
            session.setAttribute("selected_role", "ROLE_EXCENTERDIRECTOR");
        }else if(auths.toString().contains("LABMANAGER")){
            session.setAttribute("authorityName","实验室管理员");
            session.setAttribute("selected_role", "ROLE_LABMANAGER");
        }else if(auths.toString().contains("TEACHER")){
            session.setAttribute("authorityName","教师");
            session.setAttribute("selected_role", "ROLE_TEACHER");
        }else if(auths.toString().contains("STUDENT")){
            session.setAttribute("authorityName","学生");
			session.setAttribute("selected_role", "ROLE_STUDENT");
		}

		if (session.getAttribute("authorityName") == null) {
			changeRole(authorityDAO.findAuthorityById(id).getAuthorityName());
			session.setAttribute("authorityName", authorityDAO.findAuthorityById(id).getCname());
			session.setAttribute("selected_role", "ROLE_" + authorityDAO.findAuthorityById(id).getAuthorityName());
		}
        String sss = session.getAttribute("selected_role").toString();
		
		//将当前登录人放到session中
		session.setAttribute("loginUser", user);
		session.setAttribute("messageNum", messageService.countmessage( ));

		// 权限等级
		// 获取当前系统权限
		String auth = session.getAttribute("selected_role").toString();
		// 获取权限英文
		auth = auth.split("_")[1];
		// 根据名称查找对象
		Authority autho = authorityDAO.findAuthorityByName(auth);
		// 返回权限等级
		session.setAttribute("auth_level", autho.getType());

		// 菜单权限
		acno = auth.equals("SUPERADMIN")?"-1":acno;
		List<SystemMenu> myParentNode = systemMenuService.getSystemMenuByNoParent(auth, acno);
		Map<String, List<SystemMenu>> myChildrenNode = systemMenuService.getSystemMenuByParent1(auth, acno);
		if(myParentNode.size() == 0){
			myParentNode = systemMenuService.getSystemMenuByNoParent(auth, "-1");
			myChildrenNode = systemMenuService.getSystemMenuByParent1(auth, "-1");
		}
		mav.addObject("myParentNode", myParentNode);
		mav.addObject("myChildrenNode", myChildrenNode);

		mav.setViewName("system/test.jsp");
		return mav;
	}

	/****************************************************************************
	 * 功能：系统默认的url 作者：李小龙
     * update Hezhaoyi 2019-6-24
	 ****************************************************************************/
	@RequestMapping("/testSecurity")
	public ModelAndView testSecurity(HttpSession session, ModelMap modelMap, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("PROJECT_NAME",pConfig.PROJECT_NAME);

		mav.addObject("annexManage",pConfig.annexManage);
		mav.addObject("softManage",pConfig.softManage);
		mav.addObject("baseManage",pConfig.baseManage);
		mav.addObject("jobReservation", pConfig.jobReservation);
		mav.addObject("deviceLend", pConfig.deviceLend);
		mav.addObject("stationNum",pConfig.stationNum);
		mav.addObject("userOperation",pConfig.userOperation);
		mav.addObject("showroom",pConfig.showroom);
		mav.addObject("yuntai",pConfig.yuntai);
		mav.addObject("labAddAdim",pConfig.labAddAdim);
		// 学校代码
		mav.addObject("schoolCode", pConfig.schoolCode);
		mav.addObject("cmsAccess", pConfig.cmsAccess);
		session.setAttribute("cmsUrl", pConfig.cmsUrl);
		session.setAttribute("cmsSiteUrl", pConfig.cmsSiteUrl);
		session.setAttribute("backToCms", pConfig.backToCms);
		session.setAttribute("cms",pConfig.cms);
		session.setAttribute("PROJECT_NAME",pConfig.PROJECT_NAME);

//
//		// 当前登录人
		User user = shareService.getUser();
		mav.addObject("user", user);
		String acno;
		// 默认学院
		if(user.getSchoolAcademy()!=null && user.getSchoolAcademy().getAcademyNumber()!=null) {
			modelMap.addAttribute("selected_academy", user.getSchoolAcademy().getAcademyNumber());
			mav.addObject("academy", user.getSchoolAcademy());
			acno = user.getSchoolAcademy().getAcademyNumber();
		}else {
			modelMap.addAttribute("selected_academy", "-1");
			acno = "-1";
		}
//		//权限管理
		String authority = "";
		int i = 0;
		int id = 1;
		Set<Authority> auths = user.getAuthorities();//得到登陆者所拥有的所有权限
		for (Authority a : auths) {
			if (a.getType() >= i) {
				authority = a.getCname();
				i = a.getType();
				id=a.getId();
			}
		}
//		mav.addObject("authority", authority);
		Cookie[] cookies = request.getCookies(); //获取cookie数组
		if(cookies != null) {
			int isuser = 0;
			for (Cookie cookie : cookies) {//遍历cookie数组
				if(cookie.getName().equals("username")) {
					if(cookie.getValue().equals(user.getUsername())){
						isuser = 1;
						break;
					}
				}
			}
			if(isuser == 1) {
				for (Cookie cookie : cookies) {//遍历cookie数组
					if (cookie.getName().equals("authorityId")) {
						session.setAttribute("authorityName", authorityDAO.findAuthorityById(Integer.parseInt(cookie.getValue())).getCname());
						session.setAttribute("selected_role", "ROLE_" + authorityDAO.findAuthorityById(Integer.parseInt(cookie.getValue())).getAuthorityName());
						break;
					}
				}
			}
		}
		//用户多个身份登录的角色优先级顺序：院系级管理员（实验中心主任）、实验室管理员、教师（学生）
		if(session.getAttribute("selected_role")!=null) {
			// 切换角色后不需要重置权限
		}else if(auths.toString().contains("ACADEMYLEVELM")){
			session.setAttribute("authorityName","院系级系统管理员");
			session.setAttribute("selected_role", "ROLE_ACADEMYLEVELM");
		}else if(auths.toString().contains("EXCENTERDIRECTOR")){
			session.setAttribute("authorityName","实验中心主任");
			session.setAttribute("selected_role", "ROLE_EXCENTERDIRECTOR");
		}else if(auths.toString().contains("LABMANAGER")){
			session.setAttribute("authorityName","实验室管理员");
			session.setAttribute("selected_role", "ROLE_LABMANAGER");
		}else if(auths.toString().contains("TEACHER")){
			session.setAttribute("authorityName","教师");
			session.setAttribute("selected_role", "ROLE_TEACHER");
		}else if(auths.toString().contains("STUDENT")){
			session.setAttribute("authorityName","学生");
			session.setAttribute("selected_role", "ROLE_STUDENT");
		}

		if (session.getAttribute("authorityName") == null) {
			changeRole(authorityDAO.findAuthorityById(id).getAuthorityName());
			session.setAttribute("authorityName", authorityDAO.findAuthorityById(id).getCname());
			session.setAttribute("selected_role", "ROLE_" + authorityDAO.findAuthorityById(id).getAuthorityName());
		}
		String sss = session.getAttribute("selected_role").toString();

		//将当前登录人放到session中
		session.setAttribute("loginUser", user);
		session.setAttribute("messageNum", messageService.countmessage( ));

		// 权限等级
		// 获取当前系统权限
		String auth = session.getAttribute("selected_role").toString();
		// 获取权限英文
		auth = auth.split("_")[1];
		// 根据名称查找对象
		Authority autho = authorityDAO.findAuthorityByName(auth);
		// 返回权限等级
		session.setAttribute("auth_level", autho.getType());

		//菜单权限
		acno = auth.equals("SUPERADMIN")?"-1":acno;
		List<SystemMenu> myParentNode = systemMenuService.getSystemMenuByNoParent(auth, acno);
		Map<String, List<SystemMenu>> myChildrenNode = systemMenuService.getSystemMenuByParent1(auth, acno);
		if(myParentNode.size() == 0){
			myParentNode = systemMenuService.getSystemMenuByNoParent(auth, "-1");
			myChildrenNode = systemMenuService.getSystemMenuByParent1(auth, "-1");
		}
		mav.addObject("myParentNode", myParentNode);
		mav.addObject("myChildrenNode", myChildrenNode);

        mav.setViewName("redirect:/pages/security/dist/views/index.html");
        return mav;
	}
	/****************************************************************************
	 * 功能：选择前往的中心 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/checkCenter")
	public ModelAndView checkCenter(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		User user = shareService.getUser();  // 当前登录人

		List<LabCenter> centers = new ArrayList<LabCenter>();
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") != -1 || 
				SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SCHOOLLEADER") != -1
				) //当用户为校领导、超级管理员时，可以查看所有中心
		{
			centers = labCenterService.findAllLabCenterByQuery(request, new LabCenter(), 1, -1);
		} 
		else 
		{
			SchoolAcademy academy = user.getSchoolAcademy();  // 所属学院
			if (academy != null) 
			{
				centers.addAll(academy.getLabCenters());  // 所属学院下的中心
			}
			LabCenter str = new LabCenter();
			str.setStatus(1);
			List<LabCenter> labCenters = labCenterService.findAllLabCenterByQuery(request, str, 1, -1);
			centers.addAll(labCenters);
		}

		mav.addObject("user", user);
		mav.addObject("centers", centers);
		mav.setViewName("system/checkCenter.jsp");
		return mav;
	}

	/****************************************************************************
	 * 功能：开发人员选择前往的登录页面 作者：魏诚
	 ****************************************************************************/
	@RequestMapping("/system/developer")
	public ModelAndView developer() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("system/developer.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * @功能：登陆成功默认的url 
	 * @作者：黄崔俊
	 * @时间：2015-11-12 14:29:16
	 ****************************************************************************/
	@RequestMapping("/login")
	public ModelAndView test(HttpServletRequest request,HttpSession httpSession) {
		ModelAndView mav = new ModelAndView();
		
		// 当前登录人
		User user = shareService.getUser();
		/*
		 * 角色判断：如果具有老师权限则默认为老师，如果没有教师权限则默认为学生
		*/
		if(request.getSession().getAttribute("selected_role")!=null){
			// 切换角色以后不需要再重置权限
		}else if(user.getAuthorities().toString().contains("SUPERADMIN")){
			request.getSession().setAttribute("selected_role", "ROLE_SUPERADMIN");
		}else if(user.getAuthorities().toString().contains("TEACHER")){
			request.getSession().setAttribute("selected_role", "ROLE_TEACHER");
		}else if(user.getAuthorities().toString().contains("STUDENT")){
			request.getSession().setAttribute("selected_role", "ROLE_STUDENT");
		}else if(user.getAuthorities().toString().contains("FAMILYMEMBER")){
			request.getSession().setAttribute("selected_role", "ROLE_FAMILYMEMBER");
		}
		
		httpSession.setAttribute("loginUser", user);
		mav.addObject("user", user);
		mav.setViewName("redirect:/test");
		return mav;
	}
	/**
	 * Description 切换角色
	 * @param username
	 * @return
	 * @author zhangyu
	 * @date 2017-12-25
	 */
	@RequestMapping("/common/changeUserRolePage")
	public ModelAndView changeUserRolePage(@RequestParam("username") String username) {
		
		ModelAndView mav = new ModelAndView();
		User user = shareService.findUserByUsername(username);
		mav.addObject("user", user);
		
		Set<Authority> authorities = user.getAuthorities();
		List<AuthorityDTO> authorityDTOs = new ArrayList<AuthorityDTO>();
		for(Authority authority:authorities){
			AuthorityDTO authorityDTO = new AuthorityDTO();
			authorityDTO.setCname(authority.getCname());
			authorityDTO.setAuthorityName(authority.getAuthorityName());
			authorityDTOs.add(authorityDTO);
		}
		mav.addObject("allAuthoritys", authorityDTOs);
		
		mav.setViewName("user/selAuthority.jsp");
		
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("/common/changeUserRole")
	public void changeUserRole(@RequestParam("auth") String authorityName, HttpServletRequest request, HttpServletResponse response) {
		changeRole(authorityName);
		Authority authority = authorityDAO.findAuthorityByName(authorityName);
		request.getSession().setAttribute("authorityName", authority.getCname());
		request.getSession().setAttribute("selected_role", "ROLE_"+authority.getAuthorityName());
		Cookie authorityId= new Cookie("authorityId",authority.getId().toString());//将切换的角色权限id加入cookie中
		authorityId.setPath("/");
		response.addCookie(authorityId);//将cookie返回加入
		User user = shareService.getUserDetail();
		Cookie username= new Cookie("username",user.getUsername());//将当前登录人的username加入cookie中
		username.setPath("/");
		response.addCookie(username);
	}
	
	private void changeRole(final String authorityName) {

		final SecurityContext context = SecurityContextHolder.getContext();

		final Object credentials = context.getAuthentication().getCredentials();
		final Object details = context.getAuthentication().getDetails();
		final Object principal = context.getAuthentication().getPrincipal();
		final String name = context.getAuthentication().getName();

		Authentication authentication = new Authentication() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getName() {
				return name;
			}

			@Override
			public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
			}

			@Override
			public boolean isAuthenticated() {
				return true;
			}

			@Override
			public Object getPrincipal() {
				return principal;
			}

			@Override
			public Object getDetails() {
				return details;
			}

			@Override
			public Object getCredentials() {
				return credentials;
			}

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				List<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();
				authorities.add(new SimpleGrantedAuthority("ROLE_"+authorityName));
				return authorities;
			}
		};

		context.setAuthentication(authentication);
	}

	//重定向
	@RequestMapping("/redirect")
	public ModelAndView redirect(HttpServletRequest request){
		String name=shareService.getUser().getUsername();
		ModelAndView mav = new ModelAndView();
		String add=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		String pathPj = pConfig.PROJECT_NAME;
		mav.setViewName("redirect:"+add+"/"+pathPj+"cms/cmsAdmin?name="+name);

		return mav;
	}

	/****************************************************************************
	 * 功能：返回登录状态：汪哲玮
	 ****************************************************************************/

	@RequestMapping(value="/checkLogin",produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	public @ResponseBody String checkLogin(){
		JSONObject aapJson = new JSONObject();
		String backend=null;

		String authority=null;
		if(shareService.getUser()!=null){
			backend=shareService.getUser().getUsername();
			//获得用户最大的权限
			if(shareService.getUser().getAuthorities().contains(authorityDAO.findAuthorityById(11))){
				authority="超级管理员";
			}else if(shareService.getUser().getAuthorities().contains(authorityDAO.findAuthorityById(2))){
				authority="教师";
			}else if(shareService.getUser().getAuthorities().contains(authorityDAO.findAuthorityById(1))){
				authority="学生";
			}
		}
		aapJson.put("backend",backend );
		aapJson.put("authority",authority );
		String item = "successCallback("+aapJson.toString()+")";
		return item;
	}
}