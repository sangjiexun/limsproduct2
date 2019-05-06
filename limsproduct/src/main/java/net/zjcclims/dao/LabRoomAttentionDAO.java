package net.zjcclims.dao;

import net.zjcclims.domain.LabRoomAttention;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Calendar;
import java.util.Set;

/**
 * DAO to manage LabRoomAttention entities.
 * 
 */
public interface LabRoomAttentionDAO extends JpaDao<LabRoomAttention> {

	/**
	 * JPQL Query - findLabRoomAttentionByDate
	 *
	 */
	public Set<LabRoomAttention> findLabRoomAttentionByDate(Calendar date) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomAttentionByDate
	 *
	 */
	public Set<LabRoomAttention> findLabRoomAttentionByDate(Calendar date, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomAttentionByUsernameContaining
	 *
	 */
	public Set<LabRoomAttention> findLabRoomAttentionByUsernameContaining(String username) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomAttentionByUsernameContaining
	 *
	 */
	public Set<LabRoomAttention> findLabRoomAttentionByUsernameContaining(String username, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomAttentionByCnameContaining
	 *
	 */
	public Set<LabRoomAttention> findLabRoomAttentionByCnameContaining(String cname) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomAttentionByCnameContaining
	 *
	 */
	public Set<LabRoomAttention> findLabRoomAttentionByCnameContaining(String cname, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomAttentionByPrimaryKey
	 *
	 */
	public LabRoomAttention findLabRoomAttentionByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomAttentionByPrimaryKey
	 *
	 */
	public LabRoomAttention findLabRoomAttentionByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomAttentionByUsername
	 *
	 */
	public Set<LabRoomAttention> findLabRoomAttentionByUsername(String username_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomAttentionByUsername
	 *
	 */
	public Set<LabRoomAttention> findLabRoomAttentionByUsername(String username_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomAttentionByCname
	 *
	 */
	public Set<LabRoomAttention> findLabRoomAttentionByCname(String cname_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomAttentionByCname
	 *
	 */
	public Set<LabRoomAttention> findLabRoomAttentionByCname(String cname_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomAttentionByEnable
	 *
	 */
	public Set<LabRoomAttention> findLabRoomAttentionByEnable(Integer enable) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomAttentionByEnable
	 *
	 */
	public Set<LabRoomAttention> findLabRoomAttentionByEnable(Integer enable, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomAttentions
	 *
	 */
	public Set<LabRoomAttention> findAllLabRoomAttentions() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomAttentions
	 *
	 */
	public Set<LabRoomAttention> findAllLabRoomAttentions(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomAttentionById
	 *
	 */
	public LabRoomAttention findLabRoomAttentionById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomAttentionById
	 *
	 */
	public LabRoomAttention findLabRoomAttentionById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomAttentionByLabId
	 *
	 */
	public Set<LabRoomAttention> findLabRoomAttentionByLabId(Integer labId) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomAttentionByLabId
	 *
	 */
	public Set<LabRoomAttention> findLabRoomAttentionByLabId(Integer labId, int startResult, int maxRows) throws DataAccessException;

}