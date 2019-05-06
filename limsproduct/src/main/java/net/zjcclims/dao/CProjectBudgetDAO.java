package net.zjcclims.dao;


import net.zjcclims.domain.CProjectBudget;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage CProjectBudget entities.
 * 
 */
public interface CProjectBudgetDAO extends JpaDao<CProjectBudget> {

	/**
	 * JPQL Query - findCProjectBudgetByNameContaining
	 *
	 */
	public Set<CProjectBudget> findCProjectBudgetByNameContaining(String name) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectBudgetByNameContaining
	 *
	 */
	public Set<CProjectBudget> findCProjectBudgetByNameContaining(String name, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllCProjectBudgets
	 *
	 */
	public Set<CProjectBudget> findAllCProjectBudgets() throws DataAccessException;

	/**
	 * JPQL Query - findAllCProjectBudgets
	 *
	 */
	public Set<CProjectBudget> findAllCProjectBudgets(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectBudgetById
	 *
	 */
	public CProjectBudget findCProjectBudgetById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectBudgetById
	 *
	 */
	public CProjectBudget findCProjectBudgetById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectBudgetByName
	 *
	 */
	public Set<CProjectBudget> findCProjectBudgetByName(String name_1) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectBudgetByName
	 *
	 */
	public Set<CProjectBudget> findCProjectBudgetByName(String name_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectBudgetByPrimaryKey
	 *
	 */
	public CProjectBudget findCProjectBudgetByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectBudgetByPrimaryKey
	 *
	 */
	public CProjectBudget findCProjectBudgetByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}