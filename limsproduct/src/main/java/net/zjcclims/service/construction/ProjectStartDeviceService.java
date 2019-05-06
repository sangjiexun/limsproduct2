package net.zjcclims.service.construction;


import net.zjcclims.domain.*;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectStartDevice entities
 * 
 */
public interface ProjectStartDeviceService {

	/**
	 * Delete an existing ProjectStartedReport entity
	 * 
	 */
	public ProjectStartDevice deleteProjectStartDeviceProjectStartedReport(Integer projectstartdevice_id, Integer related_projectstartedreport_id);

	/**
	 * Return a count of all ProjectStartDevice entity
	 * 
	 */
	public Integer countProjectStartDevices();

	/**
	 * Load an existing ProjectStartDevice entity
	 * 
	 */
	public Set<ProjectStartDevice> loadProjectStartDevices();

	/**
	 * Return all ProjectStartDevice entity
	 * 
	 */
	public List<ProjectStartDevice> findAllProjectStartDevices(Integer startResult, Integer maxRows);

	/**
	 * Save an existing ProjectStartDevice entity
	 * 
	 */
	public void saveProjectStartDevice(ProjectStartDevice projectstartdevice);

	/**
	 * Delete an existing ProjectStartDevice entity
	 * 
	 */
	public void deleteProjectStartDevice(ProjectStartDevice projectstartdevice_1);

	/**
	 */
	public ProjectStartDevice findProjectStartDeviceByPrimaryKey(Integer id);

	/**
	 * Save an existing ProjectStartedReport entity
	 * 
	 */
	public ProjectStartDevice saveProjectStartDeviceProjectStartedReport(Integer id_1, ProjectStartedReport related_projectstartedreport);

	/****************************************************************************
	 * 功能：根据启动报告ID查询启动报告设备信息
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	public List<ProjectStartDevice> findProjectStartDeviceByProStartId(int idKey);

	/****************************************************************************
	 * 功能：保存启动报告设备信息对象
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	public ProjectStartDevice save(ProjectStartDevice projectStartDevice);
}