package net.zjcclims.dao;

import net.zjcclims.domain.ChoseUser;
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
 * DAO to manage CmsArticle entities.
 *
 */
@Repository("ChoseUserDAO")
@Transactional
public class ChoseUserDAOImpl extends AbstractJpaDao<ChoseUser> implements
        ChoseUserDAO {

    /**
     * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
     *
     */
    private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ChoseUser.class }));

    /**
     * EntityManager injected by Spring for persistence unit zjcclims
     *
     */
    @PersistenceContext(unitName = "zjcclimsConn")
    private EntityManager entityManager;

    /**
     * Instantiates a new ChoseUserDAOImpl
     *
     */
    public ChoseUserDAOImpl() {
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
     * JPQL Query - findAllChoseUsers
     *
     */
    @Transactional
    public Set<ChoseUser> findAllChoseUsers() throws DataAccessException {

        return findAllChoseUsers(-1, -1);
    }

    /**
     * JPQL Query - findAllChoseUsers
     *
     */

    @SuppressWarnings("unchecked")
    @Transactional
    public Set<ChoseUser> findAllChoseUsers(int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findAllChoseUsers", startResult, maxRows);
        return new LinkedHashSet<ChoseUser>(query.getResultList());
    }
    @Transactional
    public Set<ChoseUser> findChoseUserByUsername(String username) throws DataAccessException {

        return findChoseUserByUsername(username,-1, -1);
    }

    /**
     * JPQL Query - findAllChoseUsers
     *
     */

    @SuppressWarnings("unchecked")
    @Transactional
    public Set<ChoseUser> findChoseUserByUsername(String username, int startResult, int maxRows) throws DataAccessException {
        Query query = createQuery("select cu from ChoseUser cu where cu.username = ?1", startResult, maxRows, username);
        return new LinkedHashSet<ChoseUser>(query.getResultList());
    }

    /**
     * Used to determine whether or not to merge the entity or persist the entity when calling Store
     * @see store
     *
     *
     */
    public boolean canBeMerged(ChoseUser entity) {
        return true;
    }
}
