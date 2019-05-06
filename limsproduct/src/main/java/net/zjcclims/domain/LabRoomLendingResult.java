package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

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
		@NamedQuery(name = "findAllLabRoomLendingResults", query = "select myLabRoomLendingResult from LabRoomLendingResult myLabRoomLendingResult"),
		@NamedQuery(name = "findLabRoomLendingResultById", query = "select myLabRoomLendingResult from LabRoomLendingResult myLabRoomLendingResult where myLabRoomLendingResult.id = ?1"),
		@NamedQuery(name = "findLabRoomLendingResultByPrimaryKey", query = "select myLabRoomLendingResult from LabRoomLendingResult myLabRoomLendingResult where myLabRoomLendingResult.id = ?1"),
		@NamedQuery(name = "findLabRoomLendingResultByRemark", query = "select myLabRoomLendingResult from LabRoomLendingResult myLabRoomLendingResult where myLabRoomLendingResult.remark = ?1") })
@Table(name = "lab_room_lending_result")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabRoomLendingResult")
public class LabRoomLendingResult implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ʵѵ�ҽ�����˽���
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
	Integer id;
	/**
	 * ��ע
	 * 
	 */

	@Column(name = "remark", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String remark;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lending_id", referencedColumnName = "id") })
	@XmlTransient
	LabRoomLending labRoomLending;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "audit_result", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionary;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "audit_user", referencedColumnName = "username") })
	@XmlTransient
	User user;

	/**
	 * ʵѵ�ҽ�����˽���
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ʵѵ�ҽ�����˽���
	 * 
	 */
	public Integer getId() {
		return this.id;
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
	 */
	public void setLabRoomLending(LabRoomLending labRoomLending) {
		this.labRoomLending = labRoomLending;
	}

	/**
	 */
	@JsonIgnore
	public LabRoomLending getLabRoomLending() {
		return labRoomLending;
	}

	/**
	 */
	public void setCDictionary(CDictionary CDictionary) {
		this.CDictionary = CDictionary;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionary() {
		return CDictionary;
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
	public LabRoomLendingResult() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabRoomLendingResult that) {
		setId(that.getId());
		setRemark(that.getRemark());
		setLabRoomLending(that.getLabRoomLending());
		setCDictionary(that.getCDictionary());
		setUser(that.getUser());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("remark=[").append(remark).append("] ");

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
		if (!(obj instanceof LabRoomLendingResult))
			return false;
		LabRoomLendingResult equalCheck = (LabRoomLendingResult) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
