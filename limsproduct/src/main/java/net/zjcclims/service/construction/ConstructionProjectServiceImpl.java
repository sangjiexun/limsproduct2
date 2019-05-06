package net.zjcclims.service.construction;


import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ConstructionProject entities
 * 
 */

@Service("ConstructionProjectService")
@Transactional
public class ConstructionProjectServiceImpl implements
		ConstructionProjectService {

	/**
	 * DAO injected by Spring that manages CProjectStatus entities
	 * 
	 */
	@Autowired
	private CProjectStatusDAO cProjectStatusDAO;

	/**
	 * DAO injected by Spring that manages ConstructionProject entities
	 * 
	 */
	@Autowired
	private ConstructionProjectDAO constructionProjectDAO;

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * Instantiates a new ConstructionProjectServiceImpl.
	 *
	 */
	public ConstructionProjectServiceImpl() {
	}

	/**
	 * Delete an existing CProjectStatus entity
	 * 
	 */
	@Transactional
	public ConstructionProject deleteConstructionProjectCProjectStatus(Integer constructionproject_id, Integer related_cprojectstatus_id) {
		ConstructionProject constructionproject = constructionProjectDAO.findConstructionProjectByPrimaryKey(constructionproject_id, -1, -1);
		CProjectStatus related_cprojectstatus = cProjectStatusDAO.findCProjectStatusByPrimaryKey(related_cprojectstatus_id, -1, -1);

		constructionproject.setCProjectStatus(null);
		related_cprojectstatus.getConstructionProjects().remove(constructionproject);
		constructionproject = constructionProjectDAO.store(constructionproject);
		constructionProjectDAO.flush();

		related_cprojectstatus = cProjectStatusDAO.store(related_cprojectstatus);
		cProjectStatusDAO.flush();

		cProjectStatusDAO.remove(related_cprojectstatus);
		cProjectStatusDAO.flush();

		return constructionproject;
	}

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public ConstructionProject deleteConstructionProjectLabConstructApp(Integer constructionproject_id, Integer related_labconstructapp_id) {
		ConstructionProject constructionproject = constructionProjectDAO.findConstructionProjectByPrimaryKey(constructionproject_id, -1, -1);
		LabConstructApp related_labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp_id, -1, -1);

		constructionproject.setLabConstructApp(null);
		related_labconstructapp.getConstructionProjects().remove(constructionproject);
		constructionproject = constructionProjectDAO.store(constructionproject);
		constructionProjectDAO.flush();

		related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
		labConstructAppDAO.flush();

		labConstructAppDAO.remove(related_labconstructapp);
		labConstructAppDAO.flush();

		return constructionproject;
	}

	/**
	 * Load an existing ConstructionProject entity
	 * 
	 */
	@Transactional
	public Set<ConstructionProject> loadConstructionProjects() {
		return constructionProjectDAO.findAllConstructionProjects();
	}

	/**
	 * Save an existing ConstructionProject entity
	 * 
	 */
	@Transactional
	public void saveConstructionProject(ConstructionProject constructionproject) {
		ConstructionProject existingConstructionProject = constructionProjectDAO.findConstructionProjectByPrimaryKey(constructionproject.getId());

		if (existingConstructionProject != null) {
			if (existingConstructionProject != constructionproject) {
				existingConstructionProject.setId(constructionproject.getId());
			}
			constructionproject = constructionProjectDAO.store(existingConstructionProject);
		} else {
			constructionproject = constructionProjectDAO.store(constructionproject);
		}
		constructionProjectDAO.flush();
	}

	/**
	 * Return all ConstructionProject entity
	 * 
	 */
	@Transactional
	public List<ConstructionProject> findAllConstructionProjects(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<ConstructionProject>(constructionProjectDAO.findAllConstructionProjects(startResult, maxRows));
	}

	/**
	 * Delete an existing ConstructionProject entity
	 * 
	 */
	@Transactional
	public void deleteConstructionProject(ConstructionProject constructionproject) {
		constructionProjectDAO.remove(constructionproject);
		constructionProjectDAO.flush();
	}

	/**
	 * Save an existing CProjectStatus entity
	 * 
	 */
	@Transactional
	public ConstructionProject saveConstructionProjectCProjectStatus(Integer id, CProjectStatus related_cprojectstatus) {
		ConstructionProject constructionproject = constructionProjectDAO.findConstructionProjectByPrimaryKey(id, -1, -1);
		CProjectStatus existingCProjectStatus = cProjectStatusDAO.findCProjectStatusByPrimaryKey(related_cprojectstatus.getId());

		// copy into the existing record to preserve existing relationships
		if (existingCProjectStatus != null) {
			existingCProjectStatus.setId(related_cprojectstatus.getId());
			existingCProjectStatus.setName(related_cprojectstatus.getName());
			related_cprojectstatus = existingCProjectStatus;
		} else {
			related_cprojectstatus = cProjectStatusDAO.store(related_cprojectstatus);
			cProjectStatusDAO.flush();
		}

		constructionproject.setCProjectStatus(related_cprojectstatus);
		related_cprojectstatus.getConstructionProjects().add(constructionproject);
		constructionproject = constructionProjectDAO.store(constructionproject);
		constructionProjectDAO.flush();

		related_cprojectstatus = cProjectStatusDAO.store(related_cprojectstatus);
		cProjectStatusDAO.flush();

		return constructionproject;
	}

	/**
	 */
	@Transactional
	public ConstructionProject findConstructionProjectByPrimaryKey(Integer id) {
		return constructionProjectDAO.findConstructionProjectByPrimaryKey(id);
	}

	/**
	 * Return a count of all ConstructionProject entity
	 * 
	 */
	@Transactional
	public Integer countConstructionProjects() {
		return ((Long) constructionProjectDAO.createQuerySingleResult("select count(o) from ConstructionProject o").getSingleResult()).intValue();
	}

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public ConstructionProject saveConstructionProjectLabConstructApp(Integer id, LabConstructApp related_labconstructapp) {
		ConstructionProject constructionproject = constructionProjectDAO.findConstructionProjectByPrimaryKey(id, -1, -1);
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
			related_labconstructapp = existinglabConstructApp;
		} else {
			related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
			labConstructAppDAO.flush();
		}

		constructionproject.setLabConstructApp(related_labconstructapp);
		related_labconstructapp.getConstructionProjects().add(constructionproject);
		constructionproject = constructionProjectDAO.store(constructionproject);
		constructionProjectDAO.flush();

		related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
		labConstructAppDAO.flush();

		return constructionproject;
	}
}
