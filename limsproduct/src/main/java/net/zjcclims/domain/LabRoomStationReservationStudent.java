package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
		@NamedQuery(name = "findAllLabRoomStationReservationStudents", query = "select myLabRoomStationReservationStudent from LabRoomStationReservationStudent myLabRoomStationReservationStudent"),
		@NamedQuery(name = "findLabRoomStationReservationStudentByCname", query = "select myLabRoomStationReservationStudent from LabRoomStationReservationStudent myLabRoomStationReservationStudent where myLabRoomStationReservationStudent.cname = ?1"),
		@NamedQuery(name = "findLabRoomStationReservationStudentByCnameContaining", query = "select myLabRoomStationReservationStudent from LabRoomStationReservationStudent myLabRoomStationReservationStudent where myLabRoomStationReservationStudent.cname like ?1"),
		@NamedQuery(name = "findLabRoomStationReservationStudentById", query = "select myLabRoomStationReservationStudent from LabRoomStationReservationStudent myLabRoomStationReservationStudent where myLabRoomStationReservationStudent.id = ?1"),
		@NamedQuery(name = "findLabRoomStationReservationStudentByPrimaryKey", query = "select myLabRoomStationReservationStudent from LabRoomStationReservationStudent myLabRoomStationReservationStudent where myLabRoomStationReservationStudent.id = ?1"),
		@NamedQuery(name = "findLabRoomStationReservationStudentByUsername", query = "select myLabRoomStationReservationStudent from LabRoomStationReservationStudent myLabRoomStationReservationStudent where myLabRoomStationReservationStudent.username = ?1"),
		@NamedQuery(name = "findLabRoomStationReservationStudentByUsernameContaining", query = "select myLabRoomStationReservationStudent from LabRoomStationReservationStudent myLabRoomStationReservationStudent where myLabRoomStationReservationStudent.username like ?1") })
@Table(name = "lab_room_station_reservation_student")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabRoomStationReservationStudent")
public class LabRoomStationReservationStudent implements Serializable {
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
	 * ѧ��ţ������
	 * 
	 */

	@Column(name = "username", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String username;
	/**
	 * ѧ�����������ֶΣ�
	 * 
	 */

	@Column(name = "cname", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String cname;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "reservation", referencedColumnName = "id") })
	@XmlTransient
	LabRoomStationReservation labRoomStationReservation;

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
	 * ѧ��ţ������
	 * 
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * ѧ��ţ������
	 * 
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * ѧ�����������ֶΣ�
	 * 
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}

	/**
	 * ѧ�����������ֶΣ�
	 * 
	 */
	public String getCname() {
		return this.cname;
	}

	/**
	 */
	public void setLabRoomStationReservation(LabRoomStationReservation labRoomStationReservation) {
		this.labRoomStationReservation = labRoomStationReservation;
	}

	/**
	 */
	@JsonIgnore
	public LabRoomStationReservation getLabRoomStationReservation() {
		return labRoomStationReservation;
	}

	/**
	 */
	public LabRoomStationReservationStudent() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabRoomStationReservationStudent that) {
		setId(that.getId());
		setUsername(that.getUsername());
		setCname(that.getCname());
		setLabRoomStationReservation(that.getLabRoomStationReservation());
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
		if (!(obj instanceof LabRoomStationReservationStudent))
			return false;
		LabRoomStationReservationStudent equalCheck = (LabRoomStationReservationStudent) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
