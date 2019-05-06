package net.zjcclims.dao;

import java.util.Calendar;
import java.util.Set;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

import net.zjcclims.domain.VisitingRegistration;

/**
 * DAO to manage VisitingRegistration entities.
 * 
 */
public interface VisitingRegistrationDAO extends JpaDao<VisitingRegistration> {

	/**
	 * JPQL Query - findVisitingRegistrationByRegistrant
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByRegistrant(String registrant) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByRegistrant
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByRegistrant(String registrant, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByClass_
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByClass_(String class_) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByClass_
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByClass_(String class_, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByProjectNameContaining
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByProjectNameContaining(String projectName) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByProjectNameContaining
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByProjectNameContaining(String projectName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByVisitingUnitContaining
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByVisitingUnitContaining(String visitingUnit) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByVisitingUnitContaining
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByVisitingUnitContaining(String visitingUnit, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByTeacher
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByTeacher(String teacher) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByTeacher
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByTeacher(String teacher, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByTeacherContaining
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByTeacherContaining(String teacher_1) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByTeacherContaining
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByTeacherContaining(String teacher_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByProjectName
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByProjectName(String projectName_1) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByProjectName
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByProjectName(String projectName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByCourseName
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByCourseName(String courseName) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByCourseName
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByCourseName(String courseName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByFlag
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByFlag(Integer flag) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByFlag
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByFlag(Integer flag, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByLabroomVisiting
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByLabroomVisiting(String labroomVisiting) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByLabroomVisiting
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByLabroomVisiting(String labroomVisiting, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationById
	 *
	 */
	public VisitingRegistration findVisitingRegistrationById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationById
	 *
	 */
	public VisitingRegistration findVisitingRegistrationById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByClass_Containing
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByClass_Containing(String class__1) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByClass_Containing
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByClass_Containing(String class__1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByCourseNameContaining
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByCourseNameContaining(String courseName_1) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByCourseNameContaining
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByCourseNameContaining(String courseName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByVisitingUnit
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByVisitingUnit(String visitingUnit_1) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByVisitingUnit
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByVisitingUnit(String visitingUnit_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByRegistrantContaining
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByRegistrantContaining(String registrant_1) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByRegistrantContaining
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByRegistrantContaining(String registrant_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByCourseHours
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByCourseHours(String courseHours) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByCourseHours
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByCourseHours(String courseHours, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByPrimaryKey
	 *
	 */
	public VisitingRegistration findVisitingRegistrationByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByPrimaryKey
	 *
	 */
	public VisitingRegistration findVisitingRegistrationByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByVisitorNumber
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByVisitorNumber(Integer visitorNumber) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByVisitorNumber
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByVisitorNumber(Integer visitorNumber, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllVisitingRegistrations
	 *
	 */
	public Set<VisitingRegistration> findAllVisitingRegistrations() throws DataAccessException;

	/**
	 * JPQL Query - findAllVisitingRegistrations
	 *
	 */
	public Set<VisitingRegistration> findAllVisitingRegistrations(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByLabroomVisitingContaining
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByLabroomVisitingContaining(String labroomVisiting_1) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByLabroomVisitingContaining
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByLabroomVisitingContaining(String labroomVisiting_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByCourseHoursContaining
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByCourseHoursContaining(String courseHours_1) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByCourseHoursContaining
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByCourseHoursContaining(String courseHours_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByDate
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByDate(java.util.Calendar date) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByDate
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByDate(Calendar date, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByStudentNumber
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByStudentNumber(Integer studentNumber) throws DataAccessException;

	/**
	 * JPQL Query - findVisitingRegistrationByStudentNumber
	 *
	 */
	public Set<VisitingRegistration> findVisitingRegistrationByStudentNumber(Integer studentNumber, int startResult, int maxRows) throws DataAccessException;

}