package net.gvsun.lims.vo.operationOutline;

import net.zjcclims.domain.OperationOutlineCourseObjective;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *Desciption 课程目标vo
 * @author 刘博越
 * 2019-7-3
 */
public class CourseObjectiveVO implements Serializable {
    //id
    private int id;
    //对应大纲id
    private String operationOutlineId;
    //课程目标名称
    private String objectiveName;
    //课程目标内容
    private String objectiveContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOperationOutlineId() {
        return operationOutlineId;
    }

    public void setOperationOutlineId(String operationOutlineId) {
        this.operationOutlineId = operationOutlineId;
    }

    public String getObjectiveName() {
        return objectiveName;
    }

    public void setObjectiveName(String objectiveName) {
        this.objectiveName = objectiveName;
    }

    public String getObjectiveContent() {
        return objectiveContent;
    }

    public void setObjectiveContent(String objectiveContent) {
        this.objectiveContent = objectiveContent;
    }
    public List<CourseObjectiveVO> toVo(List<OperationOutlineCourseObjective> operationOutlineCourseObjectives){
        List <CourseObjectiveVO> returnList = new ArrayList<>();
        for(OperationOutlineCourseObjective o:operationOutlineCourseObjectives){
            CourseObjectiveVO c = new CourseObjectiveVO();
            c.setId(o.getId());
            c.setOperationOutlineId(String.valueOf(o.getOperationOutlineId()));
            c.setObjectiveName(o.getObjectiveName());
            c.setObjectiveContent(o.getObjectiveContent());
            returnList.add(c);
        }
        return returnList;
    }
}
