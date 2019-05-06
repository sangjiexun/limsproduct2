package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
        @NamedQuery(name = "findAllChoseUsers", query = "select myChoseUser from ChoseUser myChoseUser"),
        @NamedQuery(name = "findChoseUserById", query = "select myChoseUser from ChoseUser myChoseUser where myChoseUser.id = ?1")
})
@Table(name = "chose_user")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ChoseUser")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class ChoseUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     */

    @Column(name = "id", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @XmlElement
    Integer id;

    //所属文档id
    @Column(name = "document_id")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    Integer documentId;

    //真正的入学时间
    @Column(name = "real_attendance_time", length = 20)
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String realAttendanceTime;

    /**
     * 导师username（非外键关联）
     *
     */

    @Column(name = "professor")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String professor;
    /**
     * 是否提交选择导师志愿1是2否
     *
     */

    @Column(name = "is_chose_professor")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    Integer isChoseProfessor;
    /**
     * 是否提交选择毕业论文志愿1是2否
     *
     */

    @Column(name = "is_chose_dissertation")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    Integer isChoseDissertation;
    /**
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "dissertation_id", referencedColumnName = "id") })
    @XmlTransient
    ChoseDissertation choseDissertation;

    /**
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "username", referencedColumnName = "username") })
    @XmlTransient
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public String getRealAttendanceTime() {
        return realAttendanceTime;
    }

    public void setRealAttendanceTime(String realAttendanceTime) {
        this.realAttendanceTime = realAttendanceTime;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public Integer getIsChoseProfessor() {
        return isChoseProfessor;
    }

    public void setIsChoseProfessor(Integer isChoseProfessor) {
        this.isChoseProfessor = isChoseProfessor;
    }

    public Integer getIsChoseDissertation() {
        return isChoseDissertation;
    }

    public void setIsChoseDissertation(Integer isChoseDissertation) {
        this.isChoseDissertation = isChoseDissertation;
    }

    public ChoseDissertation getChoseDissertation() {
        return choseDissertation;
    }

    public void setChoseDissertation(ChoseDissertation choseDissertation) {
        this.choseDissertation = choseDissertation;
    }

    /**
     */

    public ChoseUser() {
    }

    /**
     * Copies the contents of the specified bean into this bean.
     *
     */
    public void copy(ChoseUser that) {
        this.setDocumentId(that.getDocumentId());
        this.setRealAttendanceTime(that.getRealAttendanceTime());
        this.setProfessor(that.getProfessor());
        this.setIsChoseProfessor(that.getIsChoseProfessor());
        this.setIsChoseDissertation(that.getIsChoseDissertation());
        this.setChoseDissertation(that.getChoseDissertation());
    }

    /**
     * Returns a textual representation of a bean.
     *
     */

    public String toString() {

        return "id=[" + id + "] " +
                "documentId=[" + documentId + "] " +
                "realAttendanceTime=[" + realAttendanceTime + "] " +
                "professor=[" + professor + "] " +
                "isChoseProfessor=[" + isChoseProfessor + "] " +
                "isChoseDissertation=[" + isChoseDissertation + "] " +
                "choseDissertation=[" + choseDissertation + "] ";
    }

    /**
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((documentId == null) ? 0 : documentId.hashCode());
        result = prime * result + ((realAttendanceTime == null) ? 0 : realAttendanceTime.hashCode());
        result = prime * result + ((professor == null) ? 0 : professor.hashCode());
        result = prime * result + ((isChoseProfessor == null) ? 0 : isChoseProfessor.hashCode());
        result = prime * result + ((isChoseDissertation == null) ? 0 : isChoseDissertation.hashCode());
        result = prime * result + ((choseDissertation == null) ? 0 : choseDissertation.hashCode());
        return result;
    }

    /**
     */
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof ChoseUser))
            return false;
        ChoseUser equalCheck = (ChoseUser) obj;
        if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
            return false;
        if (id != null && !id.equals(equalCheck.id))
            return false;
        return true;
    }
}
