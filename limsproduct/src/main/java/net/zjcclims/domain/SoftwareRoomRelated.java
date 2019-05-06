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
		@NamedQuery(name = "findAllSoftwareRoomRelateds", query = "select mySoftwareRoomRelated from SoftwareRoomRelated mySoftwareRoomRelated"),
		@NamedQuery(name = "findSoftwareRoomRelatedById", query = "select mySoftwareRoomRelated from SoftwareRoomRelated mySoftwareRoomRelated where mySoftwareRoomRelated.id = ?1"),
		@NamedQuery(name = "findSoftwareRoomRelatedByPrimaryKey", query = "select mySoftwareRoomRelated from SoftwareRoomRelated mySoftwareRoomRelated where mySoftwareRoomRelated.id = ?1") })
@Table(name = "software_room_related")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "SoftwareRoomRelated")
public class SoftwareRoomRelated implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ʵѵ��-�������
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_room", referencedColumnName = "id") })
	@XmlTransient
	LabRoom labRoom;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "software", referencedColumnName = "id") })
	@XmlTransient
	Software software;

	/**
	 * ʵѵ��-�������
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ʵѵ��-�������
	 * 
	 */
	public Integer getId() {
		return this.id;
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
	public void setSoftware(Software software) {
		this.software = software;
	}

	/**
	 */
	@JsonIgnore
	public Software getSoftware() {
		return software;
	}

	/**
	 */
	public SoftwareRoomRelated() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(SoftwareRoomRelated that) {
		setId(that.getId());
		setLabRoom(that.getLabRoom());
		setSoftware(that.getSoftware());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");

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
		if (!(obj instanceof SoftwareRoomRelated))
			return false;
		SoftwareRoomRelated equalCheck = (SoftwareRoomRelated) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
