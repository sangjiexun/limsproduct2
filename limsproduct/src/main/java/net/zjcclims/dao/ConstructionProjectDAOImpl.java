package net.zjcclims.dao;


import net.zjcclims.domain.ConstructionProject;
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
 * DAO to manage ConstructionProject entities.
 * 
 */
@Repository("ConstructionProjectDAO")
@Transactional
public class ConstructionProjectDAOImpl extends AbstractJpaDao<ConstructionProject>
		implements ConstructionProjectDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ConstructionProject.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulims
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ConstructionProjectDAOImpl
	 *
	 */
	public ConstructionProjectDAOImpl() {
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
	 * JPQL Query - findAllConstructionProjects
	 *
	 */
	@Transactional
	public Set<ConstructionProject> findAllConstructionProjects() throws DataAccessException {

		return findAllConstructionProjects(-1, -1);
	}

	/**
	 * JPQL Query - findAllConstructionProjects
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ConstructionProject> findAllConstructionProjects(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllConstructionProjects", startResult, maxRows);
		return new LinkedHashSet<ConstructionProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findConstructionProjectById
	 *
	 */
	@Transactional
	public ConstructionProject findConstructionProjectById(Integer id) throws DataAccessException {

		return findConstructionProjectById(id, -1, -1);
	}

	/**
	 * JPQL Query - findConstructionProjectById
	 *
	 */

	@Transactional
	public ConstructionProject findConstructionProjectById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findConstructionProjectById", startResult, maxRows, id);
			return (ConstructionProject) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findConstructionProjectByPrimaryKey
	 *
	 */
	@Transactional
	public ConstructionProject findConstructionProjectByPrimaryKey(Integer id) throws DataAccessException {

		return findConstructionProjectByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findConstructionProjectByPrimaryKey
	 *
	 */

	@Transactional
	public ConstructionProject findConstructionProjectByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findConstructionProjectByPrimaryKey", startResult, maxRows, id);
			return (ConstructionProject) query.getSingleResult();
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
	public boolean canBeMerged(ConstructionProject entity) {
		return true;
	}
}
