package net.zjcclims.service.basic_data;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import net.zjcclims.dao.VisitingRegistrationDAO;

import net.zjcclims.domain.VisitingRegistration;

/**
 * Spring service that handles CRUD requests for VisitingRegistration entities
 * 
 */

@Service("VisitingRegistrationService")
@Transactional
public class VisitingRegistrationServiceImpl implements
		VisitingRegistrationService {

	/**
	 * DAO injected by Spring that manages VisitingRegistration entities
	 * 
	 */
	@Autowired
	private VisitingRegistrationDAO visitingRegistrationDAO;

	/**
	 * Instantiates a new VisitingRegistrationServiceImpl.
	 *
	 */
	public VisitingRegistrationServiceImpl() {
	}

	/**
	 * Load an existing VisitingRegistration entity
	 * 
	 */
	@Transactional
	public Set<VisitingRegistration> loadVisitingRegistrations() {
		return visitingRegistrationDAO.findAllVisitingRegistrations();
	}

	/**
	 * Return a count of all VisitingRegistration entity
	 * 
	 */
	@Transactional
	public Integer countVisitingRegistrations() {
		return ((Long) visitingRegistrationDAO.createQuerySingleResult("select count(o) from VisitingRegistration o").getSingleResult()).intValue();
	}

	/**
	 * Save an existing VisitingRegistration entity
	 * 
	 */
	@Transactional
	public void saveVisitingRegistration(VisitingRegistration visitingregistration) {
		VisitingRegistration existingVisitingRegistration = visitingRegistrationDAO.findVisitingRegistrationByPrimaryKey(visitingregistration.getId());

		if (existingVisitingRegistration != null) {
			if (existingVisitingRegistration != visitingregistration) {
				existingVisitingRegistration.setId(visitingregistration.getId());
				existingVisitingRegistration.setCourseName(visitingregistration.getCourseName());
				existingVisitingRegistration.setProjectName(visitingregistration.getProjectName());
				existingVisitingRegistration.setTeacher(visitingregistration.getTeacher());
				existingVisitingRegistration.setClass_(visitingregistration.getClass_());
				existingVisitingRegistration.setStudentNumber(visitingregistration.getStudentNumber());
				existingVisitingRegistration.setDate(visitingregistration.getDate());
				existingVisitingRegistration.setLabroomVisiting(visitingregistration.getLabroomVisiting());
				existingVisitingRegistration.setCourseHours(visitingregistration.getCourseHours());
				existingVisitingRegistration.setVisitingUnit(visitingregistration.getVisitingUnit());
				existingVisitingRegistration.setVisitorNumber(visitingregistration.getVisitorNumber());
				existingVisitingRegistration.setRegistrant(visitingregistration.getRegistrant());
				existingVisitingRegistration.setFlag(visitingregistration.getFlag());
			}
			visitingregistration = visitingRegistrationDAO.store(existingVisitingRegistration);
		} else {
			visitingregistration = visitingRegistrationDAO.store(visitingregistration);
		}
		visitingRegistrationDAO.flush();
	}

	/**
	 * Delete an existing VisitingRegistration entity
	 * 
	 */
	@Transactional
	public void deleteVisitingRegistration(VisitingRegistration visitingregistration) {
		visitingRegistrationDAO.remove(visitingregistration);
		visitingRegistrationDAO.flush();
	}

	/**
	 */
	@Transactional
	public VisitingRegistration findVisitingRegistrationByPrimaryKey(Integer id) {
		return visitingRegistrationDAO.findVisitingRegistrationByPrimaryKey(id);
	}

	/**
	 * Return all VisitingRegistration entity
	 * 
	 */
	@Transactional
	public List<VisitingRegistration> findAllVisitingRegistrations(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<VisitingRegistration>(visitingRegistrationDAO.findAllVisitingRegistrations(startResult, maxRows));
	}
}
