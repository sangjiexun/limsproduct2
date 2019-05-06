package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.SchoolCourseSchedule;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage SchoolCourseSchedule entities.
 * 
 */
@Repository("SchoolCourseScheduleDAO")
@Transactional
public class SchoolCourseScheduleDAOImpl extends AbstractJpaDao<SchoolCourseSchedule>
		implements SchoolCourseScheduleDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { SchoolCourseSchedule.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new SchoolCourseScheduleDAOImpl
	 *
	 */
	public SchoolCourseScheduleDAOImpl() {
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
	 * JPQL Query - findSchoolCourseScheduleByWeekType
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByWeekType(String weekType) throws DataAccessException {

		return findSchoolCourseScheduleByWeekType(weekType, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByWeekType
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByWeekType(String weekType, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByWeekType", startResult, maxRows, weekType);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByStartClassSection
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByStartClassSection(Integer startClassSection) throws DataAccessException {

		return findSchoolCourseScheduleByStartClassSection(startClassSection, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByStartClassSection
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByStartClassSection(Integer startClassSection, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByStartClassSection", startResult, maxRows, startClassSection);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByRemarkContaining
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByRemarkContaining(String remark) throws DataAccessException {

		return findSchoolCourseScheduleByRemarkContaining(remark, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByRemarkContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByRemarkContaining(String remark, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByRemarkContaining", startResult, maxRows, remark);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByTeacherContaining
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTeacherContaining(String teacher) throws DataAccessException {

		return findSchoolCourseScheduleByTeacherContaining(teacher, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByTeacherContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTeacherContaining(String teacher, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByTeacherContaining", startResult, maxRows, teacher);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByXkkhContaining
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByXkkhContaining(String xkkh) throws DataAccessException {

		return findSchoolCourseScheduleByXkkhContaining(xkkh, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByXkkhContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByXkkhContaining(String xkkh, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByXkkhContaining", startResult, maxRows, xkkh);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByIsShared
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByIsShared(Boolean isShared) throws DataAccessException {

		return findSchoolCourseScheduleByIsShared(isShared, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByIsShared
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByIsShared(Boolean isShared, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByIsShared", startResult, maxRows, isShared);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByStartWeek
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByStartWeek(Integer startWeek) throws DataAccessException {

		return findSchoolCourseScheduleByStartWeek(startWeek, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByStartWeek
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByStartWeek(Integer startWeek, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByStartWeek", startResult, maxRows, startWeek);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByClassCode
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByClassCode(String classCode) throws DataAccessException {

		return findSchoolCourseScheduleByClassCode(classCode, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByClassCode
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByClassCode(String classCode, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByClassCode", startResult, maxRows, classCode);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByAcademy
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByAcademy(String academy) throws DataAccessException {

		return findSchoolCourseScheduleByAcademy(academy, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByAcademy
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByAcademy(String academy, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByAcademy", startResult, maxRows, academy);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByWeekTypeContaining
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByWeekTypeContaining(String weekType) throws DataAccessException {

		return findSchoolCourseScheduleByWeekTypeContaining(weekType, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByWeekTypeContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByWeekTypeContaining(String weekType, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByWeekTypeContaining", startResult, maxRows, weekType);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByBuildingContaining
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByBuildingContaining(String building) throws DataAccessException {

		return findSchoolCourseScheduleByBuildingContaining(building, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByBuildingContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByBuildingContaining(String building, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByBuildingContaining", startResult, maxRows, building);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByCourseNameContaining
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByCourseNameContaining(String courseName) throws DataAccessException {

		return findSchoolCourseScheduleByCourseNameContaining(courseName, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByCourseNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByCourseNameContaining(String courseName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByCourseNameContaining", startResult, maxRows, courseName);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByXkkh
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByXkkh(String xkkh) throws DataAccessException {

		return findSchoolCourseScheduleByXkkh(xkkh, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByXkkh
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByXkkh(String xkkh, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByXkkh", startResult, maxRows, xkkh);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllSchoolCourseSchedules
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findAllSchoolCourseSchedules() throws DataAccessException {

		return findAllSchoolCourseSchedules(-1, -1);
	}

	/**
	 * JPQL Query - findAllSchoolCourseSchedules
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findAllSchoolCourseSchedules(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllSchoolCourseSchedules", startResult, maxRows);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByCourseName
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByCourseName(String courseName) throws DataAccessException {

		return findSchoolCourseScheduleByCourseName(courseName, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByCourseName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByCourseName(String courseName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByCourseName", startResult, maxRows, courseName);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByAcademyContaining
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByAcademyContaining(String academy) throws DataAccessException {

		return findSchoolCourseScheduleByAcademyContaining(academy, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByAcademyContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByAcademyContaining(String academy, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByAcademyContaining", startResult, maxRows, academy);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByEndClassSection
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByEndClassSection(Integer endClassSection) throws DataAccessException {

		return findSchoolCourseScheduleByEndClassSection(endClassSection, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByEndClassSection
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByEndClassSection(Integer endClassSection, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByEndClassSection", startResult, maxRows, endClassSection);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByEndWeek
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByEndWeek(Integer endWeek) throws DataAccessException {

		return findSchoolCourseScheduleByEndWeek(endWeek, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByEndWeek
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByEndWeek(Integer endWeek, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByEndWeek", startResult, maxRows, endWeek);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByCourseIdContaining
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByCourseIdContaining(String courseId) throws DataAccessException {

		return findSchoolCourseScheduleByCourseIdContaining(courseId, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByCourseIdContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByCourseIdContaining(String courseId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByCourseIdContaining", startResult, maxRows, courseId);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByTeacherIdContaining
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTeacherIdContaining(String teacherId) throws DataAccessException {

		return findSchoolCourseScheduleByTeacherIdContaining(teacherId, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByTeacherIdContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTeacherIdContaining(String teacherId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByTeacherIdContaining", startResult, maxRows, teacherId);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleById
	 *
	 */
	@Transactional
	public SchoolCourseSchedule findSchoolCourseScheduleById(Integer id) throws DataAccessException {

		return findSchoolCourseScheduleById(id, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleById
	 *
	 */

	@Transactional
	public SchoolCourseSchedule findSchoolCourseScheduleById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSchoolCourseScheduleById", startResult, maxRows, id);
			return (net.zjcclims.domain.SchoolCourseSchedule) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByRemark
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByRemark(String remark) throws DataAccessException {

		return findSchoolCourseScheduleByRemark(remark, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByRemark
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByRemark(String remark, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByRemark", startResult, maxRows, remark);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByTeacherId
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTeacherId(String teacherId) throws DataAccessException {

		return findSchoolCourseScheduleByTeacherId(teacherId, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByTeacherId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTeacherId(String teacherId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByTeacherId", startResult, maxRows, teacherId);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByCourseType
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByCourseType(Integer courseType) throws DataAccessException {

		return findSchoolCourseScheduleByCourseType(courseType, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByCourseType
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByCourseType(Integer courseType, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByCourseType", startResult, maxRows, courseType);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByStudentNumber
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByStudentNumber(Integer studentNumber) throws DataAccessException {

		return findSchoolCourseScheduleByStudentNumber(studentNumber, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByStudentNumber
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByStudentNumber(Integer studentNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByStudentNumber", startResult, maxRows, studentNumber);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByCourseId
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByCourseId(String courseId) throws DataAccessException {

		return findSchoolCourseScheduleByCourseId(courseId, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByCourseId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByCourseId(String courseId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByCourseId", startResult, maxRows, courseId);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByTeacher
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTeacher(String teacher) throws DataAccessException {

		return findSchoolCourseScheduleByTeacher(teacher, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByTeacher
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTeacher(String teacher, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByTeacher", startResult, maxRows, teacher);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByTerm
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTerm(String term) throws DataAccessException {

		return findSchoolCourseScheduleByTerm(term, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByTerm
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTerm(String term, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByTerm", startResult, maxRows, term);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByTermContaining
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTermContaining(String term) throws DataAccessException {

		return findSchoolCourseScheduleByTermContaining(term, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByTermContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTermContaining(String term, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByTermContaining", startResult, maxRows, term);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByPeople
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByPeople(Integer people) throws DataAccessException {

		return findSchoolCourseScheduleByPeople(people, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByPeople
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByPeople(Integer people, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByPeople", startResult, maxRows, people);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByWeekday
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByWeekday(Integer weekday) throws DataAccessException {

		return findSchoolCourseScheduleByWeekday(weekday, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByWeekday
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByWeekday(Integer weekday, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByWeekday", startResult, maxRows, weekday);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByPrimaryKey
	 *
	 */
	@Transactional
	public SchoolCourseSchedule findSchoolCourseScheduleByPrimaryKey(Integer id) throws DataAccessException {

		return findSchoolCourseScheduleByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByPrimaryKey
	 *
	 */

	@Transactional
	public SchoolCourseSchedule findSchoolCourseScheduleByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSchoolCourseScheduleByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.SchoolCourseSchedule) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByBuilding
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByBuilding(String building) throws DataAccessException {

		return findSchoolCourseScheduleByBuilding(building, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByBuilding
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByBuilding(String building, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByBuilding", startResult, maxRows, building);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByClassCodeContaining
	 *
	 */
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByClassCodeContaining(String classCode) throws DataAccessException {

		return findSchoolCourseScheduleByClassCodeContaining(classCode, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolCourseScheduleByClassCodeContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByClassCodeContaining(String classCode, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolCourseScheduleByClassCodeContaining", startResult, maxRows, classCode);
		return new LinkedHashSet<SchoolCourseSchedule>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(SchoolCourseSchedule entity) {
		return true;
	}
}
