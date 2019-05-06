package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;

import org.skyway.spring.util.databinding.TypeConversionUtils;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllLabRoomStationReservations", query = "select myLabRoomStationReservation from LabRoomStationReservation myLabRoomStationReservation"),
		@NamedQuery(name = "findLabRoomStationReservationByEndPeriod", query = "select myLabRoomStationReservation from LabRoomStationReservation myLabRoomStationReservation where myLabRoomStationReservation.endPeriod = ?1"),
		@NamedQuery(name = "findLabRoomStationReservationByEndTime", query = "select myLabRoomStationReservation from LabRoomStationReservation myLabRoomStationReservation where myLabRoomStationReservation.endTime = ?1"),
		@NamedQuery(name = "findLabRoomStationReservationById", query = "select myLabRoomStationReservation from LabRoomStationReservation myLabRoomStationReservation where myLabRoomStationReservation.id = ?1"),
		@NamedQuery(name = "findLabRoomStationReservationByPrimaryKey", query = "select myLabRoomStationReservation from LabRoomStationReservation myLabRoomStationReservation where myLabRoomStationReservation.id = ?1"),
		@NamedQuery(name = "findLabRoomStationReservationByReason", query = "select myLabRoomStationReservation from LabRoomStationReservation myLabRoomStationReservation where myLabRoomStationReservation.reason = ?1"),
		@NamedQuery(name = "findLabRoomStationReservationByReasonContaining", query = "select myLabRoomStationReservation from LabRoomStationReservation myLabRoomStationReservation where myLabRoomStationReservation.reason like ?1"),
		@NamedQuery(name = "findLabRoomStationReservationByReservation", query = "select myLabRoomStationReservation from LabRoomStationReservation myLabRoomStationReservation where myLabRoomStationReservation.reservation = ?1"),
		@NamedQuery(name = "findLabRoomStationReservationByReservationAfter", query = "select myLabRoomStationReservation from LabRoomStationReservation myLabRoomStationReservation where myLabRoomStationReservation.reservation > ?1"),
		@NamedQuery(name = "findLabRoomStationReservationByReservationBefore", query = "select myLabRoomStationReservation from LabRoomStationReservation myLabRoomStationReservation where myLabRoomStationReservation.reservation < ?1"),
		@NamedQuery(name = "findLabRoomStationReservationByStartPeriod", query = "select myLabRoomStationReservation from LabRoomStationReservation myLabRoomStationReservation where myLabRoomStationReservation.startPeriod = ?1"),
		@NamedQuery(name = "findLabRoomStationReservationByStartTime", query = "select myLabRoomStationReservation from LabRoomStationReservation myLabRoomStationReservation where myLabRoomStationReservation.startTime = ?1"),
		@NamedQuery(name = "findLabRoomStationReservationByStationCount", query = "select myLabRoomStationReservation from LabRoomStationReservation myLabRoomStationReservation where myLabRoomStationReservation.stationCount = ?1") })
