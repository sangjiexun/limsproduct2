package net.zjcclims.service.asset;

import java.util.List;
import java.util.Set;

import net.zjcclims.dao.SmartAgentUserDAO;

import net.zjcclims.domain.SmartAgentUser;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

/**
 * Spring service that handles CRUD requests for SmartAgentUser entities
 * 
 */

@Service("SmartAgentUserService")
@Transactional
public class SmartAgentUserServiceImpl implements SmartAgentUserService {

	/**
	 * DAO injected by Spring that manages SmartAgentUser entities
	 * 
	 */
	@Autowired
	private SmartAgentUserDAO smartAgentUserDAO;

	/**
	 * Instantiates a new SmartAgentUserServiceImpl.
	 *
	 */
	public SmartAgentUserServiceImpl() {
	}

	/**
	 * Return a count of all SmartAgentUser entity
	 * 
	 */
	@Transactional
	public Integer countSmartAgentUsers() {
		return ((Long) smartAgentUserDAO.createQuerySingleResult("select count(o) from SmartAgentUser o").getSingleResult()).intValue();
	}

	/**
	 * Save an existing SmartAgentUser entity
	 * 
	 */
	@Transactional
	public void saveSmartAgentUser(SmartAgentUser smartagentuser) {
		SmartAgentUser existingSmartAgentUser = smartAgentUserDAO.findSmartAgentUserByPrimaryKey(smartagentuser.getId());

		if (existingSmartAgentUser != null) {
			if (existingSmartAgentUser != smartagentuser) {
				existingSmartAgentUser.setId(smartagentuser.getId());
				existingSmartAgentUser.setUsername(smartagentuser.getUsername());
				existingSmartAgentUser.setCname(smartagentuser.getCname());
				existingSmartAgentUser.setAcademy(smartagentuser.getAcademy());
				existingSmartAgentUser.setSerialNo(smartagentuser.getSerialNo());
			}
			smartagentuser = smartAgentUserDAO.store(existingSmartAgentUser);
		} else {
			smartagentuser = smartAgentUserDAO.store(smartagentuser);
		}
		smartAgentUserDAO.flush();
	}

	/**
	 * Return all SmartAgentUser entity
	 * 
	 */
	@Transactional
	public List<SmartAgentUser> findAllSmartAgentUsers(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<SmartAgentUser>(smartAgentUserDAO.findAllSmartAgentUsers(startResult, maxRows));
	}

	/**
	 * Delete an existing SmartAgentUser entity
	 * 
	 */
	@Transactional
	public void deleteSmartAgentUser(SmartAgentUser smartagentuser) {
		smartAgentUserDAO.remove(smartagentuser);
		smartAgentUserDAO.flush();
	}

	/**
	 */
	@Transactional
	public SmartAgentUser findSmartAgentUserByPrimaryKey(Integer id) {
		return smartAgentUserDAO.findSmartAgentUserByPrimaryKey(id);
	}

	/**
	 * Load an existing SmartAgentUser entity
	 * 
	 */
	@Transactional
	public Set<SmartAgentUser> loadSmartAgentUsers() {
		return smartAgentUserDAO.findAllSmartAgentUsers();
	}
}
