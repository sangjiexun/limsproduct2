package net.zjcclims.service.construction;


import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectStartDevice entities
 * 
 */

@Service("ProjectStartDeviceService")
@Transactional
public class ProjectStartDeviceServiceImpl implements ProjectStartDeviceService {

	/**
	 * DAO injected by Spring that manages ProjectStartDevice entities
	 * 
	 */
	@Autowired
	private ProjectStartDeviceDAO projectStartDeviceDAO;

	/**
	 * DAO injected by Spring that manages ProjectStartedReport entities
	 * 
	 */
	@Autowired
	private ProjectStartedReportDAO projectStartedReportDAO;

	/**
	 * Instantiates a new ProjectStartDeviceServiceImpl.
	 *
	 */
	public ProjectStartDeviceServiceImpl() {
	}

	/**
	 * Delete an existing ProjectStartedReport entity
	 * 
	 */
	@Transactional
	public ProjectStartDevice deleteProjectStartDeviceProjectStartedReport(Integer projectstartdevice_id, Integer related_projectstartedreport_id) {
		ProjectStartDevice projectstartdevice = projectStartDeviceDAO.findProjectStartDeviceByPrimaryKey(projectstartdevice_id, -1, -1);
		ProjectStartedReport related_projectstartedreport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(related_projectstartedreport_id, -1, -1);

		projectstartdevice.setProjectStartedReport(null);
		related_projectstartedreport.getProjectStartDevices().remove(projectstartdevice);
		projectstartdevice = projectStartDeviceDAO.store(projectstartdevice);
		projectStartDeviceDAO.flush();

		related_projectstartedreport = projectStartedReportDAO.store(related_projectstartedreport);
		projectStartedReportDAO.flush();

		projectStartedReportDAO.remove(related_projectstartedreport);
		projectStartedReportDAO.flush();

		return projectstartdevice;
	}

	/**
	 * Return a count of all ProjectStartDevice entity
	 * 
	 */
	@Transactional
	public Integer countProjectStartDevices() {
		return ((Long) projectStartDeviceDAO.createQuerySingleResult("select count(o) from ProjectStartDevice o").getSingleResult()).intValue();
	}

	/**
	 * Load an existing ProjectStartDevice entity
	 * 
	 */
	@Transactional
	public Set<ProjectStartDevice> loadProjectStartDevices() {
		return projectStartDeviceDAO.findAllProjectStartDevices();
	}

	/**
	 * Return all ProjectStartDevice entity
	 * 
	 */
	@Transactional
	public List<ProjectStartDevice> findAllProjectStartDevices(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<ProjectStartDevice>(projectStartDeviceDAO.findAllProjectStartDevices(startResult, maxRows));
	}

	/**
	 * Save an existing ProjectStartDevice entity
	 * 
	 */
	@Transactional
	public void saveProjectStartDevice(ProjectStartDevice projectstartdevice) {
		ProjectStartDevice existingProjectStartDevice = projectStartDeviceDAO.findProjectStartDeviceByPrimaryKey(projectstartdevice.getId());

		if (existingProjectStartDevice != null) {
			if (existingProjectStartDevice != projectstartdevice) {
				existingProjectStartDevice.setId(projectstartdevice.getId());
				existingProjectStartDevice.setEquipmentName(projectstartdevice.getEquipmentName());
				existingProjectStartDevice.setFormat(projectstartdevice.getFormat());
				existingProjectStartDevice.setAmount(projectstartdevice.getAmount());
				existingProjectStartDevice.setUnitPrice(projectstartdevice.getUnitPrice());
				existingProjectStartDevice.setCollection(projectstartdevice.getCollection());
				existingProjectStartDevice.setPurchasePattern(projectstartdevice.getPurchasePattern());
			}
			projectstartdevice = projectStartDeviceDAO.store(existingProjectStartDevice);
		} else {
			projectstartdevice = projectStartDeviceDAO.store(projectstartdevice);
		}
		projectStartDeviceDAO.flush();
	}

	/**
	 * Delete an existing ProjectStartDevice entity
	 * 
	 */
	@Transactional
	public void deleteProjectStartDevice(ProjectStartDevice projectstartdevice) {
		projectStartDeviceDAO.remove(projectstartdevice);
		projectStartDeviceDAO.flush();
	}

	/**
	 */
	@Transactional
	public ProjectStartDevice findProjectStartDeviceByPrimaryKey(Integer id) {
		return projectStartDeviceDAO.findProjectStartDeviceByPrimaryKey(id);
	}

	/**
	 * Save an existing ProjectStartedReport entity
	 * 
	 */
	@Transactional
	public ProjectStartDevice saveProjectStartDeviceProjectStartedReport(Integer id, ProjectStartedReport related_projectstartedreport) {
		ProjectStartDevice projectstartdevice = projectStartDeviceDAO.findProjectStartDeviceByPrimaryKey(id, -1, -1);
		ProjectStartedReport existingprojectStartedReport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(related_projectstartedreport.getId());

		// copy into the existing record to preserve existing relationships
		if (existingprojectStartedReport != null) {
			existingprojectStartedReport.setId(related_projectstartedreport.getId());
			existingprojectStartedReport.setProjectName(related_projectstartedreport.getProjectName());
			existingprojectStartedReport.setLabAddress(related_projectstartedreport.getLabAddress());
			existingprojectStartedReport.setLabArea(related_projectstartedreport.getLabArea());
			existingprojectStartedReport.setFeeApp(related_projectstartedreport.getFeeApp());
			existingprojectStartedReport.setFeeCode(related_projectstartedreport.getFeeCode());
			existingprojectStartedReport.setStartDate(related_projectstartedreport.getStartDate());
			existingprojectStartedReport.setOpenLabItem(related_projectstartedreport.getOpenLabItem());
			existingprojectStartedReport.setEquipmentList(related_projectstartedreport.getEquipmentList());
			existingprojectStartedReport.setFeeApprovalDetail(related_projectstartedreport.getFeeApprovalDetail());
			related_projectstartedreport = existingprojectStartedReport;
		} else {
			related_projectstartedreport = projectStartedReportDAO.store(related_projectstartedreport);
			projectStartedReportDAO.flush();
		}

		projectstartdevice.setProjectStartedReport(related_projectstartedreport);
		related_projectstartedreport.getProjectStartDevices().add(projectstartdevice);
		projectstartdevice = projectStartDeviceDAO.store(projectstartdevice);
		projectStartDeviceDAO.flush();

		related_projectstartedreport = projectStartedReportDAO.store(related_projectstartedreport);
		projectStartedReportDAO.flush();

		return projectstartdevice;
	}
	
	/****************************************************************************
	 * 功能：根据启动报告ID查询启动报告设备信息
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	@Override
	public List<ProjectStartDevice> findProjectStartDeviceByProStartId(int idKey) {
		// TODO Auto-generated method stub
		String sql="select c from ProjectStartDevice c where c.projectStartedReport.id =" +idKey;
		

		return projectStartDeviceDAO.executeQuery(sql,0,-1);
	}
	
	/****************************************************************************
	 * 功能：保存启动报告设备信息对象
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	@Override
	public ProjectStartDevice save(ProjectStartDevice projectStartDevice) {
		// TODO Auto-generated method stub
		return projectStartDeviceDAO.store(projectStartDevice);
	}
}
