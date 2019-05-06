
package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllAuditSerialNumbers", query = "select myAuditSerialNumber from AuditSerialNumber myAuditSerialNumber"),
		@NamedQuery(name = "findAuditSerialNumberByBusinessId", query = "select myAuditSerialNumber from AuditSerialNumber myAuditSerialNumber where myAuditSerialNumber.businessId = ?1"),
		@NamedQuery(name = "findAuditSerialNumberByBusinessIdContaining", query = "select myAuditSerialNumber from AuditSerialNumber myAuditSerialNumber where myAuditSerialNumber.businessId like ?1"),
		@NamedQuery(name = "findAuditSerialNumberByBusinessType", query = "select myAuditSerialNumber from AuditSerialNumber myAuditSerialNumber where myAuditSerialNumber.businessType = ?1"),
		@NamedQuery(name = "findAuditSerialNumberByBusinessTypeContaining", query = "select myAuditSerialNumber from AuditSerialNumber myAuditSerialNumber where myAuditSerialNumber.businessType like ?1"),
		@NamedQuery(name = "findAuditSerialNumberByEnable", query = "select myAuditSerialNumber from AuditSerialNumber myAuditSerialNumber where myAuditSerialNumber.enable = ?1"),
		@NamedQuery(name = "findAuditSerialNumberByPrimaryKey", query = "select myAuditSerialNumber from AuditSerialNumber myAuditSerialNumber where myAuditSerialNumber.uuid = ?1"),
		@NamedQuery(name = "findAuditSerialNumberByUuid", query = "select myAuditSerialNumber from AuditSerialNumber myAuditSerialNumber where myAuditSerialNumber.uuid = ?1"),
		@NamedQuery(name = "findAuditSerialNumberByUuidContaining", query = "select myAuditSerialNumber from AuditSerialNumber myAuditSerialNumber where myAuditSerialNumber.uuid like ?1") })

@Table(name = "audit_serial_number")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "AuditSerialNumber")

public class AuditSerialNumber implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	* ��ˮ��
	* 
	 */

	@Column(name = "uuid", nullable = false)
	@Basic(fetch = FetchType.EAGER)

	@Id
	@XmlElement
	String uuid;
	/**
	* ���ҵ��id
	* 
	 */

	@Column(name = "business_id")
	@Basic(fetch = FetchType.EAGER)

	@XmlElement
	String businessId;
	/**
	* ҵ������
	* 
	 */

	@Column(name = "business_type")
	@Basic(fetch = FetchType.EAGER)

	@XmlElement
	String businessType;
	/**
	* �Ƿ�����
	* 
	 */

	@Column(name = "enable")
	@Basic(fetch = FetchType.EAGER)

	@XmlElement
	Boolean enable;

	/**
	* ��ˮ��
	* 
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	* ��ˮ��
	* 
	 */
	public String getUuid() {
		return this.uuid;
	}

	/**
	* ���ҵ��id
	* 
	 */
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	/**
	* ���ҵ��id
	* 
	 */
	public String getBusinessId() {
		return this.businessId;
	}

	/**
	* ҵ������
	* 
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	* ҵ������
	* 
	 */
	public String getBusinessType() {
		return this.businessType;
	}

	/**
	* �Ƿ�����
	* 
	 */
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	/**
	* �Ƿ�����
	* 
	 */
	public Boolean getEnable() {
		return this.enable;
	}

	/**
	 */
	public AuditSerialNumber() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(AuditSerialNumber that) {
		setUuid(that.getUuid());
		setBusinessId(that.getBusinessId());
		setBusinessType(that.getBusinessType());
		setEnable(that.getEnable());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("uuid=[").append(uuid).append("] ");
		buffer.append("businessId=[").append(businessId).append("] ");
		buffer.append("businessType=[").append(businessType).append("] ");
		buffer.append("enable=[").append(enable).append("] ");

		return buffer.toString();
	}

	/**
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((uuid == null) ? 0 : uuid.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof AuditSerialNumber))
			return false;
		AuditSerialNumber equalCheck = (AuditSerialNumber) obj;
		if ((uuid == null && equalCheck.uuid != null) || (uuid != null && equalCheck.uuid == null))
			return false;
		if (uuid != null && !uuid.equals(equalCheck.uuid))
			return false;
		return true;
	}
}
