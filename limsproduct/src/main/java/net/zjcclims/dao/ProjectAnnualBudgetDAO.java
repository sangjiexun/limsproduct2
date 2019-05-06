package net.zjcclims.dao;


import net.zjcclims.domain.ProjectAnnualBudget;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Calendar;
import java.util.Set;

/**
 * DAO to manage ProjectAnnualBudget entities.
 * 
 */
public interface ProjectAnnualBudgetDAO extends JpaDao<ProjectAnnualBudget> {

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectSourceContaining
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectSourceContaining(String projectSource) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectSourceContaining
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectSourceContaining(String projectSource, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetById
	 *
	 */
	public ProjectAnnualBudget findProjectAnnualBudgetById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetById
	 *
	 */
	public ProjectAnnualBudget findProjectAnnualBudgetById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByStatus
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByStatus(String status) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByStatus
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByStatus(String status, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectFunds
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectFunds(String projectFunds) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectFunds
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectFunds(String projectFunds, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectNameContaining
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectNameContaining(String projectName) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectNameContaining
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectNameContaining(String projectName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByDeadline
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByDeadline(java.util.Calendar deadline) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByDeadline
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByDeadline(Calendar deadline, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByAcademyName
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByAcademyName(String academyName) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByAcademyName
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByAcademyName(String academyName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByAcademyNameContaining
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByAcademyNameContaining(String academyName_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByAcademyNameContaining
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByAcademyNameContaining(String academyName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByAppendixContaining
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByAppendixContaining(String appendix) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByAppendixContaining
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByAppendixContaining(String appendix, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByPrimaryKey
	 *
	 */
	public ProjectAnnualBudget findProjectAnnualBudgetByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByPrimaryKey
	 *
	 */
	public ProjectAnnualBudget findProjectAnnualBudgetByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectProceedContaining
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectProceedContaining(String projectProceed) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectProceedContaining
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectProceedContaining(String projectProceed, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectProceed
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectProceed(String projectProceed_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectProceed
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectProceed(String projectProceed_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectFundsContaining
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectFundsContaining(String projectFunds_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectFundsContaining
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectFundsContaining(String projectFunds_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByStatusContaining
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByStatusContaining(String status_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByStatusContaining
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByStatusContaining(String status_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByConstructYear
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByConstructYear(String constructYear) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByConstructYear
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByConstructYear(String constructYear, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByAppendix
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByAppendix(String appendix_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByAppendix
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByAppendix(String appendix_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByManagerContaining
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByManagerContaining(String manager) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByManagerContaining
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByManagerContaining(String manager, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByManager
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByManager(String manager_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByManager
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByManager(String manager_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectName
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectName(String projectName_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectName
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectName(String projectName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectSource
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectSource(String projectSource_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByProjectSource
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByProjectSource(String projectSource_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectAnnualBudgets
	 *
	 */
	public Set<ProjectAnnualBudget> findAllProjectAnnualBudgets() throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectAnnualBudgets
	 *
	 */
	public Set<ProjectAnnualBudget> findAllProjectAnnualBudgets(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByConstructYearContaining
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByConstructYearContaining(String constructYear_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAnnualBudgetByConstructYearContaining
	 *
	 */
	public Set<ProjectAnnualBudget> findProjectAnnualBudgetByConstructYearContaining(String constructYear_1, int startResult, int maxRows) throws DataAccessException;

}