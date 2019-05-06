package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.PreRoomType;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage PreRoomType entities.
 * 
 */
@Repository("PreRoomTypeDAO")
@Transactional
public class PreRoomTypeDAOImpl extends AbstractJpaDao<PreRoomType> implements
		PreRoomTypeDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { PreRoomType.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new PreRoomTypeDAOImpl
	 *
	 */
	public PreRoomTypeDAOImpl() {
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
	 * JPQL Query - findPreRoomTypeByPrimaryKey
	 *
	 */
	@Transactional
	public PreRoomType findPreRoomTypeByPrimaryKey(Integer id) throws DataAccessException {

		return findPreRoomTypeByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findPreRoomTypeByPrimaryKey
	 *
	 */

	@Transactional
	public PreRoomType findPreRoomTypeByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findPreRoomTypeByPrimaryKey", startResult, maxRows, id);
			return (PreRoomType) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findPreRoomTypeByRoomType
	 *
	 */
	@Transactional
	public Set<PreRoomType> findPreRoomTypeByRoomType(String roomType) throws DataAccessException {

		return findPreRoomTypeByRoomType(roomType, -1, -1);
	}

	/**
	 * JPQL Query - findPreRoomTypeByRoomType
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreRoomType> findPreRoomTypeByRoomType(String roomType, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findPreRoomTypeByRoomType", startResult, maxRows, roomType);
		return new LinkedHashSet<PreRoomType>(query.getResultList());
	}

	/**
	 * JPQL Query - findPreRoomTypeById
	 *
	 */
	@Transactional
	public PreRoomType findPreRoomTypeById(Integer id) throws DataAccessException {

		return findPreRoomTypeById(id, -1, -1);
	}

	/**
	 * JPQL Query - findPreRoomTypeById
	 *
	 */

	@Transactional
	public PreRoomType findPreRoomTypeById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findPreRoomTypeById", startResult, maxRows, id);
			return (PreRoomType) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllPreRoomTypes
	 *
	 */
	@Transactional
	public Set<PreRoomType> findAllPreRoomTypes() throws DataAccessException {

		return findAllPreRoomTypes(-1, -1);
	}

	/**
	 * JPQL Query - findAllPreRoomTypes
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreRoomType> findAllPreRoomTypes(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllPreRoomTypes", startResult, maxRows);
		return new LinkedHashSet<PreRoomType>(query.getResultList());
	}

	/**
	 * JPQL Query - findPreRoomTypeByRoomTypeContaining
	 *
	 */
	@Transactional
	public Set<PreRoomType> findPreRoomTypeByRoomTypeContaining(String roomType) throws DataAccessException {

		return findPreRoomTypeByRoomTypeContaining(roomType, -1, -1);
	}

	/**
	 * JPQL Query - findPreRoomTypeByRoomTypeContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreRoomType> findPreRoomTypeByRoomTypeContaining(String roomType, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findPreRoomTypeByRoomTypeContaining", startResult, maxRows, roomType);
		return new LinkedHashSet<PreRoomType>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(PreRoomType entity) {
		return true;
	}
}
