package net.zjcclims.service.construction;

import net.zjcclims.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectCompletionItem entities
 * 
 */
public interface ProjectCompletionItemService {

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	public ProjectCompletionItem saveProjectCompletionItemLabConstructApp(Integer id, LabConstructApp related_labconstructapp);

	/**
	 * Save an existing ProjectAcceptanceApplication entity
	 * 
	 */
	public ProjectCompletionItem saveProjectCompletionItemProjectAcceptanceApplication(Integer id_1, ProjectAcceptanceApplication related_projectacceptanceapplication);

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	public ProjectCompletionItem deleteProjectCompletionItemLabConstructApp(Integer projectcompletionitem_id, Integer related_labconstructapp_id);

	/**
	 * Load an existing ProjectCompletionItem entity
	 * 
	 */
	public Set<ProjectCompletionItem> loadProjectCompletionItems();

	/**
	 * Save an existing ProjectStartedReport entity
	 * 
	 */
	public ProjectCompletionItem saveProjectCompletionItemProjectStartedReports(Integer id_2, ProjectStartedReport related_projectstartedreports);

	/**
	 */
	public ProjectCompletionItem findProjectCompletionItemByPrimaryKey(Integer id_3);

	/**
	 * Delete an existing ProjectAcceptanceApplication entity
	 * 
	 */
	public ProjectCompletionItem deleteProjectCompletionItemProjectAcceptanceApplication(Integer projectcompletionitem_id_1, Integer related_projectacceptanceapplication_id);

	/**
	 * Delete an existing ProjectStartedReport entity
	 * 
	 */
	public ProjectCompletionItem deleteProjectCompletionItemProjectStartedReports(Integer projectcompletionitem_id_2, Integer related_projectstartedreports_id);

	/**
	 * Return all ProjectCompletionItem entity
	 * 
	 */
	public List<ProjectCompletionItem> findAllProjectCompletionItems(Integer startResult, Integer maxRows);

	/**
	 * Delete an existing ProjectCompletionItem entity
	 * 
	 */
	public void deleteProjectCompletionItem(ProjectCompletionItem projectcompletionitem);

	/**
	 * Delete an existing ProjectStartedReport entity
	 * 
	 */
	public ProjectCompletionItem deleteProjectCompletionItemProjectStartedReport(Integer projectcompletionitem_id_3, Integer related_projectstartedreport_id);

	/**
	 * Save an existing ProjectStartedReport entity
	 * 
	 */
	public ProjectCompletionItem saveProjectCompletionItemProjectStartedReport(Integer id_4, ProjectStartedReport related_projectstartedreport);

	/**
	 * Return a count of all ProjectCompletionItem entity
	 * 
	 */
	public Integer countProjectCompletionItems();

	/**
	 * Save an existing ProjectCompletionItem entity
	 * 
	 */
	public void saveProjectCompletionItem(ProjectCompletionItem projectcompletionitem_1);
	
	/***************************************************************************************
	 * 功能：实验室建设项目-实验室建设项目可开设实验（实训）项目清单
	 * 作者：李德
	 * 时间：2015-04-03
	 **************************************************************************************/
	@Transactional
	public List<ProjectCompletionItem> findProjectCompletionItemByAppKey(Integer id);

}