
package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.LabConstructionGrandsonProject;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage LabConstructionGrandsonProject entities.
 * 
 */
@Repository("LabConstructionGrandsonProjectDAO")

@Transactional
public class LabConstructionGrandsonProjectDAOImpl extends AbstractJpaDao<LabConstructionGrandsonProject>
		implements LabConstructionGrandsonProjectDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] {
			LabConstructionGrandsonProject.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabConstructionGrandsonProjectDAOImpl
	 *
	 */
	public LabConstructionGrandsonProjectDAOImpl() {
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
	 * JPQL Query - findLabConstructionGrandsonProjectByProjectImplementTimeBefore
	 *
	 */
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByProjectImplementTimeBefore(java.util.Calendar projectImplementTime) throws DataAccessException {

		return findLabConstructionGrandsonProjectByProjectImplementTimeBefore(projectImplementTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByProjectImplementTimeBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByProjectImplementTimeBefore(java.util.Calendar projectImplementTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionGrandsonProjectByProjectImplementTimeBefore", startResult, maxRows, projectImplementTime);
		return new LinkedHashSet<LabConstructionGrandsonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByProjectNameContaining
	 *
	 */
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByProjectNameContaining(String projectName) throws DataAccessException {

		return findLabConstructionGrandsonProjectByProjectNameContaining(projectName, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByProjectNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByProjectNameContaining(String projectName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionGrandsonProjectByProjectNameContaining", startResult, maxRows, projectName);
		return new LinkedHashSet<LabConstructionGrandsonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByTenderActualAmount
	 *
	 */
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByTenderActualAmount(Integer tenderActualAmount) throws DataAccessException {

		return findLabConstructionGrandsonProjectByTenderActualAmount(tenderActualAmount, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByTenderActualAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByTenderActualAmount(Integer tenderActualAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionGrandsonProjectByTenderActualAmount", startResult, maxRows, tenderActualAmount);
		return new LinkedHashSet<LabConstructionGrandsonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByBudget
	 *
	 */
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByBudget(Integer budget) throws DataAccessException {

		return findLabConstructionGrandsonProjectByBudget(budget, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByBudget
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByBudget(Integer budget, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionGrandsonProjectByBudget", startResult, maxRows, budget);
		return new LinkedHashSet<LabConstructionGrandsonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectById
	 *
	 */
	@Transactional
	public LabConstructionGrandsonProject findLabConstructionGrandsonProjectById(Integer id) throws DataAccessException {

		return findLabConstructionGrandsonProjectById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectById
	 *
	 */

	@Transactional
	public LabConstructionGrandsonProject findLabConstructionGrandsonProjectById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabConstructionGrandsonProjectById", startResult, maxRows, id);
			return (LabConstructionGrandsonProject) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByCreateUser
	 *
	 */
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByCreateUser(String createUser) throws DataAccessException {

		return findLabConstructionGrandsonProjectByCreateUser(createUser, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByCreateUser
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByCreateUser(String createUser, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionGrandsonProjectByCreateUser", startResult, maxRows, createUser);
		return new LinkedHashSet<LabConstructionGrandsonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByStatus
	 *
	 */
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByStatus(Integer status) throws DataAccessException {

		return findLabConstructionGrandsonProjectByStatus(status, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByStatus
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByStatus(Integer status, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionGrandsonProjectByStatus", startResult, maxRows, status);
		return new LinkedHashSet<LabConstructionGrandsonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByCreateTimeBefore
	 *
	 */
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByCreateTimeBefore(java.util.Calendar createTime) throws DataAccessException {

		return findLabConstructionGrandsonProjectByCreateTimeBefore(createTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByCreateTimeBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByCreateTimeBefore(java.util.Calendar createTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionGrandsonProjectByCreateTimeBefore", startResult, maxRows, createTime);
		return new LinkedHashSet<LabConstructionGrandsonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByCreateUserContaining
	 *
	 */
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByCreateUserContaining(String createUser) throws DataAccessException {

		return findLabConstructionGrandsonProjectByCreateUserContaining(createUser, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByCreateUserContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByCreateUserContaining(String createUser, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionGrandsonProjectByCreateUserContaining", startResult, maxRows, createUser);
		return new LinkedHashSet<LabConstructionGrandsonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByCreateTime
	 *
	 */
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByCreateTime(java.util.Calendar createTime) throws DataAccessException {

		return findLabConstructionGrandsonProjectByCreateTime(createTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByCreateTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByCreateTime(java.util.Calendar createTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionGrandsonProjectByCreateTime", startResult, maxRows, createTime);
		return new LinkedHashSet<LabConstructionGrandsonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByAcceptanceaActualAmount
	 *
	 */
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByAcceptanceaActualAmount(Integer acceptanceaActualAmount) throws DataAccessException {

		return findLabConstructionGrandsonProjectByAcceptanceaActualAmount(acceptanceaActualAmount, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByAcceptanceaActualAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByAcceptanceaActualAmount(Integer acceptanceaActualAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionGrandsonProjectByAcceptanceaActualAmount", startResult, maxRows, acceptanceaActualAmount);
		return new LinkedHashSet<LabConstructionGrandsonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabConstructionGrandsonProjects
	 *
	 */
	@Transactional
	public Set<LabConstructionGrandsonProject> findAllLabConstructionGrandsonProjects() throws DataAccessException {

		return findAllLabConstructionGrandsonProjects(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabConstructionGrandsonProjects
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionGrandsonProject> findAllLabConstructionGrandsonProjects(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabConstructionGrandsonProjects", startResult, maxRows);
		return new LinkedHashSet<LabConstructionGrandsonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByStage
	 *
	 */
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByStage(Integer stage) throws DataAccessException {

		return findLabConstructionGrandsonProjectByStage(stage, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByStage
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByStage(Integer stage, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionGrandsonProjectByStage", startResult, maxRows, stage);
		return new LinkedHashSet<LabConstructionGrandsonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByProjectImplementTimeAfter
	 *
	 */
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByProjectImplementTimeAfter(java.util.Calendar projectImplementTime) throws DataAccessException {

		return findLabConstructionGrandsonProjectByProjectImplementTimeAfter(projectImplementTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByProjectImplementTimeAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByProjectImplementTimeAfter(java.util.Calendar projectImplementTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionGrandsonProjectByProjectImplementTimeAfter", startResult, maxRows, projectImplementTime);
		return new LinkedHashSet<LabConstructionGrandsonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByProjectName
	 *
	 */
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByProjectName(String projectName) throws DataAccessException {

		return findLabConstructionGrandsonProjectByProjectName(projectName, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByProjectName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByProjectName(String projectName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionGrandsonProjectByProjectName", startResult, maxRows, projectName);
		return new LinkedHashSet<LabConstructionGrandsonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByCreateTimeAfter
	 *
	 */
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByCreateTimeAfter(java.util.Calendar createTime) throws DataAccessException {

		return findLabConstructionGrandsonProjectByCreateTimeAfter(createTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByCreateTimeAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByCreateTimeAfter(java.util.Calendar createTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionGrandsonProjectByCreateTimeAfter", startResult, maxRows, createTime);
		return new LinkedHashSet<LabConstructionGrandsonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByProjectImplementTime
	 *
	 */
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByProjectImplementTime(java.util.Calendar projectImplementTime) throws DataAccessException {

		return findLabConstructionGrandsonProjectByProjectImplementTime(projectImplementTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByProjectImplementTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByProjectImplementTime(java.util.Calendar projectImplementTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionGrandsonProjectByProjectImplementTime", startResult, maxRows, projectImplementTime);
		return new LinkedHashSet<LabConstructionGrandsonProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByPrimaryKey
	 *
	 */
	@Transactional
	public LabConstructionGrandsonProject findLabConstructionGrandsonProjectByPrimaryKey(Integer id) throws DataAccessException {

		return findLabConstructionGrandsonProjectByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByPrimaryKey
	 *
	 */

	@Transactional
	public LabConstructionGrandsonProject findLabConstructionGrandsonProjectByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabConstructionGrandsonProjectByPrimaryKey", startResult, maxRows, id);
			return (LabConstructionGrandsonProject) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(LabConstructionGrandsonProject entity) {
		return true;
	}
}
