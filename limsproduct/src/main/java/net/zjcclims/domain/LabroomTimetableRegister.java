package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Calendar;
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
		@NamedQuery(name = "findAllLabroomTimetableRegisters", query = "select myLabroomTimetableRegister from LabroomTimetableRegister myLabroomTimetableRegister"),
		@NamedQuery(name = "findLabroomTimetableRegisterByClassNumber", query = "select myLabroomTimetableRegister from LabroomTimetableRegister myLabroomTimetableRegister where myLabroomTimetableRegister.classNumber = ?1"),
		@NamedQuery(name = "findLabroomTimetableRegisterByClassNumberContaining", query = "select myLabroomTimetableRegister from LabroomTimetableRegister myLabroomTimetableRegister where myLabroomTimetableRegister.classNumber like ?1"),
		@NamedQuery(name = "findLabroomTimetableRegisterByConfirmDate", query = "select myLabroomTimetableRegister from LabroomTimetableRegister myLabroomTimetableRegister where myLabroomTimetableRegister.confirmDate = ?1"),
		@NamedQuery(name = "findLabroomTimetableRegisterByConfirmDateAfter", query = "select myLabroomTimetableRegister from LabroomTimetableRegister myLabroomTimetableRegister where myLabroomTimetableRegister.confirmDate > ?1"),
		@NamedQuery(name = "findLabroomTimetableRegisterByConfirmDateBefore", query = "select myLabroomTimetableRegister from LabroomTimetableRegister myLabroomTimetableRegister where myLabroomTimetableRegister.confirmDate < ?1"),
		@NamedQuery(name = "findLabroomTimetableRegisterByConfirmUser", query = "select myLabroomTimetableRegister from LabroomTimetableRegister myLabroomTimetableRegister where myLabroomTimetableRegister.confirmUser = ?1"),
		@NamedQuery(name = "findLabroomTimetableRegisterByConfirmUserContaining", query = "select myLabroomTimetableRegister from LabroomTimetableRegister myLabroomTimetableRegister where myLabroomTimetableRegister.confirmUser like ?1"),
		@NamedQuery(name = "findLabroomTimetableRegisterByEndClass", query = "select myLabroomTimetableRegister from LabroomTimetableRegister myLabroomTimetableRegister where myLabroomTimetableRegister.endClass = ?1"),
		@NamedQuery(name = "findLabroomTimetableRegisterByEndWeek", query = "select myLabroomTimetableRegister from LabroomTimetableRegister myLabroomTimetableRegister where myLabroomTimetableRegister.endWeek = ?1"),
		@NamedQuery(name = "findLabroomTimetableRegisterById", query = "select myLabroomTimetableRegister from LabroomTimetableRegister myLabroomTimetableRegister where myLabroomTimetableRegister.id = ?1"),
		@NamedQuery(name = "findLabroomTimetableRegisterByPrimaryKey", query = "select myLabroomTimetableRegister from LabroomTimetableRegister myLabroomTimetableRegister where myLabroomTimetableRegister.id = ?1"),
		@NamedQuery(name = "findLabroomTimetableRegisterByProgramName", query = "select myLabroomTimetableRegister from LabroomTimetableRegister myLabroomTimetableRegister where myLabroomTimetableRegister.programName = ?1"),
		@NamedQuery(name = "findLabroomTimetableRegisterByProgramNameContaining", query = "select myLabroomTimetableRegister from LabroomTimetableRegister myLabroomTimetableRegister where myLabroomTimetableRegister.programName like ?1"),
		@NamedQuery(name = "findLabroomTimetableRegisterByStartClass", query = "select myLabroomTimetableRegister from LabroomTimetableRegister myLabroomTimetableRegister where myLabroomTimetableRegister.startClass = ?1"),
		@NamedQuery(name = "findLabroomTimetableRegisterByStartWeek", query = "select myLabroomTimetableRegister from LabroomTimetableRegister myLabroomTimetableRegister where myLabroomTimetableRegister.startWeek = ?1"),
		@NamedQuery(name = "findLabroomTimetableRegisterByTeacher", query = "select myLabroomTimetableRegister from LabroomTimetableRegister myLabroomTimetableRegister where myLabroomTimetableRegister.teacher = ?1"),
		@NamedQuery(name = "findLabroomTimetableRegisterByTeacherContaining", query = "select myLabroomTimetableRegister from LabroomTimetableRegister myLabroomTimetableRegister where myLabroomTimetableRegister.teacher like ?1"),
		@NamedQuery(name = "findLabroomTimetableRegisterByTotalClass", query = "select myLabroomTimetableRegister from LabroomTimetableRegister myLabroomTimetableRegister where myLabroomTimetableRegister.totalClass = ?1"),
		@NamedQuery(name = "findLabroomTimetableRegisterByTotalWeek", query = "select myLabroomTimetableRegister from LabroomTimetableRegister myLabroomTimetableRegister where myLabroomTimetableRegister.totalWeek = ?1"),
		@NamedQuery(name = "findLabroomTimetableRegisterByWeekday", query = "select myLabroomTimetableRegister from LabroomTimetableRegister myLabroomTimetableRegister where myLabroomTimetableRegister.weekday = ?1") })
