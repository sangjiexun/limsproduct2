package net.zjcclims.dao;

import net.zjcclims.domain.ItemOpenTeacher;
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
@Repository("ItemOpenTeacherDAO")
@Transactional
public class ItemOpenTeacherDAOImpl extends AbstractJpaDao<ItemOpenTeacher>
        implements ItemOpenTeacherDAO {

    /**
     * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
     *
     */
    private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ItemOpenTeacher.class }));

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
    public boolean canBeMerged(ItemOpenTeacher itemOpenTeacher) {
        return true;
    }
}