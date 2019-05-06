package net.zjcclims.service.construction;


import net.zjcclims.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectDevice entities
 * 
 */
public interface ProjectDeviceService {

	/**
	 */
	public ProjectDevice findProjectDeviceByPrimaryKey(Integer id);

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	public ProjectDevice deleteProjectDeviceLabConstructApp(Integer projectdevice_id, Integer related_labconstructapp_id);

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	public ProjectDevice saveProjectDeviceLabConstructApp(Integer id_1, LabConstructApp related_labconstructapp);

	/**
	 * Delete an existing ProjectStartedReport entity
	 * 
	 */
	public ProjectDevice deleteProjectDeviceProjectStartedReport(Integer projectdevice_id_1, Integer related_projectstartedreport_id);

	/**
	 * Delete an existing LabRoom entity
	 * 
	 */
	public ProjectDevice deleteProjectDeviceLabRoom(Integer projectdevice_id_2, Integer related_labroom_id);

	/**
	 * Return all ProjectDevice entity
	 * 
	 */
	public List<ProjectDevice> findAllProjectDevices(Integer startResult, Integer maxRows);

	/**
	 * Save an existing LabRoom entity
	 * 
	 */
	public ProjectDevice saveProjectDeviceLabRoom(Integer id_2, LabRoom related_labroom);

	/**
	 * Save an existing ProjectStartedReport entity
	 * 
	 */
	public ProjectDevice saveProjectDeviceProjectStartedReport(Integer id_3, ProjectStartedReport related_projectstartedreport);

	/**
	 * Return a count of all ProjectDevice entity
	 * 
	 */
	public Integer countProjectDevices();

	/**
	 * Save an existing ProjectDevice entity
	 * 
	 */
	public void saveProjectDevice(ProjectDevice projectdevice);

	/**
	 * Load an existing ProjectDevice entity
	 * 
	 */
	public Set<ProjectDevice> loadProjectDevices();

	/**
	 * Delete an existing ProjectDevice entity
	 * 
	 */
	public void deleteProjectDevice(ProjectDevice projectdevice_1);
	
	/***************************************************************************************
	 * 功能：实验室建设项目-实验室建设项目设备明细
	 * 作者：李德
	 * 时间：2015-04-03
	 **************************************************************************************/
	@Transactional
	public List<ProjectDevice> findProjectDeviceByAppKey(Integer id);
}