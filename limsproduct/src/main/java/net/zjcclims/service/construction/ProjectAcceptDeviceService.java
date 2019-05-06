package net.zjcclims.service.construction;


import net.zjcclims.domain.ProjectAcceptDevice;
import net.zjcclims.domain.ProjectAcceptanceApplication;
import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectAcceptDevice entities
 * 
 */
public interface ProjectAcceptDeviceService {

	/**
	 * Delete an existing ProjectAcceptDevice entity
	 * 
	 */
	public void deleteProjectAcceptDevice(ProjectAcceptDevice projectacceptdevice);

	/**
	 * Save an existing ProjectAcceptanceApplication entity
	 * 
	 */
	public ProjectAcceptDevice saveProjectAcceptDeviceProjectAcceptanceApplication(Integer id, ProjectAcceptanceApplication related_projectacceptanceapplication);

	/**
	 */
	public ProjectAcceptDevice findProjectAcceptDeviceByPrimaryKey(Integer id_1);

	/**
	 * Delete an existing ProjectAcceptanceApplication entity
	 * 
	 */
	public ProjectAcceptDevice deleteProjectAcceptDeviceProjectAcceptanceApplication(Integer projectacceptdevice_id, Integer related_projectacceptanceapplication_id);

	/**
	 * Return a count of all ProjectAcceptDevice entity
	 * 
	 */
	public Integer countProjectAcceptDevices();

	/**
	 * Save an existing ProjectAcceptDevice entity
	 * 
	 */
	public void saveProjectAcceptDevice(ProjectAcceptDevice projectacceptdevice_1);

	/**
	 * Load an existing ProjectAcceptDevice entity
	 * 
	 */
	public Set<ProjectAcceptDevice> loadProjectAcceptDevices();

	/**
	 * Return all ProjectAcceptDevice entity
	 * 
	 */
	public List<ProjectAcceptDevice> findAllProjectAcceptDevices(Integer startResult, Integer maxRows);
	/****************************************************************************
	 * 功能：根据验收申请ID查询验收申请设备详细
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	public List<ProjectAcceptDevice> findProjectAcceptDeviceByProAppId(int idKey);

	/****************************************************************************
	 * 功能：保存验收申请设备详细对象
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	public ProjectAcceptDevice save(ProjectAcceptDevice projectAcceptDevice);


}