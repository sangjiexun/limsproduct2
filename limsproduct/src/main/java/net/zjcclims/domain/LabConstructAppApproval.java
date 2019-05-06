package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllLabConstructAppApprovals", query = "select myLabConstructAppApproval from LabConstructAppApproval myLabConstructAppApproval"),
		@NamedQuery(name = "findLabConstructAppApprovalByCreatedate", query = "select myLabConstructAppApproval from LabConstructAppApproval myLabConstructAppApproval where myLabConstructAppApproval.createdate = ?1"),
		@NamedQuery(name = "findLabConstructAppApprovalByCreatedateAfter", query = "select myLabConstructAppApproval from LabConstructAppApproval myLabConstructAppApproval where myLabConstructAppApproval.createdate > ?1"),
		@NamedQuery(name = "findLabConstructAppApprovalByCreatedateBefore", query = "select myLabConstructAppApproval from LabConstructAppApproval myLabConstructAppApproval where myLabConstructAppApproval.createdate < ?1"),
		@NamedQuery(name = "findLabConstructAppApprovalByFlag", query = "select myLabConstructAppApproval from LabConstructAppApproval myLabConstructAppApproval where myLabConstructAppApproval.flag = ?1"),
		@NamedQuery(name = "findLabConstructAppApprovalById", query = "select myLabConstructAppApproval from LabConstructAppApproval myLabConstructAppApproval where myLabConstructAppApproval.id = ?1"),
		@NamedQuery(name = "findLabConstructAppApprovalByLevel", query = "select myLabConstructAppApproval from LabConstructAppApproval myLabConstructAppApproval where myLabConstructAppApproval.level = ?1"),
		@NamedQuery(name = "findLabConstructAppApprovalByPrimaryKey", query = "select myLabConstructAppApproval from LabConstructAppApproval myLabConstructAppApproval where myLabConstructAppApproval.id = ?1"),
		@NamedQuery(name = "findLabConstructAppApprovalByResult", query = "select myLabConstructAppApproval from LabConstructAppApproval myLabConstructAppApproval where myLabConstructAppApproval.result = ?1"),
		@NamedQuery(name = "findLabConstructAppApprovalByResultContaining", query = "select myLabConstructAppApproval from LabConstructAppApproval myLabConstructAppApproval where myLabConstructAppApproval.result like ?1") })
@Table(name = "lab_construct_app_approval")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabConstructAppApproval")
public class LabConstructAppApproval implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	Integer id;
	/**
	 */

	@Column(name = "result")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String result;
	/**
	 * ��������
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "createdate")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createdate;
	/**
	 * ������1ͬ��0��ͬ��
	 * 
	 */

	@Column(name = "flag")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer flag;
	/**
	 */

	@Column(name = "level")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer level;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "creater", referencedColumnName = "username") })
	@XmlTransient
	User user;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_construct_app_id", referencedColumnName = "id") })
	@XmlTransient
	LabConstructApp labConstructApp;

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
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 */
	public String getResult() {
		return this.result;
	}

	/**
	 * ��������
	 * 
	 */
	public void setCreatedate(Calendar createdate) {
		this.createdate = createdate;
	}

	/**
	 * ��������
	 * 
	 */
	public Calendar getCreatedate() {
		return this.createdate;
	}

	/**
	 * ������1ͬ��0��ͬ��
	 * 
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	/**
	 * ������1ͬ��0��ͬ��
	 * 
	 */
	public Integer getFlag() {
		return this.flag;
	}

	/**
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 */
	public Integer getLevel() {
		return this.level;
	}

	/**
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 */
	public User getUser() {
		return user;
	}

	/**
	 */
	public void setLabConstructApp(LabConstructApp labConstructApp) {
		this.labConstructApp = labConstructApp;
	}

	/**
	 */
	public LabConstructApp getLabConstructApp() {
		return labConstructApp;
	}

	/**
	 */
	public LabConstructAppApproval() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabConstructAppApproval that) {
		setId(that.getId());
		setResult(that.getResult());
		setCreatedate(that.getCreatedate());
		setFlag(that.getFlag());
		setLevel(that.getLevel());
		setUser(that.getUser());
		setLabConstructApp(that.getLabConstructApp());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("result=[").append(result).append("] ");
		buffer.append("createdate=[").append(createdate).append("] ");
		buffer.append("flag=[").append(flag).append("] ");
		buffer.append("level=[").append(level).append("] ");

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
		if (!(obj instanceof LabConstructAppApproval))
			return false;
		LabConstructAppApproval equalCheck = (LabConstructAppApproval) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
