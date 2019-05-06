package net.zjcclims.dao;

import net.zjcclims.domain.OperationOutlineCourse;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage OperationOutlineCourse entities.
 * 
 */
public interface OperationOutlineCourseDAO extends
        JpaDao<OperationOutlineCourse> {

	/**
	 * JPQL Query - findOperationOutlineCourseByCourseContent
	 *
	 */
	public Set<OperationOutlineCourse> findOperationOutlineCourseByCourseContent(String courseContent) throws DataAccessException;

	/**
	 * JPQL Query - findOperationOutlineCourseByCourseContent
	 *
	 */
	public Set<OperationOutlineCourse> findOperationOutlineCourseByCourseContent(String courseContent, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findOperationOutlineCourseByWeek
	 *
	 */
	public Set<OperationOutlineCourse> findOperationOutlineCourseByWeek(Integer week) throws DataAccessException;

	/**
	 * JPQL Query - findOperationOutlineCourseByWeek
	 *
	 */
	public Set<OperationOutlineCourse> findOperationOutlineCourseByWeek(Integer week, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findOperationOutlineCourseByPrimaryKey
	 *
	 */
	public OperationOutlineCourse findOperationOutlineCourseByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findOperationOutlineCourseByPrimaryKey
	 *
	 */
	public OperationOutlineCourse findOperationOutlineCourseByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllOperationOutlineCourses
	 *
	 */
	public Set<OperationOutlineCourse> findAllOperationOutlineCourses() throws DataAccessException;

	/**
	 * JPQL Query - findAllOperationOutlineCourses
	 *
	 */
	public Set<OperationOutlineCourse> findAllOperationOutlineCourses(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findOperationOutlineCourseById
	 *
	 */
	public OperationOutlineCourse findOperationOutlineCourseById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findOperationOutlineCourseById
	 *
	 */
	public OperationOutlineCourse findOperationOutlineCourseById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}