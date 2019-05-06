package net.zjcclims.service.construction;


import net.zjcclims.domain.LabConstructApp;
import net.zjcclims.domain.ProjectAppMajor;
import net.zjcclims.domain.SchoolMajor;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectAppMajor entities
 * 
 */
public interface ProjectAppMajorService {

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	public ProjectAppMajor saveProjectAppMajorLabConstructApp(Integer id, LabConstructApp related_labconstructapp);

	/**
	 * Return all ProjectAppMajor entity
	 * 
	 */
	public List<ProjectAppMajor> findAllProjectAppMajors(Integer startResult, Integer maxRows);

	/**
	 * Delete an existing ProjectAppMajor entity
	 * 
	 */
	public void deleteProjectAppMajor(ProjectAppMajor projectappmajor);

	/**
	 * Load an existing ProjectAppMajor entity
	 * 
	 */
	public Set<ProjectAppMajor> loadProjectAppMajors();

	/**
	 */
	public ProjectAppMajor findProjectAppMajorByPrimaryKey(Integer id_1);

	/**
	 * Delete an existing SchoolMajor entity
	 * 
	 */
	public ProjectAppMajor deleteProjectAppMajorSchoolMajor(Integer projectappmajor_id, String related_schoolmajor_majorNumber);

	/**
	 * Save an existing SchoolMajor entity
	 * 
	 */
	public ProjectAppMajor saveProjectAppMajorSchoolMajor(Integer id_2, SchoolMajor related_schoolmajor);

	/**
	 * Save an existing ProjectAppMajor entity
	 * 
	 */
	public void saveProjectAppMajor(ProjectAppMajor projectappmajor_1);

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	public ProjectAppMajor deleteProjectAppMajorLabConstructApp(Integer projectappmajor_id_1, Integer related_labconstructapp_id);

	/**
	 * Return a count of all ProjectAppMajor entity
	 * 
	 */
	public Integer countProjectAppMajors();
}