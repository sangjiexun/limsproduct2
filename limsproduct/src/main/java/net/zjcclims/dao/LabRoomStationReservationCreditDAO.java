package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.LabRoomStationReservationCredit;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage LabRoomStationReservationCredit entities.
 * 
 */
public interface LabRoomStationReservationCreditDAO extends
		JpaDao<LabRoomStationReservationCredit> {

	/**
	 * JPQL Query - findLabRoomStationReservationCreditByRemarkContaining
	 *
	 */
	public Set<LabRoomStationReservationCredit> findLabRoomStationReservationCreditByRemarkContaining(String remark) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationCreditByRemarkContaining
	 *
	 */
	public Set<LabRoomStationReservationCredit> findLabRoomStationReservationCreditByRemarkContaining(String remark, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationCreditByPrimaryKey
	 *
	 */
	public LabRoomStationReservationCredit findLabRoomStationReservationCreditByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationCreditByPrimaryKey
	 *
	 */
	public LabRoomStationReservationCredit findLabRoomStationReservationCreditByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationCreditById
	 *
	 */
	public LabRoomStationReservationCredit findLabRoomStationReservationCreditById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationCreditById
	 *
	 */
	public LabRoomStationReservationCredit findLabRoomStationReservationCreditById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomStationReservationCredits
	 *
	 */
	public Set<LabRoomStationReservationCredit> findAllLabRoomStationReservationCredits() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomStationReservationCredits
	 *
	 */
	public Set<LabRoomStationReservationCredit> findAllLabRoomStationReservationCredits(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationCreditByRemark
	 *
	 */
	public Set<LabRoomStationReservationCredit> findLabRoomStationReservationCreditByRemark(String remark_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomStationReservationCreditByRemark
	 *
	 */
	public Set<LabRoomStationReservationCredit> findLabRoomStationReservationCreditByRemark(String remark_1, int startResult, int maxRows) throws DataAccessException;

}