package net.zjcclims.service.construction;


import net.zjcclims.dao.CProjectPurposeDAO;
import net.zjcclims.dao.LabConstructAppDAO;
import net.zjcclims.domain.CProjectPurpose;
import net.zjcclims.domain.LabConstructApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for CProjectPurpose entities
 * 
 */

@Service("CProjectPurposeService")
@Transactional
public class CProjectPurposeServiceImpl implements CProjectPurposeService {

	/**
	 * DAO injected by Spring that manages CProjectPurpose entities
	 * 
	 */
	@Autowired
	private CProjectPurposeDAO cProjectPurposeDAO;

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * Instantiates a new CProjectPurposeServiceImpl.
	 *
	 */
	public CProjectPurposeServiceImpl() {
	}

	/**
	 * Delete an existing CProjectPurpose entity
	 * 
	 */
	@Transactional
	public void deleteCProjectPurpose(CProjectPurpose cprojectpurpose) {
		cProjectPurposeDAO.remove(cprojectpurpose);
		cProjectPurposeDAO.flush();
	}

	/**
	 * Return all CProjectPurpose entity
	 * 
	 */
	@Transactional
	public List<CProjectPurpose> findAllCProjectPurposes(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<CProjectPurpose>(cProjectPurposeDAO.findAllCProjectPurposes(startResult, maxRows));
	}

	/**
	 * Save an existing CProjectPurpose entity
	 * 
	 */
	@Transactional
	public void saveCProjectPurpose(CProjectPurpose cprojectpurpose) {
		CProjectPurpose existingCProjectPurpose = cProjectPurposeDAO.findCProjectPurposeByPrimaryKey(cprojectpurpose.getId());

		if (existingCProjectPurpose != null) {
			if (existingCProjectPurpose != cprojectpurpose) {
				existingCProjectPurpose.setId(cprojectpurpose.getId());
				existingCProjectPurpose.setName(cprojectpurpose.getName());
			}
			cprojectpurpose = cProjectPurposeDAO.store(existingCProjectPurpose);
		} else {
			cprojectpurpose = cProjectPurposeDAO.store(cprojectpurpose);
		}
		cProjectPurposeDAO.flush();
	}

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public CProjectPurpose deleteCProjectPurposeLabConstructApps(Integer cprojectpurpose_id, Integer related_labconstructapps_id) {
		LabConstructApp related_labconstructapps = labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapps_id, -1, -1);

		CProjectPurpose cprojectpurpose = cProjectPurposeDAO.findCProjectPurposeByPrimaryKey(cprojectpurpose_id, -1, -1);

		related_labconstructapps.setCProjectPurpose(null);
		cprojectpurpose.getLabConstructApps().remove(related_labconstructapps);

		labConstructAppDAO.remove(related_labconstructapps);
		labConstructAppDAO.flush();

		return cprojectpurpose;
	}

	/**
	 * Return a count of all CProjectPurpose entity
	 * 
	 */
	@Transactional
	public Integer countCProjectPurposes() {
		return ((Long) cProjectPurposeDAO.createQuerySingleResult("select count(o) from CProjectPurpose o").getSingleResult()).intValue();
	}

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public CProjectPurpose saveCProjectPurposeLabConstructApps(Integer id, LabConstructApp related_labconstructapps) {
		CProjectPurpose cprojectpurpose = cProjectPurposeDAO.findCProjectPurposeByPrimaryKey(id, -1, -1);
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

		related_labconstructapps.setCProjectPurpose(cprojectpurpose);
		cprojectpurpose.getLabConstructApps().add(related_labconstructapps);
		related_labconstructapps = labConstructAppDAO.store(related_labconstructapps);
		labConstructAppDAO.flush();

		cprojectpurpose = cProjectPurposeDAO.store(cprojectpurpose);
		cProjectPurposeDAO.flush();

		return cprojectpurpose;
	}

	/**
	 * Load an existing CProjectPurpose entity
	 * 
	 */
	@Transactional
	public Set<CProjectPurpose> loadCProjectPurposes() {
		return cProjectPurposeDAO.findAllCProjectPurposes();
	}

	/**
	 */
	@Transactional
	public CProjectPurpose findCProjectPurposeByPrimaryKey(Integer id) {
		return cProjectPurposeDAO.findCProjectPurposeByPrimaryKey(id);
	}
	
	/****************************************************************************
	 * 功能：查询出所有的项目来源类别
	 * 作者：李德
	 * 时间：2015-03-19
	 ****************************************************************************/
	@Override
	public Set<CProjectPurpose> findAllCProjectPurpose() {
		// TODO Auto-generated method stub
		return cProjectPurposeDAO.findAllCProjectPurposes();
	}
	
	/****************************************************************************
	 * 功能：查询出所有的用途
	 * 作者：李德
	 * 时间：2015-05-11
	 ****************************************************************************/
	@Override
	public List<CProjectPurpose> findAllCProjectPurposeByCProjectPurpose(CProjectPurpose cProjectPurpose) {
		// TODO Auto-generated method stub
		String sql="select c from CProjectPurpose c where 1=1";		
		sql+=" order by c.id desc";
		return cProjectPurposeDAO.executeQuery(sql,0,-1);
	}
	/****************************************************************************
	 * 功能：查询出所有的用途
	 * 作者：李德
	 * 时间：2015-05-11
	 ****************************************************************************/
	@Override
	public List<CProjectPurpose> findAllCProjectPurposeByCProjectPurpose(CProjectPurpose cProjectPurpose,
			int page, int pageSize) {
		// TODO Auto-generated method stub
		String sql="select c from CProjectPurpose c where 1=1";

		sql+=" order by c.id asc";
		return cProjectPurposeDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
	}
	
	/****************************************************************************
	 * 功能：保存实验室对象
	 * 作者：李德
	 * 时间：2015-05-11
	 ****************************************************************************/
	@Override
	public CProjectPurpose save(CProjectPurpose cProjectPurpose) {
		// TODO Auto-generated method stub
		return cProjectPurposeDAO.store(cProjectPurpose);
	}

}
