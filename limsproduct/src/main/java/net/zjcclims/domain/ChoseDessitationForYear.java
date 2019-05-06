package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Calendar;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllChoseDessitationForYears", query = "select myChoseDessitationForYear from ChoseDessitationForYear myChoseDessitationForYear"),
		@NamedQuery(name = "findChoseDessitationForYearByDocumentId", query = "select myChoseDessitationForYear from ChoseDessitationForYear myChoseDessitationForYear where myChoseDessitationForYear.documentId = ?1"),
		@NamedQuery(name = "findChoseDessitationForYearByFinishTime", query = "select myChoseDessitationForYear from ChoseDessitationForYear myChoseDessitationForYear where myChoseDessitationForYear.finishTime = ?1"),
		@NamedQuery(name = "findChoseDessitationForYearByFinishTimeAfter", query = "select myChoseDessitationForYear from ChoseDessitationForYear myChoseDessitationForYear where myChoseDessitationForYear.finishTime > ?1"),
		@NamedQuery(name = "findChoseDessitationForYearByFinishTimeBefore", query = "select myChoseDessitationForYear from ChoseDessitationForYear myChoseDessitationForYear where myChoseDessitationForYear.finishTime < ?1"),
		@NamedQuery(name = "findChoseDessitationForYearById", query = "select myChoseDessitationForYear from ChoseDessitationForYear myChoseDessitationForYear where myChoseDessitationForYear.id = ?1"),
		@NamedQuery(name = "findChoseDessitationForYearByPrimaryKey", query = "select myChoseDessitationForYear from ChoseDessitationForYear myChoseDessitationForYear where myChoseDessitationForYear.id = ?1"),
		@NamedQuery(name = "findChoseDessitationForYearByRequirements", query = "select myChoseDessitationForYear from ChoseDessitationForYear myChoseDessitationForYear where myChoseDessitationForYear.requirements = ?1"),
		@NamedQuery(name = "findChoseDessitationForYearByRequirementsContaining", query = "select myChoseDessitationForYear from ChoseDessitationForYear myChoseDessitationForYear where myChoseDessitationForYear.requirements like ?1"),
		@NamedQuery(name = "findChoseDessitationForYearByState", query = "select myChoseDessitationForYear from ChoseDessitationForYear myChoseDessitationForYear where myChoseDessitationForYear.state = ?1"),
		@NamedQuery(name = "findChoseDessitationForYearByStudent", query = "select myChoseDessitationForYear from ChoseDessitationForYear myChoseDessitationForYear where myChoseDessitationForYear.student = ?1"),
		@NamedQuery(name = "findChoseDessitationForYearByStudentCname", query = "select myChoseDessitationForYear from ChoseDessitationForYear myChoseDessitationForYear where myChoseDessitationForYear.studentCname = ?1"),
		@NamedQuery(name = "findChoseDessitationForYearByStudentCnameContaining", query = "select myChoseDessitationForYear from ChoseDessitationForYear myChoseDessitationForYear where myChoseDessitationForYear.studentCname like ?1"),
		@NamedQuery(name = "findChoseDessitationForYearByStudentContaining", query = "select myChoseDessitationForYear from ChoseDessitationForYear myChoseDessitationForYear where myChoseDessitationForYear.student like ?1"),
		@NamedQuery(name = "findChoseDessitationForYearByTeacher", query = "select myChoseDessitationForYear from ChoseDessitationForYear myChoseDessitationForYear where myChoseDessitationForYear.teacher = ?1"),
		@NamedQuery(name = "findChoseDessitationForYearByTeacherContaining", query = "select myChoseDessitationForYear from ChoseDessitationForYear myChoseDessitationForYear where myChoseDessitationForYear.teacher like ?1"),
		@NamedQuery(name = "findChoseDessitationForYearByTheYear", query = "select myChoseDessitationForYear from ChoseDessitationForYear myChoseDessitationForYear where myChoseDessitationForYear.theYear = ?1"),
		@NamedQuery(name = "findChoseDessitationForYearByTheme", query = "select myChoseDessitationForYear from ChoseDessitationForYear myChoseDessitationForYear where myChoseDessitationForYear.theme = ?1"),
		@NamedQuery(name = "findChoseDessitationForYearByThemeContaining", query = "select myChoseDessitationForYear from ChoseDessitationForYear myChoseDessitationForYear where myChoseDessitationForYear.theme like ?1") })
