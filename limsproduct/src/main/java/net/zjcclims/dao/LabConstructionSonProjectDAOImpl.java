
package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.LabConstructionSonProject;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage LabConstructionSonProject entities.
 * 
 */
@Repository("LabConstructionSonProjectDAO")

@Transactional
public class LabConstructionSonProjectDAOImpl extends AbstractJpaDao<LabConstructionSonProject>
		implements LabConstructionSonProjectDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] {
			LabConstructionSonProject.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabConstructionSonProjectDAOImpl
	 *
	 */
	public LabConstructionSonProjectDAOImpl() {
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
	 * JPQL Query - findLabConstructionSonProjectByBudgetBalanceTimeAfter
	 *
	 */
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByBudgetBalanceTimeAfter(java.util.Calendar budgetBalanceTime) throws DataAccessException {

		return findLabConstructionSonProjectByBudgetBalanceTimeAfter(budgetBalanceTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByBudgetBalanceTimeAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByBudgetBalanceTimeAfter(java.util.Calendar budgetBalanceTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionSonProjectByBudgetBalanceTimeAfter", startResult, maxRows, budgetBalanceTime);
		return new LinkedHashSet<LabConstructionSonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByCreateUser
	 *
	 */
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByCreateUser(String createUser) throws DataAccessException {

		return findLabConstructionSonProjectByCreateUser(createUser, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByCreateUser
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByCreateUser(String createUser, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionSonProjectByCreateUser", startResult, maxRows, createUser);
		return new LinkedHashSet<LabConstructionSonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectById
	 *
	 */
	@Transactional
	public LabConstructionSonProject findLabConstructionSonProjectById(Integer id) throws DataAccessException {

		return findLabConstructionSonProjectById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectById
	 *
	 */

	@Transactional
	public LabConstructionSonProject findLabConstructionSonProjectById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabConstructionSonProjectById", startResult, maxRows, id);
			return (LabConstructionSonProject) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByBudget
	 *
	 */
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByBudget(Integer budget) throws DataAccessException {

		return findLabConstructionSonProjectByBudget(budget, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByBudget
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByBudget(Integer budget, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionSonProjectByBudget", startResult, maxRows, budget);
		return new LinkedHashSet<LabConstructionSonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByProjectImplementTimeAfter
	 *
	 */
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByProjectImplementTimeAfter(java.util.Calendar projectImplementTime) throws DataAccessException {

		return findLabConstructionSonProjectByProjectImplementTimeAfter(projectImplementTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByProjectImplementTimeAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByProjectImplementTimeAfter(java.util.Calendar projectImplementTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionSonProjectByProjectImplementTimeAfter", startResult, maxRows, projectImplementTime);
		return new LinkedHashSet<LabConstructionSonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabConstructionSonProjects
	 *
	 */
	@Transactional
	public Set<LabConstructionSonProject> findAllLabConstructionSonProjects() throws DataAccessException {

		return findAllLabConstructionSonProjects(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabConstructionSonProjects
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionSonProject> findAllLabConstructionSonProjects(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabConstructionSonProjects", startResult, maxRows);
		return new LinkedHashSet<LabConstructionSonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByPrimaryKey
	 *
	 */
	@Transactional
	public LabConstructionSonProject findLabConstructionSonProjectByPrimaryKey(Integer id) throws DataAccessException {

		return findLabConstructionSonProjectByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByPrimaryKey
	 *
	 */

	@Transactional
	public LabConstructionSonProject findLabConstructionSonProjectByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabConstructionSonProjectByPrimaryKey", startResult, maxRows, id);
			return (LabConstructionSonProject) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByCreateUserContaining
	 *
	 */
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByCreateUserContaining(String createUser) throws DataAccessException {

		return findLabConstructionSonProjectByCreateUserContaining(createUser, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByCreateUserContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByCreateUserContaining(String createUser, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionSonProjectByCreateUserContaining", startResult, maxRows, createUser);
		return new LinkedHashSet<LabConstructionSonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByCreateTime
	 *
	 */
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByCreateTime(java.util.Calendar createTime) throws DataAccessException {

		return findLabConstructionSonProjectByCreateTime(createTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByCreateTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByCreateTime(java.util.Calendar createTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionSonProjectByCreateTime", startResult, maxRows, createTime);
		return new LinkedHashSet<LabConstructionSonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByBudgetBalanceTimeBefore
	 *
	 */
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByBudgetBalanceTimeBefore(java.util.Calendar budgetBalanceTime) throws DataAccessException {

		return findLabConstructionSonProjectByBudgetBalanceTimeBefore(budgetBalanceTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByBudgetBalanceTimeBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByBudgetBalanceTimeBefore(java.util.Calendar budgetBalanceTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionSonProjectByBudgetBalanceTimeBefore", startResult, maxRows, budgetBalanceTime);
		return new LinkedHashSet<LabConstructionSonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByProjectNameContaining
	 *
	 */
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByProjectNameContaining(String projectName) throws DataAccessException {

		return findLabConstructionSonProjectByProjectNameContaining(projectName, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByProjectNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByProjectNameContaining(String projectName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionSonProjectByProjectNameContaining", startResult, maxRows, projectName);
		return new LinkedHashSet<LabConstructionSonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByProjectImplementTime
	 *
	 */
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByProjectImplementTime(java.util.Calendar projectImplementTime) throws DataAccessException {

		return findLabConstructionSonProjectByProjectImplementTime(projectImplementTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByProjectImplementTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByProjectImplementTime(java.util.Calendar projectImplementTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionSonProjectByProjectImplementTime", startResult, maxRows, projectImplementTime);
		return new LinkedHashSet<LabConstructionSonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByProjectImplementTimeBefore
	 *
	 */
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByProjectImplementTimeBefore(java.util.Calendar projectImplementTime) throws DataAccessException {

		return findLabConstructionSonProjectByProjectImplementTimeBefore(projectImplementTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByProjectImplementTimeBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByProjectImplementTimeBefore(java.util.Calendar projectImplementTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionSonProjectByProjectImplementTimeBefore", startResult, maxRows, projectImplementTime);
		return new LinkedHashSet<LabConstructionSonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByCreateTimeBefore
	 *
	 */
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByCreateTimeBefore(java.util.Calendar createTime) throws DataAccessException {

		return findLabConstructionSonProjectByCreateTimeBefore(createTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByCreateTimeBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByCreateTimeBefore(java.util.Calendar createTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionSonProjectByCreateTimeBefore", startResult, maxRows, createTime);
		return new LinkedHashSet<LabConstructionSonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByCreateTimeAfter
	 *
	 */
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByCreateTimeAfter(java.util.Calendar createTime) throws DataAccessException {

		return findLabConstructionSonProjectByCreateTimeAfter(createTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByCreateTimeAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByCreateTimeAfter(java.util.Calendar createTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionSonProjectByCreateTimeAfter", startResult, maxRows, createTime);
		return new LinkedHashSet<LabConstructionSonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByProjectName
	 *
	 */
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByProjectName(String projectName) throws DataAccessException {

		return findLabConstructionSonProjectByProjectName(projectName, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByProjectName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByProjectName(String projectName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionSonProjectByProjectName", startResult, maxRows, projectName);
		return new LinkedHashSet<LabConstructionSonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectBySubmitted
	 *
	 */
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectBySubmitted(Boolean submitted) throws DataAccessException {

		return findLabConstructionSonProjectBySubmitted(submitted, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectBySubmitted
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectBySubmitted(Boolean submitted, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionSonProjectBySubmitted", startResult, maxRows, submitted);
		return new LinkedHashSet<LabConstructionSonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByBudgetBalanceTime
	 *
	 */
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByBudgetBalanceTime(java.util.Calendar budgetBalanceTime) throws DataAccessException {

		return findLabConstructionSonProjectByBudgetBalanceTime(budgetBalanceTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionSonProjectByBudgetBalanceTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByBudgetBalanceTime(java.util.Calendar budgetBalanceTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionSonProjectByBudgetBalanceTime", startResult, maxRows, budgetBalanceTime);
		return new LinkedHashSet<LabConstructionSonProject>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(LabConstructionSonProject entity) {
		return true;
	}
}
