package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.LabRoomReservationCredit;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage LabRoomReservationCredit entities.
 * 
 */
public interface LabRoomReservationCreditDAO extends
		JpaDao<LabRoomReservationCredit> {

	/**
	 * JPQL Query - findLabRoomReservationCreditByRemarkContaining
	 *
	 */
	public Set<LabRoomReservationCredit> findLabRoomReservationCreditByRemarkContaining(String remark) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomReservationCreditByRemarkContaining
	 *
	 */
	public Set<LabRoomReservationCredit> findLabRoomReservationCreditByRemarkContaining(String remark, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomReservationCredits
	 *
	 */
	public Set<LabRoomReservationCredit> findAllLabRoomReservationCredits() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomReservationCredits
	 *
	 */
	public Set<LabRoomReservationCredit> findAllLabRoomReservationCredits(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomReservationCreditByPrimaryKey
	 *
	 */
	public LabRoomReservationCredit findLabRoomReservationCreditByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomReservationCreditByPrimaryKey
	 *
	 */
	public LabRoomReservationCredit findLabRoomReservationCreditByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomReservationCreditById
	 *
	 */
	public LabRoomReservationCredit findLabRoomReservationCreditById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomReservationCreditById
	 *
	 */
	public LabRoomReservationCredit findLabRoomReservationCreditById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomReservationCreditByRemark
	 *
	 */
	public Set<LabRoomReservationCredit> findLabRoomReservationCreditByRemark(String remark_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomReservationCreditByRemark
	 *
	 */
	public Set<LabRoomReservationCredit> findLabRoomReservationCreditByRemark(String remark_1, int startResult, int maxRows) throws DataAccessException;

}