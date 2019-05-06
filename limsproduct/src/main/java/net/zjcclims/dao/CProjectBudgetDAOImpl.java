package net.zjcclims.dao;


import net.zjcclims.domain.CProjectBudget;
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
 * DAO to manage CProjectBudget entities.
 * 
 */
@Repository("CProjectBudgetDAO")
@Transactional
public class CProjectBudgetDAOImpl extends AbstractJpaDao<CProjectBudget>
		implements CProjectBudgetDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { CProjectBudget.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulims
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new CProjectBudgetDAOImpl
	 *
	 */
	public CProjectBudgetDAOImpl() {
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
	 * JPQL Query - findCProjectBudgetByNameContaining
	 *
	 */
	@Transactional
	public Set<CProjectBudget> findCProjectBudgetByNameContaining(String name) throws DataAccessException {

		return findCProjectBudgetByNameContaining(name, -1, -1);
	}

	/**
	 * JPQL Query - findCProjectBudgetByNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CProjectBudget> findCProjectBudgetByNameContaining(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findCProjectBudgetByNameContaining", startResult, maxRows, name);
		return new LinkedHashSet<CProjectBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllCProjectBudgets
	 *
	 */
	@Transactional
	public Set<CProjectBudget> findAllCProjectBudgets() throws DataAccessException {

		return findAllCProjectBudgets(-1, -1);
	}

	/**
	 * JPQL Query - findAllCProjectBudgets
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CProjectBudget> findAllCProjectBudgets(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllCProjectBudgets", startResult, maxRows);
		return new LinkedHashSet<CProjectBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findCProjectBudgetById
	 *
	 */
	@Transactional
	public CProjectBudget findCProjectBudgetById(Integer id) throws DataAccessException {

		return findCProjectBudgetById(id, -1, -1);
	}

	/**
	 * JPQL Query - findCProjectBudgetById
	 *
	 */

	@Transactional
	public CProjectBudget findCProjectBudgetById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findCProjectBudgetById", startResult, maxRows, id);
			return (CProjectBudget) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findCProjectBudgetByName
	 *
	 */
	@Transactional
	public Set<CProjectBudget> findCProjectBudgetByName(String name) throws DataAccessException {

		return findCProjectBudgetByName(name, -1, -1);
	}

	/**
	 * JPQL Query - findCProjectBudgetByName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CProjectBudget> findCProjectBudgetByName(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findCProjectBudgetByName", startResult, maxRows, name);
		return new LinkedHashSet<CProjectBudget>(query.getResultList());
	}

	/**
	 * JPQL Query - findCProjectBudgetByPrimaryKey
	 *
	 */
	@Transactional
	public CProjectBudget findCProjectBudgetByPrimaryKey(Integer id) throws DataAccessException {

		return findCProjectBudgetByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findCProjectBudgetByPrimaryKey
	 *
	 */

	@Transactional
	public CProjectBudget findCProjectBudgetByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findCProjectBudgetByPrimaryKey", startResult, maxRows, id);
			return (CProjectBudget) query.getSingleResult();
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
	public boolean canBeMerged(CProjectBudget entity) {
		return true;
	}
}
