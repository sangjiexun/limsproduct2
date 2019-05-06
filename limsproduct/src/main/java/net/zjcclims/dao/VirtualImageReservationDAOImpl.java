package net.zjcclims.dao;


import net.zjcclims.domain.VirtualImage;
import net.zjcclims.domain.VirtualImageReservation;
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
 * DAO to manage VirtualLabConstruction entities.
 * 
 */
@Repository("VirtualImageReservationDAO")
@Transactional
public class VirtualImageReservationDAOImpl extends AbstractJpaDao<VirtualImageReservation>
		implements VirtualImageReservationDAO {

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
	public VirtualImageReservationDAOImpl() {
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

	@Override
	public boolean canBeMerged(VirtualImageReservation virtualImageReservation) {
		return false;
	}

	@Transactional
	public VirtualImageReservation findVirtualImageReservationByPrimaryKey(Integer id) throws DataAccessException {

		return findVirtualImageReservationByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findLabRoomByPrimaryKey
	 *
	 */

	@Transactional
	public VirtualImageReservation findVirtualImageReservationByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findVirtualImageReservationByPrimaryKey", startResult, maxRows, id);
			return (VirtualImageReservation) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
