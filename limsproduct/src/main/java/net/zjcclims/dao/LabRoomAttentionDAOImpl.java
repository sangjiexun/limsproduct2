package net.zjcclims.dao;

import net.zjcclims.domain.LabRoomAttention;
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
 * DAO to manage LabRoomAttention entities.
 * 
 */
@Repository("LabRoomAttentionDAO")
@Transactional
public class LabRoomAttentionDAOImpl extends AbstractJpaDao<LabRoomAttention>
		implements LabRoomAttentionDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabRoomAttention.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabRoomAttentionDAOImpl
	 *
	 */
	public LabRoomAttentionDAOImpl() {
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
	 * JPQL Query - findLabRoomAttentionByDate
	 *
	 */
	@Transactional
	public Set<LabRoomAttention> findLabRoomAttentionByDate(java.util.Calendar date) throws DataAccessException {

		return findLabRoomAttentionByDate(date, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomAttentionByDate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomAttention> findLabRoomAttentionByDate(java.util.Calendar date, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomAttentionByDate", startResult, maxRows, date);
		return new LinkedHashSet<LabRoomAttention>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomAttentionByUsernameContaining
	 *
	 */
	@Transactional
	public Set<LabRoomAttention> findLabRoomAttentionByUsernameContaining(String username) throws DataAccessException {

		return findLabRoomAttentionByUsernameContaining(username, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomAttentionByUsernameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomAttention> findLabRoomAttentionByUsernameContaining(String username, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomAttentionByUsernameContaining", startResult, maxRows, username);
		return new LinkedHashSet<LabRoomAttention>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomAttentionByCnameContaining
	 *
	 */
	@Transactional
	public Set<LabRoomAttention> findLabRoomAttentionByCnameContaining(String cname) throws DataAccessException {

		return findLabRoomAttentionByCnameContaining(cname, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomAttentionByCnameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomAttention> findLabRoomAttentionByCnameContaining(String cname, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomAttentionByCnameContaining", startResult, maxRows, cname);
		return new LinkedHashSet<LabRoomAttention>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomAttentionByPrimaryKey
	 *
	 */
	@Transactional
	public LabRoomAttention findLabRoomAttentionByPrimaryKey(Integer id) throws DataAccessException {

		return findLabRoomAttentionByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomAttentionByPrimaryKey
	 *
	 */

	@Transactional
	public LabRoomAttention findLabRoomAttentionByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomAttentionByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomAttention) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabRoomAttentionByUsername
	 *
	 */
	@Transactional
	public Set<LabRoomAttention> findLabRoomAttentionByUsername(String username) throws DataAccessException {

		return findLabRoomAttentionByUsername(username, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomAttentionByUsername
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomAttention> findLabRoomAttentionByUsername(String username, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomAttentionByUsername", startResult, maxRows, username);
		return new LinkedHashSet<LabRoomAttention>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomAttentionByCname
	 *
	 */
	@Transactional
	public Set<LabRoomAttention> findLabRoomAttentionByCname(String cname) throws DataAccessException {

		return findLabRoomAttentionByCname(cname, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomAttentionByCname
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomAttention> findLabRoomAttentionByCname(String cname, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomAttentionByCname", startResult, maxRows, cname);
		return new LinkedHashSet<LabRoomAttention>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomAttentionByEnable
	 *
	 */
	@Transactional
	public Set<LabRoomAttention> findLabRoomAttentionByEnable(Integer enable) throws DataAccessException {

		return findLabRoomAttentionByEnable(enable, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomAttentionByEnable
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomAttention> findLabRoomAttentionByEnable(Integer enable, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomAttentionByEnable", startResult, maxRows, enable);
		return new LinkedHashSet<LabRoomAttention>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabRoomAttentions
	 *
	 */
	@Transactional
	public Set<LabRoomAttention> findAllLabRoomAttentions() throws DataAccessException {

		return findAllLabRoomAttentions(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabRoomAttentions
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomAttention> findAllLabRoomAttentions(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabRoomAttentions", startResult, maxRows);
		return new LinkedHashSet<LabRoomAttention>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomAttentionById
	 *
	 */
	@Transactional
	public LabRoomAttention findLabRoomAttentionById(Integer id) throws DataAccessException {

		return findLabRoomAttentionById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomAttentionById
	 *
	 */

	@Transactional
	public LabRoomAttention findLabRoomAttentionById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomAttentionById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabRoomAttention) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabRoomAttentionByLabId
	 *
	 */
	@Transactional
	public Set<LabRoomAttention> findLabRoomAttentionByLabId(Integer labId) throws DataAccessException {

		return findLabRoomAttentionByLabId(labId, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomAttentionByLabId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomAttention> findLabRoomAttentionByLabId(Integer labId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabRoomAttentionByLabId", startResult, maxRows, labId);
		return new LinkedHashSet<LabRoomAttention>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(LabRoomAttention entity) {
		return true;
	}
}
