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
		@NamedQuery(name = "findAllRoutineInspections", query = "select myRoutineInspection from RoutineInspection myRoutineInspection"),
		@NamedQuery(name = "findRoutineInspectionByAcademyNumber", query = "select myRoutineInspection from RoutineInspection myRoutineInspection where myRoutineInspection.academyNumber = ?1"),
		@NamedQuery(name = "findRoutineInspectionByAcademyNumberContaining", query = "select myRoutineInspection from RoutineInspection myRoutineInspection where myRoutineInspection.academyNumber like ?1"),
		@NamedQuery(name = "findRoutineInspectionByCheckContent", query = "select myRoutineInspection from RoutineInspection myRoutineInspection where myRoutineInspection.checkContent = ?1"),
		@NamedQuery(name = "findRoutineInspectionByCheckContentContaining", query = "select myRoutineInspection from RoutineInspection myRoutineInspection where myRoutineInspection.checkContent like ?1"),
		@NamedQuery(name = "findRoutineInspectionByCreationDate", query = "select myRoutineInspection from RoutineInspection myRoutineInspection where myRoutineInspection.creationDate = ?1"),
		@NamedQuery(name = "findRoutineInspectionById", query = "select myRoutineInspection from RoutineInspection myRoutineInspection where myRoutineInspection.id = ?1"),
		@NamedQuery(name = "findRoutineInspectionByPrimaryKey", query = "select myRoutineInspection from RoutineInspection myRoutineInspection where myRoutineInspection.id = ?1"),
		@NamedQuery(name = "findRoutineInspectionByTypeAuditing", query = "select myRoutineInspection from RoutineInspection myRoutineInspection where myRoutineInspection.typeAuditing = ?1"),
		@NamedQuery(name = "findRoutineInspectionByTypeAuditingContaining", query = "select myRoutineInspection from RoutineInspection myRoutineInspection where myRoutineInspection.typeAuditing like ?1"),
		@NamedQuery(name = "findRoutineInspectionByTypeTable", query = "select myRoutineInspection from RoutineInspection myRoutineInspection where myRoutineInspection.typeTable = ?1"),
		@NamedQuery(name = "findRoutineInspectionByTypeTableContaining", query = "select myRoutineInspection from RoutineInspection myRoutineInspection where myRoutineInspection.typeTable like ?1"),
		@NamedQuery(name = "findRoutineInspectionByWeek", query = "select myRoutineInspection from RoutineInspection myRoutineInspection where myRoutineInspection.week = ?1"),
		@NamedQuery(name = "findRoutineInspectionByWeekContaining", query = "select myRoutineInspection from RoutineInspection myRoutineInspection where myRoutineInspection.week like ?1") })
