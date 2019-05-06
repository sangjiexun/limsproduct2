package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Calendar;
import java.util.LinkedHashSet;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.skyway.spring.util.databinding.TypeConversionUtils;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllLabReservations", query = "select myLabReservation from LabReservation myLabReservation"),
		@NamedQuery(name = "findLabReservationByAuditResults", query = "select myLabReservation from LabReservation myLabReservation where myLabReservation.auditResults = ?1"),
		@NamedQuery(name = "findLabReservationByElectiveGroup", query = "select myLabReservation from LabReservation myLabReservation where myLabReservation.electiveGroup = ?1"),
		@NamedQuery(name = "findLabReservationByElectiveGroupContaining", query = "select myLabReservation from LabReservation myLabReservation where myLabReservation.electiveGroup like ?1"),
		@NamedQuery(name = "findLabReservationByEnvironmentalRequirements", query = "select myLabReservation from LabReservation myLabReservation where myLabReservation.environmentalRequirements = ?1"),
		@NamedQuery(name = "findLabReservationByEventName", query = "select myLabReservation from LabReservation myLabReservation where myLabReservation.eventName = ?1"),
		@NamedQuery(name = "findLabReservationByEventNameContaining", query = "select myLabReservation from LabReservation myLabReservation where myLabReservation.eventName like ?1"),
		@NamedQuery(name = "findLabReservationById", query = "select myLabReservation from LabReservation myLabReservation where myLabReservation.id = ?1"),
		@NamedQuery(name = "findLabReservationByItemReleasese", query = "select myLabReservation from LabReservation myLabReservation where myLabReservation.itemReleasese = ?1"),
		@NamedQuery(name = "findLabReservationByNumber", query = "select myLabReservation from LabReservation myLabReservation where myLabReservation.number = ?1"),
		@NamedQuery(name = "findLabReservationByPrimaryKey", query = "select myLabReservation from LabReservation myLabReservation where myLabReservation.id = ?1"),
		@NamedQuery(name = "findLabReservationByRemarks", query = "select myLabReservation from LabReservation myLabReservation where myLabReservation.remarks = ?1"),
		@NamedQuery(name = "findLabReservationByRemarksContaining", query = "select myLabReservation from LabReservation myLabReservation where myLabReservation.remarks like ?1"),
		@NamedQuery(name = "findLabReservationByReservations", query = "select myLabReservation from LabReservation myLabReservation where myLabReservation.reservations = ?1"),
		@NamedQuery(name = "findLabReservationBySelecteNumber", query = "select myLabReservation from LabReservation myLabReservation where myLabReservation.selecteNumber = ?1") })
