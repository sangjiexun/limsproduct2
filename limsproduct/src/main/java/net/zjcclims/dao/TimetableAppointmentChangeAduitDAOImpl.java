package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.TimetableAppointmentChangeAduit;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage TimetableAppointmentChangeAduit entities.
 * 
 */
@Repository("TimetableAppointmentChangeAduitDAO")
@Transactional
public class TimetableAppointmentChangeAduitDAOImpl extends AbstractJpaDao<TimetableAppointmentChangeAduit>
		implements TimetableAppointmentChangeAduitDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { TimetableAppointmentChangeAduit.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new TimetableAppointmentChangeAduitDAOImpl
	 *
	 */
	public TimetableAppointmentChangeAduitDAOImpl() {
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
	 * JPQL Query - findTimetableAppointmentChangeAduitByMemContaining
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByMemContaining(String mem) throws DataAccessException {

		return findTimetableAppointmentChangeAduitByMemContaining(mem, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByMemContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByMemContaining(String mem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentChangeAduitByMemContaining", startResult, maxRows, mem);
		return new LinkedHashSet<TimetableAppointmentChangeAduit>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByStatus
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByStatus(Integer status) throws DataAccessException {

		return findTimetableAppointmentChangeAduitByStatus(status, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByStatus
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByStatus(Integer status, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentChangeAduitByStatus", startResult, maxRows, status);
		return new LinkedHashSet<TimetableAppointmentChangeAduit>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByResultContaining
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByResultContaining(String result) throws DataAccessException {

		return findTimetableAppointmentChangeAduitByResultContaining(result, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByResultContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByResultContaining(String result, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentChangeAduitByResultContaining", startResult, maxRows, result);
		return new LinkedHashSet<TimetableAppointmentChangeAduit>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByResult
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByResult(String result) throws DataAccessException {

		return findTimetableAppointmentChangeAduitByResult(result, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByResult
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByResult(String result, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentChangeAduitByResult", startResult, maxRows, result);
		return new LinkedHashSet<TimetableAppointmentChangeAduit>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByCreateDateBefore
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByCreateDateBefore(java.util.Calendar createDate) throws DataAccessException {

		return findTimetableAppointmentChangeAduitByCreateDateBefore(createDate, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByCreateDateBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByCreateDateBefore(java.util.Calendar createDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentChangeAduitByCreateDateBefore", startResult, maxRows, createDate);
		return new LinkedHashSet<TimetableAppointmentChangeAduit>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByPrimaryKey
	 *
	 */
	@Transactional
	public TimetableAppointmentChangeAduit findTimetableAppointmentChangeAduitByPrimaryKey(Integer id) throws DataAccessException {

		return findTimetableAppointmentChangeAduitByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByPrimaryKey
	 *
	 */

	@Transactional
	public TimetableAppointmentChangeAduit findTimetableAppointmentChangeAduitByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findTimetableAppointmentChangeAduitByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.TimetableAppointmentChangeAduit) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByCreateDate
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByCreateDate(java.util.Calendar createDate) throws DataAccessException {

		return findTimetableAppointmentChangeAduitByCreateDate(createDate, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByCreateDate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByCreateDate(java.util.Calendar createDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentChangeAduitByCreateDate", startResult, maxRows, createDate);
		return new LinkedHashSet<TimetableAppointmentChangeAduit>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByMem
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByMem(String mem) throws DataAccessException {

		return findTimetableAppointmentChangeAduitByMem(mem, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByMem
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByMem(String mem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentChangeAduitByMem", startResult, maxRows, mem);
		return new LinkedHashSet<TimetableAppointmentChangeAduit>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllTimetableAppointmentChangeAduits
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChangeAduit> findAllTimetableAppointmentChangeAduits() throws DataAccessException {

		return findAllTimetableAppointmentChangeAduits(-1, -1);
	}

	/**
	 * JPQL Query - findAllTimetableAppointmentChangeAduits
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChangeAduit> findAllTimetableAppointmentChangeAduits(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllTimetableAppointmentChangeAduits", startResult, maxRows);
		return new LinkedHashSet<TimetableAppointmentChangeAduit>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByCreateDateAfter
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByCreateDateAfter(java.util.Calendar createDate) throws DataAccessException {

		return findTimetableAppointmentChangeAduitByCreateDateAfter(createDate, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitByCreateDateAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChangeAduit> findTimetableAppointmentChangeAduitByCreateDateAfter(java.util.Calendar createDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentChangeAduitByCreateDateAfter", startResult, maxRows, createDate);
		return new LinkedHashSet<TimetableAppointmentChangeAduit>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitById
	 *
	 */
	@Transactional
	public TimetableAppointmentChangeAduit findTimetableAppointmentChangeAduitById(Integer id) throws DataAccessException {

		return findTimetableAppointmentChangeAduitById(id, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeAduitById
	 *
	 */

	@Transactional
	public TimetableAppointmentChangeAduit findTimetableAppointmentChangeAduitById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findTimetableAppointmentChangeAduitById", startResult, maxRows, id);
			return (net.zjcclims.domain.TimetableAppointmentChangeAduit) query.getSingleResult();
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
	public boolean canBeMerged(TimetableAppointmentChangeAduit entity) {
		return true;
	}
}
