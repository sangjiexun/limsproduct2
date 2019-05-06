package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.PreSoftware;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage PreSoftware entities.
 * 
 */
public interface PreSoftwareDAO extends JpaDao<PreSoftware> {

	/**
	 * JPQL Query - findPreSoftwareById
	 *
	 */
	public PreSoftware findPreSoftwareById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findPreSoftwareById
	 *
	 */
	public PreSoftware findPreSoftwareById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreSoftwareByName
	 *
	 */
	public Set<PreSoftware> findPreSoftwareByName(String name) throws DataAccessException;

	/**
	 * JPQL Query - findPreSoftwareByName
	 *
	 */
	public Set<PreSoftware> findPreSoftwareByName(String name, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreSoftwareByPrimaryKey
	 *
	 */
	public PreSoftware findPreSoftwareByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findPreSoftwareByPrimaryKey
	 *
	 */
	public PreSoftware findPreSoftwareByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllPreSoftwares
	 *
	 */
	public Set<PreSoftware> findAllPreSoftwares() throws DataAccessException;

	/**
	 * JPQL Query - findAllPreSoftwares
	 *
	 */
	public Set<PreSoftware> findAllPreSoftwares(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreSoftwareByNameContaining
	 *
	 */
	public Set<PreSoftware> findPreSoftwareByNameContaining(String name_1) throws DataAccessException;

	/**
	 * JPQL Query - findPreSoftwareByNameContaining
	 *
	 */
	public Set<PreSoftware> findPreSoftwareByNameContaining(String name_1, int startResult, int maxRows) throws DataAccessException;

}