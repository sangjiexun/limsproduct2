package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Calendar;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllSchoolMajors", query = "select mySchoolMajor from SchoolMajor mySchoolMajor"),
		@NamedQuery(name = "findSchoolMajorByCreatedAt", query = "select mySchoolMajor from SchoolMajor mySchoolMajor where mySchoolMajor.createdAt = ?1"),
		@NamedQuery(name = "findSchoolMajorByCreatedBy", query = "select mySchoolMajor from SchoolMajor mySchoolMajor where mySchoolMajor.createdBy = ?1"),
		@NamedQuery(name = "findSchoolMajorByCreatedByContaining", query = "select mySchoolMajor from SchoolMajor mySchoolMajor where mySchoolMajor.createdBy like ?1"),
		@NamedQuery(name = "findSchoolMajorByMajorName", query = "select mySchoolMajor from SchoolMajor mySchoolMajor where mySchoolMajor.majorName = ?1"),
		@NamedQuery(name = "findSchoolMajorByMajorNameContaining", query = "select mySchoolMajor from SchoolMajor mySchoolMajor where mySchoolMajor.majorName like ?1"),
		@NamedQuery(name = "findSchoolMajorByMajorNumber", query = "select mySchoolMajor from SchoolMajor mySchoolMajor where mySchoolMajor.majorNumber = ?1"),
		@NamedQuery(name = "findSchoolMajorByMajorNumberContaining", query = "select mySchoolMajor from SchoolMajor mySchoolMajor where mySchoolMajor.majorNumber like ?1"),
		@NamedQuery(name = "findSchoolMajorByPrimaryKey", query = "select mySchoolMajor from SchoolMajor mySchoolMajor where mySchoolMajor.majorNumber = ?1"),
		@NamedQuery(name = "findSchoolMajorByStudentType", query = "select mySchoolMajor from SchoolMajor mySchoolMajor where mySchoolMajor.studentType = ?1"),
		@NamedQuery(name = "findSchoolMajorByStudentTypeContaining", query = "select mySchoolMajor from SchoolMajor mySchoolMajor where mySchoolMajor.studentType like ?1"),
		@NamedQuery(name = "findSchoolMajorByUpdatedAt", query = "select mySchoolMajor from SchoolMajor mySchoolMajor where mySchoolMajor.updatedAt = ?1"),
		@NamedQuery(name = "findSchoolMajorByUpdatedBy", query = "select mySchoolMajor from SchoolMajor mySchoolMajor where mySchoolMajor.updatedBy = ?1"),
		@NamedQuery(name = "findSchoolMajorByUpdatedByContaining", query = "select mySchoolMajor from SchoolMajor mySchoolMajor where mySchoolMajor.updatedBy like ?1") })
