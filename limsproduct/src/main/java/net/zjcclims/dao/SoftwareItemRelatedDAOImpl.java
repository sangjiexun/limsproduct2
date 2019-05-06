package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.SoftwareItemRelated;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage SoftwareItemRelated entities.
 * 
 */
@Repository("SoftwareItemRelatedDAO")
@Transactional
public class SoftwareItemRelatedDAOImpl extends AbstractJpaDao<SoftwareItemRelated>
		implements SoftwareItemRelatedDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { SoftwareItemRelated.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new SoftwareItemRelatedDAOImpl
	 *
	 */
	public SoftwareItemRelatedDAOImpl() {
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
	 * JPQL Query - findSoftwareItemRelatedById
	 *
	 */
	@Transactional
	public SoftwareItemRelated findSoftwareItemRelatedById(Integer id) throws DataAccessException {

		return findSoftwareItemRelatedById(id, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareItemRelatedById
	 *
	 */

	@Transactional
	public SoftwareItemRelated findSoftwareItemRelatedById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSoftwareItemRelatedById", startResult, maxRows, id);
			return (net.zjcclims.domain.SoftwareItemRelated) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findSoftwareItemRelatedByPrimaryKey
	 *
	 */
	@Transactional
	public SoftwareItemRelated findSoftwareItemRelatedByPrimaryKey(Integer id) throws DataAccessException {

		return findSoftwareItemRelatedByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareItemRelatedByPrimaryKey
	 *
	 */

	@Transactional
	public SoftwareItemRelated findSoftwareItemRelatedByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSoftwareItemRelatedByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.SoftwareItemRelated) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllSoftwareItemRelateds
	 *
	 */
	@Transactional
	public Set<SoftwareItemRelated> findAllSoftwareItemRelateds() throws DataAccessException {

		return findAllSoftwareItemRelateds(-1, -1);
	}

	/**
	 * JPQL Query - findAllSoftwareItemRelateds
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareItemRelated> findAllSoftwareItemRelateds(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllSoftwareItemRelateds", startResult, maxRows);
		return new LinkedHashSet<SoftwareItemRelated>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(SoftwareItemRelated entity) {
		return true;
	}
}
