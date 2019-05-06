package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

import net.zjcclims.domain.User;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllCreditOptions", query = "select myCreditOption from CreditOption myCreditOption"),
		@NamedQuery(name = "findCreditOptionByCreateDate", query = "select myCreditOption from CreditOption myCreditOption where myCreditOption.createDate = ?1"),
		/*@NamedQuery(name = "findCreditOptionByCreater", query = "select myCreditOption from CreditOption myCreditOption where myCreditOption.creater = ?1"),*/
		/*@NamedQuery(name = "findCreditOptionByCreaterContaining", query = "select myCreditOption from CreditOption myCreditOption where myCreditOption.creater like ?1"),*/
		@NamedQuery(name = "findCreditOptionByDeduction", query = "select myCreditOption from CreditOption myCreditOption where myCreditOption.deduction = ?1"),
		@NamedQuery(name = "findCreditOptionByEnabled", query = "select myCreditOption from CreditOption myCreditOption where myCreditOption.enabled = ?1"),
		@NamedQuery(name = "findCreditOptionById", query = "select myCreditOption from CreditOption myCreditOption where myCreditOption.id = ?1"),
		@NamedQuery(name = "findCreditOptionByMemo", query = "select myCreditOption from CreditOption myCreditOption where myCreditOption.memo = ?1"),
		@NamedQuery(name = "findCreditOptionByMemoContaining", query = "select myCreditOption from CreditOption myCreditOption where myCreditOption.memo like ?1"),
		@NamedQuery(name = "findCreditOptionByName", query = "select myCreditOption from CreditOption myCreditOption where myCreditOption.name = ?1"),
		@NamedQuery(name = "findCreditOptionByNameContaining", query = "select myCreditOption from CreditOption myCreditOption where myCreditOption.name like ?1"),
		@NamedQuery(name = "findCreditOptionByPrimaryKey", query = "select myCreditOption from CreditOption myCreditOption where myCreditOption.id = ?1"),
		@NamedQuery(name = "findCreditOptionByStatus", query = "select myCreditOption from CreditOption myCreditOption where myCreditOption.status = ?1") })
@Table(name = "credit_option")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "CreditOption")
public class CreditOption implements Serializable {
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
	 * �۷���
	 * 
	 */

	@Column(name = "name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String name;
	/**
	 * �۷�ֵ
	 * 
	 */

	@Column(name = "deduction")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer deduction;
	/**
	 * ������
	 * 
	 */

	/*@Column(name = "creater")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String creater;*/
	/**
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createDate;
	/**
	 * �Ƿ���ã�0������ 1���ã�
	 * 
	 */

	@Column(name = "enabled")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer enabled;
	/**
	 * ״̬��0�ݸ� 1�ѷ�����
	 * 
	 */

	@Column(name = "status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer status;
	/**
	 * ��ע
	 * 
	 */

	@Column(name = "memo")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String memo;

	/**
	 */
	@OneToMany(mappedBy = "creditOption", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomReservationCredit> labRoomReservationCredits;
	
	/**
	 */
	public void setLabRoomReservationCredits(Set<LabRoomReservationCredit> labRoomReservationCredits) {
		this.labRoomReservationCredits = labRoomReservationCredits;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomReservationCredit> getLabRoomReservationCredits() {
		if (labRoomReservationCredits == null) {
			labRoomReservationCredits = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomReservationCredit>();
		}
		return labRoomReservationCredits;
	}
	
	/**
	 */
	@OneToMany(mappedBy = "creditOption", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDeviceReservationCredit> labRoomDeviceReservationCredits;

	/**
	 */
	@OneToMany(mappedBy = "creditOption", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomStationReservationCredit> labRoomStationReservationCredits;
	
	/**
	 */
	public void setLabRoomStationReservationCredits(Set<LabRoomStationReservationCredit> labRoomStationReservationCredits) {
		this.labRoomStationReservationCredits = labRoomStationReservationCredits;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomStationReservationCredit> getLabRoomStationReservationCredits() {
		if (labRoomStationReservationCredits == null) {
			labRoomStationReservationCredits = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomStationReservationCredit>();
		}
		return labRoomStationReservationCredits;
	}
	
	/**
	 */
	public void setLabRoomDeviceReservationCredits(Set<LabRoomDeviceReservationCredit> labRoomDeviceReservationCredits) {
		this.labRoomDeviceReservationCredits = labRoomDeviceReservationCredits;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomDeviceReservationCredit> getLabRoomDeviceReservationCredits() {
		if (labRoomDeviceReservationCredits == null) {
			labRoomDeviceReservationCredits = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDeviceReservationCredit>();
		}
		return labRoomDeviceReservationCredits;
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
	 * �۷���
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * �۷���
	 * 
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * �۷�ֵ
	 * 
	 */
	public void setDeduction(Integer deduction) {
		this.deduction = deduction;
	}

	/**
	 * �۷�ֵ
	 * 
	 */
	public Integer getDeduction() {
		return this.deduction;
	}

	/**
	 * ������
	 * 
	 */
/*	public void setCreater(String creater) {
		this.creater = creater;
	}

	*//**
	 * ������
	 * 
	 *//*
	public String getCreater() {
		return this.creater;
	}*/

	/**
	 * ����ʱ��
	 * 
	 */
	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public Calendar getCreateDate() {
		return this.createDate;
	}

	/**
	 * �Ƿ���ã�0������ 1���ã�
	 * 
	 */
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	/**
	 * �Ƿ���ã�0������ 1���ã�
	 * 
	 */
	public Integer getEnabled() {
		return this.enabled;
	}

	/**
	 * ״̬��0�ݸ� 1�ѷ�����
	 * 
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * ״̬��0�ݸ� 1�ѷ�����
	 * 
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * ��ע
	 * 
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * ��ע
	 * 
	 */
	public String getMemo() {
		return this.memo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "creater", referencedColumnName = "username") })
	@XmlTransient
	User user;
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@JsonIgnore
	public User getUser() {
		return user;
	}
	
	/**
	 */
	public CreditOption() {
	}
	

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(CreditOption that) {
		setId(that.getId());
		setName(that.getName());
		setDeduction(that.getDeduction());
		/*setCreater(that.getCreater());*/
		setCreateDate(that.getCreateDate());
		setEnabled(that.getEnabled());
		setStatus(that.getStatus());
		setMemo(that.getMemo());
		/*setUser(that.getUser());*/
		setLabRoomDeviceReservationCredits(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDeviceReservationCredit>(that.getLabRoomDeviceReservationCredits()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("name=[").append(name).append("] ");
		buffer.append("deduction=[").append(deduction).append("] ");
	/*	buffer.append("creater=[").append(creater).append("] ");*/
		buffer.append("createDate=[").append(createDate).append("] ");
		buffer.append("enabled=[").append(enabled).append("] ");
		buffer.append("status=[").append(status).append("] ");
		buffer.append("memo=[").append(memo).append("] ");

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
		if (!(obj instanceof CreditOption))
			return false;
		CreditOption equalCheck = (CreditOption) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
	
}