@Table(name = "school_major")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "SchoolMajor")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class SchoolMajor implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * רҵ����
	 * 
	 */

	@Column(name = "major_number", length = 40, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	String majorNumber;
	/**
	 * רҵ���
	 * 
	 */

	@Column(name = "major_name", length = 100)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String majorName;
	/**
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createdAt;
	/**
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar updatedAt;
	/**
	 * ������
	 * 
	 */

	@Column(name = "created_by", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String createdBy;
	/**
	 * ������
	 * 
	 */

	@Column(name = "updated_by", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String updatedBy;
	/**
	 * �༶����
	 * 
	 */

	@Column(name = "student_type", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String studentType;

	@OneToMany(mappedBy = "schoolMajor", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectAppMajor> projectAppMajors;

	@OneToMany(mappedBy = "schoolMajor", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SchoolCourse> schoolCourses;

	public void setSchoolCourses(Set<SchoolCourse> schoolCourses) {
		this.schoolCourses = schoolCourses;
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

	@OneToMany(mappedBy = "schoolMajor", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationItem> operationItems;

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

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "m_outline_major", joinColumns = { @JoinColumn(name = "school_major", referencedColumnName = "major_number", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "operation_outline_id", referencedColumnName = "id", nullable = false, updatable = false) })
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationOutline> operationOutlines;

	public void setOperationOutlines(Set<OperationOutline> operationOutlines) {
		this.operationOutlines = operationOutlines;
	}

	@JsonIgnore
	public Set<OperationOutline> getOperationOutlines() {
		if (operationOutlines == null) {
			operationOutlines = new java.util.LinkedHashSet<net.zjcclims.domain.OperationOutline>();
		}
		return operationOutlines;
	}



	@ManyToMany(mappedBy = "schoolMajor", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<SchoolCourseInfo> schoolCourseInfos;

	public Set<SchoolCourseInfo> getSchoolCourseInfos() {
		return schoolCourseInfos;
	}

	public void setSchoolCourseInfos(Set<SchoolCourseInfo> schoolCourseInfos) {
		this.schoolCourseInfos = schoolCourseInfos;
	}

	public void setProjectAppMajors(Set<ProjectAppMajor> projectAppMajors) {
		this.projectAppMajors = projectAppMajors;
	}

	/**
	 */
	public Set<ProjectAppMajor> getProjectAppMajors() {
		if (projectAppMajors == null) {
			projectAppMajors = new java.util.LinkedHashSet<ProjectAppMajor>();
		}
		return projectAppMajors;
	}


	/**
	 * רҵ����
	 * 
	 */
	public void setMajorNumber(String majorNumber) {
		this.majorNumber = majorNumber;
	}

	/**
	 * רҵ����
	 * 
	 */
	public String getMajorNumber() {
		return this.majorNumber;
	}

	/**
	 * רҵ���
	 * 
	 */
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	/**
	 * רҵ���
	 * 
	 */
	public String getMajorName() {
		return this.majorName;
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
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * ������
	 * 
	 */
	public String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * ������
	 * 
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * ������
	 * 
	 */
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	/**
	 * �༶����
	 * 
	 */
	public void setStudentType(String studentType) {
		this.studentType = studentType;
	}

	/**
	 * �༶����
	 * 
	 */
	public String getStudentType() {
		return this.studentType;
	}


	/**
	 *//*
	public void setOperationItems(Set<OperationItem> operationItems) {
		this.operationItems = operationItems;
	}

	*//**
	 *//*
	@JsonIgnore
	public Set<OperationItem> getOperationItems() {
		if (operationItems == null) {
			operationItems = new java.util.LinkedHashSet<net.gvsun.domain.OperationItem>();
		}
		return operationItems;
	}
*/




	/**
	 */
	public SchoolMajor() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(SchoolMajor that) {
		setMajorNumber(that.getMajorNumber());
		setMajorName(that.getMajorName());
		setCreatedAt(that.getCreatedAt());
		setUpdatedAt(that.getUpdatedAt());
		setCreatedBy(that.getCreatedBy());
		setUpdatedBy(that.getUpdatedBy());
		setStudentType(that.getStudentType());
		setSchoolCourses(new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourse>(that.getSchoolCourses()));
		setOperationItems(new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>(that.getOperationItems()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *	
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("majorNumber=[").append(majorNumber).append("] ");
		buffer.append("majorName=[").append(majorName).append("] ");
		buffer.append("createdAt=[").append(createdAt).append("] ");
		buffer.append("updatedAt=[").append(updatedAt).append("] ");
		buffer.append("createdBy=[").append(createdBy).append("] ");
		buffer.append("updatedBy=[").append(updatedBy).append("] ");
		buffer.append("studentType=[").append(studentType).append("] ");

		return buffer.toString();
	}

	/**
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((majorNumber == null) ? 0 : majorNumber.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof SchoolMajor))
			return false;
		SchoolMajor equalCheck = (SchoolMajor) obj;
		if ((majorNumber == null && equalCheck.majorNumber != null) || (majorNumber != null && equalCheck.majorNumber == null))
			return false;
		if (majorNumber != null && !majorNumber.equals(equalCheck.majorNumber))
			return false;
		return true;
	}
}
