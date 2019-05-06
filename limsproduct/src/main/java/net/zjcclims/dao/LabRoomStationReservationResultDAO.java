package net.zjcclims.dao;

import java.util.Calendar;
import java.util.Set;

import net.zjcclims.domain.LabRoomStationReservationResult;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage LabRoomStationReservationResult entities.
 * 
 */
public interface LabRoomStationReservationResultDAO extends
		JpaDao<LabRoomStationReservationResult> {

	/**
	 * JPQL Query - findLabRoomStationReservationResultByPrimaryKey
	 *
	 */
	public LabRoomStationReservationResult findLabRoomStationReservationResultByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationResultByPrimaryKey
	 *
	 */
	public LabRoomStationReservationResult findLabRoomStationReservationResultByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationResultByAuditTime
	 *
	 */
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByAuditTime(java.util.Calendar auditTime) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationResultByAuditTime
	 *
	 */
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByAuditTime(Calendar auditTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationResultByTag
	 *
	 */
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByTag(Integer tag) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationResultByTag
	 *
	 */
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByTag(Integer tag, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationResultByAuditTimeAfter
	 *
	 */
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByAuditTimeAfter(java.util.Calendar auditTime_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationResultByAuditTimeAfter
	 *
	 */
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByAuditTimeAfter(Calendar auditTime_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationResultById
	 *
	 */
	public LabRoomStationReservationResult findLabRoomStationReservationResultById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationResultById
	 *
	 */
	public LabRoomStationReservationResult findLabRoomStationReservationResultById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationResultByRemarkContaining
	 *
	 */
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByRemarkContaining(String remark) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationResultByRemarkContaining
	 *
	 */
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByRemarkContaining(String remark, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationResultByAuditResult
	 *
	 */
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByAuditResult(Integer auditResult) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationResultByAuditResult
	 *
	 */
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByAuditResult(Integer auditResult, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationResultByRemark
	 *
	 */
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByRemark(String remark_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationResultByRemark
	 *
	 */
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByRemark(String remark_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationResultByAuditTimeBefore
	 *
	 */
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByAuditTimeBefore(java.util.Calendar auditTime_2) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationResultByAuditTimeBefore
	 *
	 */
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByAuditTimeBefore(Calendar auditTime_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomStationReservationResults
	 *
	 */
	public Set<LabRoomStationReservationResult> findAllLabRoomStationReservationResults() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomStationReservationResults
	 *
	 */
	public Set<LabRoomStationReservationResult> findAllLabRoomStationReservationResults(int startResult, int maxRows) throws DataAccessException;

}