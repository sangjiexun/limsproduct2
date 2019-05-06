package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.skyway.spring.util.databinding.TypeConversionUtils;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllLabRoomLendings", query = "select myLabRoomLending from LabRoomLending myLabRoomLending"),
		@NamedQuery(name = "findLabRoomLendingByApplyDate", query = "select myLabRoomLending from LabRoomLending myLabRoomLending where myLabRoomLending.applyDate = ?1"),
		@NamedQuery(name = "findLabRoomLendingByLendingStatus", query = "select myLabRoomLending from LabRoomLending myLabRoomLending where myLabRoomLending.lendingStatus = ?1"),
		@NamedQuery(name = "findLabRoomLendingById", query = "select myLabRoomLending from LabRoomLending myLabRoomLending where myLabRoomLending.id = ?1"),
		@NamedQuery(name = "findLabRoomLendingByLendingReason", query = "select myLabRoomLending from LabRoomLending myLabRoomLending where myLabRoomLending.lendingReason = ?1"),
		@NamedQuery(name = "findLabRoomLendingByLendingReasonContaining", query = "select myLabRoomLending from LabRoomLending myLabRoomLending where myLabRoomLending.lendingReason like ?1"),
		@NamedQuery(name = "findLabRoomLendingByLendingTime", query = "select myLabRoomLending from LabRoomLending myLabRoomLending where myLabRoomLending.lendingTime = ?1"),
		@NamedQuery(name = "findLabRoomLendingByLendingUserNumber", query = "select myLabRoomLending from LabRoomLending myLabRoomLending where myLabRoomLending.lendingUserNumber = ?1"),
		@NamedQuery(name = "findLabRoomLendingByLendingUserPhone", query = "select myLabRoomLending from LabRoomLending myLabRoomLending where myLabRoomLending.lendingUserPhone = ?1"),
		@NamedQuery(name = "findLabRoomLendingByLendingUserPhoneContaining", query = "select myLabRoomLending from LabRoomLending myLabRoomLending where myLabRoomLending.lendingUserPhone like ?1"),
		@NamedQuery(name = "findLabRoomLendingByPrimaryKey", query = "select myLabRoomLending from LabRoomLending myLabRoomLending where myLabRoomLending.id = ?1") })
