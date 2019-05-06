package net.zjcclims.dao;

import net.zjcclims.domain.OperationOutlineCourse;
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
 * DAO to manage OperationOutlineCourse entities.
 * 
 */
@Repository("OperationOutlineCourseDAO")
@Transactional
public class OperationOutlineCourseDAOImpl extends AbstractJpaDao<OperationOutlineCourse>
		implements OperationOutlineCourseDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { OperationOutlineCourse.class }));

	/**
	 * EntityManager injected by Spring for persistence unit fbConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new OperationOutlineCourseDAOImpl
	 *
	 */
	public OperationOutlineCourseDAOImpl() {
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
	 * JPQL Query - findOperationOutlineCourseByCourseContent
	 *
	 */
	@Transactional
	public Set<OperationOutlineCourse> findOperationOutlineCourseByCourseContent(String courseContent) throws DataAccessException {

		return findOperationOutlineCourseByCourseContent(courseContent, -1, -1);
	}

	/**
	 * JPQL Query - findOperationOutlineCourseByCourseContent
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<OperationOutlineCourse> findOperationOutlineCourseByCourseContent(String courseContent, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findOperationOutlineCourseByCourseContent", startResult, maxRows, courseContent);
		return new LinkedHashSet<OperationOutlineCourse>(query.getResultList());
	}

	/**
	 * JPQL Query - findOperationOutlineCourseByWeek
	 *
	 */
	@Transactional
	public Set<OperationOutlineCourse> findOperationOutlineCourseByWeek(Integer week) throws DataAccessException {

		return findOperationOutlineCourseByWeek(week, -1, -1);
	}

	/**
	 * JPQL Query - findOperationOutlineCourseByWeek
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<OperationOutlineCourse> findOperationOutlineCourseByWeek(Integer week, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findOperationOutlineCourseByWeek", startResult, maxRows, week);
		return new LinkedHashSet<OperationOutlineCourse>(query.getResultList());
	}

	/**
	 * JPQL Query - findOperationOutlineCourseByPrimaryKey
	 *
	 */
	@Transactional
	public OperationOutlineCourse findOperationOutlineCourseByPrimaryKey(Integer id) throws DataAccessException {

		return findOperationOutlineCourseByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findOperationOutlineCourseByPrimaryKey
	 *
	 */

	@Transactional
	public OperationOutlineCourse findOperationOutlineCourseByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findOperationOutlineCourseByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.OperationOutlineCourse) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllOperationOutlineCourses
	 *
	 */
	@Transactional
	public Set<OperationOutlineCourse> findAllOperationOutlineCourses() throws DataAccessException {

		return findAllOperationOutlineCourses(-1, -1);
	}

	/**
	 * JPQL Query - findAllOperationOutlineCourses
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<OperationOutlineCourse> findAllOperationOutlineCourses(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllOperationOutlineCourses", startResult, maxRows);
		return new LinkedHashSet<OperationOutlineCourse>(query.getResultList());
	}

	/**
	 * JPQL Query - findOperationOutlineCourseById
	 *
	 */
	@Transactional
	public OperationOutlineCourse findOperationOutlineCourseById(Integer id) throws DataAccessException {

		return findOperationOutlineCourseById(id, -1, -1);
	}

	/**
	 * JPQL Query - findOperationOutlineCourseById
	 *
	 */

	@Transactional
	public OperationOutlineCourse findOperationOutlineCourseById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findOperationOutlineCourseById", startResult, maxRows, id);
			return (net.zjcclims.domain.OperationOutlineCourse) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 *
	 * 
	 *
	 */
	public boolean canBeMerged(OperationOutlineCourse entity) {
		return true;
	}
}
