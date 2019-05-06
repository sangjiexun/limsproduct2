package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllTimetableAppointmentChanges", query = "select myTimetableAppointmentChange from TimetableAppointmentChange myTimetableAppointmentChange"),
		@NamedQuery(name = "findTimetableAppointmentChangeByEndClass", query = "select myTimetableAppointmentChange from TimetableAppointmentChange myTimetableAppointmentChange where myTimetableAppointmentChange.endClass = ?1"),
		@NamedQuery(name = "findTimetableAppointmentChangeById", query = "select myTimetableAppointmentChange from TimetableAppointmentChange myTimetableAppointmentChange where myTimetableAppointmentChange.id = ?1"),
		@NamedQuery(name = "findTimetableAppointmentChangeByItemName", query = "select myTimetableAppointmentChange from TimetableAppointmentChange myTimetableAppointmentChange where myTimetableAppointmentChange.itemName = ?1"),
		@NamedQuery(name = "findTimetableAppointmentChangeByItemNameContaining", query = "select myTimetableAppointmentChange from TimetableAppointmentChange myTimetableAppointmentChange where myTimetableAppointmentChange.itemName like ?1"),
		@NamedQuery(name = "findTimetableAppointmentChangeByPrimaryKey", query = "select myTimetableAppointmentChange from TimetableAppointmentChange myTimetableAppointmentChange where myTimetableAppointmentChange.id = ?1"),
		@NamedQuery(name = "findTimetableAppointmentChangeByStartClass", query = "select myTimetableAppointmentChange from TimetableAppointmentChange myTimetableAppointmentChange where myTimetableAppointmentChange.startClass = ?1"),
		@NamedQuery(name = "findTimetableAppointmentChangeByStatus", query = "select myTimetableAppointmentChange from TimetableAppointmentChange myTimetableAppointmentChange where myTimetableAppointmentChange.status = ?1"),
		@NamedQuery(name = "findTimetableAppointmentChangeByWeek", query = "select myTimetableAppointmentChange from TimetableAppointmentChange myTimetableAppointmentChange where myTimetableAppointmentChange.week = ?1"),
		@NamedQuery(name = "findTimetableAppointmentChangeByWeekday", query = "select myTimetableAppointmentChange from TimetableAppointmentChange myTimetableAppointmentChange where myTimetableAppointmentChange.weekday = ?1") })
@Table(name = "timetable_appointment_change")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "text/net/zjcclims/domain", name = "TimetableAppointmentChange")
public class TimetableAppointmentChange implements Serializable {
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
	 * ��Ŀ���
	 * 
	 */

	@Column(name = "itemName")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String itemName;
	/**
	 * �ܴ�
	 * 
	 */

	@Column(name = "week")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer week;
	/**
	 * ����
	 * 
	 */

	@Column(name = "weekday")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer weekday;
	/**
	 * �ܴ�
	 * 
	 */

	@Column(name = "flag")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer flag;
	

	/**
	 * ��ʼ�ܴ�
	 * 
	 */

	@Column(name = "start_class")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer startClass;
	/**
	 * �����ܴ�
	 * 
	 */

	@Column(name = "end_class")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer endClass;
	/**
	 */

