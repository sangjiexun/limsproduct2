package net.zjcclims.service.construction;



import net.zjcclims.domain.*;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectStartFeeList entities
 * 
 */
public interface ProjectStartFeeListService {

	/**
	 * Delete an existing ProjectStartedReport entity
	 * 
	 */
	public ProjectStartFeeList deleteProjectStartFeeListProjectStartedReport(Integer projectstartfeelist_id, Integer related_projectstartedreport_id);

	/**
	 * Delete an existing CFundAppItem entity
	 * 
	 */
	public ProjectStartFeeList deleteProjectStartFeeListCFundAppItem(Integer projectstartfeelist_id_1, Integer related_cfundappitem_id);

	/**
	 * Save an existing ProjectStartedReport entity
	 * 
	 */
	public ProjectStartFeeList saveProjectStartFeeListProjectStartedReport(Integer id, ProjectStartedReport related_projectstartedreport);

	/**
	 * Save an existing ProjectStartFeeList entity
	 * 
	 */
	public void saveProjectStartFeeList(ProjectStartFeeList projectstartfeelist);

	/**
	 * Delete an existing ProjectStartFeeList entity
	 * 
	 */
	public void deleteProjectStartFeeList(ProjectStartFeeList projectstartfeelist_1);

	/**
	 * Return a count of all ProjectStartFeeList entity
	 * 
	 */
	public Integer countProjectStartFeeLists();

	/**
	 */
	public ProjectStartFeeList findProjectStartFeeListByPrimaryKey(Integer id_1);

	/**
	 * Save an existing CFundAppItem entity
	 * 
	 */
	public ProjectStartFeeList saveProjectStartFeeListCFundAppItem(Integer id_2, CFundAppItem related_cfundappitem);

	/**
	 * Load an existing ProjectStartFeeList entity
	 * 
	 */
	public Set<ProjectStartFeeList> loadProjectStartFeeLists();

	/**
	 * Return all ProjectStartFeeList entity
	 * 
	 */
	public List<ProjectStartFeeList> findAllProjectStartFeeLists(Integer startResult, Integer maxRows);

	/****************************************************************************
	 * 功能：根据启动报告ID查询经费
	 * 作者：李德
	 * 时间：2015-04-13
	 ****************************************************************************/
	public List<ProjectStartFeeList> findProjectStartFeeListByProStartId(int idKey);

	/****************************************************************************
	 * 功能：保存经费对象
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	public ProjectStartFeeList save(ProjectStartFeeList projectStartFeeList);
}