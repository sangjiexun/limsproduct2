package net.zjcclims.dao;


import net.zjcclims.domain.ProjectAcceptFeeList;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.Set;

/**
 * DAO to manage ProjectAcceptFeeList entities.
 * 
 */
public interface ProjectAcceptFeeListDAO extends JpaDao<ProjectAcceptFeeList> {

	/**
	 * JPQL Query - findProjectAcceptFeeListByAmount
	 *
	 */
	public Set<ProjectAcceptFeeList> findProjectAcceptFeeListByAmount(java.math.BigDecimal amount) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptFeeListByAmount
	 *
	 */
	public Set<ProjectAcceptFeeList> findProjectAcceptFeeListByAmount(BigDecimal amount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptFeeListByBudgetaryItem
	 *
	 */
	public Set<ProjectAcceptFeeList> findProjectAcceptFeeListByBudgetaryItem(String budgetaryItem) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptFeeListByBudgetaryItem
	 *
	 */
	public Set<ProjectAcceptFeeList> findProjectAcceptFeeListByBudgetaryItem(String budgetaryItem, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectAcceptFeeLists
	 *
	 */
	public Set<ProjectAcceptFeeList> findAllProjectAcceptFeeLists() throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectAcceptFeeLists
	 *
	 */
	public Set<ProjectAcceptFeeList> findAllProjectAcceptFeeLists(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptFeeListByOtherFundsSource
	 *
	 */
	public Set<ProjectAcceptFeeList> findProjectAcceptFeeListByOtherFundsSource(java.math.BigDecimal otherFundsSource) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptFeeListByOtherFundsSource
	 *
	 */
	public Set<ProjectAcceptFeeList> findProjectAcceptFeeListByOtherFundsSource(BigDecimal otherFundsSource, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptFeeListByPrimaryKey
	 *
	 */
	public ProjectAcceptFeeList findProjectAcceptFeeListByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptFeeListByPrimaryKey
	 *
	 */
	public ProjectAcceptFeeList findProjectAcceptFeeListByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptFeeListById
	 *
	 */
	public ProjectAcceptFeeList findProjectAcceptFeeListById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptFeeListById
	 *
	 */
	public ProjectAcceptFeeList findProjectAcceptFeeListById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptFeeListByBudgetaryItemContaining
	 *
	 */
	public Set<ProjectAcceptFeeList> findProjectAcceptFeeListByBudgetaryItemContaining(String budgetaryItem_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptFeeListByBudgetaryItemContaining
	 *
	 */
	public Set<ProjectAcceptFeeList> findProjectAcceptFeeListByBudgetaryItemContaining(String budgetaryItem_1, int startResult, int maxRows) throws DataAccessException;

}