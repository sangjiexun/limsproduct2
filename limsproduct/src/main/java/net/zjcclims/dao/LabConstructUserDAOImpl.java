package net.zjcclims.dao;


import net.zjcclims.domain.LabConstructUser;
import org.skyway.spring.util.dao.AbstractJpaDao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * DAO to manage LabConstructUser entities.
 * 
 */
@Repository("LabConstructUserDAO")
@Transactional
public class LabConstructUserDAOImpl extends AbstractJpaDao<LabConstructUser>
		implements LabConstructUserDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabConstructUser.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabConstructUserDAOImpl
	 *
	 */
	public LabConstructUserDAOImpl() {
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
	 * JPQL Query - findLabConstructUserByInfo
	 *
	 */
	@Transactional
	public Set<LabConstructUser> findLabConstructUserByInfo(String info) throws DataAccessException {

		return findLabConstructUserByInfo(info, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructUserByInfo
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructUser> findLabConstructUserByInfo(String info, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructUserByInfo", startResult, maxRows, info);
		return new LinkedHashSet<LabConstructUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructUserByPrimaryKey
	 *
	 */
	@Transactional
	public LabConstructUser findLabConstructUserByPrimaryKey(Integer id) throws DataAccessException {

		return findLabConstructUserByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructUserByPrimaryKey
	 *
	 */

	@Transactional
	public LabConstructUser findLabConstructUserByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabConstructUserByPrimaryKey", startResult, maxRows, id);
			return (LabConstructUser) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabConstructUserById
	 *
	 */
	@Transactional
	public LabConstructUser findLabConstructUserById(Integer id) throws DataAccessException {

		return findLabConstructUserById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructUserById
	 *
	 */

	@Transactional
	public LabConstructUser findLabConstructUserById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabConstructUserById", startResult, maxRows, id);
			return (LabConstructUser) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabConstructUserByResponsibilityContent
	 *
	 */
	@Transactional
	public Set<LabConstructUser> findLabConstructUserByResponsibilityContent(String responsibilityContent) throws DataAccessException {

		return findLabConstructUserByResponsibilityContent(responsibilityContent, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructUserByResponsibilityContent
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructUser> findLabConstructUserByResponsibilityContent(String responsibilityContent, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructUserByResponsibilityContent", startResult, maxRows, responsibilityContent);
		return new LinkedHashSet<LabConstructUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructUserByResponsibilityContentContaining
	 *
	 */
	@Transactional
	public Set<LabConstructUser> findLabConstructUserByResponsibilityContentContaining(String responsibilityContent) throws DataAccessException {

		return findLabConstructUserByResponsibilityContentContaining(responsibilityContent, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructUserByResponsibilityContentContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructUser> findLabConstructUserByResponsibilityContentContaining(String responsibilityContent, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructUserByResponsibilityContentContaining", startResult, maxRows, responsibilityContent);
		return new LinkedHashSet<LabConstructUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabConstructUsers
	 *
	 */
	@Transactional
	public Set<LabConstructUser> findAllLabConstructUsers() throws DataAccessException {

		return findAllLabConstructUsers(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabConstructUsers
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructUser> findAllLabConstructUsers(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabConstructUsers", startResult, maxRows);
		return new LinkedHashSet<LabConstructUser>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(LabConstructUser entity) {
		return true;
	}
}
