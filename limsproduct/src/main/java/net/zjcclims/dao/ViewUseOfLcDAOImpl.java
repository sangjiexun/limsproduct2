package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.ViewUseOfLc;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage ViewUseOfLc entities.
 * 
 */
@Repository("ViewUseOfLcDAO")
@Transactional
public class ViewUseOfLcDAOImpl extends AbstractJpaDao<ViewUseOfLc> implements
		ViewUseOfLcDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ViewUseOfLc.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ViewUseOfLcDAOImpl
	 *
	 */
	public ViewUseOfLcDAOImpl() {
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
	 * JPQL Query - findViewUseOfLcByLabRoomNameContaining
	 *
	 */
	@Transactional
	public Set<ViewUseOfLc> findViewUseOfLcByLabRoomNameContaining(String labRoomName) throws DataAccessException {

		return findViewUseOfLcByLabRoomNameContaining(labRoomName, -1, -1);
	}

	/**
	 * JPQL Query - findViewUseOfLcByLabRoomNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewUseOfLc> findViewUseOfLcByLabRoomNameContaining(String labRoomName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewUseOfLcByLabRoomNameContaining", startResult, maxRows, labRoomName);
		return new LinkedHashSet<ViewUseOfLc>(query.getResultList());
	}

	/**
	 * JPQL Query - findViewUseOfLcByUser
	 *
	 */
	@Transactional
	public Set<ViewUseOfLc> findViewUseOfLcByUser(String user) throws DataAccessException {

		return findViewUseOfLcByUser(user, -1, -1);
	}

	/**
	 * JPQL Query - findViewUseOfLcByUser
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewUseOfLc> findViewUseOfLcByUser(String user, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewUseOfLcByUser", startResult, maxRows, user);
		return new LinkedHashSet<ViewUseOfLc>(query.getResultList());
	}

	/**
	 * JPQL Query - findViewUseOfLcByLabRoomName
	 *
	 */
	@Transactional
	public Set<ViewUseOfLc> findViewUseOfLcByLabRoomName(String labRoomName) throws DataAccessException {

		return findViewUseOfLcByLabRoomName(labRoomName, -1, -1);
	}

	/**
	 * JPQL Query - findViewUseOfLcByLabRoomName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewUseOfLc> findViewUseOfLcByLabRoomName(String labRoomName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewUseOfLcByLabRoomName", startResult, maxRows, labRoomName);
		return new LinkedHashSet<ViewUseOfLc>(query.getResultList());
	}

	/**
	 * JPQL Query - findViewUseOfLcByMachinenameContaining
	 *
	 */
	@Transactional
	public Set<ViewUseOfLc> findViewUseOfLcByMachinenameContaining(String machinename) throws DataAccessException {

		return findViewUseOfLcByMachinenameContaining(machinename, -1, -1);
	}

	/**
	 * JPQL Query - findViewUseOfLcByMachinenameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewUseOfLc> findViewUseOfLcByMachinenameContaining(String machinename, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewUseOfLcByMachinenameContaining", startResult, maxRows, machinename);
		return new LinkedHashSet<ViewUseOfLc>(query.getResultList());
	}

	/**
	 * JPQL Query - findViewUseOfLcByBegintime
	 *
	 */
	@Transactional
	public Set<ViewUseOfLc> findViewUseOfLcByBegintime(java.util.Calendar begintime) throws DataAccessException {

		return findViewUseOfLcByBegintime(begintime, -1, -1);
	}

	/**
	 * JPQL Query - findViewUseOfLcByBegintime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewUseOfLc> findViewUseOfLcByBegintime(java.util.Calendar begintime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewUseOfLcByBegintime", startResult, maxRows, begintime);
		return new LinkedHashSet<ViewUseOfLc>(query.getResultList());
	}

	/**
	 * JPQL Query - findViewUseOfLcByEndtime
	 *
	 */
	@Transactional
	public Set<ViewUseOfLc> findViewUseOfLcByEndtime(java.util.Calendar endtime) throws DataAccessException {

		return findViewUseOfLcByEndtime(endtime, -1, -1);
	}

	/**
	 * JPQL Query - findViewUseOfLcByEndtime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewUseOfLc> findViewUseOfLcByEndtime(java.util.Calendar endtime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewUseOfLcByEndtime", startResult, maxRows, endtime);
		return new LinkedHashSet<ViewUseOfLc>(query.getResultList());
	}

	/**
	 * JPQL Query - findViewUseOfLcByPrimaryKey
	 *
	 */
	@Transactional
	public ViewUseOfLc findViewUseOfLcByPrimaryKey(Integer id) throws DataAccessException {

		return findViewUseOfLcByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findViewUseOfLcByPrimaryKey
	 *
	 */

	@Transactional
	public ViewUseOfLc findViewUseOfLcByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findViewUseOfLcByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.ViewUseOfLc) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findViewUseOfLcByUserContaining
	 *
	 */
	@Transactional
	public Set<ViewUseOfLc> findViewUseOfLcByUserContaining(String user) throws DataAccessException {

		return findViewUseOfLcByUserContaining(user, -1, -1);
	}

	/**
	 * JPQL Query - findViewUseOfLcByUserContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewUseOfLc> findViewUseOfLcByUserContaining(String user, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewUseOfLcByUserContaining", startResult, maxRows, user);
		return new LinkedHashSet<ViewUseOfLc>(query.getResultList());
	}

	/**
	 * JPQL Query - findViewUseOfLcById
	 *
	 */
	@Transactional
	public ViewUseOfLc findViewUseOfLcById(Integer id) throws DataAccessException {

		return findViewUseOfLcById(id, -1, -1);
	}

	/**
	 * JPQL Query - findViewUseOfLcById
	 *
	 */

	@Transactional
	public ViewUseOfLc findViewUseOfLcById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findViewUseOfLcById", startResult, maxRows, id);
			return (net.zjcclims.domain.ViewUseOfLc) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findViewUseOfLcByMachinename
	 *
	 */
	@Transactional
	public Set<ViewUseOfLc> findViewUseOfLcByMachinename(String machinename) throws DataAccessException {

		return findViewUseOfLcByMachinename(machinename, -1, -1);
	}

	/**
	 * JPQL Query - findViewUseOfLcByMachinename
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewUseOfLc> findViewUseOfLcByMachinename(String machinename, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewUseOfLcByMachinename", startResult, maxRows, machinename);
		return new LinkedHashSet<ViewUseOfLc>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllViewUseOfLcs
	 *
	 */
	@Transactional
	public Set<ViewUseOfLc> findAllViewUseOfLcs() throws DataAccessException {

		return findAllViewUseOfLcs(-1, -1);
	}

	/**
	 * JPQL Query - findAllViewUseOfLcs
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewUseOfLc> findAllViewUseOfLcs(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllViewUseOfLcs", startResult, maxRows);
		return new LinkedHashSet<ViewUseOfLc>(query.getResultList());
	}

	/**
	 * JPQL Query - findViewUseOfLcByDeviceNumber
	 *
	 */
	@Transactional
	public Set<ViewUseOfLc> findViewUseOfLcByDeviceNumber(Integer deviceNumber) throws DataAccessException {

		return findViewUseOfLcByDeviceNumber(deviceNumber, -1, -1);
	}

	/**
	 * JPQL Query - findViewUseOfLcByDeviceNumber
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewUseOfLc> findViewUseOfLcByDeviceNumber(Integer deviceNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewUseOfLcByDeviceNumber", startResult, maxRows, deviceNumber);
		return new LinkedHashSet<ViewUseOfLc>(query.getResultList());
	}

	/**
	 * JPQL Query - findViewUseOfLcByLabRoomId
	 *
	 */
	@Transactional
	public Set<ViewUseOfLc> findViewUseOfLcByLabRoomId(Integer labRoomId) throws DataAccessException {

		return findViewUseOfLcByLabRoomId(labRoomId, -1, -1);
	}

	/**
	 * JPQL Query - findViewUseOfLcByLabRoomId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewUseOfLc> findViewUseOfLcByLabRoomId(Integer labRoomId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewUseOfLcByLabRoomId", startResult, maxRows, labRoomId);
		return new LinkedHashSet<ViewUseOfLc>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(ViewUseOfLc entity) {
		return true;
	}
}
