package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

import net.zjcclims.domain.VisitingRegistration;

/**
 * DAO to manage VisitingRegistration entities.
 * 
 */
@Repository("VisitingRegistrationDAO")
@Transactional
public class VisitingRegistrationDAOImpl extends AbstractJpaDao<VisitingRegistration>
		implements VisitingRegistrationDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { VisitingRegistration.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new VisitingRegistrationDAOImpl
	 *
	 */
	public VisitingRegistrationDAOImpl() {
		super();
	}

	/**
	 * Get the entity manager that manages persistence unit 
	 *
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Returns the set of entity classes managed by this DAO.
	 *
	 */
	public Set<Class<?>> getTypes() {
		return dataTypes;
	}

	/**
	 * JPQL Query - findVisitingRegistrationByRegistrant
	 *
	 */
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByRegistrant(String registrant) throws DataAccessException {

		return findVisitingRegistrationByRegistrant(registrant, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationByRegistrant
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByRegistrant(String registrant, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVisitingRegistrationByRegistrant", startResult, maxRows, registrant);
		return new LinkedHashSet<VisitingRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findVisitingRegistrationByClass_
	 *
	 */
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByClass_(String class_) throws DataAccessException {

		return findVisitingRegistrationByClass_(class_, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationByClass_
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByClass_(String class_, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVisitingRegistrationByClass_", startResult, maxRows, class_);
		return new LinkedHashSet<VisitingRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findVisitingRegistrationByProjectNameContaining
	 *
	 */
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByProjectNameContaining(String projectName) throws DataAccessException {

		return findVisitingRegistrationByProjectNameContaining(projectName, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationByProjectNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByProjectNameContaining(String projectName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVisitingRegistrationByProjectNameContaining", startResult, maxRows, projectName);
		return new LinkedHashSet<VisitingRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findVisitingRegistrationByVisitingUnitContaining
	 *
	 */
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByVisitingUnitContaining(String visitingUnit) throws DataAccessException {

		return findVisitingRegistrationByVisitingUnitContaining(visitingUnit, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationByVisitingUnitContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByVisitingUnitContaining(String visitingUnit, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVisitingRegistrationByVisitingUnitContaining", startResult, maxRows, visitingUnit);
		return new LinkedHashSet<VisitingRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findVisitingRegistrationByTeacher
	 *
	 */
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByTeacher(String teacher) throws DataAccessException {

		return findVisitingRegistrationByTeacher(teacher, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationByTeacher
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByTeacher(String teacher, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVisitingRegistrationByTeacher", startResult, maxRows, teacher);
		return new LinkedHashSet<VisitingRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findVisitingRegistrationByTeacherContaining
	 *
	 */
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByTeacherContaining(String teacher) throws DataAccessException {

		return findVisitingRegistrationByTeacherContaining(teacher, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationByTeacherContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByTeacherContaining(String teacher, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVisitingRegistrationByTeacherContaining", startResult, maxRows, teacher);
		return new LinkedHashSet<VisitingRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findVisitingRegistrationByProjectName
	 *
	 */
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByProjectName(String projectName) throws DataAccessException {

		return findVisitingRegistrationByProjectName(projectName, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationByProjectName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByProjectName(String projectName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVisitingRegistrationByProjectName", startResult, maxRows, projectName);
		return new LinkedHashSet<VisitingRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findVisitingRegistrationByCourseName
	 *
	 */
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByCourseName(String courseName) throws DataAccessException {

		return findVisitingRegistrationByCourseName(courseName, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationByCourseName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByCourseName(String courseName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVisitingRegistrationByCourseName", startResult, maxRows, courseName);
		return new LinkedHashSet<VisitingRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findVisitingRegistrationByFlag
	 *
	 */
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByFlag(Integer flag) throws DataAccessException {

		return findVisitingRegistrationByFlag(flag, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationByFlag
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByFlag(Integer flag, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVisitingRegistrationByFlag", startResult, maxRows, flag);
		return new LinkedHashSet<VisitingRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findVisitingRegistrationByLabroomVisiting
	 *
	 */
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByLabroomVisiting(String labroomVisiting) throws DataAccessException {

		return findVisitingRegistrationByLabroomVisiting(labroomVisiting, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationByLabroomVisiting
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByLabroomVisiting(String labroomVisiting, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVisitingRegistrationByLabroomVisiting", startResult, maxRows, labroomVisiting);
		return new LinkedHashSet<VisitingRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findVisitingRegistrationById
	 *
	 */
	@Transactional
	public VisitingRegistration findVisitingRegistrationById(Integer id) throws DataAccessException {

		return findVisitingRegistrationById(id, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationById
	 *
	 */

	@Transactional
	public VisitingRegistration findVisitingRegistrationById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findVisitingRegistrationById", startResult, maxRows, id);
			return (net.zjcclims.domain.VisitingRegistration) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findVisitingRegistrationByClass_Containing
	 *
	 */
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByClass_Containing(String class_) throws DataAccessException {

		return findVisitingRegistrationByClass_Containing(class_, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationByClass_Containing
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByClass_Containing(String class_, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVisitingRegistrationByClass_Containing", startResult, maxRows, class_);
		return new LinkedHashSet<VisitingRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findVisitingRegistrationByCourseNameContaining
	 *
	 */
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByCourseNameContaining(String courseName) throws DataAccessException {

		return findVisitingRegistrationByCourseNameContaining(courseName, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationByCourseNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByCourseNameContaining(String courseName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVisitingRegistrationByCourseNameContaining", startResult, maxRows, courseName);
		return new LinkedHashSet<VisitingRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findVisitingRegistrationByVisitingUnit
	 *
	 */
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByVisitingUnit(String visitingUnit) throws DataAccessException {

		return findVisitingRegistrationByVisitingUnit(visitingUnit, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationByVisitingUnit
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByVisitingUnit(String visitingUnit, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVisitingRegistrationByVisitingUnit", startResult, maxRows, visitingUnit);
		return new LinkedHashSet<VisitingRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findVisitingRegistrationByRegistrantContaining
	 *
	 */
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByRegistrantContaining(String registrant) throws DataAccessException {

		return findVisitingRegistrationByRegistrantContaining(registrant, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationByRegistrantContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByRegistrantContaining(String registrant, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVisitingRegistrationByRegistrantContaining", startResult, maxRows, registrant);
		return new LinkedHashSet<VisitingRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findVisitingRegistrationByCourseHours
	 *
	 */
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByCourseHours(String courseHours) throws DataAccessException {

		return findVisitingRegistrationByCourseHours(courseHours, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationByCourseHours
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByCourseHours(String courseHours, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVisitingRegistrationByCourseHours", startResult, maxRows, courseHours);
		return new LinkedHashSet<VisitingRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findVisitingRegistrationByPrimaryKey
	 *
	 */
	@Transactional
	public VisitingRegistration findVisitingRegistrationByPrimaryKey(Integer id) throws DataAccessException {

		return findVisitingRegistrationByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationByPrimaryKey
	 *
	 */

	@Transactional
	public VisitingRegistration findVisitingRegistrationByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findVisitingRegistrationByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.VisitingRegistration) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findVisitingRegistrationByVisitorNumber
	 *
	 */
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByVisitorNumber(Integer visitorNumber) throws DataAccessException {

		return findVisitingRegistrationByVisitorNumber(visitorNumber, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationByVisitorNumber
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByVisitorNumber(Integer visitorNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVisitingRegistrationByVisitorNumber", startResult, maxRows, visitorNumber);
		return new LinkedHashSet<VisitingRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllVisitingRegistrations
	 *
	 */
	@Transactional
	public Set<VisitingRegistration> findAllVisitingRegistrations() throws DataAccessException {

		return findAllVisitingRegistrations(-1, -1);
	}

	/**
	 * JPQL Query - findAllVisitingRegistrations
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VisitingRegistration> findAllVisitingRegistrations(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllVisitingRegistrations", startResult, maxRows);
		return new LinkedHashSet<VisitingRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findVisitingRegistrationByLabroomVisitingContaining
	 *
	 */
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByLabroomVisitingContaining(String labroomVisiting) throws DataAccessException {

		return findVisitingRegistrationByLabroomVisitingContaining(labroomVisiting, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationByLabroomVisitingContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByLabroomVisitingContaining(String labroomVisiting, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVisitingRegistrationByLabroomVisitingContaining", startResult, maxRows, labroomVisiting);
		return new LinkedHashSet<VisitingRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findVisitingRegistrationByCourseHoursContaining
	 *
	 */
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByCourseHoursContaining(String courseHours) throws DataAccessException {

		return findVisitingRegistrationByCourseHoursContaining(courseHours, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationByCourseHoursContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByCourseHoursContaining(String courseHours, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVisitingRegistrationByCourseHoursContaining", startResult, maxRows, courseHours);
		return new LinkedHashSet<VisitingRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findVisitingRegistrationByDate
	 *
	 */
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByDate(java.util.Calendar date) throws DataAccessException {

		return findVisitingRegistrationByDate(date, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationByDate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByDate(java.util.Calendar date, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVisitingRegistrationByDate", startResult, maxRows, date);
		return new LinkedHashSet<VisitingRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findVisitingRegistrationByStudentNumber
	 *
	 */
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByStudentNumber(Integer studentNumber) throws DataAccessException {

		return findVisitingRegistrationByStudentNumber(studentNumber, -1, -1);
	}

	/**
	 * JPQL Query - findVisitingRegistrationByStudentNumber
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VisitingRegistration> findVisitingRegistrationByStudentNumber(Integer studentNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVisitingRegistrationByStudentNumber", startResult, maxRows, studentNumber);
		return new LinkedHashSet<VisitingRegistration>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(VisitingRegistration entity) {
		return true;
	}
}
