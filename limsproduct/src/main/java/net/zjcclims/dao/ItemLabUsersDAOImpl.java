package net.zjcclims.dao;

import net.zjcclims.domain.ItemLabUsers;
import org.skyway.spring.util.dao.AbstractJpaDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * DAO to manage DeviceRepair entities.
 *
 */
@Repository("ItemLabUsersDAO")
@Transactional
public class ItemLabUsersDAOImpl extends AbstractJpaDao<ItemLabUsers>
        implements ItemLabUsersDAO {

    /**
     * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
     *
     */
    private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ItemLabUsers.class }));

    /**
     * EntityManager injected by Spring for persistence unit zjcclimsConn
     *
     */
    @PersistenceContext(unitName = "zjcclimsConn")
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Set<Class<?>> getTypes() {
        return dataTypes;
    }

    @Override
    public boolean canBeMerged(ItemLabUsers itemLabUsers) {
        return true;
    }
}