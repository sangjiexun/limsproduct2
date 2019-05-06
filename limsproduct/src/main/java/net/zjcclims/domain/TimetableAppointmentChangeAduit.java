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
		@NamedQuery(name = "findAllTimetableAppointmentChangeAduits", query = "select myTimetableAppointmentChangeAduit from TimetableAppointmentChangeAduit myTimetableAppointmentChangeAduit"),
		@NamedQuery(name = "findTimetableAppointmentChangeAduitByCreateDate", query = "select myTimetableAppointmentChangeAduit from TimetableAppointmentChangeAduit myTimetableAppointmentChangeAduit where myTimetableAppointmentChangeAduit.createDate = ?1"),
		@NamedQuery(name = "findTimetableAppointmentChangeAduitByCreateDateAfter", query = "select myTimetableAppointmentChangeAduit from TimetableAppointmentChangeAduit myTimetableAppointmentChangeAduit where myTimetableAppointmentChangeAduit.createDate > ?1"),
		@NamedQuery(name = "findTimetableAppointmentChangeAduitByCreateDateBefore", query = "select myTimetableAppointmentChangeAduit from TimetableAppointmentChangeAduit myTimetableAppointmentChangeAduit where myTimetableAppointmentChangeAduit.createDate < ?1"),
		@NamedQuery(name = "findTimetableAppointmentChangeAduitById", query = "select myTimetableAppointmentChangeAduit from TimetableAppointmentChangeAduit myTimetableAppointmentChangeAduit where myTimetableAppointmentChangeAduit.id = ?1"),
		@NamedQuery(name = "findTimetableAppointmentChangeAduitByMem", query = "select myTimetableAppointmentChangeAduit from TimetableAppointmentChangeAduit myTimetableAppointmentChangeAduit where myTimetableAppointmentChangeAduit.mem = ?1"),
		@NamedQuery(name = "findTimetableAppointmentChangeAduitByMemContaining", query = "select myTimetableAppointmentChangeAduit from TimetableAppointmentChangeAduit myTimetableAppointmentChangeAduit where myTimetableAppointmentChangeAduit.mem like ?1"),
		@NamedQuery(name = "findTimetableAppointmentChangeAduitByPrimaryKey", query = "select myTimetableAppointmentChangeAduit from TimetableAppointmentChangeAduit myTimetableAppointmentChangeAduit where myTimetableAppointmentChangeAduit.id = ?1"),
		@NamedQuery(name = "findTimetableAppointmentChangeAduitByResult", query = "select myTimetableAppointmentChangeAduit from TimetableAppointmentChangeAduit myTimetableAppointmentChangeAduit where myTimetableAppointmentChangeAduit.result = ?1"),
		@NamedQuery(name = "findTimetableAppointmentChangeAduitByResultContaining", query = "select myTimetableAppointmentChangeAduit from TimetableAppointmentChangeAduit myTimetableAppointmentChangeAduit where myTimetableAppointmentChangeAduit.result like ?1"),
		@NamedQuery(name = "findTimetableAppointmentChangeAduitByStatus", query = "select myTimetableAppointmentChangeAduit from TimetableAppointmentChangeAduit myTimetableAppointmentChangeAduit where myTimetableAppointmentChangeAduit.status = ?1") })
@Table(name = "timetable_appointment_change_aduit")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zicclims/net/zjcclims/domain", name = "TimetableAppointmentChangeAduit")
public class TimetableAppointmentChangeAduit implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ������˱�
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
	Integer id;
	/**
	 * ��־λ��2ϵ����3ʵѵ�ҹ���Ա4ʵѵ��ѧ���飩
	 * 
	 */

	@Column(name = "status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer status;
	/**
	 * ��˽��0��ͨ��1ͨ��
	 * 
	 */

	@Column(name = "result")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String result;
	/**
	 * ���ʱ��
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "create_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createDate;
	/**
	 * ��ע
	 * 
	 */

	@Column(name = "mem")
	@Basic(fetch = FetchType.EAGER)
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
	@JoinColumns({ @JoinColumn(name = "timetable_appointment_change_id", referencedColumnName = "id") })
	@XmlTransient
	TimetableAppointmentChange timetableAppointmentChange;

	/**
	 * ������˱�
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ������˱�
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ��־λ��2ϵ����3ʵѵ�ҹ���Ա4ʵѵ��ѧ���飩
	 * 
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * ��־λ��2ϵ����3ʵѵ�ҹ���Ա4ʵѵ��ѧ���飩
	 * 
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * ��˽��0��ͨ��1ͨ��
	 * 
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * ��˽��0��ͨ��1ͨ��
	 * 
	 */
	public String getResult() {
		return this.result;
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
	public void setTimetableAppointmentChange(TimetableAppointmentChange timetableAppointmentChange) {
		this.timetableAppointmentChange = timetableAppointmentChange;
	}

	/**
	 */
	@JsonIgnore
	public TimetableAppointmentChange getTimetableAppointmentChange() {
		return timetableAppointmentChange;
	}

	/**
	 */
	public TimetableAppointmentChangeAduit() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(TimetableAppointmentChangeAduit that) {
		setId(that.getId());
		setStatus(that.getStatus());
		setResult(that.getResult());
		setCreateDate(that.getCreateDate());
		setMem(that.getMem());
		setUser(that.getUser());
		setTimetableAppointmentChange(that.getTimetableAppointmentChange());
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
		if (!(obj instanceof TimetableAppointmentChangeAduit))
			return false;
		TimetableAppointmentChangeAduit equalCheck = (TimetableAppointmentChangeAduit) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
