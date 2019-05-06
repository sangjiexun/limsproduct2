package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.CreditOption;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage CreditOption entities.
 * 
 */
@Repository("CreditOptionDAO")
@Transactional
public class CreditOptionDAOImpl extends AbstractJpaDao<CreditOption> implements
		CreditOptionDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { CreditOption.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new CreditOptionDAOImpl
	 *
	 */
	public CreditOptionDAOImpl() {
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
	 * JPQL Query - findCreditOptionByStatus
	 *
	 */
	@Transactional
	public Set<CreditOption> findCreditOptionByStatus(Integer status) throws DataAccessException {

		return findCreditOptionByStatus(status, -1, -1);
	}

	/**
	 * JPQL Query - findCreditOptionByStatus
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CreditOption> findCreditOptionByStatus(Integer status, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findCreditOptionByStatus", startResult, maxRows, status);
		return new LinkedHashSet<CreditOption>(query.getResultList());
	}

	/**
	 * JPQL Query - findCreditOptionByNameContaining
	 *
	 */
	@Transactional
	public Set<CreditOption> findCreditOptionByNameContaining(String name) throws DataAccessException {

		return findCreditOptionByNameContaining(name, -1, -1);
	}

	/**
	 * JPQL Query - findCreditOptionByNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CreditOption> findCreditOptionByNameContaining(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findCreditOptionByNameContaining", startResult, maxRows, name);
		return new LinkedHashSet<CreditOption>(query.getResultList());
	}

	/**
	 * JPQL Query - findCreditOptionByEnabled
	 *
	 */
	@Transactional
	public Set<CreditOption> findCreditOptionByEnabled(Integer enabled) throws DataAccessException {

		return findCreditOptionByEnabled(enabled, -1, -1);
	}

	/**
	 * JPQL Query - findCreditOptionByEnabled
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CreditOption> findCreditOptionByEnabled(Integer enabled, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findCreditOptionByEnabled", startResult, maxRows, enabled);
		return new LinkedHashSet<CreditOption>(query.getResultList());
	}

	/**
	 * JPQL Query - findCreditOptionByName
	 *
	 */
	@Transactional
	public Set<CreditOption> findCreditOptionByName(String name) throws DataAccessException {

		return findCreditOptionByName(name, -1, -1);
	}

	/**
	 * JPQL Query - findCreditOptionByName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CreditOption> findCreditOptionByName(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findCreditOptionByName", startResult, maxRows, name);
		return new LinkedHashSet<CreditOption>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllCreditOptions
	 *
	 */
	@Transactional
	public Set<CreditOption> findAllCreditOptions() throws DataAccessException {

		return findAllCreditOptions(-1, -1);
	}

	/**
	 * JPQL Query - findAllCreditOptions
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CreditOption> findAllCreditOptions(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllCreditOptions", startResult, maxRows);
		return new LinkedHashSet<CreditOption>(query.getResultList());
	}

	/**
	 * JPQL Query - findCreditOptionByMemo
	 *
	 */
	@Transactional
	public Set<CreditOption> findCreditOptionByMemo(String memo) throws DataAccessException {

		return findCreditOptionByMemo(memo, -1, -1);
	}

	/**
	 * JPQL Query - findCreditOptionByMemo
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CreditOption> findCreditOptionByMemo(String memo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findCreditOptionByMemo", startResult, maxRows, memo);
		return new LinkedHashSet<CreditOption>(query.getResultList());
	}

	/**
	 * JPQL Query - findCreditOptionById
	 *
	 */
	@Transactional
	public CreditOption findCreditOptionById(Integer id) throws DataAccessException {

		return findCreditOptionById(id, -1, -1);
	}

	/**
	 * JPQL Query - findCreditOptionById
	 *
	 */

	@Transactional
	public CreditOption findCreditOptionById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findCreditOptionById", startResult, maxRows, id);
			return (net.zjcclims.domain.CreditOption) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findCreditOptionByCreaterContaining
	 *
	 */
	@Transactional
	public Set<CreditOption> findCreditOptionByCreaterContaining(String creater) throws DataAccessException {

		return findCreditOptionByCreaterContaining(creater, -1, -1);
	}

	/**
	 * JPQL Query - findCreditOptionByCreaterContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CreditOption> findCreditOptionByCreaterContaining(String creater, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findCreditOptionByCreaterContaining", startResult, maxRows, creater);
		return new LinkedHashSet<CreditOption>(query.getResultList());
	}

	/**
	 * JPQL Query - findCreditOptionByCreateDate
	 *
	 */
	@Transactional
	public Set<CreditOption> findCreditOptionByCreateDate(java.util.Calendar createDate) throws DataAccessException {

		return findCreditOptionByCreateDate(createDate, -1, -1);
	}

	/**
	 * JPQL Query - findCreditOptionByCreateDate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CreditOption> findCreditOptionByCreateDate(java.util.Calendar createDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findCreditOptionByCreateDate", startResult, maxRows, createDate);
		return new LinkedHashSet<CreditOption>(query.getResultList());
	}

	/**
	 * JPQL Query - findCreditOptionByDeduction
	 *
	 */
	@Transactional
	public Set<CreditOption> findCreditOptionByDeduction(Integer deduction) throws DataAccessException {

		return findCreditOptionByDeduction(deduction, -1, -1);
	}

	/**
	 * JPQL Query - findCreditOptionByDeduction
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CreditOption> findCreditOptionByDeduction(Integer deduction, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findCreditOptionByDeduction", startResult, maxRows, deduction);
		return new LinkedHashSet<CreditOption>(query.getResultList());
	}

	/**
	 * JPQL Query - findCreditOptionByPrimaryKey
	 *
	 */
	@Transactional
	public CreditOption findCreditOptionByPrimaryKey(Integer id) throws DataAccessException {

		return findCreditOptionByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findCreditOptionByPrimaryKey
	 *
	 */

	@Transactional
	public CreditOption findCreditOptionByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findCreditOptionByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.CreditOption) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findCreditOptionByMemoContaining
	 *
	 */
	@Transactional
	public Set<CreditOption> findCreditOptionByMemoContaining(String memo) throws DataAccessException {

		return findCreditOptionByMemoContaining(memo, -1, -1);
	}

	/**
	 * JPQL Query - findCreditOptionByMemoContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<CreditOption> findCreditOptionByMemoContaining(String memo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findCreditOptionByMemoContaining", startResult, maxRows, memo);
		return new LinkedHashSet<CreditOption>(query.getResultList());
	}

	/**
	 * JPQL Query - findCreditOptionByCreater
	 *
	 */
	/*@Transactional
	public Set<CreditOption> findCreditOptionByCreater(String creater) throws DataAccessException {

		return findCreditOptionByCreater(creater, -1, -1);
	}*/

	/**
	 * JPQL Query - findCreditOptionByCreater
	 *
	 */

	/*@SuppressWarnings("unchecked")
	@Transactional
	public Set<CreditOption> findCreditOptionByCreater(String creater, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findCreditOptionByCreater", startResult, maxRows, creater);
		return new LinkedHashSet<CreditOption>(query.getResultList());
	}*/

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(CreditOption entity) {
		return true;
	}
}
