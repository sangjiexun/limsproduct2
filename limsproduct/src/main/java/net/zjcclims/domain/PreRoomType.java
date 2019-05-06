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
		@NamedQuery(name = "findAllPreRoomTypes", query = "select myPreRoomType from PreRoomType myPreRoomType"),
		@NamedQuery(name = "findPreRoomTypeById", query = "select myPreRoomType from PreRoomType myPreRoomType where myPreRoomType.id = ?1"),
		@NamedQuery(name = "findPreRoomTypeByPrimaryKey", query = "select myPreRoomType from PreRoomType myPreRoomType where myPreRoomType.id = ?1"),
		@NamedQuery(name = "findPreRoomTypeByRoomType", query = "select myPreRoomType from PreRoomType myPreRoomType where myPreRoomType.roomType = ?1"),
		@NamedQuery(name = "findPreRoomTypeByRoomTypeContaining", query = "select myPreRoomType from PreRoomType myPreRoomType where myPreRoomType.roomType like ?1") })
@Table(name = "pre_room_type")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "PreRoomType")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class PreRoomType implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ???????????????
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ??????????????
	 * 
	 */

	@Column(name = "room_type", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String roomType;

	/**
	 */
	@OneToMany(mappedBy = "preRoomType", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<PreTimetableAppointment> preTimetableAppointments;
	/**
	 */
	@OneToMany(mappedBy = "preRoomType", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<PreLabRoom> preLabRooms;

	/**
	 * ???????????????
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ???????????????
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ??????????????
	 * 
	 */
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	/**
	 * ??????????????
	 * 
	 */
	public String getRoomType() {
		return this.roomType;
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
	public void setPreLabRooms(Set<PreLabRoom> preLabRooms) {
		this.preLabRooms = preLabRooms;
	}

	/**
	 */
	@JsonIgnore
	public Set<PreLabRoom> getPreLabRooms() {
		if (preLabRooms == null) {
			preLabRooms = new LinkedHashSet<PreLabRoom>();
		}
		return preLabRooms;
	}

	/**
	 */
	public PreRoomType() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(PreRoomType that) {
		setId(that.getId());
		setRoomType(that.getRoomType());
		setPreTimetableAppointments(new LinkedHashSet<PreTimetableAppointment>(that.getPreTimetableAppointments()));
		setPreLabRooms(new LinkedHashSet<PreLabRoom>(that.getPreLabRooms()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("roomType=[").append(roomType).append("] ");

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
		if (!(obj instanceof PreRoomType))
			return false;
		PreRoomType equalCheck = (PreRoomType) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
