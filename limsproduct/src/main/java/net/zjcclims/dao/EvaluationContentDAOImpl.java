package net.zjcclims.dao;


import net.zjcclims.domain.EvaluationContent;
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
 * DAO to manage EvaluationContent entities.
 * 
 */
@Repository("EvaluationContentDAO")
@Transactional
public class EvaluationContentDAOImpl extends AbstractJpaDao<EvaluationContent>
		implements EvaluationContentDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { EvaluationContent.class }));

	/**
	 * EntityManager injected by Spring for persistence unit xzyxyConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new EvaluationContentDAOImpl
	 *
	 */
	public EvaluationContentDAOImpl() {
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
	 * JPQL Query - findEvaluationContentByScores
	 *
	 */
	@Transactional
	public Set<EvaluationContent> findEvaluationContentByScores(Integer scores) throws DataAccessException {

		return findEvaluationContentByScores(scores, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationContentByScores
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationContent> findEvaluationContentByScores(Integer scores, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationContentByScores", startResult, maxRows, scores);
		return new LinkedHashSet<EvaluationContent>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationContentById
	 *
	 */
	@Transactional
	public EvaluationContent findEvaluationContentById(Integer id) throws DataAccessException {

		return findEvaluationContentById(id, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationContentById
	 *
	 */

	@Transactional
	public EvaluationContent findEvaluationContentById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findEvaluationContentById", startResult, maxRows, id);
			return (EvaluationContent) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findEvaluationContentByPrimaryKey
	 *
	 */
	@Transactional
	public EvaluationContent findEvaluationContentByPrimaryKey(Integer id) throws DataAccessException {

		return findEvaluationContentByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationContentByPrimaryKey
	 *
	 */

	@Transactional
	public EvaluationContent findEvaluationContentByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findEvaluationContentByPrimaryKey", startResult, maxRows, id);
			return (EvaluationContent) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findEvaluationContentByMemo
	 *
	 */
	@Transactional
	public Set<EvaluationContent> findEvaluationContentByMemo(String memo) throws DataAccessException {

		return findEvaluationContentByMemo(memo, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationContentByMemo
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationContent> findEvaluationContentByMemo(String memo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationContentByMemo", startResult, maxRows, memo);
		return new LinkedHashSet<EvaluationContent>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationContentByMemoContaining
	 *
	 */
	@Transactional
	public Set<EvaluationContent> findEvaluationContentByMemoContaining(String memo) throws DataAccessException {

		return findEvaluationContentByMemoContaining(memo, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationContentByMemoContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationContent> findEvaluationContentByMemoContaining(String memo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationContentByMemoContaining", startResult, maxRows, memo);
		return new LinkedHashSet<EvaluationContent>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationContentByOptionsContaining
	 *
	 */
	@Transactional
	public Set<EvaluationContent> findEvaluationContentByOptionsContaining(String options) throws DataAccessException {

		return findEvaluationContentByOptionsContaining(options, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationContentByOptionsContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationContent> findEvaluationContentByOptionsContaining(String options, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationContentByOptionsContaining", startResult, maxRows, options);
		return new LinkedHashSet<EvaluationContent>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationContentByStatus
	 *
	 */
	@Transactional
	public Set<EvaluationContent> findEvaluationContentByStatus(Integer status) throws DataAccessException {

		return findEvaluationContentByStatus(status, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationContentByStatus
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationContent> findEvaluationContentByStatus(Integer status, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationContentByStatus", startResult, maxRows, status);
		return new LinkedHashSet<EvaluationContent>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllEvaluationContents
	 *
	 */
	@Transactional
	public Set<EvaluationContent> findAllEvaluationContents() throws DataAccessException {

		return findAllEvaluationContents(-1, -1);
	}

	/**
	 * JPQL Query - findAllEvaluationContents
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationContent> findAllEvaluationContents(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllEvaluationContents", startResult, maxRows);
		return new LinkedHashSet<EvaluationContent>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationContentByOptions
	 *
	 */
	@Transactional
	public Set<EvaluationContent> findEvaluationContentByOptions(String options) throws DataAccessException {

		return findEvaluationContentByOptions(options, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationContentByOptions
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationContent> findEvaluationContentByOptions(String options, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationContentByOptions", startResult, maxRows, options);
		return new LinkedHashSet<EvaluationContent>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(EvaluationContent entity) {
		return true;
	}
}
