package net.zjcclims.service.basic_data;

import java.util.List;
import java.util.Set;

import net.zjcclims.domain.InnovationAchievementRegistration;

/**
 * Spring service that handles CRUD requests for InnovationAchievementRegistration entities
 * 
 */
public interface InnovationAchievementRegistrationService {

	/**
	 */
	public InnovationAchievementRegistration findInnovationAchievementRegistrationByPrimaryKey(Integer id);

	/**
	 * Return a count of all InnovationAchievementRegistration entity
	 * 
	 */
	public Integer countInnovationAchievementRegistrations();

	/**
	 * Load an existing InnovationAchievementRegistration entity
	 * 
	 */
	public Set<InnovationAchievementRegistration> loadInnovationAchievementRegistrations();

	/**
	 * Return all InnovationAchievementRegistration entity
	 * 
	 */
	public List<InnovationAchievementRegistration> findAllInnovationAchievementRegistrations(Integer startResult, Integer maxRows);

	/**
	 * Delete an existing InnovationAchievementRegistration entity
	 * 
	 */
	public void deleteInnovationAchievementRegistration(InnovationAchievementRegistration innovationachievementregistration);

	/**
	 * Save an existing InnovationAchievementRegistration entity
	 * 
	 */
	public void saveInnovationAchievementRegistration(InnovationAchievementRegistration innovationachievementregistration_1);
}