package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.PreTimetableAppointment;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage PreTimetableAppointment entities.
 * 
 */
public interface PreTimetableAppointmentDAO extends
		JpaDao<PreTimetableAppointment> {

	/**
	 * JPQL Query - findPreTimetableAppointmentByPrimaryKey
	 *
	 */
	public PreTimetableAppointment findPreTimetableAppointmentByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableAppointmentByPrimaryKey
	 *
	 */
	public PreTimetableAppointment findPreTimetableAppointmentByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableAppointmentByStuNumber
	 *
	 */
	public Set<PreTimetableAppointment> findPreTimetableAppointmentByStuNumber(Integer stuNumber) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableAppointmentByStuNumber
	 *
	 */
	public Set<PreTimetableAppointment> findPreTimetableAppointmentByStuNumber(Integer stuNumber, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableAppointmentById
	 *
	 */
	public PreTimetableAppointment findPreTimetableAppointmentById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableAppointmentById
	 *
	 */
	public PreTimetableAppointment findPreTimetableAppointmentById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableAppointmentByState
	 *
	 */
	public Set<PreTimetableAppointment> findPreTimetableAppointmentByState(Integer state) throws DataAccessException;

	/**
	 * JPQL Query - findPreTimetableAppointmentByState
	 *
	 */
	public Set<PreTimetableAppointment> findPreTimetableAppointmentByState(Integer state, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllPreTimetableAppointments
	 *
	 */
	public Set<PreTimetableAppointment> findAllPreTimetableAppointments() throws DataAccessException;

	/**
	 * JPQL Query - findAllPreTimetableAppointments
	 *
	 */
	public Set<PreTimetableAppointment> findAllPreTimetableAppointments(int startResult, int maxRows) throws DataAccessException;

}