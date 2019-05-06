package net.zjcclims.dao;


import net.zjcclims.domain.TimetableAppointmentCycle;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage TimetableAppointmentCycle entities.
 * 
 */
public interface TimetableAppointmentCycleDAO extends
        JpaDao<TimetableAppointmentCycle> {

	/**
	 * JPQL Query - findTimetableAppointmentCycleById
	 *
	 */
	public TimetableAppointmentCycle findTimetableAppointmentCycleById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentCycleById
	 *
	 */
	public TimetableAppointmentCycle findTimetableAppointmentCycleById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllTimetableAppointmentCycles
	 *
	 */
	public Set<TimetableAppointmentCycle> findAllTimetableAppointmentCycles() throws DataAccessException;

	/**
	 * JPQL Query - findAllTimetableAppointmentCycles
	 *
	 */
	public Set<TimetableAppointmentCycle> findAllTimetableAppointmentCycles(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentCycleByPrimaryKey
	 *
	 */
	public TimetableAppointmentCycle findTimetableAppointmentCycleByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentCycleByPrimaryKey
	 *
	 */
	public TimetableAppointmentCycle findTimetableAppointmentCycleByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentCycleByDetailNo
	 *
	 */
	public Set<TimetableAppointmentCycle> findTimetableAppointmentCycleByDetailNo(String detailNo) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentCycleByDetailNo
	 *
	 */
	public Set<TimetableAppointmentCycle> findTimetableAppointmentCycleByDetailNo(String detailNo, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentCycleByDetailNoContaining
	 *
	 */
	public Set<TimetableAppointmentCycle> findTimetableAppointmentCycleByDetailNoContaining(String detailNo_1) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentCycleByDetailNoContaining
	 *
	 */
	public Set<TimetableAppointmentCycle> findTimetableAppointmentCycleByDetailNoContaining(String detailNo_1, int startResult, int maxRows) throws DataAccessException;

}