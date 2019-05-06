package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.SystemPreDay;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage SystemPreDay entities.
 * 
 */
public interface SystemPreDayDAO extends JpaDao<SystemPreDay> {

	/**
	 * JPQL Query - findSystemPreDayByDayNum
	 *
	 */
	public Set<SystemPreDay> findSystemPreDayByDayNum(Integer dayNum) throws DataAccessException;

	/**
	 * JPQL Query - findSystemPreDayByDayNum
	 *
	 */
	public Set<SystemPreDay> findSystemPreDayByDayNum(Integer dayNum, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSystemPreDayById
	 *
	 */
	public SystemPreDay findSystemPreDayById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findSystemPreDayById
	 *
	 */
	public SystemPreDay findSystemPreDayById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllSystemPreDays
	 *
	 */
	public Set<SystemPreDay> findAllSystemPreDays() throws DataAccessException;

	/**
	 * JPQL Query - findAllSystemPreDays
	 *
	 */
	public Set<SystemPreDay> findAllSystemPreDays(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findSystemPreDayByPrimaryKey
	 *
	 */
	public SystemPreDay findSystemPreDayByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findSystemPreDayByPrimaryKey
	 *
	 */
	public SystemPreDay findSystemPreDayByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

}