package net.zjcclims.service.construction;


import net.zjcclims.domain.CProjectPurpose;
import net.zjcclims.domain.LabConstructApp;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for CProjectPurpose entities
 * 
 */
public interface CProjectPurposeService {

	/**
	 * Delete an existing CProjectPurpose entity
	 * 
	 */
	public void deleteCProjectPurpose(CProjectPurpose cprojectpurpose);

	/**
	 * Return all CProjectPurpose entity
	 * 
	 */
	public List<CProjectPurpose> findAllCProjectPurposes(Integer startResult, Integer maxRows);

	/**
	 * Save an existing CProjectPurpose entity
	 * 
	 */
	public void saveCProjectPurpose(CProjectPurpose cprojectpurpose_1);

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	public CProjectPurpose deleteCProjectPurposeLabConstructApps(Integer cprojectpurpose_id, Integer related_labconstructapps_id);

	/**
	 * Return a count of all CProjectPurpose entity
	 * 
	 */
	public Integer countCProjectPurposes();

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	public CProjectPurpose saveCProjectPurposeLabConstructApps(Integer id, LabConstructApp related_labconstructapps);

	/**
	 * Load an existing CProjectPurpose entity
	 * 
	 */
	public Set<CProjectPurpose> loadCProjectPurposes();

	/**
	 */
	public CProjectPurpose findCProjectPurposeByPrimaryKey(Integer id_1);
	
	/****************************************************************************
	 * 功能：查询出所有的用途类别
	 * 作者：李德
	 * 时间：2015-03-19
	 ****************************************************************************/
	public Set<CProjectPurpose> findAllCProjectPurpose();
	
	/****************************************************************************
	 * 功能：查询出所有的用途
	 * 作者：李德
	 * 时间：2015-05-11
	 ****************************************************************************/
	public List<CProjectPurpose> findAllCProjectPurposeByCProjectPurpose(CProjectPurpose cProjectPurpose);
	/****************************************************************************
	 * 功能：查询出所有的用途
	 * 作者：李德
	 * 时间：2015-05-11
	 ****************************************************************************/
	public List<CProjectPurpose> findAllCProjectPurposeByCProjectPurpose(CProjectPurpose cProjectPurpose,
                                                                         int page, int pageSize);
	
	/****************************************************************************
	 * 功能：保存实验室对象
	 * 作者：李德
	 * 时间：2015-05-11
	 ****************************************************************************/
	public CProjectPurpose save(CProjectPurpose cProjectPurpose);
	

}