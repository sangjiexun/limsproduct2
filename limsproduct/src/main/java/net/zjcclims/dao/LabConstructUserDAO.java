package net.zjcclims.dao;


import net.zjcclims.domain.LabConstructUser;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage LabConstructUser entities.
 * 
 */
public interface LabConstructUserDAO extends JpaDao<LabConstructUser> {

	/**
	 * JPQL Query - findLabConstructUserByInfo
	 *
	 */
	public Set<LabConstructUser> findLabConstructUserByInfo(String info) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructUserByInfo
	 *
	 */
	public Set<LabConstructUser> findLabConstructUserByInfo(String info, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructUserByPrimaryKey
	 *
	 */
	public LabConstructUser findLabConstructUserByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructUserByPrimaryKey
	 *
	 */
	public LabConstructUser findLabConstructUserByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructUserById
	 *
	 */
	public LabConstructUser findLabConstructUserById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructUserById
	 *
	 */
	public LabConstructUser findLabConstructUserById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructUserByResponsibilityContent
	 *
	 */
	public Set<LabConstructUser> findLabConstructUserByResponsibilityContent(String responsibilityContent) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructUserByResponsibilityContent
	 *
	 */
	public Set<LabConstructUser> findLabConstructUserByResponsibilityContent(String responsibilityContent, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructUserByResponsibilityContentContaining
	 *
	 */
	public Set<LabConstructUser> findLabConstructUserByResponsibilityContentContaining(String responsibilityContent_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructUserByResponsibilityContentContaining
	 *
	 */
	public Set<LabConstructUser> findLabConstructUserByResponsibilityContentContaining(String responsibilityContent_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabConstructUsers
	 *
	 */
	public Set<LabConstructUser> findAllLabConstructUsers() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabConstructUsers
	 *
	 */
	public Set<LabConstructUser> findAllLabConstructUsers(int startResult, int maxRows) throws DataAccessException;

}