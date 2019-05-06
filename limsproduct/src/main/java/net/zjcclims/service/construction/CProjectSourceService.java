package net.zjcclims.service.construction;


import net.zjcclims.domain.CProjectSource;
import net.zjcclims.domain.LabConstructApp;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for CProjectSource entities
 * 
 */
public interface CProjectSourceService {

	/**
	 * Delete an existing CProjectSource entity
	 * 
	 */
	public void deleteCProjectSource(CProjectSource cprojectsource);

	/**
	 * Return a count of all CProjectSource entity
	 * 
	 */
	public Integer countCProjectSources();

	/**
	 */
	public CProjectSource findCProjectSourceByPrimaryKey(Integer id);

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	public CProjectSource deleteCProjectSourceLabConstructApps(Integer cprojectsource_id, Integer related_labconstructapps_id);

	/**
	 * Load an existing CProjectSource entity
	 * 
	 */
	public Set<CProjectSource> loadCProjectSources();

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	public CProjectSource saveCProjectSourceLabConstructApps(Integer id_1, LabConstructApp related_labconstructapps);

	/**
	 * Return all CProjectSource entity
	 * 
	 */
	public List<CProjectSource> findAllCProjectSources(Integer startResult, Integer maxRows);

	/**
	 * Save an existing CProjectSource entity
	 * 
	 */
	public void saveCProjectSource(CProjectSource cprojectsource_1);
	
	/****************************************************************************
	 * 功能：查询出所有的项目来源类别
	 * 作者：李德
	 * 时间：2015-03-19
	 ****************************************************************************/
	public Set<CProjectSource> findAllCProjectSource();
	
	/****************************************************************************
	 * 功能：查询出所有的项目来源
	 * 作者：李德
	 * 时间：2015-05-08
	 ****************************************************************************/
	public List<CProjectSource> findAllCProjectSourceByCProjectSource(CProjectSource cProjectSource);
	/****************************************************************************
	 * 功能：查询出所有的项目来源
	 * 作者：李德
	 * 时间：2015-05-08
	 ****************************************************************************/
	public List<CProjectSource> findAllCProjectSourceByCProjectSource(CProjectSource cProjectSource,
                                                                      int page, int pageSize);
	
	/****************************************************************************
	 * 功能：保存实验室对象
	 * 作者：李德
	 * 时间：2015-05-08
	 ****************************************************************************/
	public CProjectSource save(CProjectSource cProjectSource);
}