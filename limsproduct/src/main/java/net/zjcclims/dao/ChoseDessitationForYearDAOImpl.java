package net.zjcclims.dao;

import net.zjcclims.domain.ChoseDessitationForYear;
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
 * DAO to manage ChoseDessitationForYear entities.
 * 
 */
@Repository("ChoseDessitationForYearDAO")
@Transactional
public class ChoseDessitationForYearDAOImpl extends AbstractJpaDao<ChoseDessitationForYear>
		implements ChoseDessitationForYearDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ChoseDessitationForYear.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ChoseDessitationForYearDAOImpl
	 *
	 */
	public ChoseDessitationForYearDAOImpl() {
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
	 * JPQL Query - findChoseDessitationForYearByDocumentId
	 *
	 */
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByDocumentId(Integer documentId) throws DataAccessException {

		return findChoseDessitationForYearByDocumentId(documentId, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByDocumentId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByDocumentId(Integer documentId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDessitationForYearByDocumentId", startResult, maxRows, documentId);
		return new LinkedHashSet<ChoseDessitationForYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByRequirementsContaining
	 *
	 */
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByRequirementsContaining(String requirements) throws DataAccessException {

		return findChoseDessitationForYearByRequirementsContaining(requirements, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByRequirementsContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByRequirementsContaining(String requirements, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDessitationForYearByRequirementsContaining", startResult, maxRows, requirements);
		return new LinkedHashSet<ChoseDessitationForYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByStudent
	 *
	 */
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByStudent(String student) throws DataAccessException {

		return findChoseDessitationForYearByStudent(student, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByStudent
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByStudent(String student, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDessitationForYearByStudent", startResult, maxRows, student);
		return new LinkedHashSet<ChoseDessitationForYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByTeacherContaining
	 *
	 */
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByTeacherContaining(String teacher) throws DataAccessException {

		return findChoseDessitationForYearByTeacherContaining(teacher, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByTeacherContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByTeacherContaining(String teacher, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDessitationForYearByTeacherContaining", startResult, maxRows, teacher);
		return new LinkedHashSet<ChoseDessitationForYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByTeacher
	 *
	 */
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByTeacher(String teacher) throws DataAccessException {

		return findChoseDessitationForYearByTeacher(teacher, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByTeacher
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByTeacher(String teacher, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDessitationForYearByTeacher", startResult, maxRows, teacher);
		return new LinkedHashSet<ChoseDessitationForYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByStudentContaining
	 *
	 */
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByStudentContaining(String student) throws DataAccessException {

		return findChoseDessitationForYearByStudentContaining(student, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByStudentContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByStudentContaining(String student, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDessitationForYearByStudentContaining", startResult, maxRows, student);
		return new LinkedHashSet<ChoseDessitationForYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByState
	 *
	 */
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByState(Integer state) throws DataAccessException {

		return findChoseDessitationForYearByState(state, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByState
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByState(Integer state, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDessitationForYearByState", startResult, maxRows, state);
		return new LinkedHashSet<ChoseDessitationForYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByPrimaryKey
	 *
	 */
	@Transactional
	public ChoseDessitationForYear findChoseDessitationForYearByPrimaryKey(Integer id) throws DataAccessException {

		return findChoseDessitationForYearByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByPrimaryKey
	 *
	 */

	@Transactional
	public ChoseDessitationForYear findChoseDessitationForYearByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findChoseDessitationForYearByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.ChoseDessitationForYear) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findChoseDessitationForYearById
	 *
	 */
	@Transactional
	public ChoseDessitationForYear findChoseDessitationForYearById(Integer id) throws DataAccessException {

		return findChoseDessitationForYearById(id, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDessitationForYearById
	 *
	 */

	@Transactional
	public ChoseDessitationForYear findChoseDessitationForYearById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findChoseDessitationForYearById", startResult, maxRows, id);
			return (net.zjcclims.domain.ChoseDessitationForYear) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByFinishTimeAfter
	 *
	 */
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByFinishTimeAfter(java.util.Calendar finishTime) throws DataAccessException {

		return findChoseDessitationForYearByFinishTimeAfter(finishTime, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByFinishTimeAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByFinishTimeAfter(java.util.Calendar finishTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDessitationForYearByFinishTimeAfter", startResult, maxRows, finishTime);
		return new LinkedHashSet<ChoseDessitationForYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByRequirements
	 *
	 */
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByRequirements(String requirements) throws DataAccessException {

		return findChoseDessitationForYearByRequirements(requirements, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByRequirements
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByRequirements(String requirements, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDessitationForYearByRequirements", startResult, maxRows, requirements);
		return new LinkedHashSet<ChoseDessitationForYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByFinishTimeBefore
	 *
	 */
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByFinishTimeBefore(java.util.Calendar finishTime) throws DataAccessException {

		return findChoseDessitationForYearByFinishTimeBefore(finishTime, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByFinishTimeBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByFinishTimeBefore(java.util.Calendar finishTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDessitationForYearByFinishTimeBefore", startResult, maxRows, finishTime);
		return new LinkedHashSet<ChoseDessitationForYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByStudentCname
	 *
	 */
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByStudentCname(String studentCname) throws DataAccessException {

		return findChoseDessitationForYearByStudentCname(studentCname, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByStudentCname
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByStudentCname(String studentCname, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDessitationForYearByStudentCname", startResult, maxRows, studentCname);
		return new LinkedHashSet<ChoseDessitationForYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByThemeContaining
	 *
	 */
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByThemeContaining(String theme) throws DataAccessException {

		return findChoseDessitationForYearByThemeContaining(theme, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByThemeContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByThemeContaining(String theme, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDessitationForYearByThemeContaining", startResult, maxRows, theme);
		return new LinkedHashSet<ChoseDessitationForYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllChoseDessitationForYears
	 *
	 */
	@Transactional
	public Set<ChoseDessitationForYear> findAllChoseDessitationForYears() throws DataAccessException {

		return findAllChoseDessitationForYears(-1, -1);
	}

	/**
	 * JPQL Query - findAllChoseDessitationForYears
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDessitationForYear> findAllChoseDessitationForYears(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllChoseDessitationForYears", startResult, maxRows);
		return new LinkedHashSet<ChoseDessitationForYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByFinishTime
	 *
	 */
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByFinishTime(java.util.Calendar finishTime) throws DataAccessException {

		return findChoseDessitationForYearByFinishTime(finishTime, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByFinishTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByFinishTime(java.util.Calendar finishTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDessitationForYearByFinishTime", startResult, maxRows, finishTime);
		return new LinkedHashSet<ChoseDessitationForYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByStudentCnameContaining
	 *
	 */
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByStudentCnameContaining(String studentCname) throws DataAccessException {

		return findChoseDessitationForYearByStudentCnameContaining(studentCname, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByStudentCnameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByStudentCnameContaining(String studentCname, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDessitationForYearByStudentCnameContaining", startResult, maxRows, studentCname);
		return new LinkedHashSet<ChoseDessitationForYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByTheme
	 *
	 */
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByTheme(String theme) throws DataAccessException {

		return findChoseDessitationForYearByTheme(theme, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByTheme
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByTheme(String theme, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDessitationForYearByTheme", startResult, maxRows, theme);
		return new LinkedHashSet<ChoseDessitationForYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByTheYear
	 *
	 */
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByTheYear(Integer theYear) throws DataAccessException {

		return findChoseDessitationForYearByTheYear(theYear, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDessitationForYearByTheYear
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDessitationForYear> findChoseDessitationForYearByTheYear(Integer theYear, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDessitationForYearByTheYear", startResult, maxRows, theYear);
		return new LinkedHashSet<ChoseDessitationForYear>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(ChoseDessitationForYear entity) {
		return true;
	}
}
