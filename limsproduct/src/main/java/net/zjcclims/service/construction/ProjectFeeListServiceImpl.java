package net.zjcclims.service.construction;


import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectFeeList entities
 * 
 */

@Service("ProjectFeeListService")
@Transactional
public class ProjectFeeListServiceImpl implements ProjectFeeListService {

	/**
	 * DAO injected by Spring that manages CFundAppItem entities
	 * 
	 */
	@Autowired
	private CFundAppItemDAO cFundAppItemDAO;

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * DAO injected by Spring that manages ProjectFeeList entities
	 * 
	 */
	@Autowired
	private ProjectFeeListDAO projectFeeListDAO;

	/**
	 * Instantiates a new ProjectFeeListServiceImpl.
	 *
	 */
	public ProjectFeeListServiceImpl() {
	}

	/**
	 */
	@Transactional
	public ProjectFeeList findProjectFeeListByPrimaryKey(Integer id) {
		return projectFeeListDAO.findProjectFeeListByPrimaryKey(id);
	}

	/**
	 * Return all ProjectFeeList entity
	 * 
	 */
	@Transactional
	public List<ProjectFeeList> findAllProjectFeeLists(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<ProjectFeeList>(projectFeeListDAO.findAllProjectFeeLists(startResult, maxRows));
	}

	/**
	 * Delete an existing CFundAppItem entity
	 * 
	 */
	@Transactional
	public ProjectFeeList deleteProjectFeeListCFundAppItem(Integer projectfeelist_id, Integer related_cfundappitem_id) {
		ProjectFeeList projectfeelist = projectFeeListDAO.findProjectFeeListByPrimaryKey(projectfeelist_id, -1, -1);
		CFundAppItem related_cfundappitem = cFundAppItemDAO.findCFundAppItemByPrimaryKey(related_cfundappitem_id, -1, -1);

		projectfeelist.setCFundAppItem(null);
		//related_cfundappitem.getProjectFeeLists().remove(projectfeelist);
		projectfeelist = projectFeeListDAO.store(projectfeelist);
		projectFeeListDAO.flush();

		related_cfundappitem = cFundAppItemDAO.store(related_cfundappitem);
		cFundAppItemDAO.flush();

		cFundAppItemDAO.remove(related_cfundappitem);
		cFundAppItemDAO.flush();

		return projectfeelist;
	}

	/**
	 * Save an existing CFundAppItem entity
	 * 
	 */
	@Transactional
	public ProjectFeeList saveProjectFeeListCFundAppItem(Integer id, CFundAppItem related_cfundappitem) {
		ProjectFeeList projectfeelist = projectFeeListDAO.findProjectFeeListByPrimaryKey(id, -1, -1);
		CFundAppItem existingCFundAppItem = cFundAppItemDAO.findCFundAppItemByPrimaryKey(related_cfundappitem.getId());

		// copy into the existing record to preserve existing relationships
		if (existingCFundAppItem != null) {
			existingCFundAppItem.setId(related_cfundappitem.getId());
			existingCFundAppItem.setName(related_cfundappitem.getName());
			related_cfundappitem = existingCFundAppItem;
		} else {
			related_cfundappitem = cFundAppItemDAO.store(related_cfundappitem);
			cFundAppItemDAO.flush();
		}

		projectfeelist.setCFundAppItem(related_cfundappitem);
		//related_cfundappitem.getProjectFeeLists().add(projectfeelist);
		projectfeelist = projectFeeListDAO.store(projectfeelist);
		projectFeeListDAO.flush();

		related_cfundappitem = cFundAppItemDAO.store(related_cfundappitem);
		cFundAppItemDAO.flush();

		return projectfeelist;
	}

	/**
	 * Load an existing ProjectFeeList entity
	 * 
	 */
	@Transactional
	public Set<ProjectFeeList> loadProjectFeeLists() {
		return projectFeeListDAO.findAllProjectFeeLists();
	}

	/**
	 * Save an existing ProjectFeeList entity
	 * 
	 */
	@Transactional
	public void saveProjectFeeList(ProjectFeeList projectfeelist) {
		ProjectFeeList existingProjectFeeList = projectFeeListDAO.findProjectFeeListByPrimaryKey(projectfeelist.getId());

		if (existingProjectFeeList != null) {
			if (existingProjectFeeList != projectfeelist) {
				existingProjectFeeList.setId(projectfeelist.getId());
				existingProjectFeeList.setOtherFundsSource(projectfeelist.getOtherFundsSource());
				existingProjectFeeList.setBudgetaryItem(projectfeelist.getBudgetaryItem());
				existingProjectFeeList.setAmount(projectfeelist.getAmount());
			}
			projectfeelist = projectFeeListDAO.store(existingProjectFeeList);
		} else {
			projectfeelist = projectFeeListDAO.store(projectfeelist);
		}
		projectFeeListDAO.flush();
	}

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public ProjectFeeList deleteProjectFeeListLabConstructApp(Integer projectfeelist_id, Integer related_labconstructapp_id) {
		ProjectFeeList projectfeelist = projectFeeListDAO.findProjectFeeListByPrimaryKey(projectfeelist_id, -1, -1);
		LabConstructApp related_labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp_id, -1, -1);

		projectfeelist.setLabConstructApp(null);
		related_labconstructapp.getProjectFeeLists().remove(projectfeelist);
		projectfeelist = projectFeeListDAO.store(projectfeelist);
		projectFeeListDAO.flush();

		related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
		labConstructAppDAO.flush();

		labConstructAppDAO.remove(related_labconstructapp);
		labConstructAppDAO.flush();

		return projectfeelist;
	}

	/**
	 * Delete an existing ProjectFeeList entity
	 * 
	 */
	@Transactional
	public void deleteProjectFeeList(ProjectFeeList projectfeelist) {
		projectFeeListDAO.remove(projectfeelist);
		projectFeeListDAO.flush();
	}

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public ProjectFeeList saveProjectFeeListLabConstructApp(Integer id, LabConstructApp related_labconstructapp) {
		ProjectFeeList projectfeelist = projectFeeListDAO.findProjectFeeListByPrimaryKey(id, -1, -1);
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
			existinglabConstructApp.setPlanSchedule(related_labconstructapp.getPlanSchedule());
			related_labconstructapp = existinglabConstructApp;
		} else {
			related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
			labConstructAppDAO.flush();
		}

		projectfeelist.setLabConstructApp(related_labconstructapp);
		related_labconstructapp.getProjectFeeLists().add(projectfeelist);
		projectfeelist = projectFeeListDAO.store(projectfeelist);
		projectFeeListDAO.flush();

		related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
		labConstructAppDAO.flush();

		return projectfeelist;
	}

	/**
	 * Return a count of all ProjectFeeList entity
	 * 
	 */
	@Transactional
	public Integer countProjectFeeLists() {
		return ((Long) projectFeeListDAO.createQuerySingleResult("select count(o) from ProjectFeeList o").getSingleResult()).intValue();
	}
	
	/***************************************************************************************
	 * 功能：实验室建设项目-实验室建设项目参加人员
	 * 作者：李德
	 * 时间：2015-04-03
	 **************************************************************************************/
	@Transactional
	public List<ProjectFeeList> findProjectFeeListByAppKey(Integer id) {
		List<ProjectFeeList> projectFeeLists = projectFeeListDAO
				.executeQuery("select a from ProjectFeeList a where a.labConstructApp.id = "
						+ id + " order by a.id desc");
		return projectFeeLists;
	}
}
