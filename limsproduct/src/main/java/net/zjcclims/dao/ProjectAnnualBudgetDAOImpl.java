package net.zjcclims.dao;


import net.zjcclims.domain.ProjectAnnualBudget;
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
 * DAO to manage ProjectAnnualBudget entities.
 * 
 */
@Repository("ProjectAnnualBudgetDAO")
@Transactional
public class ProjectAnnualBudgetDAOImpl extends AbstractJpaDao<ProjectAnnualBudget>
		implements ProjectAnnualBudgetDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ProjectAnnualBudget.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ProjectAnnualBudgetDAOImpl
	 *
	 */
	public ProjectAnnualBudgetDAOImpl() {
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
	 * JPQL Query - findProjectAnnualBudgetByProjectSourceContaining
	 *
	 */
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectSourceContaining(String projectSource) throws DataAccessException {

		return findProjectAnnualBudgetByProjectSourceContaining(projectSource, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectSourceContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectSourceContaining(String projectSource, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAnnualBudgetByProjectSourceContaining", startResult, maxRows, projectSource);
		return new LinkedHashSet<ProjectAnnualBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetById
	 *
	 */
	@Transactional
	public ProjectAnnualBudget findProjectAnnualBudgetById(Integer id) throws DataAccessException {

		return findProjectAnnualBudgetById(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetById
	 *
	 */

	@Transactional
	public ProjectAnnualBudget findProjectAnnualBudgetById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectAnnualBudgetById", startResult, maxRows, id);
			return (ProjectAnnualBudget) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByStatus
	 *
	 */
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByStatus(String status) throws DataAccessException {

		return findProjectAnnualBudgetByStatus(status, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByStatus
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByStatus(String status, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAnnualBudgetByStatus", startResult, maxRows, status);
		return new LinkedHashSet<ProjectAnnualBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectFunds
	 *
	 */
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectFunds(String projectFunds) throws DataAccessException {

		return findProjectAnnualBudgetByProjectFunds(projectFunds, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectFunds
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectFunds(String projectFunds, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAnnualBudgetByProjectFunds", startResult, maxRows, projectFunds);
		return new LinkedHashSet<ProjectAnnualBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectNameContaining
	 *
	 */
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectNameContaining(String projectName) throws DataAccessException {

		return findProjectAnnualBudgetByProjectNameContaining(projectName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectNameContaining(String projectName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAnnualBudgetByProjectNameContaining", startResult, maxRows, projectName);
		return new LinkedHashSet<ProjectAnnualBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByDeadline
	 *
	 */
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByDeadline(java.util.Calendar deadline) throws DataAccessException {

		return findProjectAnnualBudgetByDeadline(deadline, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByDeadline
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByDeadline(java.util.Calendar deadline, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAnnualBudgetByDeadline", startResult, maxRows, deadline);
		return new LinkedHashSet<ProjectAnnualBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByAcademyName
	 *
	 */
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByAcademyName(String academyName) throws DataAccessException {

		return findProjectAnnualBudgetByAcademyName(academyName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByAcademyName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByAcademyName(String academyName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAnnualBudgetByAcademyName", startResult, maxRows, academyName);
		return new LinkedHashSet<ProjectAnnualBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByAcademyNameContaining
	 *
	 */
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByAcademyNameContaining(String academyName) throws DataAccessException {

		return findProjectAnnualBudgetByAcademyNameContaining(academyName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByAcademyNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByAcademyNameContaining(String academyName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAnnualBudgetByAcademyNameContaining", startResult, maxRows, academyName);
		return new LinkedHashSet<ProjectAnnualBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByAppendixContaining
	 *
	 */
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByAppendixContaining(String appendix) throws DataAccessException {

		return findProjectAnnualBudgetByAppendixContaining(appendix, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByAppendixContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByAppendixContaining(String appendix, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAnnualBudgetByAppendixContaining", startResult, maxRows, appendix);
		return new LinkedHashSet<ProjectAnnualBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByPrimaryKey
	 *
	 */
	@Transactional
	public ProjectAnnualBudget findProjectAnnualBudgetByPrimaryKey(Integer id) throws DataAccessException {

		return findProjectAnnualBudgetByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByPrimaryKey
	 *
	 */

	@Transactional
	public ProjectAnnualBudget findProjectAnnualBudgetByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectAnnualBudgetByPrimaryKey", startResult, maxRows, id);
			return (ProjectAnnualBudget) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectProceedContaining
	 *
	 */
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectProceedContaining(String projectProceed) throws DataAccessException {

		return findProjectAnnualBudgetByProjectProceedContaining(projectProceed, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectProceedContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectProceedContaining(String projectProceed, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAnnualBudgetByProjectProceedContaining", startResult, maxRows, projectProceed);
		return new LinkedHashSet<ProjectAnnualBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectProceed
	 *
	 */
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectProceed(String projectProceed) throws DataAccessException {

		return findProjectAnnualBudgetByProjectProceed(projectProceed, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectProceed
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectProceed(String projectProceed, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAnnualBudgetByProjectProceed", startResult, maxRows, projectProceed);
		return new LinkedHashSet<ProjectAnnualBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectFundsContaining
	 *
	 */
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectFundsContaining(String projectFunds) throws DataAccessException {

		return findProjectAnnualBudgetByProjectFundsContaining(projectFunds, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectFundsContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectFundsContaining(String projectFunds, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAnnualBudgetByProjectFundsContaining", startResult, maxRows, projectFunds);
		return new LinkedHashSet<ProjectAnnualBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByStatusContaining
	 *
	 */
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByStatusContaining(String status) throws DataAccessException {

		return findProjectAnnualBudgetByStatusContaining(status, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByStatusContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByStatusContaining(String status, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAnnualBudgetByStatusContaining", startResult, maxRows, status);
		return new LinkedHashSet<ProjectAnnualBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByConstructYear
	 *
	 */
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByConstructYear(String constructYear) throws DataAccessException {

		return findProjectAnnualBudgetByConstructYear(constructYear, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByConstructYear
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByConstructYear(String constructYear, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAnnualBudgetByConstructYear", startResult, maxRows, constructYear);
		return new LinkedHashSet<ProjectAnnualBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByAppendix
	 *
	 */
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByAppendix(String appendix) throws DataAccessException {

		return findProjectAnnualBudgetByAppendix(appendix, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByAppendix
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByAppendix(String appendix, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAnnualBudgetByAppendix", startResult, maxRows, appendix);
		return new LinkedHashSet<ProjectAnnualBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByManagerContaining
	 *
	 */
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByManagerContaining(String manager) throws DataAccessException {

		return findProjectAnnualBudgetByManagerContaining(manager, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByManagerContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByManagerContaining(String manager, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAnnualBudgetByManagerContaining", startResult, maxRows, manager);
		return new LinkedHashSet<ProjectAnnualBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByManager
	 *
	 */
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByManager(String manager) throws DataAccessException {

		return findProjectAnnualBudgetByManager(manager, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByManager
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByManager(String manager, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAnnualBudgetByManager", startResult, maxRows, manager);
		return new LinkedHashSet<ProjectAnnualBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectName
	 *
	 */
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectName(String projectName) throws DataAccessException {

		return findProjectAnnualBudgetByProjectName(projectName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectName(String projectName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAnnualBudgetByProjectName", startResult, maxRows, projectName);
		return new LinkedHashSet<ProjectAnnualBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectSource
	 *
	 */
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectSource(String projectSource) throws DataAccessException {

		return findProjectAnnualBudgetByProjectSource(projectSource, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectSource
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectSource(String projectSource, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAnnualBudgetByProjectSource", startResult, maxRows, projectSource);
		return new LinkedHashSet<ProjectAnnualBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllProjectAnnualBudgets
	 *
	 */
	@Transactional
	public Set<ProjectAnnualBudget> findAllProjectAnnualBudgets() throws DataAccessException {

		return findAllProjectAnnualBudgets(-1, -1);
	}

	/**
	 * JPQL Query - findAllProjectAnnualBudgets
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAnnualBudget> findAllProjectAnnualBudgets(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllProjectAnnualBudgets", startResult, maxRows);
		return new LinkedHashSet<ProjectAnnualBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByConstructYearContaining
	 *
	 */
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByConstructYearContaining(String constructYear) throws DataAccessException {

		return findProjectAnnualBudgetByConstructYearContaining(constructYear, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAnnualBudgetByConstructYearContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByConstructYearContaining(String constructYear, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAnnualBudgetByConstructYearContaining", startResult, maxRows, constructYear);
		return new LinkedHashSet<ProjectAnnualBudget>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(ProjectAnnualBudget entity) {
		return true;
	}
}
