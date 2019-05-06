package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllMInspectSettings", query = "select myMInspectSetting from MInspectSetting myMInspectSetting"),
		@NamedQuery(name = "findMInspectSettingByBatchId", query = "select myMInspectSetting from MInspectSetting myMInspectSetting where myMInspectSetting.batchId = ?1"),
		@NamedQuery(name = "findMInspectSettingById", query = "select myMInspectSetting from MInspectSetting myMInspectSetting where myMInspectSetting.id = ?1"),
		@NamedQuery(name = "findMInspectSettingByPrimaryKey", query = "select myMInspectSetting from MInspectSetting myMInspectSetting where myMInspectSetting.id = ?1") })
@Table(name = "m_inspect_setting")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "MInspectSetting")
public class MInspectSetting implements Serializable {
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
	 * ���id
	 *
	 */

	@Column(name = "batch_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer batchId;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "standard_id", referencedColumnName = "id") })
	@XmlTransient
	LabInspect labInspect;

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
	 * ���id
	 *
	 */
	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	/**
	 * ���id
	 *
	 */
	public Integer getBatchId() {
		return this.batchId;
	}

	/**
	 */
	public void setLabInspect(LabInspect labInspect) {
		this.labInspect = labInspect;
	}

	/**
	 */
	@JsonIgnore
	public LabInspect getLabInspect() {
		return labInspect;
	}

	/**
	 */
	public MInspectSetting() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(MInspectSetting that) {
		setId(that.getId());
		setBatchId(that.getBatchId());
		setLabInspect(that.getLabInspect());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("batchId=[").append(batchId).append("] ");

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
		if (!(obj instanceof MInspectSetting))
			return false;
		MInspectSetting equalCheck = (MInspectSetting) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
