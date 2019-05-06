package net.zjcclims.service.construction;



import net.zjcclims.domain.ProjectStartUser;
import net.zjcclims.domain.ProjectStartedReport;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectStartUser entities
 * 
 */
public interface ProjectStartUserService {

	/**
	 * Delete an existing ProjectStartUser entity
	 * 
	 */
	public void deleteProjectStartUser(ProjectStartUser projectstartuser);

	/**
	 * Delete an existing ProjectStartedReport entity
	 * 
	 */
	public ProjectStartUser deleteProjectStartUserProjectStartedReport(Integer projectstartuser_id, Integer related_projectstartedreport_id);

	/**
	 * Save an existing ProjectStartUser entity
	 * 
	 */
	public void saveProjectStartUser(ProjectStartUser projectstartuser_1);

	/**
	 * Return a count of all ProjectStartUser entity
	 * 
	 */
	public Integer countProjectStartUsers();

	/**
	 */
	public ProjectStartUser findProjectStartUserByPrimaryKey(Integer id);

	/**
	 * Return all ProjectStartUser entity
	 * 
	 */
	public List<ProjectStartUser> findAllProjectStartUsers(Integer startResult, Integer maxRows);

	/**
	 * Save an existing ProjectStartedReport entity
	 * 
	 */
	public ProjectStartUser saveProjectStartUserProjectStartedReport(Integer id_1, ProjectStartedReport related_projectstartedreport);

	/**
	 * Load an existing ProjectStartUser entity
	 * 
	 */
	public Set<ProjectStartUser> loadProjectStartUsers();
}