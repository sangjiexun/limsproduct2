package net.zjcclims.dao;

import java.util.Calendar;
import java.util.Set;

import net.zjcclims.domain.OperItemAudit;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage OperItemAudit entities.
 * 
 */
public interface OperItemAuditDAO extends JpaDao<OperItemAudit> {

	/**
	 * JPQL Query - findAllOperItemAudits
	 *
	 */
	public Set<OperItemAudit> findAllOperItemAudits() throws DataAccessException;

	/**
	 * JPQL Query - finOperItemAuditudits
	 *
	 */
	public Set<OperItemAudit> findAllOperItemAudits(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findOperItemAuditByMem
	 *
	 */
	public Set<OperItemAudit> findOperItemAuditByMem(String mem) throws DataAccessException;

	/**
	 * JPQL Query - findOperItemAuditByMem
	 *
	 */
	public Set<OperItemAudit> findOperItemAuditByMem(String mem, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findOperItemAuditByStatus
	 *
	 */
	public Set<OperItemAudit> findOperItemAuditByStatus(Integer status) throws DataAccessException;

	/**
	 * JPQL Query - findOperItemAuditByStatus
	 *
	 */
	public Set<OperItemAudit> findOperItemAuditByStatus(Integer status, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findOperItemAuditByPrimaryKey
	 *
	 */
	public OperItemAudit findOperItemAuditByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findOperItemAuditByPrimaryKey
	 *
	 */
	public OperItemAudit findOperItemAuditByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findOperItemAuditByResult
	 *
	 */
	public Set<OperItemAudit> findOperItemAuditByResult(String result) throws DataAccessException;

	/**
	 * JPQL Query - findOperItemAuditByResult
	 *
	 */
	public Set<OperItemAudit> findOperItemAuditByResult(String result, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findOperItemAuditByCreateDate
	 *
	 */
	public Set<OperItemAudit> findOperItemAuditByCreateDate(java.util.Calendar createDate) throws DataAccessException;

	/**
	 * JPQL Query - findOperItemAuditByCreateDate
	 *
	 */
	public Set<OperItemAudit> findOperItemAuditByCreateDate(Calendar createDate, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findOperItemAuditByResultContaining
	 *
	 */
	public Set<OperItemAudit> findOperItemAuditByResultContaining(String result_1) throws DataAccessException;

	/**
	 * JPQL Query - findOperItemAuditByResultContaining
	 *
	 */
	public Set<OperItemAudit> findOperItemAuditByResultContaining(String result_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findOperItemAuditByAuditRoles
	 *
	 */
	public Set<OperItemAudit> findOperItemAuditByAuditRoles(Integer auditRoles) throws DataAccessException;

	/**
	 * JPQL Query - findOperItemAuditByAuditRoles
	 *
	 */
	public Set<OperItemAudit> findOperItemAuditByAuditRoles(Integer auditRoles, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findOperItemAuditById
	 *
	 */
	public OperItemAudit findOperItemAuditById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findOperItemAuditById
	 *
	 */
	public OperItemAudit findOperItemAuditById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}