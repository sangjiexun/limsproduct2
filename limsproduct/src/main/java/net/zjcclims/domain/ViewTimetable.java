package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllViewTimetables", query = "select myViewTimetable from ViewTimetable myViewTimetable"),
		@NamedQuery(name = "findViewTimetableByCourseNo", query = "select myViewTimetable from ViewTimetable myViewTimetable where myViewTimetable.courseNo = ?1"),
		@NamedQuery(name = "findViewTimetableByCourseNoContaining", query = "select myViewTimetable from ViewTimetable myViewTimetable where myViewTimetable.courseNo like ?1"),
		@NamedQuery(name = "findViewTimetableByEndTime", query = "select myViewTimetable from ViewTimetable myViewTimetable where myViewTimetable.endTime = ?1"),
		@NamedQuery(name = "findViewTimetableByEndTimeContaining", query = "select myViewTimetable from ViewTimetable myViewTimetable where myViewTimetable.endTime like ?1"),
		@NamedQuery(name = "findViewTimetableById", query = "select myViewTimetable from ViewTimetable myViewTimetable where myViewTimetable.id = ?1"),
		@NamedQuery(name = "findViewTimetableByLabId", query = "select myViewTimetable from ViewTimetable myViewTimetable where myViewTimetable.labId = ?1"),
		@NamedQuery(name = "findViewTimetableByPId", query = "select myViewTimetable from ViewTimetable myViewTimetable where myViewTimetable.PId = ?1"),
		@NamedQuery(name = "findViewTimetableByPIdContaining", query = "select myViewTimetable from ViewTimetable myViewTimetable where myViewTimetable.PId like ?1"),
		@NamedQuery(name = "findViewTimetableByPrimaryKey", query = "select myViewTimetable from ViewTimetable myViewTimetable where myViewTimetable.id = ?1"),
		@NamedQuery(name = "findViewTimetableByStartTime", query = "select myViewTimetable from ViewTimetable myViewTimetable where myViewTimetable.startTime = ?1"),
		@NamedQuery(name = "findViewTimetableByStartTimeContaining", query = "select myViewTimetable from ViewTimetable myViewTimetable where myViewTimetable.startTime like ?1"),
		@NamedQuery(name = "findViewTimetableByTimetableStyle", query = "select myViewTimetable from ViewTimetable myViewTimetable where myViewTimetable.timetableStyle = ?1"),
		@NamedQuery(name = "findViewTimetableByUsername", query = "select myViewTimetable from ViewTimetable myViewTimetable where myViewTimetable.username = ?1"),
		@NamedQuery(name = "findViewTimetableByUsernameContaining", query = "select myViewTimetable from ViewTimetable myViewTimetable where myViewTimetable.username like ?1") })
@Table(name = "view_timetable")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ViewTimetable")
public class ViewTimetable implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
	Integer id;
	/**
	 * ʵ����id
	 * 
	 */

	@Column(name = "lab_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer labId;
	/**
	 * �û���
	 * 
	 */

	@Column(name = "username", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String username;
	/**
	 * ��ʼʱ��
	 * 
	 */

	@Column(name = "start_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String startTime;
	/**
	 * ����ʱ��
	 * 
	 */

	@Column(name = "end_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String endTime;
	/**
	 * �ſ����ͣ�1��ʾ�����ſΣ�2��ʾ�����ſ�,3��ʾʵ����ԤԼ��
	 * 
	 */

	@Column(name = "timetable_style")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer timetableStyle;
	/**
	 * �γ̱��
	 * 
	 */

	@Column(name = "course_no", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String courseNo;
	/**
	 */

	@Column(name = "p_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String PId;

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
	 * ʵ����id
	 * 
	 */
	public void setLabId(Integer labId) {
		this.labId = labId;
	}

	/**
	 * ʵ����id
	 * 
	 */
	public Integer getLabId() {
		return this.labId;
	}

	/**
	 * �û���
	 * 
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * �û���
	 * 
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * ��ʼʱ��
	 * 
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * ��ʼʱ��
	 * 
	 */
	public String getStartTime() {
		return this.startTime;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public String getEndTime() {
		return this.endTime;
	}

	/**
	 * �ſ����ͣ�1��ʾ�����ſΣ�2��ʾ�����ſ�,3��ʾʵ����ԤԼ��
	 * 
	 */
	public void setTimetableStyle(Integer timetableStyle) {
		this.timetableStyle = timetableStyle;
	}

	/**
	 * �ſ����ͣ�1��ʾ�����ſΣ�2��ʾ�����ſ�,3��ʾʵ����ԤԼ��
	 * 
	 */
	public Integer getTimetableStyle() {
		return this.timetableStyle;
	}

	/**
	 * �γ̱��
	 * 
	 */
	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}

	/**
	 * �γ̱��
	 * 
	 */
	public String getCourseNo() {
		return this.courseNo;
	}

	/**
	 */
	public void setPId(String PId) {
		this.PId = PId;
	}

	/**
	 */
	public String getPId() {
		return this.PId;
	}

	/**
	 */
	public ViewTimetable() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ViewTimetable that) {
		setId(that.getId());
		setLabId(that.getLabId());
		setUsername(that.getUsername());
		setStartTime(that.getStartTime());
		setEndTime(that.getEndTime());
		setTimetableStyle(that.getTimetableStyle());
		setCourseNo(that.getCourseNo());
		setPId(that.getPId());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("labId=[").append(labId).append("] ");
		buffer.append("username=[").append(username).append("] ");
		buffer.append("startTime=[").append(startTime).append("] ");
		buffer.append("endTime=[").append(endTime).append("] ");
		buffer.append("timetableStyle=[").append(timetableStyle).append("] ");
		buffer.append("courseNo=[").append(courseNo).append("] ");
		buffer.append("PId=[").append(PId).append("] ");

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
		if (!(obj instanceof ViewTimetable))
			return false;
		ViewTimetable equalCheck = (ViewTimetable) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
