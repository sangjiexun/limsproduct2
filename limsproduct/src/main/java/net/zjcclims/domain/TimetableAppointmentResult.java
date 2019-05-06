package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllTimetableAppointmentResults", query = "select myTimetableAppointmentResult from TimetableAppointmentResult myTimetableAppointmentResult"),
		@NamedQuery(name = "findTimetableAppointmentResultByAuditResult", query = "select myTimetableAppointmentResult from TimetableAppointmentResult myTimetableAppointmentResult where myTimetableAppointmentResult.auditResult = ?1"),
		@NamedQuery(name = "findTimetableAppointmentResultByAuditResultContaining", query = "select myTimetableAppointmentResult from TimetableAppointmentResult myTimetableAppointmentResult where myTimetableAppointmentResult.auditResult like ?1"),
		@NamedQuery(name = "findTimetableAppointmentResultById", query = "select myTimetableAppointmentResult from TimetableAppointmentResult myTimetableAppointmentResult where myTimetableAppointmentResult.id = ?1"),
		@NamedQuery(name = "findTimetableAppointmentResultByPrimaryKey", query = "select myTimetableAppointmentResult from TimetableAppointmentResult myTimetableAppointmentResult where myTimetableAppointmentResult.id = ?1"),
		@NamedQuery(name = "findTimetableAppointmentResultByRemark", query = "select myTimetableAppointmentResult from TimetableAppointmentResult myTimetableAppointmentResult where myTimetableAppointmentResult.remark = ?1"),
		@NamedQuery(name = "findTimetableAppointmentResultByRemarkContaining", query = "select myTimetableAppointmentResult from TimetableAppointmentResult myTimetableAppointmentResult where myTimetableAppointmentResult.remark like ?1"),
		@NamedQuery(name = "findTimetableAppointmentResultByTag", query = "select myTimetableAppointmentResult from TimetableAppointmentResult myTimetableAppointmentResult where myTimetableAppointmentResult.tag = ?1"),
		@NamedQuery(name = "findTimetableAppointmentResultByUser", query = "select myTimetableAppointmentResult from TimetableAppointmentResult myTimetableAppointmentResult where myTimetableAppointmentResult.user = ?1"),
		@NamedQuery(name = "findTimetableAppointmentResultByUserContaining", query = "select myTimetableAppointmentResult from TimetableAppointmentResult myTimetableAppointmentResult where myTimetableAppointmentResult.user like ?1") })
@Table(name = "timetable_appointment_result")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "TimetableAppointmentResult")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class TimetableAppointmentResult implements Serializable {
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
	 * �����
	 * 
	 */

	@Column(name = "user")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String user;
	/**
	 * ��˽��
	 * 
	 */

	@Column(name = "audit_result")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String auditResult;
	/**
	 * �ſ�id
	 * 
	 */

	@Column(name = "coursecode")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String coursecode;
	public String getCoursecode() {
		return coursecode;
	}

	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}

	/**
	 * ������
	 * 
	 */

	@Column(name = "remark")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String remark;
	/**
	 * ��˵ȼ�
	 * 
	 */

	@Column(name = "tag")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer tag;

	@Column(name = "labroom_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer labroomId;
	public Integer getLabroomId() {
		return labroomId;
	}

	public void setLabroomId(Integer labroomId) {
		this.labroomId = labroomId;
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
	 * �����
	 * 
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * �����
	 * 
	 */
	public String getUser() {
		return this.user;
	}

	/**
	 * ��˽��
	 * 
	 */
	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	/**
	 * ��˽��
	 * 
	 */
	public String getAuditResult() {
		return this.auditResult;
	}



	/**
	 * ������
	 * 
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * ������
	 * 
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * ��˵ȼ�
	 * 
	 */
	public void setTag(Integer tag) {
		this.tag = tag;
	}

	/**
	 * ��˵ȼ�
	 * 
	 */
	public Integer getTag() {
		return this.tag;
	}

	/**
	 */
	public TimetableAppointmentResult() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(TimetableAppointmentResult that) {
		setId(that.getId());
		setUser(that.getUser());
		setAuditResult(that.getAuditResult());
		setRemark(that.getRemark());
		setTag(that.getTag());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("user=[").append(user).append("] ");
		buffer.append("auditResult=[").append(auditResult).append("] ");
		buffer.append("remark=[").append(remark).append("] ");
		buffer.append("tag=[").append(tag).append("] ");

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
		if (!(obj instanceof TimetableAppointmentResult))
			return false;
		TimetableAppointmentResult equalCheck = (TimetableAppointmentResult) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
