package net.zjcclims.dao;

import net.zjcclims.domain.MInspectSetting;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage MInspectSetting entities.
 *
 */
public interface MInspectSettingDAO extends JpaDao<MInspectSetting> {

	/**
	 * JPQL Query - findMInspectSettingByPrimaryKey
	 *
	 */
	public MInspectSetting findMInspectSettingByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findMInspectSettingByPrimaryKey
	 *
	 */
	public MInspectSetting findMInspectSettingByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllMInspectSettings
	 *
	 */
	public Set<MInspectSetting> findAllMInspectSettings() throws DataAccessException;

	/**
	 * JPQL Query - findAllMInspectSettings
	 *
	 */
	public Set<MInspectSetting> findAllMInspectSettings(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findMInspectSettingById
	 *
	 */
	public MInspectSetting findMInspectSettingById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findMInspectSettingById
	 *
	 */
	public MInspectSetting findMInspectSettingById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findMInspectSettingByBatchId
	 *
	 */
	public Set<MInspectSetting> findMInspectSettingByBatchId(Integer batchId) throws DataAccessException;

	/**
	 * JPQL Query - findMInspectSettingByBatchId
	 *
	 */
	public Set<MInspectSetting> findMInspectSettingByBatchId(Integer batchId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findMInspectSettingByStandardId
	 *
	 */
	public Set<MInspectSetting> findMInspectSettingByStandardId(Integer standardId) throws DataAccessException;

	/**
	 * JPQL Query - findMInspectSettingByStandardId
	 *
	 */
	public Set<MInspectSetting> findMInspectSettingByStandardId(Integer standardId, int startResult, int maxRows) throws DataAccessException;

}