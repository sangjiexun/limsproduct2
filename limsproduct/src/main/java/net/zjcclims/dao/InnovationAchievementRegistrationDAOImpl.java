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

import net.zjcclims.domain.InnovationAchievementRegistration;

/**
 * DAO to manage InnovationAchievementRegistration entities.
 * 
 */
@Repository("InnovationAchievementRegistrationDAO")
@Transactional
public class InnovationAchievementRegistrationDAOImpl extends AbstractJpaDao<InnovationAchievementRegistration>
		implements InnovationAchievementRegistrationDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { InnovationAchievementRegistration.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new InnovationAchievementRegistrationDAOImpl
	 *
	 */
	public InnovationAchievementRegistrationDAOImpl() {
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
	 * JPQL Query - findInnovationAchievementRegistrationByLabRoomNameContaining
	 *
	 */
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByLabRoomNameContaining(String labRoomName) throws DataAccessException {

		return findInnovationAchievementRegistrationByLabRoomNameContaining(labRoomName, -1, -1);
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByLabRoomNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByLabRoomNameContaining(String labRoomName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findInnovationAchievementRegistrationByLabRoomNameContaining", startResult, maxRows, labRoomName);
		return new LinkedHashSet<InnovationAchievementRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByLabRoomNumber
	 *
	 */
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByLabRoomNumber(String labRoomNumber) throws DataAccessException {

		return findInnovationAchievementRegistrationByLabRoomNumber(labRoomNumber, -1, -1);
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByLabRoomNumber
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByLabRoomNumber(String labRoomNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findInnovationAchievementRegistrationByLabRoomNumber", startResult, maxRows, labRoomNumber);
		return new LinkedHashSet<InnovationAchievementRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllInnovationAchievementRegistrations
	 *
	 */
	@Transactional
	public Set<InnovationAchievementRegistration> findAllInnovationAchievementRegistrations() throws DataAccessException {

		return findAllInnovationAchievementRegistrations(-1, -1);
	}

	/**
	 * JPQL Query - findAllInnovationAchievementRegistrations
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<InnovationAchievementRegistration> findAllInnovationAchievementRegistrations(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllInnovationAchievementRegistrations", startResult, maxRows);
		return new LinkedHashSet<InnovationAchievementRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByLabRoomNumberContaining
	 *
	 */
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByLabRoomNumberContaining(String labRoomNumber) throws DataAccessException {

		return findInnovationAchievementRegistrationByLabRoomNumberContaining(labRoomNumber, -1, -1);
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByLabRoomNumberContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByLabRoomNumberContaining(String labRoomNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findInnovationAchievementRegistrationByLabRoomNumberContaining", startResult, maxRows, labRoomNumber);
		return new LinkedHashSet<InnovationAchievementRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByScoreContaining
	 *
	 */
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByScoreContaining(String score) throws DataAccessException {

		return findInnovationAchievementRegistrationByScoreContaining(score, -1, -1);
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByScoreContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByScoreContaining(String score, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findInnovationAchievementRegistrationByScoreContaining", startResult, maxRows, score);
		return new LinkedHashSet<InnovationAchievementRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByCname
	 *
	 */
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByCname(String cname) throws DataAccessException {

		return findInnovationAchievementRegistrationByCname(cname, -1, -1);
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByCname
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByCname(String cname, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findInnovationAchievementRegistrationByCname", startResult, maxRows, cname);
		return new LinkedHashSet<InnovationAchievementRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByInnovationNameContaining
	 *
	 */
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByInnovationNameContaining(String innovationName) throws DataAccessException {

		return findInnovationAchievementRegistrationByInnovationNameContaining(innovationName, -1, -1);
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByInnovationNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByInnovationNameContaining(String innovationName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findInnovationAchievementRegistrationByInnovationNameContaining", startResult, maxRows, innovationName);
		return new LinkedHashSet<InnovationAchievementRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByInnovationName
	 *
	 */
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByInnovationName(String innovationName) throws DataAccessException {

		return findInnovationAchievementRegistrationByInnovationName(innovationName, -1, -1);
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByInnovationName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByInnovationName(String innovationName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findInnovationAchievementRegistrationByInnovationName", startResult, maxRows, innovationName);
		return new LinkedHashSet<InnovationAchievementRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByUsernameContaining
	 *
	 */
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByUsernameContaining(String username) throws DataAccessException {

		return findInnovationAchievementRegistrationByUsernameContaining(username, -1, -1);
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByUsernameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByUsernameContaining(String username, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findInnovationAchievementRegistrationByUsernameContaining", startResult, maxRows, username);
		return new LinkedHashSet<InnovationAchievementRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByScore
	 *
	 */
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByScore(String score) throws DataAccessException {

		return findInnovationAchievementRegistrationByScore(score, -1, -1);
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByScore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByScore(String score, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findInnovationAchievementRegistrationByScore", startResult, maxRows, score);
		return new LinkedHashSet<InnovationAchievementRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationById
	 *
	 */
	@Transactional
	public InnovationAchievementRegistration findInnovationAchievementRegistrationById(Integer id) throws DataAccessException {

		return findInnovationAchievementRegistrationById(id, -1, -1);
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationById
	 *
	 */

	@Transactional
	public InnovationAchievementRegistration findInnovationAchievementRegistrationById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findInnovationAchievementRegistrationById", startResult, maxRows, id);
			return (net.zjcclims.domain.InnovationAchievementRegistration) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByUsername
	 *
	 */
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByUsername(String username) throws DataAccessException {

		return findInnovationAchievementRegistrationByUsername(username, -1, -1);
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByUsername
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByUsername(String username, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findInnovationAchievementRegistrationByUsername", startResult, maxRows, username);
		return new LinkedHashSet<InnovationAchievementRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByPrimaryKey
	 *
	 */
	@Transactional
	public InnovationAchievementRegistration findInnovationAchievementRegistrationByPrimaryKey(Integer id) throws DataAccessException {

		return findInnovationAchievementRegistrationByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByPrimaryKey
	 *
	 */

	@Transactional
	public InnovationAchievementRegistration findInnovationAchievementRegistrationByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findInnovationAchievementRegistrationByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.InnovationAchievementRegistration) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByCnameContaining
	 *
	 */
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByCnameContaining(String cname) throws DataAccessException {

		return findInnovationAchievementRegistrationByCnameContaining(cname, -1, -1);
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByCnameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByCnameContaining(String cname, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findInnovationAchievementRegistrationByCnameContaining", startResult, maxRows, cname);
		return new LinkedHashSet<InnovationAchievementRegistration>(query.getResultList());
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByLabRoomName
	 *
	 */
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByLabRoomName(String labRoomName) throws DataAccessException {

		return findInnovationAchievementRegistrationByLabRoomName(labRoomName, -1, -1);
	}

	/**
	 * JPQL Query - findInnovationAchievementRegistrationByLabRoomName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<InnovationAchievementRegistration> findInnovationAchievementRegistrationByLabRoomName(String labRoomName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findInnovationAchievementRegistrationByLabRoomName", startResult, maxRows, labRoomName);
		return new LinkedHashSet<InnovationAchievementRegistration>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(InnovationAchievementRegistration entity) {
		return true;
	}
}
