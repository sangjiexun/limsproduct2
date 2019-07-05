package net.zjcclims.domain;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "findOperationOutlineCourseObjectiveByPrimaryKey", query = "select o from OperationOutlineCourseObjective o where o.id = ?1")})
@Table(name = "operation_outline_course_objective", schema = "limsproduct")
public class OperationOutlineCourseObjective {
    private int id;
    private Integer operationOutlineId;
    private String objectiveName;
    private String objectiveContent;

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
    @Column(name = "objective_name")
    public String getObjectiveName() {
        return objectiveName;
    }

    public void setObjectiveName(String objectiveName) {
        this.objectiveName = objectiveName;
    }

    @Basic
    @Column(name = "objective_content")
    public String getObjectiveContent() {
        return objectiveContent;
    }

    public void setObjectiveContent(String objectiveContent) {
        this.objectiveContent = objectiveContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OperationOutlineCourseObjective that = (OperationOutlineCourseObjective) o;

        if (id != that.id) return false;
        if (operationOutlineId != null ? !operationOutlineId.equals(that.operationOutlineId) : that.operationOutlineId != null)
            return false;
        if (objectiveName != null ? !objectiveName.equals(that.objectiveName) : that.objectiveName != null)
            return false;
        if (objectiveContent != null ? !objectiveContent.equals(that.objectiveContent) : that.objectiveContent != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (operationOutlineId != null ? operationOutlineId.hashCode() : 0);
        result = 31 * result + (objectiveName != null ? objectiveName.hashCode() : 0);
        result = 31 * result + (objectiveContent != null ? objectiveContent.hashCode() : 0);
        return result;
    }
}
