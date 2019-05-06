package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.LabRoomStationReservation;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage LabRoomStationReservation entities.
 * 
 */
@Repository("LabRoomStationReservationDAO")
@Transactional
public class LabRoomStationReservationDAOImpl extends AbstractJpaDao<LabRoomStationReservation>
		implements LabRoomStationReservationDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabRoomStationReservation.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabRoomStationReservationDAOImpl
	 *
	 */
	public LabRoomStationReservationDAOImpl() {
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
	 * JPQL Query - findLabRoomStationReservationByStartTime
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservation> findLabRoomStationReservationByStartTime(java.util.Calendar startTime) throws DataAccessException {

		return findLabRoomStationReservationByStartTime(startTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationByStartTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservation> findLabRoomStationReservationByStartTime(java.util.Calendar startTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationByStartTime", startResult, maxRows, startTime);
		return new LinkedHashSet<LabRoomStationReservation>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomStationReservationById
	 *
	 */
	@Transactional
	public LabRoomStationReservation findLabRoomStationReservationById(Integer id) throws DataAccessException {

		return findLabRoomStationReservationById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationById
	 *
	 */

	@Transactional
	public LabRoomStationReservation findLabRoomStationReservationById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomStationReservationById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomStationReservation) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabRoomStationReservationByStationCount
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservation> findLabRoomStationReservationByStationCount(Integer stationCount) throws DataAccessException {

		return findLabRoomStationReservationByStationCount(stationCount, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationByStationCount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservation> findLabRoomStationReservationByStationCount(Integer stationCount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationByStationCount", startResult, maxRows, stationCount);
		return new LinkedHashSet<LabRoomStationReservation>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabRoomStationReservations
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservation> findAllLabRoomStationReservations() throws DataAccessException {

		return findAllLabRoomStationReservations(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabRoomStationReservations
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservation> findAllLabRoomStationReservations(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabRoomStationReservations", startResult, maxRows);
		return new LinkedHashSet<LabRoomStationReservation>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomStationReservationByReasonContaining
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservation> findLabRoomStationReservationByReasonContaining(String reason) throws DataAccessException {

		return findLabRoomStationReservationByReasonContaining(reason, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationByReasonContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservation> findLabRoomStationReservationByReasonContaining(String reason, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationByReasonContaining", startResult, maxRows, reason);
		return new LinkedHashSet<LabRoomStationReservation>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomStationReservationByEndTime
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservation> findLabRoomStationReservationByEndTime(java.util.Calendar endTime) throws DataAccessException {

		return findLabRoomStationReservationByEndTime(endTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationByEndTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservation> findLabRoomStationReservationByEndTime(java.util.Calendar endTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationByEndTime", startResult, maxRows, endTime);
		return new LinkedHashSet<LabRoomStationReservation>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomStationReservationByEndPeriod
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservation> findLabRoomStationReservationByEndPeriod(Integer endPeriod) throws DataAccessException {

		return findLabRoomStationReservationByEndPeriod(endPeriod, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationByEndPeriod
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservation> findLabRoomStationReservationByEndPeriod(Integer endPeriod, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationByEndPeriod", startResult, maxRows, endPeriod);
		return new LinkedHashSet<LabRoomStationReservation>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomStationReservationByReservationBefore
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservation> findLabRoomStationReservationByReservationBefore(java.util.Calendar reservation) throws DataAccessException {

		return findLabRoomStationReservationByReservationBefore(reservation, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationByReservationBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservation> findLabRoomStationReservationByReservationBefore(java.util.Calendar reservation, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationByReservationBefore", startResult, maxRows, reservation);
		return new LinkedHashSet<LabRoomStationReservation>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomStationReservationByPrimaryKey
	 *
	 */
	@Transactional
	public LabRoomStationReservation findLabRoomStationReservationByPrimaryKey(Integer id) throws DataAccessException {

		return findLabRoomStationReservationByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationByPrimaryKey
	 *
	 */

	@Transactional
	public LabRoomStationReservation findLabRoomStationReservationByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomStationReservationByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomStationReservation) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabRoomStationReservationByReservation
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservation> findLabRoomStationReservationByReservation(java.util.Calendar reservation) throws DataAccessException {

		return findLabRoomStationReservationByReservation(reservation, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationByReservation
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservation> findLabRoomStationReservationByReservation(java.util.Calendar reservation, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationByReservation", startResult, maxRows, reservation);
		return new LinkedHashSet<LabRoomStationReservation>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomStationReservationByReservationAfter
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservation> findLabRoomStationReservationByReservationAfter(java.util.Calendar reservation) throws DataAccessException {

		return findLabRoomStationReservationByReservationAfter(reservation, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationByReservationAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservation> findLabRoomStationReservationByReservationAfter(java.util.Calendar reservation, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationByReservationAfter", startResult, maxRows, reservation);
		return new LinkedHashSet<LabRoomStationReservation>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomStationReservationByReason
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservation> findLabRoomStationReservationByReason(String reason) throws DataAccessException {

		return findLabRoomStationReservationByReason(reason, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationByReason
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservation> findLabRoomStationReservationByReason(String reason, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationByReason", startResult, maxRows, reason);
		return new LinkedHashSet<LabRoomStationReservation>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomStationReservationByStartPeriod
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservation> findLabRoomStationReservationByStartPeriod(Integer startPeriod) throws DataAccessException {

		return findLabRoomStationReservationByStartPeriod(startPeriod, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationByStartPeriod
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservation> findLabRoomStationReservationByStartPeriod(Integer startPeriod, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationByStartPeriod", startResult, maxRows, startPeriod);
		return new LinkedHashSet<LabRoomStationReservation>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(LabRoomStationReservation entity) {
		return true;
	}
}
