package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.LabRoomReservationCredit;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage LabRoomReservationCredit entities.
 * 
 */
@Repository("LabRoomReservationCreditDAO")
@Transactional
public class LabRoomReservationCreditDAOImpl extends AbstractJpaDao<LabRoomReservationCredit>
		implements LabRoomReservationCreditDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabRoomReservationCredit.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabRoomReservationCreditDAOImpl
	 *
	 */
	public LabRoomReservationCreditDAOImpl() {
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
	 * JPQL Query - findLabRoomReservationCreditByRemarkContaining
	 *
	 */
	@Transactional
	public Set<LabRoomReservationCredit> findLabRoomReservationCreditByRemarkContaining(String remark) throws DataAccessException {

		return findLabRoomReservationCreditByRemarkContaining(remark, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomReservationCreditByRemarkContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomReservationCredit> findLabRoomReservationCreditByRemarkContaining(String remark, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomReservationCreditByRemarkContaining", startResult, maxRows, remark);
		return new LinkedHashSet<LabRoomReservationCredit>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabRoomReservationCredits
	 *
	 */
	@Transactional
	public Set<LabRoomReservationCredit> findAllLabRoomReservationCredits() throws DataAccessException {

		return findAllLabRoomReservationCredits(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabRoomReservationCredits
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomReservationCredit> findAllLabRoomReservationCredits(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabRoomReservationCredits", startResult, maxRows);
		return new LinkedHashSet<LabRoomReservationCredit>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomReservationCreditByPrimaryKey
	 *
	 */
	@Transactional
	public LabRoomReservationCredit findLabRoomReservationCreditByPrimaryKey(Integer id) throws DataAccessException {

		return findLabRoomReservationCreditByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomReservationCreditByPrimaryKey
	 *
	 */

	@Transactional
	public LabRoomReservationCredit findLabRoomReservationCreditByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomReservationCreditByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomReservationCredit) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabRoomReservationCreditById
	 *
	 */
	@Transactional
	public LabRoomReservationCredit findLabRoomReservationCreditById(Integer id) throws DataAccessException {

		return findLabRoomReservationCreditById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomReservationCreditById
	 *
	 */

	@Transactional
	public LabRoomReservationCredit findLabRoomReservationCreditById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomReservationCreditById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomReservationCredit) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabRoomReservationCreditByRemark
	 *
	 */
	@Transactional
	public Set<LabRoomReservationCredit> findLabRoomReservationCreditByRemark(String remark) throws DataAccessException {

		return findLabRoomReservationCreditByRemark(remark, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomReservationCreditByRemark
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomReservationCredit> findLabRoomReservationCreditByRemark(String remark, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomReservationCreditByRemark", startResult, maxRows, remark);
		return new LinkedHashSet<LabRoomReservationCredit>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(LabRoomReservationCredit entity) {
		return true;
	}
}
