package net.zjcclims.dao;


import net.zjcclims.domain.ProjectBudget;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.Set;

/**
 * DAO to manage ProjectBudget entities.
 * 
 */
public interface ProjectBudgetDAO extends JpaDao<ProjectBudget> {

	/**
	 * JPQL Query - findProjectBudgetById
	 *
	 */
	public ProjectBudget findProjectBudgetById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findProjectBudgetById
	 *
	 */
	public ProjectBudget findProjectBudgetById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectBudgets
	 *
	 */
	public Set<ProjectBudget> findAllProjectBudgets() throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectBudgets
	 *
	 */
	public Set<ProjectBudget> findAllProjectBudgets(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectBudgetByPrimaryKey
	 *
	 */
	public ProjectBudget findProjectBudgetByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectBudgetByPrimaryKey
	 *
	 */
	public ProjectBudget findProjectBudgetByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectBudgetByUseFee
	 *
	 */
	public Set<ProjectBudget> findProjectBudgetByUseFee(java.math.BigDecimal useFee) throws DataAccessException;

	/**
	 * JPQL Query - findProjectBudgetByUseFee
	 *
	 */
	public Set<ProjectBudget> findProjectBudgetByUseFee(BigDecimal useFee, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectBudgetByBudgetFee
	 *
	 */
	public Set<ProjectBudget> findProjectBudgetByBudgetFee(java.math.BigDecimal budgetFee) throws DataAccessException;

	/**
	 * JPQL Query - findProjectBudgetByBudgetFee
	 *
	 */
	public Set<ProjectBudget> findProjectBudgetByBudgetFee(BigDecimal budgetFee, int startResult, int maxRows) throws DataAccessException;

}