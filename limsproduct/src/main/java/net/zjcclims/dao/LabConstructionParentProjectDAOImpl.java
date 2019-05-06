
package net.zjcclims.dao;

import net.zjcclims.domain.LabConstructionParentProject;
import org.skyway.spring.util.dao.AbstractJpaDao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * DAO to manage LabConstructionParentProject entities.
 * 
 */
@Repository("LabConstructionParentProjectDAO")

@Transactional
public class LabConstructionParentProjectDAOImpl extends AbstractJpaDao<LabConstructionParentProject>
		implements LabConstructionParentProjectDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] {
			LabConstructionParentProject.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabConstructionParentProjectDAOImpl
	 *
	 */
	public LabConstructionParentProjectDAOImpl() {
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
	 * JPQL Query - findLabConstructionParentProjectByCreateTimeBefore
	 *
	 */
	@Transactional
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByCreateTimeBefore(java.util.Calendar createTime) throws DataAccessException {

		return findLabConstructionParentProjectByCreateTimeBefore(createTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionParentProjectByCreateTimeBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByCreateTimeBefore(java.util.Calendar createTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionParentProjectByCreateTimeBefore", startResult, maxRows, createTime);
		return new LinkedHashSet<LabConstructionParentProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionParentProjectBySubmitted
	 *
	 */
	@Transactional
	public Set<LabConstructionParentProject> findLabConstructionParentProjectBySubmitted(Boolean submitted) throws DataAccessException {

		return findLabConstructionParentProjectBySubmitted(submitted, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionParentProjectBySubmitted
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionParentProject> findLabConstructionParentProjectBySubmitted(Boolean submitted, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionParentProjectBySubmitted", startResult, maxRows, submitted);
		return new LinkedHashSet<LabConstructionParentProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionParentProjectByProjectNameContaining
	 *
	 */
	@Transactional
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByProjectNameContaining(String projectName) throws DataAccessException {

		return findLabConstructionParentProjectByProjectNameContaining(projectName, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionParentProjectByProjectNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByProjectNameContaining(String projectName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionParentProjectByProjectNameContaining", startResult, maxRows, projectName);
		return new LinkedHashSet<LabConstructionParentProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionParentProjectByPrimaryKey
	 *
	 */
	@Transactional
	public LabConstructionParentProject findLabConstructionParentProjectByPrimaryKey(Integer id) throws DataAccessException {

		return findLabConstructionParentProjectByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionParentProjectByPrimaryKey
	 *
	 */

	@Transactional
	public LabConstructionParentProject findLabConstructionParentProjectByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabConstructionParentProjectByPrimaryKey", startResult, maxRows, id);
			return (LabConstructionParentProject) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabConstructionParentProjectByCreateUser
	 *
	 */
	@Transactional
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByCreateUser(String createUser) throws DataAccessException {

		return findLabConstructionParentProjectByCreateUser(createUser, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionParentProjectByCreateUser
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByCreateUser(String createUser, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionParentProjectByCreateUser", startResult, maxRows, createUser);
		return new LinkedHashSet<LabConstructionParentProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionParentProjectByCreateUserContaining
	 *
	 */
	@Transactional
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByCreateUserContaining(String createUser) throws DataAccessException {

		return findLabConstructionParentProjectByCreateUserContaining(createUser, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionParentProjectByCreateUserContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByCreateUserContaining(String createUser, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionParentProjectByCreateUserContaining", startResult, maxRows, createUser);
		return new LinkedHashSet<LabConstructionParentProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionParentProjectByBudget
	 *
	 */
	@Transactional
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByBudget(BigDecimal budget) throws DataAccessException {

		return findLabConstructionParentProjectByBudget(budget, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionParentProjectByBudget
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByBudget(BigDecimal budget, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionParentProjectByBudget", startResult, maxRows, budget);
		return new LinkedHashSet<LabConstructionParentProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionParentProjectByCreateTime
	 *
	 */
	@Transactional
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByCreateTime(java.util.Calendar createTime) throws DataAccessException {

		return findLabConstructionParentProjectByCreateTime(createTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionParentProjectByCreateTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByCreateTime(java.util.Calendar createTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionParentProjectByCreateTime", startResult, maxRows, createTime);
		return new LinkedHashSet<LabConstructionParentProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionParentProjectByCreateTimeAfter
	 *
	 */
	@Transactional
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByCreateTimeAfter(java.util.Calendar createTime) throws DataAccessException {

		return findLabConstructionParentProjectByCreateTimeAfter(createTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionParentProjectByCreateTimeAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByCreateTimeAfter(java.util.Calendar createTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionParentProjectByCreateTimeAfter", startResult, maxRows, createTime);
		return new LinkedHashSet<LabConstructionParentProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabConstructionParentProjects
	 *
	 */
	@Transactional
	public Set<LabConstructionParentProject> findAllLabConstructionParentProjects() throws DataAccessException {

		return findAllLabConstructionParentProjects(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabConstructionParentProjects
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionParentProject> findAllLabConstructionParentProjects(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabConstructionParentProjects", startResult, maxRows);
		return new LinkedHashSet<LabConstructionParentProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionParentProjectById
	 *
	 */
	@Transactional
	public LabConstructionParentProject findLabConstructionParentProjectById(Integer id) throws DataAccessException {

		return findLabConstructionParentProjectById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionParentProjectById
	 *
	 */

	@Transactional
	public LabConstructionParentProject findLabConstructionParentProjectById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabConstructionParentProjectById", startResult, maxRows, id);
			return (LabConstructionParentProject) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabConstructionParentProjectByProjectName
	 *
	 */
	@Transactional
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByProjectName(String projectName) throws DataAccessException {

		return findLabConstructionParentProjectByProjectName(projectName, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionParentProjectByProjectName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByProjectName(String projectName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionParentProjectByProjectName", startResult, maxRows, projectName);
		return new LinkedHashSet<LabConstructionParentProject>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(LabConstructionParentProject entity) {
		return true;
	}
}
