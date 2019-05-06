package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.SmartAgent;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage SmartAgent entities.
 * 
 */
public interface SmartAgentDAO extends JpaDao<SmartAgent> {

	/**
	 * JPQL Query - findSmartAgentByLabNameContaining
	 *
	 */
	public Set<SmartAgent> findSmartAgentByLabNameContaining(String labName) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByLabNameContaining
	 *
	 */
	public Set<SmartAgent> findSmartAgentByLabNameContaining(String labName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentBySerialNo
	 *
	 */
	public SmartAgent findSmartAgentBySerialNo(String serialNo) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentBySerialNo
	 *
	 */
	public SmartAgent findSmartAgentBySerialNo(String serialNo, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByDeviceNumber
	 *
	 */
	public Set<SmartAgent> findSmartAgentByDeviceNumber(String deviceNumber) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByDeviceNumber
	 *
	 */
	public Set<SmartAgent> findSmartAgentByDeviceNumber(String deviceNumber, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentBySerialNoContaining
	 *
	 */
	public Set<SmartAgent> findSmartAgentBySerialNoContaining(String serialNo_1) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentBySerialNoContaining
	 *
	 */
	public Set<SmartAgent> findSmartAgentBySerialNoContaining(String serialNo_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByDeviceName
	 *
	 */
	public Set<SmartAgent> findSmartAgentByDeviceName(String deviceName) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByDeviceName
	 *
	 */
	public Set<SmartAgent> findSmartAgentByDeviceName(String deviceName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByLabName
	 *
	 */
	public Set<SmartAgent> findSmartAgentByLabName(String labName_1) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByLabName
	 *
	 */
	public Set<SmartAgent> findSmartAgentByLabName(String labName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByRemark
	 *
	 */
	public Set<SmartAgent> findSmartAgentByRemark(String remark) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByRemark
	 *
	 */
	public Set<SmartAgent> findSmartAgentByRemark(String remark, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByCurrIpContaining
	 *
	 */
	public Set<SmartAgent> findSmartAgentByCurrIpContaining(String currIp) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByCurrIpContaining
	 *
	 */
	public Set<SmartAgent> findSmartAgentByCurrIpContaining(String currIp, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByLabId
	 *
	 */
	public Set<SmartAgent> findSmartAgentByLabId(Integer labId) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByLabId
	 *
	 */
	public Set<SmartAgent> findSmartAgentByLabId(Integer labId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByDeviceNumberContaining
	 *
	 */
	public Set<SmartAgent> findSmartAgentByDeviceNumberContaining(String deviceNumber_1) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByDeviceNumberContaining
	 *
	 */
	public Set<SmartAgent> findSmartAgentByDeviceNumberContaining(String deviceNumber_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByCurrIp
	 *
	 */
	public Set<SmartAgent> findSmartAgentByCurrIp(String currIp_1) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByCurrIp
	 *
	 */
	public Set<SmartAgent> findSmartAgentByCurrIp(String currIp_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByDbhostContaining
	 *
	 */
	public Set<SmartAgent> findSmartAgentByDbhostContaining(String dbhost) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByDbhostContaining
	 *
	 */
	public Set<SmartAgent> findSmartAgentByDbhostContaining(String dbhost, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByDbhost
	 *
	 */
	public Set<SmartAgent> findSmartAgentByDbhost(String dbhost_1) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByDbhost
	 *
	 */
	public Set<SmartAgent> findSmartAgentByDbhost(String dbhost_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByPrimaryKey
	 *
	 */
	public SmartAgent findSmartAgentByPrimaryKey(String serialNo_2) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByPrimaryKey
	 *
	 */
	public SmartAgent findSmartAgentByPrimaryKey(String serialNo_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByDeviceNameContaining
	 *
	 */
	public Set<SmartAgent> findSmartAgentByDeviceNameContaining(String deviceName_1) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByDeviceNameContaining
	 *
	 */
	public Set<SmartAgent> findSmartAgentByDeviceNameContaining(String deviceName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByRemarkContaining
	 *
	 */
	public Set<SmartAgent> findSmartAgentByRemarkContaining(String remark_1) throws DataAccessException;

	/**
	 * JPQL Query - findSmartAgentByRemarkContaining
	 *
	 */
	public Set<SmartAgent> findSmartAgentByRemarkContaining(String remark_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllSmartAgents
	 *
	 */
	public Set<SmartAgent> findAllSmartAgents() throws DataAccessException;

	/**
	 * JPQL Query - findAllSmartAgents
	 *
	 */
	public Set<SmartAgent> findAllSmartAgents(int startResult, int maxRows) throws DataAccessException;

}