package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllProjectAcceptDevices", query = "select myProjectAcceptDevice from ProjectAcceptDevice myProjectAcceptDevice"),
		@NamedQuery(name = "findProjectAcceptDeviceByAmount", query = "select myProjectAcceptDevice from ProjectAcceptDevice myProjectAcceptDevice where myProjectAcceptDevice.amount = ?1"),
		@NamedQuery(name = "findProjectAcceptDeviceByCollection", query = "select myProjectAcceptDevice from ProjectAcceptDevice myProjectAcceptDevice where myProjectAcceptDevice.collection = ?1"),
		@NamedQuery(name = "findProjectAcceptDeviceByEquipmentName", query = "select myProjectAcceptDevice from ProjectAcceptDevice myProjectAcceptDevice where myProjectAcceptDevice.equipmentName = ?1"),
		@NamedQuery(name = "findProjectAcceptDeviceByEquipmentNameContaining", query = "select myProjectAcceptDevice from ProjectAcceptDevice myProjectAcceptDevice where myProjectAcceptDevice.equipmentName like ?1"),
		@NamedQuery(name = "findProjectAcceptDeviceByFormat", query = "select myProjectAcceptDevice from ProjectAcceptDevice myProjectAcceptDevice where myProjectAcceptDevice.format = ?1"),
		@NamedQuery(name = "findProjectAcceptDeviceByFormatContaining", query = "select myProjectAcceptDevice from ProjectAcceptDevice myProjectAcceptDevice where myProjectAcceptDevice.format like ?1"),
		@NamedQuery(name = "findProjectAcceptDeviceById", query = "select myProjectAcceptDevice from ProjectAcceptDevice myProjectAcceptDevice where myProjectAcceptDevice.id = ?1"),
		@NamedQuery(name = "findProjectAcceptDeviceByPrimaryKey", query = "select myProjectAcceptDevice from ProjectAcceptDevice myProjectAcceptDevice where myProjectAcceptDevice.id = ?1"),
		@NamedQuery(name = "findProjectAcceptDeviceByPurchasePattern", query = "select myProjectAcceptDevice from ProjectAcceptDevice myProjectAcceptDevice where myProjectAcceptDevice.purchasePattern = ?1"),
		@NamedQuery(name = "findProjectAcceptDeviceByPurchasePatternContaining", query = "select myProjectAcceptDevice from ProjectAcceptDevice myProjectAcceptDevice where myProjectAcceptDevice.purchasePattern like ?1"),
		@NamedQuery(name = "findProjectAcceptDeviceByUnitPrice", query = "select myProjectAcceptDevice from ProjectAcceptDevice myProjectAcceptDevice where myProjectAcceptDevice.unitPrice = ?1") })
@Table(name = "project_accept_device")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ProjectAcceptDevice")
public class ProjectAcceptDevice implements Serializable {
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
	@JoinColumns({ @JoinColumn(name = "project_accept_id", referencedColumnName = "id") })
	@XmlTransient
	ProjectAcceptanceApplication projectAcceptanceApplication;

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
	public void setProjectAcceptanceApplication(ProjectAcceptanceApplication projectAcceptanceApplication) {
		this.projectAcceptanceApplication = projectAcceptanceApplication;
	}

	/**
	 */
	public ProjectAcceptanceApplication getProjectAcceptanceApplication() {
		return projectAcceptanceApplication;
	}

	/**
	 */
	public ProjectAcceptDevice() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ProjectAcceptDevice that) {
		setId(that.getId());
		setEquipmentName(that.getEquipmentName());
		setFormat(that.getFormat());
		setAmount(that.getAmount());
		setUnitPrice(that.getUnitPrice());
		setCollection(that.getCollection());
		setPurchasePattern(that.getPurchasePattern());
		setProjectAcceptanceApplication(that.getProjectAcceptanceApplication());
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
		if (!(obj instanceof ProjectAcceptDevice))
			return false;
		ProjectAcceptDevice equalCheck = (ProjectAcceptDevice) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
