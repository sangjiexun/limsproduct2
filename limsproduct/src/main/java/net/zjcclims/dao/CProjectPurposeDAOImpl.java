package net.zjcclims.dao;


import net.zjcclims.domain.CProjectPurpose;
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
 * DAO to manage CProjectPurpose entities.
 * 
 */
@Repository("CProjectPurposeDAO")
@Transactional
public class CProjectPurposeDAOImpl extends AbstractJpaDao<CProjectPurpose>
		implements CProjectPurposeDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { CProjectPurpose.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulims
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new CProjectPurposeDAOImpl
	 *
	 */
	public CProjectPurposeDAOImpl() {
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
	 * JPQL Query - findCProjectPurposeByName
	 *
	 */
	@Transactional
	public Set<CProjectPurpose> findCProjectPurposeByName(String name) throws DataAccessException {

		return findCProjectPurposeByName(name, -1, -1);
	}

	/**
	 * JPQL Query - findCProjectPurposeByName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CProjectPurpose> findCProjectPurposeByName(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findCProjectPurposeByName", startResult, maxRows, name);
		return new LinkedHashSet<CProjectPurpose>(query.getResultList());
	}

	/**
	 * JPQL Query - findCProjectPurposeByPrimaryKey
	 *
	 */
	@Transactional
	public CProjectPurpose findCProjectPurposeByPrimaryKey(Integer id) throws DataAccessException {

		return findCProjectPurposeByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findCProjectPurposeByPrimaryKey
	 *
	 */

	@Transactional
	public CProjectPurpose findCProjectPurposeByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findCProjectPurposeByPrimaryKey", startResult, maxRows, id);
			return (CProjectPurpose) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findCProjectPurposeById
	 *
	 */
	@Transactional
	public CProjectPurpose findCProjectPurposeById(Integer id) throws DataAccessException {

		return findCProjectPurposeById(id, -1, -1);
	}

	/**
	 * JPQL Query - findCProjectPurposeById
	 *
	 */

	@Transactional
	public CProjectPurpose findCProjectPurposeById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findCProjectPurposeById", startResult, maxRows, id);
			return (CProjectPurpose) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllCProjectPurposes
	 *
	 */
	@Transactional
	public Set<CProjectPurpose> findAllCProjectPurposes() throws DataAccessException {

		return findAllCProjectPurposes(-1, -1);
	}

	/**
	 * JPQL Query - findAllCProjectPurposes
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CProjectPurpose> findAllCProjectPurposes(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllCProjectPurposes", startResult, maxRows);
		return new LinkedHashSet<CProjectPurpose>(query.getResultList());
	}

	/**
	 * JPQL Query - findCProjectPurposeByNameContaining
	 *
	 */
	@Transactional
	public Set<CProjectPurpose> findCProjectPurposeByNameContaining(String name) throws DataAccessException {

		return findCProjectPurposeByNameContaining(name, -1, -1);
	}

	/**
	 * JPQL Query - findCProjectPurposeByNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CProjectPurpose> findCProjectPurposeByNameContaining(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findCProjectPurposeByNameContaining", startResult, maxRows, name);
		return new LinkedHashSet<CProjectPurpose>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(CProjectPurpose entity) {
		return true;
	}
}
