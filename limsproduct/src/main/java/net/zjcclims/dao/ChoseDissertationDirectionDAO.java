package net.zjcclims.dao;

import net.zjcclims.domain.ChoseDissertationDirection;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage ChoseDissertationDirection entities.
 * 
 */
public interface ChoseDissertationDirectionDAO extends
        JpaDao<ChoseDissertationDirection> {

	/**
	 * JPQL Query - findChoseDissertationDirectionByName
	 *
	 */
	public Set<ChoseDissertationDirection> findChoseDissertationDirectionByName(String name) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationDirectionByName
	 *
	 */
	public Set<ChoseDissertationDirection> findChoseDissertationDirectionByName(String name, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationDirectionByNameContaining
	 *
	 */
	public Set<ChoseDissertationDirection> findChoseDissertationDirectionByNameContaining(String name_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationDirectionByNameContaining
	 *
	 */
	public Set<ChoseDissertationDirection> findChoseDissertationDirectionByNameContaining(String name_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationDirectionByPrimaryKey
	 *
	 */
	public ChoseDissertationDirection findChoseDissertationDirectionByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationDirectionByPrimaryKey
	 *
	 */
	public ChoseDissertationDirection findChoseDissertationDirectionByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationDirectionById
	 *
	 */
	public ChoseDissertationDirection findChoseDissertationDirectionById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationDirectionById
	 *
	 */
	public ChoseDissertationDirection findChoseDissertationDirectionById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllChoseDissertationDirections
	 *
	 */
	public Set<ChoseDissertationDirection> findAllChoseDissertationDirections() throws DataAccessException;

	/**
	 * JPQL Query - findAllChoseDissertationDirections
	 *
	 */
	public Set<ChoseDissertationDirection> findAllChoseDissertationDirections(int startResult, int maxRows) throws DataAccessException;

}