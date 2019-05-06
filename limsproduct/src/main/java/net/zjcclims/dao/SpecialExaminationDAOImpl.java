package net.zjcclims.dao;

import net.zjcclims.domain.SpecialExamination;
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
 * DAO to manage SpecialExamination entities.
 * 
 */
@Repository("SpecialExaminationDAO")
@Transactional
public class SpecialExaminationDAOImpl extends AbstractJpaDao<SpecialExamination>
		implements SpecialExaminationDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { SpecialExamination.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new SpecialExaminationDAOImpl
	 *
	 */
	public SpecialExaminationDAOImpl() {
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
	 * JPQL Query - findSpecialExaminationByCreationDate
	 *
	 */
	@Transactional
	public Set<SpecialExamination> findSpecialExaminationByCreationDate(java.util.Calendar creationDate) throws DataAccessException {

		return findSpecialExaminationByCreationDate(creationDate, -1, -1);
	}

	/**
	 * JPQL Query - findSpecialExaminationByCreationDate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SpecialExamination> findSpecialExaminationByCreationDate(java.util.Calendar creationDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSpecialExaminationByCreationDate", startResult, maxRows, creationDate);
		return new LinkedHashSet<SpecialExamination>(query.getResultList());
	}

	/**
	 * JPQL Query - findSpecialExaminationByCheckItem
	 *
	 */
	@Transactional
	public Set<SpecialExamination> findSpecialExaminationByCheckItem(String checkItem) throws DataAccessException {

		return findSpecialExaminationByCheckItem(checkItem, -1, -1);
	}

	/**
	 * JPQL Query - findSpecialExaminationByCheckItem
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SpecialExamination> findSpecialExaminationByCheckItem(String checkItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSpecialExaminationByCheckItem", startResult, maxRows, checkItem);
		return new LinkedHashSet<SpecialExamination>(query.getResultList());
	}

	/**
	 * JPQL Query - findSpecialExaminationByCheckType
	 *
	 */
	@Transactional
	public Set<SpecialExamination> findSpecialExaminationByCheckType(String checkType) throws DataAccessException {

		return findSpecialExaminationByCheckType(checkType, -1, -1);
	}

	/**
	 * JPQL Query - findSpecialExaminationByCheckType
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SpecialExamination> findSpecialExaminationByCheckType(String checkType, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSpecialExaminationByCheckType", startResult, maxRows, checkType);
		return new LinkedHashSet<SpecialExamination>(query.getResultList());
	}

	/**
	 * JPQL Query - findSpecialExaminationByCheckTypeContaining
	 *
	 */
	@Transactional
	public Set<SpecialExamination> findSpecialExaminationByCheckTypeContaining(String checkType) throws DataAccessException {

		return findSpecialExaminationByCheckTypeContaining(checkType, -1, -1);
	}

	/**
	 * JPQL Query - findSpecialExaminationByCheckTypeContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SpecialExamination> findSpecialExaminationByCheckTypeContaining(String checkType, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSpecialExaminationByCheckTypeContaining", startResult, maxRows, checkType);
		return new LinkedHashSet<SpecialExamination>(query.getResultList());
	}

	/**
	 * JPQL Query - findSpecialExaminationByAcademyNumberContaining
	 *
	 */
	@Transactional
	public Set<SpecialExamination> findSpecialExaminationByAcademyNumberContaining(String academyNumber) throws DataAccessException {

		return findSpecialExaminationByAcademyNumberContaining(academyNumber, -1, -1);
	}

	/**
	 * JPQL Query - findSpecialExaminationByAcademyNumberContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SpecialExamination> findSpecialExaminationByAcademyNumberContaining(String academyNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSpecialExaminationByAcademyNumberContaining", startResult, maxRows, academyNumber);
		return new LinkedHashSet<SpecialExamination>(query.getResultList());
	}

	/**
	 * JPQL Query - findSpecialExaminationByAcademyNumber
	 *
	 */
	@Transactional
	public Set<SpecialExamination> findSpecialExaminationByAcademyNumber(String academyNumber) throws DataAccessException {

		return findSpecialExaminationByAcademyNumber(academyNumber, -1, -1);
	}

	/**
	 * JPQL Query - findSpecialExaminationByAcademyNumber
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SpecialExamination> findSpecialExaminationByAcademyNumber(String academyNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSpecialExaminationByAcademyNumber", startResult, maxRows, academyNumber);
		return new LinkedHashSet<SpecialExamination>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllSpecialExaminations
	 *
	 */
	@Transactional
	public Set<SpecialExamination> findAllSpecialExaminations() throws DataAccessException {

		return findAllSpecialExaminations(-1, -1);
	}

	/**
	 * JPQL Query - findAllSpecialExaminations
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SpecialExamination> findAllSpecialExaminations(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllSpecialExaminations", startResult, maxRows);
		return new LinkedHashSet<SpecialExamination>(query.getResultList());
	}

	/**
	 * JPQL Query - findSpecialExaminationByPrimaryKey
	 *
	 */
	@Transactional
	public SpecialExamination findSpecialExaminationByPrimaryKey(Integer id) throws DataAccessException {

		return findSpecialExaminationByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findSpecialExaminationByPrimaryKey
	 *
	 */

	@Transactional
	public SpecialExamination findSpecialExaminationByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSpecialExaminationByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.SpecialExamination) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findSpecialExaminationByCheckItemContaining
	 *
	 */
	@Transactional
	public Set<SpecialExamination> findSpecialExaminationByCheckItemContaining(String checkItem) throws DataAccessException {

		return findSpecialExaminationByCheckItemContaining(checkItem, -1, -1);
	}

	/**
	 * JPQL Query - findSpecialExaminationByCheckItemContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SpecialExamination> findSpecialExaminationByCheckItemContaining(String checkItem, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSpecialExaminationByCheckItemContaining", startResult, maxRows, checkItem);
		return new LinkedHashSet<SpecialExamination>(query.getResultList());
	}

	/**
	 * JPQL Query - findSpecialExaminationByCheckContentContaining
	 *
	 */
	@Transactional
	public Set<SpecialExamination> findSpecialExaminationByCheckContentContaining(String checkContent) throws DataAccessException {

		return findSpecialExaminationByCheckContentContaining(checkContent, -1, -1);
	}

	/**
	 * JPQL Query - findSpecialExaminationByCheckContentContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SpecialExamination> findSpecialExaminationByCheckContentContaining(String checkContent, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSpecialExaminationByCheckContentContaining", startResult, maxRows, checkContent);
		return new LinkedHashSet<SpecialExamination>(query.getResultList());
	}

	/**
	 * JPQL Query - findSpecialExaminationByCheckContent
	 *
	 */
	@Transactional
	public Set<SpecialExamination> findSpecialExaminationByCheckContent(String checkContent) throws DataAccessException {

		return findSpecialExaminationByCheckContent(checkContent, -1, -1);
	}

	/**
	 * JPQL Query - findSpecialExaminationByCheckContent
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SpecialExamination> findSpecialExaminationByCheckContent(String checkContent, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSpecialExaminationByCheckContent", startResult, maxRows, checkContent);
		return new LinkedHashSet<SpecialExamination>(query.getResultList());
	}

	/**
	 * JPQL Query - findSpecialExaminationById
	 *
	 */
	@Transactional
	public SpecialExamination findSpecialExaminationById(Integer id) throws DataAccessException {

		return findSpecialExaminationById(id, -1, -1);
	}

	/**
	 * JPQL Query - findSpecialExaminationById
	 *
	 */

	@Transactional
	public SpecialExamination findSpecialExaminationById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSpecialExaminationById", startResult, maxRows, id);
			return (net.zjcclims.domain.SpecialExamination) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(SpecialExamination entity) {
		return true;
	}
}
