package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllEvaluationResults", query = "select myEvaluationResult from EvaluationResult myEvaluationResult"),
		@NamedQuery(name = "findEvaluationResultByCreateTime", query = "select myEvaluationResult from EvaluationResult myEvaluationResult where myEvaluationResult.createTime = ?1"),
		@NamedQuery(name = "findEvaluationResultById", query = "select myEvaluationResult from EvaluationResult myEvaluationResult where myEvaluationResult.id = ?1"),
		@NamedQuery(name = "findEvaluationResultByMemo", query = "select myEvaluationResult from EvaluationResult myEvaluationResult where myEvaluationResult.memo = ?1"),
		@NamedQuery(name = "findEvaluationResultByMemoContaining", query = "select myEvaluationResult from EvaluationResult myEvaluationResult where myEvaluationResult.memo like ?1"),
		@NamedQuery(name = "findEvaluationResultByPrimaryKey", query = "select myEvaluationResult from EvaluationResult myEvaluationResult where myEvaluationResult.id = ?1"),
		@NamedQuery(name = "findEvaluationResultByScore", query = "select myEvaluationResult from EvaluationResult myEvaluationResult where myEvaluationResult.score = ?1"),
		@NamedQuery(name = "findEvaluationResultByStudentName", query = "select myEvaluationResult from EvaluationResult myEvaluationResult where myEvaluationResult.studentName = ?1"),
		@NamedQuery(name = "findEvaluationResultByStudentNameContaining", query = "select myEvaluationResult from EvaluationResult myEvaluationResult where myEvaluationResult.studentName like ?1"),
		@NamedQuery(name = "findEvaluationResultByStudentNo", query = "select myEvaluationResult from EvaluationResult myEvaluationResult where myEvaluationResult.studentNo = ?1"),
		@NamedQuery(name = "findEvaluationResultByStudentNoContaining", query = "select myEvaluationResult from EvaluationResult myEvaluationResult where myEvaluationResult.studentNo like ?1"),
		@NamedQuery(name = "findEvaluationResultByTeacherName", query = "select myEvaluationResult from EvaluationResult myEvaluationResult where myEvaluationResult.teacherName = ?1"),
		@NamedQuery(name = "findEvaluationResultByTeacherNameContaining", query = "select myEvaluationResult from EvaluationResult myEvaluationResult where myEvaluationResult.teacherName like ?1"),
		@NamedQuery(name = "findEvaluationResultByTeacherNo", query = "select myEvaluationResult from EvaluationResult myEvaluationResult where myEvaluationResult.teacherNo = ?1"),
		@NamedQuery(name = "findEvaluationResultByTeacherNoContaining", query = "select myEvaluationResult from EvaluationResult myEvaluationResult where myEvaluationResult.teacherNo like ?1"),
		@NamedQuery(name = "findEvaluationResultByTermId", query = "select myEvaluationResult from EvaluationResult myEvaluationResult where myEvaluationResult.termId = ?1") })
@Table(name = "evaluation_result")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "EvaluationResult")
public class EvaluationResult implements Serializable {
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
	 * ѧ��
	 * 
	 */

	@Column(name = "term_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer termId;
	/**
	 * ��ʦ����
	 * 
	 */

	@Column(name = "teacher_no", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String teacherNo;
	/**
	 * ��ʦ����
	 * 
	 */

	@Column(name = "teacher_name", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String teacherName;
	/**
	 * ѧ��ѧ��
	 * 
	 */

	@Column(name = "student_no", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String studentNo;
	/**
	 * ѧ������
	 * 
	 */

	@Column(name = "student_name", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String studentName;
	/**
	 * ����÷�
	 * 
	 */

	@Column(name = "score")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer score;
	/**
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createTime;
	/**
	 * ��ע
	 * 
	 */

	@Column(name = "memo")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String memo;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "content_id", referencedColumnName = "id") })
	@XmlTransient
	EvaluationContent evaluationContent;
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
	 * ѧ��
	 * 
	 */
	public void setTermId(Integer termId) {
		this.termId = termId;
	}

	/**
	 * ѧ��
	 * 
	 */
	public Integer getTermId() {
		return this.termId;
	}

	/**
	 * ��ʦ����
	 * 
	 */
	public void setTeacherNo(String teacherNo) {
		this.teacherNo = teacherNo;
	}

	/**
	 * ��ʦ����
	 * 
	 */
	public String getTeacherNo() {
		return this.teacherNo;
	}

	/**
	 * ��ʦ����
	 * 
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	/**
	 * ��ʦ����
	 * 
	 */
	public String getTeacherName() {
		return this.teacherName;
	}

	/**
	 * ѧ��ѧ��
	 * 
	 */
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	/**
	 * ѧ��ѧ��
	 * 
	 */
	public String getStudentNo() {
		return this.studentNo;
	}

	/**
	 * ѧ������
	 * 
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	/**
	 * ѧ������
	 * 
	 */
	public String getStudentName() {
		return this.studentName;
	}

	/**
	 * ����÷�
	 * 
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * ����÷�
	 * 
	 */
	public Integer getScore() {
		return this.score;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public Calendar getCreateTime() {
		return this.createTime;
	}

	/**
	 * ��ע
	 * 
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * ��ע
	 * 
	 */
	public String getMemo() {
		return this.memo;
	}

	/**
	 */
	public void setEvaluationContent(EvaluationContent evaluationContent) {
		this.evaluationContent = evaluationContent;
	}

	/**
	 */
	@JsonIgnore
	public EvaluationContent getEvaluationContent() {
		return evaluationContent;
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
	public EvaluationResult() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(EvaluationResult that) {
		setId(that.getId());
		setTermId(that.getTermId());
		setTeacherNo(that.getTeacherNo());
		setTeacherName(that.getTeacherName());
		setStudentNo(that.getStudentNo());
		setStudentName(that.getStudentName());
		setScore(that.getScore());
		setCreateTime(that.getCreateTime());
		setMemo(that.getMemo());
		setEvaluationContent(that.getEvaluationContent());
		setSchoolCourseInfo(that.getSchoolCourseInfo());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("termId=[").append(termId).append("] ");
		buffer.append("teacherNo=[").append(teacherNo).append("] ");
		buffer.append("teacherName=[").append(teacherName).append("] ");
		buffer.append("studentNo=[").append(studentNo).append("] ");
		buffer.append("studentName=[").append(studentName).append("] ");
		buffer.append("score=[").append(score).append("] ");
		buffer.append("createTime=[").append(createTime).append("] ");
		buffer.append("memo=[").append(memo).append("] ");

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
		if (!(obj instanceof EvaluationResult))
			return false;
		EvaluationResult equalCheck = (EvaluationResult) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
