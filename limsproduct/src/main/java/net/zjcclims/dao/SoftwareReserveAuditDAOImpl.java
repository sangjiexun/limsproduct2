package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.SoftwareReserveAudit;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage SoftwareReserveAudit entities.
 * 
 */
@Repository("SoftwareReserveAuditDAO")
@Transactional
public class SoftwareReserveAuditDAOImpl extends AbstractJpaDao<SoftwareReserveAudit>
		implements SoftwareReserveAuditDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { SoftwareReserveAudit.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new SoftwareReserveAuditDAOImpl
	 *
	 */
	public SoftwareReserveAuditDAOImpl() {
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
	 * JPQL Query - findAllSoftwareReserveAudits
	 *
	 */
	@Transactional
	public Set<SoftwareReserveAudit> findAllSoftwareReserveAudits() throws DataAccessException {

		return findAllSoftwareReserveAudits(-1, -1);
	}

	/**
	 * JPQL Query - findAllSoftwareReserveAudits
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserveAudit> findAllSoftwareReserveAudits(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllSoftwareReserveAudits", startResult, maxRows);
		return new LinkedHashSet<SoftwareReserveAudit>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveAuditByMem
	 *
	 */
	@Transactional
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByMem(String mem) throws DataAccessException {

		return findSoftwareReserveAuditByMem(mem, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveAuditByMem
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByMem(String mem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveAuditByMem", startResult, maxRows, mem);
		return new LinkedHashSet<SoftwareReserveAudit>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveAuditByStatus
	 *
	 */
	@Transactional
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByStatus(Integer status) throws DataAccessException {

		return findSoftwareReserveAuditByStatus(status, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveAuditByStatus
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByStatus(Integer status, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveAuditByStatus", startResult, maxRows, status);
		return new LinkedHashSet<SoftwareReserveAudit>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveAuditByPrimaryKey
	 *
	 */
	@Transactional
	public SoftwareReserveAudit findSoftwareReserveAuditByPrimaryKey(Integer id) throws DataAccessException {

		return findSoftwareReserveAuditByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveAuditByPrimaryKey
	 *
	 */

	@Transactional
	public SoftwareReserveAudit findSoftwareReserveAuditByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSoftwareReserveAuditByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.SoftwareReserveAudit) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findSoftwareReserveAuditByResult
	 *
	 */
	@Transactional
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByResult(String result) throws DataAccessException {

		return findSoftwareReserveAuditByResult(result, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveAuditByResult
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByResult(String result, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveAuditByResult", startResult, maxRows, result);
		return new LinkedHashSet<SoftwareReserveAudit>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveAuditByCreateDate
	 *
	 */
	@Transactional
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByCreateDate(java.util.Calendar createDate) throws DataAccessException {

		return findSoftwareReserveAuditByCreateDate(createDate, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveAuditByCreateDate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByCreateDate(java.util.Calendar createDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveAuditByCreateDate", startResult, maxRows, createDate);
		return new LinkedHashSet<SoftwareReserveAudit>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveAuditByResultContaining
	 *
	 */
	@Transactional
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByResultContaining(String result) throws DataAccessException {

		return findSoftwareReserveAuditByResultContaining(result, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveAuditByResultContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByResultContaining(String result, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveAuditByResultContaining", startResult, maxRows, result);
		return new LinkedHashSet<SoftwareReserveAudit>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveAuditByAuditRoles
	 *
	 */
	@Transactional
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByAuditRoles(Integer auditRoles) throws DataAccessException {

		return findSoftwareReserveAuditByAuditRoles(auditRoles, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveAuditByAuditRoles
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByAuditRoles(Integer auditRoles, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveAuditByAuditRoles", startResult, maxRows, auditRoles);
		return new LinkedHashSet<SoftwareReserveAudit>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveAuditById
	 *
	 */
	@Transactional
	public SoftwareReserveAudit findSoftwareReserveAuditById(Integer id) throws DataAccessException {

		return findSoftwareReserveAuditById(id, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveAuditById
	 *
	 */

	@Transactional
	public SoftwareReserveAudit findSoftwareReserveAuditById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSoftwareReserveAuditById", startResult, maxRows, id);
			return (net.zjcclims.domain.SoftwareReserveAudit) query.getSingleResult();
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
	public boolean canBeMerged(SoftwareReserveAudit entity) {
		return true;
	}
}
