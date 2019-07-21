/****************************************************************************
 * 命名规范：
 * 本控制层调整必须是按照如下的路径调整：@RequestMapping("/device/system/sample")，全部小写。
 * 列表信息listxxxx，獲取信息：getxxxx 编辑信息：editxxxx 删除信息：detelexxxx 新增信息 newxxxx
 * 导出信息exportxxxx 保存信息：savexxxx 
 ****************************************************************************/

package net.zjcclims.web.system;

import java.text.ParseException;
import java.util.Map;
import java.util.Set;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import net.gvsun.lims.dto.common.PConfigDTO;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabCenterService;
//import net.zjcclims.service.system.TimeDetailService;
import net.zjcclims.service.system.User2DetailService;
import net.zjcclims.service.system.UserDetailService;

import net.zjcclims.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/****************************************************************************
 * 功能：系统后台管理模块 作者：魏诚 时间：2014-07-14
 ****************************************************************************/
@Controller("UsersController")
@SessionAttributes("selected_academy")
public class UsersController<JsonResult>
{
	/************************************************************
	 * @初始化WebDataBinder，这个WebDataBinder用于填充被@InitBinder注释的处理 方法的command和form对象
	 * 
	 ************************************************************/
	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request)
	{ // Register static property editors.
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
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	UserDetailService userDetailService;
	@Autowired(required = false)
	User2DetailService user2DetailService;
	@Autowired
	ShareService shareService;
	@Autowired
	LabCenterService labCenterService;
	@Autowired
	UserDAO userDAO;
	@Autowired
	SchoolAcademyDAO schoolAcademyDAO;
	@Autowired
	SchoolClassesDAO schoolClassesDAO;
	@Autowired
	private SchoolMajorDAO schoolMajorDAO;
	@Autowired
	private UserCardDAO userCardDAO;
	@Autowired private CommonServerDAO commonServerDAO;
	/************************************************************
	 * @内容：用户列表
	 * @作者：叶明盾
	 * @日期：2014-07-30
	 ************************************************************/
	@RequestMapping("/system/listUser")
	public ModelAndView listUser(HttpServletRequest request, @ModelAttribute User user,@RequestParam int currpage,@ModelAttribute("selected_academy") String acno)
	{
		ModelAndView mav = new ModelAndView();
		PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
		// 设置分页变量并赋值为20
		int pageSize = CommonConstantInterface.INT_PAGESIZE;
		if(request.getSession().getAttribute("selected_role").equals("ROLE_SUPERADMIN")){
			acno = null;
		}
		// 设置用户的总记录数并赋值
		int totalRecords = userDetailService.getUserTotalRecords(user,acno);

		Map<String, Integer> pageModel = shareService.getPage( currpage, pageSize,totalRecords);
		//
		mav.addObject("newFlag", true);
		//将pageModel映射到pageModel
		mav.addObject("pageModel", pageModel);//总页数
		//将currpage映射到page，用来获取当前页的页码
		mav.addObject("page", currpage);//方框中的页数
		//将totalRecords映射到totalRecords，用来获取总记录数
		mav.addObject("totalRecords", totalRecords);//总记录数
		mav.addObject("userOperation", pConfigDTO.userOperation);

		//获取用户列表
		mav.addObject("userMap", userDetailService.getUserTotalRecords(user,acno,currpage,pageSize));
		// 将该Model映射到listUser.jsp
		mav.setViewName("system/user/listUser.jsp");
		return mav;
	}
	@RequestMapping("/system/user/student")
	public ModelAndView student(@ModelAttribute User user,@RequestParam int currpage)
	{
		ModelAndView mav = new ModelAndView();
		PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
		// 设置分页变量并赋值为20
		int pageSize = CommonConstantInterface.INT_PAGESIZE;
		// 设置用户的总记录数并赋值
		int totalRecords = userDetailService.getUserTotalRecords("4444");
		Map<String, Integer> pageModel = shareService.getPage( currpage, pageSize,totalRecords);
		//
		mav.addObject("newFlag", true);
		//将pageModel映射到pageModel
		mav.addObject("pageModel", pageModel);//总页数
		//将currpage映射到page，用来获取当前页的页码
		mav.addObject("page", currpage);//方框中的页数
		//将totalRecords映射到totalRecords，用来获取总记录数
		mav.addObject("totalRecords", totalRecords);//总记录数
		mav.addObject("userOperation", pConfigDTO.userOperation);
		//获取用户列表
		mav.addObject("userMap", userDetailService.getUserTotalRecords(user,"4444",currpage,pageSize));
		// 将该Model映射到listUser.jsp
		mav.setViewName("system/user/student.jsp");
		return mav;

	}
	/************************************************************
	 * @内容：显示用户详细信息
	 * @作者：叶明盾
	 * @日期：2014-09-02
	 ************************************************************/
	@RequestMapping("/system/userDetailInfo")
	public ModelAndView userDetailInfo(@RequestParam String num)
	{
		ModelAndView mav=new ModelAndView();
		//将前端获取到的Username传递给后台
		mav.addObject("num", num);
		//根据用户的Username查找出用户
		mav.addObject("users", userDetailService.findUserByNum(num));
		//将该Model映射将该Model映射到userDetailInfo.jsp
		mav.setViewName("system/user/userDetailInfo.jsp");
		return mav;
	}
	@RequestMapping("/system/userAdd")
	public  ModelAndView userAdd(@RequestParam String num,@ModelAttribute User user,@RequestParam int currpage)
	{

		ModelAndView mav=new ModelAndView();
		PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
		// 设置分页变量并赋值为20
		int pageSize = CommonConstantInterface.INT_PAGESIZE;
		// 设置用户的总记录数并赋值
		int totalRecords = userDetailService.getUserTotalRecords("4444");
		Map<String, Integer> pageModel = shareService.getPage( currpage, pageSize,totalRecords);
		//
		mav.addObject("newFlag", true);
		//将pageModel映射到pageModel
		mav.addObject("pageModel", pageModel);//总页数
		//将currpage映射到page，用来获取当前页的页码
		mav.addObject("page", currpage);//方框中的页数
		//将totalRecords映射到totalRecords，用来获取总记录数
		mav.addObject("totalRecords", totalRecords);//总记录数
		mav.addObject("userOperation", pConfigDTO.userOperation);
		user.setUsername("1111116");
		user.setCname("梁正浩");
		user.setPassword("123456");
		SchoolAcademy a =schoolAcademyDAO.findSchoolAcademyByAcademyNumber("444");
		user.setSchoolAcademy(a);
		userDetailService.saveUser(user);
		//获取用户列表
		mav.addObject("userMap", userDetailService.getUserTotalRecords(null,"4444",currpage,pageSize));
		// 将该Model映射到listUser.jsp
		//将前端获取到的Username传递给后台
		mav.addObject("num", num);
		//根据用户的Username查找出用户
		mav.addObject("users", userDetailService.findUserByNum(num));
		//将该Model映射将该Model映射到userDetailInfo.jsp
		mav.setViewName("system/user/student.jsp");
		return mav;
	}
	/***********************************************************************************
	 * @description：系统管理--用户管理（导入用户）
	 * @author 郑昕茹
	 * @date：2016-12-19
	 * **********************************************************************************/
	@RequestMapping("/system/importUser")
	public ModelAndView importUser(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		String fileName = shareService.getUpdateFilePath(request);
		String logoRealPathDir = request.getSession().getServletContext().getRealPath("/");
		String filePath = logoRealPathDir + fileName;
		System.out.println(filePath);
		if(filePath.endsWith("xls")||filePath.endsWith("xlsx")){
			userDetailService.importUser(filePath);
		}
		mav.setViewName("redirect:/system/listUser?currpage=1");
		return mav;
	}
	
	/***********************************************************************************
	 * @description：系统管理--用户管理（导入用户前格式检查）
	 * @author 郑昕茹
	 * @日期：2016-08-04
	 * **********************************************************************************/
	@RequestMapping("/system/checkRegex")
	public @ResponseBody
	String checkRegex(HttpServletRequest request) throws ParseException {
		//获取文件地址
		String fileName=shareService.getUpdateFilePath(request);
		//获取服务器地址
		String logoRealPathDir = request.getSession().getServletContext().getRealPath("/");
		//获取文件全部地址
		String filePath=logoRealPathDir+fileName;
		return userDetailService.checkRegex(filePath);
	}
	
	
	/************************************************************
	 * @内容：用户列表
	 * @作者：叶明盾
	 * @日期：2014-07-30
	 ************************************************************/
	@RequestMapping("/system/deleteUser")
	public String deleteUser(@RequestParam String username)
	{
		User user = userDAO.findUserByPrimaryKey(username);
		if(user != null){
			user.setUserStatus("0");
			userDAO.store(user);
		}
		return "redirect:/system/user/listUser?currpage=1";
	
	}
	@RequestMapping("/system/deleteStudent")
	public String deleteStudent(@RequestParam String username)
	{
		User user = userDAO.findUserByPrimaryKey(username);
		if(user != null){
			user.setUserStatus("0");
			userDAO.store(user);
		}
		return "redirect:/system/user/student?currpage=1";

	}
	/**************************************************************************
	 * @Description 重置密码
	 * @author 张德冰
	 * @date 2018-09-27
	 ***************************************************************************/
	@RequestMapping("/system/resetPassword")
	public String resetPassword(@RequestParam String username)
	{
		User user = userDAO.findUserByPrimaryKey(username);
		if(user != null){
			//加密
			String password = shareService.createMD5(user.getUsername());
			user.setPassword(password);
			userDAO.store(user);
		}
		return "redirect:/system/listUser?currpage=1";

	}


	/**
	 * 功能：系统管理-班级管理入口
	 * 作者：姜新剑
	 * 日期：2016.1.12
	 */
	@RequestMapping("/system/listSchoolClasses")
	public ModelAndView listSchoolClasses(@ModelAttribute SchoolClasses schoolClasses,@RequestParam int currpage,String result ) {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		int pageSize=20;//每页20条记录
		//System.out.println(schoolClasses);
		//查询出来的总记录条数
		int totalRecords = userDetailService.findSchoolClasses(schoolClasses);
		//分页信息
		Map<String,Integer> pageModel =shareService.getPage(currpage, pageSize,totalRecords);
		//根据分页信息查询出来的记录
		List <Object[]> listSchoolClasses=userDetailService.findSchoolClasses(schoolClasses,currpage,pageSize);
		// 读取user表中的班级信息，把人数统计出来后赋值给classStudentsNumber字段。
//		for (SchoolClasses schoolClass : userDetailService.findSchoolClasses(schoolClasses)) {
//			String number=userDetailService.classesNumber(schoolClass.getClassNumber())+"";
//			schoolClass.setClassStudentsNumber(number);
//			schoolClassesDAO.store(schoolClass);
//		}
		mav.addObject("listSchoolClasses",listSchoolClasses);
		mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("currpage", currpage);
		//学院下拉框
		mav.addObject("academy",userDetailService.getAllSchoolAcademy());
		//班级下拉框
		mav.addObject("major",userDetailService.getAllSchoolMajor());
		mav.addObject("schoolClasses",schoolClasses);
		mav.addObject("pageSize", pageSize);
		mav.addObject("result", result);
		mav.setViewName("system/user/listAllSchoolClasses.jsp");
		return mav;
	}
	/**
	 * 功能：新建班级管理
	 * 作者：姜新剑
	 * 日期：2016.1.12
	 */
	@RequestMapping("/newSchoolClasses")
	public ModelAndView newSchoolClasses(){
		ModelAndView mav=new ModelAndView();
		mav.addObject("schoolClasses",new SchoolClasses());
		//学院下拉框
		mav.addObject("academy",userDetailService.getAllSchoolAcademy());
		//班级下拉框
		mav.addObject("major",userDetailService.getAllSchoolMajor());
		mav.setViewName("system/user/newSchoolClasses.jsp");
		return mav;
	}
	/**
	 * 功能：修改班级信息
	 * 作者：姜新剑
	 * 日期：2016.1.12
	 */
	@RequestMapping("/editSchoolClasses")
	public ModelAndView editSchoolClasses(@RequestParam String classNumber){
		ModelAndView mav =new ModelAndView();
		mav.addObject("schoolClasses",schoolClassesDAO.findSchoolClassesByClassNumber(classNumber));
		//学院下拉框
		mav.addObject("academy",userDetailService.getAllSchoolAcademy());
		//班级下拉框
		mav.addObject("major",userDetailService.getAllSchoolMajor());
		mav.setViewName("system/user/editSchoolClasses.jsp");
		return mav;
	}
	/**
	 * 功能：删除班级信息
	 * 作者：姜新剑
	 * 日期：2016.1.12
	 */
	@RequestMapping("/deleteSchoolClasses")
	public ModelAndView deleteSchoolClasses(@RequestParam String classNumber){
		ModelAndView mav=new ModelAndView();
		schoolClassesDAO.remove( schoolClassesDAO.findSchoolClassesByClassNumber(classNumber));
		mav.setViewName("redirect:/system/listSchoolClasses?currpage=1");
		return mav;
	}
	/**
	 * 功能：保存班级信息
	 * 作者：姜新剑
	 * 日期：2016.1.12
	 */
	@RequestMapping("/saveSchoolClasses")
	public String saveSchoolClasses(@ModelAttribute SchoolClasses schoolClasses){
		schoolClassesDAO.store(schoolClasses);
		return "redirect:/system/listSchoolClasses?currpage=1";
	}
	/**
	 * 功能：查看班级所在的学生
	 * 作者：姜新剑
	 * 日期：2016.1.12
	 */
	@RequestMapping("/numberSchoolClasses")
	public ModelAndView numberSchoolClasses(@RequestParam String classNumber){
		ModelAndView mav=new ModelAndView();
		mav.addObject("user", userDetailService.findUserByClassNumber(classNumber));
		mav.addObject("classNumber",classNumber);
		mav.setViewName("system/user/deleteSchoolClassesStudent.jsp");
		return mav;
	}
	/**
	 * 功能：批量添加学生页面
	 * 作者：廖文辉
	 * 日期：2018.8.30
	 */
	@RequestMapping("/addSchoolClassesStudent")
	public ModelAndView addSchoolClassesStudent(@RequestParam String classNumber,@RequestParam Integer currpage) {
		ModelAndView mav=new ModelAndView();
		int pageSize=20;//每页20条记录
		String academy = userDetailService.findUserAcadenybyClassNumber(classNumber);
		int totalRecords= userDetailService.findUserByOtherClassNumber(classNumber,academy,0,-1).size();
		mav.addObject("totalRecords",totalRecords);
		//分页信息
		Map<String,Integer> pageModel =shareService.getPage(currpage, pageSize,totalRecords);
		mav.addObject("pageModel",pageModel);
		mav.addObject("page",currpage);
		mav.addObject("pageSize",pageSize);
		mav.addObject("user", userDetailService.findUserByOtherClassNumber(classNumber,academy,currpage,pageSize));
		mav.addObject("classNumber",classNumber);
		mav.setViewName("system/user/addSchoolClassesStudent.jsp");
		return mav;
	}
	/***********************************************************
	 * 内容：导入班级
	 * 作者：裴继超
	 * 日期：2016年3月3日
	 ***********************************************************/
	@RequestMapping("/importClasses")
	public ModelAndView importClasses(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		//获取文件地址
		String fileName=shareService.getUpdateFilePath(request);
		//获取服务器地址
		String logoRealPathDir=request.getSession().getServletContext().getRealPath("/");
		//获取文件全部地址
		String filePath=logoRealPathDir+fileName;
		if(filePath.endsWith("xls")||filePath.endsWith("xlsx")){
			String result=userDetailService.importClasses(filePath);

			mav.addObject("result", result);
		}
		mav.setViewName("redirect:/system/listSchoolClasses?currpage=1");
		return mav;
	}
	/**
	 * 功能：批量删除班级学生
	 * 作者：姜新剑
	 * 日期：2016.1.14
	 */
	@RequestMapping("/deleteSchoolClassesStudent")
	public String deleteSchoolClassesStudent(@RequestParam String scrapAppIds) {
		String[] ids = scrapAppIds.split(",");
		String classNumber=userDetailService.findUserByUsername(ids[0]).getSchoolClasses().getClassNumber();
		//System.out.println("---------");
		//System.out.println(ids);
		for(int i=0;i<=ids.length-1;i++){
			User user=userDetailService.findUserByUsername(ids[i]);
			SchoolClasses schoolClasses=new SchoolClasses();
			schoolClasses.setClassNumber(null);
			schoolClasses=null;
			user.setSchoolClasses(schoolClasses);
			userDetailService.storeUser(user);
		}
		return "redirect:/numberSchoolClasses?classNumber="+classNumber;
	}
	/**
	 * 功能：批量增加班级学生
	 * 作者：廖文辉
	 * 日期：2018.9.11
	 */
	@RequestMapping("/addSchoolClassesStudents")
	public String addSchoolClassesStudents(@RequestParam String scrapAppIds,@RequestParam String classNumber) {
		String[] ids = scrapAppIds.split(",");
		/**//*String classNumber=userDetailService.findUserByUsername(ids[0]).getSchoolClasses().getClassNumber();*/
		//System.out.println("---------");
		//System.out.println(ids);
		for(int i=0;i<=ids.length-1;i++){
			User user=userDetailService.findUserByUsername(ids[i]);
			SchoolClasses schoolClasses=schoolClassesDAO.findSchoolClassesByClassNumber(classNumber);
			/*schoolClasses.setClassNumber(null);*/
			user.setSchoolClasses(schoolClasses);
			userDetailService.storeUser(user);
		}
		return "redirect:/addSchoolClassesStudent?classNumber="+classNumber+"&currpage=1";
	}
	/**
	 * 功能：卡号绑定
	 * 作者：廖文辉
	 * 日期：2018.9.3
	 */
	@RequestMapping("/system/cardBlind")
	public ModelAndView cardBlind(@RequestParam String username){
		ModelAndView mav = new ModelAndView();
		User user =userDAO.findUserByUsername(username);
		mav.addObject("users",user);
		mav.addObject("userCards",userDetailService.findCardNoByUserName(username));
		mav.setViewName("system/user/cardBlind.jsp");
		return mav;
	}

	/**
	 * Description 保存卡号，并更新物联相关数据
	 * @param username 用户名
	 * @param cardno 修改后的卡号
	 * @param cardId user_card的id
	 * @return
	 * @author 廖文辉 2018.9.3
	 * @author 陈乐为 2019-7-11 修改
	 */
	@RequestMapping("/system/saveCardBlind")
	@ResponseBody
	public String saveCardBlind(@RequestParam String username,String cardno, Integer cardId){
		// 旧卡号
		String old_card_no = "";
		String old_acl_card = "";
		// 新卡号
		String new_card_no = "";
		String new_acl_card = "";
		UserCard userCard = userCardDAO.findUserCardById(cardId);
		if (userCard != null) {
			// 保存前取到旧卡号
			String oldSQL = "select * from view_user where id = " + cardId;
			Query query = entityManager.createNativeQuery(oldSQL);
			List<Object[]> list = query.getResultList();
			for (Object[] obj : list) {
				if (obj[1] != null && obj[1] != "" && obj[3] != null && obj[3] != "") {
					old_card_no = obj[1].toString();
					old_acl_card = obj[3].toString();
					break;
				}
			}
			// 保存对象
			userCard.setCardNo(cardno);
			userCard.setEnabled(String.valueOf(1));
			userCardDAO.store(userCard);
			userCardDAO.flush();
			// 保存后，获取新卡号
			String newSQL = "select * from view_user where id = " + cardId;
			Query newQuery = entityManager.createNativeQuery(newSQL);
			List<Object[]> newList = newQuery.getResultList();
			for (Object[] obj : newList) {
				if (obj[1] != null && obj[1] != "" && obj[3] != null && obj[3] != "") {
					new_card_no = obj[1].toString();
					new_acl_card = obj[3].toString();
					break;
				}
			}
			// 同步卡号
			Set<CommonServer> commonServers = commonServerDAO.findAllCommonServers();
			for (CommonServer server : commonServers) {
				if (server.getServerIp() != null && new_acl_card != "" && old_acl_card != "") {
					String url = "http://" + server.getServerIp() + ":" + server.getServerSn() + "/iot/acldoor/" + username + "/changecard/" + old_acl_card + "/" + new_acl_card;
					String s = HttpClientUtil.doPost(url);
				}
				if (server.getServerIp() != null && new_card_no != "" && old_card_no != "") {
					String url = "http://" + server.getServerIp() + ":" + server.getServerSn() + "/iot/guard/" + username + "/changecard/" + old_card_no + "/" + new_card_no;
					String s = HttpClientUtil.doPost(url);
				}
			}
		}
		return "success";
	}

}