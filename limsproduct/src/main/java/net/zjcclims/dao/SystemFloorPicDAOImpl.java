package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.SystemFloorPic;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage SystemFloorPic entities.
 * 
 */
@Repository("SystemFloorPicDAO")
@Transactional
public class SystemFloorPicDAOImpl extends AbstractJpaDao<SystemFloorPic>
		implements SystemFloorPicDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { SystemFloorPic.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new SystemFloorPicDAOImpl
	 *
	 */
	public SystemFloorPicDAOImpl() {
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
	 * JPQL Query - findSystemFloorPicByFloorNo
	 *
	 */
	@Transactional
	public Set<SystemFloorPic> findSystemFloorPicByFloorNo(Integer floorNo) throws DataAccessException {

		return findSystemFloorPicByFloorNo(floorNo, -1, -1);
	}

	/**
	 * JPQL Query - findSystemFloorPicByFloorNo
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SystemFloorPic> findSystemFloorPicByFloorNo(Integer floorNo, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSystemFloorPicByFloorNo", startResult, maxRows, floorNo);
		return new LinkedHashSet<SystemFloorPic>(query.getResultList());
	}

	/**
	 * JPQL Query - findSystemFloorPicBySystemBuild
	 *
	 */
	@Transactional
	public Set<SystemFloorPic> findSystemFloorPicBySystemBuild(String systemBuild) throws DataAccessException {

		return findSystemFloorPicBySystemBuild(systemBuild, -1, -1);
	}

	/**
	 * JPQL Query - findSystemFloorPicBySystemBuild
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SystemFloorPic> findSystemFloorPicBySystemBuild(String systemBuild, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSystemFloorPicBySystemBuild", startResult, maxRows, systemBuild);
		return new LinkedHashSet<SystemFloorPic>(query.getResultList());
	}

	/**
	 * JPQL Query - findSystemFloorPicByDocumentUrlContaining
	 *
	 */
	@Transactional
	public Set<SystemFloorPic> findSystemFloorPicByDocumentUrlContaining(String documentUrl) throws DataAccessException {

		return findSystemFloorPicByDocumentUrlContaining(documentUrl, -1, -1);
	}

	/**
	 * JPQL Query - findSystemFloorPicByDocumentUrlContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SystemFloorPic> findSystemFloorPicByDocumentUrlContaining(String documentUrl, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSystemFloorPicByDocumentUrlContaining", startResult, maxRows, documentUrl);
		return new LinkedHashSet<SystemFloorPic>(query.getResultList());
	}

	/**
	 * JPQL Query - findSystemFloorPicBySystemBuildContaining
	 *
	 */
	@Transactional
	public Set<SystemFloorPic> findSystemFloorPicBySystemBuildContaining(String systemBuild) throws DataAccessException {

		return findSystemFloorPicBySystemBuildContaining(systemBuild, -1, -1);
	}

	/**
	 * JPQL Query - findSystemFloorPicBySystemBuildContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SystemFloorPic> findSystemFloorPicBySystemBuildContaining(String systemBuild, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSystemFloorPicBySystemBuildContaining", startResult, maxRows, systemBuild);
		return new LinkedHashSet<SystemFloorPic>(query.getResultList());
	}

	/**
	 * JPQL Query - findSystemFloorPicByDocumentName
	 *
	 */
	@Transactional
	public Set<SystemFloorPic> findSystemFloorPicByDocumentName(String documentName) throws DataAccessException {

		return findSystemFloorPicByDocumentName(documentName, -1, -1);
	}

	/**
	 * JPQL Query - findSystemFloorPicByDocumentName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SystemFloorPic> findSystemFloorPicByDocumentName(String documentName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSystemFloorPicByDocumentName", startResult, maxRows, documentName);
		return new LinkedHashSet<SystemFloorPic>(query.getResultList());
	}

	/**
	 * JPQL Query - findSystemFloorPicByPrimaryKey
	 *
	 */
	@Transactional
	public SystemFloorPic findSystemFloorPicByPrimaryKey(Integer id) throws DataAccessException {

		return findSystemFloorPicByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findSystemFloorPicByPrimaryKey
	 *
	 */

	@Transactional
	public SystemFloorPic findSystemFloorPicByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSystemFloorPicByPrimaryKey", startResult, maxRows, id);
			return (SystemFloorPic) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findSystemFloorPicByDocumentNameContaining
	 *
	 */
	@Transactional
	public Set<SystemFloorPic> findSystemFloorPicByDocumentNameContaining(String documentName) throws DataAccessException {

		return findSystemFloorPicByDocumentNameContaining(documentName, -1, -1);
	}

	/**
	 * JPQL Query - findSystemFloorPicByDocumentNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SystemFloorPic> findSystemFloorPicByDocumentNameContaining(String documentName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSystemFloorPicByDocumentNameContaining", startResult, maxRows, documentName);
		return new LinkedHashSet<SystemFloorPic>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllSystemFloorPics
	 *
	 */
	@Transactional
	public Set<SystemFloorPic> findAllSystemFloorPics() throws DataAccessException {

		return findAllSystemFloorPics(-1, -1);
	}

	/**
	 * JPQL Query - findAllSystemFloorPics
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SystemFloorPic> findAllSystemFloorPics(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllSystemFloorPics", startResult, maxRows);
		return new LinkedHashSet<SystemFloorPic>(query.getResultList());
	}

	/**
	 * JPQL Query - findSystemFloorPicById
	 *
	 */
	@Transactional
	public SystemFloorPic findSystemFloorPicById(Integer id) throws DataAccessException {

		return findSystemFloorPicById(id, -1, -1);
	}

	/**
	 * JPQL Query - findSystemFloorPicById
	 *
	 */

	@Transactional
	public SystemFloorPic findSystemFloorPicById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSystemFloorPicById", startResult, maxRows, id);
			return (SystemFloorPic) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findSystemFloorPicByDocumentUrl
	 *
	 */
	@Transactional
	public Set<SystemFloorPic> findSystemFloorPicByDocumentUrl(String documentUrl) throws DataAccessException {

		return findSystemFloorPicByDocumentUrl(documentUrl, -1, -1);
	}

	/**
	 * JPQL Query - findSystemFloorPicByDocumentUrl
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SystemFloorPic> findSystemFloorPicByDocumentUrl(String documentUrl, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSystemFloorPicByDocumentUrl", startResult, maxRows, documentUrl);
		return new LinkedHashSet<SystemFloorPic>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(SystemFloorPic entity) {
		return true;
	}
}
