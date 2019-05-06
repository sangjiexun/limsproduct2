package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.LabRoomTraining;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage LabRoomTraining entities.
 * 
 */
@Repository("LabRoomTrainingDAO")
@Transactional
public class LabRoomTrainingDAOImpl extends AbstractJpaDao<LabRoomTraining>
		implements LabRoomTrainingDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabRoomTraining.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabRoomTrainingDAOImpl
	 *
	 */
	public LabRoomTrainingDAOImpl() {
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
	 * JPQL Query - findLabRoomTrainingByAddressContaining
	 *
	 */
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByAddressContaining(String address) throws DataAccessException {

		return findLabRoomTrainingByAddressContaining(address, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomTrainingByAddressContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByAddressContaining(String address, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomTrainingByAddressContaining", startResult, maxRows, address);
		return new LinkedHashSet<LabRoomTraining>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomTrainingByAccessJoin
	 *
	 */
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByAccessJoin(String accessJoin) throws DataAccessException {

		return findLabRoomTrainingByAccessJoin(accessJoin, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomTrainingByAccessJoin
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByAccessJoin(String accessJoin, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomTrainingByAccessJoin", startResult, maxRows, accessJoin);
		return new LinkedHashSet<LabRoomTraining>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomTrainingByNumber
	 *
	 */
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByNumber(Integer number) throws DataAccessException {

		return findLabRoomTrainingByNumber(number, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomTrainingByNumber
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByNumber(Integer number, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomTrainingByNumber", startResult, maxRows, number);
		return new LinkedHashSet<LabRoomTraining>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomTrainingByContent
	 *
	 */
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByContent(String content) throws DataAccessException {

		return findLabRoomTrainingByContent(content, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomTrainingByContent
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByContent(String content, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomTrainingByContent", startResult, maxRows, content);
		return new LinkedHashSet<LabRoomTraining>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomTrainingByJoinNumber
	 *
	 */
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByJoinNumber(Integer joinNumber) throws DataAccessException {

		return findLabRoomTrainingByJoinNumber(joinNumber, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomTrainingByJoinNumber
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByJoinNumber(Integer joinNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomTrainingByJoinNumber", startResult, maxRows, joinNumber);
		return new LinkedHashSet<LabRoomTraining>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomTrainingByAddress
	 *
	 */
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByAddress(String address) throws DataAccessException {

		return findLabRoomTrainingByAddress(address, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomTrainingByAddress
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByAddress(String address, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomTrainingByAddress", startResult, maxRows, address);
		return new LinkedHashSet<LabRoomTraining>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomTrainingByFlag
	 *
	 */
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByFlag(Integer flag) throws DataAccessException {

		return findLabRoomTrainingByFlag(flag, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomTrainingByFlag
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByFlag(Integer flag, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomTrainingByFlag", startResult, maxRows, flag);
		return new LinkedHashSet<LabRoomTraining>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomTrainingByPassRateContaining
	 *
	 */
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByPassRateContaining(String passRate) throws DataAccessException {

		return findLabRoomTrainingByPassRateContaining(passRate, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomTrainingByPassRateContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByPassRateContaining(String passRate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomTrainingByPassRateContaining", startResult, maxRows, passRate);
		return new LinkedHashSet<LabRoomTraining>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomTrainingByPrimaryKey
	 *
	 */
	@Transactional
	public LabRoomTraining findLabRoomTrainingByPrimaryKey(Integer id) throws DataAccessException {

		return findLabRoomTrainingByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomTrainingByPrimaryKey
	 *
	 */

	@Transactional
	public LabRoomTraining findLabRoomTrainingByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomTrainingByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomTraining) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabRoomTrainingByTime
	 *
	 */
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByTime(java.util.Calendar time) throws DataAccessException {

		return findLabRoomTrainingByTime(time, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomTrainingByTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByTime(java.util.Calendar time, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomTrainingByTime", startResult, maxRows, time);
		return new LinkedHashSet<LabRoomTraining>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabRoomTrainings
	 *
	 */
	@Transactional
	public Set<LabRoomTraining> findAllLabRoomTrainings() throws DataAccessException {

		return findAllLabRoomTrainings(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabRoomTrainings
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomTraining> findAllLabRoomTrainings(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabRoomTrainings", startResult, maxRows);
		return new LinkedHashSet<LabRoomTraining>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomTrainingByAccessJoinContaining
	 *
	 */
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByAccessJoinContaining(String accessJoin) throws DataAccessException {

		return findLabRoomTrainingByAccessJoinContaining(accessJoin, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomTrainingByAccessJoinContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByAccessJoinContaining(String accessJoin, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomTrainingByAccessJoinContaining", startResult, maxRows, accessJoin);
		return new LinkedHashSet<LabRoomTraining>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomTrainingByPassRate
	 *
	 */
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByPassRate(String passRate) throws DataAccessException {

		return findLabRoomTrainingByPassRate(passRate, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomTrainingByPassRate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomTraining> findLabRoomTrainingByPassRate(String passRate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomTrainingByPassRate", startResult, maxRows, passRate);
		return new LinkedHashSet<LabRoomTraining>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomTrainingById
	 *
	 */
	@Transactional
	public LabRoomTraining findLabRoomTrainingById(Integer id) throws DataAccessException {

		return findLabRoomTrainingById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomTrainingById
	 *
	 */

	@Transactional
	public LabRoomTraining findLabRoomTrainingById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomTrainingById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomTraining) query.getSingleResult();
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
	public boolean canBeMerged(LabRoomTraining entity) {
		return true;
	}
}
