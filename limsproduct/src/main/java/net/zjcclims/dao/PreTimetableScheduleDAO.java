package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.PreTimetableSchedule;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage PreTimetableSchedule entities.
 * 
 */
public interface PreTimetableScheduleDAO extends JpaDao<PreTimetableSchedule> {

	/**
	 * JPQL Query - findPreTimetableScheduleByEndClass
	 *
	 */
	public Set<PreTimetableSchedule> findPreTimetableScheduleByEndClass(Integer endClass) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableScheduleByEndClass
	 *
	 */
	public Set<PreTimetableSchedule> findPreTimetableScheduleByEndClass(Integer endClass, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableScheduleByStartClass
	 *
	 */
	public Set<PreTimetableSchedule> findPreTimetableScheduleByStartClass(Integer startClass) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableScheduleByStartClass
	 *
	 */
	public Set<PreTimetableSchedule> findPreTimetableScheduleByStartClass(Integer startClass, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableScheduleByEndWeek
	 *
	 */
	public Set<PreTimetableSchedule> findPreTimetableScheduleByEndWeek(Integer endWeek) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableScheduleByEndWeek
	 *
	 */
	public Set<PreTimetableSchedule> findPreTimetableScheduleByEndWeek(Integer endWeek, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableScheduleByStartWeek
	 *
	 */
	public Set<PreTimetableSchedule> findPreTimetableScheduleByStartWeek(Integer startWeek) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableScheduleByStartWeek
	 *
	 */
	public Set<PreTimetableSchedule> findPreTimetableScheduleByStartWeek(Integer startWeek, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableScheduleByPrimaryKey
	 *
	 */
	public PreTimetableSchedule findPreTimetableScheduleByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableScheduleByPrimaryKey
	 *
	 */
	public PreTimetableSchedule findPreTimetableScheduleByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableScheduleByEndWday
	 *
	 */
	public Set<PreTimetableSchedule> findPreTimetableScheduleByEndWday(Integer endWday) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableScheduleByEndWday
	 *
	 */
	public Set<PreTimetableSchedule> findPreTimetableScheduleByEndWday(Integer endWday, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllPreTimetableSchedules
	 *
	 */
	public Set<PreTimetableSchedule> findAllPreTimetableSchedules() throws DataAccessException;

	/**
	 * JPQL Query - findAllPreTimetableSchedules
	 *
	 */
	public Set<PreTimetableSchedule> findAllPreTimetableSchedules(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableScheduleById
	 *
	 */
	public PreTimetableSchedule findPreTimetableScheduleById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableScheduleById
	 *
	 */
	public PreTimetableSchedule findPreTimetableScheduleById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableScheduleByStartWday
	 *
	 */
	public Set<PreTimetableSchedule> findPreTimetableScheduleByStartWday(Integer startWday) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableScheduleByStartWday
	 *
	 */
	public Set<PreTimetableSchedule> findPreTimetableScheduleByStartWday(Integer startWday, int startResult, int maxRows) throws DataAccessException;

}