package net.zjcclims.dao;

import net.zjcclims.domain.ReservationSetItem;
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

@Repository("ReservationSetItemDAO")
@Transactional
public class ReservationSetItemDAOImpl extends AbstractJpaDao<ReservationSetItem> implements
        ReservationSetItemDAO{

    /**
     * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
     *
     */
    private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ReservationSetItem.class }));

    /**
     * EntityManager injected by Spring for persistence unit zjcclimsConn
     *
     */
    @PersistenceContext(unitName = "zjcclimsConn")
    private EntityManager entityManager;

    /**
     * Instantiates a new LabRoomDAOImpl
     *
     */
    public ReservationSetItemDAOImpl() {
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
    public ReservationSetItem findReservationSetItemById(Integer id) throws DataAccessException {

        return findReservationSetItemById(id,-1,-1);
    }
    @Transactional
    public ReservationSetItem findReservationSetItemById(Integer id, int startResult, int maxRows) throws DataAccessException{
        try {
            Query query = createNamedQuery("findReservationSetItemById", startResult, maxRows, id);
            return (net.zjcclims.domain.ReservationSetItem) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }


    @Transactional
    public ReservationSetItem findReservationSetItemBylabRoomIdAndType(Integer id,Integer type) throws DataAccessException{

        return findReservationSetItemBylabRoomIdAndType(id,type,-1,-1);
    }


    @Transactional
    public ReservationSetItem findReservationSetItemBylabRoomIdAndType(Integer id,Integer type, int startResult, int maxRows) throws DataAccessException{
        try {
            Query query = createNamedQuery("findReservationSetItemBylabRoomIdAndType", startResult, maxRows, id, type);
            return (net.zjcclims.domain.ReservationSetItem) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
    /**
     * Used to determine whether or not to merge the entity or persist the entity when calling Store
     * @see
     *
     *
     */
    public boolean canBeMerged(ReservationSetItem entity) {
        return true;
    }

}
