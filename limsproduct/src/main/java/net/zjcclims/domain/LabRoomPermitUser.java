package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

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
		@NamedQuery(name = "findAllLabRoomPermitUsers", query = "select myLabRoomPermitUser from LabRoomPermitUser myLabRoomPermitUser"),
		@NamedQuery(name = "findLabRoomPermitUserByFlag", query = "select myLabRoomPermitUser from LabRoomPermitUser myLabRoomPermitUser where myLabRoomPermitUser.flag = ?1"),
		@NamedQuery(name = "findLabRoomPermitUserById", query = "select myLabRoomPermitUser from LabRoomPermitUser myLabRoomPermitUser where myLabRoomPermitUser.id = ?1"),
		@NamedQuery(name = "findLabRoomPermitUserByPrimaryKey", query = "select myLabRoomPermitUser from LabRoomPermitUser myLabRoomPermitUser where myLabRoomPermitUser.id = ?1") })
@Table(name = "lab_room_permit_user")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabRoomPermitUser")
public class LabRoomPermitUser implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ʵѵ������ʹ����
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ���λ��1 ������ѵͨ��  2 ��ѵͨ��  3 ��ѵ���ţ�
	 * 
	 */

	@Column(name = "flag")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer flag;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_room", referencedColumnName = "id") })
	@XmlTransient
	LabRoom labRoom;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "student", referencedColumnName = "username") })
	@XmlTransient
	User user;

	/**
	 * ʵѵ������ʹ����
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ʵѵ������ʹ����
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ���λ��1 ������ѵͨ��  2 ��ѵͨ��  3 ��ѵ���ţ�
	 * 
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	/**
	 * ���λ��1 ������ѵͨ��  2 ��ѵͨ��  3 ��ѵ���ţ�
	 * 
	 */
	public Integer getFlag() {
		return this.flag;
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
	public LabRoomPermitUser() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabRoomPermitUser that) {
		setId(that.getId());
		setFlag(that.getFlag());
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
		buffer.append("flag=[").append(flag).append("] ");

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
		if (!(obj instanceof LabRoomPermitUser))
			return false;
		LabRoomPermitUser equalCheck = (LabRoomPermitUser) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
