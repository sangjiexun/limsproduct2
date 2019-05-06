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
		@NamedQuery(name = "findAllPreSoftwares", query = "select myPreSoftware from PreSoftware myPreSoftware"),
		@NamedQuery(name = "findPreSoftwareById", query = "select myPreSoftware from PreSoftware myPreSoftware where myPreSoftware.id = ?1"),
		@NamedQuery(name = "findPreSoftwareByName", query = "select myPreSoftware from PreSoftware myPreSoftware where myPreSoftware.name = ?1"),
		@NamedQuery(name = "findPreSoftwareByNameContaining", query = "select myPreSoftware from PreSoftware myPreSoftware where myPreSoftware.name like ?1"),
		@NamedQuery(name = "findPreSoftwareByPrimaryKey", query = "select myPreSoftware from PreSoftware myPreSoftware where myPreSoftware.id = ?1") })
@Table(name = "pre_software")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "PreSoftware")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class PreSoftware implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ???(???????)
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ???????
	 * 
	 */

	@Column(name = "name", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String name;

	/**
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "pre_m_room_soft", joinColumns = { @JoinColumn(name = "software_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false, updatable = false) })
	@XmlElement(name = "", namespace = "")
	Set<PreLabRoom> preLabRooms;
	/**
	 */
	@OneToMany(mappedBy = "preSoftware", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<PreTimetableAppointment> preTimetableAppointments;

	/**
	 * ???(???????)
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ???(???????)
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ???????
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ???????
	 * 
	 */
	public String getName() {
		return this.name;
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
	public PreSoftware() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(PreSoftware that) {
		setId(that.getId());
		setName(that.getName());
		setPreLabRooms(new LinkedHashSet<PreLabRoom>(that.getPreLabRooms()));
		setPreTimetableAppointments(new LinkedHashSet<PreTimetableAppointment>(that.getPreTimetableAppointments()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("name=[").append(name).append("] ");

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
		if (!(obj instanceof PreSoftware))
			return false;
		PreSoftware equalCheck = (PreSoftware) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
