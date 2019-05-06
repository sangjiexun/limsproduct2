package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.PreLabRoom;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage PreLabRoom entities.
 * 
 */
@Repository("PreLabRoomDAO")
@Transactional
public class PreLabRoomDAOImpl extends AbstractJpaDao<PreLabRoom> implements
		PreLabRoomDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { PreLabRoom.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new PreLabRoomDAOImpl
	 *
	 */
	public PreLabRoomDAOImpl() {
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
	 * JPQL Query - findPreLabRoomById
	 *
	 */
	@Transactional
	public PreLabRoom findPreLabRoomById(Integer id) throws DataAccessException {

		return findPreLabRoomById(id, -1, -1);
	}

	/**
	 * JPQL Query - findPreLabRoomById
	 *
	 */

	@Transactional
	public PreLabRoom findPreLabRoomById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findPreLabRoomById", startResult, maxRows, id);
			return (PreLabRoom) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findPreLabRoomByRoomName
	 *
	 */
	@Transactional
	public Set<PreLabRoom> findPreLabRoomByRoomName(String roomName) throws DataAccessException {

		return findPreLabRoomByRoomName(roomName, -1, -1);
	}

	/**
	 * JPQL Query - findPreLabRoomByRoomName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreLabRoom> findPreLabRoomByRoomName(String roomName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findPreLabRoomByRoomName", startResult, maxRows, roomName);
		return new LinkedHashSet<PreLabRoom>(query.getResultList());
	}

	/**
	 * JPQL Query - findPreLabRoomByPrimaryKey
	 *
	 */
	@Transactional
	public PreLabRoom findPreLabRoomByPrimaryKey(Integer id) throws DataAccessException {

		return findPreLabRoomByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findPreLabRoomByPrimaryKey
	 *
	 */

	@Transactional
	public PreLabRoom findPreLabRoomByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findPreLabRoomByPrimaryKey", startResult, maxRows, id);
			return (PreLabRoom) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findPreLabRoomByRoomNameContaining
	 *
	 */
	@Transactional
	public Set<PreLabRoom> findPreLabRoomByRoomNameContaining(String roomName) throws DataAccessException {

		return findPreLabRoomByRoomNameContaining(roomName, -1, -1);
	}

	/**
	 * JPQL Query - findPreLabRoomByRoomNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreLabRoom> findPreLabRoomByRoomNameContaining(String roomName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findPreLabRoomByRoomNameContaining", startResult, maxRows, roomName);
		return new LinkedHashSet<PreLabRoom>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllPreLabRooms
	 *
	 */
	@Transactional
	public Set<PreLabRoom> findAllPreLabRooms() throws DataAccessException {

		return findAllPreLabRooms(-1, -1);
	}

	/**
	 * JPQL Query - findAllPreLabRooms
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreLabRoom> findAllPreLabRooms(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllPreLabRooms", startResult, maxRows);
		return new LinkedHashSet<PreLabRoom>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(PreLabRoom entity) {
		return true;
	}
}
