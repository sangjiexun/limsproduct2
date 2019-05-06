package net.zjcclims.dao;


import net.zjcclims.domain.ProjectDevice;
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
 * DAO to manage ProjectDevice entities.
 * 
 */
@Repository("ProjectDeviceDAO")
@Transactional
public class ProjectDeviceDAOImpl extends AbstractJpaDao<ProjectDevice>
		implements ProjectDeviceDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ProjectDevice.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulims
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ProjectDeviceDAOImpl
	 *
	 */
	public ProjectDeviceDAOImpl() {
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
	 * JPQL Query - findAllProjectDevices
	 *
	 */
	@Transactional
	public Set<ProjectDevice> findAllProjectDevices() throws DataAccessException {

		return findAllProjectDevices(-1, -1);
	}

	/**
	 * JPQL Query - findAllProjectDevices
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectDevice> findAllProjectDevices(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllProjectDevices", startResult, maxRows);
		return new LinkedHashSet<ProjectDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectDeviceByFormat
	 *
	 */
	@Transactional
	public Set<ProjectDevice> findProjectDeviceByFormat(String format) throws DataAccessException {

		return findProjectDeviceByFormat(format, -1, -1);
	}

	/**
	 * JPQL Query - findProjectDeviceByFormat
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectDevice> findProjectDeviceByFormat(String format, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectDeviceByFormat", startResult, maxRows, format);
		return new LinkedHashSet<ProjectDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectDeviceByEquipmentNameContaining
	 *
	 */
	@Transactional
	public Set<ProjectDevice> findProjectDeviceByEquipmentNameContaining(String equipmentName) throws DataAccessException {

		return findProjectDeviceByEquipmentNameContaining(equipmentName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectDeviceByEquipmentNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectDevice> findProjectDeviceByEquipmentNameContaining(String equipmentName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectDeviceByEquipmentNameContaining", startResult, maxRows, equipmentName);
		return new LinkedHashSet<ProjectDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectDeviceByPurchasePatternContaining
	 *
	 */
	@Transactional
	public Set<ProjectDevice> findProjectDeviceByPurchasePatternContaining(String purchasePattern) throws DataAccessException {

		return findProjectDeviceByPurchasePatternContaining(purchasePattern, -1, -1);
	}

	/**
	 * JPQL Query - findProjectDeviceByPurchasePatternContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectDevice> findProjectDeviceByPurchasePatternContaining(String purchasePattern, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectDeviceByPurchasePatternContaining", startResult, maxRows, purchasePattern);
		return new LinkedHashSet<ProjectDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectDeviceByPrimaryKey
	 *
	 */
	@Transactional
	public ProjectDevice findProjectDeviceByPrimaryKey(Integer id) throws DataAccessException {

		return findProjectDeviceByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectDeviceByPrimaryKey
	 *
	 */

	@Transactional
	public ProjectDevice findProjectDeviceByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectDeviceByPrimaryKey", startResult, maxRows, id);
			return (ProjectDevice) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectDeviceByFormatContaining
	 *
	 */
	@Transactional
	public Set<ProjectDevice> findProjectDeviceByFormatContaining(String format) throws DataAccessException {

		return findProjectDeviceByFormatContaining(format, -1, -1);
	}

	/**
	 * JPQL Query - findProjectDeviceByFormatContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectDevice> findProjectDeviceByFormatContaining(String format, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectDeviceByFormatContaining", startResult, maxRows, format);
		return new LinkedHashSet<ProjectDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectDeviceByUnitPrice
	 *
	 */
	@Transactional
	public Set<ProjectDevice> findProjectDeviceByUnitPrice(java.math.BigDecimal unitPrice) throws DataAccessException {

		return findProjectDeviceByUnitPrice(unitPrice, -1, -1);
	}

	/**
	 * JPQL Query - findProjectDeviceByUnitPrice
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectDevice> findProjectDeviceByUnitPrice(java.math.BigDecimal unitPrice, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectDeviceByUnitPrice", startResult, maxRows, unitPrice);
		return new LinkedHashSet<ProjectDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectDeviceByAmount
	 *
	 */
	@Transactional
	public Set<ProjectDevice> findProjectDeviceByAmount(java.math.BigDecimal amount) throws DataAccessException {

		return findProjectDeviceByAmount(amount, -1, -1);
	}

	/**
	 * JPQL Query - findProjectDeviceByAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectDevice> findProjectDeviceByAmount(java.math.BigDecimal amount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectDeviceByAmount", startResult, maxRows, amount);
		return new LinkedHashSet<ProjectDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectDeviceByPurchasePattern
	 *
	 */
	@Transactional
	public Set<ProjectDevice> findProjectDeviceByPurchasePattern(String purchasePattern) throws DataAccessException {

		return findProjectDeviceByPurchasePattern(purchasePattern, -1, -1);
	}

	/**
	 * JPQL Query - findProjectDeviceByPurchasePattern
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectDevice> findProjectDeviceByPurchasePattern(String purchasePattern, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectDeviceByPurchasePattern", startResult, maxRows, purchasePattern);
		return new LinkedHashSet<ProjectDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectDeviceByCollection
	 *
	 */
	@Transactional
	public Set<ProjectDevice> findProjectDeviceByCollection(java.math.BigDecimal collection) throws DataAccessException {

		return findProjectDeviceByCollection(collection, -1, -1);
	}

	/**
	 * JPQL Query - findProjectDeviceByCollection
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectDevice> findProjectDeviceByCollection(java.math.BigDecimal collection, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectDeviceByCollection", startResult, maxRows, collection);
		return new LinkedHashSet<ProjectDevice>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectDeviceById
	 *
	 */
	@Transactional
	public ProjectDevice findProjectDeviceById(Integer id) throws DataAccessException {

		return findProjectDeviceById(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectDeviceById
	 *
	 */

	@Transactional
	public ProjectDevice findProjectDeviceById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectDeviceById", startResult, maxRows, id);
			return (ProjectDevice) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectDeviceByEquipmentName
	 *
	 */
	@Transactional
	public Set<ProjectDevice> findProjectDeviceByEquipmentName(String equipmentName) throws DataAccessException {

		return findProjectDeviceByEquipmentName(equipmentName, -1, -1);
	}

	/**
	 * JPQL Query - findProjectDeviceByEquipmentName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectDevice> findProjectDeviceByEquipmentName(String equipmentName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectDeviceByEquipmentName", startResult, maxRows, equipmentName);
		return new LinkedHashSet<ProjectDevice>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(ProjectDevice entity) {
		return true;
	}
}
