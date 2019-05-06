package net.zjcclims.dao;

import net.zjcclims.domain.Asset;
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
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * DAO to manage Asset entities.
 * 
 */
@Repository("AssetClassificationDAO")
@Transactional
public class AssetClassificationDAOImpl extends AbstractJpaDao<AssetClassification> implements AssetClassificationDAO {



	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new AssetDAOImpl
	 *
	 */
	public AssetClassificationDAOImpl() {
		super();
	}

	/**
	 * Get the entity manager that manages persistence unit
	 *
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { AssetClassification.class }));
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
	public boolean canBeMerged(AssetClassification entity) {
		return true;
	}

	/**
	 * JPQL Query - findAsseClassificationtById
	 *
	 */
	@Transactional
	public AssetClassification findAsseClassificationtById(Integer id) throws DataAccessException {

		return findAsseClassificationtById(id, -1, -1);
	}

	/**
	 * JPQL Query - findAsseClassificationtById
	 *
	 */

	@Transactional
	public AssetClassification findAsseClassificationtById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findAsseClassificationtById", startResult, maxRows, id);
			return (net.zjcclims.domain.AssetClassification) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}
}
