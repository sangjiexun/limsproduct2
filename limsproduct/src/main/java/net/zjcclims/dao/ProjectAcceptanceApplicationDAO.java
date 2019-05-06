package net.zjcclims.dao;


import net.zjcclims.domain.ProjectAcceptanceApplication;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;

/**
 * DAO to manage ProjectAcceptanceApplication entities.
 * 
 */
public interface ProjectAcceptanceApplicationDAO extends
		JpaDao<ProjectAcceptanceApplication> {

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByExpectCompleteDateBefore
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByExpectCompleteDateBefore(java.util.Calendar expectCompleteDate) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByExpectCompleteDateBefore
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByExpectCompleteDateBefore(Calendar expectCompleteDate, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByRealityCompleteDate
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByRealityCompleteDate(java.util.Calendar realityCompleteDate) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByRealityCompleteDate
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByRealityCompleteDate(Calendar realityCompleteDate, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOriginalLabroomItem
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOriginalLabroomItem(Integer originalLabroomItem) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOriginalLabroomItem
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOriginalLabroomItem(Integer originalLabroomItem, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByActualBenefits
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByActualBenefits(String actualBenefits) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByActualBenefits
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByActualBenefits(String actualBenefits, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByExpectCompleteDate
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByExpectCompleteDate(java.util.Calendar expectCompleteDate_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByExpectCompleteDate
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByExpectCompleteDate(Calendar expectCompleteDate_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByRealityCompleteDateBefore
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByRealityCompleteDateBefore(java.util.Calendar realityCompleteDate_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByRealityCompleteDateBefore
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByRealityCompleteDateBefore(Calendar realityCompleteDate_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByTargetLabroomArea
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByTargetLabroomArea(java.math.BigDecimal targetLabroomArea) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByTargetLabroomArea
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByTargetLabroomArea(BigDecimal targetLabroomArea, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOriginalLabroomAdd
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOriginalLabroomAdd(String originalLabroomAdd) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOriginalLabroomAdd
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOriginalLabroomAdd(String originalLabroomAdd, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByPrimaryKey
	 *
	 */
	public ProjectAcceptanceApplication findProjectAcceptanceApplicationByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByPrimaryKey
	 *
	 */
	public ProjectAcceptanceApplication findProjectAcceptanceApplicationByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectExpectedBenefits
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectExpectedBenefits(String projectExpectedBenefits) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectExpectedBenefits
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectExpectedBenefits(String projectExpectedBenefits, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectStartDateBefore
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectStartDateBefore(java.util.Calendar projectStartDate) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectStartDateBefore
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectStartDateBefore(Calendar projectStartDate, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectStartDate
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectStartDate(java.util.Calendar projectStartDate_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectStartDate
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectStartDate(Calendar projectStartDate_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByMajorNameContaining
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByMajorNameContaining(String majorName) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByMajorNameContaining
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByMajorNameContaining(String majorName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOpenLabItem
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOpenLabItem(Integer openLabItem) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOpenLabItem
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOpenLabItem(Integer openLabItem, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOriginalLabroomValue
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOriginalLabroomValue(java.math.BigDecimal originalLabroomValue) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOriginalLabroomValue
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOriginalLabroomValue(BigDecimal originalLabroomValue, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByRealityCompleteDateAfter
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByRealityCompleteDateAfter(java.util.Calendar realityCompleteDate_2) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByRealityCompleteDateAfter
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByRealityCompleteDateAfter(Calendar realityCompleteDate_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByRealityLabItem
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByRealityLabItem(Integer realityLabItem) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByRealityLabItem
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByRealityLabItem(Integer realityLabItem, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByConstructCondition
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByConstructCondition(String constructCondition) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByConstructCondition
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByConstructCondition(String constructCondition, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectAcceptanceApplications
	 *
	 */
	public Set<ProjectAcceptanceApplication> findAllProjectAcceptanceApplications() throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectAcceptanceApplications
	 *
	 */
	public Set<ProjectAcceptanceApplication> findAllProjectAcceptanceApplications(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByExpectLabItem
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByExpectLabItem(Integer expectLabItem) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByExpectLabItem
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByExpectLabItem(Integer expectLabItem, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByExpectCompleteDateAfter
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByExpectCompleteDateAfter(java.util.Calendar expectCompleteDate_2) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByExpectCompleteDateAfter
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByExpectCompleteDateAfter(Calendar expectCompleteDate_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByAppDateBefore
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByAppDateBefore(java.util.Calendar appDate) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByAppDateBefore
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByAppDateBefore(Calendar appDate, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByAppDateAfter
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByAppDateAfter(java.util.Calendar appDate_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByAppDateAfter
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByAppDateAfter(Calendar appDate_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByTargetLabroomAddContaining
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByTargetLabroomAddContaining(String targetLabroomAdd) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByTargetLabroomAddContaining
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByTargetLabroomAddContaining(String targetLabroomAdd, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByTargetLabroomItem
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByTargetLabroomItem(Integer targetLabroomItem) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByTargetLabroomItem
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByTargetLabroomItem(Integer targetLabroomItem, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByAppDate
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByAppDate(java.util.Calendar appDate_2) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByAppDate
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByAppDate(Calendar appDate_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOriginalLabroomAddContaining
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOriginalLabroomAddContaining(String originalLabroomAdd_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOriginalLabroomAddContaining
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOriginalLabroomAddContaining(String originalLabroomAdd_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationById
	 *
	 */
	public ProjectAcceptanceApplication findProjectAcceptanceApplicationById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationById
	 *
	 */
	public ProjectAcceptanceApplication findProjectAcceptanceApplicationById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByCourseNameContaining
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByCourseNameContaining(String courseName) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByCourseNameContaining
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByCourseNameContaining(String courseName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectStartDateAfter
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectStartDateAfter(java.util.Calendar projectStartDate_2) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectStartDateAfter
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectStartDateAfter(Calendar projectStartDate_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectSituation
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectSituation(String projectSituation) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectSituation
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectSituation(String projectSituation, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByTargetLabroomValue
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByTargetLabroomValue(java.math.BigDecimal targetLabroomValue) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByTargetLabroomValue
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByTargetLabroomValue(BigDecimal targetLabroomValue, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOriginalLabroomArea
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOriginalLabroomArea(java.math.BigDecimal originalLabroomArea) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOriginalLabroomArea
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOriginalLabroomArea(BigDecimal originalLabroomArea, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByMajorName
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByMajorName(String majorName_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByMajorName
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByMajorName(String majorName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByTargetLabroomAdd
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByTargetLabroomAdd(String targetLabroomAdd_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByTargetLabroomAdd
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByTargetLabroomAdd(String targetLabroomAdd_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByMajorAmount
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByMajorAmount(Integer majorAmount) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByMajorAmount
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByMajorAmount(Integer majorAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByCourseAmount
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByCourseAmount(Integer courseAmount) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByCourseAmount
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByCourseAmount(Integer courseAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByCourseName
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByCourseName(String courseName_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByCourseName
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByCourseName(String courseName_1, int startResult, int maxRows) throws DataAccessException;
	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectNameContaining
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectNameContaining(String projectName) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectNameContaining
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectNameContaining(String projectName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByFeeAmount
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByFeeAmount(java.math.BigDecimal feeAmount) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByFeeAmount
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByFeeAmount(BigDecimal feeAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOtherFee
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOtherFee(java.math.BigDecimal otherFee) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOtherFee
	 *
	 */
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOtherFee(BigDecimal otherFee, int startResult, int maxRows) throws DataAccessException;

}