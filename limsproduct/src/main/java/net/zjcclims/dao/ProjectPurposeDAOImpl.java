package net.zjcclims.dao;


import net.zjcclims.domain.ProjectPurpose;
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
 * DAO to manage ProjectPurpose entities.
 * 
 */
@Repository("ProjectPurposeDAO")
@Transactional
public class ProjectPurposeDAOImpl extends AbstractJpaDao<ProjectPurpose>
		implements ProjectPurposeDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ProjectPurpose.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ProjectPurposeDAOImpl
	 *
	 */
	public ProjectPurposeDAOImpl() {
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
	 * JPQL Query - findProjectPurposeById
	 *
	 */
	@Transactional
	public ProjectPurpose findProjectPurposeById(Integer id) throws DataAccessException {

		return findProjectPurposeById(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectPurposeById
	 *
	 */

	@Transactional
	public ProjectPurpose findProjectPurposeById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectPurposeById", startResult, maxRows, id);
			return (ProjectPurpose) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllProjectPurposes
	 *
	 */
	@Transactional
	public Set<ProjectPurpose> findAllProjectPurposes() throws DataAccessException {

		return findAllProjectPurposes(-1, -1);
	}

	/**
	 * JPQL Query - findAllProjectPurposes
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectPurpose> findAllProjectPurposes(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllProjectPurposes", startResult, maxRows);
		return new LinkedHashSet<ProjectPurpose>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectPurposeByPrimaryKey
	 *
	 */
	@Transactional
	public ProjectPurpose findProjectPurposeByPrimaryKey(Integer id) throws DataAccessException {

		return findProjectPurposeByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectPurposeByPrimaryKey
	 *
	 */

	@Transactional
	public ProjectPurpose findProjectPurposeByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectPurposeByPrimaryKey", startResult, maxRows, id);
			return (ProjectPurpose) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectPurposeByLabConstructAppId
	 *
	 */
	@Transactional
	public Set<ProjectPurpose> findProjectPurposeByLabConstructAppId(Integer labConstructAppId) throws DataAccessException {

		return findProjectPurposeByLabConstructAppId(labConstructAppId, -1, -1);
	}

	/**
	 * JPQL Query - findProjectPurposeByLabConstructAppId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectPurpose> findProjectPurposeByLabConstructAppId(Integer labConstructAppId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectPurposeByLabConstructAppId", startResult, maxRows, labConstructAppId);
		return new LinkedHashSet<ProjectPurpose>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectPurposeByProjectPurposeId
	 *
	 */
	@Transactional
	public Set<ProjectPurpose> findProjectPurposeByProjectPurposeId(Integer projectPurposeId) throws DataAccessException {

		return findProjectPurposeByProjectPurposeId(projectPurposeId, -1, -1);
	}

	/**
	 * JPQL Query - findProjectPurposeByProjectPurposeId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectPurpose> findProjectPurposeByProjectPurposeId(Integer projectPurposeId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectPurposeByProjectPurposeId", startResult, maxRows, projectPurposeId);
		return new LinkedHashSet<ProjectPurpose>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectPurposeByInfo
	 *
	 */
	@Transactional
	public Set<ProjectPurpose> findProjectPurposeByInfo(String info) throws DataAccessException {

		return findProjectPurposeByInfo(info, -1, -1);
	}

	/**
	 * JPQL Query - findProjectPurposeByInfo
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectPurpose> findProjectPurposeByInfo(String info, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectPurposeByInfo", startResult, maxRows, info);
		return new LinkedHashSet<ProjectPurpose>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(ProjectPurpose entity) {
		return true;
	}
}
