package net.zjcclims.service.construction;


import net.zjcclims.dao.ProjectAcceptUserDAO;
import net.zjcclims.dao.ProjectAcceptanceApplicationDAO;
import net.zjcclims.domain.ProjectAcceptUser;
import net.zjcclims.domain.ProjectAcceptanceApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectAcceptUser entities
 * 
 */

@Service("ProjectAcceptUserService")
@Transactional
public class ProjectAcceptUserServiceImpl implements ProjectAcceptUserService {

	/**
	 * DAO injected by Spring that manages ProjectAcceptUser entities
	 * 
	 */
	@Autowired
	private ProjectAcceptUserDAO projectAcceptUserDAO;

	/**
	 * DAO injected by Spring that manages ProjectAcceptanceApplication entities
	 * 
	 */
	@Autowired
	private ProjectAcceptanceApplicationDAO projectAcceptanceApplicationDAO;

	/**
	 * Instantiates a new ProjectAcceptUserServiceImpl.
	 *
	 */
	public ProjectAcceptUserServiceImpl() {
	}

	/**
	 * Delete an existing ProjectAcceptanceApplication entity
	 * 
	 */
	@Transactional
	public ProjectAcceptUser deleteProjectAcceptUserProjectAcceptanceApplication(Integer projectacceptuser_id, Integer related_projectacceptanceapplication_id) {
		ProjectAcceptUser projectacceptuser = projectAcceptUserDAO.findProjectAcceptUserByPrimaryKey(projectacceptuser_id, -1, -1);
		ProjectAcceptanceApplication related_projectacceptanceapplication = projectAcceptanceApplicationDAO.findProjectAcceptanceApplicationByPrimaryKey(related_projectacceptanceapplication_id, -1, -1);

		projectacceptuser.setProjectAcceptanceApplication(null);
		related_projectacceptanceapplication.getProjectAcceptUsers().remove(projectacceptuser);
		projectacceptuser = projectAcceptUserDAO.store(projectacceptuser);
		projectAcceptUserDAO.flush();

		related_projectacceptanceapplication = projectAcceptanceApplicationDAO.store(related_projectacceptanceapplication);
		projectAcceptanceApplicationDAO.flush();

		projectAcceptanceApplicationDAO.remove(related_projectacceptanceapplication);
		projectAcceptanceApplicationDAO.flush();

		return projectacceptuser;
	}

	/**
	 * Save an existing ProjectAcceptanceApplication entity
	 * 
	 */
	@Transactional
	public ProjectAcceptUser saveProjectAcceptUserProjectAcceptanceApplication(Integer id, ProjectAcceptanceApplication related_projectacceptanceapplication) {
		ProjectAcceptUser projectacceptuser = projectAcceptUserDAO.findProjectAcceptUserByPrimaryKey(id, -1, -1);
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

		projectacceptuser.setProjectAcceptanceApplication(related_projectacceptanceapplication);
		related_projectacceptanceapplication.getProjectAcceptUsers().add(projectacceptuser);
		projectacceptuser = projectAcceptUserDAO.store(projectacceptuser);
		projectAcceptUserDAO.flush();

		related_projectacceptanceapplication = projectAcceptanceApplicationDAO.store(related_projectacceptanceapplication);
		projectAcceptanceApplicationDAO.flush();

		return projectacceptuser;
	}

	/**
	 * Return all ProjectAcceptUser entity
	 * 
	 */
	@Transactional
	public List<ProjectAcceptUser> findAllProjectAcceptUsers(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<ProjectAcceptUser>(projectAcceptUserDAO.findAllProjectAcceptUsers(startResult, maxRows));
	}

	/**
	 * Save an existing ProjectAcceptUser entity
	 * 
	 */
	@Transactional
	public void saveProjectAcceptUser(ProjectAcceptUser projectacceptuser) {
		ProjectAcceptUser existingProjectAcceptUser = projectAcceptUserDAO.findProjectAcceptUserByPrimaryKey(projectacceptuser.getId());

		if (existingProjectAcceptUser != null) {
			if (existingProjectAcceptUser != projectacceptuser) {
				existingProjectAcceptUser.setId(projectacceptuser.getId());
				existingProjectAcceptUser.setResponsibilityContent(projectacceptuser.getResponsibilityContent());
				existingProjectAcceptUser.setInfo(projectacceptuser.getInfo());
				existingProjectAcceptUser.setCname(projectacceptuser.getCname());
				existingProjectAcceptUser.setSex(projectacceptuser.getSex());
				existingProjectAcceptUser.setAge(projectacceptuser.getAge());
				existingProjectAcceptUser.setPosition(projectacceptuser.getPosition());
				existingProjectAcceptUser.setJobTitle(projectacceptuser.getJobTitle());
			}
			projectacceptuser = projectAcceptUserDAO.store(existingProjectAcceptUser);
		} else {
			projectacceptuser = projectAcceptUserDAO.store(projectacceptuser);
		}
		projectAcceptUserDAO.flush();
	}

	/**
	 * Return a count of all ProjectAcceptUser entity
	 * 
	 */
	@Transactional
	public Integer countProjectAcceptUsers() {
		return ((Long) projectAcceptUserDAO.createQuerySingleResult("select count(o) from ProjectAcceptUser o").getSingleResult()).intValue();
	}

	/**
	 * Load an existing ProjectAcceptUser entity
	 * 
	 */
	@Transactional
	public Set<ProjectAcceptUser> loadProjectAcceptUsers() {
		return projectAcceptUserDAO.findAllProjectAcceptUsers();
	}

	/**
	 */
	@Transactional
	public ProjectAcceptUser findProjectAcceptUserByPrimaryKey(Integer id) {
		return projectAcceptUserDAO.findProjectAcceptUserByPrimaryKey(id);
	}

	/**
	 * Delete an existing ProjectAcceptUser entity
	 * 
	 */
	@Transactional
	public void deleteProjectAcceptUser(ProjectAcceptUser projectacceptuser) {
		projectAcceptUserDAO.remove(projectacceptuser);
		projectAcceptUserDAO.flush();
	}
}
