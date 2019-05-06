package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
        @NamedQuery(name = "findAllRefuseItemBackups", query = "select myRefuseItemBackup from RefuseItemBackup myRefuseItemBackup"),
        @NamedQuery(name = "findRefuseItemBackupById", query = "select myRefuseItemBackup from RefuseItemBackup myRefuseItemBackup where myRefuseItemBackup.id = ?1"),
        @NamedQuery(name = "findRefuseItemBackupByPrimaryKey", query = "select myRefuseItemBackup from RefuseItemBackup myRefuseItemBackup where myRefuseItemBackup.id = ?1"),
        @NamedQuery(name = "findRefuseItemBackupByBusinessId", query = "select myRefuseItemBackup from RefuseItemBackup myRefuseItemBackup where myRefuseItemBackup.businessId = ?1"),
        @NamedQuery(name = "findRefuseItemBackupByTerm", query = "select myRefuseItemBackup from RefuseItemBackup myRefuseItemBackup where myRefuseItemBackup.term = ?1"),
        @NamedQuery(name = "findRefuseItemBackupByType", query = "select myRefuseItemBackup from RefuseItemBackup myRefuseItemBackup where myRefuseItemBackup.type = ?1") })
@Table(name = "refuse_item_backup")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "RefuseItemBackup")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class RefuseItemBackup implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    @Column(name = "id", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlElement
    Integer id;

    /**
     * 业务id
     */
    @Column(name = "business_id")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String businessId;

    /**
     * 业务类型
     */
    @Column(name = "type")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String type;

    /**
     * 学期
     */
    @Column(name = "term")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    Integer term;

    /**
     * 开始节次
     */
    @Column(name = "start_class")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    Integer startClass;

    /**
     * 结束节次
     */
    @Column(name = "end_class")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    Integer endClass;

    /**
     * 开始周次
     */
    @Column(name = "start_week")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    Integer startWeek;

    /**
     * 结束周次
     */
    @Column(name = "end_week")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    Integer endWeek;

    /**
     * 星期
     */
    @Column(name = "weekday")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    Integer weekday;

    /**
     * 实验室名称
     */
    @Column(name = "lab_room_name")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String labRoomName;

    /**
     * 授课教师姓名
     */
    @Column(name = "teacher")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String teacher;

    /**
     * 指导教师姓名
     */
    @Column(name = "tutor")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String tutor;

    /**
     * 创建人
     */
    @Column(name = "creator")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String creator;

    /**
     * 实验项目名称
     */
    @Column(name = "operation_item_name")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String operationItemName;
    /**
     * 备注
     */
    @Column(name = "memo")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String memo;

    /**
     * 审核信息
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "audit_id", referencedColumnName = "id") })
    @XmlTransient
    AuditRefuseBackup auditRefuseBackup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getStartClass() {
        return startClass;
    }

    public void setStartClass(Integer startClass) {
        this.startClass = startClass;
    }

    public Integer getEndClass() {
        return endClass;
    }

    public void setEndClass(Integer endClass) {
        this.endClass = endClass;
    }

    public Integer getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(Integer startWeek) {
        this.startWeek = startWeek;
    }

    public Integer getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(Integer endWeek) {
        this.endWeek = endWeek;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public String getLabRoomName() {
        return labRoomName;
    }

    public void setLabRoomName(String labRoomName) {
        this.labRoomName = labRoomName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getOperationItemName() {
        return operationItemName;
    }

    public void setOperationItemName(String operationItemName) {
        this.operationItemName = operationItemName;
    }

    public AuditRefuseBackup getAuditRefuseBackup() {
        return auditRefuseBackup;
    }

    public void setAuditRefuseBackup(AuditRefuseBackup auditRefuseBackup) {
        this.auditRefuseBackup = auditRefuseBackup;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     */
    public RefuseItemBackup() {
    }

    /**
     * Copies the contents of the specified bean into this bean.
     *
     */
    public void copy(RefuseItemBackup that) {
        setId(that.getId());
        setAuditRefuseBackup(that.getAuditRefuseBackup());
        setOperationItemName(that.getOperationItemName());
        setBusinessId(that.getBusinessId());
        setStartWeek(that.getStartWeek());
        setTerm(that.getTerm());
        setTutor(that.getTutor());
        setWeekday(that.getWeekday());
        setType(that.getType());
        setLabRoomName(that.getLabRoomName());
        setStartClass(that.getStartClass());
        setCreator(that.getCreator());
        setEndClass(that.getEndClass());
        setEndWeek(that.getEndWeek());
        setTeacher(that.getTeacher());
        setMemo(that.getMemo());
    }

    /**
     * Returns a textual representation of a bean.
     *
     */
    public String toString() {

        StringBuilder buffer = new StringBuilder();
        buffer.append("id=[").append(id).append("] ");
        buffer.append("businessId=[").append(businessId).append("] ");
        buffer.append("type=[").append(type).append("] ");
        buffer.append("term=[").append(term).append("] ");
        buffer.append("startClass=[").append(startClass).append("] ");
        buffer.append("endClass=[").append(endClass).append("] ");
        buffer.append("startWeek=[").append(startWeek).append("] ");
        buffer.append("endWeek=[").append(endWeek).append("] ");
        buffer.append("weekday=[").append(weekday).append("] ");
        buffer.append("labRoomName=[").append(labRoomName).append("] ");
        buffer.append("teacher=[").append(teacher).append("] ");
        buffer.append("tutor=[").append(tutor).append("] ");
        buffer.append("creator=[").append(creator).append("] ");
        buffer.append("operationItemName=[").append(operationItemName).append("] ");
        buffer.append("auditRefuseBackup=[").append(auditRefuseBackup).append("] ");
        buffer.append("memo=[").append(memo).append("] ");
        return buffer.toString();
    }

    /**
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int) (prime * result + ((id == null) ? 0 : id.hashCode()));
        return result;
    }

    /**
     */
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof RefuseItemBackup))
            return false;
        RefuseItemBackup equalCheck = (RefuseItemBackup) obj;
        if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
            return false;
        if (id != null && !id.equals(equalCheck.id))
            return false;
        return true;
    }
}
