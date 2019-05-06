package net.zjcclims.dao;

import net.zjcclims.domain.ChoseDissertationRecord;
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
 * DAO to manage ChoseDissertationRecord entities.
 * 
 */
@Repository("ChoseDissertationRecordDAO")
@Transactional
public class ChoseDissertationRecordDAOImpl extends AbstractJpaDao<ChoseDissertationRecord>
		implements ChoseDissertationRecordDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ChoseDissertationRecord.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ChoseDissertationRecordDAOImpl
	 *
	 */
	public ChoseDissertationRecordDAOImpl() {
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
	 * JPQL Query - findChoseDissertationRecordByAduitResult
	 *
	 */
	@Transactional
	public Set<ChoseDissertationRecord> findChoseDissertationRecordByAduitResult(Integer aduitResult) throws DataAccessException {

		return findChoseDissertationRecordByAduitResult(aduitResult, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDissertationRecordByAduitResult
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDissertationRecord> findChoseDissertationRecordByAduitResult(Integer aduitResult, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDissertationRecordByAduitResult", startResult, maxRows, aduitResult);
		return new LinkedHashSet<ChoseDissertationRecord>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDissertationRecordById
	 *
	 */
	@Transactional
	public ChoseDissertationRecord findChoseDissertationRecordById(Integer id) throws DataAccessException {

		return findChoseDissertationRecordById(id, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDissertationRecordById
	 *
	 */

	@Transactional
	public ChoseDissertationRecord findChoseDissertationRecordById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findChoseDissertationRecordById", startResult, maxRows, id);
			return (net.zjcclims.domain.ChoseDissertationRecord) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findChoseDissertationRecordByPrimaryKey
	 *
	 */
	@Transactional
	public ChoseDissertationRecord findChoseDissertationRecordByPrimaryKey(Integer id) throws DataAccessException {

		return findChoseDissertationRecordByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDissertationRecordByPrimaryKey
	 *
	 */

	@Transactional
	public ChoseDissertationRecord findChoseDissertationRecordByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findChoseDissertationRecordByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.ChoseDissertationRecord) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllChoseDissertationRecords
	 *
	 */
	@Transactional
	public Set<ChoseDissertationRecord> findAllChoseDissertationRecords() throws DataAccessException {

		return findAllChoseDissertationRecords(-1, -1);
	}

	/**
	 * JPQL Query - findAllChoseDissertationRecords
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDissertationRecord> findAllChoseDissertationRecords(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllChoseDissertationRecords", startResult, maxRows);
		return new LinkedHashSet<ChoseDissertationRecord>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDissertationRecordByCurrAduit
	 *
	 */
	@Transactional
	public Set<ChoseDissertationRecord> findChoseDissertationRecordByCurrAduit(Integer currAduit) throws DataAccessException {

		return findChoseDissertationRecordByCurrAduit(currAduit, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDissertationRecordByCurrAduit
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDissertationRecord> findChoseDissertationRecordByCurrAduit(Integer currAduit, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDissertationRecordByCurrAduit", startResult, maxRows, currAduit);
		return new LinkedHashSet<ChoseDissertationRecord>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(ChoseDissertationRecord entity) {
		return true;
	}
}
