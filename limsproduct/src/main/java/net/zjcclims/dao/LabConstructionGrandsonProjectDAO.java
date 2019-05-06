
package net.zjcclims.dao;

import java.util.Calendar;
import java.util.Set;

import net.zjcclims.domain.LabConstructionGrandsonProject;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage LabConstructionGrandsonProject entities.
 * 
 */
public interface LabConstructionGrandsonProjectDAO extends JpaDao<LabConstructionGrandsonProject> {

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByProjectImplementTimeBefore
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByProjectImplementTimeBefore(Calendar projectImplementTime) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByProjectImplementTimeBefore
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByProjectImplementTimeBefore(Calendar projectImplementTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByProjectNameContaining
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByProjectNameContaining(String projectName) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByProjectNameContaining
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByProjectNameContaining(String projectName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByTenderActualAmount
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByTenderActualAmount(Integer tenderActualAmount) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByTenderActualAmount
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByTenderActualAmount(Integer tenderActualAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByBudget
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByBudget(Integer budget) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByBudget
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByBudget(Integer budget, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectById
	 *
	 */
	public LabConstructionGrandsonProject findLabConstructionGrandsonProjectById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectById
	 *
	 */
	public LabConstructionGrandsonProject findLabConstructionGrandsonProjectById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByCreateUser
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByCreateUser(String createUser) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByCreateUser
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByCreateUser(String createUser, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByStatus
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByStatus(Integer status) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByStatus
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByStatus(Integer status, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByCreateTimeBefore
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByCreateTimeBefore(Calendar createTime) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByCreateTimeBefore
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByCreateTimeBefore(Calendar createTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByCreateUserContaining
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByCreateUserContaining(String createUser_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByCreateUserContaining
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByCreateUserContaining(String createUser_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByCreateTime
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByCreateTime(Calendar createTime_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByCreateTime
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByCreateTime(Calendar createTime_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByAcceptanceaActualAmount
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByAcceptanceaActualAmount(Integer acceptanceaActualAmount) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByAcceptanceaActualAmount
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByAcceptanceaActualAmount(Integer acceptanceaActualAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabConstructionGrandsonProjects
	 *
	 */
	public Set<LabConstructionGrandsonProject> findAllLabConstructionGrandsonProjects() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabConstructionGrandsonProjects
	 *
	 */
	public Set<LabConstructionGrandsonProject> findAllLabConstructionGrandsonProjects(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByStage
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByStage(Integer stage) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByStage
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByStage(Integer stage, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByProjectImplementTimeAfter
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByProjectImplementTimeAfter(Calendar projectImplementTime_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByProjectImplementTimeAfter
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByProjectImplementTimeAfter(Calendar projectImplementTime_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByProjectName
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByProjectName(String projectName_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByProjectName
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByProjectName(String projectName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByCreateTimeAfter
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByCreateTimeAfter(Calendar createTime_2) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByCreateTimeAfter
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByCreateTimeAfter(Calendar createTime_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByProjectImplementTime
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByProjectImplementTime(Calendar projectImplementTime_2) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByProjectImplementTime
	 *
	 */
	public Set<LabConstructionGrandsonProject> findLabConstructionGrandsonProjectByProjectImplementTime(Calendar projectImplementTime_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByPrimaryKey
	 *
	 */
	public LabConstructionGrandsonProject findLabConstructionGrandsonProjectByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionGrandsonProjectByPrimaryKey
	 *
	 */
	public LabConstructionGrandsonProject findLabConstructionGrandsonProjectByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}