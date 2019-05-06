package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.LabRoomStationReservationStudent;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage LabRoomStationReservationStudent entities.
 * 
 */
public interface LabRoomStationReservationStudentDAO extends
		JpaDao<LabRoomStationReservationStudent> {

	/**
	 * JPQL Query - findLabRoomStationReservationStudentByCnameContaining
	 *
	 */
	public Set<LabRoomStationReservationStudent> findLabRoomStationReservationStudentByCnameContaining(String cname) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationStudentByCnameContaining
	 *
	 */
	public Set<LabRoomStationReservationStudent> findLabRoomStationReservationStudentByCnameContaining(String cname, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationStudentById
	 *
	 */
	public LabRoomStationReservationStudent findLabRoomStationReservationStudentById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationStudentById
	 *
	 */
	public LabRoomStationReservationStudent findLabRoomStationReservationStudentById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationStudentByUsernameContaining
	 *
	 */
	public Set<LabRoomStationReservationStudent> findLabRoomStationReservationStudentByUsernameContaining(String username) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationStudentByUsernameContaining
	 *
	 */
	public Set<LabRoomStationReservationStudent> findLabRoomStationReservationStudentByUsernameContaining(String username, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomStationReservationStudents
	 *
	 */
	public Set<LabRoomStationReservationStudent> findAllLabRoomStationReservationStudents() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomStationReservationStudents
	 *
	 */
	public Set<LabRoomStationReservationStudent> findAllLabRoomStationReservationStudents(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationStudentByPrimaryKey
	 *
	 */
	public LabRoomStationReservationStudent findLabRoomStationReservationStudentByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationStudentByPrimaryKey
	 *
	 */
	public LabRoomStationReservationStudent findLabRoomStationReservationStudentByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationStudentByUsername
	 *
	 */
	public Set<LabRoomStationReservationStudent> findLabRoomStationReservationStudentByUsername(String username_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationStudentByUsername
	 *
	 */
	public Set<LabRoomStationReservationStudent> findLabRoomStationReservationStudentByUsername(String username_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationStudentByCname
	 *
	 */
	public Set<LabRoomStationReservationStudent> findLabRoomStationReservationStudentByCname(String cname_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationStudentByCname
	 *
	 */
	public Set<LabRoomStationReservationStudent> findLabRoomStationReservationStudentByCname(String cname_1, int startResult, int maxRows) throws DataAccessException;

}