package net.zjcclims.web.system;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zjcclims.dao.AuthorityDAO;
import net.zjcclims.dao.UserDAO;
import net.zjcclims.domain.Authority;
import net.zjcclims.domain.SchoolAcademy;
import net.zjcclims.domain.SystemBuild;
import net.zjcclims.domain.User;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabCenterService;
import net.zjcclims.service.system.SystemAuthorityService;
import net.zjcclims.service.system.showAcademyAuthority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller("SystemAuthorityController")
@SessionAttributes("selected_academy")
public class SystemAuthorityController<JsonResult>{

	@Autowired
	private SystemAuthorityService systemAuthorityService;
	
	@Autowired
	private ShareService shareService;
	
	@Autowired
	private AuthorityDAO authorityDAO;
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private LabCenterService labCenterService;
	
	/************************************************************
	 * @权限管理模块入口
	 * @作者：喻泉声
	 * @日期：2014-09-4
	 ************************************************************/
	@RequestMapping("/userAuthorityMange/listUserAuthority")
	public ModelAndView listUserAuthority(@ModelAttribute("selected_academy") String acno, HttpSession session, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		/*//当前登录人
		User user=shareService.getUser();

		//判断当前登陆人是否为项目立项的审核人或者超级管理员
		String judge=",";
		for(Authority authority:user.getAuthorities()){
			judge = judge + "," + authority.getId() + "," ;
		}
		boolean isEXCENTERDIRECTOR;//中心主任
		boolean isSUPERADMIN;//超级管理员
		if(judge.indexOf(",11,")>-1){
			isSUPERADMIN = true;
		}else isSUPERADMIN = false;
		if(judge.indexOf(",4,")>-1){
			isEXCENTERDIRECTOR = true;
		}else isEXCENTERDIRECTOR = false;
		mav.addObject("isEXCENTERDIRECTOR", isEXCENTERDIRECTOR);
		mav.addObject("isSUPERADMIN", isSUPERADMIN);*/

        String role = (String)session.getAttribute("selected_role");
        List<showAcademyAuthority>  AllUserAuthority=systemAuthorityService.getUserTotalRecords(acno,request);
        if(role.equals("ROLE_SUPERADMIN")){
            mav.addObject("AllUserAuthority",AllUserAuthority);
        }else if(role.equals("ROLE_EXCENTERDIRECTOR")){
            List<showAcademyAuthority>  userAuthorityForEXCENTERDIRECTOR=new ArrayList<showAcademyAuthority>();
            for (showAcademyAuthority showAcademyAuthority : AllUserAuthority) {
                if (showAcademyAuthority.getAuthorityId()<=7
                        ||showAcademyAuthority.getAuthorityId()==13
                        ||showAcademyAuthority.getAuthorityId()==18) {
                    userAuthorityForEXCENTERDIRECTOR.add(showAcademyAuthority);
                }else {

                }
            }
            mav.addObject("AllUserAuthority",userAuthorityForEXCENTERDIRECTOR);
        }

		mav.setViewName("/system/userAuthorityMange/listUserAuthority.jsp");
		return mav;
	}
	
	/************************************************************
	 * @根据权限id查看该权限下的人数
	 * @作者：喻泉声
	 * @日期：2014-09-4
	 ************************************************************/
	@RequestMapping("/userAuthorityMange/listUserAuthorityDetail")
	public ModelAndView showAcademyAuthority(@ModelAttribute User user,@RequestParam int page,int Id,@ModelAttribute("selected_academy") String acno, HttpServletRequest request) {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		
		//查询表单的对象
		mav.addObject("user",user);

		Authority authority = authorityDAO.findAuthorityById(Id);
		mav.addObject("authority",authority);
		//判断当前登录人的权限是否为实验中心主任、院系级系统管理员
		int isaut=0;
		if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_EXCENTERDIRECTOR") != -1 ||
				request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_ACADEMYLEVELM") != -1) {
			//教务处、设备科、超管
			if (authority.getAuthorityName().equals("DEAN") || authority.getAuthorityName().equals("ASSETMANAGER") || authority.getAuthorityName().equals("SUPERADMIN")) {
				isaut = 1;
			}
		}
		//实验室管理员、设备管理员、研究生、学生、教师这五个权限人员列表只能查看不能添加、删除
		if (authority.getAuthorityName().equals("LABMANAGER") || authority.getAuthorityName().equals("EQUIPMENTADMIN") || authority.getAuthorityName().equals("GRADUATE") ||
				authority.getAuthorityName().equals("STUDENT") || authority.getAuthorityName().equals("TEACHER")) {
			isaut = 1;
		}
		mav.addObject("isaut", isaut);

		//中心所属学院
		String academyNumber=shareService.getUser().getSchoolAcademy().getAcademyNumber();
		mav.addObject("academyNumber", academyNumber);
		int pageSize=10;//每页20条记录
		//查询出来的总记录条数
		int totalRecords=systemAuthorityService.findUserByUser(user,Id,academyNumber,request);
		//分页信息
		Map<String,Integer> pageModel =shareService.getPage( page,pageSize,totalRecords);
		//根据分页信息查询出来的记录
		List<User> listUser=systemAuthorityService.findUserByUser(user,page,pageSize,Id,academyNumber,request);
		mav.addObject("listUser",listUser);
		mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", page);
		mav.addObject("pageSize", pageSize);
		
