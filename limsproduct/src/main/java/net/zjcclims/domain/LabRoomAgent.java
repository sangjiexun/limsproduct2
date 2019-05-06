package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllLabRoomAgents", query = "select myLabRoomAgent from LabRoomAgent myLabRoomAgent"),
		@NamedQuery(name = "findLabRoomAgentByHardwareIp", query = "select myLabRoomAgent from LabRoomAgent myLabRoomAgent where myLabRoomAgent.hardwareIp = ?1"),
		@NamedQuery(name = "findLabRoomAgentByHardwareIpContaining", query = "select myLabRoomAgent from LabRoomAgent myLabRoomAgent where myLabRoomAgent.hardwareIp like ?1"),
		@NamedQuery(name = "findLabRoomAgentByManufactor", query = "select myLabRoomAgent from LabRoomAgent myLabRoomAgent where myLabRoomAgent.manufactor = ?1"),
		@NamedQuery(name = "findLabRoomAgentByManufactorContaining", query = "select myLabRoomAgent from LabRoomAgent myLabRoomAgent where myLabRoomAgent.manufactor like ?1"),
		@NamedQuery(name = "findLabRoomAgentBySnNo", query = "select myLabRoomAgent from LabRoomAgent myLabRoomAgent where myLabRoomAgent.snNo = ?1"),
		@NamedQuery(name = "findLabRoomAgentBySnNoContaining", query = "select myLabRoomAgent from LabRoomAgent myLabRoomAgent where myLabRoomAgent.snNo like ?1"),
		@NamedQuery(name = "findLabRoomAgentById", query = "select myLabRoomAgent from LabRoomAgent myLabRoomAgent where myLabRoomAgent.id = ?1"),
		@NamedQuery(name = "findLabRoomAgentByPrimaryKey", query = "select myLabRoomAgent from LabRoomAgent myLabRoomAgent where myLabRoomAgent.id = ?1") })
@Table(name = "lab_room_agent")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabRoomAgent")
public class LabRoomAgent implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ʵ���ҷ��Ҵ����˱�
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	/**
	 * ����Ӳ��IP
	 * 
	 */

	@Column(name = "hardware_ip")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String hardwareIp;

	/**
	 * ����Ӳ���˿�
	 * 
	 */

	@Column(name = "manufactor")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String manufactor;

	/**
	 * PageCam
	 * 
	 */

	@Column(name = "sn_no")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String snNo;

	public Integer getDoorindex() {
		return doorindex;
	}

	public void setDoorindex(Integer doorindex) {
		this.doorindex = doorindex;
	}

	@Column(name = "doorindex")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer doorindex;
	
	@Column(name = "hardware_status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String hardwareStatus;

	@Column(name = "hardware_version")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String hardwareVersion;

	@Column(name = "hareware_module")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String harewareModule;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "hardware_type", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionary;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_room_id", referencedColumnName = "id") })
	@XmlTransient
	LabRoom labRoom;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "server_id", referencedColumnName = "id") })
	@XmlTransient
	CommonServer commonServer;

	/**
	 */
	@OneToMany(mappedBy = "labRoomAgent", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDevice> labRoomDevices;
	
	/**
	 */
	@OneToMany(mappedBy = "labRoomAgentByEntranceId", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabEntranceVideo> labEntranceVideosForVideoId;
	/**
	 */
	@OneToMany(mappedBy = "labRoomAgentByEntranceId", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabEntranceVideo> labEntranceVideosForEntranceId;
	
	/**
	 */
	public void setLabEntranceVideosForVideoId(Set<LabEntranceVideo> labEntranceVideosForVideoId) {
		this.labEntranceVideosForVideoId = labEntranceVideosForVideoId;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabEntranceVideo> getLabEntranceVideosForVideoId() {
		if (labEntranceVideosForVideoId == null) {
			labEntranceVideosForVideoId = new java.util.LinkedHashSet<net.zjcclims.domain.LabEntranceVideo>();
		}
		return labEntranceVideosForVideoId;
	}

	/**
	 */
	public void setLabEntranceVideosForEntranceId(Set<LabEntranceVideo> labEntranceVideosForEntranceId) {
		this.labEntranceVideosForEntranceId = labEntranceVideosForEntranceId;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabEntranceVideo> getLabEntranceVideosForEntranceId() {
		if (labEntranceVideosForEntranceId == null) {
			labEntranceVideosForEntranceId = new java.util.LinkedHashSet<net.zjcclims.domain.LabEntranceVideo>();
		}
		return labEntranceVideosForEntranceId;
	}
	/**
	 * ʵ���ҷ��Ҵ����˱�
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ʵ���ҷ��Ҵ����˱�
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ����Ӳ��IP
	 * 
	 */
	public void setHardwareIp(String hardwareIp) {
		this.hardwareIp = hardwareIp;
	}

	/**
	 * ����Ӳ��IP
	 * 
	 */
	public String getHardwareIp() {
		return this.hardwareIp;
	}

	public String getManufactor() {
		return manufactor;
	}

	public void setManufactor(String manufactor) {
		this.manufactor = manufactor;
	}

	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}
	public String getHardwareStatus() {
		return hardwareStatus;
	}

	public void setHardwareStatus(String hardwareStatus) {
		this.hardwareStatus = hardwareStatus;
	}

	public String getHardwareVersion() {
		return hardwareVersion;
	}

	public void setHardwareVersion(String hardwareVersion) {
		this.hardwareVersion = hardwareVersion;
	}

	public String getHarewareModule() {
		return harewareModule;
	}

	public void setHarewareModule(String harewareModule) {
		this.harewareModule = harewareModule;
	}
	/**
	 */
	public void setCDictionary(CDictionary CDictionary) {
		this.CDictionary = CDictionary;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionary() {
		return CDictionary;
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
	public void setCommonServer(CommonServer commonServer) {
		this.commonServer = commonServer;
	}

	/**
	 */
	@JsonIgnore
	public CommonServer getCommonServer() {
		return commonServer;
	}

	/**
	 */
    @JsonIgnore
	public void setLabRoomDevice(Set<LabRoomDevice> labRoomDevices) {
		this.labRoomDevices = labRoomDevices;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomDevice> getLabRoomDevices() {
		if (labRoomDevices == null) {
			labRoomDevices = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>();
		}
		return labRoomDevices;
	}
	/**
	 */
	public LabRoomAgent() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabRoomAgent that) {
		setId(that.getId());
		setHardwareIp(that.getHardwareIp());
		setCDictionary(that.getCDictionary());
		setLabRoom(that.getLabRoom());
		setCommonServer(that.getCommonServer());
		setHardwareVersion(that.getHardwareVersion());
		setLabEntranceVideosForVideoId(new java.util.LinkedHashSet<net.zjcclims.domain.LabEntranceVideo>(that.getLabEntranceVideosForVideoId()));
		setLabEntranceVideosForEntranceId(new java.util.LinkedHashSet<net.zjcclims.domain.LabEntranceVideo>(that.getLabEntranceVideosForEntranceId()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("hardwareIp=[").append(hardwareIp).append("] ");

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
		if (!(obj instanceof LabRoomAgent))
			return false;
		LabRoomAgent equalCheck = (LabRoomAgent) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
