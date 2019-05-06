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
		@NamedQuery(name = "findAllTimetableSoftwareRelateds", query = "select myTimetableSoftwareRelated from TimetableSoftwareRelated myTimetableSoftwareRelated"),
		@NamedQuery(name = "findTimetableSoftwareRelatedById", query = "select myTimetableSoftwareRelated from TimetableSoftwareRelated myTimetableSoftwareRelated where myTimetableSoftwareRelated.id = ?1"),
		@NamedQuery(name = "findTimetableSoftwareRelatedByPrimaryKey", query = "select myTimetableSoftwareRelated from TimetableSoftwareRelated myTimetableSoftwareRelated where myTimetableSoftwareRelated.id = ?1") })
@Table(name = "timetable_software_related")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "TimetableSoftwareRelated")
public class TimetableSoftwareRelated implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * �ſΡ�ԤԼ֮��������������
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
	Integer id;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "software_id", referencedColumnName = "id") })
	@XmlTransient
	Software software;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "appointment_id", referencedColumnName = "id") })
	@XmlTransient
	TimetableAppointment timetableAppointment;

	/**
	 * �ſΡ�ԤԼ֮��������������
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * �ſΡ�ԤԼ֮��������������
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 */
	public void setSoftware(Software software) {
		this.software = software;
	}

	/**
	 */
	@JsonIgnore
	public Software getSoftware() {
		return software;
	}

	/**
	 */
	public void setTimetableAppointment(TimetableAppointment timetableAppointment) {
		this.timetableAppointment = timetableAppointment;
	}

	/**
	 */
	@JsonIgnore
	public TimetableAppointment getTimetableAppointment() {
		return timetableAppointment;
	}

	/**
	 */
	public TimetableSoftwareRelated() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(TimetableSoftwareRelated that) {
		setId(that.getId());
		setSoftware(that.getSoftware());
		setTimetableAppointment(that.getTimetableAppointment());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");

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
		if (!(obj instanceof TimetableSoftwareRelated))
			return false;
		TimetableSoftwareRelated equalCheck = (TimetableSoftwareRelated) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
