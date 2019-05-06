package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllSoftwareReserves", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve"),
		@NamedQuery(name = "findSoftwareReserveByApplyReason", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.applyReason = ?1"),
		@NamedQuery(name = "findSoftwareReserveByApplyReasonContaining", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.applyReason like ?1"),
		@NamedQuery(name = "findSoftwareReserveByApproceAdvice", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.approceAdvice = ?1"),
		@NamedQuery(name = "findSoftwareReserveByApproceAdviceContaining", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.approceAdvice like ?1"),
		@NamedQuery(name = "findSoftwareReserveByApproveState", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.approveState = ?1"),
		@NamedQuery(name = "findSoftwareReserveByApproveTime", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.approveTime = ?1"),
		@NamedQuery(name = "findSoftwareReserveByApproveUser", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.approveUser = ?1"),
		@NamedQuery(name = "findSoftwareReserveByApproveUserContaining", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.approveUser like ?1"),
		@NamedQuery(name = "findSoftwareReserveByCd", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.cd = ?1"),
		@NamedQuery(name = "findSoftwareReserveByCode", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.code = ?1"),
		@NamedQuery(name = "findSoftwareReserveByCodeContaining", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.code like ?1"),
		@NamedQuery(name = "findSoftwareReserveByCourse", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.course = ?1"),
		@NamedQuery(name = "findSoftwareReserveByCourseContaining", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.course like ?1"),
		@NamedQuery(name = "findSoftwareReserveByCreateTime", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.createTime = ?1"),
		@NamedQuery(name = "findSoftwareReserveByDongle", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.dongle = ?1"),
		@NamedQuery(name = "findSoftwareReserveByFrame", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.frame = ?1"),
		@NamedQuery(name = "findSoftwareReserveByFrameContaining", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.frame like ?1"),
		@NamedQuery(name = "findSoftwareReserveById", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.id = ?1"),
		@NamedQuery(name = "findSoftwareReserveByName", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.name = ?1"),
		@NamedQuery(name = "findSoftwareReserveByNameContaining", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.name like ?1"),
		@NamedQuery(name = "findSoftwareReserveByPhone", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.phone = ?1"),
		@NamedQuery(name = "findSoftwareReserveByPhoneContaining", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.phone like ?1"),
		@NamedQuery(name = "findSoftwareReserveByPrimaryKey", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.id = ?1"),
		@NamedQuery(name = "findSoftwareReserveByProvideSoftware", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.provideSoftware = ?1"),
		@NamedQuery(name = "findSoftwareReserveByPurpose", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.purpose = ?1"),
		@NamedQuery(name = "findSoftwareReserveByPurposeContaining", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.purpose like ?1"),
		@NamedQuery(name = "findSoftwareReserveByRemark", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.remark = ?1"),
		@NamedQuery(name = "findSoftwareReserveByRemarkContaining", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.remark like ?1"),
		@NamedQuery(name = "findSoftwareReserveByRequireTime", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.requireTime = ?1"),
		@NamedQuery(name = "findSoftwareReserveByRequirement", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.requirement = ?1"),
		@NamedQuery(name = "findSoftwareReserveByRequirementContaining", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.requirement like ?1"),
		@NamedQuery(name = "findSoftwareReserveBySupply", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.supply = ?1"),
		@NamedQuery(name = "findSoftwareReserveBySupplyContaining", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.supply like ?1"),
		@NamedQuery(name = "findSoftwareReserveBySupplyPhone", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.supplyPhone = ?1"),
		@NamedQuery(name = "findSoftwareReserveBySupplyPhoneContaining", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.supplyPhone like ?1"),
		@NamedQuery(name = "findSoftwareReserveByTeacher", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.teacher = ?1"),
		@NamedQuery(name = "findSoftwareReserveByTeacherContaining", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.teacher like ?1"),
		@NamedQuery(name = "findSoftwareReserveByTerm", query = "select mySoftwareReserve from SoftwareReserve mySoftwareReserve where mySoftwareReserve.term = ?1")})
@Table(name = "software_reserve")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "SoftwareReserve")
public class SoftwareReserve implements Serializable {
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
	 * �γ����
	 * 
	 */

	@Column(name = "course", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String course;
	//点数
	@Column(name = "point")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer point;
	
	/**
	 * ������
	 * 
	 */

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	/**
	 * ��װҪ��
	 * 
	 */

	@Column(name = "requirement")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String requirement;
	/**
	 * ʵ����
	 * 
	 */

	/**
	 * ѧ��id
	 * 
	 */

	@Column(name = "term")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer term;
	/**
	 * ʹ�ý�ʦ(������)
	 * 
	 */

	@Column(name = "teacher", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String teacher;
	/**
	 * ��ϵ�绰
	 * 
	 */

	@Column(name = "phone", length = 15)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String phone;
	/**
	 * ��Ҫ��;
	 * 
	 */

	@Column(name = "purpose", length = 100)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String purpose;
	/**
	 * ������
	 * 
	 */

	@Column(name = "code", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String code;
	/**
	 * ������
	 * 
	 */

	@Column(name = "name", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String name;
	/**
	 * ��ע
	 * 
	 */

	@Column(name = "remark")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String remark;
	/**
	 * ������
	 * 
	 */

	@Column(name = "approve_user", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String approveUser;
	/**
	 * �������
	 * 
	 */

	@Column(name = "approce_advice")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String approceAdvice;
	/**
	 * ����״̬��3����ͨ��2����δͨ��1δ���0δ�ύ
	 * 
	 */

	@Column(name = "approve_state")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer approveState;
	/**
	 * ���ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approve_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar approveTime;
	/**
	 * �ܷ��ṩ���
	 * 
	 */

	@Column(name = "provide_software")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Boolean provideSoftware;
	/**
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createTime;
	/**
	 * ���ܹ�����
	 * 
	 */

	@Column(name = "dongle")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Boolean dongle;
	/**
	 */

	@Column(name = "cd")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Boolean cd;
	/**
	 * ��װʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "require_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar requireTime;
	/**
	 * ����ܹ�
	 * 
	 */

	@Column(name = "frame")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String frame;
	/**
	 * ��������
	 * 
	 */

	@Column(name = "apply_reason")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String applyReason;
	/**
	 * ��Ӧ��
	 * 
	 */

	@Column(name = "supply")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String supply;
	/**
	 * ��Ӧ����ϵ�绰
	 * 
	 */

	@Column(name = "supply_phone", length = 15)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String supplyPhone;

	@Column(name = "state")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer state;
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	@Column(name = "button_mark")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer buttonMark;
	
	public Integer getButtonMark() {
		return buttonMark;
	}

	public void setButtonMark(Integer buttonMark) {
		this.buttonMark = buttonMark;
	}
	
	@OneToMany(mappedBy = "softwareReserve", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.CommonDocument> commonDocuments;
	
	
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "user_id", referencedColumnName = "username")})
	@XmlTransient
	User user;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_id", referencedColumnName = "id")})
	@XmlTransient
	LabRoom labRoom;
	
	/**
	 */
	@OneToMany(mappedBy = "softwareReserve", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SoftwareReserveAudit> softwareReserveAudits;
	@JsonIgnore
	public java.util.Set<net.zjcclims.domain.SoftwareReserveAudit> getSoftwareReserveAudits() {
		return softwareReserveAudits;
	}

	public void setSoftwareReserveAudits(
			java.util.Set<net.zjcclims.domain.SoftwareReserveAudit> softwareReserveAudits) {
		this.softwareReserveAudits = softwareReserveAudits;
	}
	
	public LabRoom getLabRoom() {
		return labRoom;
	}

	public void setLabRoom(LabRoom labRoom) {
		this.labRoom = labRoom;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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
	 * �γ����
	 * 
	 */
	public void setCourse(String course) {
		this.course = course;
	}

	/**
	 * �γ����
	 * 
	 */
	public String getCourse() {
		return this.course;
	}


	/**
	 * ��װҪ��
	 * 
	 */
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	/**
	 * ��װҪ��
	 * 
	 */
	public String getRequirement() {
		return this.requirement;
	}


	/**
	 * ѧ��id
	 * 
	 */
	public void setTerm(Integer term) {
		this.term = term;
	}

	/**
	 * ѧ��id
	 * 
	 */
	public Integer getTerm() {
		return this.term;
	}

	/**
	 * ʹ�ý�ʦ(������)
	 * 
	 */
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	/**
	 * ʹ�ý�ʦ(������)
	 * 
	 */
	public String getTeacher() {
		return this.teacher;
	}

	/**
	 * ��ϵ�绰
	 * 
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * ��ϵ�绰
	 * 
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * ��Ҫ��;
	 * 
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	/**
	 * ��Ҫ��;
	 * 
	 */
	public String getPurpose() {
		return this.purpose;
	}

	/**
	 * ������
	 * 
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * ������
	 * 
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * ������
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ������
	 * 
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * ��ע
	 * 
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * ��ע
	 * 
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * ������
	 * 
	 */
	public void setApproveUser(String approveUser) {
		this.approveUser = approveUser;
	}

	/**
	 * ������
	 * 
	 */
	public String getApproveUser() {
		return this.approveUser;
	}

	/**
	 * �������
	 * 
	 */
	public void setApproceAdvice(String approceAdvice) {
		this.approceAdvice = approceAdvice;
	}

	/**
	 * �������
	 * 
	 */
	public String getApproceAdvice() {
		return this.approceAdvice;
	}

	/**
	 * ����״̬��3����ͨ��2����δͨ��1δ���0δ�ύ
	 * 
	 */
	public void setApproveState(Integer approveState) {
		this.approveState = approveState;
	}

	/**
	 * ����״̬��3����ͨ��2����δͨ��1δ���0δ�ύ
	 * 
	 */
	public Integer getApproveState() {
		return this.approveState;
	}

	/**
	 * ���ʱ��
	 * 
	 */
	public void setApproveTime(Calendar approveTime) {
		this.approveTime = approveTime;
	}

	/**
	 * ���ʱ��
	 * 
	 */
	public Calendar getApproveTime() {
		return this.approveTime;
	}

	/**
	 * �ܷ��ṩ���
	 * 
	 */
	public void setProvideSoftware(Boolean provideSoftware) {
		this.provideSoftware = provideSoftware;
	}

	/**
	 * �ܷ��ṩ���
	 * 
	 */
	public Boolean getProvideSoftware() {
		return this.provideSoftware;
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
	 * ���ܹ�����
	 * 
	 */
	public void setDongle(Boolean dongle) {
		this.dongle = dongle;
	}

	/**
	 * ���ܹ�����
	 * 
	 */
	public Boolean getDongle() {
		return this.dongle;
	}

	/**
	 */
	public void setCd(Boolean cd) {
		this.cd = cd;
	}

	/**
	 */
	public Boolean getCd() {
		return this.cd;
	}

	/**
	 * ��װʱ��
	 * 
	 */
	public void setRequireTime(Calendar requireTime) {
		this.requireTime = requireTime;
	}

	/**
	 * ��װʱ��
	 * 
	 */
	public Calendar getRequireTime() {
		return this.requireTime;
	}

	/**
	 * ����ܹ�
	 * 
	 */
	public void setFrame(String frame) {
		this.frame = frame;
	}

	/**
	 * ����ܹ�
	 * 
	 */
	public String getFrame() {
		return this.frame;
	}

	/**
	 * ��������
	 * 
	 */
	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	/**
	 * ��������
	 * 
	 */
	public String getApplyReason() {
		return this.applyReason;
	}

	/**
	 * ��Ӧ��
	 * 
	 */
	public void setSupply(String supply) {
		this.supply = supply;
	}

	/**
	 * ��Ӧ��
	 * 
	 */
	public String getSupply() {
		return this.supply;
	}

	/**
	 * ��Ӧ����ϵ�绰
	 * 
	 */
	public void setSupplyPhone(String supplyPhone) {
		this.supplyPhone = supplyPhone;
	}

	/**
	 * ��Ӧ����ϵ�绰
	 * 
	 */
	public String getSupplyPhone() {
		return this.supplyPhone;
	}

	/**
	 */
	public SoftwareReserve() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(SoftwareReserve that) {
		setId(that.getId());
		setCourse(that.getCourse());
		setRequirement(that.getRequirement());
		setTerm(that.getTerm());
		setTeacher(that.getTeacher());
		setPhone(that.getPhone());
		setPurpose(that.getPurpose());
		setCode(that.getCode());
		setName(that.getName());
		setRemark(that.getRemark());
		setApproveUser(that.getApproveUser());
		setApproceAdvice(that.getApproceAdvice());
		setApproveState(that.getApproveState());
		setApproveTime(that.getApproveTime());
		setProvideSoftware(that.getProvideSoftware());
		setCreateTime(that.getCreateTime());
		setDongle(that.getDongle());
		setCd(that.getCd());
		setRequireTime(that.getRequireTime());
		setFrame(that.getFrame());
		setApplyReason(that.getApplyReason());
		setSupply(that.getSupply());
		setSupplyPhone(that.getSupplyPhone());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("course=[").append(course).append("] ");
		buffer.append("requirement=[").append(requirement).append("] ");
		buffer.append("term=[").append(term).append("] ");
		buffer.append("teacher=[").append(teacher).append("] ");
		buffer.append("phone=[").append(phone).append("] ");
		buffer.append("purpose=[").append(purpose).append("] ");
		buffer.append("code=[").append(code).append("] ");
		buffer.append("name=[").append(name).append("] ");
		buffer.append("remark=[").append(remark).append("] ");
		buffer.append("approveUser=[").append(approveUser).append("] ");
		buffer.append("approceAdvice=[").append(approceAdvice).append("] ");
		buffer.append("approveState=[").append(approveState).append("] ");
		buffer.append("approveTime=[").append(approveTime).append("] ");
		buffer.append("provideSoftware=[").append(provideSoftware).append("] ");
		buffer.append("createTime=[").append(createTime).append("] ");
		buffer.append("dongle=[").append(dongle).append("] ");
		buffer.append("cd=[").append(cd).append("] ");
		buffer.append("requireTime=[").append(requireTime).append("] ");
		buffer.append("frame=[").append(frame).append("] ");
		buffer.append("applyReason=[").append(applyReason).append("] ");
		buffer.append("supply=[").append(supply).append("] ");
		buffer.append("supplyPhone=[").append(supplyPhone).append("] ");

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
		if (!(obj instanceof SoftwareReserve))
			return false;
		SoftwareReserve equalCheck = (SoftwareReserve) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
