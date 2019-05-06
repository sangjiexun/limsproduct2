package net.zjcclims.dao;


import net.zjcclims.domain.CFundAppItem;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage CFundAppItem entities.
 * 
 */
public interface CFundAppItemDAO extends JpaDao<CFundAppItem> {

	/**
	 * JPQL Query - findCFundAppItemByName
	 *
	 */
	public Set<CFundAppItem> findCFundAppItemByName(String name) throws DataAccessException;

	/**
	 * JPQL Query - findCFundAppItemByName
	 *
	 */
	public Set<CFundAppItem> findCFundAppItemByName(String name, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCFundAppItemByPrimaryKey
	 *
	 */
	public CFundAppItem findCFundAppItemByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findCFundAppItemByPrimaryKey
	 *
	 */
	public CFundAppItem findCFundAppItemByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllCFundAppItems
	 *
	 */
	public Set<CFundAppItem> findAllCFundAppItems() throws DataAccessException;

	/**
	 * JPQL Query - findAllCFundAppItems
	 *
	 */
	public Set<CFundAppItem> findAllCFundAppItems(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCFundAppItemByNameContaining
	 *
	 */
	public Set<CFundAppItem> findCFundAppItemByNameContaining(String name_1) throws DataAccessException;

	/**
	 * JPQL Query - findCFundAppItemByNameContaining
	 *
	 */
	public Set<CFundAppItem> findCFundAppItemByNameContaining(String name_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCFundAppItemById
	 *
	 */
	public CFundAppItem findCFundAppItemById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findCFundAppItemById
	 *
	 */
	public CFundAppItem findCFundAppItemById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}