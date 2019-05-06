package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.TimetableAppointmentChange;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage TimetableAppointmentChange entities.
 * 
 */
@Repository("TimetableAppointmentChangeDAO")
@Transactional
public class TimetableAppointmentChangeDAOImpl extends AbstractJpaDao<TimetableAppointmentChange>
		implements TimetableAppointmentChangeDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { TimetableAppointmentChange.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new TimetableAppointmentChangeDAOImpl
	 *
	 */
	public TimetableAppointmentChangeDAOImpl() {
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
	 * JPQL Query - findTimetableAppointmentChangeByWeek
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByWeek(Integer week) throws DataAccessException {

		return findTimetableAppointmentChangeByWeek(week, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByWeek
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByWeek(Integer week, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentChangeByWeek", startResult, maxRows, week);
		return new LinkedHashSet<TimetableAppointmentChange>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByWeekday
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByWeekday(Integer weekday) throws DataAccessException {

		return findTimetableAppointmentChangeByWeekday(weekday, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByWeekday
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByWeekday(Integer weekday, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentChangeByWeekday", startResult, maxRows, weekday);
		return new LinkedHashSet<TimetableAppointmentChange>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByStartClass
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByStartClass(Integer startClass) throws DataAccessException {

		return findTimetableAppointmentChangeByStartClass(startClass, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByStartClass
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByStartClass(Integer startClass, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentChangeByStartClass", startResult, maxRows, startClass);
		return new LinkedHashSet<TimetableAppointmentChange>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByEndClass
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByEndClass(Integer endClass) throws DataAccessException {

		return findTimetableAppointmentChangeByEndClass(endClass, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByEndClass
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByEndClass(Integer endClass, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentChangeByEndClass", startResult, maxRows, endClass);
		return new LinkedHashSet<TimetableAppointmentChange>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllTimetableAppointmentChanges
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChange> findAllTimetableAppointmentChanges() throws DataAccessException {

		return findAllTimetableAppointmentChanges(-1, -1);
	}

	/**
	 * JPQL Query - findAllTimetableAppointmentChanges
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChange> findAllTimetableAppointmentChanges(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllTimetableAppointmentChanges", startResult, maxRows);
		return new LinkedHashSet<TimetableAppointmentChange>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByItemNameContaining
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByItemNameContaining(String itemName) throws DataAccessException {

		return findTimetableAppointmentChangeByItemNameContaining(itemName, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByItemNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByItemNameContaining(String itemName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentChangeByItemNameContaining", startResult, maxRows, itemName);
		return new LinkedHashSet<TimetableAppointmentChange>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByAddressContaining
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByAddressContaining(String address) throws DataAccessException {

		return findTimetableAppointmentChangeByAddressContaining(address, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByAddressContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByAddressContaining(String address, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentChangeByAddressContaining", startResult, maxRows, address);
		return new LinkedHashSet<TimetableAppointmentChange>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByAddress
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByAddress(String address) throws DataAccessException {

		return findTimetableAppointmentChangeByAddress(address, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByAddress
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByAddress(String address, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentChangeByAddress", startResult, maxRows, address);
		return new LinkedHashSet<TimetableAppointmentChange>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByStatus
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByStatus(Integer status) throws DataAccessException {

		return findTimetableAppointmentChangeByStatus(status, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByStatus
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByStatus(Integer status, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentChangeByStatus", startResult, maxRows, status);
		return new LinkedHashSet<TimetableAppointmentChange>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByCauseContaining
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByCauseContaining(String cause) throws DataAccessException {

		return findTimetableAppointmentChangeByCauseContaining(cause, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByCauseContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByCauseContaining(String cause, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentChangeByCauseContaining", startResult, maxRows, cause);
		return new LinkedHashSet<TimetableAppointmentChange>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByFlag
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByFlag(Integer flag) throws DataAccessException {

		return findTimetableAppointmentChangeByFlag(flag, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByFlag
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByFlag(Integer flag, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentChangeByFlag", startResult, maxRows, flag);
		return new LinkedHashSet<TimetableAppointmentChange>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByItemName
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByItemName(String itemName) throws DataAccessException {

		return findTimetableAppointmentChangeByItemName(itemName, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByItemName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByItemName(String itemName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentChangeByItemName", startResult, maxRows, itemName);
		return new LinkedHashSet<TimetableAppointmentChange>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeById
	 *
	 */
	@Transactional
	public TimetableAppointmentChange findTimetableAppointmentChangeById(Integer id) throws DataAccessException {

		return findTimetableAppointmentChangeById(id, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeById
	 *
	 */

	@Transactional
	public TimetableAppointmentChange findTimetableAppointmentChangeById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findTimetableAppointmentChangeById", startResult, maxRows, id);
			return (net.zjcclims.domain.TimetableAppointmentChange) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByCause
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByCause(String cause) throws DataAccessException {

		return findTimetableAppointmentChangeByCause(cause, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByCause
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentChange> findTimetableAppointmentChangeByCause(String cause, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentChangeByCause", startResult, maxRows, cause);
		return new LinkedHashSet<TimetableAppointmentChange>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByPrimaryKey
	 *
	 */
	@Transactional
	public TimetableAppointmentChange findTimetableAppointmentChangeByPrimaryKey(Integer id) throws DataAccessException {

		return findTimetableAppointmentChangeByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentChangeByPrimaryKey
	 *
	 */

	@Transactional
	public TimetableAppointmentChange findTimetableAppointmentChangeByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findTimetableAppointmentChangeByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.TimetableAppointmentChange) query.getSingleResult();
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
	public boolean canBeMerged(TimetableAppointmentChange entity) {
		return true;
	}
}
