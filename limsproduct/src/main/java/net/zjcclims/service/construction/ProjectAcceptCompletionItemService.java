package net.zjcclims.service.construction;



import net.zjcclims.domain.ProjectAcceptCompletionItem;
import net.zjcclims.domain.ProjectAcceptanceApplication;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectAcceptCompletionItem entities
 * 
 */
public interface ProjectAcceptCompletionItemService {

	/**
	 * Save an existing ProjectAcceptCompletionItem entity
	 * 
	 */
	public void saveProjectAcceptCompletionItem(ProjectAcceptCompletionItem projectacceptcompletionitem);

	/**
	 * Load an existing ProjectAcceptCompletionItem entity
	 * 
	 */
	public Set<ProjectAcceptCompletionItem> loadProjectAcceptCompletionItems();

	/**
	 * Save an existing ProjectAcceptanceApplication entity
	 * 
	 */
	public ProjectAcceptCompletionItem saveProjectAcceptCompletionItemProjectAcceptanceApplication(Integer id, ProjectAcceptanceApplication related_projectacceptanceapplication);

	/**
	 */
	public ProjectAcceptCompletionItem findProjectAcceptCompletionItemByPrimaryKey(Integer id_1);

	/**
	 * Return all ProjectAcceptCompletionItem entity
	 * 
	 */
	public List<ProjectAcceptCompletionItem> findAllProjectAcceptCompletionItems(Integer startResult, Integer maxRows);

	/**
	 * Return a count of all ProjectAcceptCompletionItem entity
	 * 
	 */
	public Integer countProjectAcceptCompletionItems();

	/**
	 * Delete an existing ProjectAcceptanceApplication entity
	 * 
	 */
	public ProjectAcceptCompletionItem deleteProjectAcceptCompletionItemProjectAcceptanceApplication(Integer projectacceptcompletionitem_id, Integer related_projectacceptanceapplication_id);

	/**
	 * Delete an existing ProjectAcceptCompletionItem entity
	 * 
	 */
	public void deleteProjectAcceptCompletionItem(ProjectAcceptCompletionItem projectacceptcompletionitem_1);
	/****************************************************************************
	 * 功能：根据验收申请ID查询验收申请开设实验项目
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	public List<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByProAppId(int idKey);

	/****************************************************************************
	 * 功能：保存验收申请开设实验项目对象
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	public ProjectAcceptCompletionItem save(ProjectAcceptCompletionItem projectAcceptCompletionItem);

}