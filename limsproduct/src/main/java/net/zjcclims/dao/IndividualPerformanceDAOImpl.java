package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.IndividualPerformance;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage IndividualPerformance entities.
 * 
 */
@Repository("IndividualPerformanceDAO")
@Transactional
public class IndividualPerformanceDAOImpl extends AbstractJpaDao<IndividualPerformance>
		implements IndividualPerformanceDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { IndividualPerformance.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new IndividualPerformanceDAOImpl
	 *
	 */
	public IndividualPerformanceDAOImpl() {
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
	 * JPQL Query - findIndividualPerformanceByCreaterContaining
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByCreaterContaining(String creater) throws DataAccessException {

		return findIndividualPerformanceByCreaterContaining(creater, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByCreaterContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByCreaterContaining(String creater, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByCreaterContaining", startResult, maxRows, creater);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByThemeAddition
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByThemeAddition(String themeAddition) throws DataAccessException {

		return findIndividualPerformanceByThemeAddition(themeAddition, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByThemeAddition
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByThemeAddition(String themeAddition, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByThemeAddition", startResult, maxRows, themeAddition);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByEndTime
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByEndTime(java.util.Calendar endTime) throws DataAccessException {

		return findIndividualPerformanceByEndTime(endTime, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByEndTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByEndTime(java.util.Calendar endTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByEndTime", startResult, maxRows, endTime);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceBySchoolTermContaining
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceBySchoolTermContaining(String schoolTerm) throws DataAccessException {

		return findIndividualPerformanceBySchoolTermContaining(schoolTerm, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceBySchoolTermContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceBySchoolTermContaining(String schoolTerm, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceBySchoolTermContaining", startResult, maxRows, schoolTerm);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByYearCodeContaining
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByYearCodeContaining(String yearCode) throws DataAccessException {

		return findIndividualPerformanceByYearCodeContaining(yearCode, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByYearCodeContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByYearCodeContaining(String yearCode, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByYearCodeContaining", startResult, maxRows, yearCode);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByCreateDate
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByCreateDate(java.util.Calendar createDate) throws DataAccessException {

		return findIndividualPerformanceByCreateDate(createDate, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByCreateDate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByCreateDate(java.util.Calendar createDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByCreateDate", startResult, maxRows, createDate);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceBySchoolAcademy
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceBySchoolAcademy(String schoolAcademy) throws DataAccessException {

		return findIndividualPerformanceBySchoolAcademy(schoolAcademy, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceBySchoolAcademy
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceBySchoolAcademy(String schoolAcademy, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceBySchoolAcademy", startResult, maxRows, schoolAcademy);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByPublicDateAfter
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByPublicDateAfter(java.util.Calendar publicDate) throws DataAccessException {

		return findIndividualPerformanceByPublicDateAfter(publicDate, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByPublicDateAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByPublicDateAfter(java.util.Calendar publicDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByPublicDateAfter", startResult, maxRows, publicDate);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByOtherTeacher
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByOtherTeacher(String otherTeacher) throws DataAccessException {

		return findIndividualPerformanceByOtherTeacher(otherTeacher, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByOtherTeacher
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByOtherTeacher(String otherTeacher, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByOtherTeacher", startResult, maxRows, otherTeacher);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByPositional
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByPositional(String positional) throws DataAccessException {

		return findIndividualPerformanceByPositional(positional, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByPositional
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByPositional(String positional, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByPositional", startResult, maxRows, positional);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceBySchoolAcademyContaining
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceBySchoolAcademyContaining(String schoolAcademy) throws DataAccessException {

		return findIndividualPerformanceBySchoolAcademyContaining(schoolAcademy, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceBySchoolAcademyContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceBySchoolAcademyContaining(String schoolAcademy, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceBySchoolAcademyContaining", startResult, maxRows, schoolAcademy);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByAdditionNum
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByAdditionNum(Integer additionNum) throws DataAccessException {

		return findIndividualPerformanceByAdditionNum(additionNum, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByAdditionNum
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByAdditionNum(Integer additionNum, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByAdditionNum", startResult, maxRows, additionNum);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByResTeacherContaining
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByResTeacherContaining(String resTeacher) throws DataAccessException {

		return findIndividualPerformanceByResTeacherContaining(resTeacher, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByResTeacherContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByResTeacherContaining(String resTeacher, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByResTeacherContaining", startResult, maxRows, resTeacher);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByAidAmount
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByAidAmount(java.math.BigDecimal aidAmount) throws DataAccessException {

		return findIndividualPerformanceByAidAmount(aidAmount, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByAidAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByAidAmount(java.math.BigDecimal aidAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByAidAmount", startResult, maxRows, aidAmount);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByPublicDate
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByPublicDate(java.util.Calendar publicDate) throws DataAccessException {

		return findIndividualPerformanceByPublicDate(publicDate, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByPublicDate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByPublicDate(java.util.Calendar publicDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByPublicDate", startResult, maxRows, publicDate);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByPrimaryKey
	 *
	 */
	@Transactional
	public IndividualPerformance findIndividualPerformanceByPrimaryKey(Integer id) throws DataAccessException {

		return findIndividualPerformanceByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByPrimaryKey
	 *
	 */

	@Transactional
	public IndividualPerformance findIndividualPerformanceByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findIndividualPerformanceByPrimaryKey", startResult, maxRows, id);
			return (IndividualPerformance) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findIndividualPerformanceByAwardsDepartment
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByAwardsDepartment(String awardsDepartment) throws DataAccessException {

		return findIndividualPerformanceByAwardsDepartment(awardsDepartment, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByAwardsDepartment
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByAwardsDepartment(String awardsDepartment, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByAwardsDepartment", startResult, maxRows, awardsDepartment);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByStartTime
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByStartTime(java.util.Calendar startTime) throws DataAccessException {

		return findIndividualPerformanceByStartTime(startTime, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByStartTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByStartTime(java.util.Calendar startTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByStartTime", startResult, maxRows, startTime);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByClassHour
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByClassHour(Integer classHour) throws DataAccessException {

		return findIndividualPerformanceByClassHour(classHour, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByClassHour
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByClassHour(Integer classHour, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByClassHour", startResult, maxRows, classHour);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByFileUrlContaining
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByFileUrlContaining(String fileUrl) throws DataAccessException {

		return findIndividualPerformanceByFileUrlContaining(fileUrl, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByFileUrlContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByFileUrlContaining(String fileUrl, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByFileUrlContaining", startResult, maxRows, fileUrl);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByBookConcern
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByBookConcern(String bookConcern) throws DataAccessException {

		return findIndividualPerformanceByBookConcern(bookConcern, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByBookConcern
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByBookConcern(String bookConcern, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByBookConcern", startResult, maxRows, bookConcern);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllIndividualPerformances
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findAllIndividualPerformances() throws DataAccessException {

		return findAllIndividualPerformances(-1, -1);
	}

	/**
	 * JPQL Query - findAllIndividualPerformances
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findAllIndividualPerformances(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllIndividualPerformances", startResult, maxRows);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByPositionalContaining
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByPositionalContaining(String positional) throws DataAccessException {

		return findIndividualPerformanceByPositionalContaining(positional, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByPositionalContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByPositionalContaining(String positional, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByPositionalContaining", startResult, maxRows, positional);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByStatus
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByStatus(Integer status) throws DataAccessException {

		return findIndividualPerformanceByStatus(status, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByStatus
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByStatus(Integer status, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByStatus", startResult, maxRows, status);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByAuditStatus
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByAuditStatus(Integer auditStatus) throws DataAccessException {

		return findIndividualPerformanceByAuditStatus(auditStatus, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByAuditStatus
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByAuditStatus(Integer auditStatus, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByAuditStatus", startResult, maxRows, auditStatus);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByThemeAdditionContaining
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByThemeAdditionContaining(String themeAddition) throws DataAccessException {

		return findIndividualPerformanceByThemeAdditionContaining(themeAddition, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByThemeAdditionContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByThemeAdditionContaining(String themeAddition, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByThemeAdditionContaining", startResult, maxRows, themeAddition);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByOtherTeacherContaining
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByOtherTeacherContaining(String otherTeacher) throws DataAccessException {

		return findIndividualPerformanceByOtherTeacherContaining(otherTeacher, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByOtherTeacherContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByOtherTeacherContaining(String otherTeacher, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByOtherTeacherContaining", startResult, maxRows, otherTeacher);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByAwardsDepartmentContaining
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByAwardsDepartmentContaining(String awardsDepartment) throws DataAccessException {

		return findIndividualPerformanceByAwardsDepartmentContaining(awardsDepartment, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByAwardsDepartmentContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByAwardsDepartmentContaining(String awardsDepartment, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByAwardsDepartmentContaining", startResult, maxRows, awardsDepartment);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByPublicDateBefore
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByPublicDateBefore(java.util.Calendar publicDate) throws DataAccessException {

		return findIndividualPerformanceByPublicDateBefore(publicDate, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByPublicDateBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByPublicDateBefore(java.util.Calendar publicDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByPublicDateBefore", startResult, maxRows, publicDate);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByBookConcernContaining
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByBookConcernContaining(String bookConcern) throws DataAccessException {

		return findIndividualPerformanceByBookConcernContaining(bookConcern, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByBookConcernContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByBookConcernContaining(String bookConcern, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByBookConcernContaining", startResult, maxRows, bookConcern);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByYearCode
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByYearCode(String yearCode) throws DataAccessException {

		return findIndividualPerformanceByYearCode(yearCode, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByYearCode
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByYearCode(String yearCode, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByYearCode", startResult, maxRows, yearCode);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByCredit
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByCredit(java.math.BigDecimal credit) throws DataAccessException {

		return findIndividualPerformanceByCredit(credit, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByCredit
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByCredit(java.math.BigDecimal credit, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByCredit", startResult, maxRows, credit);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByCreater
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByCreater(String creater) throws DataAccessException {

		return findIndividualPerformanceByCreater(creater, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByCreater
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByCreater(String creater, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByCreater", startResult, maxRows, creater);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceById
	 *
	 */
	@Transactional
	public IndividualPerformance findIndividualPerformanceById(Integer id) throws DataAccessException {

		return findIndividualPerformanceById(id, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceById
	 *
	 */

	@Transactional
	public IndividualPerformance findIndividualPerformanceById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findIndividualPerformanceById", startResult, maxRows, id);
			return (IndividualPerformance) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findIndividualPerformanceByType
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByType(Integer type) throws DataAccessException {

		return findIndividualPerformanceByType(type, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByType
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByType(Integer type, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByType", startResult, maxRows, type);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceBySchoolTerm
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceBySchoolTerm(String schoolTerm) throws DataAccessException {

		return findIndividualPerformanceBySchoolTerm(schoolTerm, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceBySchoolTerm
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceBySchoolTerm(String schoolTerm, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceBySchoolTerm", startResult, maxRows, schoolTerm);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByMemo
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByMemo(String memo) throws DataAccessException {

		return findIndividualPerformanceByMemo(memo, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByMemo
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByMemo(String memo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByMemo", startResult, maxRows, memo);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByResTeacher
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByResTeacher(String resTeacher) throws DataAccessException {

		return findIndividualPerformanceByResTeacher(resTeacher, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByResTeacher
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByResTeacher(String resTeacher, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByResTeacher", startResult, maxRows, resTeacher);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByPublicationTypeContaining
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByPublicationTypeContaining(String publicationType) throws DataAccessException {

		return findIndividualPerformanceByPublicationTypeContaining(publicationType, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByPublicationTypeContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByPublicationTypeContaining(String publicationType, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByPublicationTypeContaining", startResult, maxRows, publicationType);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByPublicationType
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByPublicationType(String publicationType) throws DataAccessException {

		return findIndividualPerformanceByPublicationType(publicationType, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByPublicationType
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByPublicationType(String publicationType, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByPublicationType", startResult, maxRows, publicationType);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByFileUrl
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByFileUrl(String fileUrl) throws DataAccessException {

		return findIndividualPerformanceByFileUrl(fileUrl, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByFileUrl
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByFileUrl(String fileUrl, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByFileUrl", startResult, maxRows, fileUrl);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualPerformanceByTheme
	 *
	 */
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByTheme(String theme) throws DataAccessException {

		return findIndividualPerformanceByTheme(theme, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualPerformanceByTheme
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualPerformance> findIndividualPerformanceByTheme(String theme, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualPerformanceByTheme", startResult, maxRows, theme);
		return new LinkedHashSet<IndividualPerformance>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(IndividualPerformance entity) {
		return true;
	}
}
