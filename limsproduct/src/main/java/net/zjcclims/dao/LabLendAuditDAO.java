package net.zjcclims.dao;

import java.util.Calendar;
import java.util.Set;

import net.zjcclims.domain.LabLendAudit;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage LabLendAudit entities.
 * 
 */
public interface LabLendAuditDAO extends JpaDao<LabLendAudit> {

	/**
	 * JPQL Query - findAllLabLendAudits
	 *
	 */
	public Set<LabLendAudit> findAllLabLendAudits() throws DataAccessException;

	/**
	 * JPQL Query - finLabLendAuditudits
	 *
	 */
	public Set<LabLendAudit> findAllLabLendAudits(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabLendAuditByMem
	 *
	 */
	public Set<LabLendAudit> findLabLendAuditByMem(String mem) throws DataAccessException;

	/**
	 * JPQL Query - findLabLendAuditByMem
	 *
	 */
	public Set<LabLendAudit> findLabLendAuditByMem(String mem, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabLendAuditByStatus
	 *
	 */
	public Set<LabLendAudit> findLabLendAuditByStatus(Integer status) throws DataAccessException;

	/**
	 * JPQL Query - findLabLendAuditByStatus
	 *
	 */
	public Set<LabLendAudit> findLabLendAuditByStatus(Integer status, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabLendAuditByPrimaryKey
	 *
	 */
	public LabLendAudit findLabLendAuditByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabLendAuditByPrimaryKey
	 *
	 */
	public LabLendAudit findLabLendAuditByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabLendAuditByResult
	 *
	 */
	public Set<LabLendAudit> findLabLendAuditByResult(String result) throws DataAccessException;

	/**
	 * JPQL Query - findLabLendAuditByResult
	 *
	 */
	public Set<LabLendAudit> findLabLendAuditByResult(String result, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabLendAuditByCreateDate
	 *
	 */
	public Set<LabLendAudit> findLabLendAuditByCreateDate(java.util.Calendar createDate) throws DataAccessException;

	/**
	 * JPQL Query - findLabLendAuditByCreateDate
	 *
	 */
	public Set<LabLendAudit> findLabLendAuditByCreateDate(Calendar createDate, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabLendAuditByResultContaining
	 *
	 */
	public Set<LabLendAudit> findLabLendAuditByResultContaining(String result_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabLendAuditByResultContaining
	 *
	 */
	public Set<LabLendAudit> findLabLendAuditByResultContaining(String result_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabLendAuditByAuditRoles
	 *
	 */
	public Set<LabLendAudit> findLabLendAuditByAuditRoles(Integer auditRoles) throws DataAccessException;

	/**
	 * JPQL Query - findLabLendAuditByAuditRoles
	 *
	 */
	public Set<LabLendAudit> findLabLendAuditByAuditRoles(Integer auditRoles, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabLendAuditById
	 *
	 */
	public LabLendAudit findLabLendAuditById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabLendAuditById
	 *
	 */
	public LabLendAudit findLabLendAuditById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}