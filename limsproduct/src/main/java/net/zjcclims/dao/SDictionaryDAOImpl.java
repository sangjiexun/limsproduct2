package net.zjcclims.dao;

import net.zjcclims.domain.SDictionary;
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
 * DAO to manage SDictionary entities.
 * 
 */
@Repository("SDictionaryDAO")
@Transactional
public class SDictionaryDAOImpl extends AbstractJpaDao<SDictionary> implements
		SDictionaryDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { SDictionary.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new SDictionaryDAOImpl
	 *
	 */
	public SDictionaryDAOImpl() {
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
	 * JPQL Query - findSDictionaryByNoTwo
	 *
	 */
	@Transactional
	public Set<SDictionary> findSDictionaryByNoTwo(String noTwo) throws DataAccessException {

		return findSDictionaryByNoTwo(noTwo, -1, -1);
	}

	/**
	 * JPQL Query - findSDictionaryByNoTwo
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SDictionary> findSDictionaryByNoTwo(String noTwo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSDictionaryByNoTwo", startResult, maxRows, noTwo);
		return new LinkedHashSet<SDictionary>(query.getResultList());
	}

	/**
	 * JPQL Query - findSDictionaryByNoContaining
	 *
	 */
	@Transactional
	public Set<SDictionary> findSDictionaryByNoContaining(String no) throws DataAccessException {

		return findSDictionaryByNoContaining(no, -1, -1);
	}

	/**
	 * JPQL Query - findSDictionaryByNoContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SDictionary> findSDictionaryByNoContaining(String no, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSDictionaryByNoContaining", startResult, maxRows, no);
		return new LinkedHashSet<SDictionary>(query.getResultList());
	}

	/**
	 * JPQL Query - findSDictionaryByNoTwoContaining
	 *
	 */
	@Transactional
	public Set<SDictionary> findSDictionaryByNoTwoContaining(String noTwo) throws DataAccessException {

		return findSDictionaryByNoTwoContaining(noTwo, -1, -1);
	}

	/**
	 * JPQL Query - findSDictionaryByNoTwoContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SDictionary> findSDictionaryByNoTwoContaining(String noTwo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSDictionaryByNoTwoContaining", startResult, maxRows, noTwo);
		return new LinkedHashSet<SDictionary>(query.getResultList());
	}

	/**
	 * JPQL Query - findSDictionaryByCategory
	 *
	 */
	@Transactional
	public Set<SDictionary> findSDictionaryByCategory(String category) throws DataAccessException {

		return findSDictionaryByCategory(category, -1, -1);
	}

	/**
	 * JPQL Query - findSDictionaryByCategory
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SDictionary> findSDictionaryByCategory(String category, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSDictionaryByCategory", startResult, maxRows, category);
		return new LinkedHashSet<SDictionary>(query.getResultList());
	}

	/**
	 * JPQL Query - findSDictionaryById
	 *
	 */
	@Transactional
	public SDictionary findSDictionaryById(Integer id) throws DataAccessException {

		return findSDictionaryById(id, -1, -1);
	}

	/**
	 * JPQL Query - findSDictionaryById
	 *
	 */

	@Transactional
	public SDictionary findSDictionaryById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSDictionaryById", startResult, maxRows, id);
			return (net.zjcclims.domain.SDictionary) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findSDictionaryByNo
	 *
	 */
	@Transactional
	public Set<SDictionary> findSDictionaryByNo(String no) throws DataAccessException {

		return findSDictionaryByNo(no, -1, -1);
	}

	/**
	 * JPQL Query - findSDictionaryByNo
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SDictionary> findSDictionaryByNo(String no, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSDictionaryByNo", startResult, maxRows, no);
		return new LinkedHashSet<SDictionary>(query.getResultList());
	}

	/**
	 * JPQL Query - findSDictionaryByTypeDictionary
	 *
	 */
	@Transactional
	public Set<SDictionary> findSDictionaryByTypeDictionary(String typeDictionary) throws DataAccessException {

		return findSDictionaryByTypeDictionary(typeDictionary, -1, -1);
	}

	/**
	 * JPQL Query - findSDictionaryByTypeDictionary
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SDictionary> findSDictionaryByTypeDictionary(String typeDictionary, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSDictionaryByTypeDictionary", startResult, maxRows, typeDictionary);
		return new LinkedHashSet<SDictionary>(query.getResultList());
	}

	/**
	 * JPQL Query - findSDictionaryByCategoryContaining
	 *
	 */
	@Transactional
	public Set<SDictionary> findSDictionaryByCategoryContaining(String category) throws DataAccessException {

		return findSDictionaryByCategoryContaining(category, -1, -1);
	}

	/**
	 * JPQL Query - findSDictionaryByCategoryContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SDictionary> findSDictionaryByCategoryContaining(String category, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSDictionaryByCategoryContaining", startResult, maxRows, category);
		return new LinkedHashSet<SDictionary>(query.getResultList());
	}

	/**
	 * JPQL Query - findSDictionaryByNoOne
	 *
	 */
	@Transactional
	public Set<SDictionary> findSDictionaryByNoOne(String noOne) throws DataAccessException {

		return findSDictionaryByNoOne(noOne, -1, -1);
	}

	/**
	 * JPQL Query - findSDictionaryByNoOne
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SDictionary> findSDictionaryByNoOne(String noOne, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSDictionaryByNoOne", startResult, maxRows, noOne);
		return new LinkedHashSet<SDictionary>(query.getResultList());
	}

	/**
	 * JPQL Query - findSDictionaryByPrimaryKey
	 *
	 */
	@Transactional
	public SDictionary findSDictionaryByPrimaryKey(Integer id) throws DataAccessException {

		return findSDictionaryByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findSDictionaryByPrimaryKey
	 *
	 */

	@Transactional
	public SDictionary findSDictionaryByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSDictionaryByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.SDictionary) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findSDictionaryByNoOneContaining
	 *
	 */
	@Transactional
	public Set<SDictionary> findSDictionaryByNoOneContaining(String noOne) throws DataAccessException {

		return findSDictionaryByNoOneContaining(noOne, -1, -1);
	}

	/**
	 * JPQL Query - findSDictionaryByNoOneContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SDictionary> findSDictionaryByNoOneContaining(String noOne, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSDictionaryByNoOneContaining", startResult, maxRows, noOne);
		return new LinkedHashSet<SDictionary>(query.getResultList());
	}

	/**
	 * JPQL Query - findSDictionaryByNoThree
	 *
	 */
	@Transactional
	public Set<SDictionary> findSDictionaryByNoThree(String noThree) throws DataAccessException {

		return findSDictionaryByNoThree(noThree, -1, -1);
	}

	/**
	 * JPQL Query - findSDictionaryByNoThree
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SDictionary> findSDictionaryByNoThree(String noThree, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSDictionaryByNoThree", startResult, maxRows, noThree);
		return new LinkedHashSet<SDictionary>(query.getResultList());
	}

	/**
	 * JPQL Query - findSDictionaryByTypeDictionaryContaining
	 *
	 */
	@Transactional
	public Set<SDictionary> findSDictionaryByTypeDictionaryContaining(String typeDictionary) throws DataAccessException {

		return findSDictionaryByTypeDictionaryContaining(typeDictionary, -1, -1);
	}

	/**
	 * JPQL Query - findSDictionaryByTypeDictionaryContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SDictionary> findSDictionaryByTypeDictionaryContaining(String typeDictionary, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSDictionaryByTypeDictionaryContaining", startResult, maxRows, typeDictionary);
		return new LinkedHashSet<SDictionary>(query.getResultList());
	}

	/**
	 * JPQL Query - findSDictionaryByNoThreeContaining
	 *
	 */
	@Transactional
	public Set<SDictionary> findSDictionaryByNoThreeContaining(String noThree) throws DataAccessException {

		return findSDictionaryByNoThreeContaining(noThree, -1, -1);
	}

	/**
	 * JPQL Query - findSDictionaryByNoThreeContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SDictionary> findSDictionaryByNoThreeContaining(String noThree, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSDictionaryByNoThreeContaining", startResult, maxRows, noThree);
		return new LinkedHashSet<SDictionary>(query.getResultList());
	}

	/**
	 * JPQL Query - findSDictionaryByNameContaining
	 *
	 */
	@Transactional
	public Set<SDictionary> findSDictionaryByNameContaining(String name) throws DataAccessException {

		return findSDictionaryByNameContaining(name, -1, -1);
	}

	/**
	 * JPQL Query - findSDictionaryByNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SDictionary> findSDictionaryByNameContaining(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSDictionaryByNameContaining", startResult, maxRows, name);
		return new LinkedHashSet<SDictionary>(query.getResultList());
	}

	/**
	 * JPQL Query - findSDictionaryByName
	 *
	 */
	@Transactional
	public Set<SDictionary> findSDictionaryByName(String name) throws DataAccessException {

		return findSDictionaryByName(name, -1, -1);
	}

	/**
	 * JPQL Query - findSDictionaryByName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SDictionary> findSDictionaryByName(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSDictionaryByName", startResult, maxRows, name);
		return new LinkedHashSet<SDictionary>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllSDictionarys
	 *
	 */
	@Transactional
	public Set<SDictionary> findAllSDictionarys() throws DataAccessException {

		return findAllSDictionarys(-1, -1);
	}

	/**
	 * JPQL Query - findAllSDictionarys
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SDictionary> findAllSDictionarys(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllSDictionarys", startResult, maxRows);
		return new LinkedHashSet<SDictionary>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(SDictionary entity) {
		return true;
	}
}
