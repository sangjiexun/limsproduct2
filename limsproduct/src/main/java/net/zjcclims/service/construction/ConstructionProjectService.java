package net.zjcclims.service.construction;


import net.zjcclims.domain.*;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ConstructionProject entities
 * 
 */
public interface ConstructionProjectService {

	/**
	 * Delete an existing CProjectStatus entity
	 * 
	 */
	public ConstructionProject deleteConstructionProjectCProjectStatus(Integer constructionproject_id, Integer related_cprojectstatus_id);

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	public ConstructionProject deleteConstructionProjectLabConstructApp(Integer constructionproject_id_1, Integer related_labconstructapp_id);

	/**
	 * Load an existing ConstructionProject entity
	 * 
	 */
	public Set<ConstructionProject> loadConstructionProjects();

	/**
	 * Save an existing ConstructionProject entity
	 * 
	 */
	public void saveConstructionProject(ConstructionProject constructionproject);

	/**
	 * Return all ConstructionProject entity
	 * 
	 */
	public List<ConstructionProject> findAllConstructionProjects(Integer startResult, Integer maxRows);

	/**
	 * Delete an existing ConstructionProject entity
	 * 
	 */
	public void deleteConstructionProject(ConstructionProject constructionproject_1);

	/**
	 * Save an existing CProjectStatus entity
	 * 
	 */
	public ConstructionProject saveConstructionProjectCProjectStatus(Integer id, CProjectStatus related_cprojectstatus);

	/**
	 */
	public ConstructionProject findConstructionProjectByPrimaryKey(Integer id_1);

	/**
	 * Return a count of all ConstructionProject entity
	 * 
	 */
	public Integer countConstructionProjects();

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	public ConstructionProject saveConstructionProjectLabConstructApp(Integer id_2, LabConstructApp related_labconstructapp);
}