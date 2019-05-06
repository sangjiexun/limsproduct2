package net.zjcclims.dao;

import net.zjcclims.domain.LabInspectSetting;
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
 * DAO to manage LabInspectSetting entities.
 *
 */
@Repository("LabInspectSettingDAO")
@Transactional
public class LabInspectSettingDAOImpl extends AbstractJpaDao<LabInspectSetting>
		implements LabInspectSettingDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabInspectSetting.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new LabInspectSettingDAOImpl
	 *
	 */
	public LabInspectSettingDAOImpl() {
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
	 * JPQL Query - findLabInspectSettingBySemeterContaining
	 *
	 */
	@Transactional
	public Set<LabInspectSetting> findLabInspectSettingBySemeterContaining(String semeter) throws DataAccessException {

		return findLabInspectSettingBySemeterContaining(semeter, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectSettingBySemeterContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabInspectSetting> findLabInspectSettingBySemeterContaining(String semeter, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabInspectSettingBySemeterContaining", startResult, maxRows, semeter);
		return new LinkedHashSet<LabInspectSetting>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabInspectSettingByEndTimeAfter
	 *
	 */
	@Transactional
	public Set<LabInspectSetting> findLabInspectSettingByEndTimeAfter(java.util.Calendar endTime) throws DataAccessException {

		return findLabInspectSettingByEndTimeAfter(endTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectSettingByEndTimeAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabInspectSetting> findLabInspectSettingByEndTimeAfter(java.util.Calendar endTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabInspectSettingByEndTimeAfter", startResult, maxRows, endTime);
		return new LinkedHashSet<LabInspectSetting>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabInspectSettingByPrimaryKey
	 *
	 */
	@Transactional
	public LabInspectSetting findLabInspectSettingByPrimaryKey(Integer id) throws DataAccessException {

		return findLabInspectSettingByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectSettingByPrimaryKey
	 *
	 */

	@Transactional
	public LabInspectSetting findLabInspectSettingByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabInspectSettingByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.LabInspectSetting) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabInspectSettingByIsRegular
	 *
	 */
	@Transactional
	public Set<LabInspectSetting> findLabInspectSettingByIsRegular(Boolean isRegular) throws DataAccessException {

		return findLabInspectSettingByIsRegular(isRegular, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectSettingByIsRegular
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabInspectSetting> findLabInspectSettingByIsRegular(Boolean isRegular, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabInspectSettingByIsRegular", startResult, maxRows, isRegular);
		return new LinkedHashSet<LabInspectSetting>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabInspectSettingByEndTimeBefore
	 *
	 */
	@Transactional
	public Set<LabInspectSetting> findLabInspectSettingByEndTimeBefore(java.util.Calendar endTime) throws DataAccessException {

		return findLabInspectSettingByEndTimeBefore(endTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectSettingByEndTimeBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabInspectSetting> findLabInspectSettingByEndTimeBefore(java.util.Calendar endTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabInspectSettingByEndTimeBefore", startResult, maxRows, endTime);
		return new LinkedHashSet<LabInspectSetting>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabInspectSettingByStartTimeAfter
	 *
	 */
	@Transactional
	public Set<LabInspectSetting> findLabInspectSettingByStartTimeAfter(java.util.Calendar startTime) throws DataAccessException {

		return findLabInspectSettingByStartTimeAfter(startTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectSettingByStartTimeAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabInspectSetting> findLabInspectSettingByStartTimeAfter(java.util.Calendar startTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabInspectSettingByStartTimeAfter", startResult, maxRows, startTime);
		return new LinkedHashSet<LabInspectSetting>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabInspectSettingBySemeter
	 *
	 */
	@Transactional
	public Set<LabInspectSetting> findLabInspectSettingBySemeter(String semeter) throws DataAccessException {

		return findLabInspectSettingBySemeter(semeter, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectSettingBySemeter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabInspectSetting> findLabInspectSettingBySemeter(String semeter, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabInspectSettingBySemeter", startResult, maxRows, semeter);
		return new LinkedHashSet<LabInspectSetting>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabInspectSettingByWeek
	 *
	 */
	@Transactional
	public Set<LabInspectSetting> findLabInspectSettingByWeek(Integer week) throws DataAccessException {

		return findLabInspectSettingByWeek(week, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectSettingByWeek
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabInspectSetting> findLabInspectSettingByWeek(Integer week, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabInspectSettingByWeek", startResult, maxRows, week);
		return new LinkedHashSet<LabInspectSetting>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllLabInspectSettings
	 *
	 */
	@Transactional
	public Set<LabInspectSetting> findAllLabInspectSettings() throws DataAccessException {

		return findAllLabInspectSettings(-1, -1);
	}

	/**
	 * JPQL Query - findAllLabInspectSettings
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabInspectSetting> findAllLabInspectSettings(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllLabInspectSettings", startResult, maxRows);
		return new LinkedHashSet<LabInspectSetting>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabInspectSettingByStartTime
	 *
	 */
	@Transactional
	public Set<LabInspectSetting> findLabInspectSettingByStartTime(java.util.Calendar startTime) throws DataAccessException {

		return findLabInspectSettingByStartTime(startTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectSettingByStartTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabInspectSetting> findLabInspectSettingByStartTime(java.util.Calendar startTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabInspectSettingByStartTime", startResult, maxRows, startTime);
		return new LinkedHashSet<LabInspectSetting>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabInspectSettingByStartTimeBefore
	 *
	 */
	@Transactional
	public Set<LabInspectSetting> findLabInspectSettingByStartTimeBefore(java.util.Calendar startTime) throws DataAccessException {

		return findLabInspectSettingByStartTimeBefore(startTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectSettingByStartTimeBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabInspectSetting> findLabInspectSettingByStartTimeBefore(java.util.Calendar startTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabInspectSettingByStartTimeBefore", startResult, maxRows, startTime);
		return new LinkedHashSet<LabInspectSetting>(query.getResultList());
	}

	/**
	 * JPQL Query - findLabInspectSettingById
	 *
	 */
	@Transactional
	public LabInspectSetting findLabInspectSettingById(Integer id) throws DataAccessException {

		return findLabInspectSettingById(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectSettingById
	 *
	 */

	@Transactional
	public LabInspectSetting findLabInspectSettingById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findLabInspectSettingById", startResult, maxRows, id);
			return (net.zjcclims.domain.LabInspectSetting) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findLabInspectSettingByEndTime
	 *
	 */
	@Transactional
	public Set<LabInspectSetting> findLabInspectSettingByEndTime(java.util.Calendar endTime) throws DataAccessException {

		return findLabInspectSettingByEndTime(endTime, -1, -1);
	}

	/**
	 * JPQL Query - findLabInspectSettingByEndTime
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<LabInspectSetting> findLabInspectSettingByEndTime(java.util.Calendar endTime, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findLabInspectSettingByEndTime", startResult, maxRows, endTime);
		return new LinkedHashSet<LabInspectSetting>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 *
	 *
	 */
	public boolean canBeMerged(LabInspectSetting entity) {
		return true;
	}
}
