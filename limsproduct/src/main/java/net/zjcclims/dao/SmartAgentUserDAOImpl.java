package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.SmartAgentUser;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage SmartAgentUser entities.
 * 
 */
@Repository("SmartAgentUserDAO")
@Transactional
public class SmartAgentUserDAOImpl extends AbstractJpaDao<SmartAgentUser>
		implements SmartAgentUserDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { SmartAgentUser.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new SmartAgentUserDAOImpl
	 *
	 */
	public SmartAgentUserDAOImpl() {
		super();
	}

	/**
	 * Get the entity manager that manages persistence unit 
	 *
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Returns the set of entity classes managed by this DAO.
	 *
	 */
	public Set<Class<?>> getTypes() {
		return dataTypes;
	}

	/**
	 * JPQL Query - findSmartAgentUserById
	 *
	 */
	@Transactional
	public SmartAgentUser findSmartAgentUserById(Integer id) throws DataAccessException {

		return findSmartAgentUserById(id, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentUserById
	 *
	 */

	@Transactional
	public SmartAgentUser findSmartAgentUserById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSmartAgentUserById", startResult, maxRows, id);
			return (net.zjcclims.domain.SmartAgentUser) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllSmartAgentUsers
	 *
	 */
	@Transactional
	public Set<SmartAgentUser> findAllSmartAgentUsers() throws DataAccessException {

		return findAllSmartAgentUsers(-1, -1);
	}

	/**
	 * JPQL Query - findAllSmartAgentUsers
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgentUser> findAllSmartAgentUsers(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllSmartAgentUsers", startResult, maxRows);
		return new LinkedHashSet<SmartAgentUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findSmartAgentUserByPrimaryKey
	 *
	 */
	@Transactional
	public SmartAgentUser findSmartAgentUserByPrimaryKey(Integer id) throws DataAccessException {

		return findSmartAgentUserByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentUserByPrimaryKey
	 *
	 */

	@Transactional
	public SmartAgentUser findSmartAgentUserByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSmartAgentUserByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.SmartAgentUser) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findSmartAgentUserByAcademyContaining
	 *
	 */
	@Transactional
	public Set<SmartAgentUser> findSmartAgentUserByAcademyContaining(String academy) throws DataAccessException {

		return findSmartAgentUserByAcademyContaining(academy, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentUserByAcademyContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgentUser> findSmartAgentUserByAcademyContaining(String academy, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentUserByAcademyContaining", startResult, maxRows, academy);
		return new LinkedHashSet<SmartAgentUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findSmartAgentUserByCnameContaining
	 *
	 */
	@Transactional
	public Set<SmartAgentUser> findSmartAgentUserByCnameContaining(String cname) throws DataAccessException {

		return findSmartAgentUserByCnameContaining(cname, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentUserByCnameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgentUser> findSmartAgentUserByCnameContaining(String cname, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentUserByCnameContaining", startResult, maxRows, cname);
		return new LinkedHashSet<SmartAgentUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findSmartAgentUserBySerialNo
	 *
	 */
	@Transactional
	public Set<SmartAgentUser> findSmartAgentUserBySerialNo(String serialNo) throws DataAccessException {

		return findSmartAgentUserBySerialNo(serialNo, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentUserBySerialNo
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgentUser> findSmartAgentUserBySerialNo(String serialNo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentUserBySerialNo", startResult, maxRows, serialNo);
		return new LinkedHashSet<SmartAgentUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findSmartAgentUserByAcademy
	 *
	 */
	@Transactional
	public Set<SmartAgentUser> findSmartAgentUserByAcademy(String academy) throws DataAccessException {

		return findSmartAgentUserByAcademy(academy, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentUserByAcademy
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgentUser> findSmartAgentUserByAcademy(String academy, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentUserByAcademy", startResult, maxRows, academy);
		return new LinkedHashSet<SmartAgentUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findSmartAgentUserByCname
	 *
	 */
	@Transactional
	public Set<SmartAgentUser> findSmartAgentUserByCname(String cname) throws DataAccessException {

		return findSmartAgentUserByCname(cname, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentUserByCname
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgentUser> findSmartAgentUserByCname(String cname, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentUserByCname", startResult, maxRows, cname);
		return new LinkedHashSet<SmartAgentUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findSmartAgentUserByUsername
	 *
	 */
	@Transactional
	public Set<SmartAgentUser> findSmartAgentUserByUsername(String username) throws DataAccessException {

		return findSmartAgentUserByUsername(username, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentUserByUsername
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgentUser> findSmartAgentUserByUsername(String username, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentUserByUsername", startResult, maxRows, username);
		return new LinkedHashSet<SmartAgentUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findSmartAgentUserByUsernameContaining
	 *
	 */
	@Transactional
	public Set<SmartAgentUser> findSmartAgentUserByUsernameContaining(String username) throws DataAccessException {

		return findSmartAgentUserByUsernameContaining(username, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentUserByUsernameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgentUser> findSmartAgentUserByUsernameContaining(String username, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentUserByUsernameContaining", startResult, maxRows, username);
		return new LinkedHashSet<SmartAgentUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findSmartAgentUserBySerialNoContaining
	 *
	 */
	@Transactional
	public Set<SmartAgentUser> findSmartAgentUserBySerialNoContaining(String serialNo) throws DataAccessException {

		return findSmartAgentUserBySerialNoContaining(serialNo, -1, -1);
	}

	/**
	 * JPQL Query - findSmartAgentUserBySerialNoContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SmartAgentUser> findSmartAgentUserBySerialNoContaining(String serialNo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSmartAgentUserBySerialNoContaining", startResult, maxRows, serialNo);
		return new LinkedHashSet<SmartAgentUser>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(SmartAgentUser entity) {
		return true;
	}
}
