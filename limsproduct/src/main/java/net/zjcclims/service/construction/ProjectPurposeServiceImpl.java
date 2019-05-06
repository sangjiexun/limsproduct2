package net.zjcclims.service.construction;

import net.zjcclims.dao.CProjectPurposeDAO;
import net.zjcclims.dao.LabConstructAppDAO;
import net.zjcclims.dao.ProjectPurposeDAO;
import net.zjcclims.domain.CProjectPurpose;
import net.zjcclims.domain.LabConstructApp;
import net.zjcclims.domain.ProjectPurpose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectPurpose entities
 * 
 */

@Service("ProjectPurposeService")
@Transactional
public class ProjectPurposeServiceImpl implements ProjectPurposeService {

	/**
	 * DAO injected by Spring that manages CProjectPurpose entities
	 * 
	 */
	@Autowired
	private CProjectPurposeDAO cProjectPurposeDAO;

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * DAO injected by Spring that manages ProjectPurpose entities
	 * 
	 */
	@Autowired
	private ProjectPurposeDAO projectPurposeDAO;

	/**
	 * Instantiates a new ProjectPurposeServiceImpl.
	 *
	 */
	public ProjectPurposeServiceImpl() {
	}

	/**
	 * Return a count of all ProjectPurpose entity
	 * 
	 */
	@Transactional
	public Integer countProjectPurposes() {
		return ((Long) projectPurposeDAO.createQuerySingleResult("select count(o) from ProjectPurpose o").getSingleResult()).intValue();
	}

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public ProjectPurpose deleteProjectPurposeLabConstructApp(Integer projectpurpose_id, Integer related_labconstructapp_id) {
		ProjectPurpose projectpurpose = projectPurposeDAO.findProjectPurposeByPrimaryKey(projectpurpose_id, -1, -1);
		LabConstructApp related_labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp_id, -1, -1);

		projectpurpose.setLabConstructApp(null);
		related_labconstructapp.getProjectPurposes().remove(projectpurpose);
		projectpurpose = projectPurposeDAO.store(projectpurpose);
		projectPurposeDAO.flush();

		related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
		labConstructAppDAO.flush();

		labConstructAppDAO.remove(related_labconstructapp);
		labConstructAppDAO.flush();

		return projectpurpose;
	}

	/**
	 * Return all ProjectPurpose entity
	 * 
	 */
	@Transactional
	public List<ProjectPurpose> findAllProjectPurposes(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<ProjectPurpose>(projectPurposeDAO.findAllProjectPurposes(startResult, maxRows));
	}

	/**
	 * Load an existing ProjectPurpose entity
	 * 
	 */
	@Transactional
	public Set<ProjectPurpose> loadProjectPurposes() {
		return projectPurposeDAO.findAllProjectPurposes();
	}

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public ProjectPurpose saveProjectPurposeLabConstructApp(Integer id, LabConstructApp related_labconstructapp) {
		ProjectPurpose projectpurpose = projectPurposeDAO.findProjectPurposeByPrimaryKey(id, -1, -1);
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

		projectpurpose.setLabConstructApp(related_labconstructapp);
		related_labconstructapp.getProjectPurposes().add(projectpurpose);
		projectpurpose = projectPurposeDAO.store(projectpurpose);
		projectPurposeDAO.flush();

		related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
		labConstructAppDAO.flush();

		return projectpurpose;
	}

	/**
	 * Delete an existing ProjectPurpose entity
	 * 
	 */
	@Transactional
	public void deleteProjectPurpose(ProjectPurpose projectpurpose) {
		projectPurposeDAO.remove(projectpurpose);
		projectPurposeDAO.flush();
	}

	/**
	 */
	@Transactional
	public ProjectPurpose findProjectPurposeByPrimaryKey(Integer id) {
		return projectPurposeDAO.findProjectPurposeByPrimaryKey(id);
	}

	/**
	 * Save an existing ProjectPurpose entity
	 * 
	 */
	@Transactional
	public void saveProjectPurpose(ProjectPurpose projectpurpose) {
		ProjectPurpose existingProjectPurpose = projectPurposeDAO.findProjectPurposeByPrimaryKey(projectpurpose.getId());

		if (existingProjectPurpose != null) {
			if (existingProjectPurpose != projectpurpose) {
				existingProjectPurpose.setId(projectpurpose.getId());
				existingProjectPurpose.setLabConstructAppId(projectpurpose.getLabConstructAppId());
				existingProjectPurpose.setProjectPurposeId(projectpurpose.getProjectPurposeId());
				existingProjectPurpose.setInfo(projectpurpose.getInfo());
			}
			projectpurpose = projectPurposeDAO.store(existingProjectPurpose);
		} else {
			projectpurpose = projectPurposeDAO.store(projectpurpose);
		}
		projectPurposeDAO.flush();
	}

	/**
	 * Save an existing CProjectPurpose entity
	 * 
	 */
	@Transactional
	public ProjectPurpose saveProjectPurposeCProjectPurpose(Integer id, CProjectPurpose related_cprojectpurpose) {
		ProjectPurpose projectpurpose = projectPurposeDAO.findProjectPurposeByPrimaryKey(id, -1, -1);
		CProjectPurpose existingCProjectPurpose = cProjectPurposeDAO.findCProjectPurposeByPrimaryKey(related_cprojectpurpose.getId());

		// copy into the existing record to preserve existing relationships
		if (existingCProjectPurpose != null) {
			existingCProjectPurpose.setId(related_cprojectpurpose.getId());
			existingCProjectPurpose.setName(related_cprojectpurpose.getName());
			related_cprojectpurpose = existingCProjectPurpose;
		} else {
			related_cprojectpurpose = cProjectPurposeDAO.store(related_cprojectpurpose);
			cProjectPurposeDAO.flush();
		}

		projectpurpose.setCProjectPurpose(related_cprojectpurpose);
		related_cprojectpurpose.getProjectPurposes().add(projectpurpose);
		projectpurpose = projectPurposeDAO.store(projectpurpose);
		projectPurposeDAO.flush();

		related_cprojectpurpose = cProjectPurposeDAO.store(related_cprojectpurpose);
		cProjectPurposeDAO.flush();

		return projectpurpose;
	}

	/**
	 * Delete an existing CProjectPurpose entity
	 * 
	 */
	@Transactional
	public ProjectPurpose deleteProjectPurposeCProjectPurpose(Integer projectpurpose_id, Integer related_cprojectpurpose_id) {
		ProjectPurpose projectpurpose = projectPurposeDAO.findProjectPurposeByPrimaryKey(projectpurpose_id, -1, -1);
		CProjectPurpose related_cprojectpurpose = cProjectPurposeDAO.findCProjectPurposeByPrimaryKey(related_cprojectpurpose_id, -1, -1);

		projectpurpose.setCProjectPurpose(null);
		related_cprojectpurpose.getProjectPurposes().remove(projectpurpose);
		projectpurpose = projectPurposeDAO.store(projectpurpose);
		projectPurposeDAO.flush();

		related_cprojectpurpose = cProjectPurposeDAO.store(related_cprojectpurpose);
		cProjectPurposeDAO.flush();

		cProjectPurposeDAO.remove(related_cprojectpurpose);
		cProjectPurposeDAO.flush();

		return projectpurpose;
	}
}
