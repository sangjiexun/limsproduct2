package net.zjcclims.service.system;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.zjcclims.service.common.ShareService;
import net.zjcclims.dao.AuthorityDAO;
import net.zjcclims.dao.TimetableGroupStudentsDAO;
import net.zjcclims.dao.UserDAO;
import net.zjcclims.domain.Authority;
import net.zjcclims.domain.SchoolAcademy;
import net.zjcclims.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


@Service("SystemAuthorityService")
public class SystemAuthorityServiceImpl implements SystemAuthorityService {
	/*@Autowired
	private AuthorityService authorityService;*/
	
	@Autowired
	private ShareService shareService;
	
	@Autowired
	private AuthorityDAO authorityDAO;
	
	@Autowired
	private UserDAO userDAO;
	

	@Transactional
	public List<showAcademyAuthority> getUserTotalRecords(String academyNumber, HttpServletRequest request){
		
		List<showAcademyAuthority> listNumber=new ArrayList<showAcademyAuthority>();
		//判断当前登录人的权限是否为实验中心主任、院系级系统管理员
		int isaut=0;
		String auth = request.getSession().getAttribute("selected_role").toString();
		if (auth.indexOf("ROLE_EXCENTERDIRECTOR") != -1 ||
				auth.indexOf("ROLE_ACADEMYLEVELM") != -1) {
			isaut = 1;
		}
		List<Authority> listAuthority = this.findAuthorityByUser(auth);
		for (Authority authority : listAuthority){
			String hql = "select count(distinct u) from User u join u.authorities a where enabled is true and a.id="+authority.getId();
			if (isaut==1) {//本学院
				if(academyNumber!=null&&!academyNumber.equals("")){
					hql+=" and u.schoolAcademy.academyNumber='"+academyNumber+"'";
				}
			}
			showAcademyAuthority newdate=new showAcademyAuthority();
			newdate.setAuthorityNumber(((Long)userDAO.createQuerySingleResult(hql).getSingleResult()).intValue());
			newdate.setAuthorityName(authority.getAuthorityName());
			newdate.setAuthorityCname(authority.getCname());
			newdate.setAuthorityId(authority.getId());
			listNumber.add(newdate);
		}
		
		return listNumber;
	}
	
