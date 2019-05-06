package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.OperItemAudit;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage OperItemAudit entities.
 * 
 */
@Repository("OperItemAuditDAO")
@Transactional
public class OperItemAuditDAOImpl extends AbstractJpaDao<OperItemAudit>
		implements OperItemAuditDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { OperItemAudit.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new OperItemAuditDAOImpl
	 *
	 */
	public OperItemAuditDAOImpl() {
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
	 * JPQL Query - findAllOperItemAudits
	 *
	 */
	@Transactional
	public Set<OperItemAudit> findAllOperItemAudits() throws DataAccessException {

		return findAllOperItemAudits(-1, -1);
	}

	/**
	 * JPQL Query - findAllOperItemAudits
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<OperItemAudit> findAllOperItemAudits(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllOperItemAudits", startResult, maxRows);
		return new LinkedHashSet<OperItemAudit>(query.getResultList());
	}

	/**
	 * JPQL Query - findOperItemAuditByMem
	 *
	 */
	@Transactional
	public Set<OperItemAudit> findOperItemAuditByMem(String mem) throws DataAccessException {

		return findOperItemAuditByMem(mem, -1, -1);
	}

	/**
	 * JPQL Query - findOperItemAuditByMem
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<OperItemAudit> findOperItemAuditByMem(String mem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findOperItemAuditByMem", startResult, maxRows, mem);
		return new LinkedHashSet<OperItemAudit>(query.getResultList());
	}

	/**
	 * JPQL Query - findOperItemAuditByStatus
	 *
	 */
	@Transactional
	public Set<OperItemAudit> findOperItemAuditByStatus(Integer status) throws DataAccessException {

		return findOperItemAuditByStatus(status, -1, -1);
	}

	/**
	 * JPQL Query - findOperItemAuditByStatus
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<OperItemAudit> findOperItemAuditByStatus(Integer status, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findOperItemAuditByStatus", startResult, maxRows, status);
		return new LinkedHashSet<OperItemAudit>(query.getResultList());
	}

	/**
	 * JPQL Query - findOperItemAuditByPrimaryKey
	 *
	 */
	@Transactional
	public OperItemAudit findOperItemAuditByPrimaryKey(Integer id) throws DataAccessException {

		return findOperItemAuditByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findOperItemAuditByPrimaryKey
	 *
	 */

	@Transactional
	public OperItemAudit findOperItemAuditByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findOperItemAuditByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.OperItemAudit) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findOperItemAuditByResult
	 *
	 */
	@Transactional
	public Set<OperItemAudit> findOperItemAuditByResult(String result) throws DataAccessException {

		return findOperItemAuditByResult(result, -1, -1);
	}

	/**
	 * JPQL Query - findOperItemAuditByResult
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<OperItemAudit> findOperItemAuditByResult(String result, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findOperItemAuditByResult", startResult, maxRows, result);
		return new LinkedHashSet<OperItemAudit>(query.getResultList());
	}

	/**
	 * JPQL Query - findOperItemAuditByCreateDate
	 *
	 */
	@Transactional
	public Set<OperItemAudit> findOperItemAuditByCreateDate(java.util.Calendar createDate) throws DataAccessException {

		return findOperItemAuditByCreateDate(createDate, -1, -1);
	}

	/**
	 * JPQL Query - findOperItemAuditByCreateDate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<OperItemAudit> findOperItemAuditByCreateDate(java.util.Calendar createDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findOperItemAuditByCreateDate", startResult, maxRows, createDate);
		return new LinkedHashSet<OperItemAudit>(query.getResultList());
	}

	/**
	 * JPQL Query - findOperItemAuditByResultContaining
	 *
	 */
	@Transactional
	public Set<OperItemAudit> findOperItemAuditByResultContaining(String result) throws DataAccessException {

		return findOperItemAuditByResultContaining(result, -1, -1);
	}

	/**
	 * JPQL Query - findOperItemAuditByResultContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<OperItemAudit> findOperItemAuditByResultContaining(String result, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findOperItemAuditByResultContaining", startResult, maxRows, result);
		return new LinkedHashSet<OperItemAudit>(query.getResultList());
	}

	/**
	 * JPQL Query - findOperItemAuditByAuditRoles
	 *
	 */
	@Transactional
	public Set<OperItemAudit> findOperItemAuditByAuditRoles(Integer auditRoles) throws DataAccessException {

		return findOperItemAuditByAuditRoles(auditRoles, -1, -1);
	}

	/**
	 * JPQL Query - findOperItemAuditByAuditRoles
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<OperItemAudit> findOperItemAuditByAuditRoles(Integer auditRoles, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findOperItemAuditByAuditRoles", startResult, maxRows, auditRoles);
		return new LinkedHashSet<OperItemAudit>(query.getResultList());
	}

	/**
	 * JPQL Query - findOperItemAuditById
	 *
	 */
	@Transactional
	public OperItemAudit findOperItemAuditById(Integer id) throws DataAccessException {

		return findOperItemAuditById(id, -1, -1);
	}

	/**
	 * JPQL Query - findOperItemAuditById
	 *
	 */

	@Transactional
	public OperItemAudit findOperItemAuditById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findOperItemAuditById", startResult, maxRows, id);
			return (net.zjcclims.domain.OperItemAudit) query.getSingleResult();
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
	public boolean canBeMerged(OperItemAudit entity) {
		return true;
	}
}
