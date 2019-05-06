
package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.LabConstructionProjectDocument;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage LabConstructionProjectDocument entities.
 * 
 */
@Repository("LabConstructionProjectDocumentDAO")

@Transactional
public class LabConstructionProjectDocumentDAOImpl extends AbstractJpaDao<LabConstructionProjectDocument>
		implements LabConstructionProjectDocumentDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] {
			LabConstructionProjectDocument.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabConstructionProjectDocumentDAOImpl
	 *
	 */
	public LabConstructionProjectDocumentDAOImpl() {
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
	 * JPQL Query - findLabConstructionProjectDocumentByEnable
	 *
	 */
	@Transactional
	public Set<LabConstructionProjectDocument> findLabConstructionProjectDocumentByEnable(Boolean enable) throws DataAccessException {

		return findLabConstructionProjectDocumentByEnable(enable, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionProjectDocumentByEnable
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionProjectDocument> findLabConstructionProjectDocumentByEnable(Boolean enable, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionProjectDocumentByEnable", startResult, maxRows, enable);
		return new LinkedHashSet<LabConstructionProjectDocument>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabConstructionProjectDocumentById
	 *
	 */
	@Transactional
	public LabConstructionProjectDocument findLabConstructionProjectDocumentById(Integer id) throws DataAccessException {

		return findLabConstructionProjectDocumentById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionProjectDocumentById
	 *
	 */

	@Transactional
	public LabConstructionProjectDocument findLabConstructionProjectDocumentById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabConstructionProjectDocumentById", startResult, maxRows, id);
			return (LabConstructionProjectDocument) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabConstructionProjectDocumentByPrimaryKey
	 *
	 */
	@Transactional
	public LabConstructionProjectDocument findLabConstructionProjectDocumentByPrimaryKey(Integer id) throws DataAccessException {

		return findLabConstructionProjectDocumentByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionProjectDocumentByPrimaryKey
	 *
	 */

	@Transactional
	public LabConstructionProjectDocument findLabConstructionProjectDocumentByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabConstructionProjectDocumentByPrimaryKey", startResult, maxRows, id);
			return (LabConstructionProjectDocument) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabConstructionProjectDocumentByStage
	 *
	 */
	@Transactional
	public Set<LabConstructionProjectDocument> findLabConstructionProjectDocumentByStage(Integer stage) throws DataAccessException {

		return findLabConstructionProjectDocumentByStage(stage, -1, -1);
	}

	/**
	 * JPQL Query - findLabConstructionProjectDocumentByStage
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionProjectDocument> findLabConstructionProjectDocumentByStage(Integer stage, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabConstructionProjectDocumentByStage", startResult, maxRows, stage);
		return new LinkedHashSet<LabConstructionProjectDocument>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabConstructionProjectDocuments
	 *
	 */
	@Transactional
	public Set<LabConstructionProjectDocument> findAllLabConstructionProjectDocuments() throws DataAccessException {

		return findAllLabConstructionProjectDocuments(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabConstructionProjectDocuments
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabConstructionProjectDocument> findAllLabConstructionProjectDocuments(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabConstructionProjectDocuments", startResult, maxRows);
		return new LinkedHashSet<LabConstructionProjectDocument>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(LabConstructionProjectDocument entity) {
		return true;
	}
}
