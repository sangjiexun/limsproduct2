package net.zjcclims.dao;


import net.zjcclims.domain.ProjectAcceptUser;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage ProjectAcceptUser entities.
 * 
 */
public interface ProjectAcceptUserDAO extends JpaDao<ProjectAcceptUser> {

	/**
	 * JPQL Query - findProjectAcceptUserByCnameContaining
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserByCnameContaining(String cname) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserByCnameContaining
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserByCnameContaining(String cname, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserByJobTitleContaining
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserByJobTitleContaining(String jobTitle) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserByJobTitleContaining
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserByJobTitleContaining(String jobTitle, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserByPositionContaining
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserByPositionContaining(String position) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserByPositionContaining
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserByPositionContaining(String position, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserBySexContaining
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserBySexContaining(String sex) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserBySexContaining
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserBySexContaining(String sex, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserByInfo
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserByInfo(String info) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserByInfo
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserByInfo(String info, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserById
	 *
	 */
	public ProjectAcceptUser findProjectAcceptUserById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserById
	 *
	 */
	public ProjectAcceptUser findProjectAcceptUserById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserByResponsibilityContent
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserByResponsibilityContent(String responsibilityContent) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserByResponsibilityContent
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserByResponsibilityContent(String responsibilityContent, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserBySex
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserBySex(String sex_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserBySex
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserBySex(String sex_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserByPosition
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserByPosition(String position_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserByPosition
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserByPosition(String position_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserByResponsibilityContentContaining
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserByResponsibilityContentContaining(String responsibilityContent_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserByResponsibilityContentContaining
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserByResponsibilityContentContaining(String responsibilityContent_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserByCname
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserByCname(String cname_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserByCname
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserByCname(String cname_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserByJobTitle
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserByJobTitle(String jobTitle_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserByJobTitle
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserByJobTitle(String jobTitle_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectAcceptUsers
	 *
	 */
	public Set<ProjectAcceptUser> findAllProjectAcceptUsers() throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectAcceptUsers
	 *
	 */
	public Set<ProjectAcceptUser> findAllProjectAcceptUsers(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserByAge
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserByAge(Integer age) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserByAge
	 *
	 */
	public Set<ProjectAcceptUser> findProjectAcceptUserByAge(Integer age, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserByPrimaryKey
	 *
	 */
	public ProjectAcceptUser findProjectAcceptUserByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptUserByPrimaryKey
	 *
	 */
	public ProjectAcceptUser findProjectAcceptUserByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}