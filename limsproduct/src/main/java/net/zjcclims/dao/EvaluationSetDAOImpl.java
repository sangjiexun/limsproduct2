package net.zjcclims.dao;


import net.zjcclims.domain.EvaluationSet;
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
 * DAO to manage EvaluationSet entities.
 * 
 */
@Repository("EvaluationSetDAO")
@Transactional
public class EvaluationSetDAOImpl extends AbstractJpaDao<EvaluationSet>
		implements EvaluationSetDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { EvaluationSet.class }));

	/**
	 * EntityManager injected by Spring for persistence unit xzyxyConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new EvaluationSetDAOImpl
	 *
	 */
	public EvaluationSetDAOImpl() {
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
	 * JPQL Query - findEvaluationSetByStatus
	 *
	 */
	@Transactional
	public Set<EvaluationSet> findEvaluationSetByStatus(Integer status) throws DataAccessException {

		return findEvaluationSetByStatus(status, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationSetByStatus
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationSet> findEvaluationSetByStatus(Integer status, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationSetByStatus", startResult, maxRows, status);
		return new LinkedHashSet<EvaluationSet>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationSetByMemoContaining
	 *
	 */
	@Transactional
	public Set<EvaluationSet> findEvaluationSetByMemoContaining(String memo) throws DataAccessException {

		return findEvaluationSetByMemoContaining(memo, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationSetByMemoContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationSet> findEvaluationSetByMemoContaining(String memo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationSetByMemoContaining", startResult, maxRows, memo);
		return new LinkedHashSet<EvaluationSet>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationSetByMemo
	 *
	 */
	@Transactional
	public Set<EvaluationSet> findEvaluationSetByMemo(String memo) throws DataAccessException {

		return findEvaluationSetByMemo(memo, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationSetByMemo
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationSet> findEvaluationSetByMemo(String memo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationSetByMemo", startResult, maxRows, memo);
		return new LinkedHashSet<EvaluationSet>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationSetByPrimaryKey
	 *
	 */
	@Transactional
	public EvaluationSet findEvaluationSetByPrimaryKey(Integer id) throws DataAccessException {

		return findEvaluationSetByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationSetByPrimaryKey
	 *
	 */

	@Transactional
	public EvaluationSet findEvaluationSetByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findEvaluationSetByPrimaryKey", startResult, maxRows, id);
			return (EvaluationSet) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllEvaluationSets
	 *
	 */
	@Transactional
	public Set<EvaluationSet> findAllEvaluationSets() throws DataAccessException {

		return findAllEvaluationSets(-1, -1);
	}

	/**
	 * JPQL Query - findAllEvaluationSets
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationSet> findAllEvaluationSets(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllEvaluationSets", startResult, maxRows);
		return new LinkedHashSet<EvaluationSet>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationSetByStartTime
	 *
	 */
	@Transactional
	public Set<EvaluationSet> findEvaluationSetByStartTime(java.util.Calendar startTime) throws DataAccessException {

		return findEvaluationSetByStartTime(startTime, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationSetByStartTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationSet> findEvaluationSetByStartTime(java.util.Calendar startTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationSetByStartTime", startResult, maxRows, startTime);
		return new LinkedHashSet<EvaluationSet>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationSetByEndTime
	 *
	 */
	@Transactional
	public Set<EvaluationSet> findEvaluationSetByEndTime(java.util.Calendar endTime) throws DataAccessException {

		return findEvaluationSetByEndTime(endTime, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationSetByEndTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationSet> findEvaluationSetByEndTime(java.util.Calendar endTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationSetByEndTime", startResult, maxRows, endTime);
		return new LinkedHashSet<EvaluationSet>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationSetById
	 *
	 */
	@Transactional
	public EvaluationSet findEvaluationSetById(Integer id) throws DataAccessException {

		return findEvaluationSetById(id, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationSetById
	 *
	 */

	@Transactional
	public EvaluationSet findEvaluationSetById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findEvaluationSetById", startResult, maxRows, id);
			return (EvaluationSet) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findEvaluationSetByTermId
	 *
	 */
	@Transactional
	public Set<EvaluationSet> findEvaluationSetByTermId(Integer termId) throws DataAccessException {

		return findEvaluationSetByTermId(termId, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationSetByTermId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationSet> findEvaluationSetByTermId(Integer termId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationSetByTermId", startResult, maxRows, termId);
		return new LinkedHashSet<EvaluationSet>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(EvaluationSet entity) {
		return true;
	}
}
