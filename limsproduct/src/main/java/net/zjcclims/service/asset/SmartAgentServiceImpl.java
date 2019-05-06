package net.zjcclims.service.asset;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.zjcclims.dao.SmartAgentDAO;
import net.zjcclims.dao.SmartAgentUserDAO;

import net.zjcclims.domain.SmartAgent;
import net.zjcclims.domain.SmartAgentUser;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

/**
 * Spring service that handles CRUD requests for SmartAgent entities
 * 
 */

@Service("SmartAgentService")
@Transactional
public class SmartAgentServiceImpl implements SmartAgentService {

	/**
	 * DAO injected by Spring that manages SmartAgent entities
	 * 
	 */
	@Autowired
	private SmartAgentDAO smartAgentDAO;
	
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private SmartAgentUserDAO smartAgentUserDAO;

	/**
	 * Instantiates a new SmartAgentServiceImpl.
	 *
	 */
	public SmartAgentServiceImpl() {
	}

	/**
	 * Return all SmartAgent entity
	 * 
	 */
	@Transactional
	public List<SmartAgent> findAllSmartAgents(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<SmartAgent>(smartAgentDAO.findAllSmartAgents(startResult, maxRows));
	}

	/**
	 * Save an existing SmartAgent entity
	 * 
	 */
	@Transactional
	public void saveSmartAgent(SmartAgent smartagent) {
		SmartAgent existingSmartAgent = smartAgentDAO.findSmartAgentByPrimaryKey(smartagent.getSerialNo());

		if (existingSmartAgent != null) {
			if (existingSmartAgent != smartagent) {
				existingSmartAgent.setSerialNo(smartagent.getSerialNo());
				existingSmartAgent.setCurrIp(smartagent.getCurrIp());
				existingSmartAgent.setDbhost(smartagent.getDbhost());
				existingSmartAgent.setDeviceNumber(smartagent.getDeviceNumber());
				existingSmartAgent.setDeviceName(smartagent.getDeviceName());
				existingSmartAgent.setLabId(smartagent.getLabId());
				existingSmartAgent.setLabName(smartagent.getLabName());
				existingSmartAgent.setRemark(smartagent.getRemark());
			}
			smartagent = smartAgentDAO.store(existingSmartAgent);
		} else {
			smartagent = smartAgentDAO.store(smartagent);
		}
		smartAgentDAO.flush();
	}

	/**
	 * Delete an existing SmartAgent entity
	 * 
	 */
	@Transactional
	public void deleteSmartAgent(SmartAgent smartagent) {
		smartAgentDAO.remove(smartagent);
		smartAgentDAO.flush();
	}

	/**
	 */
	@Transactional
	public SmartAgent findSmartAgentByPrimaryKey(String serialNo) {
		return smartAgentDAO.findSmartAgentByPrimaryKey(serialNo);
	}

	/**
	 * Return a count of all SmartAgent entity
	 * 
	 */
	@Transactional
	public Integer countSmartAgents() {
		return ((Long) smartAgentDAO.createQuerySingleResult("select count(o) from SmartAgent o").getSingleResult()).intValue();
	}

	/**
	 * Load an existing SmartAgent entity
	 * 
	 */
	@Transactional
	public Set<SmartAgent> loadSmartAgents() {
		return smartAgentDAO.findAllSmartAgents();
	}

	@Override
	public List<String> findUserBySerialNo(String id) {
		String sql="select username,cname,serial_no from smart_agent_user where serial_no="+id;
		List<String> userList = entityManager.createNativeQuery(sql).getResultList();
		return userList;
	}

	@Override
	public List<SmartAgentUser> findSmartAgentUserBySerialNo(String id) {
		String sql="select l from SmartAgentUser l where 1=1";
		if(id!=null){
			sql+=" and l.serialNo like '"+id+"'";
		}
		return smartAgentUserDAO.executeQuery(sql, 0, -1);
	}
}
