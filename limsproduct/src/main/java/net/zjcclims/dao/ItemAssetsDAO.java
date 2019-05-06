package net.zjcclims.dao;

import net.zjcclims.domain.ItemAssets;
import org.skyway.spring.util.dao.JpaDao;

/**
 * DAO to manage DeviceRepair entities.
 *
 */
public interface ItemAssetsDAO extends JpaDao<ItemAssets> {

    ItemAssets findItemAssetsById(Integer id);

    ItemAssets findItemAssetsById(Integer id, int startResult, int maxRows);
}