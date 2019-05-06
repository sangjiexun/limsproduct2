package net.zjcclims.dao;

import net.zjcclims.domain.LabSecurityCheck;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage LabSecurityCheck entities.
 * 
 */
public interface LabSecurityCheckDAO extends JpaDao<LabSecurityCheck> {

	/**
	 * JPQL Query - findLabSecurityCheckByAcademyNumber
	 *
	 */
	public Set<LabSecurityCheck> findLabSecurityCheckByAcademyNumber(String academyNumber) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckByAcademyNumber
	 *
	 */
	public Set<LabSecurityCheck> findLabSecurityCheckByAcademyNumber(String academyNumber, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckByMonthContaining
	 *
	 */
	public Set<LabSecurityCheck> findLabSecurityCheckByMonthContaining(String month) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckByMonthContaining
	 *
	 */
	public Set<LabSecurityCheck> findLabSecurityCheckByMonthContaining(String month, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckByPrimaryKey
	 *
	 */
	public LabSecurityCheck findLabSecurityCheckByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckByPrimaryKey
	 *
	 */
	public LabSecurityCheck findLabSecurityCheckByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckByTypeAuditingContaining
	 *
	 */
	public Set<LabSecurityCheck> findLabSecurityCheckByTypeAuditingContaining(String typeAuditing) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckByTypeAuditingContaining
	 *
	 */
	public Set<LabSecurityCheck> findLabSecurityCheckByTypeAuditingContaining(String typeAuditing, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckByAcademyNumberContaining
	 *
	 */
	public Set<LabSecurityCheck> findLabSecurityCheckByAcademyNumberContaining(String academyNumber_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckByAcademyNumberContaining
	 *
	 */
	public Set<LabSecurityCheck> findLabSecurityCheckByAcademyNumberContaining(String academyNumber_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckByMonth
	 *
	 */
	public Set<LabSecurityCheck> findLabSecurityCheckByMonth(String month_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckByMonth
	 *
	 */
	public Set<LabSecurityCheck> findLabSecurityCheckByMonth(String month_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckById
	 *
	 */
	public LabSecurityCheck findLabSecurityCheckById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckById
	 *
	 */
	public LabSecurityCheck findLabSecurityCheckById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckByTypeAuditing
	 *
	 */
	public Set<LabSecurityCheck> findLabSecurityCheckByTypeAuditing(String typeAuditing_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckByTypeAuditing
	 *
	 */
	public Set<LabSecurityCheck> findLabSecurityCheckByTypeAuditing(String typeAuditing_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckByTypeTable
	 *
	 */
	public Set<LabSecurityCheck> findLabSecurityCheckByTypeTable(String typeTable) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckByTypeTable
	 *
	 */
	public Set<LabSecurityCheck> findLabSecurityCheckByTypeTable(String typeTable, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckByTypeTableContaining
	 *
	 */
	public Set<LabSecurityCheck> findLabSecurityCheckByTypeTableContaining(String typeTable_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckByTypeTableContaining
	 *
	 */
	public Set<LabSecurityCheck> findLabSecurityCheckByTypeTableContaining(String typeTable_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabSecurityChecks
	 *
	 */
	public Set<LabSecurityCheck> findAllLabSecurityChecks() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabSecurityChecks
	 *
	 */
	public Set<LabSecurityCheck> findAllLabSecurityChecks(int startResult, int maxRows) throws DataAccessException;

}