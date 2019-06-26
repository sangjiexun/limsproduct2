package net.zjcclims.domain;

import javax.persistence.*;

@Entity
@Table(name = "operation_outline_course_objective_related", schema = "limsproduct", catalog = "")
public class OperationOutlineCourseObjectiveRelated {
    private int id;
    private Integer operationOutlineId;
    private Integer type;
    private String objectiveIds;
    private String objectiveNames;
    private String graduationRequirement;
    private String requirementPoint;
    private String courseContent;
    private String courseRequirement;
    private Integer courseHour;
    private String method;
    private String appraiseName;
    private Integer appraisePercentage;
    private String appraiseDetail;
    private String averageScore;
    private String objectiveCompletionRate;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "operation_outline_id")
    public Integer getOperationOutlineId() {
        return operationOutlineId;
    }

    public void setOperationOutlineId(Integer operationOutlineId) {
        this.operationOutlineId = operationOutlineId;
    }

    @Basic
    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "objective_ids")
    public String getObjectiveIds() {
        return objectiveIds;
    }

    public void setObjectiveIds(String objectiveIds) {
        this.objectiveIds = objectiveIds;
    }

    @Basic
    @Column(name = "objective_names")
    public String getObjectiveNames() {
        return objectiveNames;
    }

    public void setObjectiveNames(String objectiveNames) {
        this.objectiveNames = objectiveNames;
    }

    @Basic
    @Column(name = "graduation_requirement")
    public String getGraduationRequirement() {
        return graduationRequirement;
    }

    public void setGraduationRequirement(String graduationRequirement) {
        this.graduationRequirement = graduationRequirement;
    }

    @Basic
    @Column(name = "requirement_point")
    public String getRequirementPoint() {
        return requirementPoint;
    }

    public void setRequirementPoint(String requirementPoint) {
        this.requirementPoint = requirementPoint;
    }

    @Basic
    @Column(name = "course_content")
    public String getCourseContent() {
        return courseContent;
    }

    public void setCourseContent(String courseContent) {
        this.courseContent = courseContent;
    }

    @Basic
    @Column(name = "course_requirement")
    public String getCourseRequirement() {
        return courseRequirement;
    }

    public void setCourseRequirement(String courseRequirement) {
        this.courseRequirement = courseRequirement;
    }

    @Basic
    @Column(name = "course_hour")
    public Integer getCourseHour() {
        return courseHour;
    }

    public void setCourseHour(Integer courseHour) {
        this.courseHour = courseHour;
    }

    @Basic
    @Column(name = "method")
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Basic
    @Column(name = "appraise_name")
    public String getAppraiseName() {
        return appraiseName;
    }

    public void setAppraiseName(String appraiseName) {
        this.appraiseName = appraiseName;
    }

    @Basic
    @Column(name = "appraise_percentage")
    public Integer getAppraisePercentage() {
        return appraisePercentage;
    }

    public void setAppraisePercentage(Integer appraisePercentage) {
        this.appraisePercentage = appraisePercentage;
    }

    @Basic
    @Column(name = "appraise_detail")
    public String getAppraiseDetail() {
        return appraiseDetail;
    }

    public void setAppraiseDetail(String appraiseDetail) {
        this.appraiseDetail = appraiseDetail;
    }

    @Basic
    @Column(name = "average_score")
    public String getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(String averageScore) {
        this.averageScore = averageScore;
    }

    @Basic
    @Column(name = "objective_completion_rate")
    public String getObjectiveCompletionRate() {
        return objectiveCompletionRate;
    }

    public void setObjectiveCompletionRate(String objectiveCompletionRate) {
        this.objectiveCompletionRate = objectiveCompletionRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OperationOutlineCourseObjectiveRelated that = (OperationOutlineCourseObjectiveRelated) o;

        if (id != that.id) return false;
        if (operationOutlineId != null ? !operationOutlineId.equals(that.operationOutlineId) : that.operationOutlineId != null)
            return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (objectiveIds != null ? !objectiveIds.equals(that.objectiveIds) : that.objectiveIds != null) return false;
        if (objectiveNames != null ? !objectiveNames.equals(that.objectiveNames) : that.objectiveNames != null)
            return false;
        if (graduationRequirement != null ? !graduationRequirement.equals(that.graduationRequirement) : that.graduationRequirement != null)
            return false;
        if (requirementPoint != null ? !requirementPoint.equals(that.requirementPoint) : that.requirementPoint != null)
            return false;
        if (courseContent != null ? !courseContent.equals(that.courseContent) : that.courseContent != null)
            return false;
        if (courseRequirement != null ? !courseRequirement.equals(that.courseRequirement) : that.courseRequirement != null)
            return false;
        if (courseHour != null ? !courseHour.equals(that.courseHour) : that.courseHour != null) return false;
        if (method != null ? !method.equals(that.method) : that.method != null) return false;
        if (appraiseName != null ? !appraiseName.equals(that.appraiseName) : that.appraiseName != null) return false;
        if (appraisePercentage != null ? !appraisePercentage.equals(that.appraisePercentage) : that.appraisePercentage != null)
            return false;
        if (appraiseDetail != null ? !appraiseDetail.equals(that.appraiseDetail) : that.appraiseDetail != null)
            return false;
        if (averageScore != null ? !averageScore.equals(that.averageScore) : that.averageScore != null) return false;
        if (objectiveCompletionRate != null ? !objectiveCompletionRate.equals(that.objectiveCompletionRate) : that.objectiveCompletionRate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (operationOutlineId != null ? operationOutlineId.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (objectiveIds != null ? objectiveIds.hashCode() : 0);
        result = 31 * result + (objectiveNames != null ? objectiveNames.hashCode() : 0);
        result = 31 * result + (graduationRequirement != null ? graduationRequirement.hashCode() : 0);
        result = 31 * result + (requirementPoint != null ? requirementPoint.hashCode() : 0);
        result = 31 * result + (courseContent != null ? courseContent.hashCode() : 0);
        result = 31 * result + (courseRequirement != null ? courseRequirement.hashCode() : 0);
        result = 31 * result + (courseHour != null ? courseHour.hashCode() : 0);
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (appraiseName != null ? appraiseName.hashCode() : 0);
        result = 31 * result + (appraisePercentage != null ? appraisePercentage.hashCode() : 0);
        result = 31 * result + (appraiseDetail != null ? appraiseDetail.hashCode() : 0);
        result = 31 * result + (averageScore != null ? averageScore.hashCode() : 0);
        result = 31 * result + (objectiveCompletionRate != null ? objectiveCompletionRate.hashCode() : 0);
        return result;
    }
}
