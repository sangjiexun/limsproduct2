package net.zjcclims.dao;


import net.zjcclims.domain.CProjectSource;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage CProjectSource entities.
 * 
 */
public interface CProjectSourceDAO extends JpaDao<CProjectSource> {

	/**
	 * JPQL Query - findCProjectSourceByNameContaining
	 *
	 */
	public Set<CProjectSource> findCProjectSourceByNameContaining(String name) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectSourceByNameContaining
	 *
	 */
	public Set<CProjectSource> findCProjectSourceByNameContaining(String name, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectSourceByName
	 *
	 */
	public Set<CProjectSource> findCProjectSourceByName(String name_1) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectSourceByName
	 *
	 */
	public Set<CProjectSource> findCProjectSourceByName(String name_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllCProjectSources
	 *
	 */
	public Set<CProjectSource> findAllCProjectSources() throws DataAccessException;

	/**
	 * JPQL Query - findAllCProjectSources
	 *
	 */
	public Set<CProjectSource> findAllCProjectSources(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectSourceByPrimaryKey
	 *
	 */
	public CProjectSource findCProjectSourceByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectSourceByPrimaryKey
	 *
	 */
	public CProjectSource findCProjectSourceByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectSourceById
	 *
	 */
	public CProjectSource findCProjectSourceById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectSourceById
	 *
	 */
	public CProjectSource findCProjectSourceById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}