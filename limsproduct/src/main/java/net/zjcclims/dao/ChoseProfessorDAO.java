package net.zjcclims.dao;

import net.zjcclims.domain.ChoseProfessor;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage ChoseProfessor entities.
 * 
 */
public interface ChoseProfessorDAO extends JpaDao<ChoseProfessor> {

	/**
	 * JPQL Query - findChoseProfessorByType
	 *
	 */
	public Set<ChoseProfessor> findChoseProfessorByType(Integer type) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorByType
	 *
	 */
	public Set<ChoseProfessor> findChoseProfessorByType(Integer type, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorByFinalNumber
	 *
	 */
	public Set<ChoseProfessor> findChoseProfessorByFinalNumber(Integer finalNumber) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorByFinalNumber
	 *
	 */
	public Set<ChoseProfessor> findChoseProfessorByFinalNumber(Integer finalNumber, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorById
	 *
	 */
	public ChoseProfessor findChoseProfessorById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorById
	 *
	 */
	public ChoseProfessor findChoseProfessorById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorByPrimaryKey
	 *
	 */
	public ChoseProfessor findChoseProfessorByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorByPrimaryKey
	 *
	 */
	public ChoseProfessor findChoseProfessorByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllChoseProfessors
	 *
	 */
	public Set<ChoseProfessor> findAllChoseProfessors() throws DataAccessException;

	/**
	 * JPQL Query - findAllChoseProfessors
	 *
	 */
	public Set<ChoseProfessor> findAllChoseProfessors(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorByExceptNumber
	 *
	 */
	public Set<ChoseProfessor> findChoseProfessorByExceptNumber(Integer exceptNumber) throws DataAccessException;

	/**
	 * JPQL Query - findChoseProfessorByExceptNumber
	 *
	 */
	public Set<ChoseProfessor> findChoseProfessorByExceptNumber(Integer exceptNumber, int startResult, int maxRows) throws DataAccessException;

}