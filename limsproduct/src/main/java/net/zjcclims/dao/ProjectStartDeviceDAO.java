package net.zjcclims.dao;


import net.zjcclims.domain.ProjectStartDevice;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.Set;

/**
 * DAO to manage ProjectStartDevice entities.
 * 
 */
public interface ProjectStartDeviceDAO extends JpaDao<ProjectStartDevice> {

	/**
	 * JPQL Query - findProjectStartDeviceByUnitPrice
	 *
	 */
	public Set<ProjectStartDevice> findProjectStartDeviceByUnitPrice(java.math.BigDecimal unitPrice) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartDeviceByUnitPrice
	 *
	 */
	public Set<ProjectStartDevice> findProjectStartDeviceByUnitPrice(BigDecimal unitPrice, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartDeviceByPurchasePatternContaining
	 *
	 */
	public Set<ProjectStartDevice> findProjectStartDeviceByPurchasePatternContaining(String purchasePattern) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartDeviceByPurchasePatternContaining
	 *
	 */
	public Set<ProjectStartDevice> findProjectStartDeviceByPurchasePatternContaining(String purchasePattern, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectStartDevices
	 *
	 */
	public Set<ProjectStartDevice> findAllProjectStartDevices() throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectStartDevices
	 *
	 */
	public Set<ProjectStartDevice> findAllProjectStartDevices(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartDeviceByAmount
	 *
	 */
	public Set<ProjectStartDevice> findProjectStartDeviceByAmount(java.math.BigDecimal amount) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartDeviceByAmount
	 *
	 */
	public Set<ProjectStartDevice> findProjectStartDeviceByAmount(BigDecimal amount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartDeviceByFormatContaining
	 *
	 */
	public Set<ProjectStartDevice> findProjectStartDeviceByFormatContaining(String format) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartDeviceByFormatContaining
	 *
	 */
	public Set<ProjectStartDevice> findProjectStartDeviceByFormatContaining(String format, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartDeviceById
	 *
	 */
	public ProjectStartDevice findProjectStartDeviceById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartDeviceById
	 *
	 */
	public ProjectStartDevice findProjectStartDeviceById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartDeviceByCollection
	 *
	 */
	public Set<ProjectStartDevice> findProjectStartDeviceByCollection(java.math.BigDecimal collection) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartDeviceByCollection
	 *
	 */
	public Set<ProjectStartDevice> findProjectStartDeviceByCollection(BigDecimal collection, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartDeviceByPurchasePattern
	 *
	 */
	public Set<ProjectStartDevice> findProjectStartDeviceByPurchasePattern(String purchasePattern_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartDeviceByPurchasePattern
	 *
	 */
	public Set<ProjectStartDevice> findProjectStartDeviceByPurchasePattern(String purchasePattern_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartDeviceByEquipmentNameContaining
	 *
	 */
	public Set<ProjectStartDevice> findProjectStartDeviceByEquipmentNameContaining(String equipmentName) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartDeviceByEquipmentNameContaining
	 *
	 */
	public Set<ProjectStartDevice> findProjectStartDeviceByEquipmentNameContaining(String equipmentName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartDeviceByEquipmentName
	 *
	 */
	public Set<ProjectStartDevice> findProjectStartDeviceByEquipmentName(String equipmentName_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartDeviceByEquipmentName
	 *
	 */
	public Set<ProjectStartDevice> findProjectStartDeviceByEquipmentName(String equipmentName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartDeviceByPrimaryKey
	 *
	 */
	public ProjectStartDevice findProjectStartDeviceByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartDeviceByPrimaryKey
	 *
	 */
	public ProjectStartDevice findProjectStartDeviceByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartDeviceByFormat
	 *
	 */
	public Set<ProjectStartDevice> findProjectStartDeviceByFormat(String format_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectStartDeviceByFormat
	 *
	 */
	public Set<ProjectStartDevice> findProjectStartDeviceByFormat(String format_1, int startResult, int maxRows) throws DataAccessException;

}