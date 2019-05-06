package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.PreSoftware;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage PreSoftware entities.
 * 
 */
@Repository("PreSoftwareDAO")
@Transactional
public class PreSoftwareDAOImpl extends AbstractJpaDao<PreSoftware> implements
		PreSoftwareDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { PreSoftware.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new PreSoftwareDAOImpl
	 *
	 */
	public PreSoftwareDAOImpl() {
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
	 * JPQL Query - findPreSoftwareById
	 *
	 */
	@Transactional
	public PreSoftware findPreSoftwareById(Integer id) throws DataAccessException {

		return findPreSoftwareById(id, -1, -1);
	}

	/**
	 * JPQL Query - findPreSoftwareById
	 *
	 */

	@Transactional
	public PreSoftware findPreSoftwareById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findPreSoftwareById", startResult, maxRows, id);
			return (PreSoftware) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findPreSoftwareByName
	 *
	 */
	@Transactional
	public Set<PreSoftware> findPreSoftwareByName(String name) throws DataAccessException {

		return findPreSoftwareByName(name, -1, -1);
	}

	/**
	 * JPQL Query - findPreSoftwareByName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreSoftware> findPreSoftwareByName(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findPreSoftwareByName", startResult, maxRows, name);
		return new LinkedHashSet<PreSoftware>(query.getResultList());
	}

	/**
	 * JPQL Query - findPreSoftwareByPrimaryKey
	 *
	 */
	@Transactional
	public PreSoftware findPreSoftwareByPrimaryKey(Integer id) throws DataAccessException {

		return findPreSoftwareByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findPreSoftwareByPrimaryKey
	 *
	 */

	@Transactional
	public PreSoftware findPreSoftwareByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findPreSoftwareByPrimaryKey", startResult, maxRows, id);
			return (PreSoftware) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllPreSoftwares
	 *
	 */
	@Transactional
	public Set<PreSoftware> findAllPreSoftwares() throws DataAccessException {

		return findAllPreSoftwares(-1, -1);
	}

	/**
	 * JPQL Query - findAllPreSoftwares
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreSoftware> findAllPreSoftwares(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllPreSoftwares", startResult, maxRows);
		return new LinkedHashSet<PreSoftware>(query.getResultList());
	}

	/**
	 * JPQL Query - findPreSoftwareByNameContaining
	 *
	 */
	@Transactional
	public Set<PreSoftware> findPreSoftwareByNameContaining(String name) throws DataAccessException {

		return findPreSoftwareByNameContaining(name, -1, -1);
	}

	/**
	 * JPQL Query - findPreSoftwareByNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreSoftware> findPreSoftwareByNameContaining(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findPreSoftwareByNameContaining", startResult, maxRows, name);
		return new LinkedHashSet<PreSoftware>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(PreSoftware entity) {
		return true;
	}
}
