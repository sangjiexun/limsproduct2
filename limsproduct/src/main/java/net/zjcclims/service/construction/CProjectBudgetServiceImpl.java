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
 * Spring service that handles CRUD requests for CProjectBudget entities
 * 
 */

@Service("CProjectBudgetService")
@Transactional
public class CProjectBudgetServiceImpl implements CProjectBudgetService {

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
	 * Instantiates a new CProjectBudgetServiceImpl.
	 *
	 */
	public CProjectBudgetServiceImpl() {
	}

	/**
	 * Delete an existing ProjectBudget entity
	 * 
	 */
	@Transactional
	public CProjectBudget deleteCProjectBudgetProjectBudgets(Integer cprojectbudget_id, Integer related_projectbudgets_id) {
		ProjectBudget related_projectbudgets = projectBudgetDAO.findProjectBudgetByPrimaryKey(related_projectbudgets_id, -1, -1);

		CProjectBudget cprojectbudget = cProjectBudgetDAO.findCProjectBudgetByPrimaryKey(cprojectbudget_id, -1, -1);

		related_projectbudgets.setCProjectBudget(null);
		cprojectbudget.getProjectBudgets().remove(related_projectbudgets);

		projectBudgetDAO.remove(related_projectbudgets);
		projectBudgetDAO.flush();

		return cprojectbudget;
	}

	/**
	 * Return all CProjectBudget entity
	 * 
	 */
	@Transactional
	public List<CProjectBudget> findAllCProjectBudgets(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<CProjectBudget>(cProjectBudgetDAO.findAllCProjectBudgets(startResult, maxRows));
	}

	/**
	 * Delete an existing CProjectBudget entity
	 * 
	 */
	@Transactional
	public void deleteCProjectBudget(CProjectBudget cprojectbudget) {
		cProjectBudgetDAO.remove(cprojectbudget);
		cProjectBudgetDAO.flush();
	}

	/**
	 * Load an existing CProjectBudget entity
	 * 
	 */
	@Transactional
	public Set<CProjectBudget> loadCProjectBudgets() {
		return cProjectBudgetDAO.findAllCProjectBudgets();
	}

	/**
	 * Save an existing CProjectBudget entity
	 * 
	 */
	@Transactional
	public void saveCProjectBudget(CProjectBudget cprojectbudget) {
		CProjectBudget existingCProjectBudget = cProjectBudgetDAO.findCProjectBudgetByPrimaryKey(cprojectbudget.getId());

		if (existingCProjectBudget != null) {
			if (existingCProjectBudget != cprojectbudget) {
				existingCProjectBudget.setId(cprojectbudget.getId());
				existingCProjectBudget.setName(cprojectbudget.getName());
			}
			cprojectbudget = cProjectBudgetDAO.store(existingCProjectBudget);
		} else {
			cprojectbudget = cProjectBudgetDAO.store(cprojectbudget);
		}
		cProjectBudgetDAO.flush();
	}

	/**
	 * Return a count of all CProjectBudget entity
	 * 
	 */
	@Transactional
	public Integer countCProjectBudgets() {
		return ((Long) cProjectBudgetDAO.createQuerySingleResult("select count(o) from CProjectBudget o").getSingleResult()).intValue();
	}

	/**
	 */
	@Transactional
	public CProjectBudget findCProjectBudgetByPrimaryKey(Integer id) {
		return cProjectBudgetDAO.findCProjectBudgetByPrimaryKey(id);
	}

	/**
	 * Save an existing ProjectBudget entity
	 * 
	 */
	@Transactional
	public CProjectBudget saveCProjectBudgetProjectBudgets(Integer id, ProjectBudget related_projectbudgets) {
		CProjectBudget cprojectbudget = cProjectBudgetDAO.findCProjectBudgetByPrimaryKey(id, -1, -1);
		ProjectBudget existingprojectBudgets = projectBudgetDAO.findProjectBudgetByPrimaryKey(related_projectbudgets.getId());

		// copy into the existing record to preserve existing relationships
		if (existingprojectBudgets != null) {
			existingprojectBudgets.setId(related_projectbudgets.getId());
			existingprojectBudgets.setBudgetFee(related_projectbudgets.getBudgetFee());
			existingprojectBudgets.setUseFee(related_projectbudgets.getUseFee());
			related_projectbudgets = existingprojectBudgets;
		} else {
			related_projectbudgets = projectBudgetDAO.store(related_projectbudgets);
			projectBudgetDAO.flush();
		}

		related_projectbudgets.setCProjectBudget(cprojectbudget);
		cprojectbudget.getProjectBudgets().add(related_projectbudgets);
		related_projectbudgets = projectBudgetDAO.store(related_projectbudgets);
		projectBudgetDAO.flush();

		cprojectbudget = cProjectBudgetDAO.store(cprojectbudget);
		cProjectBudgetDAO.flush();

		return cprojectbudget;
	}
	
	/****************************************************************************
	 * 功能：查询出所有的预算科目
	 * 作者：李德
	 * 时间：2015-05-11
	 ****************************************************************************/
	@Override
	public List<CProjectBudget> findAllCProjectBudgetByCProjectBudget(CProjectBudget cProjectBudget) {
		// TODO Auto-generated method stub
		String sql="select c from CProjectBudget c where 1=1";
		sql+=" order by c.id desc";
		return cProjectBudgetDAO.executeQuery(sql,0,-1);
	}
	/****************************************************************************
	 * 功能：查询出所有的预算科目
	 * 作者：李德
	 * 时间：2015-05-11
	 ****************************************************************************/
	@Override
	public List<CProjectBudget> findAllCProjectBudgetByCProjectBudget(CProjectBudget cProjectBudget,
                                                                      int page, int pageSize) {
		// TODO Auto-generated method stub
		String sql="select c from CProjectBudget c where 1=1";

		sql+=" order by c.id asc";
		return cProjectBudgetDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
	}
	
	/****************************************************************************
	 * 功能：保存实验室对象
	 * 作者：李德
	 * 时间：2015-05-11
	 ****************************************************************************/
	@Override
	public CProjectBudget save(CProjectBudget cProjectBudget) {
		// TODO Auto-generated method stub
		return cProjectBudgetDAO.store(cProjectBudget);
	}
	
}
