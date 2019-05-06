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
		@NamedQuery(name = "findAllPreTimetableAppointments", query = "select myPreTimetableAppointment from PreTimetableAppointment myPreTimetableAppointment"),
		@NamedQuery(name = "findPreTimetableAppointmentById", query = "select myPreTimetableAppointment from PreTimetableAppointment myPreTimetableAppointment where myPreTimetableAppointment.id = ?1"),
		@NamedQuery(name = "findPreTimetableAppointmentByPrimaryKey", query = "select myPreTimetableAppointment from PreTimetableAppointment myPreTimetableAppointment where myPreTimetableAppointment.id = ?1"),
		@NamedQuery(name = "findPreTimetableAppointmentByState", query = "select myPreTimetableAppointment from PreTimetableAppointment myPreTimetableAppointment where myPreTimetableAppointment.state = ?1"),
		@NamedQuery(name = "findPreTimetableAppointmentByStuNumber", query = "select myPreTimetableAppointment from PreTimetableAppointment myPreTimetableAppointment where myPreTimetableAppointment.stuNumber = ?1") })
@Table(name = "pre_timetable_appointment")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "PreTimetableAppointment")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class PreTimetableAppointment implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ???(???μ????)
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

	@Column(name = "stu_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer stuNumber;
	/**
	 * ?????? ????0??1??????
	 * 
	 */

	@Column(name = "state")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer state;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "soft_ware", referencedColumnName = "id") })
	@XmlTransient
	PreSoftware preSoftware;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "tutor", referencedColumnName = "username") })
	@XmlTransient
	User userByTutor;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "term_id", referencedColumnName = "id") })
	@XmlTransient
	SchoolTerm schoolTerm;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "teacher", referencedColumnName = "username") })
	@XmlTransient
	User userByTeacher;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "course_info", referencedColumnName = "course_number") })
	@XmlTransient
	SchoolCourseInfo schoolCourseInfo;
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
	@OneToMany(mappedBy = "preTimetableAppointment", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<net.zjcclims.domain.PreTimetableSchedule> preTimetableSchedules;
	/**
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "pre_m_timetable_room", joinColumns = { @JoinColumn(name = "appointment_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false, updatable = false) })
	@XmlElement(name = "", namespace = "")
	Set<net.zjcclims.domain.PreLabRoom> preLabRooms;

	/**
	 * ???(???μ????)
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ???(???μ????)
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ???????
	 * 
	 */
	public void setStuNumber(Integer stuNumber) {
		this.stuNumber = stuNumber;
	}

	/**
	 * ???????
	 * 
	 */
	public Integer getStuNumber() {
		return this.stuNumber;
	}

	/**
	 * ?????? ????0??1??????
	 * 
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * ?????? ????0??1??????
	 * 
	 */
	public Integer getState() {
		return this.state;
	}

	/**
	 */
	public void setPreSoftware(PreSoftware preSoftware) {
		this.preSoftware = preSoftware;
	}

	/**
	 */
	@JsonIgnore
	public PreSoftware getPreSoftware() {
		return preSoftware;
	}

	/**
	 */
	public void setUserByTutor(User userByTutor) {
		this.userByTutor = userByTutor;
	}

	/**
	 */
	@JsonIgnore
	public User getUserByTutor() {
		return userByTutor;
	}

	/**
	 */
	public void setSchoolTerm(SchoolTerm schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	/**
	 */
	@JsonIgnore
	public SchoolTerm getSchoolTerm() {
		return schoolTerm;
	}

	/**
	 */
	public void setUserByTeacher(User userByTeacher) {
		this.userByTeacher = userByTeacher;
	}

	/**
	 */
	@JsonIgnore
	public User getUserByTeacher() {
		return userByTeacher;
	}

	/**
	 */
	public void setSchoolCourseInfo(SchoolCourseInfo schoolCourseInfo) {
		this.schoolCourseInfo = schoolCourseInfo;
	}

	/**
	 */
	@JsonIgnore
	public SchoolCourseInfo getSchoolCourseInfo() {
		return schoolCourseInfo;
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
	public void setPreTimetableSchedules(Set<PreTimetableSchedule> preTimetableSchedules) {
		this.preTimetableSchedules = preTimetableSchedules;
	}

	/**
	 */
	@JsonIgnore
	public Set<PreTimetableSchedule> getPreTimetableSchedules() {
		if (preTimetableSchedules == null) {
			preTimetableSchedules = new LinkedHashSet<net.zjcclims.domain.PreTimetableSchedule>();
		}
		return preTimetableSchedules;
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
			preLabRooms = new LinkedHashSet<net.zjcclims.domain.PreLabRoom>();
		}
		return preLabRooms;
	}

	/**
	 */
	public PreTimetableAppointment() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(PreTimetableAppointment that) {
		setId(that.getId());
		setStuNumber(that.getStuNumber());
		setState(that.getState());
		setPreSoftware(that.getPreSoftware());
		setUserByTutor(that.getUserByTutor());
		setSchoolTerm(that.getSchoolTerm());
		setUserByTeacher(that.getUserByTeacher());
		setSchoolCourseInfo(that.getSchoolCourseInfo());
		setPreRoomType(that.getPreRoomType());
		setSchoolAcademy(that.getSchoolAcademy());
		setPreTimetableSchedules(new LinkedHashSet<net.zjcclims.domain.PreTimetableSchedule>(that.getPreTimetableSchedules()));
		setPreLabRooms(new LinkedHashSet<net.zjcclims.domain.PreLabRoom>(that.getPreLabRooms()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("stuNumber=[").append(stuNumber).append("] ");
		buffer.append("state=[").append(state).append("] ");

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
		if (!(obj instanceof PreTimetableAppointment))
			return false;
		PreTimetableAppointment equalCheck = (PreTimetableAppointment) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
