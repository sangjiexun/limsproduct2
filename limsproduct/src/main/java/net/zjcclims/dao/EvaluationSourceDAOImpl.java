package net.zjcclims.dao;


import net.zjcclims.domain.EvaluationSource;
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
 * DAO to manage EvaluationSource entities.
 * 
 */
@Repository("EvaluationSourceDAO")
@Transactional
public class EvaluationSourceDAOImpl extends AbstractJpaDao<EvaluationSource>
		implements EvaluationSourceDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { EvaluationSource.class }));

	/**
	 * EntityManager injected by Spring for persistence unit xzyxyConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new EvaluationSourceDAOImpl
	 *
	 */
	public EvaluationSourceDAOImpl() {
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
	 * JPQL Query - findEvaluationSourceById
	 *
	 */
	@Transactional
	public EvaluationSource findEvaluationSourceById(Integer id) throws DataAccessException {

		return findEvaluationSourceById(id, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationSourceById
	 *
	 */

	@Transactional
	public EvaluationSource findEvaluationSourceById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findEvaluationSourceById", startResult, maxRows, id);
			return (EvaluationSource) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllEvaluationSources
	 *
	 */
	@Transactional
	public Set<EvaluationSource> findAllEvaluationSources() throws DataAccessException {

		return findAllEvaluationSources(-1, -1);
	}

	/**
	 * JPQL Query - findAllEvaluationSources
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationSource> findAllEvaluationSources(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllEvaluationSources", startResult, maxRows);
		return new LinkedHashSet<EvaluationSource>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationSourceByPrimaryKey
	 *
	 */
	@Transactional
	public EvaluationSource findEvaluationSourceByPrimaryKey(Integer id) throws DataAccessException {

		return findEvaluationSourceByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationSourceByPrimaryKey
	 *
	 */

	@Transactional
	public EvaluationSource findEvaluationSourceByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findEvaluationSourceByPrimaryKey", startResult, maxRows, id);
			return (EvaluationSource) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findEvaluationSourceByTeacherContaining
	 *
	 */
	@Transactional
	public Set<EvaluationSource> findEvaluationSourceByTeacherContaining(String teacher) throws DataAccessException {

		return findEvaluationSourceByTeacherContaining(teacher, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationSourceByTeacherContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationSource> findEvaluationSourceByTeacherContaining(String teacher, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationSourceByTeacherContaining", startResult, maxRows, teacher);
		return new LinkedHashSet<EvaluationSource>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationSourceByTermId
	 *
	 */
	@Transactional
	public Set<EvaluationSource> findEvaluationSourceByTermId(Integer termId) throws DataAccessException {

		return findEvaluationSourceByTermId(termId, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationSourceByTermId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationSource> findEvaluationSourceByTermId(Integer termId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationSourceByTermId", startResult, maxRows, termId);
		return new LinkedHashSet<EvaluationSource>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationSourceByTeacher
	 *
	 */
	@Transactional
	public Set<EvaluationSource> findEvaluationSourceByTeacher(String teacher) throws DataAccessException {

		return findEvaluationSourceByTeacher(teacher, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationSourceByTeacher
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationSource> findEvaluationSourceByTeacher(String teacher, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationSourceByTeacher", startResult, maxRows, teacher);
		return new LinkedHashSet<EvaluationSource>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationSourceByStudentContaining
	 *
	 */
	@Transactional
	public Set<EvaluationSource> findEvaluationSourceByStudentContaining(String student) throws DataAccessException {

		return findEvaluationSourceByStudentContaining(student, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationSourceByStudentContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationSource> findEvaluationSourceByStudentContaining(String student, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationSourceByStudentContaining", startResult, maxRows, student);
		return new LinkedHashSet<EvaluationSource>(query.getResultList());
	}

	/**
	 * JPQL Query - findEvaluationSourceByStudent
	 *
	 */
	@Transactional
	public Set<EvaluationSource> findEvaluationSourceByStudent(String student) throws DataAccessException {

		return findEvaluationSourceByStudent(student, -1, -1);
	}

	/**
	 * JPQL Query - findEvaluationSourceByStudent
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<EvaluationSource> findEvaluationSourceByStudent(String student, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findEvaluationSourceByStudent", startResult, maxRows, student);
		return new LinkedHashSet<EvaluationSource>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(EvaluationSource entity) {
		return true;
	}
}
