package net.zjcclims.service.construction;


import net.zjcclims.dao.LabConstructAppDAO;
import net.zjcclims.dao.ProjectAppCourseDAO;
import net.zjcclims.dao.SchoolCourseDAO;
import net.zjcclims.domain.LabConstructApp;
import net.zjcclims.domain.ProjectAppCourse;
import net.zjcclims.domain.SchoolCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectAppCourse entities
 * 
 */

@Service("ProjectAppCourseService")
@Transactional
public class ProjectAppCourseServiceImpl implements ProjectAppCourseService {

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * DAO injected by Spring that manages ProjectAppCourse entities
	 * 
	 */
	@Autowired
	private ProjectAppCourseDAO projectAppCourseDAO;

	/**
	 * DAO injected by Spring that manages SchoolCourse entities
	 * 
	 */
	@Autowired
	private SchoolCourseDAO schoolCourseDAO;

	/**
	 * Instantiates a new ProjectAppCourseServiceImpl.
	 *
	 */
	public ProjectAppCourseServiceImpl() {
	}

	/**
	 */
	@Transactional
	public ProjectAppCourse findProjectAppCourseByPrimaryKey(Integer id) {
		return projectAppCourseDAO.findProjectAppCourseByPrimaryKey(id);
	}

	/**
	 * Load an existing ProjectAppCourse entity
	 * 
	 */
	@Transactional
	public Set<ProjectAppCourse> loadProjectAppCourses() {
		return projectAppCourseDAO.findAllProjectAppCourses();
	}

	/**
	 * Save an existing ProjectAppCourse entity
	 * 
	 */
	@Transactional
	public void saveProjectAppCourse(ProjectAppCourse projectappcourse) {
		ProjectAppCourse existingProjectAppCourse = projectAppCourseDAO.findProjectAppCourseByPrimaryKey(projectappcourse.getId());

		if (existingProjectAppCourse != null) {
			if (existingProjectAppCourse != projectappcourse) {
				existingProjectAppCourse.setId(projectappcourse.getId());
				existingProjectAppCourse.setInfo(projectappcourse.getInfo());
			}
			projectappcourse = projectAppCourseDAO.store(existingProjectAppCourse);
		} else {
			projectappcourse = projectAppCourseDAO.store(projectappcourse);
		}
		projectAppCourseDAO.flush();
	}

	/**
	 * Delete an existing SchoolCourse entity
	 * 
	 */
	@Transactional
	public ProjectAppCourse deleteProjectAppCourseSchoolCourse(Integer projectappcourse_id, String related_schoolcourse_courseNo) {
		ProjectAppCourse projectappcourse = projectAppCourseDAO.findProjectAppCourseByPrimaryKey(projectappcourse_id, -1, -1);
		SchoolCourse related_schoolcourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(related_schoolcourse_courseNo, -1, -1);

		projectappcourse.setSchoolCourse(null);
		//related_schoolcourse.getProjectAppCourses().remove(projectappcourse);
		projectappcourse = projectAppCourseDAO.store(projectappcourse);
		projectAppCourseDAO.flush();

		related_schoolcourse = schoolCourseDAO.store(related_schoolcourse);
		schoolCourseDAO.flush();

		schoolCourseDAO.remove(related_schoolcourse);
		schoolCourseDAO.flush();

		return projectappcourse;
	}

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public ProjectAppCourse deleteProjectAppCourseLabConstructApp(Integer projectappcourse_id, Integer related_labconstructapp_id) {
		ProjectAppCourse projectappcourse = projectAppCourseDAO.findProjectAppCourseByPrimaryKey(projectappcourse_id, -1, -1);
		LabConstructApp related_labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp_id, -1, -1);

		projectappcourse.setLabConstructApp(null);
		related_labconstructapp.getProjectAppCourses().remove(projectappcourse);
		projectappcourse = projectAppCourseDAO.store(projectappcourse);
		projectAppCourseDAO.flush();

		related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
		labConstructAppDAO.flush();

		labConstructAppDAO.remove(related_labconstructapp);
		labConstructAppDAO.flush();

