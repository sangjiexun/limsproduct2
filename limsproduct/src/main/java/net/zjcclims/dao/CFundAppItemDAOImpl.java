package net.zjcclims.dao;


import net.zjcclims.domain.CFundAppItem;
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
 * DAO to manage CFundAppItem entities.
 * 
 */
@Repository("CFundAppItemDAO")
@Transactional
public class CFundAppItemDAOImpl extends AbstractJpaDao<CFundAppItem> implements
		CFundAppItemDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { CFundAppItem.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulims
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new CFundAppItemDAOImpl
	 *
	 */
	public CFundAppItemDAOImpl() {
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
	 * JPQL Query - findCFundAppItemByName
	 *
	 */
	@Transactional
	public Set<CFundAppItem> findCFundAppItemByName(String name) throws DataAccessException {

		return findCFundAppItemByName(name, -1, -1);
	}

	/**
	 * JPQL Query - findCFundAppItemByName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CFundAppItem> findCFundAppItemByName(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findCFundAppItemByName", startResult, maxRows, name);
		return new LinkedHashSet<CFundAppItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findCFundAppItemByPrimaryKey
	 *
	 */
	@Transactional
	public CFundAppItem findCFundAppItemByPrimaryKey(Integer id) throws DataAccessException {

		return findCFundAppItemByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findCFundAppItemByPrimaryKey
	 *
	 */

	@Transactional
	public CFundAppItem findCFundAppItemByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findCFundAppItemByPrimaryKey", startResult, maxRows, id);
			return (CFundAppItem) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllCFundAppItems
	 *
	 */
	@Transactional
	public Set<CFundAppItem> findAllCFundAppItems() throws DataAccessException {

		return findAllCFundAppItems(-1, -1);
	}

	/**
	 * JPQL Query - findAllCFundAppItems
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CFundAppItem> findAllCFundAppItems(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllCFundAppItems", startResult, maxRows);
		return new LinkedHashSet<CFundAppItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findCFundAppItemByNameContaining
	 *
	 */
	@Transactional
	public Set<CFundAppItem> findCFundAppItemByNameContaining(String name) throws DataAccessException {

		return findCFundAppItemByNameContaining(name, -1, -1);
	}

	/**
	 * JPQL Query - findCFundAppItemByNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CFundAppItem> findCFundAppItemByNameContaining(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findCFundAppItemByNameContaining", startResult, maxRows, name);
		return new LinkedHashSet<CFundAppItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findCFundAppItemById
	 *
	 */
	@Transactional
	public CFundAppItem findCFundAppItemById(Integer id) throws DataAccessException {

		return findCFundAppItemById(id, -1, -1);
	}

	/**
	 * JPQL Query - findCFundAppItemById
	 *
	 */

	@Transactional
	public CFundAppItem findCFundAppItemById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findCFundAppItemById", startResult, maxRows, id);
			return (CFundAppItem) query.getSingleResult();
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
	public boolean canBeMerged(CFundAppItem entity) {
		return true;
	}
}
