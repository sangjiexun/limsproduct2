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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllTimetableBatchs", query = "select myTimetableBatch from TimetableBatch myTimetableBatch"),
		@NamedQuery(name = "findTimetableBatchByBatchName", query = "select myTimetableBatch from TimetableBatch myTimetableBatch where myTimetableBatch.batchName = ?1"),
		@NamedQuery(name = "findTimetableBatchByBatchNameContaining", query = "select myTimetableBatch from TimetableBatch myTimetableBatch where myTimetableBatch.batchName like ?1"),
		@NamedQuery(name = "findTimetableBatchByCountGroup", query = "select myTimetableBatch from TimetableBatch myTimetableBatch where myTimetableBatch.countGroup = ?1"),
		@NamedQuery(name = "findTimetableBatchByCourseCode", query = "select myTimetableBatch from TimetableBatch myTimetableBatch where myTimetableBatch.courseCode = ?1"),
		@NamedQuery(name = "findTimetableBatchByCourseCodeContaining", query = "select myTimetableBatch from TimetableBatch myTimetableBatch where myTimetableBatch.courseCode like ?1"),
		@NamedQuery(name = "findTimetableBatchById", query = "select myTimetableBatch from TimetableBatch myTimetableBatch where myTimetableBatch.id = ?1"),
		@NamedQuery(name = "findTimetableBatchByIfselect", query = "select myTimetableBatch from TimetableBatch myTimetableBatch where myTimetableBatch.ifselect = ?1"),
		@NamedQuery(name = "findTimetableBatchByPrimaryKey", query = "select myTimetableBatch from TimetableBatch myTimetableBatch where myTimetableBatch.id = ?1") })
@Table(name = "timetable_batch")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "TimetableBatch")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class TimetableBatch implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * �ſη���?
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
	Integer id;
	/**
	 * ������
	 * 
	 */

	@Column(name = "count_group")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer countGroup;
	/**
	 * �����ƣ�Ĭ��Ϊ��1���...
	 * 
	 */

	@Column(name = "batch_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String batchName;
	/**
	 * ѡ������
	 * 
	 */

	@Column(name = "course_code")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String courseCode;
	/**
	 * �Ƿ�ѧ��ѡ��0�Զ�ѡ��1ѧ��ѡ��
	 * 
	 */

	@Column(name = "ifselect")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer ifselect;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar startDate;
	/**
	 * ѡ�ο�ʼ����
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar endDate;
	@Column(name = "flag")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer flag;

	@Column(name = "self_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer selfId;

	@Column(name = "course_no")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String courseNo;

	@Column(name = "max_group_num")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer maxGroupNum;

	/**
	 */
	@OneToMany(mappedBy = "timetableBatch", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableBatchItems> timetableBatchItemses;
	/**
	 */
	@OneToMany(mappedBy = "timetableBatch", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableGroup> timetableGroups;

	public Integer getSelfId() {
		return selfId;
	}

	public void setSelfId(Integer selfId) {
		this.selfId = selfId;
	}

	public String getCourseNo() {
		return courseNo;
	}

	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}

	public Integer getMaxGroupNum() {
		return maxGroupNum;
	}

	public void setMaxGroupNum(Integer maxGroupNum) {
		this.maxGroupNum = maxGroupNum;
	}

	/**
	 * �ſη���?
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * �ſη���?
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ������
	 * 
	 */
	public void setCountGroup(Integer countGroup) {
		this.countGroup = countGroup;
	}

	/**
	 * ������
	 * 
	 */
	public Integer getCountGroup() {
		return this.countGroup;
	}

	/**
	 * �����ƣ�Ĭ��Ϊ��1���...
	 * 
	 */
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	/**
	 * �����ƣ�Ĭ��Ϊ��1���...
	 * 
	 */
	public String getBatchName() {
		return this.batchName;
	}

	/**
	 * ѡ������
	 * 
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	/**
	 * ѡ������
	 * 
	 */
	public String getCourseCode() {
		return this.courseCode;
	}

	/**
	 * �Ƿ�ѧ��ѡ��0�Զ�ѡ��1ѧ��ѡ��
	 * 
	 */
	public void setIfselect(Integer ifselect) {
		this.ifselect = ifselect;
	}

	/**
	 * �Ƿ�ѧ��ѡ��0�Զ�ѡ��1ѧ��ѡ��
	 * 
	 */
	public Integer getIfselect() {
		return this.ifselect;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	/**
	 * ѡ�ο�ʼ����
	 * 
	 */
	public Calendar getStartDate() {
		return this.startDate;
	}

	/**
	 * ѡ�ο�ʼ����
	 * 
	 */
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	/**
	 * ѡ�ο�ʼ����
	 * 
	 */
	public Calendar getEndDate() {
		return this.endDate;
	}

	
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Integer getFlag() {
		return this.flag;
	}
	/**
	 */
	public void setTimetableBatchItemses(Set<TimetableBatchItems> timetableBatchItemses) {
		this.timetableBatchItemses = timetableBatchItemses;
	}

	/**
	 */
	@JsonIgnore
	public Set<TimetableBatchItems> getTimetableBatchItemses() {
		if (timetableBatchItemses == null) {
			timetableBatchItemses = new java.util.LinkedHashSet<net.zjcclims.domain.TimetableBatchItems>();
		}
		return timetableBatchItemses;
	}

	/**
	 */
	public void setTimetableGroups(Set<TimetableGroup> timetableGroups) {
		this.timetableGroups = timetableGroups;
	}

	/**
	 */
	@JsonIgnore
	public Set<TimetableGroup> getTimetableGroups() {
		if (timetableGroups == null) {
			timetableGroups = new java.util.LinkedHashSet<net.zjcclims.domain.TimetableGroup>();
		}
		return timetableGroups;
	}

	/**
	 */
	public TimetableBatch() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(TimetableBatch that) {
		setId(that.getId());
		setCountGroup(that.getCountGroup());
		setBatchName(that.getBatchName());
		setCourseCode(that.getCourseCode());
		setIfselect(that.getIfselect());
		setTimetableBatchItemses(new java.util.LinkedHashSet<net.zjcclims.domain.TimetableBatchItems>(that.getTimetableBatchItemses()));
		setTimetableGroups(new java.util.LinkedHashSet<net.zjcclims.domain.TimetableGroup>(that.getTimetableGroups()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("countGroup=[").append(countGroup).append("] ");
		buffer.append("batchName=[").append(batchName).append("] ");
		buffer.append("courseCode=[").append(courseCode).append("] ");
		buffer.append("ifselect=[").append(ifselect).append("] ");

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
		if (!(obj instanceof TimetableBatch))
			return false;
		TimetableBatch equalCheck = (TimetableBatch) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
