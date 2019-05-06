package net.zjcclims.service.construction;


import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for LabConstructUser entities
 * 
 */

@Service("LabConstructUserService")
@Transactional
public class LabConstructUserServiceImpl implements LabConstructUserService {

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * DAO injected by Spring that manages LabConstructUser entities
	 * 
	 */
	@Autowired
	private LabConstructUserDAO labConstructUserDAO;

	/**
	 * DAO injected by Spring that manages User entities
	 * 
	 */
	@Autowired
	private UserDAO userDAO;

	/**
	 * Instantiates a new LabConstructUserServiceImpl.
	 *
	 */
	public LabConstructUserServiceImpl() {
	}

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public LabConstructUser saveLabConstructUserLabConstructApp(Integer id, LabConstructApp related_labconstructapp) {
		LabConstructUser labconstructuser = labConstructUserDAO.findLabConstructUserByPrimaryKey(id, -1, -1);
		LabConstructApp existinglabConstructApp = labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp.getId());

		// copy into the existing record to preserve existing relationships
		if (existinglabConstructApp != null) {
			existinglabConstructApp.setId(related_labconstructapp.getId());
			existinglabConstructApp.setLabConstructAppCode(related_labconstructapp.getLabConstructAppCode());
			existinglabConstructApp.setProjectName(related_labconstructapp.getProjectName());
			existinglabConstructApp.setPartyId(related_labconstructapp.getPartyId());
			existinglabConstructApp.setAppDate(related_labconstructapp.getAppDate());
			//existinglabConstructApp.setProjectSourceId(related_labconstructapp.getProjectSourceId());
			//existinglabConstructApp.setProjectPurposeId(related_labconstructapp.getProjectPurposeId());
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
			existinglabConstructApp.setCourseAmount(related_labconstructapp.getCourseAmount());
			existinglabConstructApp.setMajorAmount(related_labconstructapp.getMajorAmount());
			existinglabConstructApp.setFeeAmount(related_labconstructapp.getFeeAmount());
			existinglabConstructApp.setOtherFee(related_labconstructapp.getOtherFee());
			related_labconstructapp = existinglabConstructApp;
		} else {
			related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
			labConstructAppDAO.flush();
		}

		labconstructuser.setLabConstructApp(related_labconstructapp);
		related_labconstructapp.getLabConstructUsers().add(labconstructuser);
		labconstructuser = labConstructUserDAO.store(labconstructuser);
		labConstructUserDAO.flush();

		related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
		labConstructAppDAO.flush();

		return labconstructuser;
	}

	/**
	 * Return all LabConstructUser entity
	 * 
	 */
	@Transactional
	public List<LabConstructUser> findAllLabConstructUsers(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<LabConstructUser>(labConstructUserDAO.findAllLabConstructUsers(startResult, maxRows));
	}

	/**
	 * Save an existing User entity
	 * 
	 */
	@Transactional
	public LabConstructUser saveLabConstructUserUser(Integer id, User related_user) {
		LabConstructUser labconstructuser = labConstructUserDAO.findLabConstructUserByPrimaryKey(id, -1, -1);
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
			//existinguser.setAcademyNumber(related_user.getAcademyNumber());
			//existinguser.setMajorNumber(related_user.getMajorNumber());
			existinguser.setUserRole(related_user.getUserRole());
			//existinguser.setClassesNumber(related_user.getClassesNumber());
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

	//labconstructuser.setUser(related_user);
		//related_user.getLabConstructUsers().add(labconstructuser);
		labconstructuser = labConstructUserDAO.store(labconstructuser);
		labConstructUserDAO.flush();

		related_user = userDAO.store(related_user);
		userDAO.flush();

		return labconstructuser;
	}

	/**
	 * Save an existing LabConstructUser entity
	 * 
	 */
	@Transactional
	public void saveLabConstructUser(LabConstructUser labconstructuser) {
		LabConstructUser existingLabConstructUser = labConstructUserDAO.findLabConstructUserByPrimaryKey(labconstructuser.getId());

		if (existingLabConstructUser != null) {
			if (existingLabConstructUser != labconstructuser) {
				existingLabConstructUser.setId(labconstructuser.getId());
				existingLabConstructUser.setResponsibilityContent(labconstructuser.getResponsibilityContent());
				existingLabConstructUser.setInfo(labconstructuser.getInfo());
			}
			labconstructuser = labConstructUserDAO.store(existingLabConstructUser);
		} else {
			labconstructuser = labConstructUserDAO.store(labconstructuser);
		}
		labConstructUserDAO.flush();
	}

	/**
	 */
	@Transactional
	public LabConstructUser findLabConstructUserByPrimaryKey(Integer id) {
		return labConstructUserDAO.findLabConstructUserByPrimaryKey(id);
	}

	/**
	 * Load an existing LabConstructUser entity
	 * 
	 */
	@Transactional
	public Set<LabConstructUser> loadLabConstructUsers() {
		return labConstructUserDAO.findAllLabConstructUsers();
	}

	/**
	 * Delete an existing LabConstructUser entity
	 * 
	 */
	@Transactional
	public void deleteLabConstructUser(LabConstructUser labconstructuser) {
		labConstructUserDAO.remove(labconstructuser);
		labConstructUserDAO.flush();
	}

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public LabConstructUser deleteLabConstructUserLabConstructApp(Integer labconstructuser_id, Integer related_labconstructapp_id) {
		LabConstructUser labconstructuser = labConstructUserDAO.findLabConstructUserByPrimaryKey(labconstructuser_id, -1, -1);
		LabConstructApp related_labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp_id, -1, -1);

		labconstructuser.setLabConstructApp(null);
		related_labconstructapp.getLabConstructUsers().remove(labconstructuser);
		labconstructuser = labConstructUserDAO.store(labconstructuser);
		labConstructUserDAO.flush();

		related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
		labConstructAppDAO.flush();

		labConstructAppDAO.remove(related_labconstructapp);
		labConstructAppDAO.flush();

		return labconstructuser;
	}

	/**
	 * Delete an existing User entity
	 * 
	 */
	@Transactional
	public LabConstructUser deleteLabConstructUserUser(Integer labconstructuser_id, String related_user_username) {
		LabConstructUser labconstructuser = labConstructUserDAO.findLabConstructUserByPrimaryKey(labconstructuser_id, -1, -1);
		User related_user = userDAO.findUserByPrimaryKey(related_user_username, -1, -1);

		//labconstructuser.setUser(null);
		//related_user.getLabConstructUsers().remove(labconstructuser);
		labconstructuser = labConstructUserDAO.store(labconstructuser);
		labConstructUserDAO.flush();

		related_user = userDAO.store(related_user);
		userDAO.flush();

		userDAO.remove(related_user);
		userDAO.flush();

		return labconstructuser;
	}

	/**
	 * Return a count of all LabConstructUser entity
	 * 
	 */
	@Transactional
	public Integer countLabConstructUsers() {
		return ((Long) labConstructUserDAO.createQuerySingleResult("select count(o) from LabConstructUser o").getSingleResult()).intValue();
	}
	
	/***************************************************************************************
	 * 功能：实验室建设项目-实验室建设项目参加人员
	 * 作者：李德
	 * 时间：2015-04-03
	 **************************************************************************************/
	@Transactional
	public List<LabConstructUser> findLabConstructUserByAppKey(Integer id) {
		List<LabConstructUser> labConstructUsers = labConstructUserDAO
				.executeQuery("select a from LabConstructUser a where a.labConstructApp.id = "
						+ id + " order by a.id desc");
		return labConstructUsers;
	}
}
