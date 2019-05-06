package net.zjcclims.dao;


import net.zjcclims.domain.ProjectStartUser;
import org.skyway.spring.util.dao.AbstractJpaDao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * DAO to manage ProjectStartUser entities.
 * 
 */
@Repository("ProjectStartUserDAO")
@Transactional
public class ProjectStartUserDAOImpl extends AbstractJpaDao<ProjectStartUser>
		implements ProjectStartUserDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ProjectStartUser.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ProjectStartUserDAOImpl
	 *
	 */
	public ProjectStartUserDAOImpl() {
		super();
	}

	/**
	 * Get the entity manager that manages persistence unit 
	 *
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Returns the set of entity classes managed by this DAO.
	 *
	 */
	public Set<Class<?>> getTypes() {
		return dataTypes;
	}

	/**
	 * JPQL Query - findProjectStartUserByInfo
	 *
	 */
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserByInfo(String info) throws DataAccessException {

		return findProjectStartUserByInfo(info, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartUserByInfo
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserByInfo(String info, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartUserByInfo", startResult, maxRows, info);
		return new LinkedHashSet<ProjectStartUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartUserByPrimaryKey
	 *
	 */
	@Transactional
	public ProjectStartUser findProjectStartUserByPrimaryKey(Integer id) throws DataAccessException {

		return findProjectStartUserByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartUserByPrimaryKey
	 *
	 */

	@Transactional
	public ProjectStartUser findProjectStartUserByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectStartUserByPrimaryKey", startResult, maxRows, id);
			return (ProjectStartUser) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectStartUserBySex
	 *
	 */
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserBySex(String sex) throws DataAccessException {

		return findProjectStartUserBySex(sex, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartUserBySex
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserBySex(String sex, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartUserBySex", startResult, maxRows, sex);
		return new LinkedHashSet<ProjectStartUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartUserByResponsibilityContent
	 *
	 */
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserByResponsibilityContent(String responsibilityContent) throws DataAccessException {

		return findProjectStartUserByResponsibilityContent(responsibilityContent, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartUserByResponsibilityContent
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserByResponsibilityContent(String responsibilityContent, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartUserByResponsibilityContent", startResult, maxRows, responsibilityContent);
		return new LinkedHashSet<ProjectStartUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartUserByCnameContaining
	 *
	 */
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserByCnameContaining(String cname) throws DataAccessException {

		return findProjectStartUserByCnameContaining(cname, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartUserByCnameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserByCnameContaining(String cname, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartUserByCnameContaining", startResult, maxRows, cname);
		return new LinkedHashSet<ProjectStartUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartUserByPositionContaining
	 *
	 */
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserByPositionContaining(String position) throws DataAccessException {

		return findProjectStartUserByPositionContaining(position, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartUserByPositionContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserByPositionContaining(String position, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartUserByPositionContaining", startResult, maxRows, position);
		return new LinkedHashSet<ProjectStartUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartUserByCname
	 *
	 */
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserByCname(String cname) throws DataAccessException {

		return findProjectStartUserByCname(cname, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartUserByCname
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserByCname(String cname, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartUserByCname", startResult, maxRows, cname);
		return new LinkedHashSet<ProjectStartUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartUserBySexContaining
	 *
	 */
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserBySexContaining(String sex) throws DataAccessException {

		return findProjectStartUserBySexContaining(sex, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartUserBySexContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserBySexContaining(String sex, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartUserBySexContaining", startResult, maxRows, sex);
		return new LinkedHashSet<ProjectStartUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartUserByJobTitle
	 *
	 */
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserByJobTitle(String jobTitle) throws DataAccessException {

		return findProjectStartUserByJobTitle(jobTitle, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartUserByJobTitle
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserByJobTitle(String jobTitle, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartUserByJobTitle", startResult, maxRows, jobTitle);
		return new LinkedHashSet<ProjectStartUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllProjectStartUsers
	 *
	 */
	@Transactional
	public Set<ProjectStartUser> findAllProjectStartUsers() throws DataAccessException {

		return findAllProjectStartUsers(-1, -1);
	}

	/**
	 * JPQL Query - findAllProjectStartUsers
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartUser> findAllProjectStartUsers(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllProjectStartUsers", startResult, maxRows);
		return new LinkedHashSet<ProjectStartUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartUserByResponsibilityContentContaining
	 *
	 */
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserByResponsibilityContentContaining(String responsibilityContent) throws DataAccessException {

		return findProjectStartUserByResponsibilityContentContaining(responsibilityContent, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartUserByResponsibilityContentContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserByResponsibilityContentContaining(String responsibilityContent, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartUserByResponsibilityContentContaining", startResult, maxRows, responsibilityContent);
		return new LinkedHashSet<ProjectStartUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartUserById
	 *
	 */
	@Transactional
	public ProjectStartUser findProjectStartUserById(Integer id) throws DataAccessException {

		return findProjectStartUserById(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartUserById
	 *
	 */

	@Transactional
	public ProjectStartUser findProjectStartUserById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectStartUserById", startResult, maxRows, id);
			return (ProjectStartUser) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectStartUserByPosition
	 *
	 */
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserByPosition(String position) throws DataAccessException {

		return findProjectStartUserByPosition(position, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartUserByPosition
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserByPosition(String position, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartUserByPosition", startResult, maxRows, position);
		return new LinkedHashSet<ProjectStartUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartUserByJobTitleContaining
	 *
	 */
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserByJobTitleContaining(String jobTitle) throws DataAccessException {

		return findProjectStartUserByJobTitleContaining(jobTitle, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartUserByJobTitleContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserByJobTitleContaining(String jobTitle, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartUserByJobTitleContaining", startResult, maxRows, jobTitle);
		return new LinkedHashSet<ProjectStartUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectStartUserByAge
	 *
	 */
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserByAge(Integer age) throws DataAccessException {

		return findProjectStartUserByAge(age, -1, -1);
	}

	/**
	 * JPQL Query - findProjectStartUserByAge
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectStartUser> findProjectStartUserByAge(Integer age, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectStartUserByAge", startResult, maxRows, age);
		return new LinkedHashSet<ProjectStartUser>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(ProjectStartUser entity) {
		return true;
	}
}
