package net.zjcclims.dao;

import java.util.Calendar;
import java.util.Set;

import net.zjcclims.domain.SchoolYear;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage SchoolYear entities.
 * 
 */
public interface SchoolYearDAO extends JpaDao<SchoolYear> {

	/**
	 * JPQL Query - findSchoolYearByPrimaryKey
	 *
	 */
	public SchoolYear findSchoolYearByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearByPrimaryKey
	 *
	 */
	public SchoolYear findSchoolYearByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearByYearNameContaining
	 *
	 */
	public Set<SchoolYear> findSchoolYearByYearNameContaining(String yearName) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearByYearNameContaining
	 *
	 */
	public Set<SchoolYear> findSchoolYearByYearNameContaining(String yearName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearByCode
	 *
	 */
	public Set<SchoolYear> findSchoolYearByCode(String code) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearByCode
	 *
	 */
	public Set<SchoolYear> findSchoolYearByCode(String code, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllSchoolYears
	 *
	 */
	public Set<SchoolYear> findAllSchoolYears() throws DataAccessException;

	/**
	 * JPQL Query - findAllSchoolYears
	 *
	 */
	public Set<SchoolYear> findAllSchoolYears(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearByYearStart
	 *
	 */
	public Set<SchoolYear> findSchoolYearByYearStart(java.util.Calendar yearStart) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearByYearStart
	 *
	 */
	public Set<SchoolYear> findSchoolYearByYearStart(Calendar yearStart, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearByYearEndBefore
	 *
	 */
	public Set<SchoolYear> findSchoolYearByYearEndBefore(java.util.Calendar yearEnd) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearByYearEndBefore
	 *
	 */
	public Set<SchoolYear> findSchoolYearByYearEndBefore(Calendar yearEnd, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearByCodeContaining
	 *
	 */
	public Set<SchoolYear> findSchoolYearByCodeContaining(String code_1) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearByCodeContaining
	 *
	 */
	public Set<SchoolYear> findSchoolYearByCodeContaining(String code_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearByYearEndAfter
	 *
	 */
	public Set<SchoolYear> findSchoolYearByYearEndAfter(java.util.Calendar yearEnd_1) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearByYearEndAfter
	 *
	 */
	public Set<SchoolYear> findSchoolYearByYearEndAfter(Calendar yearEnd_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearByYearEnd
	 *
	 */
	public Set<SchoolYear> findSchoolYearByYearEnd(java.util.Calendar yearEnd_2) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearByYearEnd
	 *
	 */
	public Set<SchoolYear> findSchoolYearByYearEnd(Calendar yearEnd_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearById
	 *
	 */
	public SchoolYear findSchoolYearById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearById
	 *
	 */
	public SchoolYear findSchoolYearById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearByYearStartBefore
	 *
	 */
	public Set<SchoolYear> findSchoolYearByYearStartBefore(java.util.Calendar yearStart_1) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearByYearStartBefore
	 *
	 */
	public Set<SchoolYear> findSchoolYearByYearStartBefore(Calendar yearStart_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearByYearStartAfter
	 *
	 */
	public Set<SchoolYear> findSchoolYearByYearStartAfter(java.util.Calendar yearStart_2) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearByYearStartAfter
	 *
	 */
	public Set<SchoolYear> findSchoolYearByYearStartAfter(Calendar yearStart_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearByYearName
	 *
	 */
	public Set<SchoolYear> findSchoolYearByYearName(String yearName_1) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolYearByYearName
	 *
	 */
	public Set<SchoolYear> findSchoolYearByYearName(String yearName_1, int startResult, int maxRows) throws DataAccessException;

}