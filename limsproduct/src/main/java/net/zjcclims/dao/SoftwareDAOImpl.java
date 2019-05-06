package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zjcclims.domain.Software;

import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 * DAO to manage Software entities.
 * 
 */
@Repository("SoftwareDAO")
@Transactional
public class SoftwareDAOImpl extends AbstractJpaDao<Software> implements
		SoftwareDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { Software.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new SoftwareDAOImpl
	 *
	 */
	public SoftwareDAOImpl() {
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
	 * JPQL Query - findSoftwareByNoContaining
	 *
	 */
	@Transactional
	public Set<Software> findSoftwareByNoContaining(String no) throws DataAccessException {

		return findSoftwareByNoContaining(no, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareByNoContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<Software> findSoftwareByNoContaining(String no, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareByNoContaining", startResult, maxRows, no);
		return new LinkedHashSet<Software>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareByProperty
	 *
	 */
	@Transactional
	public Set<Software> findSoftwareByProperty(String property) throws DataAccessException {

		return findSoftwareByProperty(property, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareByProperty
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<Software> findSoftwareByProperty(String property, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareByProperty", startResult, maxRows, property);
		return new LinkedHashSet<Software>(query.getResultList());
	}



	/**
	 * JPQL Query - findSoftwareByPurchaseDateBefore
	 *
	 */
	@Transactional
	public Set<Software> findSoftwareByPurchaseDateBefore(java.util.Calendar purchaseDate) throws DataAccessException {

		return findSoftwareByPurchaseDateBefore(purchaseDate, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareByPurchaseDateBefore
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<Software> findSoftwareByPurchaseDateBefore(java.util.Calendar purchaseDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareByPurchaseDateBefore", startResult, maxRows, purchaseDate);
		return new LinkedHashSet<Software>(query.getResultList());
	}


	/**
	 * JPQL Query - findSoftwareByPurchasePersonContaining
	 *
	 */
	@Transactional
	public Set<Software> findSoftwareByPurchasePersonContaining(String purchasePerson) throws DataAccessException {

		return findSoftwareByPurchasePersonContaining(purchasePerson, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareByPurchasePersonContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<Software> findSoftwareByPurchasePersonContaining(String purchasePerson, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareByPurchasePersonContaining", startResult, maxRows, purchasePerson);
		return new LinkedHashSet<Software>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareByPropertyContaining
	 *
	 */
	@Transactional
	public Set<Software> findSoftwareByPropertyContaining(String property) throws DataAccessException {

		return findSoftwareByPropertyContaining(property, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareByPropertyContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<Software> findSoftwareByPropertyContaining(String property, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareByPropertyContaining", startResult, maxRows, property);
		return new LinkedHashSet<Software>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareByEditionContaining
	 *
	 */
	@Transactional
	public Set<Software> findSoftwareByEditionContaining(String edition) throws DataAccessException {

		return findSoftwareByEditionContaining(edition, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareByEditionContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<Software> findSoftwareByEditionContaining(String edition, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareByEditionContaining", startResult, maxRows, edition);
		return new LinkedHashSet<Software>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareByPurchasePerson
	 *
	 */
	@Transactional
	public Set<Software> findSoftwareByPurchasePerson(String purchasePerson) throws DataAccessException {

		return findSoftwareByPurchasePerson(purchasePerson, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareByPurchasePerson
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<Software> findSoftwareByPurchasePerson(String purchasePerson, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareByPurchasePerson", startResult, maxRows, purchasePerson);
		return new LinkedHashSet<Software>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareByPrice
	 *
	 */
	@Transactional
	public Set<Software> findSoftwareByPrice(java.math.BigDecimal price) throws DataAccessException {

		return findSoftwareByPrice(price, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareByPrice
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<Software> findSoftwareByPrice(java.math.BigDecimal price, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareByPrice", startResult, maxRows, price);
		return new LinkedHashSet<Software>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareByNo
	 *
	 */
	@Transactional
	public Set<Software> findSoftwareByNo(String no) throws DataAccessException {

		return findSoftwareByNo(no, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareByNo
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<Software> findSoftwareByNo(String no, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareByNo", startResult, maxRows, no);
		return new LinkedHashSet<Software>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareByPurchaseDate
	 *
	 */
	@Transactional
	public Set<Software> findSoftwareByPurchaseDate(java.util.Calendar purchaseDate) throws DataAccessException {

		return findSoftwareByPurchaseDate(purchaseDate, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareByPurchaseDate
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<Software> findSoftwareByPurchaseDate(java.util.Calendar purchaseDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareByPurchaseDate", startResult, maxRows, purchaseDate);
		return new LinkedHashSet<Software>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareById
	 *
	 */
	@Transactional
	public Software findSoftwareById(Integer id) throws DataAccessException {

		return findSoftwareById(id, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareById
	 *
	 */

	@Transactional
	public Software findSoftwareById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSoftwareById", startResult, maxRows, id);
			return (net.zjcclims.domain.Software) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllSoftwares
	 *
	 */
	@Transactional
	public Set<Software> findAllSoftwares() throws DataAccessException {

		return findAllSoftwares(-1, -1);
	}

	/**
	 * JPQL Query - findAllSoftwares
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<Software> findAllSoftwares(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllSoftwares", startResult, maxRows);
		return new LinkedHashSet<Software>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareByNameContaining
	 *
	 */
	@Transactional
	public Set<Software> findSoftwareByNameContaining(String name) throws DataAccessException {

		return findSoftwareByNameContaining(name, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareByNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<Software> findSoftwareByNameContaining(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareByNameContaining", startResult, maxRows, name);
		return new LinkedHashSet<Software>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareByCode
	 *
	 */
	@Transactional
	public Set<Software> findSoftwareByCode(String code) throws DataAccessException {

		return findSoftwareByCode(code, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareByCode
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<Software> findSoftwareByCode(String code, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareByCode", startResult, maxRows, code);
		return new LinkedHashSet<Software>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareByAcademyContaining
	 *
	 */
	@Transactional
	public Set<Software> findSoftwareByAcademyContaining(String academy) throws DataAccessException {

		return findSoftwareByAcademyContaining(academy, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareByAcademyContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<Software> findSoftwareByAcademyContaining(String academy, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareByAcademyContaining", startResult, maxRows, academy);
		return new LinkedHashSet<Software>(query.getResultList());
	}




	/**
	 * JPQL Query - findSoftwareByBuyType
	 *
	 */
	@Transactional
	public Set<Software> findSoftwareByBuyType(Integer buyType) throws DataAccessException {

		return findSoftwareByBuyType(buyType, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareByBuyType
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<Software> findSoftwareByBuyType(Integer buyType, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareByBuyType", startResult, maxRows, buyType);
		return new LinkedHashSet<Software>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareByAcademy
	 *
	 */
	@Transactional
	public Set<Software> findSoftwareByAcademy(String academy) throws DataAccessException {

		return findSoftwareByAcademy(academy, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareByAcademy
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<Software> findSoftwareByAcademy(String academy, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareByAcademy", startResult, maxRows, academy);
		return new LinkedHashSet<Software>(query.getResultList());
	}


	/**
	 * JPQL Query - findSoftwareByEdition
	 *
	 */
	@Transactional
	public Set<Software> findSoftwareByEdition(String edition) throws DataAccessException {

		return findSoftwareByEdition(edition, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareByEdition
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<Software> findSoftwareByEdition(String edition, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareByEdition", startResult, maxRows, edition);
		return new LinkedHashSet<Software>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareByPurchaseDateAfter
	 *
	 */
	@Transactional
	public Set<Software> findSoftwareByPurchaseDateAfter(java.util.Calendar purchaseDate) throws DataAccessException {

		return findSoftwareByPurchaseDateAfter(purchaseDate, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareByPurchaseDateAfter
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<Software> findSoftwareByPurchaseDateAfter(java.util.Calendar purchaseDate, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareByPurchaseDateAfter", startResult, maxRows, purchaseDate);
		return new LinkedHashSet<Software>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareByName
	 *
	 */
	@Transactional
	public Set<Software> findSoftwareByName(String name) throws DataAccessException {

		return findSoftwareByName(name, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareByName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<Software> findSoftwareByName(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareByName", startResult, maxRows, name);
		return new LinkedHashSet<Software>(query.getResultList());
	}

	/**
	 * JPQL Query - findSoftwareByPrimaryKey
	 *
	 */
	@Transactional
	public Software findSoftwareByPrimaryKey(Integer id) throws DataAccessException {

		return findSoftwareByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareByPrimaryKey
	 *
	 */

	@Transactional
	public Software findSoftwareByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findSoftwareByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.Software) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findSoftwareByCodeContaining
	 *
	 */
	@Transactional
	public Set<Software> findSoftwareByCodeContaining(String code) throws DataAccessException {

		return findSoftwareByCodeContaining(code, -1, -1);
	}

	/**
	 * JPQL Query - findSoftwareByCodeContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<Software> findSoftwareByCodeContaining(String code, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findSoftwareByCodeContaining", startResult, maxRows, code);
		return new LinkedHashSet<Software>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(Software entity) {
		return true;
	}
}
