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
		@NamedQuery(name = "findAllPreTimetableSchedules", query = "select myPreTimetableSchedule from PreTimetableSchedule myPreTimetableSchedule"),
		@NamedQuery(name = "findPreTimetableScheduleByEndClass", query = "select myPreTimetableSchedule from PreTimetableSchedule myPreTimetableSchedule where myPreTimetableSchedule.endClass = ?1"),
		@NamedQuery(name = "findPreTimetableScheduleByEndWday", query = "select myPreTimetableSchedule from PreTimetableSchedule myPreTimetableSchedule where myPreTimetableSchedule.endWday = ?1"),
		@NamedQuery(name = "findPreTimetableScheduleByEndWeek", query = "select myPreTimetableSchedule from PreTimetableSchedule myPreTimetableSchedule where myPreTimetableSchedule.endWeek = ?1"),
		@NamedQuery(name = "findPreTimetableScheduleById", query = "select myPreTimetableSchedule from PreTimetableSchedule myPreTimetableSchedule where myPreTimetableSchedule.id = ?1"),
		@NamedQuery(name = "findPreTimetableScheduleByPrimaryKey", query = "select myPreTimetableSchedule from PreTimetableSchedule myPreTimetableSchedule where myPreTimetableSchedule.id = ?1"),
		@NamedQuery(name = "findPreTimetableScheduleByStartClass", query = "select myPreTimetableSchedule from PreTimetableSchedule myPreTimetableSchedule where myPreTimetableSchedule.startClass = ?1"),
		@NamedQuery(name = "findPreTimetableScheduleByStartWday", query = "select myPreTimetableSchedule from PreTimetableSchedule myPreTimetableSchedule where myPreTimetableSchedule.startWday = ?1"),
		@NamedQuery(name = "findPreTimetableScheduleByStartWeek", query = "select myPreTimetableSchedule from PreTimetableSchedule myPreTimetableSchedule where myPreTimetableSchedule.startWeek = ?1") })
@Table(name = "pre_timetable_schedule")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "PreTimetableSchedule")
public class PreTimetableSchedule implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ??? ????????
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ?????? ?????
	 * 
	 */

	@Column(name = "start_week")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer startWeek;
	/**
	 * ??????
	 * 
	 */

	@Column(name = "end_week")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer endWeek;
	/**
	 * ???????
	 * 
	 */

	@Column(name = "start_wday")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer startWday;
	/**
	 * ????????
	 * 
	 */

	@Column(name = "end_wday")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer endWday;
	/**
	 * ??????
	 * 
	 */

	@Column(name = "start_class")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer startClass;
	/**
	 * ???????
	 * 
	 */

	@Column(name = "end_class")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer endClass;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "appointment_id", referencedColumnName = "id") })
	@XmlTransient
	PreTimetableAppointment preTimetableAppointment;

	/**
	 * ??? ????????
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ??? ????????
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ?????? ?????
	 * 
	 */
	public void setStartWeek(Integer startWeek) {
		this.startWeek = startWeek;
	}

	/**
	 * ?????? ?????
	 * 
	 */
	public Integer getStartWeek() {
		return this.startWeek;
	}

	/**
	 * ??????
	 * 
	 */
	public void setEndWeek(Integer endWeek) {
		this.endWeek = endWeek;
	}

	/**
	 * ??????
	 * 
	 */
	public Integer getEndWeek() {
		return this.endWeek;
	}

	/**
	 * ???????
	 * 
	 */
	public void setStartWday(Integer startWday) {
		this.startWday = startWday;
	}

	/**
	 * ???????
	 * 
	 */
	public Integer getStartWday() {
		return this.startWday;
	}

	/**
	 * ????????
	 * 
	 */
	public void setEndWday(Integer endWday) {
		this.endWday = endWday;
	}

	/**
	 * ????????
	 * 
	 */
	public Integer getEndWday() {
		return this.endWday;
	}

	/**
	 * ??????
	 * 
	 */
	public void setStartClass(Integer startClass) {
		this.startClass = startClass;
	}

	/**
	 * ??????
	 * 
	 */
	public Integer getStartClass() {
		return this.startClass;
	}

	/**
	 * ???????
	 * 
	 */
	public void setEndClass(Integer endClass) {
		this.endClass = endClass;
	}

	/**
	 * ???????
	 * 
	 */
	public Integer getEndClass() {
		return this.endClass;
	}

	/**
	 */
	public void setPreTimetableAppointment(PreTimetableAppointment preTimetableAppointment) {
		this.preTimetableAppointment = preTimetableAppointment;
	}

	/**
	 */
	@JsonIgnore
	public PreTimetableAppointment getPreTimetableAppointment() {
		return preTimetableAppointment;
	}

	/**
	 */
	public PreTimetableSchedule() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(PreTimetableSchedule that) {
		setId(that.getId());
		setStartWeek(that.getStartWeek());
		setEndWeek(that.getEndWeek());
		setStartWday(that.getStartWday());
		setEndWday(that.getEndWday());
		setStartClass(that.getStartClass());
		setEndClass(that.getEndClass());
		setPreTimetableAppointment(that.getPreTimetableAppointment());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("startWeek=[").append(startWeek).append("] ");
		buffer.append("endWeek=[").append(endWeek).append("] ");
		buffer.append("startWday=[").append(startWday).append("] ");
		buffer.append("endWday=[").append(endWday).append("] ");
		buffer.append("startClass=[").append(startClass).append("] ");
		buffer.append("endClass=[").append(endClass).append("] ");

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
		if (!(obj instanceof PreTimetableSchedule))
			return false;
		PreTimetableSchedule equalCheck = (PreTimetableSchedule) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
