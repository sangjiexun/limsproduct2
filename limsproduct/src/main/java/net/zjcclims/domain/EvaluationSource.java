package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllEvaluationSources", query = "select myEvaluationSource from EvaluationSource myEvaluationSource"),
		@NamedQuery(name = "findEvaluationSourceById", query = "select myEvaluationSource from EvaluationSource myEvaluationSource where myEvaluationSource.id = ?1"),
		@NamedQuery(name = "findEvaluationSourceByPrimaryKey", query = "select myEvaluationSource from EvaluationSource myEvaluationSource where myEvaluationSource.id = ?1"),
		@NamedQuery(name = "findEvaluationSourceByStudent", query = "select myEvaluationSource from EvaluationSource myEvaluationSource where myEvaluationSource.student = ?1"),
		@NamedQuery(name = "findEvaluationSourceByStudentContaining", query = "select myEvaluationSource from EvaluationSource myEvaluationSource where myEvaluationSource.student like ?1"),
		@NamedQuery(name = "findEvaluationSourceByTermId", query = "select myEvaluationSource from EvaluationSource myEvaluationSource where myEvaluationSource.termId = ?1") })
@Table(name = "evaluation_source")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "EvaluationSource")
public class EvaluationSource implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ����
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ѧ��id
	 * 
	 */

	@Column(name = "term_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer termId;
	
	/**
	 * ѧ��ѧ��
	 * 
	 */
	
	@Column(name = "student")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String student;
	
	@Column(name = "status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer status;
	
	/**
	 * ��ʦ����
	 * 
	 */
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "teacher", referencedColumnName = "username") })
	@XmlTransient
	User user;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "course_number", referencedColumnName = "course_number") })
	@XmlTransient
	SchoolCourseInfo schoolCourseInfo;

	/**
	 * ����
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ����
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ѧ��id
	 * 
	 */
	public void setTermId(Integer termId) {
		this.termId = termId;
	}

	/**
	 * ѧ��id
	 * 
	 */
	public Integer getTermId() {
		return this.termId;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * ѧ��id
	 * 
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * ѧ��ѧ��
	 * 
	 */
	public void setStudent(String student) {
		this.student = student;
	}

	/**
	 * ѧ��ѧ��
	 * 
	 */
	public String getStudent() {
		return this.student;
	}
	
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
	public EvaluationSource() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(EvaluationSource that) {
		setId(that.getId());
		setTermId(that.getTermId());
		setStudent(that.getStudent());
		setSchoolCourseInfo(that.getSchoolCourseInfo());
		setUser(that.getUser());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("termId=[").append(termId).append("] ");
		buffer.append("student=[").append(student).append("] ");

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
		if (!(obj instanceof EvaluationSource))
			return false;
		EvaluationSource equalCheck = (EvaluationSource) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
