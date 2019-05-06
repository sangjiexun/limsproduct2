package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.TimetableAppointmentResult;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage TimetableAppointmentResult entities.
 * 
 */
@Repository("TimetableAppointmentResultDAO")
@Transactional
public class TimetableAppointmentResultDAOImpl extends AbstractJpaDao<TimetableAppointmentResult>
		implements TimetableAppointmentResultDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { TimetableAppointmentResult.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new TimetableAppointmentResultDAOImpl
	 *
	 */
	public TimetableAppointmentResultDAOImpl() {
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
	 * JPQL Query - findTimetableAppointmentResultByAuditResult
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByAuditResult(String auditResult) throws DataAccessException {

		return findTimetableAppointmentResultByAuditResult(auditResult, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentResultByAuditResult
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByAuditResult(String auditResult, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentResultByAuditResult", startResult, maxRows, auditResult);
		return new LinkedHashSet<TimetableAppointmentResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentResultByAppointmentId
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByAppointmentId(Integer appointmentId) throws DataAccessException {

		return findTimetableAppointmentResultByAppointmentId(appointmentId, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentResultByAppointmentId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByAppointmentId(Integer appointmentId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentResultByAppointmentId", startResult, maxRows, appointmentId);
		return new LinkedHashSet<TimetableAppointmentResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllTimetableAppointmentResults
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentResult> findAllTimetableAppointmentResults() throws DataAccessException {

		return findAllTimetableAppointmentResults(-1, -1);
	}

	/**
	 * JPQL Query - findAllTimetableAppointmentResults
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentResult> findAllTimetableAppointmentResults(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllTimetableAppointmentResults", startResult, maxRows);
		return new LinkedHashSet<TimetableAppointmentResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentResultByUser
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByUser(String user) throws DataAccessException {

		return findTimetableAppointmentResultByUser(user, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentResultByUser
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByUser(String user, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentResultByUser", startResult, maxRows, user);
		return new LinkedHashSet<TimetableAppointmentResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentResultByTag
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByTag(Integer tag) throws DataAccessException {

		return findTimetableAppointmentResultByTag(tag, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentResultByTag
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByTag(Integer tag, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentResultByTag", startResult, maxRows, tag);
		return new LinkedHashSet<TimetableAppointmentResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentResultByUserContaining
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByUserContaining(String user) throws DataAccessException {

		return findTimetableAppointmentResultByUserContaining(user, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentResultByUserContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByUserContaining(String user, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentResultByUserContaining", startResult, maxRows, user);
		return new LinkedHashSet<TimetableAppointmentResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentResultByRemark
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByRemark(String remark) throws DataAccessException {

		return findTimetableAppointmentResultByRemark(remark, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentResultByRemark
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByRemark(String remark, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentResultByRemark", startResult, maxRows, remark);
		return new LinkedHashSet<TimetableAppointmentResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentResultByRemarkContaining
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByRemarkContaining(String remark) throws DataAccessException {

		return findTimetableAppointmentResultByRemarkContaining(remark, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentResultByRemarkContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByRemarkContaining(String remark, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentResultByRemarkContaining", startResult, maxRows, remark);
		return new LinkedHashSet<TimetableAppointmentResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentResultByPrimaryKey
	 *
	 */
	@Transactional
	public TimetableAppointmentResult findTimetableAppointmentResultByPrimaryKey(Integer id) throws DataAccessException {

		return findTimetableAppointmentResultByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentResultByPrimaryKey
	 *
	 */

	@Transactional
	public TimetableAppointmentResult findTimetableAppointmentResultByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findTimetableAppointmentResultByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.TimetableAppointmentResult) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findTimetableAppointmentResultByAuditResultContaining
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByAuditResultContaining(String auditResult) throws DataAccessException {

		return findTimetableAppointmentResultByAuditResultContaining(auditResult, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentResultByAuditResultContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentResult> findTimetableAppointmentResultByAuditResultContaining(String auditResult, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentResultByAuditResultContaining", startResult, maxRows, auditResult);
		return new LinkedHashSet<TimetableAppointmentResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentResultById
	 *
	 */
	@Transactional
	public TimetableAppointmentResult findTimetableAppointmentResultById(Integer id) throws DataAccessException {

		return findTimetableAppointmentResultById(id, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentResultById
	 *
	 */

	@Transactional
	public TimetableAppointmentResult findTimetableAppointmentResultById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findTimetableAppointmentResultById", startResult, maxRows, id);
			return (net.zjcclims.domain.TimetableAppointmentResult) query.getSingleResult();
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
	public boolean canBeMerged(TimetableAppointmentResult entity) {
		return true;
	}
}
