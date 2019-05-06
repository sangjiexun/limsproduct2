package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

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
		@NamedQuery(name = "findAllLabRoomReservationCredits", query = "select myLabRoomReservationCredit from LabRoomReservationCredit myLabRoomReservationCredit"),
		@NamedQuery(name = "findLabRoomReservationCreditById", query = "select myLabRoomReservationCredit from LabRoomReservationCredit myLabRoomReservationCredit where myLabRoomReservationCredit.id = ?1"),
		@NamedQuery(name = "findLabRoomReservationCreditByPrimaryKey", query = "select myLabRoomReservationCredit from LabRoomReservationCredit myLabRoomReservationCredit where myLabRoomReservationCredit.id = ?1"),
		@NamedQuery(name = "findLabRoomReservationCreditByRemark", query = "select myLabRoomReservationCredit from LabRoomReservationCredit myLabRoomReservationCredit where myLabRoomReservationCredit.remark = ?1"),
		@NamedQuery(name = "findLabRoomReservationCreditByRemarkContaining", query = "select myLabRoomReservationCredit from LabRoomReservationCredit myLabRoomReservationCredit where myLabRoomReservationCredit.remark like ?1") })
@Table(name = "lab_room_reservation_credit")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabRoomReservationCredit")
public class LabRoomReservationCredit implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ʵѵ��ԤԼ�����ǼǱ�
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	/**
	 * ��ע
	 * 
	 */

	@Column(name = "remark")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String remark;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "redit_option", referencedColumnName = "id") })
	@XmlTransient
	CreditOption creditOption;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "reservation", referencedColumnName = "id") })
	@XmlTransient
	LabReservation labReservation;
	
	/**
	 */
	@Column(name = "username")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String username;

	/**
	 * ʵѵ��ԤԼ�����ǼǱ�
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ʵѵ��ԤԼ�����ǼǱ�
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
	public void setCreditOption(CreditOption creditOption) {
		this.creditOption = creditOption;
	}

	/**
	 */
	@JsonIgnore
	public CreditOption getCreditOption() {
		return creditOption;
	}

	/**
	 */
	public void setLabReservation(LabReservation labReservation) {
		this.labReservation = labReservation;
	}

	/**
	 */
	@JsonIgnore
	public LabReservation getLabReservation() {
		return labReservation;
	}

	/**
	 */
	public LabRoomReservationCredit() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabRoomReservationCredit that) {
		setId(that.getId());
		setRemark(that.getRemark());
		setCreditOption(that.getCreditOption());
		setLabReservation(that.getLabReservation());
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
		if (!(obj instanceof LabRoomReservationCredit))
			return false;
		LabRoomReservationCredit equalCheck = (LabRoomReservationCredit) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
