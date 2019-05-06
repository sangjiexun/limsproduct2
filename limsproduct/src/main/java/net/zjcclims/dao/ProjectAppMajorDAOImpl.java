package net.zjcclims.dao;


import net.zjcclims.domain.ProjectAppMajor;
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
 * DAO to manage ProjectAppMajor entities.
 * 
 */
@Repository("ProjectAppMajorDAO")
@Transactional
public class ProjectAppMajorDAOImpl extends AbstractJpaDao<ProjectAppMajor>
		implements ProjectAppMajorDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ProjectAppMajor.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ProjectAppMajorDAOImpl
	 *
	 */
	public ProjectAppMajorDAOImpl() {
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
	 * JPQL Query - findAllProjectAppMajors
	 *
	 */
	@Transactional
	public Set<ProjectAppMajor> findAllProjectAppMajors() throws DataAccessException {

		return findAllProjectAppMajors(-1, -1);
	}

	/**
	 * JPQL Query - findAllProjectAppMajors
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAppMajor> findAllProjectAppMajors(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllProjectAppMajors", startResult, maxRows);
		return new LinkedHashSet<ProjectAppMajor>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAppMajorById
	 *
	 */
	@Transactional
	public ProjectAppMajor findProjectAppMajorById(Integer id) throws DataAccessException {

		return findProjectAppMajorById(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAppMajorById
	 *
	 */

	@Transactional
	public ProjectAppMajor findProjectAppMajorById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectAppMajorById", startResult, maxRows, id);
			return (ProjectAppMajor) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectAppMajorByPrimaryKey
	 *
	 */
	@Transactional
	public ProjectAppMajor findProjectAppMajorByPrimaryKey(Integer id) throws DataAccessException {

		return findProjectAppMajorByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAppMajorByPrimaryKey
	 *
	 */

	@Transactional
	public ProjectAppMajor findProjectAppMajorByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectAppMajorByPrimaryKey", startResult, maxRows, id);
			return (ProjectAppMajor) query.getSingleResult();
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
	public boolean canBeMerged(ProjectAppMajor entity) {
		return true;
	}
}
