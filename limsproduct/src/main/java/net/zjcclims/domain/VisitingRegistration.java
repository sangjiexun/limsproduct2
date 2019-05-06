package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Calendar;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllVisitingRegistrations", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration"),
		@NamedQuery(name = "findVisitingRegistrationByClass_", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.class_ = ?1"),
		@NamedQuery(name = "findVisitingRegistrationByClass_Containing", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.class_ like ?1"),
		@NamedQuery(name = "findVisitingRegistrationByCourseHours", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.courseHours = ?1"),
		@NamedQuery(name = "findVisitingRegistrationByCourseHoursContaining", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.courseHours like ?1"),
		@NamedQuery(name = "findVisitingRegistrationByCourseName", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.courseName = ?1"),
		@NamedQuery(name = "findVisitingRegistrationByCourseNameContaining", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.courseName like ?1"),
		@NamedQuery(name = "findVisitingRegistrationByDate", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.date = ?1"),
		@NamedQuery(name = "findVisitingRegistrationByFlag", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.flag = ?1"),
		@NamedQuery(name = "findVisitingRegistrationById", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.id = ?1"),
		@NamedQuery(name = "findVisitingRegistrationByLabroomVisiting", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.labroomVisiting = ?1"),
		@NamedQuery(name = "findVisitingRegistrationByLabroomVisitingContaining", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.labroomVisiting like ?1"),
		@NamedQuery(name = "findVisitingRegistrationByPrimaryKey", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.id = ?1"),
		@NamedQuery(name = "findVisitingRegistrationByProjectName", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.projectName = ?1"),
		@NamedQuery(name = "findVisitingRegistrationByProjectNameContaining", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.projectName like ?1"),
		@NamedQuery(name = "findVisitingRegistrationByRegistrant", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.registrant = ?1"),
		@NamedQuery(name = "findVisitingRegistrationByRegistrantContaining", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.registrant like ?1"),
		@NamedQuery(name = "findVisitingRegistrationByStudentNumber", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.studentNumber = ?1"),
		@NamedQuery(name = "findVisitingRegistrationByTeacher", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.teacher = ?1"),
		@NamedQuery(name = "findVisitingRegistrationByTeacherContaining", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.teacher like ?1"),
		@NamedQuery(name = "findVisitingRegistrationByVisitingUnit", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.visitingUnit = ?1"),
		@NamedQuery(name = "findVisitingRegistrationByVisitingUnitContaining", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.visitingUnit like ?1"),
		@NamedQuery(name = "findVisitingRegistrationByVisitorNumber", query = "select myVisitingRegistration from VisitingRegistration myVisitingRegistration where myVisitingRegistration.visitorNumber = ?1") })
@Table(name = "visiting_registration")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclimsttt/zjcclims/domain", name = "VisitingRegistration")
public class VisitingRegistration implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * �γ����
	 * 
	 */

	@Column(name = "course_name", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String courseName;
	/**
	 * ��Ŀ���
	 * 
	 */

	@Column(name = "project_name", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String projectName;
	/**
	 * ��ʦ
	 * 
	 */

	@Column(name = "teacher", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String teacher;
	/**
	 * �༶
	 * 
	 */

	@Column(name = "class", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String class_;
	/**
	 * ѧ������
	 * 
	 */

	@Column(name = "student_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer studentNumber;
	/**
	 * ����
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar date;
	/**
	 * �ι�ʵѵ��
	 * 
	 */

	@Column(name = "labroom_visiting")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String labroomVisiting;
	/**
	 * ��ʱ��
	 * 
	 */

	@Column(name = "course_hours", length = 11)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String courseHours;
	/**
	 * �ι۵�λ
	 * 
	 */

	@Column(name = "visiting_unit")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String visitingUnit;
	/**
	 * �ι�����
	 * 
	 */

	@Column(name = "visitor_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer visitorNumber;
	/**
	 * �Ǽ���
	 * 
	 */

	@Column(name = "registrant")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String registrant;
	/**
	 * 1ΪУ��2ΪУ��
	 * 
	 */

	@Column(name = "flag")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer flag;

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
	 * �γ����
	 * 
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * �γ����
	 * 
	 */
	public String getCourseName() {
		return this.courseName;
	}

	/**
	 * ��Ŀ���
	 * 
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * ��Ŀ���
	 * 
	 */
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * ��ʦ
	 * 
	 */
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	/**
	 * ��ʦ
	 * 
	 */
	public String getTeacher() {
		return this.teacher;
	}

	/**
	 * �༶
	 * 
	 */
	public void setClass_(String class_) {
		this.class_ = class_;
	}

	/**
	 * �༶
	 * 
	 */
	public String getClass_() {
		return this.class_;
	}

	/**
	 * ѧ������
	 * 
	 */
	public void setStudentNumber(Integer studentNumber) {
		this.studentNumber = studentNumber;
	}

	/**
	 * ѧ������
	 * 
	 */
	public Integer getStudentNumber() {
		return this.studentNumber;
	}

	/**
	 * ����
	 * 
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}

	/**
	 * ����
	 * 
	 */
	public Calendar getDate() {
		return this.date;
	}

	/**
	 * �ι�ʵѵ��
	 * 
	 */
	public void setLabroomVisiting(String labroomVisiting) {
		this.labroomVisiting = labroomVisiting;
	}

	/**
	 * �ι�ʵѵ��
	 * 
	 */
	public String getLabroomVisiting() {
		return this.labroomVisiting;
	}

	/**
	 * ��ʱ��
	 * 
	 */
	public void setCourseHours(String courseHours) {
		this.courseHours = courseHours;
	}

	/**
	 * ��ʱ��
	 * 
	 */
	public String getCourseHours() {
		return this.courseHours;
	}

	/**
	 * �ι۵�λ
	 * 
	 */
	public void setVisitingUnit(String visitingUnit) {
		this.visitingUnit = visitingUnit;
	}

	/**
	 * �ι۵�λ
	 * 
	 */
	public String getVisitingUnit() {
		return this.visitingUnit;
	}

	/**
	 * �ι�����
	 * 
	 */
	public void setVisitorNumber(Integer visitorNumber) {
		this.visitorNumber = visitorNumber;
	}

	/**
	 * �ι�����
	 * 
	 */
	public Integer getVisitorNumber() {
		return this.visitorNumber;
	}

	/**
	 * �Ǽ���
	 * 
	 */
	public void setRegistrant(String registrant) {
		this.registrant = registrant;
	}

	/**
	 * �Ǽ���
	 * 
	 */
	public String getRegistrant() {
		return this.registrant;
	}

	/**
	 * 1ΪУ��2ΪУ��
	 * 
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	/**
	 * 1ΪУ��2ΪУ��
	 * 
	 */
	public Integer getFlag() {
		return this.flag;
	}

	/**
	 */
	public VisitingRegistration() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(VisitingRegistration that) {
		setId(that.getId());
		setCourseName(that.getCourseName());
		setProjectName(that.getProjectName());
		setTeacher(that.getTeacher());
		setClass_(that.getClass_());
		setStudentNumber(that.getStudentNumber());
		setDate(that.getDate());
		setLabroomVisiting(that.getLabroomVisiting());
		setCourseHours(that.getCourseHours());
		setVisitingUnit(that.getVisitingUnit());
		setVisitorNumber(that.getVisitorNumber());
		setRegistrant(that.getRegistrant());
		setFlag(that.getFlag());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("courseName=[").append(courseName).append("] ");
		buffer.append("projectName=[").append(projectName).append("] ");
		buffer.append("teacher=[").append(teacher).append("] ");
		buffer.append("class_=[").append(class_).append("] ");
		buffer.append("studentNumber=[").append(studentNumber).append("] ");
		buffer.append("date=[").append(date).append("] ");
		buffer.append("labroomVisiting=[").append(labroomVisiting).append("] ");
		buffer.append("courseHours=[").append(courseHours).append("] ");
		buffer.append("visitingUnit=[").append(visitingUnit).append("] ");
		buffer.append("visitorNumber=[").append(visitorNumber).append("] ");
		buffer.append("registrant=[").append(registrant).append("] ");
		buffer.append("flag=[").append(flag).append("] ");

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
		if (!(obj instanceof VisitingRegistration))
			return false;
		VisitingRegistration equalCheck = (VisitingRegistration) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
