package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.LabroomTimetableRegister;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage LabroomTimetableRegister entities.
 * 
 */
@Repository("LabroomTimetableRegisterDAO")
@Transactional
public class LabroomTimetableRegisterDAOImpl extends AbstractJpaDao<LabroomTimetableRegister>
		implements LabroomTimetableRegisterDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabroomTimetableRegister.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabroomTimetableRegisterDAOImpl
	 *
	 */
	public LabroomTimetableRegisterDAOImpl() {
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
	 * JPQL Query - findLabroomTimetableRegisterByTeacherContaining
	 *
	 */
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByTeacherContaining(String teacher) throws DataAccessException {

		return findLabroomTimetableRegisterByTeacherContaining(teacher, -1, -1);
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByTeacherContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByTeacherContaining(String teacher, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabroomTimetableRegisterByTeacherContaining", startResult, maxRows, teacher);
		return new LinkedHashSet<LabroomTimetableRegister>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByConfirmDate
	 *
	 */
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByConfirmDate(java.util.Calendar confirmDate) throws DataAccessException {

		return findLabroomTimetableRegisterByConfirmDate(confirmDate, -1, -1);
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByConfirmDate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByConfirmDate(java.util.Calendar confirmDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabroomTimetableRegisterByConfirmDate", startResult, maxRows, confirmDate);
		return new LinkedHashSet<LabroomTimetableRegister>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByClassNumber
	 *
	 */
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByClassNumber(String classNumber) throws DataAccessException {

		return findLabroomTimetableRegisterByClassNumber(classNumber, -1, -1);
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByClassNumber
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByClassNumber(String classNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabroomTimetableRegisterByClassNumber", startResult, maxRows, classNumber);
		return new LinkedHashSet<LabroomTimetableRegister>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByEndClass
	 *
	 */
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByEndClass(Integer endClass) throws DataAccessException {

		return findLabroomTimetableRegisterByEndClass(endClass, -1, -1);
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByEndClass
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByEndClass(Integer endClass, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabroomTimetableRegisterByEndClass", startResult, maxRows, endClass);
		return new LinkedHashSet<LabroomTimetableRegister>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByConfirmUserContaining
	 *
	 */
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByConfirmUserContaining(String confirmUser) throws DataAccessException {

		return findLabroomTimetableRegisterByConfirmUserContaining(confirmUser, -1, -1);
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByConfirmUserContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByConfirmUserContaining(String confirmUser, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabroomTimetableRegisterByConfirmUserContaining", startResult, maxRows, confirmUser);
		return new LinkedHashSet<LabroomTimetableRegister>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByStartClass
	 *
	 */
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByStartClass(Integer startClass) throws DataAccessException {

		return findLabroomTimetableRegisterByStartClass(startClass, -1, -1);
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByStartClass
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByStartClass(Integer startClass, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabroomTimetableRegisterByStartClass", startResult, maxRows, startClass);
		return new LinkedHashSet<LabroomTimetableRegister>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByWeekday
	 *
	 */
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByWeekday(Integer weekday) throws DataAccessException {

		return findLabroomTimetableRegisterByWeekday(weekday, -1, -1);
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByWeekday
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByWeekday(Integer weekday, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabroomTimetableRegisterByWeekday", startResult, maxRows, weekday);
		return new LinkedHashSet<LabroomTimetableRegister>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByStartWeek
	 *
	 */
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByStartWeek(Integer startWeek) throws DataAccessException {

		return findLabroomTimetableRegisterByStartWeek(startWeek, -1, -1);
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByStartWeek
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByStartWeek(Integer startWeek, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabroomTimetableRegisterByStartWeek", startResult, maxRows, startWeek);
		return new LinkedHashSet<LabroomTimetableRegister>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabroomTimetableRegisters
	 *
	 */
	@Transactional
	public Set<LabroomTimetableRegister> findAllLabroomTimetableRegisters() throws DataAccessException {

		return findAllLabroomTimetableRegisters(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabroomTimetableRegisters
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabroomTimetableRegister> findAllLabroomTimetableRegisters(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabroomTimetableRegisters", startResult, maxRows);
		return new LinkedHashSet<LabroomTimetableRegister>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByConfirmUser
	 *
	 */
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByConfirmUser(String confirmUser) throws DataAccessException {

		return findLabroomTimetableRegisterByConfirmUser(confirmUser, -1, -1);
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByConfirmUser
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByConfirmUser(String confirmUser, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabroomTimetableRegisterByConfirmUser", startResult, maxRows, confirmUser);
		return new LinkedHashSet<LabroomTimetableRegister>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByClassNumberContaining
	 *
	 */
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByClassNumberContaining(String classNumber) throws DataAccessException {

		return findLabroomTimetableRegisterByClassNumberContaining(classNumber, -1, -1);
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByClassNumberContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByClassNumberContaining(String classNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabroomTimetableRegisterByClassNumberContaining", startResult, maxRows, classNumber);
		return new LinkedHashSet<LabroomTimetableRegister>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByProgramNameContaining
	 *
	 */
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByProgramNameContaining(String programName) throws DataAccessException {

		return findLabroomTimetableRegisterByProgramNameContaining(programName, -1, -1);
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByProgramNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByProgramNameContaining(String programName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabroomTimetableRegisterByProgramNameContaining", startResult, maxRows, programName);
		return new LinkedHashSet<LabroomTimetableRegister>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByConfirmDateBefore
	 *
	 */
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByConfirmDateBefore(java.util.Calendar confirmDate) throws DataAccessException {

		return findLabroomTimetableRegisterByConfirmDateBefore(confirmDate, -1, -1);
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByConfirmDateBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByConfirmDateBefore(java.util.Calendar confirmDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabroomTimetableRegisterByConfirmDateBefore", startResult, maxRows, confirmDate);
		return new LinkedHashSet<LabroomTimetableRegister>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByPrimaryKey
	 *
	 */
	@Transactional
	public LabroomTimetableRegister findLabroomTimetableRegisterByPrimaryKey(Integer id) throws DataAccessException {

		return findLabroomTimetableRegisterByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByPrimaryKey
	 *
	 */

	@Transactional
	public LabroomTimetableRegister findLabroomTimetableRegisterByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabroomTimetableRegisterByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabroomTimetableRegister) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByConfirmDateAfter
	 *
	 */
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByConfirmDateAfter(java.util.Calendar confirmDate) throws DataAccessException {

		return findLabroomTimetableRegisterByConfirmDateAfter(confirmDate, -1, -1);
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByConfirmDateAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByConfirmDateAfter(java.util.Calendar confirmDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabroomTimetableRegisterByConfirmDateAfter", startResult, maxRows, confirmDate);
		return new LinkedHashSet<LabroomTimetableRegister>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterById
	 *
	 */
	@Transactional
	public LabroomTimetableRegister findLabroomTimetableRegisterById(Integer id) throws DataAccessException {

		return findLabroomTimetableRegisterById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterById
	 *
	 */

	@Transactional
	public LabroomTimetableRegister findLabroomTimetableRegisterById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabroomTimetableRegisterById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabroomTimetableRegister) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByTotalClass
	 *
	 */
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByTotalClass(Integer totalClass) throws DataAccessException {

		return findLabroomTimetableRegisterByTotalClass(totalClass, -1, -1);
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByTotalClass
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByTotalClass(Integer totalClass, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabroomTimetableRegisterByTotalClass", startResult, maxRows, totalClass);
		return new LinkedHashSet<LabroomTimetableRegister>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByTeacher
	 *
	 */
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByTeacher(String teacher) throws DataAccessException {

		return findLabroomTimetableRegisterByTeacher(teacher, -1, -1);
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByTeacher
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByTeacher(String teacher, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabroomTimetableRegisterByTeacher", startResult, maxRows, teacher);
		return new LinkedHashSet<LabroomTimetableRegister>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByEndWeek
	 *
	 */
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByEndWeek(Integer endWeek) throws DataAccessException {

		return findLabroomTimetableRegisterByEndWeek(endWeek, -1, -1);
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByEndWeek
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByEndWeek(Integer endWeek, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabroomTimetableRegisterByEndWeek", startResult, maxRows, endWeek);
		return new LinkedHashSet<LabroomTimetableRegister>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByTotalWeek
	 *
	 */
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByTotalWeek(Integer totalWeek) throws DataAccessException {

		return findLabroomTimetableRegisterByTotalWeek(totalWeek, -1, -1);
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByTotalWeek
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByTotalWeek(Integer totalWeek, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabroomTimetableRegisterByTotalWeek", startResult, maxRows, totalWeek);
		return new LinkedHashSet<LabroomTimetableRegister>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByProgramName
	 *
	 */
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByProgramName(String programName) throws DataAccessException {

		return findLabroomTimetableRegisterByProgramName(programName, -1, -1);
	}

	/**
	 * JPQL Query - findLabroomTimetableRegisterByProgramName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByProgramName(String programName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabroomTimetableRegisterByProgramName", startResult, maxRows, programName);
		return new LinkedHashSet<LabroomTimetableRegister>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(LabroomTimetableRegister entity) {
		return true;
	}
}
