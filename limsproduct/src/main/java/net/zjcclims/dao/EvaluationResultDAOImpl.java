package net.zjcclims.dao;


import net.zjcclims.domain.EvaluationResult;
import org.skyway.spring.util.dao.AbstractJpaDao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * DAO to manage EvaluationResult entities.
 * 
 */
@Repository("EvaluationResultDAO")
@Transactional
public class EvaluationResultDAOImpl extends AbstractJpaDao<EvaluationResult>
		implements EvaluationResultDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { EvaluationResult.class }));

	/**
	 * EntityManager injected by Spring for persistence unit xzyxyConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new EvaluationResultDAOImpl
	 *
	 */
	public EvaluationResultDAOImpl() {
		super();
	}

	/**
	 * Get the entity manager that manages persistence unit 
	 *
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Returns the set of entity classes managed by this DAO.
	 *
	 */
	public Set<Class<?>> getTypes() {
		return dataTypes;
	}

	/**
	 * JPQL Query - findEvaluationResultByTeacherNameContaining
	 *
	 */
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByTeacherNameContaining(String teacherName) throws DataAccessException {

		return findEvaluationResultByTeacherNameContaining(teacherName, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationResultByTeacherNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByTeacherNameContaining(String teacherName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationResultByTeacherNameContaining", startResult, maxRows, teacherName);
		return new LinkedHashSet<EvaluationResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationResultByMemo
	 *
	 */
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByMemo(String memo) throws DataAccessException {

		return findEvaluationResultByMemo(memo, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationResultByMemo
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByMemo(String memo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationResultByMemo", startResult, maxRows, memo);
		return new LinkedHashSet<EvaluationResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationResultByScore
	 *
	 */
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByScore(Integer score) throws DataAccessException {

		return findEvaluationResultByScore(score, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationResultByScore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByScore(Integer score, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationResultByScore", startResult, maxRows, score);
		return new LinkedHashSet<EvaluationResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationResultByTeacherNoContaining
	 *
	 */
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByTeacherNoContaining(String teacherNo) throws DataAccessException {

		return findEvaluationResultByTeacherNoContaining(teacherNo, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationResultByTeacherNoContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByTeacherNoContaining(String teacherNo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationResultByTeacherNoContaining", startResult, maxRows, teacherNo);
		return new LinkedHashSet<EvaluationResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationResultByCreateTime
	 *
	 */
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByCreateTime(java.util.Calendar createTime) throws DataAccessException {

		return findEvaluationResultByCreateTime(createTime, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationResultByCreateTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByCreateTime(java.util.Calendar createTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationResultByCreateTime", startResult, maxRows, createTime);
		return new LinkedHashSet<EvaluationResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationResultByStudentNoContaining
	 *
	 */
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByStudentNoContaining(String studentNo) throws DataAccessException {

		return findEvaluationResultByStudentNoContaining(studentNo, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationResultByStudentNoContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByStudentNoContaining(String studentNo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationResultByStudentNoContaining", startResult, maxRows, studentNo);
		return new LinkedHashSet<EvaluationResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationResultById
	 *
	 */
	@Transactional
	public EvaluationResult findEvaluationResultById(Integer id) throws DataAccessException {

		return findEvaluationResultById(id, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationResultById
	 *
	 */

	@Transactional
	public EvaluationResult findEvaluationResultById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findEvaluationResultById", startResult, maxRows, id);
			return (EvaluationResult) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findEvaluationResultByStudentName
	 *
	 */
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByStudentName(String studentName) throws DataAccessException {

		return findEvaluationResultByStudentName(studentName, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationResultByStudentName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByStudentName(String studentName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationResultByStudentName", startResult, maxRows, studentName);
		return new LinkedHashSet<EvaluationResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllEvaluationResults
	 *
	 */
	@Transactional
	public Set<EvaluationResult> findAllEvaluationResults() throws DataAccessException {

		return findAllEvaluationResults(-1, -1);
	}

	/**
	 * JPQL Query - findAllEvaluationResults
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationResult> findAllEvaluationResults(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllEvaluationResults", startResult, maxRows);
		return new LinkedHashSet<EvaluationResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationResultByTermId
	 *
	 */
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByTermId(Integer termId) throws DataAccessException {

		return findEvaluationResultByTermId(termId, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationResultByTermId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByTermId(Integer termId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationResultByTermId", startResult, maxRows, termId);
		return new LinkedHashSet<EvaluationResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationResultByTeacherNo
	 *
	 */
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByTeacherNo(String teacherNo) throws DataAccessException {

		return findEvaluationResultByTeacherNo(teacherNo, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationResultByTeacherNo
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByTeacherNo(String teacherNo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationResultByTeacherNo", startResult, maxRows, teacherNo);
		return new LinkedHashSet<EvaluationResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationResultByPrimaryKey
	 *
	 */
	@Transactional
	public EvaluationResult findEvaluationResultByPrimaryKey(Integer id) throws DataAccessException {

		return findEvaluationResultByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationResultByPrimaryKey
	 *
	 */

	@Transactional
	public EvaluationResult findEvaluationResultByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findEvaluationResultByPrimaryKey", startResult, maxRows, id);
			return (EvaluationResult) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findEvaluationResultByStudentNameContaining
	 *
	 */
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByStudentNameContaining(String studentName) throws DataAccessException {

		return findEvaluationResultByStudentNameContaining(studentName, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationResultByStudentNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByStudentNameContaining(String studentName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationResultByStudentNameContaining", startResult, maxRows, studentName);
		return new LinkedHashSet<EvaluationResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationResultByTeacherName
	 *
	 */
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByTeacherName(String teacherName) throws DataAccessException {

		return findEvaluationResultByTeacherName(teacherName, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationResultByTeacherName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByTeacherName(String teacherName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationResultByTeacherName", startResult, maxRows, teacherName);
		return new LinkedHashSet<EvaluationResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationResultByMemoContaining
	 *
	 */
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByMemoContaining(String memo) throws DataAccessException {

		return findEvaluationResultByMemoContaining(memo, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationResultByMemoContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByMemoContaining(String memo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationResultByMemoContaining", startResult, maxRows, memo);
		return new LinkedHashSet<EvaluationResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationResultByStudentNo
	 *
	 */
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByStudentNo(String studentNo) throws DataAccessException {

		return findEvaluationResultByStudentNo(studentNo, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationResultByStudentNo
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationResult> findEvaluationResultByStudentNo(String studentNo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationResultByStudentNo", startResult, maxRows, studentNo);
		return new LinkedHashSet<EvaluationResult>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(EvaluationResult entity) {
		return true;
	}
}
