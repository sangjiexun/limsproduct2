package net.zjcclims.dao;


import net.zjcclims.domain.ProjectDeviceList;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.Set;

/**
 * DAO to manage ProjectDeviceList entities.
 * 
 */
public interface ProjectDeviceListDAO extends JpaDao<ProjectDeviceList> {

	/**
	 * JPQL Query - findAllProjectDeviceLists
	 *
	 */
	public Set<ProjectDeviceList> findAllProjectDeviceLists() throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectDeviceLists
	 *
	 */
	public Set<ProjectDeviceList> findAllProjectDeviceLists(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceListByAmount
	 *
	 */
	public Set<ProjectDeviceList> findProjectDeviceListByAmount(java.math.BigDecimal amount) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceListByAmount
	 *
	 */
	public Set<ProjectDeviceList> findProjectDeviceListByAmount(BigDecimal amount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceListByOtherFundsSource
	 *
	 */
	public Set<ProjectDeviceList> findProjectDeviceListByOtherFundsSource(String otherFundsSource) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceListByOtherFundsSource
	 *
	 */
	public Set<ProjectDeviceList> findProjectDeviceListByOtherFundsSource(String otherFundsSource, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceListById
	 *
	 */
	public ProjectDeviceList findProjectDeviceListById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceListById
	 *
	 */
	public ProjectDeviceList findProjectDeviceListById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceListByBudgetaryItemContaining
	 *
	 */
	public Set<ProjectDeviceList> findProjectDeviceListByBudgetaryItemContaining(String budgetaryItem) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceListByBudgetaryItemContaining
	 *
	 */
	public Set<ProjectDeviceList> findProjectDeviceListByBudgetaryItemContaining(String budgetaryItem, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceListByPrimaryKey
	 *
	 */
	public ProjectDeviceList findProjectDeviceListByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceListByPrimaryKey
	 *
	 */
	public ProjectDeviceList findProjectDeviceListByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceListByOtherFundsSourceContaining
	 *
	 */
	public Set<ProjectDeviceList> findProjectDeviceListByOtherFundsSourceContaining(String otherFundsSource_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceListByOtherFundsSourceContaining
	 *
	 */
	public Set<ProjectDeviceList> findProjectDeviceListByOtherFundsSourceContaining(String otherFundsSource_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceListByBudgetaryItem
	 *
	 */
	public Set<ProjectDeviceList> findProjectDeviceListByBudgetaryItem(String budgetaryItem_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceListByBudgetaryItem
	 *
	 */
	public Set<ProjectDeviceList> findProjectDeviceListByBudgetaryItem(String budgetaryItem_1, int startResult, int maxRows) throws DataAccessException;

}