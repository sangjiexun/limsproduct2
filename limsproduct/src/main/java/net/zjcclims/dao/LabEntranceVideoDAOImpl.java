package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.LabEntranceVideo;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage LabEntranceVideo entities.
 * 
 */
@Repository("LabEntranceVideoDAO")
@Transactional
public class LabEntranceVideoDAOImpl extends AbstractJpaDao<LabEntranceVideo>
		implements LabEntranceVideoDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabEntranceVideo.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabEntranceVideoDAOImpl
	 *
	 */
	public LabEntranceVideoDAOImpl() {
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
	 * JPQL Query - findLabEntranceVideoById
	 *
	 */
	@Transactional
	public LabEntranceVideo findLabEntranceVideoById(Integer id) throws DataAccessException {

		return findLabEntranceVideoById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabEntranceVideoById
	 *
	 */

	@Transactional
	public LabEntranceVideo findLabEntranceVideoById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabEntranceVideoById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabEntranceVideo) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllLabEntranceVideos
	 *
	 */
	@Transactional
	public Set<LabEntranceVideo> findAllLabEntranceVideos() throws DataAccessException {

		return findAllLabEntranceVideos(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabEntranceVideos
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabEntranceVideo> findAllLabEntranceVideos(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabEntranceVideos", startResult, maxRows);
		return new LinkedHashSet<LabEntranceVideo>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabEntranceVideoByPrimaryKey
	 *
	 */
	@Transactional
	public LabEntranceVideo findLabEntranceVideoByPrimaryKey(Integer id) throws DataAccessException {

		return findLabEntranceVideoByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabEntranceVideoByPrimaryKey
	 *
	 */

	@Transactional
	public LabEntranceVideo findLabEntranceVideoByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabEntranceVideoByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabEntranceVideo) query.getSingleResult();
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
	public boolean canBeMerged(LabEntranceVideo entity) {
		return true;
	}
}
