package net.zjcclims.service.construction;


import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectDeviceList entities
 * 
 */

@Service("ProjectDeviceListService")
@Transactional
public class ProjectDeviceListServiceImpl implements ProjectDeviceListService {

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
	 * Instantiates a new ProjectDeviceListServiceImpl.
	 *
	 */
	public ProjectDeviceListServiceImpl() {
	}

	/**
	 * Load an existing ProjectDeviceList entity
	 * 
	 */
	@Transactional
	public Set<ProjectDeviceList> loadProjectDeviceLists() {
		return projectDeviceListDAO.findAllProjectDeviceLists();
	}

	/**
	 * Delete an existing CFundAppItem entity
	 * 
	 */
	@Transactional
	public ProjectDeviceList deleteProjectDeviceListCFundAppItem(Integer projectdevicelist_id, Integer related_cfundappitem_id) {
		ProjectDeviceList projectdevicelist = projectDeviceListDAO.findProjectDeviceListByPrimaryKey(projectdevicelist_id, -1, -1);
		CFundAppItem related_cfundappitem = cFundAppItemDAO.findCFundAppItemByPrimaryKey(related_cfundappitem_id, -1, -1);

		projectdevicelist.setCFundAppItem(null);
		related_cfundappitem.getProjectDeviceLists().remove(projectdevicelist);
		projectdevicelist = projectDeviceListDAO.store(projectdevicelist);
		projectDeviceListDAO.flush();

		related_cfundappitem = cFundAppItemDAO.store(related_cfundappitem);
		cFundAppItemDAO.flush();

		cFundAppItemDAO.remove(related_cfundappitem);
		cFundAppItemDAO.flush();

		return projectdevicelist;
	}

	/**
	 * Return a count of all ProjectDeviceList entity
	 * 
	 */
	@Transactional
	public Integer countProjectDeviceLists() {
		return ((Long) projectDeviceListDAO.createQuerySingleResult("select count(o) from ProjectDeviceList o").getSingleResult()).intValue();
	}

	/**
	 * Return all ProjectDeviceList entity
	 * 
	 */
	@Transactional
	public List<ProjectDeviceList> findAllProjectDeviceLists(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<ProjectDeviceList>(projectDeviceListDAO.findAllProjectDeviceLists(startResult, maxRows));
	}

	/**
	 * Save an existing ProjectDeviceList entity
	 * 
	 */
	@Transactional
	public void saveProjectDeviceList(ProjectDeviceList projectdevicelist) {
		ProjectDeviceList existingProjectDeviceList = projectDeviceListDAO.findProjectDeviceListByPrimaryKey(projectdevicelist.getId());

		if (existingProjectDeviceList != null) {
			if (existingProjectDeviceList != projectdevicelist) {
				existingProjectDeviceList.setId(projectdevicelist.getId());
				existingProjectDeviceList.setOtherFundsSource(projectdevicelist.getOtherFundsSource());
				existingProjectDeviceList.setBudgetaryItem(projectdevicelist.getBudgetaryItem());
				existingProjectDeviceList.setAmount(projectdevicelist.getAmount());
			}
			projectdevicelist = projectDeviceListDAO.store(existingProjectDeviceList);
		} else {
			projectdevicelist = projectDeviceListDAO.store(projectdevicelist);
		}
		projectDeviceListDAO.flush();
	}

	/**
	 * Delete an existing ProjectDeviceList entity
	 * 
	 */
	@Transactional
	public void deleteProjectDeviceList(ProjectDeviceList projectdevicelist) {
		projectDeviceListDAO.remove(projectdevicelist);
		projectDeviceListDAO.flush();
	}

	/**
	 */
	@Transactional
	public ProjectDeviceList findProjectDeviceListByPrimaryKey(Integer id) {
		return projectDeviceListDAO.findProjectDeviceListByPrimaryKey(id);
	}

	/**
	 * Save an existing CFundAppItem entity
	 * 
	 */
	@Transactional
	public ProjectDeviceList saveProjectDeviceListCFundAppItem(Integer id, CFundAppItem related_cfundappitem) {
		ProjectDeviceList projectdevicelist = projectDeviceListDAO.findProjectDeviceListByPrimaryKey(id, -1, -1);
		CFundAppItem existingCFundAppItem = cFundAppItemDAO.findCFundAppItemByPrimaryKey(related_cfundappitem.getId());

		// copy into the existing record to preserve existing relationships
		if (existingCFundAppItem != null) {
			existingCFundAppItem.setId(related_cfundappitem.getId());
			existingCFundAppItem.setName(related_cfundappitem.getName());
			related_cfundappitem = existingCFundAppItem;
		} else {
			related_cfundappitem = cFundAppItemDAO.store(related_cfundappitem);
			cFundAppItemDAO.flush();
		}

		projectdevicelist.setCFundAppItem(related_cfundappitem);
		related_cfundappitem.getProjectDeviceLists().add(projectdevicelist);
		projectdevicelist = projectDeviceListDAO.store(projectdevicelist);
		projectDeviceListDAO.flush();

		related_cfundappitem = cFundAppItemDAO.store(related_cfundappitem);
		cFundAppItemDAO.flush();

		return projectdevicelist;
	}
	
	/***************************************************************************************
	 * 功能：根据主键获取ProjectDeviceList对象
	 * 作者：李德
	 * 时间：2015-03-27
	 **************************************************************************************/
	@Transactional
	public List<ProjectDeviceList> findProjectDeviceListByKey(Integer id) {
		List<ProjectDeviceList> projectDeviceLists = projectDeviceListDAO
				.executeQuery("select a from ProjectDeviceList a where a.id = "
						+ id + " order by a.id desc");
		return projectDeviceLists;
	}
}
