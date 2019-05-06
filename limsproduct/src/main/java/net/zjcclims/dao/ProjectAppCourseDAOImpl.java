package net.zjcclims.dao;


import net.zjcclims.domain.ProjectAppCourse;
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
 * DAO to manage ProjectAppCourse entities.
 * 
 */
@Repository("ProjectAppCourseDAO")
@Transactional
public class ProjectAppCourseDAOImpl extends AbstractJpaDao<ProjectAppCourse>
		implements ProjectAppCourseDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ProjectAppCourse.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ProjectAppCourseDAOImpl
	 *
	 */
	public ProjectAppCourseDAOImpl() {
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
	 * JPQL Query - findProjectAppCourseByInfo
	 *
	 */
	@Transactional
	public Set<ProjectAppCourse> findProjectAppCourseByInfo(String info) throws DataAccessException {

		return findProjectAppCourseByInfo(info, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAppCourseByInfo
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAppCourse> findProjectAppCourseByInfo(String info, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAppCourseByInfo", startResult, maxRows, info);
		return new LinkedHashSet<ProjectAppCourse>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAppCourseByInfoContaining
	 *
	 */
	@Transactional
	public Set<ProjectAppCourse> findProjectAppCourseByInfoContaining(String info) throws DataAccessException {

		return findProjectAppCourseByInfoContaining(info, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAppCourseByInfoContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAppCourse> findProjectAppCourseByInfoContaining(String info, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAppCourseByInfoContaining", startResult, maxRows, info);
		return new LinkedHashSet<ProjectAppCourse>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllProjectAppCourses
	 *
	 */
	@Transactional
	public Set<ProjectAppCourse> findAllProjectAppCourses() throws DataAccessException {

		return findAllProjectAppCourses(-1, -1);
	}

	/**
	 * JPQL Query - findAllProjectAppCourses
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAppCourse> findAllProjectAppCourses(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllProjectAppCourses", startResult, maxRows);
		return new LinkedHashSet<ProjectAppCourse>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAppCourseByPrimaryKey
	 *
	 */
	@Transactional
	public ProjectAppCourse findProjectAppCourseByPrimaryKey(Integer id) throws DataAccessException {

		return findProjectAppCourseByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAppCourseByPrimaryKey
	 *
	 */

	@Transactional
	public ProjectAppCourse findProjectAppCourseByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectAppCourseByPrimaryKey", startResult, maxRows, id);
			return (ProjectAppCourse) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectAppCourseById
	 *
	 */
	@Transactional
	public ProjectAppCourse findProjectAppCourseById(Integer id) throws DataAccessException {

		return findProjectAppCourseById(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAppCourseById
	 *
	 */

	@Transactional
	public ProjectAppCourse findProjectAppCourseById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectAppCourseById", startResult, maxRows, id);
			return (ProjectAppCourse) query.getSingleResult();
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
	public boolean canBeMerged(ProjectAppCourse entity) {
		return true;
	}
}
