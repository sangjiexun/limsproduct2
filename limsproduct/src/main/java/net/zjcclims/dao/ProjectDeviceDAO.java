package net.zjcclims.dao;


import net.zjcclims.domain.ProjectDevice;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.Set;

/**
 * DAO to manage ProjectDevice entities.
 * 
 */
public interface ProjectDeviceDAO extends JpaDao<ProjectDevice> {

	/**
	 * JPQL Query - findAllProjectDevices
	 *
	 */
	public Set<ProjectDevice> findAllProjectDevices() throws DataAccessException;

	/**
	 * JPQL Query - findAllProjectDevices
	 *
	 */
	public Set<ProjectDevice> findAllProjectDevices(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceByFormat
	 *
	 */
	public Set<ProjectDevice> findProjectDeviceByFormat(String format) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceByFormat
	 *
	 */
	public Set<ProjectDevice> findProjectDeviceByFormat(String format, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceByEquipmentNameContaining
	 *
	 */
	public Set<ProjectDevice> findProjectDeviceByEquipmentNameContaining(String equipmentName) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceByEquipmentNameContaining
	 *
	 */
	public Set<ProjectDevice> findProjectDeviceByEquipmentNameContaining(String equipmentName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceByPurchasePatternContaining
	 *
	 */
	public Set<ProjectDevice> findProjectDeviceByPurchasePatternContaining(String purchasePattern) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceByPurchasePatternContaining
	 *
	 */
	public Set<ProjectDevice> findProjectDeviceByPurchasePatternContaining(String purchasePattern, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceByPrimaryKey
	 *
	 */
	public ProjectDevice findProjectDeviceByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceByPrimaryKey
	 *
	 */
	public ProjectDevice findProjectDeviceByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceByFormatContaining
	 *
	 */
	public Set<ProjectDevice> findProjectDeviceByFormatContaining(String format_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceByFormatContaining
	 *
	 */
	public Set<ProjectDevice> findProjectDeviceByFormatContaining(String format_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceByUnitPrice
	 *
	 */
	public Set<ProjectDevice> findProjectDeviceByUnitPrice(java.math.BigDecimal unitPrice) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceByUnitPrice
	 *
	 */
	public Set<ProjectDevice> findProjectDeviceByUnitPrice(BigDecimal unitPrice, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceByAmount
	 *
	 */
	public Set<ProjectDevice> findProjectDeviceByAmount(java.math.BigDecimal amount) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceByAmount
	 *
	 */
	public Set<ProjectDevice> findProjectDeviceByAmount(BigDecimal amount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceByPurchasePattern
	 *
	 */
	public Set<ProjectDevice> findProjectDeviceByPurchasePattern(String purchasePattern_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceByPurchasePattern
	 *
	 */
	public Set<ProjectDevice> findProjectDeviceByPurchasePattern(String purchasePattern_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceByCollection
	 *
	 */
	public Set<ProjectDevice> findProjectDeviceByCollection(java.math.BigDecimal collection) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceByCollection
	 *
	 */
	public Set<ProjectDevice> findProjectDeviceByCollection(BigDecimal collection, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceById
	 *
	 */
	public ProjectDevice findProjectDeviceById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceById
	 *
	 */
	public ProjectDevice findProjectDeviceById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceByEquipmentName
	 *
	 */
	public Set<ProjectDevice> findProjectDeviceByEquipmentName(String equipmentName_1) throws DataAccessException;

	/**
	 * JPQL Query - findProjectDeviceByEquipmentName
	 *
	 */
	public Set<ProjectDevice> findProjectDeviceByEquipmentName(String equipmentName_1, int startResult, int maxRows) throws DataAccessException;

}