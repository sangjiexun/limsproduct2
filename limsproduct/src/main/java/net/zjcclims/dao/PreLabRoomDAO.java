package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.PreLabRoom;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage PreLabRoom entities.
 * 
 */
public interface PreLabRoomDAO extends JpaDao<PreLabRoom> {

	/**
	 * JPQL Query - findPreLabRoomById
	 *
	 */
	public PreLabRoom findPreLabRoomById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findPreLabRoomById
	 *
	 */
	public PreLabRoom findPreLabRoomById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreLabRoomByRoomName
	 *
	 */
	public Set<PreLabRoom> findPreLabRoomByRoomName(String roomName) throws DataAccessException;

	/**
	 * JPQL Query - findPreLabRoomByRoomName
	 *
	 */
	public Set<PreLabRoom> findPreLabRoomByRoomName(String roomName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreLabRoomByPrimaryKey
	 *
	 */
	public PreLabRoom findPreLabRoomByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findPreLabRoomByPrimaryKey
	 *
	 */
	public PreLabRoom findPreLabRoomByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreLabRoomByRoomNameContaining
	 *
	 */
	public Set<PreLabRoom> findPreLabRoomByRoomNameContaining(String roomName_1) throws DataAccessException;

	/**
	 * JPQL Query - findPreLabRoomByRoomNameContaining
	 *
	 */
	public Set<PreLabRoom> findPreLabRoomByRoomNameContaining(String roomName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllPreLabRooms
	 *
	 */
	public Set<PreLabRoom> findAllPreLabRooms() throws DataAccessException;

	/**
	 * JPQL Query - findAllPreLabRooms
	 *
	 */
	public Set<PreLabRoom> findAllPreLabRooms(int startResult, int maxRows) throws DataAccessException;

}