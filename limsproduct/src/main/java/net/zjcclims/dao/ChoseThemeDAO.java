package net.zjcclims.dao;

import net.zjcclims.domain.ChoseTheme;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Calendar;
import java.util.Set;

/**
 * DAO to manage ChoseTheme entities.
 * 
 */
public interface ChoseThemeDAO extends JpaDao<ChoseTheme> {

	/**
	 * JPQL Query - findChoseThemeByTeacherNumber
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByTeacherNumber(Integer teacherNumber) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByTeacherNumber
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByTeacherNumber(Integer teacherNumber, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByAdvanceDay
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByAdvanceDay(Integer advanceDay) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByAdvanceDay
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByAdvanceDay(Integer advanceDay, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByMaxStudent
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByMaxStudent(Integer maxStudent) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByMaxStudent
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByMaxStudent(Integer maxStudent, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByEndTime
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByEndTime(java.util.Calendar endTime) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByEndTime
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByEndTime(Calendar endTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllChoseThemes
	 *
	 */
	public Set<ChoseTheme> findAllChoseThemes() throws DataAccessException;

	/**
	 * JPQL Query - findAllChoseThemes
	 *
	 */
	public Set<ChoseTheme> findAllChoseThemes(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByStudentNumber
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByStudentNumber(Integer studentNumber) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByStudentNumber
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByStudentNumber(Integer studentNumber, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByTheYear
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByTheYear(Integer theYear) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByTheYear
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByTheYear(Integer theYear, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByBatchNumber
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByBatchNumber(Integer batchNumber) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByBatchNumber
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByBatchNumber(Integer batchNumber, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByEndTimeBefore
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByEndTimeBefore(java.util.Calendar endTime_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByEndTimeBefore
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByEndTimeBefore(Calendar endTime_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeById
	 *
	 */
	public ChoseTheme findChoseThemeById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeById
	 *
	 */
	public ChoseTheme findChoseThemeById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByStartTimeAfter
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByStartTimeAfter(java.util.Calendar startTime) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByStartTimeAfter
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByStartTimeAfter(Calendar startTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByEndTimeAfter
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByEndTimeAfter(java.util.Calendar endTime_2) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByEndTimeAfter
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByEndTimeAfter(Calendar endTime_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByState
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByState(Integer state) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByState
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByState(Integer state, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByPrimaryKey
	 *
	 */
	public ChoseTheme findChoseThemeByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByPrimaryKey
	 *
	 */
	public ChoseTheme findChoseThemeByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByStartTime
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByStartTime(java.util.Calendar startTime_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByStartTime
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByStartTime(Calendar startTime_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByStartTimeBefore
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByStartTimeBefore(java.util.Calendar startTime_2) throws DataAccessException;

	/**
	 * JPQL Query - findChoseThemeByStartTimeBefore
	 *
	 */
	public Set<ChoseTheme> findChoseThemeByStartTimeBefore(Calendar startTime_2, int startResult, int maxRows) throws DataAccessException;

}