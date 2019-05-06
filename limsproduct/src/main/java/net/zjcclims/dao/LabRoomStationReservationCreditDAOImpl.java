package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.LabRoomStationReservationCredit;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage LabRoomStationReservationCredit entities.
 * 
 */
@Repository("LabRoomStationReservationCreditDAO")
@Transactional
public class LabRoomStationReservationCreditDAOImpl extends AbstractJpaDao<LabRoomStationReservationCredit>
		implements LabRoomStationReservationCreditDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabRoomStationReservationCredit.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabRoomStationReservationCreditDAOImpl
	 *
	 */
	public LabRoomStationReservationCreditDAOImpl() {
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
	 * JPQL Query - findLabRoomStationReservationCreditByRemarkContaining
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservationCredit> findLabRoomStationReservationCreditByRemarkContaining(String remark) throws DataAccessException {

		return findLabRoomStationReservationCreditByRemarkContaining(remark, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationCreditByRemarkContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservationCredit> findLabRoomStationReservationCreditByRemarkContaining(String remark, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationCreditByRemarkContaining", startResult, maxRows, remark);
		return new LinkedHashSet<LabRoomStationReservationCredit>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomStationReservationCreditByPrimaryKey
	 *
	 */
	@Transactional
	public LabRoomStationReservationCredit findLabRoomStationReservationCreditByPrimaryKey(Integer id) throws DataAccessException {

		return findLabRoomStationReservationCreditByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationCreditByPrimaryKey
	 *
	 */

	@Transactional
	public LabRoomStationReservationCredit findLabRoomStationReservationCreditByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomStationReservationCreditByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomStationReservationCredit) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabRoomStationReservationCreditById
	 *
	 */
	@Transactional
	public LabRoomStationReservationCredit findLabRoomStationReservationCreditById(Integer id) throws DataAccessException {

		return findLabRoomStationReservationCreditById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationCreditById
	 *
	 */

	@Transactional
	public LabRoomStationReservationCredit findLabRoomStationReservationCreditById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomStationReservationCreditById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomStationReservationCredit) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllLabRoomStationReservationCredits
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservationCredit> findAllLabRoomStationReservationCredits() throws DataAccessException {

		return findAllLabRoomStationReservationCredits(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabRoomStationReservationCredits
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservationCredit> findAllLabRoomStationReservationCredits(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabRoomStationReservationCredits", startResult, maxRows);
		return new LinkedHashSet<LabRoomStationReservationCredit>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomStationReservationCreditByRemark
	 *
	 */
	@Transactional
	public Set<LabRoomStationReservationCredit> findLabRoomStationReservationCreditByRemark(String remark) throws DataAccessException {

		return findLabRoomStationReservationCreditByRemark(remark, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomStationReservationCreditByRemark
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomStationReservationCredit> findLabRoomStationReservationCreditByRemark(String remark, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomStationReservationCreditByRemark", startResult, maxRows, remark);
		return new LinkedHashSet<LabRoomStationReservationCredit>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(LabRoomStationReservationCredit entity) {
		return true;
	}
}
