package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Set;

import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllLabWorkRooms", query = "select myLabWorkRoom from LabWorkRoom myLabWorkRoom"),
		@NamedQuery(name = "findLabWorkRoomById", query = "select myLabWorkRoom from LabWorkRoom myLabWorkRoom where myLabWorkRoom.id = ?1"),
		@NamedQuery(name = "findLabWorkRoomByPrimaryKey", query = "select myLabWorkRoom from LabWorkRoom myLabWorkRoom where myLabWorkRoom.id = ?1"),
		@NamedQuery(name = "findLabWorkRoomByWorkRoomAddress", query = "select myLabWorkRoom from LabWorkRoom myLabWorkRoom where myLabWorkRoom.workRoomAddress = ?1"),
		@NamedQuery(name = "findLabWorkRoomByWorkRoomAddressContaining", query = "select myLabWorkRoom from LabWorkRoom myLabWorkRoom where myLabWorkRoom.workRoomAddress like ?1"),
		@NamedQuery(name = "findLabWorkRoomByWorkRoomName", query = "select myLabWorkRoom from LabWorkRoom myLabWorkRoom where myLabWorkRoom.workRoomName = ?1"),
		@NamedQuery(name = "findLabWorkRoomByWorkRoomNameContaining", query = "select myLabWorkRoom from LabWorkRoom myLabWorkRoom where myLabWorkRoom.workRoomName like ?1"),
		@NamedQuery(name = "findLabWorkRoomByWorkRoomNumber", query = "select myLabWorkRoom from LabWorkRoom myLabWorkRoom where myLabWorkRoom.workRoomNumber = ?1"),
		@NamedQuery(name = "findLabWorkRoomByWorkRoomNumberContaining", query = "select myLabWorkRoom from LabWorkRoom myLabWorkRoom where myLabWorkRoom.workRoomNumber like ?1") })
@Table(name = "lab_work_room")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabWorkRoom")
public class LabWorkRoom implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ����
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * �����ұ��
	 * 
	 */

	@Column(name = "work_room_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String workRoomNumber;
	/**
	 * ���������
	 * 
	 */

	@Column(name = "work_room_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String workRoomName;
	/**
	 * �����ҵص�
	 * 
	 */

	@Column(name = "work_room_address")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String workRoomAddress;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "academy_number", referencedColumnName = "academy_number") })
	@XmlTransient
	SchoolAcademy schoolAcademy;

	/**
	 * ����
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ����
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * �����ұ��
	 * 
	 */
	public void setWorkRoomNumber(String workRoomNumber) {
		this.workRoomNumber = workRoomNumber;
	}

	/**
	 * �����ұ��
	 * 
	 */
	public String getWorkRoomNumber() {
		return this.workRoomNumber;
	}

	/**
	 * ���������
	 * 
	 */
	public void setWorkRoomName(String workRoomName) {
		this.workRoomName = workRoomName;
	}

	/**
	 * ���������
	 * 
	 */
	public String getWorkRoomName() {
		return this.workRoomName;
	}

	/**
	 * �����ҵص�
	 * 
	 */
	public void setWorkRoomAddress(String workRoomAddress) {
		this.workRoomAddress = workRoomAddress;
	}

	/**
	 * �����ҵص�
	 * 
	 */
	public String getWorkRoomAddress() {
		return this.workRoomAddress;
	}

	/**
	 */
	public void setSchoolAcademy(SchoolAcademy schoolAcademy) {
		this.schoolAcademy = schoolAcademy;
	}

	/**
	 */
	@JsonIgnore
	public SchoolAcademy getSchoolAcademy() {
		return schoolAcademy;
	}

	/**
	 */
	public LabWorkRoom() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabWorkRoom that) {
		setId(that.getId());
		setWorkRoomNumber(that.getWorkRoomNumber());
		setWorkRoomName(that.getWorkRoomName());
		setWorkRoomAddress(that.getWorkRoomAddress());
		setSchoolAcademy(that.getSchoolAcademy());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("workRoomNumber=[").append(workRoomNumber).append("] ");
		buffer.append("workRoomName=[").append(workRoomName).append("] ");
		buffer.append("workRoomAddress=[").append(workRoomAddress).append("] ");

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
		if (!(obj instanceof LabWorkRoom))
			return false;
		LabWorkRoom equalCheck = (LabWorkRoom) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
