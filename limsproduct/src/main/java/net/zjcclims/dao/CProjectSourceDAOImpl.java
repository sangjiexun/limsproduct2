package net.zjcclims.dao;


import net.zjcclims.domain.CProjectSource;
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
 * DAO to manage CProjectSource entities.
 * 
 */
@Repository("CProjectSourceDAO")
@Transactional
public class CProjectSourceDAOImpl extends AbstractJpaDao<CProjectSource>
		implements CProjectSourceDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { CProjectSource.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulims
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new CProjectSourceDAOImpl
	 *
	 */
	public CProjectSourceDAOImpl() {
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
	 * JPQL Query - findCProjectSourceByNameContaining
	 *
	 */
	@Transactional
	public Set<CProjectSource> findCProjectSourceByNameContaining(String name) throws DataAccessException {

		return findCProjectSourceByNameContaining(name, -1, -1);
	}

	/**
	 * JPQL Query - findCProjectSourceByNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CProjectSource> findCProjectSourceByNameContaining(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findCProjectSourceByNameContaining", startResult, maxRows, name);
		return new LinkedHashSet<CProjectSource>(query.getResultList());
	}

	/**
	 * JPQL Query - findCProjectSourceByName
	 *
	 */
	@Transactional
	public Set<CProjectSource> findCProjectSourceByName(String name) throws DataAccessException {

		return findCProjectSourceByName(name, -1, -1);
	}

	/**
	 * JPQL Query - findCProjectSourceByName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CProjectSource> findCProjectSourceByName(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findCProjectSourceByName", startResult, maxRows, name);
		return new LinkedHashSet<CProjectSource>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllCProjectSources
	 *
	 */
	@Transactional
	public Set<CProjectSource> findAllCProjectSources() throws DataAccessException {

		return findAllCProjectSources(-1, -1);
	}

	/**
	 * JPQL Query - findAllCProjectSources
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CProjectSource> findAllCProjectSources(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllCProjectSources", startResult, maxRows);
		return new LinkedHashSet<CProjectSource>(query.getResultList());
	}

	/**
	 * JPQL Query - findCProjectSourceByPrimaryKey
	 *
	 */
	@Transactional
	public CProjectSource findCProjectSourceByPrimaryKey(Integer id) throws DataAccessException {

		return findCProjectSourceByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findCProjectSourceByPrimaryKey
	 *
	 */

	@Transactional
	public CProjectSource findCProjectSourceByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findCProjectSourceByPrimaryKey", startResult, maxRows, id);
			return (CProjectSource) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findCProjectSourceById
	 *
	 */
	@Transactional
	public CProjectSource findCProjectSourceById(Integer id) throws DataAccessException {

		return findCProjectSourceById(id, -1, -1);
	}

	/**
	 * JPQL Query - findCProjectSourceById
	 *
	 */

	@Transactional
	public CProjectSource findCProjectSourceById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findCProjectSourceById", startResult, maxRows, id);
			return (CProjectSource) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(CProjectSource entity) {
		return true;
	}
}
