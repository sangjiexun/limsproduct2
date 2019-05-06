package net.zjcclims.service.construction;


import net.zjcclims.dao.CFundAppItemDAO;
import net.zjcclims.dao.ProjectAcceptFeeListDAO;
import net.zjcclims.dao.ProjectAcceptanceApplicationDAO;
import net.zjcclims.domain.CFundAppItem;
import net.zjcclims.domain.ProjectAcceptFeeList;
import net.zjcclims.domain.ProjectAcceptanceApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectAcceptFeeList entities
 * 
 */

@Service("ProjectAcceptFeeListService")
@Transactional
public class ProjectAcceptFeeListServiceImpl implements
		ProjectAcceptFeeListService {

	/**
	 * DAO injected by Spring that manages CFundAppItem entities
	 * 
	 */
	@Autowired
	private CFundAppItemDAO cFundAppItemDAO;

	/**
	 * DAO injected by Spring that manages ProjectAcceptFeeList entities
	 * 
	 */
	@Autowired
	private ProjectAcceptFeeListDAO projectAcceptFeeListDAO;

	/**
	 * DAO injected by Spring that manages ProjectAcceptanceApplication entities
	 * 
	 */
	@Autowired
	private ProjectAcceptanceApplicationDAO projectAcceptanceApplicationDAO;

	/**
	 * Instantiates a new ProjectAcceptFeeListServiceImpl.
	 *
	 */
	public ProjectAcceptFeeListServiceImpl() {
	}

	/**
	 * Return a count of all ProjectAcceptFeeList entity
	 * 
	 */
	@Transactional
	public Integer countProjectAcceptFeeLists() {
		return ((Long) projectAcceptFeeListDAO.createQuerySingleResult("select count(o) from ProjectAcceptFeeList o").getSingleResult()).intValue();
	}

	/**
	 * Delete an existing ProjectAcceptanceApplication entity
	 * 
	 */
	@Transactional
	public ProjectAcceptFeeList deleteProjectAcceptFeeListProjectAcceptanceApplication(Integer projectacceptfeelist_id, Integer related_projectacceptanceapplication_id) {
		ProjectAcceptFeeList projectacceptfeelist = projectAcceptFeeListDAO.findProjectAcceptFeeListByPrimaryKey(projectacceptfeelist_id, -1, -1);
		ProjectAcceptanceApplication related_projectacceptanceapplication = projectAcceptanceApplicationDAO.findProjectAcceptanceApplicationByPrimaryKey(related_projectacceptanceapplication_id, -1, -1);

		projectacceptfeelist.setProjectAcceptanceApplication(null);
		related_projectacceptanceapplication.getProjectAcceptFeeLists().remove(projectacceptfeelist);
		projectacceptfeelist = projectAcceptFeeListDAO.store(projectacceptfeelist);
		projectAcceptFeeListDAO.flush();

		related_projectacceptanceapplication = projectAcceptanceApplicationDAO.store(related_projectacceptanceapplication);
		projectAcceptanceApplicationDAO.flush();

		projectAcceptanceApplicationDAO.remove(related_projectacceptanceapplication);
		projectAcceptanceApplicationDAO.flush();

		return projectacceptfeelist;
	}

	/**
	 * Load an existing ProjectAcceptFeeList entity
	 * 
	 */
	@Transactional
	public Set<ProjectAcceptFeeList> loadProjectAcceptFeeLists() {
		return projectAcceptFeeListDAO.findAllProjectAcceptFeeLists();
	}

	/**
	 * Save an existing ProjectAcceptanceApplication entity
	 * 
	 */
	@Transactional
	public ProjectAcceptFeeList saveProjectAcceptFeeListProjectAcceptanceApplication(Integer id, ProjectAcceptanceApplication related_projectacceptanceapplication) {
		ProjectAcceptFeeList projectacceptfeelist = projectAcceptFeeListDAO.findProjectAcceptFeeListByPrimaryKey(id, -1, -1);
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

		projectacceptfeelist.setProjectAcceptanceApplication(related_projectacceptanceapplication);
		related_projectacceptanceapplication.getProjectAcceptFeeLists().add(projectacceptfeelist);
		projectacceptfeelist = projectAcceptFeeListDAO.store(projectacceptfeelist);
		projectAcceptFeeListDAO.flush();

		related_projectacceptanceapplication = projectAcceptanceApplicationDAO.store(related_projectacceptanceapplication);
		projectAcceptanceApplicationDAO.flush();

		return projectacceptfeelist;
	}

	/**
	 */
	@Transactional
	public ProjectAcceptFeeList findProjectAcceptFeeListByPrimaryKey(Integer id) {
		return projectAcceptFeeListDAO.findProjectAcceptFeeListByPrimaryKey(id);
	}

	/**
	 * Save an existing CFundAppItem entity
	 * 
	 */
	@Transactional
	public ProjectAcceptFeeList saveProjectAcceptFeeListCFundAppItem(Integer id, CFundAppItem related_cfundappitem) {
		ProjectAcceptFeeList projectacceptfeelist = projectAcceptFeeListDAO.findProjectAcceptFeeListByPrimaryKey(id, -1, -1);
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

		projectacceptfeelist.setCFundAppItem(related_cfundappitem);
		related_cfundappitem.getProjectAcceptFeeLists().add(projectacceptfeelist);
		projectacceptfeelist = projectAcceptFeeListDAO.store(projectacceptfeelist);
		projectAcceptFeeListDAO.flush();

		related_cfundappitem = cFundAppItemDAO.store(related_cfundappitem);
		cFundAppItemDAO.flush();

		return projectacceptfeelist;
	}

	/**
	 * Delete an existing ProjectAcceptFeeList entity
	 * 
	 */
	@Transactional
	public void deleteProjectAcceptFeeList(ProjectAcceptFeeList projectacceptfeelist) {
		projectAcceptFeeListDAO.remove(projectacceptfeelist);
		projectAcceptFeeListDAO.flush();
	}

	/**
	 * Return all ProjectAcceptFeeList entity
	 * 
	 */
	@Transactional
	public List<ProjectAcceptFeeList> findAllProjectAcceptFeeLists(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<ProjectAcceptFeeList>(projectAcceptFeeListDAO.findAllProjectAcceptFeeLists(startResult, maxRows));
	}

	/**
	 * Delete an existing CFundAppItem entity
	 * 
	 */
	@Transactional
	public ProjectAcceptFeeList deleteProjectAcceptFeeListCFundAppItem(Integer projectacceptfeelist_id, Integer related_cfundappitem_id) {
		ProjectAcceptFeeList projectacceptfeelist = projectAcceptFeeListDAO.findProjectAcceptFeeListByPrimaryKey(projectacceptfeelist_id, -1, -1);
		CFundAppItem related_cfundappitem = cFundAppItemDAO.findCFundAppItemByPrimaryKey(related_cfundappitem_id, -1, -1);

		projectacceptfeelist.setCFundAppItem(null);
		related_cfundappitem.getProjectAcceptFeeLists().remove(projectacceptfeelist);
		projectacceptfeelist = projectAcceptFeeListDAO.store(projectacceptfeelist);
		projectAcceptFeeListDAO.flush();

		related_cfundappitem = cFundAppItemDAO.store(related_cfundappitem);
		cFundAppItemDAO.flush();

		cFundAppItemDAO.remove(related_cfundappitem);
		cFundAppItemDAO.flush();

		return projectacceptfeelist;
	}

	/**
	 * Save an existing ProjectAcceptFeeList entity
	 * 
	 */
	@Transactional
	public void saveProjectAcceptFeeList(ProjectAcceptFeeList projectacceptfeelist) {
		ProjectAcceptFeeList existingProjectAcceptFeeList = projectAcceptFeeListDAO.findProjectAcceptFeeListByPrimaryKey(projectacceptfeelist.getId());

		if (existingProjectAcceptFeeList != null) {
			if (existingProjectAcceptFeeList != projectacceptfeelist) {
				existingProjectAcceptFeeList.setId(projectacceptfeelist.getId());
				existingProjectAcceptFeeList.setOtherFundsSource(projectacceptfeelist.getOtherFundsSource());
				existingProjectAcceptFeeList.setBudgetaryItem(projectacceptfeelist.getBudgetaryItem());
				existingProjectAcceptFeeList.setAmount(projectacceptfeelist.getAmount());
			}
			projectacceptfeelist = projectAcceptFeeListDAO.store(existingProjectAcceptFeeList);
		} else {
			projectacceptfeelist = projectAcceptFeeListDAO.store(projectacceptfeelist);
		}
		projectAcceptFeeListDAO.flush();
	}
	
	/****************************************************************************
	 * 功能：根据验收申请ID查询验收申请经费
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	@Override
	public List<ProjectAcceptFeeList> findProjectAcceptFeeListByProAppId(int idKey) {
		// TODO Auto-generated method stub
		System.out.println(idKey);
		String sql="select c from ProjectAcceptFeeList c where c.projectAcceptanceApplication.id =" +idKey;
		

		return projectAcceptFeeListDAO.executeQuery(sql,0,-1);
	}
	
	/****************************************************************************
	 * 功能：保存验收申请经费
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	@Override
	public ProjectAcceptFeeList save(ProjectAcceptFeeList projectAcceptFeeList) {
		// TODO Auto-generated method stub
		return projectAcceptFeeListDAO.store(projectAcceptFeeList);
	}
}
