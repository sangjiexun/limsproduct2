package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
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
		@NamedQuery(name = "findAllLabRoomStationReservationCredits", query = "select myLabRoomStationReservationCredit from LabRoomStationReservationCredit myLabRoomStationReservationCredit"),
		@NamedQuery(name = "findLabRoomStationReservationCreditById", query = "select myLabRoomStationReservationCredit from LabRoomStationReservationCredit myLabRoomStationReservationCredit where myLabRoomStationReservationCredit.id = ?1"),
		@NamedQuery(name = "findLabRoomStationReservationCreditByPrimaryKey", query = "select myLabRoomStationReservationCredit from LabRoomStationReservationCredit myLabRoomStationReservationCredit where myLabRoomStationReservationCredit.id = ?1"),
		@NamedQuery(name = "findLabRoomStationReservationCreditByRemark", query = "select myLabRoomStationReservationCredit from LabRoomStationReservationCredit myLabRoomStationReservationCredit where myLabRoomStationReservationCredit.remark = ?1"),
		@NamedQuery(name = "findLabRoomStationReservationCreditByRemarkContaining", query = "select myLabRoomStationReservationCredit from LabRoomStationReservationCredit myLabRoomStationReservationCredit where myLabRoomStationReservationCredit.remark like ?1") })
@Table(name = "lab_room_station_reservation_credit")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zicclims/net/zjcclims/domain", name = "LabRoomStationReservationCredit")
public class LabRoomStationReservationCredit implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ʵѵ�ҹ�λԤԼ�����ǼǱ�
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy =GenerationType.AUTO)
	@Id
	@XmlElement
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
	LabRoomStationReservation labRoomStationReservation;
	
	/**
	 */
	@Column(name = "username")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String username;
	/**
	 * ʵѵ�ҹ�λԤԼ�����ǼǱ�
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ʵѵ�ҹ�λԤԼ�����ǼǱ�
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
	public LabRoomStationReservationCredit() {
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
	public void copy(LabRoomStationReservationCredit that) {
		setId(that.getId());
		setRemark(that.getRemark());
		setCreditOption(that.getCreditOption());
		setLabRoomStationReservation(that.getLabRoomStationReservation());
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
		if (!(obj instanceof LabRoomStationReservationCredit))
			return false;
		LabRoomStationReservationCredit equalCheck = (LabRoomStationReservationCredit) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
