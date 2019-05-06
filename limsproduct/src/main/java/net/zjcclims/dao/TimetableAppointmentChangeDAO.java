package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.TimetableAppointmentChange;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage TimetableAppointmentChange entities.
 * 
 */
public interface TimetableAppointmentChangeDAO extends
		JpaDao<TimetableAppointmentChange> {

	/**
	 * JPQL Query - findTimetableAppointmentChangeByWeek
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByWeek(Integer week) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByWeek
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByWeek(Integer week, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByWeekday
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByWeekday(Integer weekday) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByWeekday
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByWeekday(Integer weekday, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByStartClass
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByStartClass(Integer startClass) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByStartClass
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByStartClass(Integer startClass, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByEndClass
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByEndClass(Integer endClass) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByEndClass
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByEndClass(Integer endClass, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllTimetableAppointmentChanges
	 *
	 */
	public Set<TimetableAppointmentChange> findAllTimetableAppointmentChanges() throws DataAccessException;

	/**
	 * JPQL Query - findAllTimetableAppointmentChanges
	 *
	 */
	public Set<TimetableAppointmentChange> findAllTimetableAppointmentChanges(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByItemNameContaining
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByItemNameContaining(String itemName) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByItemNameContaining
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByItemNameContaining(String itemName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByAddressContaining
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByAddressContaining(String address) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByAddressContaining
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByAddressContaining(String address, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByAddress
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByAddress(String address_1) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByAddress
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByAddress(String address_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByStatus
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByStatus(Integer status) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByStatus
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByStatus(Integer status, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByCauseContaining
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByCauseContaining(String cause) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByCauseContaining
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByCauseContaining(String cause, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByFlag
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByFlag(Integer flag) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByFlag
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByFlag(Integer flag, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByItemName
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByItemName(String itemName_1) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByItemName
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByItemName(String itemName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeById
	 *
	 */
	public TimetableAppointmentChange findTimetableAppointmentChangeById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeById
	 *
	 */
	public TimetableAppointmentChange findTimetableAppointmentChangeById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByCause
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByCause(String cause_1) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByCause
	 *
	 */
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByCause(String cause_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByPrimaryKey
	 *
	 */
	public TimetableAppointmentChange findTimetableAppointmentChangeByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentChangeByPrimaryKey
	 *
	 */
	public TimetableAppointmentChange findTimetableAppointmentChangeByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}