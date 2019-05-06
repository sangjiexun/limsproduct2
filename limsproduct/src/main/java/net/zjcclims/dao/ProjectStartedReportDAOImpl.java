package net.zjcclims.dao;


import net.zjcclims.domain.ProjectStartedReport;
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
 * DAO to manage ProjectStartedReport entities.
 * 
 */
@Repository("ProjectStartedReportDAO")
@Transactional
public class ProjectStartedReportDAOImpl extends AbstractJpaDao<ProjectStartedReport>
		implements ProjectStartedReportDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ProjectStartedReport.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ProjectStartedReportDAOImpl
	 *
	 */
	public ProjectStartedReportDAOImpl() {
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
	 * JPQL Query - findProjectStartedReportByFeeApp
	 *
	 */
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByFeeApp(java.math.BigDecimal feeApp) throws DataAccessException {

		return findProjectStartedReportByFeeApp(feeApp, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartedReportByFeeApp
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByFeeApp(java.math.BigDecimal feeApp, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartedReportByFeeApp", startResult, maxRows, feeApp);
		return new LinkedHashSet<ProjectStartedReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartedReportByLabAddress
	 *
	 */
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByLabAddress(String labAddress) throws DataAccessException {

		return findProjectStartedReportByLabAddress(labAddress, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartedReportByLabAddress
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByLabAddress(String labAddress, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartedReportByLabAddress", startResult, maxRows, labAddress);
		return new LinkedHashSet<ProjectStartedReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartedReportById
	 *
	 */
	@Transactional
	public ProjectStartedReport findProjectStartedReportById(Integer id) throws DataAccessException {

		return findProjectStartedReportById(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartedReportById
	 *
	 */

	@Transactional
	public ProjectStartedReport findProjectStartedReportById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectStartedReportById", startResult, maxRows, id);
			return (ProjectStartedReport) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectStartedReportByOpenLabItem
	 *
	 */
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByOpenLabItem(Integer openLabItem) throws DataAccessException {

		return findProjectStartedReportByOpenLabItem(openLabItem, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartedReportByOpenLabItem
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByOpenLabItem(Integer openLabItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartedReportByOpenLabItem", startResult, maxRows, openLabItem);
		return new LinkedHashSet<ProjectStartedReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartedReportByLabArea
	 *
	 */
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByLabArea(java.math.BigDecimal labArea) throws DataAccessException {

		return findProjectStartedReportByLabArea(labArea, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartedReportByLabArea
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByLabArea(java.math.BigDecimal labArea, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartedReportByLabArea", startResult, maxRows, labArea);
		return new LinkedHashSet<ProjectStartedReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartedReportByFeeCodeContaining
	 *
	 */
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByFeeCodeContaining(String feeCode) throws DataAccessException {

		return findProjectStartedReportByFeeCodeContaining(feeCode, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartedReportByFeeCodeContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByFeeCodeContaining(String feeCode, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartedReportByFeeCodeContaining", startResult, maxRows, feeCode);
		return new LinkedHashSet<ProjectStartedReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartedReportByStartDate
	 *
	 */
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByStartDate(java.util.Calendar startDate) throws DataAccessException {

		return findProjectStartedReportByStartDate(startDate, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartedReportByStartDate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByStartDate(java.util.Calendar startDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartedReportByStartDate", startResult, maxRows, startDate);
		return new LinkedHashSet<ProjectStartedReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartedReportByPrimaryKey
	 *
	 */
	@Transactional
	public ProjectStartedReport findProjectStartedReportByPrimaryKey(Integer id) throws DataAccessException {

		return findProjectStartedReportByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartedReportByPrimaryKey
	 *
	 */

	@Transactional
	public ProjectStartedReport findProjectStartedReportByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectStartedReportByPrimaryKey", startResult, maxRows, id);
			return (ProjectStartedReport) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectStartedReportByProjectNameContaining
	 *
	 */
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByProjectNameContaining(String projectName) throws DataAccessException {

		return findProjectStartedReportByProjectNameContaining(projectName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartedReportByProjectNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByProjectNameContaining(String projectName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartedReportByProjectNameContaining", startResult, maxRows, projectName);
		return new LinkedHashSet<ProjectStartedReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartedReportByEquipmentList
	 *
	 */
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByEquipmentList(Integer equipmentList) throws DataAccessException {

		return findProjectStartedReportByEquipmentList(equipmentList, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartedReportByEquipmentList
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByEquipmentList(Integer equipmentList, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartedReportByEquipmentList", startResult, maxRows, equipmentList);
		return new LinkedHashSet<ProjectStartedReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartedReportByLabAddressContaining
	 *
	 */
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByLabAddressContaining(String labAddress) throws DataAccessException {

		return findProjectStartedReportByLabAddressContaining(labAddress, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartedReportByLabAddressContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByLabAddressContaining(String labAddress, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartedReportByLabAddressContaining", startResult, maxRows, labAddress);
		return new LinkedHashSet<ProjectStartedReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllProjectStartedReports
	 *
	 */
	@Transactional
	public Set<ProjectStartedReport> findAllProjectStartedReports() throws DataAccessException {

		return findAllProjectStartedReports(-1, -1);
	}

	/**
	 * JPQL Query - findAllProjectStartedReports
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartedReport> findAllProjectStartedReports(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllProjectStartedReports", startResult, maxRows);
		return new LinkedHashSet<ProjectStartedReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartedReportByStartDateAfter
	 *
	 */
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByStartDateAfter(java.util.Calendar startDate) throws DataAccessException {

		return findProjectStartedReportByStartDateAfter(startDate, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartedReportByStartDateAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByStartDateAfter(java.util.Calendar startDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartedReportByStartDateAfter", startResult, maxRows, startDate);
		return new LinkedHashSet<ProjectStartedReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartedReportByProjectName
	 *
	 */
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByProjectName(String projectName) throws DataAccessException {

		return findProjectStartedReportByProjectName(projectName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartedReportByProjectName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByProjectName(String projectName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartedReportByProjectName", startResult, maxRows, projectName);
		return new LinkedHashSet<ProjectStartedReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartedReportByFeeCode
	 *
	 */
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByFeeCode(String feeCode) throws DataAccessException {

		return findProjectStartedReportByFeeCode(feeCode, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartedReportByFeeCode
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByFeeCode(String feeCode, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartedReportByFeeCode", startResult, maxRows, feeCode);
		return new LinkedHashSet<ProjectStartedReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartedReportByStartDateBefore
	 *
	 */
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByStartDateBefore(java.util.Calendar startDate) throws DataAccessException {

		return findProjectStartedReportByStartDateBefore(startDate, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartedReportByStartDateBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByStartDateBefore(java.util.Calendar startDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartedReportByStartDateBefore", startResult, maxRows, startDate);
		return new LinkedHashSet<ProjectStartedReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartedReportByFeeApprovalDetail
	 *
	 */
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByFeeApprovalDetail(Integer feeApprovalDetail) throws DataAccessException {

		return findProjectStartedReportByFeeApprovalDetail(feeApprovalDetail, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartedReportByFeeApprovalDetail
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByFeeApprovalDetail(Integer feeApprovalDetail, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartedReportByFeeApprovalDetail", startResult, maxRows, feeApprovalDetail);
		return new LinkedHashSet<ProjectStartedReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartedReportByFeeAmount
	 *
	 */
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByFeeAmount(java.math.BigDecimal feeAmount) throws DataAccessException {

		return findProjectStartedReportByFeeAmount(feeAmount, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartedReportByFeeAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByFeeAmount(java.math.BigDecimal feeAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartedReportByFeeAmount", startResult, maxRows, feeAmount);
		return new LinkedHashSet<ProjectStartedReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartedReportByOtherFee
	 *
	 */
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByOtherFee(java.math.BigDecimal otherFee) throws DataAccessException {

		return findProjectStartedReportByOtherFee(otherFee, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartedReportByOtherFee
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartedReport> findProjectStartedReportByOtherFee(java.math.BigDecimal otherFee, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartedReportByOtherFee", startResult, maxRows, otherFee);
		return new LinkedHashSet<ProjectStartedReport>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(ProjectStartedReport entity) {
		return true;
	}
}
