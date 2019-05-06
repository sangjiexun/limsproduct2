package net.zjcclims.dao;

import net.zjcclims.domain.SystemMenu;
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
 * DAO to manage SystemMenu entities.
 * 
 */
@Repository("SystemMenuDAO")
@Transactional
public class SystemMenuDAOImpl extends AbstractJpaDao<SystemMenu> implements SystemMenuDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { SystemMenu.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new SystemMenuDAOImpl
	 *
	 */
	public SystemMenuDAOImpl() {
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
	 * JPQL Query - findSystemMenuByProjectNameContaining
	 *
	 */
	@Transactional
	public Set<SystemMenu> findSystemMenuByProjectNameContaining(String projectName) throws DataAccessException {

		return findSystemMenuByProjectNameContaining(projectName, -1, -1);
	}

	/**
	 * JPQL Query - findSystemMenuByProjectNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SystemMenu> findSystemMenuByProjectNameContaining(String projectName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSystemMenuByProjectNameContaining", startResult, maxRows, projectName);
		return new LinkedHashSet<SystemMenu>(query.getResultList());
	}

	/**
	 * JPQL Query - findSystemMenuById
	 *
	 */
	@Transactional
	public SystemMenu findSystemMenuById(Integer id) throws DataAccessException {

		return findSystemMenuById(id, -1, -1);
	}

	/**
	 * JPQL Query - findSystemMenuById
	 *
	 */

	@Transactional
	public SystemMenu findSystemMenuById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSystemMenuById", startResult, maxRows, id);
			return (SystemMenu) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findSystemMenuByProjectName
	 *
	 */
	@Transactional
	public Set<SystemMenu> findSystemMenuByProjectName(String projectName) throws DataAccessException {

		return findSystemMenuByProjectName(projectName, -1, -1);
	}

	/**
	 * JPQL Query - findSystemMenuByProjectName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SystemMenu> findSystemMenuByProjectName(String projectName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSystemMenuByProjectName", startResult, maxRows, projectName);
		return new LinkedHashSet<SystemMenu>(query.getResultList());
	}

	/**
	 * JPQL Query - findSystemMenuByPrimaryKey
	 *
	 */
	@Transactional
	public SystemMenu findSystemMenuByPrimaryKey(Integer id) throws DataAccessException {

		return findSystemMenuByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findSystemMenuByPrimaryKey
	 *
	 */

	@Transactional
	public SystemMenu findSystemMenuByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSystemMenuByPrimaryKey", startResult, maxRows, id);
			return (SystemMenu) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findSystemMenuByName
	 *
	 */
	@Transactional
	public Set<SystemMenu> findSystemMenuByName(String name) throws DataAccessException {

		return findSystemMenuByName(name, -1, -1);
	}

	/**
	 * JPQL Query - findSystemMenuByName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SystemMenu> findSystemMenuByName(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSystemMenuByName", startResult, maxRows, name);
		return new LinkedHashSet<SystemMenu>(query.getResultList());
	}

	/**
	 * JPQL Query - findSystemMenuByNameContaining
	 *
	 */
	@Transactional
	public Set<SystemMenu> findSystemMenuByNameContaining(String name) throws DataAccessException {

		return findSystemMenuByNameContaining(name, -1, -1);
	}

	/**
	 * JPQL Query - findSystemMenuByNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SystemMenu> findSystemMenuByNameContaining(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSystemMenuByNameContaining", startResult, maxRows, name);
		return new LinkedHashSet<SystemMenu>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllSystemMenus
	 *
	 */
	@Transactional
	public Set<SystemMenu> findAllSystemMenus() throws DataAccessException {

		return findAllSystemMenus(-1, -1);
	}

	/**
	 * JPQL Query - findAllSystemMenus
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SystemMenu> findAllSystemMenus(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllSystemMenus", startResult, maxRows);
		return new LinkedHashSet<SystemMenu>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(SystemMenu entity) {
		return true;
	}
}
