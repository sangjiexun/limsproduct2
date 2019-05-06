package net.zjcclims.dao;


import net.zjcclims.domain.EvaluationSource;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * DAO to manage EvaluationSource entities.
 * 
 */
public interface EvaluationSourceDAO extends JpaDao<EvaluationSource> {

	/**
	 * JPQL Query - findEvaluationSourceById
	 *
	 */
	public EvaluationSource findEvaluationSourceById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSourceById
	 *
	 */
	public EvaluationSource findEvaluationSourceById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllEvaluationSources
	 *
	 */
	public Set<EvaluationSource> findAllEvaluationSources() throws DataAccessException;

	/**
	 * JPQL Query - findAllEvaluationSources
	 *
	 */
	public Set<EvaluationSource> findAllEvaluationSources(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSourceByPrimaryKey
	 *
	 */
	public EvaluationSource findEvaluationSourceByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSourceByPrimaryKey
	 *
	 */
	public EvaluationSource findEvaluationSourceByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSourceByTeacherContaining
	 *
	 */
	public Set<EvaluationSource> findEvaluationSourceByTeacherContaining(String teacher) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSourceByTeacherContaining
	 *
	 */
	public Set<EvaluationSource> findEvaluationSourceByTeacherContaining(String teacher, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSourceByTermId
	 *
	 */
	public Set<EvaluationSource> findEvaluationSourceByTermId(Integer termId) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSourceByTermId
	 *
	 */
	public Set<EvaluationSource> findEvaluationSourceByTermId(Integer termId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSourceByTeacher
	 *
	 */
	public Set<EvaluationSource> findEvaluationSourceByTeacher(String teacher_1) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSourceByTeacher
	 *
	 */
	public Set<EvaluationSource> findEvaluationSourceByTeacher(String teacher_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSourceByStudentContaining
	 *
	 */
	public Set<EvaluationSource> findEvaluationSourceByStudentContaining(String student) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSourceByStudentContaining
	 *
	 */
	public Set<EvaluationSource> findEvaluationSourceByStudentContaining(String student, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSourceByStudent
	 *
	 */
	public Set<EvaluationSource> findEvaluationSourceByStudent(String student_1) throws DataAccessException;

	/**
	 * JPQL Query - findEvaluationSourceByStudent
	 *
	 */
	public Set<EvaluationSource> findEvaluationSourceByStudent(String student_1, int startResult, int maxRows) throws DataAccessException;

}