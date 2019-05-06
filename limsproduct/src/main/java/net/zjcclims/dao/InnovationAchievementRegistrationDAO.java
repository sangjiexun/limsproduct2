package net.zjcclims.dao;

import java.util.Set;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

import net.zjcclims.domain.InnovationAchievementRegistration;

/**
 * DAO to manage InnovationAchievementRegistration entities.
 * 
 */
public interface InnovationAchievementRegistrationDAO extends
		JpaDao<InnovationAchievementRegistration> {

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByLabRoomNameContaining
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByLabRoomNameContaining(String labRoomName) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByLabRoomNameContaining
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByLabRoomNameContaining(String labRoomName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByLabRoomNumber
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByLabRoomNumber(String labRoomNumber) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByLabRoomNumber
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByLabRoomNumber(String labRoomNumber, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllInnovationAchievementRegistrations
	 *
	 */
	public Set<InnovationAchievementRegistration> findAllInnovationAchievementRegistrations() throws DataAccessException;

	/**
	 * JPQL Query - findAllInnovationAchievementRegistrations
	 *
	 */
	public Set<InnovationAchievementRegistration> findAllInnovationAchievementRegistrations(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByLabRoomNumberContaining
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByLabRoomNumberContaining(String labRoomNumber_1) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByLabRoomNumberContaining
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByLabRoomNumberContaining(String labRoomNumber_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByScoreContaining
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByScoreContaining(String score) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByScoreContaining
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByScoreContaining(String score, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByCname
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByCname(String cname) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByCname
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByCname(String cname, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByInnovationNameContaining
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByInnovationNameContaining(String innovationName) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByInnovationNameContaining
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByInnovationNameContaining(String innovationName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByInnovationName
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByInnovationName(String innovationName_1) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByInnovationName
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByInnovationName(String innovationName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByUsernameContaining
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByUsernameContaining(String username) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByUsernameContaining
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByUsernameContaining(String username, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByScore
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByScore(String score_1) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByScore
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByScore(String score_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationById
	 *
	 */
	public InnovationAchievementRegistration findInnovationAchievementRegistrationById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationById
	 *
	 */
	public InnovationAchievementRegistration findInnovationAchievementRegistrationById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByUsername
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByUsername(String username_1) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByUsername
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByUsername(String username_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByPrimaryKey
	 *
	 */
	public InnovationAchievementRegistration findInnovationAchievementRegistrationByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByPrimaryKey
	 *
	 */
	public InnovationAchievementRegistration findInnovationAchievementRegistrationByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByCnameContaining
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByCnameContaining(String cname_1) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByCnameContaining
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByCnameContaining(String cname_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByLabRoomName
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByLabRoomName(String labRoomName_1) throws DataAccessException;

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByLabRoomName
	 *
	 */
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByLabRoomName(String labRoomName_1, int startResult, int maxRows) throws DataAccessException;

}