package net.zjcclims.dao;

import net.zjcclims.domain.LabSecurityCheckDetails;
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
 * DAO to manage LabSecurityCheckDetails entities.
 * 
 */
@Repository("LabSecurityCheckDetailsDAO")
@Transactional
public class LabSecurityCheckDetailsDAOImpl extends AbstractJpaDao<LabSecurityCheckDetails>
		implements LabSecurityCheckDetailsDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabSecurityCheckDetails.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabSecurityCheckDetailsDAOImpl
	 *
	 */
	public LabSecurityCheckDetailsDAOImpl() {
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
	 * JPQL Query - findAllLabSecurityCheckDetailss
	 *
	 */
	@Transactional
	public Set<LabSecurityCheckDetails> findAllLabSecurityCheckDetailss() throws DataAccessException {

		return findAllLabSecurityCheckDetailss(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabSecurityCheckDetailss
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabSecurityCheckDetails> findAllLabSecurityCheckDetailss(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabSecurityCheckDetailss", startResult, maxRows);
		return new LinkedHashSet<LabSecurityCheckDetails>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabSecurityCheckDetailsById
	 *
	 */
	@Transactional
	public LabSecurityCheckDetails findLabSecurityCheckDetailsById(Integer id) throws DataAccessException {

		return findLabSecurityCheckDetailsById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabSecurityCheckDetailsById
	 *
	 */

	@Transactional
	public LabSecurityCheckDetails findLabSecurityCheckDetailsById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabSecurityCheckDetailsById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabSecurityCheckDetails) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabSecurityCheckDetailsByPrimaryKey
	 *
	 */
	@Transactional
	public LabSecurityCheckDetails findLabSecurityCheckDetailsByPrimaryKey(Integer id) throws DataAccessException {

		return findLabSecurityCheckDetailsByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabSecurityCheckDetailsByPrimaryKey
	 *
	 */

	@Transactional
	public LabSecurityCheckDetails findLabSecurityCheckDetailsByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabSecurityCheckDetailsByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabSecurityCheckDetails) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabSecurityCheckDetailsByResult
	 *
	 */
	@Transactional
	public Set<LabSecurityCheckDetails> findLabSecurityCheckDetailsByResult(String result) throws DataAccessException {

		return findLabSecurityCheckDetailsByResult(result, -1, -1);
	}

	/**
	 * JPQL Query - findLabSecurityCheckDetailsByResult
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabSecurityCheckDetails> findLabSecurityCheckDetailsByResult(String result, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabSecurityCheckDetailsByResult", startResult, maxRows, result);
		return new LinkedHashSet<LabSecurityCheckDetails>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabSecurityCheckDetailsByResultContaining
	 *
	 */
	@Transactional
	public Set<LabSecurityCheckDetails> findLabSecurityCheckDetailsByResultContaining(String result) throws DataAccessException {

		return findLabSecurityCheckDetailsByResultContaining(result, -1, -1);
	}

	/**
	 * JPQL Query - findLabSecurityCheckDetailsByResultContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabSecurityCheckDetails> findLabSecurityCheckDetailsByResultContaining(String result, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabSecurityCheckDetailsByResultContaining", startResult, maxRows, result);
		return new LinkedHashSet<LabSecurityCheckDetails>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(LabSecurityCheckDetails entity) {
		return true;
	}
}
