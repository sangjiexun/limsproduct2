package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = "findLabOpenUpAcademyBylabRoomIdAndType", query = "select myLabOpenUpAcademy from LabOpenUpAcademy myLabOpenUpAcademy where myLabOpenUpAcademy.labRoomId = ?1 and myLabOpenUpAcademy.type =?2")})
@Table(name = "lab_open_up_academy")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabOpenUpAcademy")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class LabOpenUpAcademy implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ʵ���ҷ��ұ�
     *
     */

    @Column(name = "id", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlElement
    Integer id;
    /**
     * ʵ���ұ��
     *
     */

    @Column(name = "lab_room_id")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    Integer labRoomId;
    /*
     * */
    @Column(name = "academy_number")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String academyNumber;
    /*
     * */
    @Column(name = "authority_name")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String authorityName;
    /*
     * */
    @Column(name = "type")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    Integer type;

    public LabOpenUpAcademy() {
    }

//    public static long getSerialVersionUID() {
//        return serialVersionUID;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLabRoomId() {
        return labRoomId;
    }

    public void setLabRoomId(Integer labRoomId) {
        this.labRoomId = labRoomId;
    }

    public String getAcademyNumber() {
        return academyNumber;
    }

    public void setAcademyNumber(String academyNumber) {
        this.academyNumber = academyNumber;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
