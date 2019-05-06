package net.zjcclims.dao;


import net.zjcclims.domain.TimetableAppointmentCycle;
import org.skyway.spring.util.dao.AbstractJpaDao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * DAO to manage TimetableAppointmentCycle entities.
 * 
 */
@Repository("TimetableAppointmentCycleDAO")
@Transactional
public class TimetableAppointmentCycleDAOImpl extends AbstractJpaDao<TimetableAppointmentCycle>
		implements TimetableAppointmentCycleDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { TimetableAppointmentCycle.class }));

	/**
	 * EntityManager injected by Spring for persistence unit gvsunlimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new TimetableAppointmentCycleDAOImpl
	 *
	 */
	public TimetableAppointmentCycleDAOImpl() {
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
	 * JPQL Query - findTimetableAppointmentCycleById
	 *
	 */
	@Transactional
	public TimetableAppointmentCycle findTimetableAppointmentCycleById(Integer id) throws DataAccessException {

		return findTimetableAppointmentCycleById(id, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentCycleById
	 *
	 */

	@Transactional
	public TimetableAppointmentCycle findTimetableAppointmentCycleById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findTimetableAppointmentCycleById", startResult, maxRows, id);
			return (TimetableAppointmentCycle) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllTimetableAppointmentCycles
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentCycle> findAllTimetableAppointmentCycles() throws DataAccessException {

		return findAllTimetableAppointmentCycles(-1, -1);
	}

	/**
	 * JPQL Query - findAllTimetableAppointmentCycles
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentCycle> findAllTimetableAppointmentCycles(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllTimetableAppointmentCycles", startResult, maxRows);
		return new LinkedHashSet<TimetableAppointmentCycle>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentCycleByPrimaryKey
	 *
	 */
	@Transactional
	public TimetableAppointmentCycle findTimetableAppointmentCycleByPrimaryKey(Integer id) throws DataAccessException {

		return findTimetableAppointmentCycleByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentCycleByPrimaryKey
	 *
	 */

	@Transactional
	public TimetableAppointmentCycle findTimetableAppointmentCycleByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findTimetableAppointmentCycleByPrimaryKey", startResult, maxRows, id);
			return (TimetableAppointmentCycle) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findTimetableAppointmentCycleByDetailNo
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentCycle> findTimetableAppointmentCycleByDetailNo(String detailNo) throws DataAccessException {

		return findTimetableAppointmentCycleByDetailNo(detailNo, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentCycleByDetailNo
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentCycle> findTimetableAppointmentCycleByDetailNo(String detailNo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentCycleByDetailNo", startResult, maxRows, detailNo);
		return new LinkedHashSet<TimetableAppointmentCycle>(query.getResultList());
	}

	/**
	 * JPQL Query - findTimetableAppointmentCycleByDetailNoContaining
	 *
	 */
	@Transactional
	public Set<TimetableAppointmentCycle> findTimetableAppointmentCycleByDetailNoContaining(String detailNo) throws DataAccessException {

		return findTimetableAppointmentCycleByDetailNoContaining(detailNo, -1, -1);
	}

	/**
	 * JPQL Query - findTimetableAppointmentCycleByDetailNoContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<TimetableAppointmentCycle> findTimetableAppointmentCycleByDetailNoContaining(String detailNo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findTimetableAppointmentCycleByDetailNoContaining", startResult, maxRows, detailNo);
		return new LinkedHashSet<TimetableAppointmentCycle>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(TimetableAppointmentCycle entity) {
		return true;
	}
}
