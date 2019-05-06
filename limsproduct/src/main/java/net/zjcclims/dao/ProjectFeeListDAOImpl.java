package net.zjcclims.dao;


import net.zjcclims.domain.ProjectFeeList;
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
 * DAO to manage ProjectFeeList entities.
 * 
 */
@Repository("ProjectFeeListDAO")
@Transactional
public class ProjectFeeListDAOImpl extends AbstractJpaDao<ProjectFeeList>
		implements ProjectFeeListDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ProjectFeeList.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ProjectFeeListDAOImpl
	 *
	 */
	public ProjectFeeListDAOImpl() {
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
	 * JPQL Query - findProjectFeeListByOtherFundsSource
	 *
	 */
	@Transactional
	public Set<ProjectFeeList> findProjectFeeListByOtherFundsSource(java.math.BigDecimal otherFundsSource) throws DataAccessException {

		return findProjectFeeListByOtherFundsSource(otherFundsSource, -1, -1);
	}

	/**
	 * JPQL Query - findProjectFeeListByOtherFundsSource
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectFeeList> findProjectFeeListByOtherFundsSource(java.math.BigDecimal otherFundsSource, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectFeeListByOtherFundsSource", startResult, maxRows, otherFundsSource);
		return new LinkedHashSet<ProjectFeeList>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllProjectFeeLists
	 *
	 */
	@Transactional
	public Set<ProjectFeeList> findAllProjectFeeLists() throws DataAccessException {

		return findAllProjectFeeLists(-1, -1);
	}

	/**
	 * JPQL Query - findAllProjectFeeLists
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectFeeList> findAllProjectFeeLists(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllProjectFeeLists", startResult, maxRows);
		return new LinkedHashSet<ProjectFeeList>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectFeeListByPrimaryKey
	 *
	 */
	@Transactional
	public ProjectFeeList findProjectFeeListByPrimaryKey(Integer id) throws DataAccessException {

		return findProjectFeeListByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectFeeListByPrimaryKey
	 *
	 */

	@Transactional
	public ProjectFeeList findProjectFeeListByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectFeeListByPrimaryKey", startResult, maxRows, id);
			return (ProjectFeeList) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectFeeListById
	 *
	 */
	@Transactional
	public ProjectFeeList findProjectFeeListById(Integer id) throws DataAccessException {

		return findProjectFeeListById(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectFeeListById
	 *
	 */

	@Transactional
	public ProjectFeeList findProjectFeeListById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectFeeListById", startResult, maxRows, id);
			return (ProjectFeeList) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectFeeListByBudgetaryItemContaining
	 *
	 */
	@Transactional
	public Set<ProjectFeeList> findProjectFeeListByBudgetaryItemContaining(String budgetaryItem) throws DataAccessException {

		return findProjectFeeListByBudgetaryItemContaining(budgetaryItem, -1, -1);
	}

	/**
	 * JPQL Query - findProjectFeeListByBudgetaryItemContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectFeeList> findProjectFeeListByBudgetaryItemContaining(String budgetaryItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectFeeListByBudgetaryItemContaining", startResult, maxRows, budgetaryItem);
		return new LinkedHashSet<ProjectFeeList>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectFeeListByAmount
	 *
	 */
	@Transactional
	public Set<ProjectFeeList> findProjectFeeListByAmount(java.math.BigDecimal amount) throws DataAccessException {

		return findProjectFeeListByAmount(amount, -1, -1);
	}

	/**
	 * JPQL Query - findProjectFeeListByAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectFeeList> findProjectFeeListByAmount(java.math.BigDecimal amount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectFeeListByAmount", startResult, maxRows, amount);
		return new LinkedHashSet<ProjectFeeList>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectFeeListByBudgetaryItem
	 *
	 */
	@Transactional
	public Set<ProjectFeeList> findProjectFeeListByBudgetaryItem(String budgetaryItem) throws DataAccessException {

		return findProjectFeeListByBudgetaryItem(budgetaryItem, -1, -1);
	}

	/**
	 * JPQL Query - findProjectFeeListByBudgetaryItem
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectFeeList> findProjectFeeListByBudgetaryItem(String budgetaryItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectFeeListByBudgetaryItem", startResult, maxRows, budgetaryItem);
		return new LinkedHashSet<ProjectFeeList>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(ProjectFeeList entity) {
		return true;
	}
}
