package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllProjectDevices", query = "select myProjectDevice from ProjectDevice myProjectDevice"),
		@NamedQuery(name = "findProjectDeviceByAmount", query = "select myProjectDevice from ProjectDevice myProjectDevice where myProjectDevice.amount = ?1"),
		@NamedQuery(name = "findProjectDeviceByCollection", query = "select myProjectDevice from ProjectDevice myProjectDevice where myProjectDevice.collection = ?1"),
		@NamedQuery(name = "findProjectDeviceByEquipmentName", query = "select myProjectDevice from ProjectDevice myProjectDevice where myProjectDevice.equipmentName = ?1"),
		@NamedQuery(name = "findProjectDeviceByEquipmentNameContaining", query = "select myProjectDevice from ProjectDevice myProjectDevice where myProjectDevice.equipmentName like ?1"),
		@NamedQuery(name = "findProjectDeviceByFormat", query = "select myProjectDevice from ProjectDevice myProjectDevice where myProjectDevice.format = ?1"),
		@NamedQuery(name = "findProjectDeviceByFormatContaining", query = "select myProjectDevice from ProjectDevice myProjectDevice where myProjectDevice.format like ?1"),
		@NamedQuery(name = "findProjectDeviceById", query = "select myProjectDevice from ProjectDevice myProjectDevice where myProjectDevice.id = ?1"),
		@NamedQuery(name = "findProjectDeviceByPrimaryKey", query = "select myProjectDevice from ProjectDevice myProjectDevice where myProjectDevice.id = ?1"),
		@NamedQuery(name = "findProjectDeviceByPurchasePattern", query = "select myProjectDevice from ProjectDevice myProjectDevice where myProjectDevice.purchasePattern = ?1"),
		@NamedQuery(name = "findProjectDeviceByPurchasePatternContaining", query = "select myProjectDevice from ProjectDevice myProjectDevice where myProjectDevice.purchasePattern like ?1"),
		@NamedQuery(name = "findProjectDeviceByUnitPrice", query = "select myProjectDevice from ProjectDevice myProjectDevice where myProjectDevice.unitPrice = ?1") })
@Table(name = "project_device")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ProjectDevice")
public class ProjectDevice implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ʵ���ҽ�����Ŀ�豸��ϸ��
	 * 
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
	@JoinColumns({ @JoinColumn(name = "arrange_place_id", referencedColumnName = "id") })
	@XmlTransient
	LabRoom labRoom;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "project_started_report_id", referencedColumnName = "id") })
	@XmlTransient
	ProjectStartedReport projectStartedReport;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_construct_app_id", referencedColumnName = "id") })
	@XmlTransient
	LabConstructApp labConstructApp;
	

	/**
	 * ʵ���ҽ�����Ŀ�豸��ϸ��
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ʵ���ҽ�����Ŀ�豸��ϸ��
	 * 
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
	public void setLabRoom(LabRoom labRoom) {
		this.labRoom = labRoom;
	}

	/**
	 */
	public LabRoom getLabRoom() {
		return labRoom;
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
	public void setLabConstructApp(LabConstructApp labConstructApp) {
		this.labConstructApp = labConstructApp;
	}

	/**
	 */
	public LabConstructApp getLabConstructApp() {
		return labConstructApp;
	}

	/**
	 */
	public ProjectDevice() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ProjectDevice that) {
		setId(that.getId());
		setEquipmentName(that.getEquipmentName());
		setFormat(that.getFormat());
		setAmount(that.getAmount());
		setUnitPrice(that.getUnitPrice());
		setCollection(that.getCollection());
		setPurchasePattern(that.getPurchasePattern());
		setLabRoom(that.getLabRoom());
		setProjectStartedReport(that.getProjectStartedReport());
		setLabConstructApp(that.getLabConstructApp());
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
		if (!(obj instanceof ProjectDevice))
			return false;
		ProjectDevice equalCheck = (ProjectDevice) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
