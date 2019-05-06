package net.zjcclims.service.cms;

import net.zjcclims.domain.User;

import java.util.Set;

/**
 * Spring service that handles CRUD requests for User entities
 * 
 */
public interface CmsSystemService {

	/**
	 * 功能:找到所有的用户
	 */	
	public  Set<User>  loadUsers();

	/**
	 * 功能:保存用户
	 */	
	public void saveUser(User user);
	
	/**
	 * 功能:删除一个用户。
	 */		
	public void deleteUser(String userid);
	

}