	/***********************************************************************************************
	 * 根据user对象和权限id以及学院编号查询用户数量
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Transactional
	public int findUserByUser(User user,int Id,String academyNumber, HttpServletRequest request) {
		// TODO Auto-generated method stub
		String sql="select count(distinct u) from User u join u.authorities a where a.id="+Id;
		//判断当前登录人的权限是否为实验中心主任、院系级系统管理员
		int isaut=0;
		if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_EXCENTERDIRECTOR") != -1 ||
				request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_ACADEMYLEVELM") != -1) {
			isaut = 1;
		}
		if (isaut==1) {//本学院
			if(academyNumber!=null&&!academyNumber.equals("")){
				sql+=" and u.schoolAcademy.academyNumber='"+academyNumber+"'";
			}
		}
		if(user!=null){
			if(user.getCname()!=null&&!user.getCname().equalsIgnoreCase("")){
				sql+=" and u.cname like '%"+user.getCname()+"%'";
			}
		}
		sql += " and u.enabled is true";
		return ((Long)userDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	/***********************************************************************************************
	 * 根据user对象和权限id以及学院编号查询用户 并分页
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Transactional
	public List<User> findUserByUser(User user, int page,int pageSize,int Id,String academyNumber, HttpServletRequest request) {
		// TODO Auto-generated method stub
		String sql = "select distinct u from User u join u.authorities a where  a.id=" + Id;
		//判断当前登录人的权限是否为实验中心主任、院系级系统管理员
		int isaut = 0;
		if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_EXCENTERDIRECTOR") != -1 ||
				request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_ACADEMYLEVELM") != -1) {
			isaut = 1;
		}
		if (isaut == 1) {//本学院
			if (academyNumber != null && !academyNumber.equals("")) {
				sql += " and u.schoolAcademy.academyNumber='" + academyNumber + "'";
			}
		}
		if (user != null) {
			if (user.getCname() != null && !user.getCname().equalsIgnoreCase("")) {
				sql += " and u.cname like '%" + user.getCname() + "%'";
			}
		}
		sql += " and u.enabled is true";
		
		List<User> users=userDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
		return users;
	}
	
	@Transactional
	public void deleteUserAuthority(User user,Authority authority){
		user.getAuthorities().remove(authority);
		authorityDAO.store(authority);
		
		userDAO.store(user);	
	}
	
	@Transactional
	public void saveUserAuthority(User user,Authority authority){
		user.getAuthorities().add(authority);
		authorityDAO.store(authority);
		
		userDAO.store(user);	
		
	}
	
	@Transactional
	public List<User> findAllAcademyMap(int Id){
		String sql="select u from User u where u.schoolAcademy.academyNumber='"+shareService.getUser().getSchoolAcademy().getAcademyNumber()+"' and u.userStatus=1";

		return userDAO.executeQuery(sql,0,-1);
	}
	
	@Transactional
	public void saveUserAuth(String userId, int authorities) {
		
		Set<Authority> currentAuthorities=new HashSet<Authority>();
		
		User user=userDAO.findUserByPrimaryKey(userId);
		//用户已有的权限
		Set<Authority> historyAuthorities=user.getAuthorities();
					
		Authority authority=new Authority();
		if (authorities!=0)
		{
				authority=authorityDAO.findAuthorityById(authorities);
				currentAuthorities.add(authority);	
		}
		if(historyAuthorities.contains(authority)){//已经有的权限包含要增加的权限
			
		}else{
			historyAuthorities.add(authority);
			
		}
		
		user.setAuthorities(historyAuthorities);
		userDAO.store(user);
		
	}
	/***********************************************************************************************
	 * Description 根据当前权限查询可管理的权限列表
	 * @author 陈乐为 2018-11-27
	 ***********************************************************************************************/
	@Override
	public List<Authority> findAuthorityByUser(String auth) {
		int type = shareService.getLevelByAuthName(auth);

		String sql="select a from Authority a where 1=1 and (a.type=0 or a.type>="+type+")";//a.type<="+type;  贺子龙
		return authorityDAO.executeQuery(sql, 0,-1);
	}
	/***********************************************************************************************
	 * 根据权限id和用户名、工号查询可添加权限的用户数量
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public int findUserByCnameAndUsername(String cname, String username,String userRole,
			Integer authorityId, String academyNumber) {
//		if(userRole!=null&&!userRole.equals("")){
//			if(userRole.equals("s")){
//				userRole="0";			
//			}else if(userRole.equals("t")){
//				userRole="1";
//			}
//		}
		
		
		// TODO Auto-generated method stub
		String sql="select count(u) from User u where 1=1";
//		if (authorityId<=7||authorityId==13||authorityId==18) {//本学院
//			if(academyNumber!=null&&!academyNumber.equals("")){
//				sql+=" and u.schoolAcademy.academyNumber='"+academyNumber+"'";
//			}
//		}else {//校级
//		}
		if(cname!=null&&!cname.equals("")){
			sql+=" and u.cname like '%"+cname+"%'";
		}
		if(username!=null&&!username.equals("")){
			sql+=" and u.username like '%"+username+"%'";
		}
		if(userRole!=null&&!userRole.equals("")){
			sql+=" and u.userRole like '"+userRole+"'";
		}
		if(authorityId!=null&&authorityId!=0){
			sql+=" and u.username not in(select u.username from User u join u.authorities a where a.id="+authorityId+"))";
		}
		//sql+=" and u.userRole=1";
		//非12个学院的用户
		sql+=" or u.schoolAcademy.academyNumber not in(select s.academyNumber from  SchoolAcademy s where s.academyNumber like '02__')";
		return ((Long) userDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	/***********************************************************************************************
	 * 根据权限id和用户名、工号查询可添加权限的用户并分页
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public List<User> findUserByUserAndSchoolAcademy(String cname,
			String username,String userRole, Integer authorityId, String academyNumber,
			Integer page, int pageSize) {
		// TODO Auto-generated method stub
//		if(userRole!=null&&!userRole.equals("")){
//			if(userRole.equals("s")){
//				userRole="0";			
//			}else if(userRole.equals("t")){
//				userRole="1";
//			}
//		}
		String sql="select u from User u where 1=1";
//		if (authorityId<=7||authorityId==13||authorityId==18) {//本学院
//			if(academyNumber!=null&&!academyNumber.equals("")){
//				sql+=" and u.schoolAcademy.academyNumber='"+academyNumber+"'";
//			}
//		}else {//校级
//		}
		if(cname!=null&&!cname.equals("")){
			sql+=" and u.cname like '%"+cname+"%'";
		}
		if(username!=null&&!username.equals("")){
			sql+=" and u.username like '%"+username+"%'";
		}
		if(userRole!=null&&!userRole.equals("")){
			sql+=" and u.userRole like '"+userRole+"'";
		}
		if(authorityId!=null&&authorityId!=0){
			sql+=" and u.username not in(select u.username from User u join u.authorities a where a.id="+authorityId+"))";
		}
		//sql+=" and u.userRole=1";
		//非12个学院的用户
		sql+=" or u.schoolAcademy.academyNumber not in(select s.academyNumber from  SchoolAcademy s where s.academyNumber like '02__')";
		//System.out.println(sql);
		return userDAO.executeQuery(sql, (page-1)*pageSize,pageSize);
	}
	
	/**************************************************************************
	 * Description:系统管理-权限管理-根据user对象和权限id查询用户数量
	 * 
	 * @author：于侃
	 * @date ：2016-08-24
	 **************************************************************************/
	public int findUserByIdAndUser(User user,int Id) {
		// TODO Auto-generated method stub
		String sql="select count(distinct u) from User u join u.authorities a where a.id="+Id;
		if(user!=null){
			if(user.getCname()!=null&&!user.getCname().equalsIgnoreCase("")){
			
				sql+=" and u.cname like '%"+user.getCname()+"%'";
			}
		}
		return ((Long)userDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	
	/**************************************************************************
	 * Description:系统管理-权限管理-根据user对象和权限id查询用户 并分页
	 * 
	 * @author：于侃
	 * @date ：2016-08-24
	 **************************************************************************/
	public List<User> findUserByIdAndUser(User user, int page,int pageSize,int Id) {
		// TODO Auto-generated method stub
				String sql="select distinct u from User u join u.authorities a where  a.id="+Id;
				if(user!=null){
					if(user.getCname()!=null&&!user.getCname().equalsIgnoreCase("")){
					
						sql+=" and u.cname like '%"+user.getCname()+"%'";
					}
				}
		List<User> users=userDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
		return users;
	}

	/**************************************************************************
	 * Description:系统管理-权限管理-根据权限id和用户名、工号查询可添加权限的用户数量
	 * 
	 * @author：于侃
	 * @date ：2016-08-24
	 **************************************************************************/
	public int findUserByCnameAndUname(String cname, String username,Integer authorityId
			, String academyNumber) {
			String sql="select count(u) from User u where 1=1";
			if (authorityId<=7||authorityId==13||authorityId==18) {//本学院
				if(academyNumber!=null&&!academyNumber.equals("")){
					sql+=" and u.schoolAcademy.academyNumber='"+academyNumber+"'";
				}
			}else {//校级
			}
			if(cname!=null&&!cname.equals("")){
				sql+=" and u.cname like '%"+cname+"%'";
			}
			if(username!=null&&!username.equals("")){
				sql+=" and u.username like '%"+username+"%'";
			}
			if(authorityId!=null&&authorityId!=0){
				sql+=" and u.username not in(select u.username from User u join u.authorities a where a.id="+authorityId+"))";
			}
			sql+=" and u.userRole=1";
			//非12个学院的用户
			sql+=" or u.schoolAcademy.academyNumber not in(select s.academyNumber from  SchoolAcademy s where s.academyNumber like '02__')";
			return ((Long) userDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
		}
	
	/**************************************************************************
	 * Description:系统管理-权限管理-根据权限id和用户名、工号查询可添加权限的用户并分页
	 * 
	 * @author：于侃
	 * @date ：2016-08-24
	 **************************************************************************/
	public List<User> findUserByUserAndId(String cname,
			String username, Integer authorityId, String academyNumber,
			Integer page, int pageSize) {
		// TODO Auto-generated method stub
		
		String sql="select u from User u where 1=1";
		if (authorityId<=7||authorityId==13||authorityId==18) {//本学院
			if(academyNumber!=null&&!academyNumber.equals("")){
				sql+=" and u.schoolAcademy.academyNumber='"+academyNumber+"'";
			}
		}else {//校级
		}
		if(cname!=null&&!cname.equals("")){
			sql+=" and u.cname like '%"+cname+"%'";
		}
		if(username!=null&&!username.equals("")){
			sql+=" and u.username like '%"+username+"%'";
		}
		if(authorityId!=null&&authorityId!=0){
			sql+=" and u.username not in(select u.username from User u join u.authorities a where a.id="+authorityId+"))";
		}
		sql+=" and u.userRole=1";
		//非12个学院的用户
		sql+=" or u.schoolAcademy.academyNumber not in(select s.academyNumber from  SchoolAcademy s where s.academyNumber like '02__')";
		//System.out.println(sql);
		return userDAO.executeQuery(sql, (page-1)*pageSize,pageSize);
	}
}
