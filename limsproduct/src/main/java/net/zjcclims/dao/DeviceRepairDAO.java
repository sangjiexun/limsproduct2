package net.zjcclims.dao;

import net.zjcclims.domain.DeviceRepair;
import net.zjcclims.domain.User;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;

/**
 * DAO to manage DeviceRepair entities.
 *
 */
public interface DeviceRepairDAO extends JpaDao<DeviceRepair> {

    public Set<DeviceRepair> findAllDeviceRepairs() throws DataAccessException;

    public Set<DeviceRepair> findAllDeviceRepairs(int startResult, int maxRows) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByDeviceName(String deviceName) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByDeviceName(String deviceName, int startResult, int maxRows) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByDeviceNumber(String deviceNumber) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByDeviceNumber(String deviceNumber, int startResult, int maxRows) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByLabAddress(String labAddress) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByLabAddress(String labAddress, int startResult, int maxRows) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByCreater(User creater) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByCreater(User creater, int startResult, int maxRows) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByCreateDate(java.util.Calendar createDate) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByCreateDate(Calendar createDate, int startResult, int maxRows) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByContent(String content) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByContent(String content, int startResult, int maxRows) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByAuditStage(Integer auditStage) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByAuditStage(Integer auditStage, int startResult, int maxRows) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByConfirmUser(User confirmUser) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByConfirmUser(User confirmUser, int startResult, int maxRows) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByConfirmAmount(BigDecimal confirmAccount) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByConfirmAmount(BigDecimal confirmAccount, int startResult, int maxRows) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByConfirmContent(String confirmContent) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByConfirmContent(String confirmContent, int startResult, int maxRows) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByConfirmDate(java.util.Calendar confirmDate) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByConfirmDate(Calendar confirmDate, int startResult, int maxRows) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByType(Integer type) throws DataAccessException;

    public Set<DeviceRepair> findDeviceRepairByType(Integer type, int startResult, int maxRows) throws DataAccessException;

    public DeviceRepair findDeviceRepairById(Integer id) throws DataAccessException;

    public DeviceRepair findDeviceRepairById(Integer id, int startResult, int maxRows) throws DataAccessException;

}