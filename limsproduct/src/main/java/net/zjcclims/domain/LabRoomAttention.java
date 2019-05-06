package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Calendar;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllLabRoomAttentions", query = "select myLabRoomAttention from LabRoomAttention myLabRoomAttention"),
		@NamedQuery(name = "findLabRoomAttentionByCname", query = "select myLabRoomAttention from LabRoomAttention myLabRoomAttention where myLabRoomAttention.cname = ?1"),
		@NamedQuery(name = "findLabRoomAttentionByCnameContaining", query = "select myLabRoomAttention from LabRoomAttention myLabRoomAttention where myLabRoomAttention.cname like ?1"),
		@NamedQuery(name = "findLabRoomAttentionByDate", query = "select myLabRoomAttention from LabRoomAttention myLabRoomAttention where myLabRoomAttention.date = ?1"),
		@NamedQuery(name = "findLabRoomAttentionByEnable", query = "select myLabRoomAttention from LabRoomAttention myLabRoomAttention where myLabRoomAttention.enable = ?1"),
		@NamedQuery(name = "findLabRoomAttentionById", query = "select myLabRoomAttention from LabRoomAttention myLabRoomAttention where myLabRoomAttention.id = ?1"),
		@NamedQuery(name = "findLabRoomAttentionByLabId", query = "select myLabRoomAttention from LabRoomAttention myLabRoomAttention where myLabRoomAttention.labId = ?1"),
		@NamedQuery(name = "findLabRoomAttentionByPrimaryKey", query = "select myLabRoomAttention from LabRoomAttention myLabRoomAttention where myLabRoomAttention.id = ?1"),
		@NamedQuery(name = "findLabRoomAttentionByUsername", query = "select myLabRoomAttention from LabRoomAttention myLabRoomAttention where myLabRoomAttention.username = ?1"),
		@NamedQuery(name = "findLabRoomAttentionByUsernameContaining", query = "select myLabRoomAttention from LabRoomAttention myLabRoomAttention where myLabRoomAttention.username like ?1") })
@Table(name = "lab_room_attention")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabRoomAttention")
public class LabRoomAttention implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ��������
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * �Ķ��˹���
	 * 
	 */

	@Column(name = "username", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String username;
	/**
	 * �Ķ�������
	 * 
	 */

	@Column(name = "cname", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String cname;
	
	@Column(name = "device_name", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String deviceName;
	
	@Column(name = "device_no", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String deviceNo;
	
	/**
	 * �Ķ�ʵ����
	 * 
	 */

	@Column(name = "lab_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer labId;
	/**
	 * �Ķ�ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar date;
	/**
	 * �Ƿ����
	 * 
	 */

	@Column(name = "enable")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer enable;

	/**
	 * ��������
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ��������
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * �Ķ��˹���
	 * 
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * �Ķ��˹���
	 * 
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * �Ķ�������
	 * 
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}

	/**
	 * �Ķ�������
	 * 
	 */
	public String getCname() {
		return this.cname;
	}

	
	
	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * @param deviceName the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * @return the deviceNo
	 */
	public String getDeviceNo() {
		return deviceNo;
	}

	/**
	 * @param deviceNo the deviceNo to set
	 */
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	/**
	 * �Ķ�ʵ����
	 * 
	 */
	public void setLabId(Integer labId) {
		this.labId = labId;
	}

	/**
	 * �Ķ�ʵ����
	 * 
	 */
	public Integer getLabId() {
		return this.labId;
	}

	/**
	 * �Ķ�ʱ��
	 * 
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}

	/**
	 * �Ķ�ʱ��
	 * 
	 */
	public Calendar getDate() {
		return this.date;
	}

	/**
	 * �Ƿ����
	 * 
	 */
	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	/**
	 * �Ƿ����
	 * 
	 */
	public Integer getEnable() {
		return this.enable;
	}

	/**
	 */
	public LabRoomAttention() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabRoomAttention that) {
		setId(that.getId());
		setUsername(that.getUsername());
		setCname(that.getCname());
		setLabId(that.getLabId());
		setDate(that.getDate());
		setEnable(that.getEnable());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("username=[").append(username).append("] ");
		buffer.append("cname=[").append(cname).append("] ");
		buffer.append("labId=[").append(labId).append("] ");
		buffer.append("date=[").append(date).append("] ");
		buffer.append("enable=[").append(enable).append("] ");

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
		if (!(obj instanceof LabRoomAttention))
			return false;
		LabRoomAttention equalCheck = (LabRoomAttention) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
