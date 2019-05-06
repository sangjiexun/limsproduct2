package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Calendar;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllRemoteOpenDoors", query = "select myRemoteOpenDoor from RemoteOpenDoor myRemoteOpenDoor"),
		@NamedQuery(name = "findRemoteOpenDoorByControllerId", query = "select myRemoteOpenDoor from RemoteOpenDoor myRemoteOpenDoor where myRemoteOpenDoor.controllerId = ?1"),
		@NamedQuery(name = "findRemoteOpenDoorByControllerIdContaining", query = "select myRemoteOpenDoor from RemoteOpenDoor myRemoteOpenDoor where myRemoteOpenDoor.controllerId like ?1"),
		@NamedQuery(name = "findRemoteOpenDoorByCreateTime", query = "select myRemoteOpenDoor from RemoteOpenDoor myRemoteOpenDoor where myRemoteOpenDoor.createTime = ?1"),
		@NamedQuery(name = "findRemoteOpenDoorByCreaterName", query = "select myRemoteOpenDoor from RemoteOpenDoor myRemoteOpenDoor where myRemoteOpenDoor.createrName = ?1"),
		@NamedQuery(name = "findRemoteOpenDoorByCreaterNameContaining", query = "select myRemoteOpenDoor from RemoteOpenDoor myRemoteOpenDoor where myRemoteOpenDoor.createrName like ?1"),
		@NamedQuery(name = "findRemoteOpenDoorByCreaterUsername", query = "select myRemoteOpenDoor from RemoteOpenDoor myRemoteOpenDoor where myRemoteOpenDoor.createrUsername = ?1"),
		@NamedQuery(name = "findRemoteOpenDoorByCreaterUsernameContaining", query = "select myRemoteOpenDoor from RemoteOpenDoor myRemoteOpenDoor where myRemoteOpenDoor.createrUsername like ?1"),
		@NamedQuery(name = "findRemoteOpenDoorByDeviceType", query = "select myRemoteOpenDoor from RemoteOpenDoor myRemoteOpenDoor where myRemoteOpenDoor.deviceType = ?1"),
		@NamedQuery(name = "findRemoteOpenDoorByDoorNo", query = "select myRemoteOpenDoor from RemoteOpenDoor myRemoteOpenDoor where myRemoteOpenDoor.doorNo = ?1"),
		@NamedQuery(name = "findRemoteOpenDoorByDoorNoContaining", query = "select myRemoteOpenDoor from RemoteOpenDoor myRemoteOpenDoor where myRemoteOpenDoor.doorNo like ?1"),
		@NamedQuery(name = "findRemoteOpenDoorById", query = "select myRemoteOpenDoor from RemoteOpenDoor myRemoteOpenDoor where myRemoteOpenDoor.id = ?1"),
		@NamedQuery(name = "findRemoteOpenDoorByLabRoomId", query = "select myRemoteOpenDoor from RemoteOpenDoor myRemoteOpenDoor where myRemoteOpenDoor.labRoomId = ?1"),
		@NamedQuery(name = "findRemoteOpenDoorByLabRoomName", query = "select myRemoteOpenDoor from RemoteOpenDoor myRemoteOpenDoor where myRemoteOpenDoor.labRoomName = ?1"),
		@NamedQuery(name = "findRemoteOpenDoorByLabRoomNameContaining", query = "select myRemoteOpenDoor from RemoteOpenDoor myRemoteOpenDoor where myRemoteOpenDoor.labRoomName like ?1"),
		@NamedQuery(name = "findRemoteOpenDoorByPrimaryKey", query = "select myRemoteOpenDoor from RemoteOpenDoor myRemoteOpenDoor where myRemoteOpenDoor.id = ?1"),
		@NamedQuery(name = "findRemoteOpenDoorByRemoteAction", query = "select myRemoteOpenDoor from RemoteOpenDoor myRemoteOpenDoor where myRemoteOpenDoor.remoteAction = ?1"),
		@NamedQuery(name = "findRemoteOpenDoorByStatus", query = "select myRemoteOpenDoor from RemoteOpenDoor myRemoteOpenDoor where myRemoteOpenDoor.status = ?1"),
		@NamedQuery(name = "findRemoteOpenDoorByUpdateTime", query = "select myRemoteOpenDoor from RemoteOpenDoor myRemoteOpenDoor where myRemoteOpenDoor.updateTime = ?1") })
@Table(name = "remote_open_door")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "RemoteOpenDoor")
public class RemoteOpenDoor implements Serializable {
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
	 * ʵѵ��id
	 * 
	 */

