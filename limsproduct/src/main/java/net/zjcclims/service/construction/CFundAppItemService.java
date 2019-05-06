package net.zjcclims.service.construction;


import net.zjcclims.domain.CFundAppItem;
import net.zjcclims.domain.ProjectDeviceList;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for CFundAppItem entities
 * 
 */
public interface CFundAppItemService {

	/**
	 * Delete an existing CFundAppItem entity
	 * 
	 */
	public void deleteCFundAppItem(CFundAppItem cfundappitem);

	/**
	 * Save an existing ProjectDeviceList entity
	 * 
	 */
	public CFundAppItem saveCFundAppItemProjectDeviceLists(Integer id, ProjectDeviceList related_projectdevicelists);

	/**
	 * Save an existing CFundAppItem entity
	 * 
	 */
	public void saveCFundAppItem(CFundAppItem cfundappitem_1);

	/**
	 * Return a count of all CFundAppItem entity
	 * 
	 */
	public Integer countCFundAppItems();

	/**
	 * Return all CFundAppItem entity
	 * 
	 */
	public List<CFundAppItem> findAllCFundAppItems(Integer startResult, Integer maxRows);

	/**
	 * Load an existing CFundAppItem entity
	 * 
	 */
	public Set<CFundAppItem> loadCFundAppItems();

	/**
	 */
	public CFundAppItem findCFundAppItemByPrimaryKey(Integer id_1);

	/**
	 * Delete an existing ProjectDeviceList entity
	 * 
	 */
	public CFundAppItem deleteCFundAppItemProjectDeviceLists(Integer cfundappitem_id, Integer related_projectdevicelists_id);
	
	/****************************************************************************
	 * 功能：查询出所有的预算科目
	 * 作者：李德
	 * 时间：2015-06-16
	 ****************************************************************************/
	public List<CFundAppItem> findAllCFundAppItemByCFundAppItem(CFundAppItem cFundAppItem);
	/****************************************************************************
	 * 功能：查询出所有的预算科目
	 * 作者：李德
	 * 时间：2015-06-16
	 ****************************************************************************/
	public List<CFundAppItem> findAllCFundAppItemByCFundAppItem(CFundAppItem cFundAppItem,
                                                                int page, int pageSize);
	
	/****************************************************************************
	 * 功能：保存预算科目
	 * 作者：李德
	 * 时间：2015-06-16
	 ****************************************************************************/
	public CFundAppItem save(CFundAppItem cFundAppItem);
}