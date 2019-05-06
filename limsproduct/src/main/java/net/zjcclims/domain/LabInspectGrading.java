package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Calendar;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllLabInspectGradings", query = "select myLabInspectGrading from LabInspectGrading myLabInspectGrading"),
		@NamedQuery(name = "findLabInspectGradingByBatchId", query = "select myLabInspectGrading from LabInspectGrading myLabInspectGrading where myLabInspectGrading.batchId = ?1"),
		@NamedQuery(name = "findLabInspectGradingByCreateTime", query = "select myLabInspectGrading from LabInspectGrading myLabInspectGrading where myLabInspectGrading.createTime = ?1"),
		@NamedQuery(name = "findLabInspectGradingByDocumentId", query = "select myLabInspectGrading from LabInspectGrading myLabInspectGrading where myLabInspectGrading.documentId = ?1"),
		@NamedQuery(name = "findLabInspectGradingById", query = "select myLabInspectGrading from LabInspectGrading myLabInspectGrading where myLabInspectGrading.id = ?1"),
		@NamedQuery(name = "findLabInspectGradingByLabRoomId", query = "select myLabInspectGrading from LabInspectGrading myLabInspectGrading where myLabInspectGrading.labRoomId = ?1"),
		@NamedQuery(name = "findLabInspectGradingByPoint", query = "select myLabInspectGrading from LabInspectGrading myLabInspectGrading where myLabInspectGrading.point = ?1"),
		@NamedQuery(name = "findLabInspectGradingByPrimaryKey", query = "select myLabInspectGrading from LabInspectGrading myLabInspectGrading where myLabInspectGrading.id = ?1"),
		@NamedQuery(name = "findLabInspectGradingByStandardId", query = "select myLabInspectGrading from LabInspectGrading myLabInspectGrading where myLabInspectGrading.standardId = ?1") })
@Table(name = "lab_inspect_grading")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabInspectGrading")
public class LabInspectGrading implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
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
	 * ��׼
	 *
	 */

	@Column(name = "standard_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer standardId;
	/**
	 * ����
	 *
	 */

	@Column(name = "point")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer point;
	/**
	 * ʵ�����id
	 *
	 */

	@Column(name = "lab_room_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer labRoomId;
	/**
	 * ����ʱ��
	 *
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createTime;
	/**
	 * ���۱�id
	 *
	 */

	@Column(name = "document_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer documentId;

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
	 * ��׼
	 *
	 */
	public void setStandardId(Integer standardId) {
		this.standardId = standardId;
	}

	/**
	 * ��׼
	 *
	 */
	public Integer getStandardId() {
		return this.standardId;
	}

	/**
	 * ����
	 *
	 */
	public void setPoint(Integer point) {
		this.point = point;
	}

	/**
	 * ����
	 *
	 */
	public Integer getPoint() {
		return this.point;
	}

	/**
	 * ʵ�����id
	 *
	 */
	public void setLabRoomId(Integer labRoomId) {
		this.labRoomId = labRoomId;
	}

	/**
	 * ʵ�����id
	 *
	 */
	public Integer getLabRoomId() {
		return this.labRoomId;
	}

	/**
	 * ����ʱ��
	 *
	 */
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	/**
	 * ����ʱ��
	 *
	 */
	public Calendar getCreateTime() {
		return this.createTime;
	}

	/**
	 * ���۱�id
	 *
	 */
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	/**
	 * ���۱�id
	 *
	 */
	public Integer getDocumentId() {
		return this.documentId;
	}

	/**
	 */
	public LabInspectGrading() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabInspectGrading that) {
		setId(that.getId());
		setBatchId(that.getBatchId());
		setStandardId(that.getStandardId());
		setPoint(that.getPoint());
		setLabRoomId(that.getLabRoomId());
		setCreateTime(that.getCreateTime());
		setDocumentId(that.getDocumentId());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("batchId=[").append(batchId).append("] ");
		buffer.append("standardId=[").append(standardId).append("] ");
		buffer.append("point=[").append(point).append("] ");
		buffer.append("labRoomId=[").append(labRoomId).append("] ");
		buffer.append("createTime=[").append(createTime).append("] ");
		buffer.append("documentId=[").append(documentId).append("] ");

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
		if (!(obj instanceof LabInspectGrading))
			return false;
		LabInspectGrading equalCheck = (LabInspectGrading) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
