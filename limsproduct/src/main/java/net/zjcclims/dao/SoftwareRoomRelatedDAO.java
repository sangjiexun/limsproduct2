package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.SoftwareRoomRelated;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage SoftwareRoomRelated entities.
 * 
 */
public interface SoftwareRoomRelatedDAO extends JpaDao<SoftwareRoomRelated> {

	/**
	 * JPQL Query - findSoftwareRoomRelatedById
	 *
	 */
	public SoftwareRoomRelated findSoftwareRoomRelatedById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareRoomRelatedById
	 *
	 */
	public SoftwareRoomRelated findSoftwareRoomRelatedById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllSoftwareRoomRelateds
	 *
	 */
	public Set<SoftwareRoomRelated> findAllSoftwareRoomRelateds() throws DataAccessException;

	/**
	 * JPQL Query - findAllSoftwareRoomRelateds
	 *
	 */
	public Set<SoftwareRoomRelated> findAllSoftwareRoomRelateds(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareRoomRelatedByPrimaryKey
	 *
	 */
	public SoftwareRoomRelated findSoftwareRoomRelatedByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareRoomRelatedByPrimaryKey
	 *
	 */
	public SoftwareRoomRelated findSoftwareRoomRelatedByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}