package net.zjcclims.dao;

import net.zjcclims.domain.ItemAssets;
import org.skyway.spring.util.dao.AbstractJpaDao;
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
 * DAO to manage DeviceRepair entities.
 *
 */
@Repository("ItemAssetsDAO")
@Transactional
public class ItemAssetsDAOImpl extends AbstractJpaDao<ItemAssets>
        implements ItemAssetsDAO {

    /**
     * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
     *
     */
    private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ItemAssets.class }));

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
    public ItemAssets findItemAssetsById(Integer id) {
        return findItemAssetsById(id, -1,  -1);
    }

    @Override
    public ItemAssets findItemAssetsById(Integer id, int startResult, int maxRows) {
        try {
            Query query = createNamedQuery("findItemAssetsById", startResult, maxRows, id);
            return (net.zjcclims.domain.ItemAssets) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public boolean canBeMerged(ItemAssets itemAssets) {
        return true;
    }
}