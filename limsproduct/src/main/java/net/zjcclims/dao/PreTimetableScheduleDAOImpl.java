package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.PreTimetableSchedule;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage PreTimetableSchedule entities.
 * 
 */
@Repository("PreTimetableScheduleDAO")
@Transactional
public class PreTimetableScheduleDAOImpl extends AbstractJpaDao<PreTimetableSchedule>
		implements PreTimetableScheduleDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { PreTimetableSchedule.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new PreTimetableScheduleDAOImpl
	 *
	 */
	public PreTimetableScheduleDAOImpl() {
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
	 * JPQL Query - findPreTimetableScheduleByEndClass
	 *
	 */
	@Transactional
	public Set<PreTimetableSchedule> findPreTimetableScheduleByEndClass(Integer endClass) throws DataAccessException {

		return findPreTimetableScheduleByEndClass(endClass, -1, -1);
	}

	/**
	 * JPQL Query - findPreTimetableScheduleByEndClass
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreTimetableSchedule> findPreTimetableScheduleByEndClass(Integer endClass, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findPreTimetableScheduleByEndClass", startResult, maxRows, endClass);
		return new LinkedHashSet<PreTimetableSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findPreTimetableScheduleByStartClass
	 *
	 */
	@Transactional
	public Set<PreTimetableSchedule> findPreTimetableScheduleByStartClass(Integer startClass) throws DataAccessException {

		return findPreTimetableScheduleByStartClass(startClass, -1, -1);
	}

	/**
	 * JPQL Query - findPreTimetableScheduleByStartClass
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreTimetableSchedule> findPreTimetableScheduleByStartClass(Integer startClass, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findPreTimetableScheduleByStartClass", startResult, maxRows, startClass);
		return new LinkedHashSet<PreTimetableSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findPreTimetableScheduleByEndWeek
	 *
	 */
	@Transactional
	public Set<PreTimetableSchedule> findPreTimetableScheduleByEndWeek(Integer endWeek) throws DataAccessException {

		return findPreTimetableScheduleByEndWeek(endWeek, -1, -1);
	}

	/**
	 * JPQL Query - findPreTimetableScheduleByEndWeek
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreTimetableSchedule> findPreTimetableScheduleByEndWeek(Integer endWeek, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findPreTimetableScheduleByEndWeek", startResult, maxRows, endWeek);
		return new LinkedHashSet<PreTimetableSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findPreTimetableScheduleByStartWeek
	 *
	 */
	@Transactional
	public Set<PreTimetableSchedule> findPreTimetableScheduleByStartWeek(Integer startWeek) throws DataAccessException {

		return findPreTimetableScheduleByStartWeek(startWeek, -1, -1);
	}

	/**
	 * JPQL Query - findPreTimetableScheduleByStartWeek
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreTimetableSchedule> findPreTimetableScheduleByStartWeek(Integer startWeek, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findPreTimetableScheduleByStartWeek", startResult, maxRows, startWeek);
		return new LinkedHashSet<PreTimetableSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findPreTimetableScheduleByPrimaryKey
	 *
	 */
	@Transactional
	public PreTimetableSchedule findPreTimetableScheduleByPrimaryKey(Integer id) throws DataAccessException {

		return findPreTimetableScheduleByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findPreTimetableScheduleByPrimaryKey
	 *
	 */

	@Transactional
	public PreTimetableSchedule findPreTimetableScheduleByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findPreTimetableScheduleByPrimaryKey", startResult, maxRows, id);
			return (PreTimetableSchedule) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findPreTimetableScheduleByEndWday
	 *
	 */
	@Transactional
	public Set<PreTimetableSchedule> findPreTimetableScheduleByEndWday(Integer endWday) throws DataAccessException {

		return findPreTimetableScheduleByEndWday(endWday, -1, -1);
	}

	/**
	 * JPQL Query - findPreTimetableScheduleByEndWday
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreTimetableSchedule> findPreTimetableScheduleByEndWday(Integer endWday, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findPreTimetableScheduleByEndWday", startResult, maxRows, endWday);
		return new LinkedHashSet<PreTimetableSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllPreTimetableSchedules
	 *
	 */
	@Transactional
	public Set<PreTimetableSchedule> findAllPreTimetableSchedules() throws DataAccessException {

		return findAllPreTimetableSchedules(-1, -1);
	}

	/**
	 * JPQL Query - findAllPreTimetableSchedules
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreTimetableSchedule> findAllPreTimetableSchedules(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllPreTimetableSchedules", startResult, maxRows);
		return new LinkedHashSet<PreTimetableSchedule>(query.getResultList());
	}

	/**
	 * JPQL Query - findPreTimetableScheduleById
	 *
	 */
	@Transactional
	public PreTimetableSchedule findPreTimetableScheduleById(Integer id) throws DataAccessException {

		return findPreTimetableScheduleById(id, -1, -1);
	}

	/**
	 * JPQL Query - findPreTimetableScheduleById
	 *
	 */

	@Transactional
	public PreTimetableSchedule findPreTimetableScheduleById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findPreTimetableScheduleById", startResult, maxRows, id);
			return (PreTimetableSchedule) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findPreTimetableScheduleByStartWday
	 *
	 */
	@Transactional
	public Set<PreTimetableSchedule> findPreTimetableScheduleByStartWday(Integer startWday) throws DataAccessException {

		return findPreTimetableScheduleByStartWday(startWday, -1, -1);
	}

	/**
	 * JPQL Query - findPreTimetableScheduleByStartWday
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreTimetableSchedule> findPreTimetableScheduleByStartWday(Integer startWday, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findPreTimetableScheduleByStartWday", startResult, maxRows, startWday);
		return new LinkedHashSet<PreTimetableSchedule>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(PreTimetableSchedule entity) {
		return true;
	}
}
