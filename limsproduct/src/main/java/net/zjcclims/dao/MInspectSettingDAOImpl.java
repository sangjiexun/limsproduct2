package net.zjcclims.dao;

import net.zjcclims.domain.MInspectSetting;
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
 * DAO to manage MInspectSetting entities.
 *
 */
@Repository("MInspectSettingDAO")
@Transactional
public class MInspectSettingDAOImpl extends AbstractJpaDao<MInspectSetting>
		implements MInspectSettingDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { MInspectSetting.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclims
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new MInspectSettingDAOImpl
	 *
	 */
	public MInspectSettingDAOImpl() {
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
	 * JPQL Query - findMInspectSettingByPrimaryKey
	 *
	 */
	@Transactional
	public MInspectSetting findMInspectSettingByPrimaryKey(Integer id) throws DataAccessException {

		return findMInspectSettingByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findMInspectSettingByPrimaryKey
	 *
	 */

	@Transactional
	public MInspectSetting findMInspectSettingByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findMInspectSettingByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.MInspectSetting) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllMInspectSettings
	 *
	 */
	@Transactional
	public Set<MInspectSetting> findAllMInspectSettings() throws DataAccessException {

		return findAllMInspectSettings(-1, -1);
	}

	/**
	 * JPQL Query - findAllMInspectSettings
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<MInspectSetting> findAllMInspectSettings(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllMInspectSettings", startResult, maxRows);
		return new LinkedHashSet<MInspectSetting>(query.getResultList());
	}

	/**
	 * JPQL Query - findMInspectSettingById
	 *
	 */
	@Transactional
	public MInspectSetting findMInspectSettingById(Integer id) throws DataAccessException {

		return findMInspectSettingById(id, -1, -1);
	}

	/**
	 * JPQL Query - findMInspectSettingById
	 *
	 */

	@Transactional
	public MInspectSetting findMInspectSettingById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findMInspectSettingById", startResult, maxRows, id);
			return (net.zjcclims.domain.MInspectSetting) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findMInspectSettingByBatchId
	 *
	 */
	@Transactional
	public Set<MInspectSetting> findMInspectSettingByBatchId(Integer batchId) throws DataAccessException {

		return findMInspectSettingByBatchId(batchId, -1, -1);
	}

	/**
	 * JPQL Query - findMInspectSettingByBatchId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<MInspectSetting> findMInspectSettingByBatchId(Integer batchId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findMInspectSettingByBatchId", startResult, maxRows, batchId);
		return new LinkedHashSet<MInspectSetting>(query.getResultList());
	}

	/**
	 * JPQL Query - findMInspectSettingByStandardId
	 *
	 */
	@Transactional
	public Set<MInspectSetting> findMInspectSettingByStandardId(Integer standardId) throws DataAccessException {

		return findMInspectSettingByStandardId(standardId, -1, -1);
	}

	/**
	 * JPQL Query - findMInspectSettingByStandardId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<MInspectSetting> findMInspectSettingByStandardId(Integer standardId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findMInspectSettingByStandardId", startResult, maxRows, standardId);
		return new LinkedHashSet<MInspectSetting>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 *
	 *
	 */
	public boolean canBeMerged(MInspectSetting entity) {
		return true;
	}
}
