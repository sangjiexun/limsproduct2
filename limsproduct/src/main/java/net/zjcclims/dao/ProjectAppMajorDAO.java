package net.zjcclims.dao;


import net.zjcclims.domain.ProjectAppMajor;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage ProjectAppMajor entities.
 * 
 */
public interface ProjectAppMajorDAO extends JpaDao<ProjectAppMajor> {

	/**
	 * JPQL Query - findAllProjectAppMajors
	 *
	 */
	public Set<ProjectAppMajor> findAllProjectAppMajors() throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectAppMajors
	 *
	 */
	public Set<ProjectAppMajor> findAllProjectAppMajors(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAppMajorById
	 *
	 */
	public ProjectAppMajor findProjectAppMajorById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAppMajorById
	 *
	 */
	public ProjectAppMajor findProjectAppMajorById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAppMajorByPrimaryKey
	 *
	 */
	public ProjectAppMajor findProjectAppMajorByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAppMajorByPrimaryKey
	 *
	 */
	public ProjectAppMajor findProjectAppMajorByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}