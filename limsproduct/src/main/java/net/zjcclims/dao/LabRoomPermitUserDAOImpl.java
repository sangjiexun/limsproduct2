package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.LabRoomPermitUser;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage LabRoomPermitUser entities.
 * 
 */
@Repository("LabRoomPermitUserDAO")
@Transactional
public class LabRoomPermitUserDAOImpl extends AbstractJpaDao<LabRoomPermitUser>
		implements LabRoomPermitUserDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabRoomPermitUser.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabRoomPermitUserDAOImpl
	 *
	 */
	public LabRoomPermitUserDAOImpl() {
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
	 * JPQL Query - findLabRoomPermitUserByPrimaryKey
	 *
	 */
	@Transactional
	public LabRoomPermitUser findLabRoomPermitUserByPrimaryKey(Integer id) throws DataAccessException {

		return findLabRoomPermitUserByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomPermitUserByPrimaryKey
	 *
	 */

	@Transactional
	public LabRoomPermitUser findLabRoomPermitUserByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomPermitUserByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomPermitUser) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabRoomPermitUserByFlag
	 *
	 */
	@Transactional
	public Set<LabRoomPermitUser> findLabRoomPermitUserByFlag(Integer flag) throws DataAccessException {

		return findLabRoomPermitUserByFlag(flag, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomPermitUserByFlag
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomPermitUser> findLabRoomPermitUserByFlag(Integer flag, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomPermitUserByFlag", startResult, maxRows, flag);
		return new LinkedHashSet<LabRoomPermitUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomPermitUserById
	 *
	 */
	@Transactional
	public LabRoomPermitUser findLabRoomPermitUserById(Integer id) throws DataAccessException {

		return findLabRoomPermitUserById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomPermitUserById
	 *
	 */

	@Transactional
	public LabRoomPermitUser findLabRoomPermitUserById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomPermitUserById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomPermitUser) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllLabRoomPermitUsers
	 *
	 */
	@Transactional
	public Set<LabRoomPermitUser> findAllLabRoomPermitUsers() throws DataAccessException {

		return findAllLabRoomPermitUsers(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabRoomPermitUsers
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomPermitUser> findAllLabRoomPermitUsers(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabRoomPermitUsers", startResult, maxRows);
		return new LinkedHashSet<LabRoomPermitUser>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(LabRoomPermitUser entity) {
		return true;
	}
}
