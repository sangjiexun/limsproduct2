package net.zjcclims.dao;

import java.util.Calendar;
import java.util.Set;

import net.zjcclims.domain.SoftwareReserve;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage SoftwareReserve entities.
 * 
 */
public interface SoftwareReserveDAO extends JpaDao<SoftwareReserve> {

	/**
	 * JPQL Query - findSoftwareReserveByApplyReason
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByApplyReason(String applyReason) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByApplyReason
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByApplyReason(String applyReason, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByApplyReasonContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByApplyReasonContaining(String applyReason_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByApplyReasonContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByApplyReasonContaining(String applyReason_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveBySupplyPhoneContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveBySupplyPhoneContaining(String supplyPhone) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveBySupplyPhoneContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveBySupplyPhoneContaining(String supplyPhone, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveById
	 *
	 */
	public SoftwareReserve findSoftwareReserveById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveById
	 *
	 */
	public SoftwareReserve findSoftwareReserveById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByTeacher
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByTeacher(String teacher) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByTeacher
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByTeacher(String teacher, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveBySupplyPhone
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveBySupplyPhone(String supplyPhone_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveBySupplyPhone
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveBySupplyPhone(String supplyPhone_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByRemark
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByRemark(String remark) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByRemark
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByRemark(String remark, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByCourse
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByCourse(String course) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByCourse
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByCourse(String course, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByPhoneContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByPhoneContaining(String phone) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByPhoneContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByPhoneContaining(String phone, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByRequireTime
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByRequireTime(java.util.Calendar requireTime) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByRequireTime
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByRequireTime(Calendar requireTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByApproveTime
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByApproveTime(java.util.Calendar approveTime) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByApproveTime
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByApproveTime(Calendar approveTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByFrameContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByFrameContaining(String frame) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByFrameContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByFrameContaining(String frame, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByTeacherContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByTeacherContaining(String teacher_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByTeacherContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByTeacherContaining(String teacher_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByPurpose
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByPurpose(String purpose) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByPurpose
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByPurpose(String purpose, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByCreateTime
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByCreateTime(java.util.Calendar createTime) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByCreateTime
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByCreateTime(Calendar createTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByApproceAdviceContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByApproceAdviceContaining(String approceAdvice) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByApproceAdviceContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByApproceAdviceContaining(String approceAdvice, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByRequirementContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByRequirementContaining(String requirement) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByRequirementContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByRequirementContaining(String requirement, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveBySupply
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveBySupply(String supply) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveBySupply
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveBySupply(String supply, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByApproveUserContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByApproveUserContaining(String approveUser) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByApproveUserContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByApproveUserContaining(String approveUser, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByRequirement
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByRequirement(String requirement_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByRequirement
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByRequirement(String requirement_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByCd
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByCd(Boolean cd) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByCd
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByCd(Boolean cd, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByCourseContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByCourseContaining(String course_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByCourseContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByCourseContaining(String course_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByPurposeContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByPurposeContaining(String purpose_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByPurposeContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByPurposeContaining(String purpose_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByApproveUser
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByApproveUser(String approveUser_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByApproveUser
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByApproveUser(String approveUser_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByUserIdContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByUserIdContaining(String userId) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByUserIdContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByUserIdContaining(String userId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByProvideSoftware
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByProvideSoftware(Boolean provideSoftware) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByProvideSoftware
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByProvideSoftware(Boolean provideSoftware, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByApproveState
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByApproveState(Integer approveState) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByApproveState
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByApproveState(Integer approveState, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByNameContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByNameContaining(String name) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByNameContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByNameContaining(String name, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByName
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByName(String name_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByName
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByName(String name_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByUserId
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByUserId(String userId_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByUserId
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByUserId(String userId_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllSoftwareReserves
	 *
	 */
	public Set<SoftwareReserve> findAllSoftwareReserves() throws DataAccessException;

	/**
	 * JPQL Query - findAllSoftwareReserves
	 *
	 */
	public Set<SoftwareReserve> findAllSoftwareReserves(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveBySupplyContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveBySupplyContaining(String supply_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveBySupplyContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveBySupplyContaining(String supply_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByPhone
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByPhone(String phone_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByPhone
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByPhone(String phone_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByLabId
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByLabId(Integer labId) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByLabId
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByLabId(Integer labId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByTerm
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByTerm(Integer term) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByTerm
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByTerm(Integer term, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByFrame
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByFrame(String frame_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByFrame
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByFrame(String frame_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByCode
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByCode(String code) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByCode
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByCode(String code, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByRemarkContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByRemarkContaining(String remark_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByRemarkContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByRemarkContaining(String remark_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByApproceAdvice
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByApproceAdvice(String approceAdvice_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByApproceAdvice
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByApproceAdvice(String approceAdvice_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByDongle
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByDongle(Boolean dongle) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByDongle
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByDongle(Boolean dongle, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByCodeContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByCodeContaining(String code_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByCodeContaining
	 *
	 */
	public Set<SoftwareReserve> findSoftwareReserveByCodeContaining(String code_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByPrimaryKey
	 *
	 */
	public SoftwareReserve findSoftwareReserveByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveByPrimaryKey
	 *
	 */
	public SoftwareReserve findSoftwareReserveByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}