package net.zjcclims.dao;

import net.zjcclims.domain.LabSecurityCheckDetails;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage LabSecurityCheckDetails entities.
 * 
 */
public interface LabSecurityCheckDetailsDAO extends
        JpaDao<LabSecurityCheckDetails> {

	/**
	 * JPQL Query - findAllLabSecurityCheckDetailss
	 *
	 */
	public Set<LabSecurityCheckDetails> findAllLabSecurityCheckDetailss() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabSecurityCheckDetailss
	 *
	 */
	public Set<LabSecurityCheckDetails> findAllLabSecurityCheckDetailss(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckDetailsById
	 *
	 */
	public LabSecurityCheckDetails findLabSecurityCheckDetailsById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckDetailsById
	 *
	 */
	public LabSecurityCheckDetails findLabSecurityCheckDetailsById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckDetailsByPrimaryKey
	 *
	 */
	public LabSecurityCheckDetails findLabSecurityCheckDetailsByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckDetailsByPrimaryKey
	 *
	 */
	public LabSecurityCheckDetails findLabSecurityCheckDetailsByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckDetailsByResult
	 *
	 */
	public Set<LabSecurityCheckDetails> findLabSecurityCheckDetailsByResult(String result) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckDetailsByResult
	 *
	 */
	public Set<LabSecurityCheckDetails> findLabSecurityCheckDetailsByResult(String result, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckDetailsByResultContaining
	 *
	 */
	public Set<LabSecurityCheckDetails> findLabSecurityCheckDetailsByResultContaining(String result_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabSecurityCheckDetailsByResultContaining
	 *
	 */
	public Set<LabSecurityCheckDetails> findLabSecurityCheckDetailsByResultContaining(String result_1, int startResult, int maxRows) throws DataAccessException;

}