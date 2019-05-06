package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllSystemPreDays", query = "select mySystemPreDay from SystemPreDay mySystemPreDay"),
		@NamedQuery(name = "findSystemPreDayByDayNum", query = "select mySystemPreDay from SystemPreDay mySystemPreDay where mySystemPreDay.dayNum = ?1"),
		@NamedQuery(name = "findSystemPreDayByDayProcess", query = "select mySystemPreDay from SystemPreDay mySystemPreDay where mySystemPreDay.process = ?1"),
		@NamedQuery(name = "findSystemPreDayById", query = "select mySystemPreDay from SystemPreDay mySystemPreDay where mySystemPreDay.id = ?1"),
		@NamedQuery(name = "findSystemPreDayByCreateTime", query = "select mySystemPreDay from SystemPreDay mySystemPreDay where mySystemPreDay.createTime = ?1"),
		@NamedQuery(name = "findSystemPreDayByPrimaryKey", query = "select mySystemPreDay from SystemPreDay mySystemPreDay where mySystemPreDay.id = ?1") })
@Table(name = "system_pre_day")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "SystemPreDay")
public class SystemPreDay implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ����ݽ�ʱ�����ñ�
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ��ǰ�ݽ�����
	 * 
	 */

	@Column(name = "day_num")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer dayNum;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "term_id", referencedColumnName = "id") })
	@XmlTransient
	SchoolTerm schoolTerm;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "creater", referencedColumnName = "username") })
	@XmlTransient
	User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createTime;
	
	@Column(name = "process")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer process;



	/**
	 * ����ݽ�ʱ�����ñ�
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ����ݽ�ʱ�����ñ�
	 * 
	 */
	public Integer getId() {
		return this.id;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 */
	@JsonIgnore
	public User getUser() {
		return user;
	}
	
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	/**
	 */
	public Calendar getCreateTime() {
		return this.createTime;
	}


	/**
	 * ��ǰ�ݽ�����
	 * 
	 */
	public void setDayNum(Integer dayNum) {
		this.dayNum = dayNum;
	}

	/**
	 * ��ǰ�ݽ�����
	 * 
	 */
	public Integer getDayNum() {
		return this.dayNum;
	}
	
	public void setProcess(Integer process) {
		this.process = process;
	}

	/**
	 * ����ݽ�ʱ�����ñ�
	 * 
	 */
	public Integer getProcess() {
		return this.process;
	}
	

	/**
	 */
	public void setSchoolTerm(SchoolTerm schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	/**
	 */
	@JsonIgnore
	public SchoolTerm getSchoolTerm() {
		return schoolTerm;
	}

	/**
	 */
	public SystemPreDay() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(SystemPreDay that) {
		setId(that.getId());
		setDayNum(that.getDayNum());
		setSchoolTerm(that.getSchoolTerm());
		setUser(that.getUser());
		setCreateTime(that.getCreateTime());
		setProcess(that.getProcess());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("dayNum=[").append(dayNum).append("] ");
		buffer.append("process=[").append(process).append("] ");
		buffer.append("createTime=[").append(createTime).append("] ");

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
		if (!(obj instanceof SystemPreDay))
			return false;
		SystemPreDay equalCheck = (SystemPreDay) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
