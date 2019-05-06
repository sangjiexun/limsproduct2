package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.LabRoomLendingResult;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage LabRoomLendingResult entities.
 * 
 */
@Repository("LabRoomLendingResultDAO")
@Transactional
public class LabRoomLendingResultDAOImpl extends AbstractJpaDao<LabRoomLendingResult>
		implements LabRoomLendingResultDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabRoomLendingResult.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabRoomLendingResultDAOImpl
	 *
	 */
	public LabRoomLendingResultDAOImpl() {
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
	 * JPQL Query - findLabRoomLendingResultByRemark
	 *
	 */
	@Transactional
	public Set<LabRoomLendingResult> findLabRoomLendingResultByRemark(String remark) throws DataAccessException {

		return findLabRoomLendingResultByRemark(remark, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomLendingResultByRemark
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomLendingResult> findLabRoomLendingResultByRemark(String remark, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomLendingResultByRemark", startResult, maxRows, remark);
		return new LinkedHashSet<LabRoomLendingResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabRoomLendingResults
	 *
	 */
	@Transactional
	public Set<LabRoomLendingResult> findAllLabRoomLendingResults() throws DataAccessException {

		return findAllLabRoomLendingResults(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabRoomLendingResults
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomLendingResult> findAllLabRoomLendingResults(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabRoomLendingResults", startResult, maxRows);
		return new LinkedHashSet<LabRoomLendingResult>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomLendingResultById
	 *
	 */
	@Transactional
	public LabRoomLendingResult findLabRoomLendingResultById(Integer id) throws DataAccessException {

		return findLabRoomLendingResultById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomLendingResultById
	 *
	 */

	@Transactional
	public LabRoomLendingResult findLabRoomLendingResultById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomLendingResultById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomLendingResult) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabRoomLendingResultByPrimaryKey
	 *
	 */
	@Transactional
	public LabRoomLendingResult findLabRoomLendingResultByPrimaryKey(Integer id) throws DataAccessException {

		return findLabRoomLendingResultByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomLendingResultByPrimaryKey
	 *
	 */

	@Transactional
	public LabRoomLendingResult findLabRoomLendingResultByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomLendingResultByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomLendingResult) query.getSingleResult();
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
	public boolean canBeMerged(LabRoomLendingResult entity) {
		return true;
	}
}