@Table(name = "lab_room_lending")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/zjcclims/domain", name = "LabRoomLending")
public class LabRoomLending implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Description 主键-自增
	 */
	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
	Integer id;
	/**
	 * Description 借用日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "lending_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar lendingTime;
	/**
	 * Description 借用理由
	 */
	@Column(name = "lending_reason")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String lendingReason;
	
	@Column(name = "lending_unit")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String lendingUnit;
	/**
	 * Description 借用状态：0未提交，1待审核...
	 */
	@Column(name = "lending_status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer lendingStatus;
	/**
	 * Description 借用人数
	 */
	@Column(name = "lending_user_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer lendingUserNumber;
	/**
	 * Description 借用人电话
	 */
	@Column(name = "lending_user_phone")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String lendingUserPhone;
	/**
	 * Description 申请日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "apply_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar applyDate;
	/**
	 * 开始时间-精确到半点
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar startTime;
	
	public void setStartTime(Calendar startTime) {
		TypeConversionUtils.clearDateFields(startTime);
		this.startTime = startTime;
	}

	public Calendar getStartTime() {
		return this.startTime;
	}
	
	/**
	 * 结束时间-精确到半点
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar endTime;
	
	public void setEndTime(Calendar endTime) {
		TypeConversionUtils.clearDateFields(endTime);
		this.endTime = endTime;
	}

	public Calendar getEndTime() {
		return this.endTime;
	}

	/**
	 * Description 借用类型
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lending_type", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByLendingType;
	/**
	 * Description 使用人类型
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lending_user_type", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByLendingUserType;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_room", referencedColumnName = "id") })
	@XmlTransient
	LabRoom labRoom;
	
	/**
	 */
	@OneToMany(mappedBy = "labRoomLending", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomLendingResult> labRoomLendingResults;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "class", referencedColumnName = "class_number") })
	@XmlTransient
	SchoolClasses schoolClasses;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lending_user", referencedColumnName = "username") })
	@XmlTransient
	User userByLendingUser;
	
	
	/**
	 */
	@OneToMany(mappedBy = "labRoomLending", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabLendAudit> LabLendAudits;
	/**
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ���õ����
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public void setLendingTime(Calendar lendingTime) {
		this.lendingTime = lendingTime;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public Calendar getLendingTime() {
		return this.lendingTime;
	}

	/**
	 * ��������
	 * 
	 */
	public void setLendingUnit(String lendingUnit) {
		this.lendingUnit = lendingUnit;
	}

	/**
	 * ��������
	 * 
	 */
	public String getLendingUnit() {
		return this.lendingUnit;
	}
	
	public void setLendingReason(String lendingReason) {
		this.lendingReason = lendingReason;
	}

	/**
	 * ��������
	 * 
	 */
	public String getLendingReason() {
		return this.lendingReason;
	}

	public void setLendingStatus(Integer lendingStatus) {
		this.lendingStatus = lendingStatus;
	}

	/**
	 * ����״̬��3����ͨ��2����δͨ��1δ���0δ�ύ
	 * 
	 */
	public Integer getLendingStatus() {
		return this.lendingStatus;
	}
	
	/**
	 * ʹ������
	 * 
	 */
	public void setLendingUserNumber(Integer lendingUserNumber) {
		this.lendingUserNumber = lendingUserNumber;
	}

	/**
	 * ʹ������
	 * 
	 */
	public Integer getLendingUserNumber() {
		return this.lendingUserNumber;
	}

	/**
	 */
	public void setLabRoomLendingResults(Set<LabRoomLendingResult> labRoomLendingResults) {
		this.labRoomLendingResults = labRoomLendingResults;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomLendingResult> getLabRoomLendingResults() {
		if (labRoomLendingResults == null) {
			labRoomLendingResults = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomLendingResult>();
		}
		return labRoomLendingResults;
	}
	
	/**
	 * �����˵绰
	 * 
	 */
	public void setLendingUserPhone(String lendingUserPhone) {
		this.lendingUserPhone = lendingUserPhone;
	}

	/**
	 * �����˵绰
	 * 
	 */
	public String getLendingUserPhone() {
		return this.lendingUserPhone;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public void setApplyDate(Calendar applyDate) {
		this.applyDate = applyDate;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public Calendar getApplyDate() {
		return this.applyDate;
	}

	/**
	 */
	public void setLabRoomLendingType(CDictionary CDictionaryByLendingType) {
		this.CDictionaryByLendingType = CDictionaryByLendingType;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getLabRoomLendingType() {
		return CDictionaryByLendingType;
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
	public void setSchoolClasses(SchoolClasses schoolClasses) {
		this.schoolClasses = schoolClasses;
	}

	/**
	 */
	@JsonIgnore
	public SchoolClasses getSchoolClasses() {
		return schoolClasses;
	}
	
	/**
	 */
	public void setUserByLendingUser(User userByLendingUser) {
		this.userByLendingUser = userByLendingUser;
	}

	/**
	 */
	@JsonIgnore
	public User getUserByLendingUser() {
		return userByLendingUser;
	}

	/**
	 */
	public void setLendingUserType(CDictionary CDictionaryByLendingUserType) {
		this.CDictionaryByLendingUserType = CDictionaryByLendingUserType;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getLendingUserType() {
		return CDictionaryByLendingUserType;
	}

	/**
	 */
	public LabRoomLending() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabRoomLending that) {
		setId(that.getId());
		setLendingTime(that.getLendingTime());
		setLendingReason(that.getLendingReason());
		setLendingUserNumber(that.getLendingUserNumber());
		setLendingUserPhone(that.getLendingUserPhone());
		setApplyDate(that.getApplyDate());
		setLabRoom(that.getLabRoom());
		setUserByLendingUser(that.getUserByLendingUser());
		setSchoolClasses(that.getSchoolClasses());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("lendingTime=[").append(lendingTime).append("] ");
		buffer.append("lendingReason=[").append(lendingReason).append("] ");
		buffer.append("lendingUserNumber=[").append(lendingUserNumber).append("] ");
		buffer.append("lendingUserPhone=[").append(lendingUserPhone).append("] ");
		buffer.append("applyDate=[").append(applyDate).append("] ");

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
		if (!(obj instanceof LabRoomLending))
			return false;
		LabRoomLending equalCheck = (LabRoomLending) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
