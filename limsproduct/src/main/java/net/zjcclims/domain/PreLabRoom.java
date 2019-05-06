package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.LinkedHashSet;
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
		@NamedQuery(name = "findAllPreLabRooms", query = "select myPreLabRoom from PreLabRoom myPreLabRoom"),
		@NamedQuery(name = "findPreLabRoomById", query = "select myPreLabRoom from PreLabRoom myPreLabRoom where myPreLabRoom.id = ?1"),
		@NamedQuery(name = "findPreLabRoomByPrimaryKey", query = "select myPreLabRoom from PreLabRoom myPreLabRoom where myPreLabRoom.id = ?1"),
		@NamedQuery(name = "findPreLabRoomByRoomName", query = "select myPreLabRoom from PreLabRoom myPreLabRoom where myPreLabRoom.roomName = ?1"),
		@NamedQuery(name = "findPreLabRoomByRoomNameContaining", query = "select myPreLabRoom from PreLabRoom myPreLabRoom where myPreLabRoom.roomName like ?1") })
@Table(name = "pre_lab_room")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "PreLabRoom")
public class PreLabRoom implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ???(?????)
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ????????
	 * 
	 */

	@Column(name = "room_name", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String roomName;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "capa_range", referencedColumnName = "id") })
	@XmlTransient
	PreCapacityRange preCapacityRange;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "room_type", referencedColumnName = "id") })
	@XmlTransient
	PreRoomType preRoomType;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "academy_number", referencedColumnName = "academy_number") })
	@XmlTransient
	SchoolAcademy schoolAcademy;
	/**
	 */
	@ManyToMany(mappedBy = "preLabRooms", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<PreTimetableAppointment> preTimetableAppointments;
	/**
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "pre_m_room_soft", joinColumns = { @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns ={ @JoinColumn(name = "software_id", referencedColumnName = "id", nullable = false, updatable = false) } )
	@XmlElement(name = "", namespace = "")
	Set<net.zjcclims.domain.PreSoftware> preSoftwares;

	/**
	 * ???(?????)
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ???(?????)
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ????????
	 * 
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	/**
	 * ????????
	 * 
	 */
	public String getRoomName() {
		return this.roomName;
	}

	/**
	 */
	public void setPreCapacityRange(PreCapacityRange preCapacityRange) {
		this.preCapacityRange = preCapacityRange;
	}

	/**
	 */
	@JsonIgnore
	public PreCapacityRange getPreCapacityRange() {
		return preCapacityRange;
	}

	/**
	 */
	public void setPreRoomType(PreRoomType preRoomType) {
		this.preRoomType = preRoomType;
	}

	/**
	 */
	@JsonIgnore
	public PreRoomType getPreRoomType() {
		return preRoomType;
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
	public void setPreTimetableAppointments(Set<PreTimetableAppointment> preTimetableAppointments) {
		this.preTimetableAppointments = preTimetableAppointments;
	}

	/**
	 */
	@JsonIgnore
	public Set<PreTimetableAppointment> getPreTimetableAppointments() {
		if (preTimetableAppointments == null) {
			preTimetableAppointments = new LinkedHashSet<PreTimetableAppointment>();
		}
		return preTimetableAppointments;
	}

	/**
	 */
	public void setPreSoftwares(Set<PreSoftware> preSoftwares) {
		this.preSoftwares = preSoftwares;
	}

	/**
	 */
	@JsonIgnore
	public Set<PreSoftware> getPreSoftwares() {
		if (preSoftwares == null) {
			preSoftwares = new LinkedHashSet<net.zjcclims.domain.PreSoftware>();
		}
		return preSoftwares;
	}

	/**
	 */
	public PreLabRoom() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(PreLabRoom that) {
		setId(that.getId());
		setRoomName(that.getRoomName());
		setPreCapacityRange(that.getPreCapacityRange());
		setPreRoomType(that.getPreRoomType());
		setSchoolAcademy(that.getSchoolAcademy());
		setPreTimetableAppointments(new LinkedHashSet<PreTimetableAppointment>(that.getPreTimetableAppointments()));
		setPreSoftwares(new LinkedHashSet<net.zjcclims.domain.PreSoftware>(that.getPreSoftwares()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("roomName=[").append(roomName).append("] ");

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
		if (!(obj instanceof PreLabRoom))
			return false;
		PreLabRoom equalCheck = (PreLabRoom) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
