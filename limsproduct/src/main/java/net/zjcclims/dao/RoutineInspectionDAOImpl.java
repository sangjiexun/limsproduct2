package net.zjcclims.dao;

import net.zjcclims.domain.RoutineInspection;
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
 * DAO to manage RoutineInspection entities.
 * 
 */
@Repository("RoutineInspectionDAO")
@Transactional
public class RoutineInspectionDAOImpl extends AbstractJpaDao<RoutineInspection>
		implements RoutineInspectionDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { RoutineInspection.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new RoutineInspectionDAOImpl
	 *
	 */
	public RoutineInspectionDAOImpl() {
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
	 * JPQL Query - findRoutineInspectionByWeekContaining
	 *
	 */
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByWeekContaining(String week) throws DataAccessException {

		return findRoutineInspectionByWeekContaining(week, -1, -1);
	}

	/**
	 * JPQL Query - findRoutineInspectionByWeekContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByWeekContaining(String week, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRoutineInspectionByWeekContaining", startResult, maxRows, week);
		return new LinkedHashSet<RoutineInspection>(query.getResultList());
	}

	/**
	 * JPQL Query - findRoutineInspectionByPrimaryKey
	 *
	 */
	@Transactional
	public RoutineInspection findRoutineInspectionByPrimaryKey(Integer id) throws DataAccessException {

		return findRoutineInspectionByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findRoutineInspectionByPrimaryKey
	 *
	 */

	@Transactional
	public RoutineInspection findRoutineInspectionByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findRoutineInspectionByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.RoutineInspection) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findRoutineInspectionByWeek
	 *
	 */
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByWeek(String week) throws DataAccessException {

		return findRoutineInspectionByWeek(week, -1, -1);
	}

	/**
	 * JPQL Query - findRoutineInspectionByWeek
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByWeek(String week, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRoutineInspectionByWeek", startResult, maxRows, week);
		return new LinkedHashSet<RoutineInspection>(query.getResultList());
	}

	/**
	 * JPQL Query - findRoutineInspectionByTypeAuditing
	 *
	 */
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByTypeAuditing(String typeAuditing) throws DataAccessException {

		return findRoutineInspectionByTypeAuditing(typeAuditing, -1, -1);
	}

	/**
	 * JPQL Query - findRoutineInspectionByTypeAuditing
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByTypeAuditing(String typeAuditing, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRoutineInspectionByTypeAuditing", startResult, maxRows, typeAuditing);
		return new LinkedHashSet<RoutineInspection>(query.getResultList());
	}

	/**
	 * JPQL Query - findRoutineInspectionByAcademyNumberContaining
	 *
	 */
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByAcademyNumberContaining(String academyNumber) throws DataAccessException {

		return findRoutineInspectionByAcademyNumberContaining(academyNumber, -1, -1);
	}

	/**
	 * JPQL Query - findRoutineInspectionByAcademyNumberContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByAcademyNumberContaining(String academyNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRoutineInspectionByAcademyNumberContaining", startResult, maxRows, academyNumber);
		return new LinkedHashSet<RoutineInspection>(query.getResultList());
	}

	/**
	 * JPQL Query - findRoutineInspectionById
	 *
	 */
	@Transactional
	public RoutineInspection findRoutineInspectionById(Integer id) throws DataAccessException {

		return findRoutineInspectionById(id, -1, -1);
	}

	/**
	 * JPQL Query - findRoutineInspectionById
	 *
	 */

	@Transactional
	public RoutineInspection findRoutineInspectionById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findRoutineInspectionById", startResult, maxRows, id);
			return (net.zjcclims.domain.RoutineInspection) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findRoutineInspectionByTypeTableContaining
	 *
	 */
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByTypeTableContaining(String typeTable) throws DataAccessException {

		return findRoutineInspectionByTypeTableContaining(typeTable, -1, -1);
	}

	/**
	 * JPQL Query - findRoutineInspectionByTypeTableContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByTypeTableContaining(String typeTable, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRoutineInspectionByTypeTableContaining", startResult, maxRows, typeTable);
		return new LinkedHashSet<RoutineInspection>(query.getResultList());
	}

	/**
	 * JPQL Query - findRoutineInspectionByAcademyNumber
	 *
	 */
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByAcademyNumber(String academyNumber) throws DataAccessException {

		return findRoutineInspectionByAcademyNumber(academyNumber, -1, -1);
	}

	/**
	 * JPQL Query - findRoutineInspectionByAcademyNumber
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByAcademyNumber(String academyNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRoutineInspectionByAcademyNumber", startResult, maxRows, academyNumber);
		return new LinkedHashSet<RoutineInspection>(query.getResultList());
	}

	/**
	 * JPQL Query - findRoutineInspectionByCreationDate
	 *
	 */
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByCreationDate(java.util.Calendar creationDate) throws DataAccessException {

		return findRoutineInspectionByCreationDate(creationDate, -1, -1);
	}

	/**
	 * JPQL Query - findRoutineInspectionByCreationDate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByCreationDate(java.util.Calendar creationDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRoutineInspectionByCreationDate", startResult, maxRows, creationDate);
		return new LinkedHashSet<RoutineInspection>(query.getResultList());
	}

	/**
	 * JPQL Query - findRoutineInspectionByTypeAuditingContaining
	 *
	 */
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByTypeAuditingContaining(String typeAuditing) throws DataAccessException {

		return findRoutineInspectionByTypeAuditingContaining(typeAuditing, -1, -1);
	}

	/**
	 * JPQL Query - findRoutineInspectionByTypeAuditingContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByTypeAuditingContaining(String typeAuditing, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRoutineInspectionByTypeAuditingContaining", startResult, maxRows, typeAuditing);
		return new LinkedHashSet<RoutineInspection>(query.getResultList());
	}

	/**
	 * JPQL Query - findRoutineInspectionByCheckContentContaining
	 *
	 */
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByCheckContentContaining(String checkContent) throws DataAccessException {

		return findRoutineInspectionByCheckContentContaining(checkContent, -1, -1);
	}

	/**
	 * JPQL Query - findRoutineInspectionByCheckContentContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByCheckContentContaining(String checkContent, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRoutineInspectionByCheckContentContaining", startResult, maxRows, checkContent);
		return new LinkedHashSet<RoutineInspection>(query.getResultList());
	}

	/**
	 * JPQL Query - findRoutineInspectionByTypeTable
	 *
	 */
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByTypeTable(String typeTable) throws DataAccessException {

		return findRoutineInspectionByTypeTable(typeTable, -1, -1);
	}

	/**
	 * JPQL Query - findRoutineInspectionByTypeTable
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByTypeTable(String typeTable, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRoutineInspectionByTypeTable", startResult, maxRows, typeTable);
		return new LinkedHashSet<RoutineInspection>(query.getResultList());
	}

	/**
	 * JPQL Query - findRoutineInspectionByCheckContent
	 *
	 */
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByCheckContent(String checkContent) throws DataAccessException {

		return findRoutineInspectionByCheckContent(checkContent, -1, -1);
	}

	/**
	 * JPQL Query - findRoutineInspectionByCheckContent
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RoutineInspection> findRoutineInspectionByCheckContent(String checkContent, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findRoutineInspectionByCheckContent", startResult, maxRows, checkContent);
		return new LinkedHashSet<RoutineInspection>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllRoutineInspections
	 *
	 */
	@Transactional
	public Set<RoutineInspection> findAllRoutineInspections() throws DataAccessException {

		return findAllRoutineInspections(-1, -1);
	}

	/**
	 * JPQL Query - findAllRoutineInspections
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<RoutineInspection> findAllRoutineInspections(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllRoutineInspections", startResult, maxRows);
		return new LinkedHashSet<RoutineInspection>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(RoutineInspection entity) {
		return true;
	}
}
