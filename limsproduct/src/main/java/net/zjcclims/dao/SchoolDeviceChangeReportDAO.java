package net.zjcclims.dao;

import java.math.BigDecimal;

import java.util.Set;

import net.zjcclims.domain.SchoolDeviceChangeReport;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage SchoolDeviceChangeReport entities.
 * 
 */
public interface SchoolDeviceChangeReportDAO extends
		JpaDao<SchoolDeviceChangeReport> {

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberValueLast
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberValueLast(Integer deviceNumberValueLast) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberValueLast
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberValueLast(Integer deviceNumberValueLast, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberReduce
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberReduce(Integer deviceNumberReduce) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberReduce
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberReduce(Integer deviceNumberReduce, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceThis
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceThis(java.math.BigDecimal devicePriceThis) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceThis
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceThis(BigDecimal devicePriceThis, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberThis
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberThis(Integer deviceNumberThis) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberThis
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberThis(Integer deviceNumberThis, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByPrimaryKey
	 *
	 */
	public SchoolDeviceChangeReport findSchoolDeviceChangeReportByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByPrimaryKey
	 *
	 */
	public SchoolDeviceChangeReport findSchoolDeviceChangeReportByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllSchoolDeviceChangeReports
	 *
	 */
	public Set<SchoolDeviceChangeReport> findAllSchoolDeviceChangeReports() throws DataAccessException;

	/**
	 * JPQL Query - findAllSchoolDeviceChangeReports
	 *
	 */
	public Set<SchoolDeviceChangeReport> findAllSchoolDeviceChangeReports(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberAdd
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberAdd(Integer deviceNumberAdd) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberAdd
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberAdd(Integer deviceNumberAdd, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByYearCode
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByYearCode(Integer yearCode) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByYearCode
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByYearCode(Integer yearCode, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberValueThis
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberValueThis(Integer deviceNumberValueThis) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberValueThis
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberValueThis(Integer deviceNumberValueThis, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportById
	 *
	 */
	public SchoolDeviceChangeReport findSchoolDeviceChangeReportById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportById
	 *
	 */
	public SchoolDeviceChangeReport findSchoolDeviceChangeReportById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceReduce
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceReduce(java.math.BigDecimal devicePriceReduce) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceReduce
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceReduce(BigDecimal devicePriceReduce, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberLast
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberLast(Integer deviceNumberLast) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDeviceNumberLast
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDeviceNumberLast(Integer deviceNumberLast, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceLast
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceLast(java.math.BigDecimal devicePriceLast) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceLast
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceLast(BigDecimal devicePriceLast, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceValueThis
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceValueThis(java.math.BigDecimal devicePriceValueThis) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceValueThis
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceValueThis(BigDecimal devicePriceValueThis, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceValueLast
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceValueLast(java.math.BigDecimal devicePriceValueLast) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceValueLast
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceValueLast(BigDecimal devicePriceValueLast, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceAdd
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceAdd(java.math.BigDecimal devicePriceAdd) throws DataAccessException;

	/**
	 * JPQL Query - findSchoolDeviceChangeReportByDevicePriceAdd
	 *
	 */
	public Set<SchoolDeviceChangeReport> findSchoolDeviceChangeReportByDevicePriceAdd(BigDecimal devicePriceAdd, int startResult, int maxRows) throws DataAccessException;

}