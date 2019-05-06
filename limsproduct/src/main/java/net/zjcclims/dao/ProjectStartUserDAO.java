package net.zjcclims.dao;


import net.zjcclims.domain.ProjectStartUser;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage ProjectStartUser entities.
 * 
 */
public interface ProjectStartUserDAO extends JpaDao<ProjectStartUser> {

	/**
	 * JPQL Query - findProjectStartUserByInfo
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserByInfo(String info) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserByInfo
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserByInfo(String info, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserByPrimaryKey
	 *
	 */
	public ProjectStartUser findProjectStartUserByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserByPrimaryKey
	 *
	 */
	public ProjectStartUser findProjectStartUserByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserBySex
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserBySex(String sex) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserBySex
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserBySex(String sex, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserByResponsibilityContent
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserByResponsibilityContent(String responsibilityContent) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserByResponsibilityContent
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserByResponsibilityContent(String responsibilityContent, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserByCnameContaining
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserByCnameContaining(String cname) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserByCnameContaining
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserByCnameContaining(String cname, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserByPositionContaining
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserByPositionContaining(String position) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserByPositionContaining
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserByPositionContaining(String position, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserByCname
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserByCname(String cname_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserByCname
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserByCname(String cname_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserBySexContaining
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserBySexContaining(String sex_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserBySexContaining
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserBySexContaining(String sex_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserByJobTitle
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserByJobTitle(String jobTitle) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserByJobTitle
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserByJobTitle(String jobTitle, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectStartUsers
	 *
	 */
	public Set<ProjectStartUser> findAllProjectStartUsers() throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectStartUsers
	 *
	 */
	public Set<ProjectStartUser> findAllProjectStartUsers(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserByResponsibilityContentContaining
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserByResponsibilityContentContaining(String responsibilityContent_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserByResponsibilityContentContaining
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserByResponsibilityContentContaining(String responsibilityContent_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserById
	 *
	 */
	public ProjectStartUser findProjectStartUserById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserById
	 *
	 */
	public ProjectStartUser findProjectStartUserById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserByPosition
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserByPosition(String position_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserByPosition
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserByPosition(String position_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserByJobTitleContaining
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserByJobTitleContaining(String jobTitle_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserByJobTitleContaining
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserByJobTitleContaining(String jobTitle_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserByAge
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserByAge(Integer age) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartUserByAge
	 *
	 */
	public Set<ProjectStartUser> findProjectStartUserByAge(Integer age, int startResult, int maxRows) throws DataAccessException;

}