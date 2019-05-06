package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.SoftwareReserve;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage SoftwareReserve entities.
 * 
 */
@Repository("SoftwareReserveDAO")
@Transactional
public class SoftwareReserveDAOImpl extends AbstractJpaDao<SoftwareReserve>
		implements SoftwareReserveDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { SoftwareReserve.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new SoftwareReserveDAOImpl
	 *
	 */
	public SoftwareReserveDAOImpl() {
		super();
	}

	/**
	 * Get the entity manager that manages persistence unit 
	 *
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Returns the set of entity classes managed by this DAO.
	 *
	 */
	public Set<Class<?>> getTypes() {
		return dataTypes;
	}

	/**
	 * JPQL Query - findSoftwareReserveByApplyReason
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByApplyReason(String applyReason) throws DataAccessException {

		return findSoftwareReserveByApplyReason(applyReason, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByApplyReason
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByApplyReason(String applyReason, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByApplyReason", startResult, maxRows, applyReason);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByApplyReasonContaining
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByApplyReasonContaining(String applyReason) throws DataAccessException {

		return findSoftwareReserveByApplyReasonContaining(applyReason, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByApplyReasonContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByApplyReasonContaining(String applyReason, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByApplyReasonContaining", startResult, maxRows, applyReason);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveBySupplyPhoneContaining
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveBySupplyPhoneContaining(String supplyPhone) throws DataAccessException {

		return findSoftwareReserveBySupplyPhoneContaining(supplyPhone, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveBySupplyPhoneContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveBySupplyPhoneContaining(String supplyPhone, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveBySupplyPhoneContaining", startResult, maxRows, supplyPhone);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveById
	 *
	 */
	@Transactional
	public SoftwareReserve findSoftwareReserveById(Integer id) throws DataAccessException {

		return findSoftwareReserveById(id, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveById
	 *
	 */

	@Transactional
	public SoftwareReserve findSoftwareReserveById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSoftwareReserveById", startResult, maxRows, id);
			return (net.zjcclims.domain.SoftwareReserve) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findSoftwareReserveByTeacher
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByTeacher(String teacher) throws DataAccessException {

		return findSoftwareReserveByTeacher(teacher, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByTeacher
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByTeacher(String teacher, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByTeacher", startResult, maxRows, teacher);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveBySupplyPhone
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveBySupplyPhone(String supplyPhone) throws DataAccessException {

		return findSoftwareReserveBySupplyPhone(supplyPhone, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveBySupplyPhone
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveBySupplyPhone(String supplyPhone, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveBySupplyPhone", startResult, maxRows, supplyPhone);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByRemark
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByRemark(String remark) throws DataAccessException {

		return findSoftwareReserveByRemark(remark, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByRemark
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByRemark(String remark, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByRemark", startResult, maxRows, remark);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByCourse
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByCourse(String course) throws DataAccessException {

		return findSoftwareReserveByCourse(course, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByCourse
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByCourse(String course, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByCourse", startResult, maxRows, course);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByPhoneContaining
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByPhoneContaining(String phone) throws DataAccessException {

		return findSoftwareReserveByPhoneContaining(phone, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByPhoneContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByPhoneContaining(String phone, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByPhoneContaining", startResult, maxRows, phone);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByRequireTime
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByRequireTime(java.util.Calendar requireTime) throws DataAccessException {

		return findSoftwareReserveByRequireTime(requireTime, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByRequireTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByRequireTime(java.util.Calendar requireTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByRequireTime", startResult, maxRows, requireTime);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByApproveTime
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByApproveTime(java.util.Calendar approveTime) throws DataAccessException {

		return findSoftwareReserveByApproveTime(approveTime, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByApproveTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByApproveTime(java.util.Calendar approveTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByApproveTime", startResult, maxRows, approveTime);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByFrameContaining
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByFrameContaining(String frame) throws DataAccessException {

		return findSoftwareReserveByFrameContaining(frame, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByFrameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByFrameContaining(String frame, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByFrameContaining", startResult, maxRows, frame);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByTeacherContaining
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByTeacherContaining(String teacher) throws DataAccessException {

		return findSoftwareReserveByTeacherContaining(teacher, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByTeacherContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByTeacherContaining(String teacher, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByTeacherContaining", startResult, maxRows, teacher);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByPurpose
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByPurpose(String purpose) throws DataAccessException {

		return findSoftwareReserveByPurpose(purpose, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByPurpose
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByPurpose(String purpose, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByPurpose", startResult, maxRows, purpose);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByCreateTime
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByCreateTime(java.util.Calendar createTime) throws DataAccessException {

		return findSoftwareReserveByCreateTime(createTime, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByCreateTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByCreateTime(java.util.Calendar createTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByCreateTime", startResult, maxRows, createTime);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByApproceAdviceContaining
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByApproceAdviceContaining(String approceAdvice) throws DataAccessException {

		return findSoftwareReserveByApproceAdviceContaining(approceAdvice, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByApproceAdviceContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByApproceAdviceContaining(String approceAdvice, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByApproceAdviceContaining", startResult, maxRows, approceAdvice);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByRequirementContaining
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByRequirementContaining(String requirement) throws DataAccessException {

		return findSoftwareReserveByRequirementContaining(requirement, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByRequirementContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByRequirementContaining(String requirement, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByRequirementContaining", startResult, maxRows, requirement);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveBySupply
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveBySupply(String supply) throws DataAccessException {

		return findSoftwareReserveBySupply(supply, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveBySupply
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveBySupply(String supply, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveBySupply", startResult, maxRows, supply);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByApproveUserContaining
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByApproveUserContaining(String approveUser) throws DataAccessException {

		return findSoftwareReserveByApproveUserContaining(approveUser, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByApproveUserContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByApproveUserContaining(String approveUser, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByApproveUserContaining", startResult, maxRows, approveUser);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByRequirement
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByRequirement(String requirement) throws DataAccessException {

		return findSoftwareReserveByRequirement(requirement, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByRequirement
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByRequirement(String requirement, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByRequirement", startResult, maxRows, requirement);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByCd
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByCd(Boolean cd) throws DataAccessException {

		return findSoftwareReserveByCd(cd, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByCd
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByCd(Boolean cd, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByCd", startResult, maxRows, cd);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByCourseContaining
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByCourseContaining(String course) throws DataAccessException {

		return findSoftwareReserveByCourseContaining(course, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByCourseContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByCourseContaining(String course, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByCourseContaining", startResult, maxRows, course);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByPurposeContaining
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByPurposeContaining(String purpose) throws DataAccessException {

		return findSoftwareReserveByPurposeContaining(purpose, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByPurposeContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByPurposeContaining(String purpose, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByPurposeContaining", startResult, maxRows, purpose);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByApproveUser
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByApproveUser(String approveUser) throws DataAccessException {

		return findSoftwareReserveByApproveUser(approveUser, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByApproveUser
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByApproveUser(String approveUser, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByApproveUser", startResult, maxRows, approveUser);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByUserIdContaining
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByUserIdContaining(String userId) throws DataAccessException {

		return findSoftwareReserveByUserIdContaining(userId, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByUserIdContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByUserIdContaining(String userId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByUserIdContaining", startResult, maxRows, userId);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByProvideSoftware
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByProvideSoftware(Boolean provideSoftware) throws DataAccessException {

		return findSoftwareReserveByProvideSoftware(provideSoftware, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByProvideSoftware
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByProvideSoftware(Boolean provideSoftware, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByProvideSoftware", startResult, maxRows, provideSoftware);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByApproveState
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByApproveState(Integer approveState) throws DataAccessException {

		return findSoftwareReserveByApproveState(approveState, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByApproveState
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByApproveState(Integer approveState, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByApproveState", startResult, maxRows, approveState);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByNameContaining
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByNameContaining(String name) throws DataAccessException {

		return findSoftwareReserveByNameContaining(name, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByNameContaining(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByNameContaining", startResult, maxRows, name);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByName
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByName(String name) throws DataAccessException {

		return findSoftwareReserveByName(name, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByName(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByName", startResult, maxRows, name);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByUserId
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByUserId(String userId) throws DataAccessException {

		return findSoftwareReserveByUserId(userId, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByUserId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByUserId(String userId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByUserId", startResult, maxRows, userId);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllSoftwareReserves
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findAllSoftwareReserves() throws DataAccessException {

		return findAllSoftwareReserves(-1, -1);
	}

	/**
	 * JPQL Query - findAllSoftwareReserves
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findAllSoftwareReserves(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllSoftwareReserves", startResult, maxRows);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveBySupplyContaining
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveBySupplyContaining(String supply) throws DataAccessException {

		return findSoftwareReserveBySupplyContaining(supply, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveBySupplyContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveBySupplyContaining(String supply, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveBySupplyContaining", startResult, maxRows, supply);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByPhone
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByPhone(String phone) throws DataAccessException {

		return findSoftwareReserveByPhone(phone, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByPhone
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByPhone(String phone, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByPhone", startResult, maxRows, phone);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByLabId
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByLabId(Integer labId) throws DataAccessException {

		return findSoftwareReserveByLabId(labId, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByLabId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByLabId(Integer labId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByLabId", startResult, maxRows, labId);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByTerm
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByTerm(Integer term) throws DataAccessException {

		return findSoftwareReserveByTerm(term, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByTerm
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByTerm(Integer term, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByTerm", startResult, maxRows, term);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByFrame
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByFrame(String frame) throws DataAccessException {

		return findSoftwareReserveByFrame(frame, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByFrame
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByFrame(String frame, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByFrame", startResult, maxRows, frame);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByCode
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByCode(String code) throws DataAccessException {

		return findSoftwareReserveByCode(code, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByCode
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByCode(String code, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByCode", startResult, maxRows, code);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByRemarkContaining
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByRemarkContaining(String remark) throws DataAccessException {

		return findSoftwareReserveByRemarkContaining(remark, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByRemarkContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByRemarkContaining(String remark, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByRemarkContaining", startResult, maxRows, remark);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByApproceAdvice
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByApproceAdvice(String approceAdvice) throws DataAccessException {

		return findSoftwareReserveByApproceAdvice(approceAdvice, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByApproceAdvice
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByApproceAdvice(String approceAdvice, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByApproceAdvice", startResult, maxRows, approceAdvice);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByDongle
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByDongle(Boolean dongle) throws DataAccessException {

		return findSoftwareReserveByDongle(dongle, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByDongle
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByDongle(Boolean dongle, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByDongle", startResult, maxRows, dongle);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByCodeContaining
	 *
	 */
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByCodeContaining(String code) throws DataAccessException {

		return findSoftwareReserveByCodeContaining(code, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByCodeContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SoftwareReserve> findSoftwareReserveByCodeContaining(String code, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareReserveByCodeContaining", startResult, maxRows, code);
		return new LinkedHashSet<SoftwareReserve>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareReserveByPrimaryKey
	 *
	 */
	@Transactional
	public SoftwareReserve findSoftwareReserveByPrimaryKey(Integer id) throws DataAccessException {

		return findSoftwareReserveByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareReserveByPrimaryKey
	 *
	 */

	@Transactional
	public SoftwareReserve findSoftwareReserveByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSoftwareReserveByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.SoftwareReserve) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(SoftwareReserve entity) {
		return true;
	}
}
