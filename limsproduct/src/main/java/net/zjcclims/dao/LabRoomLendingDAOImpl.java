package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

import net.zjcclims.domain.LabRoomLending;

/**
 * DAO to manage LabRoomLending entities.
 * 
 */
@Repository("LabRoomLendingDAO")
@Transactional
public class LabRoomLendingDAOImpl extends AbstractJpaDao<LabRoomLending>
		implements LabRoomLendingDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabRoomLending.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabRoomLendingDAOImpl
	 *
	 */
	public LabRoomLendingDAOImpl() {
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
	 * JPQL Query - findLabRoomLendingByClass_
	 *
	 */
	/*@Transactional
	public Set<LabRoomLending> findLabRoomLendingByClass_(String class_) throws DataAccessException {

		return findLabRoomLendingByClass_(class_, -1, -1);
	}*/

	/**
	 * JPQL Query - findLabRoomLendingByClass_
	 *
	 */

	/*@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomLending> findLabRoomLendingByClass_(String class_, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomLendingByClass_", startResult, maxRows, class_);
		return new LinkedHashSet<LabRoomLending>(query.getResultList());
	}*/

	/**
	 * JPQL Query - findAllLabRoomLendings
	 *
	 */
	@Transactional
	public Set<LabRoomLending> findAllLabRoomLendings() throws DataAccessException {

		return findAllLabRoomLendings(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabRoomLendings
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomLending> findAllLabRoomLendings(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabRoomLendings", startResult, maxRows);
		return new LinkedHashSet<LabRoomLending>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomLendingByLendingTime
	 *
	 */
	@Transactional
	public Set<LabRoomLending> findLabRoomLendingByLendingTime(java.util.Calendar lendingTime) throws DataAccessException {

		return findLabRoomLendingByLendingTime(lendingTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomLendingByLendingTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomLending> findLabRoomLendingByLendingTime(java.util.Calendar lendingTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomLendingByLendingTime", startResult, maxRows, lendingTime);
		return new LinkedHashSet<LabRoomLending>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomLendingByPrimaryKey
	 *
	 */
	@Transactional
	public LabRoomLending findLabRoomLendingByPrimaryKey(Integer id) throws DataAccessException {

		return findLabRoomLendingByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomLendingByPrimaryKey
	 *
	 */

	@Transactional
	public LabRoomLending findLabRoomLendingByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomLendingByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomLending) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabRoomLendingByLendingReason
	 *
	 */
	@Transactional
	public Set<LabRoomLending> findLabRoomLendingByLendingReason(String lendingReason) throws DataAccessException {

		return findLabRoomLendingByLendingReason(lendingReason, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomLendingByLendingReason
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomLending> findLabRoomLendingByLendingReason(String lendingReason, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomLendingByLendingReason", startResult, maxRows, lendingReason);
		return new LinkedHashSet<LabRoomLending>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomLendingByClass_Containing
	 *
	 */
	/*@Transactional
	public Set<LabRoomLending> findLabRoomLendingByClass_Containing(String class_) throws DataAccessException {

		return findLabRoomLendingByClass_Containing(class_, -1, -1);
	}*/

	/**
	 * JPQL Query - findLabRoomLendingByClass_Containing
	 *
	 */

	/*@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomLending> findLabRoomLendingByClass_Containing(String class_, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomLendingByClass_Containing", startResult, maxRows, class_);
		return new LinkedHashSet<LabRoomLending>(query.getResultList());
	}*/

	/**
	 * JPQL Query - findLabRoomLendingByLendingUserPhoneContaining
	 *
	 */
	@Transactional
	public Set<LabRoomLending> findLabRoomLendingByLendingUserPhoneContaining(String lendingUserPhone) throws DataAccessException {

		return findLabRoomLendingByLendingUserPhoneContaining(lendingUserPhone, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomLendingByLendingUserPhoneContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomLending> findLabRoomLendingByLendingUserPhoneContaining(String lendingUserPhone, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomLendingByLendingUserPhoneContaining", startResult, maxRows, lendingUserPhone);
		return new LinkedHashSet<LabRoomLending>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomLendingByLendingReasonContaining
	 *
	 */
	@Transactional
	public Set<LabRoomLending> findLabRoomLendingByLendingReasonContaining(String lendingReason) throws DataAccessException {

		return findLabRoomLendingByLendingReasonContaining(lendingReason, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomLendingByLendingReasonContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomLending> findLabRoomLendingByLendingReasonContaining(String lendingReason, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomLendingByLendingReasonContaining", startResult, maxRows, lendingReason);
		return new LinkedHashSet<LabRoomLending>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomLendingById
	 *
	 */
	@Transactional
	public LabRoomLending findLabRoomLendingById(Integer id) throws DataAccessException {

		return findLabRoomLendingById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomLendingById
	 *
	 */

	@Transactional
	public LabRoomLending findLabRoomLendingById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomLendingById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomLending) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabRoomLendingByApplyDate
	 *
	 */
	@Transactional
	public Set<LabRoomLending> findLabRoomLendingByApplyDate(java.util.Calendar applyDate) throws DataAccessException {

		return findLabRoomLendingByApplyDate(applyDate, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomLendingByApplyDate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomLending> findLabRoomLendingByApplyDate(java.util.Calendar applyDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomLendingByApplyDate", startResult, maxRows, applyDate);
		return new LinkedHashSet<LabRoomLending>(query.getResultList());
	}
	
	/**
	 * JPQL Query - findLabRoomLendingByLendingStatus
	 *
	 */
	@Transactional
	public Set<LabRoomLending> findLabRoomLendingByLendingStatus(Integer lendingStatus) throws DataAccessException {

		return findLabRoomLendingByLendingStatus(lendingStatus, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomLendingByLendingStatus
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomLending> findLabRoomLendingByLendingStatus(Integer lendingStatus, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomLendingByLendingStatus", startResult, maxRows, lendingStatus);
		return new LinkedHashSet<LabRoomLending>(query.getResultList());
	}
	
	/**
	 * JPQL Query - findLabRoomLendingByLendingUserNumber
	 *
	 */
	@Transactional
	public Set<LabRoomLending> findLabRoomLendingByLendingUserNumber(Integer lendingUserNumber) throws DataAccessException {

		return findLabRoomLendingByLendingUserNumber(lendingUserNumber, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomLendingByLendingUserNumber
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomLending> findLabRoomLendingByLendingUserNumber(Integer lendingUserNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomLendingByLendingUserNumber", startResult, maxRows, lendingUserNumber);
		return new LinkedHashSet<LabRoomLending>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomLendingByLendingUserPhone
	 *
	 */
	@Transactional
	public Set<LabRoomLending> findLabRoomLendingByLendingUserPhone(String lendingUserPhone) throws DataAccessException {

		return findLabRoomLendingByLendingUserPhone(lendingUserPhone, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomLendingByLendingUserPhone
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomLending> findLabRoomLendingByLendingUserPhone(String lendingUserPhone, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomLendingByLendingUserPhone", startResult, maxRows, lendingUserPhone);
		return new LinkedHashSet<LabRoomLending>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(LabRoomLending entity) {
		return true;
	}
}
