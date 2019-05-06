package net.zjcclims.dao;

import net.zjcclims.domain.SystemMenu;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage SystemMenu entities.
 * 
 */
public interface SystemMenuDAO extends JpaDao<SystemMenu> {

	/**
	 * JPQL Query - findSystemMenuByProjectNameContaining
	 *
	 */
	public Set<SystemMenu> findSystemMenuByProjectNameContaining(String projectName) throws DataAccessException;

	/**
	 * JPQL Query - findSystemMenuByProjectNameContaining
	 *
	 */
	public Set<SystemMenu> findSystemMenuByProjectNameContaining(String projectName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSystemMenuById
	 *
	 */
	public SystemMenu findSystemMenuById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findSystemMenuById
	 *
	 */
	public SystemMenu findSystemMenuById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSystemMenuByProjectName
	 *
	 */
	public Set<SystemMenu> findSystemMenuByProjectName(String projectName_1) throws DataAccessException;

	/**
	 * JPQL Query - findSystemMenuByProjectName
	 *
	 */
	public Set<SystemMenu> findSystemMenuByProjectName(String projectName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSystemMenuByPrimaryKey
	 *
	 */
	public SystemMenu findSystemMenuByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findSystemMenuByPrimaryKey
	 *
	 */
	public SystemMenu findSystemMenuByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSystemMenuByName
	 *
	 */
	public Set<SystemMenu> findSystemMenuByName(String name) throws DataAccessException;

	/**
	 * JPQL Query - findSystemMenuByName
	 *
	 */
	public Set<SystemMenu> findSystemMenuByName(String name, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSystemMenuByNameContaining
	 *
	 */
	public Set<SystemMenu> findSystemMenuByNameContaining(String name_1) throws DataAccessException;

	/**
	 * JPQL Query - findSystemMenuByNameContaining
	 *
	 */
	public Set<SystemMenu> findSystemMenuByNameContaining(String name_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllSystemMenus
	 *
	 */
	public Set<SystemMenu> findAllSystemMenus() throws DataAccessException;

	/**
	 * JPQL Query - findAllSystemMenus
	 *
	 */
	public Set<SystemMenu> findAllSystemMenus(int startResult, int maxRows) throws DataAccessException;

}