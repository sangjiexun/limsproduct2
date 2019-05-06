package net.zjcclims.service.cms;

import net.zjcclims.dao.UserDAO;

import net.zjcclims.domain.User;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

/**
 * Spring service that handles CRUD requests for User entities
 * 
 */

@Service("CmsSystemService")
@Transactional
public class CmsSystemServiceImpl implements CmsSystemService {

	@Autowired
	private UserDAO userDAO;

	@Override
	public  Set<User>  loadUsers(){
		return userDAO.findAllUsers();
	}

	@Override		
	public void saveUser(User user){
		user = userDAO.store(user);
		userDAO.flush();				
	} 

	@Override
	public void deleteUser(String userid){
		userDAO.remove(userDAO.findUserByPrimaryKey(userid));
		userDAO.flush();
	}

}
