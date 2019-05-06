package net.zjcclims.dao;


import net.zjcclims.domain.ProjectStartDevice;
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
 * DAO to manage ProjectStartDevice entities.
 * 
 */
@Repository("ProjectStartDeviceDAO")
@Transactional
public class ProjectStartDeviceDAOImpl extends AbstractJpaDao<ProjectStartDevice>
		implements ProjectStartDeviceDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ProjectStartDevice.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ProjectStartDeviceDAOImpl
	 *
	 */
	public ProjectStartDeviceDAOImpl() {
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
	 * JPQL Query - findProjectStartDeviceByUnitPrice
	 *
	 */
	@Transactional
	public Set<ProjectStartDevice> findProjectStartDeviceByUnitPrice(java.math.BigDecimal unitPrice) throws DataAccessException {

		return findProjectStartDeviceByUnitPrice(unitPrice, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartDeviceByUnitPrice
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartDevice> findProjectStartDeviceByUnitPrice(java.math.BigDecimal unitPrice, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartDeviceByUnitPrice", startResult, maxRows, unitPrice);
		return new LinkedHashSet<ProjectStartDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartDeviceByPurchasePatternContaining
	 *
	 */
	@Transactional
	public Set<ProjectStartDevice> findProjectStartDeviceByPurchasePatternContaining(String purchasePattern) throws DataAccessException {

		return findProjectStartDeviceByPurchasePatternContaining(purchasePattern, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartDeviceByPurchasePatternContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartDevice> findProjectStartDeviceByPurchasePatternContaining(String purchasePattern, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartDeviceByPurchasePatternContaining", startResult, maxRows, purchasePattern);
		return new LinkedHashSet<ProjectStartDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllProjectStartDevices
	 *
	 */
	@Transactional
	public Set<ProjectStartDevice> findAllProjectStartDevices() throws DataAccessException {

		return findAllProjectStartDevices(-1, -1);
	}

	/**
	 * JPQL Query - findAllProjectStartDevices
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartDevice> findAllProjectStartDevices(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllProjectStartDevices", startResult, maxRows);
		return new LinkedHashSet<ProjectStartDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartDeviceByAmount
	 *
	 */
	@Transactional
	public Set<ProjectStartDevice> findProjectStartDeviceByAmount(java.math.BigDecimal amount) throws DataAccessException {

		return findProjectStartDeviceByAmount(amount, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartDeviceByAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartDevice> findProjectStartDeviceByAmount(java.math.BigDecimal amount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartDeviceByAmount", startResult, maxRows, amount);
		return new LinkedHashSet<ProjectStartDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartDeviceByFormatContaining
	 *
	 */
	@Transactional
	public Set<ProjectStartDevice> findProjectStartDeviceByFormatContaining(String format) throws DataAccessException {

		return findProjectStartDeviceByFormatContaining(format, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartDeviceByFormatContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartDevice> findProjectStartDeviceByFormatContaining(String format, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartDeviceByFormatContaining", startResult, maxRows, format);
		return new LinkedHashSet<ProjectStartDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartDeviceById
	 *
	 */
	@Transactional
	public ProjectStartDevice findProjectStartDeviceById(Integer id) throws DataAccessException {

		return findProjectStartDeviceById(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartDeviceById
	 *
	 */

	@Transactional
	public ProjectStartDevice findProjectStartDeviceById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectStartDeviceById", startResult, maxRows, id);
			return (ProjectStartDevice) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectStartDeviceByCollection
	 *
	 */
	@Transactional
	public Set<ProjectStartDevice> findProjectStartDeviceByCollection(java.math.BigDecimal collection) throws DataAccessException {

		return findProjectStartDeviceByCollection(collection, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartDeviceByCollection
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartDevice> findProjectStartDeviceByCollection(java.math.BigDecimal collection, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartDeviceByCollection", startResult, maxRows, collection);
		return new LinkedHashSet<ProjectStartDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartDeviceByPurchasePattern
	 *
	 */
	@Transactional
	public Set<ProjectStartDevice> findProjectStartDeviceByPurchasePattern(String purchasePattern) throws DataAccessException {

		return findProjectStartDeviceByPurchasePattern(purchasePattern, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartDeviceByPurchasePattern
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartDevice> findProjectStartDeviceByPurchasePattern(String purchasePattern, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartDeviceByPurchasePattern", startResult, maxRows, purchasePattern);
		return new LinkedHashSet<ProjectStartDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartDeviceByEquipmentNameContaining
	 *
	 */
	@Transactional
	public Set<ProjectStartDevice> findProjectStartDeviceByEquipmentNameContaining(String equipmentName) throws DataAccessException {

		return findProjectStartDeviceByEquipmentNameContaining(equipmentName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartDeviceByEquipmentNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartDevice> findProjectStartDeviceByEquipmentNameContaining(String equipmentName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartDeviceByEquipmentNameContaining", startResult, maxRows, equipmentName);
		return new LinkedHashSet<ProjectStartDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartDeviceByEquipmentName
	 *
	 */
	@Transactional
	public Set<ProjectStartDevice> findProjectStartDeviceByEquipmentName(String equipmentName) throws DataAccessException {

		return findProjectStartDeviceByEquipmentName(equipmentName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartDeviceByEquipmentName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartDevice> findProjectStartDeviceByEquipmentName(String equipmentName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartDeviceByEquipmentName", startResult, maxRows, equipmentName);
		return new LinkedHashSet<ProjectStartDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartDeviceByPrimaryKey
	 *
	 */
	@Transactional
	public ProjectStartDevice findProjectStartDeviceByPrimaryKey(Integer id) throws DataAccessException {

		return findProjectStartDeviceByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartDeviceByPrimaryKey
	 *
	 */

	@Transactional
	public ProjectStartDevice findProjectStartDeviceByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectStartDeviceByPrimaryKey", startResult, maxRows, id);
			return (ProjectStartDevice) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectStartDeviceByFormat
	 *
	 */
	@Transactional
	public Set<ProjectStartDevice> findProjectStartDeviceByFormat(String format) throws DataAccessException {

		return findProjectStartDeviceByFormat(format, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartDeviceByFormat
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartDevice> findProjectStartDeviceByFormat(String format, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartDeviceByFormat", startResult, maxRows, format);
		return new LinkedHashSet<ProjectStartDevice>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(ProjectStartDevice entity) {
		return true;
	}
}
