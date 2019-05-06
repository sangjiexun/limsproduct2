package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Set;

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
		@NamedQuery(name = "findAllLabEntranceVideos", query = "select myLabEntranceVideo from LabEntranceVideo myLabEntranceVideo"),
		@NamedQuery(name = "findLabEntranceVideoById", query = "select myLabEntranceVideo from LabEntranceVideo myLabEntranceVideo where myLabEntranceVideo.id = ?1"),
		@NamedQuery(name = "findLabEntranceVideoByPrimaryKey", query = "select myLabEntranceVideo from LabEntranceVideo myLabEntranceVideo where myLabEntranceVideo.id = ?1") })
@Table(name = "lab_entrance_video")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabEntranceVideo")
public class LabEntranceVideo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * �Ž�����ͷ��ϵ��
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	Integer id;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "entrance_id", referencedColumnName = "id") })
	@XmlTransient
	LabRoomAgent labRoomAgentByEntranceId;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "video_id", referencedColumnName = "id") })
	@XmlTransient
	LabRoomAgent labRoomAgentByVideoId;

	/**
	 * �Ž�����ͷ��ϵ��
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * �Ž�����ͷ��ϵ��
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 */
	public void setLabRoomAgentByEntranceId(LabRoomAgent labRoomAgentByEntranceId) {
		this.labRoomAgentByEntranceId = labRoomAgentByEntranceId;
	}

	/**
	 */
	@JsonIgnore
	public LabRoomAgent getLabRoomAgentByEntranceId() {
		return labRoomAgentByEntranceId;
	}

	/**
	 */
	public void setLabRoomAgentByVideoId(LabRoomAgent labRoomAgentByVideoId) {
		this.labRoomAgentByVideoId = labRoomAgentByVideoId;
	}

	/**
	 */
	@JsonIgnore
	public LabRoomAgent getLabRoomAgentByVideoId() {
		return labRoomAgentByVideoId;
	}

	/**
	 */
	public LabEntranceVideo() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabEntranceVideo that) {
		setId(that.getId());
		setLabRoomAgentByEntranceId(that.getLabRoomAgentByEntranceId());
		setLabRoomAgentByVideoId(that.getLabRoomAgentByVideoId());
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
		if (!(obj instanceof LabEntranceVideo))
			return false;
		LabEntranceVideo equalCheck = (LabEntranceVideo) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
