package net.zjcclims.service.asset;

import java.util.List;
import java.util.Set;

import net.zjcclims.domain.SmartAgentUser;

/**
 * Spring service that handles CRUD requests for SmartAgentUser entities
 * 
 */
public interface SmartAgentUserService {

	/**
	 * Return a count of all SmartAgentUser entity
	 * 
	 */
	public Integer countSmartAgentUsers();

	/**
	 * Save an existing SmartAgentUser entity
	 * 
	 */
	public void saveSmartAgentUser(SmartAgentUser smartagentuser);

	/**
	 * Return all SmartAgentUser entity
	 * 
	 */
	public List<SmartAgentUser> findAllSmartAgentUsers(Integer startResult, Integer maxRows);

	/**
	 * Delete an existing SmartAgentUser entity
	 * 
	 */
	public void deleteSmartAgentUser(SmartAgentUser smartagentuser_1);

	/**
	 */
	public SmartAgentUser findSmartAgentUserByPrimaryKey(Integer id);

	/**
	 * Load an existing SmartAgentUser entity
	 * 
	 */
	public Set<SmartAgentUser> loadSmartAgentUsers();
}