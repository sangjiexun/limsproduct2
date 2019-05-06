package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllMOutlineCourses", query = "select myMOutlineCourse from MOutlineCourse myMOutlineCourse"),
		@NamedQuery(name = "findMOutlineCourseByCombine", query = "select myMOutlineCourse from MOutlineCourse myMOutlineCourse where myMOutlineCourse.combine = ?1"),
		@NamedQuery(name = "findMOutlineCourseByCombineContaining", query = "select myMOutlineCourse from MOutlineCourse myMOutlineCourse where myMOutlineCourse.combine like ?1"),
		@NamedQuery(name = "findMOutlineCourseByFlag", query = "select myMOutlineCourse from MOutlineCourse myMOutlineCourse where myMOutlineCourse.flag = ?1"),
		@NamedQuery(name = "findMOutlineCourseByFlagContaining", query = "select myMOutlineCourse from MOutlineCourse myMOutlineCourse where myMOutlineCourse.flag like ?1"),
		@NamedQuery(name = "findMOutlineCourseById", query = "select myMOutlineCourse from MOutlineCourse myMOutlineCourse where myMOutlineCourse.id = ?1"),
		@NamedQuery(name = "findMOutlineCourseByPrimaryKey", query = "select myMOutlineCourse from MOutlineCourse myMOutlineCourse where myMOutlineCourse.id = ?1") })
@Table( name = "m_outline_course")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "MOutlineCourse")
public class MOutlineCourse implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ���id
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * flag(0 ���� 1 ѡ��)
	 * 
	 */

	@Column(name = "flag", length = 1)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String flag;
	/**
	 * ѡ�޵����flag ��D1 D2
	 * 
	 */

	@Column(name = "combine")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String combine;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "course_number", referencedColumnName = "course_number") })
	@XmlTransient
	SchoolCourseInfo schoolCourseInfo;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "outline_id", referencedColumnName = "id") })
	@XmlTransient
	OperationOutline operationOutline;

	/**
	 * ���id
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ���id
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * flag(0 ���� 1 ѡ��)
	 * 
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * flag(0 ���� 1 ѡ��)
	 * 
	 */
	public String getFlag() {
		return this.flag;
	}

	/**
	 * ѡ�޵����flag ��D1 D2
	 * 
	 */
	public void setCombine(String combine) {
		this.combine = combine;
	}

	/**
	 * ѡ�޵����flag ��D1 D2
	 * 
	 */
	public String getCombine() {
		return this.combine;
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
	public void setOperationOutline(OperationOutline operationOutline) {
		this.operationOutline = operationOutline;
	}

	/**
	 */
	@JsonIgnore
	public OperationOutline getOperationOutline() {
		return operationOutline;
	}

	/**
	 */
	public MOutlineCourse() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(MOutlineCourse that) {
		setId(that.getId());
		setFlag(that.getFlag());
		setCombine(that.getCombine());
		setSchoolCourseInfo(that.getSchoolCourseInfo());
		setOperationOutline(that.getOperationOutline());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("flag=[").append(flag).append("] ");
		buffer.append("combine=[").append(combine).append("] ");

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
		if (!(obj instanceof MOutlineCourse))
			return false;
		MOutlineCourse equalCheck = (MOutlineCourse) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
