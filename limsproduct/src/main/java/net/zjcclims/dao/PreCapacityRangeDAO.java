package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.PreCapacityRange;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage PreCapacityRange entities.
 * 
 */
public interface PreCapacityRangeDAO extends JpaDao<PreCapacityRange> {

	/**
	 * JPQL Query - findPreCapacityRangeByCapaRangeContaining
	 *
	 */
	public Set<PreCapacityRange> findPreCapacityRangeByCapaRangeContaining(String capaRange) throws DataAccessException;

	/**
	 * JPQL Query - findPreCapacityRangeByCapaRangeContaining
	 *
	 */
	public Set<PreCapacityRange> findPreCapacityRangeByCapaRangeContaining(String capaRange, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreCapacityRangeById
	 *
	 */
	public PreCapacityRange findPreCapacityRangeById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findPreCapacityRangeById
	 *
	 */
	public PreCapacityRange findPreCapacityRangeById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreCapacityRangeByCapaTypeContaining
	 *
	 */
	public Set<PreCapacityRange> findPreCapacityRangeByCapaTypeContaining(String capaType) throws DataAccessException;

	/**
	 * JPQL Query - findPreCapacityRangeByCapaTypeContaining
	 *
	 */
	public Set<PreCapacityRange> findPreCapacityRangeByCapaTypeContaining(String capaType, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreCapacityRangeByCapaRange
	 *
	 */
	public Set<PreCapacityRange> findPreCapacityRangeByCapaRange(String capaRange_1) throws DataAccessException;

	/**
	 * JPQL Query - findPreCapacityRangeByCapaRange
	 *
	 */
	public Set<PreCapacityRange> findPreCapacityRangeByCapaRange(String capaRange_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreCapacityRangeByCapaType
	 *
	 */
	public Set<PreCapacityRange> findPreCapacityRangeByCapaType(String capaType_1) throws DataAccessException;

	/**
	 * JPQL Query - findPreCapacityRangeByCapaType
	 *
	 */
	public Set<PreCapacityRange> findPreCapacityRangeByCapaType(String capaType_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findPreCapacityRangeByPrimaryKey
	 *
	 */
	public PreCapacityRange findPreCapacityRangeByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findPreCapacityRangeByPrimaryKey
	 *
	 */
	public PreCapacityRange findPreCapacityRangeByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllPreCapacityRanges
	 *
	 */
	public Set<PreCapacityRange> findAllPreCapacityRanges() throws DataAccessException;

	/**
	 * JPQL Query - findAllPreCapacityRanges
	 *
	 */
	public Set<PreCapacityRange> findAllPreCapacityRanges(int startResult, int maxRows) throws DataAccessException;

}