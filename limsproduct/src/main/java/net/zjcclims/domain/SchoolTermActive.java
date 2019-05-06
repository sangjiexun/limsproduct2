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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllSchoolTermActives", query = "select mySchoolTermActive from SchoolTermActive mySchoolTermActive"),
		@NamedQuery(name = "findSchoolTermActiveByActiveFinishtime", query = "select mySchoolTermActive from SchoolTermActive mySchoolTermActive where mySchoolTermActive.activeFinishtime = ?1"),
		@NamedQuery(name = "findSchoolTermActiveByActiveStarttime", query = "select mySchoolTermActive from SchoolTermActive mySchoolTermActive where mySchoolTermActive.activeStarttime = ?1"),
		@NamedQuery(name = "findSchoolTermActiveByDeadLine", query = "select mySchoolTermActive from SchoolTermActive mySchoolTermActive where mySchoolTermActive.deadLine = ?1"),
		@NamedQuery(name = "findSchoolTermActiveById", query = "select mySchoolTermActive from SchoolTermActive mySchoolTermActive where mySchoolTermActive.id = ?1"),
		@NamedQuery(name = "findSchoolTermActiveByPrimaryKey", query = "select mySchoolTermActive from SchoolTermActive mySchoolTermActive where mySchoolTermActive.id = ?1") })
@Table(name = "school_term_active")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "SchoolTermActive")
public class SchoolTermActive implements Serializable {
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
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "active_starttime")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar activeStarttime;
	/**
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "active_finishtime")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar activeFinishtime;
	/**
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deadLine")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar deadLine;

	public Calendar getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Calendar deadLine) {
		this.deadLine = deadLine;
	}

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "school_term_id", referencedColumnName = "id") })
	@XmlTransient
	SchoolTerm schoolTerm;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "user_no", referencedColumnName = "username") })
	@XmlTransient
	User user;
	/**
	 */
	

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
	public void setActiveStarttime(Calendar activeStarttime) {
		this.activeStarttime = activeStarttime;
	}

	/**
	 */
	public Calendar getActiveStarttime() {
		return this.activeStarttime;
	}

	/**
	 */
	public void setActiveFinishtime(Calendar activeFinishtime) {
		this.activeFinishtime = activeFinishtime;
	}

	/**
	 */
	public Calendar getActiveFinishtime() {
		return this.activeFinishtime;
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
	public SchoolTermActive() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(SchoolTermActive that) {
		setId(that.getId());
		setActiveStarttime(that.getActiveStarttime());
		setActiveFinishtime(that.getActiveFinishtime());
		setSchoolTerm(that.getSchoolTerm());
		setUser(that.getUser());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("activeStarttime=[").append(activeStarttime).append("] ");
		buffer.append("activeFinishtime=[").append(activeFinishtime).append("] ");
		buffer.append("deadLine=[").append(deadLine).append("] ");
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
		if (!(obj instanceof SchoolTermActive))
			return false;
		SchoolTermActive equalCheck = (SchoolTermActive) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}