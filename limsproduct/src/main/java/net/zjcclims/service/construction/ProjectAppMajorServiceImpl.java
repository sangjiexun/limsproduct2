package net.zjcclims.service.construction;


import net.zjcclims.dao.LabConstructAppDAO;
import net.zjcclims.dao.ProjectAppMajorDAO;
import net.zjcclims.dao.SchoolMajorDAO;
import net.zjcclims.domain.LabConstructApp;
import net.zjcclims.domain.ProjectAppMajor;
import net.zjcclims.domain.SchoolMajor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectAppMajor entities
 * 
 */

@Service("ProjectAppMajorService")
@Transactional
public class ProjectAppMajorServiceImpl implements ProjectAppMajorService {

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * DAO injected by Spring that manages ProjectAppMajor entities
	 * 
	 */
	@Autowired
	private ProjectAppMajorDAO projectAppMajorDAO;

	/**
	 * DAO injected by Spring that manages SchoolMajor entities
	 * 
	 */
	@Autowired
	private SchoolMajorDAO schoolMajorDAO;

	/**
	 * Instantiates a new ProjectAppMajorServiceImpl.
	 *
	 */
	public ProjectAppMajorServiceImpl() {
	}

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public ProjectAppMajor saveProjectAppMajorLabConstructApp(Integer id, LabConstructApp related_labconstructapp) {
		ProjectAppMajor projectappmajor = projectAppMajorDAO.findProjectAppMajorByPrimaryKey(id, -1, -1);
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

		projectappmajor.setLabConstructApp(related_labconstructapp);
		related_labconstructapp.getProjectAppMajors().add(projectappmajor);
		projectappmajor = projectAppMajorDAO.store(projectappmajor);
		projectAppMajorDAO.flush();

		related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
		labConstructAppDAO.flush();

		return projectappmajor;
	}

	/**
	 * Return all ProjectAppMajor entity
	 * 
	 */
	@Transactional
	public List<ProjectAppMajor> findAllProjectAppMajors(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<ProjectAppMajor>(projectAppMajorDAO.findAllProjectAppMajors(startResult, maxRows));
	}

	/**
	 * Delete an existing ProjectAppMajor entity
	 * 
	 */
	@Transactional
	public void deleteProjectAppMajor(ProjectAppMajor projectappmajor) {
		projectAppMajorDAO.remove(projectappmajor);
		projectAppMajorDAO.flush();
	}

	/**
	 * Load an existing ProjectAppMajor entity
	 * 
	 */
	@Transactional
	public Set<ProjectAppMajor> loadProjectAppMajors() {
		return projectAppMajorDAO.findAllProjectAppMajors();
	}

	/**
	 */
	@Transactional
	public ProjectAppMajor findProjectAppMajorByPrimaryKey(Integer id) {
		return projectAppMajorDAO.findProjectAppMajorByPrimaryKey(id);
	}

	/**
	 * Delete an existing SchoolMajor entity
	 * 
	 */
	@Transactional
	public ProjectAppMajor deleteProjectAppMajorSchoolMajor(Integer projectappmajor_id, String related_schoolmajor_majorNumber) {
		ProjectAppMajor projectappmajor = projectAppMajorDAO.findProjectAppMajorByPrimaryKey(projectappmajor_id, -1, -1);
		SchoolMajor related_schoolmajor = schoolMajorDAO.findSchoolMajorByPrimaryKey(related_schoolmajor_majorNumber, -1, -1);

		projectappmajor.setSchoolMajor(null);
		//related_schoolmajor.getProjectAppMajors().remove(projectappmajor);
		projectappmajor = projectAppMajorDAO.store(projectappmajor);
		projectAppMajorDAO.flush();

		related_schoolmajor = schoolMajorDAO.store(related_schoolmajor);
		schoolMajorDAO.flush();

		schoolMajorDAO.remove(related_schoolmajor);
		schoolMajorDAO.flush();

		return projectappmajor;
	}

	/**
	 * Save an existing SchoolMajor entity
	 * 
	 */
	@Transactional
	public ProjectAppMajor saveProjectAppMajorSchoolMajor(Integer id, SchoolMajor related_schoolmajor) {
		ProjectAppMajor projectappmajor = projectAppMajorDAO.findProjectAppMajorByPrimaryKey(id, -1, -1);
		SchoolMajor existingschoolMajor = schoolMajorDAO.findSchoolMajorByPrimaryKey(related_schoolmajor.getMajorNumber());

		// copy into the existing record to preserve existing relationships
		if (existingschoolMajor != null) {
			existingschoolMajor.setMajorNumber(related_schoolmajor.getMajorNumber());
			existingschoolMajor.setMajorName(related_schoolmajor.getMajorName());
			//existingschoolMajor.setAcademyNumber(related_schoolmajor.getAcademyNumber());
			existingschoolMajor.setCreatedAt(related_schoolmajor.getCreatedAt());
			existingschoolMajor.setUpdatedAt(related_schoolmajor.getUpdatedAt());
			existingschoolMajor.setCreatedBy(related_schoolmajor.getCreatedBy());
			existingschoolMajor.setUpdatedBy(related_schoolmajor.getUpdatedBy());
			existingschoolMajor.setStudentType(related_schoolmajor.getStudentType());
			related_schoolmajor = existingschoolMajor;
		} else {
			related_schoolmajor = schoolMajorDAO.store(related_schoolmajor);
			schoolMajorDAO.flush();
		}

		projectappmajor.setSchoolMajor(related_schoolmajor);
		//related_schoolmajor.getProjectAppMajors().add(projectappmajor);
		projectappmajor = projectAppMajorDAO.store(projectappmajor);
		projectAppMajorDAO.flush();

		related_schoolmajor = schoolMajorDAO.store(related_schoolmajor);
		schoolMajorDAO.flush();

		return projectappmajor;
	}

	/**
	 * Save an existing ProjectAppMajor entity
	 * 
	 */
	@Transactional
	public void saveProjectAppMajor(ProjectAppMajor projectappmajor) {
		ProjectAppMajor existingProjectAppMajor = projectAppMajorDAO.findProjectAppMajorByPrimaryKey(projectappmajor.getId());

		if (existingProjectAppMajor != null) {
			if (existingProjectAppMajor != projectappmajor) {
				existingProjectAppMajor.setId(projectappmajor.getId());
			}
			projectappmajor = projectAppMajorDAO.store(existingProjectAppMajor);
		} else {
			projectappmajor = projectAppMajorDAO.store(projectappmajor);
		}
		projectAppMajorDAO.flush();
	}

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public ProjectAppMajor deleteProjectAppMajorLabConstructApp(Integer projectappmajor_id, Integer related_labconstructapp_id) {
		ProjectAppMajor projectappmajor = projectAppMajorDAO.findProjectAppMajorByPrimaryKey(projectappmajor_id, -1, -1);
		LabConstructApp related_labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp_id, -1, -1);

		projectappmajor.setLabConstructApp(null);
		related_labconstructapp.getProjectAppMajors().remove(projectappmajor);
		projectappmajor = projectAppMajorDAO.store(projectappmajor);
		projectAppMajorDAO.flush();

		related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
		labConstructAppDAO.flush();

		labConstructAppDAO.remove(related_labconstructapp);
		labConstructAppDAO.flush();

		return projectappmajor;
	}

	/**
	 * Return a count of all ProjectAppMajor entity
	 * 
	 */
	@Transactional
	public Integer countProjectAppMajors() {
		return ((Long) projectAppMajorDAO.createQuerySingleResult("select count(o) from ProjectAppMajor o").getSingleResult()).intValue();
	}
}
