package net.zjcclims.dao;


import net.zjcclims.domain.ProjectAppCourse;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage ProjectAppCourse entities.
 * 
 */
public interface ProjectAppCourseDAO extends JpaDao<ProjectAppCourse> {

	/**
	 * JPQL Query - findProjectAppCourseByInfo
	 *
	 */
	public Set<ProjectAppCourse> findProjectAppCourseByInfo(String info) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAppCourseByInfo
	 *
	 */
	public Set<ProjectAppCourse> findProjectAppCourseByInfo(String info, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAppCourseByInfoContaining
	 *
	 */
	public Set<ProjectAppCourse> findProjectAppCourseByInfoContaining(String info_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAppCourseByInfoContaining
	 *
	 */
	public Set<ProjectAppCourse> findProjectAppCourseByInfoContaining(String info_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectAppCourses
	 *
	 */
	public Set<ProjectAppCourse> findAllProjectAppCourses() throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectAppCourses
	 *
	 */
	public Set<ProjectAppCourse> findAllProjectAppCourses(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAppCourseByPrimaryKey
	 *
	 */
	public ProjectAppCourse findProjectAppCourseByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAppCourseByPrimaryKey
	 *
	 */
	public ProjectAppCourse findProjectAppCourseByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAppCourseById
	 *
	 */
	public ProjectAppCourse findProjectAppCourseById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAppCourseById
	 *
	 */
	public ProjectAppCourse findProjectAppCourseById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}