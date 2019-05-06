package net.zjcclims.dao;


import net.zjcclims.domain.LabConstructAppApproval;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Calendar;
import java.util.Set;

/**
 * DAO to manage LabConstructAppApproval entities.
 * 
 */
public interface LabConstructAppApprovalDAO extends
		JpaDao<LabConstructAppApproval> {

	/**
	 * JPQL Query - findLabConstructAppApprovalByResultContaining
	 *
	 */
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByResultContaining(String result) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppApprovalByResultContaining
	 *
	 */
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByResultContaining(String result, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppApprovalByPrimaryKey
	 *
	 */
	public LabConstructAppApproval findLabConstructAppApprovalByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppApprovalByPrimaryKey
	 *
	 */
	public LabConstructAppApproval findLabConstructAppApprovalByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppApprovalByResult
	 *
	 */
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByResult(String result_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppApprovalByResult
	 *
	 */
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByResult(String result_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppApprovalByFlag
	 *
	 */
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByFlag(Integer flag) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppApprovalByFlag
	 *
	 */
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByFlag(Integer flag, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppApprovalByCreatedateBefore
	 *
	 */
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByCreatedateBefore(java.util.Calendar createdate) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppApprovalByCreatedateBefore
	 *
	 */
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByCreatedateBefore(Calendar createdate, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppApprovalByLevel
	 *
	 */
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByLevel(Integer level) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppApprovalByLevel
	 *
	 */
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByLevel(Integer level, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabConstructAppApprovals
	 *
	 */
	public Set<LabConstructAppApproval> findAllLabConstructAppApprovals() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabConstructAppApprovals
	 *
	 */
	public Set<LabConstructAppApproval> findAllLabConstructAppApprovals(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppApprovalById
	 *
	 */
	public LabConstructAppApproval findLabConstructAppApprovalById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppApprovalById
	 *
	 */
	public LabConstructAppApproval findLabConstructAppApprovalById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppApprovalByCreatedate
	 *
	 */
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByCreatedate(java.util.Calendar createdate_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppApprovalByCreatedate
	 *
	 */
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByCreatedate(Calendar createdate_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppApprovalByCreatedateAfter
	 *
	 */
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByCreatedateAfter(java.util.Calendar createdate_2) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructAppApprovalByCreatedateAfter
	 *
	 */
	public Set<LabConstructAppApproval> findLabConstructAppApprovalByCreatedateAfter(Calendar createdate_2, int startResult, int maxRows) throws DataAccessException;

}