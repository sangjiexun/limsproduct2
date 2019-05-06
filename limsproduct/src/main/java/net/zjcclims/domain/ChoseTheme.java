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
		@NamedQuery(name = "findAllChoseThemes", query = "select myChoseTheme from ChoseTheme myChoseTheme"),
		@NamedQuery(name = "findChoseThemeByAdvanceDay", query = "select myChoseTheme from ChoseTheme myChoseTheme where myChoseTheme.advanceDay = ?1"),
		@NamedQuery(name = "findChoseThemeByBatchNumber", query = "select myChoseTheme from ChoseTheme myChoseTheme where myChoseTheme.batchNumber = ?1"),
		@NamedQuery(name = "findChoseThemeByEndTime", query = "select myChoseTheme from ChoseTheme myChoseTheme where myChoseTheme.endTime = ?1"),
		@NamedQuery(name = "findChoseThemeByEndTimeAfter", query = "select myChoseTheme from ChoseTheme myChoseTheme where myChoseTheme.endTime > ?1"),
		@NamedQuery(name = "findChoseThemeByEndTimeBefore", query = "select myChoseTheme from ChoseTheme myChoseTheme where myChoseTheme.endTime < ?1"),
		@NamedQuery(name = "findChoseThemeById", query = "select myChoseTheme from ChoseTheme myChoseTheme where myChoseTheme.id = ?1"),
		@NamedQuery(name = "findChoseThemeByMaxStudent", query = "select myChoseTheme from ChoseTheme myChoseTheme where myChoseTheme.maxStudent = ?1"),
		@NamedQuery(name = "findChoseThemeByPrimaryKey", query = "select myChoseTheme from ChoseTheme myChoseTheme where myChoseTheme.id = ?1"),
		@NamedQuery(name = "findChoseThemeByStartTime", query = "select myChoseTheme from ChoseTheme myChoseTheme where myChoseTheme.startTime = ?1"),
		@NamedQuery(name = "findChoseThemeByStartTimeAfter", query = "select myChoseTheme from ChoseTheme myChoseTheme where myChoseTheme.startTime > ?1"),
		@NamedQuery(name = "findChoseThemeByStartTimeBefore", query = "select myChoseTheme from ChoseTheme myChoseTheme where myChoseTheme.startTime < ?1"),
		@NamedQuery(name = "findChoseThemeByState", query = "select myChoseTheme from ChoseTheme myChoseTheme where myChoseTheme.state = ?1"),
		@NamedQuery(name = "findChoseThemeByStudentNumber", query = "select myChoseTheme from ChoseTheme myChoseTheme where myChoseTheme.studentNumber = ?1"),
		@NamedQuery(name = "findChoseThemeByTeacherNumber", query = "select myChoseTheme from ChoseTheme myChoseTheme where myChoseTheme.teacherNumber = ?1"),
		@NamedQuery(name = "findChoseThemeByTheYear", query = "select myChoseTheme from ChoseTheme myChoseTheme where myChoseTheme.theYear = ?1") })
