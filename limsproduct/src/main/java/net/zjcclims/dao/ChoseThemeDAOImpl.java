package net.zjcclims.dao;

import net.zjcclims.domain.ChoseTheme;
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
 * DAO to manage ChoseTheme entities.
 * 
 */
@Repository("ChoseThemeDAO")
@Transactional
public class ChoseThemeDAOImpl extends AbstractJpaDao<ChoseTheme> implements
		ChoseThemeDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ChoseTheme.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ChoseThemeDAOImpl
	 *
	 */
	public ChoseThemeDAOImpl() {
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
	 * JPQL Query - findChoseThemeByTeacherNumber
	 *
	 */
	@Transactional
	public Set<ChoseTheme> findChoseThemeByTeacherNumber(Integer teacherNumber) throws DataAccessException {

		return findChoseThemeByTeacherNumber(teacherNumber, -1, -1);
	}

	/**
	 * JPQL Query - findChoseThemeByTeacherNumber
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseTheme> findChoseThemeByTeacherNumber(Integer teacherNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseThemeByTeacherNumber", startResult, maxRows, teacherNumber);
		return new LinkedHashSet<ChoseTheme>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseThemeByAdvanceDay
	 *
	 */
	@Transactional
	public Set<ChoseTheme> findChoseThemeByAdvanceDay(Integer advanceDay) throws DataAccessException {

		return findChoseThemeByAdvanceDay(advanceDay, -1, -1);
	}

	/**
	 * JPQL Query - findChoseThemeByAdvanceDay
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseTheme> findChoseThemeByAdvanceDay(Integer advanceDay, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseThemeByAdvanceDay", startResult, maxRows, advanceDay);
		return new LinkedHashSet<ChoseTheme>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseThemeByMaxStudent
	 *
	 */
	@Transactional
	public Set<ChoseTheme> findChoseThemeByMaxStudent(Integer maxStudent) throws DataAccessException {

		return findChoseThemeByMaxStudent(maxStudent, -1, -1);
	}

	/**
	 * JPQL Query - findChoseThemeByMaxStudent
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseTheme> findChoseThemeByMaxStudent(Integer maxStudent, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseThemeByMaxStudent", startResult, maxRows, maxStudent);
		return new LinkedHashSet<ChoseTheme>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseThemeByEndTime
	 *
	 */
	@Transactional
	public Set<ChoseTheme> findChoseThemeByEndTime(java.util.Calendar endTime) throws DataAccessException {

		return findChoseThemeByEndTime(endTime, -1, -1);
	}

	/**
	 * JPQL Query - findChoseThemeByEndTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseTheme> findChoseThemeByEndTime(java.util.Calendar endTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseThemeByEndTime", startResult, maxRows, endTime);
		return new LinkedHashSet<ChoseTheme>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllChoseThemes
	 *
	 */
	@Transactional
	public Set<ChoseTheme> findAllChoseThemes() throws DataAccessException {

		return findAllChoseThemes(-1, -1);
	}

	/**
	 * JPQL Query - findAllChoseThemes
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseTheme> findAllChoseThemes(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllChoseThemes", startResult, maxRows);
		return new LinkedHashSet<ChoseTheme>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseThemeByStudentNumber
	 *
	 */
	@Transactional
	public Set<ChoseTheme> findChoseThemeByStudentNumber(Integer studentNumber) throws DataAccessException {

		return findChoseThemeByStudentNumber(studentNumber, -1, -1);
	}

	/**
	 * JPQL Query - findChoseThemeByStudentNumber
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseTheme> findChoseThemeByStudentNumber(Integer studentNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseThemeByStudentNumber", startResult, maxRows, studentNumber);
		return new LinkedHashSet<ChoseTheme>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseThemeByTheYear
	 *
	 */
	@Transactional
	public Set<ChoseTheme> findChoseThemeByTheYear(Integer theYear) throws DataAccessException {

		return findChoseThemeByTheYear(theYear, -1, -1);
	}

	/**
	 * JPQL Query - findChoseThemeByTheYear
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseTheme> findChoseThemeByTheYear(Integer theYear, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseThemeByTheYear", startResult, maxRows, theYear);
		return new LinkedHashSet<ChoseTheme>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseThemeByBatchNumber
	 *
	 */
	@Transactional
	public Set<ChoseTheme> findChoseThemeByBatchNumber(Integer batchNumber) throws DataAccessException {

		return findChoseThemeByBatchNumber(batchNumber, -1, -1);
	}

	/**
	 * JPQL Query - findChoseThemeByBatchNumber
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseTheme> findChoseThemeByBatchNumber(Integer batchNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseThemeByBatchNumber", startResult, maxRows, batchNumber);
		return new LinkedHashSet<ChoseTheme>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseThemeByEndTimeBefore
	 *
	 */
	@Transactional
	public Set<ChoseTheme> findChoseThemeByEndTimeBefore(java.util.Calendar endTime) throws DataAccessException {

		return findChoseThemeByEndTimeBefore(endTime, -1, -1);
	}

	/**
	 * JPQL Query - findChoseThemeByEndTimeBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseTheme> findChoseThemeByEndTimeBefore(java.util.Calendar endTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseThemeByEndTimeBefore", startResult, maxRows, endTime);
		return new LinkedHashSet<ChoseTheme>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseThemeById
	 *
	 */
	@Transactional
	public ChoseTheme findChoseThemeById(Integer id) throws DataAccessException {

		return findChoseThemeById(id, -1, -1);
	}

	/**
	 * JPQL Query - findChoseThemeById
	 *
	 */

	@Transactional
	public ChoseTheme findChoseThemeById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findChoseThemeById", startResult, maxRows, id);
			return (net.zjcclims.domain.ChoseTheme) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findChoseThemeByStartTimeAfter
	 *
	 */
	@Transactional
	public Set<ChoseTheme> findChoseThemeByStartTimeAfter(java.util.Calendar startTime) throws DataAccessException {

		return findChoseThemeByStartTimeAfter(startTime, -1, -1);
	}

	/**
	 * JPQL Query - findChoseThemeByStartTimeAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseTheme> findChoseThemeByStartTimeAfter(java.util.Calendar startTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseThemeByStartTimeAfter", startResult, maxRows, startTime);
		return new LinkedHashSet<ChoseTheme>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseThemeByEndTimeAfter
	 *
	 */
	@Transactional
	public Set<ChoseTheme> findChoseThemeByEndTimeAfter(java.util.Calendar endTime) throws DataAccessException {

		return findChoseThemeByEndTimeAfter(endTime, -1, -1);
	}

	/**
	 * JPQL Query - findChoseThemeByEndTimeAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseTheme> findChoseThemeByEndTimeAfter(java.util.Calendar endTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseThemeByEndTimeAfter", startResult, maxRows, endTime);
		return new LinkedHashSet<ChoseTheme>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseThemeByState
	 *
	 */
	@Transactional
	public Set<ChoseTheme> findChoseThemeByState(Integer state) throws DataAccessException {

		return findChoseThemeByState(state, -1, -1);
	}

	/**
	 * JPQL Query - findChoseThemeByState
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseTheme> findChoseThemeByState(Integer state, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseThemeByState", startResult, maxRows, state);
		return new LinkedHashSet<ChoseTheme>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseThemeByPrimaryKey
	 *
	 */
	@Transactional
	public ChoseTheme findChoseThemeByPrimaryKey(Integer id) throws DataAccessException {

		return findChoseThemeByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findChoseThemeByPrimaryKey
	 *
	 */

	@Transactional
	public ChoseTheme findChoseThemeByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findChoseThemeByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.ChoseTheme) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findChoseThemeByStartTime
	 *
	 */
	@Transactional
	public Set<ChoseTheme> findChoseThemeByStartTime(java.util.Calendar startTime) throws DataAccessException {

		return findChoseThemeByStartTime(startTime, -1, -1);
	}

	/**
	 * JPQL Query - findChoseThemeByStartTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseTheme> findChoseThemeByStartTime(java.util.Calendar startTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseThemeByStartTime", startResult, maxRows, startTime);
		return new LinkedHashSet<ChoseTheme>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseThemeByStartTimeBefore
	 *
	 */
	@Transactional
	public Set<ChoseTheme> findChoseThemeByStartTimeBefore(java.util.Calendar startTime) throws DataAccessException {

		return findChoseThemeByStartTimeBefore(startTime, -1, -1);
	}

	/**
	 * JPQL Query - findChoseThemeByStartTimeBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseTheme> findChoseThemeByStartTimeBefore(java.util.Calendar startTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseThemeByStartTimeBefore", startResult, maxRows, startTime);
		return new LinkedHashSet<ChoseTheme>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(ChoseTheme entity) {
		return true;
	}
}
