package net.zjcclims.service.construction;


import net.zjcclims.domain.CProjectBudget;
import net.zjcclims.domain.ProjectBudget;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectBudget entities
 * 
 */
public interface ProjectBudgetService {

	/**
	 * Return all ProjectBudget entity
	 * 
	 */
	public List<ProjectBudget> findAllProjectBudgets(Integer startResult, Integer maxRows);

	/**
	 * Delete an existing CProjectBudget entity
	 * 
	 */
	public ProjectBudget deleteProjectBudgetCProjectBudget(Integer projectbudget_id, Integer related_cprojectbudget_id);

	/**
	 * Save an existing CProjectBudget entity
	 * 
	 */
	public ProjectBudget saveProjectBudgetCProjectBudget(Integer id, CProjectBudget related_cprojectbudget);

	/**
	 * Load an existing ProjectBudget entity
	 * 
	 */
	public Set<ProjectBudget> loadProjectBudgets();

	/**
	 * Save an existing ProjectBudget entity
	 * 
	 */
	public void saveProjectBudget(ProjectBudget projectbudget);

	/**
	 * Return a count of all ProjectBudget entity
	 * 
	 */
	public Integer countProjectBudgets();

	/**
	 */
	public ProjectBudget findProjectBudgetByPrimaryKey(Integer id_1);

	/**
	 * Delete an existing ProjectBudget entity
	 * 
	 */
	public void deleteProjectBudget(ProjectBudget projectbudget_1);
}