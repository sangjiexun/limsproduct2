package net.zjcclims.dao;


import net.zjcclims.domain.LabConstructApp;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;

/**
 * DAO to manage LabConstructApp entities.
 * 
 */
public interface LabConstructAppDAO extends JpaDao<LabConstructApp> {

	/**
	 * JPQL Query - findLabConstructAppByAppDate
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByAppDate(java.util.Calendar appDate) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByAppDate
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByAppDate(Calendar appDate, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByPartyId
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByPartyId(Integer partyId) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByPartyId
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByPartyId(Integer partyId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByOtherAppendix
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByOtherAppendix(String otherAppendix) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByOtherAppendix
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByOtherAppendix(String otherAppendix, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByApprovalAppendix
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByApprovalAppendix(String approvalAppendix) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByApprovalAppendix
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByApprovalAppendix(String approvalAppendix, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByConstructBasisContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByConstructBasisContaining(String constructBasis) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByConstructBasisContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByConstructBasisContaining(String constructBasis, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByProjectNameContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByProjectNameContaining(String projectName) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByProjectNameContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByProjectNameContaining(String projectName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByLabConstructAppCode
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByLabConstructAppCode(String labConstructAppCode) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByLabConstructAppCode
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByLabConstructAppCode(String labConstructAppCode, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByPrimaryKey
	 *
	 */
	public LabConstructApp findLabConstructAppByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByPrimaryKey
	 *
	 */
	public LabConstructApp findLabConstructAppByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByProjectBasis
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByProjectBasis(String projectBasis) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByProjectBasis
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByProjectBasis(String projectBasis, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppBySpecialInnovationContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppBySpecialInnovationContaining(String specialInnovation) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppBySpecialInnovationContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppBySpecialInnovationContaining(String specialInnovation, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByApprovalAppendixContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByApprovalAppendixContaining(String approvalAppendix_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByApprovalAppendixContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByApprovalAppendixContaining(String approvalAppendix_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByParticipant
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByParticipant(Integer participant) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByParticipant
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByParticipant(Integer participant, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByExpectedResultContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByExpectedResultContaining(String expectedResult) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByExpectedResultContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByExpectedResultContaining(String expectedResult, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByLabConstructAppCodeContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByLabConstructAppCodeContaining(String labConstructAppCode_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByLabConstructAppCodeContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByLabConstructAppCodeContaining(String labConstructAppCode_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByOpenLabItem
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByOpenLabItem(Integer openLabItem) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByOpenLabItem
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByOpenLabItem(Integer openLabItem, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByProjectName
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByProjectName(String projectName_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByProjectName
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByProjectName(String projectName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByConstructBasis
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByConstructBasis(String constructBasis_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByConstructBasis
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByConstructBasis(String constructBasis_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByProjectBasisContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByProjectBasisContaining(String projectBasis_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByProjectBasisContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByProjectBasisContaining(String projectBasis_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByEquipmentDetail
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByEquipmentDetail(Integer equipmentDetail) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByEquipmentDetail
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByEquipmentDetail(Integer equipmentDetail, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByOtherAppendixContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByOtherAppendixContaining(String otherAppendix_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByOtherAppendixContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByOtherAppendixContaining(String otherAppendix_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByAppDateBefore
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByAppDateBefore(java.util.Calendar appDate_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByAppDateBefore
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByAppDateBefore(Calendar appDate_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByAppropriationBudget
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByAppropriationBudget(Integer appropriationBudget) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByAppropriationBudget
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByAppropriationBudget(Integer appropriationBudget, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByPrimaryObjective
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByPrimaryObjective(String primaryObjective) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByPrimaryObjective
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByPrimaryObjective(String primaryObjective, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByExpectedResult
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByExpectedResult(String expectedResult_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByExpectedResult
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByExpectedResult(String expectedResult_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppBySpecialInnovation
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppBySpecialInnovation(String specialInnovation_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppBySpecialInnovation
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppBySpecialInnovation(String specialInnovation_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByAppDateAfter
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByAppDateAfter(java.util.Calendar appDate_2) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByAppDateAfter
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByAppDateAfter(Calendar appDate_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabConstructApps
	 *
	 */
	public Set<LabConstructApp> findAllLabConstructApps() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabConstructApps
	 *
	 */
	public Set<LabConstructApp> findAllLabConstructApps(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByPrimaryObjectiveContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByPrimaryObjectiveContaining(String primaryObjective_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByPrimaryObjectiveContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByPrimaryObjectiveContaining(String primaryObjective_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppById
	 *
	 */
	public LabConstructApp findLabConstructAppById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppById
	 *
	 */
	public LabConstructApp findLabConstructAppById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByPlanSchedule
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByPlanSchedule(String planSchedule) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByPlanSchedule
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByPlanSchedule(String planSchedule, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByCourseAmount
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByCourseAmount(Integer courseAmount) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByCourseAmount
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByCourseAmount(Integer courseAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByMajorAmount
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByMajorAmount(Integer majorAmount) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByMajorAmount
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByMajorAmount(Integer majorAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByFeeAmount
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByFeeAmount(java.math.BigDecimal feeAmount) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByFeeAmount
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByFeeAmount(BigDecimal feeAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByOtherFee
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByOtherFee(java.math.BigDecimal otherFee) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByOtherFee
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByOtherFee(BigDecimal otherFee, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByMajorName
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByMajorName(String majorName) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByMajorName
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByMajorName(String majorName, int startResult, int maxRows) throws DataAccessException;
	/**
	 * JPQL Query - findLabConstructAppByMajorNameContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByMajorNameContaining(String majorName_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByMajorNameContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByMajorNameContaining(String majorName_1, int startResult, int maxRows) throws DataAccessException;
	/**
	 * JPQL Query - findLabConstructAppByPurposeNameContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByPurposeNameContaining(String purposeName) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByPurposeNameContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByPurposeNameContaining(String purposeName, int startResult, int maxRows) throws DataAccessException;
	/**
	 * JPQL Query - findLabConstructAppByPurposeName
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByPurposeName(String purposeName_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByPurposeName
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByPurposeName(String purposeName_1, int startResult, int maxRows) throws DataAccessException;
	
	/**
	 * JPQL Query - findLabConstructAppByCourseNameContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByCourseNameContaining(String courseName) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByCourseNameContaining
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByCourseNameContaining(String courseName, int startResult, int maxRows) throws DataAccessException;
	/**
	 * JPQL Query - findLabConstructAppByCourseName
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByCourseName(String courseName_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppByCourseName
	 *
	 */
	public Set<LabConstructApp> findLabConstructAppByCourseName(String courseName_1, int startResult, int maxRows) throws DataAccessException;


}