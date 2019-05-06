package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.LabRoomDeviceReservationCredit;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage LabRoomDeviceReservationCredit entities.
 * 
 */
@Repository("LabRoomDeviceReservationCreditDAO")
@Transactional
public class LabRoomDeviceReservationCreditDAOImpl extends AbstractJpaDao<LabRoomDeviceReservationCredit>
		implements LabRoomDeviceReservationCreditDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabRoomDeviceReservationCredit.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabRoomDeviceReservationCreditDAOImpl
	 *
	 */
	public LabRoomDeviceReservationCreditDAOImpl() {
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
	 * JPQL Query - findLabRoomDeviceReservationCreditById
	 *
	 */
	@Transactional
	public LabRoomDeviceReservationCredit findLabRoomDeviceReservationCreditById(Integer id) throws DataAccessException {

		return findLabRoomDeviceReservationCreditById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomDeviceReservationCreditById
	 *
	 */

	@Transactional
	public LabRoomDeviceReservationCredit findLabRoomDeviceReservationCreditById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomDeviceReservationCreditById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomDeviceReservationCredit) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabRoomDeviceReservationCreditByRemarkContaining
	 *
	 */
	@Transactional
	public Set<LabRoomDeviceReservationCredit> findLabRoomDeviceReservationCreditByRemarkContaining(String remark) throws DataAccessException {

		return findLabRoomDeviceReservationCreditByRemarkContaining(remark, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomDeviceReservationCreditByRemarkContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomDeviceReservationCredit> findLabRoomDeviceReservationCreditByRemarkContaining(String remark, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomDeviceReservationCreditByRemarkContaining", startResult, maxRows, remark);
		return new LinkedHashSet<LabRoomDeviceReservationCredit>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomDeviceReservationCreditByPrimaryKey
	 *
	 */
	@Transactional
	public LabRoomDeviceReservationCredit findLabRoomDeviceReservationCreditByPrimaryKey(Integer id) throws DataAccessException {

		return findLabRoomDeviceReservationCreditByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomDeviceReservationCreditByPrimaryKey
	 *
	 */

	@Transactional
	public LabRoomDeviceReservationCredit findLabRoomDeviceReservationCreditByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomDeviceReservationCreditByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomDeviceReservationCredit) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabRoomDeviceReservationCreditByRemark
	 *
	 */
	@Transactional
	public Set<LabRoomDeviceReservationCredit> findLabRoomDeviceReservationCreditByRemark(String remark) throws DataAccessException {

		return findLabRoomDeviceReservationCreditByRemark(remark, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomDeviceReservationCreditByRemark
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomDeviceReservationCredit> findLabRoomDeviceReservationCreditByRemark(String remark, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomDeviceReservationCreditByRemark", startResult, maxRows, remark);
		return new LinkedHashSet<LabRoomDeviceReservationCredit>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabRoomDeviceReservationCredits
	 *
	 */
	@Transactional
	public Set<LabRoomDeviceReservationCredit> findAllLabRoomDeviceReservationCredits() throws DataAccessException {

		return findAllLabRoomDeviceReservationCredits(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabRoomDeviceReservationCredits
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomDeviceReservationCredit> findAllLabRoomDeviceReservationCredits(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabRoomDeviceReservationCredits", startResult, maxRows);
		return new LinkedHashSet<LabRoomDeviceReservationCredit>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(LabRoomDeviceReservationCredit entity) {
		return true;
	}
}
