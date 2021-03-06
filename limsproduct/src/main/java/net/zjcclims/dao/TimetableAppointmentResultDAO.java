package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.TimetableAppointmentResult;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage TimetableAppointmentResult entities.
 * 
 */
public interface TimetableAppointmentResultDAO extends
		JpaDao<TimetableAppointmentResult> {

	/**
	 * JPQL Query - findTimetableAppointmentResultByAuditResult
	 *
	 */
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByAuditResult(String auditResult) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentResultByAuditResult
	 *
	 */
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByAuditResult(String auditResult, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentResultByAppointmentId
	 *
	 */
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByAppointmentId(Integer appointmentId) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentResultByAppointmentId
	 *
	 */
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByAppointmentId(Integer appointmentId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllTimetableAppointmentResults
	 *
	 */
	public Set<TimetableAppointmentResult> findAllTimetableAppointmentResults() throws DataAccessException;

	/**
	 * JPQL Query - findAllTimetableAppointmentResults
	 *
	 */
	public Set<TimetableAppointmentResult> findAllTimetableAppointmentResults(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentResultByUser
	 *
	 */
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByUser(String user) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentResultByUser
	 *
	 */
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByUser(String user, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentResultByTag
	 *
	 */
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByTag(Integer tag) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentResultByTag
	 *
	 */
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByTag(Integer tag, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentResultByUserContaining
	 *
	 */
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByUserContaining(String user_1) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentResultByUserContaining
	 *
	 */
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByUserContaining(String user_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentResultByRemark
	 *
	 */
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByRemark(String remark) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentResultByRemark
	 *
	 */
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByRemark(String remark, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentResultByRemarkContaining
	 *
	 */
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByRemarkContaining(String remark_1) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentResultByRemarkContaining
	 *
	 */
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByRemarkContaining(String remark_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentResultByPrimaryKey
	 *
	 */
	public TimetableAppointmentResult findTimetableAppointmentResultByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentResultByPrimaryKey
	 *
	 */
	public TimetableAppointmentResult findTimetableAppointmentResultByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentResultByAuditResultContaining
	 *
	 */
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByAuditResultContaining(String auditResult_1) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentResultByAuditResultContaining
	 *
	 */
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByAuditResultContaining(String auditResult_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentResultById
	 *
	 */
	public TimetableAppointmentResult findTimetableAppointmentResultById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableAppointmentResultById
	 *
	 */
	public TimetableAppointmentResult findTimetableAppointmentResultById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}