package net.zjcclims.service.construction;

import net.zjcclims.domain.CFundAppItem;
import net.zjcclims.domain.ProjectDeviceList;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectDeviceList entities
 * 
 */
public interface ProjectDeviceListService {

	/**
	 * Load an existing ProjectDeviceList entity
	 * 
	 */
	public Set<ProjectDeviceList> loadProjectDeviceLists();

	/**
	 * Delete an existing CFundAppItem entity
	 * 
	 */
	public ProjectDeviceList deleteProjectDeviceListCFundAppItem(Integer projectdevicelist_id, Integer related_cfundappitem_id);

	/**
	 * Return a count of all ProjectDeviceList entity
	 * 
	 */
	public Integer countProjectDeviceLists();

	/**
	 * Return all ProjectDeviceList entity
	 * 
	 */
	public List<ProjectDeviceList> findAllProjectDeviceLists(Integer startResult, Integer maxRows);

	/**
	 * Save an existing ProjectDeviceList entity
	 * 
	 */
	public void saveProjectDeviceList(ProjectDeviceList projectdevicelist);

	/**
	 * Delete an existing ProjectDeviceList entity
	 * 
	 */
	public void deleteProjectDeviceList(ProjectDeviceList projectdevicelist_1);

	/**
	 */
	public ProjectDeviceList findProjectDeviceListByPrimaryKey(Integer id);

	/**
	 * Save an existing CFundAppItem entity
	 * 
	 */
	public ProjectDeviceList saveProjectDeviceListCFundAppItem(Integer id_1, CFundAppItem related_cfundappitem);

	/***************************************************************************************
	 * 功能：根据主键获取ProjectDeviceList对象
	 * 作者：李德
	 * 时间：2015-03-27
	 **************************************************************************************/
	@Transactional
	public List<ProjectDeviceList> findProjectDeviceListByKey(Integer id);

}