		return projectappcourse;
	}

	/**
	 * Return all ProjectAppCourse entity
	 * 
	 */
	@Transactional
	public List<ProjectAppCourse> findAllProjectAppCourses(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<ProjectAppCourse>(projectAppCourseDAO.findAllProjectAppCourses(startResult, maxRows));
	}

	/**
	 * Delete an existing ProjectAppCourse entity
	 * 
	 */
	@Transactional
	public void deleteProjectAppCourse(ProjectAppCourse projectappcourse) {
		projectAppCourseDAO.remove(projectappcourse);
		projectAppCourseDAO.flush();
	}

	/**
	 * Save an existing SchoolCourse entity
	 * 
	 */
	@Transactional
	public ProjectAppCourse saveProjectAppCourseSchoolCourse(Integer id, SchoolCourse related_schoolcourse) {
		ProjectAppCourse projectappcourse = projectAppCourseDAO.findProjectAppCourseByPrimaryKey(id, -1, -1);
		SchoolCourse existingschoolCourse = schoolCourseDAO.findSchoolCourseByPrimaryKey(related_schoolcourse.getCourseNo());

		// copy into the existing record to preserve existing relationships
		if (existingschoolCourse != null) {
			existingschoolCourse.setCourseNo(related_schoolcourse.getCourseNo());
			existingschoolCourse.setCourseNo(related_schoolcourse.getCourseNo());
			existingschoolCourse.setCourseCode(related_schoolcourse.getCourseCode());
			//existingschoolCourse.setCourseNumber(related_schoolcourse.getCourseNumber());
			//existingschoolCourse.setTeacher(related_schoolcourse.getTeacher());
			existingschoolCourse.setCourseName(related_schoolcourse.getCourseName());
			//existingschoolCourse.setTermId(related_schoolcourse.getTermId());
			existingschoolCourse.setCourseType(related_schoolcourse.getCourseType());
			//existingschoolCourse.setCourseStatus(related_schoolcourse.getCourseStatus());
			//existingschoolCourse.setAcademyNumber(related_schoolcourse.getAcademyNumber());
			//existingschoolCourse.setCreatedBy(related_schoolcourse.getCreatedBy());
			//existingschoolCourse.setUpdatedBy(related_schoolcourse.getUpdatedBy());
			existingschoolCourse.setCreatedDate(related_schoolcourse.getCreatedDate());
			existingschoolCourse.setUpdatedDate(related_schoolcourse.getUpdatedDate());
			existingschoolCourse.setMemo(related_schoolcourse.getMemo());
			existingschoolCourse.setClassesName(related_schoolcourse.getClassesName());
			existingschoolCourse.setCredits(related_schoolcourse.getCredits());
			existingschoolCourse.setPlanStudentNumber(related_schoolcourse.getPlanStudentNumber());
			//existingschoolCourse.setStudentType(related_schoolcourse.getStudentType());
			existingschoolCourse.setPlanLabTime(related_schoolcourse.getPlanLabTime());
			existingschoolCourse.setCourseAddressType(related_schoolcourse.getCourseAddressType());
			existingschoolCourse.setState(related_schoolcourse.getState());
			related_schoolcourse = existingschoolCourse;
		} else {
			related_schoolcourse = schoolCourseDAO.store(related_schoolcourse);
			schoolCourseDAO.flush();
		}

		projectappcourse.setSchoolCourse(related_schoolcourse);
		//related_schoolcourse.getProjectAppCourses().add(projectappcourse);
		projectappcourse = projectAppCourseDAO.store(projectappcourse);
		projectAppCourseDAO.flush();

		related_schoolcourse = schoolCourseDAO.store(related_schoolcourse);
		schoolCourseDAO.flush();

		return projectappcourse;
	}

	/**
	 * Return a count of all ProjectAppCourse entity
	 * 
	 */
	@Transactional
	public Integer countProjectAppCourses() {
		return ((Long) projectAppCourseDAO.createQuerySingleResult("select count(o) from ProjectAppCourse o").getSingleResult()).intValue();
	}

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public ProjectAppCourse saveProjectAppCourseLabConstructApp(Integer id, LabConstructApp related_labconstructapp) {
		ProjectAppCourse projectappcourse = projectAppCourseDAO.findProjectAppCourseByPrimaryKey(id, -1, -1);
		LabConstructApp existinglabConstructApp = labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp.getId());

		// copy into the existing record to preserve existing relationships
		if (existinglabConstructApp != null) {
			existinglabConstructApp.setId(related_labconstructapp.getId());
			existinglabConstructApp.setLabConstructAppCode(related_labconstructapp.getLabConstructAppCode());
			existinglabConstructApp.setProjectName(related_labconstructapp.getProjectName());
			existinglabConstructApp.setPartyId(related_labconstructapp.getPartyId());
			existinglabConstructApp.setAppDate(related_labconstructapp.getAppDate());
			//existinglabConstructApp.setProjectSourceId(related_labconstructapp.getProjectSourceId());
			//existinglabConstructApp.setProposerId(related_labconstructapp.getProposerId());
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

		projectappcourse.setLabConstructApp(related_labconstructapp);
		related_labconstructapp.getProjectAppCourses().add(projectappcourse);
		projectappcourse = projectAppCourseDAO.store(projectappcourse);
		projectAppCourseDAO.flush();

		related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
		labConstructAppDAO.flush();

		return projectappcourse;
	}
}
