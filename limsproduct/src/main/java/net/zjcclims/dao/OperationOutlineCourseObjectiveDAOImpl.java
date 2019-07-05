
package net.zjcclims.dao;

import net.gvsun.lims.vo.operationOutline.CourseObjectiveVO;
import net.zjcclims.domain.OperationOutlineCourseObjective;
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
import java.util.Set;

@Repository("OperationOutlineCourseObjectiveDAO")
@Transactional
public class OperationOutlineCourseObjectiveDAOImpl extends AbstractJpaDao<OperationOutlineCourseObjective> implements
        OperationOutlineCourseObjectiveDAO{
    /**
     * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
     *
     */
    private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { OperationOutlineCourseObjective.class }));

    /**
     * EntityManager injected by Spring for persistence unit zjcclimsConn
     *
     */
    @PersistenceContext(unitName = "zjcclimsConn")
    private EntityManager entityManager;

    /**
     * Instantiates a new OperationOutlineCourseObjectiveImpl
     *
     */
    public OperationOutlineCourseObjectiveDAOImpl() {
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


    @Transactional
    public CourseObjectiveVO saveOperationOutlineCourseObjective(CourseObjectiveVO courseObjectiveVO) throws DataAccessException{
        OperationOutlineCourseObjective o = new OperationOutlineCourseObjective();
        o.setOperationOutlineId(Integer.parseInt(courseObjectiveVO.getOperationOutlineId()));
        o.setObjectiveName(courseObjectiveVO.getObjectiveName());
        o.setObjectiveContent(courseObjectiveVO.getObjectiveContent());
        this.store(o);
        this.flush();
        courseObjectiveVO.setId(o.getId());
        return courseObjectiveVO;
    }
    /**
     * JPQL Query - findSchoolAcademyByPrimaryKey
     *
     */
    @Transactional
    public OperationOutlineCourseObjective findOperationOutlineCourseObjectiveByPrimaryKey(Integer objectiveId) throws DataAccessException {

        return findOperationOutlineCourseObjectiveByPrimaryKey(objectiveId, -1, -1);
    }
    /**
     * JPQL Query - findSchoolAcademyByPrimaryKey
     *
     */

    @Transactional
    public OperationOutlineCourseObjective findOperationOutlineCourseObjectiveByPrimaryKey(Integer objectiveId, int startResult, int maxRows) throws DataAccessException {
        try {
            Query query = createNamedQuery("findOperationOutlineCourseObjectiveByPrimaryKey", startResult, maxRows, objectiveId);
            return (net.zjcclims.domain.OperationOutlineCourseObjective) query.getSingleResult();
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
    public boolean canBeMerged(OperationOutlineCourseObjective entity) {
        return true;
    }

}
