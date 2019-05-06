
package net.zjcclims.dao;

import net.zjcclims.domain.AuditSerialNumber;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage AuditSerialNumber entities.
 * 
 */
public interface AuditSerialNumberDAO extends JpaDao<AuditSerialNumber> {

	/**
	 * JPQL Query - findAuditSerialNumberByUuid
	 *
	 */
	public AuditSerialNumber findAuditSerialNumberByUuid(String uuid) throws DataAccessException;

	/**
	 * JPQL Query - findAuditSerialNumberByUuid
	 *
	 */
	public AuditSerialNumber findAuditSerialNumberByUuid(String uuid, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAuditSerialNumberByBusinessType
	 *
	 */
	public Set<AuditSerialNumber> findAuditSerialNumberByBusinessType(String businessType) throws DataAccessException;

	/**
	 * JPQL Query - findAuditSerialNumberByBusinessType
	 *
	 */
	public Set<AuditSerialNumber> findAuditSerialNumberByBusinessType(String businessType, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAuditSerialNumberByBusinessId
	 *
	 */
	public Set<AuditSerialNumber> findAuditSerialNumberByBusinessId(String businessId) throws DataAccessException;

	/**
	 * JPQL Query - findAuditSerialNumberByBusinessId
	 *
	 */
	public Set<AuditSerialNumber> findAuditSerialNumberByBusinessId(String businessId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAuditSerialNumberByPrimaryKey
	 *
	 */
	public AuditSerialNumber findAuditSerialNumberByPrimaryKey(String uuid_1) throws DataAccessException;

	/**
	 * JPQL Query - findAuditSerialNumberByPrimaryKey
	 *
	 */
	public AuditSerialNumber findAuditSerialNumberByPrimaryKey(String uuid_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAuditSerialNumberByUuidContaining
	 *
	 */
	public Set<AuditSerialNumber> findAuditSerialNumberByUuidContaining(String uuid_2) throws DataAccessException;

	/**
	 * JPQL Query - findAuditSerialNumberByUuidContaining
	 *
	 */
	public Set<AuditSerialNumber> findAuditSerialNumberByUuidContaining(String uuid_2, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAuditSerialNumberByBusinessTypeContaining
	 *
	 */
	public Set<AuditSerialNumber> findAuditSerialNumberByBusinessTypeContaining(String businessType_1) throws DataAccessException;

	/**
	 * JPQL Query - findAuditSerialNumberByBusinessTypeContaining
	 *
	 */
	public Set<AuditSerialNumber> findAuditSerialNumberByBusinessTypeContaining(String businessType_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAuditSerialNumberByEnable
	 *
	 */
	public Set<AuditSerialNumber> findAuditSerialNumberByEnable(Boolean enable) throws DataAccessException;

	/**
	 * JPQL Query - findAuditSerialNumberByEnable
	 *
	 */
	public Set<AuditSerialNumber> findAuditSerialNumberByEnable(Boolean enable, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllAuditSerialNumbers
	 *
	 */
	public Set<AuditSerialNumber> findAllAuditSerialNumbers() throws DataAccessException;

	/**
	 * JPQL Query - findAllAuditSerialNumbers
	 *
	 */
	public Set<AuditSerialNumber> findAllAuditSerialNumbers(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAuditSerialNumberByBusinessIdContaining
	 *
	 */
	public Set<AuditSerialNumber> findAuditSerialNumberByBusinessIdContaining(String businessId_1) throws DataAccessException;

	/**
	 * JPQL Query - findAuditSerialNumberByBusinessIdContaining
	 *
	 */
	public Set<AuditSerialNumber> findAuditSerialNumberByBusinessIdContaining(String businessId_1, int startResult, int maxRows) throws DataAccessException;

}