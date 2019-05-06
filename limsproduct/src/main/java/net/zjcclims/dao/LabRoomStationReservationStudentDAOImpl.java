package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.LabRoomStationReservationStudent;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage LabRoomStationReservationStudent entities.
 * 
 */
@Repository("LabRoomStationReservationStudentDAO")
@Transactional
public class LabRoomStationReservationStudentDAOImpl extends AbstractJpaDao<LabRoomStationReservationStudent>
		implements LabRoomStationReservationStudentDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabRoomStationReservationStudent.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabRoomStationReservationStudentDAOImpl
	 *
	 */
	public LabRoomStationReservationStudentDAOImpl() {
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
	 * JPQL Query - findLabRoomStationReservationStudentByCnameContaining
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservationStudent> findLabRoomStationReservationStudentByCnameContaining(String cname) throws DataAccessException {

		return findLabRoomStationReservationStudentByCnameContaining(cname, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationStudentByCnameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservationStudent> findLabRoomStationReservationStudentByCnameContaining(String cname, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationStudentByCnameContaining", startResult, maxRows, cname);
		return new LinkedHashSet<LabRoomStationReservationStudent>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomStationReservationStudentById
	 *
	 */
	@Transactional
	public LabRoomStationReservationStudent findLabRoomStationReservationStudentById(Integer id) throws DataAccessException {

		return findLabRoomStationReservationStudentById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationStudentById
	 *
	 */

	@Transactional
	public LabRoomStationReservationStudent findLabRoomStationReservationStudentById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomStationReservationStudentById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomStationReservationStudent) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabRoomStationReservationStudentByUsernameContaining
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservationStudent> findLabRoomStationReservationStudentByUsernameContaining(String username) throws DataAccessException {

		return findLabRoomStationReservationStudentByUsernameContaining(username, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationStudentByUsernameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservationStudent> findLabRoomStationReservationStudentByUsernameContaining(String username, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationStudentByUsernameContaining", startResult, maxRows, username);
		return new LinkedHashSet<LabRoomStationReservationStudent>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabRoomStationReservationStudents
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservationStudent> findAllLabRoomStationReservationStudents() throws DataAccessException {

		return findAllLabRoomStationReservationStudents(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabRoomStationReservationStudents
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservationStudent> findAllLabRoomStationReservationStudents(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabRoomStationReservationStudents", startResult, maxRows);
		return new LinkedHashSet<LabRoomStationReservationStudent>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomStationReservationStudentByPrimaryKey
	 *
	 */
	@Transactional
	public LabRoomStationReservationStudent findLabRoomStationReservationStudentByPrimaryKey(Integer id) throws DataAccessException {

		return findLabRoomStationReservationStudentByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationStudentByPrimaryKey
	 *
	 */

	@Transactional
	public LabRoomStationReservationStudent findLabRoomStationReservationStudentByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomStationReservationStudentByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomStationReservationStudent) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabRoomStationReservationStudentByUsername
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservationStudent> findLabRoomStationReservationStudentByUsername(String username) throws DataAccessException {

		return findLabRoomStationReservationStudentByUsername(username, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationStudentByUsername
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservationStudent> findLabRoomStationReservationStudentByUsername(String username, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationStudentByUsername", startResult, maxRows, username);
		return new LinkedHashSet<LabRoomStationReservationStudent>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomStationReservationStudentByCname
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservationStudent> findLabRoomStationReservationStudentByCname(String cname) throws DataAccessException {

		return findLabRoomStationReservationStudentByCname(cname, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationStudentByCname
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservationStudent> findLabRoomStationReservationStudentByCname(String cname, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationStudentByCname", startResult, maxRows, cname);
		return new LinkedHashSet<LabRoomStationReservationStudent>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(LabRoomStationReservationStudent entity) {
		return true;
	}
}
