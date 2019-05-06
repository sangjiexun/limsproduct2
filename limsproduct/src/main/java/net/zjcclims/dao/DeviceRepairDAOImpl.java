package net.zjcclims.dao;

import net.zjcclims.domain.DeviceRepair;
import net.zjcclims.domain.User;
import org.skyway.spring.util.dao.AbstractJpaDao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.*;

/**
 * DAO to manage DeviceRepair entities.
 *
 */
@Repository("DeviceRepairDAO")
@Transactional
public class DeviceRepairDAOImpl extends AbstractJpaDao<DeviceRepair>
        implements DeviceRepairDAO {

    private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { DeviceRepair.class }));

    @PersistenceContext(unitName = "zjcclimsConn")
    private EntityManager entityManager;

    public DeviceRepairDAOImpl() {
        super();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Set<Class<?>> getTypes() {
        return dataTypes;
    }

    @Transactional
    public Set<DeviceRepair> findAllDeviceRepairs() throws DataAccessException {

        return findAllDeviceRepairs(-1, -1);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public Set<DeviceRepair> findAllDeviceRepairs(int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findAllDeviceRepairs", startResult, maxRows);
        return new LinkedHashSet<DeviceRepair>(query.getResultList());
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByDeviceName(String deviceName) throws DataAccessException {

        return findDeviceRepairByDeviceName(deviceName, -1, -1);
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByDeviceName(String deviceName, int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findDeviceRepairByDeviceName", startResult, maxRows, deviceName);
        return new LinkedHashSet<DeviceRepair>(query.getResultList());
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByDeviceNumber(String deviceNumber) throws DataAccessException {

        return findDeviceRepairByDeviceNumber(deviceNumber, -1, -1);
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByDeviceNumber(String deviceNumber, int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findDeviceRepairByDeviceNumber", startResult, maxRows, deviceNumber);
        return new LinkedHashSet<DeviceRepair>(query.getResultList());
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByLabAddress(String labAddress) throws DataAccessException {

        return findDeviceRepairByLabAddress(labAddress, -1, -1);
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByLabAddress(String labAddress, int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findDeviceRepairByLabAddress", startResult, maxRows, labAddress);
        return new LinkedHashSet<DeviceRepair>(query.getResultList());
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByCreater(User creater) throws DataAccessException {

        return findDeviceRepairByCreater(creater, -1, -1);
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByCreater(User creater, int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findDeviceRepairByCreater", startResult, maxRows, creater);
        return new LinkedHashSet<DeviceRepair>(query.getResultList());
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByCreateDate(Calendar createDate) throws DataAccessException {

        return findDeviceRepairByCreateDate(createDate, -1, -1);
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByCreateDate(Calendar createDate, int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findDeviceRepairByCreateDate", startResult, maxRows, createDate);
        return new LinkedHashSet<DeviceRepair>(query.getResultList());
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByContent(String content) throws DataAccessException {

        return findDeviceRepairByContent(content, -1, -1);
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByContent(String content, int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findDeviceRepairByContent", startResult, maxRows, content);
        return new LinkedHashSet<DeviceRepair>(query.getResultList());
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByAuditStage(Integer auditStage) throws DataAccessException {

        return findDeviceRepairByAuditStage(auditStage, -1, -1);
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByAuditStage(Integer auditStage, int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findDeviceRepairByAuditStage", startResult, maxRows, auditStage);
        return new LinkedHashSet<DeviceRepair>(query.getResultList());
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByConfirmUser(User confirmUser) throws DataAccessException {

        return findDeviceRepairByConfirmUser(confirmUser, -1, -1);
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByConfirmUser(User confirmUser, int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findDeviceRepairByConfirmUser", startResult, maxRows, confirmUser);
        return new LinkedHashSet<DeviceRepair>(query.getResultList());
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByConfirmAmount(BigDecimal confirmAccount) throws DataAccessException {

        return findDeviceRepairByConfirmAmount(confirmAccount, -1, -1);
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByConfirmAmount(BigDecimal confirmAccount, int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findDeviceRepairByConfirmAmount", startResult, maxRows, confirmAccount);
        return new LinkedHashSet<DeviceRepair>(query.getResultList());
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByConfirmContent(String confirmContent) throws DataAccessException {

        return findDeviceRepairByConfirmContent(confirmContent, -1, -1);
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByConfirmContent(String confirmContent, int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findDeviceRepairByConfirmContent", startResult, maxRows, confirmContent);
        return new LinkedHashSet<DeviceRepair>(query.getResultList());
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByConfirmDate(Calendar confirmDate) throws DataAccessException {

        return findDeviceRepairByConfirmDate(confirmDate, -1, -1);
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByConfirmDate(Calendar confirmDate, int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findDeviceRepairByConfirmDate", startResult, maxRows, confirmDate);
        return new LinkedHashSet<DeviceRepair>(query.getResultList());
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByType(Integer type) throws DataAccessException {

        return findDeviceRepairByType(type, -1, -1);
    }

    @Override
    public Set<DeviceRepair> findDeviceRepairByType(Integer type, int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findDeviceRepairByType", startResult, maxRows, type);
        return new LinkedHashSet<DeviceRepair>(query.getResultList());
    }

    @Override
    public DeviceRepair findDeviceRepairById(Integer id) throws DataAccessException {

        return findDeviceRepairById(id, -1, -1);
    }

    @Override
    public DeviceRepair findDeviceRepairById(Integer id, int startResult, int maxRows) throws DataAccessException {
        Query query = createNamedQuery("findDeviceRepairById", startResult, maxRows, id);
        return (DeviceRepair) query.getSingleResult();
    }

    public boolean canBeMerged(DeviceRepair entity) {
        return true;
    }
}
