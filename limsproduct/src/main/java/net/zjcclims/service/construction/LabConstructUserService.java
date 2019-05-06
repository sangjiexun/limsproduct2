package net.zjcclims.service.construction;


import net.zjcclims.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for LabConstructUser entities
 * 
 */
public interface LabConstructUserService {

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	public LabConstructUser saveLabConstructUserLabConstructApp(Integer id, LabConstructApp related_labconstructapp);

	/**
	 * Return all LabConstructUser entity
	 * 
	 */
	public List<LabConstructUser> findAllLabConstructUsers(Integer startResult, Integer maxRows);

	/**
	 * Save an existing User entity
	 * 
	 */
	public LabConstructUser saveLabConstructUserUser(Integer id_1, User related_user);

	/**
	 * Save an existing LabConstructUser entity
	 * 
	 */
	public void saveLabConstructUser(LabConstructUser labconstructuser);

	/**
	 */
	public LabConstructUser findLabConstructUserByPrimaryKey(Integer id_2);

	/**
	 * Load an existing LabConstructUser entity
	 * 
	 */
	public Set<LabConstructUser> loadLabConstructUsers();

	/**
	 * Delete an existing LabConstructUser entity
	 * 
	 */
	public void deleteLabConstructUser(LabConstructUser labconstructuser_1);

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	public LabConstructUser deleteLabConstructUserLabConstructApp(Integer labconstructuser_id, Integer related_labconstructapp_id);

	/**
	 * Delete an existing User entity
	 * 
	 */
	public LabConstructUser deleteLabConstructUserUser(Integer labconstructuser_id_1, String related_user_username);

	/**
	 * Return a count of all LabConstructUser entity
	 * 
	 */
	public Integer countLabConstructUsers();
	
	/***************************************************************************************
	 * 功能：实验室建设项目-实验室建设项目参加人员
	 * 作者：李德
	 * 时间：2015-04-03
	 **************************************************************************************/
	@Transactional
	public List<LabConstructUser> findLabConstructUserByAppKey(Integer id);
}