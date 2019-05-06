package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllSmartAgents", query = "select mySmartAgent from SmartAgent mySmartAgent"),
		@NamedQuery(name = "findSmartAgentByCurrIp", query = "select mySmartAgent from SmartAgent mySmartAgent where mySmartAgent.currIp = ?1"),
		@NamedQuery(name = "findSmartAgentByCurrIpContaining", query = "select mySmartAgent from SmartAgent mySmartAgent where mySmartAgent.currIp like ?1"),
		@NamedQuery(name = "findSmartAgentByDbhost", query = "select mySmartAgent from SmartAgent mySmartAgent where mySmartAgent.dbhost = ?1"),
		@NamedQuery(name = "findSmartAgentByDbhostContaining", query = "select mySmartAgent from SmartAgent mySmartAgent where mySmartAgent.dbhost like ?1"),
		@NamedQuery(name = "findSmartAgentByDeviceName", query = "select mySmartAgent from SmartAgent mySmartAgent where mySmartAgent.deviceName = ?1"),
		@NamedQuery(name = "findSmartAgentByDeviceNameContaining", query = "select mySmartAgent from SmartAgent mySmartAgent where mySmartAgent.deviceName like ?1"),
		@NamedQuery(name = "findSmartAgentByDeviceNumber", query = "select mySmartAgent from SmartAgent mySmartAgent where mySmartAgent.deviceNumber = ?1"),
		@NamedQuery(name = "findSmartAgentByDeviceNumberContaining", query = "select mySmartAgent from SmartAgent mySmartAgent where mySmartAgent.deviceNumber like ?1"),
		@NamedQuery(name = "findSmartAgentByLabId", query = "select mySmartAgent from SmartAgent mySmartAgent where mySmartAgent.labId = ?1"),
		@NamedQuery(name = "findSmartAgentByLabName", query = "select mySmartAgent from SmartAgent mySmartAgent where mySmartAgent.labName = ?1"),
		@NamedQuery(name = "findSmartAgentByLabNameContaining", query = "select mySmartAgent from SmartAgent mySmartAgent where mySmartAgent.labName like ?1"),
		@NamedQuery(name = "findSmartAgentByPrimaryKey", query = "select mySmartAgent from SmartAgent mySmartAgent where mySmartAgent.serialNo = ?1"),
		@NamedQuery(name = "findSmartAgentByRemark", query = "select mySmartAgent from SmartAgent mySmartAgent where mySmartAgent.remark = ?1"),
		@NamedQuery(name = "findSmartAgentByRemarkContaining", query = "select mySmartAgent from SmartAgent mySmartAgent where mySmartAgent.remark like ?1"),
		@NamedQuery(name = "findSmartAgentBySerialNo", query = "select mySmartAgent from SmartAgent mySmartAgent where mySmartAgent.serialNo = ?1"),
		@NamedQuery(name = "findSmartAgentBySerialNoContaining", query = "select mySmartAgent from SmartAgent mySmartAgent where mySmartAgent.serialNo like ?1") })
@Table(name = "smart_agent")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "SmartAgent")
public class SmartAgent implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Ψһ��ʶ��
	 * 
	 */

	@Column(name = "serial_no", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	String serialNo;
	/**
	 * ����ip��ַ
	 * 
	 */

	@Column(name = "curr_ip")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String currIp;
	/**
	 * ���ݷ�������ַ
	 * 
	 */

	@Column(name = "dbhost")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String dbhost;
	/**
	 * �豸���
	 * 
	 */

	@Column(name = "device_number", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String deviceNumber;
	/**
	 * �豸����
	 * 
	 */

	@Column(name = "device_name", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String deviceName;
	/**
	 * ����ʵ����id
	 * 
	 */

	@Column(name = "lab_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer labId;
	/**
	 * ����ʵ��������
	 * 
	 */

	@Column(name = "lab_name", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String labName;
	/**
	 * ��ע
	 * 
	 */

	@Column(name = "remark")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String remark;

	/**
	 * Ψһ��ʶ��
	 * 
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * Ψһ��ʶ��
	 * 
	 */
	public String getSerialNo() {
		return this.serialNo;
	}

	/**
	 * ����ip��ַ
	 * 
	 */
	public void setCurrIp(String currIp) {
		this.currIp = currIp;
	}

	/**
	 * ����ip��ַ
	 * 
	 */
	public String getCurrIp() {
		return this.currIp;
	}

	/**
	 * ���ݷ�������ַ
	 * 
	 */
	public void setDbhost(String dbhost) {
		this.dbhost = dbhost;
	}

	/**
	 * ���ݷ�������ַ
	 * 
	 */
	public String getDbhost() {
		return this.dbhost;
	}

	/**
	 * �豸���
	 * 
	 */
	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	/**
	 * �豸���
	 * 
	 */
	public String getDeviceNumber() {
		return this.deviceNumber;
	}

	/**
	 * �豸����
	 * 
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * �豸����
	 * 
	 */
	public String getDeviceName() {
		return this.deviceName;
	}

	/**
	 * ����ʵ����id
	 * 
	 */
	public void setLabId(Integer labId) {
		this.labId = labId;
	}

	/**
	 * ����ʵ����id
	 * 
	 */
	public Integer getLabId() {
		return this.labId;
	}

	/**
	 * ����ʵ��������
	 * 
	 */
	public void setLabName(String labName) {
		this.labName = labName;
	}

	/**
	 * ����ʵ��������
	 * 
	 */
	public String getLabName() {
		return this.labName;
	}

	/**
	 * ��ע
	 * 
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * ��ע
	 * 
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 */
	public SmartAgent() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(SmartAgent that) {
		setSerialNo(that.getSerialNo());
		setCurrIp(that.getCurrIp());
		setDbhost(that.getDbhost());
		setDeviceNumber(that.getDeviceNumber());
		setDeviceName(that.getDeviceName());
		setLabId(that.getLabId());
		setLabName(that.getLabName());
		setRemark(that.getRemark());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("serialNo=[").append(serialNo).append("] ");
		buffer.append("currIp=[").append(currIp).append("] ");
		buffer.append("dbhost=[").append(dbhost).append("] ");
		buffer.append("deviceNumber=[").append(deviceNumber).append("] ");
		buffer.append("deviceName=[").append(deviceName).append("] ");
		buffer.append("labId=[").append(labId).append("] ");
		buffer.append("labName=[").append(labName).append("] ");
		buffer.append("remark=[").append(remark).append("] ");

		return buffer.toString();
	}

	/**
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((serialNo == null) ? 0 : serialNo.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof SmartAgent))
			return false;
		SmartAgent equalCheck = (SmartAgent) obj;
		if ((serialNo == null && equalCheck.serialNo != null) || (serialNo != null && equalCheck.serialNo == null))
			return false;
		if (serialNo != null && !serialNo.equals(equalCheck.serialNo))
			return false;
		return true;
	}
}
