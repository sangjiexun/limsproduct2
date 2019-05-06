package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 */

@Entity
@NamedQueries({})
@Table(name = "item_plans")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ItemPlan")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class ItemPlan implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ��������
	 *
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * 选课开始时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar startTime;
	/**
	 * 选课结束时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar endTime;

	/**
	 * 项目
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "item_id", referencedColumnName = "id") })
	@XmlTransient
	OperationItem operationItem;

	/**
	 * 关联自主课程
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "self_course_id", referencedColumnName = "id") })
	@XmlTransient
	TimetableSelfCourse timetableSelfCourse;
	/**
	 * 类型
	 */
	@Column(name = "type")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer type;

	/**
	 * ��������
	 *
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ��������
	 *
	 */
	public Integer getId() {
		return this.id;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public OperationItem getOperationItem() {
		return operationItem;
	}

	public void setOperationItem(OperationItem operationItem) {
		this.operationItem = operationItem;
	}

	public TimetableSelfCourse getTimetableSelfCourse() {
		return timetableSelfCourse;
	}

	public void setTimetableSelfCourse(TimetableSelfCourse timetableSelfCourse) {
		this.timetableSelfCourse = timetableSelfCourse;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 */
	public ItemPlan() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ItemPlan that) {
		setId(that.getId());
		setEndTime(that.getEndTime());
		setStartTime(that.getStartTime());
		setOperationItem(that.getOperationItem());
		setTimetableSelfCourse(that.getTimetableSelfCourse());
		setType(that.getType());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("startTime=[").append(startTime).append("] ");
		buffer.append("endTime=[").append(endTime).append("] ");
		buffer.append("type=[").append(type).append("] ");

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
		if (!(obj instanceof ItemPlan))
			return false;
		ItemPlan equalCheck = (ItemPlan) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
