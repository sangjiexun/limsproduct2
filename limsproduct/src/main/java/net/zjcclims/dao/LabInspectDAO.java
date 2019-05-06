package net.zjcclims.dao;

import net.zjcclims.domain.LabInspect;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage LabInspect entities.
 *
 */
public interface LabInspectDAO extends JpaDao<LabInspect> {

	/**
	 * JPQL Query - findLabInspectByPrimaryKey
	 *
	 */
	public LabInspect findLabInspectByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectByPrimaryKey
	 *
	 */
	public LabInspect findLabInspectByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabInspects
	 *
	 */
	public Set<LabInspect> findAllLabInspects() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabInspects
	 *
	 */
	public Set<LabInspect> findAllLabInspects(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectByStandardNameContaining
	 *
	 */
	public Set<LabInspect> findLabInspectByStandardNameContaining(String standardName) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectByStandardNameContaining
	 *
	 */
	public Set<LabInspect> findLabInspectByStandardNameContaining(String standardName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectById
	 *
	 */
	public LabInspect findLabInspectById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectById
	 *
	 */
	public LabInspect findLabInspectById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectByStandardName
	 *
	 */
	public Set<LabInspect> findLabInspectByStandardName(String standardName_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabInspectByStandardName
	 *
	 */
	public Set<LabInspect> findLabInspectByStandardName(String standardName_1, int startResult, int maxRows) throws DataAccessException;

}