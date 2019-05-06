
package net.zjcclims.dao;

import net.zjcclims.domain.AuditSerialNumber;
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
 * DAO to manage AuditSerialNumber entities.
 * 
 */
@Repository("AuditSerialNumberDAO")

@Transactional
public class AuditSerialNumberDAOImpl extends AbstractJpaDao<AuditSerialNumber> implements AuditSerialNumberDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] {
			AuditSerialNumber.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new AuditSerialNumberDAOImpl
	 *
	 */
	public AuditSerialNumberDAOImpl() {
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
	 * JPQL Query - findAuditSerialNumberByUuid
	 *
	 */
	@Transactional
	public AuditSerialNumber findAuditSerialNumberByUuid(String uuid) throws DataAccessException {

		return findAuditSerialNumberByUuid(uuid, -1, -1);
	}

	/**
	 * JPQL Query - findAuditSerialNumberByUuid
	 *
	 */

	@Transactional
	public AuditSerialNumber findAuditSerialNumberByUuid(String uuid, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findAuditSerialNumberByUuid", startResult, maxRows, uuid);
			return (AuditSerialNumber) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAuditSerialNumberByBusinessType
	 *
	 */
	@Transactional
	public Set<AuditSerialNumber> findAuditSerialNumberByBusinessType(String businessType) throws DataAccessException {

		return findAuditSerialNumberByBusinessType(businessType, -1, -1);
	}

	/**
	 * JPQL Query - findAuditSerialNumberByBusinessType
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<AuditSerialNumber> findAuditSerialNumberByBusinessType(String businessType, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAuditSerialNumberByBusinessType", startResult, maxRows, businessType);
		return new LinkedHashSet<AuditSerialNumber>(query.getResultList());
	}

	/**
	 * JPQL Query - findAuditSerialNumberByBusinessId
	 *
	 */
	@Transactional
	public Set<AuditSerialNumber> findAuditSerialNumberByBusinessId(String businessId) throws DataAccessException {

		return findAuditSerialNumberByBusinessId(businessId, -1, -1);
	}

	/**
	 * JPQL Query - findAuditSerialNumberByBusinessId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<AuditSerialNumber> findAuditSerialNumberByBusinessId(String businessId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAuditSerialNumberByBusinessId", startResult, maxRows, businessId);
		return new LinkedHashSet<AuditSerialNumber>(query.getResultList());
	}

	/**
	 * JPQL Query - findAuditSerialNumberByPrimaryKey
	 *
	 */
	@Transactional
	public AuditSerialNumber findAuditSerialNumberByPrimaryKey(String uuid) throws DataAccessException {

		return findAuditSerialNumberByPrimaryKey(uuid, -1, -1);
	}

	/**
	 * JPQL Query - findAuditSerialNumberByPrimaryKey
	 *
	 */

	@Transactional
	public AuditSerialNumber findAuditSerialNumberByPrimaryKey(String uuid, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findAuditSerialNumberByPrimaryKey", startResult, maxRows, uuid);
			return (AuditSerialNumber) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAuditSerialNumberByUuidContaining
	 *
	 */
	@Transactional
	public Set<AuditSerialNumber> findAuditSerialNumberByUuidContaining(String uuid) throws DataAccessException {

		return findAuditSerialNumberByUuidContaining(uuid, -1, -1);
	}

	/**
	 * JPQL Query - findAuditSerialNumberByUuidContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<AuditSerialNumber> findAuditSerialNumberByUuidContaining(String uuid, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAuditSerialNumberByUuidContaining", startResult, maxRows, uuid);
		return new LinkedHashSet<AuditSerialNumber>(query.getResultList());
	}

	/**
	 * JPQL Query - findAuditSerialNumberByBusinessTypeContaining
	 *
	 */
	@Transactional
	public Set<AuditSerialNumber> findAuditSerialNumberByBusinessTypeContaining(String businessType) throws DataAccessException {

		return findAuditSerialNumberByBusinessTypeContaining(businessType, -1, -1);
	}

	/**
	 * JPQL Query - findAuditSerialNumberByBusinessTypeContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<AuditSerialNumber> findAuditSerialNumberByBusinessTypeContaining(String businessType, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAuditSerialNumberByBusinessTypeContaining", startResult, maxRows, businessType);
		return new LinkedHashSet<AuditSerialNumber>(query.getResultList());
	}

	/**
	 * JPQL Query - findAuditSerialNumberByEnable
	 *
	 */
	@Transactional
	public Set<AuditSerialNumber> findAuditSerialNumberByEnable(Boolean enable) throws DataAccessException {

		return findAuditSerialNumberByEnable(enable, -1, -1);
	}

	/**
	 * JPQL Query - findAuditSerialNumberByEnable
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<AuditSerialNumber> findAuditSerialNumberByEnable(Boolean enable, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAuditSerialNumberByEnable", startResult, maxRows, enable);
		return new LinkedHashSet<AuditSerialNumber>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllAuditSerialNumbers
	 *
	 */
	@Transactional
	public Set<AuditSerialNumber> findAllAuditSerialNumbers() throws DataAccessException {

		return findAllAuditSerialNumbers(-1, -1);
	}

	/**
	 * JPQL Query - findAllAuditSerialNumbers
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<AuditSerialNumber> findAllAuditSerialNumbers(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllAuditSerialNumbers", startResult, maxRows);
		return new LinkedHashSet<AuditSerialNumber>(query.getResultList());
	}

	/**
	 * JPQL Query - findAuditSerialNumberByBusinessIdContaining
	 *
	 */
	@Transactional
	public Set<AuditSerialNumber> findAuditSerialNumberByBusinessIdContaining(String businessId) throws DataAccessException {

		return findAuditSerialNumberByBusinessIdContaining(businessId, -1, -1);
	}

	/**
	 * JPQL Query - findAuditSerialNumberByBusinessIdContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<AuditSerialNumber> findAuditSerialNumberByBusinessIdContaining(String businessId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAuditSerialNumberByBusinessIdContaining", startResult, maxRows, businessId);
		return new LinkedHashSet<AuditSerialNumber>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(AuditSerialNumber entity) {
		return true;
	}
}
