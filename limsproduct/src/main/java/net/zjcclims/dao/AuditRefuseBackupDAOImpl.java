package net.zjcclims.dao;

import net.zjcclims.domain.AuditRefuseBackup;
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

@Repository("AuditRefuseBackupDAO")
@Transactional
public class AuditRefuseBackupDAOImpl extends AbstractJpaDao<AuditRefuseBackup> implements AuditRefuseBackupDAO {

    /**
     * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
     *
     */
    private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { AuditRefuseBackup.class }));

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
     * JPQL Query - findAuditRefuseBackupByQuantity
     *
     */
    @Transactional
    public Set<AuditRefuseBackup> findAllAuditRefuseBackups() throws DataAccessException {

        return findAllAuditRefuseBackups(-1, -1);
    }

    /**
     * JPQL Query - findAuditRefuseBackupByQuantity
     *
     */

    @SuppressWarnings("unchecked")
    @Transactional
    public Set<AuditRefuseBackup> findAllAuditRefuseBackups(int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findAllAuditRefuseBackups", startResult, maxRows);
        return new LinkedHashSet<AuditRefuseBackup>(query.getResultList());
    }

    /**
     * JPQL Query - findAuditRefuseBackupByPrimaryKey
     *
     */
    @Transactional
    public AuditRefuseBackup findAuditRefuseBackupById(Integer id) throws DataAccessException {
        return findAuditRefuseBackupById(id, -1, -1);
    }

    /**
     * JPQL Query - findAuditRefuseBackupByPrimaryKey
     *
     */

    @Transactional
    public AuditRefuseBackup findAuditRefuseBackupById(Integer id, int startResult, int maxRows) throws DataAccessException {
        try {
            Query query = createNamedQuery("findAuditRefuseBackupById", startResult, maxRows, id);
            return (net.zjcclims.domain.AuditRefuseBackup) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * JPQL Query - findAuditRefuseBackupByPrimaryKey
     *
     */
    @Transactional
    public AuditRefuseBackup findAuditRefuseBackupByPrimaryKey(Integer id) throws DataAccessException {
        return findAuditRefuseBackupByPrimaryKey(id, -1, -1);
    }

    /**
     * JPQL Query - findAuditRefuseBackupByPrimaryKey
     *
     */

    @Transactional
    public AuditRefuseBackup findAuditRefuseBackupByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
        try {
            Query query = createNamedQuery("findAuditRefuseBackupByPrimaryKey", startResult, maxRows, id);
            return (net.zjcclims.domain.AuditRefuseBackup) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * JPQL Query - findAuditRefuseBackupByQuantity
     *
     */
    @Transactional
    public Set<AuditRefuseBackup> findAuditRefuseBackupByAuditInfo(String auditInfo) throws DataAccessException {

        return findAuditRefuseBackupByAuditInfo(auditInfo, -1, -1);
    }

    /**
     * JPQL Query - findAuditRefuseBackupByQuantity
     *
     */

    @SuppressWarnings("unchecked")
    @Transactional
    public Set<AuditRefuseBackup> findAuditRefuseBackupByAuditInfo(String auditInfo, int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findAuditRefuseBackupByAuditInfo", startResult, maxRows, auditInfo);
        return new LinkedHashSet<AuditRefuseBackup>(query.getResultList());
    }

    /**
     * JPQL Query - findAuditRefuseBackupByQuantity
     *
     */
    @Transactional
    public Set<AuditRefuseBackup> findAuditRefuseBackupByAuditInfoContaining(String auditInfo) throws DataAccessException {

        return findAuditRefuseBackupByAuditInfoContaining(auditInfo, -1, -1);
    }

    /**
     * JPQL Query - findAuditRefuseBackupByQuantity
     *
     */

    @SuppressWarnings("unchecked")
    @Transactional
    public Set<AuditRefuseBackup> findAuditRefuseBackupByAuditInfoContaining(String auditInfo, int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findAuditRefuseBackupByAuditInfoContaining", startResult, maxRows, auditInfo);
        return new LinkedHashSet<AuditRefuseBackup>(query.getResultList());
    }

    /**
     * JPQL Query - findAuditRefuseBackupByQuantity
     *
     */
    @Transactional
    public Set<AuditRefuseBackup> findAuditRefuseBackupByAuditContent(String auditContent) throws DataAccessException {

        return findAuditRefuseBackupByAuditContent(auditContent, -1, -1);
    }

    /**
     * JPQL Query - findAuditRefuseBackupByQuantity
     *
     */

    @SuppressWarnings("unchecked")
    @Transactional
    public Set<AuditRefuseBackup> findAuditRefuseBackupByAuditContent(String auditContent, int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findAuditRefuseBackupByAuditContent", startResult, maxRows, auditContent);
        return new LinkedHashSet<AuditRefuseBackup>(query.getResultList());
    }

    /**
     * JPQL Query - findAuditRefuseBackupByQuantity
     *
     */
    @Transactional
    public Set<AuditRefuseBackup> findAuditRefuseBackupByAuditContentContaining(String auditContent) throws DataAccessException {

        return findAuditRefuseBackupByAuditContentContaining(auditContent, -1, -1);
    }

    /**
     * JPQL Query - findAuditRefuseBackupByQuantity
     *
     */

    @SuppressWarnings("unchecked")
    @Transactional
    public Set<AuditRefuseBackup> findAuditRefuseBackupByAuditContentContaining(String auditContent, int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findAuditRefuseBackupByAuditContentContaining", startResult, maxRows, auditContent);
        return new LinkedHashSet<AuditRefuseBackup>(query.getResultList());
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
    public boolean canBeMerged(AuditRefuseBackup o) {
        return true;
    }
}