@Table(name = "routine_inspection")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "RoutineInspection")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class RoutineInspection implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * �������
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
    Integer id;
	/**
	 * ����ѧԺ,ʵ���Ҷ�Ӧ��ѧԺ
	 * 
	 */

	@Column(name = "academy_number", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String academyNumber;
	/**
	 * ���ͨ��1������˲�ͨ��2��������ˣ�3�������ύ��4��
	 * 
	 */

	@Column(name = "type_auditing", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String typeAuditing;
	/**
	 * �������
	 * 
	 */

	@Column(name = "check_content")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String checkContent;

	/**
	 * 安全管理情况
	 *
	 */

	@Column(name = "safety_management")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String safetyManagement;
	/**
	 * ������?1�����������ϱ��?2��
	 * 
	 */

	@Column(name = "type_table", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String typeTable;
	/**
	 * �ܴ�
	 * 
	 */

	@Column(name = "week", length = 11)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer week;
	/**
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Calendar creationDate;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_room", referencedColumnName = "id") })
	@XmlTransient
	LabRoom labRoom;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_center_id", referencedColumnName = "id") })
	@XmlTransient
	LabCenter labCenter;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "user", referencedColumnName = "username") })
	@XmlTransient
	User user;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "term", referencedColumnName = "id") })
	@XmlTransient
	SchoolTerm schoolTerm;
	/**
	 */
	@OneToMany(mappedBy = "routineInspection", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.CommonDocument> commonDocuments;

	/**
	 * �������
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * �������
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ����ѧԺ,ʵ���Ҷ�Ӧ��ѧԺ
	 * 
	 */
	public void setAcademyNumber(String academyNumber) {
		this.academyNumber = academyNumber;
	}

	/**
	 * ����ѧԺ,ʵ���Ҷ�Ӧ��ѧԺ
	 * 
	 */
	public String getAcademyNumber() {
		return this.academyNumber;
	}

	/**
	 * ���ͨ��1������˲�ͨ��2��������ˣ�3�������ύ��4��
	 * 
	 */
	public void setTypeAuditing(String typeAuditing) {
		this.typeAuditing = typeAuditing;
	}

	/**
	 * ���ͨ��1������˲�ͨ��2��������ˣ�3�������ύ��4��
	 * 
	 */
	public String getTypeAuditing() {
		return this.typeAuditing;
	}

	/**
	 * �������
	 * 
	 */
	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}

	/**
	 * �������
	 * 
	 */
	public String getCheckContent() {
		return this.checkContent;
	}

	/**
	 * �������
	 *
	 */
	public void setSafetyManagement(String safetyManagement) {
		this.safetyManagement = safetyManagement;
	}

	/**
	 * �������
	 *
	 */
	public String getSafetyManagement() {
		return this.safetyManagement;
	}

	/**
	 * ������?1�����������ϱ��?2��
	 * 
	 */
	public void setTypeTable(String typeTable) {
		this.typeTable = typeTable;
	}

	/**
	 * ������?1�����������ϱ��?2��
	 * 
	 */
	public String getTypeTable() {
		return this.typeTable;
	}

	/**
	 * �ܴ�
	 * 
	 */
	public void setWeek(Integer week) {
		this.week = week;
	}

	/**
	 * �ܴ�
	 * 
	 */
	public Integer getWeek() {
		return this.week;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public Calendar getCreationDate() {
		return this.creationDate;
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
	 */
	public void setLabCenter(LabCenter labCenter) {
		this.labCenter = labCenter;
	}

	/**
	 */
	@JsonIgnore
	public LabCenter getLabCenter() {
		return labCenter;
	}

	/**
	 */
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
	public void setSchoolTerm(SchoolTerm schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	/**
	 */
	@JsonIgnore
	public SchoolTerm getSchoolTerm() {
		return schoolTerm;
	}

	/**
	 */
	public void setCommonDocuments(Set<CommonDocument> commonDocuments) {
		this.commonDocuments = commonDocuments;
	}

	/**
	 */
	@JsonIgnore
	public Set<CommonDocument> getCommonDocuments() {
		if (commonDocuments == null) {
			commonDocuments = new java.util.LinkedHashSet<net.zjcclims.domain.CommonDocument>();
		}
		return commonDocuments;
	}

	/**
	 */
	public RoutineInspection() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(RoutineInspection that) {
		setId(that.getId());
		setAcademyNumber(that.getAcademyNumber());
		setTypeAuditing(that.getTypeAuditing());
		setCheckContent(that.getCheckContent());
		setSafetyManagement(that.getSafetyManagement());
		setTypeTable(that.getTypeTable());
		setWeek(that.getWeek());
		setCreationDate(that.getCreationDate());
		setLabRoom(that.getLabRoom());
		setLabCenter(that.getLabCenter());
		setUser(that.getUser());
		setSchoolTerm(that.getSchoolTerm());
		setCommonDocuments(new java.util.LinkedHashSet<net.zjcclims.domain.CommonDocument>(that.getCommonDocuments()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("academyNumber=[").append(academyNumber).append("] ");
		buffer.append("typeAuditing=[").append(typeAuditing).append("] ");
		buffer.append("checkContent=[").append(checkContent).append("] ");
		buffer.append("safetyManagement=[").append(safetyManagement).append("] ");
		buffer.append("typeTable=[").append(typeTable).append("] ");
		buffer.append("week=[").append(week).append("] ");
		buffer.append("creationDate=[").append(creationDate).append("] ");

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
		if (!(obj instanceof RoutineInspection))
			return false;
		RoutineInspection equalCheck = (RoutineInspection) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
