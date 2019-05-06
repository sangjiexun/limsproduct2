package net.zjcclims.service.construction;


import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectDevice entities
 * 
 */

@Service("ProjectDeviceService")
@Transactional
public class ProjectDeviceServiceImpl implements ProjectDeviceService {

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * DAO injected by Spring that manages LabRoom entities
	 * 
	 */
	@Autowired
	private LabRoomDAO labRoomDAO;

	/**
	 * DAO injected by Spring that manages ProjectDevice entities
	 * 
	 */
	@Autowired
	private ProjectDeviceDAO projectDeviceDAO;

	/**
	 * DAO injected by Spring that manages ProjectStartedReport entities
	 * 
	 */
	@Autowired
	private ProjectStartedReportDAO projectStartedReportDAO;

	/**
	 * Instantiates a new ProjectDeviceServiceImpl.
	 *
	 */
	public ProjectDeviceServiceImpl() {
	}

	/**
	 */
	@Transactional
	public ProjectDevice findProjectDeviceByPrimaryKey(Integer id) {
		return projectDeviceDAO.findProjectDeviceByPrimaryKey(id);
	}

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public ProjectDevice deleteProjectDeviceLabConstructApp(Integer projectdevice_id, Integer related_labconstructapp_id) {
		ProjectDevice projectdevice = projectDeviceDAO.findProjectDeviceByPrimaryKey(projectdevice_id, -1, -1);
		LabConstructApp related_labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp_id, -1, -1);

		projectdevice.setLabConstructApp(null);
		related_labconstructapp.getProjectDevices().remove(projectdevice);
		projectdevice = projectDeviceDAO.store(projectdevice);
		projectDeviceDAO.flush();

		related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
		labConstructAppDAO.flush();

		labConstructAppDAO.remove(related_labconstructapp);
		labConstructAppDAO.flush();

