package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.WorkerOption;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage WorkerOption entities.
 * 
 */
public interface WorkerOptionDAO extends JpaDao<WorkerOption> {

	/**
	 * JPQL Query - findAllWorkerOptions
	 *
	 */
	public Set<WorkerOption> findAllWorkerOptions() throws DataAccessException;

	/**
	 * JPQL Query - findAllWorkerOptions
	 *
	 */
	public Set<WorkerOption> findAllWorkerOptions(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findWorkerOptionByPrimaryKey
	 *
	 */
	public WorkerOption findWorkerOptionByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findWorkerOptionByPrimaryKey
	 *
	 */
	public WorkerOption findWorkerOptionByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findWorkerOptionByWorker
	 *
	 */
	public Set<WorkerOption> findWorkerOptionByWorker(Integer worker) throws DataAccessException;

	/**
	 * JPQL Query - findWorkerOptionByWorker
	 *
	 */
	public Set<WorkerOption> findWorkerOptionByWorker(Integer worker, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findWorkerOptionById
	 *
	 */
	public WorkerOption findWorkerOptionById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findWorkerOptionById
	 *
	 */
	public WorkerOption findWorkerOptionById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}