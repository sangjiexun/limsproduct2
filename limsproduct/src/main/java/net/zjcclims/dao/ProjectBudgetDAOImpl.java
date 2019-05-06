package net.zjcclims.dao;


import net.zjcclims.domain.ProjectBudget;
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
 * DAO to manage ProjectBudget entities.
 * 
 */
@Repository("ProjectBudgetDAO")
@Transactional
public class ProjectBudgetDAOImpl extends AbstractJpaDao<ProjectBudget>
		implements ProjectBudgetDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ProjectBudget.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulims
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ProjectBudgetDAOImpl
	 *
	 */
	public ProjectBudgetDAOImpl() {
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
	 * JPQL Query - findProjectBudgetById
	 *
	 */
	@Transactional
	public ProjectBudget findProjectBudgetById(Integer id) throws DataAccessException {

		return findProjectBudgetById(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectBudgetById
	 *
	 */

	@Transactional
	public ProjectBudget findProjectBudgetById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectBudgetById", startResult, maxRows, id);
			return (ProjectBudget) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllProjectBudgets
	 *
	 */
	@Transactional
	public Set<ProjectBudget> findAllProjectBudgets() throws DataAccessException {

		return findAllProjectBudgets(-1, -1);
	}

	/**
	 * JPQL Query - findAllProjectBudgets
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectBudget> findAllProjectBudgets(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllProjectBudgets", startResult, maxRows);
		return new LinkedHashSet<ProjectBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectBudgetByPrimaryKey
	 *
	 */
	@Transactional
	public ProjectBudget findProjectBudgetByPrimaryKey(Integer id) throws DataAccessException {

		return findProjectBudgetByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectBudgetByPrimaryKey
	 *
	 */

	@Transactional
	public ProjectBudget findProjectBudgetByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectBudgetByPrimaryKey", startResult, maxRows, id);
			return (ProjectBudget) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectBudgetByUseFee
	 *
	 */
	@Transactional
	public Set<ProjectBudget> findProjectBudgetByUseFee(java.math.BigDecimal useFee) throws DataAccessException {

		return findProjectBudgetByUseFee(useFee, -1, -1);
	}

	/**
	 * JPQL Query - findProjectBudgetByUseFee
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectBudget> findProjectBudgetByUseFee(java.math.BigDecimal useFee, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectBudgetByUseFee", startResult, maxRows, useFee);
		return new LinkedHashSet<ProjectBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectBudgetByBudgetFee
	 *
	 */
	@Transactional
	public Set<ProjectBudget> findProjectBudgetByBudgetFee(java.math.BigDecimal budgetFee) throws DataAccessException {

		return findProjectBudgetByBudgetFee(budgetFee, -1, -1);
	}

	/**
	 * JPQL Query - findProjectBudgetByBudgetFee
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectBudget> findProjectBudgetByBudgetFee(java.math.BigDecimal budgetFee, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectBudgetByBudgetFee", startResult, maxRows, budgetFee);
		return new LinkedHashSet<ProjectBudget>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(ProjectBudget entity) {
		return true;
	}
}
