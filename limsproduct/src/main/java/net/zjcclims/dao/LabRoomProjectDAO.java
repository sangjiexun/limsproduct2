package net.zjcclims.dao;


import net.zjcclims.domain.LabRoomProject;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage LabRoomProject entities.
 * 
 */
public interface LabRoomProjectDAO extends JpaDao<LabRoomProject> {

	/**
	 * JPQL Query - findAllLabRoomProjects
	 *
	 */
	public Set<LabRoomProject> findAllLabRoomProjects() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomProjects
	 *
	 */
	public Set<LabRoomProject> findAllLabRoomProjects(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomProjectByPrimaryKey
	 *
	 */
	public LabRoomProject findLabRoomProjectByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomProjectByPrimaryKey
	 *
	 */
	public LabRoomProject findLabRoomProjectByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomProjectById
	 *
	 */
	public LabRoomProject findLabRoomProjectById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomProjectById
	 *
	 */
	public LabRoomProject findLabRoomProjectById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}