package net.zjcclims.dao;


import net.zjcclims.domain.CProjectStatus;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage CProjectStatus entities.
 * 
 */
public interface CProjectStatusDAO extends JpaDao<CProjectStatus> {

	/**
	 * JPQL Query - findCProjectStatusByPrimaryKey
	 *
	 */
	public CProjectStatus findCProjectStatusByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectStatusByPrimaryKey
	 *
	 */
	public CProjectStatus findCProjectStatusByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectStatusByName
	 *
	 */
	public Set<CProjectStatus> findCProjectStatusByName(String name) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectStatusByName
	 *
	 */
	public Set<CProjectStatus> findCProjectStatusByName(String name, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllCProjectStatuss
	 *
	 */
	public Set<CProjectStatus> findAllCProjectStatuss() throws DataAccessException;

	/**
	 * JPQL Query - findAllCProjectStatuss
	 *
	 */
	public Set<CProjectStatus> findAllCProjectStatuss(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectStatusById
	 *
	 */
	public CProjectStatus findCProjectStatusById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectStatusById
	 *
	 */
	public CProjectStatus findCProjectStatusById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectStatusByNameContaining
	 *
	 */
	public Set<CProjectStatus> findCProjectStatusByNameContaining(String name_1) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectStatusByNameContaining
	 *
	 */
	public Set<CProjectStatus> findCProjectStatusByNameContaining(String name_1, int startResult, int maxRows) throws DataAccessException;

}