package net.zjcclims.dao;


import net.zjcclims.domain.ProjectAcceptDevice;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.Set;

/**
 * DAO to manage ProjectAcceptDevice entities.
 * 
 */
public interface ProjectAcceptDeviceDAO extends JpaDao<ProjectAcceptDevice> {

	/**
	 * JPQL Query - findProjectAcceptDeviceByPurchasePattern
	 *
	 */
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByPurchasePattern(String purchasePattern) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptDeviceByPurchasePattern
	 *
	 */
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByPurchasePattern(String purchasePattern, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectAcceptDevices
	 *
	 */
	public Set<ProjectAcceptDevice> findAllProjectAcceptDevices() throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectAcceptDevices
	 *
	 */
	public Set<ProjectAcceptDevice> findAllProjectAcceptDevices(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptDeviceById
	 *
	 */
	public ProjectAcceptDevice findProjectAcceptDeviceById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptDeviceById
	 *
	 */
	public ProjectAcceptDevice findProjectAcceptDeviceById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptDeviceByAmount
	 *
	 */
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByAmount(java.math.BigDecimal amount) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptDeviceByAmount
	 *
	 */
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByAmount(BigDecimal amount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptDeviceByUnitPrice
	 *
	 */
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByUnitPrice(java.math.BigDecimal unitPrice) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptDeviceByUnitPrice
	 *
	 */
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByUnitPrice(BigDecimal unitPrice, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptDeviceByFormat
	 *
	 */
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByFormat(String format) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptDeviceByFormat
	 *
	 */
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByFormat(String format, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptDeviceByEquipmentNameContaining
	 *
	 */
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByEquipmentNameContaining(String equipmentName) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptDeviceByEquipmentNameContaining
	 *
	 */
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByEquipmentNameContaining(String equipmentName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptDeviceByPrimaryKey
	 *
	 */
	public ProjectAcceptDevice findProjectAcceptDeviceByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptDeviceByPrimaryKey
	 *
	 */
	public ProjectAcceptDevice findProjectAcceptDeviceByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptDeviceByFormatContaining
	 *
	 */
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByFormatContaining(String format_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptDeviceByFormatContaining
	 *
	 */
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByFormatContaining(String format_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptDeviceByCollection
	 *
	 */
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByCollection(java.math.BigDecimal collection) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptDeviceByCollection
	 *
	 */
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByCollection(BigDecimal collection, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptDeviceByEquipmentName
	 *
	 */
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByEquipmentName(String equipmentName_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptDeviceByEquipmentName
	 *
	 */
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByEquipmentName(String equipmentName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptDeviceByPurchasePatternContaining
	 *
	 */
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByPurchasePatternContaining(String purchasePattern_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectAcceptDeviceByPurchasePatternContaining
	 *
	 */
	public Set<ProjectAcceptDevice> findProjectAcceptDeviceByPurchasePatternContaining(String purchasePattern_1, int startResult, int maxRows) throws DataAccessException;

}