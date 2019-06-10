package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@NamedQueries({
        @NamedQuery(name = "findLabRelevantConfigBylabRoomIdAndCategory", query = "select myLabRelevantConfig from LabRelevantConfig myLabRelevantConfig where myLabRelevantConfig.labRoomId = ?1 and myLabRelevantConfig.configCategory =?2")})
@Table(name = "lab_relevant_config")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabRelevantConfig")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class LabRelevantConfig implements Serializable {
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
    @Column(name = "config_category")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String configCategory;
    /*
     * */
    @Column(name = "set_item")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    Integer setItem;

    public LabRelevantConfig() {
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

    public String getConfigCategory() {
        return configCategory;
    }

    public void setConfigCategory(String configCategory) {
        this.configCategory = configCategory;
    }

    public Integer getSetItem() {
        return setItem;
    }

    public void setSetItem(Integer setItem) {
        this.setItem = setItem;
    }
}
