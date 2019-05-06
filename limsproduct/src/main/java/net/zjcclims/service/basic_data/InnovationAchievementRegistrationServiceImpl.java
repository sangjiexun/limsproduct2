package net.zjcclims.service.basic_data;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import net.zjcclims.dao.InnovationAchievementRegistrationDAO;

import net.zjcclims.domain.InnovationAchievementRegistration;

/**
 * Spring service that handles CRUD requests for InnovationAchievementRegistration entities
 * 
 */

@Service("InnovationAchievementRegistrationService")
@Transactional
public class InnovationAchievementRegistrationServiceImpl implements
		InnovationAchievementRegistrationService {

	/**
	 * DAO injected by Spring that manages InnovationAchievementRegistration entities
	 * 
	 */
	@Autowired
	private InnovationAchievementRegistrationDAO innovationAchievementRegistrationDAO;

	/**
	 * Instantiates a new InnovationAchievementRegistrationServiceImpl.
	 *
	 */
	public InnovationAchievementRegistrationServiceImpl() {
	}

	/**
	 */
	@Transactional
	public InnovationAchievementRegistration findInnovationAchievementRegistrationByPrimaryKey(Integer id) {
		return innovationAchievementRegistrationDAO.findInnovationAchievementRegistrationByPrimaryKey(id);
	}

	/**
	 * Return a count of all InnovationAchievementRegistration entity
	 * 
	 */
	@Transactional
	public Integer countInnovationAchievementRegistrations() {
		return ((Long) innovationAchievementRegistrationDAO.createQuerySingleResult("select count(o) from InnovationAchievementRegistration o").getSingleResult()).intValue();
	}

	/**
	 * Load an existing InnovationAchievementRegistration entity
	 * 
	 */
	@Transactional
	public Set<InnovationAchievementRegistration> loadInnovationAchievementRegistrations() {
		return innovationAchievementRegistrationDAO.findAllInnovationAchievementRegistrations();
	}

	/**
	 * Return all InnovationAchievementRegistration entity
	 * 
	 */
	@Transactional
	public List<InnovationAchievementRegistration> findAllInnovationAchievementRegistrations(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<InnovationAchievementRegistration>(innovationAchievementRegistrationDAO.findAllInnovationAchievementRegistrations(startResult, maxRows));
	}

	/**
	 * Delete an existing InnovationAchievementRegistration entity
	 * 
	 */
	@Transactional
	public void deleteInnovationAchievementRegistration(InnovationAchievementRegistration innovationachievementregistration) {
		innovationAchievementRegistrationDAO.remove(innovationachievementregistration);
		innovationAchievementRegistrationDAO.flush();
	}

	/**
	 * Save an existing InnovationAchievementRegistration entity
	 * 
	 */
	@Transactional
	public void saveInnovationAchievementRegistration(InnovationAchievementRegistration innovationachievementregistration) {
		InnovationAchievementRegistration existingInnovationAchievementRegistration = innovationAchievementRegistrationDAO.findInnovationAchievementRegistrationByPrimaryKey(innovationachievementregistration.getId());

		if (existingInnovationAchievementRegistration != null) {
			if (existingInnovationAchievementRegistration != innovationachievementregistration) {
				existingInnovationAchievementRegistration.setId(innovationachievementregistration.getId());
				existingInnovationAchievementRegistration.setUsername(innovationachievementregistration.getUsername());
				existingInnovationAchievementRegistration.setCname(innovationachievementregistration.getCname());
				existingInnovationAchievementRegistration.setLabRoomName(innovationachievementregistration.getLabRoomName());
				existingInnovationAchievementRegistration.setInnovationName(innovationachievementregistration.getInnovationName());
				existingInnovationAchievementRegistration.setScore(innovationachievementregistration.getScore());
				existingInnovationAchievementRegistration.setLabRoomNumber(innovationachievementregistration.getLabRoomNumber());
			}
			innovationachievementregistration = innovationAchievementRegistrationDAO.store(existingInnovationAchievementRegistration);
		} else {
			innovationachievementregistration = innovationAchievementRegistrationDAO.store(innovationachievementregistration);
		}
		innovationAchievementRegistrationDAO.flush();
	}
}