@Table(name = "chose_theme")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ChoseTheme")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class ChoseTheme implements Serializable {
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
	 * ����ѧ������
	 * 
	 */

	@Column(name = "student_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer studentNumber;
	/**
	 * ��ѡ��ʼʱ��
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "start_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Calendar startTime;
	/**
	 * ��ѡ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "end_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Calendar endTime;
	/**
	 * ״̬��0δ����1�ѷ���
	 * 
	 */

	@Column(name = "state")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer state;
	
	@Column(name = "type")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer type;
	/**
	 * ��Ŀ
	 * 
	 */

	@Column(name = "tittle")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String tittle;
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	/**
	 * ��
	 * 
	 */

	@Column(name = "the_year")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer theYear;
	/**
	 * �������
	 * 
	 */

	@Column(name = "batch_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer batchNumber;
	/**
	 * ��ǰ��������
	 * 
	 */

	@Column(name = "advance_day")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer advanceDay;
	/**
	 * ��ʦ����
	 * 
	 */

	@Column(name = "teacher_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer teacherNumber;
	/**
	 * ��ʦ������ѧ������
	 * 
	 */

	@Column(name = "max_student")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer maxStudent;

	/**
	 */
	@Column(name = "expect_deadline")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Calendar expectDeadline;
	
	/**
	 */
	@Column(name = "expect_startline")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Calendar expectStartline;
	
	
	public Calendar getExpectStartline() {
		return expectStartline;
	}

	public void setExpectStartline(Calendar expectStartline) {
		this.expectStartline = expectStartline;
	}

	public Calendar getExpectDeadline() {
		return expectDeadline;
	}

	public void setExpectDeadline(Calendar expectDeadline) {
		this.expectDeadline = expectDeadline;
	}

	/**
	 */
	
	@OneToMany(mappedBy = "choseTheme", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.ChoseProfessor> choseProfessors;
	/**
	 */
	@OneToMany(mappedBy = "choseTheme", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.ChoseAttention> choseAttentions;
	/**
	 */
	@OneToMany(mappedBy = "choseTheme", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.ChoseProfessorBatch> choseProfessorBatchs;

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
	 * ����ѧ������
	 *
	 */
	public void setStudentNumber(Integer studentNumber) {
		this.studentNumber = studentNumber;
	}

	/**
	 * ����ѧ������
	 *
	 */
	public Integer getStudentNumber() {
		return this.studentNumber;
	}

	/**
	 * ��ѡ��ʼʱ��
	 *
	 */
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	/**
	 * ��ѡ��ʼʱ��
	 *
	 */
	public Calendar getStartTime() {
		return this.startTime;
	}

	/**
	 * ��ѡ����ʱ��
	 *
	 */
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	/**
	 * ��ѡ����ʱ��
	 *
	 */
	public Calendar getEndTime() {
		return this.endTime;
	}

	/**
	 * ״̬��0δ����1�ѷ���
	 *
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * ״̬��0δ����1�ѷ���
	 *
	 */
	public Integer getState() {
		return this.state;
	}

	/**
	 * ��
	 *
	 */
	public void setTheYear(Integer theYear) {
		this.theYear = theYear;
	}

	/**
	 * ��
	 *
	 */
	public Integer getTheYear() {
		return this.theYear;
	}

	/**
	 * �������
	 *
	 */
	public void setBatchNumber(Integer batchNumber) {
		this.batchNumber = batchNumber;
	}

	/**
	 * �������
	 *
	 */
	public Integer getBatchNumber() {
		return this.batchNumber;
	}

	/**
	 * ��ǰ��������
	 *
	 */
	public void setAdvanceDay(Integer advanceDay) {
		this.advanceDay = advanceDay;
	}

	/**
	 * ��ǰ��������
	 *
	 */
	public Integer getAdvanceDay() {
		return this.advanceDay;
	}

	/**
	 * ��ʦ����
	 *
	 */
	public void setTeacherNumber(Integer teacherNumber) {
		this.teacherNumber = teacherNumber;
	}

	/**
	 * ��ʦ����
	 *
	 */
	public Integer getTeacherNumber() {
		return this.teacherNumber;
	}

	/**
	 * ��ʦ������ѧ������
	 *
	 */
	public void setMaxStudent(Integer maxStudent) {
		this.maxStudent = maxStudent;
	}

	/**
	 * ��ʦ������ѧ������
	 *
	 */
	public Integer getMaxStudent() {
		return this.maxStudent;
	}

	/**
	 */
	public void setChoseProfessors(Set<ChoseProfessor> choseProfessors) {
		this.choseProfessors = choseProfessors;
	}

	/**
	 */
	@JsonIgnore
	public Set<ChoseProfessor> getChoseProfessors() {
		if (choseProfessors == null) {
			choseProfessors = new java.util.LinkedHashSet<net.zjcclims.domain.ChoseProfessor>();
		}
		return choseProfessors;
	}

	/**
	 */
	public void setChoseAttentions(Set<ChoseAttention> choseAttentions) {
		this.choseAttentions = choseAttentions;
	}

	/**
	 */
	@JsonIgnore
	public Set<ChoseAttention> getChoseAttentions() {
		if (choseAttentions == null) {
			choseAttentions = new java.util.LinkedHashSet<net.zjcclims.domain.ChoseAttention>();
		}
		return choseAttentions;
	}

	/**
	 */
	public void setChoseProfessorBatchs(Set<ChoseProfessorBatch> choseProfessorBatchs) {
		this.choseProfessorBatchs = choseProfessorBatchs;
	}

	/**
	 */
	@JsonIgnore
	public Set<ChoseProfessorBatch> getChoseProfessorBatchs() {
		if (choseProfessorBatchs == null) {
			choseProfessorBatchs = new java.util.LinkedHashSet<net.zjcclims.domain.ChoseProfessorBatch>();
		}
		return choseProfessorBatchs;
	}

	/**
	 */
	public ChoseTheme() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ChoseTheme that) {
		setId(that.getId());
		setStudentNumber(that.getStudentNumber());
		setStartTime(that.getStartTime());
		setEndTime(that.getEndTime());
		setState(that.getState());
		setTheYear(that.getTheYear());
		setBatchNumber(that.getBatchNumber());
		setAdvanceDay(that.getAdvanceDay());
		setTeacherNumber(that.getTeacherNumber());
		setMaxStudent(that.getMaxStudent());
		setChoseProfessors(new java.util.LinkedHashSet<net.zjcclims.domain.ChoseProfessor>(that.getChoseProfessors()));
		setChoseAttentions(new java.util.LinkedHashSet<net.zjcclims.domain.ChoseAttention>(that.getChoseAttentions()));
		setChoseProfessorBatchs(new java.util.LinkedHashSet<net.zjcclims.domain.ChoseProfessorBatch>(that.getChoseProfessorBatchs()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("studentNumber=[").append(studentNumber).append("] ");
		buffer.append("startTime=[").append(startTime).append("] ");
		buffer.append("endTime=[").append(endTime).append("] ");
		buffer.append("state=[").append(state).append("] ");
		buffer.append("theYear=[").append(theYear).append("] ");
		buffer.append("batchNumber=[").append(batchNumber).append("] ");
		buffer.append("advanceDay=[").append(advanceDay).append("] ");
		buffer.append("teacherNumber=[").append(teacherNumber).append("] ");
		buffer.append("maxStudent=[").append(maxStudent).append("] ");

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
		if (!(obj instanceof ChoseTheme))
			return false;
		ChoseTheme equalCheck = (ChoseTheme) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
