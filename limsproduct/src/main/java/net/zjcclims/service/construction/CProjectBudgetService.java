package net.zjcclims.service.construction;



import net.zjcclims.domain.CProjectBudget;
import net.zjcclims.domain.ProjectBudget;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for CProjectBudget entities
 * 
 */
public interface CProjectBudgetService {

	/**
	 * Delete an existing ProjectBudget entity
	 * 
	 */
	public CProjectBudget deleteCProjectBudgetProjectBudgets(Integer cprojectbudget_id, Integer related_projectbudgets_id);

	/**
	 * Return all CProjectBudget entity
	 * 
	 */
	public List<CProjectBudget> findAllCProjectBudgets(Integer startResult, Integer maxRows);

	/**
	 * Delete an existing CProjectBudget entity
	 * 
	 */
	public void deleteCProjectBudget(CProjectBudget cprojectbudget);

	/**
	 * Load an existing CProjectBudget entity
	 * 
	 */
	public Set<CProjectBudget> loadCProjectBudgets();

	/**
	 * Save an existing CProjectBudget entity
	 * 
	 */
	public void saveCProjectBudget(CProjectBudget cprojectbudget_1);

	/**
	 * Return a count of all CProjectBudget entity
	 * 
	 */
	public Integer countCProjectBudgets();

	/**
	 */
	public CProjectBudget findCProjectBudgetByPrimaryKey(Integer id);

	/**
	 * Save an existing ProjectBudget entity
	 * 
	 */
	public CProjectBudget saveCProjectBudgetProjectBudgets(Integer id_1, ProjectBudget related_projectbudgets);
	
	/****************************************************************************
	 * 功能：查询出所有的预算科目
	 * 作者：李德
	 * 时间：2015-05-11
	 ****************************************************************************/
	public List<CProjectBudget> findAllCProjectBudgetByCProjectBudget(CProjectBudget cProjectBudget);
	/****************************************************************************
	 * 功能：查询出所有的预算科目
	 * 作者：李德
	 * 时间：2015-05-11
	 ****************************************************************************/
	public List<CProjectBudget> findAllCProjectBudgetByCProjectBudget(CProjectBudget cProjectBudget,
                                                                      int page, int pageSize);
	
	/****************************************************************************
	 * 功能：保存实验室对象
	 * 作者：李德
	 * 时间：2015-05-11
	 ****************************************************************************/
	public CProjectBudget save(CProjectBudget cProjectBudget);
}