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
import org.skyway.spring.util.databinding.TypeConversionUtils;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllLabRoomAdmins", query = "select myLabRoomAdmin from LabRoomAdmin myLabRoomAdmin"),
		@NamedQuery(name = "findLabRoomAdminById", query = "select myLabRoomAdmin from LabRoomAdmin myLabRoomAdmin where myLabRoomAdmin.id = ?1"),
		@NamedQuery(name = "findLabRoomAdminByPrimaryKey", query = "select myLabRoomAdmin from LabRoomAdmin myLabRoomAdmin where myLabRoomAdmin.id = ?1"),
		@NamedQuery(name = "findLabRoomAdminByTypeId", query = "select myLabRoomAdmin from LabRoomAdmin myLabRoomAdmin where myLabRoomAdmin.typeId = ?1") })
@Table(name = "lab_room_admin")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabRoomAdmin")
public class LabRoomAdmin implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ʵ���ҹ���Ա��
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	/**
	 * ����id��1��ʾ����Ա��2��ʾ��������Ա��
	 * 
	 */

	@Column(name = "type_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer typeId;
	/**
	 * Description 开始日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "start_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar startDate;
	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	/**
	 * Description 结束日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "end_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar endDate;
	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	/**
	 * 开始时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar startTime;

	public void setStartTime(Calendar startTime) {
		TypeConversionUtils.clearDateFields(startTime);
		this.startTime = startTime;
	}

	public Calendar getStartTime() {
		return this.startTime;
	}
	/**
	 * 结束时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar endTime;

	public void setEndTime(Calendar endTime) {
		TypeConversionUtils.clearDateFields(endTime);
		this.endTime = endTime;
	}

	public Calendar getEndTime() {
		return this.endTime;
	}
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_room_id", referencedColumnName = "id") })
	@XmlTransient
	LabRoom labRoom;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "username", referencedColumnName = "username") })
	@XmlTransient
	User user;

	/**
	 * ʵ���ҹ���Ա��
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ʵ���ҹ���Ա��
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ����id��1��ʾ����Ա��2��ʾ��������Ա��
	 * 
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	/**
	 * ����id��1��ʾ����Ա��2��ʾ��������Ա��
	 * 
	 */
	public Integer getTypeId() {
		return this.typeId;
	}

	/**
	 */
	public void setLabRoom(LabRoom labRoom) {
		this.labRoom = labRoom;
	}

	/**
	 */
	@JsonIgnore
	public LabRoom getLabRoom() {
		return labRoom;
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
	public LabRoomAdmin() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabRoomAdmin that) {
		setId(that.getId());
		setTypeId(that.getTypeId());
		setLabRoom(that.getLabRoom());
		setUser(that.getUser());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("typeId=[").append(typeId).append("] ");
		buffer.append("username=[").append(user.getUsername()).append("] ");

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
		if (!(obj instanceof LabRoomAdmin))
			return false;
		LabRoomAdmin equalCheck = (LabRoomAdmin) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
