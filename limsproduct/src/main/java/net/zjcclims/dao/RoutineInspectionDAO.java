package net.zjcclims.dao;

import net.zjcclims.domain.RoutineInspection;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Calendar;
import java.util.Set;

/**
 * DAO to manage RoutineInspection entities.
 * 
 */
public interface RoutineInspectionDAO extends JpaDao<RoutineInspection> {

	/**
	 * JPQL Query - findRoutineInspectionByWeekContaining
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByWeekContaining(String week) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByWeekContaining
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByWeekContaining(String week, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByPrimaryKey
	 *
	 */
	public RoutineInspection findRoutineInspectionByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByPrimaryKey
	 *
	 */
	public RoutineInspection findRoutineInspectionByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByWeek
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByWeek(String week_1) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByWeek
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByWeek(String week_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByTypeAuditing
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByTypeAuditing(String typeAuditing) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByTypeAuditing
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByTypeAuditing(String typeAuditing, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByAcademyNumberContaining
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByAcademyNumberContaining(String academyNumber) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByAcademyNumberContaining
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByAcademyNumberContaining(String academyNumber, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionById
	 *
	 */
	public RoutineInspection findRoutineInspectionById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionById
	 *
	 */
	public RoutineInspection findRoutineInspectionById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByTypeTableContaining
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByTypeTableContaining(String typeTable) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByTypeTableContaining
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByTypeTableContaining(String typeTable, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByAcademyNumber
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByAcademyNumber(String academyNumber_1) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByAcademyNumber
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByAcademyNumber(String academyNumber_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByCreationDate
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByCreationDate(java.util.Calendar creationDate) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByCreationDate
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByCreationDate(Calendar creationDate, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByTypeAuditingContaining
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByTypeAuditingContaining(String typeAuditing_1) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByTypeAuditingContaining
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByTypeAuditingContaining(String typeAuditing_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByCheckContentContaining
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByCheckContentContaining(String checkContent) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByCheckContentContaining
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByCheckContentContaining(String checkContent, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByTypeTable
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByTypeTable(String typeTable_1) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByTypeTable
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByTypeTable(String typeTable_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByCheckContent
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByCheckContent(String checkContent_1) throws DataAccessException;

	/**
	 * JPQL Query - findRoutineInspectionByCheckContent
	 *
	 */
	public Set<RoutineInspection> findRoutineInspectionByCheckContent(String checkContent_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllRoutineInspections
	 *
	 */
	public Set<RoutineInspection> findAllRoutineInspections() throws DataAccessException;

	/**
	 * JPQL Query - findAllRoutineInspections
	 *
	 */
	public Set<RoutineInspection> findAllRoutineInspections(int startResult, int maxRows) throws DataAccessException;

}