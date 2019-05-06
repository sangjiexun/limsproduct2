package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.SoftwareRoomRelated;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage SoftwareRoomRelated entities.
 * 
 */
@Repository("SoftwareRoomRelatedDAO")
@Transactional
public class SoftwareRoomRelatedDAOImpl extends AbstractJpaDao<SoftwareRoomRelated>
		implements SoftwareRoomRelatedDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { SoftwareRoomRelated.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new SoftwareRoomRelatedDAOImpl
	 *
	 */
	public SoftwareRoomRelatedDAOImpl() {
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
	 * JPQL Query - findSoftwareRoomRelatedById
	 *
	 */
	@Transactional
	public SoftwareRoomRelated findSoftwareRoomRelatedById(Integer id) throws DataAccessException {

		return findSoftwareRoomRelatedById(id, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareRoomRelatedById
	 *
	 */

	@Transactional
	public SoftwareRoomRelated findSoftwareRoomRelatedById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSoftwareRoomRelatedById", startResult, maxRows, id);
			return (net.zjcclims.domain.SoftwareRoomRelated) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllSoftwareRoomRelateds
	 *
	 */
	@Transactional
	public Set<SoftwareRoomRelated> findAllSoftwareRoomRelateds() throws DataAccessException {

		return findAllSoftwareRoomRelateds(-1, -1);
	}

	/**
	 * JPQL Query - findAllSoftwareRoomRelateds
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareRoomRelated> findAllSoftwareRoomRelateds(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllSoftwareRoomRelateds", startResult, maxRows);
		return new LinkedHashSet<SoftwareRoomRelated>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareRoomRelatedByPrimaryKey
	 *
	 */
	@Transactional
	public SoftwareRoomRelated findSoftwareRoomRelatedByPrimaryKey(Integer id) throws DataAccessException {

		return findSoftwareRoomRelatedByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareRoomRelatedByPrimaryKey
	 *
	 */

	@Transactional
	public SoftwareRoomRelated findSoftwareRoomRelatedByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSoftwareRoomRelatedByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.SoftwareRoomRelated) query.getSingleResult();
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
	public boolean canBeMerged(SoftwareRoomRelated entity) {
		return true;
	}
}
