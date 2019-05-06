package net.zjcclims.service.construction;


import net.zjcclims.dao.LabConstructAppApprovalDAO;
import net.zjcclims.dao.LabConstructAppDAO;
import net.zjcclims.dao.UserDAO;
import net.zjcclims.domain.LabConstructApp;
import net.zjcclims.domain.LabConstructAppApproval;
import net.zjcclims.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for LabConstructAppApproval entities
 * 
 */

@Service("LabConstructAppApprovalService")
@Transactional
public class LabConstructAppApprovalServiceImpl implements
		LabConstructAppApprovalService {

	/**
	 * DAO injected by Spring that manages LabConstructAppApproval entities
	 * 
	 */
	@Autowired
	private LabConstructAppApprovalDAO labConstructAppApprovalDAO;

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * DAO injected by Spring that manages User entities
	 * 
	 */
	@Autowired
	private UserDAO userDAO;

	/**
	 * Instantiates a new LabConstructAppApprovalServiceImpl.
	 *
	 */
	public LabConstructAppApprovalServiceImpl() {
	}

	/**
	 * Save an existing User entity
	 * 
	 */
	@Transactional
	public LabConstructAppApproval saveLabConstructAppApprovalUser(Integer id, User related_user) {
		LabConstructAppApproval labconstructappapproval = labConstructAppApprovalDAO.findLabConstructAppApprovalByPrimaryKey(id, -1, -1);
		User existinguser = userDAO.findUserByPrimaryKey(related_user.getUsername());

		// copy into the existing record to preserve existing relationships
		if (existinguser != null) {
			existinguser.setUsername(related_user.getUsername());
			existinguser.setUsername(related_user.getUsername());
			existinguser.setCardno(related_user.getCardno());
			existinguser.setCname(related_user.getCname());
			existinguser.setPassword(related_user.getPassword());
			existinguser.setUserSexy(related_user.getUserSexy());
			existinguser.setUserStatus(related_user.getUserStatus());
			existinguser.setTeacherNumber(related_user.getTeacherNumber());
			existinguser.setUserRole(related_user.getUserRole());
			existinguser.setLastLogin(related_user.getLastLogin());
			existinguser.setCreatedAt(related_user.getCreatedAt());
			existinguser.setUpdatedAt(related_user.getUpdatedAt());
			existinguser.setTelephone(related_user.getTelephone());
			existinguser.setEmail(related_user.getEmail());
			existinguser.setEnabled(related_user.getEnabled());
			//existinguser.setMajorDirection(related_user.getMajorDirection());
			existinguser.setEnrollmentStatus(related_user.getEnrollmentStatus());
			existinguser.setIfEnrollment(related_user.getIfEnrollment());
			//existinguser.setUserType(related_user.getUserType());
			existinguser.setAttendanceTime(related_user.getAttendanceTime());
			existinguser.setGrade(related_user.getGrade());
			related_user = existinguser;
		} else {
			related_user = userDAO.store(related_user);
			userDAO.flush();
		}

		labconstructappapproval.setUser(related_user);
		related_user.getLabConstructAppApprovals().add(labconstructappapproval);
		labconstructappapproval = labConstructAppApprovalDAO.store(labconstructappapproval);
		labConstructAppApprovalDAO.flush();

		related_user = userDAO.store(related_user);
		userDAO.flush();

		return labconstructappapproval;
	}

	/**
	 */
	@Transactional
	public LabConstructAppApproval findLabConstructAppApprovalByPrimaryKey(Integer id) {
		return labConstructAppApprovalDAO.findLabConstructAppApprovalByPrimaryKey(id);
	}

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public LabConstructAppApproval deleteLabConstructAppApprovalLabConstructApp(Integer labconstructappapproval_id, Integer related_labconstructapp_id) {
		LabConstructAppApproval labconstructappapproval = labConstructAppApprovalDAO.findLabConstructAppApprovalByPrimaryKey(labconstructappapproval_id, -1, -1);
		LabConstructApp related_labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp_id, -1, -1);

		labconstructappapproval.setLabConstructApp(null);
		related_labconstructapp.getLabConstructAppApprovals().remove(labconstructappapproval);
		labconstructappapproval = labConstructAppApprovalDAO.store(labconstructappapproval);
		labConstructAppApprovalDAO.flush();

		related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
		labConstructAppDAO.flush();

		labConstructAppDAO.remove(related_labconstructapp);
		labConstructAppDAO.flush();

		return labconstructappapproval;
	}

	/**
	 * Save an existing LabConstructAppApproval entity
	 * 
	 */
	@Transactional
	public void saveLabConstructAppApproval(LabConstructAppApproval labconstructappapproval) {
		LabConstructAppApproval existingLabConstructAppApproval = labConstructAppApprovalDAO.findLabConstructAppApprovalByPrimaryKey(labconstructappapproval.getId());

		if (existingLabConstructAppApproval != null) {
			if (existingLabConstructAppApproval != labconstructappapproval) {
				existingLabConstructAppApproval.setId(labconstructappapproval.getId());
				existingLabConstructAppApproval.setResult(labconstructappapproval.getResult());
				existingLabConstructAppApproval.setCreatedate(labconstructappapproval.getCreatedate());
				existingLabConstructAppApproval.setFlag(labconstructappapproval.getFlag());
				existingLabConstructAppApproval.setLevel(labconstructappapproval.getLevel());
			}
			labconstructappapproval = labConstructAppApprovalDAO.store(existingLabConstructAppApproval);
		} else {
			labconstructappapproval = labConstructAppApprovalDAO.store(labconstructappapproval);
		}
		labConstructAppApprovalDAO.flush();
	}

	/**
	 * Load an existing LabConstructAppApproval entity
	 * 
	 */
	@Transactional
	public Set<LabConstructAppApproval> loadLabConstructAppApprovals() {
		return labConstructAppApprovalDAO.findAllLabConstructAppApprovals();
	}

	/**
	 * Delete an existing User entity
	 * 
	 */
	@Transactional
	public LabConstructAppApproval deleteLabConstructAppApprovalUser(Integer labconstructappapproval_id, String related_user_username) {
		LabConstructAppApproval labconstructappapproval = labConstructAppApprovalDAO.findLabConstructAppApprovalByPrimaryKey(labconstructappapproval_id, -1, -1);
		User related_user = userDAO.findUserByPrimaryKey(related_user_username, -1, -1);

		labconstructappapproval.setUser(null);
		related_user.getLabConstructAppApprovals().remove(labconstructappapproval);
		labconstructappapproval = labConstructAppApprovalDAO.store(labconstructappapproval);
		labConstructAppApprovalDAO.flush();

		related_user = userDAO.store(related_user);
		userDAO.flush();

		userDAO.remove(related_user);
		userDAO.flush();

		return labconstructappapproval;
	}

	/**
	 * Return all LabConstructAppApproval entity
	 * 
	 */
	@Transactional
	public List<LabConstructAppApproval> findAllLabConstructAppApprovals(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<LabConstructAppApproval>(labConstructAppApprovalDAO.findAllLabConstructAppApprovals(startResult, maxRows));
	}

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public LabConstructAppApproval saveLabConstructAppApprovalLabConstructApp(Integer id, LabConstructApp related_labconstructapp) {
		LabConstructAppApproval labconstructappapproval = labConstructAppApprovalDAO.findLabConstructAppApprovalByPrimaryKey(id, -1, -1);
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

		labconstructappapproval.setLabConstructApp(related_labconstructapp);
		related_labconstructapp.getLabConstructAppApprovals().add(labconstructappapproval);
		labconstructappapproval = labConstructAppApprovalDAO.store(labconstructappapproval);
		labConstructAppApprovalDAO.flush();

		related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
		labConstructAppDAO.flush();

		return labconstructappapproval;
	}

	/**
	 * Return a count of all LabConstructAppApproval entity
	 * 
	 */
	@Transactional
	public Integer countLabConstructAppApprovals() {
		return ((Long) labConstructAppApprovalDAO.createQuerySingleResult("select count(o) from LabConstructAppApproval o").getSingleResult()).intValue();
	}

	/**
	 * Delete an existing LabConstructAppApproval entity
	 * 
	 */
	@Transactional
	public void deleteLabConstructAppApproval(LabConstructAppApproval labconstructappapproval) {
		labConstructAppApprovalDAO.remove(labconstructappapproval);
		labConstructAppApprovalDAO.flush();
	}
}
