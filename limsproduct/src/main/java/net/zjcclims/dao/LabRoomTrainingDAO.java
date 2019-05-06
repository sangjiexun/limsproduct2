package net.zjcclims.dao;

import java.util.Calendar;
import java.util.Set;

import net.zjcclims.domain.LabRoomTraining;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage LabRoomTraining entities.
 * 
 */
public interface LabRoomTrainingDAO extends JpaDao<LabRoomTraining> {

	/**
	 * JPQL Query - findLabRoomTrainingByAddressContaining
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByAddressContaining(String address) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByAddressContaining
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByAddressContaining(String address, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByAccessJoin
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByAccessJoin(String accessJoin) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByAccessJoin
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByAccessJoin(String accessJoin, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByNumber
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByNumber(Integer number) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByNumber
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByNumber(Integer number, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByContent
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByContent(String content) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByContent
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByContent(String content, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByJoinNumber
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByJoinNumber(Integer joinNumber) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByJoinNumber
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByJoinNumber(Integer joinNumber, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByAddress
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByAddress(String address_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByAddress
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByAddress(String address_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByFlag
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByFlag(Integer flag) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByFlag
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByFlag(Integer flag, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByPassRateContaining
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByPassRateContaining(String passRate) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByPassRateContaining
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByPassRateContaining(String passRate, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByPrimaryKey
	 *
	 */
	public LabRoomTraining findLabRoomTrainingByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByPrimaryKey
	 *
	 */
	public LabRoomTraining findLabRoomTrainingByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByTime
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByTime(java.util.Calendar time) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByTime
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByTime(Calendar time, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomTrainings
	 *
	 */
	public Set<LabRoomTraining> findAllLabRoomTrainings() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomTrainings
	 *
	 */
	public Set<LabRoomTraining> findAllLabRoomTrainings(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByAccessJoinContaining
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByAccessJoinContaining(String accessJoin_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByAccessJoinContaining
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByAccessJoinContaining(String accessJoin_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByPassRate
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByPassRate(String passRate_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingByPassRate
	 *
	 */
	public Set<LabRoomTraining> findLabRoomTrainingByPassRate(String passRate_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingById
	 *
	 */
	public LabRoomTraining findLabRoomTrainingById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingById
	 *
	 */
	public LabRoomTraining findLabRoomTrainingById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}