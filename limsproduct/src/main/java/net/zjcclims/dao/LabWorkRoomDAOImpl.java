package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.LabWorkRoom;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage LabWorkRoom entities.
 * 
 */
@Repository("LabWorkRoomDAO")
@Transactional
public class LabWorkRoomDAOImpl extends AbstractJpaDao<LabWorkRoom> implements
		LabWorkRoomDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabWorkRoom.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabWorkRoomDAOImpl
	 *
	 */
	public LabWorkRoomDAOImpl() {
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
	 * JPQL Query - findLabWorkRoomByWorkRoomNumber
	 *
	 */
	@Transactional
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomNumber(String workRoomNumber) throws DataAccessException {

		return findLabWorkRoomByWorkRoomNumber(workRoomNumber, -1, -1);
	}

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomNumber
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomNumber(String workRoomNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabWorkRoomByWorkRoomNumber", startResult, maxRows, workRoomNumber);
		return new LinkedHashSet<LabWorkRoom>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomNumberContaining
	 *
	 */
	@Transactional
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomNumberContaining(String workRoomNumber) throws DataAccessException {

		return findLabWorkRoomByWorkRoomNumberContaining(workRoomNumber, -1, -1);
	}

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomNumberContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomNumberContaining(String workRoomNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabWorkRoomByWorkRoomNumberContaining", startResult, maxRows, workRoomNumber);
		return new LinkedHashSet<LabWorkRoom>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomNameContaining
	 *
	 */
	@Transactional
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomNameContaining(String workRoomName) throws DataAccessException {

		return findLabWorkRoomByWorkRoomNameContaining(workRoomName, -1, -1);
	}

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomNameContaining(String workRoomName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabWorkRoomByWorkRoomNameContaining", startResult, maxRows, workRoomName);
		return new LinkedHashSet<LabWorkRoom>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomName
	 *
	 */
	@Transactional
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomName(String workRoomName) throws DataAccessException {

		return findLabWorkRoomByWorkRoomName(workRoomName, -1, -1);
	}

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomName(String workRoomName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabWorkRoomByWorkRoomName", startResult, maxRows, workRoomName);
		return new LinkedHashSet<LabWorkRoom>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabWorkRooms
	 *
	 */
	@Transactional
	public Set<LabWorkRoom> findAllLabWorkRooms() throws DataAccessException {

		return findAllLabWorkRooms(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabWorkRooms
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabWorkRoom> findAllLabWorkRooms(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabWorkRooms", startResult, maxRows);
		return new LinkedHashSet<LabWorkRoom>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomAddress
	 *
	 */
	@Transactional
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomAddress(String workRoomAddress) throws DataAccessException {

		return findLabWorkRoomByWorkRoomAddress(workRoomAddress, -1, -1);
	}

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomAddress
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomAddress(String workRoomAddress, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabWorkRoomByWorkRoomAddress", startResult, maxRows, workRoomAddress);
		return new LinkedHashSet<LabWorkRoom>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabWorkRoomById
	 *
	 */
	@Transactional
	public LabWorkRoom findLabWorkRoomById(Integer id) throws DataAccessException {

		return findLabWorkRoomById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabWorkRoomById
	 *
	 */

	@Transactional
	public LabWorkRoom findLabWorkRoomById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabWorkRoomById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabWorkRoom) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomAddressContaining
	 *
	 */
	@Transactional
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomAddressContaining(String workRoomAddress) throws DataAccessException {

		return findLabWorkRoomByWorkRoomAddressContaining(workRoomAddress, -1, -1);
	}

	/**
	 * JPQL Query - findLabWorkRoomByWorkRoomAddressContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabWorkRoom> findLabWorkRoomByWorkRoomAddressContaining(String workRoomAddress, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabWorkRoomByWorkRoomAddressContaining", startResult, maxRows, workRoomAddress);
		return new LinkedHashSet<LabWorkRoom>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabWorkRoomByPrimaryKey
	 *
	 */
	@Transactional
	public LabWorkRoom findLabWorkRoomByPrimaryKey(Integer id) throws DataAccessException {

		return findLabWorkRoomByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabWorkRoomByPrimaryKey
	 *
	 */

	@Transactional
	public LabWorkRoom findLabWorkRoomByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabWorkRoomByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabWorkRoom) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(LabWorkRoom entity) {
		return true;
	}
}
