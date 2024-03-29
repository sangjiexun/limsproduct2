package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;



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
		@NamedQuery(name = "findAllTimetableTutorRelateds", query = "select myTimetableTutorRelated from TimetableTutorRelated myTimetableTutorRelated"),
		@NamedQuery(name = "findTimetableTutorRelatedById", query = "select myTimetableTutorRelated from TimetableTutorRelated myTimetableTutorRelated where myTimetableTutorRelated.id = ?1"),
		@NamedQuery(name = "findTimetableTutorRelatedByPrimaryKey", query = "select myTimetableTutorRelated from TimetableTutorRelated myTimetableTutorRelated where myTimetableTutorRelated.id = ?1") })
@Table(name = "timetable_tutor_related")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "TimetableTutorRelated")
public class TimetableTutorRelated implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * �ſκ�����ָ����ʦ
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
	@JoinColumns({ @JoinColumn(name = "tutor", referencedColumnName = "username", nullable = false) })
	@XmlTransient
	User user;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "appointment_id", referencedColumnName = "id") })
	@XmlTransient
	TimetableAppointment timetableAppointment;

	/**
	 * �ſκ�����ָ����ʦ
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * �ſκ�����ָ����ʦ
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 */
	@JsonIgnore
	public User getUser() {
		return user;
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
	public TimetableTutorRelated() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(TimetableTutorRelated that) {
		setId(that.getId());
		setUser(that.getUser());
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
		if (!(obj instanceof TimetableTutorRelated))
			return false;
		TimetableTutorRelated equalCheck = (TimetableTutorRelated) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
