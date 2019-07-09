package net.gvsun.lims.vo.operationOutline;

import net.zjcclims.domain.OperationOutlineCourseObjective;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *Desciption 课程目标相关数据Vo
 * @author 刘博越
 * 2019-7-4
 */
public class ObjectiveRelatedVO implements Serializable {
    //id
    private int id;
    //对应大纲id
    private String operationOutlineId;
    //记录数据类型。0：毕业要求；1：课程内容；2：考核方法；3：课程目标达成度
    private String type;
    //对应课程目标id集合
    private String objectiveIds;
    //对应课程目标名字集合
    private String objectiveNames;
    //毕业要求
    private String graduationRequirement;
    //毕业要求指标点
    private String requirementPoint;
    //教学课程内容
    private String courseContent;
    //教学要求
    private String courseRequirement;
    //学时
    private Integer courseHour;
    //教学方式
    private String method;
    //考核环节
    private String appraiseName;
    //所占分值
    private Integer appraisePercentage;
    //考核与评价细则
    private String appraiseDetail;
    //学生平均分（指代符号）
    private String averageScore;
    //达成度计算结果
    private String objectiveCompletionRate;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getObjectiveIds() {
        return objectiveIds;
    }

    public void setObjectiveIds(String objectiveIds) {
        this.objectiveIds = objectiveIds;
    }

    public String getObjectiveNames() {
        return objectiveNames;
    }

    public void setObjectiveNames(String objectiveNames) {
        this.objectiveNames = objectiveNames;
    }

    public String getGraduationRequirement() {
        return graduationRequirement;
    }

    public void setGraduationRequirement(String graduationRequirement) {
        this.graduationRequirement = graduationRequirement;
    }

    public String getRequirementPoint() {
        return requirementPoint;
    }

    public void setRequirementPoint(String requirementPoint) {
        this.requirementPoint = requirementPoint;
    }

    public String getCourseContent() {
        return courseContent;
    }

    public void setCourseContent(String courseContent) {
        this.courseContent = courseContent;
    }

    public String getCourseRequirement() {
        return courseRequirement;
    }

    public void setCourseRequirement(String courseRequirement) {
        this.courseRequirement = courseRequirement;
    }

    public Integer getCourseHour() {
        return courseHour;
    }

    public void setCourseHour(Integer courseHour) {
        this.courseHour = courseHour;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAppraiseName() {
        return appraiseName;
    }

    public void setAppraiseName(String appraiseName) {
        this.appraiseName = appraiseName;
    }

    public Integer getAppraisePercentage() {
        return appraisePercentage;
    }

    public void setAppraisePercentage(Integer appraisePercentage) {
        this.appraisePercentage = appraisePercentage;
    }

    public String getAppraiseDetail() {
        return appraiseDetail;
    }

    public void setAppraiseDetail(String appraiseDetail) {
        this.appraiseDetail = appraiseDetail;
    }

    public String getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(String averageScore) {
        this.averageScore = averageScore;
    }

    public String getObjectiveCompletionRate() {
        return objectiveCompletionRate;
    }

    public void setObjectiveCompletionRate(String objectiveCompletionRate) {
        this.objectiveCompletionRate = objectiveCompletionRate;
    }

}
