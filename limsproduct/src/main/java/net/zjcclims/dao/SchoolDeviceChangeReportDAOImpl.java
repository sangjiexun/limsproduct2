package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.SchoolDeviceChangeReport;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage SchoolDeviceChangeReport entities.
 * 
 */
@Repository("SchoolDeviceChangeReportDAO")
@Transactional
public class SchoolDeviceChangeReportDAOImpl extends AbstractJpaDao<SchoolDeviceChangeReport>
		implements SchoolDeviceChangeReportDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { SchoolDeviceChangeReport.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new SchoolDeviceChangeReportDAOImpl
	 *
	 */
	public SchoolDeviceChangeReportDAOImpl() {
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
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberValueLast
	 *
	 */
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberValueLast(Integer deviceNumberValueLast) throws DataAccessException {

		return findSchoolDeviceChangeReportByDeviceNumberValueLast(deviceNumberValueLast, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberValueLast
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberValueLast(Integer deviceNumberValueLast, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolDeviceChangeReportByDeviceNumberValueLast", startResult, maxRows, deviceNumberValueLast);
		return new LinkedHashSet<SchoolDeviceChangeReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberReduce
	 *
	 */
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberReduce(Integer deviceNumberReduce) throws DataAccessException {

		return findSchoolDeviceChangeReportByDeviceNumberReduce(deviceNumberReduce, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberReduce
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberReduce(Integer deviceNumberReduce, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolDeviceChangeReportByDeviceNumberReduce", startResult, maxRows, deviceNumberReduce);
		return new LinkedHashSet<SchoolDeviceChangeReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceThis
	 *
	 */
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceThis(java.math.BigDecimal devicePriceThis) throws DataAccessException {

		return findSchoolDeviceChangeReportByDevicePriceThis(devicePriceThis, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceThis
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceThis(java.math.BigDecimal devicePriceThis, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolDeviceChangeReportByDevicePriceThis", startResult, maxRows, devicePriceThis);
		return new LinkedHashSet<SchoolDeviceChangeReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberThis
	 *
	 */
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberThis(Integer deviceNumberThis) throws DataAccessException {

		return findSchoolDeviceChangeReportByDeviceNumberThis(deviceNumberThis, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberThis
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberThis(Integer deviceNumberThis, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolDeviceChangeReportByDeviceNumberThis", startResult, maxRows, deviceNumberThis);
		return new LinkedHashSet<SchoolDeviceChangeReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByPrimaryKey
	 *
	 */
	@Transactional
	public SchoolDeviceChangeReport findSchoolDeviceChangeReportByPrimaryKey(Integer id) throws DataAccessException {

		return findSchoolDeviceChangeReportByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByPrimaryKey
	 *
	 */

	@Transactional
	public SchoolDeviceChangeReport findSchoolDeviceChangeReportByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSchoolDeviceChangeReportByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.SchoolDeviceChangeReport) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllSchoolDeviceChangeReports
	 *
	 */
	@Transactional
	public Set<SchoolDeviceChangeReport> findAllSchoolDeviceChangeReports() throws DataAccessException {

		return findAllSchoolDeviceChangeReports(-1, -1);
	}

	/**
	 * JPQL Query - findAllSchoolDeviceChangeReports
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolDeviceChangeReport> findAllSchoolDeviceChangeReports(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllSchoolDeviceChangeReports", startResult, maxRows);
		return new LinkedHashSet<SchoolDeviceChangeReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberAdd
	 *
	 */
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberAdd(Integer deviceNumberAdd) throws DataAccessException {

		return findSchoolDeviceChangeReportByDeviceNumberAdd(deviceNumberAdd, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberAdd
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberAdd(Integer deviceNumberAdd, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolDeviceChangeReportByDeviceNumberAdd", startResult, maxRows, deviceNumberAdd);
		return new LinkedHashSet<SchoolDeviceChangeReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByYearCode
	 *
	 */
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByYearCode(Integer yearCode) throws DataAccessException {

		return findSchoolDeviceChangeReportByYearCode(yearCode, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByYearCode
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByYearCode(Integer yearCode, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolDeviceChangeReportByYearCode", startResult, maxRows, yearCode);
		return new LinkedHashSet<SchoolDeviceChangeReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberValueThis
	 *
	 */
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberValueThis(Integer deviceNumberValueThis) throws DataAccessException {

		return findSchoolDeviceChangeReportByDeviceNumberValueThis(deviceNumberValueThis, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberValueThis
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberValueThis(Integer deviceNumberValueThis, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolDeviceChangeReportByDeviceNumberValueThis", startResult, maxRows, deviceNumberValueThis);
		return new LinkedHashSet<SchoolDeviceChangeReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportById
	 *
	 */
	@Transactional
	public SchoolDeviceChangeReport findSchoolDeviceChangeReportById(Integer id) throws DataAccessException {

		return findSchoolDeviceChangeReportById(id, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportById
	 *
	 */

	@Transactional
	public SchoolDeviceChangeReport findSchoolDeviceChangeReportById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSchoolDeviceChangeReportById", startResult, maxRows, id);
			return (net.zjcclims.domain.SchoolDeviceChangeReport) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceReduce
	 *
	 */
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceReduce(java.math.BigDecimal devicePriceReduce) throws DataAccessException {

		return findSchoolDeviceChangeReportByDevicePriceReduce(devicePriceReduce, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceReduce
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceReduce(java.math.BigDecimal devicePriceReduce, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolDeviceChangeReportByDevicePriceReduce", startResult, maxRows, devicePriceReduce);
		return new LinkedHashSet<SchoolDeviceChangeReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberLast
	 *
	 */
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberLast(Integer deviceNumberLast) throws DataAccessException {

		return findSchoolDeviceChangeReportByDeviceNumberLast(deviceNumberLast, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberLast
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberLast(Integer deviceNumberLast, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolDeviceChangeReportByDeviceNumberLast", startResult, maxRows, deviceNumberLast);
		return new LinkedHashSet<SchoolDeviceChangeReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceLast
	 *
	 */
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceLast(java.math.BigDecimal devicePriceLast) throws DataAccessException {

		return findSchoolDeviceChangeReportByDevicePriceLast(devicePriceLast, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceLast
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceLast(java.math.BigDecimal devicePriceLast, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolDeviceChangeReportByDevicePriceLast", startResult, maxRows, devicePriceLast);
		return new LinkedHashSet<SchoolDeviceChangeReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceValueThis
	 *
	 */
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceValueThis(java.math.BigDecimal devicePriceValueThis) throws DataAccessException {

		return findSchoolDeviceChangeReportByDevicePriceValueThis(devicePriceValueThis, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceValueThis
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceValueThis(java.math.BigDecimal devicePriceValueThis, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolDeviceChangeReportByDevicePriceValueThis", startResult, maxRows, devicePriceValueThis);
		return new LinkedHashSet<SchoolDeviceChangeReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceValueLast
	 *
	 */
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceValueLast(java.math.BigDecimal devicePriceValueLast) throws DataAccessException {

		return findSchoolDeviceChangeReportByDevicePriceValueLast(devicePriceValueLast, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceValueLast
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceValueLast(java.math.BigDecimal devicePriceValueLast, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolDeviceChangeReportByDevicePriceValueLast", startResult, maxRows, devicePriceValueLast);
		return new LinkedHashSet<SchoolDeviceChangeReport>(query.getResultList());
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceAdd
	 *
	 */
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceAdd(java.math.BigDecimal devicePriceAdd) throws DataAccessException {

		return findSchoolDeviceChangeReportByDevicePriceAdd(devicePriceAdd, -1, -1);
	}

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceAdd
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceAdd(java.math.BigDecimal devicePriceAdd, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSchoolDeviceChangeReportByDevicePriceAdd", startResult, maxRows, devicePriceAdd);
		return new LinkedHashSet<SchoolDeviceChangeReport>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(SchoolDeviceChangeReport entity) {
		return true;
	}
}
