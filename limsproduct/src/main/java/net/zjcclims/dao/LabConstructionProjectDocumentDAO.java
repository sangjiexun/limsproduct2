
package net.zjcclims.dao;

import java.util.Set;

import net.zjcclims.domain.LabConstructionProjectDocument;

import org.skyway.spring.util.dao.JpaDao;

import org.springframework.dao.DataAccessException;

/**
 * DAO to manage LabConstructionProjectDocument entities.
 * 
 */
public interface LabConstructionProjectDocumentDAO extends JpaDao<LabConstructionProjectDocument> {

	/**
	 * JPQL Query - findLabConstructionProjectDocumentByEnable
	 *
	 */
	public Set<LabConstructionProjectDocument> findLabConstructionProjectDocumentByEnable(Boolean enable) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionProjectDocumentByEnable
	 *
	 */
	public Set<LabConstructionProjectDocument> findLabConstructionProjectDocumentByEnable(Boolean enable, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionProjectDocumentById
	 *
	 */
	public LabConstructionProjectDocument findLabConstructionProjectDocumentById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionProjectDocumentById
	 *
	 */
	public LabConstructionProjectDocument findLabConstructionProjectDocumentById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionProjectDocumentByPrimaryKey
	 *
	 */
	public LabConstructionProjectDocument findLabConstructionProjectDocumentByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionProjectDocumentByPrimaryKey
	 *
	 */
	public LabConstructionProjectDocument findLabConstructionProjectDocumentByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionProjectDocumentByStage
	 *
	 */
	public Set<LabConstructionProjectDocument> findLabConstructionProjectDocumentByStage(Integer stage) throws DataAccessException;

	/**
	 * JPQL Query - findLabConstructionProjectDocumentByStage
	 *
	 */
	public Set<LabConstructionProjectDocument> findLabConstructionProjectDocumentByStage(Integer stage, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllLabConstructionProjectDocuments
	 *
	 */
	public Set<LabConstructionProjectDocument> findAllLabConstructionProjectDocuments() throws DataAccessException;

	/**
	 * JPQL Query - findAllLabConstructionProjectDocuments
	 *
	 */
	public Set<LabConstructionProjectDocument> findAllLabConstructionProjectDocuments(int startResult, int maxRows) throws DataAccessException;

}