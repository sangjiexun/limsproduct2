package net.zjcclims.dao;

import net.gvsun.lims.vo.operationOutline.ObjectiveRelatedVO;
import net.zjcclims.domain.OperationOutlineCourseObjectiveRelated;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

public interface OperationOutlineCourseObjectiveRelatedDAO extends JpaDao<OperationOutlineCourseObjectiveRelated> {

    public ObjectiveRelatedVO saveOperationOutlineCourseObjectiveRelated(ObjectiveRelatedVO objectiveRelatedVO) throws DataAccessException;
    /**
     * JPQL Query - findAssetCabinetByPrimaryKey
     *
     */
    public OperationOutlineCourseObjectiveRelated findOperationOutlineCourseObjectiveRelatedByPrimaryKey(Integer objectiveId) throws DataAccessException;
    /**
     * JPQL Query - findAssetCabinetByPrimaryKey
     *
     */
    public OperationOutlineCourseObjectiveRelated findOperationOutlineCourseObjectiveRelatedByPrimaryKey(Integer objectiveId, int startResult, int maxRows) throws DataAccessException ;
}
