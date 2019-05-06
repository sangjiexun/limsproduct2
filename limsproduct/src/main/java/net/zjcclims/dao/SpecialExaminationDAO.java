package net.zjcclims.dao;

import net.zjcclims.domain.SpecialExamination;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Calendar;
import java.util.Set;

/**
 * DAO to manage SpecialExamination entities.
 * 
 */
public interface SpecialExaminationDAO extends JpaDao<SpecialExamination> {

	/**
	 * JPQL Query - findSpecialExaminationByCreationDate
	 *
	 */
	public Set<SpecialExamination> findSpecialExaminationByCreationDate(java.util.Calendar creationDate) throws DataAccessException;

	/**
	 * JPQL Query - findSpecialExaminationByCreationDate
	 *
	 */
	public Set<SpecialExamination> findSpecialExaminationByCreationDate(Calendar creationDate, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSpecialExaminationByCheckItem
	 *
	 */
	public Set<SpecialExamination> findSpecialExaminationByCheckItem(String checkItem) throws DataAccessException;

	/**
	 * JPQL Query - findSpecialExaminationByCheckItem
	 *
	 */
	public Set<SpecialExamination> findSpecialExaminationByCheckItem(String checkItem, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSpecialExaminationByCheckType
	 *
	 */
	public Set<SpecialExamination> findSpecialExaminationByCheckType(String checkType) throws DataAccessException;

	/**
	 * JPQL Query - findSpecialExaminationByCheckType
	 *
	 */
	public Set<SpecialExamination> findSpecialExaminationByCheckType(String checkType, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSpecialExaminationByCheckTypeContaining
	 *
	 */
	public Set<SpecialExamination> findSpecialExaminationByCheckTypeContaining(String checkType_1) throws DataAccessException;

	/**
	 * JPQL Query - findSpecialExaminationByCheckTypeContaining
	 *
	 */
	public Set<SpecialExamination> findSpecialExaminationByCheckTypeContaining(String checkType_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSpecialExaminationByAcademyNumberContaining
	 *
	 */
	public Set<SpecialExamination> findSpecialExaminationByAcademyNumberContaining(String academyNumber) throws DataAccessException;

	/**
	 * JPQL Query - findSpecialExaminationByAcademyNumberContaining
	 *
	 */
	public Set<SpecialExamination> findSpecialExaminationByAcademyNumberContaining(String academyNumber, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSpecialExaminationByAcademyNumber
	 *
	 */
	public Set<SpecialExamination> findSpecialExaminationByAcademyNumber(String academyNumber_1) throws DataAccessException;

	/**
	 * JPQL Query - findSpecialExaminationByAcademyNumber
	 *
	 */
	public Set<SpecialExamination> findSpecialExaminationByAcademyNumber(String academyNumber_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllSpecialExaminations
	 *
	 */
	public Set<SpecialExamination> findAllSpecialExaminations() throws DataAccessException;

	/**
	 * JPQL Query - findAllSpecialExaminations
	 *
	 */
	public Set<SpecialExamination> findAllSpecialExaminations(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSpecialExaminationByPrimaryKey
	 *
	 */
	public SpecialExamination findSpecialExaminationByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findSpecialExaminationByPrimaryKey
	 *
	 */
	public SpecialExamination findSpecialExaminationByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSpecialExaminationByCheckItemContaining
	 *
	 */
	public Set<SpecialExamination> findSpecialExaminationByCheckItemContaining(String checkItem_1) throws DataAccessException;

	/**
	 * JPQL Query - findSpecialExaminationByCheckItemContaining
	 *
	 */
	public Set<SpecialExamination> findSpecialExaminationByCheckItemContaining(String checkItem_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSpecialExaminationByCheckContentContaining
	 *
	 */
	public Set<SpecialExamination> findSpecialExaminationByCheckContentContaining(String checkContent) throws DataAccessException;

	/**
	 * JPQL Query - findSpecialExaminationByCheckContentContaining
	 *
	 */
	public Set<SpecialExamination> findSpecialExaminationByCheckContentContaining(String checkContent, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSpecialExaminationByCheckContent
	 *
	 */
	public Set<SpecialExamination> findSpecialExaminationByCheckContent(String checkContent_1) throws DataAccessException;

	/**
	 * JPQL Query - findSpecialExaminationByCheckContent
	 *
	 */
	public Set<SpecialExamination> findSpecialExaminationByCheckContent(String checkContent_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSpecialExaminationById
	 *
	 */
	public SpecialExamination findSpecialExaminationById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findSpecialExaminationById
	 *
	 */
	public SpecialExamination findSpecialExaminationById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}