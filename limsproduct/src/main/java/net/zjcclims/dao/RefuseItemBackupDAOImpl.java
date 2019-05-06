package net.zjcclims.dao;

import net.zjcclims.domain.RefuseItemBackup;
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

@Repository("RefuseItemBackupDAO")
@Transactional
public class RefuseItemBackupDAOImpl extends AbstractJpaDao<RefuseItemBackup> implements RefuseItemBackupDAO {

    /**
     * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
     *
     */
    private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { RefuseItemBackup.class }));

    /**
     * EntityManager injected by Spring for persistence unit zjcclimsConn
     *
     */
    @PersistenceContext(unitName = "zjcclimsConn")
    private EntityManager entityManager;
    /**
     * The {@link EntityManager} which is used by all query manipulation and execution in this DAO.
     *
     * @return the {@link EntityManager}
     */
    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Set<Class<?>> getTypes() {
        return dataTypes;
    }

    /**
     * JPQL Query - findRefuseItemBackupByQuantity
     *
     */
    @Transactional
    public Set<RefuseItemBackup> findAllRefuseItemBackups() throws DataAccessException {

        return findAllRefuseItemBackups(-1, -1);
    }

    /**
     * JPQL Query - findRefuseItemBackupByQuantity
     *
     */

    @SuppressWarnings("unchecked")
    @Transactional
    public Set<RefuseItemBackup> findAllRefuseItemBackups(int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findAllRefuseItemBackups", startResult, maxRows);
        return new LinkedHashSet<RefuseItemBackup>(query.getResultList());
    }

    /**
     * JPQL Query - findRefuseItemBackupByPrimaryKey
     *
     */
    @Transactional
    public RefuseItemBackup findRefuseItemBackupById(Integer id) throws DataAccessException {
        return findRefuseItemBackupById(id, -1, -1);
    }

    /**
     * JPQL Query - findRefuseItemBackupByPrimaryKey
     *
     */

    @Transactional
    public RefuseItemBackup findRefuseItemBackupById(Integer id, int startResult, int maxRows) throws DataAccessException {
        try {
            Query query = createNamedQuery("findRefuseItemBackupById", startResult, maxRows, id);
            return (net.zjcclims.domain.RefuseItemBackup) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * JPQL Query - findRefuseItemBackupByPrimaryKey
     *
     */
    @Transactional
    public RefuseItemBackup findRefuseItemBackupByPrimaryKey(Integer id) throws DataAccessException {
        return findRefuseItemBackupByPrimaryKey(id, -1, -1);
    }

    /**
     * JPQL Query - findRefuseItemBackupByPrimaryKey
     *
     */

    @Transactional
    public RefuseItemBackup findRefuseItemBackupByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
        try {
            Query query = createNamedQuery("findRefuseItemBackupByPrimaryKey", startResult, maxRows, id);
            return (net.zjcclims.domain.RefuseItemBackup) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * JPQL Query - findAuditRefuseBackupByQuantity
     *
     */
    @Transactional
    public Set<RefuseItemBackup> findRefuseItemBackupByBusinessId(String businessId) throws DataAccessException {

        return findRefuseItemBackupByBusinessId(businessId, -1, -1);
    }

    /**
     * JPQL Query - findRefuseItemBackupByQuantity
     *
     */

    @SuppressWarnings("unchecked")
    @Transactional
    public Set<RefuseItemBackup> findRefuseItemBackupByBusinessId(String businessId, int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findRefuseItemBackupByBusinessId", startResult, maxRows, businessId);
        return new LinkedHashSet<RefuseItemBackup>(query.getResultList());
    }

    /**
     * JPQL Query - findAuditRefuseBackupByQuantity
     *
     */
    @Transactional
    public Set<RefuseItemBackup> findRefuseItemBackupByType(String type) throws DataAccessException {

        return findRefuseItemBackupByType(type, -1, -1);
    }

    /**
     * JPQL Query - findRefuseItemBackupByQuantity
     *
     */

    @SuppressWarnings("unchecked")
    @Transactional
    public Set<RefuseItemBackup> findRefuseItemBackupByType(String type, int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findRefuseItemBackupByType", startResult, maxRows, type);
        return new LinkedHashSet<RefuseItemBackup>(query.getResultList());
    }

    /**
     * JPQL Query - findAuditRefuseBackupByQuantity
     *
     */
    @Transactional
    public Set<RefuseItemBackup> findRefuseItemBackupByTerm(Integer term) throws DataAccessException {

        return findRefuseItemBackupByTerm(term, -1, -1);
    }

    /**
     * JPQL Query - findRefuseItemBackupByQuantity
     *
     */

    @SuppressWarnings("unchecked")
    @Transactional
    public Set<RefuseItemBackup> findRefuseItemBackupByTerm(Integer term, int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findRefuseItemBackupByTerm", startResult, maxRows, term);
        return new LinkedHashSet<RefuseItemBackup>(query.getResultList());
    }

    /**
     * Each DAO can decide whether the object passed can be merged using the .merge() method
     * Generally speaking, Objects whos primary keys are auto generated must be passed to the persist method
     * in order to have their primary keys fields filled in.
     *
     * @param o
     * @return
     */
    @Override
    public boolean canBeMerged(RefuseItemBackup o) {
        return true;
    }
}
