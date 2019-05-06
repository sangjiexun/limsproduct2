package net.zjcclims.dao;

import net.zjcclims.domain.ProjectAcceptCompletionItem;
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

/**o
 * DAO to manage PrjectAcceptCompletionItem entities.
 * 
 */
@Repository("ProjectAcceptCompletionItemDAO")
@Transactional
public class ProjectAcceptCompletionItemDAOImpl extends AbstractJpaDao<ProjectAcceptCompletionItem>
		implements ProjectAcceptCompletionItemDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ProjectAcceptCompletionItem.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ProjectAcceptCompletionItemDAOImpl
	 *
	 */
	public ProjectAcceptCompletionItemDAOImpl() {
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
	 * JPQL Query - findProjectAcceptCompletionItemByIsOriginalContaining
	 *
	 */
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByIsOriginalContaining(String isOriginal) throws DataAccessException {

		return findProjectAcceptCompletionItemByIsOriginalContaining(isOriginal, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByIsOriginalContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByIsOriginalContaining(String isOriginal, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptCompletionItemByIsOriginalContaining", startResult, maxRows, isOriginal);
		return new LinkedHashSet<ProjectAcceptCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByPrimaryKey
	 *
	 */
	@Transactional
	public ProjectAcceptCompletionItem findProjectAcceptCompletionItemByPrimaryKey(Integer id) throws DataAccessException {

		return findProjectAcceptCompletionItemByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByPrimaryKey
	 *
	 */

	@Transactional
	public ProjectAcceptCompletionItem findProjectAcceptCompletionItemByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectAcceptCompletionItemByPrimaryKey", startResult, maxRows, id);
			return (ProjectAcceptCompletionItem) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByRequiredHour
	 *
	 */
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByRequiredHour(Integer requiredHour) throws DataAccessException {

		return findProjectAcceptCompletionItemByRequiredHour(requiredHour, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByRequiredHour
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByRequiredHour(Integer requiredHour, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptCompletionItemByRequiredHour", startResult, maxRows, requiredHour);
		return new LinkedHashSet<ProjectAcceptCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentPropertyContaining
	 *
	 */
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentPropertyContaining(String experimentProperty) throws DataAccessException {

		return findProjectAcceptCompletionItemByExperimentPropertyContaining(experimentProperty, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentPropertyContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentPropertyContaining(String experimentProperty, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptCompletionItemByExperimentPropertyContaining", startResult, maxRows, experimentProperty);
		return new LinkedHashSet<ProjectAcceptCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByUseSitutation
	 *
	 */
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByUseSitutation(Integer useSitutation) throws DataAccessException {

		return findProjectAcceptCompletionItemByUseSitutation(useSitutation, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByUseSitutation
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByUseSitutation(Integer useSitutation, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptCompletionItemByUseSitutation", startResult, maxRows, useSitutation);
		return new LinkedHashSet<ProjectAcceptCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllProjectAcceptCompletionItems
	 *
	 */
	@Transactional
	public Set<ProjectAcceptCompletionItem> findAllProjectAcceptCompletionItems() throws DataAccessException {

		return findAllProjectAcceptCompletionItems(-1, -1);
	}

	/**
	 * JPQL Query - findAllProjectAcceptCompletionItems
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptCompletionItem> findAllProjectAcceptCompletionItems(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllProjectAcceptCompletionItems", startResult, maxRows);
		return new LinkedHashSet<ProjectAcceptCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByPerGroupAmount
	 *
	 */
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByPerGroupAmount(Integer perGroupAmount) throws DataAccessException {

		return findProjectAcceptCompletionItemByPerGroupAmount(perGroupAmount, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByPerGroupAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByPerGroupAmount(Integer perGroupAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptCompletionItemByPerGroupAmount", startResult, maxRows, perGroupAmount);
		return new LinkedHashSet<ProjectAcceptCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentNameContaining
	 *
	 */
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentNameContaining(String experimentName) throws DataAccessException {

		return findProjectAcceptCompletionItemByExperimentNameContaining(experimentName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentNameContaining(String experimentName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptCompletionItemByExperimentNameContaining", startResult, maxRows, experimentName);
		return new LinkedHashSet<ProjectAcceptCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByIsOriginal
	 *
	 */
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByIsOriginal(String isOriginal) throws DataAccessException {

		return findProjectAcceptCompletionItemByIsOriginal(isOriginal, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByIsOriginal
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByIsOriginal(String isOriginal, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptCompletionItemByIsOriginal", startResult, maxRows, isOriginal);
		return new LinkedHashSet<ProjectAcceptCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemById
	 *
	 */
	@Transactional
	public ProjectAcceptCompletionItem findProjectAcceptCompletionItemById(Integer id) throws DataAccessException {

		return findProjectAcceptCompletionItemById(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemById
	 *
	 */

	@Transactional
	public ProjectAcceptCompletionItem findProjectAcceptCompletionItemById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectAcceptCompletionItemById", startResult, maxRows, id);
			return (ProjectAcceptCompletionItem) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByObjectItemContaining
	 *
	 */
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByObjectItemContaining(String objectItem) throws DataAccessException {

		return findProjectAcceptCompletionItemByObjectItemContaining(objectItem, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByObjectItemContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByObjectItemContaining(String objectItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptCompletionItemByObjectItemContaining", startResult, maxRows, objectItem);
		return new LinkedHashSet<ProjectAcceptCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByApprovalAppId
	 *
	 */
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByApprovalAppId(Integer approvalAppId) throws DataAccessException {

		return findProjectAcceptCompletionItemByApprovalAppId(approvalAppId, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByApprovalAppId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByApprovalAppId(Integer approvalAppId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptCompletionItemByApprovalAppId", startResult, maxRows, approvalAppId);
		return new LinkedHashSet<ProjectAcceptCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentName
	 *
	 */
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentName(String experimentName) throws DataAccessException {

		return findProjectAcceptCompletionItemByExperimentName(experimentName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentName(String experimentName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptCompletionItemByExperimentName", startResult, maxRows, experimentName);
		return new LinkedHashSet<ProjectAcceptCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemBySimultaneouslyAmount
	 *
	 */
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemBySimultaneouslyAmount(Integer simultaneouslyAmount) throws DataAccessException {

		return findProjectAcceptCompletionItemBySimultaneouslyAmount(simultaneouslyAmount, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemBySimultaneouslyAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemBySimultaneouslyAmount(Integer simultaneouslyAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptCompletionItemBySimultaneouslyAmount", startResult, maxRows, simultaneouslyAmount);
		return new LinkedHashSet<ProjectAcceptCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentProperty
	 *
	 */
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentProperty(String experimentProperty) throws DataAccessException {

		return findProjectAcceptCompletionItemByExperimentProperty(experimentProperty, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentProperty
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentProperty(String experimentProperty, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptCompletionItemByExperimentProperty", startResult, maxRows, experimentProperty);
		return new LinkedHashSet<ProjectAcceptCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByStartReportId
	 *
	 */
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByStartReportId(Integer startReportId) throws DataAccessException {

		return findProjectAcceptCompletionItemByStartReportId(startReportId, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByStartReportId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByStartReportId(Integer startReportId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptCompletionItemByStartReportId", startResult, maxRows, startReportId);
		return new LinkedHashSet<ProjectAcceptCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentInstructor
	 *
	 */
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentInstructor(Integer experimentInstructor) throws DataAccessException {

		return findProjectAcceptCompletionItemByExperimentInstructor(experimentInstructor, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentInstructor
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentInstructor(Integer experimentInstructor, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptCompletionItemByExperimentInstructor", startResult, maxRows, experimentInstructor);
		return new LinkedHashSet<ProjectAcceptCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByEquipmentAmount
	 *
	 */
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByEquipmentAmount(Integer equipmentAmount) throws DataAccessException {

		return findProjectAcceptCompletionItemByEquipmentAmount(equipmentAmount, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByEquipmentAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByEquipmentAmount(Integer equipmentAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptCompletionItemByEquipmentAmount", startResult, maxRows, equipmentAmount);
		return new LinkedHashSet<ProjectAcceptCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentOutline
	 *
	 */
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentOutline(Integer experimentOutline) throws DataAccessException {

		return findProjectAcceptCompletionItemByExperimentOutline(experimentOutline, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentOutline
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentOutline(Integer experimentOutline, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptCompletionItemByExperimentOutline", startResult, maxRows, experimentOutline);
		return new LinkedHashSet<ProjectAcceptCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByObjectItem
	 *
	 */
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByObjectItem(String objectItem) throws DataAccessException {

		return findProjectAcceptCompletionItemByObjectItem(objectItem, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByObjectItem
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByObjectItem(String objectItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptCompletionItemByObjectItem", startResult, maxRows, objectItem);
		return new LinkedHashSet<ProjectAcceptCompletionItem>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(ProjectAcceptCompletionItem entity) {
		return true;
	}
}
