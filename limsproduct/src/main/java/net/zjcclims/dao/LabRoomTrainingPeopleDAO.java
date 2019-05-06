package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.LabRoomTrainingPeople;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage LabRoomTrainingPeople entities.
 * 
 */
public interface LabRoomTrainingPeopleDAO extends JpaDao<LabRoomTrainingPeople> {

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByTelephone
	 *
	 */
	public Set<LabRoomTrainingPeople> findLabRoomTrainingPeopleByTelephone(String telephone) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByTelephone
	 *
	 */
	public Set<LabRoomTrainingPeople> findLabRoomTrainingPeopleByTelephone(String telephone, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomTrainingPeoples
	 *
	 */
	public Set<LabRoomTrainingPeople> findAllLabRoomTrainingPeoples() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomTrainingPeoples
	 *
	 */
	public Set<LabRoomTrainingPeople> findAllLabRoomTrainingPeoples(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByEMailContaining
	 *
	 */
	public Set<LabRoomTrainingPeople> findLabRoomTrainingPeopleByEMailContaining(String EMail) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByEMailContaining
	 *
	 */
	public Set<LabRoomTrainingPeople> findLabRoomTrainingPeopleByEMailContaining(String EMail, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByMessageFlag
	 *
	 */
	public Set<LabRoomTrainingPeople> findLabRoomTrainingPeopleByMessageFlag(Integer messageFlag) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByMessageFlag
	 *
	 */
	public Set<LabRoomTrainingPeople> findLabRoomTrainingPeopleByMessageFlag(Integer messageFlag, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByTelephoneContaining
	 *
	 */
	public Set<LabRoomTrainingPeople> findLabRoomTrainingPeopleByTelephoneContaining(String telephone_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByTelephoneContaining
	 *
	 */
	public Set<LabRoomTrainingPeople> findLabRoomTrainingPeopleByTelephoneContaining(String telephone_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByEMail
	 *
	 */
	public Set<LabRoomTrainingPeople> findLabRoomTrainingPeopleByEMail(String EMail_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByEMail
	 *
	 */
	public Set<LabRoomTrainingPeople> findLabRoomTrainingPeopleByEMail(String EMail_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingPeopleById
	 *
	 */
	public LabRoomTrainingPeople findLabRoomTrainingPeopleById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingPeopleById
	 *
	 */
	public LabRoomTrainingPeople findLabRoomTrainingPeopleById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByPrimaryKey
	 *
	 */
	public LabRoomTrainingPeople findLabRoomTrainingPeopleByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByPrimaryKey
	 *
	 */
	public LabRoomTrainingPeople findLabRoomTrainingPeopleByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}