package net.zjcclims.dao;

import net.zjcclims.domain.ChoseDissertationDirection;
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
 * DAO to manage ChoseDissertationDirection entities.
 * 
 */
@Repository("ChoseDissertationDirectionDAO")
@Transactional
public class ChoseDissertationDirectionDAOImpl extends AbstractJpaDao<ChoseDissertationDirection>
		implements ChoseDissertationDirectionDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ChoseDissertationDirection.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ChoseDissertationDirectionDAOImpl
	 *
	 */
	public ChoseDissertationDirectionDAOImpl() {
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
	 * JPQL Query - findChoseDissertationDirectionByName
	 *
	 */
	@Transactional
	public Set<ChoseDissertationDirection> findChoseDissertationDirectionByName(String name) throws DataAccessException {

		return findChoseDissertationDirectionByName(name, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDissertationDirectionByName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDissertationDirection> findChoseDissertationDirectionByName(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDissertationDirectionByName", startResult, maxRows, name);
		return new LinkedHashSet<ChoseDissertationDirection>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDissertationDirectionByNameContaining
	 *
	 */
	@Transactional
	public Set<ChoseDissertationDirection> findChoseDissertationDirectionByNameContaining(String name) throws DataAccessException {

		return findChoseDissertationDirectionByNameContaining(name, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDissertationDirectionByNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDissertationDirection> findChoseDissertationDirectionByNameContaining(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDissertationDirectionByNameContaining", startResult, maxRows, name);
		return new LinkedHashSet<ChoseDissertationDirection>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDissertationDirectionByPrimaryKey
	 *
	 */
	@Transactional
	public ChoseDissertationDirection findChoseDissertationDirectionByPrimaryKey(Integer id) throws DataAccessException {

		return findChoseDissertationDirectionByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDissertationDirectionByPrimaryKey
	 *
	 */

	@Transactional
	public ChoseDissertationDirection findChoseDissertationDirectionByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findChoseDissertationDirectionByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.ChoseDissertationDirection) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findChoseDissertationDirectionById
	 *
	 */
	@Transactional
	public ChoseDissertationDirection findChoseDissertationDirectionById(Integer id) throws DataAccessException {

		return findChoseDissertationDirectionById(id, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDissertationDirectionById
	 *
	 */

	@Transactional
	public ChoseDissertationDirection findChoseDissertationDirectionById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findChoseDissertationDirectionById", startResult, maxRows, id);
			return (net.zjcclims.domain.ChoseDissertationDirection) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllChoseDissertationDirections
	 *
	 */
	@Transactional
	public Set<ChoseDissertationDirection> findAllChoseDissertationDirections() throws DataAccessException {

		return findAllChoseDissertationDirections(-1, -1);
	}

	/**
	 * JPQL Query - findAllChoseDissertationDirections
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDissertationDirection> findAllChoseDissertationDirections(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllChoseDissertationDirections", startResult, maxRows);
		return new LinkedHashSet<ChoseDissertationDirection>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(ChoseDissertationDirection entity) {
		return true;
	}
}
