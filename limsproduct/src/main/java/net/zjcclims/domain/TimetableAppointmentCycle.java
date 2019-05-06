package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllTimetableAppointmentCycles", query = "select myTimetableAppointmentCycle from TimetableAppointmentCycle myTimetableAppointmentCycle"),
		@NamedQuery(name = "findTimetableAppointmentCycleByDetailNo", query = "select myTimetableAppointmentCycle from TimetableAppointmentCycle myTimetableAppointmentCycle where myTimetableAppointmentCycle.detailNo = ?1"),
		@NamedQuery(name = "findTimetableAppointmentCycleByDetailNoContaining", query = "select myTimetableAppointmentCycle from TimetableAppointmentCycle myTimetableAppointmentCycle where myTimetableAppointmentCycle.detailNo like ?1"),
		@NamedQuery(name = "findTimetableAppointmentCycleById", query = "select myTimetableAppointmentCycle from TimetableAppointmentCycle myTimetableAppointmentCycle where myTimetableAppointmentCycle.id = ?1"),
		@NamedQuery(name = "findTimetableAppointmentCycleByPrimaryKey", query = "select myTimetableAppointmentCycle from TimetableAppointmentCycle myTimetableAppointmentCycle where myTimetableAppointmentCycle.id = ?1") })
@Table(name = "timetable_appointment_cycle")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "TimetableAppointmentCycle")
public class TimetableAppointmentCycle implements Serializable {
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
	 */

	@Column(name = "detail_no")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String detailNo;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "tutor", referencedColumnName = "username") })
	@XmlTransient
	User userByTutor;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "item_id", referencedColumnName = "id") })
	@XmlTransient
	OperationItem operationItem;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "teacher", referencedColumnName = "username") })
	@XmlTransient
	User userByTeacher;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "labroom_id", referencedColumnName = "id") })
	@XmlTransient
	LabRoom labRoom;

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
	 */
	public void setDetailNo(String detailNo) {
		this.detailNo = detailNo;
	}

	/**
	 */
	public String getDetailNo() {
		return this.detailNo;
	}

	/**
	 */
	public void setUserByTutor(User userByTutor) {
		this.userByTutor = userByTutor;
	}

	/**
	 */
	public User getUserByTutor() {
		return userByTutor;
	}

	/**
	 */
	public void setOperationItem(OperationItem operationItem) {
		this.operationItem = operationItem;
	}

	/**
	 */
	public OperationItem getOperationItem() {
		return operationItem;
	}

	/**
	 */
	public void setUserByTeacher(User userByTeacher) {
		this.userByTeacher = userByTeacher;
	}

	/**
	 */
	public User getUserByTeacher() {
		return userByTeacher;
	}

	/**
	 */
	public void setLabRoom(LabRoom labRoom) {
		this.labRoom = labRoom;
	}

	/**
	 */
	public LabRoom getLabRoom() {
		return labRoom;
	}

	/**
	 */
	public TimetableAppointmentCycle() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(TimetableAppointmentCycle that) {
		setId(that.getId());
		setDetailNo(that.getDetailNo());
		setUserByTutor(that.getUserByTutor());
		setOperationItem(that.getOperationItem());
		setUserByTeacher(that.getUserByTeacher());
		setLabRoom(that.getLabRoom());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("detailNo=[").append(detailNo).append("] ");

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
		if (!(obj instanceof TimetableAppointmentCycle))
			return false;
		TimetableAppointmentCycle equalCheck = (TimetableAppointmentCycle) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
