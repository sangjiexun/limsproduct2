package net.zjcclims.dao;

import java.util.Calendar;
import java.util.Set;

import net.zjcclims.domain.IndividualDictionary;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage IndividualDictionary entities.
 * 
 */
public interface IndividualDictionaryDAO extends JpaDao<IndividualDictionary> {

	/**
	 * JPQL Query - findIndividualDictionaryByAuditStatus
	 *
	 */
	public Set<IndividualDictionary> findIndividualDictionaryByAuditStatus(Integer auditStatus) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualDictionaryByAuditStatus
	 *
	 */
	public Set<IndividualDictionary> findIndividualDictionaryByAuditStatus(Integer auditStatus, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualDictionaryByCreater
	 *
	 */
	public Set<IndividualDictionary> findIndividualDictionaryByCreater(String creater) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualDictionaryByCreater
	 *
	 */
	public Set<IndividualDictionary> findIndividualDictionaryByCreater(String creater, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllIndividualDictionarys
	 *
	 */
	public Set<IndividualDictionary> findAllIndividualDictionarys() throws DataAccessException;

	/**
	 * JPQL Query - findAllIndividualDictionarys
	 *
	 */
	public Set<IndividualDictionary> findAllIndividualDictionarys(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualDictionaryByPrimaryKey
	 *
	 */
	public IndividualDictionary findIndividualDictionaryByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualDictionaryByPrimaryKey
	 *
	 */
	public IndividualDictionary findIndividualDictionaryByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualDictionaryByCreaterContaining
	 *
	 */
	public Set<IndividualDictionary> findIndividualDictionaryByCreaterContaining(String creater_1) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualDictionaryByCreaterContaining
	 *
	 */
	public Set<IndividualDictionary> findIndividualDictionaryByCreaterContaining(String creater_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualDictionaryById
	 *
	 */
	public IndividualDictionary findIndividualDictionaryById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualDictionaryById
	 *
	 */
	public IndividualDictionary findIndividualDictionaryById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualDictionaryByName
	 *
	 */
	public Set<IndividualDictionary> findIndividualDictionaryByName(String name) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualDictionaryByName
	 *
	 */
	public Set<IndividualDictionary> findIndividualDictionaryByName(String name, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualDictionaryByCreateDate
	 *
	 */
	public Set<IndividualDictionary> findIndividualDictionaryByCreateDate(Calendar createDate) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualDictionaryByCreateDate
	 *
	 */
	public Set<IndividualDictionary> findIndividualDictionaryByCreateDate(Calendar createDate, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualDictionaryByType
	 *
	 */
	public Set<IndividualDictionary> findIndividualDictionaryByType(Integer type) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualDictionaryByType
	 *
	 */
	public Set<IndividualDictionary> findIndividualDictionaryByType(Integer type, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualDictionaryByNameContaining
	 *
	 */
	public Set<IndividualDictionary> findIndividualDictionaryByNameContaining(String name_1) throws DataAccessException;

	/**
	 * JPQL Query - findIndividualDictionaryByNameContaining
	 *
	 */
	public Set<IndividualDictionary> findIndividualDictionaryByNameContaining(String name_1, int startResult, int maxRows) throws DataAccessException;

}