package net.zjcclims.dao;

import net.zjcclims.domain.ChoseDissertation;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage ChoseDissertation entities.
 * 
 */
public interface ChoseDissertationDAO extends JpaDao<ChoseDissertation> {

	/**
	 * JPQL Query - findChoseDissertationByRemarkContaining
	 *
	 */
	public Set<ChoseDissertation> findChoseDissertationByRemarkContaining(String remark) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationByRemarkContaining
	 *
	 */
	public Set<ChoseDissertation> findChoseDissertationByRemarkContaining(String remark, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationByContent
	 *
	 */
	public Set<ChoseDissertation> findChoseDissertationByContent(String content) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationByContent
	 *
	 */
	public Set<ChoseDissertation> findChoseDissertationByContent(String content, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationByContentContaining
	 *
	 */
	public Set<ChoseDissertation> findChoseDissertationByContentContaining(String content_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationByContentContaining
	 *
	 */
	public Set<ChoseDissertation> findChoseDissertationByContentContaining(String content_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationByState
	 *
	 */
	public Set<ChoseDissertation> findChoseDissertationByState(Integer state) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationByState
	 *
	 */
	public Set<ChoseDissertation> findChoseDissertationByState(Integer state, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationByTittle
	 *
	 */
	public Set<ChoseDissertation> findChoseDissertationByTittle(String tittle) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationByTittle
	 *
	 */
	public Set<ChoseDissertation> findChoseDissertationByTittle(String tittle, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationById
	 *
	 */
	public ChoseDissertation findChoseDissertationById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationById
	 *
	 */
	public ChoseDissertation findChoseDissertationById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationByTittleContaining
	 *
	 */
	public Set<ChoseDissertation> findChoseDissertationByTittleContaining(String tittle_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationByTittleContaining
	 *
	 */
	public Set<ChoseDissertation> findChoseDissertationByTittleContaining(String tittle_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllChoseDissertations
	 *
	 */
	public Set<ChoseDissertation> findAllChoseDissertations() throws DataAccessException;

	/**
	 * JPQL Query - findAllChoseDissertations
	 *
	 */
	public Set<ChoseDissertation> findAllChoseDissertations(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationByPrimaryKey
	 *
	 */
	public ChoseDissertation findChoseDissertationByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationByPrimaryKey
	 *
	 */
	public ChoseDissertation findChoseDissertationByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationByRemark
	 *
	 */
	public Set<ChoseDissertation> findChoseDissertationByRemark(String remark_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationByRemark
	 *
	 */
	public Set<ChoseDissertation> findChoseDissertationByRemark(String remark_1, int startResult, int maxRows) throws DataAccessException;

}