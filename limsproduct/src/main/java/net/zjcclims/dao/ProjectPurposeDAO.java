package net.zjcclims.dao;


import net.zjcclims.domain.ProjectPurpose;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage ProjectPurpose entities.
 * 
 */
public interface ProjectPurposeDAO extends JpaDao<ProjectPurpose> {

	/**
	 * JPQL Query - findProjectPurposeById
	 *
	 */
	public ProjectPurpose findProjectPurposeById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findProjectPurposeById
	 *
	 */
	public ProjectPurpose findProjectPurposeById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectPurposes
	 *
	 */
	public Set<ProjectPurpose> findAllProjectPurposes() throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectPurposes
	 *
	 */
	public Set<ProjectPurpose> findAllProjectPurposes(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectPurposeByPrimaryKey
	 *
	 */
	public ProjectPurpose findProjectPurposeByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectPurposeByPrimaryKey
	 *
	 */
	public ProjectPurpose findProjectPurposeByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectPurposeByLabConstructAppId
	 *
	 */
	public Set<ProjectPurpose> findProjectPurposeByLabConstructAppId(Integer labConstructAppId) throws DataAccessException;

	/**
	 * JPQL Query - findProjectPurposeByLabConstructAppId
	 *
	 */
	public Set<ProjectPurpose> findProjectPurposeByLabConstructAppId(Integer labConstructAppId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectPurposeByProjectPurposeId
	 *
	 */
	public Set<ProjectPurpose> findProjectPurposeByProjectPurposeId(Integer projectPurposeId) throws DataAccessException;

	/**
	 * JPQL Query - findProjectPurposeByProjectPurposeId
	 *
	 */
	public Set<ProjectPurpose> findProjectPurposeByProjectPurposeId(Integer projectPurposeId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectPurposeByInfo
	 *
	 */
	public Set<ProjectPurpose> findProjectPurposeByInfo(String info) throws DataAccessException;

	/**
	 * JPQL Query - findProjectPurposeByInfo
	 *
	 */
	public Set<ProjectPurpose> findProjectPurposeByInfo(String info, int startResult, int maxRows) throws DataAccessException;

}