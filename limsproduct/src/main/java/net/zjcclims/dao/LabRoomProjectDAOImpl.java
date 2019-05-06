package net.zjcclims.dao;

import net.zjcclims.domain.LabRoomProject;
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
 * DAO to manage LabRoomProject entities.
 * 
 */
@Repository("LabRoomProjectDAO")
@Transactional
public class LabRoomProjectDAOImpl extends AbstractJpaDao<LabRoomProject>
		implements LabRoomProjectDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabRoomProject.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabRoomProjectDAOImpl
	 *
	 */
	public LabRoomProjectDAOImpl() {
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
	 * JPQL Query - findAllLabRoomProjects
	 *
	 */
	@Transactional
	public Set<LabRoomProject> findAllLabRoomProjects() throws DataAccessException {

		return findAllLabRoomProjects(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabRoomProjects
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabRoomProject> findAllLabRoomProjects(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabRoomProjects", startResult, maxRows);
		return new LinkedHashSet<LabRoomProject>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabRoomProjectByPrimaryKey
	 *
	 */
	@Transactional
	public LabRoomProject findLabRoomProjectByPrimaryKey(Integer id) throws DataAccessException {

		return findLabRoomProjectByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomProjectByPrimaryKey
	 *
	 */

	@Transactional
	public LabRoomProject findLabRoomProjectByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomProjectByPrimaryKey", startResult, maxRows, id);
			return (LabRoomProject) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabRoomProjectById
	 *
	 */
	@Transactional
	public LabRoomProject findLabRoomProjectById(Integer id) throws DataAccessException {

		return findLabRoomProjectById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomProjectById
	 *
	 */

	@Transactional
	public LabRoomProject findLabRoomProjectById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabRoomProjectById", startResult, maxRows, id);
			return (LabRoomProject) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(LabRoomProject entity) {
		return true;
	}
}
