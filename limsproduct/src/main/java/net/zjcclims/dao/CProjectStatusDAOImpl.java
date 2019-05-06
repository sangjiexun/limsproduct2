package net.zjcclims.dao;


import net.zjcclims.domain.CProjectStatus;
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
 * DAO to manage CProjectStatus entities.
 * 
 */
@Repository("CProjectStatusDAO")
@Transactional
public class CProjectStatusDAOImpl extends AbstractJpaDao<CProjectStatus>
		implements CProjectStatusDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { CProjectStatus.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulims
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new CProjectStatusDAOImpl
	 *
	 */
	public CProjectStatusDAOImpl() {
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
	 * JPQL Query - findCProjectStatusByPrimaryKey
	 *
	 */
	@Transactional
	public CProjectStatus findCProjectStatusByPrimaryKey(Integer id) throws DataAccessException {

		return findCProjectStatusByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findCProjectStatusByPrimaryKey
	 *
	 */

	@Transactional
	public CProjectStatus findCProjectStatusByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findCProjectStatusByPrimaryKey", startResult, maxRows, id);
			return (CProjectStatus) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findCProjectStatusByName
	 *
	 */
	@Transactional
	public Set<CProjectStatus> findCProjectStatusByName(String name) throws DataAccessException {

		return findCProjectStatusByName(name, -1, -1);
	}

	/**
	 * JPQL Query - findCProjectStatusByName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CProjectStatus> findCProjectStatusByName(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findCProjectStatusByName", startResult, maxRows, name);
		return new LinkedHashSet<CProjectStatus>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllCProjectStatuss
	 *
	 */
	@Transactional
	public Set<CProjectStatus> findAllCProjectStatuss() throws DataAccessException {

		return findAllCProjectStatuss(-1, -1);
	}

	/**
	 * JPQL Query - findAllCProjectStatuss
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CProjectStatus> findAllCProjectStatuss(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllCProjectStatuss", startResult, maxRows);
		return new LinkedHashSet<CProjectStatus>(query.getResultList());
	}

	/**
	 * JPQL Query - findCProjectStatusById
	 *
	 */
	@Transactional
	public CProjectStatus findCProjectStatusById(Integer id) throws DataAccessException {

		return findCProjectStatusById(id, -1, -1);
	}

	/**
	 * JPQL Query - findCProjectStatusById
	 *
	 */

	@Transactional
	public CProjectStatus findCProjectStatusById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findCProjectStatusById", startResult, maxRows, id);
			return (CProjectStatus) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findCProjectStatusByNameContaining
	 *
	 */
	@Transactional
	public Set<CProjectStatus> findCProjectStatusByNameContaining(String name) throws DataAccessException {

		return findCProjectStatusByNameContaining(name, -1, -1);
	}

	/**
	 * JPQL Query - findCProjectStatusByNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CProjectStatus> findCProjectStatusByNameContaining(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findCProjectStatusByNameContaining", startResult, maxRows, name);
		return new LinkedHashSet<CProjectStatus>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(CProjectStatus entity) {
		return true;
	}
}
