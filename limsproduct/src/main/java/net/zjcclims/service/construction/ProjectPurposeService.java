package net.zjcclims.service.construction;

import net.zjcclims.domain.*;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectPurpose entities
 * 
 */
public interface ProjectPurposeService {

	/**
	 * Return a count of all ProjectPurpose entity
	 * 
	 */
	public Integer countProjectPurposes();

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	public ProjectPurpose deleteProjectPurposeLabConstructApp(Integer projectpurpose_id, Integer related_labconstructapp_id);

	/**
	 * Return all ProjectPurpose entity
	 * 
	 */
	public List<ProjectPurpose> findAllProjectPurposes(Integer startResult, Integer maxRows);

	/**
	 * Load an existing ProjectPurpose entity
	 * 
	 */
	public Set<ProjectPurpose> loadProjectPurposes();

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	public ProjectPurpose saveProjectPurposeLabConstructApp(Integer id, LabConstructApp related_labconstructapp);

	/**
	 * Delete an existing ProjectPurpose entity
	 * 
	 */
	public void deleteProjectPurpose(ProjectPurpose projectpurpose);

	/**
	 */
	public ProjectPurpose findProjectPurposeByPrimaryKey(Integer id_1);

	/**
	 * Save an existing ProjectPurpose entity
	 * 
	 */
	public void saveProjectPurpose(ProjectPurpose projectpurpose_1);

	/**
	 * Save an existing CProjectPurpose entity
	 * 
	 */
	public ProjectPurpose saveProjectPurposeCProjectPurpose(Integer id_2, CProjectPurpose related_cprojectpurpose);

	/**
	 * Delete an existing CProjectPurpose entity
	 * 
	 */
	public ProjectPurpose deleteProjectPurposeCProjectPurpose(Integer projectpurpose_id_1, Integer related_cprojectpurpose_id);
}