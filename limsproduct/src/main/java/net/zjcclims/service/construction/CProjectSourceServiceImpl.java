package net.zjcclims.service.construction;


import net.zjcclims.dao.CProjectSourceDAO;
import net.zjcclims.dao.LabConstructAppDAO;
import net.zjcclims.domain.CProjectSource;
import net.zjcclims.domain.LabConstructApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for CProjectSource entities
 * 
 */

@Service("CProjectSourceService")
@Transactional
public class CProjectSourceServiceImpl implements CProjectSourceService {

	/**
	 * DAO injected by Spring that manages CProjectSource entities
	 * 
	 */
	@Autowired
	private CProjectSourceDAO cProjectSourceDAO;

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * Instantiates a new CProjectSourceServiceImpl.
	 *
	 */
	public CProjectSourceServiceImpl() {
	}

	/**
	 * Delete an existing CProjectSource entity
	 * 
	 */
	@Transactional
	public void deleteCProjectSource(CProjectSource cprojectsource) {
		cProjectSourceDAO.remove(cprojectsource);
		cProjectSourceDAO.flush();
	}

	/**
	 * Return a count of all CProjectSource entity
	 * 
	 */
	@Transactional
	public Integer countCProjectSources() {
		return ((Long) cProjectSourceDAO.createQuerySingleResult("select count(o) from CProjectSource o").getSingleResult()).intValue();
	}

	/**
	 */
	@Transactional
	public CProjectSource findCProjectSourceByPrimaryKey(Integer id) {
		return cProjectSourceDAO.findCProjectSourceByPrimaryKey(id);
	}

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public CProjectSource deleteCProjectSourceLabConstructApps(Integer cprojectsource_id, Integer related_labconstructapps_id) {
		LabConstructApp related_labconstructapps = labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapps_id, -1, -1);

		CProjectSource cprojectsource = cProjectSourceDAO.findCProjectSourceByPrimaryKey(cprojectsource_id, -1, -1);

		related_labconstructapps.setCProjectSource(null);
		cprojectsource.getLabConstructApps().remove(related_labconstructapps);

		labConstructAppDAO.remove(related_labconstructapps);
		labConstructAppDAO.flush();

		return cprojectsource;
	}

	/**
	 * Load an existing CProjectSource entity
	 * 
	 */
	@Transactional
	public Set<CProjectSource> loadCProjectSources() {
		return cProjectSourceDAO.findAllCProjectSources();
	}

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public CProjectSource saveCProjectSourceLabConstructApps(Integer id, LabConstructApp related_labconstructapps) {
		CProjectSource cprojectsource = cProjectSourceDAO.findCProjectSourceByPrimaryKey(id, -1, -1);
		LabConstructApp existinglabConstructApps = labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapps.getId());

		// copy into the existing record to preserve existing relationships
		if (existinglabConstructApps != null) {
			existinglabConstructApps.setId(related_labconstructapps.getId());
			existinglabConstructApps.setLabConstructAppCode(related_labconstructapps.getLabConstructAppCode());
			existinglabConstructApps.setProjectName(related_labconstructapps.getProjectName());
			existinglabConstructApps.setPartyId(related_labconstructapps.getPartyId());
			existinglabConstructApps.setAppDate(related_labconstructapps.getAppDate());
			existinglabConstructApps.setParticipant(related_labconstructapps.getParticipant());
			existinglabConstructApps.setPrimaryObjective(related_labconstructapps.getPrimaryObjective());
			existinglabConstructApps.setSpecialInnovation(related_labconstructapps.getSpecialInnovation());
			existinglabConstructApps.setProjectBasis(related_labconstructapps.getProjectBasis());
			existinglabConstructApps.setConstructBasis(related_labconstructapps.getConstructBasis());
			existinglabConstructApps.setExpectedResult(related_labconstructapps.getExpectedResult());
			existinglabConstructApps.setAppropriationBudget(related_labconstructapps.getAppropriationBudget());
			existinglabConstructApps.setEquipmentDetail(related_labconstructapps.getEquipmentDetail());
			existinglabConstructApps.setOpenLabItem(related_labconstructapps.getOpenLabItem());
			existinglabConstructApps.setOtherAppendix(related_labconstructapps.getOtherAppendix());
			existinglabConstructApps.setApprovalAppendix(related_labconstructapps.getApprovalAppendix());
			related_labconstructapps = existinglabConstructApps;
		} else {
			related_labconstructapps = labConstructAppDAO.store(related_labconstructapps);
			labConstructAppDAO.flush();
		}

		related_labconstructapps.setCProjectSource(cprojectsource);
		cprojectsource.getLabConstructApps().add(related_labconstructapps);
		related_labconstructapps = labConstructAppDAO.store(related_labconstructapps);
		labConstructAppDAO.flush();

		cprojectsource = cProjectSourceDAO.store(cprojectsource);
		cProjectSourceDAO.flush();

		return cprojectsource;
	}

	/**
	 * Return all CProjectSource entity
	 * 
	 */
	@Transactional
	public List<CProjectSource> findAllCProjectSources(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<CProjectSource>(cProjectSourceDAO.findAllCProjectSources(startResult, maxRows));
	}

	/**
	 * Save an existing CProjectSource entity
	 * 
	 */
	@Transactional
	public void saveCProjectSource(CProjectSource cprojectsource) {
		CProjectSource existingCProjectSource = cProjectSourceDAO.findCProjectSourceByPrimaryKey(cprojectsource.getId());

		if (existingCProjectSource != null) {
			if (existingCProjectSource != cprojectsource) {
				existingCProjectSource.setId(cprojectsource.getId());
				existingCProjectSource.setName(cprojectsource.getName());
			}
			cprojectsource = cProjectSourceDAO.store(existingCProjectSource);
		} else {
			cprojectsource = cProjectSourceDAO.store(cprojectsource);
		}
		cProjectSourceDAO.flush();
	}
	
	/****************************************************************************
	 * 功能：查询出所有的项目来源类别
	 * 作者：李德
	 * 时间：2015-03-19
	 ****************************************************************************/
	@Override
	public Set<CProjectSource> findAllCProjectSource() {
		// TODO Auto-generated method stub
		return cProjectSourceDAO.findAllCProjectSources();
	}
	
	/****************************************************************************
	 * 功能：查询出所有的项目来源
	 * 作者：李德
	 * 时间：2015-05-08
	 ****************************************************************************/
	@Override
	public List<CProjectSource> findAllCProjectSourceByCProjectSource(CProjectSource cProjectSource) {
		// TODO Auto-generated method stub
		String sql="select c from CProjectSource c where 1=1";		
		sql+=" order by c.id desc";
		return cProjectSourceDAO.executeQuery(sql,0,-1);
	}
	/****************************************************************************
	 * 功能：查询出所有的项目来源
	 * 作者：李德
	 * 时间：2015-05-08
	 ****************************************************************************/
	@Override
	public List<CProjectSource> findAllCProjectSourceByCProjectSource(CProjectSource cProjectSource,
			int page, int pageSize) {
		// TODO Auto-generated method stub
		String sql="select c from CProjectSource c where 1=1";

		sql+=" order by c.id asc";
		return cProjectSourceDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
	}
	
	/****************************************************************************
	 * 功能：保存实验室对象
	 * 作者：李德
	 * 时间：2015-05-08
	 ****************************************************************************/
	@Override
	public CProjectSource save(CProjectSource cProjectSource) {
		// TODO Auto-generated method stub
		return cProjectSourceDAO.store(cProjectSource);
	}	
	
}
