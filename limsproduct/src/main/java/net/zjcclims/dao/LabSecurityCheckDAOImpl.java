package net.zjcclims.dao;

import net.zjcclims.domain.LabSecurityCheck;
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
 * DAO to manage LabSecurityCheck entities.
 * 
 */
@Repository("LabSecurityCheckDAO")
@Transactional
public class LabSecurityCheckDAOImpl extends AbstractJpaDao<LabSecurityCheck>
		implements LabSecurityCheckDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabSecurityCheck.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabSecurityCheckDAOImpl
	 *
	 */
	public LabSecurityCheckDAOImpl() {
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
	 * JPQL Query - findLabSecurityCheckByAcademyNumber
	 *
	 */
	@Transactional
	public Set<LabSecurityCheck> findLabSecurityCheckByAcademyNumber(String academyNumber) throws DataAccessException {

		return findLabSecurityCheckByAcademyNumber(academyNumber, -1, -1);
	}

	/**
	 * JPQL Query - findLabSecurityCheckByAcademyNumber
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabSecurityCheck> findLabSecurityCheckByAcademyNumber(String academyNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabSecurityCheckByAcademyNumber", startResult, maxRows, academyNumber);
		return new LinkedHashSet<LabSecurityCheck>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabSecurityCheckByMonthContaining
	 *
	 */
	@Transactional
	public Set<LabSecurityCheck> findLabSecurityCheckByMonthContaining(String month) throws DataAccessException {

		return findLabSecurityCheckByMonthContaining(month, -1, -1);
	}

	/**
	 * JPQL Query - findLabSecurityCheckByMonthContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabSecurityCheck> findLabSecurityCheckByMonthContaining(String month, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabSecurityCheckByMonthContaining", startResult, maxRows, month);
		return new LinkedHashSet<LabSecurityCheck>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabSecurityCheckByPrimaryKey
	 *
	 */
	@Transactional
	public LabSecurityCheck findLabSecurityCheckByPrimaryKey(Integer id) throws DataAccessException {

		return findLabSecurityCheckByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabSecurityCheckByPrimaryKey
	 *
	 */

	@Transactional
	public LabSecurityCheck findLabSecurityCheckByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabSecurityCheckByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabSecurityCheck) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabSecurityCheckByTypeAuditingContaining
	 *
	 */
	@Transactional
	public Set<LabSecurityCheck> findLabSecurityCheckByTypeAuditingContaining(String typeAuditing) throws DataAccessException {

		return findLabSecurityCheckByTypeAuditingContaining(typeAuditing, -1, -1);
	}

	/**
	 * JPQL Query - findLabSecurityCheckByTypeAuditingContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabSecurityCheck> findLabSecurityCheckByTypeAuditingContaining(String typeAuditing, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabSecurityCheckByTypeAuditingContaining", startResult, maxRows, typeAuditing);
		return new LinkedHashSet<LabSecurityCheck>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabSecurityCheckByAcademyNumberContaining
	 *
	 */
	@Transactional
	public Set<LabSecurityCheck> findLabSecurityCheckByAcademyNumberContaining(String academyNumber) throws DataAccessException {

		return findLabSecurityCheckByAcademyNumberContaining(academyNumber, -1, -1);
	}

	/**
	 * JPQL Query - findLabSecurityCheckByAcademyNumberContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabSecurityCheck> findLabSecurityCheckByAcademyNumberContaining(String academyNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabSecurityCheckByAcademyNumberContaining", startResult, maxRows, academyNumber);
		return new LinkedHashSet<LabSecurityCheck>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabSecurityCheckByMonth
	 *
	 */
	@Transactional
	public Set<LabSecurityCheck> findLabSecurityCheckByMonth(String month) throws DataAccessException {

		return findLabSecurityCheckByMonth(month, -1, -1);
	}

	/**
	 * JPQL Query - findLabSecurityCheckByMonth
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabSecurityCheck> findLabSecurityCheckByMonth(String month, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabSecurityCheckByMonth", startResult, maxRows, month);
		return new LinkedHashSet<LabSecurityCheck>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabSecurityCheckById
	 *
	 */
	@Transactional
	public LabSecurityCheck findLabSecurityCheckById(Integer id) throws DataAccessException {

		return findLabSecurityCheckById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabSecurityCheckById
	 *
	 */

	@Transactional
	public LabSecurityCheck findLabSecurityCheckById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabSecurityCheckById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabSecurityCheck) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabSecurityCheckByTypeAuditing
	 *
	 */
	@Transactional
	public Set<LabSecurityCheck> findLabSecurityCheckByTypeAuditing(String typeAuditing) throws DataAccessException {

		return findLabSecurityCheckByTypeAuditing(typeAuditing, -1, -1);
	}

	/**
	 * JPQL Query - findLabSecurityCheckByTypeAuditing
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabSecurityCheck> findLabSecurityCheckByTypeAuditing(String typeAuditing, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabSecurityCheckByTypeAuditing", startResult, maxRows, typeAuditing);
		return new LinkedHashSet<LabSecurityCheck>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabSecurityCheckByTypeTable
	 *
	 */
	@Transactional
	public Set<LabSecurityCheck> findLabSecurityCheckByTypeTable(String typeTable) throws DataAccessException {

		return findLabSecurityCheckByTypeTable(typeTable, -1, -1);
	}

	/**
	 * JPQL Query - findLabSecurityCheckByTypeTable
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabSecurityCheck> findLabSecurityCheckByTypeTable(String typeTable, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabSecurityCheckByTypeTable", startResult, maxRows, typeTable);
		return new LinkedHashSet<LabSecurityCheck>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabSecurityCheckByTypeTableContaining
	 *
	 */
	@Transactional
	public Set<LabSecurityCheck> findLabSecurityCheckByTypeTableContaining(String typeTable) throws DataAccessException {

		return findLabSecurityCheckByTypeTableContaining(typeTable, -1, -1);
	}

	/**
	 * JPQL Query - findLabSecurityCheckByTypeTableContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabSecurityCheck> findLabSecurityCheckByTypeTableContaining(String typeTable, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabSecurityCheckByTypeTableContaining", startResult, maxRows, typeTable);
		return new LinkedHashSet<LabSecurityCheck>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabSecurityChecks
	 *
	 */
	@Transactional
	public Set<LabSecurityCheck> findAllLabSecurityChecks() throws DataAccessException {

		return findAllLabSecurityChecks(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabSecurityChecks
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabSecurityCheck> findAllLabSecurityChecks(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabSecurityChecks", startResult, maxRows);
		return new LinkedHashSet<LabSecurityCheck>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(LabSecurityCheck entity) {
		return true;
	}
}
