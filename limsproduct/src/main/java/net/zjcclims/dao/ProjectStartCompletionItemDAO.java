package net.zjcclims.dao;


import net.zjcclims.domain.ProjectStartCompletionItem;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage ProjectStartCompletionItem entities.
 * 
 */
public interface ProjectStartCompletionItemDAO extends
		JpaDao<ProjectStartCompletionItem> {

	/**
	 * JPQL Query - findProjectStartCompletionItemByRequiredHour
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByRequiredHour(Integer requiredHour) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByRequiredHour
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByRequiredHour(Integer requiredHour, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentPropertyContaining
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentPropertyContaining(String experimentProperty) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentPropertyContaining
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentPropertyContaining(String experimentProperty, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentProperty
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentProperty(String experimentProperty_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentProperty
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentProperty(String experimentProperty_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByIsOriginal
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByIsOriginal(String isOriginal) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByIsOriginal
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByIsOriginal(String isOriginal, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectStartCompletionItems
	 *
	 */
	public Set<ProjectStartCompletionItem> findAllProjectStartCompletionItems() throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectStartCompletionItems
	 *
	 */
	public Set<ProjectStartCompletionItem> findAllProjectStartCompletionItems(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByUseSitutation
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByUseSitutation(Integer useSitutation) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByUseSitutation
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByUseSitutation(Integer useSitutation, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByApprovalAppId
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByApprovalAppId(Integer approvalAppId) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByApprovalAppId
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByApprovalAppId(Integer approvalAppId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByPrimaryKey
	 *
	 */
	public ProjectStartCompletionItem findProjectStartCompletionItemByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByPrimaryKey
	 *
	 */
	public ProjectStartCompletionItem findProjectStartCompletionItemByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByStartReportId
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByStartReportId(Integer startReportId) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByStartReportId
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByStartReportId(Integer startReportId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByEquipmentAmount
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByEquipmentAmount(Integer equipmentAmount) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByEquipmentAmount
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByEquipmentAmount(Integer equipmentAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByObjectItemContaining
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByObjectItemContaining(String objectItem) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByObjectItemContaining
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByObjectItemContaining(String objectItem, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemBySimultaneouslyAmount
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemBySimultaneouslyAmount(Integer simultaneouslyAmount) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemBySimultaneouslyAmount
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemBySimultaneouslyAmount(Integer simultaneouslyAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByIsOriginalContaining
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByIsOriginalContaining(String isOriginal_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByIsOriginalContaining
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByIsOriginalContaining(String isOriginal_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentOutline
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentOutline(Integer experimentOutline) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentOutline
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentOutline(Integer experimentOutline, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByObjectItem
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByObjectItem(String objectItem_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByObjectItem
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByObjectItem(String objectItem_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentName
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentName(String experimentName) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentName
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentName(String experimentName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByPerGroupAmount
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByPerGroupAmount(Integer perGroupAmount) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByPerGroupAmount
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByPerGroupAmount(Integer perGroupAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentNameContaining
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentNameContaining(String experimentName_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentNameContaining
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentNameContaining(String experimentName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemById
	 *
	 */
	public ProjectStartCompletionItem findProjectStartCompletionItemById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemById
	 *
	 */
	public ProjectStartCompletionItem findProjectStartCompletionItemById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentInstructor
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentInstructor(Integer experimentInstructor) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartCompletionItemByExperimentInstructor
	 *
	 */
	public Set<ProjectStartCompletionItem> findProjectStartCompletionItemByExperimentInstructor(Integer experimentInstructor, int startResult, int maxRows) throws DataAccessException;

}