package net.zjcclims.dao;

import java.util.Calendar;
import java.util.Set;

import net.zjcclims.domain.LabroomTimetableRegister;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage LabroomTimetableRegister entities.
 * 
 */
public interface LabroomTimetableRegisterDAO extends
		JpaDao<LabroomTimetableRegister> {

	/**
	 * JPQL Query - findLabroomTimetableRegisterByTeacherContaining
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByTeacherContaining(String teacher) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByTeacherContaining
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByTeacherContaining(String teacher, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByConfirmDate
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByConfirmDate(java.util.Calendar confirmDate) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByConfirmDate
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByConfirmDate(Calendar confirmDate, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByClassNumber
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByClassNumber(String classNumber) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByClassNumber
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByClassNumber(String classNumber, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByEndClass
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByEndClass(Integer endClass) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByEndClass
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByEndClass(Integer endClass, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByConfirmUserContaining
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByConfirmUserContaining(String confirmUser) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByConfirmUserContaining
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByConfirmUserContaining(String confirmUser, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByStartClass
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByStartClass(Integer startClass) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByStartClass
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByStartClass(Integer startClass, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByWeekday
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByWeekday(Integer weekday) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByWeekday
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByWeekday(Integer weekday, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByStartWeek
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByStartWeek(Integer startWeek) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByStartWeek
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByStartWeek(Integer startWeek, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabroomTimetableRegisters
	 *
	 */
	public Set<LabroomTimetableRegister> findAllLabroomTimetableRegisters() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabroomTimetableRegisters
	 *
	 */
	public Set<LabroomTimetableRegister> findAllLabroomTimetableRegisters(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByConfirmUser
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByConfirmUser(String confirmUser_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByConfirmUser
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByConfirmUser(String confirmUser_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByClassNumberContaining
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByClassNumberContaining(String classNumber_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByClassNumberContaining
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByClassNumberContaining(String classNumber_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByProgramNameContaining
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByProgramNameContaining(String programName) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByProgramNameContaining
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByProgramNameContaining(String programName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByConfirmDateBefore
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByConfirmDateBefore(java.util.Calendar confirmDate_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByConfirmDateBefore
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByConfirmDateBefore(Calendar confirmDate_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByPrimaryKey
	 *
	 */
	public LabroomTimetableRegister findLabroomTimetableRegisterByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByPrimaryKey
	 *
	 */
	public LabroomTimetableRegister findLabroomTimetableRegisterByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByConfirmDateAfter
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByConfirmDateAfter(java.util.Calendar confirmDate_2) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByConfirmDateAfter
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByConfirmDateAfter(Calendar confirmDate_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterById
	 *
	 */
	public LabroomTimetableRegister findLabroomTimetableRegisterById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterById
	 *
	 */
	public LabroomTimetableRegister findLabroomTimetableRegisterById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByTotalClass
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByTotalClass(Integer totalClass) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByTotalClass
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByTotalClass(Integer totalClass, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByTeacher
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByTeacher(String teacher_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByTeacher
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByTeacher(String teacher_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByEndWeek
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByEndWeek(Integer endWeek) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByEndWeek
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByEndWeek(Integer endWeek, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByTotalWeek
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByTotalWeek(Integer totalWeek) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByTotalWeek
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByTotalWeek(Integer totalWeek, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByProgramName
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByProgramName(String programName_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabroomTimetableRegisterByProgramName
	 *
	 */
	public Set<LabroomTimetableRegister> findLabroomTimetableRegisterByProgramName(String programName_1, int startResult, int maxRows) throws DataAccessException;

}