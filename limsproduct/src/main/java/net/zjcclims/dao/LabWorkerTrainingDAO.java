package net.zjcclims.dao;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Set;

import net.zjcclims.domain.LabWorkerTraining;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage LabWorkerTraining entities.
 * 
 */
public interface LabWorkerTrainingDAO extends JpaDao<LabWorkerTraining> {

	/**
	 * JPQL Query - findLabWorkerTrainingByPrimaryKey
	 *
	 */
	public LabWorkerTraining findLabWorkerTrainingByPrimaryKey(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkerTrainingByPrimaryKey
	 *
	 */
	public LabWorkerTraining findLabWorkerTrainingByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkerTrainingByScore
	 *
	 */
	public Set<LabWorkerTraining> findLabWorkerTrainingByScore(java.math.BigDecimal score) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkerTrainingByScore
	 *
	 */
	public Set<LabWorkerTraining> findLabWorkerTrainingByScore(BigDecimal score, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkerTrainingByBeginTime
	 *
	 */
	public Set<LabWorkerTraining> findLabWorkerTrainingByBeginTime(java.util.Calendar beginTime) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkerTrainingByBeginTime
	 *
	 */
	public Set<LabWorkerTraining> findLabWorkerTrainingByBeginTime(Calendar beginTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkerTrainingByOrganizerContaining
	 *
	 */
	public Set<LabWorkerTraining> findLabWorkerTrainingByOrganizerContaining(String organizer) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkerTrainingByOrganizerContaining
	 *
	 */
	public Set<LabWorkerTraining> findLabWorkerTrainingByOrganizerContaining(String organizer, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkerTrainingByEndTime
	 *
	 */
	public Set<LabWorkerTraining> findLabWorkerTrainingByEndTime(java.util.Calendar endTime) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkerTrainingByEndTime
	 *
	 */
	public Set<LabWorkerTraining> findLabWorkerTrainingByEndTime(Calendar endTime, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkerTrainingByAnnexContaining
	 *
	 */
	public Set<LabWorkerTraining> findLabWorkerTrainingByAnnexContaining(String annex) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkerTrainingByAnnexContaining
	 *
	 */
	public Set<LabWorkerTraining> findLabWorkerTrainingByAnnexContaining(String annex, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkerTrainingByLearnContentContaining
	 *
	 */
	public Set<LabWorkerTraining> findLabWorkerTrainingByLearnContentContaining(String learnContent) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkerTrainingByLearnContentContaining
	 *
	 */
	public Set<LabWorkerTraining> findLabWorkerTrainingByLearnContentContaining(String learnContent, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkerTrainingByAnnex
	 *
	 */
	public Set<LabWorkerTraining> findLabWorkerTrainingByAnnex(String annex_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkerTrainingByAnnex
	 *
	 */
	public Set<LabWorkerTraining> findLabWorkerTrainingByAnnex(String annex_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkerTrainingByOrganizer
	 *
	 */
	public Set<LabWorkerTraining> findLabWorkerTrainingByOrganizer(String organizer_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkerTrainingByOrganizer
	 *
	 */
	public Set<LabWorkerTraining> findLabWorkerTrainingByOrganizer(String organizer_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkerTrainingById
	 *
	 */
	public LabWorkerTraining findLabWorkerTrainingById(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkerTrainingById
	 *
	 */
	public LabWorkerTraining findLabWorkerTrainingById(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkerTrainingByLearnContent
	 *
	 */
	public Set<LabWorkerTraining> findLabWorkerTrainingByLearnContent(String learnContent_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabWorkerTrainingByLearnContent
	 *
	 */
	public Set<LabWorkerTraining> findLabWorkerTrainingByLearnContent(String learnContent_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabWorkerTrainings
	 *
	 */
	public Set<LabWorkerTraining> findAllLabWorkerTrainings() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabWorkerTrainings
	 *
	 */
	public Set<LabWorkerTraining> findAllLabWorkerTrainings(int startResult, int maxRows) throws DataAccessException;

}