package net.zjcclims.dao;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Set;

import net.zjcclims.domain.Software;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage Software entities.
 * 
 */
public interface SoftwareDAO extends JpaDao<Software> {

	/**
	 * JPQL Query - findSoftwareByNoContaining
	 *
	 */
	public Set<Software> findSoftwareByNoContaining(String no) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByNoContaining
	 *
	 */
	public Set<Software> findSoftwareByNoContaining(String no, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByProperty
	 *
	 */
	public Set<Software> findSoftwareByProperty(String property) throws DataAccessException;
	/**
	 * JPQL Query - findSoftwareByPurchaseDateBefore
	 *
	 */
	public Set<Software> findSoftwareByPurchaseDateBefore(java.util.Calendar purchaseDate) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByPurchaseDateBefore
	 *
	 */
	public Set<Software> findSoftwareByPurchaseDateBefore(Calendar purchaseDate, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByPurchasePersonContaining
	 *
	 */
	public Set<Software> findSoftwareByPurchasePersonContaining(String purchasePerson) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByPurchasePersonContaining
	 *
	 */
	public Set<Software> findSoftwareByPurchasePersonContaining(String purchasePerson, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByPropertyContaining
	 *
	 */
	public Set<Software> findSoftwareByPropertyContaining(String property_1) throws DataAccessException;
	/**
	 * JPQL Query - findSoftwareByPropertyContaining
	 *
	 */
	public Set<Software> findSoftwareByPropertyContaining(String property_1, int startResult, int maxRows) throws DataAccessException;
	/**
	 * JPQL Query - findSoftwareByEditionContaining
	 *
	 */
	public Set<Software> findSoftwareByEditionContaining(String edition) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByEditionContaining
	 *
	 */
	public Set<Software> findSoftwareByEditionContaining(String edition, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByPurchasePerson
	 *
	 */
	public Set<Software> findSoftwareByPurchasePerson(String purchasePerson_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByPurchasePerson
	 *
	 */
	public Set<Software> findSoftwareByPurchasePerson(String purchasePerson_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByPrice
	 *
	 */
	public Set<Software> findSoftwareByPrice(java.math.BigDecimal price) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByPrice
	 *
	 */
	public Set<Software> findSoftwareByPrice(BigDecimal price, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByNo
	 *
	 */
	public Set<Software> findSoftwareByNo(String no_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByNo
	 *
	 */
	public Set<Software> findSoftwareByNo(String no_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByPurchaseDate
	 *
	 */
	public Set<Software> findSoftwareByPurchaseDate(java.util.Calendar purchaseDate_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByPurchaseDate
	 *
	 */
	public Set<Software> findSoftwareByPurchaseDate(Calendar purchaseDate_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareById
	 *
	 */
	public Software findSoftwareById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareById
	 *
	 */
	public Software findSoftwareById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllSoftwares
	 *
	 */
	public Set<Software> findAllSoftwares() throws DataAccessException;

	/**
	 * JPQL Query - findAllSoftwares
	 *
	 */
	public Set<Software> findAllSoftwares(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByNameContaining
	 *
	 */
	public Set<Software> findSoftwareByNameContaining(String name) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByNameContaining
	 *
	 */
	public Set<Software> findSoftwareByNameContaining(String name, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByCode
	 *
	 */
	public Set<Software> findSoftwareByCode(String code) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByCode
	 *
	 */
	public Set<Software> findSoftwareByCode(String code, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByAcademyContaining
	 *
	 */
	public Set<Software> findSoftwareByAcademyContaining(String academy) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByAcademyContaining
	 *
	 */
	public Set<Software> findSoftwareByAcademyContaining(String academy, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByBuyType
	 *
	 */
	public Set<Software> findSoftwareByBuyType(Integer buyType) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByBuyType
	 *
	 */
	public Set<Software> findSoftwareByBuyType(Integer buyType, int startResult, int maxRows) throws DataAccessException;
	/**
	 * JPQL Query - findSoftwareByAcademy
	 *
	 */
	public Set<Software> findSoftwareByAcademy(String academy_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByAcademy
	 *
	 */
	public Set<Software> findSoftwareByAcademy(String academy_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByEdition
	 *
	 */
	public Set<Software> findSoftwareByEdition(String edition_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByEdition
	 *
	 */
	public Set<Software> findSoftwareByEdition(String edition_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByPurchaseDateAfter
	 *
	 */
	public Set<Software> findSoftwareByPurchaseDateAfter(java.util.Calendar purchaseDate_2) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByPurchaseDateAfter
	 *
	 */
	public Set<Software> findSoftwareByPurchaseDateAfter(Calendar purchaseDate_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByName
	 *
	 */
	public Set<Software> findSoftwareByName(String name_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByName
	 *
	 */
	public Set<Software> findSoftwareByName(String name_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByPrimaryKey
	 *
	 */
	public Software findSoftwareByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByPrimaryKey
	 *
	 */
	public Software findSoftwareByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByCodeContaining
	 *
	 */
	public Set<Software> findSoftwareByCodeContaining(String code_1) throws DataAccessException;

	/**
	 * JPQL Query - findSoftwareByCodeContaining
	 *
	 */
	public Set<Software> findSoftwareByCodeContaining(String code_1, int startResult, int maxRows) throws DataAccessException;

}