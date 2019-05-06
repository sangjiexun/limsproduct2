package net.zjcclims.dao;


import net.zjcclims.domain.ProjectAcceptFeeList;
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
 * DAO to manage ProjectAcceptFeeList entities.
 * 
 */
@Repository("ProjectAcceptFeeListDAO")
@Transactional
public class ProjectAcceptFeeListDAOImpl extends AbstractJpaDao<ProjectAcceptFeeList>
		implements ProjectAcceptFeeListDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ProjectAcceptFeeList.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ProjectAcceptFeeListDAOImpl
	 *
	 */
	public ProjectAcceptFeeListDAOImpl() {
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
	 * JPQL Query - findProjectAcceptFeeListByAmount
	 *
	 */
	@Transactional
	public Set<ProjectAcceptFeeList> findProjectAcceptFeeListByAmount(java.math.BigDecimal amount) throws DataAccessException {

		return findProjectAcceptFeeListByAmount(amount, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptFeeListByAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptFeeList> findProjectAcceptFeeListByAmount(java.math.BigDecimal amount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptFeeListByAmount", startResult, maxRows, amount);
		return new LinkedHashSet<ProjectAcceptFeeList>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptFeeListByBudgetaryItem
	 *
	 */
	@Transactional
	public Set<ProjectAcceptFeeList> findProjectAcceptFeeListByBudgetaryItem(String budgetaryItem) throws DataAccessException {

		return findProjectAcceptFeeListByBudgetaryItem(budgetaryItem, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptFeeListByBudgetaryItem
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptFeeList> findProjectAcceptFeeListByBudgetaryItem(String budgetaryItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptFeeListByBudgetaryItem", startResult, maxRows, budgetaryItem);
		return new LinkedHashSet<ProjectAcceptFeeList>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllProjectAcceptFeeLists
	 *
	 */
	@Transactional
	public Set<ProjectAcceptFeeList> findAllProjectAcceptFeeLists() throws DataAccessException {

		return findAllProjectAcceptFeeLists(-1, -1);
	}

	/**
	 * JPQL Query - findAllProjectAcceptFeeLists
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptFeeList> findAllProjectAcceptFeeLists(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllProjectAcceptFeeLists", startResult, maxRows);
		return new LinkedHashSet<ProjectAcceptFeeList>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptFeeListByOtherFundsSource
	 *
	 */
	@Transactional
	public Set<ProjectAcceptFeeList> findProjectAcceptFeeListByOtherFundsSource(java.math.BigDecimal otherFundsSource) throws DataAccessException {

		return findProjectAcceptFeeListByOtherFundsSource(otherFundsSource, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptFeeListByOtherFundsSource
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptFeeList> findProjectAcceptFeeListByOtherFundsSource(java.math.BigDecimal otherFundsSource, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptFeeListByOtherFundsSource", startResult, maxRows, otherFundsSource);
		return new LinkedHashSet<ProjectAcceptFeeList>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptFeeListByPrimaryKey
	 *
	 */
	@Transactional
	public ProjectAcceptFeeList findProjectAcceptFeeListByPrimaryKey(Integer id) throws DataAccessException {

		return findProjectAcceptFeeListByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptFeeListByPrimaryKey
	 *
	 */

	@Transactional
	public ProjectAcceptFeeList findProjectAcceptFeeListByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectAcceptFeeListByPrimaryKey", startResult, maxRows, id);
			return (ProjectAcceptFeeList) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectAcceptFeeListById
	 *
	 */
	@Transactional
	public ProjectAcceptFeeList findProjectAcceptFeeListById(Integer id) throws DataAccessException {

		return findProjectAcceptFeeListById(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptFeeListById
	 *
	 */

	@Transactional
	public ProjectAcceptFeeList findProjectAcceptFeeListById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectAcceptFeeListById", startResult, maxRows, id);
			return (ProjectAcceptFeeList) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectAcceptFeeListByBudgetaryItemContaining
	 *
	 */
	@Transactional
	public Set<ProjectAcceptFeeList> findProjectAcceptFeeListByBudgetaryItemContaining(String budgetaryItem) throws DataAccessException {

		return findProjectAcceptFeeListByBudgetaryItemContaining(budgetaryItem, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptFeeListByBudgetaryItemContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptFeeList> findProjectAcceptFeeListByBudgetaryItemContaining(String budgetaryItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptFeeListByBudgetaryItemContaining", startResult, maxRows, budgetaryItem);
		return new LinkedHashSet<ProjectAcceptFeeList>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(ProjectAcceptFeeList entity) {
		return true;
	}
}
