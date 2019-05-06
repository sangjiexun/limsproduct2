package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Calendar;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllLabInspectSettings", query = "select myLabInspectSetting from LabInspectSetting myLabInspectSetting"),
		@NamedQuery(name = "findLabInspectSettingByEndTime", query = "select myLabInspectSetting from LabInspectSetting myLabInspectSetting where myLabInspectSetting.endTime = ?1"),
		@NamedQuery(name = "findLabInspectSettingByEndTimeAfter", query = "select myLabInspectSetting from LabInspectSetting myLabInspectSetting where myLabInspectSetting.endTime > ?1"),
		@NamedQuery(name = "findLabInspectSettingByEndTimeBefore", query = "select myLabInspectSetting from LabInspectSetting myLabInspectSetting where myLabInspectSetting.endTime < ?1"),
		@NamedQuery(name = "findLabInspectSettingById", query = "select myLabInspectSetting from LabInspectSetting myLabInspectSetting where myLabInspectSetting.id = ?1"),
		@NamedQuery(name = "findLabInspectSettingByIsRegular", query = "select myLabInspectSetting from LabInspectSetting myLabInspectSetting where myLabInspectSetting.isRegular = ?1"),
		@NamedQuery(name = "findLabInspectSettingByPrimaryKey", query = "select myLabInspectSetting from LabInspectSetting myLabInspectSetting where myLabInspectSetting.id = ?1"),
		@NamedQuery(name = "findLabInspectSettingBySemeter", query = "select myLabInspectSetting from LabInspectSetting myLabInspectSetting where myLabInspectSetting.semeter = ?1"),
		@NamedQuery(name = "findLabInspectSettingBySemeterContaining", query = "select myLabInspectSetting from LabInspectSetting myLabInspectSetting where myLabInspectSetting.semeter like ?1"),
		@NamedQuery(name = "findLabInspectSettingByStartTime", query = "select myLabInspectSetting from LabInspectSetting myLabInspectSetting where myLabInspectSetting.startTime = ?1"),
		@NamedQuery(name = "findLabInspectSettingByStartTimeAfter", query = "select myLabInspectSetting from LabInspectSetting myLabInspectSetting where myLabInspectSetting.startTime > ?1"),
		@NamedQuery(name = "findLabInspectSettingByStartTimeBefore", query = "select myLabInspectSetting from LabInspectSetting myLabInspectSetting where myLabInspectSetting.startTime < ?1"),
		@NamedQuery(name = "findLabInspectSettingByWeek", query = "select myLabInspectSetting from LabInspectSetting myLabInspectSetting where myLabInspectSetting.week = ?1") })
@Table(name = "lab_inspect_setting")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabInspectSetting")
public class LabInspectSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ���-Ψһһ��id ����
	 *
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ѧ��
	 *
	 */

	@Column(name = "semeter")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String semeter;
	/**
	 * �������
	 *
	 */

	@Column(name = "is_regular")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Boolean isRegular;
	/**
	 * һ������������0000000-1111111
	 *
	 */

	@Column(name = "week")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer week;
	/**
	 * ��ʼʱ��
	 *
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "start_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar startTime;
	/**
	 * ����ʱ��
	 *
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "end_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar endTime;

	@Column(name = "title")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String title;

	@Column(name = "comment")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String comment;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * ���-Ψһһ��id ����
	 *
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ���-Ψһһ��id ����
	 *
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ѧ��
	 *
	 */
	public void setSemeter(String semeter) {
		this.semeter = semeter;
	}

	/**
	 * ѧ��
	 *
	 */
	public String getSemeter() {
		return this.semeter;
	}

	/**
	 * �������
	 *
	 */
	public void setIsRegular(Boolean isRegular) {
		this.isRegular = isRegular;
	}

	/**
	 * �������
	 *
	 */
	public Boolean getIsRegular() {
		return this.isRegular;
	}

	/**
	 * һ������������0000000-1111111
	 *
	 */
	public void setWeek(Integer week) {
		this.week = week;
	}

	/**
	 * һ������������0000000-1111111
	 *
	 */
	public Integer getWeek() {
		return this.week;
	}

	/**
	 * ��ʼʱ��
	 *
	 */
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	/**
	 * ��ʼʱ��
	 *
	 */
	public Calendar getStartTime() {
		return this.startTime;
	}

	/**
	 * ����ʱ��
	 *
	 */
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	/**
	 * ����ʱ��
	 *
	 */
	public Calendar getEndTime() {
		return this.endTime;
	}

	/**
	 */
	public LabInspectSetting() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabInspectSetting that) {
		setId(that.getId());
		setSemeter(that.getSemeter());
		setIsRegular(that.getIsRegular());
		setWeek(that.getWeek());
		setStartTime(that.getStartTime());
		setEndTime(that.getEndTime());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("semeter=[").append(semeter).append("] ");
		buffer.append("isRegular=[").append(isRegular).append("] ");
		buffer.append("week=[").append(week).append("] ");
		buffer.append("startTime=[").append(startTime).append("] ");
		buffer.append("endTime=[").append(endTime).append("] ");

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
		if (!(obj instanceof LabInspectSetting))
			return false;
		LabInspectSetting equalCheck = (LabInspectSetting) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
