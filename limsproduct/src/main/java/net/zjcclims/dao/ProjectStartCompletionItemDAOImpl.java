package net.zjcclims.dao;


import net.zjcclims.domain.ProjectStartCompletionItem;
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
 * DAO to manage ProjectStartCompletionItem entities.
 * 
 */
@Repository("ProjectStartCompletionItemDAO")
@Transactional
public class ProjectStartCompletionItemDAOImpl extends AbstractJpaDao<ProjectStartCompletionItem>
		implements ProjectStartCompletionItemDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ProjectStartCompletionItem.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ProjectStartCompletionItemDAOImpl
	 *
	 */
	public ProjectStartCompletionItemDAOImpl() {
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
	 * JPQL Query - findProjectStartCompletionItemByRequiredHour
	 *
	 */
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByRequiredHour(Integer requiredHour) throws DataAccessException {

		return findProjectStartCompletionItemByRequiredHour(requiredHour, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByRequiredHour
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByRequiredHour(Integer requiredHour, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartCompletionItemByRequiredHour", startResult, maxRows, requiredHour);
		return new LinkedHashSet<ProjectStartCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentPropertyContaining
	 *
	 */
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentPropertyContaining(String experimentProperty) throws DataAccessException {

		return findProjectStartCompletionItemByExperimentPropertyContaining(experimentProperty, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentPropertyContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentPropertyContaining(String experimentProperty, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartCompletionItemByExperimentPropertyContaining", startResult, maxRows, experimentProperty);
		return new LinkedHashSet<ProjectStartCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentProperty
	 *
	 */
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentProperty(String experimentProperty) throws DataAccessException {

		return findProjectStartCompletionItemByExperimentProperty(experimentProperty, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentProperty
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentProperty(String experimentProperty, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartCompletionItemByExperimentProperty", startResult, maxRows, experimentProperty);
		return new LinkedHashSet<ProjectStartCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByIsOriginal
	 *
	 */
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByIsOriginal(String isOriginal) throws DataAccessException {

		return findProjectStartCompletionItemByIsOriginal(isOriginal, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByIsOriginal
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByIsOriginal(String isOriginal, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartCompletionItemByIsOriginal", startResult, maxRows, isOriginal);
		return new LinkedHashSet<ProjectStartCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllProjectStartCompletionItems
	 *
	 */
	@Transactional
	public Set<ProjectStartCompletionItem> findAllProjectStartCompletionItems() throws DataAccessException {

		return findAllProjectStartCompletionItems(-1, -1);
	}

	/**
	 * JPQL Query - findAllProjectStartCompletionItems
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartCompletionItem> findAllProjectStartCompletionItems(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllProjectStartCompletionItems", startResult, maxRows);
		return new LinkedHashSet<ProjectStartCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByUseSitutation
	 *
	 */
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByUseSitutation(Integer useSitutation) throws DataAccessException {

		return findProjectStartCompletionItemByUseSitutation(useSitutation, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByUseSitutation
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByUseSitutation(Integer useSitutation, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartCompletionItemByUseSitutation", startResult, maxRows, useSitutation);
		return new LinkedHashSet<ProjectStartCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByApprovalAppId
	 *
	 */
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByApprovalAppId(Integer approvalAppId) throws DataAccessException {

		return findProjectStartCompletionItemByApprovalAppId(approvalAppId, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByApprovalAppId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByApprovalAppId(Integer approvalAppId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartCompletionItemByApprovalAppId", startResult, maxRows, approvalAppId);
		return new LinkedHashSet<ProjectStartCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByPrimaryKey
	 *
	 */
	@Transactional
	public ProjectStartCompletionItem findProjectStartCompletionItemByPrimaryKey(Integer id) throws DataAccessException {

		return findProjectStartCompletionItemByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByPrimaryKey
	 *
	 */

	@Transactional
	public ProjectStartCompletionItem findProjectStartCompletionItemByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectStartCompletionItemByPrimaryKey", startResult, maxRows, id);
			return (ProjectStartCompletionItem) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByStartReportId
	 *
	 */
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByStartReportId(Integer startReportId) throws DataAccessException {

		return findProjectStartCompletionItemByStartReportId(startReportId, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByStartReportId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByStartReportId(Integer startReportId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartCompletionItemByStartReportId", startResult, maxRows, startReportId);
		return new LinkedHashSet<ProjectStartCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByEquipmentAmount
	 *
	 */
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByEquipmentAmount(Integer equipmentAmount) throws DataAccessException {

		return findProjectStartCompletionItemByEquipmentAmount(equipmentAmount, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByEquipmentAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByEquipmentAmount(Integer equipmentAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartCompletionItemByEquipmentAmount", startResult, maxRows, equipmentAmount);
		return new LinkedHashSet<ProjectStartCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByObjectItemContaining
	 *
	 */
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByObjectItemContaining(String objectItem) throws DataAccessException {

		return findProjectStartCompletionItemByObjectItemContaining(objectItem, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByObjectItemContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByObjectItemContaining(String objectItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartCompletionItemByObjectItemContaining", startResult, maxRows, objectItem);
		return new LinkedHashSet<ProjectStartCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemBySimultaneouslyAmount
	 *
	 */
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemBySimultaneouslyAmount(Integer simultaneouslyAmount) throws DataAccessException {

		return findProjectStartCompletionItemBySimultaneouslyAmount(simultaneouslyAmount, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemBySimultaneouslyAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemBySimultaneouslyAmount(Integer simultaneouslyAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartCompletionItemBySimultaneouslyAmount", startResult, maxRows, simultaneouslyAmount);
		return new LinkedHashSet<ProjectStartCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByIsOriginalContaining
	 *
	 */
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByIsOriginalContaining(String isOriginal) throws DataAccessException {

		return findProjectStartCompletionItemByIsOriginalContaining(isOriginal, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByIsOriginalContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByIsOriginalContaining(String isOriginal, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartCompletionItemByIsOriginalContaining", startResult, maxRows, isOriginal);
		return new LinkedHashSet<ProjectStartCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentOutline
	 *
	 */
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentOutline(Integer experimentOutline) throws DataAccessException {

		return findProjectStartCompletionItemByExperimentOutline(experimentOutline, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentOutline
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentOutline(Integer experimentOutline, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartCompletionItemByExperimentOutline", startResult, maxRows, experimentOutline);
		return new LinkedHashSet<ProjectStartCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByObjectItem
	 *
	 */
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByObjectItem(String objectItem) throws DataAccessException {

		return findProjectStartCompletionItemByObjectItem(objectItem, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByObjectItem
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByObjectItem(String objectItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartCompletionItemByObjectItem", startResult, maxRows, objectItem);
		return new LinkedHashSet<ProjectStartCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentName
	 *
	 */
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentName(String experimentName) throws DataAccessException {

		return findProjectStartCompletionItemByExperimentName(experimentName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentName(String experimentName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartCompletionItemByExperimentName", startResult, maxRows, experimentName);
		return new LinkedHashSet<ProjectStartCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByPerGroupAmount
	 *
	 */
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByPerGroupAmount(Integer perGroupAmount) throws DataAccessException {

		return findProjectStartCompletionItemByPerGroupAmount(perGroupAmount, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByPerGroupAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByPerGroupAmount(Integer perGroupAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartCompletionItemByPerGroupAmount", startResult, maxRows, perGroupAmount);
		return new LinkedHashSet<ProjectStartCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentNameContaining
	 *
	 */
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentNameContaining(String experimentName) throws DataAccessException {

		return findProjectStartCompletionItemByExperimentNameContaining(experimentName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentNameContaining(String experimentName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartCompletionItemByExperimentNameContaining", startResult, maxRows, experimentName);
		return new LinkedHashSet<ProjectStartCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemById
	 *
	 */
	@Transactional
	public ProjectStartCompletionItem findProjectStartCompletionItemById(Integer id) throws DataAccessException {

		return findProjectStartCompletionItemById(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemById
	 *
	 */

	@Transactional
	public ProjectStartCompletionItem findProjectStartCompletionItemById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectStartCompletionItemById", startResult, maxRows, id);
			return (ProjectStartCompletionItem) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentInstructor
	 *
	 */
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentInstructor(Integer experimentInstructor) throws DataAccessException {

		return findProjectStartCompletionItemByExperimentInstructor(experimentInstructor, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentInstructor
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentInstructor(Integer experimentInstructor, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartCompletionItemByExperimentInstructor", startResult, maxRows, experimentInstructor);
		return new LinkedHashSet<ProjectStartCompletionItem>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 *
	 *
	 */
	public boolean canBeMerged(ProjectStartCompletionItem entity) {
		return true;
	}
}
