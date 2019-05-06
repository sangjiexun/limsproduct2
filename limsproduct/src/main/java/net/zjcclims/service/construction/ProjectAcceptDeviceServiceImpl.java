package net.zjcclims.service.construction;


import net.zjcclims.dao.ProjectAcceptDeviceDAO;
import net.zjcclims.dao.ProjectAcceptanceApplicationDAO;
import net.zjcclims.domain.ProjectAcceptDevice;
import net.zjcclims.domain.ProjectAcceptanceApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectAcceptDevice entities
 * 
 */

@Service("ProjectAcceptDeviceService")
@Transactional
public class ProjectAcceptDeviceServiceImpl implements
		ProjectAcceptDeviceService {

	/**
	 * DAO injected by Spring that manages ProjectAcceptDevice entities
	 * 
	 */
	@Autowired
	private ProjectAcceptDeviceDAO projectAcceptDeviceDAO;

	/**
	 * DAO injected by Spring that manages ProjectAcceptanceApplication entities
	 * 
	 */
	@Autowired
	private ProjectAcceptanceApplicationDAO projectAcceptanceApplicationDAO;

	/**
	 * Instantiates a new ProjectAcceptDeviceServiceImpl.
	 *
	 */
	public ProjectAcceptDeviceServiceImpl() {
	}

	/**
	 * Delete an existing ProjectAcceptDevice entity
	 * 
	 */
	@Transactional
	public void deleteProjectAcceptDevice(ProjectAcceptDevice projectacceptdevice) {
		projectAcceptDeviceDAO.remove(projectacceptdevice);
		projectAcceptDeviceDAO.flush();
	}

	/**
	 * Save an existing ProjectAcceptanceApplication entity
	 * 
	 */
	@Transactional
	public ProjectAcceptDevice saveProjectAcceptDeviceProjectAcceptanceApplication(Integer id, ProjectAcceptanceApplication related_projectacceptanceapplication) {
		ProjectAcceptDevice projectacceptdevice = projectAcceptDeviceDAO.findProjectAcceptDeviceByPrimaryKey(id, -1, -1);
		ProjectAcceptanceApplication existingprojectAcceptanceApplication = projectAcceptanceApplicationDAO.findProjectAcceptanceApplicationByPrimaryKey(related_projectacceptanceapplication.getId());

		// copy into the existing record to preserve existing relationships
		if (existingprojectAcceptanceApplication != null) {
			existingprojectAcceptanceApplication.setId(related_projectacceptanceapplication.getId());
			existingprojectAcceptanceApplication.setAppDate(related_projectacceptanceapplication.getAppDate());
			existingprojectAcceptanceApplication.setProjectStartDate(related_projectacceptanceapplication.getProjectStartDate());
			existingprojectAcceptanceApplication.setExpectCompleteDate(related_projectacceptanceapplication.getExpectCompleteDate());
			existingprojectAcceptanceApplication.setRealityCompleteDate(related_projectacceptanceapplication.getRealityCompleteDate());
			existingprojectAcceptanceApplication.setOriginalLabroomArea(related_projectacceptanceapplication.getOriginalLabroomArea());
			existingprojectAcceptanceApplication.setOriginalLabroomAdd(related_projectacceptanceapplication.getOriginalLabroomAdd());
			existingprojectAcceptanceApplication.setOriginalLabroomValue(related_projectacceptanceapplication.getOriginalLabroomValue());
			existingprojectAcceptanceApplication.setOriginalLabroomItem(related_projectacceptanceapplication.getOriginalLabroomItem());
			existingprojectAcceptanceApplication.setTargetLabroomArea(related_projectacceptanceapplication.getTargetLabroomArea());
			existingprojectAcceptanceApplication.setTargetLabroomAdd(related_projectacceptanceapplication.getTargetLabroomAdd());
			existingprojectAcceptanceApplication.setTargetLabroomValue(related_projectacceptanceapplication.getTargetLabroomValue());
			existingprojectAcceptanceApplication.setTargetLabroomItem(related_projectacceptanceapplication.getTargetLabroomItem());
			existingprojectAcceptanceApplication.setMajorAmount(related_projectacceptanceapplication.getMajorAmount());
			existingprojectAcceptanceApplication.setMajorName(related_projectacceptanceapplication.getMajorName());
			existingprojectAcceptanceApplication.setCourseAmount(related_projectacceptanceapplication.getCourseAmount());
			existingprojectAcceptanceApplication.setCourseName(related_projectacceptanceapplication.getCourseName());
			existingprojectAcceptanceApplication.setExpectLabItem(related_projectacceptanceapplication.getExpectLabItem());
			existingprojectAcceptanceApplication.setRealityLabItem(related_projectacceptanceapplication.getRealityLabItem());
			existingprojectAcceptanceApplication.setConstructCondition(related_projectacceptanceapplication.getConstructCondition());
			existingprojectAcceptanceApplication.setOpenLabItem(related_projectacceptanceapplication.getOpenLabItem());
			existingprojectAcceptanceApplication.setProjectSituation(related_projectacceptanceapplication.getProjectSituation());
			existingprojectAcceptanceApplication.setProjectExpectedBenefits(related_projectacceptanceapplication.getProjectExpectedBenefits());
			existingprojectAcceptanceApplication.setActualBenefits(related_projectacceptanceapplication.getActualBenefits());
			related_projectacceptanceapplication = existingprojectAcceptanceApplication;
		} else {
			related_projectacceptanceapplication = projectAcceptanceApplicationDAO.store(related_projectacceptanceapplication);
			projectAcceptanceApplicationDAO.flush();
		}

		projectacceptdevice.setProjectAcceptanceApplication(related_projectacceptanceapplication);
		related_projectacceptanceapplication.getProjectAcceptDevices().add(projectacceptdevice);
		projectacceptdevice = projectAcceptDeviceDAO.store(projectacceptdevice);
		projectAcceptDeviceDAO.flush();

		related_projectacceptanceapplication = projectAcceptanceApplicationDAO.store(related_projectacceptanceapplication);
		projectAcceptanceApplicationDAO.flush();

		return projectacceptdevice;
	}

	/**
	 */
	@Transactional
	public ProjectAcceptDevice findProjectAcceptDeviceByPrimaryKey(Integer id) {
		return projectAcceptDeviceDAO.findProjectAcceptDeviceByPrimaryKey(id);
	}

	/**
	 * Delete an existing ProjectAcceptanceApplication entity
	 * 
	 */
	@Transactional
	public ProjectAcceptDevice deleteProjectAcceptDeviceProjectAcceptanceApplication(Integer projectacceptdevice_id, Integer related_projectacceptanceapplication_id) {
		ProjectAcceptDevice projectacceptdevice = projectAcceptDeviceDAO.findProjectAcceptDeviceByPrimaryKey(projectacceptdevice_id, -1, -1);
		ProjectAcceptanceApplication related_projectacceptanceapplication = projectAcceptanceApplicationDAO.findProjectAcceptanceApplicationByPrimaryKey(related_projectacceptanceapplication_id, -1, -1);

		projectacceptdevice.setProjectAcceptanceApplication(null);
		related_projectacceptanceapplication.getProjectAcceptDevices().remove(projectacceptdevice);
		projectacceptdevice = projectAcceptDeviceDAO.store(projectacceptdevice);
		projectAcceptDeviceDAO.flush();

		related_projectacceptanceapplication = projectAcceptanceApplicationDAO.store(related_projectacceptanceapplication);
		projectAcceptanceApplicationDAO.flush();

		projectAcceptanceApplicationDAO.remove(related_projectacceptanceapplication);
		projectAcceptanceApplicationDAO.flush();

		return projectacceptdevice;
	}

	/**
	 * Return a count of all ProjectAcceptDevice entity
	 * 
	 */
	@Transactional
	public Integer countProjectAcceptDevices() {
		return ((Long) projectAcceptDeviceDAO.createQuerySingleResult("select count(o) from ProjectAcceptDevice o").getSingleResult()).intValue();
	}

	/**
	 * Save an existing ProjectAcceptDevice entity
	 * 
	 */
	@Transactional
	public void saveProjectAcceptDevice(ProjectAcceptDevice projectacceptdevice) {
		ProjectAcceptDevice existingProjectAcceptDevice = projectAcceptDeviceDAO.findProjectAcceptDeviceByPrimaryKey(projectacceptdevice.getId());

		if (existingProjectAcceptDevice != null) {
			if (existingProjectAcceptDevice != projectacceptdevice) {
				existingProjectAcceptDevice.setId(projectacceptdevice.getId());
				existingProjectAcceptDevice.setEquipmentName(projectacceptdevice.getEquipmentName());
				existingProjectAcceptDevice.setFormat(projectacceptdevice.getFormat());
				existingProjectAcceptDevice.setAmount(projectacceptdevice.getAmount());
				existingProjectAcceptDevice.setUnitPrice(projectacceptdevice.getUnitPrice());
				existingProjectAcceptDevice.setCollection(projectacceptdevice.getCollection());
				existingProjectAcceptDevice.setPurchasePattern(projectacceptdevice.getPurchasePattern());
			}
			projectacceptdevice = projectAcceptDeviceDAO.store(existingProjectAcceptDevice);
		} else {
			projectacceptdevice = projectAcceptDeviceDAO.store(projectacceptdevice);
		}
		projectAcceptDeviceDAO.flush();
	}

	/**
	 * Load an existing ProjectAcceptDevice entity
	 * 
	 */
	@Transactional
	public Set<ProjectAcceptDevice> loadProjectAcceptDevices() {
		return projectAcceptDeviceDAO.findAllProjectAcceptDevices();
	}

	/**
	 * Return all ProjectAcceptDevice entity
	 * 
	 */
	@Transactional
	public List<ProjectAcceptDevice> findAllProjectAcceptDevices(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<ProjectAcceptDevice>(projectAcceptDeviceDAO.findAllProjectAcceptDevices(startResult, maxRows));
	}
	
	/****************************************************************************
	 * 功能：根据验收申请ID查询验收申请设备详细
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	@Override
	public List<ProjectAcceptDevice> findProjectAcceptDeviceByProAppId(int idKey) {
		// TODO Auto-generated method stub
		String sql="select c from ProjectAcceptDevice c where c.projectAcceptanceApplication.id =" +idKey;
		

		return projectAcceptDeviceDAO.executeQuery(sql,0,-1);
	}
	
	/****************************************************************************
	 * 功能：保存验收申请设备详细对象
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	@Override
	public ProjectAcceptDevice save(ProjectAcceptDevice projectAcceptDevice) {
		// TODO Auto-generated method stub
		return projectAcceptDeviceDAO.store(projectAcceptDevice);
	}
}
