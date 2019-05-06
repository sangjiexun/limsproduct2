package net.zjcclims.dao;

import net.zjcclims.domain.ChoseAttention;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage ChoseAttention entities.
 * 
 */
public interface ChoseAttentionDAO extends JpaDao<ChoseAttention> {

	/**
	 * JPQL Query - findChoseAttentionByTittleContaining
	 *
	 */
	public Set<ChoseAttention> findChoseAttentionByTittleContaining(String tittle) throws DataAccessException;

	/**
	 * JPQL Query - findChoseAttentionByTittleContaining
	 *
	 */
	public Set<ChoseAttention> findChoseAttentionByTittleContaining(String tittle, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseAttentionByContent
	 *
	 */
	public Set<ChoseAttention> findChoseAttentionByContent(String content) throws DataAccessException;

	/**
	 * JPQL Query - findChoseAttentionByContent
	 *
	 */
	public Set<ChoseAttention> findChoseAttentionByContent(String content, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseAttentionByTittle
	 *
	 */
	public Set<ChoseAttention> findChoseAttentionByTittle(String tittle_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseAttentionByTittle
	 *
	 */
	public Set<ChoseAttention> findChoseAttentionByTittle(String tittle_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseAttentionByPrimaryKey
	 *
	 */
	public ChoseAttention findChoseAttentionByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findChoseAttentionByPrimaryKey
	 *
	 */
	public ChoseAttention findChoseAttentionByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseAttentionByType
	 *
	 */
	public Set<ChoseAttention> findChoseAttentionByType(Integer type) throws DataAccessException;

	/**
	 * JPQL Query - findChoseAttentionByType
	 *
	 */
	public Set<ChoseAttention> findChoseAttentionByType(Integer type, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseAttentionById
	 *
	 */
	public ChoseAttention findChoseAttentionById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseAttentionById
	 *
	 */
	public ChoseAttention findChoseAttentionById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllChoseAttentions
	 *
	 */
	public Set<ChoseAttention> findAllChoseAttentions() throws DataAccessException;

	/**
	 * JPQL Query - findAllChoseAttentions
	 *
	 */
	public Set<ChoseAttention> findAllChoseAttentions(int startResult, int maxRows) throws DataAccessException;

}