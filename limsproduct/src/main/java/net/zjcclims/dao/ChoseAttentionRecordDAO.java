package net.zjcclims.dao;

import net.zjcclims.domain.ChoseAttentionRecord;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage ChoseAttentionRecord entities.
 * 
 */
public interface ChoseAttentionRecordDAO extends JpaDao<ChoseAttentionRecord> {

	/**
	 * JPQL Query - findAllChoseAttentionRecords
	 *
	 */
	public Set<ChoseAttentionRecord> findAllChoseAttentionRecords() throws DataAccessException;

	/**
	 * JPQL Query - findAllChoseAttentionRecords
	 *
	 */
	public Set<ChoseAttentionRecord> findAllChoseAttentionRecords(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseAttentionRecordByPrimaryKey
	 *
	 */
	public ChoseAttentionRecord findChoseAttentionRecordByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findChoseAttentionRecordByPrimaryKey
	 *
	 */
	public ChoseAttentionRecord findChoseAttentionRecordByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseAttentionRecordByUsername
	 *
	 */
	public Set<ChoseAttentionRecord> findChoseAttentionRecordByUsername(String username) throws DataAccessException;

	/**
	 * JPQL Query - findChoseAttentionRecordByUsername
	 *
	 */
	public Set<ChoseAttentionRecord> findChoseAttentionRecordByUsername(String username, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseAttentionRecordByUsernameContaining
	 *
	 */
	public Set<ChoseAttentionRecord> findChoseAttentionRecordByUsernameContaining(String username_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseAttentionRecordByUsernameContaining
	 *
	 */
	public Set<ChoseAttentionRecord> findChoseAttentionRecordByUsernameContaining(String username_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseAttentionRecordById
	 *
	 */
	public ChoseAttentionRecord findChoseAttentionRecordById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseAttentionRecordById
	 *
	 */
	public ChoseAttentionRecord findChoseAttentionRecordById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseAttentionRecordByThemeId
	 *
	 */
	public Set<ChoseAttentionRecord> findChoseAttentionRecordByThemeId(Integer themeId) throws DataAccessException;

	/**
	 * JPQL Query - findChoseAttentionRecordByThemeId
	 *
	 */
	public Set<ChoseAttentionRecord> findChoseAttentionRecordByThemeId(Integer themeId, int startResult, int maxRows) throws DataAccessException;

}