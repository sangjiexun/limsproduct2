package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;




@Entity
@NamedQueries({
		@NamedQuery(name = "findAllVirtualImages", query = "select myVirtualImage from VirtualImage myVirtualImage"),
		@NamedQuery(name = "findVirtualImageByPrimaryKey", query = "select myVirtualImage from VirtualImage myVirtualImage where myVirtualImage.id = ?1"),
})

@Table(name = "virtual_image")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "VirtualImage")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class VirtualImage implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ���⾵����ݱ�
	 *
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	Integer id;
	/**
	 */

	@Column(name = "name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String name;
	/**
	 */

	@Column(name = "hardware_set")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String hardwareSet;
	/**
	 */

	@Column(name = "image_code")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String imageCode;
	/**
	 */

	@Column(name = "provider")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String provider;
	/**
	 */

	@Column(name = "set_note")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String setNote;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({@JoinColumn(name="lab_room_id", referencedColumnName="id")})
	@XmlTransient
	LabRoom labRoom;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHardwareSet() {
		return hardwareSet;
	}

	public void setHardwareSet(String hardwareSet) {
		this.hardwareSet = hardwareSet;
	}

	public String getImageCode() {
		return imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getSetNote() {
		return setNote;
	}

	public void setSetNote(String setNote) {
		this.setNote = setNote;
	}

	public LabRoom getLabRoom() {
		return labRoom;
	}

	public void setLabRoom(LabRoom labRoom) {
		this.labRoom = labRoom;
	}
}
