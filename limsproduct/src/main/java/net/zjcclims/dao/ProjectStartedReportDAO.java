package net.zjcclims.dao;


import net.zjcclims.domain.ProjectStartedReport;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;

/**
 * DAO to manage ProjectStartedReport entities.
 * 
 */
public interface ProjectStartedReportDAO extends JpaDao<ProjectStartedReport> {

	/**
	 * JPQL Query - findProjectStartedReportByFeeApp
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByFeeApp(java.math.BigDecimal feeApp) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByFeeApp
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByFeeApp(BigDecimal feeApp, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByLabAddress
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByLabAddress(String labAddress) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByLabAddress
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByLabAddress(String labAddress, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportById
	 *
	 */
	public ProjectStartedReport findProjectStartedReportById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportById
	 *
	 */
	public ProjectStartedReport findProjectStartedReportById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByOpenLabItem
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByOpenLabItem(Integer openLabItem) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByOpenLabItem
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByOpenLabItem(Integer openLabItem, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByLabArea
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByLabArea(java.math.BigDecimal labArea) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByLabArea
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByLabArea(BigDecimal labArea, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByFeeCodeContaining
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByFeeCodeContaining(String feeCode) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByFeeCodeContaining
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByFeeCodeContaining(String feeCode, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByStartDate
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByStartDate(java.util.Calendar startDate) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByStartDate
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByStartDate(Calendar startDate, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByPrimaryKey
	 *
	 */
	public ProjectStartedReport findProjectStartedReportByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByPrimaryKey
	 *
	 */
	public ProjectStartedReport findProjectStartedReportByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByProjectNameContaining
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByProjectNameContaining(String projectName) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByProjectNameContaining
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByProjectNameContaining(String projectName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByEquipmentList
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByEquipmentList(Integer equipmentList) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByEquipmentList
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByEquipmentList(Integer equipmentList, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByLabAddressContaining
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByLabAddressContaining(String labAddress_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByLabAddressContaining
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByLabAddressContaining(String labAddress_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectStartedReports
	 *
	 */
	public Set<ProjectStartedReport> findAllProjectStartedReports() throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectStartedReports
	 *
	 */
	public Set<ProjectStartedReport> findAllProjectStartedReports(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByStartDateAfter
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByStartDateAfter(java.util.Calendar startDate_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByStartDateAfter
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByStartDateAfter(Calendar startDate_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByProjectName
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByProjectName(String projectName_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByProjectName
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByProjectName(String projectName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByFeeCode
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByFeeCode(String feeCode_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByFeeCode
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByFeeCode(String feeCode_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByStartDateBefore
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByStartDateBefore(java.util.Calendar startDate_2) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByStartDateBefore
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByStartDateBefore(Calendar startDate_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByFeeApprovalDetail
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByFeeApprovalDetail(Integer feeApprovalDetail) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByFeeApprovalDetail
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByFeeApprovalDetail(Integer feeApprovalDetail, int startResult, int maxRows) throws DataAccessException;
	/**
	 * JPQL Query - findProjectStartedReportByFeeAmount
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByFeeAmount(java.math.BigDecimal feeAmount) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByFeeAmount
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByFeeAmount(BigDecimal feeAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByOtherFee
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByOtherFee(java.math.BigDecimal otherFee) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartedReportByOtherFee
	 *
	 */
	public Set<ProjectStartedReport> findProjectStartedReportByOtherFee(BigDecimal otherFee, int startResult, int maxRows) throws DataAccessException;

}