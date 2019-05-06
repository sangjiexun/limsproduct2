package net.zjcclims.dao;

import java.util.Calendar;
import java.util.Set;

import net.zjcclims.domain.SoftwareReserveAudit;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage SoftwareReserveAudit entities.
 * 
 */
public interface SoftwareReserveAuditDAO extends JpaDao<SoftwareReserveAudit> {

	/**
	 * JPQL Query - findAllSoftwareReserveAudits
	 *
	 */
	public Set<SoftwareReserveAudit> findAllSoftwareReserveAudits() throws DataAccessException;

	/**
	 * JPQL Query - finSoftwareReserveAuditudits
	 *
	 */
	public Set<SoftwareReserveAudit> findAllSoftwareReserveAudits(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveAuditByMem
	 *
	 */
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByMem(String mem) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveAuditByMem
	 *
	 */
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByMem(String mem, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveAuditByStatus
	 *
	 */
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByStatus(Integer status) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveAuditByStatus
	 *
	 */
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByStatus(Integer status, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveAuditByPrimaryKey
	 *
	 */
	public SoftwareReserveAudit findSoftwareReserveAuditByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveAuditByPrimaryKey
	 *
	 */
	public SoftwareReserveAudit findSoftwareReserveAuditByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveAuditByResult
	 *
	 */
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByResult(String result) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveAuditByResult
	 *
	 */
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByResult(String result, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveAuditByCreateDate
	 *
	 */
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByCreateDate(java.util.Calendar createDate) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveAuditByCreateDate
	 *
	 */
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByCreateDate(Calendar createDate, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveAuditByResultContaining
	 *
	 */
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByResultContaining(String result_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveAuditByResultContaining
	 *
	 */
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByResultContaining(String result_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveAuditByAuditRoles
	 *
	 */
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByAuditRoles(Integer auditRoles) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveAuditByAuditRoles
	 *
	 */
	public Set<SoftwareReserveAudit> findSoftwareReserveAuditByAuditRoles(Integer auditRoles, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveAuditById
	 *
	 */
	public SoftwareReserveAudit findSoftwareReserveAuditById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareReserveAuditById
	 *
	 */
	public SoftwareReserveAudit findSoftwareReserveAuditById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}