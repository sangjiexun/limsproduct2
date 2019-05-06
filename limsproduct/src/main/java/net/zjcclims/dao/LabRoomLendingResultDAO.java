package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.LabRoomLendingResult;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage LabRoomLendingResult entities.
 * 
 */
public interface LabRoomLendingResultDAO extends JpaDao<LabRoomLendingResult> {

	/**
	 * JPQL Query - findLabRoomLendingResultByRemark
	 *
	 */
	public Set<LabRoomLendingResult> findLabRoomLendingResultByRemark(String remark) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingResultByRemark
	 *
	 */
	public Set<LabRoomLendingResult> findLabRoomLendingResultByRemark(String remark, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomLendingResults
	 *
	 */
	public Set<LabRoomLendingResult> findAllLabRoomLendingResults() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabRoomLendingResults
	 *
	 */
	public Set<LabRoomLendingResult> findAllLabRoomLendingResults(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingResultById
	 *
	 */
	public LabRoomLendingResult findLabRoomLendingResultById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingResultById
	 *
	 */
	public LabRoomLendingResult findLabRoomLendingResultById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingResultByPrimaryKey
	 *
	 */
	public LabRoomLendingResult findLabRoomLendingResultByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabRoomLendingResultByPrimaryKey
	 *
	 */
	public LabRoomLendingResult findLabRoomLendingResultByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}