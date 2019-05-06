package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.LabLendAudit;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage LabLendAudit entities.
 * 
 */
@Repository("LabLendAuditDAO")
@Transactional
public class LabLendAuditDAOImpl extends AbstractJpaDao<LabLendAudit>
		implements LabLendAuditDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabLendAudit.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabLendAuditDAOImpl
	 *
	 */
	public LabLendAuditDAOImpl() {
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
	 * JPQL Query - findAllLabLendAudits
	 *
	 */
	@Transactional
	public Set<LabLendAudit> findAllLabLendAudits() throws DataAccessException {

		return findAllLabLendAudits(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabLendAudits
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabLendAudit> findAllLabLendAudits(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabLendAudits", startResult, maxRows);
		return new LinkedHashSet<LabLendAudit>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabLendAuditByMem
	 *
	 */
	@Transactional
	public Set<LabLendAudit> findLabLendAuditByMem(String mem) throws DataAccessException {

		return findLabLendAuditByMem(mem, -1, -1);
	}

	/**
	 * JPQL Query - findLabLendAuditByMem
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabLendAudit> findLabLendAuditByMem(String mem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabLendAuditByMem", startResult, maxRows, mem);
		return new LinkedHashSet<LabLendAudit>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabLendAuditByStatus
	 *
	 */
	@Transactional
	public Set<LabLendAudit> findLabLendAuditByStatus(Integer status) throws DataAccessException {

		return findLabLendAuditByStatus(status, -1, -1);
	}

	/**
	 * JPQL Query - findLabLendAuditByStatus
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabLendAudit> findLabLendAuditByStatus(Integer status, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabLendAuditByStatus", startResult, maxRows, status);
		return new LinkedHashSet<LabLendAudit>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabLendAuditByPrimaryKey
	 *
	 */
	@Transactional
	public LabLendAudit findLabLendAuditByPrimaryKey(Integer id) throws DataAccessException {

		return findLabLendAuditByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabLendAuditByPrimaryKey
	 *
	 */

	@Transactional
	public LabLendAudit findLabLendAuditByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabLendAuditByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabLendAudit) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabLendAuditByResult
	 *
	 */
	@Transactional
	public Set<LabLendAudit> findLabLendAuditByResult(String result) throws DataAccessException {

		return findLabLendAuditByResult(result, -1, -1);
	}

	/**
	 * JPQL Query - findLabLendAuditByResult
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabLendAudit> findLabLendAuditByResult(String result, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabLendAuditByResult", startResult, maxRows, result);
		return new LinkedHashSet<LabLendAudit>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabLendAuditByCreateDate
	 *
	 */
	@Transactional
	public Set<LabLendAudit> findLabLendAuditByCreateDate(java.util.Calendar createDate) throws DataAccessException {

		return findLabLendAuditByCreateDate(createDate, -1, -1);
	}

	/**
	 * JPQL Query - findLabLendAuditByCreateDate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabLendAudit> findLabLendAuditByCreateDate(java.util.Calendar createDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabLendAuditByCreateDate", startResult, maxRows, createDate);
		return new LinkedHashSet<LabLendAudit>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabLendAuditByResultContaining
	 *
	 */
	@Transactional
	public Set<LabLendAudit> findLabLendAuditByResultContaining(String result) throws DataAccessException {

		return findLabLendAuditByResultContaining(result, -1, -1);
	}

	/**
	 * JPQL Query - findLabLendAuditByResultContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabLendAudit> findLabLendAuditByResultContaining(String result, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabLendAuditByResultContaining", startResult, maxRows, result);
		return new LinkedHashSet<LabLendAudit>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabLendAuditByAuditRoles
	 *
	 */
	@Transactional
	public Set<LabLendAudit> findLabLendAuditByAuditRoles(Integer auditRoles) throws DataAccessException {

		return findLabLendAuditByAuditRoles(auditRoles, -1, -1);
	}

	/**
	 * JPQL Query - findLabLendAuditByAuditRoles
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabLendAudit> findLabLendAuditByAuditRoles(Integer auditRoles, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabLendAuditByAuditRoles", startResult, maxRows, auditRoles);
		return new LinkedHashSet<LabLendAudit>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabLendAuditById
	 *
	 */
	@Transactional
	public LabLendAudit findLabLendAuditById(Integer id) throws DataAccessException {

		return findLabLendAuditById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabLendAuditById
	 *
	 */

	@Transactional
	public LabLendAudit findLabLendAuditById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabLendAuditById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabLendAudit) query.getSingleResult();
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
	public boolean canBeMerged(LabLendAudit entity) {
		return true;
	}
}