@Table(name = "labroom_timetable_register")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zicclims/net/zjcclims/domain", name = "LabroomTimetableRegister")
public class LabroomTimetableRegister implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ʵѵ��ʹ�õǼǱ�
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
	Integer id;
	/**
	 * �Ͽν�ʦ�����
	 * 
	 */

	@Column(name = "teacher")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String teacher;
	/**
	 * �Ͽΰ༶�����
	 * 
	 */

	@Column(name = "class_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String classNumber;
	/**
	 * ����
	 * 
	 */

	@Column(name = "weekday")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer weekday;
	/**
	 * ��ʼ�ڴ�
	 * 
	 */

	@Column(name = "start_class")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer startClass;
	/**
	 * ����ڴ�
	 * 
	 */

	@Column(name = "end_class")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer endClass;
	/**
	 * �ܽڴ�
	 * 
	 */

	@Column(name = "total_class")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer totalClass;
	/**
	 * ��ʼ�ܴ�
	 * 
	 */

	@Column(name = "start_week")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer startWeek;
	/**
	 * �����ܴ�
	 * 
	 */

	@Column(name = "end_week")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer endWeek;
	/**
	 * ���ܴ�
	 * 
	 */

	@Column(name = "total_week")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer totalWeek;
	/**
	 * ��Ŀ��Ʒ����
	 * 
	 */

	@Column(name = "program_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String programName;
	/**
	 * ȷ��ʱ��
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "confirm_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar confirmDate;

	@Column(name = "confirm_remark", length = 255)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String confirmRemark;

	public String getConfirmRemark() {
		return confirmRemark;
	}

	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
	}
	/**
	 * ȷ����
	 * 
	 */

	@Column(name = "confirm_user")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String confirmUser;

	/**
	 */
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "course_number", referencedColumnName = "course_no") })
	@XmlTransient
	SchoolCourse schoolCourse;*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "course_number", referencedColumnName = "course_number") })
	@XmlTransient
	SchoolCourseInfo schoolCourseInfo;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "labroom_id", referencedColumnName = "id") })
	@XmlTransient
	LabRoom labRoom;
	
	/**
	 * 备注
	 * 
	 */

	@Column(name = "note")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String note;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "object", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByObject;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "application", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByApplication;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "equipment_situation", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByEquipmentSituation;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "tidy_situation", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByTidySituation;
	
	/**
	 * 仪器名称
	 * 
	 */

	@Column(name = "device_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String deviceName;
	/**
	 * 软件名称
	 * 
	 */

	@Column(name = "software_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String softwareName;

	/**
	 * ʵѵ��ʹ�õǼǱ�
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ʵѵ��ʹ�õǼǱ�
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * �Ͽν�ʦ�����
	 * 
	 */
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	/**
	 * �Ͽν�ʦ�����
	 * 
	 */
	public String getTeacher() {
		return this.teacher;
	}

	/**
	 * �Ͽΰ༶�����
	 * 
	 */
	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}

	/**
	 * �Ͽΰ༶�����
	 * 
	 */
	public String getClassNumber() {
		return this.classNumber;
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
	 * ��ʼ�ڴ�
	 * 
	 */
	public void setStartClass(Integer startClass) {
		this.startClass = startClass;
	}

	/**
	 * ��ʼ�ڴ�
	 * 
	 */
	public Integer getStartClass() {
		return this.startClass;
	}

	/**
	 * ����ڴ�
	 * 
	 */
	public void setEndClass(Integer endClass) {
		this.endClass = endClass;
	}

	/**
	 * ����ڴ�
	 * 
	 */
	public Integer getEndClass() {
		return this.endClass;
	}

	/**
	 * �ܽڴ�
	 * 
	 */
	public void setTotalClass(Integer totalClass) {
		this.totalClass = totalClass;
	}

	/**
	 * �ܽڴ�
	 * 
	 */
	public Integer getTotalClass() {
		return this.totalClass;
	}

	/**
	 * ��ʼ�ܴ�
	 * 
	 */
	public void setStartWeek(Integer startWeek) {
		this.startWeek = startWeek;
	}

	/**
	 * ��ʼ�ܴ�
	 * 
	 */
	public Integer getStartWeek() {
		return this.startWeek;
	}

	/**
	 * �����ܴ�
	 * 
	 */
	public void setEndWeek(Integer endWeek) {
		this.endWeek = endWeek;
	}

	/**
	 * �����ܴ�
	 * 
	 */
	public Integer getEndWeek() {
		return this.endWeek;
	}

	/**
	 * ���ܴ�
	 * 
	 */
	public void setTotalWeek(Integer totalWeek) {
		this.totalWeek = totalWeek;
	}

	/**
	 * ���ܴ�
	 * 
	 */
	public Integer getTotalWeek() {
		return this.totalWeek;
	}

	/**
	 * ��Ŀ��Ʒ����
	 * 
	 */
	public void setProgramName(String programName) {
		this.programName = programName;
	}

	/**
	 * ��Ŀ��Ʒ����
	 * 
	 */
	public String getProgramName() {
		return this.programName;
	}

	/**
	 * ȷ��ʱ��
	 * 
	 */
	public void setConfirmDate(Calendar confirmDate) {
		this.confirmDate = confirmDate;
	}

	/**
	 * ȷ��ʱ��
	 * 
	 */
	public Calendar getConfirmDate() {
		return this.confirmDate;
	}

	/**
	 * ȷ����
	 * 
	 */
	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}

	/**
	 * ȷ����
	 * 
	 */
	public String getConfirmUser() {
		return this.confirmUser;
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
	 * 备注
	 * 
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 备注
	 * 
	 */
	public String getNote() {
		return this.note;
	}

	/**
	 */
	public void setCDictionaryByObject(CDictionary CDictionaryByObject) {
		this.CDictionaryByObject = CDictionaryByObject;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByObject() {
		return CDictionaryByObject;
	}

	/**
	 */
	public void setCDictionaryByApplication(CDictionary CDictionaryByApplication) {
		this.CDictionaryByApplication = CDictionaryByApplication;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByApplication() {
		return CDictionaryByApplication;
	}

	/**
	 */
	public void setCDictionaryByEquipmentSituation(CDictionary CDictionaryByEquipmentSituation) {
		this.CDictionaryByEquipmentSituation = CDictionaryByEquipmentSituation;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByEquipmentSituation() {
		return CDictionaryByEquipmentSituation;
	}

	/**
	 */
	public void setCDictionaryByTidySituation(CDictionary CDictionaryByTidySituation) {
		this.CDictionaryByTidySituation = CDictionaryByTidySituation;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByTidySituation() {
		return CDictionaryByTidySituation;
	}
	
	/**
	 * 仪器名称
	 * 
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * 仪器名称
	 * 
	 */
	public String getDeviceName() {
		return this.deviceName;
	}

	/**
	 * 软件名称
	 * 
	 */
	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	/**
	 * 软件名称
	 * 
	 */
	public String getSoftwareName() {
		return this.softwareName;
	}

	/**
	 */
	public LabroomTimetableRegister() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabroomTimetableRegister that) {
		setId(that.getId());
		setTeacher(that.getTeacher());
		setClassNumber(that.getClassNumber());
		setWeekday(that.getWeekday());
		setStartClass(that.getStartClass());
		setEndClass(that.getEndClass());
		setTotalClass(that.getTotalClass());
		setStartWeek(that.getStartWeek());
		setEndWeek(that.getEndWeek());
		setTotalWeek(that.getTotalWeek());
		setProgramName(that.getProgramName());
		setConfirmDate(that.getConfirmDate());
		setConfirmUser(that.getConfirmUser());
		setSchoolCourseInfo(that.getSchoolCourseInfo());
		setLabRoom(that.getLabRoom());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("teacher=[").append(teacher).append("] ");
		buffer.append("classNumber=[").append(classNumber).append("] ");
		buffer.append("weekday=[").append(weekday).append("] ");
		buffer.append("startClass=[").append(startClass).append("] ");
		buffer.append("endClass=[").append(endClass).append("] ");
		buffer.append("totalClass=[").append(totalClass).append("] ");
		buffer.append("startWeek=[").append(startWeek).append("] ");
		buffer.append("endWeek=[").append(endWeek).append("] ");
		buffer.append("totalWeek=[").append(totalWeek).append("] ");
		buffer.append("programName=[").append(programName).append("] ");
		buffer.append("confirmDate=[").append(confirmDate).append("] ");
		buffer.append("confirmUser=[").append(confirmUser).append("] ");

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
		if (!(obj instanceof LabroomTimetableRegister))
			return false;
		LabroomTimetableRegister equalCheck = (LabroomTimetableRegister) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
