package net.zjcclims.dao;


import net.zjcclims.domain.ConstructionProject;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage ConstructionProject entities.
 * 
 */
public interface ConstructionProjectDAO extends JpaDao<ConstructionProject> {

	/**
	 * JPQL Query - findAllConstructionProjects
	 *
	 */
	public Set<ConstructionProject> findAllConstructionProjects() throws DataAccessException;

	/**
	 * JPQL Query - findAllConstructionProjects
	 *
	 */
	public Set<ConstructionProject> findAllConstructionProjects(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findConstructionProjectById
	 *
	 */
	public ConstructionProject findConstructionProjectById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findConstructionProjectById
	 *
	 */
	public ConstructionProject findConstructionProjectById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findConstructionProjectByPrimaryKey
	 *
	 */
	public ConstructionProject findConstructionProjectByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findConstructionProjectByPrimaryKey
	 *
	 */
	public ConstructionProject findConstructionProjectByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}