@Table(name = "lab_reservation")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabReservation")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class LabReservation implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ʵ����ԤԼ��
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	/**
	 * ����
	 * 
	 */

	@Column(name = "event_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String eventName;
	/**
	 * ԤԼ����/ʵ������
	 * 
	 */

	@Column(name = "reservations", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String reservations;
	/**
	 * ��ע
	 * 
	 */

	@Column(name = "remarks")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String remarks;
	/**
	 * ѡ����
	 * 
	 */

	@Column(name = "elective_group")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String electiveGroup;
	/**
	 * ����Ҫ��
	 * 
	 */

	@Column(name = "environmental_requirements", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String environmentalRequirements;
	/**
	 * ����
	 * 
	 */

	@Column(name = "number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String number;
	/**
	 * 1 ͨ��2����У�3δ��ˣ�4 ��˾ܾ�
	 * 
	 */

	@Column(name = "audit_results")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer auditResults;
	/**
	 * ���� 1���Է���
	 * 
	 */

	@Column(name = "item_releasese")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer itemReleasese;
	/**
	 * ��ѡ����
	 * 
	 */

	@Column(name = "selecte_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer selecteNumber;
	/**
	 * 开始时间-精确到半点
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar startTime;
	
	public void setStartTime(Calendar startTime) {
		TypeConversionUtils.clearDateFields(startTime);
		this.startTime = startTime;
	}

	public Calendar getStartTime() {
		return this.startTime;
	}
	/**
	 * 结束时间-精确到半点
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar endTime;
	
	public void setEndTime(Calendar endTime) {
		TypeConversionUtils.clearDateFields(endTime);
		this.endTime = endTime;
	}

	public Calendar getEndTime() {
		return this.endTime;
	}
	/**
	 * Description 借用日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "lending_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar lendingTime;
	/**
	 */
	/**
	 * Description 借用理由
	 */
	@Column(name = "lending_reason")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String lendingReason;
	
	/**
	 * Description 借用人电话
	 */
	@Column(name = "lending_user_phone")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String lendingUserPhone;
	
	/**
	 * Description 申请日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "apply_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar applyDate;
	
	@Column(name = "lending_unit")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String lendingUnit;
	
	@Column(name = "audit_stage")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer auditStage;
	
	@Column(name = "button_mark")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer buttonMark;
	
	@OneToMany(mappedBy = "labReservation", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomReservationCredit> labRoomReservationCredits;
	
	/**
	 */
	public void setLabRoomReservationCredits(Set<LabRoomReservationCredit> labRoomReservationCredits) {
		this.labRoomReservationCredits = labRoomReservationCredits;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomReservationCredit> getLabRoomReservationCredits() {
		if (labRoomReservationCredits == null) {
			labRoomReservationCredits = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomReservationCredit>();
		}
		return labRoomReservationCredits;
	}
	
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "activity_category", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByActivityCategory;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_room", referencedColumnName = "id") })
	@XmlTransient
	LabRoom labRoom;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "contacts", referencedColumnName = "username") })
	@XmlTransient
	User user;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_reservet_ype", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByLabReservetYpe;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "self_course_code", referencedColumnName = "id") })
	@XmlTransient
	TimetableSelfCourse timetableSelfCourse;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "class_no", referencedColumnName = "course_no") })
	@XmlTransient
	SchoolCourse schoolCourse;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "timetable_id", referencedColumnName = "id") })
	@XmlTransient
	TimetableAppointment timetableAppointment;
	
	/**
	 * Description 借用类型
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lending_type", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByLendingType;
	/**
	 * Description 使用人类型
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lending_user_type", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByLendingUserType;

	public CDictionary getCDictionaryByLendingUserType() {
		return CDictionaryByLendingUserType;
	}

	public void setCDictionaryByLendingUserType(
			CDictionary cDictionaryByLendingUserType) {
		CDictionaryByLendingUserType = cDictionaryByLendingUserType;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "class", referencedColumnName = "class_number") })
	@XmlTransient
	SchoolClasses schoolClasses;
	
	/**
	 */
	@OneToMany(mappedBy = "labReservation", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabReservationTimeTable> labReservationTimeTables;
	/**
	 */
	@OneToMany(mappedBy = "labReservation", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabReservationAudit> labReservationAudits;
	/**
	 */
	@OneToMany(mappedBy = "labReservation", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabReservationTimeTableStudent> labReservationTimeTableStudents;

	@JsonIgnore
	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "teacher", referencedColumnName = "username") })
	@XmlTransient
	User teacher;

	/**
	 * ʵ����ԤԼ��
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ʵ����ԤԼ��
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ����
	 * 
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * ����
	 * 
	 */
	public String getEventName() {
		return this.eventName;
	}

	/**
	 * ԤԼ����/ʵ������
	 * 
	 */
	public void setReservations(String reservations) {
		this.reservations = reservations;
	}

	/**
	 * ԤԼ����/ʵ������
	 * 
	 */
	public String getReservations() {
		return this.reservations;
	}

	/**
	 * ��ע
	 * 
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * ��ע
	 * 
	 */
	public String getRemarks() {
		return this.remarks;
	}

	/**
	 * ѡ����
	 * 
	 */
	public void setElectiveGroup(String electiveGroup) {
		this.electiveGroup = electiveGroup;
	}

	/**
	 * ѡ����
	 * 
	 */
	public String getElectiveGroup() {
		return this.electiveGroup;
	}

	/**
	 * ����Ҫ��
	 * 
	 */
	public void setEnvironmentalRequirements(String environmentalRequirements) {
		this.environmentalRequirements = environmentalRequirements;
	}

	/**
	 * ����Ҫ��
	 * 
	 */
	public String getEnvironmentalRequirements() {
		return this.environmentalRequirements;
	}

	/**
	 * ����
	 * 
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * ����
	 * 
	 */
	public String getNumber() {
		return this.number;
	}

	/**
	 * 1 ͨ��2����У�3δ��ˣ�4 ��˾ܾ�
	 * 
	 */
	public void setAuditResults(Integer auditResults) {
		this.auditResults = auditResults;
	}

	/**
	 * 1 ͨ��2����У�3δ��ˣ�4 ��˾ܾ�
	 * 
	 */
	public Integer getAuditResults() {
		return this.auditResults;
	}

	/**
	 * ���� 1���Է���
	 * 
	 */
	public void setItemReleasese(Integer itemReleasese) {
		this.itemReleasese = itemReleasese;
	}

	/**
	 * ���� 1���Է���
	 * 
	 */
	public Integer getItemReleasese() {
		return this.itemReleasese;
	}

	/**
	 * ��ѡ����
	 * 
	 */
	public void setSelecteNumber(Integer selecteNumber) {
		this.selecteNumber = selecteNumber;
	}

	/**
	 * ��ѡ����
	 * 
	 */
	public Integer getSelecteNumber() {
		return this.selecteNumber;
	}

	/**
	 */
	public void setCDictionaryByActivityCategory(CDictionary CDictionaryByActivityCategory) {
		this.CDictionaryByActivityCategory = CDictionaryByActivityCategory;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByActivityCategory() {
		return CDictionaryByActivityCategory;
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
	public void setCDictionaryByLabReservetYpe(CDictionary CDictionaryByLabReservetYpe) {
		this.CDictionaryByLabReservetYpe = CDictionaryByLabReservetYpe;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByLabReservetYpe() {
		return CDictionaryByLabReservetYpe;
	}

	/**
	 */
	public void setTimetableSelfCourse(TimetableSelfCourse timetableSelfCourse) {
		this.timetableSelfCourse = timetableSelfCourse;
	}

	/**
	 */
	@JsonIgnore
	public TimetableSelfCourse getTimetableSelfCourse() {
		return timetableSelfCourse;
	}

	/**
	 */
	public void setSchoolCourse(SchoolCourse schoolCourse) {
		this.schoolCourse = schoolCourse;
	}

	/**
	 */
	@JsonIgnore
	public SchoolCourse getSchoolCourse() {
		return schoolCourse;
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
	public void setLabReservationTimeTables(Set<LabReservationTimeTable> labReservationTimeTables) {
		this.labReservationTimeTables = labReservationTimeTables;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabReservationTimeTable> getLabReservationTimeTables() {
		if (labReservationTimeTables == null) {
			labReservationTimeTables = new java.util.LinkedHashSet<net.zjcclims.domain.LabReservationTimeTable>();
		}
		return labReservationTimeTables;
	}

	/**
	 */
	public void setLabReservationAudits(Set<LabReservationAudit> labReservationAudits) {
		this.labReservationAudits = labReservationAudits;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabReservationAudit> getLabReservationAudits() {
		if (labReservationAudits == null) {
			labReservationAudits = new java.util.LinkedHashSet<net.zjcclims.domain.LabReservationAudit>();
		}
		return labReservationAudits;
	}

	/**
	 */
	public void setLabReservationTimeTableStudents(Set<LabReservationTimeTableStudent> labReservationTimeTableStudents) {
		this.labReservationTimeTableStudents = labReservationTimeTableStudents;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabReservationTimeTableStudent> getLabReservationTimeTableStudents() {
		if (labReservationTimeTableStudents == null) {
			labReservationTimeTableStudents = new java.util.LinkedHashSet<net.zjcclims.domain.LabReservationTimeTableStudent>();
		}
		return labReservationTimeTableStudents;
	}

	/**
	 */
	public LabReservation() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabReservation that) {
		setId(that.getId());
		setEventName(that.getEventName());
		setReservations(that.getReservations());
		setRemarks(that.getRemarks());
		setElectiveGroup(that.getElectiveGroup());
		setEnvironmentalRequirements(that.getEnvironmentalRequirements());
		setNumber(that.getNumber());
		setAuditResults(that.getAuditResults());
		setItemReleasese(that.getItemReleasese());
		setSelecteNumber(that.getSelecteNumber());
		setCDictionaryByActivityCategory(that.getCDictionaryByActivityCategory());
		setLabRoom(that.getLabRoom());
		setUser(that.getUser());
		setCDictionaryByLabReservetYpe(that.getCDictionaryByLabReservetYpe());
		setTimetableSelfCourse(that.getTimetableSelfCourse());
		setSchoolCourse(that.getSchoolCourse());
		setTimetableAppointment(that.getTimetableAppointment());
		setLabReservationTimeTables(new java.util.LinkedHashSet<net.zjcclims.domain.LabReservationTimeTable>(that.getLabReservationTimeTables()));
		setLabReservationAudits(new java.util.LinkedHashSet<net.zjcclims.domain.LabReservationAudit>(that.getLabReservationAudits()));
		setLabReservationTimeTableStudents(new java.util.LinkedHashSet<net.zjcclims.domain.LabReservationTimeTableStudent>(that.getLabReservationTimeTableStudents()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("eventName=[").append(eventName).append("] ");
		buffer.append("reservations=[").append(reservations).append("] ");
		buffer.append("remarks=[").append(remarks).append("] ");
		buffer.append("electiveGroup=[").append(electiveGroup).append("] ");
		buffer.append("environmentalRequirements=[").append(environmentalRequirements).append("] ");
		buffer.append("number=[").append(number).append("] ");
		buffer.append("auditResults=[").append(auditResults).append("] ");
		buffer.append("itemReleasese=[").append(itemReleasese).append("] ");
		buffer.append("selecteNumber=[").append(selecteNumber).append("] ");

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
		if (!(obj instanceof LabReservation))
			return false;
		LabReservation equalCheck = (LabReservation) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}

	public Calendar getLendingTime() {
		return lendingTime;
	}

	public void setLendingTime(Calendar lendingTime) {
		this.lendingTime = lendingTime;
	}

	public String getLendingReason() {
		return lendingReason;
	}

	public void setLendingReason(String lendingReason) {
		this.lendingReason = lendingReason;
	}

	public String getLendingUserPhone() {
		return lendingUserPhone;
	}

	public void setLendingUserPhone(String lendingUserPhone) {
		this.lendingUserPhone = lendingUserPhone;
	}

	public Calendar getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Calendar applyDate) {
		this.applyDate = applyDate;
	}

	public String getLendingUnit() {
		return lendingUnit;
	}

	public void setLendingUnit(String lendingUnit) {
		this.lendingUnit = lendingUnit;
	}

	public CDictionary getCDictionaryByLendingType() {
		return CDictionaryByLendingType;
	}

	public void setCDictionaryByLendingType(CDictionary cDictionaryByLendingType) {
		CDictionaryByLendingType = cDictionaryByLendingType;
	}

	public SchoolClasses getSchoolClasses() {
		return schoolClasses;
	}

	public void setSchoolClasses(SchoolClasses schoolClasses) {
		this.schoolClasses = schoolClasses;
	}

	public Integer getAuditStage() {
		return auditStage;
	}

	public void setAuditStage(Integer auditStage) {
		this.auditStage = auditStage;
	}

	public Integer getButtonMark() {
		return buttonMark;
	}

	public void setButtonMark(Integer buttonMark) {
		this.buttonMark = buttonMark;
	}
}
