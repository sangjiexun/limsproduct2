package net.zjcclims.dao;

import net.gvsun.lims.vo.operationOutline.CourseObjectiveVO;
import net.zjcclims.domain.OperationOutlineCourseObjective;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

public interface OperationOutlineCourseObjectiveDAO extends JpaDao<OperationOutlineCourseObjective> {

    public CourseObjectiveVO saveOperationOutlineCourseObjective(CourseObjectiveVO courseObjectiveVO) throws DataAccessException;
    /**
     * JPQL Query - findAssetCabinetByPrimaryKey
     *
     */
    public OperationOutlineCourseObjective findOperationOutlineCourseObjectiveByPrimaryKey(Integer objectiveId) throws DataAccessException;
    /**
     * JPQL Query - findAssetCabinetByPrimaryKey
     *
     */
    public OperationOutlineCourseObjective findOperationOutlineCourseObjectiveByPrimaryKey(Integer objectiveId, int startResult, int maxRows) throws DataAccessException ;
}
