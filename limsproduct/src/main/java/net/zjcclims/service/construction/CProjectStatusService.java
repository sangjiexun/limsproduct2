package net.zjcclims.service.construction;



import net.zjcclims.domain.CProjectStatus;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for CProjectStatus entities
 * 
 */
public interface CProjectStatusService {

	/**
	 * Save an existing CProjectStatus entity
	 * 
	 */
	public void saveCProjectStatus(CProjectStatus cprojectstatus);

	/**
	 * Return a count of all CProjectStatus entity
	 * 
	 */
	public Integer countCProjectStatuss();

	/**
	 * Return all CProjectStatus entity
	 * 
	 */
	public List<CProjectStatus> findAllCProjectStatuss(Integer startResult, Integer maxRows);

	/**
	 */
	public CProjectStatus findCProjectStatusByPrimaryKey(Integer id);

	/**
	 * Delete an existing CProjectStatus entity
	 * 
	 */
	public void deleteCProjectStatus(CProjectStatus cprojectstatus_1);

	/**
	 * Load an existing CProjectStatus entity
	 * 
	 */
	public Set<CProjectStatus> loadCProjectStatuss();
}