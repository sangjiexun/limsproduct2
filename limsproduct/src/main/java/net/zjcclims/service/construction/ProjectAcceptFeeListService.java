package net.zjcclims.service.construction;


import net.zjcclims.domain.CFundAppItem;
import net.zjcclims.domain.ProjectAcceptFeeList;
import net.zjcclims.domain.ProjectAcceptanceApplication;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectAcceptFeeList entities
 * 
 */
public interface ProjectAcceptFeeListService {

	/**
	 * Return a count of all ProjectAcceptFeeList entity
	 * 
	 */
	public Integer countProjectAcceptFeeLists();

	/**
	 * Delete an existing ProjectAcceptanceApplication entity
	 * 
	 */
	public ProjectAcceptFeeList deleteProjectAcceptFeeListProjectAcceptanceApplication(Integer projectacceptfeelist_id, Integer related_projectacceptanceapplication_id);

	/**
	 * Load an existing ProjectAcceptFeeList entity
	 * 
	 */
	public Set<ProjectAcceptFeeList> loadProjectAcceptFeeLists();

	/**
	 * Save an existing ProjectAcceptanceApplication entity
	 * 
	 */
	public ProjectAcceptFeeList saveProjectAcceptFeeListProjectAcceptanceApplication(Integer id, ProjectAcceptanceApplication related_projectacceptanceapplication);

	/**
	 */
	public ProjectAcceptFeeList findProjectAcceptFeeListByPrimaryKey(Integer id_1);

	/**
	 * Save an existing CFundAppItem entity
	 * 
	 */
	public ProjectAcceptFeeList saveProjectAcceptFeeListCFundAppItem(Integer id_2, CFundAppItem related_cfundappitem);

	/**
	 * Delete an existing ProjectAcceptFeeList entity
	 * 
	 */
	public void deleteProjectAcceptFeeList(ProjectAcceptFeeList projectacceptfeelist);

	/**
	 * Return all ProjectAcceptFeeList entity
	 * 
	 */
	public List<ProjectAcceptFeeList> findAllProjectAcceptFeeLists(Integer startResult, Integer maxRows);

	/**
	 * Delete an existing CFundAppItem entity
	 * 
	 */
	public ProjectAcceptFeeList deleteProjectAcceptFeeListCFundAppItem(Integer projectacceptfeelist_id_1, Integer related_cfundappitem_id);

	/**
	 * Save an existing ProjectAcceptFeeList entity
	 * 
	 */
	public void saveProjectAcceptFeeList(ProjectAcceptFeeList projectacceptfeelist_1);

	/****************************************************************************
	 * 功能：根据验收申请ID查询验收申请经费
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	public List<ProjectAcceptFeeList> findProjectAcceptFeeListByProAppId(int idKey);

	/****************************************************************************
	 * 功能：保存验收申请经费
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	public ProjectAcceptFeeList save(ProjectAcceptFeeList projectAcceptFeeList);

}