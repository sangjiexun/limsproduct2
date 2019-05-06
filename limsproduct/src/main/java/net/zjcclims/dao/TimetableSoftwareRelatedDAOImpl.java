package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.TimetableSoftwareRelated;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage TimetableSoftwareRelated entities.
 * 
 */
@Repository("TimetableSoftwareRelatedDAO")
@Transactional
public class TimetableSoftwareRelatedDAOImpl extends AbstractJpaDao<TimetableSoftwareRelated>
		implements TimetableSoftwareRelatedDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { TimetableSoftwareRelated.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new TimetableSoftwareRelatedDAOImpl
	 *
	 */
	public TimetableSoftwareRelatedDAOImpl() {
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
	 * JPQL Query - findTimetableSoftwareRelatedByPrimaryKey
	 *
	 */
	@Transactional
	public TimetableSoftwareRelated findTimetableSoftwareRelatedByPrimaryKey(Integer id) throws DataAccessException {

		return findTimetableSoftwareRelatedByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableSoftwareRelatedByPrimaryKey
	 *
	 */

	@Transactional
	public TimetableSoftwareRelated findTimetableSoftwareRelatedByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findTimetableSoftwareRelatedByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.TimetableSoftwareRelated) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findTimetableSoftwareRelatedById
	 *
	 */
	@Transactional
	public TimetableSoftwareRelated findTimetableSoftwareRelatedById(Integer id) throws DataAccessException {

		return findTimetableSoftwareRelatedById(id, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableSoftwareRelatedById
	 *
	 */

	@Transactional
	public TimetableSoftwareRelated findTimetableSoftwareRelatedById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findTimetableSoftwareRelatedById", startResult, maxRows, id);
			return (net.zjcclims.domain.TimetableSoftwareRelated) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllTimetableSoftwareRelateds
	 *
	 */
	@Transactional
	public Set<TimetableSoftwareRelated> findAllTimetableSoftwareRelateds() throws DataAccessException {

		return findAllTimetableSoftwareRelateds(-1, -1);
	}

	/**
	 * JPQL Query - findAllTimetableSoftwareRelateds
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableSoftwareRelated> findAllTimetableSoftwareRelateds(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllTimetableSoftwareRelateds", startResult, maxRows);
		return new LinkedHashSet<TimetableSoftwareRelated>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(TimetableSoftwareRelated entity) {
		return true;
	}
}
