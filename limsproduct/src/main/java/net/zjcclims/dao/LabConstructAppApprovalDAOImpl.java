package net.zjcclims.dao;


import net.zjcclims.domain.LabConstructAppApproval;
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
 * DAO to manage LabConstructAppApproval entities.
 * 
 */
@Repository("LabConstructAppApprovalDAO")
@Transactional
public class LabConstructAppApprovalDAOImpl extends AbstractJpaDao<LabConstructAppApproval>
		implements LabConstructAppApprovalDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabConstructAppApproval.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulims
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabConstructAppApprovalDAOImpl
	 *
	 */
	public LabConstructAppApprovalDAOImpl() {
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
	 * JPQL Query - findLabConstructAppApprovalByResultContaining
	 *
	 */
	@Transactional
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByResultContaining(String result) throws DataAccessException {

		return findLabConstructAppApprovalByResultContaining(result, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppApprovalByResultContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByResultContaining(String result, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppApprovalByResultContaining", startResult, maxRows, result);
		return new LinkedHashSet<LabConstructAppApproval>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppApprovalByPrimaryKey
	 *
	 */
	@Transactional
	public LabConstructAppApproval findLabConstructAppApprovalByPrimaryKey(Integer id) throws DataAccessException {

		return findLabConstructAppApprovalByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppApprovalByPrimaryKey
	 *
	 */

	@Transactional
	public LabConstructAppApproval findLabConstructAppApprovalByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabConstructAppApprovalByPrimaryKey", startResult, maxRows, id);
			return (LabConstructAppApproval) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabConstructAppApprovalByResult
	 *
	 */
	@Transactional
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByResult(String result) throws DataAccessException {

		return findLabConstructAppApprovalByResult(result, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppApprovalByResult
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByResult(String result, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppApprovalByResult", startResult, maxRows, result);
		return new LinkedHashSet<LabConstructAppApproval>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppApprovalByFlag
	 *
	 */
	@Transactional
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByFlag(Integer flag) throws DataAccessException {

		return findLabConstructAppApprovalByFlag(flag, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppApprovalByFlag
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByFlag(Integer flag, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppApprovalByFlag", startResult, maxRows, flag);
		return new LinkedHashSet<LabConstructAppApproval>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppApprovalByCreatedateBefore
	 *
	 */
	@Transactional
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByCreatedateBefore(java.util.Calendar createdate) throws DataAccessException {

		return findLabConstructAppApprovalByCreatedateBefore(createdate, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppApprovalByCreatedateBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByCreatedateBefore(java.util.Calendar createdate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppApprovalByCreatedateBefore", startResult, maxRows, createdate);
		return new LinkedHashSet<LabConstructAppApproval>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppApprovalByLevel
	 *
	 */
	@Transactional
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByLevel(Integer level) throws DataAccessException {

		return findLabConstructAppApprovalByLevel(level, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppApprovalByLevel
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByLevel(Integer level, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppApprovalByLevel", startResult, maxRows, level);
		return new LinkedHashSet<LabConstructAppApproval>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabConstructAppApprovals
	 *
	 */
	@Transactional
	public Set<LabConstructAppApproval> findAllLabConstructAppApprovals() throws DataAccessException {

		return findAllLabConstructAppApprovals(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabConstructAppApprovals
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructAppApproval> findAllLabConstructAppApprovals(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabConstructAppApprovals", startResult, maxRows);
		return new LinkedHashSet<LabConstructAppApproval>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppApprovalById
	 *
	 */
	@Transactional
	public LabConstructAppApproval findLabConstructAppApprovalById(Integer id) throws DataAccessException {

		return findLabConstructAppApprovalById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppApprovalById
	 *
	 */

	@Transactional
	public LabConstructAppApproval findLabConstructAppApprovalById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabConstructAppApprovalById", startResult, maxRows, id);
			return (LabConstructAppApproval) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabConstructAppApprovalByCreatedate
	 *
	 */
	@Transactional
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByCreatedate(java.util.Calendar createdate) throws DataAccessException {

		return findLabConstructAppApprovalByCreatedate(createdate, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppApprovalByCreatedate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByCreatedate(java.util.Calendar createdate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppApprovalByCreatedate", startResult, maxRows, createdate);
		return new LinkedHashSet<LabConstructAppApproval>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppApprovalByCreatedateAfter
	 *
	 */
	@Transactional
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByCreatedateAfter(java.util.Calendar createdate) throws DataAccessException {

		return findLabConstructAppApprovalByCreatedateAfter(createdate, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppApprovalByCreatedateAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByCreatedateAfter(java.util.Calendar createdate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppApprovalByCreatedateAfter", startResult, maxRows, createdate);
		return new LinkedHashSet<LabConstructAppApproval>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(LabConstructAppApproval entity) {
		return true;
	}
}
