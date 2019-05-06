package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllLabRoomDeviceLendings", query = "select myLabRoomDeviceLending from LabRoomDeviceLending myLabRoomDeviceLending"),
		@NamedQuery(name = "findLabRoomDeviceLendingByBackTime", query = "select myLabRoomDeviceLending from LabRoomDeviceLending myLabRoomDeviceLending where myLabRoomDeviceLending.backTime = ?1"),
		@NamedQuery(name = "findLabRoomDeviceLendingByContent", query = "select myLabRoomDeviceLending from LabRoomDeviceLending myLabRoomDeviceLending where myLabRoomDeviceLending.content = ?1"),
		@NamedQuery(name = "findLabRoomDeviceLendingById", query = "select myLabRoomDeviceLending from LabRoomDeviceLending myLabRoomDeviceLending where myLabRoomDeviceLending.id = ?1"),
		@NamedQuery(name = "findLabRoomDeviceLendingByLendingTime", query = "select myLabRoomDeviceLending from LabRoomDeviceLending myLabRoomDeviceLending where myLabRoomDeviceLending.lendingTime = ?1"),
		@NamedQuery(name = "findLabRoomDeviceLendingByPrimaryKey", query = "select myLabRoomDeviceLending from LabRoomDeviceLending myLabRoomDeviceLending where myLabRoomDeviceLending.id = ?1"),
		@NamedQuery(name = "findLabRoomDeviceLendingByStage", query = "select myLabRoomDeviceLending from LabRoomDeviceLending myLabRoomDeviceLending where myLabRoomDeviceLending.stage = ?1"),
		@NamedQuery(name = "findLabRoomDeviceLendingByReturnTime", query = "select myLabRoomDeviceLending from LabRoomDeviceLending myLabRoomDeviceLending where myLabRoomDeviceLending.returnTime = ?1") })
@Table(name = "lab_room_device_lending")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabRoomDeviceLending")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class LabRoomDeviceLending implements Serializable {
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
	 * ��������
	 * 
	 */

	@Column(name = "content", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String content;
	
	@Column(name = "lend_batch", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String lendBatch;
	
	@Column(name = "remark", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String remark;
	
	@Column(name = "lend_type")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String lendType;
	
	@Column(name = "parts")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String parts;
	/**
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lending_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar lendingTime;
	/**
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar startTime;
	/**
	 * Ԥ�ƹ黹ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "return_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar returnTime;
	/**
	 * ʵ�ʹ黹ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "back_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar backTime;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "Lending_user", referencedColumnName = "username") })
	@XmlTransient
	User userByLendingUser;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "teacher", referencedColumnName = "username") })
	@XmlTransient
	User userByTeacher;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "department_head", referencedColumnName = "username") })
	@XmlTransient
	User userByDepartmentHead;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "school_device_number", referencedColumnName = "id") })
	@XmlTransient
	LabRoomDevice labRoomDevice;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lending_status", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionary;
	
	/**
	 */
	@OneToMany(mappedBy = "labRoomDeviceLending", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDeviceLendingResult> labRoomDeviceLendingResults;

	/**
	 * 阶段（0未审核 1系主任审核通过 2实训室管理员审核通过 3实训室主任审核通过）
	 * 
	 */

	@Column(name = "stage")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer stage;
	
	/**
	 * 阶段（0未审核 1系主任审核通过 2实训室管理员审核通过 3实训室主任审核通过）
	 * 
	 */
	public void setStage(Integer stage) {
		this.stage = stage;
	}

	/**
	 * 阶段（0未审核 1系主任审核通过 2实训室管理员审核通过 3实训室主任审核通过）
	 * 
	 */
	public Integer getStage() {
		return this.stage;
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
	 * ��������
	 * 
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * ��������
	 * 
	 */
	public String getContent() {
		return this.content;
	}

	
	public String getLendType() {
		return lendType;
	}

	public void setLendType(String lendType) {
		this.lendType = lendType;
	}
	
	

	public String getParts() {
		return parts;
	}

	public void setParts(String parts) {
		this.parts = parts;
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
	 * Ԥ�ƹ黹ʱ��
	 * 
	 */
	public void setReturnTime(Calendar returnTime) {
		this.returnTime = returnTime;
	}

	/**
	 * Ԥ�ƹ黹ʱ��
	 * 
	 */
	public Calendar getReturnTime() {
		return this.returnTime;
	}

	/**
	 * ʵ�ʹ黹ʱ��
	 * 
	 */
	public void setBackTime(Calendar backTime) {
		this.backTime = backTime;
	}

	/**
	 * ʵ�ʹ黹ʱ��
	 * 
	 */
	public Calendar getBackTime() {
		return this.backTime;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the lendBatch
	 */
	public String getLendBatch() {
		return lendBatch;
	}

	/**
	 * @param lendBatch the lendBatch to set
	 */
	public void setLendBatch(String lendBatch) {
		this.lendBatch = lendBatch;
	}

	/**
	 * @return the startTime
	 */
	public Calendar getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
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
	public void setUserByTeacher(User userByTeacher) {
		this.userByTeacher = userByTeacher;
	}

	/**
	 */
	@JsonIgnore
	public User getUserByTeacher() {
		return userByTeacher;
	}
	/**
	 */
	public void setUserByDepartmentHead(User userByDepartmentHead) {
		this.userByDepartmentHead = userByDepartmentHead;
	}

	/**
	 */
	@JsonIgnore
	public User getUserByDepartmentHead() {
		return userByDepartmentHead;
	}
	

	public LabRoomDevice getLabRoomDevice() {
		return labRoomDevice;
	}

	public void setLabRoomDevice(LabRoomDevice labRoomDevice) {
		this.labRoomDevice = labRoomDevice;
	}


	public CDictionary getCDictionary() {
		return CDictionary;
	}

	public void setCDictionary(CDictionary cDictionary) {
		CDictionary = cDictionary;
	}

	/**
	 */
	public void setLabRoomDeviceLendingResults(Set<LabRoomDeviceLendingResult> labRoomDeviceLendingResults) {
		this.labRoomDeviceLendingResults = labRoomDeviceLendingResults;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomDeviceLendingResult> getLabRoomDeviceLendingResults() {
		if (labRoomDeviceLendingResults == null) {
			labRoomDeviceLendingResults = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDeviceLendingResult>();
		}
		return labRoomDeviceLendingResults;
	}

	/**
	 */
	public LabRoomDeviceLending() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabRoomDeviceLending that) {
		setId(that.getId());
		setContent(that.getContent());
		setLendType(that.getLendType());
		setParts(that.getParts());
		setLendingTime(that.getLendingTime());
		setReturnTime(that.getReturnTime());
		setBackTime(that.getBackTime());
		setUserByLendingUser(that.getUserByLendingUser());
		setUserByTeacher(that.getUserByTeacher());
		setUserByDepartmentHead(that.getUserByDepartmentHead());
		setLabRoomDevice(that.getLabRoomDevice());
		setCDictionary(that.getCDictionary());
		setStage(that.getStage());
		setLabRoomDeviceLendingResults(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDeviceLendingResult>(that.getLabRoomDeviceLendingResults()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("content=[").append(content).append("] ");
		buffer.append("lendingTime=[").append(lendingTime).append("] ");
		buffer.append("returnTime=[").append(returnTime).append("] ");
		buffer.append("backTime=[").append(backTime).append("] ");
		buffer.append("stage=[").append(stage).append("] ");
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
		if (!(obj instanceof LabRoomDeviceLending))
			return false;
		LabRoomDeviceLending equalCheck = (LabRoomDeviceLending) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
