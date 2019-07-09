package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllSystemFloorPics", query = "select mySystemFloorPic from SystemFloorPic mySystemFloorPic"),
		@NamedQuery(name = "findSystemFloorPicByDocumentName", query = "select mySystemFloorPic from SystemFloorPic mySystemFloorPic where mySystemFloorPic.documentName = ?1"),
		@NamedQuery(name = "findSystemFloorPicByDocumentNameContaining", query = "select mySystemFloorPic from SystemFloorPic mySystemFloorPic where mySystemFloorPic.documentName like ?1"),
		@NamedQuery(name = "findSystemFloorPicByDocumentUrl", query = "select mySystemFloorPic from SystemFloorPic mySystemFloorPic where mySystemFloorPic.documentUrl = ?1"),
		@NamedQuery(name = "findSystemFloorPicByDocumentUrlContaining", query = "select mySystemFloorPic from SystemFloorPic mySystemFloorPic where mySystemFloorPic.documentUrl like ?1"),
		@NamedQuery(name = "findSystemFloorPicByFloorNo", query = "select mySystemFloorPic from SystemFloorPic mySystemFloorPic where mySystemFloorPic.floorNo = ?1"),
		@NamedQuery(name = "findSystemFloorPicById", query = "select mySystemFloorPic from SystemFloorPic mySystemFloorPic where mySystemFloorPic.id = ?1"),
		@NamedQuery(name = "findSystemFloorPicByPrimaryKey", query = "select mySystemFloorPic from SystemFloorPic mySystemFloorPic where mySystemFloorPic.id = ?1"),
		@NamedQuery(name = "findSystemFloorPicBySystemBuild", query = "select mySystemFloorPic from SystemFloorPic mySystemFloorPic where mySystemFloorPic.systemBuild = ?1"),
		@NamedQuery(name = "findSystemFloorPicBySystemBuildContaining", query = "select mySystemFloorPic from SystemFloorPic mySystemFloorPic where mySystemFloorPic.systemBuild like ?1") })
@Table(name = "system_floor_pic")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "limsproduct/net/limsproduct/domain", name = "SystemFloorPic")
public class SystemFloorPic implements Serializable {
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
	 * ¥����
	 * 
	 */

	@Column(name = "system_build")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String systemBuild;
	/**
	 * ¥���
	 * 
	 */

	@Column(name = "floor_no")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer floorNo;

	@Column(name = "floor_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String floorName;

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}
	/**
	 * �ϴ����ļ�����
	 * 
	 */

	@Column(name = "document_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String documentName;
	/**
	 * �ϴ����ļ�����·��
	 * 
	 */

	@Column(name = "document_url")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String documentUrl;

	@Column(name = "photo_url")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String photoUrl;

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

	/**
	 * ¥����
	 * 
	 */
	public void setSystemBuild(String systemBuild) {
		this.systemBuild = systemBuild;
	}

	/**
	 * ¥����
	 * 
	 */
	public String getSystemBuild() {
		return this.systemBuild;
	}

	/**
	 * ¥���
	 * 
	 */
	public void setFloorNo(Integer floorNo) {
		this.floorNo = floorNo;
	}

	/**
	 * ¥���
	 * 
	 */
	public Integer getFloorNo() {
		return this.floorNo;
	}

	/**
	 * �ϴ����ļ�����
	 * 
	 */
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	/**
	 * �ϴ����ļ�����
	 * 
	 */
	public String getDocumentName() {
		return this.documentName;
	}

	/**
	 * �ϴ����ļ�����·��
	 * 
	 */
	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	/**
	 * �ϴ����ļ�����·��
	 * 
	 */
	public String getDocumentUrl() {
		return this.documentUrl;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	/**
	 */
	public SystemFloorPic() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(SystemFloorPic that) {
		setId(that.getId());
		setSystemBuild(that.getSystemBuild());
		setFloorNo(that.getFloorNo());
		setDocumentName(that.getDocumentName());
		setDocumentUrl(that.getDocumentUrl());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("systemBuild=[").append(systemBuild).append("] ");
		buffer.append("floorNo=[").append(floorNo).append("] ");
		buffer.append("documentName=[").append(documentName).append("] ");
		buffer.append("documentUrl=[").append(documentUrl).append("] ");

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
		if (!(obj instanceof SystemFloorPic))
			return false;
		SystemFloorPic equalCheck = (SystemFloorPic) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
