package net.zjcclims.dao;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Set;

import net.zjcclims.domain.IndividualPerformance;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage IndividualPerformance entities.
 * 
 */
public interface IndividualPerformanceDAO extends JpaDao<IndividualPerformance> {

	/**
	 * JPQL Query - findIndividualPerformanceByCreaterContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByCreaterContaining(String creater) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByCreaterContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByCreaterContaining(String creater, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByThemeAddition
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByThemeAddition(String themeAddition) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByThemeAddition
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByThemeAddition(String themeAddition, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByEndTime
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByEndTime(Calendar endTime) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByEndTime
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByEndTime(Calendar endTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceBySchoolTermContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceBySchoolTermContaining(String schoolTerm) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceBySchoolTermContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceBySchoolTermContaining(String schoolTerm, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByYearCodeContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByYearCodeContaining(String yearCode) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByYearCodeContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByYearCodeContaining(String yearCode, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByCreateDate
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByCreateDate(Calendar createDate) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByCreateDate
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByCreateDate(Calendar createDate, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceBySchoolAcademy
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceBySchoolAcademy(String schoolAcademy) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceBySchoolAcademy
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceBySchoolAcademy(String schoolAcademy, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByPublicDateAfter
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByPublicDateAfter(Calendar publicDate) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByPublicDateAfter
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByPublicDateAfter(Calendar publicDate, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByOtherTeacher
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByOtherTeacher(String otherTeacher) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByOtherTeacher
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByOtherTeacher(String otherTeacher, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByPositional
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByPositional(String positional) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByPositional
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByPositional(String positional, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceBySchoolAcademyContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceBySchoolAcademyContaining(String schoolAcademy_1) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceBySchoolAcademyContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceBySchoolAcademyContaining(String schoolAcademy_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByAdditionNum
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByAdditionNum(Integer additionNum) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByAdditionNum
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByAdditionNum(Integer additionNum, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByResTeacherContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByResTeacherContaining(String resTeacher) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByResTeacherContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByResTeacherContaining(String resTeacher, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByAidAmount
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByAidAmount(BigDecimal aidAmount) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByAidAmount
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByAidAmount(BigDecimal aidAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByPublicDate
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByPublicDate(Calendar publicDate_1) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByPublicDate
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByPublicDate(Calendar publicDate_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByPrimaryKey
	 *
	 */
	public IndividualPerformance findIndividualPerformanceByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByPrimaryKey
	 *
	 */
	public IndividualPerformance findIndividualPerformanceByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByAwardsDepartment
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByAwardsDepartment(String awardsDepartment) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByAwardsDepartment
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByAwardsDepartment(String awardsDepartment, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByStartTime
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByStartTime(Calendar startTime) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByStartTime
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByStartTime(Calendar startTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByClassHour
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByClassHour(Integer classHour) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByClassHour
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByClassHour(Integer classHour, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByFileUrlContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByFileUrlContaining(String fileUrl) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByFileUrlContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByFileUrlContaining(String fileUrl, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByBookConcern
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByBookConcern(String bookConcern) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByBookConcern
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByBookConcern(String bookConcern, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllIndividualPerformances
	 *
	 */
	public Set<IndividualPerformance> findAllIndividualPerformances() throws DataAccessException;

	/**
	 * JPQL Query - findAllIndividualPerformances
	 *
	 */
	public Set<IndividualPerformance> findAllIndividualPerformances(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByPositionalContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByPositionalContaining(String positional_1) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByPositionalContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByPositionalContaining(String positional_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByStatus
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByStatus(Integer status) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByStatus
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByStatus(Integer status, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByAuditStatus
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByAuditStatus(Integer auditStatus) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByAuditStatus
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByAuditStatus(Integer auditStatus, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByThemeAdditionContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByThemeAdditionContaining(String themeAddition_1) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByThemeAdditionContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByThemeAdditionContaining(String themeAddition_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByOtherTeacherContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByOtherTeacherContaining(String otherTeacher_1) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByOtherTeacherContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByOtherTeacherContaining(String otherTeacher_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByAwardsDepartmentContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByAwardsDepartmentContaining(String awardsDepartment_1) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByAwardsDepartmentContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByAwardsDepartmentContaining(String awardsDepartment_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByPublicDateBefore
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByPublicDateBefore(Calendar publicDate_2) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByPublicDateBefore
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByPublicDateBefore(Calendar publicDate_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByBookConcernContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByBookConcernContaining(String bookConcern_1) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByBookConcernContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByBookConcernContaining(String bookConcern_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByYearCode
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByYearCode(String yearCode_1) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByYearCode
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByYearCode(String yearCode_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByCredit
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByCredit(BigDecimal credit) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByCredit
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByCredit(BigDecimal credit, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByCreater
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByCreater(String creater_1) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByCreater
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByCreater(String creater_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceById
	 *
	 */
	public IndividualPerformance findIndividualPerformanceById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceById
	 *
	 */
	public IndividualPerformance findIndividualPerformanceById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByType
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByType(Integer type) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByType
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByType(Integer type, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceBySchoolTerm
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceBySchoolTerm(String schoolTerm_1) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceBySchoolTerm
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceBySchoolTerm(String schoolTerm_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByMemo
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByMemo(String memo) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByMemo
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByMemo(String memo, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByResTeacher
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByResTeacher(String resTeacher_1) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByResTeacher
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByResTeacher(String resTeacher_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByPublicationTypeContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByPublicationTypeContaining(String publicationType) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByPublicationTypeContaining
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByPublicationTypeContaining(String publicationType, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByPublicationType
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByPublicationType(String publicationType_1) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByPublicationType
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByPublicationType(String publicationType_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByFileUrl
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByFileUrl(String fileUrl_1) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByFileUrl
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByFileUrl(String fileUrl_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByTheme
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByTheme(String theme) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualPerformanceByTheme
	 *
	 */
	public Set<IndividualPerformance> findIndividualPerformanceByTheme(String theme, int startResult, int maxRows) throws DataAccessException;

}