		return projectdevice;
	}

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public ProjectDevice saveProjectDeviceLabConstructApp(Integer id, LabConstructApp related_labconstructapp) {
		ProjectDevice projectdevice = projectDeviceDAO.findProjectDeviceByPrimaryKey(id, -1, -1);
		LabConstructApp existinglabConstructApp = labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp.getId());

		// copy into the existing record to preserve existing relationships
		if (existinglabConstructApp != null) {
			existinglabConstructApp.setId(related_labconstructapp.getId());
			existinglabConstructApp.setLabConstructAppCode(related_labconstructapp.getLabConstructAppCode());
			existinglabConstructApp.setProjectName(related_labconstructapp.getProjectName());
			existinglabConstructApp.setPartyId(related_labconstructapp.getPartyId());
			existinglabConstructApp.setAppDate(related_labconstructapp.getAppDate());
			existinglabConstructApp.setParticipant(related_labconstructapp.getParticipant());
			existinglabConstructApp.setPrimaryObjective(related_labconstructapp.getPrimaryObjective());
			existinglabConstructApp.setSpecialInnovation(related_labconstructapp.getSpecialInnovation());
			existinglabConstructApp.setProjectBasis(related_labconstructapp.getProjectBasis());
			existinglabConstructApp.setConstructBasis(related_labconstructapp.getConstructBasis());
			existinglabConstructApp.setExpectedResult(related_labconstructapp.getExpectedResult());
			existinglabConstructApp.setAppropriationBudget(related_labconstructapp.getAppropriationBudget());
			existinglabConstructApp.setEquipmentDetail(related_labconstructapp.getEquipmentDetail());
			existinglabConstructApp.setOpenLabItem(related_labconstructapp.getOpenLabItem());
			existinglabConstructApp.setOtherAppendix(related_labconstructapp.getOtherAppendix());
			existinglabConstructApp.setApprovalAppendix(related_labconstructapp.getApprovalAppendix());
			related_labconstructapp = existinglabConstructApp;
		} else {
			related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
			labConstructAppDAO.flush();
		}

		projectdevice.setLabConstructApp(related_labconstructapp);
		related_labconstructapp.getProjectDevices().add(projectdevice);
		projectdevice = projectDeviceDAO.store(projectdevice);
		projectDeviceDAO.flush();

		related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
		labConstructAppDAO.flush();

		return projectdevice;
	}

	/**
	 * Delete an existing ProjectStartedReport entity
	 * 
	 */
	@Transactional
	public ProjectDevice deleteProjectDeviceProjectStartedReport(Integer projectdevice_id, Integer related_projectstartedreport_id) {
		ProjectDevice projectdevice = projectDeviceDAO.findProjectDeviceByPrimaryKey(projectdevice_id, -1, -1);
		ProjectStartedReport related_projectstartedreport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(related_projectstartedreport_id, -1, -1);

		projectdevice.setProjectStartedReport(null);
		//related_projectstartedreport.getProjectDevices().remove(projectdevice);
		projectdevice = projectDeviceDAO.store(projectdevice);
		projectDeviceDAO.flush();

		related_projectstartedreport = projectStartedReportDAO.store(related_projectstartedreport);
		projectStartedReportDAO.flush();

		projectStartedReportDAO.remove(related_projectstartedreport);
		projectStartedReportDAO.flush();

		return projectdevice;
	}

	/**
	 * Delete an existing LabRoom entity
	 * 
	 */
	@Transactional
	public ProjectDevice deleteProjectDeviceLabRoom(Integer projectdevice_id, Integer related_labroom_id) {
		ProjectDevice projectdevice = projectDeviceDAO.findProjectDeviceByPrimaryKey(projectdevice_id, -1, -1);
		LabRoom related_labroom = labRoomDAO.findLabRoomByPrimaryKey(related_labroom_id, -1, -1);

		projectdevice.setLabRoom(null);
		related_labroom.getProjectDevices().remove(projectdevice);
		projectdevice = projectDeviceDAO.store(projectdevice);
		projectDeviceDAO.flush();

		related_labroom = labRoomDAO.store(related_labroom);
		labRoomDAO.flush();

		labRoomDAO.remove(related_labroom);
		labRoomDAO.flush();

		return projectdevice;
	}

	/**
	 * Return all ProjectDevice entity
	 * 
	 */
	@Transactional
	public List<ProjectDevice> findAllProjectDevices(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<ProjectDevice>(projectDeviceDAO.findAllProjectDevices(startResult, maxRows));
	}

	/**
	 * Save an existing LabRoom entity
	 * 
	 */
	@Transactional
	public ProjectDevice saveProjectDeviceLabRoom(Integer id, LabRoom related_labroom) {
		ProjectDevice projectdevice = projectDeviceDAO.findProjectDeviceByPrimaryKey(id, -1, -1);
		LabRoom existinglabRoom = labRoomDAO.findLabRoomByPrimaryKey(related_labroom.getId());

		// copy into the existing record to preserve existing relationships
		if (existinglabRoom != null) {
			existinglabRoom.setId(related_labroom.getId());
			existinglabRoom.setLabRoomName(related_labroom.getLabRoomName());
			existinglabRoom.setLabRoomNumber(related_labroom.getLabRoomNumber());
			existinglabRoom.setSort(related_labroom.getSort());
			existinglabRoom.setLabRoomEnName(related_labroom.getLabRoomEnName());
			existinglabRoom.setLabRoonAbbreviation(related_labroom.getLabRoonAbbreviation());
			//existinglabRoom.setLabRoomType(related_labroom.getLabRoomType());
			existinglabRoom.setLabRoomAddress(related_labroom.getLabRoomAddress());
			existinglabRoom.setSystemRoom(related_labroom.getSystemRoom());
			existinglabRoom.setLabRoomArea(related_labroom.getLabRoomArea());
			existinglabRoom.setLabRoomCapacity(related_labroom.getLabRoomCapacity());
			existinglabRoom.setLabRoomManagerAgencies(related_labroom.getLabRoomManagerAgencies());
//			existinglabRoom.setLabRoomSubject(related_labroom.getLabRoomSubject());
			existinglabRoom.setLabRoomPhone(related_labroom.getLabRoomPhone());
			//existinglabRoom.setLabRoomActive(related_labroom.getLabRoomActive());
			//existinglabRoom.setLabRoomReservation(related_labroom.getLabRoomReservation());
			//existinglabRoom.setLabRoomAudit(related_labroom.getLabRoomAudit());
			existinglabRoom.setLabRoomIntroduction(related_labroom.getLabRoomIntroduction());
			existinglabRoom.setLabRoomRegulations(related_labroom.getLabRoomRegulations());
			existinglabRoom.setLabRoomPrizeInformation(related_labroom.getLabRoomPrizeInformation());
			//existinglabRoom.setLabAnnexId(related_labroom.getLabAnnexId());
			existinglabRoom.setIsUsed(related_labroom.getIsUsed());
			related_labroom = existinglabRoom;
		} else {
			related_labroom = labRoomDAO.store(related_labroom);
			labRoomDAO.flush();
		}

		projectdevice.setLabRoom(related_labroom);
		related_labroom.getProjectDevices().add(projectdevice);
		projectdevice = projectDeviceDAO.store(projectdevice);
		projectDeviceDAO.flush();

		related_labroom = labRoomDAO.store(related_labroom);
		labRoomDAO.flush();

		return projectdevice;
	}

	/**
	 * Save an existing ProjectStartedReport entity
	 * 
	 */
	@Transactional
	public ProjectDevice saveProjectDeviceProjectStartedReport(Integer id, ProjectStartedReport related_projectstartedreport) {
		ProjectDevice projectdevice = projectDeviceDAO.findProjectDeviceByPrimaryKey(id, -1, -1);
		ProjectStartedReport existingprojectStartedReport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(related_projectstartedreport.getId());

		// copy into the existing record to preserve existing relationships
		if (existingprojectStartedReport != null) {
			existingprojectStartedReport.setId(related_projectstartedreport.getId());
			//existingprojectStartedReport.setProjectSourceId(related_projectstartedreport.getProjectSourceId());
			existingprojectStartedReport.setLabAddress(related_projectstartedreport.getLabAddress());
			existingprojectStartedReport.setLabArea(related_projectstartedreport.getLabArea());
			//existingprojectStartedReport.setFeeApproval(related_projectstartedreport.getFeeApproval());
			existingprojectStartedReport.setFeeCode(related_projectstartedreport.getFeeCode());
			//existingprojectStartedReport.setAppliantId(related_projectstartedreport.getAppliantId());
			existingprojectStartedReport.setStartDate(related_projectstartedreport.getStartDate());
			//existingprojectStartedReport.setOpenLabItem(related_projectstartedreport.getOpenLabItem());
			existingprojectStartedReport.setEquipmentList(related_projectstartedreport.getEquipmentList());
			existingprojectStartedReport.setFeeApprovalDetail(related_projectstartedreport.getFeeApprovalDetail());
			related_projectstartedreport = existingprojectStartedReport;
		} else {
			related_projectstartedreport = projectStartedReportDAO.store(related_projectstartedreport);
			projectStartedReportDAO.flush();
		}

		projectdevice.setProjectStartedReport(related_projectstartedreport);
		//related_projectstartedreport.getProjectDevices().add(projectdevice);
		projectdevice = projectDeviceDAO.store(projectdevice);
		projectDeviceDAO.flush();

		related_projectstartedreport = projectStartedReportDAO.store(related_projectstartedreport);
		projectStartedReportDAO.flush();

		return projectdevice;
	}

	/**
	 * Return a count of all ProjectDevice entity
	 * 
	 */
	@Transactional
	public Integer countProjectDevices() {
		return ((Long) projectDeviceDAO.createQuerySingleResult("select count(o) from ProjectDevice o").getSingleResult()).intValue();
	}

	/**
	 * Save an existing ProjectDevice entity
	 * 
	 */
	@Transactional
	public void saveProjectDevice(ProjectDevice projectdevice) {
		ProjectDevice existingProjectDevice = projectDeviceDAO.findProjectDeviceByPrimaryKey(projectdevice.getId());

		if (existingProjectDevice != null) {
			if (existingProjectDevice != projectdevice) {
				existingProjectDevice.setId(projectdevice.getId());
				existingProjectDevice.setEquipmentName(projectdevice.getEquipmentName());
				existingProjectDevice.setFormat(projectdevice.getFormat());
				existingProjectDevice.setAmount(projectdevice.getAmount());
				existingProjectDevice.setUnitPrice(projectdevice.getUnitPrice());
				existingProjectDevice.setCollection(projectdevice.getCollection());
				existingProjectDevice.setPurchasePattern(projectdevice.getPurchasePattern());
			}
			projectdevice = projectDeviceDAO.store(existingProjectDevice);
		} else {
			projectdevice = projectDeviceDAO.store(projectdevice);
		}
		projectDeviceDAO.flush();
	}

	/**
	 * Load an existing ProjectDevice entity
	 * 
	 */
	@Transactional
	public Set<ProjectDevice> loadProjectDevices() {
		return projectDeviceDAO.findAllProjectDevices();
	}

	/**
	 * Delete an existing ProjectDevice entity
	 * 
	 */
	@Transactional
	public void deleteProjectDevice(ProjectDevice projectdevice) {
		projectDeviceDAO.remove(projectdevice);
		projectDeviceDAO.flush();
	}
	
	/***************************************************************************************
	 * 功能：实验室建设项目-实验室建设设备明细对
	 * 作者：李德
	 * 时间：2015-04-03
	 **************************************************************************************/
	@Transactional
	public List<ProjectDevice> findProjectDeviceByAppKey(Integer id) {
		List<ProjectDevice> projectDevices = projectDeviceDAO
				.executeQuery("select a from ProjectDevice a where a.labConstructApp.id = "
						+ id + " order by a.id desc");
		return projectDevices;
	}
}
