package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.IndividualDictionary;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage IndividualDictionary entities.
 * 
 */
@Repository("IndividualDictionaryDAO")
@Transactional
public class IndividualDictionaryDAOImpl extends AbstractJpaDao<IndividualDictionary>
		implements IndividualDictionaryDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { IndividualDictionary.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new IndividualDictionaryDAOImpl
	 *
	 */
	public IndividualDictionaryDAOImpl() {
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
	 * JPQL Query - findIndividualDictionaryByAuditStatus
	 *
	 */
	@Transactional
	public Set<IndividualDictionary> findIndividualDictionaryByAuditStatus(Integer auditStatus) throws DataAccessException {

		return findIndividualDictionaryByAuditStatus(auditStatus, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualDictionaryByAuditStatus
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualDictionary> findIndividualDictionaryByAuditStatus(Integer auditStatus, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualDictionaryByAuditStatus", startResult, maxRows, auditStatus);
		return new LinkedHashSet<IndividualDictionary>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualDictionaryByCreater
	 *
	 */
	@Transactional
	public Set<IndividualDictionary> findIndividualDictionaryByCreater(String creater) throws DataAccessException {

		return findIndividualDictionaryByCreater(creater, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualDictionaryByCreater
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualDictionary> findIndividualDictionaryByCreater(String creater, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualDictionaryByCreater", startResult, maxRows, creater);
		return new LinkedHashSet<IndividualDictionary>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllIndividualDictionarys
	 *
	 */
	@Transactional
	public Set<IndividualDictionary> findAllIndividualDictionarys() throws DataAccessException {

		return findAllIndividualDictionarys(-1, -1);
	}

	/**
	 * JPQL Query - findAllIndividualDictionarys
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualDictionary> findAllIndividualDictionarys(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllIndividualDictionarys", startResult, maxRows);
		return new LinkedHashSet<IndividualDictionary>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualDictionaryByPrimaryKey
	 *
	 */
	@Transactional
	public IndividualDictionary findIndividualDictionaryByPrimaryKey(Integer id) throws DataAccessException {

		return findIndividualDictionaryByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualDictionaryByPrimaryKey
	 *
	 */

	@Transactional
	public IndividualDictionary findIndividualDictionaryByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findIndividualDictionaryByPrimaryKey", startResult, maxRows, id);
			return (IndividualDictionary) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findIndividualDictionaryByCreaterContaining
	 *
	 */
	@Transactional
	public Set<IndividualDictionary> findIndividualDictionaryByCreaterContaining(String creater) throws DataAccessException {

		return findIndividualDictionaryByCreaterContaining(creater, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualDictionaryByCreaterContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualDictionary> findIndividualDictionaryByCreaterContaining(String creater, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualDictionaryByCreaterContaining", startResult, maxRows, creater);
		return new LinkedHashSet<IndividualDictionary>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualDictionaryById
	 *
	 */
	@Transactional
	public IndividualDictionary findIndividualDictionaryById(Integer id) throws DataAccessException {

		return findIndividualDictionaryById(id, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualDictionaryById
	 *
	 */

	@Transactional
	public IndividualDictionary findIndividualDictionaryById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findIndividualDictionaryById", startResult, maxRows, id);
			return (IndividualDictionary) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findIndividualDictionaryByName
	 *
	 */
	@Transactional
	public Set<IndividualDictionary> findIndividualDictionaryByName(String name) throws DataAccessException {

		return findIndividualDictionaryByName(name, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualDictionaryByName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualDictionary> findIndividualDictionaryByName(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualDictionaryByName", startResult, maxRows, name);
		return new LinkedHashSet<IndividualDictionary>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualDictionaryByCreateDate
	 *
	 */
	@Transactional
	public Set<IndividualDictionary> findIndividualDictionaryByCreateDate(java.util.Calendar createDate) throws DataAccessException {

		return findIndividualDictionaryByCreateDate(createDate, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualDictionaryByCreateDate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualDictionary> findIndividualDictionaryByCreateDate(java.util.Calendar createDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualDictionaryByCreateDate", startResult, maxRows, createDate);
		return new LinkedHashSet<IndividualDictionary>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualDictionaryByType
	 *
	 */
	@Transactional
	public Set<IndividualDictionary> findIndividualDictionaryByType(Integer type) throws DataAccessException {

		return findIndividualDictionaryByType(type, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualDictionaryByType
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualDictionary> findIndividualDictionaryByType(Integer type, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualDictionaryByType", startResult, maxRows, type);
		return new LinkedHashSet<IndividualDictionary>(query.getResultList());
	}

	/**
	 * JPQL Query - findIndividualDictionaryByNameContaining
	 *
	 */
	@Transactional
	public Set<IndividualDictionary> findIndividualDictionaryByNameContaining(String name) throws DataAccessException {

		return findIndividualDictionaryByNameContaining(name, -1, -1);
	}

	/**
	 * JPQL Query - findIndividualDictionaryByNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<IndividualDictionary> findIndividualDictionaryByNameContaining(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findIndividualDictionaryByNameContaining", startResult, maxRows, name);
		return new LinkedHashSet<IndividualDictionary>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(IndividualDictionary entity) {
		return true;
	}
}
