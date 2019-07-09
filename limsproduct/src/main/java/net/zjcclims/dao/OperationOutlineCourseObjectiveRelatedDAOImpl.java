
package net.zjcclims.dao;

import net.gvsun.lims.vo.operationOutline.CourseObjectiveVO;
import net.gvsun.lims.vo.operationOutline.ObjectiveRelatedVO;
import net.zjcclims.domain.OperationOutlineCourseObjectiveRelated;
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

@Repository("OperationOutlineCourseObjectiveRelatedDAO")
@Transactional
public class OperationOutlineCourseObjectiveRelatedDAOImpl extends AbstractJpaDao<OperationOutlineCourseObjectiveRelated> implements
        OperationOutlineCourseObjectiveRelatedDAO{
    /**
     * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
     *
     */
    private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { OperationOutlineCourseObjectiveRelated.class }));

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
    public OperationOutlineCourseObjectiveRelatedDAOImpl() {
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
    public ObjectiveRelatedVO saveOperationOutlineCourseObjectiveRelated(ObjectiveRelatedVO objectiveRelatedVO) throws DataAccessException{
        OperationOutlineCourseObjectiveRelated o = new OperationOutlineCourseObjectiveRelated();
        o.setOperationOutlineId(Integer.parseInt(objectiveRelatedVO.getOperationOutlineId()));
        o.setType(Integer.parseInt(objectiveRelatedVO.getType()));
        o.setObjectiveIds(objectiveRelatedVO.getObjectiveIds());
        o.setObjectiveNames(objectiveRelatedVO.getObjectiveNames());
        o.setGraduationRequirement(objectiveRelatedVO.getGraduationRequirement());
        o.setRequirementPoint(objectiveRelatedVO.getRequirementPoint());
        o.setCourseContent(objectiveRelatedVO.getCourseContent());
        o.setCourseRequirement(objectiveRelatedVO.getCourseRequirement());
        o.setCourseHour(objectiveRelatedVO.getCourseHour());
        o.setMethod(objectiveRelatedVO.getMethod());
        o.setAppraiseName(objectiveRelatedVO.getAppraiseName());
        o.setAppraisePercentage(objectiveRelatedVO.getAppraisePercentage());
        o.setAppraiseDetail(objectiveRelatedVO.getAppraiseDetail());
        o.setAverageScore(objectiveRelatedVO.getAverageScore());
        o.setObjectiveCompletionRate(objectiveRelatedVO.getObjectiveCompletionRate());
        o=this.store(o);
        this.flush();
        objectiveRelatedVO.setId(o.getId());
        return objectiveRelatedVO;
    }
    /**
     * JPQL Query - findSchoolAcademyByPrimaryKey
     *
     */
    @Transactional
    public OperationOutlineCourseObjectiveRelated findOperationOutlineCourseObjectiveRelatedByPrimaryKey(Integer objectiveRelatedId) throws DataAccessException {

        return findOperationOutlineCourseObjectiveRelatedByPrimaryKey(objectiveRelatedId, -1, -1);
    }
    /**
     * JPQL Query - findSchoolAcademyByPrimaryKey
     *
     */

    @Transactional
    public OperationOutlineCourseObjectiveRelated findOperationOutlineCourseObjectiveRelatedByPrimaryKey(Integer objectiveRelatedId, int startResult, int maxRows) throws DataAccessException {
        try {
            Query query = createNamedQuery("findOperationOutlineCourseObjectiveRelatedByPrimaryKey", startResult, maxRows, objectiveRelatedId);
            return (OperationOutlineCourseObjectiveRelated) query.getSingleResult();
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
    public boolean canBeMerged(OperationOutlineCourseObjectiveRelated entity) {
        return true;
    }

}
