package net.zjcclims.service.construction;


import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectStartCompletionItem entities
 * 
 */

@Service("ProjectStartCompletionItemService")
@Transactional
public class ProjectStartCompletionItemServiceImpl implements
		ProjectStartCompletionItemService {

	/**
	 * DAO injected by Spring that manages ProjectStartCompletionItem entities
	 * 
	 */
	@Autowired
	private ProjectStartCompletionItemDAO projectStartCompletionItemDAO;

	/**
	 * DAO injected by Spring that manages ProjectStartedReport entities
	 * 
	 */
	@Autowired
	private ProjectStartedReportDAO projectStartedReportDAO;

	/**
	 * Instantiates a new ProjectStartCompletionItemServiceImpl.
	 *
	 */
	public ProjectStartCompletionItemServiceImpl() {
	}

	/**
	 * Load an existing ProjectStartCompletionItem entity
	 * 
	 */
	@Transactional
	public Set<ProjectStartCompletionItem> loadProjectStartCompletionItems() {
		return projectStartCompletionItemDAO.findAllProjectStartCompletionItems();
	}

	/**
	 * Save an existing ProjectStartedReport entity
	 * 
	 */
	@Transactional
	public ProjectStartCompletionItem saveProjectStartCompletionItemProjectStartedReport(Integer id, ProjectStartedReport related_projectstartedreport) {
		ProjectStartCompletionItem projectstartcompletionitem = projectStartCompletionItemDAO.findProjectStartCompletionItemByPrimaryKey(id, -1, -1);
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

		projectstartcompletionitem.setProjectStartedReport(related_projectstartedreport);
		related_projectstartedreport.getProjectStartCompletionItems().add(projectstartcompletionitem);
		projectstartcompletionitem = projectStartCompletionItemDAO.store(projectstartcompletionitem);
		projectStartCompletionItemDAO.flush();

		related_projectstartedreport = projectStartedReportDAO.store(related_projectstartedreport);
		projectStartedReportDAO.flush();

		return projectstartcompletionitem;
	}

	/**
	 * Delete an existing ProjectStartCompletionItem entity
	 * 
	 */
	@Transactional
	public void deleteProjectStartCompletionItem(ProjectStartCompletionItem projectstartcompletionitem) {
		projectStartCompletionItemDAO.remove(projectstartcompletionitem);
		projectStartCompletionItemDAO.flush();
	}

	/**
	 * Delete an existing ProjectStartedReport entity
	 * 
	 */
	@Transactional
	public ProjectStartCompletionItem deleteProjectStartCompletionItemProjectStartedReport(Integer projectstartcompletionitem_id, Integer related_projectstartedreport_id) {
		ProjectStartCompletionItem projectstartcompletionitem = projectStartCompletionItemDAO.findProjectStartCompletionItemByPrimaryKey(projectstartcompletionitem_id, -1, -1);
		ProjectStartedReport related_projectstartedreport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(related_projectstartedreport_id, -1, -1);

		projectstartcompletionitem.setProjectStartedReport(null);
		related_projectstartedreport.getProjectStartCompletionItems().remove(projectstartcompletionitem);
		projectstartcompletionitem = projectStartCompletionItemDAO.store(projectstartcompletionitem);
		projectStartCompletionItemDAO.flush();

		related_projectstartedreport = projectStartedReportDAO.store(related_projectstartedreport);
		projectStartedReportDAO.flush();

		projectStartedReportDAO.remove(related_projectstartedreport);
		projectStartedReportDAO.flush();

		return projectstartcompletionitem;
	}

	/**
	 * Save an existing ProjectStartCompletionItem entity
	 * 
	 */
	@Transactional
	public void saveProjectStartCompletionItem(ProjectStartCompletionItem projectstartcompletionitem) {
		ProjectStartCompletionItem existingProjectStartCompletionItem = projectStartCompletionItemDAO.findProjectStartCompletionItemByPrimaryKey(projectstartcompletionitem.getId());

		if (existingProjectStartCompletionItem != null) {
			if (existingProjectStartCompletionItem != projectstartcompletionitem) {
				existingProjectStartCompletionItem.setId(projectstartcompletionitem.getId());
				existingProjectStartCompletionItem.setIsOriginal(projectstartcompletionitem.getIsOriginal());
				existingProjectStartCompletionItem.setExperimentName(projectstartcompletionitem.getExperimentName());
				existingProjectStartCompletionItem.setExperimentProperty(projectstartcompletionitem.getExperimentProperty());
				existingProjectStartCompletionItem.setRequiredHour(projectstartcompletionitem.getRequiredHour());
				existingProjectStartCompletionItem.setObjectItem(projectstartcompletionitem.getObjectItem());
				existingProjectStartCompletionItem.setEquipmentAmount(projectstartcompletionitem.getEquipmentAmount());
				existingProjectStartCompletionItem.setSimultaneouslyAmount(projectstartcompletionitem.getSimultaneouslyAmount());
				existingProjectStartCompletionItem.setPerGroupAmount(projectstartcompletionitem.getPerGroupAmount());
				existingProjectStartCompletionItem.setExperimentOutline(projectstartcompletionitem.getExperimentOutline());
				existingProjectStartCompletionItem.setExperimentInstructor(projectstartcompletionitem.getExperimentInstructor());
				existingProjectStartCompletionItem.setUseSitutation(projectstartcompletionitem.getUseSitutation());
				existingProjectStartCompletionItem.setStartReportId(projectstartcompletionitem.getStartReportId());
				existingProjectStartCompletionItem.setApprovalAppId(projectstartcompletionitem.getApprovalAppId());
			}
			projectstartcompletionitem = projectStartCompletionItemDAO.store(existingProjectStartCompletionItem);
		} else {
			projectstartcompletionitem = projectStartCompletionItemDAO.store(projectstartcompletionitem);
		}
		projectStartCompletionItemDAO.flush();
	}

	/**
	 * Return all ProjectStartCompletionItem entity
	 * 
	 */
	@Transactional
	public List<ProjectStartCompletionItem> findAllProjectStartCompletionItems(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<ProjectStartCompletionItem>(projectStartCompletionItemDAO.findAllProjectStartCompletionItems(startResult, maxRows));
	}

	/**
	 */
	@Transactional
	public ProjectStartCompletionItem findProjectStartCompletionItemByPrimaryKey(Integer id) {
		return projectStartCompletionItemDAO.findProjectStartCompletionItemByPrimaryKey(id);
	}

	/**
	 * Return a count of all ProjectStartCompletionItem entity
	 * 
	 */
	@Transactional
	public Integer countProjectStartCompletionItems() {
		return ((Long) projectStartCompletionItemDAO.createQuerySingleResult("select count(o) from ProjectStartCompletionItem o").getSingleResult()).intValue();
	}
	
	/****************************************************************************
	 * 功能：根据启动报告ID查询启动报告开设实验项目
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	@Override
	public List<ProjectStartCompletionItem> findProjectStartCompletionItemByProStartId(int idKey) {
		// TODO Auto-generated method stub
		String sql="select c from ProjectStartCompletionItem c where c.projectStartedReport.id =" +idKey;
		

		return projectStartCompletionItemDAO.executeQuery(sql,0,-1);
	}
	
	/****************************************************************************
	 * 功能：保存启动报告开设实验项目对象
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	@Override
	public ProjectStartCompletionItem save(ProjectStartCompletionItem projectStartCompletionItem) {
		// TODO Auto-generated method stub
		return projectStartCompletionItemDAO.store(projectStartCompletionItem);
	}
	
}
