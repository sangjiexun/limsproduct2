package net.zjcclims.dao;


import net.zjcclims.domain.EvaluationContent;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage EvaluationContent entities.
 * 
 */
public interface EvaluationContentDAO extends JpaDao<EvaluationContent> {

	/**
	 * JPQL Query - findEvaluationContentByScores
	 *
	 */
	public Set<EvaluationContent> findEvaluationContentByScores(Integer scores) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationContentByScores
	 *
	 */
	public Set<EvaluationContent> findEvaluationContentByScores(Integer scores, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationContentById
	 *
	 */
	public EvaluationContent findEvaluationContentById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationContentById
	 *
	 */
	public EvaluationContent findEvaluationContentById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationContentByPrimaryKey
	 *
	 */
	public EvaluationContent findEvaluationContentByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationContentByPrimaryKey
	 *
	 */
	public EvaluationContent findEvaluationContentByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationContentByMemo
	 *
	 */
	public Set<EvaluationContent> findEvaluationContentByMemo(String memo) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationContentByMemo
	 *
	 */
	public Set<EvaluationContent> findEvaluationContentByMemo(String memo, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationContentByMemoContaining
	 *
	 */
	public Set<EvaluationContent> findEvaluationContentByMemoContaining(String memo_1) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationContentByMemoContaining
	 *
	 */
	public Set<EvaluationContent> findEvaluationContentByMemoContaining(String memo_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationContentByOptionsContaining
	 *
	 */
	public Set<EvaluationContent> findEvaluationContentByOptionsContaining(String options) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationContentByOptionsContaining
	 *
	 */
	public Set<EvaluationContent> findEvaluationContentByOptionsContaining(String options, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationContentByStatus
	 *
	 */
	public Set<EvaluationContent> findEvaluationContentByStatus(Integer status) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationContentByStatus
	 *
	 */
	public Set<EvaluationContent> findEvaluationContentByStatus(Integer status, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllEvaluationContents
	 *
	 */
	public Set<EvaluationContent> findAllEvaluationContents() throws DataAccessException;

	/**
	 * JPQL Query - findAllEvaluationContents
	 *
	 */
	public Set<EvaluationContent> findAllEvaluationContents(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationContentByOptions
	 *
	 */
	public Set<EvaluationContent> findEvaluationContentByOptions(String options_1) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationContentByOptions
	 *
	 */
	public Set<EvaluationContent> findEvaluationContentByOptions(String options_1, int startResult, int maxRows) throws DataAccessException;

}