package net.zjcclims.service.construction;


import net.zjcclims.domain.*;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectStartCompletionItem entities
 * 
 */
public interface ProjectStartCompletionItemService {

	/**
	 * Load an existing ProjectStartCompletionItem entity
	 * 
	 */
	public Set<ProjectStartCompletionItem> loadProjectStartCompletionItems();

	/**
	 * Save an existing ProjectStartedReport entity
	 * 
	 */
	public ProjectStartCompletionItem saveProjectStartCompletionItemProjectStartedReport(Integer id, ProjectStartedReport related_projectstartedreport);

	/**
	 * Delete an existing ProjectStartCompletionItem entity
	 * 
	 */
	public void deleteProjectStartCompletionItem(ProjectStartCompletionItem projectstartcompletionitem);

	/**
	 * Delete an existing ProjectStartedReport entity
	 * 
	 */
	public ProjectStartCompletionItem deleteProjectStartCompletionItemProjectStartedReport(Integer projectstartcompletionitem_id, Integer related_projectstartedreport_id);

	/**
	 * Save an existing ProjectStartCompletionItem entity
	 * 
	 */
	public void saveProjectStartCompletionItem(ProjectStartCompletionItem projectstartcompletionitem_1);

	/**
	 * Return all ProjectStartCompletionItem entity
	 * 
	 */
	public List<ProjectStartCompletionItem> findAllProjectStartCompletionItems(Integer startResult, Integer maxRows);

	/**
	 */
	public ProjectStartCompletionItem findProjectStartCompletionItemByPrimaryKey(Integer id_1);

	/**
	 * Return a count of all ProjectStartCompletionItem entity
	 * 
	 */
	public Integer countProjectStartCompletionItems();
	
	/****************************************************************************
	 * 功能：根据启动报告ID查询启动报告开设实验项目
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	public List<ProjectStartCompletionItem> findProjectStartCompletionItemByProStartId(int idKey);

	/****************************************************************************
	 * 功能：保存启动报告开设实验项目对象
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	public ProjectStartCompletionItem save(ProjectStartCompletionItem projectStartCompletionItem);
}