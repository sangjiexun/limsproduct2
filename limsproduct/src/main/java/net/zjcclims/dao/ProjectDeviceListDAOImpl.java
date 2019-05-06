package net.zjcclims.dao;


import net.zjcclims.domain.ProjectDeviceList;
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
 * DAO to manage ProjectDeviceList entities.
 * 
 */
@Repository("ProjectDeviceListDAO")
@Transactional
public class ProjectDeviceListDAOImpl extends AbstractJpaDao<ProjectDeviceList>
		implements ProjectDeviceListDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ProjectDeviceList.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulims
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ProjectDeviceListDAOImpl
	 *
	 */
	public ProjectDeviceListDAOImpl() {
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
	 * JPQL Query - findAllProjectDeviceLists
	 *
	 */
	@Transactional
	public Set<ProjectDeviceList> findAllProjectDeviceLists() throws DataAccessException {

		return findAllProjectDeviceLists(-1, -1);
	}

	/**
	 * JPQL Query - findAllProjectDeviceLists
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectDeviceList> findAllProjectDeviceLists(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllProjectDeviceLists", startResult, maxRows);
		return new LinkedHashSet<ProjectDeviceList>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectDeviceListByAmount
	 *
	 */
	@Transactional
	public Set<ProjectDeviceList> findProjectDeviceListByAmount(java.math.BigDecimal amount) throws DataAccessException {

		return findProjectDeviceListByAmount(amount, -1, -1);
	}

	/**
	 * JPQL Query - findProjectDeviceListByAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectDeviceList> findProjectDeviceListByAmount(java.math.BigDecimal amount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectDeviceListByAmount", startResult, maxRows, amount);
		return new LinkedHashSet<ProjectDeviceList>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectDeviceListByOtherFundsSource
	 *
	 */
	@Transactional
	public Set<ProjectDeviceList> findProjectDeviceListByOtherFundsSource(String otherFundsSource) throws DataAccessException {

		return findProjectDeviceListByOtherFundsSource(otherFundsSource, -1, -1);
	}

	/**
	 * JPQL Query - findProjectDeviceListByOtherFundsSource
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectDeviceList> findProjectDeviceListByOtherFundsSource(String otherFundsSource, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectDeviceListByOtherFundsSource", startResult, maxRows, otherFundsSource);
		return new LinkedHashSet<ProjectDeviceList>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectDeviceListById
	 *
	 */
	@Transactional
	public ProjectDeviceList findProjectDeviceListById(Integer id) throws DataAccessException {

		return findProjectDeviceListById(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectDeviceListById
	 *
	 */

	@Transactional
	public ProjectDeviceList findProjectDeviceListById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectDeviceListById", startResult, maxRows, id);
			return (ProjectDeviceList) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectDeviceListByBudgetaryItemContaining
	 *
	 */
	@Transactional
	public Set<ProjectDeviceList> findProjectDeviceListByBudgetaryItemContaining(String budgetaryItem) throws DataAccessException {

		return findProjectDeviceListByBudgetaryItemContaining(budgetaryItem, -1, -1);
	}

	/**
	 * JPQL Query - findProjectDeviceListByBudgetaryItemContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectDeviceList> findProjectDeviceListByBudgetaryItemContaining(String budgetaryItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectDeviceListByBudgetaryItemContaining", startResult, maxRows, budgetaryItem);
		return new LinkedHashSet<ProjectDeviceList>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectDeviceListByPrimaryKey
	 *
	 */
	@Transactional
	public ProjectDeviceList findProjectDeviceListByPrimaryKey(Integer id) throws DataAccessException {

		return findProjectDeviceListByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectDeviceListByPrimaryKey
	 *
	 */

	@Transactional
	public ProjectDeviceList findProjectDeviceListByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectDeviceListByPrimaryKey", startResult, maxRows, id);
			return (ProjectDeviceList) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectDeviceListByOtherFundsSourceContaining
	 *
	 */
	@Transactional
	public Set<ProjectDeviceList> findProjectDeviceListByOtherFundsSourceContaining(String otherFundsSource) throws DataAccessException {

		return findProjectDeviceListByOtherFundsSourceContaining(otherFundsSource, -1, -1);
	}

	/**
	 * JPQL Query - findProjectDeviceListByOtherFundsSourceContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectDeviceList> findProjectDeviceListByOtherFundsSourceContaining(String otherFundsSource, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectDeviceListByOtherFundsSourceContaining", startResult, maxRows, otherFundsSource);
		return new LinkedHashSet<ProjectDeviceList>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectDeviceListByBudgetaryItem
	 *
	 */
	@Transactional
	public Set<ProjectDeviceList> findProjectDeviceListByBudgetaryItem(String budgetaryItem) throws DataAccessException {

		return findProjectDeviceListByBudgetaryItem(budgetaryItem, -1, -1);
	}

	/**
	 * JPQL Query - findProjectDeviceListByBudgetaryItem
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectDeviceList> findProjectDeviceListByBudgetaryItem(String budgetaryItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectDeviceListByBudgetaryItem", startResult, maxRows, budgetaryItem);
		return new LinkedHashSet<ProjectDeviceList>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(ProjectDeviceList entity) {
		return true;
	}
}
