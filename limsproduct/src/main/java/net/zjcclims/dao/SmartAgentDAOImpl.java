package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.SmartAgent;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage SmartAgent entities.
 * 
 */
@Repository("SmartAgentDAO")
@Transactional
public class SmartAgentDAOImpl extends AbstractJpaDao<SmartAgent> implements
		SmartAgentDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { SmartAgent.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new SmartAgentDAOImpl
	 *
	 */
	public SmartAgentDAOImpl() {
		super();
	}

	/**
	 * Get the entity manager that manages persistence unit 
	 *
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Returns the set of entity classes managed by this DAO.
	 *
	 */
	public Set<Class<?>> getTypes() {
		return dataTypes;
	}

	/**
	 * JPQL Query - findSmartAgentByLabNameContaining
	 *
	 */
	@Transactional
	public Set<SmartAgent> findSmartAgentByLabNameContaining(String labName) throws DataAccessException {

		return findSmartAgentByLabNameContaining(labName, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentByLabNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgent> findSmartAgentByLabNameContaining(String labName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentByLabNameContaining", startResult, maxRows, labName);
		return new LinkedHashSet<SmartAgent>(query.getResultList());
	}

	/**
	 * JPQL Query - findSmartAgentBySerialNo
	 *
	 */
	@Transactional
	public SmartAgent findSmartAgentBySerialNo(String serialNo) throws DataAccessException {

		return findSmartAgentBySerialNo(serialNo, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentBySerialNo
	 *
	 */

	@Transactional
	public SmartAgent findSmartAgentBySerialNo(String serialNo, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSmartAgentBySerialNo", startResult, maxRows, serialNo);
			return (net.zjcclims.domain.SmartAgent) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findSmartAgentByDeviceNumber
	 *
	 */
	@Transactional
	public Set<SmartAgent> findSmartAgentByDeviceNumber(String deviceNumber) throws DataAccessException {

		return findSmartAgentByDeviceNumber(deviceNumber, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentByDeviceNumber
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgent> findSmartAgentByDeviceNumber(String deviceNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentByDeviceNumber", startResult, maxRows, deviceNumber);
		return new LinkedHashSet<SmartAgent>(query.getResultList());
	}

	/**
	 * JPQL Query - findSmartAgentBySerialNoContaining
	 *
	 */
	@Transactional
	public Set<SmartAgent> findSmartAgentBySerialNoContaining(String serialNo) throws DataAccessException {

		return findSmartAgentBySerialNoContaining(serialNo, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentBySerialNoContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgent> findSmartAgentBySerialNoContaining(String serialNo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentBySerialNoContaining", startResult, maxRows, serialNo);
		return new LinkedHashSet<SmartAgent>(query.getResultList());
	}

	/**
	 * JPQL Query - findSmartAgentByDeviceName
	 *
	 */
	@Transactional
	public Set<SmartAgent> findSmartAgentByDeviceName(String deviceName) throws DataAccessException {

		return findSmartAgentByDeviceName(deviceName, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentByDeviceName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgent> findSmartAgentByDeviceName(String deviceName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentByDeviceName", startResult, maxRows, deviceName);
		return new LinkedHashSet<SmartAgent>(query.getResultList());
	}

	/**
	 * JPQL Query - findSmartAgentByLabName
	 *
	 */
	@Transactional
	public Set<SmartAgent> findSmartAgentByLabName(String labName) throws DataAccessException {

		return findSmartAgentByLabName(labName, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentByLabName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgent> findSmartAgentByLabName(String labName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentByLabName", startResult, maxRows, labName);
		return new LinkedHashSet<SmartAgent>(query.getResultList());
	}

	/**
	 * JPQL Query - findSmartAgentByRemark
	 *
	 */
	@Transactional
	public Set<SmartAgent> findSmartAgentByRemark(String remark) throws DataAccessException {

		return findSmartAgentByRemark(remark, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentByRemark
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgent> findSmartAgentByRemark(String remark, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentByRemark", startResult, maxRows, remark);
		return new LinkedHashSet<SmartAgent>(query.getResultList());
	}

	/**
	 * JPQL Query - findSmartAgentByCurrIpContaining
	 *
	 */
	@Transactional
	public Set<SmartAgent> findSmartAgentByCurrIpContaining(String currIp) throws DataAccessException {

		return findSmartAgentByCurrIpContaining(currIp, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentByCurrIpContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgent> findSmartAgentByCurrIpContaining(String currIp, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentByCurrIpContaining", startResult, maxRows, currIp);
		return new LinkedHashSet<SmartAgent>(query.getResultList());
	}

	/**
	 * JPQL Query - findSmartAgentByLabId
	 *
	 */
	@Transactional
	public Set<SmartAgent> findSmartAgentByLabId(Integer labId) throws DataAccessException {

		return findSmartAgentByLabId(labId, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentByLabId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgent> findSmartAgentByLabId(Integer labId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentByLabId", startResult, maxRows, labId);
		return new LinkedHashSet<SmartAgent>(query.getResultList());
	}

	/**
	 * JPQL Query - findSmartAgentByDeviceNumberContaining
	 *
	 */
	@Transactional
	public Set<SmartAgent> findSmartAgentByDeviceNumberContaining(String deviceNumber) throws DataAccessException {

		return findSmartAgentByDeviceNumberContaining(deviceNumber, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentByDeviceNumberContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgent> findSmartAgentByDeviceNumberContaining(String deviceNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentByDeviceNumberContaining", startResult, maxRows, deviceNumber);
		return new LinkedHashSet<SmartAgent>(query.getResultList());
	}

	/**
	 * JPQL Query - findSmartAgentByCurrIp
	 *
	 */
	@Transactional
	public Set<SmartAgent> findSmartAgentByCurrIp(String currIp) throws DataAccessException {

		return findSmartAgentByCurrIp(currIp, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentByCurrIp
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgent> findSmartAgentByCurrIp(String currIp, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentByCurrIp", startResult, maxRows, currIp);
		return new LinkedHashSet<SmartAgent>(query.getResultList());
	}

	/**
	 * JPQL Query - findSmartAgentByDbhostContaining
	 *
	 */
	@Transactional
	public Set<SmartAgent> findSmartAgentByDbhostContaining(String dbhost) throws DataAccessException {

		return findSmartAgentByDbhostContaining(dbhost, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentByDbhostContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgent> findSmartAgentByDbhostContaining(String dbhost, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentByDbhostContaining", startResult, maxRows, dbhost);
		return new LinkedHashSet<SmartAgent>(query.getResultList());
	}

	/**
	 * JPQL Query - findSmartAgentByDbhost
	 *
	 */
	@Transactional
	public Set<SmartAgent> findSmartAgentByDbhost(String dbhost) throws DataAccessException {

		return findSmartAgentByDbhost(dbhost, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentByDbhost
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgent> findSmartAgentByDbhost(String dbhost, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentByDbhost", startResult, maxRows, dbhost);
		return new LinkedHashSet<SmartAgent>(query.getResultList());
	}

	/**
	 * JPQL Query - findSmartAgentByPrimaryKey
	 *
	 */
	@Transactional
	public SmartAgent findSmartAgentByPrimaryKey(String serialNo) throws DataAccessException {

		return findSmartAgentByPrimaryKey(serialNo, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentByPrimaryKey
	 *
	 */

	@Transactional
	public SmartAgent findSmartAgentByPrimaryKey(String serialNo, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSmartAgentByPrimaryKey", startResult, maxRows, serialNo);
			return (net.zjcclims.domain.SmartAgent) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findSmartAgentByDeviceNameContaining
	 *
	 */
	@Transactional
	public Set<SmartAgent> findSmartAgentByDeviceNameContaining(String deviceName) throws DataAccessException {

		return findSmartAgentByDeviceNameContaining(deviceName, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentByDeviceNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgent> findSmartAgentByDeviceNameContaining(String deviceName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentByDeviceNameContaining", startResult, maxRows, deviceName);
		return new LinkedHashSet<SmartAgent>(query.getResultList());
	}

	/**
	 * JPQL Query - findSmartAgentByRemarkContaining
	 *
	 */
	@Transactional
	public Set<SmartAgent> findSmartAgentByRemarkContaining(String remark) throws DataAccessException {

		return findSmartAgentByRemarkContaining(remark, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentByRemarkContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgent> findSmartAgentByRemarkContaining(String remark, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentByRemarkContaining", startResult, maxRows, remark);
		return new LinkedHashSet<SmartAgent>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllSmartAgents
	 *
	 */
	@Transactional
	public Set<SmartAgent> findAllSmartAgents() throws DataAccessException {

		return findAllSmartAgents(-1, -1);
	}

	/**
	 * JPQL Query - findAllSmartAgents
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgent> findAllSmartAgents(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllSmartAgents", startResult, maxRows);
		return new LinkedHashSet<SmartAgent>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(SmartAgent entity) {
		return true;
	}
}