	@Column(name = "lab_room_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer labRoomId;
	/**
	 * ʵѵ�����
	 * 
	 */

	@Column(name = "lab_room_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String labRoomName;
	/**
	 * �Ž������ID
	 * 
	 */

	@Column(name = "controllerId")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String controllerId;
	/**
	 * ��ID
	 * 
	 */

	@Column(name = "doorNo")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String doorNo;
	/**
	 * �豸���Ĭ��0
	 * 
	 */

	@Column(name = "deviceType")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer deviceType;
	/**
	 * Զ�̶�����Ĭ��1
	 * 
	 */

	@Column(name = "remoteAction")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer remoteAction;
	/**
	 * �����˹��ţ��Զ���ȡ��ǰ��¼�˹��ţ�
	 * 
	 */

	@Column(name = "creater_username")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String createrUsername;
	/**
	 * �����������Զ���ȡ��ǰ��¼������
	 * 
	 */

	@Column(name = "creater_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String createrName;
	/**
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createTime;
	/**
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar updateTime;
	/**
	 * ������״̬��0-��ʼֵ��1-�ɹ���2-ʧ�ܣ�3-��ʱ����ʱʱ��Ĭ��Ϊ10���ӣ�
	 * 
	 */

	@Column(name = "status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer status;

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
	 * ʵѵ��id
	 * 
	 */
	public void setLabRoomId(Integer labRoomId) {
		this.labRoomId = labRoomId;
	}

	/**
	 * ʵѵ��id
	 * 
	 */
	public Integer getLabRoomId() {
		return this.labRoomId;
	}

	/**
	 * ʵѵ�����
	 * 
	 */
	public void setLabRoomName(String labRoomName) {
		this.labRoomName = labRoomName;
	}

	/**
	 * ʵѵ�����
	 * 
	 */
	public String getLabRoomName() {
		return this.labRoomName;
	}

	/**
	 * �Ž������ID
	 * 
	 */
	public void setControllerId(String controllerId) {
		this.controllerId = controllerId;
	}

	/**
	 * �Ž������ID
	 * 
	 */
	public String getControllerId() {
		return this.controllerId;
	}

	/**
	 * ��ID
	 * 
	 */
	public void setDoorNo(String doorNo) {
		this.doorNo = doorNo;
	}

	/**
	 * ��ID
	 * 
	 */
	public String getDoorNo() {
		return this.doorNo;
	}

	/**
	 * �豸���Ĭ��0
	 * 
	 */
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * �豸���Ĭ��0
	 * 
	 */
	public Integer getDeviceType() {
		return this.deviceType;
	}

	/**
	 * Զ�̶�����Ĭ��1
	 * 
	 */
	public void setRemoteAction(Integer remoteAction) {
		this.remoteAction = remoteAction;
	}

	/**
	 * Զ�̶�����Ĭ��1
	 * 
	 */
	public Integer getRemoteAction() {
		return this.remoteAction;
	}

	/**
	 * �����˹��ţ��Զ���ȡ��ǰ��¼�˹��ţ�
	 * 
	 */
	public void setCreaterUsername(String createrUsername) {
		this.createrUsername = createrUsername;
	}

	/**
	 * �����˹��ţ��Զ���ȡ��ǰ��¼�˹��ţ�
	 * 
	 */
	public String getCreaterUsername() {
		return this.createrUsername;
	}

	/**
	 * �����������Զ���ȡ��ǰ��¼������
	 * 
	 */
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	/**
	 * �����������Զ���ȡ��ǰ��¼������
	 * 
	 */
	public String getCreaterName() {
		return this.createrName;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public Calendar getCreateTime() {
		return this.createTime;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public void setUpdateTime(Calendar updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public Calendar getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * ������״̬��0-��ʼֵ��1-�ɹ���2-ʧ�ܣ�3-��ʱ����ʱʱ��Ĭ��Ϊ10���ӣ�
	 * 
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * ������״̬��0-��ʼֵ��1-�ɹ���2-ʧ�ܣ�3-��ʱ����ʱʱ��Ĭ��Ϊ10���ӣ�
	 * 
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	 */
	public RemoteOpenDoor() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(RemoteOpenDoor that) {
		setId(that.getId());
		setLabRoomId(that.getLabRoomId());
		setLabRoomName(that.getLabRoomName());
		setControllerId(that.getControllerId());
		setDoorNo(that.getDoorNo());
		setDeviceType(that.getDeviceType());
		setRemoteAction(that.getRemoteAction());
		setCreaterUsername(that.getCreaterUsername());
		setCreaterName(that.getCreaterName());
		setCreateTime(that.getCreateTime());
		setUpdateTime(that.getUpdateTime());
		setStatus(that.getStatus());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("labRoomId=[").append(labRoomId).append("] ");
		buffer.append("labRoomName=[").append(labRoomName).append("] ");
		buffer.append("controllerId=[").append(controllerId).append("] ");
		buffer.append("doorNo=[").append(doorNo).append("] ");
		buffer.append("deviceType=[").append(deviceType).append("] ");
		buffer.append("remoteAction=[").append(remoteAction).append("] ");
		buffer.append("createrUsername=[").append(createrUsername).append("] ");
		buffer.append("createrName=[").append(createrName).append("] ");
		buffer.append("createTime=[").append(createTime).append("] ");
		buffer.append("updateTime=[").append(updateTime).append("] ");
		buffer.append("status=[").append(status).append("] ");

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
		if (!(obj instanceof RemoteOpenDoor))
			return false;
		RemoteOpenDoor equalCheck = (RemoteOpenDoor) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
