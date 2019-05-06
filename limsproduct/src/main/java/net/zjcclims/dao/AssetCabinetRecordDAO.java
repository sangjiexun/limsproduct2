package net.zjcclims.dao;

import net.zjcclims.domain.Asset;
import net.zjcclims.domain.AssetCabinetRecord;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage AssetCabinet entities.
 * 
 */
public interface AssetCabinetRecordDAO extends JpaDao<AssetCabinetRecord> {
    /**
     * JPQL Query - findAssetCabinetRecordById
     *
     */
    public AssetCabinetRecord findAssetCabinetRecordById(Integer id) throws DataAccessException;

    /**
     * JPQL Query - findAssetCabinetRecordById
     *
     */
    public AssetCabinetRecord findAssetCabinetRecordById(Integer id, int startResult, int maxRows) throws DataAccessException;
    /**
     * JPQL Query - findAssetCabinetRecordsByAssetId
     *
     */
    public Set<AssetCabinetRecord> findAssetCabinetRecordsByAssetId(Integer assetId) throws DataAccessException;

    /**
     * JPQL Query - findAssetCabinetRecordsByAssetId
     *
     */
    public Set<AssetCabinetRecord> findAssetCabinetRecordsByAssetId(Integer assetId, int startResult, int maxRows) throws DataAccessException;



}