	@Column(name = "status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer status;
	/**
	 * ��Ŀ���
	 * 
	 */

	@Column(name = "cause")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String cause;
	
	/**
	 * ��Ŀ���
	 * 
	 */
	@Column(name = "address")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String address;
	/**
	 * ��Ŀ���
	 * 
	 */
	@Column(name = "teacher")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String teacher;
	
	@Column(name = "button_mark")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer buttonMark;
	
	@Column(name = "state")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer state;
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getButtonMark() {
		return buttonMark;
	}

	public void setButtonMark(Integer buttonMark) {
		this.buttonMark = buttonMark;
	}

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "timetable_id", referencedColumnName = "id") })
	@XmlTransient
	TimetableAppointment timetableAppointment;
	/**
	 */
	@OneToMany(mappedBy = "timetableAppointmentChange", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableAppointmentChangeAduit> timetableAppointmentChangeAduits;
	/**
	 */
	public void setTimetableAppointmentChangeAduits(Set<TimetableAppointmentChangeAduit> timetableAppointmentChangeAduits) {
		this.timetableAppointmentChangeAduits = timetableAppointmentChangeAduits;
	}

	/**
	 */
	@JsonIgnore
	public Set<TimetableAppointmentChangeAduit> getTimetableAppointmentChangeAduits() {
		if (timetableAppointmentChangeAduits == null) {
			timetableAppointmentChangeAduits = new java.util.LinkedHashSet<net.zjcclims.domain.TimetableAppointmentChangeAduit>();
		}
		return timetableAppointmentChangeAduits;
	}
	/**
	 */
	
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "laroom_id", referencedColumnName = "id") })
	@XmlTransient
	LabRoom labRoom;
	public LabRoom getLabRoom() {
		return labRoom;
	}

	public void setLabRoom(LabRoom labRoom) {
		this.labRoom = labRoom;
	}

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "create_teacher", referencedColumnName = "username") })
	@XmlTransient
	User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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
	 * ��Ŀ���
	 * 
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * ��Ŀ���
	 * 
	 */
	public String getItemName() {
		return this.itemName;
	}

	/**
	 * �ܴ�
	 * 
	 */
	public void setWeek(Integer week) {
		this.week = week;
	}

	/**
	 * �ܴ�
	 * 
	 */
	public Integer getWeek() {
		return this.week;
	}

	/**
	 * ����
	 * 
	 */
	public void setWeekday(Integer weekday) {
		this.weekday = weekday;
	}

	/**
	 * ����
	 * 
	 */
	public Integer getWeekday() {
		return this.weekday;
	}

	

	/**
	 * ��ʼ�ܴ�
	 * 
	 */
	public void setStartClass(Integer startClass) {
		this.startClass = startClass;
	}

	/**
	 * ��ʼ�ܴ�
	 * 
	 */
	public Integer getStartClass() {
		return this.startClass;
	}

	/**
	 * �����ܴ�
	 * 
	 */
	public void setEndClass(Integer endClass) {
		this.endClass = endClass;
	}

	/**
	 * �����ܴ�
	 * 
	 */
	public Integer getEndClass() {
		return this.endClass;
	}

	/**
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 */
	public Integer getStatus() {
		return this.status;
	}
	/**
	 */
	public Integer getFlag() {
		return flag;
	}
	/**
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}
	/**
	 */
	public void setTimetableAppointment(TimetableAppointment timetableAppointment) {
		this.timetableAppointment = timetableAppointment;
	}

	/**
	 */
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 */
	@JsonIgnore
	public TimetableAppointment getTimetableAppointment() {
		return timetableAppointment;
	}
	/**
	 */
	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	/**
	 */
	public TimetableAppointmentChange() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(TimetableAppointmentChange that) {
		setId(that.getId());
		setItemName(that.getItemName());
		setWeek(that.getWeek());
		setWeekday(that.getWeekday());
		setFlag(that.getFlag());
		setStartClass(that.getStartClass());
		setEndClass(that.getEndClass());
		setStatus(that.getStatus());
		setTimetableAppointment(that.getTimetableAppointment());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();
		buffer.append("id=[").append(id).append("] ");
		buffer.append("itemName=[").append(itemName).append("] ");
		buffer.append("week=[").append(week).append("] ");
		buffer.append("weekday=[").append(weekday).append("] ");
		buffer.append("flag=[").append(flag).append("] ");
		buffer.append("startClass=[").append(startClass).append("] ");
		buffer.append("endClass=[").append(endClass).append("] ");
		buffer.append("status=[").append(status).append("] ");
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
		if (!(obj instanceof TimetableAppointmentChange))
			return false;
		TimetableAppointmentChange equalCheck = (TimetableAppointmentChange) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
