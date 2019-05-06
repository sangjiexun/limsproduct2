package net.zjcclims.service.construction;


import net.zjcclims.dao.CProjectBudgetDAO;
import net.zjcclims.dao.ProjectBudgetDAO;
import net.zjcclims.domain.CProjectBudget;
import net.zjcclims.domain.ProjectBudget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectBudget entities
 * 
 */

@Service("ProjectBudgetService")
@Transactional
public class ProjectBudgetServiceImpl implements ProjectBudgetService {

	/**
	 * DAO injected by Spring that manages CProjectBudget entities
	 * 
	 */
	@Autowired
	private CProjectBudgetDAO cProjectBudgetDAO;

	/**
	 * DAO injected by Spring that manages ProjectBudget entities
	 * 
	 */
	@Autowired
	private ProjectBudgetDAO projectBudgetDAO;

	/**
	 * Instantiates a new ProjectBudgetServiceImpl.
	 *
	 */
	public ProjectBudgetServiceImpl() {
	}

	/**
	 * Return all ProjectBudget entity
	 * 
	 */
	@Transactional
	public List<ProjectBudget> findAllProjectBudgets(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<ProjectBudget>(projectBudgetDAO.findAllProjectBudgets(startResult, maxRows));
	}

	/**
	 * Delete an existing CProjectBudget entity
	 * 
	 */
	@Transactional
	public ProjectBudget deleteProjectBudgetCProjectBudget(Integer projectbudget_id, Integer related_cprojectbudget_id) {
		ProjectBudget projectbudget = projectBudgetDAO.findProjectBudgetByPrimaryKey(projectbudget_id, -1, -1);
		CProjectBudget related_cprojectbudget = cProjectBudgetDAO.findCProjectBudgetByPrimaryKey(related_cprojectbudget_id, -1, -1);

		projectbudget.setCProjectBudget(null);
		related_cprojectbudget.getProjectBudgets().remove(projectbudget);
		projectbudget = projectBudgetDAO.store(projectbudget);
		projectBudgetDAO.flush();

		related_cprojectbudget = cProjectBudgetDAO.store(related_cprojectbudget);
		cProjectBudgetDAO.flush();

		cProjectBudgetDAO.remove(related_cprojectbudget);
		cProjectBudgetDAO.flush();

		return projectbudget;
	}

	/**
	 * Save an existing CProjectBudget entity
	 * 
	 */
	@Transactional
	public ProjectBudget saveProjectBudgetCProjectBudget(Integer id, CProjectBudget related_cprojectbudget) {
		ProjectBudget projectbudget = projectBudgetDAO.findProjectBudgetByPrimaryKey(id, -1, -1);
		CProjectBudget existingCProjectBudget = cProjectBudgetDAO.findCProjectBudgetByPrimaryKey(related_cprojectbudget.getId());

		// copy into the existing record to preserve existing relationships
		if (existingCProjectBudget != null) {
			existingCProjectBudget.setId(related_cprojectbudget.getId());
			existingCProjectBudget.setName(related_cprojectbudget.getName());
			related_cprojectbudget = existingCProjectBudget;
		} else {
			related_cprojectbudget = cProjectBudgetDAO.store(related_cprojectbudget);
			cProjectBudgetDAO.flush();
		}

		projectbudget.setCProjectBudget(related_cprojectbudget);
		related_cprojectbudget.getProjectBudgets().add(projectbudget);
		projectbudget = projectBudgetDAO.store(projectbudget);
		projectBudgetDAO.flush();

		related_cprojectbudget = cProjectBudgetDAO.store(related_cprojectbudget);
		cProjectBudgetDAO.flush();

		return projectbudget;
	}

	/**
	 * Load an existing ProjectBudget entity
	 * 
	 */
	@Transactional
	public Set<ProjectBudget> loadProjectBudgets() {
		return projectBudgetDAO.findAllProjectBudgets();
	}

	/**
	 * Save an existing ProjectBudget entity
	 * 
	 */
	@Transactional
	public void saveProjectBudget(ProjectBudget projectbudget) {
		ProjectBudget existingProjectBudget = projectBudgetDAO.findProjectBudgetByPrimaryKey(projectbudget.getId());

		if (existingProjectBudget != null) {
			if (existingProjectBudget != projectbudget) {
				existingProjectBudget.setId(projectbudget.getId());
				existingProjectBudget.setBudgetFee(projectbudget.getBudgetFee());
				existingProjectBudget.setUseFee(projectbudget.getUseFee());
			}
			projectbudget = projectBudgetDAO.store(existingProjectBudget);
		} else {
			projectbudget = projectBudgetDAO.store(projectbudget);
		}
		projectBudgetDAO.flush();
	}

	/**
	 * Return a count of all ProjectBudget entity
	 * 
	 */
	@Transactional
	public Integer countProjectBudgets() {
		return ((Long) projectBudgetDAO.createQuerySingleResult("select count(o) from ProjectBudget o").getSingleResult()).intValue();
	}

	/**
	 */
	@Transactional
	public ProjectBudget findProjectBudgetByPrimaryKey(Integer id) {
		return projectBudgetDAO.findProjectBudgetByPrimaryKey(id);
	}

	/**
	 * Delete an existing ProjectBudget entity
	 * 
	 */
	@Transactional
	public void deleteProjectBudget(ProjectBudget projectbudget) {
		projectBudgetDAO.remove(projectbudget);
		projectBudgetDAO.flush();
	}
}
