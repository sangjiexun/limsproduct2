package net.zjcclims.dao;


import net.zjcclims.domain.CProjectPurpose;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage CProjectPurpose entities.
 * 
 */
public interface CProjectPurposeDAO extends JpaDao<CProjectPurpose> {

	/**
	 * JPQL Query - findCProjectPurposeByName
	 *
	 */
	public Set<CProjectPurpose> findCProjectPurposeByName(String name) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectPurposeByName
	 *
	 */
	public Set<CProjectPurpose> findCProjectPurposeByName(String name, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectPurposeByPrimaryKey
	 *
	 */
	public CProjectPurpose findCProjectPurposeByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectPurposeByPrimaryKey
	 *
	 */
	public CProjectPurpose findCProjectPurposeByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectPurposeById
	 *
	 */
	public CProjectPurpose findCProjectPurposeById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectPurposeById
	 *
	 */
	public CProjectPurpose findCProjectPurposeById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllCProjectPurposes
	 *
	 */
	public Set<CProjectPurpose> findAllCProjectPurposes() throws DataAccessException;

	/**
	 * JPQL Query - findAllCProjectPurposes
	 *
	 */
	public Set<CProjectPurpose> findAllCProjectPurposes(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectPurposeByNameContaining
	 *
	 */
	public Set<CProjectPurpose> findCProjectPurposeByNameContaining(String name_1) throws DataAccessException;

	/**
	 * JPQL Query - findCProjectPurposeByNameContaining
	 *
	 */
	public Set<CProjectPurpose> findCProjectPurposeByNameContaining(String name_1, int startResult, int maxRows) throws DataAccessException;

}