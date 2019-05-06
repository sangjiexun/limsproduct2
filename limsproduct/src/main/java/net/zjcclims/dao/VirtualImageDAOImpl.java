package net.zjcclims.dao;


import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.VirtualImage;
import net.zjcclims.domain.VirtualLabConstruction;
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
 * DAO to manage VirtualLabConstruction entities.
 * 
 */
@Repository("VirtualImageDAO")
@Transactional
public class VirtualImageDAOImpl extends AbstractJpaDao<VirtualImage>
		implements VirtualImageDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { VirtualImage.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new VirtualLabConstructionDAOImpl
	 *
	 */
	public VirtualImageDAOImpl() {
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

	@Transactional
	public VirtualImage findVirtualImageByPrimaryKey(Integer id) throws DataAccessException {

		return findVirtualImageByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomByPrimaryKey
	 *
	 */

	@Transactional
	public VirtualImage findVirtualImageByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findVirtualImageByPrimaryKey", startResult, maxRows, id);
			return (net.zjcclims.domain.VirtualImage) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public boolean canBeMerged(VirtualImage virtualImage) {
		return false;
	}
}
