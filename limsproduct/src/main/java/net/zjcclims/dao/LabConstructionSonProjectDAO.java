
package net.zjcclims.dao;

import java.util.Calendar;
import java.util.Set;

import net.zjcclims.domain.LabConstructionSonProject;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage LabConstructionSonProject entities.
 * 
 */
public interface LabConstructionSonProjectDAO extends JpaDao<LabConstructionSonProject> {

	/**
	 * JPQL Query - findLabConstructionSonProjectByBudgetBalanceTimeAfter
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByBudgetBalanceTimeAfter(Calendar budgetBalanceTime) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByBudgetBalanceTimeAfter
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByBudgetBalanceTimeAfter(Calendar budgetBalanceTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByCreateUser
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByCreateUser(String createUser) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByCreateUser
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByCreateUser(String createUser, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectById
	 *
	 */
	public LabConstructionSonProject findLabConstructionSonProjectById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectById
	 *
	 */
	public LabConstructionSonProject findLabConstructionSonProjectById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByBudget
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByBudget(Integer budget) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByBudget
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByBudget(Integer budget, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByProjectImplementTimeAfter
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByProjectImplementTimeAfter(Calendar projectImplementTime) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByProjectImplementTimeAfter
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByProjectImplementTimeAfter(Calendar projectImplementTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabConstructionSonProjects
	 *
	 */
	public Set<LabConstructionSonProject> findAllLabConstructionSonProjects() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabConstructionSonProjects
	 *
	 */
	public Set<LabConstructionSonProject> findAllLabConstructionSonProjects(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByPrimaryKey
	 *
	 */
	public LabConstructionSonProject findLabConstructionSonProjectByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByPrimaryKey
	 *
	 */
	public LabConstructionSonProject findLabConstructionSonProjectByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByCreateUserContaining
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByCreateUserContaining(String createUser_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByCreateUserContaining
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByCreateUserContaining(String createUser_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByCreateTime
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByCreateTime(Calendar createTime) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByCreateTime
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByCreateTime(Calendar createTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByBudgetBalanceTimeBefore
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByBudgetBalanceTimeBefore(Calendar budgetBalanceTime_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByBudgetBalanceTimeBefore
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByBudgetBalanceTimeBefore(Calendar budgetBalanceTime_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByProjectNameContaining
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByProjectNameContaining(String projectName) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByProjectNameContaining
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByProjectNameContaining(String projectName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByProjectImplementTime
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByProjectImplementTime(Calendar projectImplementTime_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByProjectImplementTime
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByProjectImplementTime(Calendar projectImplementTime_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByProjectImplementTimeBefore
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByProjectImplementTimeBefore(Calendar projectImplementTime_2) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByProjectImplementTimeBefore
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByProjectImplementTimeBefore(Calendar projectImplementTime_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByCreateTimeBefore
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByCreateTimeBefore(Calendar createTime_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByCreateTimeBefore
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByCreateTimeBefore(Calendar createTime_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByCreateTimeAfter
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByCreateTimeAfter(Calendar createTime_2) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByCreateTimeAfter
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByCreateTimeAfter(Calendar createTime_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByProjectName
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByProjectName(String projectName_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByProjectName
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByProjectName(String projectName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectBySubmitted
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectBySubmitted(Boolean submitted) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectBySubmitted
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectBySubmitted(Boolean submitted, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByBudgetBalanceTime
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByBudgetBalanceTime(Calendar budgetBalanceTime_2) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionSonProjectByBudgetBalanceTime
	 *
	 */
	public Set<LabConstructionSonProject> findLabConstructionSonProjectByBudgetBalanceTime(Calendar budgetBalanceTime_2, int startResult, int maxRows) throws DataAccessException;

}