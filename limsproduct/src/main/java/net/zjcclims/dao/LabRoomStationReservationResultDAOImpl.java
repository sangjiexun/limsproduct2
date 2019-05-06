package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.LabRoomStationReservationResult;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage LabRoomStationReservationResult entities.
 * 
 */
@Repository("LabRoomStationReservationResultDAO")
@Transactional
public class LabRoomStationReservationResultDAOImpl extends AbstractJpaDao<LabRoomStationReservationResult>
		implements LabRoomStationReservationResultDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabRoomStationReservationResult.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabRoomStationReservationResultDAOImpl
	 *
	 */
	public LabRoomStationReservationResultDAOImpl() {
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
	 * JPQL Query - findLabRoomStationReservationResultByPrimaryKey
	 *
	 */
	@Transactional
	public LabRoomStationReservationResult findLabRoomStationReservationResultByPrimaryKey(Integer id) throws DataAccessException {

		return findLabRoomStationReservationResultByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationResultByPrimaryKey
	 *
	 */

	@Transactional
	public LabRoomStationReservationResult findLabRoomStationReservationResultByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomStationReservationResultByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomStationReservationResult) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabRoomStationReservationResultByAuditTime
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByAuditTime(java.util.Calendar auditTime) throws DataAccessException {

		return findLabRoomStationReservationResultByAuditTime(auditTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationResultByAuditTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByAuditTime(java.util.Calendar auditTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationResultByAuditTime", startResult, maxRows, auditTime);
		return new LinkedHashSet<LabRoomStationReservationResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomStationReservationResultByTag
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByTag(Integer tag) throws DataAccessException {

		return findLabRoomStationReservationResultByTag(tag, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationResultByTag
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByTag(Integer tag, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationResultByTag", startResult, maxRows, tag);
		return new LinkedHashSet<LabRoomStationReservationResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomStationReservationResultByAuditTimeAfter
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByAuditTimeAfter(java.util.Calendar auditTime) throws DataAccessException {

		return findLabRoomStationReservationResultByAuditTimeAfter(auditTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationResultByAuditTimeAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByAuditTimeAfter(java.util.Calendar auditTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationResultByAuditTimeAfter", startResult, maxRows, auditTime);
		return new LinkedHashSet<LabRoomStationReservationResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomStationReservationResultById
	 *
	 */
	@Transactional
	public LabRoomStationReservationResult findLabRoomStationReservationResultById(Integer id) throws DataAccessException {

		return findLabRoomStationReservationResultById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationResultById
	 *
	 */

	@Transactional
	public LabRoomStationReservationResult findLabRoomStationReservationResultById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomStationReservationResultById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomStationReservationResult) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabRoomStationReservationResultByRemarkContaining
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByRemarkContaining(String remark) throws DataAccessException {

		return findLabRoomStationReservationResultByRemarkContaining(remark, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationResultByRemarkContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByRemarkContaining(String remark, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationResultByRemarkContaining", startResult, maxRows, remark);
		return new LinkedHashSet<LabRoomStationReservationResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomStationReservationResultByAuditResult
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByAuditResult(Integer auditResult) throws DataAccessException {

		return findLabRoomStationReservationResultByAuditResult(auditResult, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationResultByAuditResult
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByAuditResult(Integer auditResult, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationResultByAuditResult", startResult, maxRows, auditResult);
		return new LinkedHashSet<LabRoomStationReservationResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomStationReservationResultByRemark
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByRemark(String remark) throws DataAccessException {

		return findLabRoomStationReservationResultByRemark(remark, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationResultByRemark
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByRemark(String remark, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationResultByRemark", startResult, maxRows, remark);
		return new LinkedHashSet<LabRoomStationReservationResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomStationReservationResultByAuditTimeBefore
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByAuditTimeBefore(java.util.Calendar auditTime) throws DataAccessException {

		return findLabRoomStationReservationResultByAuditTimeBefore(auditTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationResultByAuditTimeBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservationResult> findLabRoomStationReservationResultByAuditTimeBefore(java.util.Calendar auditTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationResultByAuditTimeBefore", startResult, maxRows, auditTime);
		return new LinkedHashSet<LabRoomStationReservationResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabRoomStationReservationResults
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservationResult> findAllLabRoomStationReservationResults() throws DataAccessException {

		return findAllLabRoomStationReservationResults(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabRoomStationReservationResults
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservationResult> findAllLabRoomStationReservationResults(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabRoomStationReservationResults", startResult, maxRows);
		return new LinkedHashSet<LabRoomStationReservationResult>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(LabRoomStationReservationResult entity) {
		return true;
	}
}
