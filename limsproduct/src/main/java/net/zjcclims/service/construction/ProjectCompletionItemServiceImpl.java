package net.zjcclims.service.construction;


import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectCompletionItem entities
 * 
 */

@Service("ProjectCompletionItemService")
@Transactional
public class ProjectCompletionItemServiceImpl implements
		ProjectCompletionItemService {

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * DAO injected by Spring that manages ProjectAcceptanceApplication entities
	 * 
	 */
	@Autowired
	private ProjectAcceptanceApplicationDAO projectAcceptanceApplicationDAO;

	/**
	 * DAO injected by Spring that manages ProjectCompletionItem entities
	 * 
	 */
	@Autowired
	private ProjectCompletionItemDAO projectCompletionItemDAO;

	/**
	 * DAO injected by Spring that manages ProjectStartedReport entities
	 * 
	 */
	@Autowired
	private ProjectStartedReportDAO projectStartedReportDAO;

	/**
	 * Instantiates a new ProjectCompletionItemServiceImpl.
	 *
	 */
	public ProjectCompletionItemServiceImpl() {
	}

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public ProjectCompletionItem saveProjectCompletionItemLabConstructApp(Integer id, LabConstructApp related_labconstructapp) {
		ProjectCompletionItem projectcompletionitem = projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(id, -1, -1);
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

		projectcompletionitem.setLabConstructApp(related_labconstructapp);
		related_labconstructapp.getProjectCompletionItems().add(projectcompletionitem);
		projectcompletionitem = projectCompletionItemDAO.store(projectcompletionitem);
		projectCompletionItemDAO.flush();

		related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
		labConstructAppDAO.flush();

		return projectcompletionitem;
	}

	/**
	 * Save an existing ProjectAcceptanceApplication entity
	 * 
	 */
	@Transactional
	public ProjectCompletionItem saveProjectCompletionItemProjectAcceptanceApplication(Integer id, ProjectAcceptanceApplication related_projectacceptanceapplication) {
		ProjectCompletionItem projectcompletionitem = projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(id, -1, -1);
		ProjectAcceptanceApplication existingprojectAcceptanceApplication = projectAcceptanceApplicationDAO.findProjectAcceptanceApplicationByPrimaryKey(related_projectacceptanceapplication.getId());

		// copy into the existing record to preserve existing relationships
		if (existingprojectAcceptanceApplication != null) {
			existingprojectAcceptanceApplication.setId(related_projectacceptanceapplication.getId());
			existingprojectAcceptanceApplication.setExpectCompleteDate(related_projectacceptanceapplication.getExpectCompleteDate());
			existingprojectAcceptanceApplication.setRealityCompleteDate(related_projectacceptanceapplication.getRealityCompleteDate());
			existingprojectAcceptanceApplication.setOriginalLabroomArea(related_projectacceptanceapplication.getOriginalLabroomArea());
			existingprojectAcceptanceApplication.setOriginalLabroomAdd(related_projectacceptanceapplication.getOriginalLabroomAdd());
			existingprojectAcceptanceApplication.setOriginalLabroomValue(related_projectacceptanceapplication.getOriginalLabroomValue());
			existingprojectAcceptanceApplication.setOriginalLabroomItem(related_projectacceptanceapplication.getOriginalLabroomItem());
			existingprojectAcceptanceApplication.setMajorAmount(related_projectacceptanceapplication.getMajorAmount());
			existingprojectAcceptanceApplication.setCourseAmount(related_projectacceptanceapplication.getCourseAmount());
			existingprojectAcceptanceApplication.setExpectLabItem(related_projectacceptanceapplication.getExpectLabItem());
			existingprojectAcceptanceApplication.setRealityLabItem(related_projectacceptanceapplication.getRealityLabItem());
			existingprojectAcceptanceApplication.setConstructCondition(related_projectacceptanceapplication.getConstructCondition());
			existingprojectAcceptanceApplication.setOpenLabItem(related_projectacceptanceapplication.getOpenLabItem());
			related_projectacceptanceapplication = existingprojectAcceptanceApplication;
		} else {
			related_projectacceptanceapplication = projectAcceptanceApplicationDAO.store(related_projectacceptanceapplication);
			projectAcceptanceApplicationDAO.flush();
		}

		projectcompletionitem.setProjectAcceptanceApplication(related_projectacceptanceapplication);
		//related_projectacceptanceapplication.getProjectCompletionItems().add(projectcompletionitem);
		projectcompletionitem = projectCompletionItemDAO.store(projectcompletionitem);
		projectCompletionItemDAO.flush();

		related_projectacceptanceapplication = projectAcceptanceApplicationDAO.store(related_projectacceptanceapplication);
		projectAcceptanceApplicationDAO.flush();

		return projectcompletionitem;
	}

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public ProjectCompletionItem deleteProjectCompletionItemLabConstructApp(Integer projectcompletionitem_id, Integer related_labconstructapp_id) {
		ProjectCompletionItem projectcompletionitem = projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(projectcompletionitem_id, -1, -1);
		LabConstructApp related_labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp_id, -1, -1);

		projectcompletionitem.setLabConstructApp(null);
		related_labconstructapp.getProjectCompletionItems().remove(projectcompletionitem);
		projectcompletionitem = projectCompletionItemDAO.store(projectcompletionitem);
		projectCompletionItemDAO.flush();

		related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
		labConstructAppDAO.flush();

		labConstructAppDAO.remove(related_labconstructapp);
		labConstructAppDAO.flush();

		return projectcompletionitem;
	}

	/**
	 * Load an existing ProjectCompletionItem entity
	 * 
	 */
	@Transactional
	public Set<ProjectCompletionItem> loadProjectCompletionItems() {
		return projectCompletionItemDAO.findAllProjectCompletionItems();
	}

	/**
	 * Save an existing ProjectStartedReport entity
	 * 
	 */
	@Transactional
	public ProjectCompletionItem saveProjectCompletionItemProjectStartedReports(Integer id, ProjectStartedReport related_projectstartedreports) {
		ProjectCompletionItem projectcompletionitem = projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(id, -1, -1);
		ProjectStartedReport existingprojectStartedReports = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(related_projectstartedreports.getId());

		// copy into the existing record to preserve existing relationships
		if (existingprojectStartedReports != null) {
			existingprojectStartedReports.setId(related_projectstartedreports.getId());
			//existingprojectStartedReports.setProjectSourceId(related_projectstartedreports.getProjectSourceId());
			existingprojectStartedReports.setLabAddress(related_projectstartedreports.getLabAddress());
			existingprojectStartedReports.setLabArea(related_projectstartedreports.getLabArea());
			//existingprojectStartedReports.setFeeApproval(related_projectstartedreports.getFeeApproval());
			existingprojectStartedReports.setFeeCode(related_projectstartedreports.getFeeCode());
			//existingprojectStartedReports.setAppliantId(related_projectstartedreports.getAppliantId());
			existingprojectStartedReports.setStartDate(related_projectstartedreports.getStartDate());
			existingprojectStartedReports.setEquipmentList(related_projectstartedreports.getEquipmentList());
			existingprojectStartedReports.setFeeApprovalDetail(related_projectstartedreports.getFeeApprovalDetail());
			related_projectstartedreports = existingprojectStartedReports;
		} else {
			related_projectstartedreports = projectStartedReportDAO.store(related_projectstartedreports);
			projectStartedReportDAO.flush();
		}

		//related_projectstartedreports.setProjectCompletionItem(projectcompletionitem);
		//projectcompletionitem.getProjectStartedReports().add(related_projectstartedreports);
		related_projectstartedreports = projectStartedReportDAO.store(related_projectstartedreports);
		projectStartedReportDAO.flush();

		projectcompletionitem = projectCompletionItemDAO.store(projectcompletionitem);
		projectCompletionItemDAO.flush();

		return projectcompletionitem;
	}

	/**
	 */
	@Transactional
	public ProjectCompletionItem findProjectCompletionItemByPrimaryKey(Integer id) {
		return projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(id);
	}

	/**
	 * Delete an existing ProjectAcceptanceApplication entity
	 * 
	 */
	@Transactional
	public ProjectCompletionItem deleteProjectCompletionItemProjectAcceptanceApplication(Integer projectcompletionitem_id, Integer related_projectacceptanceapplication_id) {
		ProjectCompletionItem projectcompletionitem = projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(projectcompletionitem_id, -1, -1);
		ProjectAcceptanceApplication related_projectacceptanceapplication = projectAcceptanceApplicationDAO.findProjectAcceptanceApplicationByPrimaryKey(related_projectacceptanceapplication_id, -1, -1);

		projectcompletionitem.setProjectAcceptanceApplication(null);
		//related_projectacceptanceapplication.getProjectCompletionItems().remove(projectcompletionitem);
		projectcompletionitem = projectCompletionItemDAO.store(projectcompletionitem);
		projectCompletionItemDAO.flush();

		related_projectacceptanceapplication = projectAcceptanceApplicationDAO.store(related_projectacceptanceapplication);
		projectAcceptanceApplicationDAO.flush();

		projectAcceptanceApplicationDAO.remove(related_projectacceptanceapplication);
		projectAcceptanceApplicationDAO.flush();

		return projectcompletionitem;
	}

	/**
	 * Delete an existing ProjectStartedReport entity
	 * 
	 */
	@Transactional
	public ProjectCompletionItem deleteProjectCompletionItemProjectStartedReports(Integer projectcompletionitem_id, Integer related_projectstartedreports_id) {
		ProjectStartedReport related_projectstartedreports = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(related_projectstartedreports_id, -1, -1);

		ProjectCompletionItem projectcompletionitem = projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(projectcompletionitem_id, -1, -1);

		//related_projectstartedreports.setProjectCompletionItem(null);
		//projectcompletionitem.getProjectStartedReports().remove(related_projectstartedreports);

		projectStartedReportDAO.remove(related_projectstartedreports);
		projectStartedReportDAO.flush();

		return projectcompletionitem;
	}

	/**
	 * Return all ProjectCompletionItem entity
	 * 
	 */
	@Transactional
	public List<ProjectCompletionItem> findAllProjectCompletionItems(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<ProjectCompletionItem>(projectCompletionItemDAO.findAllProjectCompletionItems(startResult, maxRows));
	}

	/**
	 * Delete an existing ProjectCompletionItem entity
	 * 
	 */
	@Transactional
	public void deleteProjectCompletionItem(ProjectCompletionItem projectcompletionitem) {
		projectCompletionItemDAO.remove(projectcompletionitem);
		projectCompletionItemDAO.flush();
	}

	/**
	 * Delete an existing ProjectStartedReport entity
	 * 
	 */
	@Transactional
	public ProjectCompletionItem deleteProjectCompletionItemProjectStartedReport(Integer projectcompletionitem_id, Integer related_projectstartedreport_id) {
		ProjectCompletionItem projectcompletionitem = projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(projectcompletionitem_id, -1, -1);
		ProjectStartedReport related_projectstartedreport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(related_projectstartedreport_id, -1, -1);

		projectcompletionitem.setProjectStartedReport(null);
		//related_projectstartedreport.getProjectCompletionItems().remove(projectcompletionitem);
		projectcompletionitem = projectCompletionItemDAO.store(projectcompletionitem);
		projectCompletionItemDAO.flush();

		related_projectstartedreport = projectStartedReportDAO.store(related_projectstartedreport);
		projectStartedReportDAO.flush();

		projectStartedReportDAO.remove(related_projectstartedreport);
		projectStartedReportDAO.flush();

		return projectcompletionitem;
	}

	/**
	 * Save an existing ProjectStartedReport entity
	 * 
	 */
	@Transactional
	public ProjectCompletionItem saveProjectCompletionItemProjectStartedReport(Integer id, ProjectStartedReport related_projectstartedreport) {
		ProjectCompletionItem projectcompletionitem = projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(id, -1, -1);
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
			existingprojectStartedReport.setEquipmentList(related_projectstartedreport.getEquipmentList());
			existingprojectStartedReport.setFeeApprovalDetail(related_projectstartedreport.getFeeApprovalDetail());
			related_projectstartedreport = existingprojectStartedReport;
		} else {
			related_projectstartedreport = projectStartedReportDAO.store(related_projectstartedreport);
			projectStartedReportDAO.flush();
		}

		projectcompletionitem.setProjectStartedReport(related_projectstartedreport);
		//related_projectstartedreport.getProjectCompletionItems().add(projectcompletionitem);
		projectcompletionitem = projectCompletionItemDAO.store(projectcompletionitem);
		projectCompletionItemDAO.flush();

		related_projectstartedreport = projectStartedReportDAO.store(related_projectstartedreport);
		projectStartedReportDAO.flush();

		return projectcompletionitem;
	}

	/**
	 * Return a count of all ProjectCompletionItem entity
	 * 
	 */
	@Transactional
	public Integer countProjectCompletionItems() {
		return ((Long) projectCompletionItemDAO.createQuerySingleResult("select count(o) from ProjectCompletionItem o").getSingleResult()).intValue();
	}

	/**
	 * Save an existing ProjectCompletionItem entity
	 * 
	 */
	@Transactional
	public void saveProjectCompletionItem(ProjectCompletionItem projectcompletionitem) {
		ProjectCompletionItem existingProjectCompletionItem = projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(projectcompletionitem.getId());

		if (existingProjectCompletionItem != null) {
			if (existingProjectCompletionItem != projectcompletionitem) {
				existingProjectCompletionItem.setId(projectcompletionitem.getId());
				existingProjectCompletionItem.setExperimentName(projectcompletionitem.getExperimentName());
				existingProjectCompletionItem.setExperimentProperty(projectcompletionitem.getExperimentProperty());
				existingProjectCompletionItem.setRequiredHour(projectcompletionitem.getRequiredHour());
				existingProjectCompletionItem.setObjectItem(projectcompletionitem.getObjectItem());
				existingProjectCompletionItem.setEquipmentAmount(projectcompletionitem.getEquipmentAmount());
				existingProjectCompletionItem.setSimultaneouslyAmount(projectcompletionitem.getSimultaneouslyAmount());
				existingProjectCompletionItem.setPerGroupAmount(projectcompletionitem.getPerGroupAmount());
				existingProjectCompletionItem.setExperimentOutline(projectcompletionitem.getExperimentOutline());
				existingProjectCompletionItem.setExperimentInstructor(projectcompletionitem.getExperimentInstructor());
				existingProjectCompletionItem.setUseSitutation(projectcompletionitem.getUseSitutation());
			}
			projectcompletionitem = projectCompletionItemDAO.store(existingProjectCompletionItem);
		} else {
			projectcompletionitem = projectCompletionItemDAO.store(projectcompletionitem);
		}
		projectCompletionItemDAO.flush();
	}
	
	/***************************************************************************************
	 * 功能：实验室建设项目-实验室建设可开设实验（实训）项目清单
	 * 作者：李德
	 * 时间：2015-04-03
	 **************************************************************************************/
	@Transactional
	public List<ProjectCompletionItem> findProjectCompletionItemByAppKey(Integer id) {
		List<ProjectCompletionItem> projectCompletionItems = projectCompletionItemDAO
				.executeQuery("select a from ProjectCompletionItem a where a.labConstructApp.id = "
						+ id + " order by a.id desc");
		return projectCompletionItems;
	}
	
}
