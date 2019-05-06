package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.PreTimetableAppointment;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage PreTimetableAppointment entities.
 * 
 */
@Repository("PreTimetableAppointmentDAO")
@Transactional
public class PreTimetableAppointmentDAOImpl extends AbstractJpaDao<PreTimetableAppointment>
		implements PreTimetableAppointmentDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { PreTimetableAppointment.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new PreTimetableAppointmentDAOImpl
	 *
	 */
	public PreTimetableAppointmentDAOImpl() {
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
	 * JPQL Query - findPreTimetableAppointmentByPrimaryKey
	 *
	 */
	@Transactional
	public PreTimetableAppointment findPreTimetableAppointmentByPrimaryKey(Integer id) throws DataAccessException {

		return findPreTimetableAppointmentByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findPreTimetableAppointmentByPrimaryKey
	 *
	 */

	@Transactional
	public PreTimetableAppointment findPreTimetableAppointmentByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findPreTimetableAppointmentByPrimaryKey", startResult, maxRows, id);
			return (PreTimetableAppointment) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findPreTimetableAppointmentByStuNumber
	 *
	 */
	@Transactional
	public Set<PreTimetableAppointment> findPreTimetableAppointmentByStuNumber(Integer stuNumber) throws DataAccessException {

		return findPreTimetableAppointmentByStuNumber(stuNumber, -1, -1);
	}

	/**
	 * JPQL Query - findPreTimetableAppointmentByStuNumber
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreTimetableAppointment> findPreTimetableAppointmentByStuNumber(Integer stuNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findPreTimetableAppointmentByStuNumber", startResult, maxRows, stuNumber);
		return new LinkedHashSet<PreTimetableAppointment>(query.getResultList());
	}

	/**
	 * JPQL Query - findPreTimetableAppointmentById
	 *
	 */
	@Transactional
	public PreTimetableAppointment findPreTimetableAppointmentById(Integer id) throws DataAccessException {

		return findPreTimetableAppointmentById(id, -1, -1);
	}

	/**
	 * JPQL Query - findPreTimetableAppointmentById
	 *
	 */

	@Transactional
	public PreTimetableAppointment findPreTimetableAppointmentById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findPreTimetableAppointmentById", startResult, maxRows, id);
			return (PreTimetableAppointment) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findPreTimetableAppointmentByState
	 *
	 */
	@Transactional
	public Set<PreTimetableAppointment> findPreTimetableAppointmentByState(Integer state) throws DataAccessException {

		return findPreTimetableAppointmentByState(state, -1, -1);
	}

	/**
	 * JPQL Query - findPreTimetableAppointmentByState
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreTimetableAppointment> findPreTimetableAppointmentByState(Integer state, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findPreTimetableAppointmentByState", startResult, maxRows, state);
		return new LinkedHashSet<PreTimetableAppointment>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllPreTimetableAppointments
	 *
	 */
	@Transactional
	public Set<PreTimetableAppointment> findAllPreTimetableAppointments() throws DataAccessException {

		return findAllPreTimetableAppointments(-1, -1);
	}

	/**
	 * JPQL Query - findAllPreTimetableAppointments
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreTimetableAppointment> findAllPreTimetableAppointments(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllPreTimetableAppointments", startResult, maxRows);
		return new LinkedHashSet<PreTimetableAppointment>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(PreTimetableAppointment entity) {
		return true;
	}
}
