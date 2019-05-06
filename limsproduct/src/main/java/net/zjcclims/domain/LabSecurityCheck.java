package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllLabSecurityChecks", query = "select myLabSecurityCheck from LabSecurityCheck myLabSecurityCheck"),
		@NamedQuery(name = "findLabSecurityCheckByAcademyNumber", query = "select myLabSecurityCheck from LabSecurityCheck myLabSecurityCheck where myLabSecurityCheck.academyNumber = ?1"),
		@NamedQuery(name = "findLabSecurityCheckByAcademyNumberContaining", query = "select myLabSecurityCheck from LabSecurityCheck myLabSecurityCheck where myLabSecurityCheck.academyNumber like ?1"),
		@NamedQuery(name = "findLabSecurityCheckById", query = "select myLabSecurityCheck from LabSecurityCheck myLabSecurityCheck where myLabSecurityCheck.id = ?1"),
		@NamedQuery(name = "findLabSecurityCheckByMonth", query = "select myLabSecurityCheck from LabSecurityCheck myLabSecurityCheck where myLabSecurityCheck.month = ?1"),
		@NamedQuery(name = "findLabSecurityCheckByMonthContaining", query = "select myLabSecurityCheck from LabSecurityCheck myLabSecurityCheck where myLabSecurityCheck.month like ?1"),
		@NamedQuery(name = "findLabSecurityCheckByPrimaryKey", query = "select myLabSecurityCheck from LabSecurityCheck myLabSecurityCheck where myLabSecurityCheck.id = ?1"),
		@NamedQuery(name = "findLabSecurityCheckByTypeAuditing", query = "select myLabSecurityCheck from LabSecurityCheck myLabSecurityCheck where myLabSecurityCheck.typeAuditing = ?1"),
		@NamedQuery(name = "findLabSecurityCheckByTypeAuditingContaining", query = "select myLabSecurityCheck from LabSecurityCheck myLabSecurityCheck where myLabSecurityCheck.typeAuditing like ?1"),
		@NamedQuery(name = "findLabSecurityCheckByTypeTable", query = "select myLabSecurityCheck from LabSecurityCheck myLabSecurityCheck where myLabSecurityCheck.typeTable = ?1"),
		@NamedQuery(name = "findLabSecurityCheckByTypeTableContaining", query = "select myLabSecurityCheck from LabSecurityCheck myLabSecurityCheck where myLabSecurityCheck.typeTable like ?1") })
@Table(name = "lab_security_check")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabSecurityCheck")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class LabSecurityCheck implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * lab_security_check
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
    Integer id;
	/**
	 * �·�
	 * 
	 */

	@Column(name = "month", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String month;
	/**
	 * ����ѧԺ,ʵ���Ҷ�Ӧ��ѧԺ
	 * 
	 */

	@Column(name = "academy_number", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String academyNumber;
	/**
	 * һ�ࣨ1�������ࣨ2�������ࣨ3��
	 * 
	 */

	@Column(name = "type_table", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String typeTable;
	/**
	 * ���ͨ��1������˲�ͨ��2��������ˣ�3�������ύ��4��
	 * 
	 */

	@Column(name = "type_auditing", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String typeAuditing;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_room", referencedColumnName = "id") })
	@XmlTransient
	LabRoom labRoom;
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
	@OneToMany(mappedBy = "labSecurityCheck", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabSecurityCheckDetails> labSecurityCheckDetailses;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_center_id", referencedColumnName = "id") })
	@XmlTransient
	LabCenter labCenter;

	/**
	 * lab_security_check
	 *
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * lab_security_check
	 *
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * �·�
	 *
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * �·�
	 *
	 */
	public String getMonth() {
		return this.month;
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
	 * һ�ࣨ1�������ࣨ2�������ࣨ3��
	 *
	 */
	public void setTypeTable(String typeTable) {
		this.typeTable = typeTable;
	}

	/**
	 * һ�ࣨ1�������ࣨ2�������ࣨ3��
	 *
	 */
	public String getTypeTable() {
		return this.typeTable;
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
	public void setLabSecurityCheckDetailses(Set<LabSecurityCheckDetails> labSecurityCheckDetailses) {
		this.labSecurityCheckDetailses = labSecurityCheckDetailses;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabSecurityCheckDetails> getLabSecurityCheckDetailses() {
		if (labSecurityCheckDetailses == null) {
			labSecurityCheckDetailses = new java.util.LinkedHashSet<net.zjcclims.domain.LabSecurityCheckDetails>();
		}
		return labSecurityCheckDetailses;
	}

	public LabCenter getLabCenter() {
		return labCenter;
	}

	public void setLabCenter(LabCenter labCenter) {
		this.labCenter = labCenter;
	}


	/**
	 */
	public LabSecurityCheck() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabSecurityCheck that) {
		setId(that.getId());
		setMonth(that.getMonth());
		setAcademyNumber(that.getAcademyNumber());
		setTypeTable(that.getTypeTable());
		setTypeAuditing(that.getTypeAuditing());
		setLabRoom(that.getLabRoom());
		setUser(that.getUser());
		setSchoolTerm(that.getSchoolTerm());
		setLabSecurityCheckDetailses(new java.util.LinkedHashSet<net.zjcclims.domain.LabSecurityCheckDetails>(that.getLabSecurityCheckDetailses()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("month=[").append(month).append("] ");
		buffer.append("academyNumber=[").append(academyNumber).append("] ");
		buffer.append("typeTable=[").append(typeTable).append("] ");
		buffer.append("typeAuditing=[").append(typeAuditing).append("] ");

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
		if (!(obj instanceof LabSecurityCheck))
			return false;
		LabSecurityCheck equalCheck = (LabSecurityCheck) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
