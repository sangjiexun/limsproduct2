package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 */

@Entity
@NamedQueries({
        @NamedQuery(name = "findAllDeviceRepairs", query = "select myDeviceRepair from DeviceRepair myDeviceRepair"),
        @NamedQuery(name = "findDeviceRepairByDeviceName", query = "select myDeviceRepair from DeviceRepair myDeviceRepair where myDeviceRepair.deviceName = ?1"),
        @NamedQuery(name = "findDeviceRepairByDeviceNumber", query = "select myDeviceRepair from DeviceRepair myDeviceRepair where myDeviceRepair.deviceNumber = ?1"),
        @NamedQuery(name = "findDeviceRepairByLabAddress", query = "select myDeviceRepair from DeviceRepair myDeviceRepair where myDeviceRepair.labAddress = ?1"),
        @NamedQuery(name = "findDeviceRepairByCreater", query = "select myDeviceRepair from DeviceRepair myDeviceRepair where myDeviceRepair.creater = ?1"),
        @NamedQuery(name = "findDeviceRepairByCreateDate", query = "select myDeviceRepair from DeviceRepair myDeviceRepair where myDeviceRepair.createDate = ?1"),
        @NamedQuery(name = "findDeviceRepairByContent", query = "select myDeviceRepair from DeviceRepair myDeviceRepair where myDeviceRepair.content = ?1"),
        @NamedQuery(name = "findDeviceRepairByAuditStage", query = "select myDeviceRepair from DeviceRepair myDeviceRepair where myDeviceRepair.auditStage = ?1"),
        @NamedQuery(name = "findDeviceRepairByConfirmUser", query = "select myDeviceRepair from DeviceRepair myDeviceRepair where myDeviceRepair.confirmUser = ?1"),
        @NamedQuery(name = "findDeviceRepairByConfirmAmount", query = "select myDeviceRepair from DeviceRepair myDeviceRepair where myDeviceRepair.confirmAmount = ?1"),
        @NamedQuery(name = "findDeviceRepairByConfirmContent", query = "select myDeviceRepair from DeviceRepair myDeviceRepair where myDeviceRepair.confirmContent = ?1"),
        @NamedQuery(name = "findDeviceRepairByConfirmDate", query = "select myDeviceRepair from DeviceRepair myDeviceRepair where myDeviceRepair.confirmDate = ?1"),
        @NamedQuery(name = "findDeviceRepairByType", query = "select myDeviceRepair from DeviceRepair myDeviceRepair where myDeviceRepair.type = ?1"),
        @NamedQuery(name = "findDeviceRepairById", query = "select myDeviceRepair from DeviceRepair myDeviceRepair where myDeviceRepair.id = ?1")
})
@Table(name = "device_repair")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "DeviceRepair")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class DeviceRepair implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     */

    @Column(name = "id", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @XmlElement
    Integer id;

    @Column(name = "device_name")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String deviceName;

    @Column(name = "device_number")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String deviceNumber;


    @Column(name = "lab_address")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String labAddress;

    @Column(name = "creater")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String creater;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    Calendar createDate;

    @Column(name = "content", columnDefinition = "TEXT")
    @Basic(fetch = FetchType.EAGER)
    @Lob
    @XmlElement
    String content;

    @Column(name = "audit_stage")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    Integer auditStage;

    @Column(name = "confirm_user")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String confirmUser;

    @Column(name = "confirm_amount")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    BigDecimal confirmAmount;

    @Column(name = "confirm_content")
    @Basic(fetch = FetchType.EAGER)
    @Lob
    @XmlElement
    String confirmContent;

    @Column(name = "memo")
    @Basic(fetch = FetchType.EAGER)
    @Lob
    @XmlElement
    String memo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "confirm_date")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    Calendar confirmDate;

    @Column(name = "type")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    Integer type;

    @Column(name = "report_user")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String reportUser;

    @Column(name = "expect_amount")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    BigDecimal expectAmount;

    @Column(name = "acceptance_user")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String acceptanceUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "acceptance_date")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    Calendar acceptanceDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getLabAddress() {
        return labAddress;
    }

    public void setLabAddress(String labAddress) {
        this.labAddress = labAddress;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAuditStage() {
        return auditStage;
    }

    public void setAuditStage(Integer auditStage) {
        this.auditStage = auditStage;
    }

    public String getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(String confirmUser) {
        this.confirmUser = confirmUser;
    }

    public BigDecimal getConfirmAmount() {
        return confirmAmount;
    }

    public void setConfirmAmount(BigDecimal confirmAmount) {
        this.confirmAmount = confirmAmount;
    }

    public String getConfirmContent() {
        return confirmContent;
    }

    public void setConfirmContent(String confirmContent) {
        this.confirmContent = confirmContent;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Calendar getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Calendar confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getReportUser() {
        return reportUser;
    }

    public void setReportUser(String reportUser) {
        this.reportUser = reportUser;
    }

    public BigDecimal getExpectAmount() {
        return expectAmount;
    }

    public void setExpectAmount(BigDecimal expectAmount) {
        this.expectAmount = expectAmount;
    }

    public String getAcceptanceUser() {
        return acceptanceUser;
    }

    public void setAcceptanceUser(String acceptanceUser) {
        this.acceptanceUser = acceptanceUser;
    }

    public Calendar getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(Calendar acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    /**
     */
    public DeviceRepair() {
    }

    /**
     * Copies the contents of the specified bean into this bean.
     *
     */
    public void copy(DeviceRepair that) {
        this.setAuditStage(that.getAuditStage());
        this.setConfirmAmount(that.getConfirmAmount());
        this.setConfirmContent(that.getConfirmContent());
        this.setConfirmDate(that.getConfirmDate());
        this.setConfirmUser(that.getConfirmUser());
        this.setContent(that.getContent());
        this.setCreateDate(that.getCreateDate());
        this.setCreater(that.getCreater());
        this.setDeviceName(that.getDeviceName());
        this.setDeviceNumber(that.getDeviceNumber());
        this.setId(that.getId());
        this.setLabAddress(that.getLabAddress());
        this.setMemo(that.getMemo());
        this.setType(that.getType());
        this.setReportUser(that.getReportUser());
        this.setExpectAmount(that.getExpectAmount());
        this.setAcceptanceUser(that.getAcceptanceUser());
    }

    /**
     * Returns a textual representation of a bean.
     *
     */
    public String toString() {

        StringBuilder buffer = new StringBuilder();

        buffer.append("id=[").append(id).append("] ");
        buffer.append("content=[").append(content).append("] ");
        buffer.append("deviceName=[").append(deviceName).append("] ");
        buffer.append("deviceNumber=[").append(deviceNumber).append("] ");
        buffer.append("labAddress=[").append(labAddress).append("] ");
        buffer.append("creater=[").append(creater).append("] ");
        buffer.append("createDate=[").append(createDate).append("] ");
        buffer.append("auditStage=[").append(auditStage).append("] ");
        buffer.append("confirmUser=[").append(confirmUser).append("] ");
        buffer.append("confirmContent=[").append(confirmContent).append("] ");
        buffer.append("memo=[").append(memo).append("] ");
        buffer.append("confirmDate=[").append(confirmDate).append("] ");
        buffer.append("type=[").append(type).append("] ");
        buffer.append("reportUser=[").append(reportUser).append("] ");
        buffer.append("expectAmount=[").append(expectAmount).append("] ");
        buffer.append("acceptanceUser=[").append(acceptanceUser).append("] ");
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
        if (!(obj instanceof DeviceRepair))
            return false;
        DeviceRepair equalCheck = (DeviceRepair) obj;
        if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
            return false;
        if (id != null && !id.equals(equalCheck.id))
            return false;
        return true;
    }
}
