package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.PreRoomType;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage PreRoomType entities.
 * 
 */
public interface PreRoomTypeDAO extends JpaDao<PreRoomType> {

	/**
	 * JPQL Query - findPreRoomTypeByPrimaryKey
	 *
	 */
	public PreRoomType findPreRoomTypeByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findPreRoomTypeByPrimaryKey
	 *
	 */
	public PreRoomType findPreRoomTypeByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreRoomTypeByRoomType
	 *
	 */
	public Set<PreRoomType> findPreRoomTypeByRoomType(String roomType) throws DataAccessException;

	/**
	 * JPQL Query - findPreRoomTypeByRoomType
	 *
	 */
	public Set<PreRoomType> findPreRoomTypeByRoomType(String roomType, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreRoomTypeById
	 *
	 */
	public PreRoomType findPreRoomTypeById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findPreRoomTypeById
	 *
	 */
	public PreRoomType findPreRoomTypeById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllPreRoomTypes
	 *
	 */
	public Set<PreRoomType> findAllPreRoomTypes() throws DataAccessException;

	/**
	 * JPQL Query - findAllPreRoomTypes
	 *
	 */
	public Set<PreRoomType> findAllPreRoomTypes(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreRoomTypeByRoomTypeContaining
	 *
	 */
	public Set<PreRoomType> findPreRoomTypeByRoomTypeContaining(String roomType_1) throws DataAccessException;

	/**
	 * JPQL Query - findPreRoomTypeByRoomTypeContaining
	 *
	 */
	public Set<PreRoomType> findPreRoomTypeByRoomTypeContaining(String roomType_1, int startResult, int maxRows) throws DataAccessException;

}