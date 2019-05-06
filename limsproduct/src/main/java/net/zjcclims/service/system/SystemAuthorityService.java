package net.zjcclims.service.system;

import java.util.List;

import net.zjcclims.domain.Authority;
import net.zjcclims.domain.User;

import javax.servlet.http.HttpServletRequest;

public interface SystemAuthorityService {
	/***********************************************************************************************
	 * 根据学院查询每个权限下的用户数量
	 * 作者：李小龙
	 ***********************************************************************************************/
	public List<showAcademyAuthority> getUserTotalRecords(String academyNumber, HttpServletRequest request);
	
	/***********************************************************************************************
	 * 根据user对象和权限id以及学院编号查询用户数量
	 * 作者：李小龙
	 ***********************************************************************************************/
	public int findUserByUser(User user,int Id,String academyNumber, HttpServletRequest request);
	/***********************************************************************************************
	 * 根据user对象和权限id以及学院编号查询用户 并分页
	 * 作者：李小龙
	 ***********************************************************************************************/
	public List<User> findUserByUser(User user, int page,int pageSize,int Id,String academyNumber, HttpServletRequest request);
	
	public void deleteUserAuthority(User user,Authority authority);
	
	public void saveUserAuthority(User user,Authority authority);
	
	public List<User> findAllAcademyMap(int Id);
	
	public void saveUserAuth(String userId, int authorities);
	/***********************************************************************************************
	 * Description 根据当前权限查询可管理的权限列表
	 * @author 陈乐为 2018-11-27
	 ***********************************************************************************************/
	public List<Authority> findAuthorityByUser(String auth);
	/***********************************************************************************************
	 * 根据权限id和用户名、工号查询可添加权限的用户数量
	 * 作者：李小龙
	 ***********************************************************************************************/
	public int findUserByCnameAndUsername(String cname, String username,String userRole,
			Integer authorityId, String academyNumber);
	/***********************************************************************************************
	 * 根据权限id和用户名、工号查询可添加权限的用户并分页
	 * 作者：李小龙
	 ***********************************************************************************************/
	public List<User> findUserByUserAndSchoolAcademy(String cname,
			String username,String userRole, Integer authorityId, String academyNumber,
			Integer page, int pageSize);
	
	/**************************************************************************
	 * Description:系统管理-权限管理-根据user对象和权限id查询用户数量
	 * 
	 * @author：于侃
	 * @date ：2016-08-24
	 **************************************************************************/
	public int findUserByIdAndUser(User user,int Id);
	
	/**************************************************************************
	 * Description:系统管理-权限管理-根据user对象和权限id查询用户 并分页
	 * 
	 * @author：于侃
	 * @date ：2016-08-24
	 **************************************************************************/
	public List<User> findUserByIdAndUser(User user, int page,int pageSize,int Id);
	
	/**************************************************************************
	 * Description:系统管理-权限管理-根据权限id和用户名、工号查询可添加权限的用户数量
	 * 
	 * @author：于侃
	 * @date ：2016-08-24
	 **************************************************************************/
	public int findUserByCnameAndUname(String cname, String username,Integer authorityId
			, String academyNumber);
	
	/**************************************************************************
	 * Description:系统管理-权限管理-根据权限id和用户名、工号查询可添加权限的用户并分页
	 * 
	 * @author：于侃
	 * @date ：2016-08-24
	 **************************************************************************/
	public List<User> findUserByUserAndId(String cname,
			String username, Integer authorityId, String academyNumber,
			Integer page, int pageSize);
}
