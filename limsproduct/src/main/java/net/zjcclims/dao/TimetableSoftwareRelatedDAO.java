package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.TimetableSoftwareRelated;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage TimetableSoftwareRelated entities.
 * 
 */
public interface TimetableSoftwareRelatedDAO extends
		JpaDao<TimetableSoftwareRelated> {

	/**
	 * JPQL Query - findTimetableSoftwareRelatedByPrimaryKey
	 *
	 */
	public TimetableSoftwareRelated findTimetableSoftwareRelatedByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableSoftwareRelatedByPrimaryKey
	 *
	 */
	public TimetableSoftwareRelated findTimetableSoftwareRelatedByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableSoftwareRelatedById
	 *
	 */
	public TimetableSoftwareRelated findTimetableSoftwareRelatedById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findTimetableSoftwareRelatedById
	 *
	 */
	public TimetableSoftwareRelated findTimetableSoftwareRelatedById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllTimetableSoftwareRelateds
	 *
	 */
	public Set<TimetableSoftwareRelated> findAllTimetableSoftwareRelateds() throws DataAccessException;

	/**
	 * JPQL Query - findAllTimetableSoftwareRelateds
	 *
	 */
	public Set<TimetableSoftwareRelated> findAllTimetableSoftwareRelateds(int startResult, int maxRows) throws DataAccessException;

}