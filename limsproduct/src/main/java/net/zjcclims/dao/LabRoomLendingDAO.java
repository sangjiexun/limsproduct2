package net.zjcclims.dao;

import java.util.Calendar;
import java.util.Set;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

import net.zjcclims.domain.LabRoomLending;

/**
 * DAO to manage LabRoomLending entities.
 * 
 */
public interface LabRoomLendingDAO extends JpaDao<LabRoomLending> {

	/**
	 * JPQL Query - findLabRoomLendingByClass_
	 *
	 */
	/*public Set<LabRoomLending> findLabRoomLendingByClass_(String class_) throws DataAccessException;*/

	/**
	 * JPQL Query - findLabRoomLendingByClass_
	 *
	 */
	/*public Set<LabRoomLending> findLabRoomLendingByClass_(String class_, int startResult, int maxRows) throws DataAccessException;*/

	/**
	 * JPQL Query - findAllLabRoomLendings
	 *
	 */
	public Set<LabRoomLending> findAllLabRoomLendings() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomLendings
	 *
	 */
	public Set<LabRoomLending> findAllLabRoomLendings(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingByLendingTime
	 *
	 */
	public Set<LabRoomLending> findLabRoomLendingByLendingTime(java.util.Calendar lendingTime) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingByLendingTime
	 *
	 */
	public Set<LabRoomLending> findLabRoomLendingByLendingTime(Calendar lendingTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingByPrimaryKey
	 *
	 */
	public LabRoomLending findLabRoomLendingByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingByLendingStatus
	 *
	 */
	public Set<LabRoomLending> findLabRoomLendingByLendingStatus(Integer lendingStatus) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingByLendingStatus
	 *
	 */
	public Set<LabRoomLending> findLabRoomLendingByLendingStatus(Integer lendingStatus, int startResult, int maxRows) throws DataAccessException;
	
	/**
	 * JPQL Query - findLabRoomLendingByPrimaryKey
	 *
	 */
	public LabRoomLending findLabRoomLendingByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingByLendingReason
	 *
	 */
	public Set<LabRoomLending> findLabRoomLendingByLendingReason(String lendingReason) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingByLendingReason
	 *
	 */
	public Set<LabRoomLending> findLabRoomLendingByLendingReason(String lendingReason, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingByClass_Containing
	 *
	 */
	/*public Set<LabRoomLending> findLabRoomLendingByClass_Containing(String class__1) throws DataAccessException;*/

	/**
	 * JPQL Query - findLabRoomLendingByClass_Containing
	 *
	 */
	/*public Set<LabRoomLending> findLabRoomLendingByClass_Containing(String class__1, int startResult, int maxRows) throws DataAccessException;*/

	/**
	 * JPQL Query - findLabRoomLendingByLendingUserPhoneContaining
	 *
	 */
	public Set<LabRoomLending> findLabRoomLendingByLendingUserPhoneContaining(String lendingUserPhone) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingByLendingUserPhoneContaining
	 *
	 */
	public Set<LabRoomLending> findLabRoomLendingByLendingUserPhoneContaining(String lendingUserPhone, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingByLendingReasonContaining
	 *
	 */
	public Set<LabRoomLending> findLabRoomLendingByLendingReasonContaining(String lendingReason_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingByLendingReasonContaining
	 *
	 */
	public Set<LabRoomLending> findLabRoomLendingByLendingReasonContaining(String lendingReason_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingById
	 *
	 */
	public LabRoomLending findLabRoomLendingById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingById
	 *
	 */
	public LabRoomLending findLabRoomLendingById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingByApplyDate
	 *
	 */
	public Set<LabRoomLending> findLabRoomLendingByApplyDate(java.util.Calendar applyDate) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingByApplyDate
	 *
	 */
	public Set<LabRoomLending> findLabRoomLendingByApplyDate(Calendar applyDate, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingByLendingUserNumber
	 *
	 */
	public Set<LabRoomLending> findLabRoomLendingByLendingUserNumber(Integer lendingUserNumber) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingByLendingUserNumber
	 *
	 */
	public Set<LabRoomLending> findLabRoomLendingByLendingUserNumber(Integer lendingUserNumber, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingByLendingUserPhone
	 *
	 */
	public Set<LabRoomLending> findLabRoomLendingByLendingUserPhone(String lendingUserPhone_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingByLendingUserPhone
	 *
	 */
	public Set<LabRoomLending> findLabRoomLendingByLendingUserPhone(String lendingUserPhone_1, int startResult, int maxRows) throws DataAccessException;

}