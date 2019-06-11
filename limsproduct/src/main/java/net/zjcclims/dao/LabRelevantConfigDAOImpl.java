
package net.zjcclims.dao;

import net.zjcclims.domain.LabRelevantConfig;
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

@Repository("LabRelevantConfigDAO")
@Transactional
public class LabRelevantConfigDAOImpl extends AbstractJpaDao<LabRelevantConfig> implements
        LabRelevantConfigDAO{
    /**
     * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
     *
     */
    private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { LabRelevantConfig.class }));

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
    public LabRelevantConfigDAOImpl() {
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
    public LabRelevantConfig findLabRelevantConfigBylabRoomIdAndCategory(Integer id,String category) throws DataAccessException{

        return findLabRelevantConfigBylabRoomIdAndCategory(id,category,-1,-1);
    }


    @Transactional
    public LabRelevantConfig findLabRelevantConfigBylabRoomIdAndCategory(Integer id,String category, int startResult, int maxRows) throws DataAccessException{
        try {
            Query query = createNamedQuery("findLabRelevantConfigBylabRoomIdAndCategory", startResult, maxRows, id, category);
            return (net.zjcclims.domain.LabRelevantConfig) query.getSingleResult();
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
    public boolean canBeMerged(LabRelevantConfig entity) {
        return true;
    }

}
