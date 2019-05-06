package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@NamedQueries({
        @NamedQuery(name = "findReservationSetItemById", query = "select myReservationSetItem from ReservationSetItem myReservationSetItem where myReservationSetItem.id = ?1"),
        @NamedQuery(name = "findReservationSetItemBylabRoomIdAndType", query = "select myReservationSetItem from ReservationSetItem myReservationSetItem where myReservationSetItem.labRoomId = ?1 and myReservationSetItem.itemType =?2")})
@Table(name = "reservation_set_item")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ReservationSetItem")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class ReservationSetItem implements Serializable {
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
    @Column(name = "open_inweekend")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    Integer openInweekend;
    /*
     * */
    @Column(name = "start_hour_inweek", precision = 11)
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    BigDecimal startHourInweek;
    /*
     * */
    @Column(name = "end_hour_inweek", precision = 11)
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    BigDecimal endHourInweek;
    /*
     * */
    @Column(name = "start_hour_inweekend")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    BigDecimal startHourInweekend;
    /*
     * */
    @Column(name = "end_hour_inweekend", precision = 11)
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    BigDecimal endHourInweekend;
    /**学期外键待定
     */
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "term", referencedColumnName = "id") })
    @XmlTransient
    SchoolTerm schoolTerm;*/
    /**
     * 标志位0实验室配置项，1设备配置项
     */
    @Column(name = "item_type", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    Integer itemType;

    public ReservationSetItem() {
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

    public Integer getOpenInweekend() {
        return openInweekend;
    }

    public void setOpenInweekend(Integer openInweekend) {
        this.openInweekend = openInweekend;
    }

    public BigDecimal getStartHourInweek() {
        return startHourInweek;
    }

    public void setStartHourInweek(BigDecimal startHourInweek) {
        this.startHourInweek = startHourInweek;
    }

    public BigDecimal getEndHourInweek() {
        return endHourInweek;
    }

    public void setEndHourInweek(BigDecimal endHourInweek) {
        this.endHourInweek = endHourInweek;
    }

    public BigDecimal getStartHourInweekend() {
        return startHourInweekend;
    }

    public void setStartHourInweekend(BigDecimal startHourInweekend) {
        this.startHourInweekend = startHourInweekend;
    }

    public BigDecimal getEndHourInweekend() {
        return endHourInweekend;
    }

    public void setEndHourInweekend(BigDecimal endHourInweekend) {
        this.endHourInweekend = endHourInweekend;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }
}
