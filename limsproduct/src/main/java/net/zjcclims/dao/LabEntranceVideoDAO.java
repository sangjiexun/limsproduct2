package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.LabEntranceVideo;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage LabEntranceVideo entities.
 * 
 */
public interface LabEntranceVideoDAO extends JpaDao<LabEntranceVideo> {

	/**
	 * JPQL Query - findLabEntranceVideoById
	 *
	 */
	public LabEntranceVideo findLabEntranceVideoById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabEntranceVideoById
	 *
	 */
	public LabEntranceVideo findLabEntranceVideoById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabEntranceVideos
	 *
	 */
	public Set<LabEntranceVideo> findAllLabEntranceVideos() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabEntranceVideos
	 *
	 */
	public Set<LabEntranceVideo> findAllLabEntranceVideos(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabEntranceVideoByPrimaryKey
	 *
	 */
	public LabEntranceVideo findLabEntranceVideoByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabEntranceVideoByPrimaryKey
	 *
	 */
	public LabEntranceVideo findLabEntranceVideoByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}