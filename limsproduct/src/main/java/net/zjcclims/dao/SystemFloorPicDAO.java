package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.SystemFloorPic;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage SystemFloorPic entities.
 * 
 */
public interface SystemFloorPicDAO extends JpaDao<SystemFloorPic> {

	/**
	 * JPQL Query - findSystemFloorPicByFloorNo
	 *
	 */
	public Set<SystemFloorPic> findSystemFloorPicByFloorNo(Integer floorNo) throws DataAccessException;

	/**
	 * JPQL Query - findSystemFloorPicByFloorNo
	 *
	 */
	public Set<SystemFloorPic> findSystemFloorPicByFloorNo(Integer floorNo, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSystemFloorPicBySystemBuild
	 *
	 */
	public Set<SystemFloorPic> findSystemFloorPicBySystemBuild(String systemBuild) throws DataAccessException;

	/**
	 * JPQL Query - findSystemFloorPicBySystemBuild
	 *
	 */
	public Set<SystemFloorPic> findSystemFloorPicBySystemBuild(String systemBuild, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSystemFloorPicByDocumentUrlContaining
	 *
	 */
	public Set<SystemFloorPic> findSystemFloorPicByDocumentUrlContaining(String documentUrl) throws DataAccessException;

	/**
	 * JPQL Query - findSystemFloorPicByDocumentUrlContaining
	 *
	 */
	public Set<SystemFloorPic> findSystemFloorPicByDocumentUrlContaining(String documentUrl, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSystemFloorPicBySystemBuildContaining
	 *
	 */
	public Set<SystemFloorPic> findSystemFloorPicBySystemBuildContaining(String systemBuild_1) throws DataAccessException;

	/**
	 * JPQL Query - findSystemFloorPicBySystemBuildContaining
	 *
	 */
	public Set<SystemFloorPic> findSystemFloorPicBySystemBuildContaining(String systemBuild_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSystemFloorPicByDocumentName
	 *
	 */
	public Set<SystemFloorPic> findSystemFloorPicByDocumentName(String documentName) throws DataAccessException;

	/**
	 * JPQL Query - findSystemFloorPicByDocumentName
	 *
	 */
	public Set<SystemFloorPic> findSystemFloorPicByDocumentName(String documentName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSystemFloorPicByPrimaryKey
	 *
	 */
	public SystemFloorPic findSystemFloorPicByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findSystemFloorPicByPrimaryKey
	 *
	 */
	public SystemFloorPic findSystemFloorPicByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSystemFloorPicByDocumentNameContaining
	 *
	 */
	public Set<SystemFloorPic> findSystemFloorPicByDocumentNameContaining(String documentName_1) throws DataAccessException;

	/**
	 * JPQL Query - findSystemFloorPicByDocumentNameContaining
	 *
	 */
	public Set<SystemFloorPic> findSystemFloorPicByDocumentNameContaining(String documentName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllSystemFloorPics
	 *
	 */
	public Set<SystemFloorPic> findAllSystemFloorPics() throws DataAccessException;

	/**
	 * JPQL Query - findAllSystemFloorPics
	 *
	 */
	public Set<SystemFloorPic> findAllSystemFloorPics(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSystemFloorPicById
	 *
	 */
	public SystemFloorPic findSystemFloorPicById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findSystemFloorPicById
	 *
	 */
	public SystemFloorPic findSystemFloorPicById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSystemFloorPicByDocumentUrl
	 *
	 */
	public Set<SystemFloorPic> findSystemFloorPicByDocumentUrl(String documentUrl_1) throws DataAccessException;

	/**
	 * JPQL Query - findSystemFloorPicByDocumentUrl
	 *
	 */
	public Set<SystemFloorPic> findSystemFloorPicByDocumentUrl(String documentUrl_1, int startResult, int maxRows) throws DataAccessException;

}