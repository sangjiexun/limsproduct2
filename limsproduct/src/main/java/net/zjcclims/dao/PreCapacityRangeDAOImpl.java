package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.PreCapacityRange;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage PreCapacityRange entities.
 * 
 */
@Repository("PreCapacityRangeDAO")
@Transactional
public class PreCapacityRangeDAOImpl extends AbstractJpaDao<PreCapacityRange>
		implements PreCapacityRangeDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { PreCapacityRange.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new PreCapacityRangeDAOImpl
	 *
	 */
	public PreCapacityRangeDAOImpl() {
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
	 * JPQL Query - findPreCapacityRangeByCapaRangeContaining
	 *
	 */
	@Transactional
	public Set<PreCapacityRange> findPreCapacityRangeByCapaRangeContaining(String capaRange) throws DataAccessException {

		return findPreCapacityRangeByCapaRangeContaining(capaRange, -1, -1);
	}

	/**
	 * JPQL Query - findPreCapacityRangeByCapaRangeContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreCapacityRange> findPreCapacityRangeByCapaRangeContaining(String capaRange, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findPreCapacityRangeByCapaRangeContaining", startResult, maxRows, capaRange);
		return new LinkedHashSet<PreCapacityRange>(query.getResultList());
	}

	/**
	 * JPQL Query - findPreCapacityRangeById
	 *
	 */
	@Transactional
	public PreCapacityRange findPreCapacityRangeById(Integer id) throws DataAccessException {

		return findPreCapacityRangeById(id, -1, -1);
	}

	/**
	 * JPQL Query - findPreCapacityRangeById
	 *
	 */

	@Transactional
	public PreCapacityRange findPreCapacityRangeById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findPreCapacityRangeById", startResult, maxRows, id);
			return (PreCapacityRange) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findPreCapacityRangeByCapaTypeContaining
	 *
	 */
	@Transactional
	public Set<PreCapacityRange> findPreCapacityRangeByCapaTypeContaining(String capaType) throws DataAccessException {

		return findPreCapacityRangeByCapaTypeContaining(capaType, -1, -1);
	}

	/**
	 * JPQL Query - findPreCapacityRangeByCapaTypeContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreCapacityRange> findPreCapacityRangeByCapaTypeContaining(String capaType, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findPreCapacityRangeByCapaTypeContaining", startResult, maxRows, capaType);
		return new LinkedHashSet<PreCapacityRange>(query.getResultList());
	}

	/**
	 * JPQL Query - findPreCapacityRangeByCapaRange
	 *
	 */
	@Transactional
	public Set<PreCapacityRange> findPreCapacityRangeByCapaRange(String capaRange) throws DataAccessException {

		return findPreCapacityRangeByCapaRange(capaRange, -1, -1);
	}

	/**
	 * JPQL Query - findPreCapacityRangeByCapaRange
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreCapacityRange> findPreCapacityRangeByCapaRange(String capaRange, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findPreCapacityRangeByCapaRange", startResult, maxRows, capaRange);
		return new LinkedHashSet<PreCapacityRange>(query.getResultList());
	}

	/**
	 * JPQL Query - findPreCapacityRangeByCapaType
	 *
	 */
	@Transactional
	public Set<PreCapacityRange> findPreCapacityRangeByCapaType(String capaType) throws DataAccessException {

		return findPreCapacityRangeByCapaType(capaType, -1, -1);
	}

	/**
	 * JPQL Query - findPreCapacityRangeByCapaType
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreCapacityRange> findPreCapacityRangeByCapaType(String capaType, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findPreCapacityRangeByCapaType", startResult, maxRows, capaType);
		return new LinkedHashSet<PreCapacityRange>(query.getResultList());
	}

	/**
	 * JPQL Query - findPreCapacityRangeByPrimaryKey
	 *
	 */
	@Transactional
	public PreCapacityRange findPreCapacityRangeByPrimaryKey(Integer id) throws DataAccessException {

		return findPreCapacityRangeByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findPreCapacityRangeByPrimaryKey
	 *
	 */

	@Transactional
	public PreCapacityRange findPreCapacityRangeByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findPreCapacityRangeByPrimaryKey", startResult, maxRows, id);
			return (PreCapacityRange) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllPreCapacityRanges
	 *
	 */
	@Transactional
	public Set<PreCapacityRange> findAllPreCapacityRanges() throws DataAccessException {

		return findAllPreCapacityRanges(-1, -1);
	}

	/**
	 * JPQL Query - findAllPreCapacityRanges
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<PreCapacityRange> findAllPreCapacityRanges(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllPreCapacityRanges", startResult, maxRows);
		return new LinkedHashSet<PreCapacityRange>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(PreCapacityRange entity) {
		return true;
	}
}
