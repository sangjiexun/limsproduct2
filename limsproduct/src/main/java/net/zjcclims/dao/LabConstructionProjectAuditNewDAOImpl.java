
package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.LabConstructionProjectAuditNew;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage LabConstructionProjectAuditNew entities.
 * 
 */
@Repository("LabConstructionProjectAuditNewDAO")

@Transactional
public class LabConstructionProjectAuditNewDAOImpl extends AbstractJpaDao<LabConstructionProjectAuditNew>
		implements LabConstructionProjectAuditNewDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] {
			LabConstructionProjectAuditNew.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabConstructionProjectAuditNewDAOImpl
	 *
	 */
	public LabConstructionProjectAuditNewDAOImpl() {
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
	 * JPQL Query - findLabConstructionProjectAuditNewByResult
	 *
	 */
	@Transactional
	public Set<LabConstructionProjectAuditNew> findLabConstructionProjectAuditNewByResult(Integer result) throws DataAccessException {

		return findLabConstructionProjectAuditNewByResult(result, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewByResult
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionProjectAuditNew> findLabConstructionProjectAuditNewByResult(Integer result, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionProjectAuditNewByResult", startResult, maxRows, result);
		return new LinkedHashSet<LabConstructionProjectAuditNew>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewByPrimaryKey
	 *
	 */
	@Transactional
	public LabConstructionProjectAuditNew findLabConstructionProjectAuditNewByPrimaryKey(Integer id) throws DataAccessException {

		return findLabConstructionProjectAuditNewByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewByPrimaryKey
	 *
	 */

	@Transactional
	public LabConstructionProjectAuditNew findLabConstructionProjectAuditNewByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabConstructionProjectAuditNewByPrimaryKey", startResult, maxRows, id);
			return (LabConstructionProjectAuditNew) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllLabConstructionProjectAuditNews
	 *
	 */
	@Transactional
	public Set<LabConstructionProjectAuditNew> findAllLabConstructionProjectAuditNews() throws DataAccessException {

		return findAllLabConstructionProjectAuditNews(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabConstructionProjectAuditNews
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionProjectAuditNew> findAllLabConstructionProjectAuditNews(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabConstructionProjectAuditNews", startResult, maxRows);
		return new LinkedHashSet<LabConstructionProjectAuditNew>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewByStage
	 *
	 */
	@Transactional
	public Set<LabConstructionProjectAuditNew> findLabConstructionProjectAuditNewByStage(Integer stage) throws DataAccessException {

		return findLabConstructionProjectAuditNewByStage(stage, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewByStage
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionProjectAuditNew> findLabConstructionProjectAuditNewByStage(Integer stage, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionProjectAuditNewByStage", startResult, maxRows, stage);
		return new LinkedHashSet<LabConstructionProjectAuditNew>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewById
	 *
	 */
	@Transactional
	public LabConstructionProjectAuditNew findLabConstructionProjectAuditNewById(Integer id) throws DataAccessException {

		return findLabConstructionProjectAuditNewById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewById
	 *
	 */

	@Transactional
	public LabConstructionProjectAuditNew findLabConstructionProjectAuditNewById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabConstructionProjectAuditNewById", startResult, maxRows, id);
			return (LabConstructionProjectAuditNew) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewByRemark
	 *
	 */
	@Transactional
	public Set<LabConstructionProjectAuditNew> findLabConstructionProjectAuditNewByRemark(String remark) throws DataAccessException {

		return findLabConstructionProjectAuditNewByRemark(remark, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewByRemark
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionProjectAuditNew> findLabConstructionProjectAuditNewByRemark(String remark, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionProjectAuditNewByRemark", startResult, maxRows, remark);
		return new LinkedHashSet<LabConstructionProjectAuditNew>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewByRemarkContaining
	 *
	 */
	@Transactional
	public Set<LabConstructionProjectAuditNew> findLabConstructionProjectAuditNewByRemarkContaining(String remark) throws DataAccessException {

		return findLabConstructionProjectAuditNewByRemarkContaining(remark, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewByRemarkContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionProjectAuditNew> findLabConstructionProjectAuditNewByRemarkContaining(String remark, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionProjectAuditNewByRemarkContaining", startResult, maxRows, remark);
		return new LinkedHashSet<LabConstructionProjectAuditNew>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(LabConstructionProjectAuditNew entity) {
		return true;
	}
}
