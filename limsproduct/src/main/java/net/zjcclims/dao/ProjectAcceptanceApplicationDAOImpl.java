package net.zjcclims.dao;


import net.zjcclims.domain.ProjectAcceptanceApplication;
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
 * DAO to manage ProjectAcceptanceApplication entities.
 * 
 */
@Repository("ProjectAcceptanceApplicationDAO")
@Transactional
public class ProjectAcceptanceApplicationDAOImpl extends AbstractJpaDao<ProjectAcceptanceApplication>
		implements ProjectAcceptanceApplicationDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ProjectAcceptanceApplication.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ProjectAcceptanceApplicationDAOImpl
	 *
	 */
	public ProjectAcceptanceApplicationDAOImpl() {
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
	 * JPQL Query - findProjectAcceptanceApplicationByExpectCompleteDateBefore
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByExpectCompleteDateBefore(java.util.Calendar expectCompleteDate) throws DataAccessException {

		return findProjectAcceptanceApplicationByExpectCompleteDateBefore(expectCompleteDate, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByExpectCompleteDateBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByExpectCompleteDateBefore(java.util.Calendar expectCompleteDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByExpectCompleteDateBefore", startResult, maxRows, expectCompleteDate);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByRealityCompleteDate
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByRealityCompleteDate(java.util.Calendar realityCompleteDate) throws DataAccessException {

		return findProjectAcceptanceApplicationByRealityCompleteDate(realityCompleteDate, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByRealityCompleteDate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByRealityCompleteDate(java.util.Calendar realityCompleteDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByRealityCompleteDate", startResult, maxRows, realityCompleteDate);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOriginalLabroomItem
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOriginalLabroomItem(Integer originalLabroomItem) throws DataAccessException {

		return findProjectAcceptanceApplicationByOriginalLabroomItem(originalLabroomItem, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOriginalLabroomItem
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOriginalLabroomItem(Integer originalLabroomItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByOriginalLabroomItem", startResult, maxRows, originalLabroomItem);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByActualBenefits
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByActualBenefits(String actualBenefits) throws DataAccessException {

		return findProjectAcceptanceApplicationByActualBenefits(actualBenefits, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByActualBenefits
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByActualBenefits(String actualBenefits, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByActualBenefits", startResult, maxRows, actualBenefits);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByExpectCompleteDate
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByExpectCompleteDate(java.util.Calendar expectCompleteDate) throws DataAccessException {

		return findProjectAcceptanceApplicationByExpectCompleteDate(expectCompleteDate, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByExpectCompleteDate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByExpectCompleteDate(java.util.Calendar expectCompleteDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByExpectCompleteDate", startResult, maxRows, expectCompleteDate);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByRealityCompleteDateBefore
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByRealityCompleteDateBefore(java.util.Calendar realityCompleteDate) throws DataAccessException {

		return findProjectAcceptanceApplicationByRealityCompleteDateBefore(realityCompleteDate, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByRealityCompleteDateBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByRealityCompleteDateBefore(java.util.Calendar realityCompleteDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByRealityCompleteDateBefore", startResult, maxRows, realityCompleteDate);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByTargetLabroomArea
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByTargetLabroomArea(java.math.BigDecimal targetLabroomArea) throws DataAccessException {

		return findProjectAcceptanceApplicationByTargetLabroomArea(targetLabroomArea, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByTargetLabroomArea
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByTargetLabroomArea(java.math.BigDecimal targetLabroomArea, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByTargetLabroomArea", startResult, maxRows, targetLabroomArea);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOriginalLabroomAdd
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOriginalLabroomAdd(String originalLabroomAdd) throws DataAccessException {

		return findProjectAcceptanceApplicationByOriginalLabroomAdd(originalLabroomAdd, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOriginalLabroomAdd
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOriginalLabroomAdd(String originalLabroomAdd, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByOriginalLabroomAdd", startResult, maxRows, originalLabroomAdd);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByPrimaryKey
	 *
	 */
	@Transactional
	public ProjectAcceptanceApplication findProjectAcceptanceApplicationByPrimaryKey(Integer id) throws DataAccessException {

		return findProjectAcceptanceApplicationByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByPrimaryKey
	 *
	 */

	@Transactional
	public ProjectAcceptanceApplication findProjectAcceptanceApplicationByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectAcceptanceApplicationByPrimaryKey", startResult, maxRows, id);
			return (ProjectAcceptanceApplication) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectExpectedBenefits
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectExpectedBenefits(String projectExpectedBenefits) throws DataAccessException {

		return findProjectAcceptanceApplicationByProjectExpectedBenefits(projectExpectedBenefits, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectExpectedBenefits
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectExpectedBenefits(String projectExpectedBenefits, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByProjectExpectedBenefits", startResult, maxRows, projectExpectedBenefits);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectStartDateBefore
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectStartDateBefore(java.util.Calendar projectStartDate) throws DataAccessException {

		return findProjectAcceptanceApplicationByProjectStartDateBefore(projectStartDate, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectStartDateBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectStartDateBefore(java.util.Calendar projectStartDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByProjectStartDateBefore", startResult, maxRows, projectStartDate);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectStartDate
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectStartDate(java.util.Calendar projectStartDate) throws DataAccessException {

		return findProjectAcceptanceApplicationByProjectStartDate(projectStartDate, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectStartDate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectStartDate(java.util.Calendar projectStartDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByProjectStartDate", startResult, maxRows, projectStartDate);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByMajorNameContaining
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByMajorNameContaining(String majorName) throws DataAccessException {

		return findProjectAcceptanceApplicationByMajorNameContaining(majorName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByMajorNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByMajorNameContaining(String majorName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByMajorNameContaining", startResult, maxRows, majorName);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOpenLabItem
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOpenLabItem(Integer openLabItem) throws DataAccessException {

		return findProjectAcceptanceApplicationByOpenLabItem(openLabItem, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOpenLabItem
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOpenLabItem(Integer openLabItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByOpenLabItem", startResult, maxRows, openLabItem);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOriginalLabroomValue
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOriginalLabroomValue(java.math.BigDecimal originalLabroomValue) throws DataAccessException {

		return findProjectAcceptanceApplicationByOriginalLabroomValue(originalLabroomValue, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOriginalLabroomValue
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOriginalLabroomValue(java.math.BigDecimal originalLabroomValue, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByOriginalLabroomValue", startResult, maxRows, originalLabroomValue);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByRealityCompleteDateAfter
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByRealityCompleteDateAfter(java.util.Calendar realityCompleteDate) throws DataAccessException {

		return findProjectAcceptanceApplicationByRealityCompleteDateAfter(realityCompleteDate, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByRealityCompleteDateAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByRealityCompleteDateAfter(java.util.Calendar realityCompleteDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByRealityCompleteDateAfter", startResult, maxRows, realityCompleteDate);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByRealityLabItem
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByRealityLabItem(Integer realityLabItem) throws DataAccessException {

		return findProjectAcceptanceApplicationByRealityLabItem(realityLabItem, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByRealityLabItem
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByRealityLabItem(Integer realityLabItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByRealityLabItem", startResult, maxRows, realityLabItem);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByConstructCondition
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByConstructCondition(String constructCondition) throws DataAccessException {

		return findProjectAcceptanceApplicationByConstructCondition(constructCondition, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByConstructCondition
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByConstructCondition(String constructCondition, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByConstructCondition", startResult, maxRows, constructCondition);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllProjectAcceptanceApplications
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findAllProjectAcceptanceApplications() throws DataAccessException {

		return findAllProjectAcceptanceApplications(-1, -1);
	}

	/**
	 * JPQL Query - findAllProjectAcceptanceApplications
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findAllProjectAcceptanceApplications(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllProjectAcceptanceApplications", startResult, maxRows);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByExpectLabItem
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByExpectLabItem(Integer expectLabItem) throws DataAccessException {

		return findProjectAcceptanceApplicationByExpectLabItem(expectLabItem, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByExpectLabItem
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByExpectLabItem(Integer expectLabItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByExpectLabItem", startResult, maxRows, expectLabItem);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByExpectCompleteDateAfter
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByExpectCompleteDateAfter(java.util.Calendar expectCompleteDate) throws DataAccessException {

		return findProjectAcceptanceApplicationByExpectCompleteDateAfter(expectCompleteDate, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByExpectCompleteDateAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByExpectCompleteDateAfter(java.util.Calendar expectCompleteDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByExpectCompleteDateAfter", startResult, maxRows, expectCompleteDate);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByAppDateBefore
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByAppDateBefore(java.util.Calendar appDate) throws DataAccessException {

		return findProjectAcceptanceApplicationByAppDateBefore(appDate, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByAppDateBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByAppDateBefore(java.util.Calendar appDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByAppDateBefore", startResult, maxRows, appDate);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByAppDateAfter
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByAppDateAfter(java.util.Calendar appDate) throws DataAccessException {

		return findProjectAcceptanceApplicationByAppDateAfter(appDate, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByAppDateAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByAppDateAfter(java.util.Calendar appDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByAppDateAfter", startResult, maxRows, appDate);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByTargetLabroomAddContaining
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByTargetLabroomAddContaining(String targetLabroomAdd) throws DataAccessException {

		return findProjectAcceptanceApplicationByTargetLabroomAddContaining(targetLabroomAdd, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByTargetLabroomAddContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByTargetLabroomAddContaining(String targetLabroomAdd, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByTargetLabroomAddContaining", startResult, maxRows, targetLabroomAdd);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByTargetLabroomItem
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByTargetLabroomItem(Integer targetLabroomItem) throws DataAccessException {

		return findProjectAcceptanceApplicationByTargetLabroomItem(targetLabroomItem, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByTargetLabroomItem
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByTargetLabroomItem(Integer targetLabroomItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByTargetLabroomItem", startResult, maxRows, targetLabroomItem);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByAppDate
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByAppDate(java.util.Calendar appDate) throws DataAccessException {

		return findProjectAcceptanceApplicationByAppDate(appDate, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByAppDate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByAppDate(java.util.Calendar appDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByAppDate", startResult, maxRows, appDate);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOriginalLabroomAddContaining
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOriginalLabroomAddContaining(String originalLabroomAdd) throws DataAccessException {

		return findProjectAcceptanceApplicationByOriginalLabroomAddContaining(originalLabroomAdd, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOriginalLabroomAddContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOriginalLabroomAddContaining(String originalLabroomAdd, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByOriginalLabroomAddContaining", startResult, maxRows, originalLabroomAdd);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationById
	 *
	 */
	@Transactional
	public ProjectAcceptanceApplication findProjectAcceptanceApplicationById(Integer id) throws DataAccessException {

		return findProjectAcceptanceApplicationById(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationById
	 *
	 */

	@Transactional
	public ProjectAcceptanceApplication findProjectAcceptanceApplicationById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectAcceptanceApplicationById", startResult, maxRows, id);
			return (ProjectAcceptanceApplication) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByCourseNameContaining
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByCourseNameContaining(String courseName) throws DataAccessException {

		return findProjectAcceptanceApplicationByCourseNameContaining(courseName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByCourseNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByCourseNameContaining(String courseName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByCourseNameContaining", startResult, maxRows, courseName);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectStartDateAfter
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectStartDateAfter(java.util.Calendar projectStartDate) throws DataAccessException {

		return findProjectAcceptanceApplicationByProjectStartDateAfter(projectStartDate, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectStartDateAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectStartDateAfter(java.util.Calendar projectStartDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByProjectStartDateAfter", startResult, maxRows, projectStartDate);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectSituation
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectSituation(String projectSituation) throws DataAccessException {

		return findProjectAcceptanceApplicationByProjectSituation(projectSituation, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectSituation
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectSituation(String projectSituation, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByProjectSituation", startResult, maxRows, projectSituation);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByTargetLabroomValue
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByTargetLabroomValue(java.math.BigDecimal targetLabroomValue) throws DataAccessException {

		return findProjectAcceptanceApplicationByTargetLabroomValue(targetLabroomValue, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByTargetLabroomValue
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByTargetLabroomValue(java.math.BigDecimal targetLabroomValue, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByTargetLabroomValue", startResult, maxRows, targetLabroomValue);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOriginalLabroomArea
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOriginalLabroomArea(java.math.BigDecimal originalLabroomArea) throws DataAccessException {

		return findProjectAcceptanceApplicationByOriginalLabroomArea(originalLabroomArea, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOriginalLabroomArea
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOriginalLabroomArea(java.math.BigDecimal originalLabroomArea, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByOriginalLabroomArea", startResult, maxRows, originalLabroomArea);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByMajorName
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByMajorName(String majorName) throws DataAccessException {

		return findProjectAcceptanceApplicationByMajorName(majorName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByMajorName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByMajorName(String majorName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByMajorName", startResult, maxRows, majorName);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByTargetLabroomAdd
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByTargetLabroomAdd(String targetLabroomAdd) throws DataAccessException {

		return findProjectAcceptanceApplicationByTargetLabroomAdd(targetLabroomAdd, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByTargetLabroomAdd
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByTargetLabroomAdd(String targetLabroomAdd, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByTargetLabroomAdd", startResult, maxRows, targetLabroomAdd);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByMajorAmount
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByMajorAmount(Integer majorAmount) throws DataAccessException {

		return findProjectAcceptanceApplicationByMajorAmount(majorAmount, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByMajorAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByMajorAmount(Integer majorAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByMajorAmount", startResult, maxRows, majorAmount);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByCourseAmount
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByCourseAmount(Integer courseAmount) throws DataAccessException {

		return findProjectAcceptanceApplicationByCourseAmount(courseAmount, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByCourseAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByCourseAmount(Integer courseAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByCourseAmount", startResult, maxRows, courseAmount);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByCourseName
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByCourseName(String courseName) throws DataAccessException {

		return findProjectAcceptanceApplicationByCourseName(courseName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByCourseName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByCourseName(String courseName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByCourseName", startResult, maxRows, courseName);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}
	
	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectNameContaining
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectNameContaining(String projectName) throws DataAccessException {

		return findProjectAcceptanceApplicationByProjectNameContaining(projectName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByProjectNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProjectNameContaining(String projectName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByProjectNameContaining", startResult, maxRows, projectName);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}
	
	/**
	 * JPQL Query - findProjectAcceptanceApplicationByFeeAmount
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByFeeAmount(java.math.BigDecimal feeAmount) throws DataAccessException {

		return findProjectAcceptanceApplicationByFeeAmount(feeAmount, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByFeeAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByFeeAmount(java.math.BigDecimal feeAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByFeeAmount", startResult, maxRows, feeAmount);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}
	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOtherFee
	 *
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOtherFee(java.math.BigDecimal otherFee) throws DataAccessException {

		return findProjectAcceptanceApplicationByOtherFee(otherFee, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptanceApplicationByOtherFee
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByOtherFee(java.math.BigDecimal otherFee, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptanceApplicationByOtherFee", startResult, maxRows, otherFee);
		return new LinkedHashSet<ProjectAcceptanceApplication>(query.getResultList());
	}


	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(ProjectAcceptanceApplication entity) {
		return true;
	}
}
