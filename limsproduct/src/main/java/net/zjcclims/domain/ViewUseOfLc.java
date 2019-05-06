package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Calendar;

import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllViewUseOfLcs", query = "select myViewUseOfLc from ViewUseOfLc myViewUseOfLc"),
		@NamedQuery(name = "findViewUseOfLcByBegintime", query = "select myViewUseOfLc from ViewUseOfLc myViewUseOfLc where myViewUseOfLc.begintime = ?1"),
		@NamedQuery(name = "findViewUseOfLcByDeviceNumber", query = "select myViewUseOfLc from ViewUseOfLc myViewUseOfLc where myViewUseOfLc.deviceNumber = ?1"),
		@NamedQuery(name = "findViewUseOfLcByEndtime", query = "select myViewUseOfLc from ViewUseOfLc myViewUseOfLc where myViewUseOfLc.endtime = ?1"),
		@NamedQuery(name = "findViewUseOfLcById", query = "select myViewUseOfLc from ViewUseOfLc myViewUseOfLc where myViewUseOfLc.id = ?1"),
		@NamedQuery(name = "findViewUseOfLcByLabRoomId", query = "select myViewUseOfLc from ViewUseOfLc myViewUseOfLc where myViewUseOfLc.labRoomId = ?1"),
		@NamedQuery(name = "findViewUseOfLcByLabRoomName", query = "select myViewUseOfLc from ViewUseOfLc myViewUseOfLc where myViewUseOfLc.labRoomName = ?1"),
		@NamedQuery(name = "findViewUseOfLcByLabRoomNameContaining", query = "select myViewUseOfLc from ViewUseOfLc myViewUseOfLc where myViewUseOfLc.labRoomName like ?1"),
		@NamedQuery(name = "findViewUseOfLcByMachinename", query = "select myViewUseOfLc from ViewUseOfLc myViewUseOfLc where myViewUseOfLc.machinename = ?1"),
		@NamedQuery(name = "findViewUseOfLcByMachinenameContaining", query = "select myViewUseOfLc from ViewUseOfLc myViewUseOfLc where myViewUseOfLc.machinename like ?1"),
		@NamedQuery(name = "findViewUseOfLcByPrimaryKey", query = "select myViewUseOfLc from ViewUseOfLc myViewUseOfLc where myViewUseOfLc.id = ?1"),
		@NamedQuery(name = "findViewUseOfLcByUsername", query = "select myViewUseOfLc from ViewUseOfLc myViewUseOfLc where myViewUseOfLc.username = ?1"),
		@NamedQuery(name = "findViewUseOfLcByUsernameContaining", query = "select myViewUseOfLc from ViewUseOfLc myViewUseOfLc where myViewUseOfLc.username like ?1") })
@Table(name = "view_use_of_lc")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ViewUseOfLc")
public class ViewUseOfLc implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	Integer id;
	/**
	 * �������
	 * 
	 */

	@Column(name = "device_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer deviceNumber;
	/**
	 * �������
	 * 
	 */

	@Column(name = "machinename")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String machinename;
	/**
	 * ʹ����
	 * 
	 */

	@Column(name = "username")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String username;
	/**
	 * ��ʼʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "begintime")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar begintime;
	/**
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "endtime")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar endtime;
	/**
	 * ���ڻ�
	 * 
	 */

	@Column(name = "lab_room_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String labRoomName;
	/**
	 * ���ڻ�id
	 * 
	 */

	@Column(name = "lab_room_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer labRoomId;

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
	 * �������
	 * 
	 */
	public void setDeviceNumber(Integer deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	/**
	 * �������
	 * 
	 */
	public Integer getDeviceNumber() {
		return this.deviceNumber;
	}

	/**
	 * �������
	 * 
	 */
	public void setMachinename(String machinename) {
		this.machinename = machinename;
	}

	/**
	 * �������
	 * 
	 */
	public String getMachinename() {
		return this.machinename;
	}

	/**
	 * ʹ����
	 * 
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * ʹ����
	 * 
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * ��ʼʱ��
	 * 
	 */
	public void setBegintime(Calendar begintime) {
		this.begintime = begintime;
	}

	/**
	 * ��ʼʱ��
	 * 
	 */
	public Calendar getBegintime() {
		return this.begintime;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public void setEndtime(Calendar endtime) {
		this.endtime = endtime;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public Calendar getEndtime() {
		return this.endtime;
	}

	/**
	 * ���ڻ�
	 * 
	 */
	public void setLabRoomName(String labRoomName) {
		this.labRoomName = labRoomName;
	}

	/**
	 * ���ڻ�
	 * 
	 */
	public String getLabRoomName() {
		return this.labRoomName;
	}

	/**
	 * ���ڻ�id
	 * 
	 */
	public void setLabRoomId(Integer labRoomId) {
		this.labRoomId = labRoomId;
	}

	/**
	 * ���ڻ�id
	 * 
	 */
	public Integer getLabRoomId() {
		return this.labRoomId;
	}

	/**
	 */
	public ViewUseOfLc() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ViewUseOfLc that) {
		setId(that.getId());
		setDeviceNumber(that.getDeviceNumber());
		setMachinename(that.getMachinename());
		setUsername(that.getUsername());
		setBegintime(that.getBegintime());
		setEndtime(that.getEndtime());
		setLabRoomName(that.getLabRoomName());
		setLabRoomId(that.getLabRoomId());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("deviceNumber=[").append(deviceNumber).append("] ");
		buffer.append("machinename=[").append(machinename).append("] ");
		buffer.append("username=[").append(username).append("] ");
		buffer.append("begintime=[").append(begintime).append("] ");
		buffer.append("endtime=[").append(endtime).append("] ");
		buffer.append("labRoomName=[").append(labRoomName).append("] ");
		buffer.append("labRoomId=[").append(labRoomId).append("] ");

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
		if (!(obj instanceof ViewUseOfLc))
			return false;
		ViewUseOfLc equalCheck = (ViewUseOfLc) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
