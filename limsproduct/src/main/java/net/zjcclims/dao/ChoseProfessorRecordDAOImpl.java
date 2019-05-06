package net.zjcclims.dao;

import net.zjcclims.domain.ChoseProfessorRecord;
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
 * DAO to manage ChoseProfessorRecord entities.
 * 
 */
@Repository("ChoseProfessorRecordDAO")
@Transactional
public class ChoseProfessorRecordDAOImpl extends AbstractJpaDao<ChoseProfessorRecord>
		implements ChoseProfessorRecordDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ChoseProfessorRecord.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ChoseProfessorRecordDAOImpl
	 *
	 */
	public ChoseProfessorRecordDAOImpl() {
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
	 * JPQL Query - findChoseProfessorRecordByAduitResult
	 *
	 */
	@Transactional
	public Set<ChoseProfessorRecord> findChoseProfessorRecordByAduitResult(Integer aduitResult) throws DataAccessException {

		return findChoseProfessorRecordByAduitResult(aduitResult, -1, -1);
	}

	/**
	 * JPQL Query - findChoseProfessorRecordByAduitResult
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseProfessorRecord> findChoseProfessorRecordByAduitResult(Integer aduitResult, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseProfessorRecordByAduitResult", startResult, maxRows, aduitResult);
		return new LinkedHashSet<ChoseProfessorRecord>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseProfessorRecordByPrimaryKey
	 *
	 */
	@Transactional
	public ChoseProfessorRecord findChoseProfessorRecordByPrimaryKey(Integer id) throws DataAccessException {

		return findChoseProfessorRecordByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findChoseProfessorRecordByPrimaryKey
	 *
	 */

	@Transactional
	public ChoseProfessorRecord findChoseProfessorRecordByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findChoseProfessorRecordByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.ChoseProfessorRecord) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllChoseProfessorRecords
	 *
	 */
	@Transactional
	public Set<ChoseProfessorRecord> findAllChoseProfessorRecords() throws DataAccessException {

		return findAllChoseProfessorRecords(-1, -1);
	}

	/**
	 * JPQL Query - findAllChoseProfessorRecords
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseProfessorRecord> findAllChoseProfessorRecords(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllChoseProfessorRecords", startResult, maxRows);
		return new LinkedHashSet<ChoseProfessorRecord>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseProfessorRecordById
	 *
	 */
	@Transactional
	public ChoseProfessorRecord findChoseProfessorRecordById(Integer id) throws DataAccessException {

		return findChoseProfessorRecordById(id, -1, -1);
	}

	/**
	 * JPQL Query - findChoseProfessorRecordById
	 *
	 */

	@Transactional
	public ChoseProfessorRecord findChoseProfessorRecordById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findChoseProfessorRecordById", startResult, maxRows, id);
			return (net.zjcclims.domain.ChoseProfessorRecord) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findChoseProfessorRecordByCurrAduit
	 *
	 */
	@Transactional
	public Set<ChoseProfessorRecord> findChoseProfessorRecordByCurrAduit(Integer currAduit) throws DataAccessException {

		return findChoseProfessorRecordByCurrAduit(currAduit, -1, -1);
	}

	/**
	 * JPQL Query - findChoseProfessorRecordByCurrAduit
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseProfessorRecord> findChoseProfessorRecordByCurrAduit(Integer currAduit, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseProfessorRecordByCurrAduit", startResult, maxRows, currAduit);
		return new LinkedHashSet<ChoseProfessorRecord>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(ChoseProfessorRecord entity) {
		return true;
	}
}
