package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.SchoolYear;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage SchoolYear entities.
 * 
 */
@Repository("SchoolYearDAO")
@Transactional
public class SchoolYearDAOImpl extends AbstractJpaDao<SchoolYear> implements
		SchoolYearDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { SchoolYear.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new SchoolYearDAOImpl
	 *
	 */
	public SchoolYearDAOImpl() {
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
	 * JPQL Query - findSchoolYearByPrimaryKey
	 *
	 */
	@Transactional
	public SchoolYear findSchoolYearByPrimaryKey(Integer id) throws DataAccessException {

		return findSchoolYearByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolYearByPrimaryKey
	 *
	 */

	@Transactional
	public SchoolYear findSchoolYearByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSchoolYearByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.SchoolYear) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findSchoolYearByYearNameContaining
	 *
	 */
	@Transactional
	public Set<SchoolYear> findSchoolYearByYearNameContaining(String yearName) throws DataAccessException {

		return findSchoolYearByYearNameContaining(yearName, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolYearByYearNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolYear> findSchoolYearByYearNameContaining(String yearName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolYearByYearNameContaining", startResult, maxRows, yearName);
		return new LinkedHashSet<SchoolYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolYearByCode
	 *
	 */
	@Transactional
	public Set<SchoolYear> findSchoolYearByCode(String code) throws DataAccessException {

		return findSchoolYearByCode(code, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolYearByCode
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolYear> findSchoolYearByCode(String code, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolYearByCode", startResult, maxRows, code);
		return new LinkedHashSet<SchoolYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllSchoolYears
	 *
	 */
	@Transactional
	public Set<SchoolYear> findAllSchoolYears() throws DataAccessException {

		return findAllSchoolYears(-1, -1);
	}

	/**
	 * JPQL Query - findAllSchoolYears
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolYear> findAllSchoolYears(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllSchoolYears", startResult, maxRows);
		return new LinkedHashSet<SchoolYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolYearByYearStart
	 *
	 */
	@Transactional
	public Set<SchoolYear> findSchoolYearByYearStart(java.util.Calendar yearStart) throws DataAccessException {

		return findSchoolYearByYearStart(yearStart, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolYearByYearStart
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolYear> findSchoolYearByYearStart(java.util.Calendar yearStart, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolYearByYearStart", startResult, maxRows, yearStart);
		return new LinkedHashSet<SchoolYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolYearByYearEndBefore
	 *
	 */
	@Transactional
	public Set<SchoolYear> findSchoolYearByYearEndBefore(java.util.Calendar yearEnd) throws DataAccessException {

		return findSchoolYearByYearEndBefore(yearEnd, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolYearByYearEndBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolYear> findSchoolYearByYearEndBefore(java.util.Calendar yearEnd, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolYearByYearEndBefore", startResult, maxRows, yearEnd);
		return new LinkedHashSet<SchoolYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolYearByCodeContaining
	 *
	 */
	@Transactional
	public Set<SchoolYear> findSchoolYearByCodeContaining(String code) throws DataAccessException {

		return findSchoolYearByCodeContaining(code, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolYearByCodeContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolYear> findSchoolYearByCodeContaining(String code, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolYearByCodeContaining", startResult, maxRows, code);
		return new LinkedHashSet<SchoolYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolYearByYearEndAfter
	 *
	 */
	@Transactional
	public Set<SchoolYear> findSchoolYearByYearEndAfter(java.util.Calendar yearEnd) throws DataAccessException {

		return findSchoolYearByYearEndAfter(yearEnd, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolYearByYearEndAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolYear> findSchoolYearByYearEndAfter(java.util.Calendar yearEnd, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolYearByYearEndAfter", startResult, maxRows, yearEnd);
		return new LinkedHashSet<SchoolYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolYearByYearEnd
	 *
	 */
	@Transactional
	public Set<SchoolYear> findSchoolYearByYearEnd(java.util.Calendar yearEnd) throws DataAccessException {

		return findSchoolYearByYearEnd(yearEnd, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolYearByYearEnd
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolYear> findSchoolYearByYearEnd(java.util.Calendar yearEnd, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolYearByYearEnd", startResult, maxRows, yearEnd);
		return new LinkedHashSet<SchoolYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolYearById
	 *
	 */
	@Transactional
	public SchoolYear findSchoolYearById(Integer id) throws DataAccessException {

		return findSchoolYearById(id, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolYearById
	 *
	 */

	@Transactional
	public SchoolYear findSchoolYearById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSchoolYearById", startResult, maxRows, id);
			return (net.zjcclims.domain.SchoolYear) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findSchoolYearByYearStartBefore
	 *
	 */
	@Transactional
	public Set<SchoolYear> findSchoolYearByYearStartBefore(java.util.Calendar yearStart) throws DataAccessException {

		return findSchoolYearByYearStartBefore(yearStart, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolYearByYearStartBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolYear> findSchoolYearByYearStartBefore(java.util.Calendar yearStart, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolYearByYearStartBefore", startResult, maxRows, yearStart);
		return new LinkedHashSet<SchoolYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolYearByYearStartAfter
	 *
	 */
	@Transactional
	public Set<SchoolYear> findSchoolYearByYearStartAfter(java.util.Calendar yearStart) throws DataAccessException {

		return findSchoolYearByYearStartAfter(yearStart, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolYearByYearStartAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolYear> findSchoolYearByYearStartAfter(java.util.Calendar yearStart, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolYearByYearStartAfter", startResult, maxRows, yearStart);
		return new LinkedHashSet<SchoolYear>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolYearByYearName
	 *
	 */
	@Transactional
	public Set<SchoolYear> findSchoolYearByYearName(String yearName) throws DataAccessException {

		return findSchoolYearByYearName(yearName, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolYearByYearName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolYear> findSchoolYearByYearName(String yearName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolYearByYearName", startResult, maxRows, yearName);
		return new LinkedHashSet<SchoolYear>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(SchoolYear entity) {
		return true;
	}
}
