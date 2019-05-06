package net.zjcclims.dao;

import net.zjcclims.domain.LabInspectGrading;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Calendar;
import java.util.Set;

/**
 * DAO to manage LabInspectGrading entities.
 *
 */
public interface LabInspectGradingDAO extends JpaDao<LabInspectGrading> {

	/**
	 * JPQL Query - findLabInspectGradingByPrimaryKey
	 *
	 */
	public LabInspectGrading findLabInspectGradingByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectGradingByPrimaryKey
	 *
	 */
	public LabInspectGrading findLabInspectGradingByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectGradingByLabRoomId
	 *
	 */
	public Set<LabInspectGrading> findLabInspectGradingByLabRoomId(Integer labRoomId) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectGradingByLabRoomId
	 *
	 */
	public Set<LabInspectGrading> findLabInspectGradingByLabRoomId(Integer labRoomId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectGradingByCreateTime
	 *
	 */
	public Set<LabInspectGrading> findLabInspectGradingByCreateTime(Calendar createTime) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectGradingByCreateTime
	 *
	 */
	public Set<LabInspectGrading> findLabInspectGradingByCreateTime(Calendar createTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectGradingByStandardId
	 *
	 */
	public Set<LabInspectGrading> findLabInspectGradingByStandardId(Integer standardId) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectGradingByStandardId
	 *
	 */
	public Set<LabInspectGrading> findLabInspectGradingByStandardId(Integer standardId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectGradingByBatchId
	 *
	 */
	public Set<LabInspectGrading> findLabInspectGradingByBatchId(Integer batchId) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectGradingByBatchId
	 *
	 */
	public Set<LabInspectGrading> findLabInspectGradingByBatchId(Integer batchId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabInspectGradings
	 *
	 */
	public Set<LabInspectGrading> findAllLabInspectGradings() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabInspectGradings
	 *
	 */
	public Set<LabInspectGrading> findAllLabInspectGradings(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectGradingById
	 *
	 */
	public LabInspectGrading findLabInspectGradingById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectGradingById
	 *
	 */
	public LabInspectGrading findLabInspectGradingById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectGradingByDocumentId
	 *
	 */
	public Set<LabInspectGrading> findLabInspectGradingByDocumentId(Integer documentId) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectGradingByDocumentId
	 *
	 */
	public Set<LabInspectGrading> findLabInspectGradingByDocumentId(Integer documentId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectGradingByPoint
	 *
	 */
	public Set<LabInspectGrading> findLabInspectGradingByPoint(Integer point) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectGradingByPoint
	 *
	 */
	public Set<LabInspectGrading> findLabInspectGradingByPoint(Integer point, int startResult, int maxRows) throws DataAccessException;

}