package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.WorkerOption;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage WorkerOption entities.
 * 
 */
@Repository("WorkerOptionDAO")
@Transactional
public class WorkerOptionDAOImpl extends AbstractJpaDao<WorkerOption> implements
		WorkerOptionDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { WorkerOption.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new WorkerOptionDAOImpl
	 *
	 */
	public WorkerOptionDAOImpl() {
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
	 * JPQL Query - findAllWorkerOptions
	 *
	 */
	@Transactional
	public Set<WorkerOption> findAllWorkerOptions() throws DataAccessException {

		return findAllWorkerOptions(-1, -1);
	}

	/**
	 * JPQL Query - findAllWorkerOptions
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<WorkerOption> findAllWorkerOptions(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllWorkerOptions", startResult, maxRows);
		return new LinkedHashSet<WorkerOption>(query.getResultList());
	}

	/**
	 * JPQL Query - findWorkerOptionByPrimaryKey
	 *
	 */
	@Transactional
	public WorkerOption findWorkerOptionByPrimaryKey(Integer id) throws DataAccessException {

		return findWorkerOptionByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findWorkerOptionByPrimaryKey
	 *
	 */

	@Transactional
	public WorkerOption findWorkerOptionByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findWorkerOptionByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.WorkerOption) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findWorkerOptionByWorker
	 *
	 */
	@Transactional
	public Set<WorkerOption> findWorkerOptionByWorker(Integer worker) throws DataAccessException {

		return findWorkerOptionByWorker(worker, -1, -1);
	}

	/**
	 * JPQL Query - findWorkerOptionByWorker
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<WorkerOption> findWorkerOptionByWorker(Integer worker, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findWorkerOptionByWorker", startResult, maxRows, worker);
		return new LinkedHashSet<WorkerOption>(query.getResultList());
	}

	/**
	 * JPQL Query - findWorkerOptionById
	 *
	 */
	@Transactional
	public WorkerOption findWorkerOptionById(Integer id) throws DataAccessException {

		return findWorkerOptionById(id, -1, -1);
	}

	/**
	 * JPQL Query - findWorkerOptionById
	 *
	 */

	@Transactional
	public WorkerOption findWorkerOptionById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findWorkerOptionById", startResult, maxRows, id);
			return (net.zjcclims.domain.WorkerOption) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(WorkerOption entity) {
		return true;
	}
}
