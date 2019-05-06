package net.zjcclims.service.construction;


import net.zjcclims.dao.ProjectAcceptCompletionItemDAO;
import net.zjcclims.dao.ProjectAcceptanceApplicationDAO;
import net.zjcclims.domain.ProjectAcceptCompletionItem;
import net.zjcclims.domain.ProjectAcceptanceApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectAcceptCompletionItem entities
 * 
 */

@Service("ProjectAcceptCompletionItemService")
@Transactional
public class ProjectAcceptCompletionItemServiceImpl implements
		ProjectAcceptCompletionItemService {

	/**
	 * DAO injected by Spring that manages ProjectAcceptCompletionItem entities
	 * 
	 */
	@Autowired
	private ProjectAcceptCompletionItemDAO projectAcceptCompletionItemDAO;

	/**
	 * DAO injected by Spring that manages ProjectAcceptanceApplication entities
	 * 
	 */
	@Autowired
	private ProjectAcceptanceApplicationDAO projectAcceptanceApplicationDAO;

	/**
	 * Instantiates a new ProjectAcceptCompletionItemServiceImpl.
	 *
	 */
	public ProjectAcceptCompletionItemServiceImpl() {
	}

	/**
	 * Save an existing ProjectAcceptCompletionItem entity
	 * 
	 */
	@Transactional
	public void saveProjectAcceptCompletionItem(ProjectAcceptCompletionItem projectacceptcompletionitem) {
		ProjectAcceptCompletionItem existingProjectAcceptCompletionItem = projectAcceptCompletionItemDAO.findProjectAcceptCompletionItemByPrimaryKey(projectacceptcompletionitem.getId());

		if (existingProjectAcceptCompletionItem != null) {
			if (existingProjectAcceptCompletionItem != projectacceptcompletionitem) {
				existingProjectAcceptCompletionItem.setId(projectacceptcompletionitem.getId());
				existingProjectAcceptCompletionItem.setIsOriginal(projectacceptcompletionitem.getIsOriginal());
				existingProjectAcceptCompletionItem.setExperimentName(projectacceptcompletionitem.getExperimentName());
				existingProjectAcceptCompletionItem.setExperimentProperty(projectacceptcompletionitem.getExperimentProperty());
				existingProjectAcceptCompletionItem.setRequiredHour(projectacceptcompletionitem.getRequiredHour());
				existingProjectAcceptCompletionItem.setObjectItem(projectacceptcompletionitem.getObjectItem());
				existingProjectAcceptCompletionItem.setEquipmentAmount(projectacceptcompletionitem.getEquipmentAmount());
				existingProjectAcceptCompletionItem.setSimultaneouslyAmount(projectacceptcompletionitem.getSimultaneouslyAmount());
				existingProjectAcceptCompletionItem.setPerGroupAmount(projectacceptcompletionitem.getPerGroupAmount());
				existingProjectAcceptCompletionItem.setExperimentOutline(projectacceptcompletionitem.getExperimentOutline());
				existingProjectAcceptCompletionItem.setExperimentInstructor(projectacceptcompletionitem.getExperimentInstructor());
				existingProjectAcceptCompletionItem.setUseSitutation(projectacceptcompletionitem.getUseSitutation());
				existingProjectAcceptCompletionItem.setStartReportId(projectacceptcompletionitem.getStartReportId());
				existingProjectAcceptCompletionItem.setApprovalAppId(projectacceptcompletionitem.getApprovalAppId());
			}
			projectacceptcompletionitem = projectAcceptCompletionItemDAO.store(existingProjectAcceptCompletionItem);
		} else {
			projectacceptcompletionitem = projectAcceptCompletionItemDAO.store(projectacceptcompletionitem);
		}
		projectAcceptCompletionItemDAO.flush();
	}

	/**
	 * Load an existing ProjectAcceptCompletionItem entity
	 * 
	 */
	@Transactional
	public Set<ProjectAcceptCompletionItem> loadProjectAcceptCompletionItems() {
		return projectAcceptCompletionItemDAO.findAllProjectAcceptCompletionItems();
	}

	/**
	 * Save an existing ProjectAcceptanceApplication entity
	 * 
	 */
	@Transactional
	public ProjectAcceptCompletionItem saveProjectAcceptCompletionItemProjectAcceptanceApplication(Integer id, ProjectAcceptanceApplication related_projectacceptanceapplication) {
		ProjectAcceptCompletionItem projectacceptcompletionitem = projectAcceptCompletionItemDAO.findProjectAcceptCompletionItemByPrimaryKey(id, -1, -1);
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

		projectacceptcompletionitem.setProjectAcceptanceApplication(related_projectacceptanceapplication);
		related_projectacceptanceapplication.getProjectAcceptCompletionItems().add(projectacceptcompletionitem);
		projectacceptcompletionitem = projectAcceptCompletionItemDAO.store(projectacceptcompletionitem);
		projectAcceptCompletionItemDAO.flush();

		related_projectacceptanceapplication = projectAcceptanceApplicationDAO.store(related_projectacceptanceapplication);
		projectAcceptanceApplicationDAO.flush();

		return projectacceptcompletionitem;
	}

	/**
	 */
	@Transactional
	public ProjectAcceptCompletionItem findProjectAcceptCompletionItemByPrimaryKey(Integer id) {
		return projectAcceptCompletionItemDAO.findProjectAcceptCompletionItemByPrimaryKey(id);
	}

	/**
	 * Return all ProjectAcceptCompletionItem entity
	 * 
	 */
	@Transactional
	public List<ProjectAcceptCompletionItem> findAllProjectAcceptCompletionItems(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<ProjectAcceptCompletionItem>(projectAcceptCompletionItemDAO.findAllProjectAcceptCompletionItems(startResult, maxRows));
	}

	/**
	 * Return a count of all ProjectAcceptCompletionItem entity
	 * 
	 */
	@Transactional
	public Integer countProjectAcceptCompletionItems() {
		return ((Long) projectAcceptCompletionItemDAO.createQuerySingleResult("select count(o) from ProjectAcceptCompletionItem o").getSingleResult()).intValue();
	}

	/**
	 * Delete an existing ProjectAcceptanceApplication entity
	 * 
	 */
	@Transactional
	public ProjectAcceptCompletionItem deleteProjectAcceptCompletionItemProjectAcceptanceApplication(Integer projectacceptcompletionitem_id, Integer related_projectacceptanceapplication_id) {
		ProjectAcceptCompletionItem projectacceptcompletionitem = projectAcceptCompletionItemDAO.findProjectAcceptCompletionItemByPrimaryKey(projectacceptcompletionitem_id, -1, -1);
		ProjectAcceptanceApplication related_projectacceptanceapplication = projectAcceptanceApplicationDAO.findProjectAcceptanceApplicationByPrimaryKey(related_projectacceptanceapplication_id, -1, -1);

		projectacceptcompletionitem.setProjectAcceptanceApplication(null);
		related_projectacceptanceapplication.getProjectAcceptCompletionItems().remove(projectacceptcompletionitem);
		projectacceptcompletionitem = projectAcceptCompletionItemDAO.store(projectacceptcompletionitem);
		projectAcceptCompletionItemDAO.flush();

		related_projectacceptanceapplication = projectAcceptanceApplicationDAO.store(related_projectacceptanceapplication);
		projectAcceptanceApplicationDAO.flush();

		projectAcceptanceApplicationDAO.remove(related_projectacceptanceapplication);
		projectAcceptanceApplicationDAO.flush();

		return projectacceptcompletionitem;
	}

	/**
	 * Delete an existing ProjectAcceptCompletionItem entity
	 * 
	 */
	@Transactional
	public void deleteProjectAcceptCompletionItem(ProjectAcceptCompletionItem projectacceptcompletionitem) {
		projectAcceptCompletionItemDAO.remove(projectacceptcompletionitem);
		projectAcceptCompletionItemDAO.flush();
	}
	
	/****************************************************************************
	 * 功能：根据验收申请ID查询验收申请开设实验项目
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	@Override
	public List<ProjectAcceptCompletionItem> findProjectAcceptCompletionItemByProAppId(int idKey) {
		// TODO Auto-generated method stub
		String sql="select c from ProjectAcceptCompletionItem c where c.projectAcceptanceApplication.id =" +idKey;
		

		return projectAcceptCompletionItemDAO.executeQuery(sql,0,-1);
	}
	
	/****************************************************************************
	 * 功能：保存验收申请开设实验项目对象
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	@Override
	public ProjectAcceptCompletionItem save(ProjectAcceptCompletionItem projectAcceptCompletionItem) {
		// TODO Auto-generated method stub
		return projectAcceptCompletionItemDAO.store(projectAcceptCompletionItem);
	}
}
