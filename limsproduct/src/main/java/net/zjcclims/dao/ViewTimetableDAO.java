package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.ViewTimetable;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage ViewTimetable entities.
 * 
 */
public interface ViewTimetableDAO extends JpaDao<ViewTimetable> {

	/**
	 * JPQL Query - findViewTimetableByEndTimeContaining
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByEndTimeContaining(String endTime) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByEndTimeContaining
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByEndTimeContaining(String endTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByTimetableStyle
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByTimetableStyle(Integer timetableStyle) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByTimetableStyle
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByTimetableStyle(Integer timetableStyle, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByCourseNo
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByCourseNo(String courseNo) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByCourseNo
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByCourseNo(String courseNo, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByUsername
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByUsername(String username) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByUsername
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByUsername(String username, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableById
	 *
	 */
	public ViewTimetable findViewTimetableById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableById
	 *
	 */
	public ViewTimetable findViewTimetableById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByPIdContaining
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByPIdContaining(String PId) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByPIdContaining
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByPIdContaining(String PId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByEndTime
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByEndTime(String endTime_1) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByEndTime
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByEndTime(String endTime_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByPrimaryKey
	 *
	 */
	public ViewTimetable findViewTimetableByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByPrimaryKey
	 *
	 */
	public ViewTimetable findViewTimetableByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByStartTimeContaining
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByStartTimeContaining(String startTime) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByStartTimeContaining
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByStartTimeContaining(String startTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByUsernameContaining
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByUsernameContaining(String username_1) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByUsernameContaining
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByUsernameContaining(String username_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllViewTimetables
	 *
	 */
	public Set<ViewTimetable> findAllViewTimetables() throws DataAccessException;

	/**
	 * JPQL Query - findAllViewTimetables
	 *
	 */
	public Set<ViewTimetable> findAllViewTimetables(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByCourseNoContaining
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByCourseNoContaining(String courseNo_1) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByCourseNoContaining
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByCourseNoContaining(String courseNo_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByPId
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByPId(String PId_1) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByPId
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByPId(String PId_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByStartTime
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByStartTime(String startTime_1) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByStartTime
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByStartTime(String startTime_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByLabId
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByLabId(Integer labId) throws DataAccessException;

	/**
	 * JPQL Query - findViewTimetableByLabId
	 *
	 */
	public Set<ViewTimetable> findViewTimetableByLabId(Integer labId, int startResult, int maxRows) throws DataAccessException;

}