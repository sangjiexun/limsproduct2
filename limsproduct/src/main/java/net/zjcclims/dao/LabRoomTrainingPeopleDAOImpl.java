package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.LabRoomTrainingPeople;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage LabRoomTrainingPeople entities.
 * 
 */
@Repository("LabRoomTrainingPeopleDAO")
@Transactional
public class LabRoomTrainingPeopleDAOImpl extends AbstractJpaDao<LabRoomTrainingPeople>
		implements LabRoomTrainingPeopleDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabRoomTrainingPeople.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabRoomTrainingPeopleDAOImpl
	 *
	 */
	public LabRoomTrainingPeopleDAOImpl() {
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
	 * JPQL Query - findLabRoomTrainingPeopleByTelephone
	 *
	 */
	@Transactional
	public Set<LabRoomTrainingPeople> findLabRoomTrainingPeopleByTelephone(String telephone) throws DataAccessException {

		return findLabRoomTrainingPeopleByTelephone(telephone, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByTelephone
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomTrainingPeople> findLabRoomTrainingPeopleByTelephone(String telephone, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomTrainingPeopleByTelephone", startResult, maxRows, telephone);
		return new LinkedHashSet<LabRoomTrainingPeople>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabRoomTrainingPeoples
	 *
	 */
	@Transactional
	public Set<LabRoomTrainingPeople> findAllLabRoomTrainingPeoples() throws DataAccessException {

		return findAllLabRoomTrainingPeoples(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabRoomTrainingPeoples
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomTrainingPeople> findAllLabRoomTrainingPeoples(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabRoomTrainingPeoples", startResult, maxRows);
		return new LinkedHashSet<LabRoomTrainingPeople>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByEMailContaining
	 *
	 */
	@Transactional
	public Set<LabRoomTrainingPeople> findLabRoomTrainingPeopleByEMailContaining(String EMail) throws DataAccessException {

		return findLabRoomTrainingPeopleByEMailContaining(EMail, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByEMailContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomTrainingPeople> findLabRoomTrainingPeopleByEMailContaining(String EMail, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomTrainingPeopleByEMailContaining", startResult, maxRows, EMail);
		return new LinkedHashSet<LabRoomTrainingPeople>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByMessageFlag
	 *
	 */
	@Transactional
	public Set<LabRoomTrainingPeople> findLabRoomTrainingPeopleByMessageFlag(Integer messageFlag) throws DataAccessException {

		return findLabRoomTrainingPeopleByMessageFlag(messageFlag, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByMessageFlag
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomTrainingPeople> findLabRoomTrainingPeopleByMessageFlag(Integer messageFlag, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomTrainingPeopleByMessageFlag", startResult, maxRows, messageFlag);
		return new LinkedHashSet<LabRoomTrainingPeople>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByTelephoneContaining
	 *
	 */
	@Transactional
	public Set<LabRoomTrainingPeople> findLabRoomTrainingPeopleByTelephoneContaining(String telephone) throws DataAccessException {

		return findLabRoomTrainingPeopleByTelephoneContaining(telephone, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByTelephoneContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomTrainingPeople> findLabRoomTrainingPeopleByTelephoneContaining(String telephone, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomTrainingPeopleByTelephoneContaining", startResult, maxRows, telephone);
		return new LinkedHashSet<LabRoomTrainingPeople>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByEMail
	 *
	 */
	@Transactional
	public Set<LabRoomTrainingPeople> findLabRoomTrainingPeopleByEMail(String EMail) throws DataAccessException {

		return findLabRoomTrainingPeopleByEMail(EMail, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByEMail
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomTrainingPeople> findLabRoomTrainingPeopleByEMail(String EMail, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomTrainingPeopleByEMail", startResult, maxRows, EMail);
		return new LinkedHashSet<LabRoomTrainingPeople>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomTrainingPeopleById
	 *
	 */
	@Transactional
	public LabRoomTrainingPeople findLabRoomTrainingPeopleById(Integer id) throws DataAccessException {

		return findLabRoomTrainingPeopleById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomTrainingPeopleById
	 *
	 */

	@Transactional
	public LabRoomTrainingPeople findLabRoomTrainingPeopleById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomTrainingPeopleById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomTrainingPeople) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByPrimaryKey
	 *
	 */
	@Transactional
	public LabRoomTrainingPeople findLabRoomTrainingPeopleByPrimaryKey(Integer id) throws DataAccessException {

		return findLabRoomTrainingPeopleByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomTrainingPeopleByPrimaryKey
	 *
	 */

	@Transactional
	public LabRoomTrainingPeople findLabRoomTrainingPeopleByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomTrainingPeopleByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomTrainingPeople) query.getSingleResult();
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
	public boolean canBeMerged(LabRoomTrainingPeople entity) {
		return true;
	}
}
