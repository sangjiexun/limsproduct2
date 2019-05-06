package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllSystemRooms", query = "select mySystemRoom from SystemRoom mySystemRoom"),
		@NamedQuery(name = "findSystemRoomByCreatedAt", query = "select mySystemRoom from SystemRoom mySystemRoom where mySystemRoom.createdAt = ?1"),
		@NamedQuery(name = "findSystemRoomByDepartmentNumber", query = "select mySystemRoom from SystemRoom mySystemRoom where mySystemRoom.departmentNumber = ?1"),
		@NamedQuery(name = "findSystemRoomByDepartmentNumberContaining", query = "select mySystemRoom from SystemRoom mySystemRoom where mySystemRoom.departmentNumber like ?1"),
		@NamedQuery(name = "findSystemRoomByPrimaryKey", query = "select mySystemRoom from SystemRoom mySystemRoom where mySystemRoom.roomNumber = ?1"),
		@NamedQuery(name = "findSystemRoomByRoomName", query = "select mySystemRoom from SystemRoom mySystemRoom where mySystemRoom.roomName = ?1"),
		@NamedQuery(name = "findSystemRoomByRoomNameContaining", query = "select mySystemRoom from SystemRoom mySystemRoom where mySystemRoom.roomName like ?1"),
		@NamedQuery(name = "findSystemRoomByRoomNo", query = "select mySystemRoom from SystemRoom mySystemRoom where mySystemRoom.roomNo = ?1"),
		@NamedQuery(name = "findSystemRoomByRoomNoContaining", query = "select mySystemRoom from SystemRoom mySystemRoom where mySystemRoom.roomNo like ?1"),
		@NamedQuery(name = "findSystemRoomByRoomNumber", query = "select mySystemRoom from SystemRoom mySystemRoom where mySystemRoom.roomNumber = ?1"),
		@NamedQuery(name = "findSystemRoomByRoomNumberContaining", query = "select mySystemRoom from SystemRoom mySystemRoom where mySystemRoom.roomNumber like ?1"),
		@NamedQuery(name = "findSystemRoomByRoomUse", query = "select mySystemRoom from SystemRoom mySystemRoom where mySystemRoom.roomUse = ?1"),
		@NamedQuery(name = "findSystemRoomByRoomUseContaining", query = "select mySystemRoom from SystemRoom mySystemRoom where mySystemRoom.roomUse like ?1"),
		@NamedQuery(name = "findSystemRoomByUpdatedAt", query = "select mySystemRoom from SystemRoom mySystemRoom where mySystemRoom.updatedAt = ?1") })
@Table(name = "system_room")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "SystemRoom")
public class SystemRoomVisual implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ������
	 *
	 */

	@Column(name = "room_number", length = 40, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	String roomNumber;
	/**
	 * �����
	 *
	 */

	@Column(name = "room_no", length = 10)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String roomNo;
	/**
	 * �������
	 *
	 */

	@Column(name = "room_name", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String roomName;
	/**
	 * ������;
	 *
	 */

	@Column(name = "room_use", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String roomUse;
	/**
	 * �������
	 *
	 */

	@Column(name = "department_number", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String departmentNumber;
	/**
	 * ����ʱ��
	 *
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createdAt;
	/**
	 * ����ʱ��
	 *
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar updatedAt;

	@Column(name = "floor_no", length = 11)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer floorNo;

	@Column(name = "device_no", length = 11)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer deviceNo;

	@Column(name = "campus_name", length = 11)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String campusName;

	@Column(name = "build_name", length = 11)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String buildName;

	@Column(name = "build_number", length = 11)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String buildNumber;



	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "build_number", referencedColumnName = "build_number") })
	@XmlTransient
	SystemBuild systemBuild;

	/**
	 */
	@OneToMany(mappedBy = "systemRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<LabRoom> labRooms;

	public Integer getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(Integer deviceNo) {
		this.deviceNo = deviceNo;
	}

	public Set<LabRoom> getLabRooms() {
		return labRooms;
	}

	public void setLabRooms(Set<LabRoom> labRooms) {
		this.labRooms = labRooms;
	}

	public String getCampusName() {
		return campusName;
	}

	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	/**
	 * ������
	 *
	 */
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	/**
	 * ������
	 *
	 */
	public String getRoomNumber() {
		return this.roomNumber;
	}

	/**
	 * �����
	 *
	 */
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	/**
	 * �����
	 *
	 */
	public String getRoomNo() {
		return this.roomNo;
	}

	/**
	 * �������
	 *
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	/**
	 * �������
	 *
	 */
	public String getRoomName() {
		return this.roomName;
	}

	/**
	 * ������;
	 *
	 */
	public void setRoomUse(String roomUse) {
		this.roomUse = roomUse;
	}

	/**
	 * ������;
	 *
	 */
	public String getRoomUse() {
		return this.roomUse;
	}

	/**
	 * �������
	 *
	 */
	public void setDepartmentNumber(String departmentNumber) {
		this.departmentNumber = departmentNumber;
	}

	/**
	 * �������
	 *
	 */
	public String getDepartmentNumber() {
		return this.departmentNumber;
	}

	/**
	 * ����ʱ��
	 *
	 */
	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * ����ʱ��
	 *
	 */
	public Calendar getCreatedAt() {
		return this.createdAt;
	}

	/**
	 * ����ʱ��
	 *
	 */
	public void setUpdatedAt(Calendar updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * ����ʱ��
	 *
	 */
	public Calendar getUpdatedAt() {
		return this.updatedAt;
	}

	public void setFloorNo(Integer floorNo) {
		this.floorNo = floorNo;
	}

	/**
	 * ������;
	 *
	 */
	public Integer getFloorNo() {
		return this.floorNo;
	}

	/**
	 */
	public void setSystemBuild(SystemBuild systemBuild) {
		this.systemBuild = systemBuild;
	}

	/**
	 */
	@JsonIgnore
	public SystemBuild getSystemBuild() {
		return systemBuild;
	}

	/**
	 */
	public SystemRoomVisual() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(SystemRoomVisual that) {
		setRoomNumber(that.getRoomNumber());
		setRoomNo(that.getRoomNo());
		setRoomName(that.getRoomName());
		setRoomUse(that.getRoomUse());
		setDepartmentNumber(that.getDepartmentNumber());
		setCreatedAt(that.getCreatedAt());
		setUpdatedAt(that.getUpdatedAt());
		setSystemBuild(that.getSystemBuild());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("roomNumber=[").append(roomNumber).append("] ");
		buffer.append("roomNo=[").append(roomNo).append("] ");
		buffer.append("roomName=[").append(roomName).append("] ");
		buffer.append("roomUse=[").append(roomUse).append("] ");
		buffer.append("departmentNumber=[").append(departmentNumber).append("] ");
		buffer.append("createdAt=[").append(createdAt).append("] ");
		buffer.append("updatedAt=[").append(updatedAt).append("] ");

		return buffer.toString();
	}

	/**
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((roomNumber == null) ? 0 : roomNumber.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof SystemRoomVisual))
			return false;
		SystemRoomVisual equalCheck = (SystemRoomVisual) obj;
		if ((roomNumber == null && equalCheck.roomNumber != null) || (roomNumber != null && equalCheck.roomNumber == null))
			return false;
		if (roomNumber != null && !roomNumber.equals(equalCheck.roomNumber))
			return false;
		return true;
	}
}
