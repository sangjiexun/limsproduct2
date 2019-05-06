package net.zjcclims.dao;

import net.zjcclims.domain.AssetClassification;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

/**
 * DAO to manage Asset entities.
 * 
 */
public interface AssetClassificationDAO extends JpaDao<AssetClassification> {
	/**
	 * JPQL Query - findAsseClassificationtById
	 *
	 */
	public AssetClassification findAsseClassificationtById(Integer id) throws DataAccessException;
	/**
	 * JPQL Query - findAssetByPrimaryKey
	 *
	 */
	public AssetClassification findAsseClassificationtById(Integer id, int startResult, int maxRows) throws DataAccessException;

}