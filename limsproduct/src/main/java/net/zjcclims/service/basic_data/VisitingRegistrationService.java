package net.zjcclims.service.basic_data;

import java.util.List;
import java.util.Set;

import net.zjcclims.domain.VisitingRegistration;

/**
 * Spring service that handles CRUD requests for VisitingRegistration entities
 * 
 */
public interface VisitingRegistrationService {

	/**
	 * Load an existing VisitingRegistration entity
	 * 
	 */
	public Set<VisitingRegistration> loadVisitingRegistrations();

	/**
	 * Return a count of all VisitingRegistration entity
	 * 
	 */
	public Integer countVisitingRegistrations();

	/**
	 * Save an existing VisitingRegistration entity
	 * 
	 */
	public void saveVisitingRegistration(VisitingRegistration visitingregistration);

	/**
	 * Delete an existing VisitingRegistration entity
	 * 
	 */
	public void deleteVisitingRegistration(VisitingRegistration visitingregistration_1);

	/**
	 */
	public VisitingRegistration findVisitingRegistrationByPrimaryKey(Integer id);

	/**
	 * Return all VisitingRegistration entity
	 * 
	 */
	public List<VisitingRegistration> findAllVisitingRegistrations(Integer startResult, Integer maxRows);
}