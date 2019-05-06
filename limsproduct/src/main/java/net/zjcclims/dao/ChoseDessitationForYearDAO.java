package net.zjcclims.dao;

import net.zjcclims.domain.ChoseDessitationForYear;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Calendar;
import java.util.Set;

/**
 * DAO to manage ChoseDessitationForYear entities.
 * 
 */
public interface ChoseDessitationForYearDAO extends
        JpaDao<ChoseDessitationForYear> {

	/**
	 * JPQL Query - findChoseDessitationForYearByDocumentId
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByDocumentId(Integer documentId) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByDocumentId
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByDocumentId(Integer documentId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByRequirementsContaining
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByRequirementsContaining(String requirements) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByRequirementsContaining
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByRequirementsContaining(String requirements, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByStudent
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByStudent(String student) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByStudent
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByStudent(String student, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByTeacherContaining
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByTeacherContaining(String teacher) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByTeacherContaining
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByTeacherContaining(String teacher, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByTeacher
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByTeacher(String teacher_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByTeacher
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByTeacher(String teacher_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByStudentContaining
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByStudentContaining(String student_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByStudentContaining
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByStudentContaining(String student_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByState
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByState(Integer state) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByState
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByState(Integer state, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByPrimaryKey
	 *
	 */
	public ChoseDessitationForYear findChoseDessitationForYearByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByPrimaryKey
	 *
	 */
	public ChoseDessitationForYear findChoseDessitationForYearByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearById
	 *
	 */
	public ChoseDessitationForYear findChoseDessitationForYearById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearById
	 *
	 */
	public ChoseDessitationForYear findChoseDessitationForYearById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByFinishTimeAfter
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByFinishTimeAfter(java.util.Calendar finishTime) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByFinishTimeAfter
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByFinishTimeAfter(Calendar finishTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByRequirements
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByRequirements(String requirements_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByRequirements
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByRequirements(String requirements_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByFinishTimeBefore
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByFinishTimeBefore(java.util.Calendar finishTime_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByFinishTimeBefore
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByFinishTimeBefore(Calendar finishTime_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByStudentCname
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByStudentCname(String studentCname) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByStudentCname
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByStudentCname(String studentCname, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByThemeContaining
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByThemeContaining(String theme) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByThemeContaining
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByThemeContaining(String theme, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllChoseDessitationForYears
	 *
	 */
	public Set<ChoseDessitationForYear> findAllChoseDessitationForYears() throws DataAccessException;

	/**
	 * JPQL Query - findAllChoseDessitationForYears
	 *
	 */
	public Set<ChoseDessitationForYear> findAllChoseDessitationForYears(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByFinishTime
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByFinishTime(java.util.Calendar finishTime_2) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByFinishTime
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByFinishTime(Calendar finishTime_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByStudentCnameContaining
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByStudentCnameContaining(String studentCname_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByStudentCnameContaining
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByStudentCnameContaining(String studentCname_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByTheme
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByTheme(String theme_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByTheme
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByTheme(String theme_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByTheYear
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByTheYear(Integer theYear) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDessitationForYearByTheYear
	 *
	 */
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByTheYear(Integer theYear, int startResult, int maxRows) throws DataAccessException;

}