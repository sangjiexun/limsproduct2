package net.zjcclims.dao;

import java.util.Calendar;
import java.util.Set;

import net.zjcclims.domain.RemoteOpenDoor;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage RemoteOpenDoor entities.
 * 
 */
public interface RemoteOpenDoorDAO extends JpaDao<RemoteOpenDoor> {

	/**
	 * JPQL Query - findRemoteOpenDoorByDoorNoContaining
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByDoorNoContaining(String doorNo) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByDoorNoContaining
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByDoorNoContaining(String doorNo, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorById
	 *
	 */
	public RemoteOpenDoor findRemoteOpenDoorById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorById
	 *
	 */
	public RemoteOpenDoor findRemoteOpenDoorById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByCreaterName
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByCreaterName(String createrName) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByCreaterName
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByCreaterName(String createrName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByUpdateTime
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByUpdateTime(java.util.Calendar updateTime) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByUpdateTime
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByUpdateTime(Calendar updateTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByControllerIdContaining
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByControllerIdContaining(String controllerId) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByControllerIdContaining
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByControllerIdContaining(String controllerId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByDeviceType
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByDeviceType(Integer deviceType) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByDeviceType
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByDeviceType(Integer deviceType, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByStatus
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByStatus(Integer status) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByStatus
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByStatus(Integer status, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByCreaterUsernameContaining
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByCreaterUsernameContaining(String createrUsername) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByCreaterUsernameContaining
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByCreaterUsernameContaining(String createrUsername, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByPrimaryKey
	 *
	 */
	public RemoteOpenDoor findRemoteOpenDoorByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByPrimaryKey
	 *
	 */
	public RemoteOpenDoor findRemoteOpenDoorByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByRemoteAction
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByRemoteAction(Integer remoteAction) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByRemoteAction
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByRemoteAction(Integer remoteAction, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllRemoteOpenDoors
	 *
	 */
	public Set<RemoteOpenDoor> findAllRemoteOpenDoors() throws DataAccessException;

	/**
	 * JPQL Query - findAllRemoteOpenDoors
	 *
	 */
	public Set<RemoteOpenDoor> findAllRemoteOpenDoors(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByLabRoomId
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByLabRoomId(Integer labRoomId) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByLabRoomId
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByLabRoomId(Integer labRoomId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByLabRoomName
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByLabRoomName(String labRoomName) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByLabRoomName
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByLabRoomName(String labRoomName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByControllerId
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByControllerId(String controllerId_1) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByControllerId
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByControllerId(String controllerId_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByCreaterNameContaining
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByCreaterNameContaining(String createrName_1) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByCreaterNameContaining
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByCreaterNameContaining(String createrName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByCreateTime
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByCreateTime(java.util.Calendar createTime) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByCreateTime
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByCreateTime(Calendar createTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByDoorNo
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByDoorNo(String doorNo_1) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByDoorNo
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByDoorNo(String doorNo_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByCreaterUsername
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByCreaterUsername(String createrUsername_1) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByCreaterUsername
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByCreaterUsername(String createrUsername_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByLabRoomNameContaining
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByLabRoomNameContaining(String labRoomName_1) throws DataAccessException;

	/**
	 * JPQL Query - findRemoteOpenDoorByLabRoomNameContaining
	 *
	 */
	public Set<RemoteOpenDoor> findRemoteOpenDoorByLabRoomNameContaining(String labRoomName_1, int startResult, int maxRows) throws DataAccessException;

}