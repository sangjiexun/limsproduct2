
package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.LabConstructionProjectAuditNew;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage LabConstructionProjectAuditNew entities.
 * 
 */
public interface LabConstructionProjectAuditNewDAO extends JpaDao<LabConstructionProjectAuditNew> {

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewByResult
	 *
	 */
	public Set<LabConstructionProjectAuditNew> findLabConstructionProjectAuditNewByResult(Integer result) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewByResult
	 *
	 */
	public Set<LabConstructionProjectAuditNew> findLabConstructionProjectAuditNewByResult(Integer result, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewByPrimaryKey
	 *
	 */
	public LabConstructionProjectAuditNew findLabConstructionProjectAuditNewByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewByPrimaryKey
	 *
	 */
	public LabConstructionProjectAuditNew findLabConstructionProjectAuditNewByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabConstructionProjectAuditNews
	 *
	 */
	public Set<LabConstructionProjectAuditNew> findAllLabConstructionProjectAuditNews() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabConstructionProjectAuditNews
	 *
	 */
	public Set<LabConstructionProjectAuditNew> findAllLabConstructionProjectAuditNews(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewByStage
	 *
	 */
	public Set<LabConstructionProjectAuditNew> findLabConstructionProjectAuditNewByStage(Integer stage) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewByStage
	 *
	 */
	public Set<LabConstructionProjectAuditNew> findLabConstructionProjectAuditNewByStage(Integer stage, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewById
	 *
	 */
	public LabConstructionProjectAuditNew findLabConstructionProjectAuditNewById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewById
	 *
	 */
	public LabConstructionProjectAuditNew findLabConstructionProjectAuditNewById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewByRemark
	 *
	 */
	public Set<LabConstructionProjectAuditNew> findLabConstructionProjectAuditNewByRemark(String remark) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewByRemark
	 *
	 */
	public Set<LabConstructionProjectAuditNew> findLabConstructionProjectAuditNewByRemark(String remark, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewByRemarkContaining
	 *
	 */
	public Set<LabConstructionProjectAuditNew> findLabConstructionProjectAuditNewByRemarkContaining(String remark_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionProjectAuditNewByRemarkContaining
	 *
	 */
	public Set<LabConstructionProjectAuditNew> findLabConstructionProjectAuditNewByRemarkContaining(String remark_1, int startResult, int maxRows) throws DataAccessException;

}