package net.zjcclims.dao;

import net.zjcclims.domain.ChoseProfessorRecord;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage ChoseProfessorRecord entities.
 * 
 */
public interface ChoseProfessorRecordDAO extends JpaDao<ChoseProfessorRecord> {

	/**
	 * JPQL Query - findChoseProfessorRecordByAduitResult
	 *
	 */
	public Set<ChoseProfessorRecord> findChoseProfessorRecordByAduitResult(Integer aduitResult) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorRecordByAduitResult
	 *
	 */
	public Set<ChoseProfessorRecord> findChoseProfessorRecordByAduitResult(Integer aduitResult, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorRecordByPrimaryKey
	 *
	 */
	public ChoseProfessorRecord findChoseProfessorRecordByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorRecordByPrimaryKey
	 *
	 */
	public ChoseProfessorRecord findChoseProfessorRecordByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllChoseProfessorRecords
	 *
	 */
	public Set<ChoseProfessorRecord> findAllChoseProfessorRecords() throws DataAccessException;

	/**
	 * JPQL Query - findAllChoseProfessorRecords
	 *
	 */
	public Set<ChoseProfessorRecord> findAllChoseProfessorRecords(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorRecordById
	 *
	 */
	public ChoseProfessorRecord findChoseProfessorRecordById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorRecordById
	 *
	 */
	public ChoseProfessorRecord findChoseProfessorRecordById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorRecordByCurrAduit
	 *
	 */
	public Set<ChoseProfessorRecord> findChoseProfessorRecordByCurrAduit(Integer currAduit) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorRecordByCurrAduit
	 *
	 */
	public Set<ChoseProfessorRecord> findChoseProfessorRecordByCurrAduit(Integer currAduit, int startResult, int maxRows) throws DataAccessException;

}