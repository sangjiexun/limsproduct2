package net.zjcclims.dao;


import net.zjcclims.domain.ProjectAcceptUser;
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
 * DAO to manage ProjectAcceptUser entities.
 * 
 */
@Repository("ProjectAcceptUserDAO")
@Transactional
public class ProjectAcceptUserDAOImpl extends AbstractJpaDao<ProjectAcceptUser>
		implements ProjectAcceptUserDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { ProjectAcceptUser.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new ProjectAcceptUserDAOImpl
	 *
	 */
	public ProjectAcceptUserDAOImpl() {
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
	 * JPQL Query - findProjectAcceptUserByCnameContaining
	 *
	 */
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserByCnameContaining(String cname) throws DataAccessException {

		return findProjectAcceptUserByCnameContaining(cname, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptUserByCnameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserByCnameContaining(String cname, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptUserByCnameContaining", startResult, maxRows, cname);
		return new LinkedHashSet<ProjectAcceptUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptUserByJobTitleContaining
	 *
	 */
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserByJobTitleContaining(String jobTitle) throws DataAccessException {

		return findProjectAcceptUserByJobTitleContaining(jobTitle, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptUserByJobTitleContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserByJobTitleContaining(String jobTitle, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptUserByJobTitleContaining", startResult, maxRows, jobTitle);
		return new LinkedHashSet<ProjectAcceptUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptUserByPositionContaining
	 *
	 */
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserByPositionContaining(String position) throws DataAccessException {

		return findProjectAcceptUserByPositionContaining(position, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptUserByPositionContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserByPositionContaining(String position, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptUserByPositionContaining", startResult, maxRows, position);
		return new LinkedHashSet<ProjectAcceptUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptUserBySexContaining
	 *
	 */
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserBySexContaining(String sex) throws DataAccessException {

		return findProjectAcceptUserBySexContaining(sex, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptUserBySexContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserBySexContaining(String sex, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptUserBySexContaining", startResult, maxRows, sex);
		return new LinkedHashSet<ProjectAcceptUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptUserByInfo
	 *
	 */
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserByInfo(String info) throws DataAccessException {

		return findProjectAcceptUserByInfo(info, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptUserByInfo
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserByInfo(String info, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptUserByInfo", startResult, maxRows, info);
		return new LinkedHashSet<ProjectAcceptUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptUserById
	 *
	 */
	@Transactional
	public ProjectAcceptUser findProjectAcceptUserById(Integer id) throws DataAccessException {

		return findProjectAcceptUserById(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptUserById
	 *
	 */

	@Transactional
	public ProjectAcceptUser findProjectAcceptUserById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectAcceptUserById", startResult, maxRows, id);
			return (ProjectAcceptUser) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findProjectAcceptUserByResponsibilityContent
	 *
	 */
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserByResponsibilityContent(String responsibilityContent) throws DataAccessException {

		return findProjectAcceptUserByResponsibilityContent(responsibilityContent, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptUserByResponsibilityContent
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserByResponsibilityContent(String responsibilityContent, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptUserByResponsibilityContent", startResult, maxRows, responsibilityContent);
		return new LinkedHashSet<ProjectAcceptUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptUserBySex
	 *
	 */
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserBySex(String sex) throws DataAccessException {

		return findProjectAcceptUserBySex(sex, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptUserBySex
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserBySex(String sex, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptUserBySex", startResult, maxRows, sex);
		return new LinkedHashSet<ProjectAcceptUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptUserByPosition
	 *
	 */
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserByPosition(String position) throws DataAccessException {

		return findProjectAcceptUserByPosition(position, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptUserByPosition
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserByPosition(String position, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptUserByPosition", startResult, maxRows, position);
		return new LinkedHashSet<ProjectAcceptUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptUserByResponsibilityContentContaining
	 *
	 */
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserByResponsibilityContentContaining(String responsibilityContent) throws DataAccessException {

		return findProjectAcceptUserByResponsibilityContentContaining(responsibilityContent, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptUserByResponsibilityContentContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserByResponsibilityContentContaining(String responsibilityContent, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptUserByResponsibilityContentContaining", startResult, maxRows, responsibilityContent);
		return new LinkedHashSet<ProjectAcceptUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptUserByCname
	 *
	 */
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserByCname(String cname) throws DataAccessException {

		return findProjectAcceptUserByCname(cname, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptUserByCname
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserByCname(String cname, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptUserByCname", startResult, maxRows, cname);
		return new LinkedHashSet<ProjectAcceptUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptUserByJobTitle
	 *
	 */
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserByJobTitle(String jobTitle) throws DataAccessException {

		return findProjectAcceptUserByJobTitle(jobTitle, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptUserByJobTitle
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserByJobTitle(String jobTitle, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptUserByJobTitle", startResult, maxRows, jobTitle);
		return new LinkedHashSet<ProjectAcceptUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllProjectAcceptUsers
	 *
	 */
	@Transactional
	public Set<ProjectAcceptUser> findAllProjectAcceptUsers() throws DataAccessException {

		return findAllProjectAcceptUsers(-1, -1);
	}

	/**
	 * JPQL Query - findAllProjectAcceptUsers
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptUser> findAllProjectAcceptUsers(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllProjectAcceptUsers", startResult, maxRows);
		return new LinkedHashSet<ProjectAcceptUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptUserByAge
	 *
	 */
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserByAge(Integer age) throws DataAccessException {

		return findProjectAcceptUserByAge(age, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptUserByAge
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<ProjectAcceptUser> findProjectAcceptUserByAge(Integer age, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findProjectAcceptUserByAge", startResult, maxRows, age);
		return new LinkedHashSet<ProjectAcceptUser>(query.getResultList());
	}

	/**
	 * JPQL Query - findProjectAcceptUserByPrimaryKey
	 *
	 */
	@Transactional
	public ProjectAcceptUser findProjectAcceptUserByPrimaryKey(Integer id) throws DataAccessException {

		return findProjectAcceptUserByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findProjectAcceptUserByPrimaryKey
	 *
	 */

	@Transactional
	public ProjectAcceptUser findProjectAcceptUserByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findProjectAcceptUserByPrimaryKey", startResult, maxRows, id);
			return (ProjectAcceptUser) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(ProjectAcceptUser entity) {
		return true;
	}
}
