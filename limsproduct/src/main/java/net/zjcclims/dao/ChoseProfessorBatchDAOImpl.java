package net.zjcclims.dao;

import net.zjcclims.domain.ChoseProfessorBatch;
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
 * DAO to manage ChoseProfessorBatch entities.
 * 
 */
@Repository("ChoseProfessorBatchDAO")
@Transactional
public class ChoseProfessorBatchDAOImpl extends AbstractJpaDao<ChoseProfessorBatch>
		implements ChoseProfessorBatchDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ChoseProfessorBatch.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ChoseProfessorBatchDAOImpl
	 *
	 */
	public ChoseProfessorBatchDAOImpl() {
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
	 * JPQL Query - findAllChoseProfessorBatchs
	 *
	 */
	@Transactional
	public Set<ChoseProfessorBatch> findAllChoseProfessorBatchs() throws DataAccessException {

		return findAllChoseProfessorBatchs(-1, -1);
	}

	/**
	 * JPQL Query - findAllChoseProfessorBatchs
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseProfessorBatch> findAllChoseProfessorBatchs(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllChoseProfessorBatchs", startResult, maxRows);
		return new LinkedHashSet<ChoseProfessorBatch>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseProfessorBatchByBatchNumber
	 *
	 */
	@Transactional
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByBatchNumber(Integer batchNumber) throws DataAccessException {

		return findChoseProfessorBatchByBatchNumber(batchNumber, -1, -1);
	}

	/**
	 * JPQL Query - findChoseProfessorBatchByBatchNumber
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByBatchNumber(Integer batchNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseProfessorBatchByBatchNumber", startResult, maxRows, batchNumber);
		return new LinkedHashSet<ChoseProfessorBatch>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseProfessorBatchByEndTime
	 *
	 */
	@Transactional
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByEndTime(java.util.Calendar endTime) throws DataAccessException {

		return findChoseProfessorBatchByEndTime(endTime, -1, -1);
	}

	/**
	 * JPQL Query - findChoseProfessorBatchByEndTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByEndTime(java.util.Calendar endTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseProfessorBatchByEndTime", startResult, maxRows, endTime);
		return new LinkedHashSet<ChoseProfessorBatch>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseProfessorBatchByStartTimeBefore
	 *
	 */
	@Transactional
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByStartTimeBefore(java.util.Calendar startTime) throws DataAccessException {

		return findChoseProfessorBatchByStartTimeBefore(startTime, -1, -1);
	}

	/**
	 * JPQL Query - findChoseProfessorBatchByStartTimeBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByStartTimeBefore(java.util.Calendar startTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseProfessorBatchByStartTimeBefore", startResult, maxRows, startTime);
		return new LinkedHashSet<ChoseProfessorBatch>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseProfessorBatchById
	 *
	 */
	@Transactional
	public ChoseProfessorBatch findChoseProfessorBatchById(Integer id) throws DataAccessException {

		return findChoseProfessorBatchById(id, -1, -1);
	}

	/**
	 * JPQL Query - findChoseProfessorBatchById
	 *
	 */

	@Transactional
	public ChoseProfessorBatch findChoseProfessorBatchById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findChoseProfessorBatchById", startResult, maxRows, id);
			return (net.zjcclims.domain.ChoseProfessorBatch) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findChoseProfessorBatchByStartTime
	 *
	 */
	@Transactional
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByStartTime(java.util.Calendar startTime) throws DataAccessException {

		return findChoseProfessorBatchByStartTime(startTime, -1, -1);
	}

	/**
	 * JPQL Query - findChoseProfessorBatchByStartTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByStartTime(java.util.Calendar startTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseProfessorBatchByStartTime", startResult, maxRows, startTime);
		return new LinkedHashSet<ChoseProfessorBatch>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseProfessorBatchByPrimaryKey
	 *
	 */
	@Transactional
	public ChoseProfessorBatch findChoseProfessorBatchByPrimaryKey(Integer id) throws DataAccessException {

		return findChoseProfessorBatchByPrimaryKey(id, 0, -1);
	}

	/**
	 * JPQL Query - findChoseProfessorBatchByPrimaryKey
	 *
	 */

	@Transactional
	public ChoseProfessorBatch findChoseProfessorBatchByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findChoseProfessorBatchByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.ChoseProfessorBatch) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findChoseProfessorBatchByStartTimeAfter
	 *
	 */
	@Transactional
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByStartTimeAfter(java.util.Calendar startTime) throws DataAccessException {

		return findChoseProfessorBatchByStartTimeAfter(startTime, -1, -1);
	}

	/**
	 * JPQL Query - findChoseProfessorBatchByStartTimeAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByStartTimeAfter(java.util.Calendar startTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseProfessorBatchByStartTimeAfter", startResult, maxRows, startTime);
		return new LinkedHashSet<ChoseProfessorBatch>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseProfessorBatchByEndTimeBefore
	 *
	 */
	@Transactional
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByEndTimeBefore(java.util.Calendar endTime) throws DataAccessException {

		return findChoseProfessorBatchByEndTimeBefore(endTime, -1, -1);
	}

	/**
	 * JPQL Query - findChoseProfessorBatchByEndTimeBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByEndTimeBefore(java.util.Calendar endTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseProfessorBatchByEndTimeBefore", startResult, maxRows, endTime);
		return new LinkedHashSet<ChoseProfessorBatch>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseProfessorBatchByEndTimeAfter
	 *
	 */
	@Transactional
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByEndTimeAfter(java.util.Calendar endTime) throws DataAccessException {

		return findChoseProfessorBatchByEndTimeAfter(endTime, -1, -1);
	}

	/**
	 * JPQL Query - findChoseProfessorBatchByEndTimeAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseProfessorBatch> findChoseProfessorBatchByEndTimeAfter(java.util.Calendar endTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseProfessorBatchByEndTimeAfter", startResult, maxRows, endTime);
		return new LinkedHashSet<ChoseProfessorBatch>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(ChoseProfessorBatch entity) {
		return true;
	}
}
