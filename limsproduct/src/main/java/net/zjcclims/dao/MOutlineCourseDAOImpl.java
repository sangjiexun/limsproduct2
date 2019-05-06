package net.zjcclims.dao;

import net.zjcclims.domain.MOutlineCourse;
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
 * DAO to manage MOutlineCourse entities.
 * 
 */
@Repository("MOutlineCourseDAO")
@Transactional
public class MOutlineCourseDAOImpl extends AbstractJpaDao<MOutlineCourse>
		implements MOutlineCourseDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { MOutlineCourse.class }));

	/**
	 * EntityManager injected by Spring for persistence unit fbmulimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new MOutlineCourseDAOImpl
	 *
	 */
	public MOutlineCourseDAOImpl() {
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
	 * JPQL Query - findMOutlineCourseByPrimaryKey
	 *
	 */
	@Transactional
	public MOutlineCourse findMOutlineCourseByPrimaryKey(Integer id) throws DataAccessException {

		return findMOutlineCourseByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findMOutlineCourseByPrimaryKey
	 *
	 */

	@Transactional
	public MOutlineCourse findMOutlineCourseByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findMOutlineCourseByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.MOutlineCourse) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findMOutlineCourseByFlagContaining
	 *
	 */
	@Transactional
	public Set<MOutlineCourse> findMOutlineCourseByFlagContaining(String flag) throws DataAccessException {

		return findMOutlineCourseByFlagContaining(flag, -1, -1);
	}

	/**
	 * JPQL Query - findMOutlineCourseByFlagContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<MOutlineCourse> findMOutlineCourseByFlagContaining(String flag, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findMOutlineCourseByFlagContaining", startResult, maxRows, flag);
		return new LinkedHashSet<MOutlineCourse>(query.getResultList());
	}

	/**
	 * JPQL Query - findMOutlineCourseById
	 *
	 */
	@Transactional
	public MOutlineCourse findMOutlineCourseById(Integer id) throws DataAccessException {

		return findMOutlineCourseById(id, -1, -1);
	}

	/**
	 * JPQL Query - findMOutlineCourseById
	 *
	 */

	@Transactional
	public MOutlineCourse findMOutlineCourseById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findMOutlineCourseById", startResult, maxRows, id);
			return (net.zjcclims.domain.MOutlineCourse) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllMOutlineCourses
	 *
	 */
	@Transactional
	public Set<MOutlineCourse> findAllMOutlineCourses() throws DataAccessException {

		return findAllMOutlineCourses(-1, -1);
	}

	/**
	 * JPQL Query - findAllMOutlineCourses
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<MOutlineCourse> findAllMOutlineCourses(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllMOutlineCourses", startResult, maxRows);
		return new LinkedHashSet<MOutlineCourse>(query.getResultList());
	}

	/**
	 * JPQL Query - findMOutlineCourseByFlag
	 *
	 */
	@Transactional
	public Set<MOutlineCourse> findMOutlineCourseByFlag(String flag) throws DataAccessException {

		return findMOutlineCourseByFlag(flag, -1, -1);
	}

	/**
	 * JPQL Query - findMOutlineCourseByFlag
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<MOutlineCourse> findMOutlineCourseByFlag(String flag, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findMOutlineCourseByFlag", startResult, maxRows, flag);
		return new LinkedHashSet<MOutlineCourse>(query.getResultList());
	}

	/**
	 * JPQL Query - findMOutlineCourseByCombine
	 *
	 */
	@Transactional
	public Set<MOutlineCourse> findMOutlineCourseByCombine(String combine) throws DataAccessException {

		return findMOutlineCourseByCombine(combine, -1, -1);
	}

	/**
	 * JPQL Query - findMOutlineCourseByCombine
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<MOutlineCourse> findMOutlineCourseByCombine(String combine, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findMOutlineCourseByCombine", startResult, maxRows, combine);
		return new LinkedHashSet<MOutlineCourse>(query.getResultList());
	}

	/**
	 * JPQL Query - findMOutlineCourseByCombineContaining
	 *
	 */
	@Transactional
	public Set<MOutlineCourse> findMOutlineCourseByCombineContaining(String combine) throws DataAccessException {

		return findMOutlineCourseByCombineContaining(combine, -1, -1);
	}

	/**
	 * JPQL Query - findMOutlineCourseByCombineContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<MOutlineCourse> findMOutlineCourseByCombineContaining(String combine, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findMOutlineCourseByCombineContaining", startResult, maxRows, combine);
		return new LinkedHashSet<MOutlineCourse>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 *
	 * 
	 *
	 */
	public boolean canBeMerged(MOutlineCourse entity) {
		return true;
	}
}
