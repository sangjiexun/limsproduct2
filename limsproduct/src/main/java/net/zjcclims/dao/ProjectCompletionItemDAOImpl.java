package net.zjcclims.dao;


import net.zjcclims.domain.ProjectCompletionItem;
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
 * DAO to manage ProjectCompletionItem entities.
 * 
 */
@Repository("ProjectCompletionItemDAO")
@Transactional
public class ProjectCompletionItemDAOImpl extends AbstractJpaDao<ProjectCompletionItem>
		implements ProjectCompletionItemDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ProjectCompletionItem.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulims
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ProjectCompletionItemDAOImpl
	 *
	 */
	public ProjectCompletionItemDAOImpl() {
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
	 * JPQL Query - findProjectCompletionItemByExperimentPropertyContaining
	 *
	 */
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentPropertyContaining(String experimentProperty) throws DataAccessException {

		return findProjectCompletionItemByExperimentPropertyContaining(experimentProperty, -1, -1);
	}

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentPropertyContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentPropertyContaining(String experimentProperty, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectCompletionItemByExperimentPropertyContaining", startResult, maxRows, experimentProperty);
		return new LinkedHashSet<ProjectCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectCompletionItemByPrimaryKey
	 *
	 */
	@Transactional
	public ProjectCompletionItem findProjectCompletionItemByPrimaryKey(Integer id) throws DataAccessException {

		return findProjectCompletionItemByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectCompletionItemByPrimaryKey
	 *
	 */

	@Transactional
	public ProjectCompletionItem findProjectCompletionItemByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectCompletionItemByPrimaryKey", startResult, maxRows, id);
			return (ProjectCompletionItem) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllProjectCompletionItems
	 *
	 */
	@Transactional
	public Set<ProjectCompletionItem> findAllProjectCompletionItems() throws DataAccessException {

		return findAllProjectCompletionItems(-1, -1);
	}

	/**
	 * JPQL Query - findAllProjectCompletionItems
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectCompletionItem> findAllProjectCompletionItems(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllProjectCompletionItems", startResult, maxRows);
		return new LinkedHashSet<ProjectCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectCompletionItemByUseSitutation
	 *
	 */
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByUseSitutation(Integer useSitutation) throws DataAccessException {

		return findProjectCompletionItemByUseSitutation(useSitutation, -1, -1);
	}

	/**
	 * JPQL Query - findProjectCompletionItemByUseSitutation
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByUseSitutation(Integer useSitutation, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectCompletionItemByUseSitutation", startResult, maxRows, useSitutation);
		return new LinkedHashSet<ProjectCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectCompletionItemByObjectItemContaining
	 *
	 */
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByObjectItemContaining(String objectItem) throws DataAccessException {

		return findProjectCompletionItemByObjectItemContaining(objectItem, -1, -1);
	}

	/**
	 * JPQL Query - findProjectCompletionItemByObjectItemContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByObjectItemContaining(String objectItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectCompletionItemByObjectItemContaining", startResult, maxRows, objectItem);
		return new LinkedHashSet<ProjectCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentName
	 *
	 */
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentName(String experimentName) throws DataAccessException {

		return findProjectCompletionItemByExperimentName(experimentName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentName(String experimentName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectCompletionItemByExperimentName", startResult, maxRows, experimentName);
		return new LinkedHashSet<ProjectCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectCompletionItemById
	 *
	 */
	@Transactional
	public ProjectCompletionItem findProjectCompletionItemById(Integer id) throws DataAccessException {

		return findProjectCompletionItemById(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectCompletionItemById
	 *
	 */

	@Transactional
	public ProjectCompletionItem findProjectCompletionItemById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectCompletionItemById", startResult, maxRows, id);
			return (ProjectCompletionItem) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentNameContaining
	 *
	 */
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentNameContaining(String experimentName) throws DataAccessException {

		return findProjectCompletionItemByExperimentNameContaining(experimentName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentNameContaining(String experimentName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectCompletionItemByExperimentNameContaining", startResult, maxRows, experimentName);
		return new LinkedHashSet<ProjectCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectCompletionItemByPerGroupAmount
	 *
	 */
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByPerGroupAmount(Integer perGroupAmount) throws DataAccessException {

		return findProjectCompletionItemByPerGroupAmount(perGroupAmount, -1, -1);
	}

	/**
	 * JPQL Query - findProjectCompletionItemByPerGroupAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByPerGroupAmount(Integer perGroupAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectCompletionItemByPerGroupAmount", startResult, maxRows, perGroupAmount);
		return new LinkedHashSet<ProjectCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectCompletionItemByEquipmentAmount
	 *
	 */
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByEquipmentAmount(Integer equipmentAmount) throws DataAccessException {

		return findProjectCompletionItemByEquipmentAmount(equipmentAmount, -1, -1);
	}

	/**
	 * JPQL Query - findProjectCompletionItemByEquipmentAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByEquipmentAmount(Integer equipmentAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectCompletionItemByEquipmentAmount", startResult, maxRows, equipmentAmount);
		return new LinkedHashSet<ProjectCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentInstructor
	 *
	 */
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentInstructor(Integer experimentInstructor) throws DataAccessException {

		return findProjectCompletionItemByExperimentInstructor(experimentInstructor, -1, -1);
	}

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentInstructor
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentInstructor(Integer experimentInstructor, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectCompletionItemByExperimentInstructor", startResult, maxRows, experimentInstructor);
		return new LinkedHashSet<ProjectCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentOutline
	 *
	 */
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentOutline(Integer experimentOutline) throws DataAccessException {

		return findProjectCompletionItemByExperimentOutline(experimentOutline, -1, -1);
	}

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentOutline
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentOutline(Integer experimentOutline, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectCompletionItemByExperimentOutline", startResult, maxRows, experimentOutline);
		return new LinkedHashSet<ProjectCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentProperty
	 *
	 */
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentProperty(String experimentProperty) throws DataAccessException {

		return findProjectCompletionItemByExperimentProperty(experimentProperty, -1, -1);
	}

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentProperty
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentProperty(String experimentProperty, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectCompletionItemByExperimentProperty", startResult, maxRows, experimentProperty);
		return new LinkedHashSet<ProjectCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectCompletionItemBySimultaneouslyAmount
	 *
	 */
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemBySimultaneouslyAmount(Integer simultaneouslyAmount) throws DataAccessException {

		return findProjectCompletionItemBySimultaneouslyAmount(simultaneouslyAmount, -1, -1);
	}

	/**
	 * JPQL Query - findProjectCompletionItemBySimultaneouslyAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemBySimultaneouslyAmount(Integer simultaneouslyAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectCompletionItemBySimultaneouslyAmount", startResult, maxRows, simultaneouslyAmount);
		return new LinkedHashSet<ProjectCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectCompletionItemByRequiredHour
	 *
	 */
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByRequiredHour(Integer requiredHour) throws DataAccessException {

		return findProjectCompletionItemByRequiredHour(requiredHour, -1, -1);
	}

	/**
	 * JPQL Query - findProjectCompletionItemByRequiredHour
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByRequiredHour(Integer requiredHour, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectCompletionItemByRequiredHour", startResult, maxRows, requiredHour);
		return new LinkedHashSet<ProjectCompletionItem>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectCompletionItemByObjectItem
	 *
	 */
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByObjectItem(String objectItem) throws DataAccessException {

		return findProjectCompletionItemByObjectItem(objectItem, -1, -1);
	}

	/**
	 * JPQL Query - findProjectCompletionItemByObjectItem
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectCompletionItem> findProjectCompletionItemByObjectItem(String objectItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectCompletionItemByObjectItem", startResult, maxRows, objectItem);
		return new LinkedHashSet<ProjectCompletionItem>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(ProjectCompletionItem entity) {
		return true;
	}
}
