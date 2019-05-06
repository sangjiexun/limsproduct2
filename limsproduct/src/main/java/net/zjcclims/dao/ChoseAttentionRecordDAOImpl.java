package net.zjcclims.dao;

import net.zjcclims.domain.ChoseAttentionRecord;
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
 * DAO to manage ChoseAttentionRecord entities.
 * 
 */
@Repository("ChoseAttentionRecordDAO")
@Transactional
public class ChoseAttentionRecordDAOImpl extends AbstractJpaDao<ChoseAttentionRecord>
		implements ChoseAttentionRecordDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ChoseAttentionRecord.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ChoseAttentionRecordDAOImpl
	 *
	 */
	public ChoseAttentionRecordDAOImpl() {
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
	 * JPQL Query - findAllChoseAttentionRecords
	 *
	 */
	@Transactional
	public Set<ChoseAttentionRecord> findAllChoseAttentionRecords() throws DataAccessException {

		return findAllChoseAttentionRecords(-1, -1);
	}

	/**
	 * JPQL Query - findAllChoseAttentionRecords
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseAttentionRecord> findAllChoseAttentionRecords(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllChoseAttentionRecords", startResult, maxRows);
		return new LinkedHashSet<ChoseAttentionRecord>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseAttentionRecordByPrimaryKey
	 *
	 */
	@Transactional
	public ChoseAttentionRecord findChoseAttentionRecordByPrimaryKey(Integer id) throws DataAccessException {

		return findChoseAttentionRecordByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findChoseAttentionRecordByPrimaryKey
	 *
	 */

	@Transactional
	public ChoseAttentionRecord findChoseAttentionRecordByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findChoseAttentionRecordByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.ChoseAttentionRecord) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findChoseAttentionRecordByUsername
	 *
	 */
	@Transactional
	public Set<ChoseAttentionRecord> findChoseAttentionRecordByUsername(String username) throws DataAccessException {

		return findChoseAttentionRecordByUsername(username, -1, -1);
	}

	/**
	 * JPQL Query - findChoseAttentionRecordByUsername
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseAttentionRecord> findChoseAttentionRecordByUsername(String username, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseAttentionRecordByUsername", startResult, maxRows, username);
		return new LinkedHashSet<ChoseAttentionRecord>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseAttentionRecordByUsernameContaining
	 *
	 */
	@Transactional
	public Set<ChoseAttentionRecord> findChoseAttentionRecordByUsernameContaining(String username) throws DataAccessException {

		return findChoseAttentionRecordByUsernameContaining(username, -1, -1);
	}

	/**
	 * JPQL Query - findChoseAttentionRecordByUsernameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseAttentionRecord> findChoseAttentionRecordByUsernameContaining(String username, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseAttentionRecordByUsernameContaining", startResult, maxRows, username);
		return new LinkedHashSet<ChoseAttentionRecord>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseAttentionRecordById
	 *
	 */
	@Transactional
	public ChoseAttentionRecord findChoseAttentionRecordById(Integer id) throws DataAccessException {

		return findChoseAttentionRecordById(id, -1, -1);
	}

	/**
	 * JPQL Query - findChoseAttentionRecordById
	 *
	 */

	@Transactional
	public ChoseAttentionRecord findChoseAttentionRecordById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findChoseAttentionRecordById", startResult, maxRows, id);
			return (net.zjcclims.domain.ChoseAttentionRecord) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findChoseAttentionRecordByThemeId
	 *
	 */
	@Transactional
	public Set<ChoseAttentionRecord> findChoseAttentionRecordByThemeId(Integer themeId) throws DataAccessException {

		return findChoseAttentionRecordByThemeId(themeId, -1, -1);
	}

	/**
	 * JPQL Query - findChoseAttentionRecordByThemeId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseAttentionRecord> findChoseAttentionRecordByThemeId(Integer themeId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseAttentionRecordByThemeId", startResult, maxRows, themeId);
		return new LinkedHashSet<ChoseAttentionRecord>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(ChoseAttentionRecord entity) {
		return true;
	}
}
