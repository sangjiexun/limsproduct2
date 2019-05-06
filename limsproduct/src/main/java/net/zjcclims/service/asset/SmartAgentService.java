package net.zjcclims.service.asset;

import java.util.List;
import java.util.Set;

import net.zjcclims.domain.SmartAgent;
import net.zjcclims.domain.SmartAgentUser;

/**
 * Spring service that handles CRUD requests for SmartAgent entities
 * 
 */
public interface SmartAgentService {

	/**
	 * Return all SmartAgent entity
	 * 
	 */
	public List<SmartAgent> findAllSmartAgents(Integer startResult, Integer maxRows);

	/**
	 * Save an existing SmartAgent entity
	 * 
	 */
	public void saveSmartAgent(SmartAgent smartagent);

	/**
	 * Delete an existing SmartAgent entity
	 * 
	 */
	public void deleteSmartAgent(SmartAgent smartagent_1);

	/**
	 */
	public SmartAgent findSmartAgentByPrimaryKey(String serialNo);

	/**
	 * Return a count of all SmartAgent entity
	 * 
	 */
	public Integer countSmartAgents();

	/**
	 * Load an existing SmartAgent entity
	 * 
	 */
	public Set<SmartAgent> loadSmartAgents();
	
	public List<String> findUserBySerialNo(String id);
	public List<SmartAgentUser> findSmartAgentUserBySerialNo(String id);
}