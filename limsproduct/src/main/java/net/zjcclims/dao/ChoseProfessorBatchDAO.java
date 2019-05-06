package net.zjcclims.dao;

import net.zjcclims.domain.ChoseProfessorBatch;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Calendar;
import java.util.Set;

/**
 * DAO to manage ChoseProfessorBatch entities.
 * 
 */
public interface ChoseProfessorBatchDAO extends JpaDao<ChoseProfessorBatch> {

	/**
	 * JPQL Query - findAllChoseProfessorBatchs
	 *
	 */
	public Set<ChoseProfessorBatch> findAllChoseProfessorBatchs() throws DataAccessException;

	/**
	 * JPQL Query - findAllChoseProfessorBatchs
	 *
	 */
	public Set<ChoseProfessorBatch> findAllChoseProfessorBatchs(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorBatchByBatchNumber
	 *
	 */
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByBatchNumber(Integer batchNumber) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorBatchByBatchNumber
	 *
	 */
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByBatchNumber(Integer batchNumber, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorBatchByEndTime
	 *
	 */
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByEndTime(java.util.Calendar endTime) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorBatchByEndTime
	 *
	 */
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByEndTime(Calendar endTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorBatchByStartTimeBefore
	 *
	 */
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByStartTimeBefore(java.util.Calendar startTime) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorBatchByStartTimeBefore
	 *
	 */
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByStartTimeBefore(Calendar startTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorBatchById
	 *
	 */
	public ChoseProfessorBatch findChoseProfessorBatchById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorBatchById
	 *
	 */
	public ChoseProfessorBatch findChoseProfessorBatchById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorBatchByStartTime
	 *
	 */
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByStartTime(java.util.Calendar startTime_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorBatchByStartTime
	 *
	 */
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByStartTime(Calendar startTime_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorBatchByPrimaryKey
	 *
	 */
	public ChoseProfessorBatch findChoseProfessorBatchByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorBatchByPrimaryKey
	 *
	 */
	public ChoseProfessorBatch findChoseProfessorBatchByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorBatchByStartTimeAfter
	 *
	 */
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByStartTimeAfter(java.util.Calendar startTime_2) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorBatchByStartTimeAfter
	 *
	 */
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByStartTimeAfter(Calendar startTime_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorBatchByEndTimeBefore
	 *
	 */
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByEndTimeBefore(java.util.Calendar endTime_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorBatchByEndTimeBefore
	 *
	 */
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByEndTimeBefore(Calendar endTime_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorBatchByEndTimeAfter
	 *
	 */
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByEndTimeAfter(java.util.Calendar endTime_2) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorBatchByEndTimeAfter
	 *
	 */
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByEndTimeAfter(Calendar endTime_2, int startResult, int maxRows) throws DataAccessException;

}