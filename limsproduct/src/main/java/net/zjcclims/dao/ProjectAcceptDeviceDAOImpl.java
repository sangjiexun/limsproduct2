package net.zjcclims.dao;


import net.zjcclims.domain.ProjectAcceptDevice;
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
 * DAO to manage ProjectAcceptDevice entities.
 * 
 */
@Repository("ProjectAcceptDeviceDAO")
@Transactional
public class ProjectAcceptDeviceDAOImpl extends AbstractJpaDao<ProjectAcceptDevice>
		implements ProjectAcceptDeviceDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ProjectAcceptDevice.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ProjectAcceptDeviceDAOImpl
	 *
	 */
	public ProjectAcceptDeviceDAOImpl() {
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
	 * JPQL Query - findProjectAcceptDeviceByPurchasePattern
	 *
	 */
	@Transactional
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByPurchasePattern(String purchasePattern) throws DataAccessException {

		return findProjectAcceptDeviceByPurchasePattern(purchasePattern, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptDeviceByPurchasePattern
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByPurchasePattern(String purchasePattern, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptDeviceByPurchasePattern", startResult, maxRows, purchasePattern);
		return new LinkedHashSet<ProjectAcceptDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllProjectAcceptDevices
	 *
	 */
	@Transactional
	public Set<ProjectAcceptDevice> findAllProjectAcceptDevices() throws DataAccessException {

		return findAllProjectAcceptDevices(-1, -1);
	}

	/**
	 * JPQL Query - findAllProjectAcceptDevices
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptDevice> findAllProjectAcceptDevices(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllProjectAcceptDevices", startResult, maxRows);
		return new LinkedHashSet<ProjectAcceptDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptDeviceById
	 *
	 */
	@Transactional
	public ProjectAcceptDevice findProjectAcceptDeviceById(Integer id) throws DataAccessException {

		return findProjectAcceptDeviceById(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptDeviceById
	 *
	 */

	@Transactional
	public ProjectAcceptDevice findProjectAcceptDeviceById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectAcceptDeviceById", startResult, maxRows, id);
			return (ProjectAcceptDevice) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectAcceptDeviceByAmount
	 *
	 */
	@Transactional
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByAmount(java.math.BigDecimal amount) throws DataAccessException {

		return findProjectAcceptDeviceByAmount(amount, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptDeviceByAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByAmount(java.math.BigDecimal amount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptDeviceByAmount", startResult, maxRows, amount);
		return new LinkedHashSet<ProjectAcceptDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptDeviceByUnitPrice
	 *
	 */
	@Transactional
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByUnitPrice(java.math.BigDecimal unitPrice) throws DataAccessException {

		return findProjectAcceptDeviceByUnitPrice(unitPrice, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptDeviceByUnitPrice
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByUnitPrice(java.math.BigDecimal unitPrice, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptDeviceByUnitPrice", startResult, maxRows, unitPrice);
		return new LinkedHashSet<ProjectAcceptDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptDeviceByFormat
	 *
	 */
	@Transactional
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByFormat(String format) throws DataAccessException {

		return findProjectAcceptDeviceByFormat(format, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptDeviceByFormat
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByFormat(String format, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptDeviceByFormat", startResult, maxRows, format);
		return new LinkedHashSet<ProjectAcceptDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptDeviceByEquipmentNameContaining
	 *
	 */
	@Transactional
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByEquipmentNameContaining(String equipmentName) throws DataAccessException {

		return findProjectAcceptDeviceByEquipmentNameContaining(equipmentName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptDeviceByEquipmentNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByEquipmentNameContaining(String equipmentName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptDeviceByEquipmentNameContaining", startResult, maxRows, equipmentName);
		return new LinkedHashSet<ProjectAcceptDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptDeviceByPrimaryKey
	 *
	 */
	@Transactional
	public ProjectAcceptDevice findProjectAcceptDeviceByPrimaryKey(Integer id) throws DataAccessException {

		return findProjectAcceptDeviceByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptDeviceByPrimaryKey
	 *
	 */

	@Transactional
	public ProjectAcceptDevice findProjectAcceptDeviceByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectAcceptDeviceByPrimaryKey", startResult, maxRows, id);
			return (ProjectAcceptDevice) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectAcceptDeviceByFormatContaining
	 *
	 */
	@Transactional
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByFormatContaining(String format) throws DataAccessException {

		return findProjectAcceptDeviceByFormatContaining(format, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptDeviceByFormatContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByFormatContaining(String format, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptDeviceByFormatContaining", startResult, maxRows, format);
		return new LinkedHashSet<ProjectAcceptDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptDeviceByCollection
	 *
	 */
	@Transactional
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByCollection(java.math.BigDecimal collection) throws DataAccessException {

		return findProjectAcceptDeviceByCollection(collection, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptDeviceByCollection
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByCollection(java.math.BigDecimal collection, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptDeviceByCollection", startResult, maxRows, collection);
		return new LinkedHashSet<ProjectAcceptDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptDeviceByEquipmentName
	 *
	 */
	@Transactional
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByEquipmentName(String equipmentName) throws DataAccessException {

		return findProjectAcceptDeviceByEquipmentName(equipmentName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptDeviceByEquipmentName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByEquipmentName(String equipmentName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptDeviceByEquipmentName", startResult, maxRows, equipmentName);
		return new LinkedHashSet<ProjectAcceptDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptDeviceByPurchasePatternContaining
	 *
	 */
	@Transactional
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByPurchasePatternContaining(String purchasePattern) throws DataAccessException {

		return findProjectAcceptDeviceByPurchasePatternContaining(purchasePattern, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptDeviceByPurchasePatternContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByPurchasePatternContaining(String purchasePattern, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptDeviceByPurchasePatternContaining", startResult, maxRows, purchasePattern);
		return new LinkedHashSet<ProjectAcceptDevice>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(ProjectAcceptDevice entity) {
		return true;
	}
}
