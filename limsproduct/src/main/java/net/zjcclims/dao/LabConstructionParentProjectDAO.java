
package net.zjcclims.dao;

import net.zjcclims.domain.LabConstructionParentProject;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;

/**
 * DAO to manage LabConstructionParentProject entities.
 * 
 */
public interface LabConstructionParentProjectDAO extends JpaDao<LabConstructionParentProject> {

	/**
	 * JPQL Query - findLabConstructionParentProjectByCreateTimeBefore
	 *
	 */
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByCreateTimeBefore(Calendar createTime) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionParentProjectByCreateTimeBefore
	 *
	 */
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByCreateTimeBefore(Calendar createTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionParentProjectBySubmitted
	 *
	 */
	public Set<LabConstructionParentProject> findLabConstructionParentProjectBySubmitted(Boolean submitted) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionParentProjectBySubmitted
	 *
	 */
	public Set<LabConstructionParentProject> findLabConstructionParentProjectBySubmitted(Boolean submitted, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionParentProjectByProjectNameContaining
	 *
	 */
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByProjectNameContaining(String projectName) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionParentProjectByProjectNameContaining
	 *
	 */
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByProjectNameContaining(String projectName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionParentProjectByPrimaryKey
	 *
	 */
	public LabConstructionParentProject findLabConstructionParentProjectByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionParentProjectByPrimaryKey
	 *
	 */
	public LabConstructionParentProject findLabConstructionParentProjectByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionParentProjectByCreateUser
	 *
	 */
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByCreateUser(String createUser) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionParentProjectByCreateUser
	 *
	 */
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByCreateUser(String createUser, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionParentProjectByCreateUserContaining
	 *
	 */
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByCreateUserContaining(String createUser_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionParentProjectByCreateUserContaining
	 *
	 */
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByCreateUserContaining(String createUser_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionParentProjectByBudget
	 *
	 */
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByBudget(BigDecimal budget) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionParentProjectByBudget
	 *
	 */
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByBudget(BigDecimal budget, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionParentProjectByCreateTime
	 *
	 */
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByCreateTime(Calendar createTime_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionParentProjectByCreateTime
	 *
	 */
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByCreateTime(Calendar createTime_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionParentProjectByCreateTimeAfter
	 *
	 */
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByCreateTimeAfter(Calendar createTime_2) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionParentProjectByCreateTimeAfter
	 *
	 */
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByCreateTimeAfter(Calendar createTime_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabConstructionParentProjects
	 *
	 */
	public Set<LabConstructionParentProject> findAllLabConstructionParentProjects() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabConstructionParentProjects
	 *
	 */
	public Set<LabConstructionParentProject> findAllLabConstructionParentProjects(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionParentProjectById
	 *
	 */
	public LabConstructionParentProject findLabConstructionParentProjectById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionParentProjectById
	 *
	 */
	public LabConstructionParentProject findLabConstructionParentProjectById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionParentProjectByProjectName
	 *
	 */
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByProjectName(String projectName_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionParentProjectByProjectName
	 *
	 */
	public Set<LabConstructionParentProject> findLabConstructionParentProjectByProjectName(String projectName_1, int startResult, int maxRows) throws DataAccessException;

}