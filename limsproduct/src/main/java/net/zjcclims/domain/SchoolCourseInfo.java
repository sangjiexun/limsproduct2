package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllSchoolCourseInfos", query = "select mySchoolCourseInfo from SchoolCourseInfo mySchoolCourseInfo"),
		@NamedQuery(name = "findSchoolCourseInfoByAcademyNumber", query = "select mySchoolCourseInfo from SchoolCourseInfo mySchoolCourseInfo where mySchoolCourseInfo.academyNumber = ?1"),
		@NamedQuery(name = "findSchoolCourseInfoByAcademyNumberContaining", query = "select mySchoolCourseInfo from SchoolCourseInfo mySchoolCourseInfo where mySchoolCourseInfo.academyNumber like ?1"),
		@NamedQuery(name = "findSchoolCourseInfoByCourseEnName", query = "select mySchoolCourseInfo from SchoolCourseInfo mySchoolCourseInfo where mySchoolCourseInfo.courseEnName = ?1"),
		@NamedQuery(name = "findSchoolCourseInfoByCourseEnNameContaining", query = "select mySchoolCourseInfo from SchoolCourseInfo mySchoolCourseInfo where mySchoolCourseInfo.courseEnName like ?1"),
		@NamedQuery(name = "findSchoolCourseInfoByCourseName", query = "select mySchoolCourseInfo from SchoolCourseInfo mySchoolCourseInfo where mySchoolCourseInfo.courseName = ?1"),
		@NamedQuery(name = "findSchoolCourseInfoByCourseNameContaining", query = "select mySchoolCourseInfo from SchoolCourseInfo mySchoolCourseInfo where mySchoolCourseInfo.courseName like ?1"),
		@NamedQuery(name = "findSchoolCourseInfoByCourseNumber", query = "select mySchoolCourseInfo from SchoolCourseInfo mySchoolCourseInfo where mySchoolCourseInfo.courseNumber = ?1"),
		@NamedQuery(name = "findSchoolCourseInfoByCourseNumberContaining", query = "select mySchoolCourseInfo from SchoolCourseInfo mySchoolCourseInfo where mySchoolCourseInfo.courseNumber like ?1"),
		@NamedQuery(name = "findSchoolCourseInfoByCreatedAt", query = "select mySchoolCourseInfo from SchoolCourseInfo mySchoolCourseInfo where mySchoolCourseInfo.createdAt = ?1"),
		@NamedQuery(name = "findSchoolCourseInfoByCreatedBy", query = "select mySchoolCourseInfo from SchoolCourseInfo mySchoolCourseInfo where mySchoolCourseInfo.createdBy = ?1"),
		@NamedQuery(name = "findSchoolCourseInfoByFlag", query = "select mySchoolCourseInfo from SchoolCourseInfo mySchoolCourseInfo where mySchoolCourseInfo.flag = ?1"),
		@NamedQuery(name = "findSchoolCourseInfoByPrimaryKey", query = "select mySchoolCourseInfo from SchoolCourseInfo mySchoolCourseInfo where mySchoolCourseInfo.courseNumber = ?1"),
		@NamedQuery(name = "findSchoolCourseInfoByTheoreticalHours", query = "select mySchoolCourseInfo from SchoolCourseInfo mySchoolCourseInfo where mySchoolCourseInfo.theoreticalHours = ?1"),
		@NamedQuery(name = "findSchoolCourseInfoByTotalHours", query = "select mySchoolCourseInfo from SchoolCourseInfo mySchoolCourseInfo where mySchoolCourseInfo.totalHours = ?1"),
		@NamedQuery(name = "findSchoolCourseInfoByUpdatedAt", query = "select mySchoolCourseInfo from SchoolCourseInfo mySchoolCourseInfo where mySchoolCourseInfo.updatedAt = ?1"),
		@NamedQuery(name = "findSchoolCourseInfoByUpdatedBy", query = "select mySchoolCourseInfo from SchoolCourseInfo mySchoolCourseInfo where mySchoolCourseInfo.updatedBy = ?1") })
