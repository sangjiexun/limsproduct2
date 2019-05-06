package net.zjcclims.dao;

import net.zjcclims.domain.ChoseProfessor;
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
 * DAO to manage ChoseProfessor entities.
 * 
 */
@Repository("ChoseProfessorDAO")
@Transactional
public class ChoseProfessorDAOImpl extends AbstractJpaDao<ChoseProfessor>
		implements ChoseProfessorDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ChoseProfessor.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ChoseProfessorDAOImpl
	 *
	 */
	public ChoseProfessorDAOImpl() {
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
	 * JPQL Query - findChoseProfessorByType
	 *
	 */
	@Transactional
	public Set<ChoseProfessor> findChoseProfessorByType(Integer type) throws DataAccessException {

		return findChoseProfessorByType(type, -1, -1);
	}

	/**
	 * JPQL Query - findChoseProfessorByType
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseProfessor> findChoseProfessorByType(Integer type, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseProfessorByType", startResult, maxRows, type);
		return new LinkedHashSet<ChoseProfessor>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseProfessorByFinalNumber
	 *
	 */
	@Transactional
	public Set<ChoseProfessor> findChoseProfessorByFinalNumber(Integer finalNumber) throws DataAccessException {

		return findChoseProfessorByFinalNumber(finalNumber, -1, -1);
	}

	/**
	 * JPQL Query - findChoseProfessorByFinalNumber
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseProfessor> findChoseProfessorByFinalNumber(Integer finalNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseProfessorByFinalNumber", startResult, maxRows, finalNumber);
		return new LinkedHashSet<ChoseProfessor>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseProfessorById
	 *
	 */
	@Transactional
	public ChoseProfessor findChoseProfessorById(Integer id) throws DataAccessException {

		return findChoseProfessorById(id, -1, -1);
	}

	/**
	 * JPQL Query - findChoseProfessorById
	 *
	 */

	@Transactional
	public ChoseProfessor findChoseProfessorById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findChoseProfessorById", startResult, maxRows, id);
			return (net.zjcclims.domain.ChoseProfessor) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findChoseProfessorByPrimaryKey
	 *
	 */
	@Transactional
	public ChoseProfessor findChoseProfessorByPrimaryKey(Integer id) throws DataAccessException {

		return findChoseProfessorByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findChoseProfessorByPrimaryKey
	 *
	 */

	@Transactional
	public ChoseProfessor findChoseProfessorByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findChoseProfessorByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.ChoseProfessor) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllChoseProfessors
	 *
	 */
	@Transactional
	public Set<ChoseProfessor> findAllChoseProfessors() throws DataAccessException {

		return findAllChoseProfessors(-1, -1);
	}

	/**
	 * JPQL Query - findAllChoseProfessors
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseProfessor> findAllChoseProfessors(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllChoseProfessors", startResult, maxRows);
		return new LinkedHashSet<ChoseProfessor>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseProfessorByExceptNumber
	 *
	 */
	@Transactional
	public Set<ChoseProfessor> findChoseProfessorByExceptNumber(Integer exceptNumber) throws DataAccessException {

		return findChoseProfessorByExceptNumber(exceptNumber, -1, -1);
	}

	/**
	 * JPQL Query - findChoseProfessorByExceptNumber
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseProfessor> findChoseProfessorByExceptNumber(Integer exceptNumber, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseProfessorByExceptNumber", startResult, maxRows, exceptNumber);
		return new LinkedHashSet<ChoseProfessor>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(ChoseProfessor entity) {
		return true;
	}
}
