package net.zjcclims.dao;

import net.zjcclims.domain.ChoseDissertationRecord;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage ChoseDissertationRecord entities.
 * 
 */
public interface ChoseDissertationRecordDAO extends
        JpaDao<ChoseDissertationRecord> {

	/**
	 * JPQL Query - findChoseDissertationRecordByAduitResult
	 *
	 */
	public Set<ChoseDissertationRecord> findChoseDissertationRecordByAduitResult(Integer aduitResult) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationRecordByAduitResult
	 *
	 */
	public Set<ChoseDissertationRecord> findChoseDissertationRecordByAduitResult(Integer aduitResult, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationRecordById
	 *
	 */
	public ChoseDissertationRecord findChoseDissertationRecordById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationRecordById
	 *
	 */
	public ChoseDissertationRecord findChoseDissertationRecordById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationRecordByPrimaryKey
	 *
	 */
	public ChoseDissertationRecord findChoseDissertationRecordByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationRecordByPrimaryKey
	 *
	 */
	public ChoseDissertationRecord findChoseDissertationRecordByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllChoseDissertationRecords
	 *
	 */
	public Set<ChoseDissertationRecord> findAllChoseDissertationRecords() throws DataAccessException;

	/**
	 * JPQL Query - findAllChoseDissertationRecords
	 *
	 */
	public Set<ChoseDissertationRecord> findAllChoseDissertationRecords(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationRecordByCurrAduit
	 *
	 */
	public Set<ChoseDissertationRecord> findChoseDissertationRecordByCurrAduit(Integer currAduit) throws DataAccessException;

	/**
	 * JPQL Query - findChoseDissertationRecordByCurrAduit
	 *
	 */
	public Set<ChoseDissertationRecord> findChoseDissertationRecordByCurrAduit(Integer currAduit, int startResult, int maxRows) throws DataAccessException;

}