@Table(name = "school_course_info")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "SchoolCourseInfo")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class SchoolCourseInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * �γ���Ϣ���
	 * 
	 */

	@Column(name = "course_number", length = 40, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	String courseNumber;
	/**
	 * �γ����
	 * 
	 */

	@Column(name = "course_name", length = 100)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String courseName;
	/**
	 * �γ�Ӣ�����
	 * 
	 */

	@Column(name = "course_en_name", length = 200)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String courseEnName;
	/**
	 * ѧԺ
	 * 
	 */

	@Column(name = "academy_number", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String academyNumber;
	/**
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createdAt;
	/**
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar updatedAt;
	/**
	 * ������
	 * 
	 */

	@Column(name = "created_by")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer createdBy;
	/**
	 * ������
	 * 
	 */

	@Column(name = "updated_by")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer updatedBy;
	/**
	 * ����ѧʱ
	 * 
	 */

	@Column(name = "theoretical_hours")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer theoreticalHours;
	/**
	 * ��ѧʱ
	 * 
	 */

	@Column(name = "total_hours")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer totalHours;
	/**
	 * �γ̱��Ϊ��1Ϊ�Խ��γ̣�0Ϊ�����
	 * 
	 */

	@Column(name = "flag")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer flag;

	@Column(name = "course_type", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String courseType;

	@Column(name = "course_type_name", length = 100)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String courseTypeName;

	@Column(name="credits")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer credits;

	@Column(name = "term", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String term;


	@Column(name = "course_nature", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String courseNature;

	@Column(name = "content", length = 255)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String content;
	/**
	 */
	@Column(name = "inspection_way", length = 255)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String inspectionWay;
	/**
	 */
	@Column(name = "resources", length = 255)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String resources;

	@Column(name = "goal", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String goal;

	/**
	 */
	@OneToMany(mappedBy = "schoolCourseInfo", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SchoolCourse> schoolCourses;
	/**
	 */
	@OneToMany(mappedBy = "schoolCourseInfo", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableAppointment> timetableAppointments;
	/**
	 */
	@OneToMany(mappedBy = "schoolCourseInfo", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationItem> operationItems;
	/**
	 */
	@OneToMany(mappedBy = "schoolCourseInfo", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableSelfCourse> timetableSelfCourses;

	/**
	 */
	@OneToMany(mappedBy = "schoolCourseInfoByClassId", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationOutline> operationOutlinesForClassId;
	/**
	 */
	@OneToMany(mappedBy = "schoolCourseInfoByFollowUpCourses", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationOutline> operationOutlinesForFollowUpCourses;
	/**
	 */
	@OneToMany(mappedBy = "schoolCourseInfoByFirstCourses", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationOutline> operationOutlinesForFirstCourses;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable( name = "course_type_related", joinColumns = { @JoinColumn(name = "course_number", referencedColumnName = "course_number", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "course_type_id", referencedColumnName = "major_number", nullable = false, updatable = false) })
	@XmlElement(name = "", namespace = "")
	java.util.Set<SchoolMajor> schoolMajor;
	/**
	 * �γ���Ϣ���
	 *
	 */

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "course_term_related", joinColumns = { @JoinColumn(name = "course_number", referencedColumnName = "course_number", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "term_id", referencedColumnName = "id", nullable = false, updatable = false) })
	@XmlElement(name = "", namespace = "")
	java.util.Set<SchoolTerm> schoolTerms;
	/**
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "course_teacher_related", joinColumns = { @JoinColumn(name = "course_number", referencedColumnName = "course_number", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "uesrname", referencedColumnName = "username", nullable = false, updatable = false) })
	@XmlElement(name = "", namespace = "")
	java.util.Set<User> users;

	@OneToMany(mappedBy = "schoolCourseInfo", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<CommonDocument> commonDocuments;

	@OneToMany(mappedBy = "schoolCourseInfo", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabroomTimetableRegister> labroomTimetableRegisters;

	public void setLabroomTimetableRegisters(Set<LabroomTimetableRegister> labroomTimetableRegisters) {
		this.labroomTimetableRegisters = labroomTimetableRegisters;
	}

	@JsonIgnore
	public Set<LabroomTimetableRegister> getLabroomTimetableRegisters() {
		if (labroomTimetableRegisters == null) {
			labroomTimetableRegisters = new java.util.LinkedHashSet<net.zjcclims.domain.LabroomTimetableRegister>();
		}
		return labroomTimetableRegisters;
	}

	public String getCourseNature() {
		return courseNature;
	}

	public void setCourseNature(String courseNature) {
		this.courseNature = courseNature;
	}

	public java.util.Set<CommonDocument> getCommonDocuments() {
		if (commonDocuments == null) {
			commonDocuments = new java.util.LinkedHashSet<CommonDocument>();
		}
		return commonDocuments;
	}

	public void setCommonDocuments(java.util.Set<CommonDocument> commonDocuments) {
		this.commonDocuments = commonDocuments;
	}

	public Set<SchoolMajor> getSchoolMajor() {
		return schoolMajor;
	}

	public void setSchoolMajor(Set<SchoolMajor> schoolMajor) {
		this.schoolMajor = schoolMajor;
	}

	public Set<SchoolTerm> getSchoolTerms() {
		return schoolTerms;
	}

	public void setSchoolTerms(Set<SchoolTerm> schoolTerms) {
		this.schoolTerms = schoolTerms;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}


	/**
	 */
	@OneToMany(mappedBy = "schoolCourseInfo", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.PreTimetableAppointment> preTimetableAppointments;

	/**
	 */
	public void setPreTimetableAppointments(Set<PreTimetableAppointment> preTimetableAppointments) {
		this.preTimetableAppointments = preTimetableAppointments;
	}

	/**
	 */
	@JsonIgnore
	public Set<PreTimetableAppointment> getPreTimetableAppointments() {
		if (preTimetableAppointments == null) {
			preTimetableAppointments = new java.util.LinkedHashSet<net.zjcclims.domain.PreTimetableAppointment>();
		}
		return preTimetableAppointments;
	}

	/**
	 * �γ���Ϣ���
	 * 
	 */
	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}

	/**
	 * �γ���Ϣ���
	 * 
	 */
	public String getCourseNumber() {
		return this.courseNumber;
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
	 * �γ�Ӣ�����
	 * 
	 */
	public void setCourseEnName(String courseEnName) {
		this.courseEnName = courseEnName;
	}

	/**
	 * �γ�Ӣ�����
	 * 
	 */
	public String getCourseEnName() {
		return this.courseEnName;
	}

	/**
	 * ѧԺ
	 * 
	 */
	public void setAcademyNumber(String academyNumber) {
		this.academyNumber = academyNumber;
	}

	/**
	 * ѧԺ
	 * 
	 */
	public String getAcademyNumber() {
		return this.academyNumber;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public Calendar getCreatedAt() {
		return this.createdAt;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public void setUpdatedAt(Calendar updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public Calendar getUpdatedAt() {
		return this.updatedAt;
	}

	/**
	 * ������
	 * 
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * ������
	 * 
	 */
	public Integer getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * ������
	 * 
	 */
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * ������
	 * 
	 */
	public Integer getUpdatedBy() {
		return this.updatedBy;
	}

	/**
	 * ����ѧʱ
	 * 
	 */
	public void setTheoreticalHours(Integer theoreticalHours) {
		this.theoreticalHours = theoreticalHours;
	}

	/**
	 * ����ѧʱ
	 * 
	 */
	public Integer getTheoreticalHours() {
		return this.theoreticalHours;
	}

	/**
	 * ��ѧʱ
	 * 
	 */
	public void setTotalHours(Integer totalHours) {
		this.totalHours = totalHours;
	}

	/**
	 * ��ѧʱ
	 * 
	 */
	public Integer getTotalHours() {
		return this.totalHours;
	}

	/**
	 * �γ̱��Ϊ��1Ϊ�Խ��γ̣�0Ϊ�����
	 * 
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	/**
	 * �γ̱��Ϊ��1Ϊ�Խ��γ̣�0Ϊ�����
	 * 
	 */
	public Integer getFlag() {
		return this.flag;
	}

	public Integer getCredits() {
		return credits;
	}

	public void setCredits(Integer credits) {
		this.credits = credits;
	}


	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getCourseType() {
		return this.courseType;
	}

	public void setCourseTypeName(String courseTypeName) {
		this.courseTypeName = courseTypeName;
	}

	public String getCourseTypeName() {
		return this.courseTypeName;
	}

	/**
	 */
	public void setSchoolCourses(Set<SchoolCourse> schoolCourses) {
		this.schoolCourses = schoolCourses;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getInspectionWay() {
		return inspectionWay;
	}

	public void setInspectionWay(String inspectionWay) {
		this.inspectionWay = inspectionWay;
	}

	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}
	
	/**
	 */
	@JsonIgnore
	public Set<SchoolCourse> getSchoolCourses() {
		if (schoolCourses == null) {
			schoolCourses = new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourse>();
		}
		return schoolCourses;
	}

	/**
	 */
	public void setTimetableAppointments(Set<TimetableAppointment> timetableAppointments) {
		this.timetableAppointments = timetableAppointments;
	}

	/**
	 */
	@JsonIgnore
	public Set<TimetableAppointment> getTimetableAppointments() {
		if (timetableAppointments == null) {
			timetableAppointments = new java.util.LinkedHashSet<net.zjcclims.domain.TimetableAppointment>();
		}
		return timetableAppointments;
	}

	/**
	 */
	public void setOperationItems(Set<OperationItem> operationItems) {
		this.operationItems = operationItems;
	}

	/**
	 */
	@JsonIgnore
	public Set<OperationItem> getOperationItems() {
		if (operationItems == null) {
			operationItems = new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>();
		}
		return operationItems;
	}

	/**
	 */
	public void setTimetableSelfCourses(Set<TimetableSelfCourse> timetableSelfCourses) {
		this.timetableSelfCourses = timetableSelfCourses;
	}

	/**
	 */
	@JsonIgnore
	public Set<TimetableSelfCourse> getTimetableSelfCourses() {
		if (timetableSelfCourses == null) {
			timetableSelfCourses = new java.util.LinkedHashSet<net.zjcclims.domain.TimetableSelfCourse>();
		}
		return timetableSelfCourses;
	}


	/**
	 */
	public void setOperationOutlinesForClassId(Set<OperationOutline> operationOutlinesForClassId) {
		this.operationOutlinesForClassId = operationOutlinesForClassId;
	}

	/**
	 */
	@JsonIgnore
	public Set<OperationOutline> getOperationOutlinesForClassId() {
		if (operationOutlinesForClassId == null) {
			operationOutlinesForClassId = new java.util.LinkedHashSet<net.zjcclims.domain.OperationOutline>();
		}
		return operationOutlinesForClassId;
	}
	/**
	 */
	public void setOperationOutlinesForFollowUpCourses(Set<OperationOutline> operationOutlinesForFollowUpCourses) {
		this.operationOutlinesForFollowUpCourses = operationOutlinesForFollowUpCourses;
	}

	/**
	 */
	@JsonIgnore
	public Set<OperationOutline> getOperationOutlinesForFollowUpCourses() {
		if (operationOutlinesForFollowUpCourses == null) {
			operationOutlinesForFollowUpCourses = new java.util.LinkedHashSet<net.zjcclims.domain.OperationOutline>();
		}
		return operationOutlinesForFollowUpCourses;
	}

	/**
	 */
	public void setOperationOutlinesForFirstCourses(Set<OperationOutline> operationOutlinesForFirstCourses) {
		this.operationOutlinesForFirstCourses = operationOutlinesForFirstCourses;
	}

	/**
	 */
	@JsonIgnore
	public Set<OperationOutline> getOperationOutlinesForFirstCourses() {
		if (operationOutlinesForFirstCourses == null) {
			operationOutlinesForFirstCourses = new java.util.LinkedHashSet<net.zjcclims.domain.OperationOutline>();
		}
		return operationOutlinesForFirstCourses;
	}

	/**
	 */
	public SchoolCourseInfo() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(SchoolCourseInfo that) {
		setCourseNumber(that.getCourseNumber());
		setCourseName(that.getCourseName());
		setCourseEnName(that.getCourseEnName());
		setAcademyNumber(that.getAcademyNumber());
		setCreatedAt(that.getCreatedAt());
		setUpdatedAt(that.getUpdatedAt());
		setCreatedBy(that.getCreatedBy());
		setUpdatedBy(that.getUpdatedBy());
		setTheoreticalHours(that.getTheoreticalHours());
		setTotalHours(that.getTotalHours());
		setFlag(that.getFlag());
		setSchoolCourses(new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourse>(that.getSchoolCourses()));
		setTimetableAppointments(new java.util.LinkedHashSet<net.zjcclims.domain.TimetableAppointment>(that.getTimetableAppointments()));
		setOperationItems(new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>(that.getOperationItems()));
		setTimetableSelfCourses(new java.util.LinkedHashSet<net.zjcclims.domain.TimetableSelfCourse>(that.getTimetableSelfCourses()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("courseNumber=[").append(courseNumber).append("] ");
		buffer.append("courseName=[").append(courseName).append("] ");
		buffer.append("courseEnName=[").append(courseEnName).append("] ");
		buffer.append("academyNumber=[").append(academyNumber).append("] ");
		buffer.append("createdAt=[").append(createdAt).append("] ");
		buffer.append("updatedAt=[").append(updatedAt).append("] ");
		buffer.append("createdBy=[").append(createdBy).append("] ");
		buffer.append("updatedBy=[").append(updatedBy).append("] ");
		buffer.append("theoreticalHours=[").append(theoreticalHours).append("] ");
		buffer.append("totalHours=[").append(totalHours).append("] ");
		buffer.append("flag=[").append(flag).append("] ");

		return buffer.toString();
	}

	/**
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((courseNumber == null) ? 0 : courseNumber.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof SchoolCourseInfo))
			return false;
		SchoolCourseInfo equalCheck = (SchoolCourseInfo) obj;
		if ((courseNumber == null && equalCheck.courseNumber != null) || (courseNumber != null && equalCheck.courseNumber == null))
			return false;
		if (courseNumber != null && !courseNumber.equals(equalCheck.courseNumber))
			return false;
		return true;
	}
}
