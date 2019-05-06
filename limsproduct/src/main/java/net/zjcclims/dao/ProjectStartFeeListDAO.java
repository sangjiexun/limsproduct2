package net.zjcclims.dao;


import net.zjcclims.domain.ProjectStartFeeList;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.Set;

/**
 * DAO to manage ProjectStartFeeList entities.
 * 
 */
public interface ProjectStartFeeListDAO extends JpaDao<ProjectStartFeeList> {

	/**
	 * JPQL Query - findProjectStartFeeListByAmount
	 *
	 */
	public Set<ProjectStartFeeList> findProjectStartFeeListByAmount(java.math.BigDecimal amount) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartFeeListByAmount
	 *
	 */
	public Set<ProjectStartFeeList> findProjectStartFeeListByAmount(BigDecimal amount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartFeeListByOtherFundsSource
	 *
	 */
	public Set<ProjectStartFeeList> findProjectStartFeeListByOtherFundsSource(java.math.BigDecimal otherFundsSource) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartFeeListByOtherFundsSource
	 *
	 */
	public Set<ProjectStartFeeList> findProjectStartFeeListByOtherFundsSource(BigDecimal otherFundsSource, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectStartFeeLists
	 *
	 */
	public Set<ProjectStartFeeList> findAllProjectStartFeeLists() throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectStartFeeLists
	 *
	 */
	public Set<ProjectStartFeeList> findAllProjectStartFeeLists(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartFeeListByBudgetaryItem
	 *
	 */
	public Set<ProjectStartFeeList> findProjectStartFeeListByBudgetaryItem(String budgetaryItem) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartFeeListByBudgetaryItem
	 *
	 */
	public Set<ProjectStartFeeList> findProjectStartFeeListByBudgetaryItem(String budgetaryItem, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartFeeListByBudgetaryItemContaining
	 *
	 */
	public Set<ProjectStartFeeList> findProjectStartFeeListByBudgetaryItemContaining(String budgetaryItem_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartFeeListByBudgetaryItemContaining
	 *
	 */
	public Set<ProjectStartFeeList> findProjectStartFeeListByBudgetaryItemContaining(String budgetaryItem_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartFeeListByPrimaryKey
	 *
	 */
	public ProjectStartFeeList findProjectStartFeeListByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartFeeListByPrimaryKey
	 *
	 */
	public ProjectStartFeeList findProjectStartFeeListByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartFeeListById
	 *
	 */
	public ProjectStartFeeList findProjectStartFeeListById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartFeeListById
	 *
	 */
	public ProjectStartFeeList findProjectStartFeeListById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}