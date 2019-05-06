package net.zjcclims.dao;

import net.zjcclims.domain.Asset;
import net.zjcclims.domain.AssetCabinet;
import net.zjcclims.domain.AssetCabinetRecord;
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
 * DAO to manage AssetCabinet entities.
 * 
 */
@Repository("AssetCabinetRecordDAO")
@Transactional
public class AssetCabinetRecordDAOImpl extends AbstractJpaDao<AssetCabinetRecord> implements
		AssetCabinetRecordDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { AssetCabinet.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new AssetCabinetDAOImpl
	 *
	 */
	public AssetCabinetRecordDAOImpl() {
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
	 * JPQL Query - findAllAssetCabinets
	 *
	 */
	@Transactional
	public Set<AssetCabinet> findAllAssetCabinets() throws DataAccessException {

		return findAllAssetCabinets(-1, -1);
	}

	/**
	 * JPQL Query - findAllAssetCabinets
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<AssetCabinet> findAllAssetCabinets(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllAssetCabinets", startResult, maxRows);
		return new LinkedHashSet<AssetCabinet>(query.getResultList());
	}

	/**
	 * JPQL Query - findAssetCabinetByCabinetNameContaining
	 *
	 */
	@Transactional
	public Set<AssetCabinet> findAssetCabinetByCabinetNameContaining(String cabinetName) throws DataAccessException {

		return findAssetCabinetByCabinetNameContaining(cabinetName, -1, -1);
	}

	/**
	 * JPQL Query - findAssetCabinetByCabinetNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<AssetCabinet> findAssetCabinetByCabinetNameContaining(String cabinetName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAssetCabinetByCabinetNameContaining", startResult, maxRows, cabinetName);
		return new LinkedHashSet<AssetCabinet>(query.getResultList());
	}

	/**
	 * JPQL Query - findAssetCabinetByPrimaryKey
	 *
	 */
	@Transactional
	public AssetCabinet findAssetCabinetByPrimaryKey(Integer id) throws DataAccessException {

		return findAssetCabinetByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findAssetCabinetByPrimaryKey
	 *
	 */

	@Transactional
	public AssetCabinet findAssetCabinetByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findAssetCabinetByPrimaryKey", startResult, maxRows, id);
			return (AssetCabinet) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAssetCabinetById
	 *
	 */
	@Transactional
	public AssetCabinet findAssetCabinetById(Integer id) throws DataAccessException {

		return findAssetCabinetById(id, -1, -1);
	}

	/**
	 * JPQL Query - findAssetCabinetById
	 *
	 */

	@Transactional
	public AssetCabinet findAssetCabinetById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findAssetCabinetById", startResult, maxRows, id);
			return (AssetCabinet) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAssetCabinetByCabinetCode
	 *
	 */
	@Transactional
	public Set<AssetCabinet> findAssetCabinetByCabinetCode(String cabinetCode) throws DataAccessException {

		return findAssetCabinetByCabinetCode(cabinetCode, -1, -1);
	}

	/**
	 * JPQL Query - findAssetCabinetByCabinetCode
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<AssetCabinet> findAssetCabinetByCabinetCode(String cabinetCode, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAssetCabinetByCabinetCode", startResult, maxRows, cabinetCode);
		return new LinkedHashSet<AssetCabinet>(query.getResultList());
	}

	/**
	 * JPQL Query - findAssetCabinetByCabinetName
	 *
	 */
	@Transactional
	public Set<AssetCabinet> findAssetCabinetByCabinetName(String cabinetName) throws DataAccessException {

		return findAssetCabinetByCabinetName(cabinetName, -1, -1);
	}

	/**
	 * JPQL Query - findAssetCabinetByCabinetName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<AssetCabinet> findAssetCabinetByCabinetName(String cabinetName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAssetCabinetByCabinetName", startResult, maxRows, cabinetName);
		return new LinkedHashSet<AssetCabinet>(query.getResultList());
	}

	/**
	 * JPQL Query - findAssetCabinetByCabinetCodeContaining
	 *
	 */
	@Transactional
	public Set<AssetCabinet> findAssetCabinetByCabinetCodeContaining(String cabinetCode) throws DataAccessException {

		return findAssetCabinetByCabinetCodeContaining(cabinetCode, -1, -1);
	}
	/**
	 * JPQL Query - findAssetCabinetByCabinetCodeContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<AssetCabinet> findAssetCabinetByCabinetCodeContaining(String cabinetCode, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAssetCabinetByCabinetCodeContaining", startResult, maxRows, cabinetCode);
		return new LinkedHashSet<AssetCabinet>(query.getResultList());
	}


	/**
	 * JPQL Query - findAssetCabinetRecordById
	 *
	 */
	@Transactional
	public AssetCabinetRecord findAssetCabinetRecordById(Integer id) throws DataAccessException {

		return findAssetCabinetRecordById(id, -1, -1);
	}

	/**
	 * JPQL Query - findAssetCabinetRecordById
	 *
	 */

	@Transactional
	public AssetCabinetRecord findAssetCabinetRecordById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findAssetCabinetRecordById", startResult, maxRows,id );
			return (net.zjcclims.domain.AssetCabinetRecord) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAssetCabinetRecordsByAssetId
	 *
	 */
	@Transactional
	public Set<AssetCabinetRecord> findAssetCabinetRecordsByAssetId(Integer assetId) throws DataAccessException {

		return findAssetCabinetRecordsByAssetId(assetId, -1, -1);
	}

	/**
	 * JPQL Query - findAssetCabinetRecordsByAssetId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<AssetCabinetRecord> findAssetCabinetRecordsByAssetId(Integer assetId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAssetCabinetRecordsByAssetId",startResult, maxRows,assetId);
		return new LinkedHashSet<AssetCabinetRecord>(query.getResultList());
	}




	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see store
	 * 
	 *
	 */
	public boolean canBeMerged(AssetCabinetRecord entity) {
		return true;
	}
}
