package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.RemoteOpenDoor;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage RemoteOpenDoor entities.
 * 
 */
@Repository("RemoteOpenDoorDAO")
@Transactional
public class RemoteOpenDoorDAOImpl extends AbstractJpaDao<RemoteOpenDoor>
		implements RemoteOpenDoorDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { RemoteOpenDoor.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new RemoteOpenDoorDAOImpl
	 *
	 */
	public RemoteOpenDoorDAOImpl() {
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
	 * JPQL Query - findRemoteOpenDoorByDoorNoContaining
	 *
	 */
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByDoorNoContaining(String doorNo) throws DataAccessException {

		return findRemoteOpenDoorByDoorNoContaining(doorNo, -1, -1);
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByDoorNoContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByDoorNoContaining(String doorNo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRemoteOpenDoorByDoorNoContaining", startResult, maxRows, doorNo);
		return new LinkedHashSet<RemoteOpenDoor>(query.getResultList());
	}

	/**
	 * JPQL Query - findRemoteOpenDoorById
	 *
	 */
	@Transactional
	public RemoteOpenDoor findRemoteOpenDoorById(Integer id) throws DataAccessException {

		return findRemoteOpenDoorById(id, -1, -1);
	}

	/**
	 * JPQL Query - findRemoteOpenDoorById
	 *
	 */

	@Transactional
	public RemoteOpenDoor findRemoteOpenDoorById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findRemoteOpenDoorById", startResult, maxRows, id);
			return (net.zjcclims.domain.RemoteOpenDoor) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByCreaterName
	 *
	 */
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByCreaterName(String createrName) throws DataAccessException {

		return findRemoteOpenDoorByCreaterName(createrName, -1, -1);
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByCreaterName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByCreaterName(String createrName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRemoteOpenDoorByCreaterName", startResult, maxRows, createrName);
		return new LinkedHashSet<RemoteOpenDoor>(query.getResultList());
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByUpdateTime
	 *
	 */
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByUpdateTime(java.util.Calendar updateTime) throws DataAccessException {

		return findRemoteOpenDoorByUpdateTime(updateTime, -1, -1);
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByUpdateTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByUpdateTime(java.util.Calendar updateTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRemoteOpenDoorByUpdateTime", startResult, maxRows, updateTime);
		return new LinkedHashSet<RemoteOpenDoor>(query.getResultList());
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByControllerIdContaining
	 *
	 */
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByControllerIdContaining(String controllerId) throws DataAccessException {

		return findRemoteOpenDoorByControllerIdContaining(controllerId, -1, -1);
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByControllerIdContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByControllerIdContaining(String controllerId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRemoteOpenDoorByControllerIdContaining", startResult, maxRows, controllerId);
		return new LinkedHashSet<RemoteOpenDoor>(query.getResultList());
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByDeviceType
	 *
	 */
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByDeviceType(Integer deviceType) throws DataAccessException {

		return findRemoteOpenDoorByDeviceType(deviceType, -1, -1);
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByDeviceType
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByDeviceType(Integer deviceType, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRemoteOpenDoorByDeviceType", startResult, maxRows, deviceType);
		return new LinkedHashSet<RemoteOpenDoor>(query.getResultList());
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByStatus
	 *
	 */
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByStatus(Integer status) throws DataAccessException {

		return findRemoteOpenDoorByStatus(status, -1, -1);
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByStatus
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByStatus(Integer status, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRemoteOpenDoorByStatus", startResult, maxRows, status);
		return new LinkedHashSet<RemoteOpenDoor>(query.getResultList());
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByCreaterUsernameContaining
	 *
	 */
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByCreaterUsernameContaining(String createrUsername) throws DataAccessException {

		return findRemoteOpenDoorByCreaterUsernameContaining(createrUsername, -1, -1);
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByCreaterUsernameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByCreaterUsernameContaining(String createrUsername, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRemoteOpenDoorByCreaterUsernameContaining", startResult, maxRows, createrUsername);
		return new LinkedHashSet<RemoteOpenDoor>(query.getResultList());
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByPrimaryKey
	 *
	 */
	@Transactional
	public RemoteOpenDoor findRemoteOpenDoorByPrimaryKey(Integer id) throws DataAccessException {

		return findRemoteOpenDoorByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByPrimaryKey
	 *
	 */

	@Transactional
	public RemoteOpenDoor findRemoteOpenDoorByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findRemoteOpenDoorByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.RemoteOpenDoor) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByRemoteAction
	 *
	 */
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByRemoteAction(Integer remoteAction) throws DataAccessException {

		return findRemoteOpenDoorByRemoteAction(remoteAction, -1, -1);
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByRemoteAction
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByRemoteAction(Integer remoteAction, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRemoteOpenDoorByRemoteAction", startResult, maxRows, remoteAction);
		return new LinkedHashSet<RemoteOpenDoor>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllRemoteOpenDoors
	 *
	 */
	@Transactional
	public Set<RemoteOpenDoor> findAllRemoteOpenDoors() throws DataAccessException {

		return findAllRemoteOpenDoors(-1, -1);
	}

	/**
	 * JPQL Query - findAllRemoteOpenDoors
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RemoteOpenDoor> findAllRemoteOpenDoors(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllRemoteOpenDoors", startResult, maxRows);
		return new LinkedHashSet<RemoteOpenDoor>(query.getResultList());
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByLabRoomId
	 *
	 */
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByLabRoomId(Integer labRoomId) throws DataAccessException {

		return findRemoteOpenDoorByLabRoomId(labRoomId, -1, -1);
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByLabRoomId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByLabRoomId(Integer labRoomId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRemoteOpenDoorByLabRoomId", startResult, maxRows, labRoomId);
		return new LinkedHashSet<RemoteOpenDoor>(query.getResultList());
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByLabRoomName
	 *
	 */
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByLabRoomName(String labRoomName) throws DataAccessException {

		return findRemoteOpenDoorByLabRoomName(labRoomName, -1, -1);
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByLabRoomName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByLabRoomName(String labRoomName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRemoteOpenDoorByLabRoomName", startResult, maxRows, labRoomName);
		return new LinkedHashSet<RemoteOpenDoor>(query.getResultList());
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByControllerId
	 *
	 */
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByControllerId(String controllerId) throws DataAccessException {

		return findRemoteOpenDoorByControllerId(controllerId, -1, -1);
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByControllerId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByControllerId(String controllerId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRemoteOpenDoorByControllerId", startResult, maxRows, controllerId);
		return new LinkedHashSet<RemoteOpenDoor>(query.getResultList());
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByCreaterNameContaining
	 *
	 */
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByCreaterNameContaining(String createrName) throws DataAccessException {

		return findRemoteOpenDoorByCreaterNameContaining(createrName, -1, -1);
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByCreaterNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByCreaterNameContaining(String createrName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRemoteOpenDoorByCreaterNameContaining", startResult, maxRows, createrName);
		return new LinkedHashSet<RemoteOpenDoor>(query.getResultList());
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByCreateTime
	 *
	 */
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByCreateTime(java.util.Calendar createTime) throws DataAccessException {

		return findRemoteOpenDoorByCreateTime(createTime, -1, -1);
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByCreateTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByCreateTime(java.util.Calendar createTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRemoteOpenDoorByCreateTime", startResult, maxRows, createTime);
		return new LinkedHashSet<RemoteOpenDoor>(query.getResultList());
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByDoorNo
	 *
	 */
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByDoorNo(String doorNo) throws DataAccessException {

		return findRemoteOpenDoorByDoorNo(doorNo, -1, -1);
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByDoorNo
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByDoorNo(String doorNo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRemoteOpenDoorByDoorNo", startResult, maxRows, doorNo);
		return new LinkedHashSet<RemoteOpenDoor>(query.getResultList());
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByCreaterUsername
	 *
	 */
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByCreaterUsername(String createrUsername) throws DataAccessException {

		return findRemoteOpenDoorByCreaterUsername(createrUsername, -1, -1);
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByCreaterUsername
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByCreaterUsername(String createrUsername, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRemoteOpenDoorByCreaterUsername", startResult, maxRows, createrUsername);
		return new LinkedHashSet<RemoteOpenDoor>(query.getResultList());
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByLabRoomNameContaining
	 *
	 */
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByLabRoomNameContaining(String labRoomName) throws DataAccessException {

		return findRemoteOpenDoorByLabRoomNameContaining(labRoomName, -1, -1);
	}

	/**
	 * JPQL Query - findRemoteOpenDoorByLabRoomNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RemoteOpenDoor> findRemoteOpenDoorByLabRoomNameContaining(String labRoomName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRemoteOpenDoorByLabRoomNameContaining", startResult, maxRows, labRoomName);
		return new LinkedHashSet<RemoteOpenDoor>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(RemoteOpenDoor entity) {
		return true;
	}
}
