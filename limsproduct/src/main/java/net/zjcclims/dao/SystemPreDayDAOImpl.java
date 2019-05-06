package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.SystemPreDay;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage SystemPreDay entities.
 * 
 */
@Repository("SystemPreDayDAO")
@Transactional
public class SystemPreDayDAOImpl extends AbstractJpaDao<SystemPreDay> implements
		SystemPreDayDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { SystemPreDay.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new SystemPreDayDAOImpl
	 *
	 */
	public SystemPreDayDAOImpl() {
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
	 * JPQL Query - findSystemPreDayByDayNum
	 *
	 */
	@Transactional
	public Set<SystemPreDay> findSystemPreDayByDayNum(Integer dayNum) throws DataAccessException {

		return findSystemPreDayByDayNum(dayNum, -1, -1);
	}

	/**
	 * JPQL Query - findSystemPreDayByDayNum
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SystemPreDay> findSystemPreDayByDayNum(Integer dayNum, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSystemPreDayByDayNum", startResult, maxRows, dayNum);
		return new LinkedHashSet<SystemPreDay>(query.getResultList());
	}

	/**
	 * JPQL Query - findSystemPreDayById
	 *
	 */
	@Transactional
	public SystemPreDay findSystemPreDayById(Integer id) throws DataAccessException {

		return findSystemPreDayById(id, -1, -1);
	}

	/**
	 * JPQL Query - findSystemPreDayById
	 *
	 */

	@Transactional
	public SystemPreDay findSystemPreDayById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSystemPreDayById", startResult, maxRows, id);
			return (net.zjcclims.domain.SystemPreDay) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllSystemPreDays
	 *
	 */
	@Transactional
	public Set<SystemPreDay> findAllSystemPreDays() throws DataAccessException {

		return findAllSystemPreDays(-1, -1);
	}

	/**
	 * JPQL Query - findAllSystemPreDays
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SystemPreDay> findAllSystemPreDays(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllSystemPreDays", startResult, maxRows);
		return new LinkedHashSet<SystemPreDay>(query.getResultList());
	}

	/**
	 * JPQL Query - findSystemPreDayByPrimaryKey
	 *
	 */
	@Transactional
	public SystemPreDay findSystemPreDayByPrimaryKey(Integer id) throws DataAccessException {

		return findSystemPreDayByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findSystemPreDayByPrimaryKey
	 *
	 */

	@Transactional
	public SystemPreDay findSystemPreDayByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSystemPreDayByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.SystemPreDay) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(SystemPreDay entity) {
		return true;
	}
}
