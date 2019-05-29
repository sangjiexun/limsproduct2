package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.ziclix.python.sql.Fetch;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllAssetCabinets", query = "select myAssetCabinet from AssetCabinet myAssetCabinet"),
		@NamedQuery(name = "findAssetCabinetByCabinetCode", query = "select myAssetCabinet from AssetCabinet myAssetCabinet where myAssetCabinet.cabinetCode = ?1"),
		@NamedQuery(name = "findAssetCabinetByCabinetCodeContaining", query = "select myAssetCabinet from AssetCabinet myAssetCabinet where myAssetCabinet.cabinetCode like ?1"),
		@NamedQuery(name = "findAssetCabinetByCabinetName", query = "select myAssetCabinet from AssetCabinet myAssetCabinet where myAssetCabinet.cabinetName = ?1"),
		@NamedQuery(name = "findAssetCabinetByCabinetNameContaining", query = "select myAssetCabinet from AssetCabinet myAssetCabinet where myAssetCabinet.cabinetName like ?1"),
		@NamedQuery(name = "findAssetCabinetById", query = "select myAssetCabinet from AssetCabinet myAssetCabinet where myAssetCabinet.id = ?1"),
		@NamedQuery(name = "findAssetCabinetByPrimaryKey", query = "select myAssetCabinet from AssetCabinet myAssetCabinet where myAssetCabinet.id = ?1") })
@Table(name = "asset_cabinet")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "AssetCabinet")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class AssetCabinet implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ��Ʒ����
	 * 
	 */

	@Column(name = "cabinet_code")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String cabinetCode;
	/**
	 */

	@Column(name = "cabinet_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String cabinetName;
	
	@Column(name = "flag")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer flag;

	@Column(name="capacity")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer capacity;

	@Column(name="type")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer type;

	@Column(name = "hardware_ip")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String hardwareIp;

	@Column(name = "hardware_type")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String hardwareType;

	@Column(name="server_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer serverId;

	@Column(name = "location")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String location;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_id", referencedColumnName = "id") })
	@XmlTransient
	LabRoom labRoom;


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
	 * ��Ʒ����
	 * 
	 */
	public void setCabinetCode(String cabinetCode) {
		this.cabinetCode = cabinetCode;
	}

	/**
	 * ��Ʒ����
	 * 
	 */
	public String getCabinetCode() {
		return this.cabinetCode;
	}

	/**
	 */
	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
	}

	/**
	 */
	public String getCabinetName() {
		return this.cabinetName;
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

	
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getHardwareIp() {
		return hardwareIp;
	}

	public void setHardwareIp(String hardwareIp) {
		this.hardwareIp = hardwareIp;
	}

	public String getHardwareType() {
		return hardwareType;
	}

	public void setHardwareType(String hardwareType) {
		this.hardwareType = hardwareType;
	}

	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}



	/**
	 */
	public AssetCabinet() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(AssetCabinet that) {
		setId(that.getId());
		setCabinetCode(that.getCabinetCode());
		setCabinetName(that.getCabinetName());
		setLabRoom(that.getLabRoom());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("cabinetCode=[").append(cabinetCode).append("] ");
		buffer.append("cabinetName=[").append(cabinetName).append("] ");

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
		if (!(obj instanceof AssetCabinet))
			return false;
		AssetCabinet equalCheck = (AssetCabinet) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
