package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.SchoolCourseSchedule;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage SchoolCourseSchedule entities.
 * 
 */
public interface SchoolCourseScheduleDAO extends JpaDao<SchoolCourseSchedule> {

	/**
	 * JPQL Query - findSchoolCourseScheduleByWeekType
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByWeekType(String weekType) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByWeekType
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByWeekType(String weekType, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByStartClassSection
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByStartClassSection(Integer startClassSection) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByStartClassSection
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByStartClassSection(Integer startClassSection, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByRemarkContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByRemarkContaining(String remark) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByRemarkContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByRemarkContaining(String remark, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByTeacherContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTeacherContaining(String teacher) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByTeacherContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTeacherContaining(String teacher, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByXkkhContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByXkkhContaining(String xkkh) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByXkkhContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByXkkhContaining(String xkkh, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByIsShared
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByIsShared(Boolean isShared) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByIsShared
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByIsShared(Boolean isShared, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByStartWeek
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByStartWeek(Integer startWeek) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByStartWeek
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByStartWeek(Integer startWeek, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByClassCode
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByClassCode(String classCode) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByClassCode
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByClassCode(String classCode, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByAcademy
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByAcademy(String academy) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByAcademy
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByAcademy(String academy, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByWeekTypeContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByWeekTypeContaining(String weekType_1) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByWeekTypeContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByWeekTypeContaining(String weekType_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByBuildingContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByBuildingContaining(String building) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByBuildingContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByBuildingContaining(String building, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByCourseNameContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByCourseNameContaining(String courseName) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByCourseNameContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByCourseNameContaining(String courseName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByXkkh
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByXkkh(String xkkh_1) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByXkkh
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByXkkh(String xkkh_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllSchoolCourseSchedules
	 *
	 */
	public Set<SchoolCourseSchedule> findAllSchoolCourseSchedules() throws DataAccessException;

	/**
	 * JPQL Query - findAllSchoolCourseSchedules
	 *
	 */
	public Set<SchoolCourseSchedule> findAllSchoolCourseSchedules(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByCourseName
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByCourseName(String courseName_1) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByCourseName
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByCourseName(String courseName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByAcademyContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByAcademyContaining(String academy_1) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByAcademyContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByAcademyContaining(String academy_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByEndClassSection
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByEndClassSection(Integer endClassSection) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByEndClassSection
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByEndClassSection(Integer endClassSection, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByEndWeek
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByEndWeek(Integer endWeek) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByEndWeek
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByEndWeek(Integer endWeek, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByCourseIdContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByCourseIdContaining(String courseId) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByCourseIdContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByCourseIdContaining(String courseId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByTeacherIdContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTeacherIdContaining(String teacherId) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByTeacherIdContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTeacherIdContaining(String teacherId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleById
	 *
	 */
	public SchoolCourseSchedule findSchoolCourseScheduleById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleById
	 *
	 */
	public SchoolCourseSchedule findSchoolCourseScheduleById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByRemark
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByRemark(String remark_1) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByRemark
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByRemark(String remark_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByTeacherId
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTeacherId(String teacherId_1) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByTeacherId
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTeacherId(String teacherId_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByCourseType
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByCourseType(Integer courseType) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByCourseType
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByCourseType(Integer courseType, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByStudentNumber
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByStudentNumber(Integer studentNumber) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByStudentNumber
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByStudentNumber(Integer studentNumber, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByCourseId
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByCourseId(String courseId_1) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByCourseId
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByCourseId(String courseId_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByTeacher
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTeacher(String teacher_1) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByTeacher
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTeacher(String teacher_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByTerm
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTerm(String term) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByTerm
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTerm(String term, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByTermContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTermContaining(String term_1) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByTermContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByTermContaining(String term_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByPeople
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByPeople(Integer people) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByPeople
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByPeople(Integer people, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByWeekday
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByWeekday(Integer weekday) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByWeekday
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByWeekday(Integer weekday, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByPrimaryKey
	 *
	 */
	public SchoolCourseSchedule findSchoolCourseScheduleByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByPrimaryKey
	 *
	 */
	public SchoolCourseSchedule findSchoolCourseScheduleByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByBuilding
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByBuilding(String building_1) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByBuilding
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByBuilding(String building_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByClassCodeContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByClassCodeContaining(String classCode_1) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolCourseScheduleByClassCodeContaining
	 *
	 */
	public Set<SchoolCourseSchedule> findSchoolCourseScheduleByClassCodeContaining(String classCode_1, int startResult, int maxRows) throws DataAccessException;

}