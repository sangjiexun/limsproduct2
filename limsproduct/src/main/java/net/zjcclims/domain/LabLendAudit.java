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
		@NamedQuery(name = "findAllLabLendAudits", query = "select myLabLendAudit from LabLendAudit myLabLendAudit"),
		@NamedQuery(name = "findLabLendAuditByAuditRoles", query = "select myLabLendAudit from LabLendAudit myLabLendAudit where myLabLendAudit.auditRoles = ?1"),
		@NamedQuery(name = "findLabLendAuditByCreateDate", query = "select myLabLendAudit from LabLendAudit myLabLendAudit where myLabLendAudit.createDate = ?1"),
		@NamedQuery(name = "findLabLendAuditById", query = "select myLabLendAudit from LabLendAudit myLabLendAudit where myLabLendAudit.id = ?1"),
		@NamedQuery(name = "findLabLendAuditByMem", query = "select myLabLendAudit from LabLendAudit myLabLendAudit where myLabLendAudit.mem = ?1"),
		@NamedQuery(name = "findLabLendAuditByPrimaryKey", query = "select myLabLendAudit from LabLendAudit myLabLendAudit where myLabLendAudit.id = ?1"),
		@NamedQuery(name = "findLabLendAuditByResult", query = "select myLabLendAudit from LabLendAudit myLabLendAudit where myLabLendAudit.result = ?1"),
		@NamedQuery(name = "findLabLendAuditByResultContaining", query = "select myLabLendAudit from LabLendAudit myLabLendAudit where myLabLendAudit.result like ?1"),
		@NamedQuery(name = "findLabLendAuditByStatus", query = "select myLabLendAudit from LabLendAudit myLabLendAudit where myLabLendAudit.status = ?1") })
@Table(name = "oper_item_audit")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcctext/net/zjcclims/domain", name = "LabLendAudit")
public class LabLendAudit implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * �������״̬ ��1���ͨ��2��˾ܾ�
	 * 
	 */

	@Column(name = "status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer status;
	/**
	 * ������
	 * 
	 */

	@Column(name = "result")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String result;
	/**
	 * ��˽�ɫ
	 * 
	 */

	@Column(name = "audit_roles")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer auditRoles;
	/**
	 * ���ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createDate;
	/**
	 * ��ע
	 * 
	 */

	@Column(name = "mem", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String mem;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "audit_user", referencedColumnName = "username") })
	@XmlTransient
	User user;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_lend_id", referencedColumnName = "id") })
	@XmlTransient
	LabRoomLending labRoomLending;

	public LabRoomLending getLabRoomLending() {
		return labRoomLending;
	}

	public void setLabRoomLending(LabRoomLending labRoomLending) {
		this.labRoomLending = labRoomLending;
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
	 * �������״̬ ��1���ͨ��2��˾ܾ�
	 * 
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * �������״̬ ��1���ͨ��2��˾ܾ�
	 * 
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * ������
	 * 
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * ������
	 * 
	 */
	public String getResult() {
		return this.result;
	}

	/**
	 * ��˽�ɫ
	 * 
	 */
	public void setAuditRoles(Integer auditRoles) {
		this.auditRoles = auditRoles;
	}

	/**
	 * ��˽�ɫ
	 * 
	 */
	public Integer getAuditRoles() {
		return this.auditRoles;
	}

	/**
	 * ���ʱ��
	 * 
	 */
	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}

	/**
	 * ���ʱ��
	 * 
	 */
	public Calendar getCreateDate() {
		return this.createDate;
	}

	/**
	 * ��ע
	 * 
	 */
	public void setMem(String mem) {
		this.mem = mem;
	}

	/**
	 * ��ע
	 * 
	 */
	public String getMem() {
		return this.mem;
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
	public LabLendAudit() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabLendAudit that) {
		setId(that.getId());
		setStatus(that.getStatus());
		setResult(that.getResult());
		setAuditRoles(that.getAuditRoles());
		setCreateDate(that.getCreateDate());
		setMem(that.getMem());
		setUser(that.getUser());
		setLabRoomLending(that.getLabRoomLending());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("status=[").append(status).append("] ");
		buffer.append("result=[").append(result).append("] ");
		buffer.append("auditRoles=[").append(auditRoles).append("] ");
		buffer.append("createDate=[").append(createDate).append("] ");
		buffer.append("mem=[").append(mem).append("] ");

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
		if (!(obj instanceof LabLendAudit))
			return false;
		LabLendAudit equalCheck = (LabLendAudit) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
