package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
        @NamedQuery(name = "findAllAuditRefuseBackups", query = "select myAuditRefuseBackup from AuditRefuseBackup myAuditRefuseBackup"),
        @NamedQuery(name = "findAuditRefuseBackupById", query = "select myAuditRefuseBackup from AuditRefuseBackup myAuditRefuseBackup where myAuditRefuseBackup.id = ?1"),
        @NamedQuery(name = "findAuditRefuseBackupByPrimaryKey", query = "select myAuditRefuseBackup from AuditRefuseBackup myAuditRefuseBackup where myAuditRefuseBackup.id = ?1"),
        @NamedQuery(name = "findAuditRefuseBackupByAuditInfo", query = "select myAuditRefuseBackup from AuditRefuseBackup myAuditRefuseBackup where myAuditRefuseBackup.auditInfo = ?1"),
        @NamedQuery(name = "findAuditRefuseBackupByAuditInfoContaining", query = "select myAuditRefuseBackup from AuditRefuseBackup myAuditRefuseBackup where myAuditRefuseBackup.auditInfo like ?1"),
        @NamedQuery(name = "findAuditRefuseBackupByAuditContent", query = "select myAuditRefuseBackup from AuditRefuseBackup myAuditRefuseBackup where myAuditRefuseBackup.auditContent = ?1"),
        @NamedQuery(name = "findAuditRefuseBackupByAuditContentContaining", query = "select myAuditRefuseBackup from AuditRefuseBackup myAuditRefuseBackup where myAuditRefuseBackup.auditContent like ?1") })
@Table(name = "audit_refuse_backup")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "AuditRefuseBackup")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class AuditRefuseBackup implements Serializable {
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
     * 审核信息
     */
    @Column(name = "audit_info")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String auditInfo;

    /**
     * 审核意见
     */
    @Column(name = "audit_content")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String auditContent;

    /**
     * 业务日志
     */
    @OneToMany(mappedBy = "auditRefuseBackup", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
    @XmlElement(name = "", namespace = "")
    java.util.Set<net.zjcclims.domain.RefuseItemBackup> refuseItemBackup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuditInfo() {
        return auditInfo;
    }

    public void setAuditInfo(String auditInfo) {
        this.auditInfo = auditInfo;
    }

    public String getAuditContent() {
        return auditContent;
    }

    public void setAuditContent(String auditContent) {
        this.auditContent = auditContent;
    }

    public Set<RefuseItemBackup> getRefuseItemBackup() {
        if(refuseItemBackup == null){
            refuseItemBackup = new HashSet<>();
        }
        return refuseItemBackup;
    }

    public void setRefuseItemBackup(Set<RefuseItemBackup> refuseItemBackup) {
        this.refuseItemBackup = refuseItemBackup;
    }

    /**
     */
    public AuditRefuseBackup() {
    }

    /**
     * Copies the contents of the specified bean into this bean.
     *
     */
    public void copy(AuditRefuseBackup that) {
        setId(that.getId());
        setAuditContent(that.getAuditContent());
        setAuditInfo(that.getAuditInfo());
    }

    /**
     * Returns a textual representation of a bean.
     *
     */
    public String toString() {

        StringBuilder buffer = new StringBuilder();
        buffer.append("id=[").append(id).append("] ");
        buffer.append("auditInfo=[").append(auditInfo).append("] ");
        buffer.append("auditContent=[").append(auditContent).append("] ");
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
        if (!(obj instanceof AuditRefuseBackup))
            return false;
        AuditRefuseBackup equalCheck = (AuditRefuseBackup) obj;
        if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
            return false;
        if (id != null && !id.equals(equalCheck.id))
            return false;
        return true;
    }
}
