package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllSchoolCourseSchedules", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule"),
		@NamedQuery(name = "findSchoolCourseScheduleByAcademy", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.academy = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByAcademyContaining", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.academy like ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByBuilding", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.building = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByBuildingContaining", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.building like ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByClassCode", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.classCode = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByClassCodeContaining", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.classCode like ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByCourseId", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.courseId = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByCourseIdContaining", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.courseId like ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByCourseName", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.courseName = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByCourseNameContaining", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.courseName like ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByCourseType", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.courseType = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByEndClassSection", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.endClassSection = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByEndWeek", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.endWeek = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleById", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.id = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByIsShared", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.isShared = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByPeople", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.people = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByPrimaryKey", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.id = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByRemark", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.remark = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByRemarkContaining", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.remark like ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByStartClassSection", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.startClassSection = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByStartWeek", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.startWeek = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByStudentNumber", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.studentNumber = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByTeacher", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.teacher = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByTeacherContaining", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.teacher like ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByTeacherId", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.teacherId = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByTeacherIdContaining", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.teacherId like ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByTerm", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.term = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByTermContaining", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.term like ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByWeekType", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.weekType = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByWeekTypeContaining", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.weekType like ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByWeekday", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.weekday = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByXkkh", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.xkkh = ?1"),
		@NamedQuery(name = "findSchoolCourseScheduleByXkkhContaining", query = "select mySchoolCourseSchedule from SchoolCourseSchedule mySchoolCourseSchedule where mySchoolCourseSchedule.xkkh like ?1") })
