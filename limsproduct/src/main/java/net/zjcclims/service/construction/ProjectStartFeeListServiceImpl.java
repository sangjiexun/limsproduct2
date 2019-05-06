package net.zjcclims.service.construction;


import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectStartFeeList entities
 * 
 */

@Service("ProjectStartFeeListService")
@Transactional
public class ProjectStartFeeListServiceImpl implements
		ProjectStartFeeListService {

	/**
	 * DAO injected by Spring that manages CFundAppItem entities
	 * 
	 */
	@Autowired
	private CFundAppItemDAO cFundAppItemDAO;

	/**
	 * DAO injected by Spring that manages ProjectStartFeeList entities
	 * 
	 */
	@Autowired
	private ProjectStartFeeListDAO projectStartFeeListDAO;

	/**
	 * DAO injected by Spring that manages ProjectStartedReport entities
	 * 
	 */
	@Autowired
	private ProjectStartedReportDAO projectStartedReportDAO;

	/**
	 * Instantiates a new ProjectStartFeeListServiceImpl.
	 *
	 */
	public ProjectStartFeeListServiceImpl() {
	}

	/**
	 * Delete an existing ProjectStartedReport entity
	 * 
	 */
	@Transactional
	public ProjectStartFeeList deleteProjectStartFeeListProjectStartedReport(Integer projectstartfeelist_id, Integer related_projectstartedreport_id) {
		ProjectStartFeeList projectstartfeelist = projectStartFeeListDAO.findProjectStartFeeListByPrimaryKey(projectstartfeelist_id, -1, -1);
		ProjectStartedReport related_projectstartedreport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(related_projectstartedreport_id, -1, -1);

		projectstartfeelist.setProjectStartedReport(null);
		related_projectstartedreport.getProjectStartFeeLists().remove(projectstartfeelist);
		projectstartfeelist = projectStartFeeListDAO.store(projectstartfeelist);
		projectStartFeeListDAO.flush();

		related_projectstartedreport = projectStartedReportDAO.store(related_projectstartedreport);
		projectStartedReportDAO.flush();

		projectStartedReportDAO.remove(related_projectstartedreport);
		projectStartedReportDAO.flush();

		return projectstartfeelist;
	}

	/**
	 * Delete an existing CFundAppItem entity
	 * 
	 */
	@Transactional
	public ProjectStartFeeList deleteProjectStartFeeListCFundAppItem(Integer projectstartfeelist_id, Integer related_cfundappitem_id) {
		ProjectStartFeeList projectstartfeelist = projectStartFeeListDAO.findProjectStartFeeListByPrimaryKey(projectstartfeelist_id, -1, -1);
		CFundAppItem related_cfundappitem = cFundAppItemDAO.findCFundAppItemByPrimaryKey(related_cfundappitem_id, -1, -1);

		projectstartfeelist.setCFundAppItem(null);
		related_cfundappitem.getProjectStartFeeLists().remove(projectstartfeelist);
		projectstartfeelist = projectStartFeeListDAO.store(projectstartfeelist);
		projectStartFeeListDAO.flush();

		related_cfundappitem = cFundAppItemDAO.store(related_cfundappitem);
		cFundAppItemDAO.flush();

		cFundAppItemDAO.remove(related_cfundappitem);
		cFundAppItemDAO.flush();

		return projectstartfeelist;
	}

	/**
	 * Save an existing ProjectStartedReport entity
	 * 
	 */
	@Transactional
	public ProjectStartFeeList saveProjectStartFeeListProjectStartedReport(Integer id, ProjectStartedReport related_projectstartedreport) {
		ProjectStartFeeList projectstartfeelist = projectStartFeeListDAO.findProjectStartFeeListByPrimaryKey(id, -1, -1);
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

		projectstartfeelist.setProjectStartedReport(related_projectstartedreport);
		related_projectstartedreport.getProjectStartFeeLists().add(projectstartfeelist);
		projectstartfeelist = projectStartFeeListDAO.store(projectstartfeelist);
		projectStartFeeListDAO.flush();

		related_projectstartedreport = projectStartedReportDAO.store(related_projectstartedreport);
		projectStartedReportDAO.flush();

		return projectstartfeelist;
	}

	/**
	 * Save an existing ProjectStartFeeList entity
	 * 
	 */
	@Transactional
	public void saveProjectStartFeeList(ProjectStartFeeList projectstartfeelist) {
		ProjectStartFeeList existingProjectStartFeeList = projectStartFeeListDAO.findProjectStartFeeListByPrimaryKey(projectstartfeelist.getId());

		if (existingProjectStartFeeList != null) {
			if (existingProjectStartFeeList != projectstartfeelist) {
				existingProjectStartFeeList.setId(projectstartfeelist.getId());
				existingProjectStartFeeList.setOtherFundsSource(projectstartfeelist.getOtherFundsSource());
				existingProjectStartFeeList.setBudgetaryItem(projectstartfeelist.getBudgetaryItem());
				existingProjectStartFeeList.setAmount(projectstartfeelist.getAmount());
			}
			projectstartfeelist = projectStartFeeListDAO.store(existingProjectStartFeeList);
		} else {
			projectstartfeelist = projectStartFeeListDAO.store(projectstartfeelist);
		}
		projectStartFeeListDAO.flush();
	}

	/**
	 * Delete an existing ProjectStartFeeList entity
	 * 
	 */
	@Transactional
	public void deleteProjectStartFeeList(ProjectStartFeeList projectstartfeelist) {
		projectStartFeeListDAO.remove(projectstartfeelist);
		projectStartFeeListDAO.flush();
	}

	/**
	 * Return a count of all ProjectStartFeeList entity
	 * 
	 */
	@Transactional
	public Integer countProjectStartFeeLists() {
		return ((Long) projectStartFeeListDAO.createQuerySingleResult("select count(o) from ProjectStartFeeList o").getSingleResult()).intValue();
	}

	/**
	 */
	@Transactional
	public ProjectStartFeeList findProjectStartFeeListByPrimaryKey(Integer id) {
		return projectStartFeeListDAO.findProjectStartFeeListByPrimaryKey(id);
	}

	/**
	 * Save an existing CFundAppItem entity
	 * 
	 */
	@Transactional
	public ProjectStartFeeList saveProjectStartFeeListCFundAppItem(Integer id, CFundAppItem related_cfundappitem) {
		ProjectStartFeeList projectstartfeelist = projectStartFeeListDAO.findProjectStartFeeListByPrimaryKey(id, -1, -1);
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

		projectstartfeelist.setCFundAppItem(related_cfundappitem);
		related_cfundappitem.getProjectStartFeeLists().add(projectstartfeelist);
		projectstartfeelist = projectStartFeeListDAO.store(projectstartfeelist);
		projectStartFeeListDAO.flush();

		related_cfundappitem = cFundAppItemDAO.store(related_cfundappitem);
		cFundAppItemDAO.flush();

		return projectstartfeelist;
	}

	/**
	 * Load an existing ProjectStartFeeList entity
	 * 
	 */
	@Transactional
	public Set<ProjectStartFeeList> loadProjectStartFeeLists() {
		return projectStartFeeListDAO.findAllProjectStartFeeLists();
	}

	/**
	 * Return all ProjectStartFeeList entity
	 * 
	 */
	@Transactional
	public List<ProjectStartFeeList> findAllProjectStartFeeLists(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<ProjectStartFeeList>(projectStartFeeListDAO.findAllProjectStartFeeLists(startResult, maxRows));
	}
	
	/****************************************************************************
	 * 功能：根据申请报告ID查询启动报告
	 * 作者：李德
	 * 时间：2015-04-13
	 ****************************************************************************/
	@Override
	public List<ProjectStartFeeList> findProjectStartFeeListByProStartId(int idKey) {
		// TODO Auto-generated method stub
		String sql="select c from ProjectStartFeeList c where c.projectStartedReport.id =" +idKey;
		

		return projectStartFeeListDAO.executeQuery(sql,0,-1);
	}
	
	/****************************************************************************
	 * 功能：保存实验室对象
	 * 作者：李德
	 * 时间：2015-03-20
	 ****************************************************************************/
	@Override
	public ProjectStartFeeList save(ProjectStartFeeList projectStartFeeList) {
		// TODO Auto-generated method stub
		return projectStartFeeListDAO.store(projectStartFeeList);
	}	
	
}
