package net.zjcclims.dao;

import java.util.Calendar;
import java.util.Set;

import net.zjcclims.domain.LabRoomStationReservation;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage LabRoomStationReservation entities.
 * 
 */
public interface LabRoomStationReservationDAO extends
		JpaDao<LabRoomStationReservation> {

	/**
	 * JPQL Query - findLabRoomStationReservationByStartTime
	 *
	 */
	public Set<LabRoomStationReservation> findLabRoomStationReservationByStartTime(java.util.Calendar startTime) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationByStartTime
	 *
	 */
	public Set<LabRoomStationReservation> findLabRoomStationReservationByStartTime(Calendar startTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationById
	 *
	 */
	public LabRoomStationReservation findLabRoomStationReservationById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationById
	 *
	 */
	public LabRoomStationReservation findLabRoomStationReservationById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationByStationCount
	 *
	 */
	public Set<LabRoomStationReservation> findLabRoomStationReservationByStationCount(Integer stationCount) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationByStationCount
	 *
	 */
	public Set<LabRoomStationReservation> findLabRoomStationReservationByStationCount(Integer stationCount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomStationReservations
	 *
	 */
	public Set<LabRoomStationReservation> findAllLabRoomStationReservations() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomStationReservations
	 *
	 */
	public Set<LabRoomStationReservation> findAllLabRoomStationReservations(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationByReasonContaining
	 *
	 */
	public Set<LabRoomStationReservation> findLabRoomStationReservationByReasonContaining(String reason) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationByReasonContaining
	 *
	 */
	public Set<LabRoomStationReservation> findLabRoomStationReservationByReasonContaining(String reason, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationByEndTime
	 *
	 */
	public Set<LabRoomStationReservation> findLabRoomStationReservationByEndTime(java.util.Calendar endTime) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationByEndTime
	 *
	 */
	public Set<LabRoomStationReservation> findLabRoomStationReservationByEndTime(Calendar endTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationByEndPeriod
	 *
	 */
	public Set<LabRoomStationReservation> findLabRoomStationReservationByEndPeriod(Integer endPeriod) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationByEndPeriod
	 *
	 */
	public Set<LabRoomStationReservation> findLabRoomStationReservationByEndPeriod(Integer endPeriod, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationByReservationBefore
	 *
	 */
	public Set<LabRoomStationReservation> findLabRoomStationReservationByReservationBefore(java.util.Calendar reservation) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationByReservationBefore
	 *
	 */
	public Set<LabRoomStationReservation> findLabRoomStationReservationByReservationBefore(Calendar reservation, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationByPrimaryKey
	 *
	 */
	public LabRoomStationReservation findLabRoomStationReservationByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationByPrimaryKey
	 *
	 */
	public LabRoomStationReservation findLabRoomStationReservationByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationByReservation
	 *
	 */
	public Set<LabRoomStationReservation> findLabRoomStationReservationByReservation(java.util.Calendar reservation_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationByReservation
	 *
	 */
	public Set<LabRoomStationReservation> findLabRoomStationReservationByReservation(Calendar reservation_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationByReservationAfter
	 *
	 */
	public Set<LabRoomStationReservation> findLabRoomStationReservationByReservationAfter(java.util.Calendar reservation_2) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationByReservationAfter
	 *
	 */
	public Set<LabRoomStationReservation> findLabRoomStationReservationByReservationAfter(Calendar reservation_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationByReason
	 *
	 */
	public Set<LabRoomStationReservation> findLabRoomStationReservationByReason(String reason_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationByReason
	 *
	 */
	public Set<LabRoomStationReservation> findLabRoomStationReservationByReason(String reason_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationByStartPeriod
	 *
	 */
	public Set<LabRoomStationReservation> findLabRoomStationReservationByStartPeriod(Integer startPeriod) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationByStartPeriod
	 *
	 */
	public Set<LabRoomStationReservation> findLabRoomStationReservationByStartPeriod(Integer startPeriod, int startResult, int maxRows) throws DataAccessException;

}