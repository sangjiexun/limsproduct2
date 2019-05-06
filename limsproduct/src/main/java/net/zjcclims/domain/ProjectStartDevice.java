package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllProjectStartDevices", query = "select myProjectStartDevice from ProjectStartDevice myProjectStartDevice"),
		@NamedQuery(name = "findProjectStartDeviceByAmount", query = "select myProjectStartDevice from ProjectStartDevice myProjectStartDevice where myProjectStartDevice.amount = ?1"),
		@NamedQuery(name = "findProjectStartDeviceByCollection", query = "select myProjectStartDevice from ProjectStartDevice myProjectStartDevice where myProjectStartDevice.collection = ?1"),
		@NamedQuery(name = "findProjectStartDeviceByEquipmentName", query = "select myProjectStartDevice from ProjectStartDevice myProjectStartDevice where myProjectStartDevice.equipmentName = ?1"),
		@NamedQuery(name = "findProjectStartDeviceByEquipmentNameContaining", query = "select myProjectStartDevice from ProjectStartDevice myProjectStartDevice where myProjectStartDevice.equipmentName like ?1"),
		@NamedQuery(name = "findProjectStartDeviceByFormat", query = "select myProjectStartDevice from ProjectStartDevice myProjectStartDevice where myProjectStartDevice.format = ?1"),
		@NamedQuery(name = "findProjectStartDeviceByFormatContaining", query = "select myProjectStartDevice from ProjectStartDevice myProjectStartDevice where myProjectStartDevice.format like ?1"),
		@NamedQuery(name = "findProjectStartDeviceById", query = "select myProjectStartDevice from ProjectStartDevice myProjectStartDevice where myProjectStartDevice.id = ?1"),
		@NamedQuery(name = "findProjectStartDeviceByPrimaryKey", query = "select myProjectStartDevice from ProjectStartDevice myProjectStartDevice where myProjectStartDevice.id = ?1"),
		@NamedQuery(name = "findProjectStartDeviceByPurchasePattern", query = "select myProjectStartDevice from ProjectStartDevice myProjectStartDevice where myProjectStartDevice.purchasePattern = ?1"),
		@NamedQuery(name = "findProjectStartDeviceByPurchasePatternContaining", query = "select myProjectStartDevice from ProjectStartDevice myProjectStartDevice where myProjectStartDevice.purchasePattern like ?1"),
		@NamedQuery(name = "findProjectStartDeviceByUnitPrice", query = "select myProjectStartDevice from ProjectStartDevice myProjectStartDevice where myProjectStartDevice.unitPrice = ?1") })
@Table(name = "project_start_device")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ProjectStartDevice")
public class ProjectStartDevice implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
	Integer id;
	/**
	 * �豸���
	 * 
	 */

	@Column(name = "equipment_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String equipmentName;
	/**
	 * �����ͺ�
	 * 
	 */

	@Column(name = "format", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String format;
	/**
	 * ����
	 * 
	 */

	@Column(name = "amount", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal amount;
	/**
	 * ����
	 * 
	 */

	@Column(name = "unit_price", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal unitPrice;
	/**
	 * �ϼ�
	 * 
	 */

	@Column(name = "collection", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal collection;
	/**
	 * �ɹ���ʽ
	 * 
	 */

	@Column(name = "purchase_pattern", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String purchasePattern;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "project_start_report_id", referencedColumnName = "id") })
	@XmlTransient
	ProjectStartedReport projectStartedReport;

	/**
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * �豸���
	 * 
	 */
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	/**
	 * �豸���
	 * 
	 */
	public String getEquipmentName() {
		return this.equipmentName;
	}

	/**
	 * �����ͺ�
	 * 
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * �����ͺ�
	 * 
	 */
	public String getFormat() {
		return this.format;
	}

	/**
	 * ����
	 * 
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * ����
	 * 
	 */
	public BigDecimal getAmount() {
		return this.amount;
	}

	/**
	 * ����
	 * 
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * ����
	 * 
	 */
	public BigDecimal getUnitPrice() {
		return this.unitPrice;
	}

	/**
	 * �ϼ�
	 * 
	 */
	public void setCollection(BigDecimal collection) {
		this.collection = collection;
	}

	/**
	 * �ϼ�
	 * 
	 */
	public BigDecimal getCollection() {
		return this.collection;
	}

	/**
	 * �ɹ���ʽ
	 * 
	 */
	public void setPurchasePattern(String purchasePattern) {
		this.purchasePattern = purchasePattern;
	}

	/**
	 * �ɹ���ʽ
	 * 
	 */
	public String getPurchasePattern() {
		return this.purchasePattern;
	}

	/**
	 */
	public void setProjectStartedReport(ProjectStartedReport projectStartedReport) {
		this.projectStartedReport = projectStartedReport;
	}

	/**
	 */
	public ProjectStartedReport getProjectStartedReport() {
		return projectStartedReport;
	}

	/**
	 */
	public ProjectStartDevice() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ProjectStartDevice that) {
		setId(that.getId());
		setEquipmentName(that.getEquipmentName());
		setFormat(that.getFormat());
		setAmount(that.getAmount());
		setUnitPrice(that.getUnitPrice());
		setCollection(that.getCollection());
		setPurchasePattern(that.getPurchasePattern());
		setProjectStartedReport(that.getProjectStartedReport());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("equipmentName=[").append(equipmentName).append("] ");
		buffer.append("format=[").append(format).append("] ");
		buffer.append("amount=[").append(amount).append("] ");
		buffer.append("unitPrice=[").append(unitPrice).append("] ");
		buffer.append("collection=[").append(collection).append("] ");
		buffer.append("purchasePattern=[").append(purchasePattern).append("] ");

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
		if (!(obj instanceof ProjectStartDevice))
			return false;
		ProjectStartDevice equalCheck = (ProjectStartDevice) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
