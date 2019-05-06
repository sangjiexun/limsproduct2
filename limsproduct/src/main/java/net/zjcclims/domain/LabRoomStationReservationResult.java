package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Calendar;
import java.util.Set;

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
		@NamedQuery(name = "findAllLabRoomStationReservationResults", query = "select myLabRoomStationReservationResult from LabRoomStationReservationResult myLabRoomStationReservationResult"),
		@NamedQuery(name = "findLabRoomStationReservationResultByAuditResult", query = "select myLabRoomStationReservationResult from LabRoomStationReservationResult myLabRoomStationReservationResult where myLabRoomStationReservationResult.auditResult = ?1"),
		@NamedQuery(name = "findLabRoomStationReservationResultByAuditTime", query = "select myLabRoomStationReservationResult from LabRoomStationReservationResult myLabRoomStationReservationResult where myLabRoomStationReservationResult.auditTime = ?1"),
		@NamedQuery(name = "findLabRoomStationReservationResultByAuditTimeAfter", query = "select myLabRoomStationReservationResult from LabRoomStationReservationResult myLabRoomStationReservationResult where myLabRoomStationReservationResult.auditTime > ?1"),
		@NamedQuery(name = "findLabRoomStationReservationResultByAuditTimeBefore", query = "select myLabRoomStationReservationResult from LabRoomStationReservationResult myLabRoomStationReservationResult where myLabRoomStationReservationResult.auditTime < ?1"),
		@NamedQuery(name = "findLabRoomStationReservationResultById", query = "select myLabRoomStationReservationResult from LabRoomStationReservationResult myLabRoomStationReservationResult where myLabRoomStationReservationResult.id = ?1"),
		@NamedQuery(name = "findLabRoomStationReservationResultByPrimaryKey", query = "select myLabRoomStationReservationResult from LabRoomStationReservationResult myLabRoomStationReservationResult where myLabRoomStationReservationResult.id = ?1"),
		@NamedQuery(name = "findLabRoomStationReservationResultByRemark", query = "select myLabRoomStationReservationResult from LabRoomStationReservationResult myLabRoomStationReservationResult where myLabRoomStationReservationResult.remark = ?1"),
		@NamedQuery(name = "findLabRoomStationReservationResultByRemarkContaining", query = "select myLabRoomStationReservationResult from LabRoomStationReservationResult myLabRoomStationReservationResult where myLabRoomStationReservationResult.remark like ?1"),
		@NamedQuery(name = "findLabRoomStationReservationResultByTag", query = "select myLabRoomStationReservationResult from LabRoomStationReservationResult myLabRoomStationReservationResult where myLabRoomStationReservationResult.tag = ?1") })
@Table(name = "lab_room_station_reservation_result")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zicclims/net/zjcclims/domain", name = "LabRoomStationReservationResult")
public class LabRoomStationReservationResult implements Serializable {
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
	 * ��˽��0��ͨ��1ͨ��
	 * 
	 */

	@Column(name = "audit_result")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer auditResult;
	/**
	 * ��ע
	 * 
	 */

	@Column(name = "remark")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String remark;
	/**
	 * ��־λ��1ϵ����2ʵѵ�ҹ���Ա3ʵѵ��������4ʵѵ�����Σ�
	 * 
	 */

	@Column(name = "tag")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer tag;
	/**
	 * ���ʱ��
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "audit_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar auditTime;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "audit_user", referencedColumnName = "username") })
	@XmlTransient
	User user;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_room_reservation", referencedColumnName = "id") })
	@XmlTransient
	LabRoomStationReservation labRoomStationReservation;

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
	 * ��˽��0��ͨ��1ͨ��
	 * 
	 */
	public void setAuditResult(Integer auditResult) {
		this.auditResult = auditResult;
	}

	/**
	 * ��˽��0��ͨ��1ͨ��
	 * 
	 */
	public Integer getAuditResult() {
		return this.auditResult;
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
	 * ��־λ��1ϵ����2ʵѵ�ҹ���Ա3ʵѵ��������4ʵѵ�����Σ�
	 * 
	 */
	public void setTag(Integer tag) {
		this.tag = tag;
	}

	/**
	 * ��־λ��1ϵ����2ʵѵ�ҹ���Ա3ʵѵ��������4ʵѵ�����Σ�
	 * 
	 */
	public Integer getTag() {
		return this.tag;
	}

	/**
	 * ���ʱ��
	 * 
	 */
	public void setAuditTime(Calendar auditTime) {
		this.auditTime = auditTime;
	}

	/**
	 * ���ʱ��
	 * 
	 */
	public Calendar getAuditTime() {
		return this.auditTime;
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
	public void setLabRoomStationReservation(LabRoomStationReservation labRoomStationReservation) {
		this.labRoomStationReservation = labRoomStationReservation;
	}

	/**
	 */
	@JsonIgnore
	public LabRoomStationReservation getLabRoomStationReservation() {
		return labRoomStationReservation;
	}

	/**
	 */
	public LabRoomStationReservationResult() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabRoomStationReservationResult that) {
		setId(that.getId());
		setAuditResult(that.getAuditResult());
		setRemark(that.getRemark());
		setTag(that.getTag());
		setAuditTime(that.getAuditTime());
		setUser(that.getUser());
		setLabRoomStationReservation(that.getLabRoomStationReservation());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("auditResult=[").append(auditResult).append("] ");
		buffer.append("remark=[").append(remark).append("] ");
		buffer.append("tag=[").append(tag).append("] ");
		buffer.append("auditTime=[").append(auditTime).append("] ");

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
		if (!(obj instanceof LabRoomStationReservationResult))
			return false;
		LabRoomStationReservationResult equalCheck = (LabRoomStationReservationResult) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
