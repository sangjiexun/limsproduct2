package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.LabRoomPermitUser;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage LabRoomPermitUser entities.
 * 
 */
public interface LabRoomPermitUserDAO extends JpaDao<LabRoomPermitUser> {

	/**
	 * JPQL Query - findLabRoomPermitUserByPrimaryKey
	 *
	 */
	public LabRoomPermitUser findLabRoomPermitUserByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomPermitUserByPrimaryKey
	 *
	 */
	public LabRoomPermitUser findLabRoomPermitUserByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomPermitUserByFlag
	 *
	 */
	public Set<LabRoomPermitUser> findLabRoomPermitUserByFlag(Integer flag) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomPermitUserByFlag
	 *
	 */
	public Set<LabRoomPermitUser> findLabRoomPermitUserByFlag(Integer flag, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomPermitUserById
	 *
	 */
	public LabRoomPermitUser findLabRoomPermitUserById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomPermitUserById
	 *
	 */
	public LabRoomPermitUser findLabRoomPermitUserById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomPermitUsers
	 *
	 */
	public Set<LabRoomPermitUser> findAllLabRoomPermitUsers() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomPermitUsers
	 *
	 */
	public Set<LabRoomPermitUser> findAllLabRoomPermitUsers(int startResult, int maxRows) throws DataAccessException;

}