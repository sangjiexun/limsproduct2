package net.zjcclims.dao;

import net.zjcclims.domain.LabInspectSetting;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Calendar;
import java.util.Set;

/**
 * DAO to manage LabInspectSetting entities.
 *
 */
public interface LabInspectSettingDAO extends
		JpaDao<LabInspectSetting> {

	/**
	 * JPQL Query - findLabInspectSettingBySemeterContaining
	 *
	 */
	public Set<LabInspectSetting> findLabInspectSettingBySemeterContaining(String semeter) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingBySemeterContaining
	 *
	 */
	public Set<LabInspectSetting> findLabInspectSettingBySemeterContaining(String semeter, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingByEndTimeAfter
	 *
	 */
	public Set<LabInspectSetting> findLabInspectSettingByEndTimeAfter(Calendar endTime) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingByEndTimeAfter
	 *
	 */
	public Set<LabInspectSetting> findLabInspectSettingByEndTimeAfter(Calendar endTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingByPrimaryKey
	 *
	 */
	public LabInspectSetting findLabInspectSettingByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingByPrimaryKey
	 *
	 */
	public LabInspectSetting findLabInspectSettingByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingByIsRegular
	 *
	 */
	public Set<LabInspectSetting> findLabInspectSettingByIsRegular(Boolean isRegular) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingByIsRegular
	 *
	 */
	public Set<LabInspectSetting> findLabInspectSettingByIsRegular(Boolean isRegular, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingByEndTimeBefore
	 *
	 */
	public Set<LabInspectSetting> findLabInspectSettingByEndTimeBefore(Calendar endTime_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingByEndTimeBefore
	 *
	 */
	public Set<LabInspectSetting> findLabInspectSettingByEndTimeBefore(Calendar endTime_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingByStartTimeAfter
	 *
	 */
	public Set<LabInspectSetting> findLabInspectSettingByStartTimeAfter(Calendar startTime) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingByStartTimeAfter
	 *
	 */
	public Set<LabInspectSetting> findLabInspectSettingByStartTimeAfter(Calendar startTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingBySemeter
	 *
	 */
	public Set<LabInspectSetting> findLabInspectSettingBySemeter(String semeter_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingBySemeter
	 *
	 */
	public Set<LabInspectSetting> findLabInspectSettingBySemeter(String semeter_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingByWeek
	 *
	 */
	public Set<LabInspectSetting> findLabInspectSettingByWeek(Integer week) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingByWeek
	 *
	 */
	public Set<LabInspectSetting> findLabInspectSettingByWeek(Integer week, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabInspectSettings
	 *
	 */
	public Set<LabInspectSetting> findAllLabInspectSettings() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabInspectSettings
	 *
	 */
	public Set<LabInspectSetting> findAllLabInspectSettings(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingByStartTime
	 *
	 */
	public Set<LabInspectSetting> findLabInspectSettingByStartTime(Calendar startTime_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingByStartTime
	 *
	 */
	public Set<LabInspectSetting> findLabInspectSettingByStartTime(Calendar startTime_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingByStartTimeBefore
	 *
	 */
	public Set<LabInspectSetting> findLabInspectSettingByStartTimeBefore(Calendar startTime_2) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingByStartTimeBefore
	 *
	 */
	public Set<LabInspectSetting> findLabInspectSettingByStartTimeBefore(Calendar startTime_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingById
	 *
	 */
	public LabInspectSetting findLabInspectSettingById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingById
	 *
	 */
	public LabInspectSetting findLabInspectSettingById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingByEndTime
	 *
	 */
	public Set<LabInspectSetting> findLabInspectSettingByEndTime(Calendar endTime_2) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectSettingByEndTime
	 *
	 */
	public Set<LabInspectSetting> findLabInspectSettingByEndTime(Calendar endTime_2, int startResult, int maxRows) throws DataAccessException;

}