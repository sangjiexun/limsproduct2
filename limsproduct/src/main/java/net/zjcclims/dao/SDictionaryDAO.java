package net.zjcclims.dao;

import net.zjcclims.domain.SDictionary;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage SDictionary entities.
 * 
 */
public interface SDictionaryDAO extends JpaDao<SDictionary> {

	/**
	 * JPQL Query - findSDictionaryByNoTwo
	 *
	 */
	public Set<SDictionary> findSDictionaryByNoTwo(String noTwo) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByNoTwo
	 *
	 */
	public Set<SDictionary> findSDictionaryByNoTwo(String noTwo, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByNoContaining
	 *
	 */
	public Set<SDictionary> findSDictionaryByNoContaining(String no) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByNoContaining
	 *
	 */
	public Set<SDictionary> findSDictionaryByNoContaining(String no, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByNoTwoContaining
	 *
	 */
	public Set<SDictionary> findSDictionaryByNoTwoContaining(String noTwo_1) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByNoTwoContaining
	 *
	 */
	public Set<SDictionary> findSDictionaryByNoTwoContaining(String noTwo_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByCategory
	 *
	 */
	public Set<SDictionary> findSDictionaryByCategory(String category) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByCategory
	 *
	 */
	public Set<SDictionary> findSDictionaryByCategory(String category, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryById
	 *
	 */
	public SDictionary findSDictionaryById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryById
	 *
	 */
	public SDictionary findSDictionaryById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByNo
	 *
	 */
	public Set<SDictionary> findSDictionaryByNo(String no_1) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByNo
	 *
	 */
	public Set<SDictionary> findSDictionaryByNo(String no_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByTypeDictionary
	 *
	 */
	public Set<SDictionary> findSDictionaryByTypeDictionary(String typeDictionary) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByTypeDictionary
	 *
	 */
	public Set<SDictionary> findSDictionaryByTypeDictionary(String typeDictionary, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByCategoryContaining
	 *
	 */
	public Set<SDictionary> findSDictionaryByCategoryContaining(String category_1) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByCategoryContaining
	 *
	 */
	public Set<SDictionary> findSDictionaryByCategoryContaining(String category_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByNoOne
	 *
	 */
	public Set<SDictionary> findSDictionaryByNoOne(String noOne) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByNoOne
	 *
	 */
	public Set<SDictionary> findSDictionaryByNoOne(String noOne, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByPrimaryKey
	 *
	 */
	public SDictionary findSDictionaryByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByPrimaryKey
	 *
	 */
	public SDictionary findSDictionaryByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByNoOneContaining
	 *
	 */
	public Set<SDictionary> findSDictionaryByNoOneContaining(String noOne_1) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByNoOneContaining
	 *
	 */
	public Set<SDictionary> findSDictionaryByNoOneContaining(String noOne_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByNoThree
	 *
	 */
	public Set<SDictionary> findSDictionaryByNoThree(String noThree) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByNoThree
	 *
	 */
	public Set<SDictionary> findSDictionaryByNoThree(String noThree, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByTypeDictionaryContaining
	 *
	 */
	public Set<SDictionary> findSDictionaryByTypeDictionaryContaining(String typeDictionary_1) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByTypeDictionaryContaining
	 *
	 */
	public Set<SDictionary> findSDictionaryByTypeDictionaryContaining(String typeDictionary_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByNoThreeContaining
	 *
	 */
	public Set<SDictionary> findSDictionaryByNoThreeContaining(String noThree_1) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByNoThreeContaining
	 *
	 */
	public Set<SDictionary> findSDictionaryByNoThreeContaining(String noThree_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByNameContaining
	 *
	 */
	public Set<SDictionary> findSDictionaryByNameContaining(String name) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByNameContaining
	 *
	 */
	public Set<SDictionary> findSDictionaryByNameContaining(String name, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByName
	 *
	 */
	public Set<SDictionary> findSDictionaryByName(String name_1) throws DataAccessException;

	/**
	 * JPQL Query - findSDictionaryByName
	 *
	 */
	public Set<SDictionary> findSDictionaryByName(String name_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllSDictionarys
	 *
	 */
	public Set<SDictionary> findAllSDictionarys() throws DataAccessException;

	/**
	 * JPQL Query - findAllSDictionarys
	 *
	 */
	public Set<SDictionary> findAllSDictionarys(int startResult, int maxRows) throws DataAccessException;

}