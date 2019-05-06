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
		@NamedQuery(name = "findAllSpecialExaminations", query = "select mySpecialExamination from SpecialExamination mySpecialExamination"),
		@NamedQuery(name = "findSpecialExaminationByAcademyNumber", query = "select mySpecialExamination from SpecialExamination mySpecialExamination where mySpecialExamination.academyNumber = ?1"),
		@NamedQuery(name = "findSpecialExaminationByAcademyNumberContaining", query = "select mySpecialExamination from SpecialExamination mySpecialExamination where mySpecialExamination.academyNumber like ?1"),
		@NamedQuery(name = "findSpecialExaminationByCheckContent", query = "select mySpecialExamination from SpecialExamination mySpecialExamination where mySpecialExamination.checkContent = ?1"),
		@NamedQuery(name = "findSpecialExaminationByCheckContentContaining", query = "select mySpecialExamination from SpecialExamination mySpecialExamination where mySpecialExamination.checkContent like ?1"),
		@NamedQuery(name = "findSpecialExaminationByCheckItem", query = "select mySpecialExamination from SpecialExamination mySpecialExamination where mySpecialExamination.checkItem = ?1"),
		@NamedQuery(name = "findSpecialExaminationByCheckItemContaining", query = "select mySpecialExamination from SpecialExamination mySpecialExamination where mySpecialExamination.checkItem like ?1"),
		@NamedQuery(name = "findSpecialExaminationByCheckType", query = "select mySpecialExamination from SpecialExamination mySpecialExamination where mySpecialExamination.checkType = ?1"),
		@NamedQuery(name = "findSpecialExaminationByCheckTypeContaining", query = "select mySpecialExamination from SpecialExamination mySpecialExamination where mySpecialExamination.checkType like ?1"),
		@NamedQuery(name = "findSpecialExaminationByCreationDate", query = "select mySpecialExamination from SpecialExamination mySpecialExamination where mySpecialExamination.creationDate = ?1"),
		@NamedQuery(name = "findSpecialExaminationById", query = "select mySpecialExamination from SpecialExamination mySpecialExamination where mySpecialExamination.id = ?1"),
		@NamedQuery(name = "findSpecialExaminationByPrimaryKey", query = "select mySpecialExamination from SpecialExamination mySpecialExamination where mySpecialExamination.id = ?1") })
@Table(name = "special_examination")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "SpecialExamination")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class SpecialExamination implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ר�����
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
    Integer id;
	/**
	 * ���ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Calendar creationDate;
	/**
	 * ѧԺ
	 * 
	 */

	@Column(name = "academy_number", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String academyNumber;
	/**
	 * �������
	 * 
	 */

	@Column(name = "check_content")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    @Lob
    String checkContent;
	/**
	 * �������
	 * 
	 */

	@Column(name = "check_type", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String checkType;
	/**
	 * �����
	 * 
	 */

	@Column(name = "check_item", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String checkItem;


	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	// 参与检查人员字段
	@Column(name= "participant")
	@Basic
	@XmlElement

	String participant;
	/**
	 * �������
	 * 
	 */

	@Column(name = "rectification")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    @Lob
    String rectification;
	/**
	 * �������
	 * 
	 */

	@Column(name = "reviewExam")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String reviewExam;
	
	/**
	 * ���ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reviewTime")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Calendar reviewTime;

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
	@OneToMany(mappedBy = "specialExamination", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.CommonDocument> commonDocuments;
	
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_annex", referencedColumnName = "id") })
	@XmlTransient
	LabAnnex labAnnex;
	
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "reviewUser", referencedColumnName = "username") })
	@XmlTransient
	User reviewUser;

	/**
	 * ר�����
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ר�����
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ���ʱ��
	 * 
	 */
	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * ���ʱ��
	 * 
	 */
	public Calendar getCreationDate() {
		return this.creationDate;
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
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	/**
	 * �������
	 * 
	 */
	public String getCheckType() {
		return this.checkType;
	}

	/**
	 * �����
	 * 
	 */
	public void setCheckItem(String checkItem) {
		this.checkItem = checkItem;
	}

	/**
	 * �����
	 * 
	 */
	public String getCheckItem() {
		return this.checkItem;
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
	public SpecialExamination() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(SpecialExamination that) {
		setId(that.getId());
		setCreationDate(that.getCreationDate());
		setAcademyNumber(that.getAcademyNumber());
		setCheckContent(that.getCheckContent());
		setCheckType(that.getCheckType());
		setCheckItem(that.getCheckItem());
		setLabRoom(that.getLabRoom());
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
		buffer.append("creationDate=[").append(creationDate).append("] ");
		buffer.append("academyNumber=[").append(academyNumber).append("] ");
		buffer.append("checkContent=[").append(checkContent).append("] ");
		buffer.append("checkType=[").append(checkType).append("] ");
		buffer.append("checkItem=[").append(checkItem).append("] ");

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
		if (!(obj instanceof SpecialExamination))
			return false;
		SpecialExamination equalCheck = (SpecialExamination) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}

	public LabAnnex getLabAnnex() {
		return labAnnex;
	}

	public void setLabAnnex(LabAnnex labAnnex) {
		this.labAnnex = labAnnex;
	}

	public String getRectification() {
		return rectification;
	}

	public void setRectification(String rectification) {
		this.rectification = rectification;
	}

	public String getReviewExam() {
		return reviewExam;
	}

	public void setReviewExam(String reviewExam) {
		this.reviewExam = reviewExam;
	}

	public User getReviewUser() {
		return reviewUser;
	}

	public void setReviewUser(User reviewUser) {
		this.reviewUser = reviewUser;
	}

	public Calendar getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Calendar reviewTime) {
		this.reviewTime = reviewTime;
	}
}