@Table(name = "chose_dissertation_for_year")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ChoseDessitationForYear")
public class ChoseDessitationForYear implements Serializable {
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
	 * ��
	 * 
	 */

	@Column(name = "the_year")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer theYear;
	/**
	 * ��ʦusername
	 * 
	 */

	@Column(name = "teacher")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String teacher;
	/**
	 * ����
	 * 
	 */

	@Column(name = "theme")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String theme;
	/**
	 * Ҫ��
	 * 
	 */

	@Column(name = "requirements")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String requirements;
	/**
	 * ���ʱ��
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "finish_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Calendar finishTime;
	/**
	 * ѧ��username
	 * 
	 */

	@Column(name = "student")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String student;
	/**
	 * ѧ��cname
	 * 
	 */

	@Column(name = "student_cname")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String studentCname;
	/**
	 * common_doucument��id
	 * 
	 */

	@Column(name = "document_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer documentId;
	/**
	 * 0δ����1�ѷ���
	 * 
	 */

	@Column(name = "state")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer state;

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
	 * ��ʦusername
	 * 
	 */
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	/**
	 * ��ʦusername
	 * 
	 */
	public String getTeacher() {
		return this.teacher;
	}

	/**
	 * ����
	 * 
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * ����
	 * 
	 */
	public String getTheme() {
		return this.theme;
	}

	/**
	 * Ҫ��
	 * 
	 */
	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}

	/**
	 * Ҫ��
	 * 
	 */
	public String getRequirements() {
		return this.requirements;
	}

	/**
	 * ���ʱ��
	 * 
	 */
	public void setFinishTime(Calendar finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * ���ʱ��
	 * 
	 */
	public Calendar getFinishTime() {
		return this.finishTime;
	}

	/**
	 * ѧ��username
	 * 
	 */
	public void setStudent(String student) {
		this.student = student;
	}

	/**
	 * ѧ��username
	 * 
	 */
	public String getStudent() {
		return this.student;
	}

	/**
	 * ѧ��cname
	 * 
	 */
	public void setStudentCname(String studentCname) {
		this.studentCname = studentCname;
	}

	/**
	 * ѧ��cname
	 * 
	 */
	public String getStudentCname() {
		return this.studentCname;
	}

	/**
	 * common_doucument��id
	 * 
	 */
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	/**
	 * common_doucument��id
	 * 
	 */
	public Integer getDocumentId() {
		return this.documentId;
	}

	/**
	 * 0δ����1�ѷ���
	 * 
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * 0δ����1�ѷ���
	 * 
	 */
	public Integer getState() {
		return this.state;
	}

	/**
	 */
	public ChoseDessitationForYear() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ChoseDessitationForYear that) {
		setId(that.getId());
		setTheYear(that.getTheYear());
		setTeacher(that.getTeacher());
		setTheme(that.getTheme());
		setRequirements(that.getRequirements());
		setFinishTime(that.getFinishTime());
		setStudent(that.getStudent());
		setStudentCname(that.getStudentCname());
		setDocumentId(that.getDocumentId());
		setState(that.getState());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("theYear=[").append(theYear).append("] ");
		buffer.append("teacher=[").append(teacher).append("] ");
		buffer.append("theme=[").append(theme).append("] ");
		buffer.append("requirements=[").append(requirements).append("] ");
		buffer.append("finishTime=[").append(finishTime).append("] ");
		buffer.append("student=[").append(student).append("] ");
		buffer.append("studentCname=[").append(studentCname).append("] ");
		buffer.append("documentId=[").append(documentId).append("] ");
		buffer.append("state=[").append(state).append("] ");

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
		if (!(obj instanceof ChoseDessitationForYear))
			return false;
		ChoseDessitationForYear equalCheck = (ChoseDessitationForYear) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