		mav.addObject("Id",Id);
		//所有部门
		Set<SchoolAcademy> academys=shareService.getAllSchoolAcademy();
		mav.addObject("academys", academys);
		mav.setViewName("/system/userAuthorityMange/listUserAuthorityDetail.jsp");

	   	return mav;
		
	}
	
	/************************************************************
	 * @删除权限管理模块信息
	 * @作者：喻泉声
	 * @日期：2014-09-4
	 ************************************************************/
	@RequestMapping("/userAuthorityMange/deleteUserAuthority")
	public String deleteUserAuthority(String username,int Id,int page){
		User users=userDAO.findUserByPrimaryKey(username);
		Authority authoritys=authorityDAO.findAuthorityById(Id);
		
		systemAuthorityService.deleteUserAuthority(users,authoritys);
		
		return "redirect:/userAuthorityMange/listUserAuthorityDetail?page="+page+"&Id="+Id;
	}
	
	/************************************************************
	 * @新建权限管理模块信息
	 * @作者：喻泉声
	 * @日期：2014-09-4
	 ************************************************************/
	@RequestMapping("/userAuthorityMange/newUserAuthority")
	public ModelAndView newUserAuthority(int Id,int page){
		ModelAndView mav=new ModelAndView();
		
		mav.addObject("listUser", new User());
		
		mav.addObject("Id", Id);
		mav.addObject("page", page);
		mav.addObject("authority",authorityDAO.findAuthorityById(Id));
		
		mav.addObject("userMap",systemAuthorityService.findAllAcademyMap(Id));
		
		mav.setViewName("/system/userAuthorityMange/newUserAuthority.jsp");

	   	return mav;
	}
	
	/****************************************************************************
	 * 功能：AJAX 根据姓名、工号查询当前所在学院的用户以及不属于12个学院的用户
	 * 作者：李小龙
	 * @throws UnsupportedEncodingException 
	 ****************************************************************************/
	@RequestMapping("/userAuthorityMange/findUserByCnameAndUsername")
	public @ResponseBody String findUserByCnameAndUsername(@RequestParam String cname, String username, String userRole, String academy, Integer authorityId, Integer page,HttpServletRequest request,@ModelAttribute("selected_academy") String acno) throws UnsupportedEncodingException {
		//当前所在中心的学院
		//String academyNumber=labCenterService.findLabCenterByPrimaryKey(cid).getSchoolAcademy().getAcademyNumber();
		String academyNumber=shareService.getUser().getSchoolAcademy().getAcademyNumber();
		String academyForSearch="";
		if (academy!=null&&!academy.equals("")) {
			academyForSearch=academy;
		}else {
			academyForSearch=academyNumber;
		}
		/*cname=URLDecoder.decode(cname,"utf-8");*/
		//分页开始
		int totalRecords=systemAuthorityService.findUserByCnameAndUsername(cname,username,userRole,authorityId,academyForSearch);
		
		int pageSize=20;
		Map<String,Integer> pageModel =shareService.getPage( page,pageSize,totalRecords);
		//根据分页信息查询出来的记录
		List<User> userList=systemAuthorityService.findUserByUserAndSchoolAcademy(cname,username,userRole,authorityId,academyForSearch,page,pageSize);
	    String s="";
	    for (User d : userList) {
	    	String acd="";
	    	if(d.getSchoolAcademy()!=null){
	    		acd=d.getSchoolAcademy().getAcademyName();
	    	}
			s+="<tr>"+
	    	"<td>"+d.getCname()+"</td>"+
			"<td>"+d.getUsername()+"</td>"+
			"<td>"+acd+"</td>"+
			"<td><input type='checkbox' name='CK_name' value='"+d.getUsername()+"'/></td>"+
			"</tr>";			
		}
	    s+="<tr><td colspan='6'>"+
	    	    "<a href='#' onclick='firstPage(1);'>"+"首页"+"</a>&nbsp;"+
	    	    "<a href='#' onclick='previousPage("+page+");'>"+"上一页"+"</a>&nbsp;"+
	    	    "<a href='#' onclick='nextPage("+page+","+pageModel.get("totalPage")+");'>"+"下一页"+"</a>&nbsp;"+
	    	    "<a href='#' onclick='lastPage("+pageModel.get("totalPage")+");'>"+"末页"+"</a>&nbsp;"+
	    	    "当前第"+page+"页&nbsp; 共"+pageModel.get("totalPage")+"页  "+totalRecords+"条记录"+
	    	    		"</td></tr>";
		return shareService.htmlEncode(s);
	}
	/****************************************************************************
	 * 功能：权限下的用户
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/userAuthorityMange/saveUserAuthority")
	public ModelAndView saveUserAuthority(@RequestParam Integer authorityId,String[] array) {
		ModelAndView mav=new ModelAndView();
		//authorityId对应的权限
		Authority a=authorityDAO.findAuthorityByPrimaryKey(authorityId);
		for (String i : array) {
	  		//username对应的用户
			User u=userDAO.findUserByPrimaryKey(i);
			Set<Authority> authoritys=u.getAuthorities();
			authoritys.add(a);
			u.getAuthorities().addAll(authoritys);
			userDAO.store(u);
	  	}		
		
		mav.setViewName("redirect:/userAuthorityMange/listUserAuthorityDetail?page=1&Id="+authorityId);
		return mav;
	}
	
}
