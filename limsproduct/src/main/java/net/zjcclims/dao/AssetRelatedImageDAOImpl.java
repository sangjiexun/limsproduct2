package net.zjcclims.dao;

import net.zjcclims.domain.AssetReceiveRecord;
import net.zjcclims.domain.AssetRelatedImage;
import net.zjcclims.domain.AssetStorage;
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
 * DAO to manage AssetReceiveRecord entities.
 * 
 */
@Repository("AssetRelatedImageDAO")
@Transactional
public class AssetRelatedImageDAOImpl extends AbstractJpaDao<AssetRelatedImage>
		implements AssetRelatedImageDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { AssetReceiveRecord.class }));

	/**
	 * EntityManager injected by Spring for persistence unit zjcclimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new AssetReceiveRecordDAOImpl
	 *
	 */
	public AssetRelatedImageDAOImpl() {
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
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 *
	 *
	 */
	public boolean canBeMerged(AssetRelatedImage entity) {
		return true;
	}
}
