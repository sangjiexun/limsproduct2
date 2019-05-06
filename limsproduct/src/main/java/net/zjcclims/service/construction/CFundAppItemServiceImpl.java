package net.zjcclims.service.construction;


import net.zjcclims.dao.CFundAppItemDAO;
import net.zjcclims.dao.ProjectDeviceListDAO;
import net.zjcclims.domain.CFundAppItem;
import net.zjcclims.domain.ProjectDeviceList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for CFundAppItem entities
 * 
 */

@Service("CFundAppItemService")
@Transactional
public class CFundAppItemServiceImpl implements CFundAppItemService {

	/**
	 * DAO injected by Spring that manages CFundAppItem entities
	 * 
	 */
	@Autowired
	private CFundAppItemDAO cFundAppItemDAO;

	/**
	 * DAO injected by Spring that manages ProjectDeviceList entities
	 * 
	 */
	@Autowired
	private ProjectDeviceListDAO projectDeviceListDAO;

	/**
	 * Instantiates a new CFundAppItemServiceImpl.
	 *
	 */
	public CFundAppItemServiceImpl() {
	}

	/**
	 * Delete an existing CFundAppItem entity
	 * 
	 */
	@Transactional
	public void deleteCFundAppItem(CFundAppItem cfundappitem) {
		cFundAppItemDAO.remove(cfundappitem);
		cFundAppItemDAO.flush();
	}

	/**
	 * Save an existing ProjectDeviceList entity
	 * 
	 */
	@Transactional
	public CFundAppItem saveCFundAppItemProjectDeviceLists(Integer id, ProjectDeviceList related_projectdevicelists) {
		CFundAppItem cfundappitem = cFundAppItemDAO.findCFundAppItemByPrimaryKey(id, -1, -1);
		ProjectDeviceList existingprojectDeviceLists = projectDeviceListDAO.findProjectDeviceListByPrimaryKey(related_projectdevicelists.getId());

		// copy into the existing record to preserve existing relationships
		if (existingprojectDeviceLists != null) {
			existingprojectDeviceLists.setId(related_projectdevicelists.getId());
			existingprojectDeviceLists.setOtherFundsSource(related_projectdevicelists.getOtherFundsSource());
			existingprojectDeviceLists.setBudgetaryItem(related_projectdevicelists.getBudgetaryItem());
			existingprojectDeviceLists.setAmount(related_projectdevicelists.getAmount());
			related_projectdevicelists = existingprojectDeviceLists;
		} else {
			related_projectdevicelists = projectDeviceListDAO.store(related_projectdevicelists);
			projectDeviceListDAO.flush();
		}

		related_projectdevicelists.setCFundAppItem(cfundappitem);
		cfundappitem.getProjectDeviceLists().add(related_projectdevicelists);
		related_projectdevicelists = projectDeviceListDAO.store(related_projectdevicelists);
		projectDeviceListDAO.flush();

		cfundappitem = cFundAppItemDAO.store(cfundappitem);
		cFundAppItemDAO.flush();

		return cfundappitem;
	}

	/**
	 * Save an existing CFundAppItem entity
	 * 
	 */
	@Transactional
	public void saveCFundAppItem(CFundAppItem cfundappitem) {
		CFundAppItem existingCFundAppItem = cFundAppItemDAO.findCFundAppItemByPrimaryKey(cfundappitem.getId());

		if (existingCFundAppItem != null) {
			if (existingCFundAppItem != cfundappitem) {
				existingCFundAppItem.setId(cfundappitem.getId());
				existingCFundAppItem.setName(cfundappitem.getName());
			}
			cfundappitem = cFundAppItemDAO.store(existingCFundAppItem);
		} else {
			cfundappitem = cFundAppItemDAO.store(cfundappitem);
		}
		cFundAppItemDAO.flush();
	}

	/**
	 * Return a count of all CFundAppItem entity
	 * 
	 */
	@Transactional
	public Integer countCFundAppItems() {
		return ((Long) cFundAppItemDAO.createQuerySingleResult("select count(o) from CFundAppItem o").getSingleResult()).intValue();
	}

	/**
	 * Return all CFundAppItem entity
	 * 
	 */
	@Transactional
	public List<CFundAppItem> findAllCFundAppItems(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<CFundAppItem>(cFundAppItemDAO.findAllCFundAppItems(startResult, maxRows));
	}

	/**
	 * Load an existing CFundAppItem entity
	 * 
	 */
	@Transactional
	public Set<CFundAppItem> loadCFundAppItems() {
		return cFundAppItemDAO.findAllCFundAppItems();
	}

	/**
	 */
	@Transactional
	public CFundAppItem findCFundAppItemByPrimaryKey(Integer id) {
		return cFundAppItemDAO.findCFundAppItemByPrimaryKey(id);
	}

	/**
	 * Delete an existing ProjectDeviceList entity
	 * 
	 */
	@Transactional
	public CFundAppItem deleteCFundAppItemProjectDeviceLists(Integer cfundappitem_id, Integer related_projectdevicelists_id) {
		ProjectDeviceList related_projectdevicelists = projectDeviceListDAO.findProjectDeviceListByPrimaryKey(related_projectdevicelists_id, -1, -1);

		CFundAppItem cfundappitem = cFundAppItemDAO.findCFundAppItemByPrimaryKey(cfundappitem_id, -1, -1);

		related_projectdevicelists.setCFundAppItem(null);
		cfundappitem.getProjectDeviceLists().remove(related_projectdevicelists);

		projectDeviceListDAO.remove(related_projectdevicelists);
		projectDeviceListDAO.flush();

		return cfundappitem;
	}
	
	/****************************************************************************
	 * 功能：查询出所有的预算科目
	 * 作者：李德
	 * 时间：2015-06-16
	 ****************************************************************************/
	@Override
	public List<CFundAppItem> findAllCFundAppItemByCFundAppItem(CFundAppItem cFundAppItem) {
		// TODO Auto-generated method stub
		String sql="select c from CFundAppItem c where 1=1";
		sql+=" order by c.id desc";
		return cFundAppItemDAO.executeQuery(sql,0,-1);
	}
	/****************************************************************************
	 * 功能：查询出所有的预算科目
	 * 作者：李德
	 * 时间：2015-06-16
	 ****************************************************************************/
	@Override
	public List<CFundAppItem> findAllCFundAppItemByCFundAppItem(CFundAppItem cFundAppItem,
                                                                int page, int pageSize) {
		// TODO Auto-generated method stub
		String sql="select c from CFundAppItem c where 1=1";

		sql+=" order by c.id asc";
		return cFundAppItemDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
	}
	
	/****************************************************************************
	 * 功能：保存实验室对象
	 * 作者：李德
	 * 时间：2015-06-16
	 ****************************************************************************/
	@Override
	public CFundAppItem save(CFundAppItem cFundAppItem) {
		// TODO Auto-generated method stub
		return cFundAppItemDAO.store(cFundAppItem);
	}	
	
}
