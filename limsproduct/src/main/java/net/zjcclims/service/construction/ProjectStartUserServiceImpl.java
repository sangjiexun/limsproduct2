package net.zjcclims.service.construction;


import net.zjcclims.dao.ProjectStartUserDAO;
import net.zjcclims.dao.ProjectStartedReportDAO;
import net.zjcclims.domain.ProjectStartUser;
import net.zjcclims.domain.ProjectStartedReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectStartUser entities
 * 
 */

@Service("ProjectStartUserService")
@Transactional
public class ProjectStartUserServiceImpl implements ProjectStartUserService {

	/**
	 * DAO injected by Spring that manages ProjectStartUser entities
	 * 
	 */
	@Autowired
	private ProjectStartUserDAO projectStartUserDAO;

	/**
	 * DAO injected by Spring that manages ProjectStartedReport entities
	 * 
	 */
	@Autowired
	private ProjectStartedReportDAO projectStartedReportDAO;

	/**
	 * Instantiates a new ProjectStartUserServiceImpl.
	 *
	 */
	public ProjectStartUserServiceImpl() {
	}

	/**
	 * Delete an existing ProjectStartUser entity
	 * 
	 */
	@Transactional
	public void deleteProjectStartUser(ProjectStartUser projectstartuser) {
		projectStartUserDAO.remove(projectstartuser);
		projectStartUserDAO.flush();
	}

	/**
	 * Delete an existing ProjectStartedReport entity
	 * 
	 */
	@Transactional
	public ProjectStartUser deleteProjectStartUserProjectStartedReport(Integer projectstartuser_id, Integer related_projectstartedreport_id) {
		ProjectStartUser projectstartuser = projectStartUserDAO.findProjectStartUserByPrimaryKey(projectstartuser_id, -1, -1);
		ProjectStartedReport related_projectstartedreport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(related_projectstartedreport_id, -1, -1);

		projectstartuser.setProjectStartedReport(null);
		related_projectstartedreport.getProjectStartUsers().remove(projectstartuser);
		projectstartuser = projectStartUserDAO.store(projectstartuser);
		projectStartUserDAO.flush();

		related_projectstartedreport = projectStartedReportDAO.store(related_projectstartedreport);
		projectStartedReportDAO.flush();

		projectStartedReportDAO.remove(related_projectstartedreport);
		projectStartedReportDAO.flush();

		return projectstartuser;
	}

	/**
	 * Save an existing ProjectStartUser entity
	 * 
	 */
	@Transactional
	public void saveProjectStartUser(ProjectStartUser projectstartuser) {
		ProjectStartUser existingProjectStartUser = projectStartUserDAO.findProjectStartUserByPrimaryKey(projectstartuser.getId());

		if (existingProjectStartUser != null) {
			if (existingProjectStartUser != projectstartuser) {
				existingProjectStartUser.setId(projectstartuser.getId());
				existingProjectStartUser.setResponsibilityContent(projectstartuser.getResponsibilityContent());
				existingProjectStartUser.setInfo(projectstartuser.getInfo());
				existingProjectStartUser.setCname(projectstartuser.getCname());
				existingProjectStartUser.setSex(projectstartuser.getSex());
				existingProjectStartUser.setAge(projectstartuser.getAge());
				existingProjectStartUser.setPosition(projectstartuser.getPosition());
				existingProjectStartUser.setJobTitle(projectstartuser.getJobTitle());
			}
			projectstartuser = projectStartUserDAO.store(existingProjectStartUser);
		} else {
			projectstartuser = projectStartUserDAO.store(projectstartuser);
		}
		projectStartUserDAO.flush();
	}

	/**
	 * Return a count of all ProjectStartUser entity
	 * 
	 */
	@Transactional
	public Integer countProjectStartUsers() {
		return ((Long) projectStartUserDAO.createQuerySingleResult("select count(o) from ProjectStartUser o").getSingleResult()).intValue();
	}

	/**
	 */
	@Transactional
	public ProjectStartUser findProjectStartUserByPrimaryKey(Integer id) {
		return projectStartUserDAO.findProjectStartUserByPrimaryKey(id);
	}

	/**
	 * Return all ProjectStartUser entity
	 * 
	 */
	@Transactional
	public List<ProjectStartUser> findAllProjectStartUsers(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<ProjectStartUser>(projectStartUserDAO.findAllProjectStartUsers(startResult, maxRows));
	}

	/**
	 * Save an existing ProjectStartedReport entity
	 * 
	 */
	@Transactional
	public ProjectStartUser saveProjectStartUserProjectStartedReport(Integer id, ProjectStartedReport related_projectstartedreport) {
		ProjectStartUser projectstartuser = projectStartUserDAO.findProjectStartUserByPrimaryKey(id, -1, -1);
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

		projectstartuser.setProjectStartedReport(related_projectstartedreport);
		related_projectstartedreport.getProjectStartUsers().add(projectstartuser);
		projectstartuser = projectStartUserDAO.store(projectstartuser);
		projectStartUserDAO.flush();

		related_projectstartedreport = projectStartedReportDAO.store(related_projectstartedreport);
		projectStartedReportDAO.flush();

		return projectstartuser;
	}

	/**
	 * Load an existing ProjectStartUser entity
	 * 
	 */
	@Transactional
	public Set<ProjectStartUser> loadProjectStartUsers() {
		return projectStartUserDAO.findAllProjectStartUsers();
	}
}
