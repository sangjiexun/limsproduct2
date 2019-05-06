package net.zjcclims.dao;


import net.zjcclims.domain.ProjectStartFeeList;
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
 * DAO to manage ProjectStartFeeList entities.
 * 
 */
@Repository("ProjectStartFeeListDAO")
@Transactional
public class ProjectStartFeeListDAOImpl extends AbstractJpaDao<ProjectStartFeeList>
		implements ProjectStartFeeListDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ProjectStartFeeList.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ProjectStartFeeListDAOImpl
	 *
	 */
	public ProjectStartFeeListDAOImpl() {
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
	 * JPQL Query - findProjectStartFeeListByAmount
	 *
	 */
	@Transactional
	public Set<ProjectStartFeeList> findProjectStartFeeListByAmount(java.math.BigDecimal amount) throws DataAccessException {

		return findProjectStartFeeListByAmount(amount, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartFeeListByAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartFeeList> findProjectStartFeeListByAmount(java.math.BigDecimal amount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartFeeListByAmount", startResult, maxRows, amount);
		return new LinkedHashSet<ProjectStartFeeList>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartFeeListByOtherFundsSource
	 *
	 */
	@Transactional
	public Set<ProjectStartFeeList> findProjectStartFeeListByOtherFundsSource(java.math.BigDecimal otherFundsSource) throws DataAccessException {

		return findProjectStartFeeListByOtherFundsSource(otherFundsSource, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartFeeListByOtherFundsSource
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartFeeList> findProjectStartFeeListByOtherFundsSource(java.math.BigDecimal otherFundsSource, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartFeeListByOtherFundsSource", startResult, maxRows, otherFundsSource);
		return new LinkedHashSet<ProjectStartFeeList>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllProjectStartFeeLists
	 *
	 */
	@Transactional
	public Set<ProjectStartFeeList> findAllProjectStartFeeLists() throws DataAccessException {

		return findAllProjectStartFeeLists(-1, -1);
	}

	/**
	 * JPQL Query - findAllProjectStartFeeLists
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartFeeList> findAllProjectStartFeeLists(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllProjectStartFeeLists", startResult, maxRows);
		return new LinkedHashSet<ProjectStartFeeList>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartFeeListByBudgetaryItem
	 *
	 */
	@Transactional
	public Set<ProjectStartFeeList> findProjectStartFeeListByBudgetaryItem(String budgetaryItem) throws DataAccessException {

		return findProjectStartFeeListByBudgetaryItem(budgetaryItem, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartFeeListByBudgetaryItem
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartFeeList> findProjectStartFeeListByBudgetaryItem(String budgetaryItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartFeeListByBudgetaryItem", startResult, maxRows, budgetaryItem);
		return new LinkedHashSet<ProjectStartFeeList>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartFeeListByBudgetaryItemContaining
	 *
	 */
	@Transactional
	public Set<ProjectStartFeeList> findProjectStartFeeListByBudgetaryItemContaining(String budgetaryItem) throws DataAccessException {

		return findProjectStartFeeListByBudgetaryItemContaining(budgetaryItem, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartFeeListByBudgetaryItemContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartFeeList> findProjectStartFeeListByBudgetaryItemContaining(String budgetaryItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartFeeListByBudgetaryItemContaining", startResult, maxRows, budgetaryItem);
		return new LinkedHashSet<ProjectStartFeeList>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartFeeListByPrimaryKey
	 *
	 */
	@Transactional
	public ProjectStartFeeList findProjectStartFeeListByPrimaryKey(Integer id) throws DataAccessException {

		return findProjectStartFeeListByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartFeeListByPrimaryKey
	 *
	 */

	@Transactional
	public ProjectStartFeeList findProjectStartFeeListByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectStartFeeListByPrimaryKey", startResult, maxRows, id);
			return (ProjectStartFeeList) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectStartFeeListById
	 *
	 */
	@Transactional
	public ProjectStartFeeList findProjectStartFeeListById(Integer id) throws DataAccessException {

		return findProjectStartFeeListById(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartFeeListById
	 *
	 */

	@Transactional
	public ProjectStartFeeList findProjectStartFeeListById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectStartFeeListById", startResult, maxRows, id);
			return (ProjectStartFeeList) query.getSingleResult();
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
	public boolean canBeMerged(ProjectStartFeeList entity) {
		return true;
	}
}
