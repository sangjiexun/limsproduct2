package net.zjcclims.dao;


import net.zjcclims.domain.EvaluationResult;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Calendar;
import java.util.Set;

/**
 * DAO to manage EvaluationResult entities.
 * 
 */
public interface EvaluationResultDAO extends JpaDao<EvaluationResult> {

	/**
	 * JPQL Query - findEvaluationResultByTeacherNameContaining
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByTeacherNameContaining(String teacherName) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByTeacherNameContaining
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByTeacherNameContaining(String teacherName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByMemo
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByMemo(String memo) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByMemo
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByMemo(String memo, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByScore
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByScore(Integer score) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByScore
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByScore(Integer score, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByTeacherNoContaining
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByTeacherNoContaining(String teacherNo) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByTeacherNoContaining
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByTeacherNoContaining(String teacherNo, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByCreateTime
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByCreateTime(Calendar createTime) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByCreateTime
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByCreateTime(Calendar createTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByStudentNoContaining
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByStudentNoContaining(String studentNo) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByStudentNoContaining
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByStudentNoContaining(String studentNo, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultById
	 *
	 */
	public EvaluationResult findEvaluationResultById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultById
	 *
	 */
	public EvaluationResult findEvaluationResultById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByStudentName
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByStudentName(String studentName) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByStudentName
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByStudentName(String studentName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllEvaluationResults
	 *
	 */
	public Set<EvaluationResult> findAllEvaluationResults() throws DataAccessException;

	/**
	 * JPQL Query - findAllEvaluationResults
	 *
	 */
	public Set<EvaluationResult> findAllEvaluationResults(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByTermId
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByTermId(Integer termId) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByTermId
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByTermId(Integer termId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByTeacherNo
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByTeacherNo(String teacherNo_1) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByTeacherNo
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByTeacherNo(String teacherNo_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByPrimaryKey
	 *
	 */
	public EvaluationResult findEvaluationResultByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByPrimaryKey
	 *
	 */
	public EvaluationResult findEvaluationResultByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByStudentNameContaining
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByStudentNameContaining(String studentName_1) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByStudentNameContaining
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByStudentNameContaining(String studentName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByTeacherName
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByTeacherName(String teacherName_1) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByTeacherName
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByTeacherName(String teacherName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByMemoContaining
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByMemoContaining(String memo_1) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByMemoContaining
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByMemoContaining(String memo_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByStudentNo
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByStudentNo(String studentNo_1) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationResultByStudentNo
	 *
	 */
	public Set<EvaluationResult> findEvaluationResultByStudentNo(String studentNo_1, int startResult, int maxRows) throws DataAccessException;

}