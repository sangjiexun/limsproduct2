package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.SmartAgentUser;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage SmartAgentUser entities.
 * 
 */
public interface SmartAgentUserDAO extends JpaDao<SmartAgentUser> {

	/**
	 * JPQL Query - findSmartAgentUserById
	 *
	 */
	public SmartAgentUser findSmartAgentUserById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentUserById
	 *
	 */
	public SmartAgentUser findSmartAgentUserById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllSmartAgentUsers
	 *
	 */
	public Set<SmartAgentUser> findAllSmartAgentUsers() throws DataAccessException;

	/**
	 * JPQL Query - findAllSmartAgentUsers
	 *
	 */
	public Set<SmartAgentUser> findAllSmartAgentUsers(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentUserByPrimaryKey
	 *
	 */
	public SmartAgentUser findSmartAgentUserByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentUserByPrimaryKey
	 *
	 */
	public SmartAgentUser findSmartAgentUserByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentUserByAcademyContaining
	 *
	 */
	public Set<SmartAgentUser> findSmartAgentUserByAcademyContaining(String academy) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentUserByAcademyContaining
	 *
	 */
	public Set<SmartAgentUser> findSmartAgentUserByAcademyContaining(String academy, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentUserByCnameContaining
	 *
	 */
	public Set<SmartAgentUser> findSmartAgentUserByCnameContaining(String cname) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentUserByCnameContaining
	 *
	 */
	public Set<SmartAgentUser> findSmartAgentUserByCnameContaining(String cname, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentUserBySerialNo
	 *
	 */
	public Set<SmartAgentUser> findSmartAgentUserBySerialNo(String serialNo) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentUserBySerialNo
	 *
	 */
	public Set<SmartAgentUser> findSmartAgentUserBySerialNo(String serialNo, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentUserByAcademy
	 *
	 */
	public Set<SmartAgentUser> findSmartAgentUserByAcademy(String academy_1) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentUserByAcademy
	 *
	 */
	public Set<SmartAgentUser> findSmartAgentUserByAcademy(String academy_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentUserByCname
	 *
	 */
	public Set<SmartAgentUser> findSmartAgentUserByCname(String cname_1) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentUserByCname
	 *
	 */
	public Set<SmartAgentUser> findSmartAgentUserByCname(String cname_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentUserByUsername
	 *
	 */
	public Set<SmartAgentUser> findSmartAgentUserByUsername(String username) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentUserByUsername
	 *
	 */
	public Set<SmartAgentUser> findSmartAgentUserByUsername(String username, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentUserByUsernameContaining
	 *
	 */
	public Set<SmartAgentUser> findSmartAgentUserByUsernameContaining(String username_1) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentUserByUsernameContaining
	 *
	 */
	public Set<SmartAgentUser> findSmartAgentUserByUsernameContaining(String username_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentUserBySerialNoContaining
	 *
	 */
	public Set<SmartAgentUser> findSmartAgentUserBySerialNoContaining(String serialNo_1) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentUserBySerialNoContaining
	 *
	 */
	public Set<SmartAgentUser> findSmartAgentUserBySerialNoContaining(String serialNo_1, int startResult, int maxRows) throws DataAccessException;

}