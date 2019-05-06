package net.zjcclims.dao;

import net.zjcclims.domain.MOutlineCourse;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage MOutlineCourse entities.
 * 
 */
public interface MOutlineCourseDAO extends JpaDao<MOutlineCourse> {

	/**
	 * JPQL Query - findMOutlineCourseByPrimaryKey
	 *
	 */
	public MOutlineCourse findMOutlineCourseByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findMOutlineCourseByPrimaryKey
	 *
	 */
	public MOutlineCourse findMOutlineCourseByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findMOutlineCourseByFlagContaining
	 *
	 */
	public Set<MOutlineCourse> findMOutlineCourseByFlagContaining(String flag) throws DataAccessException;

	/**
	 * JPQL Query - findMOutlineCourseByFlagContaining
	 *
	 */
	public Set<MOutlineCourse> findMOutlineCourseByFlagContaining(String flag, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findMOutlineCourseById
	 *
	 */
	public MOutlineCourse findMOutlineCourseById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findMOutlineCourseById
	 *
	 */
	public MOutlineCourse findMOutlineCourseById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllMOutlineCourses
	 *
	 */
	public Set<MOutlineCourse> findAllMOutlineCourses() throws DataAccessException;

	/**
	 * JPQL Query - findAllMOutlineCourses
	 *
	 */
	public Set<MOutlineCourse> findAllMOutlineCourses(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findMOutlineCourseByFlag
	 *
	 */
	public Set<MOutlineCourse> findMOutlineCourseByFlag(String flag_1) throws DataAccessException;

	/**
	 * JPQL Query - findMOutlineCourseByFlag
	 *
	 */
	public Set<MOutlineCourse> findMOutlineCourseByFlag(String flag_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findMOutlineCourseByCombine
	 *
	 */
	public Set<MOutlineCourse> findMOutlineCourseByCombine(String combine) throws DataAccessException;

	/**
	 * JPQL Query - findMOutlineCourseByCombine
	 *
	 */
	public Set<MOutlineCourse> findMOutlineCourseByCombine(String combine, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findMOutlineCourseByCombineContaining
	 *
	 */
	public Set<MOutlineCourse> findMOutlineCourseByCombineContaining(String combine_1) throws DataAccessException;

	/**
	 * JPQL Query - findMOutlineCourseByCombineContaining
	 *
	 */
	public Set<MOutlineCourse> findMOutlineCourseByCombineContaining(String combine_1, int startResult, int maxRows) throws DataAccessException;

}