package net.zjcclims.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllSchoolYears", query = "select mySchoolYear from SchoolYear mySchoolYear"),
		@NamedQuery(name = "findSchoolYearByCode", query = "select mySchoolYear from SchoolYear mySchoolYear where mySchoolYear.code = ?1"),
		@NamedQuery(name = "findSchoolYearByCodeContaining", query = "select mySchoolYear from SchoolYear mySchoolYear where mySchoolYear.code like ?1"),
		@NamedQuery(name = "findSchoolYearById", query = "select mySchoolYear from SchoolYear mySchoolYear where mySchoolYear.id = ?1"),
		@NamedQuery(name = "findSchoolYearByPrimaryKey", query = "select mySchoolYear from SchoolYear mySchoolYear where mySchoolYear.id = ?1"),
		@NamedQuery(name = "findSchoolYearByYearEnd", query = "select mySchoolYear from SchoolYear mySchoolYear where mySchoolYear.yearEnd = ?1"),
		@NamedQuery(name = "findSchoolYearByYearEndAfter", query = "select mySchoolYear from SchoolYear mySchoolYear where mySchoolYear.yearEnd > ?1"),
		@NamedQuery(name = "findSchoolYearByYearEndBefore", query = "select mySchoolYear from SchoolYear mySchoolYear where mySchoolYear.yearEnd < ?1"),
		@NamedQuery(name = "findSchoolYearByYearName", query = "select mySchoolYear from SchoolYear mySchoolYear where mySchoolYear.yearName = ?1"),
		@NamedQuery(name = "findSchoolYearByYearNameContaining", query = "select mySchoolYear from SchoolYear mySchoolYear where mySchoolYear.yearName like ?1"),
		@NamedQuery(name = "findSchoolYearByYearStart", query = "select mySchoolYear from SchoolYear mySchoolYear where mySchoolYear.yearStart = ?1"),
		@NamedQuery(name = "findSchoolYearByYearStartAfter", query = "select mySchoolYear from SchoolYear mySchoolYear where mySchoolYear.yearStart > ?1"),
		@NamedQuery(name = "findSchoolYearByYearStartBefore", query = "select mySchoolYear from SchoolYear mySchoolYear where mySchoolYear.yearStart < ?1") })
@Table(name = "school_year")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "SchoolYear")
public class SchoolYear implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ѧ����
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
	Integer id;
	/**
	 * ѧ�����
	 * 
	 */

	@Column(name = "year_name", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String yearName;
	/**
	 * ѧ�꿪ʼʱ��
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "year_start")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar yearStart;
	/**
	 * ѧ�����ʱ��
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "year_end")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar yearEnd;
	/**
	 * ��year_start�Ŀ�ʼ�걣��һ��(�ڴ�����ʵ��)
	 * 
	 */

	@Column(name = "code", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer code;
	
	/**
	 */
	/*@OneToMany(mappedBy = "schoolYear", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.gvsun.domain.LabRoomYearRun> labRoomYearRuns;*/
	/**
	 * ѧ����
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ѧ����
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ѧ�����
	 * 
	 */
	public void setYearName(String yearName) {
		this.yearName = yearName;
	}

	/**
	 * ѧ�����
	 * 
	 */
	public String getYearName() {
		return this.yearName;
	}

	/**
	 * ѧ�꿪ʼʱ��
	 * 
	 */
	public void setYearStart(Calendar yearStart) {
		this.yearStart = yearStart;
	}

	/**
	 * ѧ�꿪ʼʱ��
	 * 
	 */
	public Calendar getYearStart() {
		return this.yearStart;
	}

	/**
	 * ѧ�����ʱ��
	 * 
	 */
	public void setYearEnd(Calendar yearEnd) {
		this.yearEnd = yearEnd;
	}

	/**
	 * ѧ�����ʱ��
	 * 
	 */
	public Calendar getYearEnd() {
		return this.yearEnd;
	}

	/**
	 * ��year_start�Ŀ�ʼ�걣��һ��(�ڴ�����ʵ��)
	 * 
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * ��year_start�Ŀ�ʼ�걣��һ��(�ڴ�����ʵ��)
	 * 
	 */
	public Integer getCode() {
		return this.code;
	}

	
	/**
	 */
	/*public void setLabRoomYearRuns(Set<LabRoomYearRun> labRoomYearRuns) {
		this.labRoomYearRuns = labRoomYearRuns;
	}

	*//**
	 *//*
	@JsonIgnore
	public Set<LabRoomYearRun> getLabRoomYearRuns() {
		if (labRoomYearRuns == null) {
			labRoomYearRuns = new java.util.LinkedHashSet<net.gvsun.domain.LabRoomYearRun>();
		}
		return labRoomYearRuns;
	}*/
    
	/**
	 */
	public SchoolYear() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(SchoolYear that) {
		setId(that.getId());
		setYearName(that.getYearName());
		setYearStart(that.getYearStart());
		setYearEnd(that.getYearEnd());
		setCode(that.getCode());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("yearName=[").append(yearName).append("] ");
		buffer.append("yearStart=[").append(yearStart).append("] ");
		buffer.append("yearEnd=[").append(yearEnd).append("] ");
		buffer.append("code=[").append(code).append("] ");

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
		if (!(obj instanceof SchoolYear))
			return false;
		SchoolYear equalCheck = (SchoolYear) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
