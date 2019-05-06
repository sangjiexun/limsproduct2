package net.zjcclims.dao;


import net.zjcclims.domain.ProjectFeeList;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.Set;

/**
 * DAO to manage ProjectFeeList entities.
 * 
 */
public interface ProjectFeeListDAO extends JpaDao<ProjectFeeList> {

	/**
	 * JPQL Query - findProjectFeeListByOtherFundsSource
	 *
	 */
	public Set<ProjectFeeList> findProjectFeeListByOtherFundsSource(java.math.BigDecimal otherFundsSource) throws DataAccessException;

	/**
	 * JPQL Query - findProjectFeeListByOtherFundsSource
	 *
	 */
	public Set<ProjectFeeList> findProjectFeeListByOtherFundsSource(BigDecimal otherFundsSource, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectFeeLists
	 *
	 */
	public Set<ProjectFeeList> findAllProjectFeeLists() throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectFeeLists
	 *
	 */
	public Set<ProjectFeeList> findAllProjectFeeLists(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectFeeListByPrimaryKey
	 *
	 */
	public ProjectFeeList findProjectFeeListByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findProjectFeeListByPrimaryKey
	 *
	 */
	public ProjectFeeList findProjectFeeListByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectFeeListById
	 *
	 */
	public ProjectFeeList findProjectFeeListById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectFeeListById
	 *
	 */
	public ProjectFeeList findProjectFeeListById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectFeeListByBudgetaryItemContaining
	 *
	 */
	public Set<ProjectFeeList> findProjectFeeListByBudgetaryItemContaining(String budgetaryItem) throws DataAccessException;

	/**
	 * JPQL Query - findProjectFeeListByBudgetaryItemContaining
	 *
	 */
	public Set<ProjectFeeList> findProjectFeeListByBudgetaryItemContaining(String budgetaryItem, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectFeeListByAmount
	 *
	 */
	public Set<ProjectFeeList> findProjectFeeListByAmount(java.math.BigDecimal amount) throws DataAccessException;

	/**
	 * JPQL Query - findProjectFeeListByAmount
	 *
	 */
	public Set<ProjectFeeList> findProjectFeeListByAmount(BigDecimal amount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectFeeListByBudgetaryItem
	 *
	 */
	public Set<ProjectFeeList> findProjectFeeListByBudgetaryItem(String budgetaryItem_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectFeeListByBudgetaryItem
	 *
	 */
	public Set<ProjectFeeList> findProjectFeeListByBudgetaryItem(String budgetaryItem_1, int startResult, int maxRows) throws DataAccessException;

}