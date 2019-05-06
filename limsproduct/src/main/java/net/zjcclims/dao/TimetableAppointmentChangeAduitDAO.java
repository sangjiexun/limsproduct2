package net.zjcclims.dao;

import java.util.Calendar;
import java.util.Set;

import net.zjcclims.domain.TimetableAppointmentChangeAduit;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage TimetableAppointmentChangeAduit entities.
 * 
 */
public interface TimetableAppointmentChangeAduitDAO extends
		JpaDao<TimetableAppointmentChangeAduit> {

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByMemContaining
	 *
	 */
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByMemContaining(String mem) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByMemContaining
	 *
	 */
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByMemContaining(String mem, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByStatus
	 *
	 */
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByStatus(Integer status) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByStatus
	 *
	 */
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByStatus(Integer status, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByResultContaining
	 *
	 */
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByResultContaining(String result) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByResultContaining
	 *
	 */
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByResultContaining(String result, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByResult
	 *
	 */
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByResult(String result_1) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByResult
	 *
	 */
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByResult(String result_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByCreateDateBefore
	 *
	 */
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByCreateDateBefore(java.util.Calendar createDate) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByCreateDateBefore
	 *
	 */
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByCreateDateBefore(Calendar createDate, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByPrimaryKey
	 *
	 */
	public TimetableAppointmentChangeAduit findTimetableAppointmentChangeAduitByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByPrimaryKey
	 *
	 */
	public TimetableAppointmentChangeAduit findTimetableAppointmentChangeAduitByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByCreateDate
	 *
	 */
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByCreateDate(java.util.Calendar createDate_1) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByCreateDate
	 *
	 */
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByCreateDate(Calendar createDate_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByMem
	 *
	 */
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByMem(String mem_1) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByMem
	 *
	 */
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByMem(String mem_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllTimetableAppointmentChangeAduits
	 *
	 */
	public Set<TimetableAppointmentChangeAduit> findAllTimetableAppointmentChangeAduits() throws DataAccessException;

	/**
	 * JPQL Query - findAllTimetableAppointmentChangeAduits
	 *
	 */
	public Set<TimetableAppointmentChangeAduit> findAllTimetableAppointmentChangeAduits(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByCreateDateAfter
	 *
	 */
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByCreateDateAfter(java.util.Calendar createDate_2) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByCreateDateAfter
	 *
	 */
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByCreateDateAfter(Calendar createDate_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitById
	 *
	 */
	public TimetableAppointmentChangeAduit findTimetableAppointmentChangeAduitById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitById
	 *
	 */
	public TimetableAppointmentChangeAduit findTimetableAppointmentChangeAduitById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}