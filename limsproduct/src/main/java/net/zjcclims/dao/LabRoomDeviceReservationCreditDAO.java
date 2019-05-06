package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.LabRoomDeviceReservationCredit;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage LabRoomDeviceReservationCredit entities.
 * 
 */
public interface LabRoomDeviceReservationCreditDAO extends
		JpaDao<LabRoomDeviceReservationCredit> {

	/**
	 * JPQL Query - findLabRoomDeviceReservationCreditById
	 *
	 */
	public LabRoomDeviceReservationCredit findLabRoomDeviceReservationCreditById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomDeviceReservationCreditById
	 *
	 */
	public LabRoomDeviceReservationCredit findLabRoomDeviceReservationCreditById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomDeviceReservationCreditByRemarkContaining
	 *
	 */
	public Set<LabRoomDeviceReservationCredit> findLabRoomDeviceReservationCreditByRemarkContaining(String remark) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomDeviceReservationCreditByRemarkContaining
	 *
	 */
	public Set<LabRoomDeviceReservationCredit> findLabRoomDeviceReservationCreditByRemarkContaining(String remark, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomDeviceReservationCreditByPrimaryKey
	 *
	 */
	public LabRoomDeviceReservationCredit findLabRoomDeviceReservationCreditByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomDeviceReservationCreditByPrimaryKey
	 *
	 */
	public LabRoomDeviceReservationCredit findLabRoomDeviceReservationCreditByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomDeviceReservationCreditByRemark
	 *
	 */
	public Set<LabRoomDeviceReservationCredit> findLabRoomDeviceReservationCreditByRemark(String remark_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomDeviceReservationCreditByRemark
	 *
	 */
	public Set<LabRoomDeviceReservationCredit> findLabRoomDeviceReservationCreditByRemark(String remark_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomDeviceReservationCredits
	 *
	 */
	public Set<LabRoomDeviceReservationCredit> findAllLabRoomDeviceReservationCredits() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomDeviceReservationCredits
	 *
	 */
	public Set<LabRoomDeviceReservationCredit> findAllLabRoomDeviceReservationCredits(int startResult, int maxRows) throws DataAccessException;

}