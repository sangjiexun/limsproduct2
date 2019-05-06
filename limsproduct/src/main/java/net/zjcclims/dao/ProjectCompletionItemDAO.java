package net.zjcclims.dao;


import net.zjcclims.domain.ProjectCompletionItem;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage ProjectCompletionItem entities.
 * 
 */
public interface ProjectCompletionItemDAO extends JpaDao<ProjectCompletionItem> {

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentPropertyContaining
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentPropertyContaining(String experimentProperty) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentPropertyContaining
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentPropertyContaining(String experimentProperty, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByPrimaryKey
	 *
	 */
	public ProjectCompletionItem findProjectCompletionItemByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByPrimaryKey
	 *
	 */
	public ProjectCompletionItem findProjectCompletionItemByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectCompletionItems
	 *
	 */
	public Set<ProjectCompletionItem> findAllProjectCompletionItems() throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectCompletionItems
	 *
	 */
	public Set<ProjectCompletionItem> findAllProjectCompletionItems(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByUseSitutation
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByUseSitutation(Integer useSitutation) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByUseSitutation
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByUseSitutation(Integer useSitutation, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByObjectItemContaining
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByObjectItemContaining(String objectItem) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByObjectItemContaining
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByObjectItemContaining(String objectItem, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentName
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentName(String experimentName) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentName
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentName(String experimentName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemById
	 *
	 */
	public ProjectCompletionItem findProjectCompletionItemById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemById
	 *
	 */
	public ProjectCompletionItem findProjectCompletionItemById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentNameContaining
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentNameContaining(String experimentName_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentNameContaining
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentNameContaining(String experimentName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByPerGroupAmount
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByPerGroupAmount(Integer perGroupAmount) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByPerGroupAmount
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByPerGroupAmount(Integer perGroupAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByEquipmentAmount
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByEquipmentAmount(Integer equipmentAmount) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByEquipmentAmount
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByEquipmentAmount(Integer equipmentAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentInstructor
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentInstructor(Integer experimentInstructor) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentInstructor
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentInstructor(Integer experimentInstructor, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentOutline
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentOutline(Integer experimentOutline) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentOutline
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentOutline(Integer experimentOutline, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentProperty
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentProperty(String experimentProperty_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByExperimentProperty
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByExperimentProperty(String experimentProperty_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemBySimultaneouslyAmount
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemBySimultaneouslyAmount(Integer simultaneouslyAmount) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemBySimultaneouslyAmount
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemBySimultaneouslyAmount(Integer simultaneouslyAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByRequiredHour
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByRequiredHour(Integer requiredHour) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByRequiredHour
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByRequiredHour(Integer requiredHour, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByObjectItem
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByObjectItem(String objectItem_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectCompletionItemByObjectItem
	 *
	 */
	public Set<ProjectCompletionItem> findProjectCompletionItemByObjectItem(String objectItem_1, int startResult, int maxRows) throws DataAccessException;

}