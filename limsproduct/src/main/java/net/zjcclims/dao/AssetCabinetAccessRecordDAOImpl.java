package net.zjcclims.dao;

import net.zjcclims.domain.AssetCabinetAccessRecord;
import net.zjcclims.domain.AssetClassification;
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
import java.util.Set;

/**
 * DAO to manage Asset entities.
 * 
 */
@Repository("AssetCabinetAccessRecordDAO")
@Transactional
public class AssetCabinetAccessRecordDAOImpl extends AbstractJpaDao<AssetCabinetAccessRecord> implements AssetCabinetAccessRecordDAO {



	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new AssetDAOImpl
	 *
	 */
	public AssetCabinetAccessRecordDAOImpl() {
		super();
	}

	/**
	 * Get the entity manager that manages persistence unit
	 *
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { AssetCabinetAccessRecord.class }));
	/**
	 * Returns the set of entity classes managed by this DAO.
	 *
	 */
	public Set<Class<?>> getTypes() {
		return dataTypes;
	}

	/**
	 * JPQL Query - findAssetByEnAlias
	 *
	 */


	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 *
	 *
	 *
	 */
	public boolean canBeMerged(AssetCabinetAccessRecord entity) {
		return true;
	}


}
