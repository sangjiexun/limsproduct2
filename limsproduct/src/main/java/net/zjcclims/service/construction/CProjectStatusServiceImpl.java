package net.zjcclims.service.construction;


import net.zjcclims.dao.CProjectStatusDAO;
import net.zjcclims.domain.CProjectStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for CProjectStatus entities
 * 
 */

@Service("CProjectStatusService")
@Transactional
public class CProjectStatusServiceImpl implements CProjectStatusService {

	/**
	 * DAO injected by Spring that manages CProjectStatus entities
	 * 
	 */
	@Autowired
	private CProjectStatusDAO cProjectStatusDAO;

	/**
	 * Instantiates a new CProjectStatusServiceImpl.
	 *
	 */
	public CProjectStatusServiceImpl() {
	}

	/**
	 * Save an existing CProjectStatus entity
	 * 
	 */
	@Transactional
	public void saveCProjectStatus(CProjectStatus cprojectstatus) {
		CProjectStatus existingCProjectStatus = cProjectStatusDAO.findCProjectStatusByPrimaryKey(cprojectstatus.getId());

		if (existingCProjectStatus != null) {
			if (existingCProjectStatus != cprojectstatus) {
				existingCProjectStatus.setId(cprojectstatus.getId());
				existingCProjectStatus.setName(cprojectstatus.getName());
			}
			cprojectstatus = cProjectStatusDAO.store(existingCProjectStatus);
		} else {
			cprojectstatus = cProjectStatusDAO.store(cprojectstatus);
		}
		cProjectStatusDAO.flush();
	}

	/**
	 * Return a count of all CProjectStatus entity
	 * 
	 */
	@Transactional
	public Integer countCProjectStatuss() {
		return ((Long) cProjectStatusDAO.createQuerySingleResult("select count(o) from CProjectStatus o").getSingleResult()).intValue();
	}

	/**
	 * Return all CProjectStatus entity
	 * 
	 */
	@Transactional
	public List<CProjectStatus> findAllCProjectStatuss(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<CProjectStatus>(cProjectStatusDAO.findAllCProjectStatuss(startResult, maxRows));
	}

	/**
	 */
	@Transactional
	public CProjectStatus findCProjectStatusByPrimaryKey(Integer id) {
		return cProjectStatusDAO.findCProjectStatusByPrimaryKey(id);
	}

	/**
	 * Delete an existing CProjectStatus entity
	 * 
	 */
	@Transactional
	public void deleteCProjectStatus(CProjectStatus cprojectstatus) {
		cProjectStatusDAO.remove(cprojectstatus);
		cProjectStatusDAO.flush();
	}

	/**
	 * Load an existing CProjectStatus entity
	 * 
	 */
	@Transactional
	public Set<CProjectStatus> loadCProjectStatuss() {
		return cProjectStatusDAO.findAllCProjectStatuss();
	}
}
