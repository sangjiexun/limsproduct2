package net.zjcclims.dao;


import net.zjcclims.domain.ProjectAcceptCompletionItem;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage ProjectAcceptCompletionItem entities.
 * 
 */
public interface ProjectAcceptCompletionItemDAO extends
		JpaDao<ProjectAcceptCompletionItem> {

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByIsOriginalContaining
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByIsOriginalContaining(String isOriginal) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByIsOriginalContaining
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByIsOriginalContaining(String isOriginal, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByPrimaryKey
	 *
	 */
	public ProjectAcceptCompletionItem findProjectAcceptCompletionItemByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByPrimaryKey
	 *
	 */
	public ProjectAcceptCompletionItem findProjectAcceptCompletionItemByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByRequiredHour
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByRequiredHour(Integer requiredHour) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByRequiredHour
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByRequiredHour(Integer requiredHour, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentPropertyContaining
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentPropertyContaining(String experimentProperty) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentPropertyContaining
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentPropertyContaining(String experimentProperty, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByUseSitutation
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByUseSitutation(Integer useSitutation) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByUseSitutation
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByUseSitutation(Integer useSitutation, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectAcceptCompletionItems
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findAllProjectAcceptCompletionItems() throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectAcceptCompletionItems
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findAllProjectAcceptCompletionItems(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByPerGroupAmount
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByPerGroupAmount(Integer perGroupAmount) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByPerGroupAmount
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByPerGroupAmount(Integer perGroupAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentNameContaining
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentNameContaining(String experimentName) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentNameContaining
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentNameContaining(String experimentName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByIsOriginal
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByIsOriginal(String isOriginal_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByIsOriginal
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByIsOriginal(String isOriginal_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemById
	 *
	 */
	public ProjectAcceptCompletionItem findProjectAcceptCompletionItemById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemById
	 *
	 */
	public ProjectAcceptCompletionItem findProjectAcceptCompletionItemById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByObjectItemContaining
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByObjectItemContaining(String objectItem) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByObjectItemContaining
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByObjectItemContaining(String objectItem, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByApprovalAppId
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByApprovalAppId(Integer approvalAppId) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByApprovalAppId
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByApprovalAppId(Integer approvalAppId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentName
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentName(String experimentName_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentName
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentName(String experimentName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemBySimultaneouslyAmount
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemBySimultaneouslyAmount(Integer simultaneouslyAmount) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemBySimultaneouslyAmount
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemBySimultaneouslyAmount(Integer simultaneouslyAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentProperty
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentProperty(String experimentProperty_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentProperty
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentProperty(String experimentProperty_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByStartReportId
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByStartReportId(Integer startReportId) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByStartReportId
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByStartReportId(Integer startReportId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentInstructor
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentInstructor(Integer experimentInstructor) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentInstructor
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentInstructor(Integer experimentInstructor, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByEquipmentAmount
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByEquipmentAmount(Integer equipmentAmount) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByEquipmentAmount
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByEquipmentAmount(Integer equipmentAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentOutline
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentOutline(Integer experimentOutline) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByExperimentOutline
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByExperimentOutline(Integer experimentOutline, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByObjectItem
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByObjectItem(String objectItem_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptCompletionItemByObjectItem
	 *
	 */
	public Set<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByObjectItem(String objectItem_1, int startResult, int maxRows) throws DataAccessException;

}