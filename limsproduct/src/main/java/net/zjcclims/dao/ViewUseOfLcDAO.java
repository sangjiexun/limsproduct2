package net.zjcclims.dao;

import java.util.Calendar;
import java.util.Set;

import net.zjcclims.domain.ViewUseOfLc;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage ViewUseOfLc entities.
 * 
 */
public interface ViewUseOfLcDAO extends JpaDao<ViewUseOfLc> {

	/**
	 * JPQL Query - findViewUseOfLcByLabRoomNameContaining
	 *
	 */
	public Set<ViewUseOfLc> findViewUseOfLcByLabRoomNameContaining(String labRoomName) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcByLabRoomNameContaining
	 *
	 */
	public Set<ViewUseOfLc> findViewUseOfLcByLabRoomNameContaining(String labRoomName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcByUser
	 *
	 */
	public Set<ViewUseOfLc> findViewUseOfLcByUser(String user) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcByUser
	 *
	 */
	public Set<ViewUseOfLc> findViewUseOfLcByUser(String user, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcByLabRoomName
	 *
	 */
	public Set<ViewUseOfLc> findViewUseOfLcByLabRoomName(String labRoomName_1) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcByLabRoomName
	 *
	 */
	public Set<ViewUseOfLc> findViewUseOfLcByLabRoomName(String labRoomName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcByMachinenameContaining
	 *
	 */
	public Set<ViewUseOfLc> findViewUseOfLcByMachinenameContaining(String machinename) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcByMachinenameContaining
	 *
	 */
	public Set<ViewUseOfLc> findViewUseOfLcByMachinenameContaining(String machinename, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcByBegintime
	 *
	 */
	public Set<ViewUseOfLc> findViewUseOfLcByBegintime(java.util.Calendar begintime) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcByBegintime
	 *
	 */
	public Set<ViewUseOfLc> findViewUseOfLcByBegintime(Calendar begintime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcByEndtime
	 *
	 */
	public Set<ViewUseOfLc> findViewUseOfLcByEndtime(java.util.Calendar endtime) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcByEndtime
	 *
	 */
	public Set<ViewUseOfLc> findViewUseOfLcByEndtime(Calendar endtime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcByPrimaryKey
	 *
	 */
	public ViewUseOfLc findViewUseOfLcByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcByPrimaryKey
	 *
	 */
	public ViewUseOfLc findViewUseOfLcByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcByUserContaining
	 *
	 */
	public Set<ViewUseOfLc> findViewUseOfLcByUserContaining(String user_1) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcByUserContaining
	 *
	 */
	public Set<ViewUseOfLc> findViewUseOfLcByUserContaining(String user_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcById
	 *
	 */
	public ViewUseOfLc findViewUseOfLcById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcById
	 *
	 */
	public ViewUseOfLc findViewUseOfLcById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcByMachinename
	 *
	 */
	public Set<ViewUseOfLc> findViewUseOfLcByMachinename(String machinename_1) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcByMachinename
	 *
	 */
	public Set<ViewUseOfLc> findViewUseOfLcByMachinename(String machinename_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllViewUseOfLcs
	 *
	 */
	public Set<ViewUseOfLc> findAllViewUseOfLcs() throws DataAccessException;

	/**
	 * JPQL Query - findAllViewUseOfLcs
	 *
	 */
	public Set<ViewUseOfLc> findAllViewUseOfLcs(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcByDeviceNumber
	 *
	 */
	public Set<ViewUseOfLc> findViewUseOfLcByDeviceNumber(Integer deviceNumber) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcByDeviceNumber
	 *
	 */
	public Set<ViewUseOfLc> findViewUseOfLcByDeviceNumber(Integer deviceNumber, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcByLabRoomId
	 *
	 */
	public Set<ViewUseOfLc> findViewUseOfLcByLabRoomId(Integer labRoomId) throws DataAccessException;

	/**
	 * JPQL Query - findViewUseOfLcByLabRoomId
	 *
	 */
	public Set<ViewUseOfLc> findViewUseOfLcByLabRoomId(Integer labRoomId, int startResult, int maxRows) throws DataAccessException;

}