@Table(name = "school_course_schedule")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "SchoolCourseSchedule")
public class SchoolCourseSchedule implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * �γ̰��ű�
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	Integer id;
	/**
	 * ѧ��
	 * 
	 */

	@Column(name = "term", length = 11)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String term;
	/**
	 * �γ̴���
	 * 
	 */

	@Column(name = "course_id", length = 60)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String courseId;
	/**
	 * �γ�����
	 * 
	 */

	@Column(name = "course_name", length = 60)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String courseName;
	/**
	 * ��Ӧ������xkkh��ƽ�а����
	 * 
	 */

	@Column(name = "xkkh", length = 100)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String xkkh;
	/**
	 * ��ѧ�����
	 * 
	 */

	@Column(name = "class_code", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String classCode;
	/**
	 * ��ѧ����
	 * 
	 */

	@Column(name = "building", length = 100)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String building;
	/**
	 * ��ʼ��
	 * 
	 */

	@Column(name = "start_week")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer startWeek;
	/**
	 * ������
	 * 
	 */

	@Column(name = "end_week")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer endWeek;
	/**
	 * �ܴ�����,���ܣ�˫�ܣ���˫��
	 * 
	 */

	@Column(name = "week_type", length = 10)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String weekType;
	/**
	 * ���ڼ�
	 * 
	 */

	@Column(name = "weekday")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer weekday;
	/**
	 * ��ʼ�Ľڴ�
	 * 
	 */

	@Column(name = "start_class_section")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer startClassSection;
	/**
	 * �����Ľڴ�
	 * 
	 */

	@Column(name = "end_class_section")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer endClassSection;
	/**
	 * �Ͽ���ʦ����
	 * 
	 */

	@Column(name = "teacher_id", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String teacherId;
	/**
	 * ��ʦ����
	 * 
	 */

	@Column(name = "teacher", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String teacher;
	/**
	 */

	@Column(name = "student_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer studentNumber;
	/**
	 * ѧԺ
	 * 
	 */

	@Column(name = "academy", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String academy;
	/**
	 * �Ƿ��ǹ�������͵�,1���͵ģ�0��ʦά����
	 * 
	 */

	@Column(name = "is_shared", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Boolean isShared;
	/**
	 * ��ע
	 * 
	 */

	@Column(name = "remark")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String remark;
	/**
	 * ����
	 * 
	 */

	@Column(name = "people")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer people;
	/**
	 * �γ�����
	 * 
	 */

	@Column(name = "course_type")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer courseType;

	/**
	 * �γ̰��ű�
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * �γ̰��ű�
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ѧ��
	 * 
	 */
	public void setTerm(String term) {
		this.term = term;
	}

	/**
	 * ѧ��
	 * 
	 */
	public String getTerm() {
		return this.term;
	}

	/**
	 * �γ̴���
	 * 
	 */
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	/**
	 * �γ̴���
	 * 
	 */
	public String getCourseId() {
		return this.courseId;
	}

	/**
	 * �γ�����
	 * 
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * �γ�����
	 * 
	 */
	public String getCourseName() {
		return this.courseName;
	}

	/**
	 * ��Ӧ������xkkh��ƽ�а����
	 * 
	 */
	public void setXkkh(String xkkh) {
		this.xkkh = xkkh;
	}

	/**
	 * ��Ӧ������xkkh��ƽ�а����
	 * 
	 */
	public String getXkkh() {
		return this.xkkh;
	}

	/**
	 * ��ѧ�����
	 * 
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	/**
	 * ��ѧ�����
	 * 
	 */
	public String getClassCode() {
		return this.classCode;
	}

	/**
	 * ��ѧ����
	 * 
	 */
	public void setBuilding(String building) {
		this.building = building;
	}

	/**
	 * ��ѧ����
	 * 
	 */
	public String getBuilding() {
		return this.building;
	}

	/**
	 * ��ʼ��
	 * 
	 */
	public void setStartWeek(Integer startWeek) {
		this.startWeek = startWeek;
	}

	/**
	 * ��ʼ��
	 * 
	 */
	public Integer getStartWeek() {
		return this.startWeek;
	}

	/**
	 * ������
	 * 
	 */
	public void setEndWeek(Integer endWeek) {
		this.endWeek = endWeek;
	}

	/**
	 * ������
	 * 
	 */
	public Integer getEndWeek() {
		return this.endWeek;
	}

	/**
	 * �ܴ�����,���ܣ�˫�ܣ���˫��
	 * 
	 */
	public void setWeekType(String weekType) {
		this.weekType = weekType;
	}

	/**
	 * �ܴ�����,���ܣ�˫�ܣ���˫��
	 * 
	 */
	public String getWeekType() {
		return this.weekType;
	}

	/**
	 * ���ڼ�
	 * 
	 */
	public void setWeekday(Integer weekday) {
		this.weekday = weekday;
	}

	/**
	 * ���ڼ�
	 * 
	 */
	public Integer getWeekday() {
		return this.weekday;
	}

	/**
	 * ��ʼ�Ľڴ�
	 * 
	 */
	public void setStartClassSection(Integer startClassSection) {
		this.startClassSection = startClassSection;
	}

	/**
	 * ��ʼ�Ľڴ�
	 * 
	 */
	public Integer getStartClassSection() {
		return this.startClassSection;
	}

	/**
	 * �����Ľڴ�
	 * 
	 */
	public void setEndClassSection(Integer endClassSection) {
		this.endClassSection = endClassSection;
	}

	/**
	 * �����Ľڴ�
	 * 
	 */
	public Integer getEndClassSection() {
		return this.endClassSection;
	}

	/**
	 * �Ͽ���ʦ����
	 * 
	 */
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	/**
	 * �Ͽ���ʦ����
	 * 
	 */
	public String getTeacherId() {
		return this.teacherId;
	}

	/**
	 * ��ʦ����
	 * 
	 */
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	/**
	 * ��ʦ����
	 * 
	 */
	public String getTeacher() {
		return this.teacher;
	}

	/**
	 */
	public void setStudentNumber(Integer studentNumber) {
		this.studentNumber = studentNumber;
	}

	/**
	 */
	public Integer getStudentNumber() {
		return this.studentNumber;
	}

	/**
	 * ѧԺ
	 * 
	 */
	public void setAcademy(String academy) {
		this.academy = academy;
	}

	/**
	 * ѧԺ
	 * 
	 */
	public String getAcademy() {
		return this.academy;
	}

	/**
	 * �Ƿ��ǹ�������͵�,1���͵ģ�0��ʦά����
	 * 
	 */
	public void setIsShared(Boolean isShared) {
		this.isShared = isShared;
	}

	/**
	 * �Ƿ��ǹ�������͵�,1���͵ģ�0��ʦά����
	 * 
	 */
	public Boolean getIsShared() {
		return this.isShared;
	}

	/**
	 * ��ע
	 * 
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * ��ע
	 * 
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * ����
	 * 
	 */
	public void setPeople(Integer people) {
		this.people = people;
	}

	/**
	 * ����
	 * 
	 */
	public Integer getPeople() {
		return this.people;
	}

	/**
	 * �γ�����
	 * 
	 */
	public void setCourseType(Integer courseType) {
		this.courseType = courseType;
	}

	/**
	 * �γ�����
	 * 
	 */
	public Integer getCourseType() {
		return this.courseType;
	}

	/**
	 */
	public SchoolCourseSchedule() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(SchoolCourseSchedule that) {
		setId(that.getId());
		setTerm(that.getTerm());
		setCourseId(that.getCourseId());
		setCourseName(that.getCourseName());
		setXkkh(that.getXkkh());
		setClassCode(that.getClassCode());
		setBuilding(that.getBuilding());
		setStartWeek(that.getStartWeek());
		setEndWeek(that.getEndWeek());
		setWeekType(that.getWeekType());
		setWeekday(that.getWeekday());
		setStartClassSection(that.getStartClassSection());
		setEndClassSection(that.getEndClassSection());
		setTeacherId(that.getTeacherId());
		setTeacher(that.getTeacher());
		setStudentNumber(that.getStudentNumber());
		setAcademy(that.getAcademy());
		setIsShared(that.getIsShared());
		setRemark(that.getRemark());
		setPeople(that.getPeople());
		setCourseType(that.getCourseType());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("term=[").append(term).append("] ");
		buffer.append("courseId=[").append(courseId).append("] ");
		buffer.append("courseName=[").append(courseName).append("] ");
		buffer.append("xkkh=[").append(xkkh).append("] ");
		buffer.append("classCode=[").append(classCode).append("] ");
		buffer.append("building=[").append(building).append("] ");
		buffer.append("startWeek=[").append(startWeek).append("] ");
		buffer.append("endWeek=[").append(endWeek).append("] ");
		buffer.append("weekType=[").append(weekType).append("] ");
		buffer.append("weekday=[").append(weekday).append("] ");
		buffer.append("startClassSection=[").append(startClassSection).append("] ");
		buffer.append("endClassSection=[").append(endClassSection).append("] ");
		buffer.append("teacherId=[").append(teacherId).append("] ");
		buffer.append("teacher=[").append(teacher).append("] ");
		buffer.append("studentNumber=[").append(studentNumber).append("] ");
		buffer.append("academy=[").append(academy).append("] ");
		buffer.append("isShared=[").append(isShared).append("] ");
		buffer.append("remark=[").append(remark).append("] ");
		buffer.append("people=[").append(people).append("] ");
		buffer.append("courseType=[").append(courseType).append("] ");

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
		if (!(obj instanceof SchoolCourseSchedule))
			return false;
		SchoolCourseSchedule equalCheck = (SchoolCourseSchedule) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
