package net.zjcclims.dao;


import net.zjcclims.domain.LabConstructApp;
import org.skyway.spring.util.dao.AbstractJpaDao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * DAO to manage LabConstructApp entities.
 * 
 */
@Repository("LabConstructAppDAO")
@Transactional
public class LabConstructAppDAOImpl extends AbstractJpaDao<LabConstructApp>
		implements LabConstructAppDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabConstructApp.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulims
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabConstructAppDAOImpl
	 *
	 */
	public LabConstructAppDAOImpl() {
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
	 * JPQL Query - findLabConstructAppByAppDate
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByAppDate(java.util.Calendar appDate) throws DataAccessException {

		return findLabConstructAppByAppDate(appDate, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByAppDate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByAppDate(java.util.Calendar appDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByAppDate", startResult, maxRows, appDate);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByPartyId
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByPartyId(Integer partyId) throws DataAccessException {

		return findLabConstructAppByPartyId(partyId, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByPartyId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByPartyId(Integer partyId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByPartyId", startResult, maxRows, partyId);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByOtherAppendix
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByOtherAppendix(String otherAppendix) throws DataAccessException {

		return findLabConstructAppByOtherAppendix(otherAppendix, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByOtherAppendix
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByOtherAppendix(String otherAppendix, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByOtherAppendix", startResult, maxRows, otherAppendix);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByApprovalAppendix
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByApprovalAppendix(String approvalAppendix) throws DataAccessException {

		return findLabConstructAppByApprovalAppendix(approvalAppendix, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByApprovalAppendix
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByApprovalAppendix(String approvalAppendix, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByApprovalAppendix", startResult, maxRows, approvalAppendix);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByConstructBasisContaining
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByConstructBasisContaining(String constructBasis) throws DataAccessException {

		return findLabConstructAppByConstructBasisContaining(constructBasis, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByConstructBasisContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByConstructBasisContaining(String constructBasis, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByConstructBasisContaining", startResult, maxRows, constructBasis);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByProjectNameContaining
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByProjectNameContaining(String projectName) throws DataAccessException {

		return findLabConstructAppByProjectNameContaining(projectName, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByProjectNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByProjectNameContaining(String projectName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByProjectNameContaining", startResult, maxRows, projectName);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByLabConstructAppCode
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByLabConstructAppCode(String labConstructAppCode) throws DataAccessException {

		return findLabConstructAppByLabConstructAppCode(labConstructAppCode, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByLabConstructAppCode
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByLabConstructAppCode(String labConstructAppCode, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByLabConstructAppCode", startResult, maxRows, labConstructAppCode);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByPrimaryKey
	 *
	 */
	@Transactional
	public LabConstructApp findLabConstructAppByPrimaryKey(Integer id) throws DataAccessException {

		return findLabConstructAppByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByPrimaryKey
	 *
	 */

	@Transactional
	public LabConstructApp findLabConstructAppByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabConstructAppByPrimaryKey", startResult, maxRows, id);
			return (LabConstructApp) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabConstructAppByProjectBasis
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByProjectBasis(String projectBasis) throws DataAccessException {

		return findLabConstructAppByProjectBasis(projectBasis, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByProjectBasis
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByProjectBasis(String projectBasis, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByProjectBasis", startResult, maxRows, projectBasis);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppBySpecialInnovationContaining
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppBySpecialInnovationContaining(String specialInnovation) throws DataAccessException {

		return findLabConstructAppBySpecialInnovationContaining(specialInnovation, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppBySpecialInnovationContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppBySpecialInnovationContaining(String specialInnovation, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppBySpecialInnovationContaining", startResult, maxRows, specialInnovation);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByApprovalAppendixContaining
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByApprovalAppendixContaining(String approvalAppendix) throws DataAccessException {

		return findLabConstructAppByApprovalAppendixContaining(approvalAppendix, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByApprovalAppendixContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByApprovalAppendixContaining(String approvalAppendix, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByApprovalAppendixContaining", startResult, maxRows, approvalAppendix);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByParticipant
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByParticipant(Integer participant) throws DataAccessException {

		return findLabConstructAppByParticipant(participant, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByParticipant
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByParticipant(Integer participant, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByParticipant", startResult, maxRows, participant);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByExpectedResultContaining
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByExpectedResultContaining(String expectedResult) throws DataAccessException {

		return findLabConstructAppByExpectedResultContaining(expectedResult, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByExpectedResultContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByExpectedResultContaining(String expectedResult, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByExpectedResultContaining", startResult, maxRows, expectedResult);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByLabConstructAppCodeContaining
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByLabConstructAppCodeContaining(String labConstructAppCode) throws DataAccessException {

		return findLabConstructAppByLabConstructAppCodeContaining(labConstructAppCode, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByLabConstructAppCodeContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByLabConstructAppCodeContaining(String labConstructAppCode, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByLabConstructAppCodeContaining", startResult, maxRows, labConstructAppCode);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByOpenLabItem
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByOpenLabItem(Integer openLabItem) throws DataAccessException {

		return findLabConstructAppByOpenLabItem(openLabItem, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByOpenLabItem
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByOpenLabItem(Integer openLabItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByOpenLabItem", startResult, maxRows, openLabItem);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByProjectName
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByProjectName(String projectName) throws DataAccessException {

		return findLabConstructAppByProjectName(projectName, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByProjectName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByProjectName(String projectName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByProjectName", startResult, maxRows, projectName);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByConstructBasis
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByConstructBasis(String constructBasis) throws DataAccessException {

		return findLabConstructAppByConstructBasis(constructBasis, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByConstructBasis
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByConstructBasis(String constructBasis, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByConstructBasis", startResult, maxRows, constructBasis);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByProjectBasisContaining
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByProjectBasisContaining(String projectBasis) throws DataAccessException {

		return findLabConstructAppByProjectBasisContaining(projectBasis, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByProjectBasisContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByProjectBasisContaining(String projectBasis, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByProjectBasisContaining", startResult, maxRows, projectBasis);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByEquipmentDetail
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByEquipmentDetail(Integer equipmentDetail) throws DataAccessException {

		return findLabConstructAppByEquipmentDetail(equipmentDetail, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByEquipmentDetail
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByEquipmentDetail(Integer equipmentDetail, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByEquipmentDetail", startResult, maxRows, equipmentDetail);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByOtherAppendixContaining
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByOtherAppendixContaining(String otherAppendix) throws DataAccessException {

		return findLabConstructAppByOtherAppendixContaining(otherAppendix, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByOtherAppendixContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByOtherAppendixContaining(String otherAppendix, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByOtherAppendixContaining", startResult, maxRows, otherAppendix);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByAppDateBefore
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByAppDateBefore(java.util.Calendar appDate) throws DataAccessException {

		return findLabConstructAppByAppDateBefore(appDate, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByAppDateBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByAppDateBefore(java.util.Calendar appDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByAppDateBefore", startResult, maxRows, appDate);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByAppropriationBudget
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByAppropriationBudget(Integer appropriationBudget) throws DataAccessException {

		return findLabConstructAppByAppropriationBudget(appropriationBudget, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByAppropriationBudget
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByAppropriationBudget(Integer appropriationBudget, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByAppropriationBudget", startResult, maxRows, appropriationBudget);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByPrimaryObjective
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByPrimaryObjective(String primaryObjective) throws DataAccessException {

		return findLabConstructAppByPrimaryObjective(primaryObjective, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByPrimaryObjective
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByPrimaryObjective(String primaryObjective, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByPrimaryObjective", startResult, maxRows, primaryObjective);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByExpectedResult
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByExpectedResult(String expectedResult) throws DataAccessException {

		return findLabConstructAppByExpectedResult(expectedResult, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByExpectedResult
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByExpectedResult(String expectedResult, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByExpectedResult", startResult, maxRows, expectedResult);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppBySpecialInnovation
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppBySpecialInnovation(String specialInnovation) throws DataAccessException {

		return findLabConstructAppBySpecialInnovation(specialInnovation, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppBySpecialInnovation
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppBySpecialInnovation(String specialInnovation, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppBySpecialInnovation", startResult, maxRows, specialInnovation);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByAppDateAfter
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByAppDateAfter(java.util.Calendar appDate) throws DataAccessException {

		return findLabConstructAppByAppDateAfter(appDate, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByAppDateAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByAppDateAfter(java.util.Calendar appDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByAppDateAfter", startResult, maxRows, appDate);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabConstructApps
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findAllLabConstructApps() throws DataAccessException {

		return findAllLabConstructApps(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabConstructApps
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findAllLabConstructApps(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabConstructApps", startResult, maxRows);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByPrimaryObjectiveContaining
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByPrimaryObjectiveContaining(String primaryObjective) throws DataAccessException {

		return findLabConstructAppByPrimaryObjectiveContaining(primaryObjective, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByPrimaryObjectiveContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByPrimaryObjectiveContaining(String primaryObjective, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByPrimaryObjectiveContaining", startResult, maxRows, primaryObjective);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}
	

	/**
	 * JPQL Query - findLabConstructAppByPlanSchedule
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByPlanSchedule(String planSchedule) throws DataAccessException {

		return findLabConstructAppByPlanSchedule(planSchedule, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByPlanSchedule
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByPlanSchedule(String planSchedule, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByPlanSchedule", startResult, maxRows, planSchedule);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	

	/**
	 * JPQL Query - findLabConstructAppById
	 *
	 */
	@Transactional
	public LabConstructApp findLabConstructAppById(Integer id) throws DataAccessException {

		return findLabConstructAppById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppById
	 *
	 */

	@Transactional
	public LabConstructApp findLabConstructAppById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabConstructAppById", startResult, maxRows, id);
			return (LabConstructApp) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}
	

	/**
	 * JPQL Query - findLabConstructAppByMajorAmount
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByMajorAmount(Integer majorAmount) throws DataAccessException {

		return findLabConstructAppByMajorAmount(majorAmount, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByMajorAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByMajorAmount(Integer majorAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByMajorAmount", startResult, maxRows, majorAmount);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByCourseAmount
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByCourseAmount(Integer courseAmount) throws DataAccessException {

		return findLabConstructAppByCourseAmount(courseAmount, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByCourseAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByCourseAmount(Integer courseAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByCourseAmount", startResult, maxRows, courseAmount);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}


	/**
	 * JPQL Query - findLabConstructAppByOtherFee
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByOtherFee(java.math.BigDecimal otherFee) throws DataAccessException {

		return findLabConstructAppByOtherFee(otherFee, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByOtherFee
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByOtherFee(java.math.BigDecimal otherFee, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByOtherFee", startResult, maxRows, otherFee);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByFeeAmount
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByFeeAmount(java.math.BigDecimal feeAmount) throws DataAccessException {

		return findLabConstructAppByFeeAmount(feeAmount, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByFeeAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByFeeAmount(java.math.BigDecimal feeAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByFeeAmount", startResult, maxRows, feeAmount);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByPurposeNameContaining
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByPurposeNameContaining(String purposeName) throws DataAccessException {

		return findLabConstructAppByPurposeNameContaining(purposeName, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByPurposeNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByPurposeNameContaining(String purposeName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByPurposeNameContaining", startResult, maxRows, purposeName);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}
	/**
	 * JPQL Query - findLabConstructAppByPurposeName
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByPurposeName(String purposeName) throws DataAccessException {

		return findLabConstructAppByPurposeName(purposeName, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByPurposeName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByPurposeName(String purposeName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByPurposeName", startResult, maxRows, purposeName);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructAppByMajorNameContaining
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByMajorNameContaining(String majorName) throws DataAccessException {

		return findLabConstructAppByMajorNameContaining(majorName, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByMajorNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByMajorNameContaining(String majorName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByMajorNameContaining", startResult, maxRows, majorName);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}
	
	/**
	 * JPQL Query - findLabConstructAppByMajorName
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByMajorName(String majorName) throws DataAccessException {

		return findLabConstructAppByMajorName(majorName, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByMajorName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByMajorName(String majorName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByMajorName", startResult, maxRows, majorName);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}
	
	/**
	 * JPQL Query - findLabConstructAppByCourseNameContaining
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByCourseNameContaining(String courseName) throws DataAccessException {

		return findLabConstructAppByCourseNameContaining(courseName, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByCourseNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByCourseNameContaining(String courseName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByCourseNameContaining", startResult, maxRows, courseName);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}
	/**
	 * JPQL Query - findLabConstructAppByCourseName
	 *
	 */
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByCourseName(String courseName) throws DataAccessException {

		return findLabConstructAppByCourseName(courseName, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructAppByCourseName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructApp> findLabConstructAppByCourseName(String courseName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructAppByCourseName", startResult, maxRows, courseName);
		return new LinkedHashSet<LabConstructApp>(query.getResultList());
	}
	
	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(LabConstructApp entity) {
		return true;
	}
}
