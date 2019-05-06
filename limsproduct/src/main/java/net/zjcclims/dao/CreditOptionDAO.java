package net.zjcclims.dao;

import java.util.Calendar;
import java.util.Set;

import net.zjcclims.domain.CreditOption;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage CreditOption entities.
 * 
 */
public interface CreditOptionDAO extends JpaDao<CreditOption> {

	/**
	 * JPQL Query - findCreditOptionByStatus
	 *
	 */
	public Set<CreditOption> findCreditOptionByStatus(Integer status) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionByStatus
	 *
	 */
	public Set<CreditOption> findCreditOptionByStatus(Integer status, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionByNameContaining
	 *
	 */
	public Set<CreditOption> findCreditOptionByNameContaining(String name) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionByNameContaining
	 *
	 */
	public Set<CreditOption> findCreditOptionByNameContaining(String name, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionByEnabled
	 *
	 */
	public Set<CreditOption> findCreditOptionByEnabled(Integer enabled) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionByEnabled
	 *
	 */
	public Set<CreditOption> findCreditOptionByEnabled(Integer enabled, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionByName
	 *
	 */
	public Set<CreditOption> findCreditOptionByName(String name_1) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionByName
	 *
	 */
	public Set<CreditOption> findCreditOptionByName(String name_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllCreditOptions
	 *
	 */
	public Set<CreditOption> findAllCreditOptions() throws DataAccessException;

	/**
	 * JPQL Query - findAllCreditOptions
	 *
	 */
	public Set<CreditOption> findAllCreditOptions(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionByMemo
	 *
	 */
	public Set<CreditOption> findCreditOptionByMemo(String memo) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionByMemo
	 *
	 */
	public Set<CreditOption> findCreditOptionByMemo(String memo, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionById
	 *
	 */
	public CreditOption findCreditOptionById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionById
	 *
	 */
	public CreditOption findCreditOptionById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionByCreaterContaining
	 *
	 */
	public Set<CreditOption> findCreditOptionByCreaterContaining(String creater) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionByCreaterContaining
	 *
	 */
	public Set<CreditOption> findCreditOptionByCreaterContaining(String creater, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionByCreateDate
	 *
	 */
	public Set<CreditOption> findCreditOptionByCreateDate(java.util.Calendar createDate) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionByCreateDate
	 *
	 */
	public Set<CreditOption> findCreditOptionByCreateDate(Calendar createDate, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionByDeduction
	 *
	 */
	public Set<CreditOption> findCreditOptionByDeduction(Integer deduction) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionByDeduction
	 *
	 */
	public Set<CreditOption> findCreditOptionByDeduction(Integer deduction, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionByPrimaryKey
	 *
	 */
	public CreditOption findCreditOptionByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionByPrimaryKey
	 *
	 */
	public CreditOption findCreditOptionByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionByMemoContaining
	 *
	 */
	public Set<CreditOption> findCreditOptionByMemoContaining(String memo_1) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionByMemoContaining
	 *
	 */
	public Set<CreditOption> findCreditOptionByMemoContaining(String memo_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findCreditOptionByCreater
	 *
	 */
	/*public Set<CreditOption> findCreditOptionByCreater(String creater_1) throws DataAccessException;*/

	/**
	 * JPQL Query - findCreditOptionByCreater
	 *
	 */
	/*public Set<CreditOption> findCreditOptionByCreater(String creater_1, int startResult, int maxRows) throws DataAccessException;*/

}