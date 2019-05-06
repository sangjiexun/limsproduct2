package net.zjcclims.service.construction;


import net.zjcclims.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectFeeList entities
 * 
 */
public interface ProjectFeeListService {

	/**
	 */
	public ProjectFeeList findProjectFeeListByPrimaryKey(Integer id);

	/**
	 * Return all ProjectFeeList entity
	 * 
	 */
	public List<ProjectFeeList> findAllProjectFeeLists(Integer startResult, Integer maxRows);

	/**
	 * Delete an existing CFundAppItem entity
	 * 
	 */
	public ProjectFeeList deleteProjectFeeListCFundAppItem(Integer projectfeelist_id, Integer related_cfundappitem_id);

	/**
	 * Save an existing CFundAppItem entity
	 * 
	 */
	public ProjectFeeList saveProjectFeeListCFundAppItem(Integer id_1, CFundAppItem related_cfundappitem);

	/**
	 * Load an existing ProjectFeeList entity
	 * 
	 */
	public Set<ProjectFeeList> loadProjectFeeLists();

	/**
	 * Save an existing ProjectFeeList entity
	 * 
	 */
	public void saveProjectFeeList(ProjectFeeList projectfeelist);

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	public ProjectFeeList deleteProjectFeeListLabConstructApp(Integer projectfeelist_id_1, Integer related_labconstructapp_id);

	/**
	 * Delete an existing ProjectFeeList entity
	 * 
	 */
	public void deleteProjectFeeList(ProjectFeeList projectfeelist_1);

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	public ProjectFeeList saveProjectFeeListLabConstructApp(Integer id_2, LabConstructApp related_labconstructapp);

	/**
	 * Return a count of all ProjectFeeList entity
	 * 
	 */
	public Integer countProjectFeeLists();
	
	/***************************************************************************************
	 * 功能：实验室建设项目-实验室建设项目经费
	 * 作者：李德
	 * 时间：2015-04-03
	 **************************************************************************************/
	@Transactional
	public List<ProjectFeeList> findProjectFeeListByAppKey(Integer id);
}