package net.zjcclims.service.construction;


import net.zjcclims.domain.ProjectAcceptUser;
import net.zjcclims.domain.ProjectAcceptanceApplication;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectAcceptUser entities
 * 
 */
public interface ProjectAcceptUserService {

	/**
	 * Delete an existing ProjectAcceptanceApplication entity
	 * 
	 */
	public ProjectAcceptUser deleteProjectAcceptUserProjectAcceptanceApplication(Integer projectacceptuser_id, Integer related_projectacceptanceapplication_id);

	/**
	 * Save an existing ProjectAcceptanceApplication entity
	 * 
	 */
	public ProjectAcceptUser saveProjectAcceptUserProjectAcceptanceApplication(Integer id, ProjectAcceptanceApplication related_projectacceptanceapplication);

	/**
	 * Return all ProjectAcceptUser entity
	 * 
	 */
	public List<ProjectAcceptUser> findAllProjectAcceptUsers(Integer startResult, Integer maxRows);

	/**
	 * Save an existing ProjectAcceptUser entity
	 * 
	 */
	public void saveProjectAcceptUser(ProjectAcceptUser projectacceptuser);

	/**
	 * Return a count of all ProjectAcceptUser entity
	 * 
	 */
	public Integer countProjectAcceptUsers();

	/**
	 * Load an existing ProjectAcceptUser entity
	 * 
	 */
	public Set<ProjectAcceptUser> loadProjectAcceptUsers();

	/**
	 */
	public ProjectAcceptUser findProjectAcceptUserByPrimaryKey(Integer id_1);

	/**
	 * Delete an existing ProjectAcceptUser entity
	 * 
	 */
	public void deleteProjectAcceptUser(ProjectAcceptUser projectacceptuser_1);
}