package net.zjcclims.dao;

import net.zjcclims.domain.ChoseAttention;
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
 * DAO to manage ChoseAttention entities.
 * 
 */
@Repository("ChoseAttentionDAO")
@Transactional
public class ChoseAttentionDAOImpl extends AbstractJpaDao<ChoseAttention>
		implements ChoseAttentionDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ChoseAttention.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ChoseAttentionDAOImpl
	 *
	 */
	public ChoseAttentionDAOImpl() {
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
	 * JPQL Query - findChoseAttentionByTittleContaining
	 *
	 */
	@Transactional
	public Set<ChoseAttention> findChoseAttentionByTittleContaining(String tittle) throws DataAccessException {

		return findChoseAttentionByTittleContaining(tittle, -1, -1);
	}

	/**
	 * JPQL Query - findChoseAttentionByTittleContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseAttention> findChoseAttentionByTittleContaining(String tittle, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseAttentionByTittleContaining", startResult, maxRows, tittle);
		return new LinkedHashSet<ChoseAttention>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseAttentionByContent
	 *
	 */
	@Transactional
	public Set<ChoseAttention> findChoseAttentionByContent(String content) throws DataAccessException {

		return findChoseAttentionByContent(content, -1, -1);
	}

	/**
	 * JPQL Query - findChoseAttentionByContent
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseAttention> findChoseAttentionByContent(String content, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseAttentionByContent", startResult, maxRows, content);
		return new LinkedHashSet<ChoseAttention>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseAttentionByTittle
	 *
	 */
	@Transactional
	public Set<ChoseAttention> findChoseAttentionByTittle(String tittle) throws DataAccessException {

		return findChoseAttentionByTittle(tittle, -1, -1);
	}

	/**
	 * JPQL Query - findChoseAttentionByTittle
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseAttention> findChoseAttentionByTittle(String tittle, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseAttentionByTittle", startResult, maxRows, tittle);
		return new LinkedHashSet<ChoseAttention>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseAttentionByPrimaryKey
	 *
	 */
	@Transactional
	public ChoseAttention findChoseAttentionByPrimaryKey(Integer id) throws DataAccessException {

		return findChoseAttentionByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findChoseAttentionByPrimaryKey
	 *
	 */

	@Transactional
	public ChoseAttention findChoseAttentionByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findChoseAttentionByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.ChoseAttention) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findChoseAttentionByType
	 *
	 */
	@Transactional
	public Set<ChoseAttention> findChoseAttentionByType(Integer type) throws DataAccessException {

		return findChoseAttentionByType(type, -1, -1);
	}

	/**
	 * JPQL Query - findChoseAttentionByType
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseAttention> findChoseAttentionByType(Integer type, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseAttentionByType", startResult, maxRows, type);
		return new LinkedHashSet<ChoseAttention>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseAttentionById
	 *
	 */
	@Transactional
	public ChoseAttention findChoseAttentionById(Integer id) throws DataAccessException {

		return findChoseAttentionById(id, -1, -1);
	}

	/**
	 * JPQL Query - findChoseAttentionById
	 *
	 */

	@Transactional
	public ChoseAttention findChoseAttentionById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findChoseAttentionById", startResult, maxRows, id);
			return (net.zjcclims.domain.ChoseAttention) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllChoseAttentions
	 *
	 */
	@Transactional
	public Set<ChoseAttention> findAllChoseAttentions() throws DataAccessException {

		return findAllChoseAttentions(-1, -1);
	}

	/**
	 * JPQL Query - findAllChoseAttentions
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseAttention> findAllChoseAttentions(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllChoseAttentions", startResult, maxRows);
		return new LinkedHashSet<ChoseAttention>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(ChoseAttention entity) {
		return true;
	}
}