@Table(name = "lab_room_station_reservation")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabRoomStationReservation")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class LabRoomStationReservation implements Serializable {
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
	 * ����ԤԼ��λ��
	 * 
	 */

	@Column(name = "station_count")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer stationCount;
	/**
	 * ��ʼʱ�䣨��ȷ����㣩
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar startTime;
	/**
	 * ����ʱ�䣨��ȷ����㣩
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar endTime;
	/**
	 * ��ʼʱ���Ӧ������
	 * 
	 */

	@Column(name = "start_period")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer startPeriod;
	/**
	 * ����ʱ���Ӧ������
	 * 
	 */

	@Column(name = "end_period")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer endPeriod;
	/**
	 * ԤԼ����
	 * 
	 */

	@Column(name = "reason")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String reason;
	
	/**
	 * ԤԼ����
	 * 
	 */

	@Column(name = "remark")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String remark;
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * ԤԼʱ��
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "reservation_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar reservation;
	
	@Column(name = "result")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer result;

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

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
	@Column(name = "button_mark")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer buttonMark;
	
	public Integer getButtonMark() {
		return buttonMark;
	}

	public void setButtonMark(Integer buttonMark) {
		this.buttonMark = buttonMark;
	}

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "term_id", referencedColumnName = "id") })
	@XmlTransient
	SchoolTerm schoolTerm;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "username", referencedColumnName = "username") })
	@XmlTransient
	User user;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "dean", referencedColumnName = "username") })
	@XmlTransient
	User userByDean;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "teacher", referencedColumnName = "username") })
	@XmlTransient
	User userByTeacher;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab", referencedColumnName = "id") })
	@XmlTransient
	LabRoom labRoom;
	
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "user_role", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionary;
	/**
	 */
	@OneToMany(mappedBy = "labRoomStationReservation", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomStationReservationStudent> labRoomStationReservationStudents;

	/**
	 */
	@OneToMany(mappedBy = "labRoomStationReservation", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomStationReservationResult> labRoomStationReservationResults;
	/**
	 */
	@OneToMany(mappedBy = "labRoomStationReservation", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomStationReservationCredit> labRoomStationReservationCredits;
	/**
	 */
	public void setLabRoomStationReservationCredits(Set<LabRoomStationReservationCredit> labRoomStationReservationCredits) {
		this.labRoomStationReservationCredits = labRoomStationReservationCredits;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomStationReservationCredit> getLabRoomStationReservationCredits() {
		if (labRoomStationReservationCredits == null) {
			labRoomStationReservationCredits = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomStationReservationCredit>();
		}
		return labRoomStationReservationCredits;
	}
	/**
	 */
	public void setLabRoomStationReservationResults(Set<LabRoomStationReservationResult> labRoomStationReservationResults) {
		this.labRoomStationReservationResults = labRoomStationReservationResults;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomStationReservationResult> getLabRoomStationReservationResults() {
		if (labRoomStationReservationResults == null) {
			labRoomStationReservationResults = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomStationReservationResult>();
		}
		return labRoomStationReservationResults;
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
	public void setUserByDean(User userByDean) {
		this.userByDean = userByDean;
	}

	/**
	 */
	@JsonIgnore
	public User getUserByDean() {
		return userByDean;
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
	
	public void setCDictionary(CDictionary CDictionary) {
		this.CDictionary = CDictionary;
	}

	/**
	 */
	public CDictionary getCDictionary() {
		return CDictionary;
	}

	/**
	 * ����ԤԼ��λ��
	 * 
	 */
	public void setStationCount(Integer stationCount) {
		this.stationCount = stationCount;
	}

	/**
	 * ����ԤԼ��λ��
	 * 
	 */
	public Integer getStationCount() {
		return this.stationCount;
	}

	/**
	 * ��ʼʱ�䣨��ȷ����㣩
	 * 
	 */
	public void setStartTime(Calendar startTime) {
		TypeConversionUtils.clearDateFields(startTime);
		this.startTime = startTime;
	}

	/**
	 * ��ʼʱ�䣨��ȷ����㣩
	 * 
	 */
	public Calendar getStartTime() {
		return this.startTime;
	}

	/**
	 * ����ʱ�䣨��ȷ����㣩
	 * 
	 */
	public void setEndTime(Calendar endTime) {
		TypeConversionUtils.clearDateFields(endTime);
		this.endTime = endTime;
	}

	/**
	 * ����ʱ�䣨��ȷ����㣩
	 * 
	 */
	public Calendar getEndTime() {
		return this.endTime;
	}

	/**
	 * ��ʼʱ���Ӧ������
	 * 
	 */
	public void setStartPeriod(Integer startPeriod) {
		this.startPeriod = startPeriod;
	}

	/**
	 * ��ʼʱ���Ӧ������
	 * 
	 */
	public Integer getStartPeriod() {
		return this.startPeriod;
	}

	/**
	 * ����ʱ���Ӧ������
	 * 
	 */
	public void setEndPeriod(Integer endPeriod) {
		this.endPeriod = endPeriod;
	}

	/**
	 * ����ʱ���Ӧ������
	 * 
	 */
	public Integer getEndPeriod() {
		return this.endPeriod;
	}

	/**
	 * ԤԼ����
	 * 
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * ԤԼ����
	 * 
	 */
	public String getReason() {
		return this.reason;
	}

	/**
	 * ԤԼʱ��
	 * 
	 */
	public void setReservation(Calendar reservation) {
		this.reservation = reservation;
	}

	/**
	 * ԤԼʱ��
	 * 
	 */
	public Calendar getReservation() {
		return this.reservation;
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
	public void setLabRoom(LabRoom labRoom) {
		this.labRoom = labRoom;
	}

	/**
	 */
	@JsonIgnore
	public LabRoom getLabRoom() {
		return labRoom;
	}

	/**
	 */
	public void setLabRoomStationReservationStudents(Set<LabRoomStationReservationStudent> labRoomStationReservationStudents) {
		this.labRoomStationReservationStudents = labRoomStationReservationStudents;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomStationReservationStudent> getLabRoomStationReservationStudents() {
		if (labRoomStationReservationStudents == null) {
			labRoomStationReservationStudents = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomStationReservationStudent>();
		}
		return labRoomStationReservationStudents;
	}

	/**
	 */
	public LabRoomStationReservation() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabRoomStationReservation that) {
		setId(that.getId());
		setStationCount(that.getStationCount());
		setStartTime(that.getStartTime());
		setEndTime(that.getEndTime());
		setStartPeriod(that.getStartPeriod());
		setEndPeriod(that.getEndPeriod());
		setReason(that.getReason());
		setReservation(that.getReservation());
		setSchoolTerm(that.getSchoolTerm());
		setUser(that.getUser());
		setLabRoom(that.getLabRoom());
		setLabRoomStationReservationStudents(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomStationReservationStudent>(that.getLabRoomStationReservationStudents()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("stationCount=[").append(stationCount).append("] ");
		buffer.append("startTime=[").append(startTime).append("] ");
		buffer.append("endTime=[").append(endTime).append("] ");
		buffer.append("startPeriod=[").append(startPeriod).append("] ");
		buffer.append("endPeriod=[").append(endPeriod).append("] ");
		buffer.append("reason=[").append(reason).append("] ");
		buffer.append("reservation=[").append(reservation).append("] ");

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
		if (!(obj instanceof LabRoomStationReservation))
			return false;
		LabRoomStationReservation equalCheck = (LabRoomStationReservation) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
