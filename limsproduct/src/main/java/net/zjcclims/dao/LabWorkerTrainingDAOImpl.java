package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.LabWorkerTraining;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage LabWorkerTraining entities.
 * 
 */
@Repository("LabWorkerTrainingDAO")
@Transactional
public class LabWorkerTrainingDAOImpl extends AbstractJpaDao<LabWorkerTraining>
		implements LabWorkerTrainingDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabWorkerTraining.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabWorkerTrainingDAOImpl
	 *
	 */
	public LabWorkerTrainingDAOImpl() {
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
	 * JPQL Query - findLabWorkerTrainingByPrimaryKey
	 *
	 */
	@Transactional
	public LabWorkerTraining findLabWorkerTrainingByPrimaryKey(Integer id) throws DataAccessException {

		return findLabWorkerTrainingByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabWorkerTrainingByPrimaryKey
	 *
	 */

	@Transactional
	public LabWorkerTraining findLabWorkerTrainingByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabWorkerTrainingByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabWorkerTraining) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabWorkerTrainingByScore
	 *
	 */
	@Transactional
	public Set<LabWorkerTraining> findLabWorkerTrainingByScore(java.math.BigDecimal score) throws DataAccessException {

		return findLabWorkerTrainingByScore(score, -1, -1);
	}

	/**
	 * JPQL Query - findLabWorkerTrainingByScore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabWorkerTraining> findLabWorkerTrainingByScore(java.math.BigDecimal score, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabWorkerTrainingByScore", startResult, maxRows, score);
		return new LinkedHashSet<LabWorkerTraining>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabWorkerTrainingByBeginTime
	 *
	 */
	@Transactional
	public Set<LabWorkerTraining> findLabWorkerTrainingByBeginTime(java.util.Calendar beginTime) throws DataAccessException {

		return findLabWorkerTrainingByBeginTime(beginTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabWorkerTrainingByBeginTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabWorkerTraining> findLabWorkerTrainingByBeginTime(java.util.Calendar beginTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabWorkerTrainingByBeginTime", startResult, maxRows, beginTime);
		return new LinkedHashSet<LabWorkerTraining>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabWorkerTrainingByOrganizerContaining
	 *
	 */
	@Transactional
	public Set<LabWorkerTraining> findLabWorkerTrainingByOrganizerContaining(String organizer) throws DataAccessException {

		return findLabWorkerTrainingByOrganizerContaining(organizer, -1, -1);
	}

	/**
	 * JPQL Query - findLabWorkerTrainingByOrganizerContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabWorkerTraining> findLabWorkerTrainingByOrganizerContaining(String organizer, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabWorkerTrainingByOrganizerContaining", startResult, maxRows, organizer);
		return new LinkedHashSet<LabWorkerTraining>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabWorkerTrainingByEndTime
	 *
	 */
	@Transactional
	public Set<LabWorkerTraining> findLabWorkerTrainingByEndTime(java.util.Calendar endTime) throws DataAccessException {

		return findLabWorkerTrainingByEndTime(endTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabWorkerTrainingByEndTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabWorkerTraining> findLabWorkerTrainingByEndTime(java.util.Calendar endTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabWorkerTrainingByEndTime", startResult, maxRows, endTime);
		return new LinkedHashSet<LabWorkerTraining>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabWorkerTrainingByAnnexContaining
	 *
	 */
	@Transactional
	public Set<LabWorkerTraining> findLabWorkerTrainingByAnnexContaining(String annex) throws DataAccessException {

		return findLabWorkerTrainingByAnnexContaining(annex, -1, -1);
	}

	/**
	 * JPQL Query - findLabWorkerTrainingByAnnexContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabWorkerTraining> findLabWorkerTrainingByAnnexContaining(String annex, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabWorkerTrainingByAnnexContaining", startResult, maxRows, annex);
		return new LinkedHashSet<LabWorkerTraining>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabWorkerTrainingByLearnContentContaining
	 *
	 */
	@Transactional
	public Set<LabWorkerTraining> findLabWorkerTrainingByLearnContentContaining(String learnContent) throws DataAccessException {

		return findLabWorkerTrainingByLearnContentContaining(learnContent, -1, -1);
	}

	/**
	 * JPQL Query - findLabWorkerTrainingByLearnContentContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabWorkerTraining> findLabWorkerTrainingByLearnContentContaining(String learnContent, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabWorkerTrainingByLearnContentContaining", startResult, maxRows, learnContent);
		return new LinkedHashSet<LabWorkerTraining>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabWorkerTrainingByAnnex
	 *
	 */
	@Transactional
	public Set<LabWorkerTraining> findLabWorkerTrainingByAnnex(String annex) throws DataAccessException {

		return findLabWorkerTrainingByAnnex(annex, -1, -1);
	}

	/**
	 * JPQL Query - findLabWorkerTrainingByAnnex
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabWorkerTraining> findLabWorkerTrainingByAnnex(String annex, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabWorkerTrainingByAnnex", startResult, maxRows, annex);
		return new LinkedHashSet<LabWorkerTraining>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabWorkerTrainingByOrganizer
	 *
	 */
	@Transactional
	public Set<LabWorkerTraining> findLabWorkerTrainingByOrganizer(String organizer) throws DataAccessException {

		return findLabWorkerTrainingByOrganizer(organizer, -1, -1);
	}

	/**
	 * JPQL Query - findLabWorkerTrainingByOrganizer
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabWorkerTraining> findLabWorkerTrainingByOrganizer(String organizer, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabWorkerTrainingByOrganizer", startResult, maxRows, organizer);
		return new LinkedHashSet<LabWorkerTraining>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabWorkerTrainingById
	 *
	 */
	@Transactional
	public LabWorkerTraining findLabWorkerTrainingById(Integer id) throws DataAccessException {

		return findLabWorkerTrainingById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabWorkerTrainingById
	 *
	 */

	@Transactional
	public LabWorkerTraining findLabWorkerTrainingById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabWorkerTrainingById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabWorkerTraining) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabWorkerTrainingByLearnContent
	 *
	 */
	@Transactional
	public Set<LabWorkerTraining> findLabWorkerTrainingByLearnContent(String learnContent) throws DataAccessException {

		return findLabWorkerTrainingByLearnContent(learnContent, -1, -1);
	}

	/**
	 * JPQL Query - findLabWorkerTrainingByLearnContent
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabWorkerTraining> findLabWorkerTrainingByLearnContent(String learnContent, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabWorkerTrainingByLearnContent", startResult, maxRows, learnContent);
		return new LinkedHashSet<LabWorkerTraining>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabWorkerTrainings
	 *
	 */
	@Transactional
	public Set<LabWorkerTraining> findAllLabWorkerTrainings() throws DataAccessException {

		return findAllLabWorkerTrainings(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabWorkerTrainings
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabWorkerTraining> findAllLabWorkerTrainings(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabWorkerTrainings", startResult, maxRows);
		return new LinkedHashSet<LabWorkerTraining>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(LabWorkerTraining entity) {
		return true;
	}
}
