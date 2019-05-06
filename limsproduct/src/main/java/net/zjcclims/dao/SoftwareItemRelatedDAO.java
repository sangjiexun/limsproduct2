package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.SoftwareItemRelated;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage SoftwareItemRelated entities.
 * 
 */
public interface SoftwareItemRelatedDAO extends JpaDao<SoftwareItemRelated> {

	/**
	 * JPQL Query - findSoftwareItemRelatedById
	 *
	 */
	public SoftwareItemRelated findSoftwareItemRelatedById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareItemRelatedById
	 *
	 */
	public SoftwareItemRelated findSoftwareItemRelatedById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareItemRelatedByPrimaryKey
	 *
	 */
	public SoftwareItemRelated findSoftwareItemRelatedByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareItemRelatedByPrimaryKey
	 *
	 */
	public SoftwareItemRelated findSoftwareItemRelatedByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllSoftwareItemRelateds
	 *
	 */
	public Set<SoftwareItemRelated> findAllSoftwareItemRelateds() throws DataAccessException;

	/**
	 * JPQL Query - findAllSoftwareItemRelateds
	 *
	 */
	public Set<SoftwareItemRelated> findAllSoftwareItemRelateds(int startResult, int maxRows) throws DataAccessException;

}