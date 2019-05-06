package net.zjcclims.dao;


import net.zjcclims.domain.EvaluationSet;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Calendar;
import java.util.Set;

/**
 * DAO to manage EvaluationSet entities.
 * 
 */
public interface EvaluationSetDAO extends JpaDao<EvaluationSet> {

	/**
	 * JPQL Query - findEvaluationSetByStatus
	 *
	 */
	public Set<EvaluationSet> findEvaluationSetByStatus(Integer status) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSetByStatus
	 *
	 */
	public Set<EvaluationSet> findEvaluationSetByStatus(Integer status, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSetByMemoContaining
	 *
	 */
	public Set<EvaluationSet> findEvaluationSetByMemoContaining(String memo) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSetByMemoContaining
	 *
	 */
	public Set<EvaluationSet> findEvaluationSetByMemoContaining(String memo, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSetByMemo
	 *
	 */
	public Set<EvaluationSet> findEvaluationSetByMemo(String memo_1) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSetByMemo
	 *
	 */
	public Set<EvaluationSet> findEvaluationSetByMemo(String memo_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSetByPrimaryKey
	 *
	 */
	public EvaluationSet findEvaluationSetByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSetByPrimaryKey
	 *
	 */
	public EvaluationSet findEvaluationSetByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllEvaluationSets
	 *
	 */
	public Set<EvaluationSet> findAllEvaluationSets() throws DataAccessException;

	/**
	 * JPQL Query - findAllEvaluationSets
	 *
	 */
	public Set<EvaluationSet> findAllEvaluationSets(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSetByStartTime
	 *
	 */
	public Set<EvaluationSet> findEvaluationSetByStartTime(Calendar startTime) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSetByStartTime
	 *
	 */
	public Set<EvaluationSet> findEvaluationSetByStartTime(Calendar startTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSetByEndTime
	 *
	 */
	public Set<EvaluationSet> findEvaluationSetByEndTime(Calendar endTime) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSetByEndTime
	 *
	 */
	public Set<EvaluationSet> findEvaluationSetByEndTime(Calendar endTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSetById
	 *
	 */
	public EvaluationSet findEvaluationSetById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSetById
	 *
	 */
	public EvaluationSet findEvaluationSetById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSetByTermId
	 *
	 */
	public Set<EvaluationSet> findEvaluationSetByTermId(Integer termId) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSetByTermId
	 *
	 */
	public Set<EvaluationSet> findEvaluationSetByTermId(Integer termId, int startResult, int maxRows) throws DataAccessException;

}