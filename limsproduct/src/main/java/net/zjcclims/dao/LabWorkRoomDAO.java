package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.LabWorkRoom;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage LabWorkRoom entities.
 * 
 */
public interface LabWorkRoomDAO extends JpaDao<LabWorkRoom> {

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomNumber
	 *
	 */
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomNumber(String workRoomNumber) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomNumber
	 *
	 */
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomNumber(String workRoomNumber, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomNumberContaining
	 *
	 */
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomNumberContaining(String workRoomNumber_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomNumberContaining
	 *
	 */
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomNumberContaining(String workRoomNumber_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomNameContaining
	 *
	 */
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomNameContaining(String workRoomName) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomNameContaining
	 *
	 */
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomNameContaining(String workRoomName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomName
	 *
	 */
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomName(String workRoomName_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomName
	 *
	 */
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomName(String workRoomName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabWorkRooms
	 *
	 */
	public Set<LabWorkRoom> findAllLabWorkRooms() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabWorkRooms
	 *
	 */
	public Set<LabWorkRoom> findAllLabWorkRooms(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomAddress
	 *
	 */
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomAddress(String workRoomAddress) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomAddress
	 *
	 */
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomAddress(String workRoomAddress, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkRoomById
	 *
	 */
	public LabWorkRoom findLabWorkRoomById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkRoomById
	 *
	 */
	public LabWorkRoom findLabWorkRoomById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomAddressContaining
	 *
	 */
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomAddressContaining(String workRoomAddress_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomAddressContaining
	 *
	 */
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomAddressContaining(String workRoomAddress_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkRoomByPrimaryKey
	 *
	 */
	public LabWorkRoom findLabWorkRoomByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkRoomByPrimaryKey
	 *
	 */
	public LabWorkRoom findLabWorkRoomByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}