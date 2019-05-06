package net.zjcclims.dao;

import net.zjcclims.domain.LabInspect;
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
 * DAO to manage LabInspect entities.
 *
 */
@Repository("LabInspectDAO")
@Transactional
public class LabInspectDAOImpl extends AbstractJpaDao<LabInspect>
		implements LabInspectDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabInspect.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclims
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabInspectDAOImpl
	 *
	 */
	public LabInspectDAOImpl() {
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
	 * JPQL Query - findLabInspectByPrimaryKey
	 *
	 */
	@Transactional
	public LabInspect findLabInspectByPrimaryKey(Integer id) throws DataAccessException {

		return findLabInspectByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectByPrimaryKey
	 *
	 */

	@Transactional
	public LabInspect findLabInspectByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabInspectByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabInspect) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllLabInspects
	 *
	 */
	@Transactional
	public Set<LabInspect> findAllLabInspects() throws DataAccessException {

		return findAllLabInspects(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabInspects
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabInspect> findAllLabInspects(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabInspects", startResult, maxRows);
		return new LinkedHashSet<LabInspect>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabInspectByStandardNameContaining
	 *
	 */
	@Transactional
	public Set<LabInspect> findLabInspectByStandardNameContaining(String standardName) throws DataAccessException {

		return findLabInspectByStandardNameContaining(standardName, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectByStandardNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabInspect> findLabInspectByStandardNameContaining(String standardName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabInspectByStandardNameContaining", startResult, maxRows, standardName);
		return new LinkedHashSet<LabInspect>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabInspectById
	 *
	 */
	@Transactional
	public LabInspect findLabInspectById(Integer id) throws DataAccessException {

		return findLabInspectById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectById
	 *
	 */

	@Transactional
	public LabInspect findLabInspectById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabInspectById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabInspect) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabInspectByStandardName
	 *
	 */
	@Transactional
	public Set<LabInspect> findLabInspectByStandardName(String standardName) throws DataAccessException {

		return findLabInspectByStandardName(standardName, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectByStandardName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabInspect> findLabInspectByStandardName(String standardName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabInspectByStandardName", startResult, maxRows, standardName);
		return new LinkedHashSet<LabInspect>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 *
	 *
	 */
	public boolean canBeMerged(LabInspect entity) {
		return true;
	}
}
