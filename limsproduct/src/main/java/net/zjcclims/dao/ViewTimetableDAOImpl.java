package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.ViewTimetable;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage ViewTimetable entities.
 * 
 */
@Repository("ViewTimetableDAO")
@Transactional
public class ViewTimetableDAOImpl extends AbstractJpaDao<ViewTimetable>
		implements ViewTimetableDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ViewTimetable.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ViewTimetableDAOImpl
	 *
	 */
	public ViewTimetableDAOImpl() {
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
	 * JPQL Query - findViewTimetableByEndTimeContaining
	 *
	 */
	@Transactional
	public Set<ViewTimetable> findViewTimetableByEndTimeContaining(String endTime) throws DataAccessException {

		return findViewTimetableByEndTimeContaining(endTime, -1, -1);
	}

	/**
	 * JPQL Query - findViewTimetableByEndTimeContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewTimetable> findViewTimetableByEndTimeContaining(String endTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewTimetableByEndTimeContaining", startResult, maxRows, endTime);
		return new LinkedHashSet<ViewTimetable>(query.getResultList());
	}

	/**
	 * JPQL Query - findViewTimetableByTimetableStyle
	 *
	 */
	@Transactional
	public Set<ViewTimetable> findViewTimetableByTimetableStyle(Integer timetableStyle) throws DataAccessException {

		return findViewTimetableByTimetableStyle(timetableStyle, -1, -1);
	}

	/**
	 * JPQL Query - findViewTimetableByTimetableStyle
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewTimetable> findViewTimetableByTimetableStyle(Integer timetableStyle, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewTimetableByTimetableStyle", startResult, maxRows, timetableStyle);
		return new LinkedHashSet<ViewTimetable>(query.getResultList());
	}

	/**
	 * JPQL Query - findViewTimetableByCourseNo
	 *
	 */
	@Transactional
	public Set<ViewTimetable> findViewTimetableByCourseNo(String courseNo) throws DataAccessException {

		return findViewTimetableByCourseNo(courseNo, -1, -1);
	}

	/**
	 * JPQL Query - findViewTimetableByCourseNo
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewTimetable> findViewTimetableByCourseNo(String courseNo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewTimetableByCourseNo", startResult, maxRows, courseNo);
		return new LinkedHashSet<ViewTimetable>(query.getResultList());
	}

	/**
	 * JPQL Query - findViewTimetableByUsername
	 *
	 */
	@Transactional
	public Set<ViewTimetable> findViewTimetableByUsername(String username) throws DataAccessException {

		return findViewTimetableByUsername(username, -1, -1);
	}

	/**
	 * JPQL Query - findViewTimetableByUsername
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewTimetable> findViewTimetableByUsername(String username, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewTimetableByUsername", startResult, maxRows, username);
		return new LinkedHashSet<ViewTimetable>(query.getResultList());
	}

	/**
	 * JPQL Query - findViewTimetableById
	 *
	 */
	@Transactional
	public ViewTimetable findViewTimetableById(Integer id) throws DataAccessException {

		return findViewTimetableById(id, -1, -1);
	}

	/**
	 * JPQL Query - findViewTimetableById
	 *
	 */

	@Transactional
	public ViewTimetable findViewTimetableById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findViewTimetableById", startResult, maxRows, id);
			return (net.zjcclims.domain.ViewTimetable) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findViewTimetableByPIdContaining
	 *
	 */
	@Transactional
	public Set<ViewTimetable> findViewTimetableByPIdContaining(String PId) throws DataAccessException {

		return findViewTimetableByPIdContaining(PId, -1, -1);
	}

	/**
	 * JPQL Query - findViewTimetableByPIdContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewTimetable> findViewTimetableByPIdContaining(String PId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewTimetableByPIdContaining", startResult, maxRows, PId);
		return new LinkedHashSet<ViewTimetable>(query.getResultList());
	}

	/**
	 * JPQL Query - findViewTimetableByEndTime
	 *
	 */
	@Transactional
	public Set<ViewTimetable> findViewTimetableByEndTime(String endTime) throws DataAccessException {

		return findViewTimetableByEndTime(endTime, -1, -1);
	}

	/**
	 * JPQL Query - findViewTimetableByEndTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewTimetable> findViewTimetableByEndTime(String endTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewTimetableByEndTime", startResult, maxRows, endTime);
		return new LinkedHashSet<ViewTimetable>(query.getResultList());
	}

	/**
	 * JPQL Query - findViewTimetableByPrimaryKey
	 *
	 */
	@Transactional
	public ViewTimetable findViewTimetableByPrimaryKey(Integer id) throws DataAccessException {

		return findViewTimetableByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findViewTimetableByPrimaryKey
	 *
	 */

	@Transactional
	public ViewTimetable findViewTimetableByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findViewTimetableByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.ViewTimetable) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findViewTimetableByStartTimeContaining
	 *
	 */
	@Transactional
	public Set<ViewTimetable> findViewTimetableByStartTimeContaining(String startTime) throws DataAccessException {

		return findViewTimetableByStartTimeContaining(startTime, -1, -1);
	}

	/**
	 * JPQL Query - findViewTimetableByStartTimeContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewTimetable> findViewTimetableByStartTimeContaining(String startTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewTimetableByStartTimeContaining", startResult, maxRows, startTime);
		return new LinkedHashSet<ViewTimetable>(query.getResultList());
	}

	/**
	 * JPQL Query - findViewTimetableByUsernameContaining
	 *
	 */
	@Transactional
	public Set<ViewTimetable> findViewTimetableByUsernameContaining(String username) throws DataAccessException {

		return findViewTimetableByUsernameContaining(username, -1, -1);
	}

	/**
	 * JPQL Query - findViewTimetableByUsernameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewTimetable> findViewTimetableByUsernameContaining(String username, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewTimetableByUsernameContaining", startResult, maxRows, username);
		return new LinkedHashSet<ViewTimetable>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllViewTimetables
	 *
	 */
	@Transactional
	public Set<ViewTimetable> findAllViewTimetables() throws DataAccessException {

		return findAllViewTimetables(-1, -1);
	}

	/**
	 * JPQL Query - findAllViewTimetables
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewTimetable> findAllViewTimetables(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllViewTimetables", startResult, maxRows);
		return new LinkedHashSet<ViewTimetable>(query.getResultList());
	}

	/**
	 * JPQL Query - findViewTimetableByCourseNoContaining
	 *
	 */
	@Transactional
	public Set<ViewTimetable> findViewTimetableByCourseNoContaining(String courseNo) throws DataAccessException {

		return findViewTimetableByCourseNoContaining(courseNo, -1, -1);
	}

	/**
	 * JPQL Query - findViewTimetableByCourseNoContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewTimetable> findViewTimetableByCourseNoContaining(String courseNo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewTimetableByCourseNoContaining", startResult, maxRows, courseNo);
		return new LinkedHashSet<ViewTimetable>(query.getResultList());
	}

	/**
	 * JPQL Query - findViewTimetableByPId
	 *
	 */
	@Transactional
	public Set<ViewTimetable> findViewTimetableByPId(String PId) throws DataAccessException {

		return findViewTimetableByPId(PId, -1, -1);
	}

	/**
	 * JPQL Query - findViewTimetableByPId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewTimetable> findViewTimetableByPId(String PId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewTimetableByPId", startResult, maxRows, PId);
		return new LinkedHashSet<ViewTimetable>(query.getResultList());
	}

	/**
	 * JPQL Query - findViewTimetableByStartTime
	 *
	 */
	@Transactional
	public Set<ViewTimetable> findViewTimetableByStartTime(String startTime) throws DataAccessException {

		return findViewTimetableByStartTime(startTime, -1, -1);
	}

	/**
	 * JPQL Query - findViewTimetableByStartTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewTimetable> findViewTimetableByStartTime(String startTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewTimetableByStartTime", startResult, maxRows, startTime);
		return new LinkedHashSet<ViewTimetable>(query.getResultList());
	}

	/**
	 * JPQL Query - findViewTimetableByLabId
	 *
	 */
	@Transactional
	public Set<ViewTimetable> findViewTimetableByLabId(Integer labId) throws DataAccessException {

		return findViewTimetableByLabId(labId, -1, -1);
	}

	/**
	 * JPQL Query - findViewTimetableByLabId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ViewTimetable> findViewTimetableByLabId(Integer labId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findViewTimetableByLabId", startResult, maxRows, labId);
		return new LinkedHashSet<ViewTimetable>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(ViewTimetable entity) {
		return true;
	}
}
