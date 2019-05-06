package net.zjcclims.dao;

import net.zjcclims.domain.LabInspectGrading;
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
 * DAO to manage LabInspectGrading entities.
 *
 */
@Repository("LabInspectGradingDAO")
@Transactional
public class LabInspectGradingDAOImpl extends AbstractJpaDao<LabInspectGrading>
		implements LabInspectGradingDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabInspectGrading.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclims
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabInspectGradingDAOImpl
	 *
	 */
	public LabInspectGradingDAOImpl() {
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
	 * JPQL Query - findLabInspectGradingByPrimaryKey
	 *
	 */
	@Transactional
	public LabInspectGrading findLabInspectGradingByPrimaryKey(Integer id) throws DataAccessException {

		return findLabInspectGradingByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectGradingByPrimaryKey
	 *
	 */

	@Transactional
	public LabInspectGrading findLabInspectGradingByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabInspectGradingByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabInspectGrading) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabInspectGradingByLabRoomId
	 *
	 */
	@Transactional
	public Set<LabInspectGrading> findLabInspectGradingByLabRoomId(Integer labRoomId) throws DataAccessException {

		return findLabInspectGradingByLabRoomId(labRoomId, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectGradingByLabRoomId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabInspectGrading> findLabInspectGradingByLabRoomId(Integer labRoomId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabInspectGradingByLabRoomId", startResult, maxRows, labRoomId);
		return new LinkedHashSet<LabInspectGrading>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabInspectGradingByCreateTime
	 *
	 */
	@Transactional
	public Set<LabInspectGrading> findLabInspectGradingByCreateTime(java.util.Calendar createTime) throws DataAccessException {

		return findLabInspectGradingByCreateTime(createTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectGradingByCreateTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabInspectGrading> findLabInspectGradingByCreateTime(java.util.Calendar createTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabInspectGradingByCreateTime", startResult, maxRows, createTime);
		return new LinkedHashSet<LabInspectGrading>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabInspectGradingByStandardId
	 *
	 */
	@Transactional
	public Set<LabInspectGrading> findLabInspectGradingByStandardId(Integer standardId) throws DataAccessException {

		return findLabInspectGradingByStandardId(standardId, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectGradingByStandardId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabInspectGrading> findLabInspectGradingByStandardId(Integer standardId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabInspectGradingByStandardId", startResult, maxRows, standardId);
		return new LinkedHashSet<LabInspectGrading>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabInspectGradingByBatchId
	 *
	 */
	@Transactional
	public Set<LabInspectGrading> findLabInspectGradingByBatchId(Integer batchId) throws DataAccessException {

		return findLabInspectGradingByBatchId(batchId, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectGradingByBatchId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabInspectGrading> findLabInspectGradingByBatchId(Integer batchId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabInspectGradingByBatchId", startResult, maxRows, batchId);
		return new LinkedHashSet<LabInspectGrading>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabInspectGradings
	 *
	 */
	@Transactional
	public Set<LabInspectGrading> findAllLabInspectGradings() throws DataAccessException {

		return findAllLabInspectGradings(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabInspectGradings
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabInspectGrading> findAllLabInspectGradings(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabInspectGradings", startResult, maxRows);
		return new LinkedHashSet<LabInspectGrading>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabInspectGradingById
	 *
	 */
	@Transactional
	public LabInspectGrading findLabInspectGradingById(Integer id) throws DataAccessException {

		return findLabInspectGradingById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectGradingById
	 *
	 */

	@Transactional
	public LabInspectGrading findLabInspectGradingById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabInspectGradingById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabInspectGrading) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabInspectGradingByDocumentId
	 *
	 */
	@Transactional
	public Set<LabInspectGrading> findLabInspectGradingByDocumentId(Integer documentId) throws DataAccessException {

		return findLabInspectGradingByDocumentId(documentId, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectGradingByDocumentId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabInspectGrading> findLabInspectGradingByDocumentId(Integer documentId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabInspectGradingByDocumentId", startResult, maxRows, documentId);
		return new LinkedHashSet<LabInspectGrading>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabInspectGradingByPoint
	 *
	 */
	@Transactional
	public Set<LabInspectGrading> findLabInspectGradingByPoint(Integer point) throws DataAccessException {

		return findLabInspectGradingByPoint(point, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectGradingByPoint
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabInspectGrading> findLabInspectGradingByPoint(Integer point, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabInspectGradingByPoint", startResult, maxRows, point);
		return new LinkedHashSet<LabInspectGrading>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 *
	 *
	 */
	public boolean canBeMerged(LabInspectGrading entity) {
		return true;
	}
}
