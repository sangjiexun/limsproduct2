package net.zjcclims.dao;

import net.zjcclims.domain.ChoseDissertation;
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
 * DAO to manage ChoseDissertation entities.
 * 
 */
@Repository("ChoseDissertationDAO")
@Transactional
public class ChoseDissertationDAOImpl extends AbstractJpaDao<ChoseDissertation>
		implements ChoseDissertationDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ChoseDissertation.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ChoseDissertationDAOImpl
	 *
	 */
	public ChoseDissertationDAOImpl() {
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
	 * JPQL Query - findChoseDissertationByRemarkContaining
	 *
	 */
	@Transactional
	public Set<ChoseDissertation> findChoseDissertationByRemarkContaining(String remark) throws DataAccessException {

		return findChoseDissertationByRemarkContaining(remark, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDissertationByRemarkContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDissertation> findChoseDissertationByRemarkContaining(String remark, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDissertationByRemarkContaining", startResult, maxRows, remark);
		return new LinkedHashSet<ChoseDissertation>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDissertationByContent
	 *
	 */
	@Transactional
	public Set<ChoseDissertation> findChoseDissertationByContent(String content) throws DataAccessException {

		return findChoseDissertationByContent(content, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDissertationByContent
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDissertation> findChoseDissertationByContent(String content, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDissertationByContent", startResult, maxRows, content);
		return new LinkedHashSet<ChoseDissertation>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDissertationByContentContaining
	 *
	 */
	@Transactional
	public Set<ChoseDissertation> findChoseDissertationByContentContaining(String content) throws DataAccessException {

		return findChoseDissertationByContentContaining(content, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDissertationByContentContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDissertation> findChoseDissertationByContentContaining(String content, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDissertationByContentContaining", startResult, maxRows, content);
		return new LinkedHashSet<ChoseDissertation>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDissertationByState
	 *
	 */
	@Transactional
	public Set<ChoseDissertation> findChoseDissertationByState(Integer state) throws DataAccessException {

		return findChoseDissertationByState(state, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDissertationByState
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDissertation> findChoseDissertationByState(Integer state, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDissertationByState", startResult, maxRows, state);
		return new LinkedHashSet<ChoseDissertation>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDissertationByTittle
	 *
	 */
	@Transactional
	public Set<ChoseDissertation> findChoseDissertationByTittle(String tittle) throws DataAccessException {

		return findChoseDissertationByTittle(tittle, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDissertationByTittle
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDissertation> findChoseDissertationByTittle(String tittle, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDissertationByTittle", startResult, maxRows, tittle);
		return new LinkedHashSet<ChoseDissertation>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDissertationById
	 *
	 */
	@Transactional
	public ChoseDissertation findChoseDissertationById(Integer id) throws DataAccessException {

		return findChoseDissertationById(id, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDissertationById
	 *
	 */

	@Transactional
	public ChoseDissertation findChoseDissertationById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findChoseDissertationById", startResult, maxRows, id);
			return (net.zjcclims.domain.ChoseDissertation) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findChoseDissertationByTittleContaining
	 *
	 */
	@Transactional
	public Set<ChoseDissertation> findChoseDissertationByTittleContaining(String tittle) throws DataAccessException {

		return findChoseDissertationByTittleContaining(tittle, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDissertationByTittleContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDissertation> findChoseDissertationByTittleContaining(String tittle, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDissertationByTittleContaining", startResult, maxRows, tittle);
		return new LinkedHashSet<ChoseDissertation>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllChoseDissertations
	 *
	 */
	@Transactional
	public Set<ChoseDissertation> findAllChoseDissertations() throws DataAccessException {

		return findAllChoseDissertations(-1, -1);
	}

	/**
	 * JPQL Query - findAllChoseDissertations
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDissertation> findAllChoseDissertations(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllChoseDissertations", startResult, maxRows);
		return new LinkedHashSet<ChoseDissertation>(query.getResultList());
	}

	/**
	 * JPQL Query - findChoseDissertationByPrimaryKey
	 *
	 */
	@Transactional
	public ChoseDissertation findChoseDissertationByPrimaryKey(Integer id) throws DataAccessException {

		return findChoseDissertationByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDissertationByPrimaryKey
	 *
	 */

	@Transactional
	public ChoseDissertation findChoseDissertationByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findChoseDissertationByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.ChoseDissertation) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findChoseDissertationByRemark
	 *
	 */
	@Transactional
	public Set<ChoseDissertation> findChoseDissertationByRemark(String remark) throws DataAccessException {

		return findChoseDissertationByRemark(remark, -1, -1);
	}

	/**
	 * JPQL Query - findChoseDissertationByRemark
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ChoseDissertation> findChoseDissertationByRemark(String remark, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findChoseDissertationByRemark", startResult, maxRows, remark);
		return new LinkedHashSet<ChoseDissertation>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(ChoseDissertation entity) {
		return true;
	}
}
